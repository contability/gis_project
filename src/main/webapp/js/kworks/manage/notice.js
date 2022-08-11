$(function() {
	noticeObj.init();
});

/**
 * 공지사항 객체
 * @type {Object}
 */
var noticeObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_notice_list",
	
	/**
	 * 초기화 
	 */
	init : function() {
		var that = this;
		that.popupYN();
		that.addDialog.init(that);
		that.modifyDialog.init(that);
		that.bindEvents();
	},
	
	popupYN : function(){
		var that = this;
		
		var chkPopupTag = $('.chk_popupAt', that.selector);
		
		for(var i = 0; i < chkPopupTag.length; i++){
			
			var popupStart = null;
			var popupEnd = null;
			var spanYN = null;
			var childNodes = chkPopupTag[i].childNodes;
			
			for(var j = 0; j < childNodes.length; j++){
				
				if(childNodes[j].className){
					if(childNodes[j].className.indexOf('popupStart') > -1){
						popupStart = childNodes[j].value;
					}else if(childNodes[j].className.indexOf('popupEnd') > -1){
						popupEnd = childNodes[j].value;
					} 
				}else{
					if(childNodes[j].nodeName.indexOf('SPAN') > -1){
						spanYN = childNodes[j];
					}
				}
				
			}
			
			if(popupStart){
				var dateStr = that.getDateStr();
				var today = new Date(dateStr);
				
				var startDate = new Date(popupStart);
				
				if(today >= startDate){
					spanYN.innerHTML = 'Y';
					if(popupEnd){
						var endDate = new Date(popupEnd);
						if(today > endDate){
							spanYN.innerHTML = 'N';
						}
					}
				}
			}else{
				spanYN.innerHTML = 'N';
			}
		}
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 공지사항 항목 선택
		$(".tbl_notice_list tbody tr", that.selector).click(function() {
			var element = $(this);
			var noticeNo = element.attr("data-notice-no");
			that.openModifyDialog(noticeNo);
			$(element).addClass("on");
		});

		// 공지사항 등록 창 열기
		$(".a_new_notice", that.selector).click(function() {
			that.openAddDialog();
	    });
	},
	
	/**
	 * 선택 해제
	 */
	clearSelected : function() {
		var that = this;
		$(".tbl_notice_list tbody tr", that.selector).removeClass("on");
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
	 * @param notieceNo
	 */
	openModifyDialog : function(notieceNo) {
		var that = this;
		that.closeDialog();
		that.modifyDialog.open(notieceNo);
	},
	
	getDateStr : function(){
		var today = new Date();
		
		var year = today.getFullYear();
		var month = today.getMonth() + 1;
		var date = today.getDate();
		var dateStr = year + '-' + stringUtils.lpad('' + month, 2) + '-' + stringUtils.lpad('' + date, 2);
		
		return dateStr;
	},
	
	chkPopupYN : function(start, end, selector){
		var that = this;
		
		var tagNm = '';
		if(selector.indexOf('add') > -1){
			tagNm = '#sel_notice_add_popup_at';
		}else{
			tagNm = '#sel_notice_modify_popup_at';
		}
		
		var dateStr = that.getDateStr();
		var today = new Date(dateStr);
		
		if(start){
			var startDate = new Date(start);
			
			if(today >= startDate){
				if(end){
					var endDate = new Date(end);
					
					if(startDate <= endDate){
						$(tagNm, selector).val('Y');
						if(today > endDate){
							$(tagNm, selector).val('N');
						}
					}else{
						$(tagNm, selector).val('Y');
						$('input[name=popupEnd]', selector).val('');
						alert('종료일은 시작일 이후여야만 합니다.');
					}
				}else{
					$(tagNm, selector).val('Y');
				}
			}else if(today < startDate){
				if(end){
					var endDate = new Date(end);
					
					if(startDate > endDate){
						$('input[name=popupEnd]', selector).val('');
						$(tagNm, selector).val('N');
						alert('종료일은 시작일 이후여야만 합니다.');
					}
				}else{
					$(tagNm, selector).val('N');
				}
			}
		}else if(end){
			var endDate = new Date(end);
			
			if(today > endDate){
				$('input[name=popupEnd]', selector).val('');
				alert('종료일은 현재 날짜보다 이후여야만 합니다.');
				$(tagNm, selector).val('N');
			}else{
				$('input[name=popupStart]', selector).val(today.toISOString().substring(0,10));
				$(tagNm, selector).val('Y');
			}
		}else{
			$(tagNm, selector).val('N');
		}
	},
	
	changePopupYN : function(popupAt, selector){
		var that = this;
		
		var dateStr = that.getDateStr();
		
		if(popupAt.indexOf('Y') > -1){
			$('input[name=popupStart]', selector).val(dateStr);
			$('input[name=popupEnd]', selector).val('');
		}else{
			$('input[name=popupStart]', selector).val('');
			$('input[name=popupEnd]', selector).val('');
		}
	}
};

/**
 * 공지사항 등록 팝업
 * @type {Object}
 */
noticeObj.addDialog = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_notice_add",
	
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
		that.bindEvents();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 공지사항 등록 다이어로그 설정
		$(that.selector).dialog({
			autoOpen: false,
	     	height: 626,
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
					if(name == "noticeSj") {
						if(!obj.value) {
							alert("제목을 입력하여 주십시오.");
							form.find("input[name=" + name + "]").focus();
							return false;
						}
					}
					else if(name == "noticeCn") {
						// textarea 의 내용이 아닌 ckeditor 의 내용 입력
						obj.value = CKEDITOR.instances.txa_notice_add_cn.getData();
					}
				}
			},
			success : function(result) {
				if(result && result.rowCount && result.rowCount == 1) {
					that.parent.search();
				}
				else {
					alert("공지사항 등록에 실패했습니다.");
				}
			}
		});
		
		// 편집기 적용
		CKEDITOR.replace( 'txa_notice_add_cn',
		{
			customConfig: CONTEXT_PATH + '/js/kworks/cmmn/ckeditorconfig.js'
		});
	},
	
	bindEvents : function(){
		var that = this;
		
		$('#sel_notice_add_popup_at', that.selector).change(function(){
			var popupAt = $('#sel_notice_add_popup_at', that.selector).val();
			noticeObj.changePopupYN(popupAt, that.selector);
		});
		
		$('.popupDate', that.selector).change(function(){
			var start = $('input[name=popupStart]', that.selector).val();
			var end = $('input[name=popupEnd]', that.selector).val();
			noticeObj.chkPopupYN(start, end, that.selector);
		});
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		$("input[name=noticeSj]", that.selector).val("");
		$("textarea[name=noticeCn]", that.selector).text("");
		CKEDITOR.instances.txa_notice_add_cn.setData("");
		$(".span_file", that.selector).html('<input type="file" name="noticeFile" value="" />');
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
 * 공지사항 수정 팝업
 * @type {Object}
 */
noticeObj.modifyDialog = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_notice_modify",
		
	/**
	 * 부모 객체 - 공지사항 객체
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 공지사항 번호
	 * @type {Object}
	 */
	noticeNo : null,
		
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
		
		// 공지사항 수정 다이어로그 설정
		$(that.selector).dialog({
			autoOpen: false,
			width: 700,
	     	height: 691,
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
					if(name == "noticeSj") {
						if(!obj.value) {
							alert("제목을 입력하여 주십시오.");
							form.find("input[name=" + name + "]").focus();
							return false;
						}
					}
					else if(name == "noticeCn") {
						// textarea 의 내용이 아닌 ckeditor 의 내용 입력
						obj.value = CKEDITOR.instances.txa_notice_modify_cn.getData();
					}
				}
			},
			success : function(result) {
				if(result && result.rowCount && result.rowCount == 1) {
					that.parent.search();
				}
				else {
					alert("공지사항 수정에 실패했습니다.");
				}
			}
		});
		
		// 편집기 적용
		CKEDITOR.replace( 'txa_notice_modify_cn',
		{
			customConfig: CONTEXT_PATH + '/js/kworks/cmmn/ckeditorconfig.js'
		});
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 공지사항 파일 삭제
		$(".fileList li a.a_remove_file", that.selector).click(function() {
			var fileNo = $(".fileList input[name=fileNo]", that.selector).val();
			if(fileNo != "") {
				$(".fileList input[name=deleteFileNo]", that.selector).val(fileNo);
				$(".fileList", that.selector).hide();
			}
		});
		
		// 공지사항 파일 추가 시 기존 파일 삭제
		$(".span_file", that.selector).on("change", "input[name=noticeFile]", function() {
			$(".fileList li a.a_remove_file", that.selector).trigger("click");
		});
		
		$('#sel_notice_modify_popup_at', that.selector).change(function(){
			var popupAt = $('#sel_notice_modify_popup_at', that.selector).val();
			noticeObj.changePopupYN(popupAt, that.selector);
		});
		
		$('.popupDate', that.selector).change(function(){
			var start = $('input[name=popupStart]', that.selector).val();
			var end = $('input[name=popupEnd]', that.selector).val();
			noticeObj.chkPopupYN(start, end, that.selector);
		});
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		that.parent.clearSelected();
		
		that.noticeNo = null;
		$("input[name=noticeSj]", that.selector).val("");
		$("textarea[name=noticeCn]", that.selector).text("");
		CKEDITOR.instances.txa_notice_modify_cn.setData("");
		$(".fileList input[name=fileNo]", that.selector).val("");
		$(".fileList input[name=deleteFileNo]", that.selector).val("");
		$(".fileList .orignlFileNm", that.selector).text("");
		$(".span_file", that.selector).html('<input type="file" name="noticeFile" value="" />');
		$(".fileList", that.selector).hide();
	},
	
	/**
	 * 수정
	 */
	modify : function() {
		var that = this;
		var url = restUtils.getResUrl() + that.noticeNo + "/modify.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 삭제
	 */
	remove : function() {
		var that = this;
		var url = restUtils.getResUrl() + that.noticeNo + "/remove.do";
		$.post(url).done(function(result) {
			if(result && result.rowCount && result.rowCount == 1) {
				that.parent.search(1);
			}
			else {
				alert("공지사항 삭제에 실패했습니다.");
			}
		}).fail(function() {
			alert("공지사항 삭제에 실패했습니다.");
		});
	},
	
	/**
	 * 열기
	 * @param {Number} noticeNo 공지사항 번호
	 */
	open : function(noticeNo) {
		var that = this;
		that.clear();
		
		that.noticeNo = noticeNo;
		var url = restUtils.getResUrl() + noticeNo + "/select.do";
		
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;

				// 공지사항 정보 로딩
				$("input[name=noticeSj]", that.selector).val(data.noticeSj);
				CKEDITOR.instances.txa_notice_modify_cn.setData(data.noticeCn);
				
				// 공지사항 파일 정보 로딩
				if(data.kwsNoticeFiles && data.kwsNoticeFiles.length > 0 && data.kwsNoticeFiles[0] && data.kwsNoticeFiles[0].kwsFile) {
					var file = data.kwsNoticeFiles[0].kwsFile;
					$(".fileList input[name=fileNo]", that.selector).val(file.fileNo);
					$(".fileList .orignlFileNm", that.selector).text(file.orignlFileNm);
					$(".fileList", that.selector).show();
				}
				
				$('input[name=popupStart]', that.selector).val(data.popupStart);
				$('input[name=popupEnd]', that.selector).val(data.popupEnd);
				
				noticeObj.chkPopupYN(data.popupStart, data.popupEnd, that.selector);
				
				$(that.selector).dialog("open");
			}
			else {
				alert("공지사항 정보를 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("공지사항 정보를 불러오는데 실패했습니다.");
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