/**
 * 도시공원 - 민원
 */
cityParkObj.civilAppealSearchObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : ".div_civil_appeal",
	
	/**
	 * 부모 객체 (도시 공원)
	 */
	parent : null,
	
	/**
	 * 제목
	 */
	TITLE : "민원 관리",
	
	/**
	 * 데이터
	 */
	data : null,
	
	/**
	 * 초기화 
	 * @param parent
	 */
	init : function(parent) {
		var that = this;
		that.selector = parent.selector + " " + that.selector;
		that.initUi();
		that.bindEvents();
		that.loadCl();
		that.loadCityPark();
		that.loadProcessStatus();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 구분 목록
		$(".sel_cl", that.selector).combobox({
			valueField : "codeId",
			textField : "codeNm",
			width : 160
		});
		
		// 공원명
		$(".sel_city_park", that.selector).combobox({
			valueField : "ftrIdn",
			textField : "prkNam",
			width : 160
		});
		
		// 처리 상태
		$(".sel_process_status", that.selector).combobox({
			valueField : "codeId",
			textField : "codeNm",
			width : 160
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
		
		var startDate = new Date();
		startDate.setDate(new Date().getDate() - 7);
		$(".date_start", that.selector).datebox("setValue", $.fn.datebox.defaults.formatter(startDate));
		$(".date_end", that.selector).datebox("setValue", $.fn.datebox.defaults.formatter(new Date()));
		
		// 등록
		$(".a_registration", that.selector).linkbutton({
			iconCls : "icon-add"
		});
		
		// 검색
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
	},
	
	/**
	 * 분류 표시
	 */
	loadCl : function() {
		var that = this;
		var data = domnCodeObj.getData("KWS-0327").slice();
		data.unshift({ "codeId" : "", "codeNm" : "전체" });
		$(".sel_cl", that.selector).combobox("loadData", data);
		$(".sel_cl", that.selector).combobox("setValue", "");
	},
	
	/**
	 * 도시공원 표시
	 */
	loadCityPark : function() {
		var that = this;
		var rows = cityParkUtils.listAllCityPark();
		rows.unshift({ "ftrIdn" : "", "prkNam" : "전체" });
		$(".sel_city_park", that.selector).combobox("loadData", rows);
		$(".sel_city_park", that.selector).combobox("setValue", "");
	},
	
	/**
	 * 진행 상태 표시
	 */
	loadProcessStatus : function() {
		var that = this;
		var data = domnCodeObj.getData("KWS-0328").slice();
		data.unshift({ "codeId" : "", "codeNm" : "전체" });
		$(".sel_process_status", that.selector).combobox("loadData", data);
		$(".sel_process_status", that.selector).combobox("setValue", "");
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 등록
		$(".a_registration", that.selector).click(function() {
			cityParkObj.civilAppealEditObj.open();
			return false;
		});
		
		// 검색
		$(".a_search", that.selector).click(function() {
			var params = that.getParams();
			cityParkObj.civilAppealResultObj.open(params);
			return false;
		});
		
	},
	
	/**
	 * 파라미터 목록 반환
	 * @returns {Object} 파라미터 목록
	 */
	getParams : function() {
		var that = this;
		
		var params = {};
		// 분류
		var rcvCde = $(".sel_cl", that.selector).combobox("getValue");
		// 공원관리번호
		var ftfIdn = $(".sel_city_park", that.selector).combobox("getValue");
		// 처리구분
		var proCde = $(".sel_process_status", that.selector).combobox("getValue");
		// 접수 일자 - 시작
		var rcvYmdStart = $(".date_start", that.selector).datebox("getValue");
		// 접수 일자 - 종료
		var rcvYmdEnd = $(".date_end", that.selector).datebox("getValue");
		
		if(rcvCde && rcvCde != '') {
			params.rcvCde = rcvCde;
		}
		if(ftfIdn && ftfIdn != '') {
			params.ftfIdn = ftfIdn;
		}
		if(proCde && proCde != '') {
			params.proCde = proCde;
		}
		if(rcvYmdStart && rcvYmdStart != '') {
			params.rcvYmdStart = rcvYmdStart;
		}
		if(rcvYmdEnd && rcvYmdEnd != '') {
			params.rcvYmdEnd = rcvYmdEnd;
		}
		
		return params;
	}
		
};

/**
 * 민원 편집
 */
cityParkObj.civilAppealEditObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도시공원 민원대장 등록",
	
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "CityParkCivilAppealRegistration",
	
	/**
	 * 데이터 아이디
	 * @type {String}
	 */
	DATA_ID : "CtyRserMa",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 객체 아이디
	 */
	fid : null,
	
	/**
	 * 열기
	 */
	open : function(rowData) {
		var that = this;
		var url = CONTEXT_PATH + "/window/cityPark/civilAppealEdit/page.do";
		var windowOptions = {
			width : 380,
			height : 465,
			modal : true,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(rowData) {
				that.fid = rowData.ftrIdn;
			}
			var mode = rowData?"edit":"new";
			that.initUi(mode);
			that.load(rowData);
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
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.fid = null;
		return false;
	},
	
	/**
	 * UI 초기화
	 * @param mode
	 */
	initUi : function(mode) {
		var that = this;
		$(".tbl_civil_appeal", that.selector).propertygrid({
			columns : [ [ {
				field : "field",
				title : "항목",
				width : 100
			}, {
				field : "value",
				title : "내용",
				width : 150,
				formatter : function(value, row) {
					if(value) {
						if (row.editor && row.editor.type == "combobox") {
							var comboData = row.editor.options.data;
							for(var i=0, len=comboData.length; i < len; i++) {
								if(comboData[i][row.editor.options.valueField] == value) {
									return comboData[i][row.editor.options.textField];
								}
							}
						}
					}
					return value;
				},
				
				styler : function(value, row) {
					if(row.column == "aplExp" || row.column == "proExp") {
						return "height:70px;"; 
					}
					return null;
				}
			} ] ]
		});
		
		if(mode == "new") {
			// 등록
			$(".a_registration", that.selector).linkbutton({
				iconCls : "icon-save"
			});
			
			// 취소
			$(".a_cancel", that.selector).linkbutton({
				iconCls : "icon-cancel"
			});
			
			// 수정, 삭제 숨김
			$(".a_update", that.selector).hide();
			$(".a_delete", that.selector).hide();
		}
		else {
			// 등록 숨김
			$(".a_registration", that.selector).hide();
			
			// 수정
			$(".a_update", that.selector).linkbutton({
				iconCls : "icon-save"
			});
			
			// 삭제
			$(".a_delete", that.selector).linkbutton({
				iconCls : "icon-remove"
			});

			// 취소
			$(".a_cancel", that.selector).linkbutton({
				iconCls : "icon-cancel"
			});
		}
		
	},
	
	/**
	 * 불러오기
	 * @param data
	 */
	load : function(data) {
		var that = this;
		var rows = [];
		
		if(!data) {
			data = {
				ftfIdn : "",
				rcvYmd : $.fn.datebox.defaults.formatter(new Date()),
				rcvCde : "",
				apmNam : "",
				apmAdr : "",
				apmTel : "",
				aplExp : "",
				proYmd : $.fn.datebox.defaults.formatter(new Date()),
				proCde : "PRO001",
				proExp : ""
			};
		}
		
		rows.push({ field : "공원명", column : "ftfIdn", value : data.ftfIdn, editor : {
			type : "combobox",
			options : {
				required : true,
				data : cityParkUtils.listAllCityPark(),
				valueField : "ftrIdn",
				textField : "prkNam"
			}
		}});
		
		rows.push({ field : "접수일자", column : "rcvYmd", value : data.rcvYmd, editor : {
			type : "datebox",
			options : {
				required : true
			}
		} });
		
		rows.push({ field : "접수구분", column : "rcvCde", value : data.rcvCde, editor : {
			type : "combobox",
			options : {
				required : true,
				data : domnCodeObj.getData("KWS-0327").slice(),
				valueField : "codeId",
				textField : "codeNm",
				editable : false
			}
		} });
		
		rows.push({ field : "민원인 성명", column : "apmNam", value : data.apmNam, editor : "textbox" });
		rows.push({ field : "민원인 주소", column : "apmAdr", value : data.apmAdr, editor : "textbox" });
		rows.push({ field : "민원인 전화번호", column : "apmTel", value : data.apmTel, editor : "textbox" });
		rows.push({ field : "민원내용", column : "aplExp", value : data.aplExp, editor : {
			type : "textbox",
			options : {
				multiline : true,
				height : 70
			}
		}});
		rows.push({ field : "담당자", column : "proNam", value : data.proNam, editor : "textbox" });
		
		rows.push({ field : "처리일", column : "proYmd", value : data.proYmd, editor : {
			type : "datebox",
			options : {
				required : true
			}
		} });
		
		rows.push({ field : "처리구분", column : "proCde", value : data.proCde, editor : {
			type : "combobox",
			options : {
				required : true,
				data : domnCodeObj.getData("KWS-0328").slice(),
				valueField : "codeId",
				textField : "codeNm",
				editable : false
			}
		} });
		
		rows.push({ field : "처리내용", column : "proExp", value : data.proExp, editor : {
			type : "textbox",
			options : {
				multiline : true,
				height : 70
			}
		} });
		$(".tbl_civil_appeal", that.selector).propertygrid("loadData", rows);
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 등록
		$(".a_registration", that.selector).click(function() {
			if(that.validator()) {
				that.registration();
			}
			return false;
		});
		
		// 수정
		$(".a_update", that.selector).click(function() {
			if(that.validator()) {
				that.modify();
			}
			return false;
		});
		
		// 삭제
		$(".a_delete", that.selector).click(function() {
			$.messager.confirm(that.TITLE, "삭제하시겠습니까?", function(isTrue) {
				if(isTrue) {
					that.remove();
				}
			});
		});
		
		// 취소
		$(".a_cancel", that.selector).click(function() {
			that.close();
			return false;
		});
	},
	
	/**
	 * 검증
	 * @returns {Boolean} 검증 여부
	 */
	validator : function() {
		var that = this;
		var rows = $(".tbl_civil_appeal", that.selector).propertygrid("getRows");
		for(var i=0, len=rows.length; i < len; i++) {
			var row = rows[i];
			if(row.editor && row.editor.options && row.editor.options.required) {
				if(!row.value) {
					$.messager.alert(that.TITLE, "[" + row.field + "] 필수 항목 입니다.");
					return false;
				}
			}
		}
		return true;
	},
	
	/**
	 * 파라미터 목록 반환
	 * @returns {Object} 파라미터 목록
	 * 
	 */
	getParams : function() {
		var that = this;
		var params = { ftrCde : "ZT101" };
		var rows = $(".tbl_civil_appeal", that.selector).propertygrid("getRows");
		for(var i=0, len=rows.length; i < len; i++) {
			var row = rows[i];
			params[row.column] = row.value;
		}
		return params;
	},
	
	/**
	 * 등록
	 */
	registration : function() {
		var that = this;
		var params = that.getParams();
		var url = CONTEXT_PATH + "/cityPark/civilAppeal/add.do";
		$.post(url, params).done(function(response) {
			if(response && response.rowCount == 1) {
				$.messager.alert(that.TITLE, "민원이 등록되었습니다.", null, function() {
					that.close();
					cityParkObj.civilAppealResultObj.search();
				});
			}
		});
	},
	
	/**
	 * 수정
	 */
	modify : function() {
		var that = this;
		var params = that.getParams();
		var url = CONTEXT_PATH + "/cityPark/civilAppeal/" + that.fid + "/modify.do";
		$.post(url, params).done(function(response) {
			if(response && response.rowCount == 1) {
				$.messager.alert(that.TITLE, "민원이 수정되었습니다.", null, function() {
					that.close();
					cityParkObj.civilAppealResultObj.search();
				});
			}
		}); 
	}, 
	
	/**
	 * 삭제
	 */
	remove : function() {
		var that = this;
		var url = CONTEXT_PATH + "/cityPark/civilAppeal/" + that.fid + "/remove.do";
		$.post(url).done(function(response) {
			if(response && response.rowCount == 1) {
				$.messager.alert(that.TITLE, "민원이 삭제되었습니다.", null, function() {
					that.close();
					cityParkObj.civilAppealResultObj.search(1);
				});
			}
		}); 
	}
	
};

/**
 * 도시공원 - 민원 결과
 */
cityParkObj.civilAppealResultObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "검색 결과 목록 현황",
	
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "CityParkCivilAppealResult",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 검색 파라미터
	 */
	params : null,
	
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
		
		var url = CONTEXT_PATH + "/window/cityPark/civilAppealResult/page.do";
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
		that.params = null;
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
		
		$(".grid_civil_appeal", that.selector).datagrid({
			height : 330,
			rownumbers : true,
			columns : [[
			    { field : "rcvYmd", title : "접수일자", width:100, sortable : false },
			    { field : "apmNam", title : "민원인 성명", width:100, sortable : false },
			    { field : "apmAdr", title : "민원인 주소", width:100, sortable : false },
			    { field : "apmTel", title : "민원인 전화번호", width:100, sortable : false },
			    { field : "aplExp", title : "민원내용", width:100, sortable : false },
			    { field : "proNam", title : "담당자", width:100, sortable : false },
			    { field : "proYmd", title : "처리일", width:100, sortable : false },
			    { field : "proExp", title : "처리내용", width:100, sortable : false }
			]],
			singleSelect : true,
			fitColumns : true,
			onSelect : function(rowIndex, rowData) {
				cityParkObj.civilAppealEditObj.open(rowData);
			},
			pagination : true
		});
		var pagination = $(".grid_civil_appeal", that.selector).datagrid("getPager");
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
	 * @param pageIndex
	 */
	search : function(pageIndex) {
		var that = this;
		
		if(pageIndex) {
			that.pageNumber = pageIndex;
		}
		
		var params = $.extend({ pageIndex : that.pageNumber, recordCountPerPage : that.pageSize }, that.params);
		var url = CONTEXT_PATH + "/cityPark/civilAppeal/list.do";
		$.get(url, params).done(function(response) {
			if(response && response.rows) {
				$(".grid_civil_appeal", that.selector).datagrid("loadData", {
					total : response.paginationInfo.totalRecordCount,
					rows : response.rows
				});
			}
		});
	}
		
};