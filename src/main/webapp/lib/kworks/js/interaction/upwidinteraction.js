goog.provide('kworks.interaction.Upwid');
goog.provide('kworks.interaction.UpwidEventType');
goog.provide('kworks.interaction.UpwidEvent');

goog.require('goog.array');
goog.require('goog.asserts');
goog.require('goog.events');
goog.require('goog.events.Event');
goog.require('goog.events.EventType');
goog.require('goog.functions');
goog.require('ol');
goog.require('ol.Collection');
goog.require('ol.CollectionEventType');
goog.require('ol.Feature');
goog.require('ol.MapBrowserEvent.EventType');
goog.require('ol.MapBrowserPointerEvent');
goog.require('ol.ViewHint');
goog.require('ol.coordinate');
goog.require('ol.events.condition');
goog.require('ol.extent');
goog.require('ol.geom.GeometryType');
goog.require('ol.geom.LineString');
goog.require('ol.geom.MultiLineString');
goog.require('ol.interaction.Pointer');
goog.require('ol.layer.Vector');
goog.require('ol.source.Vector');
goog.require('ol.structs.RBush');

/**
 * @enum {string}
 */
kworks.interaction.UpwidEventType = {
	/**
	 * Triggered upon feature modification start
	 * @event kworks.interaction.UpwidEvent #upwidstart
	 * @api
	 */
	UPWIDSTART: 'upwidstart',
		
	/**
	 * Triggered upon feature modification end
	 * @event kworks.interaction.UpwidEvent #upwidend
	 * @api
	 */
	UPWIDEND: 'upwidend'
};


/**
 * @classdesc
 * Events emitted by {@link kworks.interaction.Upwid} instances are instances of
 * this type.
 *
 * @constructor
 * @extends {goog.events.Event}
 * @implements {kworks.interaction.UpwidEvent}
 * @param {kworks.interaction.UpwidEventType} type Type.
 * @param {ol.Collection.<ol.Feature>} features The features modified.
 * @param {ol.MapBrowserPointerEvent} mapBrowserPointerEvent Associated {@link ol.MapBrowserPointerEvent}.
 */
kworks.interaction.UpwidEvent = function(type, features, mapBrowserPointerEvent) {

	goog.base(this, type);

	/**
	 * The features being modified.
	 * @type {ol.Collection.<ol.Feature>}
	 * @api
	 */
	this.features = features;

	/**
	 * Associated {@link ol.MapBrowserPointerEvent}.
	 * @type {ol.MapBrowserPointerEvent}
	 * @api
	 */
	this.mapBrowserPointerEvent = mapBrowserPointerEvent;
};
goog.inherits(kworks.interaction.UpwidEvent, goog.events.Event);


/**
 * @typedef {{depth: (Array.<number>|undefined),
 *            feature: ol.Feature,
 *            geometry: ol.geom.SimpleGeometry,
 *            index: (number|undefined),
 *            segment: Array.<ol.Extent>}}
 */
ol.interaction.SegmentDataType;


/**
 * @classdesc
 * Interaction for modifying vector data.
 *
 * @constructor
 * @extends {ol.interaction.Pointer}
 * @param {olx.interaction.ModifyOptions} options Options.
 * @api stable
 */
kworks.interaction.Upwid = function(options) {

	goog.base(this, {
		handleDownEvent: kworks.interaction.Upwid.handleDownEvent_,
	    handleDragEvent: kworks.interaction.Upwid.handleDragEvent_,
	    handleEvent: kworks.interaction.Upwid.handleEvent,
	    handleUpEvent: kworks.interaction.Upwid.handleUpEvent_
	});	

	/**
	 * @type {ol.events.ConditionType}
	 * @private
	 */
	this.deleteCondition_ = options.deleteCondition ? options.deleteCondition :
		/** @type {ol.events.ConditionType} */ 
		(goog.functions.and(ol.events.condition.noModifierKeys, ol.events.condition.singleClick));	
	
	/**
	 * Editing vertex.
	 * @type {ol.Feature}
	 * @private
	 */
	this.vertexFeature_ = null;

	/**
	 * Segments intersecting {@link this.vertexFeature_} by segment uid.
	 * @type {Object.<string, boolean>}
	 * @private
	 */
	this.vertexSegments_ = null;

	/**
	 * @type {ol.Pixel}
	 * @private
	 */
	this.lastPixel_ = [0, 0];

	/**
	 * Tracks if the next `singleclick` event should be ignored to prevent
	 * accidental deletion right after vertex creation.
	 * @type {boolean}
	 * @private
	 */
	this.ignoreNextSingleClick_ = false;
	
	/**
	 * @type {boolean}
	 * @private
	 */
	this.modified_ = false;	
	  
	/**
	 * Segment RTree for each layer
	 * @type {Object.<*, ol.structs.RBush>}
	 * @private
	 */
	this.rBush_ = new ol.structs.RBush();

	/**
	 * @type {number}
	 * @private
	 */
	this.pixelTolerance_ = options.pixelTolerance !== undefined ? options.pixelTolerance : 10;

	/**
	 * @type {boolean}
	 * @private
	 */
	this.snappedToVertex_ = false;

	/**
	 * Indicate whether the interaction is currently changing a feature's
	 * coordinates.
	 * @type {boolean}
	 * @private
	 */
	this.changingFeature_ = false;
	  
	/**
	 * Draw overlay where sketch features are drawn.
	 * @type {ol.layer.Vector}
	 * @private
	 */
	this.overlay_ = new ol.layer.Vector({
		source : new ol.source.Vector({
	    useSpatialIndex : false,
	    wrapX : !!options.wrapX}),
	    style : options.style ? options.style : kworks.interaction.Upwid.getDefaultStyleFunction(),
	    updateWhileAnimating : true,
	    updateWhileInteracting : true
	});

	/**
	 * @const
	 * @private
	 * @type {Object.<string, function(ol.Feature, ol.geom.Geometry)> }
	 */
	this.SEGMENT_WRITERS_ = {
		'LineString': this.writeLineStringGeometry_,
		'MultiLineString': this.writeMultiLineStringGeometry_,
	};

	/**
	 * @type {ol.Collection.<ol.Feature>}
	 * @private
	 */
	this.features_ = options.features;

	this.features_.forEach(this.addFeature_, this);
	goog.events.listen(this.features_, ol.CollectionEventType.ADD, this.handleFeatureAdd_, false, this);
};
goog.inherits(kworks.interaction.Upwid, ol.interaction.Pointer);


kworks.interaction.Upwid.prototype.setFeatures = function(features) {
	this.features_.forEach(this.removeFeature_, this);
	
	this.features_ = features;
	this.features_.forEach(this.addFeature_, this);
	goog.events.listen(this.features_, ol.CollectionEventType.ADD, this.handleFeatureAdd_, false, this);
};

kworks.interaction.Upwid.prototype.clearFeatures = function() {
	if(this.features_) {
		goog.events.listen(this.features_, ol.CollectionEventType.ADD, this.handleFeatureAdd_, false, this);
		
		this.features_.forEach(this.removeFeature_, this);
		this.features_ = new ol.Collection();
	}
};

/**
 * @param {ol.Feature} feature Feature.
 * @private
 */
kworks.interaction.Upwid.prototype.addFeature_ = function(feature) {
	var geometry = feature.getGeometry();
	if (goog.isDef(this.SEGMENT_WRITERS_[geometry.getType()])) {
		this.SEGMENT_WRITERS_[geometry.getType()].call(this, feature, geometry);
	}
	
	var map = this.getMap();
	if (!goog.isNull(map)) {
		this.handlePointerAtPixel_(this.lastPixel_, map);
	}
};

/**
 * @param {ol.MapBrowserPointerEvent} evt Map browser event
 * @private
 */
kworks.interaction.Upwid.prototype.willModifyFeatures_ = function(evt) {
	if (!this.modified_) {
		this.modified_ = true;
		this.dispatchEvent(new kworks.interaction.UpwidEvent(
			kworks.interaction.UpwidEventType.UPWIDSTART, this.features_, evt));
	}
};

/**
 * @param {ol.Feature} feature Feature.
 * @private
 */
kworks.interaction.Upwid.prototype.removeFeature_ = function(feature) {
	this.removeFeatureSegmentData_(feature);
	// Remove the vertex feature if the collection of canditate features
	// is empty.
	if (this.vertexFeature_ && this.features_.getLength() === 0) {
		this.overlay_.getSource().removeFeature(this.vertexFeature_);
		this.vertexFeature_ = null;
	}
	goog.events.unlisten(feature, goog.events.EventType.CHANGE, this.handleFeatureChange_, false, this);
};

/**
 * @param {ol.Feature} feature Feature.
 * @private
 */
kworks.interaction.Upwid.prototype.removeFeatureSegmentData_ = function(feature) {
	var rBush = this.rBush_;
	var /** @type {Array.<ol.interaction.SegmentDataType>} */ nodesToRemove = [];
	rBush.forEach(
		/**
		 * @param {ol.interaction.SegmentDataType} node RTree node.
		 */
		function(node) {
			if (feature === node.feature) {
				nodesToRemove.push(node);
			}
		}
	);
	for (var i = nodesToRemove.length - 1; i >= 0; --i) {
		rBush.remove(nodesToRemove[i]);
	}
};

/**
 * @inheritDoc
 */
kworks.interaction.Upwid.prototype.setMap = function(map) {
	this.overlay_.setMap(map);
	goog.base(this, 'setMap', map);
};

/**
 * @param {ol.CollectionEvent} evt Event.
 * @private
 */
kworks.interaction.Upwid.prototype.handleFeatureAdd_ = function(evt) {
	var feature = evt.element;
	goog.asserts.assertInstanceof(feature, ol.Feature, 'feature should be an ol.Feature');
	this.addFeature_(feature);
};

/**
 * @param {goog.events.Event} evt Event.
 * @private
 */
kworks.interaction.Upwid.prototype.handleFeatureChange_ = function(evt) {
	if (!this.changingFeature_) {
		var feature = (evt.target); // @type {ol.Feature} 
		this.removeFeature_(feature);
		this.addFeature_(feature);
	}
};

/**
 * @param {ol.CollectionEvent} evt Event.
 * @private
 */
kworks.interaction.Upwid.prototype.handleFeatureRemove_ = function(evt) {
	var feature = (evt.element);	// @type {ol.Feature}
	this.removeFeature_(feature);
};

/**
 * @param {ol.Feature} feature Feature
 * @param {ol.geom.LineString} geometry Geometry.
 * @private
 */
kworks.interaction.Upwid.prototype.writeLineStringGeometry_ = function(feature, geometry) {
	var coordinates = geometry.getCoordinates();
	var i, ii, segment, segmentData;
	for (i = 0, ii = coordinates.length - 1; i < ii; ++i) {
		segment = coordinates.slice(i, i + 2);
		/** @type {ol.interaction.SegmentDataType} */
		segmentData = ({
			feature: feature,
			geometry: geometry,
			index: i,
			segment: segment
		});
		this.rBush_.insert(ol.extent.boundingExtent(segment), segmentData);
	}
};

/**
 * @param {ol.Feature} feature Feature
 * @param {ol.geom.MultiLineString} geometry Geometry.
 * @private
 */
kworks.interaction.Upwid.prototype.writeMultiLineStringGeometry_ = function(feature, geometry) {
	var lines = geometry.getCoordinates();
	var coordinates, i, ii, j, jj, segment, segmentData;
	for (j = 0, jj = lines.length; j << jj; ++j) {
		coordinates = lines[j];
		for (i = 0, ii = coordinates.length - 1; i < ii; ++i) {
			segment = coordinates.slice(i, i + 2);
			/** @type {ol.interaction.SegmentDataType} */
			segmentData = ({
				feature: feature,
				geometry: geometry,
				depth: [j],
				index: i,
				segment: segment
			});
			this.rBush_.insert(ol.extent.boundingExtent(segment), segmentData);
		}
	}
};

/**
 * @param {ol.Coordinate} coordinates Coordinates.
 * @return {ol.Feature} Vertex feature.
 * @private
 */
kworks.interaction.Upwid.prototype.createOrUpdateVertexFeature_ = function(coordinates) {
	var vertexFeature = this.vertexFeature_;
	if (goog.isNull(vertexFeature)) {
		vertexFeature = new ol.Feature(new ol.geom.Point(coordinates));
		this.vertexFeature_ = vertexFeature;
		this.overlay_.getSource().addFeature(vertexFeature);
	} else {
		var geometry = (vertexFeature.getGeometry());	// @type {ol.geom.Point}
		geometry.setCoordinates(coordinates);
	}
	
	return vertexFeature;
};

/**
 * @param {ol.interaction.SegmentDataType} a
 * @param {ol.interaction.SegmentDataType} b
 * @return {number}
 * @private
 */
kworks.interaction.Upwid.compareIndexes_ = function(a, b) {
  return a.index - b.index;
};

/**
 * @param {ol.MapBrowserPointerEvent} evt Event.
 * @return {boolean} Start drag sequence?
 * @this {kworks.interaction.Upwid}
 * @private
 */
kworks.interaction.Upwid.handleDownEvent_ = function(evt) {
	this.handlePointerAtPixel_(evt.pixel, evt.map);
	this.dragSegments_ = [];
	
	return !goog.isNull(this.vertexFeature_);
};

/**
 * @param {ol.MapBrowserPointerEvent} evt Event.
 * @this {kworks.interaction.Upwid}
 * @private
 */
kworks.interaction.Upwid.handleDragEvent_ = function(evt) {
//	this.ignoreNextSingleClick_ = false;
//	this.willModifyFeatures_(evt);
	
	return false;
};

/**
 * @param {ol.MapBrowserPointerEvent} evt Event.
 * @return {boolean} Stop drag sequence?
 * @this {kworks.interaction.Upwid}
 * @private
 */
kworks.interaction.Upwid.handleUpEvent_ = function(evt) {
	this.ignoreNextSingleClick_ = false;
	this.willModifyFeatures_(evt);
	  
	var vertexFeature = this.vertexFeature_;
	if (vertexFeature) {
		// 편집 대상 데이터
		var feature = this.features_.item(0);
		var linestring = feature.getGeometry();
		var coordinates = linestring.getCoordinates();
		
		// 입력위치
		var geometry = vertexFeature.getGeometry();	// @type {ol.geom.Point}
		var vertex = geometry.getCoordinates();
		var center = spatialUtils.calculateNearPoint(linestring, vertex);
		
		// 상월 생성
		var upwidLine = spatialUtils.createUpwidLine(coordinates, center, 0.5);
		if (upwidLine == null) {
			$.messager.alert("상월 편집", "상월을 추가할 수 없는 위치입니다.");
			return false;
		}
		var newGeom = new ol.geom.LineString(upwidLine);
		feature.setGeometry(newGeom);
		
		var features = new ol.Collection();
		features.push(feature);
		
		this.features_ = features;
		this.features_.forEach(this.addFeature_, this);
		goog.events.listen(this.features_, ol.CollectionEventType.ADD, this.handleFeatureAdd_, false, this);
	}
	
	if (this.modified_) {
		this.dispatchEvent(new ol.interaction.ModifyEvent(
				kworks.interaction.UpwidEventType.UPWIDEND, this.features_, evt));
		this.modified_ = false;
	}
	  
	return false;
};

/**
 * @param {ol.MapBrowserEvent} mapBrowserEvent Map browser event.
 * @return {boolean} `false` to stop event propagation.
 * @this {kworks.interaction.Upwid}
 * @api
 */
kworks.interaction.Upwid.handleEvent = function(mapBrowserEvent) {
	if (!(mapBrowserEvent instanceof ol.MapBrowserPointerEvent)) {
		return true;
	}
	  
	var handled = null;
	if (!mapBrowserEvent.map.getView().getHints()[ol.ViewHint.INTERACTING] &&
		mapBrowserEvent.type == ol.MapBrowserEvent.EventType.POINTERMOVE && !this.handlingDownUpSequence) {
		this.handlePointerMove_(mapBrowserEvent);
	}
	
	if (this.vertexFeature_ && this.deleteCondition_(mapBrowserEvent)) {
		if (mapBrowserEvent.type != ol.MapBrowserEvent.EventType.SINGLECLICK || !this.ignoreNextSingleClick_) {
			var geometry = this.vertexFeature_.getGeometry();
		    goog.asserts.assertInstanceof(geometry, ol.geom.Point, 'geometry should be an ol.geom.Point');
		    this.willModifyFeatures_(mapBrowserEvent);
		    handled = this.removeVertex_();
		    this.dispatchEvent(new kworks.interaction.UpwidEvent(
	    		kworks.interaction.UpwidEventType.UPWIDEND, this.features_, mapBrowserEvent));
		      	this.modified_ = false;
	    } 
		else {
			handled = true;
	    }
	}

	if (mapBrowserEvent.type == ol.MapBrowserEvent.EventType.SINGLECLICK) {
		this.ignoreNextSingleClick_ = false;
	}

	return ol.interaction.Pointer.handleEvent.call(this, mapBrowserEvent) && !handled;
};

/**
 * @param {ol.MapBrowserEvent} evt Event.
 * @private
 */
kworks.interaction.Upwid.prototype.handlePointerMove_ = function(evt) {
	this.lastPixel_ = evt.pixel;
	this.handlePointerAtPixel_(evt.pixel, evt.map);
};

/**
 * @param {ol.Pixel} pixel Pixel
 * @param {ol.Map} map Map.
 * @private
 */
kworks.interaction.Upwid.prototype.handlePointerAtPixel_ = function(pixel, map) {
	var pixelCoordinate = map.getCoordinateFromPixel(pixel);
	var sortByDistance = function(a, b) {
		return ol.coordinate.squaredDistanceToSegment(pixelCoordinate, a.segment) - ol.coordinate.squaredDistanceToSegment(pixelCoordinate, b.segment);
	};
	
	var lowerLeft = map.getCoordinateFromPixel([pixel[0] - this.pixelTolerance_, pixel[1] + this.pixelTolerance_]);
	var upperRight = map.getCoordinateFromPixel([pixel[0] + this.pixelTolerance_, pixel[1] - this.pixelTolerance_]);
	var box = ol.extent.boundingExtent([lowerLeft, upperRight]);
	
	var rBush = this.rBush_;
	var nodes = rBush.getInExtent(box);
	if (nodes.length > 0) {
		nodes.sort(sortByDistance);
		var node = nodes[0];
		var closestSegment = node.segment;
		var vertex = (ol.coordinate.closestOnSegment(pixelCoordinate, closestSegment));
		var vertexPixel = map.getPixelFromCoordinate(vertex);
		
		if (Math.sqrt(ol.coordinate.squaredDistance(pixel, vertexPixel)) <= this.pixelTolerance_) {
			var pixel1 = map.getPixelFromCoordinate(closestSegment[0]);
			var pixel2 = map.getPixelFromCoordinate(closestSegment[1]);
			var squaredDist1 = ol.coordinate.squaredDistance(vertexPixel, pixel1);
			var squaredDist2 = ol.coordinate.squaredDistance(vertexPixel, pixel2);
			var dist = Math.sqrt(Math.min(squaredDist1, squaredDist2));
			this.snappedToVertex_ = dist <= this.pixelTolerance_;
			if (this.snappedToVertex_) {
				vertex = squaredDist1 > squaredDist2 ? closestSegment[1] : closestSegment[0];
			}	
			
			this.createOrUpdateVertexFeature_(vertex);
			var vertexSegments = {};
			vertexSegments[goog.getUid(closestSegment)] = true;
			var segment;
			for (var i = 1, ii = nodes.length; i < ii; ++i) {
				segment = nodes[i].segment;
				if ((ol.coordinate.equals(closestSegment[0], segment[0]) &&
					ol.coordinate.equals(closestSegment[1], segment[1]) ||
					(ol.coordinate.equals(closestSegment[0], segment[1]) &&
					ol.coordinate.equals(closestSegment[1], segment[0])))) {
					vertexSegments[goog.getUid(segment)] = true;
				} 
				else {
					break;
				}
			}
			
			this.vertexSegments_ = vertexSegments;
			return;			
		}
	}
	
	if (!goog.isNull(this.vertexFeature_)) {
		this.overlay_.getSource().removeFeature(this.vertexFeature_);
		this.vertexFeature_ = null;
	}	
};

/**
 * @param {ol.interaction.SegmentDataType} segmentData Segment data.
 * @param {ol.Coordinate} vertex Vertex.
 * @private
 */
kworks.interaction.Upwid.prototype.insertVertex_ = function(segmentData, vertex) {
	var segment = segmentData.segment;
	var feature = segmentData.feature;
	var geometry = segmentData.geometry;
	var depth = segmentData.depth;
	var index = segmentData.index;
	var coordinates;

	while (vertex.length < geometry.getStride()) {
		vertex.push(0);
	}

	switch (geometry.getType()) {
    case ol.geom.GeometryType.MULTI_LINE_STRING:
    	goog.asserts.assertInstanceof(geometry, ol.geom.MultiLineString);
    	coordinates = geometry.getCoordinates();
    	coordinates[depth[0]].splice(index + 1, 0, vertex);
    	break;
    	
    case ol.geom.GeometryType.LINE_STRING:
    	goog.asserts.assertInstanceof(geometry, ol.geom.LineString);
    	coordinates = geometry.getCoordinates();
    	coordinates.splice(index + 1, 0, vertex);
    	break;
    	
    default:
      return;
	}

	geometry.setCoordinates(coordinates);
	var rTree = this.rBush_;
	goog.asserts.assert(goog.isDef(segment));
	rTree.remove(segmentData);
	goog.asserts.assert(goog.isDef(index));
	this.updateSegmentIndices_(geometry, index, depth, 1);
	/** @type {ol.interaction.SegmentDataType} */
	var newSegmentData = ({
		segment: [segment[0], vertex],
		feature: feature,
		geometry: geometry,
		depth: depth,
		index: index
	});
	
	rTree.insert(ol.extent.boundingExtent(newSegmentData.segment), newSegmentData);
	this.dragSegments_.push([newSegmentData, 1]);

	/** @type {ol.interaction.SegmentDataType} */
	var newSegmentData2 = ({
		segment: [vertex, segment[1]],
		feature: feature,
		geometry: geometry,
		depth: depth,
		index: index + 1
	});
	
	rTree.insert(ol.extent.boundingExtent(newSegmentData2.segment), newSegmentData2);
	this.dragSegments_.push([newSegmentData2, 0]);
};

/**
 * Removes a vertex from all matching features.
 * @return {boolean} True when a vertex was removed.
 * @private
 */
kworks.interaction.Upwid.prototype.removeVertex_ = function() {
	var dragSegments = this.dragSegments_;
	var segmentsByFeature = {};
	var component, coordinates, dragSegment, geometry, i, index, left;
	var newIndex, newSegment, right, segmentData, uid, deleted;
	for (i = dragSegments.length - 1; i >= 0; --i) {
		dragSegment = dragSegments[i];
		segmentData = dragSegment[0];
		geometry = segmentData.geometry;
		coordinates = geometry.getCoordinates();
		uid = goog.getUid(segmentData.feature);
		if (segmentData.depth) {
			// separate feature components
			uid += '-' + segmentData.depth.join('-');
		}
		
		left = right = index = undefined;
		if (dragSegment[1] === 0) {
			right = segmentData;
			index = segmentData.index;
		} 
		else if (dragSegment[1] == 1) {
			left = segmentData;
			index = segmentData.index + 1;
		}
		
		if (!(uid in segmentsByFeature)) {
			segmentsByFeature[uid] = [left, right, index];
		}
		
		newSegment = segmentsByFeature[uid];
		if (left !== undefined) {
			newSegment[0] = left;
		}
		
		if (right !== undefined) {
			newSegment[1] = right;
		}
		
		if (newSegment[0] !== undefined && newSegment[1] !== undefined) {
			component = coordinates;
			deleted = false;
			newIndex = index - 1;
			
			switch (geometry.getType()) {
			case ol.geom.GeometryType.MULTI_LINE_STRING:
				coordinates[segmentData.depth[0]].splice(index, 1);
				deleted = true;
				break;
				
			case ol.geom.GeometryType.LINE_STRING:
				coordinates.splice(index, 1);
				deleted = true;
				break;
				
			default:
				// pass
			}

			if (deleted) {
				this.rBush_.remove(newSegment[0]);
				this.rBush_.remove(newSegment[1]);
				this.setGeometryCoordinates_(geometry, coordinates);
				goog.asserts.assert(newIndex >= 0, 'newIndex should be larger than 0');
				/** @type {ol.interaction.SegmentDataType} */
				var newSegmentData = ({
					depth: segmentData.depth,
					feature: segmentData.feature,
					geometry: segmentData.geometry,
					index: newIndex,
					segment: [newSegment[0].segment[0], newSegment[1].segment[1]]
				});
				
				this.rBush_.insert(ol.extent.boundingExtent(newSegmentData.segment), newSegmentData);
				this.updateSegmentIndices_(geometry, index, segmentData.depth, -1);

				if (this.vertexFeature_) {
					this.overlay_.getSource().removeFeature(this.vertexFeature_);
					this.vertexFeature_ = null;
				}
			}
		}
	}
	return true;
};

/**
 * @param {ol.geom.SimpleGeometry} geometry Geometry.
 * @param {Array} coordinates Coordinates.
 * @private
 */
kworks.interaction.Upwid.prototype.setGeometryCoordinates_ = function(geometry, coordinates) {
	this.changingFeature_ = true;
	geometry.setCoordinates(coordinates);
	this.changingFeature_ = false;
};

/**
 * @param {ol.geom.SimpleGeometry} geometry Geometry.
 * @param {number} index Index.
 * @param {Array.<number>|undefined} depth Depth.
 * @param {number} delta Delta (1 or -1).
 * @private
 */
kworks.interaction.Upwid.prototype.updateSegmentIndices_ = function(geometry, index, depth, delta) {
	this.rBush_.forEachInExtent(geometry.getExtent(), function(segmentDataMatch) {
    if (segmentDataMatch.geometry === geometry && 
		(!goog.isDef(depth) || goog.array.equals(segmentDataMatch.depth, depth)) && 
		segmentDataMatch.index > index) {
    		segmentDataMatch.index += delta;
    	}
	});
};

/**
 * @return {ol.style.StyleFunction} Styles.
 */
kworks.interaction.Upwid.getDefaultStyleFunction = function() {
	return function(feature, resolution) {
		var radius = 0.5 / resolution;
		var style = new ol.style.Style({
            image : new ol.style.Circle({
                radius : radius,
                stroke : new ol.style.Stroke({
                    color : 'rgba(255, 255, 255, 1.0)',
                    width : 3
                }),
                fill : new ol.style.Fill({
                    color : 'rgba(0, 153, 255, 0.2)'
                })
            })
		});
		
		return style;
	};
};