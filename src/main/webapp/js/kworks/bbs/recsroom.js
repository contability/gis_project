$(function() {
	recsroomObj.init();
});

/**
 * 자료실 객체
 * @Object
 */
var recsroomObj = {
		
	/**
	 * 선택자
	 * @String
	 */
	selector : "#div_recsroom_list",
	
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
		
		// 자료실 항목 선택
		$(".tbl_recsroom_list tbody tr", that.selector).click(function() {
			var element = $(this);
			var recsroomNo = element.attr("data-recsroom-no");
			
			// 조회 수 증가
			var rdCntElement = $(".rdCnt", element);
			var rdCnt = parseInt(rdCntElement.text()) + 1;
			rdCntElement.text(rdCnt);
			
			that.selectDialog.close();
			that.selectDialog.open(recsroomNo);
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
		$(".tbl_recsroom_list tbody tr", that.selector).removeClass("on");
	},
		
};

/**
 * 자료실 조회 팝업
 * @Object
 * @author admin
 */
recsroomObj.selectDialog = {
		
	/**
	 * 선택자
	 * @String
	 */
	selector : "#div_recsroom_select",
		
	/**
	 * 부모 객체 - 자료실 객체
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
		
		// 자료실 수정 다이어로그 설정
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
		$(".recsroomSj", that.selector).text("");
		$(".recsroomCn", that.selector).html("");
		
		$(".div_file_list", that.selector).hide();
		
	},
	
	/**
	 * 열기
	 * @param recsroomNo 자료실 번호
	 */
	open : function(recsroomNo) {
		var that = this;
		that.clear();
		
		// 포털화면에서도 같이 사용하기 위해서 CONTEXT_PATH 사용
		var url = CONTEXT_PATH + "/bbs/recsroom/" + recsroomNo + "/select.do";
		
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;

				// 자료실 정보 로딩
				$(".recsroomSj", that.selector).text(data.recsroomSj);
				$(".recsroomCn", that.selector).html(data.recsroomCn);
				
				// 자료실 파일 정보 로딩
				if(data.kwsRecsroomFiles && data.kwsRecsroomFiles.length > 0) {
					var tagStr = '';
					for(var i=0, len = data.kwsRecsroomFiles.length; i < len; i++) {
						var file = data.kwsRecsroomFiles[i].kwsFile;
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
