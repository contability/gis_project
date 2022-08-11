/**
 * 좌표계
 * @type {Object}
 */
var coordinateObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	title : "좌표계",
	
	/**
	 * 좌표계 데이터
	 * @type {Array.<Object>}
	 */
	data : null,
	
	/**
	 * 데이터 로딩
	 * @returns {Promise} Promise 객체
	 */
	load : function() {
		var that = this;
		var promise = $.get(
			CONTEXT_PATH + "/rest/cntm/listAll.do"
		);
		promise.done(function(response, status) {
			if (response && response.rows) {
				that.data = response.rows;
				that.setCrsList();
			} else {
				$.messager.alert(that.title, "좌표계 정보를 불러오는 데 실패했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.title, "좌표계 정보를 불러오는 데 실패했습니다.");
		});
		return promise;
	},
	
	/**
	 * 좌표계 목록 설정
	 */
	setCrsList : function() {
		var that = this;
		for(var i=0, len=that.data.length; i < len; i++) {
			var cntm = that.data[i];
			
			// 기본적으로 등록되어 있는 좌표계가 아닌 좌표계만 등록
			if(!ol.proj.get(cntm.cntmId)) {
				that.setCrs(cntm);
			}
		}
	},
	
	/**
	 * 좌표계 설정
	 * @param {Object} 좌표계 정보
	 */
	setCrs : function(cntm) {
		var crsId = cntm.cntmId;
		var srid = crsId.split(":")[1];
		var proj4Str = cntm.cntmCn;
		
		proj4.defs(crsId, proj4Str);
		
		var projection = new ol.proj.Projection({
			code : crsId,
			extent : [90112, 1192896, 1990673, 2761664],
			units : "m",
			axisOrientation : "enu"
		});
		ol.proj.addProjection(projection);
		
		var projection = new ol.proj.Projection({
			code : "http://www.opengis.net/gml/srs/epsg.xml#"+srid,
			extent : [90112, 1192896, 1990673, 2761664],
			units : "m",
			axisOrientation : "enu"
		});
		ol.proj.addProjection(projection);
		
		var projection = new ol.proj.Projection({
			code : "urn:ogc:def:crs:"+crsId,
			extent : [90112, 1192896, 1990673, 2761664],
			units : "m",
			axisOrientation : "enu"
		});
		ol.proj.addProjection(projection);
	},
	
	/**
	 * 데이터 반환
	 * @return {Array} 좌표계 목록
	 */
	getData : function() {
		var that = this;
		return that.data;
	}
		
};