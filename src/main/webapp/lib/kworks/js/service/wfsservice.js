goog.provide('kworks.service.WFS');

/**
 * WFS 기본 파라미터 정보
 * 
 * @type {Object.<String, String>}
 */
kworks.service.WFS.defaultParams = {
	url : null,				// 서비스주소
	featurePrefix : null,	// prefix
	srsName : null,			// 좌표계정보
	geometryName : "geom",	// 공간컬럼명
	version : "1.1.0"		// 버전정보
};

/**
 * WFS 기본 파라미터 설정
 * 
 * @param {Object} params 
 */
kworks.service.WFS.setDefaultParams = function(params) {
	$.extend(kworks.service.WFS.defaultParams, params);
};

/**
 * OBJECTID 검색
 * 
 * @param {String} featureType WFS 레이어명
 * @param {Array.<String>} values OBJECTID 목록
 * @param {Function} callback 콜백 함수
 * @Param {Object} params 검색 파라미터
 * @Param {Object} options 검색 옵션
 */
kworks.service.WFS.getFeatureByObjectId = function(featureType, values, callback, params, options) {
	var p = $.extend({}, kworks.service.WFS.defaultParams, params);
	if(goog.isString(values)) {
		values = values.split(",");
	}
	var filter = null;
	if(p.serverType == "arcgis") {
		if(values.length == 1) {
			filter = new OpenLayers.Filter.Comparison({
				type : "==",
				property : "OBJECTID",
				value : values[0]
			});
		}
		else if(values.length > 1) {
			var filters = [];
			for(var i in values) {
				filters.push(new OpenLayers.Filter.Comparison({
					type : "==",
					property : "OBJECTID",
					value : values[i]
				}));
			}
			filter = new OpenLayers.Filter.Logical({
				type : "||",
				filters : filters
			});
		}
		else {
			alert("ID 값이 없습니다.");
		}
	}
	else {
		filter = new OpenLayers.Filter.FeatureId({
			fids : values
		});
	}
	kworks.service.WFS.getFeature(featureType, filter, callback, params, options);
};

/**
 * CQL 검색
 * 
 * @param {String} featureType WFS 레이어명 
 * @Param {String} value CQL 값 (쿼리 문자열)
 * @Param {Function} callback 콜백함수
 * @Param {Object} params 검색 파라미터
 * @Param {Object} options 검색 옵션
 */
kworks.service.WFS.getFeatureByCQL = function(featureType, value, callback, params, options) {
	var format = new OpenLayers.Format.CQL();
	var filter = format.read(value);
	kworks.service.WFS.getFeature(featureType, filter, callback, params, options);
};

/**
 * APIProperty: type
 * {String} type: type of the comparison. This is one of

 */
/**
 * 속성 검색
 * 
 * @param {String} featureType WFS 레이어명 
 * @param {String} type 속성검색 조건 
 * 		- OpenLayers.Filter.Comparison.EQUAL_TO                 = "==";
 * 		- OpenLayers.Filter.Comparison.NOT_EQUAL_TO             = "!=";
 * 		- OpenLayers.Filter.Comparison.LESS_THAN                = "<";
 * 		- OpenLayers.Filter.Comparison.GREATER_THAN             = ">";
 * 		- OpenLayers.Filter.Comparison.LESS_THAN_OR_EQUAL_TO    = "<=";
 * 		- OpenLayers.Filter.Comparison.GREATER_THAN_OR_EQUAL_TO = ">=";
 * 		- OpenLayers.Filter.Comparison.BETWEEN                  = "..";
 * 		- OpenLayers.Filter.Comparison.LIKE                     = "~"; 
 * @param {String} property 검색할 컬럼명
 * @param {String} value 검색할 값
 * @Param {Function} callback 콜백함수
 * @Param {Object} params 검색 파라미터
 * @Param {Object} options 검색 옵션
 */
kworks.service.WFS.getFeatureByComparison = function(featureType, type, property, value, callback, params, options) {
	var filter = null;
	if(value instanceof Array) {
		if(value.length == 1) {
			filter = new OpenLayers.Filter.Comparison({
				type : type,
				property : property,
				value : value[0]
			});
		}
		else if(value.length > 1) {
			var filters = [];
			for(var i in value) {
				for(var j in featureType) {
					filters.push(new OpenLayers.Filter.Comparison({
						type : type,
						property : property,
						value : value[i]
					}));
					filter = new OpenLayers.Filter.Logical({
						type : "||",
						filters : filters
					});
				}
			}
		}
	}
	else {
		filter = new OpenLayers.Filter.Comparison({
			type : type,
			property : property,
			value : value
		});
	}
	
	
	kworks.service.WFS.getFeature(featureType, filter, callback, params, options);
};

/**
 * 영역 검색
 * 
 * @param {String} featureType WFS 레이어명 
 * @param {Array.<Float>} value 영역 (left, bottom, right, top)
 * @Param {Function} callback 콜백함수
 * @Param {Object} params 검색 파라미터
 * @Param {Object} options 검색 옵션
 */
kworks.service.WFS.getFeatureByBBOX = function(featureType, value, callback, params, options) {
	var srsName = kworks.service.WFS.defaultParams["srsName"];
	if(options && options.projection && ol.proj.get(srsName) != options.projection) {
		var transform = ol.proj.getTransform(options.projection, ol.proj.get(srsName));
		value = ol.extent.applyTransform(value, transform);
	}
	
	var filter = new OpenLayers.Filter.Spatial({
        type : "BBOX",
        value : OpenLayers.Bounds.fromArray(value)
    });
	kworks.service.WFS.getFeature(featureType, filter, callback, params, options);
};

/**
 * 공간 검색
 * 
 * @param {String} featureType WFS 레이어명 
 * @param {String} type 공간검색 조건 
 * 		- OpenLayers.Filter.Spatial.INTERSECTS = "INTERSECTS";
 * 		- OpenLayers.Filter.Spatial.DWITHIN = "WITHIN";
 * 		- OpenLayers.Filter.Spatial.CONTAINS = "CONTAINS";
 * @param {ol.geom.Geometry} value 공간정보
 * @Param {Function} callback 콜백함수
 * @Param {Object} params 검색 파라미터
 * @Param {Object} options 검색 옵션 
 */
kworks.service.WFS.getFeatureBySpatial = function(featureType, type, value, callback, params, options) {
	if(goog.isString(featureType)) {
		featureType = featureType.split(",");
	}
	
	if(options && options.projection && ol.proj.get(kworks.service.WFS.defaultParams["srsName"]) != options.projection) {
		var srsName = kworks.service.WFS.defaultParams["srsName"];
		value = value.clone().transform(options.projection, ol.proj.get(srsName));
	}
	
	var geometry = null;
	if(options && options.version && options.version == 2) {
		geometry = value;
	}
	else {
		var ol2format = new OpenLayers.Format.GeoJSON();
		var ol3format = new ol.format.GeoJSON();
		
		var json = null;
		if(value instanceof ol.geom.Circle) {
			var center = value.getCenter();
			var radius = value.getRadius();
			var point = new ol.geom.Point(center);
			if(options && options.buffer) {
				options.buffer = parseFloat(options.buffer) + radius;
			}
			else {
				if(!options) {
					options = {};
				}
				options.buffer = radius;
			}
			json = ol3format.writeGeometryObject(point);
		}
		else {
			json = ol3format.writeGeometryObject(value);
		}
		geometry = ol2format.read(json, "Geometry");
	}

	if(options && options.buffer && options.buffer > 0) {
		var jsts_parser = new jsts.io.OpenLayersParser();
		var jGeom = jsts_parser.read(geometry.clone());				
		var jbGeom = jGeom.buffer(options.buffer);
		geometry = jsts_parser.write(jbGeom);
	}
	
	var filter = new OpenLayers.Filter.Spatial({
        type : type,
        value : geometry
    });
	kworks.service.WFS.getFeature(featureType, filter, callback, params, options);
};

/**
 * 반경 검색
 * 
 * @param {String} featureType WFS 레이어명 
 * @param {Number} distance 반경 
 * @param {ol.geom.Geometry} value 공간정보
 * @Param {Function} callback 콜백함수
 * @Param {Object} params 검색 파라미터
 * @Param {Object} options 검색 옵션 
 */
kworks.service.WFS.getFeatureByDWithin = function(featureType, distance, value, callback, params, options) {
	if(goog.isString(featureType)) {
		featureType = [featureType];
	}
	
	if(options && options.projection) {
		var srsName = kworks.service.WFS.defaultParams["srsName"];
		value = value.clone().transform(options.projection, ol.proj.get(srsName));
	}
	
	var geometry = null;
	if(options && options.version && options.version == 2) {
		geometry = value;
	}
	else {
		var ol2format = new OpenLayers.Format.GeoJSON();
		var ol3format = new ol.format.GeoJSON();
		var json = ol3format.writeGeometryObject(value);
		geometry = ol2format.read(json, "Geometry");
	}

	var filter = new OpenLayers.Filter.Spatial({
        type : "DWITHIN",
        value : geometry,
        distance : distance,
        distanceUnits : "m"
    });
	kworks.service.WFS.getFeature(featureType, filter, callback, params, options);
};

/**
 * 연관 검색
 * 
 * @param {String} featureType WFS 레이어명 
 * @param {Number} distance 반경 
 * @param {ol.geom.Geometry} value 공간정보
 * @Param {Function} callback 콜백함수
 * @Param {Object} params 검색 파라미터
 * @Param {Object} options 검색 옵션 
 */
kworks.service.WFS.getFeatureByRelation = function(featureType, values, relationFeatureType, type, callback, params, options) {
	kworks.service.WFS.getFeatureByObjectId(featureType, values, function(features) {
		var buffer = "0.01";
		if(options && options.buffer && options.buffer > 0) {
			buffer = options.buffer;
		}
		var multiPolygon = null; 
		for(var i in features) {
			var feature = features[i];
			var geometry = feature.getGeometry().clone();
			
			var ol3format = new ol.format.GeoJSON();
			
			var reader = new jsts.io.GeoJSONParser();
			var polygon = reader.read(ol3format.writeGeometryObject(geometry)).buffer(buffer);
			
			if(i==0) {
				multiPolygon = polygon;
			}
			else {
				multiPolygon = multiPolygon.union(polygon);
			}
		}
		var jsts_parser = new jsts.io.OpenLayersParser();
		var ol2format = new OpenLayers.Format.GeoJSON();
		var ol3format = new ol.format.GeoJSON();
		var json = ol2format.write(jsts_parser.write(multiPolygon));
		var geom = ol3format.readGeometry(json);
		kworks.service.WFS.getFeatureBySpatial(relationFeatureType, type, geom, callback, params, options);
	}, params, options);
};

/**
 * 공간검색 호출 및 결과 파싱 (WFS - GetFeature)
 * 
 * @param {String} featureType WFS 레이어명
 * @param {OpenLayers.Filter} filter OpenLayers.Filter 클래스의 하위 클래스의 객체
 * @Param {Function} callback 콜백함수
 * @Param {Object} params 검색 파라미터
 * @Param {Object} options 검색 옵션 
 */
kworks.service.WFS.getFeature = function(featureType, filter, callback, params, options) {
	var p = $.extend({}, kworks.service.WFS.defaultParams, params);
	p.featureType = featureType;
	
	if(!goog.isDef(options)) options = {};
	options.filter = filter;
	
	var format = new kworks.format.WFS();
	var data = format.writeGetFeature(p, options);
	
	$.ajax({
    	url : p.url,
    	type : "POST",
    	dataType : "text",
    	data : data,
    	contentType : "text/xml",
    	success : function(response) {
    		var format = new ol.format.GML3();
    		var features = [];
    		if(p.serverType == "arcgis" || p.serverType == "kmaps") {
        		var prefixOgc = "";
        		if(browserUtils.checkIe()) {
        			prefixOgc = "gml:";
    			}
        		var featureMembers = $.parseXML(response).getElementsByTagName(prefixOgc+"featureMember");
        		for(var i=0, len=featureMembers.length; i < len; i++) {
        			var feature = format.readFeatures(featureMembers[i]);
        			if(p.serverType == "arcgis") {
        				feature.tableName = featureMembers[i].firstChild.localName;
        				feature.fid = feature.getProperties()["OBJECTID"];
        			}
        			features.push(feature);
        		}
    		}
    		else {
    			features = format.readFeatures(response);
    		}
    		
    		if(p.serverType != "arcgis") {
    			for(var i in features) {
    				var feature = features[i];
    				var split = feature.getId().split(".");
    				feature.tableName = split[0];
    				feature.fid = split[1];
    			}
    		}
    		
    		if(goog.isDef(callback)) callback(features);
    	},
    	error : function(response) {
    		var format = new ol.format.GML3();
    		if(goog.isDef(callback)) callback(format.readFeatures(response));
    	}
    });
};


/**
 * 객체 등록/수정/삭제 (WFS - Transaction)
 * 
 * @param {String} featureType WFS 레이어명
 * @param {String} srsName 좌표계
 * @param {Array.<ol.Feature>} insertFeatures 등록할 객체 목록
 * @param {Array.<ol.Feature>} updateFeatures 수정할 객체 목록
 * @param {Array.<ol.Feature>} deleteFeatures 삭제할 객체 목록
 * @Param {Function} callback 콜백함수
 * 수정자 : 이승재, 2019.12.10
 */
kworks.service.WFS.transaction = function(url, params, insertFeatures, updateFeatures, deleteFeatures, callback) {
	if(params.geometryName) {
		if(params.geometryName != "geometry") {
			for(var i in insertFeatures) {
				var feature = insertFeatures[i];
				if(feature.getGeometryName() == "geometry") {
					var geometry = feature.getGeometry();
					feature.setGeometryName(params.geometryName);
					feature.setGeometry(geometry);
					feature.unset("geometry");
				}
			}
			for(var i in updateFeatures) {
				var feature = updateFeatures[i];
				if(feature.getGeometryName() == "geometry") {
					var geometry = feature.getGeometry();
					feature.setGeometryName(params.geometryName);
					feature.setGeometry(geometry);
					feature.unset("geometry");
				}
			}
		}
	}

	if(params.serverType == "arcgis") {
		for(var i in insertFeatures) {
			var feature = insertFeatures[i];
			var properties = feature.getProperties();
			if(properties["OBJECTID"]) {
				feature.unset("OBJECTID");
			}
			if(properties["SHAPE.LEN"]) {
				feature.unset("SHAPE.LEN");
			}
			if(properties["SHAPE.AREA"]) {
				feature.unset("SHAPE.AREA");
			}
		}
		for(var i in updateFeatures) {
			var feature = updateFeatures[i];
			var properties = feature.getProperties();
			if(properties["OBJECTID"]) {
				feature.unset("OBJECTID");
			}
			if(properties["SHAPE.LEN"]) {
				feature.unset("SHAPE.LEN");
			}
			if(properties["SHAPE.AREA"]) {
				feature.unset("SHAPE.AREA");
			}
			
			kworks.service.WFS.getArcgisFid(params.featureType, feature);
		}
		for(var i in deleteFeatures) {
			var feature = deleteFeatures[i];
			kworks.service.WFS.getArcgisFid(params.featureType, feature);
		}
	}

	var format = new kworks.format.WFS();
	var node = format.writeTransaction(insertFeatures, updateFeatures, deleteFeatures, params);
	var data = new XMLSerializer().serializeToString(node);
	if (browserUtils.checkIe()) {
		data = data.replace(/xmlns:NS\d+="" NS\d+:/gi, "");
	}
	
	$.ajax({
		url : url,
		type : "POST",
		dataType : "text",
		data : data,
		contentType : "text/xml",
    	success : function(response) {
    		// 여기 response 확인해볼것
    		
    		
    		/*var prefixWfs = "";
    		if(browserUtils.checkIe()) {
    			prefixWfs = "wfs\\:";
    		}
    		var xml = $.parseXML(response);
    		var inserted = $(xml).find(prefixWfs+"totalInserted").text();
    		var updated = $(xml).find(prefixWfs+"totalUpdated").text();
    		var deleted = $(xml).find(prefixWfs+"totalDeleted").text();*/
    		
    		// 초기화
    		var inserted = 0;
    		var updated = 0;
    		var deleted = 0;
    		
    		var insertElements = $.parseXML(response).getElementsByTagNameNS("http://www.opengis.net/wfs","totalInserted");
    		if(insertElements && insertElements.length > 0) {
    			inserted = parseInt($(insertElements.item(0)).text());
    		}
    		
    		var updateElements = $.parseXML(response).getElementsByTagNameNS("http://www.opengis.net/wfs","totalUpdated");
    		if(updateElements && updateElements.length > 0) {
    			updated = parseInt($(updateElements.item(0)).text());
    		}
    		
    		var deleteElements = $.parseXML(response).getElementsByTagNameNS("http://www.opengis.net/wfs","totalDeleted");
    		if(deleteElements && deleteElements.length > 0){
    			deleted = parseInt($(deleteElements.item(0)).text());
    		}
    		
    		if(goog.isDef(callback)) callback({
    			inserted : inserted,
    			updated : updated,
    			deleted : deleted
    		});
    	}
	});
	
};


kworks.service.WFS.getArcgisFid = function(layerName, feature) {
	//var ftrIdn = feature.getProperties()["FTR_IDN"];
	var ftrIdn = 0;
	if(feature.ftrIdn != null) {
		ftrIdn = feature.ftrIdn;
	}
	else if(feature.getProperties().FTR_IDN != null) {
		ftrIdn = feature.getProperties().FTR_IDN;
	}
	
	var data = '<GetFeature xmlns="http://www.opengis.net/wfs" service="WFS" version="1.1.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd"><Query typeName="' + layerName + '"><Filter xmlns="http://www.opengis.net/ogc"><PropertyIsEqualTo><PropertyName>FTR_IDN</PropertyName><Literal>' + ftrIdn + '</Literal></PropertyIsEqualTo></Filter></Query></GetFeature>';
	
	$.ajax({
    	url : CONTEXT_PATH + "/proxy/wfs.do",
    	type : "POST",
    	dataType : "text",
    	data : data,
    	async : false,
    	contentType : "text/xml",
    	success : function(response) {
			var format = new ol.format.GML3();
			var features = format.readFeatures($.parseXML(response));
			if(features && features.length && features.length > 0) {
				feature.setId(features[0].getId());
			}
    	}
	});
};