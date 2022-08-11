windowObj.controlPointObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도시기준점 목록",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "ListContorlPoint",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 도시기준점 단 건 조회
		$("table", that.selector).on("click", "tr", function(){
			var element = this;
			var ftrIdn = $(element).attr("data-ftr-idn");
			var objectid = $(element).attr("data-objectid");
			
			windowObj.controlPointViewObj.open(ftrIdn, objectid);
		});
		
		// 목록추가 버튼
		$("#btn_listControlPoint_add", that.selector).click(function(){
			windowObj.controlPointAddObj.open();
		});
		
		// 검색버튼
		$(".tableTop a", that.selector).click(function(){
			that.search($(".tableTop .txt_listControlPoint_serIdn", that.selector).val());
		});
		
		// 엔터키
		$(".tableTop .txt_listControlPoint_serIdn", that.selector).keydown(function(e){
			if(e.keyCode == 13){
				that.search($(".tableTop .txt_listControlPoint_serIdn", that.selector).val());
			}
		});
	},
	
	open : function() {
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/ctrlPnt/listAll.do";
		var windowOptions = {
			width : 700,
			height : 600,
			left : 350,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.bindEvents();
			
		});
		that.selector = "#" + id;
	},
	
	search : function(searchStr){
		var that = this;
		var url = CONTEXT_PATH + "/window/ctrlPnt/list.do";
		var params = {};
		params.searchCondition = "serIdn";
		params.serIdn = searchStr;
		
		$.post(url,params).done(function(result){
			that.makeTable(result.result);
		}).fail(function(){
			alert("도시기준점 목록을 불러오는데 실패하였습니다.");
		});
	},
	
	makeTable : function(data) {
		var that = this;
		var tableStr = "";
		$(".tbl_listControlPoint tbody", that.selector).empty();
		
		$.each(data, function(k,v) {
			tableStr += "<tr data-seridn='" + v.serIdn + "' data-objectid='${v.objectid}'>";
			tableStr += "<td class='serIdn'><center>" + v.serIdn + "</center></td>";
			tableStr += "<td class='grad'><center>" + v.grad + "</center></td>";
			tableStr += "<td class='hjdCde'><center>" + v.hjdCde + "</center></td>";
			tableStr += "<td class='planCdeNm'><center>" + v.planCdeNm + "</center></td>";
			tableStr += "<td class='wrkOrg'><center>" + v.wrkOrg + "</center></td>";
			tableStr += "<td class='pcbDes'><center>" + v.pcbDes + "</center></td>";
			tableStr += "<td class='srvYmd'><center>" + v.srvYmd + "</center></td>";
			tableStr += "</tr>";
		});
		
		$(".tbl_listControlPoint tbody", that.selector).append(tableStr);
	},
	
	close : function() {
		var that = this;
		windowObj.controlPointViewObj.close();
		windowObj.removeWindow(that.selector);
		that.selector = null;
		return false;
	}
};

/**
 * 도시기준점 정보창 객체
 */
windowObj.controlPointViewObj = {

	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도시기준점 점의조서",
	
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "ViewContorlPoint",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	ftrCde : null,
	
	ftrIdn : null,
	
	serIdn : null,
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 관리이력 버튼
		$("#btn_controlPointHistory_list", that.selector).click(function() {
			that.ftrCde = $(".hid_controlView_ftrCde", that.selector).val();
			that.serIdn = $(".hid_controlView_serIdn", that.selector).val();
			windowObj.controlPointManageListObj.open(that.ftrCde, that.serIdn);
		});
		
		// 인쇄버튼
		$("#btn_controlPoint_print", that.selector).click(function() {
			that.print();
		});
		
		//편집버튼
		$("#btn_controlPoint_modify", that.selector).click(function() {
			windowObj.controlPointEditObj.open($(".hid_controlView_ftrIdn", that.selector).val());
			that.close();
		});
		
		// 닫기버튼
		$("#btn_controlPoint_close", that.selector).click(function() {
			that.close();
		});
	},	
	
	open : function(ftrIdn, objectid) {
		var that = this;
		
		windowObj.controlPointManageListObj.close();
		windowObj.controlPointManageViewObj.close();
		windowObj.controlPointManageAddObj.close();
		windowObj.controlPointManageEditObj.close();
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/ctrlPnt/" + ftrIdn + "/selectOne.do";
		var windowOptions = {
			width : 600,
			height : 600,
			left : 500,
			onClose : function() {
				that.close();
			}
		};
		
		//위치이동
		spatialSearchUtils.selectOne(that.TITLE, "RDL_PCBS_PS", objectid, null, function(data) {
			if(data) {
				var format = new ol.format.WKT();
				var geom = data[MAP.GEOMETRY_ATTR_NAME];
				if(geom) {
					var features = [format.readFeature(geom)];
					highlightObj.showOrangeFeatures(that.CLASS_NAME, features, true, {
						projection : ol.proj.get(MAP.PROJECTION)
					});
				}
			}
		});
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.bindEvents();
		});
		
		that.selector = "#" + id;
	},
	
	//인쇄 (레포팅 툴사용)
	print : function() {
		var that = this;
		
		that.ftrCde = $(".hid_controlView_ftrCde", that.selector).val();
		that.ftrIdn = $(".hid_controlView_ftrIdn", that.selector).val();
		
		var url = CONTEXT_PATH + "/clipreport/ctrlPntWtnnc.do?ftrCde=" + that.ftrCde + "&ftrIdn=" + that.ftrIdn;
		
		popupUtils.open(url, "점의조서", { width : 900 , height : 800, left : 300, top:150 });
		
	},
	
	close : function() {
		var that = this;
		
		that.ftrCde = "";
		that.ftrIdn = "";
		that.serIdn = "";
		
		windowObj.controlPointManageListObj.close();
		windowObj.controlPointManageViewObj.close();
		windowObj.controlPointManageAddObj.close();
		windowObj.controlPointManageEditObj.close();
		
		windowObj.removeWindow(that.selector);
		
		that.selector = null;
		
		return false;
	}
};


/**
 * 도시기준점 편집창 객체
 */
windowObj.controlPointEditObj = {
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도시기준점 점의조서 수정",
	
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "ModifyContorlPoint",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function(){
		var that = this;
		//수정버튼
		$("#btn_controlPoint_modify_save", that.selector).click(function() {
			that.modify();
		});
		
		//닫기버튼
		$("#btn_controlPoint_modify_close", that.selector).click(function() {
			that.close();
		});
		
		// 약도 이미지삭제 버튼
		$(".td_rdlPcbsPs_modify_outlineMap",that.selector).on("click","a",function(){
			$(".td_rdlPcbsPs_modify_outlineMap input",that.selector).val("outlineMap");
			
			var inputStr = "<input type='file' name='outlineMap' />";
			inputStr += "<input type='hidden' name='deleteImageType' value='outlineMap' />";
			
			$(".td_rdlPcbsPs_modify_outlineMap",that.selector).empty();
			$(".td_rdlPcbsPs_modify_outlineMap",that.selector).append(inputStr);
		});
		
		//관측도 이미지삭제 버튼
		$(".td_rdlPcbsPs_modify_observationMap",that.selector).on("click","a",function(){
			$(".td_rdlPcbsPs_modify_observationMap input",that.selector).val("observationMap");
			
			var inputStr = "<input type='file' name='observationMap' />";
			inputStr += "<input type='hidden' name='deleteImageType' value='observationMap' />";
			
			$(".td_rdlPcbsPs_modify_observationMap",that.selector).empty();
			$(".td_rdlPcbsPs_modify_observationMap",that.selector).append(inputStr);
		});
		
		// 근경 이미지삭제 버튼
		$(".td_rdlPcbsPs_modify_closeRangeView",that.selector).on("click","a",function(){
			$(".td_rdlPcbsPs_modify_closeRangeView input",that.selector).val("closeRangeView");
			
			var inputStr = "<input type='file' name='closeRangeView' />";
			inputStr += "<input type='hidden' name='deleteImageType' value='closeRangeView' />";
			
			$(".td_rdlPcbsPs_modify_closeRangeView",that.selector).empty();
			$(".td_rdlPcbsPs_modify_closeRangeView",that.selector).append(inputStr);
		});
		
		// 원경 이미지삭제 버튼
		$(".td_rdlPcbsPs_modify_distantView",that.selector).on("click","a",function(){
			$(".td_rdlPcbsPs_modify_distantView input",that.selector).val("distantView");
			
			var inputStr = "<input type='file' name='distantView' />";
			inputStr += "<input type='hidden' name='deleteImageType' value='distantView' />";
			
			$(".td_rdlPcbsPs_modify_distantView",that.selector).empty();
			$(".td_rdlPcbsPs_modify_distantView",that.selector).append(inputStr);
		});
		
	},	
	
	open : function(ftrIdn){
		var that = this;
		windowObj.controlPointViewObj.close();
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/ctrlPnt/" + ftrIdn + "/modifyControlPointPage.do";
		var windowOptions = {
			width : 610,
			height : 590,
			left : 500,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.bindEvents();
		});
		
		that.selector = "#" + id;
	},
	
	modify : function(){
		var that = this;
		
		var ftrIdn = $(".hdn_ftrIdn", that.selector).val();
		var objectid = $(".hdn_objectid", that.selector).val();
		var url = CONTEXT_PATH + "/window/ctrlPnt/" + ftrIdn + "/modifyControlPoint.do";
		
		$("form", that.selector).attr("action",url);
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result && result.cnt && result.cnt == 1) {
					that.close();
					//windowObj.controlPointObj.open();
					windowObj.controlPointViewObj.open(ftrIdn, objectid);
				}
				else {
					alert("도시기준점 편집에 실패했습니다.");
				}
			}
		});
		$("form", that.selector).submit();
	},
	
	close : function(){
		var that = this;
		
		windowObj.removeWindow(that.selector);
		
		that.selector = null;
		return false;
	}
};

/**
 * 도시기준점 등록창 객체
 */
windowObj.controlPointAddObj = {
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도시기준점 점의조서 등록",
	
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "AddContorlPoint",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		//추가 버튼
		$("#btn_controlPoint_add", that.selector).click(function() {
			that.add();
		});
		//추가 버튼
		$("#btn_controlPoint_close", that.selector).click(function() {
			that.close();
		});
	},	
	
	/**
	 * 팝업창 열기
	 */
	open : function() {
		var that = this;
		windowObj.controlPointViewObj.close();
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/ctrlPnt/addControlPointPage.do";
		var windowOptions = {
			width : 600,
			height : 570,
			left : 500,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.bindEvents();
		});
		
		that.selector = "#" + id;
	},
	
	/**
	 * 편집 저장
	 */
	add : function(){
		var that = this;
		
		var url = CONTEXT_PATH + "/window/ctrlPnt/addControlPoint.do";
		
		$("form", that.selector).attr("action",url);
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result && result.cnt && result.cnt == 1) {
					windowObj.controlPointObj.open();
					that.close();
				}
				else {
					alert("도시기준점 등록에 실패했습니다.");
				}
			}
		});
		$("form", that.selector).submit();
	},
	
	/**
	 * 팝업창 닫기
	 */
	close : function(){
		var that = this;
		windowObj.removeWindow(that.selector);
		that.selector = null;
		return false;
	}
};

/**
 * 도시기준점 관리이력 목록창 객체
 */
windowObj.controlPointManageListObj = {
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도시기준점 관리이력 목록",
	
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "ListContorlPointHistory",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	ftrCde : null,
	
	serIdn : null,
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 추가
		$("#btn_controlPointHistory_add", that.selector).click(function() {
			windowObj.controlPointManageAddObj.open(that.serIdn);
		});
		
		//인쇄버튼
		$("#btn_controlPointHistory_print", that.selector).click(function() {
			that.print();
		});
		
		//닫기 버튼
		$("#btn_controlPointHistory_close", that.selector).click(function() {
			that.close();
		});
		
		//상세정보 보기
		$(".tbl_controlPointHistoryList", that.selector).on("click", "tr", function() {
			var element = this;
			
			windowObj.controlPointManageViewObj.open($(element).attr("data-ftr-idn"));
		});
	},	
	
	open : function(ftrCde, serIdn){
		var that = this;
		
		that.ftrCde = ftrCde;
		that.serIdn = serIdn;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/ctrlPnt/" + that.serIdn + "/listControlPointHistory.do";
		var windowOptions = {
			width : 600,
			height : 600,
			left : 350,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.bindEvents();
		});
		that.selector = "#" + id;
	},
	
	//인쇄
	print : function() {
		var that = this;

		var url = CONTEXT_PATH + "/clipreport/ctrlPntRegstr.do?ftrCde=" + that.ftrCde + "&serIdn=" + that.serIdn;
		
		popupUtils.open(url, "도시기준점 관리대장", { width : 900 , height : 800, left : 300, top:150 });
	},
	
	close : function(){
		var that = this;
		
		that.ftrCde = "";
		that.serIdn = "";
		
		windowObj.removeWindow(that.selector);
		
		that.selector = null;
		return false;
	}		
};

/**
 * 도시기준점 관리이력 정보창 객체
 */
windowObj.controlPointManageViewObj = {
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도시기준점 관리이력 정보",
	
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "ViewContorlPointHistory",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	ftrIdn : null,
	
	ftrCde : null,
	
	serIdn : null,
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		$("#btn_controlPointHistoryView_modify", that.selector).click(function() {
			windowObj.controlPointManageEditObj.open(that.ftrIdn);
		});
		
		$("#btn_controlPointHistoryView_print", that.selector).click(function(){
			that.print();
		});
		
		$("#btn_controlPointHistoryView_close", that.selector).click(function(){
			that.close();
		});
	},	
	
	open : function(ftrIdn) {
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/ctrlPnt/" + ftrIdn + "/selectOneControlPointHistory.do";
		var windowOptions = {
			width : 600,
			height : 490,
			left : 500,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.bindEvents();
			that.ftrIdn = ftrIdn;
		});
		
		that.selector = "#" + id;
	},
	
	print : function() {
		var that = this;

		that.ftrCde = $(".hid_controlPointHistoryView_ftrCde").val();
		
		var url = CONTEXT_PATH + "/clipreport/ctrlPntWrinvstg.do" + "?" + "ftrCde=" + that.ftrCde + "&ftrIdn=" + that.ftrIdn;
		
		popupUtils.open(url, "도시기준점 조사서", { width : 900 , height : 800, left : 300, top:150 });
	},
	
	close : function() {
		var that = this;
		
		that.ftrCde = "";
		that.ftrIdn = "";
		that.serIdn = "";
		
		windowObj.removeWindow(that.selector);
		
		that.selector = null;
		return false;
	}
};

/**
 * 도시기준점 관리이력 등록창 객체
 */
windowObj.controlPointManageAddObj = {
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도시기준점 관리이력 등록",
	
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "AddContorlPointHistory",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function(){
		var that = this;
		
		// 등록
		$("#btn_controlPointHistory_add", that.selector).click(function() {
			that.add();
		});
		
		// 닫기
		$("#btn_controlPointHistory_close", that.selector).click(function() {
			that.close();
		});
	},	
	
	open : function(serIdn){
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/ctrlPnt/" + serIdn + "/addControlPointHistoryPage.do";
		var windowOptions = {
			width : 600,
			height : 300,
			left : 500,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.bindEvents();
		});
		
		that.selector = "#" + id;
	},
	
	add : function(){
		var that = this;
		
		var url = CONTEXT_PATH + "/window/ctrlPnt/addControlPointHistory.do";
		
		$("form", that.selector).attr("action",url);
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result && result.cnt && result.cnt == 1) {
					windowObj.controlPointObj.open();
					that.close();
				}
				else {
					alert("도시기준점 관리이력 등록에 실패했습니다.");
				}
			}
		});
		
		$("form", that.selector).submit();
	},
	
	close : function(){
		var that = this;
		
		windowObj.removeWindow(that.selector);
		
		that.selector = null;
		return false;
	}	
};

/**
 * 도시기준점 관리이력 수정창 객체
 */
windowObj.controlPointManageEditObj = {
		/**
		 * 제목
		 * @type {String}
		 */
		TITLE : "도시기준점 관리이력 편집",
		
		/**
		 * 클래스 명
		 * @type {String}
		 */
		CLASS_NAME : "ModifyContorlPointHistory",
		
		/**
		 * 선택자
		 * @type {String}
		 */
		selector : null,
		
		ftrIdn : null,
		
		/**
		 * 이벤트 등록
		 */
		bindEvents : function(){
			var that = this;
			
			$("#a_rdtPcbsDt_modify_change",that.selector).click(function(){
				that.modify();
			});
			
			$("#a_rdtPcbsDt_modify_close",that.selector).click(function(){
				that.close();
			});
			
			// 근경 이미지삭제 버튼
			$(".td_rdtPcbsDt_modify_closeRangeView",that.selector).on("click","a",function(){
				var inputStr = "<input type='file' name='closeRangeView' />";
				inputStr += "<input type='hidden' name='deleteImageType' value='closeRangeView' />";
				$(".td_rdtPcbsDt_modify_closeRangeView",that.selector).empty();
				$(".td_rdtPcbsDt_modify_closeRangeView",that.selector).append(inputStr);
			});
			
			// 원경 이미지삭제 버튼
			$(".td_rdtPcbsDt_modify_distantView",that.selector).on("click","a",function(){
				var inputStr = "<input type='file' name='distantView' />";
				inputStr += "<input type='hidden' name='deleteImageType' value='distantView' />";
				$(".td_rdtPcbsDt_modify_distantView",that.selector).empty();
				$(".td_rdtPcbsDt_modify_distantView",that.selector).append(inputStr);
			});
			
			// 기타 이미지삭제 버튼
			$(".td_rdtPcbsDt_modify_tempView",that.selector).on("click","a",function(){
				var inputStr = "<input type='file' name='tempView' />";
				inputStr += "<input type='hidden' name='deleteImageType' value='tempView' />";
				$(".td_rdtPcbsDt_modify_tempView",that.selector).empty();
				$(".td_rdtPcbsDt_modify_tempView",that.selector).append(inputStr);
			});
		},	
		
		open : function(ftrIdn){
			var that = this;
			windowObj.controlPointManageViewObj.close();
			// 이전 창 닫기
			if(that.selector) {
				that.close();
			}
			
			var url = CONTEXT_PATH + "/window/ctrlPnt/" + ftrIdn + "/modifyControlPointHistoryPage.do";
			that.ftrIdn = ftrIdn;
			
			var windowOptions = {
				width : 600,
				height : 520,
				left : 500,
				onClose : function() {
					that.close();
				}
			};
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
				that.bindEvents();
			});
			
			that.selector = "#" + id;
		},
		
		modify : function(){
			var that = this;
			var ftrIdn = that.ftrIdn;
			var modifyUrl = CONTEXT_PATH + "/window/ctrlPnt/" + ftrIdn + "/modifyControlPointHistory.do";
			
			$("form", that.selector).attr("action",modifyUrl);
			$("form", that.selector).ajaxForm({
				dataType : "json",
				success : function(result) {
					if(result && result.cnt && result.cnt == 1) {
						windowObj.controlPointManageViewObj.open(that.ftrIdn);
						that.close();
					}
					else {
						alert("도시기준점 관리이력 편집에 실패했습니다.");
					}
				}
			});
			$("form", that.selector).submit();
		},
		
		close : function(){
			var that = this;
			windowObj.removeWindow(that.selector);
			that.selector = null;
			return false;
		}	
};