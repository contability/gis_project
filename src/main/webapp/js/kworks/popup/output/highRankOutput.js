$(function() {
	$(".div_loading").hide();
	
	jqueryUtils.configAjax();
	
	// 좌표계 검색
	var cntmPromise = coordinateObj.load();
	
	// Sld 설정
	var sld = JSON.parse($("#hid_sld").val());
	sldObj.setData(sld);
	var sldPromise = sldObj.putSld();
	
	var backgroundMapPromise = backgroundMapObj.init(false);
	$.when(cntmPromise, sldPromise, backgroundMapPromise).then(function() {
		var viewId = $("#hid_view_id").val();
		var view = backgroundMapObj.getView(viewId);
		var tmsType = $("#hid_tms_type").val();
		var tms = null;
		if(tmsType) {
			tms = backgroundMapObj.getLayer(viewId, tmsType);
			
			// Lks : 항공영상 원점 정보
			var originX = parseFloat($("#hid_origin_x").val());
			var originY = parseFloat($("#hid_origin_y").val());
			if (originX > 0 && originY > 0) {
				var origin = [originX, originY];
				tms[0].getSource().getTileGrid().origin_ = origin;		
			}
			// Lks : End
		}
		
		var centerX = $("#hid_center_x").val();
		var centerY = $("#hid_center_y").val();
		var center = [centerX, centerY];
		
		var scale = $("#hid_sc").val();
		
		var minX = $("#hid_min_x").val();
		var minY = $("#hid_min_y").val();
		var maxX = $("#hid_max_x").val();
		var maxY = $("#hid_max_y").val();
		var extent = [minX, minY, maxX, maxY];
		
		var flag = $("#hid_flag").val();
		
		
		var baseMaps = $("#hid_base_maps").val();
		if(baseMaps) {
			themamapObj.basemapObj.data = JSON.parse(baseMaps);
		}
		
		highRankMapObj.init(view, tms, center, scale, extent, flag);
		themamapObj.basemapObj.show(highRankMapObj.getMap());
		
		// 지도 이벤트 막기
		var map = highRankMapObj.getMap();
		
		var interactions = map.getInteractions();
	    interactions.forEach(function(interaction){
	    	if(interaction.get("active")) interaction.setActive(false);
	    });
	    
	    var userGraphics = $("#hid_user_graphics").val();
	    if(userGraphics) {
	    	var format = new ol.format.GeoJSON();
		    var features = format.readFeatures(userGraphics);
		    var from = ol.proj.get(MAP.PROJECTION);
			var to = highRankMapObj.getMap().getView().getProjection();
		    for(var i=0, len=features.length; i < len; i++) {
				if(features[i].getProperties().type == "Circle") {
					features[i] = spatialUtils.convertPointToCircle(features[i]);
				}
				if(from != to) {
					var geometry = feature.getGeometry();
					feature.setGeometry(spatialUtils.transform(geometry, from ,to));
				}
			}
		    windowObj.drawToolObj.source.addFeatures(features);
	    }
	    
		exportOutputObj.init();
	});
});

var exportOutputObj = {
		
	map : null,
	
	init : function(view, center, scale) {
		var that = this;
		that.initUi();
		that.getWatermarkCheck();
		that.bindEvents();
		that.loadPrints();
	},
	
	/// 동해시 워터마크 사용유무(체크박스 값 확인)함수  
	getWatermarkCheck : function(){
		var that = this;
		var checkWatermark;
		
		var checkedWatermark = $("#sel_check").is(":checked");
		if(checkedWatermark == false){
			checkWatermark = FULL_PATH + "/images/kworks/map/print/";
		} else {
			checkWatermark = FULL_PATH + "/images/kworks/map/print/watermark.png";
		}
		
		that.checkWatermark = checkWatermark;
	},
	
	initUi : function() {
		var that = this;
		
		// 프린터
		$("#sel_print").combobox({
			valueField : "name",
			textField : "name",
			width : 220,
			onSelect : function(data) {
				that.loadPapers(data.name);
			}
		});
		
		// 용지
		$("#sel_paper").combobox({
			valueField : "name",
			textField : "name",
			width : 60
		});
		
		var layoutId = $("#hid_layout_id").val();
			
		if(layoutId == 1){
			$("#div_info").css("height","199px");
			$("#div_scale").css("height","65px");
			$("#div_scale").css("top","765px");
		}else if(layoutId == 2){
			$("#div_info").css("height","204px");
			$("#div_scale").css("top","243px");
			$("#div_scale").css("padding","3px 0px 20px 4px");
			$("#div_scale").css("height","45px");
			$("#div_scale").css("width","216px");
		}
	},
	
	bindEvents : function() {
		var that = this;
		
		/// 인쇄 실행
		$("#a_exeute_output").click(function() {
			that.execute();
			return false;
		});
	},
	
	/**
	 * 프린터 목록 표시
	 */
	loadPrints : function() {
		var that = this;
		
		var data = opener.hghnkOutputObj.getData();

		var comboboxData = [];
		for(var i in data.printers) {
			comboboxData.push({ name : i});
		}
		$("#sel_print").combobox("loadData", comboboxData);
		if(data.defaultPrint) {
			$("#sel_print").combobox("setValue", data.defaultPrint);
		}
		else {
			$("#sel_print").combobox("setValue", comboboxData[0].name);
		}
		that.loadPapers(comboboxData[0].name);
	},
	
	/**
	 * 용지 목록 표시
	 */
	loadPapers : function(printerName) {
		var data = opener.hghnkOutputObj.getData();
		var comboboxData = [];
		for(var i in data.printers) {
			if(i == printerName) {
				for(var j in data.printers[i].papers) {
					comboboxData.push({ name : data.printers[i].papers[j]});
				}
				break;
			}
		}
		$("#sel_paper").combobox("loadData", comboboxData);
		$("#sel_paper").combobox("setValue", comboboxData[0].name);
	},
	
	/**
	 * 내보내기 실행
	 */
	execute : function() {
		var that = this;
		that.addExportOutput().done(function(response) {
			if(response && response.rowCount && response.rowCount > 0) {
				var printer = $("#sel_print").combobox("getValue");
				var paper = $("#sel_paper").combobox("getValue");
				var layoutId = $("#hid_layout_id").val();
				var srs = $("#hid_export_cntm_id").val();
				
				var centerX = $("#hid_center_x").val();
				var centerY = $("#hid_center_y").val();
				var center = [centerX, centerY].join();
				
				var scale = $("#hid_sc").val();
				var flag = $("#hid_flag").val();
				var minX = $("#hid_min_x").val();
				var minY = $("#hid_min_y").val();
				var maxX = $("#hid_max_x").val();
				var maxY = $("#hid_max_y").val();
				var bBox = [minX, minY, maxX, maxY].join();
				
				var layers = sldObj.getLayerNames().join();
				var sld = FULL_PATH + "/cmmn/sld/getSLD.do?sldKey=" + sldObj.getPrintSldKey();
				
				var author = $("#hid_user_name").val();
				var department = $("#hid_dept_name").val();
				var watermark = that.checkWatermark;
				
				var params = null;
				
				// 축척일 때
				if(flag == '0'){
					params = {					
						REQUEST : 'SendPrint',
						PRINTER : printer,
						PAPER : paper,
						LAYOUT : layoutId,
						SRS : srs,
						CENTER : center,
						SCALE : scale,
						LAYERS : layers,
						SLD : sld,
						AUTHOR : author,
						DEPARTMENT : department,
						// 워터마크
						Watermark : watermark
					};
				}else if(flag == '1'){
					params = {					
						REQUEST : 'SendPrint',
						PRINTER : printer,
						PAPER : paper,
						LAYOUT : layoutId,
						SRS : srs,
						BBOX : bBox,
						SCALE : scale,
						LAYERS : layers,
						SLD : sld,
						AUTHOR : author,
						DEPARTMENT : department,
						// 워터마크
						Watermark : watermark
					};
				}
				
				

				// 영상 출력 맵핑 정보
				var viewId = $("#hid_view_id").val();
				var tmsType = $("#hid_tms_type").val();
				
				var baseMaps = [];
				var baseMapTransparencies = [];
				if(viewId && tmsType) {
					if(tmsType == "hybrid") {
						baseMaps.push(viewId + "_SATELLITE");
						baseMapTransparencies.push(0);
					}
					baseMaps.push(viewId + "_" + tmsType);
					baseMapTransparencies.push(0);
				}
				
				var maps = themamapObj.basemapObj.getData();
				for(var i=0, len=maps.length; i < len; i++) {
					var tmsType = themamapObj.basemapObj.getTypeByMapNo(maps[i].mapNo);
					if(tmsType && maps[i].visible && maps[i].opacity > 0) {
						baseMaps.push("TMS_" + tmsType);
						baseMapTransparencies.push(sldObj.convertOpacityToPercent(maps[i].opacity));	
					}
				}
				params["BaseMaps"] = baseMaps.join().toUpperCase();
				params["BaseMapTransparency"] = baseMapTransparencies.join();
				
				if(MAP.SERVER_TYPE != 'kmaps') {
					params["SLD"] += "Print";
				}
				
				if(params["BaseMaps"]) {
					params["BMD"] = FULL_PATH + "/cmmn/baseMap/getBaseMapDescriptor.do?baseMaps=" + params["BaseMaps"];
					
					/// Lks : 항공영상 원점이동 정보
					var originX = $("#hid_origin_x").val();
					var originY = $("#hid_origin_y").val();
					if (originX.length > 0 && originY.length > 0) {
						var origin = [originX, originY].join();
						params["BaseMapOrigin"] = origin;
					}
					// Lks : End
				}
				
				var features = windowObj.drawToolObj.source.getFeatures();
				if(features && features.length > 0) {
					var exportNo = $("#export_no").val();
					params["UserGraphic"] = FULL_PATH + "/rest/export/getUserGraphics.do?exportNo="+exportNo;
				}
				
				// 고급출력 실행
				$(".div_loading").show();
				opener.hghnkOutputObj.execute(params).done(function(result) {
					if($(result).find("> PrintResult > Message").text() == "successful printing.") {
						alert("출력 완료 했습니다.");
					}
					else {
						alert("출력에 실패 했습니다.");
					}
					$(".div_loading").hide();
				}).fail(function() {
					alert("고급출력 프로그램이 제대로 설치되지 않았습니다. 고급 출력을 사용하기 위해 설치가 필요합니다.");
					opener.hghnkOutputObj.download();
					$(".div_loading").hide();
				});
			}
			else {
				alert("출력을 등록하는데 실패 했습니다.");
			}
		}).fail(function() {
			alert("출력을 등록하는데 실패 했습니다.");
		});
	},
	
	addExportOutput : function() {
		var exportNo = $("#export_no").val();
		var url = CONTEXT_PATH + "/rest/export/" + exportNo + "/addOutput.do";
		return $.post(url);
	}
	
};