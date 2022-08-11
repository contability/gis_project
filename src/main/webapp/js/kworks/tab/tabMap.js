/**
 * 지도
 * @type {Object}
 */
var mapObj = {
	
	/**
	 * KWorks 지도
	 * @type {kworks.Map}
	 */
	map : null,
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		
		that.createMap();
		that.addInteractions();
		that.bindEvents();
		
		// 화면 크기 변환 시 지도 영역 크기 조절 이벤트 등록
		mainObj.addResizeHandler("map", function(width, height) {
			that.resizeWindowHandler(width, height);
		});
	},
	
	/**
	 * 지도 객체 반환
	 * @returns {kworks.Map} 지도 객체
	 */
	getMap : function() {
		var that = this;
		return that.map;
	},

	/**
	 * Lks : 항공영상 원점 정보
	 * 
	 */
	getTMSOrigin : function() {
		return mapToolObj.moveAerialMapObj.TILE_ORIGIN;
	},
	
	/**
	 * 지도 생성
	 */
	createMap : function() {
		var that = this;
		that.map = new kworks.Map({
			target : 'div_map',
			layers : [ 
			    new ol.layer.Group({
			    	id : "base_map_group"
			    }),
			    new ol.layer.Group({
					id : "overlay_group"
			    }),			    
			    new ol.layer.Image({
					id : "kc_wms",
					source : new kworks.source.ImageWMS({
						url : CONTEXT_PATH + "/proxy/wms.do",
						params : $.extend(serverObj.getWMSParams(), sldObj.getParams()),
						ratio : 1,
						serverType : MAP.SERVER_TYPE
					})
			    }) 
			],
			view : backgroundMapObj.getView("tms")
		});
	},
	
	/**
	 * 인터렉션 목록 등록
	 */
	addInteractions : function() {
		var that = this;
		that.setMousePositionInteraction();
		that.setDragAndDropInteraction();
	},
	
	/**
	 * 마우스 인터렉션 등록
	 */
	setMousePositionInteraction : function() {
		var that = this;
		var interaction = that.map.getInteraction("mousePosition");
		interaction.setHandler(function(coordinate) {
			var mode = interaction.getMode();
			if(coordinate) {
				var position = null;
				if(mode == "TM") {
					position = interaction.getTM(coordinate, ol.proj.get(MAP.PROJECTION));
				}
				else if(mode == "LONLAT") {
					position = interaction.getLonLat(coordinate, ol.proj.get("EPSG:4326"));
				}
				else if(mode == "DMS") {
					position = interaction.getDMS(coordinate, ol.proj.get("EPSG:4326"));
				}
				else {
					return interaction.getTM(coordinate, ol.proj.get(MAP.PROJECTION));
				}
				$("#a_kworks_coordinate").html(position);
			}
		});
		var mode = userEnvironmentObj.getData("centerPointTy");
		interaction.setMode(mode);
	},
	
	setDragAndDropInteraction : function() {
		var that = this;
		var interaction = that.getMap().getInteraction("dragAndDrop");
		interaction.on("addfeatures", function(event) {
			menuObj.fileObj.import.addLayer(event.file, event.features);
		});
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 확대
		$("#a_map_control_zoomin").click(function() {
			that.map.zoomIn();
			return false;
		});
		
		// 축소
		$("#a_map_control_zoomout").click(function() {
			that.map.zoomOut();
			return false;
		});
		
		// home
		$("#a_map_control_home").click(function() {
			that.moveInit();
			return false;
		});
		
		// 이전
		$("#a_map_control_prev").click(function() {
			that.map.moveMapHistory('prev');
			return false;
		});
		
		// 다음
		$("#a_map_control_next").click(function() {
			that.map.moveMapHistory('next');
			return false;
		});
		
		// 축척
		$(".kworks-scale-line").click(function() {
			windowObj.scaleObj.open();
			return false;
		});
		
		// 좌표
		$("#a_kworks_coordinate").click(function() {
			windowObj.coordinateObj.open();
			return false;
		});
	},
	
	bindMoveEnd : function() {
		var that = this;
		that.map.on("moveend", function() {
			var from = that.getMap().getView().getProjection();
			var to = ol.proj.get("EPSG:4326");
			var center = spatialUtils.transformCooridate(that.getMap().getCenter(), from, to);
			
			$.cookie("initCenterX_"+PROJECT_CODE, center[0]);
			$.cookie("initCenterY_"+PROJECT_CODE, center[1]);
			$.cookie("initSc_"+PROJECT_CODE, parseInt(that.getMap().getScale()));
		});
	},
	
	reloadWMS : function() {
		var that = this;
		return sldObj.putSld().done(function() {
			var layer = that.map.getLayer("kc_wms");
			var source = layer.getSource();
			source.updateParams($.extend({ nocache : Math.random() }, sldObj.getParams()));
		});
	},
	
	resizeWindowHandler : function(windowWidth, windowHeight) {
		var that = this;
		
		var navWidth = $("#div_menu").width();
		var headerHeight = $("#header").height();
		
		var containerWidth = windowWidth - navWidth;
		var containerHeight = windowHeight - headerHeight;
		
		// 지도 영역 갱신
		$("#map_container").width(containerWidth);
		$("#map_container").height(containerHeight);
		if(that.getMap()) that.getMap().updateSize();
		
		var left = menuObj.getPanelSize();
		
		// 지도 상단 객체 위치
		// 지도 툴바 위치
		var offset = 30;
		var mapToolSelector = $("#map .map_control");
		if(userEnvironmentObj.getData("mapToolLc") == "LT") {
			mapToolSelector.css("right", "");
			mapToolSelector.css("left", left+offset);
		}
		else {
			mapToolSelector.css("left", "");
			mapToolSelector.css("right", "40px");
		}
		
		// 지도 하단 객체 위치
		var bottomLeft = left;
		var bottomRight = 0;
		
		// 색인도 크기
		var indexWindowSize = userEnvironmentObj.getData("indexWindowSize");
		$("#div_index_container").width(indexWindowSize).height(indexWindowSize);
		
		// 색인도 위치
		var indexSelector = $("#div_index_container");
		var indexWindowLc = userEnvironmentObj.getData("indexWindowLc");
		if(indexWindowLc == "LB") {
			indexSelector.css("right", "");
			indexSelector.css("left", bottomLeft);
			$("#div_index_open").css("right", "");
			$("#div_index_open").css("left", bottomLeft);
			
			$("#div_index_open a img").attr("src", $("#div_index_open a img").attr("src").replace("right", "left"));
			$(".div_index_close a img", indexSelector).attr("src", $(".div_index_close a img", indexSelector).attr("src").replace("right", "left"));
			
			$(".div_index_mode", indexSelector).css("left", "");
			$(".div_index_mode", indexSelector).css("right", 0);
			
			$(".div_index_close", indexSelector).css("right", "");
			$(".div_index_close", indexSelector).css("left", 0);
			
			bottomLeft += indexSelector.width() + offset;
		}
		else {
			indexSelector.css("left", "");
			indexSelector.css("right", bottomRight);
			$("#div_index_open").css("left", "");
			$("#div_index_open").css("right", bottomRight);
			
			$("#div_index_open a img").attr("src", $("#div_index_open a img").attr("src").replace("left", "right"));
			$(".div_index_close a img", indexSelector).attr("src", $(".div_index_close a img", indexSelector).attr("src").replace("left", "right"));
			
			$(".div_index_mode", indexSelector).css("right", "");
			$(".div_index_mode", indexSelector).css("left", 0);
			
			$(".div_index_close", indexSelector).css("left", "");
			$(".div_index_close", indexSelector).css("right", 0);
			
			bottomRight += indexSelector.width() + offset;
		}
		if(indexMapObj.getMap()) {
			indexMapObj.getMap().updateSize();
		}
		
		// 축척 바 위치
		var scaleSelector = $(mapObj.getMap().getViewport()).find(".kworks-scale-line");
		var scBarLc = userEnvironmentObj.getData("scBarLc");
		if(scBarLc == "LB") {
			scaleSelector.show();
			scaleSelector.css("right", "");
			if(bottomLeft==left) { bottomLeft += offset; }
			scaleSelector.css("left", bottomLeft);
			
			bottomLeft += 200 + offset;
			
			scaleSelector.css('bottom', '40px');
		}
		else if(scBarLc == "RB") {
			scaleSelector.show();
			scaleSelector.css("left", "");
			if(bottomLeft==left) { bottomRight += offset; }
			scaleSelector.css("right", bottomRight);
			
			bottomRight += 200 + offset;
			
			scaleSelector.css('bottom', '40px');
		}
		else {
			scaleSelector.hide();
		}
		
		// 좌표표시 위치
		var scaleSelector = $("#div_coordinate_container");
		var centerPointLc = userEnvironmentObj.getData("centerPointLc");
		if(centerPointLc == "LB") {
			scaleSelector.show();
			scaleSelector.css("right", "");
			if(bottomLeft==left) { bottomLeft += offset; }
			scaleSelector.css("left", bottomLeft);
			
			bottomLeft += scaleSelector.width() + offset;
		}
		else if(centerPointLc == "RB") {
			scaleSelector.show();
			scaleSelector.css("left", "");
			if(bottomLeft==left) { bottomRight += offset; }
			scaleSelector.css("right", bottomRight);
			
			bottomRight += scaleSelector.width() + offset;
		}
		else {
			scaleSelector.hide();
		}
		
	},
	
	/**
	 * 초기(전체) 영역으로 이동
	 */
	moveInit : function() {
		var that = this;
		var map = mapObj.getMap();
		var from = ol.proj.get(MAP.PROJECTION);
		var to = map.getView().getProjection();
		var split = MAP.INIT_EXTENT.split(",");
		for(var i=0, len=split.length; i < len; i++) {
			split[i] = parseInt(split[i]);
		}
		var extent = spatialUtils.transformExtent(split, from ,to);
		that.map.moveToExtent(extent);
	},
	
	getVectorFeatures : function() {
		var that = this;
		var addFeatures = [];
		var layers = that.map.getLayers();
		
		for(var i=0, len=layers.get("length"); i < len; i++) {
			var layer = layers.item(i);
			var layerId = layer.get("id");
			if(layer.getVisible() && layer.getSource && layer.getSource().getFeatures) {
				var features = $.extend([], layer.getSource().getFeatures());
				if(features && features.length > 0) {
					if(layerId && (layerId == "drawToolLayer" || layerId.indexOf("user_") == 0)) {
						// Lks : 그리기 도구 & 사용자 그래픽
						for(var j=0, jLen=features.length; j < jLen; j++) {
							if(features[j].getProperties().type == "Circle") {
								features[j] = spatialUtils.convertCircleToPoint(features[j]);
							}
							addFeatures.push(features[j]);
						}
					}
					else if(layer.getStyle() instanceof Function) {
						// Lks : 공간확인(Orange)
						for(var j=0, jLen=features.length; j < jLen; j++) {
							var feature = features[j];
							var style = null;
							feature.setProperties(that.getPropertiesByStyle(feature, style));
							if(layer.getStyle()(feature) instanceof Array) {
								style = layer.getStyle()(feature)[0];
							}
							else {
								style = layer.getStyle()(feature);
							}
							addFeatures.push(feature);
						}
					}
					else if(layer.getStyle() instanceof ol.style.Style) {
						// Lks : 검색목록 선택  & 측정 & SHAPE 가져오기
						var style = layer.getStyle();
						for(var j=0, jLen=features.length; j < jLen; j++) {
							var feature = features[j];
							feature.setProperties(that.getPropertiesByStyle(feature, style));
							if(feature.getProperties().type == "Circle") {
								feature = spatialUtils.convertCircleToPoint(feature);
							}
							addFeatures.push(feature);
						}
					}
					else {
						for(var j=0, jLen=features.length; j < jLen; j++) {
							var feature = features[j];
							var geometry = feature.getGeometry();
							var properties = null;
							if(geometry instanceof ol.geom.Point) {
								properties = {
									type : "WellKnown",
									radius : 5,
									stroke : "#3399CC",
									strokeOpacity : 1,
									strokeWidth: 1.25,
									strokeDasharray : "solid",
									fill : "#ffffff",
									fillOpacity : 0.4
								};
							}
							else if(geometry instanceof ol.geom.LineString || geometry instanceof ol.geom.MultiLineString) {
								properties = {
									type : "LineString",
									stroke : "#3399CC",
									strokeOpacity : 1,
									strokeWidth: 1.25,
									strokeDasharray : "solid"
								};
							}
							else if(geometry instanceof ol.geom.Polygon || geometry instanceof ol.geom.MultiPolygon || geometry instanceof ol.geom.Circle) {
								properties = {
									type : "Polygon",
									stroke : "#3399CC",
									strokeOpacity : 1,
									strokeWidth: 1.25,
									strokeDasharray : "solid",
									fill : "#ffffff",
									fillOpacity : 0.4
								};
							}
							else {
								console.log("정의되지 않은 타입 입니다.");
							}
							feature.setProperties(properties);
							addFeatures.push(feature);
						}
					}
				}
			}
		}
		var format = new ol.format.GeoJSON();
		return format.writeFeatures(addFeatures);
	},
	
	getPropertiesByStyle : function(feature, style) {
		var fill = style.getFill();
		var image = style.getImage();
		var stroke = style.getStroke();
		var geometry = feature.getGeometry();
		
		// Lks : 채움색
		var fillOpacity = 0;
		var fillColor = null;
		// Lks : 외곽선
		var strokeOpacity = 0;
		var strokeColor = null;
		var strokeWidth = 1;
		var strokeDasharray = "solid";
		
		var properties = null;
		if(geometry instanceof ol.geom.Point) {
			
			if(image instanceof ol.style.Icon) {
				properties = {
					type : "Point",
					resource : image.getSrc(),
					size : image.getSize(),
					anchor : image.anchor_
				};
			}
			else {
				if (image) {
					// Lks : 색상객체 변환 수정
					var color = colorUtils.convertColor(image.getFill().getColor());
					fillColor = goog.color.rgbArrayToHex(color);
					fillOpacity = color[3];
					
					if(image.getStroke()) {
						color = colorUtils.convertColor(image.getStroke().getColor());
						strokeColor = goog.color.rgbArrayToHex(color);
						strokeOpacity = color[3];
						strokeWidth = image.getStroke().getWidth()?image.getStroke().getWidth():2;
						strokeDasharray = image.getStroke().getLineDash()?image.getStroke().getLineDash():"solid";
					}
					else {
						strokeColor = "#ffffff";
						strokeOpacity = 0;
					}
				}
				else {
					strokeColor = "#ffffff";
					strokeOpacity = 0;
				}
				
				properties = {
						type : "WellKnown",
						radius : image.getRadius(),
						stroke : strokeColor,
						strokeOpacity : strokeOpacity,
						strokeWidth: strokeWidth,
						strokeDasharray : strokeDasharray,
						fill : fillColor,
						fillOpacity : fillOpacity
				};
			}
		}
		else if(geometry instanceof ol.geom.LineString || geometry instanceof ol.geom.MultiLineString) {
			if (stroke) {
				// Lks : 색상객체 변환 수정
				var color = colorUtils.convertColor(stroke.getColor());
				strokeColor = color.hex;
				strokeOpacity = color.opacity;
				strokeWidth = stroke.getWidth()?stroke.getWidth():2;
				strokeDasharray = stroke.getLineDash()?stroke.getLineDash():"solid";
			}
			else {
				console.log("Stroke 정의가 없습니다.");
				strokeColor = "#ffffff";
				strokeOpacity = 0;
			}

			properties = {
					type : "LineString",
					stroke : strokeColor,
					strokeOpacity : strokeOpacity,
					strokeWidth: strokeWidth,
					strokeDasharray : strokeDasharray
			};
		}
		else if(geometry instanceof ol.geom.Polygon || geometry instanceof ol.geom.MultiPolygon || geometry instanceof ol.geom.Circle) {
			var type = null;
			if(geometry instanceof ol.geom.Circle) {
				type = "Circle";
				feature = spatialUtils.convertCircleToPoint(feature);
			}
			else {
				type = "Polygon";
			}
			
			var color = null; 
			if (fill) {
				// Lks : 색상객체 변환 수정
				color = colorUtils.convertColor(fill.getColor());
				fillColor = color.hex;
				fillOpacity = color.opacity;
			}
			else {
				fillColor = "#ffffff";
				fillOpacity = 0;
			}
			
			if (stroke) {
				// Lks : 색상객체 변환 수정
				color = colorUtils.convertColor(stroke.getColor());
				strokeColor = color.hex;
				strokeOpacity = color.opacity;
				strokeWidth = stroke.getWidth()?stroke.getWidth():2;
				strokeDasharray = stroke.getLineDash()?stroke.getLineDash():"solid";
			}
			else {
				strokeColor = "#ffffff";
				strokeOpacity = 0;
			}
			
			properties = {
				type : type,
				stroke : strokeColor,
				strokeOpacity : strokeOpacity,
				strokeWidth: strokeWidth,
				strokeDasharray : strokeDasharray,
				fill : fillColor,
				fillOpacity : fillOpacity
			};
		}
		else {
			console.log("정의되지 않은 타입 입니다.");
		}
		
		return properties;
	},
	
	/**
	 * 특정 영역이 이미지목록 반환
	 */
	getClipImages : function(maps, extent) {
		var that = this;
		
		var deferred = $.Deferred();
		
		var promises = [];
		var baseMap = that.getMap();
		var resolution = baseMap.getView().getResolutionForExtent(extent, baseMap.getSize());
		var buffer = resolution * 20;
		var clipExtent = ol.extent.buffer(extent, buffer);
		if(buffer < 20) {
			buffer = 20;
		}
		var bufferExtent = ol.extent.buffer(extent, buffer);
		
		that.getClipImage(baseMap, clipExtent);
		promises.push(baseMap.deferred.promise());
		for(var i=0, len=maps.get("length"); i < len; i++) {
			var map = maps.item(i);
			that.getClipImage(map, clipExtent);
			promises.push(map.deferred.promise());
		}
		baseMap.moveToExtent(bufferExtent);
		
		$.when.all(promises).then(function(datas) {
			deferred.resolve(datas);
			that.map.deferred = null;
			for(var i=0, len=maps.get("length"); i < len; i++) {
				maps.item(i).deferred = null;
			}
		});
		return deferred.promise();
	},
	
	/**
	 * 특정 영역 이미지 반환
	 */
	getClipImage : function(map, clipExtent) {
		var that = this;
		if(!map.deferred) {
			map.deferred = $.Deferred();
		}
		map.once("postcompose", function(event) {
			that.getClipImageTimeout(map, event.context.canvas, clipExtent);
		});
		map.renderSync();
	},
	
	/**
	 * 타일을 덜 불러올 경우 재귀 호출로 다시 요청
	 */
	getClipImageTimeout : function(map, canvas, clipExtent) {
		var that = this;
		setTimeout(function() {
			if(map.tileQueue_.isEmpty()) {
				var layers = map.getLayers();
				var checked = false;
				for(var i=0, len=layers.get("length"); i < len; i++) {
					var source = layers.item(i).getSource();
					if(source instanceof ol.source.XYZ) {
						checked = true;
						break;
					}
				}
				var format = null;
				if(checked) {
					format = "image/jpeg";
				}
				else {
					format = "image/png";
				}
				
				var mapExtent = map.getExtent();
				var resolution = map.getView().getResolution();

				var left = Math.floor((clipExtent[0] - mapExtent[0]) / resolution) - 10;
				var bottom = Math.floor((clipExtent[1] - mapExtent[1]) / resolution) - 10;
				
				var width = Math.floor((clipExtent[2] - mapExtent[0]) / resolution) - left + 20;
				var height = Math.floor((clipExtent[3] - mapExtent[1]) / resolution) - bottom + 20;

				var cropCanvas = $("<canvas width='"+width+"px' height='"+height+"px' />");
				var cropContext = cropCanvas[0].getContext("2d");
				cropContext.drawImage(canvas, left, bottom, width, height, 0, 0, width, height);

				var data = cropCanvas[0].toDataURL(format);
				map.deferred.resolve(data);
			}
			else {
				return that.getClipImageTimeout(map, canvas, clipExtent);
			}
		}, 500);
	}
};

var indexMapObj = {
	
	/**
	 * 인덱스 지도
	 * @type {kworks.Map}
	 */
	map : null,
	
	init : function() {
		var that = this;
		that.createMap();
		that.bindEvents();
	},
	
	createMap : function() {
		var that = this;
		that.map = new kworks.IndexMap({
			map : mapObj.getMap(),
			target : 'index_map',
			layers : [
				new ol.layer.Image({
					id : "kc_index",
		            source: new kworks.source.ImageWMS({
		              url: CONTEXT_PATH + "/proxy/wms.do",
		              params : $.extend(serverObj.getWMSParams(), {
		                  'LAYERS' : serverObj.getBlankLayers(),
		                  'STYLES' : serverObj.getBlankLayers(),
		            	  'FORMAT' : "image/jpeg"
		              }),
		              ratio : 1,
		              serverType: MAP.SERVER_TYPE
		            })
		        })],
				mode : "MAX",
				view : new ol.View({
					projection : new ol.proj.get(MAP.PROJECTION),
					maxResolution : INDEX_MAP.MAX_RESOLUTION,
					center : [INDEX_MAP.CENTER_X, INDEX_MAP.CENTER_Y],
					zoom : 0
				})
		});
		
		var layer = that.map.getLayer("kc_index");
		var layerNames = [caseUtils.getText(INDEX_MAP.LAYER)];
		sldObj.reloadWMSFromSld("IndexMap", "인덱스맵", layer, layerNames);
		
	},
	
	bindEvents : function() {
		var that = this;
		
		// 모드 변경
		$("#a_index_mode").click(function() {
			if(that.map.getMode() == "MAX") {
				that.map.setMode("EXTENT");
				$(this).find("img").attr("src", $(this).find("img").attr("src").replace("_on", "_off"));
			}	
			else {
				that.map.setMode("MAX");
				$(this).find("img").attr("src", $(this).find("img").attr("src").replace("_off", "_on"));                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
			}
			return false;
		});
		
		// 인덱스맵 열기
		$("#a_index_open").click(function() {
			$("#div_index_container").show();
			$("#div_index_open").hide();
			if(that.map) that.map.updateSize();
			return false;
		});
		
		// 인덱스맵 닫기
		$("#a_index_close").click(function() {
			$("#div_index_container").hide();
			$("#div_index_open").show();
			return false;
		});
	},
	
	getMap : function() {
		var that = this;
		return that.map;
	}
	
};