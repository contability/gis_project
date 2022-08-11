windowObj.lineSettingObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,

	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "선 설정",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "LineSetting",
	
	/**
	 * Rule 객체
	 */
	data : null,
	
	/**
	 * 열기
	 * @param rule 룰 객체
	 * @param options 옵션
	 */
	open : function(rule, options) {
		var that = this;
		
		var url = CONTEXT_PATH + "/window/lineSetting/page.do";
		var windowOptions = {
			width : 270,
			height : 480,
			modal : true,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.data = $.extend(true, {}, rule);
			that.initUi();
			that.bindEvents();
			
			// 방향성 표시 숨김
			if(options.noDirection) {
				$(".div_content_panel_direction", that.selector).hide();
			}
			
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
	
		// 외곽선 투명도
		var strokeOpacity = 0;
		if(that.data.line.strokeOpacity) {
			strokeOpacity = that.data.line.strokeOpacity;
		}
		$(".txt_line_opacity", that.selector).slider({
			value : sldObj.convertOpacityToPercent(strokeOpacity),
			showTip:true,
			onChange : function(newValue,oldValue){
				that.data.line.strokeOpacity = sldObj.convertOpacityToValue(newValue);
				that.preview();
			}
		});
		
		// 외곽선 색상
		var stroke = "#000000";
		if(that.data.line.stroke) {
			stroke = that.data.line.stroke;
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
				that.data.line.stroke = "#" + hex;
				that.preview();
			}
		});
		$(".div_line_color", that.selector).find("div").css('background-color', stroke);
		
		// 외곽선 두께
		var strokeWidth = 2;
		if(that.data.line.strokeWidth) {
			strokeWidth = that.data.line.strokeWidth;
		}
		$(".txt_width", that.selector).numberspinner({
			min : 1,
			max : 20,
			value : strokeWidth,
			onChange : function(newValue, oldValue) {
				that.data.line.strokeWidth = parseInt(newValue);
				that.preview();
			}
		});
		
		// 외곽선 패턴
		var strokeDasharray = "solid";
		if(that.data.line.strokeDasharray) {
			strokeDasharray = that.data.line.strokeDasharray;
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
				that.data.line.strokeDasharray = newValue;
				that.preview();
			}
		});
		$(".sel_pattern", that.selector).combobox("setValue", strokeDasharray);
		
// Lks : 2019.07.08 - 운영되고 있는 SLD구조랑 다름.
//		if(that.data.line.point) {
//			$(".chk_line_direction", that.selector).prop("checked", true);
//		}
		if(that.data.point) {
			$(".chk_line_direction", that.selector).prop("checked", true);
		}
		
		delete that.data.line.point;
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
			
			var isDirection = $(".chk_line_direction", that.selector).is(":checked");
//			if(isDirection) {
//				that.setDirection();
//			}
			that.setDirection(isDirection);
			
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
	 * 방향성 설정
	 */
	setDirection : function(isDirection) {
		var that = this;

		if (!isDirection) {
			that.data.point = null;
			return;
		}
		
// Lks : 2019.07.08 - 운영되고 있는 SLD구조랑 다름.
//		if(!line.point) {
//			line.point = {};
//		}
//		var point = line.point;
		
		var line = that.data.line;
		
		if(!that.data.point) {
			that.data.point = {};
		}
		var point = that.data.point;

		point.size = "8.0";
		point.anchor = "cm";
		point.mark = {};
		point.mark.wellKnownName = {};
		point.mark.wellKnownName = "triangle";
		point.mark.fill = line.stroke;
		point.mark.fillOpacity = line.strokeOpacity;
		point.mark.stroke = line.stroke;
		point.mark.strokeOpacity = line.strokeOpacity;
		point.mark.strokeWidth = line.strokeWidth;
	},
	
	/**
	 * 미리보기
	 */
	preview : function() {
		var that = this;
		var canvas = $("canvas", that.selector)[0];
		legendObj.drawLine(canvas.getContext("2d"), that.data.line, 90, 40);
	}
		
};