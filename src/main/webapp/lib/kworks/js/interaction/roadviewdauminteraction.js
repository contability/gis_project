goog.provide('kworks.interaction.RoadViewDaum');
goog.require('ol.interaction.Draw');

kworks.interaction.RoadViewDaum = function(opt_options) {
	this.target = opt_options.target;
	
	var id = goog.isDef(opt_options.id) ? opt_options.id : "roadview";
	var name = goog.isDef(opt_options.name) ? opt_options.name : "roadview";
	
	this.executeHandler = goog.isDef(opt_options.executeHandler) ? opt_options.executeHandler : null;	// miriele : refactoring roadview ux
	this.roadview = daum.maps.Roadview(document.getElementById(this.target));
	this.roadviewClient = new daum.maps.RoadviewClient();
	
	var drawSource = new ol.source.Vector();

	this.drawLayer = new ol.layer.Vector({
		id : "drawLayer",
		source : drawSource,
		style: new ol.style.Style({
            image: new ol.style.Icon({
            	src : CONTEXT_PATH + "/images/kworks/map/roadview/roadbot0.png",
            	opacity : 0.7
            })
    	})
	});

	var options = {
		source : drawSource,
		type : "Point",
		style: new ol.style.Style({
            image: new ol.style.Icon({
            	src : CONTEXT_PATH + "/images/kworks/map/roadview/roadview_wk.png"
            })
    	})	
	};
	
	goog.base(this, options);
	
	this.set("id", id);
	this.set("name", name);
	
	this.on('propertychange', function(evt) {
		if(this.get("active")) {
			this.on("drawend", this.loadRoadView);
			this.map_.removeLayer(this.drawLayer);
			this.map_.addLayer(this.drawLayer);
		}
		else {
			this.un("drawend", this.loadRoadView);
		}
	});
};
goog.inherits(kworks.interaction.RoadViewDaum, ol.interaction.Draw);

kworks.interaction.RoadViewDaum.prototype.setMap = function(map) {
	goog.base(this, 'setMap', map);
	map.addLayer(this.drawLayer);
	
	var that = this;
	var roadview = this.roadview;
	daum.maps.event.addListener(roadview, 'position_changed', function(){
		that.changePosition();
    });
	
	daum.maps.event.addListener(roadview, 'viewpoint_changed', function(){
		that.changeAngle();
	});
	
	this.updateState_();
};

kworks.interaction.RoadViewDaum.prototype.changePosition = function() {
	var that = this;
	
	that.clear();
	if(!that.getActive()) return;
	
	var mapProjection = that.map_.getView().getProjection();
	var position = that.roadview.getPosition();
    var geom = new ol.geom.Point([position.getLng(), position.getLat()]).transform(ol.proj.get("EPSG:4326"), mapProjection);
    var feature = new ol.Feature(geom);
    that.drawLayer.getSource().addFeature(feature);
    
    if(!ol.extent.containsCoordinate(that.map_.getExtent(), geom.getCoordinates())) {
    	that.map_.moveToCenter(geom.getCoordinates());
    }
    
    that.changeAngle();
};

kworks.interaction.RoadViewDaum.prototype.changeAngle = function() {
	var that = this;
	
	if(!that.getActive()) return;
	var viewpoint = this.roadview.getViewpoint();
	var angle = viewpoint.pan;
	var threshold = 22.5;
	for(var i=0; i<16; i++){		        	
    	if(angle > (threshold * i) && angle < (threshold * (i + 1))) {
    		if(this.drawLayer.getSource().getFeatures().length > 0) {
    			var feature = this.drawLayer.getSource().getFeatures()[0];
    			feature.setStyle(new ol.style.Style({
    	            image: new ol.style.Icon({
    	            	src : CONTEXT_PATH + "/images/kworks/map/roadview/roadbot" + i + ".png",
    	            	opacity : 0.7
    	            })
    	    	}));
    		}
    	}
	};
};

kworks.interaction.RoadViewDaum.prototype.loadRoadView = function(evt) {
	var feature = evt.feature;
	var coordinates = feature.getGeometry().clone().transform(this.map_.getView().getProjection(), ol.proj.get("EPSG:4326")).getCoordinates();
	var position = new daum.maps.LatLng(coordinates[1], coordinates[0]);
	
	var div = $("#"+this.target);
	var roadview = this.roadview;
	
	var that = this;

	this.roadviewClient.getNearestPanoId(position, 50, function(panoId, obj) {
		if(panoId) {
			if(!div.is(":visible")) {
				div.show(function() {
					roadview.setPanoId(panoId, position);
					roadview.relayout();
					if(that.executeHandler) {
						that.executeHandler();
					}
				});
			}
			else {
				roadview.setPanoId(panoId, position);
				roadview.relayout();
				if(that.executeHandler) {
					that.executeHandler();
				}
			}
		}
		else {
			that.clear();
		}
	});
	
};

kworks.interaction.RoadViewDaum.prototype.clear = function() {
	var features = this.drawLayer.getSource().getFeatures();
	for(var i=features.length-1; i >= 0; i--) {
		this.drawLayer.getSource().removeFeature(features[i]);
	}
};