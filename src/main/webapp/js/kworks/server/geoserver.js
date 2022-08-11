var serverObj = {
	
	getWMSParams : function() {
		var params = {
			'VERSION' : "1.1.0",
			'FORMAT' : "image/png"
		};
		return params;
	},
	
	getBlankLayers : function() {
		return caseUtils.getText("BLANK");
	}
		
};