goog.provide('kworks.interaction.Translate');

goog.require('ol.interaction.Translate');

kworks.interaction.Translate = function(options) {
	goog.base(this, options);
};
goog.inherits(kworks.interaction.Translate, ol.interaction.Translate);

kworks.interaction.Translate.prototype.setFeatures = function(features) {
	this.features_ = features;
};