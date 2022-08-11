var policyRegstrDownHistoryObj = {
	
	/**
	 * 선택자
	 * type {String}
	 */
	selector : "#div_policyRegstrDownHistory_list",
	
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