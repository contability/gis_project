/**
 * 지하수관정 객체
 * @type {Object}
 */
var ugrwtrObj = {

	TITLE : "지하수관정 검색",
	
	CLASS_NAME : "UndergroundWater",
	
	selector : null,

	bindEvents : function() {
		var that = this;
		
		// 초기화
		$("div", that.selector).on("click", ".btn_init4", function() {
			$("select[name=disCde]", that.selector).val("");
			$("select[name=permNtFo]", that.selector).val("");
			$("select[name=dvopLocC]", that.selector).val("");
			$("input[name=permNtNo]", that.selector).val("");
			$("input[name=rgtMbdNm]", that.selector).val("");
			$("select[name=uwaterSrv]", that.selector).val("");
		});
		
		// 검색
		$("div", that.selector).on("click", ".btn_search4", function() {
			that.search();
		});

		// 닫기
		$("div", that.selector).on("click", ".btn_close", function() {
			that.close();
		});
	},

	// 검색창 열기
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/ugrwtr/page.do";
		var windowOptions = {
			width : 600,
			height : 180,
			top : 120,
			left : 330,
			isClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, that.params, windowOptions, function() {
			that.bindEvents();
		});
		
		that.selector = "#" + id;
	},
	
	search : function() {
		var that = this;
		
		resultObj.close();
		
		var url = CONTEXT_PATH + "/ugrwtr/list.do";
		var params = {};
		
		// 사용구분
		params.disCde = $("select[name=disCde]", that.selector).val();
		// 시설구분
		params.permNtFo = $("select[name=permNtFo]", that.selector).val();
		// 법정동
		params.dvopLocC = $("select[name=dvopLocC]", that.selector).val();
		// 허가신고번호
		params.permNtNo = $("input[name=permNtNo]", that.selector).val();
		// 성명(상호)
		params.rgtMbdNm = $("input[name=rgtMbdNm]", that.selector).val();
		// 용도구분
		params.uwaterSrv = $("select[name=uwaterSrv]", that.selector).val();
		
		$.get(url, params).done(function(response) {
			var data = [];
			var ids = [];
			
			if(response.result.length < 1){
				alert("조건에 맞는 결과가 없습니다.");
				return false;
			}
			else {
				data.push({
					dataAlias : "지하수관정",
					dataId : "BML_WELL_PS",
					ids : ids
				});
				
				for(var j in response.result) {
					ids.push(response.result[j].objectid);
				}
				
				resultObj.addRows(data);
			}
		}).fail(function() {
			alert("지하수관정 리스트를 가져오는데 실패하였습니다.");
		});
	},

	// 닫기
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
	}
};

/**
 * 지하수관정 상세조회 객체
 * @type {Object}
 */
ugrwtrObj.view = {
		
	TITLE : "지하수관정 상세조회",
	
	CLASS_NAME : "UndergroundWaterDetail",

	selector : null,
	
	objectid : null,
	
	ftrIdn : null,
	
	bindEvents : function() {
		var that = this;
		
		// 점검정비이력
		$(".a_open_chckImprmnHist_viewUgrwtr", that.selector).on("click", function() {
			var permNtNo = "";
			permNtNo = $(".permNtNo").val();
			
			ugrwtrObj.chckImprmnHist.open(permNtNo);
		});
		
		// 출력
		$(".a_outpt_viewUgrwtr", that.selector).on("click", function() {
			var ftrCde = "";
			var objectid = "";
			
			ftrCde = $(".ftrCde").val();
			objectid = $(".objectid").val(); 

			ugrwtrObj.view.outpt(ftrCde, objectid);
		});
		
		// 편집
		$(".a_modify_viewUgrwtr", that.selector).on("click", function() {
			var ftrIdn = "";
			ftrIdn = $(".ftrIdn").val();
			
			ugrwtrObj.modify.open(ftrIdn);
			
			that.close();
		});
		
		// 상세 레이아웃 토글 기능
		$(".UWtogClick", that.selector).on("click", function() {
			var that = this;
			var togId = $(that).attr("togId");
			
			if($("#"+togId, that.selector).hasClass("accordion-collapse") ) {
				$("." + togId, that.selector).hide();
				$("#"+togId, that.selector).removeClass("accordion-collapse");
				$("#"+togId, that.selector).addClass("accordion-expand");
			} else {
				$("." + togId, that.selector).show();
				$("#"+togId, that.selector).removeClass("accordion-expand");
				$("#"+togId, that.selector).addClass("accordion-collapse");
			}
		});
		
		// 이미지 상세조회
		$(".bmlWellImage", that.selector).on("click", function() {
			var that = this;
			
			var TITLE = "지하수관정 사진조회";
			var imageNo = $(that, that.selector).attr("data-image-no");
			
			imageObj.view.open(TITLE, imageNo);
		});
		
		// window close
		$(".a_close_viewUgrwtr", that.selector).on("click", function() {
			that.close();
		});
	},
	
	open : function(fids) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		ftrIdn = fids;
		
		var url = CONTEXT_PATH + "/ugrwtr/" + ftrIdn + "/selectOne.do";
		var windowOptions = {
			width : 630,
			height : 600,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.bindEvents();
		});
		
		that.selector = "#" + id;
	},
	
	outpt : function(ftrCde, objectid) {
		var url = CONTEXT_PATH + "/clipreport/ugrwtr.do?ftrCde=" + ftrCde + "&ftrIdn=" + ftrIdn;
		
		popupUtils.open(url, "지하수관정", { width : 900 , height : 800, left : 300, top:150 });
	},
	
	close : function() {
		var that = this;
		
		ftrIdn = null;
		objectid = null;
		windowObj.removeWindow(that.selector);
	}
};

/**
 * 지하수관정 편집 객체
 * @type {Object}
 */
ugrwtrObj.modify = {
	
	TITLE : "지하수관정 정보수정",
	
	CLASS_NAME : "UndergroundWaterModify",

	selector : null,
	
	objectid : null,
	
	ftrIdn : null,
	
	init : function() {
		var that = this;
		var fid = that.ftrIdn;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					that.close();
					
					ugrwtrObj.view.open(fid);
				}
				else {
					alert("지하수관정 수정에 실패하였습니다.");
				}
			}
		});
		
	},
	
	bindEvents : function() {
		var that = this;
		
		// save
		$(".a_save_modifyUgrwtr", that.selector).on("click", function() {
			that.modify();
		});
		
		// window close
		$(".a_close_modifyUgrwtr", that.selector).on("click", function() {
			that.close();
		});
	
	},
	
	open : function(ftrIdn) {
		var that = this;
		that.ftrIdn = ftrIdn;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/ugrwtr/" + ftrIdn + "/edit.do";
		var windowOptions = {
			width : 630,
			height : 600,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.init();
			that.bindEvents();
		});
		
		that.selector = "#" + id;
	},
	
	modify : function() {
		var that = this;
		
		var ftrIdn = $("#ftrIdn").val();
		var url = CONTEXT_PATH + "/ugrwtr/" + ftrIdn + "/modify.do";
		
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;

		objectid = null;
		ftrIdn = null;
		
		windowObj.removeWindow(that.selector);
	}
};

ugrwtrObj.chckImprmnHist = {
	
	TITLE : "지하수관정 점검정비이력",
	
	CLASS_NAME : "UndergroundWaterChckImprmnHist",

	selector : null,
	
	objectid : null,
	
	ftrIdn : null,
	
	bindEvents : function() {
		var that = this;
		
		// 편집창 open
		$("#undrWtrTubChkRecdData tbody tr", that.selector).on("click", function() {
			var element = this;

			var chkIdn = "";
			chkIdn = $(element).attr("chkIdn");
			
			ugrwtrObj.chckImprmnHist.modify.open(chkIdn);
		});
		
		// 등록
		$(".a_add_listUgrwtrChckHist", that.selector).on("click", function() {
			var permNtNo = $(".permNtNo").val();
			
			ugrwtrObj.chckImprmnHist.add.open(permNtNo);
		});
		
		// window close
		$(".a_close_listUgrwtrChckHist", that.selector).on("click", function() {
			that.close();
		});
	},
	
	open : function(permNtNo) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/ugrwtr/chckImprmnHist/" + permNtNo + "/page.do";
		var windowOptions = {
			width : 600,
			height : 358,
			top : 120,
			left : 330,
			isClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, that.params, windowOptions, function() {
			that.bindEvents();
		});
		
		that.selector = "#" + id;
	},
	
	close : function() {
		var that = this;
		
		objectid = null;
		ftrIdn = null;
		windowObj.removeWindow(that.selector);
	}
};

ugrwtrObj.chckImprmnHist.modify = {
		
	TITLE : "지하수관정 점검이력 수정",
	
	CLASS_NAME : "UndergroundWaterChckImprmnHistModify",

	selector : null,
	
	chkIdn : null,
	
	permNtNo : null,
	
	init : function() {
		var that = this;
		var permNtNo = $(".permNtNo").val();
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					that.close();
					
					ugrwtrObj.chckImprmnHist.open(permNtNo);
				}
				else {
					alert("지하수관정 점검관리이력 수정에 실패하였습니다.");
				}
			}
		});
		
	},
	
	bindEvents : function() {
		var that = this;
		
		// 달력
		$("#undrWtrTubchkRecdEditform .easyui-datebox", that.selector).datebox();
		
		// 저장
		$(".a_save_modifyUgrwtrHist", that.selector).on("click", function() {
			var chkIdn = $(".chkIdn").val();
			
			that.modify(chkIdn);
		});
		
		// 삭제
		$(".a_delete_modifyUgrwtrHist", that.selector).on("click", function() {
			var chkIdn = $(".chkIdn").val();
			var permNtNo = $(".permNtNo").val();
			
			that.remove(chkIdn, permNtNo);
		});
		
		// window close
		$(".a_close_modifyUgrwtrHist", that.selector).on("click", function() {
			that.close();
		});
	},
	
	open : function(chkIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/ugrwtr/chckImprmnHist/" + chkIdn + "/selectOne.do";
		var windowOptions = {
			width : 400,
			height : 244,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.init();
			that.bindEvents();
		});
		
		that.selector = "#" + id;
	},
	
	modify : function(chkIdn) {
		var that = this;

		var url = CONTEXT_PATH + "/ugrwtr/chckImprmnHist/" + chkIdn + "/modify.do";
		
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	remove : function(chkIdn, permNtNo) {
		var that = this;
		var url = CONTEXT_PATH + "/ugrwtr/chckImprmnHist/" + chkIdn + "/remove.do";
		
		that.permNtNo = permNtNo;
		
		$.post(url).done(function(result) {
			if(result) {
				that.close();
				
				ugrwtrObj.chckImprmnHist.open(permNtNo);
			}
			else {
				alert("지하수관정 점검이력 삭제에 실패했습니다.");
			}
		}).fail(function() {
			alert("지하수관정 점검이력 삭제에 실패했습니다.");
		});
	},
	
	close : function() {
		var that = this;
		
		chkIdn = null;
		permNtNo = null;
		
		windowObj.removeWindow(that.selector);
	}		
};

// 지하수관정 점검정비이력 등록
ugrwtrObj.chckImprmnHist.add = {
		
	TITLE : "지하수관정 점검이력 등록",
	
	CLASS_NAME : "UndergroundWaterChckImprmnHistAdd",

	selector : null,
	
	chkIdn : null,
	
	permNtNo : null,
	
	init : function() {
		var that = this;
		
		// 달력
		$("#undrWtrTubchkRecdInsertform .easyui-datebox", that.selector).datebox();
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					that.close();
					ugrwtrObj.chckImprmnHist.open(that.permNtNo);
				}
				else {
					alert("지하수관정 점검관리이력 등록에 실패하였습니다.");
				}
			}
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장
		$(".a_add_addUgrwtrHist", that.selctor).on("click", function() {
			var permNtNo = $("#permNtNo").val();
			
			that.save(permNtNo);
		});
		
		// window close
		$(".a_close_addUgrwtrHist", that.selector).on("click", function() {
			that.close();
		});
	},
		
	open : function(permNtNo) {
		var that = this;
		
		that.permNtNo = permNtNo;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/ugrwtr/chckImprmnHist/addPage.do";
		var windowOptions = {
			width : 400,
			height : 244,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.init();
			that.bindEvents();
		});
		
		that.selector = "#" + id;
	},
	
	save : function() {
		var that = this;

		var url = CONTEXT_PATH + "/ugrwtr/chckImprmnHist/" + that.permNtNo + "/add.do";
		
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		permNtNo = null;
		
		windowObj.removeWindow(that.selector);
	}	
};
