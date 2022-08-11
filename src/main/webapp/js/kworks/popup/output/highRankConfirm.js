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
		
		var baseMaps = $("#hid_base_maps").val();
		if(baseMaps) {
			themamapObj.basemapObj.data = JSON.parse(baseMaps);
		}
		highRankMapObj.init(view, tms, center, scale);
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
		that.bindEvents();
		that.loadPrints();
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
		
		// 반려 창
		$("#div_export_return").window({
			width : 500,
			height : 270,
			modal : true,
			maximizable : false,
			minimizable : false,
			resizable : false,
			closed : true
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 승인 버튼이벤트
		$("#a_export_confirm").click(function() {
			that.exportConsent();
			return false;
		});
		
		// 반려 창 열기
		$("#a_open_return").click(function() {
			that.openReturn();
			return false;
		});
		
		// 반려 등록
		$("#a_export_return").click(function() {
			that.exportReturn();
			return false;
		});
		
		// 반려 창 닫기
		$("#a_export_return_close").click(function() {
			that.closeReturn();
			return false;
		});
		
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
		var printer = $("#sel_print").combobox("getValue");
		var paper = $("#sel_paper").combobox("getValue");
		var layoutId = $("#hid_layout_id").val();
		var srs = $("#hid_export_cntm_id").val();
		
		var centerX = $("#hid_center_x").val();
		var centerY = $("#hid_center_y").val();
		var center = [centerX, centerY].join();
		
		var scale = $("#hid_sc").val();
		var layers = sldObj.getLayerNames().join();
		var sld = FULL_PATH + "/cmmn/sld/getSLD.do?sldKey=" + sldObj.getPrintSldKey();
		var author = $("#hid_user_name").val();
		var department = $("#hid_dept_name").val();
		
		var params = {
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
			DEPARTMENT : department
		};
		
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
		
		/// 서버 타입이 kmaps 가 아닐 경우 SLD 뒤에 Print 추가
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
	},
	
	/**
	 * 승인
	 */
	exportConsent : function() {
		var exportNo = $("#export_no").val();
		var url = CONTEXT_PATH + "/rest/export/" +  exportNo + "/consent.do";
		
		$.post(url).done(function(result) {
			if(result && result.rowCount && result.rowCount == 1) {
				// 부모창 목록 갱신
				opener.exportObj.search();
				alert("승인 되었습니다.");
				// 고급출력창 닫기
				self.close();
			}
			else {
				alert("승인에 실패했습니다.");
			}
		}).fail(function() {
			alert("승인에 실패했습니다.");
		});
	},
	
	/**
	 * 반려 창 열기
	 */
	openReturn : function() {
		$("#div_export_return").window("open");
	},
	
	/**
	 * 반려 창 닫기
	 */
	closeReturn : function() {
		$("#div_export_return").window("open");
	},
	
	/**
	 * 반려
	 */
	exportReturn : function() {
		var exportNo = $("#export_no").val();
		var url = CONTEXT_PATH + "/rest/export/" +  exportNo + "/return.do";
		var params = {
			returnPrvonsh : $("#txa_export_detail_return_prvonsh").html()
		};
		$.post(url, params).done(function(result) {
			if(result && result.rowCount && result.rowCount == 1) {
				alert("반려 되었습니다.");
				opener.exportObj.search();
				self.close();
			}
			else {
				alert("반려에 실패했습니다.");
			}
		});
	}
	
};