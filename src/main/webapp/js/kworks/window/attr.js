/**
 * 속성 창 객체 (화면에서 한 개만 유지하기 위해서 사용)
 * @type {Object}
 */
windowObj.attrObj = {
		
	/**
	 * 속성 정보 창 객체
	 */
	obj : null,
	
	/**
	 * @param {Array.<Object>} data 속성 정보
	 * @param {Object} options 속성 창 객체 생성 시 옵션 목록 
	 */
	open : function(data, options) {
		var that = this;
		if(that.obj) {
			that.obj.close(true);
		}
		that.obj = new Attr(data, options);
	},
	
	/**
	 * 닫기
	 * @param {Boolean} stopEventBubbing 이벤트 버블링 막기 여부
	 */
	close : function(stopEventBubbing) {
		var that = this;
		if(that.obj) {
			that.obj.close(stopEventBubbing);
			that.obj = null;
		}
	}
		
};

/**
 * @classdesc
 * 속성 정보 창 클래스
 * @param {Array.<Object>} data 속성 정보
 * @param {Object} options 속성 창 객체 생성 시 옵션 목록 
 */
var Attr = function(data, options) {
	var that = this;
	
	that.TITLE = "시설물 정보";
	that.CLASS_NAME = "Attr";
	that.URL = CONTEXT_PATH + "/window/attr/page.do";
	that.WINDOW_OPTIONS = {
		width : 480,
		height : 500,
		right : 50,
		onClose : function() {
			that.close();
		}
	};
	
	that.selector = null;
	that.data = data;
	$.extend(that, options);
	
	that.createWindow();
};

/**
 * 속성 정보 창 생성
 * 수정자 : 이승재, 2020.11.24
 * 수정내용
 *   - 공간확인 시 레이어 없이 지형정보만 조회 할 경우
 *   - createDataList 수행 후 createDatagrid 할 경우 에러 발생
 *   - 기존에는 createDataList에서 ajax 호출에 의한 실행 시간차가 생겨 오류가 안난 듯
 *   - 지형정보만 조회할 경우 ajax 호출이 발생하지 않아
 *   -  createDatagrid 수행 후 createDataList 수행으로 실행 순서 변경
 */
Attr.prototype.createWindow = function() {
	var that = this;
	var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, that.URL, null, that.WINDOW_OPTIONS, function() {
		that.bindEvents();
		that.createLayerOut();
		that.createDatagrid();
		that.createDataList();
	});
	that.selector = "#" + id;
};

/**
 * 이벤트 등록
 */
Attr.prototype.bindEvents = function() {
	var that = this;
	
	// 농지·산지 전용 조회
	$(".a_attr_seaoll", that.selector).click(function() {
		var element = $(this);
		var pnu = element.attr("data-pnu");
		seaollObj.open(pnu);
		return false;
	});
	
	// 토지·건물 정보 조회
	$(".a_attr_kras", that.selector).click(function() {
		var element = $(this);
		var pnu = element.attr("data-pnu");
		krasObj.open(pnu);
		return false;
	});
	
	// 결과 목록에 등록
	$(".a_attr_add_list", that.selector).click(function() {
		that.addResult();
		return false;
	});
	
	// 닫기
	$(".a_attr_close", that.selector).click(function() {
		that.close();
		return false;
	});
};

/**
 * 레이아웃 생성
 */
Attr.prototype.createLayerOut = function() {
	var that = this;
	$(".div_window_attr", that.selector).layout({
		fit : true
	});
};

/**
 * 데이터 목록 생성
 */
Attr.prototype.createDataList = function() {
	var that = this;
	var data = that.data;
	
	var tagStr = '';
	for(var i=0, len=data.length; i < len; i++) {
		var obj = data[i];
		tagStr += '<div title="' + obj.dataAlias + '" >';
		tagStr += '<ul>';
		if (obj.dataId != "TOPOGRAPHIC") {
			var ids = obj.ids;
			for(var j=0, jLen=ids.length; j < jLen; j++) {
				var fid = ids[j];
				tagStr += '<li data-data-id="'+ obj.dataId +'" data-fid="'+ fid +'" class="li_attr_data_fid" >';
				tagStr += '<img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/2depth_bullet2.png" />';
				tagStr += fid;
				tagStr += '</li>';
			}
		}
		else {
			tagStr += '<li data-data-id="'+ obj.dataId +'" data-fid="'+ i +'" class="li_attr_data_fid" >';
			tagStr += '<img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/2depth_bullet2.png" />';
			tagStr += '지형데이터';
			tagStr += '</li>';
		}
		tagStr += "</ul>";
		tagStr += "</div>";
	}
	$(".div_attr_datas", that.selector).html(tagStr);
	$(".div_attr_datas", that.selector).accordion({
		title : "선택된 시설물",
		multiple : true				//다중 선택 가능
			
	});
	
	// 항목 선택 이벤트 등록
	$(".div_attr_datas ul li", that.selector).click(function() {
		var element = $(this);
		that.selectFid(element);
	});
	
	//목록 생성 후 오브젝트 전체 선택
	if(data.length == 1){
		//첫번째 데이터 선택
		$(".div_attr_datas ul li:first", that.selector).trigger("click");
	}else if(data.length > 1){
		$(".accordion-header").trigger("click");
		$(".accordion-header:first", that.selector).trigger("click");
		$(".div_attr_datas ul li:first", that.selector).trigger("click");
	}

	
};

/**
 * 데이터 그리드 생성
 */
Attr.prototype.createDatagrid = function() {
	var that = this;
	$(".tbl_attr_detail", that.selector).datagrid({
		columns : [[
            {field:"field", title:"항목명", width:100},
		    {field:"value", title:"내 용", width:200}
		]],
		fitColumns : true,
		onSelect : function(rowIndex, rowData) {
			$(".tbl_attr_detail", that.selector).datagrid("unselectAll");
		}
	});
};

/**
 * FID 선택
 * @param {Element} element 선택된 객체
 */
Attr.prototype.selectFid = function(element) {
	var that = this;
	var params = { sysId : SYS_ID };
	
	$(".div_attr_datas ul li", that.selector).removeClass("on");
	element.addClass("on");
	
	var dataId = element.attr("data-data-id");
	var fid = element.attr("data-fid");
	
	if (dataId != "TOPOGRAPHIC") {
		var kwsData = dataObj.getDataByDataId(dataId, true);
		
		// 공간 검색
		spatialSearchUtils.selectOne(that.TITLE, dataId, fid, null, function(data) {
			if(data) {
				var dataFields = kwsData.kwsDataFields;
				
				var rows = [];
				for(var i=0, len=dataFields.length; i < len; i++) {
					var dataField = dataFields[i];
					var indictTy = dataField.indictTy;
					var sysTy = dataField.sysTy;
					
					if(PROJECT_CODE != "gn") {
						if(indictTy != "HIDE" && indictTy != "WKT") {
							var field = camelcaseUtils.underbarToCamelcase(dataField.fieldId);
							var value = data[field];
							var row = {
								field : dataField.fieldAlias,
								value : value
							};
							rows.push(row);
						}
					} else {
						if(indictTy != "HIDE" && indictTy != "WKT") {
							// 강릉시 도로대장 관리서비스(sysId == "11") 사용하는 필드 "COMMON" && "ROAD_REGISTER"
							if(params.sysId == "11") {
								if(sysTy == "COMMON" || sysTy == "ROAD_REGISTER") {
									var field = camelcaseUtils.underbarToCamelcase(dataField.fieldId);
									var value = data[field];
									var row = {
										field : dataField.fieldAlias,
										value : value
									};
									rows.push(row);
								}
							} 
							// 강릉시 도로대장 관리서비스가 아닌 경우 사용하는 필드 "COMMON" && ""
							else {
								if(sysTy == "COMMON" || sysTy == "") {
									var field = camelcaseUtils.underbarToCamelcase(dataField.fieldId);
									var value = data[field];
									var row = {
										field : dataField.fieldAlias,
										value : value
									};
									rows.push(row);
								}
							}
						}		
					}
				}
				
				var datagridData = {
					total : rows.length,
					rows : rows	
				};
				$(".tbl_attr_detail", that.selector).datagrid("loadData", datagridData);
				
				var format = new ol.format.WKT();
				var features = [format.readFeature(data[MAP.GEOMETRY_ATTR_NAME])];
				highlightObj.showOrangeFeatures(that.CLASS_NAME, features, false, {
					projection : ol.proj.get(MAP.PROJECTION)
				});
				
				$(".a_attr_add_list").show();
				that.toggleKras(dataId, data);
			}
		});
	}
	else {
		// 지형지도
		var rows = [];
		var group = that.data[fid].dataAlias;
		var data = that.data[fid].data;
		
		var row = {
			field : "좌표계",
			value : data.srs
		};
		rows.push(row);

		row = {
			field : "위치좌표",
			value : data.location
		};
		rows.push(row);
		
		var types = topographicObj.getTypes(group);
		if (types["DEM"]) {
			var type = types["DEM"];
			var value = data[type.layer.toLowerCase()];
			if (value && value != "NoData") {
				row = {
					field : type.title,
					value : value + " m"
				};
				rows.push(row);
			}
		}

		if (types["SLOPE"]) {
			var type = types["SLOPE"];
			var value = data[type.layer.toLowerCase()];
			if (value && value != "NoData") {
				row = {
					field : type.title,
					value : value + " °"
				};
				rows.push(row);
			}
		}
		
		if (types["ASPECT"]) {
			var type = types["ASPECT"];
			var value = data[type.layer.toLowerCase()];
			if (value && value != "NoData") {
				row = {
					field : type.title,
					value : value + " °"
				};
				rows.push(row);
			}
		}
	
		var datagridData = {
			total : rows.length,
			rows : rows	
		};
		$(".tbl_attr_detail", that.selector).datagrid("loadData", datagridData);
	
		highlightObj.removeOrangeFeatures(that.CLASS_NAME);
		$(".a_attr_add_list").hide();
		that.toggleKras(dataId);
	}
};

/**
 * 토지, 건물 정보 버튼 보기 / 숨김
 */
Attr.prototype.toggleKras = function(dataId, data) {
	
	var commonLand = COMMON.LAND;
	
	// 순창군은 기본 정보만 조회할 수 있는 '연속지적' 레이어와
	// 상세 정보까지 조회할 수 있는 '토지정보' 레이어로 나눠져있다.
	if(PROJECT_CODE == 'sunchang'){
		commonLand = 'LP_PA_CBND_LAND';
	}
	
	if(dataId == commonLand) {
			$(".a_attr_kras").attr("data-pnu", data.pnu);
			$(".a_attr_kras").show();
		if(CONTACT.SEAOLL_USE_AT == "true") {
			$(".a_attr_seaoll").attr("data-pnu", data.pnu);
			$(".a_attr_seaoll").show();
		}
		else {
			$(".a_attr_seaoll").hide();
		}
	}
	else if(dataId == COMMON.BULD) {
		var pnuStart = data.bdMgtSn.substring(0, 10);
		var mntYn = data.bdMgtSn.substring(11, 12);
		var pnuEnd = data.bdMgtSn.substring(11, 19);
        
        if(mntYn == "0"){
			mntYn = "1";
		}else if(mntYn = "1"){
			mntYn = "2" ;
		}
		
		$(".a_attr_kras").attr("data-pnu", pnuStart+mntYn+pnuEnd);
		$(".a_attr_kras").show();
		$(".a_attr_seaoll").hide();
	}
	else {
		$(".a_attr_kras").hide();
		$(".a_attr_seaoll").hide();
	}
};

/**
 * 목록 추가
 */
Attr.prototype.addResult = function() {
	var that = this;

	// 지형지도 활용 여부
	// 목록 추가 시 지형지도는 삭제
	// 이승재, 2020.07.28
	if (IS_TOPOGRAPHICMAP == 'true') {
		var index = -1;
		var rows = that.data;
		for (var i = 0, len = that.data.length; i < len; i++) {
			if (rows[i].dataId == 'TOPOGRAPHIC') {
				index = i;
				break;
			}
		}
		if (index > -1) {
			that.data.splice(index, 1);
		}
	}
	
	resultObj.addRows(that.data);
	that.close();
};

/**
 * 닫기
 * @param {Boolean} stopEventBubbing 이벤트 버블링 막기 여부
 */
Attr.prototype.close = function(stopEventBubbing) {
	var that = this;
	windowObj.removeWindow(that.selector);
	highlightObj.removeFeatures(that.CLASS_NAME);
	if(!stopEventBubbing && that.onClose) {
		that.onClose();
	};
};