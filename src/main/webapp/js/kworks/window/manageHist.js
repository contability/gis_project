// 관리이력 객체
windowObj.manageHistObj = {
	
	TITLE : "관리이력",
	
	CLASS_NAME : "ManageHistory",
	
	selector : null,
	
	data : null,
	
	dataId : null,
	
	dataAlias : null,
	
	fid : null,
	
	ftrIdn : null,
	
	dataCtgryId : null,
	
	ftrCde : null,
	
	initUi : function() {
		var that = this;
		var dataCtgryId = that.dataCtgryId;

		if(dataCtgryId == "ROAD" || dataCtgryId == "ROAD_ATTACHMENT" || dataCtgryId == "TRANSPORTATION" || dataCtgryId == "WATER" || dataCtgryId == "WATER_ATTACHMENT" || dataCtgryId == "SEWAGE" || dataCtgryId == "SEWAGE_ATTACHMENT") {
			$("#tbl_manage_history_list", that.selector).datagrid({
				height : 200,
				columns : [[
				            {field:"mnhNam", title:"관리이력구분", width:100, align:"center"},
				            {field:"sbjNam", title:"유지보수사유", width:160, align:"center"},
				            {field:"sreYmd", title:"보수시작일", width:100, align:"center"},
				            {field:"ereYmd", title:"보수종료일", width:100, align:"center"}
				          ]],
	            fitColumns : true,
	            singleSelect : true,
	            selectOnCheck : false,
	            checkOnSelect : false,
	            onSelect : function(index, field) {
	            	windowObj.manageHistObj.view.open(index, dataCtgryId, field);
	            }
			});
		}
		else {
			$("#tbl_manage_history_list", that.selector).datagrid({
				height : 200,
				columns : [[
				            {field:"mnhNam", title:"관리이력구분", width:100, align:"center"},
				            {field:"sbjCde", title:"유지보수사유", width:160, align:"center"},
				            {field:"sreYmd", title:"보수시작일", width:100, align:"center"},
				            {field:"ereYmd", title:"보수종료일", width:100, align:"center"}
				          ]],
	            fitColumns : true,
	            singleSelect : true,
	            selectOnCheck : false,
	            checkOnSelect : false,
	            onSelect : function(index, field) {
	            	windowObj.manageHistObj.view.open(index, dataCtgryId, field);
	            }
			});
		}
	},
	
	bindEvents : function() {
		var that = this;
		
		// 등록
		$(".a_add_list_hist", that.selector).on("click", function() {
			windowObj.manageHistObj.add.open(that.dataCtgryId, that.ftrCde, that.ftrIdn);
			return false;
		});
		
		// 닫기
		$(".a_close_list_hist", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	open : function(dataId, fid, ftrIdn, dataCtgryId, data, ftrCde) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}

		that.dataId = dataId;
		that.fid = fid;
		that.ftrIdn = ftrIdn;
		that.dataCtgryId = dataCtgryId;
		that.ftrCde = ftrCde;
		
		var url = CONTEXT_PATH + "/window/manageHist/listManageHistory/page.do";
		var windowOptions = {
			width : 490,
			height : 270,
			inClose : function(){
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
			that.loadData(ftrIdn, ftrCde, dataCtgryId);
		});
		that.selector = "#" + id;
	},
	
	loadData : function(ftrIdn, ftrCde, dataCtgryId){
		var that = this;
		var url = "";
		var histUrl = CONTEXT_PATH + "/manageHist/" + ftrIdn + "/" + ftrCde;
		
		that.ftrIdn = ftrIdn;
		that.ftrCde = ftrCde;
		that.dataCtgryId = dataCtgryId;
		
		//강릉, 과천, 동해, 속초 공사대장 조회 가능하도록 수정
		if(PROJECT_CODE == "gn" || PROJECT_CODE == "gc" || PROJECT_CODE == "dh" || PROJECT_CODE == "sc") {
			if(dataCtgryId == "ROAD" || dataCtgryId == "ROAD_ATTACHMENT" || dataCtgryId == "TRANSPORTATION") {
				url = histUrl + "/listRoadCntrwkRegstr.do";
			} else if(dataCtgryId == "WATER" || dataCtgryId == "WATER_ATTACHMENT") {
				url = histUrl + "/listWrppCntrwkRegstr.do";
			} else if(dataCtgryId == "SEWAGE" || dataCtgryId == "SEWAGE_ATTACHMENT") {
				url = histUrl + "/listSwegCntrwkRegstr.do";
			} else {
				$.messager.alert(that.TITLE, dataCtgryId + "는 정의되지 않은 카테고리 입니다.");
				return;
			}
		}
		else {
			if(dataCtgryId == "ROAD" || dataCtgryId == "ROAD_ATTACHMENT" || dataCtgryId == "TRANSPORTATION" || dataCtgryId == "WATER" || dataCtgryId == "WATER_ATTACHMENT" || dataCtgryId == "SEWAGE" || dataCtgryId == "SEWAGE_ATTACHMENT"){
				url = histUrl + "/listCommon.do";
			}
			else {
				$.messager.alert(that.TITLE, dataCtgryId + "는 정의되지 않은 카테고리 입니다.");
				return;
			}
		} 
	
		
		$.get(url).done(function(response) {
			$("#tbl_manage_history_list").datagrid("loadData", { total : response.rows.length, rows : response.rows });
		}).fail(function() {
			$.messager.alert("관리이력을 가져오는데 실패했습니다.");
		});
	},
	
	close : function() {
		var that = this;
		
		that.data = null;
		that.dataId = null;
		that.fids = null;
		that.dataCtgryId = null;
		that.ftrCde = null;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
};

windowObj.manageHistObj.view = {
	
	TITLE : "관리이력 상세정보",
	
	CLASS_NAME : "ManageHistoryDetail",
	
	selector : null,
	
	ftrIdn : null,
	
	dataCtgryId : null,
	
	shtIdn : null,
	
	regstrFtrIdn : null,
	
	cntIdn : null,
	
	bindEvents : function() {
		var that = this;
		
		// 공사대장 열기
		$(".a_view_cntrct_regstr", that.selector).on("click", function() {
			var dataCtgryId = that.dataCtgryId;
			var cntIdn = that.cntIdn;
			//공사대장 관리번호
			var regstrFtrIdn = that.regstrFtrIdn;
			
			if(dataCtgryId == "ROAD" || dataCtgryId == "ROAD_ATTACHMENT" || dataCtgryId == "TRANSPORTATION") {
				if(regstrFtrIdn != "") {
					roadCntrwkRegstrObj.view.open(cntIdn);
				} else {
					alert("도로공사대장이 존재하지 않습니다.");
				}
			} else if(dataCtgryId == "WATER" || dataCtgryId == "WATER_ATTACHMENT") {
				if(regstrFtrIdn != "") {
					wrppCntrwkRegstrObj.view.open(cntIdn);
				} else {
					alert("상수공사대장이 존재하지 않습니다.");
				}
			} else if(dataCtgryId == "SEWAGE" || dataCtgryId == "SEWAGE_ATTACHMENT"){
				if(regstrFtrIdn != "") {
					swgeCntrwkRegstrObj.view.open(cntIdn);
				} else {
					alert("하수공사대장이 존재하지 않습니다.");
				}
			}
			
			return false;
		});
		
		// 편집
		$(".a_edit_view_hist", that.selector).on("click", function() {
			windowObj.manageHistObj.edit.open(that.dataCtgryId, that.ftrIdn, that.shtIdn);
			that.close();
			
			return false;
		});
		
		// 닫기
		$(".a_close_view_hist", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	open : function(index, dataCtgryId, field) {
		var that = this;
		
		that.dataCtgryId = dataCtgryId;
		that.ftrIdn = field.ftrIdn;
		that.shtIdn = field.shtIdn;
		that.regstrFtrIdn = field.regstrFtrIdn;
		that.cntIdn = field.cntIdn;

		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/manageHist/viewManageHistory/page.do";
		var windowOptions = {
			width : 360,
			height : 400,
			inClose : function(){
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.bindEvents();
			that.loadData(that.dataCtgryId, that.ftrIdn, that.shtIdn, that.regstrFtrIdn, that.cntIdn);
		});
		that.selector = "#" + id;
	},
	
	loadData : function(dataCtgryId, ftrIdn, shtIdn, regstrFtrIdn, cntIdn) {
		var that = this;
		var url = "";
		var histUrl = CONTEXT_PATH + "/manageHist/" + shtIdn;
		var tagStr = "";
		
		if(dataCtgryId == "ROAD" || dataCtgryId == "ROAD_ATTACHMENT" || dataCtgryId == "TRANSPORTATION" || dataCtgryId == "WATER" || dataCtgryId == "WATER_ATTACHMENT" || dataCtgryId == "SEWAGE" || dataCtgryId == "SEWAGE_ATTACHMENT"){
			url = histUrl + "/selectCommon.do";
		}
		else {
			$.messager.alert(that.TITLE, dataCtgryId + "는 정의되지 않은 카테고리 입니다.");
			return;
		}
		
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;
				
				tagStr += "<tr>";
				tagStr += "<th scope='row'>지형지물부호</th>";
				tagStr += "<td>" + data.ftrNam + "</td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>관리번호</th>";
				tagStr += "<td>" + data.ftrIdn + "</td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>이력관리번호</th>";
				tagStr += "<td>" + data.shtIdn + "</td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>관리이력구분</th>";
				tagStr += "<td>" + data.mnhNam + "</td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>보수사유</th>";
				if(dataCtgryId == "ROAD" || dataCtgryId == "ROAD_ATTACHMENT" || dataCtgryId == "TRANSPORTATION" || dataCtgryId == "WATER" || dataCtgryId == "WATER_ATTACHMENT" || dataCtgryId == "SEWAGE" || dataCtgryId == "SEWAGE_ATTACHMENT") {
					tagStr += "<td>" + data.sbjNam + "</td>";
				}
				else {
					tagStr += "<td>" + data.sbjCde + "</td>";
				}
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>보수사유<br>상세설명</th>";
				tagStr += "<td>" + data.rerDes + "</td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>보수내용</th>";
				tagStr += "<td>" + data.repDes + "</td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>보수시작일</th>";
				tagStr += "<td>" + data.sreYmd + "</td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>보수종료일</th>";
				tagStr += "<td>" + data.ereYmd + "</td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>시공자</th>";
				tagStr += "<td>" + data.oprNam + "</td>";
				tagStr += "</tr>";
				
				$("#tbl_view_manage_hist", that.selector).html(tagStr);
			}
			else {
				alert("관리이력 상세정보를 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("관리이력 상세정보를 불러오는데 실패했습니다.");
		});
	},
	
	close : function() {
		var that = this;
		
		that.ftrIdn = null;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
};

// 관리이력 편집 객체
windowObj.manageHistObj.edit = {
	
	TITLE : "관리이력 상세정보 수정",
	
	CLASS_NAME : "ManageHistoryModify",
	
	selector : null,
	
	ftrIdn : null,
	
	dataCtgryId : null,
	
	shtIdn : null,
	
	initUi : function() {
		var that = this;

		// databox 생성
		$('.easyui-datebox', that.selector).datebox({
		    required:true
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장
		$(".a_save_modify_hist", that.selector).on("click", function() {
			that.modify(that.dataCtgryId, that.ftrIdn, that.shtIdn);
			return false;
		});
		
		// 닫기
		$(".a_close_modify_hist", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
		
	open : function(dataCtgryId, ftrIdn, shtIdn) {
		var that = this;
		
		that.dataCtgryId = dataCtgryId;
		that.ftrIdn = ftrIdn;
		that.shtIdn = shtIdn;
		
		var url = CONTEXT_PATH + "/window/manageHist/modifyManageHistory/page.do";
		var windowOptions = {
			width : 335,
			height : 480,
			inClose : function(){
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.bindEvents();
			// promise step4
			that.loadData(dataCtgryId, ftrIdn, shtIdn).done(function() {
				that.initUi();
			});
		});
		that.selector = "#" + id;
	},

	loadData : function(dataCtgryId, ftrIdn, shtIdn) {
		var that = this;
		// promise step1
		var deferred = $.Deferred();
		
		var url = "";
		var histUrl = CONTEXT_PATH + "/manageHist/" + shtIdn;
		var tagStr = "";
		
		if(dataCtgryId == "ROAD" || dataCtgryId == "ROAD_ATTACHMENT" || dataCtgryId == "TRANSPORTATION" || dataCtgryId == "WATER" || dataCtgryId == "WATER_ATTACHMENT" || dataCtgryId == "SEWAGE" || dataCtgryId == "SEWAGE_ATTACHMENT"){
			url = histUrl + "/selectCommon.do";
		}
		else {
			$.messager.alert(that.TITLE, dataCtgryId + "는 정의되지 않은 카테고리 입니다.");
			return;
		}
		
		$.get(url).done(function(result) {
			if(result && result.data && result.mnhList) {
				var data = result.data;
				var mnhList = result.mnhList;
				var sbjCde = result.sbjCde;
				
				tagStr += "<tr>";
				tagStr += "<th scope='row'>지형지물부호</th>";
				tagStr += "<td><input class='w_200' id='txt_manageHist_ftr_nam' name='txtManageHistFtrNam' value='" + data.ftrNam + "' disabled='disabled' /></td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>관리번호</th>";
				tagStr += "<td><input class='w_200' id='txt_manageHist_ftr_idn' name='txtManageHistFtrIdn' value='" + data.ftrIdn + "' disabled='disabled' /></td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>이력관리번호</th>";
				tagStr += "<td><input class='w_200' id='txt_manageHist_sht_idn' name='txtManageHistShtIdn' value='" + data.shtIdn + "' disabled='disabled' /></td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>관리이력구분</th>";
				tagStr += "<td>";
				tagStr += "<select id='select_manageHist_mnh_cde' class='w_200'>";
				for(var i in mnhList) {
					tagStr += "<option value='" + mnhList[i].codeId + "'";
					if(mnhList[i].codeId == data.mnhCde) {
						tagStr += "selected='selected'";
					}
					tagStr += " >" + mnhList[i].codeNm + "</option>";
				}
				tagStr += "</select>";
				tagStr += "</td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>보수사유</th>";
				if(dataCtgryId == "ROAD" || dataCtgryId == "ROAD_ATTACHMENT" || dataCtgryId == "TRANSPORTATION" || dataCtgryId == "WATER" || dataCtgryId == "WATER_ATTACHMENT" || dataCtgryId == "SEWAGE" || dataCtgryId == "SEWAGE_ATTACHMENT") {
					tagStr += "<td>";
					tagStr += "<select id='select_manageHist_sbj_cde' class='w_200'>";
					for(var i in sbjCde) {
						tagStr += "<option value='" + sbjCde[i].codeId + "'";
						if(sbjCde[i].codeId == data.sbjCde) {
							tagStr += "selected='selected'";
						}
						tagStr += " >" + sbjCde[i].codeNm + "</option>";
					}
					tagStr += "</select>";
					tagStr += "</td>";
					tagStr += "</tr>";
				}
				else {
					tagStr += "<td><input class='w_200' id='txt_manageHist_sbj_cde' name='txtManageHistSbjCde' value='" + data.sbjCde + "' /></td>";
					tagStr += "</tr>";
				}
				tagStr += "<tr>";
				tagStr += "<th scope='row'>보수사유<br>상세설명</th>";
				tagStr += "<td><textarea id='txa_manageHist_rer_des' name='txa_manageHist_rer_des' class='w_200 h_50'>" + data.rerDes + "</textarea></td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>보수내용</th>";
				tagStr += "<td><textarea id='txa_manageHist_rep_des' name='txa_manageHist_rep_des' class='w_200 h_50'>" + data.repDes + "</textarea></td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>보수시작일</th>";
				tagStr += "<td><input class='inputText easyui-datebox w_200' type='text' id='txt_manageHist_sre_ymd' name='txtManageHistSreYmd' value='" + data.sreYmd + "' /></td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>보수종료일</th>";
				tagStr += "<td><input class='inputText easyui-datebox w_200' type='text' id='txt_manageHist_ere_ymd' name='txtManageHistEreYmd' value='" + data.ereYmd + "' /></td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>시공자</th>";
				tagStr += "<td><textarea id='txa_manageHist_opr_nam' name='txa_manageHist_opr_nam' class='w_200'>" + data.oprNam + "</textarea></td>";
				tagStr += "</tr>";
				
				$("#tbl_modify_manage_hist", that.selector).html(tagStr);
				// promise step2
				deferred.resolve();
			}
			else {
				alert("관리이력 상세정보를 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("관리이력 상세정보를 불러오는데 실패했습니다.");
		});
		// promise step3
		return deferred.promise();
	},
	
	modify : function(dataCtgryId, ftrIdn, shtIdn) {
		var that = this;
		
		var url = "";
		var histUrl = CONTEXT_PATH + "/manageHist/" + shtIdn;
		
		if(dataCtgryId == "ROAD" || dataCtgryId == "ROAD_ATTACHMENT" || dataCtgryId == "TRANSPORTATION" || dataCtgryId == "WATER" || dataCtgryId == "WATER_ATTACHMENT" || dataCtgryId == "SEWAGE" || dataCtgryId == "SEWAGE_ATTACHMENT"){
			url = histUrl + "/modifyCommon.do";
		}
		else {
			$.messager.alert(that.TITLE, dataCtgryId + "는 정의되지 않은 카테고리 입니다.");
			return;
		}
		
		var data = {};

		data.mnhCde = $("#select_manageHist_mnh_cde").val();
		
		if(dataCtgryId == "ROAD" || dataCtgryId == "ROAD_ATTACHMENT" || dataCtgryId == "TRANSPORTATION" || dataCtgryId == "WATER" || dataCtgryId == "WATER_ATTACHMENT" || dataCtgryId == "SEWAGE" || dataCtgryId == "SEWAGE_ATTACHMENT"){
			data.sbjCde = $("#select_manageHist_sbj_cde").val();
		}
		else {
			data.sbjCde = $("#txt_manageHist_sbj_cde").val();
		}
		
		data.rerDes = $("#txa_manageHist_rer_des").val();
		data.repDes = $("#txa_manageHist_rep_des").val();
		data.sreYmd = $("#txt_manageHist_sre_ymd").datebox('getValue');
		data.ereYmd = $("#txt_manageHist_ere_ymd").datebox('getValue');
		data.oprNam = $("#txa_manageHist_opr_nam").val();
		
		if(data.rerDes == "") {
			alert("보수사유상세설명은 필수입력 입니다.");
			return false;
		}
		else if(data.repDes == "") {
			alert("보수내용은 필수입력 입니다.");
			return false;
		}
		else if(data.sreYmd == "") {
			alert("보수시작일은 필수입력 입니다.");
			return false;
		}
		else if(data.ereYmd == "") {
			alert("보수종료일은 필수입력 입니다.");
			return false;
		}
		else {
			$.post(url, data).done(function() {
				windowObj.manageHistObj.close();
				that.close();
				windowObj.registerObj.manageHistory();
			}).fail(function(result) {
				alert("관리이력 상세정보를 저장하는데 실패했습니다.");
			});
		}
	},
	
	close : function() {
		var that = this;
		
		that.ftrIdn = null;
		that.dataCtgryId = null;
		that.shtIdn = null;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
};

//관리이력 등록 객체
windowObj.manageHistObj.add = {
	
	TITLE : "관리이력 상세정보 등록",
	
	CLASS_NAME : "ManageHistoryAdd",
	
	selector : null,
	
	dataCtgryId : null,
	
	ftrIdn : null,
	
	shtIdn : null,
	
	initUi : function() {
		var that = this;

		// databox 생성
		$('.easyui-datebox', that.selector).datebox({
		    required:true
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장
		$(".a_save_add_hist", that.selector).on("click", function() {
			that.add(that.dataCtgryId);
			return false;
		});
		
		// 닫기
		$(".a_close_add_hist", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	open : function(dataCtgryId, ftrCde, ftrIdn) {
		var that = this;
		
		that.dataCtgryId = dataCtgryId;
		that.ftrCde = ftrCde;
		that.ftrIdn = ftrIdn;
		
		var url = CONTEXT_PATH + "/window/manageHist/addManageHistory/page.do";
		var windowOptions = {
			width : 335,
			height : 450,
			inClose : function(){
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.bindEvents();
			// promise step4
			that.loadData(dataCtgryId, ftrCde, ftrIdn).done(function() {
				that.initUi();
			});
		});
		that.selector = "#" + id;
	},
	
	loadData : function(dataCtgryId, ftrCde, ftrIdn) {
		var that = this;
		// promise step1
		var deferred = $.Deferred();
		
		var url = "";
		var histUrl = CONTEXT_PATH + "/manageHist/";
		var tagStr = "";
		var data = {};

		data.ftrCde = that.ftrCde;
		data.ftrIdn = that.ftrIdn;
		that.dataCtgryId = dataCtgryId;
		
		if(dataCtgryId == "ROAD" || dataCtgryId == "ROAD_ATTACHMENT" || dataCtgryId == "TRANSPORTATION" || dataCtgryId == "WATER" || dataCtgryId == "WATER_ATTACHMENT" || dataCtgryId == "SEWAGE" || dataCtgryId == "SEWAGE_ATTACHMENT"){
			url = histUrl + "cmtManageHistCode.do";
		}
		else {
			$.messager.alert(that.TITLE, that.dataCtgryId + "는 정의되지 않은 카테고리 입니다.");
			return;
		}
		
		$.post(url, data).done(function(result) {
			if(result && result.data) {
				var data = result.data;
				var sbjCde = result.sbjCde;
				var mnhList = result.mnhList;
				
				tagStr += "<tr>";
				tagStr += "<th scope='row'>지형지물부호</th>";
				tagStr += "<td><input class='w_200' id='txt_add_manageHist_ftr_cde' name='txtAddManageHistFtrNam' ftr-cde='" + data.codeId + "' value='" + data.codeNm + "' disabled='disabled' /></td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>관리번호</th>";
				tagStr += "<td><input class='w_200' id='txt_add_manageHist_ftr_idn' name='txtAddManageHistFtrIdn' value='" + that.ftrIdn + "' disabled='disabled' /></td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>관리이력구분</th>";
				tagStr += "<td>";
				tagStr += "<select id='select_add_manageHist_mnh_cde' class='w_200'>";
				for(var i in mnhList) {
					tagStr += "<option value='" + mnhList[i].codeId + "'>" + mnhList[i].codeNm + "</option>";
				}
				tagStr += "</select>";
				tagStr += "</td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>보수사유</th>";
				if(dataCtgryId == "ROAD" || dataCtgryId == "ROAD_ATTACHMENT" || dataCtgryId == "TRANSPORTATION" || dataCtgryId == "WATER" || dataCtgryId == "WATER_ATTACHMENT" || dataCtgryId == "SEWAGE" || dataCtgryId == "SEWAGE_ATTACHMENT"){
					tagStr += "<td>";
					tagStr += "<select id='select_manageHist_sbj_cde' class='w_200'>";
					for(var i in sbjCde) {
						tagStr += "<option value='" + sbjCde[i].codeId + "'>" + sbjCde[i].codeNm + "</option>";
					}
					tagStr += "</select>";
					tagStr += "</td>";
					tagStr += "</tr>";
				}
				else {
					tagStr += "<td><input class='w_200' id='txt_add_manageHist_sbj_cde' name='txtAddManageHistSbjCde' value='' /></td>";
				}
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>보수사유<br>상세설명</th>";
				tagStr += "<td><textarea id='txa_add_manageHist_rer_des' name='txaAddManageHist_rer_des' class='w_200 h_50'></textarea></td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>보수내용</th>";
				tagStr += "<td><textarea id='txa_add_manageHist_rep_des' name='txaAddManageHist_rep_des' class='w_200 h_50'></textarea></td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>보수시작일</th>";
				tagStr += "<td><input class='inputText easyui-datebox w_200' type='text' id='txt_add_manageHist_sre_ymd' name='txtAddManageHistSreYmd' value='' /></td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>보수종료일</th>";
				tagStr += "<td><input class='inputText easyui-datebox w_200' type='text' id='txt_add_manageHist_ere_ymd' name='txtAddManageHistEreYmd' value='' /></td>";
				tagStr += "</tr>";
				tagStr += "<tr>";
				tagStr += "<th scope='row'>시공자</th>";
				tagStr += "<td><textarea id='txa_add_manageHist_opr_nam' name='txaAddManageHist_opr_nam' class='w_200'></textarea></td>";
				tagStr += "</tr>";
				
				$("#tbl_add_manage_hist", that.selector).html(tagStr);
				// promise step2
				deferred.resolve();
			}
			else {
				alert("관리이력 상세정보를 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("관리이력 상세정보를 불러오는데 실패했습니다.");
		});
		// promise step3
		return deferred.promise();
	},
	
	add : function(dataCtgryId) {
		var that = this;
		
		that.dataCtgryId = dataCtgryId;
		
		var url = "";
		var histUrl = CONTEXT_PATH + "/manageHist";
		
		if(dataCtgryId == "ROAD" || dataCtgryId == "ROAD_ATTACHMENT" || dataCtgryId == "TRANSPORTATION" || dataCtgryId == "WATER" || dataCtgryId == "WATER_ATTACHMENT" || dataCtgryId == "SEWAGE" || dataCtgryId == "SEWAGE_ATTACHMENT") {
			url = histUrl + "/addCommon.do";
		}
		else {
			$.messager.alert(that.TITLE, dataCtgryId + "는 정의되지 않은 카테고리 입니다.");
			return;
		}
		
		var data = {};

		data.ftrCde = $("#txt_add_manageHist_ftr_cde", that.selector).attr("ftr-cde");
		data.ftrIdn = $("#txt_add_manageHist_ftr_idn", that.selector).val();
		data.mnhCde = $("#select_add_manageHist_mnh_cde", that.selector).val();
		if(dataCtgryId == "ROAD" || dataCtgryId == "ROAD_ATTACHMENT" || dataCtgryId == "TRANSPORTATION" || dataCtgryId == "WATER" || dataCtgryId == "WATER_ATTACHMENT" || dataCtgryId == "SEWAGE" || dataCtgryId == "SEWAGE_ATTACHMENT"){
			data.sbjCde = $("#select_manageHist_sbj_cde", that.selector).val();
		}
		else {
			data.sbjCde = $("#txt_add_manageHist_sbj_cde", that.selector).val();
		}
		data.rerDes = $("#txa_add_manageHist_rer_des", that.selector).val();
		data.repDes = $("#txa_add_manageHist_rep_des", that.selector).val();
		data.sreYmd = $("#txt_add_manageHist_sre_ymd", that.selector).datebox('getValue');
		data.ereYmd = $("#txt_add_manageHist_ere_ymd", that.selector).datebox('getValue');
		data.oprNam = $("#txa_add_manageHist_opr_nam", that.selector).val();
		
		if(data.rerDes == "") {
			alert("보수사유상세설명은 필수입력 입니다.");
			return false;
		}
		else if(data.repDes == "") {
			alert("보수내용은 필수입력 입니다.");
			return false;
		}
		else if(data.sreYmd == "") {
			alert("보수시작일은 필수입력 입니다.");
			return false;
		}
		else if(data.ereYmd == "") {
			alert("보수종료일은 필수입력 입니다.");
			return false;
		}
		else {
			$.post(url, data).done(function() {
				that.close();
				windowObj.registerObj.manageHistory();
			}).fail(function(result) {
				alert("관리이력 상세정보를 저장하는데 실패했습니다.");
			});
		}
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		
		that.dataCtgryId = null;
		that.selector = null;
	}

};