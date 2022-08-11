goog.provide('kworks.interaction.Measure');
goog.provide('kworks.interaction.MeasureEventType');
goog.provide('kworks.interaction.MeasureEvent');

goog.require('ol.interaction.Draw');
goog.require('goog.events');
goog.require('goog.events.Event');

kworks.interaction.MeasureEventType = {
	MEASUREEND : 'measureend'
};

kworks.interaction.MeasureEvent = function(type, value) {
	goog.base(this, type);
	this.value = value;	
};
goog.inherits(kworks.interaction.MeasureEvent, goog.events.Event);

// 측정 (좌표, 거리, 면적, 반경(면적))
kworks.interaction.Measure = function(opt_options) {
	
	// 측정 타입
	this.type = goog.isDef(opt_options.type) ? opt_options.type : "coordinate";
	
	// 도형 타입
	var geomType = "";
	if(opt_options.type == "coordinate") {
		geomType = "Point";
	}
	else if(opt_options.type == "distance") {
		geomType = "LineString";
	}
	else if(opt_options.type == "area") {
		geomType = "Polygon";
	}
	else if(opt_options.type == "radius") {
		geomType = "Circle";
	}
	else {
		geomType = "Point";
	}
	
	// 도형
	this.feature = null;
	
	// 오버레이
	this.overlay = null;
	
	// 레이어 옵션
	var options = {
		source : opt_options.source,
        type : geomType,
        style : new ol.style.Style({
            fill : new ol.style.Fill({
                color : 'rgba(255, 255, 255, 0.2)'
            }),
            stroke : new ol.style.Stroke({
                color : 'rgba(0, 0, 0, 0.5)',
                lineDash : [10, 10],
                width : 2
            }),
            image : new ol.style.Circle({
                radius : 5,
                stroke : new ol.style.Stroke({
                    color : 'rgba(0, 0, 0, 0.7)'
                }),
                fill : new ol.style.Fill({
                    color : 'rgba(255, 255, 255, 0.2)'
                })
            })
        })
	};
	goog.base(this, options);
	
	// 이벤트 등록
	this.on('drawstart', this.drawStart);
	this.on('drawend', this.drawEnd);
	
	/// 측정 중 취소하는 경우 취소 이벤트 호출
	this.on('propertychange', function(evt) {
		if(!this.get("active")) {
			this.cancel();
		}
	});
	
};
goog.inherits(kworks.interaction.Measure, ol.interaction.Draw);

kworks.interaction.Measure.prototype.cancel = function() {
	if(this.feature) {
		this.feature = null;
	}
	if(this.overlay) {
		this.getMap().removeOverlay(this.overlay);
		this.overlay = null;
	}
	this.getMap().un('pointermove', this.drawing, this);
};

kworks.interaction.Measure.prototype.drawStart = function(evt) {
	var measureLayer = this.getMap().getLayer("measureVector");
	if(goog.isDef(measureLayer)) {
		this.getMap().removeLayer(measureLayer);
		this.getMap().addLayer(measureLayer);
	}
	
	this.feature = evt.feature;
	this.overlay = new ol.Overlay({
		element : goog.dom.createDom(goog.dom.TagName.DIV, {'class' : 'kworks-tooltip kworks-tooltip-measure'}),
		offset : [0, -15],
		positioning : 'bottom-center'
	});
	this.overlay.set("name", "measure");
	this.getMap().addOverlay(this.overlay);
	this.getMap().on('pointermove', this.drawing, this);
};

kworks.interaction.Measure.prototype.drawing = function(evt) {
	if(evt.dragging) {
		return;
	}
	var coordinate = evt.coordinate;
	if(this.feature) {
		var output = null;
		var geom = this.feature.getGeometry();
		if(geom instanceof ol.geom.Polygon) {
			output = this.formatArea(geom.getArea());
		}
		else if(geom instanceof ol.geom.LineString) {
			output = this.formatLength(geom.getLength());
		}
		else if(geom instanceof ol.geom.Circle) {
			output = this.formatLength(geom.getRadius());
		}
		$(this.overlay.getElement()).html(output);
		this.overlay.setPosition(coordinate);
	}
};

kworks.interaction.Measure.prototype.drawEnd = function(evt) {
	var value = null;
	if(this.type == "coordinate") {
		var coordinate = evt.feature.getGeometry().getLastCoordinate();
		var val = coordinate[0].toFixed(4) + ",  " + coordinate[1].toFixed(4);
		if(this.map_.getView().getProjection() != ol.proj.get("EPSG:4326")) {
			var lonlat = evt.feature.getGeometry().clone().transform(this.map_.getView().getProjection(), ol.proj.get("EPSG:4326")).getCoordinates();
			val += "<br /> " + lonlat[0].toFixed(8) + ", " + lonlat[1].toFixed(8);
			val += "<br />" + ol.coordinate.degreesToStringHDMS_(lonlat[0], 'EW') + '&nbsp&nbsp' + ol.coordinate.degreesToStringHDMS_(lonlat[1], 'NS');
		}
		$(this.overlay.getElement()).html(val);
		this.overlay.setPosition(coordinate);
		this.overlay.setOffset([0, -13]);
		value = coordinate[0].toFixed(2) + ", " + coordinate[1].toFixed(2);
	}
	else if(this.type == "distance") {
		value = evt.feature.getGeometry().getLength();
	}
	else if(this.type == "area") {
		value = evt.feature.getGeometry().getArea();
	}
	else if(this.type == "radius") {
		var geometry = this.feature.getGeometry();
		var radius = geometry.getRadius();
		var area = radius * radius * Math.PI;
		
		var overlay = new ol.Overlay({
			element : goog.dom.createDom(goog.dom.TagName.DIV, "kworks-tooltip kworks-tooltip-static"),
			offset : [0, 0],
			positioning : 'bottom-center'
		});
		overlay.set("name", "measure");
		overlay.setPosition(geometry.getCenter());
		this.getMap().addOverlay(overlay);
		$(overlay.getElement()).html(this.formatArea(area));
		value = area;
	}
	else {
		this.overlay.setOffset([0, -7]);
	}
	$(this.overlay.getElement()).attr("class", "kworks-tooltip kworks-tooltip-static");
	
	this.feature = null;
	this.overlay = null;
	this.getMap().un('pointermove', this.drawing, this);

	this.dispatchEvent(new kworks.interaction.MeasureEvent(kworks.interaction.MeasureEventType.MEASUREEND, value));
};

kworks.interaction.Measure.prototype.formatLength = function(length) {
	var output;
    if (length > 1000) {
    	output = (Math.round(length / 1000 * 100) / 100) + ' ' + 'km';
    } else {
    	output = (Math.round(length * 100) / 100) + ' ' + 'm';
    }
    return output;
};

kworks.interaction.Measure.prototype.formatArea = function(area) {
	var output;
    if (area > 1000000) {
        output = (Math.round(area / 1000000 * 100) / 100) + ' ' + 'km<sup>2</sup>';
    } else {
        output = (Math.round(area * 100) / 100) + ' ' + 'm<sup>2</sup>';
    }
    return output;
};

kworks.interaction.Measure.createMeasureLayer = function(map, source) {
	var measureVector = new ol.layer.Vector({
		id : "measureVector",
		source : source,
		style : new ol.style.Style({
            fill : new ol.style.Fill({
                color : 'rgba(255, 255, 255, 0.2)'
            }),
            stroke : new ol.style.Stroke({
                color : '#ffcc33',
                width : 2
            }),
            image : new ol.style.Circle({
                radius : 7,
                fill : new ol.style.Fill({
                    color : '#ffcc33'
                })
            })
        })
	});
	map.addLayer(measureVector);
};


// 측정기능 초기화 (도형, 오버레이 삭제)
kworks.interaction.Measure.clear = function(map) {
	var measureLayer = map.getLayer("measureVector");
	if(goog.isDef(measureLayer)) {
		measureLayer.getSource().clear();
	}
	var overlays = map.getOverlays();
	for(var i=overlays.get("length")-1; i >= 0; i--) {
		var overlay = overlays.item(i);
		if(overlay.get("name") == "measure") {
			map.removeOverlay(overlay);
		}
	}
	
};

