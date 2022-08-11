/*
 * 이승재, 2021.09.16
 * 데이터권한 및 권한그룹 정보를 제공하도록 수정
 * 파일명 변경(dataAuthor -> userAuthor), 
 * userAuthorObj추가, dataAuthorObj수정, userAuthorGroupObj 통합(userAuthorGroup.js 삭제)
 */
var userAuthorObj = {
	TITLE : "사용자 권한",
	
	/*
	* 데이터 권한
	*/
	userDataAuthors : {},
	
	/**
	 * 권한그룹
	 */
	userAuthorGroups : {},
	
	load : function() {
		var that = this;
		
		var url = CONTEXT_PATH + "/rest/userAuthor/listAll.do";
		$.ajax({
			type : "GET",
			url : url,
			async : false,
			success : function(response) {
				if(response) {
					//권한그룹
					if (response.userAuthorGroups) {
						var userAuthorGroups = response.userAuthorGroups;
						for (var i = 0; i < userAuthorGroups.length; i++) {
							var userAuthorGroup = userAuthorGroups[i];
							that.userAuthorGroups[userAuthorGroup.authorGroupNm] = userAuthorGroup;
						}
					}
					
					//데이터 권한
					if (response.userDataAuthors) {
						var userDataAuthors = response.userDataAuthors;
						for(var i=0, len=userDataAuthors.length; i < len; i++) {
							var userDataAuthor = userDataAuthors[i];
							that.userDataAuthors[userDataAuthor.dataId] = userDataAuthor;
						}
					}
				}
				else {
					$.messager.alert(that.TITLE, "사용자 권한을 가져오는 데 실패 했습니다.");
				}
			},
			fail : function() {
				$.messager.alert(that.TITLE, "사용자 권한을 가져오는 데 실패 했습니다.");
			}
		});
	},
	
	init : function() {
		var that = this;
		that.load();
		dataAuthorObj.init();	// 2021.09.24, 이승재 추가
		userAuthorGroupObj.init();	// 2021.09.24, 이승재 추가
	}
};


var dataAuthorObj = {
	
	/*
	* 데이터 권한
	*/
	data : {},	// 2021.09.24, 이승재 추가
	
	init : function() {
		var that = this;
		
		that.data = userAuthorObj.userDataAuthors;
	},
	
	getDataAuthor : function(dataId) {
		return userAuthorObj.userDataAuthors[dataId];
	}
};

var userAuthorGroupObj = {
	
	/**
	 * 권한그룹
	 */
	authorGroup : {},	// 2021.09.24, 이승재 추가
	
	init : function() {
		var that = this;
		
		that.authorGroup = userAuthorObj.userAuthorGroups;
	},
	
	getUserAuthorGroup : function(authorGroupName) {
		return userAuthorObj.userAuthorGroups[authorGroupName];
	}
};