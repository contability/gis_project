$(function() {
	$(".div_loading").hide();
	mobileExportObj.init();
});


/**
 * 재난현장 모바일
 */
var mobileExportObj = {

	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_mobile_export_list",

	/**
	 * 초기화 
	 */
	init : function() {
		var that = this;
		
		that.bindEvents();
		that.initTag();
	},

	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 검색
		$(".a_search", that.selector).click(function() {
			that.search(1);
	    });
		
		// 다운로드
		$(".a_download", that.selector).click(function() {
			that.zipDownload();
			return false;
		});
	},
	
	/**
	 * 달력
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
	 * 검색
	 * @param {Number} pageIndex 페이지 인덱스
	 */
	search : function(pageIndex) {
		var that = this;
		
		if(pageIndex) {
			$("#pageIndex").val(pageIndex);
		}
		$("form", that.selector).submit();
	},
	
	/**
	 * 파일 다운로드
	 */
	zipDownload : function() {
		var that = this;
		
		var url = CONTEXT_PATH + "/manage/mobileExport/download.do";
		$(".div_loading").show();
		
		var xmlHttpRequest = new XMLHttpRequest();
		xmlHttpRequest.open("GET", url, true);
		xmlHttpRequest.setRequestHeader("Content-Type", "application/json");
		xmlHttpRequest.responseType = 'blob';
		xmlHttpRequest.onload = function(event) {
			if (xmlHttpRequest.status == 200) {
				var disposition = xmlHttpRequest.getResponseHeader('content-disposition');
				var idx = disposition.indexOf("filename=") + 9;
				var fileName = disposition.substring(idx, disposition.length);
				var cut = fileName.indexOf(";");
				if (cut > 0 ) {
					fileName = fileName.substring(0, cut);
				}
				
				var blob = xmlHttpRequest.response;
				if (window.navigator.msSaveOrOpenBlob) {
					window.navigator.msSaveBlob(blob, fileName);
				}
				else {
					var link = document.createElement('a');
					var downloadUrl = URL.createObjectURL(blob);
					link.href = downloadUrl;
					link.download = fileName;
					document.body.appendChild(link);
					link.click();
					window.URL.revokeObjectURL(downloadUrl);
				}				
			}
			else {
				alert("다운로드에 실패 했습니다.");
			}
			$(".div_loading").hide();
		};
		xmlHttpRequest.send();
	}
};