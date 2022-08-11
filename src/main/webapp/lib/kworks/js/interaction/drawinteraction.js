goog.provide('kworks.interaction.Draw');
goog.provide('kworks.interaction.DrawEvent');

goog.require('ol.interaction.Draw');

/**
 * @enum {string}
 */
kworks.interaction.DrawEventType = {
  /**
   * Triggered upon feature draw start
   * @event ol.interaction.DrawEvent#drawstart
   * @api stable
   */
  DRAWSTART: 'drawstart',
  /**
   * Triggered upon feature draw add
   * @event ol.interaction.DrawEvent#drawend
   * @api stable
   */
  DRAWADD : 'drawadd',
  /**
   * Triggered upon feature draw end
   * @event ol.interaction.DrawEvent#drawend
   * @api stable
   */
  DRAWEND: 'drawend'
};

/**
 * @classdesc
 * Interaction for drawing feature geometries.
 *
 * @constructor
 * @extends {ol.interaction.Pointer}
 * @fires ol.interaction.DrawEvent
 * @param {olx.interaction.DrawOptions} options Options.
 * @api stable
 */
kworks.interaction.Draw = function(options) {
	  goog.base(this, options);
};
goog.inherits(kworks.interaction.Draw, ol.interaction.Draw);

/**
 * Add a new coordinate to the drawing.
 * @param {ol.MapBrowserEvent} event Event.
 * @private
 */
kworks.interaction.Draw.prototype.addToDrawing_ = function(event) {
  var coordinate = event.coordinate;
  var geometry = this.sketchFeature_.getGeometry();
  goog.asserts.assertInstanceof(geometry, ol.geom.SimpleGeometry,
      'geometry must be an ol.geom.SimpleGeometry');
  var done = null;
  var coordinates;
  if (this.mode_ === ol.interaction.DrawMode.LINE_STRING) {
    this.finishCoordinate_ = coordinate.slice();
    coordinates = this.sketchCoords_;
    coordinates.push(coordinate.slice());
    done = coordinates.length > this.maxPoints_;
    this.geometryFunction_(coordinates, geometry);
  } else if (this.mode_ === ol.interaction.DrawMode.POLYGON) {
    coordinates = this.sketchCoords_[0];
    coordinates.push(coordinate.slice());
    done = coordinates.length > this.maxPoints_;
    if (done) {
      this.finishCoordinate_ = coordinates[0];
    }
    this.geometryFunction_(this.sketchCoords_, geometry);
  }
  this.updateSketchFeatures_();
  if (done) {
    this.finishDrawing();
  }
  
  // Point add event
  this.dispatchEvent(new ol.interaction.DrawEvent(kworks.interaction.DrawEventType.DRAWADD, this.sketchFeature_));
};
