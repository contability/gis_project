$(function() {
	logObj.init();
});

var logObj = {
	
	// 객체초기화 function
	init : function() {
		logObj.initTag();
		logObj.bindEvent();
	}, 
	
	// 페이지 내 태그 초기화
	initTag : function() {
		// 체크박스 디폴트로 체크되있게 설정
		$("input[name='allSearch']").prop("checked", true);
		
		// datepicker 설정
		$(".datepicker").datepicker( {
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
		
		// 체크박스가 있는 경우에만 datepicker를 disabled 해놓게 설정
		if($("input[name='allSearch']").val() != undefined) {
			$(".datepicker").prop("disabled", true);
		}
	},
	
	// 이벤트 바인딩
	bindEvent : function() {
		
		// 전체검색 체크박스 체크이벤트
		if($("input[name='allSearch']").val() != undefined) {
			checkbox = $("input[name='allSearch']");
			
			$(checkbox).change(function() {
				if(checkbox.prop("checked")) {
					$(".datepicker").prop("disabled", true);
				} else {
					$(".datepicker").prop("disabled", false);
				}
			});
		}
	}
};