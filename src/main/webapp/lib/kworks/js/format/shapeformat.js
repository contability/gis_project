goog.provide('kworks.format.Shape');

kworks.format.Shape = function() {
	
	goog.base(this);
	
	this.shapeTypes = {
        "0": "Null Shape",
        "1": "Point", // standard shapes
        "3": "PolyLine",
        "5": "Polygon",
        "8": "MultiPoint",
        "11": "PointZ", // 3d shapes
        "13": "PolyLineZ",
        "15": "PolygonZ",
        "18": "MultiPointZ",
        "21": "PointM", // user-defined measurement shapes
        "23": "PolyLineM",
        "25": "PolygonM",
        "28": "MultiPointM",
        "31": "MultiPatch"
    };
	
	this.reader = new FileReader();
};
goog.inherits(kworks.format.Shape, ol.Object);

kworks.format.Shape.prototype.readFeature = function(file, callback) {
	var that = this;
	that.reader.onload = function(e) {
		that.readFeature_(e.target.result, callback);
	};
	if(that.reader.readAsBinaryString) {
		that.reader.readAsBinaryString(file);
	}
	else {
		that.reader.readAsArrayBuffer(file);
	}
};

kworks.format.Shape.prototype.readFeature_ = function(data, callback) {
	var header = null;
	var records = null;
	if(data instanceof ArrayBuffer) {
		var dataView = new DataView(data);
		header = this.readFileHeaderFromDataView_(dataView);
		records = this.readRecordsFromDataView_(dataView);
	}
	else {
		var stream = new Gordon.Stream(data);
		header = this.readFileHeaderFromStream_(stream);
		records = this.readRecordsFromStream_(stream);
	}
	
	this.formatIntoGeoJson(records, header.bounds);
	if(callback) callback({
		header : this.header,
		records : this.records,
		geojson : this.geojson
	});
};

kworks.format.Shape.prototype.readFileHeaderFromDataView_ = function(dataView) {
	var header = {};
	this.offset = 0;
	
	// The main file header is fixed at 100 bytes in length
	if(dataView.byteLength < 100) throw "Invalid Header Length";
	
	// File code (always hex value 0x0000270a)
	header.fileCode = dataView.getInt32(0);
	if(header.fileCode != 9994) throw "Invalid File Code";
	
	// Unused; five uint32
	header.fileLength = dataView.getInt32(24);
	
	header.version = dataView.getInt32(28, true);
	
	header.shapeType = this.shapeTypes[dataView.getInt32(32, true)];
	
	this.offset = 36;
	header.bounds = this.readBoundsFromDataView_(dataView);

    header.rangeZ = {
        min: dataView.getFloat64(68, true),
        max: dataView.getFloat64(76, true)
    };

    header.rangeM = {
        min: dataView.getFloat64(84, true),
        max: dataView.getFloat64(92, true)
    };
	
	return header;
};


kworks.format.Shape.prototype.readFileHeaderFromStream_ = function(stream) {
	var header = {};
	
    if(stream < 100) throw "Invalid Header Length";
	
    header.fileCode = stream.readSI32(true);
    if(header.fileCode != parseInt(0x0000270a)) throw "Invalid File Code";
    
    // unused skip
    stream.seek(20);

    header.fileLength = stream.readSI32(true) * 2;

    header.version = stream.readSI32();

    header.shapeType = this.shapeTypes[stream.readSI32()];

    header.bounds = this.readBoundsFromStream_(stream);

    header.rangeZ = {
        min: stream.readDouble(),
        max: stream.readDouble()
    };

    header.rangeM = {
        min: stream.readDouble(),
        max: stream.readDouble()
    };
	
    return header;
};

kworks.format.Shape.prototype.readBoundsFromDataView_ = function(dataView) {
	var bounds = {
		left: dataView.getFloat64(this.offset, true),
        bottom: dataView.getFloat64(this.offset+8, true),
        right: dataView.getFloat64(this.offset+16, true),
        top: dataView.getFloat64(this.offset+24, true)
	};
	this.offset += 32;
	
	return bounds;
};

kworks.format.Shape.prototype.readBoundsFromStream_ = function(stream) {
	return {
        left: stream.readDouble(),
        bottom: stream.readDouble(),
        right: stream.readDouble(),
        top: stream.readDouble()
    };
};

kworks.format.Shape.prototype.readRecordsFromDataView_ = function(dataView) {
	 var records = [];
	 this.offset = 100;
	 
	 do {
		//no more records
		if(this.offset >= dataView.byteLength) break; 
		
		var record = {};
		
        // Record number (1-based)
        record.id = dataView.getInt32(this.offset);
        this.offset += 4;

        // Record length (in 16-bit words)
        record.length = dataView.getInt32(this.offset) * 2;
        this.offset += 4;

        record.shapeType = this.shapeTypes[dataView.getInt32(this.offset, true)];
        this.offset += 4;
        
        // Read specific shape
        $.extend(record, this["read" + record.shapeType + "FromDataView_"](dataView));
        
        records.push(record);
	 } while(true);
	 
	 return records;
};

kworks.format.Shape.prototype.readRecordsFromStream_ = function(stream) {
	 var records = [];
	 do {
		 var record = {};

         // Record number (1-based)
         record.id = stream.readSI32(true);

         if(record.id == 0) break; //no more records

         // Record length (in 16-bit words)
         record.length = stream.readSI32(true) * 2;

         record.shapeType = this.shapeTypes[stream.readSI32()];

         // Read specific shape
         $.extend(record, this["read" + record.shapeType + "FromStream_"](stream));

         records.push(record);
	 } while(true);

	 return records;
};

kworks.format.Shape.prototype.readPointFromDataView_ = function(dataView) {
	var record = {};
	record.x = dataView.getFloat64(this.offset, true);
	record.y = dataView.getFloat64(this.offset+8, true);
	this.offset += 16;
	return record;
};

kworks.format.Shape.prototype.readPointFromStream_ = function(stream) {
    return {
    	x : stream.readDouble(),
    	y : stream.readDouble()
    };
};

kworks.format.Shape.prototype.readMultiPointFromDataView_ = function(dataView) {
	var record = {};
	record.bounds = this.readBoundsFromDataView_(dataView);
	record.points = this.readPointsFromDataView_(dataView);
    return record;
};

kworks.format.Shape.prototype.readMultiPointFromStream_ = function(stream) {
    var record = {};
    record.bounds = this.readBoundsFromStream_(stream);
    record.points = this.readPointsFromStream_(stream);
    return record;
};

kworks.format.Shape.prototype.readPointsFromDataView_ = function(dataView, numPoints) {
    var points = [];
    if(!numPoints) {
    	numPoints = dataView.getInt32(this.offset, true);
    	this.offset += 4;
    }
    while(numPoints--) {
        points.push({
            x: dataView.getFloat64(this.offset, true),
            y: dataView.getFloat64(this.offset+8, true),
        });
        this.offset += 16;
    }
    return points;
};

kworks.format.Shape.prototype.readPointsFromStream_ = function(stream, numPoints) {
    var points = [];
    if(!numPoints) {
    	numPoints = stream.readSI32();
    }
    while(numPoints--) {
        points.push({
            x: stream.readDouble(),
            y: stream.readDouble()
        });
    }
    return points;
};

kworks.format.Shape.prototype.readPolyLineFromDataView_ = function(dataView) {
    return this.readPolygonFromDataView_(dataView);
};

kworks.format.Shape.prototype.readPolyLineFromStream_ = function(stream) {
    return this.readPolygonFromStream_(stream);
};

kworks.format.Shape.prototype.readPolygonFromDataView_ = function(dataView) {
    var record = {};
    record.bounds = this.readBoundsFromDataView_(dataView);
    $.extend(record, this.readPartsFromDataView_(dataView));
    record.points = this.readPointsFromDataView_(dataView, record.numPoints);
    return record;
};

kworks.format.Shape.prototype.readPolygonFromStream_ = function(stream) {
    var record = {};
    record.bounds = this.readBoundsFromStream_(stream);
    $.extend(record, this.readPartsFromStream_(stream));
    record.points = this.readPointsFromStream_(stream, record.numPoints);
    return record;
};

kworks.format.Shape.prototype.readPartsFromDataView_ = function(dataView) {
    var parts = [];

    var numParts = dataView.getInt32(this.offset, true);
    this.offset += 4;

    var numPoints = dataView.getInt32(this.offset, true);
    this.offset += 4;

    while(numParts--) {
    	parts.push(dataView.getInt32(this.offset, true));
    	this.offset += 4;
    }
    
    return {
    	numPoints : numPoints,
    	parts : parts
    };
};

kworks.format.Shape.prototype.readPartsFromStream_ = function(stream) {
    var parts = [];
    var numParts = stream.readSI32();
    var numPoints = stream.readSI32();
    
    while(numParts--) {
    	parts.push(stream.readSI32());
    }
    
    return {
    	numPoints : numPoints,
    	parts : parts
    };
};

kworks.format.Shape.prototype.formatIntoGeoJson = function(records, bounds){
    var features = [];
    var feature, geometry, points, fbounds, gcoords, parts, geojson = {};

    geojson.type = "FeatureCollection";
    geojson.bbox = [
        bounds.left,
        bounds.bottom,
        bounds.right,
        bounds.top
    ];
    geojson.features = features;

    for (var r = 0, record; record = records[r]; r++) {
        feature = {}, fbounds = record.bounds, points = record.points, parts = record.parts;
        feature.type = "Feature";
        if (record.shapeType !== 'Point') {
            feature.bbox = [
                fbounds.left,
                fbounds.bottom,
                fbounds.right,
                fbounds.top
            ];
        }
        geometry = feature.geometry = {};

        switch (record.shapeType) {
            case "Point":
                geometry.type = "Point";
                geometry.coordinates = [record.x, record.y];
                break;
            case "MultiPoint":
            case "PolyLine":
                geometry.type = (record.shapeType == "PolyLine" ? "LineString" : "MultiPoint");
                gcoords = geometry.coordinates = [];

                for (var p = 0; p < points.length; p++){
                    var point = points[p];
                    gcoords.push([point.x,point.y]);
                }
                break;
            case "Polygon":
                geometry.type = "Polygon";
                gcoords = geometry.coordinates = [];

                for (var pt = 0; pt < parts.length; pt++){
                    var partIndex = parts[pt];
                    var part = [];
                    var point = null;

                    // partIndex 0 == main poly, partIndex > 0 == holes in poly
                    for (var p = partIndex; p < (parts[pt+1] || points.length); p++){
                        point = points[p];
                        part.push([point.x,point.y]);
                    }
                    gcoords.push(part);
                }
                break;
            default:
        }
        features.push(feature);
    }
    this.geojson = geojson;
};