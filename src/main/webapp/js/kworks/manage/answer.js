$(function() {
	answerObj.init();
});

/**
 * 묻고답하기-답변 게시판 객체
 * @type {Object}
 */
var answerObj = {
		
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
		that.modifyDialog.init(that);
		that.bindEvents();
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 묻고답하기-답변 게시판 항목 선택
		$("tbody tr", that.selector).click(function() {
			var element = $(this);
			var qestnNo = element.attr("data-qestn-no");
			that.modifyDialog.close();
			that.modifyDialog.open(qestnNo);
			$(element).addClass("on");
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
		$("tbody tr", that.selector).removeClass("on");
	},

		
};

/**
 * 묻고답하기-답변 게시판 답변 수정 팝업
 * @type {Object}
 */
answerObj.modifyDialog = {
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_answer_modify",
		
	/**
	 * 부모 객체 - 묻고답하기-답변 게시판 객체
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 묻고답하기-답변 게시판 번호
	 * @type {Object}
	 */
	answerNo : null,
		
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
	      	buttons: {
		    	"저장" : function() {
		    		that.save();
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
					if(name == "answerCn") {
						// textarea 의 내용이 아닌 ckeditor 의 내용 입력
						obj.value = CKEDITOR.instances.txa_answer_modify_cn.getData();
						if(!obj.value) {
							alert("답변을 입력하여 주십시오.");
							return false;
						}
					}
				}
			},
			success : function(result) {
				if(result && result.rowCount && result.rowCount == 1) {
					that.parent.search();
				}
				else {
					alert("답변 저장에 실패했습니다.");
				}
			}
		});
		
		// 편집기 적용
		CKEDITOR.replace( 'txa_answer_modify_cn',
		{
			customConfig: CONTEXT_PATH + '/js/kworks/common/ckeditorconfig.js'
		});
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		
		that.parent.clearSelected();
		that.answerNo = null;
		
		$("input[name=qestnNo]", that.selector).val("");
		
		$(".qestnSj", that.selector).html("");
		$(".qestnCn", that.selector).html("");
		$(".wrterId", that.selector).html("");

		$(".div_file_list", that.selector).hide();
		
		$(".div_answer .answerCn", that.selector).html("");
		$(".div_answer", that.selector).hide();

		$("textarea[name=answerCn]", that.selector).text("");
		CKEDITOR.instances.txa_answer_modify_cn.setData("");
	},
	
	/**
	 * 답변 저장
	 */
	save : function() {
		var that = this;
		if(that.answerNo) {
			that.modify();
		}
		else {
			that.add();
		}
	},
	
	/**
	 * 답변 추가
	 */
	add : function() {
		var that = this;
		var url = restUtils.getResUrl() + "add.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 답변 수정
	 */
	modify : function() {
		var that = this;
		var url = restUtils.getResUrl() + that.answerNo + "/modify.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 열기
	 * @param {Number} qestnNo 게시물 번호
	 */
	open : function(qestnNo) {
		var that = this;
		that.clear();
		
		$("input[name=qestnNo]", that.selector).val(qestnNo);
		var url = restUtils.getResUrl() + qestnNo + "/select.do";
		
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;

				// 게시물 정보 로딩
				$(".qestnSj", that.selector).html(data.qestnSj);
				$(".qestnCn", that.selector).html(data.qestnCn);
				$(".wrterId", that.selector).html(data.wrterId);
				
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
					that.answerNo = data.kwsAnswer.answerNo;
					CKEDITOR.instances.txa_answer_modify_cn.setData(data.kwsAnswer.answerCn);
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