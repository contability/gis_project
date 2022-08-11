goog.provide('kworks.source.VWorld');
goog.require('ol.source.XYZ');
kworks.source.VWorld = function(opt_options) {
	var options = {
		projection : opt_options.projection,
		urls : opt_options.urls,
		tileGrid : new ol.tilegrid.TileGrid({
			origin : opt_options.origin,
			resolutions : opt_options.resolutions
		}),
		tileUrlFunction : function(coordinate) {
			var z = coordinate[0];
			var x = coordinate[1];
			var limit = Math.pow(2, z);
			var y = limit - coordinate[2] - 1;
			var url = this.urls[goog.math.randomInt(this.urls.length)];
			return url.replace(/\{z\}/g, z).replace(/\{y\}/g, y).replace(/\{x\}/g, x);
		}
	};
	goog.base(this, options);
};
goog.inherits(kworks.source.VWorld, ol.source.XYZ);

kworks.source.VWorld.prototype.getUrls = function(type) {
	var urls = null;
	// 영상
	if(type == "satellite") {
		urls = [
//old		        
			CONTEXT_PATH + "/proxy.do?url=http://xdworld.vworld.kr:8080/2d/Satellite/201301/{z}/{x}/{y}.jpeg"
		];
		
	}
	// 하이브리드
	else if(type == "hybrid") {
		urls = [
// old		        
//			CONTEXT_PATH + "/proxy.do?url=http://xdworld.vworld.kr:8080/2d/Hybrid/201411/{z}/{x}/{y}.png"

// 2016.10.24 update
			CONTEXT_PATH + "/proxy.do?url=http://xdworld.vworld.kr:8080/2d/Hybrid/201512/{z}/{x}/{y}.png"
		];
	}
	// 야간
	else if(type == "midnight") {
		urls = [
//old		        
//			CONTEXT_PATH + "/proxy.do?url=http://xdworld.vworld.kr:8080/2d/midnight/201411/{z}/{x}/{y}.png"

//2016.10.24 update
			CONTEXT_PATH + "/proxy.do?url=http://xdworld.vworld.kr:8080/2d/midnight/201512/{z}/{x}/{y}.png"
		];
	}
	// 2D화색
	else if(type == "gray") {
		urls = [
//old		        
//			CONTEXT_PATH + "/proxy.do?url=http://xdworld.vworld.kr:8080/2d/gray/201411/{z}/{x}/{y}.png"

//2016.10.24 update
			CONTEXT_PATH + "/proxy.do?url=http://xdworld.vworld.kr:8080/2d/gray/201512/{z}/{x}/{y}.png"
		];
	}
	// 기본
	else {
		urls = [
//old		        
//			CONTEXT_PATH + "/proxy.do?url=http://xdworld.vworld.kr:8080/2d/Base/201411/{z}/{x}/{y}.png"

//2016.10.24 update
			CONTEXT_PATH + "/proxy.do?url=http://xdworld.vworld.kr:8080/2d/Base/201512/{z}/{x}/{y}.png"
		];
	}
	return urls; 
};
