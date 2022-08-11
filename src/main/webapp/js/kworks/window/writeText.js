windowObj.writeTextObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,

	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "텍스트 입력",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "WriteText",
	
	/**
	 * 완료 시 실행할 함수
	 * @type {Function}
	 */
	onSubmit : null,
	
	/**
	 * 열기
	 * @param options 옵션
	 */
	open : function(options) {
		var that = this;
		var url = CONTEXT_PATH + "/window/writeText/page.do";
		var windowOptions = {
			width : 320,
			height : 130,
			modal : true,
			onClose : function() {
				that.close();
			}
		};
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
			
			// 현재 객체에 전달되는 값 적용 (onSubmit)
			if(options) {
				$.extend(that, options);
			}
		});
		that.selector = "#" + id;
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		$("#txt_draw_text", that.selector).textbox({
			required : true,
			width : 200,
			prompt : "텍스트를 입력하십시오."
		});
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		/// 등록
		$(".a_register", that.selector).click(function() {
			that.submit();
			return false;
		});
		
		// 취소
		$(".a_cancel", that.selector).click(function() {
			if(that.onSubmit) {
				that.onSubmit(null);
			}
			that.close();
			return false;
		});
	},
	
	/**
	 * 제출
	 */
	submit : function() {
		var that = this;
		
		// 필수 입력 값 체크
		if(!$("#txt_draw_text", that.selector).textbox("isValid")) {
			$.messager.alert(that.TITLE, "텍스트를 입력하여 주십시오.");
			return;
		}
		
		var text = $("#txt_draw_text", that.selector).textbox("getValue");
		if(that.onSubmit) {
			that.onSubmit(text);
		}
		that.close();
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		that.onSubmit = null;
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		that.clear();
		windowObj.removeWindow(that.selector);
	}
		
};