goog.provide('kworks.XRayMap');

goog.require('kworks.Map');

kworks.XRayMap = function(options) {

	this.map_ = options.map;
	
	options.controls = new ol.Collection();
	options.interactions = new ol.Collection();
	
	goog.base(this, options);
	
	this.xrayLayer = options.xrayLayer;
	
	this.updateArc();
	
	this.map_.on("moveend", function(evt) {
		this.change(evt);
	}, this);
	
	this.beforeRender();
};
goog.inherits(kworks.XRayMap, kworks.Map);

kworks.XRayMap.prototype.active = function() {
	var layers = new ol.Collection();
	layers.push(this.xrayLayer);
	this.getLayerGroup().setLayers(layers);
	
	var view = this.map_.getView();
	this.setView(new ol.View({
		projection : view.getProjection(),
		maxResolution : view.maxResolution_
	}));
	this.change();
};


kworks.XRayMap.prototype.change = function(evt) {
	var offset = 5;
	
	var left = parseInt($("#div_xray").css("left"));
	var top = parseInt($("#div_xray").css("top"));

	var width = $("#div_xray").width() / 2;
	var height = $("#div_xray").height() / 2;
	
	var x = left + width + offset;
	var y = top + height + offset;
	
	var center = this.map_.getCoordinateFromPixel([x, y]);
	this.getView().setCenter(center);
	this.moveToScale(this.map_.getScale());
};

kworks.XRayMap.prototype.updateArc = function() {
	var size = $(this.getViewport()).width() / 2;
	$(this.getViewport()).css("background-color", "#ffffff");
	$(this.getViewport()).css("border-radius", size + "px");
	$(this.getViewport()).css("-webkit-border-radius", size + "px");
	$(this.getViewport()).css("-moz-border-radius", size + "px");
};

kworks.XRayMap.prototype.changeSize = function(size) {
	$(this.getTargetElement()).width(size);
	$(this.getTargetElement()).height(size);
	$(this.getViewport()).width(size);
	$(this.getViewport()).height(size);
	this.updateSize();
	this.updateArc();
	this.change();
};

kworks.XRayMap.prototype.changeLayer = function(params) {
	this.xrayLayer.getSource().updateParams(params);
};