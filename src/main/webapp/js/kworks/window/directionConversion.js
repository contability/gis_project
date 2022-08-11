windowObj.directionConversionObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "하수 방향성 전환",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "DirectionConversion",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 레이어명
	 * @type {String}
	 */
	layerName : null,
	
	/**
	 * 레이어 별칭
	 * @type {String}
	 */
	layerAlias : null,
	
	/**
	 * 열기
	 */
	open : function(layerName, layerAlias) {
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		that.layerName = layerName;
		that.layerAlias = layerAlias;
		
		var url = CONTEXT_PATH + "/window/directionConversion/page.do";
		var windowOptions = {
			width : 300,
			height : 125,
			top : 100,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.bindEvents();
		});
		that.selector = "#" + id;
	},
	
	close : function() {
		var that = this;
		that.clear();
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.layerName = null;
		that.layerAlias = null;
		return false;
	},
	
	/**
	 * 화면 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		// 하수 관로 선택
		$(".a_reverse").click(function() {
			selectObj.active(that.CLASS_NAME, "Point", "drawend", function(evt) {
				that.search(evt.feature);
			}, true);
		});
		
	},
	
	/**
	 * 하수관로 검색
	 * 
	 * @param {ol.Feature} feature 선택한 지점 객체 정보
	 */
	search : function(feature) {
		var that = this;
		var buffer = mapObj.getMap().getView().getResolution() * 10;
		if(buffer < 1) {
			buffer = 1;
		}
		
		var geometry = feature.getGeometry();
		var bufferGeometry = spatialUtils.buffer(geometry, buffer);
		
		// 좌표 변환
		var mapProjection = mapObj.getMap().getView().getProjection();
		var sysProjection = ol.proj.get(MAP.PROJECTION);
		var transformGeometry = spatialUtils.transform(bufferGeometry, mapProjection, sysProjection);
		var format = new ol.format.WKT();
		var wkt = format.writeFeature(new ol.Feature(transformGeometry));
		
		// 필터
		var filter = {
			filterType : "SPATIAL",
			spatialType : "INTERSECTS",
			wkt : wkt
		};
		// 공간 검색
		spatialSearchUtils.list(that.TITLE, that.layerName, filter, function(rows) {
			if(rows && rows.length > 0) {
				if (rows.length > 1) {
					//1개 선택
					windowObj.spatialSelectObj.open(that.layerAlias, rows, {
						onSubmit : function(selectedRow) {
							if (selectedRow) {
								that.setSelectedFeatuer(selectedRow);
							} else {
								$.messager.alert("하수방향성전환", "선택된 하수관거가 없습니다.");
							}
						}
					});
				} else {
					that.setSelectedFeatuer(rows[0]);
				}
			}
			else {
				$.messager.alert("하수방향성전환", "선택 지점에 하수관로가 없습니다. 하수관거를 선택하여 주십시오.");
			}
		}, { isNoMessage : true });
	},
	
	setSelectedFeatuer : function(row) {
		var that = this;
		
		if (row) {
			var format = new ol.format.WKT();
			var feature = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);
			
			var pkColumn = dataObj.getPkColumn(that.layerName);
			var fid = row[pkColumn];
			feature.setId(fid);
			
			var fieldConvertionObjName = camelcaseUtils.underbarToCamelcase(that.layerName);
			var fieldConvertionObj = FIELD_CONVERSION[fieldConvertionObjName];
			if (fieldConvertionObj) {
				debugger;
				var fcArr = fieldConvertionObj.swapField;
				fcArr.forEach(function(fieldPairObj) {
					var field1Value = String(row[camelcaseUtils.underbarToCamelcase(fieldPairObj.field1)]).trim();
					var field2Value = String(row[camelcaseUtils.underbarToCamelcase(fieldPairObj.field2)]).trim();
					
					//방향성전환에 따른 swap 필드 세팅
					feature.set(fieldPairObj.field1, field1Value);
					feature.set(fieldPairObj.field2, field2Value);
				});
				
				//평균구배 재개산을 위한 연장값 추가 세팅
				feature.set(fieldConvertionObj.lenFieldNm, String(row[camelcaseUtils.underbarToCamelcase(fieldConvertionObj.lenFieldNm)]).trim());
			}
			
			that.reverse(feature);
		}
	},
	
	/**
	 * 하수관로 검색
	 * 
	 * @param {ol.Feature} 선택된 하수관로 객체
	 */
	reverse : function(feature) {
		var that = this;
		
		highlightObj.showRedFeatures(that.CLASS_NAME, [feature], false, {
			projection : ol.proj.get(MAP.PROJECTION)
		});
		$.messager.confirm("하수방향성전환", "선택하신 하수관거의 방향성을 전환하시겠습니까?", function(isTrue) {
			if(isTrue) {
				var geometry = feature.getGeometry();
				var coordinates = geometry.getCoordinates();
				var reverseCoords = [];
				if(geometry instanceof ol.geom.LineString) {
					reverseCoords = coordinates.reverse();
				}
				else {
					for(var i in coordinates) {
						reverseCoords.push(coordinates[i].reverse());
					}
				}
				geometry.setCoordinates(reverseCoords);
				feature.setGeometry(geometry);
				
				var fieldConvertionObjName = camelcaseUtils.underbarToCamelcase(that.layerName);
				var fieldConvertionObj = FIELD_CONVERSION[fieldConvertionObjName];
				if (fieldConvertionObj) {
					var fcArr = fieldConvertionObj.swapField;
					fcArr.forEach(function(fieldPairObj) {
						var tempValue = feature.get(fieldPairObj.field1);
						feature.set(fieldPairObj.field1, feature.get(fieldPairObj.field2));
						feature.set(fieldPairObj.field2, tempValue);
					});
					
					//평군구배 재 개산
					//시점관저고
					var sbkAlt = parseFloat(feature.get(fieldConvertionObj.sbkFieldNm));
					//종점괁저고
					var sblAlt = parseFloat(feature.get(fieldConvertionObj.sblFieldNm));
					//연장
					var pipLen = parseFloat(feature.get(fieldConvertionObj.lenFieldNm));
					//평균구배
					var pipSlp = (sbkAlt - sblAlt) / pipLen * 100;
					//편균구배 반올림(소수둘째자리)
					pipSlp = Math.round(pipSlp * 100) /100;
					feature.set(fieldConvertionObj.slpFeildNm, pipSlp);
				} else {
					feature.values_ = {};
				}
							
				var url = CONTEXT_PATH + "/proxy/transaction.do?sysId="+SYS_ID+"&editType=ATTRIBUTE";
				var params = {
					featureType : that.layerName,
					featureNS : MAP.PREFIX,
					featurePrefix : MAP.PREFIX,
					srsName : MAP.PROJECTION,
					geometryName : MAP.GEOMETRY_ATTR_NAME.toUpperCase(),
					serverType : MAP.SERVER_TYPE
				};
				
				kworks.service.WFS.transaction(url, params, null, [feature], null, function(response) {
					if(response.updated == 1) {
						$.messager.alert("하수방향성전환", "하수 방향성이 전환되었습니다.");
						mapObj.reloadWMS();
						that.close();
					}
					else {
						$.messager.alert("하수방향성전환", "하수 방향성 전환에 실패하였습니다. 관리자에게 문의하십시오.");
						that.clear();
					}
				});
			}
			else {
				that.clear();
			}
		});
	},
	
	/**
	 * 기능 정리
	 */
	clear : function() {
		var that = this;
		selectObj.clear(that.CLASS_NAME);
		highlightObj.removeFeatures(that.CLASS_NAME);
		mainObj.defaultActive();
	}
		
};