/**
 * 고성 실행 코드
 */
customObj.gsObj = {
		load : function(sysId) {

			//도로시설물관리
			if(sysId == 2) {
				hDongObj.init();
				index1000Obj.init();
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
			//환경공간업무 
			else if (sysId == 5) {
				hDongObj.init();
				index1000Obj.init();
				windowObj.explantEditObj.init(); // 현지조사 편집 초기화
				windowObj.reantEditObj.init(); // 작업일지 편집 초기화
			}
		},
		deactive : function(sysId) {

		}
};