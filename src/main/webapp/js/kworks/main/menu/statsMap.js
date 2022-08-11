/**
 * 왼쪽메뉴 통계지도
 * @type {Object}
 */
menuObj.statsMapObj = {
		
	/**
	 * 페이지 명
	 * @type {String}
	 */
	PAGE : "statsMap",
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_menu_panel_stats_map",
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "통계지도",
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.layerObj.init();
		statsMapObj.leftMenu.init();
		that.open();
	},
	
	/**
	 * 왼쪽메뉴 통계지도 열기
	 */
	open : function() {
		var that = this;
		$(that.selector).show();
	},
	
	/**
	 * 왼쪽메뉴 통계지도 닫기
	 */
	close : function() {
		var that = this;
		
		menuObj.statsMapObj.layerObj.clear();
		statsMapObj.info.close();
		$(".statsMapSubMenu").removeClass("on");
		
		$(that.selector).hide();
	}
};

/**
 * 통계지도 레이어
 * @type {Object}
 */
menuObj.statsMapObj.layerObj = {
	/**
	 * 제목
	 * @type {String}
	 */	
	TITLE : "통계지도 레이어",
	
	/**
	 * ol.source.Vector
	 * @type {ol.source.Vector}
	 */	
	source : null,
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.initGis();
	},
	
	/**
	 * GIS 초기화
	 */
	initGis : function() {
		var that = this;
		that.source = new ol.source.Vector();
		var layer = new ol.layer.Vector({
			id : "statsLayer",
			source : that.source,
			style : function(feature) {
				var style = new ol.style.Style({
					fill : new ol.style.Fill({
						color : feature.getProperties()["color"]
					}),
					stroke : new ol.style.Stroke({
						color : "#ffffff"
					})
				});
				return [style];
			}
		});
		mapObj.getMap().addLayer(layer);
		
		var interaction = new kworks.interaction.Select({
			layers : [layer],
			condition : ol.events.condition.pointerMove
		});
		interaction.set("id", "statsChoice");
		interaction.set("name", "statsChoice");
		mapObj.getMap().addInteraction(interaction);
		interaction.setActive(false);
		
		that.createInfoDiv();
		interaction.on("change:active", function(evt) {
			var isActive = evt.target.getActive();
			if(isActive) {
				$("#map_container").mousemove(function(event) {
					var features = interaction.getFeatures();
					if(features.getLength() > 0) {
						var feature = features.item(0);
						var properties = feature.getProperties();
						
						$("#div_menu_panel_stats_info_popup").css({
							left : event.clientX + 10,
							top : event.clientY
						});
						
						$("#div_menu_panel_stats_info_popup .span_adm_dr_nm").text(properties["admDrNm"]);
						$("#div_menu_panel_stats_info_popup .span_value").text(properties["value"]);
						$("#div_menu_panel_stats_info_popup .span_unit").text(properties["unit"]);
						$("#div_menu_panel_stats_info_popup").show();
					}
					else {
						$("#div_menu_panel_stats_info_popup").hide();
					}
				});
			}
			else {
				$("#map_container").unbind("mousemove");
				$("#div_menu_panel_stats_info_popup").hide();
				evt.target.removeOverlayFeatures();
			}
		});
	},
	
	/**
	 * 정보 표시 팝업 생성
	 */
	createInfoDiv : function() {
		var tagStr = '';
		tagStr += '<div id="div_menu_panel_stats_info_popup">';
		tagStr += '<span class="span_adm_dr_nm"></span> : ';
		tagStr += '<span class="span_value"></span> ';
		tagStr += '(<span class="span_unit"></span>)';
		tagStr += '</div>';
		$("body").append(tagStr);
	},
	
	/**
	 * 도형 목록 로딩
	 * @param {Function} callback 콜백 함수
	 */
	loadData : function(dataId, callback) {
		var that = this;
		// 공간 검색
		spatialSearchUtils.listAll(that.TITLE, dataId, null, function(rows) {
			var features = [];
			for(var i=0, len=rows.length; i < len; i++) {
				var row = rows[i];
				var format = new ol.format.WKT();
				var feature = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);
				feature.setGeometry(gisObj.toTransformMap(feature.getGeometry()));
				feature.setId(row.admDrCd);
				feature.setProperties({
					color : "rgba(0, 0, 0, 0)",
					admDrNm : rows[i].admDrNm
				});
				features.push(feature);
			};
			if(features.length > 0) {
				that.source.addFeatures(features);
				mapObj.getMap().activeInteractions("drag", "statsChoice");
			}
			if(callback) {
				callback();
			}
		});
	},
	
	showPolygonFeatures : function(iemUnit) {
		var that = this;
		
		var valArrLen = statsMapObj.valArr.length;
		var opaArrLen = statsMapObj.opaArr.length;
		var features = that.source.getFeatures();
		
		for(var i=0, len=features.length; i < len; i++) {
			var feature = features[i];
			var admDrCd = feature.getId();
			feature.setProperties({ isRemove : true });
			$.each(statsMapObj.VALU,function(k,v){
				if(v.administzone == admDrCd){
					var opaLevel = 0;
					
					for(var j=0; j < valArrLen; j++){
						if(Number(statsMapObj.valArr[j]) < Number(v.valu)){
							opaLevel = j;
						}
					}
					
					if(opaLevel > opaArrLen){
						opaLevel = opaArrLen - 1;
					}
					
					var stateOpacity = statsMapObj.opaArr[opaLevel];
					var rgbaStr = "rgba(" + statsMapObj.r + "," + statsMapObj.g + "," + statsMapObj.b + "," + stateOpacity + ")";
					feature.setProperties({
						color : rgbaStr,
						value : numberUtils.formatCurrency(v.valu),
						unit : iemUnit,
						isRemove : false
					});
					return false;
				}
			});
		}
		
		// 통계 결과가 없는 도형은 삭제
		for(var i=features.length-1; i >= 0; i--) {
			var feature = features[i];
			if(feature.getProperties()["isRemove"]) {
				that.source.removeFeature(feature);
			}
		}
	},
	
	/**
	 * 통계지도 객체 닫기
	 */
	clear : function() {
		var that = this;
		that.source.clear();
	}
		
};

/**
 * 통계지도 객체
 * @Object
 */
statsMapObj = {
		
		/**
		 * 년도값
		 */
		YEAR : null,
		
		/**
		 * 레이어 id 값
		 */
		DATA_ID : null,
		
		/**
		 * 통계항목의 분류 ID 값
		 */
		CL_NO : null,
		
		/**
		 * 통계 급간 단계
		 */
		LEVEL : 5,
		
		/**
		 * 통계 최소값
		 */
		MIN_VAL : null,
		
		/**
		 * 통계 최대값
		 */
		MAX_VAL : null,
		
		/**
		 * 통계지도 표시 색상
		 */
		COLOR : "0000ff",
		
		/**
		 * 통계지도 색상 Red
		 * @type {Number}
		 */
		r : 0,
		
		/**
		 * 통계지도 색상 Green
		 * @type {Number}
		 */
		g : 0,
		
		/**
		 * 통계지도 색상 Blue
		 * @type {Number}
		 */
		b : 255,
		
		/**
		 * 통계지도 투명도 배열
		 * @type {Array}
		 */
		opaArr : null,
		
		/**
		 * 통계지도 값 배열
		 * @type {Array}
		 */
		valArr : null,
		
		/**
		 * 통계 데이터 값 배열
		 * @type {Array}
		 */
		VALU : null,
		
		/**
		 * 초기화
		 */
		init : function(){
			
		},
		
		/**
		 * 통계값 배열 설정
		 */
		makeValueArray : function(minVal,maxVal,level){
			var valArr = [];
			var mi = Number(minVal);
			var mx = Number(maxVal);
			var lv = Number(level);
			
			for(var i = (lv - 1); i > 0; i--){
				var val = mi + (((mx - mi) / lv) * i);
				val = val.toFixed(3);
				valArr[i] = val;
			}
			
			valArr[0] = mi.toFixed(3);
			valArr[lv] = mx.toFixed(3);
			return valArr;
		},
		
		/**
		 * 테이블 html 만들기
		 * @params {Object}
		 * params.levelNum {Number} 급간 값
		 * params.color {Number} 색상값
		 * params.opaArr {Array} 투명도 배열
		 * params.valArr {Array} 통계값 배열
		 */
		makeInfoTable : function(params){
			var htmlStr = "";
			for(var i=0; i < params.levelNum; i++){
				htmlStr += "<tr>";
				
				htmlStr += "<td class='td_statsMap_color' style='background:#" + params.color + ";opacity:" + params.opaArr[i] + ";height:20px;width:50px;'>";
				htmlStr += "</td>";
				
				htmlStr += "<td class='h_20 w_50'>";
				htmlStr += "<span>" + params.valArr[i] + "</span>";
				htmlStr += "</td>";
				
				htmlStr += "<td class='h_20 w_50'>";
				htmlStr += "<span>" + params.valArr[i+1] + "</span>";
				htmlStr += "</td>";
				
				htmlStr += "</tr>";
			}
			return htmlStr;
		},
		
		/**
		 * 투명도값 배열 설정
		 */
		makeOpacityArray : function(levelNum){
			var opaArr = new Array();
			
			if(levelNum == 5){
				opaArr = ["0.1","0.3","0.5","0.7","0.9"];
			}else if(levelNum == 6){
				opaArr = ["0.1","0.26","0.42","0.58","0.74","0.9"];
			}else if(levelNum == 7){
				opaArr = ["0.1","0.24","0.37","0.5","0.64","0.77","0.9"];
			}
			
			return opaArr;
		}
};

/**
 * 왼쪽메뉴 > 통계지도 메뉴
 * @Object
 */
statsMapObj.leftMenu = {
		/**
		 * 선택자
		 * @type {String}
		 */
		selector : ".div_menu_stats_map_list",
		
		URL : restUtils.getResUrl(),
		
		/**
		 * 표현할 통계메뉴 리스트
		 * @type {String}
		 */
		displayMenuList : null,
		
		/**
		 * 초기화
		 */
		init : function(){
			var that = this;
			that.initUi();
			that.bindEvents();
		},
		
		/**
		 * 화면 초기화
		 */
		initUi : function(){
			var that = this;
			$(".statsMapYear",that.selector).empty();
			$(".div_stats_map_menu_list",that.selector).empty();
			
			var contentSelector = "#div_menu_panel_stats_map .content";
			var contentHi = $("#div_menu_panel").height() - 70;
			
			$(".div_menu_stats_map_list", contentSelector).height(contentHi);
			$(".div_stats_map_menu_list", contentSelector).height(contentHi- 40);
			var param = {};
			var url = that.URL + "statsMap/list.do";
			
			$.post(url, param).done(function(data){
				
				var htmlStr = "";
				htmlStr = that.menuMake(data);
				
				$(".div_stats_map_menu_list",that.selector).append(htmlStr);
				$(".statsMapYear", that.selector).val(Number(data.year));
				
				$(".statsMapYear", that.selector).combobox({
						onChange : function(newValue){
							that.yearChange(newValue);
						}
				});
				
				that.menuListView();
			}).fail(function() {
				alert("통계지도 목록을 가져오는데 실패하였습니다.");
			});
		},
		
		
		/**
		 * 이벤트 등록
		 */
		bindEvents : function() {
			var that = this;
			//mainMenu 클릭시 이벤트
			$(that.selector).on("click",".statsMapMainMenu",function(){
				$(".statsMapSubMenu" ,this).toggle() ;
				return false;
			});
			
			//subMenu 클릭시 이벤트
			$(that.selector).on("click",".statsMapSubMenu",function(){
				var element = $(this);
				var iemNo = element.attr("data-iemNo");
				var iemUnit = element.attr("data-iemUnit");
				if(element.hasClass("on")) {
					element.removeClass("on");
					statsMapObj.info.close();
					menuObj.statsMapObj.layerObj.clear();
				}
				else {
					$(".statsMapSubMenu").removeClass("on");
					element.addClass("on");
					var dataId = element.attr("data-dataId");
					menuObj.statsMapObj.layerObj.loadData(dataId, function() {
						statsMapObj.info.open(iemNo,iemUnit);
					});
				}
				return false;
			});
			
		},
		
		/**
		 * 년도 변경시 발생 이벤트
		 */
		yearChange : function(year){
			var that = this;
			$(".statsMapMainMenu ul",that.selector).remove();
			
			var param = {};
			param.iemYear = year;
			that.callSubMenu(param);
			statsMapObj.YEAR = year;
		},
		
		/**
		 * 통계지도 메뉴 구성
		 * 년도 selectBox 포함
		 */
		menuMake : function(data){
			var that = this;
			that.displayMenuList = {};
			
			//년도 selectBox 생성
			var optionStr = "";
			$.each(data.groupByIemYear,function(k,v){
				optionStr += "<option value='" + v.iemYear + "'>" + v.iemYear + "년</option>";
			});
			
			$(".statsMapYear", that.selector).append(optionStr);
			
			var htmlStr = "";
			htmlStr += "<ul class='ul_menu_menu_list'>";
			
			$.each(data.kwsStatsCl, function(k,v){
				htmlStr += "<li class='statsMapMainMenu li_menu_group collapse cursor_point ' data='" + v.clNo + "' style='display:none;'>";
				htmlStr += "<h2>";
				htmlStr += v.clNm;
				htmlStr += "</h2>";
				
				$.each(data.kwsStatsMastr, function(key,val){
					if(v.clNo == val.clNo){
						//서브메뉴 생성
						htmlStr += that.subMenuMake(val);
					}
				});
				
				htmlStr += "</li>";
			});
			
			htmlStr += "</ul>";
			return htmlStr;
		},
		
		/**
		 * subMenu 메뉴 Html구성
		 */
		subMenuMake : function(data){
			var that = this;
			var htmlStr = "";
			
			htmlStr += "<ul>";
			htmlStr += "<li class='statsMapSubMenu li_menu img " + data.clNo + " cursor_point display_n' ";
			htmlStr += "data-clNo='" + data.clNo + "' ";
			htmlStr += "data-iemNo='" + data.iemNo + "' ";
			htmlStr += "data-iemUnit='" + data.iemUnit + "' ";
			htmlStr += "data-iemYear='" + data.iemYear + "' ";
			htmlStr += "data-dataId='" + data.dataId + "' ";
			htmlStr += ">";
			
			htmlStr += '<img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/left_3dep_bl.png" alt="DASH" />';
			htmlStr += "<span class='f_s_12'>";
			htmlStr += data.iemNm;
			htmlStr += "</span>";
			htmlStr += "</li>";
			htmlStr += "</ul>";
			
			that.displayMenuList["menu_"+data.clNo] = (data.clNo);
			
			return htmlStr;
		},
		
		/**
		 * menu 설정
		 */
		menuListView : function(){
			var that = this;
			$(".div_stats_map_menu_list > ul > li", that.selector).css("display","none");
			$.each(that.displayMenuList,function(k,v){
				$(".div_stats_map_menu_list li[data='" + v + "']",that.selector).css("display","");
			});
		},
		
		/**
		 * subMenu 설정
		 * 년도 값으로 sub메뉴 값을 불러옴
		 */
		callSubMenu : function(param){
			var that = this;
			var url = that.URL + "statsMap/listKwsStatsMastr.do";
			that.displayMenuList = {};
			$.post(url, param).done(function(data){
				$.each(data.result,function(k,v){
					var clNo = v.clNo;
					var htmlStr = "";
					
					//서브메뉴 생성
					htmlStr = that.subMenuMake(v);
					$("li[data^='" + clNo + "']").append(htmlStr);
				});
				
				that.menuListView();
			}).fail(function() {
				alert("통계지도 목록을 가져오는데 실패하였습니다.");
			});
		}
};

/**
 * 왼쪽메뉴 > 통계지도 메뉴 > subMenu > 정보팝업
 * @Object
 */
statsMapObj.info = {
		
		/**
		 * TITLE
		 * @type {String}
		 */
		TITLE : "통계지도 정보",
		
		/**
		 * URL
		 * @type {String}
		 */
		URL : restUtils.getResUrl() + "statsMap/statsMapInfo.do",
		
		/**
		 * 파라미터 객체
		 * @type {Object}
		 */
		params : null,
		
		/**
		 * easyui window 옵션
		 * @type {Object}
		 */
		WINDOW_OPTIONS : null,
		
		/**
		 * 선택자
		 * @type {String}
		 */
		selector : null,
		
		/**
		 * 통계항목 ID
		 * @type {Number}
		 */
		iemNo : null,
		
		/**
		 * 통계항목의 단위
		 * @type {String}
		 */
		iemUnit : null,
		
		/**
		 * 통계년도
		 * @type {String}
		 */
		year : null,
		
		/**
		 * 초기화
		 */
		init : function(){
			
		},
		
		/**
		 * 화면 초기화
		 */
		initUi : function(){
			var that = this;
			
			that.WINDOW_OPTIONS = {
					width : 300,
					height : 400,
					top : 120,
					left : 350,
					onClose : function() {
						that.close();
					} 	
			};
		},
		
		/**
		 * 이벤트 등록
		 */
		bindEvents : function() {
			var that = this;
			
			// 간략 보기 클릭 시 이벤트
			$(that.selector).on("click", ".a_stats_select", function() {
				var element = $(this);
				var isOn = element.find("img").attr("src").indexOf("_on")>0?true:false;
				if(isOn) {
					mainObj.defaultActive();
					return false;
				}
				else {
					mapObj.getMap().activeInteractions("drag", "statsChoice");
				}
			});
			
			//설정 버튼 클릭시 이벤트
			$(that.selector).on("click",".statsMapInfoTopBtn .img_set",function(){
				statsMapObj.statsMapSet.open();
				return false;
			});
			
			//통계정보 버튼 클릭시 이벤트
			$(that.selector).on("click",".statsMapInfoTopBtn .img_stats_info",function(){
				that.statsInfoPopup();
				return false;
			});
		},
		
		/**
		 * 팝업창 열기
		 */
		open : function(iemNo,iemUnit){
			var that = this;
			that.close();
			
			that.iemNo = iemNo;
			that.year = statsMapObj.YEAR;
			var url = restUtils.getResUrl() + "statsMap/minMaxValu.do";
			var param = {};
			param.iemNo = iemNo;
			param.iemUnit = iemUnit;
			$.post(url, param).done(function(data){
				var minVal = data.minVal.valu;
				var maxVal = data.maxVal.valu;
				statsMapObj.MIN_VAL = minVal;
				statsMapObj.MAX_VAL = maxVal;
				statsMapObj.VALU = data.list;
				that.initUi();
				that.selector = "#" + windowObj.createWindow("statsMapInfoPop", that.TITLE, that.URL, that.params, that.WINDOW_OPTIONS, function(){
					that.bindEvents();
					$(".div_statsMap_Info_contents table tbody", that.selector).append(that.infoTable());
					menuObj.statsMapObj.layerObj.showPolygonFeatures(iemUnit);
					that.iemUnit = data.iemUnit;
					$(".span_iemUnit",that.selector).text("단위 : " + data.iemUnit);
				});
				
			}).fail(function() {
				alert("통계지도 데이터 로딩을 실패했습니다.");
			});
		},
		
		/**
		 * 테이블 생성
		 */
		infoTable : function(){
			var htmlStr = "";
			var minVal = statsMapObj.MIN_VAL;
			var maxVal = statsMapObj.MAX_VAL;
			var levelNum = statsMapObj.LEVEL;
			
			statsMapObj.valArr = statsMapObj.makeValueArray(minVal,maxVal,levelNum);
			statsMapObj.opaArr = statsMapObj.makeOpacityArray(levelNum);
			
			var params = {};
			params.levelNum	= levelNum;
			params.color	= statsMapObj.COLOR;
			params.opaArr	= statsMapObj.opaArr;
			params.valArr	= statsMapObj.valArr;
			
			htmlStr = statsMapObj.makeInfoTable(params);
			return htmlStr;
		},
		
		/**
		 * 팝업창 닫기
		 */
		close : function(){
			var that = this;
			statsMapObj.statsMapSet.close();
			windowObj.removeWindow(that.selector);
		},
		
		/**
		 * 통계정보 윈도우팝업창 열기
		 */
		statsInfoPopup : function(){
			var that = this;
			var url = restUtils.getResUrl() + "clipreport/" + that.iemNo + "/statsMap.do";
			popupUtils.open(url, "통계정보", { width : 900 , height : 800, left : 300, top:150 });
		}
};

/**
 * 왼쪽메뉴 > 통계지도 메뉴 > subMenu > 정보팝업 > 설정팝업
 * @Object
 */
statsMapObj.statsMapSet = {
		/**
		 * TITLE
		 * @type {String}
		 */
		TITLE : "통계지도 설정",
		
		/**
		 * URL
		 * @type {String}
		 */
		URL : restUtils.getResUrl() + "statsMap/statsMapSet.do",
		
		/**
		 * 설정팝업 파라미터객체
		 * @type {Object}
		 */
		params : null,
		
		/**
		 * easyui window 옵션
		 * @type {Object}
		 */
		WINDOW_OPTIONS : null,
		
		/**
		 * 선택자
		 * @type {String}
		 */
		selector : null,
		
		/**
		 * 통계지도설정 급간 레벨
		 * @type {Number}
		 */
		LEVEL : null,
		
		/**
		 * 통계지도설정 색상
		 * @type {Number}
		 */
		COLOR : null,
		
		/**
		 * 통계지도설정 색상 Red
		 * @type {Number}
		 */
		r : null,
		
		/**
		 * 통계지도설정 색상 Green
		 * @type {Number}
		 */
		g : null,
		
		/**
		 * 통계지도설정 색상 Blue
		 * @type {Number}
		 */
		b : null,
		
		/**
		 * 통계지도설정 투명도 배열
		 * @type {Array}
		 */
		opaArr : null,
		
		/**
		 * 통계지도설정 값 배열
		 * @type {Array}
		 */
		valArr : null,
		
		/**
		 * 초기화
		 */
		init : function(){
			var that = this;
			that.initUi();
			that.bindEvents();
			that.COLOR = statsMapObj.COLOR;
			that.r = statsMapObj.r;
			that.g = statsMapObj.g;
			that.b = statsMapObj.b;
			that.LEVEL = statsMapObj.LEVEL;
		},
		
		/**
		 * 화면초기화
		 */
		initUi : function(){
			var that = this;
			
			that.WINDOW_OPTIONS = {
					width : 300,
					height : 400,
					top : 120,
					left : 700,
					onClose : function() {
						that.close(that.selector);
					} 	
			};
		},
		
		/**
		 * 이벤트 등록
		 */
		bindEvents : function() {
			var that = this;
			//급간설정 변경시 이벤트
			$("#select_statsMap_level_set",".div_statsMap_set_contents").combobox({
				onChange : function(newValue){
					that.LEVEL= newValue;
					$(".div_statsMap_set_info table tbody", that.selector).empty();
					$(".div_statsMap_set_info table tbody", that.selector).append(that.infoTable(that.LEVEL));
				}
			});
			
			$(that.selector).on("click",".btn_modify2",function(){
				that.save();
			});
			
			$(that.selector).on("click",".btn_popCancel",function(){
				that.close();
			});
		},
		
		/**
		 * 팝업창 열기
		 */
		open : function(){
			var that = this;
			windowObj.removeWindow(that.selector);
			that.init();
			
			that.selector = "#" + windowObj.createWindow("statsMapSetPop", that.TITLE, that.URL, that.params, that.WINDOW_OPTIONS, function(){
				that.bindEvents();
				
				//$("#select_statsMap_level_set",that.selector).val(statsMapObj.LEVEL);
				$("#select_statsMap_level_set",that.selector).combobox('select',statsMapObj.LEVEL);
				$('#txt_statsMap_color_set .txt_color_set').val(statsMapObj.COLOR);
				$('#div_statsMap_color_set div').css("backgroundColor","#"+statsMapObj.COLOR);
				
				$("#div_statsMap_color_set").ColorPicker({
					color : "#" + statsMapObj.COLOR,
					onChange : function (hsb, hex, rgb) {
						$('.div_statsMap_set_contents #div_statsMap_color_set div').css('backgroundColor', '#' + hex);
						$('.div_statsMap_set_contents #div_statsMap_color_set .txt_color_set').val(hex);
						
						$(".div_statsMap_set_info table tbody .td_statsMap_color",that.selector).css("background","#"+ hex);
						
						that.COLOR = hex;
						that.r = rgb.r;
						that.g = rgb.g;
						that.b = rgb.b;
					}
				});
				
				$(".div_statsMap_set_info table tbody", that.selector).empty();
				$(".div_statsMap_set_info table tbody", that.selector).append(statsMapObj.info.infoTable());
			});
		},
		
		/**
		 * 통계지도 설정 테이블 생성
		 */
		infoTable : function(level){
			var that = this;
			var htmlStr = "";
			that.opaArr = [];
			that.valArr = [];
			
			var minVal = statsMapObj.MIN_VAL;
			var maxVal = statsMapObj.MAX_VAL;
			
			that.valArr = statsMapObj.makeValueArray(minVal,maxVal,level);
			that.opaArr = statsMapObj.makeOpacityArray(level);
			
			var tblParams = {};
			tblParams.levelNum	= level;
			tblParams.color		= that.COLOR;
			tblParams.opaArr	= that.opaArr;
			tblParams.valArr	= that.valArr;
			
			htmlStr = statsMapObj.makeInfoTable(tblParams);
			return htmlStr;
		},
		
		/**
		 * 통계지도 설정값 저장
		 */
		save : function(){
			var that = this;
			statsMapObj.LEVEL	= that.LEVEL;
			statsMapObj.COLOR	= that.COLOR;
			statsMapObj.r		= that.r;
			statsMapObj.g		= that.g;
			statsMapObj.b		= that.b;
			statsMapObj.opaArr	= that.opaArr;
			statsMapObj.valArr	= that.valArr;
			
			statsMapObj.info.open(statsMapObj.info.iemNo,statsMapObj.info.iemUnit);
		},
		
		/**
		 * 통계지도 설정팝업 닫기
		 */
		close : function(){
			var that = this;
			windowObj.removeWindow(that.selector);
		}	
};