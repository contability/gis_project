/*
 * modifyInteraction.js와 파일 통합(2021.01.22)
 */

goog.provide('kworks.interaction');

goog.require('ol.Collection');
goog.require('ol.Kinetic');
goog.require('ol.interaction.DoubleClickZoom');
goog.require('ol.interaction.DragPan');
goog.require('ol.interaction.DragRotate');
goog.require('ol.interaction.DragZoom');
goog.require('ol.interaction.KeyboardPan');
goog.require('ol.interaction.KeyboardZoom');
goog.require('ol.interaction.MouseWheelZoom');
goog.require('ol.interaction.PinchRotate');
goog.require('ol.interaction.PinchZoom');

kworks.interaction.defaults = function(opt_options) {

	var options = goog.isDef(opt_options) ? opt_options : {};

	var interactions = new ol.Collection();

	var kinetic = new ol.Kinetic(-0.005, 0.05, 100);

	var dragRotate = new ol.interaction.DragRotate();
	dragRotate.set("id", "dragRotate");
	dragRotate.set("name", "drag");
	interactions.push(dragRotate);

	var doubleClickZoom = new ol.interaction.DoubleClickZoom({
		delta: options.zoomDelta,
		duration: options.zoomDuration
	});
	doubleClickZoom.set("id", "doubleClickZoom");
	doubleClickZoom.set("name", "drag");
	interactions.push(doubleClickZoom);

	var dragPan = new ol.interaction.DragPan({
      kinetic: kinetic
    });
	dragPan.set("id", "dragPan");
	dragPan.set("name", "drag");
    interactions.push(dragPan);

    var pinchRotate = new ol.interaction.PinchRotate();
    pinchRotate.set("id", "pinchRotate");
    pinchRotate.set("name", "drag");
    interactions.push(pinchRotate);

    var pinchZoom = new ol.interaction.PinchZoom({
        duration: options.zoomDuration
    });
    pinchZoom.set("id", "pinchZoom");
    pinchZoom.set("name", "drag");
    interactions.push(pinchZoom);

    var keyboardPan = new ol.interaction.KeyboardPan();
    keyboardPan.set("id", "keyboardPan");
    keyboardPan.set("name", "drag");
    interactions.push(keyboardPan);
    
    var keyboardZoom = new ol.interaction.KeyboardZoom({
        id : "keyboardZoom",
        name : "drag",
        delta: options.zoomDelta,
        duration: options.zoomDuration
    });
    keyboardZoom.set("id", "keyboardZoom");
    keyboardZoom.set("name", "drag");
    interactions.push(keyboardZoom);

    var mouseWheelZoom = new ol.interaction.MouseWheelZoom({
        duration: options.zoomDuration
    });
    mouseWheelZoom.set("id", "mouseWheelZoom");
    mouseWheelZoom.set("name", "drag");
    interactions.push(mouseWheelZoom);

    var dragZoom = new ol.interaction.DragZoom();
    dragZoom.set("id", "dragZoom");
    dragZoom.set("name", "drag");
    interactions.push(dragZoom);
    
    /// 영역 확대
    var dragZoomIn = new kworks.interaction.DragZoomIn();
    dragZoomIn.set("id", "dragZoomIn");
    dragZoomIn.set("name", "dragZoomIn");
    interactions.push(dragZoomIn);
    
    /// 영역 축소
    var dragZoomOut = new kworks.interaction.DragZoomOut();
    dragZoomOut.set("id", "dragZoomOut");
    dragZoomOut.set("name", "dragZoomOut");
    interactions.push(dragZoomOut);
    
    /// 마우스 위치
    var mousePosition = new kworks.interaction.MousePosition();
    mousePosition.set("id", "mousePosition");
    mousePosition.set("name", "mousePosition");
    interactions.push(mousePosition);
    
    /// 드래그 앤 드롭 (Shape, GeoJson)
    var dragAndDropInteraction = new kworks.interaction.DragAndDrop({
    	formatConstructors: [
            ol.format.GeoJSON,
            kworks.format.GML3
	    ]
	});
    dragAndDropInteraction.set("id", "dragAndDrop");
    dragAndDropInteraction.set("name", "dragAndDrop");
    interactions.push(dragAndDropInteraction);
    
    /// 측정
    if(goog.isDef(options.measureSource)) {
    	var distance = new kworks.interaction.Measure({ type : "coordinate", source : options.measureSource });
    	distance.set("id", "coordinate");
    	distance.set("name", "coordinate");
    	interactions.push(distance);
    	
    	// 거리 측정
    	var distance = new kworks.interaction.Measure({ type : "distance", source : options.measureSource });
    	distance.set("id", "distance");
    	distance.set("name", "distance");
    	interactions.push(distance);
    	
    	//면적 측정
    	var area = new kworks.interaction.Measure({ type : "area", source : options.measureSource });
    	area.set("id", "area");
    	area.set("name", "area");
    	interactions.push(area);
    	
    	// 반경 측정
    	var radius = new kworks.interaction.Measure({ type : "radius", source : options.measureSource });
    	radius.set("id", "radius");
    	radius.set("name", "radius");
    	interactions.push(radius);
    }
    
    return interactions;

};

/*
 * modifyInteraction.js
 */

goog.provide('kworks.interaction.Modify');

goog.require('ol.interaction.Modify');

kworks.interaction.Modify = function(options) {
	goog.base(this, options);
};
goog.inherits(kworks.interaction.Modify, ol.interaction.Modify);

kworks.interaction.Modify.prototype.setFeatures = function(features) {
	this.features_.forEach(this.removeFeature_, this);
	
	this.features_ = features;
	this.features_.forEach(this.addFeature_, this);
	  goog.events.listen(this.features_, ol.CollectionEventType.ADD,
	      this.handleFeatureAdd_, false, this);
	  goog.events.listen(this.features_, ol.CollectionEventType.REMOVE,
	      this.handleFeatureRemove_, false, this);
};

kworks.interaction.Modify.prototype.clearFeatures = function() {
	if(this.features_) {
		goog.events.listen(this.features_, ol.CollectionEventType.ADD, this.handleFeatureAdd_, false, this);
		goog.events.listen(this.features_, ol.CollectionEventType.REMOVE, this.handleFeatureRemove_, false, this);
		
		this.features_.forEach(this.removeFeature_, this);
		this.features_ = new ol.Collection();
	}
};

kworks.interaction.Modify.prototype.removeVertex_ = function() {
	var dragSegments = this.dragSegments_;
	var segmentsByFeature = {};
	var component, coordinates, dragSegment, geometry, i, index, left;
	var newIndex, newSegment, right, segmentData, uid, deleted;
	
	if(!dragSegments) {
		dragSegments = [];
	}
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
		} else if (dragSegment[1] == 1) {
			left = segmentData;
			index = segmentData.index + 1;
		}
		if (!(uid in segmentsByFeature)) {
			segmentsByFeature[uid] = [ left, right, index ];
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
			case ol.geom.GeometryType.MULTI_POLYGON:
				component = component[segmentData.depth[1]];
				/* falls through */
			case ol.geom.GeometryType.POLYGON:
				component = component[segmentData.depth[0]];
				if (component.length > 4) {
					if (index == component.length - 1) {
						index = 0;
					}
					component.splice(index, 1);
					deleted = true;
					if (index === 0) {
						// close the ring again
						component.pop();
						component.push(component[0]);
						newIndex = component.length - 1;
					}
				}
				break;
			default:
				// pass
			}

			if (deleted) {
				this.rBush_.remove(newSegment[0]);
				this.rBush_.remove(newSegment[1]);
				this.setGeometryCoordinates_(geometry, coordinates);
				goog.asserts.assert(newIndex >= 0,
						'newIndex should be larger than 0');
				var newSegmentData = /** @type {ol.interaction.SegmentDataType} */
				({
					depth : segmentData.depth,
					feature : segmentData.feature,
					geometry : segmentData.geometry,
					index : newIndex,
					segment : [ newSegment[0].segment[0],
							newSegment[1].segment[1] ]
				});
				this.rBush_
						.insert(ol.extent
								.boundingExtent(newSegmentData.segment),
								newSegmentData);
				this.updateSegmentIndices_(geometry, index, segmentData.depth,
						-1);

				if (this.vertexFeature_) {
					this.overlay_.getSource()
							.removeFeature(this.vertexFeature_);
					this.vertexFeature_ = null;
				}
			}
		}
	}
	return true;
};
