windowObj.polygonSettingObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,

	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "면 설정",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "PolygonSetting",
	
	/**
	 * Rule 객체
	 */
	data : null,
	
	/**
	 * 채우기, 외곽선 OFF 시 이전 설정 저장
	 */
	backup : {},
	
	/**
	 * 열기
	 * @param rule 룰 객체
	 * @param options 옵션
	 */
	open : function(rule, options) {
		var that = this;
		
		var url = CONTEXT_PATH + "/window/polygonSetting/page.do";
		var windowOptions = {
			width : 270,
			height : 580,
			modal : true,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.data = $.extend(true, {}, rule);
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
	 * 레이어 설정 창 닫기
	 */
	close : function() {
		var that = this;
		that.data = null;
		that.backup = {};
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
	
		// 채우기 투명도
		var fillOpacity = 0;
		if(that.data.polygon.fillOpacity) {
			fillOpacity = that.data.polygon.fillOpacity;
		}
		$(".txt_fill_opacity", that.selector).slider({
			value : sldObj.convertOpacityToPercent(fillOpacity),
			showTip:true,
			onChange : function(newValue,oldValue){
				that.data.polygon.fillOpacity = sldObj.convertOpacityToValue(newValue);
				that.preview();
			}
		});
		
		// 채우기 색상
		var fill = "#000000";
		if(that.data.polygon.fill) {
			fill = that.data.polygon.fill;
			$(".chk_fill", that.selector).prop("checked", true);
		}
		else {
			$(".div_content_panel_fill .div_content").hide();
		}
		$(".div_fill_color", that.selector).ColorPicker({
			color: fill,
			onChange: function (hsb, hex, rgb, element) {
				$(".div_fill_color", that.selector).find("div").css('background-color', '#' + hex);
				that.data.polygon.fill = "#" + hex;
				that.preview();
			},
			onSubmit : function (hsb, hex, rgb, element) {
				$(element).ColorPickerHide();
			}
		});
		$(".div_fill_color", that.selector).find("div").css('background-color', fill);
		
		// 외곽선 투명도
		var strokeOpacity = 0;
		if(that.data.polygon.strokeOpacity) {
			strokeOpacity = that.data.polygon.strokeOpacity;
		}
		$(".txt_line_opacity", that.selector).slider({
			value : sldObj.convertOpacityToPercent(strokeOpacity),
			showTip:true,
			onChange : function(newValue,oldValue){
				that.data.polygon.strokeOpacity = sldObj.convertOpacityToValue(newValue);
				that.preview();
			}
		});
		
		// 외곽선 색상
		var stroke = "#000000";
		if(that.data.polygon.stroke) {
			stroke = that.data.polygon.stroke;
			$(".chk_stroke", that.selector).prop("checked", true);
		}
		else {
			$(".div_content_panel_stroke .div_content").hide();
		}
		$(".div_line_color", that.selector).ColorPicker({
			color: stroke,
			onShow: function (colpkr) {
				$(colpkr).fadeIn(500);
				return false;
			},
			onHide: function (colpkr) {
				$(colpkr).fadeOut(500);
				return false;
			},
			onChange: function (hsb, hex, rgb) {
				$(".div_line_color", that.selector).find("div").css('background-color', '#' + hex);
				that.data.polygon.stroke = "#" + hex;
				that.preview();
			}
		});
		$(".div_line_color", that.selector).find("div").css('background-color', stroke);
		
		// 외곽선 두께
		var strokeWidth = 1;
		if(that.data.polygon.strokeWidth) {
			strokeWidth = that.data.polygon.strokeWidth;
		}
		$(".txt_width", that.selector).numberspinner({
			min : 1,
			max : 20,
			value : strokeWidth,
			onChange : function(newValue, oldValue) {
				that.data.polygon.strokeWidth = parseInt(newValue);
				that.preview();
			}
		});
		
		// 외곽선 패턴
		var strokeDasharray = "solid";
		if(that.data.polygon.strokeDasharray) {
			strokeDasharray = that.data.polygon.strokeDasharray;
		}
		$(".sel_pattern", that.selector).combobox({
			data : [
			    { value : "solid", text : "실선", src : '<img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/layerLine_01_off.png" alt="실선" style="width:150px;" />' },
			    { value : "dash",  text : "점선", src : '<img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/layerLine_02_off.png" alt="점선" style="width:150px;" />' },
			    { value : "dashDot",  text : "일점쇄선", src : '<img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/layerLine_03_off.png" alt="일점쇄선" style="width:150px;" />' },
			    { value : "dashDotDot",  text : "이점쇄선", src : '<img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/layerLine_04_off.png" alt="이점쇄선" style="width:150px;" />' }
			],
			editable : false,
			valueField : "value",
			textField : "text",
			formatter : function(row) {
				return row.src;
			},
			onChange : function(newValue, oldValue) {
				that.data.polygon.strokeDasharray = newValue;
				that.preview();
			}
		});
		$(".sel_pattern", that.selector).combobox("setValue", strokeDasharray);
		
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
		
		// 채우기 설정 
		$(".chk_fill", that.selector).click(function() {
			var element = $(this);
			if(element.is(":checked")) {
				$(".div_content_panel_fill .div_content").show();
				that.data.polygon.fill = that.backup.fill?that.backup.fill:"#000000";
				that.data.polygon.fillOpacity = that.backup.fillOpacity?that.backup.fillOpacity:"1.0";
				that.preview();
			}
			else {
				if(that.data.polygon.fill) {
					that.backup.fill = that.data.polygon.fill;
					delete that.data.polygon.fill;
				}
				if(that.data.polygon.fillOpacity) {
					that.data.polygon.fillOpacity = that.data.polygon.fillOpacity;
					delete that.data.polygon.fillOpacity;
				}
				
				$(".div_content_panel_fill .div_content").hide();
				that.preview();
			}
		});
		
		// 외곽선 설정 
		$(".chk_stroke", that.selector).click(function() {
			var element = $(this);
			if(element.is(":checked")) {
				$(".div_content_panel_stroke .div_content").show();
				that.data.polygon.stroke = that.backup.stroke?that.backup.stroke:"#000000";
				that.data.polygon.strokeOpacity = that.backup.strokeOpacity?that.backup.strokeOpacity:"1.0";
				that.data.polygon.strokeWidth = that.backup.strokeWidth?that.backup.strokeWidth:1.0;
				that.data.polygon.strokeDasharray = that.backup.strokeDasharray?that.backup.strokeDasharray:"solid";
				that.preview();
			}
			else {
				if(that.data.polygon.stroke) {
					that.backup.stroke = that.data.polygon.stroke;
					delete that.data.polygon.stroke;
				}
				if(that.data.polygon.strokeOpacity) {
					that.backup.strokeOpacity = that.data.polygon.strokeOpacity;
					delete that.data.polygon.strokeOpacity;
				}
				if(that.data.polygon.strokeWidth) {
					that.backup.strokeWidth = that.data.polygon.strokeWidth;
					delete that.data.polygon.strokeWidth;
				}
				if(that.data.polygon.strokeDasharray) {
					that.backup.strokeDasharray = that.data.polygon.strokeDasharray;
					delete that.data.polygon.strokeDasharray;
				}
				
				$(".div_content_panel_stroke .div_content").hide();
				that.preview();
			}
		});
		
		// 설정
		$(".a_setting", that.selector).click(function() {
			
			if(!$(".chk_fill", that.selector).is(":checked") && !$(".chk_stroke", that.selector).is(":checked")) {
				$.messager.alert("채우기 설정과 외곽선 설정 중에 하나를 설정하여 주십시오.");
				return;
			}
			
			var minScale = $(".txt_min_scale", that.selector).numberspinner("getValue");
			if(minScale) {
				that.data.minScale = parseFloat(minScale);
			}
			
			var maxScale = $(".txt_max_scale", that.selector).numberspinner("getValue");
			if(maxScale) {
				that.data.maxScale = parseFloat(maxScale);
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
	
	/**
	 * 미리보기
	 */
	preview : function() {
		var that = this;
		var canvas = $("canvas", that.selector)[0];
		legendObj.drawPolygon(canvas.getContext("2d"), that.data.polygon, 90, 40);
	}
		
};