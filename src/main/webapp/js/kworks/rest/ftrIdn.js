var ftrIdnObj = {
		
	TITLE : "관리번호",
		
	getData : function(dataId) {
		var that = this;
		var ftrIdn = null;
		var url = CONTEXT_PATH + "/cmmn/idgen/" + dataId + "/next.do";
		$.ajax({
			url : url,
			async : false
		}).done(function(response) {
			if (response && response.id) {
				ftrIdn = response.id;
			} else {
				$.messager.alert(that.TITLE, "관리번호를 불러오는 데 실패 했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "관리번호를 불러오는 데 실패 했습니다.");
		});
		return parseInt(ftrIdn);
	}
		
};