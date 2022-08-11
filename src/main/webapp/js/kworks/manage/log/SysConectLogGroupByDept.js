$(function() {
	GroupByDeptObj.init();
});

/*
 * 부서별 접속 통계 객체
 */
var GroupByDeptObj = {
	
	init : function() {
		GroupByDeptObj.bindEvent();
	},
	
	/*
	 * 이벤트 바인딩
	 */
	bindEvent : function() {
		
		var that = this;
		// 검색버튼 클릭이벤트
		$("a[id='search']").on("click", function() {
			$("form").submit();
		});
	},
	
	/*
	 * 페이지 이동
	 */
	goPage : function(pgNo){
		$("#pageIndex").val(pgNo);
		$("form").submit();
	}
};