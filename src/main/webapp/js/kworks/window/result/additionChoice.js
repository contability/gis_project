/**
 * 추가 선택 객체
 * @type {Object}
 */
windowObj.resultAdditionChoice = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "추가 선택",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "ResultAdditionChoice",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 데이터 (KWS_DATA)
	 */
	data : null,
	
	/**
	 * 선택완료 시 실행될 함수
	 */
	onSubmit : null,
	
	/**
	 * 열기
	 * @param {Object} data 데이터
	 * @param {Object} options 옵션
	 */
	open : function(data, options) {
		var that = this;
		
		var url = CONTEXT_PATH + "/window/result/additionChoice/page.do";
		var windowOptions = {
			width : 360,
			height : 240,
			top : 120,
			onClose : function() {
				that.close();
			}
		};
		
		that.data = data;
		var title = that.TITLE + " [" + data.dataAlias + "]";
		var id = windowObj.createWindow(that.CLASS_NAME, title, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
			// 현재 객체에 전달되는 값 적용 (onSubmit)
			if(options) {
				$.extend(that, options);
			}
		});
		that.selector = "#" + id;
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		that.data = null;
		windowObj.removeWindow(that.selector);
		selectObj.clear(that.CLASS_NAME);
		highlightObj.removeFeatures(that.CLASS_NAME);
		$("#a_map_tool_bass img").trigger("click");
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		that.clear();
		that.selector = null;
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 버퍼 거리
		$(".txt_spatial_buffer_distance", that.selector).numberspinner({
			min : 0,
			max : 300,
			increment : 10
		});
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 점 그리기
		$(".a_spatial_point", that.selector).click(function() {
			selectObj.active(that.CLASS_NAME, "Point", null, null, true);
			event.preventDefault();
		});
		
		// 선 그리기
		$(".a_spatial_linestring", that.selector).click(function(event) {
			selectObj.active(that.CLASS_NAME, "LineString", null, null, true);
			event.preventDefault();
		});

		// 면 그리기
		$(".a_spatial_rect", that.selector).click(function(event) {
			selectObj.active(that.CLASS_NAME, "Rect", null, null, true);
			event.preventDefault();
		});
		
		// 다각형 그리기
		$(".a_spatial_polygon", that.selector).click(function(event) {
			selectObj.active(that.CLASS_NAME, "Polygon", null, null, true);
			event.preventDefault();
		});
		
		// 원 그리기
		$(".a_spatial_circle", that.selector).click(function(event) {
			selectObj.active(that.CLASS_NAME, "Circle", null, null, true);
			event.preventDefault();
		});
		
		// 검색
		$(".a_spatial_search", that.selector).click(function() {
			that.search();
			return false;
		});
		
		// 닫기
		$(".a_spatial_close", that.selector).click(function() {
			that.close();
			return false;
		});
		
	},
	
	/**
	 * 검색
	 */
	search : function() {
		var that = this;
		
		var feature = null;
		var features = selectObj.getFeatures(that.CLASS_NAME);
		if(features.length == 1) {
			feature = features[0];
		}
		else {
			$.messager.alert(that.TITLE, "지도에 검색할 영역을 그려주십시오.");
		}
		
		// 공간 필터 적용
		var spatialType = $("input[name=rad_result_addition_choise_spatial_type]:checked").val();
		
		// 버퍼 적용
		var buffer = $(".txt_spatial_buffer_distance", that.selector).numberspinner("getValue");
		var geometry = feature.getGeometry();
		var bufferGeometry = spatialUtils.buffer(geometry, buffer);
		highlightObj.showYellowFeatures(that.CLASS_NAME, [new ol.Feature(bufferGeometry)]);
		
		// 좌표 변환
		var mapProjection = mapObj.getMap().getView().getProjection();
		var sysProjection = ol.proj.get(MAP.PROJECTION);
		var transformGeometry = spatialUtils.transform(bufferGeometry, mapProjection, sysProjection);
		var format = new ol.format.WKT();
		var wkt = format.writeFeature(new ol.Feature(transformGeometry));
		
		// 필터
		var filter = {
			filterType : "SPATIAL",
			spatialType : spatialType,
			wkt : wkt
		};
		// 공간 검색
		spatialSearchUtils.listAllSummary(that.TITLE, that.data.dataId, filter, function(data) {
			if(data) {
				if(that.onSubmit) {
					that.onSubmit(data);
				}
				mapToolObj.deactive();
			}
			that.close();
		});
	}
		
};