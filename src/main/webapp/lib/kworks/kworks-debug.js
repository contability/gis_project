(function() {
	var jsFiles = [ 
	    "map.js", 
	    "maps.js",
	    "magnifyingmap.js",
	    "xraymap.js",
	    "indexmap.js",
	    "control/controldefaults.js",
	    "control/scalelinecontrol.js",
	    "format/stream.js",
	    "format/wmsformat.js",
	    "format/wfsformat.js",
	    "format/shapeformat.js",
	    "format/gml/gml3format.js",
	    "interaction/mousepositioninteraction.js",
	    "interaction/draganddropinteraction.js",
	    "interaction/dragzoomininteraction.js",
	    "interaction/dragzoomoutinteraction.js",
	    "interaction/drawinteraction.js",
	    "interaction/interactiondefaults.js",
	    "interaction/measureinteraction.js",
	    "interaction/roadviewdauminteraction.js",
	    "interaction/dragfeatureinteraction.js",
	    "interaction/translateinteraction.js",
	    "interaction/selectinteraction.js",
	    "interaction/upwidinteraction.js",
	    "interaction/downwidinteraction.js",
	    "service/wmsservice.js",
	    "service/wfsservice.js",
	    "source/wmssource.js",
	    "source/imagewmssource.js",
	    "source/daumsource.js",
	    "source/naversource.js",
	    "source/tmssource.js",
	    "source/vworldsource.js",
	    "view/daumview.js",
	    "view/naverview.js",
	    "view/vworldview.js"
	];
	var scriptTags = new Array(jsFiles.length);
	var host = CONTEXT_PATH + "/lib/kworks/js/";
	for (var i = 0, len = jsFiles.length; i < len; i++) {
		scriptTags[i] = "<script src='" + host + jsFiles[i] + "'></script>";
	}
	if (scriptTags.length > 0) {
		document.write(scriptTags.join(""));
	}
})();