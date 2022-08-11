windowObj.drawToolObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "그리기 도구",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "DrawTool",
	
	index : 0,
	
	source : null,
	
	/**
	 * 벡터 레이어
	 */
	layer : null,
	
	/**
	 * 도형 선택 인터렉션
	 */
	interaction : null,
	
	map : null,
	
	/**
	 * 그리기 타입 목록
	 * @type {Array<String>}
	 */
	drawTypes : {
		Point : "심볼", 
		LineString : "선", 
		Rect : "면", 
		Polygon : "다각형", 
		Circle : "원", 
		Text : "텍스트"
	},
	
	init : function(map) {
		var that = this;
		if(map) {
			that.map = map;
		}
		else {
			that.map = mapObj.getMap();
		}
		that.initGis();
		
	},
	
	initGis : function() {
		var that = this;
		
		that.addLayer();
		
		for(var type in that.drawTypes) {
			var options = {};
			if(type == "Rect") {
				options.type = "Circle";
				options.geometryFunction = ol.interaction.Draw.createRegularPolygon(4);
			}
			else if(type == "Text") {
				options.type = "Point";
			}
			else {
				options.type = type;
			}
			options.source = that.source;
			
			var interaction = new kworks.interaction.Draw(options);
			interaction.set("id", "draw"+type);
			interaction.set("name", "draw"+type);
			that.map.addInteraction(interaction);
			interaction.setActive(false);
			
			interaction.on("drawend", function(event) {
				var type = event.target.get("id").replace("draw", "");
				that.addFeature(event.feature, type);
			});
		}
		
		// 선택 Interaction
		that.interaction = new ol.interaction.Select({
			layers : [that.layer]
		});
		that.interaction.set('id','drawSelect');
		that.interaction.set('name','drawSelect');
		that.interaction.on("select", function(event) {
			var feature = null;
			if(event.selected && event.selected.length > 0) {
				feature = event.selected[0];
			}
			if(feature) {
				that.selectFeatureElement(feature);
				
				var features = new ol.Collection();
				features.push(feature);
				
				var modify = that.map.getInteraction("drawModify");
				var translate = that.map.getInteraction("drawTranslate");
				if(modify && modify.getActive()) {
					modify.setFeatures(features);
				}
				else if(translate && translate.getActive()) {
					translate.setFeatures(features);				
				}
			}
		});
		that.map.addInteraction(that.interaction);
		that.interaction.on("propertychange", function(event) {
			if(!event.target.get("active")) {
				event.target.getFeatures().clear();
				that.selectFeatureElement();
			}
		});
		
		var modifyInteraction = new kworks.interaction.Modify({
			//features : that.interaction.getFeatures(),
			features : new ol.Collection(),
			deleteCondition : function(event) {
				return ol.events.condition.shiftKeyOnly(event) && ol.events.condition.singleClick(event);
			}
		});
		modifyInteraction.set('id','drawModify');
		modifyInteraction.set('name','drawSelect');
		that.map.addInteraction(modifyInteraction);
		modifyInteraction.setActive(false);
		
		var translateInteraction = new kworks.interaction.Translate({
			features : new ol.Collection()
		});
		translateInteraction.set('id','drawTranslate');
		translateInteraction.set('name','drawSelect');
		that.map.addInteraction(translateInteraction);
		translateInteraction.setActive(false);
	},
	
	setMap : function(map) {
		var that = this;
		that.map = map;
	},
	
	addLayer : function() {
		var that = this;
		that.source = new ol.source.Vector();
		that.layer = new ol.layer.Vector({
			id : "drawToolLayer",
			source : that.source,
			style : function(feature) {
				return that.createStyle(feature.getProperties());
			}
		});
		that.map.addLayer(that.layer);
	},
	
	addFeature : function(feature, type) {
		var that = this;
		feature.setId(++that.index);
		
		var symbolizer = null;
		switch(type) {
			case "Point" : 
				symbolizer = sldObj.createPointSymbolizer();
				break;
			case "LineString" :
				symbolizer = sldObj.createLineSymbolizer();
				break;
			case "Rect" :
				symbolizer = sldObj.createPolygonSymbolizer();
				break;
			case "Polygon" :
				symbolizer = sldObj.createPolygonSymbolizer();
				break;
			case "Circle" :
				symbolizer = sldObj.createPolygonSymbolizer();
				break;
			case "Text" : 
				symbolizer = sldObj.createTextRule();
				delete symbolizer.maxScale;
				break;
		}
		symbolizer["type"] = type;
		var properties = $.extend(feature.getProperties(), symbolizer);
		feature.setProperties(properties);
		
		if(properties.type == "Text") {
			windowObj.writeTextObj.open({
				onSubmit : function(text) {
					if(text) {
						feature.setProperties($.extend(feature.getProperties(), { label : text }));
						that.addFeatureElement(feature);
					}
					else {
						that.source.removeFeature(feature);
					}
				}
			});
		}
		else {
			that.addFeatureElement(feature);
		}
	},
	
	loadFeatureElements : function() {
		var that = this;
		var features = that.source.getFeatures();
		
		var tagStr = '';
		for(var i=0, len=features.length; i < len; i++) {
			var feature = features[i];
			tagStr += that.createFeatureElement(feature);
		}
		$(".tby_draw_list", that.selector).html(tagStr);
	},
	
	addFeatureElement : function(feature) {
		var that = this;
		$(".tby_draw_list", that.selector).append(that.createFeatureElement(feature));
	},
	
	createFeatureElement : function(feature) {
		var that = this;
		var tagStr = '';
		var properties = feature.getProperties();
		var id = feature.getId();
		tagStr += '<tr data-id="' + id + '" >';
		tagStr += '<td>' + id + '</td>';
		tagStr += '<td>' + that.drawTypes[properties.type] + '</td>';
		tagStr += '<td><a href="#" class="a_setting" data-id="' + id + '" ><img src="' + CONTEXT_PATH + '/images/kworks/map/common/layer_icon2_ov.png" alt="스타일 설정" /></a></td>';
		tagStr += '<td><a href="#" class="a_remove" data-id="' + id + '" ><img src="' + CONTEXT_PATH + '/images/kworks/map/common/boomark_icon1_ov.png" alt="지우기" /></a></td>';
		tagStr += '</tr>';
		return tagStr;
	},
	
	selectFeatureElement : function(feature) {
		var that = this;
		$(".tby_draw_list tr", that.selector).removeClass("on");
		if(feature) {
			$(".tby_draw_list tr[data-id="+ feature.getId() +"]", that.selector).addClass("on");
		}
	},
	
	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		
		var url = CONTEXT_PATH + "/window/drawTool/page.do";
		var windowOptions = {
			width : 370,
			height : 420,
			top : 120,
			right : 50,
			onClose : function() {
				mapToolObj.deactive();
			}
		};
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
			that.loadFeatureElements();
		});
		that.selector = "#" + id;
	},
	
	initUi : function() {
		var that = this;
		
		$(".txt_layer_name", that.selector).textbox({
			required : true,
			width : 230,
			prompt : "레이어명을 입력하세요"
		});
		
	},
	
	bindEvents : function() {
		var that = this;
		
		// 심볼
		$(".a_draw_tool_point", that.selector).click(function(event) {
			that.map.activeInteractions("drawPoint", "drag");
			event.preventDefault();
		});
		
		// 선
		$(".a_draw_tool_linestring", that.selector).click(function(event) {
			that.map.activeInteractions("drawLineString", "drag");
			event.preventDefault();
		});
		
		// 면
		$(".a_draw_tool_rect", that.selector).click(function(event) {
			that.map.activeInteractions("drawRect", "drag");
			event.preventDefault();
		});
		
		// 다각형
		$(".a_draw_tool_polygon", that.selector).click(function(event) {
			that.map.activeInteractions("drawPolygon", "drag");
			event.preventDefault();
		});
		
		// 원
		$(".a_draw_tool_circle", that.selector).click(function(event) {
			that.map.activeInteractions("drawCircle", "drag");
			event.preventDefault();
		});
		
		// 텍스트
		$(".a_draw_tool_text", that.selector).click(function(event) {
			that.map.activeInteractions("drawText", "drag");
			event.preventDefault();
		});
		
		// 선택
		$(".a_draw_tool_select", that.selector).click(function(event) {
			that.map.activeInteractions("drag", ["drawSelect", "drawModify"]);
			event.preventDefault();
		});
		
		// 선택
		$(".a_draw_tool_move", that.selector).click(function(event) {
			that.map.activeInteractions("drag", ["drawSelect", "drawTranslate"]);
			event.preventDefault();
		});
		
		// 객체 전환
		$(".a_draw_tool_convert", that.selector).click(function(event) {
			var features = highlightObj.sources.red.getFeatures();
			if(features.length > 0) {
				for(var i=0, len=features.length; i < len; i++) {
					var feature = features[i];
					var geom = feature.getGeometry().clone();
					
					if(geom instanceof ol.geom.Point || geom instanceof ol.geom.MultiPoint) {
						type = "Point";
					}
					else if(geom instanceof ol.geom.LineString || geom instanceof ol.geom.MultiLineString) {
						type = "LineString";
					}
					else if(geom instanceof ol.geom.Polygon || geom instanceof ol.geom.MultiPolygon) {
						type = "Polygon";
					}
					
					var newFeature = new ol.Feature(geom);
					that.source.addFeature(newFeature);
					that.addFeature(newFeature, type);
				}
				
				$.messager.alert(that.TITLE, "공간객체로 전환되었습니다.");
			}
			else {
				$.messager.alert(that.TITLE, "선택된 객체가 없습니다.");
			}
		});
		
		// 도형 선택
		$(".tby_draw_list", that.selector).on("click", "tr", function() {
			var element = $(this);
			var id = element.attr("data-id");
			var features = that.source.getFeatures();
			for(var i=0, len=features.length; i < len; i++) {
				var feature = features[i];
				if(feature.getId() == id) {
					$(".a_draw_tool_select img", that.selector).trigger("click");
					
					var featureCollection = that.interaction.getFeatures();
					featureCollection.push(feature);
					that.selectFeatureElement(feature);
					break;
				}
			}
			return false;
		});
		
		// 스타일 설정
		$(".tby_draw_list", that.selector).on("click", ".a_setting", function() {
			var element = $(this);
			var selectFeature = null;
			var id = element.attr("data-id");
			var features = that.source.getFeatures();
			for(var i=features.length-1; i >= 0; i--) {
				var feature = features[i];
				if(feature.getId() == id) {
					selectFeature = feature;
				}
			}
			
			if(selectFeature) {
				var properties = selectFeature.getProperties();
				var type = properties.type;
				var rule = {};
				if(properties.minScale) {
					rule.minScale = properties.minScale;
				}
				if(properties.maxScale) {
					rule.maxScale = properties.maxScale;
				}
				
				switch(type) {
					case "Point" : 
						rule.point = properties;
						windowObj.symbolSettingObj.open(rule, {
							onSubmit : function(ruleObj) {
								var properties = ruleObj.point;
								if(ruleObj.minScale) {
									properties.minScale = ruleObj.minScale;
								}
								if(ruleObj.maxScale) {
									properties.maxScale = ruleObj.maxScale;
								}
								selectFeature.setProperties(properties);
							}
						});
						break;
					case "LineString" :
						rule.line = properties;
						windowObj.lineSettingObj.open(rule, {
							noDirection : true,
							onSubmit : function(ruleObj) {
								var properties = ruleObj.line;
								if(ruleObj.minScale) {
									properties.minScale = ruleObj.minScale;
								}
								if(ruleObj.maxScale) {
									properties.maxScale = ruleObj.maxScale;
								}
								selectFeature.setProperties(properties);
							}
						});
						break;
					case "Rect" :
					case "Polygon" :
					case "Circle" :
						rule.polygon = properties;
						windowObj.polygonSettingObj.open(rule, {
							onSubmit : function(ruleObj) {
								var properties = ruleObj.polygon;
								if(ruleObj.minScale) {
									properties.minScale = ruleObj.minScale;
								}
								if(ruleObj.maxScale) {
									properties.maxScale = ruleObj.maxScale;
								}
								selectFeature.setProperties(properties);
							}
						});
						break;
					case "Text" : 
						rule = properties;
						windowObj.labelSettingObj.open(rule, {
							onSubmit : function(ruleObj) {
								var properties = ruleObj;
								selectFeature.setProperties(properties);
							}
						});
						break;
				}
			}
			
			var featureCollection = that.interaction.getFeatures();
			featureCollection.clear();
			that.selectFeatureElement();
			
			return false;
		});
		
		// 스타일 지우기
		$(".tby_draw_list", that.selector).on("click", ".a_remove", function() {
			var element = $(this);
			var id = element.attr("data-id");
			var features = that.source.getFeatures();
			for(var i=features.length-1; i >= 0; i--) {
				var feature = features[i];
				if(feature.getId() == id) {
					that.source.removeFeature(feature);
				}
			}
			element.parent().parent().remove();
			
			var featureCollection = that.interaction.getFeatures();
			featureCollection.clear();
			that.selectFeatureElement();
			
			return false;
		});
		
		// 저장
		$(".a_save", that.selector).click(function() {
			that.persist();
		});
		
		// 닫기
		$(".a_close", that.selector).click(function() {
			mapToolObj.deactive();
		});
	},
	
	createStyle : function(properties) {
		var that = this;
		var options = null;
		var type = properties["type"];
		
		var scale = that.map.getScale();
		var visible = true;
		if(properties.minScale && scale <= properties.minScale) {
			visible = false;
		}
		if(properties.maxScale && scale > properties.maxScale) {
			visible = false;
		}
		
		if(visible) {
			switch(type) {
				case "WellKnown" :
					options = {
						image : that.createWellKnownStyle(properties)
					};
					break;
				case "Point" :
					options = {
						image : that.createPointStyle(properties)
					};
					break;
				case "LineString" :
					options = {
						stroke : that.createStrokeStyle(properties)
					};
					break;
				case "Rect" :
				case "Polygon" :
				case "Circle" :
					options = {
						fill : that.createFillStyle(properties),
						stroke : that.createStrokeStyle(properties)
					};
					break;
				case "Text" :
					options = {
						text : that.createTextStyle(properties)
					};
					break;
			}
		}
		var style = new ol.style.Style(options);
		return style;
	},
	
	createWellKnownStyle : function(properties) {
		var that = this;
		var options = {
            radius : properties.radius,
            fill : that.createFillStyle(properties)
        };
		if(properties.strokeOpacity) {
			options.stroke = that.createStrokeStyle(properties);
		}
		return new ol.style.Circle(options);
	},
	
	createPointStyle : function(properties) {
		var src = null;
		if(properties.resource.indexOf("http") == 0) {
			src = properties.resource;
		}
		else {
			src = MAP.SYMBOL_URL + "/" + properties.resource;
		}
		var style = new ol.style.Icon({
	        src: src,
			anchor : canvasUtils.toAnchor(properties.anchor)
		});
		return style;
	},
	
	createStrokeStyle : function(properties) {
		var style = new ol.style.Stroke({
			color : colorUtils.toColorFromHex(properties.stroke, properties.strokeOpacity),
			width : properties.strokeWidth,
			lineDash : canvasUtils.toLineDash(properties.strokeDasharray, properties.strokeWidth)
		});
		return style;
	},
	
	createFillStyle : function(properties) {
		var style = new ol.style.Fill({
			color : colorUtils.toColorFromHex(properties.fill, properties.fillOpacity)
		});
		return style;
	},
	
	createTextStyle : function(properties) {
		var that = this;
		var style = new ol.style.Text({
			text : properties.label,
			textAlign : canvasUtils.toTextAlign(properties.anchor.charAt(0)),
			textBaseline : canvasUtils.toTextBaseline(properties.anchor.charAt(1)),
			font : canvasUtils.toFont(properties),
			fill : that.createFillStyle(properties),
			stroke : that.createHaloStyle(properties),
			offsetX : properties.displacementX,
			offsetY : properties.displacementY
		});
		return style;
	},
	
	createHaloStyle : function(properties) {
		var style = new ol.style.Stroke({
			color : colorUtils.toColorFromHex(properties.halo, properties.haloOpacity),
			width : properties.haloRadius
		});
		return style;
	},
	
	persist : function() {
		var that = this;
		if(!$(".txt_layer_name", that.selector).textbox("isValid")) {
			$.messager.alert(that.TITLE, "레이어명을 입력하여 주십시오.");
			$(".txt_layer_name", that.selector).textbox("textbox").focus();
			return false;
		}
		
		var features = that.source.getFeatures();
		if(features.length <= 0) {
			$.messager.alert(that.TITLE, "한 개 이상의 도형을 그려 주십시오.");
			return false;
		}
		
		var from = that.map.getView().getProjection();
		var to = ol.proj.get(MAP.PROJECTION);
		if(from != to) {
			for(var i=0, len=features.length; i < len; i++) {
				var feature = features[i];
				var geometry = feature.getGeometry();
				feature.setGeometry(spatialUtils.transform(geometry, from ,to));
			}
		}
		
		for(var i=0, len=features.length; i < len; i++) {
			if(features[i].getProperties().type == "Circle") {
				features[i] = spatialUtils.convertCircleToPoint(features[i]);
			}
		}

		var format = new ol.format.GeoJSON();
		var geojson = format.writeFeatures(features);
		
		var url = CONTEXT_PATH + "/rest/userLayer/add.do";
		var params = {
			lyrNo : 100,
			lyrNm : $(".txt_layer_name", that.selector).textbox("getValue"),
			geojson : geojson
		};
		$.post(url, params).done(function(response) {
			if(response && response.rowCount) {
				menuObj.layerObj.userObj.addUserLayerNode(response.kwsUserLyr);
				that.clear();
				$.messager.alert(that.TITLE, "사용자 레이어가 등록되었습니다. 사용자 레이어는 왼쪽 '레이어' 메뉴 > '사용자' 탭에서 확인할 수 있습니다.");
			}
			else {
				$.messager.alert(that.TITLE, "사용자 레이어를 등록하는데 실패했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "사용자 레이어를 등록하는데 실패했습니다.");
		});
	},
	
	clear : function() {
		var that = this;
		that.source.clear();
		$(".txt_layer_name", that.selector).textbox("clear");
		$(".tby_draw_list", that.selector).html("");
		$("#a_map_tool_bass img").trigger("click");
	},
	
	close : function() {
		var that = this;
		$("#a_map_tool_bass img").trigger("click");
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
		
};