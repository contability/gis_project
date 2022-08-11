/**
 * 파노라마 VR 위치 및 조회
 */
var panoramaVrObj = {
	/**
	 * 선택자 
	 */
	selector : null,
	
	/**
	 * 벡터 소스
	 */
	source : null,
	
	/**
	 * 레이어
	 */
	layer : null,
	
	/**
	 * 마우스 hover interaction
	 */
	interactionHover : null,
	
	/**
	 * 마우스 클릭 interaction
	 */
	interactionClick : null,
	
	features : null,
	
	hoverFeature : null,
	
	/**
	 * 스타일
	 * @param isOn
	 * @returns {ol.style.Style}
	 */
	getStyle : function(isOn) {
		var str = isOn?"_ov":"";
		return new ol.style.Style({
			image : new ol.style.Icon({
				src : MAP.SYMBOL_URL + '/drone' + str + '.png',
				anchor : [1, 1]
			})
		});
	},
	
	/**
	 * vr 촬영포인트 초기화 
	 */
	initFeatures : function() {
		var that = this;
		
		var features = [];
		
		var skyVeiwPoint1 = gisObj.toTransformMap(new ol.geom.Point([210292.45, 550371.066]));
		var skyVeiwFeature1 = new ol.Feature({
			geometry : skyVeiwPoint1,
			name : '묵호항' 
		}); 
		skyVeiwFeature1.setId(1);
		skyVeiwFeature1.setStyle(that.getStyle());
		features.push(skyVeiwFeature1);
		
		var skyVeiwPoint2 = gisObj.toTransformMap(new ol.geom.Point([214267.518, 542302.253]));
		var skyVeiwFeature2 = new ol.Feature({
			geometry : skyVeiwPoint2,
			name : '추암해수욕장' 
		}); 
		skyVeiwFeature2.setId(2);
		skyVeiwFeature2.setStyle(that.getStyle());
		features.push(skyVeiwFeature2);
		
		var skyVeiwPoint3 = gisObj.toTransformMap(new ol.geom.Point([202711.265, 541574.542]));
		var skyVeiwFeature3 = new ol.Feature({
			geometry : skyVeiwPoint3,
			name : '무릉별유천지' 
		}); 
		skyVeiwFeature3.setId(3);
		skyVeiwFeature3.setStyle(that.getStyle());
		features.push(skyVeiwFeature3);
		
		//관리자, 드론촬영항공사진조회_지적재조사 권한그룹 사용자만 조회
		var skyVeiwPoint4;
		var skyVeiwPoint5;
		var skyVeiwPoint6;
		var skyVeiwFeature4;
		var skyVeiwFeature5;
		var skyVeiwFeature6;
		if (userAuthorGroupObj.getUserAuthorGroup('관리자') || userAuthorGroupObj.getUserAuthorGroup('드론촬영항공사진조회_지적재조사')) {
			skyVeiwPoint4 = gisObj.toTransformMap(new ol.geom.Point([209124.913, 549228.744]));
			skyVeiwFeature4 = new ol.Feature({
				geometry : skyVeiwPoint4,
				name : '지적재조사1' 
			}); 
			skyVeiwFeature4.setId(4);
			skyVeiwFeature4.setStyle(that.getStyle());
			features.push(skyVeiwFeature4);
		
			skyVeiwPoint5 = gisObj.toTransformMap(new ol.geom.Point([209188.196, 549222.547]));
			skyVeiwFeature5 = new ol.Feature({
				geometry : skyVeiwPoint5,
				name : '지적재조사2' 
			}); 
			skyVeiwFeature5.setId(5);
			skyVeiwFeature5.setStyle(that.getStyle());
			features.push(skyVeiwFeature5);
		
			skyVeiwPoint6 = gisObj.toTransformMap(new ol.geom.Point([209371.998, 549599.068]));
			skyVeiwFeature6 = new ol.Feature({
				geometry : skyVeiwPoint6,
				name : '지적재조사3' 
			}); 
			skyVeiwFeature6.setId(6);
			skyVeiwFeature6.setStyle(that.getStyle());
			features.push(skyVeiwFeature6);
		}
		
		that.features = features;
	},
	
	/**
	 * GIS 초기화
	 */
	initGIS : function() {
		var that = this;
		
		that.source = new ol.source.Vector();
		that.layer = new ol.layer.Vector({
			id : "droneLayer",
			source : that.source
		});
		mapObj.getMap().addLayer(that.layer);
		
		//마우스 Hover
		that.interactionHover = new ol.interaction.Select({
			layers : [that.layer],
			condition : ol.events.condition.pointerMove
		});
		that.interactionHover.set('id','droneHover');
		that.interactionHover.set('name','droneHover');
		that.interactionHover.on("select", function(event) {
			var feature = null;
			
			// 기존 마우스 hover feature 원래대로
			if (that.hoverFeature) {
				var feature = that.hoverFeature;
				feature.setStyle(that.getStyle());
				that.hoverFeature = null;
			}
			
			if(event.selected && event.selected.length > 0) {
				feature = event.selected[0];
				feature.setStyle(that.getStyle(true));
				that.hoverFeature = feature;
			} 
			that.interactionHover.getFeatures().clear();
		});
		mapObj.getMap().addInteraction(that.interactionHover);
		
		//클릭
		that.interactionClick = new ol.interaction.Select({
			layers : [that.layer],
			condition : ol.events.condition.click
		});
		that.interactionClick.set('id','droneClick');
		that.interactionClick.set('name','droneClick');
		that.interactionClick.on("select", function(event) {
			var feature = null;
			if(event.selected && event.selected.length > 0) {
				feature = event.selected[0];
			}
			
			if(feature) {
				// 개발서버와 운영서버의 IP
				var serverIP  = '';
				if (ENV_TYPE == 'dev') {
					serverIP = '192.168.0.12:8085';
				} else {
					serverIP = '106.4.1.42:8080';
				}
				
				// vr 영상의 url 생성
				var vrUrl = '';
				var featureId = feature.getId();
				switch(featureId) {
					case 1:
					vrUrl = 'http://' + serverIP + '/vtour-1';
					break;
					case 2:
					vrUrl = 'http://' + serverIP + '/vtour-2';
					break;
					case 3:
					vrUrl = 'http://' + serverIP + '/vtour-3';
					break;
					case 4:
					vrUrl = 'http://' + serverIP + '/vtour-4';
					break;
					case 5:
					vrUrl = 'http://' + serverIP + '/vtour-5';
					break;
					case 6:
					vrUrl = 'http://' + serverIP + '/vtour-6';
					break;
					default:
					vrUrl = 'http://' + serverIP + '/vtour-1';
					break;
				}
				
				window.open(vrUrl);
				that.interactionClick.getFeatures().clear();
			}
		});
		mapObj.getMap().addInteraction(that.interactionClick);
	},
	
	addFeatures : function() {
		var that = this;
		
		that.source.addFeatures(that.features);
	},
	
	/**
	 * 초기화 
	 */
	init : function() {
		var that = this;

		that.initFeatures();
		that.initGIS();
		that.addFeatures();
	}
};
