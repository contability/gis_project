/**
 * 양구 업무 메뉴
 */
menuObj.jobObj.ygObj = {
	
	load : function(sysId) {
		// 지적기반 생태자연도
		if(sysId == 3) {
			return ecologyNatureMapObj;
		}
		return menuObj.jobObj;
	}
		
};