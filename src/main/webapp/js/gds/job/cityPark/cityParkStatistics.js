/**
 * 도시공원 - 통계
 */
cityParkObj.statisticsObj = {
		
	/**
	 * 선택자
	 */
	selector : ".div_statistics",
	
	/**
	 * 부모 객체 (도시 공원)
	 */
	parent : null,
	
	/**
	 * 제목
	 */
	TITLE : "민원 관리",
	
	/**
	 * 초기화
	 * @param parent
	 */
	init : function(parent) {
		var that = this;
		that.selector = parent.selector + " " + that.selector;
		that.bindEvents();
	},
	
	/**
	 * 이벤트 등록
	 * @param parent
	 */
	bindEvents : function(parent) {
		var that = this;
		
		// 법정구역별 도시공원 현황
		$(".a_statistics_bjd", that.selector).click(function() {
			var url = CONTEXT_PATH + "/clipreport/CITY_PARK/CITYPARK_STATS_BJD/stats.do";			
			popupUtils.open(url, "city_park_statistics", { width : 900 , height : 800, left : 300, top:150 });
		});
		
		// 공원별 시설물 현황
		$(".a_statistics_facilities", that.selector).click(function() {
			var url = CONTEXT_PATH + "/clipreport/CITY_PARK/CITYPARK_STATS_FACILITIES/stats.do";			
			popupUtils.open(url, "city_park_statistics", { width : 900 , height : 800, left : 300, top:150 });
		});
		
		// 미관광장 현황
		$(".a_statistics_aestheticPlaza", that.selector).click(function() {
			var url = CONTEXT_PATH + "/clipreport/CITY_PARK/CITYPARK_STATS_AESTHETICPLAZA/stats.do";			
			popupUtils.open(url, "city_park_statistics", { width : 900 , height : 800, left : 300, top:150 });
		});
		
		// 소공원 현황
		$(".a_statistics_smallPark", that.selector).click(function() {
			var url = CONTEXT_PATH + "/clipreport/CITY_PARK/CITYPARK_STATS_SMALLPARK/stats.do";			
			popupUtils.open(url, "city_park_statistics", { width : 900 , height : 800, left : 300, top:150 });
		});
		
		// 어린이 공원 현황
		$(".a_statistics_childrensPark", that.selector).click(function() {
			var url = CONTEXT_PATH + "/clipreport/CITY_PARK/CITYPARK_STATS_CHILDRENSPARK/stats.do";			
			popupUtils.open(url, "city_park_statistics", { width : 900 , height : 800, left : 300, top:150 });
		});
		
	}
		
};