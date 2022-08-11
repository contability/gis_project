var userEnvironmentObj = {
	
	TITLE : "사용자 환경",
	
	data : {
		mapToolLc : "RT",
		indexWindowLc : "RB",
		indexWindowSize : 300,
		scBarLc : "LB",
		centerPointTy : "TM",
		centerPointLc : "LB",
		zoomLensSize : 300,
		xrayLensSize : 300,
		themamapId : null,
		initCenterX : 0,
		initCenterY : 0,
		initSc : 0
	},
		
	load : function() {
		var that = this;
		var deferred = $.Deferred();
		
		var url = CONTEXT_PATH + "/rest/userEnvrn/select.do";
		$.get(url).done(function(response) {
			if (response && response.data) {
				if(response.data.mapToolLc) {
					that.data.mapToolLc = response.data.mapToolLc;
				}
				if(response.data.indexWindowLc) {
					that.data.indexWindowLc = response.data.indexWindowLc;
				}
				if(response.data.indexWindowSize) {
					that.data.indexWindowSize = response.data.indexWindowSize;
				}
				if(response.data.scBarLc) {
					that.data.scBarLc = response.data.scBarLc;
				}
				if(response.data.centerPointTy) {
					that.data.centerPointTy = response.data.centerPointTy;
				}
				if(response.data.centerPointLc) {
					that.data.centerPointLc = response.data.centerPointLc;
				}
				if(response.data.zoomLensSize) {
					that.data.zoomLensSize = response.data.zoomLensSize;
				}
				if(response.data.xrayLensSize) {
					that.data.xrayLensSize = response.data.xrayLensSize;
				}
				if(response.data.themamapId) {
					that.data.themamapId = response.data.themamapId;
				}
				if(response.data.initCenterX) {
					that.data.initCenterX = response.data.initCenterX;
				}
				if(response.data.initCenterY) {
					that.data.initCenterY = response.data.initCenterY;
				}
				if(response.data.initSc) {
					that.data.initSc = response.data.initSc;
				}
			}
			deferred.resolve();
		});
		return deferred.promise();
	},
	
	setData : function(property, value) {
		var that = this;
		that.data[property] = value;
	},
	
	getData : function(property) {
		var that = this;
		return that.data[property];
	},
	
	save : function(params, callback) {
		var that = this;
		
		var from = mapObj.getMap().getView().getProjection();
		var to = ol.proj.get(MAP.PROJECTION);
		var center = spatialUtils.transformCooridate(mapObj.getMap().getCenter(), from, to);
		
		var url = CONTEXT_PATH + "/rest/userEnvrn/persist.do";
		return $.post(url, $.extend(that.data, params, {
			initCenterX : center[0],
			initCenterY : center[1],
			initSc : parseInt(mapObj.getMap().getScale())
		}));
	},
	
	moveUser : function() {
		var that = this;
		var center = null;
		var to = mapObj.getMap().getView().getProjection();
		if($.cookie("initCenterX_"+PROJECT_CODE) && $.cookie("initCenterY_"+PROJECT_CODE)) {
			var from = ol.proj.get("EPSG:4326");
			center = spatialUtils.transformCooridate([parseFloat($.cookie("initCenterX_"+PROJECT_CODE)), parseFloat($.cookie("initCenterY_"+PROJECT_CODE))], from, to);
		}
		else if(that.getData("initCenterX") && that.getData("initCenterY")) {
			var from = ol.proj.get(MAP.PROJECTION);
			center = spatialUtils.transformCooridate([that.getData("initCenterX"), that.getData("initCenterY")], from, to);
		}
		var initSc = $.cookie("initSc_"+PROJECT_CODE)?$.cookie("initSc_"+PROJECT_CODE):that.getData("initSc");
		if(center && initSc) {
			mapObj.getMap().moveToCenter(center);
			mapObj.getMap().moveToScale(initSc);
		}
	}
		
};