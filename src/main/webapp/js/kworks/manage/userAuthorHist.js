$(function() {
	userAuthorHistObj.init();
});

/**
 * 사용자 권한부여 이력관리 객체
 * @type {Object}
 */
var userAuthorHistObj = {
		
	/**
	 * selctor
	 * @type {String}
	 */
	selector : "#div_user_author_hist_list",
	
	/**
	 * 초기화 
	 */
	init : function() {
		var that = this;
		that.bindEvents();
	},	
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		// 검색
		$(".a_search", that.selector).click(function() {
			that.search(1);
			return false;
		});
			
		// 달력
		$(".datepicker", that.selector).datepicker({
			dateFormat:"yy-mm-dd"
		});
		
		// 달력 - 1개월
		$(".date1month", that.selector).click(function() {
			$("input[name=searchStartDt]", that.selector).val(that.getMonth(-1));
			$("input[name=searchEndDt]", that.selector).val(that.getToday());
		});
		// 달력 - 3개월
		$(".date3month", that.selector).click(function() {
			$("input[name=searchStartDt]", that.selector).val(that.getMonth(-3));
			$("input[name=searchEndDt]", that.selector).val(that.getToday());
		});
		// 달력 - 6개월
		$(".date6month", that.selector).click(function() {
			$("input[name=searchStartDt]", that.selector).val(that.getMonth(-6));
			$("input[name=searchEndDt]", that.selector).val(that.getToday());
		});
		
		// excel download
		$(".a_excelDownload_user_author_hist", that.selector).click(function() {
			that.excelDownload(1);
		});
		
	},
	
	/**
	 * 금일 날짜 값을 문자열로 변환
	 */
	getToday : function(){
		var today = new Date();
		var year = today.getFullYear();
		var month = today.getMonth() + 1;
		var day = today.getDate();
		var todayStr = year + "-" + month + "-" + day;
		
		return todayStr;
	},
	
	/**
	 * 월값을 문자열로 변환
	 */
	getMonth : function(mon){
		var today = new Date();
		
		today.setMonth(today.getMonth() + mon);
		today.setDate(today.getDate() + 1);
		
		var year = today.getFullYear();
		var month = today.getMonth() + 1;
		var day = today.getDate();
		var todayStr = year + "-" + month + "-" + day;
		
		return todayStr;
	},
	
	/**
	 * 검색
	 * @param {Number} pageIndex 페이지 인덱스
	 */
	search : function(pageIndex) {
		var that = this;
		if(pageIndex) {
			$("#pageIndex").val(pageIndex);
		}
		$("form", that.selector).submit();
	},
	
	/**
	 * 엑셀다운로드
	 * @param {Number} pageIndex 페이지 인덱스
	 */
	excelDownload : function(pageIndex) {
		var that = this;
		
		$("form", that.selector).attr("action", restUtils.getResUrl() + "excel.do");
		$("form", that.selector).submit();

		// 기존 검색 url로 변경
		$("form", that.selector).attr("action", restUtils.getResUrl() + "list.do");
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		$("input[name=searchKeyword]").val("");
	}
	
};
