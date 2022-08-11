/**
 * 도시공원 변천 내역 (사용안함 - 2차 예정)
 */
cityParkObj.changeDetailsSearchObj = {
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 부모 객체 (도시 공원)
	 */
	parent : null,
	
	/**
	 * 제목
	 */
	TITLE : "변천내역",
	
	/**
	 * 데이터 아이디
	 */
	DATA_ID : {
		PARK : "CTY_PARK_HT",
		GREEN : "CTY_GREN_HT"
	},
	
	/**
	 * 초기화
	 * @param parent
	 */
	init : function(parent) {
		var that = this;
		that.selector = parent.selector + " .div_change_details";
		that.initUi();
		that.loadDong();
		that.bindEvents();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 구분 목록
		$(".sel_cl", that.selector).combobox({
			data : [
			    {value:"", text:"전체"}, 
			    {value:"PARK", text:"공원"}, 
			    {value:"GREEN", text:"녹지"}
			],
			valueField : "value",
			textField : "text",
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
		
		// 기간 시작 일
		$(".date_start", that.selector).datebox();
		
		// 기간 종료 일
		$(".date_end", that.selector).datebox();
		
		// 기간 시작일은 기간 종료일 이전만 선택 가능
		$(".date_start", that.selector).datebox('calendar').calendar({
			validator : function(date) {
				var value = $(".date_end", that.selector).datebox("getValue");
				if(value) {
					if(date <= $.fn.datebox.defaults.parser(value)) {
						return true;
					}
					else {
						return false;
					}
				}
				return true;
			}
		});
		
		// 기간 종료일은 기간 시작일 이후만 선택 가능
		$(".date_end", that.selector).datebox('calendar').calendar({
			validator : function(date) {
				var value = $(".date_start", that.selector).datebox("getValue");
				if(value) {
					if(date >= $.fn.datebox.defaults.parser(value)) {
						return true;
					}
					else {
						return false;
					}
				}
				return true;
			}
		});
		
		// 폐지 여부
		$(".switch_abolition_yn", that.selector).switchbutton({
			onText : "포함",
			offText : "미포함"
		});
		
		// 검색
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
		
	},
	
	/**
	 * 동 목록 불러오기
	 */
	loadDong : function() {
		var that = this;
		dongObj.getPromise().done(function() {
			var data = dongObj.getData();
			$(".sel_dong", that.selector).combobox("loadData", data);
		});
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		$(".a_search", that.selector).click(function() {
			var params = that.getParams();
			cityParkObj.changeDetailsResultObj.open(params);
		});
		
	},
	
	/**
	 * 파라미터 반환
	 * @returns {Object} 파라미터 목록
	 */
	getParams : function() {
		var that = this;
		
		var params = {};
		
		// 분류 
		var type =  $(".sel_cl", that.selector).combobox("getValue");
		// 법정동
		var bjdCde = $(".sel_dong", that.selector).combobox("getValue");
		// 명칭
		var name = $(".txt_name", that.selector).textbox("getValue");
		// 위치
		var location = $(".txt_location", that.selector).textbox("getValue");
		// 기간 - 시작
		var dateStart = $(".date_start", that.selector).datebox("getValue");
		// 기간 - 종료
		var dateEnd = $(".date_end", that.selector).datebox("getValue");
		// 폐지여부
		var abolitionYn = $(".switch_abolition_yn", that.selector).switchbutton("options").checked?"Y":"N";
		
		if(type && type != '')  params.type = type;
		if(bjdCde && bjdCde != '')  params.bjdCde = bjdCde;
		if(name && name != '')  params.name = name;
		if(location && location != '')  params.location = location;
		if(dateStart && dateStart != '')  params.dateStart = dateStart;
		if(dateEnd && dateEnd != '')  params.dateEnd = dateEnd;
		if(abolitionYn && abolitionYn != '')  params.abolitionYn = abolitionYn;
		
		return params;
	}
		
};

/**
 * 도시공원 - 변천 내역 결과
 */
cityParkObj.changeDetailsResultObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "검색 결과 목록 현황",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "CityParkChangeDetailsResult",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 페이지 번호
	 */
	pageNumber : 1,
	
	/**
	 * 페이지 크기
	 */
	pageSize : 10,
	
	/**
	 * 열기
	 */
	open : function(params) {
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/cityPark/changeDetailResult/page.do";
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
			that.initUi();
			that.search(1);
		});
		that.selector = "#" + id;
	},
	
	/**
	 * 닫기
	 * @returns {Boolean}
	 */
	close : function() {
		var that = this;
		windowObj.removeWindow(that.selector);
		that.selector = null;
		return false;
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 결과창 위치 조절
		$(that.selector).window({
			top : $(window).height() - 400
		});
		
		// 화면 크기 변환 시 결과 목록 영역 크기 조절 이벤트 등록
		mainObj.addResizeHandler(that.CLASS_NAME, function(width, height) {
			that.resizeWindowHandler(width, height);
		});
		
		mainObj.resizeWindowHandler($(window).width(), $(window).height());
		
		// 대장출력
		$(".a_output_register").linkbutton({
			width : 110,
			iconCls : 'icon-print'
		});
		
		$(".grid_change_details", that.selector).datagrid({
			height : 330,
			rownumbers : true,
			columns : [[
			    { field : "parkName", title : "공원명", width:100, sortable : false },      
			    { field : "location", title : "위치", width:100, sortable : false },
			    { field : "area", title : "면적(㎡)", width:100, sortable : false },
			    { field : "notificationNo", title : "고시번호", width:100, sortable : false },
			    { field : "notificationDate", title : "고시일자", width:100, sortable : false },
			    { field : "notificationType", title : "고시구분", width:100, sortable : false },
			    { field : "remark", title : "비고", width:100, sortable : false }
			]],
			singleSelect : true,
			fitColumns : true,
			pagination : true
		});
		var pagination = $(".grid_change_details", that.selector).datagrid("getPager");
		pagination.pagination({
			displayMsg : "{from} - {to} / {total}",
			onSelectPage : function(pageNumber, pageSize) {
				that.pageNumber = pageNumber;
				that.pageSize = pageSize;
				that.search();
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
	},
	
	/**
	 * 검색
	 * @param pageIndex {Number} 페이지 번호
	 */
	search : function(pageIndex) {
		var that = this;
		
		if(pageIndex) {
			that.pageNumber = pageIndex;
		}
		
		var params = $.extend({ pageIndex : that.pageNumber, recordCountPerPage : that.pageSize }, that.params);
		var url = CONTEXT_PATH + "/cityPark/changeDetails/list.do";
		$.get(url, params).done(function(response) {
			if(response && response.rows) {
				$(".grid_change_details", that.selector).datagrid("loadData", {
					total : response.paginationInfo.totalRecordCount,
					rows : response.rows
				});
			}
		});
	}
		
};