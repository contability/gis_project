/**
 * 배경지도
 * @type {Object}
 */
var backgroundMapObj = {
	
	/**
	 * 서비스 명 (tms, daum, naver, vworld)
	 * @type {String}
	 */
	service : null,
	
	/**
	 * 타입 명 (base, satellite, bybrid...)
	 * @type {String}
	 */
	type : null,
	
	/**
	 * 데이터
	 */
	data : {
		
		/**
		 * TMS - 지자체 보유 항공영상
		 */
		tms : {
			getView : TMS.getView,
			types : {}
		},
		
		/**
		 * 네이버 지도
		 */
		naver : {
			getView : function() {
				return new kworks.view.Naver();
			},
			types : {
			}
		},
		
		/**
		 * 다음 지도
		 */
		daum : {
			getView : function() {
				return new kworks.view.Daum();	
			},
			types : {
			}
		},
		
		/**
		 * 브이월드 지도
		 */
		vworld : {
			getView : function() {
				return new kworks.view.VWorld();
			},
			types : {
			}
		}
	},
	
	/**
	* 레이어 목록
	*/
	layers : {},
	
	/**
	 * 초기화
	 */
	init : function(isSetBaseMap) {
		var that = this;
		return that.loadBaseMap(isSetBaseMap);
	},
	
	loadBaseMap : function(isSetBaseMap) {
		var that = this;
		var deferred = $.Deferred();

		var url = CONTEXT_PATH + "/cmmn/baseMap/listAll.do";
		$.get(url).done(function(response) {
			var baseMap = null;
			if(response && response.rows) {
				var rows = response.rows;
				for(var i=0, len=rows.length; i < len; i++) {
					var row = rows[i];
					
					var urls = [];
					var hosts = row.kwsBaseMapHosts;
					for(var j=0, jLen=hosts.length; j < jLen; j++) {
						host = hosts[j];
						var url = CONTEXT_PATH + "/proxy.do?url=http://";
						url += host.url;
						url += ":" + host.port;
						url += "/" + row.requestFormat.replace("\{e\}", row.extension);
					
						urls.push(url);
					}
					
					var resolutions = [];
					var levels = row.kwsBaseMapLevels;
					for(var j=0, jLen=levels.length; j < jLen; j++) {
						var level = levels[j];
						resolutions.push(level.resolution);
					}
					
					var options = {
						projection : row.srs,
						urls : urls,
						origin : [row.originX, row.originY],
						resolutions : resolutions
					};
					if(row.minX && row.minY && row.maxX && row.maxY) {
						options.extent = [row.minX, row.minY, row.maxX, row.maxY];
					}
					else {
						options.extent = null;
					}
					
					var id = row.title.toLowerCase();
					var split = id.split("_");
					var service = split[0];
					var type = split[1];
					
					var source = null;
					if(service == "tms") {
						source = new kworks.source.Tms(options);
					}
					else if(service == "naver") {
						source = new kworks.source.Naver(options);
					}
					else if(service == "daum") {
						source = new kworks.source.Daum(options);
					}
					else if(service == "vworld") {
						source = new kworks.source.VWorld(options);
					}

					if(service == "tms") {
						if(row.baseMapYn.toUpperCase() == "Y") {
							that.data[service].types[type] = {
								mapNo : row.mapNo,
								description : row.description,
								photographyYear : row.photographyYear,
								comment : row.cm,
								layers : [new ol.layer.Tile({ id:id, source : source, name : "bgLayer" })]
							};
						}
						if(row.layerYn.toUpperCase() == "Y") {
							that.layers[type] = {
								mapNo : row.mapNo,
								description : row.description,
								comment : row.cm,
								layer : new ol.layer.Tile({ id : "layer_base_map_" + row.mapNo, source : new kworks.source.Tms(options) })
							};
						}
					}
					else {
						that.data[service].types[type] = {
							mapNo : row.mapNo,
							description : row.description,
							comment : row.cm,
							layers : [new ol.layer.Tile({ id:id, source : source, name : "bgLayer" })]
						};	
					}
				
					if(row.baseYn == "Y") {
						baseMap = row.title;
						
						// 이승재, 2020.05.18,  배경지도 캡션기능 범용화
						var comment = row.cm;
						comment = stringUtils.unescapeHtml(comment);
						if (comment != '') {
							$("#div_description").html(comment);
						}
					}
					
				}
			}
			
			// 이승재, 2021.01.18, for..in문 수정 후 외부배경지도에서 하이브리도 지도와 위성영상 지도가 중첩되지않는 현상 처리 
			var services = Object.keys(that.data);
			for(var i = 0; i < services.length; i++) {
				var service = services[i];
				var types = that.data[service].types;
				if(types["satellite"] && types["hybrid"]) {
					types["hybrid"].layers.unshift(types["satellite"].layers[0]);
				}
			}
			
			if(isSetBaseMap && baseMap) {
				var id = baseMap.toLowerCase();
				var split = id.split("_");
				var service = split[0];
				var type = split[1];
				that.change(service, type);
			}
			
			if(!that.service) {
				that.service = "tms";
			}
			
			deferred.resolve();
		});
		
		return deferred.promise();
	},
	
	getData : function() {
		var that = this;
		return that.data;
	},
	
	/**
	* 레이어 목록 반환
	*/
	getLayers : function() {
		var that = this;
		return that.layers;
	},
	
	/**
	 * 뷰 반환
	 * @param {String} service 서비스
	 * @returns {Object} 뷰
	 */
	getView : function(service) {
		var that = this;
		return that.data[service].getView();
	},
	
	getService : function() {
		var that = this;
		return that.service;
	},
	
	getType : function() {
		var that = this;
		return that.type;
	},
	
	setService : function(service) {
		var that = this;
		that.service = service;
	},
	
	setType : function(type) {
		var that = this;
		that.type = type;
	},
	
	/**
	 * 레이어 반환
	 * @param {String} service 서비스
	 * @param {String} type 타입
	 * @returns {Object} 레이어
	 */
	getLayer : function(service, type) {
		var that = this;
		return that.data[service].types[type].layers;
	},
	
	removeLayers : function() {
		var that = this;
		
		that.service = null;
		that.type = null;
		
		var map = mapObj.getMap();
		var layers = map.getLayers();
		for(var i=layers.get("length")-1; i >= 0; i--) {
			var layer = layers.item(i);
			if(layer.get("name") == "bgLayer") {
				layers.remove(layer);
			}
		}
	},
	
	change : function(service, type, map) {
		var that = this;
		if(!map) {
			map = mapObj.getMap();
			that.service = service;
			that.type = type;
		}
		
		if(service == "tms") {
			themamapObj.basemapObj.show();
		}
		else {
			themamapObj.basemapObj.hide();	
		}
		
		that.changeTmsLayer(map, service, type);
		that.changeView(map, service);
	},
	
	changeTmsLayer : function(map, service, type) {
		var that = this;
		if(!map) {
			map = mapObj.getMap();
		}
		if(!service) {
			service = that.service;
		}
		if(!type) {
			type = that.type;
		}
		
		var newLayers = new ol.Collection();
		
		if(type) {
			var bgLayers = that.getLayer(service, type);
//			for(let i in bgLayers) {
//				newLayers.push(bgLayers[i]);
//			}
			for (var i = 0; i < bgLayers.length; i++) {
				var bglayer = bgLayers[i]; 
				newLayers.push(bglayer);
			}
		}
		var layers = map.getLayers();
		for(var i=0, len=layers.get("length"); i < len; i++) {
			var layer = layers.item(i);
			if(layer.get("name") != "bgLayer") {
				newLayers.push(layer);
			}
		}
		map.getLayerGroup().setLayers(newLayers);
	},
	
	changeView : function(map, service) {
		var that = this;
		if(!map) {
			map = mapObj.getMap();
		}
		if(!service) {
			service = that.service;
		}

		// 이전 지도 위치
		var center = map.getCenter();
		var scale = map.getScale();
		var from = map.getView().getProjection();
		
		// 뷰 설정
		var view = that.getView(service);
		var to = view.getProjection();
		map.setView(view);
		
		// 이전, 다음 영역 좌표 변환
		var history = map.historyStack;
		for(var i=0, len=history.get("length"); i < len; i++) {
			var item = history.item(i);
			var geom = new ol.geom.Point(item.center).transform(from, to);
			item.center = geom.getCoordinates();
		}

		// 벡터 레이어 좌표 변환
		var layers = map.getLayers();
		for(var i=0, len=layers.get("length"); i < len; i++) {
			var layer = layers.item(i);
			if(layer instanceof ol.layer.Vector) {
				var features = layer.getSource().getFeatures();
//				for(var j in features) {
//					features[j].getGeometry().transform(from, to);
//				}
				for (var j = 0; j < features.length; j++) {
					features[j].getGeometry().transform(from, to);
				}
			}
		}
		
		// 이전 지도 위치로 이동
		map.moveToCenter(spatialUtils.transformCooridate(center, from, to));
		map.moveToScale(scale);
	}
	
};