menuObj.jobObj.dhObj = {
	
	load : function(sysId) {
		return menuObj.jobObj;
	},
	
	customFacilitiesSearch : function(menuId, menuNm, url){
		url += '?menuId=' + menuId + '&menuNm=' + menuNm;
		url = encodeURI(url);
		
		$.get(url).done(function(rs){
			if(rs && rs.isAuthor){
				switch (menuNm) {
				case '재산 조회':
					cmmnPropObj.open();
					break;
				case '지번불부합 재산 조회':
					unKnownCmmnPropObj.search();
					break;
				case '대부 및 사용허가 조회':
					cmmnLoanObj.open();
					break;
				case '무단 사용 조회':
					cmmnOccpObj.open();
					break;
				case '현장조사 결과조회':
					cmmnAcinObj.open();
					break;
				default:
					break;
				}
			}else{
				$.messager.alert(menuNm, '권한이 없습니다.');
			}
		});
	}
};