/**
 * 양양 업무 메뉴
 */
menuObj.jobObj.yyObj = {
		
	load : function(sysId) {
		
		// 지적기반 생태자연도
		if(sysId == 5) {
			return ecologyNatureMapObj;
		}
		
		if(sysId==7){
			return policyDeptSearchObj;
		}
		
		return menuObj.jobObj;
	},

customFacilitiesSearch : function(menuId, menuNm, url) {
	url += "?menuId=" + menuId + "&menuNm=" + menuNm;
	url = encodeURI(url);
	
	$.get(url).done(function(response) {
		if(response && response.isAuthor) {
			switch(menuNm) {
			case "도시기준점 목록":
				ctrlPntObj.allList.search(menuId, menuNm, url);
				break;
			case "도시기준점 검색":
				ctrlPntObj.open();
				break;
			default :
				break;
			}
		}
		else {
			$.messager.alert(menuNm, "권한이 없습니다.");
		}
	});
}
		
};