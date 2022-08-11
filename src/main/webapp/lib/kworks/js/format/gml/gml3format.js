goog.provide('kworks.format.GML3');

goog.require('ol.format.GML3');

kworks.format.GML3 = function(opt_options) {
	var options = (opt_options ? opt_options : {});
	goog.base(this, options);
};
goog.inherits(kworks.format.GML3, ol.format.GML3);

kworks.format.GML3.prototype.writePoint_ = function(node, geometry, objectStack) {
	var context = objectStack[objectStack.length - 1];
	goog.asserts.assert(goog.isObject(context), 'context should be an Object');
	var srsName = context['srsName'];
	if (srsName) {
		node.setAttribute('srsName', srsName);
	}
	var pos = ol.xml.createElementNS(node.namespaceURI, 'gml:pos');
	node.appendChild(pos);
	this.writePos_(pos, geometry, objectStack);
};

kworks.format.GML3.prototype.writeLinearRing_ = function(node, geometry, objectStack) {
	var context = objectStack[objectStack.length - 1];
	goog.asserts.assert(goog.isObject(context), 'context should be an Object');
	var srsName = context['srsName'];
	if (srsName) {
		node.setAttribute('srsName', srsName);
	}
	var posList = ol.xml.createElementNS(node.namespaceURI, 'gml:posList');
	node.appendChild(posList);
	this.writePosList_(posList, geometry, objectStack);
};

kworks.format.GML3.prototype.RING_NODE_FACTORY_ = function(value, objectStack, opt_nodeName) {
	var context = objectStack[objectStack.length - 1];
	var parentNode = context.node;
	goog.asserts.assert(goog.isObject(context), 'context should be an Object');
	var exteriorWritten = context['exteriorWritten'];
	if (!goog.isDef(exteriorWritten)) {
		context['exteriorWritten'] = true;
	}
	return ol.xml.createElementNS(parentNode.namespaceURI, goog.isDef(exteriorWritten) ? 'gml:interior' : 'gml:exterior');
};

kworks.format.GML3.prototype.writeSurfaceOrPolygon_ = function(node, geometry, objectStack) {
	var context = objectStack[objectStack.length - 1];
	goog.asserts.assert(goog.isObject(context), 'context should be an Object');
	var srsName = context['srsName'];
	if (node.nodeName !== 'PolygonPatch' && goog.isDefAndNotNull(srsName)) {
		node.setAttribute('srsName', srsName);
	}
	if (node.nodeName === 'gml:Polygon' || node.nodeName === 'gml:PolygonPatch') {
		var rings = geometry.getLinearRings();
		ol.xml.pushSerializeAndPop({
			node : node,
			srsName : srsName
		}, kworks.format.GML3.RING_SERIALIZERS_, this.RING_NODE_FACTORY_, rings, objectStack, undefined, this);
	} else if (node.nodeName === 'gml:Surface') {
		var patches = ol.xml.createElementNS(node.namespaceURI, 'gml:patches');
		node.appendChild(patches);
		this.writeSurfacePatches_(patches, geometry, objectStack);
	}
};

kworks.format.GML3.prototype.writeCurveOrLineString_ = function(node, geometry, objectStack) {
	var context = objectStack[objectStack.length - 1];
	goog.asserts.assert(goog.isObject(context), 'context should be an Object');
	var srsName = context['srsName'];
	if (node.nodeName !== 'LineStringSegment' && goog.isDefAndNotNull(srsName)) {
		node.setAttribute('srsName', srsName);
	}
	if (node.nodeName === 'gml:LineString' || node.nodeName === 'gml:LineStringSegment') {
		var posList = ol.xml.createElementNS(node.namespaceURI, 'gml:posList');
		node.appendChild(posList);
		this.writePosList_(posList, geometry, objectStack);
	} else if (node.nodeName === 'gml:Curve') {
		var segments = ol.xml.createElementNS(node.namespaceURI, 'gml:segments');
		node.appendChild(segments);
		this.writeCurveSegments_(segments, geometry, objectStack);
	}
};

kworks.format.GML3.prototype.writeRing_ = function(node, ring, objectStack) {
	var linearRing = ol.xml.createElementNS(node.namespaceURI, 'gml:LinearRing');
	node.appendChild(linearRing);
	this.writeLinearRing_(linearRing, ring, objectStack);
};

kworks.format.GML3.prototype.writeCurveSegments_ = function(node, line, objectStack) {
	var child = ol.xml.createElementNS(node.namespaceURI, 'gml:LineStringSegment');
	node.appendChild(child);
	this.writeCurveOrLineString_(child, line, objectStack);
};

//kworks.format.GML3.prototype.GEOMETRY_NODE_FACTORY_ = function(value, objectStack, opt_nodeName) {
//	var context = objectStack[objectStack.length - 1];
//	goog.asserts.assert(goog.isObject(context), 'context should be an Object');
//	var multiSurface = context['multiSurface'];
//	var surface = context['surface'];
//	var curve = context['curve'];
//	var multiCurve = context['multiCurve'];
//	var parentNode = objectStack[objectStack.length - 1].node;
//	goog.asserts.assert(ol.xml.isNode(parentNode), 'parentNode should be a node');
//	var nodeName;
//	if (!goog.isArray(value)) {
//		goog.asserts.assertInstanceof(value, ol.geom.Geometry, 'value should be an ol.geom.Geometry');
//		nodeName = value.getType();
//		if (nodeName === 'MultiPolygon' && multiSurface === true) {
//			nodeName = 'MultiSurface';
//		} else if (nodeName === 'Polygon' && surface === true) {
//			nodeName = 'Surface';
//		} else if (nodeName === 'LineString' && curve === true) {
//			nodeName = 'Curve';
//		} else if (nodeName === 'MultiLineString' && multiCurve === true) {
//			nodeName = 'MultiCurve';
//		}
//	} else {
//		nodeName = 'Envelope';
//	}
//	return ol.xml.createElementNS('http://www.opengis.net/gml', 'gml:' + nodeName);
//};

kworks.format.GML3.prototype.writeGeometryElement = function(node, geometry, objectStack) {
	var context = objectStack[objectStack.length - 1];
	goog.asserts.assert(goog.isObject(context), 'context should be an Object');
	var item = goog.object.clone(context);
	item.node = node;
	var value;
	if (goog.isArray(geometry)) {
		if (context.dataProjection) {
			value = ol.proj.transformExtent(geometry, context.featureProjection, context.dataProjection);
		} else {
			value = geometry;
		}
	} else {
		goog.asserts.assertInstanceof(geometry, ol.geom.Geometry, 'geometry should be an ol.geom.Geometry');
		value = ol.format.Feature.transformWithOptions(geometry, true, context);
	}
	ol.xml.pushSerializeAndPop(/** @type {ol.xml.NodeStackItem} */
	(item), kworks.format.GML3.GEOMETRY_SERIALIZERS_, this.GEOMETRY_NODE_FACTORY_, [ value ], objectStack, undefined, this);
};

kworks.format.GML3.RING_SERIALIZERS_ = {
  'http://www.opengis.net/gml': {
    'exterior': ol.xml.makeChildAppender(kworks.format.GML3.prototype.writeRing_),
    'interior': ol.xml.makeChildAppender(kworks.format.GML3.prototype.writeRing_)
  },
  'http://www.opengis.net/gml/3.2': {
	    'exterior': ol.xml.makeChildAppender(kworks.format.GML3.prototype.writeRing_),
	    'interior': ol.xml.makeChildAppender(kworks.format.GML3.prototype.writeRing_)
	  }
};


kworks.format.GML3.GEOMETRY_SERIALIZERS_ = {
	'http://www.opengis.net/gml' : {
		'Curve' : ol.xml.makeChildAppender(kworks.format.GML3.prototype.writeCurveOrLineString_),
		'MultiCurve' : ol.xml.makeChildAppender(ol.format.GML3.prototype.writeMultiCurveOrLineString_),
		'Point' : ol.xml.makeChildAppender(kworks.format.GML3.prototype.writePoint_),
		'MultiPoint' : ol.xml.makeChildAppender(ol.format.GML3.prototype.writeMultiPoint_),
		'LineString' : ol.xml.makeChildAppender(kworks.format.GML3.prototype.writeCurveOrLineString_),
		'MultiLineString' : ol.xml.makeChildAppender(ol.format.GML3.prototype.writeMultiCurveOrLineString_),
		'LinearRing' : ol.xml.makeChildAppender(kworks.format.GML3.prototype.writeLinearRing_),
		'Polygon' : ol.xml.makeChildAppender(kworks.format.GML3.prototype.writeSurfaceOrPolygon_),
		'MultiPolygon' : ol.xml.makeChildAppender(ol.format.GML3.prototype.writeMultiSurfaceOrPolygon_),
		'Surface' : ol.xml.makeChildAppender(kworks.format.GML3.prototype.writeSurfaceOrPolygon_),
		'MultiSurface' : ol.xml.makeChildAppender(ol.format.GML3.prototype.writeMultiSurfaceOrPolygon_),
		'Envelope' : ol.xml.makeChildAppender(ol.format.GML3.prototype.writeEnvelope)
	},
	'http://www.opengis.net/gml/3.2' : {
		'Curve' : ol.xml.makeChildAppender(kworks.format.GML3.prototype.writeCurveOrLineString_),
		'MultiCurve' : ol.xml.makeChildAppender(ol.format.GML3.prototype.writeMultiCurveOrLineString_),
		'Point' : ol.xml.makeChildAppender(kworks.format.GML3.prototype.writePoint_),
		'MultiPoint' : ol.xml.makeChildAppender(ol.format.GML3.prototype.writeMultiPoint_),
		'LineString' : ol.xml.makeChildAppender(kworks.format.GML3.prototype.writeCurveOrLineString_),
		'MultiLineString' : ol.xml.makeChildAppender(ol.format.GML3.prototype.writeMultiCurveOrLineString_),
		'LinearRing' : ol.xml.makeChildAppender(kworks.format.GML3.prototype.writeLinearRing_),
		'Polygon' : ol.xml.makeChildAppender(kworks.format.GML3.prototype.writeSurfaceOrPolygon_),
		'MultiPolygon' : ol.xml.makeChildAppender(ol.format.GML3.prototype.writeMultiSurfaceOrPolygon_),
		'Surface' : ol.xml.makeChildAppender(kworks.format.GML3.prototype.writeSurfaceOrPolygon_),
		'MultiSurface' : ol.xml.makeChildAppender(ol.format.GML3.prototype.writeMultiSurfaceOrPolygon_),
		'Envelope' : ol.xml.makeChildAppender(ol.format.GML3.prototype.writeEnvelope)
	}
};

kworks.format.GML3.prototype.GEOMETRY_NODE_FACTORY_ = function(value, objectStack, opt_nodeName) {
	var context = objectStack[objectStack.length - 1];
	goog.asserts.assert(goog.isObject(context), 'context should be an Object');
	var multiSurface = context['multiSurface'];
	var surface = context['surface'];
	var curve = context['curve'];
	var multiCurve = context['multiCurve'];
	var parentNode = objectStack[objectStack.length - 1].node;
	goog.asserts.assert(ol.xml.isNode(parentNode), 'parentNode should be a node');
	var nodeName;
	if (!goog.isArray(value)) {
		goog.asserts.assertInstanceof(value, ol.geom.Geometry, 'value should be an ol.geom.Geometry');
		nodeName = value.getType();
		if (nodeName === 'MultiPolygon' && multiSurface === true) {
			nodeName = 'MultiSurface';
		} else if (nodeName === 'Polygon' && surface === true) {
			nodeName = 'Surface';
		} else if (nodeName === 'LineString' && curve === true) {
			nodeName = 'Curve';
		} else if (nodeName === 'MultiLineString' && multiCurve === true) {
			nodeName = 'MultiCurve';
		}
	} else {
		nodeName = 'Envelope';
	}
	
	var serverType = context['serverType'];
	if (serverType == 'arcgis') {
		return ol.xml.createElementNS('http://www.opengis.net/gml/3.2', 'gml:' + nodeName);
	} else {
		return ol.xml.createElementNS('http://www.opengis.net/gml', 'gml:' + nodeName);
	}
};

kworks.format.GML3.prototype.readFeatures = function(source, opt_options) {
  if (ol.xml.isDocument(source)) {
    return this.readFeaturesFromDocument(
        /** @type {Document} */ (source), opt_options);
  } else if (ol.xml.isNode(source)) {
    return this.readFeaturesFromNode(/** @type {Node} */ (source), opt_options);
  } else if (goog.isString(source)) {
	  var features = [];
	  var prefixOgc = "";
	  if(browserUtils.checkIe()) {
		prefixOgc = "gml:";
	}
	var featureMembers = $.parseXML(source).getElementsByTagName(prefixOgc+"featureMember");
	if(featureMembers.length > 1) {
		for(var i=0, len=featureMembers.length; i < len; i++) {
		  var feature = this.readFeatures(featureMembers[i]);
		  features.push(feature);
	    }
	  }
	  else {
		  var doc = ol.xml.parse(source);
		  features = this.readFeaturesFromDocument(doc, opt_options);
	  }
    return features;
  } else {
    goog.asserts.fail('Unknown source type');
    return [];
  }
};