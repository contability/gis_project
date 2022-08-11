/**
 * 점용대장 검색결과 객체
 * 
 * @type {Object}
 */
var policyRegResultObj = {
		
		/**
		 * 선택자
		 * 
		 * @type {String}
		 */
		selector : "#div_policy_reg_result",

		/**
		 * 제목
		 * 
		 * @type {String}
		 */
		TITLE : "검색 결과 목록 현황",

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
		 * 외부에서 메뉴 세팅
		 * 검색 대상별로 각기 다른 메뉴세팅
		 */
		externalOptions : [],

		dataId : "PLCY_STAT_AS",
		
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
			$("#div_policy_reg_result").window({
				top : $(window).height() - 400
			});

			// 검색 결과 목록 데이터 그리드 생성
			$(".grid_result_layers", that.selector).datagrid({
				columns : [ [ {
					field : "dataId",
					hidden : true
				}, {
					field : "plcyDep",
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
			$(".a_setting_item", that.selector).off();
			$(".a_setting_item", that.selector).click(function() {
				that.settingItem();
				return false;
			});

			// 목록 위치
			$(".a_move_location", that.selector).off();
			$(".a_move_location", that.selector).click(function() {
				that.moveLocation();
				return false;
			});
			
			// 대장 조회
			$(".a_search_register", that.selector).off();
			$(".a_search_register", that.selector).click(function() {
				that.searchRegister();
				return false;
			});

			// 지도 고정
			$(".a_export_fix", that.selector).off();
			$(".a_export_fix", that.selector).click(function(event) {
				var element = $(this);
				that.toggleFixed(element);
				event.preventDefault();
			});
		},
		
		open : function(data) {
			var that = this;

			that.close();
			
			// 데이터 목록 그리드 생성
			var rows = [];
			for (var i = 0, len = data.length; i < len; i++) {
				var obj = data[i];
				var totCnt = 0;
				for (var j = 0; j < obj.ids.length; j++) {
					if (obj.ids[j]) 
						totCnt += obj.ids.length;
				}
				if (totCnt > 0) {
					var row = {
							ids : obj.ids,
							dataId : "PLCY_STAT_AS",
							plcyDep : obj.plcyDep,
							totalCount : obj.count
							//featureIds : obj.featureIds
					};
					rows.push(row);
				}	
				else {
					var obj = data[i];
					var row = {
							ids : obj.ids,
							dataId : "PLCY_STAT_AS",
							plcyDep : obj.plcyDep,
							totalCount : obj.count
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
		
		createData : function(data){
			var that = this;
//			var rows = [];
			for (var i = 0, len = data.length; i < len; i++) {
				var obj = data[i];
				var totCnt = 0;
				for (var j = 0; j < obj.length; j++) {
					if (obj[j].ids) 
						totCnt += obj.length;
				}
				that.open(obj);
			}
			
		},
		
		/**
		 * 항목 설정
		 */
		settingItem : function() {
			var that = this;
			var options = that.gridOptions[that.data.ids];
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
			
			var selects = $(".grid_result_detail", that.selector).datagrid("getSelections");
			if(selects.length == 1) {
				var info = {
						dataId : data.dataId,
						ftrIdn : selects[0].ftrIdn,
						dataAlias : data.dataAlias,
						ftrCde : selects[0].ftrCde
					};
				windowObj.policyRegisterObj.open(info);
			} else { 
				$.messager.alert(that.TITLE, "선택된 행이 없습니다.");
			}

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
				var filter = {
					filterType : "FIDS",
					fids : that.data.ids,
					sortOrdr : camelcaseUtils.camelcaseToUnderbar(that.sort),
					sortDirection : that.ordr
				};
	
				var optins = { isNoMessage : true };
				spatialSearchUtils.listAllSummary(that.TITLE, that.data.dataId,
						filter, function(data) {
							if (data) {
								that.data.ids = data.ids;
								that.loadDetail();
							} else {
								// 1개의 데이터가 있는 상황에서 남은 1개를 삭제할 때 발생
								// 검색 시 결과가 없는 경우는 메세지 출력 후 검색 결과 목록 현황 창을 띄우지않음
								// 검색 결과 목록 현황 창이 떠있는 상태에서 검색결과가 없는 경우 발생
								// 검색 결과 목록 삭제
								var rowData = $(".grid_result_layers", that.selector).datagrid(
								"getSelected");
								var rowIndex = $(".grid_result_layers", that.selector).datagrid(
								"getRowIndex", rowData);
								$(".grid_result_layers", that.selector).datagrid(
										"deleteRow", rowIndex);
								
								// 검색결과 삭제
								rowData = $(".grid_result_detail", that.selector).datagrid(
								"getSelected");
								rowIndex = $(".grid_result_detail", that.selector).datagrid(
								"getRowIndex", rowData);
								$(".grid_result_detail", that.selector).datagrid(
										"deleteRow", rowIndex); 
							}
							
						}, optins);
			}
			// length 가 0일경우
			if(that.data.ids.length < 1){
				that.loadDetail();
			}
		},

		/**
		 * 상세 정보 목록 표시
		 */
		loadDetail : function() {
			var that = this;

			that.removeFeatures();

			var data = that.data;
			var dataId = data.dataId;

			if (data.ids) {
				// 공간 검색
				var ids = null;
				var firstIndex = pagingUtils.getFirstIndex(that.pageNumber,
					that.pageSize);
				var lastIndex = pagingUtils
					.getLastIndex(that.pageNumber, that.pageSize);
				ids = data.ids.slice(firstIndex, lastIndex);

				var filter = {
						filterType : "FIDS",
						fids : ids,
						sortOrdr : camelcaseUtils.camelcaseToUnderbar(that.sort),
						sortDirection : that.ordr
				};

				spatialSearchUtils.listAll(that.TITLE, dataId, filter, function(rows) {
					if (rows && rows.length > 0) {
						var gridData = {
								total : data.ids.length,
								rows : rows
						};
						$(".grid_result_detail", that.selector).datagrid("loadData",
							gridData);
					}
					else {
						var gridData = {
								total : data.ids.length,
								rows : rows
						};
						$(".grid_result_detail", that.selector).datagrid("loadData",
							gridData);
					}
				});
			} else {
				var gridData = {
						total : 0,
						rows : null
				};
				$(".grid_result_detail", that.selector).datagrid("loadData",
					gridData);
			}
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
					if (data[i].ids == rows[j].ids) {
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
		 * 목록 추가 - 중복된 데이터 아이디(KWS_DATA)가 있는 경우
		 * 
		 * @param data
		 */
		appendRows : function(data) {
			var that = this;
			var gridData = $(".grid_result_layers", that.selector).datagrid(
					"getData");
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
		
		newRows : function(data) {
			var that = this;
			
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
		
		selectData : function(data) {
			var that = this;  // that = {selector: "#div_result", TITLE: "검색 결과 목록 현황", gridOptions: {…}, data: {…}, sort: null, …}, dataId = "RDL_REAL_LS"
			that.data = data; //that = {selector: "#div_result", TITLE: "검색 결과 목록 현황", gridOptions: {…}, data: {…}, sort: null, …}, dataId = "RDL_REAL_LS"
			that.createDetail();
			that.modifyMenu(data.dataId);
		},

		/**
		 * 상세 정보 생성
		 */
		createDetail : function(pageNumber, pageSize) {
			var that = this;  // 검색결과 목록현황
			var dataId = that.data.dataId; // datatId = "RDL_BRDG_AS" 테이블명 가져오기

			if (!that.gridOptions[dataId]) { //that = {selector: "#div_result", TITLE: "검색 결과 목록 현황", gridOptions: {…}, data: {…}, sort: null, …}, dataId = "RDL_REAL_LS"
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

			$(".grid_result_detail", that.selector).datagrid(
					that.gridOptions[dataId]);
			var pagination = $(".grid_result_detail", that.selector).datagrid(
					"getPager");
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
		
		getDataPkColumn : function() {
			var that = this;
			return that.gridOptions[that.data.dataId].pk;
		},

		/**
		 * 메뉴 변경
		 * 
		 * @param {String}
		 *            dataId 데이터 아이디
		 * 수정자 : 이승재, 2019.12.06
		 */
		modifyMenu : function(dataId) {
			var that = this;

			var isMenuOption = false;
			var isEventOption = false;
			var externalOptionIndex = -1;
			if (that.externalOptions.length > 0) {
				for (var i = 0; i < that.externalOptions.length; i++) {
					if (that.externalOptions[i].dataId == dataId) {
						if (that.externalOptions[i].menuObj) {
							isMenuOption = true;
							externalOptionIndex = i;
						}
				
						if (that.externalOptions[i].eventObj) {
							isEventOption = true;
							externalOptionIndex = i;
						}
					}
				}
			}
			
			if (isMenuOption) {
				that.externalOptions[externalOptionIndex].menuObj(dataId);
			} else {
				that.defaultMenuSetting(dataId);
			}
			
			if (isEventOption) {
				that.externalOptions[externalOptionIndex].eventObj();
			} else {
				that.bindEvents();
			}
		},
		
		/**
		 * 그리드 옵션 생성
		 * 
		 * @param {String}
		 *            dataId 데이터 아이디
		 */
		createGridOptions : function(dataId) {
			var that = this;
			var params = { sysId : SYS_ID };

			var kwsData = dataObj.getDataByDataId(dataId, true);
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
				var sysTy = dataField.sysTy;

				if (dataField.pkAt == "Y") {
					pk = fieldId;

					frozenColumns.push({
						field : fieldId,
						title : "번호",
						width : 100,
						sortable : true
					});
				} else if (indictTy != "WKT" && indictTy != "HIDE") {
					if(PROJECT_CODE != "gn") {
						columns.push({
							field : fieldId,
							title : fieldAlias,
							width : 100,
							sortable : false
						});
					} else {
						// 강릉시 도로대장 관리서비스(sysId == "11") 사용하는 필드 "COMMON" && "ROAD_REGISTER"
						if(params.sysId == "11") {
							if(sysTy == "COMMON" || sysTy == "ROAD_REGISTER"){
								columns.push({
									field : fieldId,
									title : fieldAlias,
									width : 100,
									sortable : false
								});
							}
						} 
						// 강릉시 도로대장 관리서비스가 아닌 경우 사용하는 필드 "COMMON" && ""
						else {
							if(sysTy == "COMMON" || sysTy == ""){
								columns.push({
									field : fieldId,
									title : fieldAlias,
									width : 100,
									sortable : false
								});
							}
						}
					}
					
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
		 * 기본메뉴설정
		 * 
		 * @param {String}
		 *            dataId 데이터 아이디
		 * 작성자 : 이승재, 2019.12.06
		 */
		defaultMenuSetting : function(dataId) {
			var that = this;
			var data = dataObj.getDataByDataId(dataId);
			var dataAuthor = dataAuthorObj.getDataAuthor(dataId);
			
			/*
			 * 검색결과 목록 가공(추가선택, 선택제거) 버튼 활성화
			 */
			if (data && data.dataTy == "LAYER") {
				$(".a_addition_choice").show();
				$(".a_remove_rows").show();
				$(".a_search_condition").show();
				$(".a_search_relation").show();
				
				//목록 위치보기 버튼 활성화
				$(".a_move_location").show();
				$(".a_export_fix").show();
				
				//데이터 Expot 버튼 활성화
				// 내보내기 여부 표시
				if (dataAuthor && dataAuthor.exportAt == 'Y') {
					$(".a_export_excel", that.selector).show();
					$(".a_export_shape", that.selector).show();
				} else {
					$(".a_export_excel", that.selector).hide();
					$(".a_export_shape", that.selector).hide();
				}
			}
			// 속성정보일 경우
			else {
				$(".a_addition_choice").show();
				$(".a_remove_rows").show();
				$(".a_search_condition").show();
				//속정정보일 경우 연관검색 비활성화
				$(".a_search_relation").hide();
				
				//목록 위치보기 버튼 비활성화
				$(".a_move_location").hide();
				$(".a_export_fix").hide();
				
				//데이터 Expot 버튼 활성화
				//내보내기 여부 표시
				//속성정보일 경우 엑셀 내보내기만 활성화
				if (dataAuthor && dataAuthor.exportAt == 'Y') {
					$(".a_export_excel", that.selector).show();
					$(".a_export_shape", that.selector).hide();
				} else {
					$(".a_export_excel", that.selector).hide();
					$(".a_export_shape", that.selector).hide();
				}
			}
							
			/*
			 * 공사위치 버튼 사용 여부
			 */
			if(dataId == "RDT_CONS_MA" || dataId == "WTT_CONS_MA" || dataId == "WTT_SPLY_MA" || dataId == "SWT_CONS_MA"  
				|| dataId == "LD_USE_MA")
			{
				$(".a_corporation_location").show();
			}
			else {
				$(".a_corporation_location").hide();
			}
			
			//양구군 토지공사대장 위치 내보내기 활성화
			if (dataId.toUpperCase() == "LDL_CONS_PS" && PROJECT_CODE.toUpperCase() == "YG" && dataAuthor && dataAuthor.exportAt == 'Y') {
				$(".a_export_ldConsPs", that.selector).show();
				$(".a_export_shape", that.selector).hide();
			}
			else {
				$(".a_export_ldConsPs", that.selector).hide();
			}
			
			//도시기준점 점의조서 일괄출력 사용여부
			//작업자 : 문세준, 2020.02.25
			if (dataId.toUpperCase() == "ETL_RGCP_PS" && dataAuthor && dataAuthor.prntngAt == 'Y') {
				$(".a_batchPrint_ctrlpnt").show();
			}
			else {
				$(".a_batchPrint_ctrlpnt").hide();
			}
		},
		
		/**
		 * 도형 표시
		 */
		showFeatures : function() {
			var that = this;
			
			that.removeFeatures();
			
			// 이승재, 2020.05.22
			// 아래 2020.05.15 수정에 의해
			// 속성정보 목록에서도 '공간정보가 없습니다.' 메세지 출력
			// data의 종류를 확인하여 showFeatures의 로직을 실행하도록 수정
			var data = dataObj.getDataByDataId(that.data.dataId);
			if (data.dataTy == 'LAYER') {
				var format = new ol.format.WKT();
				var features = [];
				var data = $(".grid_result_detail", that.selector).datagrid(
						"getSelections");
				for (var i = 0, len = data.length; i < len; i++) {
					var row = data[i];
					var geom = row[MAP.GEOMETRY_ATTR_NAME];
					if (geom) {
						var feature = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);
						features.push(feature);
					} else {
						// 이승재, 2020.05.15
						// 공간정보가 없는 row에 대한 예외 처리
						if (data.length == 1) {
							$.messager.alert(that.TITLE, '공간정보가 없습니다.');
						}
					}
				}
				if (features && features.length > 0) {
					highlightObj.showRedFeatures(that.CLASS_NAME, features,
							!that.isFixed, {
								projection : ol.proj.get(MAP.PROJECTION)
							});
				}
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
		 * 검색 결과 목록 갱신
		 * 이승재, 2020.12.08
		 */
		refreshResultLayersGrid : function() {
			var that = this;
			var rowData = $(".grid_result_layers", that.selector).datagrid(
					"getSelected");
			var rowIndex = $(".grid_result_layers", that.selector).datagrid(
					"getRowIndex", rowData);
			rowData.totalCount = rowData.ids.length;
			$(".grid_result_layers", that.selector)
					.datagrid("refreshRow", rowIndex);
			$(".grid_result_layers", that.selector).datagrid("selectRow", rowIndex);
		},
		
		/**
		 * 닫기
		 */
		close : function() {
			var that = this;
			if(that.selector) {
			$(that.selector).window("close");
			}
		}
		
};