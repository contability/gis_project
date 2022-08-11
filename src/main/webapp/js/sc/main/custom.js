/**
 * 속초 실행 코드
 */
customObj.scObj = {
		load : function(sysId) {
			// 드론영상
			if (IS_CADASTRALMAP == 'true') {
				var dataAuthor = dataAuthorObj.getDataAuthor(CADASTRALMAP_DATAID);
				if (dataAuthor.indictAt == 'N') {
					$("#a_menu_cadastral_map").hide();
				}
				else {
					cadastralObj.init();
				}
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
		}
};