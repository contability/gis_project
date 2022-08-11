/**
 * 망실신고 관리 객체
 */
lossSttemntObj = {
		
	TITLE : "망실신고 검색",
	
	CLASS_NAME : "LossSttemntSearch",
	
	selector : null,
	
	isCreated : false,
	
	bindEvents : function() {
		var that = this;
		
		// 초기화
		$(".a_reset_lossSttemntSearch", that.selector).on("click", function() {
			$("input[name=bseNam]", that.selector).val("");

			return false;
		});
		
		// 검색
		$(".a_search_lossSttemntSearch", that.selector).on("click", function() {
			that.search();
			
			return false;
		});
	},
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/lossSttemnt/searchLossSttemntPage.do";
		var windowOptions = {
			width : 300,
			height : 145,
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
		
		var url = CONTEXT_PATH + "/lossSttemnt/listLossSttemnt.do";
		var params = {};
		
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
					dataAlias : "망실신고",
					dataId : "ETT_LSCP_DT",
					ids : ids
				});
				
				for(var j in response.result) {
					ids.push(response.result[j].shtIdn);
				}
				
				resultObj.addRows(data);
			}
		}).fail(function() {
			alert("망실신고 리스트를 가져오는데 실패하였습니다.");
		});
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
};

lossSttemntObj.add = {
		
	TITLE : "망실신고 등록",
	
	CLASS_NAME : "LossSttemntAdd",

	selector : null,
	
	ftrIdn : null,
	
	bseNam : null,
	
	isCreated : false,
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					that.close();
					alert("망실신고가 등록되었습니다.");
				}
				else {
					alert("망실신고 등록에 실패하였습니다.");
				}
			}
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장
		$("#a_save_lossSttemntAdd", that.selector).on("click", function() {
			that.save();
		});
	},
	
	open : function(ftrIdn, bseNam) {
		var that = this;
		
		that.ftrIdn = ftrIdn;
		that.bseNam = bseNam;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/lossSttemnt/addLossSttemntPage.do";
		var windowOptions = {
			width : 420,
			height : 246,
			top : 150,
			left : 500,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.init();
				that.bindEvents();
				
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	save : function() {
		var that = this;

		var url = CONTEXT_PATH + "/lossSttemnt/" + that.ftrIdn + "/" + that.bseNam + "/addLossSttemnt.do";
		
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.ftrIdn = null;
		that.bseNam = null;
		that.isCreated = false;
	}
};

lossSttemntObj.view = {
		
	TITLE : "망실신고 상세정보",
	
	CLASS_NAME : "LossSttemntDetail",

	selector : null,
	
	isCreated : false,
	
	shtIdn : null,
	
	open : function(fids) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.shtIdn = fids;
		
		var url = CONTEXT_PATH + "/lossSttemnt/" + that.shtIdn + "/selectOneLossSttemntPage.do";
		var windowOptions = {
			width : 420,
			height : 154,
			top : 150,
			left : 500,
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
		that.selector = null;
		that.shtIdn = null;
		that.isCreated = false;
	}
};