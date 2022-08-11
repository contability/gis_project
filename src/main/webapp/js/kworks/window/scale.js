/**
 * 축척
 * @type {Object}
 */
windowObj.scaleObj = {
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "축척 설정",
		
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "Scale",
	
	/**
	 * 열기
	 * @param options 옵션
	 */
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
			return;
		}
		
		var url = CONTEXT_PATH + "/window/scale/page.do";
		var windowOptions = {
			width : 270,
			height : 90,
			onClose : function() {
				that.close();
			}
		};
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
		});
		that.selector = "#" + id;
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 축척
		$(".txt_scale", that.selector).numberspinner({
			min : 0,
			max : 1000000,
			increment : 1000
		});
		$(".txt_scale", that.selector).numberspinner("setValue", mapObj.getMap().getScale());
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 축척 이동
		$(".a_move_scale", that.selector).click(function() {
			var scale = $(".txt_scale", that.selector).numberspinner("getValue");
			mapObj.getMap().moveToScale(scale);
			return false;
		});
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
	
	
};