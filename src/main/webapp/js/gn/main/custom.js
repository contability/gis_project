/**
 * 강릉 실행 코드
 */
customObj.gnObj = {
	load : function(sysId) {
		// 공통
		// 지형지도 활용
		if (IS_TOPOGRAPHICMAP == 'true') {
			topographicObj.init();
		}
		
		//도로시설물관리
		if(sysId == 2) {
			//공강정보 편집 시 행정동과 1/1000 도엽번호 취득을 위하여 
			hDongObj.init();
			index1000Obj.init();
		}
		//상수시설물관리
		else if (sysId == 3) {
			//공강정보 편집 시 행정동과 1/1000 도엽번호 취득을 위하여 
			hDongObj.init();
			index1000Obj.init();
			
			//상수측량지점(상수관로심도) 사진보기 퀵메뉴 세팅
			wrppAcmsrSpotObj.quickSearch.init();
		}
		//하수시설물관리
		else if (sysId == 4) {
			//공강정보 편집 시 행정동과 1/1000 도엽번호 취득을 위하여 
			hDongObj.init();
			index1000Obj.init();
							
			//배수설비인허가대장 퀵메뉴 세팅
			drngEqpCnfmPrmisnObj.quickSearch.init();
		}
		//도로대장관리
		else if (sysId == 11) {
			hDongObj.init();
			index1000Obj.init();

			// 도로대장 목록
			roadRegObj.load();
			
			// 검색결과 객체 초기화
			roadRegResultObj.init();
			// 영상조회 객체 초기화
			roadVideoResultObj.init();		
		}
		// 점용대장
		else if (sysId == 12) {
			hDongObj.init();
			index1000Obj.init();

			// 점용대장 목록
			ocpatRegObj.load();
			
			// 검색결과 객체 초기화
			ocpatRegResultObj.init();
		}
		// 공유재산
		else if(sysId == 13){
			useAreaObj.open();
		}
	},
	
	deactive : function(sysId) {
		//도로대장관리
		if (sysId == 11) {
			roadRegResultObj.close();	// Lks : 도로대장
			roadVideoResultObj.close();	// Lks : 도로대장 영상조회 닫기
		}
		// 점용대장
		else if (sysId == 12) {
			ocpatRegResultObj.close();
		}
	}
};