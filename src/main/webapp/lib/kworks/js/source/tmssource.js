goog.provide('kworks.source.Tms');
goog.require('ol.source.XYZ');
kworks.source.Tms = function(opt_options) {
	var options = {
		projection : opt_options.projection,
		urls : opt_options.urls,
		tileGrid : new ol.tilegrid.TileGrid({
			extent : opt_options.extent,
			origin : opt_options.origin,
			resolutions : opt_options.resolutions
		}),
		tileUrlFunction : function(coordinate) {
			var z = coordinate[0];
			var x = coordinate[1];
			var y = coordinate[2];
			var url = this.urls[goog.math.randomInt(this.urls.length)];
			// 2022.04.22, 이승재 TMS cache 사용하도록 수정
			//return url.replace(/\{z\}/g, z).replace(/\{y\}/g, y).replace(/\{x\}/g, x) + "?nocache=" + Math.random();
			return url.replace(/\{z\}/g, z).replace(/\{y\}/g, y).replace(/\{x\}/g, x);
		}
	};
	goog.base(this, options);
};
goog.inherits(kworks.source.Tms, ol.source.XYZ);