$(function() {
	jqueryUtils.configAjax();
	
	// 좌표계 검색
	var cntmPromise = coordinateObj.load();
	
	// Sld 설정
	sldObj.setDataToString(opener.sldObj.getDataToString());
	var sldPromise = sldObj.putSld();
	
	var backgroundMapPromise = backgroundMapObj.init(false);
	$.when(cntmPromise, sldPromise, backgroundMapPromise).then(function() {
		var openerMap = opener.mapObj.getMap();
		var service = opener.backgroundMapObj.getService();
		backgroundMapObj.setService(service);
		
		var tmsType = opener.backgroundMapObj.getType();
		var tms = null;
		if(tmsType) {
			backgroundMapObj.setType(tmsType);
			tms = backgroundMapObj.getLayer(service, tmsType);
		}

		/// Lks : 항공영상 원점
		var origin = opener.mapObj.getTMSOrigin();
		if (origin) {
			tms[0].getSource().getTileGrid().origin_ = origin;		
		}
		/// Lks : End
		
		var view = backgroundMapObj.getView(service, tms);
		
		var center = openerMap.getCenter();
		var scale = openerMap.getScale();
		var extent = openerMap.getExtent();
		
		themamapObj.basemapObj.data = $.extend(true, [], opener.themamapObj.basemapObj.getData());
		highRankMapObj.init(view, tms, center, scale, extent);
		themamapObj.basemapObj.show(highRankMapObj.getMap());
		
		var format = new ol.format.GeoJSON();
		var features = format.readFeatures(opener.mapObj.getVectorFeatures());
		for(var i=0, len=features.length; i < len; i++) {
			if(features[i].getProperties().type == "Circle") {
				features[i] = spatialUtils.convertPointToCircle(features[i]);
			}
		}
		windowObj.drawToolObj.source.addFeatures(features);
		
		exportOutputObj.init();
	});
});

var exportOutputObj = {
		
	TITLE : "출력",
		
	map : null,
	
	/**
	 * 팝업
	 * @type {Popup}
	 */
	popup : null,
		
	init : function() {
		var that = this;
		
		that.initUi();
		that.bindEvents();
	},
	
	initUi : function() {
		// 파일 명 텍스트 박스
		$("#export_name").textbox({
			required : true,
			width : 200,
			prompt : "출력명을 입력하십시오."
		});
		
		$("#div_menubar *").css("vertical-align","");
		
	},
	
	bindEvents : function() {
		var that = this;
		
		// 내보내기 요청
		$("#a_export_request_export").click(function() {
			that.submit();
			return false;
		});
	},
	
	submit : function() {
		var that = this;
		
		var features = $.extend([], windowObj.drawToolObj.source.getFeatures());
		var from = highRankMapObj.getMap().getView().getProjection();
		var to = ol.proj.get(MAP.PROJECTION);
		if(from != to) {
			for(var i=0, len=features.length; i < len; i++) {
				var feature = features[i];
				var geometry = feature.getGeometry();
				feature.setGeometry(spatialUtils.transform(geometry, from ,to));
			}
		}
		for(var i=0, len=features.length; i < len; i++) {
			if(features[i].getProperties().type == "Circle") {
				features[i] = spatialUtils.convertCircleToPoint(features[i]);
			}
		}
		
		var format = new ol.format.GeoJSON();
		var userGraphics = format.writeFeatures(features); 
		var typeCheck = $('input[name=exportType]:checked').val();
		
		var exportType = 'EXPORT_OUTPUT';
		
		windowObj.exportObj.open({
			userName : $("#hid_user_name").val(),
			deptName : $("#hid_dept_name").val(),
			exportType : exportType,
			onSubmit : function(opt_params) {
				var center = highRankMapObj.getMap().getCenter();
				var from = highRankMapObj.getMap().getView().getProjection();
				var to = ol.proj.get("EPSG:4326");
				var transformCenter = spatialUtils.transformCooridate(center, from, to);
				
				var extent = $("#txt_extent").val().split(',');
				
				/// Lks : 항공영상 원점
				var origin = opener.mapObj.getTMSOrigin();
				if (origin == null) {
					origin = [0,0];
				}
				/// Lks : End
				
				var url = CONTEXT_PATH + "/rest/export/addByOutput.do";
				var params = null;
				var flag = null;
				
				// 축척일 때
				if(typeCheck=='scale'){
					flag = '0';
				// 영역일 때
				}else if(typeCheck=='area'){
					flag = '1';
				}
				
				params = $.extend({
					exportCntmId : highRankMapObj.getMap().getView().getProjection().getCode(),
					layoutId : $("#hid_layout_id").val(),
					exportTy : "OUTPUT",
					centerLon : transformCenter[0],
					centerLat : transformCenter[1],
					centerX : center[0],
					centerY : center[1],
					dataIds : sldObj.getLayerNames(),
					sc : $("#txt_scale").numberspinner("getValue"),
					viewId : backgroundMapObj.getService(),
					tmsType : backgroundMapObj.getType(),
					sld : JSON.stringify(sldObj.getData()),
					baseMaps : JSON.stringify(themamapObj.basemapObj.getData()),
					userGraphics : userGraphics,
					originX : origin[0],
					originY : origin[1],
					minX : extent[0],
					minY : extent[1],
					maxX : extent[2],
					maxY : extent[3],
					flag : flag
					}, opt_params);
				
				
				$.post(url, params).done(function(result) {
					if(result && result.rowCount) {
						$.messager.alert(that.TITLE, "내보내기 요청이 완료 되었습니다.", null, function() {
							opener.menuObj.outputObj.confirmRedirect();
							self.close();
						});
					}
					else {
						$.messager.alert(that.TITLE, "내보내기 요청이  실패하였습니다.");
					}
				}).fail(function() {
					$.messager.alert(that.TITLE, "내보내기 요청이  실패하였습니다.");
				});
				
			}
		});
	}
};