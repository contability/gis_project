goog.provide('kworks.format.WMS');

kworks.format.WMS = function() {
	goog.base(this);
};
goog.inherits(kworks.format.WMS, ol.Object);

kworks.format.WMS.prototype.readGetStyles = function(response) {
    var format = new OpenLayers.Format.SLD({multipleSymbolizers: true});
    var obj = format.read(response);
    return obj;
};

kworks.format.WMS.prototype.writeGetStyles = function(response) {
    var format = new OpenLayers.Format.SLD({multipleSymbolizers: true});
    var xml = format.write(response);
    return xml;
};