$(function() {
	conectLogObj.init();
});

var conectLogObj = {
	
	/**
	 * 선택자
	 * type {String}
	 */
	selector : "#div_conectlog",
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		
		that.initTag();
		that.bindEvent();
	},
	
	/**
	 * 태그 초기화
	 */
	initTag : function() {
		
		var that = this;
		
		// 달력 옵션 초기화
		$(".datepicker", that.selector).datepicker( {
			dateFormat : "yy-mm-dd",
			showButtonPanel : true,
			closeText : "닫기",
			dayNamesMin : ['일', '월', '화', '수', '목', '금', '토'],
			monthNames : ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
			monthNamesShort : ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
			changeMonth : true,
			changeYear : true,
			minDate : new Date(2000, 1-1, 1),
			maxDate : new Date(2100, 1-1, 1-1)
		});
	},
	
	/**
	 * 이벤트등록
	 */
	bindEvent : function() {
		var that = this;
		
		// 검색 이벤트
		$("#search", that.selector).on("click", function() {
			$("#pageIndex").val(1);
			$("form", that.selector).submit();
		});
		
		// 셀랙트박스 변경 이벤트
		$(".selectBox", that.selector).on("change", function(){
			var url = restUtils.getResUrl() + $(".selectBox").val() + ".do";
			
			location.href = url;
		});
	},
	
	/**
	 * 페이지 이동
	 * @param pageIndex
	 */
	search : function(pageIndex) {
		var that = this;
		if(pageIndex) {
			$("#pageIndex").val(pageIndex);
		}
		$("form", that.selector).submit();
	}
};