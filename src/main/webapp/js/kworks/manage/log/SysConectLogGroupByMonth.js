$(function() {
	GroupByMonthObj.init();
});

/*
 * 부서별 접속 통계 객체
 */
var GroupByMonthObj = {
	
	init : function() {
		GroupByMonthObj.bindEvent();
	},
	
	/*
	 * 이벤트 바인딩
	 */
	bindEvent : function() {
		// 검색버튼 클릭이벤트
		$("a[id='search']").on("click", function() {
			$("form").submit();
//			GroupByMonthObj.setUrl();
//			GroupByMonthObj.setParam();
//			GroupByMonthObj.printLog();
		});
		
		// datepicker only month
		$(".monthpicker").datepicker( {
			dateFormat : 'yy-mm',
			showButtonPanel : true,
			closeText : "닫기", 
			dayNamesMin : ['일', '월', '화', '수', '목', '금', '토'], 
			monthNames : ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'], 
			monthNamesShort : ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'], 
			changeMonth : true,
			changeYear : true,
			minDate : new Date(2000, 1-1, 1), 
			maxDate : new Date(2100, 1-1, 1-1),
			onClose : function() {
				var year = $(".ui-datepicker-year").val();
				var month = $(".ui-datepicker-month").val();
				var day = 1;
				$(this).datepicker("setDate", new Date(year, month, day));
			}
		});
		
		$(".monthpicker").focus(function() {
			$(".ui-datepicker-calendar").hide();
		});
	},
	
	/*
	 * 페이지 이동
	 */
	goPage : function(pgNo){
		$("#pageIndex").val(pgNo);
		$("form").submit();
	}
};