goog.provide('kworks.proj');

proj4.defs('EPSG:4326', '+title=WGS 84 (long/lat) +proj=longlat +ellps=WGS84 +datum=WGS84 +units=degrees');
proj4.defs("EPSG:3857", "+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +wktext  +no_defs");

proj4.defs("EPSG:5179", "+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=GRS80 +units=m +no_defs");
proj4.defs("EPSG:5181", "+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=500000 +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs");
proj4.defs("EPSG:5183", "+proj=tmerc +lat_0=38 +lon_0=129 +k=1 +x_0=200000 +y_0=500000 +ellps=GRS80 +units=m +no_defs");
proj4.defs("EPSG:5186", "+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=600000 +ellps=GRS80 +units=m +no_defs");
proj4.defs("EPSG:5187", "+proj=tmerc +lat_0=38 +lon_0=129 +k=1 +x_0=200000 +y_0=600000 +ellps=GRS80 +units=m +no_defs");


//EPSG:3857 (Google, Spherical Mercator)
var projection = new ol.proj.Projection({
	code : "EPSG:3857",
	units : "m",
	axisOrientation : "enu"
});
ol.proj.addProjection(projection);
var projection = new ol.proj.Projection({
	code : "http://www.opengis.net/gml/srs/epsg.xml#3857",
	units : "m",
	axisOrientation : "enu"
});
ol.proj.addProjection(projection);

// EPSG:5179 (UTM-K) [Naver]
var projection = new ol.proj.Projection({
	code : "EPSG:5179",
	extent : [90112, 1192896, 1990673, 2761664],
	units : "m",
	axisOrientation : "enu"
});
ol.proj.addProjection(projection);
var projection = new ol.proj.Projection({
	code : "http://www.opengis.net/gml/srs/epsg.xml#5179",
	extent : [90112, 1192896, 1990673, 2761664],
	units : "m",
	axisOrientation : "enu"
});
ol.proj.addProjection(projection);
var projection = new ol.proj.Projection({
	code : "urn:ogc:def:crs:EPSG:5179",
	extent : [90112, 1192896, 1990673, 2761664],
	units : "m",
	axisOrientation : "enu"
});
ol.proj.addProjection(projection);


// EPSG:5181 (GRS80 중부원점 20만, 50만) [Daum]
var projection = new ol.proj.Projection({
	code : "EPSG:5181",
	canWrapX : true,
	extent : [-30000, -60000, 494288, 988576],
	units : "m",
	axisOrientation : "enu"
});
ol.proj.addProjection(projection);
var projection = new ol.proj.Projection({
	code : "http://www.opengis.net/gml/srs/epsg.xml#5181",
	extent : [-30000, -60000, 494288, 988576],
	units : "m",
	axisOrientation : "enu"
});
ol.proj.addProjection(projection);
var projection = new ol.proj.Projection({
	code : "urn:ogc:def:crs:EPSG:5181",
	extent : [-30000, -60000, 494288, 988576],
	units : "m",
	axisOrientation : "enu"
});
ol.proj.addProjection(projection);

//EPSG:5183 (GRS80 동부원점 20만, 50만)
var projection = new ol.proj.Projection({
	code : "EPSG:5183",
	units : "m",
	axisOrientation : "enu"
});
ol.proj.addProjection(projection);
var projection = new ol.proj.Projection({
	code : "http://www.opengis.net/gml/srs/epsg.xml#5183",
	units : "m",
	axisOrientation : "enu"
});
ol.proj.addProjection(projection);
var projection = new ol.proj.Projection({
	code : "urn:ogc:def:crs:EPSG:5183",
	units : "m",
	axisOrientation : "enu"
});
ol.proj.addProjection(projection);	

// EPSG:5186 (GRS80 중부원점 20만, 60만)
var projection = new ol.proj.Projection({
	code : "EPSG:5186",
	units : "m",
	axisOrientation : "enu"
});
ol.proj.addProjection(projection);
var projection = new ol.proj.Projection({
	code : "http://www.opengis.net/gml/srs/epsg.xml#5186",
	units : "m",
	axisOrientation : "enu"
});
ol.proj.addProjection(projection);
var projection = new ol.proj.Projection({
	code : "urn:ogc:def:crs:EPSG:5186",
	units : "m",
	axisOrientation : "enu"
});
ol.proj.addProjection(projection);	

//EPSG:5187 (GRS80 동부원점 20만, 60만)
var projection = new ol.proj.Projection({
	code : "EPSG:5187",
	units : "m",
	axisOrientation : "enu"
});
ol.proj.addProjection(projection);
var projection = new ol.proj.Projection({
	code : "http://www.opengis.net/gml/srs/epsg.xml#5187",
	units : "m",
	axisOrientation : "enu"
});
ol.proj.addProjection(projection);
var projection = new ol.proj.Projection({
	code : "urn:ogc:def:crs:EPSG:5187",
	units : "m",
	axisOrientation : "enu"
});
ol.proj.addProjection(projection);	
