var baseMapAuthorObj = {
	
	data : {},
	
	TITLE : "사용자 데이터 권한",
	
	init : function() {
		var that = this;
		that.load();
	},
	
	load : function() {
		var that = this;
		var url = CONTEXT_PATH + "/rest/baseMapAuthor/listAll.do";
		$.get(url).done(function(response) {
			if(response && response.rows) {
				var rows = response.rows;
				for(var i=0, len=rows.length; i < len; i++) {
					var row = rows[i];
					that.data[row.mapNo] = row;
				}
			}
			else {
				$.messager.alert(that.TITLE, "사용자 기본 지도 권한을 가져오는 데 실패 했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "사용자 기본 지도 권한을 가져오는 데 실패 했습니다.");
		});
	},
	
	getBaseMapAuthor : function(mapNo) {
		var that = this;
		return that.data[mapNo];
	}
	
};