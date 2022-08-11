/**
 * 측량기준점 검색
 */
windowObj.surveyBasePointObj = {

	TITLE : "측량기준점 통합검색",
	
	CLASS_NAME : "ControlPointSearch",
	
	selector : null,
	
	/**
	 * 데이터 목록
	 */
	datas : null,


	open : function(categoryIds) {
		var that = this;
		
		var datas = [];
		for(var i=0, len=categoryIds.length; i < len; i++) {
			var category = layerObj.getLayerByCategoryId(categoryIds[i]);
			for(var j=0, jLen=category.kwsLyrs.length; j < jLen; j++) {
				var kwsLyr = category.kwsLyrs[j];
				var data = dataObj.getDataByDataId(kwsLyr.dataId);
				if(data) {
					datas.push(data);
				}
			}
		}
		datas = datas.sort(function(a, b) {
			return a.dataId > b.dataId ? 1 : -1;
		});
		
		if(datas.length <= 0) {
			$.messager.alert(that.TITLE, "조건 검색 가능한 대상이 없습니다.");
			return;
		}

		that.datas = datas;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		var windowOptions = {
			width : 610,
			height : 179,
			top : 120,
			left : 330,
			onClose : function() {
				that.close();
			}
		};
		
		var url = CONTEXT_PATH + "/ctrlpnt/totalSearchPage.do";
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
		});
		
		that.selector = "#" + id;
	},
	
	initUi : function() {
		var that = this;
		
		$(".easyui-textbox", that.selector).textbox();
		$(".easyui-datebox", that.selector).datebox();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 초기화
		$("#searchControlPoint .a_reset", that.selector).on("click", function() {
			$(".txt_bse_nam").textbox("clear");
			$(".txt_str_ymd").datebox("clear");
			$(".txt_end_ymd").datebox("clear");
			$(".txt_set_loc").textbox("clear");
		});
		
		// 검색
		$("#searchControlPoint .a_search", that.selector).on("click", function() {
			that.search();
			return false;
		});
	},
	
	search : function() {
		var that = this;
		
		var dataIds = [];
		for(var i=0, len=that.datas.length; i < len; i++) {
			var id = that.datas[i].dataId;
			dataIds.push(id);
		}
		
		var params = {};
		params.dataIds = dataIds;
		params.bseNam = $(".txt_bse_nam", that.selector).textbox("getValue");
		params.setYmd = $(".txt_str_ymd", that.selector).datebox("getValue");
		params.setYmd = $(".txt_end_ymd", that.selector).datebox("getValue");
		params.setLoc = $(".txt_set_loc", that.selector).textbox("getValue");
		
		var url = CONTEXT_PATH + "/ctrlpnt/searchSummaries.do";
		$.post(url, params).done(function(response) {
			
			if (response && response.datas) {
				if (response.datas.length >= 1) {
					resultObj.addRows(response.datas);
				}
				else {
					alert("조건에 맞는 결과가 없습니다.");
					return false;
				}
			}
			else {
				alert("조건에 맞는 결과가 없습니다.");
				return false;
			}
			
		}).fail(function() {
			alert("기준점 리스트를 가져오는데 실패하였습니다.");
		});
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.datas = null;
	}
};

/**
 * 기준점 조회 
 */
windowObj.surveyBasePointObj.view = {

	TITLE : "기준점 조회",
	
	CLASS_NAME : "ControlPointView",
	
	selector : null,

	dataId : null,

	ftrCde : null,

	ftrIdn : null,
	
	
	open : function(dataId, ftrIdn) {
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		that.dataId = dataId;
		that.ftrIdn = ftrIdn;
		
		var windowOptions = {
			width : 680,
			height : 499,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var url = CONTEXT_PATH + "/ctrlpnt/" + dataId +  "/" + ftrIdn +  "/viewRegiser.do";
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
			that.loadPassPoint();
			that.loadPhoto();
		});
		
		that.selector = "#" + id;
	},

	initUi : function(){
		var that = this;
		
		that.ftrCde = $("input[name=ftrCde]", that.selector).val();

		// tab 초기화
		$(".tab-list-1 li a", that.selector).on("click", function() {
			$(".tab-list-1 li, .tab-panel-1", that.selecor).removeClass("active");
			$(this, that.selector).parent().addClass("active");
			$(".tab-panel-1", that.selector).eq($(this, that.selector).parent().index()).addClass("active");
		});
		
	},	
	
	bindEvents : function() {
		var that = this;

		// 대장출력
		$("#btn_print", that.selector).on("click", function() {
			that.print();
			return false;
		});

		// 편집버튼
		$("#btn_edit", that.selector).on("click", function() {
			that.modify();
			return false;
		});
		
		// 삭제버튼
		$("#btn_delete", that.selector).on("click", function() {
			that.remove();
			return false;
		});
		
		// 시준점 등록버튼
		$("#btn_pass_add", that.selector).on("click", function() {
			that.addPassPoint();
			return false;
		});

		// 사진 등록버튼
		$("#btn_image_add", that.selector).on("click", function() {
			that.addPhoto();
			return false;
		});
		
		// 닫기버튼
		$("#btn_close", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	loadPassPoint : function() {
		var that = this;
		
		var url = CONTEXT_PATH + "/ctrlpnt/" + that.ftrCde + "/" + that.ftrIdn + "/listPassPoint.do";
		$.get(url).done(function(response){
			var trTag = '<tr><th width="10%">순번</th><th width="30%">시준점</th><th width="30%">방위각</th><th width="30%">거리</th></tr>';
			var rows = response.rows;
			
			for(var i=0; i<rows.length;i++){
				var row = rows[i];
				trTag += '<tr data-no="' + row.ecpNo + '" class="selectableRow">';
				trTag += '<td>' + (i+1) +'</td>';
				trTag += '<td>' + row.ecpNam + '</td>';
				trTag += '<td>' + row.ecpDeg + '</td>';
				trTag += '<td>' + row.ecpLen + '</td>';
				trTag += '</tr>';
			}
			$('.tbodyPassPoint', that.selector).html(trTag);
			
			// 클릭 이벤트 설정
			$(".tbodyPassPoint tr", that.selector).click(function() {
				var element = $(this);
				var no = element.attr("data-no");
				
				if(no && no.length > 0) {
					$(".tbodyPassPoint tr", that.selecor).removeClass("tr_select");
					$(this, that.selector).addClass("tr_select");
					
					windowObj.surveyBasePointObj.passPointModify.open(that, that.dataId, no);
				}
			});
		});
	},

	loadPhoto : function() {
		var that = this;
		
		var url = CONTEXT_PATH + "/ctrlpnt/" + that.ftrCde + "/" + that.ftrIdn + "/listPhoto.do";
		$.get(url).done(function(response){
			var trTag = '<tr><th width="8%">순번</th><th>사진</th></tr>';
			var rows = response.rows;
			
			for(var i=0; i<rows.length;i++){
				var row = rows[i];
				trTag += '<tr data-no="' + row.imageNo + '" class="selectableRow">';
				trTag += '<td>' + (i+1) +'</td>';
				trTag += '<td>' + row.imageCn + '</td>';
				trTag += '</tr>';
			}
			$('.tbodyImage', that.selector).html(trTag);
			
			// 클릭 이벤트 설정
			$(".tbodyImage tr", that.selector).click(function() {
				var element = $(this);
				var no = element.attr("data-no");
				
				if(no && no.length > 0) {
					$(".tbodyImage tr", that.selecor).removeClass("tr_select");
					$(this, that.selector).addClass("tr_select");
					
					windowObj.surveyBasePointObj.photoModify.open(that, no);
				}
			});
		});
	},

	/**
	 * 대장출력
	 */
	print : function() {
		var that = this;
		var dataId = that.dataId;
		var ftrIdn = that.ftrIdn;
		var ftrCde = that.ftrCde;		
		
		var url = CONTEXT_PATH + "/clipreport/ctrlPnt.do?dataId=" + dataId + "&ftrIdn=" + ftrIdn + "&ftrCde=" + ftrCde;
		
        popupUtils.open(url, "기준점", { width : 1300 , height : 800, left : 300, top:150 });
	},
	
	/**
	 * 변경
	 */
	modify : function() {
		var that = this;
		
		windowObj.surveyBasePointObj.modify.open(that.dataId, that.ftrIdn);
		that.close();
	},
	
	/**
	 * 삭제
	 */
	remove : function() {
		var that = this;
		
		$.messager.confirm(that.TITLE, "기준점 정보를 삭제하시겠습니까?", function(isTrue) {
			if(isTrue) {
				var url = CONTEXT_PATH + "/ctrlpnt/" + that.ftrCde + "/" + that.ftrIdn + "/delete.do";
				
				$.get(url).done(function(result) {
					if(result.type == 'del' && result.state >= 1) {
						$.messager.alert({
							title : that.TITLE,
							msg : "기준점 정보를 삭제하였습니다.",
							fn : function() {
								that.close();
							}
						});
					}
					else {
						$.messager.alert(that.TITLE, "기준점 삭제에 실패했습니다.");
					}
				}).fail(function() {
					$.messager.alert(that.TITLE, "기준점 삭제에 실패했습니다.");
				});
			}
		});
	},
	
	addPassPoint : function() {
		var that = this;
		
		windowObj.surveyBasePointObj.passPointAdd.open(that, that.dataId, that.ftrIdn);
	},

	addPhoto : function() {
		var that = this;
		
		windowObj.surveyBasePointObj.photoAdd.open(that, that.dataId, that.ftrIdn);
	},
	
	/**
	 * 닫기
	 */
	close:function(){
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.dataId = null;
		that.ftrCde = null;
		that.ftrIdn = null;
		that.selector = null;
	}
};

/**
 * 기준점 등록 
 */
windowObj.surveyBasePointObj.add = {

	TITLE : "기준점 등록",
	
	CLASS_NAME : "ControlPointAdd",
	
	selector : null,

	
	open : function(dataId) {
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		var windowOptions = {
			width : 680,
			height : 489,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var url = CONTEXT_PATH + "/ctrlpnt/" + dataId +  "/addRegiser.do";
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.init();
			that.initUi();
			that.bindEvents();
		});
		
		that.selector = "#" + id;
	},

	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result.state >= 1) {
					that.close();
					$.messager.alert(that.TITLE, "기준점이 등록되었습니다.");
				}
				else {
					$.messager.alert(that.TITLE, "기준점 등록에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function(){
		var that = this;
		
		$(".easyui-datebox", that.selector).datebox();
		$(".easyui-combobox", that.selector).combobox();
		$(".easyui-textbox", that.selector).textbox();
		$(".easyui-numberbox", that.selector).numberbox({
			min : 0,
			precision : 3
		});
	},
	
	bindEvents : function() { 
		var that = this;

		// 저장버튼
		$("#btn_insert", that.selector).on("click", function() {
			that.save();
			return false;
		});
		
		// 취소버튼
		$("#btn_close", that.selector).on("click", function() {
			that.close();
		});
	},

	getDefaultSpatialValue : function(spatialDataobj, coord) {
		var value = null;
		
		var from = mapObj.getMap().getView().getProjection();
		var to = ol.proj.get(MAP.PROJECTION);
		var transCoord = spatialUtils.transformCooridate(coord, from , to);
		
		var format = new ol.format.WKT();
		var data = spatialDataobj.getData();
		for(var i=0, len=data.length; i < len; i++) {
			var features = [format.readFeature(data[i][MAP.GEOMETRY_ATTR_NAME])];
			if(features && features.length > 0) {
				var feature = features[0];
				if(feature.getGeometry().containsXY(transCoord[0], transCoord[1])) {
					value = data[i][camelcaseUtils.underbarToCamelcase(spatialDataobj.codeField)];
					break;
				}
			}
		}
		
		return value;
	},
	
	save : function() {
		var that = this;

		// Lks : 측량기준점의 위치좌표는 좌표축의 표기가 반대인것이 맞음.
		var x = $(".nggX", that.selector).textbox("getValue");
		var y = $(".nggY", that.selector).textbox("getValue");
		var wkt = "POINT(" + y + " " + x + ")";
		$("input[name=wkt]", that.selector).val(wkt);
		var position = [y, x];
	
		var bjdCde = that.getDefaultSpatialValue(dongObj, position);
		$("input[name=bjdCde]", that.selector).val(bjdCde);

		var hjdCde = that.getDefaultSpatialValue(hDongObj, position);
		$("input[name=hjdCde]", that.selector).val(hjdCde);

		var bseHgt = $("input[name=bseHgt]", that.selector).val();
		if (!bseHgt) {
			$("input[name=bseHgt]", that.selector).val("0");
		}
		
		var url = CONTEXT_PATH + "/ctrlpnt/add.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		if (that.selector) {
			windowObj.removeWindow(that.selector);
		}
		that.selector = null;
	}
};

/**
 * 편집
 */
windowObj.surveyBasePointObj.modify = {

	TITLE : "기준점 편집",
	
	CLASS_NAME : "ControlPointModify",
	
	selector : null,

	/**
	 * 데이터아이디
	 */
	dataId : null,

	/**
	 * 관리번호
	 */
	ftrIdn : null,
	
	
	open : function(dataId, ftrIdn) {
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		that.parent = parent;
		that.dataId = dataId;
		that.ftrIdn = ftrIdn;
		
		var windowOptions = {
			width : 680,
			height : 489,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var url = CONTEXT_PATH + "/ctrlpnt/" + dataId +  "/" + ftrIdn +  "/modifyRegiser.do";
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.init();
			that.initUi();
			that.bindEvents();
		});
		
		that.selector = "#" + id;
	},

	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result.state >= 1) {
					that.close();
					$.messager.alert(that.TITLE, "기준점이 변경되었습니다.");
				}
				else {
					$.messager.alert(that.TITLE, "기준점 변경에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function(){
		var that = this;
		
		$(".easyui-datebox", that.selector).datebox();
		$(".easyui-combobox", that.selector).combobox();
		$(".easyui-textbox", that.selector).textbox();
		$(".easyui-numberbox", that.selector).numberbox({
			min : 0,
			precision : 3
		});
	},
	
	bindEvents : function() {
		var that = this;

		// 저장버튼
		$("#btn_update", that.selector).on("click", function() {
			that.modify();
			return false;
		});

		// 닫기버튼
		$("#btn_close", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	getDefaultSpatialValue : function(spatialDataobj, coord) {
		var value = null;
		
		var from = mapObj.getMap().getView().getProjection();
		var to = ol.proj.get(MAP.PROJECTION);
		var transCoord = spatialUtils.transformCooridate(coord, from , to);
		
		var format = new ol.format.WKT();
		var data = spatialDataobj.getData();
		for(var i=0, len=data.length; i < len; i++) {
			var features = [format.readFeature(data[i][MAP.GEOMETRY_ATTR_NAME])];
			if(features && features.length > 0) {
				var feature = features[0];
				if(feature.getGeometry().containsXY(transCoord[0], transCoord[1])) {
					value = data[i][camelcaseUtils.underbarToCamelcase(spatialDataobj.codeField)];
					break;
				}
			}
		}
		
		return value;
	},
	
	modify : function() {
		var that = this;

		// Lks : 측량기준점의 위치좌표는 좌표축의 표기가 반대인것이 맞음.
		var x = $(".nggX", that.selector).textbox("getValue");
		var y = $(".nggY", that.selector).textbox("getValue");
		var wkt = "POINT(" + y + " " + x + ")";
		$("input[name=wkt]", that.selector).val(wkt);
		var position = [y, x];
	
		var bjdCde = that.getDefaultSpatialValue(dongObj, position);
		$("input[name=bjdCde]", that.selector).val(bjdCde);

		var hjdCde = that.getDefaultSpatialValue(hDongObj, position);
		$("input[name=hjdCde]", that.selector).val(hjdCde);

		var bseHgt = $("input[name=bseHgt]", that.selector).val();
		if (!bseHgt) {
			$("input[name=bseHgt]", that.selector).val("0");
		}
		
		var url = CONTEXT_PATH + "/ctrlpnt/" + that.ftrIdn + "/update.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		windowObj.surveyBasePointObj.view.open(that.dataId, that.ftrIdn);
		
		that.dataId = null;
		that.ftrIdn = null;
		that.isDelete = false;
		that.selector = null;
	}
};


///////////////////////////////////////////////////////////////////////////////////////////
// 시준점
//
/**
 * 시준점 등록 
 */
windowObj.surveyBasePointObj.passPointAdd = {

	TITLE : "시준점 등록",
	
	CLASS_NAME : "AddPassPoint",
	
	selector : null,

	/**
	 * 부모 창
	 */
	parent : null,
	
	/**
	 * 데이터아이디
	 */
	dataId : null,
	
	/**
	 * 지형지물부호
	 */
	ftrCde : null,
	
	/**
	 * 관리번호
	 */
	ftrIdn : null,
	
	
	open : function(parent, dataId, ftrIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.parent = parent;
		that.dataId = dataId;
		that.ftrIdn = ftrIdn;
		
		var windowOptions = {
			width : 660,
			top : 120,
			left : 330,
			isClose : function() {
				that.close();
			}
		};
			
		var url = CONTEXT_PATH + "/ctrlpnt/" + dataId + "/" + ftrIdn + "/addPassPoint.do";
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.init();
			that.initUi();
			that.bindEvents();
		});
		
		that.selector = "#" + id;
	},
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result.state >= 1) {
					$.messager.alert({
						title : that.TITLE,
						msg : "시준점이 등록되었습니다.",
						fn : function() {
							that.close();
						}
					});
				}
				else {
					$.messager.alert(that.TITLE, "시준점 등록에 실패하였습니다.");
				}
			}
		});
	},

	initUi : function(){
		var that = this;
		
		that.ftrCde = $("input[name=ftrCde]", that.selector).val();
		
		$(".easyui-textbox", that.selector).textbox();
		$(".easyui-numberbox", that.selector).numberbox({
			min : 0,
			precision : 3
		});
	},	
	
	bindEvents : function() {
		var that = this;

		// 등록버튼
		$("#btn_insert", that.selector).on("click", function() {
			that.insert();
			return false;
		});

		// 닫기버튼
		$("#btn_close", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	/**
	 * 등록
	 */
	insert : function() {
		var that = this;
		
		var bseHgt = $("input[name=ecpLen]", that.selector).val();
		if (!bseHgt) {
			$("input[name=ecpLen]", that.selector).val("0");
		}
		
		var url = CONTEXT_PATH + "/ctrlpnt/" + that.ftrCde + "/" + that.ftrIdn + "/insertPassPoint.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.parent.loadPassPoint();
		that.ftrCde = null;
		that.ftrIdn = null;
		that.selector = null;
	}	
};

/**
 * 시준점 변경 
 */
windowObj.surveyBasePointObj.passPointModify = {

	TITLE : "시준점 편집",
	
	CLASS_NAME : "ModifyPassPoint",
	
	selector : null,

	/**
	 * 부모 창
	 */
	parent : null,
	
	/**
	 * 데이터아이디
	 */
	dataId : null,

	/**
	 * 시준점 고유번호
	 */
	ecpNo : null,
	
	
	open : function(parent, dataId, ecpNo) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.parent = parent;
		that.dataId = dataId;
		that.ecpNo = ecpNo;
		
		var windowOptions = {
			width : 660,
			top : 120,
			left : 330,
			isClose : function() {
				that.close();
			}
		};
			
		var url = CONTEXT_PATH + "/ctrlpnt/" + dataId + "/" + ecpNo + "/modifyPassPoint.do";
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.init();
			that.initUi();
			that.bindEvents();
		});
		
		that.selector = "#" + id;
	},
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result.state >= 1) {
					$.messager.alert({
						title : that.TITLE,
						msg : "시준점이 변경되었습니다.",
						fn : function() {
							that.close();
						}
					});
				}
				else {
					$.messager.alert(that.TITLE, "시준점 변경에 실패하였습니다.");
				}
			}
		});
	},

	initUi : function(){
		var that = this;
		
		that.ftrCde = $("input[name=ftrCde]", that.selector).val();
		that.ecpNo = $("input[name=ecpNo]", that.selector).val();
		
		$(".easyui-textbox", that.selector).textbox();
		$(".easyui-numberbox", that.selector).numberbox({
			min : 0,
			precision : 3
		});
	},	
	
	bindEvents : function() {
		var that = this;

		// 저장버튼
		$("#btn_update", that.selector).on("click", function() {
			that.modify();
			return false;
		});

		// 삭제버튼
		$("#btn_delete", that.selector).on("click", function() {
			that.remove();
			return false;
		});
		
		// 닫기버튼
		$("#btn_close", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	/**
	 * 변경
	 */
	modify : function() {
		var that = this;
		
		var bseHgt = $("input[name=ecpLen]", that.selector).val();
		if (!bseHgt) {
			$("input[name=ecpLen]", that.selector).val("0");
		}
		
		var url = CONTEXT_PATH + "/ctrlpnt/" + that.ecpNo + "/updatePassPoint.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 삭제
	 */
	remove : function() {
		var that = this;
		
		$.messager.confirm(that.TITLE, "시준점을 삭제하시겠습니까?", function(isTrue) {
			if(isTrue) {
				var url = CONTEXT_PATH + "/ctrlpnt/" + that.ecpNo + "/deletePassPoint.do";
				
				$.get(url).done(function(result) {
					if(result.type == 'del' && result.state >= 1) {
						$.messager.alert({
							title : that.TITLE,
							msg : "시준점을 삭제하였습니다.",
							fn : function() {
								that.close();
							}
						});
					}
					else {
						$.messager.alert(that.TITLE, "시준점 삭제에 실패했습니다.");
					}
				}).fail(function() {
					$.messager.alert(that.TITLE, "시준점 삭제에 실패했습니다.");
				});
			}
		});
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.parent.loadPassPoint();
		that.ftrCde = null;
		that.ftrIdn = null;
		that.ecpNo = null;
		that.selector = null;
	}	
};

///////////////////////////////////////////////////////////////////////////////////////////
// 사진
//
/**
* 사진 등록 
*/
windowObj.surveyBasePointObj.photoAdd = {

	TITLE : "사진 등록",
	
	CLASS_NAME : "AddPhoto",
	
	selector : null,

	/**
	 * 부모 창
	 */
	parent : null,
	
	/**
	 * 데이터아이디
	 */
	dataId : null,
	
	/**
	 * 지형지물부호
	 */
	ftrCde : null,
	
	/**
	 * 관리번호
	 */
	ftrIdn : null,
	
	/**
	 * 이미지 타입
	 */
	imageTy : null,
	
	
	open : function(parent, dataId, ftrIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.parent = parent;
		that.dataId = dataId;
		that.ftrIdn = ftrIdn;
		
		var windowOptions = {
			width : 415,
			top : 300,
			onClose : function() {
				that.close();
			}
		};
			
		var url = CONTEXT_PATH + "/ctrlpnt/" + dataId + "/" + ftrIdn + "/addPhoto.do";
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.init();
			that.initUi();
			that.bindEvents();
		});
		
		that.selector = "#" + id;
	},
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			beforeSubmit : function(arr, form) {
				for(var i=0, len=arr.length; i < len; i++) {
					var obj = arr[i];
					var name = obj.name;
					
					// 필수 입력 체크
					if(name == "imageCn") {
						if(!obj.value) {
							$.messager.alert(that.TITLE, "제목을 입력하여 주십시오.", null, function() {
								form.find("input[name=" + name + "]").focus();								
							});
							return false;
						}
					}
					else if(name == "orignlFileNm") {
						if(!obj.value) {
							$.messager.alert(that.TITLE, "사진을 등록하여 주십시오.");
							return false;
						}

						var file = obj.value;
						if(file instanceof String) {
							fileName = file;
						}
						else {
							fileName = file.name;
						}
						var extension = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
						var extensions = ["jpg", "jpeg", "bmp", "gif", "png"];
						if(extensions.indexOf(extension) == -1) {
							$.messager.alert(that.TITLE, "다음 확장자만 등록 가능합니다. (" + extensions.join(", ") + ")");
							return false;	
						}
					}
				}
			},
			success : function(result) {
				if(result.data) {
					that.close();
				}
				else {
					$.messager.alert(that.TITLE, "사진 등록에 실패하였습니다.");
				}
			}
		});
	},

	initUi : function(){
		var that = this;
		
		that.ftrCde = $("input[name=ftrCde]", that.selector).val();
		that.imageTy = $("input[name=imageTy]", that.selector).val();
		
		$(".easyui-textbox", that.selector).textbox();
	},	
	
	bindEvents : function() {
		var that = this;

		// 저장버튼
		$("#btn_insert", that.selector).on("click", function() {
			that.insert();
			return false;
		});

		// 닫기버튼
		$("#btn_close", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	/**
	 * 등록
	 */
	insert : function() {
		var that = this;
		$("form", that.selector).submit();
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.parent.loadPhoto();
		that.dataId = null;
		that.ftrCde = null;
		that.ftrIdn = null;
		that.imageTy = null;
		that.selector = null;
	}	
};

/**
* 사진 편집 
*/
windowObj.surveyBasePointObj.photoModify = {

	TITLE : "사진 편집",
	
	CLASS_NAME : "ModifyPhoto",
	
	selector : null,

	/**
	 * 부모 창
	 */
	parent : null,
	
	/**
	 * 사진 고유번호
	 */
	imageNo : null,
	
	
	open : function(parent, imageNo) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.parent = parent;
		that.imageNo = imageNo;
		
		var windowOptions = {
			width : 425,
			top : 120,
			isClose : function() {
				that.close();
			}
		};
			
		var url = CONTEXT_PATH + "/ctrlpnt/" + imageNo + "/modifyPhoto.do";
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.init();
			that.initUi();
			that.bindEvents();
			that.loadImage();
		});
		
		that.selector = "#" + id;
	},
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			beforeSubmit : function(arr, form) {
				for(var i=0, len=arr.length; i < len; i++) {
					var obj = arr[i];
					var name = obj.name;
					
					// 필수 입력 체크
					if(name == "imageCn") {
						if(!obj.value) {
							$.messager.alert(that.TITLE, "제목을 입력하여 주십시오.", null, function() {
								form.find("input[name=" + name + "]").focus();								
							});
							return false;
						}
					}
					else if(name == "orignlFileNm") {
						if(obj.value) {
							var file = obj.value;
							if(file instanceof String) {
								fileName = file;
							}
							else {
								fileName = file.name;
							}
							var extension = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
							var extensions = ["jpg", "jpeg", "bmp", "gif", "png"];
							if(extensions.indexOf(extension) == -1) {
								$.messager.alert(that.TITLE, "다음 확장자만 등록 가능합니다. (" + extensions.join(", ") + ")");
								return false;	
							}
						}
					}
				}
			},
			success : function(result) {
				if(result.data) {
					that.close();
				}
				else {
					$.messager.alert(that.TITLE, "사진 등록에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function(){
		var that = this;
		
		$(".easyui-textbox", that.selector).textbox();
	},	
	
	bindEvents : function() {
		var that = this;

		// 저장버튼
		$("#btn_update", that.selector).on("click", function() {
			that.modify();
			return false;
		});

		// 삭제버튼
		$("#btn_delete", that.selector).on("click", function() {
			that.remove();
			return false;
		});
		
		// 닫기버튼
		$("#btn_close", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	/**
	 * 사진 로딩
	 */
	loadImage : function() {
		var that = this;

		var fileNo = $("input[name=fileNo]", that.selector).val();
		
		var imgDownloadUrl = CONTEXT_PATH + "/cmmn/file/" + fileNo + "/download.do";
		var thumbnailUrl = CONTEXT_PATH + "/cmmn/image/" + that.imageNo + "/thumbnail.do?nocache=" + Math.random();
		$("#img_view_photo_manage_thumbnail", that.selector).attr("src", thumbnailUrl);
		
		var imageViewUrl = CONTEXT_PATH + "/popup/image/view.do?imageFileNo=" + fileNo;
		$("#img_view_photo_manage_thumbnail", that.selector).parent().attr("href", imageViewUrl);

		$(".a_down_view_photo", that.selector).attr("href", imgDownloadUrl);
	},
	
	/**
	 * 저장
	 */
	modify : function() {
		var that = this;
		$("form", that.selector).submit();
	},

	/**
	 * 삭제
	 */
	remove : function() {
		var that = this;
		
		$.messager.confirm(that.TITLE, "사진을 삭제하시겠습니까?", function(isTrue) {
			if(isTrue) {
				var url = CONTEXT_PATH + "/photoManage/" + that.imageNo + "/remove.do";
				
				$.get(url).done(function() {
					$.messager.alert({
						title : that.TITLE,
						msg : "사진을 삭제하였습니다.",
						fn : function() {
							that.close();
						}
					});
				}).fail(function() {
					$.messager.alert(that.TITLE, "사진 삭제에 실패했습니다.");
				});
			}
		});
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.parent.loadPhoto();
		that.imageNo = null;
		that.selector = null;
	}	
	
};
		