/**
 * 도로 객체
 * @type {Object}
 */
var roadObj = {
		
	/**
	 * 제목
	 */
	TITLE : "도로",
	
	/**
	 * 프로미스 객체
	 * @type {Object}
	 */
	promise : null,
	
	/**
	 * 데이터 아이디
	 * @type {String}
	 */
	dataId : COMMON.ROAD,
	
	/**
	 * 도로 데이터 아이디
	 * @type {Array<String>}
	 */
	roadDataIds : ["RDL_RSMN_AS", "RDL_BRDG_AS", "RDL_EVRD_AS", "RDL_UGRD_AS", "RDL_SBWY_AS", "RDL_TRNL_AS"],
	
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
			sortOrdr : "RDN_NAM",
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
	
	getRoadDataIds : function() {
		var that = this;
		return that.roadDataIds;
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
	
	checkRoadCode : function(rdnCde) {
		var that = this;
		for(var i=0, len=that.data.length; i < len; i++) {
			var row = that.data[i];
			if(row.rdnCde == rdnCde) {
				return true;
			}
		}
		return false;
	},
	
	modifyRoadNm : function(rdnCde, rdnNam) {
		var that = this;
		for(var i=0, len=that.data.length; i < len; i++) {
			var row = that.data[i];
			if(row.rdnCde == rdnCde) {
				row.rdnNam = rdnNam;
			}
		}
	},
	
	/**
	 * 도로명 반환
	 * @param {String} rdnCode 도로 코드
	 */
	getRoadNm : function(rdnCde) {
		var that = this;
		for(var i=0, len=that.data.length; i < len; i++) {
			var row = that.data[i];
			if(row.rdnCde == rdnCde) {
				return row.rdnNam;
			}
		}
		return "";
	},
	
	getRoadCode : function(rdnNam) {
		var that = this;
		for(var i=0, len=that.data.length; i < len; i++) {
			var row = that.data[i];
			if(row.rdnNam == rdnNam) {
				return row.rdnCde;
			}
		}
		return "";
	},
	
	searchByRdnCde : function(rdnCde, title) {
		var deferred = $.Deferred();
		
		var url = CONTEXT_PATH + "/rest/road/" + rdnCde + "/select.do";
		$.get(url).done(function(response) {
			if(response && response.data) {
				var rdlIdns = [];
				var rdlCtlrLss = response.data.rdlCtlrLss;
				for(var i=0, len=rdlCtlrLss.length ; i < len; i++) {
					var rdtAlcnDts = rdlCtlrLss[i].rdtAlcnDts;
					for(var j=0, jLen=rdtAlcnDts.length; j < jLen; j++) {
						var rdtAlcnDt = rdtAlcnDts[j];
						rdlIdns.push(rdtAlcnDt.rdlIdn);
					}
				}
				if(rdlIdns.length > 0) {
					deferred.resolve(rdlIdns);
					
				}
				else {
					$.messager.alert(title, "도로 정보가 없습니다.");
				}
			}
			else {
				$.messager.alert(title, "도로 정보를 가져오는 데 실패 했습니다.");
			}
		}).fail(function() {
			$.messager.alert(title, "도로 정보를 가져오는 데 실패 했습니다.");
		});
		
		return deferred.promise();
	},
	
	searchByRdlIdns : function(rdlIdns, title, className) {
		var that = this;
		
		var deferred = $.Deferred();
		
		if(rdlIdns && rdlIdns.length > 0) {
			var filter = {
				filterType : "CQL",
				cql : "RDL_IDN IN (" + rdlIdns.join() + ")",
				isOriginColumnValue : true
			};

			var promises = {};
			var format = new ol.format.WKT();
			var features = [];
			var data = {};
			for(var i=0, len=that.roadDataIds.length; i < len; i++) {
				promises[that.roadDataIds[i]] = $.Deferred();
				spatialSearchUtils.listAll(title, that.roadDataIds[i], filter, function(rows, dataId) {
					if(rows && rows.length > 0) {
						data[dataId] = rows;
						for(var j=0, jLen=rows.length; j < jLen; j++) {
							features.push(format.readFeature(rows[j][MAP.GEOMETRY_ATTR_NAME]));
						}
					}
					promises[dataId].resolve();
				}, { isNoMessage : true });
			}
			$.when(promises["RDL_RSMN_AS"], promises["RDL_BRDG_AS"], promises["RDL_EVRD_AS"], promises["RDL_UGRD_AS"], promises["RDL_SBWY_AS"], promises["RDL_TRNL_AS"]).then(function() {
				if(features.length > 0) {
					deferred.resolve(features, data);
				}
				else {
					$.messager.alert(title, "도로 정보가 없습니다.");
				}
			});
		}
		else {
			$.messager.alert(title, "도로 정보가 없습니다.");
		}
		
		return deferred.promise();
	},
	
	highlight : function(rdlIdns, title, className) {
		var that = this;
		that.searchByRdlIdns(rdlIdns, title, className).done(function(features) {
			highlightObj.showRedFeatures(className, features, true, {
				projection : ol.proj.get(MAP.PROJECTION)
			});
		});
	}
		
};