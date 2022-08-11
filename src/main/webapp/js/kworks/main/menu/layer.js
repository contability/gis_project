menuObj.layerObj = {
		
	/**
	 * 페이지 명
	 * @type {String}
	 */
	PAGE : "layer",
		
	selector : "#div_menu_panel_layer",
	
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "LayerMenu",
	
	TITLE : "레이어",
	
	isCreated : false,
	
	isChange : false,
	
	init : function() {
		var that = this;
		that.initUi();
		that.categoryObj.init(that);
		that.individualizationObj.init(that);
		that.userObj.init();
		
		that.addSldHandler();
		
		// 레이어 트리는 초기에 실행 후 닫음
		menuObj.close();
		that.isCreated = true;
	},
	
	initUi : function() {
		var that = this;
		// 분류별 설정 클릭 시 레이어 삭제 또는 레이어 설정한 내용이 있을 경우 트리를 다시 그림 
		$(".div_menu_panel_layer_tabs", that.selector).tabs({
			onSelect : function(title, index) {
				if(title == "분류별설정") {
					if(that.isChange) {
						that.categoryObj.reloadTree();
						that.isChange = false;
					}
				}
			}
		});
	},
	
	selectTab : function(title) {
		var that = this;
		$(".div_menu_panel_layer_tabs", that.selector).tabs("select", title);
	},
	
	resizeWindowHandler : function() {
		var that = this;
		var height = $("#div_menu_panel").height();
		var titleHeight = $("> .title", that.selector).height();
		var tagsHeight = $(".tabs-header", that.selector).height();
		var toolHeight = $(".div_tool", that.selector).height();
		var offset = 20;
		$(".div_tree", that.selector).height(height - titleHeight - tagsHeight - toolHeight - offset);
	},
	
	reloadTree : function() {
		var that = this;
		if(that.isCreated) {
			that.individualizationObj.reloadTree();
			that.categoryObj.reloadTree();
		}
	},
	
	reloadBaseMapTree : function() {
		var that = this;
		if(that.isCreated) {
			that.individualizationObj.reloadBaseMapTree();
			that.categoryObj.reloadBaseMapTree();
		}
	},
	
	addSldHandler : function() {
		var that = this;
		sldObj.addHandler(that.CLASS_NAME, function() {
			that.handlerSldChange();
		});
	},
	
	removeSldHandler : function() {
		var that = this;
		sldObj.removeHandler(that.CLASS_NAME);
	},
	
	handlerSldChange : function() {
		var that = this;
		if($(that.selector).is(":visible")) {
			that.reloadTree();
		}
		else {
			that.isChange = true;	
		}
	},
	
	/**
	 * 트리에서 발생한 SLD 변경 시에는 SLD 변경 핸들러 막기
	 */
	reloadWMS : function() {
		var that = this;
		that.removeSldHandler();
		mapObj.reloadWMS().done(function() {
			that.addSldHandler();
		});
	},
	
	reloadBaseMap : function() {
		themamapObj.basemapObj.show();
	},
	
	open : function() {
		var that = this;
		
		if(that.isChange) {
			that.reloadTree();
			that.isChange = false;
		}
		
		$(that.selector).show();
		// 화면 크기 변환 시 트리 영역 크기 조절 이벤트 등록
		mainObj.addResizeHandler("layerMenu", function() {
			that.resizeWindowHandler();
		});
		that.resizeWindowHandler();
	},
	
	close : function() {
		var that = this;
		$(that.selector).hide();
		mainObj.removeResizeHandler("layerMenu");
	}
		
};

//////////////////////////////////////////////////
//
// 개별설정
//
menuObj.layerObj.individualizationObj = {
	
	selector : "#div_menu_panel_layer .div_individualization_tab",
	
	TITLE : "레이어",
	
	parent : null,
	
	/**
	 * 전체 선택 / 전체 해제 이벤트 시 클릭 이벤트 수행 안되도록 함
	 */
	isCheck : true,
	
	init : function(parent) {
		var that = this;
		that.parent = parent;
		that.createTree();
		that.createBaseMapTree();
		that.bindEvents();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 레이어 추가
		$(".a_add_layer", that.selector).click(function() {
			var layers = sldObj.getLayerNames({
				isAll : true
			});
			
			var mapNos = themamapObj.basemapObj.getBaseMapNos();
			windowObj.layerObj.open({
				removeLayerIds : layers,
				removeMapNos : mapNos,
				onSelect : function(layers) {
					that.addLayers(layers);
				}
			});
		});
		
		// 레이어 순서조정
		$(".a_order_set", that.selector).click(function() {
			menuObj.layerObj.orderSetObj.open();
		});
		
		// 전체 확장
		$(".a_expand_all", that.selector).click(function() {
			$(".ul_layer_tree", that.selector).tree("expandAll");
			$(".ul_base_map_tree", that.selector).tree("expandAll");
		});
		
		// 전체 축소
		$(".a_collapse_all", that.selector).click(function() {
			$(".ul_layer_tree", that.selector).tree("collapseAll");
			$(".ul_base_map_tree", that.selector).tree("collapseAll");
		});
		
		// 전체 선택
		$(".a_check_all", that.selector).click(function() {
			that.isCheck = false;
			
			var nodes = $(".ul_layer_tree", that.selector).tree("getRoots");
			for(var i=0, len=nodes.length; i < len; i++) {
				var node = nodes[i];
				$(".ul_layer_tree", that.selector).tree("check", node.target);
			}
			sldObj.setVisible(true);
			that.parent.reloadWMS();
			
			menuObj.layerObj.categoryObj.triggerCheck(null, null, true);
			
			var nodes = $(".ul_base_map_tree", that.selector).tree("getRoots");
			for(var i=0, len=nodes.length; i < len; i++) {
				var node = nodes[i];
				$(".ul_base_map_tree", that.selector).tree("check", node.target);
			}
			themamapObj.basemapObj.setVisibleAll(true);
			that.parent.reloadBaseMap();
			
			menuObj.layerObj.categoryObj.triggerBaseMapCheck(null, true);
			
			that.isCheck = true;
		});
		
		// 전체 해제
		$(".a_uncheck_all", that.selector).click(function() {
			that.isCheck = false;
			
			var nodes = $(".ul_layer_tree", that.selector).tree("getRoots");
			for(var i=0, len=nodes.length; i < len; i++) {
				var node = nodes[i];
				$(".ul_layer_tree", that.selector).tree("uncheck", node.target);
			}
			sldObj.setVisible(false);
			that.parent.reloadWMS();
			
			menuObj.layerObj.categoryObj.triggerCheck(null, null, false);
			
			var nodes = $(".ul_base_map_tree", that.selector).tree("getRoots");
			for(var i=0, len=nodes.length; i < len; i++) {
				var node = nodes[i];
				$(".ul_base_map_tree", that.selector).tree("uncheck", node.target);
			}
			themamapObj.basemapObj.setVisibleAll(false);
			that.parent.reloadBaseMap();
			
			menuObj.layerObj.categoryObj.triggerBaseMapCheck(null, false);
			
			that.isCheck = true;
		});
		
	},
	
	bindTreeEvents : function() {
		var that = this;
		
		// 레이어 설정
		$(".ul_layer_tree li a.a_setting_layer", that.selector).unbind("click");
		$(".ul_layer_tree li a.a_setting_layer", that.selector).click(function() {
			var element = $(this);
			var layerId = element.attr("data-layer-id");
			var dataId = element.attr("data-layer-src");
			var title = element.attr("data-layer-title");
			
			windowObj.layerSettingObj.open(layerId, dataId, title, {
				onSubmit : function(rules, text) {
					sldObj.setRules(layerId, rules, text);
					that.parent.reloadWMS();
					
					// 레이어 하위 룰 노드 변경
					var layerNode = $(".ul_layer_tree", that.selector).tree("find", layerId);
					// 룰 목록 삭제
					var children = layerNode.children;
					for(var i=children.length-1; i >= 0; i--) {
						var child = children[i];
						var ruleNode = $(".ul_layer_tree", that.selector).tree("find", child.id);
						$(".ul_layer_tree", that.selector).tree("pop", ruleNode.target);
						$(".ul_layer_tree", that.selector).tree("remove", ruleNode.target);
					}
					
					// 룰 목록 추가
					var data = [];
					var namedLayer = sldObj.getNamedLayer(layerId);
					for(var i=0, len=namedLayer.rules.length; i < len; i++) {
						var rule = namedLayer.rules[i];
						data.push(that.createRuleNode(layerId, rule));
					}
					if(namedLayer.text) {
						data.push(that.createTextNode(layerId, namedLayer.text));
					}
					$(".ul_layer_tree", that.selector).tree("append", {
						parent : layerNode.target,
						data : data
					});
					
					that.drawLegends();
					that.bindTreeEvents();
					
					// 트리의 변경을 저장하여 분류별설정 선택  시 트리를 새로 그리도록 함
					that.parent.isChange = true;
				}
			});
			return false;
		});
		
		// 레이어 삭제
		$(".ul_layer_tree li a.a_remove_layer", that.selector).unbind("click");
		$(".ul_layer_tree li a.a_remove_layer", that.selector).click(function() {
			var target = $(this).parent().parent();
			
			var roots = $(".ul_layer_tree", that.selector).tree("getRoots");
			if(roots.length == 1) {
				$.messager.alert(that.TITLE, "삭제할 수 없습니다.레이어는 최소 한 개는 포함되어야 합니다.");
			}
			else {
				var node = $(".ul_layer_tree", that.selector).tree("getNode", target);
				sldObj.removeData(node.id);
				that.parent.reloadWMS();
				$(".ul_layer_tree", that.selector).tree("remove", target);
				that.parent.isChange = true;
			}
			
			return false;
		});
	},
	
	createTree : function() {
		var that = this;

		var data = that.createTreeData();
		
		$(".ul_layer_tree", that.selector).tree({
			data : data,
			dnd : true,
			animate : true,
			checkbox : true,
			onBeforeDrag : function(node) {
				// 레이어 타입만 이동 가능
				if(node.type != "layer") {
					return false;
				}
			},
			onDragOver : function(targetNode, source, point) {
				var target = $(".ul_layer_tree", that.selector).tree('getNode', targetNode);
				return that.isMove(source.type, target.type, point);
			},
			onBeforeDrop : function(targetNode, source, point) {
				var target = $(".ul_layer_tree", that.selector).tree('getNode', targetNode);
				return that.isMove(source.type, target.type, point);
			},
			onDrop : function(targetNode, source, point) {
				$("#div_loading").show();
				
				var target = $(".ul_layer_tree", that.selector).tree('getNode', targetNode);
				if(!that.isMove(source.type, target.type, point)) {
					return false;
				}
				
				sldObj.changeLayerOrder(source.id, target.id, point);
				that.drawLegends();
				that.bindTreeEvents();
				
				
				that.parent.reloadWMS();
				
				$("#div_loading").hide();
			},
			onCheck : function(node, checked) {
				if(!that.isCheck) {
					return false;
				}
				if(node.type == "layer") {
					sldObj.setNamedLayerVisible(node.id, checked);
					menuObj.layerObj.categoryObj.triggerCheck(node.id, null, checked);
				}
				else if(node.type == "rule") {
					sldObj.setRuleVisible(node.layerId, node.ruleId, checked);
					menuObj.layerObj.categoryObj.triggerCheck(node.layerId, node.ruleId, checked);
				}
				else if(node.type == "text") {
					sldObj.setTextVisible(node.layerId, checked);
					menuObj.layerObj.categoryObj.triggerCheck(node.layerId, "Label", checked);
				}
				that.parent.reloadWMS();
			}
		});
		
		that.drawLegends();
		that.bindTreeEvents();
	},
	
	createBaseMapTree : function() {
		var that = this;
			
		$(".ul_base_map_tree", that.selector).tree({
			data : that.createBaseMapTreeData(),
			dnd : true,
			animate : true,
			checkbox : true,
			onClick : function(node) {
				if(node.type == "opacity") {
					$(".easyui-numberspinner", node.target).numberspinner("textbox").focus();
				}
			},
			onBeforeDrag : function(node) {
				if(node.type != "baseMap") {
					return false;
				}
			},
			onDragOver : function(targetNode, source, point) {
				var target = $(".ul_base_map_tree", that.selector).tree('getNode', targetNode);
				return that.isMove(target.type, source.type, point);
			},
			onBeforeDrop : function(targetNode, source, point) {
				var target = $(".ul_base_map_tree", that.selector).tree('getNode', targetNode);
				return that.isMove(target.type, source.type, point);
			},
			onDrop : function(targetNode, source, point) {
				$("#div_loading").show();
				
				var target = $(".ul_base_map_tree", that.selector).tree('getNode', targetNode);
				if(!that.isMove(target.type, source.type, point)) {
					return false;
				}
				
				themamapObj.basemapObj.changeLayerOrder(source.mapNo, target.mapNo, point);
				that.initBaseMapTreeUi([themamapObj.basemapObj.getBaseMapByMapNo(source.mapNo)]);
				that.parent.reloadBaseMap();
				

				$("#div_loading").hide();
				
			},
			onCheck : function(node, checked) {
				if(!that.isCheck) {
					return false;
				}
				if(node.type == "baseMap") {
					themamapObj.basemapObj.setVisible(node.mapNo, checked);
					menuObj.layerObj.categoryObj.triggerBaseMapCheck(node.mapNo, checked);
				}
				that.parent.reloadBaseMap();
			}
		});
		
		that.initBaseMapTreeUi(themamapObj.basemapObj.getData());
	},
	
	initBaseMapTreeUi : function(baseMaps) {
		var that = this;
		for(var i=0, len=baseMaps.length; i < len; i++) {
			var node = $(".div_base_map_node_" + baseMaps[i].mapNo, that.selector);
			var opacity = sldObj.convertOpacityToPercent(baseMaps[i].opacity);
			
			$(".easyui-numberspinner", node).numberspinner({
				value : opacity,
				width : 60,
				min : 0,
				max : 100,
				onChange : function(newValue, oldValue) {
					var parentNode = $(this).parent().parent();
					if(newValue != oldValue) {
						$(".easyui-slider", parentNode).slider("setValue", newValue);
					}
				}
			});
			
			$(".easyui-slider", node).slider({
				value : opacity,
				width : 80,
				onChange : function(newValue, oldValue) {
					var parentNode = $(this).parent().parent();
					if(newValue != oldValue) {
						$(".easyui-numberspinner", parentNode).numberspinner("setValue", newValue);
					}
				}
			});
			
			$(".easyui-linkbutton", node).linkbutton({
				onClick : function() {
					var node = $(this);
					var divNode = node.parent().parent();
					var mapNo = divNode.attr("data-map-no");
					var percent = $(".easyui-numberspinner", divNode).numberspinner("getValue");
					var opacity = sldObj.convertOpacityToValue(percent);
					themamapObj.basemapObj.setOpacity(mapNo, opacity);
					that.parent.reloadBaseMap();
				}
			});
		}
		
		var treeNode = $(".div_base_map_node", that.selector).parent().parent();
		treeNode.height(30);
		treeNode.find(".tree-checkbox").hide();
		
		// 레이어 삭제
		$(".ul_base_map_tree li a.a_remove_layer", that.selector).unbind("click");
		$(".ul_base_map_tree li a.a_remove_base_map", that.selector).click(function() {
			var element = $(this);
			var mapNo = element.attr("data-map-no");
			
			themamapObj.basemapObj.removeByMapNo(mapNo);
			that.parent.reloadBaseMap();
			that.parent.reloadBaseMapTree();

			return false;
		});
	},
	
	isMove : function(sourceType, targetType , point) {
		if(point == "append") {
			return false;
		}
		else if(sourceType != targetType) {
			return false;
		}
		else {
			return true;
		}
	},
	
	drawLegends : function() {
		var that = this;
		var data = sldObj.getData();
		for(var i=0, len=data.namedLayers.length; i < len; i++) {
			var namedLayer = data.namedLayers[i];
			var layerId = namedLayer.name;
			for(var j=0, jLen=namedLayer.rules.length; j < jLen; j++) {
				var rule = namedLayer.rules[j];
				var ruleId = rule.name;
				var canvas = $(".canvas_legend[data-layer-id="+layerId+"][data-rule-id="+ruleId+"]", that.selector);
				if(canvas.length == 1) {
					legendObj.draw(canvas[0], rule);
				}
			}
			
			if(namedLayer.text) {
				var canvas = $(".canvas_text[data-layer-id="+layerId+"]", that.selector);
				if(canvas.length == 1) {
					legendObj.drawText(canvas[0].getContext("2d"), namedLayer.text);
				}
			}
		}
	},
	
	createTreeData : function() {
		var that = this;
		var treeData = [];
		var data = sldObj.getData();
		for(var i=0, len=data.namedLayers.length; i < len; i++) {
			var namedLayer = data.namedLayers[i];
			treeData.push(that.createLayerNode(namedLayer));
		}
		return treeData.reverse();
	},
	
	createBaseMapTreeData : function() {
		var baseMaps = themamapObj.basemapObj.getData();
		
		var data = [];
		if(baseMaps && baseMaps.length > 0) {
			for(var i=0, len=baseMaps.length; i < len; i++) {
				var mapNo = baseMaps[i].mapNo;
				var type = themamapObj.basemapObj.getTypeByMapNo(mapNo);
				if(!type) continue;
				
				var text = "<span class='span_text'> " + themamapObj.basemapObj.getTypeName(type) + "</span>";
				text += "<a class='f_r a_remove_base_map' href='#' data-map-no='" + mapNo + "'><img src='" + CONTEXT_PATH + "/images/kworks/map/common/boomark_icon1_ov.png' alt='삭제' /></a>";
				
				var opacityText = "<div class='div_base_map_node div_base_map_node_" + mapNo + "' data-map-no='" + mapNo + "' >";
				opacityText += "<span><input class='easyui-slider' value='' /></span>";
				opacityText += "<span class='span_value'><input class='easyui-numberspinner' value='0' /></span>";
				opacityText += "<span class='span_apply'><a class='easyui-linkbutton'>적용</a></span>";
				opacityText += "</div>";
				
				var obj = {
					id : "layer_base_map_" + mapNo,
					mapNo : mapNo,
					text :  text,
					state : "closed",
					type : "baseMap",
					checked : baseMaps[i].visible,
					children : [{
						id : "opacity_" + type,
						text : opacityText,
						type : "opacity"
					}]
				};
				data.push(obj);
			}
		}
		
		return data.reverse();
	},
	
	createLayerNode : function(namedLayer) {
		var that = this;
		var layerId = namedLayer.name;
		var dataId = namedLayer.source;
		if (!dataId || dataId.length <= 0)
			dataId = layerId;
		
		var text = null;
		if(namedLayer.title.length >= 13){
			text = "<span class='span_text' title='" + namedLayer.title + "'>" + namedLayer.title.substr(0,12) + "..." + "</span>";
		}
		else{
			text = "<span class='span_text'>" + namedLayer.title + "</span>";
		}

		text += "<a class='f_r a_remove_layer' href='#' data-layer-id='" + layerId + "'><img src='" + CONTEXT_PATH + "/images/kworks/map/common/boomark_icon1_ov.png' alt='삭제' /></a>";
		text += "<a class='f_r a_setting_layer' href='#' data-layer-id='" + layerId + "' data-layer-src='" + dataId + "' data-layer-title='" + namedLayer.title + "'><img src='" + CONTEXT_PATH + "/images/kworks/map/common/layer_icon2_ov.png' alt='설정' /></a>";
		
		var layerNode = {
			id : layerId,
			source : dataId,
			text :  text,
			state : "closed",
			type : "layer",
			children : []
		};
		
		// 룰 노드 생성 및 추가
		for(var j=0, jLen=namedLayer.rules.length; j < jLen; j++) {
			var rule = namedLayer.rules[j];
			layerNode.children.push(that.createRuleNode(layerId, rule));
		}
		
		// 라벨 노드 추가
		if(namedLayer.text) {
			layerNode.children.push(that.createTextNode(layerId, namedLayer.text));
		}
		
		return layerNode;
	},
	
	createRuleNode : function(layerId, rule) {
		var ruleId = rule.name;
		var nodeId = layerId + "_" + ruleId;
		var text = "<canvas class='canvas_legend' data-layer-id='"+layerId+"' data-rule-id='"+ruleId+"' width='16' height='16' ></canvas>";
		text += "<span class='span_text'>" + rule.title + "</span>";
		
		var ruleNode = {
			id : nodeId,
			text : text,
			layerId : layerId,
			ruleId : ruleId,
			checked : rule.visible,
			type : "rule"
		};
		return ruleNode;
	},
	
	createTextNode : function(layerId, textRule) {
		var text = "<canvas class='canvas_text' data-layer-id='"+layerId+"' width='16' height='16' ></canvas>";
		text += "<span class='span_text'> Label</span>";
		var labelNode = {
			id : layerId + "_" + "Label",
			text : text,
			layerId : layerId,
			checked : textRule.visible,
			type : "text"
		};
		return labelNode;
	},
	
	addLayers : function(layers) {
		var that = this;
		var layerIds = [];
		var baseMapNos = [];
		for(var i in layers) {
			var layer = layers[i];
			if(layer.type == "BASE_MAP") {
				var obj = {
					mapNo : layer.id,
					opacity : 1.0,
					visible : true
				};
				baseMapNos.push(obj);
			}
			else {
				layerIds.push(layers[i].id);
			}
		}
		
		if(layerIds.length > 0) {
			sldObj.addDatas(layerIds);
			that.parent.reloadTree();
			that.parent.reloadWMS();
		}
		
		if(baseMapNos.length > 0) {
			themamapObj.basemapObj.add(baseMapNos);
			
			that.parent.reloadBaseMapTree();
			that.parent.reloadBaseMap();
		}
	},
	
	reloadTree : function() {
		var that = this;
		$(".ul_layer_tree", that.selector).tree("loadData", that.createTreeData());
		that.drawLegends();
		that.bindTreeEvents();
	},
	
	reloadBaseMapTree : function() {
		var that = this;
		$(".ul_base_map_tree", that.selector).tree("loadData", that.createBaseMapTreeData());
		that.initBaseMapTreeUi(themamapObj.basemapObj.getData());
	},
	
	triggerCheck : function(layerId, ruleId, checked) {
		var that = this;
		
		that.isCheck = false;
		
		if(layerId) {
			var id = "";
			if(ruleId) {
				id = layerId + "_" + ruleId;
			}
			else {
				id = layerId;
			}
			var node = $(".ul_layer_tree", that.selector).tree("find", id);
			if(checked) {
				$(".ul_layer_tree", that.selector).tree("check", node.target);
			}
			else {
				$(".ul_layer_tree", that.selector).tree("uncheck", node.target);
			}
		}
		else {
			var nodes = $(".ul_layer_tree", that.selector).tree("getRoots");
			for(var i=0, len=nodes.length; i < len; i++) {
				var node = nodes[i];
				if(checked) {
					$(".ul_layer_tree", that.selector).tree("check", node.target);
				}
				else {
					$(".ul_layer_tree", that.selector).tree("uncheck", node.target);
				}
			}
		}
		
		that.isCheck = true;
	},
	
	triggerBaseMapCheck : function(mapNo, checked) {
		var that = this;
		
		that.isCheck = false;
		
		if(mapNo) {
			var id = "layer_base_map_" + mapNo;
			var node = $(".ul_base_map_tree", that.selector).tree("find", id);
			if(checked) {
				$(".ul_base_map_tree", that.selector).tree("check", node.target);
			}
			else {
				$(".ul_base_map_tree", that.selector).tree("uncheck", node.target);
			}
		}
		else {
			var nodes = $(".ul_base_map_tree", that.selector).tree("getRoots");
			for(var i=0, len=nodes.length; i < len; i++) {
				var node = nodes[i];
				if(checked) {
					$(".ul_base_map_tree", that.selector).tree("check", node.target);
				}
				else {
					$(".ul_base_map_tree", that.selector).tree("uncheck", node.target);
				}
			}
		}
		
		that.isCheck = true;
	}
	
};

/////////////////////////////////////////////////////////
//
// 분류별 설정
//
menuObj.layerObj.categoryObj = {
	
	selector : "#div_menu_panel_layer .div_category_tab",
	
	TITLE : "레이어",
	
	parent : null,
	
	/**
	 * 전체 선택 / 전체 해제 이벤트 시 클릭 이벤트 수행 안되도록 함
	 */
	isCheck : true,
		
	init : function(parent) {
		var that = this;
		that.parent = parent;
		that.createTree();
		that.createBaseMapTree();
		that.bindEvents();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 레이어 추가
		$(".a_add_layer", that.selector).click(function() {
			var layers = sldObj.getLayerNames({
				isAll : true
			});
			
			var mapNos = themamapObj.basemapObj.getBaseMapNos();
			windowObj.layerObj.open({
				removeLayerIds : layers,
				removeMapNos : mapNos,
				onSelect : function(layers) {
					that.addLayers(layers);
				}
			});
		});
		
		// 전체 확장
		$(".a_expand_all", that.selector).click(function() {
			$(".ul_layer_tree2", that.selector).tree("expandAll");
			$(".ul_base_map_tree2", that.selector).tree("expandAll");
		});
		
		// 전체 축소
		$(".a_collapse_all", that.selector).click(function() {
			$(".ul_layer_tree2", that.selector).tree("collapseAll");
			
			$(".ul_base_map_tree2", that.selector).tree("collapseAll");
		});
		
		// 전체 선택
		$(".a_check_all", that.selector).click(function() {
			that.isCheck = false;
			
			var nodes = $(".ul_layer_tree2", that.selector).tree("getRoots");
			for(var i=0, len=nodes.length; i < len; i++) {
				var node = nodes[i];
				$(".ul_layer_tree2", that.selector).tree("check", node.target);
			}
			sldObj.setVisible(true);
			that.parent.reloadWMS();
			
			menuObj.layerObj.individualizationObj.triggerCheck(null, null, true);
			
			var nodes = $(".ul_base_map_tree2", that.selector).tree("getRoots");
			for(var i=0, len=nodes.length; i < len; i++) {
				var node = nodes[i];
				$(".ul_base_map_tree2", that.selector).tree("check", node.target);
			}
			themamapObj.basemapObj.setVisibleAll(true);
			that.parent.reloadBaseMap();
			
			menuObj.layerObj.individualizationObj.triggerBaseMapCheck(null, true);
			
			that.isCheck = true;
		});
		
		// 전체 해제
		$(".a_uncheck_all", that.selector).click(function() {
			that.isCheck = false;
			
			var nodes = $(".ul_layer_tree2", that.selector).tree("getRoots");
			for(var i=0, len=nodes.length; i < len; i++) {
				var node = nodes[i];
				$(".ul_layer_tree2", that.selector).tree("uncheck", node.target);
			}
			sldObj.setVisible(false);
			that.parent.reloadWMS();
			
			menuObj.layerObj.individualizationObj.triggerCheck(null, null, false);
			
			var nodes = $(".ul_base_map_tree2", that.selector).tree("getRoots");
			for(var i=0, len=nodes.length; i < len; i++) {
				var node = nodes[i];
				$(".ul_base_map_tree2", that.selector).tree("uncheck", node.target);
			}
			
			themamapObj.basemapObj.setVisibleAll(false);
			that.parent.reloadBaseMap();
			
			menuObj.layerObj.individualizationObj.triggerBaseMapCheck(null, false);
			
			that.isCheck = true;
		});
		
	},
	
	bindTreeEvents : function() {
		var that = this;
		
		// 레이어 설정
		$(".ul_layer_tree2 li a.a_setting_layer", that.selector).unbind("click");
		$(".ul_layer_tree2 li a.a_setting_layer", that.selector).click(function() {
			var element = $(this);
			var layerId = element.attr("data-layer-id");
			var dataId = element.attr("data-layer-src");
			var title = element.attr("data-layer-title");
			
			windowObj.layerSettingObj.open(layerId, dataId, title, {
				onSubmit : function(rules, text) {
					sldObj.setRules(layerId, rules, text);
					that.parent.reloadWMS();
					
					// 레이어 하위 룰 노드 변경
					var layerNode = $(".ul_layer_tree2", that.selector).tree("find", layerId);
					// 룰 목록 삭제
					var children = layerNode.children;
					for(var i=children.length-1; i >= 0; i--) {
						var child = children[i];
						var ruleNode = $(".ul_layer_tree2", that.selector).tree("find", child.id);
						$(".ul_layer_tree2", that.selector).tree("pop", ruleNode.target);
						$(".ul_layer_tree2", that.selector).tree("remove", ruleNode.target);
					}
					
					// 룰 목록 추가
					var data = [];
					var namedLayer = sldObj.getNamedLayer(layerId);
					for(var i=0, len=namedLayer.rules.length; i < len; i++) {
						var rule = namedLayer.rules[i];
						data.push(that.createRuleNode(layerId, rule));
					}
					if(namedLayer.text) {
						data.push(that.createTextNode(layerId, namedLayer.text));
					}
					$(".ul_layer_tree2", that.selector).tree("append", {
						parent : layerNode.target,
						data : data
					});
					
					that.drawLegends();
					that.bindTreeEvents();
					
					// 트리의 변경을 저장하여 분류별설정 선택  시 트리를 새로 그리도록 함
					that.parent.isChange = true;
				}
			});
			return false;
		});
		
		// 레이어 삭제
		$(".ul_layer_tree2 li a.a_remove_layer", that.selector).unbind("click");
		$(".ul_layer_tree2 li a.a_remove_layer", that.selector).click(function() {
			var target = $(this).parent().parent();
			
			var roots = $(".ul_layer_tree2", that.selector).tree("getRoots");
			if(roots.length == 1) {
				$.messager.alert(that.TITLE, "삭제할 수 없습니다.레이어는 최소 한 개는 포함되어야 합니다.");
			}
			else {
				var node = $(".ul_layer_tree2", that.selector).tree("getNode", target);
				sldObj.removeData(node.id);
				that.parent.reloadWMS();
				$(".ul_layer_tree2", that.selector).tree("remove", target);
				that.parent.isChange = true;
			}
			
			return false;
		});
	},
	
	createTree : function() {
		var that = this;

		var data = that.createTreeData();
		
		$(".ul_layer_tree2", that.selector).tree({
			data : data,
			checkbox : true,
			onCheck : function(node, checked) {
				if(!that.isCheck) {
					return false;
				}
				
				if(node.type == "category") {
					for(var i=0, len=node.children.length; i < len; i++) {
						var layerNode = node.children[i];
						sldObj.setNamedLayerVisible(layerNode.id, checked);
						menuObj.layerObj.individualizationObj.triggerCheck(layerNode.id, null, checked);
					}
				}
				else if(node.type == "layer") {
					sldObj.setNamedLayerVisible(node.id, checked);
					menuObj.layerObj.individualizationObj.triggerCheck(node.id, null, checked);
					
					if(node.id.indexOf('BML_PROP_AS') > -1){
						var element = $('#a_quick_menu_prop');
						var imgElement = element.find('img');
						
						if(!checked){
							imageUtils.off(imgElement);
						}else{
							imageUtils.on(imgElement);
						}
					}else if(node.id.indexOf('BML_BUID_AS') > -1){
						var element = $('#a_quick_menu_buid');
						var imgElement = element.find('img');
						
						if(!checked){
							imageUtils.off(imgElement);
						}else{
							imageUtils.on(imgElement);
						}
					}else if(node.id.indexOf('BML_LOAN_AS') > -1){
						var element = $('#a_quick_menu_loan');
						var imgElement = element.find('img');
						
						if(!checked){
							imageUtils.off(imgElement);
						}else{
							imageUtils.on(imgElement);
						}
					}else if(node.id.indexOf('BML_OCCP_AS') > -1){
						var element = $('#a_quick_menu_occp');
						var imgElement = element.find('img');
						
						if(!checked){
							imageUtils.off(imgElement);
						}else{
							imageUtils.on(imgElement);
						}
					}else if(node.id.indexOf('V_BML_ACEX_AS') > -1){
						var element = $('#a_quick_menu_acex');
						var imgElement = element.find('img');
						
						if(!checked){
							imageUtils.off(imgElement);
						}else{
							imageUtils.on(imgElement);
						}
					}
					
				}
				else if(node.type == "rule") {
					sldObj.setRuleVisible(node.layerId, node.ruleId, checked);
					menuObj.layerObj.individualizationObj.triggerCheck(node.layerId, node.ruleId, checked);
				}
				else if(node.type == "text") {
					sldObj.setTextVisible(node.layerId, checked);
					menuObj.layerObj.individualizationObj.triggerCheck(node.layerId, "Label", checked);
				}
				that.parent.reloadWMS();
			}
		});
		
		that.drawLegends();
		that.bindTreeEvents();
	},
	
	createBaseMapTree : function() {
		var that = this;
		var treeData = that.createBaseMapTreeData();
		
		$(".ul_base_map_tree2", that.selector).tree({
			data : treeData,
			checkbox : true,
			onClick : function(node) {
				if(node.type == "opacity") {
					$(".easyui-numberspinner", node.target).numberspinner("textbox").focus();
				}
			},
			onCheck : function(node, checked) {
				if(!that.isCheck) {
					return false;
				}
				
				if(node.type == "category") {
					for(var i=0, len=node.children.length; i < len; i++) {
						var layerNode = node.children[i];
						themamapObj.basemapObj.setVisible(layerNode.mapNo, checked);
						menuObj.layerObj.individualizationObj.triggerBaseMapCheck(layerNode.mapNo, checked);
					}
				}
				else if(node.type == "baseMap") {
					themamapObj.basemapObj.setVisible(node.mapNo, checked);
					menuObj.layerObj.individualizationObj.triggerBaseMapCheck(node.mapNo, checked);
				}
				that.parent.reloadBaseMap();
			}
		});
		
		that.initBaseMapTreeUi(themamapObj.basemapObj.getData());
	},
	
	initBaseMapTreeUi : function(baseMaps) {
		var that = this;
		for(var i=0, len=baseMaps.length; i < len; i++) {
			var node = $(".div_base_map_node_" + baseMaps[i].mapNo, that.selector);
			var opacity = sldObj.convertOpacityToPercent(baseMaps[i].opacity);
			
			$(".easyui-numberspinner", node).numberspinner({
				value : opacity,
				width : 50,
				min : 0,
				max : 100,
				onChange : function(newValue, oldValue) {
					var parentNode = $(this).parent().parent();
					if(newValue != oldValue) {
						$(".easyui-slider", parentNode).slider("setValue", newValue);
					}
				}
			});
			
			$(".easyui-slider", node).slider({
				value : opacity,
				width : 70,
				onChange : function(newValue, oldValue) {
					var parentNode = $(this).parent().parent();
					if(newValue != oldValue) {
						$(".easyui-numberspinner", parentNode).numberspinner("setValue", newValue);
					}
				}
			});
			
			$(".easyui-linkbutton", node).linkbutton({
				onClick : function() {
					var node = $(this);
					var divNode = node.parent().parent();
					var mapNo = divNode.attr("data-map-no");
					var percent = $(".easyui-numberspinner", divNode).numberspinner("getValue");
					var opacity = sldObj.convertOpacityToValue(percent);
					themamapObj.basemapObj.setOpacity(mapNo, opacity);
					that.parent.reloadBaseMap();
				}
			});
		}
		
		var treeNode = $(".div_base_map_node2", that.selector).parent().parent();
		treeNode.height(30);
		treeNode.find(".tree-checkbox").hide();
		
		// 레이어 삭제
		$(".ul_base_map_tree2 li a.a_remove_layer", that.selector).unbind("click");
		$(".ul_base_map_tree2 li a.a_remove_base_map", that.selector).click(function() {
			var element = $(this);
			var mapNo = element.attr("data-map-no");
			
			themamapObj.basemapObj.removeByMapNo(mapNo);
			that.parent.reloadBaseMap();
			that.parent.reloadBaseMapTree();

			return false;
		});
	},	
	
	drawLegends : function() {
		var that = this;
		var data = sldObj.getData();
		for(var i=0, len=data.namedLayers.length; i < len; i++) {
			var namedLayer = data.namedLayers[i];
			var layerId = namedLayer.name;
			for(var j=0, jLen=namedLayer.rules.length; j < jLen; j++) {
				var rule = namedLayer.rules[j];
				var ruleId = rule.name;
				var canvas = $(".canvas_legend[data-layer-id="+layerId+"][data-rule-id="+ruleId+"]", that.selector);
				if(canvas.length == 1) {
					legendObj.draw(canvas[0], rule);
				}
			}
			
			if(namedLayer.text) {
				var canvas = $(".canvas_text[data-layer-id="+layerId+"]", that.selector);
				if(canvas.length == 1) {
					legendObj.drawText(canvas[0].getContext("2d"), namedLayer.text);
				}
			}
		}
	},
	
	createTreeData : function() {
		var that = this;
		var treeData = [];

		var categories = {};

		var data = sldObj.getData();
		for(var i=0, len=data.namedLayers.length; i < len; i++) {
			// 레이어 노드 생성
			var namedLayer = data.namedLayers[i];
			var layerId = namedLayer.name;
			var dataId = namedLayer.source;
			if (!dataId || dataId.length <= 0)
				dataId = layerId;
			
//			var text = "<span class='span_text'>" + namedLayer.title + "</span>";
			var text = null;
			if(namedLayer.title.length >= 12){
				text = "<span class='span_text' title='" + namedLayer.title + "'>" + namedLayer.title.substr(0,11) + "..." + "</span>";
			}
			else{
				text = "<span class='span_text'>" + namedLayer.title + "</span>";
			}

			text += "<a class='f_r a_remove_layer' href='#' data-layer-id='" + layerId + "'><img src='" + CONTEXT_PATH + "/images/kworks/map/common/boomark_icon1_ov.png' alt='삭제' /></a>";
			text += "<a class='f_r a_setting_layer' href='#' data-layer-id='" + layerId + "' data-layer-src='" + dataId + "' data-layer-title='" + namedLayer.title + "'><img src='" + CONTEXT_PATH + "/images/kworks/map/common/layer_icon2_ov.png' alt='설정' /></a>";
			
			var layerNode = {
				id : layerId,
				source : dataId,
				text :  text,
				state : "closed",
				type : "layer",
				children : []
			};
			
//			// 룰 노드 생성 및 추가
//			for(var j=0, jLen=namedLayer.rules.length; j < jLen; j++) {
//				var rule = namedLayer.rules[j];
//				var ruleId = namedLayer.rules[j].name;
//				var text = "<canvas class='canvas_legend' data-layer-id='"+layerId+"' data-rule-id='"+ruleId+"' width='16' height='16' ></canvas>";
//				text += "<span class='span_text'>" + rule.title + "</span>";
//				
//				var ruleNode = {
//					id : layerId + "_" + ruleId,
//					text : text,
//					layerId : layerId,
//					ruleId : ruleId,
//					checked : rule.visible,
//					type : "rule"
//				};
//				layerNode.children.push(ruleNode);
//			}
//			
//			// 라벨 노드 추가
//			if(namedLayer.text) {
//				var text = "<canvas class='canvas_text' data-layer-id='"+layerId+"' width='16' height='16' ></canvas>";
//				text += "<span class='span_text'> Label</span>";
//				var labelNode = {
//					id : layerId + "_" + "Label",
//					text : text,
//					layerId : layerId,
//					checked : namedLayer.text.visible,
//					type : "text"
//				};
//				layerNode.children.push(labelNode);
//			}
//			
//			var layer = layerObj.getLayerByLayerName(layerId);
//			if(layer) {
//				var categoryName = layer.lyrCtgryId;
//				if(!categories[categoryName]) {
//					var category = layerObj.getLayerCategoryByCategoryName(categoryName);
//					categories[categoryName] = {
//						id : categoryName,
//						text : category.lyrCtgryNm,
//						type : "category",
//						children : []
//					};
//				}
//				categories[categoryName].children.push(layerNode);
//			}
//		}
//		
//		for(var i in categories) {
//			treeData.push(categories[i]);
//		}
//		return treeData;

			// 룰 노드 생성 및 추가
			for(var j=0, jLen=namedLayer.rules.length; j < jLen; j++) {
				var rule = namedLayer.rules[j];
				layerNode.children.push(that.createRuleNode(layerId, rule));
			}
			
			// 라벨 노드 추가
			if(namedLayer.text) {
				layerNode.children.push(that.createTextNode(layerId, namedLayer.text));
			}

			var layer = layerObj.getLayerByLayerName(layerId);
			if(layer) {
				var categoryName = layer.lyrCtgryId;
				if(!categories[categoryName]) {
					var category = layerObj.getLayerCategoryByCategoryName(categoryName);
					categories[categoryName] = {
						id : categoryName,
						text : category.lyrCtgryNm,
						type : "category",
						children : []
					};
				}
				categories[categoryName].children.push(layerNode);
			}
		}
		
		for(var i in categories) {
			treeData.push(categories[i]);
		}
		return treeData;
	},
		
	createRuleNode : function(layerId, rule) {
		var ruleId = rule.name;
		var nodeId = layerId + "_" + ruleId;
		var text = "<canvas class='canvas_legend' data-layer-id='"+layerId+"' data-rule-id='"+ruleId+"' width='16' height='16' ></canvas>";
		text += "<span class='span_text'>" + rule.title + "</span>";
		
		var ruleNode = {
			id : nodeId,
			text : text,
			layerId : layerId,
			ruleId : ruleId,
			checked : rule.visible,
			type : "rule"
		};
		return ruleNode;
	},
	
	createTextNode : function(layerId, textRule) {
		var text = "<canvas class='canvas_text' data-layer-id='"+layerId+"' width='16' height='16' ></canvas>";
		text += "<span class='span_text'> Label</span>";
		var labelNode = {
			id : layerId + "_" + "Label",
			text : text,
			layerId : layerId,
			checked : textRule.visible,
			type : "text"
		};
		return labelNode;
	},
			
	createBaseMapTreeData : function() {
		var baseMaps = themamapObj.basemapObj.getData();
		
		var data = [];
		if(baseMaps && baseMaps.length > 0) {
			var obj = {
				id : "category_base_map",
				text : "항공사진",
				type : "category",
				children : []
			};
			data.push(obj);
			
			for(var i=0, len=baseMaps.length; i < len; i++) {
				var mapNo = baseMaps[i].mapNo;
				var type = themamapObj.basemapObj.getTypeByMapNo(mapNo);
				if(!type) continue;
				
				var text = '';
				
				// 항공사진 제목이 길 경우 뒷 부분 생략
				if(type.length > 13){
					replaceType = type.replace(type.substring(14,type.length),'...');
					text += "<span class='span_text' title='"+type+"'> " + themamapObj.basemapObj.getTypeName(replaceType) + "</span>";
				}else{
                    text += "<span class='span_text'>" + themamapObj.basemapObj.getTypeName(type) + "</span>";
				}
				
				text += "<a class='f_r a_remove_base_map' href='#' data-map-no='" + mapNo + "'><img src='" + CONTEXT_PATH + "/images/kworks/map/common/boomark_icon1_ov.png' alt='삭제' /></a>";
				
				var opacityText = "<div class='div_base_map_node2 div_base_map_node_" + mapNo + "' data-map-no='" + mapNo + "' >";
				opacityText += "<span><input class='easyui-slider' value='' /></span>";
				opacityText += "<span class='span_value'><input class='easyui-numberspinner' value='0' /></span>";
				opacityText += "<span class='span_apply'><a class='easyui-linkbutton'>적용</a></span>";
				opacityText += "</div>";
				
				var map = {
					id : "layer_base_map_" + mapNo,
					mapNo : mapNo,
					text :  text,
					type : "baseMap",
					checked : baseMaps[i].visible,
					children : [{
						id : "opacity_" + type,
						text : opacityText,
						type : "opacity"
					}]					
				};
				obj.children.push(map);
			}
		}
		
		return data;
	},
	
	addLayers : function(layers) {
		var that = this;
		var layerIds = [];
		var baseMapNos = [];
		for(var i in layers) {
			var layer = layers[i];
			if(layer.type == "BASE_MAP") {
				var obj = {
					mapNo : layer.id,
					opacity : 1.0,
					visible : true
				};
				baseMapNos.push(obj);
			}
			else {
				layerIds.push(layers[i].id);
			}
		}
		
		if(layerIds.length > 0) {
			sldObj.addDatas(layerIds);
			that.parent.reloadTree();
			that.parent.reloadWMS();
		}
		
		if(baseMapNos.length > 0) {
			themamapObj.basemapObj.add(baseMapNos);
			
			that.parent.reloadBaseMapTree();
			that.parent.reloadBaseMap();
		}
	},
	
	reloadTree : function() {
		var that = this;
		$(".ul_layer_tree2", that.selector).tree("loadData", that.createTreeData());
		that.drawLegends();
		that.bindTreeEvents();
	},

	reloadBaseMapTree : function() {
		var that = this;
		$(".ul_base_map_tree2", that.selector).tree("loadData", that.createBaseMapTreeData());
		that.initBaseMapTreeUi(themamapObj.basemapObj.getData());
	},
	
	triggerCheck : function(layerId, ruleId, checked) {
		var that = this;
		
		that.isCheck = false;
		
		if(layerId) {
			var id = "";
			if(ruleId) {
				id = layerId + "_" + ruleId;
			}
			else {
				id = layerId;
			}
			var node = $(".ul_layer_tree2", that.selector).tree("find", id);
			if(checked) {
				$(".ul_layer_tree2", that.selector).tree("check", node.target);
			}
			else {
				$(".ul_layer_tree2", that.selector).tree("uncheck", node.target);
			}
		}
		else {
			var nodes = $(".ul_layer_tree2", that.selector).tree("getRoots");
			for(var i=0, len=nodes.length; i < len; i++) {
				var node = nodes[i];
				if(checked) {
					$(".ul_layer_tree2", that.selector).tree("check", node.target);
				}
				else {
					$(".ul_layer_tree2", that.selector).tree("uncheck", node.target);
				}
			}
		}
		
		that.isCheck = true;
	},
	
	triggerBaseMapCheck : function(mapNo, checked) {
		var that = this;
		
		that.isCheck = false;
		
		if(mapNo) {
			var id = "layer_base_map_" + mapNo;
			var node = $(".ul_base_map_tree2", that.selector).tree("find", id);
			if(checked) {
				$(".ul_base_map_tree2", that.selector).tree("check", node.target);
			}
			else {
				$(".ul_base_map_tree2", that.selector).tree("uncheck", node.target);
			}
		}
		else {
			var nodes = $(".ul_base_map_tree2", that.selector).tree("getRoots");
			for(var i=0, len=nodes.length; i < len; i++) {
				var node = nodes[i];
				if(checked) {
					$(".ul_base_map_tree2", that.selector).tree("check", node.target);
				}
				else {
					$(".ul_base_map_tree2", that.selector).tree("uncheck", node.target);
				}
			}
		}
		
		that.isCheck = true;
	}

};

/////////////////////////////////////////////////////////
//
// 사용자
//
menuObj.layerObj.userObj = {
	
	selector : "#div_menu_panel_layer .div_user_tab",
	
	TITLE : "레이어",
	
	init : function() {
		var that = this;
		that.load().done(function(response) {
			that.addLayers(response.rows);
			that.createTree(response.rows);
			that.bindEvents();
			that.bindTreeEvent();
		}).fail(function() {
			$.messager.alert(that.TITLE, "사용자 레이어 목록을 불러오는데 실패했습니다.");
		});
	},
	
	load : function() {
		var url = CONTEXT_PATH + "/rest/userLayer/listAll.do";
		return $.get(url);
	},
	
	createTree : function(rows) {
		var that = this;
	
		var data = that.createTreeData(rows);
		
		$(".ul_layer_tree", that.selector).tree({
			data : data,
			checkbox : true,
			onCheck : function(node, checked) {
				if(node.type == "Group") {
					var children = node.children;
					for(var i=0, len=children.length; i < len; i++) {
						var child = children[i];
						that.visible(child.id, checked);
					}
				}
				else if(node.type == "Layer") {
					that.visible(node.id, checked);
				}
			},
			onSelect : function(node, checked) {
				if(node && node.type == "Layer" && node.id) {
					that.move(node.id);
				}
			}
		});
		
	},
	
	addLayers : function(rows) {
		var that = this;
		for(var i=0, len=rows.length; i < len; i++) {
			var row = rows[i];
			that.addUserLayer(row, false);
		}
	},
	
	addUserLayer : function(row, visible) {
		var format = new ol.format.GeoJSON();
		var features = format.readFeatures(row.geojson);
		
		var from = ol.proj.get(MAP.PROJECTION);
		var to = mapObj.getMap().getView().getProjection();
		
		for(var i=0, len=features.length; i < len; i++) {
			if(features[i].getProperties().type == "Circle") {
				features[i] = spatialUtils.convertPointToCircle(features[i]);
			}
			if(from != to) {
				var geometry = features[i].getGeometry();
				features[i].setGeometry(spatialUtils.transform(geometry, from ,to));
			}
		}
		
		var source = new ol.source.Vector({features : features});
		var layer = new ol.layer.Vector({
			id : "user_" + row.lyrNo,
			source : source,
			style : function(feature) {
				return windowObj.drawToolObj.createStyle(feature.getProperties());
			},
			visible : visible
		});
		mapObj.getMap().addLayer(layer);
	},
	
	addImportLayer : function(row) {
		var that = this;
		var source = new ol.source.Vector({features : row.features});
		
		var color = that.getRandomColor();
		var rule = {
			point : {
				resource : "DEFAULT_SYMBOL.png",
				size : 15,
				anchor : "cm"
			},
			line : {
				stroke : color,
				strokeOpacity : 0.7,
				strokeWidth : 2,
				strokeDasharray : "solid",
			},
			polygon : {
				stroke : color,
				strokeOpacity : 0.7,
				strokeWidth : 1,
				strokeDasharray : "solid",
				fill : color,
				fillOpacity : 0.7
			}	
		};
		
		var layer = new ol.layer.Vector({
			id : row.id,
			source : source,
			rule : rule,
			style : that.createStyle(rule)
		});
		mapObj.getMap().addLayer(layer);
	},
	
	createStyle : function(rule) {
		return new ol.style.Style({
			fill : new ol.style.Fill({
				color : colorUtils.toColorFromHex(rule.polygon.fill, rule.polygon.fillOpacity)
			}),
			stroke : new ol.style.Stroke({
				color : colorUtils.toColorFromHex(rule.line.stroke, rule.line.strokeOpacity),
				width : rule.line.strokeWidth,
				lineDash : canvasUtils.toLineDash(rule.line.strokeDasharray, rule.line.strokeWidth)
			}),
			image : new ol.style.Icon({
				src: MAP.SYMBOL_URL + "/" + rule.point.resource,
				anchor : canvasUtils.toAnchor(rule.point.anchor)
			})
		});
	},
	
	createPolygonStyle : function(rule) {
		return new ol.style.Style({
			fill : new ol.style.Fill({
				color : colorUtils.toColorFromHex(rule.polygon.fill, rule.polygon.fillOpacity)
			}),
		});
	},
	
	createLineStyle : function(rule) {
		return new ol.style.Style({
			stroke : new ol.style.Stroke({
				color : colorUtils.toColorFromHex(rule.line.stroke, rule.line.strokeOpacity),
				width : rule.line.strokeWidth,
				lineDash : canvasUtils.toLineDash(rule.line.strokeDasharray, rule.line.strokeWidth)
			}),
		});
	},
	
	/**
	 * 랜덤 색상 생성
	 * @return {String} 색상
	 */
	getRandomColor : function() {
		var color = "rgba(";
		color += Math.floor(Math.random() * 256);
		color += ", ";
		color += Math.floor(Math.random() * 256);
		color += ", ";
		color += Math.floor(Math.random() * 256);
		color += ", 0.7)";
		return color;
	},
	
	createTreeData : function(rows) {
		var that = this;
		
		var data = [];
		var drawData = {
	    	id : "Draw",
	    	text : "그리기",
	    	state : "open",
	    	type : "Group",
	    	children : []
	    };
		
		for(var i=0, len=rows.length; i < len; i++) {
			var row = rows[i];
			drawData.children.push(that.createUserLayerObj(row, false));
		}
		data.push(drawData);
		
		var importData = {
	    	id : "Import",
	    	text : "가져오기",
	    	state : "open",
	    	type : "Group",
	    	children : []
	    };
		data.push(importData);
		
		return data;
	},
	
	createUserLayerObj : function(row, checked) {
		var id = "user_" + row.lyrNo;
		var text = "<span class='span_text'>" + row.lyrNm + "</span>";
		text += "<a class='f_r a_remove_layer ma_r_5' href='#' data-id='" + id + "' data-layer-id='" + row.lyrNo + "'><img src='" + CONTEXT_PATH + "/images/kworks/map/common/boomark_icon1_ov.png' alt='삭제' /></a>";
		var obj = {
			id : id,
			text : text,
			type : "Layer",
			checked : checked
		};
		return obj;
	},
	
	createImportLayerObj : function(row) {
		var id = row.id;
		var text = "<span class='span_text'>" + row.name + "</span>";
		text += "<a class='f_r a_remove_layer ma_r_5' href='#' data-id='" + id + "'><img src='" + CONTEXT_PATH + "/images/kworks/map/common/boomark_icon1_ov.png' alt='삭제' /></a>";
		text += "<a class='f_r a_setting_layer' href='#' data-id='" + id + "'><img src='" + CONTEXT_PATH + "/images/kworks/map/common/layer_icon2_ov.png' alt='설정' /></a>";
		var obj = {
			id : id,
			text : text,
			type : "Layer",
			checked : true
		};
		return obj;
	},
	
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 전체 확장
		$(".a_expand_all", that.selector).click(function() {
			$(".ul_layer_tree", that.selector).tree("expandAll");
		});
		
		// 전체 축소
		$(".a_collapse_all", that.selector).click(function() {
			$(".ul_layer_tree", that.selector).tree("collapseAll");
		});
		
		// 전체 선택
		$(".a_check_all", that.selector).click(function() {
			var nodes = $(".ul_layer_tree", that.selector).tree("getRoots");
			for(var i=0, len=nodes.length; i < len; i++) {
				var node = nodes[i];
				if(!node.checked) {
					$(".ul_layer_tree", that.selector).tree("check", node.target);
				}
			}
		});
		
		// 전체 해제
		$(".a_uncheck_all", that.selector).click(function() {
			var nodes = $(".ul_layer_tree", that.selector).tree("getRoots");
			for(var i=0, len=nodes.length; i < len; i++) {
				var node = nodes[i];
				$(".ul_layer_tree", that.selector).tree("uncheck", node.target);
			}
		});
	},
	
	/**
	 * 트리 이벤트 연결
	 */
	bindTreeEvent : function() {
		var that = this;
		
		// 레이어 설정
		$(".ul_layer_tree li a.a_setting_layer", that.selector).unbind("click");
		$(".ul_layer_tree li a.a_setting_layer", that.selector).click(function() {
			var element = $(this);
			var id = element.attr("data-id");
			that.setting(id);
			return false;
		});
		
		// 레이어 삭제
		$(".ul_layer_tree li a.a_remove_layer", that.selector).unbind("click");
		$(".ul_layer_tree li a.a_remove_layer", that.selector).click(function() {
			var element = $(this);
			var id = element.attr("data-id");
			var userLyrId = element.attr("data-layer-id");
			that.remove(id, userLyrId);
			return false;
		});
	},
	
	/**
	 * 화면 표시
	 * @param {String} id 레이어 아이디
	 * @param {boolean} checked 화면 표시 여부
	 */
	visible : function(id, checked) {
		mapObj.getMap().getLayer(id).setVisible(checked);
	},
	
	/**
	 * 레이어 위치로 이동
	 * @param {String} id 레이어 아이디
	 */
	move : function(id) {
		var layer = mapObj.getMap().getLayer(id);
		mapObj.getMap().moveToExtent(layer.getSource().getExtent());
	},
	
	addUserLayerNode : function(row) {
		var that = this;

		that.addUserLayer(row, true);

		var drawNode = $(".ul_layer_tree", that.selector).tree("find", "Draw");
		var data = that.createUserLayerObj(row, true);
		var target = drawNode.target;
		$(".ul_layer_tree", that.selector).tree("append", { data : data, parent : target });
		that.bindTreeEvent();
	},
	
	addImportLayerNode : function(row) {
		var that = this;
		that.addImportLayer(row);

		if($(".ul_layer_tree", that.selector).length > 0) {
			var importNode = $(".ul_layer_tree", that.selector).tree("find", "Import");
			var data = that.createImportLayerObj(row);
			var target = importNode.target;
			$(".ul_layer_tree", that.selector).tree("append", { data : data, parent : target });
			that.bindTreeEvent();
		}
	},
	
	setting : function(id) {
		var that = this;
		var layer = mapObj.getMap().getLayer(id);
		if(layer && layer.getSource() && layer.getSource().getFeatures() && layer.getSource().getFeatures().length > 0 ) {
			var feature = layer.getSource().getFeatures()[0];
			var geometry = feature.getGeometry();
			var rule = layer.get("rule");
			
			if(geometry instanceof ol.geom.Point) {
				windowObj.symbolSettingObj.open(rule, {
					onSubmit : function(ruleObj) {
						layer.set("rule", ruleObj);
						layer.setStyle(that.createStyle(ruleObj));
					}
				});
			}
			else if(geometry instanceof ol.geom.LineString || geometry instanceof ol.geom.MultiLineString) {
				windowObj.lineSettingObj.open(rule, {
					noDirection : true,
					onSubmit : function(ruleObj) {
						
						layer.set("rule", ruleObj);
						layer.setStyle(that.createStyle(ruleObj));
					}
				});
			}
			else if(geometry instanceof ol.geom.Polygon || geometry instanceof ol.geom.MultiPolygon || geometry instanceof ol.geom.Circle) {
				windowObj.polygonSettingObj.open(rule, {
					onSubmit : function(ruleObj) {
						if(($("#chk_window_polygon_setting_fill").is(":checked"))&&(!$("#chk_window_line_setting_stroke").is(":checked"))) {
							// Lks : 채우기 O, 선 X
							ruleObj.polygon.fill;
							ruleObj.polygon.fillOpacity;
							
							ruleObj.polygon.stroke = null;
							if(ruleObj.polygon.stroke == null) {
								layer.set("rule", ruleObj);
								layer.setStyle(that.createPolygonStyle(ruleObj));
							}
						}
						else if((!$("#chk_window_polygon_setting_fill").is(":checked"))&&($("#chk_window_line_setting_stroke").is(":checked"))) {
							// Lks : 채우기 X, 선 O
							ruleObj.line.stroke = ruleObj.polygon.stroke; 
							ruleObj.line.strokeOpacity = ruleObj.polygon.strokeOpacity;
							ruleObj.line.strokeWidth = ruleObj.polygon.strokeWidth;
							ruleObj.line.strokeDasharray = ruleObj.polygon.strokeDasharray;
							
							ruleObj.polygon.fill = null;
							ruleObj.polygon.fillOpacity = null;
							
							if(ruleObj.polygon.fill == null) {
								layer.set("rule", ruleObj);
								layer.setStyle(that.createLineStyle(ruleObj));
							}
						}
						else if(($("#chk_window_polygon_setting_fill").is(":checked"))&&($("#chk_window_line_setting_stroke").is(":checked"))) {
							// Lks : 채우기 O, 선 O
							ruleObj.line.stroke = ruleObj.polygon.stroke;
							ruleObj.line.strokeOpacity = ruleObj.polygon.strokeOpacity;
							ruleObj.line.strokeWidth = ruleObj.polygon.strokeWidth;
							ruleObj.line.strokeDasharray = ruleObj.polygon.strokeDasharray;
							
							layer.set("rule", ruleObj);
							layer.setStyle(that.createStyle(ruleObj));
						}
						else {
							$.messager.alert("채우기 설정과 외곽선 설정 중에 하나를 설정하여 주십시오.");
							return;
						}
					}
				});
			}
			else {
				console.log("정의되지 않은 타입 입니다.");
			}
			
		}
	},
	
	/**
	 * 레이어 제거
	 * @param {String} id 레이어 아이디
	 * @param {Long} userLyrId 사용자 레이어 아이디 
	 */
	remove : function(id, userLyrId) {
		var that = this;
		$.messager.confirm(that.TITLE, "선택하신 레이어를 삭제하시겠습니까?", function(isTrue) {
			if(isTrue) {
				if(userLyrId) {
					var url = CONTEXT_PATH + "/rest/userLayer/" + userLyrId + "/remove.do";
					$.post(url).done(function(response) {
						if(response && response.rowCount) {
							mapObj.getMap().removeLayer(mapObj.getMap().getLayer(id));
							var node = $(".ul_layer_tree", that.selector).tree("find", id);
							$(".ul_layer_tree", that.selector).tree("remove", node.target);
						}
						else {
							$.messager.alert(that.TITLE, "사용자 레이어를 삭제하는데 실패 했습니다.");
						}
					}).fail(function() {
						$.messager.alert(that.TITLE, "사용자 레이어를 삭제하는데 실패 했습니다.");
					});
				}
				else {
					mapObj.getMap().removeLayer(mapObj.getMap().getLayer(id));
					var node = $(".ul_layer_tree", that.selector).tree("find", id);
					$(".ul_layer_tree", that.selector).tree("remove", node.target);
				}
			}
		});
	}
};

/**
 * 레이어 순서조정 객체
 * @type {Object}
 * @author GittGDS_YJG
 */
 menuObj.layerObj.orderSetObj = 
{
	
	/**
	 * 제목
	 * @type {String}
	 */
	title : "레이어 순서조정",
		
	/**
	 * 클래스 명
	 * @type {String}
	 */
	className : "LayerOrderSet",
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "",
	
	/**
	 * 생성여부
	 */
	isCreated : false,
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 레이어 선택
		$("ul", that.selector).on("click", "li", function(){
			var element = this;
			$("ul li", that.selector).removeClass("bg_blue1");
			$(element).addClass("bg_blue1");
		});

		// 최상단으로 이동
		$(that.selector).on("click", ".a_layer_order_first", function() {
			that.first();
			return false;
		});

		$(that.selector).on("click", ".a_layer_order_up", function() {
			that.up();
			return false;
		});
		
		$(that.selector).on("click", ".a_layer_order_down", function() {
			that.down();
			return false;
		});
		
		$(that.selector).on("click", ".a_layer_order_last", function() {
			that.last();
			return false;
		});
		
		$(that.selector).on("click", ".a_layer_order_set_save", function() {
			that.save();
			return false;
		});
		
		$(that.selector).on("click", ".a_layer_order_set_cancel", function() {
			that.close();
			return false;
		});
		
	},
	
	/**
	 * 레이어 순서조정 팝업창 열기
	 */
	open : function() {
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
				
		var url = CONTEXT_PATH + "/window/layerOrderSet/page.do";
		var windowOptions = {
			width : 300,
			height : 415,
			top : 180,
			left : 350,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.className, that.title, url, null, windowOptions, function() {
			that.bindEvents();
			that.getData();
			
			if(EXTENSIONS.AERIAL_PHOTO || PROJECT_CODE == 'yy') {	//양양 지적원도 타일링 개발 서버 적용 코드
			//기존 코드
			//if(EXTENSIONS.AERIAL_PHOTO) {
				if(themamapObj.basemapObj.getData().length > 0) {
					$(".ul_layer_ordr_list", that.selector).height(200);
					that.loadBaseMap();
				}
				else {
					$(".ul_base_map_ordr_list", that.selector).hide();	
				}
			}
			else {
				$(".ul_base_map_ordr_list", that.selector).hide();
			}
		});
		
		that.selector = "#" + id;
		that.isCreated = true;
	},
	
	getData : function() {
		var that = this;
		var namedLayers = sldObj.getData().namedLayers;
		
		var liStr = "";
		for(var i=namedLayers.length - 1; i>-1; i--) {
			liStr += that.makeLi(namedLayers[i].name, namedLayers[i].title);
		}
		
		$(".ul_layer_ordr_list", that.selector).html(liStr);
	},
	
	loadBaseMap : function() {
		var that = this;
		var data = themamapObj.basemapObj.getData();
		var liStr = "";
		for(var i=data.length-1; i >= 0; i--) {
			var mapNo = data[i].mapNo;
			var type = themamapObj.basemapObj.getTypeByMapNo(mapNo);
			if(!type) continue;
			
			var name = themamapObj.basemapObj.getTypeName(type);
			liStr += that.makeLi(mapNo, name);
		}
		$(".ul_base_map_ordr_list", that.selector).html(liStr);
	},
	
	/**
	 * LI 태그 생성
	 */
	makeLi : function(name, text) {
		var liStr = "";
		
		liStr += "<li class='pa_l_10 pa_t_10 h_20' data='" + name + "'>";
		liStr += text;
		liStr += "</li>";
		
		return liStr;
	},
	
	/**
	 * LI 태그 선택시 클래스 추가 생성 (bg_blue1)
	 */
	makeSelectLi : function(name, text) {
		var liStr = "";
		
		liStr += "<li class='pa_l_10 pa_t_10 h_20 bg_blue1' data='" + name + "'>";
		liStr += text;
		liStr += "</li>";
		
		return liStr;
	},
	
	/**
	 * 최상단으로 이동
	 */
	first : function() {
		var that = this;
		var node = $("li.bg_blue1", that.selector);
		var ulNode = node.parent();
		ulNode.prepend(node.detach());
	},
	
	/**
	 * 위로 이동
	 */
	up : function() {
		var that = this;
		var node = $("li.bg_blue1", that.selector);
		var prevNode = $("li.bg_blue1", that.selector).prev();
		if(prevNode.length > 0) {
			prevNode.before(node.detach());
		}
		else {
			$.messager.alert("레이어 순서 조정", "최상단 입니다.");
		}
	},
	
	/**
	 * 아래로 이동
	 */
	down : function() {
		var that = this;
		var node = $("li.bg_blue1", that.selector);
		var nextNode = $("li.bg_blue1", that.selector).next();
		if(nextNode.length > 0) {
			nextNode.after(node.detach());
		}
		else {
			$.messager.alert("레이어 순서 조정", "최하단 입니다.");
		}
	},
	
	/**
	 * 제일 아래로 이동
	 */
	last : function() {
		var that = this;
		var node = $("li.bg_blue1", that.selector);
		var ulNode = node.parent();
		ulNode.append(node.detach());
	},
	
	/**
	 * 저장
	 */
	save : function() {
		var that = this;
		
		var namedLayers = sldObj.getData().namedLayers;
		var namedLayersArray = new Array();
		$(".ul_layer_ordr_list li", that.selector).each(function() {
			var node = $(this);
			var dataNm = node.attr("data");
			for(var i = namedLayers.length-1; i >= 0; i--) {
				if(dataNm == namedLayers[i].name){
					namedLayersArray.push(namedLayers[i]);
				}
			}
		});
		sldObj.getData().namedLayers = namedLayersArray.reverse();
		
		menuObj.layerObj.individualizationObj.reloadTree();
		menuObj.layerObj.reloadWMS();
		
		if(EXTENSIONS.AERIAL_PHOTO || PROJECT_CODE == 'yy') {	//양양 지적원도 타일링 개발 서버 적용 코드
		//기존 코드
		//if(EXTENSIONS.AERIAL_PHOTO) {
			var baseMaps = [];
			$(".ul_base_map_ordr_list li", that.selector).each(function() {
				var node = $(this);
				var mapNo = node.attr("data");
				var baseMapObj = themamapObj.basemapObj.getBaseMapByMapNo(mapNo);
				baseMaps.push(baseMapObj);
			});
			themamapObj.basemapObj.data = baseMaps.reverse();
			
			menuObj.layerObj.individualizationObj.reloadBaseMapTree();
			menuObj.layerObj.reloadBaseMap();
		}
		
		that.close();
	},
	
	/**
	 * 정리
	 */
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		
		return false;
	}
};