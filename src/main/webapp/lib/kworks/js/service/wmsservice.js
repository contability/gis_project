goog.provide('kworks.service.WMS');

kworks.service.WMS.defaultParams = {
	service : 'WMS',
	version : "1.3.0"
};

kworks.service.WMS.setDefaultParams = function(params) {
	$.extend(kworks.service.WMS.defaultParams, params);
};

kworks.service.WMS.getCapabilities = function(callback){
	var p = $.extend({}, kworks.service.WMS.defaultParams);
	p.request = 'GetCapabilities';
	
	var data = kworks.service.WMS.gfnConvertObjToStr(p);
	
	$.ajax({
    	url : BaseUrl + '/proxy/wms.do?' + data,
    	type : "GET",
    	contentType : "text/xml",
    	success : function(response) {
    		var format = new ol.format.WMSCapabilities();
    		if(goog.isDef(callback)) callback(format.read(response));
    	},
    	error : function(response) {
    		var format = new ol.format.WMSCapabilities();
    		if(goog.isDef(callback)) callback(format.read(response));
    	}
    });
};

kworks.service.WMS.getStyles = function(params, callback) {
	var p = $.extend({}, kworks.service.WMS.defaultParams, params);
	p.request = 'GetStyles';
	p.version = '1.1.1';
	
	var data = kworks.service.WMS.gfnConvertObjToStr(p);
	
	$.ajax({
    	url : BaseUrl + '/proxy/wms.do?' + data,
    	type : "GET",
    	contentType : "text/xml",
    	dataType : "text",
    	success : function(response) {
    		var format = new kworks.format.WMS();
    		if(goog.isDef(callback)) callback(format.readGetStyles(response.replace(/\n/g, "").replace(/\r/g, "").replace(/> *</g, "><")));
    	},
    	error : function(response) {
    		var format = new kworks.format.WMS();
    		if(goog.isDef(callback)) callback(format.readGetStyles(response.replace(/\n/g, "").replace(/\r/g, "").replace(/> *</g, "><")));
    	}
    });
};

kworks.service.WMS.gfnConvertObjToStr = function(obj, ch) {
	var retVal = "";
	if(!ch) {
		ch = "&";
	}
	for(var i in obj) {
		retVal += ch;
		retVal += i;
		retVal += "=";
		retVal += obj[i];
	}
	return retVal.substr(1);
};