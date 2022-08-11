$(function(){
	ManagerLeftMenu.init();
});

/**
 * 관리자페이지 왼쪽메뉴 객체
 * @type {Object}
 */
var ManagerLeftMenu = {
	
	/**
	 * 초기화 
	 */
	init : function(){
		var that = this;
		
		// 과천시 재난현장 모바일 파일 다운로드 창
		$(".div_loading").hide();
		that.selectMeneu();
	},
	
	/*
	 * 현재 보이는 페이지를 확인하여 왼쪽메뉴를 bold 처리
	 */
	selectMeneu : function(){
		var menuPage = $("#menu_request_uri").val() ;
		$.each($("#ul_menu_list li a"),function(){
			var hrefStr = $(this).attr("href").substring(0, $(this).attr("href").indexOf(".do") + 3);
			if(menuPage == hrefStr){
				$(this).find("span").addClass("selected");
			}
		});
	}
};