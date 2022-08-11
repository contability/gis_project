/**
 * 급수 공사대장 객체
 */
// 급수 공사대장 검색
wspCntrwkRegstrObj = {
		
	TITLE : "급수공사대장 검색",
	
	CLASS_NAME : "WspCntrwkRegstrSearch",
	
	selector : null,
	
	isCreated : false,
	
	initUi : function() {
		var that = this;
		
		// easyUi datebox init
		$("#listWspCntrwkRegstr .easyui-datebox", that.selector).datebox();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 초기화
		$(".a_reset_wspCntrwkRegstr", that.selector).on("click", function() {
			$("input[name=begYmdMin]", that.selector).val("");
			$("input[name=begYmdMax]", that.selector).val("");
			$("input[name=oprNam]", that.selector).val("");
			$("input[name=fnsYmdMin]", that.selector).val("");
			$("input[name=fnsYmdMax]", that.selector).val("");
			$("input[name=totAmtMin]", that.selector).val("");
			$("input[name=totAmtMax]", that.selector).val("");
			
			return false;
		});
		
		// 검색
		$(".a_search_wspCntrwkRegstr", that.selector).on("click", function() {
			that.search();
			
			return false;
		});
	},
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wsp/searchWspCntrwkRegstrPage.do";
		var windowOptions = {
			width : 655,
			height : 181,
			top : 120,
			left : 330,
			isClose : function() {
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
	
	search : function() {
		var that = this;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wsp/listWspCntrwkRegstr.do";
		var params = {};
		
		params.begYmdMin = $("input[name=begYmdMin]", that.selector).val();
		params.begYmdMax = $("input[name=begYmdMax]", that.selector).val();
		params.oprNam = $("input[name=oprNam]", that.selector).val();
		params.fnsYmdMin = $("input[name=fnsYmdMin]", that.selector).val();
		params.fnsYmdMax = $("input[name=fnsYmdMax]", that.selector).val();
		params.totAmtMin = $("input[name=totAmtMin]", that.selector).val();
		params.totAmtMax = $("input[name=totAmtMax]", that.selector).val();
		
		$.get(url, params).done(function(response) {
			var data = [];
			var ids = [];
			
			if(response.result.length < 1){
				alert("조건에 맞는 결과가 없습니다.");
				return false;
			}
			else {
				data.push({
					dataAlias : "공사대장",
					dataId : "WTT_SPLY_MA",
					ids : ids
				});
				
				for(var j in response.result) {
					ids.push(response.result[j].ftrIdn);
				}
				
				resultObj.addRows(data);
			}
		}).fail(function() {
			alert("공사대장 리스트를 가져오는데 실패하였습니다.");
		});
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
};

/// 급수 공사대장 조회
wspCntrwkRegstrObj.view = {
		
	TITLE : "급수공사대장 상세정보",
	
	CLASS_NAME : "WspCntrwkRegstrDetail",

	selector : null,
	
	ftrIdn : null,
	
	isCreated : false,
	
	bindEvents : function() {
		var that = this;

		// 편집
		$(".btn_modify_wspCntrwkRegstrDetail", that.selector).on("click", function() {
			var ftrIdn = $("#ftrIdn", that.selector).val();
			
			wspCntrwkRegstrObj.modify.open(ftrIdn);
			that.close();
			
			return false;
		});
	},
	
	open : function(fids) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.ftrIdn = fids;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wsp/" + that.ftrIdn + "/selectOneWspCntrwkRegstrPage.do";
		var windowOptions = {
			width : 680,
			height : 424,
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
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.ftrIdn = null;
		that.selector = null;
		that.isCreated = false;
	}
};

/// 급수 공사대장 등록
wspCntrwkRegstrObj.add = {
		
	TITLE : "급수공사대장 등록",
	
	CLASS_NAME : "WspCntrwkRegstrAdd",

	selector : null,
	
	isCreated : false,
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					that.close();
					alert("급수공사대장이 등록되었습니다.");
				}
				else {
					alert("급수공사대장 등록에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function() {
		var that = this;
		
		// easyUi datebox init
		$("#wspCntrwkRegstrAdd .easyui-datebox", that.selector).datebox();
		
		// number olny
		$(".numOnly").keyup(function() {
			$(this).val($(this).val().replace(/[^0-9]/g, ""));
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장
		$(".btn_save_wspCntrwkRegstrAdd", that.selector).on("click", function() {
			that.save();
			
			return false;
		});
	},
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wsp/addWspCntrwkRegstrPage.do";
		var windowOptions = {
			width : 600,
			height : 423,
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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wsp/addWspCntrwkRegstr.do";
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

/// 급수 공사대장 수정
wspCntrwkRegstrObj.modify = {
		
	TITLE : "급수공사대장 수정",
	
	CLASS_NAME : "WspCntrwkRegstrModify",

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
					wspCntrwkRegstrObj.view.open(that.ftrIdn);
					that.close();
					alert("공사대장이 수정되었습니다.");
				}
				else {
					alert("공사대장 수정에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function() {
		var that = this;
		
		// easyUi datebox init
		$("#wspCntrwkRegstrModify .easyui-datebox", that.selector).datebox();
	},
	
	bindEvents : function() {
		var that = this;
	
		// 저장
		$(".btn_save_wspCntrwkRegstrModify", that.selector).on("click", function() {
			that.modify();
			return false;
		});
	},
	
	open : function(ftrIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.ftrIdn = ftrIdn;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wsp/" + that.ftrIdn + "/modifyWspCntrwkRegstrPage.do";
		var windowOptions = {
			width : 600,
			height : 423,
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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wsp/" + that.ftrIdn + "/modifyWspCntrwkRegstr.do";
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