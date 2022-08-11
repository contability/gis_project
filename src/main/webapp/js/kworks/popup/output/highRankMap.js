var highRankMapObj = {
	
	map : null,
	
	scale : null,
	
	extent : null,
	
	flag : null,
	
	init : function(view, tms, center, scale, extent, flag) {
		var that = this;
		
		that.scale = scale;
		that.extent = extent;
		that.flag = flag;
		
		that.initUi();
		that.bindEvents();
		
		that.initGis(view, tms);
		that.moveMap(center, scale);
		
	},
	
	initUi : function() {
		// 축척바
		$("#txt_scale").numberspinner({
			min : 100,
			max : 483800,
			increment : 100
		});
	},
	
	bindEvents : function() {
		var that = this;
		// 축척 설정
		$("#a_scale").click(function() {
			var scale = $("#txt_scale").numberspinner("getValue");
			that.map.moveToScale(scale);
			return false;
		});
	},
	
	initGis : function(view, tms) {
		var that = this;
		
		var layers = [];
		if(tms) {
			layers = layers.concat(tms);
		}
		layers.push(new ol.layer.Group({
	    	id : "base_map_group"
	    }));
		layers.push(new ol.layer.Image({
			id : "kc_wms",
			source : new kworks.source.ImageWMS({
				url : CONTEXT_PATH + "/proxy/wms.do",
				params : $.extend(serverObj.getWMSParams(), sldObj.getParams()),
				ratio : 1,
				serverType : MAP.SERVER_TYPE
			})
		}));
		
		that.map = new kworks.Map({
			target : 'div_map',
			layers : layers,
			view : view
		});
		
		that.map.on("moveend", function(evt) {
			that.loadLegend();
			
			var scale = null;
			var extent = null;
			
			if(!that.flag){
				scale = evt.map.getScale();
				extent = evt.map.getExtent();
			}else{
				scale = that.scale;
				extent = that.extent;
			}
			
			// 20170105 / 윤중근 / 축척제한 1000 해제
			/*if(scale > 1000) {
				evt.map.moveToScale(1000);
			}*/
			that.loadScale(scale);
			that.loadExtent(extent);
		});
		
		/// 벡터 레이어 추가
		windowObj.drawToolObj.setMap(highRankMapObj.getMap());
		windowObj.drawToolObj.addLayer();
	},
	
	moveMap : function(center, scale) {
		var that = this;
		that.map.moveToCenter(center);
		that.map.moveToScale(scale);
		
	},
	
	getMap : function() {
		var that = this;
		return that.map;
	},
	
	/**
	 * 범례 표시
	 */
	loadLegend : function() {
		var that = this;
		var map = that.map;
		
		var tagStr = "";
		var layers = map.getLayer("kc_wms").getSource().getParams().LAYERS.split(",");
		for(var i in layers) {
			var name = layers[i].toUpperCase();
			var rules = sldObj.getNamedLayer(name).rules;
			for(var i in rules) {
				var rule = rules[i];
				if(rule.minScale && rule.maxScale) {
					if(map.getScale() >= rule.minScale && map.getScale() < rule.maxScale) {
						tagStr += that.createLegend(name, rule);
					}
				}
				else if(rule.maxScale) {
					if(map.getScale() < rule.maxScale) {
						tagStr += that.createLegend(name, rule);
					}
				}
				else if(rule.minScale) {
					if(map.getScale() >= rule.minScale) {
						tagStr += that.createLegend(name, rule);
					}
				}
				else {
					tagStr += that.createLegend(name, rule);
				}
			}
		}
		$("#div_legend ul").html(tagStr);
		
		// 심볼 표시
		$("#div_legend ul canvas").each(function() {
			var node = $(this);
			var layerName = node.attr("data-layer-name");
			var ruleName = node.attr("data-rule-name");
			var rule = sldObj.getRule(layerName, ruleName);
			if(rule) {
				legendObj.draw(node[0], rule);
			}
		});
		
	},
	
	/**
	 * 축척 표시
	 * @param {Number} scale 축척
	 */
	loadScale : function(scale) {
		if(scale) {
			scale = parseInt(scale);
			$("#txt_scale").numberspinner("setValue", parseInt(scale));
		}
	},
	
	loadExtent : function(extent){
		if(extent){
			$("#txt_extent").val(extent);
		}
	},
	
	/**
	 * 축척 변경
	 * @param {Number} scale 축척
	 */
	changeScale : function(scale) {
		var that = this;
		that.map.moveToScale(scale);
	},
	
	/**
	 * 범례 생성
	 */
	createLegend : function(layerName, rule) {
		var tagStr = "";
		tagStr += '<li>';
		tagStr += '<canvas data-layer-name="';
		tagStr += layerName;
		tagStr += '" data-rule-name="';
		tagStr += rule.name;
		tagStr += '" width="16" height="16"></canvas>';
		tagStr += rule.title;
		tagStr += '</li>';
		return tagStr;
	}
};