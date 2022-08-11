/**
 * 역사지도
 */
menuObj.historyMapObj = {
		
	/**
	 * 배경지도 백업
	 */
	backup : {
		service : null,
		type : null
	},
	
	/**
	 * 페이지명
	 */
	PAGE : "historyMap",
	
	/**
	 * 선택자
	 */
	selector : "#div_menu_panel_history_map",
		
	/**
	 * 클래스명
	 */
	CLASS_NAME : "historyMap",
	
	/**
	 * 제목
	 */
	TITLE : "역사 지도",
	
	/**
	 * 방향성
	 */
	direction : null,
	
	/**
	 * 간격 함수
	 */
	interval : null,
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.initUi();
		that.createFlightLayer();
		that.bindEvents();
		that.playerUi("stop");
		that.open();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 항공사진 목록 선택 패널
		$(".div_base_map_select_tool", that.selector).panel({
			title : "항공사진 목록 선택"
		});
		
		// 검색 종료 년도
		$(".sel_end", that.selector).combobox({
			width : 80,
			data : []
		});
		
		// 검색 시작 년도
		$(".sel_start", that.selector).combobox({
			width : 80,
			data : that.getYears(),
			onSelect : function(record) {
				var data = that.getYears(record.value);
				$(".sel_end", that.selector).combobox("loadData", data);
				$(".sel_end", that.selector).combobox("setValue", data[data.length-1].value);
			}
		});
		
		// 선택 버튼
		$(".a_select_base_map", that.selector).linkbutton({
			onClick : function() {
				var startYear = $(".sel_start", that.selector).combobox("getValue");
				var endYear = $(".sel_end", that.selector).combobox("getValue");
				$(".div_base_map input[type=checkbox]").each(function() {
					var node = $(this);
					var year = node.attr("data-year");
					if(year >= startYear && year <= endYear) {
						node.prop("checked", true);
					}
					else {
						node.prop("checked", false);
					}
				});
			}
		});
		
		// 항공사진 역사지도 조회 패널
		$(".div_base_map_tool", that.selector).panel({
			title : "항공사진 역사지도 조회"
		});
		
		// 동작 방식
		$(".switch_play_type", that.selector).switchbutton({
			checked : true,
			onText : "자동",
			offText : "수동",
			onChange : function(checked) {
				if(checked) {
					$(".number_play_inteval").numberspinner("enable");
				}
				else {
					$(".number_play_inteval").numberspinner("disable");
				}
			}
		});
		
		// 자동넘김 시간
		$(".number_play_inteval").numberspinner({
			width : 60,
			value : 3,
			min : 1,
			max : 10
		});
		
		// 역방향
		$(".a_prev_play", that.selector).click(function() {
			that.direction = "prev";
			var value = $(".switch_play_type", that.selector).switchbutton('options').checked;
			if(value) {
				that.start();
			}
			that.play();
		});
		
		// 정방향
		$(".a_next_play", that.selector).click(function() {
			that.direction = "next";
			var value = $(".switch_play_type", that.selector).switchbutton('options').checked;
			if(value) {
				that.start();
			}
			that.play();
		});

		// 시작
		$(".a_play_start", that.selector).click(function() {
			that.start();
		});
		
		// 일시정지
		$(".a_play_pause", that.selector).click(function() {
			that.pause();
		});
		
		// 정지
		$(".a_play_stop", that.selector).click(function() {
			that.stop();
		});
	},
	
	/**
	 * 플레이어 UI 설정
	 * @param mode
	 */
	playerUi : function(mode) {
		var that = this;
		var node = $(".div_base_map_tool", that.selector);
		$(".a_player", node).hide();
		if(mode == "play") {
			$(".a_play_pause", node).show();
			$(".a_play_stop", node).show();
		}
		else if(mode == "pause") {
			$(".a_play_start", node).show();
			$(".a_play_stop", node).show();
		}
		else if(mode == "stop"){
			$(".a_prev_play", node).show();
			$(".a_next_play", node).show();
		}
		else {
			console.log("정의되지 않은 모드 입니다.");
		}
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 항목 선택 시 체크
		$(".div_background_map_item", that.selector).click(function() {
			var node = $(this);
			$("input[type=checkbox]", node).trigger("click");
		});
		
		// 이벤트 버블 막음 
		$(".div_base_map input[type=checkbox]").click(function(event) {
			event.stopPropagation();
		});
		
	},
	
	/**
	 * 년도 목록
	 * @param startYear
	 * @returns {Array}
	 */
	getYears : function(startYear) {
		var years = [];
		var types = backgroundMapObj.data.tms.types;
		for(var type in types) {
			var photographyYear = types[type].photographyYear;
			if(startYear && photographyYear < startYear) {
				continue;
			}
			if(years.indexOf(photographyYear) == -1) {
				years.push(photographyYear);
			}
		}
		years.sort();
		
		var data = [];
		for(var i=0, len=years.length; i < len; i++) {
			var year = years[i];
			var obj = {
				value : year,
				text : year
			};
			if(i==0) {
				obj.selected = true;
			}
			data.push(obj);
		}
		return data;
	},
	
	/**
	 * 기본지도 목록
	 */
	createFlightLayer : function() {
		var that = this;
		var tagStr = "";
		var types = backgroundMapObj.data.tms.types;
		for(var type in types) {
			var obj = types[type];
			var divTag = '';
			divTag += '<div data-service="tms" data-type="' + type + '" data-map-no="' + obj.mapNo + '" class="div_background_map_item">';
			divTag += '<div class="div_check">';
			divTag += '<input type="checkbox" value="' + obj.mapNo + '" data-year="'+ obj.photographyYear +'" />';
			divTag += '</div>';
			divTag += '<div class="div_img" >';
			divTag += '<img src="' + MAP.TMS_URL + '/' + type + '.png" alt="' + type + '" title="' + type + '" />';
			divTag += '</div>';
			divTag += '<div class="div_content" >';
			divTag += obj.description;
			divTag += '</div>';
			divTag += '</div>';
			
			tagStr = divTag + tagStr;
		}
		$(".div_base_map", that.selector).html(tagStr);
		$(".div_base_map input[type=checkbox]").prop("checked", true);
	},
	
	/**
	 * 목록 사이즈 조절
	 */
	resizeWindowHandler : function() {
		var that = this;
		var height = $("#div_menu_panel").height();
		var titleHeight = $("> .title", that.selector).height();
		var clearfixHeight = $(".clearfix", that.selector).height();
		var toolsHeight = $(".div_base_map_select_tool").panel().parent().height() + $(".div_base_map_tool").panel().parent().height();
		var offset = 0;
		$(".div_base_map", that.selector).height(height - titleHeight - clearfixHeight - toolsHeight - offset);
	},
	
	/**
	 * 역사지도 동작
	 */
	play : function() {
		var that = this;
		var node = null;
		
		var checks = $(".div_background_map_item input[type=checkbox]:checked");
		
		if(checks.length > 1) {
			var selectNodes = $(".div_background_map_item.on input[type=checkbox]");
			if(selectNodes.length > 0) {
				
				var index = checks.index(selectNodes);
				if(that.direction == "prev") {
					index++;
				}
				else {
					index--;
				}
				
				if(index < 0) {
					index = checks.length-1;
				}
				else if(index > checks.length-1) {
					index = 0;
				}

				node = $(".div_background_map_item input[type=checkbox]:checked:eq(" + index + ")");
			}
			else {
				if(that.direction == "prev") {
					node = $(".div_background_map_item input[type=checkbox]:checked:first", that.selector);
				}
				else {
					node = $(".div_background_map_item input[type=checkbox]:checked:last", that.selector);
				}
			}
			
			var mapNo = node.val();
			$(".div_background_map_item").removeClass("on");
			$(".div_background_map_item[data-map-no=" + mapNo +"]").addClass("on");
			var type = $(".div_background_map_item[data-map-no=" + mapNo +"]").attr("data-type");
			backgroundMapObj.change("tms", type);
		}
		else {
			$.messager.alert("역사 지도", "역사지도 조회는 2개 이상의 항공사진이 선택되어야 합니다.");
			that.stop();
		}
	},
	
	/**
	 * 자동 시작
	 */
	start : function() {
		var that = this;
		var second = $(".number_play_inteval").numberspinner("getValue");
		that.interval = setInterval(function() { that.play(); }, second*1000);
		that.playerUi("play");
	},
	
	/**
	 * 일시 멈춤
	 */
	pause : function() {
		var that = this;
		if(that.interval) {
			clearInterval(that.interval);
			that.interval = null;
		}
		that.playerUi("pause");
	},
	
	/**
	 * 중지
	 */
	stop : function() {
		var that = this;
		if(that.interval) {
			clearInterval(that.interval);
			that.interval = null;
		}
		that.playerUi("stop");
		
		backgroundMapObj.change("tms", null);
		$(".div_background_map_item").removeClass("on");
	},
	
	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		$(that.selector).show();
		
		that.backup.service = backgroundMapObj.getService();
		that.backup.type = backgroundMapObj.getType();
		backgroundMapObj.change("tms", null);
		
		themamapObj.basemapObj.hide();
		
		// 화면 크기 변환 시 트리 영역 크기 조절 이벤트 등록
		mainObj.addResizeHandler("historyMap", function() {
			that.resizeWindowHandler();
		});
		that.resizeWindowHandler();
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		$(that.selector).hide();
		that.stop();
		backgroundMapObj.change(that.backup.service, that.backup.type);
		themamapObj.basemapObj.show();
		mainObj.removeResizeHandler("historyMap");
	}
		
};