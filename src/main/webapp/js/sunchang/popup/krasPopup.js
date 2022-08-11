$(function() {
	jqueryUtils.configAjax();
	
	// krasObj 일부 재정의
	krasObj.close = function() {
		var that = this;
		that.pnu = null;
		that.restrictYn = "N";
		that.retryCount = 0;
		for(var key in that.data) {
			that.data[key] = null;
		}
		that.hideLoadingBar();
		opener.krasObj.krasPopup.popup.close();
		that.selector = null;
		return false;
	};
	
	// krasObj 초기화
	$('.landLoc').text(pnuObj.getAddr(PNU, true));
	krasObj.pnu = PNU;
	krasObj.initUi();
	krasObj.bindEvents();
	krasObj.searchBass();
	
	// 닫기 이벤트 함수 재 정의
	$(".a_close").off();
	$(".a_close").click(function() {
		opener.highlightObj.removeFeatures(opener.quickObj.CLASS_NAME);
		krasObj.close();
	});
	
	// 세움터 연계 여부
	if(CONTACT.EAIS_USE_AT == 'true') {
		krasObj.searchBildngPrmisnRegstrList();
	}
	
	// 첫번째 탭 선택
	$(".div_kras_tab_list li a:first").trigger("click");
	
	// 2021.08.05 정신형
	// 팝업창 스크롤
	$(".div_window_kras").parent().css("height","100%");
});