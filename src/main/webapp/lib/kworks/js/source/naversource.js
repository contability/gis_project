goog.provide('kworks.source.Naver');
goog.require('ol.source.XYZ');
kworks.source.Naver = function(opt_options) {
	var options = {
		projection : opt_options.projection,
		urls : opt_options.urls,
		tileGrid : new ol.tilegrid.TileGrid({
			origin : opt_options.origin,
			resolutions : opt_options.resolutions
		}),
		tileUrlFunction : function(coordinate) {
			var z = coordinate[0] + 1;
			var x = coordinate[1];
			var y = coordinate[2];
			var url = this.urls[goog.math.randomInt(this.urls.length)];
			return url.replace(/\{z\}/g, z).replace(/\{y\}/g, y).replace(/\{x\}/g, x);
		}
	};
	goog.base(this, options);
	
};
goog.inherits(kworks.source.Naver, ol.source.XYZ);

kworks.source.Naver.prototype.getUrls = function(type) {
	var urls = null;
	// 영상
	if(type == "satellite") {
		urls = [
//			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/130/0/1/{z}/{x}/{y}/bl_st_bg",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/130/0/1/{z}/{x}/{y}/bl_st_bg",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/130/0/1/{z}/{x}/{y}/bl_st_bg",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/130/0/1/{z}/{x}/{y}/bl_st_bg"

// 2016.10.24 update
//			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/157/0/1/{z}/{x}/{y}/bl_st_bg",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/157/0/1/{z}/{x}/{y}/bl_st_bg",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/157/0/1/{z}/{x}/{y}/bl_st_bg",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/157/0/1/{z}/{x}/{y}/bl_st_bg"
			
			// 2019.10.25 update
			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/198/0/1/{z}/{x}/{y}/bl_st_bg",
			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/198/0/1/{z}/{x}/{y}/bl_st_bg",
			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/198/0/1/{z}/{x}/{y}/bl_st_bg",
			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/198/0/1/{z}/{x}/{y}/bl_st_bg"
		];
		
	}
	// 하이브리드
	else if(type == "hybrid") {
		urls = [
//			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/130/0/0/{z}/{x}/{y}/empty/ol_st_rd/ol_st_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/130/0/0/{z}/{x}/{y}/empty/ol_st_rd/ol_st_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/130/0/0/{z}/{x}/{y}/empty/ol_st_rd/ol_st_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/130/0/0/{z}/{x}/{y}/empty/ol_st_rd/ol_st_an"

// 2016.10.24 update
//			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/157/0/0/{z}/{x}/{y}/empty/ol_st_rd/ol_st_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/157/0/0/{z}/{x}/{y}/empty/ol_st_rd/ol_st_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/157/0/0/{z}/{x}/{y}/empty/ol_st_rd/ol_st_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/157/0/0/{z}/{x}/{y}/empty/ol_st_rd/ol_st_an"
			
			// 2019.10.25 update
			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/198/0/0/{z}/{x}/{y}/empty/ol_st_rd/ol_st_an",
			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/198/0/0/{z}/{x}/{y}/empty/ol_st_rd/ol_st_an",
			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/198/0/0/{z}/{x}/{y}/empty/ol_st_rd/ol_st_an",
			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/198/0/0/{z}/{x}/{y}/empty/ol_st_rd/ol_st_an"
		];
	}
	// 자전거
	else if(type == "bike") {
		urls = [
//			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/130/0/0/{z}/{x}/{y}/empty/ol_bc_hb",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/130/0/0/{z}/{x}/{y}/empty/ol_bc_hb",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/130/0/0/{z}/{x}/{y}/empty/ol_bc_hb",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/130/0/0/{z}/{x}/{y}/empty/ol_bc_hb"

// 2016.10.24 update
//			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/157/0/0/{z}/{x}/{y}/empty/ol_bc_hb",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/157/0/0/{z}/{x}/{y}/empty/ol_bc_hb",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/157/0/0/{z}/{x}/{y}/empty/ol_bc_hb",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/157/0/0/{z}/{x}/{y}/empty/ol_bc_hb"
			
			// 2019.10.25 update
			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/198/0/0/{z}/{x}/{y}/empty/ol_bc_hb",
			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/198/0/0/{z}/{x}/{y}/empty/ol_bc_hb",
			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/198/0/0/{z}/{x}/{y}/empty/ol_bc_hb",
			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/198/0/0/{z}/{x}/{y}/empty/ol_bc_hb"
		];
	}
	// 지형도
	else if(type == "topography") {
		urls = [
//			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/130/0/0/{z}/{x}/{y}/bl_tn_bg/ol_vc_bg/ol_vc_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/130/0/0/{z}/{x}/{y}/bl_tn_bg/ol_vc_bg/ol_vc_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/130/0/0/{z}/{x}/{y}/bl_tn_bg/ol_vc_bg/ol_vc_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/130/0/0/{z}/{x}/{y}/bl_tn_bg/ol_vc_bg/ol_vc_an"

// 2016.10.24 update
//			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/157/0/0/{z}/{x}/{y}/bl_tn_bg/ol_vc_bg/ol_vc_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/157/0/0/{z}/{x}/{y}/bl_tn_bg/ol_vc_bg/ol_vc_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/157/0/0/{z}/{x}/{y}/bl_tn_bg/ol_vc_bg/ol_vc_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/157/0/0/{z}/{x}/{y}/bl_tn_bg/ol_vc_bg/ol_vc_an"

//2019.10.25 test
			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/198/0/0/{z}/{x}/{y}/bl_tn_bg/ol_vc_bg/ol_vc_an",
			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/198/0/0/{z}/{x}/{y}/bl_tn_bg/ol_vc_bg/ol_vc_an",
			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/198/0/0/{z}/{x}/{y}/bl_tn_bg/ol_vc_bg/ol_vc_an",
			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/198/0/0/{z}/{x}/{y}/bl_tn_bg/ol_vc_bg/ol_vc_an"
		];
	}
	// 교통
	else if(type == "traffic") {
		urls = [
//			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/130/1073289/0/{z}/{x}/{y}/empty/ol_tr_rt/ol_vc_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/130/1073289/0/{z}/{x}/{y}/empty/ol_tr_rt/ol_vc_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/130/1073289/0/{z}/{x}/{y}/empty/ol_tr_rt/ol_vc_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile4map.naver.net/get/130/1073289/0/{z}/{x}/{y}/empty/ol_tr_rt/ol_vc_an"

// 2016.10.24 update
//			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/157/1283022/0/{z}/{x}/{y}/empty/ol_tr_rt",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/157/1283022/0/{z}/{x}/{y}/empty/ol_tr_rt",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/157/1283022/0/{z}/{x}/{y}/empty/ol_tr_rt",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/157/1283022/0/{z}/{x}/{y}/empty/ol_tr_rt"
			
			// 2019.10.26 update
			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/198/1283022/0/{z}/{x}/{y}/empty/ol_tr_rt",
			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/198/1283022/0/{z}/{x}/{y}/empty/ol_tr_rt",
			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/198/1283022/0/{z}/{x}/{y}/empty/ol_tr_rt",
			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/198/1283022/0/{z}/{x}/{y}/empty/ol_tr_rt"
		];
	}
	// 지적편집도
	else if(type == "lgstr") {
		urls = [
//			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/130/0/0/{z}/{x}/{y}/empty/ol_lp_cn",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/130/0/0/{z}/{x}/{y}/empty/ol_lp_cn",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/130/0/0/{z}/{x}/{y}/empty/ol_lp_cn",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/130/0/0/{z}/{x}/{y}/empty/ol_lp_cn"

// 2016.10.24 update
//			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/157/0/0/{z}/{x}/{y}/empty/ol_lp_cn",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/157/0/0/{z}/{x}/{y}/empty/ol_lp_cn",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/157/0/0/{z}/{x}/{y}/empty/ol_lp_cn",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/157/0/0/{z}/{x}/{y}/empty/ol_lp_cn"
			
			// 2019.10.26 update
			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/198/0/0/{z}/{x}/{y}/empty/ol_lp_cn",
			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/198/0/0/{z}/{x}/{y}/empty/ol_lp_cn",
			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/198/0/0/{z}/{x}/{y}/empty/ol_lp_cn",
			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/198/0/0/{z}/{x}/{y}/empty/ol_lp_cn"
		];
	}
	// 기본
	else {
		urls = [
//			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/123/0/0/{z}/{x}/{y}/bl_vc_bg/ol_vc_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/123/0/0/{z}/{x}/{y}/bl_vc_bg/ol_vc_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/123/0/0/{z}/{x}/{y}/bl_vc_bg/ol_vc_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/123/0/0/{z}/{x}/{y}/bl_vc_bg/ol_vc_an"

// 2016.10.24 update
//			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/157/0/0/{z}/{x}/{y}/bl_vc_bg/ol_vc_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/157/0/0/{z}/{x}/{y}/bl_vc_bg/ol_vc_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/157/0/0/{z}/{x}/{y}/bl_vc_bg/ol_vc_an",
//			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/157/0/0/{z}/{x}/{y}/bl_vc_bg/ol_vc_an"
			
			// 2019.10.25 update
			CONTEXT_PATH + "/proxy.do?url=http://onetile1.map.naver.net/get/198/0/0/{z}/{x}/{y}/bl_vc_bg/ol_vc_an",
			CONTEXT_PATH + "/proxy.do?url=http://onetile2.map.naver.net/get/198/0/0/{z}/{x}/{y}/bl_vc_bg/ol_vc_an",
			CONTEXT_PATH + "/proxy.do?url=http://onetile3.map.naver.net/get/198/0/0/{z}/{x}/{y}/bl_vc_bg/ol_vc_an",
			CONTEXT_PATH + "/proxy.do?url=http://onetile4.map.naver.net/get/198/0/0/{z}/{x}/{y}/bl_vc_bg/ol_vc_an"
		];
	}
	return urls; 
};
