/**
 * 도메인 코드 객체
 * @type {Object}
 */
var domnCodeObj = {
		
	/**
	 * 제목
	 * @Type {String}
	 */
	TITLE : "도메인 코드",
		
	/**
	 * 도메인 목록
	 * @type {Object}
	 */
	data : {},
	
	/**
	 * 불러오기
	 * @param {String} domnId 도메인 아이디
	 * @return {Object} Promise 객체
	 */
	load : function(domnId) {
		var url = CONTEXT_PATH + "/rest/domn/"+ domnId + "/listAll.do";
		return $.ajax({
			url : url,
			async : false
		});
	},
	
	/**
	 * 도메인 정보 반환
	 * @param {String} domnId 도메인 아이디
	 * @return {Array.<Object>} 도메인 코드 목록
	 */
	getData : function(domnId) {
		var that = this;
		if(!that.data[domnId]) {
			that.load(domnId).done(function(response) {
				if(response && response.rows) {
					that.data[domnId] = response.rows;
				}
				else {
					$.messager.alert(that.TITLE, "[" + domnId + "] : 도메인 코드가 없습니다.");
				}
			}).fail(function() {
				$.messager.alert(that.TITLE, "[" + domnId + "] : 도메인 코드를 불러오는데 실패 했습니다.");
			});
		}
		return that.data[domnId];
	},
	
	/**
	 * 도메인 코드 반환
	 * @param {String} domnId 도메인 아이디
	 * @param {String} codeId 코드 아이디
	 * @return {Object} 도메인 코드
	 */
	getCode : function(domnId, codeId) {
		var that = this;
		var data = that.getData(domnId);
		for(var i=0, len=data.length; i < len; i++) {
			if(data[i].codeId == codeId) {
				return data[i];
			}
		}
		return null;
	},
	
	/**
	 * 도메인 코드 반환
	 * @param {String} domnId 도메인 아이디
	 * @param {String} codeNm 도메인 값
	 * @return {Object} 도메인 코드
	 */
	getCodeByValue : function(domnId, codeNm) {
		var that = this;
		var data = that.getData(domnId);
		for(var i=0, len=data.length; i < len; i++) {
			if(data[i].codeNm == codeNm) {
				return data[i];
			}
		}
		return null;
	}
	
};