// 점용대장 검색 관리 객체
policyRegstrObj = {
		
	TITLE : "정책지도 검색",
	
	CLASS_NAME : "PolicyRegstrSearch",
	
	selector : null,
	
	isCreated : false,
	
	initUi : function() {
		var that = this;
		
		// easyUi설정
		$("#listPolicyRegstr .easyui-datebox", that.selector).datebox();
		$("#listPolicyRegstr .easyui-textbox", that.selector).textbox();
		$("#listPolicyRegstr .easyui-combobox", that.selector).combobox();
		$("#listPolicyRegstr .easyui-numberspinner", that.selector).numberspinner();
		
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
		
		// 초기화
		$(".a_reset_policyRegstr", that.selector).on("click", function() {
			$('#plcyTit', that.selector).textbox('clear');
			$('#cttNam', that.selector).textbox('clear');
			$('#selDong', that.selector).combobox('clear');
			$('#plcyDep', that.selector).combobox('clear');
			$('#checkMauntain', that.selector).switchbutton('uncheck');
			$('#mainNum', that.selector).numberspinner('clear');
			$('#subNum', that.selector).numberspinner('clear');
			return false;
		});
		
		// 검색
		$(".a_search_policyRegstr", that.selector).on("click", function() {
			that.search();
			return false;
		});
	},
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/policy/searchPolicyPage.do";
		var windowOptions = {
			width : 660,
			height : 220,
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

		var params = {};
		params.plcyTit = $('#plcyTit', that.selector).textbox('getText');		// 정책명
		params.plcyDep = $('#plcyDep', that.selector).combobox('getValue');	// 담당부서
		params.cttNam = $('#cttNam', that.selector).textbox('getText');	// 도급자
		selDong = $('#selDong', that.selector).combobox('getValue');;
		checkMauntain = $("#checkMauntain", that.selector).switchbutton("options").checked?'2':'1';;
		mainNum = $('#mainNum', that.selector).numberspinner('getValue');
		subNum = $('#subNum', that.selector).numberspinner('getValue');
		var plcyAdr = pnuObj.createPnu(selDong,checkMauntain,mainNum,subNum);
		if (plcyAdr == '0000000000100000000'){
			plcyAdr = '';
		}
		params.plcyAdr = plcyAdr;//위치
		
		var url = CONTEXT_PATH + "/policy/totalListPolicy.do";
		
		$.get(url, params).done(function(response) {
            if(response && response.result){
			    if(response.result.length >=1){
				    policyRegResultObj.addRows(response.result);
			    }else{
			    	alert("조건에 맞는 결과가 없습니다.");
					return false;
			    }
            }

		}).fail(function() {
			alert("정책지도 목록를 가져오는데 실패하였습니다.");
		});
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
},

policyRegstrObj.add ={
		
		initUi : function(){
			var that = this;
			
			// easyUi설정
			$("#addPolicyRegstr .easyui-datebox", that.selector).datebox();
			$("#addPolicyRegstr .easyui-textbox", that.selector).textbox();
			$("#addPolicyRegstr .easyui-combobox", that.selector).combobox();
			$("#addPolicyRegstr .easyui-numberspinner", that.selector).numberspinner();
			
		},
		
		bindEvents : function(){
			
			var that = this;
			
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
		},
		
		init : function(){
		},
		
		open : function() {
			var that = this;
			
			if(that.selector) {
				that.close();
			}
			var url = CONTEXT_PATH + "/policy/addpolicyRegstrPage.do";
			var windowOptions = {
					width : 680,
					height : 531,
					top : 120,
					left : 550,
					onClose : function() {
						that.close();
					}
				};
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
				if(!that.isCreated) {
					that.initUi();
					that.init();
					that.bindEvents();
					
					that.isCreated = true; 
				}
			});
			
			that.selector = "#" + id;
		},
		save : function() {
			var that = this;
			
			var url = CONTEXT_PATH + "";
			$("form", that.selector).attr("action", url);
			$("form", that.selector).submit();
		},
		
		close : function() {
			var that = this;
			
			windowObj.removeWindow(that.selector);
			that.selector = null;
			that.isCreated = false;
		}
};

//점용대장 퀵메뉴
policyRegstrObj.quick = {

	TITLE : "정책현황 목록",
	
	CLASS_NAME : "QuickPolicyRegstr",
	
	selector : null,
	
	pnu : null,
	
	isCreated : false,

	
	search : function(uprIdn, callback) {
		
		var url = CONTEXT_PATH + "/policy/" + ftrIdn + "/viewPolicy.do";

		$.post(url).done(function(response) {
			if(callback) {
				callback(response.row);
			}
		}).fail(function() {
			alert("정책현황을 가져오는데 실패하였습니다.");
		});
	},	
	
	close : function(){
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.pnu = null;
		that.selector = null;
		that.isCreated = false;
	}
};
	