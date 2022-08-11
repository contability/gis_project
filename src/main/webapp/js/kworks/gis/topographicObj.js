/**
 * 지형데이터
 * @type {Object}
 */
var topographicObj = {
	
	/**
	 * 타입
	 */
	type : null,	
	
	/**
	 * 그룹
	 */
	groups : [],	

	/**
	 * 데이터
	 */
	datas : {},	

	curLayer : null,
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		return that.loadTopographicList();
	},
	
	/**
	 * 지형데이터 목록 로딩 
	 */
	loadTopographicList : function() {
		var that = this;
		var deferred = $.Deferred();

		var url = CONTEXT_PATH + "/cmmn/topographicMap/listAll.do";
		$.get(url).done(function(response) {
			if(response) {
				if (response.groups) {
					var groups = response.groups;
					for (var i=0; i < groups.length; i++) {
						var group = groups[i];
						that.datas[group] = {
							types : {}
						};
						
						that.groups.push(group); 
					}
				}
				
				if (response.dataTypes) {
					var dataTypes = response.dataTypes;
					for (var j=0; j < dataTypes.length; j++) {
						var dataType = dataTypes[j];
						that.datas[dataType.groupName].types[dataType.dataType] = {
							seqNo : dataType.seqNo,
							title : dataType.title,
							group : dataType.groupName,
							layer : dataType.layerName,
							dataType : dataType.dataType,
							description : dataType.description,
							srs : dataType.srs,
							minValue : dataType.minValue,
							maxValue : dataType.maxValue,
							format : dataType.requestFormat,
							makeYear : dataType.makeYear,
							makeInstitution : dataType.makeInstitution,
							iconPath : dataType.iconPath,
							opacity : 30
						};
					}
				}
			}
			deferred.resolve();
		});
		
		return deferred.promise();
	},

	getGroups : function() {
		var that = this;
		return that.groups;
	},
	
	getType : function(group, type) {
		var that = this;
		return that.datas[group].types[type];
	},	
	
	getTypes : function(group) {
		var that = this;
		return that.datas[group].types;
	},
	
	change : function(group, type) {
		var that = this;
		
		var map = mapObj.getMap();
		var layers = new ol.Collection();
		
		if (group && type) {
			var data = that.getType(group, type);
			
			var layer = new ol.layer.Image({ 
				id : group + type,
				source : new kworks.source.ImageWMS({
					url : CONTEXT_PATH + "/proxy/wms.do",
					params : {
						"LAYERS" : [data.layer],
						"FORMAT" : data.format
					},
					ratio : 1,
					serverType : MAP.SERVER_TYPE
				}),
				opacity : (100 - data.opacity) / 100
			});
			layers.push(layer);
			that.curLayer = layer;

			map.getLayer("overlay_group").setVisible(true);
		}
		else {
			map.getLayer("overlay_group").setVisible(false);
			that.curLayer = null;
		}
			
		map.getLayer("overlay_group").setLayers(layers);
		
		mapObj.reloadWMS();		
	},

	refresh : function(opacity) {
		var that = this;

		if (that.curLayer) {
			that.curLayer.setOpacity((100 - opacity) / 100);
		}
	}
};