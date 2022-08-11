/**
 * 동해 실행 코드
 */
customObj.dhObj = {
	load : function(sysId) {
		
		// 공통
		if (IS_CITY_PLAN_ROAD == 'true' && sysId != 6) {
			dhRoplResultObj.init();
		}
		
		//도로시설물관리
		if(sysId == 2) {
			hDongObj.init();
			index1000Obj.init();
		}
		//상수시설물관리
		else if (sysId == 3) {
			hDongObj.init();
			index1000Obj.init();
		}
		//하수시설물관리
		else if (sysId == 4) {
			hDongObj.init();
			index1000Obj.init();
		}
		//공유재산관리
		else if (sysId == 6) {
			hDongObj.init();
			index1000Obj.init();
		}
		
		// 파노라마 VR
		panoramaVrObj.init();
	}
};