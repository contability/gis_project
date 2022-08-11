/**
 * 동 객체
 * @type {Object}
 * 20181118 양양군.
 * 우측상단빠른검색에서 사용.
 */
var dongLiObj = {
		
	/**
	 * 제목
	 */
	TITLE : "읍면동",
	
	/**
	 * 프로미스 객체
	 * @type {Object}
	 */
	promise : null,
	
	/**
	 * 데이터 아이디
	 * @type {String}
	 */
	dataId : COMMON.DONGLI,
	
	/**
	 * 동명 필드
	 */
	nameField : COMMON.DONGLI_NAME_FIELD,
	
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
	
	checkDongLiCode : function(code){
		var that = this;
		var data = that.data;
		for(var i = 0; i < data.length; i++){
			if(data[i][camelcaseUtils.underbarToCamelcase(COMMON.DONGLI_CODE_FIELD)] == code){
				return true;
			}
		}
		
		return false;
	},
	
	/**
	 * 데이터 반환
	 * @return {Array.<String>} 데이터
	 */
	getData : function() {
		var that = this;
		return that.data;
	}
	
};