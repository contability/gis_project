menuObj.searchObj = {
		
	/**
	 * 페이지 명
	 * @type {String}
	 */
	PAGE : "search",
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_menu_panel_search",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "Search",
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "검색",
	
	/**
	 * 메뉴생성여부
	 * @type {String}
	 */
	isMenuCreated : false,
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.createMenu(PROJECT_CODE);
		that.lotNumberObj.init(that);
		that.roadNameAddressObj.init(that);
		that.buildingObj.init(that);
		that.roadNameObj.init(that);
		//that.cityplnRoadObj.init(that);
		that.unityObj.init(that);
		if (PROJECT_CODE == "gc") {
			that.cityplnRoadObj.init(that);
		}
		if (PROJECT_CODE == "gn" && IS_CITY_PLAN_ROAD == "true") {
			that.planRoadObj.init(that);
		}
		if (PROJECT_CODE == "dh") {
			if (IS_CITY_PLAN_ROAD == "true")
				that.dhPlanRoadObj.init(that);
			else
				that.cityplnRoadObj.init(that);
		}		
		
		that.bindEvents();
		that.open();
		
		// 첫 번째 탭 선택
		$(".mapTab .mTab:first", that.selector).trigger("click");
	},
	
	/**
	 * 메뉴 생성
	 * @param {String} projectCode 프로젝트코드
	 */
	//동해 통합검색 제거 및 도로구간 검색 추가
	createMenu : function(projectCode) {
		var that = this;
				
		//메뉴 기 생성여부 확인
		if (!that.isMenuCreated) {
			var tagStr = '<ul>';
		
			tagStr += '<li><a data-for-id="div_menu_panel_search_lot_number" class="mTab" href="#"><img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/search_tab1_on.png" alt="지번" /></a></li>';
			
			tagStr += '<li><a data-for-id="div_menu_panel_search_road_name_address" class="mTab" href="#"><img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/search_tab2_off.png" alt="도로명주소" /></a></li>';
			
			
			tagStr += '<li><a data-for-id="div_menu_panel_search_building" class="mTab" href="#"><img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/search_tab3_off.png" alt="건물명" /></a></li>';
				
			tagStr += '<li><a data-for-id="div_menu_panel_search_road_name" class="mTab" href="#"><img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/search_tab4_off.png" alt="도로명" /></a></li>';
			
			
			if (projectCode == "gc") {
				tagStr += '<li><a data-for-id="div_menu_panel_search_citypln_road" class="mTab" href="#"><img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/search_tab6_off.png" alt="도로구간" /></a></li>';
			}
			else if (projectCode == "dh") {
				if (IS_CITY_PLAN_ROAD == "true") {
					tagStr += '<li><a data-for-id="div_menu_panel_search_dh_plan_road" class="mTab" href="#"><img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/search_tab7_off.png" alt="도시계획도로" /></a></li>';
				} else {
					tagStr += '<li><a data-for-id="div_menu_panel_search_citypln_road" class="mTab" href="#"><img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/search_tab6_off.png" alt="도로구간" /></a></li>';				
				}
			}
			else if (projectCode == "gn") {
				if (IS_CITY_PLAN_ROAD == "true") {
					tagStr += '<li><a data-for-id="div_menu_panel_search_plan_road" class="mTab" href="#"><img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/search_tab7_off.png" alt="도시계획도로" /></a></li>';
				} else {
					tagStr += '<li><a data-for-id="div_menu_panel_search_unity" class="mTab" href="#"><img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/search_tab5_off.png" alt="통합" /></a></li>';
				}
			} else {
				tagStr += '<li><a data-for-id="div_menu_panel_search_unity" class="mTab" href="#"><img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/search_tab5_off.png" alt="통합" /></a></li>';
			}
			
			tagStr += '</ul>';
			
			$(".mapTab", that.selector).append(tagStr);
			that.isMenuCreated = true;
		}
	},
	
	bindEvents : function() {
		var that = this;
		
		// 탭 변경
		$(".mapTab .mTab", that.selector).click(function() {
			var element = $(this);
			var contentId = element.attr("data-for-id");
			
			// 선택 해제
			$(".mapTab .mTab", that.selector).find("img").each(function() {
				var imgElement = $(this);
				imgElement.attr("src", imgElement.attr("src").replace("_on", "_off"));
			});
			// 선택
			element.find("img").each(function() {
				var imgElement = $(this);
				imgElement.attr("src", imgElement.attr("src").replace("_off", "_on"));
			});
			
			$(".tab_content", that.selector).hide();
			$("#"+contentId).show();
		});
		
		// 토지건물 정보 조회
		$(that.selector).on("click", ".a_kras", function() {
			var element = $(this);
			var pnu = element.attr("data-pnu");
			krasObj.open(pnu);
			return false;
		});

	},
	
	createResult : function(selector, data, pagination) {
		var that = this;
		$(".font_total_count", selector).text(pagination.totalRecordCount);
		that.createSearchContent(selector, data);
		that.createSearchPagination(selector, pagination);
		
		$(".div_search_content", selector).show();
		
		if(data.length > 0) {
			$(".div_search_pagination", selector).show();
		}
		else {
			$(".div_search_pagination", selector).hide();
		}
		
	},
	
	createSearchContent : function(selector, data) {
		var tagStr = '';
		for(var i=0, len=data.length; i < len; i++) {
			var row = data[i];
			tagStr += '<li>';
			tagStr += '<a href="#" class="a_content" data-id="' + row.id + '" >' + row.content + '</a>';
			if(row.pnu) {
				tagStr += '<a href="#" class="a_kras" data-pnu="' + row.pnu + '" >';
				tagStr += '<img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/landbuild.png" alt="토지&#183;건물" title="토지&#183;건물" />';
				tagStr += '</a>';
			}
			tagStr += '</li>';
		}
		$(".ul_search_content", selector).html(tagStr);
	},
	
	createSearchPagination : function(selector, pagination) {
		var tagStr = '';
		
		if(pagination.currentPageNo > pagination.firstPageNo) {
			tagStr += '<a href="#" class="a_img" data-page-index="' + pagination.firstPageNo + '"><img src="images/kworks/map/common/boardLst_pp.png" /></a>';
			tagStr += '<a href="#" class="a_img" data-page-index="' + (pagination.currentPageNo-1) + '"><img src="images/kworks/map/common/boardLst_p.png" /></a>';
		}
		
		for(var i = pagination.firstPageNoOnPageList; i <= pagination.lastPageNoOnPageList; i++) {
			tagStr += '<a href="#" class="a_text';
			tagStr += i==pagination.currentPageNo?" a_select ":"";
			tagStr += '" data-page-index="' + i + '">';
			tagStr += i;
			tagStr += '</a>';
		}
		
		if(pagination.currentPageNo < pagination.lastPageNo) {
			tagStr += '<a href="#" class="a_img" data-page-index="' + (pagination.currentPageNo+1) + '"><img src="images/kworks/map/common/boardLst_n.png" /></a>';
			tagStr += '<a href="#" class="a_img" data-page-index="' + pagination.lastPageNo + '"><img src="images/kworks/map/common/boardLst_nn.png" /></a>';
		}
		
		$(".div_search_pagination", selector).html(tagStr);
	},
	
	highlight : function(id, data) {
		var that = this;
		var format = new ol.format.WKT();
		for(var i=0, len=data.length; i < len; i++) {
			var row = data[i];
			if(row[COMMON.PK] == id) {
				var feature = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);
				highlightObj.showRedFeatures(that.CLASS_NAME, [feature], true, {
					projection : ol.proj.get(MAP.PROJECTION)
				});
			}
		}
	},
	
	highlightRoad : function(rdnCde) {
		var that = this;
		var filter = {
			filterType : "COMPARISON",
			comparisonType : "EQUAL_TO",
			property : "RN_CD",
			value : rdnCde,
			isOriginColumnValue : true
		};
		spatialSearchUtils.listAll(that.TITLE, COMMON.ROAD_ROUTE, filter, function(rows, pagination) {
			var format = new ol.format.WKT();
			var features = [];
			if(rows && rows.length > 0) {
				for(var i=0, len=rows.length; i < len; i++) {
					var row = rows[i];
					features.push(format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]));
				}
			}
			if(features.length > 0) {
				highlightObj.showRedFeatures(that.CLASS_NAME, features, true, {
					projection : ol.proj.get(MAP.PROJECTION)
				});
			}
		});
	},
	
	more : function(type, param) {
		var that = this;
		switch(type) {
			case "lotNumber" : 
				$("a[data-for-id=div_menu_panel_search_lot_number]", that.selector).trigger("click");
				that.lotNumberObj.setValueAndSearch(param);
				break;
			case "roadNameAddress" : 
				$("a[data-for-id=div_menu_panel_search_road_name_address]", that.selector).trigger("click");
				that.roadNameAddressObj.setValueAndSearch(param);
				break;
			case "building" : 
				$("a[data-for-id=div_menu_panel_search_building]", that.selector).trigger("click");
				that.buildingObj.setValueAndSearch(param);
				break;
			case "roadName" : 
				$("a[data-for-id=div_menu_panel_search_road_name]", that.selector).trigger("click");
				that.roadNameObj.setValueAndSearch(param);
				break;
		}
	},
	
	open : function() {
		var that = this;
		$(that.selector).show();
	},
	
	close : function() {
		var that = this;
		$(that.selector).hide();
	},
	
	createResultByPlanRoad : function(selector, data, pagination) {
		var that = this;
		$(".font_total_count", selector).text(pagination.totalRecordCount);
		that.createSearchContentByPlanRoad(selector, data);
		that.createSearchPagination(selector, pagination);
		
		$(".div_search_content", selector).show();
		
		if(data.length > 0) {
			$(".div_search_pagination", selector).show();
		}
		else {
			$(".div_search_pagination", selector).hide();
		}
	},
	
	createSearchContentByPlanRoad : function(selector, data) {
		var tagStr = '';
		for(var i=0, len=data.length; i < len; i++) {
			var row = data[i];
			tagStr += '<li>';
			tagStr += '<a href="#" class="a_content" data-id="' + row.id + '" >' + row.content + '</a>';
			tagStr += '<a href="#" class="a_planroad" data-id="' + row.id + '" >';
			tagStr += '<img class="img_ov_off" src="' + CONTEXT_PATH + '/images/kworks/map/skin2/cpr_detail_off.png" alt="세부사항" title="세부사항" />';
			tagStr += '</a>';
			tagStr += '</li>';
		}
		$(".ul_search_content_plan_road", selector).html(tagStr);
	},
	
	highlightByPlanRoad : function(id, data, isMsg) {
		var format = new ol.format.WKT();
		for(var i=0, len=data.length; i < len; i++) {
			var row = data[i];
			if(row.ftrIdn == id) {
				if (row[MAP.GEOMETRY_ATTR_NAME]) {
					var feature = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);
					if (feature) {
						highlightObj.showRedFeatures("CityPlanRoad", [feature], true, {projection : ol.proj.get(MAP.PROJECTION)});
					}
				}
				else {
					highlightObj.removeRedFeatures("CityPlanRoad");
					if (isMsg) {
						$.messager.alert("알림", "장기 미집행 실효도로 및 변경도로입니다.<br>세부사항을 확인하세요.");
					}
					return false;
				}
			}
		}
	}
	
};

menuObj.searchObj.lotNumberObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_menu_panel_search_lot_number",

	/**
	 * 상위 객체 (menuObj.searchObj)
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "지번 검색",
	
	data : null,
		
	/**
	 * 초기화
	 * @param {Object} parent 상위 객체
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
		
		that.initUi();
		that.bindEvents();
		that.load();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 법정동 목록
		$(".sel_dong", that.selector).combobox({
			required : true,
			valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD),
			width : 160
		});
		
		// 본번
		$(".txt_mnnm", that.selector).numberspinner({
			min : 1,
			max : 9999,
			increment : 1,
			width : 160
		});
		
		// 부번
		$(".txt_slno", that.selector).numberspinner({
			min : 0,
			max : 9999,
			increment : 1,
			width : 160
		});
		
		// 검색
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
		
	},
	
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 읍면동에서 엔터 시 본번으로 이동
		$(".sel_dong", that.selector).combobox("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".txt_mnnm", that.selector).numberspinner("textbox").focus();
			}
		});
		
		// 본번에서 엔터 시 부번으로 이동
		$(".txt_mnnm", that.selector).numberspinner("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".txt_slno", that.selector).numberspinner("textbox").focus();
			}
		});
		
		// 부번에서 엔터 시 검색
		$(".txt_slno", that.selector).numberspinner("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".a_search", that.selector).trigger("click");
			}
		});
		
		// 검색
		$(".a_search", that.selector).click(function() {
			if(that.validator()) {
				that.search(1);
				quickObj.triggerOnLand();
			}
			return false;
		});
		
		// 위치 이동
		$(that.selector).on("click", ".a_content", function() {
			var element = $(this);
			var id = element.attr("data-id");
			that.parent.highlight(id, that.data);
			return false;
		});
		
		// 페이징 이동
		$(that.selector).on("click", ".div_search_pagination a", function() {
			var element = $(this);
			var pageIndex = element.attr("data-page-index");
			that.search(pageIndex);
		});
		
	},
	
	/**
	 * 동 목록 불러오기
	 */
	load : function() {
		var that = this;
		dongObj.getPromise().done(function() {
			var data = dongObj.getData();
			$(".sel_dong", that.selector).combobox("loadData", data);
		});
	},
	
	/**
	 * 입력 값 검증
	 * @returns {Boolean} 검증 여부
	 */
	validator : function() {
		var that = this;
		
		// 법정동
		if(!$(".sel_dong", that.selector).combobox("isValid")) {
			$.messager.alert(that.TITLE, "법정동을 선택하여 주십시오.");
			return false;
		}
		return true;
	},
	
	/**
	 * 
	 * @param pageIndex
	 */
	search : function(pageIndex) {
		var that = this;

		// 동 코드 
		var dongCode = $(".sel_dong", that.selector).combobox("getValue");
		if(!dongObj.checkDongCode(dongCode)) {
			$.messager.alert(that.TITLE, "법정동을 선택하여 주십시오.");
			return;
		}
		
		var mntn = $(".chk_mntn", that.selector).is(":checked")?"2":"1";
		var mnnm = $(".txt_mnnm", that.selector).numberspinner("getValue");
		var slno = $(".txt_slno", that.selector).numberspinner("getValue");
		var pnu = pnuObj.createPnuForLike(dongCode, mntn, mnnm, slno);
		that.list(pnu, pageIndex, 10, function(tagData, data, pagination) {
			that.data = data;
			that.parent.createResult(that.selector, tagData, pagination);
		});
	},
	
	list : function(pnu, pageIndex, recordCountPerPage, callback, options) {
		var that = this;
		var filter = {
			filterType : "COMPARISON",
			comparisonType : "LIKE",
			property : "PNU",
			value : pnu,
			pageIndex : pageIndex,
			recordCountPerPage : recordCountPerPage,
			pageSize : 5,
			sortOrdr : "PNU"
		};
		spatialSearchUtils.list(that.TITLE, COMMON.LAND, filter, function(rows, pagination) {
			var data = [];
			for(var i=0, len=rows.length; i < len; i++) {
				var row = rows[i];
				data.push({
					id : row[COMMON.PK],
					content : pnuObj.getLocNm(row.pnu) + " " + row.jibun,
					pnu : row.pnu
				});
			}
			if(callback) {
				callback(data, rows, pagination);
			}
		}, options);
	},
	
	setValueAndSearch : function(param) {
		var that = this;
		if(param.dongCode) {
			$(".sel_dong", that.selector).combobox("setValue", param.dongCode);
		}
		
		if(param.mntn) {
			if(param.mntn == "2") {
				$(".chk_mntn", that.selector).prop("checked", true);
			}
			else {
				$(".chk_mntn", that.selector).prop("checked", false);
			}
		}
		else {
			$(".chk_mntn", that.selector).prop("checked", false);
		}
		
		if(param.mnnm) {
			$(".txt_mnnm", that.selector).numberspinner("setValue", param.mnnm);
		}
		else {
			$(".txt_mnnm", that.selector).numberspinner("clear");
		}
		
		if(param.slno) {
			$(".txt_slno", that.selector).numberspinner("setValue", param.slno);
		}
		else {
			$(".txt_slno", that.selector).numberspinner("clear");
		}
		
		$(".a_search", that.selector).trigger("click");
	}
		
};

menuObj.searchObj.roadNameAddressObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_menu_panel_search_road_name_address",

	/**
	 * 상위 객체 (menuObj.searchObj)
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도로명주소 검색",
	
	data : null,
		
	/**
	 * 초기화
	 * @param {Object} parent 상위 객체
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
		
		that.initUi();
		that.bindEvents();
		that.load();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 도로명 목록
		$(".sel_road_name", that.selector).combobox({
			required : true,
			valueField : "rdnCde",
			textField : "rdnNam",
			width : 160
		});
		
		// 본번
		$(".txt_mnnm", that.selector).numberspinner({
			min : 1,
			max : 9999,
			increment : 1,
			width : 160
		});
		
		// 부번
		$(".txt_slno", that.selector).numberspinner({
			min : 0,
			max : 9999,
			increment : 1,
			width : 160
		});
		
		// 검색
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
		
	},
	
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 도로명에서 엔터 시 본번으로 이동
		$(".sel_road_name", that.selector).combobox("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".txt_mnnm", that.selector).numberspinner("textbox").focus();
			}
		});
		
		// 본번에서 엔터 시 부번으로 이동
		$(".txt_mnnm", that.selector).numberspinner("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".txt_slno", that.selector).numberspinner("textbox").focus();
			}
		});
		
		// 부번에서 엔터 시 검색
		$(".txt_slno", that.selector).numberspinner("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".a_search", that.selector).trigger("click");
			}
		});
		
		// 검색
		$(".a_search", that.selector).click(function() {
			if(that.validator()) {
				that.search(1);
				quickObj.triggerOnBuld();
			}
			return false;
		});
		
		// 위치 이동
		$(that.selector).on("click", ".a_content", function() {
			var element = $(this);
			var id = element.attr("data-id");
			that.parent.highlight(id, that.data);
			return false;
		});
		
		// 페이징 이동
		$(that.selector).on("click", ".div_search_pagination a", function() {
			var element = $(this);
			var pageIndex = element.attr("data-page-index");
			that.search(pageIndex);
		});
		
	},
	
	/**
	 * 도로 목록 불러오기
	 */
	load : function() {
		var that = this;
		roadObj.getPromise().done(function() {
			var data = roadObj.getData();
			$(".sel_road_name", that.selector).combobox("loadData", data);
		});
	},
	
	/**
	 * 입력 값 검증
	 * @returns {Boolean} 검증 여부
	 */
	validator : function() {
		var that = this;
		
		// 도로
		if(!$(".sel_road_name", that.selector).combobox("isValid")) {
			$.messager.alert(that.TITLE, "도로명을 선택하여 주십시오.");
			return false;
		}
		return true;
	},
	
	/**
	 * 
	 * @param pageIndex
	 */
	search : function(pageIndex) {
		var that = this;

		// 도로 코드 
		var rdnCde = $(".sel_road_name", that.selector).combobox("getValue");
		if(!roadObj.checkRoadCode(rdnCde)) {
			$.messager.alert(that.TITLE, "도로명을 선택하여 주십시오.");
			return;
		}
		var mnnm = $(".txt_mnnm", that.selector).numberspinner("getValue");
		var slno = $(".txt_slno", that.selector).numberspinner("getValue");
		
		var cql = "";
		cql += "RN_CD = '" + rdnCde + "' ";
		if(mnnm) {
			cql += "AND BULD_MNNM = '" + mnnm + "' ";
		}
		if(slno) {
			cql += "AND BULD_SLNO = '" + slno + "' ";
		}
		
		that.list(cql, pageIndex, 10, function(tagData, data,  pagination) {
			that.data = data;
			that.parent.createResult(that.selector, tagData, pagination);
		});
	},
	
	list : function(cql, pageIndex, recordCountPerPage, callback, options) {
		var that = this;
		var filter = {
			filterType : "CQL",
			cql : cql,
			pageIndex : pageIndex,
			recordCountPerPage : recordCountPerPage,
			pageSize : 5,
			sortOrdr : "BD_MGT_SN"
		};
		spatialSearchUtils.list(that.TITLE, COMMON.BULD, filter, function(rows, pagination) {
			var data = [];
			for(var i=0, len=rows.length; i < len; i++) {
				var row = rows[i];
				var pnu = row.bdMgtSn.substring(0, 19);
				var content = roadObj.getRoadNm(row.rnCd);
				content += " " + $.trim(row.buldMnnm);
				var buldSlno = $.trim(row.buldSlno);
				if(buldSlno && buldSlno != "0") {
					content += "-" + buldSlno;
				}
				
				data.push({
					id : row[COMMON.PK],
					content : content,
					pnu : pnu
				});
			}
			if(callback) {
				callback(data, rows, pagination);
			}
		}, options);
	},
	
	setValueAndSearch : function(param) {
		var that = this;
		if(param.rdnCde) {
			$(".sel_road_name", that.selector).combobox("setValue", param.rdnCde);
		}
		
		if(param.mnnm) {
			$(".txt_mnnm", that.selector).numberspinner("setValue", param.mnnm);
		}
		else {
			$(".txt_mnnm", that.selector).numberspinner("clear");
		}
		
		if(param.slno) {
			$(".txt_slno", that.selector).numberspinner("setValue", param.slno);
		}
		else {
			$(".txt_slno", that.selector).numberspinner("clear");
		}
		
		$(".a_search", that.selector).trigger("click");
	}
		
};

menuObj.searchObj.buildingObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_menu_panel_search_building",

	/**
	 * 상위 객체 (menuObj.searchObj)
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "건물명 검색",
	
	data : null,
		
	/**
	 * 초기화
	 * @param {Object} parent 상위 객체
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
		
		that.initUi();
		that.bindEvents();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 건물 명
		$(".txt_buld_nm", that.selector).textbox({
			required : true,
			width : 160,
			prompt : "건물명을 입력하십시오."
		});
		
		// 검색
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
		
	},
	
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 건물 명에서 엔터 시 검색
		$(".txt_buld_nm", that.selector).textbox("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".a_search", that.selector).trigger("click");
			}
		});
		
		// 검색
		$(".a_search", that.selector).click(function() {
			if(that.validator()) {
				that.search(1);
				quickObj.triggerOnBuld();
			}
			return false;
		});
		
		// 위치 이동
		$(that.selector).on("click", ".a_content", function() {
			var element = $(this);
			var id = element.attr("data-id");
			that.parent.highlight(id, that.data);
			return false;
		});
		
		// 페이징 이동
		$(that.selector).on("click", ".div_search_pagination a", function() {
			var element = $(this);
			var pageIndex = element.attr("data-page-index");
			that.search(pageIndex);
		});
		
	},
	
	/**
	 * 입력 값 검증
	 * @returns {Boolean} 검증 여부
	 */
	validator : function() {
		var that = this;
		
		// 건물명
		if(!$(".txt_buld_nm", that.selector).textbox("isValid")) {
			$.messager.alert(that.TITLE, "건물명을 입력하여 주십시오.");
			return false;
		}
		return true;
	},
	
	/**
	 * 
	 * @param pageIndex
	 */
	search : function(pageIndex) {
		var that = this;

		// 건물명
		var buldNm = $(".txt_buld_nm", that.selector).textbox("getValue");
		that.list(buldNm, pageIndex, 10, function(tagData, data, pagination) {
			that.data = data;
			that.parent.createResult(that.selector, tagData, pagination);
		});
	},
	
	list : function(buldNm, pageIndex, recordCountPerPage, callback, options) {
		var that = this;
		var filter = {
			filterType : "COMPARISON",
			comparisonType : "LIKE",
			property : "POS_BUL_NM",
			value : "%" + buldNm + "%",
			pageIndex : pageIndex,
			recordCountPerPage : recordCountPerPage,
			pageSize : 5,
			sortOrdr : "POS_BUL_NM"
		};
		spatialSearchUtils.list(that.TITLE, COMMON.BULD, filter, function(rows, pagination) {
			var data = [];
			for(var i=0, len=rows.length; i < len; i++) {
				var row = rows[i];
				data.push({
					id : row[COMMON.PK],
					content : row.posBulNm,
					pnu : row.bdMgtSn.substring(0, 19)
				});
			}
			
			if(callback) {
				callback(data, rows, pagination);
			}
		}, options);
	},
	
	setValueAndSearch : function(param) {
		var that = this;
		if(param.value) {
			$(".txt_buld_nm", that.selector).textbox("setValue", param.value);
		}
		$(".a_search", that.selector).trigger("click");
	}
		
};

menuObj.searchObj.roadNameObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_menu_panel_search_road_name",

	/**
	 * 상위 객체 (menuObj.searchObj)
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도로명 검색",
	
	data : null,
		
	/**
	 * 초기화
	 * @param {Object} parent 상위 객체
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
		
		that.initUi();
		that.bindEvents();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 도로 명
		$(".txt_road_name", that.selector).textbox({
			required : true,
			width : 160,
			prompt : "도로명을 입력하십시오."
		});
		
		// 검색
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
		
	},
	
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 도로 명에서 엔터 시 검색
		$(".txt_road_name", that.selector).textbox("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".a_search", that.selector).trigger("click");
			}
		});
		
		// 검색
		$(".a_search", that.selector).click(function() {
			if(that.validator()) {
				that.search(1);
			}
			return false;
		});

		// 위치 이동
		$(that.selector).on("click", ".a_content", function() {
			var element = $(this);
			var id = element.attr("data-id");
			var imageElement = element.find("img");
			that.parent.highlightRoad(id);
			
			//동해시 도로명 검색시 도로주소도로구간 레이어 설정 
			if(imageUtils.isOn(imageElement)) {
				imageUtils.off(imageElement);
				if(PROJECT_CODE == "dh") {		
					sldObj.setNamedLayerVisible(COMMON.ROAD_ROUTE, false);
				}				
			}
			else {
				imageUtils.on(imageElement);
				if(PROJECT_CODE == "dh") {
					sldObj.showDatas([COMMON.ROAD_ROUTE]);
				}
			}
			mapObj.reloadWMS();
			return false;
		});
		
		// 페이징 이동
		$(that.selector).on("click", ".div_search_pagination a", function() {
			var element = $(this);
			var pageIndex = element.attr("data-page-index");
			that.search(pageIndex);
		});
		
	},
	
	/**
	 * 입력 값 검증
	 * @returns {Boolean} 검증 여부
	 */
	validator : function() {
		var that = this;
		
		// 도로명
		if(!$(".txt_road_name", that.selector).textbox("isValid")) {
			$.messager.alert(that.TITLE, "도로명을 입력하여 주십시오.");
			return false;
		}
		return true;
	},
	
	/**
	 * 
	 * @param pageIndex
	 */
	search : function(pageIndex) {
		var that = this;

		// 도로명
		var roadName = $(".txt_road_name", that.selector).textbox("getValue");
		that.list(roadName, pageIndex, 10, function(tagData, data, pagination) {
			that.data = data;
			that.parent.createResult(that.selector, tagData, pagination);
		});
	},
	
	list : function(roadName, pageIndex, recordCountPerPage, callback, options) {
		var that = this;
		 var filter = {
			filterType : "COMPARISON",
			comparisonType : "LIKE",
			property : "RDN_NAM",
			value : "%" + roadName + "%",
			pageIndex : pageIndex,
			recordCountPerPage : recordCountPerPage,
			pageSize : 5,
			sortOrdr : "RDN_NAM"
		};
		spatialSearchUtils.list(that.TITLE, COMMON.ROAD, filter, function(rows, pagination) {
			var data = [];
			for(var i=0, len=rows.length; i < len; i++) {
				var row = rows[i];
				data.push({
					id : row.rdnCde,
					content : row.rdnNam
				});
			}

			if(callback) {
				callback(data, rows, pagination);
			}
		}, options);
	},
	
	setValueAndSearch : function(param) {
		var that = this;
		if(param.value) {
			$(".txt_road_name", that.selector).textbox("setValue", param.value);
		}
		$(".a_search", that.selector).trigger("click");
	}
		
};

menuObj.searchObj.unityObj = {
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_menu_panel_search_unity",

	/**
	 * 상위 객체 (menuObj.searchObj)
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "통합 검색",
	
	/**
	 * 검색 파라미터 - 더보기를 위해 사용
	 * @type {Object}
	 */
	param : null,
	
	data : {
		lotNumber : null,
		roadNameAddress : null,
		building : null
	},
		
	/**
	 * 초기화
	 * @param {Object} parent 상위 객체
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
		
		that.initUi();
		that.bindEvents();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 검색어
		$(".txt_search", that.selector).textbox({
			required : true,
			width : 160,
			prompt : "검색어를 입력하십시오."
		});
		
		// 검색
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
		
	},
	
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 검색어에서 엔터 시 검색
		$(".txt_search", that.selector).textbox("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".a_search", that.selector).trigger("click");
			}
		});
		
		// 검색
		$(".a_search", that.selector).click(function() {
			if(that.validator()) {
				that.search(1);
				quickObj.triggerOnLand();
				quickObj.triggerOnBuld();
			}
			return false;
		});
		
		// 지번 위치 이동
		$(".div_search_content_lot_number", that.selector).on("click", ".a_content", function() {
			var element = $(this);
			var id = element.attr("data-id");
			that.parent.highlight(id, that.data.lotNumber);
			return false;
		});
		
		// 도로명주소 위치 이동
		$(".div_search_content_road_address", that.selector).on("click", ".a_content", function() {
			var element = $(this);
			var id = element.attr("data-id");
			that.parent.highlight(id, that.data.roadNameAddress);
			return false;
		});
		
		// 건물명 위치 이동
		$(".div_search_content_building", that.selector).on("click", ".a_content", function() {
			var element = $(this);
			var id = element.attr("data-id");
			that.parent.highlight(id, that.data.building);
			return false;
		});
		
		// 도로명 위치 이동
		$(".div_search_content_road_name", that.selector).on("click", ".a_content", function() {
			var element = $(this);
			var id = element.attr("data-id");
			that.parent.highlightRoad(id);
			return false;
		});
		
		// 지번 위치 더보기
		$(".div_search_content_lot_number .a_more", that.selector).click(function() {
			that.parent.more("lotNumber", that.param);
			return false;
		});
		
		// 도로명주소 더보기
		$(".div_search_content_road_address .a_more", that.selector).click(function() {
			that.parent.more("roadNameAddress", that.param);
			return false;
		});
		
		// 건물명 위치 더보기
		$(".div_search_content_building .a_more", that.selector).click(function() {
			that.parent.more("building", that.param);
			return false;
		});
		
		// 도로명 위치 더보기
		$(".div_search_content_road_name .a_more", that.selector).click(function() {
			that.parent.more("roadName", that.param);
			return false;
		});
		
	},
	
	/**
	 * 입력 값 검증
	 * @returns {Boolean} 검증 여부
	 */
	validator : function() {
		var that = this;
		
		// 검색어
		if(!$(".txt_search", that.selector).textbox("isValid")) {
			$.messager.alert(that.TITLE, "검색어를 입력하여 주십시오.");
			return false;
		}
		return true;
	},
	
	/**
	 * 
	 * @param pageIndex
	 */
	search : function(pageIndex) {
		var that = this;

		// 검색어
		var value = $(".txt_search", that.selector).textbox("getValue");
		
		var dong = null;
		var ri = null;
		var rdnNam = null;
		var mntn = 1;
		var mnnm = null;
		var slno = null;
		
		var split = $.trim(value).split(" ");
		for(var i=0, len=split.length; i < len; i++) {
			var str = split[i];
			if(str.match(/(읍|면|동)$/)) {
				dong = str;
			}
			else if(str.match(/(리)$/)) {
				ri = str;
			}
			else if(str.match(/(길|로|가)$/)) {
				rdnNam = str;
			}
			else if(str == "산") {
				san = 2;
			}
			else if(str.indexOf("-") != -1) {
				var sub = str.split("-");
				if(sub.length == 2) {
					mnnm = sub[0];
					slno = sub[1];
				}
			}
			else if($.isNumeric(str)) {
				if(mnnm == null) {
					mnnm = str;
				}
				else if(slno == null) {
					slno = str;
				}
			}
		}
		
		var bjdNam = null;
		if(dong && ri) {
			bjdNam = dong + ri;
		}
		else if(dong) {
			bjdNam = dong;
		}
		else if(ri) {
			bjdNam = ri;
		}
		
		// 더보기를 위한 값 설정
		that.param = {
			value : value,
			mntn : mntn,
			mnnm : mnnm,
			slno : slno
		};
		$(".div_search_content", that.selector).hide();
		$(".div_search_content_none").show();
		
		// 지번		
		if(bjdNam) {
			var dongCode = dongObj.getDongCodeByDongName(bjdNam);
			that.param.dongCode = dongCode;
			
			var pnu = pnuObj.createPnuForLike(dongCode, mntn, mnnm, slno);
			that.parent.lotNumberObj.list(pnu, 1, 3, function(tagData, data, pagination) {
				if(data.length > 0) {
					that.data.lotNumber = data;
					that.parent.createSearchContent($(".div_search_content_lot_number", that.selector), tagData);
					$(".div_search_content_lot_number", that.selector).show();
					$(".div_search_content_none").hide();
				}
				if(pagination.totalRecordCount > 3) {
					$(".div_search_content_lot_number .div_tool", that.selector).show();
				}
				else {
					$(".div_search_content_lot_number .div_tool", that.selector).hide();
				}
			}, { isNoMessage : true });
		}
		
		// 도로명 주소
		if(rdnNam) {
			var rdnCde = roadObj.getRoadCode(rdnNam);
			that.param.rdnCde = rdnCde;

			var cql = "";
			cql += "RN_CD = '" + rdnCde + "' ";
			if(mnnm) {
				cql += "AND BULD_MNNM = '" + mnnm + "' ";
			}
			if(slno) {
				cql += "AND BULD_SLNO = '" + slno + "' ";
			}
			
			that.parent.roadNameAddressObj.list(cql, pageIndex, 3, function(tagData, data, pagination) {
				if(data.length > 0) {
					that.data.roadNameAddress = data;
					that.parent.createSearchContent($(".div_search_content_road_address", that.selector), tagData);
					$(".div_search_content_road_address", that.selector).show();
					$(".div_search_content_none").hide();
				}
				if(pagination.totalRecordCount > 3) {
					$(".div_search_content_road_address .div_tool", that.selector).show();
				}
				else {
					$(".div_search_content_road_address .div_tool", that.selector).hide();
				}
			}, { isNoMessage : true });
		}
		
		// 건물명
		that.parent.buildingObj.list(value, pageIndex, 3, function(tagData, data, pagination) {
			if(data.length > 0) {
				that.data.building = data;
				that.parent.createSearchContent($(".div_search_content_building", that.selector), tagData);
				$(".div_search_content_building", that.selector).show();
				$(".div_search_content_none").hide();
			}
			if(pagination.totalRecordCount > 3) {
				$(".div_search_content_building .div_tool", that.selector).show();
			}
			else {
				$(".div_search_content_building .div_tool", that.selector).hide();
			}
		}, { isNoMessage : true });
	
		// 도로명
		that.parent.roadNameObj.list(value, pageIndex, 3, function(tagData, data, pagination) {
			if(data.length > 0) {
				that.parent.createSearchContent($(".div_search_content_road_name", that.selector), tagData);
				$(".div_search_content_road_name", that.selector).show();
				$(".div_search_content_none").hide();
			}
			if(pagination.totalRecordCount > 3) {
				$(".div_search_content_road_name .div_tool", that.selector).show();
			}
			else {
				$(".div_search_content_road_name .div_tool", that.selector).hide();
			}
		}, { isNoMessage : true });
		
	}
};

menuObj.searchObj.cityplnRoadObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_menu_panel_search_citypln_road",

	/**
	 * 상위 객체 (menuObj.searchObj)
	 * @type {Object}
	 */
	parent : null,
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도로구간 검색",
		
	data : null,
			
	/**
	 * 초기화
	 * @param {Object} parent 상위 객체
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
		
		that.initUi();
		that.bindEvents();
	},
		
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 도로구간명(도시계획도로명)
		$(".txt_citypln_road_nm", that.selector).textbox({
			required : true,
			width : 150,
			prompt : "소로1-1"
		});
			
		// 검색
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
			
	},
		
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 도로구간명에서 엔터 시 검색
		$(".txt_citypln_road_nm", that.selector).textbox("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".a_search", that.selector).trigger("click");
			}
		});
			
		// 검색
		$(".a_search", that.selector).click(function() {
			if(that.validator()) {
				that.search(1);
				//quickObj.triggerOnBuld();
			}
			return false;
		});
		
		// 위치 이동
		$(that.selector).on("click", ".a_content", function() {
			var element = $(this);
			var id = element.attr("data-id");
			var imageElement = element.find("img");
			that.parent.highlight(id, that.data);
			
			//동해시 도로구간 검색시 국토계획/교통시설 레이어 설정 
			if(imageUtils.isOn(imageElement)) {
				imageUtils.off(imageElement);
				if(PROJECT_CODE == "dh") {		
					sldObj.setNamedLayerVisible(COMMON.CITYPLN_ROAD, false);
				}				
			}
			else {
				imageUtils.on(imageElement);
				if(PROJECT_CODE == "dh") {
					sldObj.showDatas([COMMON.CITYPLN_ROAD]);
				}
			}
			mapObj.reloadWMS();
			return false;
		});
		
			
		// 페이징 이동
		$(that.selector).on("click", ".div_search_pagination a", function() {
			var element = $(this);
			var pageIndex = element.attr("data-page-index");
			that.search(pageIndex);
		});
			
	},
		
	/**
	 * 입력 값 검증
	 * @returns {Boolean} 검증 여부
	 */
	validator : function() {
		var that = this;
			
		// 도로구간명
		if(!$(".txt_citypln_road_nm", that.selector).textbox("isValid")) {
			$.messager.alert(that.TITLE, "소로1-1");
			return false;
		}
		return true;
	},
		
	/**
	 * 
	 * @param pageIndex
	 */
	search : function(pageIndex) {
		var that = this;

		// 도로구간명
		var cityplnRoadNm = $(".txt_citypln_road_nm", that.selector).textbox("getValue");
		that.list(cityplnRoadNm, pageIndex, 10, function(tagData, data, pagination) {
			that.data = data;
			that.parent.createResult(that.selector, tagData, pagination);
		});
	},
		
	list : function(cityplnRoadNm, pageIndex, recordCountPerPage, callback, options) {
		var that = this;
		var filter = {
			filterType : "COMPARISON",
			comparisonType : "LIKE",
			property : "REMARK",
			value : "%" + cityplnRoadNm + "%",
			pageIndex : pageIndex,
			recordCountPerPage : recordCountPerPage,
			pageSize : 5,
			sortOrdr : "REMARK"
		};
		spatialSearchUtils.list(that.TITLE, COMMON.CITYPLN_ROAD, filter, function(rows, pagination) {
			var data = [];
			for(var i=0, len=rows.length; i < len; i++) {
				var row = rows[i];
				data.push({
					id : row[COMMON.PK],
					content : row.remark,
				});
			}
				
			if(callback) {
				callback(data, rows, pagination);
			}
		}, options);
	},
		
	setValueAndSearch : function(param) {
		var that = this;
		if(param.value) {
			$(".txt_citypln_road_nm", that.selector).textbox("setValue", param.value);
		}
		$(".a_search", that.selector).trigger("click");
	}
};

/**
 * 강릉시 도시계획도로 검색
 */
menuObj.searchObj.planRoadObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_menu_panel_search_plan_road",

	/**
	 * 상위 객체 (menuObj.searchObj)
	 * @type {Object}
	 */
	parent : null,
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도시계획도로 검색",
		
	data : null,
	
	isSpatialSearch  : false,
			
	/**
	 * 초기화
	 * @param {Object} parent 상위 객체
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
		
		that.initUi();
		that.bindEvents();
	},
		
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 구역명
		$(".sel_plan_road_sect", that.selector).combobox({
			required : true,
			valueField : "code",
			textField : "name",
			width : 160,
			editable : false
		});
		$(".sel_plan_road_sect", that.selector).combobox("setValue", "강릉");
		
		// 등급
		$(".sel_plan_road_grade", that.selector).combobox({
			required : false,
			valueField : "code",
			textField : "name",
			width : 160,
			editable : false
		});
		$(".sel_plan_road_grade", that.selector).combobox("setValue", 0);
		
		// 류
		$(".sel_plan_road_type", that.selector).combobox({
			required : false,
			valueField : "code",
			textField : "name",
			width : 160,
			editable : false
		});
		$(".sel_plan_road_type", that.selector).combobox("setValue", 0);
		
		// 번호
		$(".txt_plan_road_num", that.selector).textbox({
			required : false,
			width : 160,
			prompt : ""
		});
		
		// 검색
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
		
		// 상세조회 오버 시 이미지 변경
		$(".img_ov_off").on("mouseover", function() {
			var element = $(this);
			element.attr("src", element.attr("src").replace("_off", "_ov"));
		});
		// 상세조회 아웃 시 이미지 변경
		$(".img_ov_off").on("mouseout", function() {
			var element = $(this);
			element.attr("src", element.attr("src").replace("_ov", "_off"));
		});
		
	},
		
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 구역명에서 엔터 시 검색
		$(".sel_plan_road_sect", that.selector).combobox("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".a_search", that.selector).trigger("click");
			}
		});
			
		// 검색
		$(".a_search", that.selector).click(function() {
			if (that.validator()) {
				that.search(1);
			}
			return false;
		});
		
		// 위치 이동
		$(that.selector).on("click", ".a_content", function() {
			var element = $(this);
			var ftrIdn = element.attr("data-id");
			
			$(".ul_search_content_plan_road li", that.selecor).removeClass("li_active");
			$(this, that.selector).parent().addClass("li_active");
			
			that.parent.highlightByPlanRoad(ftrIdn, that.data, true);
			mapObj.reloadWMS();
			return false;
		});
		
		// 세부사항 조회
		if (PROJECT_CODE == "gn" && IS_CITY_PLAN_ROAD == "true") {
			$(that.selector).on("click", ".a_planroad", function() {
				var element = $(this);
				var ftrIdn = element.attr("data-id");
				
				$(".ul_search_content_plan_road li", that.selecor).removeClass("li_active");
				$(this, that.selector).parent().addClass("li_active");
				
				that.parent.highlightByPlanRoad(ftrIdn, that.data, false);
				mapObj.reloadWMS();
				
				cityPlanRoadObj.open(ftrIdn);
				return false;
			});
		}
			
		// 페이징 이동
		$(that.selector).on("click", ".div_search_pagination a", function() {
			var element = $(this);
			var pageIndex = element.attr("data-page-index");
			that.search(pageIndex);
		});
			
		// 공간데이터 조회
		$("#btn_quick", that.selector).on("click", function(event) {
			var className = "CityPlanRoad";
//			highlightObj.removeFeatures(className);
			$(".ul_search_content_plan_road li", that.selecor).removeClass("li_active");
			
			if (!that.isSpatialSearch) {
				that.isSpatialSearch = true;
				selectObj.active(className, "Point", "drawend", function(evt) {
					that.searchSpatial(evt.feature, className, CITY_PLAN_ROAD_SPATIAL_FIELD).done(function(result) {
						if (result.uprIdn){
							cityPlanRoadObj.quick.search(result.uprIdn, function(response) {
								if (response.ftrIdn) {
									highlightObj.removeFeatures("QuickSearchDong");
									highlightObj.removeFeatures("QuickSearchRoadName");
									cityPlanRoadObj.open(response.ftrIdn);
									that.isSpatialSearch = false;
									return false;
								}
								else {
									// Lks : 사용자 요청으로 신규입력 기능 막음.
									highlightObj.removeFeatures(className);
									$.messager.alert(that.TITLE, "검색 결과가 없습니다.");
									
//									$.messager.confirm(that.TITLE, "도시계획도로 정보를 찾을 수 없습니다.<br>신규 등록하시겠습니까? ", function(isTrue) {
//										if (isTrue) {
//											cityPlanRoadObj.addRegister.open(that, result.uprIdn);
//											that.isSpatialSearch = false;
//											return false;
//										}
//									});
								}
							});
						}
						else {
							highlightObj.removeFeatures(className);
						}
					});
				});
			}
			else {
				that.isSpatialSearch = false;
				selectObj.active(className);
			}
			event.preventDefault();
		});
	},
		
	/**
	 * 입력 값 검증
	 * @returns {Boolean} 검증 여부
	 */
	validator : function() {
		var that = this;
			
		// 구역명
		if(!$(".sel_plan_road_sect", that.selector).combobox("isValid")) {
			$.messager.alert(that.TITLE, "구역명은 필수 항목입니다.");
			return false;
		}
		return true;
	},
	
	/**
	 * 
	 * @param pageIndex
	 */
	search : function(pageIndex) {
		var that = this;
		
		// 구역명
		var sectName = $(".sel_plan_road_sect", that.selector).combobox("getValue");
		
		// 등급 
		var gradeCode = $(".sel_plan_road_grade", that.selector).combobox("getValue");
		if (gradeCode == "0") {
			gradeCode = null;
		}

		// 류 
		var typeCode = $(".sel_plan_road_type", that.selector).combobox("getValue");
		if (typeCode == "0") {
			typeCode = null;
		}
		
		// 번호
		var num = $(".txt_plan_road_num", that.selector).textbox("getValue");
		
		var searchParam = {
			name : sectName,
			grade : gradeCode,
			type : typeCode,
			num : num,
			pageIndex : pageIndex,
			recordCountPerPage : 10,
			pageSize : 5
		};
		
		that.list(searchParam, function(tagData, data, pagination) {
			that.data = data;
			that.parent.createResultByPlanRoad(that.selector, tagData, pagination);
		});
		
		highlightObj.removeFeatures("CityPlanRoad");
	},
		
	list : function(searchParam, callback, options) {
		var that = this;
		
		cityPlanRoadUtils.list(that.TITLE, searchParam, function(rows, pagination) {
			var data = [];
			for(var i=0, len=rows.length; i < len; i++) {
				var row = rows[i];
				data.push({
					id : row.ftrIdn,
					content : row.uprNam + " " + row.uprGrd + " " + row.uprTyp + "-" + row.uprNum,
					mnum : row.uprIdn 
				});
			}
				
			if(callback) {
				callback(data, rows, pagination);
			}
		}, options);
	},
	
	searchSpatial : function(feature, className, fieldName) {
		var deferred = $.Deferred();
		
		var title = "도시계획도로";
		
		// 축척 제한
		var scale = mapObj.getMap().getScale();
		if(scale > 2500) {
			$.messager.confirm(title, "2500 이하의 축척에서만 사용 가능합니다. 축척을 확대 하시겠습니까?", function(isTrue) {
				if(isTrue) {
					var cooridates = feature.getGeometry().getCoordinates();
					mapObj.getMap().moveToCenter(cooridates);
					mapObj.getMap().moveToScale(2500);
				}
			});
			return deferred.promise();
		}
		
		// 버퍼 적용
		var buffer = mapObj.getMap().getView().getResolution() * 0;
		if(buffer < 1) {
			buffer = 0;
		}
		var geometry = feature.getGeometry();
		var bufferGeometry = spatialUtils.buffer(geometry, buffer);
		var mapProjection = mapObj.getMap().getView().getProjection();
		var sysProjection = ol.proj.get(MAP.PROJECTION);
		var transformGeometry = spatialUtils.transform(bufferGeometry, mapProjection, sysProjection);
		var format = new ol.format.WKT();
		var wkt = format.writeFeature(new ol.Feature(transformGeometry));
		
		// 필터
		var filter = {
			filterType : "SPATIAL",
			spatialType : "INTERSECTS",
			wkt : wkt
		};
		
		// 공간 검색
		spatialSearchUtils.listAll(title, CITY_PLAN_ROAD_SPATIAL, filter, function(data) {
			if(data && data.length && data.length > 0) {
				highlightObj.removeFeatures(className);

				var keyField = camelcaseUtils.underbarToCamelcase(fieldName);
				var result = {};
				result.uprIdn = data[0][keyField];
				
				var geom = data[0][MAP.GEOMETRY_ATTR_NAME];
				var convert = new ol.format.WKT();
				var feature = convert.readFeature(geom);
				
				if (result.uprIdn && result.uprIdn.length > 0) {
					highlightObj.showRedFeatures(className, [feature], true, {projection : ol.proj.get(MAP.PROJECTION)});
				}
				else {
					$.messager.alert(title, "검색 결과가 없습니다.");
				}
			
				deferred.resolve(result);
				mainObj.defaultActive();
			}
			else {
				highlightObj.removeFeatures(className);
			}
		});
		return deferred.promise();
	}
};

/**
 * 동해시 도시계획도로 검색
 */
menuObj.searchObj.dhPlanRoadObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_menu_panel_search_dh_plan_road",

	/**
	 * 상위 객체 (menuObj.searchObj)
	 * @type {Object}
	 */
	parent : null,
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도시계획도로 검색",
		
	data : null,
			
	/**
	 * 초기화
	 * @param {Object} parent 상위 객체
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
		
		that.initUi();
		that.bindEvents();
	},
		
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 국도명
		$(".txt_road_name", that.selector).textbox({
			required : false,
			width : 160,
			prompt : "국도명을 입력하십시오."
		});		
		
		// 구분
		$(".sel_plan_road_grade", that.selector).combobox({
			required : false,
			valueField : "code",
			textField : "name",
			width : 160,
			editable : false
		});
		$(".sel_plan_road_grade", that.selector).combobox("setValue", 0);
		
		// 분류
		$(".sel_plan_road_type", that.selector).combobox({
			required : false,
			valueField : "code",
			textField : "name",
			width : 160,
			editable : false
		});
		$(".sel_plan_road_type", that.selector).combobox("setValue", 0);
		
		// 노선번호
		$(".txt_plan_road_num", that.selector).textbox({
			required : false,
			width : 160,
			prompt : ""
		});
		
		// 검색
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
	},
		
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 국도명에서 엔터 시 검색
		$(".txt_road_name", that.selector).textbox("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".a_search", that.selector).trigger("click");
			}
		});
			
		// 검색
		$(".a_search", that.selector).click(function() {
			if (that.validator()) {
				that.search(1);
			}
			return false;
		});
	},
		
	/**
	 * 입력 값 검증
	 * @returns {Boolean} 검증 여부
	 */
	validator : function() {
		var that = this;
			
		// 국도명
//		if(!$(".txt_road_name", that.selector).textbox("isValid")) {
//			$.messager.alert(that.TITLE, "국도명은 필수 항목입니다.");
//			return false;
//		}
		return true;
	},
	
	/**
	 * 
	 * @param pageIndex
	 */
	search : function(pageIndex) {
		var that = this;
		
		// 국도명
		var sectName = $(".txt_road_name", that.selector).textbox("getValue");
		
		// 구분 
		var gradeCode = $(".sel_plan_road_grade", that.selector).combobox("getValue");
		if (gradeCode == "0") {
			gradeCode = null;
		}

		// 분류 
		var typeCode = $(".sel_plan_road_type", that.selector).combobox("getValue");
		if (typeCode == "0") {
			typeCode = null;
		}
		
		// 노선번호
		var num = $(".txt_plan_road_num", that.selector).textbox("getValue");
		
		var params = {};
		params.uprNam = sectName;
		params.uprGrd = gradeCode;
		params.uprTyp = typeCode;
		params.uprNum = num;
		
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/listAllSummary.do";
		
		$.post(url, params).done(function(response) {
			if(response && response.rows) {
				var row = response.rows;
				
				var data = [];
				data.push({
					dataId : row.dataId,
					dataAlias : row.dataAlias,
					ids : row.ids	
				});
				
				dhRoplResultObj.addRows(data);
			}
			else {
				alert("조건에 맞는 결과가 없습니다.");
				return false;
			}
		}).fail(function() {
			alert("검색 목록를 가져오는데 실패하였습니다.");
		});
	}
};