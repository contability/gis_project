/**
 * 도시계획도로 조회
 */
var cityPlanRoadObj = {

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
		var url = CONTEXT_PATH + "/rest/cityPlanRoad/" + ftrIdn + "/viewRegister.do";
		var windowOptions = {
			width : 700,
			height : 499,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.initUi();
				that.bindEvents();
				that.loadHistory(that.ftrIdn);
				
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
		
		// 삭제버튼
		$("#btn_delete", that.selector).on("click", function() {
			if (editAt == 'Y' || editAt == 'y')
				that.remove();
			return false;
		});
		
		// 변경이력 등록버튼
		$("#btn_add", that.selector).on("click", function() {
			if (editAt == 'Y' || editAt == 'y')
				that.addHistory();
			return false;
		});
		
		// 닫기버튼
		$("#btn_close", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	loadHistory : function(ftrIdn) {
		var that = this;
		
		var url = CONTEXT_PATH + "/rest/cityPlanRoad/" + that.ftrIdn + "/listHistory.do";
		$.get(url).done(function(response){
			var trTag = '<tr><th width="8%">순번</th><th width="8%">등급</th><th width="8%">류별</th><th width="8%">번호</th><th width="*">고시근거</th><th width="15%">고시근거일</th></tr>';
			var rows = response.rows;
			
			for(var i=0; i<rows.length;i++){
				var row = rows[i];
				trTag += '<tr data-idn="' + row.ftrIdn + '" data-seq="' + row.uprSeq + '" class="selectableRow">';
				trTag += '<td>' + (i+1) +'</td>';
				trTag += '<td>' + row.uprGrd + '</td>';
				trTag += '<td>' + row.uprTyp + '</td>';
				trTag += '<td>' + row.uprNum + '</td>';
				trTag += '<td>' + row.uprNty + '</td>';
				trTag += '<td>' + row.uprYmd + '</td>';
				trTag += '</tr>';
			}
			$('.tbodyHistory', that.selector).html(trTag);
			
			// 클릭 이벤트 설정
			$(".tbodyHistory tr", that.selector).click(function() {
				var element = $(this);
				var idn = element.attr("data-idn");
				var seq = element.attr("data-seq");
				
				if(idn && idn.length > 0 && seq && seq.length > 0) {
					$(".tbodyHistory tr", that.selecor).removeClass("tr_select");
					$(this, that.selector).addClass("tr_select");
					
					cityPlanRoadObj.historyModify.open(that, idn, seq);
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
				var url = CONTEXT_PATH + "/rest/cityPlanRoad/" + that.ftrIdn + "/capture.do";
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
						
						var url = CONTEXT_PATH + "/clipreport/urbPlanMapnt.do?fileName=" + fileName + "&ftrIdn=" + ftrIdn;
			
		                popupUtils.open(url, "도시계획도로", { width : 1300 , height : 800, left : 300, top:150 });

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
		
		cityPlanRoadObj.modifyRegister.open(that, that.ftrIdn);
		that.close();
	},
	
	/**
	 * 삭제
	 */
	remove : function() {
		var that = this;
		
		$.messager.confirm(that.TITLE, "도시계획도로 정보를 삭제하시겠습니까?", function(isTrue) {
			if(isTrue) {
				var url = CONTEXT_PATH + "/rest/cityPlanRoad/" + that.ftrIdn + "/delete.do";
				
				$.get(url).done(function(result) {
					if(result.type == 'del' && result.state >= 1) {
						$.messager.alert({
							title : that.TITLE,
							msg : "도시계획도로 정보를 삭제하였습니다.",
							fn : function() {
								that.close();
							}
						});
					}
					else {
						$.messager.alert(that.TITLE, "도시계획도로 삭제에 실패했습니다.");
					}
				}).fail(function() {
					$.messager.alert(that.TITLE, "도시계획도로 삭제에 실패했습니다.");
				});
			}
		});
	},
	
	addHistory : function() {
		var that = this;
		
		cityPlanRoadObj.historyAdd.open(that, that.ftrIdn);
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
 * 도시계획도로 등록
 */
cityPlanRoadObj.addRegister = {
		
	TITLE : "도시계획도로 등록",
	
	CLASS_NAME : "AddCityPlanRoad",
	
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
	 * 교통시설 관리번호
	 */
	uprIdn : null,
	
	isCreated : false,

	
	open : function(parent, uprIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.parent = parent;
		that.ftrIdn = null;
		that.uprIdn = uprIdn;
		
		var url = CONTEXT_PATH + "/rest/cityPlanRoad/" + uprIdn + "/addRegister.do";
		var windowOptions = {
			width : 715,
			height : 489,
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
					that.ftrIdn = result.ftrIdn; 
					$.messager.alert({
						title : that.TITLE,
						msg : "도시계획도로 정보가 등록되었습니다.",
						fn : function() {
							cityPlanRoadObj.open(that.ftrIdn);
							that.close();
						}
					});
				}
				else {
					$.messager.alert(that.TITLE, "도시계획도로 정보 등록에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function(){
		var that = this;
		
		$("#addCityPlanRoad .easyui-textbox", that.selector).textbox();
		$("#addCityPlanRoad .easyui-combobox", that.selector).combobox();
		$("#addCityPlanRoad .easyui-datebox", that.selector).datebox();
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
		
		var url = CONTEXT_PATH + "/rest/cityPlanRoad/insert.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 닫기
	 */
	close:function(){
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.parent = null;
		that.ftrIdn = null;
		that.uprIdn = null;
		that.selector = null;
		that.isCreated = false;
	}
};

/**
 * 도시계획도로 편집
 */
cityPlanRoadObj.modifyRegister = {
		
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
		
		var url = CONTEXT_PATH + "/rest/cityPlanRoad/" + ftrIdn + "/modifyRegister.do";
		var windowOptions = {
			width : 715,
			height : 494,
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
		$("#modifyCityPlanRoad .easyui-combobox", that.selector).combobox();
		$("#modifyCityPlanRoad .easyui-datebox", that.selector).datebox();
		
		var uprGrd = $("input[name=grad]", that.selector).val();	// 등급
		var uprTyp = $("input[name=type]", that.selector).val();	// 류별
		$("#uprGrd", that.selector).combobox("select", uprGrd);
		$("#uprTyp", that.selector).combobox("select", uprTyp);
		
		var uprBjd = $("input[name=bjdCode]", that.selector).val();	// 법정동
		$("#uprBjd", that.selector).combobox("select", uprBjd);
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
		
		var url = CONTEXT_PATH + "/rest/cityPlanRoad/" + that.ftrIdn + "/update.do";
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
 * 도시계획도로 퀵메뉴
 */ 
cityPlanRoadObj.quick = {
		
	TITLE : "도시계획도로 조회",
	
	CLASS_NAME : "QuickCityPlanRoad",
	
	search : function(uprIdn, callback) {
		
		var url = CONTEXT_PATH + "/rest/cityPlanRoad/" + uprIdn + "/search.do";

		$.post(url).done(function(response) {
			if(callback) {
				callback(response.row);
			}
		}).fail(function() {
			alert("도시계획도로 대장을 가져오는데 실패하였습니다.");
		});
	},	
	
};

/**
 * 도시계획도로 변경이력 등록
 */
cityPlanRoadObj.historyAdd = {
		
	TITLE : "변천내역 등록",
	
	CLASS_NAME : "AddCityPlanRoadHistory",
	
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
		
		var url = CONTEXT_PATH + "/rest/cityPlanRoad/" + ftrIdn + "/addHistory.do";
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
						msg : "변쳔내역이 등록되었습니다.",
						fn : function() {
							that.close();
						}
					});
				}
				else {
					$.messager.alert(that.TITLE, "변쳔내역 등록에 실패하였습니다.");
				}
			}
		});
	},

	initUi : function(){
		var that = this;
		
		$("#addCityPlanRoadHistory .easyui-textbox", that.selector).textbox();
		$("#addCityPlanRoadHistory .easyui-combobox", that.selector).combobox();
		$("#addCityPlanRoadHistory .easyui-datebox", that.selector).datebox();
		
		var uprGrd = $("input[name=grad]", that.selector).val();	// 등급
		var uprTyp = $("input[name=type]", that.selector).val();	// 류별
		$("#uprGrd", that.selector).combobox("select", uprGrd);
		$("#uprTyp", that.selector).combobox("select", uprTyp);
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
		
		var url = CONTEXT_PATH + "/rest/cityPlanRoad/" + that.ftrIdn + "/insert.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.parent.loadHistory(that.ftrIdn);
		that.ftrIdn = null;
		that.selector = null;
		that.isCreated = false;
	}	
};

/**
 * 도시계획도로 변경이력 편집
 */
cityPlanRoadObj.historyModify = {
	
	TITLE : "변천내역 상세정보",
	
	CLASS_NAME : "CityPlanRoadHistory",
	
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
	uprSeq : null,
	
	isCreated : false,
	
	
	open : function(parent, ftrIdn, uprSeq) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.parent = parent;
		that.ftrIdn = ftrIdn;
		that.uprSeq = uprSeq;
		
		var url = CONTEXT_PATH + "/rest/cityPlanRoad/" + ftrIdn + "/" + uprSeq + "/detailHistory.do";
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
								msg : "변쳔내역이 변경되었습니다.",
								fn : function() {
									that.close();
								}
							});
						}
					}
					else {
						if (result.type = "upd") {
							$.messager.alert(that.TITLE, "변쳔내역 변경에 실패하였습니다.");
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
			$("#detailCityPlanRoadHistory .easyui-textbox", that.selector).textbox();
			$("#detailCityPlanRoadHistory .easyui-combobox", that.selector).combobox();
			$("#detailCityPlanRoadHistory .easyui-datebox", that.selector).datebox();
			
			var uprGrd = $("input[name=grad]", that.selector).val();	// 등급
			var uprTyp = $("input[name=type]", that.selector).val();	// 류별
			$("#uprGrd", that.selector).combobox("select", uprGrd);
			$("#uprTyp", that.selector).combobox("select", uprTyp);
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
		
		var url = CONTEXT_PATH + "/rest/cityPlanRoad/" + that.ftrIdn + "/" + that.uprSeq + "/update.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 삭제
	 */
	remove : function() {
		var that = this;
		
		$.messager.confirm(that.TITLE, "변쳔내역을 삭제하시겠습니까?", function(isTrue) {
			if(isTrue) {
				var url = CONTEXT_PATH + "/rest/cityPlanRoad/" + that.ftrIdn + "/" + that.uprSeq + "/delete.do";
				
				$.get(url).done(function(result) {
					if(result.type == 'del' && result.state >= 1) {
						$.messager.alert({
							title : that.TITLE,
							msg : "변천내역을 삭제하였습니다.",
							fn : function() {
								that.close();
							}
						});
					}
					else {
						$.messager.alert(that.TITLE, "변천내역 삭제에 실패했습니다.");
					}
				}).fail(function() {
					$.messager.alert(that.TITLE, "변쳔내역 삭제에 실패했습니다.");
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
		that.parent.loadHistory(that.ftrIdn);
		that.ftrIdn = null;
		that.uprSeq = null;
		that.selector = null;
		that.isCreated = false;
	}	
};