/**
 * 기준점 관리 객체
 */
ctrlPntObj = {
	
	TITLE : "기준점 검색",
	
	CLASS_NAME : "CtrlPntSearch",
	
	selector : null,
	
	isCreated : false,
	
	bindEvents : function() {
		var that = this;
		
		// 초기화
		$(".a_reset_ctrlpntSearch", that.selector).on("click", function() {
			$("select[name=grdCde]", that.selector).val("");
			$("input[name=bseNam]", that.selector).val("");

			return false;
		});
		
		// 검색
		$(".a_search_ctrlpntSearch", that.selector).on("click", function() {
			that.search();
			
			return false;
		});
	},
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/ctrlpnt/searchCtrlpntPage.do";
		var windowOptions = {
			width : 655,
			height : 149,
			top : 120,
			left : 330,
			isClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.bindEvents();
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	search : function() {
		var that = this;
		
		var url = CONTEXT_PATH + "/ctrlpnt/listCtrlpnt.do";
		var params = {};
		
		params.grdCde = $("select[name=grdCde]", that.selector).val();
		params.bseNam = $("input[name=bseNam]", that.selector).val();
		
		$.get(url, params).done(function(response) {
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
 * 기준점 상세정보
 */
ctrlPntObj.view = {
		
	TITLE : "기준점 상세정보",
	
	CLASS_NAME : "CtrlPntDetail",

	selector : null,
	
	ftrIdn : null,
	
	isCreated : false,
	
	initUi : function() {
		// number olny
		$(".numOnly").keyup(function() {
			$(this).val($(this).val().replace(/[^0-9]/g, ""));
		});
	},
	
	bindEvents : function() {
		var that = this;

		// 편집
		$("#a_modify_ctrlpntAdd", that.selector).on("click", function() {
			var ftrIdn = $("#ftrIdn", that.selector).val();
			
			ctrlPntObj.modify.open(ftrIdn);
			that.close();
		});
		
		// 망실신고 등록
		$("#a_lossSttemnt_ctrlpntAdd", that.selector).on("click", function() {
			var ftrIdn = $("#ftrIdn", that.selector).val();
			var bseNam = $("#bseNam", that.selector).val();
			
			lossSttemntObj.add.open(ftrIdn, bseNam);
		});
		
		// 출력
		$("#a_print_ctrlpntAdd", that.selector).on("click", function() {
			var ftrCde = $("#ftrCde", that.selector).val(); 
			var ftrIdn = $("#ftrIdn", that.selector).val(); 
			
			ctrlPntObj.view.print(ftrCde, ftrIdn);
		});
	},
	
	open : function(fids) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.ftrIdn = fids;
		
		var url = CONTEXT_PATH + "/ctrlpnt/" + that.ftrIdn + "/selectOneCtrlpntPage.do";
		var windowOptions = {
			width : 630,
			height : 579,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.initUi();
				that.bindEvents();
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	print : function(ftrCde, ftrIdn) {
		var url = CONTEXT_PATH + "/clipreport/ctrlPnt.do?ftrCde=" + ftrCde + "&ftrIdn=" + ftrIdn;
		
		popupUtils.open(url, "기준점", { width : 900 , height : 800, left : 300, top:150 });
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.ftrIdn = null;
		that.selector = null;
		that.isCreated = false;
	}
};

/**
 * 기준점 수정
 */
ctrlPntObj.modify = {
		
	TITLE : "기준점 수정",
	
	CLASS_NAME : "CtrlPntModify",

	selector : null,
	
	ftrIdn : null,
	
	isCreated : false,
	
	init : function() {
		var that = this;
		
		// number olny
		$(".numOnly").keyup(function() {
			$(this).val($(this).val().replace(/[^0-9]/g, ""));
		});
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					ctrlPntObj.view.open(that.ftrIdn);
					that.close();
					alert("기준점이 수정되었습니다.");
				}
				else {
					alert("기준점 수정에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function() {
		var that = this;
		
		// easyUi datebox init
		$("#ctrlpntModify .easyui-datebox", that.selector).datebox();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장
		$("#a_save_ctrlpntModify", that.selector).on("click", function() {
			that.modify();
		});
	},
	
	open : function(ftrIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.ftrIdn = ftrIdn;
		
		var url = CONTEXT_PATH + "/ctrlpnt/" + that.ftrIdn + "/modifyCtrlpntPage.do";
		var windowOptions = {
			width : 620,
			height : 579,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
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
	
	modify : function() {
		var that = this;
		
		that.ftrIdn = $("#ftrIdn", that.selector).val();
		
		var url = CONTEXT_PATH + "/ctrlpnt/" + that.ftrIdn + "/modifyCtrlpnt.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.ftrIdn = null;
		that.selector = null;
		that.isCreated = false;
	}
};

/// 기준점 등록
ctrlPntObj.add = {
		
	TITLE : "기준점 등록",
	
	CLASS_NAME : "CtrlpntAdd",

	selector : null,
	
	isCreated : false,
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					that.close();
					alert("기준점이 등록되었습니다.");
				}
				else {
					alert("기준점 등록에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function() {
		var that = this;
		
		// easyUi datebox init
		$("#ctrlpntAdd .easyui-datebox", that.selector).datebox();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장
		$("#a_save_ctrlpntAdd", that.selector).on("click", function() {
			that.save();
			
			return false;
		});
	},
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/ctrlpnt/addCtrlpntPage.do";
		var windowOptions = {
			width : 620,
			height : 579,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
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
	
	save : function() {
		var that = this;
		
		var nggX = $("input[name=nggX]").val();
		var nggY = $("input[name=nggY]").val();
		var url = CONTEXT_PATH + "/ctrlpnt/addCtrlpnt.do";
		
		if(nggX == "") {
			alert("N(X)는 필수 입력값 입니다.");
		}
		else if(nggY == "") {
			alert("N(Y)는 필수 입력값 입니다.");
		}
		else {
			$("form", that.selector).attr("action", url);
			$("form", that.selector).submit();
		}
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
};
