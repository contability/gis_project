goog.provide('kworks.control');

goog.require('ol.Collection');
goog.require('ol.control.Zoom');

kworks.control.defaults = function(opt_options) {
  var controls = new ol.Collection();
  controls.push(new kworks.control.ScaleLine({ units : 'metric' }));
  return controls;
};
