menuObj.themamapObj = {
		
	/**
	 * 페이지 명
	 * @type {String}
	 */
	PAGE : "themamap",
		
	selector : "#div_menu_panel_themamap",
	
	TITLE : "주제도",
	
	init : function() {
		var that = this;
		that.bindEvents();
		that.loadUser();
		that.loadPublic();
		
		var themamapId = userEnvironmentObj.getData("themamapId");
		if(!themamapId) {
			themamapId = THEMAMAP_ID;
		}
		$(".ul_themamap li[data-themamap-id="+themamapId+"]", that.selector).addClass("on");
		
		that.open();
	},
	
	resizeWindowHandler : function() {
		var that = this;
		var height = $("#div_menu_panel").height();
		var titleHeight = $("> .title", that.selector).height();
		var tagsHeight = $(".mapTab", that.selector).height();
		
		var toolHeight = 0;
		if($("#div_menu_panel_themamap_user").is(":visible")) {
			toolHeight = $(".div_tool", that.selector).height();
		}
		
		var offset = 40;
		$(".ul_themamap", that.selector).height(height - titleHeight - tagsHeight - toolHeight - offset);
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
			
			that.resizeWindowHandler();
			
			return false;
		});
		
		// 주제도 추가
		$(".a_add_themamap", that.selector).click(function() {
			windowObj.themamapObj.open({
				onSubmit : function() {
					themamapObj.load().done(function() {
						that.loadUser();
						$("#div_menu_panel_themamap_user .li_themamap_item:first", that.selector).trigger("click");
					});
				}
			});
			return false;
		});
		
		// 주제도 삭제
		$(that.selector).on("click", ".a_remove_themamap", function() {
			var element = $(this);
			var liElement = element.parent().parent().parent();
			that.removeConfirm(liElement);
			return false;
		});
		
		// 주제도 선택
		$(that.selector).on("click", ".li_themamap_item", function() {
			var element = $(this);
			that.select(element);
		});
	},
	
	loadUser : function() {
		var that = this;
		var data = themamapObj.getData("USER");
		var tagStr = "";
		for(var i=0, len=data.length; i < len; i++) {
			var themamap = data[i];
			var themamapId = themamap.themamapId;
			tagStr += that.createItem(themamapId, themamap.themamapNm, themamap.themamapDc, true);
		}
		$("#div_menu_panel_themamap_user .ul_themamap").html(tagStr);
	},
	
	loadPublic : function() {
		var that = this;
		var data = themamapObj.getData("SYS");
		var tagStr = "";
		for(var i=0, len=data.length; i < len; i++) {
			var themamap = data[i];
			var themamapId = themamap.themamapId;
			if(themamapId == THEMAMAP_ID) {
				tagStr += that.createItem(themamapId, themamap.themamapNm, themamap.themamapDc);
			}
		}
		
		//시스템 기본주제도 외 공용주제도 사용을 위해 PBLIC 주제도 Listing
		data = themamapObj.getData("PUBLIC");
		for(var i=0, len=data.length; i < len; i++) {
			var themamap = data[i];
			var themamapId = themamap.themamapId;
			tagStr += that.createItem(themamapId, themamap.themamapNm, themamap.themamapDc);
		}
		
		$("#div_menu_panel_themamap_public .ul_themamap").html(tagStr);
	},
	
	select : function(element) {
		var that = this;
		$("#div_loading").show();
		
		// 주제도 선택
		var themamapId = element.attr("data-themamap-id");
		themamapObj.selectOne(themamapId).done(function(themamap, jsonStr) {
			if(themamap) {
				if(!element.hasClass("on")) {
					type = element.attr("data-type");
					$(".li_themamap_item").removeClass("on");
					element.addClass("on");
				}
				
				sldObj.setData(JSON.parse(jsonStr));
				mapObj.reloadWMS();
				themamapObj.basemapObj.show();
				menuObj.layerObj.reloadTree();
				menuObj.layerObj.reloadBaseMapTree();
				if(themamap.themamapTy == "USER") {
					userEnvironmentObj.save({ themamapId : themamapId });
				}
				else {
					userEnvironmentObj.save({ themamapId : null });
				}
			}
			else {
				$.messager.confirm(that.TITLE, "주제도에 포함되는 레이어들에 대한 권한이 없어졌습니다. 해당 주제도를 삭제하시겠습니까?", function(isTrue) {
					if(isTrue) {
						that.remove(element, themamapId);
					}
				});
			}
			$("#div_loading").hide();
		});
	},
	
	removeConfirm : function(element) {
		var that = this;
		var themamapId = element.attr("data-themamap-id");
		if(element.hasClass("on")) {
			$.messager.alert(that.TITLE, "현재 선택 중인 주제도는 삭제할 수 없습니다.");
		}
		else {
			$.messager.confirm(that.TITLE, "삭제 하시겠습니까?", function(isTrue) {
				if(isTrue) {
					that.remove(element, themamapId);
				}
			});
		}
	},
	
	remove : function(element, themamapId) {
		$("#div_loading").show();
		themamapObj.remove(themamapId).done(function() {
			element.remove();
			$("#div_loading").hide();
		});
	},
	
	createItem : function(themamapId, themamapNm, themamapDc, isRemove) {
		var tagStr = '<li class="li_themamap_item" data-themamap-id="' + themamapId + '">';
		tagStr += '<div class="div_img">';
		tagStr += '<img src="' + CONTEXT_PATH + '/rest/themamap/' + themamapId + '/getThumnail.do" />';
		tagStr += '</div>';
		tagStr += '<div class="div_content" >';
		tagStr += '<div class="div_themamap_nm" title="' + themamapDc + '">';
		tagStr += themamapNm;
		tagStr += '</div>';
		if(isRemove) {
			tagStr += '<div class="div_content_tool">';
			tagStr += '<a href="#" class="a_remove_themamap">';
			tagStr += '<img src="'+ CONTEXT_PATH +'/images/kworks/map/common/boomark_icon1_off.png" alt="삭제" title="삭제" />';
			tagStr += '</a>';
			tagStr += '</div>';
		}
		tagStr += '</div>';
		tagStr += '</li>';
		return tagStr;
	},
	
	open : function() {
		var that = this;
		$(that.selector).show();
		// 화면 크기 변환 시 트리 영역 크기 조절 이벤트 등록
		mainObj.addResizeHandler("themamapMenu", function() {
			that.resizeWindowHandler();
		});
		that.resizeWindowHandler();
	},
	
	close : function() {
		var that = this;
		$(that.selector).hide();
		mainObj.removeResizeHandler("themamapMenu");
	}
	
};