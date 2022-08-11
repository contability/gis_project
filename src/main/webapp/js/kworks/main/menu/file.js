/**
 * 파일 메뉴 객체
 * @type {Object}
 */
menuObj.fileObj = {
		
	/**
	 * 페이지 명
	 * @type {String}
	 */
	PAGE : "file",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_menu_panel_file",
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "파일",
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.import.init();
		that["export"].init();
		
		that.bindEvents();
		that.open();
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 탭 변경
		$(".mapTab .mTab", that.selector).click(function() {
			var element = $(this);
			var contentId = element.attr("data-for-id");
			
			// 선택 해제
			$(".mapTab .mTab", that.selector).find("img").each(function() {
				var imgElement = $(this);
				imgElement.attr("src", imgElement.attr("src").replace("_on", "_off"));
			});
			// 선택
			element.find("img").each(function() {
				var imgElement = $(this);
				imgElement.attr("src", imgElement.attr("src").replace("_off", "_on"));
			});
			
			$(".tab_content", that.selector).hide();
			$("#"+contentId).show();
		});
	},
	
	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		$(that.selector).show();
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		that["export"].clear();
		$(that.selector).hide();
	}
	
};

/**
 * 파일 가져오기
 */
menuObj.fileObj.import = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_menu_panel_file_import",
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "파일 가져오기",
	
	index : 0,
		
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.initUi();
		that.loadCoordinates();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 가져오기 타입
		$(".sel_import_type", that.selector).combobox({
			onSelect : function(data) {
				// 파일 초기화
				$(".div_import_file", that.selector).html('<input id="file_import_file" name="file_import_file" type="file" />');
				
				if(data.value == "EXCEL") {
					$(".li_import_excel", that.selector).show();
				}
				else {
					$(".li_import_excel", that.selector).hide();
				}
			}
		});
		
		// 좌표계 콤보박스
		$(".sel_coorinates", that.selector).combobox({
			valueField : "cntmId",
			textField : "cntmNm"
		});
		
		// 시작 라인 수 (엑셀)
		$(".txt_start_line", that.selector).numberspinner({
			min : 1,
			max : 65535,
			increment : 1,
			value : 2
		});
		
		// X 필드 인덱스 (엑셀)
		$(".txt_x_field_index", that.selector).numberspinner({
			min : 1,
			max : 65535,
			increment : 1,
			value : 2
		});
		
		// Y 필드 (엑셀)
		$(".txt_y_field_index", that.selector).numberspinner({
			min : 1,
			max : 65535,
			increment : 1,
			value : 3
		});
		
		// 실행
		$(".a_execute", that.selector).click(function() {
			that.execute();
		});
		
	},
	
	/**
	 * 좌표계 목록 로딩
	 */
	loadCoordinates : function() {
		var that = this;
		var data = coordinateObj.getData();
		$(".sel_coorinates", that.selector).combobox("loadData", data);
		$(".sel_coorinates", that.selector).combobox("setValue", MAP.PROJECTION);
	},
	
	/**
	 * 실행
	 */
	execute : function() {
		var that = this;
		var type = $(".sel_import_type", that.selector).combobox("getValue");
		
		var extensions = {
			SHAPE : {
				"shp" : true
			},
			EXCEL : {
				"xls" : true,
				"xlsx" : true
			},
			GML : {
				gml : true,
			},
			GEOJSON : {
				geojson : true
			}
		};
		
		var files = document.getElementById('file_import_file').files;
		if(files) {
			if(files.length > 0) {
				var file = files[0];
				var extension = file.name.substring(file.name.lastIndexOf(".")+1).toLowerCase();
				if(extensions[type][extension.toLowerCase()]) {
					if(type == "SHAPE") {
						that.executeShape(file);
					} 
					else if(type == "EXCEL") {
						that.executeExcel(file);
					} 
					else if(type == "GML") {
						that.executeGml(file);
					} 
					else if(type == "GEOJSON") {
						that.executeGeoJson(file);
					}
					else {
						$.messager.alert(that.TITLE, "정의되지 않은 가져오기 타입입니다.");
					}
				}
				else {
					var types = [];
					for(var key in extensions[type]) {
						types.push(key);
					}
					$.messager.alert(that.TITLE, "확장자가 " + types.join(", ") + " 인 파일만 가져올 수 있습니다.");
				}
			}
			else {
				$.messager.alert(that.TITLE, "파일이 없습니다.");
			}
		}
		else {
			$.messager.alert(that.TITLE, "IE9 이하 브라우저에는 지원되지 않습니다.");
		}
	},
	
	/**
	 * Shape 가져오기 실행 
	 * @params {File} file
	 */
	executeShape : function(file) {
		var that = this;
		var parser = new ol.format.GeoJSON();
		var format = new kworks.format.Shape();
		format.readFeature(file, function(data) {
			var features = parser.readFeatures(data.geojson);
			that.addLayer(file, features);
		});
	},
	
	/**
	 * GML 가져오기 실행 
	 * @params {File} file
	 */
	executeGml : function(file) {
		var that = this;
		var reader = goog.fs.FileReader.readAsText(file, '');
		reader.addCallback(goog.partial(function(f, result) {
			var parser = new kworks.format.GML3();
			var features = [];
			features = parser.readFeatures(result);
			that.addLayer(file, features);
		}, file), this);
	},
	
	/**
	 * GeoJson 가져오기 실행 
	 * @params {File} file
	 */
	executeGeoJson : function(file) {
		var that = this;
		var reader = goog.fs.FileReader.readAsText(file, '');
		reader.addCallback(goog.partial(function(f, result) {
			var parser = new ol.format.GeoJSON();
			var features = parser.readFeatures(result);
			that.addLayer(file, features);
		}, file), this);
	},
	
	addLayer : function(file, features) {
		var that = this;
		var fileName = file.name.substring(0, file.name.indexOf("."));
		if(fileName.length > 13){
			fileName=file.name.substring(0, 13)+"...";
		}
		var projCode = null;
		if($(".sel_coorinates", that.selector).length > 0) {
			projCode = $(".sel_coorinates", that.selector).combobox("getValue");
		}
		else {
			projCode = MAP.PROJECTION;
		}
		
		var from = ol.proj.get(projCode);
		var to = mapObj.getMap().getView().getProjection();
		if(from != to) {
			for(var i=0, len=features.length; i < len; i++) {
				var feature = features[i];
				feature.setGeometry(spatialUtils.transform(feature.getGeometry(), from, to));
			}
		}
		
		var id = "import_" + (that.index++);
		var data = {
			id : id,
			name : fileName,
			features : features
		};
		menuObj.layerObj.userObj.addImportLayerNode(data);
		
		$.messager.confirm(that.TITLE, "가져오기 레이어는 왼쪽 '레이어' 메뉴 > '사용자' 탭에서 확인할 수 있습니다.<br /> 이동하시겠습니까?", function(isTrue) {
			if(isTrue) {
				$("#a_menu_layer").trigger("click");
				menuObj.layerObj.selectTab("사용자");
			}
		});
	},
	
	/**
	 * Excel 가져오기 실행 
	 * @params {File} file
	 */
	executeExcel : function() {
		var that = this;
		var startLine = $(".txt_start_line", that.selector).numberspinner("getValue");
		var xIndex = $(".txt_x_field_index", that.selector).numberspinner("getValue");
		var yIndex = $(".txt_y_field_index", that.selector).numberspinner("getValue");
		
		if(startLine == null || startLine == "") {
			$.messager.alert("가져오기", "시작라인수는 필수입니다.");
			return;
		}
		if(xIndex == null || xIndex == "") {
			$.messager.alert("가져오기", "X필드는 필수입니다.");
			return;
		}
		if(yIndex == null || yIndex == "") {
			$.messager.alert("가져오기", "Y필드는 필수입니다.");
			return;
		}
		
		var formData = new FormData();
		formData.append("importFile", $("#file_import_file")[0].files[0]);
		formData.append("startLine", startLine);
		formData.append("xIndex", xIndex);
		formData.append("yIndex", yIndex);
		
		$.ajax({
			url  : CONTEXT_PATH + "/input/excel.do",
			data : formData,
			processData: false,
    	    contentType: false,
    	    type: 'POST',
    	    success: function(res){
    	    	if(res) {
    	    		if(res.isSuccess) {
    	    			if(res.result && res.result && res.result.successCount > 0) {
    	    	    		var format = new ol.format.WKT();
    	    	    		var features = [];
    	    	    		for(var i in res.result.features) {
    	    	    			features.push(new ol.Feature(format.readGeometryFromText(res.result.features[i])));
    	    	    		}
    	    	    		// 레코드의 좌표 중 숫자형이 아닌 경우 오류 메세지 - 나머지는 정상 동작
    	    	    		if(res.result.failCount > 0) {
    	    	    			$.messager.alert("가져오기", "총 " + res.result.count + "건의 자료 중 " + res.result.failCount + "건의 자료를 가져오는데 실패했습니다. 엑셀 데이터를 확인 바랍니다.");
    	    	    		}
    	    	    		that.addLayer($("#file_import_file")[0].files[0], features);
    	    	    	}
    	    	    	else {
    	    	    		// 표시할 데이터가 없을 경우 메세지
    	    	    		$.messager.alert("가져오기", "엑셀파일에서 표시할 데이터가 없습니다.");
    	    	    	}
    	    		}
    	    		// 파일 가져오기 서비스에서 Exception 발생
    	    		else {
    	    			$.messager.alert("가져오기", "엑셀파일 가져오기에 실패했습니다.");
    	    		}
    	    	}
    	    	else {
    	    		$.messager.alert("가져오기", "엑셀파일 가져오기에 실패했습니다.");
    	    	}
    	    }
		});
	}
		
};

/**
 * 파일 내보내기
 * @type {Object}
 */
menuObj.fileObj["export"] = {
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_menu_panel_file_export",
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "파일 내보내기",
		
	/**
	 * 데이터 아이디 목록
	 * @type {Array.<String>}
	 */
	dataIds : null,
	
	/**
	 * 사용자지정영역
	 */
	feature : null,
		
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.initUi();
		that.loadCoordinates();
		that.loadDong();
		that.bindEvents();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 내보내기 타입
		$(".sel_export_type", that.selector).combobox({
			onSelect : function(data) {
				$("h4", that.selector).show();
				$(".li_export", that.selector).hide();
				
				var exportAt = true;
                var count = 0;
				
				//2021.09.24, 이승재, dataAuthorObj 수정에 따라 불필요
				//dataAuthorObj.load();
				var authorDatas = dataAuthorObj.data;
				 
				for(var i = 0; i < Object.keys(authorDatas).length; i++){
					var dataObj = authorDatas[Object.keys(authorDatas)[i]];
					if(dataObj.exportAt == 'N'){
                        count++;
					}

					if(Object.keys(authorDatas).length === count){
						exportAt = false;
					}
				}
				
				//내보내기 권한이 하나도 없으면 DXF, EXCEL, SHAPE 내보내기 제거
				if(!exportAt){
					$("#_easyui_combobox_i7_1").remove();
					$("#_easyui_combobox_i7_2").remove();
					$("#_easyui_combobox_i7_3").remove();
				}
				
				if(data.value == "IMAGE") {
					$("h4", that.selector).hide();
				}
				else if(data.value == "DXF") {
					$(".li_export_dxf", that.selector).show();
				}
				else if(data.value == "EXCEL") {
					$(".li_export_excel", that.selector).show();
				}
				else if(data.value == "SHAPE") {
					$(".li_export_shape", that.selector).show();
				}
				else {
					$.messager.alert(that.TITLE, "정의 되지 않은 내보내기 타입입니다.");
				}
			}
		});
		
		// 동 목록
		$(".sel_dong", that.selector).combobox({
			valueField : COMMON.PK,
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD)
		});
		
		// 좌표계 콤보박스
		$(".sel_coorinates", that.selector).combobox({
			valueField : "cntmId",
			textField : "cntmNm"
		});
		
	},
	
	/**
	 * 좌표계 목록 불러오기
	 */
	loadCoordinates : function() {
		var that = this;
		var data = coordinateObj.getData();
		$(".sel_coorinates", that.selector).combobox("loadData", data);
		$(".sel_coorinates", that.selector).combobox("setValue", MAP.PROJECTION);
	},
	
	/**
	 * 동 로딩
	 */
	loadDong : function() {
		var that = this;
		dongObj.getPromise().done(function() {
			var data = dongObj.getData();
			$(".sel_dong", that.selector).combobox("loadData", data);
			if(data && data.length > 0) {
				$(".sel_dong", that.selector).combobox("setValue", data[0].objectid);
			}
		});
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 레이어 선택
		$(".a_export_layers", that.selector).click(function() {
			var options = {
				mode : "OUTPUT",
				checkDataIds : that.dataIds,
				onSelect : function(layers) {
					var tagStr = "";
					that.dataIds = [];
					for(var i in layers) {
						that.dataIds.push(layers[i].dataId);
						tagStr += '<li>';
						tagStr += layers[i].layerTitle;
						tagStr += '</li>';
					}
					$(".ul_export_layers", that.selector).html(tagStr);
				}	
			};
			
			windowObj.layerObj.open(options);
			return false;
		});
		
		// 범위 변경
		$("input[name=rad_menu_panel_file_export_scope]", that.selector).click(function() {
			var value = $(this).val();
			if(value == "POLYGON") {
				selectObj.active(that.CLASS_NAME, "Polygon", "drawend", function(evt) {
					that.feature = evt.feature;
					highlightObj.showOrangeFeatures(that.CLASS_NAME, [that.feature]);
				});
			}
			else {
				that.clear();
			}
		});
		
		// 실행
		$(".a_execute", that.selector).click(function() {
			that.execute();
			return false;
		});
		
	},
	
	clear : function() {
		var that = this;
		that.feature = null;
		selectObj.clear(that.CLASS_NAME);
		highlightObj.removeOrangeFeatures(that.CLASS_NAME);
		mainObj.defaultActive();
	},
	
	/**
	 * 실행
	 */
	execute : function() {
		var that = this;
		
		var type = $(".sel_export_type", that.selector).combobox("getValue");
		if(type != "IMAGE") {
			if($(".ul_export_layers li", that.selector).length <= 0) {
				$.messager.alert(that.TITLE, "대상레이어가 없습니다. 대상레이어를 선택하여 주십시오.");
				return;
			}
		}
		
		var exportFilterTy = $("input[name=rad_menu_panel_file_export_scope]:checked", that.selector).val();
		if(exportFilterTy == "POLYGON") {
			if(!that.feature) {
				$.messager.alert(that.TITLE, "사용자 지정영역이 없습니다. 영역을 그려 주십시오.");
				return;
			}
		}
		
		var exportType = $(".sel_export_type", that.selector).combobox("getValue");
			
		windowObj.exportObj.open({
			userName : $("#hid_user_name").val(),
			deptName : $("#hid_dept_name").val(),
			exportType : exportType,
			onSubmit : function(params) {
				var type = $(".sel_export_type", that.selector).combobox("getValue");
				if(type == "IMAGE") {
					that.exportImage(type, params);
				}
				else if(type == "DXF" || type == "EXCEL" || type == "SHAPE") {
					that.exportData(type, params);
				}
				else {
					$.messager.alert(that.TITLE, "정의되지 않은 내보내기 타입입니다.");
				}
			}
		});
		
	},
	
	/**
	 * 이미지 내보내기
	 * @param {String} exportTy 내보내기 타입
	 * @param {String} exportNm 내보내기 명
	 */
	exportImage : function(exportTy, opt_params) {
		var that = this;
		var map = mapObj.getMap();
		
		var center = map.getCenter();
		var point = new ol.geom.Point(center);
		
		var from = map.getView().getProjection();
		var to = ol.proj.get("EPSG:4326");
		
		var transformPoint = spatialUtils.transform(point, from, to);
		var transformCenter = transformPoint.getCoordinates();
		
		map.once("postcompose", function(event) {
			var layers = map.getLayers();
			var checked = false;
			//현재 지도상에 baseMap이 있는지 확인
			//baseMap이 있다면 jpeg로 이미지파일 포맷으로 설정
			for(var i=0, len=layers.get("length"); i < len; i++) {
				if(layers.item(i).values_.id != 'base_map_group' && layers.item(i).values_.id !=  'overlay_group'){
					var source = layers.item(i).getSource();
					if(source instanceof ol.source.XYZ) {
						checked = true;
						break;
					}
				}
			}
			
			//baseMap이 있다면 jpeg로 vector만 있다면 png 포맷으로 설정
			var format = null;
			if(checked) {
				format = "image/jpeg";
			}
			else {
				format = "image/png";
			}
			
			if(map.tileQueue_.isEmpty()) {
				var canvas = event.context.canvas;
				var url = CONTEXT_PATH + "/rest/export/addByImage.do";
				var data = canvas.toDataURL(format);
				var params = $.extend({
					exportTy : exportTy,
					centerLon : transformCenter[0],
					centerLat : transformCenter[1],
					dataIds : sldObj.getLayerNames(),
					viewId : backgroundMapObj.getService(),
					tmsType : backgroundMapObj.getType(),
					baseMaps : JSON.stringify(themamapObj.basemapObj.getData()),
					data : data
				}, opt_params);
				
				$.post(url, params).done(function(result) {
					if(result && result.rowCount) {
						$.messager.confirm(that.TITLE, "내보내기 데이터는 '메인 > 나의 시스템 > 데이터요청 관리' 에서 다운로드 할 수 있습니다. 지금 확인하시겠습니까?", function(isTrue) {
							if(isTrue) {
								window.open(CONTEXT_PATH + "/self/export/list.do", "_blank");
							}
						});
					}
					else {
						$.messager.alert(that.TITLE, "내보내기 요청이 실패하였습니다.");
					}
				}).fail(function() {
					$.messager.alert(that.TITLE, "내보내기 요청이 실패하였습니다.");
				});
			}
			else {
				$.messager.alert("지도저장", "지도 로딩 중에는 사용할 수 없습니다.");
			}
		});
		map.renderSync();
	},
	
	/**
	 * 데이터 내보내기
	 * @param {String} exportTy 내보내기 타입
	 * @param {String} exportNm 내보내기 명
	 */
	exportData : function(exportTy, opt_params) {
		var that = this;
		
		var exportFilterTy = $("input[name=rad_menu_panel_file_export_scope]:checked", that.selector).val();
		var exportCntmId = $(".sel_coorinates", that.selector).combobox("getValue");
		
		var url = CONTEXT_PATH + "/rest/export/addByData.do";
		
		var params = $.extend({
			exportTy : exportTy,
			exportFilterTy : exportFilterTy,
			exportCntmId : exportCntmId,
			dataIds : that.dataIds
		}, opt_params);

		// 필터 추가
		if(exportFilterTy == "BBOX") {
			var map = mapObj.getMap();
			var from = map.getView().getProjection();
			var to = ol.proj.get(MAP.PROJECTION);
			var extent = spatialUtils.transformExtent(map.getExtent(), from ,to);
			params["minX"] = extent[0];
			params["minY"] = extent[1];
			params["maxX"] = extent[2];
			params["maxY"] = extent[3];
		}
		else if(exportFilterTy == "DONG") {
			params["dongId"] = $(".sel_dong", that.selector).combobox("getValue");
		}
		else if(exportFilterTy == "POLYGON") {
			if(that.feature) {
				var format = new ol.format.WKT();
				params["wkt"] = format.writeGeometry(that.feature.getGeometry());
			}
			else {
				$.messager.alert(that.TITLE, "사용자 지정영역이 없습니다. 영역을 그려 주십시오.");
				return;
			}
		}
		else {
			$.messager.alert(that.TITLE, "정의 되지 않은 범위 입니다.");
		}
		
		// 필드 명 가명 여부 추가 (엑셀)
		if(exportTy == "EXCEL") {
			params["fieldNmIndictAt"] = $(".chk_export_field_at", that.selector).is(":checked");
		}
		
		// 파일 내보내기 등록
		$.post(url, params).done(function(result) {
			if(result && result.rowCount) {
				$.messager.confirm(that.TITLE, "내보내기 데이터는 '메인 > 나의 시스템 > 데이터요청 관리' 에서 다운로드 할 수 있습니다. 지금 확인하시겠습니까?", function(isTrue) {
					if(isTrue) {
						window.open(CONTEXT_PATH + "/self/export/list.do", "_blank");
					}
				});
			}
			else {
				$.messager.alert(that.TITLE, "내보내기 요청이 실패하였습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "내보내기 요청이 실패하였습니다.");
		});
	}

};