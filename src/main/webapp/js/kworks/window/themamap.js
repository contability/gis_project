windowObj.themamapObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,

	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "주제도 추가",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "Themamap",
	
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
		var url = CONTEXT_PATH + "/window/themamap/page.do";
		var windowOptions = {
			width : 320,
			height : 180,
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
		
		// 주제도 명 입력
		$("#themamapNm", that.selector).textbox({
			required : true,
			width : 200,
			prompt : "주제도명을 입력하십시오."
		});
		
		// 주제도 설명 입력
		$("#themamapDc", that.selector).textbox({
			required : true,
			width : 200,
			prompt : "주제도 설명을 입력하십시오."
		});
		
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 등록
		$(".a_add", that.selector).click(function() {
			that.add();
			return false;
		});
		
		// 취소
		$(".a_cancel", that.selector).click(function() {
			that.close();
			return false;
		});
	},
	
	/**
	 * 등록
	 */
	add : function() {
		var that = this;
		
		// 필수 입력 값 체크
		if(!$("#themamapNm", that.selector).textbox("isValid")) {
			$.messager.alert(that.TITLE, "주제도 명칭을 입력하여 주십시오.", null, function() {
				$("#themamapNm", that.selector).combobox("textbox").focus();
			});
			return;
		}
		if(!$("#themamapDc", that.selector).textbox("isValid")) {
			$.messager.alert(that.TITLE, "주제도 설명을 입력하여 주십시오.", null, function() {
				$("#themamapDc", that.selector).combobox("textbox").focus();
			});
			return;
		}
		
		var themamapNm = $("#themamapNm", that.selector).textbox("getValue");
		var themamapDc = $("#themamapDc", that.selector).textbox("getValue");
		
		var params = {
			themamapNm : themamapNm,
			themamapDc : themamapDc
		};
		
		themamapObj.add(params).done(function(response) {
			if(response && response.rowCount) {
				$.messager.alert(that.TITLE, "주제도가 추가 되었습니다.");
				if(that.onSubmit) {
					that.onSubmit();
				}
				that.close();
			}
			else {
				$.messager.alert(that.TITLE, "주제도에 실패 했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "주제도에 실패 했습니다.");
		});
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