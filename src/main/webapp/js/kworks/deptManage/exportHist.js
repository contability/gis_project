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
