windowObj.labelSettingObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,

	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "레이블 설정",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "LabelSetting",
	
	/**
	 * 열기
	 * @param options 옵션
	 */
	open : function(rule, options) {
		var that = this;
		
		var url = CONTEXT_PATH + "/window/labelSetting/page.do";
		var windowOptions = {
			width : 270,
			height : 840,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.data = $.extend(true, { anchor : "cm" }, rule);
			that.initUi();
			that.bindEvents();
			that.setAnchor();
			
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
		
		// 오프셋 X
		$(".txt_offset_x", that.selector).numberspinner({
			min : -10,
			max : 10,
			value : that.data.displacementX
		});
		
		// 오프셋 Y
		$(".txt_offset_y", that.selector).numberspinner({
			min : -10,
			max : 10,
			value : that.data.displacementY
		});
		
		// 폰트명
		$(".sel_font_name", that.selector).combobox({
			data : [
			    { value : 'Gulim', text : '굴림' },
			    { value : 'Gungsuh', text : '궁서' },
			    { value : 'Dotum', text : '돋움' },
			    { value : 'Malgun Gothic', text : '맑은 고딕' },
			    { value : 'Batang', text : '바탕' }
			],
			editable : false,
			valueField : "value",
			textField : "text",
			onChange : function(newValue, oldValue) {
				that.data.fontFamily = newValue;
				that.preview();
			}
		});
		$(".sel_font_name", that.selector).combobox("setValue", that.data.fontFamily);

		// 크기
		var fontSize = 12;
		if(that.data.fontSize) {
			fontSize = that.data.fontSize;
		}
		$(".txt_size", that.selector).numberspinner({
			min : 12,
			max : 40,
			value : fontSize,
			onChange : function(newValue, oldValue) {
				that.data.fontSize = parseInt(newValue);
				that.preview();
			}
		});
		
		// 스타일 - 굵게
		if(that.data.fontWeight && that.data.fontWeight == "bold") {
			imageUtils.on($(".a_bold img", that.selector));
		}
		// 스타일 - 기울기
		if(that.data.fontStyle && that.data.fontStyle == "italic") {
			imageUtils.on($(".a_italic img", that.selector));
		}
		
		// 글꼴 투명도
		var fontOpacity = 0;
		if(that.data.fontOpacity) {
			fontOpacity = that.data.fontOpacity;
		}
		$(".txt_font_opacity", that.selector).slider({
			value : sldObj.convertOpacityToPercent(fontOpacity),
			showTip:true,
			onChange : function(newValue,oldValue){
				that.data.fontOpacity = sldObj.convertOpacityToValue(newValue);
				that.preview();
			}
		});
		
		// 글꼴 색상
		var fill = "#000000";
		if(that.data.fill) {
			fill = that.data.fill;
		}
		$(".div_font_color", that.selector).ColorPicker({
			color: fill,
			onShow: function (colpkr) {
				$(colpkr).fadeIn(500);
				return false;
			},
			onHide: function (colpkr) {
				$(colpkr).fadeOut(500);
				return false;
			},
			onChange: function (hsb, hex, rgb) {
				$(".div_font_color", that.selector).find("div").css('background-color', '#' + hex);
				that.data.fill = "#" + hex;
				that.preview();
			}
		});
		$(".div_font_color", that.selector).find("div").css('background-color', fill);
		
		// 반경
		var haloRadius = 2;
		if(that.data.haloRadius >= 0) {
			haloRadius = that.data.haloRadius;
		}
		$(".txt_radius", that.selector).numberspinner({
			min : 0,
			max : 20,
			value : haloRadius,
			onChange : function(newValue, oldValue) {
				that.data.haloRadius = parseInt(newValue);
				that.preview();
			}
		});
		
		// 그림자 투명도
		var haloOpacity = 0;
		if(that.data.haloOpacity >= 0) {
			haloOpacity = that.data.haloOpacity;
		}
		$(".txt_shadow_opacity", that.selector).slider({
			value : sldObj.convertOpacityToPercent(haloOpacity),
			showTip:true,
			onChange : function(newValue,oldValue){
				that.data.haloOpacity = sldObj.convertOpacityToValue(newValue);
				that.preview();
			}
		});
		
		// 그림자 색상
		var halo = "#000000";
		if(that.data.halo) {
			halo = that.data.halo;
		}
		$(".div_shadow_color", that.selector).ColorPicker({
			color: halo,
			onShow: function (colpkr) {
				$(colpkr).fadeIn(500);
				return false;
			},
			onHide: function (colpkr) {
				$(colpkr).fadeOut(500);
				return false;
			},
			onChange: function (hsb, hex, rgb) {
				$(".div_shadow_color", that.selector).find("div").css('background-color', '#' + hex);
				that.data.halo = "#" + hex;
				that.preview();
			}
		});
		$(".div_shadow_color", that.selector).find("div").css('background-color', halo);
		
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
		
		// 위치 선택
		$(".div_content_position ul li", that.selector).click(function() {
			var element = $(this);
			var imgElement = element.find("img");
			
			$(".div_content_position ul li").removeClass("on");
			$(".div_content_position ul li img", that.selector).each(function() {
				$(this).attr("src", $(this).attr("src").replace("_on", "_off"));
			});
			imgElement.attr("src", imgElement.attr("src").replace("_off", "_on"));
			element.addClass("on");
			
			that.data.anchor = element.attr("data-anchor");
			that.preview();
			
		});
		
		// 위치 선택
		$(".div_content_position ul li", that.selector).click(function() {
			var element = $(this);
			var imgElement = element.find("img");
			
			$(".div_content_position ul li").removeClass("on");
			$(".div_content_position ul li img", that.selector).each(function() {
				$(this).attr("src", $(this).attr("src").replace("_on", "_off"));
			});
			imgElement.attr("src", imgElement.attr("src").replace("_off", "_on"));
			element.addClass("on");
			
			that.data.anchor = element.attr("data-anchor");
		});
		
		// 스타일 - 굵게
		$(".a_bold", that.selector).click(function() {
			var element = $(this);
			var imgElement = element.find("img");
			var isSelected = imageUtils.toggle(imgElement);
			if(isSelected) {
				that.data.fontWeight = "bold";
			}
			else {
				that.data.fontWeight = "normal";
			}
			that.preview();
		});
		
		// 스타일 - 기울기
		$(".a_italic", that.selector).click(function() {
			var element = $(this);
			var imgElement = element.find("img");
			var isSelected = imageUtils.toggle(imgElement);
			if(isSelected) {
				that.data.fontStyle = "italic";
			}
			else {
				that.data.fontStyle = "normal";
			}
			that.preview();
		});
		
		// 설정
		$(".a_setting", that.selector).click(function() {
			
			var minScale = $(".txt_min_scale", that.selector).numberspinner("getValue");
			if(minScale) {
				that.data.minScale = parseFloat(minScale);
			}
			
			var maxScale = $(".txt_max_scale", that.selector).numberspinner("getValue");
			if(maxScale) {
				that.data.maxScale = parseFloat(maxScale);
			}
			
			var offsetX = $(".txt_offset_x", that.selector).numberspinner("getValue");
			if(offsetX) {
				that.data.displacementX = parseFloat(offsetX);
			}
			
			var offsetY = $(".txt_offset_y", that.selector).numberspinner("getValue");
			if(offsetY) {
				that.data.displacementY = parseFloat(offsetY);
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
		if(that.data.anchor) {
			$(".div_content_position ul li[data-anchor=" + that.data.anchor + "]", that.selector).trigger("click");
		}
	},
	
	/**
	 * 미리보기
	 */
	preview : function() {
		var that = this;
		var canvas = $("canvas", that.selector)[0];
		legendObj.drawLabel(canvas.getContext("2d"), that.data, 90, 40);
	}
		
};