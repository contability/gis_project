$(function() {
	editHistoryObj.init();
});

/**
 * 사용자 객체
 * @type {Object}
 */
var editHistoryObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_edit_history_list",
	
	/**
	 * 초기화 
	 */
	init : function() {
		var that = this;
		
		that.bindEvents();
		that.changeSearchCondition();
		
		// 기본값 : 1개월 선택
		//$(".date1month").trigger("click");
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
		
		// 검색 조건 변경
		$("#searchCondition", that.selector).change(function() {
			that.changeSearchCondition();
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
		$(".a_excelDownload_editHistory", that.selector).click(function() {
			that.excelDownload(1);
		});
	},
	
	/**
	 * 검색 조건 변경 시 이벤트
	 */
	changeSearchCondition :function() {
		var that = this;
		var searchCondition = $("#searchCondition", that.selector).val();
		
		// searchCondition == 1 : 서비스
		// searchCondition == 2 : 편집종류
		if(searchCondition == 1) {
			$("#searchKeyword", that.selector).hide();
			$("#editType", that.selector).hide();
			$("#sysId", that.selector).show();
		}
		else if(searchCondition == 2) {
			$("#searchKeyword", that.selector).hide();
			$("#sysId", that.selector).hide();
			$("#editType", that.selector).show();
		}
		else {
			$("#searchKeyword", that.selector).show();
			$("#sysId", that.selector).hide();
			$("#editType", that.selector).hide();
		}
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
	}
	
};
