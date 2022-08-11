var ecologyNatureMapObj = {
		
	CLASS_NAME : "ECOLOGY_NATURE_MAP",
		
	PAGE : "ecologyNatureMap",
	
	selector : "#div_menu_panel_job",
	
	TITLE : "지적기반 생태자연도 조회",
	
	isViweStatisticsTab : false,
	
	
	init : function() {
		var that = this;
		that.initUi();
		
		that.searchObj.init(that);
		that.statisticsObj.init(that);
		that.open();
	},
	
	initUi : function() {
		var that = this;
		
		$(".div_menu_panel_ecology_nature_map_tabs", that.selector).tabs({
			onSelect : function(title, index) {
				if(title == "조회") {
					if (that.isViweStatisticsTab) {
						that.moveInit();
						that.statisticsLayerOff();
						that.isViweStatisticsTab = false;
					}
				}
				else if(title == "통계") {
					that.moveInit();
					mapObj.map.zoomOut();
					that.statisticsLayerOn();
					that.isViweStatisticsTab = true;
					that.search();
				}
			}
		});
	},
	
	// 소유자별 통계 연속지적 레이어 On
	statisticsLayerOn : function() {
		sldObj.showDatas([COMMON.STATS]);
		mapObj.reloadWMS();
		return false;
	},
	
	// 소유자별 통계 연속지적 레이어 Off
	statisticsLayerOff : function() {
		sldObj.removeData([COMMON.STATS]);
		mapObj.reloadWMS();
		return false;
	},

	// 지도화면 전체 영역으로 이동
	moveInit : function() {
		var map = mapObj.getMap();
		var from = ol.proj.get(MAP.PROJECTION);
		var to = map.getView().getProjection();
		var split = MAP.INIT_EXTENT.split(",");
		for(var i=0, len=split.length; i < len; i++) {
			split[i] = parseInt(split[i]);
		}
		var extent = spatialUtils.transformExtent(split, from ,to);
		mapObj.map.moveToExtent(extent);
	},
	
	// 소유자별 면적 현황 검색
	search : function() {
		var that = this;
		var url = CONTEXT_PATH + "/ecologyNatureMap/list.do";
		$.get(url).done(function(response) {
			
			$("#tbl_ecology_stats_list", that.selector).show();
			$("#tbl_ecology_stats_list", that.selector).datagrid("loadData", { total : response.rows.length, rows : response.rows });
			
		}).fail(function() {
			$.messager.alert("통계 관리", "소유자별 면적현황 통계를 가져오는데 실패했습니다.");
		});
	},
	
	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		$(that.selector).show();
		// 화면 크기 변환 시 트리 영역 크기 조절 이벤트 등록
		mainObj.addResizeHandler("jopMenu", function() {
			that.resizeWindowHandler();
		});
		that.resizeWindowHandler();
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		selectObj.clear(that.CLASS_NAME);
		$(that.selector).hide();
		mainObj.removeResizeHandler("jopMenu");
	},
	
	/**
	 * 윈도우 크기 조절 핸들러
	 */
	resizeWindowHandler : function() {
		var that = this;
		var height = $("#div_menu_panel").height();
		var titleHeight = $("> .title", that.selector).height();
		var tagsHeight = $(".tabs-header", that.selector).height();
		var conditionHeight = $(".div_search_condition", that.selector).height();
		var pagenationHeight = $(".div_search_pagination", that.selector).height();
		var offset = 120;
		
		
		var contentHeight = height - titleHeight - tagsHeight - conditionHeight - pagenationHeight - offset;
		if(contentHeight < 200) contentHeight = 200;
		$(".ul_search_content", that.selector).height(contentHeight);
	}
		
};

ecologyNatureMapObj.searchObj = {
		
	selector : ".div_ecology_nature_map",
	
	parent : null,
	
	TITLE : "조회 조건",

	format : new ol.format.WKT(),

	pnu : null,
	
	data : null,
		
	init : function(parent) {
		var that = this;
		that.parent = parent;
		that.selector = parent.selector + " " + that.selector;
		
		that.initUi();
		that.bindEvents();
		that.loadDong();
		
	},
	
	initUi : function() {
		var that = this;
		
		// 동 / 리
		$(".sel_dong", that.selector).combobox({
			required : true,
			valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD),
			width : 160
		});
		
		// 산 여부
		$(".switch_mntn", that.selector).switchbutton({
			onText : "Y",
			offText : "N",
			value : 1,
			onChange : function(checked) {
				var node = $(this);
				if(checked) {
					node.switchbutton("setValue", "2");
				}
				else {
					node.switchbutton("setValue", "1");
				}
			}
		});
		
		// 본번
		$(".txt_mnnm", that.selector).numberspinner({
			required : true,
			value : 1,
			min : 1,
			max : 9999,
			increment : 1,
			width : 160
		});
		
		// 부번
		$(".txt_slno", that.selector).numberspinner({
			required : true,
			value : 0,
			min : 0,
			max : 9999,
			increment : 1,
			width : 160
		});
		
		// 화면에서 검색
		$(".a_search_map", that.selector).linkbutton({
			iconCls : "icon-search"
		});
		
		// 검색
		$(".a_search", that.selector).linkbutton();
		
		// 토지 건물
		$(".a_kras", that.selector).linkbutton();
		
		// PDF
		$(".a_pdf", that.selector).linkbutton();
		
		// 엑셀
		$(".a_excel", that.selector).linkbutton();
		
	},
	
	/**
	 * 동 목록 불러오기
	 */
	loadDong : function() {
		var that = this;
		dongObj.getPromise().done(function() {
			var data = dongObj.getData();
			$(".sel_dong", that.selector).combobox("loadData", data);
			if(data.length > 0) {
				$(".sel_dong", that.selector).combobox("setValue", data[0][camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD)]);
			}
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 읍면동에서 엔터 시 본번으로 이동
		$(".sel_dong", that.selector).combobox("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".txt_mnnm", that.selector).numberspinner("textbox").focus();
			}
		});
		
		// 본번에서 엔터 시 부번으로 이동
		$(".txt_mnnm", that.selector).numberspinner("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".txt_slno", that.selector).numberspinner("textbox").focus();
			}
		});
		
		// 부번에서 엔터 시 검색
		$(".txt_slno", that.selector).numberspinner("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".a_search", that.selector).trigger("click");
			}
		});
		
		// 화면에서 검색
		$(".a_search_map", that.selector).click(function() {
			that.searchMap();
			return false;
		});
		
		// 검색
		$(".a_search", that.selector).click(function() {
			if(that.validator()) {
				that.search();
			}
			return false;
		});
		
		// 생태자연도 선택
		$(".ul_search_content", that.selector).on("click", ".a_content", function() {
			var element = $(this).parent();
			var dataId = element.attr("data-data-id");
			
			if(dataId == COMMON.LAND) {
				var pnu = element.attr("data-pnu");
				that.parent.attrObj.open(dataId, pnu);
			}
			else {
				var fid = element.attr("data-fid");
				that.parent.attrObj.open(dataId, fid);
			}
		});
		
		
		// 토지 건물
		$(".a_kras", that.selector).click(function() {
			if(that.pnu) {
				krasObj.open(that.pnu);
			}
			else {
				$.messager.alert(that.TITLE, "검색된 정보가 없습니다. 검색 후 사용하여 주십시오.");
			}
		});
		
		// PDF
		$(".a_pdf", that.selector).click(function() {
			if(that.pnu) {
				var filter = {
					filterType : "COMPARISON",
					comparisonType : "EQUAL_TO",
					property : "PNU",
					value : that.pnu
				};
				spatialSearchUtils.listAll(that.TITLE, "NET_PICN_DT", filter, function(rows) {
					if(rows && rows.length > 0) {
						var row = rows[0];
						if(row.shtNum) {
							var pdfWindow = window.open(CONTEXT_PATH + "/ecologyNatureMap/pdf.do?shtNum="+row.shtNum, "_blank");
							pdfWindow.focus();
						}
						else {
							$.messager.alert(that.TITLE, "PDF 파일이 없습니다.");
						}
					}
					else {
						$.messager.alert(that.TITLE, "PDF 파일이 없습니다.");
					}
				});
			}
			else {
				$.messager.alert(that.TITLE, "검색된 정보가 없습니다. 검색 후 사용하여 주십시오.");
			}
		});
		
		// 엑셀
		$(".a_excel", that.selector).click(function() {
			if(that.pnu) {
				$("#frmEcologyNatureMapExcel input[name=dataId]").val(COMMON.LAND);
				$("#frmEcologyNatureMapExcel input[name=pnu]").val(that.pnu);
				$("#frmEcologyNatureMapExcel input[name=data]").val(encodeURIComponent(JSON.stringify(that.data)));
				$("#frmEcologyNatureMapExcel").submit();
			}
			else {
				$.messager.alert(that.TITLE, "검색된 정보가 없습니다. 검색 후 사용하여 주십시오.");
			}
		});
		
	},
	
	getData : function() {
		var that = this;
		return that.data;
	},
	
	searchMap : function() {
		var that = this;
		selectObj.once(that.parent.CLASS_NAME, "Point", "drawend", function(evt) {
			// 버퍼 적용
			var buffer = mapObj.getMap().getView().getResolution() * 10;
			if(buffer < 1) {
				buffer = 1;
			}
			var geometry = evt.feature.getGeometry();
			var bufferGeometry = spatialUtils.buffer(geometry, buffer);
			
			// 좌표 변환
			var mapProjection = mapObj.getMap().getView().getProjection();
			var sysProjection = ol.proj.get(MAP.PROJECTION);
			var transformGeometry = spatialUtils.transform(bufferGeometry, mapProjection, sysProjection);
			var format = new ol.format.WKT();
			var wkt = format.writeFeature(new ol.Feature(transformGeometry));
			
			var filter = {
				filterType : "SPATIAL",
				spatialType : "INTERSECTS",
				wkt : wkt
			};
			
			spatialSearchUtils.listAll(that.parent.TITLE, COMMON.LAND, filter, function(rows) {
				if(rows && rows.length > 0) {
					var row = rows[0];
					var pnu = row.pnu;
					
					// 동 / 리
					$(".sel_dong", that.selector).combobox("setValue", pnu.substring(0, 10));
					
					// 산 여부
					if(pnu.substring(10, 11) == "2") {
						$(".switch_mntn", that.selector).switchbutton("check");
					}
					else {
						$(".switch_mntn", that.selector).switchbutton("uncheck");
					}
					
					// 본번
					$(".txt_mnnm", that.selector).numberspinner("setValue", parseInt(pnu.substring(11, 15)));
					
					// 부번
					$(".txt_slno", that.selector).numberspinner("setValue", parseInt(pnu.substring(15, 19)));
					
					that.search();
				}
				else {
					$.messager.alert(that.parent.TITLE, "검색 결과가 없습니다.");
				}
			});
		}, false);
	},
	
	validator : function() {
		var that = this;
		// 동/리
		if(!$(".sel_dong", that.selector).combobox("isValid")) {
			$.messager.alert(that.TITLE, "동/리를 선택하여 주십시오.");
			return false;
		}
		else if(!$(".txt_mnnm", that.selector).numberspinner("isValid")) {
			$.messager.alert(that.TITLE, "본번을 입력하여 주십시오.");
			return false;
		}
		else if(!$(".txt_slno", that.selector).numberspinner("isValid")) {
			$.messager.alert(that.TITLE, "부번을 입력하여 주십시오.");
			return false;
		}
		return true;
	},
	
	search : function() {
		var that = this;
		// 동 코드 
		var dongCode = $(".sel_dong", that.selector).combobox("getValue");
		if(!dongObj.checkDongCode(dongCode)) {
			$.messager.alert(that.TITLE, "법정동을 선택하여 주십시오.");
			return;
		}
		
		var mntn = $(".switch_mntn", that.selector).switchbutton("options").value;
		var mnnm = $(".txt_mnnm", that.selector).numberspinner("getValue");
		var slno = $(".txt_slno", that.selector).numberspinner("getValue");
		var pnu = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
		$.get(CONTEXT_PATH + "/ecologyNatureMap/search.do", { dataId : COMMON.LAND, pnu : pnu }).done(function(response) {
			if(response && response.rows && response.rows.length > 0) {
				that.loadData(pnu, response.rows);
			}
			else {
				$.messager.alert(that.parent.TITLE, "검색 결과가 없습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.parent.TITLE, "검색 결과가 없습니다.");
		});
	},
	
	loadData : function(pnu, dataList) {
		var that = this;
		that.pnu = pnu;
		
		var jibunNm = pnuObj.getJibunNm(pnu);
		
		var tagStr = '';
		tagStr += '<li class="li_content">';
		tagStr += '<div class="div_content_title">연속지적도</a></div>';
		tagStr += '<div class="div_content_content" data-data-id="'+COMMON.LAND+'" data-pnu="'+pnu+'" ><a href="#" class="a_content">- ' + jibunNm + '</a></div>';
		
		var landGeom = null;
		var landArea = null;
		for(var i=0, len=dataList.length; i < len; i++) {
			var data = dataList[i];
			if(data.dataId == COMMON.LAND) {
				if(data.rows && data.rows.length > 0) {
					var feature = that.format.readFeature(data.rows[0][MAP.GEOMETRY_ATTR_NAME]);
					landGeom = feature.getGeometry();
					landArea = landGeom.getArea();
					
					highlightObj.showRedFeatures(that.CLASS_NAME, [feature], true, {
						projection : ol.proj.get(MAP.PROJECTION)
					});
				}
				break;
			}
		}
		
		that.data = {
			bjdNm : pnuObj.getLocNm(pnu),
			jibunNm : pnuObj.getJibunNm(pnu),
			landArea : landArea,
			etcArea : landArea,
			grades : {},
			ratios : {}
		};
		for(var i=0, len=dataList.length; i < len; i++) {
			var data = dataList[i];
			if(data.dataId != COMMON.LAND) {
				var totalArea = 0;
				var rows = data.rows;
				for(var j=0, jLen=rows.length; j < jLen; j++) {
					var row = rows[j];
					var geom = that.format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]).getGeometry();
					var intersectionsGeom = spatialUtils.intersection(landGeom, geom);
					//intersection 결과가 포인트인경우 제외
					// 이승재, 2020.08.11, intersection 결과가 멀티라인스트링, 라인스트링인 경우 제외
					if (intersectionsGeom.getType().toUpperCase() != 'POINT' 
						&& intersectionsGeom.getType().toUpperCase() != 'MULTILINESTRING' 
							&& intersectionsGeom.getType().toUpperCase() != 'LINESTRING') {
						if(intersectionsGeom.getCoordinates) {
							row.intersectionArea = intersectionsGeom.getArea();
							totalArea += row.intersectionArea;
						}
					}
				}
				data.totalArea = totalArea;
				that.data.grades[data.dataId] = totalArea;
				
				that.data.etcArea -= totalArea;
			}
		}
		
		that.data.grades["TOTAL"] = 0;
		that.data.ratios["TOTAL"] = 0;
		for(var i=0, len=dataList.length; i < len; i++) {
			var data = dataList[i];
			if(data.dataId == COMMON.LAND) {
				continue;
			}
			
			var dataInfo = dataObj.getDataByDataId(data.dataId, true);
			tagStr += '<li class="li_content">';
			
			var title = dataInfo.dataAlias.replace("생태자연도_", "");
			var ratio = data.totalArea / landArea * 100;
			that.data.ratios[data.dataId] = ratio;
			
			that.data.grades["TOTAL"] += data.totalArea;
			that.data.ratios["TOTAL"] += ratio;
			
			tagStr += '<div class="div_content_title">' + title + ' [' + ratio.toFixed(2) + ' / 100.00%]</a></div>';
			var rows = data.rows;
			var uniqueRows = [];
			//첫째행은 유일한 행
			for(var j=0, uniqueRowAt = true, jLen=rows.length; j < jLen; j++) {
				var row = rows[j];
				
				//두번째 행부터 유일성 검토
				if (j > 0) {
					uniqueRowAt = that.isUniqueRow(uniqueRows, row, dataInfo.kwsDataFields);
				}
				
				//유일한 행이라면 출력
				if (uniqueRowAt) {
					uniqueRows.push(rows[j]);
					tagStr += '<div class="div_content_content" data-data-id="'+data.dataId+'" data-fid="'+row.objectid+'" ><a href="#" class="a_content">- ' + jibunNm + '</a></div>';
				}	
			}
			tagStr += '</li>';
		}
		
		for(var key in that.data.grades) {
			that.data.grades[key] = that.data.grades[key].toFixed(2);
		}
		for(var key in that.data.ratios) {
			that.data.ratios[key] = that.data.ratios[key].toFixed(2);
		}
		
		$(".ul_search_content", that.selector).html(tagStr);
	},
	
	/*
	 * 중복된 행이 없는 경우(유일한 행) return true
	 * 중복된 행이 있는경우 return false
	 */
	isUniqueRow : function(uniqueRows, newRow, fieldInfos) {
		var uniqueRowAt = true;
		var columnCompareResult = false;
		var columnNmae = '';
		
		for (var i = 0; i < uniqueRows.length; i++, columnCompareResult = false) {
			
			/*
			 * 값이 다른 필드가 있다면 true
			 * 값이 모두 동일하다면 false
			 */
			for (var j = 0; j < fieldInfos.length; j++) {
				var fieldInfo = fieldInfos[j]; 
				if (fieldInfo.fieldId != 'GEOM' && fieldInfo.fieldId != 'OBJECTID' && fieldInfo.fieldId != 'FTR_IDN') {
					columnNmae = camelcaseUtils.underbarToCamelcase(fieldInfo.fieldId);
					if (uniqueRows[i][columnNmae] != newRow[columnNmae]) {
						columnCompareResult = true;
						break;
					}
				}
			}
			
			if (!columnCompareResult) {
				uniqueRowAt = false;
				break;
			}
		}
		
		return uniqueRowAt;
	}
};

ecologyNatureMapObj.attrObj = {
	
	selector : null,
	
	TITLE : "상세 정보",
	
	CLASS_NAME : "EcologyNatureMapAttr",	
	
	open : function(dataId, value) {
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		//var panel = $("#div_layout").layout("panel", "center").panel();
		var width = 400;
		var height = 550;
		//var height = panel.height();
		var url = CONTEXT_PATH + "/window/ecologyNatureMap/attr/page.do";
		var windowOptions = {
			left : 350,
			top : 150,
			width : width,
			height : height,
			inline : true,
			onClose : function() {
				that.close();
			}
		};
		
		jqueryUtils.setIsLoding(false);
		$("#div_loading").show();
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			if(dataId == COMMON.LAND) {
				that.searchLandData(dataId, value);
			}
			else {
				that.searchGradeData(dataId, value);
			}
			
		});
		that.selector = "#" + id;
	},
	
	searchLandData : function(dataId, pnu) {
		var that = this;
		$.get(CONTEXT_PATH + "/ecologyNatureMap/searchJibun.do", { dataId : dataId, pnu : pnu }).done(function(response) {
			if(response && response.data) {
				that.loadData(pnu, response.data);
			}
			else {
				$.messager.alert(that.TITLE, "검색 결과가 없습니다.");
			}
			
			$("#div_loading").hide();
			jqueryUtils.setIsLoding(true);
		}).fail(function() {
			$.messager.alert(that.TITLE, "검색 결과가 없습니다.");
			
			$("#div_loading").hide();
			jqueryUtils.setIsLoding(true);
		});
	},
	
	searchGradeData : function(dataId, fid) {
		var that = this;
		
		var filter = {
			filterType : "FID",
			fid : fid
		};
		spatialSearchUtils.listAll(that.TITLE, dataId, filter, function(rows) {
			if(rows && rows.length > 0) {
				var row = rows[0];
				var dataInfo = dataObj.getDataByDataId(dataId, true);
				var group = dataInfo.dataAlias;

				var gridData = [];
				for(var i=0, len=dataInfo.kwsDataFields.length; i < len; i++) {
					var fieldInfo = dataInfo.kwsDataFields[i];
					if(fieldInfo.pkAt == 'Y' || fieldInfo.indictTy == 'HIDE' || fieldInfo.indictTy == 'WKT' || fieldInfo.fieldId == 'FTR_IDN') {
						continue;
					}
					
					gridData.push({ name : fieldInfo.fieldAlias, value : row[camelcaseUtils.underbarToCamelcase(fieldInfo.fieldId)], group : group });
				}
				$(".pg_detail", that.selector).propertygrid("loadData", gridData);
			}
			else {
				$.messager.alert(that.parent.TITLE, "검색 결과가 없습니다.");
			}
			
			$("#div_loading").hide();
			jqueryUtils.setIsLoding(true);
		});

	},
	
	initUi : function() {
		var that = this;
		$(".pg_detail", that.selector).propertygrid({
		    showGroup: true,
			columns : [[
			   { field:"name", title:"항목", width:150 },
			   { field:"value", title:"내용", width:250 }
			]],
			scrollbarSize: 0
		});
	},
	
	loadData : function(pnu, data) {
		var that = this;
		
		var gridData = [];
		if(data.shtNums && data.shtNums.length > 0) {
			gridData.push({ name : "도엽번호", value : data.shtNums.join(), group : "연속지적도" });
		}
		gridData.push({ name : "동/리", value : pnuObj.getLocNm(pnu), group : "연속지적도" });
		gridData.push({ name : "지번", value : pnuObj.getJibunNm(pnu), group : "연속지적도" });
		
		gridData.push({ name : "지목", value : data.jimokNm, group : "연속지적도" });
		gridData.push({ name : "소유자", value : data.ownerNm, group : "연속지적도" });

		var landData = ecologyNatureMapObj.searchObj.getData();
		
		gridData.push({ name : "전체면적", value : landData.landArea?landData.landArea.toFixed(2):0, group : "연속지적도" });
		
		var gradeData = landData.grades;
		
		var grade3Area =  parseFloat(gradeData["NEL_ECO3_AS"]?gradeData["NEL_ECO3_AS"]:0);
		grade3Area += parseFloat(landData.etcArea?landData.etcArea:0);
		
		gridData.push({ name : "1등급편입", value : gradeData["NEL_ECO1_AS"]?gradeData["NEL_ECO1_AS"]:0, group : "연속지적도" });
		gridData.push({ name : "2등급편입", value : gradeData["NEL_ECO2_AS"]?gradeData["NEL_ECO2_AS"]:0, group : "연속지적도" });
		//gridData.push({ name : "3등급편입", value : gradeData["NEL_ECO3_AS"]?gradeData["NEL_ECO3_AS"]:0, group : "연속지적도" });
		gridData.push({ name : "3등급편입", value : grade3Area.toFixed(2), group : "연속지적도" });
		gridData.push({ name : "별도편입", value : gradeData["NEL_ECO9_AS"]?gradeData["NEL_ECO9_AS"]:0, group : "연속지적도" });
		//gridData.push({ name : "미분류면적", value : landData.etcArea?landData.etcArea.toFixed(2):0, group : "연속지적도" });
		//gridData.push({ name : "편입면적합", value : gradeData["TOTAL"], group : "연속지적도" });
		gridData.push({ name : "편입면적합", value : landData.landArea?landData.landArea.toFixed(2):0, group : "연속지적도" });
		
		// 지형특성정보
		if(data["grdnt"]) {
			gridData.push({ name : "경사도", value : data["grdnt"], group : "지형특성정보" });
		}
		if(data["sfhht"]) {
			gridData.push({ name : "고도", value : data["sfhht"], group : "지형특성정보" });
		}
		if(data["sltNgl"]) {
			gridData.push({ name : "경사향", value : data["sltNgl"], group : "지형특성정보" });
		}
		if(data["sltDrc"]) {
			gridData.push({ name : "방향", value : data["sltDrc"], group : "지형특성정보" });
		}
		
		// 토지용도지역
		for(var i=0, len=data.usePlans.length; i < len; i += 2) {
			var usePlan = '';
			var usePlan2 = '';
			
			usePlan = data.usePlans[i].codeNm;
			if ((i + 1) < len) {
				usePlan2 = data.usePlans[i + 1].codeNm;
			} else {
				usePlan2 = '';
			}
			gridData.push({ name : usePlan, value : usePlan2, group : "토지용도지역" });
		}
		
		// 토지피복정보
		for(var i=0, len=data.useCls.length; i < len; i += 2) {
			var value = '';
			var value2 = '';
			
			value = data.useCls[i];
			if ((i + 1) < len) {
				value2 = data.useCls[i + 1];
			} else {
				value2 = '';
			}
			gridData.push({ name : value, value : value2, group : "토지피복정보" });
		}
		
		$(".pg_detail", that.selector).propertygrid("loadData", gridData);
		
	},
	
	close : function() {
		var that = this;
		windowObj.removeWindow(that.selector);
		that.selector = null;
		return false;
	}
		
};

ecologyNatureMapObj.statisticsObj = {
		
	/**
	 * 선택자
	 */
	selector : ".div_ecology_statistics_map",
	
	/**
	 * 부모 객체
	 */
	parent : null,
	
	/**
	 * 제목
	 */
	TITLE : "통계 관리",
	
	init : function(parent) {
		var that = this;
		that.selector = parent.selector + " " + that.selector;

		that.initUi();
		that.loadData();
	},
	
	initUi : function() {
		var that = this;
		
		$("#tbl_ecology_stats_list", that.selector).datagrid({
			height : 127,
			columns : [[
			            {field:"statsOwn", title:"소유자", width:75, align:"center",
			            	formatter:function(value, row, index) {
			            		if(row.statsOwn) {
			            			var code = domnCodeObj.getCode("KWS-0020", row.statsOwn);
			            			return code.codeNm;
			            		}
			            		else {
			            			return value;
			            		}
			            	}},
			            {field:"area", title:"면적(ha)", width:80, align:"center"},
			            {field:"ratio", title:"비율(%)", width:70, align:"center"},
			            ]],
            fitColumns : false,
            singleSelect : true,
            selectOnCheck : false,
            checkOnSelect : true,
            onSelect : function(index, field) {
            }
		});
	},
	
	loadData : function() {
		var that = this;
		var url = CONTEXT_PATH + "/ecologyNatureMap/select.do";
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;
				
				$("#spn_ecology_area", that.selector).text(data.area);
				$("#spn_ecology_ratio", that.selector).text(data.ratio);
			} else {
				alert("전체면적을 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("전체면적 정보를 불러오는데 실패했습니다.");
		});
	},
};