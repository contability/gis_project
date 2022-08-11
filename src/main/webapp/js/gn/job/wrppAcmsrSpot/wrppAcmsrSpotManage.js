var wrppAcmsrSpotObj = {};

/**
 * 상수실시간측량지점(상수관로심도) 퀵메뉴
 */
wrppAcmsrSpotObj.quickSearch = {
		CLASS_NAME : 'WrppAcmsrSpotQuickMenu',
		
		searchWrppMesrWtnncImage : function(row) {
			var ftrCde = 'SA901';	//상수관로 지형지물부호
			var ftrIdn = row['ftrIdn'];
			var url = CONTEXT_PATH + "/photoManage/" + ftrCde + "/" + ftrIdn + "/list.do";
			
			$.get(url).done(function(response) {
				if (response != null && response.rows != null && response.rows.length > 0) {
					windowObj.photoManageObj.view.open(response.rows[0].imageNo, ftrCde, ftrIdn, null);
				} else {
					$.messager.alert("실측조서조회", "실측조서가 없습니다.");
				}
			});
		},
		
		searchWrppAcmsrSpot : function(feature) {
			var that = this;
			
			// 축척 제한
			var scale = mapObj.getMap().getScale();
			if(scale > 2500) {
				$.messager.confirm(that.TITLE, "2500 이하의 축척에서만 사용 가능합니다. 축척을 확대 하시겠습니까?", function(isTrue) {
					if(isTrue) {
						var cooridates = feature.getGeometry().getCoordinates();
						mapObj.getMap().moveToCenter(cooridates);
						mapObj.getMap().moveToScale(2500);
					}
				});
			} else {
				// 버퍼 적용
				var buffer = mapObj.getMap().getView().getResolution() * 10;
				if(buffer < 1) {
					buffer = 1;
				}
				var geometry = feature.getGeometry();
				var bufferGeometry = spatialUtils.buffer(geometry, buffer);
				highlightObj.showYellowFeatures(that.CLASS_NAME, [new ol.Feature(bufferGeometry)]);
				
				var mapProjection = mapObj.getMap().getView().getProjection();
				var sysProjection = ol.proj.get(MAP.PROJECTION);
				var transformGeometry = spatialUtils.transform(bufferGeometry, mapProjection, sysProjection);
				var format = new ol.format.WKT();
				var wkt = format.writeFeature(new ol.Feature(transformGeometry));

				var dataId = 'WTL_PIPE_PS';
				// 필터
				var filter = {
					filterType : "SPATIAL",
					spatialType : "INTERSECTS",
					wkt : wkt
				};
				spatialSearchUtils.listAll(that.TITLE, dataId, filter, function(rows) {
					if (rows && rows.length && rows.length > 0) {
						if (rows.length > 1) {
							//1개 선택
							windowObj.spatialSelectObj.open('실측지점', rows, {
								onSubmit : function(selectedRow) {
									if (selectedRow) {
										that.searchWrppMesrWtnncImage(selectedRow);
									} else {
										$.messager.alert("실측조서조회", "선택된 실측지점이 없습니다.");
									}
								}
							});
						} else {
							that.searchWrppMesrWtnncImage(rows[0]);
						}
						
					} else {
						$.messager.alert("실측조서조회", "선택 지점에 실측지점이 없습니다. 상수관로심도를 선택하여 주십시오.");
					}
					highlightObj.removeFeatures(that.CLASS_NAME);
				}, {isNoMessage : true});
			}
		},
		
		bindEvents : function() {
			var that = this;
			
			$('#a_quick_menu_wrppMesrWtnncImage').click(function(event) {
				selectObj.active(that.CLASS_NAME, 'Point', 'drawend', function(evt) {
					that.searchWrppAcmsrSpot(evt.feature);
				});
				highlightObj.removeFeatures(that.CLASS_NAME);
//				mainObj.defaultActive();
				event.preventDefault();
			});
		},
		
		initUi : function() {
			var tagStr = '<a id="a_quick_menu_wrppMesrWtnncImage" href="#"><img class="tool_toggle_radio img_ov_off tootip" src="'+ CONTEXT_PATH +'/images/kworks/map/tool/wrppMesrWtnnc_off.png" alt="실측조서보기" title="실측조서보기" /></a>';
			$('#div_quick_menu_container').append(tagStr);
		},
		
		init : function() {
			var that = this;
			
			that.initUi();
			that.bindEvents();
		}
};