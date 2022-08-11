// Lks : 도로대장
var roadRegObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도로대장 레이어 선택",
	
	/**
	 * 레이어 데이터
	 * @type {Array.<Object>}
	 */
	data : null,

	
	/**
	 * 데이터 로딩
	 * @returns {Promise} Promise 객체
	 */
	load : function() {
		var that = this;
		var deferred = $.Deferred();
		var url = CONTEXT_PATH + "/roadRegstr/listAll.do";
		$.get(url).done(function(response) {
			if (response && response.rows) {
				that.setData(response.rows);
				deferred.resolve(that.getData());
			} else {
				$.messager.alert(that.TITLE, "레이어 정보를 불러오는 데 실패했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "레이어 정보를 불러오는 데 실패했습니다.");
		});
		return deferred.promise();
	},
	
	/**
	 * 데이터 설정
	 * @param {Object} 도로대장 목록 설정
	 */
	setData : function(data) {
		var that = this;
		that.data = data;
	},
	
	/**
	 * 데이터 반환
	 * @return {Array} 도로대장 목록
	 */
	getData : function() {
		var that = this;
		return that.data;
	},
	
	getLayerId : function(regIdn, useCheck) {
		var that = this;
		
		if (useCheck == null || useCheck) {
			for (var i = 0; i < that.data.length; i++) {
				if (that.data[i].regIdn == regIdn && that.data[i].useAt == "Y")
					return that.data[i].regLayerId;
			}
		}
		else {
			for (var i = 0; i < that.data.length; i++) {
				if (that.data[i].regIdn == regIdn)
					return that.data[i].regLayerId;
			}
		}
		
		return null;
	}
};