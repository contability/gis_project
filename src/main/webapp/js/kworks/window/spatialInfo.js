windowObj.spatialInfoObj = {
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "조회 대상 검색",
	
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "SpatialInfo",
	
	/**
	 * 검색 대상 데이터 아이디 목록
	 * @type {Array.<String>}
	 */
	dataIds : null,
	
	/**
	 * 열기
	 * @param options 옵션
	 */
	open : function() {
		var that = this;
		var url = CONTEXT_PATH + "/window/spatialInfo/page.do";
		
		var windowOptions = {};
		if (IS_TOPOGRAPHICMAP == 'false') {
			windowOptions = {
				width : 260,
				height : 80,
				top : 120,
				right : 50,
				onClose : function() {
					mapToolObj.deactive();
				}
			};
		}
		else {// 지형지도 활용
			windowOptions = {
				width : 260,
				height : 135,
				top : 120,
				right : 50,
				onClose : function() {
					mapToolObj.deactive();
				}
			};
		}
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
			that.handlerSldChange();
		});
		that.selector = "#" + id;
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		windowObj.removeWindow(that.selector);
		sldObj.removeHandler("spatialinfo");
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		that.clear();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 카테고리 목록
		$(".categories", that.selector).combobox({
			valueField : "id",
			textField : "name",
			onSelect : function(data) {
				that.loadLayers(data.id);
			}
		});
		
		// 레이어 목록
		$(".layers", that.selector).combobox({
			valueField : "id",
			textField : "name"
		});
		
		if (IS_TOPOGRAPHICMAP == 'true') {
			$(".topographic", that.selector).combobox({
				valueField : "id",
				textField : "name"
			});
		}
		else {
			$(".div_window_topographic_info", that.selector).hide();
		}
		
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 레이어 정보 변경 시 핸들러 함수 등록
		sldObj.addHandler("spatialinfo", function() {
			that.handlerSldChange();
		});
	},
	
	/**
	 * 카테고리 목록 표시
	 */
	loadCategories : function() {
		var that = this;
		var data = that.data;
		
		var comboboxData = [];
		for(var categoryId in data) {
			var category = data[categoryId];
			comboboxData.push({
				id : categoryId,
				name : category.name
			});
		}
		$(".categories", that.selector).combobox("loadData", comboboxData);
		if(comboboxData.length > 0) {
			$(".categories", that.selector).combobox("setValue", comboboxData[0].id);
			that.loadLayers(comboboxData[0].id);
		}
	},
	
	/**
	 * 레이어 목록 표시
	 * @param {String} categoryId 카테고리 아이디
	 */
	loadLayers : function(categoryId) {
		var that = this;
		var data = that.data;
		
		var layerData = [];
		var category = data[categoryId];
		for(var layerId in category.layers) {
			var layerName = category.layers[layerId].title;
			layerData.push({
				id : layerId,
				name : layerName
			});
		}
		$(".layers", that.selector).combobox("loadData", layerData);
		if(layerData.length > 0) {
			$(".layers", that.selector).combobox("setValue", layerData[0].id);
		}
	},
	
	/**
	 * 레이어 정보 변경 시 실행되는 함수 - 공간확인 데이터 생성
	 */
	handlerSldChange : function() {
		var that = this;
		
		var layerNames = sldObj.getLayerNames();
		that.data = {
			"All" : {
				name : "전체",
				layers : {
					"All" : {
						title : "전체"
					}
				}
			}
		};
		for(var i=0, len=layerNames.length; i < len; i++) {
			var layerName = layerNames[i];
			var layer = layerObj.getLayerByLayerName(layerName);
			if(layer) {
				var kwsData = dataObj.getDataByDataId(layer.dataId);
				if(kwsData && kwsData.specSearchAt == "Y") {
					var categoryName = layer.lyrCtgryId;
					if(!that.data[categoryName]) {
						var category = layerObj.getLayerCategoryByCategoryName(categoryName);
						that.data[categoryName] = {
							name : category.lyrCtgryNm,
							layers : {
								"All" : {
									title : "전체"
								}
							}
						};
					}
					that.data["All"].layers[layer.lyrId] = {
						dataId : layer.dataId,
						title : layer.lyrBassStyle.title
					};
					that.data[categoryName].layers[layer.lyrId] = {
						dataId : layer.dataId,
						title : layer.lyrBassStyle.title
					};
				}
			}
			else {
				$.messager.alert("공간확인", layerName + "에 대한 정보가 없습니다.");
			}
		}
		
		that.loadCategories();
		if (IS_TOPOGRAPHICMAP == 'true') {
			that.loadTopographic();
		}
	},
	
	/**
	 * 데이터 아이디 목록 검색 (Select 선택된 것에 따른 dataId 검색)
	 * @returns {Array} 데이터 아이디 목록
	 */
	getDataIds : function() {
		var that = this;
		var data = that.data;
		
		var selectCategoryId = $(".categories", that.selector).combobox("getValue");
		var selectLayerId = $(".layers", that.selector).combobox("getValue");
		
		var dataIdsObj = {};
		// 레이어 선택
		if(selectLayerId != "All") {
			for(var categoryId in data) {
				if(categoryId == selectCategoryId) {
					var category = data[categoryId];
					for(var layerId in category.layers) {
						if(layerId == selectLayerId) {
							var layer = category.layers[layerId];
							if(layer.dataId) {
								if(!dataIdsObj[layer.dataId]) {
									dataIdsObj[layer.dataId] = true;
								}
							}
							break;
						}
					}
					break;
				}
			}
		}
		else {
			// 카테고리와 레이어가 모두 전체 일때
			if(selectCategoryId == "All") {
				for(var categoryId in data) {
					var category = data[categoryId];
					for(var layerId in category.layers) {
						var layer = category.layers[layerId];
						if(layer.dataId) {
							if(!dataIdsObj[layer.dataId]) {
								dataIdsObj[layer.dataId] = true;
							}
						}
					}
				}
			}
			// 하나의 카테고리 전체
			else {
				for(var categoryId in data) {
					if(categoryId == selectCategoryId) {
						var category = data[categoryId];
						for(var layerId in category.layers) {
							var layer = category.layers[layerId];
							if(layer.dataId) {
								if(!dataIdsObj[layer.dataId]) {
									dataIdsObj[layer.dataId] = true;
								}
							}
						}
						break;
					}
					
				}
			}
		}
		
		var dataIds = [];
		for(var dataId in dataIdsObj) {
			dataIds.push(dataId);
		}
		return dataIds;
	},
	
	/**
	 * 지형지도 그룹 목록 표시
	 */
	loadTopographic : function() {
		var that = this;
	
		var groupData = $(".topographic", that.selector).combobox("getData");
		var groups = topographicObj.getGroups();
		
		for (var i = 0; i < groups.length; i++) {
			var group = {
				id : groups[i],
				name : groups[i]  
			}
			
			groupData.push(group);
		}
		
		
		$(".topographic", that.selector).combobox("loadData", groupData);
		if(groupData.length > 0) {
			$(".topographic", that.selector).combobox("setValue", groupData[0].name);
		}
	},
	
	/**
	 * 선택된 지형지도 그룹명
	 * @returns
	 */
	getTopographicId : function() {
		var that = this;
		
		var selectId = $(".topographic", that.selector).combobox("getValue");
		
		return selectId;
	}	
	
};