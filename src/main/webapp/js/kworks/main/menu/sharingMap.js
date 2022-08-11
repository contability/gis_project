/**
 * 공유지도 객체
 */
menuObj.sharingMapObj = {
		/**
		 * 페이지
		 */
		PAGE : "sharingMap",
	
		/**
		 * 선택자
		 */
		selector : "#div_menu_panel_sharing_map",

		/**
		 * 제목
		 */
		TITLE : "공유지도",
	
		/**
		 * 지형지물 부호
		 */
		FTR_CDE : "ZZ997",
	
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
		selectFtrIdn : null,
	
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
				id : "poiLayer",
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
//				if (!that.selectFtrIdn) {
//					var prevSelectedFtrIdn = that.selectFtrIdn;
//					$(".div_sharing_map_item[data-ftr-idn="+prevSelectedFtrIdn+"]", that.selector).trigger("mouseout");
//				}
					var ftrIdn = feature.getId();
//				that.selectFtrIdn = ftrIdn;
					$(".div_sharing_map_item[data-ftr-idn="+ftrIdn+"]", that.selector).trigger("mouseover");
					$(".div_sharing_map_item[data-ftr-idn="+ftrIdn+"]", that.selector).trigger("click");
				}
			});
			mapObj.getMap().addInteraction(that.interaction);
		},
	
		/**
		 * UI 초기화
		 */
		initUi : function() {
			var that = this;
		
			// 위치 마커 항시 표시 여부
			$(".switch_always_show", that.selector).switchbutton({
				checked : false,
				onChange :function(checked) {
					that.alwaysShow = checked;
				}
			});
		
			// 파일 초기화
			$(".div_import_file", that.selector).html('<input id="file_import_file" name="file_import_file" type="file" />');
		},
		
		/**
		 * 이벤트 연결
		 */
		bindEvents : function() {
			var that = this;
		
			// 선택 표시
			$(".div_sharing_map", that.selector).on("mouseover", ".div_sharing_map_item", function() {
				var node = $(this);
				var ftrIdn = node.attr("data-ftr-idn");
				if(!that.selectFtrIdn) {
					var feature = that.source.getFeatureById(ftrIdn);
					if(feature) {
						feature.setStyle(that.getStyle(true));
					}
					node.addClass("on");
				}
			});
			$(".div_sharing_map", that.selector).on("mouseout", ".div_sharing_map_item", function() {
				var node = $(this);
				var ftrIdn = node.attr("data-ftr-idn");
				if(!that.selectFtrIdn || that.selectFtrIdn != ftrIdn) {
					that.source.getFeatureById(ftrIdn).setStyle(that.getStyle());
					node.removeClass("on");
				}
			});
		
			// 공유지도 추가
			$(".a_add_sharing_map", that.selector).click(function() {
				menuObj.sharingMapObj.add.open(that.FTR_CDE, function() {
					that.load();
				}, { title : "공유지도 등록" });
			});
		
			// KML 파일추가
			$(".a_add_kml_file", that.selector).click(function() {
				var fileInputBox = document.getElementById('file_import_file');
				fileInputBox.click();
				that.execute();
			});
			
			//선택항목 상세조회
			$(".div_sharing_map", that.selector).on("click", ".div_sharing_map_item", function() {
				var node = $(this);
				var ftrIdn = node.attr("data-ftr-idn");
				that.selectFtrIdn = ftrIdn;
				menuObj.sharingMapObj.view.open(that.FTR_CDE, ftrIdn, function() {
					that.load();
				}, function() {
					if(that.selectFtrIdn) {
						var feature = that.source.getFeatureById(ftrIdn);
						if(feature) {
							feature.setStyle(that.getStyle());
						}
						that.selectFtrIdn = null;
					}
					$(".div_sharing_map_item", that.selector).removeClass("on");
					that.interaction.getFeatures().clear();
				}, {
					title : "공유지도 상세정보"
				});
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
		 * 열기
		 */
		open : function() {
			var that = this;
			$(that.selector).show();
			that.layer.setVisible(true);
		
			// 화면 크기 변환 시 트리 영역 크기 조절 이벤트 등록
			mainObj.addResizeHandler("sharingMap", function() {
				that.resizeWindowHandler();
			});
			that.resizeWindowHandler();
		
			mapObj.getMap().on("moveend", that.show, that);
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
			$(".div_sharing_map", that.selector).height(height - titleHeight - toolsHeight - offset);
		},
		
		/**
		 * 닫기
		 */
		close : function() {
			var that = this;
			$(that.selector).hide();
			
			var visible = $(".switch_always_show", that.selector).switchbutton("options").checked;
			that.layer.setVisible(visible);
			mainObj.removeResizeHandler("sharingMap");
		},
		
		/**
		 * 공유지도 목록 불러오기
		 */
		load : function() {
			var that = this;
			that.source.clear();
		
			var url = CONTEXT_PATH + "/rest/sharingMap/listAllPoi.do";
			$.get(url).done(function(response) {
				that.rows = response.rows;
				var rows = that.rows;
				var features = [];
				for(var i=0, len=rows.length; i < len; i++) {
					var row = rows[i];
					var ftrIdn = row.ftrIdn;
					var point = gisObj.toTransformMap(new ol.geom.Point([row.lcX, row.lcY]));
					var feature = new ol.Feature(point);
					feature.setId(ftrIdn);
					feature.setStyle(that.getStyle());
					features.push(feature);
				}
				if(features.length > 0) {
					that.source.addFeatures(features);
				}
				that.show();
			}).fail(function() {
				$.messager.alert("공유지도", "공유지도를 가져오는데 실패했습니다.");
			});
		},
	
		//현재영역의 공유지도만 지도 및 목록 표출
		show : function() {
			var that = this;
			var extent = mapObj.getMap().getExtent();
			var rows = that.rows;
			var tagStr = '';
			for(var i=0, len=rows.length; i < len; i++) {
				var row = rows[i];
				var ftrIdn = row.ftrIdn;
				var imageNo = row.imageNo;
				var point = gisObj.toTransformMap(new ol.geom.Point([row.lcX, row.lcY]));
				var coords = point.getCoordinates();
				var src = '';
				if(ol.extent.containsXY(extent, coords[0], coords[1])) {
					tagStr += '<div class="div_sharing_map_item" data-ftr-idn="' + ftrIdn + '">';
					
					if (imageNo) {	//공유지도에 이미지가 첨부된 경우			
						if(row.thumbFileNo) {
							src = CONTEXT_PATH + "/cmmn/image/" + imageNo + "/thumbnail.do?nocache=" + Math.random();
						} else {
							src = CONTEXT_PATH + "/images/kworks/main/menu/video/video.png";
						}
					tagStr += '<div class="div_img" >';
					tagStr += '<img src="' + src + '" alt="' + row.poiSj + '" title="' + row.poiSj + '" />';
					tagStr += '</div>';
					tagStr += '<div class="div_content_image" >';
					} else {	//공유지도에 이미지가 없는 경우
						tagStr += '<div class="div_content" >';
					}
				
					tagStr += row.poiSj;	//이미지 유무과 상관없이 공유지도 제목 표시
					tagStr += '</div>';
					tagStr += '</div>';		
				}
			}
			$(".div_sharing_map", that.selector).html(tagStr);
		},
		
		/**
		 * Kml 가져오기
		 */
		execute : function() {
			var that = this;
					
			var files = document.getElementById('file_import_file').files;
			if(files) {
				if(files.length > 0) {
					var file = files[0];
					var extension = file.name.substring(file.name.lastIndexOf(".")+1).toLowerCase();
					if(extension == "kml") {
						that.executeImportKml(file);
					}
					else {
						$.messager.alert(that.TITLE, "확장자가 KML인 파일만 가져올 수 있습니다.");
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
		 * Kml 가져오기 실행 
		 * @params {File} file
		 */
		executeImportKml : function() {
			var that = this;
					
			var formData = new FormData();
			formData.append("importFile", $("#file_import_file")[0].files[0]);
			formData.append("ftrCde", that.FTR_CDE);
			
			$.ajax({
				url  : CONTEXT_PATH + "/rest/sharingMap/importKml.do",
				data : formData,
				processData: false,
	    	    contentType: false,
	    	    type: 'POST',
	    	    success: function(res){
	    	    	if(res) {
	    	    		if(res.isSuccess) {
	    	    			that.load();
	    	    		}
	    	    		// 파일 가져오기 서비스에서 Exception 발생
	    	    		else {
	    	    			$.messager.alert("가져오기", "Kml파일 가져오기에 실패했습니다.");
	    	    		}
	    	    	}
	    	    	else {
	    	    		$.messager.alert("가져오기", "Kml파일 가져오기에 실패했습니다.");
	    	    	}
	    	    }
			});
			
			// 파일 초기화
			$(".div_import_file", that.selector).html('<input id="file_import_file" name="file_import_file" type="file" />');
		}	
};

menuObj.sharingMapObj.add = {
		
		CLASS_NAME : "SharingMapAdd",
		
		TITLE : null,
		
		FTR_CDE : null,
		
		selector : null,
		
		onClose : null,
		
		open : function(ftrCde, onClose, options) {
			var that = this;
			
			that.FTR_CDE = ftrCde;
			that.onClose = onClose;
			
			var url = CONTEXT_PATH + "/window/sharingMap/addSharingMap/page.do?nocache=" + Math.random();
			var windowOptions = {
				width : 415,
				height : 275,
				onClose : function(){
					that.close();
				}
			};
			
			if(options && options.title) {
				that.TITLE = options.title;
			}
			else {
				that.TITLE = "공유지도 등록";
			}
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
				that.bindEvents();
				// promise step4
				that.loadData().done(function() {
					that.initUi();
				});
			});
			that.selector = "#" + id;
		},
		
		initUi : function() {
			var that = this;
			
			$("form", that.selector).ajaxForm({
				dataType : "json",
				beforeSubmit : function(arr, form) {
					for(var i=0, len=arr.length; i < len; i++) {
						var obj = arr[i];
						var name = obj.name;
						
						// 필수 입력 체크
						if(name == "poiSj") {
							if(!obj.value) {
								$.messager.alert(that.TITLE, "제목을 입력하여 주십시오.", null, function() {
									form.find("input[name=" + name + "]").focus();								
								});
								return false;
							}
						}
						else if(name == "lcX") {
							if(!obj.value) {
								$.messager.alert(that.TITLE, "위치를 입력하여 주십시오.", null, function() {
									form.find("input[name=" + name + "]").focus();	
								});
								return false;
							}
						}
						else if(name == "lcY") {
							if(!obj.value) {
								$.messager.alert(that.TITLE, "위치를 입력하여 주십시오.", null, function() {
									form.find("input[name=" + name + "]").focus();
								});
								return false;
							}
						}
						else if(name == "orignlFileNm") {
							if(obj.value) {
								var file = obj.value;
								if(file instanceof String) {
									fileName = file;
								}
								else {
									fileName = file.name;
								}
								var extension = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
								var extensions = ["jpg", "jpeg", "bmp", "gif", "png", "mp4"];
								if(extensions.indexOf(extension) == -1) {
									$.messager.alert(that.TITLE, "다음 확장자만 등록 가능합니다. (" + extensions.join(", ") + ")");
									return false;	
								}
							}
						}
					}
				},
				success : function(result) {
					if(that.onClose) {
						that.onClose();
					}
					that.close();
				}
			});
		},
		
		loadData : function() {
			var that = this;
			
			// promise step1
			var deferred = $.Deferred();
			
			// 지형지물부호
			$("#hid_add_sharing_map_ftr_cde", that.selector).val(that.FTR_CDE);
	
			// promise step2
			deferred.resolve();
					
			// promise step3
			return deferred.promise();
		},
		
		bindEvents : function() {
			var that = this;
			
			// 사진위치 좌표
			// selectObj.once
			$("#btn_add_sharing_map_lcXY", that.selector).on("click", function() {
				that.getLcXY();
				return false;
			});
			
			// 저장
			$(".a_save_add_sharing_map", that.selector).on("click", function() {
				that.add();
				return false;
			});
			
			// 닫기
			$(".a_close_add_sharing_map", that.selector).on("click", function() {
				that.close();
				return false;
			});
		},
		
		close : function() {
			var that = this;
			
			that.FTR_CDE = null;
			that.onClose = null;
			windowObj.removeWindow(that.selector);
			that.selector = null;
		},
		
		add : function() {
			var that = this;
			$("form", that.selector).submit();
		},
		
		getLcXY : function() {
			var that = this;
			selectObj.once(that.TITLE, "Point", "drawend", function(evt) {
				var coords = evt.feature.getGeometry().getCoordinates();
				var systemCoords = gisObj.totransformCooridateSystem(coords);
				
				$("#txt_add_sharing_map_lc_x", that.selector).val(systemCoords[0]);
				$("#txt_add_sharing_map_lc_y", that.selector).val(systemCoords[1]);
			}, false);
		}
		
};

//공유지도 상세보회
menuObj.sharingMapObj.view = {
		
		CLASS_NAME : "SharingMapDetail",
		
		TITLE : "공유지도 상세정보",
			
		selector : null,
		
		onRemove : null,
		
		onClose : null,
		
		/**
		 * 이벤트 바인딩 
		 */
		bindEvents : function() {
			var that = this;
		
			//공유지도 수정
			$(".a_modify_view_sharing_map", that.selector).on("click", function() {
				var ftrCde = $("#hid_view_sharing_map_ftr_cde", that.selector).val();
				var ftrIdn = $("#hid_view_sharing_map_ftr_idn", that.selector).val();
				menuObj.sharingMapObj.modify.open(ftrCde, ftrIdn, that.onRemove);
				that.close();
				return false;
			});

			//공유지도 삭제
			$(".a_remove_view_sharing_map", that.selector).on("click", function() {
				that.remove();
				return false;
			});
		
			//닫기
			$(".a_close_view_sharing_map", that.selector).on("click", function() {
				that.close();
				return false;
			});
		},
		
		/**
		 * 열기
		 * @param ftrCde, ftrIdn, onRemove, onClose, options
		 * @returns 
		 */
		open : function(ftrCde, ftrIdn, onRemove, onClose, options) {
			var that = this;

			//창이 열려 있으면 닫기
			if(that.selector) {
				that.close();
			}
			
			that.onRemove = onRemove;
			that.onClose = onClose;
	
			var url = CONTEXT_PATH + "/window/sharingMap/viewSharingMap/page.do";
			var windowOptions = {
				width : 360,
				height : 300,
				onClose : function(){
					that.close();
				}
			};
		
			var title = null;
			if(options && options.title) {
				title = options.title;
			}
			else {
				title = that.TITLE;
			}
		
			var id = windowObj.createWindow(that.CLASS_NAME, title, url, null, windowOptions, function() {
				that.bindEvents();
				that.loadData(ftrCde, ftrIdn);
			});

			that.selector = "#" + id;
		},
		
		loadData : function(ftrCde, ftrIdn) {
			var that = this;
		var url = CONTEXT_PATH + "/rest/sharingMap/" + ftrCde + '/' + ftrIdn + "/select.do";
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;
				
				if (data.imageNo) {	//사진이 있는 경우
					$(".picSlider", that.selector).show();
					$(".a_down_view_photo", that.selector).show();
					
					var imgDownloadUrl = CONTEXT_PATH + "/cmmn/file/" + data.imageFileNo + "/download.do";
					if(data.fileExtsn == "mp4") {	//동영상일 경우
						$(".li_image", that.selector).hide();
						$(".li_video", that.selector).show();
						$("#video_view").attr("src", imgDownloadUrl);
					} else {	//사진일 경우
						$(".li_image", that.selector).show();
						$(".li_video", that.selector).hide();
						var thumbnailUrl = CONTEXT_PATH + "/cmmn/image/" + data.imageNo + "/thumbnail.do?nocache=" + Math.random();
						$("#img_view_photo_manage_thumbnail", that.selector).attr("src", thumbnailUrl);
						
						var imageViewUrl = CONTEXT_PATH + "/popup/image/view.do?imageFileNo="+data.imageFileNo;
						$("#img_view_photo_manage_thumbnail", that.selector).parent().attr("href", imageViewUrl);
					}
					$(".a_down_view_photo", that.selector).attr("href", imgDownloadUrl);
				} else {	//사진이 없는 경우
					$(".picSlider", that.selector).hide();
					$(".a_down_view_photo", that.selector).hide();
					$('.cmmTable', that.selector).css('margin-top', 20);
				}
				
				//속성값 표출
				$("#hid_view_sharing_map_ftr_idn", that.selector).val(data.ftrIdn);
				$("#hid_view_sharing_map_ftr_cde", that.selector).val(data.ftrCde);
				
				$("#spn_view_sharing_map_frst_rgsde", that.selector).text(data.frstRgsde);			
				$("#spn_view_sharing_map_poi_sj", that.selector).text(data.poiSj);
				$("#spn_view_sharing_map_poi_cn", that.selector).text(data.poiCn);
				
				//상세조회 내용에 따라 레이어 팝업 리사이즈
				//이지점(아작스 콜백이 실행 끝나는)이 가장 늦게 실행됨
				that.windowResize();
			}
			else {
				alert("공유지도정보를 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("공유지도정보를 불러오는데 실패했습니다.");
		});
	},
	
		/**
		 * 상세조회 내용에 따라 레이어 팝업 리사이즈 
		 */
		windowResize : function() {
			var that = this;
			var height = $('.map_layerWrap', that.selector).height() + (($('.picSlider', that.selector).outerHeight(true) - $('.picSlider', that.selector).outerHeight()) / 2);
			$(that.selector).height(height);
			$('.window-shadow').height(height);
		
		},
		
		/**
		 * 창 닫기 
		 */
		close : function() {
			var that = this;
			if(that.onClose) {
				that.onClose();
			}
			$("#hid_view_sharing_map_ftr_idn", that.selector).val("");
			$("#hid_view_sharing_map_ftr_cde", that.selector).val("");
			that.onClose = null;
			that.onRemove = null;
			windowObj.removeWindow(that.selector);
			that.selector = null;
		},
		
		remove : function() {
			var that = this;
			
			var ftrCde = $("#hid_view_sharing_map_ftr_cde", that.selector).val();
			var ftrIdn = $("#hid_view_sharing_map_ftr_idn", that.selector).val();
			var url = CONTEXT_PATH + "/rest/sharingMap/" + ftrCde + '/' + ftrIdn + "/remove.do";
			
			$.get(url).done(function() {
				if(that.onRemove) {
					that.onRemove();
				}
				that.close();
				alert("공유지도가 삭제되었습니다.");
			}).fail(function(result) {
				alert("공유지도 삭제에 실패했습니다.");
			});
		}
};

//공유지도 수정 객체
menuObj.sharingMapObj.modify = {
		
		TITLE : null,
		
		CLASS_NAME : "SharingMapModify",
		
		selector : null,
		
		ftrIdn : null,
		
		onClose : null,
		
		initUi : function() {
			var that = this;
			
			$("form", that.selector).ajaxForm({
				dataType : "json",
				beforeSubmit : function(arr, form) {
					for(var i=0, len=arr.length; i < len; i++) {
						var obj = arr[i];
						var name = obj.name;
						
						// 필수 입력 체크
						if(name == "poiSj") {
							if(!obj.value) {
								$.messager.alert(that.TITLE, "제목을 입력하여 주십시오.", null, function() {
									form.find("input[name=" + name + "]").focus();								
								});
								return false;
							}
						}
						else if(name == "lcX") {
							if(!obj.value) {
								$.messager.alert(that.TITLE, "위치를 입력하여 주십시오.", null, function() {
									form.find("input[name=" + name + "]").focus();	
								});
								return false;
							}
						}
						else if(name == "lcY") {
							if(!obj.value) {
								$.messager.alert(that.TITLE, "위치를 입력하여 주십시오.", null, function() {
									form.find("input[name=" + name + "]").focus();
								});
								return false;
							}
						}
						else if(name == "orignlFileNm") {
							if(obj.value) {
								var file = obj.value;
								if(file instanceof String) {
									fileName = file;
								}
								else {
									fileName = file.name;
								}
								var extension = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
								var extensions = ["jpg", "jpeg", "bmp", "gif", "png", "mp4"];
								if(extensions.indexOf(extension) == -1) {
									$.messager.alert(that.TITLE, "다음 확장자만 등록 가능합니다. (" + extensions.join(", ") + ")");
									return false;	
								}
							}
						}
					}
				},
				success : function(result) {
					if(that.onClose) {
						that.onClose();
					}
					that.close();
				}
			});
		},

		bindEvents : function() {
			var that = this;
			
			$("#btn_modify_sharing_map_lcXY", that.selector).on("click", function() {
				that.getLcXY();
				return false;
			});
			
			$(".a_save_modify_sharing_map", that.selector).on("click", function() {
				that.modify();
				return false;
			});
			
			$(".a_close_modify_sharing_map", that.selector).on("click", function() {
				that.close();
				return false;
			});
		},
		
		getLcXY : function() {
			var that = this;
			
			selectObj.once(that.CLASS_NAME, "Point", "drawend", function(evt) {
				var coords = evt.feature.getGeometry().getCoordinates();
				var systemCoords = gisObj.totransformCooridateSystem(coords);
				$("#txt_modify_sharing_map_lc_x", that.selector).val(systemCoords[0]);
				$("#txt_modify_sharing_map_lc_y", that.selector).val(systemCoords[1]);
			}, false);
		},
		
		open : function(ftrCde, ftrIdn, onClose, options) {
			var that = this;

			that.ftrIdn = ftrIdn;
			that.onClose = onClose;
			
			var url = CONTEXT_PATH + "/window/sharingMap/modifySharingMap/page.do";
			var windowOptions = {
				width : 415,
				height : 275,
				onClose : function(){
					that.close();
				}
			};
			
			if(options && options.title) {
				that.TITLE = options.title;
			}
			else {
				that.TITLE = "공유지도 수정";
			}
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
				that.initUi();
				that.bindEvents();
				that.loadData(ftrCde, ftrIdn);
			});
			that.selector = "#" + id;
			
		},
		
		loadData : function(ftrCde, ftrIdn) {
			var that = this;
			
			that.ftrIdn = ftrIdn;
			
			var url = CONTEXT_PATH + "/rest/sharingMap/" + ftrCde + '/' + ftrIdn + "/select.do";
			
			$.get(url).done(function(result) {
					if(result && result.data) {
					var data = result.data;
					
					$("#hid_modify_photo_manage_image_no", that.selector).val(data.imageNo);
					$("#hid_modify_sharing_map_ftr_idn", that.selector).val(data.ftrIdn);
					$("#hid_modify_sharing_map_ftr_cde", that.selector).val(data.ftrCde);
					
					$("#txt_modify_sharing_map_poi_sj", that.selector).val(data.poiSj);
					$("#txt_modify_sharing_map_poi_cn", that.selector).val(data.poiCn);
					$("#txt_modify_sharing_map_lc_x", that.selector).val(data.lcX);
					$("#txt_modify_sharing_map_lc_y", that.selector).val(data.lcY);
				}
				else {
					alert("공유지도를 불러오는데 실패했습니다.");
				}
			}).fail(function(result) {
				alert("공유지도를 불러오는데 실패했습니다.");
			});
			
		},
		
		modify : function() {
			var that = this;
			
			$("form", that.selector).submit();
		},
		
		close : function() {
			var that = this;
			
			$("#hid_modify_photo_manage_image_no", that.selector).val("");
			$("#hid_modify_sharing_map_ftr_cde", that.selector).val("");
			$("#hid_modify_sharing_map_ftr_idn", that.selector).val("");
			that.ftrIdn = null;
			that.onClose = null;
			windowObj.removeWindow(that.selector);
			that.selector = null;
		}
};