/**
 * 점용대장 검색결과 객체
 * 
 * @type {Object}
 */
var ocpatRegResultObj = {

	/**
	 * 선택자
	 * 
	 * @type {String}
	 */
	selector : "#div_ocpat_reg_result",

	/**
	 * 제목
	 * 
	 * @type {String}
	 */
	TITLE : "점용허가 검색 현황",

	/**
	 * 상세 정보 데이터 그리드 옵션 목록
	 * 
	 * @type {Object}
	 */
	gridOptions : {},

	/**
	 * 데이터 - 현재 선택된 데이터에 포함되는 정보 (데이터 아이디, 레코드 아이디 목록 등)
	 * 
	 * @type {Object}
	 */
	data : null,
	
	dataFields : {},

	/**
	 * 정렬 항목
	 * 
	 * @type {String}
	 */
	sort : null,

	/**
	 * 정렬 순서
	 * 
	 * @type {String}
	 */
	ordr : null,

	/**
	 * 지도 고정 여부
	 */
	isFixed : false,

	/**
	 * 페이지 번호
	 */
	pageNumber : 1,

	/**
	 * 페이지 사이즈
	 */
	pageSize : 10,
	

	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.initUi();
		that.bindEvents();
	},

	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;

		// 결과창 위치 조절
		$("#div_ocpat_reg_result").window({
			top : $(window).height() - 400
		});

		// 검색 결과 목록 데이터 그리드 생성
		$(".grid_result_layers", that.selector).datagrid({
			columns : [ [ {
				field : "ocpatIdn",
				hidden : true
			}, {
				field : "ocpatAlias",
				title : "검색 내용",
				width : 198
			}, {
				field : "totalCount",
				title : "건 수",
				width : 100
			} ] ],
			fitColumns : true,
			singleSelect : true,
			onSelect : function(rowIndex, rowData) {
				that.selectData(rowData);
			}
		});

		// 상세 정보 데이터 그리드 생성
		$(".grid_result_detail", that.selector).datagrid({
			pagination : true,
			ctrlSelect : true,
			onSortColumn : function(sort, ordr) {
				that.sort = sort;
				that.ordr = ordr;
				that.sortOrdr();
			},
			onSelect : function(rowIndex, rowData) {
				that.showFeatures();
			},
			onCheckAll : function(rowIndex, rowData) {
				that.showFeatures();
			},
			onUnselect : function(rowIndex, rowData) {
				that.showFeatures();
			},
			onUncheckAll : function(rowIndex, rowData) {
				that.showFeatures();
			}
		});

		// 레이아웃 센터 창이 변경되면 그리드 크기 변경
		$(".div_result_layout", that.selector).layout("panel", "center").panel(
			{
				onResize : function(width, height) {
					that.resizeDatagrid(width, height);
				}
			});

	},

	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;

		// 항목 설정
		$(".a_setting_item", that.selector).click(function() {
			that.settingItem();
			return false;
		});


		// 목록 위치
		$(".a_move_location", that.selector).click(function() {
			that.moveLocation();
			return false;
		});
		
		// 대장 일괄출력
		$(".a_batchPrint_ocpat", that.selector).off();
		$(".a_batchPrint_ocpat", that.selector).click(function() {
			that.batchPrintOcpatpnt();
			return false;
		});

		// 대장 조회
		$(".a_search_register", that.selector).click(function() {
			that.searchRegister();
			return false;
		});

		
		// 지도 고정
		$(".a_export_fix", that.selector).click(function(event) {
			var element = $(this);
			that.toggleFixed(element);
			event.preventDefault();
		});
	},

	/**
	 * 높이 반환
	 * 
	 * @returns 높이
	 */
	getHeight : function() {
		var that = this;
		var height = 0;
		if ($(that.selector).is(":visible")) {
			height += $(that.selector).height();
		}
		return height;
	},

	getDataPkColumn : function() {
		var that = this;
		return that.gridOptions[that.data.ocpatIdn].pk;
	},

	/**
	 * 윈도우 창 변경 핸들러 (결과 창 크기 조절)
	 */
	resizeWindowHandler : function(windowWidth, windowHeight) {
		var that = this;

		var navWidth = $("#div_menu").width();
		var width = windowWidth - navWidth;

		var left = 0;
		if ($("#div_menu_panel").is(":visible")) {
			left = $("#div_menu_panel").width();
			width = width - left;
		}

		$(that.selector).window("resize",
			{
				width : width,
				left : left,
				top : windowHeight - $(that.selector).window("panel").height() - 120
			}
		);
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

		$(".grid_result_layers", that.selector).datagrid("resize", {
			width : "100%",
			height : height - 35
		});

		$(".grid_result_detail", that.selector).datagrid("resize", {
			width : "100%",
			height : height - 80
		});

	},

	/**
	 * 목록 추가
	 * 
	 * @param data
	 */
	addRows : function(data) {
		var that = this;
		
		// 기존목록 있을시 닫고 실행
		that.close();

		var gridData = $(".grid_result_layers", that.selector).datagrid("getData");
		var rows = gridData.rows;

		// 중복 검사
		var isExist = false;
		for (var i = 0, len = data.length; i < len; i++) {
			for (var j = 0, jLen = rows.length; j < jLen; j++) {
				if (data[i].ocpatIdn == rows[j].ocpatIdn) {
					isExist = true;
					break;
				}
			}
		}

		// 중복된 데이터(KWS_OCPAT_REG)가 있는 경우 선택창 표시
		if (isExist) {
			windowObj.duplicationConfirmObj.open({
				onSubmit : function(mode) {
					if (mode == "new") {
						that.open(data);
					} else if (mode == "append") {
						that.appendRows(data);
					}
				}
			});
		} else {
			that.appendRows(data);
		}
	},

	/**
	 * 목록 추가 - 중복된 데이터 아이디(KWS_OCPAT_REG)가 있는 경우
	 * 
	 * @param data
	 */
	appendRows : function(data) {
		var that = this;
		var gridData = $(".grid_result_layers", that.selector).datagrid("getData");
		var rows = gridData.rows;
		
		for (var i = 0, len = rows.length; i < len; i++) {
			for (var j = data.length - 1; j >= 0; j--) {
				// 같은 대장이 있을 경우
				if (rows[i].ocpatIdn == data[j].ocpatIdn) {
					for (var k = rows[i].featureIds.length; k >= 0; k--) {
						for (var l = data[j].featureIds.length; l >= 0; l--) {
							// 같은 레이어가 있을 경우 아이디 목록을 합친 후 중복 제거
							if (rows[i].featureIds[k].dataId == data[j].featureIds[l].dataId) {
								rows[i].featureIds[k].ids = that.mergeIds(rows[i].featureIds[k].ids, data[j].featureIds[l].ids);
								data[j].featureIds.splice(l, 1);
								break;
							}
						}
					}
					
					if (data[j].featureIds.length == 0)
						data.splice(j, 1);
				}
			}
		}
		
		// 새로운 데이터 추가
		for (var i = 0, len = data.length; i < len; i++) {
			rows.push(data[i]);
		}
		that.open(rows);
	},

	/**
	 * ID 목록을 합친 후 중복 제거
	 * 
	 * @param {Array.
	 *            <Number>} ids 아이디 목록
	 * @param {Array.
	 *            <Number>} appendIds 추가할 아이디 목록
	 * @returns {Array.<Number>} 배열
	 */
	mergeIds : function(ids, appendIds) {
		var result = ids.concat(appendIds).filter(function(item, index, array) {
			return index == array.indexOf(item);
		});
		return result;
	},

	/**
	 * 열기
	 * 
	 * @param {Array.
	 *            <Object>} data 결과 데이터 목록
	 */
	open : function(data) {
		var that = this;

		that.close();
		
		// 데이터 목록 그리드 생성
		var rows = [];
		for (var i = 0, len = data.length; i < len; i++) {
			var obj = data[i];
			var totCnt = 0;
			for (var j = 0; j < obj.featureIds.length; j++) {
				if (obj.featureIds[j].ids) 
					totCnt += obj.featureIds[j].ids.length;
			}
		
			if (totCnt > 0) {
				var row = {
						ocpatIdn : obj.ocpatIdn,
						ocpatAlias : obj.ocpatAlias,
						totalCount : totCnt,
						featureIds : obj.featureIds
				};
				rows.push(row);
			}	
			else {
				var obj = data[i];
				var row = {
						ocpatIdn : obj.ocpatIdn,
						ocpatAlias : obj.ocpatAlias,
						totalCount : totCnt,
						featureIds : obj.featureIds
				};
				rows.push(row);
			}
		}
		
		var gridData = {
			total : rows.length,
			rows : rows
		};
		
		$(".grid_result_layers", that.selector).datagrid("loadData", gridData);

		// 첫 번째 목록 선택
		if (rows.length > 0) {
			$(".grid_result_layers", that.selector).datagrid("selectRow", 0);
		} else {
			$(".grid_result_layers", that.selector).datagrid("selectRow", 0);
		}
		
		$(that.selector).window("open");

		// 화면 크기 변환 시 결과 목록 영역 크기 조절 이벤트 등록
		mainObj.addResizeHandler("result", function(width, height) {
			that.resizeWindowHandler(width, height);
		});
		mainObj.resizeWindowHandler($(window).width(), $(window).height());
	},

	/**
	 * 데이터 선택
	 * 
	 * @param {Object}
	 *            data 선택된 데이터 정보 (데이터 아이디, 레코드 목록 등)
	 */
	selectData : function(data) {
		var that = this;
		that.data = data;
		that.createDetail();
		that.modifyMenu(data.ocpatIdn);
	},

	/**
	 * 상세 정보 생성
	 */
	createDetail : function(pageNumber, pageSize) {
		var that = this;
		var ocpatIdn = that.data.ocpatIdn;

		if (!that.gridOptions[ocpatIdn]) {
			that.createGridOptions(ocpatIdn);
		}

		// 결과 목록이 1000건 이내일 경우에만 정렬 허용
		var totCnt = 0;
		for (var j = 0; j < that.data.featureIds.length; j++)
			totCnt += that.data.featureIds[j].ids.length;
		
		var sortable = true;
		if (totCnt > 1000) {
			sortable = false;
		}
		var frozenColumns = that.gridOptions[ocpatIdn].frozenColumns[0];
		for (var i = 0, len = frozenColumns.length; i < len; i++) {
			frozenColumns[i].sortable = sortable;
		}
		var columns = that.gridOptions[ocpatIdn].columns[0];
		for (var i = 0, len = columns.length; i < len; i++) {
			columns[i].sortable = false;
		}

		$(".grid_result_detail", that.selector).datagrid(that.gridOptions[ocpatIdn]);
		var pagination = $(".grid_result_detail", that.selector).datagrid("getPager");
		pagination.pagination({
			displayMsg : "{from} - {to} / {total}",
			onSelectPage : function(pageNumber, pageSize) {
				that.pageNumber = pageNumber;
				that.pageSize = pageSize;
				that.loadDetail();
			}
		});

		// 상세정보 목록 표시
		that.pageNumber = pageNumber ? pageNumber : 1;
		that.pageSize = pageSize ? pageSize : 10;
		$(".grid_result_detail", that.selector).datagrid('sort', {
			sortName : that.getDataPkColumn(),
			sortOrder : 'asc'
		});
	},

	/**
	 * 메뉴 변경
	 * 
	 * @param {String}
	 *            ocpatIdn 대장 아이디
	 */
	modifyMenu : function(ocpatIdn) {
		var that = this;
		
		$(".a_setting_item", that.selector).show();
		$(".a_move_location", that.selector).show();
		$(".a_move_location", that.selector).show();
		$(".a_export_fix", that.selector).show();
	},

	/**
	 * 그리드 옵션 생성
	 * 
	 * @param {String}
	 *            ocpatIdn 점용대장 아이디
	 */
	createGridOptions : function(ocpatIdn) {
		var that = this;

		var dataId = ocpatRegObj.getLayerId(ocpatIdn);
		
		var kwsData = dataObj.getDataDetailByDataId(dataId);
		var dataFields = kwsData.kwsDataFields;

		that.dataFields[ocpatIdn] = {
			columns : dataFields	
		};
		
		var frozenColumns = [ {
			field : "itemNo",
			checkbox : true
		} ];

		var pk = null;
		var columns = [];
		for (var i = 0, len = dataFields.length; i < len; i++) {
			var dataField = dataFields[i];
			var fieldId = camelcaseUtils.underbarToCamelcase(dataField.fieldId);
			var fieldAlias = dataField.fieldAlias;
			var indictTy = dataField.indictTy;

			if (fieldId == "ftrIdn") {
				pk = fieldId;

				frozenColumns.push({
					field : fieldId,
					title : fieldAlias,
					width : 100,
					sortable : true
				});
			} 
			else if (indictTy != "WKT" && indictTy != "HIDE") {
				columns.push({
					field : fieldId,
					title : fieldAlias,
					width : 100,
					sortable : false
				});
			}
		}

		// 컬럼이 10개가 넘어가는 경우 컬럼 고정
		var fitColumns = false;
		if (columns.length < 10)
			fitColumns = true;

		that.gridOptions[ocpatIdn] = {
			pk : pk,
			frozenColumns : [ frozenColumns ],
			columns : [ columns ],
			fitColumns : fitColumns
		};
	},

	/**
	 * 정렬
	 */
	sortOrdr : function() {
		var that = this;
		var data = that.data;

		if (data.totalCount > 1000) {
//			for (var i = 0; i < data.featureIds.length; i++) {
//				data.featureIds.ids = data.featureIds.ids.sort(function(a, b) {
//					return b - a;
//				});
//			}
			that.loadDetail();
		} else if(data.totalCount > 0){
			var layerId = data.featureIds[0].dataId;
			var featureIds = data.featureIds[0].ids;
			
			var params = {};
			params.ocpatIdn = data.ocpatIdn;
			params.layerId = layerId;
			params.featureIds = featureIds;
			params.idnField = camelcaseUtils.camelcaseToUnderbar(that.getDataPkColumn());
			params.sortField = camelcaseUtils.camelcaseToUnderbar(that.sort);
			params.sortDirection = that.ordr;
			
			var url = CONTEXT_PATH + "/ocpat/sortRegister.do";
			$.post(url, params).done(function(response) {
				if (response.rows) {
					that.data.featureIds = response.rows.featureIds;
					that.loadDetail();
				}
				else {
					var gridData = {
						total : 0,
						rows : []
					};
					$(".grid_result_detail", that.selector).datagrid("loadData", gridData);					
					$.messager.alert(that.TITLE, "대장정보를 불러오는데 실패 했습니다.");
				}
			}).fail(function() {
				$.messager.alert(that.TITLE, "대장정보를 불러오는데 실패 했습니다.");
			});		
		}
	},

	/**
	 * 상세 정보 목록 표시
	 */
	loadDetail : function() {
		var that = this;

		that.removeFeatures();
		var data = that.data;

		// 상세 데이터 가져오기 
		var ocpatIdn = data.ocpatIdn;
		var layerId = data.featureIds[0].dataId;
		var feature = data.featureIds[0];
		var featureIds = [];
		
		var firstIndex = pagingUtils.getFirstIndex(that.pageNumber,	that.pageSize);
		var lastIndex = pagingUtils.getLastIndex(that.pageNumber, that.pageSize);
		
		var count = feature.ids.length;
		if (firstIndex < count) {
			if (lastIndex <= count) {
				var ids = feature.ids.slice(firstIndex, lastIndex);
				featureIds.push(ids);
			}
			else {
				var ids = feature.ids.slice(firstIndex, count);
				featureIds.push(ids);
				firstIndex = 0;
				lastIndex -= count;
			}
		} else {
			firstIndex = firstIndex - count;
			lastIndex = firstIndex + that.pageSize;
		}

		var params = {};
		params.ocpatIdn = ocpatIdn;
		params.layerId = layerId;
		params.featureIds = featureIds;
		params.idnField = camelcaseUtils.camelcaseToUnderbar(that.getDataPkColumn());
		params.sortField = camelcaseUtils.camelcaseToUnderbar(that.sort);
		params.sortDirection = that.ordr;
		
		var url = CONTEXT_PATH + "/ocpat/searchRegister.do";
		$.post(url, params).done(function(response) {
			if(response && response.rows) {
				if(response.rows.length <= 0) {
					$.messager.alert(title, "검색 결과가 없습니다.");
				} else {
					var rows = response.rows;
					var gridData = {
						total : data.totalCount,
						rows : rows
					};
					$(".grid_result_detail", that.selector).datagrid("loadData", gridData);
				}
			}
			else {
				$.messager.alert(that.TITLE, "공간정보를 불러오는데 실패 했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "공간정보를 불러오는데 실패 했습니다.");
		});
	},

	/**
	 * 검색 결과 목록 갱신
	 */
	refreshResultLayersGrid : function() {
		var that = this;
		var rowData = $(".grid_result_layers", that.selector).datagrid("getSelected");
		var rowIndex = $(".grid_result_layers", that.selector).datagrid("getRowIndex", rowData);
		
		rowData.totalCount = rowData.ids.length;
		$(".grid_result_layers", that.selector).datagrid("refreshRow", rowIndex);
		$(".grid_result_layers", that.selector).datagrid("selectRow", rowIndex);
	},

	/**
	 * 항목 설정
	 */
	settingItem : function() {
		var that = this;
		var options = that.gridOptions[that.data.ocpatIdn];
		windowObj.resultItemSetupObj.open(options.columns, {
			onSubmit : function(data) {
				var columns = [];
				var showColumnCount = 0;
				for (var i = 0, len = data.length; i < len; i++) {
					var hidden = false;
					if (!data[i].checked) {
						hidden = true;
						showColumnCount++;
					}
					columns.push({
						field : data[i].id,
						title : data[i].text,
						width : 100,
						sortable : false,
						hidden : hidden
					});
				}

				// 컬럼이 10개가 넘어가는 경우 컬럼 고정
				if (showColumnCount < 10) {
					options.fitColumns = true;
				} else {
					options.fitColumns = false;
				}

				options.columns = [ columns ];
				that.createDetail(that.pageNumber, that.pageSize);
			}
		});
	},
	
	/*
	 * 클립리포트 버튼
	 */
	batchPrintOcpatpnt : function(){
		var that = this;
		var data = that.data;
		var url = CONTEXT_PATH + "/clipreport/";
		var selects = $(".grid_result_detail", that.selector).datagrid("getSelections"); 
		var ftrIdn ="";
        var ocpatIdn = data.ocpatIdn;
        
        switch(ocpatIdn){
        	case '100100' : 
        	  ftrCde = 'OC001'
        	 break;
        	case '200100' :
        	 ftrCde = 'OC002'
        	 break;
        	case '300100' :
        	 ftrCde = 'OC003'
        	 break;
        	case '400100' :
        	 ftrCde = 'OC004'
        	 break;
        	case '500100' :
        	 ftrCde = 'OC005'
        	 break;
        	case '600100' :
        	 ftrCde = 'OC006'
        	 break;
        	case '700100' :
           	 ftrCde = 'OC007'
           	 break;
        	case '800100' :
           	 ftrCde = 'OC008'
           	 break;
        	case '900100' :
           	 ftrCde = 'OC009'
           	 break;
        	default :
        	 ftrCde = 'Null';
        }

		if(selects.length > 0){
			for(var i=0 ; i<selects.length ; i++){
				ftrIdn+=selects[i].ftrIdn+",";
			}
			ftrIdn = ftrIdn.substring(0,ftrIdn.length-1);
				if(ftrCde=='OC001' || ftrCde=='OC002' || ftrCde=='OC003' || ftrCde=='OC004'){
				    if(data){
				    	url += "ocpatRdtOcpeDt.do?ftrCde=" + ftrCde + "&ftrIdn=" + ftrIdn;
					    popupUtils.open(url, "점용허가", { width : 900 , height : 800, left : 300, top:150 });
			    	}else{
				    	$.messager.alert(that.TITLE, "선택된 행이 없습니다.");
					    };
				}else if (ftrCde=="OC005"){
				    if(data){
				    	url += "ocpatRdtOccnDt.do?ftrCde=" + ftrCde + "&ftrIdn=" + ftrIdn;
					    popupUtils.open(url, "변경허가", { width : 900 , height : 800, left : 300, top:150 });
			    	}else{
				    	$.messager.alert(that.TITLE, "선택된 행이 없습니다.");
					    };
				}else if (ftrCde=="OC006"){
				    if(data){
				    	url += "ocpatRdtOcreDt.do?ftrCde=" + ftrCde + "&ftrIdn=" + ftrIdn;
					    popupUtils.open(url, "원상회복공사", { width : 900 , height : 800, left : 300, top:150 });
			    	}else{
				    	$.messager.alert(that.TITLE, "선택된 행이 없습니다.");
					   };
				}else if (ftrCde=="OC007"){
				    if(data){
				    	url += "ocpatRdtOcdsDt.do?ftrCde=" + ftrCde + "&ftrIdn=" + ftrIdn;
					    popupUtils.open(url, "블허가", { width : 900 , height : 800, left : 300, top:150 });
			    	}else{
				    	$.messager.alert(that.TITLE, "선택된 행이 없습니다.");
					    };
				}else if (ftrCde=="OC008"){
				    if(data){
				    	url += "ocpatRdtOccaDt.do?ftrCde=" + ftrCde + "&ftrIdn=" + ftrIdn;
					    popupUtils.open(url, "점용취소", { width : 900 , height : 800, left : 300, top:150 });
			    	}else{
				    	$.messager.alert(that.TITLE, "선택된 행이 없습니다.");
					    };
				}else if (ftrCde=="OC009"){
				    if(data){
				    	url += "ocpatRdtOcdrDt.do?ftrCde=" + ftrCde + "&ftrIdn=" + ftrIdn;
					    popupUtils.open(url, "취하원", { width : 900 , height : 800, left : 300, top:150 });
			    	}else{
				    	$.messager.alert(that.TITLE, "선택된 행이 없습니다.");
					    };
				}
		}
	},
	
	
	/**
	 * 목록 위치
	 */
	moveLocation : function() {
		var that = this;
		var format = new ol.format.WKT();
		var features = [];
		var rows = $(".grid_result_detail", that.selector).datagrid("getData").rows;
		for (var i = 0, len = rows.length; i < len; i++) {
			var row = rows[i];
			var feature = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);
			features.push(feature);
		}
		
		highlightObj.moveFeatures(features, {
			projection : ol.proj.get(MAP.PROJECTION)
		});
	},

	/**
	 * 대장 조회
	 */
	searchRegister : function() {
		var that = this;
		var data = that.data;
		var featureIds = that.data.featureIds[0];
		
		var selects = $(".grid_result_detail", that.selector).datagrid("getSelections");
		if(selects.length > 0) {
			var featureIds = [];
			var ocpatIdn = data.ocpatIdn;
			
			for(var i = 0, len = selects.length; i < len; i++) {
				var ftrIdn = selects[i][that.getDataPkColumn()];
				var layerId = that.findLayerId(ftrIdn);
				var ftrCde = selects[i].ftrCde;
				var pnu = selects[i].pnu;
				var info = {
					dataId : layerId,
					ftrIdn : ftrIdn,
					dataAlias : that.data.ocpatAlias
				};
				featureIds.push(info);
			}

			windowObj.ocpatRegisterObj.open(ftrIdn,layerId,featureIds,data,ftrCde,pnu);
		} else { 
			$.messager.alert(that.TITLE, "선택된 행이 없습니다.");
		}

	},


	findLayerId : function(ftrIdn) {
		var that = this;
		var data = that.data;
		var layerCnt = data.featureIds.length;

		for (var i = 0; i < layerCnt; i++) {
			var feature = data.featureIds[i];
			var ids = feature.ids;
			for (var i = 0; i < ids.length; i++) {
				if (ids[i] == ftrIdn)
					return feature.dataId;
			}
		}
		
		return null;
	},
	
	findReferenceId : function(ftrIdn) {
		var that = this;
		var data = that.data;
		var layerCnt = data.featureIds.length;

		for (var i = 0; i < layerCnt; i++) {
			var feature = data.featureIds[i];
			var ids = feature.ftrIdn;
			if (ids) {
				for (var i = 0; i < ids.length; i++) {
					var split = ids[i].split('.');
					if (split[1] == ftrIdn)
						return ids[i];
				}
			}
		}
		
		return null;
	},
	
	/**
	 * 지도 고정
	 * 
	 * @param {Object}
	 *            element 엘리먼트
	 */
	toggleFixed : function(element) {
		var that = this;
		if (that.isFixed) {
			$(element).find("img").attr("src",
					$(element).find("img").attr("src").replace("_on", "_off"));
			that.isFixed = false;
		} else {
			$(element).find("img").attr("src",
					$(element).find("img").attr("src").replace("_ov", "_on"));
			that.isFixed = true;
		}
	},

	/**
	 * 도형 표시
	 */
	showFeatures : function() {
		var that = this;
		that.removeFeatures();

		var format = new ol.format.WKT();
		var features = [];
		var data = $(".grid_result_detail", that.selector).datagrid("getSelections");
		for (var i = 0, len = data.length; i < len; i++) {
			var row = data[i];
			var geom = row[MAP.GEOMETRY_ATTR_NAME];
			if (geom) {
				if (geom instanceof Array) {
					for (var i = 0; i < geom.length; i++ ) {
						var feature = format.readFeature(geom[i]);
						features.push(feature);
					}
				} else {
					var feature = format.readFeature(geom);
					features.push(feature);
				}
			}
		}
		
		if (features && features.length > 0) {
			highlightObj.showRedFeatures(
				that.CLASS_NAME, 
				features,
				!that.isFixed,
				{
					projection : ol.proj.get(MAP.PROJECTION)
				}
			);
		}
	},

	/**
	 * 도형 표시 제거
	 */
	removeFeatures : function() {
		var that = this;
		highlightObj.removeFeatures(that.CLASS_NAME);
	},

	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		that.gridOptions = {};
		that.data = null;
		that.sort = null;
		that.ordr = null;
		$(".grid_result_layers", that.selector).datagrid("loadData", {
			total : 0,
			rows : []
		});
		$(".grid_result_detail", that.selector).datagrid("loadData", {
			total : 0,
			rows : []
		});
		that.removeFeatures();

		// 화면 크기 변환 시 결과 목록 영역 크기 조절 이벤트 해제
		mainObj.removeResizeHandler("result");
	},

	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		$(that.selector).window("close");
	}
};