/**
 * 계약대장 객체
 */
windowObj.cntrctRegstrObj = {
	
	TITLE : "계약대장",
	
	CLASS_NAME : "CntrctRegstr",
	
	selector : null,
	
	pageNumber : 1,
	
	pageSize : 10,
	
	initUi : function() {
		var that = this;
		
		// 회계년도
		$(".fisYear", that.selector).combobox({
			width : 130,
			valueField : "value",
			textField : "text"
		});
		
		// 계약종류
		$(".ctrtKind", that.selector).combobox({
			width : 150,
			valueField : "value",
			textField : "text"
		});
		
		// 계약번호
		$(".ctrtAcctBookMngNo", that.selector).textbox({
			width : 300
		});
		
		// 계약명
		$(".ctrtNm", that.selector).textbox({
			width : 300
		});
		
		// 계약일자
		$(".ctrtYmdStart", that.selector).datebox({
			width : 110
		});
		$(".ctrtYmdEnd", that.selector).datebox({
			width : 110
		});
		
		// 준공예정일자
		$(".compltSchdYmdStart", that.selector).datebox({
			width : 110
		});
		$(".compltSchdYmdEnd", that.selector).datebox({
			width : 110
		});
		
		$("#tbl_cntrct_regstr_list", that.selector).datagrid({
			height : 310,
			columns : [[
	            {field:"ctrtKind", title:"계약종류", width:70, align:"center"},
	            {field:"ctrtAcctBookMngNo", title:"계약대장번호", width:110, align:"center"},
	            {field:"fisYear", title:"회계년도", width:70, align:"center"},
	            {field:"ctrtNm", title:"계약명", width:200, align:"left"},
	            {field:"ctrtYmd", title:"계약일자", width:80, align:"center", hidden:true},
	            {field:"firstSummaryCtrtAmt", title:"최초총괄금액", width:120, align:"center", hidden:true},
	            {field:"summaryCtrtTotAmt", title:"총계약금액", width:120, align:"center", hidden:true},
	            {field:"splyCmmsnFee", title:"수수료", width:90, align:"center", hidden:true},
	            {field:"compltSchdYmd", title:"준공예정일자", width:80, align:"center", hidden:true},
	            {field:"ctrtMethNm", title:"계약방법", width:100, align:"center", hidden:true},
	            {field:"ctrtTypeNm", title:"계약유형", width:90, align:"center", hidden:true},
	            {field:"ctrtOutline", title:"계약내용", width:200, align:"center", hidden:true},
	            {field:"custNm", title:"업체명", width:120, align:"center", hidden:true},
	            {field:"ctrtGovNm", title:"관서", width:80, align:"center", hidden:true}
            ]],
            fitColumns : true,
            singleSelect : true,
            selectOnCheck : false,
            checkOnSelect : false,
            pagination : true,
            onSelect : function(index, field) {
            	windowObj.cntrctRegstrViewObj.open(index, field);
            }
		});
		
		var pagination = $("#tbl_cntrct_regstr_list", that.selector).datagrid("getPager");
		pagination.pagination({
			displayMsg : "{from} - {to} / {total}",
			onSelectPage : function(pageNumber, pageSize) {
				that.pageNumber = pageNumber;
				that.pageSize = pageSize;
				that.loadData();
			}
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 검색
		$(".a_search", that.selector).click(function() {
			that.loadData();
			return false;
		});

		// 닫기
		$(".a_close", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	open : function () {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/cntrctRegstr/listCntrctRegstr/page.do";
		var windowOptions = {
			width : 800,
			height : 530,
			isClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
			that.loadData();
			that.loadYears();
			that.loadCtrtKind();
		});
		that.selector = "#" + id; 
	},
	
	loadData : function() {
		var that = this;
		var url = CONTEXT_PATH + "/cntrctRegstr/list.do";
		var params = {
			pageIndex : that.pageNumber,
			recordCountPerPage : that.pageSize
		};
		
		// 회계년도
		var fisYear = $(".fisYear", that.selector).combobox("getValue");
		if(fisYear) {
			params["fisYear"] = fisYear;
		}
		
		// 계약종류
		var ctrtKind = $(".ctrtKind", that.selector).combobox("getValue");
		if(ctrtKind) {
			params["ctrtKind"] = ctrtKind;
		}
		
		// 계약번호
		var ctrtAcctBookMngNo = $(".ctrtAcctBookMngNo", that.selector).textbox("getValue");
		if(ctrtAcctBookMngNo) {
			params["ctrtAcctBookMngNo"] = ctrtAcctBookMngNo;
		}
		
		// 계약명
		var ctrtNm = $(".ctrtNm", that.selector).textbox("getValue");
		if(ctrtNm) {
			params["ctrtNm"] = ctrtNm;
		}
		
		// 계약일자
		var firstCtrtYmdStart = $(".ctrtYmdStart", that.selector).datebox("getValue");
		var firstCtrtYmdEnd = $(".ctrtYmdEnd", that.selector).datebox("getValue");
		if(firstCtrtYmdStart && firstCtrtYmdStart) {
			params["ctrtYmdStart"] = firstCtrtYmdStart;
			params["ctrtYmdEnd"] = firstCtrtYmdEnd;
		}
		
		// 준공예정일자
		var compltSchdYmdStart = $(".compltSchdYmdStart", that.selector).datebox("getValue");
		var compltSchdYmdEnd = $(".compltSchdYmdEnd", that.selector).datebox("getValue");
		if(compltSchdYmdStart && compltSchdYmdEnd) {
			params["compltSchdYmdStart"] = compltSchdYmdStart;
			params["compltSchdYmdEnd"] = compltSchdYmdEnd;
		}
		
		$.get(url, params).done(function(response) {
			$("#tbl_cntrct_regstr_list", that.selector).datagrid("loadData", { total : response.paginationInfo.totalRecordCount, rows : response.rows });
		}).fail(function() {
			$.messager.alert(that.TITLE, "계약정보 리스트를 가져오는데 실패했습니다.");
		});
	},
	
	loadYears : function() {
		var that = this;
		var url = CONTEXT_PATH + "/cntrctRegstr/listYears.do";
		$.get(url).done(function(response) {
			if(response && response.rows) {
				if(response.rows.length > 0) {
					var data = [{ value:"", text:"전체" }];
					for(var i=0, len=response.rows.length; i < len; i++) {
						var value = response.rows[i];
						data.push({
							value : value,
							text : value
						});
					}
					$(".fisYear", that.selector).combobox("loadData", data);
				}
				else {
					$.messager.alert(that.TITLE, "회계년도 정보가 없습니다.");
				}
			}
			else {
				$.messager.alert(that.TITLE, "회계년도를 불러오는데 실패했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "회계년도를 불러오는데 실패했습니다.");
		});
		
	},
	
	loadCtrtKind : function() {
		var that = this;
		var url = CONTEXT_PATH + "/cntrctRegstr/listCtrtKinds.do";
		$.get(url).done(function(response) {
			if(response && response.rows) {
				if(response.rows.length > 0) {
					var data = [{ value:"", text:"전체" }];
					for(var i=0, len=response.rows.length; i < len; i++) {
						var value = response.rows[i];
						data.push({
							value : value,
							text : value
						});
					}
					$(".ctrtKind", that.selector).combobox("loadData", data);
				}
				else {
					$.messager.alert(that.TITLE, "계약종류 정보가 없습니다.");
				}
			}
			else {
				$.messager.alert(that.TITLE, "계약종류를 불러오는데 실패했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "계약종류 불러오는데 실패했습니다.");
		});
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
	
};


windowObj.cntrctRegstrViewObj = {
		
	TITLE : "계약대장 상세조회",
	
	CLASS_NAME : "CntrctRegstrView",
	
	selector : null,
	
	open : function (index, field) {
		var that = this;
		
		that.index = index;
		that.field = field;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/cntrctRegstr/viewCntrctRegstr/page.do";
		var windowOptions = {
			width : 800,
			height : 440,
			isClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.bindEvents();
			that.loadData(field);
		});
		that.selector = "#" + id; 
	},
	
	bindEvents : function() {
		var that = this;

		// 닫기
		$(".a_close_view_cntrct_regstr", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	loadData : function(field) {
		var that = this;
		
		that.field = field;
		
		var url = CONTEXT_PATH + "/cntrctRegstr/" + field.ctrtAcctBookMngNo + "/select.do";
		
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;

				for(var key in data) {
					$("." + key, that.selector).text(data[key]);
				}
				
			}
			else {
				alert("계약정보를 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("계약정보를 불러오는데 실패했습니다.");
		});
	},
	
	close : function() {
		var that = this;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
		
};