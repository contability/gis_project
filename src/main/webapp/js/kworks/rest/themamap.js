/**
 * 주제도 객체
 * @type {Object}
 */
var themamapObj = {
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "주제도",
	
	/**
	 * 
	 */
	data : null,
	
	/**
	 * 주제도 불러오기
	 * @returns 주제도 목록
	 */
	load : function() {
		var that = this;
		var deferred = $.Deferred();
		
		var url = CONTEXT_PATH + "/rest/themamap/listAll.do?nocache="+Math.random();
		$.get(url).done(function(response) {
			if (response && response.rows) {
				that.data = response.rows;
				deferred.resolve();
			} else {
				$.messager.alert(that.TITLE, "주제도 목록를 불러오는 데 실패했습니다.");
			}
		});
		return deferred.promise();
	},
	
	/**
	 * 주제도 목록 반환
	 * @param {String} 주제도 타입
	 * @return {Array.<Object>} 주제도 목록
	 */
	getData : function(type) {
		var that = this;
		var result = [];
		for(var i=0, len=that.data.length; i < len; i++) {
			if(that.data[i].themamapTy == type) {
				result.push(that.data[i]);
			}
		}
		return result;
	},
	
	/**
	 * 데이터 로딩
	 * @returns {Promise} Promise 객체
	 */
	selectOne : function(themamapId) {
		var that = this;
		var url = CONTEXT_PATH + "/rest/themamap/" + themamapId + "/select.do";
		var deferred = $.Deferred();
		$.get(url).done(function(response) {
			if (response) {
				if(response.themamap) {
					that.basemapObj.setData(response.themamap.kwsThemamapBaseMaps);
					deferred.resolve(response.themamap, response.jsonStr);
				}
				else {
					deferred.resolve(response.themamap, response.jsonStr);
				}
			} else {
				$.messager.alert(that.TITLE, "주제도 정보를 불러오는 데 실패했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "주제도 정보를 불러오는 데 실패했습니다.");
		});
		return deferred.promise();
	},
	
	add : function(params) {
		var url = CONTEXT_PATH + "/rest/themamap/add.do";
		params["jsonStr"] = JSON.stringify(sldObj.getData());
		params["baseMapStr"] = JSON.stringify({ baseMaps : themamapObj.basemapObj.getData()});
		return $.post(url, params);
	},
	
	remove : function(themamapId) {
		var url = CONTEXT_PATH + "/rest/themamap/" + themamapId + "/remove.do";
		return $.post(url);
	}
};

/**
 * 주제도 기본지도 객체
 * @type {Object}
 */
themamapObj.basemapObj = {

	/**
	 * 기본지도 목록
	 * @type {Array<Object>}
	 */
	data : [],
	
	/**
	 * 기본지도 목록 설정
	 * @param {Array<Object>} kwsThemamapBaseMaps 주제도 목록
	 */
	setData : function(kwsThemamapBaseMaps) {
		var that = this;
		var data = [];
		for(var i=0, len=kwsThemamapBaseMaps.length; i < len; i++) {
			var kwsThemamapBaseMap = kwsThemamapBaseMaps[i];
			var visible = kwsThemamapBaseMap.visibleAt=="Y"?true:false;
			var obj = {
				mapNo : kwsThemamapBaseMap.mapNo,
				opacity : kwsThemamapBaseMap.opacity,
				visible : visible
			};
			data.push(obj);
		}
		that.data = data;
	},
	
	/**
	 * 기본 지도 목록 반환
	 * @returns {Array}
	 */
	getData : function() {
		var that = this;
		return that.data;
	},
	
	/**
	 * 지도 아이디로 기본지도 반환
	 * @param mapNo
	 * @returns
	 */
	getBaseMapByMapNo : function(mapNo) {
		var that = this;
		var data = that.data;
		for(var i=0, len=data.length; i < len; i++) {
			var obj = data[i];
			if(obj.mapNo == mapNo) {
				return obj;
			}
		}
		return null;
	},
	
	/**
	 * 지도 아이디 목록 반환
	 * @returns {Array}
	 */
	getBaseMapNos : function() {
		var that = this;
		var data = that.data;
		var baseMapNos = [];
		for(var i=0, len=data.length; i < len; i++) {
			var obj = data[i];
			baseMapNos.push(obj.mapNo);
		}
		return baseMapNos;
	},
	
	/**
	 * 지도 아이디에 해당하는 지도 타입 반환
	 * @param mapNo
	 * @returns
	 */
	getTypeByMapNo : function(mapNo) {
		var types = backgroundMapObj.getLayers();
		for(var type in types) {
			if(mapNo == types[type].mapNo) {
				return types[type].description;
			}
		}
		return null;
	},
	
	/**
	 * 타입명 반환
	 * @param type
	 * @returns {String}
	 */
	getTypeName : function(type) {
		return type;
	},
	
	/**
	 * 기본지도 목록 추가
	 * @param data
	 */
	add : function(data) {
		var that = this;
		that.data = data.concat(that.data);
	},
	
	/**
	 * 지도 아이디로 삭제
	 * @param mapNo
	 */
	removeByMapNo : function(mapNo) {
		var that = this;
		var data = that.data;
		for(var i=data.length-1; i >= 0; i--) {
			var obj = data[i];
			if(obj.mapNo == mapNo) {
				data.splice(i, 1);
				break;
			}
		}
	},
	
	/**
	 * 전체 표시 여부 설정
	 * @param visible
	 */
	setVisibleAll : function(visible) {
		var that = this;
		var data = that.data;
		for(var i=0, len=data.length; i < len; i++) {
			var obj = data[i];
			obj.visible = visible;
		}
	},
	
	/**
	 * 표시 여부 설정
	 * @param mapNo
	 * @param visible
	 */
	setVisible : function(mapNo, visible) {
		var that = this;
		var obj = that.getBaseMapByMapNo(mapNo);
		if(obj) {
			obj.visible = visible;
		}
	},
	
	/**
	 * 투명도 설정
	 * @param mapNo
	 * @param opacity
	 */
	setOpacity : function(mapNo, opacity) {
		var that = this;
		var obj = that.getBaseMapByMapNo(mapNo);
		if(obj) {
			obj.opacity = opacity;
		}
	},
	
	/**
	 * 레이어 순서 변경
	 * @param sourceMapNo
	 * @param targetMapNo
	 * @param point
	 */
	changeLayerOrder : function(sourceMapNo, targetMapNo, point) {
		var that = this;
		
		var data = that.data;
		
		var temp = null;
		
		var sourceIdx = null;
		var targetIdx = null;
		// 객체 임시 보관 후 배열에서 삭제
		for(var i=0, len=data.length; i < len; i++) {
			var obj = data[i];
			if(obj.mapNo == sourceMapNo) {
				sourceIdx = i;
				temp = obj;
				break;
			}
		}
		data.splice(sourceIdx, 1);
		
		// 타겟 위치에 임시 보관한 객체 추가
		for(var i=0, len=data.length; i < len; i++) {
			var obj = data[i];
			if(obj.mapNo == targetMapNo) {
				targetIdx = i;
				break;
			}
		}
		if(point == "top") {
			targetIdx++;
		}
		
		data.splice(targetIdx, 0, temp);
	},
	
	/**
	 * 기본지도 표시
	 */
	show : function(map) {
		var that = this;
		
		if(!map) {
			map = mapObj.getMap();
		}
		
		var layers = new ol.Collection();
		var data = that.data;
		var baseMapLayers = backgroundMapObj.getLayers();
		for(var i=0, len=data.length; i < len; i++) {
			var obj = data[i];
			for(var type in baseMapLayers) {
				var baseMapLayer = baseMapLayers[type];
				if(obj.mapNo == baseMapLayer.mapNo) {
					baseMapLayer.layer.setOpacity(obj.opacity);
					baseMapLayer.layer.setVisible(obj.visible);
					layers.push(baseMapLayer.layer);
					break;
				}
			}
		}
		
		map.getLayer("base_map_group").setVisible(true);
		map.getLayer("base_map_group").setLayers(layers);
	},
	
	/**
	 * 기본지도 숨김
	 */
	hide : function(map) {
		if(!map) {
			map = mapObj.getMap();
		}
		map.getLayer("base_map_group").setVisible(false);
	}
};