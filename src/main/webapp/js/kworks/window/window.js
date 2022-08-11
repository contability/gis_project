/**
 * 윈도우 객체
 * @type {Object}
 */
var windowObj = {
		
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "kworks_window",
		
	/**
	 * 인덱스
	 * @type {Number}
	 */
	index : 0,

	/**
	 * 윈도우 생성
	 * @param {String} className 클래스명
	 * @param {String} title 명칭
	 * @param {String} url URL
	 * @param {Object} params 파라미터 목록
	 * @param {Object} opt_options 옵션 목록
	 * @param {Function} 콜백 함수
	 */
	createWindow : function(className, title, url, params, opt_options, callback) {
		var that = this;
		var id = that.CLASS_NAME + "_" + (that.index++);
		
		var options = $.extend({}, opt_options);
		if(options.right) {
			var offset = options.width ? options.width : 0;
			offset += options.right;
			
			var left = $(window).width() - offset;
			options.left = left;
		}
		
		var divElement = $('<div id="' + id + '" title="' + title + '" class="' + className + '"></div>');
		
		var containerSelector = options.containerSelector?options.containerSelector:"body";
		$(containerSelector).append(divElement);
		
		if(title == "운동시설" || title == "유희시설" 
			|| title == "휴게시설(점)" || title == "휴게시설(선)" || title == "휴게시설(면)"){
			$("#"+id).load(url, params, function() {
				$("#" + id).window($.extend({
					maximizable : false,
					minimizable : false,
					resizable : false,
					left : 320
				}, options));
				
				if(callback) {
					callback();
				}
			});
		} else {
			$("#"+id).load(url, params, function() {
				$("#" + id).window($.extend({
					maximizable : false,
					minimizable : false,
					resizable : false
				}, options));
				
				if(callback) {
					callback();
				}
			});
		}
		return id;
	},
	
	/**
	 * 윈도우 닫기
	 * @type {String} 선택자
	 */
	removeWindow : function(selector) {
		$(selector).window("destroy");
	}
		
};