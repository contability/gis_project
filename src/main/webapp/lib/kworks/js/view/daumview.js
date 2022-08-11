goog.provide('kworks.view.Daum');
goog.require('ol.View');

kworks.view.Daum = function(options) {
	var defaultOptions = {
		projection : new ol.proj.get("EPSG:5181"),
		maxResolution : 2048,
		center : ol.extent.getCenter([-30000, -60000, 494288, 988576]),
		zoom : 0,
		maxZoom : 16,
		minZoom : 0
	};
	options = $.extend(defaultOptions, options);
	
	goog.base(this, options);
};
goog.inherits(kworks.view.Daum, ol.View);