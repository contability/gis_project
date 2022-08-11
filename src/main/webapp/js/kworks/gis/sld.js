/**
 * SLD
 * @type {Object}
 */
var sldObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "SLD",
		
	/**
	 * SLD 데이터
	 * @type {Object}
	 */
	data : {},
	
	/**
	 * Sld Cache Key
	 * @type {String}
	 */
	sldKey : null,
	
	/**
	 * Sld 가 변경 될때 실행되는 핸들러 목록 (putSld 시 실행)
	 * @type {Object.<Function>}
	 */
	handlers : {},
	
	/**
	 * SLD 데이터 설정
	 * @param {Object} data SLD 데이터
	 */
	setData : function(data) {
		var that = this;
		that.data = data;
	},
	
	setDataToString : function(str) {
		var that = this;
		that.data = JSON.parse(str);
	},
	
	/**
	 * SLD 데이터 반환
	 * @return {Object} SLD 데이터
	 */
	getData : function() {
		var that = this;
		return that.data;
	},
	
	getDataToString : function() {
		var that = this;
		return JSON.stringify(that.data);
	},
	
	/**
	 * 데이터 목록 추가 (NamedLayes)
	 * @param {Array.<String>} layerNames 레이어 아이디 목록
	 */
	addDatas : function(layerNames) {
		var that = this;
		var layers = layerObj.getLayersByLayerNames(layerNames);
		for(var i=0, len=layers.length; i < len; i++) {
			var layer = layers[i];
			that.data.namedLayers.push(layer.lyrBassStyle);
		}
	},
	
	/**
	 * 데이터 삭제
	 * @param {String} layerName 레이어 명
	 */
	removeData : function(layerName) {
		var that = this;
		var namedLayers = that.data.namedLayers;
		for(var i=namedLayers.length-1; i >= 0; i--) {
			var namedLayer = namedLayers[i];
			if(namedLayer.name == layerName) {
				namedLayers.splice(i, 1);
				break;
			}
		}
	},
	
	/**
	 * 레이어 표시 (레이어가 존재하는 경우 표시만 하고 존재하지 않는 경우 추가 후 표시)
	 * @param {Array.<String>} layerNames 추가 & 표시할 레이어명 목록
	 */
	showDatas : function(layerNames) {
		var that = this;
		var appendLayerNames = [];
		for(var i=0, len=layerNames.length; i < len; i++) {
			var layerName = layerNames[i];
			var namedLayer = that.getNamedLayer(layerName);
			if(namedLayer) {
				namedLayer.visible = true;
				for(var j=0, jLen=namedLayer.rules.length; j < jLen; j++) {
					var rule = namedLayer.rules[j];
					rule.visible = true;
				}
				if(namedLayer.text) {
					namedLayer.text.visible = true;
				}
			}
			else {
				appendLayerNames.push(layerName);
			}
		}
		that.addDatas(appendLayerNames);
	},
	
	/**
	 * 레이어 순서 변경
	 * @param {String} sourceLayerName 출발지 레이어 명
	 * @param {String} sourceLayerName 목적지 레이어 명
	 * @param {String} point 방향 (top : 위로) 
	 */
	changeLayerOrder : function(sourceLayerName, targetLayerName, point) {
		var that = this;
		var namedLayers = that.data.namedLayers;
		
		var temp = null;
		
		var sourceIdx = null;
		var targetIdx = null;
		// 객체 임시 보관 후 배열에서 삭제
		for(var i=0, len=namedLayers.length; i < len; i++) {
			var namedLayer = namedLayers[i];
			if(namedLayer.name == sourceLayerName) {
				sourceIdx = i;
				temp = namedLayer;
				break;
			}
		}
		namedLayers.splice(sourceIdx, 1);
		
		// 타겟 위치에 임시 보관한 객체 추가
		for(var i=0, len=namedLayers.length; i < len; i++) {
			var namedLayer = namedLayers[i];
			if(namedLayer.name == targetLayerName) {
				targetIdx = i;
				break;
			}
		}
		if(point == "top") {
			targetIdx++;
		}
		namedLayers.splice(targetIdx, 0, temp);
	},
	
	/**
	 * 핸들러 추가
	 * @param {String} id 핸들러 아이디
	 * @param {Function} handler 핸들러
	 */
	addHandler : function(id, handler) {
		var that = this;
		that.handlers[id] = handler;
	},
	
	/**
	 * 핸들러 제거
	 * @param {String} id 핸들러 아이디
	 */
	removeHandler : function(id) {
		var that = this;
		delete that.handlers[id];
	},
	
	/**
	 * 핸들러 목록 실행
	 */
	executeHandlers : function() {
		var that = this;
		for(var i in that.handlers) {
			var handler = that.handlers[i];
			handler(that.getData());
		}	
	},
	
	/**
	 * SLD 캐쉬에 저장
	 * @returns {Promise} Promise 객체
	 */
	putSld : function() {
		var that = this;
		
		var data = $.extend(true, {}, that.data);
		if(MAP.SERVER_TYPE == "arcgis") {
			for(var i=0, len=data.namedLayers.length; i < len; i++) {
				var namedLayer = data.namedLayers[i];
				if(namedLayer.name == "SWL_PIPE_LM") {
					var layer = namedLayer;
					for(var j=0, jLen=layer.rules.length; j < jLen; j++) {
						var rule = layer.rules[j];
						if(rule.point) {
							delete rule.point;	
						}
					}
					break;
				}
			}
		}	
		
		var jsonStr = JSON.stringify(data);
		var promise = $.ajax({
			url : CONTEXT_PATH + "/cmmn/sld/putSLD.do",
			type : "POST",
			data : jsonStr,
			contentType : "text/plain"
		}).done(function(response) {
			if(response && response.sldKey) {
				that.sldKey = response.sldKey;
				that.executeHandlers();
			}
		});
		return promise;
	},
	
	/**
	 * WMS 레이어 갱신 (SLD)
	 * @param name
	 * @param title
	 * @param layer
	 * @param layerNames
	 */
	reloadWMSFromSld : function(name, title, layer, layerNames) {
		var layers = layerObj.getLayersByLayerNames(layerNames);
		if(layers && layers.length > 0) {
			
			var namedLayers = [];
			for(var i=0, len=layers.length; i < len; i++) {
				namedLayers.push(layers[i].lyrBassStyle);
			}
			
			var data = {
				name : name,
				title : title,
				namedLayers : namedLayers
			};
			
			for(var i=0, len=data.namedLayers.length; i < len; i++) {
				var namedLayer = data.namedLayers[i];
				namedLayer.visible = true;
				if(namedLayer.rules) {
					for(var j=0, jLen=namedLayer.rules.length; j < jLen; j++) {
						var rule = namedLayer.rules[j];
						rule.visible = true;
					}
				}
			}
		
			var jsonStr = JSON.stringify(data);
			$.ajax({
				url : CONTEXT_PATH + "/cmmn/sld/putSLD.do",
				type : "POST",
				data : jsonStr,
				contentType : "text/plain"
			}).done(function(response) {
				if(response && response.sldKey) {
					var source = layer.getSource();
					source.updateParams($.extend({ nocache : Math.random() }, {
						LAYERS : layerNames.join(),
						STYLES : layerNames.join(),
						SLD_KEY : response.sldKey
					}));
				}
			});
			
		}
		
	},
	
	/**
	 * Sld Key 반환
	 * @returns {String} Sld Key
	 */
	getSldKey : function() {
		var that = this;
		return that.sldKey;
	},
	
	getPrintSldKey : function() {
		var that = this;
		var sldKey = that.sldKey;
		var data = $.extend(true, {}, that.data);
		// 아크 하수관로 예외 처리
		if(MAP.SERVER_TYPE == "arcgis") {
			var layer = null;
			for(var i=0, len=data.namedLayers.length; i < len; i++) {
				var namedLayer = data.namedLayers[i];
				if(namedLayer.name == "SWL_PIPE_LM") {
					layer = namedLayer;
				}
			}
			
			if(layer && layer.text && layer.text.visible) {
				var checked = true;
				for(var i=0, len=layer.rules.length; i < len; i++) {
					var rule = layer.rules[i];
					if(rule.visible) {
						checked = false;
					}
				}
				if(checked) {
					for(var i=0, len=layer.rules.length; i < len; i++) {
						var rule = layer.rules[i];
						rule.visible = true;
					}
					var jsonStr = JSON.stringify(data);
					$.ajax({
						url : CONTEXT_PATH + "/cmmn/sld/putSLD.do",
						type : "POST",
						data : jsonStr,
						async : false,
						contentType : "text/plain"
					}).done(function(response) {
						if(response && response.sldKey) {
							sldKey = response.sldKey;
						}
					});
				}
			}
		}
		return sldKey;
	},
	
	/**
	 * WMS 파라미터 반환
	 * @returns {String} WMS 파라미터
	 */
	getParams : function() {
		var that = this;
		
		var params = {
			nocache : Math.random()
		};
		
		var layerNames = that.getLayerNames();
		if(layerNames && layerNames.length > 0) {
			params.LAYERS = caseUtils.getText(layerNames.join());
			params.STYLES = caseUtils.getText(layerNames.join());
			params.SLD_KEY = that.sldKey;
		}
		else {
			params.LAYERS = serverObj.getBlankLayers();
			params.STYLES = serverObj.getBlankLayers();
		}
		
		if(MAP.SERVER_TYPE == "arcgis") {
			var layer = that.getNamedLayer("SWL_PIPE_LM");
			if(layer && layer.text && layer.text.visible) {
				var checked = true;
				for(var i=0, len=layer.rules.length; i < len; i++) {
					var rule = layer.rules[i];
					if(rule.visible) {
						checked = false;
					}
				}
				if(checked) {
					params.STYLES = params.STYLES.replace("SWL_PIPE_LM", "default");
				}
			}
		}
		
		return params;
	},
	
	/**
	 * 레이어 목록 반환 (기본 : visible 이 true 인 레이어 목록 반환)
	 * @param {Object} opt_options 옵션 (isAll : 전체 반환) 
	 * @returns {String} 레이어 목록
	 */
	getLayerNames : function(opt_options)
	{
		var options = opt_options || {};
		
		var that = this;
		var layers = [];

		if (that.data.namedLayers) {
//			for (var i in that.data.namedLayers)
//			{
//				if(options.isAll) {
//					layers.push(that.data.namedLayers[i].name);
//				}
//				else if (that.data.namedLayers[i].visible)
//				{
//					layers.push(that.data.namedLayers[i].name);
//				}
//			}
			for (var i = 0; i < that.data.namedLayers.length; i++)
			{
				var layer = that.data.namedLayers[i];
				
				if(options.isAll) {
					layers.push(layer.name);
				}
				else if (layer.visible)
				{
					layers.push(layer.name);
				}
			}
		}
		return layers;
	},
	
	/**
	 * 표시 설정
	 * @param {Boolean} checked 표시 여부
	 */
	setVisible : function(checked) {
		var that = this;
		var namedLayers = that.data.namedLayers;
		for(var i=0, len=namedLayers.length; i < len; i++) {
			var namedLayer = namedLayers[i];
			namedLayer.visible = checked;
			
			var rules = namedLayer.rules;
			for(var j=0, jLen=rules.length; j < jLen; j++) {
				var rule = rules[j];
				rule.visible = checked;
			}
			
			if(namedLayer.text) {
				namedLayer.text.visible = checked;
			}
		}
	},
	
	/**
	 * 레이어 표시 설정
	 * @param {String} layerName 레이어 명
	 * @param {Boolean} checked 표시 여부
	 */
	setNamedLayerVisible : function(layerName, checked) {
		var that = this;
				
		var namedLayer = that.getNamedLayer(layerName);
		namedLayer.visible = checked;
		
		var rules = namedLayer.rules;
		for(var i=0, len=rules.length; i < len; i++) {
			var rule = rules[i];
			rule.visible = checked;
		}
		
		if(namedLayer.text) {
			namedLayer.text.visible = checked;
		}
	},
	
	/**
	 * 룰 표시 설정
	 * @param {String} layerName 레이어 명
	 * @param {String} ruleName 룰 명
	 * @param {Boolean} checked 표시 여부
	 */
	setRuleVisible : function(layerName, ruleName, checked) {
		var that = this;
		var rule = that.getRule(layerName, ruleName);
		rule.visible = checked;
		that.triggerLayerVisible(layerName);
	},
	
	/**
	 * 텍스트 룰 표시 설정
	 * @param {String} layerName 레이어 명
	 * @param {Boolean} checked 표시 여부
	 */
	setTextVisible : function(layerName, checked) {
		var that = this;
		var namedLayer = that.getNamedLayer(layerName);
		namedLayer.text.visible = checked;
		that.triggerLayerVisible(layerName);
	},
	
	/**
	 * 레이어 표시 트리거 (하위 룰의 표시여부에 따라 표시 여부 수정)
	 * @param {String} layerName 레이어 명
	 */
	triggerLayerVisible : function(layerName) {
		var that = this;
		var namedLayer = that.getNamedLayer(layerName);
		
		var visible = false;
		var rules = namedLayer.rules;
		for(var i=0, len=rules.length; i < len; i++) {
			var rule = rules[i];
			if(rule.visible) {
				visible = true;
				break;
			};
		}
		
		if(namedLayer.text && !visible) {
			visible = namedLayer.text.visible;
		}
		
		namedLayer.visible = visible;
	},
	
	setRules : function(layerName, rules, text) {
		var that = this;
		var namedLayer = that.getNamedLayer(layerName);
		namedLayer.rules = rules;
		if(text) {
			namedLayer.text = text;
		}
		else {
			if(namedLayer.text) {
				delete namedLayer.text;
			}
		}
	},
	
	/**
	 * 선택한 레이어에 대한 NamedLayer 반환
	 * @param {String} layerName 레이어 명
	 * @returns {Object} NamedLayer 반환
	 */
	getNamedLayer : function(layerName) {
		var that = this;
		for(var i=0, len=that.data.namedLayers.length; i < len; i++) {
			var namedLayer = that.data.namedLayers[i];
			if(namedLayer.name == layerName.toUpperCase()) {
				return namedLayer;
			}
		}
		return null;
	},
	
	/**
	 * 룰 검색
	 * @param {String} layerName 레이어 아이디
	 * @param {String} ruldName 룰 아이디
	 * @returns {Object} 룰
	 */
	getRule : function(layerName, ruldName) {
		var that = this;
		for(var i=0, len=that.data.namedLayers.length; i < len; i++) {
			var namedLayer = that.data.namedLayers[i];
			if(namedLayer.name == layerName) {
				for(var j=0, jLen=namedLayer.rules.length; j < jLen; j++) {
					var rule = namedLayer.rules[j];
					if(rule.name == ruldName) {
						return rule;
					}
				}
			}
		}
		return null;
	},
	
	createRule : function(name, title, filter, symbolizer) {
		var rule = {
			name : name,
			title : title,
			visible : true
		};
		if(filter) {
			rule.filter = filter;
		}
		
		if(symbolizer) {
			rule = $.extend(true, rule, symbolizer);
		}	
		return rule;
	},
	
	createSymbolizer : function(type) {
		var that = this;
		var symbolizer = null;
		if(type == "POINT") {
			symbolizer = { 
				point : that.createPointSymbolizer()
			};
		}
		else if(type == "LINESTRING") {
			symbolizer = {
				line : that.createLineSymbolizer()
			};
		}
		else if(type == "POLYGON") {
			symbolizer = {
				polygon : that.createPolygonSymbolizer()
			};
		}
		else {
			$.messager.alert(that.TITLE, "정의되지 않은 레이어 타입입니다.");
		}
		return symbolizer;
	},
	
	createPointSymbolizer : function() {
		var symbolizer = {
			resource : "DEFAULT_SYMBOL.png",
			size : 15,
			anchor : "cm"
		};
		return symbolizer;
	},
	
	
	createLineSymbolizer : function() {
		var symbolizer = {
			stroke : "#e23705",
			strokeOpacity : 1,
			strokeWidth : 2,
			strokeDasharray : "solid"
		};
		return symbolizer;
	},
	
	createPolygonSymbolizer : function() {
		var symbolizer = {
			stroke : "#e23705",
			strokeOpacity : 1,
			strokeWidth : 2,
			strokeDasharray : "solid",
			fill : "#ff8000",
			fillOpacity : 0.7
		};
		return symbolizer;
	},
	
	createTextRule : function() {
		var rule = {
			visible : true,
			fontFamily : 'Gulim',
			fontStyle : "normal",
			fontWeight : "normal",
			fontSize : 12,
			anchor : "cm",
			displacementX : 0,
			displacementY : 0,
			haloRadius : 2,
			halo : "#ffffff",
			haloOpacity : 1,
			fill : "#000000",
			fillOpacity : 1,
			maxScale : 2000
		};
		return rule;
	},
	
	convertOpacityToPercent : function(value) {
		var opacity = 100 - parseFloat(value)*100;
		return parseInt(opacity);
	},
	
	convertOpacityToValue : function(value) {
		return 1- (parseInt(value) * 0.01);
	}

};