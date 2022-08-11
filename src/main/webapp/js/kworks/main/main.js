$(function() {
	// 주제도 목록 불러오기 (X-Ray 레이어 목록이 필요해서 먼저 호출)
	themamapObj.load().done(function() {
		$("#div_loading").show();
		
		// 사용자 환경 설정 불러오기
		userEnvironmentObj.load().done(function() {
			// 지도 초기화
			gisObj.init().done(function() {
				// 배경지도 초기화
				backgroundMapObj.init(true).done(function() {
					//실행순서 유의
					themamapObj.basemapObj.show(); // 기본지도 레이어 표시
					
					mainObj.init();	            // 메인 객체 초기화
					userAuthorObj.init();		// 사용자 권한 객체 초기화
					
					quickObj.init();			// Quick 초기화
					dataObj.init();				// 데이터 객체 초기화
					baseMapAuthorObj.init();	// 기본지도 권한 객체 초기화
					
					dongObj.init();				// 동 객체 초
					if(EXTENSIONS.QUICK_SEARCH_DETAIL) {
						dongLiObj.init();		// 읍면동 객체 초기화 (빠른검색 확장)
						liObj.init();			// 리 객체 초기화 (빠른검색 확장)
					}
					roadObj.init();				// 도로 객체 초기화
					
					quickSearchObj.init();		// 빠른 검색 객체 초기화
					mapToolObj.init();			// 지도 툴 객체 초기화
					multiObj.init();			// 다분면 객체 초기화
					
					menuObj.init();				// 왼쪽 메뉴 객체 초기화
					
					selectObj.init();			// 지도 선택 객체 초기화
					highlightObj.init();		// 하이라이트 객체 초기화
					windowObj.drawToolObj.init();	// 그리기 도구 초기화
					
					editObj.init();				// 편집 객체 초기화
					
					resultObj.init();			// 결과 객체 초기화
					
					mainObj.defaultActive();
					$("#div_loading").hide();
					
					userEnvironmentObj.moveUser();	// 사용자 저장 위치로 이동
					mapObj.bindMoveEnd();
					
					jqueryUtils.configAjax();	// jQuery Ajax 설정
					
					customObj.init();				//지자체볋 실행 코드 - 마지막 실행
				});
			});
		});
	});
});

/**
 * GIS
 * @type {Object}
 */
var gisObj = {
		
	deferred : null,
		
	/**
	 * GIS 기능 초기화
	 */
	init : function() {
		var that = this;
		that.deferred = $.Deferred();
		
		var themamapId = userEnvironmentObj.getData("themamapId");
		if(!themamapId) {
			themamapId = THEMAMAP_ID;
		}
		that.load(themamapId);
		return that.deferred.promise();
	},
	
	load : function(themamapId) {
		var that = this;
		
		// 사용자 환경에 저장된 주제도 or 시스템 기본 주제도 불러오기
		themamapObj.selectOne(themamapId).done(function(themamap, jsonStr) {
			if(themamap) {
				// 좌표계  불러오기
				var cntmPromise = coordinateObj.load();
				
				// 전체 레이어 목록 불러오기
				var layerPromise = layerObj.load();
				
				// SLD 불러오기
				sldObj.setData(JSON.parse(jsonStr));
				var sldPromise = sldObj.putSld();
				// 좌표계, 전체 레이어 목록, SLD 설정이 끝난 후 지도 생성
				$.when(cntmPromise, layerPromise, sldPromise).then(function() {
					mapObj.init();
					indexMapObj.init();
					that.deferred.resolve();
				});
			}
			else {
				$("#div_loading").hide();
				
				// 사용자 주제도가 환경 설정되어 있지만 권한이 없어졌을 경우 시스템 기본 주제도로 표시
				if(userEnvironmentObj.getData("themamapId")) {
					userEnvironmentObj.setData("themamapId", null);
					that.load(THEMAMAP_ID);
				}
				// 기본 주제도에 대한 권한이 없을 경우 포털로 이동
				else {
					$.messager.alert("주제도", "시스템 기본 주제도에 권한을 가진 레이어가 없습니다. 포털 페이지로 이동합니다.", null, function() {
						location.href = CONTEXT_PATH + "/portal.do";
					});
				}
			}
		});
	},
	
	/**
	 * 시스템 좌표계로 좌표 변환
	 * @param {ol.geom.Geometry} geometry 공간 정보
	 * @returns {ol.geom.Geometry} 좌표 변환된 객체
	 */
	toTransformSystem : function(geometry) {
		var from = mapObj.getMap().getView().getProjection();
		var to = ol.proj.get(MAP.PROJECTION);
		return spatialUtils.transform(geometry, from ,to);
	},
	
	/**
	 * 지도 좌표계로 좌표 변환
	 * @param {ol.geom.Geometry} geometry 공간 정보
	 * @returns {ol.geom.Geometry} 좌표 변환된 객체
	 */
	toTransformMap : function(geometry) {
		var from = ol.proj.get(MAP.PROJECTION);
		var to = mapObj.getMap().getView().getProjection();
		return spatialUtils.transform(geometry, from ,to);
	},
	
	/**
	 * 시스템 좌표계로 좌표 변환
	 * @param coordinate 좌표
	 * @returns 변환된 좌표
	 */
	totransformCooridateSystem : function(coordinate) {
		var from = mapObj.getMap().getView().getProjection();
		var to = ol.proj.get(MAP.PROJECTION);
		return spatialUtils.transformCooridate(coordinate, from ,to);
	},
	
	/**
	 * 지도 좌표계로 좌표 변환
	 * @param coordinate 좌표
	 * @returns 변환된 좌표
	 */
	totransformCooridateMap : function(coordinate) {
		var from = ol.proj.get(MAP.PROJECTION);
		var to = mapObj.getMap().getView().getProjection();
		return spatialUtils.transform(coordinate, from ,to);
	}
		
};

/**
 * 메인 객체
 * @type {Object}
 */
var mainObj = {
		
	resizeHandlers : {},
	
	/**
	 * 다분면 창 또는 로드뷰 시 기능 끄기 전까지 다른 기능 막음
	 * @type {Object}
	 */
	singleActiveObj : null,
		
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
		
		// 툴팁 표시
		$(".tootip").tooltip({
			position:'top'
		});
		
		// 지도 화면 크기조정
		that.resizeWindowHandler();
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents: function() {
		var that = this;
		// 웹브라우저 크기 변경 시 지도 화면 크기 변경
		$(window).bind('resize', function() {
			that.resizeWindowHandler();
		});
		
		// 화면 설정 열기
		$("#a_open_settings").click(function() {
			windowObj.settingsObj.open();
		});
		
		// 지도에 적용되는 인터렉션은 하나이기 때문에 다른 버튼은 다 off 시킴
		$("body").on("click", ".tool_toggle_radio", function() {
			var element = $(this);
			$(".tool_toggle_radio").each(function() {
				$(this).attr("src", $(this).attr("src").replace("_on", "_off"));
			});
			element.attr("src", element.attr("src").replace("_ov", "_on").replace("_off", "_on"));
		});
		
		// 마우스 클릭 시 이벤트 toggle
		$("body").on("click", ".img_toggle", function() {
			var element = $(this);
			if(element.hasClass("active")) {
				element.removeClass("active");
				$(this).attr("src", $(this).attr("src").replace("_ov", "_off").replace("_on", "_off"));
			}
			else {
				element.addClass("active");
				$(this).attr("src", $(this).attr("src").replace("_ov", "_on").replace("_off", "_on"));
			}
		});
		
		// 마우스 오버 시 이미지 변경
		$("body").on("mouseover", ".img_ov_off", function() {
			var element = $(this);
			element.attr("src", element.attr("src").replace("_off", "_ov"));
		});
		// 마우스 아웃 시 이미지 변경
		$("body").on("mouseout", ".img_ov_off", function() {
			var element = $(this);
			element.attr("src", element.attr("src").replace("_ov", "_off"));
		});
		
		// 브라우저 종료 시 환경 설정
		$(window).on("beforeunload", function() {
			userEnvironmentObj.save();
		});
	},
	
	addResizeHandler : function(id, handler) {
		var that = this;
		that.resizeHandlers[id] = handler;
	},
	
	removeResizeHandler : function(id) {
		var that = this;
		if(that.resizeHandlers[id]) {
			delete that.resizeHandlers[id];
		}
	},
	
	/**
	 * 윈도우 크기 변경 시 실행되는 함수
	 */
	resizeWindowHandler : function() {
		var that = this;
		var width = $(window).width();
		var height = $(window).height();
		for(var id in that.resizeHandlers) {
			var handler = that.resizeHandlers[id];
			handler(width, height);
		}
	},
	
	setSingleActiveObj : function(obj) {
		var that = this;
		that.singleActiveObj = obj;
	},
	
	getSingleActiveObj : function(obj) {
		var that = this;
		return that.singleActiveObj;
	},
	
	getActive : function(obj) {
		var that = this;
		if(that.singleActiveObj) {
			if(obj) {
				if(that.singleActiveObj == obj) {
					return null;
				}
				else {
					$.messager.alert(that.singleActiveObj.TITLE, that.singleActiveObj.TITLE + " 활성화 시 지원되지 않는 기능입니다.");
					return that.singleActiveObj;
				}
			}
			else {
				$.messager.alert(that.singleActiveObj.TITLE, that.singleActiveObj.TITLE + " 활성화 시 지원되지 않는 기능입니다.");
				return that.singleActiveObj;
			}
		}
		else {
			return null;
		}
	},
	
	defaultActive : function() {
		// 이동 기능
		$("#a_map_tool_bass img").trigger("click");
	},
	
	deactive : function() {
		mapToolObj.deactive();
		menuObj.close();
		resultObj.close();
		if (customObj[PROJECT_CODE + 'Obj']) {
			if (customObj[PROJECT_CODE + 'Obj']['deactive']) {
				customObj[PROJECT_CODE + "Obj"].deactive(SYS_ID);
			}
		}
	}
		
};

/**
 * 빠른 검색 개체
 * @type {Object}
 */
var quickSearchObj = {
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_quick_search",
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "빠른 검색",
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.initUi();
		
		if(EXTENSIONS.QUICK_SEARCH_DETAIL) {
			that.legalDongLiObj.init();
		}
		else {
			that.legalDongObj.init();
		}
		
		that.roadNameObj.init();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		/// 법정동, 도로명 콤보 박스
		$(".sel_search_type", that.selector).combobox({
			onSelect : function(data) {
				var value = data.value;
				if(EXTENSIONS.QUICK_SEARCH_DETAIL) {
					if(value == "legalDong") {
						$("#div_quick_search_road_name").hide();
						$("#div_quick_search_legal_dong").hide();
						$("#div_quick_search_legal_dong_li").show();
					}
					else {
						$("#div_quick_search_legal_dong").hide();
						$("#div_quick_search_legal_dong_li").hide();
						$("#div_quick_search_road_name").show();
					}
				}
				else {
					if(value == "legalDong") {
						$("#div_quick_search_road_name").hide();
						$("#div_quick_search_legal_dong_li").hide();
						$("#div_quick_search_legal_dong").show();
					}
					else {
						$("#div_quick_search_legal_dong").hide();
						$("#div_quick_search_legal_dong_li").hide();
						$("#div_quick_search_road_name").show();
					}
				}
			}
		});
	}

};

/**
 * 법정동 검색
 * @type {Object}
 */
quickSearchObj.legalDongObj = {

	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_quick_search_legal_dong",
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "법정동 검색",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "QuickSearchDong",
	
	/**
	 * 이동 여부 - 지도 이동일 경우는 동 선택 기능 작동 안하기 위해서 사용
	 * @type {Boolean}
	 */
	isMove  : false,
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.initUi();
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
			valueField : COMMON.PK,
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD),
			onSelect : function(record) {
				if(that.isMove) {
					that.moveDong(record);
					that.isMove = false;
				}
			}
		});
		
		// 본번
		$(".txt_mnnm", that.selector).numberspinner({
			required : true,
			min : 1,
			max : 9999,
			//value : 1,
			increment : 1
		});
		
		// 부번
		$(".txt_slno", that.selector).numberspinner({
			min : 0,
			max : 9999,
			increment : 1
		});
		
		// 검색
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
		
	},
	
	/**
	 * 로딩
	 */
	load : function() {
		var that = this;
		dongObj.getPromise().done(function() {
			var data = dongObj.getData();
			$(".sel_dong", that.selector).combobox("loadData", data);
			that.bindEvents();
			that.selectDong();
		});
	},
	
	/**
	 * 이벤트 바인딩
	 */
	bindEvents : function() {
		var that = this;
		
		// 지도 이동 시 법정동 선택
		mapObj.getMap().on("moveend", function(evt) {
			that.selectDong();
		});
		
		// 지도 이동일 경우는 동 선택 기능 작동 안하기 위해서 사용
		$(".sel_dong", that.selector).combobox("panel").find(".combobox-item").click(function() {
			that.isMove = true;
		});
		
		// 지번 텍스트박스 클릭 시 텍스트박스 클리어기능 - 동해시 요구사항
		$(".txt_mnnm", that.selector).numberspinner("textbox").click(function(){
			$(".txt_mnnm", that.selector).numberspinner('clear');
		});
		
		// 부번 텍스트박스 클릭 시 텍스트박스 클리어기능
		$(".txt_slno", that.selector).numberspinner("textbox").click(function(){
			$(".txt_slno", that.selector).numberspinner('clear');
		});
		
		// 본번에서 엔터 시 부번으로 이동 및 텍스트박스 클리어기능
		$(".txt_mnnm", that.selector).numberspinner("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".txt_slno", that.selector).numberspinner("textbox").focus();
				$(".txt_slno", that.selector).numberspinner('clear');
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
				that.search();
				quickObj.triggerOnLand();
			}
			return false;
		});
		
	},
	
	/**
	 * 지도 이동에 따른 동 선택
	 */
	selectDong : function() {
		var that = this;
		var center = mapObj.getMap().getCenter();
		if(center) {
			var geom = new ol.geom.Point(center);
			var mapProjection = mapObj.getMap().getView().getProjection();
			var sysProjection = ol.proj.get(MAP.PROJECTION);
			var transformGeometry = spatialUtils.transform(geom, mapProjection, sysProjection);
			
			var format = new ol.format.WKT();
			var wkt = format.writeFeature(new ol.Feature(transformGeometry));
			
			// 필터
			var filter = {
				filterType : "SPATIAL",
				spatialType : "INTERSECTS",
				wkt : wkt
			};
			// 공간 검색 - isNoMessage : 영역을 벗어나더라도 Alert 창 뜨지 않도록 함
			spatialSearchUtils.listAllSummary(that.TITLE, dongObj.getDataId(), filter, function(data) {
				if(data) {
					var ids = data.ids;
					if(ids.length > 0) {
						$(".sel_dong", that.selector).combobox("select", ids[0]);
					}
				}
			}, { isNoMessage : true });
		}
	},
	
	/**
	 * 동 선택 시 지도 이동
	 * @param {Object} node 동 정보
	 */
	moveDong : function(node) {
		var format = new ol.format.WKT();
		var features = [format.readFeature(node[MAP.GEOMETRY_ATTR_NAME])];
		if(features && features.length > 0) {
			//var geom = gisObj.toTransformSystem(features[0].getGeometry());
			var geom = gisObj.toTransformMap(features[0].getGeometry());
			var extent = geom.getExtent();
			var center = ol.extent.getCenter(extent);
			
			var view = mapObj.getMap().getView();
			var resolution = view.constrainResolution(view.getResolutionForExtent(extent, mapObj.getMap().getSize()), -2);
			
			// 이동한 중심점이 법정동 영역 안에 들어가지 않을 경우 도형의 중심점을 구해서 이동
			if(!geom.containsXY(center[0], center[1])) {
				var polygon = spatialUtils.toJstsGeometry(geom);
				var centroid = polygon.getCentroid().getCoordinate();
				center = [centroid.x, centroid.y];
			}
			
			mapObj.getMap().moveToResolution(resolution);
			mapObj.getMap().moveToCenter(center);
		}
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
		// 본번
		if(!$(".txt_mnnm", that.selector).numberspinner("isValid")) {
			$.messager.alert(that.TITLE, "본번을 입력하여 주십시오.", null);
			return false;
		}
		return true;
	},
	
	/**
	 * 검색
	 */
	search : function() {
		var that = this;
		
		// 동 코드 
		var dongCode = null;
		var dongData = $(".sel_dong", that.selector).combobox("getData");
		var dongId = $(".sel_dong", that.selector).combobox("getValue");
		for(var i=0, len=dongData.length; i < len; i++) {
			if(dongData[i][COMMON.PK] == dongId) {
				dongCode = dongData[i][camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD)];
			}
		}
		if(!dongCode) {
			$.messager.alert(that.TITLE, "법정동을 선택하여 주십시오.");
			return;
		}
		
		var mntn = $(".chk_mntn", that.selector).is(":checked")?"2":"1";
		var mnnm = $(".txt_mnnm", that.selector).numberspinner("getValue");
		var slno = $(".txt_slno", that.selector).numberspinner("getValue");
		
		var pnu = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
		var filter = {
			filterType : "COMPARISON",
			comparisonType : "EQUAL_TO",
			property : "PNU",
			value : pnu
		};
		spatialSearchUtils.listAll(that.TITLE, COMMON.LAND, filter, function(rows) {
			if(rows && rows.length > 0) {
				var features = [];
				var format = new ol.format.WKT();
				for(var i=0, len=rows.length; i < len; i++) {
					var data = rows[i];
					features.push(format.readFeature(data[MAP.GEOMETRY_ATTR_NAME]));
				}
				highlightObj.showRedFeatures(that.CLASS_NAME, features, true, {
					projection : ol.proj.get(MAP.PROJECTION)
				});
			}
		});
	}
};

/**
 * 도로명 검색
 * @type {Object}
 */
quickSearchObj.roadNameObj = {

	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_quick_search_road_name",
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도로명주소 검색",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "QuickSearchRoadName",
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.initUi();
		that.bindEvents();
		that.load();
	},
	
	initUi : function() {
		var that = this;
		
		// 도로명 목록
		$(".sel_road_name", that.selector).combobox({
			required : true,
			valueField : "rdnCde",
			textField : "rdnNam"
		});
		
		// 본번
		$(".txt_mnnm", that.selector).numberspinner({
			required : true,
			min : 1,
			max : 9999,
			//value : 1,
			increment : 1
		});
		
		// 부번
		$(".txt_slno", that.selector).numberspinner({
			min : 0,
			max : 9999,
			increment : 1
		});
		
		// 검색
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
		
	},
	
	load : function() {
		var that = this;
		roadObj.getPromise().done(function() {
			var data = roadObj.getData();
			$(".sel_road_name", that.selector).combobox("loadData", data);
			if(data.length > 0) {
				$(".sel_road_name", that.selector).combobox("select", data[0].rdnCde);
			}
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 지번 클릭시 클리어기능
		$(".txt_mnnm", that.selector).numberspinner("textbox").click(function(){
			$(".txt_mnnm", that.selector).numberspinner('clear');
		});
		
		// 부번 클릭시 클리어기능
		$(".txt_slno", that.selector).numberspinner("textbox").click(function(){
			$(".txt_slno", that.selector).numberspinner('clear');
		});
		
		// 본번에서 엔터 시 부번으로 이동
		$(".txt_mnnm", that.selector).numberspinner("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".txt_slno", that.selector).numberspinner("textbox").focus();
			}
		});
		
		// 부번에서 엔터 시 검색 및 텍스트박스 클리어기능
		$(".txt_slno", that.selector).numberspinner("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".a_search", that.selector).trigger("click");
				$(".txt_slno", that.selector).numberspinner('clear');
			}
		});
		
		// 검색
		$(".a_search", that.selector).click(function() {
			if(that.validator()) {
				that.search();
				quickObj.triggerOnBuld();
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
		
		// 도로명
		if(!$(".sel_road_name", that.selector).combobox("isValid")) {
			$.messager.alert(that.TITLE, "도로명을 선택하여 주십시오.");
			return false;
		}
		// 본번
		if(!$(".txt_mnnm", that.selector).numberspinner("isValid")) {
			$.messager.alert(that.TITLE, "본번을 입력하여 주십시오.", null);
			return false;
		}
		return true;
	},
	
	/**
	 * 검색
	 */
	search : function() {
		var that = this;
		
		// 도로명 코드 
		var rdnCde = $(".sel_road_name", that.selector).combobox("getValue");
		if(!roadObj.checkRoadCode(rdnCde)) {
			$.messager.alert(that.TITLE, "도로명을 선택하여 주십시오.");
			return;
		}
		
		var mnnm = $(".txt_mnnm", that.selector).numberspinner("getValue");
		var slno = $(".txt_slno", that.selector).numberspinner("getValue");
		if(!slno) slno = 0;
		
		var cql = "";
		cql += "RN_CD = '" + rdnCde + "' ";
		cql += "AND BULD_MNNM = '" + mnnm + "' ";
		cql += "AND BULD_SLNO = '" + slno + "' ";
		
		var filter = {
			filterType : "CQL",
			cql : cql
		};
		spatialSearchUtils.listAll(that.TITLE, COMMON.BULD, filter, function(rows) {
			if(rows && rows.length > 0) {
				var features = [];
				var format = new ol.format.WKT();
				for(var i=0, len=rows.length; i < len; i++) {
					var data = rows[i];
					features.push(format.readFeature(data[MAP.GEOMETRY_ATTR_NAME]));
				}
				highlightObj.showRedFeatures(that.CLASS_NAME, features, true, {
					projection : ol.proj.get(MAP.PROJECTION)
				});
			}
		});
	}
		
};

quickSearchObj.legalDongLiObj = {

	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_quick_search_legal_dong_li",
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "법정동리 검색",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "QuickSearchDongLi",
	
	/**
	 * 이동 여부 - 지도 이동일 경우는 동 선택 기능 작동 안하기 위해서 사용
	 * @type {Boolean}
	 */
	isMove  : false,
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		
		that.initUi();
		that.load();
	},
	
	initUi : function() {
		var that = this;
		
		// 법정동 목록
		$(".sel_dong", that.selector).combobox({
			required : true,
			valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONGLI_CODE_FIELD),
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONGLI_NAME_FIELD),
			onSelect : function(record) {
				if(that.isMove) {
					that.moveDong(record);
					that.isMove = false;
				}
			}
		});
		
		// 리 목록
		$(".sel_li", that.selector).combobox({
			required : true,
			valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD),
			onSelect : function(record) {
				if(that.isMove) {
					that.moveLi(record);
					that.isMove = false;
				}
			}
		});
		
		// 본번
		$(".txt_mnnm", that.selector).numberspinner({
			required : true,
			min : 1,
			max : 9999,
			increment : 1
		});
		
		// 부번
		$(".txt_slno", that.selector).numberspinner({
			min : 0,
			max : 9999,
			increment : 1
		});
		
		// 검색
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
	},
	
	load : function() {
		var that = this;
		
		that.bindEvents();
		that.searchLiCode();
	},
	
	searchLiCode : function() {
		var that = this;
		
		var center = mapObj.getMap().getCenter();
		if(center) {
			var geom = new ol.geom.Point(center);
			var mapProjection = mapObj.getMap().getView().getProjection();
			var sysProjection = ol.proj.get(MAP.PROJECTION);
			var transformGeometry = spatialUtils.transform(geom, mapProjection, sysProjection);
			
			var format = new ol.format.WKT();
			var wkt = format.writeFeature(new ol.Feature(transformGeometry));
			
			// 필터
			var filter = {
				filterType : "SPATIAL",
				spatialType : "INTERSECTS",
				wkt : wkt,
				isOriginColumnValue : true
			};
			spatialSearchUtils.listAll(that.TITLE, liObj.getDataId(), filter, function(data) {
				if(data && data.length > 0) {
					var liCode = data[0].bjdCde;
					var emdCode = liCode.substring(0,8);

					that.searchEmd(emdCode);
					that.searchLi(emdCode, liCode);
				}
			}, {
				isNoMessage : true
			});
		}
	},
	
	searchEmd : function(emdCode) {
		var that = this;
		
		dongLiObj.getPromise().done(function() {
			var data = dongLiObj.getData();
			
			$(".sel_dong", that.selector).combobox("loadData", data);
			$(".sel_dong", that.selector).combobox("setValue", emdCode);
			
			// 지도 이동일 경우는 동 선택 기능 작동 안하기 위해서 사용
			$(".sel_dong", that.selector).combobox("panel").find(".combobox-item").unbind("click");
			$(".sel_dong", that.selector).combobox("panel").find(".combobox-item").click(function() {
				that.isMove = true;
			});
		});
	},
	
	searchLi : function(emdCode, liCode) {
		var that = this;
		
		var filter = {
			filterType : "COMPARISON",
			comparisonType : "LIKE",
			property : "BJD_CDE",
			value : emdCode+"%",
			sortOrdr : "BJD_NAM",
			isOriginColumnValue : true
		};
		
		spatialSearchUtils.listAll(that.TITLE, liObj.getDataId(), filter, function(data) {
			if(data) {
				$(".sel_li", that.selector).combobox("loadData", data);
				$(".sel_li", that.selector).combobox("setValue", liCode);
				
				// 지도 이동일 경우는 리 선택 기능 작동 안하기 위해서 사용
				$(".sel_li", that.selector).combobox("panel").find(".combobox-item").unbind("click");
				$(".sel_li", that.selector).combobox("panel").find(".combobox-item").click(function() {
					that.isMove = true;
				});
			}
		}, {
			isNoMessage : true
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 지도 이동 시 법정동 선택
		mapObj.getMap().on("moveend", function(evt) {
			that.searchLiCode();
		});
				
		// 본번에서 엔터 시 부번으로 이동 및 텍스트박스 클리어기능
		$(".txt_mnnm", that.selector).numberspinner("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".txt_slno", that.selector).numberspinner("textbox").focus();
				$(".txt_slno", that.selector).numberspinner('clear');
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
				that.search();
				quickObj.triggerOnLand();
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
		
		// 법정동
		if(!$(".sel_dong", that.selector).combobox("isValid")) {
			$.messager.alert(that.TITLE, "법정동을 선택하여 주십시오.");
			return false;
		}
		// 본번
		if(!$(".txt_mnnm", that.selector).numberspinner("isValid")) {
			$.messager.alert(that.TITLE, "본번을 입력하여 주십시오.", null);
			return false;
		}
		return true;
	},
	
	moveLi : function(node) {
		var format = new ol.format.WKT();
		var features = [format.readFeature(node[MAP.GEOMETRY_ATTR_NAME])];
		if(features && features.length > 0) {
			//var geom = gisObj.toTransformSystem(features[0].getGeometry());
			var geom = gisObj.toTransformMap(features[0].getGeometry());
			var extent = geom.getExtent();
			var center = ol.extent.getCenter(extent);
			
			var view = mapObj.getMap().getView();
			var resolution = view.constrainResolution(view.getResolutionForExtent(extent, mapObj.getMap().getSize()), -2);
			
			// 이동한 중심점이 법정동 영역 안에 들어가지 않을 경우 도형의 중심점을 구해서 이동
			if(!geom.containsXY(center[0], center[1])) {
				var polygon = spatialUtils.toJstsGeometry(geom);
				var centroid = polygon.getCentroid().getCoordinate();
				center = [centroid.x, centroid.y];
			}
			
			mapObj.getMap().moveToResolution(resolution);
			mapObj.getMap().moveToCenter(center);
		}
	},
	
	moveDong : function(node) {
		var format = new ol.format.WKT();
		var features = [format.readFeature(node[MAP.GEOMETRY_ATTR_NAME])];
		if(features && features.length > 0) {
			//var geom = gisObj.toTransformSystem(features[0].getGeometry());
			var geom = gisObj.toTransformMap(features[0].getGeometry());
			var extent = geom.getExtent();
			var center = ol.extent.getCenter(extent);
			
			var view = mapObj.getMap().getView();
			var resolution = view.constrainResolution(view.getResolutionForExtent(extent, mapObj.getMap().getSize()), -2);
			
			// 이동한 중심점이 법정동 영역 안에 들어가지 않을 경우 도형의 중심점을 구해서 이동
			if(!geom.containsXY(center[0], center[1])) {
				var polygon = spatialUtils.toJstsGeometry(geom);
				var centroid = polygon.getCentroid().getCoordinate();
				center = [centroid.x, centroid.y];
			}
			
			mapObj.getMap().moveToResolution(resolution);
			mapObj.getMap().moveToCenter(center);
		}
	},
	
	/**
	 * 검색
	 */
	search : function() {
		var that = this;
		
		var liCode = $(".sel_li", that.selector).combobox("getValue");
		var mntn = $(".chk_mntn", that.selector).is(":checked")?"2":"1";
		var mnnm = $(".txt_mnnm", that.selector).numberspinner("getValue");
		var slno = $(".txt_slno", that.selector).numberspinner("getValue");
		
		var pnu = pnuObj.createPnu(liCode, mntn, mnnm, slno);
		var filter = {
			filterType : "COMPARISON",
			comparisonType : "EQUAL_TO",
			property : "PNU",
			value : pnu
		};
		spatialSearchUtils.listAll(that.TITLE, COMMON.LAND, filter, function(rows) {
			if(rows && rows.length > 0) {
				var features = [];
				var format = new ol.format.WKT();
				for(var i=0, len=rows.length; i < len; i++) {
					var data = rows[i];
					features.push(format.readFeature(data[MAP.GEOMETRY_ATTR_NAME]));
				}
				highlightObj.showRedFeatures(that.CLASS_NAME, features, true, {
					projection : ol.proj.get(MAP.PROJECTION)
				});
			}
		});
	}
};

/**
 * 지도 툴 객체
 * @type {Object}
 */
var mapToolObj = {
		
	/**
	 * 실행중인 객체
	 * @type {Object}
	 */
	activeObj : null,
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		
		that.initUi();
		that.bindEvents();
		that.handlerSldChange();
		mapToolObj.spatialInfoObj.init();	// 공간 확인 객체 초기화 
		mapToolObj.multiObj.init();			// 다분면 객체 초기화
		mapToolObj.roadviewObj.init();		// 로드뷰 객체 초기화
		
		mapToolObj.moveAerialMapObj.init();	// 항공영상 위치조절 초기화
	},
	
	initUi : function() {
		var that = this;
		that.createMapTools();
		mainObj.addResizeHandler("mapTool", function(width, height) {
			that.resizeWindowHandler(width, height);
		});
	},
	
	createMapTools : function() {
		var tagStr = '';
		for(var i=0, len=MAP_TOOLS.length; i < len; i++) {
			var tool = MAP_TOOLS[i];
			var liId = "li_map_tool_" + tool.id;
			if(tool.type == "gisbutton") {
				var id = "a_map_tool_" + tool.id;
				var title = tool.title;
				var format = tool.format?tool.format:"svg";
				tagStr += '<li id="'+liId+'"><a class="kw_toptoolbar_a" id="'+ id +'" href="#"><img class="tool_toggle_radio img_ov_off tootip" src="'+CONTEXT_PATH+'/images/kworks/map/svg/topTools/'+tool.id+'_on.'+ format +'" alt="'+title+'" title="'+title+'" /></a></li>';
			}
			else if(tool.type == "button") {
				var id = "a_map_tool_" + tool.id;
				var title = tool.title;
				var format = tool.format?tool.format:"svg";
				tagStr += '<li id="'+liId+'"><a class="kw_toptoolbar_a" id="'+ id +'" href="#"><img class="img_ov_off tootip"' +  (tool.width ? ' style="width:' + tool.width + 'px;"' : '') + ' src="'+CONTEXT_PATH+'/images/kworks/map/svg/topTools/'+tool.id+'_off.'+ format +'" alt="'+title+'" title="'+title+'" /></a></li>';
			}
			else if(tool.type = "line") {
				tagStr += '<li id="'+liId+'" class="li_line"><img src="'+CONTEXT_PATH+'/images/kworks/map/skin2/ic_set_line.png" /></li>';
			}
		}
		$("#ul_map_toolbar").html(tagStr);
	},
	
	resizeWindowHandler : function(width, height) {
		var offset = 20;
		var quickSearchWidth = $("#div_quick_search").width();
		var toolsWidth = width - quickSearchWidth - offset;
		
		var size = 41;
		for(var i=0, len=MAP_TOOLS.length; i < len; i++) {
			var tool = MAP_TOOLS[i];
			size += tool.width;
			var id = "li_map_tool_" + tool.id;
			if(size > toolsWidth) {
				if($("#ul_map_toolbar").has($("#"+id))) {
					$("#"+id).detach().appendTo("#ul_map_toolbar_vertical");
				}
				if(tool.type == "line") {
					$("#"+id).hide();
				}
			}
			else {
				if($("#ul_map_toolbar_vertical").has($("#"+id))) {
					$("#"+id).detach().appendTo("#ul_map_toolbar");
				}
				if(tool.type == "line") {
					$("#"+id).show();
				}
			}
		}
		if($("#ul_map_toolbar li:last").hasClass("li_line")) {
			$("#ul_map_toolbar li:last").hide();
		}

		if($("#ul_map_toolbar_vertical li").length > 0) {
			$("#ul_map_toolbar_on_off").show();
		}
		else {
			$("#ul_map_toolbar_on_off").hide();
		}
	},
	
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 지도 세로 툴 열기
		$("#li_map_tool_on").click(function() {
			$("#ul_map_toolbar_vertical").show();
			$("#li_map_tool_on").hide();
			$("#li_map_tool_off").show();
			return false;
		});
		
		// 지도 세로 툴 닫기
		$("#li_map_tool_off").click(function() {
			$("#ul_map_toolbar_vertical").hide();
			$("#li_map_tool_on").show();
			$("#li_map_tool_off").hide();
			return false;
		});
		
		// 기본 기능 - 다른 기능에 영향 받지 않고 실행 가능
		$("#a_map_tool_bass").click(function(event) {
			if(!mainObj.getActive()) {
				mapObj.getMap().activeInteractions("drag", "videoSelect");
				event.preventDefault();
				
				if (that.activeObj && (that.activeObj.CLASS_NAME == 'Measure' || that.activeObj.CLASS_NAME == 'Draw')) {
					that.activeObj = null;
				}
			}
			else {
				if (that.activeObj && (that.activeObj.CLASS_NAME == 'Measure' || that.activeObj.CLASS_NAME == 'Draw')) {
					that.activeObj = null;
				}
				
				return false;
			}
		});
		
		// 확대
		$("#a_map_tool_expansion").click(function(event) {
			if(!mainObj.getActive()) {
				mapObj.getMap().activeInteractions("dragZoomIn");
				event.preventDefault();
			}
			else {
				return false;
			}
		});
		
		// 축소
		$("#a_map_tool_reduction").click(function(event) {
			if(!mainObj.getActive()) {
				mapObj.getMap().activeInteractions("dragZoomOut");
				event.preventDefault();
			}
			else {
				return false;
			}
		});
		
		// 지우기
		$("#a_map_tool_eraser").click(function() {
			if(!mainObj.getActive()) {
				highlightObj.removeFeatures();
				kworks.interaction.Measure.clear(mapObj.getMap());
			}
			return false;
		});
		
		// 돋보기
		$("#a_map_tool_magnifier").click(function() {
			$("#a_map_tool_bass img").trigger("click");
			that.active(mapToolObj.zoomObj);
			return false;
		});
		
		// 엑스레이
		$("#a_map_tool_xray").click(function() {
			$("#a_map_tool_bass img").trigger("click");
			that.active(mapToolObj.xrayObj);
			return false;
		});
		
		// 측정
		$("#a_map_tool_measurement").click(function() {
			that.active(mapToolObj.measureObj);
			return false;
		});
		
		// 그리기 도구
		$("#a_map_tool_draw").click(function() {
			that.active(mapToolObj.drawToolObj);
			return false;
		});
		
		// 공간 확인
		$("#a_map_tool_spatial_info").click(function(event) {
			if(that.active(mapToolObj.spatialInfoObj)) {
				event.preventDefault();
			}
			else {
				return false;
			}
		});
		
		// 공간 검색
		$("#a_map_tool_spatial_search").click(function() {
			that.active(mapToolObj.spatialSearchObj);
			return false;
		});
		
		// 다분면
		$("#a_map_tool_multi").click(function() {
			if(!mainObj.getActive(mapToolObj.multiObj)) {
				mapToolObj.multiObj.toggle();
			}
			return false;
		});
		
		// 로드 뷰
		$("#a_map_tool_roadview").click(function(event) {
			if(!mainObj.getActive(mapToolObj.roadviewObj)) {
				mainObj.deactive();
				$(".tool_toggle_radio").each(function() {
					$(this).attr("src", $(this).attr("src").replace("_on", "_off"));
				});
				mapToolObj.roadviewObj.toggle();
				event.preventDefault();
			}
			else {
				return false;
			}
		});
		
		// 드론
		$("#a_map_tool_dron").click(function(event) {
			if(!mainObj.getActive(mapToolObj.dronObj)) {
				mainObj.deactive();
				$(".tool_toggle_radio").each(function() {
					$(this).attr("src", $(this).attr("src").replace("_on", "_off"));
				});
				mapToolObj.moveMap.toggle();
				event.preventDefault();
			}
			else {
				return false;
			}
		});
		
		// 항공영상 위치조절
		$("#a_map_tool_moveAerialMap").click(function(event) {
			if(!mainObj.getActive(mapToolObj.moveAerialMapObj)) {
				mainObj.deactive();
				mapToolObj.moveAerialMapObj.toggle();
				event.preventDefault();
			}
			else {
				return false;
			}
		});
		$("#a_map_tool_moveAerialMapT").click(function(event) {
			if(!mainObj.getActive(mapToolObj.moveAerialMapObj)) {
				mainObj.deactive();
				mapToolObj.moveAerialMapObj.toggle();
				event.preventDefault();
			}
			else {
				return false;
			}
		});
		
		/// 동해시 요구사항
		if(PROJECT_CODE == "dh") {
					
			//상단메뉴툴바 지번버튼 
			$("#a_map_tool_tool14").click(function() {
				var element = $(this);
				var imageElement = element.find("img");
				
				if(imageUtils.isOn(imageElement)) {
					imageUtils.off(imageElement);
					sldObj.setTextVisible(COMMON.LAND, false);
				}
				else {
					imageUtils.on(imageElement);
					sldObj.setTextVisible(COMMON.LAND, true);
				}
				mapObj.reloadWMS();
				return false;
			});
			
			//상단메뉴툴바 도로명버튼 
			$("#a_map_tool_tool13").click(function(event) {
				var element = $(this);
				var imageElement = element.find("img");
				if(imageUtils.isOn(imageElement)) {
					imageUtils.off(imageElement);
					
					var rnsLayers = COMMON.RNS;
					for (var i = 0; i < rnsLayers.length; i++) {
						sldObj.setNamedLayerVisible(rnsLayers[i], false);
					}
				}
				else {
					imageUtils.on(imageElement);
					sldObj.showDatas(COMMON.RNS);
				}
				mapObj.reloadWMS();
				return false;
			});
			
			///상단메뉴툴바 토지/건물버튼 
			$("#a_map_tool_tool15").click(function(event) {
				var className = "SearchInfoKras";
				quickObj.CLASS_NAME = className;
				var title = "토지/건물";
				
				// 토지/건물 연속 조회 후 버튼을 통한 기능 종료시점을 알기위하여
				// 2021.08.11, 이승재
				var element = $(this);
				var imageElement = element.find("img");
				if (imageElement.hasClass("node-selected")) {	//기능 종료
					imageElement.removeClass("node-selected");
					mainObj.defaultActive();	//기본 버튼을 클리하여 기능 종료
					return false;
				} else {
					imageElement.addClass("node-selected");
					
						selectObj.active(that.CLASS_NAME, "Point", "drawend", function(event) {
						quickObj.searchPnu(event.feature, className, title).done(function(pnu) {
							// 이승재, 2020.06.28
							// KRAS 연계조회창을 윈도우 팝업 혹은 레이어 팝업으로 띄울지 여부 확인
							if (CONTACT.KRAS_IS_WINDOWPOPUP == 'true') {
								krasObj.krasPopup.open(pnu);
							} else {
								krasObj.open(pnu);
							}
						});
					});
					event.preventDefault();
				}
			});
		}
	},
	
	handlerSldChange : function() {
		var land = sldObj.getNamedLayer(COMMON.LAND);
		
		if(land && land.text && land.text.visible) {
		var visible = land.text.visible;
			if(visible) {
				imageUtils.on($("#a_map_tool_tool14 img"));
			}
		}
		else {
			imageUtils.off($("#a_map_tool_tool14 img"));
		}
		
		var buld = sldObj.getNamedLayer(COMMON.BULD);
		if(buld && buld.visible) {
			imageUtils.on($("#a_map_tool_tool13 img"));
		}
		else {
			imageUtils.off($("#a_map_tool_tool13 img"));
		}
	},
	
	/**
	 * 기능 활성화 - 다음 기능 중 한 개만 실행 되도록 함 (돋보기, 엑스레이, 속성 확인, 속성 검색)
	 * @param {Object} activeObj 실행할 객체
	 */
	active : function(activeObj) {
		var that = this;
		if(!mainObj.getActive()) {
			if(that.activeObj) {
				that.activeObj.deactive();
			}

			if(that.activeObj == activeObj) {
				that.activeObj = null;
				return false;
			}
			else {
				that.activeObj = activeObj;
				that.activeObj.active();
				return true;
			}
		}
	},
	
	/**
	 * 기능 비활성화
	 */
	deactive : function() {
		var that = this;
		if(that.activeObj) {
			that.activeObj.deactive();
			that.activeObj = null;
		}
	},
	
	getActiveObj : function() {
		var that = this;
		return that.activeObj;
	}
	
};

/**
 * 돋보기
 * @type {Object}
 */
mapToolObj.zoomObj = {
		/**
		 * 클래스 명
		 * @type {String}
		 */
		CLASS_NAME : "Zoom",
		
	/**
	 * 선택자
	 */
	selector : "#div_zoom",
	
	/**
	 * 돋보기 지도
	 */
	map : null,
	
	/**
	 * 줌 레벨
	 */
	zoom : 2,
	
	/**
	 * 생성 여부
	 * @type {Boolean}
	 */
	isCreated : false,
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.createMap();
		that.bindEvents();
		that.isCreated = true;
	},
	
	/**
	 * 돋보기 지도 생성
	 */
	createMap : function() {
		var that = this;
		that.map = new kworks.MagnifyingMap({
			map : mapObj.getMap(),
			target : 'zoom_map'
		});
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 돋보기 이동
		$(that.selector).draggable({
	  		cursor : "default",
	  		onStartDrag : function(e) {
	  			$(that.selector).css("cursor", "move");
	  		},
	  		onStopDrag : function(e) {
	  			$(that.selector).css("cursor", "default");
	  			that.map.change();
	  		},
	  		onDrag : function(e) {
	  			var d = e.data;
	            if (d.left < 0){d.left = 0;}
	            if (d.top < 0){d.top = 0;}
	            if (d.left + $(d.target).outerWidth() > $(d.parent).width()){
	                d.left = $(d.parent).width() - $(d.target).outerWidth();
	            }
	            if (d.top + $(d.target).outerHeight() > $(d.parent).height()){
	                d.top = $(d.parent).height() - $(d.target).outerHeight();
	            }
	  		}
	  	});
		
		// 배율 변경
		$(".area_zoom", that.selector).click(function() {
			var element = $(this);
			var zoom = element.attr("data-zoom");
			that.changeZoom(zoom);
		});

		// 크기 변경
		$(".area_size", that.selector).click(function() {
			var element = $(this);
			var size = element.attr("data-next-size");
			userEnvironmentObj.setData("zoomLensSize", size);
			that.changeSize(size);
		});
	},
	
	/**
	 * 기능 활성화
	 */
	active : function() {
		var that = this;
		if(!that.isCreated) {
			that.init();
		}
		
		$(that.selector).show();
		var size = $(that.selector).width();
		var width = $("#map_container").width()/2;
		var height = $("#map_container").height()/2;
		var offset = size/2;
		$(that.selector).css("left", (width-offset) + "px");
		$(that.selector).css("top", (height-offset) + "px");
		
		that.map.active();
		that.changeSize(userEnvironmentObj.getData("zoomLensSize"));
	},
	
	/**
	 * 기능 비 활성화
	 */
	deactive : function() {
		var that = this;
		that.close();
	},
	
	/**
	 * 배율 변경
	 * @param {Number} zoom 배율
	 */
	changeZoom : function(zoom) {
		var that = this;
		that.zoom = zoom;
		that.changeImage();
		that.map.changeZoom(zoom);
	},
	
	/**
	 * 크기 변경
	 * @param {Number} size 크기
	 */
	changeSize : function(size) {
		var that = this;
		$(that.selector).width(size).height(size);
		$("#zoom_map", that.selector).width(size).height(size);
		$(".zoom_img_container", that.selector).width(size).height(size);
		that.changeImage();
		that.map.changeSize(size);
	},
	
	/**
	 * 이미지 변경
	 */
	changeImage : function() {
		var that = this;
		
		var src = $(".zoom_img", that.selector).attr("src");
		var zoom = that.zoom;
		var size = userEnvironmentObj.getData("zoomLensSize");
		
		var changeSrc = src.substring(0, src.lastIndexOf("/")+1);
		changeSrc += "zoom_";
		changeSrc += zoom;
		changeSrc += "x_";
		changeSrc += size;
		changeSrc += ".png";
		$(".zoom_img", that.selector).attr("src", changeSrc).attr("usemap", "#map_zoom_"+size);
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		$(that.selector).hide();
	}
		
};

/**
 * X-Ray
 * @type {Object} 
 */
mapToolObj.xrayObj = {
		/**
		 * 클래스 명
		 * @type {String}
		 */
		CLASS_NAME : "X-Ray",
	
	/**
	 * 선택자
	 */
	selector : "#div_xray",
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "X-Ray",
	
	/**
	 * X-Ray 지도
	 */
	map : null,
	
	/**
	 * 생성 여부
	 * @type {Boolean}
	 */
	isCreated : false,
	
	/**
	 * 현재 인덱스
	 * @type {Number}
	 */
	index : -1,
	
	/**
	 * 레이어 목록
	 */
	layers : [],
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.loadLayers();
		that.createMap();
		that.bindEvents();
		that.next();
		that.isCreated = true;
	},
	
	/**
	 * 레이어 목록 로딩
	 */
	loadLayers : function() {
		var that = this;
		var data = themamapObj.getData("XRAY");
		for(var i=0, len=data.length; i < len; i++) {
			var layer = {
				id : data[i].themamapId
			};
			that.layers.push(layer);
		}
	},
	
	/**
	 * X-Ray 지도 생성
	 */
	createMap : function() {
		var that = this;
		that.map = new kworks.XRayMap({
			map : mapObj.getMap(),
			target : 'xray_map',
			xrayLayer : new ol.layer.Image({
				id : "xray_layer",
		        source: new kworks.source.ImageWMS({
		            url: CONTEXT_PATH + "/proxy/wms.do",
		            params : $.extend(serverObj.getWMSParams(), {
		            	'LAYERS' : serverObj.getBlankLayers(),
		            	'STYLES' : serverObj.getBlankLayers(),
		            	'FORMAT' : "image/jpeg"
	                }),
		            ratio : 1,
		            serverType : MAP.SERVER_TYPE
		        })
		    })
		});
		
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 엑스레이 이동
		$(that.selector).draggable({
	  		cursor : "default",
	  		onStartDrag : function(e) {
	  			$("#div_xray").css("cursor", "move");
	  		},
	  		onStopDrag : function(e) {
	  			$("#div_xray").css("cursor", "default");
	  			that.map.change();
	  		},
	  		onDrag : function(e) {
	  			var d = e.data;
	            if (d.left < 0){d.left = 0;}
	            if (d.top < 0){d.top = 0;}
	            if (d.left + $(d.target).outerWidth() > $(d.parent).width()){
	                d.left = $(d.parent).width() - $(d.target).outerWidth();
	            }
	            if (d.top + $(d.target).outerHeight() > $(d.parent).height()){
	                d.top = $(d.parent).height() - $(d.target).outerHeight();
	            }
	  		}
	  	});
		
		// 다음 레이어
		$(".area_next", that.selector).click(function() {
			that.next();
		});

		// 크기 변경
		$(".area_size", that.selector).click(function() {
			var element = $(this);
			var size = element.attr("data-next-size");
			userEnvironmentObj.setData("xrayLensSize", size);
			that.changeSize(size);
		});
		
	},
	
	/**
	 * 기능 활성화
	 */
	active : function() {
		var that = this;
		if(!that.isCreated) {
			that.init();
		}
		
		// X-Ray 맵 토글
		$(that.selector).show();
		var size = $(that.selector).width();
		var width = $("#map_container").width()/2;
		var height = $("#map_container").height()/2;
		var offset = size/2;
		$(that.selector).css("left", (width-offset) + "px");
		$(that.selector).css("top", (height-offset) + "px");
		that.map.active();
	},
	
	/**
	 * 기능 비 활성화
	 */
	deactive : function() {
		var that = this;
		that.close();
	},
	
	/**
	 * 다음 레이어 불러오기
	 */
	next : function() {
		var that = this;
		var size = userEnvironmentObj.getData("xrayLensSize");
		
		that.index++;
		if(that.index == that.layers.length) {
			that.index = 0;
		}
		
		if(that.layers[that.index].params) {
			var layer = that.map.getLayer("xray_layer");
			var layerNames = that.layers[that.index].params.LAYERS.split(",");
			sldObj.reloadWMSFromSld("XRayMap", "엑스레이맵", layer, layerNames);
			that.changeSize(size);
		}
		else {
			
			themamapObj.selectOne(that.layers[that.index].id).done(function(themamap) {
				if(themamap) {
					var layers = [];
					for(var i=0, len=themamap.kwsThemamapLyrs.length; i < len; i++) {
						layers.push(themamap.kwsThemamapLyrs[i].lyrId);
					}
					that.layers[that.index].params = {
						LAYERS : caseUtils.getText(layers.join())
					};
					
					var layer = that.map.getLayer("xray_layer");
					var layerNames = that.layers[that.index].params.LAYERS.split(",");
					sldObj.reloadWMSFromSld("XRayMap", "엑스레이맵", layer, layerNames);
					that.changeSize(size);
				}
				else {
					that.next();
				}
			});
		}
	},
	
	/**
	 * 크기 변경
	 * @param {Number} size 크기
	 */
	changeSize : function(size) {
		var that = this;
		$(that.selector).width(size).height(size);
		$("#xray_map", that.selector).width(size).height(size);
		$(".xray_img_container", that.selector).width(size).height(size);
		that.changeImage();
		that.map.changeSize(size);
	},
	
	/**
	 * 이미지 변경
	 */
	changeImage : function() {
		var that = this;
		var src = $(".xray_img", that.selector).attr("src");
		var id = that.layers[that.index].id;
		var size = userEnvironmentObj.getData("xrayLensSize");
		
		var changeSrc = src.substring(0, src.lastIndexOf("/")+1);
		changeSrc += "x_ray_";
		changeSrc += size;
		changeSrc += "_";
		changeSrc += id;
		changeSrc += ".png";
		$(".xray_img", that.selector).attr("src", changeSrc).attr("usemap", "#map_xray_"+size);
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		$(that.selector).hide();
	}
		
};

/**
 * 측정
 * @type {Object}
 */
mapToolObj.measureObj = {
		/**
		 * 클래스 명
		 * @type {String}
		 */
		CLASS_NAME : "Measure",
		
	/**
	 * 기능 활성화
	 */
	active : function() {
		windowObj.measureObj.open();
	},
	
	/**
	 * 기능 비 활성화
	 */
	deactive : function() {
		windowObj.measureObj.close();
	}
		
};

mapToolObj.drawToolObj = {
		/**
		 * 클래스 명
		 * @type {String}
		 */
		CLASS_NAME : "Draw",
	
	/**
	 * 기능 활성화
	 */
	active : function() {
		windowObj.drawToolObj.open();
	},
	
	/**
	 * 기능 비활성화
	 */
	deactive : function() {
		windowObj.drawToolObj.close();
	}
		
};

/**
 * 공간 정보
 * @type {Object}
 */
mapToolObj.spatialInfoObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */	
	TITLE : "공간 확인",
	
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "SpatialInfo",
	
	/**
	 * 카테고리 & 레이어 정보
	 * @type {Object}
	 */
	data : null,
	
	/**
	 * 초기화
	 */
	init : function() {
	},
	
	/**
	 * 기능 활성화
	 */
	active : function() {
		var that = this;
		windowObj.spatialInfoObj.open();
		selectObj.active(that.CLASS_NAME, "Point", "drawend", function(event) {
			// Lks : 클릭한 위치의 화면좌표가 필요해서 추가
			that.search(event.feature, event.target.downPx_);
		}, true);
	},
	
	/**
	 * 기능 비 활성화
	 */
	deactive : function() {
		var that = this;
		if(windowObj.spatialInfoObj) {
			windowObj.spatialInfoObj.close();
		}
		if(windowObj.attrObj) {
			windowObj.attrObj.close(true);
		}
		that.close();
	},
	
	/**
	 * 도형 정보 검색 
	 * @param {ol.Feature} feature 도형
	 * @param {Array} position 화면좌표
	 * 수정자 : 이승재, 2020.11.24
	 * 수정내용
	 *   - 모든 레이어가 off시 지형정보만 조회 가능하도록 수정
	 *   - 공간확인 전 레이어 ID 목록으로 레이어 포함 여부를 확인 후
	 *   - 레이어가 있다면 전과 동일하게, 레이어가 없다면 지형정보만 조회하도록 수정
	 */
	search : function(feature, position) {
		var that = this;

		// 축척 제한
		var scale = mapObj.getMap().getScale();
		if(scale > 2500) {
			$.messager.confirm(that.TITLE, "2500 이하의 축척에서만 사용 가능합니다. 축척을 확대 하시겠습니까?", function(isTrue) {
				if(isTrue) {
					var cooridates = feature.getGeometry().getCoordinates();
					mapObj.getMap().moveToCenter(cooridates);
					mapObj.getMap().moveToScale(2500);
				}
			});
			that.clear();
			return;
		}
		
		// 데이터 목록
		var dataIds = windowObj.spatialInfoObj.getDataIds();
		
		// 버퍼 적용
		var buffer = mapObj.getMap().getView().getResolution() * 10;
		if(buffer < 1) {
			buffer = 1;
		}
		var geometry = feature.getGeometry();
		var bufferGeometry = spatialUtils.buffer(geometry, buffer);
		highlightObj.showYellowFeatures(that.CLASS_NAME, [new ol.Feature(bufferGeometry)]);
		
		// 좌표 변환
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
		if (dataIds.length > 0) {
			spatialSearchUtils.searchSummaries(that.TITLE, dataIds.reverse(), filter, function(rows) {
				if(rows && rows.length > 0) {
					// 지형지도 활용
					if (IS_TOPOGRAPHICMAP == 'true') {
						var group = windowObj.spatialInfoObj.getTopographicId();
						var srs = mapProjection.getCode();
						var extent = mapObj.getMap().getExtent();
						var width = $("#map_container").width(); 
						var height = $("#map_container").height(); 
				
						topographicUtils.search(that.TITLE, group, srs, extent, position, width, height, function(row) {
							if (row) {
								rows.push(row);
							}
							
							windowObj.attrObj.open(rows, {
								onClose : function() {
									mapToolObj.deactive();
								}
							});
						});
					}
					else {
						windowObj.attrObj.open(rows, {
							onClose : function() {
								mapToolObj.deactive();
							}
						});
					}
					
				}
				else {
					that.clear();
				}
			});
		} else {
			if (IS_TOPOGRAPHICMAP == 'true') {
				var group = windowObj.spatialInfoObj.getTopographicId();
				var srs = mapProjection.getCode();
				var extent = mapObj.getMap().getExtent();
				var width = $("#map_container").width(); 
				var height = $("#map_container").height(); 
				
				topographicUtils.search(that.TITLE, group, srs, extent, position, width, height, function(row) {
					windowObj.attrObj.open([row], {
						onClose : function() {
							mapToolObj.deactive();
						}
					});
				});
			} else {
				$.messager.alert(title, "공간 정보를 확인할 데이터명 목록이 없습니다.");
			}
		}
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		selectObj.clearFeatures(that.CLASS_NAME);
		highlightObj.removeFeatures(that.CLASS_NAME);
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		that.clear();
		selectObj.clear(that.CLASS_NAME);
		mainObj.defaultActive();
	}
		
};


/**
 * 공간 검색
 * @type {Object}
 */
mapToolObj.spatialSearchObj = {
		/**
		 * 클래스 명
		 * @type {String}
		 */
		CLASS_NAME : "SpatialSearch",
		
	/**
	 * 기능 활성화
	 */
	active : function() {
		windowObj.spatialSearchObj.open();
	},
	
	/**
	 * 기능 비활성화
	 */
	deactive : function() {
		windowObj.spatialSearchObj.close();
	}
		
};

/**
 * 다분면 툴
 * @type {Object}
 */
mapToolObj.multiObj = {
		/**
		 * 클래스 명
		 * @type {String}
		 */
		CLASS_NAME : "Multi",
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_multi_map",
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "다분면",
		
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.bindEvents();
	},
	
	/**
	 * 이벤트 바인딩
	 */
	bindEvents : function() {
		var that = this;
		$(".a_multi_map", that.selector).click(function() {
			var element = $(this);
			var mode = element.attr("data-mode");

			multiObj.setMode(parseInt(mode));
			that.hide();
			mainObj.deactive();
			
			if(parseInt(mode) == 0) {
				mainObj.setSingleActiveObj(null);
			}
			else {
				mainObj.setSingleActiveObj(that);
			}
		});
	},
	
	/**
	 * 툴 on/off
	 */
	toggle : function() {
		var that = this;
		$(that.selector).toggle();
	},
	
	/**
	 * 툴 숨김
	 */
	hide : function() {
		var that = this;
		$(that.selector).hide();
	}

};

/**
 * 로드뷰 툴
 * @type {Object}
 */
mapToolObj.roadviewObj = {
		/**
		 * 클래스 명
		 * @type {String}
		 */
		CLASS_NAME : "Roadview",
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "로드 뷰",
		
	/**
	 * 로드뷰 레이어
	 * @type {ol.layer.Tile}
	 */
	layer : null,
	
	/**
	 * 로드뷰 인터렉션
	 * @type {kworks.interaction.RoadViewDaum}
	 */
	interaction : null,
	
	/**
	 * 배경지도 백업 (종료 시 이전 배경지도 on을 위해 사용)
	 */
	backup : {
		service : null,
		type : null
	},
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		
		if(window.daum == null) {
			$("#a_map_tool_roadview").hide();
		}
		else {
			that.initGis();
			that.bindEvents();
		}
	},
	
	/**
	 * GIS 초기화
	 */
	initGis : function() {
		var that = this;
		that.layer = new ol.layer.Tile({ name : "roadview", source : new kworks.source.Daum({ type : "roadview" }) });
		
		try {
			that.interaction = new kworks.interaction.RoadViewDaum({
				id : "roadview",
				name : "roadview",
				target : "roadview_container",
				executeHandler : function() {
					$("#map").animate(
						{
							width : "400px",
							height : "400px"
						},
						400,
						function() {
							mapObj.getMap().updateSize();
							that.interaction.changePosition();
						}
					);
					$("#map").css("z-index", 7);
					$("#roadview_mode").show();
				}
			});
			mapObj.getMap().addInteraction(that.interaction);
			mapObj.getMap().activeInteractions("drag");
		}
		catch(exception) {
			$("#li_map_tool_roadview").hide();
			
			/**
			 * 
			 2019.07.23
			속초시 로드뷰와 통신장애(추정)에 의해 로드뷰 연계 장애 문제 대응
			IE 11에서는 Flash player에 의한 로드뷰 연계 장애는 거의 없고
			chrome만 Flash player 관련 문제가 발생
			아래 코드 
			$.messager.confirm("로드뷰", "chrome 에서 로드뷰를 사용하기 위해서는 권한이 필요합니다. 로드뷰 사용 시 확인 클릭 후 권한을 허용하여 주십시오. ", function(isTrue) {
					if(isTrue) {
						location.href = "http://get.adobe.com/flashplayer/";
					}
			는 실제적으로 크롬에서만 유효하며,
			IE에서 통신 문제로 인한 로드뷰 연계 장애 시 전혀 도움이되지 않음
			**/
			var userAgent = navigator.userAgent;
			if (/chrome/i.test(userAgent)) {
				$.messager.confirm("로드뷰", "chrome 에서 로드뷰를 사용하기 위해서는 권한이 필요합니다. 로드뷰 사용 시 확인 클릭 후 권한을 허용하여 주십시오. ", function(isTrue) {
					if(isTrue) {
						location.href = "http://get.adobe.com/flashplayer/";
					}
				});
			}
		}
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		// 지도 최대 / 최소
		$("#a_roadview_mode").click(function() {
			var element = $(this);
			if(element.hasClass("show")) {
				that.hideMap(element);
			}
			else {
				that.showMap(element);
			}
		});
	},
	
	/**
	 * 지도 열기
	 * @param element 지도 열기/닫기 버튼
	 */
	showMap : function(element) {
		element.addClass("show");
		var img = element.find("img");
		img.attr("src", img.attr("src").replace("_open_", "_close_"));
		$("#map").show();
	},
	
	/**
	 * 지도 닫기
	 * @param element 지도 열기/닫기 버튼
	 */
	hideMap : function(element) {
		element.removeClass("show");
		var img = element.find("img");
		img.attr("src", img.attr("src").replace("_close_", "_open_"));
		$("#map").hide();
	},
	
	/**
	 * 로드뷰 on/off
	 */
	toggle : function() {
		var that = this;
		if(that.interaction.get("active")) {
			mainObj.setSingleActiveObj(null);
			that.deactive();
		}
		else {
			mainObj.setSingleActiveObj(that);
			that.active();
		}
		menuObj.close();
	},
	
	/**
	 * 기능 활성화
	 */
	active : function() {
		var that = this;
		$("#div_index_open").hide();
		$("#map").css("right", "0px").css("bottom", "0px");
		that.showMap($("#a_roadview_mode"));
		
		that.backup = {
			service : backgroundMapObj.getService(),
			type : backgroundMapObj.getType()
		};
		backgroundMapObj.change("daum", "base");
		mapObj.getMap().addLayer(that.layer);
		mapObj.getMap().activeInteractions("drag", "roadview");
	},
	
	/**
	 * 기능 비활성화
	 */
	deactive : function() {
		var that = this;
		$("#roadview_mode").hide();
		$("#div_index_open").show();
		
		mapObj.getMap().removeLayer(that.layer);
		if(!that.backup.service) {
			that.backup.service = "tms";
		}
		backgroundMapObj.change(that.backup.service, that.backup.type);
		
		that.interaction.clear();
		
		$("#roadview_container").hide();
		$("#map").show();
		$("#map").width("100%").height("100%").css("z-index", 0);
		mapObj.getMap().updateSize();
		
		mainObj.defaultActive();
	}
		
};

/**
 * 양양군 요구사항
 * 항공영상 위치조절
 */
mapToolObj.moveAerialMapObj = {
		/**
		 * 클래스 명
		 * @type {String}
		 */
		CLASS_NAME : "MoveAerialMap",

	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "항공영상 위치조절",
	
	ORIGIN : null,
	
	TILE_ORIGIN : null,
	
	tileLayer : null,
	
	isActive : false,
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		
		that.bindEvent();
		$(".moveAerialMap_control").hide();
		
		that.setOrigin();
	},
	
	/**
	 * 배경지도의 중심점 저장
	 */
	setOrigin : function() {
		var that = this;
		
		//전체 맵 초기화(mapToolObj.moveAerialMapObj.init() 호출시
		//또는 배경지도 변경 시 변수 초기화 및 중심점 저장
		//menuObj.backgroundMapObj.bindEvents() 참조
		that.ORIGIN = null;
		that.TILE_ORIGIN = null;
		that.tileLayer = null;
		
		var layers = mapObj.getMap().getLayers().getArray();
		for(var i=0, len=layers.length; i<len; i++) {
			var layer = layers[i];
			if(layer instanceof ol.layer.Tile) {
				that.ORIGIN = layer.getSource().getTileGrid().getOrigin();
				return false;
			}
		}
	},
	
	/**
	 * 항공영상 위치조절 on/off
	 */
	toggle : function() {
		var that = this;
		
		if (that.isActive) {
			that.deactive();
			that.isActive = false;
		}
		else {
			that.active();
			menuObj.close();
			that.isActive = true;
		}
		
		// Lks : 항공영상
//		if(mainObj.getSingleActiveObj() == that) {
//			mainObj.setSingleActiveObj(null);
//			that.deactive();
//		}
//		else {
//			mainObj.setSingleActiveObj(that);
//			that.active();
//			menuObj.close();
//		}
	},
	
	/**
	 * 기능 활성화
	 */
	active : function() {
		var that = this;
		
		$(".map_control").hide();
		$(".moveAerialMap_control").show();
		///항공사진 이동 중 마우스를 이용한 지도 조작이 가능하도록 아래행 주석처리
		//mapObj.getMap().activeInteractions("");
		that.getTileGrid();
	},
	
	/**
	 * 기능 비활성화
	 */
	deactive : function() {
//		var that = this;
		
		$(".moveAerialMap_control").hide();
		$(".map_control").show();
		
		//기능 비활성화 시 배경지도가 이동이 초기화 된는 것을 방지 하기 위해 주석 처리
//		var x = that.ORIGIN[0];
//		var y = that.ORIGIN[1];
//		that.TILE_ORIGIN = [x,y];
//		that.tileLayer.getSource().getTileGrid().origin_ = [x,y];
		
		//기능 비활성화 시 기 저장된 중심점 데이터를 잃어버리지 않도록 주석처리
//		that.ORIGIN = null;
//		that.TILE_ORIGIN = null;
//		that.tileLayer = null;
		
		$("#a_map_tool_bass img").trigger("click");
	},
	
	/**
	 * 이벤트 바인딩
	 */
	bindEvent : function() {
		var that = this;
		
		// 클릭 이벤트 - 상
		$("#a_moveAerialMap_control_up").click(function() {
			that.moveAerialMap("up");
		});
		
		// 클릭 이벤트 - 좌
		$("#a_moveAerialMap_control_left").click(function() {
			that.moveAerialMap("left");
		});
		
		// 클릭 이벤트 - 초기위치
		$("#a_moveAerialMap_control_original").click(function() {
			that.moveAerialMap("original");
		});
		
		// 클릭 이벤트 - 우
		$("#a_moveAerialMap_control_right").click(function() {
			that.moveAerialMap("right");
		});
		
		// 클릭 이벤트 - 하
		$("#a_moveAerialMap_control_down").click(function() {
			that.moveAerialMap("down");
		});
		
		// 클릭 이벤트 - 마우스 이동
		$("#a_moveAerialMap_control_mouse").click(function() {
			that.selectPosition();
			return false;
		});
	},
	
	/**
	 * tileGrid
	 */
	getTileGrid : function() {
		var that = this;
		
		var layers = mapObj.getMap().getLayers().getArray();
		for(var i=0, len=layers.length; i<len; i++) {
			var layer = layers[i];
			if(layer instanceof ol.layer.Tile) {
				that.tileLayer = layer;
				that.TILE_ORIGIN = layer.getSource().getTileGrid().getOrigin();
				//기능 활성화 시 배경지도 중심점이 다시 세팅되지 않도록 주석 처리(지도 초기화 시 중심점 저장)
//				that.ORIGIN = layer.getSource().getTileGrid().getOrigin();
				return false;
			}
		}
	},
	
	/**
	 * 항공사진 이동 
	 */
	moveAerialMap : function(position) {
		var that = this;
		
		var map = mapObj.getMap();
		var scale = map.getScale();

		//축척에 따라 이동량 차등 적용
		var moveAmount;
		if (scale <= 500) {
			moveAmount = 0.5;
		} else if (500 < scale && scale <= 1000) {
			moveAmount = 1;
		} else if (1000 < scale && scale <= 2500) {
			moveAmount = 2;
		} else if (2500 < scale && scale <= 5000) {
			moveAmount = 4;
		} else if (5000 < scale && scale<= 10000) {
			moveAmount = 8;
		} else if (10000 < scale && scale<= 20000) {
			moveAmount = 16;
		} else if (20000 < scale && scale<= 50000) {
			moveAmount = 32;
		} else if (50000 < scale && scale<= 100000) {
			moveAmount = 64;
		} else {
			moveAmount = 128;
		}
		
		var x;
		var y;
		if(position == "up") {
			x = that.TILE_ORIGIN[0];
			y = that.TILE_ORIGIN[1] + moveAmount;
			that.TILE_ORIGIN = [x,y];
			that.tileLayer.getSource().getTileGrid().origin_ = [x,y];
		}
		else if(position == "left") {
			x = that.TILE_ORIGIN[0] - moveAmount;
			y = that.TILE_ORIGIN[1];
			that.TILE_ORIGIN = [x,y];
			that.tileLayer.getSource().getTileGrid().origin_ = [x,y];
		}
		else if(position == "original") {
			if (that.tileLayer) {
				x = that.ORIGIN[0];
				y = that.ORIGIN[1];
				that.TILE_ORIGIN = [x,y];
				that.tileLayer.getSource().getTileGrid().origin_ = [x,y];
			}
		}
		else if(position == "right") {
			x = that.TILE_ORIGIN[0] + moveAmount;
			y = that.TILE_ORIGIN[1];
			that.TILE_ORIGIN = [x,y];
			that.tileLayer.getSource().getTileGrid().origin_ = [x,y];
		}
		else if(position == "down") {
			x = that.TILE_ORIGIN[0];
			y = that.TILE_ORIGIN[1] - moveAmount;
			that.TILE_ORIGIN = [x,y];
			that.tileLayer.getSource().getTileGrid().origin_ = [x,y];
		}
		
		var map = mapObj.getMap();
		map.renderSync();
	},
	
	/**
	 * 지도상 작도된 라인의 포인트를 이용 항공사진 이동
	 */
	moveAerialMapbyMouse : function(coords) {
		var that = this;

		var x;
		var y;
		var moveAmount_x = coords[1][0] - coords[0][0];
		var moveAmount_y = coords[1][1] - coords[0][1];
		x = that.TILE_ORIGIN[0] + moveAmount_x;
		y = that.TILE_ORIGIN[1] + moveAmount_y;
		that.TILE_ORIGIN = [x,y];
		that.tileLayer.getSource().getTileGrid().origin_ = [x,y];
	},
	
	/**
	 * 항공사진 마우스 이동 시 이동 거리 지정
	 */
	selectPosition : function() {
		var that = this;
	
		selectObj.active(that.CLASS_NAME, "LineString", "drawadd", function(evt) {
			var geom = evt.feature.getGeometry();
			evt.currentTarget.finishDrawing();

			var coords = geom.getCoordinates();
			that.moveAerialMapbyMouse(coords);
			
			selectObj.clearFeatures(that.CLASS_NAME);
			mainObj.defaultActive();
		}, true);
//		selectObj.clear(that.CLASS_NAME);
	}
};

var quickObj = {
		/**
		 * 클래스 명
		 * 실행중인 기능의 클래스명
		 * @type {String}
		 */
		CLASS_NAME : null,

	init : function() {
		var that = this;
		that.initUi();
		that.bindEvents();
		that.addSldHandler();
		that.handlerSldChange();
	},
	
	initUi : function() {
		var tagStr = '';
		for(var i=0, len=QUICK_TOOLS.length; i < len; i++) {
			
			var tool = QUICK_TOOLS[i];
			var id = "a_quick_menu_" + tool.id;
			var title = tool.title;
			
			if(tool.type == "gisbutton") {
				// Lks : 2020.04.07 - 퀵버튼
				if (PROJECT_CODE == "gn" && tool.sysId) {	
					if (SYS_ID == tool.sysId) {
						tagStr += '<a id="'+ id +'" href="#"><img class="tool_toggle_radio img_ov_off tootip" src="'+ CONTEXT_PATH +'/images/kworks/map/tool/'+tool.id+'_off.png" alt="'+ title +'" title="'+ title +'" /></a>';
					}
				}
				//wongi : 2020.08.25 - 퀵버튼  // 양양군 정책지도
				else if (PROJECT_CODE == "yy" && tool.sysId){ 
					if (SYS_ID == tool.sysId) {
						tagStr += '<a id="'+ id +'" href="#"><img class="tool_toggle_radio img_ov_off tootip" src="'+ CONTEXT_PATH +'/images/kworks/map/tool/'+tool.id+'_off.png" alt="'+ title +'" title="'+ title +'" /></a>';
					}
				}
				//토지/건물 정보 조회
				else if (PROJECT_CODE == "sunchang" ){	//순창
					//2021.09.24, 이승재, dataAuthorObj 수정에 따라 불필요
					// 순창에서의 토지/건축 대장 조회는 연속지적 전체 정보(토지정보) 조회가 가능한 유저만 버튼 활성화 //2021.02.19 //정신형
					//dataAuthorObj.load();
					var dataAuthor = dataAuthorObj.getDataAuthor('LP_PA_CBND_LAND');
					
					if(tool.id=='kras' && dataAuthor.indictAt =='Y'){
						tagStr += '<a id="'+ id +'" href="#"><img class="tool_toggle_radio img_ov_off tootip" src="'+ CONTEXT_PATH +'/images/kworks/map/tool/'+tool.id+'_off.png" alt="'+ title +'" title="'+ title +'" /></a>';
					}
				}
				else {	//그외
					tagStr += '<a id="'+ id +'" href="#"><img class="tool_toggle_radio img_ov_off tootip" src="'+ CONTEXT_PATH +'/images/kworks/map/tool/'+tool.id+'_off.png" alt="'+ title +'" title="'+ title +'" /></a>';
				}
			}
			else if(tool.type == "button") {
				if(tool.sysId){
					if(SYS_ID == tool.sysId){
						tagStr += '<a id="'+ id +'" href="#"><img src="'+ CONTEXT_PATH +'/images/kworks/map/tool/'+tool.id+'_off.png" alt="'+ title +'" title="'+ title +'" /></a>';
					}
				}else{
					tagStr += '<a id="'+ id +'" href="#"><img src="'+ CONTEXT_PATH +'/images/kworks/map/tool/'+tool.id+'_off.png" alt="'+ title +'" title="'+ title +'" /></a>';
				}
			}
		}
		$("#div_quick_menu_container").html(tagStr);
	},
	
	bindEvents : function() {
		var that = this;
		
		// Quick 열기
		$("#a_quick_menu_open").click(function() {
			$("#a_quick_menu_open").hide();
			$("#a_quick_menu_close").show();
			$("#div_quick_menu_container").show();
			return false;
		});
		
		// Quick 닫기
		$("#a_quick_menu_close").click(function() {
			$("#a_quick_menu_open").show();
			$("#a_quick_menu_close").hide();
			$("#div_quick_menu_container").hide();
			return false;
		});
		
		// 지번
		$("#a_quick_menu_lot_number").click(function() {
			var element = $(this);
			var imageElement = element.find("img");
			if(imageUtils.isOn(imageElement)) {
				imageUtils.off(imageElement);
				if(PROJECT_CODE == "dh") {
					sldObj.setTextVisible(COMMON.LAND, false);
				}
				else {
					sldObj.setNamedLayerVisible(COMMON.LAND, false);
				}
			}
			else {
				imageUtils.on(imageElement);
				if(PROJECT_CODE == "dh") {
					sldObj.setTextVisible(COMMON.LAND, true);
				}
				else {
					sldObj.showDatas([COMMON.LAND]);
				}
			}
			mapObj.reloadWMS();
			return false;
		});
		
		// 도로명
		$("#a_quick_menu_road_name").click(function(event) {
			var element = $(this);
			var imageElement = element.find("img");
			if(imageUtils.isOn(imageElement)) {
				imageUtils.off(imageElement);
				
				var rnsLayers = COMMON.RNS;
				for (var i = 0; i < rnsLayers.length; i++) {
					sldObj.setNamedLayerVisible(rnsLayers[i], false);
				}
			}
			else {
				imageUtils.on(imageElement);
				sldObj.showDatas(COMMON.RNS);
			}
			mapObj.reloadWMS();
			return false;
		});
		
		// 토지/건물
		$("#a_quick_menu_kras").click(function(event) {
			var className = "SearchInfoKras";
			that.CLASS_NAME = className;
			var title = "토지/건물";
			
			// 토지/건물 연속 조회 후 버튼을 통한 기능 종료시점을 알기위하여
			// 2021.08.11, 이승재
			var element = $(this);
			var imageElement = element.find("img");
			if (imageElement.hasClass("node-selected")) {	//기능 종료
				imageElement.removeClass("node-selected");
				mainObj.defaultActive();	//기본 버튼을 클리하여 기능 종료
				return false;
			} else {	//기능 시작
				imageElement.addClass("node-selected");
				
				selectObj.active(className, "Point", "drawend", function(evt) {
					that.searchPnu(evt.feature, className, title).done(function(pnu) {
						// 이승재, 2020.05.27
						// 양양군 요구사항, KRAS 연계조회창을 윈도우 팝업으로 수정 요청
						if (CONTACT.KRAS_IS_WINDOWPOPUP == 'true') {
							krasObj.krasPopup.open(pnu);
						} else {
							krasObj.open(pnu);
						}
					});
				});
				event.preventDefault();
			}
		});
		
		// 토지사용정보
		$("#a_quick_menu_landUseInfo").click(function(event) {
			var className = "landUseInfo";
			that.CLASS_NAME = className;
			
			var title = "토지사용정보 목록";
			
			selectObj.active(className, "Point", "drawend", function(evt) {
				that.searchPnu(evt.feature, className, title).done(function(pnu) {
					landUseInfoRegstrObj.quick.open(pnu);
					
				});
			});
			event.preventDefault();
		});
	
		// 산지/농지
		$("#a_quick_menu_seaoll").click(function(event) {
			var className = "SearchInfoSeaoll";
			that.CLASS_NAME = className;
			
			var title = "산지/농지";
			selectObj.active(className, "Point", "drawend", function(evt) {
				that.searchPnu(evt.feature, className, title).done(function(pnu) {
					seaollObj.open(pnu);
				});
			});
			event.preventDefault();
		});
		
		// 점용대장
		$("#a_quick_menu_ocpat").click(function(event) {
			var className = "SearchOcpatRegstr";
			that.CLASS_NAME = className;
			
			var title = "점용대장 목록";
			
			selectObj.active(className, "Point", "drawend", function(evt) {
//				that.searchPnuGeom(evt.feature, className, title).done(function(searchArea) {
//					var rows = ocpatRegstrObj.quick.search(searchArea);
//				});
				
//				that.searchPnu(evt.feature, className, title).done(function(pnu) {
//					ocpatRegstrObj.quick.searchPnu(pnu);
//				});
				
				that.searchPnuGeom(evt.feature, className, title).done(function(result) {
					ocpatRegstrObj.quick.searchPnu(result.features, result.pnu);
				});
			});
			event.preventDefault();
		});
		
		// 정책현황
		$("#a_quick_menu_policy").click(function(event) {
			var className = "QuickPolicyRegstr";
			that.CLASS_NAME = className;
			
//			var title = "정책현황목록 목록";
			
			if (!that.isSpatialSearch) {
				that.isSpatialSearch = true;
				selectObj.active(className, "Point", "drawend", function(evt) {
					that.searchSpatial(evt.feature, className, "FTR_IDN").done(function(result) {
						if (result.ftrIdn){
								if (result.ftrIdn) {
									highlightObj.removeFeatures("QuickSearchDong");
									highlightObj.removeFeatures("QuickSearchRoadName");
									windowObj.policyRegisterObj.open(result);
									that.isSpatialSearch = false;
									return false;
								}
								else {
									highlightObj.removeFeatures(className);
									$.messager.alert(that.TITLE, "검색 결과가 없습니다.");
								}

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
	
	searchSpatial : function(feature, className, fieldName) {
		var deferred = $.Deferred();
		
		var title = "정책현황";
		
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
		spatialSearchUtils.listAll(title, "PLCY_STAT_AS", filter, function(data) {
			if(data && data.length && data.length > 0) {
				highlightObj.removeFeatures(className);

				var keyField = camelcaseUtils.underbarToCamelcase(fieldName);
				var result = {};
				result.ftrIdn = data[0][keyField];
				
				var geom = data[0][MAP.GEOMETRY_ATTR_NAME];
				var convert = new ol.format.WKT();
				var feature = convert.readFeature(geom);
				
				if (result.ftrIdn) {
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
	},
	
	/// element2, imageElement2 동해 지번 검색시 상단메뉴툴 지번버튼 클릭 
	triggerOnLand : function() {
		var element = $("#a_quick_menu_lot_number");
		var element2 = $("#a_map_tool_tool14");
		var imageElement = element.find("img");
		var imageElement2 = element2.find("img");
		if(!imageUtils.isOn(imageElement)&&!imageUtils.isOn(imageElement2)) {
			$("#a_quick_menu_lot_number").trigger("click");
			$("#a_map_tool_tool14").trigger("click");
		}
	},
	
	/// element2, imageElement2 동해 도로명 검색시 상단메뉴툴 도로명버튼 클릭
	triggerOnBuld : function() {
		var element = $("#a_quick_menu_road_name");
		var element2 = $("#a_map_tool_tool13");
		var imageElement = element.find("img");
		var imageElement2 = element2.find("img");
		if(!imageUtils.isOn(imageElement)&&!imageUtils.isOn(imageElement2)) {
			$("#a_quick_menu_road_name").trigger("click");
			$("#a_map_tool_tool13").trigger("click");
		}
	},
	
	addSldHandler : function() {
		var that = this;
		sldObj.addHandler(that.CLASS_NAME, function() {
			
			if(PROJECT_CODE == "dh"){
				mapToolObj.handlerSldChange();
			} else {
				that.handlerSldChange();
			}
		});
	},
	
	handlerSldChange : function() {
		var land = sldObj.getNamedLayer(COMMON.LAND);
		var buld = sldObj.getNamedLayer(COMMON.BULD);
		if(land) {
			var visible = land.visible;       
			if(visible) {
				imageUtils.on($("#a_quick_menu_lot_number img"));
			} else {
				imageUtils.off($("#a_quick_menu_lot_number img"));
			}
			
			if(buld && buld.visible) {
				imageUtils.on($("#a_quick_menu_road_name img"));
			} else {
				imageUtils.off($("#a_quick_menu_road_name img"));
			}
		}
	},
	
	searchPnu : function(feature, className, title) {
		var that = this;
		//function(feature, className, title, fieldName) - fieldName 소문자
		return that.searchLandField(feature, className, title, 'pnu');
	},

	searchLandField : function(feature, className, title, fieldName) {
		var deferred = $.Deferred();
		
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
		spatialSearchUtils.listAll(title, COMMON.LAND, filter, function(data) {
			if(data && data.length && data.length > 0) {
				
				var format = new ol.format.WKT();
				var features = [format.readFeature(data[0][MAP.GEOMETRY_ATTR_NAME])];
				
				highlightObj.removeFeatures(className);
				highlightObj.showRedFeatures(className, features);
				
				deferred.resolve(data[0][fieldName]);
				//토지/건물 연속조회를 위하여 이벤트 초기화 주석처리
				//2021.08.11, 이승재
				//mainObj.defaultActive();
			}
			else {
				highlightObj.removeFeatures(className);
			}
			
		});
		return deferred.promise();
	},
	
	// Lks : 점용대장
	searchPnuGeom : function(feature, className, title) {
		var that = this;
		return that.searchLandFieldGeom(feature, className, title, 'pnu');
	},
	
	searchLandFieldGeom : function(feature, className, title, fieldName) {
		var deferred = $.Deferred();
		
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
		spatialSearchUtils.listAll(title, COMMON.LAND, filter, function(data) {
			if(data && data.length && data.length > 0) {
//				var format = new ol.format.WKT();
//				var features = [format.readFeature(data[0][MAP.GEOMETRY_ATTR_NAME])];
				
				highlightObj.removeFeatures(className);
//				highlightObj.showRedFeatures(className, features);
				
				var result = {};
				result.pnu = data[0][fieldName];
				result.features = data[0][MAP.GEOMETRY_ATTR_NAME];
				
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
 * 왼쪽 메뉴 (업무관리, 검색, 배경지도, 주제도, 레이어, 통계지도, 파일, 출력, 북마크, 변화탐지, 공유지도)
 * @type {Object}
 */
var menuObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_menu",
	
	/**
	 * 선택자 판넬
	 * @type {String}
	 */
	selectorPanel : "#div_menu_panel",
	
	/**
	 * 실행 객체
	 */
	activeObj : null,
	
	/**
	 * 메뉴 목록
	 */
	menus : {},
	
	/**
	 * 초기화 
	 */
	init : function() {
		var that = this;
		
		var jobObj = null;
		
		if(menuObj.jobObj[PROJECT_CODE+"Obj"]) {
			jobObj = menuObj.jobObj[PROJECT_CODE+"Obj"].load(SYS_ID);
		}
		else {
			jobObj = menuObj.jobObj;
		}
		
		that.menus = {
			job : {
				obj : jobObj,
				isCreated : false
			},
			search : {
				obj : menuObj.searchObj,
				isCreated : false
			},
			backgroundMap : {
				obj : menuObj.backgroundMapObj,
				isCreated : false
			},
			themamap : {
				obj : menuObj.themamapObj,
				isCreated : false
			},
			layer : {
				obj : menuObj.layerObj,
				isCreated : false
			},
			statsMap : {
				obj : menuObj.statsMapObj,
				isCreated : false
			},
			file : {
				obj : menuObj.fileObj,
				isCreated : false
			},
			output : {
				obj : menuObj.outputObj,
				isCreated : false
			},
			bookMark : {
				obj : menuObj.bookMarkObj,
				isCreated : false
			},
			historyMap : {
				obj : menuObj.historyMapObj,
				isCreated : false
			},
			changeDetection : {
				obj : menuObj.changeDetectionObj,
				isCreated : false
			},
			videoData : {
				obj : menuObj.videoDataObj,
				isCreated : false
			},
			sharingMap : {
				obj : menuObj.sharingMapObj,
				isCreated : false
			},
		};

		// Lks : 지형데이터 활용
		if (IS_TOPOGRAPHICMAP == 'true') {
			var topographicMap = {
				obj : menuObj.topographicObj,
				isCreate : false
			};
				
			that.menus.topographicMap = topographicMap;
		}

		// Lks : 지적원도 
		if (IS_CADASTRALMAP == 'true') {
			var cadastralMap = {
				obj : menuObj.cadastralMapObj,
				isCreate : false
			};
			
			if (PROJECT_CODE == 'sc' || PROJECT_CODE == 'sunchang' ){
				cadastralMap.obj.TITLE = '드론영상';
			}
			
			that.menus.cadastralMap = cadastralMap;
		}
		
		// Lks : 지적 통계
		if(IS_LANDREGISTERSTATISTICS == 'true'){
			var lgstrStats = {
					obj : menuObj.lgstrStatsObj,
					isCreate : false
			};
			
			that.menus.lgstrStats = lgstrStats;
		}
		
		// 지적업무
		if(IS_LGSTRBIZ=='true'){
			//2021.09.24, 이승재, dataAuthorObj 수정에 따라 불필요
			//dataAuthorObj.load();
			var isAuthor = dataAuthorObj.getDataAuthor('KWS_LGSTR_BSNS');
			if(typeof(isAuthor) != 'undefined' && isAuthor != '' && isAuthor != null){
				///if(isAuthor.indictAt == 'Y'){
					/*var lgstrBiz = {
							obj : menuObj.lgstrBizObj,
							isCreate : false
					};
					that.menus.lgstrBiz = lgstrBiz;*/
				if(isAuthor.indictAt == 'Y'){
					//$("#a_menu_lgstrBiz").parent().remove();
					$('#a_menu_lgstrBiz').children('img').attr('src', CONTEXT_PATH + '/images/kworks/map/svg/left/nov18_off.svg');
					$('#a_menu_lgstrBiz').children('img').attr('alt', '지적업무');
					$('#a_menu_lgstrBiz').children('img').attr('title', '지적업무');
					menuObj.lgstrBizObj.TITLE = '지적업무';
				}
				
				var lgstrBiz = {
						obj : menuObj.lgstrBizObj,
						isCreate : false
				};
				that.menus.lgstrBiz = lgstrBiz;
			}else{
				$("#a_menu_lgstrBiz").parent().remove();
			}
		}
		
		//보호구역
		if(PROJECT_CODE.indexOf('gn') > -1){
			var prtsArea = {
					obj:prtsAreaObj,
					isCreate : false
			};
			
			that.menus.prtsArea = prtsArea;
		}
		
		that.bindEvents();
		
		// 레이어 트리는 초기에 생성이 되어 있어야 됨 (그리기, 가져오기 시 레이어 적용을 위해서 사용)
		that.selectMenu("layer");
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 메뉴 선택
		$(".nav_lst li a", that.selector).click(function() {
			var element = $(this);
			
			imageUtils.off($(".nav_lst li a img", that.selector));
			imageUtils.on(element.find("img"));
			
			var menuId = element.attr("data-menu-id");
			that.selectMenu(menuId);
			return false;
		});
		
		// 메뉴 닫기
		$(that.selectorPanel).on("click", ".a_menu_collapse", function() {
			that.close();
		});
		
		// 메뉴 패널 크기 조절
		$(that.selectorPanel).mouseup(function(){
			mainObj.resizeWindowHandler();
		});
	},
	
	/**
	 * 너비 반환
	 * @returns 너비
	 */
	getWidth : function() {
		var that = this;
		var width = $(that.selector).width() + that.getPanelWidth();
		return width;
	},
	
	getPanelWidth : function() {
		var that = this;
		if($(that.selectorPanel).is(":visible")) {
			return $(that.selectorPanel).width();
		}
		return 0;
	},
	
	/**
	 * 메뉴 선택
	 * @param {String} menuId 메뉴 아이디
	 */
	selectMenu : function(menuId) {
		var that = this;

		// 다분면, 로드뷰 실행 시 기능 막기
		if(mainObj.getActive()) {
			return;
		}
		
		// 메뉴별 객체 생성 여부 확인 후 없으면 생성
		var menu = that.menus[menuId];
		if(!menu) {
			$.messager.alert("메뉴 선택", "정의되지 않은 메뉴 입니다.");
			return;
		}
		
		// 현재 실행중인 객체가 있으면 닫기
		if(that.activeObj) {
			if(that.activeObj == menu.obj) {
				that.close();
				return;
			}
			else {
				that.activeObj.close();
			}
		}
		
		// 2021.08.03 정신형
		// 양양군 정책지도 트리 문자열이 길어 div를 넘어가는 문제 발생하여 길이 조절 추가.
		if(PROJECT_CODE.indexOf('yy') > -1 && SYS_ID == '7'){
			if(menuId.indexOf('job') > -1){
				$(that.selectorPanel).resizable({
					handles : 'e',
					animate : true,
					minWidth : 249,
					disabled : false
				});
				
			}else{
				$(that.selectorPanel).css('width', '249px');
				
				$(that.selectorPanel).resizable({
					handles : 'e',
					animate : true,
					minWidth : 249,
					disabled : true
				});
			}
		}
		
		
		//이승재, 2020-05-12
		//권한 중 출력여부를 지도 출력여부로 오해
		//발주처에 잘못 안내하여
		//대안 임시로 특정권한 그룹 사용자에게는 지도 출력 제한
		//속초에만 적용
		if (PROJECT_CODE == 'sc' && menu.obj.PAGE == 'output') {
			var url = CONTEXT_PATH + '/rest/userInfo/checkLimitedAuthor.do';
			var prntngMapAt;
			$.ajax({
				url : url,
				async : false
			}).done(function(response) {
				if(response && response.prntngMapAt) {
					prntngMapAt = response.prntngMapAt;
				}
			});
			
			if (prntngMapAt == 'N') {
				$.messager.alert('메뉴 선택', '출력권한이 없습니다.');
				return;
			}
		}
		
		if(!menu.isCreated) {
			that.activeObj = menu.obj;
			that.createMenu(menuId);
			
			menu.isCreated = true;
		}
		else {
			that.activeObj = menu.obj;
			that.activeObj.open();
		}
		
		$(that.selectorPanel).show();
		
		// 크기 조절
		mainObj.resizeWindowHandler();
	},
	
	/**
	 * 표시 여부 반환
	 * @returns {Boolean} 표시 여부
	 */
	getPanelSize : function() {
		var that = this;
		if($(that.selectorPanel).is(":visible")) {
			return $(that.selectorPanel).width();
		}
		else {
			return 0;
		}
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		
		imageUtils.off($(".nav_lst li a img", that.selector));
		
		// 현재 실행중인 객체가 있으면 닫기
		if(that.activeObj) {
			that.activeObj.close();
		}
		that.activeObj = null;
		$(that.selectorPanel).hide();
		
		// 크기 조절
		mainObj.resizeWindowHandler();
	},
	
	/**
	 * 메뉴 생성
	 * @param {String} menuId 메뉴 아이디
	 */
	createMenu : function(menuId) {
		var that = this;
		var menuObj = that.activeObj;
		
		var id = menuObj.selector.replace("#", "");

		var tagStr = '<div id="' + id + '" title="' + menuObj.TITLE + '">';
		
		tagStr += '<div class="title">';
		tagStr += '<span>' + menuObj.TITLE + '</span>';
		tagStr += '<a href="#" class="a_menu_collapse"><img src="' + CONTEXT_PATH + '/images/kworks/main/menu/collapse.png" alt="접기" title="접기" /></a>';
		tagStr += '</div>';
		
		tagStr += '<div class="content">';
		tagStr += '</div>';
		
		tagStr += '</div>';
		$(that.selectorPanel).append(tagStr);
		
		var url = CONTEXT_PATH + "/main/menu/" + menuObj.PAGE + "/page.do";
		$(".content", menuObj.selector).load(url, function() {
			menuObj.init();
		});
	}	
};

/**
 * 지자체별 실행 코드
 * @type {Object}
 */
var customObj = {
		init : function() {
			if (customObj[PROJECT_CODE + "Obj"]) {
				customObj[PROJECT_CODE + "Obj"].load(SYS_ID);
			}
		}
};
