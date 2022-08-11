goog.provide('kworks.view.VWorld');
goog.require('ol.View');

kworks.view.VWorld = function(options) {
	var defaultOptions = {
		projection : new ol.proj.get("EPSG:3857"),
		maxResolution : 156543.0339,
		center : [14243425.793355, 4302305.8698004],
		zoom : 6,
		maxZoom : 22,
		minZoom : 0
	};
	options = $.extend(defaultOptions, options);
	
	goog.base(this, options);
};
goog.inherits(kworks.view.VWorld, ol.View);