var roadVideoObj = {

	/**
	 * 선택자
	 * 
	 * @type {String}
	 */
	selector : "#div_road_video_result",
		
	layer : null,
	
	source : null,
	
	interaction : null,
	
	isCreated : false,
	
	regIdn : null,
	
	pathIdn : null,
	
	
	init : function(reg, path, features, times) {
		var that = this;
		that.addLayer();
		that.addInteractions();
		that.addFeatures(reg, path, features, times);
	},
	
	active : function() {
		var that = this;
		if(!that.isCreated) {
			that.init();
			that.isCreated = true;
		}
		else {
			that.layer.setVisible(true);
			highlightObj.moveFeatures(that.source.getFeatures(), {
				projection : ol.proj.get(MAP.PROJECTION)
			});
		}
		mapObj.getMap().activeInteractions("drag", "roadVideo");
	},
	
	deactive : function() {
		var that = this;
		
		if (that.layer)
			that.layer.setVisible(false);
		
		$("#road_video_view").attr("src", "");
		roadVideoObj.pathObj.destroy();
		highlightObj.removeFeatures(that.CLASS_NAME);
	},

	addLayer : function() {
		var that = this;
		that.source = new ol.source.Vector();
		that.layer = new ol.layer.Vector({
			source : that.source,
			style : new ol.style.Style({
				fill : new ol.style.Fill({
					color : 'rgba(255, 128, 0, 0.6)'
				}),
				stroke : new ol.style.Stroke({
					color : 'rgba(255, 128, 0, 1)',
					width : 6
				}),
				image : new ol.style.Circle({
					radius : 7,
					fill : new ol.style.Fill({
						color : 'rgba(255, 0, 0, 1)'
					})
				})
			})
		});
		mapObj.getMap().addLayer(that.layer);
	},
	
	addInteractions : function() {
		var that = this;

		that.interaction = new kworks.interaction.Select({
			layers : [that.layer]
		});
		that.interaction.set("id", "roadVideo");
		that.interaction.set("name", "roadVideo");
		mapObj.getMap().addInteraction(that.interaction);
	},
	
	addFeatures : function(reg, path, features, times) {
		var that = this;
		
		that.regIdn = reg;
		that.pathIdn = path;
		that.source.addFeatures(features);
		if (features && features.length > 0) {
			// 추가
			highlightObj.showRedFeatures(
				that.CLASS_NAME, 
				features,
				true,
				{
					projection : ol.proj.get(MAP.PROJECTION)
				}
			);
			
			// 이동
			highlightObj.moveFeatures(features, {
				projection : ol.proj.get(MAP.PROJECTION)
			});
			
			// 설정
			that.interaction.removeOverlayFeatures();
			roadVideoObj.pathObj.create(features[0], times);
		}
		
		// 동영상 
		var videoUrl = CONTEXT_PATH + "/roadRegstr/" + reg + "/" + path + "/roadVideo.do";
		$("#road_video_view").attr("src", videoUrl);
		
	}
};

roadVideoObj.pathObj = {
		
	feature : null,
		
	coordinates : null,
	
	timeoutFn : null,
	
	timeout : null,
	
	index : 0,
	
	create : function(feature, times) {
		var that = this;
		that.coordinates = feature.getGeometry().getCoordinates();
		that.timeout = times;
		
		that.createFeature();
		that.move();
	},
	
	destroy : function() {
		var that = this;
		clearTimeout(that.timeoutFn);
		
		that.index = 0;
		that.coordinates = [];
		if(that.feature) {
			roadVideoObj.source.removeFeature(that.feature);
			that.feature = null;
		}
	},	
	
	move : function() {
		var that = this;
		if(that.index < that.coordinates.length-1) {
			that.timeoutFn = setTimeout(function() {
				that.index++;
				that.createFeature();
				
				that.move();
			}, parseFloat(that.timeout[that.index]) * 1000);
		}
	},
	
	createFeature : function() {
		var that = this;
		
		if(that.feature) {
			roadVideoObj.source.removeFeature(that.feature);
			that.feature = null;
		}
		
		if(that.coordinates && that.coordinates.length > 0) {
			that.feature = new ol.Feature(new ol.geom.Point(that.coordinates[that.index]));
			roadVideoObj.source.addFeatures([that.feature]);
		}
	}
};