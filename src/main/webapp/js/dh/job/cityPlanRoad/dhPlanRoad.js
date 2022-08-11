/**
 * 동해시 도시계획도로 조회
 */
var dhPlanRoadObj = {

	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "도시계획도로 정보",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "CityPlanRoad",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 지형지물부호
	 */
	ftrCde : "PR001",

	/**
	 * 관리번호
	 */
	ftrIdn : null,
	
	isCreated : false,
		
	
	/**
	 * 열기
	 * @param ftrIdn - 관리번호
	 */
	open : function(ftrIdn){
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		that.ftrIdn = ftrIdn;
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + ftrIdn + "/viewRegister.do";
		var windowOptions = {
			width : 648,
			height : 670,
			onClose : function() {
				that.close();
			},
			onMove : function(left, top) {
				var width = $(window).width();
				var height = $(window).height();
				
				if (left < 0) {
					$(that.selector).window('move', {left:0});
				}
				else if (left + 648 > width) {
					$(that.selector).window('move', {left:width-648});
				}
				
				if (top < 0) {
					$(that.selector).window('move', {top:0});
				}
				else if (top + 641 > height) {
					$(that.selector).window('move', {top:height-641});
				}
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.initUi();
				that.bindEvents();
				that.loadConstruction(that.ftrIdn);
				that.loadRepair(that.ftrIdn);
				that.loadPhoto(that.ftrIdn);
				
				that.isCreated = true; 
			}
		});
		that.selector = "#" + id;
	},

	initUi : function() {
		var that = this;
		
		// tab 초기화
		$(".tab-list-1 li a", that.selector).on("click", function() {
			$(".tab-list-1 li, .tab-panel-1", that.selecor).removeClass("active");
			$(this, that.selector).parent().addClass("active");
			$(".tab-panel-1", that.selector).eq($(this, that.selector).parent().index()).addClass("active");
		});
		
//		var uprStr = $("input[name=uprStr]", that.selector).val();	// 기점
//		if (uprStr && uprStr.length == 19)
//			$("#uprStr", that.selector).html(pnuObj.getAddr(uprStr, true));
//		
//		var uprEnd = $("input[name=uprEnd]", that.selector).val();	// 종점
//		if (uprEnd && uprEnd.length == 19)
//			$("#uprEnd", that.selector).html(pnuObj.getAddr(uprEnd, true));
	},
	
	bindEvents : function() {
		var that = this;
		
		var editAt = $("input[name=editAt]", that.selector).val();	// 편집
		
		// 대장출력
		$("#btn_print", that.selector).on("click", function() {
			that.print();
			return false;
		});

		// 편집버튼
		$("#btn_edit", that.selector).on("click", function() {
			if (editAt == 'Y' || editAt == 'y') 
				that.modify();
			return false;
		});
		
		// 공사개요 등록버튼
		$("#btn_cons_add", that.selector).on("click", function() {
			if (editAt == 'Y' || editAt == 'y')
				that.addConstruction();
			return false;
		});

		// 정비이력 등록버튼
		$("#btn_repa_add", that.selector).on("click", function() {
			if (editAt == 'Y' || editAt == 'y')
				that.addRepair();
			return false;
		});

		// 사진 등록버튼
		$("#btn_photo_add", that.selector).on("click", function() {
			if (editAt == 'Y' || editAt == 'y')
				that.addPhoto();
			return false;
		});
		
		// 닫기버튼
		$("#btn_close", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	loadConstruction : function(ftrIdn) {
		var that = this;
		
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + that.ftrIdn + "/listConstruction.do";
		$.get(url).done(function(response){
			var trTag = '<tr><th width="8%">순번</th><th width="20%">시작일</th><th width="20%">종료일</th><th width="52%">공사내용</th></tr>';
			var rows = response.rows;
			
			for(var i=0; i<rows.length;i++){
				var row = rows[i];
				trTag += '<tr data-idn="' + row.ftrIdn + '" data-seq="' + row.conSeq + '" class="selectableRow">';
				trTag += '<td>' + (i+1) +'</td>';
				trTag += '<td>' + row.conStr + '</td>';
				trTag += '<td>' + row.conEnd + '</td>';
				trTag += '<td>' + row.conDoc + '</td>';
				trTag += '</tr>';
			}
			$('.tbodyConstruction', that.selector).html(trTag);
			
			// 클릭 이벤트 설정
			$(".tbodyConstruction tr", that.selector).click(function() {
				var element = $(this);
				var idn = element.attr("data-idn");
				var seq = element.attr("data-seq");
				
				if(idn && idn.length > 0 && seq && seq.length > 0) {
					$(".tbodyConstruction tr", that.selecor).removeClass("tr_select");
					$(this, that.selector).addClass("tr_select");
					
					dhPlanRoadObj.constructionModify.open(that, idn, seq);
				}
			});
		});
	},

	loadRepair : function(ftrIdn) {
		var that = this;
		
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + that.ftrIdn + "/listRepair.do";
		$.get(url).done(function(response){
			var trTag = '<tr><th width="8%">순번</th><th width="20%">시작일</th><th width="20%">종료일</th><th width="52%">정비내용</th></tr>';
			var rows = response.rows;
			
			for(var i=0; i<rows.length;i++){
				var row = rows[i];
				trTag += '<tr data-idn="' + row.ftrIdn + '" data-seq="' + row.repSeq + '" class="selectableRow">';
				trTag += '<td>' + (i+1) +'</td>';
				trTag += '<td>' + row.repStr + '</td>';
				trTag += '<td>' + row.repEnd + '</td>';
				trTag += '<td>' + row.repDoc + '</td>';
				trTag += '</tr>';
			}
			$('.tbodyRepair', that.selector).html(trTag);
			
			// 클릭 이벤트 설정
			$(".tbodyRepair tr", that.selector).click(function() {
				var element = $(this);
				var idn = element.attr("data-idn");
				var seq = element.attr("data-seq");
				
				if(idn && idn.length > 0 && seq && seq.length > 0) {
					$(".tbodyRepair tr", that.selecor).removeClass("tr_select");
					$(this, that.selector).addClass("tr_select");
					
					dhPlanRoadObj.repairModify.open(that, idn, seq);
				}
			});
		});
	},

	loadPhoto : function(ftrIdn) {
		var that = this;
		
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + that.ftrCde + "/" + that.ftrIdn + "/listPhoto.do";
		$.get(url).done(function(response){
			var trTag = '<tr><th width="8%">순번</th><th>사진</th></tr>';
			var rows = response.rows;
			
			for(var i=0; i<rows.length;i++){
				var row = rows[i];
				trTag += '<tr data-no="' + row.imageNo + '" class="selectableRow">';
				trTag += '<td>' + (i+1) +'</td>';
				trTag += '<td>' + row.imageSj + '</td>';
				trTag += '</tr>';
			}
			$('.tbodyPhoto', that.selector).html(trTag);
			
			// 클릭 이벤트 설정
			$(".tbodyPhoto tr", that.selector).click(function() {
				var element = $(this);
				var no = element.attr("data-no");
				
				if(no && no.length > 0) {
					$(".tbodyPhoto tr", that.selecor).removeClass("tr_select");
					$(this, that.selector).addClass("tr_select");
					
					dhPlanRoadObj.photoModify.open(that, no);
				}
			});
		});
	},
	
	print : function() {
		var that = this;
		
		var map = mapObj.getMap();
		var center = map.getCenter();
		var point = new ol.geom.Point(center);
		
		var from = map.getView().getProjection();
		var to = ol.proj.get("EPSG:4326");
		
		var transformPoint = spatialUtils.transform(point, from, to);
		var transformCenter = transformPoint.getCoordinates();

		map.once("postcompose", function(event) {
			if(map.tileQueue_.isEmpty()) {
				var canvas = event.context.canvas;
				var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + that.ftrIdn + "/capture.do";
				var data = canvas.toDataURL("image/jpeg");
				var params = {
					exportDtaSe : "OPEN_LIMITATION",
					exportResn : "도시계획도로 위치도",
					centerLon : transformCenter[0],
					centerLat : transformCenter[1],
					data : data,
					viewId : backgroundMapObj.getService(),
					tmsType : backgroundMapObj.getType()
				};
				
				$.post(url, params).done(function(result) {
					if(result && result.image) {
						var ftrIdn = result.ftrIdn;
						var fileName = result.image;
		
						var url = CONTEXT_PATH + "/clipreport/cityPlanRoadByOne.do?ftrIdn=" + ftrIdn + "&fileName=" + fileName;
				        popupUtils.open(url, "도로관리대장", { width : 1300 , height : 800, left : 300, top:150 });
					}
					else {
						$.messager.alert(that.TITLE, "위치도 생성에 실패하였습니다.");
					}
				}).fail(function() {
					$.messager.alert(that.TITLE, "위치도 생성에 실패하였습니다.");
				});
			}
			else {
				$.messager.alert("대장출력", "지도 로딩 중에는 사용할 수 없습니다.");
			}
		});
		map.renderSync();
				        
	},	
	
	/**
	 * 변경
	 */
	modify : function() {
		var that = this;
		
		dhPlanRoadObj.modifyRegister.open(that, that.ftrIdn);
		that.close();
	},
	
	addConstruction : function() {
		var that = this;
		
		dhPlanRoadObj.constructionAdd.open(that, that.ftrIdn);
	},

	addRepair : function() {
		var that = this;
		
		dhPlanRoadObj.repairAdd.open(that, that.ftrIdn);
	},

	addPhoto : function() {
		var that = this;
		
		dhPlanRoadObj.photoAdd.open(that, that.ftrCde, that.ftrIdn);
	},
	
	/**
	 * 닫기
	 */
	close:function(){
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.ftrIdn = null;
		that.selector = null;
		that.isCreated = false;
	}
};

/**
 * 도시계획도로 편집
 */
dhPlanRoadObj.modifyRegister = {
		
	TITLE : "도시계획도로 편집",
	
	CLASS_NAME : "ModifyCityPlanRoad",
	
	selector : null,
	
	/**
	 * 부모 창
	 */
	parent : null,
	
	/**
	 * 관리번호
	 */
	ftrIdn : null,
	
	isCreated : false,

	
	open : function(parent, ftrIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.parent = parent;
		that.ftrIdn = ftrIdn;
		
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + ftrIdn + "/modifyRegister.do";
		var windowOptions = {
			width : 648,
			height : 668,
			onClose : function() {
				that.close();
			}
		};
			
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.init();
				that.initUi();
				that.bindEvents();
				
				that.isCreated = true;
			}
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
						msg : "도시계획도로 정보가 변경되었습니다.",
						fn : function() {
							that.close();
						}
					});
				}
				else {
					$.messager.alert(that.TITLE, "도시계획도로 정보 변경에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function(){
		var that = this;
		
		$("#modifyCityPlanRoad .easyui-textbox", that.selector).textbox();
		$("#modifyCityPlanRoad .easyui-numberbox", that.selector).numberbox({min:0, precision:2});
		$("#modifyCityPlanRoad .easyui-combobox", that.selector).combobox();
		$("#modifyCityPlanRoad .easyui-datebox", that.selector).datebox();
		$("#modifyCityPlanRoad .easyui-numberspinner", that.selector).numberspinner();
		
		var uprTyp = $("input[name=uprType]", that.selector).val();	// 분류
		$("#uprTyp", that.selector).combobox("select", uprTyp);
		
		var data = dongObj.getData();
		
		// 기점 소재지 동
		$("#strDong", that.selector).combobox({
			valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD),
		});
		$("#strDong", that.selector).combobox("loadData", data);
		
		// 기점 소재지 산
		$("#strMauntain", that.selector).switchbutton({
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
		
		// 기점 소재지 본번
		$("#strMain", that.selector).numberspinner({
			required : true,
			value : 1,
			min : 1,
			max : 9999,
			increment : 1
		});
			
		// 기점 소재지 부번
		$("#strSub", that.selector).numberspinner({
			required : true,
			value : 0,
			min : 0,
			max : 9999,
			increment : 1
		});
		
		// 종점 소재지 동
		$("#endDong", that.selector).combobox({
			valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD),
		});
		$("#endDong", that.selector).combobox("loadData", data);
		
		// 종점 소재지 산
		$("#endMauntain", that.selector).switchbutton({
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

		// 종점 소재지 본번
		$("#endMain", that.selector).numberspinner({
			required : true,
			value : 1,
			min : 1,
			max : 9999,
			increment : 1
		});
			
		// 종점 소재지 부번
		$("#endSub", that.selector).numberspinner({
			required : true,
			value : 0,
			min : 0,
			max : 9999,
			increment : 1
		});
		
		var uprStr = $("input[name=uprStr]", that.selector).val();	// 기점
		if (uprStr && uprStr.length > 0) {
			$("#strDong", that.selector).combobox("select", uprStr.substring(0, 10));
			var chk = uprStr.substring(10, 11);
			if (chk == '2')
				$("#strMauntain", that.selector).switchbutton({checked : true});
			else
				$("#strMauntain", that.selector).switchbutton({checked : false});
			$("#strMain", that.selector).numberspinner("setValue", uprStr.substring(11, 15));
			$("#strSub", that.selector).numberspinner("setValue", uprStr.substring(15, 19));
		}
		
		var uprEnd = $("input[name=uprEnd]", that.selector).val();	// 종점
		if (uprEnd && uprEnd.length > 0) {
			$("#endDong", that.selector).combobox("select", uprEnd.substring(0, 10));
			var chk = uprEnd.substring(10, 11);
			if (chk == '2')
				$("#endMauntain", that.selector).switchbutton({checked : true});
			else
				$("#endMauntain", that.selector).switchbutton({checked : false});
			$("#endMain", that.selector).numberspinner("setValue", uprEnd.substring(11, 15));
			$("#endSub", that.selector).numberspinner("setValue", uprEnd.substring(15, 19));
		}
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
	
	/**
	 * 변경
	 */
	modify : function() {
		var that = this;
		
		var strDong = $("#strDong", that.selector).combobox("getValue");
		var strMauntain = $('input[name=strMauntain]').val();
		var strMain = $("#strMain", that.selector).textbox("getValue");
		var strSub = $("#strSub", that.selector).textbox("getValue");
		var uprStr = pnuObj.createPnu(strDong, strMauntain, strMain, strSub);
		if (strDong && uprStr.length == 19)
			$("input[name=uprStr]", that.selector).val(uprStr);
		else
			$("input[name=uprStr]", that.selector).val("");
		
		var endDong = $("#endDong", that.selector).combobox("getValue");
		var endMauntain = $('input[name=endMauntain]').val();
		var endMain = $("#endMain", that.selector).textbox("getValue");
		var endSub = $("#endSub", that.selector).textbox("getValue");
		var uprEnd = pnuObj.createPnu(endDong, endMauntain, endMain, endSub);
		if (endDong && uprEnd.length == 19)
			$("input[name=uprEnd]", that.selector).val(uprEnd);
		else
			$("input[name=uprEnd]", that.selector).val("");
		
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + that.ftrIdn + "/updateRegister.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 닫기
	 */
	close:function(){
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.parent.open(that.ftrIdn);
		that.parent = null;
		that.ftrIdn = null;
		that.selector = null;
		that.isCreated = false;
	}
};

/**
 * 도시계획도로 공사개요 등록
 */
dhPlanRoadObj.constructionAdd = {
		
	TITLE : "공사개요 등록",
	
	CLASS_NAME : "AddCityPlanRoadConstruction",
	
	selector : null,
	
	/**
	 * 부모 창
	 */
	parent : null,
	
	/**
	 * 관리번호
	 */
	ftrIdn : null,

	
	isCreated : false,
	
	
	open : function(parent, ftrIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.parent = parent;
		that.ftrIdn = ftrIdn;
		
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + that.ftrIdn + "/addConstruction.do";
		var windowOptions = {
			width : 660,
			top : 120,
			left : 330,
			isClose : function() {
				that.close();
			},
			onMove : function(left, top) {
				var width = $(window).width();
				var height = $(window).height();
				
				if (left < 0) {
					$(that.selector).window('move', {left:0});
				}
				else if (left + 660 > width) {
					$(that.selector).window('move', {left:width-662});
				}
				
				if (top < 0) {
					$(that.selector).window('move', {top:0});
				}
				else if (top + 325 > height) {
					$(that.selector).window('move', {top:height-325});
				}
			}
		};
			
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.init();
				that.initUi();
				that.bindEvents();
				
				that.isCreated = true;
			}
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
						msg : "공사개요가 등록되었습니다.",
						fn : function() {
							that.close();
						}
					});
				}
				else {
					$.messager.alert(that.TITLE, "공사개요 등록에 실패하였습니다.");
				}
			}
		});
	},

	initUi : function(){
		var that = this;
		
		$("#addCityPlanRoadConstruction .easyui-datebox", that.selector).datebox();
		$("#addCityPlanRoadConstruction .easyui-textbox", that.selector).textbox();
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
		
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + that.ftrIdn + "/insertConstruction.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.parent.loadConstruction(that.ftrIdn);
		that.ftrIdn = null;
		that.selector = null;
		that.isCreated = false;
	}	
};

/**
 * 도시계획도로 공사개요 편집
 */
dhPlanRoadObj.constructionModify = {
	
	TITLE : "공사개요 상세정보",
	
	CLASS_NAME : "CityPlanRoadConstruction",
	
	selector : null,

	/**
	 * 부모 창
	 */
	parent : null,
	
	/**
	 * 관리번호
	 */
	ftrIdn : null,
	
	/**
	 * 이력번호
	 */
	conSeq : null,
	
	isCreated : false,
	
	
	open : function(parent, ftrIdn, conSeq) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.parent = parent;
		that.ftrIdn = ftrIdn;
		that.conSeq = conSeq;
		
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + that.ftrIdn + "/" + that.conSeq + "/detailConstruction.do";
		var windowOptions = {
			width : 660,
			top : 120,
			left : 330,
			isClose : function() {
				that.close();
			}
		};
			
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.init();
				that.initUi();
				that.bindEvents();
				
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	init : function() {
		var that = this;
		
		var editAt = $("input[name=editAt]", that.selector).val();	// 편집
		if (editAt == 'Y' || editAt == 'y') {
			$("form", that.selector).ajaxForm({
				dataType : "json",
				success : function(result) {
					if(result.state >= 1) {
						if (result.type = "upd") {
							$.messager.alert({
								title : that.TITLE,
								msg : "공사개요가 변경되었습니다.",
								fn : function() {
									that.close();
								}
							});
						}
					}
					else {
						if (result.type = "upd") {
							$.messager.alert(that.TITLE, "공사개요 변경에 실패하였습니다.");
						}
					}
				}
			});
		}
	},

	initUi : function(){
		var that = this;
		
		var editAt = $("input[name=editAt]", that.selector).val();	// 편집
		if (editAt == 'Y' || editAt == 'y') {
			$("#detailCityPlanRoadConstruction .easyui-textbox", that.selector).textbox();
			$("#detailCityPlanRoadConstruction .easyui-datebox", that.selector).datebox();
		}
	},	

	bindEvents : function() {
		var that = this;

		var editAt = $("input[name=editAt]", that.selector).val();	// 편집
		
		// 변경버튼
		$("#btn_update", that.selector).on("click", function() {
			if (editAt == 'Y' || editAt == 'y')
				that.modify();
			return false;
		});

		// 삭제버튼
		$("#btn_delete", that.selector).on("click", function() {
			if (editAt == 'Y' || editAt == 'y')
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
		
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + that.ftrIdn + "/" + that.conSeq + "/updateConstruction.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 삭제
	 */
	remove : function() {
		var that = this;
		
		$.messager.confirm(that.TITLE, "공사개요를 삭제하시겠습니까?", function(isTrue) {
			if(isTrue) {
				var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + that.ftrIdn + "/" + that.conSeq + "/deleteConstruction.do";
				
				$.get(url).done(function(result) {
					if(result.type == 'del' && result.state >= 1) {
						$.messager.alert({
							title : that.TITLE,
							msg : "공사개요를 삭제하였습니다.",
							fn : function() {
								that.close();
							}
						});
					}
					else {
						$.messager.alert(that.TITLE, "공사개요 삭제에 실패했습니다.");
					}
				}).fail(function() {
					$.messager.alert(that.TITLE, "공사개요 삭제에 실패했습니다.");
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
		that.parent.loadConstruction(that.ftrIdn);
		that.ftrIdn = null;
		that.conSeq = null;
		that.selector = null;
		that.isCreated = false;
	}	
};


/**
 * 도시계획도로 정비이력 등록
 */
dhPlanRoadObj.repairAdd = {
		
	TITLE : "정비이력 등록",
	
	CLASS_NAME : "AddCityPlanRoadRepair",
	
	selector : null,
	
	/**
	 * 부모 창
	 */
	parent : null,
	
	/**
	 * 관리번호
	 */
	ftrIdn : null,

	
	isCreated : false,
	
	
	open : function(parent, ftrIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.parent = parent;
		that.ftrIdn = ftrIdn;
		
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + that.ftrIdn + "/addRepair.do";
		var windowOptions = {
			width : 660,
			top : 120,
			left : 330,
			isClose : function() {
				that.close();
			}
		};
			
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.init();
				that.initUi();
				that.bindEvents();
				
				that.isCreated = true;
			}
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
						msg : "정비이력이 등록되었습니다.",
						fn : function() {
							that.close();
						}
					});
				}
				else {
					$.messager.alert(that.TITLE, "정비이력 등록에 실패하였습니다.");
				}
			}
		});
	},

	initUi : function(){
		var that = this;
		
		$("#addCityPlanRoadRepair .easyui-datebox", that.selector).datebox();
		$("#addCityPlanRoadRepair .easyui-textbox", that.selector).textbox();
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
		
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + that.ftrIdn + "/insertRepair.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.parent.loadRepair(that.ftrIdn);
		that.ftrIdn = null;
		that.selector = null;
		that.isCreated = false;
	}	
};

/**
 * 도시계획도로 정비이력 편집
 */
dhPlanRoadObj.repairModify = {
	
	TITLE : "정비이력 상세정보",
	
	CLASS_NAME : "CityPlanRoadRepair",
	
	selector : null,

	/**
	 * 부모 창
	 */
	parent : null,
	
	/**
	 * 관리번호
	 */
	ftrIdn : null,
	
	/**
	 * 이력번호
	 */
	repSeq : null,
	
	isCreated : false,
	
	
	open : function(parent, ftrIdn, repSeq) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.parent = parent;
		that.ftrIdn = ftrIdn;
		that.repSeq = repSeq;
		
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + that.ftrIdn + "/" + that.repSeq + "/detailRepair.do";
		var windowOptions = {
			width : 660,
			top : 120,
			left : 330,
			isClose : function() {
				that.close();
			}
		};
			
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.init();
				that.initUi();
				that.bindEvents();
				
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	init : function() {
		var that = this;
		
		var editAt = $("input[name=editAt]", that.selector).val();	// 편집
		if (editAt == 'Y' || editAt == 'y') {
			$("form", that.selector).ajaxForm({
				dataType : "json",
				success : function(result) {
					if(result.state >= 1) {
						if (result.type = "upd") {
							$.messager.alert({
								title : that.TITLE,
								msg : "정비이력이 변경되었습니다.",
								fn : function() {
									that.close();
								}
							});
						}
					}
					else {
						if (result.type = "upd") {
							$.messager.alert(that.TITLE, "정비이력 변경에 실패하였습니다.");
						}
					}
				}
			});
		}
	},

	initUi : function(){
		var that = this;
		
		var editAt = $("input[name=editAt]", that.selector).val();	// 편집
		if (editAt == 'Y' || editAt == 'y') {
			$("#detailCityPlanRoadRepair .easyui-textbox", that.selector).textbox();
			$("#detailCityPlanRoadRepair .easyui-datebox", that.selector).datebox();
		}
	},	

	bindEvents : function() {
		var that = this;

		var editAt = $("input[name=editAt]", that.selector).val();	// 편집
		
		// 변경버튼
		$("#btn_update", that.selector).on("click", function() {
			if (editAt == 'Y' || editAt == 'y')
				that.modify();
			return false;
		});

		// 삭제버튼
		$("#btn_delete", that.selector).on("click", function() {
			if (editAt == 'Y' || editAt == 'y')
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
		
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + that.ftrIdn + "/" + that.repSeq + "/updateRepair.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 삭제
	 */
	remove : function() {
		var that = this;
		
		$.messager.confirm(that.TITLE, "정비이력을 삭제하시겠습니까?", function(isTrue) {
			if(isTrue) {
				var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + that.ftrIdn + "/" + that.repSeq + "/deleteRepair.do";
				
				$.get(url).done(function(result) {
					if(result.type == 'del' && result.state >= 1) {
						$.messager.alert({
							title : that.TITLE,
							msg : "정비이력을 삭제하였습니다.",
							fn : function() {
								that.close();
							}
						});
					}
					else {
						$.messager.alert(that.TITLE, "정비이력 삭제에 실패했습니다.");
					}
				}).fail(function() {
					$.messager.alert(that.TITLE, "정비이력 삭제에 실패했습니다.");
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
		that.parent.loadRepair(that.ftrIdn);
		that.ftrIdn = null;
		that.repSeq = null;
		that.selsector = null;
		that.isCreated = false;
	}	
};


/**
 * 도시계획도로 사진 등록
 */
dhPlanRoadObj.photoAdd = {
		
	TITLE : "사진자료 등록",
	
	CLASS_NAME : "AddCityPlanRoadPhoto",
	
	selector : null,
	
	/**
	 * 부모 창
	 */
	parent : null,
	
	/**
	 * 지형지물부호
	 */
	ftrCde : null,

	/**
	 * 관리번호
	 */
	ftrIdn : null,

	
	isCreated : false,
	
	
	open : function(parent, ftrCde, ftrIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.parent = parent;
		that.ftrCde = ftrCde;
		that.ftrIdn = ftrIdn;
		
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + that.ftrCde + "/" + that.ftrIdn + "/addPhoto.do";
		var windowOptions = {
			width : 415,
			top : 300,
			isClose : function() {
				that.close();
			}
		};
			
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.init();
				that.initUi();
				that.bindEvents();
				
				that.isCreated = true;
			}
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
//					if(name == "imageSj") {
//						if(!obj.value) {
//							$.messager.alert(that.TITLE, "제목을 입력하여 주십시오.", null, function() {
//								form.find("input[name=" + name + "]").focus();								
//							});
//							return false;
//						}
//					}
					if(name == "orignlFileNm") {
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
					$.messager.alert({
						title : that.TITLE,
						msg : "사진자료가 등록되었습니다.",
						fn : function() {
							that.close();
						}
					});
				}
				else {
					if (result.msg) {
						$.messager.alert(that.TITLE, result.msg);
					}
					else {
						$.messager.alert(that.TITLE, "사진자료 등록에 실패하였습니다.");
					}
				}
			}
		});
	},

	initUi : function(){
		var that = this;
		
		$(".easyui-textbox", that.selector).textbox();
		$(".easyui-combobox", that.selector).combobox();
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
		
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/insertPhoto.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.parent.loadPhoto(that.ftrIdn);
		that.ftrIdn = null;
		that.selector = null;
		that.isCreated = false;
	}	
};

/**
* 사진 편집 
*/
dhPlanRoadObj.photoModify = {

	TITLE : "사진 편집",
	
	CLASS_NAME : "CityPlanRoadPhoto",
	
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
			
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/" + imageNo + "/modifyPhoto.do";
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
//					if(name == "imageSj") {
//						if(!obj.value) {
//							$.messager.alert(that.TITLE, "제목을 입력하여 주십시오.", null, function() {
//								form.find("input[name=" + name + "]").focus();								
//							});
//							return false;
//						}
//					}
					if(name == "orignlFileNm") {
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
		
		var url = CONTEXT_PATH + "/rest/dhPlanRoad/updatePhoto.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},

	/**
	 * 삭제
	 */
	remove : function() {
		var that = this;
		
		$.messager.confirm(that.TITLE, "사진자료를 삭제하시겠습니까?", function(isTrue) {
			if(isTrue) {
				var url = CONTEXT_PATH + "/photoManage/" + that.imageNo + "/remove.do";
				
				$.get(url).done(function() {
					$.messager.alert({
						title : that.TITLE,
						msg : "사진자료를 삭제하였습니다.",
						fn : function() {
							that.close();
						}
					});
				}).fail(function() {
					$.messager.alert(that.TITLE, "사진자료 삭제에 실패했습니다.");
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
