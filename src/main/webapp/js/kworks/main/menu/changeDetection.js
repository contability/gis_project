menuObj.changeDetectionObj = {

	/**
	 * 페이지 명
	 * @type {String}
	 */
	PAGE : "changeDetection",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_menu_panel_changeDetection",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "changeDetection",
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "변화탐지업무지원",
	
	/**
	 * 메뉴생성여부
	 * @type {String}
	 */
	isMenuCreated : false,
	
	data : null,
	
	init : function() {
		var that = this;
		
		that.bindEvents();
		that.initUi();
		that.open();
	},
	
	initUi : function() {
		var that = this;
		
		// 작성년도
		$(".writingYear", that.selector).numberbox({
			required : false,
			width : 150
		});
		
		// 제목
		$(".subject", that.selector).textbox({
			required : false,
			width : 150
		});
		
		// 추가
		$(".a_add", that.selector).linkbutton({
			iconCls : "icon-add"
		});
		
		// 검색
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 검색
		$(".a_search", that.selector).click(function() {
			that.search(1);
			return false;
		});
		
		// 페이징 이동
		$(that.selector).on("click", ".div_search_pagination a", function() {
			var element = $(this);
			var pageIndex = element.attr("data-page-index");
			that.search(pageIndex);
		});
		
		// 추가
		$(".a_add", that.selector).click(function() {
			that.add.open();
			return false;
		});
		
		// 변화탐지내역 상세보기
		$(that.selector).on("click", ".a_content", function() {
			var element = $(this);
			var chngeDetctNo = element.attr("data-id");
			
			changeDetectionObj.open(chngeDetctNo);
			changeDetectionObj.pageNumber = "1";
			return false;
		});
	},
	
	// 검색결과
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
	
	// 검색결과 생성
	createSearchContent : function(selector, data) {
		var tagStr = '';
		
		for(var i=0, len=data.length; i < len; i++) {
			var row = data[i];
			tagStr += '<li>';
			tagStr += '<a href="#" class="a_content" data-id="' + row.chngeDetctNo + '" >' + row.chngeDetctSj + '</a>';
			tagStr += '</li>';
		}
		$(".ul_search_content", selector).html(tagStr);
	},
	
	// 검색결과 페이징 생성
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
	
	search : function(pageIndex) {
		var that = this;
		
		var writingYear = $(".writingYear", that.selector).textbox("getValue");
		var subject = $(".subject", that.selector).textbox("getValue");
		
		var url = CONTEXT_PATH + "/chngeDetct/listChangeDetectionDetails.do";
		var params = {};
		params.wrterYear = writingYear;
		params.chngeDetctSj = subject;
		params.pageIndex = pageIndex;
		params.recordCountPerPage = 10;
		
		$.get(url, params).done(function(response){
			var data = {};
			data = response.rows;
			
			var pagination = {};
			pagination= response.paginationInfo;
			
			that.createResult(that.selector, data, pagination);
		});
	},
	
	open : function() {
		var that = this;
		$(that.selector).show();
	},
	
	close : function() {
		var that = this;
		
		$(that.selector).hide();
	}
	
};

/**
 * 변화 탐지 내역 등록 
 */
menuObj.changeDetectionObj.add = {
		
	TITLE : "변화 탐지 내역 등록",
	
	CLASS_NAME : "ChangeDetectionAdd",

	selector : null,
	
	isCreated : false,
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					that.close();
					alert("변화 탐지 내역이 등록되었습니다.");
					$("#a_search_menu_changeDetectionDetails").trigger("click");
				}
				else {
					alert("변화 탐지 내역 등록에 실패하였습니다.");
				}
			}
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장
		$(".btn_save_addChangeDetection", that.selector).on("click", function() {
			that.add();
			return false;
		});
		
		// 닫기
		$(".btn_close_addChangeDetection", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	open : function() {
		var that = this;
	
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/chngeDetct/addChangeDetectionDetailsPage.do";
		var windowOptions = {
			width : 449,
			height : 263,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.init();
				that.bindEvents();
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	add : function() {
		var that = this;
		var url = CONTEXT_PATH + "/chngeDetct/addChangeDetectionDetails.do";
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

/**
 * 변화 탐지 내역 검색 결과 
 */
var changeDetectionObj  = {
	
	/**
	 * 제목
	 * @type {String}
	 */	
	TITLE : "변화 탐지 검색 결과 현황",
	
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "ChangeDetectionResult",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_change_detection_result",
	
	/**
	 * 생성 여부 (Singleton)
	 */
	isCreated : false,
	
	pageNumber : 1,

	pageSize : 10,
	
	chngeDetctNo : "",
	
	create : function() {
		var that = this;
		that.initUi();
		that.bindEvents();
		that.isCreated = true;
	},
	
	initUi : function() {
		var that = this;
		
		// 결과창 위치 조절
		$("#div_change_detection_result").window({
			top : $(window).height() - 400
		});
		
		// 변화탐지지역 그리드 생성
		$(".grid_change_detection_area", that.selector).datagrid({
			columns : [[
			    {field:"chngeAreaNo", title:"관리번호", width:80, align:"center"},
			    {field:"chngeAreaDc", title:"설명", width:200, align:"center"},
			    {field:"chngeAreaRm", title:"특이사항", width:200, align:"center"}
			]],
			pagination : true,
			fitColumns : true,
			singleSelect : true,
			onSelect : function(rowIndex, rowData) {
				//that.showFeatures();
			}
		});
		
		var pagination = $(".grid_change_detection_area", that.selector).datagrid("getPager");
		pagination.pagination({
			displayMsg : "{from} - {to} / {total}",
			onSelectPage : function(pageNumber, pageSize) {
				that.pageNumber = pageNumber;
				that.pageSize = pageSize;
				that.loadArea(that.chngeDetctNo, pageNumber);
			}
		});
		
		// 레이아웃 센터 창이 변경되면 그리드 크기 변경
		$(".div_change_detection_result_layout", that.selector).layout("panel", "center").panel({
			onResize : function(width, height) {
				that.resizeDatagrid(width, height);
			}
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 변화탐지내역 출력 버튼 이벤트
		$(".a_print_change_detection_details", that.selector).click(function() {
			var chngeDetctNo = $("#chngeDetctNo", that.selector).val();
			changeDetectionObj.print(chngeDetctNo);
		});
		
		// 변화탐지내역 수정 버튼 이벤트
		$(".a_modify_change_detection_details", that.selector).click(function() {
			var chngeDetctNo = $("#chngeDetctNo", that.selector).val();
			changeDetectionObj.modify.open(chngeDetctNo);
		});
		
		// 변화탐지내역 삭제 버튼 이벤트
		$(".a_remove_change_detection_details", that.selector).click(function() {
			$.messager.confirm(that.TITLE, "변화 탐지 내역을 삭제하시겠습니까?", function(isTrue) {
				var chngeDetctNo = $("#chngeDetctNo", that.selector).val();
				if(isTrue) {
					that.remove(chngeDetctNo);
				}
			});
		});
		
		// 변화탐지지역 상세조회 버튼 이벤트
		$(".a_detail_change_detection_area", that.selector).click(function() {
			var selects = $(".grid_change_detection_area", that.selector).datagrid("getSelections");

			if(selects.length > 0) {
				var chngeAreaNo = selects[0].chngeAreaNo;
				changeDetectionAreaObj.open(chngeAreaNo);
			}
			else {
				alert("조회하실 변화 탐지 지역을 선택하세요.");
			}
			
			return false;
		});
		
		// 변화탐지지역 추가 버튼 이벤트
		$(".a_add_change_detection_area", that.selector).click(function() {
			var chngeDetctNo = $("#chngeDetctNo", that.selector).val();
			
			changeDetectionAreaObj.add.open(chngeDetctNo);
			return false;
		});
	},

	/**
	 * 변화탐지 출력
	 * @param chngeDetctNo
	 */
	print : function(chngeDetctNo) {
		var url = CONTEXT_PATH + "/clipreport/changeDetectionPnt.do?chngeDetctNo=" + chngeDetctNo;
		popupUtils.open(url, "변화탐지출력", { width : 900 , height : 800, left : 300, top:150 });
	},
	
	/**
	 * 열기
	 */
	open : function(chngeDetctNo) {
		var that = this;
		
		if(!that.isCreated) {
			that.create();
		}
		
		that.chngeDetctNo = chngeDetctNo;
		
		that.loadDetails(that.chngeDetctNo).done(function(){
			$(that.selector).window("open");

			// 화면 크기 변환 시 결과 목록 영역 크기 조절 이벤트 등록
			mainObj.addResizeHandler("div_change_detection_result", function(width, height) {
				that.resizeWindowHandler(width, height);
			});
			mainObj.resizeWindowHandler($(window).width(), $(window).height());
		});
	},
	
	/**
	 * 변화 탐지 내역 조회
	 */
	loadDetails : function(chngeDetctNo) {
		var that = this;
		var deferred = $.Deferred();
		
		var url = CONTEXT_PATH + "/chngeDetct/" + chngeDetctNo + "/selectOneChangeDetectionDetails.do";
		$.get(url).done(function(response){
			var data = response.data;
			
			var chngeDetctSj = data.chngeDetctSy.replace(/\n/g,'<br />') ;

			$("#chngeDetctNo", that.selector).val(data.chngeDetctNo);
			$(".changeDetectionDetailsSj", that.selector).text(data.chngeDetctSj);
			$(".changeDetectionDetailsSy", that.selector).html(chngeDetctSj);
			
			that.loadArea(chngeDetctNo);
			
			deferred.resolve(response);
		});
		
		return deferred.promise();
	},
	
	/**
	 * 변화 탐지 지역 리스트 조회
	 */
	loadArea : function(chngeDetctNo) {
		var that = this;

		var data = [];
		
		var url = CONTEXT_PATH + "/chngeDetct/" + chngeDetctNo + "/listChangeDetectionArea.do";
		var params = $.extend({pageIndex : that.pageNumber, pageSize : that.pageSize}, that.params);
		$.get(url, params).done(function(response){
			var rows = response.rows;
			for(var i in rows) {
				data.push({
					chngeAreaNo : rows[i].chngeAreaNo,
					chngeAreaDc : rows[i].chngeAreaDc,
					chngeAreaRm : rows[i].chngeAreaRm
				});
			}
			
			$(".grid_change_detection_area", that.selector).datagrid("loadData", {
				total : response.paginationInfo.totalRecordCount,
				rows : data
			});
		});
	},
	
	/**
	 * 윈도우 창 크기 변경 시 크기 조절
	 */
	resizeWindowHandler : function(windowWidth, windowHeight) {
		var that= this;
		
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
	
	resizeDatagrid : function(width, height) {
		var that = this;

		$(".grid_change_detection_area", that.selector).datagrid("resize", {
			width : "100%",
			height : height - 35
		});

		$(".grid_change_detection_area", that.selector).datagrid("resize", {
			width : "100%",
			height : height - 80
		});
	},
	
	remove : function(chngeDetctNo) {
		var url = CONTEXT_PATH + "/chngeDetct/" + chngeDetctNo + "/removeChangeDetectionDetails.do";
		$.post(url).done(function(result) {
			if(result && result.rowCount && result.rowCount == 1) {
				alert("변화 탐지 내역이 삭제되었습니다.");
				changeDetectionObj.close();
				$("#a_search_menu_changeDetectionDetails").trigger("click");
			}
			else {
				alert("변화 탐지 내역 삭제에 실패했습니다.");
			}
		}).fail(function() {
			alert("변화 탐지 내역 삭제에 실패했습니다.");
		});
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		
		if(that.isCreated) {
			$(".grid_change_detection_area", that.selector).datagrid("loadData", { total : 0, rows : []});
			// 화면 크기 변환 시 결과 목록 영역 크기 조절 이벤트 해제
			mainObj.removeResizeHandler(that.CLASS_NAME);
		}
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		
		that.chngeDetctNo = "";
		that.pageNumber = 1;
		that.pageSize = 10;
		$(that.selector).window("close");
		return false;
	}
	
};

/**
 * 변화 탐지 내역 편집
 */
changeDetectionObj.modify = {
		
	/**
	 * 제목
	 */
	TITLE : "변화 탐지 내역 편집",
	
	/**
	 * 클래스 명
	 */
	CLASS_NAME : "ChangeDetectionDetailsModify",
	
	/**
	 * 선택자
	 */
	selector : null,
	
	/**
	 * 생성여부
	 */
	isCreated : false,
	
	chngeDetctNo : null,
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					changeDetectionObj.loadDetails(that.chngeDetctNo);
					alert("변화 탐지 내역이 수정되었습니다.");
					that.close();
				}
				else {
					alert("변화 탐지 내역 수정에 실패하였습니다.");
				}
			}
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 변화 탐지 내역 수정
		$(".btn_save_modifyChangeDetectionDetails", that.selector).on("click", function() {
			var element = $(this);
			var chngeDetctNo = element.attr("data-chnge-detct-no");
			
			that.save(chngeDetctNo);
			changeDetectionObj.loadDetails(chngeDetctNo);
			return false;
		});
		
		// 변화 탐지 내역 수정창 닫기
		$(".btn_close_modifyChangeDetectionDetails", that.selector).click(function() {
			that.close();
			return false;
		});
	},
	
	/**
	 * 편집창 열기
	 * @param chngeDetctNo
	 */
	open : function(chngeDetctNo) {
		var that = this;
	
		if(that.selector) {
			that.close();
		}
		
		that.chngeDetctNo = chngeDetctNo;
		
		var url = CONTEXT_PATH + "/chngeDetct/" + chngeDetctNo + "/modifyChangeDetectionDetailsPage.do";
		var windowOptions = {
			width : 449,
			height : 263,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.init();
				that.bindEvents();
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	/**
	 * 수정
	 */
	save: function(chngeDetctNo) {
		var that = this;
		var url = CONTEXT_PATH + "/chngeDetct/" + chngeDetctNo + "/modifyChangeDetectionDetails.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		
		that.chngeDetctNo = null;
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
		
};

/**
 * 변화 탐지 지역 상세조회
 */
var changeDetectionAreaObj = {
	
	/**
	 * 제목
	 */
	TITLE : "변화 탐지 지역 상세조회",
	
	/**
	 * 클래스 명
	 */
	CLASS_NAME : "ChangeDetectionAreaView",

	/**
	 * 선택자
	 */
	selector : null,
	
	/**
	 * 생성여부
	 */
	isCreated : false,
	
	originBaseMap : null,
	
	init : function() {
		changeDetectionAreaDrawObj.init();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 변화 탐지 지역 수정
		$(".btn_modify_addChangeDetection", that.selector).on("click", function() {
			var element = $(this);
			var chngeAreaNo = element.attr("data-chnge-area-no");
			var chngeDetctNo = element.attr("data-chnge-detct-no");
			
			changeDetectionAreaObj.modify.open(chngeAreaNo, chngeDetctNo);
			that.close();
			return false;
		});
		
		// 변화 탐지 지역 삭제
		$(".btn_remove_addChangeDetection", that.selector).click(function() {
			var element = $(this);
			var chngeAreaNo = element.attr("data-chnge-area-no");
			var chngeDetctNo = element.attr("data-chnge-detct-no");
			
			that.remove(chngeAreaNo, chngeDetctNo);
			that.close();
			
			$("#div_menu_panel").show();
			$("#div_change_detection_result").parent().show();
			$(".window-shadow").show();
			
			return false;
		});
		
		// 변화 탐지 지역 상세조회창 닫기
		$(".btn_close_addChangeDetection", that.selector).click(function() {
			that.close();
			
			$("#div_menu_panel").show();
			$("#div_change_detection_result").parent().show();
			$(".window-shadow").show();
			
			return false;
		});
		
	},
	
	/**
	 * 상세조회창 열기
	 * @param chngeAreaNo
	 */
	open : function(chngeAreaNo) {
		var that = this;
	
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/chngeDetct/" + chngeAreaNo + "/selectOneChangeDetectionAreaPage.do";
		var windowOptions = {
			width : 450,
			height : 223,
			top : 140,
			right : 30,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.init();
				that.bindEvents();
				that.isCreated = true;
				that.loadData(chngeAreaNo);
			}
		});
		
		that.selector = "#" + id;
	},
	
	loadData : function(chngeAreaNo) {
		var that = this;
		
		var url = CONTEXT_PATH + "/chngeDetct/" + chngeAreaNo + "/selectOneChangeDetectionArea.do";
		$.get(url).done(function(response){
			var data = response.data;
			
			$(".chngeAreaLc", that.selector).text(data.chngeAreaLc);
			$(".chngeAreaDc", that.selector).text(data.chngeAreaDc);
			$(".chngeAreaRm", that.selector).text(data.chngeAreaRm);
			
			var format = new ol.format.GeoJSON();
			var features = format.readFeatures(data.geojson);
			var from = ol.proj.get(MAP.PROJECTION);
			var to = mapObj.getMap().getView().getProjection();
			
			for(var i=0, len=features.length; i < len; i++) {
				if(features[i].getProperties().type == "Circle") {
					features[i] = spatialUtils.convertPointToCircle(features[i]);
				}
				if(from != to) {
					var geometry = feature.getGeometry();
					feature.setGeometry(spatialUtils.transform(geometry, from ,to));
				}
			}
			
			changeDetectionAreaDrawObj.source.addFeatures(features);
			
			var extent = changeDetectionAreaDrawObj.source.getExtent();
			mapObj.map.moveToExtent(extent);
			
			$("#div_menu_panel").hide();
			$("#div_change_detection_result").parent().hide();
			$(".window-shadow").hide();
			
			multiObj.setMode(parseInt(data.partitnCd.replace("RTN0","")));
			
			that.originBaseMap = $("#sel_map_layer_background_layer_base").combobox("getValue");
			
			if(data.partitnMap1 != "") {
				$("#sel_map_layer_background_layer_base").combobox("setValue", data.partitnMap1);
			}
			if(data.partitnMap2 != "") {
				$("#sel_map_1_layer_background_layer").combobox("setValue", data.partitnMap2);
			}
			if(data.partitnMap3 != "") {
				$("#sel_map_2_layer_background_layer").combobox("setValue", data.partitnMap3);
			}
			if(data.partitnMap4 != "") {
				$("#sel_map_3_layer_background_layer").combobox("setValue", data.partitnMap4);
			}
			
		});
	},
	
	/**
	 * 삭제
	 */
	remove : function(chngeAreaNo, chngeDetctNo) {
		var url = CONTEXT_PATH + "/chngeDetct/" + chngeAreaNo + "/removeChangeDetectionArea.do";
		$.post(url).done(function(result) {
			if(result && result.rowCount && result.rowCount == 1) {
				alert("변화 탐지 지역이 삭제되었습니다.");
				changeDetectionObj.loadArea(chngeDetctNo);
			}
			else {
				alert("변화 탐지 지역 삭제에 실패했습니다.");
			}
		}).fail(function() {
			alert("변화 탐지 지역 삭제에 실패했습니다.");
		});
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		
		$("#sel_map_layer_background_layer_base").combobox("setValue", that.originBaseMap);
		that.originBaseMap = null;
		changeDetectionAreaDrawObj.clear();
		multiObj.setMode(0);
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
	
};

//변화 탐지 지역 그리기 도구
var changeDetectionAreaDrawObj = {
	
	index : 0,
	
	source : null,
	
	/**
	 * 벡터 레이어
	 */
	layer : null,
	
	/**
	 * 도형 선택 인터렉션
	 */
	interaction : null,
	
	map : null,
	
	DRAW_TITLE : "changeDetectionAreaDraw",
	
	isCreated : false,
	
	/**
	 * 그리기 타입 목록
	 * @type {Array<String>}
	 */
	drawTypes : {
		Point : "심볼", 
		LineString : "선", 
		Rect : "면", 
		Polygon : "다각형", 
		Circle : "원", 
		Text : "텍스트"
	},
	
	init : function(map) {
		var that = this;
		if(!that.isCreated) {
			if(map) {
				that.map = map;
			}
			else {
				that.map = mapObj.getMap();
			}
			that.initGis();
			that.isCreated = true;
		}
	},
	
	initGis : function() {
		var that = this;
		
		that.addLayer();
		
		for(var type in that.drawTypes) {
			var options = {};
			if(type == "Rect") {
				options.type = "Circle";
				options.geometryFunction = ol.interaction.Draw.createRegularPolygon(4);
			}
			else if(type == "Text") {
				options.type = "Point";
			}
			else {
				options.type = type;
			}
			options.source = that.source;
			
			var interaction = new kworks.interaction.Draw(options);
			interaction.set("id", that.DRAW_TITLE + type);
			interaction.set("name", that.DRAW_TITLE + type);
			that.map.addInteraction(interaction);
			interaction.setActive(false);
			
			interaction.on("drawend", function(event) {
				var type = event.target.get("id").replace(that.DRAW_TITLE, "");
				that.addFeature(event.feature, type);
			});
		}
		
		// 선택 Interaction
		that.interaction = new ol.interaction.Select({
			layers : [that.layer]
		});
		that.interaction.set('id',that.DRAW_TITLE + 'Select');
		that.interaction.set('name',that.DRAW_TITLE + 'Select');
		that.interaction.on("select", function(event) {
			var feature = null;
			if(event.selected && event.selected.length > 0) {
				feature = event.selected[0];
			}
			if(feature) {
				that.selectFeatureElement(feature);
				
				var features = new ol.Collection();
				features.push(feature);
				
				var modify = that.map.getInteraction(that.DRAW_TITLE + "Modify");
				var translate = that.map.getInteraction(that.DRAW_TITLE + "Translate");
				if(modify && modify.getActive()) {
					modify.setFeatures(features);
				}
				else if(translate && translate.getActive()) {
					translate.setFeatures(features);				
				}
			}
		});
		that.map.addInteraction(that.interaction);
		that.interaction.on("propertychange", function(event) {
			if(!event.target.get("active")) {
				event.target.getFeatures().clear();
				that.selectFeatureElement();
			}
		});
		
		var modifyInteraction = new kworks.interaction.Modify({
			features : new ol.Collection(),
			deleteCondition : function(event) {
				return ol.events.condition.shiftKeyOnly(event) && ol.events.condition.singleClick(event);
			}
		});
		modifyInteraction.set('id',that.DRAW_TITLE + 'Modify');
		modifyInteraction.set('name',that.DRAW_TITLE + 'Select');
		that.map.addInteraction(modifyInteraction);
		modifyInteraction.setActive(false);
		
		var translateInteraction = new kworks.interaction.Translate({
			features : new ol.Collection()
		});
		translateInteraction.set('id',that.DRAW_TITLE + 'Translate');
		translateInteraction.set('name',that.DRAW_TITLE + 'Select');
		that.map.addInteraction(translateInteraction);
		translateInteraction.setActive(false);
	},
	
	setMap : function(map) {
		var that = this;
		that.map = map;
	},
	
	addLayer : function() {
		var that = this;
		
		that.source = new ol.source.Vector();
		that.layer = new ol.layer.Vector({
			id : that.DRAW_TITLE + "Layer",
			source : that.source,
			style : function(feature) {
				return that.createStyle(feature.getProperties());
			}
		});
		that.map.addLayer(that.layer);
	},
	
	addFeature : function(feature, type) {
		var that = this;

		feature.setId(++that.index);
		var symbolizer = null;
		switch(type) {
			case "Point" : 
				symbolizer = sldObj.createPointSymbolizer();
				break;
			case "LineString" :
				symbolizer = sldObj.createLineSymbolizer();
				break;
			case "Rect" :
				symbolizer = sldObj.createPolygonSymbolizer();
				break;
			case "Polygon" :
				symbolizer = sldObj.createPolygonSymbolizer();
				break;
			case "Circle" :
				symbolizer = sldObj.createPolygonSymbolizer();
				break;
			case "Text" : 
				symbolizer = sldObj.createTextRule();
				delete symbolizer.maxScale;
				break;
		}
		
		symbolizer["type"] = type;
		
		var properties = $.extend(feature.getProperties(), symbolizer);
		feature.setProperties(properties);
		
		if(properties.type == "Text") {
			windowObj.writeTextObj.open({
				onSubmit : function(text) {
					if(text) {
						feature.setProperties($.extend(feature.getProperties(), { label : text }));
						that.addFeatureElement(feature);
					}
					else {
						that.source.removeFeature(feature);
					}
				}
			});
		}
		else {
			that.addFeatureElement(feature);
		}
	},
	
	dataLoadFeatureElements : function(features) {
		var that = this;

		that.source.addFeatures(features);
		
		var features = that.source.getFeatures();
		var tagStr = '';
		for(var i=0, len=features.length; i < len; i++) {
			var feature = features[i];
			tagStr += that.createFeatureElement(feature);
			that.index = feature.getId();
		}
		$(".tby_draw_list", that.selector).html(tagStr);
		
		var extent = changeDetectionAreaDrawObj.source.getExtent();
		mapObj.map.moveToExtent(extent);
	},
	
	loadFeatureElements : function() {
		var that = this;

		var features = that.source.getFeatures();
		var tagStr = '';
		for(var i=0, len=features.length; i < len; i++) {
			var feature = features[i];
			tagStr += that.createFeatureElement(feature);
		}
		$(".tby_draw_list", that.selector).html(tagStr);
	},
	
	addFeatureElement : function(feature) {
		var that = this;
		$(".tby_draw_list", that.selector).append(that.createFeatureElement(feature));
	},
	
	createFeatureElement : function(feature) {
		var that = this;
		
		var tagStr = '';
		var properties = feature.getProperties();
		var id = feature.getId();
		tagStr += '<tr data-id="' + id + '" >';
		tagStr += '<td>' + id + '</td>';
		tagStr += '<td>' + that.drawTypes[properties.type] + '</td>';
		tagStr += '<td><a href="#" class="a_setting" data-id="' + id + '" ><img src="' + CONTEXT_PATH + '/images/kworks/map/common/layer_icon2_ov.png" alt="스타일 설정" /></a></td>';
		tagStr += '<td><a href="#" class="a_remove" data-id="' + id + '" ><img src="' + CONTEXT_PATH + '/images/kworks/map/common/boomark_icon1_ov.png" alt="지우기" /></a></td>';
		tagStr += '</tr>';
		return tagStr;
	},
	
	selectFeatureElement : function(feature) {
		var that = this;
		$(".tby_draw_list tr", that.selector).removeClass("on");
		if(feature) {
			$(".tby_draw_list tr[data-id="+ feature.getId() +"]", that.selector).addClass("on");
		}
	},
	
	createStyle : function(properties) {
		var that = this;
		var options = null;
		var type = properties["type"];
		
		var scale = that.map.getScale();
		var visible = true;
		if(properties.minScale && scale <= properties.minScale) {
			visible = false;
		}
		if(properties.maxScale && scale > properties.maxScale) {
			visible = false;
		}
		
		if(visible) {
			switch(type) {
				case "WellKnown" :
					options = {
						image : that.createWellKnownStyle(properties)
					};
					break;
				case "Point" :
					options = {
						image : that.createPointStyle(properties)
					};
					break;
				case "LineString" :
					options = {
						stroke : that.createStrokeStyle(properties)
					};
					break;
				case "Rect" :
				case "Polygon" :
				case "Circle" :
					options = {
						fill : that.createFillStyle(properties),
						stroke : that.createStrokeStyle(properties)
					};
					break;
				case "Text" :
					options = {
						text : that.createTextStyle(properties)
					};
					break;
			}
		}
		var style = new ol.style.Style(options);
		return style;
	},
	
	createWellKnownStyle : function(properties) {
		var that = this;
		var options = {
            radius : properties.radius,
            fill : that.createFillStyle(properties)
        };
		if(properties.strokeOpacity) {
			options.stroke = that.createStrokeStyle(properties);
		}
		return new ol.style.Circle(options);
	},
	
	createPointStyle : function(properties) {
		var src = null;
		if(properties.resource.indexOf("http") == 0) {
			src = properties.resource;
		}
		else {
			src = MAP.SYMBOL_URL + "/" + properties.resource;
		}
		var style = new ol.style.Icon({
	        src: src,
			anchor : canvasUtils.toAnchor(properties.anchor)
		});
		return style;
	},
	
	createStrokeStyle : function(properties) {
		var style = new ol.style.Stroke({
			color : colorUtils.toColorFromHex(properties.stroke, properties.strokeOpacity),
			width : properties.strokeWidth,
			lineDash : canvasUtils.toLineDash(properties.strokeDasharray, properties.strokeWidth)
		});
		return style;
	},
	
	createFillStyle : function(properties) {
		var style = new ol.style.Fill({
			color : colorUtils.toColorFromHex(properties.fill, properties.fillOpacity)
		});
		return style;
	},
	
	createTextStyle : function(properties) {
		var that = this;
		var style = new ol.style.Text({
			text : properties.label,
			textAlign : canvasUtils.toTextAlign(properties.anchor.charAt(0)),
			textBaseline : canvasUtils.toTextBaseline(properties.anchor.charAt(1)),
			font : canvasUtils.toFont(properties),
			fill : that.createFillStyle(properties),
			stroke : that.createHaloStyle(properties),
			offsetX : properties.displacementX,
			offsetY : properties.displacementY
		});
		return style;
	},
	
	createHaloStyle : function(properties) {
		var style = new ol.style.Stroke({
			color : colorUtils.toColorFromHex(properties.halo, properties.haloOpacity),
			width : properties.haloRadius
		});
		return style;
	},
	
	activeInteractions : function(types) {
		var that = this;
		for(var i=0, len=types.length; i < len; i++) {
			types[i] = that.DRAW_TITLE + types[i]; 
		}
		that.map.activeInteractions(types, "drag");
	},
	
	clear : function() {
		var that = this;
		
		that.index = 0;
		that.source.clear();
	}
	
};

/**
 * 변화 탐지 지역 등록
 */
changeDetectionAreaObj.add = {
	
	/**
	 * 선택자
	 */
	selector : null,
	
	/**
	 * 제목
	 */
	TITLE : "변화 탐지 지역 등록",
	
	/**
	 * 클래스 명
	 */
	CLASS_NAME : "ChangeDetectionAreaAdd",
	
	/**
	 * draw title 명
	 */
	DRAW_TITLE : "changeDetectionAreaDrawAdd",
	
	/**
	 * 생성여부
	 */
	isCreated : false,
	
	originBaseMap : null,
	
	init : function() {
		changeDetectionAreaDrawObj.init();
		changeDetectionAreaDrawObj.isCreatedAdd = true;
	},
	
	/**
	 * 편집창 열기
	 * @param chngeAreaNo
	 */
	open : function(chngeDetctNo) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/chngeDetct/addChangeDetectionAreaPage.do";
		var windowOptions = {
			width : 370,
			height : 570,
			top : 120,
			right : 10,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.isCreated = true;
				that.init();
			}
			that.initUi(chngeDetctNo);
			that.bindEvents();
			changeDetectionAreaDrawObj.loadFeatureElements();
			
			$("#div_menu_panel").hide();
			$("#div_change_detection_result").parent().hide();
			$(".window-shadow").hide();
			
		});
		
		that.selector = "#" + id;
	},
	
	initUi : function(chngeDetctNo) {
		var that = this;
		
		// 변화 탐지 번호
		$(".chngeDetctNo", that.selector).val(chngeDetctNo);
				
		// 위치
		$(".txt_chnge_area_lc", that.selector).textbox({
			required : true,
			width : 230,
			prompt : "위치를 입력하세요."
		});
		
		// 설명
		$(".txt_chnge_area_dc", that.selector).textbox({
			required : true,
			width : 230,
			prompt : "설명을 입력하세요."
		});
		
		// 다분면창 초기값
		$(".partitnCd", that.selector).val("RTN00");
		// 화면분할 초기 선택 (1면)
		$(".a_draw_tool_rtn01 img").trigger("click");
	},
	
	bindEvents : function() {
		var that = this;
		
		// 심볼
		$(".a_draw_tool_point", that.selector).click(function(event) {
			changeDetectionAreaDrawObj.activeInteractions(["Point"]);
			event.preventDefault();
		});
		
		// 선
		$(".a_draw_tool_linestring", that.selector).click(function(event) {
			changeDetectionAreaDrawObj.activeInteractions(["LineString"]);
			event.preventDefault();
		});
		
		// 면
		$(".a_draw_tool_rect", that.selector).click(function(event) {
			changeDetectionAreaDrawObj.activeInteractions(["Rect"]);
			event.preventDefault();
		});
		
		// 다각형
		$(".a_draw_tool_polygon", that.selector).click(function(event) {
			changeDetectionAreaDrawObj.activeInteractions(["Polygon"]);
			event.preventDefault();
		});
		
		// 원
		$(".a_draw_tool_circle", that.selector).click(function(event) {
			changeDetectionAreaDrawObj.activeInteractions(["Circle"]);
			event.preventDefault();
		});
		
		// 텍스트
		$(".a_draw_tool_text", that.selector).click(function(event) {
			changeDetectionAreaDrawObj.activeInteractions(["Text"]);
			event.preventDefault();
		});
		
		// 선택
		$(".a_draw_tool_select", that.selector).click(function(event) {
			changeDetectionAreaDrawObj.activeInteractions(["Select", "Modify"]);
			event.preventDefault();
		});
		
		// 객체편집
		$(".a_draw_tool_edit", that.selector).click(function(event) {
			changeDetectionAreaDrawObj.activeInteractions(["Select", "Translate"]);
			event.preventDefault();
		});
		
		// 객체전환
		$(".a_draw_tool_convert", that.selector).click(function(event) {
			var features = highlightObj.sources.red.getFeatures();
			if(features.length > 0) {
				for(var i=0, len=features.length; i < len; i++) {
					var feature = features[i];
					var geom = feature.getGeometry().clone();
					
					if(geom instanceof ol.geom.Point || geom instanceof ol.geom.MultiPoint) {
						type = "Point";
					}
					else if(geom instanceof ol.geom.LineString || geom instanceof ol.geom.MultiLineString) {
						type = "LineString";
					}
					else if(geom instanceof ol.geom.Polygon || geom instanceof ol.geom.MultiPolygon) {
						type = "Polygon";
					}
					
					var newFeature = new ol.Feature(geom);
					changeDetectionAreaDrawObj.source.addFeature(newFeature);
					changeDetectionAreaDrawObj.addFeature(newFeature, type);
				}
				
				$.messager.alert(that.TITLE, "공간객체로 전환되었습니다.");
			}
			else {
				$.messager.alert(that.TITLE, "선택된 객체가 없습니다.");
			}
		});
		
		// 도형선택
		$(".tby_draw_list", that.selector).on("click", "tr", function() {
			var element = $(this);
			var id = element.attr("data-id");
			var features = changeDetectionAreaDrawObj.source.getFeatures();
			for(var i=0, len=features.length; i < len; i++) {
				var feature = features[i];
				if(feature.getId() == id) {
					$(".a_draw_tool_select img", that.selector).trigger("click");
					
					var featureCollection = changeDetectionAreaDrawObj.interaction.getFeatures();
					featureCollection.push(feature);
					changeDetectionAreaDrawObj.selectFeatureElement(feature);
					break;
				}
			}
			return false;
		});
		
		// 스타일 설정
		$(".tby_draw_list", that.selector).on("click", ".a_setting", function() {
			var element = $(this);
			var selectFeature = null;
			var id = element.attr("data-id");
			var features = changeDetectionAreaDrawObj.source.getFeatures();
			for(var i=features.length-1; i >= 0; i--) {
				var feature = features[i];
				if(feature.getId() == id) {
					selectFeature = feature;
				}
			}
			
			if(selectFeature) {
				var properties = selectFeature.getProperties();
				var type = properties.type;
				var rule = {};
				if(properties.minScale) {
					rule.minScale = properties.minScale;
				}
				if(properties.maxScale) {
					rule.maxScale = properties.maxScale;
				}
				
				switch(type) {
					case "Point" : 
						rule.point = properties;
						windowObj.symbolSettingObj.open(rule, {
							onSubmit : function(ruleObj) {
								var properties = ruleObj.point;
								if(ruleObj.minScale) {
									properties.minScale = ruleObj.minScale;
								}
								if(ruleObj.maxScale) {
									properties.maxScale = ruleObj.maxScale;
								}
								selectFeature.setProperties(properties);
							}
						});
						break;
					case "LineString" :
						rule.line = properties;
						windowObj.lineSettingObj.open(rule, {
							noDirection : true,
							onSubmit : function(ruleObj) {
								var properties = ruleObj.line;
								if(ruleObj.minScale) {
									properties.minScale = ruleObj.minScale;
								}
								if(ruleObj.maxScale) {
									properties.maxScale = ruleObj.maxScale;
								}
								selectFeature.setProperties(properties);
							}
						});
						break;
					case "Rect" :
					case "Polygon" :
					case "Circle" :
						rule.polygon = properties;
						windowObj.polygonSettingObj.open(rule, {
							onSubmit : function(ruleObj) {
								var properties = ruleObj.polygon;
								if(ruleObj.minScale) {
									properties.minScale = ruleObj.minScale;
								}
								if(ruleObj.maxScale) {
									properties.maxScale = ruleObj.maxScale;
								}
								selectFeature.setProperties(properties);
							}
						});
						break;
					case "Text" : 
						rule = properties;
						windowObj.labelSettingObj.open(rule, {
							onSubmit : function(ruleObj) {
								var properties = ruleObj;
								selectFeature.setProperties(properties);
							}
						});
						break;
				}
			}
			
			var featureCollection = changeDetectionAreaDrawObj.interaction.getFeatures();
			featureCollection.clear();
			changeDetectionAreaDrawObj.selectFeatureElement();
			
			return false;
		});
		
		// 스타일 지우기
		$(".tby_draw_list", that.selector).on("click", ".a_remove", function() {
			var element = $(this);
			var id = element.attr("data-id");
			var features = changeDetectionAreaDrawObj.source.getFeatures();
			for(var i=features.length-1; i >= 0; i--) {
				var feature = features[i];
				if(feature.getId() == id) {
					changeDetectionAreaDrawObj.source.removeFeature(feature);
				}
			}
			element.parent().parent().remove();
			
			var featureCollection = changeDetectionAreaDrawObj.interaction.getFeatures();
			featureCollection.clear();
			changeDetectionAreaDrawObj.selectFeatureElement();
			
			return false;
		});
		
		// 다분면창
		$(".a_changeDetectionArea_multi_map", that.selector).click(function() {
			var element = $(this);
			var mode = element.attr("data-mode");
			
			$(".partitnCd", that.selector).val("RTN0"+mode);
			
			multiObj.setMode(parseInt(mode));
		});
		
		// 저장
		$(".a_save_addChangeDetectionArea", that.selector).click(function() {
			that.persist();
		});
		
		// 닫기
		$(".a_close_addChangeDetectionArea", that.selector).click(function() {
			that.close();
		});
	},
	
	// 저장
	persist : function() {
		var that = this;
		
		// 위치 입력 체크 (필수값)
		if(!$(".txt_chnge_area_lc", that.selector).textbox("isValid")) {
			$.messager.alert(that.TITLE, "위치를 입력하여 주십시오.");
			$(".txt_chnge_area_lc", that.selector).textbox("textbox").focus();
			return false;
		}
		
		// 설명 위치 체크 (필수값)
		if(!$(".txt_chnge_area_dc", that.selector).textbox("isValid")) {
			$.messager.alert(that.TITLE, "설명을 입력하여 주십시오.");
			$(".txt_chnge_area_dc", that.selector).textbox("textbox").focus();
			return false;
		}
		
		// features
		var features = changeDetectionAreaDrawObj.source.getFeatures();
		if(features.length <= 0) {
			$.messager.alert(that.TITLE, "한 개 이상의 도형을 그려 주십시오.");
			return false;
		}
		
		var from = changeDetectionAreaDrawObj.map.getView().getProjection();
		var to = ol.proj.get(MAP.PROJECTION);
		if(from != to) {
			for(var i=0, len=features.length; i < len; i++) {
				var feature = features[i];
				var geometry = feature.getGeometry();
				feature.setGeometry(spatialUtils.transform(geometry, from ,to));
			}
		}
		
		for(var i=0, len=features.length; i < len; i++) {
			if(features[i].getProperties().type == "Circle") {
				features[i] = spatialUtils.convertCircleToPoint(features[i]);
			}
		}

		var format = new ol.format.GeoJSON();
		var geojson = format.writeFeatures(features);
		
		var chngeDetctNo = $(".chngeDetctNo", that.selector).val();
		var chngeAreaLc = $(".txt_chnge_area_lc", that.selector).textbox("getValue");
		var chngeAreaDc = $(".txt_chnge_area_dc", that.selector).textbox("getValue");
		var chngeAreaRm = $(".txa_chnge_area_rm", that.selector).val();
		var partitnCd = $(".partitnCd", that.selector).val();
		
		var partitnMap1 = "";
		var partitnMap2 = "";
		var partitnMap3 = "";
 		var partitnMap4 = "";
		
		if($("#sel_map_layer_background_layer_base").length > 0) {
			if($("#sel_map_layer_background_layer_base").combobox("getValues")[0] != "") {
				partitnMap1 = $("#sel_map_layer_background_layer_base").combobox("getValues")[0];
			}
		}
		if($("#sel_map_1_layer_background_layer").length > 0) {
			if($("#sel_map_1_layer_background_layer").combobox("getValues")[0] != "") {
				partitnMap2 = $("#sel_map_1_layer_background_layer").combobox("getValues")[0];
			}
		}
		if($("#sel_map_2_layer_background_layer").length > 0 ) {
			if($("#sel_map_2_layer_background_layer").combobox("getValues")[0] != "") {
				partitnMap3 = $("#sel_map_2_layer_background_layer").combobox("getValues")[0];
			}
		}
		if($("#sel_map_3_layer_background_layer").length > 0) {
			if($("#sel_map_3_layer_background_layer").combobox("getValues")[0] != "") {
				partitnMap4 = $("#sel_map_3_layer_background_layer").combobox("getValues")[0];
			}	
		}
		
		var maps = multiObj.maps.getMaps();
		var extent = changeDetectionAreaDrawObj.source.getExtent();
		
		mapObj.getClipImages(maps, extent).done(function(datas) {
			var url = CONTEXT_PATH + "/chngeDetct/addChangeDetectionArea.do";
			var params = {
					chngeDetctNo : chngeDetctNo,
					chngeAreaLc : chngeAreaLc,
					chngeAreaDc : chngeAreaDc,
					chngeAreaRm : chngeAreaRm,
					partitnCd : partitnCd,
					geojson : geojson,
					data : datas,
					partitnMap1 : partitnMap1,
					partitnMap2 : partitnMap2, 
					partitnMap3 : partitnMap3, 
					partitnMap4 : partitnMap4
			};
			
			$.post(url, params).done(function(response) {
				if(response && response.rowCount) {
					multiObj.setMode(0);
					that.close();
					changeDetectionObj.loadArea(chngeDetctNo);
					$.messager.alert(that.TITLE, "변화 탐지 지역이 등록되었습니다.");
				}
				else {
					$.messager.alert(that.TITLE, "변화 탐지 지역을 등록하는데 실패하였습니다.");
				}
			}).fail(function() {
				$.messager.alert(that.TITLE, "변화 탐지 지역을 등록하는데 실패하였습니다.");
			});
		});
	},
	
	// 양식 삭제
	clear : function() {
		var that = this;

		changeDetectionAreaDrawObj.clear();
		$(".txt_chnge_area_lc", that.selector).textbox("clear");
		$(".txt_chnge_area_dc", that.selector).textbox("clear");
		$(".txa_chnge_area_rm", that.selector).val("");
		$(".tby_draw_list", that.selector).html("");
		$("#a_map_tool_bass img").trigger("click");
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;

		multiObj.setMode(0);
		that.clear();
		$("#a_map_tool_bass img").trigger("click");
		windowObj.removeWindow(that.selector);
		that.selector = null;
		//that.isCreated = false;
		
		$("#div_menu_panel").show();
		$("#div_change_detection_result").parent().show();
		$(".window-shadow").show();
	}

};

/**
 * 변화 탐지 지역 편집
 */
changeDetectionAreaObj.modify = {

	/**
	 * 선택자
	 */
	selector : null,
	
	/**
	 * 제목
	 */
	TITLE : "변화 탐지 지역 편집",
	
	/**
	 * draw title 명
	 */
	DRAW_TITLE : "changeDetectionAreaDraw",
	
	/**
	 * 클래스 명
	 */
	CLASS_NAME : "ChangeDetectionAreaModify",
	
	/**
	 * 생성여부
	 */
	isCreated : false,
	
	service : null,
	
	type : null,
	
	init : function() {
		changeDetectionAreaDrawObj.init();
		changeDetectionAreaDrawObj.isCreatedModify = true;
	},
	
	/**
	 * 편집창 열기
	 * @param chngeAreaNo
	 */
	open : function(chngeAreaNo, chngeDetctNo) {
		var that = this;
		
		that.service = backgroundMapObj.service;
		that.type = backgroundMapObj.type;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/chngeDetct/" + chngeAreaNo + "/modifyChangeDetectionAreaPage.do";
		var windowOptions = {
			width : 370,
			height : 570,
			top : 120,
			right : 10,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function(rowCount) {
			if(!that.isCreated) {
				that.isCreated = true;
				that.init();
			}
			that.initUi(chngeAreaNo, chngeDetctNo);
			that.bindEvents();
			that.loadData(chngeAreaNo);
			
			$("#div_menu_panel").hide();
			$("#div_change_detection_result").parent().hide();
			$(".window-shadow").hide();
		});
		
		that.selector = "#" + id;
	},
	
	initUi : function(chngeAreaNo, chngeDetctNo) {
		var that = this;
		
		// 위치
		$(".txt_chnge_area_lc", that.selector).textbox({
			required : true,
			width : 230,
			prompt : "위치를 입력하세요."
		});
		
		// 설명
		$(".txt_chnge_area_dc", that.selector).textbox({
			required : true,
			width : 230,
			prompt : "설명을 입력하세요."
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 심볼
		$(".a_draw_tool_point", that.selector).click(function(event) {
			changeDetectionAreaDrawObj.activeInteractions(["Point"]);
			event.preventDefault();
		});
		
		// 선
		$(".a_draw_tool_linestring", that.selector).click(function(event) {
			changeDetectionAreaDrawObj.activeInteractions(["LineString"]);
			event.preventDefault();
		});
		
		// 면
		$(".a_draw_tool_rect", that.selector).click(function(event) {
			changeDetectionAreaDrawObj.activeInteractions(["Rect"]);
			event.preventDefault();
		});
		
		// 다각형
		$(".a_draw_tool_polygon", that.selector).click(function(event) {
			changeDetectionAreaDrawObj.activeInteractions(["Polygon"]);
			event.preventDefault();
		});
		
		// 원
		$(".a_draw_tool_circle", that.selector).click(function(event) {
			changeDetectionAreaDrawObj.activeInteractions(["Circle"]);
			event.preventDefault();
		});
		
		// 텍스트
		$(".a_draw_tool_text", that.selector).click(function(event) {
			changeDetectionAreaDrawObj.activeInteractions(["Text"]);
			event.preventDefault();
		});
		
		// 선택
		$(".a_draw_tool_select", that.selector).click(function(event) {
			changeDetectionAreaDrawObj.activeInteractions(["Select", "Modify"]);
			event.preventDefault();
		});
		
		// 객체편집
		$(".a_draw_tool_edit", that.selector).click(function(event) {
			changeDetectionAreaDrawObj.activeInteractions(["Select", "Translate"]);
			event.preventDefault();
		});
		
		// 객체전환
		$(".a_draw_tool_convert", that.selector).click(function(event) {
			var features = highlightObj.sources.red.getFeatures();
			if(features.length > 0) {
				for(var i=0, len=features.length; i < len; i++) {
					var feature = features[i];
					var geom = feature.getGeometry().clone();
					
					if(geom instanceof ol.geom.Point || geom instanceof ol.geom.MultiPoint) {
						type = "Point";
					}
					else if(geom instanceof ol.geom.LineString || geom instanceof ol.geom.MultiLineString) {
						type = "LineString";
					}
					else if(geom instanceof ol.geom.Polygon || geom instanceof ol.geom.MultiPolygon) {
						type = "Polygon";
					}
					
					var newFeature = new ol.Feature(geom);
					changeDetectionAreaDrawObj.source.addFeature(newFeature);
					changeDetectionAreaDrawObj.addFeature(newFeature, type);
				}
				
				$.messager.alert(that.TITLE, "공간객체로 전환되었습니다.");
			}
			else {
				$.messager.alert(that.TITLE, "선택된 객체가 없습니다.");
			}
		});
		
		// 도형선택
		$(".tby_draw_list", that.selector).on("click", "tr", function() {
			var element = $(this);
			var id = element.attr("data-id");
			var features = changeDetectionAreaDrawObj.source.getFeatures();
			for(var i=0, len=features.length; i < len; i++) {
				var feature = features[i];
				if(feature.getId() == id) {
					$(".a_draw_tool_select img", that.selector).trigger("click");
					
					var featureCollection = changeDetectionAreaDrawObj.interaction.getFeatures();
					featureCollection.push(feature);
					changeDetectionAreaDrawObj.selectFeatureElement(feature);
					break;
				}
			}
			return false;
		});
		
		// 스타일 설정
		$(".tby_draw_list", that.selector).on("click", ".a_setting", function() {
			var element = $(this);
			var selectFeature = null;
			var id = element.attr("data-id");
			var features = changeDetectionAreaDrawObj.source.getFeatures();
			for(var i=features.length-1; i >= 0; i--) {
				var feature = features[i];
				if(feature.getId() == id) {
					selectFeature = feature;
				}
			}
			
			if(selectFeature) {
				var properties = selectFeature.getProperties();
				var type = properties.type;
				var rule = {};
				if(properties.minScale) {
					rule.minScale = properties.minScale;
				}
				if(properties.maxScale) {
					rule.maxScale = properties.maxScale;
				}
				
				switch(type) {
					case "Point" : 
						rule.point = properties;
						windowObj.symbolSettingObj.open(rule, {
							onSubmit : function(ruleObj) {
								var properties = ruleObj.point;
								if(ruleObj.minScale) {
									properties.minScale = ruleObj.minScale;
								}
								if(ruleObj.maxScale) {
									properties.maxScale = ruleObj.maxScale;
								}
								selectFeature.setProperties(properties);
							}
						});
						break;
					case "LineString" :
						rule.line = properties;
						windowObj.lineSettingObj.open(rule, {
							noDirection : true,
							onSubmit : function(ruleObj) {
								var properties = ruleObj.line;
								if(ruleObj.minScale) {
									properties.minScale = ruleObj.minScale;
								}
								if(ruleObj.maxScale) {
									properties.maxScale = ruleObj.maxScale;
								}
								selectFeature.setProperties(properties);
							}
						});
						break;
					case "Rect" :
					case "Polygon" :
					case "Circle" :
						rule.polygon = properties;
						windowObj.polygonSettingObj.open(rule, {
							onSubmit : function(ruleObj) {
								var properties = ruleObj.polygon;
								if(ruleObj.minScale) {
									properties.minScale = ruleObj.minScale;
								}
								if(ruleObj.maxScale) {
									properties.maxScale = ruleObj.maxScale;
								}
								selectFeature.setProperties(properties);
							}
						});
						break;
					case "Text" : 
						rule = properties;
						windowObj.labelSettingObj.open(rule, {
							onSubmit : function(ruleObj) {
								var properties = ruleObj;
								selectFeature.setProperties(properties);
							}
						});
						break;
				}
			}
			
			var featureCollection = changeDetectionAreaDrawObj.interaction.getFeatures();
			featureCollection.clear();
			changeDetectionAreaDrawObj.selectFeatureElement();
			
			return false;
		});
		
		// 스타일 지우기
		$(".tby_draw_list", that.selector).on("click", ".a_remove", function() {
			var element = $(this);
			var id = element.attr("data-id");
			var features = changeDetectionAreaDrawObj.source.getFeatures();
			for(var i=features.length-1; i >= 0; i--) {
				var feature = features[i];
				if(feature.getId() == id) {
					changeDetectionAreaDrawObj.source.removeFeature(feature);
				}
			}
			element.parent().parent().remove();
			
			var featureCollection = changeDetectionAreaDrawObj.interaction.getFeatures();
			featureCollection.clear();
			changeDetectionAreaDrawObj.selectFeatureElement();
			
			return false;
		});
		
		// 다분면창
		$(".a_changeDetectionArea_multi_map", that.selector).click(function() {
			var element = $(this);
			var mode = element.attr("data-mode");
			
			$(".partitnCd", that.selector).val("RTN0"+mode);
			
			multiObj.setMode(parseInt(mode));
		});
		
		// 저장
		$(".a_save_addChangeDetectionArea", that.selector).click(function() {
			that.persist();
		});
		
		// 닫기
		$(".a_close_addChangeDetectionArea", that.selector).click(function() {
			that.close();
		});
	},
	
	loadData : function(chngeAreaNo) {
		var that = this;
		
		var url = CONTEXT_PATH + "/chngeDetct/" + chngeAreaNo + "/selectOneChangeDetectionArea.do";
		$.get(url).done(function(response){
			var data = response.data;
			
			$(".chngeDetctNo", that.selector).val(data.chngeDetctNo);
			$(".chngeAreaNo", that.selector).val(data.chngeAreaNo);
			$(".txt_chnge_area_lc", that.selector).textbox("setValue",data.chngeAreaLc);
			$(".txt_chnge_area_dc", that.selector).textbox("setValue",data.chngeAreaDc);
			$(".txa_chnge_area_rm", that.selector).val(data.chngeAreaRm);
			
			var partitn = ".img_changeDetectionArea_tool_" + data.partitnCd;
			$(partitn, that.selector).trigger("click");
			
			var format = new ol.format.GeoJSON();
			var features = format.readFeatures(data.geojson);
			var from = ol.proj.get(MAP.PROJECTION);
			var to = mapObj.getMap().getView().getProjection();
			
			for(var i=0, len=features.length; i < len; i++) {
				if(features[i].getProperties().type == "Circle") {
					features[i] = spatialUtils.convertPointToCircle(features[i]);
				}
				if(from != to) {
					var geometry = feature.getGeometry();
					feature.setGeometry(spatialUtils.transform(geometry, from ,to));
				}
			}
			
			changeDetectionAreaDrawObj.dataLoadFeatureElements(features);
			
			multiObj.setMode(parseInt(data.partitnCd.replace("RTN0","")));
			
			that.originBaseMap = $("#sel_map_layer_background_layer_base").combobox("getValue");
			
			if(data.partitnMap1 != "") {
				$("#sel_map_layer_background_layer_base").combobox("setValue", data.partitnMap1);
			}
			if(data.partitnMap2 != "") {
				$("#sel_map_1_layer_background_layer").combobox("setValue", data.partitnMap2);
			}
			if(data.partitnMap3 != "") {
				$("#sel_map_2_layer_background_layer").combobox("setValue", data.partitnMap3);
			}
			if(data.partitnMap4 != "") {
				$("#sel_map_3_layer_background_layer").combobox("setValue", data.partitnMap4);
			}
			
		});
	},
	
	// 저장
	persist : function() {
		var that = this;
		
		// 위치 입력 체크 (필수값)
		if(!$(".txt_chnge_area_lc", that.selector).textbox("isValid")) {
			$.messager.alert(that.TITLE, "위치를 입력하여 주십시오.");
			$(".txt_chnge_area_lc", that.selector).textbox("textbox").focus();
			return false;
		}
		
		// 설명 위치 체크 (필수값)
		if(!$(".txt_chnge_area_dc", that.selector).textbox("isValid")) {
			$.messager.alert(that.TITLE, "설명을 입력하여 주십시오.");
			$(".txt_chnge_area_dc", that.selector).textbox("textbox").focus();
			return false;
		}
		
		// features
		var features = changeDetectionAreaDrawObj.source.getFeatures();
		if(features.length <= 0) {
			$.messager.alert(that.TITLE, "한 개 이상의 도형을 그려 주십시오.");
			return false;
		}
		
		var from = changeDetectionAreaDrawObj.map.getView().getProjection();
		var to = ol.proj.get(MAP.PROJECTION);
		if(from != to) {
			for(var i=0, len=features.length; i < len; i++) {
				var feature = features[i];
				var geometry = feature.getGeometry();
				feature.setGeometry(spatialUtils.transform(geometry, from ,to));
			}
		}
		
		for(var i=0, len=features.length; i < len; i++) {
			if(features[i].getProperties().type == "Circle") {
				features[i] = spatialUtils.convertCircleToPoint(features[i]);
			}
		}

		var format = new ol.format.GeoJSON();
		var geojson = format.writeFeatures(features);
		
		var chngeAreaNo = $(".chngeAreaNo", that.selector).val();
		var chngeDetctNo = $(".chngeDetctNo", that.selector).val();
		var chngeAreaLc = $(".txt_chnge_area_lc", that.selector).textbox("getValue");
		var chngeAreaDc = $(".txt_chnge_area_dc", that.selector).textbox("getValue");
		var chngeAreaRm = $(".txa_chnge_area_rm", that.selector).val();
		var partitnCd = $(".partitnCd", that.selector).val();
		
		var partitnMap1 = "";
		var partitnMap2 = "";
		var partitnMap3 = "";
		var partitnMap4 = "";
		
		if($("#sel_map_layer_background_layer_base").length > 0) {
			if($("#sel_map_layer_background_layer_base").combobox("getValues")[0] != "") {
				partitnMap1 = $("#sel_map_layer_background_layer_base").combobox("getValues")[0];
			}
		}
		if($("#sel_map_1_layer_background_layer").length > 0) {
			if($("#sel_map_1_layer_background_layer").combobox("getValues")[0] != "") {
				partitnMap2 = $("#sel_map_1_layer_background_layer").combobox("getValues")[0];
			}
		}
		if($("#sel_map_2_layer_background_layer").length > 0 ) {
			if($("#sel_map_2_layer_background_layer").combobox("getValues")[0] != "") {
				partitnMap3 = $("#sel_map_2_layer_background_layer").combobox("getValues")[0];
			}
		}
		if($("#sel_map_3_layer_background_layer").length > 0) {
			if($("#sel_map_3_layer_background_layer").combobox("getValues")[0] != "") {
				partitnMap4 = $("#sel_map_3_layer_background_layer").combobox("getValues")[0];
			}	
		}
		
		var maps = multiObj.maps.getMaps();
		var extent = changeDetectionAreaDrawObj.source.getExtent();
		
		mapObj.getClipImages(maps, extent).done(function(datas) {
			var url = CONTEXT_PATH + "/chngeDetct/" + chngeAreaNo + "/modifyChangeDetectionArea.do";
			var params = {
					chngeAreaNo : chngeAreaNo,
					chngeDetctNo : chngeDetctNo,
					chngeAreaLc : chngeAreaLc,
					chngeAreaDc : chngeAreaDc,
					chngeAreaRm : chngeAreaRm,
					partitnCd : partitnCd,
					geojson : geojson,
					data : datas,
					partitnMap1 : partitnMap1,
					partitnMap2 : partitnMap2, 
					partitnMap3 : partitnMap3, 
					partitnMap4 : partitnMap4
			};
			
			$.post(url, params).done(function(response) {
				if(response && response.rowCount) {
					multiObj.setMode(0);
					that.close();
					$.messager.alert(that.TITLE, "변화 탐지 지역이 수정되었습니다.");
				}
				else {
					$.messager.alert(that.TITLE, "변화 탐지 지역을 수정하는데 실패하였습니다.");
				}
			}).fail(function() {
				$.messager.alert(that.TITLE, "변화 탐지 지역을 수정하는데 실패하였습니다.");
			});
		});
	},
	
	// 양식 삭제
	clear : function() {
		var that = this;
		changeDetectionAreaDrawObj.clear();
		$(".chngeDetctNo", that.selector).val("");
		$(".chngeAreaNo", that.selector).val("");
		$(".partitnCd", that.selector).val("");
		$(".txt_chnge_area_lc", that.selector).textbox("clear");
		$(".txt_chnge_area_dc", that.selector).textbox("clear");
		$(".txa_chnge_area_rm", that.selector).val("");
		$(".tby_draw_list", that.selector).html("");
		$("#a_map_tool_bass img").trigger("click");
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		
		multiObj.setMode(0);
		that.clear();
		$("#a_map_tool_bass img").trigger("click");
		windowObj.removeWindow(that.selector);
		that.selector = null;
		
		backgroundMapObj.change(that.service, that.type);
		
		that.service = null;
		that.type = null;
		
		$("#div_menu_panel").show();
		$("#div_change_detection_result").parent().show();
		$(".window-shadow").show();
	}

};