windowObj.crossSectionalSelectObj = {
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "횡단면도 분석",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "CrossSectionalSelect",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 횡단면도 데이터 (도로 목록, 지하시설물 목록)
	 */
	data : null,
	
	/**
	 * 활성화
	 */
	active : function(url) {
		var that = this;
		that.load(url).done(function(rows) {
			that.data = rows;
			that.open();
		});
	},
	
	/**
	 * 비활성화
	 */
	deactive : function() {
		var that = this;
		that.close();
	},
	
	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/analysis/crossSectionalSelect/page.do";
		var windowOptions = {
			width : 300,
			height : 300,
			top : 100,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
		});
		that.selector = "#" + id;
	},
	
	close : function() {
		var that = this;
		selectObj.clear(that.CLASS_NAME);
		windowObj.removeWindow(that.selector);
		crossSectionalResultObj.close();
		that.selector = null;
		return false;
	},
	
	initUi : function() {
		var that = this;
		
		// 지하시설물 목록
		var rows = [];
		for(var i=0, len=that.data.length; i < len; i++) {
			var dataId = that.data[i].dataId;
			var dataAlias = dataObj.getDataByDataId(dataId).dataAlias;
			that.data[i].dataAlias = dataAlias;
			if(that.data[i].dataTy == "FACILITIES") {
				rows.push({
					dataId : that.data[i].dataId,
					dataAlias : dataAlias
				});
			}
		}
		$(".grid_layers", that.selector).datagrid({
			data : {
				total : rows.length,
				rows : rows
			},
			height : 200,
			columns : [[
			    {field:"itemNo", checkbox:true, width:60},
			    {field:"dataAlias", title:"지하시설물 선택", width:200}
			]],
			fitColumns : true,
			selectOnCheck : false,
			checkOnSelect : false,
			onSelect : function(rowIndex, rowData) {
				var checked = false;
				$(".grid_layers", that.selector).datagrid("getChecked").forEach(function(row) {
					if(row.dataId == rowData.dataId) {
						checked = true;
					}
				});
				if(checked) {
					$(".grid_layers", that.selector).datagrid("uncheckRow", rowIndex); 
				}
				else {
					$(".grid_layers", that.selector).datagrid("checkRow", rowIndex); 
				}
				$(".grid_layers", that.selector).datagrid("unselectAll");
			}
		});
		$(".grid_layers", that.selector).datagrid("checkAll");
	},
	
	bindEvents : function() {
		var that = this;
		
		// 분석 위치 선택
		$(".a_select").click(function() {
			that.select();
			return false;
		});
		
		// 분석
		$(".a_analysis", that.selector).click(function() {
			that.analysis();
			return false;
		});
		
		// 닫기
		$(".a_close", that.selector).click(function() {
			that.close();
			return false;
		});
	},
	
	/**
	 * 횡단면도 분석 목록 불러오기 (도로 목록, 시설물 목록)
	 * @returns {Object} Promise 객체 
	 */
	load : function(url) {
		var that = this;
		var deferred = $.Deferred();
		$.get(url).done(function(response) {
			if(response && response.rows) {
				deferred.resolve(response.rows);
			}
			else {
				$.messager.alert(that.TITLE, "횡단면도 분석 가능한 시설물이 없습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "횡단면도를 분석할 시설물을 가져오는데 실패했습니다.");
		});
		return deferred.promise();
	},
	
	/**
	 * 
	 */
	select : function() {
		var that = this;
		selectObj.active(that.CLASS_NAME, "LineString", "drawadd", function(evt) {
			var geom = evt.feature.getGeometry();
			evt.currentTarget.finishDrawing();
			
			if(geom.getLength() < 10) {
				$.messager.alert(that.TITLE, "지하시설물횡단면은 10m 이상부터 분석 가능합니다.");
				selectObj.clearFeatures(that.CLASS_NAME);
			}
			else if(geom.getLength() > 100) {
				$.messager.alert(that.TITLE, "지하시설물횡단면은 최대 100m 까지 분석 가능합니다.");
				selectObj.clearFeatures(that.CLASS_NAME);
			}
			else {
				mainObj.defaultActive();
			}
		}, true);
	},
	
	analysis : function() {
		var that = this;
		var features = selectObj.getFeatures(that.CLASS_NAME);
		if(features.length > 0) {
			var data = [];
			var gridData = $(".grid_layers").datagrid("getChecked");
			if(gridData && gridData.length > 0) {
				for(var i=0, len=that.data.length; i < len; i++) {
					if(that.data[i].dataTy == "ROAD") {
						data.push(that.data[i]);
					}
					else if(that.data[i].dataTy == "FACILITIES") {
						for(var j=0, jLen=gridData.length; j < jLen; j++) {
							if(that.data[i].dataId == gridData[j].dataId) {
								data.push(that.data[i]);
								break;
							}
						}
					}
				}
				var geometry = features[0].getGeometry();
				crossSectionalResultObj.open(data, geometry);
			}
			else {
				$.messager.alert(that.TITLE, "선택된 지하시설물이 없습니다.");
			}
			
		}
		else {
			$.messager.confirm(that.TITLE, "분석위치가 선택되지 않았습니다. 분석위치를 선택하시겠습니까?", function(isTrue) {
				if(isTrue) {
					$(".a_select img", that.selector).trigger("click");
				}
			});
		}
	}
		
};