/**
 * 추가 선택 객체
 * @type {Object}
 */
windowObj.resultRelationSearchObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "연관 검색",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "ResultRelationSearch",
	
	/**
	 * 검색 대상 데이터 아이디 목록
	 * @type {Array.<String>}
	 */
	dataIds : null,
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 데이터 (KWS_DATA)
	 * @type {Object}
	 */
	data : null,

	/**
	 * 아이디 목록
	 * @type {Array.<Long>}
	 */
	ids : null,
	
	/**
	 * 선택완료 시 실행될 함수
	 */
	onSubmit : null,
	
	/**
	 * 열기
	 * @param {Object} data 데이터
	 * @param {Object} options 옵션
	 */
	open : function(data, ids, options) {
		var that = this;
		
		var url = CONTEXT_PATH + "/window/result/relationSearch/page.do";
		var windowOptions = {
			width : 360,
			height : 270,
			top : 120,
			modal : true,
			onClose : function() {
				that.close();
			}
		};
		
		that.data = data;
		that.ids = ids;
		var title = that.TITLE + " [" + data.dataAlias + "]";
		var id = windowObj.createWindow(that.CLASS_NAME, title, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
			// 현재 객체에 전달되는 값 적용 (onSubmit)
			if(options) {
				$.extend(that, options);
			}
		});
		that.selector = "#" + id;
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		that.data = null;
		that.ids = null;
		that.dataIds = null;
		windowObj.removeWindow(that.selector);
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		that.clear();
		that.selector = null;
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 버퍼 거리
		$(".txt_spatial_buffer_distance", that.selector).numberspinner({
			min : 0,
			max : 300,
			increment : 10
		});
		
		// arcgis buffer hidden
		if(MAP.SERVER_TYPE == "arcgis") {
			$(".relationSearchBufferDistance", that.selector).hide();
		}
		
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 대상 레이어 선택
		$(".a_spatial_select_target_layers", that.selector).click(function() {
			windowObj.layerObj.open({
				mode : "SEARCH",
				checkDataIds : that.dataIds,
				onSelect : function(layers) {
					var tagStr = "";
					that.dataIds = [];
					for(var i in layers) {
						that.dataIds.push(layers[i].dataId);
						tagStr += '<li>';
						tagStr += layers[i].layerTitle;
						tagStr += '</li>';
					}
					$(".ul_target_layers", that.selector).html(tagStr);
				}
			});
			return false;
		});
		
		// 검색
		$(".a_spatial_search", that.selector).click(function() {
			that.search();
			return false;
		});
		
		// 닫기
		$(".a_spatial_close", that.selector).click(function() {
			that.close();
			return false;
		});
		
	},
	
	/**
	 * 검색
	 */
	search : function() {
		var that = this;

		var dataIds = that.dataIds;
		if(!dataIds || dataIds.length <= 0) {
			$.messager.alert(that.TITLE, "대상레이어를 선택하여 주십시오.");			
			return;
		}
		
		// 공간 필터 적용
		var spatialType = $("input[name=rad_result_relation_search_spatial_type]:checked").val();
		var filter = {
			filterType : "RELATION",
			spatialType : spatialType,
			relationDataId : that.data.dataId,
			fids : that.ids
		};
		
		var buffer = $(".txt_spatial_buffer_distance", that.selector).numberspinner("getValue");
		if(buffer > 0) {
			filter["relationBuffer"] = buffer;
		}
		
		spatialSearchUtils.searchSummaries(that.TITLE, dataIds, filter, function(rows) {
			if(rows && rows.length > 0) {
				if(that.onSubmit) {
					that.onSubmit(rows);
				}
			}
			that.close();
		});
	}
	
};