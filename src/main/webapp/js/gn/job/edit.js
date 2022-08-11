editObj.gnObj = {
		
	getOnLoad : function(dataId) {
		var that = this;
		
		if(dataId == "CTY_ATHL_PS" || dataId == "CTY_PLAY_PS" ||
			dataId == "CTY_REST_PS" || dataId == "CTY_REST_LS" || dataId == "CTY_REST_AS") {
			
			return that.searchPark;			
		}
		else {
			return null;
		}
	},
	
	searchPark : function() {
		var dataFeature = editObj.feature;
		
		cityParkObj.cityParkSearchUtilsObj.search(dataFeature);
	}
	
	/*
	 *  사용X
	 * 
	
	//도시공원 공간편집 등록&수정&삭제 후 검색 결과 목록 현황 재조회
	getReSearchCityPark : function(data){
		
		if(data == "CTY_PLAY_PS" || data == "CTY_ATHL_PS" || data == "CTY_REST_PS" ||
				data == "CTY_REST_LS" || data == "CTY_REST_AS" ) {
				
			var dataId = cityParkObj.cityParkResultObj.generalObj.parent.dataId;
			var fid = cityParkObj.cityParkResultObj.generalObj.parent.fid;
			cityParkObj.cityParkResultObj.open(dataId, fid);
		}
	}*/
		
};