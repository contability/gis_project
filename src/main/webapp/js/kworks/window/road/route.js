windowObj.roadRouteObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도로 노선",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "RoadRoute",
	
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
		
		var url = CONTEXT_PATH + "/window/road/route/page.do";
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
		
		// 시설 검색
		$(".a_facilities_search").click(function() {
			that.searchFacilities();
		});
		
		// 닫기
		$(".btn_close", that.selector).click(function() {
			that.close();
		});
	},
	
	load : function() {
		var that = this;
		var url = CONTEXT_PATH + "/rest/spatial/RDT_ROUT_DT/listAll.do";
		var params = {
			sortOrdr : "RUT_NAM"
		};
		return $.post(url, params).done(function(response) {
			if(response && response.rows && response.rows.length > 0) {
				that.createUlElement(response.rows);
			}
			else {
				$.messager.alert(that.TITLE, "데이터가 없습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "권한이 없습니다.");
		});
	},
	
	loadByExtent : function() {
		var that = this;
		var extent = mapObj.getMap().getExtent();
		var from = mapObj.getMap().getView().getProjection();
		var to = ol.proj.get(MAP.PROJECTION);
		var transformExtent = spatialUtils.transformExtent(extent, from ,to);
		
		var url = CONTEXT_PATH + "/rest/route/searchExtent.do";
		var params = {
			xmin : transformExtent[0],
			ymin : transformExtent[1],
			xmax : transformExtent[2],
			ymax : transformExtent[3]
		};
		$.get(url, params).done(function(response) {
			if(response && response.rows && response.rows.length > 0) {
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
			tagStr += '<li data-ftr-idn="' + row.ftrIdn + '">' + row.rutNam + '</li>';
		}
		$(".ul_content", that.selector).html(tagStr);
	},
	
	highlight : function() {
		var that = this;
		var elements = $(".ul_content li.on", that.selector);
		if(elements.length == 1) {
			var ftrIdn = elements.attr("data-ftr-idn");
			that.getRdlIdns(ftrIdn).done(function(rdlIdns) {
				roadObj.highlight(rdlIdns, that.TITLE, that.CLASS_NAME);
			});
			
		}
		else {
			$.messager.alert(that.TITLE, "선택된 노선이 없습니다.");
		}
	},
	
	getRdlIdns : function(ftrIdn) {
		var that = this;
		var url = CONTEXT_PATH + "/rest/route/" + ftrIdn + "/select.do";

		var deferred = $.Deferred();
		$.get(url).done(function(response) {
			if(response && response.data) {
				var rdlIdns = [];
				var rdtRtcnDts = response.data.rdtRtcnDts;
				for(var i=0, len=rdtRtcnDts.length ; i < len; i++) {
					var rdtAlcnDts = rdtRtcnDts[i].rdtAlcnDts;
					for(var j=0, jLen=rdtAlcnDts.length; j < jLen; j++) {
						var rdtAlcnDt = rdtAlcnDts[j];
						rdlIdns.push(rdtAlcnDt.rdlIdn);
					}
				}
				if(rdlIdns.length > 0) {
					deferred.resolve(rdlIdns);
				}
				else {
					$.messager.alert(that.TITLE, "도로 정보가 없습니다.");
				}
			}
			else {
				$.messager.alert(that.TITLE, "노선 정보를 가져오는 데 실패 했습니다.");
			}
		});
		return deferred.promise();
	},
	
	searchFacilities : function() {
		var that = this;
		var elements = $(".ul_content li.on", that.selector);
		if(elements.length == 1) {
			var ftrIdn = elements.attr("data-ftr-idn");
			that.getRdlIdns(ftrIdn).done(function(rdlIdns) {
				roadObj.searchByRdlIdns(rdlIdns, that.TITLE, that.CLASS_NAME).done(function(features, data) {
					windowObj.roadSearchFacilitiesObj.open(features, data);
				});
			});
			
		}
		else {
			$.messager.alert(that.TITLE, "선택된 노선이 없습니다.");
		}
	}
		
};