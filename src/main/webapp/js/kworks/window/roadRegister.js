windowObj.roadRegisterObj = {

	/**
	 * 제목
	 * @type {String}
	 */
	title : null,

	/**
	 * 클래스 명
	 * 
	 * @type {String}
	 */
	CLASS_NAME : "RoadRegister",

	/**
	 * 선택자
	 * 
	 * @type {String}
	 */
	selector : null,

	dataId : null,

	/**
	 * 객체 아이디 목록
	 * 
	 * @type {Array.<Number>}
	 */
	fids : [],
	
	/**
	 * KWS_DATA 정보
	 * 
	 * @type {Array.<Object>}
	 */
	data : null,
	
	dataFields : null,

	/**
	 * 도로대장 아이디
	 * 
	 * @type {String}
	 */
	regIdn : null,
	rotIdn : null,
	rotNam : null,
	secIdn : null,
	/**
	 * 객체 아이디 목록
	 * 
	 * @type {Array.<Number>}
	 */
	featureInfos : [],

	/**
	 * 인덱스 (현재 객체 아이디)
	 * 
	 * @type {Number}
	 */
	index : 0,

	/**
	 * KWS_DATA_FILED_CTGRY 정보
	 * 
	 * @type {Array.<Object>}
	 */
	categories : null,

	/**
	 * 호출자 (result - 대장조회, edit - 속성편집)
	 * 
	 * @type {String}
	 */
	feature : null,
	
	/**
	 * 프로퍼티 목록
	 */
	properties : {},
	
	/**
	 * 저장 시 호출할 콜백 함수
	 */
	onSave : null,
	
	/**
	 * 데이터 로드 후 호출할 콜백 함수  
	 */
	onLoad : null,

	// 초기화
	open : function(regIdn, featureInfos, dataFields, feature, onSave, onLoad) {
		var that = this;

		if (that.selector) {
			that.close();
		}

		that.regIdn = regIdn;
		that.featureInfos = featureInfos;
		that.dataFields = dataFields;
		that.feature = feature;
		that.onSave = onSave;
		that.onLoad = onLoad;

		// 데이터 필드 카테고리 불러오기
		that.loadDataFieldCategory().done(
				function(response) {
					var allCategories = response.rows;
					var dataId = roadRegObj.getLayerId(that.regIdn);
					that.data = dataObj.getDataDetailByDataId(dataId);

					var url = CONTEXT_PATH + "/window/roadRegister/page.do";
					var windowOptions = {
						width : 380,
						height : 600,
						tools : "#div_window_road_register_tools",
						onClose : function() {
							that.close();
						}
					};

					var title = that.data.dataAlias;
					var id = windowObj.createWindow(that.CLASS_NAME, title,
							url, null, windowOptions, function() {
								that.createCategoryBtn(allCategories);
								that.createCategoryDatagrid(allCategories);

								if (that.feature) {
									that.title = "속성편집";
									if (that.fids) {
										that.setMode("edit");
									} else {
										that.setMode("new");
									}
								} 
								else {
									that.title = "대장 조회";
									that.setMode("view");
								}

								that.initUi();
								that.checkAuthor();
								that.bindEvents();
								$(".a_category:first", that.selector).trigger("click");
							});
					that.selector = "#" + id;
				});
	},

	initUi : function() {
		$("#div_window_road_register_tools_prev").hide();
	},

	/**
	 * 권한 체크
	 */
	checkAuthor : function() {
		var that = this;
		
		var featureInfo = that.getFeatureInfo();
		var dataAuthor = dataAuthorObj.getDataAuthor(featureInfo.dataId);
		var params = { sysId : SYS_ID };
		
		if(PROJECT_CODE != "gn") {
			if (that.data.prntngAt == 'Y' && dataAuthor
					&& dataAuthor.prntngAt == 'Y') {
				$(".a_view_print", that.selector).show();
			} else {
				$(".a_view_print", that.selector).hide();
			}
		} else {
			if (params.sysId != "11" && that.data.roadRegAt == 'Y' && dataAuthor && dataAuthor.prntngAt == 'Y') {
				// 대장인쇄
				$(".a_view_road_regstr", that.selector).hide();
				// 인쇄
				$(".a_view_print", that.selector).show();
				
			} else {
				// 대장인쇄
				$(".a_view_road_regstr", that.selector).show();
				// 인쇄
				$(".a_view_print", that.selector).hide();
			}
		}
		
		if (that.data.editAt == 'Y' && dataAuthor && dataAuthor.editAt == 'Y') {
			$(".a_view_edit", that.selector).show();
		} else {
			$(".a_view_edit", that.selector).hide();
		}

		if (that.data.histManageAt == 'Y') {
			$(".a_manage_history", that.selector).show();
		} else {
			$(".a_manage_history", that.selector).hide();
		}

		if (that.data.photoManageAt == 'Y') {
			$(".a_photo_manage", that.selector).show();
		} else {
			$(".a_photo_manage", that.selector).hide();
		}
		
		// 도로대장 관리서비스에서 총괄(RDL_SECT_LS) 데이터에서 사용
		if (dataAuthor.dataId == 'RDL_SECT_LS') {
			// 통합출력
			$(".a_united_road_regstr", that.selector).show();
			// 영상관리
			$(".a_video_manage", that.selector).hide();// 사용안함.
			// 구간도면 관리
			$(".a_section_plan", that.selector).show();
			// 단위도면 관리
			$(".a_local_plan", that.selector).show();
		} else {
			$(".a_united_road_regstr", that.selector).hide();
			$(".a_video_manage", that.selector).hide();
			$(".a_section_plan", that.selector).hide();
			$(".a_local_plan", that.selector).hide();
		}
		
		if(dataAuthor.dataId == 'ETL_NTCP_PS') {
			$(".a_mesrSgnalSttusExaminHist", that.selector).show();
		} else {
			$(".a_mesrSgnalSttusExaminHist", that.selector).hide();
		}
	},

	setMode : function(mode) {
		var that = this;

		if (mode == "view") {
			if (that.index == 0) {
				$("#div_window_road_register_tools_prev").hide();
			} else {
				$("#div_window_road_register_tools_prev").show();
			}

			if (that.index == that.featureInfos.length - 1) {
				$("#div_window_road_register_tools_next").hide();
			} else {
				$("#div_window_road_register_tools_next").show();
			}

			$(".div_tool_edit", that.selector).hide();
			$(".div_tool_view", that.selector).show();
			
			that.loadView();
			
		} else if (mode == "new") {
			$("#div_window_road_register_tools_prev").hide();
			$("#div_window_road_register_tools_next").hide();
			$(".div_tool_edit", that.selector).show();
			$(".div_tool_view", that.selector).hide();
			
			that.loadNew();
			
		} else if (mode == "edit") {
			$("#div_window_road_register_tools_prev").hide();
			$("#div_window_road_register_tools_next").hide();
			$(".div_tool_edit", that.selector).show();
			$(".div_tool_view", that.selector).hide();
			
			that.loadEdit();
			
		} else {
			$.messager.alert(that.title, "정의되지 않은 타입입니다.");
		}
	},

	bindEvents : function() {
		var that = this;

		// 이전
		$("#div_window_road_register_tools_prev").click(function() {
			that.index--;
			$("#div_window_road_register_tools_next").show();
			if (that.index == 0) {
				$("#div_window_road_register_tools_prev").hide();
			}
			that.loadView();
			return false;
		});

		// 다음
		$("#div_window_road_register_tools_next").click(function() {
			that.index++;
			$("#div_window_road_register_tools_prev").show();
			if (that.index == that.fids.length - 1) {
				$("#div_window_road_register_tools_next").hide();
			}
			that.loadView();
			return false;
		});

		// 카테고리 선택
		$(".a_category", that.selector).click(
				function() {
					var element = $(this);
					var imgElement = element.find("img");
					$(".a_category img").each(
							function() {
								var img = $(this);
								img.attr("src", img.attr("src").replace("_on",
										"_off"));
							});
					imgElement.attr("src", imgElement.attr("src").replace(
							"_off", "_on"));

					$(".div_content", that.selector).hide();
					var categoryId = element.attr("data-category-id");
					var className = "div_content_" + categoryId;
					$("." + className).show();
					$("." + categoryId).propertygrid("resize");
				});

		// 인쇄
		$(".a_view_print", that.selector).click(function() {
			that.print();
			return false;
		});
		
		// 대장인쇄
		$(".a_view_road_regstr", that.selector).click(function() {
			that.roadRegstrPrint();
			return false;
		});

		// 통합출력
		$(".a_united_road_regstr", that.selector).click(function() {
			that.unitedRoadRegstrPrint();
			return false;
		});
		
		// 이력 관리
		$(".a_manage_history", that.selector).click(function() {
			that.manageHistory();
			return false;
		});

		// 사진 관리
		$(".a_photo_manage", that.selector).click(function() {
			that.managePhoto();
			return false;
		});
		
		// 구간도면
		$(".a_section_plan", that.selector).click(function() {
			that.sectionPlan();
			return false;
		});
		
		// 단위도면
		$(".a_local_plan", that.selector).click(function() {
			that.localPlan();
			return false;
		});

		// 편집
		$(".a_view_edit", that.selector).click(function() {
			that.setMode("edit");
			return false;
		});

		// 닫기
		$(".a_view_close", that.selector).click(function() {
			that.close();
			return false;
		});

		// 수정
		$(".a_edit_update", that.selector).click(function() {
			if(that.feature) {
				var valid = that.validate();
				if(valid.isValid) {
					var props = that.getProperties();
					if(props) {
						that.feature.setProperties(props);
						if(that.onSave) {
							that.onSave();
						}
						that.close();
					} else {
						$.messager.alert(that.title, "편집한 내용이 없습니다.");
					}
				} else {
					$.messager.alert(that.title, valid.message);
				}
			}
			else {
				// 민원관리
				if(that.dataId == "RDT_RSER_MA") {
					var fids = that.fids;
					var props = that.getProperties();
					roadCivilAppealObj.modify.save(fids, props);
				}
				else if(that.dataId == "WTT_WSER_MA") {
					var fids = that.fids;
					var props = that.getProperties();
					wrppCivilAppealObj.modify.save(fids, props);
				}
				else if(that.dataId == "SWT_SSER_MA") {
					var fids = that.fids;
					var props = that.getProperties();
					swgeCivilAppealObj.modify.save(fids, props);
				}
				else if(that.dataId == "SWT_SPMT_MA") {
					var fids = that.fids;
					var props = that.getProperties();
					drngEqpCnfmPrmisnObj.modify.save(fids, props);
				}
				else {
					that.update();
				}
			}
			return false;
		});

		// 수정 취소
		$(".a_edit_cancel", that.selector).click(function() {
			if (that.feature) {
				that.close();
			} else {
				that.setMode("view");
			}
			return false;
		});
	},
	
	getFtrCde : function() {
		var that = this;
		
		var ftrCdeNm = null;
		var categories = that.categories;
		// 데이터의 카테고리
		var checked = false;
		for ( var id in categories) {
			var data = $("."+id, that.selector).propertygrid("getData");
			for(var i=0, len=data.rows.length; i < len; i++) {
				var row = data.rows[i];
				if(row.fieldId == "FTR_CDE") {
					checked = true;
					ftrCdeNm = row.value;
					break;
				}
			}
			if(checked) {
				break;
			}
		}
		
		var domnId = null;
		var dataInfo = dataObj.getDataByDataId(that.dataId, true);
		var fields = dataInfo.kwsDataFields;
		for(var i=0, len=fields.length; i < len; i++) {
			if(fields[i].fieldId == "FTR_CDE") {
				domnId = fields[i].domnId;
				break;
			}
		}
		
		if(domnId) {
			var codes = domnCodeObj.getData(domnId);
			for(var i=0, len=codes.length; i < len; i++) {
				if(codes[i].codeNm == ftrCdeNm) {
					return codes[i].codeId;					
				}
			}
		}
		
		return null;
	},

	// 데이터 필드 카데고리 로드
	loadDataFieldCategory : function() {
		var url = CONTEXT_PATH + "/rest/dataFieldCtgry/listAll.do";
		var promise = $.get(url);
		return promise;
	},

	// 데이터 정보 로딩
	loadView : function() {
		var that = this;
		var params = { sysId : SYS_ID };
		
		var featureInfo = that.getFeatureInfo();
		
		var layers = [];
		layers.push(featureInfo.dataId);
		
		var ids = [];
		ids.push(featureInfo.ftrIdn);
		var featureIds = [];
		featureIds.push(ids);
		
		var refId = [];
		refId.push(featureInfo.refId);
		var refIds = [];
		refIds.push(refId);
		
		var request = {};
		request.regIdn = that.regIdn;
		request.layerIds = layers;
		request.featureIds = featureIds;
		request.ftrIdn = refIds;	// 도로구역 조회용
		
		var url = CONTEXT_PATH + "/roadRegstr/searchRoadRegister.do";	///
		$.post(url, request).done(function(response) {
			if(response && response.rows) {
				var data = response.rows[0];
			
				// 카테고리 전체
				var categories = that.categories;
				// 데이터의 카테고리
				var dataFields = that.dataFields;
				that.rotnam = data.rotNam;
				that.rotIdn = data.rotIdn;
				that.secIdn = data.secIdn;
				
				for ( var id in categories) {
					// 카테고리 전체
					var category = categories[id];
					var rows = [];
					for (var i = 0, len = dataFields.length; i < len; i++) {
						var dataField = dataFields[i];
						var indictTy = dataField.indictTy;
						var sysTy = dataField.sysTy;
						
						if(PROJECT_CODE != "gn") {
							if (indictTy != "HIDE" && indictTy != "WKT") {
								if (category.fieldCtgryId == dataField.fieldCtgryId) {
									var field = camelcaseUtils.underbarToCamelcase(dataField.fieldId);
									var value = data[field];
									
									if(that.data.dataCtgryId == "CITY_PARK"){
										//시설물대장조회 하이라이트 
										var format = new ol.format.WKT();
										var features = [format.readFeature(data[MAP.GEOMETRY_ATTR_NAME])];
										highlightObj.showRedFeatures(that.CLASS_NAME, features, true, {
											projection : ol.proj.get(MAP.PROJECTION)
										});
									}

									var fieldText = dataField.fieldAlias;
									if (dataField.unit) {
										fieldText += " ("
												+ dataField.unit
												+ ")";
									}

									rows.push({
										field : fieldText,
										fieldId : dataField.fieldId,
										value : value,
										mode : "view"
									});
								}
							}
						} else {
							// 강릉시 도로대장 관리서비스(sysId == "11") 사용하는 필드 "COMMON" && "ROAD_REGISTER"
							if(params.sysId == "11") {
								if(sysTy == "COMMON" || sysTy == "ROAD_REGISTER" || sysTy == "ROAD_REGISTER_VIEW") {
									if (indictTy != "HIDE" && indictTy != "WKT") {
										if (category.fieldCtgryId == dataField.fieldCtgryId) {
											var field = camelcaseUtils.underbarToCamelcase(dataField.fieldId);
											var value = data[field];
											
											if(that.data.dataCtgryId == "CITY_PARK"){
												//시설물대장조회 하이라이트 
												var format = new ol.format.WKT();
												var features = [format.readFeature(data[MAP.GEOMETRY_ATTR_NAME])];
												highlightObj.showRedFeatures(that.CLASS_NAME, features, true, {
													projection : ol.proj.get(MAP.PROJECTION)
												});
											}

											var fieldText = dataField.fieldAlias;
											if (dataField.unit) {
												fieldText += " ("
														+ dataField.unit
														+ ")";
											}

											rows.push({
												field : fieldText,
												fieldId : dataField.fieldId,
												value : value,
												mode : "view"
											});
										}
									}
								}
							} else {
								// 강릉시 도로대장 관리서비스가 아닌 경우 사용하는 필드 "COMMON" && ""
								if(sysTy == "COMMON" || sysTy == "") {
									if (indictTy != "HIDE" && indictTy != "WKT") {
										if (category.fieldCtgryId == dataField.fieldCtgryId) {
											var field = camelcaseUtils.underbarToCamelcase(dataField.fieldId);
											var value = data[field];
											
											if(that.data.dataCtgryId == "CITY_PARK"){
												//시설물대장조회 하이라이트 
												var format = new ol.format.WKT();
												var features = [format.readFeature(data[MAP.GEOMETRY_ATTR_NAME])];
												highlightObj.showRedFeatures(that.CLASS_NAME, features, true, {
													projection : ol.proj.get(MAP.PROJECTION)
												});
											}

											var fieldText = dataField.fieldAlias;
											if (dataField.unit) {
												fieldText += " ("
														+ dataField.unit
														+ ")";
											}

											rows.push({
												field : fieldText,
												fieldId : dataField.fieldId,
												value : value,
												mode : "view"
											});
										}
									}
								}
							}
							
						}
						
					}
					var gridData = {
						total : rows.length,
						rows : rows
					};
					$("." + id).propertygrid("loadData",
							gridData);
				}
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "공간 정보를 불러오는데 실패 했습니다.");
		});
	},

	loadNew : function() {
		var that = this;
		var params = { sysId : SYS_ID };
		
		// 카테고리 전체
		var categories = that.categories;
		// 데이터의 카테고리
		var dataFields = that.dataFields;

		for ( var id in categories) {
			// 카테고리 전체
			var category = categories[id];
			var rows = [];
			for (var i = 0, len = dataFields.length; i < len; i++) {
				var dataField = dataFields[i];
				var indictTy = dataField.indictTy;
				var sysTy = dataField.sysTy;

				if(PROJECT_CODE != "gn") {
					if (indictTy != "HIDE" && indictTy != "WKT") {
						if (category.fieldCtgryId == dataField.fieldCtgryId) {
							var fieldText = dataField.fieldAlias;
							if (dataField.unit) {
								fieldText += " (" + dataField.unit + ")";
							}

							var value = null;
							if (that.feature && that.feature.getProperties() && that.feature.getProperties()[dataField.fieldId] != null) {
								value = that.feature.getProperties()[dataField.fieldId];
							} else {
								if (dataField.fieldId == "FTR_IDN") {
									value = ftrIdnObj.getData(that.dataId);
								} 
								else if (dataField.fieldId == "LOD_YMD") {
									value = dateUtils.toDate(new Date(), "");
								}
								else if (dataField.fieldId == "BJD_CDE") {
									var format = new ol.format.WKT();
									var dongData = dongObj.getData();
									for(var j=0, jLen=dongData.length; j < jLen; j++) {
										var features = [format.readFeature(dongData[j][MAP.GEOMETRY_ATTR_NAME])];
										if(features && features.length > 0) {
											var coord = that.feature.getGeometry().getFirstCoordinate();
											var from = mapObj.getMap().getView().getProjection();
											var to = ol.proj.get(MAP.PROJECTION);
											var transCoord = spatialUtils.transformCooridate(coord, from , to);
											
											var feature = features[0];
											if(feature.getGeometry().containsXY(transCoord[0], transCoord[1])) {
												value = dongData[j].bjdCde;
												break;
											}
										}
									}
								}
								else {
									value = dataField.dfltValue;
								}
								if (value) {
									if (!that.properties) {
										that.properties = {};
									}
									that.properties[dataField.fieldId] = value;
								}
							}

							rows.push({
								column : dataField.fieldId,
								field : fieldText,
								value : value,
								editor : that.getEditor(dataField),
								mode : "edit",
								domnId : dataField.domnId
							});
						}
					}
				} else {
					// 강릉시 도로대장 관리서비스(sysId == "11") 사용하는 필드 "COMMON" && "ROAD_REGISTER"
					if(params.sysId == "11") {
						if(sysTy == "COMMON" || sysTy == "ROAD_REGISTER") {
							if (indictTy != "HIDE" && indictTy != "WKT") {
								if (category.fieldCtgryId == dataField.fieldCtgryId) {
									var fieldText = dataField.fieldAlias;
									if (dataField.unit) {
										fieldText += " (" + dataField.unit + ")";
									}

									var value = null;
									if (that.feature && that.feature.getProperties() && that.feature.getProperties()[dataField.fieldId] != null) {
										value = that.feature.getProperties()[dataField.fieldId];
									} else {
										if (dataField.fieldId == "FTR_IDN") {
											value = ftrIdnObj.getData(that.dataId);
										} 
										else if (dataField.fieldId == "LOD_YMD") {
											value = dateUtils.toDate(new Date(), "");
										}
										else if (dataField.fieldId == "BJD_CDE") {
											var format = new ol.format.WKT();
											var dongData = dongObj.getData();
											for(var j=0, jLen=dongData.length; j < jLen; j++) {
												var features = [format.readFeature(dongData[j][MAP.GEOMETRY_ATTR_NAME])];
												if(features && features.length > 0) {
													var coord = that.feature.getGeometry().getFirstCoordinate();
													var from = mapObj.getMap().getView().getProjection();
													var to = ol.proj.get(MAP.PROJECTION);
													var transCoord = spatialUtils.transformCooridate(coord, from , to);
													
													var feature = features[0];
													if(feature.getGeometry().containsXY(transCoord[0], transCoord[1])) {
														value = dongData[j].bjdCde;
														break;
													}
												}
											}
										}
										else {
											value = dataField.dfltValue;
										}
										if (value) {
											if (!that.properties) {
												that.properties = {};
											}
											that.properties[dataField.fieldId] = value;
										}
									}

									rows.push({
										column : dataField.fieldId,
										field : fieldText,
										value : value,
										editor : that.getEditor(dataField),
										mode : "edit",
										domnId : dataField.domnId
									});
								}
							}
						}
					} else {
						// 강릉시 도로대장 관리서비스가 아닌 경우 사용하는 필드 "COMMON" && ""
						if(sysTy == "COMMON" || sysTy == "") {
							if (indictTy != "HIDE" && indictTy != "WKT") {
								if (category.fieldCtgryId == dataField.fieldCtgryId) {
									var fieldText = dataField.fieldAlias;
									if (dataField.unit) {
										fieldText += " (" + dataField.unit + ")";
									}

									var value = null;
									if (that.feature && that.feature.getProperties() && that.feature.getProperties()[dataField.fieldId] != null) {
										value = that.feature.getProperties()[dataField.fieldId];
									} else {
										if (dataField.fieldId == "FTR_IDN") {
											value = ftrIdnObj.getData(that.dataId);
										} 
										else if (dataField.fieldId == "LOD_YMD") {
											value = dateUtils.toDate(new Date(), "");
										}
										else if (dataField.fieldId == "BJD_CDE") {
											var format = new ol.format.WKT();
											var dongData = dongObj.getData();
											for(var j=0, jLen=dongData.length; j < jLen; j++) {
												var features = [format.readFeature(dongData[j][MAP.GEOMETRY_ATTR_NAME])];
												if(features && features.length > 0) {
													var coord = that.feature.getGeometry().getFirstCoordinate();
													var from = mapObj.getMap().getView().getProjection();
													var to = ol.proj.get(MAP.PROJECTION);
													var transCoord = spatialUtils.transformCooridate(coord, from , to);
													
													var feature = features[0];
													if(feature.getGeometry().containsXY(transCoord[0], transCoord[1])) {
														value = dongData[j].bjdCde;
														break;
													}
												}
											}
										}
										else {
											value = dataField.dfltValue;
										}
										if (value) {
											if (!that.properties) {
												that.properties = {};
											}
											that.properties[dataField.fieldId] = value;
										}
									}

									rows.push({
										column : dataField.fieldId,
										field : fieldText,
										value : value,
										editor : that.getEditor(dataField),
										mode : "edit",
										domnId : dataField.domnId
									});
								}
							}
						}
					}
				}
				
			}
			var gridData = {
				total : rows.length,
				rows : rows
			};
			$("." + id).propertygrid("loadData", gridData);
			
			if(that.onLoad) {
				that.onLoad();
			}
		}
	},

	print : function() {

		var that = this;

//		var url = CONTEXT_PATH + "/clipreport/";
//
//		spatialSearchUtils.selectOne(that.title, that.dataId, that.getFid(), {
//			isOriginColumnValue : true
//		},
//				function(data) {
//					if (data) {
//						if (that.data.dataCtgryId == "UFL") {
//							url += that.dataId + "/" + data.objectid
//									+ "/uflPrntng.do?";
//						} else {
//							url += that.data.dataCtgryId + "/" + that.dataId
//									+ "/prntng.do?" + "ftrCde=" + data.ftrCde
//									+ "&" + "ftrIdn=" + data.ftrIdn;
//						}
//						popupUtils.open(url, that.data.dataCtgryId, {
//							width : 900,
//							height : 800,
//							left : 300,
//							top : 150
//						});
//					}
//				});
	},
	
	// 대장인쇄
	roadRegstrPrint : function() {
		var that = this;

		var featureInfo = that.getFeatureInfo();
		
		var layers = [];
		layers.push(featureInfo.dataId);
		
		var ids = [];
		ids.push(featureInfo.ftrIdn);
		var featureIds = [];
		featureIds.push(ids);
		
		var refId = [];
		refId.push(featureInfo.refId);
		var refIds = [];
		refIds.push(refId);
		
		var request = {};
		request.regIdn = that.regIdn;
		request.layerIds = layers;
		request.featureIds = featureIds;
		request.isOriginValue = true;
		request.ftrIdn = refIds;	// 도로구역 조회용
		
		var url = CONTEXT_PATH + "/roadRegstr/searchRoadRegister.do";
		$.post(url, request).done(function(response) {
			if(response && response.rows) {
				var data = response.rows[0];
				
				var printUrl = CONTEXT_PATH + "/clipreport/"
				+ that.data.roadRegAt + "/" + that.data.dataId
				+ "/roadRegstrPrint.do?" + "ftrCde=" + data.ftrCde
				+ "&" + "ftrIdn=" + data.ftrIdn;
				
				popupUtils.open(printUrl, that.data.roadRegAt, {
					width : 900,
					height : 800,
					left : 300,
					top : 150
				});
			}			
		}).fail(function() {
			$.messager.alert(that.TITLE, "도로대장 정보를 불러오는데 실패 했습니다.");
		});		
	},

	// 통합출력
	unitedRoadRegstrPrint : function() {
		var that = this;

	//	for (var i = 0, len = that.kwsDataFields.length; i < len; i++){
	//		if(that.kwsDataFields[i].fieldId =="SEC_IDN"){
	//			var secIdn = that.kwsDataFields[i].fieldAlias;
	//		}
	//		if(that.kwsDataFields[i].fieldId =="ROT_NAM"){
	//			var rotNam = that.kwsDataFields[i].fieldAlias;
	//		}
	//		if(that.kwsDataFields[i].fieldId =="ROT_IDN"){
	//			var rotIdn = that.kwsDataFields[i].fieldAlias;
	//		}
	//	}
		
		//function getrotNam(){
		//	return document.getElementById('data.rotNam').value;
		//}
		//function getrotIdn(){
		//	return document.getElementById('data.rotIdn').value;
		//}
		//function getsecIdn(){
		//	return document.getElementById('data.secIdn').value;
		//}

		var url = 
		CONTEXT_PATH + "/clipreport/";

			if (that) {

				url += that.data.roadRegAt + "/unitedRoadRegstrPrint.do?rotNam=" + that.rotnam + "&rotIdn=" + that.rotIdn + "&secIdn=" + that.secIdn;
				popupUtils.open(url, "도로대장", {width : 900, height : 800, left : 300, top : 150});
			}
	},
	
	// 관리이력
	manageHistory : function() {
		var that = this;
		
		var dataCtgryId = that.data.dataCtgryId;
		var featureInfo = that.getFeatureInfo();
		var dataId = featureInfo.dataId;
		var ftrIdn = featureInfo.ftrIdn;

		var layers = [];
		layers.push(dataId);
		
		var ids = [];
		ids.push(ftrIdn);
		var featureIds = [];
		featureIds.push(ids);

		var refId = [];
		refId.push(featureInfo.refId);
		var refIds = [];
		refIds.push(refId);
		
		var request = {};
		request.regIdn = that.regIdn;
		request.layerIds = layers;
		request.featureIds = featureIds;
		request.isOriginValue = true;
		request.ftrIdn = refIds;	// 도로구역 조회용
		
		var url = CONTEXT_PATH + "/roadRegstr/searchRoadRegister.do";
		$.post(url, request).done(function(response) {
			if(response && response.rows) {
				var data = response.rows[0];

				var fid = data.objectid;
				var ftrCde = data.ftrCde;
				windowObj.manageHistObj.open(dataId, fid, ftrIdn, dataCtgryId, that, ftrCde);
			}			
		}).fail(function() {
			$.messager.alert(that.TITLE, "정보를 불러오는데 실패 했습니다.");
		});	
	},

	// 사진관리
	managePhoto : function() {
		var that = this;

		var featureInfo = that.getFeatureInfo();
		
		var layers = [];
		layers.push(featureInfo.dataId);
		
		var ids = [];
		ids.push(featureInfo.ftrIdn);
		var featureIds = [];
		featureIds.push(ids);
		
		var refId = [];
		refId.push(featureInfo.refId);
		var refIds = [];
		refIds.push(refId);
		
		var request = {};
		request.regIdn = that.regIdn;
		request.layerIds = layers;
		request.featureIds = featureIds;
		request.isOriginValue = true;
		request.ftrIdn = refIds;	// 도로구역 조회용
		
		var url = CONTEXT_PATH + "/roadRegstr/searchRoadRegister.do";
		$.post(url, request).done(function(response) {
			if(response && response.rows) {
				var data = response.rows[0];

				var ftrCde = data.ftrCde;
				var ftrIdn = data.ftrIdn;
				windowObj.photoManageObj.open(ftrCde, ftrIdn);
			}			
		}).fail(function() {
			$.messager.alert(that.TITLE, "사진정보를 불러오는데 실패 했습니다.");
		});		
	},
	
	// 구간도면
	sectionPlan : function() {
		var that = this;
		
		var featureInfo = that.getFeatureInfo();
		windowObj.sectionPlanObj.open(featureInfo.ftrIdn);
	},
	
	// 단위도면
	localPlan : function() {
		var that = this;
		
		var featureInfo = that.getFeatureInfo();
		windowObj.localPlanObj.open(featureInfo.ftrIdn);
	},
	
	loadEdit : function() {
		var that = this;
		var params = { sysId : SYS_ID };
		
		var featureInfo = that.getFeatureInfo();
		
		var layers = [];
		layers.push(featureInfo.dataId);
		
		var ids = [];
		ids.push(featureInfo.ftrIdn);
		var featureIds = [];
		featureIds.push(ids);
		
		var refId = [];
		refId.push(featureInfo.refId);
		var refIds = [];
		refIds.push(refId);
		
		var request = {};
		request.regIdn = that.regIdn;
		request.layerIds = layers;
		request.featureIds = featureIds;
		request.isOriginValue = true;
		request.ftrIdn = refIds;	// 도로구역 조회용
		
		var url = CONTEXT_PATH + "/roadRegstr/searchRoadRegister.do";
		$.post(url, request).done(function(response) {
			if(response && response.rows) {
				var data = response.rows[0];
		
				// 카테고리 전체
				var categories = that.categories;
				// 데이터의 카테고리
				var dataFields = that.dataFields;

				for(var id in categories) {
					// 카테고리 전체
					var category = categories[id];
					var rows = [];
					for(var i = 0, len = dataFields.length; i < len; i++) {
						var dataField = dataFields[i];
						var indictTy = dataField.indictTy;
						var sysTy = dataField.sysTy;
						
						if(PROJECT_CODE != "gn") {
							if(indictTy != "HIDE" && indictTy != "WKT") {
								if(category.fieldCtgryId == dataField.fieldCtgryId) {
									var field = camelcaseUtils.underbarToCamelcase(dataField.fieldId);
									var value = null;
									
									if(that.feature && that.feature.getProperties() && that.feature.getProperties()[dataField.fieldId] != null) {
										value = that.feature.getProperties()[dataField.fieldId];
									} else {
										value = data[field];
									}

									var fieldText = dataField.fieldAlias;
									if(dataField.unit) {
										fieldText += " (" + dataField.unit + ")";
									}

									var valid = that.validateValue(dataField, value);
									if(!valid.isValid) {
										$.messager.alert(that.title, valid.message);
									}

									rows.push({
										column : dataField.fieldId,
										field : fieldText,
										value : value,
										editor : that.getEditor(dataField),
										mode : "edit",
										domnId : dataField.domnId
									});
								}
							}
						} else {
							// 강릉시 도로대장 관리서비스(sysId == "11") 사용하는 필드 "COMMON" && "ROAD_REGISTER"
							if(params.sysId == "11") {
								if(sysTy == "COMMON" || sysTy == "ROAD_REGISTER"  || sysTy == "ROAD_REGISTER_VIEW") {
									if(indictTy != "HIDE" && indictTy != "WKT") {
										if(category.fieldCtgryId == dataField.fieldCtgryId) {
											var field = camelcaseUtils.underbarToCamelcase(dataField.fieldId);
											var value = null;
											
											if(that.feature && that.feature.getProperties() && that.feature.getProperties()[dataField.fieldId] != null) {
												value = that.feature.getProperties()[dataField.fieldId];
											} else {
												value = data[field];
											}

											var fieldText = dataField.fieldAlias;
											if(dataField.unit) {
												fieldText += " (" + dataField.unit + ")";
											}

											var valid = that.validateValue(dataField, value);
											if(!valid.isValid) {
												$.messager.alert(that.title, valid.message);
											}

											rows.push({
												column : dataField.fieldId,
												field : fieldText,
												value : value,
												editor : that.getEditor(dataField),
												mode : "edit",
												domnId : dataField.domnId
											});
										}
									}
								}
							} 
							// 강릉시 도로대장 관리서비스가 아닌 경우 사용하는 필드 "COMMON" && ""
							else {
								if(sysTy == "COMMON" || sysTy == "") {
									if(indictTy != "HIDE" && indictTy != "WKT") {
										if(category.fieldCtgryId == dataField.fieldCtgryId) {
											var field = camelcaseUtils.underbarToCamelcase(dataField.fieldId);
											var value = null;
											
											if(that.feature && that.feature.getProperties() && that.feature.getProperties()[dataField.fieldId] != null) {
												value = that.feature.getProperties()[dataField.fieldId];
											} else {
												value = data[field];
											}

											var fieldText = dataField.fieldAlias;
											if(dataField.unit) {
												fieldText += " (" + dataField.unit + ")";
											}

											var valid = that.validateValue(dataField, value);
											if(!valid.isValid) {
												$.messager.alert(that.title, valid.message);
											}

											rows.push({
												column : dataField.fieldId,
												field : fieldText,
												value : value,
												editor : that.getEditor(dataField),
												mode : "edit",
												domnId : dataField.domnId
											});
										}
									}
								}
							}
						}
						
					}
					var gridData = {
						total : rows.length,
						rows : rows
					};
					$("." + id).propertygrid("loadData", gridData);
					
					if(that.onLoad) {
						that.onLoad();
					}
				}
			}			
		}).fail(function() {
			$.messager.alert(that.TITLE, "정보를 불러오는데 실패 했습니다.");
		});				
		
		// 민원관리
		if(that.dataId == "RDT_RSER_MA") {
			var ftrIdn = that.fids;
			var selectUrl = CONTEXT_PATH + "/civilAppeal/road/" + ftrIdn + "/selectOneRoadCivilAppeal.do";
			
			$.get(selectUrl).done(function(result) {
				roadCivilAppealObj.modify.originalData = result.data;
			}).fail(function() {
				alert("도로민원을 불러오는데 실패하였습니다.");
			});
		}
		else if(that.dataId == "WTT_WSER_MA") {
			var ftrIdn = that.fids;
			var selectUrl = CONTEXT_PATH + "/civilAppeal/wrpp/" + ftrIdn + "/selectOneWrppCivilAppeal.do";
			
			$.get(selectUrl).done(function(result) {
				wrppCivilAppealObj.modify.originalData = result.data;
			}).fail(function() {
				alert("상수민원을 불러오는데 실패하였습니다.");
			});
		}
		else if(that.dataId == "SWT_SSER_MA") {
			var ftrIdn = that.fids;
			var selectUrl = CONTEXT_PATH + "/civilAppeal/swge/" + ftrIdn + "/selectOneSwgeCivilAppeal.do";
			
			$.get(selectUrl).done(function(result) {
				swgeCivilAppealObj.modify.originalData = result.data;
			}).fail(function() {
				alert("하수민원을 불러오는데 실패하였습니다.");
			});
		}
		else if(that.dataId == "SWT_SPMT_MA") {
			var ftrIdn = that.fids;
			var selectUrl = CONTEXT_PATH + "/civilAppeal/swge/" + ftrIdn + "/selectOneDrngEqpCnfmPrmisn.do";
			
			$.get(selectUrl).done(function(result) {
				drngEqpCnfmPrmisnObj.modify.originalData = result.data;
			}).fail(function() {
				alert("배수설비인허가대장을 불러오는데 실패하였습니다.");
			});
		}
	},

	getEditor : function(dataField) {
		var that = this;
		
		var editor = null;

		var required = dataField.nullAt == "N" ? true : false;
		var fieldLt = dataField.fieldLt;
		var precision = dataField.dcmlLt ? dataField.dcmlLt : 0;

		// 정수 자리수
		var int = fieldLt;
		if (precision) {
			int = int - precision;
		}
		var max = 1;
		for (var i = 0; i < int; i++) {
			max *= 10 - 1;
		}

		var indictType = dataField.indictTy;
		if (indictType == "DOMAIN") {
			var domnId = dataField.domnId;
			if (domnId) {
				var data = [];
				var domnCodes = domnCodeObj.getData(domnId);
				if(dataField.fieldId == "FTR_CDE") {
					var ftrCdes = dataFtrCdeObj.getData(that.dataId);
					for(var i=0, len=ftrCdes.length; i < len; i++) {
						for(var j=0, jLen=domnCodes.length; j < jLen; j++) {
							if(ftrCdes[i].codeId == domnCodes[j].codeId) {
								data.push(domnCodes[j]);
								break;
							}
						}
					}
				}
				else {
					data = domnCodes;
				}

				editor = {
					type : "combobox",
					options : {
						required : required,
						data : data,
						valueField : "codeId",
						textField : "codeNm"
					}
				};
			}
		} else {
			var fieldType = dataField.fieldTy;
			switch (fieldType) {
			case "STRING":
				editor = {
					type : "textbox",
					options : {
						required : required,
						validType : "maxLength[" + fieldLt + "]"
					}
				};
				break;
			case "NUMBER":
				if(dataField.dataId == "CTY_ATHL_PS" && dataField.fieldId == "FTF_IDN" ||
					dataField.dataId == "CTY_PLAY_PS" && dataField.fieldId == "FTF_IDN" ||
					dataField.dataId == "CTY_REST_PS" && dataField.fieldId == "FTF_IDN" ||
					dataField.dataId == "CTY_REST_LS" && dataField.fieldId == "FTF_IDN" ||
					dataField.dataId == "CTY_REST_AS" && dataField.fieldId == "FTF_IDN" ){
					
					editor = {
						type : "textbox",
						options : {
							required : true,
							readonly : true	
						}
					};
					break;
				} 
				else if(dataField.dataId == "CTY_ATHL_PS" && dataField.fieldId == "PPK_IDN" ||
						dataField.dataId == "CTY_PLAY_PS" && dataField.fieldId == "PPK_IDN" ||
						dataField.dataId == "CTY_REST_PS" && dataField.fieldId == "PPK_IDN" ||
						dataField.dataId == "CTY_REST_LS" && dataField.fieldId == "PPK_IDN" ||
						dataField.dataId == "CTY_REST_AS" && dataField.fieldId == "PPK_IDN"){
						
					editor = {
						type : "textbox",
						options : {
							required : true,
							readonly : true
						}
					};
					break;
				}	
				else{
					editor = {
						type : "numberspinner",
						options : {
							required:false,
							min : 0,
							max : max,
							precision : precision
						}
					};
					break;
				}
			case "DATE":
				editor = {
					type : "datebox",
					options : {
						required : required
					}
				};
				break;
			case "DATE_FROM_STRING":
				editor = {
					type : "datebox",
					options : {
						required : required
					}
				};
				break;
			};
		}
		return editor;
	},

	getFormatter : function(value, row) {
		if (row.editor && row.editor.type && row.editor.type == "combobox") {
			var domnCodes = row.editor.options.data;
			for (var i = 0, len = domnCodes.length; i < len; i++) {
				if (domnCodes[i].codeId == value) {
					return domnCodes[i].codeNm;
				} else if (domnCodes[i].ftrIdn == value){
					return domnCodes[i].prkNam;
				}
			}
		} else {
			return value;
		}
	},

	getFeatureInfo : function() {
		var that = this;
		return that.featureInfos[that.index];
	},

	getFid : function() {
		var that = this;
		return that.fids[that.index];
	},
	
	// 카테고리 버튼 생성
	createCategoryBtn : function(allCategories) {
		var that = this;
		that.categories = {};
		var str = "";

		// 카테고리 추출
		for ( var i in allCategories) {
			for ( var j in that.data.kwsDataFields) {
				if (allCategories[i].fieldCtgryId == that.data.kwsDataFields[j].fieldCtgryId) {
					if (!that.categories[allCategories[i].fieldCtgryId]) {
						that.categories[allCategories[i].fieldCtgryId] = allCategories[i];
					}
				}
			}
		}

		// 카테고리 버튼 생성
		for ( var id in that.categories) {
			str += "<li>";
			str += '<a href="#" class="a_category" data-category-id="' + id
					+ '" ><img src="' + CONTEXT_PATH
					+ '/images/kworks/map/doc/tap/' + id + '_off.png" /></a>';
			str += "</li>";
		}
		// 카테고리 버튼 추가
		$(".ul_category", that.selector).html(str);
	},

	// 데이터 그리드 생성
	createCategoryDatagrid : function() {
		var that = this;
		var str = "";

		// 테이블 생성
		for ( var id in that.categories) {
			var className = "div_content_" + id;
			str += "<div class='div_content " + className + "'>";
			str += "<table class='tbl_register_detail " + id + "'></table>";
			str += "</div>";
		}
		// 태그 삽입
		$(".div_content_container", that.selector).html(str);

		// 데이터 그리드 초기 컬럼 정의
		$(".tbl_register_detail", that.selector).propertygrid({
			columns : [ [ {
				field : "field",
				title : "항목",
				width : 100
			}, {
				field : "value",
				title : "내용",
				width : 150,
				formatter : function(value, row) {
					if (row.mode == "edit") {
						return that.getFormatter(value, row);
					} else {
						return value;
					}
				}
			} ] ],
			fitColumns : true,
			singleSelect : true
		});
	},

	getProperties : function() {
		var that = this;
		var props = $.extend({}, that.properties);
		for (var id in that.categories) {
			var changeRows = $("." + id).propertygrid("getChanges");
			for (var i = 0, len = changeRows.length; i < len; i++) {
				var changeRow = changeRows[i];
				props[changeRow.column] = changeRow.value;
			}
		}
		return props;
	},

	validate : function() {
		var that = this;
		var props = {};

		var result = {
			isValid : true,
			message : ""
		};

		for ( var id in that.categories) {
			var rows = $("." + id).propertygrid("getRows");
			for (var i = 0, len = rows.length; i < len; i++) {
				var row = rows[i];
				props[row.column] = row.value;
			}
		}
		var dataFields = that.data.kwsDataFields;
		for (var i = 0, len = dataFields.length; i < len; i++) {
			var dataField = dataFields[i];
			var value = props[dataField.fieldId];
			var valid = that.validateValue(dataField, value);
			if (!valid.isValid) {
				result.isValid = false;
				result.message += valid.message + "<br />";
			}
		}
		return result;
	},

	validateValue : function(dataField, value) {
		var fieldAlias = dataField.fieldAlias;

		var result = {
			isValid : true,
			message : ""
		};

		// 표시방식이 HIDE, WKT 일 경우 성공
		var indictTy = dataField.indictTy;
		if (indictTy == "HIDE" || indictTy == "WKT") {
			return result;
		}

		var checkValue = null;
		checkValue = $.trim(value);

		// 필수 값 체크
		var required = dataField.nullAt == "N" ? true : false;
		if (required && !checkValue) {
			result.message += "[" + dataField.fieldAlias + "] 필수 항목 입니다.";
			result.isValid = false;
		}

		var fieldLt = dataField.fieldLt;
		var precision = dataField.dcmlLt ? dataField.dcmlLt : 0;
		var domnId = dataField.domnId;

		// 정수 자리수
		var int = fieldLt;
		if (precision) {
			int = int - precision;
		}
		var max = 1;
		for (var i = 0; i < int; i++) {
			max *= 10;
		}

		var indictType = dataField.indictTy;
		if (indictType == "DOMAIN") {
			if (domnId) {
				if (checkValue) {
					var domnCodes = domnCodeObj.getData(domnId);
					if (domnCodes) {
						var isCheck = false;
						for (var i = 0, len = domnCodes.length; i < len; i++) {
							var domnCode = domnCodes[i];
							if (domnCode.codeId == checkValue) {
								isCheck = true;
								break;
							}
						}
						if (!isCheck) {
							result.message += "[" + fieldAlias + "] "
									+ checkValue + "는 도메인 코드에 없는 값입니다.";
							result.isValid = false;
						}
					} else {
						// domnCodeObj 에서 메세지를 표시 하므로 메세지 표시 안함
					}
				}
			} else {
				result.message += "[" + fieldAlias + "] 도메인 아이디가 없습니다.";
				result.isValid = false;
			}
		} else {
			var fieldType = dataField.fieldTy;
			switch (fieldType) {
			case "STRING":
				if (checkValue.length > fieldLt) {
					result.message += "[" + fieldAlias + "] " + fieldLt
							+ " 이하 길이의 값을 입력하여 주십시오. ";
					result.isValid = false;
				}
				break;
			case "NUMBER":
				if (checkValue > max) {
					result.message += "[" + fieldAlias + "] " + max
							+ " 이하의 값을 입력하여 주십시오. ";
					result.isValid = false;
				}
				break;
			case "DATE":
				// 날짜 형 validate 필요
				break;
			case "DATE_FROM_STRING":
				// 날짜 형 문자열 validate 필요
				break;
			}
			;
		}

		return result;
	},

	update : function() {
		var that = this;

		var props = that.getProperties();
		if (!props) {
			$.messager.alert(that.title, "편집한 내용이 없습니다.");
			return false;
		}
		
		var valid = that.validate();
		if (!valid.isValid) {
			$.messager.alert(that.title, valid.message);
			return false;
		}

		var feature = new ol.Feature();
		feature.setId(that.getFtrIdn());
		feature.setProperties(props);
		
		// arcgis
		if(PROJECT_CODE == "gc") {
			feature.ftrIdn =that.ftrIdn;
		}
		
		var url = CONTEXT_PATH + "/proxy/transaction.do?sysId="+SYS_ID+"&editType=SPATIAL";
		var params = {
			featureType : that.dataId,
			srsName : MAP.PROJECTION,
			geometryName : MAP.GEOMETRY_ATTR_NAME.toUpperCase(),
			serverType : MAP.SERVER_TYPE
		};

		kworks.service.WFS.transaction(url, params, null, [ feature ], null,
				function(response) {
					if (response.updated == 1) {
						$.messager.alert(that.title, "편집 되었습니다.");
						that.setMode("view");
					} else if(response.updated == '' || response.updated == 0 ){
						$.messager.alert(that.title, "편집된 내용이 없습니다.");
						that.setMode("view");
					} else {
						$.messager.alert(that.title, "편집에 실패하였습니다.");
					}
				});
	},

	/**
	 * 닫기
	 */
	close : function() {
		var that = this;

		that.regIdn = null;
		that.data = null;
		that.dataFields = null;
		that.featureInfos = [];
		that.index = 0;
		that.categories = null;
		that.feature = null;
		that.properties = {};
		that.onSave = null;
		that.onLoad = null;
		that.fids = null;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}

};
