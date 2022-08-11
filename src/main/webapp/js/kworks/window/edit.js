/**
 * 공간 편집
 * @namespace
 */
var editObj = {
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "공간편집",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "Edit",
	
	/**
	 * 레이어명
	 * @type {String}
	 */
	layerName : null,

	/**
	 * 별칭
	 * @type {String}
	 */
	aliasName : null,
	
	/**
	 * 그리기 종류 (Point, LineString, Polygon)
	 * @type {String} 
	 */
	drawType : null,
	
	/**
	 * 벡터 소스
	 * @type {ol.source.Vector}
	 */
	source : null,
	
	/**
	 * 등록 또는 선택 중인 도형
	 * @type {ol.Feature}
	 */
	feature : null,
	
	/**
	 * 상/하월 편집 가능 레이어 목록
	 * @type {Array}
	 */
	upwidLayers : null,
	
	/**
	 * 편집저장 시 실행될 추가적 이벤트 함수
	 */
	onSave : null,
	
	onClose : null,
	
	/**
	 * 초기화 
	 */
	init : function() {
		var that = this;
		that.snap.initSource();
		that.choiceObj.init();
		
		that.initGis();
		
		that.snap.init();
		that.coords.init(that);
		that.offset.init(that);
		that.clip.init(that);
		that.merge.init(that);
		that.split.init(that);
		
	},
	
	/**
	 * GIS 초기화
	 */
	initGis : function() {
		var that = this;
		
		// 선택 레이어 생성 및 등록
		that.source = new ol.source.Vector();
		var layer = new ol.layer.Vector({
			id : "editLayer",
			source : that.source
		});
		mapObj.getMap().addLayer(layer);
		that.source.on("addfeature", that.addFeature, that);
		
		// 점, 선, 면 그리기 인터렉션 생성 및 등록
		var types = ["Point", "LineString", "Polygon"];
		for(var i in types) {
			var options = {};
			options["type"] = types[i];
			options["source"] = that.source;
			var interaction = new ol.interaction.Draw(options);
			interaction.set("id", "edit"+types[i]);
			interaction.set("name", "edit"+types[i]);
			mapObj.getMap().addInteraction(interaction);
			interaction.setActive(false);
			interaction.on("drawend", that.drawend, that);
		}
		
		// 도형 편집 인터렉션
		var modify = new kworks.interaction.Modify({
			features : new ol.Collection()
		});
		modify.set("id", "editModify");
		modify.set("name", "editModify");
		mapObj.getMap().addInteraction(modify);
		modify.setActive(false);
		modify.on("modifyend", that.modifyend, that);
		
		// 도형 이동 인터렉션
		var translate = new kworks.interaction.Translate({
			features : new ol.Collection()
		});
		translate.set("id", "editTranslate");
		translate.set("name", "editTranslate");
		mapObj.getMap().addInteraction(translate);
		translate.setActive(false);

		// Lks : 상월 & 하월 편집 인터렉션
		if (UPWID_EDIT['UseAt'] == 'true') {
			// 상월 편집 인터렉션
			var upwid = new kworks.interaction.Upwid({
				features : new ol.Collection()
			});
			upwid.set("id", "editUpwid");
			upwid.set("name", "editUpwid");
			mapObj.getMap().addInteraction(upwid);
			upwid.setActive(false);
			upwid.on("upwidend", that.upwidend, that);
			
			// 하월 편집 인터렉션
			var downwid = new kworks.interaction.Downwid({
				features : new ol.Collection()
			});
			downwid.set("id", "editDownwid");
			downwid.set("name", "editDownwid");
			mapObj.getMap().addInteraction(downwid);
			downwid.setActive(false);
			downwid.on("downwidend", that.downwidend, that);
		}
		
		// 이동 기능 활성 상태에서 그 도형이 포함되는 위치에서 기능이 변경되는 경우 커서가 변경되지 않어 추가한 코드
		translate.on("change:active", function() {
			if(!this.getActive()) {
				$(mapObj.getMap().getTargetElement()).css("cursor", "default");
			}
		});
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 신규등록
		$("#a_edit_regist").click(function() {
			that.regist();
		});
		
		// 선택
		$("#a_edit_select").click(function() {
			that.select();
		});
		
		// 신규등록 - 그리기
		$("#a_edit_draw").click(function() {
			that.draw();
			that.clearTools();
			$("#a_attEdit_save").show();
			$("#a_edit_save").show();
		});
		
		// 선택 - 점편집
		$("#a_edit_modify").click(function(evt) {
			that.modify(evt);
			that.clearTools();
			$("#a_attEdit_save").show();
			$("#a_edit_save").show();
		});
		
		// 선택 - 이동
		$("#a_edit_move").click(function(evt) {
			that.move(evt);
			that.clearTools();
			$("#a_edit_save").show();
		});
		
		// 선택 - 자르기
		$("#a_edit_clip").click(function(evt) {
			if(that.feature) {
				that.coords.clear();
				that.clearTools();
				$(".div_edit_markup", that.selector).hide();
				$(".div_edit_clip", that.selector).show();
				$("#a_attEdit_save").show();
				$("#a_edit_save").show();
				that.clip.active(that.feature, that.drawType);
			}
			else {
				$.messager.alert(that.TITLE, "도형이 없습니다. 도형을 그려 주십시오.");
				evt.stopPropagation();
			}
		});
		
		// 선택 - 병합
		$("#a_edit_merge").click(function(evt) {
			var element = $(this);
			if(that.feature) {
				that.merge.active().done(function(features) {
					if(features.length > 0) {
						that.coords.clear();
						that.clearTools();
						
						that.choiceObj.clear();
						that.choiceObj.addFeatures(features);
						that.choiceObj.addHandler(that.merge.selectFeature);
						
						that.merge.createElements(features);
						mapObj.getMap().activeInteractions("drag", "editChoice");
						
						$(".div_edit_markup", that.selector).hide();
						$(".div_edit_merge", that.selector).show();
						$("#a_attEdit_save").show();
						$("#a_edit_save").hide();
						$("#a_edit_merge_features").show();
						
						$(".tool_toggle_radio").each(function() {
							$(this).attr("src", $(this).attr("src").replace("_on", "_off"));
						});
						element.find("img").attr("src", element.find("img").attr("src").replace("_ov", "_on").replace("_off", "_on"));
					}
				});
			}
			else {
				$.messager.alert(that.TITLE, "도형이 없습니다. 도형을 그려 주십시오.");
			}
			evt.stopPropagation();
		});
		
		// 선택 - 분할
		$("#a_edit_split").click(function(evt) {
			if(that.feature) {
				that.coords.clear();
				that.clearTools();
				$(".div_edit_markup", that.selector).hide();
				$(".div_edit_split", that.selector).show();
				$("#a_edit_save").hide();
				$("#a_edit_split_features").show();
				$("#a_attEdit_save").hide();
				that.split.active();
			}
			else {
				$.messager.alert(that.TITLE, "도형이 없습니다. 도형을 그려 주십시오.");
				evt.stopPropagation();
			}
		});
		
		// Lks : 상월 & 하월 버튼 이벤트
		if (UPWID_EDIT['UseAt'] == 'true') {
			// 상월
			$("#a_edit_upwid").click(function(evt) {
				that.upwid(evt);
				that.clearTools();
				$("#a_attEdit_save").show();
				$("#a_edit_save").show();
			});
	
			// 하월
			$("#a_edit_downwid").click(function(evt) {
				that.downwid(evt);
				that.clearTools();
				$("#a_attEdit_save").show();
				$("#a_edit_save").show();
			});
		}
		
		// 속성 편집
		$("#a_attEdit_save").click(function(evt) {
			if(that.feature){
				var fid = that.feature.getId();
				
				var onLoad = null;
				if(that[PROJECT_CODE+"Obj"] && that[PROJECT_CODE+"Obj"].getOnLoad) {
					onLoad = that[PROJECT_CODE+"Obj"].getOnLoad(that.layerName);
				}
				
				if(fid) {
					windowObj.registerObj.open(that.layerName, [fid], that.feature, onLoad);
				}
				else {
					if(!that.feature.getProperties()["FTR_IDN"]) {
						that.feature.setProperties({ FTR_IDN : ftrIdnObj.getData(that.layerName) });
					}
					windowObj.registerObj.open(that.layerName, null, that.feature, null, onLoad);
				}
			}else{
				$.messager.alert(that.TITLE, "공간객체가 먼저 생성이 되어야 합니다.");
			}
		});
		
		// 저장  - 병합
		$("#a_edit_merge_features").click(function() {
			that.saveMerge();
			return false;
		});
		
		// 저장 - 분할
		$("#a_edit_split_features").click(function() {
			that.saveSplit();
			return false;
		});
		
		// 저장
		$("#a_edit_save").click(function() {
			that.save();
			
			return false;
		});
		
		// 취소
		$("#a_edit_cancel").click(function() {
			that.clear();
			return false;
		});
		
		// 삭제
		$("#a_edit_remove").click(function() {
			$.messager.confirm(that.TITLE, "선택하신 시설물을 삭제하시겠습니까?", function(isTrue) {
				if(isTrue) {
					that.remove();
					
				}
			});
			return false;
		});
		
		// 닫기
		$("#a_edit_close").click(function() {
			that.close();
		});
	},
	
	/**
	 * 편집 초기화
	 */
	clear : function() {
		var that = this;
		
		$(that.selector).window("resize", { height : 180 });
		$("#div_edit_detail").hide();
		
		that.source.clear();
		that.feature = null;
		
		that.coords.clear();
		that.offset.clear();
		
		that.clearTools();
		
		windowObj.registerObj.close();
		
		highlightObj.removeYellowFeatures(that.CLASS_NAME);
		
		$("#a_map_tool_bass img").trigger("click");
	},
	
	/**
	 * 도구 초기화 (자르기, 병합, 분할 초기화)
	 */
	clearTools : function() {
		var that = this;
		that.clip.clear();
		that.merge.clear();
		that.split.clear();
		$("#a_edit_merge_features").hide();
		$("#a_edit_split_features").hide();
	},
	
	/**
	 * 편집 열기 (편집 시작)
	 * @param {String} layerName 편집할 레이어 명
	 * @param {String} dataAlias 편집할 레이어 가명
	 * @param {Object} options 편집 레이어 저장 후 실행할 이벤트 함수
	 */
	open : function(layerName, dataAlias, drawType, options) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.layerName = layerName;
		that.aliasName = dataAlias;
		that.drawType = drawType;

		if (options) {
			if (options.onSave) {
				that.onSave = options.onSave;
			}
			
			if (options.onClose) {
				that.onClose = options.onClose;
			}
		}
		
		// 편집 레이어 On
		sldObj.showDatas([that.layerName]);
		mapObj.reloadWMS();
		
		var width = 450;
		if (UPWID_EDIT['UseAt'] == 'true') {
			width = 550;
			upwidLayers = UPWID_EDIT['Layers'].split(",");
		}
		
		var url = CONTEXT_PATH + "/window/edit/page.do";
		var windowOptions = {
			title : that.TITLE,
			width : width,
			height : 180,
			top : 120,
			right : 50,
			onClose : function() {
				that.snap.clear();
				that.clear();
			}
		};
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.clear();
			
			that.snap.on();
			that.bindEvents();
			that.snap.bindEvents();
			
			that.coords.loadCrs();
			that.coords.bindEvents();
			
			that.offset.bindEvents();
			that.clip.bindEvents();
			
			that.merge.bindEvents();
			that.split.bindEvents();
			
			$("#td_edit_layer_title").text(dataAlias);
		});
		that.selector = "#" + id;
	},
	
	/**
	 * 신규 등록
	 */
	regist : function() {
		var that = this;
		that.clear();
		$(that.selector).window("resize", { height : 670 });
		$("#div_edit_detail").show();
		if(that.drawType == "Point") {
			$("#a_edit_clip").hide();
			$("#a_edit_merge").hide();
		}
		else {
			$("#a_edit_clip").show();
			$("#a_edit_merge").show();
		}
		$("#a_edit_split").hide();

		that.upwidButton();
		
		$("#a_edit_remove").hide();
		$("#a_edit_draw img").trigger("click");
	},
	
	/**
	 * 선택
	 */
	select : function() {
		var that = this;
		that.clear();
		
		that.upwidButton();
		
		selectObj.active(that.CLASS_NAME, "Point", "drawend", function(evt) {
			var geom = evt.feature.getGeometry();
			that.search(geom);
		}, false);
		
	},
	
	/**
	 * 상하월 편집 버튼 제어
	 */
	upwidButton : function() {
		var that = this;
		
		if(UPWID_EDIT['UseAt'] != 'true') {
			$("#a_edit_upwid").hide();
			$("#a_edit_downwid").hide();
			return;
		}
		
		var isEditUpwid = false;
		if (upwidLayers) {
			for (var i = 0; i < upwidLayers.length; i++) {
				if (that.layerName != upwidLayers[i])
					continue;
				
				isEditUpwid = true;
				break;
			}
		}
		
		if(isEditUpwid) {
			$("#a_edit_upwid").show();
			$("#a_edit_downwid").show();
		}
		else {
			$("#a_edit_upwid").hide();
			$("#a_edit_downwid").hide();
		}
	},
	
	/**
	 * 그리기
	 */
	draw : function() {
		var that = this;
		$(".div_edit_markup", that.selector).hide();
		$(".div_edit_coords", that.selector).show();
		mapObj.getMap().activeInteractions("drag", "edit"+that.drawType);
		var geometry = that.feature?that.feature.getGeometry():null;
		that.coords.show(that.drawType, geometry);
	},
	
	/**
	 * 점편집
	 * @param {Object} evt 이벤트 객체 (이벤트 버블을 막기 위해 사용)
	 */
	modify : function(evt) {
		var that = this;
		if(that.feature) {
			var features = new ol.Collection();
			features.push(that.feature);
			var modify = mapObj.getMap().getInteraction("editModify");
			modify.setFeatures(features);
			mapObj.getMap().activeInteractions("drag", "editModify");
			$(".div_edit_markup", that.selector).hide();
			$(".div_edit_coords", that.selector).show();
			var geometry = that.feature?that.feature.getGeometry():null;
			that.coords.show(that.drawType, geometry);
		}
		else {
			$.messager.alert(that.TITLE, "선택된 시설물이 없습니다.");
			evt.stopPropagation();
		}
	},
	
	/**
	 * 이동
	 * @param {Object} evt 이벤트 객체 (이벤트 버블을 막기 위해 사용)
	 */
	move : function(evt) {
		var that = this;
		if(that.feature) {
			var features = new ol.Collection();
			features.push(that.feature);
			var translate = mapObj.getMap().getInteraction("editTranslate");
			translate.setFeatures(features);
			mapObj.getMap().activeInteractions("drag", "editTranslate");
			$(".div_edit_markup", that.selector).hide();
			$(".div_edit_move", that.selector).show();
			that.coords.clear();
		}
		else {
			$.messager.alert(that.TITLE, "선택된 시설물이 없습니다.");
			evt.stopPropagation();
		}
	},
	
	/**
	 * 상월
	 * @param {Object} evt 이벤트 객체 (이벤트 버블을 막기 위해 사용)
	 */
	upwid : function(evt) {
		var that = this;
		if(that.feature) {
			var scale = mapObj.getMap().getScale();
			if(scale > 150) {
				$.messager.confirm(that.TITLE, "상월 도형은 150 이하의 축척에서 편집이 가능합니다. 축척을 확대 하시겠습니까?", function(isTrue) {
					if(isTrue) {
						var center = that.calculateCenter(that.feature.getGeometry()).getCoordinates();
						mapObj.getMap().moveToCenter(center);
						mapObj.getMap().moveToScale(150);
					}
				});
			}
			
			var features = new ol.Collection();
			features.push(that.feature);
			var upwid = mapObj.getMap().getInteraction("editUpwid");
			upwid.setFeatures(features);
			mapObj.getMap().activeInteractions("drag", "editUpwid");
			$(".div_edit_markup", that.selector).hide();
			$(".div_edit_coords", that.selector).show();
			var geometry = that.feature?that.feature.getGeometry():null;
			that.coords.show(that.drawType, geometry);
		}
		else {
			$.messager.alert(that.TITLE, "선택된 시설물이 없습니다.");
			evt.stopPropagation();
		}
	},

	/**
	 * 하월
	 * @param {Object} evt 이벤트 객체 (이벤트 버블을 막기 위해 사용)
	 */
	downwid : function(evt) {
		var that = this;
		if(that.feature) {
			var scale = mapObj.getMap().getScale();
			if(scale > 150) {
				$.messager.confirm(that.TITLE, "하월 도형은 150 이하의 축척에서 편집이 가능합니다. 축척을 확대 하시겠습니까?", function(isTrue) {
					if(isTrue) {
						var center = that.calculateCenter(that.feature.getGeometry()).getCoordinates();
						mapObj.getMap().moveToCenter(center);
						mapObj.getMap().moveToScale(150);
					}
				});
			}
			
			var features = new ol.Collection();
			features.push(that.feature);
			var downwid = mapObj.getMap().getInteraction("editDownwid");
			downwid.setFeatures(features);
			mapObj.getMap().activeInteractions("drag", "editDownwid");
			$(".div_edit_markup", that.selector).hide();
			$(".div_edit_coords", that.selector).show();
			var geometry = that.feature?that.feature.getGeometry():null;
			that.coords.show(that.drawType, geometry);	
		}
		else {
			$.messager.alert(that.TITLE, "선택된 시설물이 없습니다.");
			evt.stopPropagation();
		}
	},
	
	/**
	 * 원의 중심을 재계산.
	 * @param {ol.geom.Geometry} geometry 공간객체
	 */
	calculateCenter : function(geometry) {
		var extent = geometry.getExtent();
		var x = (extent[0] + extent[2]) / 2;
		var y = (extent[1] + extent[3]) / 2;
		
		return new ol.geom.Point([x,y]);
	},	
	
	
	/**
	 * 도형이 추가되었을 때 이전 도형이 있을 경우 필요한 값들을 복사하고 좌표 목록 표시 
	 * (that.source 에 addfeature 발생 시 실행되는 함수)
	 * @param {Object} evt 이벤트 객체
	 */
	addFeature : function(evt) {
		var that = this;
		if(that.feature && that.feature.getId()) {
			evt.feature.setId(that.feature.getId());
			var properties = $.extend({}, that.feature.getProperties(), evt.feature.getProperties());
			delete properties[MAP.GEOMETRY_ATTR_NAME];
			evt.feature.setProperties(properties);
			evt.feature.fid = that.feature.fid;
			evt.feature.tableName = that.feature.tableName;
			that.feature = evt.feature;
		}
		else {
			that.feature = evt.feature;
		}
		var geometry = that.feature?that.feature.getGeometry():null;
		that.coords.show(that.drawType, geometry);
	},
	
	/**
	 * 그리기 완료 시 이전에 그린 도형들 삭제
	 * @param {Object} evt 이벤트 객체
	 */
	drawend : function(evt) {
		var that = this;
		that.source.clear();
	},
	
	/**
	 * 점편집 시 좌표 목록 갱신
	 * @param {Object} evt 이벤트 객체
	 */
	modifyend : function(evt) {
		var that = this;
		var features = evt.features;
		if(features.get("length") > 0) {
			var geometry = that.feature?that.feature.getGeometry():null;
			that.coords.show(that.drawType, geometry);
		}
		else {
			$.messager.alert(that.TITLE, "점편집 중 오류가 발생했습니다. 관리자에게 문의하여 주십시오.");
		}
	},

	/**
	 * 상월좌표 목록 갱신
	 * @param {Object} evt 이벤트 객체
	 */
	upwidend : function(evt) {
		var that = this;
		var features = evt.features;
		if(features.get("length") > 0) {
			var geometry = that.feature?that.feature.getGeometry():null;
			that.coords.show(that.drawType, geometry);
		}
		else {
			$.messager.alert(that.TITLE, "상월 편집 중 오류가 발생했습니다. 관리자에게 문의하여 주십시오.");
		}
	},
	
	/**
	 * 하월좌표 목록 갱신
	 * @param {Object} evt 이벤트 객체
	 */
	downwidend : function(evt) {
		var that = this;
		var features = evt.features;
		if(features.get("length") > 0) {
			var geometry = that.feature?that.feature.getGeometry():null;
			that.coords.show(that.drawType, geometry);
		}
		else {
			$.messager.alert(that.TITLE, "하월 편집 중 오류가 발생했습니다. 관리자에게 문의하여 주십시오.");
		}
	},	
	
	/*
	 
	  공간검색
	  @param {ol.geom} geom 공간정보를 검색할 지점
	  
	  2020.06.23 정신형 수정
	  시설물 편집 기능에서 시설물 선택 시 선택 범위 내에 여러 개의 해당 시설물이 존재해도 한 개만 선택 되어 조회되는 문제점 확인.
	  선택 범위에 여러 개의 해당 시설물이 존재할 때 선택된 갯수 만큼 관리 번호를 보여주는 리스트를 보여주고 선택하여 조회할 수 있게 수정.
	 
	 */
	search : function(geom) {
		var that = this;
		
		var format = new ol.format.WKT();
		var buffer = mapObj.getMap().getView().getResolution() * 3;
		if(buffer < 1) { buffer = 1; }
		var bufferGeometry = spatialUtils.buffer(geom, buffer);
		highlightObj.showYellowFeatures(that.CLASS_NAME, [new ol.Feature(bufferGeometry)]);
		
		var wkt = format.writeFeature(new ol.Feature(gisObj.toTransformSystem(bufferGeometry)));
		var filter = {
			filterType : "SPATIAL",
			spatialType : "INTERSECTS",
			wkt : wkt,
			isOriginColumnValue : true
		};
		
		spatialSearchUtils.listAll(that.TITLE, that.layerName, filter, function(rows) {
			if(rows && rows.length > 0) {
				that.source.clear();
				
				if (rows.length > 1) {
					windowObj.spatialSelectObj.open(that.aliasName, rows, {
						onSubmit : function(selectedRow) {
							if (selectedRow) {
								that.setSelectedFeatuer(selectedRow);
							} else {
								$.messager.alert(aliasName+"편집", "선택된 "+aliasName+"가 없습니다.");
							}
						}
					});
				} else {
					that.setSelectedFeatuer(rows[0]);
				}
				
			}
		});
	},
	
	/*
	 
	  2020.06.23 정신형 추가
	  시설물 편집 기능 관련. 선택된 시설물의 정보를 창에 띄워주는 function.
	  
	 */
	setSelectedFeatuer : function(row) {
		var that = this;

			if (row) {
				var format = new ol.format.WKT();
				var feature = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);

				var geometry = gisObj.toTransformMap(feature.getGeometry());
				feature.setGeometry(geometry);

				var pkColumn = dataObj.getPkColumn(that.layerName);
				var fid = row[pkColumn];
				feature.setId(fid);

				var properties = [];
						var data = dataObj.getDataByDataId(that.layerName, true);
						var dataFields = data.kwsDataFields;
						for (var i = 0, len = dataFields.length; i < len; i++) {
							var dataField = dataFields[i];
							var indictTy = dataField.indictTy;
							if (indictTy != "HIDE" && indictTy != "WKT") {
								var field = camelcaseUtils.underbarToCamelcase(dataField.fieldId);
								var value = '';
								
								if(PROJECT_CODE == "gn") {
									if(field['facNum'] != null || field['facNum'] != ''){
										value = stringUtils.lpad(row[field], 4);
									}
									
									if(field['fadNum'] != null || field['fadNum'] != ''){
										value = stringUtils.lpad(row[field], 4);
									}
								}else{
									value = row[field];
								}
								
								//var value = row[field];
								if(value) {
									properties[dataField.fieldId] = value;
								}
							}
						}
						feature.setProperties(properties);
						that.source.addFeature(feature);
						
						
						$(that.selector).window("resize", { height : 670 });
						$("#div_edit_detail").show();
						
						if(that.drawType == "Point") {
							$("#a_edit_clip").hide();
							$("#a_edit_merge").hide();
							$("#a_edit_split").hide();
						} else {
							$("#a_edit_clip").show();
							$("#a_edit_merge").show();
							$("#a_edit_split").show();
						}
						
						$(".div_edit_markup", that.selector).hide();
						$("#a_edit_remove").show();
						$(".div_edit_coords", that.selector).show();
						$("#a_edit_modify img").trigger("click");
						
						imageUtils.on($("#img_edit_snap_view"));
						
						highlightObj.removeYellowFeatures(that.CLASS_NAME);
						selectObj.clear(that.CLASS_NAME);
		}				
	},
	
	/**
	 * 좌표 적용
	 * @param {Array..<Number>} coords 적용할 좌표 배열 공간 객체 종류에 따라 다름
	 */
	applyCoords : function(coords) {
		var that = this;
		var geom = null;
		if(that.drawType == "Point") {
			if(coords.length != 1) {
				$.messager.alert(that.TITLE, "점형 시설물은 한 개의 좌표만 입력 가능합니다.");
				return;
			}
			geom = new ol.geom.Point(coords[0]);
		}
		else if(that.drawType == "LineString") {
			if(coords.length < 2) {
				$.messager.alert(that.TITLE, "점형 시설물은 2개의 이상의 좌표가 필요합니다..");
				return;
			}
			geom = new ol.geom.LineString(coords);
		}
		else if(that.drawType == "Polygon") {
			if(coords.length < 4) {
				$.messager.alert(that.TITLE, "면형 시설물은 4개의 이상의 좌표가 필요합니다..");
				return;
			}
			else if(coords) {
				var first = coords[0];
				var last = coords[coords.length-1];
				if(first[0] != last[0] || first[1] != last[1]) {
					$.messager.alert(that.TITLE, "면형 도형의 첫 좌표와 끝 좌표는 동일하여야 합니다.");
					return;
				}
			}
			geom = new ol.geom.Polygon([coords]);
		} 
		else {
			$.messager.alert(that.TITLE, "지원되지 않는 공간 타입입니다.");
		}
		
		if(that.feature) {
			that.feature.setGeometry(geom);
		}
		else {
			that.source.clear();
			that.feature = new ol.Feature({geometry : geom});
			that.source.addFeature(that.feature);
		}
		
		var modify = mapObj.getMap().getInteraction("editModify");
		if(modify.getActive()) {
			var features = new ol.Collection();
			features.push(that.feature);
			modify.setFeatures(features);
		}
	},
	
	/**
	 * 이동 Offset 적용
	 * @param {Number} x 이동할 X 축 거리
	 * @param {Number} y 이동할 y 축 거리
	 */
	applyOffset : function(x, y) {
		var that = this;
		if(that.feature) {
			/// 좌표계마다 거리가 달라 중부원점 60만을 기준으로 변환하여 이동 후 다시 좌표 변환함
			var proj = ol.proj.get("EPSG:5186");
			var mapProj = mapObj.getMap().getView().getProjection();
			var geom = that.feature.getGeometry();
			if(proj != mapProj) {
				that.feature.getGeometry().transform(mapProj, proj);
				geom.translate(x, y);
				that.feature.getGeometry().transform(proj, mapProj);
			}
			else {
				geom.translate(x, y);
			}
		}
	},
	
	/**
	 * 공간 정보 적용
	 * @param {ol.geom.Geometry} geometry 공간 정보
	 */
	applyGeometry : function(geometry) {
		var that = this;
		if(that.feature) {
			that.feature.setGeometry(geometry);
		}
	},
	
	/**
	 * 도형 저장 (등록 or 수정)
	 */
	save : function() {
		var that = this;
		if(that.feature) {
			var properties = that.feature.getProperties();
			
			// 보호구역 본번, 부번 LPAD
			if(PROJECT_CODE == "gn") {
				var oKeys = Object.keys(properties);
				
				if(oKeys.indexOf("FAC_NUM") > -1){
					properties["FAC_NUM"] = stringUtils.lpad(properties["FAC_NUM"], 4);
				}
				
				if(oKeys.indexOf("FAD_NUM") > -1){
					properties["FAD_NUM"] = stringUtils.lpad(properties["FAD_NUM"], 4);
				}
				that.feature.setProperties(properties);
			}
			
			var insertFeatures = [];
			var updateFeatures = [];
			if(that.feature.getId()) {
				updateFeatures.push(that.feature);
			}
			else {
				var properties = that.feature.getProperties();
				if(properties["FTR_IDN"] != null) {
					insertFeatures.push(that.feature);
				}
				else {
					$.messager.alert(that.TITLE, "속성정보는 필수 입력사항입니다.");
					return;
				}
				
				
			}
			
			mapObj.getMap().getInteraction("editModify").clearFeatures();
			
			that.feature.setGeometry(gisObj.toTransformSystem(that.feature.getGeometry()));
			
			var url = CONTEXT_PATH + "/proxy/transaction.do?sysId="+SYS_ID+"&editType=ATTRIBUTE";
			var params = {
				featureType : that.layerName,
				featureNS : MAP.PREFIX,
				featurePrefix : MAP.PREFIX,
				srsName : MAP.PROJECTION,
				geometryName : MAP.GEOMETRY_ATTR_NAME.toUpperCase(),
				serverType : MAP.SERVER_TYPE
			};
			kworks.service.WFS.transaction(url, params, insertFeatures, updateFeatures, null, function(response) {
				if(response.inserted == 1) {
					// 강릉시 점용대장 - 점용시설이 등록 또는 수정되면 읍면동을 업데이트한다.
					if(PROJECT_CODE == "gn" && SYS_ID == 12) {
						var udUrl = CONTEXT_PATH + "/ocpat/" + that.layerName + "/" + insertFeatures[0].get("FTF_CDE") + "/" + insertFeatures[0].get("FTF_IDN") + "/updateRegstrUmd.do";
						$.get(udUrl);
//						$.get(udUrl).done(function(result) {
//							if (result.state == 1) {
//								
//							}
//						});
					}					
					$.messager.alert(that.TITLE, "등록되었습니다.");
					//강릉시 배수설비인허가 대장과 물받이, 하수연결관 공간정보 신규 등록관의 연계
					if (that.onSave) {
						that.onSave(that.feature);
					}
					that.clear();
					mapObj.reloadWMS();
				}
				else if(response.updated == 1) {
					// 강릉시 점용대장 - 점용시설이 등록 또는 수정되면 읍면동을 업데이트한다.
					if(PROJECT_CODE == "gn" && SYS_ID == 12) {
						var udUrl = CONTEXT_PATH + "/ocpat/" + that.layerName + "/" + updateFeatures[0].get("FTF_CDE") + "/" + updateFeatures[0].get("FTF_IDN") + "/updateRegstrUmd.do";
						$.get(udUrl);
//						$.get(udUrl).done(function(result) {
//							if (result.state == 1) {
//								
//							}
//						});
					}					
					$.messager.alert(that.TITLE, "편집 되었습니다.");
					that.clear();
					mapObj.reloadWMS();
				}
				else {
					$.messager.alert(that.TITLE, "편집에 실패하였습니다.");
				}
			});
		}
		else {
			$.messager.alert(that.TITLE, "공간정보는 필수 입력사항입니다.");
		}
	},
	
	/**
	 * 도형 삭제
	 */
	remove : function() {
		var that = this;
		if(that.feature) {
			var url = CONTEXT_PATH + "/proxy/transaction.do?sysId="+SYS_ID+"&editType=SPATIAL";
			var params = {
				featureType : that.layerName,
				featureNS : MAP.PREFIX,
				featurePrefix : MAP.PREFIX,
				srsName : MAP.PROJECTION,
				geometryName : MAP.GEOMETRY_ATTR_NAME.toUpperCase(),
				serverType : MAP.SERVER_TYPE
			};
			kworks.service.WFS.transaction(url, params, null, null, [that.feature], function(response) {
				if(response.deleted == 1) {
					$.messager.alert(that.TITLE, "삭제 되었습니다.");
					that.clear();
					mapObj.reloadWMS();
				}
				else {
					$.messager.alert(that.TITLE, "삭제에 실패하였습니다.");
				}
			});
		}
	},
	
	/**
	 * 병합 저장
	 */
	saveMerge : function() {
		var that = this;
		
		var features = that.choiceObj.getSource().getFeatures();
		var deleteFeatures = [];
		for(var i=0, len=features.length; i < len; i++) {
			if(features[i].getProperties()["selected"]) {
				deleteFeatures.push(features[i]);
			}
		}
		
		var length = deleteFeatures.length;
		if(length > 0) {
			$.messager.confirm(that.TITLE, (length+1) + " 개의 시설물을 병합합니다. 병합의 대상이 되는 시설물은 삭제 됩니다. 병합하시겠습니까?", function(isTrue) {
				if(isTrue) {
					var insertFeatures = [];
					var updateFeatures = [];
					if(that.feature.getId()) {
						updateFeatures.push(that.feature);
					}
					else {
						var properties = that.feature.getProperties();
						if(properties["FTR_IDN"]) {
							insertFeatures.push(that.feature);
						}
						else {
							$.messager.alert(that.TITLE, "속성정보는 필수 입력사항입니다.");
							return;
						}
					}
					mapObj.getMap().getInteraction("editModify").clearFeatures();
					
					var mergeFeatures = deleteFeatures.concat([that.feature]);
					var mergeGeom =  null;
					if(that.drawType == "LineString") {
						mergeGeom = spatialUtils.mergeLineString(that.feature.getGeometry(), mergeFeatures[0].getGeometry());
					}
					else {
						mergeFeatures.push(that.feature);
						mergeGeom = spatialUtils.union(mergeFeatures);
					}
					that.feature.setGeometry(mergeGeom);
					
					that.feature.setGeometry(gisObj.toTransformSystem(that.feature.getGeometry()));
					
					var url = CONTEXT_PATH + "/proxy/transaction.do?sysId="+SYS_ID+"&editType=SPATIAL";
					var params = {
						featureType : that.layerName,
						featureNS : MAP.PREFIX,
						featurePrefix : MAP.PREFIX,
						srsName : MAP.PROJECTION,
						geometryName : MAP.GEOMETRY_ATTR_NAME.toUpperCase(),
						serverType : MAP.SERVER_TYPE
					};
					
					kworks.service.WFS.transaction(url, params, insertFeatures, updateFeatures, deleteFeatures, function(response) {
						if((response.inserted == 1 || response.updated == 1) && response.deleted > 0) {
							$.messager.alert(that.TITLE, "병합 되었습니다.");
							that.clear();
							mapObj.reloadWMS();
						}
						else {
							$.messager.alert(that.TITLE, "병합에 실패하였습니다.");
						}
					});
				}
			});
		}
		else {
			$.messager.alert(that.TITLE, "병합할 시설물이 없습니다. 병합할 시설물을 선택하여 주십시오.");
		}
	},
	
	/**
	 * 분할 저장
	 */
	saveSplit : function() {
		var that = this;
		
		var insertFeatures = that.choiceObj.getSource().getFeatures();
		var deleteFeatures = [that.feature];
		if(insertFeatures.length == 2) {
			for(var i=0, len=insertFeatures.length; i < len; i++) {
				var feature = insertFeatures[i];
				feature.setGeometry(gisObj.toTransformSystem(feature.getGeometry()));
			}
			
			var url = CONTEXT_PATH + "/proxy/transaction.do?sysId="+SYS_ID+"&editType=SPATIAL";
			var params = {
				featureType : that.layerName,
				featureNS : MAP.PREFIX,
				featurePrefix : MAP.PREFIX,
				srsName : MAP.PROJECTION,
				geometryName : MAP.GEOMETRY_ATTR_NAME.toUpperCase(),
				serverType : MAP.SERVER_TYPE
			};
			kworks.service.WFS.transaction(url, params, insertFeatures, null, deleteFeatures, function(response) {
				if(response.inserted == 2 && response.deleted == 1) {
					$.messager.alert(that.TITLE, "분할 되었습니다.");
					that.clear();
					mapObj.reloadWMS();
				}
				else {
					$.messager.alert(that.TITLE, "분할에 실패하였습니다.");
				}
			});
		}
		else {
			$.messager.alert(that.TITLE, "시설물을 분할하여 주십시오.");
		}
	},
	
	/**
	 * 편집 기능 닫기
	 */
	close : function() {
		var that = this;

		if (that.onClose) {
			that.onClose();
		}
		that.clear();
		that.onSave = null;
		that.onClose = null;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
		
};

/**
 * 공간 편집 스냅
 * @namespace
 */
editObj.snap = {
	
	/**
	 * 상위객체 - 공간 편집
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "공간 편집 스냅",
	
	/**
	 * 벡터소스
	 * @type {ol.source.Vector}
	 */
	source : null,
	
	/**
	 * 스냅 인터렉션
	 * @type {ol.interaction.Snap}
	 */
	interaction : null,
	
	/**
	 * 스냅 기능이 활성화 되어야 할 Interaction ID 목록
	 * @type {Array<String>}
	 */
	interactionIds : ["editPoint", "editLineString", "editPolygon", "editModify", "editUpwid", "editDownwid"],
	
	/**
	 * 초기화 
	 */
	init : function(parent) {
		var that = this;
		that.initGis();
		that.parent = parent;
	},
	
	/**
	 * GIS 초기화
	 */
	initGis : function() {
		var that = this;
		// 스냅 인터렉션
		that.interaction = new ol.interaction.Snap({
			source: that.source
		});
		mapObj.getMap().addInteraction(that.interaction);
	},
	
	/**
	 * 벡터소스 초기화
	 */
	initSource : function() {
		var that = this;
		// 스냅 레이어 생성 및 등록
		that.source = new ol.source.Vector();
		var layer = new ol.layer.Vector({
			id : "snapLayer",
			source : that.source,
			style : new ol.style.Style({
				fill : new ol.style.Fill({
					color : 'rgba(255, 255, 0, 0.5)'
				}),
				stroke : new ol.style.Stroke({
					color : 'rgba(255, 255, 0, 0.9)',
					width : 5
				}),
				image : new ol.style.Circle({
					radius : 7,
					fill : new ol.style.Fill({
						color : 'rgba(255, 255, 0, 0.9)'
					})
				})
			})
		});
		mapObj.getMap().addLayer(layer);	
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 스냅대상 체크박스
		$("#chk_edit_snap").click(function() {
			that.redraw();
		});
		
		// 스냅대상 목록
		$("#sel_edit_snap").change(function() {
			that.redraw();
		});
	},
	
	/**
	 * 스냅 기능 정리
	 */
	clear : function() {
		var that = this;
		that.un();
		that.source.clear();
		if($("#chk_edit_snap").is(":checked")) $("#chk_edit_snap").removeAttr("checked");
	},
	
	/**
	 * 스냅 활성화
	 */
	on : function() {
		var that = this;
		mapObj.getMap().getLayer('kc_wms').getSource().on("change", that.load, that);
		that.load();
		$("#sel_edit_snap").val($("#sel_edit_snap option:first").val());
		mapObj.getMap().on("moveend", that.redraw, that);
		for(var i in that.interactionIds) {
			var interaction = mapObj.getMap().getInteraction(that.interactionIds[i]);
			if(interaction) {
				interaction.on("change:active", that.redraw, that);
			}
		}
	},
	
	/**
	 * 스냅 비활성화
	 */
	un : function() {
		var that = this;
		mapObj.getMap().getLayer('kc_wms').getSource().un("change", that.load, that);
		mapObj.getMap().un("moveend", that.redraw, that);
		for(var i in that.interactionIds) {
			var interaction = mapObj.getMap().getInteraction(that.interactionIds[i]);
			if(interaction) {
				interaction.un("change:active", that.redraw, that);
			}
		}
	},
	
	/**
	 * 스냅 대상 목록 표시
	 */
	load : function() {
		var current = $("#sel_edit_snap").val();
		var layers = mapObj.getMap().getLayer('kc_wms').getSource().getParams()["LAYERS"].split(",");
		var tagStr = "";
		for(var i=layers.length-1; i >=0; i--) {
			if(layers[i]) {
				tagStr += "<option value='"+layers[i]+"'";
				tagStr += layers[i]==current?" selected='selected' ":"";
				tagStr += ">"+sldObj.getNamedLayer(layers[i]).title+"</option>";
			}
		}
		$("#sel_edit_snap").html(tagStr);
		$("#sel_edit_snap").trigger("change");
	},
	
	/**
	 * 스냅 레이어 그리기
	 */
	redraw : function() {
		var that = this;
		var isActive = false;
		
		for(var i in that.interactionIds) {
			var interaction = mapObj.getMap().getInteraction(that.interactionIds[i]);
			if(interaction && interaction.getActive()) {
				isActive = true;
			}
		}
		
		if(isActive && mapObj.getMap().getScale() < 5000 && $("#chk_edit_snap").is(":checked")) {
			var dataId = $("#sel_edit_snap").val();
			var extent = mapObj.getMap().getExtent();
			var from = mapObj.getMap().getView().getProjection();
			var to = ol.proj.get(MAP.PROJECTION);
			var transformExtent = spatialUtils.transformExtent(extent, from ,to);
			
			var filter = {
				filterType : "BBOX",
				xmin : transformExtent[0],
				ymin : transformExtent[1],
				xmax : transformExtent[2],
				ymax : transformExtent[3]
			};
			
			spatialSearchUtils.listAll(that.TITLE, dataId, filter, function(rows) {
				that.source.clear();
				
				var format = new ol.format.WKT();
				var features = [];
				for(var i=0, len=rows.length; i < len; i++) {
					var row = rows[i];
					var feature = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);
					var geometry = gisObj.toTransformMap(feature.getGeometry());
					feature.setGeometry(geometry);
					features.push(feature);
				}
				if(features.length > 0) {
					that.source.addFeatures(features);
				}
				that.interaction.setActive(true);
				
				imageUtils.on($("#img_edit_snap_view"));
			}, { isNoMessage : true });
		}
		else {
			that.source.clear();
			that.interaction.setActive(false);
			imageUtils.off($("#img_edit_snap_view"));
		}
	}
		
};

/**
 * 공간 편집 좌표 목록
 * @namespace
 */
editObj.coords = {
		
	/**
	 * 상위객체 - 공간 편집
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "공간편집 좌표목록",
		
	/**
	 * 초기화
	 * @param {Object} parent 상위객체 - 공간 편집
	 */
	init : function(parent) {
		var that = this;
		that.initGis();
		that.parent = parent;
	},
	
	/**
	 * GIS 초기화
	 */
	initGis : function() {
		var that = this;
		that.source = new ol.source.Vector();
		var layer = new ol.layer.Vector({
			id : "coordsLayer",
			source : that.source,
			style : new ol.style.Style({
				fill : new ol.style.Fill({
					color : 'rgba(255, 0, 0, 0.2)'
				}),
				stroke : new ol.style.Stroke({
					color : 'rgba(255, 0, 0, 0.6)',
					width : 5
				}),
				image : new ol.style.Circle({
					radius : 7,
					fill : new ol.style.Fill({
						color : 'rgba(255, 0, 0, 1)'
					})
				})
			})
		});
		mapObj.getMap().addLayer(layer);
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 적용  - 직접 입력 좌표 등록
		$("#a_edit_coords_apply").click(function() {
			that.apply();
		});
		
		// 행 선택
		$("#tby_edit_coords").on("click", "tr", function() {
			var node = this;
			that.select(node);
		});
		
		// 위로
		$("#a_edit_coords_up").click(function() {
			that.up();
		});
		
		// 아래로
		$("#a_edit_coords_down").click(function() {
			that.down();
		});
		
		// 추가
		$("#a_edit_coords_add").click(function() {
			that.add();
		});
		
		// 삭제
		$("#a_edit_coords_remove").click(function() {
			that.remove();
			that.highlight();
		});
		
		// 방향 전환
		$("#a_edit_coords_reverse").click(function() {
			that.reverseCoords();
		});
		
		// 좌표계 변경
		$("#sel_edit_proj").change(function() {
			var feature = that.parent.source.getFeatures();
			if(feature && feature.length > 0) {
				that.show(that.parent.drawType, feature[0].getGeometry());
			}
		});
		
	},
	
	/**
	 * 좌표계 로딩
	 */
	loadCrs : function() {
		var tagStr = "";
		var data = coordinateObj.getData();
		for(var i=0, len=data.length; i < len; i++) {
			var crsObj = data[i];
			tagStr += '<option value="'+crsObj.cntmId+'" selected="selected">'+crsObj.cntmNm+'</option>';
		}
		$("#sel_edit_proj").html(tagStr);
		$("#sel_edit_proj").val(MAP.PROJECTION);
	},
	
	/**
	 * 좌표 목록 기능 정리
	 */
	clear : function() {
		var that = this;
		that.source.clear();
		$("#tby_edit_coords").html("");
	},
	
	/**
	 * 좌표 태그 생성
	 * @param {Array<Number>} coords 좌표 (x, y)
	 */
	createCoordTag : function(coords) {
		var tagStr = "";
		tagStr += '<tr>';
		tagStr += '<td class="td_edit_coords_coord_seq"></td>';
		tagStr += '<td><input type="text" value="'+coords[0]+'" class="w_80 inputText txt_edit_coords_coord_x"></td>';
		tagStr += '<td><input type="text" value="'+coords[1]+'" class="w_80 inputText txt_edit_coords_coord_y"></td>';
		tagStr += '</tr>';
		return tagStr;
	},
	
	/**
	 * 좌표 목록 표시
	 * @param {String} drawType 그리기 타입 (Point, LineString, Polygon)
	 * @param {ol.geom} geometry 공간 정보
	 */
	show : function(drawType, geometry) {
		var that = this;
		that.clear();
		
		if(drawType == "Point") {
			$("#p_edit_coords_menu").hide();
			if(!geometry) {
				$("#tby_edit_coords").html(that.createCoordTag([0, 0]));
				that.showSeq();
			}
		}
		else {
			$("#p_edit_coords_menu").show();
		}
		
		if(geometry) {
			var geom = geometry.clone();
			var proj = $("#sel_edit_proj").val();
			var mapProj = mapObj.getMap().getView().getProjection();
			if(proj != mapProj) {
				geom = geom.transform(mapProj, proj);
			}
			
			var coords = geom.getCoordinates();
			var tagStr = '';
			if(geom instanceof ol.geom.Point) {
				tagStr += that.createCoordTag(coords);
			}
			else if(geom instanceof ol.geom.LineString) {
				for(var i=0, len=coords.length; i < len; i++) {
					var coord = coords[i];
					tagStr += that.createCoordTag(coord);
				}
			}
			else if(geom instanceof ol.geom.Polygon) {
				if(coords.length == 1) {
					var linerRing = coords[0];
					for(var i=0, len=linerRing.length; i < len; i++) {
						var coord = linerRing[i];
						tagStr += that.createCoordTag(coord);
					}
				}
				else {
					$.messager.alert(that.TITLE, "지원되지 않는 공간 형태입니다.");
				}
			}
			else {
				$.messager.alert(that.TITLE, "지원되지 않는 공간 타입입니다.");
			}
			$("#tby_edit_coords").html(tagStr);
			that.showSeq();
		}
	},
	
	/**
	 * 순번 표시
	 */
	showSeq : function() {
		var seq = 1;
		$("#tby_edit_coords tr td.td_edit_coords_coord_seq").each(function() {
			$(this).text(seq++);
		});
	},
	
	/**
	 * 좌표 목록 선택 시 강조
	 * @param {Object} node 선택한 노드
	 */
	select : function(node) {
		var that = this;
		if($(node).hasClass("on")) {
			$(node).removeClass("on");
		}
		else {
			$("#tby_edit_coords tr").removeClass("on");
			$(node).addClass("on");
		}
		that.highlight();
	},
	
	/**
	 * 화면에 선택한 지점 표시
	 */
	highlight : function() {
		var that = this;
		that.source.clear();
		$("#tby_edit_coords tr.on").each(function() {
			var node = this;
			var x = parseFloat($(node).find(".txt_edit_coords_coord_x").val());
			var y = parseFloat($(node).find(".txt_edit_coords_coord_y").val());
			var feature = new ol.Feature(new ol.geom.Point([x, y]));
			that.source.addFeatures([feature]);
		});
	},
	
	/**
	 * 좌표 반영
	 */
	apply : function() {
		var that = this;
		var coords = [];
		$("#tby_edit_coords tr").each(function() {
			var node = this;
			var x = parseFloat($(node).find(".txt_edit_coords_coord_x").val());
			var y = parseFloat($(node).find(".txt_edit_coords_coord_y").val());
			coords.push([x, y]);
		});
		
		var proj = $("#sel_edit_proj").val();
		var mapProj = mapObj.getMap().getView().getProjection();
		if(proj != mapProj) {
			for(var i in coords) {
				coords[i] = new ol.geom.Point(coords[i]).transform(proj, mapProj).getCoordinates();
			}
		}
		that.parent.applyCoords(coords);
	},
	
	/**
	 * 좌표 변환
	 */
	reverseCoords : function() {
		var that = this;
		
		var coords = [];
		$("#tby_edit_coords tr").each(function() {
			var node = this;
			var x = parseFloat($(node).find(".txt_edit_coords_coord_x").val());
			var y = parseFloat($(node).find(".txt_edit_coords_coord_y").val());
			coords.push([x, y]);
		});
		
		coords.reverse();
		
		var tagStr = "";
		for(var i=0, len=coords.length; i < len; i++) {
			tagStr += that.createCoordTag(coords[i]);
		}
		$("#tby_edit_coords").html(tagStr);
		that.showSeq();
	},
	
	/**
	 * 위로 이동
	 */
	up : function() {
		var that = this;
		var node = $("#tby_edit_coords tr.on");
		if(node.length > 0) {
			var prevNode = node.prev();
			if(prevNode.length == 0) {
				$.messager.alert(that.TITLE, "첫 좌표 입니다. 이동할 수 없습니다.");
			}
			else {
				prevNode.before(node);
			}
			that.showSeq();
		}
		else {
			$.messager.alert(that.TITLE, "선택된 좌표가 없습니다.");
		}
		
	},
	
	/**
	 * 아래로 이동
	 */
	down : function() {
		var that = this;
		var node = $("#tby_edit_coords tr.on");
		if(node.length > 0) {
			var nextNode = node.next();
			if(nextNode.length == 0) {
				$.messager.alert(that.TITLE, "마지막 좌표 입니다. 이동할 수 없습니다.");
			}
			else {
				node.before(nextNode);
			}
			that.showSeq();
		}
		else {
			$.messager.alert(that.TITLE, "선택된 좌표가 없습니다.");
		}
	},
	
	/**
	 * 좌표 추가
	 */
	add : function() {
		var that = this;
		var node = $("#tby_edit_coords tr.on");
		var tagStr = that.createCoordTag([0, 0]);
		if(node.length > 0) {
			$(tagStr).insertAfter(node);
		}
		else {
			$("#tby_edit_coords").append(tagStr);
		}
		that.showSeq();
	},
	
	/**
	 * 좌표 삭제
	 */
	remove : function() {
		var that = this;
		var node = $("#tby_edit_coords tr.on");
		if(node.length > 0) {
			node.remove();
			that.showSeq();
		}
		else {
			$.messager.alert(that.TITLE, "선택된 좌표가 없습니다.");
		}
	}
};

/**
 * 공간 편집 이동
 * @namespace
 */
editObj.offset = {
		
	/**
	 * 상위객체 - 공간 편집
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "편집 이동",
		
	/**
	 * 초기화
	 * @param {Object} parent 상위객체 - 공간 편집
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		// 적용  - 오프셋 이동
		$("#a_edit_offset_apply").click(function() {
			that.apply();
		});
	},
	
	/**
	 * 이동 초기화
	 */
	clear : function() {
		$("#txt_edit_offset_x").val(0);
		$("#txt_edit_offset_y").val(0);
	},
	
	/**
	 * 이동 적용
	 */
	apply : function() {
		var that = this;
		var x = parseFloat($("#txt_edit_offset_x").val());
		var y = parseFloat($("#txt_edit_offset_y").val());
		that.parent.applyOffset(x, y);
	}
	
};

/**
 * 선택 객체
 * @type {Object}
 */
editObj.choiceObj = {
		
	/**
	 * 선택 벡터 소스 (병합, 분할, 자르기 사용)
	 */
	source : null,
	
	/**
	 * 이벤트 핸들러
	 */
	handler : null,
	
	/**
	 * 초기화
	 * @param {Object} parent 상위객체 - 공간 편집
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
		that.initGis();
	},
	
	/**
	 * GIS 초기화
	 */
	initGis : function() {
		var that = this;
		that.source = new ol.source.Vector();
		var layer = new ol.layer.Vector({
			id : "choiceLayer",
			source : that.source,
			style : function(feature) {
				return that.createStyle(feature.getProperties()["selected"]);
			}
		});
		mapObj.getMap().addLayer(layer);
		
		var interaction = new kworks.interaction.Select({
			layers : [layer]
		});
		interaction.set("id", "editChoice");
		interaction.set("name", "editChoice");
		mapObj.getMap().addInteraction(interaction);
		interaction.setActive(false);
		
		interaction.on("select", function(evt) {
			if(that.handler) {
				that.handler(evt.selected);
			}
		});
	},
	
	/**
	 * 스타일 생성
	 * @param {String} selected 선택 여부
	 * @returns {ol.style.Style} 스타일 객체
	 */
	createStyle : function(selected) {
		if(selected) {
			return new ol.style.Style({
				fill : new ol.style.Fill({
					color : 'rgba(255, 0, 0, 0.2)'
				}),
				stroke : new ol.style.Stroke({
					color : 'rgba(255, 0, 0, 0.6)',
					width : 5
				}),
				image : new ol.style.Circle({
					radius : 7,
					fill : new ol.style.Fill({
						color : 'rgba(255, 0, 0, 1)'
					})
				})
			});
		}
		else {
			return new ol.style.Style({
				fill : new ol.style.Fill({
					color : 'rgba(255, 255, 0, 0.2)'
				}),
				stroke : new ol.style.Stroke({
					color : 'rgba(255, 255, 0, 0.6)',
					width : 2
				}),
				image : new ol.style.Circle({
					radius : 7,
					fill : new ol.style.Fill({
						color : 'rgba(255, 255, 0, 1)'
					})
				})
			});
		}
	},
	
	/**
	 * 도형 목록 추가
	 * @param {Array.<ol.Feature>} features 도형 목록
	 */
	addFeatures : function(features) {
		var that = this;
		that.source.addFeatures(features);
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
	 * 도형 목록 반환
	 * @returns {Array.<ol.Feature>} 도형 목록
	 */
	getFeatures : function() {
		var that = this;
		return that.source.getFeatures();
	},
	
	/**
	 * 이벤트 핸들러 추가
	 * @param {Function} handler 이벤트 핸들러
	 */
	addHandler : function(handler) {
		var that = this;
		that.handler = handler;
	},
	
	/**
	 * 초기화
	 */
	clear : function() {
		var that = this;
		that.source.clear();
		that.handler = null;
	}
		
	
};

/**
 * 편집 - 자르기
 * @type {Object}
 */
editObj.clip = {
		
	/**
	 * 상위객체 - 공간 편집
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "자르기",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "EditClip",
		
	/**
	 * 초기화
	 * @param {Object} parent 상위객체 - 공간 편집
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		$("#tby_edit_clip_choice").on("click", "tr", function() {
			var element = $(this);
			
			$("#tby_edit_clip_choice tr").removeClass("on");
			$(element).addClass("on");
			
			var id = element.attr("data-id");
			var features = that.parent.choiceObj.getFeatures();
			for(var i=0, len=features.length; i < len; i++) {
				var feature = features[i];
				if(feature.getId() == id) {
					feature.setProperties({ selected : true });
				}
				else {
					feature.setProperties({ selected : false });
				}
			}
			return false;
		});
		
		$("#tby_edit_clip_choice").on("click", "tr .a_remove", function() {
			var element = $(this);
			var id = element.attr("data-id");
			var source = that.parent.choiceObj.getSource();
			var features = source.getFeatures();
			for(var i=0, len=features.length; i < len; i++) {
				var feature = features[i];
				if(feature.getId() == id) {
					source.removeFeature(feature);
					break;
				}
			}

			if(source.getFeatures().length == 1) {
				var geometry = source.getFeatures()[0].getGeometry();
				that.parent.applyGeometry(geometry);
			}
			
			that.clear();
			return false;
		});
	},
	
	/**
	 * 자르기 초기화
	 */
	clear : function() {
		var that = this;
		that.parent.choiceObj.clear();
		selectObj.clear(that.CLASS_NAME);
		$("#tby_edit_clip_choice").html("");
	},
	
	/**
	 * 자르기 활성화
	 * @param {ol.Feature} feature 도형
	 * @param {String} drawType 그리기 타입
	 */
	active : function(feature, drawType) {
		var that = this;
		selectObj.active(that.CLASS_NAME, drawType, "drawend", function(evt) {
			var g1 = feature.getGeometry();
			var g2 = evt.feature.getGeometry();
			if(spatialUtils.intersects(g1, g2)) {
				var features = [];
				
				var differenceGeom = spatialUtils.difference(g1, g2);
				if(differenceGeom) {
					if(g1 instanceof ol.geom.LineString) {
						if(differenceGeom.getLineStrings) {
							if(differenceGeom.getLineStrings().length == 2) {
								var lineStrings = differenceGeom.getLineStrings();
								for(var i=0, len=lineStrings.length; i < len; i++) {
									features.push(new ol.Feature(lineStrings[i]));
								}
							}
							else {
								$.messager.alert(that.TITLE, "다중 도형은 지원되지 않습니다. 도형을 다시 그려 주십시오.");
								return;
							}
						}
						else {
							$.messager.alert(that.TITLE, "도형을 다시 그려 주십시오.");
							return;
						}
					}
					else {
						var intersectionGeom = spatialUtils.intersection(g1, g2);
						if(intersectionGeom.getPolygons) {
							$.messager.alert(that.TITLE, "다중 도형은 지원되지 않습니다. 도형을 다시 그려 주십시오.");
							return;
						}
						else {
							features.push(new ol.Feature(intersectionGeom));
						}
						
						if(differenceGeom.getPolygons) {
							$.messager.alert(that.TITLE, "다중 도형은 지원되지 않습니다. 도형을 다시 그려 주십시오.");
							return;
						}
						else {
							features.push(new ol.Feature(differenceGeom));
						}
					}
					
					that.parent.choiceObj.clear();
					that.parent.choiceObj.addFeatures(features);
					that.createElements(features);
				}
				else {
					$.messager.alert(that.TITLE, "도형을 다시 그려 주십시오.");
				}
			}
			else {
				if(g1 instanceof ol.geom.LineString) {
					$.messager.alert(that.TITLE, "두 선이 교차되지 않습니다.");
				}
				else {
					$.messager.alert(that.TITLE, "두 영역이 겹치지 않습니다.");
				}
			}
		}, false);
	},
	
	/**
	 * 도형 목록 생성
	 * @param {Array.<ol.Feature>} features 도형 목록
	 */
	createElements : function(features) {
		var tagStr = "";
		for(var i=0, len=features.length; i < len; i++) {
			var id = "edit_clip_" + (i+1);
			var feature = features[i];
			feature.setId(id);
			tagStr += '<tr data-id="' + feature.getId() + '" >';
			tagStr += '<td>' + (i+1) + '</td>';
			tagStr += '<td class="t_a_c"><a href="#" class="a_remove" data-id="' + feature.getId() + '" ><img src="' + CONTEXT_PATH + '/images/kworks/map/common/boomark_icon1_ov.png" alt="지우기" /></a></td>';
			tagStr += '</tr>';
		}
		$("#tby_edit_clip_choice").html(tagStr);
	}
	
};

/**
 * 병합 객체
 * @type {Object}
 */
editObj.merge = {
		
	/**
	 * 상위객체 - 공간 편집
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "자르기",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "EditClip",
	
	/**
	 * 초기화
	 * @param {Object} parent 상위객체 - 공간 편집
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 버퍼거리 재설정
		$("#a_edit_merge_buffer_apply").click(function() {
			that.active();
		});
		
		// 목록 선택
		$("#tby_edit_merge_choice").on("click", "tr", function() {
			var element = $(this);
			var id = element.attr("data-id");
			
			var features = that.parent.choiceObj.getFeatures();
			
			var selected = null;
			if(element.hasClass("on")) {
				element.removeClass("on");
				selected = false;
			}
			else {
				// 선형일 경우 한 개만 선택 되도록 함
				if(that.parent.drawType == "LineString") {
					$("#tby_edit_merge_choice tr").removeClass("on");
					for(var i=0, len=features.length; i < len; i++) {
						var feature = features[i];
						feature.setProperties({ selected : false });
					}
				}
				element.addClass("on");
				selected = true;
			}

			for(var i=0, len=features.length; i < len; i++) {
				var feature = features[i];
				if(feature.getId() == id) {
					feature.setProperties({ selected : selected });
					break;
				}
			}
			return false;
		});
	},
	
	/**
	 * 병합 초기화
	 */
	clear : function() {
		var that = this;
		that.parent.choiceObj.clear();
		$("#tby_edit_merge_choice").html("");
	},
	
	/**
	 * 활성화
	 * @returns {Promise} Promise 객체
	 */
	active : function() {
		var that = this;
		var deferred = $.Deferred();

		var feature = that.parent.feature;
		var layerName = that.parent.layerName;
		var geometry = null;
		
		var drawType = that.parent.drawType;
		if(drawType == "LineString") {
			var coordinates = [];
			coordinates.push(feature.getGeometry().getFirstCoordinate());
			coordinates.push(feature.getGeometry().getLastCoordinate());
			geometry = new ol.geom.MultiPoint(coordinates);
		}
		else {
			geometry = feature.getGeometry();
		}
		
		// 좌표 변환
		var mapProjection = mapObj.getMap().getView().getProjection();
		var sysProjection = ol.proj.get(MAP.PROJECTION);
		var transformGeometry = spatialUtils.transform(geometry, mapProjection, sysProjection);
		var format = new ol.format.WKT();
		var wkt = format.writeFeature(new ol.Feature(transformGeometry));
		
		var filter = {
			filterType : "SPATIAL",
			spatialType : "INTERSECTS",
			wkt : wkt,
			isOriginColumnValue : true
		};
		// 공간 검색
		spatialSearchUtils.listAll(that.TITLE, layerName, filter, function(rows) {
			if(rows && rows.length > 0) {
				var features = [feature];
				for(var i=0, len=rows.length; i < len; i++) {
					var row = rows[i];
					var f = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);
					var geometry = gisObj.toTransformMap(f.getGeometry());
					f.setGeometry(geometry);
					
					var pkColumn = dataObj.getPkColumn(layerName);
					var fid = row[pkColumn];
					if(feature.getId() != fid) {
						f.setId(fid);
						features.push(f);
						
						// intersection, touches 적용해도 원하는 결과가 나오지 않아 union 후 MultiPolygon이 나오는 것 제외
						var unionGeom = spatialUtils.union(features);
						if(unionGeom instanceof ol.geom.MultiPolygon) {
							features.pop();
						}
					}
				}
				
				// 원본 객체 제외
				features.shift();
				if(features.length <= 0) {
					$.messager.alert(that.TITLE, "병합할 대상이 없습니다.");
				}
				deferred.resolve(features);
			}
		});
		return deferred.promise();
	},
	
	/**
	 * 도형 선택
	 * @param {Array.<Feature>} features 도형 목록
	 */
	selectFeature : function(features) {
		var that = this;
		
		if(features.length > 0) {
			var feature = features[0];
			if(feature.getProperties()["selected"]) {
				feature.setProperties({ selected : false });
				$("#tby_edit_merge_choice tr[data-id=" + feature.getId() + "]").removeClass("on");
			}
			else {
				if(feature.getGeometry() instanceof ol.geom.LineString) {
					$("#tby_edit_merge_choice tr").removeClass("on");
					
					var fs = that.getFeatures();
					for(var i=0, len=fs.length; i < len; i++) {
						fs[i].setProperties({ selected : false });
					}
				}
				feature.setProperties({ selected : true });
				$("#tby_edit_merge_choice tr[data-id=" + feature.getId() + "]").addClass("on");
			}
		}
		mapObj.getMap().getInteraction("editChoice").removeOverlayFeatures();
	},
	
	/**
	 * 도형 목록 생성
	 * @param {Array.<Feature>} features 도형 목록
	 */
	createElements : function(features) {
		var tagStr = '';
		for(var i=0, len=features.length; i < len; i++) {
			var feature = features[i];
			tagStr += '<tr data-id="' + feature.getId() + '" >';
			tagStr += '<td>' + (i+1) + '</td>';
			tagStr += '<td>' + feature.getId() + '</td>';
			tagStr += '</tr>';
		}
		$("#tby_edit_merge_choice").html(tagStr);
	}
		
};

/**
 * 분할 객체
 * @type {Object}
 */
editObj.split = {
		
	/**
	 * 상위객체 - 공간 편집
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "분할",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "EditSplit",
	
	/**
	 * 도형
	 * @type {ol.Feature}
	 */
	feature : null,
		
	/**
	 * 초기화
	 * @param {Object} parent 상위객체 - 공간 편집
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 행 선택 시 지도 하이라이트
		$("#tby_edit_split_choice").on("click", "tr", function() {
			var element = $(this);
			
			$("#tby_edit_split_choice tr").removeClass("on");
			$(element).addClass("on");
			
			var id = element.attr("data-id");
			var features = that.parent.choiceObj.getFeatures();
			for(var i=0, len=features.length; i < len; i++) {
				var feature = features[i];
				if(feature.getId() == id) {
					feature.setProperties({ selected : true });
				}
				else {
					feature.setProperties({ selected : false });
				}
			}
			return false;
		});
		
		$("#a_edit_split_apply").click(function() {
			that.split();
			return false;
		});
		
		// 분할 행 선택
		$("#tby_edit_split_choice").on("click", "tr .a_attr_edit", function() {
			var element = $(this);
			var id = element.attr("data-id");
			
			var feature = null;
			var features = that.parent.choiceObj.getSource().getFeatures();
			for(var i=0, len=features.length; i < len; i++) {
				if(features[i].getId() == id) {
					feature = features[i];
					break;
				}
			}

			if(feature) {
				windowObj.registerObj.open(that.parent.layerName, [that.parent.feature.getId()], feature);
			}
			return false;
		});
	},
	
	/**
	 * 분할 초기화
	 */
	clear : function() {
		var that = this;
		that.feature = null;
		that.parent.choiceObj.clear();
		selectObj.clear(that.CLASS_NAME);
		$("#tby_edit_split_coords").html("");
		$("#tby_edit_split_choice").html("");
		$("#div_split_coords_container").hide();
		$("#div_split_feature_container").hide();
	},
	
	/**
	 * 분할 활성화
	 */
	active : function() {
		var that = this;
		
		selectObj.active(that.CLASS_NAME, that.parent.drawType, "drawend", function(evt) {
			that.feature = evt.feature;
			that.createCoordElements(that.feature.getGeometry());
		}, true);
		
		$("#div_split_coords_container").show();
		$("#div_split_feature_container").hide();
	},
	
	/**
	 * 좌표 목록 생성
	 * @param {ol.geom.Geometry} geom 공간 정보
	 */
	createCoordElements : function(geom) {
		var that = this;
		var coords = geom.getCoordinates();
		var tagStr = '';
		if(geom instanceof ol.geom.Point) {
			tagStr += that.createCoordTag(coords, 0);
		}
		else if(geom instanceof ol.geom.LineString) {
			for(var i=0, len=coords.length; i < len; i++) {
				var coord = coords[i];
				tagStr += that.createCoordTag(coord, i);
			}
		}
		else if(geom instanceof ol.geom.Polygon) {
			if(coords.length == 1) {
				var linerRing = coords[0];
				for(var i=0, len=linerRing.length; i < len; i++) {
					var coord = linerRing[i];
					tagStr += that.createCoordTag(coord, i);
				}
			}
			else {
				$.messager.alert(that.TITLE, "지원되지 않는 공간 형태입니다.");
			}
		}
		else {
			$.messager.alert(that.TITLE, "지원되지 않는 공간 타입입니다.");
		}
		$("#tby_edit_split_coords").html(tagStr);
	},
	
	/**
	 * 좌표 태그 생성
	 * @param {Array<Number>} coords 좌표 (x, y)
	 */
	createCoordTag : function(coords, seq) {
		var tagStr = "";
		tagStr += '<tr>';
		tagStr += '<td class="td_edit_coords_coord_seq">'+(seq+1)+'</td>';
		tagStr += '<td><input type="text" value="'+coords[0]+'" class="w_80 inputText txt_edit_coords_coord_x"></td>';
		tagStr += '<td><input type="text" value="'+coords[1]+'" class="w_80 inputText txt_edit_coords_coord_y"></td>';
		tagStr += '</tr>';
		return tagStr;
	},
	
	/**
	 * 분할
	 */
	split : function() {
		var that = this;
		var coords = [];
		$("#tby_edit_split_coords tr").each(function() {
			var node = this;
			var x = parseFloat($(node).find(".txt_edit_coords_coord_x").val());
			var y = parseFloat($(node).find(".txt_edit_coords_coord_y").val());
			coords.push([x, y]);
		});
		if(that.applyCoords(coords)) {
			var g1 = that.parent.feature.getGeometry();
			var g2 = that.feature.getGeometry();
			if(spatialUtils.intersects(g1, g2)) {
				var features = [];
				
				var differenceGeom = spatialUtils.difference(g1, g2);
				if(differenceGeom) {
					if(g1 instanceof ol.geom.LineString) {
						if(differenceGeom.getLineStrings) {
							if(differenceGeom.getLineStrings().length == 2) {
								var lineStrings = differenceGeom.getLineStrings();
								for(var i=0, len=lineStrings.length; i < len; i++) {
									features.push(new ol.Feature(lineStrings[i]));
								}
							}
							else {
								$.messager.alert(that.TITLE, "다중 도형은 지원되지 않습니다. 도형을 다시 그려 주십시오.");
								return;
							}
						}
						else {
							$.messager.alert(that.TITLE, "도형을 다시 그려 주십시오.");
							return;
						}
					}
					else {
						var intersectionGeom = spatialUtils.intersection(g1, g2);
						if(intersectionGeom.getPolygons) {
							$.messager.alert(that.TITLE, "다중 도형은 지원되지 않습니다. 도형을 다시 그려 주십시오.");
							return;
						}
						else {
							features.push(new ol.Feature(intersectionGeom));
						}
						
						if(differenceGeom.getPolygons) {
							$.messager.alert(that.TITLE, "다중 도형은 지원되지 않습니다. 도형을 다시 그려 주십시오.");
							return;
						}
						else {
							features.push(new ol.Feature(differenceGeom));
						}
					}
					
					that.parent.choiceObj.clear();
					that.parent.choiceObj.addFeatures(features);
					
					that.setProperties(features);
					that.createFeatureElements(features);
					
					selectObj.clear(that.CLASS_NAME);
					
					$("#div_split_coords_container").hide();
					$("#div_split_feature_container").show();
				}
				else {
					$.messager.alert(that.TITLE, "도형을 다시 그려 주십시오.");
				}
			}
			else {
				if(g1 instanceof ol.geom.LineString) {
					$.messager.alert(that.TITLE, "두 선이 교차되지 않습니다.");
				}
				else {
					$.messager.alert(that.TITLE, "두 영역이 겹치지 않습니다.");
				}
			}
		}
	},
	
	/**
	 * 좌표 적용
	 * @param {Array..<Number>} coords 적용할 좌표 배열 공간 객체 종류에 따라 다름
	 */
	applyCoords : function(coords) {
		var that = this;
		var geom = null;
		if(that.parent.drawType == "Point") {
			if(coords.length != 1) {
				$.messager.alert(that.TITLE, "점형 시설물은 한 개의 좌표만 입력 가능합니다.");
				return false;
			}
			geom = new ol.geom.Point(coords[0]);
		}
		else if(that.parent.drawType == "LineString") {
			if(coords.length < 2) {
				$.messager.alert(that.TITLE, "점형 시설물은 2개의 이상의 좌표가 필요합니다..");
				return false;
			}
			geom = new ol.geom.LineString(coords);
		}
		else if(that.parent.drawType == "Polygon") {
			if(coords.length < 4) {
				$.messager.alert(that.TITLE, "면형 시설물은 4개의 이상의 좌표가 필요합니다..");
				return false;
			}
			else if(coords) {
				var first = coords[0];
				var last = coords[coords.length-1];
				if(first[0] != last[0] || first[1] != last[1]) {
					$.messager.alert(that.TITLE, "면형 도형의 첫 좌표와 끝 좌표는 동일하여야 합니다.");
					return false;
				}
			}
			geom = new ol.geom.Polygon([coords]);
		} 
		else {
			$.messager.alert(that.TITLE, "지원되지 않는 공간 타입입니다.");
		}
		
		if(that.feature) {
			that.feature.setGeometry(geom);
		}
		return true;
	},
	
	/**
	 * 도형 속성 설정
	 * @param {Array.<ol.Feature>} features 도형 목록
	 */
	setProperties : function(features) {
		var that = this;
		for(var i=0, len=features.length; i < len; i++) {
			var id = "edit_split_" + (i+1);
			var feature = features[i];
			feature.setId(id);
			feature.setProperties($.extend({}, that.parent.feature.getProperties(), feature.getProperties()));
			feature.setProperties({ FTR_IDN : ftrIdnObj.getData(that.parent.layerName) });
		}
	},
	
	/**
	 * 도형 목록 생성 
	 * @param {Array.<ol.Feature>} features 도형 목록
	 */
	createFeatureElements : function(features) {
		var tagStr = "";
		for(var i=0, len=features.length; i < len; i++) {
			var feature = features[i];
			tagStr += '<tr data-id="' + feature.getId() + '" >';
			tagStr += '<td>' + (i+1) + '</td>';
			tagStr += '<td class="t_a_c"><a href="#" class="a_attr_edit" data-id="' + feature.getId() + '" ><img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/edit_feature_off.png" alt="속성편집" /></a></td>';
			tagStr += '</tr>';
		}
		$("#tby_edit_split_choice").html(tagStr);
	}
	
};

