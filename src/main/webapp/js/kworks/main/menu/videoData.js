/**
 * 영상 자료 객체
 */
menuObj.videoDataObj = {
	
	/**
	 * 페이지
	 */
	PAGE : "videoData",
	
	/**
	 * 선택자
	 */
	selector : "#div_menu_panel_video_data",
	
	/**
	 * 제목
	 */
	TITLE : "영상자료 공유",
	
	/**
	 * 지형지물 부호
	 */
	FTR_CDE : "ZZ999",
	
	/**
	 * 항시 표시 여부
	 */
	alwaysShow : false,
	
	/**
	 * 레이어
	 */
	layer : null,
	
	/**
	 * 벡터 소스
	 */
	source : null,
	
	/**
	 * 선택 영상 번호
	 */
	selectImageNo : null,
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.initGis();
		that.initUi();
		that.bindEvents();
		that.load();
		that.open();
	},
	
	/**
	 * GIS 초기화
	 */
	initGis : function() {
		var that = this;
		that.source = new ol.source.Vector();
		that.layer = new ol.layer.Vector({
			id : "videoLayer",
			source : that.source
		});
		mapObj.getMap().addLayer(that.layer);
		
		that.interaction = new ol.interaction.Select({
			layers : [that.layer]
		});
		that.interaction.set('id','videoSelect');
		that.interaction.set('name','videoSelect');
		that.interaction.on("select", function(event) {
			var feature = null;
			if(event.selected && event.selected.length > 0) {
				feature = event.selected[0];
			}
			if(feature) {
				var imageNo = feature.getId();
				$(".div_video_data_item[data-image-no="+imageNo+"]", that.selector).trigger("mouseover");
				$(".div_video_data_item[data-image-no="+imageNo+"]", that.selector).trigger("click");
			}
		});
		mapObj.getMap().addInteraction(that.interaction);
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 사진 마커 항시 표시 여부
		$(".switch_always_show", that.selector).switchbutton({
			checked : false,
			onChange :function(checked) {
				that.alwaysShow = checked;
			}
		});
		
	},
	
	/**
	 * 스타일
	 * @param isOn
	 * @returns {ol.style.Style}
	 */
	getStyle : function(isOn) {
		var that = this;
		var str = isOn?"_on":"";
		return new ol.style.Style({
			image : new ol.style.Icon({
				src : MAP.SYMBOL_URL + "/" + that.FTR_CDE + str + ".png",
				anchor : [1, 1]
			})
		});
	},
	
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 영상자료 추가
		$(".a_add_video_data", that.selector).click(function() {
			windowObj.photoManageObj.add.open(that.FTR_CDE, null, function() {
				that.load();
			}, { title : "영상자료 등록" });
		});
		
		// 선택 표시
		$(".div_video_data", that.selector).on("mouseover", ".div_video_data_item", function() {
			var node = $(this);
			var imageNo = node.attr("data-image-no");
			if(!that.selectImageNo) {
				var feature = that.source.getFeatureById(imageNo);
				if(feature) {
					feature.setStyle(that.getStyle(true));
				}
				node.addClass("on");
			}
		});
		$(".div_video_data", that.selector).on("mouseout", ".div_video_data_item", function() {
			var node = $(this);
			var imageNo = node.attr("data-image-no");
			if(!that.selectImageNo || that.selectImageNo != imageNo) {
				that.source.getFeatureById(imageNo).setStyle(that.getStyle());
				node.removeClass("on");
			}
		});
		$(".div_video_data", that.selector).on("click", ".div_video_data_item", function() {
			var node = $(this);
			var imageNo = node.attr("data-image-no");
			that.selectImageNo = imageNo;
			windowObj.photoManageObj.view.open(imageNo, that.FTR_CDE, null, function() {
				that.load();
			}, function() {
				if(that.selectImageNo) {
					var feature = that.source.getFeatureById(imageNo);
					if(feature) {
						feature.setStyle(that.getStyle());
					}
					that.selectImageNo = null;
				}
				$(".div_video_data_item", that.selector).removeClass("on");
				that.interaction.getFeatures().clear();
			}, {
				title : "영상관리 상세정보"
			});
		});
	},
	
	/**
	 * 영상 목록 불러오기
	 */
	load : function() {
		var that = this;
		that.source.clear();
		
		var url = CONTEXT_PATH + "/photoManage/" + that.FTR_CDE + "/listImageFile.do";
		$.get(url).done(function(response) {
			that.rows = response.rows;
			var rows = that.rows;
			var features = [];
			for(var i=0, len=rows.length; i < len; i++) {
				var row = rows[i];
				var imageNo = row.imageNo;
				var point = gisObj.toTransformMap(new ol.geom.Point([row.lcX, row.lcY]));
				var feature = new ol.Feature(point);
				feature.setId(imageNo);
				feature.setStyle(that.getStyle());
				features.push(feature);
			}
			if(features.length > 0) {
				that.source.addFeatures(features);
			}
			that.show();
		}).fail(function() {
			$.messager.alert("영상자료 공유", "사진정보를 가져오는데 실패했습니다.");
		});
	},
	
	show : function() {
		var that = this;
		var extent = mapObj.getMap().getExtent();
		var rows = that.rows;
		var tagStr = '';
		for(var i=0, len=rows.length; i < len; i++) {
			var row = rows[i];
			var imageNo = row.imageNo;
			var point = gisObj.toTransformMap(new ol.geom.Point([row.lcX, row.lcY]));
			var coords = point.getCoordinates();
			if(ol.extent.containsXY(extent, coords[0], coords[1])) {
				if(row.thumbFileNo) {
					src = CONTEXT_PATH + "/cmmn/image/" + imageNo + "/thumbnail.do?nocache=" + Math.random();
				}
				else {
					src = CONTEXT_PATH + "/images/kworks/main/menu/video/video.png";
				}
				
				tagStr += '<div class="div_video_data_item" data-image-no="' + imageNo + '">';
				tagStr += '<div class="div_img" >';
				tagStr += '<img src="' + src + '" alt="' + row.imageSj + '" title="' + row.imageSj + '" />';
				tagStr += '</div>';
				tagStr += '<div class="div_content" >';
				
				if(row.fileExtsn == "mp4" || row.fileExtsn == "MP4"
					|| row.fileExtsn == "avi" || row.fileExtsn == "AVI"
						|| row.fileExtsn == "mkv" || row.fileExtsn == "MKV"
							|| row.fileExtsn == "wmv" || row.fileExtsn == "WMV"
								|| row.fileExtsn == "mov" || row.fileExtsn == "MOV"
									|| row.fileExtsn == "png" || row.fileExtsn == "PNG"
										|| row.fileExtsn == "ts" || row.fileExtsn == "TS"
											|| row.fileExtsn == "tp" || row.fileExtsn == "TP"
												|| row.fileExtsn == "flv" || row.fileExtsn == "FLV"
													|| row.fileExtsn == "3gp" || row.fileExtsn == "3GP"){
					tagStr += "[영상] " + row.imageSj;
				}
				else if(row.fileExtsn == "jpg" || row.fileExtsn == "JPG"
					|| row.fileExtsn == "png" || row.fileExtsn == "PNG"
						|| row.fileExtsn == "jpeg" || row.fileExtsn == "JPEG"
							|| row.fileExtsn == "bmp" || row.fileExtsn == "BMP"
								|| row.fileExtsn == "gif" || row.fileExtsn == "GIF"
									|| row.fileExtsn == "tif" || row.fileExtsn == "TIF"
										|| row.fileExtsn == "tiff" || row.fileExtsn == "TIFF"){
					tagStr += "[사진] " + row.imageSj;
				}
				else {
					tagStr += row.imageSj;
				}
				tagStr += '</div>';
				tagStr += '</div>';
			}
		}
		$(".div_video_data", that.selector).html(tagStr);
	},
	
	/**
	 * 목록 사이즈 조절
	 */
	resizeWindowHandler : function() {
		var that = this;
		var height = $("#div_menu_panel").height();
		var titleHeight = $("> .title", that.selector).height();
		var toolsHeight = $(".div_tool", that.selector).height();
		var offset = 0;
		$(".div_video_data", that.selector).height(height - titleHeight - toolsHeight - offset);
	},
	
	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		$(that.selector).show();
		that.layer.setVisible(true);
		
		// 화면 크기 변환 시 트리 영역 크기 조절 이벤트 등록
		mainObj.addResizeHandler("videoData", function() {
			that.resizeWindowHandler();
		});
		that.resizeWindowHandler();
		
		mapObj.getMap().on("moveend", that.show, that);
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		$(that.selector).hide();
		
		var visible = $(".switch_always_show", that.selector).switchbutton("options").checked;
		that.layer.setVisible(visible);
		mainObj.removeResizeHandler("videoData");
	}
		
};