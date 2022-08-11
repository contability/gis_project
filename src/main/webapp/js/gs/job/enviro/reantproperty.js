windowObj.reantPropertyObj = {
		
	selector : null,
		
	TITLE : "속성 편집",
		
	CLASS_NAME : "ReplantProperty",

	fields : null,
	
	properties : null,
	
	isNew : false,
	
	open : function(layerId, properties, isNew) {
		var that = this;
		if(that.selector) {
			that.close();
		}

		var url = "";
		that.properties = properties; 
		if (properties.ftrIdn){
			url = CONTEXT_PATH + "/enviro/"+properties.ftrIdn+"/updatePageReant.do";
		}else {
			url = CONTEXT_PATH + "/enviro/insertPageReant.do";
		}
		var windowOptions = {
			width : 680,
			height : 420,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
		});
		
		that.selector = "#" + id;
	},

	close : function() {
		var that = this;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	},
	
	initUi : function() {
		var that = this;
		
		$("#enviroReantModify .easyui-textbox", that.selector).textbox();
		$("#enviroReantModify .easyui-numberbox", that.selector).numberbox({min:0, precision:2});
		$("#enviroReantModify .easyui-combobox", that.selector).combobox();
		$("#enviroReantModify .easyui-datebox", that.selector).datebox();
		$("#enviroReantModify .easyui-numberspinner", that.selector).numberspinner();

		var data = dongObj.getData();
		
		// 기점 소재지 동
		$("#selDong", that.selector).combobox({
			valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD),
		});
		$("#selDong", that.selector).combobox("loadData", data);
		
		// 기점 소재지 산
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
		
		// 기점 소재지 본번
		$("#mainNum", that.selector).numberspinner({
			required : true,
			value : 1,
			min : 1,
			max : 9999,
			increment : 1
		});
			
		// 기점 소재지 부번
		$("#subNum", that.selector).numberspinner({
			required : true,
			value : 0,
			min : 0,
			max : 9999,
			increment : 1
		});
		
		that.pnu = that.properties.pnu;	// 기점
		var pnu = that.pnu;
		if (pnu && pnu.length > 0) {
			$("#selDong", that.selector).combobox("select", pnu.substring(0, 10));
			var chk = pnu.substring(10, 11);
			if (chk == '2')
				$("#checkMauntain", that.selector).switchbutton({checked : true});
			else
				$("#checkMauntain", that.selector).switchbutton({checked : false});
			$("#mainNum", that.selector).numberspinner("setValue", pnu.substring(11, 15));
			$("#subNum", that.selector).numberspinner("setValue", pnu.substring(15, 19));
		}
		
		$("#areaGs", that.selector).textbox("setValue", that.properties.areaGs);
		$("#areaGj", that.selector).textbox("setValue", that.properties.areaGj);
		$("#areaHn", that.selector).textbox("setValue", that.properties.areaHn);
		$("#areaJw", that.selector).textbox("setValue", that.properties.areaJw);
		$("#areaTs", that.selector).textbox("setValue", that.properties.areaTs);
	},
	
	bindEvents : function() {
		var that = this;
		// 저장버튼
		$("#btn_insert", that.selector).on("click", function() {
			if(that.validator()) {
				that.insert();
			}
			return false;
		});

		// 닫기버튼
		$("#btn_close", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	validator : function() {
		var that = this;
		// 관리번호
		if(!$("#expIdn", that.selector).textbox('getText')) {
			$.messager.alert(that.TITLE, "현지조사표에 대한 관리번호를 입력하세요");
			return false;
		}
		return true;
	},
	
	insert : function(){
		var that = this;
		
		if(that.properties){
			
			that.properties = {
					expIdn : $('input[name=expIdn]').val(),
					jobType : $('input[name=jobType]').val(),
					jobPers : $('input[name=jobPers]').val(),
					jobNote : $('input[name=jobNote]').val(),
					areaGs : $('input[name=areaGs]').val(),
					areaHn : $('input[name=areaHn]').val(),
					areaJw : $('input[name=areaJw]').val(),
					areaGj : $('input[name=areaGj]').val(),
					areaTs : $('input[name=etcNote]').val(),
					etcNote : $('input[name=etcNote]').val(),
					ftrIdn : $('input[name=ftrIdn]').val()
				};
			that.close();
		}else{
			$.messager.alert(that.TITLE, "속성정보는 필수 입력 사항입니다.");
		} 
	},
	
	getProperties : function(){
		var that = this;
		return that.properties;
	},
	
}