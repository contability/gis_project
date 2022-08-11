/**
 * 횡단면도 객체
 */
var crossSectionalViewObj = {
	
	/**
	 * d3 로 선택한 객체 
	 * @type {Object}
	 */
	svg : null,
	
	/**
	 * 검색할 도로 및 지하시설물 정보 
	 * @type {Array}
	 */
	data : null,
	
	/**
	 * 횡단면도 그리기 구조
	 * @type {Object}
	 */
	layout : {
		
		/**
		 * 너비
		 * @type {Number}
		 */
		width : null,
		
		/**
		 * 높이
		 * @type {Number}
		 */
		height : null,
		
		/**
		 * 횡단면도 영역에서 실제 그리드가 그려질 영역 까지의 오프셋
		 * @type {Object}
		 */
		baseOffset : {
			/**
			 * 왼쪽 오프셋
			 * @type {Number}
			 */
			left : 60,
			
			/**
			 * 아래쪽 오프셋
			 * @type {Number}
			 */
			bottom : 55,
			
			/**
			 * 오른쪽 오프셋
			 * @type {Number}
			 */
			right : 30,
			
			/**
			 * 위쪽 오프셋
			 * @type {Number}
			 */
			top : 20
		},
		
		/**
		 * 횡단면도 영역에서 실제 그리드가 그려질 영역의 기준 픽셀
		 * @type {Object}
		 */
		baseLines : null,
		
		/**
		 * 횡단면도 영역 너비 설정
		 * @param {Number} width 너비
		 */
		setWidth : function(width) {
			var that = this;
			that.width = width;
			that.baseLines = {
				left : that.baseOffset.left,
				right: width - that.baseOffset.right
			};
		},
		
		/**
		 * 횡단면도 영역 높이 설정
		 * @param {Number} dep 최대 심도
		 * @param {Number} resolution 해상도
		 */
		setHeight : function(dep, resolution) {
			var that = this;
			that.height = dep / resolution + that.baseOffset.top + that.baseOffset.bottom;
			that.baseLines.bottom = that.height - that.baseOffset.bottom;
			that.baseLines.top = that.baseOffset.top;
		},
		
		getHeight : function() {
			var that = this;
			return that.height;
		}
	},
	
	/**
	 * 해상도 (1 픽셀 당 거리)
	 * @type {Object}
	 */
	resolution : null,
	
	/**
	 * 횡단면도 데이터
	 * @type {Object}
	 */
	crossData : null,
	
	/**
	 * 이벤트 핸들러
	 * @type {Object}
	 */
	hander : {
		
		/**
		 * 지하시설물 선택 핸들러
		 * @type {Function}
		 */
		clickFac : null
	},
	
	/**
	 * 초기화
	 */
	init : function(id, data, geom, features, handler) {
		var that = this;
		that.svg = d3.select(id);
		that.setData(data);
		that.handler = handler;
		that.parse(geom, features);
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		that.svg = null;
		that.data = null;
		that.resolution = null;
		that.crossData = null;
		that.handler = null;
	},
	
	/**
	 * 횡단면도 데이터 반환
	 */
	getCrossData : function() {
		var that = this;
		return that.crossData;
	},
	
	/**
	 * 횡단면도 정보 데이터 설정
	 * @param {Array.<Object>} data 횡단면도 정보 데이터
	 */
	setData : function(data) {
		var that = this;
		that.data = {
			rdls : {},
			facs : {}
		};
		for(var i in data) {
			if(data[i].dataTy == "ROAD") {
				that.data.rdls[data[i].dataId] = {
					title : data[i].dataAlias,
					color : data[i].color
				};
			}
			else {
				that.data.facs[data[i].dataId] = {
					dataId : data[i].dataId,
					title : data[i].dataAlias,
					color : data[i].color, 
					fieldLowDep : data[i].fieldLowDep,
					fieldHigDep : data[i].fieldHigDep,
					fieldAvgDep : data[i].fieldAvgDep,
					fieldDip : data[i].fieldDip,
					fieldDipType : data[i].fieldDipType,
					fieldDipHol : data[i].fieldDipHol,
					fieldDipVel : data[i].fieldDipVel,
					getDep : function(properties) {
						var fac = this;
						if(fac.fieldLowDep && fac.fieldHigDep) {
							var sum = parseFloat(properties[fac.fieldLowDep])+parseFloat(properties[fac.fieldHigDep]);
							return sum==0?0:sum/2;
						}
						else if(that.avgDep) {
							return parseInt(properties[fac.fieldAvgDep]);
						}
						else {
							return 0;
						}
					},
					getDipType : function(properties) {
						var type = null;
						var fac = this;
						if(fac.fieldDipType) {
							type = properties[fac.fieldDipType];
						}
						return type;
					},
					getDip : function(properties) {
						var fac = this;
						var dip = null;
						if(fac.fieldDipType) {
							var fieldInfo = dataObj.getDataField(fac.dataId, fac.fieldDipType);
							var dipType = domnCodeObj.getCodeByValue(fieldInfo.domnId, properties[fac.fieldDipType]).codeId;
							// 구형, 각형
							if(dipType == "FOR004" || dipType == "FOR024") {
								dip = {
									hol : parseFloat(properties[fac.fieldDipHol].replace(/,/g, "")),
									vel : parseFloat(properties[fac.fieldDipVel].replace(/,/g, ""))
								};
							}
							// 원형, 그외
							else {
								dip = {
									dip : parseInt(properties[fac.fieldDip].replace(/,/g, ""))
								};
							}
						}
						else {
							dip = {
								dip : parseInt(properties[fac.fieldDip].replace(/,/g, ""))
							};
						}
						return dip;
					}
				};
			}
		}
		
	},
	
	/**
	 * Feature 목록을 횡단면도 데이터로 변환
	 * @param {ol.geom.LineString} geom 검색할 위치 (선)
	 * @param {Array.<ol.Feature>} features 시설물 목록
	 */
	parse : function(geom, features) {
		var that = this;

		/// 횡단면도 정보
		that.crossData = {
			len : 0,	// 최대 거리
			dep : 0,	// 최대 심도
			rdls : [],	// 도로시설물
			facs : []	// 지하시설물
		};
		
		/// 최대 거리
		var gLine = spatialUtils.toJstsGeometry(geom.clone());
		that.crossData.len = gLine.getLength();
		
		/// 횡단면도
		var start = gLine.getStartPoint();
		var end = gLine.getEndPoint();
		for(var i in features) {
			var feature = features[i];
			var geom = spatialUtils.toJstsGeometry(feature.getGeometry().clone());
			var properties = feature.getProperties();
			if(that.data.rdls[feature.tableName]) {
				that.crossData.rdls.push({
					dist : {
						start : jsts.operation.distance.DistanceOp.distance(start, geom),
						end : that.crossData.len - jsts.operation.distance.DistanceOp.distance(end, geom)
					},
					color : that.data.rdls[feature.tableName].color
				});
			}
			if(that.data.facs[feature.tableName]) {
				that.crossData.facs.push({
					dist : jsts.operation.distance.DistanceOp.distance(start, geom),
					dep : that.data.facs[feature.tableName].getDep(properties),
					dipType : that.data.facs[feature.tableName].getDipType(properties),
					dip : that.data.facs[feature.tableName].getDip(properties),
					color : that.data.facs[feature.tableName].color,
					tableName : feature.tableName,
					properties : properties
				});
			}
		}
		
		// 최대 심도
		var maxDep = 0;
		var maxDip = 0; 
		for(var i in that.crossData.facs) {
			var fac = that.crossData.facs[i];
			if(fac.dep > maxDep) maxDep = fac.dep;
			if(fac.dip.dip) {
				var dip = fac.dip.dip / 1000;
				if(dip > maxDip) {
					maxDip = dip;
				}
			}
			else if(fac.dip.vel) {
				if(fac.dip.vel > maxDip) {
					maxDip = fac.dip.vel;
				}
			}
		}
		var sum = maxDep + maxDip;
		if(sum < 5) sum = 5;
		that.crossData.dep = Math.ceil(sum/2.5)*2.5;
	},
	
	/**
	 * 횡단면도 영역 변경
	 * @param {Number} width 너비
	 * @param {Number} height 높이
	 */
	resize : function(width) {
		var that = this;
		if(that.crossData) {
			var height = that.calculationHeight(width);
			that.svg.attr("width", width).attr("height", height);
			that.draw();
		}
	},
	
	calculationHeight : function(width) {
		var that = this;
		if(that.crossData) {
			that.layout.setWidth(width);
			that.setResolution();
			that.layout.setHeight(that.crossData.dep, that.resolution);
			return that.layout.getHeight();
		}
	},
	
	/**
	 * 해상도 설정
	 */
	setResolution : function() {
		var that = this;
		var width = that.layout.width - that.layout.baseOffset.left - that.layout.baseOffset.right;
		that.resolution = that.crossData.len / width;
	},
	
	/**
	 * 횡단면도 높이 반환
	 * @returns {Number} 높이
	 */
	getHeight : function() {
		var that = this;
		return that.layout.getHeight();
	},
	
	/**
	 * 횡단면도 그림
	 */
	draw : function() {
		var that = this;
		that.drawBg();
		that.drawBoard();
		that.drawXLines();
		that.drawYLines();
		that.drawLegends();
		that.drawRdls();
		that.drawFacs();
	},
	
	/**
	 * 배경 그림
	 */
	drawBg : function() {
		var that = this;
		that.drawRect(that.svg, 0, 0, that.layout.width, that.layout.height, "#ffffff", "#000000");
	},
	
	/**
	 * 그리드 그림
	 */
	drawBoard : function() {
		var that = this;
		var baseLines = that.layout.baseLines;
		var left = baseLines.left;
		var bottom = baseLines.bottom;
		var right = baseLines.right;
		var top = baseLines.top;
		
		that.drawLine(that.svg, left, top, left, bottom);
		that.drawLine(that.svg, left, bottom, right, bottom);
		that.drawLine(that.svg, right, bottom, right, top);
	},
	
	/**
	 * X 선 목록 그림
	 */
	drawXLines : function() {
		var that = this;
		
		var left = that.layout.baseLines.left-8;
		var right = that.layout.baseLines.right;
		
		var dep = that.crossData.dep;
		var step = dep/5;
		var textX = left - 3;
		for(var i=0; i <= dep; i += step) {
			var y = 0;
			if(i!=0) y = i / that.resolution;
			y += that.layout.baseLines.top;
			var textY = y + 3;
			that.drawLine(that.svg, left, y, right, y, "dot");
			that.drawText(that.svg, textX, textY, i.toFixed(2) +" m", "12px", "end");
		}
	},
	
	/**
	 * Y 선 목록 그림
	 */
	drawYLines : function() {
		var that = this;
		
		var bottom = that.layout.baseLines.bottom + 8;
		var right = that.layout.baseLines.right;
		var top = that.layout.baseLines.top;
		
		var len = that.crossData.len;
		var step = Math.ceil(len/10);
		var textY = bottom+13;
		for(var i=0; i <= len; i += step) {
			var x = 0;
			if(i!=0) x = i / that.resolution;
			x += that.layout.baseLines.left;
			that.drawLine(that.svg, x, top, x, bottom, "dot");
			that.drawText(that.svg, x, textY, i +" m", "12px", "middle");
		}
		that.drawLine(that.svg, right, top, right, bottom, "dot");
		that.drawText(that.svg, right, textY, len.toFixed(2) + " m", "12px", "middle");
	},
	
	/**
	 * 범례 영역 그림
	 */
	drawLegendBg : function() {
		var that = this;
		var x = that.layout.baseLines.right+10;
		var y = that.layout.baseLines.top;
		var width = 120;
		var height = that.layout.height - that.layout.baseOffset.top - that.layout.baseOffset.bottom;
		that.drawRect(that.svg, x, y, width, height, "#ffffff", "gray");
	},

	/**
	 * 범례 목록 그림
	 */
	drawLegends : function() {
		var that = this;
		
		var margin = 25;
		var radius = 6;
		var gap = 5;
		var fontSize = 12;
		
		var width = 0;
		for(var i in that.data.facs) {
			width += margin + (radius*2);
			width += (that.data.facs[i].title.length * fontSize);
		}
		
		var left = (that.layout.width - width) / 2;
		var cy = that.layout.baseLines.bottom + 40;
		
		var cx = left;
		for(var i in that.data.facs) {
			var text = that.data.facs[i].title;
			var color = that.data.facs[i].color;
			var group = that.createGroup();
			that.drawCircle(group, cx, cy, radius, color);
			that.drawText(group, cx+radius+gap, cy+(radius/2), text, fontSize+"px", "start");
			cx += margin + (radius*2);
			cx += (that.data.facs[i].title.length * fontSize);
		}
	},

	/**
	 * 도로 목록 그림
	 */
	drawRdls : function() {
		var that = this;
		for(var i in that.crossData.rdls) {
			var rdl  = that.crossData.rdls[i];
			var width = (rdl.dist.end - rdl.dist.start) / that.resolution;
			var height = 10;
			var x = rdl.dist.start / that.resolution + that.layout.baseLines.left;
			var y = that.layout.baseLines.top - height;
			that.drawRect(that.svg, x, y, width, height, rdl.color, rdl.color);
		}
	},
	
	/**
	 * 지하시설물 목록 그림
	 */
	drawFacs : function() {
		var that = this;
		for(var i in that.crossData.facs) {
			var fac = that.crossData.facs[i];
			var cx = fac.dist / that.resolution + that.layout.baseLines.left;
			var cy = fac.dep / that.resolution + that.layout.baseLines.top;
			
			var poly = null;
			if(fac.dipType) {
				var fieldInfo = dataObj.getDataField(fac.tableName, that.data.facs[fac.tableName].fieldDipType);
				var dipType = domnCodeObj.getCodeByValue(fieldInfo.domnId, fac.dipType).codeId;
				
				// 구형
				if(dipType == "FOR004") {
					var width = fac.dip.hol / 2 / that.resolution;
					var height = fac.dip.vel / 2 / that.resolution;
					cy += height;
					poly = that.drawEllipse(that.svg, cx, cy, width, height, fac.color);
				}
				// 각형 (사각형만 기준점이 달라 계산 방식이 다름)
				else if(dipType == "FOR024") {
					var width = fac.dip.hol / that.resolution;
					var height = fac.dip.vel / that.resolution;
					cx -= width / 2;
					poly = that.drawRect(that.svg, cx, cy, width, height, fac.color, "black");
				}
				// 원형, 미분류
				else {
					var width = fac.dip.dip / 2 / 1000 / that.resolution;
					var height = fac.dip.dip / 2 / 1000 / that.resolution;
					cy += height;
					poly = that.drawEllipse(that.svg, cx, cy, width, height, fac.color);
				}
			}
			else {
				var width = fac.dip.dip / 2 / 1000 / that.resolution;
				var height = fac.dip.dip / 2 / 1000 / that.resolution;
				cy += height;
				poly = that.drawEllipse(that.svg, cx, cy, width, height, fac.color);
			}

			if(that.handler && that.handler.clickFac) {
				poly.attr("data-index", i);
				poly.on("click", function() {
					var node = this;
					that.handler.clickFac(that.crossData.facs[$(node).attr("data-index")]);
				}, fac);
			}		
		}
	},
	
	/**
	 * 그룹 생성
	 */
	createGroup : function() {
		var that = this;
		var group = that.svg.append("g");
		return group;
	},
	
	/**
	 * 선 생성 및 그림
	 * @param {Object} node 그림을 그릴 노드
	 * @param {Number} x1 시작 X
	 * @param {Number} y1 시작 Y
	 * @param {Number} x2 종료 X
	 * @param {Number} y2 종료 Y
	 * @param {String} dash 선 스타일
	 */
	drawLine : function(node, x1, y1, x2, y2, dash) {
		var line = node.append("line").attr("x1", x1).attr("y1", y1).attr("x2", x2).attr("y2", y2).attr("stroke", "gray");
		if(dash) {
			if(dash == "dot") {
				line.attr("stroke-dasharray", 1);
			}
		}
		return line;
	},
	
	/**
	 * 사각형 생성 및 그림
	 * @param {Object} node 그림을 그릴 노드
	 * @param {Number} x 시작 X
	 * @param {Number} y 시작 Y
	 * @param {Number} width 너비
	 * @param {Number} height 높이
	 * @param {String} fill 색상
	 * @param {String} stroke 선 색상
	 */
	drawRect : function(node, x, y, width, height, fill, stroke) {
		return node.append("rect").attr("x", x).attr("y", y).attr("width", width).attr("height", height).attr("fill", fill).attr("stroke", stroke);
	},
	
	/**
	 * 원 생성 및 그림
	 * @param {Object} node 그림을 그릴 노드
	 * @param {Number} cx 시작 X
	 * @param {Number} cy 시작 Y
	 * @param {Number} r 반경
	 * @param {String} color 색상
	 */
	drawCircle : function(node, cx, cy, r, color) {
		return node.append("circle").attr("cx", cx).attr("cy", cy).attr("r", r).attr("stroke", "black").attr("fill", color);
	},
	
	drawEllipse : function(node, cx, cy, rx, ry, color) {
		return node.append("ellipse").attr("cx", cx).attr("cy", cy).attr("rx", rx).attr("ry", ry).attr("stroke", "black").attr("fill", color);
	},
	
	/**
	 * 텍스트 생성 및 그림
	 * @param {Object} node 그림을 그릴 노드
	 * @param {Number} x 시작 X
	 * @param {Number} y 시작 Y
	 * @param {String} text 텍스트
	 * @param {String} size 글씨 크기
	 * @param {String} anchor 텍스트 위치
	 */
	drawText : function(node, x, y, text, size, anchor) {
		return node.append("text").attr("x", x).attr("y", y).attr("fill", "#000000").attr("font-size", size).attr("text-anchor", anchor).attr("font-family", "Malgun Gothic").text(text);
	}
		
};