/**
 * 동 객체
 * @type {Object}
 */
var index1000Obj = {
		
	/**
	 * 제목
	 */
	TITLE : '1/1000색인도',
	
	/**
	 * 프로미스 객체
	 * @type {Object}
	 */
	promise : null,
	
	/**
	 * 데이터 아이디
	 * @type {String}
	 */
	dataId : COMMON.INDEX1000,
	
	/**
	 * 동코드 필드
	 */
	codeField : COMMON.INDEX1000_NUM_FIELD,
	
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
		if (that.dataId != '') {
			that.load();
		}
	},
	
	/**
	 * 로딩
	 */
	load : function() {
		var that = this;
		var deferred = $.Deferred();
		var filter = {
			sortOrdr : that.codeField,
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
	}	
};