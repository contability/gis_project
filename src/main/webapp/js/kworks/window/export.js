/**
 * 내보내기 객체
 * @type {Object}
 */
windowObj.exportObj = {
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,

	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "서약서",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "Export",
	
	/**
	 * 사용자 명
	 */
	userName : null,
	
	/**
	 * 부서 명
	 */
	deptName : null,
	
	/**
	 * 완료 시 실행할 함수
	 * @type {Function}
	 */
	onSubmit : null,
	
	exportType : null,
	
	/**
	 * 열기
	 * @param options 옵션
	 */
	open : function(options) {
		var that = this;
		var url = CONTEXT_PATH + "/window/export/page.do";
		var windowOptions = {
			width : 730,
			height : 490,
			modal : true,
			onClose : function() {
				that.close();
			}
		};
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			// 현재 객체에 전달되는 값 적용 (onSubmit)
			if(options) {
				that.exportType = options.exportType;
				$.extend(that, options);
			}
			
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
		
		// 부서명
		$(".deptName", that.selector).text(that.deptName);
		
		// 사용자명
		$(".userName", that.selector).text(that.userName);
		
		// 출력명 (내보내기 명)
		$(".export_name", that.selector).textbox({
			required : true,
			width : 200,
			prompt : "출력명을 입력하십시오."
		});
		
		// 출력 사유
		$('.export_resn', that.selector).textbox({
			required : true,
			width : 602,
			height : 50,
			multiline : true,
			prompt : '출력 사유를 입력하십시오.'
		});
		
		if(that.exportType.indexOf('IMAGE') > -1){
			$('.export_resn', that.selector).textbox({
				required: false, 
				prompt : ''
			});
		}
		
		// 데이터 분류
		$(".export_data_se", that.selector).combobox({
			editable : false, 
			width : 120
		});
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		/// 출력 사유 100자 막기
		$(".export_resn", that.selector).keyup(function() {
			var element = $(this);
			var value = element.val();
			if(value.length > 100) {
				element.val(value.substring(0, 100));
			}
		});
		
		/// 내보내기 요청
		$(".a_confirm", that.selector).click(function() {
			that.submit();
			return false;
		});
		
		/// 취소
		$(".a_cancel", that.selector).click(function() {
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
		if(!$(".export_name", that.selector).textbox("isValid")) {
			$.messager.alert(that.TITLE, "출력명을 입력하여 주십시오.");
			return;
		}
		
		if(that.exportType.indexOf('IMAGE') < 0 && !$('.export_resn', that.selector).textbox('isValid')){
			$.messager.alert(that.TITLE, '출력 사유를 입력하여 주십시오.');
			return false;
		}
		
		if(!$("#chk_export_security_confirm").is(":checked")) {
			$.messager.alert(that.TITLE, "내보내기 요청을 위해서는 서약서 동의가 필요합니다. 서약서 동의를 체크하여 주십시오.");
			return;
		}
		
		var exportNm = $(".export_name", that.selector).textbox("getValue");
		var exportDtaSe = $(".export_data_se", that.selector).combobox("getValue");
		var exportResn = $(".export_resn", that.selector).val();
		
		var params = {
			exportNm : exportNm,
			exportDtaSe : exportDtaSe,
			exportResn : exportResn
		};
		
		if(that.onSubmit) {
			that.onSubmit(params);
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