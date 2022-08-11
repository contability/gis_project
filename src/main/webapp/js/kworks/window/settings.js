/**
 * 화면설정
 * @namespace
 */
windowObj.settingsObj = {
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "화면 설정",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "Settings",
	
	/**
	 * 초기화
	 */
	open : function() {
		var that = this;
		var url = CONTEXT_PATH + "/window/settings/page.do";
		var options = {
			width : 425,
			height : 325,
			top : 150,
			modal : true,
			onClose : function() {
				that.close();
			}
		};
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, options, function() {
			that.bindEvents();
			that.initCheck();
		});
		that.selector = "#" + id;
	},
	
	bindEvents : function() {
		var that = this;
		
		// 왼쪽 메뉴 선택 이벤트
		$(".div_menu_container ul li a", that.selector).click(function() {
			var element = $(this);
			var id = element.attr("data-content-id");
			
			$(".div_menu_container ul li", that.selector).removeClass("on");
			element.parent().addClass("on");
			
			$(".div_content_container > div", that.selector).hide();
			$("#"+id).show();
		});
		
		// 지도조작 위치 변경
		$("input[name=rad_settings_maptool_location]").change(function() {
			var element = $(this);
			userEnvironmentObj.setData("mapToolLc", element.val());
			mainObj.resizeWindowHandler();
		});
		
		// 인덱스맵
		$("input[name=rad_settings_index_location]").change(function() {
			var element = $(this);
			userEnvironmentObj.setData("indexWindowLc", element.val());
			mainObj.resizeWindowHandler();
		});
		$("input[name=rad_settings_index_size]").change(function() {
			var element = $(this);
			userEnvironmentObj.setData("indexWindowSize", element.val());
			mainObj.resizeWindowHandler();
		});
		
		// 지도 축척 바
		$("input[name=rad_settings_scale_location]").change(function() {
			var element = $(this);
			userEnvironmentObj.setData("scBarLc", element.val());
			mainObj.resizeWindowHandler();
		});
		
		// 지도 중심 좌표
		$("input[name=rad_settings_center_mode]").change(function() {
			var element = $(this);
			userEnvironmentObj.setData("centerPointTy", element.val());
			var interaction = mapObj.getMap().getInteraction("mousePosition");
			interaction.setMode(element.val());
		});
		$("input[name=rad_settings_center_location]").change(function() {
			var element = $(this);
			userEnvironmentObj.setData("centerPointLc", element.val());
			mainObj.resizeWindowHandler();
		});
		
		// Zoom 렌즈
		$("input[name=rad_settings_zoom_size]").change(function() {
			var element = $(this);
			userEnvironmentObj.setData("zoomLensSize", element.val());
			mapToolObj.zoomObj.changeSize(element.val());
		});
		
		// XRay 렌즈
		$("input[name=rad_settings_xray_size]").change(function() {
			var element = $(this);
			userEnvironmentObj.setData("xrayLensSize", element.val());
			mapToolObj.xrayObj.changeSize(element.val());
		});
		
		// 환경 저장
		$(".a_save", that.selector).click(function() {
			that.save();
		});
		
		// 닫기
		$(".a_close", that.selector).click(function() {
			that.close();
		});
	},
	
	initCheck : function() {
		var that = this;
		
		// 지도조작 위치
		var mapToolLc = userEnvironmentObj.getData("mapToolLc");
		$("input[name=rad_settings_maptool_location]:input[value="+mapToolLc+"]", that.selector).prop("checked", true);
		
		// 인덱스맵
		var indexWindowLc = userEnvironmentObj.getData("indexWindowLc");
		$("input[name=rad_settings_index_size]:input[value="+indexWindowLc+"]", that.selector).prop("checked", true);
		var indexWindowSize = userEnvironmentObj.getData("indexWindowSize");
		$("input[name=rad_settings_index_location]:input[value="+indexWindowSize+"]", that.selector).prop("checked", true);
		
		// 지도 축척 바
		var scBarLc = userEnvironmentObj.getData("scBarLc");
		$("input[name=rad_settings_scale_location]:input[value="+scBarLc+"]", that.selector).prop("checked", true);
		
		// 지도 중심 좌표
		var centerPointTy = userEnvironmentObj.getData("centerPointTy");
		$("input[name=rad_settings_center_mode]:input[value="+centerPointTy+"]", that.selector).prop("checked", true);
		var centerPointLc = userEnvironmentObj.getData("centerPointLc");
		$("input[name=rad_settings_center_location]:input[value="+centerPointLc+"]", that.selector).prop("checked", true);
		
		// Zoom 렌즈
		var zoomLensSize = userEnvironmentObj.getData("zoomLensSize");
		$("input[name=rad_settings_zoom_size]:input[value="+zoomLensSize+"]", that.selector).prop("checked", true);
		
		// XRay 렌즈
		var xrayLensSize = userEnvironmentObj.getData("xrayLensSize");
		$("input[name=rad_settings_xray_size]:input[value="+xrayLensSize+"]", that.selector).prop("checked", true);
		
	},
	
	save : function() {
		var that = this;
		
		// 지도조작 위치
		var mapToolLc = $("input[name=rad_settings_maptool_location]:checked").val();
		// 인덱스맵
		var indexWindowLc = $("input[name=rad_settings_index_location]:checked").val();
		var indexWindowSize = $("input[name=rad_settings_index_size]:checked").val();
		// 지도 축척 바
		var scBarLc = $("input[name=rad_settings_scale_location]:checked").val();
		// 지도 중심 좌표
		var centerPointTy = $("input[name=rad_settings_center_mode]:checked").val();
		var centerPointLc = $("input[name=rad_settings_center_location]:checked").val();
		// Zoom 렌즈
		var zoomLensSize = $("input[name=rad_settings_zoom_size]:checked").val();
		// XRay 렌즈
		var xrayLensSize = $("input[name=rad_settings_xray_size]:checked").val();
		
		var params = {
			mapToolLc : mapToolLc,
			indexWindowLc : indexWindowLc,
			indexWindowSize : indexWindowSize,
			scBarLc : scBarLc,
			centerPointTy : centerPointTy,
			centerPointLc : centerPointLc,
			zoomLensSize : zoomLensSize,
			xrayLensSize : xrayLensSize
		};
		userEnvironmentObj.save(params).done(function(response) {
			if(response && response.rowCount && response.rowCount == 1) {
				$.messager.alert(that.TITLE, "사용자 환경 설정이 저장 되었습니다.");
			}
			else {
				$.messager.alert(that.TITLE, "사용자 환경 설정 저장에 실패했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "사용자 환경 설정 저장에 실패했습니다.");
		});
	},
		
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		windowObj.removeWindow(that.selector);
	}
		
};