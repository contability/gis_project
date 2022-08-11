/**
 * 하천측점
 */
riverSidePointObj = {
	
	TITLE : "하천측점 검색",
	
	CLASS_NAME : "RiverSidePointSearch",
	
	selector : null,
	
	isCreated : false,
	
	bindEvents : function() {
		var that = this;
		
		// 초기화
		$(".a_reset_riverSidePointSearch", that.selector).on("click", function() {
			$("select[name=rivNam]", that.selector).val("");
			$("input[name=filNam]", that.selector).val("");
			return false;
		});
		
		// 검색
		$(".a_search_riverSidePointSearch", that.selector).on("click", function() {
			that.search();
			return false;
		});
	},
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/riverSidePoint/searchRiverSidePointPage.do";
		var windowOptions = {
			width : 565,
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
		
		var url = CONTEXT_PATH + "/riverSidePoint/listRiverSidePoint.do";
		var params = {};
		
		params.rivNam = $("select[name=rivNam]", that.selector).val();
		params.filNam = $("input[name=filNam]", that.selector).val();
		
		$.get(url, params).done(function(response) {
			var data = [];
			var ids = [];
			
			if(response.result.length < 1){
				alert("조건에 맞는 결과가 없습니다.");
				return false;
			}
			else {
				data.push({
					dataAlias : "하천측점",
					dataId : "RIVR_STAT_PS",
					ids : ids
				});
				
				for(var j in response.result) {
					ids.push(response.result[j].ftrIdn);
				}
				
				resultObj.addRows(data);
			}
		}).fail(function() {
			alert("하천측점 리스트를 가져오는데 실패하였습니다.");
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
 * 하천측점 상세정보
 */
riverSidePointObj.view = {
	TITLE : "하천측점 상세정보",
	
	CLASS_NAME : "RiverSidePointDetail",

	selector : null,
	
	staIdn : null,
	
	isCreated : false,
	
	initUi : function() {
		
	},
	
	bindEvents : function() {
		var that = this;
		
		// 횡단면도 내려받기
		$(".btn_down_riverSidePointView", that.selector).on("click", function() {
			that.imageDown(ftrCde, staIdn);
			return false;
		});
		
		// 편집
		$(".btn_modify_riverSidePointView", that.selector).on("click", function() {
			var staIdn = $("#staIdn", that.selector).val();
			
			riverSidePointObj.modify.open(staIdn);
			that.close();
			
			return false;
		});
	},
	
	open : function(fids) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.staIdn = fids;
		
		var url = CONTEXT_PATH + "/riverSidePoint/" + that.staIdn + "/selectOneRiverSidePointPage.do";
		var windowOptions = {
			width : 440,
			height : 530,
			top : 150,
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
	
	imageDown : function() {
		var that = this;
		
		var imageNo = $("#imageNo", that.selector).val();

		var url = CONTEXT_PATH + "/cmmn/image/" + imageNo + "/download.do";
		
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.staIdn = null;
		that.selector = null;
		that.isCreated = false;
	}
};

/**
 * 하천측점 수정
 */
riverSidePointObj.modify = {
		
	TITLE : "하천측점 수정",
	
	CLASS_NAME : "RiverSidePointModify",

	selector : null,
	
	staIdn : null,
	
	isCreated : false,
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			beforeSubmit : function(arr, form) {
				for(var i=0, len=arr.length; i < len; i++) {
					var obj = arr[i];
					var name = obj.name;

					// 필수 입력 체크
					if(name == "imaExp") {
						if(!obj.value) {
							alert("설명을 입력하여 주십시오.");
							form.find("input[name=" + name + "]").focus();
							return false;
						}
					}
				}
			},
			success : function(result) {
				if(result) {
					riverSidePointObj.view.open(that.staIdn);
					that.close();
					alert("하천측점이 수정되었습니다.");
				}
				else {
					alert("하천측점 수정에 실패하였습니다.");
				}
			}
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장
		$("#btn_save_riverSidePointModify", that.selector).on("click", function() {
			riverSidePointObj.modify.save();
			return false;
		});
	},
	
	open : function(staIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.staIdn = staIdn;
		
		var url = CONTEXT_PATH + "/riverSidePoint/" + that.staIdn + "/modifyRiverSidePointPage.do";
		var windowOptions = {
			width : 370,
			height : 237,
			top : 150,
			left : 550,
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
		
		that.staIdn = $("#staIdn", that.selector).val();
		
		var url = CONTEXT_PATH + "/riverSidePoint/" + that.staIdn + "/modifyRiverSidePoint.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.staIdn = null;
		that.selector = null;
		that.isCreated = false;
	}
};