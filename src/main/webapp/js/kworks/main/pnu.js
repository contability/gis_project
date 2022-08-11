/**
 * 행정구역 
 * @namespace
 */
var pnuObj = {
	
	/**
	 * 시군구명
	 * @type {String}
	 */
	signguNm : COM.CITY_NAME,
	
	createPnu : function(dongCode, mntn, mnnm, slno) {
		var pnu = dongObj.getDongCode(dongCode);
		pnu += mntn;
		pnu += stringUtils.lpad(mnnm, 4);
		pnu += stringUtils.lpad(slno, 4);
		return pnu;
	},
	
	createPnuForLike : function(dongCode, mntn, mnnm, slno) {
		var pnu = dongObj.getDongCode(dongCode);
		pnu += mntn;
		if(mnnm) {
			pnu += stringUtils.lpad(mnnm, 4);
		}
		if(slno) {
			pnu += stringUtils.lpad(slno, 4);
		}
		pnu += "%";
		return pnu;
	},
	
	/**
	 * 주소 반환
	 * 
	 * @param {String} pnu 토지코드
	 * @returns {String} 주소
	 */
	getAddr : function(pnu, isFull) {
		var that = this;
		return that.getLocNm(pnu, isFull) + " " + that.getJibunNm(pnu);
	},
	
	/**
	 * 행정구역 반환
	 * 
	 * @param {String} pnu 토지코드
	 * @returns {String} 행정구역
	 */
	getLocNm : function(pnu, isFull) {
		var that = this;
		var dongCode = pnu.substring(0, 10);
		if(isFull) {
			return that.signguNm + " " + dongObj.getDongName(dongCode);
		}
		else {
			return dongObj.getDongName(dongCode);
		}
	},
	
	/**
	 * 지번 반환
	 * 
	 * @param {String} pnu 토지코드
	 * @returns {String} 지번
	 */
	getJibunNm : function(pnu) {
		var jibun = pnu.substring(10, 11)=="2"?"산 ":"";
		jibun += parseInt(pnu.substring(11, 15));
		jibun += "-";
		jibun += parseInt(pnu.substring(15, 19));
		return jibun;
	}
		
};