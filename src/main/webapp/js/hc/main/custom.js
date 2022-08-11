/**
 * 양양 실행 코드
 */
customObj.yyObj = {
		load : function(sysId) {
			//상수시설물관리
			if (sysId == 3) {
				hDongObj.init();
				index1000Obj.init();
			}
			//하수시설물관리
			else if (sysId == 4) {
				hDongObj.init();
				index1000Obj.init();
			}
		},
		deactive : function(sysId) {
			//정책지도자료관리서비스
			if (sysId == 7) {
				policyRegResultObj.close();	// 정책지도자료관리서비스
				
			}
		}
};