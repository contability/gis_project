/**
 * 시연 - TMS (항공영상) 프로퍼티
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
			maxResolution : 65.28,
			center : [200352, 537120],
			zoom : 0,
			maxZoom : 11,
			minZoom : 0
		});
	}
		
};