
$(function(){
	$("#connectBtn").one("click",function() {
		connectDialog.init();
	});	
});

/**
 * 접속통계 조회 팝업
 * @Object
 * @author admin
 */
connectDialog = {
		
	/**
	 * 선택자
	 * @String
	 */
	selector : "#div_connect_select",
		
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
		that.open();
	},
	
	/**
	 * UI 초기화
	 * 
	 */
	initUi : function() {
		var that = this;
		
		// 자료실 수정 다이어로그 설정
		$(that.selector).dialog({
			autoOpen: false,
	     	height: 200,
	      	width: 350,
	      	open : function(){
	      		$(this).parent().offset ( { top : 60 , left : 980 } );
	      	},
	      	close: function() {
	      		that.close();
	      		location.reload();
	      	}
	    });
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
		$(that.selector).dialog("close");
	}
		
};
