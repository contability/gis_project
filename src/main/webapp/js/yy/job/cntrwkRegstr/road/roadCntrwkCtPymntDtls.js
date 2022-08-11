/**
 * 도로 공사비 지급 내역 객체
 */

/// 도로 공사비 지급 내역 조회
roadCntrwkRegstrObj.roadCntrwkCtPymntDtls = {

	TITLE : "공사비 지급 내역 상세정보",
	
	CLASS_NAME : "RoadCntrwkCtPymntDtlsDetail",

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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/" + that.ftrIdn + "/" + that.shtIdn + "/selectOneRoadCntrwkCtPymntDtlsPage.do";
		var windowOptions = {
			width : 450,
			height : 119,
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

/// 도로 공사비 지급 내역 수정
roadCntrwkRegstrObj.roadCntrwkCtPymntDtls.modify = {

	TITLE : "공사비 지급 내역 수정",
	
	CLASS_NAME : "RoadCntrwkCtPymntDtlsModify",

	selector : null,
	
	ftrIdn : null,
	
	shtIdn : null,
	
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
					// 가나다
					roadCntrwkRegstrObj.modify.close();

					var ftrIdn = $("#ftrIdn").val();
					roadCntrwkRegstrObj.modify.open(ftrIdn);
					
					that.close();
					
					alert("공사비 지급 내역이 수정되었습니다.");
				}
				else {
					alert("공사비 지급 내역 수정에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function() {
		var that = this;
		
		// easyUi datebox init
		$("#modifyRoadCntrwkCtPymntDtls .easyui-datebox", that.selector).datebox();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장
		$(".btn_save_roadCntrwkCtPymntDtlsModify", that.selector).on("click", function() {
			$.messager.confirm(that.TITLE, "저장하시겠습니까?", function(isTrue) {
				if(isTrue) {
					that.modify();
				}
			});
			return false;
		});

		// 삭제
		$(".btn_save_roadCntrwkCtPymntDtlsRemove", that.selector).on("click", function() {
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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/" + that.ftrIdn + "/" + that.shtIdn + "/modifyRoadCntrwkCtPymntDtlsPage.do";
		var windowOptions = {
			width : 450,
			height : 185,
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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/" + that.ftrIdn + "/" + that.shtIdn + "/modifyRoadCntrwkCtPymntDtls.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	remove : function() {
		var that = this;
		
		that.shtIdn = $("#shtIdn", that.selector).val();
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/" + that.shtIdn + "/removeRoadCntrwkCtPymntDtls.do";
		
		$.post(url).done(function(result) {
			if(result) {
				roadCntrwkRegstrObj.modify.close();

				var ftrIdn = $("#ftrIdn").val();
				roadCntrwkRegstrObj.modify.open(ftrIdn);
				
				that.close();
				
				alert("공사비 지급 내역을 삭제하였습니다.");
			}
			else {
				alert("공사비 지급 내역 삭제에 실패했습니다.");
			}
		}).fail(function() {
			alert("공사비 지급 내역 삭제에 실패했습니다.");
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

/// 도로 공사비 지급 내역 등록
roadCntrwkRegstrObj.roadCntrwkCtPymntDtls.add = {
		
	TITLE : "공사비 지급 내역 등록",
	
	CLASS_NAME : "RoadCntrwkCtPymntDtlsAdd",

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
					roadCntrwkRegstrObj.modify.close();
					
					roadCntrwkRegstrObj.modify.open(that.ftrIdn);
					
					that.close();
					
					alert("공사비 지급 내역이 등록되었습니다.");
				}
				else {
					alert("공사비 지급 내역 등록에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function() {
		var that = this;
		
		// easyUi datebox init
		$("#addRoadCntrwkCtPymntDtls .easyui-datebox", that.selector).datebox();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장
		$(".btn_save_roadCntrwkCtPymntDtlsAdd", that.selector).on("click", function() {
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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/addRoadCntrwkCtPymntDtlsPage.do";
		var windowOptions = {
			width : 450,
			height : 185,
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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/" + that.ftrIdn + "/addRoadCntrwkCtPymntDtls.do";
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
