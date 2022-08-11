$(function() {
	qestnObj.init();
});

/**
 * 묻고답하기-질문 게시판 객체
 * @type {Object}
 */
var qestnObj = {
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_qestn_list",
		
	/**
	 * 초기화 
	 */
	init : function() {
		var that = this;
		that.selectDialog.init(that);
		that.addDialog.init(that);
		that.modifyDialog.init(that);
		that.bindEvents();
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 질문 항목 선택
		$(".tbl_qestn_list tbody tr", that.selector).click(function() {
			var element = $(this);
			var qestnNo = element.attr("data-qestn-no");
			that.openSelectDialog(qestnNo);
			$(element).addClass("on");
		});

		// 질문 등록 창 열기
		$(".a_new_qestn", that.selector).click(function() {
			that.openAddDialog();
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
	 * 선택 해제
	 */
	clearSelected : function() {
		var that = this;
		$(".tbl_qestn_list tbody tr", that.selector).removeClass("on");
	},

	
	/**
	 * 팝업 창 닫기
	 */
	closeDialog : function() {
		var that = this;
		that.selectDialog.close();
		that.addDialog.close();
		that.modifyDialog.close();
	},
	
	/**
	 * 조회 팝업 열기
	 * @param {Number} qestnNo 질문 번호
	 */
	openSelectDialog : function(qestnNo) {
		var that = this;
		that.closeDialog();
		that.selectDialog.open(qestnNo);
	},
	
	/**
	 * 등록 팝업 열기
	 */
	openAddDialog : function() {
		var that = this;
		that.closeDialog();
		that.addDialog.open();
	},
	
	/**
	 * 수정 팝업 열기
	 * @param {Number} qestnNo 질문 번호
	 */
	openModifyDialog : function(qestnNo) {
		var that = this;
		that.closeDialog();
		that.modifyDialog.open(qestnNo);
	}

};

/**
 * 묻고답하기-질문 게시판 조회 팝업
 * @type {Object}
 */
qestnObj.selectDialog = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_qestn_select",
		
	/**
	 * 부모 객체 - 묻고답하기-질문 게시판 객체
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 묻고답하기-질문 게시판 번호
	 * @type {Object}
	 */
	qestnNo : null,
		
	/**
	 * 초기화
	 * @param {Object} parent  부모 객체
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
		
		// 게시물 수정 다이어로그 설정
		$(that.selector).dialog({
			autoOpen: false,
	     	height: 600,
	      	width: 700,
	      	close: function() {
	      		that.close();
	      	}
	    });
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		
		that.parent.clearSelected();
		that.qestnNo = null;
		$(".qestnSj", that.selector).text("");
		$(".qestnCn", that.selector).html("");

		$(".div_file_list", that.selector).hide();
		
		$(".div_answer .answerCn", that.selector).html("");
		$(".div_answer", that.selector).hide();
	},
	
	/**
	 * 열기
	 * @param {Number} qestnNo 게시물 번호
	 */
	open : function(qestnNo) {
		var that = this;
		that.clear();
		
		that.qestnNo = qestnNo;
		var url = restUtils.getResUrl() + qestnNo + "/select.do";
		
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;
				
				$(".qestnSj", that.selector).text(data.qestnSj);
				$(".qestnCn", that.selector).html(data.qestnCn);
				
				// 게시물 파일 정보 로딩
				if(data.kwsQestnFiles && data.kwsQestnFiles.length > 0) {
					var tagStr = '';
					for(var i=0, len = data.kwsQestnFiles.length; i < len; i++) {
						var file = data.kwsQestnFiles[i].kwsFile;
						tagStr += '<li>';
						tagStr += '<a href="' + CONTEXT_PATH + '/cmmn/file/' + file.fileNo + '/download.do" >';
						tagStr += '<span>' + file.orignlFileNm + "</span>";
						tagStr += '</a>';
						tagStr += '</li>';
					}
					$(".fileList", that.selector).html(tagStr);
					$(".div_file_list", that.selector).show();
				}
				
				if(data.kwsAnswer) {
					$(".div_answer .answerCn", that.selector).html(data.kwsAnswer.answerCn);
					$(".div_answer", that.selector).show();
				}
				
				// 답변 중일 경우에만 수정 가능
				var buttons = {};
				if(data.progrsSttus == "ANSWER_WAITING") {
					buttons["수정"] = function() {
			    		that.parent.openModifyDialog(that.qestnNo);
			    		that.close();
			    	};
				}
				buttons["닫기"] = function() {
		        	that.close();
		        };
				
				$(that.selector).dialog({
			      	buttons: buttons
			    });
				$(that.selector).dialog("open");
			}
			else {
				alert("게시물 정보를 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("게시물 정보를 불러오는데 실패했습니다.");
		});
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		that.clear();
		$(that.selector).dialog("close");
	}
		
};

/**
 * 묻고답하기-질문 게시판 등록 팝업
 * @type {Object}
 */
qestnObj.addDialog = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_qestn_add",
	
	/**
	 * 부모 객체 - 묻고답하기-질문 게시판 객체
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
		
		// 묻고답하기-질문 게시판 등록 다이어로그 설정
		$(that.selector).dialog({
			autoOpen: false,
	     	height: 600,
	      	width: 700,
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
		
		// Ajax 파일 업로드를 위해 ajaxForm 으로 등록
		$("form", that.selector).ajaxForm({
			dataType : "json",
			beforeSubmit : function(arr, form) {
				for(var i=0, len=arr.length; i < len; i++) {
					var obj = arr[i];
					var name = obj.name;
					
					// 필수 입력 체크
					if(name == "qestnSj") {
						if(!obj.value) {
							alert("제목을 입력하여 주십시오.");
							form.find("input[name=" + name + "]").focus();
							return false;
						}
					}
					else if(name == "qestnCn") {
						// textarea 의 내용이 아닌 ckeditor 의 내용 입력
						obj.value = CKEDITOR.instances.txa_qestn_add_cn.getData();
					}
				}
			},
			success : function(result) {
				if(result && result.rowCount && result.rowCount == 1) {
					that.parent.search();
				}
				else {
					alert("게시물 등록에 실패했습니다.");
				}
			}
		});
		
		// 편집기 적용
		CKEDITOR.replace( 'txa_qestn_add_cn',
		{
			customConfig: CONTEXT_PATH + '/js/kworks/cmmn/ckeditorconfig.js'
		});
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		$("input[name=qestnSj]", that.selector).val("");
		$("textarea[name=qestnCn]", that.selector).html("");
		CKEDITOR.instances.txa_qestn_add_cn.setData("");
		$(".span_file", that.selector).html('<input type="file" name="qestnFile" value="" />');
	},
	
	/**
	 * 등록
	 */
	add : function() {
		var that = this;
		$("form", that.selector).submit();
	},
	
	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		$(that.selector).dialog("open");
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		that.clear();
		$(that.selector).dialog("close");
	}

};

/**
 * 묻고답하기-질문 게시판 수정 팝업
 * @type {Object}
 */
qestnObj.modifyDialog = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_qestn_modify",
		
	/**
	 * 부모 객체 - 묻고답하기-질문 게시판 객체
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 묻고답하기-질문 게시판 번호
	 * @type {Object}
	 */
	qestnNo : null,
		
	/**
	 * 초기화
	 * @param {Object} parent  부모 객체
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
		that.initUi();
		that.bindEvents();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 게시물 수정 다이어로그 설정
		$(that.selector).dialog({
			autoOpen: false,
	     	height: 600,
	      	width: 700,
	      	buttons: {
		    	"수정" : function() {
		    		that.modify();
		    	},
		    	"삭제" : function() {
		    		if(confirm("삭제하시겠습니까?")) {
		    			that.remove();
		    		}
		    	},
		        "닫기" : function() {
		        	that.close();
		        }
			},
	      	close: function() {
	      		that.close();
	      	}
	    });
		
		// Ajax 파일 업로드를 위해 ajaxForm 으로 등록
		$("form", that.selector).ajaxForm({
			dataType : "json",
			beforeSubmit : function(arr) {
				for(var i=0, len=arr.length; i < len; i++) {
					var obj = arr[i];
					var name = obj.name;
					
					// 필수 입력 체크
					if(name == "qestnSj") {
						if(!obj.value) {
							alert("제목을 입력하여 주십시오.");
							form.find("input[name=" + name + "]").focus();
							return false;
						}
					}
					else if(name == "qestnCn") {
						// textarea 의 내용이 아닌 ckeditor 의 내용 입력
						obj.value = CKEDITOR.instances.txa_qestn_modify_cn.getData();
					}
				}
			},
			success : function(result) {
				if(result && result.rowCount && result.rowCount == 1) {
					that.parent.search();
				}
				else {
					alert("게시물 수정에 실패했습니다.");
				}
			}
		});
		
		// 편집기 적용
		CKEDITOR.replace( 'txa_qestn_modify_cn',
		{
			customConfig: CONTEXT_PATH + '/js/kworks/cmmn/ckeditorconfig.js'
		});
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 게시물 파일 삭제
		$(".fileList li a.a_remove_file", that.selector).click(function() {
			var fileNo = $(".fileList input[name=fileNo]", that.selector).val();
			if(fileNo != "") {
				$(".fileList input[name=deleteFileNo]", that.selector).val(fileNo);
				$(".fileList", that.selector).hide();
			}
		});
		
		// 게시물 파일 추가 시 기존 파일 삭제
		$(".span_file", that.selector).on("change", "input[name=qestnFile]", function() {
			$(".fileList li a.a_remove_file", that.selector).trigger("click");
		});
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		that.qestnNo = null;
		$("input[name=qestnSj]", that.selector).val("");
		$("textarea[name=qestnCn]", that.selector).text("");
		CKEDITOR.instances.txa_qestn_modify_cn.setData("");
		$(".fileList input[name=fileNo]", that.selector).val("");
		$(".fileList input[name=deleteFileNo]", that.selector).val("");
		$(".fileList .orignlFileNm", that.selector).text("");
		$(".span_file", that.selector).html('<input type="file" name="qestnFile" value="" />');
		$(".fileList", that.selector).hide();
	},
	
	/**
	 * 수정
	 */
	modify : function() {
		var that = this;
		var url = restUtils.getResUrl() + that.qestnNo + "/modify.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 삭제
	 */
	remove : function() {
		var that = this;
		var url = restUtils.getResUrl() + that.qestnNo + "/remove.do";
		$.post(url).done(function(result) {
			if(result && result.rowCount && result.rowCount == 1) {
				that.parent.search(1);
			}
			else {
				alert("게시물 삭제에 실패했습니다.");
			}
		}).fail(function() {
			alert("게시물 삭제에 실패했습니다.");
		});
	},
	
	/**
	 * 열기
	 * @param {Number} qestnNo 게시물 번호
	 */
	open : function(qestnNo) {
		var that = this;
		that.clear();
		
		that.qestnNo = qestnNo;
		var url = restUtils.getResUrl() + qestnNo + "/select.do";
		
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;

				// 게시물 정보 로딩
				$("input[name=qestnSj]", that.selector).val(data.qestnSj);
				CKEDITOR.instances.txa_qestn_modify_cn.setData(data.qestnCn);
				
				// 게시물 파일 정보 로딩
				if(data.kwsQestnFiles && data.kwsQestnFiles.length > 0 && data.kwsQestnFiles[0] && data.kwsQestnFiles[0].kwsFile) {
					var file = data.kwsQestnFiles[0].kwsFile;
					$(".fileList input[name=fileNo]", that.selector).val(file.fileNo);
					$(".fileList .orignlFileNm", that.selector).text(file.orignlFileNm);
					$(".fileList", that.selector).show();
				}
				
				$(that.selector).dialog("open");
			}
			else {
				alert("게시물 정보를 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("게시물 정보를 불러오는데 실패했습니다.");
		});
		
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		that.clear();
		$(that.selector).dialog("close");
	}
	
};