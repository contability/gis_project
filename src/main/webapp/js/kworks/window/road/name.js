windowObj.roadNameObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도로명",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "RoadName",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	open : function() {
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/road/name/page.do";
		var windowOptions = {
			width : 270,
			height : 370,
			top : 120,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
			that.search();
		});
		that.selector = "#" + id;
	},
	
	close : function() {
		var that = this;
		windowObj.removeWindow(that.selector);
		that.selector = null;
		return false;
	},
	
	initUi : function() {
		var that = this;
		
		// 검색 영역
		$(".sel_search_extent", that.selector).combobox({
			width : 120
		});
		
		// 검색
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 검색
		$(".a_search", that.selector).click(function() {
			that.search();
		});
		
		// 노선명 선택
		$(".ul_content", that.selector).on("click", "li", function() {
			var element = $(this);
			$(".ul_content li", that.selector).removeClass("on");
			element.addClass("on");
		});
		
		// 위치 보기
		$(".a_highlight", that.selector).click(function() {
			that.highlight();
		});
		
		// 시설검색
		$(".a_facilities_search", that.selector).click(function() {
			that.searchFacilities();
		});
		
		// 편집
		$(".a_modify", that.selector).click(function() {
			that.modify();
		});
		
		// 닫기
		$(".btn_close", that.selector).click(function() {
			that.close();
		});
	},
	
	load : function() {
		var that = this;
		roadObj.getPromise().done(function() {
			var data = roadObj.getData();
			that.createUlElement(data);
		});
	},
	
	loadByExtent : function() {
		var that = this;
		var extent = mapObj.getMap().getExtent();
		var from = mapObj.getMap().getView().getProjection();
		var to = ol.proj.get(MAP.PROJECTION);
		var transformExtent = spatialUtils.transformExtent(extent, from ,to);
		
		var url = CONTEXT_PATH + "/rest/road/searchExtent.do";
		var params = {
			xmin : transformExtent[0],
			ymin : transformExtent[1],
			xmax : transformExtent[2],
			ymax : transformExtent[3]
		};
		$.get(url, params).done(function(response) {
			if(response && response.rows && response.rows.length > 0) {
				/*for(var i=0, len=response.rows.length; i < len; i++) {
					var row = response.rows[i];
					row.rbpLoc = "Y";
				}*/
				that.createUlElement(response.rows);
			}
			else {
				$.messager.alert(that.TITLE, "데이터가 없습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "권한이 없습니다.");
		});
	},
	
	search : function() {
		var that = this;
		var type = $(".sel_search_extent", that.selector).combobox("getValue");
		if(type == "ALL") {
			that.load();
		}
		else {
			if(mapObj.getMap().getScale() > 5000) {
				$.messager.alert(that.TITLE, "현재 영역 검색은 5000 이하의 축척에서만 가능합니다.");
			}
			else {
				that.loadByExtent();
			}
		}
	},
	
	createUlElement : function(rows) {
		var that = this;
		var tagStr = '';
		for(var i=0, len=rows.length; i < len; i++) {
			var row = rows[i];
			// 서산 시 도로 데이터가 정비되지 않아 임시 처리한 부분임
			if(row.rbpLoc == "Y") {
				tagStr += '<li data-rdn-cde="' + row.rdnCde + '">' + row.rdnNam + '</li>';
			}
		}
		$(".ul_content", that.selector).html(tagStr);
	},
	
	highlight : function() {
		var that = this;
		var elements = $(".ul_content li.on", that.selector);
		if(elements.length == 1) {
			var rdnCde = elements.attr("data-rdn-cde");
			roadObj.searchByRdnCde(rdnCde, that.TITLE).done(function(rdlIdns) {
				roadObj.highlight(rdlIdns, that.TITLE, that.CLASS_NAME);
			});
		}
		else {
			$.messager.alert(that.TITLE, "선택된 도로가 없습니다.");
		}
	},
	
	searchFacilities : function() {
		var that = this;
		var elements = $(".ul_content li.on", that.selector);
		if(elements.length == 1) {
			var rdnCde = elements.attr("data-rdn-cde");
			roadObj.searchByRdnCde(rdnCde, that.TITLE).done(function(rdlIdns) {
				roadObj.searchByRdlIdns(rdlIdns, that.TITLE, that.CLASS_NAME).done(function(features, data) {
					windowObj.roadSearchFacilitiesObj.open(features, data);
				});
			});
		}
		else {
			$.messager.alert(that.TITLE, "선택된 노선이 없습니다.");
		}
	},
	
	modify : function() {
		var that = this;
		var elements = $(".ul_content li.on", that.selector);
		if(elements.length == 1) {
			var rdnCde = elements.attr("data-rdn-cde");
			windowObj.roadNameEditObj.open({
				rdnCde : rdnCde,
				rdnNam : elements.text(),
				onSubmit : function(rn) {
					elements.text(rn);
					roadObj.modifyRoadNm(rdnCde, rn);
				}
			});
		}
		else {
			$.messager.alert(that.TITLE, "선택된 노선이 없습니다.");
		}
	}
		
};