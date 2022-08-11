/**
 * 지적원도
 */
menuObj.cadastralMapObj = {

	/**
	 * 타이틀
	 */
	TITLE : "지적원도",

	/**
	 * 페이지
	 */
	PAGE : "cadastralMap",
	
	/**
	 * 선택자
	 */
	selector : "#div_menu_panel_cadastral",
	
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "CadastralMapMenu",
	
	/**
	 * 생성여부
	 */
	isCreated : false,
	
	/**
	 * 타일맵 영역 레이어
	 */
	layer : null,
	
	/**
	 * 타일맵 영역 피쳐소스
	 */
	source : null,
	
	/**
	 * 지적원도 타일
	 */
	tiles : null,
	
	/**
	 * 맵 이벤트 키
	 */
	eventKey : null,
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		
		that.initGis();
		that.initUi();
		that.bindEvents();
		that.loadDong();
		that.open();
		
		that.isCreated = true;
	},
	
	/**
	 * GIS 초기화
	 */
	initGis : function() {
		var that = this;
		
		that.tiles = cadastralObj.getTiles();
		that.source = cadastralObj.getVectorSource();
		that.layer = cadastralObj.getVectorLayer();
		mapObj.getMap().addLayer(that.layer);
		
		// 속초 드론영상은 CheckBox false로 시작
		if(PROJECT_CODE == 'sc' || PROJECT_CODE == 'sunchang'){
			for(var i = 0; i < that.tiles.length; i++){
				that.tiles[i].visible = false;
			}
		}
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		//속초 드론 영상은 법정동 검색을 필요로 하지 않는다. 
		if(PROJECT_CODE == 'sc' || PROJECT_CODE == 'sunchang'){
			$(".div_search").css("display","none");
			$(".div_title", that.selector).html("드론영상 목록");
			$(that.selector).attr("title","드론영상");
		}else{
			// 법정동 목록
			$(".sel_dong", that.selector).combobox({
				required : true,
				valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONGLI_CODE_FIELD),
				textField : camelcaseUtils.underbarToCamelcase(COMMON.DONGLI_NAME_FIELD),
				width : 160,
				onSelect : function(record) {
					that.loadLi(record);
				}
			});

			$(".sel_li", that.selector).combobox({
				valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
				textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD),
				width : 160
			});
			
			// 본번
			$(".txt_mnnm", that.selector).numberspinner({
				min : 1,
				max : 9999,
				increment : 1,
				width : 160
			});
			
			// 부번
			$(".txt_slno", that.selector).numberspinner({
				min : 0,
				max : 9999,
				increment : 1,
				width : 160
			});
			
			// 검색
			$(".a_search", that.selector).linkbutton({
				iconCls : "icon-search"
			});
		}
		
		
		that.createCadastralTree();
	},
	
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		if(PROJECT_CODE != 'sc' && PROJECT_CODE != 'sunchang' ){
			// 읍면동에서 엔터 시 리로 이동
			$(".sel_dong", that.selector).combobox("textbox").keyup(function(e) {
				var keyCode = e.keyCode ? e.keyCode : e.which;
				if (keyCode == 13) {
					$(".sel_li", that.selector).combobox("textbox").focus();
				}
			});
	
			// 리에서 엔터 시 본번으로 이동
			$(".sel_li", that.selector).combobox("textbox").keyup(function(e) {
				var keyCode = e.keyCode ? e.keyCode : e.which;
				if (keyCode == 13) {
					$(".txt_mnnm", that.selector).numberspinner("textbox").focus();
				}
			});
			
			// 본번에서 엔터 시 부번으로 이동
			$(".txt_mnnm", that.selector).numberspinner("textbox").keyup(function(e) {
				var keyCode = e.keyCode ? e.keyCode : e.which;
				if (keyCode == 13) {
					$(".txt_slno", that.selector).numberspinner("textbox").focus();
				}
			});
			
			// 부번에서 엔터 시 검색
			$(".txt_slno", that.selector).numberspinner("textbox").keyup(function(e) {
				var keyCode = e.keyCode ? e.keyCode : e.which;
				if (keyCode == 13) {
					$(".a_search", that.selector).trigger("click");
				}
			});
			
			// 검색
			$(".a_search", that.selector).click(function() {
				if(that.validator()) {
					that.search();
				}
				return false;
			});
		}else{
			// 드론 영상 목록 클릭 시 위치 이동 (JQuery)
			
			$(".ul_cadastral_tree", that.selector).tree({
				onClick : function(node) {
					var tileNo = node.tileNo;
					var tiles = that.tiles;
					var features = [];
					
					if(!node.checked){
						for(var i = 0; i < tiles.length; i++){
							if(tileNo == tiles[i].tileNo){
								tiles[i].visible = true;
							}
						}
					}

					if (tileNo) {
						var feature = that.source.getFeatureById(tileNo);
						features.push(feature);
					}

					highlightObj.moveFeatures(features, {
						projection : ol.proj.get(MAP.PROJECTION)
					});
				}
			});
		}
		
		// 마우스 오버
		$(".ul_cadastral_tree", that.selector).on("mouseover", "li .tree-node", function() {
			var node = $(this);
			var childNode = node.context.lastChild.lastChild;
			var tileNo = childNode.getAttribute("tile-no");
			var feature = that.source.getFeatureById(tileNo);
			if(feature) {
				feature.setStyle(
					new ol.style.Style({
						fill : new ol.style.Fill({
							color : 'rgba(255, 94, 0, 0.2)'
						}),
						stroke : new ol.style.Stroke({
							color : 'rgba(255, 94, 0, 0.5)',
							width : 4
						})
					})
				);
			}
		});

		// 마우스 아웃
		$(".ul_cadastral_tree", that.selector).on("mouseout", "li .tree-node", function() {
			var node = $(this);
			var childNode = node.context.lastChild.lastChild;
			var tileNo = childNode.getAttribute("tile-no");
			var feature = that.source.getFeatureById(tileNo);
			if(feature) {
				feature.setStyle(
					new ol.style.Style({
						fill : new ol.style.Fill({
							color : 'rgba(255, 94, 0, 0.0)'
						}),
						stroke : new ol.style.Stroke({
							color : 'rgba(255, 94, 0, 0.0)',
							width : 4
						})
					})
				);
			}
		});
		
		
	},
	
	/**
	 * 동 목록 불러오기
	 */
	loadDong : function() {
		var that = this;
		
		if(PROJECT_CODE != 'sc' && PROJECT_CODE != 'sunchang'){
			dongLiObj.getPromise().done(function() {
				var data = dongLiObj.getData();
				$(".sel_dong", that.selector).combobox("loadData", data);
				$(".sel_dong", that.selector).combobox("select", data[0].emdCd);
			});
		}
	},

	/**
	 * 리 목록 불러오기
	 */
	loadLi : function(selData) {
		var that = this;

		var code = selData.emdCd;
		var filter = {
			filterType : "COMPARISON",
			comparisonType : "LIKE",
			property : "BJD_CDE",
			value : code+"%",
			sortOrdr : "BJD_NAM",
			isOriginColumnValue : true
		};
		
		spatialSearchUtils.listAll(that.TITLE, liObj.getDataId(), filter, function(data) {
			if(data) {
				$(".sel_li", that.selector).combobox("loadData", data);
				$(".sel_li", that.selector).combobox("select", data[0].bjdCde);
			}
		}, {
			isNoMessage : true
		});
	},
	
	/**
	 * 입력 값 검증
	 * @returns {Boolean} 검증 여부
	 */
	validator : function() {
		var that = this;
		
		// 법정동
		if(!$(".sel_dong", that.selector).combobox("isValid")) {
			$.messager.alert(that.TITLE, "읍면동을 선택하여 주십시오.");
			return false;
		}
		
		return true;
	},

	/**
	 * 지적검색
	 */
	search : function() {
		var that = this;

		// 동 코드 
		//var dongCode = $(".sel_dong", that.selector).combobox("getValue");
		var liCode = $(".sel_li", that.selector).combobox("getValue");
		
		var mntn = $(".chk_mntn", that.selector).is(":checked")?"2":"1";
		var mnnm = $(".txt_mnnm", that.selector).numberspinner("getValue");
		var slno = $(".txt_slno", that.selector).numberspinner("getValue");
		var pnu = pnuObj.createPnuForLike(liCode, mntn, mnnm, slno);
		
		var filter = {
			filterType : "COMPARISON",
			comparisonType : "LIKE",
			property : "PNU",
			value : pnu,
			pageIndex : 1,
			recordCountPerPage : 10,
			pageSize : 5,
			sortOrdr : "PNU"
		};
		
		spatialSearchUtils.list(that.TITLE, COMMON.LAND, filter, function(rows, pagination) {
			if (!rows || rows.length <= 0)
				return;

			// 무조건 첫번째 결과의 위치로 이동
			var row = rows[0];
			var format = new ol.format.WKT();
			var feature = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);
			var projection = ol.proj.get(MAP.PROJECTION);
			
			if(projection) {
				var mapProjection = mapObj.getMap().getView().getProjection();
				feature.setGeometry(spatialUtils.transform(feature.getGeometry(), projection, mapProjection));
			}

			var map = mapObj.getMap();
			var extent = feature.getGeometry().getExtent();
			
			var center = null;
			var resolution = null;
			if(extent[0] == extent[2] && extent[1] == extent[3]) {
				resolution = map.convertScaleToResolution(1000);
				center = [extent[0], extent[1]];
			}
			else {
				var view = map.getView();
				resolution = view.constrainResolution(view.getResolutionForExtent(extent, map.getSize()), -2);
				center = ol.extent.getCenter(extent);
			}
			
			var offsetWidth = menuObj.getPanelWidth()==0?0:menuObj.getPanelWidth()/2;
			var offsetHeight = resultObj.getHeight()==0?0:resultObj.getHeight()/2;
			
			var offsetX = resolution * offsetWidth;
			var offsetY = resolution * offsetHeight;
			map.moveToCenter([center[0]-offsetX, center[1]-offsetY]);
			map.moveToResolution(resolution);
		}, null);
		
	},
	
	/**
	 * 지적원도 트리생성
	 */
	createCadastralTree : function() {
		var that = this;
		
		$(".ul_cadastral_tree", that.selector).tree({
			data : [],
			animate : true,
			checkbox : true,
			onCheck : function(node, checked) {
				if(node.type == "cadastral") {
					var tile = cadastralObj.getTile(node.tileNo);
					
					tile[0].visible = checked;
					tile[0].layer.setVisible(checked);
				}
			}
		});
		
		that.reloadTree();
	},
	
	/**
	 * 트리노드 생성
	 */
	createTreeData : function() {
		var that = this;
		
		var result = {};
		var data = [];
		var layers = new ol.Collection();
		
		var maxScale = parseInt(CADASTRALMAP_SCALE);
		
		var map = mapObj.getMap();
		var scale = map.getScale();
		var overlay = map.getLayer("overlay_group");
			if(scale > maxScale) {
				overlay.setVisible(false);
				overlay.setLayers(layers);
	
				result.ty = "msg";
				result.data = '<div class="div_content">' + maxScale + '이하의 축척에서만 사용 가능합니다.</div>';
				
				return result; 
			}
			else {
				overlay.setVisible(true);
			}
			
		
		var tiles = that.tiles;
		var extent = map.getExtent();
		
		for(var i=0; i < tiles.length; i++) {
			var tile = tiles[i];
			var tileNo = tile.tileNo;
			
			if(PROJECT_CODE == 'sc' || PROJECT_CODE == 'sunchang'){
				var text = "<span class='span_text' tile-no='" + tileNo + "'> " + tile.title + "</span>";
				
				var opacityText = "<div class='div_cadastral_node div_cadastral_node_" + tileNo + "' tile-no='" + tileNo + "' >";
				opacityText += "<span><input class='easyui-slider' value='' /></span>";
				opacityText += "<span class='span_value'><input class='easyui-numberspinner' value='0' /></span>";
				opacityText += "</div>";

				var obj = {
					id : "cadastral_" + tileNo,
					tileNo : tileNo,
					text :  text,
					state : "closed",
					type : "cadastral",
					checked : tile.visible,
					children : [{
						id : "opacity_" + tileNo,
						text : opacityText,
						type : "opacity"
					}]
				};
				
				data.push(obj);
				
				tile.layer.setVisible(tile.visible);
				layers.push(tile.layer);
			}else{
				if(ol.extent.intersects(extent, tile.extent)) {
					var text = "<span class='span_text' tile-no='" + tileNo + "'> " + tile.title + "</span>";
					
					var opacityText = "<div class='div_cadastral_node div_cadastral_node_" + tileNo + "' tile-no='" + tileNo + "' >";
					opacityText += "<span><input class='easyui-slider' value='' /></span>";
					opacityText += "<span class='span_value'><input class='easyui-numberspinner' value='0' /></span>";
					opacityText += "</div>";
	
					var obj = {
						id : "cadastral_" + tileNo,
						tileNo : tileNo,
						text :  text,
						state : "closed",
						type : "cadastral",
						checked : tile.visible,
						children : [{
							id : "opacity_" + tileNo,
							text : opacityText,
							type : "opacity"
						}]
					};
					
					data.push(obj);
					
					tile.layer.setVisible(tile.visible);
					layers.push(tile.layer);
				}
				else {
					tile.layer.setVisible(false);
				}

			}
		}
		
		overlay.setVisible(true);
		overlay.setLayers(layers);
		
		result.ty = "node";
		result.data = data;
		return result;
	},
	
	/**
	 * 트리 초기화
	 */
	initTreeUi : function() {
		var that = this;
		var tiles = that.tiles;
		
		for(var i=0; i < tiles.length; i++) {
			var node = $(".div_cadastral_node_" + tiles[i].tileNo, that.selector);
			var opacity = tiles[i].opacity;
			
			$(".easyui-slider", node).slider({
				value : opacity,
				width : 120,
				onChange : function(newValue, oldValue) {
					var node = $(this);
					if(newValue != oldValue) {
						var parentNode = node.parent().parent();
						$(".easyui-numberspinner", parentNode).numberspinner("setValue", newValue);
						
						var tileNo = parentNode.context.parentNode.parentNode.getAttribute("tile-no");
						var tile = cadastralObj.getTile(tileNo);
						tile[0].layer.setOpacity((100 - newValue) / 100);
						tile[0].opacity = newValue;
					}
				}
			});

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
		}
		
		var treeNode = $(".div_cadastral_node", that.selector).parent().parent();
		treeNode.height(30);
		treeNode.find(".tree-checkbox").hide();
	},
	
	/**
	 * 트리 갱신
	 */
	reloadTree : function() {
		var that = this;
		
		var result = that.createTreeData();
		if (result.ty == "msg") {
			$(".div_tree", that.selector).hide();
			$(".div_msg", that.selector).show();
			$(".div_msg", that.selector).html(result.data);
		}
		else if (result.ty == "node") {
			$(".div_tree", that.selector).show();
			$(".div_msg", that.selector).hide();
			
			$(".ul_cadastral_tree", that.selector).tree("loadData", result.data);
			that.initTreeUi();
		}
	},
	
	/**
	 * 창 크기 변경
	 */
	resizeWindowHandler : function() {
		var that = this;
		var height = $("#div_menu_panel").height();
		var titleHeight = $("> .title", that.selector).height();
		var searchHeight = $(".div_search", that.selector).height();
		var midHeight = $(".div_title", that.selector).height();
		var offset = 20;
		$(".div_tree", that.selector).height(height - titleHeight - searchHeight - midHeight - offset);
	},
	
	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		$(that.selector).show();
		that.layer.setVisible(true);
		
		if (that.isCreated) {
			that.reloadTree();
		}
		
		// 화면 크기 변환 시 트리 영역 크기 조절 이벤트 등록
		mainObj.addResizeHandler(that.CLASS_NAME, function() {
			that.resizeWindowHandler();
		});
		that.resizeWindowHandler();
		
		// 지도조작이 끝나면 목록 갱신호출
		eventKey = mapObj.getMap().on("moveend", that.reloadTree, that);
		
		if(PROJECT_CODE == 'sc' || PROJECT_CODE == 'sunchang'){
			that.reloadTree();
		}
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		
		$(that.selector).hide();
		that.layer.setVisible(false);
		mapObj.getMap().getLayer("overlay_group").setVisible(false);
		
		ol.Observable.unByKey(eventKey);
		mainObj.removeResizeHandler(that.CLASS_NAME);
	}	
};