/**
 * 해안 침식 객체
 */
var beachErosionObj = {
		
	/**
	 * 제목
	 * 
	 * @type {String}
	 */
	TITLE : "해안침식 분석 데이터 조회",
	
	/**
	 * 클래스명
	 * 
	 * @type {String}
	 */
	CLASS_NAME : "BeachErosion",
	
	/**
	 * 선택자
	 * 
	 * @type {String}
	 */
	selector : null,
	
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
		
		// 관측년도
		$(".mesrYy", that.selector).combobox({
			width : 200,
			url : CONTEXT_PATH + "/beachErosion/mesrYy/combobox.do",
			loadFilter : function(response) {
				if(response && response.data) {
					var rows = [{ value:"All", text:"전체" }];
					for(var i in response.data) {
						var value = response.data[i];
						rows.push({
							value : value,
							text : value
						});
					}
					return rows;
				}
				else {
					return null;
				}
			},
			value : "All"
		});
		
		// 대상지역
		$(".zoneNm", that.selector).combobox({
			width : 200,
			url : CONTEXT_PATH + "/beachErosion/zoneNm/combobox.do",
			loadFilter : function(response) {
				if(response && response.data) {
					var rows = [{ value:"All", text:"전체" }];
					for(var i in response.data) {
						var value = response.data[i];
						rows.push({
							value : value,
							text : value
						});
					}
					return rows;
				}
				else {
					return null;
				}
			},
			value : "All"
		});
		
		
		// 관측시작월
		$(".bganMt", that.selector).combobox({
			width : 200,
			url : CONTEXT_PATH + "/beachErosion/bganMt/combobox.do",
			loadFilter : function(response) {
				if(response && response.data) {
					var rows = [{ value:"All", text:"전체" }];
					for(var i in response.data) {
						var value = response.data[i];
						rows.push({
							value : value,
							text : value
						});
					}
					return rows;
				}
				else {
					return null;
				}
			},
			value : "All"
		});
		
		// 관측종료월
		$(".edanMt", that.selector).combobox({
			width : 200,
			url : CONTEXT_PATH + "/beachErosion/edanMt/combobox.do",
			loadFilter : function(response) {
				if(response && response.data) {
					var rows = [{ value:"All", text:"전체" }];
					for(var i in response.data) {
						var value = response.data[i];
						rows.push({
							value : value,
							text : value
						});
					}
					return rows;
				}
				else {
					return null;
				}
			},
			value : "All"
		});
	},
	
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 초기화
		$(".a_reset", that.selector).click(function() {
			$(".mesrYy", that.selector).combobox("setValue", "All");
			$(".zoneNm", that.selector).combobox("setValue", "All");
			$(".bganMt", that.selector).combobox("setValue", "All");
			$(".edanMt", that.selector).combobox("setValue", "All");
		});
		
		// 검색
		$(".a_search", that.selector).click(function() {
			that.search();
		});
		
	},
	
	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/beachErosion/searchBeachErosion/page.do";
		var windowOptions = {
			width : 665,
			height : 190,
			top : 120,
			left : 330,
			isClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.init();
		});
		that.selector = "#" + id;
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	},
	
	/**
	 * 검색
	 */
	search : function() {
		var that = this;
		
		var params = {
			mesrYy : $(".mesrYy", that.selector).combobox("getValue"),
			zoneNm : $(".zoneNm", that.selector).combobox("getValue"),
			bganMt : $(".bganMt", that.selector).combobox("getValue"),
			edanMt : $(".edanMt", that.selector).combobox("getValue")	
		};
		                         
		var url = CONTEXT_PATH + "/beachErosion/listCount.do";
		$.get(url, params).done(function(response) {
			if(response && response.rowCount > 0) {
				that.resultObj.open(response.rowCount, params);
			}
			else {
				$.messager.alert(that.TITLE, "해안침식분석 결과가 없습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "해안침식분석 결과를 가져오는데 실패했습니다.");
		});
		
	}
		
};

/**
 * 해안침식 결과 객체
 */
beachErosionObj.resultObj = /**
 * @author admin
 *
 */
{

	/**
	 * 제목
	 * 
	 * @type {String}
	 */
	TITLE : "해안침식 분석 결과 ",

	/**
	 * 클래스명
	 * 
	 * @type {String}
	 */
	CLASS : "BeachErosionResult",
	
	/**
	 * 선택자
	 * 
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 파라미터 목록
	 * 
	 * @type {Array.<String>}
	 */
	params : null,

	/**
	 * 페이지 번호
	 */
	pageNumber : 1,

	/**
	 * 페이지 사이즈
	 */
	pageSize : 10,
	
	/**
	 * 결과 창 열기
	 * @param totalCount 총 개수
	 * @param params 파라미터 목록
	 */
	open : function(totalCount, params) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/beachErosion/beachErosionResult/page.do";
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
			that.params = params;
			that.totalCount = totalCount;
			
			that.initUi();
			that.loadLayer();
			that.loadDetail();
			that.addLayer();
		});
		that.selector = "#" + id;
		
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.pageNumber = 1;
		that.pageSize = 10;
		// 화면 크기 변환 시 결과 목록 영역 크기 조절 이벤트 해제
		mainObj.removeResizeHandler(that.CLASS_NAME);
		that.removeLayer();
	},
	
	/**
	 * UI 초기화
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
			 singleSelect : true
		});
		
		// 상세 정보 데이터 그리드 생성
		$(".grid_result_detail", that.selector).datagrid({
			columns : [[
			    { field:"mesrYy", title:"측량년도", width:100 },
			    { field:"bganMt", title:"분석시작월", width:100 },
			    { field:"edanMt", title:"분석종료월", width:100 },
			    { field:"zoneNm", title:"측량구역명칭", width:100 },
			    { field:"washAr", title:"침식면적", width:100 },
			    { field:"washVl", title:"침식체적량", width:100 },
			    { field:"amnAr", title:"퇴적면적", width:100 },
			    { field:"amnVl", title:"퇴적체적량", width:100 }
			]],
			pagination : true,
			fitColumns : true,
			singleSelect : true,
			onSelect : function(rowIndex, rowData) {
				that.toggleLayer(rowData);
			}
		});
		var pagination = $(".grid_result_detail", that.selector).datagrid("getPager");
		pagination.pagination({
			displayMsg : "{from} - {to} / {total}",
			onSelectPage : function(pageNumber, pageSize) {
				that.pageNumber = pageNumber;
				that.pageSize = pageSize;
				that.loadDetail();
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
	 * 레이어 불러오기
	 * @param totalCount 총 개수 
	 */
	loadLayer : function(totalCount) {
		var that = this;
		var rows = [{dataId:"VIEW_OCL_BERS_MA", dataAlias:"해안침식", totalCount : that.totalCount}];
		$(".grid_result_layers", that.selector).datagrid("loadData", rows);
		
	},
	
	/**
	 * 상세정보 불러오기
	 */
	loadDetail : function() {
		var that = this;
		var params = $.extend({pageIndex : that.pageNumber, pageSize : that.pageSize}, that.params);
		var url = CONTEXT_PATH + "/beachErosion/list.do";
		$.get(url, params).done(function(response) {
			var data = {
				total : that.totalCount,
				rows : response.rows
			};
			$(".grid_result_detail", that.selector).datagrid("loadData", data);
		}).fail(function() {
			$.messager.alert(that.TITLE, "해안침식분석 결과를 가져오는데 실패했습니다.");
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
			height : height - 5
		});
		
	},
	
	/**
	 * 레이어 추가
	 */
	addLayer : function() {
		var that = this;
		
		var map = mapObj.getMap();
		
		var beachErosionLayer = new ol.layer.Image({
			id : that.CLASS_NAME,
			source : new kworks.source.ImageWMS({
				url : CONTEXT_PATH + "/proxy/wms.do",
				params : $.extend(serverObj.getWMSParams(), { LAYERS : serverObj.getBlankLayers(), STYLES : serverObj.getBlankLayers() }),
				ratio : 1,
				serverType : MAP.SERVER_TYPE
			})
		});
		var newLayers = new ol.Collection();
		var layers = map.getLayers();
		for(var i=0, len=layers.get("length"); i < len; i++) {
			var layer = layers.item(i);
			newLayers.push(layer);
			if(layer.get("id") == "kc_wms") {
				newLayers.push(beachErosionLayer);
			}
		}
		map.getLayerGroup().setLayers(newLayers);
	},
	
	/**
	 * 레이어 삭제
	 */
	removeLayer : function() {
		var that = this;
		var map = mapObj.getMap();
		var layers = map.getLayers();
		
		var layer = map.getLayer(that.className);
		layers.remove(layer);
	},

	/**
	 * 선택된 레이어로 변경
	 * @param rowData 선택된 행 정보
	 */
	toggleLayer : function(rowData) {
		var that = this;
		var map = mapObj.getMap();
		
		var layerNames = [rowData.lyrId];
		
		if(layerNames && layerNames.length > 0) {
			var layer = map.getLayer(that.CLASS_NAME);
			sldObj.reloadWMSFromSld("BeachErosion", "해안 침식", layer, layerNames);
			
			var filter = {
				filterType : "COMPARISON",
				comparisonType : "EQUAL_TO",
				property : "ZONE_CDE",
				value : rowData.zoneCde,
				isOriginColumnValue : true
			};
			spatialSearchUtils.listAll(that.TITLE, rowData.lyrId, filter, function(rows) {
				var format = new ol.format.WKT();
				var features = [];
				for (var i = 0, len = rows.length; i < len; i++) {
					var row = rows[i];
					var feature = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);
					features.push(feature);
				}
				highlightObj.moveFeatures(features, {
					projection : ol.proj.get(MAP.PROJECTION)
				});
			});
			
		}
		
	}
		
};