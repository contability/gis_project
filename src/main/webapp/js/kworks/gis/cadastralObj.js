/**
 * 지적원도 데이터
 * @type {Object}
 */
var cadastralObj = {
		
	/**
	 * 타일맵
	 */
	tiles : {},	
	
	/**
	 * 영역 소스
	 */
	vectorSource : null,

	/**
	 * 영역 레이어
	 */
	vectorLayer : null,
	
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		
		that.vectorSource = new ol.source.Vector();
		that.vectorLayer = new ol.layer.Vector({
			id : "cadastralLayer",
			source : that.vectorSource,
			style : new ol.style.Style({
				fill : new ol.style.Fill({
					color : 'rgba(255, 94, 0, 0.0)'
				}),
				stroke : new ol.style.Stroke({
					color : 'rgba(255, 94, 0, 0.0)',
					width : 4
				})
			})
		});
		
		return that.load();
	},

	load : function() {
		var that = this;
		that.vectorSource.clear();

		var url = CONTEXT_PATH + "/cmmn/cadastralMap/listAll.do";
		$.ajax({
			type : "GET",
			url : url,
			async : false,
			success : function(response) {
				var tiles = response.tiles;
				that.tiles = tiles;
				
				var features = [];
				for(var i=0, len=tiles.length; i < len; i++) {
					var tile = tiles[i];
					
					// 영역 벡터데이터
					var extent = [];
					extent.push(tile.minX);
					extent.push(tile.minY);
					extent.push(tile.maxX);
					extent.push(tile.maxY);
					tile.extent = extent;
					
					var coords = [];
					coords.push(gisObj.toTransformMap(new ol.geom.Point([tile.minX, tile.minY])).getCoordinates());
					coords.push(gisObj.toTransformMap(new ol.geom.Point([tile.maxX, tile.minY])).getCoordinates());
					coords.push(gisObj.toTransformMap(new ol.geom.Point([tile.maxX, tile.maxY])).getCoordinates());
					coords.push(gisObj.toTransformMap(new ol.geom.Point([tile.minX, tile.maxY])).getCoordinates());
					coords.push(gisObj.toTransformMap(new ol.geom.Point([tile.minX, tile.minY])).getCoordinates());
					var feature = new ol.Feature({
						geometry : new ol.geom.Polygon([coords])
					});
					feature.setId(tile.tileNo);
					features.push(feature);
					
					// 타일맵 데이터
					var urls = [];
					var url = CONTEXT_PATH + "/proxy.do?url=http://";
					url += tile.host;
					url += ":" + tile.port;
					url += "/" + tile.requestFormat.replace("\{e\}", tile.extension);
					urls.push(url);
					
					var resolutions = [];
					var levels = tile.kwsOverlayTileLevels;
					for(var j=0, jLen=levels.length; j < jLen; j++) {
						var level = levels[j];
						resolutions.push(level.resolution);
					}
					
					var options = {
						projection : tile.srs,
						urls : urls,
						origin : [tile.originX, tile.originY],
						resolutions : resolutions,
						extent : [tile.minX, tile.minY, tile.maxX, tile.maxY]
					};
					
					tile.visible = true;
					tile.opacity = parseInt(CADASTRALMAP_OPACITY);
					var source = new kworks.source.Tms(options);
					tile.layer = new ol.layer.Tile({
						id : tile.tileNo, 
						source : source, 
						name : tile.title,
						visible : true,
						opacity : (100 - tile.opacity) / 100
					});
				}
				
				if(features.length > 0) {
					that.vectorSource.addFeatures(features);
				}
			},
			fail : function() {
				$.messager.alert("지적원도", "지적원도 정보를 가져오는데 실패했습니다.");
			}
		});
			
//		$.get(url).done(function(response) {
//			var tiles = response.tiles;
//			that.tiles = tiles;
//			
//			var features = [];
//			for(var i=0, len=tiles.length; i < len; i++) {
//				var tile = tiles[i];
//				
//				// 영역 벡터데이터
//				var extent = [];
//				extent.push(tile.minX);
//				extent.push(tile.minY);
//				extent.push(tile.maxX);
//				extent.push(tile.maxY);
//				tile.extent = extent;
//				
//				var coords = [];
//				coords.push(gisObj.toTransformMap(new ol.geom.Point([tile.minX, tile.minY])).getCoordinates());
//				coords.push(gisObj.toTransformMap(new ol.geom.Point([tile.maxX, tile.minY])).getCoordinates());
//				coords.push(gisObj.toTransformMap(new ol.geom.Point([tile.maxX, tile.maxY])).getCoordinates());
//				coords.push(gisObj.toTransformMap(new ol.geom.Point([tile.minX, tile.maxY])).getCoordinates());
//				coords.push(gisObj.toTransformMap(new ol.geom.Point([tile.minX, tile.minY])).getCoordinates());
//				var feature = new ol.Feature({
//					geometry : new ol.geom.Polygon([coords])
//				});
//				feature.setId(tile.tileNo);
//				features.push(feature);
//				
//				// 타일맵 데이터
//				var urls = [];
//				var url = CONTEXT_PATH + "/proxy.do?url=http://";
//				url += tile.host;
//				url += ":" + tile.port;
//				url += "/" + tile.requestFormat.replace("\{e\}", tile.extension);
//				urls.push(url);
//				
//				var resolutions = [];
//				var levels = tile.kwsOverlayTileLevels;
//				for(var j=0, jLen=levels.length; j < jLen; j++) {
//					var level = levels[j];
//					resolutions.push(level.resolution);
//				}
//				
//				var options = {
//					projection : tile.srs,
//					urls : urls,
//					origin : [tile.originX, tile.originY],
//					resolutions : resolutions,
//					extent : [tile.minX, tile.minY, tile.maxX, tile.maxY]
//				};
//				
//				tile.visible = true;
//				tile.opacity = parseInt(CADASTRALMAP_OPACITY);
//				var source = new kworks.source.Tms(options);
//				tile.layer = new ol.layer.Tile({
//					id : tile.tileNo, 
//					source : source, 
//					name : tile.title,
//					visible : true,
//					opacity : (100 - tile.opacity) / 100
//				});
//			}
//			
//			if(features.length > 0) {
//				that.vectorSource.addFeatures(features);
//			}
//		}).fail(function() {
//			$.messager.alert("지적원도", "지적원도 정보를 가져오는데 실패했습니다.");
//		});
	},
	
	/**
	 * 타일맵 데이터
	 */
	getTiles : function() {
		var that = this;
		return that.tiles;
	},
	
	/**
	 * 영역 벡터
	 */
	getVectorSource : function() {
		var that = this;
		return that.vectorSource;
	},
	
	/**영역 레이어
	 */
	getVectorLayer : function() {
		var that = this;
		return that.vectorLayer;
	},
	
	/**
	 * 타일맵
	 */
	getTile : function(tileNo) {
		var that = this;
		return that.tiles.filter(function(element) {
			return element.tileNo == tileNo;
		});
	}
};