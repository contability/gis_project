/**
 * 양구 실행 코드
 */
customObj.ygObj = {
		load : function(sysId) {
			//토지중심공사이력관리
			if (sysId == 2) {
				landCenterCntrwkRegstrObj.map.init();
				landCenterCntrwkRegstrObj.map.bindMapEvents();
			}
		}
};