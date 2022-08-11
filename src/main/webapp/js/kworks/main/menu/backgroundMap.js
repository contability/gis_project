menuObj.backgroundMapObj = {
		
	/**
	 * 페이지 명
	 * @type {String}
	 */
	PAGE : "backgroundMap",
	
	selector : "#div_menu_panel_backgroundMap",
	
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "backGroundMap",
	
	TITLE : "배경지도",
	
	init : function() {
		var that = this;
		that.bindEvents();
		that.createFlightLayer();
		that.open();
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 탭 변경
		$(".mapTab .mTab", that.selector).click(function() {
			var element = $(this);
			var contentId = element.attr("data-for-id");
			
			// 선택 해제
			$(".mapTab .mTab", that.selector).find("img").each(function() {
				var imgElement = $(this);
				imgElement.attr("src", imgElement.attr("src").replace("_on", "_off"));
			});
			// 선택
			element.find("img").each(function() {
				var imgElement = $(this);
				imgElement.attr("src", imgElement.attr("src").replace("_off", "_on"));
			});
			
			$(".tab_content", that.selector).hide();
			$("#"+contentId).show();
		});
		
		// 영상 선택
		$(that.selector).on("click", ".div_background_map_item", function() {
			$("#div_loading").show();
			var element = $(this);
			var service = element.attr("data-service");
			var type = null;
			if(element.hasClass("on")) {
				element.removeClass("on");
				
				//속초시 요구사항 -> 항공사진인 경우 설명
				if(service == "tms") {
					var tagStr = "";
					tagStr += '';
					$("#div_description").html(tagStr);
				}
			}
			else {
				type = element.attr("data-type");
				$(".div_background_map_item").removeClass("on");
				element.addClass("on");
				
				// 이승재, 2020.05.18
				// 항공사진 캡션기능 범용화
				var comment = element.attr("data-comment");
				if (comment) {
					$("#div_description").html(comment);
				} else {
					$("#div_description").html('');
				}
				
				if (service != 'tms') {
					if(themamapObj.basemapObj.getData().length > 0) {
						$.messager.alert("외부지도", "외부지도 사용 시 레이어의 항공사진은 중첩해서 볼 수 없습니다.");
					}
				}
			}

			//배경지도 변경 시 조회하던 배경지도의 이동 초기화
			mapToolObj.moveAerialMapObj.moveAerialMap("original");
			
			backgroundMapObj.change(service, type);
			
			//배경지도 변경 시 새로은 배경지도의 중심점 저장
			mapToolObj.moveAerialMapObj.setOrigin();
			
			// 지도 툴 초기화
			mapToolObj.deactive();
			
			$("#div_loading").hide();
		});
		
		// 메타데이터 조회
		$(that.selector).on("click", ".a_info_metaData", function() {
			var element = $(this);
			var divElement = element.parent().parent().parent();
			
			that.metaData.open(divElement);
			
			return false;
		});
	},
	
	createFlightLayer : function() {
		var tagStr = "";
		var types = backgroundMapObj.data.tms.types;
		var typesArray = [];
		var mapNoArray = [];
		var commentArray = [];

		for(key in types) {
			typesArray.push(key);
			mapNoArray.push(types[key].mapNo);
			commentArray.push(types[key].comment);
		}
		
		// 이승재, 2020.05.18
		// 항공사진 캡션기능 범용화
		for(var i=typesArray.length-1; i>=0; --i) {
			tagStr += '<div data-service="tms" data-type="' + typesArray[i] + '" data-mapNo="' + mapNoArray[i] + '" data-comment="' + commentArray[i] + '" class="div_background_map_item">';
			tagStr += '<div class="div_img" >';
			tagStr += '<img src="' + MAP.TMS_URL + '/' + typesArray[i] + '.png" alt="' + typesArray[i] + '" title="' + typesArray[i] + '" />';
			tagStr += '</div>';
			tagStr += '<div class="div_content" >';
			
			for(var key in types) {
				if(key == typesArray[i]) {
					tagStr += types[key].description;
				}
			}
			
			if(EXTENSIONS.AERIAL_PHOTO) {
				tagStr += '<div class="div_content_tool">';
				tagStr += '<a href="#" class="a_info_metaData">';
				tagStr += '<img src="'+ CONTEXT_PATH +'/images/kworks/map/common/search_icon1_ov.png" alt="메타데이터 조회" title="메타데이터 조회" />';
				tagStr += '</a>';
				tagStr += '</div>';
			}
			
			tagStr += '</div>';
			tagStr += '</div>';
		}
		$("#div_menu_panel_background_map_flight").html(tagStr);	
	},
	
	loadDefault : function() {
		var that = this;
		var service = backgroundMapObj.getService();
		var type = backgroundMapObj.getType();
		if(service && type) {
			$(".div_background_map_item[data-service=" + service + "][data-type=" + type + "]", that.selector).addClass("on");
		}
	},
	
	resizeWindowHandler : function() {
		var that = this;
		var height = $("#div_menu_panel").height();
		var titleHeight = $("> .title", that.selector).height();
		var clearfixHeight = $(".clearfix", that.selector).height();
		var offset = 50;
		$("#div_menu_panel_background_map_flight", that.selector).height(height - titleHeight - clearfixHeight - offset);
		
	},
	
	open : function() {
		var that = this;
		
		that.loadDefault();
		
		$(that.selector).show();
		// 화면 크기 변환 시 트리 영역 크기 조절 이벤트 등록
		mainObj.addResizeHandler("backGroundMap", function() {
			that.resizeWindowHandler();
		});
		that.resizeWindowHandler();
	},
	
	close : function() {
		var that = this;
		$(that.selector).hide();
		mainObj.removeResizeHandler("backGroundMap");
	}
		
};

/**
 * 항공사진 메타데이터 조회 
 */
menuObj.backgroundMapObj.metaData = {
	
	TITLE : "항공사진 메타데이터 조회",
	
	CLASS_NAME : "AerialPhotoDetail",
	
	selector : null,
	
	isCreated : false,
	
	bindEvents : function() {
		var that = this;
		
		// 검색
		$("#a_close_viewAerialPhoto", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
		
	open : function(divElement) {
		var that = this;
		
		var mapNo = divElement.attr("data-mapNo");
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/manage/bcrnMap/" + mapNo + "/selectOneAerialPhotoPage.do";
		var windowOptions = {
			width : 410,
			height : 315,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.bindEvents();
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
	
};