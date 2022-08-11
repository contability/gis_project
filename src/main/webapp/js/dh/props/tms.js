/**
 * 동해 - TMS (항공영상) 프로퍼티
 * @namespace
 */
var TMS = {
	
	/**
	 * View 객체 반환
	 * @param {ol.View} View 객체
	 */
	getView : function() {
		return new ol.View({
			projection : new ol.proj.get(MAP.PROJECTION),
			maxResolution : 256,
			center : [205232, 546244],
			zoom : 5,
			zoomFactor : 1.5
			//maxZoom : 17,
			//minZoom : 0
		});
	}
		
};