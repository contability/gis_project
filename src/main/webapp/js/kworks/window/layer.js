/**
 * 레이어
 * @type {Object}
 */
windowObj.layerObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "레이어 선택",
	
	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "LayerSelect",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * SEARCH, OUTPUT
	 * @type {String}
	 */
	mode : null,
	
	/**
	 * 선택 버튼 클릭 시 실행할 이벤트
	 */
	onSelect : null,
	
	/**
	 * 레이어 선택 창 열기
	 * @param {Object} options 옵션 (checkDataIds 체크할 데이터 아이디 목록, removeLayerIds : 삭제할 레이어 아이디 목록, includeTypes:포함되는 타입, onSelect:선택 이벤트)
	 */
	open : function(options) {
		var that = this;
		var url = CONTEXT_PATH + "/window/layer/page.do";
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
			
			// 레이어 창 모드
			if(options && options.mode) {
				that.mode = options.mode;
			}
			
			that.loadCategories();
			that.loadLayers();
			
			// 기존코드
			/*if(EXTENSIONS.AERIAL_PHOTO || PROJECT_CODE == 'yy') {	//양양 지적원도 타일링 개발 서버 적용 코드
				if(that.mode != "SEARCH" && that.mode != "OUTPUT") {
					that.loadBaseMapCategories();
					that.loadBaseMapLayers();
				}
			}	*/
			
			if(that.mode != "SEARCH" && that.mode != "OUTPUT") {
				that.loadBaseMapCategories();
				that.loadBaseMapLayers();
			}
			
			that.bindEvents();
			
			if(options) {
				// 데이터 아이디 목록이 매개변수로 넘어오는 경우 해당하는 레이어 체크
				if(options.checkDataIds) {
					that.checkDatas(options.checkDataIds);
				}
				// 레이어 아이디 목록이 매개변수로 넘어오는 경우 해당하는 레이어 삭제
				if(options.removeLayerIds) {
					that.removeLayers(options.removeLayerIds);
				}
				// 기본 지도 번호 목록이 매개변수로 넘어오는 경우 해당하는 기본지도 삭제
				if(options.removeMapNos) {
					that.removeBaseMaps(options.removeMapNos);
				}
				
				// 포함할 타입이 있는 경우 해당하는 타입을 제외하고는 삭제
				if(options.includeTypes) {
					that.removeExcludeTypes(options.includeTypes);
				}
				// 선택 버튼 이벤트
				if(options.onSelect) {
					that.onSelect = options.onSelect;
				}
			}
			
		});
		that.selector = "#" + id;
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		that.onSelect = null;
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;

		$(".sel_select_category", that.selector).combobox({
			width : 150,
			valueField : "lyrCtgryId",
			textField : "lyrCtgryNm",
			onChange : function() {
				that.show();
			}
		});
		
		// 레이어 검색 박스
		$(".txt_select_layer", that.selector).textbox({
			width : 200,
			prompt : "레이어명을 입력하십시오."
		});

		// 전체 항목 선택
		$(".sel_select_category", that.selector).combobox("setValue", "All");
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 분류 변경
		$(".sel_select_category", that.selector).change(function() {
			that.show();
		});
		
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
		
		// 선택
		$(".a_select_select", that.selector).click(function() {
			that.select();
		});
		
		// 취소
		$(".a_select_cancel", that.selector).click(function() {
			that.close();
		});
	},
	
	loadCategories : function() {
		var that = this;
		
		var data = layerObj.getData();
		// 대상 레이어 분류 콤보박스 생성
		var comboboxData = [{
			lyrCtgryId : "All",
			lyrCtgryNm : "전체"
		}];
		for(var i=0, len=data.length; i < len; i++) {
			//KWS_LYR_CTGRY 테이블의 사용여부에 따라 레이어 카테고리 목록 제어
			if (data[i].useAt != 'N') {
				comboboxData.push({
					lyrCtgryId : data[i].lyrCtgryId,
					lyrCtgryNm : data[i].lyrCtgryNm
				});
			}
		}

		$(".sel_select_category", that.selector).combobox("loadData", comboboxData);
				
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
			//KWS_LYR_CTGRY 테이블의 사용여부에 따라 임의 추가 가능한 레이어 제어
			if (category.useAt != 'N') {
				var kwsLyrs = category.kwsLyrs;
				for(var j=0, jLen=kwsLyrs.length; j < jLen; j++) {
					var layer = kwsLyrs[j];
					layers.push({
						ctgryId : category.lyrCtgryId,
						id : layer.dataId,
						lyrId : layer.lyrId,
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
//		for(var i in layers) {
//			var layer = layers[i];
		for(var i = 0; i <layers.length; i++) {
			var layer = layers[i];
			
			// 공간검색
			if(that.mode == "SEARCH") {
				var data = dataObj.getDataByDataId(layer.id);
				if(!data || !data.specSearchAt || data.specSearchAt != 'Y') {
					continue;
				}
			}
			// 출력
			else if(that.mode == "OUTPUT") {
				var dataAuthor = dataAuthorObj.getDataAuthor(layer.id);
				if(!dataAuthor || dataAuthor.exportAt != 'Y') {
					continue;
				}
			}
			
			var id = that.CLASS_NAME + '_layer_' + layer.lyrId;
			tagStr += '<li';
			tagStr += ' data-lyr-ctgry-id="' + layer.ctgryId + '"';
			tagStr += ' data-lyr-ty="' + layer.type + '"';
			tagStr += ' data-lyr-src="' + layer.id + '"';
			tagStr += '>';
			tagStr += '<input id="' + id + '" type="checkbox" class="chk_layer" data-id="' + layer.lyrId + '" value="' + layer.lyrId + '" />';
			tagStr += '<label for="' + id + '">' + layer.title + '</label>';
			tagStr += '</li>';
		}
		$(".ul_select_layers", that.selector).html(tagStr);
	},
	
	loadBaseMapCategories : function() {
		var that = this;
		var comboboxData = $(".sel_select_category", that.selector).combobox("getData");
		comboboxData.push({
			lyrCtgryId : "BASE_MAP",
			lyrCtgryNm : "항공사진"
		});
		$(".sel_select_category", that.selector).combobox("loadData", comboboxData);
	},
	
	loadBaseMapLayers : function() {
		var that = this;
		
		var data = backgroundMapObj.getLayers();
		
		var layers = [];
		for(var i in data) {
			var type = data[i];
			
			var baseMapAuthor = baseMapAuthorObj.getBaseMapAuthor(type.mapNo);
			if(!baseMapAuthor || baseMapAuthor.indictAt != 'Y') {
				continue;
			}
			
			layers.push({
				ctgryId : "BASE_MAP",
				id : type.mapNo,
				title : type.description,
				type : "BASE_MAP"
			});
		}
		
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
//		for(var i in layers) {
//			var layer = layers[i];
		for(var i = 0; i < layers.length; i++) {
			var layer = layers[i];
		
			var id = that.CLASS_NAME + '_layer_' + layer.id;
			tagStr += '<li';
			tagStr += ' data-lyr-ctgry-id="' + layer.ctgryId + '"';
			tagStr += ' data-lyr-ty="' + layer.type + '"';
			tagStr += '>';
			tagStr += '<input id="' + id + '" type="checkbox" class="chk_base_map" data-id="' + layer.id + '" value="' + layer.id + '" />';
			tagStr += '<label for="' + id + '">' + layer.title + '</label>';
			tagStr += '</li>';
		}
		$(".ul_select_layers", that.selector).append(tagStr);
	},
	
	/**
	 * 표시
	 */
	show : function() {
		var that = this;
		var selectCtgryId = $(".sel_select_category", that.selector).combobox("getValue");
		var searchKeyword = $(".txt_select_layer", that.selector).textbox("getValue");
		$(".ul_select_layers li", that.selector).show();
		$(".ul_select_layers li", that.selector).each(function() {
			var node = $(this);
			
			// 카테고리에 맞는 레이어를 제외하고 숨김
			var ctgryId = node.attr("data-lyr-ctgry-id");
			if(selectCtgryId != "All" && selectCtgryId != ctgryId) {
				node.hide();
			}
			
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
	 * 레이어 체크
	 * @param {Object.<String>} dataIds 데이터 아이디 목록
	 */
	checkDatas : function(dataIds) {
		var that = this;
		for(var i=0, len=dataIds.length; i < len; i++) {
			var dataId = dataIds[i];
			$(".ul_select_layers li input[data-id="+dataId+"]", that.selector).prop("checked", true);
		}
		that.loadSelectedLayers();
	},
	
	/**
	 * 레이어 삭제
	 * @param {Object.<String>} layerIds 삭제할 레이어 목록
	 */
	removeLayers : function(layerIds) {
		var that = this;
		for(var i=0, len=layerIds.length; i < len; i++) {
			var layerId = layerIds[i];
			$(".ul_select_layers li input.chk_layer[value="+layerId+"]", that.selector).parent().remove();
		}
	},
	
	removeBaseMaps : function(baseMapNos) {
		var that = this;
		for(var i=0, len=baseMapNos.length; i < len; i++) {
			var baseMapNo = baseMapNos[i];
			$(".ul_select_layers li input.chk_base_map[value="+baseMapNo+"]", that.selector).parent().remove();
		}
	},
	
	/**
	 * 지정된 타입이 아닌 경우 삭제
	 * @param {Array.<String>} includeTypes 타입 목록
	 */
	removeExcludeTypes : function(includeTypes) {
		var that = this;
		$(".ul_select_layers li", that.selector).each(function() {
			var element = $(this);
			var layerType = element.attr("data-lyr-ty");
			
			var check = false;
			for(var i=0, len=includeTypes.length; i < len; i++) {
				if(includeTypes[i] == layerType) {
					check = true;
					break;
				}
			}
			if(!check) {
				element.remove();
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
	 * 선택
	 */
	select : function() {
		var that = this;
		var layers = [];
		$(".ul_select_layers li input[type=checkbox]:checked", that.selector).each(function() {
			var element = $(this);
			var type = element.parent().attr("data-lyr-ty");
			var source = element.parent().attr("data-lyr-src");
			layers.push({
				id : element.val(),
				dataId : source,
				type : type,
				layerTitle : $("label", element.parent()).text()
			});
		});
		
		if(that.onSelect) {
			that.onSelect(layers);
		}
		
		that.clear();
		that.close();
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		that.mode = null;
		windowObj.removeWindow(that.selector);
	}
	
};