/**
 * 데이터 > 데이터 필드 객체
 * @type {Object}
 */
var dataObj = {

	/**
	 * 데이터 > 데이터 필드 정보 목록
	 * @type {Object}
	 */
	data : {},
	
	TITLE : "데이터",
	
	init : function() {
		var that = this;
		that.load();
	},
	
	/**
	 * 데이터 로딩
	 * 
	 */
	load : function() {
		var that = this;
		var url = CONTEXT_PATH + "/rest/data/listAll.do";
		$.get(url).done(function(response) {
			if(response && response.rows) {
				var rows = response.rows;
				for(var i=0, len=rows.length; i < len; i++) {
					var row = rows[i];
					that.data[row.dataId] = row;
				}
			}
			else {
				$.messager.alert(that.TITLE, "데이터 정보를 가져오는 데 실패 했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "데이터 정보를 가져오는 데 실패 했습니다.");
		});
	},
	
	/**
	 * 데이터 상세 정보 로딩
	 * @param {String} 데이터 아이디
	 */
	loadDataDetail : function(dataId) {
		var that = this;
		var url = CONTEXT_PATH + "/rest/data/" + dataId + "/select.do";
		$.ajax({ url:url, async:false }).done(function(response) {
			if(response && response.data) {
				that.data[dataId] = response.data;
			}
			else {
				$.messager.alert(that.TITLE, "데이터 정보를 가져오는 데 실패했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "데이터 정보를 가져오는 데 실패했습니다.");
		});
	},
	
	/**
	 * 데이터 정보 검색
	 * @param {String} 데이터 아이디
	 * @return {Object} 데이터 정보
	 */
	getDataByDataId : function(dataId, isDetail) {
		var that = this;
		var data = that.data;
		if(isDetail && (!data[dataId] || !data[dataId].kwsDataFields)) {
			that.loadDataDetail(dataId);
		}
		return data[dataId];
	},
	
	getDataField : function(dataId, fieldId) {
		var that = this;
		var data = that.getDataByDataId(dataId, true);
		var dataFields = data.kwsDataFields;
		for(var i=0, len=dataFields.length; i < len; i++) {
			if(dataFields[i].fieldId == fieldId) {
				return dataFields[i];
			}
		}
	},
	
	getPkColumn : function(dataId) {
		var that = this;
		var kwsData = that.getDataByDataId(dataId, true);
		var dataFields = kwsData.kwsDataFields;
		
		for(var i=0, len=dataFields.length; i < len; i++) {
			var dataField = dataFields[i];
			if(dataField.pkAt == "Y") {
				return camelcaseUtils.underbarToCamelcase(dataField.fieldId);
			}
		}
	},
	
	getDataDetailByDataId : function(dataId) {
		var that = this;
		
		that.loadDataDetail(dataId);
		var data = that.data;
		
		return data[dataId];
	},
	
	getDataByRoadRegsterId : function(dataId) {
		var that = this;
		var data = that.data;
		
		var layerId = null;
		for (var key in data) {
			if (data[key].roadRegAttr == dataId) {
				layerId = data[key].dataId;
				break;
			}
		}
		
		if (layerId) {
			//	if(!data[layerId] || !data[layerId].kwsDataFields) {
			that.loadDataDetail(layerId);
			return data[layerId];
		}
		else {
			that.loadDataDetail(dataId);
			return data[dataId];
		}
	},
	
	getDataByOcpatRegsterId : function(dataId) {
		var that = this;
		var data = that.data;
		
		var layerId = null;
		for (var key in data) {
			if (data[key].roadRegAttr == dataId) {
				layerId = data[key].dataId;
				break;
			}
		}
		
		if (layerId) {
			//	if(!data[layerId] || !data[layerId].kwsDataFields) {
			that.loadDataDetail(layerId);
			return data[layerId];
		}
		else {
			that.loadDataDetail(dataId);
			return data[dataId];
		}
	},
	
	getDatabyDataCtgry : function(dataCtgryId){
		var that = this;
//		var data = that.data;
//		
		var returnDataId = [];
//		debugger;
//		for(var i=0; i<that.dataLength.length; i++){
//			if(data.dataCtgryId==dataCtgryId){
//				debugger;
//				returnDataId
//			}
//		}
		$.ajax({
			url : CONTEXT_PATH + '/rest/data//listAll.do',
			type : 'GET',
			dataType : 'json',
			async : false,
			success : function(response){
				if(response && response.rows) {
					var rows = response.rows;
					for(var i=0, len=rows.length; i < len; i++) {
						if(rows[i].dataCtgryId==dataCtgryId){
							returnDataId.push(rows[i].dataId);
						}
					}
				}else{
					$.messager.alert(that.TITLE, "데이터목록을 불러오는데 실패했습니다");
				}
			},
			error : function(err){
				$.messager.alert(that.TITLE, "데이터목록을 불러오는데 실패했습니다");
				console.error(err);
			}
		});
		
		return returnDataId;
	}
	
};