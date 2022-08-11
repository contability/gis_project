windowObj.roadNameEditObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,

	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도로명 편집",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "RoadNameEdit",
	
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
		var url = CONTEXT_PATH + "/window/road/roadNameEdit/page.do";
		var windowOptions = {
			width : 320,
			height : 130,
			modal : true,
			onClose : function() {
				that.close();
			}
		};
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi(options.rdnNam);
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
	initUi : function(prompt) {
		var that = this;
		
		// 주제도 명 입력
		$("#txt_road_name_edit_value", that.selector).textbox({
			required : true,
			width : 200,
			prompt : prompt
		});
		
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 편집
		$(".a_modify", that.selector).click(function() {
			that.modify();
			return false;
		});
		
		// 취소
		$(".a_cancel", that.selector).click(function() {
			that.close();
			return false;
		});
	},
	
	/**
	 * 편집
	 */
	modify : function() {
		var that = this;
		
		// 필수 입력 값 체크
		if(!$("#txt_road_name_edit_value", that.selector).textbox("isValid")) {
			$.messager.alert(that.TITLE, "도로명을 입력하여 주십시오.", null, function() {
				$("#txt_road_name_edit_value", that.selector).combobox("textbox").focus();
			});
			return;
		}
		
		var rdnNam = $("#txt_road_name_edit_value", that.selector).textbox("getValue");
		var params = {
			rdnCde : that.rdnCde,
			rdnNam : rdnNam
		};
		
		var url = CONTEXT_PATH + "/rest/road/" + that.rdnCde + "/modify.do";
		$.post(url, params).done(function(response) {
			if(response && response.rowCount) {
				$.messager.alert(that.TITLE, "도로명이 편집 되었습니다.");
				if(that.onSubmit) {
					that.onSubmit(rdnNam);
				}
				that.close();
			}
			else {
				$.messager.alert(that.TITLE, "도로명 편집에 실패 했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "도로명 편집에 실패 했습니다.");
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