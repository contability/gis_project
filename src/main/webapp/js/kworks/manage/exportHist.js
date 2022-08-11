$(function() {
	exportHistObj.init();
});

/**
 * 내보내기 이력관리 객체
 * @type {Object}
 */
var exportHistObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_export_hist_list",
	
	init : function() {
		var that = this;
		
		that.bindEvents();
		that.initTag();
	},
	
	bindEvents : function() {
		var that = this;
		
		$("#a_search_exportHist", that.selector).on("click", function() {
			$("#pageIndex").val(1);
			$("form", that.selector).submit();
		});
	},
	
	initTag : function () {
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

	print : function() {
		var url = CONTEXT_PATH + "/clipreport/exportHistory.do?";
		var name = "exportHist";
		
		popupUtils.open(url, name, {
			width : 1150,
			height : 920,
			left : 300,
			top : 150
		});
	}
	
};
