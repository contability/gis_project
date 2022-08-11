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
		that.selectDialog.init(that);
		that.bindEvents();
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
			
			// 조회 수 증가
			var rdCntElement = $(".rdCnt", element);
			var rdCnt = parseInt(rdCntElement.text()) + 1;
			rdCntElement.text(rdCnt);
			
			that.selectDialog.close();
			that.selectDialog.open(noticeNo);
			$(element).addClass("on");
		});
	},
	
	/**
	 * 검색
	 * @param pageIndex
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
		$(".tbl_notice_list tbody tr", that.selector).removeClass("on");
	},

		
};

/**
 * 공지사항 조회 팝업
 * @Object
 * @author admin
 */
noticeObj.selectDialog = {
		
	/**
	 * 선택자
	 * @String
	 */
	selector : "#div_notice_select",
		
	/**
	 * 부모 객체 - 공지사항 객체
	 * @Object
	 */
	parent : null,
	
	/**
	 * 초기화
	 * @param parent  부모 객체
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
		
		// 공지사항 수정 다이어로그 설정
		$(that.selector).dialog({
			autoOpen: false,
	     	height: 600,
	      	width: 700,
	      	buttons: {
		        "닫기" : function() {
		        	that.close();
		        }
			},
	      	close: function() {
	      		that.close();
	      	}
	    });
	},
	
	/**
	 * 초기화
	 */
	clear : function() {
		var that = this;
		
		that.parent.clearSelected();
		$(".noticeSj", that.selector).text("");
		$(".noticeCn", that.selector).html("");
		
		$(".div_file_list", that.selector).hide();
		
	},
	
	/**
	 * 열기
	 * @param noticeNo 공지사항 번호
	 */
	open : function(noticeNo) {
		var that = this;
		that.clear();

		// 포털화면에서도 같이 사용하기 위해서 CONTEXT_PATH 사용
		var url = CONTEXT_PATH + "/bbs/notice/" + noticeNo + "/select.do";
		
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;

				// 공지사항 정보 로딩
				$(".noticeSj", that.selector).text(data.noticeSj);
				$(".noticeCn", that.selector).html(data.noticeCn);
				
				// 공지사항 파일 정보 로딩
				if(data.kwsNoticeFiles && data.kwsNoticeFiles.length > 0) {
					var tagStr = '';
					for(var i=0, len = data.kwsNoticeFiles.length; i < len; i++) {
						var file = data.kwsNoticeFiles[i].kwsFile;
						tagStr += '<li>';
						tagStr += '<a href="' + CONTEXT_PATH + '/cmmn/file/' + file.fileNo + '/download.do" >';
						tagStr += '<span>' + file.orignlFileNm + "</span>";
						tagStr += '</a>';
						tagStr += '</li>';
					}
					$(".fileList", that.selector).html(tagStr);
					$(".div_file_list", that.selector).show();
				}
				
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


// 공지사항 팝업

// 2021.11.26
// 정신형
noticeObj.popUp = {
	CLASS_NAME : "NoticePopup",
		
	TITLE : "공지사항 팝업 조회",	
	
	parent : null,
	
	index : 0,
	
	initUi : function(){
		var that = this;
		
		$(that.selector).dialog({
			autoOpen : open,
			height : 600,
			width : 700,
			buttons : {
				"닫기" : function(){
					that.close();
				}
			},
			close : function(){
				that.close();
			}
		});
	},
	
	open : function(parent){
		var that = this;
		that.parent = parent;
		
		var url = CONTEXT_PATH + "/bbs/notice/noticePopup.do";
		
		$.ajax({
			type:'GET',
			dataType:'json',
			url:url,
			success:function(rs){
				that.createTag(rs.result);
				that.bindEvents();
			}
		});
	},
	
	bindEvents : function(){
		var that = this;
		
		$('.closeToday_a').click(function(){
			$(this).children('input').prop('checked', true);
			
			var id = $(this).attr('id');
			var closeKey = 'closeToday_' + id;
			
			that.closeToday(closeKey);
			that.close(id);
		});
		
		$('.fileDownload').click(function(){
			var fileNo = $(this).attr('fileNo');
			
			location.href = CONTEXT_PATH + '/cmmn/file/' + fileNo + '/download.do';
		});
			
	},
	
	closeToday : function(closeKey){
		var that = this;
		that.setCookie(closeKey, 'Y', 1);
	},
	
	setCookie : function(closeKey, closeValue, expires){
		var todayDate = new Date();
		todayDate.setDate(todayDate.getDate() + expires);
		document.cookie = closeKey + '=' + escape(closeValue) + '; path=/; expires=' + todayDate.toGMTString() + ';';
	},
	
	getCookie : function(cooKey){
		var cookieStr = document.cookie;
		var isClosed = false;
		
		if(cookieStr){
			var splitCookie = cookieStr.split(';');
			
			for(var i = 0; i < splitCookie.length; i++){
				while(splitCookie[i].charAt(0) == ' '){
					splitCookie[i] = splitCookie[i].substring(1);
				}
				
				if(splitCookie[i].indexOf(cooKey) != -1){
					if(splitCookie[i].substring(cooKey.length, cooKey.length+1) == 'Y'){
						isClosed = true;
						break;
					}else{
						isClosed = false;
					}
				}else{
					isClosed = false;
				}
			}
		}else{
			isClosed = false;
		}
		
		return isClosed;
	},
	
	popUp : function(id){
		var that = this;
		
		$('#' + id).dialog({
			autoOpen : true,
			width: 700,
			height: 620,
			buttons: {
				"닫기" : function(){
					that.close(id);
				}
			}
		});
		
		$('#'+id).parent().css("left",540 + (that.index * 30));
		$('#'+id).parent().css("top",120 + (that.index * 30));
		
		var aTagStr = '<a id=' + id + ' class="closeToday_a" href="#" style="text-align:left; padding-left:5px;">';
		aTagStr += '<input type="checkbox" id="closeToday_chkbx" style="vertical-align: baseline;">&nbsp오늘 하루 보지 않기';
		aTagStr += '</a>';
		
		$('#'+id).parent().children('.ui-dialog-buttonpane').prepend(aTagStr);
	},
	
	createTag : function(result){
		var that = this;
		
		var id = '';
		
		for(var i = 0; i < result.length; i++){
			var noticeData = result[i];
			var tagStr = '';
			id = 'div_notice_popUp_' + noticeData.noticeNo;
			
			var cooKey = 'closeToday_' + id + '=';
			
			if(!that.getCookie(cooKey)){
				tagStr += '<div id='+ id +' class="div_modal" title="공지사항">';
				tagStr += '<div>';
				tagStr += '<div>';
				tagStr += '<span class="title">제목</span>';
				tagStr += '<span class="content noticeSj">' + noticeData.noticeSj + '</span>';
				tagStr += '</div>';
				tagStr += '<div>';
				tagStr += '<span class="title">내용</span>';
				tagStr += '<span class="content noticeCn">' + noticeData.noticeCn + '</span>';
				tagStr += '</div>';
				if(noticeData.kwsNoticeFiles && noticeData.kwsNoticeFiles.length > 0 && noticeData.kwsNoticeFiles[0].kwsFile){
					var file = noticeData.kwsNoticeFiles[0].kwsFile;
					tagStr += '<div class="div_file_list">';
					tagStr += '<span class="title">첨부파일</span>';
					tagStr += '<div class="content"><ul class="fileList">';
					tagStr += '<li>';
					tagStr += '<a class="fileDownload" href="#" fileNo="' + file.fileNo + '">' + file.orignlFileNm + '</a>';
					tagStr += '</li>';
					tagStr += '</ul>';
					tagStr += '</div>';
					tagStr += '</div>';
				}
				tagStr += '</div>';
				tagStr += '</div>';
				
				$("body").append(tagStr);
				
				that.popUp(id);
				
				that.index++;
			}
		}
	},
	
	close : function(id){
		var that =this;
		//that.clear();
		$('#' + id).dialog("close");
	}
};
