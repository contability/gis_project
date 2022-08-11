/**
 * 지형단먄도 객체
 */
var topographicSectionViewObj = {
		
	/**
	 * d3 로 선택한 객체 
	 * @type {Object}
	 */
	svg : null,

	/**
	 * X축 해상도 (1 픽셀 당 거리)
	 * @type {Object}
	 */
	resolutionX : null,

	/**
	 * Y축 해상도 (1 픽셀 당 거리)
	 * @type {Object}
	 */
	resolutionY : null,
	
	/**
	 * 지형단면도 데이터
	 * @type {Object}
	 */
	profileData : null,
	
	/**
	 * 분석 라인
	 */
	geometry : null,
	
	/**
	 * 이벤트 핸들러
	 * @type {Object}
	 */
	hander : {
		clickEvent : null
	},
	
	
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
		 * 종단면도 영역에서 실제 그리드가 그려질 영역 까지의 오프셋
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
		 * 종단면도 영역에서 실제 그리드가 그려질 영역의 기준 픽셀
		 * @type {Object}
		 */
		baseLines : null,
		
		/**
		 * 종단면도 영역 너비 설정
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
		 * 종단면도 영역 높이 설정
		 * @param {Number} height 최대 높이
		 * @param {Number} resolution 해상도
		 */
		setHeight : function(height) {
			var that = this;
			
			that.height = height;
			that.baseLines.bottom = that.height - that.baseOffset.bottom;
			that.baseLines.top = that.baseOffset.top;
		},
		
		getHeight : function() {
			var that = this;
			return that.height;
		}
	},
	
	/**
	 * 초기화
	 */
	init : function(id, data, geometry, interval, handler) {
		var that = this;
		
		that.svg = d3.select(id);
		that.setData(data, geometry, interval);
		that.handler = handler;
	},

	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		
		that.svg = null;
		that.resolutionX = null;
		that.resolutionY = null;
		that.profileData = null;
		that.geometry = null;
		that.handler = null;
	},
	
	/**
	 * 지형단면도 데이터 반환
	 */
	getProfileData : function() {
		var that = this;
		return that.profileData;
	},
	
	/**
	 * 지형단면도 정보 데이터 설정
	 * @param {Array.<Object>} data 지형단면도 정보 데이터
	 */
	setData : function(data, geometry, interval) {
		var that = this;
		
		that.geometry = geometry;
		
		// 지형단면도 정보
		that.profileData = {
			len : 0,		// 분석 거리
			min : data.min,	// 최소 높이
			max : data.max,	// 최대 높이
			height : 0,		// 높이
			interval : interval,	// 추출간격
			datas : []
		};
		
		// 최대 거리
		var gLine = spatialUtils.toJstsGeometry(geometry.clone());
		that.profileData.len = gLine.getLength();
		
		// 최대 높이
		var range = data.max - data.min;
		that.profileData.height = (range / 2.5) * 2.5;
		
		// 
		var count = data.datas.length;
		for (var i = 0; i < count; i++) {
			var pos = data.datas[i];
			that.profileData.datas[i] = {
				x : pos.x,
				y : pos.y,
				elevation : pos.elevation,
				obj : null
			};
		}
	},
	
	/**
	 * 지형단면도 영역 변경
	 * @param {Number} width 너비
	 * @param {Number} height 높이
	 */
	resize : function(width, height) {
		var that = this;
		
		if(that.profileData) {
			that.calculation(width, height);
			that.svg.attr("width", width).attr("height", height);
			that.draw();
		}
	},
	
	calculation : function(width, height) {
		var that = this;
		
		if(that.profileData) {
			that.layout.setWidth(width);
			that.layout.setHeight(height);
			that.setResolution();
		}
	},
	
	/**
	 * 해상도 설정
	 */
	setResolution : function() {
		var that = this;
		var width = that.layout.width - that.layout.baseOffset.left - that.layout.baseOffset.right;
		that.resolutionX = that.profileData.len / width;
		
		var height = that.layout.height - that.layout.baseOffset.bottom - that.layout.baseOffset.top;
		that.resolutionY = that.profileData.height / height;
	},
	
	/**
	 * 지형단면도 높이 반환
	 * @returns {Number} 높이
	 */
	getHeight : function() {
		var that = this;
		return that.layout.getHeight();
	},
	
	/**
	 * 지형단면도 그림
	 */
	draw : function() {
		var that = this;
		that.drawBg();
		that.drawBoard();
		that.drawXLines();
		that.drawYLines();
		that.drawProfile();
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
		
		var max = that.profileData.max;
		var height = that.profileData.height;
		var step = height / 5;
		var textX = left - 3;
		
		for(var i=0; i <= height; i += step) {
			var y = 0;
			if(i!=0) {
				y = i / that.resolutionY;
			}
			
			y += that.layout.baseLines.top;
			var textY = y + 3;
			
			var label = max - i;
			that.drawLine(that.svg, left, y, right, y, "dot");
			that.drawText(that.svg, textX, textY, label.toFixed(2) +" m", "12px", "end");
		}
	},
	
	/**
	 * Y 선 목록 그림
	 */
	drawYLines : function() {
		var that = this;
		
		var bottom = that.layout.baseLines.bottom + 8;
		var top = that.layout.baseLines.top;
		
		var len = parseFloat(that.profileData.len);
		var step = len / 10; 
		var textY = bottom + 13;
		for(var i=0; i <= len; i += step) {
			var x = 0;
			if(i!=0) {
				x = i / that.resolutionX;
			}
			
			x += that.layout.baseLines.left;
			that.drawLine(that.svg, x, top, x, bottom, "dot");
			that.drawText(that.svg, x, textY, i.toFixed(2) + " m", "12px", "middle");
		}
	},
	
	drawProfile : function() {
		var that = this;
		
		var len = parseFloat(that.profileData.len);
		var interval = parseFloat(that.profileData.interval);
		var max = that.profileData.max;
		var height = that.profileData.height;
		
		var count = that.profileData.datas.length;
		for (var i = 1; i < count; i++) {
			var preData = that.profileData.datas[i-1];
			var x1 = 0;
			if((i-1) != 0) {
				x1 = ((i-1) * interval);
				if (x1 > len)
					x1 = len;
				
				x1 = x1 / that.resolutionX;
			}
			x1 += that.layout.baseLines.left;
			
			var y1 = (max - preData.elevation);
			if (y1 > height)
				y1 = height;
			if (y1 < 0)
				y1 = 0;
			y1 = y1 / that.resolutionY;
			y1 += that.layout.baseLines.top;
			
			var curData = that.profileData.datas[i];
			var x2 = 0;
			if(i != 0) {
				x2 = (i * interval);
				if (x2 > len)
					x2 = len;
				
				x2 = x2 / that.resolutionX;
			}
			x2 += that.layout.baseLines.left;
			
			var y2 = (max - curData.elevation);
			if (y2 > height)
				y2 = height;
			if (y2 < 0)
				y2 = 0;
			y2 = y2 / that.resolutionY;
			y2 += that.layout.baseLines.top;
			
			that.drawLineByColor(that.svg, x1, y1, x2, y2, "#000000");
		}
		
		for(var idx in that.profileData.datas) {
			var data = that.profileData.datas[idx];
			
			var cx = 0;
			if(idx != 0) {
				cx = (idx * interval);
				if (cx > len)
					cx = len;
				
				cx = cx / that.resolutionX;
			}
			cx += that.layout.baseLines.left;
			
			var cy = (max - data.elevation);
			if (cy > height)
				cy = height;
			if (cy < 0)
				cy = 0;
			cy = cy / that.resolutionY;
			cy += that.layout.baseLines.top;
			
			var radius = height * 0.02;
			var rx = radius / that.resolutionY;
			var ry = radius / that.resolutionY;
			
			var pos = that.drawEllipse(that.svg, cx, cy, rx, ry, "#ff0000");
			if(that.handler && that.handler.clickEvent) {
				pos.attr("data-index", idx);
				pos.on("click", function() {
					var node = this;
					that.handler.clickEvent(that.profileData.datas[$(node).attr("data-index")]);
				}, data);
			}			
		}
		
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
	 * 선 생성 및 그림
	 * @param {Object} node 그림을 그릴 노드
	 * @param {Number} x1 시작 X
	 * @param {Number} y1 시작 Y
	 * @param {Number} x2 종료 X
	 * @param {Number} y2 종료 Y
	 * @param {String} color 색상
	 */
	drawLineByColor : function(node, x1, y1, x2, y2, color) {
		var line = node.append("line").attr("x1", x1).attr("y1", y1).attr("x2", x2).attr("y2", y2).attr("stroke", color);
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
	 * 타원 생성 및 그림
	 * @param {Object} node 그림을 그릴 노드
	 * @param {Number} cx 중심 X
	 * @param {Number} cy 중심 Y
	 * @param {Number} rx X축 반경
	 * @param {Number} ry Y축 반경
	 * @param {String} color 색상 
	 * @returns
	 */
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
