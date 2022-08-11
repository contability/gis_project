/**
 * 하이라이트 객체
 * @type {Object}
 */
var highlightLoanObj = {
	
	/**
	 * 소스 목록
	 * @type {Object}
	 */
	sources : {
		
		/**
		 * 노란색 소스
		 * @type {ol.source.Vector}
		 */	
		yellow : null,
		
		/**
		 * 주황색 소스
		 * @type {ol.source.Vector}
		 */	
		orange : null,
		
		/**
		 * 빨간색 소스
		 * @type {ol.source.Vector}
		 */	
		red : null
		
	},
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		
		// 벡터 소스 (노란색)
		that.sources.yellow = new ol.source.Vector();
		var yellowLayer = new ol.layer.Vector({
			id : "yellowLayer",
			source : that.sources.yellow,
			style : new ol.style.Style({
				fill : new ol.style.Fill({
					color : 'rgba(255, 255, 0, 0.2)'
				}),
				stroke : new ol.style.Stroke({
					color : 'rgba(255, 255, 0, 0.6)',
					width : 4
				}),
				image : new ol.style.Circle({
					radius : 7,
					fill : new ol.style.Fill({
						color : 'rgba(255, 255, 0, 1)'
					})
				})
			})
		});
		cmmnLoanObj.view.getMap().addLayer(yellowLayer);
		
		// 벡터 소스 (주황색)
		that.sources.orange = new ol.source.Vector();
		var orangeLayer = new ol.layer.Vector({
			id : "orangeLayer",
			source : that.sources.orange,
			style : new ol.style.Style({
				fill : new ol.style.Fill({
					color : 'rgba(255, 94, 0, 0.2)'
				}),
				stroke : new ol.style.Stroke({
					color : 'rgba(255, 94, 0, 0.6)',
					width : 4
				}),
				image : new ol.style.Circle({
					radius : 7,
					fill : new ol.style.Fill({
						color : 'rgba(255, 94, 0, 1)'
					})
				})
			})
		});
		cmmnLoanObj.view.getMap().addLayer(orangeLayer);
		
		// 벡터 소스 (빨간색)
		that.sources.red = new ol.source.Vector();
		var redLayer = new ol.layer.Vector({
			id : "redLayer",
			source : that.sources.red,
			style : new ol.style.Style({
				fill : new ol.style.Fill({
					color : 'rgba(255, 0, 0, 0.2)'
				}),
				stroke : new ol.style.Stroke({
					color : 'rgba(255, 0, 0, 0.6)',
					width : 5
				}),
				image : new ol.style.Circle({
					radius : 7,
					fill : new ol.style.Fill({
						color : 'rgba(255, 0, 0, 1)'
					})
				})
			})
		});
		cmmnLoanObj.view.getMap().addLayer(redLayer);
	},
	
	/**
	 * 노란색 도형 표시
	 * @param {String} className 실행한 클래스 명
	 * @param {Array.<ol.Feature>} features 도형 목록
	 * @param {Boolean} isMove 이동 여부
	 * @param {Object} options 옵션 객체
	 */
	showYellowFeatures : function(className, features, isMove, options) {
		var that = this;
		that.removeYellowFeatures(className);
		that.showFeatures(className, that.sources.yellow, features, isMove, options);
	},
	
	/**
	 * 주황색 도형 표시
	 * @param {String} className 실행한 클래스 명
	 * @param {Array.<ol.Feature>} features 도형 목록
	 * @param {Boolean} isMove 이동 여부
	 * @param {Object} options 옵션 객체
	 */
	showOrangeFeatures : function(className, features, isMove, options) {
		var that = this;
		that.removeOrangeFeatures(className);
		that.showFeatures(className, that.sources.orange, features, isMove, options);
	},
	
	/**
	 * 빨간색 도형 표시
	 * @param {String} className 실행한 클래스 명
	 * @param {Array.<ol.Feature>} features 도형 목록
	 * @param {Boolean} isMove 이동 여부
	 * @param {Object} options 옵션 객체
	 */
	showRedFeatures : function(className, features, isMove, options) {
		var that = this;
		that.removeRedFeatures(className);
		that.showFeatures(className, that.sources.red, features, isMove, options);
	},
	
	/**
	 * 도형 표시
	 * @param {String} className 실행한 클래스 명
	 * @param {ol.source.Vector} source 벡터 소스
	 * @param {Array.<ol.Feature>} features 도형 목록
	 * @param {Boolean} isMove 이동 여부
	 * @param {Object} options 옵션 객체
	 */
	showFeatures : function(className, source, features, isMove, options) {
		var that = this;
		
		// 좌표 변환 여부 확인 후 지도 좌표계 추출
		var mapProjection = null;
		if(options && options.projection) {
			mapProjection = cmmnLoanObj.view.getMap().getView().getProjection();
		}
		
		for(var i=0, len=features.length; i < len; i++) {
			var feature = features[i];
			feature.type = className;
			if(options && options.projection) {
				feature.setGeometry(spatialUtils.transform(feature.getGeometry(), options.projection, mapProjection));
			}
		}

		source.addFeatures(features);
		if(isMove) {
			if(options && options.projection) {
				delete options.projection;
			}
			that.moveFeatures(features, options);
		}
	},
	
	/**
	 * 도형 목록 이동
	 * @param {Array.<ol.Feature>} features 도형 목록
	 * @param {Object} options 옵션 객체
	 */
	moveFeatures : function(features, options) {
		var map = cmmnLoanObj.view.getMap();
		var max = [];
		
		// 좌표 변환 여부 확인 후 지도 좌표계 추출
		var mapProjection = null;
		if(options && options.projection) {
			mapProjection = cmmnLoanObj.view.getMap().getView().getProjection();
		}
		for(var i=0, len=features.length; i < len; i++) {
			var feature = features[i];
			if(options && options.projection) {
				feature.setGeometry(spatialUtils.transform(feature.getGeometry(), options.projection, mapProjection));
			}
			
			var extent = feature.getGeometry().getExtent();
			if(i==0) {
				max[0] = extent[0];
				max[1] = extent[1];
				max[2] = extent[2];
				max[3] = extent[3];
			}
			else {
				if(max[0] > extent[0]) max[0] = extent[0];
				if(max[1] > extent[1]) max[1] = extent[1];
				if(max[2] < extent[2]) max[2] = extent[2];
				if(max[3] < extent[3]) max[3] = extent[3];
			}
		}
		if(max.length == 4) {
			var center = null;
			var resolution = null;
			if(max[0] == max[2] && max[1] == max[3]) {
				resolution = map.convertScaleToResolution(1000);
				center = [max[0], max[1]];
			}
			else {
				var view = map.getView();
				resolution = view.constrainResolution(view.getResolutionForExtent(max, map.getSize()), -2);
				center = ol.extent.getCenter(max);
			}
			
			var offsetWidth = menuObj.getPanelWidth()==0?0:menuObj.getPanelWidth()/2;
			var offsetHeight = 0;
			if(options && options.offsetHeight) {
				offsetHeight = options.offsetHeight;
			}
			else {
				offsetHeight = resultObj.getHeight()==0?0:resultObj.getHeight()/2;
			}
			
			var offsetX = resolution * offsetWidth;
			var offsetY = resolution * offsetHeight;
			map.moveToCenter([center[0]-offsetX, center[1]-offsetY]);
			map.moveToResolution(resolution);
		}
	},
	
	removeYellowFeatures : function(className) {
		var that = this;
		that.removeFeaturesBySourceId(className, "yellow");
	},
	
	removeOrangeFeatures : function(className) {
		var that = this;
		that.removeFeaturesBySourceId(className, "orange");
	},
	
	removeRedFeatures : function(className) {
		var that = this;
		that.removeFeaturesBySourceId(className, "red");
	},
	
	removeFeaturesBySourceId : function(className, sourceId) {
		var that = this;
		var source = that.sources[sourceId];
		var features = source.getFeatures();
		for(var i=features.length-1; i >= 0; i--) {
			var feature = features[i];
			if(className) {
				if(feature.type == className) {
					source.removeFeature(feature);
				}
			}
			else {
				source.removeFeature(feature);
			}
		}
	},
	
	/**
	 * 도형 삭제
	 * @param {String} className 실행한 클래스 명
	 */
	removeFeatures : function(className) {
		var that = this;
		for(var id in that.sources) {
			var source = that.sources[id];
			var features = source.getFeatures();
			for(var i=features.length-1; i >= 0; i--) {
				var feature = features[i];
				if(className) {
					if(feature.type == className) {
						source.removeFeature(feature);
					}
				}
				else {
					source.removeFeature(feature);
				}
				
			}
		}
	}
		
};