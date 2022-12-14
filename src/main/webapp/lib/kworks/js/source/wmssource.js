goog.provide('kworks.source.wms.ServerType');

/**
 * Available server types: `'carmentaserver'`, `'geoserver'`, `'mapserver'`,
 *     `'qgis'`. These are servers that have vendor parameters beyond the WMS
 *     specification that OpenLayers can make use of.
 * @enum {string}
 * @api
 */
kworks.source.wms.ServerType = {
  CARMENTA_SERVER: 'carmentaserver',
  GEOSERVER: 'geoserver',
  MAPSERVER: 'mapserver',
  QGIS: 'qgis',
  KMAPS : 'kmaps',
  ARCGIS : 'arcgis'
};
