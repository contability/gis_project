/**
 * 지도에 선택 기능 (점, 선)
 * @type {Object}
 */
var selectObj = {
		
	/**
	 * 벡터 소스 
	 * @type {ol.source.Vector}
	 */
	source : null,
	
	/**
	 * 이 기능을 호출한 클래스 명
	 * @type {String}
	 */
	className : null,
	
	/**
	 * 인터렉션 목록
	 * @type {Object}
	 */
	interactions : {
		/**
		 * 점 그리기 인터렉션
		 * @type {kworks.interaction.Draw}
		 */
		Point : null,
		
		/**
		 * 선 그리기 인터렉션
		 * @type {kworks.interaction.Draw}
		 */
		LineString : null,
		
		/**
		 * 사각형 그리기 인터렉션
		 * @type {kworks.interaction.Draw}
		 */
		Rect : null,
		
		/**
		 * 다각형 그리기 인터렉션
		 * @type {kworks.interaction.Draw}
		 */
		Polygon : null,
		
		/**
		 * 원 그리기 인터렉션
		 * @type {kworks.interaction.Draw}
		 */
		Circle : null
	},
	
	/**
	 * 소스 핸들러
	 */
	sourceHandler : null,
	
	/**
	 * 등록 핸들러 목록
	 * @type {Array.<Object>}
	 */
	handlers : [],
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.initGis();
		that.isCreated = true;
	},
	
	/**
	 * GIS 초기화
	 */
	initGis : function() {
		var that = this;
		that.source = new ol.source.Vector();
		var layer = new ol.layer.Vector({
			id : "selectLayer",
			source : that.source
		});
		mapObj.getMap().addLayer(layer);
		that.source.on("addfeature", function(evt) {
			that.sourceHandler(evt);
		});
		
		for(var type in that.interactions) {
			var options = {};
			if(type == "Rect") {
				options.type = "Circle";
				options.geometryFunction = ol.interaction.Draw.createRegularPolygon(4);;
			}
			else {
				options.type = type;
			}
			options.source = that.source;
			
			that.interactions[type] = new kworks.interaction.Draw(options);
			that.interactions[type].set("id", "select"+type);
			that.interactions[type].set("name", "select"+type);
			mapObj.getMap().addInteraction(that.interactions[type]);
			that.interactions[type].setActive(false);
		}
	},
	
	/**
	 * 벡터 소스 반환
	 * @returns {ol.source.Vector} 벡터 소스
	 */
	getSource : function() {
		var that = this;
		return that.source;
	},
	
	/**
	 * 한번 실행
	 * @param {String} type 기능 타입 (Point, LineString, Rect, Polygon, Circle)
	 * @param {String} eventType 이벤트 타입 (drawstart, drawadd, drawend)
	 * @param {Function} callback 콜백 함수
	 * @param {Boolean} isVisible 그린 도형 표시 여부
	 */
	once : function(CLASS_NAME, type, eventType, handler, isVisible) {
		var that = this;
		that.className = CLASS_NAME;
		that.clear();
		
		mapObj.getMap().activeInteractions("drag", "select"+type);
		
		if(eventType && handler) {
			that.interactions[type].once(eventType, function(evt) {
				handler(evt);
				$("#a_map_tool_bass img").trigger("click");
				that.clear();
			});
		}
		if(isVisible) {
			that.sourceHandler = function(evt) {
				that.visibleSingle(evt);
			};
		}
		else {
			that.sourceHandler = function(evt) {
				that.visibleNone(evt);
			};
		}
	},
	
	/**
	 * 기능 활성
	 * @param {String} type 기능 타입 (Point, LineString, Rect, Polygon, Circle)
	 * @param {String} eventType 이벤트 타입 (drawstart, drawadd, drawend)
	 * @param {Function} handler 이벤트 발생 시 실행될 함수
	 * @param {Boolean} isVisible 그린 도형 표시 여부
	 */
	active : function(CLASS_NAME, type, eventType, handler, isVisible) {
		var that = this;
		that.className = CLASS_NAME;
		that.clear();

		if(eventType && handler) {
			that.handlers.push({type : type, eventType : eventType, handler : handler});
			that.interactions[type].on(eventType, handler);
		}	
		if(isVisible) {
			that.sourceHandler = function(evt) {
				that.visibleSingle(evt);
			};
		}
		else {
			that.sourceHandler = function(evt) {
				that.visibleNone(evt);
			};
		}
		mapObj.getMap().activeInteractions("drag", "select"+type);
	},
	
	/**
	 * 화면에 표시 안함
	 * @params {Object} evt 이벤트 객체
	 */
	visibleNone : function(evt) {
		var that = this;
		var feature = evt.feature;
		feature.type = that.className;
		that.clearFeatures();
	},
	
	/**
	 * 한 개만 표시
	 * @param {Object} evt 이벤트 객체
	 */
	visibleSingle : function(evt) {
		var that = this;
		var feature = evt.feature;
		that.clearFeatures();
		feature.type = that.className;
	},
	
	/**
	 * 정리
	 */
	clear : function(className) {
		var that = this;
		that.clearFeatures(className);
//		for(var i in that.handlers) {
//			that.interactions[that.handlers[i].type].un(that.handlers[i].eventType, that.handlers[i].handler);
//		}
		for (var i = 0; i< that.handlers.length; i++) {
			var handler = that.handlers[i];
			that.interactions[handler.type].un(handler.eventType, handler.handler);
		}
		that.handlers = [];
	},
	
	/**
	 * 도형 목록 반환
	 * @type {String} className 클래스명
	 * @return {Array.<ol.Featuer>} 도형 목록
	 */
	getFeatures : function(className) {
		var that = this;
		var result = [];
		var features = that.source.getFeatures();
		for(var i=features.length-1; i >= 0; i--) {
			if(features[i].type == that.className) {
				result.push(features[i]);
			}
		}
		return result;
	},
	
	/**
	 * 도형 목록 지우기
	 * @param {String} className 클래스명
	 */
	clearFeatures : function(className) {
		var that = this;
		if(!className) className = that.className;
		
		var features = that.source.getFeatures();
		for(var i=features.length-1; i >= 0; i--) {
			if(features[i].type == className) {
				that.source.removeFeature(features[i]); 
			}
		}
	}
		
};