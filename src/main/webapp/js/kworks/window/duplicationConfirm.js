windowObj.duplicationConfirmObj = {
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "중복 확인",
	
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "DuplicationConfirm",
	
	/**
	 * 열기
	 * @param options 옵션
	 */
	open : function(options) {
		var that = this;
		var url = CONTEXT_PATH + "/window/duplicationConfirm/page.do";
		var windowOptions = {
			width : 300,
			height : 165,
			modal : true,
			onClose : function() {
				that.close();
			} 	
		};
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function(params1) {
			that.bindEvents();
			// 현재 객체에 전달되는 값 적용 (onSubmit)
			if(options) {
				$.extend(that, options);
			}
		});
		that.selector = "#" + id;
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 새로구성 & 목록추가 & 닫기
		$(".div_tool a", that.selector).click(function() {
			var element = $(this);
			if(that.onSubmit) {
				var mode = element.attr("data-mode");
				that.onSubmit(mode);
				that.close();
			}
			return false;
		});
		
		// 닫기
		$(".a_spatial_close", that.selector).click(function() {
			that.close();
			return false;
		});
		
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		that.onSubmit = null;
		windowObj.removeWindow(that.selector);
	}	
		
};