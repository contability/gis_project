goog.provide('kworks.view.Naver');
goog.require('ol.View');

kworks.view.Naver = function(options) {
	var defaultOptions = {
		projection : new ol.proj.get("EPSG:5179"),
		maxResolution : 2048,
		center : ol.extent.getCenter([90112, 1192896, 1990673, 2761664]),
		zoom : 0,
		maxZoom : 16,
		minZoom : 0
	};
	options = $.extend(defaultOptions, options);
	
	goog.base(this, options);
};
goog.inherits(kworks.view.Naver, ol.View);