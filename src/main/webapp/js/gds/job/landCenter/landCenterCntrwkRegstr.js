landCenterCntrwkRegstrObj = {
		
	TITLE : "토지중심공사대장 검색",
	
	CLASS_NAME : "LandCenterCntrwkRegstrSearch",
	
	selector : null,
	
	isCreated : false,
	
	initUi : function() {
		var that = this;
	
		// easyUi설정
		$("#listLandCenterCntrwkRegstr .easyui-datebox", that.selector).datebox();
		$("#listLandCenterCntrwkRegstr .easyui-textbox", that.selector).textbox();
		$("#listLandCenterCntrwkRegstr .easyui-combobox", that.selector).combobox();
		$("#listLandCenterCntrwkRegstr .easyui-numberbox", that.selector).numberbox();
	},
	
	bindEvents : function() {
		var that = this; 
		
		///초기화
		$(".a_reset_landCenterCntrwkRegstr").on("click", function(){
			$("input[name=cntIdn]", that.selector).val("");
			$("input[name=cntNam]", that.selector).val("");
			$("input[name=cttYmdMin]", that.selector).val("");
			$("input[name=cttYmdMax]", that.selector).val("");
			$("input[name=begYmdMin]", that.selector).val("");
			$("input[name=begYmdMax]", that.selector).val("");
			$("input[name=rfnYmdMin]", that.selector).val("");
			$("input[name=rfnYmdMax]", that.selector).val("");
			$("input[name=totAmtMin]", that.selector).val("");
			$("input[name=totAmtMax]", that.selector).val("");
			
			return false;
		});
		
		//검색
		$(".a_search_landCenterCntrwkRegstr").on("click", function(){
			that.search();
		
			return false;
		});
	},
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/landCenter/searchLandCenterCntrwkRegstrPage.do";
		var windowOptions = {
			width : 655,
			height : 220,
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
		
		var url = CONTEXT_PATH + "/landCenter/listLandCenterCntrwkRegstr.do";
		var params = {};
		
		//공사번호
		params.cntIdn = $("input[name=cntIdn]", that.selector).val();
		//공사명
		params.cntNam = $("input[name=cntNam]", that.selector).val();
		//계약일
		params.cttYmdMin = $("input[name=cttYmdMin]", that.selector).val();
		params.cttYmdMax = $("input[name=cttYmdMax]", that.selector).val();
		//착공일
		params.begYmdMin = $("input[name=begYmdMin]", that.selector).val();
		params.begYmdMax = $("input[name=begYmdMax]", that.selector).val();
		//준공일
		params.rfnYmdMin = $("input[name=rfnYmdMin]", that.selector).val();
		params.rfnYmdMax = $("input[name=rfnYmdMax]", that.selector).val();
		//계약금액
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
					dataId : "LD_CONS_MA",
					ids : ids
				});
				
				for(var j in response.result) {
					ids.push(response.result[j].cntIdn);
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

/// 토지중심공사대장 조회
landCenterCntrwkRegstrObj.view = {
		
	TITLE : "토지중심공사대장 상세정보",
	
	CALSS_NAME : "landCenterCntrwkRegstrDetail",
	
	selector : null,
	
	cntIdn : null,
	
	isCreated : false,
	
	initUi : function(){
		var that = this;
		
		// tab 초기화
		$(".tab-list-1 li a", that.selector).on("click", function() {
			$(".tab-list-1 li, .tab-panel-1", that.selecor).removeClass("active");
			$(this, that.selector).parent().addClass("active");
			$(".tab-panel-1", that.selector).eq($(this, that.selector).parent().index()).addClass("active");
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 수정
		$(".btn_modify_landCenterCntrwkRegstrDetail").on("click", function(){
			var cntIdn = $("#cntIdn", that.selector).val();
			
			landCenterCntrwkRegstrObj.modify.open(cntIdn);
			that.close();
			
			return false;
		});
		
		// 삭제
		$(".btn_delete_landCenterCntrwkRegstrDetail").on("click", function(){
			$.messager.confirm(that.TITLE, "삭제하시겠습니까?", function(isTrue) {
				if(isTrue) {
					
					that.remove();
				}
			});
			return false;
		});
		
		// 닫기
		$(".btn_close_landCenterCntrwkRegstrDetail").on("click", function(){
			that.close();
		
			return false;
		});
	},
		
	remove : function(){
		var that = this;
		
		that.cntIdn = $("#cntIdn", that.selector).val();
		
		var url = CONTEXT_PATH + "/landCenter/" + that.cntIdn + "/removeLandCenterCntrwkRegstr.do";
		
		$.post(url).done(function(result) {
			if(result) {
				
				that.close();
				alert("토지중심공사대장을 삭제하였습니다.");
			}
			else {
				alert("토지중심공사대장 삭제에 실패했습니다.");
			}
		}).fail(function() {
			alert("토지중심공사대장 삭제에 실패했습니다.");
		});
	},
	
	open : function(fids) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.cntIdn = fids;
		
		var url = CONTEXT_PATH + "/landCenter/" + that.cntIdn + "/selectOneLandCenterCntrwkRegstrPage.do";
		var windowOptions = {
			width : 680,
			height : 440,
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
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.cntIdn = null;
		that.selector = null;
		that.isCreated = false;
	}
		
};

/// 토지중심공사대장 수정
landCenterCntrwkRegstrObj.modify = {
	
	TITLE : "토지중심공사대장 수정",
	
	CLASS_NAME : "LandCenterCntrwkRegstrModify",
	
	selector : null,
	
	cntIdn : null,
	
	isCreated : false,
		
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					landCenterCntrwkRegstrObj.view.open(that.cntIdn);
					that.close();
					alert("토지중심공사대장이 수정되었습니다.");
				}
				else {
					alert("토지중심공사대장 수정에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function(){
		var that = this;
		
		// easyUi설정
		$("#landCenterCntrwkRegstrModify .easyui-datebox", that.selector).datebox();
		$("#landCenterCntrwkRegstrModify .easyui-textbox", that.selector).textbox();
		$("#landCenterCntrwkRegstrModify .easyui-combobox", that.selector).combobox();
		$("#landCenterCntrwkRegstrModify .easyui-numberbox", that.selector).numberbox();
		
		// tab 초기화
		$(".tab-list-1 li a", that.selector).on("click", function() {
			$(".tab-list-1 li, .tab-panel-1", that.selecor).removeClass("active");
			$(this, that.selector).parent().addClass("active");
			$(".tab-panel-1", that.selector).eq($(this, that.selector).parent().index()).addClass("active");
		});
	},
	
	bindEvents : function(){
		var that = this;
		
		// 저장
		$(".btn_save_landCenterCntrwkRegstrModify", that.selector).on("click", function() {
			that.modify();
			return false;
		});
		
		// 취소
		$(".btn_close_landCenterCntrwkRegstrModify", that.selector).on("click", function() {
			that.close();
		});
	},
	
	open : function(cntIdn) {
		var that = this;
		
		if(that.selector){
			that. close();
		}
		
		that.cntIdn = cntIdn;
		
		var url = CONTEXT_PATH + "/landCenter/" + that.cntIdn + "/modifyLandCenterCntrwkRegstrPage.do";
		var windowOptions = {
			width : 680,
			height : 440,
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
	
	modify : function(){
		var that = this;
		
		that.cntIdn = $("#cntIdn", that.selector).val();
		
		var url = CONTEXT_PATH + "/landCenter/" + that.cntIdn + "/modifyLandCenterCntrwkRegstr.do";
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

/// 토지중심공사대장 등록
landCenterCntrwkRegstrObj.add = {
		
	TITLE : "토지중심공사대장 등록",
	
	CLASS_NAME : "LandCenterCntrwkRegstrAdd",
	
	selector : null,
	
	isCreated : false,
		
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					that.close();
					alert("공사대장이 등록되었습니다.");
				}
				else {
					alert("공사대장 등록에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function(){
		var that = this;
		
		// easyUi설정
		$("#landCenterCntrwkRegstrAdd .easyui-datebox", that.selector).datebox();
		$("#landCenterCntrwkRegstrAdd .easyui-textbox", that.selector).textbox();
		$("#landCenterCntrwkRegstrAdd .easyui-combobox", that.selector).combobox();
		$("#landCenterCntrwkRegstrAdd .easyui-numberbox", that.selector).numberbox();
		
		// tab 초기화
		$(".tab-list-1 li a", that.selector).on("click", function() {
			$(".tab-list-1 li, .tab-panel-1", that.selecor).removeClass("active");
			$(this, that.selector).parent().addClass("active");
			$(".tab-panel-1", that.selector).eq($(this, that.selector).parent().index()).addClass("active");
		});
	},
	
	bindEvents : function() { 
		var that = this;
		
		// 저장
		$(".btn_save_landCenterCntrwkRegstrAdd", that.selector).on("click", function() {
			
			that.save();
			return false;
		});
		
		// 취소 
		$(".btn_close_landCenterCntrwkRegstrAdd", that.selector).on("click", function() {
			that.close();
		});
	},
	
	open : function(cntIdn) {
	var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.cntIdn = cntIdn;
		
		var url = CONTEXT_PATH + "/landCenter/addLandCenterCntrwkRegstrPage.do";
		var windowOptions = {
			width : 680,
			height : 390,
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
		
		var url = CONTEXT_PATH + "/landCenter/addLandCenterCntrwkRegstr.do";
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
