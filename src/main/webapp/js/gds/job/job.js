menuObj.jobObj.gdsObj = {
	
	load : function(sysId) {
		// 도시 공원
		if(sysId == 7) {
			return cityParkObj;
		}
		else if(sysId == 8) {
			return ecologyNatureMapObj;
		}
		return menuObj.jobObj;
	}
		
};