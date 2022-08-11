$(function() {
	errorLogObj.init();
});

var errorLogObj = {
	
	/**
	 * 선택자
	 */
	selector : "#div_error_list",
	
	/**
	 * 초기화
	 */	
	init : function() {
		var that = this;
		
		that.inqire.init(that);
		
		that.bindEvent();
		that.initTag();
	},
	
	/**
	 * 태그 초기화
	 */
	initTag : function () {
		$(".datepicker").datepicker( {
			dateFormat : "yy-mm-dd",
			showButtonPanel : true,
			closeText : "닫기",
			dayNamesMin : ['일', '월', '화', '수', '목', '금', '토'],
			monthNames : ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
			monthNamesShort : ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
			changeMonth : true,
			changeYear : true,
			minDate : new Date(2000, 1-1, 1),
			maxDate : new Date(2100, 1-1, 1-1)
		});
	},
	
	/**
	 * 이벤트 바인딩
	 */
	bindEvent : function() {
		
		var that = this;
		
		// 검색버튼 클릭이벤트
		$("a[id='search']").on("click", function() {
			$("#pageIndex").val(1);
			$("form", that.selector).submit();
		});
		
		// 목록 선택 이벤트
		$("tr",that.selector).on("click", function() {
			var element = $(this); 
			
			var errorNo = element.attr("error-no");
			that.openInqireDialog(errorNo);
			
			element.addClass("on");
		});
		
		// excel download
		$(".a_excelDownload_errorLog", that.selector).click(function() {
			that.excelDownload(1);
		});
	},
	
	/**
	 * 에러 상세정보 팝업 열기
	 */
	openInqireDialog : function(errorNo) {
		errorLogObj.inqire.open(errorNo);
	},
	
	/**
	 * 선택 제거
	 */
	clearSelected : function() {
		var that = this;
		$("tbody tr", that.selector).removeClass("on");
	},
	
	/**
	 * 페이지 이동
	 */
	search : function(pgNo){
		$("#pageIndex").val(pgNo);
		$("#searchDTO").submit();
	},
	
	/**
	 * 엑셀다운로드
	 * @param {Number} pageIndex 페이지 인덱스
	 */
	excelDownload : function(pageIndex) {
		var that = this;

		$("form", that.selector).attr("action", restUtils.getResUrl() + "excel.do");
		$("form", that.selector).submit();
		
		// 기존 검색 url로 변경
		$("form", that.selector).attr("action", restUtils.getResUrl() + "list.do");
	}
};

errorLogObj.inqire = {
	/**
	 * 선택자
	 */
	selector : "#div_error_inqire",
	
	parent : null,
	
	/**
	 * 초기화
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
		
		$(that.selector).dialog({
			autoOpen : false,
			width : 700,
			height: 620,
			buttons : {
				"닫기" : function() {
					that.close();
				}
			},
			close : function() {
				that.close();
			}
		});
		
		var textarea = $("textarea", that.selector);
		textarea.attr("rows","10");
		textarea.attr("cols","40");
		textarea.attr("readonly", true);
		textarea.attr("wrap", "soft");
	},
	
	/**
	 * 에러 로그 상세정보 팝업 정리 & 리스트 정리
	 */
	clear : function() {
		
		var that = this;
		
		$("label[name='errorNo']").html(null);
		$("label[name='errorDt']").html(null);
		$("textarea[name='errorMssage']").html(null);
		$("textarea[name='errorTrace']").html(null);
		
		that.parent.clearSelected();
	},
	
	/**
	 * 에러 로그 상세정보 팝업 열기
	 * @param errorNo
	 */
	open : function(errorNo) {
		var that = this;
		var url = restUtils.getResUrl() + "errorLog/" + errorNo +  "/View.do";
		
		that.close();
		
		$.ajax({
			url : url,
			type : "GET",
		}).done(function(data) {
			
			var result =  data.result;
			
			$("label[name='errorNo']").html(result.errorNo);
			$("label[name='errorDt']").html(data.errorDt);
			$("textarea[name='errorMssage']").html(result.errorMssage);
			$("textarea[name='errorTrace']").html(result.errorTrace);
			
			$(that.selector).dialog("open");
			
		}).fail(function() {
			alert("에러 상세정보를 가져오지 못했습니다.");
		});
	},
	
	/**
	 * 에러 로그 상세정보 팝업 닫기
	 */
	close : function() {
		var that = this;
		that.clear();
		$(that.selector).dialog("close");
	}
};