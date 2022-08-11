/** 
 * 지형지도 활용
 */
menuObj.topographicObj = {
	/**
	 * 페이지 명
	 * @type {String}
	 */
	PAGE : "topographic",
	
	selector : "#div_menu_panel_topographic",
	
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "TopographicMenu",
	
	TITLE : "지형지도",
	
	isCreated : false,

	isChange : false,
	
	/**
	 * 초기 셋팅
	 */
	init : function() {
		var that = this;
		
		that.initUi();
		that.listObj.init(that);
		
		that.isCreated = true;
	},
	
	initUi : function() {
		var that = this;
		
		$(".div_menu_panel_topographic_tabs", that.selector).tabs({
			onSelect : function(title, index) {
			}
		});
	},
	
	selectTab : function(title) {
		var that = this;
		$(".div_menu_panel_topographic_tabs", that.selector).tabs("select", title);
	},
	
	/**
	 * 창 크기 변경
	 */
	resizeWindowHandler : function() {
		var that = this;
		var height = $("#div_menu_panel").height();
		var titleHeight = $("> .title", that.selector).height();
		var tagsHeight = $(".tabs-header", that.selector).height();
		var toolHeight = $(".div_tool", that.selector).height();
		var offset = 20;
		$(".div_topographic_list", that.selector).height(height - titleHeight - tagsHeight  - toolHeight - offset);
	},
	
	open : function() {
		var that = this;
		
		if(that.isChange) {
			that.isChange = false;
		}
		
		$(that.selector).show();
		// 화면 크기 변환 시 트리 영역 크기 조절 이벤트 등록
		mainObj.addResizeHandler(that.CLASS_NAME, function() {
			that.resizeWindowHandler();
		});
		that.resizeWindowHandler();
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		$(that.selector).hide();
		mainObj.removeResizeHandler(that.CLASS_NAME);
	}
};

/**
 * 지형지도 목록
 */
menuObj.topographicObj.listObj = {

	selector : "#div_menu_panel_topographic .div_topographic_map",
	
	TITLE : "지형지도",
	
	parent : null,
	
	/**
	 * 초기화
	 * @param parent
	 */
	init : function(parent) {
		var that = this;
		
		that.parent = parent;
		that.createLayer();
		that.bindEvents();
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 지형단면
		$(".a_profile", that.selector).click(function() {
			windowObj.topographicSectionSelectObj.open();
		});
		
		// 데이터 선택
		$(that.selector).on("click", ".div_topographic_item", function() {
			$("#div_loading").show();
			
			var element = $(this);
			var group = null;
			var type = null;
			if(element.hasClass("on")) {// 선택 해제 
				element.removeClass("on");
			}
			else {// 선택
				group = element.attr("data-group");				
				type = element.attr("data-type");
				$(".div_topographic_item").removeClass("on");
				
				element.addClass("on");
			}

			// 지형지도 변경
			topographicObj.change(group, type);
			
			// 지도 툴 초기화
			mapToolObj.deactive();
			
			$("#div_loading").hide();
		});

		// 메타 조회
		$(that.selector).on("click", ".a_topographic_meta", function() {
			var element = $(this);
			var divElement = element.parent().parent().parent();
			
			menuObj.topographicObj.metaInfo.open(divElement);
			
			return false;
		});
		
	},
	
	/**
	 * 지형레이어 생성
	 */
	createLayer : function() {
		var tagStr = "";
		
		var groups = topographicObj.groups;
		if (groups && groups.length > 0) {
			for (var i=0; i < groups.length; i++) {
				var types = topographicObj.getTypes(groups[i]);
				if (types) {
					for (var type in types) {
						var data = topographicObj.getType(groups[i], type);
						tagStr += '<div data-type="' + data.dataType + '" data-group="' + groups[i] + '" class="div_topographic_item">';
						tagStr += '<div class="div_img" >';
						tagStr += '<img src="' + TOPOGRAPHIC_ICON_URL + data.iconPath + type + '.png" alt="' + data.title + '" title="' + data.title + '" />';
						tagStr += '</div>';
						tagStr += '<div class="div_content" >';
						tagStr += data.title;
						tagStr += '<div class="div_content_tool">';
						tagStr += '<a href="#" class="a_topographic_meta">';
						tagStr += '<img src="'+ CONTEXT_PATH + '/images/kworks/map/common/search_icon1_ov.png" alt="조회" title="조회" />';
						tagStr += '</a>';
//						tagStr += '<a href="#" class="a_topographic_setting">';
//						tagStr += '<img src="' + CONTEXT_PATH + '/images/kworks/map/common/layer_icon2_ov.png" alt="설정" title="설정" />';
//						tagStr += '</a>';
						tagStr += '</div>';
						tagStr += '</div>';
						tagStr += '</div>';
					}
				}
			}
		}
		
		$(".div_topographic_list").html(tagStr);			
	}
	
};


/**
 * 지형지도 상세정보
 */
menuObj.topographicObj.metaInfo = {
		
	TITLE : "지형지도 상세정보",
	
	CLASS_NAME : "TopographicMetaInfo",
		
	selector : null,
	
	isCreated : false,
	
	data : null,
	
	opacity : null,
	
	
	bindEvents : function() {
		var that = this;

		// 투명도
		$(".txt_opacity", that.selector).slider({
			value : 0,
			min : 0,
			max : 100,
			showTip : true,
			onChange : function(newValue, oldValue) {
				if(newValue != oldValue) {
					that.data.opacity = newValue;
					topographicObj.refresh(newValue);
				}
			}
		});
		
		// 닫기
		$("#a_close_topographic", that.selector).on("click", function() {
			that.close();
			
			return false;
		});		
	},
	
	open : function(divElement) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/cmmn/topographicMap/metaPage.do";
		var windowOptions = {
			width : 410,
			height : 350,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.bindEvents();
				
				var group = divElement.attr("data-group");
				var type = divElement.attr("data-type");
				var data = topographicObj.getType(group, type);
				that.data = data;
				that.opacity = data.opacity;

				$("#title", that.selector).text(data.title);
				$("#description", that.selector).text(data.description);
				$("#minValue", that.selector).text(data.minValue);
				$("#maxValue", that.selector).text(data.maxValue);
				$("#year", that.selector).text(data.makeYear);
				$("#institution", that.selector).text(data.makeInstitution);
				$(".txt_opacity", that.selector).slider("setValue", data.opacity);
				
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	close : function() {
		var that = this;
		
		// 투명도가 변경되었으면 다시 설정 
		if (that.opacity != that.data.opacity) {
			topographicObj.refresh(that.data.opacity);
		}
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}	
};