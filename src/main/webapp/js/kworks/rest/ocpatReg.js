// Lks : 점용허가대장 목록
var ocpatRegObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "점용허가대장 목록",
	
	/**
	 * 점용허가대장 데이터
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
		var url = CONTEXT_PATH + "/ocpat/listAll.do";
		$.get(url).done(function(response) {
			if (response && response.rows) {
				that.setData(response.rows);
				deferred.resolve(that.getData());
			} else {
				$.messager.alert(that.TITLE, "점용하가대장 정보를 불러오는 데 실패했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "점용허가대장 정보를 불러오는 데 실패했습니다.");
		});
		return deferred.promise();
	},
	
	/**
	 * 데이터 설정
	 * @param {Object} 점용허가대장 목록 설정
	 */
	setData : function(data) {
		var that = this;
		that.data = data;
	},
	
	/**
	 * 데이터 반환
	 * @return {Array} 점용허가대장 목록
	 */
	getData : function() {
		var that = this;
		return that.data;
	},

	getRegData : function(ocpatIdn) {
		var that = this;
		
		for (var i = 0; i < that.data.length; i++) {
			if (that.data[i].ocpatIdn == ocpatIdn)
				return that.data[i];
		}
		
		return null;
	},
	
	getLayerId : function(ocpatIdn, useCheck) {
		var that = this;
		
		if (useCheck != null || useCheck) {
			for (var i = 0; i < that.data.length; i++) {
				if (that.data[i].ocpatIdn == ocpatIdn && that.data[i].useAt == "Y")
					return that.data[i].ocpatLayerId;
			}
		}
		else {
			for (var i = 0; i < that.data.length; i++) {
				if (that.data[i].ocpatIdn == ocpatIdn)
					return that.data[i].ocpatLayerId;
			}
		}
		
		return null;
	}
};