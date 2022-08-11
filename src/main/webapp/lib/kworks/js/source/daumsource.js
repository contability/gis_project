goog.provide('kworks.source.Daum');
goog.require('ol.source.XYZ');
kworks.source.Daum = function(opt_options) {
	var urls = opt_options.urls?opt_options.urls:this.getUrls(opt_options.type);
	var options = {
		projection : opt_options.projection?opt_options.projection:"EPSG:5181",
		urls : urls,
		tileGrid : new ol.tilegrid.TileGrid({
			origin : opt_options.origin?opt_options.origin:[-30000, -60000],
			resolutions : opt_options.resolutions?opt_options.resolutions:[2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1, 0.5, 0.25]
		}),
		tileUrlFunction : function(coordinate) {
			var z = 14 - coordinate[0];
			var x = coordinate[1];
			var y = coordinate[2];
			var url = this.urls[goog.math.randomInt(this.urls.length)];
			return url.replace(/\{z\}/g, z).replace(/\{y\}/g, y).replace(/\{x\}/g, x);
		}
	};
	goog.base(this, options);
};
goog.inherits(kworks.source.Daum, ol.source.XYZ);

kworks.source.Daum.prototype.getUrls = function(type) {
	var urls = null;
	// 영상
	if(type == "satellite") {
		urls = [
			CONTEXT_PATH + "/proxy.do?url=http://map0.daumcdn.net/map_skyview/L{z}/{y}/{x}.jpg?v=160114",
			CONTEXT_PATH + "/proxy.do?url=http://map1.daumcdn.net/map_skyview/L{z}/{y}/{x}.jpg?v=160114",
			CONTEXT_PATH + "/proxy.do?url=http://map2.daumcdn.net/map_skyview/L{z}/{y}/{x}.jpg?v=160114",
			CONTEXT_PATH + "/proxy.do?url=http://map3.daumcdn.net/map_skyview/L{z}/{y}/{x}.jpg?v=160114",
			CONTEXT_PATH + "/proxy.do?url=http://map4.daumcdn.net/map_skyview/L{z}/{y}/{x}.jpg?v=160114"
		];
		
	}
	// 하이브리드
	else if(type == "hybrid") {
		urls = [
			CONTEXT_PATH + "/proxy.do?url=http://map0.daumcdn.net/map_hybrid/9ber/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map1.daumcdn.net/map_hybrid/9ber/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map2.daumcdn.net/map_hybrid/9ber/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map3.daumcdn.net/map_hybrid/9ber/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map4.daumcdn.net/map_hybrid/9ber/L{z}/{y}/{x}.png"
		];
	}
	// 자전거
	else if(type == "bike") {
		urls = [
			CONTEXT_PATH + "/proxy.do?url=http://map0.daumcdn.net/map_bicycle/hybrid/2.00/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map1.daumcdn.net/map_bicycle/hybrid/2.00/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map2.daumcdn.net/map_bicycle/hybrid/2.00/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map3.daumcdn.net/map_bicycle/hybrid/2.00/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map4.daumcdn.net/map_bicycle/hybrid/2.00/L{z}/{y}/{x}.png"
		];
	}
	// 지형도
	else if(type == "topography") {
		urls = [
			CONTEXT_PATH + "/proxy.do?url=http://map0.daumcdn.net/map_shaded_relief/2.00/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map1.daumcdn.net/map_shaded_relief/2.00/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map2.daumcdn.net/map_shaded_relief/2.00/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map3.daumcdn.net/map_shaded_relief/2.00/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map4.daumcdn.net/map_shaded_relief/2.00/L{z}/{y}/{x}.png"
		];
	}
	// 교통
	else if(type == "traffic") {
		urls = [
			CONTEXT_PATH + "/proxy.do?url=http://r0.maps.daum-img.net/mapserver/file/realtimeroad/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://r1.maps.daum-img.net/mapserver/file/realtimeroad/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://r2.maps.daum-img.net/mapserver/file/realtimeroad/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://r3.maps.daum-img.net/mapserver/file/realtimeroad/L{z}/{y}/{x}.png"
		];
	}
	// 로드뷰
	else if(type == "roadview") {
		urls = [
			CONTEXT_PATH + "/proxy.do?url=http://map0.daumcdn.net/map_roadviewline/7.00/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map1.daumcdn.net/map_roadviewline/7.00/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map2.daumcdn.net/map_roadviewline/7.00/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map3.daumcdn.net/map_roadviewline/7.00/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map4.daumcdn.net/map_roadviewline/7.00/L{z}/{y}/{x}.png"
		];
	}
	// 기본
	else {
		urls = [
			CONTEXT_PATH + "/proxy.do?url=http://map0.daumcdn.net/map_2d/9ber/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map1.daumcdn.net/map_2d/9ber/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map2.daumcdn.net/map_2d/9ber/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map3.daumcdn.net/map_2d/9ber/L{z}/{y}/{x}.png",
			CONTEXT_PATH + "/proxy.do?url=http://map4.daumcdn.net/map_2d/9ber/L{z}/{y}/{x}.png"
		];
	}
	return urls; 
};
