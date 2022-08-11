// 점용대장 검색 관리 객체
ocpatRegstrObj = {
		
	TITLE : "점용대장 검색",
	
	CLASS_NAME : "OcpatRegstrSearch",
	
	selector : null,
	
	isCreated : false,
	
	initUi : function() {
		var that = this;
		
		// easyUi설정
		$("#listOcpatRegstr .easyui-datebox", that.selector).datebox();
		$("#listOcpatRegstr .easyui-textbox", that.selector).textbox();
		$("#listOcpatRegstr .easyui-combobox", that.selector).combobox();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 초기화
		$(".a_reset_ocpatRegstr", that.selector).on("click", function() {
			$("input[name=pmtNum]", that.selector).val("");
			$("input[name=pmtYmdMin]", that.selector).val("");
			$("input[name=pmtYmdMax]", that.selector).val("");
			$("input[name=jygLoc]", that.selector).val("");
			$("input[name=prsNam]", that.selector).val("");
			$("input[name=jygUlm]", that.selector).val("전체");
			$("input[name=jysYmdMin]", that.selector).val("");
			$("input[name=jysYmdMax]", that.selector).val("");
			$("input[name=jyeYmdMin]", that.selector).val("");
			$("input[name=jyeYmdMax]", that.selector).val("");
			return false;
		});
		
		// 검색
		$(".a_search_ocpatRegstr", that.selector).on("click", function() {
			that.search();
			return false;
		});
	},
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/ocpat/totalSearchPage.do";
		var windowOptions = {
			width : 660,
			height : 249,
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

		var params = {};
		params.pmtNum = $("input[name=pmtNum]", that.selector).val();		// 허가번호
		params.pmtYmdMin = $("input[name=pmtYmdMin]", that.selector).val();	// 허가일
		params.pmtYmdMax = $("input[name=pmtYmdMax]", that.selector).val();	// 허가일
		params.jygLoc = $("input[name=jygLoc]", that.selector).val();		// 점용위치
		params.prsNam = $("input[name=prsNam]", that.selector).val();		// 점용자 성명
		params.jygUlm = $("#jygUlm", that.selector).val();					// 점용기간 유형
		params.jysYmdMin = $("input[name=jysYmdMin]", that.selector).val();	// 점용 시작일
		params.jysYmdMax = $("input[name=jysYmdMax]", that.selector).val();	// 점용 시작일
		params.jyeYmdMin = $("input[name=jyeYmdMin]", that.selector).val();	// 점용 종료일
		params.jyeYmdMax = $("input[name=jyeYmdMax]", that.selector).val();	// 점용 종료일
		
		var url = CONTEXT_PATH + "/ocpat/searchRegisterSummaries.do";
		
		$.post(url, params).done(function(response) {
			if(response.rows.length < 1){
				alert("조건에 맞는 결과가 없습니다.");
				return false;
			}
			else {
				ocpatRegResultObj.addRows(response.rows);
			}
		}).fail(function() {
			alert("점용대장 목록를 가져오는데 실패하였습니다.");
		});
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
};

// 점용대장 퀵메뉴
ocpatRegstrObj.quick = {
		
	TITLE : "점용대장 목록",
	
	CLASS_NAME : "SearchOcpatRegstr",

//	search : function(searchArea) {
//		var that = this;
//		
//		var params = {};
//		params.wktArea = searchArea;
//		
//		var url = CONTEXT_PATH + "/ocpat/quickSearch.do";
//
//		$.post(url, params).done(function(response) {
//			if(response.rows.length < 1){
//				alert("필지를 점용하고 있는 시설물이 없습니다.");
//				return false;
//			}
//			else {
//				ocpatRegResultObj.addRows(response.rows);
//			}
//		}).fail(function() {
//			alert("점용대장 목록를 가져오는데 실패하였습니다.");
//		});
//	},
	
	searchPnu : function(searchArea, pnu) {
		var params = {};
		params.wktArea = searchArea;
		
		var url = CONTEXT_PATH + "/ocpat/" + pnu + "/quickSearch.do";

		$.post(url, params).done(function(response) {
			if(response.rows.length < 1){
				alert("필지를 점용하고 있는 시설물이 없습니다.");
				return false;
			}
			else {
				ocpatRegResultObj.addRows(response.rows);
			}
		}).fail(function() {
			alert("점용대장 목록를 가져오는데 실패하였습니다.");
		});
	},	
};

// 점용대장 등록
ocpatRegstrObj.add = {
		
	TITLE : null,
	
	CLASS_NAME : "OcpatRegstrAdd",

	ocpatRegstr : null,
	
	selector : null,
	
	isCreated : false,
	
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result.rowCount == 1) {
					that.close();
					$.messager.alert(that.TITLE, "대장이 등록되었습니다.");
				}
				else {
					$.messager.alert(that.TITLE, "대장 등록에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function(){
		var that = this;
		
		// easyUi설정
		$("#addOcpatRegstr .easyui-datebox", that.selector).datebox();
		$("#addOcpatRegstr .easyui-textbox", that.selector).textbox();
		$("#addOcpatRegstr .easyui-combobox", that.selector).combobox();
		$("#addOcpatRegstr .easyui-numberspinner", that.selector).numberspinner();
		
		var data = dongObj.getData();
		$("#selDong", that.selector).combobox({
			valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD),
		});
		
		$("#selDong", that.selector).combobox("loadData", data);
		
		// 산 여부
		$("#checkMauntain", that.selector).switchbutton({
			onText : "Y",
			offText : "N",
			value : 1,
			onChange : function(checked) {
				var node = $(this);
				if(checked) {
					node.switchbutton("setValue", "2");
				}
				else {
					node.switchbutton("setValue", "1");
				}
			}
		});
		$("#checkMauntain", that.selector).switchbutton("setValue", "1");

		// 본번
		$("#mainNum", that.selector).numberspinner({
			required : true,
			value : 1,
			min : 1,
			max : 9999,
			increment : 1,
			width : 160
		});
		
		// 부번
		$("#subNum", that.selector).numberspinner({
			required : true,
			value : 0,
			min : 0,
			max : 9999,
			increment : 1,
			width : 160
		});
	},
	
	bindEvents : function() { 
		var that = this;

		// 저장버튼
		$(".btn_save_ocpatRegstrAdd", that.selector).on("click", function() {
			that.save();
			return false;
		});
		
		// 취소버튼
		$(".btn_close_ocpatRegstrAdd", that.selector).on("click", function() {
			that.close();
		});
	},
	
	open : function(ocpatIdn) {
		var that = this;
			
		if(that.selector) {
			that.close();
		}
		
		that.ocpatRegstr = ocpatRegObj.getRegData(ocpatIdn);
		that.TITLE = that.ocpatRegstr.ocpatAlias;
		
		var url = CONTEXT_PATH + "/ocpat/"  + ocpatIdn + "/addRegstrPage.do";
		var windowOptions = null;
		
		switch(ocpatIdn) {
		case "100100":
		case "200100":
		case "300100":
		case "400100":
			windowOptions = {
				width : 680,
				height : 531,
				top : 120,
				left : 550,
				onClose : function() {
					that.close();
				}
			};
			break;
			
		case "500100":
			windowOptions = {
				width : 680,
				height : 566,
				top : 120,
				left : 550,
				onClose : function() {
					that.close();
				}
			};
			break;
			
		case "600100":
		case "700100":
		case "800100":
		case "900100":
			windowOptions = {
				width : 680,
				height : 531,
				top : 120,
				left : 550,
				onClose : function() {
					that.close();
				}
			};
			break;
		}
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.init();
				that.initUi();
				that.bindEvents();
				
				that.isCreated = true; 
			}
		});
		
		that.selector = "#" + id;
	},
	
	save : function() {
		var that = this;
		
		var url = CONTEXT_PATH + "/ocpat/addRegstr.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
	
};

//미점용 사설안내표지판 조회
pegbObj = {
		
	TITLE : '사설안내표지판 조회',
		
	dataId : 'RDL_PEGB_PS',
	
	occNSearch : function(){
		var that = this;
		
		var projection = MAP.PROJECTION;
		
		var extent = mapObj.getMap().getExtent();
		var from = mapObj.getMap().getView().getProjection();
		var to = ol.proj.get(projection);
		var transformExtent = spatialUtils.transformExtent(extent, from, to);
		
		var extent = {
			xMin : transformExtent[0],
			yMin : transformExtent[1],
			xMax : transformExtent[2],
			yMax : transformExtent[3]
		};
		
		var indexProjection = projection.indexOf(':');
		var projectionStr = projection.substring(indexProjection + 1, projection.length);
		
		
		var cql = " OCC_YN = 'N' ";
		cql += " AND ";
		cql += " ST_INTERSECTS(GEOM, ST_GEOMFROMTEXT( 'POLYGON (( ";
		cql += extent.xMin + " " + extent.yMin;
		cql += ", ";
		
		cql += extent.xMax + " " + extent.yMin;
		cql += ", ";
		
		cql += extent.xMax + " " + extent.yMax;
		cql += ", ";
		
		cql += extent.xMin + " " + extent.yMax;
		cql += ", ";
		
		cql += extent.xMin + " " + extent.yMin;
		cql += "))', " + projectionStr + " )) =1 ";
		
		var filter = {
				filterType : 'CQL',
				cql : cql
		};
		
		spatialSearchUtils.searchSummaries(that.TITLE, that.dataId, filter, function(rows){
			if(rows && rows.length > 0){
				resultObj.close();
				resultObj.addRows(rows);
			}
		});
		
	}
};