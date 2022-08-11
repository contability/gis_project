$(function() {
	GroupBySysObj.init();
});

/*
 * 부서별 접속 통계 객체
 */
var GroupBySysObj = {
	
	init : function() {
		GroupBySysObj.bindEvent();
	},
	
	/*
	 * 이벤트 바인딩
	 */
	bindEvent : function() {
		// 검색버튼 클릭이벤트
		$("a[id='search']").on("click", function() {
			$("form").submit();
		});
	}
};