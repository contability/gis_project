/**
 * 화천 업무 메뉴
 */
menuObj.jobObj.gnObj = {
	
	load : function(sysId) {
		// 도시 공원
		if(sysId == 7) {
			return cityParkObj;
		}
		else if(sysId == 8) {
			return ecologyNatureMapObj;
		}
		return menuObj.jobObj;
	},
	
	customFacilitiesSearch : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		
		$.get(url).done(function(response) {
			if(response && response.isAuthor) {
				switch(menuNm) {
				case "도로표지판" :
					roadSgnlbrdManageObj.init(menuId, menuNm, url);
					roadSgnlbrdManageObj.search.open();
					break;
				case "배수설비인허가 목록" :
					drngEqpCnfmPrmisnObj.open();
					break;
				case "배수설비인허가 등록" :
					drngEqpCnfmPrmisnObj.add.open();
					break;
				case "배수설비인허가 등록(지도)" :
					drngEqpCnfmPrmisnObj.add.selCbnd();
					break;
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