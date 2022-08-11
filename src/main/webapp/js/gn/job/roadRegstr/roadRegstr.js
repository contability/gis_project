// 도로대장 검색 관리 객체
roadRegstrObj = {
		
	TITLE : "도로대장 검색",
	
	CLASS_NAME : "RoadRegstrSearch",
	
	selector : null,
	
	isCreated : false,
	
	initUi : function() {
		var that = this;
	
		// easyUi설정
		$("#rotNames").combobox({
			valueField : "name",
			textField : "name",
			width : 520,
			onSelect : function(data) {
				$("input[name=rotNam]", that.selector).val(data.name);
				$("input[name=rotIdn]", that.selector).val(data.idn);				
			}
		});
		
		that.loadRotNames();
	},
	
	loadRotNames : function() {
		var url = CONTEXT_PATH + "/roadRegstr/rotNamAll.do";
		
		$.post(url).done(function(response) {
			if(response.rows.length < 1){
				alert("노선명이 없습니다.");
				return false;
			}
			else {
				var comboboxData = [];
				for(var i in response.rows) {
					comboboxData.push({ name : response.rows[i].rotNam ,
						idn : response.rows[i].rotIdn});
				}
				
				$("#rotNames").combobox("loadData", comboboxData);
			}
		}).fail(function() {
			alert("노선명 리스트를 가져오는데 실패하였습니다.");
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 초기화
		$(".a_reset_roadRegstrSearch", that.selector).on("click", function() {
			$("input[name=rotNam]", that.selector).val("");
			$("input[name=rotIdn]", that.selector).val("");
			$("input[name=secIdn]", that.selector).val("");

			return false;
		});
		
		// 검색
		$(".a_search_roadRegstrSearch", that.selector).on("click", function() {
			that.search();
			
			return false;
		});
	},
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/roadRegstr/searchRoadRegstrPage.do";
		var windowOptions = {
			width : 655,
			height : 189,
			top : 120,
			left : 330,
			isClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.initUi();
				that.bindEvents();
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	search : function() {
		var that = this;
		
		var roadNam = $("input[name=rotNam]", that.selector).val();
		var roadIdn = $("input[name=rotIdn]", that.selector).val();
//		var sectIdn = $("input[name=secIdn]", that.selector).val();
		
		if( (roadNam == null || roadNam.length <= 0) && (roadIdn == null || roadIdn.length <= 0) ) {
			alert("노선명 또는 노선번호는 필수입력 항목입니다.");
			return false;
		}
		
		var idx = 0;
		var regIdns = [];
		var roadRegisters = roadRegObj.getData();
		var cnt=roadRegisters.length;
		
		for (var i=0; i < cnt; i++ ) {
			if (roadRegisters[i].useAt == "Y")
				regIdns[idx++] = roadRegisters[i].regIdn;
		}

		var params = {};
		params.regIdns = regIdns;
		params.roadNam = roadNam;//$("input[name=rotNam]", that.selector).val();
		params.roadIdn = roadIdn;//$("input[name=rotIdn]", that.selector).val();
		params.sectIdn = $("input[name=secIdn]", that.selector).val();
		
		var url = CONTEXT_PATH + "/roadRegstr/searchRoadRegisterSummaries.do";
		
		$.post(url, params).done(function(response) {
			if(response.rows.length < 1){
				alert("조건에 맞는 결과가 없습니다.");
				return false;
			}
			else {
				roadRegResultObj.addRows(response.rows);
			}
		}).fail(function() {
			alert("도로대장 리스트를 가져오는데 실패하였습니다.");
		});
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
};