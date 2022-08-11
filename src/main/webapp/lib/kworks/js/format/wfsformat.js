goog.provide('kworks.format.WFS');

/**
 * @classdesc
 * Feature format for reading and writing data in the WFS format.
 * By default, supports WFS version 1.1.0. You can pass a GML format
 * as option if you want to read a WFS that contains GML2 (WFS 1.0.0).
 * Also see {@link ol.format.GMLBase} which is used by this format.
 *
 * @constructor
 * @param {olx.format.WFSOptions=} opt_options
 *     Optional configuration object.
 * @extends {ol.format.WFS}
 * @api stable
 */
kworks.format.WFS = function(opt_options) {
	var options = opt_options ? opt_options : {};
	goog.base(this, options);
};
goog.inherits(kworks.format.WFS, ol.format.WFS);

/**
 * @param {Node} node Node.
 * @param {ol.Feature} feature Feature.
 * @param {Array.<*>} objectStack Node stack.
 * @private
 */
kworks.format.WFS.writeFeature_ = function(node, feature, objectStack) {
	var context = objectStack[objectStack.length - 1];
	goog.asserts.assert(goog.isObject(context), 'context should be an Object');
	var featureType = context['featureType'];
	var featureNS = context['featureNS'];
	var child = ol.xml.createElementNS(featureNS, featureType);
	node.appendChild(child);
	kworks.format.GML3.prototype.writeFeatureElement(child, feature, objectStack);
};

/**
 * @param {Node} node Node.
 * @param {number|string} fid Feature identifier.
 * @param {Array.<*>} objectStack Node stack.
 * @private
 */
kworks.format.WFS.writeOgcFidFilter_ = function(node, fid, objectStack) {
	var filter = ol.xml.createElementNS('http://www.opengis.net/ogc', 'ogc:Filter');
	var child = ol.xml.createElementNS('http://www.opengis.net/ogc', 'ogc:FeatureId');
	filter.appendChild(child);
	child.setAttribute('fid', fid);
	node.appendChild(filter);
};

/**
 * @param {Node} node Node.
 * @param {ol.Feature} feature Feature.
 * @param {Array.<*>} objectStack Node stack.
 * @private
 */
kworks.format.WFS.writeDelete_ = function(node, feature, objectStack) {
	var context = objectStack[objectStack.length - 1];
	goog.asserts.assert(goog.isObject(context), 'context should be an Object');
	var featureType = context['featureType'];
	var featurePrefix = context['featurePrefix'];
	featurePrefix = featurePrefix ? featurePrefix
			: ol.format.WFS.FEATURE_PREFIX;
	var featureNS = context['featureNS'];
	node.setAttribute('typeName', featurePrefix + ':' + featureType);
	ol.xml.setAttributeNS(node, ol.format.WFS.XMLNS, 'xmlns:' + featurePrefix,
			featureNS);
	var fid = feature.getId();
	if (fid) {
		kworks.format.WFS.writeOgcFidFilter_(node, fid, objectStack);
	}
};

/**
 * @param {Node} node Node.
 * @param {ol.Feature} feature Feature.
 * @param {Array.<*>} objectStack Node stack.
 * @private
 */
kworks.format.WFS.writeUpdate_ = function(node, feature, objectStack) {
	var context = objectStack[objectStack.length - 1];
	goog.asserts.assert(goog.isObject(context), 'context should be an Object');
	var featureType = context['featureType'];
	var featurePrefix = context['featurePrefix'];
	featurePrefix = featurePrefix ? featurePrefix
			: ol.format.WFS.FEATURE_PREFIX;
	var featureNS = context['featureNS'];
	node.setAttribute('typeName', featurePrefix + ':' + featureType);
	ol.xml.setAttributeNS(node, ol.format.WFS.XMLNS, 'xmlns:' + featurePrefix,
			featureNS);
	var fid = feature.getId();
	if (fid) {
		var keys = feature.getKeys();
		var values = [];
		for (var i = 0, ii = keys.length; i < ii; i++) {
			var value = feature.get(keys[i]);
			if (value !== undefined) {
				values.push({
					name : keys[i],
					value : value
				});
			}
		}
		ol.xml.pushSerializeAndPop({
			node : node,
			srsName : context['srsName'],
			serverType : context['serverType']
		}, kworks.format.WFS.TRANSACTION_SERIALIZERS_, ol.xml
				.makeSimpleNodeFactory('Property'), values, objectStack);
		kworks.format.WFS.writeOgcFidFilter_(node, fid, objectStack);
	}
};

/**
 * @type {Object.<string, Object.<string, ol.xml.Serializer>>}
 * @private
 */
kworks.format.WFS.TRANSACTION_SERIALIZERS_ = {
	'http://www.opengis.net/wfs' : {
		'Insert' : ol.xml.makeChildAppender(kworks.format.WFS.writeFeature_),
		'Update' : ol.xml.makeChildAppender(kworks.format.WFS.writeUpdate_),
		'Delete' : ol.xml.makeChildAppender(kworks.format.WFS.writeDelete_),
		'Property' : ol.xml.makeChildAppender(ol.format.WFS.writeProperty_),
		'Native' : ol.xml.makeChildAppender(ol.format.WFS.writeNative_)
	}
};

/**
 * @param {Node} node Node.
 * @param {Array.<{string}>} featureTypes Feature types.
 * @param {Array.<*>} objectStack Node stack.
 * @private
 */
kworks.format.WFS.prototype.writeGetFeature = function(params, options) {
	var format = OpenLayers.Format.WFST(params);
	var data = OpenLayers.Format.XML.prototype.write.apply(
        format, [format.writeNode("wfs:GetFeature", options)]
    );
	// miriele-20151203 : remove "xmlns:NS\d="" NS\d" from data on IE [START]
	if (fn_check_ie()) {
		data = data.replace(/xmlns:NS\d+="" NS\d+:/gi, "");
	}
	// miriele-20151203 : remove "xmlns:NS\d="" NS\d" from data on IE [END]
	return data;
};

/**
 * Encode format as WFS `Transaction` and return the Node.
 *
 * @param {Array.<ol.Feature>} inserts The features to insert.
 * @param {Array.<ol.Feature>} updates The features to update.
 * @param {Array.<ol.Feature>} deletes The features to delete.
 * @param {olx.format.WFSWriteTransactionOptions} options Write options.
 * @return {Node} Result.
 * @api stable
 */
kworks.format.WFS.prototype.writeTransaction = function(inserts, updates, deletes, options) {
	var objectStack = [];
	var node = ol.xml.createElementNS('http://www.opengis.net/wfs', 'Transaction');
	node.setAttribute('service', 'WFS');
	node.setAttribute('version', '1.1.0');
	if (options.serverType == 'arcgis') {
		node.setAttribute('xmlns:gml', 'http://www.opengis.net/gml/3.2');
	} else {
		node.setAttribute('xmlns:gml', 'http://www.opengis.net/gml'); // miriele-add
	}
	
	var baseObj = null, obj;
	if (options) {
		baseObj = options.gmlOptions ? options.gmlOptions : {};
		if (options.handle) {
			node.setAttribute('handle', options.handle);
		}
	}
	ol.xml.setAttributeNS(node, 'http://www.w3.org/2001/XMLSchema-instance', 'xsi:schemaLocation', this.schemaLocation_);

	if (inserts) {
		obj = {
			node : node,
			featureNS : options.featureNS,
			featureType : options.featureType,
			featurePrefix : options.featurePrefix,
			srsName : options.srsName,
			serverType : options.serverType
		};
		goog.object.extend(obj, baseObj);
		ol.xml.pushSerializeAndPop(obj, kworks.format.WFS.TRANSACTION_SERIALIZERS_, ol.xml.makeSimpleNodeFactory('Insert'), inserts, objectStack);
	}
	if (updates) {
		obj = {
			node : node,
			featureNS : options.featureNS,
			featureType : options.featureType,
			featurePrefix : options.featurePrefix,
			srsName : options.srsName,
			serverType : options.serverType
		};
		goog.object.extend(obj, baseObj);
		ol.xml.pushSerializeAndPop(obj, kworks.format.WFS.TRANSACTION_SERIALIZERS_, ol.xml.makeSimpleNodeFactory('Update'), updates, objectStack);
	}
	if (deletes) {
		ol.xml.pushSerializeAndPop({
			node : node,
			featureNS : options.featureNS,
			featureType : options.featureType,
			featurePrefix : options.featurePrefix
		}, kworks.format.WFS.TRANSACTION_SERIALIZERS_, ol.xml.makeSimpleNodeFactory('Delete'), deletes, objectStack);
	}
	if (options.nativeElements) {
		ol.xml.pushSerializeAndPop({
			node : node,
			featureNS : options.featureNS,
			featureType : options.featureType,
			featurePrefix : options.featurePrefix
		}, ol.format.WFS.TRANSACTION_SERIALIZERS_, ol.xml.makeSimpleNodeFactory('Native'), options.nativeElements, objectStack);
	}
	return node;
};