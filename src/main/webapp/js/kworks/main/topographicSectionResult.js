/**
 * 지형단면도 결과창
 */
var topographicSectionResultObj = {

	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "지형단면도 분석 결과",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "TopographicSectionResult",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_topographic_section_result",

	/**
	 * 생성 여부 (Singleton)
	 */
	isCreated : false,
	
	/**
	 * 
	 */
	profileData : null,

	/**
	 * 
	 */
	geometry : null,


	create : function() {
		var that = this;
		that.initUi();
		that.bindEvents();
		that.isCreated = true;
	},

	initUi : function() {
		var that = this;
		
		// 지형단면도 표시 영역
		$(".div_layout", that.selector).layout("panel", "center").panel({
			// 영역 크기 변경 시 지형단면도 다시 그림
			onResize : function() {
				var width = $(".div_profile", that.selector).width();
				var height = $(".div_profile", that.selector).height();
				topographicSectionViewObj.resize(width, height);
			}
		});
		
		that.setWindowPosition();
	},
	
	setWindowPosition : function() {
		var that = this;
		
		var windowWidth = $(window).width();
		var windowHeight = $(window).height();
		var navWidth = $("#div_menu").width();
		var width = windowWidth - navWidth;
		
		var left = navWidth;
		if($("#div_menu_panel").is(":visible")) {
			var panelWidth = $("#div_menu_panel").width();
			left += panelWidth;
			width = width - panelWidth;
		}
		
		$(that.selector).window("resize", {
			width : width,
			left : left,
			top : windowHeight - $(that.selector).height()
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 이미지 내보내기
		$(".a_export_image", that.selector).click(function() {
			 that.exportImage();
		});
		
		// 엑셀 내보내기
		$(".a_export_excel", that.selector).click(function() {
			that.exportExcel();
		});
		
	},

	/**
	 * 열기
	 */
	open : function(profileData, geometry, interval) {
		var that = this;
		if(!that.isCreated) {
			that.create();
		}
		
		that.profileData = profileData;
		that.geometry = geometry;
		
		$(that.selector).window("open");
		
		topographicSectionViewObj.init("#svg_profile", profileData, geometry, interval, {
			clickEvent : function(event) {
				var feature = new ol.Feature(new ol.geom.Point([event.x, event.y]));
				highlightObj.showRedFeatures(that.CLASS_NAME, [feature], false, {
					projection : ol.proj.get(MAP.PROJECTION)
				});
			}
		});

		var width = $(".div_profile", that.selector).width();
		var height = $(".div_profile", that.selector).height();
		topographicSectionViewObj.resize(width, height);
		
		var height = topographicSectionViewObj.getHeight() + 120;
		var top = $(window).height() - height;
		
		$(that.selector).window("move", {
			top : top
		});		
	},
	
	/**
	 * 윈도우 창 크기 변경 시 크기 조절
	 */
	resizeWindowHandler : function(windowWidth, windowHeight) {
		var that= this;
		
		var navWidth = $("#div_menu").width();
		var width = windowWidth - navWidth;
		
		var left = 0;
		if($("#div_menu_panel").is(":visible")) {
			left = $("#div_menu_panel").width();
			width = width - left;
		}
		
		$(that.selector).window("resize", {
			width : width,
			left : left,
			top : windowHeight - $(that.selector).window("panel").height() - 120
		});
	},
	
	/**
	 * 횡단면도 이미지 내보내기
	 */
	exportImage : function() {
		var that = this;
		
		var width = $("#svg_profile").width();
		var height = $("#svg_profile").height();
		var canvas = $("<canvas>").attr("width", width).attr("height", height)[0];
		var ctx = canvas.getContext("2d");
		ctx.drawSvg($(".div_profile", that.selector).html().replace(/\\/g, ""), 0, 0, width, height);
		var data = canvas.toDataURL();
		$("#frmDownloadBase64Image input[name=data]").val(data);
		$("#frmDownloadBase64Image").submit();
	},
	
	/**
	 * 엑셀 내보내기
	 */
	exportExcel : function() {
		var that = this;
		
		var table = {
			name : that.profileData.dataId,
			title : that.TITLE,
			columns : [
	           {
	        	   field : "X",
	        	   title : "X좌표"
	           },
	           {
	        	   field : "Y",
	        	   title : "Y좌표"
	           },
	           {
	        	   field : "ELEVATION",
	        	   title : "표고데이터"
	           }
           ],
			rows : []
		};
		
		var datas = that.profileData.datas;
		var count = datas.length;
		for (var i = 0; i < count; i++) {
			var data = datas[i];
			// 데이터 입력 시 반드시 문자열로
			var row = {
				X : String(data.x),
				Y : String(data.y),
				ELEVATION : String(data.elevation)
			};
			table.rows.push(row);
		}
		
		var tables = [];
		tables.push(table);
		
		var excelData = { name : "Profile", tables : tables };
		$("#frmExcelFile input[name=data]").val(encodeURIComponent(JSON.stringify(excelData)));
		$("#frmExcelFile").submit();
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		if(that.isCreated) {
			
			// 화면 크기 변환 시 결과 목록 영역 크기 조절 이벤트 해제
			mainObj.removeResizeHandler("topographic_section_result");
			
			highlightObj.removeRedFeatures(that.CLASS_NAME);
		}
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		$(that.selector).window("close");
		return false;
	}	
}