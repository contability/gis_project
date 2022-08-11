var multiObj = {
		
	maps : null,
	
	count : 0,
	
	mode : 0,
	
	init : function() {
		var that = this;
		that.initMaps();
		that.initUi();
	},
	
	initMaps : function() {
		var that = this;
		that.maps = new kworks.Maps();
		that.maps.setBaseMap(mapObj.getMap());
	},
	
	initUi : function() {
		var that = this;
		
		// 배경지도 콤보 박스
		$("#sel_map_layer_background_layer_base").combobox({
			data : that.createBackgroundLayerComboboxData(0),
			valueField : "id",
			textField : "title",
			width : 150,
			onSelect : function(data) {
				var service = data.service;
				var type = data.type;
				backgroundMapObj.change(service, type);
			}
		});
		
		// 메인 지도 지적도 on/off
		$("#a_land_register_base").click(function() {
			var element = $(this);
			var checked = null;
			if(element.hasClass("selected")) {
				element.removeClass("selected");
				imageUtils.off(element.find("img"));
				checked = false;
			}
			else {
				element.addClass("selected");
				imageUtils.on(element.find("img"));
				checked = true;
			}
			
			if(checked) {
				sldObj.showDatas([COMMON.LAND]);
			}
			else {
				sldObj.setNamedLayerVisible(COMMON.LAND, checked);
			}
			mapObj.reloadWMS();
		});
	},
	
	setMode : function(mode) {
		var that = this;
		if(that.mode != mode) {
			that.mode = mode;
			that.removeDivs();
			
			that.setCount();
			that.createDivs();
			that.createMaps();
			that.createBackgroundLayer();
			that.createThemamap();
			that.bindEvents();
			that.maps.moveEnd();
			
			if(that.mode == 0) {
				$("#div_map_layer_tool_base").hide();
			}
			else {
				$("#sel_map_layer_background_layer_base").combobox("select", backgroundMapObj.getService() + "_" + backgroundMapObj.getType());
				
				// 지적 ON/OFF 
				var element = $("#a_land_register_base");
				var namedLayer = sldObj.getNamedLayer(COMMON.LAND);
				if(namedLayer && namedLayer.visible) {
					element.addClass("selected");
					imageUtils.on(element.find("img"));
				}
				else {
					element.removeClass("selected");
					imageUtils.off(element.find("img"));
				}
				
				$("#div_map_layer_tool_base").show();
			}
			//$("#a_map_tool_bass img").trigger("click");
		}
	},
	
	setCount : function() {
		var that = this;
		switch(that.mode) {
			case 0 : 
				that.count = 1;
				break;
			case 1 :
				that.count = 2;
				break;
			case 2 :
				that.count = 2;
				break;
			case 3 :
				that.count = 3;
				break;
			case 4 :
				that.count = 3;
				break;
			case 5 :
				that.count = 3;
				break;
			case 6 :
				that.count = 3;
				break;
			case 7 :
				that.count = 4;
				break;
		}
	},
	
	removeDivs : function() {
		var that = this;
		for(var i=1; i < that.count; i++) {
			$("#map_"+i).remove();
		}
	},
	
	createDivs : function() {
		var that = this;
		for(var i=1; i < that.count; i++) {
			var tagStr = "";
			tagStr += "<div id='map_" + i + "' class='bd_black po_a' >";
			tagStr += "<div style='position:relative;width:100%;height:100%;overflow-x:hidden;'>";
			
			// 지도 레이어
			tagStr += '<div class="div_map_layer_tool">';
			tagStr += '<span class="span_title">배경지도</span>';
			tagStr += '<select id="sel_map_' + i + '_layer_background_layer">';
			tagStr += '</select>';
			tagStr += '<span class="span_title">주제도</span>';
			tagStr += '<select id="sel_map_' + i + '_layer_themamap">';
			tagStr += '</select>';
			tagStr += '<a href="#" class="a_land_register" data-index="' + i + '" >';
			tagStr += '<img src="' + CONTEXT_PATH + '/images/kworks/map/svg/topTools/land_register_off.png" alt="지번" title="지번" />';
			tagStr += '</a>';
			tagStr += '</div>';
			
			// 지도
			tagStr += "<div id='div_map_" + i + "' style='width:100%;height:100%' ></div>";
			tagStr += "</div>";
			tagStr += "</div>";
			$("#map_container").append(tagStr);
		}
		
		switch(that.mode) {
			case 0 : 
				$("#map").css("width", "100%").css("height", "100%").css("left", "").css("top", "").css("right", "0px").css("bottom", "0px");
				break;
			case 1 :
				$("#map").css("width", "50%").css("height", "100%").css("left", "0px").css("top", "0px").css("right", "").css("bottom", "");
				$("#map_1").css("width", "50%").css("height", "100%").css("left", "50%").css("top", "0%");
				break;
			case 2 :
				$("#map").css("width", "100%").css("height", "50%").css("left", "0px").css("top", "0px").css("right", "").css("bottom", "");
				$("#map_1").css("width", "100%").css("height", "50%").css("left", "0%").css("top", "50%");
				break;
			case 3 :
				$("#map").css("width", "50%").css("height", "100%").css("left", "0px").css("top", "0px").css("right", "").css("bottom", "");
				$("#map_1").css("width", "50%").css("height", "50%").css("left", "50%").css("top", "0%");
				$("#map_2").css("width", "50%").css("height", "50%").css("left", "50%").css("top", "50%");
				break;
			case 4 :
				$("#map").css("width", "50%").css("height", "50%").css("left", "0px").css("top", "0px").css("right", "").css("bottom", "");
				$("#map_1").css("width", "50%").css("height", "100%").css("left", "50%").css("top", "0%");
				$("#map_2").css("width", "50%").css("height", "50%").css("left", "0%").css("top", "50%");
				break;
			case 5 :
				$("#map").css("width", "100%").css("height", "50%").css("left", "0px").css("top", "0px").css("right", "").css("bottom", "");
				$("#map_1").css("width", "50%").css("height", "50%").css("left", "0%").css("top", "50%");
				$("#map_2").css("width", "50%").css("height", "50%").css("left", "50%").css("top", "50%");
				break;
			case 6 :
				$("#map").css("width", "50%").css("height", "50%").css("left", "0px").css("top", "0px").css("right", "").css("bottom", "");
				$("#map_1").css("width", "50%").css("height", "50%").css("left", "50%").css("top", "0%");
				$("#map_2").css("width", "100%").css("height", "50%").css("left", "0%").css("top", "50%");
				break;
			case 7 :
				$("#map").css("width", "50%").css("height", "50%").css("left", "0px").css("top", "0px").css("right", "").css("bottom", "");
				$("#map_1").css("width", "50%").css("height", "50%").css("left", "50%").css("top", "0%");
				$("#map_2").css("width", "50%").css("height", "50%").css("left", "0%").css("top", "50%");
				$("#map_3").css("width", "50%").css("height", "50%").css("left", "50%").css("top", "50%");
				break;
		}
	},
	
	createMaps : function() {
		var that = this;
		that.maps.clear();
		for(var i=1; i < that.count; i++) {
			var types = backgroundMapObj.data.tms.types;
			var layers = [];
			for(var key in types) {
				layers.push(types[key]);
			}
			layers.reverse();
			
			var index = i>layers.length?0:i-1;
			var layer = layers[index].layers;
			that.maps.addMap(that.createMap("div_map_"+i, layer));
		}
		mapObj.getMap().updateSize();
	},
	
	createMap : function(target, layer) {
		var layers = [];
		for(var i=0, len=layer.length; i < len; i++) {
			layers.push(layer[i]);
		}
		layers.push(new ol.layer.Image({
			id : "kc_wms",
			source : new kworks.source.ImageWMS({
				url : CONTEXT_PATH + "/proxy/wms.do",
				params : $.extend(serverObj.getWMSParams(), {
					LAYERS : serverObj.getBlankLayers(),
					STYLES : serverObj.getBlankLayers()
				}),
				ratio : 1,
				serverType : MAP.SERVER_TYPE
			})
		}));
		
		
		return new kworks.Map({
			target : target,
			layers : layers,
			view : backgroundMapObj.getView("tms")
		});
	},
	
	createBackgroundLayer : function() {
		var that = this;
		for(var i=1; i < that.count; i++) {
			var types = backgroundMapObj.data.tms.types;
			var mapIndex = i-1;
			var keys = [];
			for(var key in types) {
				keys.push(key);
			}
			keys.reverse();
			
			var index = i>keys.length?0:i-1;
			that.createBackgroundLayerCombobox("#sel_map_" + i + "_layer_background_layer", mapIndex);
			$("#sel_map_" + i + "_layer_background_layer").combobox("select", "tms_"+keys[index]);
			
		}
	},
	
	createThemamap : function() {
		var that = this;
		for(var i=1; i < that.count; i++) {
			var mapIndex = i-1;
			that.createThemamapCombobox("#sel_map_" + i + "_layer_themamap", mapIndex);
		}
	},
	
	bindEvents : function() {
		var that = this;
		
		$("#map_container .a_land_register").click(function() {
			var element = $(this);
			
			var index = element.attr("data-index");
			if(index != 0) {
				var checked = null;
				if(element.hasClass("selected")) {
					element.removeClass("selected");
					imageUtils.off(element.find("img"));
					checked = false;
				}
				else {
					element.addClass("selected");
					imageUtils.on(element.find("img"));
					checked = true;
				}
				
				var mapIndex = index-1;
				var map = that.maps.getMap(mapIndex);
				var sld = map.get("sld");
				if(sld) {
					var namedLayers = sld.namedLayers;
					for(var i=0, len=namedLayers.length; i < len; i++) {
						var namedLayer = namedLayers[i];
						if(namedLayer.name == COMMON.LAND) {
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
					}
				}
				that.reloadWMS(map, mapIndex);
			}
			
		});
	},
	
	createBackgroundLayerCombobox : function(selector, mapIndex) {
		var that = this;
		$(selector).combobox({
			data : that.createBackgroundLayerComboboxData(mapIndex),
			valueField : "id",
			textField : "title",
			width : 150,
			onSelect : function(data) {
				var mapIndex = data.mapIndex;
				var service = data.service;
				var type = data.type;
				var map = that.maps.getMap(mapIndex);
				backgroundMapObj.change(service, type, map);
			}
		});
	},
	
	createBackgroundLayerComboboxData : function(mapIndex) {
		var data = [];
		for(var service in backgroundMapObj.data) {
			var types = backgroundMapObj.data[service].types;
			for(var key in types) {
				data.push({
					id : service + "_" + key,
					service : service,
					type : key,
					title : types[key].description,
					mapIndex : mapIndex
				});
			}
		}
		return data;
	},
	
	createThemamapCombobox : function(selector, mapIndex) {
		var that = this;
		var data = [];
		data.push({ id:-1, name : "선택", mapIndex : mapIndex });
		var sysData = themamapObj.getData("SYS");
		for(var i=0, len=sysData.length; i < len; i++) {
			var themamap = sysData[i];
			if(themamap.themamapId == THEMAMAP_ID) {
				data.push({
					id : themamap.themamapId,
					name : themamap.themamapNm,
					mapIndex : mapIndex
				});
			}
		}
		var userData = themamapObj.getData("USER");
		for(var i=0, len=userData.length; i < len; i++) {
			var themamap = userData[i];
			data.push({
				id : themamap.themamapId,
				name : themamap.themamapNm,
				mapIndex : mapIndex
			});
		}
		
		$(selector).combobox({
			data : data,
			valueField : "id",
			textField : "name",
			width : 150,
			onSelect : function(data) {
				var themamapId = data.id;
				var mapIndex = data.mapIndex;
				that.selectThemamap(themamapId, mapIndex);
			}
		});
		$(selector).combobox("select", -1);
	},
	
	selectThemamap : function(themamapId, mapIndex) {
		var that = this;
		if(themamapId == -1) {
			var map = that.maps.getMap(mapIndex);
			map.set("sld", null);
			that.reloadWMS(map, mapIndex);
			that.triggerLandRegister(map, mapIndex);
		}
		else {
			themamapObj.selectOne(themamapId).done(function(themamap, jsonStr) {
				if(themamap) {
					var map = that.maps.getMap(mapIndex);
					var data = JSON.parse(jsonStr);
					map.set("sld", data);
					that.reloadWMS(map, mapIndex);
					that.triggerLandRegister(map, mapIndex);
				}
				else {
					$.messager.alert(that.TITLE, "주제도를 불러오는 데 실패했습니다.");
				}
			});
		}
	},
	
	reloadWMS : function(map, mapIndex) {
		var that = this;
		var sld = map.get("sld");
		if(sld) {
			var jsonStr = JSON.stringify(map.get("sld"));
			$.ajax({
				url : CONTEXT_PATH + "/cmmn/sld/putSLD.do",
				type : "POST",
				data : jsonStr,
				contentType : "text/plain"
			}).done(function(response) {
				if(response && response.sldKey) {
					var layer = map.getLayer("kc_wms");
					var source = layer.getSource();
					source.updateParams(that.getParams(map.get("sld"), response.sldKey));
				}
			});
		}
		else {
			var layer = map.getLayer("kc_wms");
			var source = layer.getSource();
			var params = null;
			if($("#map_container .a_land_register[data-index="+ (mapIndex+1) +"]").hasClass("selected")) {
				params = {LAYERS : caseUtils.getText(COMMON.LAND), STYLES : caseUtils.getText(COMMON.LAND), FORMAT : "image/png", nocache : Math.random(), SLD_KEY : null };
			}
			else {
				params = {LAYERS : serverObj.getBlankLayers(), STYLES : serverObj.getBlankLayers(), FORMAT : "image/png", nocache : Math.random(), SLD_KEY : null };
			}
			source.updateParams(params);
		}
	},
	
	/**
	 * WMS 파라미터 반환
	 * @returns {String} WMS 파라미터
	 */
	getParams : function(data, sldKey) {
		var that = this;
		var params = {
			LAYERS : caseUtils.getText(that.getLayerNames(data).join()),
			STYLES : caseUtils.getText(that.getLayerNames(data).join()),
			SLD_KEY : sldKey,
			nocache : Math.random()
		};
		return params;
	},
	
	/**
	 * 레이어 목록 반환 (기본 : visible 이 true 인 레이어 목록 반환)
	 * @param {Object} opt_options 옵션 (isAll : 전체 반환) 
	 * @returns {String} 레이어 목록
	 */
	getLayerNames : function(data, opt_options)
	{
		var options = opt_options || {};
		var layers = [];
		if (data.namedLayers)
		{
			for ( var i in data.namedLayers)
			{
				if(options.isAll) {
					layers.push(data.namedLayers[i].name);
				}
				else if (data.namedLayers[i].visible)
				{
					layers.push(data.namedLayers[i].name);
				}
			}
		}
		return layers;
	},
	
	triggerLandRegister : function(map, mapIndex) {
		var element = $("#map_container .a_land_register[data-index="+ (mapIndex+1) +"]");
		var sld = map.get("sld");
		if(sld) {
			var checked = false;
			for(var i=0, len=sld.namedLayers.length; i < len; i++) {
				var namedLayer = sld.namedLayers[i];
				if(namedLayer.name == COMMON.LAND) {
					if(namedLayer.visible) {
						checked = true;
					}
					break;
				}
			}
			
			if(checked) {
				element.addClass("selected");
				imageUtils.on(element.find("img"));
			}
			else {
				element.removeClass("selected");
				imageUtils.off(element.find("img"));
			}
		}
		else {
			if(!$("#map_container .a_land_register").hasClass("selected")) {
				element.removeClass("selected");
				imageUtils.off(element.find("img"));
			}
		}
	}
};