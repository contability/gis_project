menuObj.bookMarkObj = {
		
	/**
	 * 페이지 명
	 * @type {String}
	 */
	PAGE : "bookMark",
		
	selector : "#div_menu_panel_bookMark",
	
	TITLE : "북마크",
	
	init : function() {
		var that = this;
		that.bindEvents();
		that.load();
		that.open();
	},
	
	resizeWindowHandler : function() {
		var that = this;
		var height = $("#div_menu_panel").height() - 60;
		var titleHeight = $("> .title", that.selector).height();
		var clearfixHeight = $(".clearfix", that.selector).height();
		var offset = 50;
		$(".ul_bookmark", that.selector).height(height - titleHeight - clearfixHeight - offset);
		
	},
	
	bindEvents : function() {
		var that = this;
		
		// 북마크 추가
		$(".a_add_themamap", that.selector).click(function() {
			windowObj.bookMarkObj.open({
				onSubmit : function() {
					that.load();
				}
			});
		});
		
		// 북마크 선택
		$(that.selector).on("click", ".a_select", function() {
			var element = $(this);
// Lks : 좌표가 문자이면 영역계산이 안됨.			
//			var centerX = element.attr("data-center-x");
//			var centerY = element.attr("data-center-y");
			var centerX = parseFloat(element.attr("data-center-x"));
			var centerY = parseFloat(element.attr("data-center-y"));
			var center = gisObj.toTransformMap(new ol.geom.Point([centerX, centerY])).getCoordinates();
			
			var sc = element.attr("data-sc");
			mapObj.getMap().moveToCenter(center);
			mapObj.getMap().moveToScale(sc);
			mapObj.getMap().updateSize();
			return false;
		});
		
		// 북마크 삭제
		$(that.selector).on("click", ".a_remove", function() {
			var element = $(this);
			var liElement = element.parent().parent();
			that.remove(liElement);
		});
		
		that.resizeWindowHandler();
		
	},
	
	remove : function(element) {
		var that = this;
		var bkmkNo = element.attr("data-bkmk-no");
		$.messager.confirm(that.TITLE, "삭제 하시겠습니까?", function(isTrue) {
			if(isTrue) {
				var url = CONTEXT_PATH + "/rest/bookMark/" + bkmkNo + "/remove.do";
				$.post(url).done(function(response) {
					element.remove();
					var length = $(".bookmark_cnt", that.selector).text();
					$(".bookmark_cnt", that.selector).text(parseInt(length) - 1);
				});
			}
		});
		
		
	},
	
	load : function() {
		var that = this;
		var url = CONTEXT_PATH + "/rest/bookMark/listAll.do";
		$.get(url).done(function(response) {
			if(response && response.rows) {
				var tagStr = '';
				var rows = response.rows;
				for(var i=0, len=rows.length; i < len; i++) {
					tagStr += that.createItem(rows[i]);
				}
				$(".bookmark_cnt", that.selector).text(rows.length);
				$(".ul_bookmark", that.selector).html(tagStr);
			}
			else {
				$.messager.alert(that.TITLE, "북마크 목록을 불러오는 데 실패 했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "북마크 목록을 불러오는 데 실패 했습니다.");
		});
	},
	
	createItem : function(row) {
		var tagStr = '<li data-bkmk-no="' + row.bkmkNo + '">';
		tagStr += '<span class="span_name">';
		tagStr += '<a href="#" class="a_select" data-center-x="'+ row.centerX +'" data-center-y="'+ row.centerY +'" data-sc="'+ row.sc +'" >';
		tagStr += row.bkmkNm;
		tagStr += '</a>';
		tagStr += '</span>';
		tagStr += '<span class="span_tool">';
		tagStr += '<a href="#" class="a_remove">';
		tagStr += '<img src="' + CONTEXT_PATH + '/images/kworks/map/common/boomark_icon1_off.png" alt="삭제" />';
		tagStr += '</a>';
		tagStr += '</span>';
		tagStr += '</li>';
		return tagStr;
	},
	
	open : function() {
		var that = this;
		$(that.selector).show();
		// 화면 크기 변환 시 트리 영역 크기 조절 이벤트 등록
		mainObj.addResizeHandler("bookMark", function() {
			that.resizeWindowHandler();
		});
		that.resizeWindowHandler();
	},
	
	close : function() {
		var that = this;
		$(that.selector).hide();
	}
	
};