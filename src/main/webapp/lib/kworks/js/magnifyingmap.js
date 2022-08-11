goog.provide('kworks.MagnifyingMap');

goog.require('kworks.Map');

kworks.MagnifyingMap = function(options) {
	this.map_ = options.map;
	
	options.controls = new ol.Collection();
	options.interactions = new ol.Collection();
	
	goog.base(this, options);

	this.zoom = goog.isDef(options.zoom) ? options.zoom : 2;
	
	this.updateArc();
	
	this.map_.on("moveend", function(evt) {
		this.change();
	}, this);
};
goog.inherits(kworks.MagnifyingMap, kworks.Map);

kworks.MagnifyingMap.prototype.active = function() {
	var layers = this.map_.getLayers();
	this.getLayerGroup().setLayers(layers);
	var view = this.map_.getView();
	this.setView(new ol.View({
		projection : view.getProjection(),
		maxResolution : view.maxResolution_
	}));
	this.change();
};

kworks.MagnifyingMap.prototype.change = function() {
	var offset = 5;
	
	var left = parseInt($("#div_zoom").css("left"));
	var top = parseInt($("#div_zoom").css("top"));
	
	var width = $("#div_zoom").width() / 2;
	var height = $("#div_zoom").height() / 2;
	
	var x = left + width + offset;
	var y = top + height + offset;
	
	var center = this.map_.getCoordinateFromPixel([x, y]);
	this.getView().setCenter(center);
	this.moveToScale(this.map_.getScale()/this.zoom);
};

kworks.MagnifyingMap.prototype.updateArc = function() {
	var size = $(this.getViewport()).width() / 2;
	$(this.getViewport()).css("background-color", "#ffffff");
	$(this.getViewport()).css("border-radius", size + "px");
	$(this.getViewport()).css("-webkit-border-radius", size + "px");
	$(this.getViewport()).css("-moz-border-radius", size + "px");
};

kworks.MagnifyingMap.prototype.changeSize = function(size) {
	$(this.getTargetElement()).width(size);
	$(this.getTargetElement()).height(size);
	$(this.getViewport()).width(size);
	$(this.getViewport()).height(size);
	this.updateSize();
	this.updateArc();
	this.change();
};

kworks.MagnifyingMap.prototype.changeZoom = function(zoom) {
	this.zoom = zoom;
	this.change();
};