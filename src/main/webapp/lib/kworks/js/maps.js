goog.provide('kworks.Maps');

kworks.Maps = function(options) {
	
	options = goog.isDef(options) ? options : {};
	this.maps = new ol.Collection();
	this.baseMap = null;
	this.isConstrain = goog.isDef(options.isConstrain) ? options.isConstrain : false;

	goog.base(this);
	
	this.onMoveEnd();
};
goog.inherits(kworks.Maps, ol.Object);

kworks.Maps.prototype.addMaps = function(maps) {
	this.maps = maps;
};

kworks.Maps.prototype.getMaps = function() {
	return this.maps;
};

kworks.Maps.prototype.clear = function() {
	this.maps.clear();
};

kworks.Maps.prototype.addMap = function(map) {
	this.maps.push(map);
};

kworks.Maps.prototype.removeMap = function(map) {
	this.maps.remove(map);
};

kworks.Maps.prototype.getMap = function(index) {
	if(this.maps.get("length") > index) {
		return this.maps.item(index);
	}
	else {
		return null;
	}
};

kworks.Maps.prototype.setBaseMap = function(map) {
	this.unMoveEnd();
	this.baseMap = map;
	this.onMoveEnd();
};

kworks.Maps.prototype.getBaseMap = function() {
	return this.baseMap;
};

kworks.Maps.prototype.onMoveEnd = function() {
	if(this.baseMap) {
		this.baseMap.on("moveend", this.moveEnd, this);	
	}	
};

kworks.Maps.prototype.unMoveEnd = function() {
	if(this.baseMap) {
		this.baseMap.un("moveend", this.moveEnd, this);	
	}
};

kworks.Maps.prototype.moveEnd = function() {
	var that = this;
	var map = this.baseMap;
	var maps = this.maps;
	for(var i=0, len=maps.get("length"); i < len; i++) {
		if(maps.item(i) != map) {
			var geom = new ol.geom.Point(map.getCenter()).transform(map.getView().getProjection(), maps.item(i).getView().getProjection());
			var resolution = map.getView().getResolution();
			maps.item(i).moveToCenter(geom.getCoordinates());
			
			if(that.isConstrain) {
				resolution = maps.item(i).getView().constrainResolution(resolution);
			}
			
			maps.item(i).moveToResolution(resolution);
		}
	}
};

kworks.Maps.prototype.activeInteractions = function(opt_names, opt_ids) {
	for(var i=0, len=this.maps.get("length"); i < len; i++) {
		this.maps.item(i).activeInteractions(opt_names, opt_ids);
	}
};
