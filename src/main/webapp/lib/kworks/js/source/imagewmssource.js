goog.provide('kworks.source.ImageWMS');

goog.require('ol.source.ImageWMS');

kworks.source.ImageWMS = function(opt_options) {
	goog.base(this, opt_options);
};
goog.inherits(kworks.source.ImageWMS, ol.source.ImageWMS);

kworks.source.ImageWMS.prototype.getRequestUrl_ =
    function(extent, size, pixelRatio, projection, params) {

  goog.asserts.assert(goog.isDef(this.url_), 'url is defined');

  params[this.v13_ ? 'CRS' : 'SRS'] = projection.getCode();

  if (!('STYLES' in this.params_)) {
    /* jshint -W053 */
    params['STYLES'] = new String('');
    /* jshint +W053 */
  }

  if (pixelRatio != 1) {
    switch (this.serverType_) {
      case kworks.source.wms.ServerType.GEOSERVER:
        var dpi = (90 * pixelRatio + 0.5) | 0;
        if (goog.isDef(params['FORMAT_OPTIONS'])) {
          params['FORMAT_OPTIONS'] += ';dpi:' + dpi;
        } else {
          params['FORMAT_OPTIONS'] = 'dpi:' + dpi;
        }
        break;
      case kworks.source.wms.ServerType.MAPSERVER:
        params['MAP_RESOLUTION'] = 90 * pixelRatio;
        break;
      case kworks.source.wms.ServerType.CARMENTA_SERVER:
      case kworks.source.wms.ServerType.QGIS:
        params['DPI'] = 90 * pixelRatio;
        break;
      case kworks.source.wms.ServerType.KMAPS:
    	  break;
      case kworks.source.wms.ServerType.ARCGIS:
    	  break;
      default:
        goog.asserts.fail('unknown serverType configured');
        break;
    }
  }

  params['WIDTH'] = size[0];
  params['HEIGHT'] = size[1];

  var axisOrientation = projection.getAxisOrientation();
  var bbox;
  if (this.v13_ && axisOrientation.substr(0, 2) == 'ne') {
    bbox = [extent[1], extent[0], extent[3], extent[2]];
  } else {
    bbox = extent;
  }
  params['BBOX'] = bbox.join(',');

  return goog.uri.utils.appendParamsFromMap(this.url_, params);
};