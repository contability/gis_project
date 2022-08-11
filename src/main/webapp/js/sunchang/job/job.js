/**
 * 순창 업무 메뉴
 */
menuObj.jobObj.sunchangObj = {
		
	load : function(sysId) {
		return menuObj.jobObj;
	},

	customFacilitiesSearch : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
	}
		
};