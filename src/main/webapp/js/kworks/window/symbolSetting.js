windowObj.symbolSettingObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,

	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "심볼 설정",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "SymbolSetting",
	
	/**
	 * 열기
	 * @param rule 룰 객체
	 * @param options 옵션
	 */
	open : function(rule, options) {
		var that = this;
		
		var url = CONTEXT_PATH + "/window/symbolSetting/page.do";
		var windowOptions = {
			width : 270,
			height : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.data = $.extend(true, {}, rule);
			that.initUi();
			that.loadSymbol();
			that.bindEvents();
			that.setAnchor();
			that.preview();
			
			// 현재 객체에 전달되는 값 적용 (onSubmit)
			if(options) {
				$.extend(that, options);
			}
		});
		that.selector = "#" + id;
	},
	
	/**
	 * 레이어 설정 창 닫기
	 */
	close : function() {
		var that = this;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;

		// 최소 축척
		$(".txt_min_scale", that.selector).numberspinner({
			min : 0,
			increment : 1000,
			value : that.data.minScale
		});
		delete that.data.minScale;
		
		// 최대 축척
		$(".txt_max_scale", that.selector).numberspinner({
			min : 0,
			increment : 1000,
			value : that.data.maxScale
		});
		delete that.data.maxScale;
		
		// 축척 초기화
		$(".a_init_scale", that.selector).linkbutton();
	},
	
	/**
	 * 심볼 목록 불러오기
	 */
	loadSymbol : function() {
		var that = this;
		var tagStr = '';
		for(var i=0, len=COMMON.SYMBOLS.length; i < len; i++) {
			var symbol = COMMON.SYMBOLS[i];
			var classTag = '';
			if(that.data.point.resource == symbol) {
				classTag = 'class="on"';
			}
			
			tagStr += '<li data-symbol="' + symbol + '" ' + classTag + ' >';
			tagStr += '<img src="' + MAP.SYMBOL_URL + "/" + symbol + '" alt="' + COMMON.SYMBOLS[i] + '" />';
			tagStr += '</li>';
		}
		$(".div_content_symbol ul", that.selector).html(tagStr);
	},
	
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 축척 초기화
		$(".a_init_scale", that.selector).click(function() {
			$(".txt_min_scale", that.selector).numberspinner("clear");
			$(".txt_max_scale", that.selector).numberspinner("clear");
			
		});
		
		// 심볼 선택
		$(".div_content_symbol ul li", that.selector).click(function() {
			var element = $(this);
			$(".div_content_symbol ul li", that.selector).removeClass("on");
			element.addClass("on");
			
			that.data.point.resource = element.attr("data-symbol");
			that.data.point.size = element.find("img").height();
			
			that.preview();
		});
		
		// 심볼 위치 선택
		$(".div_content_position ul li", that.selector).click(function() {
			var element = $(this);
			var imgElement = element.find("img");
			
			$(".div_content_position ul li").removeClass("on");
			$(".div_content_position ul li img", that.selector).each(function() {
				$(this).attr("src", $(this).attr("src").replace("_on", "_off"));
			});
			imgElement.attr("src", imgElement.attr("src").replace("_off", "_on"));
			element.addClass("on");
			
			that.data.point.anchor = element.attr("data-anchor");
			that.preview();
		});
		
		// 설정
		$(".a_setting", that.selector).click(function() {
			
			var minScale = $(".txt_min_scale", that.selector).numberspinner("getValue");
			if(minScale) {
				that.data.minScale = minScale;
			}
			
			var maxScale = $(".txt_max_scale", that.selector).numberspinner("getValue");
			if(maxScale) {
				that.data.maxScale = maxScale;
			}
			
			if(that.onSubmit) {
				that.onSubmit(that.data);
				that.close();
			}
			return false;
		});
		
		// 닫기
		$(".a_close", that.selector).click(function() {
			that.close();
			return false;
		});
		
	},
	
	setAnchor : function() {
		var that = this;
		if(that.data.point.anchor) {
			$(".div_content_position ul li[data-anchor=" + that.data.point.anchor + "]", that.selector).trigger("click");
		}
	},
	
	/**
	 * 미리보기
	 */
	preview : function() {
		var that = this;
		var canvas = $("canvas", that.selector)[0];
		legendObj.previewSymbol(canvas.getContext("2d"), that.data.point, 90, 40);
	}
		
};