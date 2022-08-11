goog.provide('kworks.interaction.MousePosition');

goog.require('ol.interaction.Pointer');

kworks.interaction.MousePosition = function(opt_options) {
	
	var options = goog.isDef(opt_options) ? opt_options : {};
	goog.base(this, options);
	
	this.mode = goog.isDef(options.mode) ? options.mode : "TM";
};
goog.inherits(kworks.interaction.MousePosition, ol.interaction.Interaction);

kworks.interaction.MousePosition.prototype.setMap = function(map) {
	this.map_ = map;
	var viewport = map.getViewport();
	
	var that = this;
	$(viewport).on('mousemove', function(evt) {
		if(that.handler) {
			that.handler(that.getPosition(evt.originalEvent));
		}
	});
};

kworks.interaction.MousePosition.prototype.setMode = function(mode) {
	this.mode = mode;
};

kworks.interaction.MousePosition.prototype.getMode = function() {
	return this.mode;
};

kworks.interaction.MousePosition.prototype.setHandler = function(handler) {
	this.handler = handler;
};

kworks.interaction.MousePosition.prototype.getPosition = function(pixel) {
	var coordinate = this.map_.getCoordinateFromPixel(this.map_.getEventPixel(pixel));
	if(coordinate) {
		return coordinate;
	}
};

kworks.interaction.MousePosition.prototype.getTM = function(coordinate, projection) {
	var coords = new ol.geom.Point(coordinate).transform(this.map_.getView().getProjection(), projection).getCoordinates();
	return " X : " + coords[0].toFixed(3) + " Y : " + coords[1].toFixed(3);
};

kworks.interaction.MousePosition.prototype.getLonLat = function(coordinate, projection) {
	var coords = new ol.geom.Point(coordinate).transform(this.map_.getView().getProjection(), projection).getCoordinates();
	return " 경도 : " + coords[0].toFixed(4) + " 위도 : " + coords[1].toFixed(4);
};

kworks.interaction.MousePosition.prototype.getDMS = function(coordinate, projection) {
	var coords = new ol.geom.Point(coordinate).transform(this.map_.getView().getProjection(), projection).getCoordinates();
	return ol.coordinate.toStringHDMS(coords);
};
