$(function() {
	
	portalObj.init();
	
});

/**
 * 포털 객체
 * @type {Object}
 */
var portalObj = {
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		
		that.initUi();
		that.bindEvents();
		
		noticeObj.selectDialog.init();
		recsroomObj.selectDialog.init();
		
		// 설문조사 사용 여부
		if(EXTENSIONS.IS_SURVEY){
			windowObj.surveyObj.init();
		}
		
		//공지사항 팝업 
		noticeObj.popUp.open(this);
		
		// 서약서 사용 여부
		if(EXTENSIONS.IS_PLEDGE){
			windowObj.pledgeObj.init();
		}
		
		
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		
		// 시스템이 6개 이상일 때만 스크롤 생성
		if($("#portal_sys_slider li").length > 5) {
			// 시스템 슬라이더
			$("#portal_sys_slider").bxSlider({
			    slideWidth: 166
			    , minSlides: 5
			    , maxSlides: 6
			    , moveSlides: 1
			    , slideMargin: 5
			    , nextText: ''
				, prevText: ''
			    , pager:false
			    
			});
			
			// .bx-prev 가 #bx-prev 안에 생성되어 이벤트 전파 현상으로 스택 공간부족 나는 현상 발생
			/// bxSlider 에 nextSelector, prevSelector 옵션 삭제하고 원래 생기는 버튼 숨김 처리
			$(".bx-prev").hide();
			$(".bx-next").hide();
		}
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		
		// 나의 시스템 바로기가
		$(".btn_go").click(function() {
			var sysId = $("#mySysSelect").val();
			if(sysId) {
				location.href = CONTEXT_PATH + "/main.do?sysId="+sysId;
			}
			else {
				alert("선택된 나의 시스템이 없습니다.");
			}
			return false;
		});
		
		// 권한 없는 시스템 선택 시 이벤트
		$(".a_sys_no_author").click(function() {
			alert("접근 권한이 없습니다.");
			return false;
		});
		
		/// 시스템이 6개 이상일 때만 버튼 이벤트 등록
		if($("#portal_sys_slider li").length > 5) {
			// 시스템 슬라이더 이전 슬라이더 보기
			$("#bx-prev").on("click",function(){
				$(".bx-prev").trigger("click");
				return false;
			});
			
			// 시스템 슬라이더 다음 슬라이더 보기
			$("#bx-next").on("click",function(){
				$(".bx-next").trigger("click");
				return false;
			});
		}
		
		// 공지사항 보기
		$(".a_notice").click(function() {
			var element = $(this);
			var noticeNo = element.attr("data-notice-no");
			noticeObj.selectDialog.open(noticeNo);	
			return false;
		});
		
		// 자료실 보기
		$(".a_recsroom").click(function() {
			var element = $(this);
			var a_recsroomNo = element.attr("data-recsroom-no");
			recsroomObj.selectDialog.open(a_recsroomNo);	
			return false;
		});
		
		
	}
		
};