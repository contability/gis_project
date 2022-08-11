/**
 * 측정
 * @type {Object}
 */
windowObj.measureObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "측정",
		
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "Scale",
	
	/**
	 * 열기
	 * @param options 옵션
	 */
	open : function() {
		var that = this;
		var url = CONTEXT_PATH + "/window/measure/page.do";
		var windowOptions = {
			width : 250,
			height : 90,
			top : 120,
			right : 50,
			onClose : function() {
				that.close();
			}
		};
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
		});
		that.selector = "#" + id;
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 축척
		$(".txt_scale", that.selector).numberspinner({
			min : 0,
			max : 1000000,
			increment : 1000
		});
		$(".txt_scale", that.selector).numberspinner("setValue", mapObj.getMap().getScale());
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 면적 측정
		$(".a_measure_area", that.selector).click(function(event) {
			mapObj.getMap().activeInteractions("drag", "area");
			event.preventDefault();
		});
		
		// 거리 측정
		$(".a_measure_distance", that.selector).click(function(event) {
			mapObj.getMap().activeInteractions("drag", "distance");
			event.preventDefault();
		});
		
		// 반경 측정
		$(".a_measure_radius", that.selector).click(function(event) {
			mapObj.getMap().activeInteractions("drag", "radius");
			event.preventDefault();
		});
		
		// 좌표 측정
		$(".a_measure_coordinate", that.selector).click(function(event) {
			mapObj.getMap().activeInteractions("drag", "coordinate");
			event.preventDefault();
		});
		
		// 모두 지우기
		$(".a_measure_clear", that.selector).click(function(event) {
			kworks.interaction.Measure.clear(mapObj.getMap());
		});
		
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		windowObj.removeWindow(that.selector);
		that.selector = null;
		$("#a_map_tool_bass img").trigger("click");
//		$("#a_map_tool_measurement img").trigger("click");
	}
	
};