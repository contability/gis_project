/**
 * 공간편집
 */
windowObj.explantEditObj = {
	
	selector : null,
	
	TITLE : "공간편집",
	
	CLASS_NAME : "ExplantEdit",
	
	/**
	 * 레이어명
	 * @type {String}
	 */
	layerName : null,
	
	/**
	 * 그리기 종류 (Circle) : (Polygon)
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
	 * 아이디
	 */
	exp_idn : null,

	/**
	 * 도형 중심
	 */
	center : null,
	
	/**
	 * 주소
	 */
	address : null,
	
	/**
	 * 면적
	 */
	featureArea : null,

	/**
	 * 원본 속성
	 */
	srcProperties : null,

	/**
	 * 속성 정보
	 */
	properties : null,
	
	/**
	 * 오버레이
	 * @type {ol.Overlay}
	 */
	overlay : null,
	
	/**
	 * 현지조사표 선택
	 */
	isSelected : false,

	/**
	 * 도형 그림
	 */
	isDraw : false,
	
	onSave : null,


	/**
	 * 초기화
	 * 편집창 오픈전 사전에 초기화 되어있어야 함.
	 */
	init : function() {
		var that = this;
		
		that.initGis();
		that.initCoords();
	},
	
	/**
	 * GIS 초기화
	 */
	initGis : function() {
		var that = this;
		
		// 소스 생성
		that.source = new ol.source.Vector();
		
		// 편집 레이어 생성 - 편집시 적용되는 스타일
		var layer = new ol.layer.Vector({
			id : "editLayer",
			source : that.source,
			style : new ol.style.Style({
	            fill : new ol.style.Fill({
	                color : 'rgba(255, 0, 0, 0.2)'
	            }),
	            stroke : new ol.style.Stroke({
	                color : 'rgba(255, 0, 0, 0.9)',
	                width : 2
	            }),
			})
		});
		mapObj.getMap().addLayer(layer);
		that.source.on("addfeature", that.addFeature, that);

		///////////////////////////////////////////////////////
		// 도형 추가시 적용되는 스타일
		var options = {
			source : that.source,
	        type : "Circle",
	        style : new ol.style.Style({
	            fill : new ol.style.Fill({
	                color : 'rgba(0, 0, 255, 0.2)'
	            }),
	            stroke : new ol.style.Stroke({
	                color : 'rgba(0, 0, 255, 0.5)',
	                lineDash : [10, 10],
	                width : 2
	            }),
	            image : new ol.style.Circle({
	                radius : 5,
	                stroke : new ol.style.Stroke({
	                    color : 'rgba(0, 0, 255, 0.7)'
	                }),
	                fill : new ol.style.Fill({
	                    color : 'rgba(0, 0, 255, 0.2)'
	                })
	            })
	        })
		};
		
		var interaction = new ol.interaction.Draw(options);
		interaction.set("id", "drawCircle");
		interaction.set("name", "drawCircle");
		mapObj.getMap().addInteraction(interaction);
		interaction.setActive(false);
		interaction.on("drawstart", that.drawstart, that);
		interaction.on("drawend", that.drawend, that);
		
		///////////////////////////////////////////////////////
		// 도형 이동 인터렉션
		var translate = new kworks.interaction.Translate({
			features : new ol.Collection()
		});
		translate.set("id", "circleTranslate");
		translate.set("name", "circleTranslate");
		mapObj.getMap().addInteraction(translate);
		translate.setActive(false);
		translate.on("translatestart", that.translatestart, that);
		translate.on("translating", that.translating, that);
		translate.on("translateend", that.translateend, that);

		// 이동 기능 활성 상태에서 그 도형이 포함되는 위치에서 기능이 변경되는 경우 커서가 변경되지 않어 추가한 코드
		translate.on("change:active", function() {
			if(!this.getActive()) {
				$(mapObj.getMap().getTargetElement()).css("cursor", "default");
			}
		});
	},
	
	initCoords : function() {
		var that = this;
		
		that.coords.init(that);
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
	
		// 신규등록
		$("#a_edit_regist").click(function() {
			windowObj.explantPropertyObj.close();		
			that.regist();
		});
		
		// 선택
		$("#a_edit_select").click(function() {
			windowObj.explantPropertyObj.close();		
			that.select();
		});
		
		// 신규등록 - 그리기
		$("#a_edit_draw").click(function() {
			that.draw();
			$("#a_attEdit_save").show();
			$("#a_edit_save").show();
		});
		
		// 선택 - 이동
		$("#a_edit_move").click(function(evt) {
			that.move(evt);
			$("#a_edit_save").show();
		});
		
		// 삭제
//		$("#a_edit_remove").click(function() {
//			$.messager.confirm(that.TITLE, "선택하신 조사지역을 삭제하시겠습니까?", function(isTrue) {
//				if(isTrue) {
//					that.remove();
//				}
//			});
//			return false;
//		});
		
		// 속성 편집
		$("#a_attEdit_save").click(function(evt) {
			if(that.feature){
				var layerId = "EXPLANT_AS";
				
				if (that.properties) {// 편집
					
					
				}
				else { // 신규
					var current = new Date();
					var day = String(current.getDate()).padStart(2, '0');
					var month = String(current.getMonth() + 1).padStart(2, '0'); //January is 0!
					var year = current.getFullYear();					
					var today = year + month + day;
					
					that.properties  = {
						"invDate" : today,
						"pnu" : that.pnu,
						"disArea" : that.featureArea,
						"disDens" : 1,
						"expSize" : 0.0,
						"ftrIdn" : null,
						"expType" : null,
						"invNam" : null,
						"disType" : null,
						"expStep" : null,
						"invNote" : null
					};
				}

				windowObj.explantPropertyObj.open(layerId, that.properties); // 상세 조회창
			}
			else {
				$.messager.alert(that.TITLE, "조사지역이 먼저 지정 되어야 합니다.");
			}
		});
		
		// 저장
		$("#a_edit_save").click(function() {
			that.save();
			return false;
		});
		
		// 닫기
		$("#a_edit_close").click(function() {
			that.close();
		});
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		$(that.selector).window("resize", { height : 180 });
		$("#div_edit_detail").hide();
	},

	clear : function() {
		var that = this;
		
		that.initUi();
		
		that.source.clear();
		that.feature = null;
		that.isSelected = false;
		that.isDraw = false;
		that.srcProperties = false; 
		that.properties = false;
		
		that.coords.clear();

		selectObj.clear(that.CLASS_NAME);
		if (this.overlay)
			mapObj.getMap().removeOverlay(this.overlay);
		
	},
	
	open : function() {
		var that = this;
		if(that.selector)
			that.close();
		
		that.layerName = "현지조사표";
		that.drawType = "Circle";
		
		mapObj.reloadWMS();
		
		var url = CONTEXT_PATH + "/enviro/window/explantEditPage.do";
		var windowOptions = {
			title : that.TITLE,
			width : 450,
			height : 180,
			top : 120,
			right : 10,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.clear();
			that.bindEvents();
			that.coords.bindEvents();
			
			$("#td_edit_layer_title").text("생태교란 외래식물 현지조사");
		});
		that.selector = "#" + id;		
	},
	
	/**
	 * 신규 등록
	 */
	regist : function() {
		var that = this;
		
		that.clear();
		that.isSelected = false;
		that.isDraw = false;
		that.exp_idn = null;
		that.center = null;
		that.address = null;
		that.featureArea = null;
		that.srcProperties = null;
		that.properties = null;

		$(that.selector).window("resize", { height : 550 });
		$("#div_edit_detail").show();
		
		$("#a_edit_draw").show();
		$("#a_edit_draw img").trigger("click");
		
		$("#a_edit_remove").hide();

		$(".edit_toggle_regist").attr("src", $(".edit_toggle_regist").attr("src").replace("_off", "_on"));
		$(".edit_toggle_select").attr("src", $(".edit_toggle_select").attr("src").replace("_on", "_off"));
	},
	
	/**
	 * 도형이 추가되었을 때 이전 도형이 있을 경우 필요한 값들을 복사하고 좌표 목록 표시 
	 * (that.source 에 addfeature 발생 시 실행되는 함수)
	 * @param {Object} evt 이벤트 객체
	 */
	addFeature : function(evt) {
		var that = this;
		that.feature = evt.feature;
		
		// 원의 중심 
		var geometry = that.feature?that.feature.getGeometry():null;
		if (geometry) {
			that.center = spatialUtils.calculateCenter(geometry);

			if (that.isDraw) {
				that.getAddress(that.center);
			}
			else {
				that.address = that.createAddress(that.properties["pnu"]);
				that.featureArea = that.properties["disArea"];
			}
			
			that.coords.viewCoords(geometry, that.address, that.featureArea);
		}
	},
	
	/**
	 * 그리기
	 */
	draw : function() {
		var that = this;
		
		that.isDraw = true;
		$(that.selector).window("resize", { height : 550 });
		
		$(".div_edit_coords", that.selector).show();
		mapObj.getMap().activeInteractions("drag", "drawCircle");
		var geometry = that.feature?that.feature.getGeometry():null;
		that.coords.viewCoords(geometry, that.address, that.featureArea);
		
		$(".edit_toggle_draw").attr("src", $(".edit_toggle_draw").attr("src").replace("_off", "_on"));
		$(".edit_toggle_move").attr("src", $(".edit_toggle_move").attr("src").replace("_on", "_off"));
	},

	/**
	 * 그리기 시작
	 * @param {Object} evt 이벤트 객체
	 */
	drawstart : function(evt) {
		var editLayer = mapObj.getMap().getLayer("editLayer");
		if(goog.isDef(editLayer)) {
			mapObj.getMap().removeLayer(editLayer);
			mapObj.getMap().addLayer(editLayer);
		}

		if (this.source)
			this.source.clear();
		
		this.feature = evt.feature;
		
		// 면적 출력용 오버레이 생성
		if (this.overlay)
			mapObj.getMap().removeOverlay(this.overlay);
		
		this.overlay = new ol.Overlay({
			element : goog.dom.createDom(goog.dom.TagName.DIV, {'class' : 'kworks-tooltip kworks-tooltip-measure'}),
			offset : [0, -15],
			positioning : 'bottom-center'
		});
		this.overlay.set("name", "editOverlay");
		mapObj.getMap().addOverlay(this.overlay);
		mapObj.getMap().on('pointermove', this.drawing, this); // 마우스 이동 이벤트 지정
	},
	
	/**
	 * 그리는 중
	 * @param {Object} evt 이벤트 객체
	 */
	drawing : function(evt) {
		if(evt.dragging) {
			return;
		}

		if(this.feature) {
			var geometry = this.feature.getGeometry();
			this.outputArea(geometry);
		}
	},
	
	outputArea : function(geometry) {
		var that = this;
		
		var center = spatialUtils.calculateCenter(geometry);
		var coord = center.getCoordinates();
		that.overlay.setPosition(coord);
		
		if(geometry instanceof ol.geom.Circle) {
			var area = spatialUtils.calculateArea(geometry);
			that.featureArea = area;
		}
		
		$(that.overlay.getElement()).html("먼적:" + spatialUtils.formatArea(that.featureArea));
	},
	
	/**
	 * 그리기 완료 시 이전에 그린 도형들 삭제
	 * @param {Object} evt 이벤트 객체
	 */
	drawend : function(evt) {
		var that = this;
		
		that.source.clear();
		mapObj.getMap().un('pointermove', this.drawing, this);	// 마우스 이동 이벤트 해제
	},
	
	/**
	 * 선택
	 */
	select : function() {
		var that = this;
		
		$("#a_edit_remove").show();
		
		that.clear();
		selectObj.active(that.CLASS_NAME, "Point", "drawend", function(event) {
			var geom = event.feature.getGeometry();
			that.search(geom);
		}, false);
		
	},
	
	/*
	 * 공간검색
	 * 
	 * */
	search : function(geom){
		var that = this;
		
		var format = new ol.format.WKT();
		var buffer = mapObj.getMap().getView().getResolution() * 3;
		if (buffer < 1) {
			buffer = 1;
		}
		var bufferGeometry = spatialUtils.buffer(geom, buffer);
		highlightObj.showYellowFeatures(that.CLASS_NAME, [ new ol.Feature(
				bufferGeometry) ]);

		var wkt = format.writeFeature(new ol.Feature(gisObj
				.toTransformSystem(bufferGeometry)));
		var filter = {
			filterType : "SPATIAL",
			spatialType : "INTERSECTS",
			wkt : wkt,
			isOriginColumnValue : true
		};
		
		spatialSearchUtils.listAll(that.TITLE, "EXPLANT_AS", filter, function(rows) {
			if (rows && rows.length > 0) {
				that.source.clear();
				var format = new ol.format.WKT();
				var feature = null;
				if (rows.length > 0) {
					feature = format.readFeature(rows[0][MAP.GEOMETRY_ATTR_NAME]);
					var geometry = gisObj.toTransformMap(feature.getGeometry());
					feature.setGeometry(geometry);
					var pkColumn = dataObj.getPkColumn("EXPLANT_AS");
					feature.setId(rows[0][pkColumn]);
					
					that.properties = {};
					var properties = [];
					
					that.properties = rows[0];
					feature.setProperties(properties);
					
					that.source.addFeature(feature);
				}
				selectObj.clear(that.CLASS_NAME);
				$(that.selector).window("resize", {
					height : 600
				});
				that.isSelected = true;
				that.source.addFeature(feature);
				$("#div_edit_detail").show();
				$("#a_edit_remove").show();
				$(".div_edit_coords", that.selector).show();
				$("#a_edit_move img").trigger("click");
				highlightObj.removeYellowFeatures(that.CLASS_NAME);
			}
		});
	},

	/**
	 * 이동
	 * @param {Object} evt 이벤트 객체 (이벤트 버블을 막기 위해 사용)
	 */
	move : function(evt) {
		var that = this;
		
		if(that.feature) {
			$(that.selector).window("resize", { height : 550 });
//			$(".div_edit_draw", that.selector).show();
			
			that.oldfeature = that.feature.clone();
			var features = new ol.Collection();
			features.push(that.feature);
			
			var translate = mapObj.getMap().getInteraction("circleTranslate");
			translate.setFeatures(features);
			mapObj.getMap().activeInteractions("drag", "circleTranslate");
			
			var geometry = that.feature?that.feature.getGeometry():null;
			if (!that.feature.getId()) {
				var center = spatialUtils.calculateCenter(geometry);
				that.getAddress(center);
			}
			else {
				// 면적 출력용 오버레이 생성
				if (this.overlay)
					mapObj.getMap().removeOverlay(this.overlay);
				
				this.overlay = new ol.Overlay({
					element : goog.dom.createDom(goog.dom.TagName.DIV, {'class' : 'kworks-tooltip kworks-tooltip-measure'}),
					offset : [0, -15],
					positioning : 'bottom-center'
				});
				this.overlay.set("name", "editOverlay");
				mapObj.getMap().addOverlay(this.overlay);
				mapObj.getMap().on('pointermove', this.drawing, this); // 마우스 이동 이벤트 지정
				
				this.translateArea(geometry);
			}
			that.coords.viewCoords(geometry, that.address, that.featureArea);
			
			$(".edit_toggle_draw").attr("src", $(".edit_toggle_draw").attr("src").replace("_on", "_off"));
			$(".edit_toggle_move").attr("src", $(".edit_toggle_move").attr("src").replace("_off", "_on"));
		}
		else {
			$.messager.alert(that.TITLE, "선택된 조사지역이 없습니다.");
			evt.stopPropagation();
		}
	},
	
	/**
	 * 이동 시작
	 * @param {Object} evt 이벤트 객체
	 */
	translatestart : function(evt) {
	},
	
	/**
	 * 이동 중
	 * @param {Object} evt 이벤트 객체
	 */
	translating : function(evt) {
		if(evt.dragging) {
			return;
		}

		if(this.feature) {
			var geometry = this.feature.getGeometry();
			this.translateArea(geometry);
		}
	},

	translateArea : function(geometry) {
		var center = spatialUtils.calculateCenter(geometry);
		var coord = center.getCoordinates();

		this.overlay.setPosition(coord);
		
		$(this.overlay.getElement()).html("면적:" + spatialUtils.formatArea(this.featureArea));
	},
	
	/**
	 * 이동 완료
	 * @param {Object} evt 이벤트 객체
	 */
	translateend : function(evt) {
		var that = this;
		
		// 원의 중심 
		var geometry = that.feature?that.feature.getGeometry():null;
		if (geometry) {
			var center = spatialUtils.calculateCenter(geometry);
			that.getAddress(center);
			that.coords.viewCoords(geometry, that.address, that.featureArea);
			that.center = center;
		}
	},
	
	getAddress : function(geometry) {
		var that = this;
		var format = new ol.format.WKT();
		var wkt = format.writeFeature(new ol.Feature(gisObj.toTransformSystem(geometry)));

		$.ajax({
			url : CONTEXT_PATH + "/rest/spatial/" + COMMON.LAND + "/listAll.do",
			async : false,
			type : "POST",
			data : "filterType=SPATIAL&spatialType=INTERSECTS&wkt="+wkt,
			success : function(data) {
				that.pnu = data.rows[0].pnu;
				that.address = that.createAddress(that.pnu);
			},
			error : function(){
				$.messager.alert(that.TITLE, "주소를 찾을 수 없습니다.");
			}
		});
	},

	//pnu가지고 주소만들기
	createAddress : function(pnu) {
		var that = this;
		var address = "";
		address = pnuObj.getAddr(pnu, true);
	
		if (address.length > 0){
			return address;
		}
		else{
			return "주소를 찾을 수 없습니다.";
		}
	},
	
	/**
	 * 도형 저장 (등록 or 수정)
	 */
	save : function() {
		var that = this;
		
		if(that.feature) {
			var layerId = "EXPLANT_AS";
			
			var geometry = that.feature.getGeometry();

			if (that.isSelected && !that.isDraw) {
				that.feature.values_.geometry=geometry;
			}
			else {
				var circle = that.createCircle(geometry);
				that.feature.values_.geometry=circle;
			}
			
			
			var params = null;
			if (!that.isSelected) {	// 신규
				if (that.properties == null) {
					$.messager.alert(that.TITLE, "속성정보는 필수 입력사항입니다.");
					return;
				}else{
					var insertFeatures = [];
					
					that.feature.setGeometry(gisObj.toTransformSystem(that.feature.getGeometry()));
					
					params = {
							featureType : layerId,
							featureNS : MAP.PREFIX,
							featurePrefix : MAP.PREFIX,
							srsName : MAP.PROJECTION,
							geometryName : MAP.GEOMETRY_ATTR_NAME.toUpperCase(),
							serverType : MAP.SERVER_TYPE
						};
					var dtData = windowObj.explantPropertyObj.getProperties();

					that.feature.values_.FTR_IDN = dtData["ftrIdn"];
					that.feature.values_.FTR_CDE = "EX001";
					that.feature.values_.EXP_TYPE = dtData["expType"];
					that.feature.values_.INV_NAM = dtData["invNam"];
					that.feature.values_.INV_DATE = that.properties["invDate"];
					that.feature.values_.DIS_TYPE = dtData["disType"];
					that.feature.values_.DIS_AREA = that.properties["disArea"];
					that.feature.values_.DIS_DENS = dtData["disDens"];
					that.feature.values_.EXP_SIZE = dtData["expSize"];
					that.feature.values_.EXP_STEP = dtData["expStep"];
					that.feature.values_.INV_NOTE = dtData["invNote"];
					that.feature.values_.PNU = that.properties["pnu"];
					
					insertFeatures.push(that.feature);

					var url = CONTEXT_PATH + "/proxy/transaction.do?sysId="+SYS_ID+"&editType=SPATIAL";
					kworks.service.WFS.transaction(url, params, insertFeatures, null, null, function(response) {
						if (response.inserted == 1) {
							if (that.onSave) {
								that.onSave(that.feature);
							}
							$.messager.alert(that.TITLE, "등록 되었습니다.");
							that.close();
							that.clear();
							mapObj.reloadWMS();
						}
						else{
							$.messager.alert(that.TITLE, "등록에 실패하였습니다.");
						}
					});
				}
			}
			//편집
			else{
				if (that.properties == null) {
					$.messager.alert(that.TITLE, "속성정보는 필수 입력사항입니다.");
					return;
				}else{
					var updateFeatures = [];
					that.feature.setGeometry(gisObj.toTransformSystem(that.feature.getGeometry()));
					
					params = {
							featureType : layerId,
							featureNS : MAP.PREFIX,
							featurePrefix : MAP.PREFIX,
							srsName : MAP.PROJECTION,
							geometryName : MAP.GEOMETRY_ATTR_NAME.toUpperCase(),
							serverType : MAP.SERVER_TYPE
						};
					var dtData = windowObj.explantPropertyObj.getProperties();

					that.feature.values_.FTR_IDN = dtData["ftrIdn"];
					that.feature.values_.FTR_CDE = "BE001";
					that.feature.values_.EXP_TYPE = dtData["expType"];
					that.feature.values_.INV_NAM = dtData["invNam"];
					that.feature.values_.INV_DATE = that.properties["invDate"];
					that.feature.values_.DIS_TYPE = dtData["disType"];
					that.feature.values_.DIS_AREA = that.properties["disArea"];
					that.feature.values_.DIS_DENS = dtData["disDens"];
					that.feature.values_.EXP_SIZE = dtData["expSize"];
					that.feature.values_.EXP_STEP = dtData["expStep"];
					that.feature.values_.INV_NOTE = dtData["invNote"];
					that.feature.values_.PNU = that.properties["pnu"];
					
					updateFeatures.push(that.feature);

					var url = CONTEXT_PATH + "/proxy/transaction.do?sysId="+SYS_ID+"&editType=SPATIAL";
					kworks.service.WFS.transaction(url, params, null, updateFeatures, null, function(response) {
						if (response.updated == 1) {
							if (that.onSave) {
								that.onSave(that.feature);
							}
							$.messager.alert(that.TITLE, "편집 되었습니다.");
							that.close();
							that.clear();
							mapObj.reloadWMS();
						}else{
							$.messager.alert(that.TITLE, "편집에 실패하였습니다.");
						}
					});
				}
			}
		}
		else {
			$.messager.alert(that.TITLE, "공간정보는 필수 입력사항입니다.");
		}
	},
	
	createCircle : function(geometry) {
		var center = geometry.getCenter();
		var radius = geometry.getRadius();
		
		var unit = 18 * (Math.PI / 180.0);
		var startAngle = 0 * (Math.PI / 180.0);
		
		var coords = [];
		for (var i = 0; i < 20; i++) {
			var angle = startAngle + (i * unit);
			var x = radius * Math.cos(angle);
			var y = radius * Math.sin(angle);
			
			var coord = [];
			coord.push(center[0] + x);
			coord.push(center[1] + y);
			
			coords.push(coord);
		}
		
		var coord = coords[0];
		coords.push(coord);
		
		return new ol.geom.Polygon([coords]);		
	},
	
	close : function() {
		var that = this;

		that.clear();
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.onSave = null;

		windowObj.explantPropertyObj.close();		
	}
	
};

/**
 * 좌표목록
 */
windowObj.explantEditObj.coords = {
		
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
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 행 선택
		$("#tby_edit_coords").on("click", "tr", function() {
			var node = this;
			that.select(node);
		});
	},
	
	/**
	 * 좌표 목록 기능 정리
	 */
	clear : function() {
		var that = this;
		that.source.clear();
		
		$("#td_edit_address").text("");
		$("#td_edit_area").html("0.0");
		$("#txt_edit_coords_coord_x").html("0.0");
		$("#txt_edit_coords_coord_y").html("0.0");
	},
	
	/**
	 * 좌표 목록 표시
	 * @param {ol.geom} geometry 공간 정보
	 */
	viewCoords : function(geometry, address, area) {
		var that = this;
		
		that.clear();
		$("#p_edit_coords_menu").hide();
		
		if(geometry) {
			var center = spatialUtils.calculateCenter(geometry); 
			if(geometry instanceof ol.geom.Circle) {
				that.viewCenter(center.getCoordinates());
			}
			else if(geometry instanceof ol.geom.Polygon) {
				that.viewCenter(center.getCoordinates());
			}
			else if(geometry instanceof ol.geom.MultiPolygon) {
				that.viewCenter(center.getCoordinates());
			}
			else {
				$.messager.alert(that.TITLE, "지원되지 않는 공간 타입입니다.");
			}
			
			$("#td_edit_address").text(address);
			$("#td_edit_area").html(spatialUtils.formatArea(area));
		}
	},
	
	/**
	 * 좌표 반영
	 * @param {Array<Number>} coord 좌표 (x, y)
	 */
	viewCenter : function(coord) {
		if (coord) {
			$("#txt_edit_coords_coord_x").html(coord[0]);
			$("#txt_edit_coords_coord_y").html(coord[1]);
		}
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
	}
};