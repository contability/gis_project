/**
 * 동 객체
 * @type {Object}
 */
var dongObj = {
		
	/**
	 * 제목
	 */
	TITLE : "법정동",
	
	/**
	 * 프로미스 객체
	 * @type {Object}
	 */
	promise : null,
	
	/**
	 * 데이터 아이디
	 * @type {String}
	 */
	dataId : COMMON.DONG,
	
	/**
	 * 동명 필드
	 */
	nameField : COMMON.DONG_NAME_FIELD,
	
	/**
	 * 동코드 필드
	 */
	codeField : COMMON.DONG_CODE_FIELD,
	
	/**
	 * 데이터
	 * @type {Array.<String>}
	 */
	data : null,
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.load();
	},
	
	/**
	 * 로딩
	 */
	load : function() {
		var that = this;
		var deferred = $.Deferred();
		var filter = {
			sortOrdr : that.nameField,
			isOriginColumnValue : true
		};
		spatialSearchUtils.listAll(that.TITLE, that.dataId, filter, function(rows) {
			if(rows && rows.length > 0) {
				that.data = rows;
				deferred.resolve();
			}
		});
		that.promise = deferred.promise();
	},
	
	/**
	 * 프로미스 객체 반환
	 * @return {Object} 프로미스 객체
	 */
	getPromise : function() {
		var that = this;
		return that.promise;
	},
	
	/**
	 * 데이터 아이디 반환
	 * @return {String} 데이터 아이디
	 */
	getDataId : function() {
		var that = this;
		return that.dataId;
	},
	
	/**
	 * 데이터 반환
	 * @return {Array.<String>} 데이터
	 */
	getData : function() {
		var that = this;
		return that.data;
	},
	
	checkDongCode : function(code) {
		var that = this;
		var data = that.data;
		for(var i=0, len=data.length; i < len; i++) {
			if(data[i][camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD)] == code) {
				return true;
			}
		}
		return false;
	},
	
	getDongCode : function(code) {
		return code.length == 10 ? code : stringUtils.rpad(code, 10, '0');
	},
	
	getDongName : function(code) {
		var that = this;
		var data = that.data;
		for(var i=0, len=data.length; i < len; i++) {
			var checkCode = null;
			var codeLength = data[i][camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD)].length;
			if(codeLength == code.length) {
				checkCode = code;
			}
			else {
				checkCode = code.substring(0, codeLength);
			}
			
			if(data[i][camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD)] == checkCode) {
				return data[i][camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD)];
			}
		}
		return null;
	},
	
	getDongCodeByDongName : function(name) {
		var that = this;
		var data = that.data;
		for(var i=0, len=data.length; i < len; i++) {
			if(data[i][camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD)] == name) {
				return data[i][camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD)];
			}
		}
		return null;
	}
	
};