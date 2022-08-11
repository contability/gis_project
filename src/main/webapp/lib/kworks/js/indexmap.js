goog.provide('kworks.IndexMap');

goog.require('kworks.Map');

kworks.IndexMap = function(options) {

	this.map_ = options.map;
	this.mode = goog.isDef(options.mode) ? options.mode : 'max';
	
	options.controls = new ol.Collection();
	options.interactions = new ol.Collection();
	
	goog.base(this, options);

	this.initCenter = this.getView().getCenter();
	
	this.bboxLayer = new ol.layer.Vector({
		source : new ol.source.Vector(),
		style : new ol.style.Style({
            fill : new ol.style.Fill({
                color : 'rgba(0, 0, 255, 0.1)'
            }),
            stroke : new ol.style.Stroke({
                color : '#0000ff',
                width : 2,
            })
        })
	});
	this.addLayer(this.bboxLayer);
	
	var interaction = new ol.interaction.DragBox({
		style: new ol.style.Style({
		    stroke: new ol.style.Stroke({
		      color: [255, 0, 0, 1]
		    })
		 })
	});
	this.addInteraction(interaction);
	
	interaction.on("boxstart", function(evt) {
		this.startCoordinate = evt.coordinate;
	}, this);
	interaction.on("boxend", function(evt) {
		var extent = ol.extent.boundingExtent([this.startCoordinate, evt.coordinate]);
		var projection = this.getView().getProjection();
		var targetProjection = this.map_.getView().getProjection();
		if(projection != targetProjection) {
			var geom = ol.geom.Polygon.fromExtent(extent);
			extent = geom.transform(projection, targetProjection).getExtent();
		}
		this.map_.getView().fit(extent, this.map_.getSize());
	}, this);
	
	// 현재 지도 영역 표시
	this.map_.on("moveend", function(evt) {
		this.change();
	}, this);
	
};
goog.inherits(kworks.IndexMap, kworks.Map);

kworks.IndexMap.prototype.change = function() {
	var projection = this.map_.getView().getProjection();
	var targetProjection = this.getView().getProjection();
	
	var extent = this.map_.getExtent();
	
	if(projection != targetProjection) {
		var geom = ol.geom.Polygon.fromExtent(extent);
		extent = geom.transform(projection, targetProjection).getExtent();
	}

	if(this.mode == "MAX") {
		this.getView().setCenter(this.initCenter);
		this.getView().setResolution(this.getView().maxResolution_);
	}
	if(this.mode == "EXTENT") {
		var len = Math.abs(extent[2] - extent[0]);
		var newExtent = ol.extent.buffer(extent, len);
		this.getView().fit(newExtent, this.getSize());
	}
	var polygon = ol.geom.Polygon.fromExtent(extent);
	var feature = new ol.Feature(polygon);
	
	this.bboxLayer.getSource().clear();
	this.bboxLayer.getSource().addFeature(feature);
};

kworks.IndexMap.prototype.setMode = function(mode) {
	this.mode = mode;
	this.change();
};

kworks.IndexMap.prototype.getMode = function() {
	return this.mode;
};
