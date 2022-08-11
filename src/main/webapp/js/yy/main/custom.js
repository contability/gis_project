/**
 * 양양 실행 코드
 */
customObj.yyObj = {
		load : function(sysId) {
			// 지적원도
			if (IS_CADASTRALMAP == 'true') {
				var dataAuthor = dataAuthorObj.getDataAuthor(CADASTRALMAP_DATAID);
				if (dataAuthor.indictAt == 'N') {
					$("#a_menu_cadastral_map").hide();
				}
				else {
					cadastralObj.init();
				}
			}
			
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
			//기준점관리
			else if (sysId == 6) {
				hDongObj.init();
			}
			//정책지도자료관리서비스
			else if (sysId == 7) {
				hDongObj.init();
				index1000Obj.init();
				policyRegResultObj.init();
				editPolicyObj.init();		// 점책지도 등록객체 초기화
				policyDeptSearchObj.load.init();
				
			}
		},
		deactive : function(sysId) {
			//정책지도자료관리서비스
			if (sysId == 7) {
				policyRegResultObj.close();	// 정책지도자료관리서비스
				
			}
		}
};