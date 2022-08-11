/**
 * 공간 편집
 * @namespace
 */
var editPolicyObj = {
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "정책편집",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "policyEdit",
	
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
		that.choiceObj.init();
		
		that.initGis();
		
		that.coords.init(that);
		that.offset.init(that);
		that.repo.initUi();
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					that.close();
					$.messager.alert(that.TITLE, "정책현황이 저장되었습니다.");
				}
				else {
					$.messager.alert(that.TITLE, "정책현황 저장에 실패하였습니다.");
				}
			}
		});
		
	},
	
	/**
	 * GIS 초기화
	 */
	initGis : function() {
		var that = this;

		// 선택 레이어 생성 및 등록
		that.source = new ol.source.Vector();
		var layer = new ol.layer.Vector({
			id : "addPolicy",
			source : that.source
		});
		mapObj.getMap().addLayer(layer);
		that.source.on("addfeature", that.addFeature, that);

		// 면 그리기 인터렉션 생성 및 등록
		var options = {};
		options.type = "Polygon";
		options["source"] = that.source;
		var interaction = new ol.interaction.Draw(options);
		interaction.set("id", "PolicyAddPolygon"); // PolicyAddPolygon
		interaction.set("name", "PolicyAddPolygon");
		mapObj.getMap().addInteraction(interaction);
		interaction.setActive(false);
		interaction.on("drawend", that.drawend, that);

		// 도형 편집 인터렉션
		var modify = new kworks.interaction.Modify({
			features : new ol.Collection()
		});
		modify.set("id", "PointEditPolicy"); // 점편집 PointEditPolicy
		modify.set("name", "PointEditPolicy");
		mapObj.getMap().addInteraction(modify);
		modify.setActive(false);
		modify.on("modifyend", that.modifyend, that);

		// 도형 이동 인터렉션
		var translate = new kworks.interaction.Translate({
			features : new ol.Collection()
		});
		translate.set("id", "PolygonMovePolicy"); // 도형이동 인터럭션
		translate.set("name", "PolygonMovePolicy");
		mapObj.getMap().addInteraction(translate);
		translate.setActive(false);

		// 이동 기능 활성 상태에서 그 도형이 포함되는 위치에서 기능이 변경되는 경우 커서가 변경되지 않어 추가한 코드
		translate.on("change:active", function() {
			if (!this.getActive()) {
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
		$("#a_policy_edit_regist").click(function() {
			that.regist();
		});
		
		// 선택
		$("#a_policy_edit_select").click(function() {
			that.select();
		});
		
		// 신규등록 - 그리기
		$("#a_policy_edit_draw").click(function() {
			that.draw();
			that.clearTools();
			$("#a_policy_attEdit_save").show();
			$("#a_policy_edit_save").show();
		});
		
		// 선택 - 점편집
		$("#a_policy_edit_modify").click(function(evt) {
			that.modify(evt);
			that.clearTools();
			$("#a_policy_attEdit_save").show();
			$("#a_policy_edit_save").show();
		});
		
		// 선택 - 이동
		$("#a_policy_edit_move").click(function(evt) {
			that.move(evt);
			that.clearTools();
			$("#a_policy_edit_save").show();
		});
		
		// 속성 편집
		$("#a_policy_attEdit_save").click(
				function(evt) {
					if (that.feature) {
						var fid = that.feature.getId();
						var onLoad = null;
						//windowObj.policyAddRegisterObj.open(that.layerName, null, that.feature, null, onLoad);
						if (fid) {
							// 편집
							windowObj.editPolicyRegisterObj.open(that.layerName, [ fid ], that.feature,onLoad);
						} else {
							// 신규입력
							if (!that.feature.getProperties()["FTR_IDN"]) {
								that.feature.setProperties({
									FTR_IDN : ftrIdnObj.getData(that.layerName)
								});
							}
							windowObj.editPolicyRegisterObj.open(that.layerName, null, that.feature, null,onLoad);
						}
					} else {
						$.messager.alert(that.TITLE, "공간객체가 먼저 생성이 되어야 합니다.");
					}
				});
		
		// 저장
		$("#a_policy_edit_save").click(function() {
			that.save();
			that.repo.save();
			return false;
		});
		
		// 취소
		$("#a_policy_edit_cancel").click(function() {
			that.clear();
			return false;
		});
		
		// 삭제
		$("#a_policy_edit_remove").click(function() {
			$.messager.confirm(that.TITLE, "선택하신 시설물을 삭제하시겠습니까?", function(isTrue) {
				if(isTrue) {
					that.remove();
					
				}
			});
			return false;
		});
		
		// 닫기
		$("#a_policy_edit_close").click(function() {
			that.close();
		});
	},
	
	/**
	 * 편집 초기화
	 */
	clear : function() {
		var that = this;
		
		$(that.selector).window("resize", { height : 180 });
		$("#div_policy_edit_detail").hide();
		
		that.source.clear();
		that.feature = null;
		
		that.coords.clear();
		that.offset.clear();
		
		that.clearTools();
		
		windowObj.registerObj.close();
		
		highlightObj.removeYellowFeatures(that.CLASS_NAME);
		
		$("#a_policy_map_tool_bass img").trigger("click");
	},
	
	/**
	 * 도구 초기화 (자르기, 병합, 분할 초기화)
	 */
	clearTools : function() {
		var that = this;
		
		$("#a_policy_edit_merge_features", that.selector).hide();
		$("#a_policy_edit_split_features", that.selector).hide();
	},
	
	repoSave : function(){
		var that = this;
		that.repo.save();
		
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
		
		var url = CONTEXT_PATH + "/policy/editPolicyPage.do";
		var windowOptions = {
			title : that.TITLE,
			width : width,
			height : 600,
			top : 120,
			right : 50,
			onClose : function() {
				that.clear();
			}
		};
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.clear();
			
			that.bindEvents();
			
			that.coords.loadCrs();
			that.coords.bindEvents();
			
			that.offset.bindEvents();
			
			$("#td_policy_edit_layer_title").text(dataAlias);
		});
		that.selector = "#" + id;
	},
	
	/**
	 * 신규 등록
	 */
	regist : function() {
		var that = this;
		that.clear();
		$(that.selector).window("resize", {
			height : 600
		});
		$("#div_policy_edit_detail").show();
		if (that.drawType == "Point") {
			$("#a_policy_edit_clip").hide();
			$("#a_policy_edit_merge").hide();
		} else {
			$("#a_policy_edit_clip").show();
			$("#a_policy_edit_merge").show();
		}
		$("#a_policy_edit_split").hide();

		$("#a_policy_edit_remove").hide();
		$("#a_policy_edit_draw img").trigger("click");
	},
	
	/**
	 * 선택
	 */
	select : function() {
		var that = this;
		that.clear();

		selectObj.active(that.CLASS_NAME, "Point", "drawend", function(evt) {
			var geom = evt.feature.getGeometry();
			that.search(geom);
		}, false);

	},
	
	/**
	 * 그리기
	 */
	draw : function() {
		var that = this;
		$(".div_policy_edit_markup", that.selector).hide();
		$(".div_policy_edit_coords", that.selector).show();
		mapObj.getMap().activeInteractions("drag", "PolicyAddPolygon"); // PolicyAddPolygon
																		// //
																		// initGis
																		// 에
																		// activeInteractions
		var geometry = that.feature ? that.feature.getGeometry() : null;
		that.coords.show(that.drawType, geometry);
	},
	
	/**
	 * 점편집
	 * 
	 * @param {Object}
	 *            evt 이벤트 객체 (이벤트 버블을 막기 위해 사용)
	 */
	modify : function(evt) {
		var that = this;
		if (that.feature) {
			var features = new ol.Collection();
			features.push(that.feature);
			var modify = mapObj.getMap().getInteraction("PointEditPolicy");
			modify.setFeatures(features);
			mapObj.getMap().activeInteractions("drag", "PointEditPolicy");
			$(".div_policy_edit_markup", that.selector).hide();
			$(".div_policy_edit_coords", that.selector).show();
			var geometry = that.feature ? that.feature.getGeometry() : null;
			that.coords.show(that.drawType, geometry);
		} else {
			$.messager.alert(that.TITLE, "선택된 시설물이 없습니다.");
			evt.stopPropagation();
		}
	},
	/**
	 * 이동
	 * 
	 * @param {Object}
	 *            evt 이벤트 객체 (이벤트 버블을 막기 위해 사용)
	 */
	move : function(evt) {
		var that = this;
		if (that.feature) {
			var features = new ol.Collection();
			features.push(that.feature);
			var translate = mapObj.getMap().getInteraction("editTranslate");
			translate.setFeatures(features);
			mapObj.getMap().activeInteractions("drag", "editTranslate");
			$(".div_policy_edit_markup", that.selector).hide();
			$(".div_policy_edit_move", that.selector).show();
			that.coords.clear();
		} else {
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
	 * 공간검색
	 * @param {ol.geom} geom 공간정보를 검색할 지점
	 */
	search : function(geom) {
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

		spatialSearchUtils.listAll(
			that.TITLE,
			that.layerName,filter,
			function(rows) {
				if (rows && rows.length > 0) {
					that.source.clear();
					var format = new ol.format.WKT();
					var feature = null;
					if (rows.length > 0) {
						feature = format.readFeature(rows[0][MAP.GEOMETRY_ATTR_NAME]);
						var geometry = gisObj.toTransformMap(feature.getGeometry());
							feature.setGeometry(geometry);
							var pkColumn = dataObj.getPkColumn(that.layerName);
								feature.setId(rows[0][pkColumn]);

							var properties = [];
							var data = dataObj.getDataByDataId(
								that.layerName, true);
							var dataFields = data.kwsDataFields;
								for (var i = 0, len = dataFields.length; i < len; i++) {
								var dataField = dataFields[i];
								var indictTy = dataField.indictTy;
								if (indictTy != "HIDE" && indictTy != "WKT") {
									var field = camelcaseUtils.underbarToCamelcase(dataField.fieldId);
									var value = rows[0][field];
									if (value) {
										properties[dataField.fieldId] = value;
										}
									}
								}
								feature.setProperties(properties);
								that.source.addFeature(feature);
							}
							selectObj.clear(that.CLASS_NAME);
							$(that.selector).window("resize", {
								height : 600
							});
							$("#div_policy_edit_detail").show();
							$(".div_policy_edit_markup",
									that.selector).hide();
							$("#a_policy_edit_remove").show();
							$(".div_policy_edit_coords",
								that.selector).show();
							$("#a_policy_edit_modify img").trigger("click");

							highlightObj.removeYellowFeatures(that.CLASS_NAME);
						}
					});
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
		
		var modify = mapObj.getMap().getInteraction("PointEditPolicy");
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
//		var data = that.data;
		var ftrIdn = that.feature.values_.FTR_IDN;
		var plcyTit = that.feature.values_.PLCY_TIT;
		var plcyDep = that.feature.values_.PLCY_DEP;
		var repoDoc = that.feature.repoDoc;
		
		var repoPrt = repoDoc;
		if (that.feature) {

			var insertFeatures = [];
			var updateFeatures = [];
			if (that.feature.getId()) {
				updateFeatures.push(that.feature);
			} else {
//				var properties = that.feature.getProperties();
				insertFeatures.push(that.feature);
			}

			mapObj.getMap().getInteraction("PointEditPolicy").clearFeatures();

			that.feature.setGeometry(gisObj.toTransformSystem(that.feature
					.getGeometry()));

			var url = CONTEXT_PATH + "/policy/insert/transaction.do?sysId=" + SYS_ID
					+ "&ftrIdn="+ ftrIdn + "&plcyTit=" + encodeURI(encodeURIComponent(plcyTit));
			var params = {
				featureType : that.layerName,
				featureNS : MAP.PREFIX,
				featurePrefix : MAP.PREFIX,
				srsName : MAP.PROJECTION,
				geometryName : MAP.GEOMETRY_ATTR_NAME.toUpperCase(),
				serverType : MAP.SERVER_TYP
			};
			if(typeof plcyTit == "undefined" || plcyTit ===null || plcyTit ==""){
                    $.messager.alert(that.TITLE, "정책명은 필수 입력사항입니다.");
				}
				else if(typeof plcyDep == "undefined" || plcyDep ===null || plcyDep ==""){
					$.messager.alert(that.TITLE, "담당부서는 필수 입력사항입니다.");
				}
				else{
					kworks.service.WFS.transaction(url, params, insertFeatures,
					updateFeatures, null, function(response) {
						if (response.inserted == 1) {
							if (that.onSave) {
								that.onSave(that.feature);
							}
							var url = CONTEXT_PATH + "/policy/"+ ftrIdn +"/insertplcyRepo.do"
							var params ={
								plcyTit : plcyTit,
								repoDoc : repoDoc,
								repoPrt : repoPrt,
								ftrIdn : ftrIdn
							};
							$.post(url,params).done(function(response) {
								//alert("보고서가 등록되었습니다.");
							}).fail(function(result) {
								alert("보고서 등록에 실패했습니다.");
							});
							
							$.messager.alert(that.TITLE, "등록 되었습니다.");
							that.close();
							that.clear();
							mapObj.reloadWMS();
						}
						else if (response.updated == 1) {
								if (that.onSave) {
									that.onSave(that.feature);
								}
								var url = CONTEXT_PATH + "/policy/"+ ftrIdn +"/updateplcyRepo.do"
								var params ={
									plcyTit : plcyTit,
									repoDoc : repoDoc,
									repoPrt : repoPrt,
									ftrIdn : ftrIdn
								};
								$.post(url,params).done(function(response) {
									//alert("보고서가 등록되었습니다.");
								}).fail(function(result) {
									alert("보고서 등록에 실패했습니다.");
								});
								$.messager.alert(that.TITLE, "등록 되었습니다.");
								that.close();
								that.clear();
								mapObj.reloadWMS();
							}
						else {
							$.messager.alert(that.TITLE, "등록에 실패하였습니다.");
						}
					});
				}
		} else {
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
 * 공간 편집 좌표 목록
 * @namespace
 */
editPolicyObj.coords = {
		
	/**
	 * 상위객체 - 공간 편집
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "정책지도 좌표목록",
		
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
			id : "coordsLayerPolicy",
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
		$("#a_policy_edit_coords_apply").click(function() {
			that.apply();
		});
		
		// 행 선택
		$("#tby_policy_edit_coords").on("click", "tr", function() {
			var node = this;
			that.select(node);
		});
		
		// 위로
		$("#a_policy_edit_coords_up").click(function() {
			that.up();
		});
		
		// 아래로
		$("#a_policy_edit_coords_down").click(function() {
			that.down();
		});
		
		// 추가
		$("#a_policy_edit_coords_add").click(function() {
			that.add();
		});
		
		// 삭제
		$("#a_policy_edit_coords_remove").click(function() {
			that.remove();
			that.highlight();
		});		
	},
	
	/**
	 * 좌표계 로딩
	 */
	loadCrs : function() {
		
		var data = coordinateObj.getData();
		$("#sel_policy_edit_proj").val(MAP.PROJECTION);
	},
	
	/**
	 * 좌표 목록 기능 정리
	 */
	clear : function() {
		var that = this;
		that.source.clear();
		$("#tby_policy_edit_coords").html("");
	},
	
	/**
	 * 좌표 태그 생성
	 * @param {Array<Number>} coords 좌표 (x, y)
	 */
	createCoordTag : function(coords) {
		var tagStr = "";
		tagStr += '<tr>';
		tagStr += '<td class="td_policy_edit_coords_coord_seq"></td>';
		tagStr += '<td><input type="text" value="'+coords[0]+'" class="w_80 inputText txt_policy_edit_coords_coord_x"></td>';
		tagStr += '<td><input type="text" value="'+coords[1]+'" class="w_80 inputText txt_policy_edit_coords_coord_y"></td>';
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
			$("#p_policy_edit_coords_menu").hide();
			if(!geometry) {
				$("#tby_policy_edit_coords").html(that.createCoordTag([0, 0]));
				that.showSeq();
			}
		}
		else {
			$("#p_policy_edit_coords_menu").show();
		}
		
		if(geometry) {
			var geom = geometry.clone();
			var proj = $("#sel_policy_edit_proj").val();
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
			$("#tby_policy_edit_coords").html(tagStr);
			that.showSeq();
		}
	},
	
	/**
	 * 순번 표시
	 */
	showSeq : function() {
		var seq = 1;
		$("#tby_policy_edit_coords tr td.td_policy_edit_coords_coord_seq").each(function() {
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
			$("#tby_policy_edit_coords tr").removeClass("on");
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
		$("#tby_policy_edit_coords tr.on").each(function() {
			var node = this;
			var x = parseFloat($(node).find(".txt_policy_edit_coords_coord_x").val());
			var y = parseFloat($(node).find(".txt_policy_edit_coords_coord_y").val());
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
		$("#tby_policy_edit_coords tr").each(function() {
			var node = this;
			var x = parseFloat($(node).find(".txt_policy_edit_coords_coord_x").val());
			var y = parseFloat($(node).find(".txt_policy_edit_coords_coord_y").val());
			coords.push([x, y]);
		});
		
		var proj = $("#sel_policy_edit_proj").val();
		var mapProj = mapObj.getMap().getView().getProjection();
		if(proj != mapProj) {
			for(var i in coords) {
				coords[i] = new ol.geom.Point(coords[i]).transform(proj, mapProj).getCoordinates();
			}
		}
		that.parent.applyCoords(coords);
	},
	
	/**
	 * 위로 이동
	 */
	up : function() {
		var that = this;
		var node = $("#tby_policy_edit_coords tr.on");
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
		var node = $("#tby_policy_edit_coords tr.on");
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
		var node = $("#tby_policy_edit_coords tr.on");
		var tagStr = that.createCoordTag([0, 0]);
		if(node.length > 0) {
			$(tagStr).insertAfter(node);
		}
		else {
			$("#tby_policy_edit_coords").append(tagStr);
		}
		that.showSeq();
	},
	
	/**
	 * 좌표 삭제
	 */
	remove : function() {
		var that = this;
		var node = $("#tby_policy_edit_coords tr.on");
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
editPolicyObj.offset = {
		
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
		$("#a_policy_edit_offset_apply").click(function() {
			that.apply();
		});
	},
	
	/**
	 * 이동 초기화
	 */
	clear : function() {
		$("#txt_policy_edit_offset_x").val(0);
		$("#txt_policy_edit_offset_y").val(0);
	},
	
	/**
	 * 이동 적용
	 */
	apply : function() {
		var that = this;
		var x = parseFloat($("#txt_policy_edit_offset_x").val());
		var y = parseFloat($("#txt_policy_edit_offset_y").val());
		that.parent.applyOffset(x, y);
	}
	
};

/**
 * 선택 객체
 * @type {Object}
 */
editPolicyObj.choiceObj = {
		
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

//레포트 등록창 구현
editPolicyObj.repo = {

		/**
		* 제목
		* @type {String}
		*/
		TITLE : "보고서 등록",

		selector : "#policy_Repo_Add",
		
		/**
		 * 클래스 명
		 * 
		 * @type {String}
		 */
		CLASS_NAME : "policyRepoAdd",
		
		isCreated : false,
		
		initUi : function() {
			var that = this;
			
			$("form", that.selector).ajaxForm({
				dataType : "json",
				beforeSubmit : function(arr, form) {
					for(var i=0, len=arr.length; i < len; i++) {
						var obj = arr[i];
						var name = obj.name;
						if(name == "repoDoc") {
							// textarea 의 내용이 아닌 ckeditor 의 내용 입력
							obj.value = CKEDITOR.instances.txa_repo_add_doc.getData();
							if(!obj.value) {
								alert("내용을 입력하여 주십시오.");
								form.find("input[name=" + name + "]").focus();
								return false;
							}
						}
					}
				},
				success : function(result) {
					if(result && result.rowCount && result.rowCount == 1) {
						that.close();
						$.messager.alert(that.TITLE, "보고서가 등록 되었습니다.");
					}
					else {
						alert("보고서 등록에 실패했습니다.");
					}
				}
			});
		},
		
		save : function() {
			var that = this;
			var url = CONTEXT_PATH + "/policy/Repo/addRepoRgs.do";
			$("form", that.selector).attr("action", url);
			$("form", that.selector).submit();
		},
		
		close : function() {
			var that = this;
			
			that.isCreated = false;
			
			windowObj.removeWindow(that.selector);
			that.selector = null;
		}
};

