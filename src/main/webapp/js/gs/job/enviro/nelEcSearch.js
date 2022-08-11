/**
 * 환경공간 검색 객체
 * @type {Object}
 */
nelEcSearchObj = {

	TITLE : "주소 검색",

	CLASS_NAME : "NelEcSearch",

	selector : null,

	isCreated : false,
	
	initUi : function() {
		var that = this;
		
		// easyUi설정
		$("#searchNelEc .easyui-datebox", that.selector).datebox();
		$("#searchNelEc .easyui-textbox", that.selector).textbox();
		$("#searchNelEc .easyui-combobox", that.selector).combobox();
		$("#searchNelEc .easyui-numberspinner", that.selector).numberspinner();
		
		var data = dongObj.getData();
		$("#selDong", that.selector).combobox({
			valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD),
		});
		
		$("#selDong", that.selector).combobox("loadData", data);
		
		// 산 여부
		$("#checkMauntain", that.selector).switchbutton({
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
		$("#checkMauntain", that.selector).switchbutton("setValue", "1");

		// 본번
		$("#mainNum", that.selector).numberspinner({
			required : false,
			min : 1,
			max : 9999,
			increment : 1
		});
		
		// 부번
		$("#subNum", that.selector).numberspinner({
			required : false,
			min : 0,
			max : 9999,
			increment : 1
		});
	},
	
	bindEvents : function() {
		var that = this;

		// 검색
		$(".a_search", that.selector).on("click", function() {
			if(that.validator()) {
				that.search();
			}
			return false;
		});

		// 닫기
		$(".a_close", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},

	open : function(url) {
		var that = this;

		if (that.selector) {
			that.close();
		}

		var windowOptions = {
			width : 650,
			height : 150,
			top : 120,
			left : 340,
			isClose : function() {
				that.close();
			}
		};

		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, that.params, windowOptions, function() {
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
		var dataIds = [];
		
		dataIds = COMMON.ECOLOGY_NATURE_MAP;
		
		selDong = $('#selDong', that.selector).combobox('getValue');
		checkMauntain = $("#checkMauntain", that.selector).switchbutton("options").checked?'2':'1';;
		mainNum = $('#mainNum', that.selector).numberspinner('getValue');
		subNum = $('#subNum', that.selector).numberspinner('getValue');
		
		var pnu = pnuObj.createPnu(selDong,checkMauntain,mainNum,subNum);
		
		var params = {};
		params.pnu = pnu;
		params.dataIds = dataIds;
		var url = CONTEXT_PATH + "/enviro/search.do";
		$.post(url, params).done(function(response) {
			if (response.datas.length >= 1) {
				resultObj.addRows(response.datas);
			}
			else {
				alert("검색 결과가 없습니다.");
				return false;
			}
		}).fail(function() {
			alert("검색 결과가 없습니다.");
		});
	},
	
	validator : function() {
		var that = this;
		// 동/리
		if(!$("#selDong", that.selector).combobox('getValue')) {
			$.messager.alert(that.TITLE, "동/리를 선택하여 주십시오.");
			return false;
		}
		else if(!$("#mainNum", that.selector).numberspinner('getValue')) {
			$.messager.alert(that.TITLE, "본번을 입력하여 주십시오.");
			return false;
		}
		else if(!$("#subNum", that.selector).numberspinner('getValue')) {
			$.messager.alert(that.TITLE, "부번을 입력하여 주십시오.");
			return false;
		}
		return true;
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	},

};
