/**
 * 도로민원 객체
 */

// 도로민원 검색
roadCivilAppealObj = {
	
	TITLE : "도로민원 검색",
	
	CLASS_NAME : "RoadCivilAppealSearch",
	
	selector : null,
	
	isCreated : false,
	
	initUi : function() {
		var that = this;
		
		// easyUi datebox init
		$("#searchRoadCivilAppeal .easyui-datebox", that.selector).datebox();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 초기화
		$(".a_reset_roadCivilAppealSearch", that.selector).on("click", function() {
			$("select[name=aplCde]", that.selector).val("");
			$("select[name=proCde]", that.selector).val("");
			$("input[name=rcvYmdMin]", that.selector).val("");
			$("input[name=rcvYmdMax]", that.selector).val("");
			$("select[name=aplBjd]", that.selector).val("");
			$("input[name=apmNam]", that.selector).val("");
			$("input[name=proNam]", that.selector).val("");

			return false;
		});
		
		// 검색
		$(".a_search_roadCivilAppealSearch", that.selector).on("click", function() {
			that.search();
			
			return false;
		});
	},
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/civilAppeal/road/searchRoadCivilAppealPage.do";
		var windowOptions = {
			width : 665,
			height : 244,
			top : 120,
			left : 330,
			isClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.initUi();
				that.bindEvents();
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	search : function() {
		var that = this;
		
		var url = CONTEXT_PATH + "/civilAppeal/road/listRoadCivilAppeal.do";
		var params = {};
		
		params.aplCde = $("select[name=aplCde]", that.selector).val();
		params.proCde = $("select[name=proCde]", that.selector).val();
		params.rcvYmdMin = $("input[name=rcvYmdMin]", that.selector).val();
		params.rcvYmdMax = $("input[name=rcvYmdMax]", that.selector).val();
		params.aplBjd = $("select[name=aplBjd]", that.selector).val();
		params.apmNam = $("input[name=apmNam]", that.selector).val();
		params.proNam = $("input[name=proNam]", that.selector).val();
		
		$.get(url, params).done(function(response) {
			var data = [];
			var ids = [];
			
			if(response.result.length < 1){
				alert("조건에 맞는 결과가 없습니다.");
				return false;
			}
			else {
				data.push({
					dataAlias : "도로민원",
					dataId : "RDT_RSER_MA",
					ids : ids
				});
				
				for(var j in response.result) {
					ids.push(response.result[j].ftrIdn);
				}
				
				resultObj.addRows(data);
			}
		}).fail(function() {
			alert("도로민원 리스트를 가져오는데 실패하였습니다.");
		});
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
};

// 도로민원 등록
roadCivilAppealObj.add = {
		
	open : function(ftrIdn) {
		var that = this;
		var layerName = "RDT_RSER_MA";
		var feature = new ol.Feature();
		
		feature.setProperties({ FTR_IDN : ftrIdnObj.getData(layerName) });
		
		windowObj.registerObj.open(layerName, null, feature, function() {
			that.save(feature);
		});
	},
	
	save : function(feature) {
		var url = CONTEXT_PATH + "/civilAppeal/road/addRoadCivilAppeal.do";
		var params = {};
		
		var properties = feature.getProperties();
		for(var key in properties) {
			params[camelcaseUtils.underbarToCamelcase(key)] = properties[key];
		}
		
		$.post(url, params).done(function() {
			windowObj.registerObj.close();
			alert("도로민원이 등록되었습니다.");
		}).fail(function() {
			alert("도로민원 등록이 실패하였습니다.");
		});
	}

};

// 도로민원 수정
roadCivilAppealObj.modify = {
	
	ftrIdn : null,
	
	data : null,
	
	originalData : {},
	
	save : function(fids, props) {
		var that = this;
		
		that.ftrIdn = fids;
		that.data = props;

		var modifyData = {};
		modifyData.ftrCde = that.data.FTR_CDE;
		modifyData.ftrIdn = that.data.FTR_IDN;
		modifyData.rcvYmd = that.data.RCV_YMD;
		modifyData.rcvNam = that.data.RCV_NAM;
		modifyData.aplAdr = that.data.APL_ADR;
		modifyData.aplExp = that.data.APL_EXP;
		modifyData.aplCde = that.data.APL_CDE;
		modifyData.apmNam = that.data.APM_NAM;
		modifyData.apmAdr = that.data.APM_ADR;
		modifyData.apmTel = that.data.APM_TEL;
		modifyData.durYmd = that.data.DUR_YMD;
		modifyData.proCde = that.data.PRO_CDE;
		modifyData.proExp = that.data.PRO_EXP;
		modifyData.proYmd = that.data.PRO_YMD;
		modifyData.proNam = that.data.PRO_NAM;
		modifyData.aplBjd = that.data.APL_BJD;
		
		var params = {};
		params = $.extend({}, that.originalData, modifyData);
		
		var url = CONTEXT_PATH + "/civilAppeal/road/" + that.ftrIdn + "/modifyRoadCivilAppeal.do";
		$.post(url, params).done(function() {
			that.ftrIdn = null;
			that.data = null;
			that.originalData = {};
			
			windowObj.registerObj.close();
			
			alert("도로민원이 수정되었습니다.");
		}).fail(function() {
			alert("도로민원 수정이 실패하였습니다.");
		});
	}
	
};
