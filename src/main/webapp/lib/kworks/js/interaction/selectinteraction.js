goog.provide('kworks.interaction.Select');

goog.require('ol.interaction.Select');

kworks.interaction.Select = function(options) {
	goog.base(this, options);
};
goog.inherits(kworks.interaction.Select, ol.interaction.Select);

kworks.interaction.Select.prototype.removeOverlayFeatures = function() {
	this.featureOverlay_.getSource().clear();
};