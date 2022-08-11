$(document).ready(function(){
	indvdlzSysObj.init();
});
/**
 * 개별시스템 접속통계 
 */
indvdlzSysObj = {
		
		/*
		 * 초기화
		 */
		init : function(){
			var that = this;
			that.bindEvent();
			
			// 기본값 : 1개월 선택
			$(".date1month").trigger("click");
		},
		
		/*
		 * 이벤트 바인딩
		 */
		bindEvent : function() {
			var that = this;
			var $table = $('table.scroll'),
			$bodyCells = $table.find('tbody tr:first').children(),colWidth;
			
			var scrollWidth = 100; 
			if($bodyCells.length > 10){
				scrollWidth = $bodyCells.length * 10;
			}
			
			$('table.scroll').css('width', scrollWidth + '%');
			
			// Adjust the width of thead cells when window resizes
			$(window).resize(function() {
			    // Get the tbody columns width array
			    colWidth = $bodyCells.map(function() {
			        return $(this).width();
			    }).get();
			    
			    // Set the width of thead columns
			    $table.find('thead tr').children().each(function(i, v) {
			        $(v).width(colWidth[i]);
			    });    
			}).resize(); // Trigger resize handler
			
			$(".select_stats").on("change",function(){
				var val = $(".select_stats").val();
				if(val == "today"){
					$(location).attr('href', restUtils.getResUrl() + "list.do");
				}else if(val == "day"){
					$("form").attr("action",restUtils.getResUrl() + "listDay.do");
					$("form").submit();
				}else if(val == "month"){
					$("form").attr("action",restUtils.getResUrl() + "listMonth.do");
					$("form").submit();
				}else if(val == "week"){
					$("form").attr("action",restUtils.getResUrl() + "listWeek.do");
					$("form").submit();
				}
			});
			
			$(".a_search").click(function(){
				var url = "";
				var val = $(".select_stats").val();
				if(val == "today"){
					url = restUtils.getResUrl() + "list.do";
				}else if(val == "day"){
					url = restUtils.getResUrl() + "listDay.do";
				}else if(val == "month"){
					url = restUtils.getResUrl() + "listMonth.do";
				}else if(val == "week"){
					url = restUtils.getResUrl() + "listWeek.do";
				}
				
				$("form").attr("action",url);
				$("form").attr("method","post");
				$("form").submit();
			});
			
			$(".datepicker").datepicker({
				dateFormat:"yy-mm-dd"
			});
			
			$(".datepicker_yymm").datepicker({
				dateFormat:"yy-mm"
			});
			
			$(".date1month").click(function(){
				$("input[name=searchStartDt]").val(that.getMonth(-1));
				$("input[name=searchEndDt]").val(that.getToday());
			});
			$(".date3month").click(function(){
				$("input[name=searchStartDt]").val(that.getMonth(-3));
				$("input[name=searchEndDt]").val(that.getToday());
			});
			$(".date6month").click(function(){
				$("input[name=searchStartDt]").val(that.getMonth(-6));
				$("input[name=searchEndDt]").val(that.getToday());
			});
			$(".date12month").click(function(){
				$("input[name=searchStartDt]").val(that.getMonth(-12));
				$("input[name=searchEndDt]").val(that.getToday());
			});
			
			// excel download
			$(".a_excelDownload_indvdlzSys").click(function() {
				that.excelDownload();
			});
		},
		
		/*
		 * 금일 날짜 값을 문자열로 변환
		 */
		getToday : function(){
			var today = new Date();
			var year = today.getFullYear();
			var month = today.getMonth() + 1;
			var day = today.getDate();
			
			var todayStr = year + "-" + month + "-" + day;
			return todayStr;
		},
		
		/*
		 * 월값을 문자열로 변환
		 */
		getMonth : function(mon){
			var today = new Date();
			today.setMonth(today.getMonth() + mon);
			today.setDate(today.getDate() + 1);
			var year = today.getFullYear();
			var month = today.getMonth() + 1;
			var day = today.getDate();
			
			var todayStr = year + "-" + month + "-" + day;
			return todayStr;
		},
		
		/**
		 * 엑셀다운로드
		 * @param {Number} pageIndex 페이지 인덱스
		 */
		excelDownload : function() {
			if($(".select_stats").val() == "today") {
				$("form").attr("action", restUtils.getResUrl() + "todayIndvdlzSysConectStatsExcel.do");
				$("form").submit();
				
				// 기존 검색 url로 변경
				$("form").attr("action", restUtils.getResUrl() + "list.do");
			}
			else if($(".select_stats").val() == "day") {
				$("form").attr("action", restUtils.getResUrl() + "dayIndvdlzSysConectStatsExcel.do");
				$("form").submit();
				
				// 기존 검색 url로 변경
				$("form").attr("action", restUtils.getResUrl() + "listDay.do");
			}
			else if($(".select_stats").val() == "month") {
				$("form").attr("action", restUtils.getResUrl() + "monthIndvdlzSysConectStatsExcel.do");
				$("form").submit();
				
				// 기존 검색 url로 변경
				$("form").attr("action", restUtils.getResUrl() + "listMonth.do");
			}
			else if($(".select_stats").val() == "week") {
				$("form").attr("action", restUtils.getResUrl() + "weekIndvdlzSysConectStatsExcel.do");
				$("form").submit();
				
				// 기존 검색 url로 변경
				$("form").attr("action", restUtils.getResUrl() + "listWeek.do");
			}
		}
};