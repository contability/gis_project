/**
 * 도로 시설물 검색
 * @type {Object}
 */
windowObj.roadSearchFacilitiesObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "시설 검색",
	
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "RoadSearchFacilities",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	features : null,
	
	data : null,
	
	/**
	 * 레이어 선택 창 열기
	 * @param {Array<ol.Feature>} features 도형 목록
	 * @param {Object} data 6개 도로 테이블 검색 결과
	 */
	open : function(features, data) {
		var that = this;
		
		that.features = features;
		that.data = data;
		
		var url = CONTEXT_PATH + "/window/road/searchFacilities/page.do";
		var windowOptions = {
			width : 840,
			height : 505,
			modal : true,
			onClose : function() {
				that.close();
			} 	
		};
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.loadLayers();
			that.bindEvents();
		});
		that.selector = "#" + id;
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		// 레이어 검색 박스
		$(".txt_select_layer", that.selector).textbox({
			width : 200,
			prompt : "레이어명을 입력하십시오."
		});
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 검색어 엔터 키 클릭 시 검색
		$(".txt_select_layer", that.selector).textbox("textbox").keyup(function(event) {
			if (event.keyCode === 13) { 
				$(".a_select_layer", that.selector).trigger("click");
			}
		});
		
		// 검색
		$(".a_select_layer", that.selector).click(function() {
			that.show();
		});
		
		// 전체 선택
		$(".a_select_selectall", that.selector).click(function() {
			that.selectAll();
		});
		
		// 전체 취소
		$(".a_select_unselectall", that.selector).click(function() {
			that.unselectAll();
		});
		
		// 레이어 선택
		$(".ul_select_layers li input").change(function() {
			that.loadSelectedLayers();
		});
		
		// 검색
		$(".a_select_search", that.selector).click(function() {
			that.search();
		});
		
		// 취소
		$(".a_select_close", that.selector).click(function() {
			that.close();
		});
	},
	
	/**
	 * 레이어 목록 불러오기
	 */
	loadLayers : function() {
		var that = this;
		var data = layerObj.getData();
		
		// 레이어 추출
		var layers = [];
		for(var i=0, len=data.length; i < len; i++) {
			var category = data[i];
			var kwsLyrs = category.kwsLyrs;
			for(var j=0, jLen=kwsLyrs.length; j < jLen; j++) {
				var layer = kwsLyrs[j];
				if(category.lyrCtgryId == "ROAD") {
					layers.push({
						ctgryId : category.lyrCtgryId,
						id : layer.dataId,
						title : layer.lyrBassStyle.title,
						type : layer.lyrTy					
					});
				}
			}
		}
		
		// 레이어 명으로 정렬
		layers.sort(function(a, b) {
			if(a.title == b.title) {
				return 0;
			}
			else if(a.title > b.title) {
				return 1;
			}
			else {
				return -1;
			}
		});
		
		// 레이어 표시
		var tagStr = "";
		for(var i in layers) {
			var layer = layers[i];
			var id = that.CLASS_NAME + '_layer_' + layer.id;
			tagStr += '<li';
			tagStr += ' data-lyr-ctgry-id="' + layer.ctgryId + '"';
			tagStr += ' data-lyr-ty="' + layer.type + '"';
			tagStr += '>';
			tagStr += '<input id="' + id + '" type="checkbox" data-id="' + layer.id + '" value="' + layer.id + '" />';
			tagStr += '<label for="' + id + '">' + layer.title + '</label>';
			tagStr += '</li>';
		}
		$(".ul_select_layers", that.selector).html(tagStr);
	},
	
	/**
	 * 표시
	 */
	show : function() {
		var that = this;
		var searchKeyword = $(".txt_select_layer", that.selector).textbox("getValue");
		$(".ul_select_layers li", that.selector).show();
		$(".ul_select_layers li", that.selector).each(function() {
			var node = $(this);
			// 레이어명 검색어가 있을 경우 해당하지 않는 항목은 숨김
			if(searchKeyword && searchKeyword != "") {
				var layerTitle = node.find("label").text();
				if(layerTitle.indexOf(searchKeyword) == -1) {
					node.hide();
				}
			}
		});
	},
	
	/**
	 * 선택된 레이어 표시
	 */
	loadSelectedLayers : function() {
		var that = this;
		
		// 선택된 레이어 표시
		var tagStr = "";
		$(".ul_select_layers li input[type=checkbox]:checked", that.selector).each(function() {
			var element = $(this);
			var dataId = element.val();
			var layerTitle = $("label", element.parent(), that.selector).text();

			tagStr += '<li>';
			tagStr += '<span>' + layerTitle + '</span>';
			tagStr += '<a href="#" data-id="' + dataId + '" >';
			tagStr += '<img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/btn_remove.png" alt="삭제" title="삭제" />';
			tagStr += '</a>';
			
			tagStr += '</li>';
		});
		$(".ul_select_selected_layers").html(tagStr);
		
		// 삭제 이벤트 등록
		$(".ul_select_selected_layers li a", that.selector).click(function() {
			var element = $(this);
			var dataId = element.attr("data-id");
			$(".ul_select_layers li input[value="+dataId+"]", that.selector).prop("checked", false);
			that.loadSelectedLayers();
			return false;
		});
	},
	
	/**
	 * 전체선택
	 */
	selectAll : function() {
		var that = this;
		$(".ul_select_layers li:visible input[type=checkbox]", that.selector).prop("checked", true);
		that.loadSelectedLayers();
	},
	
	/**
	 * 전체해제
	 */
	unselectAll : function() {
		var that = this;
		$(".ul_select_layers li:visible input[type=checkbox]:checked", that.selector).prop("checked", false);
		that.loadSelectedLayers();
	},
	
	/**
	 * 검색
	 */
	search : function() {
		var that = this;
		var data = null;
		
		var roadLayers = [];
		
		var layers = [];
		$(".ul_select_layers li input[type=checkbox]:checked", that.selector).each(function() {
			var element = $(this);
			layers.push(element.val());
		});
		
		// 도로 관련 6개 테이블 제외
		var dataIds = roadObj.roadDataIds;
		for(var i=layers.length-1; i >= 0; i--) {
			for(var j=0, jLen=dataIds.length; j < jLen; j++) {
				if(layers[i] == dataIds[j]) {
					roadLayers.push(dataIds[j]);
					layers.splice(i, 1);
					break;
				}
			}
		}
		
		if(layers.length > 0) {
			var promises = {};
			for(var dataId in that.data) {
				promises[dataId] = $.Deferred();
				
				var fids = [];
				for(var i=0, len=that.data[dataId].length; i < len; i++) {
					fids.push(that.data[dataId][i].objectid);
				}
				
				var url = CONTEXT_PATH + "/rest/road/searchFacilities.do";
				var params = {
					dataIds : layers,
					relationDataId : dataId,
					fids : fids
				};
				$.get(url, params).done(function(response) {
					var relationDataId = response.filterRelationDTO.relationDataId;
					if(response && response.rows && response && response.rows.length > 0) {
						if(data) {
							for(var i=0, len=response.rows.length; i < len; i++) {
								var row = response.rows[i];
								if(data[row.dataId]) {
									data[row.dataId].ids = data[row.dataId].ids.concat(row.ids);
									data[row.dataId].ids = data[row.dataId].ids.filter(function(item, index, array) {
										return index==array.indexOf(item);
									});
								}
								else {
									data[row.dataId] = {
										dataId : row.dataId,
										dataAlias : row.dataAlias,
										ids : row.ids	
									};
								}
							}
						}
						else {
							data = {};
							for(var i=0, len=response.rows.length; i < len; i++) {
								var row = response.rows[i];
								data[row.dataId] = {
									dataId : row.dataId,
									dataAlias : row.dataAlias,
									ids : row.ids
								};
							}
						}
						
					}
					promises[relationDataId].resolve();
				});
			}
			
			for(var i=0, len=dataIds.length; i < len; i++) {
				if(!promises[dataIds[i]]) {
					promises[dataIds[i]] = $.Deferred();
					promises[dataIds[i]].resolve();
				}
			}
			
			$.when(promises["RDL_RSMN_AS"], promises["RDL_BRDG_AS"], promises["RDL_EVRD_AS"], promises["RDL_UGRD_AS"], promises["RDL_SBWY_AS"], promises["RDL_TRNL_AS"]).then(function() {
				if(data) {
					var rows = [];
					for(var dataId in that.data) {
						var checked = false;
						for(var i=0, len=roadLayers.length; i < len; i++) {
							if(dataId == roadLayers[i]) {
								checked = true;
								break;
							}
						}
						
						if(checked) {
							var kwsData = dataObj.getDataByDataId(dataId);
							var ids = [];
							for(var i=0, len=that.data[dataId].length; i < len; i++) {
								ids.push(that.data[dataId][i][dataObj.getPkColumn(dataId)]);
							}
							var obj = {
								dataId : kwsData.dataId,
								dataAlias : kwsData.dataAlias,
								ids : ids
							};
							rows.push(obj);
						}
					}
					
					for(var i in data) {
						rows.push(data[i]);
					}
					resultObj.addRows(rows);
					that.close();
				}
				else {
					$.messager.alert(that.TITLE, "검색 결과가 없습니다.");
				}
			});
		}
		
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		windowObj.removeWindow(that.selector);
		that.features = null;
		that.data = null;
	}
	
};