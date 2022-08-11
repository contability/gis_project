/**
 * 과천 실행 코드
 */
customObj.gcObj = {
		load : function(sysId) {
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
			//교통시설물관리
			else if (sysId == 8) {
				hDongObj.init();
				index1000Obj.init();
			}
		}
};