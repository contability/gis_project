/**
 * 기준점 관리 객체
 */
ctrlPntObj = {
	
	TITLE : "기준점 검색",
	
	CLASS_NAME : "ControlPointSearch",
	
	selector : null,
	
	isCreated : false,

	
	initUi : function() {
		var that = this;
		
		$("#searchControlPoint .easyui-textbox", that.selector).textbox();
		$("#searchControlPoint .easyui-datebox", that.selector).datebox();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 초기화
		$("#searchControlPoint .a_reset", that.selector).on("click", function() {
			$("#searchControlPoint input[name=bseNam]", that.selector).val("");
			$("#searchControlPoint input[name=setYmd]", that.selector).val("");
			$("#searchControlPoint input[name=setLoc]", that.selector).val("");
			return false;
		});
		
		// 검색
		$("#searchControlPoint .a_search", that.selector).on("click", function() {
			that.search();
			return false;
		});
	},

	open : function(menuUrl) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var windowOptions = {
			width : 560,
			height : 179,
			top : 120,
			left : 330,
			isClose : function() {
				that.close();
			}
		};
		
		var url = CONTEXT_PATH + menuUrl;
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.initUi();
				that.bindEvents();
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	search : function() {
		var that = this;
		
		var dataIds = ['ETL_NTBP_PS','ETL_NTTP_PS','ETL_NTLP_PS','ETL_PCSP_PS','ETL_PCTP_PS','ETL_PCTS_PS','ETL_CTBP_PS'];
		
		var params = {};
		params.dataIds = dataIds;
		params.bseNam = $("#searchControlPoint input[name=bseNam]", that.selector).val();
		params.setYmd = $("#searchControlPoint input[name=setYmd]", that.selector).val();
		params.setLoc = $("#searchControlPoint input[name=setLoc]", that.selector).val();
		
		var url = CONTEXT_PATH + "/ctrlpnt/searchSummaries.do";
		$.post(url, params).done(function(response) {
			var data = [];
			var ids = [];
			
			if(response.result.length < 1){
				alert("조건에 맞는 결과가 없습니다.");
				return false;
			}
			else {
				data.push({
					dataAlias : "기준점",
					dataId : "ETL_RGCP_PS",
					ids : ids
				});
				
				for(var j in response.result) {
					ids.push(response.result[j].ftrIdn);
				}
				
				resultObj.addRows(data);
			}
		}).fail(function() {
			alert("기준점 리스트를 가져오는데 실패하였습니다.");
		});
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
};

/**
 * 기준점 등록
 */
ctrlPntObj.addRegister = {
		
	TITLE : "측량기준점 등록",
	
	CLASS_NAME : "AddControlPoint",
	
	selector : null,
	
	isCreated : false,

	open : function(menuUrl) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var windowOptions = {
			width : 715,
			height : 489,
			onClose : function() {
				that.close();
			}
		};
			
		var url = CONTEXT_PATH + menuUrl;
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.init();
				that.initUi();
				that.bindEvents();
				
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},

	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result.state >= 1) {
					$.messager.alert({
						title : that.TITLE,
						msg : "기준점이 등록되었습니다.",
						fn : function() {
							that.close();
						}
					});
				}
				else {
					$.messager.alert(that.TITLE, "기준점 등록에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function(){
		var that = this;
		
		$("#addControlPoint .easyui-textbox", that.selector).textbox();
		$("#addControlPoint .easyui-combobox", that.selector).combobox();
		$("#addControlPoint .easyui-datebox", that.selector).datebox();
	},	

	bindEvents : function() {
		var that = this;

		// 등록버튼
		$("#btn_insert", that.selector).on("click", function() {
			that.insert();
			return false;
		});

		// 닫기버튼
		$("#btn_close", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	/**
	 * 등록
	 */
	insert : function() {
		var that = this;
		
//		var url = CONTEXT_PATH + "/rest/cityPlanRoad/insert.do";
//		$("form", that.selector).attr("action", url);
//		$("form", that.selector).submit();
	},
	
	/**
	 * 닫기
	 */
	close:function(){
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
};





/**
 * 도시기준점 전체 검색
 */
//ctrlPntObj.allList = {
//		search : function() {
//			var that = this;
//			
//			var url = CONTEXT_PATH + "/ctrlpnt/listCtrlpnt.do";
//			var params = {};
//			
//			params.grdCde = $("select[name=grdCde]", that.selector).val();
//			params.bseNam = $("input[name=bseNam]", that.selector).val();
//			
//			$.get(url, params).done(function(response) {
//				var data = [];
//				var ids = [];
//				
//				if(response.result.length < 1){
//					alert("조건에 맞는 결과가 없습니다.");
//					return false;
//				}
//				else {
//					data.push({
//						dataAlias : "기준점",
//						dataId : "ETL_RGCP_PS",
//						ids : ids
//					});
//					
//					for(var j in response.result) {
//						ids.push(response.result[j].ftrIdn);
//					}
//					
//					resultObj.addRows(data);
//				}
//			}).fail(function() {
//				alert("기준점 리스트를 가져오는데 실패하였습니다.");
//			});
//			
//		} 
//};
//
///**
// * 기준점 상세정보
// */
//ctrlPntObj.view = {
//		
//	TITLE : "기준점 상세정보",
//	
//	CLASS_NAME : "CtrlPntDetail",
//
//	selector : null,
//	
//	ftrIdn : null,
//	
//	isCreated : false,
//	
//	initUi : function() {
//		// number olny
//		$(".numOnly").keyup(function() {
//			$(this).val($(this).val().replace(/[^0-9]/g, ""));
//		});
//	},
//	
//	bindEvents : function() {
//		var that = this;
//
//		// 편집
//		$("#a_modify_ctrlpntAdd", that.selector).on("click", function() {
//			var ftrIdn = $("#ftrIdn", that.selector).val();
//			
//			ctrlPntObj.modify.open(ftrIdn);
//			that.close();
//		});
//		
//		// 망실신고 등록
////		$("#a_lossSttemnt_ctrlpntAdd", that.selector).on("click", function() {
////			var ftrIdn = $("#ftrIdn", that.selector).val();
////			var bseNam = $("#bseNam", that.selector).val();
////			
////			lossSttemntObj.add.open(ftrIdn, bseNam);
////		});
//		
//		// 출력
//		$("#a_print_ctrlpntAdd", that.selector).on("click", function() {
//			var ftrCde = $("#ftrCde", that.selector).val(); 
//			var ftrIdn = $("#ftrIdn", that.selector).val(); 
//			
//			ctrlPntObj.view.print(ftrCde, ftrIdn);
//		});
//	},
//	
//	open : function(fids) {
//		var that = this;
//		
//		if(that.selector) {
//			that.close();
//		}
//		
//		that.ftrIdn = fids;
//		
//		var url = CONTEXT_PATH + "/ctrlpnt/" + that.ftrIdn + "/selectOneCtrlpntPage.do";
//		var windowOptions = {
//			width : 630,
//			height : 579,
//			top : 120,
//			left : 550,
//			onClose : function() {
//				that.close();
//			}
//		};
//		
//		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
//			if(!that.isCreated) {
//				that.initUi();
//				that.bindEvents();
//				that.isCreated = true;
//			}
//		});
//		
//		that.selector = "#" + id;
//	},
//	
//	print : function(ftrCde, ftrIdn) {
//		var url = CONTEXT_PATH + "/clipreport/ctrlPnt.do?ftrCde=" + ftrCde + "&ftrIdn=" + ftrIdn;
//		
//		popupUtils.open(url, "기준점", { width : 900 , height : 800, left : 300, top:150 });
//	},
//	
//	close : function() {
//		var that = this;
//		
//		windowObj.removeWindow(that.selector);
//		that.ftrIdn = null;
//		that.selector = null;
//		that.isCreated = false;
//	}
//};
//
///**
// * 기준점 수정
// */
//ctrlPntObj.modify = {
//		
//	TITLE : "기준점 수정",
//	
//	CLASS_NAME : "CtrlPntModify",
//
//	selector : null,
//	
//	ftrIdn : null,
//	
//	isCreated : false,
//	
//	init : function() {
//		var that = this;
//		
//		// number olny
//		$(".numOnly").keyup(function() {
//			$(this).val($(this).val().replace(/[^0-9]/g, ""));
//		});
//		
//		$("form", that.selector).ajaxForm({
//			dataType : "json",
//			success : function(result) {
//				if(result) {
//					ctrlPntObj.view.open(that.ftrIdn);
//					that.close();
//					alert("기준점이 수정되었습니다.");
//				}
//				else {
//					alert("기준점 수정에 실패하였습니다.");
//				}
//			}
//		});
//	},
//	
//	initUi : function() {
//		var that = this;
//		
//		// easyUi datebox init
//		$("#ctrlpntModify .easyui-datebox", that.selector).datebox();
//	},
//	
//	bindEvents : function() {
//		var that = this;
//		
//		// 저장
//		$("#a_save_ctrlpntModify", that.selector).on("click", function() {
//			that.modify();
//		});
//	},
//	
//	open : function(ftrIdn) {
//		var that = this;
//		
//		if(that.selector) {
//			that.close();
//		}
//		
//		that.ftrIdn = ftrIdn;
//		
//		var url = CONTEXT_PATH + "/ctrlpnt/" + that.ftrIdn + "/modifyCtrlpntPage.do";
//		var windowOptions = {
//			width : 620,
//			height : 579,
//			top : 120,
//			left : 550,
//			onClose : function() {
//				that.close();
//			}
//		};
//		
//		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
//			if(!that.isCreated) {
//				that.init();
//				that.initUi();
//				that.bindEvents();
//				
//				that.isCreated = true;
//			}
//		});
//		
//		that.selector = "#" + id;
//	},
//	
//	modify : function() {
//		var that = this;
//		
//		that.ftrIdn = $("#ftrIdn", that.selector).val();
//		
//		var url = CONTEXT_PATH + "/ctrlpnt/" + that.ftrIdn + "/modifyCtrlpnt.do";
//		$("form", that.selector).attr("action", url);
//		$("form", that.selector).submit();
//	},
//	
//	close : function() {
//		var that = this;
//		
//		windowObj.removeWindow(that.selector);
//		that.ftrIdn = null;
//		that.selector = null;
//		that.isCreated = false;
//	}
//};
//
///// 기준점 등록
//ctrlPntObj.add = {
//		
//	TITLE : "기준점 등록",
//	
//	CLASS_NAME : "CtrlpntAdd",
//
//	selector : null,
//	
//	isCreated : false,
//	
//	init : function() {
//		var that = this;
//		
//		$("form", that.selector).ajaxForm({
//			dataType : "json",
//			success : function(result) {
//				if(result) {
//					that.close();
//					alert("기준점이 등록되었습니다.");
//				}
//				else {
//					alert("기준점 등록에 실패하였습니다.");
//				}
//			}
//		});
//	},
//	
//	initUi : function() {
//		var that = this;
//		
//		// easyUi datebox init
//		$("#ctrlpntAdd .easyui-datebox", that.selector).datebox();
//	},
//	
//	bindEvents : function() {
//		var that = this;
//		
//		// 저장
//		$("#a_save_ctrlpntAdd", that.selector).on("click", function() {
//			that.save();
//			
//			return false;
//		});
//	},
//	
//	open : function() {
//		var that = this;
//		
//		if(that.selector) {
//			that.close();
//		}
//		
//		var url = CONTEXT_PATH + "/ctrlpnt/addCtrlpntPage.do";
//		var windowOptions = {
//			width : 620,
//			height : 579,
//			top : 120,
//			left : 550,
//			onClose : function() {
//				that.close();
//			}
//		};
//		
//		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
//			if(!that.isCreated) {
//				that.init();
//				that.initUi();
//				that.bindEvents();
//				
//				that.isCreated = true;
//			}
//		});
//		
//		that.selector = "#" + id;
//	},
//	
//	save : function() {
//		var that = this;
//		
//		var nggX = $("input[name=nggX]").val();
//		var nggY = $("input[name=nggY]").val();
//		var url = CONTEXT_PATH + "/ctrlpnt/addCtrlpnt.do";
//		
//		if(nggX == "") {
//			alert("N(X)는 필수 입력값 입니다.");
//		}
//		else if(nggY == "") {
//			alert("N(Y)는 필수 입력값 입니다.");
//		}
//		else {
//			$("form", that.selector).attr("action", url);
//			$("form", that.selector).submit();
//		}
//	},
//	
//	close : function() {
//		var that = this;
//		
//		windowObj.removeWindow(that.selector);
//		that.selector = null;
//		that.isCreated = false;
//	}
//};
