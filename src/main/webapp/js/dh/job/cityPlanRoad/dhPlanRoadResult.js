/**
 * 동해시 도시계획도로 검색결과 객체
 */
var dhRoplResultObj = {

	/**
	 * 선택자
	 * 
	 * @type {String}
	 */
	selector : "#div_ropl_reg_result",
		
	/**
	 * 제목
	 * 
	 * @type {String}
	 */
	TITLE : "도시계획도로 검색 현황",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "CityPlanRoad",

	/**
	 * 데이터 - 현재 선택된 데이터에 포함되는 정보 (데이터 아이디, 레코드 아이디 목록 등)
	 * 
	 * @type {Object}
	 */
	data : null,
	
	/**
	 * 상세 정보 데이터 그리드 옵션 목록
	 * 
	 * @type {Object}
	 */
	gridOptions : {},

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
		$("#div_ropl_reg_result").window({
			top : $(window).height() - 400
		});

		// 검색 결과 목록 데이터 그리드 생성
		$(".grid_result_layers", that.selector).datagrid({
			columns : [ [ {
				field : "dataId",
				hidden : true
			}, {
				field : "dataAlias",
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
		
		// 일괄출력
		$(".a_print_ropl", that.selector).off();
		$(".a_print_ropl", that.selector).click(function() {
			that.batchPrint();
			return false;
		});

		// 대장 조회
		$(".a_search_register", that.selector).off();
		$(".a_search_register", that.selector).click(function() {
			that.searchRegister();
			return false;
		});
		
		// 속성 내보내기
		$(".a_export_excel", that.selector).off();
		$(".a_export_excel", that.selector).click(function() {
			that.exportExcel();
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
	 * 검색목록 선택 Clear
	 */
	unSelectAllRows : function () {
		var that = this;
		
		$(".grid_result_detail", that.selector).datagrid('unselectAll');
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
		return that.gridOptions[that.data.dataId].pk;
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

		$(that.selector).window(
			"resize",
			{
				width : width,
				left : left,
				top : windowHeight
						- $(that.selector).window("panel").height() - 120
			});
	},
	
	/**
	 * 그리드 크기 변경
	 * 
	 * @param {Number} width 너비
	 * @param {Number} height 높이
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
				if (data[i].dataId == rows[j].dataId) {
					isExist = true;
					break;
				}
			}
		}

		// 중복된 데이터(KWS_DATA)가 있는 경우 선택창 표시
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
	 * 목록 추가 - 중복된 데이터 아이디(KWS_DATA)가 있는 경우
	 * 
	 * @param data
	 */
	appendRows : function(data) {
		var that = this;
		var gridData = $(".grid_result_layers", that.selector).datagrid("getData");
		var rows = gridData.rows;
		
		for (var i = 0, len = rows.length; i < len; i++) {
			for (var j = data.length - 1; j >= 0; j--) {
				// 같은 데이터가 있을 경우 아이디 목록을 합친 후 중복 제거
				if (rows[i].dataId == data[j].dataId) {
					rows[i].ids = that.mergeIds(rows[i].ids, data[j].ids);
					data.splice(j, 1);
					break;
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
	 * @param {Array.<Number>} ids 아이디 목록
	 * @param {Array.<Number>} appendIds 추가할 아이디 목록
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
	 * @param {Array.<Object>} data 결과 데이터 목록
	 */
	open : function(data) {
		var that = this;

		that.close();
		
		// 데이터 목록 그리드 생성
		var rows = [];
		
		if(data[0].dataId) {
			for (var i = 0, len = data.length; i < len; i++) {
				var obj = data[i];
				var row = {
					dataId : obj.dataId,
					dataAlias : obj.dataAlias,
					totalCount : obj.ids.length,
					ids : obj.ids
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
	 * @param {Object} data 선택된 데이터 정보 (데이터 아이디, 레코드 목록 등)
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
		var dataId = that.data.dataId;

		if (!that.gridOptions[dataId]) {
			that.createGridOptions(dataId);
		}

		// 결과 목록이 1000건 이내일 경우에만 정렬 허용
		var sortable = true;
		if (that.data.ids.length > 1000) {
			sortable = false;
		}
		var frozenColumns = that.gridOptions[dataId].frozenColumns[0];
		for (var i = 0, len = frozenColumns.length; i < len; i++) {
			frozenColumns[i].sortable = sortable;
		}
		var columns = that.gridOptions[dataId].columns[0];
		for (var i = 0, len = columns.length; i < len; i++) {
			columns[i].sortable = sortable;
		}

		$(".grid_result_detail", that.selector).datagrid(that.gridOptions[dataId]);
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
			sortOrder : 'desc'
		});
	},
	
	/**
	 * 메뉴 변경
	 * 
	 * @param {String} dataId 데이터 아이디
	 */
	modifyMenu : function(ocpatIdn) {
		var that = this;
		
		$(".a_setting_item", that.selector).show();
		$(".a_move_location", that.selector).show();
		$(".a_export_fix", that.selector).show();
	},
	
	/**
	 * 그리드 옵션 생성
	 * 
	 * @param {String} dataId 데이터 아이디
	 */
	createGridOptions : function(dataId) {
		var that = this;

		var kwsData = dataObj.getDataDetailByDataId(dataId);
		var dataFields = kwsData.kwsDataFields;

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

			if (dataField.pkAt == "Y") {
				pk = fieldId;

				frozenColumns.push({
					field : fieldId,
					title : "관리번호",
					width : 100,
					sortable : true
				});
			} else if (indictTy != "WKT" && indictTy != "HIDE") {
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

		that.gridOptions[dataId] = {
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

		if (that.data.ids.length > 1000) {
			that.data.ids = that.data.ids.sort(function(a, b) {
				return b - a;
			});
			that.loadDetail();
		} else {
			var params = {};
			params.ids = that.data.ids;
			params.sortField = camelcaseUtils.camelcaseToUnderbar(that.sort);
			params.sortDirection = that.ordr;
			
			var url = CONTEXT_PATH + "/rest/dhPlanRoad/sortRegister.do";
			$.post(url, params).done(function(response) {
				if (response.rows) {
					that.data.ids = response.rows.ids;
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
		var firstIndex = pagingUtils.getFirstIndex(that.pageNumber,	that.pageSize);
		var lastIndex = pagingUtils.getLastIndex(that.pageNumber, that.pageSize);
		var ids = data.ids.slice(firstIndex, lastIndex);

		var params = {};
		params.ids = ids;
		params.sortField = camelcaseUtils.camelcaseToUnderbar(that.sort);
		params.sortDirection = that.ordr;
		
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/searchRegister.do";
		$.post(url, params).done(function(response) {
			if(response && response.rows) {
				if(response.rows.length <= 0) {
					$.messager.alert(title, "검색 결과가 없습니다.");
				} else {
					var rows = response.rows; //that.changePnuToAddress(response.rows);
					var gridData = {
						total : data.ids.length,
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
	 * PNU 코드를 주소로 변환 
	 * @param rows
	 * @returns
	 */
	changePnuToAddress : function(rows) {
		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];
 
			if (row.uprStr) {
				row.uprStr = pnuObj.getAddr(row.uprStr, true);
			}
			
			if (row.uprEnd) {
				row.uprEnd = pnuObj.getAddr(row.uprEnd, true);
			}
		}
		
		return rows;
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
		
		var options = that.gridOptions[that.data.dataId];
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
	 * 일괄출력
	 */
	batchPrint : function(){
		var that = this;
		
		var selects = $(".grid_result_detail", that.selector).datagrid("getSelections");
		if(selects.length > 0){
			var fids = "";
			
			for(var i = 0, len = selects.length; i < len; i++) {
				fids += selects[i][that.getDataPkColumn()] + ",";
			}
			fids = fids.substring(0,fids.length-1);
			var url = CONTEXT_PATH + "/clipreport/cityPlanRoadByBatch.do?ftrIdn=" + fids;
			popupUtils.open(url, "도시계획도로", { width : 900 , height : 800, left : 300, top:150 });
		}
		else {
			$.messager.alert(that.TITLE, "선택된 행이 없습니다.");
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
			if (row[MAP.GEOMETRY_ATTR_NAME]) {
				var feature = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);
				features.push(feature);
			}
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
		
		var selects = $(".grid_result_detail", that.selector).datagrid("getSelections");
		if(selects.length > 0) {
			var fids = [];
			
			for(var i = 0, len = selects.length; i < len; i++) {
				fids.push(selects[i][that.getDataPkColumn()]);
			}
			
			if (fids.length > 1) {
				$.messager.alert(that.TITLE, '하나의 행을 선택해 주십시요.');
				that.unSelectAllRows();
			} else {
				// 대장 상세보기
				dhPlanRoadObj.open(fids[0]);
			}
		} else { 
			$.messager.alert(that.TITLE, "선택된 행이 없습니다.");
		}

	},
	
	/**
	 * 엑셀 내보내기
	 */
	exportExcel : function() {
		var that = this;
		var exportType = 'EXCEL';
		
		windowObj.exportObj.open({
			userName : $("#hid_user_name").val(),
			deptName : $("#hid_dept_name").val(),
			exportType : exportType,
			onSubmit : function(params) {
				that.exportData(exportType, params);
			}
		});

	},
	
	/**
	 * 내보내기 (엑셀, 공간)
	 * 
	 * @param {String} exportTy 내보내기 타입
	 * @param {String} exportNm 내보내기 명
	 */
	exportData : function(exportTy, opt_params) {
		var that = this;
		var url = CONTEXT_PATH + "/rest/export/addByData.do";
		var params = $.extend({
			exportTy : exportTy,
			exportFilterTy : "FIDS",
			exportCntmId : MAP.PROJECTION,
			dataIds : [ that.data.dataId ],
			ids : that.data.ids
		}, opt_params);

		// 필드 명 가명 여부 추가 (엑셀)
		if (exportTy == "EXCEL") {
			params["fieldNmIndictAt"] = true;
		}

		// 파일 내보내기 등록
		that.exportRegist(url, params);
	},
	
	/**
	 * 내보내기 등록
	 * 
	 * @param {String}
	 *            url 서블릿
	 * @param {String}
	 *            params 파라미터
	 */
	exportRegist : function(url, params) {
		var that = this;
		
		// 파일 내보내기 등록
		$.post(url, params)
			.done(
				function(result) {
					if (result && result.rowCount) {
						$.messager.confirm(
							that.TITLE,
							"내보내기 데이터는 '메인 > 나의 시스템 > 데이터요청 관리' 에서 다운로드 할 수 있습니다. 지금 확인하시겠습니까?",
							function(isTrue) {
								if (isTrue) {
									window.open(
										CONTEXT_PATH
										+ "/self/export/list.do",
										"_blank");
									}
							});
					} else {
						$.messager.alert(that.TITLE,
								"내보내기 요청이 실패하였습니다.");
					}
				}).fail(function() {
			$.messager.alert(that.TITLE, "내보내기 요청이 실패하였습니다.");
		});
	},
	
	/**
	 * 지도 고정
	 * 
	 * @param {Object} element 엘리먼트
	 */
	toggleFixed : function(element) {
		var that = this;
		
		if (that.isFixed) {
			$(element).find("img").attr("src",	$(element).find("img").attr("src").replace("_on", "_off"));
			that.isFixed = false;
		} else {
			$(element).find("img").attr("src",	$(element).find("img").attr("src").replace("_ov", "_on"));
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
			else {
				if (data.length == 1) {
					$.messager.alert(that.TITLE, '공간정보가 없습니다.');
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
