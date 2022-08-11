var crossSectionalResultObj = {
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "횡단면도 분석 결과",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "CrossSectionalResult",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_cross_sectional_result",
	
	/**
	 * 횡단면도 데이터 (도로 목록, 지하시설물 목록)
	 */
	data : null,
	
	/**
	 * 생성 여부 (Singleton)
	 */
	isCreated : false,
	
	create : function() {
		var that = this;
		that.initUi();
		that.bindEvents();
		that.isCreated = true;
	},
	
	initUi : function() {
		var that = this;
		
		// 지하시설물 속성 조회
		$(".grid_attribute", that.selector).propertygrid({
			height : 290,
			columns : [[
	            {field:"field", title:"항목명", width:100},
			    {field:"value", title:"내 용", width:200}
			]],
			fitColumns : true,
			onSelect : function(rowIndex, rowData) {
				$(".grid_attribute", that.selector).propertygrid("unselectAll");
			}
		});
		
		// 횡단면도 표시 영역
		$(".div_layout", that.selector).layout("panel", "center").panel({
			// 영역 크기 변경 시 횡단면도 다시 그림
			onResize : function() {
				crossSectionalViewObj.resize($(".div_cross", that.selector).width());
			}
		});
		
		that.setWindowPosition();
	},
	
	setWindowPosition : function() {
		var that = this;
		
		var windowWidth = $(window).width();
		var windowHeight = $(window).height();
		var navWidth = $("#div_menu").width();
		var width = windowWidth - navWidth;
		
		var left = navWidth;
		if($("#div_menu_panel").is(":visible")) {
			var panelWidth = $("#div_menu_panel").width();
			left += panelWidth;
			width = width - panelWidth;
		}
		
		$(that.selector).window("resize", {
			width : width,
			left : left,
			top : windowHeight - $(that.selector).height()
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 이미지 내보내기
		$(".a_export_image", that.selector).click(function() {
			 that.exportImage();
		});
		
		// 엑셀 내보내기
		$(".a_export_excel", that.selector).click(function() {
			that.exportExcel();
		});
		
	},
	
	/**
	 * 열기
	 */
	open : function(data, geometry) {
		var that = this;
		if(!that.isCreated) {
			that.create();
		}
		
		that.load(data, geometry).done(function(features) {
			$(that.selector).window("open");
			
			// 화면 크기 변환 시 결과 목록 영역 크기 조절 이벤트 등록
			/*mainObj.addResizeHandler("cross_sectional_result", function(width, height) {
				that.resizeWindowHandler(width, height);
			});
			mainObj.resizeWindowHandler($(window).width(), $(window).height());
			*/
			
			//2021.12.27 
			//외부지도 사용시 표시안됌 현상 수정
			//조원기
			var transGeom = gisObj.toTransformSystem(geometry); 
			
			crossSectionalViewObj.init("#svg_cross", data, transGeom, features, {
				clickFac : function(fac) {
					that.showAttribute(fac);
				}
			});
			
			var width = $(".div_cross", that.selector).width();
			crossSectionalViewObj.resize(width);
			
			var height = crossSectionalViewObj.getHeight();
			$(".div_cross", that.selector).height(height);
			
			var height = crossSectionalViewObj.getHeight() + 120;
			//var top = $("#map_container").height() - height;
			var top = $(window).height() - height;
			$(that.selector).window("resize", {
				height : height
			});
			$(that.selector).window("move", {
				top : top
			});
			$(".grid_attribute", that.selector).propertygrid("resize", {
				height : height - 80
			});
		});
		
	},
	
	load : function(data, geometry) {
		var that = this;
		var deferred = $.Deferred();
		
		var dataIds = [];
		for(var i=0, len=data.length; i < len; i++) {
			dataIds.push(data[i].dataId);
		}
		
		var format = new ol.format.WKT();
		var wkt = format.writeFeature(new ol.Feature(gisObj.toTransformSystem(geometry)));
		var filter = {
			filterType : "SPATIAL",
			spatialType : "INTERSECTS",
			wkt : wkt	
		};
		spatialSearchUtils.search(that.TITLE, dataIds, filter, function(result) {
			var features = [];
			for(var i=0, len=result.length; i < len; i++) {
				var tableName = result[i].dataId;
				var rows = result[i].rows;
				for(var j=0, jLen=rows.length; j < jLen; j++) {
					var row = rows[j];
					var feature = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);
					var properties = {};
					for(var prop in row) {
						if(prop != MAP.GEOMETRY_ATTR_NAME) {
							properties[camelcaseUtils.camelcaseToUnderbar(prop)] = row[prop];
						}
					}
					feature.setProperties(properties);
					feature.tableName = tableName;
					features.push(feature);
				}
			}
			deferred.resolve(features);
		});
		
		return deferred.promise();
	},
	
	/**
	 * 윈도우 창 크기 변경 시 크기 조절
	 */
	resizeWindowHandler : function(windowWidth, windowHeight) {
		var that= this;
		
		var navWidth = $("#div_menu").width();
		var width = windowWidth - navWidth;
		
		var left = 0;
		if($("#div_menu_panel").is(":visible")) {
			left = $("#div_menu_panel").width();
			width = width - left;
		}
		
		$(that.selector).window("resize", {
			width : width,
			left : left,
			top : windowHeight - $(that.selector).window("panel").height() - 120
		});
	},
	
	showAttribute : function(fac) {
		var that = this;
		
		var tableName = fac.tableName;
		var properties = fac.properties;
		
		var data = dataObj.getDataByDataId(tableName, true);
		$(".div_attr", that.selector).panel("setTitle", data.dataAlias);
		
		var dataFields =  data.kwsDataFields;
		var rows = [];
		for(var i=0, len=dataFields.length; i < len; i++) {
			var dataField = dataFields[i];
			var indictTy = dataField.indictTy;
			if(indictTy != "HIDE" && indictTy != "WKT") {
				var value = properties[dataField.fieldId];
				rows.push({
					field : dataField.fieldAlias,
					value : value
				});
			}
		}
		var data = {
			total : rows.length,
			rows : rows
		};
		$(".grid_attribute", that.selector).propertygrid("loadData", data);
		
		var feature = new ol.Feature(fac.properties.geometry.clone());
		highlightObj.showRedFeatures(that.CLASS_NAME, [feature], false, {
			projection : ol.proj.get(MAP.PROJECTION)
		});
	},
	
	/**
	 * 엑셀 내보내기
	 */
	exportExcel : function() {
		var tableList = {};
		var data = crossSectionalViewObj.getCrossData();
		for(var i=0, len=data.facs.length; i < len; i++) {
			var fac = data.facs[i];
			var tableName = fac.tableName;
			
			var kwsData = dataObj.getDataByDataId(tableName, true);
			var dataFields =  kwsData.kwsDataFields;
			if(!tableList[tableName]) {
				tableList[tableName] = {
					name : 	tableName,
					title : kwsData.dataAlias,
					columns : [],
					rows : []
				};
				
				var row = {};
				for(var j=0, jLen=dataFields.length; j < jLen; j++) {
					var dataField = dataFields[j];
					var indictTy = dataField.indictTy;
					if(indictTy != "HIDE" && indictTy != "WKT") {
						tableList[tableName].columns.push({
							field : dataField.fieldId,
							title : dataField.fieldAlias
						});
					}
					row[dataField.fieldId] = fac.properties[dataField.fieldId];
				}
				tableList[tableName].rows.push(row);
			}
		}
		
		var tables = [];
		for(var i in tableList) {
			tables.push(tableList[i]);
		}
		var excelData = { name : "CrossSection", tables : tables };
		$("#frmExcelFile input[name=data]").val(encodeURIComponent(JSON.stringify(excelData)));
		$("#frmExcelFile").submit();
	},
	
	/**
	 * 횡단면도 이미지 내보내기
	 */
	exportImage : function() {
		var that = this;
		
		var width = $("#svg_cross").width();
		var height = $("#svg_cross").height();
		var canvas = $("<canvas>").attr("width", width).attr("height", height)[0];
		var ctx = canvas.getContext("2d");
		ctx.drawSvg($(".div_cross", that.selector).html().replace(/\\/g, ""), 0, 0, width, height);
		var data = canvas.toDataURL();
		$("#frmDownloadBase64Image input[name=data]").val(data);
		$("#frmDownloadBase64Image").submit();
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		if(that.isCreated) {
			$(".grid_attribute", that.selector).propertygrid("loadData", { total : 0, rows : []});
			// 화면 크기 변환 시 결과 목록 영역 크기 조절 이벤트 해제
			mainObj.removeResizeHandler("cross_sectional_result");
			
			highlightObj.removeRedFeatures(that.CLASS_NAME);
		}
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		$(that.selector).window("close");
		return false;
	}
};