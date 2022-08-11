/**
 * 도로대장 영상조회 결과 객체
 * 
 * @type {Object}
 */
var roadVideoResultObj = {

	/**
	 * 선택자
	 * 
	 * @type {String}
	 */
	selector : "#div_road_video_result",

	/**
	 * 제목
	 * 
	 * @type {String}
	 */
	TITLE : "도로대장 영상조회",

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
		$("#div_road_movie_result").window({
			top : $(window).height() - 400
		});

		// 동영상 생성

		// 상세 정보 데이터 그리드 생성
		$(".grid_result_detail", that.selector).datagrid({
			singleSelect : true,
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
		
		// 영상 내보내기
		$(".a_export_video", that.selector).click(function() {
			that.exportVideo();
			return false;
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

	/**
	 * PK 컬럼
	 * 
	 * @returns 컬럼명
	 */
	getDataPkColumn : function() {
		var that = this;
		return that.gridOptions[that.data.regIdn].pk;// 수정대상
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

		$(".grid_result_detail", that.selector).datagrid("resize", {
			width : "100%",
			height : height - 80
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

		//// 수정대상
		var gridData = $(".grid_result_layers", that.selector).datagrid("getData");
		var rows = gridData.rows;
		
		// 중복 검사
		var isExist = false;
		for (var i = 0, len = data.length; i < len; i++) {
			for (var j = 0, jLen = rows.length; j < jLen; j++) {
				if (data[i].regIdn == rows[j].regIdn) {
					isExist = true;
					break;
				}
			}
		}

		// 중복된 데이터가 있는 경우 선택창 표시
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
	 * 목록 추가 - 중복된 데이터 아이디가 있는 경우
	 * 
	 * @param data
	 */
	appendRows : function(data) {/// 수정대상
		var that = this;
		var gridData = $(".grid_result_layers", that.selector).datagrid("getData");
		var rows = gridData.rows;
		
		for (var i = 0, len = rows.length; i < len; i++) {
			for (var j = data.length - 1; j >= 0; j--) {
				// 같은 도로대장 조서가 있을 경우
				if (rows[i].regIdn == data[j].regIdn) {
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
	open : function(data) {/// 수정대상
		var that = this;

		//resultObj.close();
		that.close();
		roadRegResultObj.close();
		
		// 데이터 목록 그리드 생성
		var rows = [];
		if(data[0].featureIds){
			for (var i = 0, len = data.length; i < len; i++) {
				var obj = data[i];
				var totCnt = 0;
				for (var j = 0; j < obj.featureIds.length; j++) {
					if (obj.featureIds[j].ids) 
						totCnt += obj.featureIds[j].ids.length;
				}
			
				if (totCnt > 0) {
					var row = {
							regIdn : obj.regIdn,
							regAlias : obj.regAlias,
							totalCount : totCnt,
							featureIds : obj.featureIds
					};
					rows.push(row);
				}	
				else {
					var obj = data[i];
					var row = {
							regIdn : obj.regIdn,
							regAlias : obj.regAlias,
							totalCount : totCnt,
							featureIds : obj.featureIds
					};
					rows.push(row);
				}
			}
		} 
		
		that.data = rows[0];
		that.createDetail();
		that.modifyMenu(rows[0].regIdn);
		
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
		that.modifyMenu(data.regIdn);
	},
	
	/**
	 * 상세 정보 생성
	 */
	createDetail : function(pageNumber, pageSize) {
		var that = this;
		var regIdn = that.data.regIdn;

		if (!that.gridOptions[regIdn]) {
			that.createGridOptions(regIdn);
		}

		// 결과 목록이 1000건 이내일 경우에만 정렬 허용
		var totCnt = 0;
		for (var j = 0; j < that.data.featureIds.length; j++)
			totCnt += that.data.featureIds[j].ids.length;
		
		var sortable = true;
		if (totCnt > 1000) {
			sortable = false;
		}
		var frozenColumns = that.gridOptions[regIdn].frozenColumns[0];
		for (var i = 0, len = frozenColumns.length; i < len; i++) {
			frozenColumns[i].sortable = sortable;
		}
		var columns = that.gridOptions[regIdn].columns[0];
		for (var i = 0, len = columns.length; i < len; i++) {
			columns[i].sortable = false;
		}

		$(".grid_result_detail", that.selector).datagrid(that.gridOptions[regIdn]);
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
	 *            regIdn 도로대장 아이디
	 */
	modifyMenu : function(regIdn) {/// 수정대상
		var that = this;
		
		$(".a_setting_item", that.selector).show();
	},
	
	/**
	 * 그리드 옵션 생성
	 * 
	 * @param {String}
	 *            regIdn 도로대장 아이디
	 */
	createGridOptions : function(regIdn) {// 수정대상
		var that = this;
		var params = { sysId : SYS_ID };

		var dataId = roadRegObj.getLayerId(regIdn, false);
		
		var kwsData = dataObj.getDataByDataId(dataId, true);
		var dataFields = kwsData.kwsDataFields;
		if (kwsData.dataTy == "ATTRIBUTE") {
			var layerData = dataObj.getDataByRoadRegsterId(dataId);
			if (layerData) {
				var layerFields = layerData.kwsDataFields;
				if (layerFields) {
					for (var j = 0; j < layerFields.length; j++) {
						var col = layerFields[j];
						if (col.sysTy == "COMMON") {
							dataFields.push(col);
						}
					}
				}
			}
		}

		that.dataFields[regIdn] = {
			columns : dataFields	
		};
		
		var frozenColumns = [ {
			field : "itemNo",
			checkbox : false
		} ];

		var pk = null;
		var columns = [];
		for (var i = 0, len = dataFields.length; i < len; i++) {
			var dataField = dataFields[i];
			var fieldId = camelcaseUtils.underbarToCamelcase(dataField.fieldId);
			var fieldAlias = dataField.fieldAlias;
			var indictTy = dataField.indictTy;
			var sysTy = dataField.sysTy;

			if (fieldId == "ftrIdn") {
				pk = fieldId;

				frozenColumns.push({
					field : fieldId,
					title : fieldAlias,
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
						if (fieldAlias != "ID" && fieldAlias != "GEOM") {
							if(sysTy == "COMMON" || sysTy == "ROAD_REGISTER"){
								columns.push({
									field : fieldId,
									title : fieldAlias,
									width : 100,
									sortable : false
								});
							}
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

		that.gridOptions[regIdn] = {
			pk : pk,
			frozenColumns : [ frozenColumns ],
			columns : [ columns ],
			fitColumns : fitColumns
		};
	},
	
	/**
	 * 정렬
	 */
	sortOrdr : function() {// 수정대상
		var that = this;
		var data = that.data;

		if (data.totalCount > 1000) {
			that.loadDetail();
		} else if(data.totalCount > 0){
			var layerIds = [];
			var featureIds = [];
			
			for (var i = 0; i < data.featureIds.length; i++) {
				layerIds.push(data.featureIds[i].dataId);
				featureIds.push(data.featureIds[i].ids);
			}
			
			var params = {};
			params.regIdn = data.regIdn;
			params.layerIds = layerIds;
			params.featureIds = featureIds;
			params.sortField = camelcaseUtils.camelcaseToUnderbar(that.sort);
			params.sortDirection = that.ordr;
			
			var url = CONTEXT_PATH + "/roadRegstr/sortRoadVideo.do";
			$.post(url, params).done(function(response) {
				if (response.rows) {
					that.data.featureIds = response.rows.featureIds;//data.featureIds;
					that.loadDetail();
				}
				else {
					$.messager.alert(that.TITLE, "공간 정보를 불러오는데 실패 했습니다.");
				}
			}).fail(function() {
				$.messager.alert(that.TITLE, "공간 정보를 불러오는데 실패 했습니다.");
			});		
		}
	},
	
	/**
	 * 상세 정보 목록 표시
	 */
	loadDetail : function() {// 수정대상
		var that = this;

		that.removeFeatures();
		var data = that.data;
		var regIdn = data.regIdn;
		var layerCnt = data.featureIds.length;

		// 상세 데이터 가져오기 
		var layerIds = [];
		var featureIds = [];
		var firstIndex = pagingUtils.getFirstIndex(that.pageNumber,	that.pageSize);
		var lastIndex = pagingUtils.getLastIndex(that.pageNumber, that.pageSize);
		
		for (var i = 0; i < layerCnt; i++) {
			var feature = data.featureIds[i];
			var count = feature.ids.length;
			if (firstIndex < count) {
				if (lastIndex <= count) {
					layerIds.push(feature.dataId);
					var ids = feature.ids.slice(firstIndex, lastIndex);
					featureIds.push(ids);
					break;
				}
				else {
					layerIds.push(feature.dataId);
					var ids = feature.ids.slice(firstIndex, count);
					featureIds.push(ids);
					firstIndex = 0;
					lastIndex -= count;
				}
			} else {
				firstIndex = firstIndex - count;
				lastIndex = firstIndex + that.pageSize;
			}
		}

		var params = {};
		params.regIdn = regIdn;
		params.layerIds = layerIds;
		params.featureIds = featureIds;
		params.sortField = camelcaseUtils.camelcaseToUnderbar(that.sort);
		params.sortDirection = that.ordr;
		
		var url = CONTEXT_PATH + "/roadRegstr/searchRoadVideo.do";
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
				$.messager.alert(that.TITLE, "공간 정보를 불러오는데 실패 했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "공간 정보를 불러오는데 실패 했습니다.");
		});
	},

	/**
	 * 항목 설정
	 */
	settingItem : function() {
		var that = this;
		var options = that.gridOptions[that.data.regIdn];
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
	 * 공간 내보내기
	 */
	exportVideo : function() {
		var that = this;
		
		var data = $(".grid_result_detail", that.selector).datagrid("getSelections");
		if (data.length > 0) {
			var pathIdn = null;
			
			for (var i = 0, len = data.length; i < len; i++) {
				var row = data[i];
				
				pathIdn = row["pathIdn"];
			}
			
			var regIdn = that.data.regIdn;
			var url = CONTEXT_PATH + "/roadRegstr/" + regIdn + "/" + pathIdn + "/roadVideo.do";
			
			$("#frmRoadVideoDownload").attr("action", url);
			$("#frmRoadVideoDownload").submit();
		} 
		else {
			$.messager.alert(that.TITLE, "선택된 행이 없습니다.");
		}
	},
	
	
	/**
	 * 도형 표시
	 */
	showFeatures : function() {
		var that = this;
		that.removeFeatures();

		var pathIdn = null;
		var format = new ol.format.WKT();
		var features = [];
		var times = null;
		var data = $(".grid_result_detail", that.selector).datagrid("getSelections");
		for (var i = 0, len = data.length; i < len; i++) {
			var row = data[i];
			
			pathIdn = row["pathIdn"];
			var geom = row[MAP.GEOMETRY_ATTR_NAME];
			var time = row["intervalTimes"];
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
			if (time) {
				times = time;
			}
		}
		
		if (features && features.length > 0) {
			roadVideoObj.init(that.data.regIdn, pathIdn, features, times);
		}
	},

	/**
	 * 도형 표시 제거
	 */
	removeFeatures : function() {
		var that = this;
		roadVideoObj.deactive();
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
}