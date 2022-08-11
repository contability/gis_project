/**
 * 측량표지현황조사 관리 객체
 */
mesrSgnalSttusExaminHistObj = {
		
	TITLE : "측량표지현황조사 목록",
	
	CLASS_NAME : "MesrSgnalSttusExaminHistList",
	
	selector : null,

	isCreated : false,
	
	ftfCde : null,
	
	ftfIdn : null,
	
	bindEvents : function() {
		var that = this;
		
		// 등록
		$("#a_add_listMesrSgnalSttusExaminHist", that.selector).on("click", function() {
			mesrSgnalSttusExaminHistObj.add.open(that.ftfCde, that.ftfIdn);
		});
		
		// 단 건 조회
		$(".tableSelector tbody tr", that.selector).click(function() {
			var element = $(this);
			var ftrIdn = element.attr("data-ftr-idn");
			
			mesrSgnalSttusExaminHistObj.view.open(ftrIdn);
		});
	},
	
	open : function(ftfCde, ftfIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.ftfCde = ftfCde;
		that.ftfIdn = ftfIdn;
		
		var url = CONTEXT_PATH + "/mesrSgnalSttusExaminHist/" + ftfCde + "/" + ftfIdn +"/listMesrSgnalSttusExaminHistPage.do";
		var windowOptions = {
			width : 450,
			height : 300,
			top : 220,
			left : 430,
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
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.ftfCde = null;
		that.ftfIdn = null;
		that.isCreated = false;
	}
	
};

mesrSgnalSttusExaminHistObj.add = {
		
	TITLE : "측량표지현황조사 등록",
	
	CLASS_NAME : "MesrSgnalSttusExaminHistAdd",

	selector : null,
	
	isCreated : false,
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					that.close();
					alert("측량표지현황조사 내역이 등록되었습니다.");
				}
				else {
					alert("측량표지현황조사 내역이 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function() {
		var that = this;
		
		// easyUi datebox init
		$("#addMesrSgnalSttusExaminHist .easyui-datebox", that.selector).datebox();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장
		$("#a_save_addMesrSgnalSttusExaminHist", that.selector).on("click", function() {
			that.save();
			
			mesrSgnalSttusExaminHistObj.close();
			
			var ftfCde = $("#ftfCde").val();
			var ftfIdn = $("#ftfIdn").val();
			
			mesrSgnalSttusExaminHistObj.open(ftfCde, ftfIdn);
			
			return false;
		});
	},
	
	open : function(ftfCde, ftfIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/mesrSgnalSttusExaminHist/" + ftfCde + "/" + ftfIdn + "/addMesrSgnalSttusExaminHistPage.do";
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
		
		var url = CONTEXT_PATH + "/mesrSgnalSttusExaminHist/addMesrSgnalSttusExaminHist.do";
		
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

mesrSgnalSttusExaminHistObj.view = {
		
	TITLE : "측량표지현황조사 조회",
	
	CLASS_NAME : "MesrSgnalSttusExaminHistView",

	selector : null,
	
	isCreated : false,
	
	bindEvents : function() {
		var that = this;
		
		//수정
		$("#a_modify_viewMesrSgnalSttusExaminHist", that.selector).on("click", function() {
			var ftrIdn = $("#ftrIdn", that.selector).val();

			mesrSgnalSttusExaminHistObj.modify.open(ftrIdn);
			
			that.close();
			
			return false;
		});
		
		// 출력
		$("#a_print_viewMesrSgnalSttusExaminHist", that.selector).on("click", function() {
			var ftfCde = that.ftfCde;
			var ftfIdn = that.ftfIdn;
			
			that.print(ftfCde, ftfIdn);
			
			return false;
		});
	},
	
	open : function(ftrIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/mesrSgnalSttusExaminHist/" + ftrIdn + "/selectOneMesrSgnalSttusExaminHistPage.do";
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
				that.bindEvents();
				
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	print : function(ftfCde, ftfIdn) {
		var url = CONTEXT_PATH + "/clipreport/mesrSgnalSttusExaminHist.do?ftfCde=" + ftfCde + "&ftfIdn=" + ftfIdn;
		
		popupUtils.open(url, "측량표지현황조사", { width : 900 , height : 800, left : 300, top:150 });
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}

};

mesrSgnalSttusExaminHistObj.modify = {
		
	TITLE : "측량표지현황조사 수정",
	
	CLASS_NAME : "MesrSgnalSttusExaminHistModify",

	selector : null,
	
	isCreated : false,
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					that.close();
					alert("측량표지현황조사 내역이 수정되었습니다.");
				}
				else {
					alert("측량표지현황조사 내역 수정이 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function() {
		var that = this;
		
		// easyUi datebox init
		$("#modifyMesrSgnalSttusExaminHist .easyui-datebox", that.selector).datebox();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장
		$("#a_save_modifyMesrSgnalSttusExaminHist", that.selector).on("click", function() {
			var ftrIdn = $("#ftrIdn", that.selector).val();

			that.save(ftrIdn);
			
			mesrSgnalSttusExaminHistObj.view.open(ftrIdn);
			
			return false;
		});
	},
	
	open : function(ftrIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/mesrSgnalSttusExaminHist/" + ftrIdn + "/modifyMesrSgnalSttusExaminHistPage.do";
		var windowOptions = {
			width : 660,
			height : 579,
			top : 120,
			left : 450,
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
	
	save : function(ftrIdn) {
		var that = this;
		
		var url = CONTEXT_PATH + "/mesrSgnalSttusExaminHist/" + ftrIdn + "/modifyMesrSgnalSttusExaminHist.do";
		
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