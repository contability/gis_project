windowObj.explantPropertyObj = {
		
	selector : null,
		
	TITLE : "속성 편집",
		
	CLASS_NAME : "ExplantProperty",

	fields : null,
	
	properties : null,
	
	open : function(layerId, properties) {
		var that = this;
		if(that.selector) {
			that.close();
		}
		var url = "";
		that.properties = properties; 
		if (properties.ftrIdn){
			url = CONTEXT_PATH + "/enviro/"+properties.ftrIdn+"/updatePageExplant.do";
		}else {
			url = CONTEXT_PATH + "/enviro/insertPageExplant.do";
		}
		var windowOptions = {
			width : 680,
			height : 330,
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
		
		$("#enviroModify .easyui-textbox", that.selector).textbox();
		$("#enviroModify .easyui-numberbox", that.selector).numberbox({min:0, precision:2});
		$("#enviroModify .easyui-combobox", that.selector).combobox();
		$("#enviroModify .easyui-datebox", that.selector).datebox();
		$("#enviroModify .easyui-numberspinner", that.selector).numberspinner();

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
		
		
	},
	
//	dateFormatter : function(date) {
//		var y = date.getFullYear();
//        var m = date.getMonth()+1;
//        var d = date.getDate();
//        
//        return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);		
//	},
	
//	dateParser : function(s) {
//		if (!s) 
//			return new Date();
//		
//        if (s.indexOf('-') != -1) {
//            var ss = s.split('-');
//	        var y = parseInt(ss[0], 10);
//	        var m = parseInt(ss[1], 10);
//	        var d = parseInt(ss[2], 10);
//	        
//	        if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
//	        	if (d <= 0)
//	        		return new Date(y, m-1, 1);
//	        	else
//	        		return new Date(y, m-1, d);
//	        }
//	        else if (!isNaN(y) && !isNaN(m)) {
//	        	if (m <= 0)
//	        		return new Date(y, 0);
//	        	else
//	        		return new Date(y, m-1);
//	        } 
//	        else if (!isNaN(y)) {
//	            return new Date(y, 0);
//	        } 
//	        else {
//	            return new Date();
//	        }
//        }
//        else if (s.length == 8) {
//			var y = parseInt(s.substr(0, 4), 10);
//			var m = parseInt(s.substr(4, 2), 10);
//			var d = parseInt(s.substr(6, 2), 10);
//			
//        	if (d <= 0)
//        		return new Date(y, m-1, 1);
//        	else
//        		return new Date(y, m-1, d);
//        }
//        else if (s.length >= 6) {
//			var y = parseInt(s.substr(0, 4), 10);
//			var m = parseInt(s.substr(4, 2), 10);
//			
//        	if (m <= 0)
//        		return new Date(y, 0);
//        	else
//        		return new Date(y, m-1);
//        }
//        else if (s.length >= 4) {
//			var y = parseInt(s.substr(0, 4), 10);
//			
//            return new Date(y, 0);
//        }
//        else {
//        	return new Date();
//        }
//	},

	
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
	
	insert : function(){
		var that = this;
		
		if(that.properties){
			
			that.properties = {
//					ftrIdn : $('#ftrIdn').val(),
					ftrIdn : $('input[name=ftrIdn]').val(),
					expType : $('input[name=expType]').val(),
					invNam : $('input[name=invNam]').val(),
					disType : $('input[name=disType]').val(),
					disDens : $('input[name=disDens]').val(),
					expSize : $('input[name=expSize]').val(),
					expStep : $('input[name=expStep]').val(),
					invNote : $('input[name=invNote]').val()
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
};