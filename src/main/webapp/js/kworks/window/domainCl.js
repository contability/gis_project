windowObj.domainClObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,

	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도메인 설정",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "DomainCl",
	
	onSubmit : null,
	
	/**
	 * 열기
	 * @param options 옵션
	 */
	open : function(domnId, codeIdList, options) {
		var that = this;
		
		var url = CONTEXT_PATH + "/window/domainCl/page.do";
		var windowOptions = {
			width : 480,
			height : 600,
			modal : true,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.load(domnId, codeIdList);
			that.bindEvents();
			that.loadChecked();
			
			// 현재 객체에 전달되는 값 적용 (onSubmit)
			if(options) {
				$.extend(that, options);
			}
		});
		that.selector = "#" + id;
	},
	
	initUi : function() {
		var that = this;
		
		// 레이아웃
		$(".div_window_domain_cl", that.selector).layout({
			fit : true
		});
		
	},
	
	bindEvents : function() {
		var that = this;
		
		// 도메인 선택
		$(".ul_all_cls li input[type=checkbox]", that.selector).click(function() {
			that.loadChecked();
		});
		
		// 선택 완료
		$(".a_submit", that.selector).click(function() {
			var domnCodes = [];
			$(".ul_all_cls li input[type=checkbox]:checked", that.selector).each(function() {
				var element = $(this);
				var labelElement = element.parent().find("label");
				var codeId = element.attr("data-code-id");
				domnCodes.push({
					codeId : codeId,
					codeNm : labelElement.text()
				});
			});
			
			if(domnCodes.length > 0) {
				if(that.onSubmit) {
					that.onSubmit(domnCodes);
				}
				that.close();
			}
			else {
				$.messager.alert(that.TITLE, "한 개 이상의 도메인 코드를 선택하여 주십시오.");
			}
			return false;
		});
		
		// 닫기
		$(".a_close", that.selector).click(function() {
			that.close();
			return false;
		});
	},
	
	load : function(domnId, codeNameList) {
		var that = this;
		var tagStr = '';
		var domnCodes = domnCodeObj.getData(domnId);
		for(var i=0, len=domnCodes.length; i < len; i++) {
			var domnCode = domnCodes[i];
			var id = "chk_domn_cl_" + domnCode.codeId;
			var checked = codeNameList[domnCode.codeId]?'checked="checked"':'';
			tagStr += '<li>';
			tagStr += '<input type="checkbox" id="' + id + '" ' + checked + ' data-code-id="' + domnCode.codeId + '" />';
			tagStr += '<label for="' + id + '" >'+ domnCode.codeNm  +'</label>';
			tagStr += '</li>';
		}
		$(".ul_all_cls", that.selector).html(tagStr);
	},
	
	loadChecked : function() {
		var that = this;
		var tagStr = '';
		$(".ul_all_cls li input[type=checkbox]:checked", that.selector).each(function() {
			var element = $(this);
			var labelElement = element.parent().find("label");
			tagStr += '<li>' + labelElement.text() + '</li>';
		});
		$(".ul_select_cls", that.selector).html(tagStr);
	},
	
	/**
	 * 레이어 설정 창 닫기
	 */
	close : function() {
		var that = this;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
		
		
};