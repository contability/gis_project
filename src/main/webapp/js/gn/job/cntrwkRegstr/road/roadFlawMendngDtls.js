/**
 * 도로 하자 보수 내역 객체
 */

/// 도로 하자 보수 내역 조회
roadCntrwkRegstrObj.roadFlawMendngDtls = {
		
	TITLE : "하자 보수 내역 상세정보",
	
	CLASS_NAME : "RoadFlawMendngDtlsDetail",

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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/" + that.ftrIdn + "/" + that.shtIdn + "/selectOneRoadFlawMendngDtlsPage.do";
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

/// 도로 하자 보수 내역 수정
roadCntrwkRegstrObj.roadFlawMendngDtls.modify = {
		
	TITLE : "하자 보수 내역 수정",
	
	CLASS_NAME : "RoadFlawMendngDtlsModify",

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
					roadCntrwkRegstrObj.modify.close();

					var ftrIdn = $("#ftrIdn").val();
					roadCntrwkRegstrObj.modify.open(ftrIdn);
					
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
		$("#modifyRoadFlawMendngDtls .easyui-datebox", that.selector).datebox();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장
		$(".btn_save_roadFlawMendngDtlsModify", that.selector).on("click", function() {
			$.messager.confirm(that.TITLE, "저장하시겠습니까?", function(isTrue) {
				if(isTrue) {
					that.modify();
				}
			});
			return false;
		});

		// 삭제
		$(".btn_save_roadFlawMendngDtlsRemove", that.selector).on("click", function() {
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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/" + that.ftrIdn + "/" + that.shtIdn + "/modifyRoadFlawMendngDtlsPage.do";
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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/" + that.ftrIdn + "/" + that.shtIdn + "/modifyRoadFlawMendngDtls.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	remove : function() {
		var that = this;
		
		that.shtIdn = $("#shtIdn", that.selector).val();
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/" + that.shtIdn + "/removeRoadFlawMendngDtls.do";
		
		$.post(url).done(function(result) {
			if(result) {
				roadCntrwkRegstrObj.modify.close();

				var ftrIdn = $("#ftrIdn").val();
				roadCntrwkRegstrObj.modify.open(ftrIdn);
				
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

/// 도로 하자 보수 내역 등록
roadCntrwkRegstrObj.roadFlawMendngDtls.add = {
		
	TITLE : "하자 보수 내역 등록",
	
	CLASS_NAME : "RoadFlawMendngDtlsAdd",

	selector : null,
	
	ftrIdn : null,
	
	isCreated : false,
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					roadCntrwkRegstrObj.modify.close();
					
					roadCntrwkRegstrObj.modify.open(that.ftrIdn);
					
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
		$("#addRoadFlawMendngDtlsAdd .easyui-datebox", that.selector).datebox();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장
		$(".btn_save_roadFlawMendngDtlsAdd", that.selector).on("click", function() {
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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/addRoadFlawMendngDtlsPage.do";
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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/" + that.ftrIdn + "/addRoadFlawMendngDtls.do";
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
