var dataFtrCdeObj = {
		
	TITLE : "데이터 - 지형지물부호",
	
	data : {},
	
	load : function(dataId) {
		var url = CONTEXT_PATH + "/rest/dataFtrCde/listAll.do?dataId=" + dataId;
		var promise = $.ajax({
			url : url,
			async : false
		});
		return promise;
	},
	
	getData : function(dataId) {
		var that = this;
		if(!that.data[dataId]) {
			that.load(dataId).done(function(response) {
				if(response && response.rows) {
					that.data[dataId] = response.rows;
				}
				else {
					$.messager.alert(that.TITLE, "[" + dataId + "] : 지형지물 부호가 없습니다.");
				}
			}).fail(function() {
				$.messager.alert(that.TITLE, "[" + dataId + "] : 지형지물 부호를 불러오는데 실패 했습니다.");
			});
		}
		return that.data[dataId];
	}
		
};