var layerObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "레이어 선택",
	
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
		var url = CONTEXT_PATH + "/rest/lyrCtgry/listAll.do";
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
	 * @param {Object} 레이어[카테고리] 목록 설정
	 */
	setData : function(data) {
		var that = this;
		that.data = data;
		for(var i=0, len=that.data.length; i < len; i++) {
			var kwsLyrs = that.data[i].kwsLyrs;
			for(var j=0, jLen=kwsLyrs.length; j < jLen; j++) {
				var kwsLyr = kwsLyrs[j];
				if(kwsLyr.lyrBassStyle) {
					kwsLyr.lyrBassStyle = JSON.parse(kwsLyr.lyrBassStyle);
				}
			}
		}
	},
	
	/**
	 * 데이터 반환
	 * @return {Array} 레이어[카테고리] 목록
	 */
	getData : function() {
		var that = this;
		return that.data;
	},
	
	getLayerByCategoryId : function(categoryId) {
		var that = this;
		var data = that.data;
		for(var i=0, len=data.length; i < len; i++) {
			var category = data[i];
			if(category.lyrCtgryId == categoryId) {
				return category;
			}
		}
	},
	
	/**
	 * 카테고리 객체 검색
	 * @param {String} 카테고리 명
	 * @return {Object} 카테고리 객체
	 */
	getLayerCategoryByCategoryName : function(categoryName) {
		var that = this;
		var data = that.data;
		for(var i=0, len=data.length; i < len; i++) {
			var category = data[i];
			if(category.lyrCtgryId == categoryName) {
				return category;
			}
		}
		return null;
	},
	
	getLayersByLayerNames : function(layerNames) {
		var that = this;
		var layers = [];
		for(var i=0, len=layerNames.length; i < len; i++) {
			var layerName = layerNames[i];
			var layer = that.getLayerByLayerName(layerName);
			if(layer) {
				layers.push(layer);
			}
		}
		return layers;
	},
	
	/**
	 * 레이어 객체 검색
	 * @param {String} 레이어 명
	 * @return {Object} 레이어 객체
	 */
	getLayerByLayerName : function(layerName) {
		var that = this;
		var data = that.data;
		for(var i=0, len=data.length; i < len; i++) {
			var kwsLyrs = data[i].kwsLyrs;
			for(var j=0, jLen=kwsLyrs.length; j < jLen; j++) {
				var kwsLyr = kwsLyrs[j];
				if(kwsLyr.lyrId == layerName) {
					return kwsLyr;
				}
			}
		}
		return null;
	}
		
};