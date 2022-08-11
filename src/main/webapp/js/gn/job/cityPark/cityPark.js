/**
 * 도시공원 유틸
 * @type {Object}
 */
var cityParkUtils = {
		
	/**
	 * 도시공원 전체 목록 검색
	 * @returns {Array} 도시공원 목록
	 */
	listAllCityPark : function() {
		var rows = [];
		var url = CONTEXT_PATH + "/cityPark/listAll.do?nocache="+Math.random();
		$.ajax({
			url : url,
			async : false
		}).done(function(response) {
			rows = response.rows;
		});
		return rows;
	},
		
	/**
	 * 데이터 그리드 옵션 생성
	 * @param {String} dataId 데이터 아이디
	 * @returns 데이터 그리드 옵션
	 */
	createGridOptions : function(dataId) {
		var kwsData = dataObj.getDataByDataId(dataId, true);
		var dataFields = kwsData.kwsDataFields;

		var frozenColumns = [{
			field : "id",
			title : "번호",
			width : 100,
			sortable : true
		}];
		var pk = null;
		var columns = [];
		for (var i = 0, len = dataFields.length; i < len; i++) {
			var dataField = dataFields[i];
			var fieldId = camelcaseUtils.underbarToCamelcase(dataField.fieldId);
			var fieldAlias = dataField.fieldAlias;
			var indictTy = dataField.indictTy;

			if (dataField.pkAt == "Y") {
				pk = fieldId;
			} else if (indictTy != "WKT" && indictTy != "HIDE") {
				columns.push({
					field : fieldId,
					title : fieldAlias,
					width : 100,
					sortable : false
				});
			}
		}
		
		var options = {
			pk : pk,
			frozenColumns : [ frozenColumns ],
			columns : [ columns ],
			singleSelect : true,
			fitColumns : true,
			showFooter : false
		};
		return options;
	}
		
};

/**
 * 도시공원
 * @type {Object}
 */
var cityParkObj = {
		
	/**
	 * 페이지 명
	 * @type {String}
	 */
	PAGE : "cityPark",
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_menu_panel_job",
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "업무관리",
	
	/**
	 * 데이터 아이디
	 * @type {String}
	 */
	DATA_ID : "CTY_PARK_AS",
	
	/**
	 * 벡터 소스
	 * @type {ol.source.Vector}
	 */
	source : null,
	
	/**
	 * 오버레이
	 * @type {ol.Overlay}
	 */
	overlay : null,
	
	/**
	 * 오버레이 객체
	 * @type {ol.Feature}
	 */
	overlayFeature : null,
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.initGis();
		that.loadCityPark();
		
		that.initUi();
		
		that.cityParkSearchObj.init(that);
		// 2차 개발 시 진행 
		// that.changeDetailsSearchObj.init(that);
		// that.civilAppealSearchObj.init(that);
		menuObj.jobObj.init(that);
		that.statisticsObj.init(that);
		that.open();
	},
	
	/**
	 * GIS 초기화
	 */
	initGis : function() {
		var that = this;
		that.source = new ol.source.Vector();
		var layer = new ol.layer.Vector({
			id : "cityParkLayer",
			source : that.source,
			style : function(feature) {
				var properties = feature.getProperties();
				var prkCde = properties.prkCde;
				return new ol.style.Style({
					image : new ol.style.Icon({
						src : FULL_PATH + "/images/gn/citypark/"+prkCde+".png",
						anchor : [0.5, 0.5]
					})
				});
			}
		});
		mapObj.getMap().addLayer(layer);
		
	},
	
	/**
	 * 도시공원 불러오기
	 */
	loadCityPark : function() {
		var that = this;
		var format = new ol.format.WKT();
		
		var cityParks = cityParkUtils.listAllCityPark();
		var features = [];
		for (var i = 0, len = cityParks.length; i < len; i++) {
			var row = cityParks[i];
			var feature = format.readFeature(row.centroid);
			feature.setProperties(row);
			features.push(feature);
		}
		that.source.addFeatures(features);
		
		mapObj.getMap().on('pointermove', function(evt) {
			var feature = mapObj.getMap().forEachFeatureAtPixel(evt.pixel, function(feature) {
				return feature;
			});
			if(feature) {
				if(that.overlayFeature != feature) {
					if(that.overlay) {
						mapObj.getMap().removeOverlay(that.overlay);
					}
					
					that.overlayFeature = feature;
					that.overlay = new ol.Overlay({
						element : goog.dom.createDom(goog.dom.TagName.DIV, {'class' : 'citypark_tooltip'}),
						offset : [0, -25],
						positioning : 'bottom-center',
						population: 4000,
				        rainfall: 500
					});
					
					var props = feature.getProperties();
					if(props && props.prkCde) {
						var title = props.prkNam + "[" + domnCodeObj.getCode("KWS-0311", props.prkCde).codeNm + "]";
						
						var tagStr = '';
						tagStr += '<a href="#" class="a_close">x</a>';
						tagStr += '<div class="div_title">' + title + '</div>';
						tagStr += '<div class="div_content">' + props.prkLoc + '</div>';
						tagStr += '<div class="div_content">' + props.mngAdd + '</div>';
						tagStr += '<div class="div_tools"><a href="#" class="a_show_register" data-id="' + props.ftrIdn + '" >대장보기</a></div>';
						
						$(that.overlay.getElement()).html(tagStr);
						mapObj.getMap().addOverlay(that.overlay);
						that.overlay.setPosition(feature.getGeometry().getCoordinates());
						
						$(".a_close", that.overlay.getElement()).click(function() {
							mapObj.getMap().removeOverlay(that.overlay);
							that.overlayFeature = null;
							that.overlay = null;
						});
						
						$(".a_show_register", that.overlay.getElement()).click(function() {
							var element = $(this);
							var fid = element.attr("data-id");
							cityParkObj.cityParkResultObj.open(that.DATA_ID, fid);
						});	
					}
					
				}
			}
		});
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		$(".div_menu_panel_city_park_tabs", that.selector).tabs({
			tabWidth : 50,
			tabHeight : 45,
			onSelect : function(title, index) {
				if(title == "<span class='span_tab_title'>시설<br>관리</span>") {
					$('.li_menu_group.collapse').trigger('click');	
				}
			}
		});
	},
	
	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		$(that.selector).show();
		// 화면 크기 변환 시 트리 영역 크기 조절 이벤트 등록
		mainObj.addResizeHandler("jopMenu", function() {
			that.resizeWindowHandler();
		});
		that.resizeWindowHandler();
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		$(that.selector).hide();
		mainObj.removeResizeHandler("jopMenu");
	},
	
	/**
	 * 윈도우 크기 조절 핸들러
	 */
	resizeWindowHandler : function() {
		var that = this;
		var height = $("#div_menu_panel").height();
		var titleHeight = $("> .title", that.selector).height();
		var tagsHeight = $(".tabs-header", that.selector).height();
		var conditionHeight = $(".div_search_condition", that.selector).height();
		var pagenationHeight = $(".div_search_pagination", that.selector).height();
		var offset = 110;
		var contentHeight = height - titleHeight - tagsHeight - conditionHeight - pagenationHeight - offset;
		if(contentHeight < 200) contentHeight = 200;
		$(".ul_search_content", that.selector).height(contentHeight);
	}
		
};

/**
 * 도시공원 검색
 * @type {Object}
 */
cityParkObj.cityParkSearchObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : ".div_city_park",
	
	/**
	 * 부모 객체
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도시공원",
	
	/**
	 * 데이터 아이디
	 * @type {String}
	 */
	dataId : null,
	
	/**
	 * 초기화
	 * @param parent
	 */
	init : function(parent) {
		var that = this;
		that.selector = parent.selector + " " + that.selector;
		that.dataId = parent.DATA_ID;
		
		that.initUi();
		that.loadCl();
		that.loadDong();
		that.bindEvents();
	},
	
	/**
	 * UI 초기환
	 */
	initUi : function() {
		var that = this;
		
		// 구분 목록
		$(".sel_cl", that.selector).combobox({
			valueField : "codeId",
			textField : "codeNm",
			width : 160
		});
		
		// 법정동 목록
		$(".sel_dong", that.selector).combobox({
			valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD),
			width : 160
		});
		
		// 명칭
		$(".txt_name", that.selector).textbox({
			width : 160,
			prompt : "명칭을 입력하십시오."
		});
		
		// 위치
		$(".txt_location", that.selector).textbox({
			width : 160,
			prompt : "위치를 입력하십시오."
		});
		
		// 영역 내 검색 
		$(".switch_within_zone", that.selector).switchbutton({
			onText : "Y",
			offText : "N"
		});
		
		// 검색
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
	},
	
	/**
	 * 분류 불러오기
	 */
	loadCl : function() {
		var that = this;
		var data = domnCodeObj.getData("KWS-0311").slice();
		data.unshift({ "codeId" : "", "codeNm" : "전체" });
		$(".sel_cl", that.selector).combobox("loadData", data);
		$(".sel_cl", that.selector).combobox("setValue", "");
	},
	
	/**
	 * 동 목록 불러오기
	 */
	loadDong : function() {
		var that = this;
		dongObj.getPromise().done(function() {
			var data = dongObj.getData();
			data.unshift({ "bjdCde" : "", "bjdNam" : "전체" });
			$(".sel_dong", that.selector).combobox("loadData", data);
		});
	},
	
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 검색
		$(".a_search", that.selector).click(function() {
			that.search(1);
		});
		
		// 도시공원 선택
		$(".ul_search_content", that.selector).on("click", ".a_content", function() {
			var element = $(this);
			var fid = element.attr("data-id");
			cityParkObj.cityParkResultObj.open(that.dataId, fid);
		});
		
		// 페이징 이동
		$(that.selector).on("click", ".div_search_pagination a", function() {
			var element = $(this);
			var pageIndex = element.attr("data-page-index");
			that.search(pageIndex);
		});
		
		
	},
	
	/**
	 * 검색
	 */
	search : function(pageIndex) {
		var that = this;
		
		var params = {
			pageIndex : pageIndex
		};
		
		// 분류
		var prkCde = $(".sel_cl", that.selector).combobox("getValue");
		// 법정동
		var bjdCde = $(".sel_dong", that.selector).combobox("getValue");
		// 명칭
		var prkNam = $(".txt_name", that.selector).textbox("getValue");
		// 위치 
		var prkLoc = $(".txt_location", that.selector).textbox("getValue");
		// 영역 내 검색
		var withinZone = $(".switch_within_zone", that.selector).switchbutton("options").checked?"Y":"N";
		
		if(prkCde && prkCde != '') {
			params.prkCde = prkCde;
		}
		if(bjdCde && bjdCde != '') {
			params.bjdCde = bjdCde;
		}
		if(prkNam && prkNam != '') {
			params.prkNam = prkNam;
		}
		if(prkLoc && prkLoc != '') {
			params.prkLoc = prkLoc;
		}
		if(withinZone && withinZone == 'Y') {
			var map = mapObj.getMap();
			var from = map.getView().getProjection();
			var to = ol.proj.get(MAP.PROJECTION);
			var extent = spatialUtils.transformExtent(map.getExtent(), from ,to);
			var polygon = ol.geom.Polygon.fromExtent(extent);
			var format = new ol.format.WKT();
			var wkt = format.writeFeature(new ol.Feature(polygon));
			params.wkt = wkt;
		}

		$.get(CONTEXT_PATH + "/cityPark/list.do", params).done(function(response) {
			if(response && response.rows) {
				that.load(response.paginationInfo, response.rows);
			}
		});
	},
	
	/**
	 * 불러오기
	 * @param paginationInfo 페이징정보
	 * @param rows 도시공원 목록
	 */
	load : function(paginationInfo, rows) {
		var that = this;
		if(paginationInfo.totalRecordCount > 0) {
			$(".font_total_count", that.selector).text(paginationInfo.totalRecordCount);
			$(".ul_search_content", that.selector).html(that.createContents(rows));	
			$(".div_search_content_none", that.selector).hide();
			$(".div_search_content_result", that.selector).show();
			that.createSearchPagination(that.selector, paginationInfo);
		}
		else {
			$(".div_search_content_result", that.selector).hide();
			$(".div_search_pagination", that.selector).hide();
			$(".div_search_content_none", that.selector).show();
		}
	},
	
	/**
	 * 내용 생성
	 * @param rows
	 * @returns {String}
	 */
	createContents : function(rows) {
		var that = this;
		var tagStr = "";
		for(var i=0, len=rows.length; i < len; i++) {
			var row = rows[i];
			tagStr += that.createContent(row);
		}
		return tagStr;
	},
	
	createContent : function(row) {
		var title = row.prkNam + "[" + domnCodeObj.getCode("KWS-0311", row.prkCde).codeNm + "]";
		var tagStr = '';
		tagStr += '<li class="li_content">';
		tagStr += '<div class="div_content_title"><a href="#" class="a_content" data-id="' + row.ftrIdn + '" >' + title + '</a></div>';
		tagStr += '<div class="div_content_content">' + row.prkLoc + '</div>';
		tagStr += '</li>';
		return tagStr;
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
		$(".div_search_pagination", selector).show();
	}
		
};

cityParkObj.cityParkSearchUtilsObj = {
		
	TITLE : "도시공원공간검색",
	
	dataId : null,
	
	CLASS_NAME : "CityParkSearchUtils",
	
	search : function(dataFeature){
		var that = this;
		
		var dataId = "CTY_PARK_AS";
		var geometry = dataFeature.getGeometry();
		var mapProjection = mapObj.getMap().getView().getProjection();
		var sysProjection = ol.proj.get(MAP.PROJECTION);
		var transformGeometry = spatialUtils.transform(geometry, mapProjection, sysProjection);
		var format = new ol.format.WKT();
		var wkt = format.writeFeature(new ol.Feature(transformGeometry));
		var filter = {
			filterType : "SPATIAL",
			spatialType : "INTERSECTS",
			wkt : wkt
		};
		
		spatialSearchUtils.listAll(that.TITLE, dataId, filter, function(rows) {
			if(rows && rows.length > 0) {
				var row = rows[0].ftrIdn;
				var url = CONTEXT_PATH + "/cityPark/ctyPksdAs/list.do";
				var params = {ftrIdn : row};
				
				$.get(url, params).done(function(response){
					for(var i=0, len=response.rows.length; i < len; i++){
						if(response && response.rows.length > 0) {
							var ftfIdn = response.rows[0].ftfIdn;
							var ftrIdn = response.rows[0].ftrIdn;
									
							for(var key in windowObj.registerObj.categories) {
								var gridData = $("."+key).propertygrid("getData");
								var gridRows = gridData.rows;
								for(var j=0, jLen=gridRows.length; j < jLen; j++) {
									var gridRow = gridRows[j];
									if(gridRow.column == "FTF_IDN") {
										$("."+key).propertygrid('updateRow',{
											index : j,
											row : {value : ftrIdn}
										});
									}
									else if(gridRow.column == "PPK_IDN") {
										$("."+key).propertygrid('updateRow',{
											index : j,
											row : {value : ftfIdn}
										});
									}
								}
							}
						}
					}
				});
			}	
		});
	}
	
};

cityParkObj.cityParkResultObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "검색 결과 목록 현황",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "CityParkCityParkResult",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 데이터 아이디
	 */
	dataId : null,
	
	/**
	 * 지형지물부호
	 */
	FTR_CDE : "ZT001",
	
	/**
	 * 공원 번호
	 */
	fid : null,
	
	/**
	 * 열기
	 */
	open : function(dataId, fid) {
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		that.dataId = dataId;
		that.fid = fid;
		
		var url = CONTEXT_PATH + "/window/cityPark/cityParkResult/page.do?nocache="+Math.random();
		var windowOptions = {
			width : 600,
			height : 350,
			top : 100,
			collapsible : false,
			draggable : false,
			containerSelector : "#map_container",
			inline : true,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.generalObj.init(that);
			that.landObj.init(that);
			//that.civilAppealObj.init(that);
			//that.changeDetailsObj.init(that);
			that.occupationObj.init(that);
			that.photoObj.init(that);
		});
		that.selector = "#" + id;
	},
	
	/**
	 * 닫기
	 * @returns {Boolean}
	 */
	close : function() {
		var that = this;
		mainObj.removeResizeHandler(that.CLASS_NAME);
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.ftrIdn = null;
		return false;
	},
	
	initUi : function() {
		var that = this;
		
		// 결과창 위치 조절
		$(that.selector).window({
			top : $(window).height() - 350
		});
		
		$(".div_window_city_park_result_tabs", that.selector).tabs({
			tools : that.selector + ' .div_window_city_park_result_tabs_tool'
		});
		
		// 화면 크기 변환 시 결과 목록 영역 크기 조절 이벤트 등록
		mainObj.addResizeHandler(that.CLASS_NAME, function(width, height) {
			that.resizeWindowHandler(width, height);
		});
		
		mainObj.resizeWindowHandler($(window).width(), $(window).height());
		
		//대장조회 버튼
		$(".a_register_inquiry").linkbutton({
			width : 110,			
			onClick : function() {
				
				var facilities = $(".grid_facilities", that.selector).datagrid("getSelected");
				var facility = $(".grid_facility", that.selector).datagrid("getSelected");
				
				if(facility != null){
					var dataId = facility.dataId;
					var fids = [];
					
					// 휴게시설
					if(dataId == "CTY_REST_PS" || dataId == "CTY_REST_LS" || dataId == "CTY_REST_AS" ){
						if(facilities.info.indictTy == "SUMMARY_COUNT") {
							var fieldInfo = dataObj.getDataField(dataId, camelcaseUtils.camelcaseToUnderbar(facilities.info.kindField));
							if(fieldInfo && fieldInfo.domnId) {
								
								var domnId = fieldInfo.domnId;
								var codeData = domnCodeObj.getCodeByValue(domnId, facility.kind);
								var path = camelcaseUtils.underbarToCamelcase(dataId);
								
								var url = CONTEXT_PATH + "/cityPark/getPk/" + path + "/list.do";
								var params = {ftfIdn : facilities.fid, rstCde : codeData.codeId};
								
								$.get(url, params).done(function(response){
									for(var i=0, len=response.rows.length; i < len; i++){
										if(response) {
											fids.push(response.rows[i].objectid);
										} else {
											$.messager.alert(that.TITLE, "조회 대상(시설물)을 선택하십시오.", null);
										}
									}
									if(fids && fids.length > 0) {
										windowObj.registerObj.open(dataId, fids);
									}
								});
							}
						}
						
					// 운동시설	
					} else if(dataId == "CTY_ATHL_PS"){
						if(facilities.info.indictTy == "PKSD") {
							var fieldInfo = dataObj.getDataField(dataId, camelcaseUtils.camelcaseToUnderbar(facilities.info.kindField));
							if(fieldInfo && fieldInfo.domnId) {
								
								var domnId = fieldInfo.domnId;
								var codeData = domnCodeObj.getCode(domnId, facility.kind);
								var path = camelcaseUtils.underbarToCamelcase(dataId);
								
								var url = CONTEXT_PATH + "/cityPark/getPk/" + path + "/list.do";
								var params = {ftfIdn : facilities.fid, athCde : codeData.codeId};
								
								$.get(url, params).done(function(response){
									for(var i=0, len=response.rows.length; i < len; i++){
										if(response) {
											fids.push(response.rows[i].objectid);
										} else {
											$.messager.alert(that.TITLE, "조회 대상(시설물)을 선택하십시오.", null);
										}
									}
									if(fids && fids.length > 0) {
										windowObj.registerObj.open(dataId, fids);
									}
								});
							} 
						}
						
					// 유희시설	
					} else if(dataId == "CTY_PLAY_PS"){
						if(facilities.info.indictTy == "PKSD") {
							var fieldInfo = dataObj.getDataField(dataId, camelcaseUtils.camelcaseToUnderbar(facilities.info.kindField));
							if(fieldInfo && fieldInfo.domnId) {
								
								var domnId = fieldInfo.domnId;
								var codeData = domnCodeObj.getCode(domnId, facility.kind);
								var path = camelcaseUtils.underbarToCamelcase(dataId);
								
								var url = CONTEXT_PATH + "/cityPark/getPk/" + path + "/list.do";
								var params = {ftfIdn : facilities.fid, plyCde : codeData.codeId};
								
								$.get(url, params).done(function(response){
									for(var i=0, len=response.rows.length; i < len; i++){
										if(response) {
											fids.push(response.rows[i].objectid);
										} else {
											$.messager.alert(that.TITLE, "조회 대상(시설물)을 선택하십시오.", null);
										}
									}
									if(fids && fids.length > 0) {
										windowObj.registerObj.open(dataId, fids);
									}
								});
							}
						}
					} 
				} else {
					$.messager.alert(that.TITLE, "조회 대상(시설물)을 선택하십시오.", null);
				}
			}
		});
		
		
		// 대장출력
		$(".a_output_register").linkbutton({
			width : 110,
			iconCls : 'icon-print',
			onClick : function() {
				var dataInfo = dataObj.getDataByDataId(that.dataId);
				var url = CONTEXT_PATH + "/clipreport/" + dataInfo.dataCtgryId + "/" + that.dataId + "/prntng.do?ftrCde=" + that.FTR_CDE + "&ftrIdn=" + that.fid;
				popupUtils.open(url, that.dataId, {
					width : 900,
					height : 800,
					left : 300,
					top : 150
				});
			}
		});
	},
	

	/**
	 * 윈도우 창 변경 핸들러 (결과 창 크기 조절)
	 */
	resizeWindowHandler : function(windowWidth, windowHeight) {
		var that = this;
		
		var navWidth = $("#div_menu").width();
		var width = windowWidth - navWidth;
		
		var left = 0;
		if($("#div_menu_panel").is(":visible")) {
			left = $("#div_menu_panel").width();
			width = width - left;
		}
		
		$(that.selector).window("resize", {
			width : width,
			left : left,
			top : windowHeight - $(that.selector).window("panel").height() - 120
		});
		
		$(".div_window_city_park_result_tabs", that.selector).tabs("resize");
		
		if(that.landObj.isCreated) {
			that.landObj.resizeDatagrid(width);
		}
		if(that.occupationObj.isCreated) {
			that.occupationObj.resizeDatagrid(width);
		}
	},
	
	disableTab : function(title) {
		var that = this;
		$(".div_window_city_park_result_tabs", that.selector).tabs("disableTab", "민원");
	}
		
};

cityParkObj.cityParkResultObj.generalObj = {
		
	selector : null,
	
	TITLE : "도시공원 일반",
	
	facilities : [
	    {
	    	title : "조경시설",
	    	type : "BASIS",
	    	dataIds : ["CTY_SCAP_PS", "CTY_SCAP_LS", "CTY_SCAP_AS"],
	    	indictTy : "SUMMARY_COUNT",
	    	ymdFieldTitle : "설치일자",
	    	kindField : "scpCde",
	    	ymdField : "sttYmd"
	    },
	    {
	    	title : "공원관리시설",
	    	type : "BASIS",
	    	dataIds : ["CTY_FCTL_PS", "CTY_FCTL_LS ", "CTY_FCTL_AS"],
	    	indictTy : "SUMMARY_COUNT",
	    	ymdFieldTitle : "설치일자",
	    	kindField : "ftlCde",
	    	ymdField : "sttYmd"
	    },
	    {
	    	title : "조경화단",
	    	type : "CQL",
	    	dataIds : ["CTY_PKSD_AS"],
	    	indictTy : "PKSD"
	    },
	    {
	    	title : "교목",
	    	type : "BASIS",
	    	dataIds : ["CTY_TREE_PS"],
	    	indictTy : "SUMMARY_SUM",
	    	ymdFieldTitle : "식재일자",
	    	kindField : "treCde",
	    	ymdField : "treYmd",
	    	quantityField : "treNum"
	    },
	    {
	    	title : "도로 및 광장",
	    	type : "BASIS",
	    	dataIds : ["CTY_SQAR_AS"],
	    	indictTy : "BASIS"
	    },
	    /*{
	    	title : "공원등",
	    	type : "BASIS",
	    	dataIds : ["CTY_STLT_PS"],
	    	indictTy : "BASIS"
	    },*/
	    {
	    	title : "운동시설",
	    	type : "BASIS_PKSD",
	    	dataIds : ["CTY_ATHL_PS"],
	    	indictTy : "PKSD",
	    	kindDomnId : "KWS-0314",
	    	kindField : "athCde",
	    	path : "athl"
	    },
	    {
	    	title : "유희시설",
	    	type : "BASIS_PKSD",
	    	sumField : "plyNum",
	    	dataIds : ["CTY_PLAY_PS"],
	    	indictTy : "PKSD",
	    	kindDomnId : "KWS-0315",
	    	kindField : "plyCde",
	    	path : "play"
	    },
	    {
	    	title : "휴게시설",
	    	type : "BASIS",
	    	sumField : "rstNum",
	    	dataIds : ["CTY_REST_PS", "CTY_REST_LS", "CTY_REST_AS"],
	    	indictTy : "SUMMARY_COUNT",
	    	ymdFieldTitle : "설치일자",
	    	kindField : "rstCde",
	    	ymdField : "sttYmd"
	    },
	    {
	    	title : "편익시설",
	    	type : "BASIS",
	    	sumField : "cnvNum",
	    	dataIds : ["CTY_CONV_PS", "CTY_CONV_LS", "CTY_CONV_AS"],
	    	indictTy : "SUMMARY_COUNT",
	    	ymdFieldTitle : "설치일자",
	    	kindField : "cnvCde",
	    	ymdField : "sttYmd"
	    }
	    /*
	     * 기타시설 삭제 
	    ,{
	    	title : "기타시설",
	    	type : "BASIS",
	    	sumField : "etcLen",
	    	dataIds : ["CTY_ETCT_PS", "CTY_ETCT_LS", "CTY_ETCT_AS"],
	    	indictTy : "SUMMARY_COUNT",
	    	ymdFieldTitle : "설치일자",
	    	kindField : "etcCde",
	    	ymdField : "sttYmd"
	    }*/
	],
	
	init : function(parent) {
		var that = this;
		that.parent = parent;
		that.selector = parent.selector + " .div_general";
		that.initUi();
		that.load(parent.dataId, parent.fid);
	},
	
	initUi : function() {
		var that = this;
		
		$(that.selector).layout();
		$(".div_facility", that.selector).layout();
		// 레이아웃 센터 창이 변경되면 그리드 크기 변경
		$(".div_facility", that.selector).layout("panel", "center").panel({
			onResize : function(width, height) {
				that.resizeDatagrid(width, height);
			}
		});
	
		// 일반 정보
		$(".grid_general", that.selector).datagrid({
			columns : [[
			    {
			    	field : "section",
			    	title : "구분",
				    width : 140
			    },
			    {
			    	field : "attribute",
			    	title : "속성",
				    width : 260
			    }
			]],
			fitColumns : true,
			singleSelect : true
		});
		
		// 공원 시설 전체 목록
		$(".grid_facilities", that.selector).datagrid({
			scrollbarSize : 0,
			columns : [[
			    {
			    	field : "facility",
			    	title : "시설종류",
				    width : 120
			    },
			    {
			    	field : "quantity",
			    	title : "수량",
				    width : 60,
					formatter : function(value) {
						return numberUtils.formatCurrency(value);
					}
			    }
			]],
			fitColumns : true,
			singleSelect : true,
			onSelect : function(index, row) {
				
				that.createRegisterInquiryButton(row.info);
				that.createGrid(row.info, row.fid);
			}
		});
		
	},

	/**
	 * 그리드 크기 변경
	 * 
	 * @param {Number}
	 *            width 너비
	 * @param {Number}
	 *            height 높이
	 */
	resizeDatagrid : function(width, height) {
		var that = this;
		$(".grid_facility", that.selector).datagrid("resize", {
			width : "100%"
		});
	},
	
	load : function(dataId, fid) {
		var that = this;
		
		// 일반 정보
		spatialSearchUtils.selectOne(that.TITLE, dataId, fid, null, function(data) {
			var gridData = [];
			var dataInfo = dataObj.getDataByDataId(dataId, true);
			var dataFields = dataInfo.kwsDataFields;
			for(var i=0, len=dataFields.length; i < len; i++) {
				var dataField = dataFields[i];
				var indictTy = dataField.indictTy;
				if (indictTy != "HIDE" && indictTy != "WKT") {
					var field = camelcaseUtils.underbarToCamelcase(dataField.fieldId);
					var value = data[field];
					gridData.push({
						section : dataField.fieldAlias,
						attribute : value
					});
				}
			}
			$(".grid_general", that.selector).datagrid("loadData", gridData);
			
			var format = new ol.format.WKT();
			var feature = format.readFeature(data[MAP.GEOMETRY_ATTR_NAME]);
			highlightObj.moveFeatures([feature], { offsetHeight : $(that.selector).height()/2 });
		});
		
		// 시설물정보
		var dataIds = [];
		for(var i=0, len=that.facilities.length; i < len; i++) {
			if(that.facilities[i].type == "BASIS") {
				dataIds = dataIds.concat(that.facilities[i].dataIds);
			}
		}
		var filter = {
			filterType : "COMPARISON",
			comparisonType : "EQUAL_TO",
			property : "FTF_IDN",
			value : fid			
		};
		var deferred = spatialSearchUtils.searchSummaries(that.TITLE, dataIds, filter, null, { isNoMessage : true });
		
		// 공원구역
		var pksdDataIds = [];
		for(var i=0, len=that.facilities.length; i < len; i++) {
			if(that.facilities[i].type == "BASIS_PKSD") {
				pksdDataIds = pksdDataIds.concat(that.facilities[i].dataIds);
			}
		}
		var filter = {
			filterType : "COMPARISON",
			comparisonType : "EQUAL_TO",
			property : "PPK_IDN",
			value : fid			
		};
		var pksdDeferred = spatialSearchUtils.searchSummaries(that.TITLE, pksdDataIds, filter, null, { isNoMessage : true });
		
		// 조경 화단
		var cql = "FTF_IDN = " + fid + " AND SDL_CDE = 'SDL003'";
		var cqlFilter = {
			filterType : "CQL",
			cql : cql
		};
		var scapDeferred = spatialSearchUtils.searchSummaries(that.TITLE, "CTY_PKSD_AS", cqlFilter, null, { isNoMessage : true });
		
		$.when(deferred, pksdDeferred, scapDeferred).done(function(basis, pksd, scap) {
			var result = [];
			if(basis && basis.length > 0 && basis[0].rows && basis[0].rows.length > 0) {
				result = result.concat(basis[0].rows);
			}
			if(pksd && pksd.length > 0 && pksd[0].rows && pksd[0].rows.length > 0) {
				result = result.concat(pksd[0].rows);
			}
			if(scap && scap.length > 0 && scap[0].rows && scap[0].rows.length > 0) {
				result = result.concat(scap[0].rows);
			}
			
			var facDatas = [];
			for(var i=0, len=that.facilities.length; i < len; i++) {
		    	var facility = that.facilities[i];
				var obj = {
	    			facility : facility.title,
	    			quantity : 0,
	    			info : facility,
	    			fid : fid
				};
				
				for(var j=0, jLen=facility.dataIds.length; j < jLen; j++) {
					for(var k=0, kLen=result.length; k < kLen; k++) {
						if(facility.dataIds[j] == result[k].dataId) {
							obj.quantity += result[k].ids.length;
							break;
						}
					}
				}
				
				if(obj.quantity > 0) {
					facDatas.push(obj);
				}
			}
			$(".grid_facilities", that.selector).datagrid("loadData", facDatas);
			$(".grid_facilities", that.selector).datagrid("selectRow", 0);
		});
	},
	
	// 도시공원 검색결과 목록현황 대장조회버튼 활성화(운동시설, 유희시설, 휴게시설_점,선,면)/ 버튼 비활성화 
	createRegisterInquiryButton : function(info){
		
		if(info.title == '운동시설' || info.title == '유희시설' || info.title == '휴게시설') {
			$(".a_register_inquiry").linkbutton('enable');
		} else {
			$(".a_register_inquiry").linkbutton('disable');
		}
		
	},
	
	createGrid : function(info, fid) {
		var that = this;
		
		// 시설물 (조경하단 외)
		if(info.indictTy == "BASIS") {
			if(info.dataIds && info.dataIds.length > 0) {
				var dataId = info.dataIds[0];
				$(".grid_facility", that.selector).datagrid(cityParkUtils.createGridOptions(dataId));
			
				var filter = {
					filterType : "COMPARISON",
					comparisonType : "EQUAL_TO",
					property : "FTF_IDN",
					value : fid			
				};
				spatialSearchUtils.search(that.TITLE, info.dataIds, filter, function(result) {
					var rows = [];
					if(result && result.length > 0) {
						var id = 1;
						for(var i=0, len=result.length; i < len; i++) {
							for(var j=0, jLen=result[i].rows.length; j < jLen; j++) {
								result[i].rows[j].id = id++;
							}
							rows = rows.concat(result[i].rows);
						}
					}

					var gridData = {
						total : rows.length,
						rows : rows
					};
					
					$(".grid_facility", that.selector).datagrid("loadData", gridData);
				}, { isNoMessage : true });
			}
		}
		else if(info.indictTy == "SUMMARY_COUNT") {
			if(info.dataIds && info.dataIds.length > 0) {
				$(".grid_facility", that.selector).datagrid({
					frozenColumns : [[{
						field : "id",
						title : "번호",
						width : 100,
						sortable : true
					}]],
					columns : [[
					    {
					    	field : "kind",
							title : "종류",
							width : 100,
							sortable : false,
							formatter : function(value, row) {
								var domains = domnCodeObj.getData("KWS-0319").slice();
								for(var i=0, len=domains.length; i < len; i++) {
									if(domains[i].codeId == value) {
										return domains[i].codeNm;
									}
								}
								return value;
							}
					    },
					    {
					    	field : "ymd",
					    	title : info.ymdFieldTitle,
					    	width : 100,
					    	sortable : false
					    },
					    {
					    	field : "quantity",
							title : "수량",
							width : 100,
							sortable : false,
							formatter : function(value) {
								return numberUtils.formatCurrency(value);
							}
					    }
					]],
					singleSelect : true,
					fitColumns : true
				});
				
				var filter = {
					filterType : "COMPARISON",
					comparisonType : "EQUAL_TO",
					property : "FTF_IDN",
					value : fid			
				};
				spatialSearchUtils.search(that.TITLE, info.dataIds, filter, function(result) {
					var rows = [];
					if(result && result.length > 0) {
						var kinds = {};
						for(var i=0, len=result.length; i < len; i++) {
							for(var j=0, jLen=result[i].rows.length; j < jLen; j++) {
								var kind = result[i].rows[j][info.kindField];
								var ymd = result[i].rows[j][info.ymdField]?result[i].rows[j][info.ymdField]:"";
								
								var id = kind + "_" + ymd;
								if(!kinds[id]) {
									kinds[id] = {
										dataId : result[i].dataId,
										kind : kind,
										ymd : ymd,
										quantity : 0
									};
								}
								kinds[id].quantity++;
							}
						}
						
						var index = 0;
						for(var kind in kinds) {
							rows.push({
								id : ++index,
								dataId : kinds[kind].dataId,
								kind : kinds[kind].kind,
								ymd : kinds[kind].ymd,
								quantity : kinds[kind].quantity
							});
						}
					}

					var gridData = {
						total : rows.length,
						rows : rows
					};
					$(".grid_facility", that.selector).datagrid("loadData", gridData);
				}, { isNoMessage : true });
				
			}
		}
		else if(info.indictTy == "SUMMARY_SUM") {
			if(info.dataIds && info.dataIds.length > 0) {
				$(".grid_facility", that.selector).datagrid({
					frozenColumns : [[{
						field : "id",
						title : "번호",
						width : 100,
						sortable : true
					}]],
					columns : [[
					    {
					    	field : "kind",
							title : "종류",
							width : 100,
							sortable : false,
							formatter : function(value, row) {
								var domains = domnCodeObj.getData("KWS-0319").slice();
								for(var i=0, len=domains.length; i < len; i++) {
									if(domains[i].codeId == value) {
										return domains[i].codeNm;
									}
								}
								return value;
							}
					    },
					    {
					    	field : "ymd",
					    	title : info.ymdFieldTitle,
					    	width : 100,
					    	sortable : false
					    },
					    {
					    	field : "quantity",
							title : "수량",
							width : 100,
							sortable : false
					    }
					]],
					singleSelect : true,
					fitColumns : true,
					showFooter : false
				});
				
				var filter = {
					filterType : "COMPARISON",
					comparisonType : "EQUAL_TO",
					property : "FTF_IDN",
					value : fid			
				};
				spatialSearchUtils.search(that.TITLE, info.dataIds, filter, function(result) {
					var rows = [];
					if(result && result.length > 0) {
						var kinds = {};
						for(var i=0, len=result.length; i < len; i++) {
							for(var j=0, jLen=result[i].rows.length; j < jLen; j++) {
								var kind = result[i].rows[j][info.kindField];
								var ymd = result[i].rows[j][info.ymdField]?result[i].rows[j][info.ymdField]:"";
								var quantity = result[i].rows[j][info.quantityField];
								var id = kind + "_" + ymd;
								if(!kinds[id]) {
									kinds[id] = {
										kind : kind,
										ymd : ymd,
										quantity : 0
									};
								}
								kinds[id].quantity += quantity;
								
							}
						}
						
						var index = 0;
						for(var kind in kinds) {
							rows.push({
								id : ++index,
								kind : kinds[kind].kind,
								ymd : kinds[kind].ymd,
								quantity : kinds[kind].quantity
							});
						}
					}

					var gridData = {
						total : rows.length,
						rows : rows
					};
					$(".grid_facility", that.selector).datagrid("loadData", gridData);
				}, { isNoMessage : true });
				
			}
		}
		// 공원 구역 
		else if(info.indictTy == "PKSD") {
			if(info.dataIds && info.dataIds.length > 0) {
				var dataId = info.dataIds[0];
				// 조경화단
				if(dataId == "CTY_PKSD_AS") {
					$(".grid_facility", that.selector).datagrid({
						frozenColumns : [[{
							field : "id",
							title : "번호",
							width : 100,
							sortable : true
						}]],
						columns : [[
						    {
								field : "sttYmd",
								title : "설치일자",
								width : 100,
								sortable : false
						    },
						    {
						    	field : "grdAra",
								title : "면적(㎡)",
								width : 100,
								sortable : false
						    },
						    {
						    	field : "flrCde",
								title : "잔디유무",
								width : 100,
								sortable : false,
								formatter : function(value, row) {
									if(value) {
										if(value == "FLR003") {
											return "유";
										}
										else {
											return "무";
										}
									}
									else {
										return "";
									}
									
								}
						    },
						    {
						    	field : "fenCde",
								title : "펜스유무",
								width : 100,
								sortable : false,
								formatter : function(value, row) {
									if(value) {
										if(value == "YON002") {
											return "유";
										}
										else {
											return "무";
										}
									}
									else {
										return "";
									}
								}
						    },
						    {
						    	field : "kind",
								title : "식재 관목",
								width : 100,
								sortable : false,
								formatter : function(value, row) {
									var domains = domnCodeObj.getData("KWS-0319").slice();
									for(var i=0, len=domains.length; i < len; i++) {
										if(domains[i].codeId == value) {
											return domains[i].codeNm;
										}
									}
									return value;
								}
						    },
						    {
						    	field : "quantity",
								title : "수량",
								width : 100,
								sortable : false,
								formatter : function(value) {
									return numberUtils.formatCurrency(value);
								}
						    }
						]],
						singleSelect : true,
						showFooter : true,
						fitColumns : true
					});
					
					var url = CONTEXT_PATH + "/cityPark/zone/scap/list.do";
					var params = { ftfIdn : fid };
					$.get(url, params).done(function(result) {
						var index = 0;
						var rows = [];
						var merges = [];
						
						var sum = 0;
						for(var i=0, len=result.rows.length; i < len; i++) {
							var row = result.rows[i];
							var sttYmd = row.sttYmd;	// 설치 일자
							var grdAra = row.grdAra;	// 면적
							var flrCde = row.flrCde;	// 바닥재질
							var fenCde = row.fenCde; 	// 펜스유무
							
							merges.push({ index : index, rowspan : row.cityParkFacilityResults.length});
							for(var j=0, jLen=row.cityParkFacilityResults.length; j < jLen; j++) {
								var kind = row.cityParkFacilityResults[j].kind;
								var quantity = row.cityParkFacilityResults[j].quantity;
								
								rows.push({
									id : (i+1),
									sttYmd : sttYmd,
									grdAra : grdAra,
									flrCde : flrCde,
									fenCde : fenCde,
									kind : kind,			// 식재관목종류
									quantity : quantity		// 수량
								});
								index++;
								
								if(!isNaN(quantity)) {
									sum += parseInt(quantity);
								}
							}
						}
						
						var gridData = {
							total : rows.length,
							rows : rows,
							footer : [{ id : "소계", quantity : sum }]
						};
						$(".grid_facility", that.selector).datagrid("loadData", gridData);
						
						/// 셀 병합
						for(var i=0, len=merges.length; i < len; i++) {
							var mergeFields = ["id", "sttYmd", "grdAra", "flrCde", "fenCde"];
							for(var j=0, jLen=mergeFields.length; j < jLen; j++) {
								$(".grid_facility", that.selector).datagrid("mergeCells", {
									index : merges[i].index,
									field : mergeFields[j],
									rowspan : merges[i].rowspan
								});
							}
						}
					});
				}
				else {
					$(".grid_facility", that.selector).datagrid({
						frozenColumns : [[{
							field : "id",
							title : "번호",
							width : 100,
							sortable : true
						}]],
						columns : [[
							{
								field : "flrCde",
								title : "바닥재질",
								width : 100,
								sortable : false
							},
						    {
								field : "kind",
								title : "시설종류",
								width : 100,
								sortable : false,
								formatter : function(value, row) {
									var domains = domnCodeObj.getData(info.kindDomnId).slice();
									for(var i=0, len=domains.length; i < len; i++) {
										if(domains[i].codeId == value) {
											return domains[i].codeNm;
										}
									}
									return value;
								}
						    },
						    {
								field : "dt",
								title : "설치일자",
								width : 100,
								sortable : false
						    },
						    {
						    	field : "quantity",
								title : "수량",
								width : 100,
								sortable : false,
								formatter : function(value) {
									return numberUtils.formatCurrency(value);
								}
						    }
						]],
						singleSelect : true,
						showFooter : false,
						fitColumns : true
					});
					
					var url = CONTEXT_PATH + "/cityPark/zone/" + info.path + "/list.do";
					var params = { ftfIdn : fid };
					$.get(url, params).done(function(result) {
						var index = 0;
						var rows = [];
						var merges = [];
						
						for(var i=0, len=result.rows.length; i < len; i++) {
							var row = result.rows[i];
							var flrCde = row.flrCde;	// 바닥 재질
							merges.push({ index : index, rowspan : row.cityParkFacilityResults.length});
							for(var j=0, jLen=row.cityParkFacilityResults.length; j < jLen; j++) {
								var kind = row.cityParkFacilityResults[j].kind;
								var dt = row.cityParkFacilityResults[j].dt;
								var quantity = row.cityParkFacilityResults[j].quantity;
								rows.push({
									dataId : dataId,
									id : (i+1),
									flrCde : domnCodeObj.getCode("KWS-0324", flrCde).codeNm,
									kind : kind,	// 시설종류
									dt : dt, 		// 설치일자
									quantity : quantity		// 수량
								});
								index++;
							}
						}
						
						var gridData = {
							total : rows.length,
							rows : rows
						};
						$(".grid_facility", that.selector).datagrid("loadData", gridData);
						
						/// 셀 병합
						for(var i=0, len=merges.length; i < len; i++) {
							var mergeFields = ["id", "flrCde"];
							for(var j=0, jLen=mergeFields.length; j < jLen; j++) {
								$(".grid_facility", that.selector).datagrid("mergeCells", {
									index : merges[i].index,
									field : mergeFields[j],
									rowspan : merges[i].rowspan
								});
							}
						}
					});
				}
			}
		}
		
	}

};

cityParkObj.cityParkResultObj.landObj = {
		
	selector : null,
	
	parent : null,
	
	DATA_ID : "CTY_OWNR_HT",
	
	isCreated : false,
	
	init : function(parent) {
		var that = this;
		that.parent = parent;
		that.selector = parent.selector + " .div_land";
		that.initUi();
		that.load(parent.fid);
		that.isCreated = true;
	},
	
	initUi : function() {
		var that = this;
		$(".grid_land", that.selector).datagrid(cityParkUtils.createGridOptions(that.DATA_ID));
	},
	
	load : function(fid) {
		var that = this;
		var filter = {
			filterType : "COMPARISON",
			comparisonType : "EQUAL_TO",
			property : "FTF_IDN",
			value : fid			
		};
		spatialSearchUtils.search(that.TITLE, that.DATA_ID, filter, function(result) {
			var rows = [];
			if(result && result.length > 0) {
				var id = 1;
				for(var i=0, len=result.length; i < len; i++) {
					for(var j=0, jLen=result[i].rows.length; j < jLen; j++) {
						result[i].rows[j].id = id++;
					}
					rows = rows.concat(result[i].rows);
				}
			}
			var gridData = {
				total : rows.length,
				rows : rows
			};
			$(".grid_land", that.selector).datagrid("loadData", gridData);
		}, { isNoMessage : true });
	},
	
	resizeDatagrid : function(width, height) {
		var that = this;
		$(".grid_land", that.selector).datagrid("resize", {
			width : "100%"
		});
	}
		
};

cityParkObj.cityParkResultObj.civilAppealObj = {
		
	selector : null,
	
	parent : null,
	
	DATA_ID : "CTY_RSER_MA",
	
	init : function(parent) {
		var that = this;
		that.parent = parent;
		that.selector = parent.selector + " .div_civil_appeal";
		that.initUi();
		that.load(parent.fid);
	},
	
	initUi : function() {
		var that = this;
		$(".grid_civil_appeal", that.selector).datagrid(cityParkUtils.createGridOptions(that.DATA_ID));
	},
	
	load : function(fid) {
		var that = this;
		var filter = {
			filterType : "COMPARISON",
			comparisonType : "EQUAL_TO",
			property : "FTF_IDN",
			value : fid			
		};
		spatialSearchUtils.search(that.TITLE, that.DATA_ID, filter, function(result) {
			var rows = [];
			if(result && result.length > 0) {
				var id = 1;
				for(var i=0, len=result.length; i < len; i++) {
					for(var j=0, jLen=result[i].rows.length; j < jLen; j++) {
						result[i].rows[j].id = id++;
					}
					rows = rows.concat(result[i].rows);
				}
			}
			var gridData = {
				total : rows.length,
				rows : rows
			};
			$(".grid_civil_appeal", that.selector).datagrid("loadData", gridData);
		}, { isNoMessage : true });
	}
		
};

cityParkObj.cityParkResultObj.changeDetailsObj = {
		
	selector : null,
	
	parent : null,
	
	DATA_ID : "CTY_PARK_HT",
	
	init : function(parent) {
		var that = this;
		that.parent = parent;
		that.selector = parent.selector + " .div_change_details";
		that.initUi();
		that.load(parent.fid);
	},
	
	initUi : function() {
		var that = this;
		$(".grid_change_details", that.selector).datagrid(cityParkUtils.createGridOptions(that.DATA_ID));
	},
	
	load : function(fid) {
		var that = this;
		var filter = {
			filterType : "COMPARISON",
			comparisonType : "EQUAL_TO",
			property : "FTF_IDN",
			value : fid			
		};
		spatialSearchUtils.search(that.TITLE, that.DATA_ID, filter, function(result) {
			var rows = [];
			if(result && result.length > 0) {
				var id = 1;
				for(var i=0, len=result.length; i < len; i++) {
					for(var j=0, jLen=result[i].rows.length; j < jLen; j++) {
						result[i].rows[j].id = id++;
					}
					rows = rows.concat(result[i].rows);
				}
			}
			var gridData = {
				total : rows.length,
				rows : rows
			};
			$(".grid_change_details", that.selector).datagrid("loadData", gridData);
		}, { isNoMessage : true });
	}
		
};

cityParkObj.cityParkResultObj.occupationObj = {
		
	selector : null,
	
	parent : null,
	
	DATA_ID : "CTY_OCUP_PS",
	
	isCreated : false,
	
	init : function(parent) {
		var that = this;
		that.parent = parent;
		that.selector = parent.selector + " .div_occupation";
		that.initUi();
		that.load(parent.fid);
		that.isCreated = true;
	},
	
	initUi : function() {
		var that = this;
		$(".grid_occupation", that.selector).datagrid(cityParkUtils.createGridOptions(that.DATA_ID));
	},
	
	load : function(fid) {
		var that = this;
		var filter = {
			filterType : "COMPARISON",
			comparisonType : "EQUAL_TO",
			property : "FTF_IDN",
			value : fid			
		};
		spatialSearchUtils.search(that.TITLE, that.DATA_ID, filter, function(result) {
			var rows = [];
			if(result && result.length > 0) {
				var id = 1;
				for(var i=0, len=result.length; i < len; i++) {
					for(var j=0, jLen=result[i].rows.length; j < jLen; j++) {
						result[i].rows[j].id = id++;
					}
					rows = rows.concat(result[i].rows);
				}
			}
			var gridData = {
				total : rows.length,
				rows : rows
			};
			$(".grid_occupation", that.selector).datagrid("loadData", gridData);
		}, { isNoMessage : true });
	},
	
	resizeDatagrid : function(width, height) {
		var that = this;
		$(".grid_occupation", that.selector).datagrid("resize", {
			width : "100%"
		});
	}
		
};

cityParkObj.cityParkResultObj.photoObj = {
		
	selector : null,
	
	parent : null,
	
	init : function(parent) {
		var that = this;
		that.parent = parent;
		that.selector = parent.selector + " .div_photo";
		
		that.ftrCde = that.parent.FTR_CDE;
		that.ftrIdn = that.parent.fid;
		
		that.initUi();
		that.load();
	},
	
	initUi : function() {
		var that = this;
		
		$(".a_registration", that.selector).linkbutton({
			width : 100,
			iconCls : "icon-add",
			onClick : function() {
				windowObj.photoManageObj.add.open(that.ftrCde, that.ftrIdn, function() {
					that.load();
				});
			}
		});
	},
	
	load : function() {
		var that = this;
		var url = CONTEXT_PATH + "/photoManage/" + that.ftrCde + "/" + that.ftrIdn + "/list.do?nocache="+Math.random();
		$.get(url).done(function(response) {
			var tagStr = '';
			for(var i=0, len=response.rows.length; i < len; i++) {
				var row = response.rows[i];
				
				tagStr += '<li data-image-no="' + row.imageNo + '">';
				tagStr += '<div>';
				tagStr += '<img src="' + CONTEXT_PATH + '/cmmn/image/' + row.imageNo + '/thumbnail.do?nocache='+Math.random() + ' alt="' + row.imageSj + '" />';
				tagStr += '</div>';
				tagStr += '<div>';
				tagStr += row.imageSj;
				tagStr += '</div>';
				tagStr += '</li>';
			}
			$(".ul_photo", that.selector).html(tagStr);
			
			$(".ul_photo li", that.selector).click(function() {
				var node = $(this);
				var imageNo = node.attr("data-image-no");
				windowObj.photoManageObj.view.open(imageNo, that.ftrCde, that.ftrIdn, function() {
					that.load();
				});
			});
		}).fail(function() {
			$.messager.alert("사진정보를 가져오는데 실패했습니다.");
		});
		
	}
		
};
