windowObj.topographicSectionSelectObj = {

	/**
	 * 제목
	 */
	TITLE : "지형단면 분석",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "TopographicSectionSelect",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 선택된 DEM
	 * @type {String}
	 */
	selectDem : null,
		
	
	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/analysis/topographicSectionSelect/page.do";
		var windowOptions = {
			width : 300,
			height : 200,
			top : 100,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
		});
		that.selector = "#" + id;
	},
	
	close : function() {
		var that = this;
		selectObj.clear(that.CLASS_NAME);
		windowObj.removeWindow(that.selector);
		topographicSectionResultObj.close();
		that.selector = null;
		return false;
	},

	initUi : function() {
		var that = this;

		$(".topographic", that.selector).combobox({
			valueField : "seqNo",
			textField : "title",
			onSelect : function(record) {
				that.selectDem = record;
			}
		});

		var demData = $(".topographic", that.selector).combobox("getData");
		var data = topographicObj.getGroups();
		for (var i = 0; i < data.length; i++) {
			var type = topographicObj.getType(data[i], "DEM");
			if (type) {
				demData.push(type);
			}
		}
		
		$(".topographic", that.selector).combobox("loadData", demData);
		if(demData.length > 0) {
			$(".topographic", that.selector).combobox("select", demData[0].title);
			that.selectDem = demData[0];
		}
		
		$(".interval_distance", that.selector).numberspinner({
			min : 10,
			max : 500,
			increment : 10
		});		
	},
	
	bindEvents : function() {
		var that = this;
		
		// 분석 위치 선택
		$(".a_select").click(function() {
			that.select();
			return false;
		});
		
		// 분석
		$(".a_analysis", that.selector).click(function() {
			that.analysis();
			return false;
		});
		
		// 닫기
		$(".a_close", that.selector).click(function() {
			that.close();
			return false;
		});
	},
	
	select : function() {
		var that = this;
		
		selectObj.active(that.CLASS_NAME, "LineString", "drawadd", function(evt) {
			var geom = evt.feature.getGeometry();
			evt.currentTarget.finishDrawing();
			
			if(geom.getLength() < 10) {
				$.messager.alert(that.TITLE, "지형단면은 10m 이상부터 분석 가능합니다.");
				selectObj.clearFeatures(that.CLASS_NAME);
			}
			else if(geom.getLength() > 10000) {
				$.messager.alert(that.TITLE, "지형단면은 최대 10km 까지 분석 가능합니다.");
				selectObj.clearFeatures(that.CLASS_NAME);
			}
			else {
				mainObj.defaultActive();
			}
		}, true);
	},
	
	analysis : function() {
		var that = this;
		
		var features = selectObj.getFeatures(that.CLASS_NAME);
		if(features.length > 0) {
			if (that.selectDem) {
				var layer = that.selectDem.layer;
				var group = that.selectDem.group;
				var interval = $(".interval_distance", that.selector).numberspinner("getValue");

				var mapProjection = mapObj.getMap().getView().getProjection();
				var srs = mapProjection.getCode();
				var extent = mapObj.getMap().getExtent();
				var width = $("#map_container").width(); 
				var height = $("#map_container").height(); 
				
				var geometry = features[0].getGeometry();
				var coordinate = geometry.getCoordinates();
				var start = mapObj.getMap().getPixelFromCoordinate(coordinate[0]);
				var end = mapObj.getMap().getPixelFromCoordinate(coordinate[1]);
				
				if (geometry.getLength() < interval) {
					$.messager.alert(that.TITLE, "분석위치의 길이는 추출간격의 길이보다 길어야 합니다.");
					selectObj.clearFeatures(that.CLASS_NAME);
				}
				else {
					topographicUtils.profile(that.TITLE, group, layer, interval, srs, extent, start, end, width, height, function(result) {
						if (result) {
							topographicSectionResultObj.open(result, geometry, interval);
						}
					});
				}
			}
		}
		else {
			$.messager.confirm(that.TITLE, "분석위치가 선택되지 않았습니다. 분석위치를 선택하시겠습니까?", function(isTrue) {
				if(isTrue) {
					$(".a_select img", that.selector).trigger("click");
				}
			});
		}
	}
}