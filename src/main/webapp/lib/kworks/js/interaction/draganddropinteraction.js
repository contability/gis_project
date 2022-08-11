goog.provide('kworks.interaction.DragAndDrop');

goog.require('ol.interaction.DragAndDrop');

kworks.interaction.DragAndDrop = function(opt_options) {
	goog.base(this, opt_options);
	
	this.projection = goog.isDef(opt_options.projection) ? opt_options.projection : ol.proj.get("EPSG:4326");
	this.sourceProjection = goog.isDef(opt_options.sourceProjection) ? opt_options.sourceProjection : ol.proj.get("EPSG:4326");
};
goog.inherits(kworks.interaction.DragAndDrop, ol.interaction.DragAndDrop);

kworks.interaction.DragAndDrop.prototype.handleDrop_ = function(event) {
	var files = event.getBrowserEvent().dataTransfer.files;
	var i, ii, file;
	for (i = 0, ii = files.length; i < ii; ++i) {
		file = files[i];
		
		var that = this;
		var extension = file.name.substring(file.name.lastIndexOf(".")+1).toLowerCase();
		if(extension == "shp") {
			var format = new kworks.format.Shape();
			format.readFeature(file, function(data) {
				that.handleResult_(file, data.geojson);
			});
		}
		else {
			var reader = goog.fs.FileReader.readAsText(file, '');
			reader.addCallback(goog.partial(this.handleResult_, file), this);
		}
	}
};

kworks.interaction.DragAndDrop.prototype.setProjection = function(projection) {
	this.projection = projection;
};

kworks.interaction.DragAndDrop.prototype.setSourceProjection = function(projection) {
	this.sourceProjection = projection;
};

kworks.interaction.DragAndDrop.prototype.handleResult_ = function(file, result) {
	var map = this.getMap();
	goog.asserts.assert(!goog.isNull(map), 'map should not be null');
	var projection = this.projection_;
	if (goog.isNull(projection)) {
		var view = map.getView();
		goog.asserts.assert(!goog.isNull(view), 'view should not be null');
		projection = view.getProjection();
		goog.asserts.assert(goog.isDef(projection), 'projection should be defined');
	}
	var formatConstructors = this.formatConstructors_;
	
	var features = [];
	var i, ii;
	for (i = 0, ii = formatConstructors.length; i < ii; ++i) {
		var formatConstructor = formatConstructors[i];
		var format = new formatConstructor();
		var readFeatures = this.tryReadFeatures_(format, result);
		if (!goog.isNull(readFeatures)) {
			var j, jj;
			for (j = 0, jj = readFeatures.length; j < jj; ++j) {
				var feature = readFeatures[j];
				/*if(projection != this.sourceProjection) {
					feature.getGeometry().transform(sourceProjection, projection);
				}*/
				features.push(feature);
			}
		}
	}
	this.dispatchEvent(new ol.interaction.DragAndDropEvent(ol.interaction.DragAndDropEventType.ADD_FEATURES, this, file, features, projection));
};