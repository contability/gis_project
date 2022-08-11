windowObj.bookMarkObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,

	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "북마크 추가",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "BookMark",
	
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
		var url = CONTEXT_PATH + "/window/bookMark/page.do";
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
		
		// 주제도 명 입력
		$("#bkmkNm", that.selector).textbox({
			required : true,
			width : 200,
			prompt : "북마크명을 입력하십시오."
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
		if(!$("#bkmkNm", that.selector).textbox("isValid")) {
			$.messager.alert(that.TITLE, "북마크 명칭을 입력하여 주십시오.", null, function() {
				$("#bkmkNm", that.selector).combobox("textbox").focus();
			});
			return;
		}
		
		var center = mapObj.getMap().getCenter();
		var transformCenter = gisObj.toTransformSystem(new ol.geom.Point(center)).getCoordinates();
		
		var centerX = transformCenter[0];
		var centerY = transformCenter[1];
		var sc = parseInt(mapObj.getMap().getScale());
		var bkmkNm = $("#bkmkNm", that.selector).textbox("getValue");
		var params = {
			bkmkNm : bkmkNm,
			centerX : centerX,
			centerY : centerY,
			sc : sc
		};
		
		var url = CONTEXT_PATH + "/rest/bookMark/add.do";
		$.post(url, params).done(function(response) {
			if(!response.errMsg){
				if(response && response.rowCount) {
					$.messager.alert(that.TITLE, "북마크가 추가 되었습니다.");
					if(that.onSubmit) {
						that.onSubmit();
					}
					that.close();
				}
				else {
					$.messager.alert(that.TITLE, "북마크 추가에 실패 했습니다.");
				}
			} else {
				alert(response.errMsg);
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "북마크 추가에 실패 했습니다.");
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