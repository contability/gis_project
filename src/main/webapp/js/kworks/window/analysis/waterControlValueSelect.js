windowObj.waterControlValueSelect = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "차단제수변 분석",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "WaterControlValueSelect",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 열기
	 */
	open : function(dataId) {
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/analysis/waterControlValueSelect/page.do";
		var windowOptions = {
			width : 300,
			height : 125,
			top : 100,
			onClose : function() {
				that.close();
			}
		};
		
		sldObj.showDatas([dataId]);
		mapObj.reloadWMS();
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.bindEvents();
		});
		that.selector = "#" + id;
	},
	
	/**
	 * 닫기
	 * @returns {Boolean}
	 */
	close : function() {
		var that = this;
		selectObj.clear(that.CLASS_NAME);
		windowObj.removeWindow(that.selector);
		mainObj.defaultActive();
		that.selector = null;
		return false;
	},
	
	bindEvents : function() {
		var that = this;
		
		// 분석 위치 선택
		$(".a_select_point").click(function() {
			that.select();
			return false;
		});
		
	},
	
	select : function() { 
		var that = this;
		selectObj.active(that.CLASS_NAME, "Point", "drawend", function(evt) {
			var coordinates = evt.feature.getGeometry().getCoordinates();
			//2021-12-27
			//조원기
			//외부지도 사용시 좌표값 달라짐 -> 보정작업
		    var coordConver = gisObj.totransformCooridateSystem(coordinates);
			that.analysis(coordConver);
		});
	},
	
	analysis : function(coordinates) {
		var that = this;
		
		var buffer = mapObj.getMap().getView().getResolution() * 10;
		if(buffer < 1) { buffer = 1; }
		
		var url = CONTEXT_PATH + "/analysis/waterControlValue/analysis.do";
		var params = {
			x : coordinates[0],
			y : coordinates[1],
			buffer : buffer
		};
		$.post(url, params).done(function(result) {
			if(result && result.data) {
				if(result.data == "noPipe") {
					$.messager.alert(that.TITLE, "선택한 지점에 상수관로가 없습니다.");
				}
				else {
					var format = new ol.format.GML3();
		    		var features = [];
		    		
		    		/*var prefixOgc = "";
		    		if(browserUtils.checkIe()) {
		    			prefixOgc = "gml:";
					}*/
		    		//var featureMembers = $.parseXML(result.data).getElementsByTagName(prefixOgc+"featureMember");
		    		var featureMembers = $.parseXML(result.data).getElementsByTagNameNS("http://www.opengis.net/gml", "featureMember");
		    		for(var i=0, len=featureMembers.length; i < len; i++) {
	        			var feature = format.readFeatures(featureMembers[i]);
	        			if(feature.getId()) {
	        				var split = feature.getId().split(".");
		    				feature.tableName = split[0];
		    				feature.fid = split[1];
		        			features.push(feature);
	        			}
	        		}
		    		
		    		if(features.length > 0) {
		    			windowObj.waterControlValueResult.open(features);
		    			that.close();
		    		}
		    		else {
		    			$.messager.alert(that.TITLE, "분석 결과가 없습니다.");
		    		}
				}
			}
			else {
				$.messager.alert(that.TITLE, "차단제수변 분석에 오류가 발생했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "차단제수변 분석에 오류가 발생했습니다.");
		});
		
		
	}
	
};