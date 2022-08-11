/**
 * 배수설비인허가 객체
 */

// 배수설비인허가대장 검색
drngEqpCnfmPrmisnObj = {
	
	TITLE : "배수설비인허가대장 검색",
	
	CLASS_NAME : "DrngEqpCnfmPrmisnSearch",
	
	selector : null,
	
	isCreated : false,
	
	initUi : function() {
		var that = this;
		
		// easyUi datebox init
		$("#searchDrngEqpCnfmPrmisn .easyui-datebox", that.selector).datebox();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 초기화
		$(".a_reset_DrngEqpCnfmPrmisnSearch", that.selector).on("click", function() {
			$("input[name=pmtYmdMin]", that.selector).val("");
			$("input[name=pmtYmdMax]", that.selector).val("");
			$("input[name=pmtNum]", that.selector).val("");
			$("select[name=brcCde]", that.selector).val("");
			$("select[name=aplBjd]", that.selector).val("");
			$("input[name=aplNam]", that.selector).val("");
			$("input[name=proNam]", that.selector).val("");

			return false;
		});
		
		// 검색
		$(".a_search_DrngEqpCnfmPrmisnSearch", that.selector).on("click", function() {
			that.search();
			
			return false;
		});
	},
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/civilAppeal/swge/searchDrngEqpCnfmPrmisnPage.do";
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
		
		var url = CONTEXT_PATH + "/civilAppeal/swge/listDrngEqpCnfmPrmisn.do";
		var params = {};
		
		params.pmtYmdMin = $("input[name=pmtYmdMin]", that.selector).val();
		params.pmtYmdMax = $("input[name=pmtYmdMax]", that.selector).val();
		params.pmtNum = $("input[name=pmtNum]", that.selector).val();
		params.brcCde = $("select[name=brcCde]", that.selector).val();
		params.aplBjd = $("select[name=aplBjd]", that.selector).val();
		params.aplNam = $("input[name=aplNam]", that.selector).val();
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
					dataAlias : "배수설비인허가대장",
					dataId : "SWT_SPMT_MA",
					ids : ids
				});
				
				for(var j in response.result) {
					ids.push(response.result[j].ftrIdn);
				}
				
				resultObj.addRows(data);
			}
		}).fail(function() {
			alert("배수설비인허가대장 리스트를 가져오는데 실패하였습니다.");
		});
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
};

// 배수설비인허가대장 등록
drngEqpCnfmPrmisnObj.add = {
		
	open : function(ftrIdn) {
		var that = this;
		var layerName = "SWT_SPMT_MA";
		var feature = new ol.Feature();
		
		feature.setProperties({ FTR_IDN : ftrIdnObj.getData(layerName) });
		
		windowObj.registerObj.open(layerName, null, feature, function() {
			that.save(feature);
		});
	},
	
	save : function(feature) {
		var url = CONTEXT_PATH + "/civilAppeal/swge/addDrngEqpCnfmPrmisn.do";
		var params = {};
		
		var properties = feature.getProperties();
		for(var key in properties) {
			params[camelcaseUtils.underbarToCamelcase(key)] = properties[key];
		}
		
		$.post(url, params).done(function() {
			windowObj.registerObj.close();
			alert("배수설비인허가대장이 등록되었습니다.");
		}).fail(function() {
			alert("배수설비인허가대장 등록이 실패하였습니다.");
		});
	}

};

// 배수설비인허가대장 수정
drngEqpCnfmPrmisnObj.modify = {
	
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
		modifyData.ftfCde = that.data.FTF_CDE;
		modifyData.pmtYmd = that.data.PMT_YMD;
		modifyData.aplBjd = that.data.APL_BJD;
		modifyData.aplSan = that.data.APL_SAN;
		modifyData.aplBon = that.data.APL_BON;
		modifyData.aplBub = that.data.APL_BUB;
		modifyData.aplNam = that.data.APL_NAM;
		modifyData.aplAdr = that.data.APL_ADR;
		modifyData.aplTel = that.data.APL_TEL;
		modifyData.cntYmd = that.data.CNT_YMD;
		modifyData.sdrVol = that.data.SDR_VOL;
		modifyData.bldUse = that.data.BLD_USE;
		modifyData.bldAra = that.data.BLD_ARA;
		modifyData.ftfIdn = that.data.FTF_IDN;
		modifyData.brcCde = that.data.BRC_CDE;
		modifyData.proNam = that.data.PRO_NAM;
		modifyData.pmtNum = that.data.PMT_NUM;
		modifyData.bldStr = that.data.BLD_STR;
		modifyData.pipCde = that.data.PIP_CDE;
		modifyData.pmsCde = that.data.PMS_CDE;
		modifyData.pipIdn = that.data.PIP_IDN;
		modifyData.etcDes = that.data.ETC_DES;

		var params = {};
		params = $.extend({}, that.originalData, modifyData);
		
		var url = CONTEXT_PATH + "/civilAppeal/swge/" + that.ftrIdn + "/modifyDrngEqpCnfmPrmisn.do";
		$.post(url, params).done(function() {
			that.ftrIdn = null;
			that.data = null;
			that.originalData = {};
			
			windowObj.registerObj.close();
			
			alert("배수설비인허가대장이 수정되었습니다.");
		}).fail(function() {
			alert("배수설비인허가대장 수정이 실패하였습니다.");
		});
	}
	
};
