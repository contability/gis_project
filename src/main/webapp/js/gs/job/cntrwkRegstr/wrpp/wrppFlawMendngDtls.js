/**
 * 상수 하자 보수 내역 객체
 */

/// 상수 하자 보수 내역 조회
wrppCntrwkRegstrObj.wrppFlawMendngDtls = {
		
	TITLE : "하자 보수 내역 상세정보",
	
	CLASS_NAME : "WrppFlawMendngDtlsDetail",

	selector : null,
	
	ftrIdn : null,
	
	shtIdn : null,
	
	isCreated : false,
	
	open : function(ftrIdn, shtIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.ftrIdn = ftrIdn;
		that.shtIdn = shtIdn;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wrpp/" + that.ftrIdn + "/" + that.shtIdn + "/selectOneWrppFlawMendngDtlsPage.do";
		var windowOptions = {
			width : 450,
			height : 189,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.ftrIdn = null;
		that.selector = null;
		that.isCreated = false;
	}
};

/// 상수 하자 보수 내역 수정
wrppCntrwkRegstrObj.wrppFlawMendngDtls.modify = {
		
	TITLE : "하자 보수 내역 수정",
	
	CLASS_NAME : "WrppFlawMendngDtlsModify",

	selector : null,
	
	ftrIdn : null,
	
	shtIdn : null,
	
	isCreated : false,
	
	init : function() {
		var that = this;
		
		// ajax
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					wrppCntrwkRegstrObj.modify.close();

					var ftrIdn = $("#ftrIdn").val();
					wrppCntrwkRegstrObj.modify.open(ftrIdn);
					
					that.close();
					
					alert("하자 보수 내역이 수정되었습니다.");
				}
				else {
					alert("하자 보수 내역 수정에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function() {
		var that = this;
		
		// easyUi datebox init
		$("#modifyWrppFlawMendngDtls .easyui-datebox", that.selector).datebox();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장
		$(".btn_save_wrppFlawMendngDtlsModify", that.selector).on("click", function() {
			$.messager.confirm(that.TITLE, "저장하시겠습니까?", function(isTrue) {
				if(isTrue) {
					that.modify();
				}
			});
			return false;
		});

		// 삭제
		$(".btn_save_wrppFlawMendngDtlsRemove", that.selector).on("click", function() {
			$.messager.confirm(that.TITLE, "삭제하시겠습니까?", function(isTrue) {
				if(isTrue) {
					that.remove();
				}
			});
			return false;
		});
	},
	
	open : function(ftrIdn, shtIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.ftrIdn = ftrIdn;
		that.shtIdn = shtIdn;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wrpp/" + that.ftrIdn + "/" + that.shtIdn + "/modifyWrppFlawMendngDtlsPage.do";
		var windowOptions = {
			width : 450,
			height : 244,
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
		that.shtIdn = $("#shtIdn", that.selector).val();
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wrpp/" + that.ftrIdn + "/" + that.shtIdn + "/modifyWrppFlawMendngDtls.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	remove : function() {
		var that = this;
		
		that.shtIdn = $("#shtIdn", that.selector).val();
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wrpp/" + that.shtIdn + "/removeWrppFlawMendngDtls.do";
		
		$.post(url).done(function(result) {
			if(result) {
				wrppCntrwkRegstrObj.modify.close();

				var ftrIdn = $("#ftrIdn").val();
				wrppCntrwkRegstrObj.modify.open(ftrIdn);
				
				that.close();
				
				alert("하자 보수 내역을 삭제하였습니다.");
			}
			else {
				alert("하자 보수 내역 삭제에 실패했습니다.");
			}
		}).fail(function() {
			alert("하자 보수 내역 삭제에 실패했습니다.");
		});
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.ftrIdn = null;
		that.shtIdn = null;
		that.selector = null;
		that.isCreated = false;
	}
};

/// 상수 하자 보수 내역 등록
wrppCntrwkRegstrObj.wrppFlawMendngDtls.add = {
		
	TITLE : "하자 보수 내역 등록",
	
	CLASS_NAME : "WrppFlawMendngDtlsAdd",

	selector : null,
	
	ftrIdn : null,
	
	isCreated : false,
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					wrppCntrwkRegstrObj.modify.close();
					
					wrppCntrwkRegstrObj.modify.open(that.ftrIdn);
					
					that.close();
					
					alert("하자 보수 내역이 등록되었습니다.");
				}
				else {
					alert("하자 보수 내역 등록에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function() {
		var that = this;
		
		// easyUi datebox init
		$("#addWrppFlawMendngDtlsAdd .easyui-datebox", that.selector).datebox();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장
		$(".btn_save_wrppFlawMendngDtlsAdd", that.selector).on("click", function() {
			that.add();
			
			return false;
		});
	},
	
	open : function(ftrIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.ftrIdn = ftrIdn;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wrpp/addWrppFlawMendngDtlsPage.do";
		var windowOptions = {
			width : 450,
			height : 244,
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
	
	add : function() {
		var that = this;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wrpp/" + that.ftrIdn + "/addWrppFlawMendngDtls.do";
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
