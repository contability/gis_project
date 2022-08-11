/**
 * 양양 업무 메뉴
 */
menuObj.jobObj.gsObj = {
		
	load : function(sysId) {
		
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
			case "현지조사표 편집":
				windowObj.explantEditObj.open();
				break;
			case "제거작업일지 편집":
				windowObj.reantEditObj.open();
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