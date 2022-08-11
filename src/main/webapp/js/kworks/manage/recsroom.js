$(function() {
	recsroomObj.init();
});

/**
 * 자료실 객체
 * @type {Object}
 */
var recsroomObj = {
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_recsroom_list",
		
	/**
	 * 초기화 
	 */
	init : function() {
		var that = this;
		that.addDialog.init(that);
		that.modifyDialog.init(that);
		that.bindEvents();
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 자료실 항목 선택
		$(".tbl_recsroom_list tbody tr", that.selector).click(function() {
			var element = $(this);
			var recsroomNo = element.attr("data-recsroom-no");
			that.openModifyDialog(recsroomNo);
			$(element).addClass("on");
		});

		// 자료실 등록 창 열기
		$(".a_new_recsroom", that.selector).click(function() {
			that.openAddDialog();
	    });
	},
	
	/**
	 * 검색
	 * @param {Number} pageIndex
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
		$(".tbl_recsroom_list tbody tr", that.selector).removeClass("on");
	},
	
	/**
	 * 팝업 창 닫기
	 */
	closeDialog : function() {
		var that = this;
		that.modifyDialog.close();
		that.addDialog.close();
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
	 * @param qestnNo
	 */
	openModifyDialog : function(qestnNo) {
		var that = this;
		that.closeDialog();
		that.modifyDialog.open(qestnNo);
	}
		
};

/**
 * 자료실 등록 팝업
 * @type {Object}
 */
recsroomObj.addDialog = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_recsroom_add",
	
	/**
	 * 부모 객체 - 자료실 객체
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
		
		// 자료실 등록 다이어로그 설정
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
					if(name == "recsroomSj") {
						if(!obj.value) {
							alert("제목을 입력하여 주십시오.");
							form.find("input[name=" + name + "]").focus();
							return false;
						}
					}
					else if(name == "recsroomCn") {
						// textarea 의 내용이 아닌 ckeditor 의 내용 입력
						obj.value = CKEDITOR.instances.txa_recsroom_add_cn.getData();
					}
				}
			},
			success : function(result) {
				if(result && result.rowCount && result.rowCount == 1) {
					that.parent.search();
				}
				else {
					alert("자료실 등록에 실패했습니다.");
				}
			}
		});
		
		// 편집기 적용
		CKEDITOR.replace( 'txa_recsroom_add_cn',
		{
			customConfig: CONTEXT_PATH + '/js/kworks/cmmn/ckeditorconfig.js'
		});
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		
		$("input[name=recsroomSj]", that.selector).val("");
		$("textarea[name=recsroomCn]", that.selector).text("");
		CKEDITOR.instances.txa_recsroom_add_cn.setData("");
		$(".span_file", that.selector).html('<input type="file" name="recsroomFile" value="" />');
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
 * 자료실 수정 팝업
 * @Object
 */
recsroomObj.modifyDialog = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_recsroom_modify",
		
	/**
	 * 부모 객체 - 자료실 객체
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 자료실 번호
	 * @type {Object}
	 */
	recsroomNo : null,
		
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
		
		// 자료실 수정 다이어로그 설정
		$(that.selector).dialog({
			autoOpen: false,
			width: 700,
	     	height: 630,
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
			beforeSubmit : function(arr, form) {
				for(var i=0, len=arr.length; i < len; i++) {
					var obj = arr[i];
					var name = obj.name;
					
					// 필수 입력 체크
					if(name == "recsroomSj") {
						if(!obj.value) {
							alert("제목을 입력하여 주십시오.");
							form.find("input[name=" + name + "]").focus();
							return false;
						}
					}
					else if(name == "recsroomCn") {
						// textarea 의 내용이 아닌 ckeditor 의 내용 입력
						obj.value = CKEDITOR.instances.txa_recsroom_modify_cn.getData();
					}
				}
			},
			success : function(result) {
				if(result && result.rowCount && result.rowCount == 1) {
					that.parent.search();
				}
				else {
					alert("자료실 수정에 실패했습니다.");
				}
			}
		});
		
		// 편집기 적용
		CKEDITOR.replace( 'txa_recsroom_modify_cn',
		{
			customConfig: CONTEXT_PATH + '/js/kworks/cmmn/ckeditorconfig.js'
		});
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 자료실 파일 삭제
		$(".fileList li a.a_remove_file", that.selector).click(function() {
			var fileNo = $(".fileList input[name=fileNo]", that.selector).val();
			if(fileNo != "") {
				$(".fileList input[name=deleteFileNo]", that.selector).val(fileNo);
				$(".fileList", that.selector).hide();
			}
		});
		
		// 자료실 파일 추가 시 기존 파일 삭제
		$(".span_file", that.selector).on("change", "input[name=recsroomFile]", function() {
			$(".fileList li a.a_remove_file", that.selector).trigger("click");
		});
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		
		that.parent.clearSelected();
		that.recsroomNo = null;
		$("input[name=recsroomSj]", that.selector).val("");
		$("textarea[name=recsroomCn]", that.selector).text("");
		CKEDITOR.instances.txa_recsroom_modify_cn.setData("");
		$(".fileList input[name=fileNo]", that.selector).val("");
		$(".fileList input[name=deleteFileNo]", that.selector).val("");
		$(".fileList .orignlFileNm", that.selector).text("");
		$(".span_file", that.selector).html('<input type="file" name="recsroomFile" value="" />');
		$(".fileList", that.selector).hide();
	},
	
	/**
	 * 수정
	 */
	modify : function() {
		var that = this;
		var url = restUtils.getResUrl() + that.recsroomNo + "/modify.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 삭제
	 */
	remove : function() {
		var that = this;
		var url = restUtils.getResUrl() + that.recsroomNo + "/remove.do";
		$.post(url).done(function(result) {
			if(result && result.rowCount && result.rowCount == 1) {
				that.parent.search(1);
			}
			else {
				alert("자료실 삭제에 실패했습니다.");
			}
		}).fail(function() {
			alert("자료실 삭제에 실패했습니다.");
		});
	},
	
	/**
	 * 열기
	 * @param {Number} recsroomNo 자료실 번호
	 */
	open : function(recsroomNo) {
		var that = this;
		that.clear();
		
		that.recsroomNo = recsroomNo;
		var url = restUtils.getResUrl() + recsroomNo + "/select.do";
		
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;

				// 자료실 정보 로딩
				$("input[name=recsroomSj]", that.selector).val(data.recsroomSj);
				CKEDITOR.instances.txa_recsroom_modify_cn.setData(data.recsroomCn);
				
				// 자료실 파일 정보 로딩
				if(data.kwsRecsroomFiles && data.kwsRecsroomFiles.length > 0 && data.kwsRecsroomFiles[0] && data.kwsRecsroomFiles[0].kwsFile) {
					var file = data.kwsRecsroomFiles[0].kwsFile;
					$(".fileList input[name=fileNo]", that.selector).val(file.fileNo);
					$(".fileList .orignlFileNm", that.selector).text(file.orignlFileNm);
					$(".fileList", that.selector).show();
				}
				
				$(that.selector).dialog("open");
			}
			else {
				alert("자료실 정보를 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("자료실 정보를 불러오는데 실패했습니다.");
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