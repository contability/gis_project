/**
 * 강릉 업무 메뉴
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
		url = encodeURI(url);
		
		$.get(url).done(function(response) {
			if(response && response.isAuthor) {
				switch(menuNm) {
				case "도로표지판" :
					roadSgnlbrdManageObj.init(menuId, menuNm, url);
					roadSgnlbrdManageObj.search.open();
					break;
				case "관광안내표지판" :
					tursmGuidanceSgnlbrdManageObj.init(menuId, menuNm, url);
					tursmGuidanceSgnlbrdManageObj.search.open();
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
				case "보호구역":
					prtsAreaObj.transformJobMenu();
					break;
				case '미점용 사설안내표지판 조회':
					pegbObj.occNSearch();
					break;
				case '재산조회':
					cmmnPropObj.open();
					break;
				case '지번불부합 재산조회':
					unKnownCmmnPropObj.search();
					break;
				case '대부 및 사용허가조회':
					cmmnLoanObj.open();
					break;
				case '무단사용 조회':
					cmmnOccpObj.open();
					break;
				case '실태조사서 조회':
					cmmnAcexObj.open();
					break;
				case '대부종료기한임박재산 조회':
					cmmnLoanObj.endImminent.open();
					break;
				case '현장조사 결과조회':
					cmmnAcinObj.open();
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