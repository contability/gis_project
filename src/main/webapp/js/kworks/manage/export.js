$(function() {
	exportObj.init();
});

/**
 * 내보내기 관리 객체
 * @type {Object}
 */
var exportObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_export_list",
	
	/**
	 * 고급 출력 팝업
	 * @type {Object}
	 */
	popup : null,
	
	/**
	 * 초기화 
	 */
	init : function() {
		var that = this;
		that.initOutput();
		that.bindEvents();
		that.detailDialog.init(that);
		that.returnDialog.init(that);
	},
	
	/**
	 * 출력 초기화
	 */
	initOutput : function() {
		var that = this;
		hghnkOutputObj.getCapabilities(true);
		hghnkOutputObj.getPromise().done(function() {
			$(".div_output_guide", that.selector).hide();
			$("form[name=frm_export_list]", that.selector).show();
		});
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 고급 출력 서비스 이용
		$(".a_connect_output", that.selector).click(function() {
			hghnkOutputObj.getCapabilities(true);
			hghnkOutputObj.getPromise().done(function() {
				$(".div_output_guide", that.selector).hide();
				$("form[name=frm_export_list]", that.selector).show();
			});
			return false;
		});
		
		$(".tbl_export_list tbody tr").hover(function(){
			$('.tbl_export_list tr').css('background', '#ffffff');
			$(this).css('background', '#b9d5ef');
		});
		
		$(".tbl_export_list tbody tr", that.selector).click(function() {
			var element = $(this);
			var exportNo = element.attr("data-export-no");
			var exportTy = element.attr("data-export-ty");
			
			var url = CONTEXT_PATH + "/rest/export/" +  exportNo + "/select.do";
			$.get(url).done(function(result) {
				if(result && result.data) {
					if(exportTy == "OUTPUT") {
						that.openOutputPopup(exportNo, result.data);
					}
					else {
						that.openDetailDialog(exportNo, result.data);
					}
				}
				else {
					alert("내보내기 상세정보를 불러오는데 실패했습니다.");
				}
			}).fail(function(result) {
				alert("내보내기 상세정보를 불러오는데 실패했습니다.");
			});
		});
		
		// 검색
		$(".a_search", that.selector).click(function() {
			that.search(1);
	    });
	},
	
	/**
	 * 검색
	 * @param {Number} pageIndex 페이지 인덱스
	 */
	search : function(pageIndex) {
		var that = this;
		if(pageIndex) {
			$("#pageIndex").val(pageIndex);
		}
		$("form", that.selector).submit();
	},
	
	/**
	 * 출력 팝업 열기
	 * @param exportNo
	 * @param data
	 */
	openOutputPopup : function(exportNo, data) {
		var that = this;
		that.closeDialog();
		
		var layoutId = data.kwsExportFilterOutpt.layoutId;
		var layOut = hghnkOutputObj.getLayout(layoutId);
		var width, height = 0;
		if(layOut.type == "Landscape") {
			width = 1120;
			height = 790;
		}
		else {
			width = 750;
			height = 850;
		}
		
		var url = CONTEXT_PATH + "/popup/output/highRankConfirm.do?exportNo="+exportNo;
		var options = {
			scrollbars : "no",
			left : 100+"px",
			top : 20+"px",
			width : width+"px",
			height : height+"px"
		};
		that.popup =  popupUtils.open(url, "exportOutput", options);
	},
	
	/**
	 * 상세정보 팝업 열기
	 * @param exportNo
	 */
	openDetailDialog : function(exportNo, data) {
		var that = this;
		that.closeDialog();
		that.detailDialog.open(exportNo, data);
	},
	
	/**
	 * 반려처리 팝업 열기
	 * @param exportNo
	 */
	openReturnDialog : function(exportNo) {
		var that = this;
		that.returnDialog.open(exportNo);
	},
	
	/**
	 * 팝업 창 닫기
	 */
	closeDialog : function() {
		var that = this;
		if(that.popup) {
			that.popup.close();
		}
		that.detailDialog.close();
		that.returnDialog.close();
	}
		
};

/**
 * 내보내기 상세정보 팝업 (승인처리)
 * @type {Object}
 */
exportObj.detailDialog = {
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_export_detail",
	
	/**
	 * 부모 객체 - 내보내기 객체
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 초기화
	 * @param {Object} parent 부모 객체
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
		
		that.initUi();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 내보내기 상세정보 다이어로그 설정
		$(that.selector).dialog({
			autoOpen: false,
	     	height: 600,
	      	width: 700,
	      	close: function() {
	      		that.close();
	      	}
	    });
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result && result.rowCount && result.rowCount == 1) {
					alert("승인 되었습니다.");
					that.parent.search();
				}
				else {
					alert("승인에 실패했습니다.");
				}
			}
		});
	},
	
	/**
	 * 열기
	 * @param {Number} exportNo 내보내기 번호
	 */
	open : function(exportNo, data){
		var that = this;
		
		that.exportNo = exportNo;
		
		// 좌표계 초기 로딩 시 hide
		$(".div_export_detail_exportCntmId", that.selector).hide();
		
		// 요청자
		$(".rqesterId", that.selector).html(data.kwsUser.userNm);
		
		// 요청 일시
		$(".requstDt", that.selector).html(dateUtils.toDateTime(new Date(data.requstDt)));
		
		// 내보내기 명
		$(".exportNm", that.selector).html(data.exportNm);
		
		// 내보내기 타입
		$(".exportTy", that.selector).html(data.exportTy);
		
		// 내보내기 좌표계
		if(data.exportTy=="DXF"||data.exportTy=="EXCEL"||data.exportTy=="SHAPE") {
			$(".div_export_detail_exportCntmId", that.selector).show();
			$(".exportCntmId", that.selector).html(data.exportCntmId);
		}
		
		// 파일 다운로드
		var tagStr = "";
		for(var i in data.kwsExportFiles) {
			var file = data.kwsExportFiles[i].kwsFile;
			tagStr += '<li>';
			tagStr += '<a href="' + CONTEXT_PATH + '/cmmn/file/' + file.fileNo + '/download.do" >';
			tagStr += '<span>' + file.orignlFileNm + "</span>";
			tagStr += '</a>';
			tagStr += '</li>';
		}
		$(".fileList", that.selector).html(tagStr);
		
		// 상태에따라 버튼 동적 생성
		var buttons = {};
		if(data.progrsSttus == "CONSENT_WAITING") {
			buttons["승인"] = function() {
				that.exportConsent(that.exportNo);
	    	};
	    	buttons["반려"] = function() {
	    		that.exportReturn();
	    	};
		}
		buttons["닫기"] = function() {
        	that.close();
        };
		
		$(that.selector).dialog({
	      	buttons: buttons
	    });
		
		$(that.selector).dialog("open");
	},
	
	/**
	 * 승인
	 */
	exportConsent : function() {
		var that = this;
		var url = CONTEXT_PATH + "/rest/export/" +  that.exportNo + "/consent.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 반려
	 */
	exportReturn : function(exportNo) {
		var that = this;
		var exportNo = that.exportNo;
		that.parent.openReturnDialog(exportNo);
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		$(that.selector).dialog("close");
	}
};

/**
 * 내보내기 반려 팝업
 * @type {Object}
 */
exportObj.returnDialog = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_export_return",
	
	/**
	 * 부모 객체 - 공지사항 객체
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 초기화
	 * @param {Object} parent 부모 객체
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
		
		that.initUi();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 반려 등록 다이어로그 설정
		$(that.selector).dialog({
			autoOpen: false,
			width: 700,
	     	height: 440,
	     	modal : true,
	      	buttons: {
		    	"등록" : function() {
		    		that.add();
		    	},
		        "닫기" : function() {
		        	that.close();
		        }
			},
	      	close: function() {
	      		that.close();
	      	}
	    });
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result && result.rowCount && result.rowCount == 1) {
					alert("반려 되었습니다.");
					that.close();
					that.parent.search();
				}
				else {
					alert("반려에 실패했습니다.");
				}
			}
		});
	},

	/**
	 * 등록
	 */
	add : function() {
		var that = this;
		var url = CONTEXT_PATH + "/rest/export/" +  that.exportNo + "/return.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 열기
	 */
	open : function(exportNo) {
		var that = this;
		that.exportNo = exportNo;
		$(that.selector).dialog("open");
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		$(that.selector).dialog("close");
	}
	
};