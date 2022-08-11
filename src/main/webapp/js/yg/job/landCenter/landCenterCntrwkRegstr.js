var landCenterCntrwkRegstrObj = {
		
	TITLE : "토지중심공사대장 검색",
	
	CLASS_NAME : "LandCenterCntrwkRegstrSearch",
	
	selector : null,
	
	isCreated : false,
	
	///퀵서치 결과가 1건일 경우 데이터를 삭제하여도 검색결과 목록창에 데이터목록이 갱신되지 않는 현상 처리
	///퀵서치 결과가 1건일 경우 데이터 삭제 후 목록갱신을 하여도 "검색결과가 없습니다." 메세지는 출력되나
	///목록갱신이 발생하지 않음
	///퀵서치 결과가 1건일 경우 데이터 삭제 후 검색결과 목록창을 닫도록 처리
	//퀵서치 수행여부
	isQuickSearch : false,
	
	//퀵서치 수행일 경우 검색 결과 수
	quickSearchResultCount : 0,
	
	initUi : function() {
		var that = this;
	
		// easyUi설정
		$("#listLandCenterCntrwkRegstr .easyui-datebox", that.selector).datebox();
		$("#listLandCenterCntrwkRegstr .easyui-textbox", that.selector).textbox();
		$("#listLandCenterCntrwkRegstr .easyui-combobox", that.selector).combobox();
		$("#listLandCenterCntrwkRegstr .easyui-numberbox", that.selector).numberbox();
		
		// 산여부
		$(".chk_mntn", that.selector).prop("disabled", true);
		
		// 본번
		$(".txt_mnnm", that.selector).numberbox({
			min : 1,
			max : 9999,
			disabled : true
		});

		// 부번
		$(".txt_slno", that.selector).numberbox({
			min : 0,
			max : 9999,
			disabled : true
		});
		
	},
	
	bindEvents : function() {
		var that = this; 
		
		$(".sel_dong", that.selector).combobox({
	         onChange : function(oldValue, newValue){
	        	 var dongCode = $(".sel_dong", that.selector).combobox("getValue");
	        	 
	        	 // 읍면동 = 양구군O 
	        	 if(dongCode == "4280000000"){
	        		 
	            	// checkbox, numberbox 비활성화
	            	$(".chk_mntn", that.selector).prop("disabled", true);
	            	$(".txt_mnnm", that.selector).numberbox({
	            		disabled : true
	            	});
	            	$(".txt_slno", that.selector).numberbox({
	            		disabled : true
	            	});
	            	
	            	///산 여부, 본번, 부번 초기화 
	            	$("#listLandUseInfoRegstr .easyui-numberbox", that.selector).numberbox('clear');
	            	$(".chk_mntn").attr('checked', false);
	            	
	            }
	        	// 읍면동 = 양구군X 
	        	else {
	        		// checkbox, numberbox 활성화
	            	$(".chk_mntn", that.selector).prop("disabled", false);
	            	$(".txt_mnnm", that.selector).numberbox({
	            		disabled : false
	            	});
	            	$(".txt_slno", that.selector).numberbox({
	            		disabled : false
	            	});
	            	
	            }
	         }
	      });
		
		//검색
		$(".a_search_landCenterCntrwkRegstr", that.selector).on("click", function(){
			if(that.validator()){
				that.search();
			}
			
			return false;
		});
		
		///초기화
		$(".a_reset_landCenterCntrwkRegstr", that.selector).on("click", function(){
			
			$("#listLandCenterCntrwkRegstr .easyui-textbox", that.selector).textbox('clear');
			$("#listLandCenterCntrwkRegstr .easyui-numberbox", that.selector).numberbox('clear');
			$("#listLandCenterCntrwkRegstr .easyui-datebox", that.selector).datebox('clear');
			
			return false;
		});
		
	},
	
	validator : function() {
		var that = this;
		
		// 법정동
		if(!$(".sel_dong", that.selector).combobox("isValid")) {
			$.messager.alert(that.TITLE, "법정동을 선택하여 주십시오.");
			return false;
		}
		// 본번
		if(!$(".txt_mnnm", that.selector).numberbox("isValid")) {
			$.messager.alert(that.TITLE, "본번을 입력하여 주십시오.", null);
			return false;
		}
		return true;
	},
	
	search : function() {
		var that = this;
		//조건 검색일 경우 퀵서치 검색이 아님을 저장
		that.isQuickSearch = false;
		
		var cql = '1 = 1';
		//공사번호
		var cntIdn = $("input[name=cntIdn]", that.selector).val();
		if (cntIdn != '') {
			cql += " AND CNT_IDN = '" + cntIdn + "'";
		}
		
		//공사명
		var cntNam = $("input[name=cntNam]", that.selector).val();
		if (cntNam != '') {
			cql += " AND CNT_NAM LIKE '%" + cntNam + "%'";
		}
		
		//계약일
		var cttYmdMin = $("input[name=cttYmdMin]", that.selector).val();
		if (cttYmdMin != '') {
			cql += " AND CTT_YMD >= '" + cttYmdMin + "'";
		}
		var cttYmdMax = $("input[name=cttYmdMax]", that.selector).val();
		if (cttYmdMax != '') {
			cql += " AND CTT_YMD <= '" + cttYmdMax + "'";
		}
			
		//PNU
		var dongCode = $(".sel_dong", that.selector).combobox("getValue");
		var mntn = $(".chk_mntn", that.selector).is(":checked")?"2":"1";
		var mnnm = $(".txt_mnnm", that.selector).numberbox("getValue");
		var slno = $(".txt_slno", that.selector).numberbox("getValue");
		if(dongCode != "4280000000") {
			if(mnnm == '' && slno != ''){
				$.messager.alert(that.TITLE, "본번을 입력하십시오.");
			} else {
				if(dongCode!='' && mntn == "1" && mnnm!='' && slno!=''){
					var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
					cql += " AND PNU = '" + pnuCode + "'";
				}
				else if(dongCode!='' && mntn == "2" && mnnm!='' && slno!=''){
					var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
					cql += " AND PNU = '" + pnuCode + "'";
				}
				else if(dongCode!='' && mntn == "1" && mnnm!=''){
					var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
					var cutPnuCode = pnuCode.substring(0, 15);
					cql += " AND PNU LIKE '" + cutPnuCode + "%'";
				}
				else if(dongCode!='' && mntn == "2" && mnnm!=''){
					var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
					var cutPnuCode = pnuCode.substring(0, 15);
					cql += " AND PNU LIKE '" + cutPnuCode + "%'";
				}
				else if(dongCode!='' && mntn == "1"){
					var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
					var cutPnuCode = pnuCode.substring(0, 11);
					cql += " AND PNU LIKE '" + cutPnuCode + "%'";
				}
				else if(dongCode!='' && mntn == "2"){
					var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
					var cutPnuCode = pnuCode.substring(0, 11);
					cql += " AND PNU LIKE '" + cutPnuCode + "%'";
				}
			}
		}
		
		var dataIds = 'LDL_CONS_PS';
		var filter = {
				filterType : "CQL",
				cql : cql
		};
		spatialSearchUtils.searchSummaries(that.TITLE, dataIds, filter, function(rows) {
			if(rows && rows.length > 0) {
				resultObj.close();
				resultObj.addRows(rows);
				mapToolObj.deactive();
				that.close();
			}
		});
	},
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/landCenter/searchLandCenterCntrwkRegstrPage.do";
		var windowOptions = {
			width : 655,
			height : 240, //IE 창크기 
			top : 120,
			left : 330,
			isClose : function() {
				that.close();
				
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.initUi();
				that.bindEvents();
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	},
	
	inputBoxValidator : function($divContents, objTitle) {
		var validationResult = true;
		
		$divContents.find('input').each(function() {
			if ($(this).prop('required')) {
				if ($(this).hasClass('easyui-textbox')) {
					if ($(this).textbox('getValue') == '' || $(this).textbox('getValue') == null) {
						var fieldAlias = $('label[for="' + $(this).attr('id') + '"]').text();
						validationResult = false;
						$(this).focus();
						$.messager.alert(objTitle, fieldAlias + "은(는) 필수값입니다. 입력해 주십시오.");
						return false;
					}
				} else if ($(this).hasClass('easyui-numberbox')) {
					if ($(this).numberbox('getValue') == '' || $(this).numberbox('getValue') == null) {
						var fieldAlias = $('label[for="' + $(this).attr('id') + '"]').text();
						validationResult = false;
						$(this).focus();
						$.messager.alert(objTitle, fieldAlias + "은(는) 필수값입니다. 입력해 주십시오.");
						return false;
					}
				} else if ($(this).hasClass('easyui-datebox')) {
					if ($(this).datebox('getValue') == '' || $(this).datebox('getValue') == null) {
						var fieldAlias = $('label[for="' + $(this).attr('id') + '"]').text();
						validationResult = false;
						$(this).focus();
						$.messager.alert(objTitle, fieldAlias + "은(는) 필수값입니다. 입력해 주십시오.");
						return false;
					}
				} else {
					if ($(this).val() == '' || $(this).val() == null) {
						var fieldAlias = $('label[for="' + $(this).attr('id') + '"]').text();
						validationResult = false;
						$(this).focus();
						$.messager.alert(objTitle, fieldAlias + "은(는) 필수값입니다. 입력해 주십시오.");
						return false;
					}
				}
			}
		});
		
		if (validationResult) {
			return true;
		} else {
			return false;
		}
	},
	
	comboBoxValidator : function($divContents, objTitle) {
		var validationResult = true;
		
		$divContents.find('select').each(function() {
			if ($(this).prop('required')) {
				if ($(this).hasClass('easyui-combobox')) {
					if ($(this).combobox('getValue') == '' || $(this).combobox('getValue') == null) {
						var fieldAlias = $('label[for="' + $(this).attr('id') + '"]').text();
						validationResult = false;
						$(this).focus();
						$.messager.alert(objTitle, fieldAlias + "은(는) 필수값입니다. 입력해 주십시오.");
						return false;
					}
				}
			}
		});
		
		if (validationResult) {
			return true;
		} else {
			return false;
		}
	}
};

///토지중심공사대장 Map 조회
landCenterCntrwkRegstrObj.map = {
		/**
		 * 벡터 소스
		 */
		source : null,
		
		iconName : 'LDL_CONS_PS_POI',
		
		CLASS_NAME : 'LDL_CONS_PS_POI',
		
		highlightedFeature : null,
		
		currentResolution : null,
		
		maxFeatureCount : null,
		
		vector : null,
		
		init : function () {
			var that = this;
			
			that.initGis();
			that.loadLdlConsPoi();
		},
		
		initGis : function () {
			var that = this;
			
			that.source = new ol.source.Vector();			
			that.vector = new ol.layer.Vector({
				id : "ldlConsPoi",
				source : new ol.source.Cluster({
					distance : 40,
					source : that.source
				}),
				style : that.styleFunction
			});
			mapObj.getMap().addLayer(that.vector);
		},
		
		styleFunction : function(feature, resolution) {
			if (resolution != landCenterCntrwkRegstrObj.map.currentResolution) {
				landCenterCntrwkRegstrObj.map.calculateClusterInfo(resolution);
				landCenterCntrwkRegstrObj.map.currentResolution = resolution;
			}
			var style;
			var size = feature.get('features').length;
			if (size > 1) {
				
				style = new ol.style.Style({
					image: new ol.style.Circle({
			        radius: feature.get('radius'),
			        fill: new ol.style.Fill({
			          color: [255, 153, 0, Math.min(0.8, 0.4 + (size / landCenterCntrwkRegstrObj.map.maxFeatureCount))]
			        })
			      }),
			      text: new ol.style.Text({
			    	  font : '14px sans-serif',
			        text: size.toString(),
			        fill: new ol.style.Fill({
			        	color: '#fff'
			        }),
			        stroke: new ol.style.Stroke({
			        	color: 'rgba(0, 0, 0, 0.6)',
			        	width: 3
			        }),
			      })
			    });
			  } else {
			    style = new ol.style.Style({
					image : new ol.style.Icon({
						src : MAP.SYMBOL_URL + "/" + landCenterCntrwkRegstrObj.map.iconName + ".png",
						anchor : [0.5, 1]
					})
				});
			  }
			  return style;
		},
		
		calculateClusterInfo : function(resolution) {
			var that = this;

			that.maxFeatureCount = 0;
			var features = that.vector.getSource().getFeatures();
			var feature, radius;
			for (var i = features.length - 1; i >= 0; --i) {
				feature = features[i];
				var originalFeatures = feature.get('features');
				var extent = ol.extent.createEmpty();
				var j = (void 0), jj = (void 0);
				for (j = 0, jj = originalFeatures.length; j < jj; ++j) {
					ol.extent.extend(extent, originalFeatures[j].getGeometry().getExtent());
				}
				that.maxFeatureCount = Math.max(that.maxFeatureCount, jj);
				radius = 0.25 * (ol.extent.getWidth(extent) + ol.extent.getHeight(extent)) / resolution;
				feature.set('radius', radius);
			}
		},
		
		loadLdlConsPoi : function () {
			var that = this;
			
			that.source.clear();
			var format = new ol.format.WKT();
			
			var url = CONTEXT_PATH + "/landCenter/listAllPoi.do?nocache="+Math.random();
			$.get(url).done(function(response) {
				that.rows = response.rows;
				var rows = that.rows;
				var features = [];
				for(var i=0, len=rows.length; i < len; i++) {
					var row = rows[i];
					if (row.wkt != '') {
						var feature = format.readFeature(row.wkt);
						features.push(feature);
					}
				}
				if(features.length > 0) {
					that.source.addFeatures(features);
				}
			}).fail(function() {
				$.messager.alert("토지중심공사이력관리", "토지중심공사위치를 가져오는데 실패했습니다.");
			});
		},
		
		bindMapEvents : function() {
			var that = this;
			
			//map singleclick
			mapObj.getMap().on('singleclick', function(evt) {
				if (mapToolObj.activeObj 
								&& (mapToolObj.activeObj.CLASS_NAME == 'X-Ray' || mapToolObj.activeObj.CLASS_NAME == 'Measure' 
							|| mapToolObj.activeObj.CLASS_NAME == 'Measure' || mapToolObj.activeObj.CLASS_NAME == 'Draw' 
								|| mapToolObj.activeObj.CLASS_NAME == 'SpatialInfo' || mapToolObj.activeObj.CLASS_NAME == 'SpatialSearch' 
									|| mapToolObj.activeObj.CLASS_NAME == 'Zoom')) {
					return false;
				}
						
				if (quickObj.CLASS_NAME == 'SearchInfoKras' || quickObj.CLASS_NAME == 'landUseInfo' 
							|| quickObj.CLASS_NAME == 'SearchInfoSeaoll') {
						quickObj.CLASS_NAME = null;
						return false;
				}
						
				if(mainObj.getSingleActiveObj()) {
					return null;
				}
						
				landCenterCntrwkRegstrObj.quick.getSearchLoc(evt.pixel);	
			});
			
			mapObj.getMap().on('pointermove', function(evt) {
				if (evt.dragging) {
					return;
				}
				
				var feature = mapObj.getMap().forEachFeatureAtPixel(evt.pixel, function(feature) {
					return feature;
				});
				
				if (feature != that.highlightedFeature) {
					if (that.highlightedFeature) {
						highlightObj.removeFeatures(that.CLASS_NAME);
					}
				
					if (feature) {
						highlightObj.showRedFeatures(that.CLASS_NAME, [feature], false, {
							projection : ol.proj.get(MAP.PROJECTION)
						});
					}
				}
			
				that.highlightedFeature = feature;
			});
		}
},

/// 토지중심공사대장 조회
landCenterCntrwkRegstrObj.view = {
		
	TITLE : "토지중심공사대장 상세정보",
	
	CALSS_NAME : "landCenterCntrwkRegstrDetail",
	
	selector : null,
	
	cntIdn : null,
	
	isCreated : false,
	
	initUi : function() {
		var that = this;
		
		// tab 초기화
		$(".tab-list-1 li a", that.selector).on("click", function() {
			$(".tab-list-1 li, .tab-panel-1", that.selecor).removeClass("active");
			$(this, that.selector).parent().addClass("active");
			$(".tab-panel-1", that.selector).eq($(this, that.selector).parent().index()).addClass("active");
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		$(".tableSelector tbody tr", that.selector).click(function() {
			var element = $(this);
			var fids = element.attr("data-lui-idn");
			
			if(fids && fids.length > 0) {
				
				landUseInfoRegstrObj.view.open([fids]);
			}
		});
		
		$(".cntDept", that.selector).combobox({
			onChange : function(oldValue, newValue){
				$(".cntDept", that.selector).combobox("getValue");
			}
		});
		
		// 수정버튼
		$(".btn_modify_landCenterCntrwkRegstrDetail").on("click", function(){
			var cntIdn = $("#cntIdn", that.selector).val();
			
			landCenterCntrwkRegstrObj.modify.open(cntIdn);
			that.close();
			
			return false;
		});
		
		// 삭제버튼
		$(".btn_delete_landCenterCntrwkRegstrDetail").on("click", function(){
			$.messager.confirm(that.TITLE, "삭제하시겠습니까?", function(isTrue) {
				if(isTrue) {
					that.remove();
				}
			});
			return false;
		});
		
		// 닫기버튼
		$(".btn_close_landCenterCntrwkRegstrDetail").on("click", function(){
			that.close();
		
			return false;
		});
	},
		
	remove : function(){
		var that = this;
		
		that.cntIdn = $("#cntIdn", that.selector).val();
		
		var url = CONTEXT_PATH + "/landCenter/" + that.cntIdn + "/removeLandCenterCntrwkRegstr.do";
		
		$.post(url).done(function(result) {
			if(result) {
				landCenterCntrwkRegstrObj.map.loadLdlConsPoi();

				//Quick Search 시 검색결과가 한건 이었으면, 삭제 후 결과 목록 현황을 닫는다.
				//검색결과가 한건 이상일 경우 검색결과목록창을 갱신한다.
				if (landCenterCntrwkRegstrObj.isQuickSearch) {
					if (landCenterCntrwkRegstrObj.quickSearchResultCount > 1) {
						landCenterCntrwkRegstrObj.quickSearchResultCount = landCenterCntrwkRegstrObj.quickSearchResultCount -1;
						resultObj.refreshResultLayersGrid();
					} else {
						landCenterCntrwkRegstrObj.isQuickSearch = false;
						resultObj.close();
					}
				} else {
					resultObj.refreshResultLayersGrid();
				}
	
				that.close();
				$.messager.alert(that.TITLE, "토지중심공사대장을 삭제하였습니다.");
			}
			else {
				$.messager.alert(that.TITLE, "토지중심공사대장 삭제에 실패했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "토지중심공사대장 삭제에 실패했습니다.");
		});
	},
	
	open : function(fids) {
		var that = this;

		if(fids.length == 1){

			if(that.selector) {
				that.close();
			}
			
//			that.cntIdn = cntIdn;
			
			var url = CONTEXT_PATH + "/landCenter/" + fids + "/selectOneLandCenterCntrwkRegstrPage.do";
			var windowOptions = {
				width : 680,
				height : 432,
				top : 120,
				left : 550,
				onClose : function() {
					that.close();
				}
			};
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
				if(!that.isCreated) {
					
					that.initUi();
					that.bindEvents();
				
					//초기화
					var getCodeNm = "";
					var getSanCde = "";
				
					var pnu = $("#pnu").val();
				
					var splitSelDong = pnu.substr(0,10);
					var splitSanCde = pnu.substr(10,1);
					if(splitSanCde == 2){
						getSanCde = "산";
					}
				
					var splitFacNum = pnu.substr(11,4);
					var splitFadNum = pnu.substr(15,4);
					var zeroTrimFacNum = splitFacNum.replace(/(^0+)/, "");
					var zeroTrimFadNum = splitFadNum.replace(/(^0+)/, "");
					if(zeroTrimFadNum != ""){
						zeroTrimFadNum = " - " + zeroTrimFadNum ;
					}
					
					if(pnu != null){
						$.get(CONTEXT_PATH +"/landCenter/" + splitSelDong + "/selectSeldong.do").done(function(data){
							if(data!= null && data.result!=""){
								for(var i=0, iLen=data.result.length; i<iLen; i++){
									if(data.result[i].codeId == splitSelDong) {
										getCodeNm = data.result[i].codeNm;
									}
								}
								$("#setAddr").html(getCodeNm + " " +  getSanCde + " " + zeroTrimFacNum + zeroTrimFadNum);
							} 
						}).fail(function(){
							$.messager.alert("읍면동 정보", "해당 읍면동 정보를 불러오는데 실패했습니다.");
						});
					}
	
					var cntIdn = document.getElementById('cntIdn').value;
					if(cntIdn != null ){
						$.get(CONTEXT_PATH +"/landCenter/" + cntIdn + "/selectFile.do").done(function(data){
							if(data!= null && data.result!=""){
								var tagStr = '';
								tagStr += '<tr>';
								tagStr += '	<th>붙임문서</th>';
								tagStr += '	<td colspan="5">';
								//tagStr += '<a href="'+ CONTEXT_PATH + '/cmmn/file/' + data.result.fileNo + '/download.do" target="_blank">';
								tagStr += '<a href="'+ CONTEXT_PATH + '/landCenter/pdf.do?fileNo=' + data.result.fileNo +'" target="_blank">';
								tagStr += '<span>' + data.result.orignlFileNm + "</span>";
								tagStr += '	</td>';
								tagStr += '</tr>';
							$(".atcFileViewArea").append(tagStr);
							} 
						}).fail(function(){
							$.messager.alert("파일정보", "파일 정보를 불러오는데 실패했습니다.");
						});
					}
					that.isCreated = true;
				}
			});
			that.selector = "#" + id;
		} else {
			$.messager.alert(that.TITLE, "하나의 항목만 조회가 가능합니다.");
		}
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.cntIdn = null;
		that.selector = null;
		that.isCreated = false;
		
		$(".addFileArea").html('');
		$("#setAddr").html('');
	}
		
};

/// 토지중심공사대장 수정
landCenterCntrwkRegstrObj.modify = {
	
	TITLE : "토지중심공사대장 수정",
	
	CLASS_NAME : "LandCenterCntrwkRegstrModify",
	
	selector : null,
	
	cntIdn : null,
	
	isCreated : false,
		
	init : function() {
		var that = this;

		var objectid = document.getElementById('landCenterCntrwkRegstrModifyformObjectid').value; 
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					landCenterCntrwkRegstrObj.view.open([objectid]);
					that.close();
					landCenterCntrwkRegstrObj.map.loadLdlConsPoi();
					resultObj.refreshResultLayersGrid();
					alert("토지중심공사대장이 수정되었습니다.");
				}
				else {
					alert("토지중심공사대장 수정에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function(){
		var that = this;
		
		// easyUi설정
		$("#landCenterCntrwkRegstrModify .easyui-datebox", that.selector).datebox();
		$("#landCenterCntrwkRegstrModify .easyui-textbox", that.selector).textbox();
		$("#landCenterCntrwkRegstrModify .easyui-combobox", that.selector).combobox();
		$("#landCenterCntrwkRegstrModify .easyui-numberbox", that.selector).numberbox();
		
		// tab 초기화
		$(".tab-list-1 li a", that.selector).on("click", function() {
			$(".tab-list-1 li, .tab-panel-1", that.selecor).removeClass("active");
			$(this, that.selector).parent().addClass("active");
			$(".tab-panel-1", that.selector).eq($(this, that.selector).parent().index()).addClass("active");
		});
		
		//pnu = 법/정읍면동10자리 + 산1자리 + 본번4자리 + 부번4자리 = 총 19자리 
		var pnu = $("#pnu").val();
		var splitPnu = pnu.substr(0,10);
		var splitSanCde = pnu.substr(10,1);
		var splitFacNum = pnu.substr(11,4);
		var splitFadNum = pnu.substr(15,4);
		
		var trimZeroFacNum = splitFacNum.replace(/(^0+)/,"");
		var trimZeroFadNum = splitFadNum.replace(/(^0+)/,"");
		
		$(".modify_sel_dong").combobox("select", splitPnu);
		
		if(splitSanCde != null && splitSanCde == 2){
			$(".modify_sanCde").prop("checked",true);
		} else {
			$(".modify_sanCde").prop("checked",false);
		}
		
		//본번
		if(splitFacNum != 0){
			$(".modify_facNum").numberbox('setValue', trimZeroFacNum);
		} else {
			$(".modify_facNum").numberbox('setValue', '');
		}		
		//부번
		if(splitFadNum != 0){
			$(".modify_fadNum").numberbox('setValue', trimZeroFadNum);
		} else {
			$(".modify_fadNum").numberbox('setValue', '');
		}
		
		//공사부서 콤보박스
		var cntDept = $("#landCenterCntrwkRegstrModifyformCntDept", that.selector).val();
		$(".modify_cnt_dept").combobox("select", cntDept);
		
	},
	
	bindEvents : function(){
		var that = this;
		
		// 저장버튼
		$(".btn_save_landCenterCntrwkRegstrModify", that.selector).on("click", function() {
			if (landCenterCntrwkRegstrObj.inputBoxValidator($('.window_article', that.selector), that.TITLE)
					&& landCenterCntrwkRegstrObj.comboBoxValidator($('.window_article', that.selector), that.TITLE)) {
				that.modify();
			}
		});
		
		// 취소버튼
		$(".btn_close_landCenterCntrwkRegstrModify", that.selector).on("click", function() {
			that.close();
		});
		
		//붙임문서 삭제버튼
		$(that.selector).on("click", ".fileCheck", function() {
			var node = $(this);
			$.messager.confirm("삭제", "붙임문서를 삭제하시겠습니까?", function(isTrue) {
				if(isTrue) {
            		var getVal = node.attr("data-file-no");
            		that.fileRemove(getVal);
            	}
            });
			return;
		});
	},
	
	open : function(cntIdn) {
		var that = this;
		
		if(that.selector){
			that. close();
		}
		
		that.cntIdn = cntIdn;
		
		var url = CONTEXT_PATH + "/landCenter/" + that.cntIdn + "/modifyLandCenterCntrwkRegstrPage.do";
		var windowOptions = {
			width : 680,
			height : 440,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				var cntIdn = that.cntIdn; 
				
				that.init();
				that.initUi();
				that.bindEvents();
				that.getFileInfo(cntIdn);
				
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	getFileInfo : function(cntIdn){
		var tagStr = '';							
		if(cntIdn != null ){
			$.get(CONTEXT_PATH + "/landCenter/" + cntIdn + "/selectFile.do").done(function(data){
				if(data!= null && data.result!=""){
					$("#oldAtcFile").val(data.result.orignlFileNm);
					tagStr += '<a class="fileCheck" width="10px" data-file-no="' + data.result.fileNo + '">선택삭제</a>';
					$(".atcFileModifyArea").append(tagStr);
				} 
			}).fail(function(){
				$.messager.alert("파일정보", "파일 정보를 불러오는데 실패했습니다.");
			});
		}
	},
	
	modify : function(){
		var that = this;
		
		var url = CONTEXT_PATH + "/landCenter/modifyLandCenterCntrwkRegstr.do";
		var enctype = "multipart/form-data";
		var method = "post";

		var dongCode = $(".modify_sel_dong", that.selector).combobox("getValue");
		var mntn = $(".modify_sanCde", that.selector).is(":checked")?"2":"1";
		var mnnm = $(".modify_facNum", that.selector).numberbox("getValue");
		var slno = $(".modify_fadNum", that.selector).numberbox("getValue");
		var pnu = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
		$("#pnu").val(pnu);
		$('input[name=sanCde]').val(mntn);
		
		var legacyPnu = document.getElementById('landCenterCntrwkRegstrModifyformlegacyPnu').value;
		if (pnu != legacyPnu) {
			var pnuUrl = CONTEXT_PATH + "/landUseInfo/" + pnu + "/searchNullPnu.do";
			$.get(pnuUrl).done(function(result) {
				if(result.result == "") {
					var isTure = confirm('주소가 올바르지 않거나 현재는 없는 주소입니다.\n계속 진행하겠습니까?\n계속 진행할 경우 위치정보는 수정되지 않습니다.');
					if (isTure) {
						document.getElementById('landCenterCntrwkRegstrModifyformlcinfoUpdtAt').value = false;
					} else {
						return;
					}
				} else {
					document.getElementById('landCenterCntrwkRegstrModifyformlcinfoUpdtAt').value = true;
				}
				
				$("form", that.selector).attr("method", method);
				$("form", that.selector).attr("action", url);
				$("form", that.selector).attr("enctype", enctype);
				$("form", that.selector).submit();
			});
		} else {
			document.getElementById('landCenterCntrwkRegstrModifyformlcinfoUpdtAt').value = false;
			
			$("form", that.selector).attr("method", method);
			$("form", that.selector).attr("action", url);
			$("form", that.selector).attr("enctype", enctype);
			$("form", that.selector).submit();
		}
	},
	
	fileRemove : function(getVal){
		var fileNo = getVal;
		
		$.post(CONTEXT_PATH + "/landCenter/" + fileNo + "/removeAtcFile.do").done(function(rowCount){
			if(rowCount.rowCount == 1) {
				$('.atcFileModifyArea').html('');
				$("#oldAtcFile").val('');
				$.messager.alert("성공", "붙임문서가 삭제되었습니다");
			}
		});
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.ftrIdn = null;
		that.selector = null;
		that.isCreated = false;
		
	}
};

/// 토지중심공사대장 등록
landCenterCntrwkRegstrObj.add = {
		
	TITLE : "토지중심공사대장 등록",
	
	CLASS_NAME : "LandCenterCntrwkRegstrAdd",
	
	selector : null,
	
	isCreated : false,
	
	coords : null,
	
	systemCoords : null,
		
	pnu : null,
		
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					that.close();
					landCenterCntrwkRegstrObj.map.loadLdlConsPoi();
					$.messager.alert(that.TITLE, "공사대장이 등록되었습니다.");
				}
				else {
					$.messager.alert(that.TITLE, "공사대장 등록에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function(){
		var that = this;
		
		// easyUi설정
		$("#landCenterCntrwkRegstrAdd .easyui-datebox", that.selector).datebox();
		$("#landCenterCntrwkRegstrAdd .easyui-textbox", that.selector).textbox();
		$("#landCenterCntrwkRegstrAdd .easyui-combobox", that.selector).combobox();
		$("#landCenterCntrwkRegstrAdd .easyui-numberbox", that.selector).numberbox();
		
		// tab 초기화
		$(".tab-list-1 li a", that.selector).on("click", function() {
			$(".tab-list-1 li, .tab-panel-1", that.selecor).removeClass("active");
			$(this, that.selector).parent().addClass("active");
			$(".tab-panel-1", that.selector).eq($(this, that.selector).parent().index()).addClass("active");
		});
	},
	
	bindEvents : function() { 
		var that = this;
		
		// 본번
		$(".facNum", that.selector).numberbox({
			prompt : "필수 입력",
			max : 9999,
			min : 1
		});
		
		// 부번
		$(".fadNum", that.selector).numberbox({
			max : 9999,
			min : 0
		});
		
		// 저장버튼
		$(".btn_save_landCenterCntrwkRegstrAdd", that.selector).on("click", function() {
			if (landCenterCntrwkRegstrObj.inputBoxValidator($('.window_article', that.selector), that.TITLE)
					&& landCenterCntrwkRegstrObj.comboBoxValidator($('.window_article', that.selector), that.TITLE)) {
				that.save();
			}
			return false;
		});
		
		// 취소버튼
		$(".btn_close_landCenterCntrwkRegstrAdd", that.selector).on("click", function() {
			that.close();
		});
	},
	
	bindData : function() {
		var that =this;

		document.getElementById('addLandCenterCntrwkRegstrAddformLcX').value = that.systemCoords[0];
		document.getElementById('addLandCenterCntrwkRegstrAddformLcY').value = that.systemCoords[1];
		
		//pnu = 법/정읍면동10자리 + 산1자리 + 본번4자리 + 부번4자리 = 총 19자리 
		var pnu = that.pnu;
		var bjdCde = pnu.substr(0,10);
		var sanCde = pnu.substr(10,1);
		var facNum = pnu.substr(11,4).replace(/(^0+)/,"");
		var fadNum = pnu.substr(15,4).replace(/(^0+)/,"");
		
		document.getElementById('pnu').value = pnu;
		
		$(".bjdCde", that.selector).combobox("select", bjdCde);
		
		if(sanCde != null && sanCde == 2){
			$(".add_sanCde").prop("checked",true);
		} else {
			$(".add_sanCde").prop("checked",false);
		}
		$('input[name=sanCde]').val(sanCde);
		
		//본번
		$(".facNum").numberbox('setValue', facNum);
		
		//부번
		$(".fadNum").numberbox('setValue', fadNum);
	},
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}

		var url = CONTEXT_PATH + "/landCenter/addLandCenterCntrwkRegstrPage.do";
		var windowOptions = {
			width : 680,
			height : 405,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.init();
				that.initUi();
				that.bindEvents();
				if (that.coords != null && that.systemCoords != null && that.pnu != null) {
					that.bindData();
				}

				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	save : function() {
		var that = this;

		var dongCode = $(".bjdCde", that.selector).combobox("getValue");
		var mntn = $(".add_sanCde", that.selector).is(":checked")?"2":"1";
		var mnnm = $(".facNum", that.selector).numberbox("getValue");
		var slno = $(".fadNum", that.selector).numberbox("getValue");
		
		var pnu = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
		$("#pnu").val(pnu);
		$('input[name=sanCde]').val(mntn);
		
		var pnuUrl = CONTEXT_PATH + "/landUseInfo/" + pnu + "/searchNullPnu.do";
		$.get(pnuUrl).done(function(result) {
			if(result.result == "") {
				alert("올바른 주소를 입력하여 주십시오.");
			}
			else {
				var url = CONTEXT_PATH + "/landCenter/addLandCenterCntrwkRegstr.do";
				var enctype = "multipart/form-data";
				var method = "post";
				
				$("form", that.selector).attr("method", method);
				$("form", that.selector).attr("action", url);
				$("form", that.selector).attr("enctype", enctype);
				$("form", that.selector).submit();
			}
		});
		
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.coords = null;
		that.systemCoords = null;
		that.pnu = null;
		that.isCreated = false;
	},
	
	getLcXY : function() {
		var that = this;
		
		selectObj.once(that.TITLE, "Point", "drawend", function(evt) {
			that.coords = evt.feature.getGeometry().getCoordinates();
			that.systemCoords = gisObj.totransformCooridateSystem(that.coords);

			quickObj.searchPnu(evt.feature, that.CLASS_NAME, that.TITLE).done(function(pnu) {
				that.pnu = pnu;
				that.open();
			});
		}, false);
	}
};

/// 토지공사대장 퀵버튼
landCenterCntrwkRegstrObj.quick = {
		
	TITLE : "토지공사대장",
	
	CLASS_NAME : "landConsInfo",
	
	dataId : 'LDL_CONS_PS',
	
	getSearchLoc : function(pixel) {
		var that = this;
		
		var coord = mapObj.getMap().getCoordinateFromPixel(pixel);
		var systemCoord = gisObj.totransformCooridateSystem(coord);
		var feature = new ol.Feature({geometry : new ol.geom.Point(systemCoord)});
		
		that.searchLdlConsPs(feature).done(function(rows) {
			if(rows && rows.length > 0) {
				//퀴서치 일경우 퀵서치 여부, 검색결과 수 저장
				landCenterCntrwkRegstrObj.isQuickSearch = true;
				landCenterCntrwkRegstrObj.quickSearchResultCount = rows[0].ids.length;
				resultObj.addRows(rows);
			} else {
				landCenterCntrwkRegstrObj.isQuickSearch = false;
			}
			
		});
	},
	
	searchLdlConsPs : function(feature) {
		var that = this;
		var deferred = $.Deferred();
		
		// 버퍼 적용
		var buffer = mapObj.getMap().getView().getResolution() * 30;
		if(buffer < 1) {
			buffer = 1;
		}
		var geometry = feature.getGeometry();
		var bufferGeometry = spatialUtils.buffer(geometry, buffer);
		var mapProjection = mapObj.getMap().getView().getProjection();
		var sysProjection = ol.proj.get(MAP.PROJECTION);
		var transformGeometry = spatialUtils.transform(bufferGeometry, mapProjection, sysProjection);
		var format = new ol.format.WKT();
		var wkt = format.writeFeature(new ol.Feature(transformGeometry));
		
		// 필터
		var filter = {
			filterType : "SPATIAL",
			spatialType : "INTERSECTS",
			wkt : wkt
		};
		// 공간 검색
		var options ={isNoMessage : true};
		spatialSearchUtils.searchSummaries(that.TITLE, that.dataId, filter, function(data) {
			if(data && data.length && data.length > 0) {
				deferred.resolve(data);
				mainObj.defaultActive();
			}
		}, options);
		return deferred.promise();
	}
};