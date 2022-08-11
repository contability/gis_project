/**
 * 좌표
 * @type {Object}
 */
windowObj.coordinateObj = {
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "좌표 설정",
		
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "Coordinate",
	
	/**
	 * 열기
	 * @param options 옵션
	 */
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
			return;
		}
		
		var url = CONTEXT_PATH + "/window/coordinate/page.do";
		var windowOptions = {
			width : 500,
			height : 300,
			onClose : function() {
				that.close();
			}
		};
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
		});
		that.selector = "#" + id;
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// TM X 좌표
		$(".tm_x", that.selector).numberspinner({
			min : 0,
			max : 200000,
			prompt : 188795.77,
			precision : 2
		});
		// TM Y 좌표
		$(".tm_y", that.selector).numberspinner({
			min : 0,
			max : 600000,
			prompt : 567854.94,
			precision : 2
		});
		
		// LONLAT 경도
		$(".lonlat_lon", that.selector).numberspinner({
			min : 0,
			max : 360,
			prompt : 126.9877,
			precision : 4
		});
		// LONLAT 위도
		$(".lonlat_lat", that.selector).numberspinner({
			min : 0,
			max : 360,
			prompt : 37.4292,
			precision : 4
		});
		
		
		// DMS 경도 도
		$(".dms_lon_do", that.selector).numberspinner({
			min : 0,
			max : 360,
			prompt : 126
		});
		// DMS 경도 분
		$(".dms_lon_min", that.selector).numberspinner({
			min : 0,
			max : 360,
			prompt : 59
		});
		// DMS 경도 초
		$(".dms_lon_secnd", that.selector).numberspinner({
			min : 0,
			max : 360,
			prompt : 16
		});
		
		// DMS 위도 도
		$(".dms_lat_do", that.selector).numberspinner({
			min : 0,
			max : 360,
			prompt : 37
		});
		// DMS 위도 분
		$(".dms_lat_min", that.selector).numberspinner({
			min : 0,
			max : 360,
			prompt : 25
		});
		// DMS 위도 초
		$(".dms_lat_secnd", that.selector).numberspinner({
			min : 0,
			max : 360,
			prompt : 45
		});
	
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 지도 이동 TM
		$(".a_move_tm").click(function() {
			that.moveTm();
		});
		
		// 지도 이동 LONLAT
		$(".a_move_lonlat").click(function() {
			that.moveLonLat();
		});
		
		// 지도 이동 DMS
		$(".a_move_dms").click(function() {
			that.moveDms();
		});
	},
	
	/**
	 * 지도 이동 TM
	 */
	moveTm : function() {
		var that = this;
		var map = mapObj.getMap();
		
		var x = $(".tm_x", that.selector).numberspinner("getValue");
		var y = $(".tm_y", that.selector).numberspinner("getValue");
		
		if(x == '') {
			$.messager.alert(that.TITLE, "X 좌표를 입력하여 주십시오.");
			return;
		}
		else if(y == '') {
			$.messager.alert(that.TITLE, "Y 좌표를 입력하여 주십시오.");
			return;
		}
		var point = new ol.geom.Point([x, y]).transform(MAP.PROJECTION, map.getView().getProjection());
		map.moveToCenter(point.getCoordinates());
	},
	
	/**
	 * 지도 이동 LONLAT
	 */
	moveLonLat : function() {
		var that = this;
		var map = mapObj.getMap();
		
		var lon = $(".lonlat_lon", that.selector).numberspinner("getValue");
		var lat = $(".lonlat_lat", that.selector).numberspinner("getValue");
		
		if(lon == '') {
			$.messager.alert(that.TITLE, "경도를 입력하여 주십시오.");
			return;
		}
		else if(lat == '') {
			$.messager.alert(that.TITLE, "위도를 입력하여 주십시오.");
			return;
		}
		
		var point = new ol.geom.Point([lon, lat]).transform(ol.proj.get("EPSG:4326"), map.getView().getProjection());
		map.moveToCenter(point.getCoordinates());
	},
	
	/**
	 * 지도 이동 DMS
	 */
	moveDms : function() {
		var that = this;
		var map = mapObj.getMap();
		
		var lonDo = $(".dms_lon_do", that.selector).numberspinner("getValue");
		var lonMin = $(".dms_lon_min", that.selector).numberspinner("getValue");
		var lonSecnd = $(".dms_lon_secnd", that.selector).numberspinner("getValue");
		var latDo = $(".dms_lat_do", that.selector).numberspinner("getValue");
		var latMin = $(".dms_lat_min", that.selector).numberspinner("getValue");
		var latSecnd = $(".dms_lat_secnd", that.selector).numberspinner("getValue");
		
		if(lonDo == "") {
			$.messager.alert("위치이동", "경도 > 도를 입력하여 주십시오.");
			return;
		}
		else if(lonMin == "") {
			$.messager.alert("위치이동", "경도 > 분을 입력하여 주십시오.");
			return;
		}
		else if(lonSecnd == "") {
			$.messager.alert("위치이동", "경도 > 초를 입력하여 주십시오.");
			return;
		}
		else if(latDo == "") {
			$.messager.alert("위치이동", "위도 > 도를 입력하여 주십시오.");
			return;
		}
		else if(latMin == "") {
			$.messager.alert("위치이동", "위도 > 분을 입력하여 주십시오.");
			return;
		}
		else if(latSecnd == "") {
			$.messager.alert("위치이동", "위도 > 초를 입력하여 주십시오.");
			return;
		}
		
		var lon = that.convertLonLatByDms(lonDo, lonMin, lonSecnd);
		var lat = that.convertLonLatByDms(latDo, latMin, latSecnd);
		var point = new ol.geom.Point([lon, lat]).transform(ol.proj.get("EPSG:4326"), map.getView().getProjection());
		map.moveToCenter(point.getCoordinates());
	},
	
	/**
	 * DMS 를 LonLat 좌표로 변환
	 * @param {Number} d 도
	 * @param {Number} m 분
	 * @param {Number} s 초
	 * @returns
	 */
	convertLonLatByDms : function(d, m, s) {
		return parseFloat(d) + parseFloat(m / 60) + parseFloat(s / 3600);
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}	
	
};