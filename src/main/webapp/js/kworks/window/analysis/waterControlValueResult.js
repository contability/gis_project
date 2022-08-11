windowObj.waterControlValueResult = {

	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "차단제수변 분석결과",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "WaterControlValueResult",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 상세 정보 데이터 그리드 옵션 목록
	 * @type {Object}
	 */
	gridOptions : {},
	
	/**
	 * 결과 데이터
	 */
	data : null,
	
	/**
	 * PK 항목
	 * @type {String}
	 */
	pk : null,
	
	/**
	 * 열기
	 */
	open : function(features) {
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/analysis/waterControlValueResult/page.do";
		var windowOptions = {
			width : 600,
			height : 400,
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
			
			highlightObj.showYellowFeatures(that.CLASS_NAME, features);
			that.loadLayers(features);
			
			that.bindEvents();
		});
		that.selector = "#" + id;
	},
	
	/**
	 * 닫기
	 * @returns {Boolean}
	 */
	close : function() {
		var that = this;
		highlightObj.removeFeatures(that.CLASS_NAME);
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.gridOptions = {};
		that.data = null;
		that.pk = null;
		
		// 화면 크기 변환 시 결과 목록 영역 크기 조절 이벤트 해제
		mainObj.removeResizeHandler(that.CLASS_NAME);
		return false;
	},
	
	/**
	 * UI 
	 */
	initUi : function() {
		var that = this;
		
		$(".div_result_layout", that.selector).layout();
		
		// 결과창 위치 조절
		$(that.selector).window({
			top : $(window).height() - 400
		});
		
		// 검색 결과 목록 데이터 그리드 생성
		$(".grid_result_layers", that.selector).datagrid({
			columns : [[
			    {field:"dataId", hidden : true},
				{field:"dataAlias", title:"검색 내용", width:198},
				{field:"totalCount", title:"건 수", width:100}
			 ]],
			 fitColumns : true,
			 singleSelect : true,
			 onSelect : function(rowIndex, rowData) {
				 that.selectData(rowData);
			 }
		});
		
		// 상세 정보 데이터 그리드 생성
		$(".grid_result_detail", that.selector).datagrid({
			ctrlSelect : true,
			remoteSort : false,
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
		
		// 화면 크기 변환 시 결과 목록 영역 크기 조절 이벤트 등록
		mainObj.addResizeHandler(that.CLASS_NAME, function(width, height) {
			that.resizeWindowHandler(width, height);
		});
		
		// 레이아웃 센터 창이 변경되면 그리드 크기 변경
		$(".div_result_layout", that.selector).layout("panel", "center").panel({
			onResize : function(width, height) {
				that.resizeDatagrid(width, height);
			}
		});
		
		mainObj.resizeWindowHandler($(window).width(), $(window).height());
	},
	
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 대장 조회
		$(".a_search_register", that.selector).click(function() {
			that.searchRegister();
			return false;
		});
		
		// 속성 내보내기
		$(".a_export_excel", that.selector).click(function() {
			that.exportExcel();
			return false;
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
	},
	
	/**
	 * 그리드 크기 변경
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
	 * 차단제수변 결과 파싱
	 * @param {Array.<ol.Feature>} features 도형 목록
	 */
	loadLayers : function(features) {
		var that = this;
		var data = {};
		for(var i=0, len=features.length; i < len; i++) {
			var feature = features[i];
			var table = feature.tableName;
			var id = feature.fid;
			if(!data[table]) {
				data[table] = {
					ids : []
				};
			}
			data[table].ids.push(id);
		}
		
		var rows = [];
		for(var dataId in data) {
			var obj = data[dataId];
			var dataInfo = dataObj.getDataByDataId(dataId);
			var row = {
				dataId : dataInfo.dataId,
				dataAlias : dataInfo.dataAlias,
				totalCount : obj.ids.length,
				ids : obj.ids
			};
			rows.push(row);
		}
		
		var gridData = {
			total : rows.length,
			rows : rows
		};
		$(".grid_result_layers", that.selector).datagrid("loadData", gridData);
		// 첫 번째 목록 선택
		if(rows.length > 0) {
			$(".grid_result_layers", that.selector).datagrid("selectRow", 0);
		}
	},
	
	/**
	 * 데이터 선택
	 * @param {Object} data 선택된 데이터 정보 (데이터 아이디, 레코드 목록 등)
	 */
	selectData : function(data) {
		var that = this;
		that.data = data;
		that.createDetail();
		
	},
	
	/**
	 * 상세 정보 생성
	 */
	createDetail : function(pageNumber, pageSize) {
		var that = this;
		var dataId = that.data.dataId;
		if(!that.gridOptions[dataId]) {
			that.createGridOptions(dataId);
		}
		$(".grid_result_detail", that.selector).datagrid(that.gridOptions[dataId]);
		that.loadDetail();
		$(".grid_result_detail", that.selector).datagrid('sort', {
			sortName: that.pk,
			sortOrder: 'desc'
		});
	},
	
	/**
	 * 그리드 옵션 생성
	 * @param {String} dataId 데이터 아이디
	 */
	createGridOptions : function(dataId) {
		var that = this;
		
		var kwsData = dataObj.getDataByDataId(dataId, true);
		var dataFields = kwsData.kwsDataFields;
		
		var frozenColumns = [
 		    {field:"itemNo",checkbox:true}
 		];
		var columns = [];
		for(var i=0, len=dataFields.length; i < len; i++) {
			var dataField = dataFields[i];
			var fieldId = camelcaseUtils.underbarToCamelcase(dataField.fieldId);
			var fieldAlias = dataField.fieldAlias;
			var indictTy = dataField.indictTy;
			
			if(dataField.pkAt == "Y") {
				that.pk = fieldId;
				
				frozenColumns.push({
					field : fieldId,
					title : "번호",
					width : 100,
					sortable : true
				});
			}
			else if(indictTy != "WKT" && indictTy != "HIDE") {
				columns.push({
					field : fieldId,
					title : fieldAlias,
					width : 100,
					sortable : true
				});
			}
		}
		
		// 컬럼이 10개가 넘어가는 경우 컬럼 고정
		var fitColumns = false;
		if(columns.length < 10) fitColumns = true;
		
		that.gridOptions[dataId] = {
			frozenColumns : [frozenColumns],
			columns : [columns],
			fitColumns : fitColumns
		};
	},
	
	/**
	 * 상세 정보 목록 표시 
	 */
	loadDetail : function() {
		var that = this;
		
		highlightObj.removeRedFeatures(that.CLASS_NAME);
		
		var data = that.data;
		var dataId = data.dataId;
		var fids = data.ids;
		
		// 공간 검색
		var filter = {
			filterType : "FIDS",
			fids : fids
		};
		spatialSearchUtils.listAll(that.TITLE, dataId, filter, function(rows) {
			if(rows && rows.length > 0) {
				var gridData = {
					total : data.ids.length,
					rows : rows
				};
				$(".grid_result_detail", that.selector).datagrid("loadData", gridData);
			}
		});
	},
	
	/**
	 * 도형 표시
	 */
	showFeatures : function() {
		var that = this;
		var format = new ol.format.WKT();
		var features = [];
		var data = $(".grid_result_detail", that.selector).datagrid("getSelections");
		for(var i=0, len=data.length; i < len; i++) {
			var row = data[i];
			var geom = row[MAP.GEOMETRY_ATTR_NAME];
			if(geom) {
				var feature = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);
				features.push(feature);
			}
		}
		if(features && features.length > 0) {
			highlightObj.showRedFeatures(that.CLASS_NAME, features, false, {
				projection : ol.proj.get(MAP.PROJECTION)
			});
		}
	},
	
	/**
	 * 대장 조회
	 */
	searchRegister : function() {
		var that = this;
		var selects = $(".grid_result_detail", that.selector).datagrid("getSelections");
		if(selects.length > 0) {
			var fids = [];
			for(var i=0, len=selects.length; i < len; i++) {
				fids.push(selects[i][that.pk]);
			}
			windowObj.registerObj.open(that.data.dataId, fids);
		}
		else {
			$.messager.alert(that.TITLE, "선택된 행이 없습니다.");
		}
		
	},
	
	/**
	 * 엑셀 내보내기
	 */
	exportExcel : function() {
		var that = this;
		var data = [];
		
		var gridData = $(".grid_result_layers", that.selector).datagrid("getData");
		var rows = gridData.rows;
		for(var i=0, len=rows.length; i < len; i++) {
			var row = rows[i];
			data.push({
				dataId : row.dataId,
				ids : row.ids
			});
		}
		var excelData = { name : "WaterControlValue", data : data };
		$("#frmExcelFileFromData input[name=data]").val(encodeURIComponent(JSON.stringify(excelData)));
		$("#frmExcelFileFromData").submit();
	}
	
};