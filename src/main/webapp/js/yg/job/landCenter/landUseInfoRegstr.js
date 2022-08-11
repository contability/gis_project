/**
 * 토지사용정보대장 객체
 */
/// 토지사용정보대장 검색
landUseInfoRegstrObj = {
		
	TITLE : "토지사용정보대장 검색",
	
	CLASS_NAME : "LandUseInfoRegstrSearch",
	
	selector : null,
	
	isCreated : false,
	
	initUi : function() {
		var that = this;
		
		// easyUi설정
		$("#listLandUseInfoRegstr .easyui-combobox", that.selector).combobox();
		$("#listLandUseInfoRegstr .easyui-textbox", that.selector).textbox();
		$("#listLandUseInfoRegstr .easyui-numberbox", that.selector).numberbox();
		
		// 산여부
		$(".chk_mntn", that.selector).prop("disabled", true);
		
		// 본번
		$(".txt_mnnm", that.selector).numberbox({
			min : 1,
			max : 9999,
			disabled : true
		});

		// 부번
		$(".txt_slno", that.selector).numberbox({
			min : 0,
			max : 9999,
			disabled : true
		});
	
	},
	
	bindEvents : function(){
		var that = this;
		
		$(".sel_dong", that.selector).combobox({
	         onChange : function(oldValue, newValue){
	        	 var dongCode = $(".sel_dong", that.selector).combobox("getValue");
	        	 
	        	 // 읍면동 = 강릉시O 
	        	 if(dongCode == "4215000000"){
	        		 
	            	// checkbox, numberbox 비활성화
	            	$(".chk_mntn", that.selector).prop("disabled", true);
	            	$(".txt_mnnm", that.selector).numberbox({
	            		disabled : true
	            	});
	            	$(".txt_slno", that.selector).numberbox({
	            		disabled : true
	            	});
	            	
	            	///산 여부, 본번, 부번 초기화 
	            	$("#listLandUseInfoRegstr .easyui-numberbox", that.selector).numberbox('clear');
	            	$(".chk_mntn").attr('checked', false);
	            	
	            }
	        	// 읍면동 = 강릉시X 
	        	else {
	        		// checkbox, numberbox 활성화
	            	$(".chk_mntn", that.selector).prop("disabled", false);
	            	$(".txt_mnnm", that.selector).numberbox({
	            		disabled : false
	            	});
	            	$(".txt_slno", that.selector).numberbox({
	            		disabled : false
	            	});
	            	
	            }
	         }
	      });
		
		// 검색
		$(".a_search_landUseInfoRegstr", that.selector).on("click", function(){
			if(that.validator()) {
				that.search();
			}
			return false;
		});
		
		// 초기화버튼
		$(".a_reset_landUseInfoRegstr", that.selector).on("click", function(){
			
			///초기화 
			$("#selDong", that.selector).combobox('select',"4280000000");
			$("#listLandUseInfoRegstr .easyui-numberbox", that.selector).numberbox('clear');
			$(".chk_mntn").attr('checked', false);
			
			///비활성화
			$(".chk_mntn", that.selector).prop("disabled", true);
        	$(".txt_mnnm", that.selector).numberbox({disabled : true});
        	$(".txt_slno", that.selector).numberbox({disabled : true});
			
			return false;
		});
		
	},
	
	validator : function() {
		var that = this;
		
		// 법정동
		if(!$(".sel_dong", that.selector).combobox("isValid")) {
			$.messager.alert(that.TITLE, "법정동을 선택하여 주십시오.");
			return false;
		}
		// 본번
		if(!$(".txt_mnnm", that.selector).numberbox("isValid")) {
			$.messager.alert(that.TITLE, "본번을 입력하여 주십시오.", null);
			return false;
		}
		return true;
	},
	 
	search : function(){
		var that = this;
		
		var url = CONTEXT_PATH + "/landUseInfo/listAllLandUseInfoRegstr.do";
		var params = {}; 
		
		var dongCode = $(".sel_dong", that.selector).combobox("getValue");
		var mntn = $(".chk_mntn", that.selector).is(":checked")?"2":"1";
		var mnnm = $(".txt_mnnm", that.selector).numberbox("getValue");
		var slno = $(".txt_slno", that.selector).numberbox("getValue");
		var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
		
		params.cntNam = $("input[name=cntNam]", that.selector).val();
		params.luiIdn = $("input[name=luiIdn]", that.selector).val();
		params.ownNam = $("input[name=ownNam]", that.selector).val();
		
		// 읍면동이 강릉시인 경우 PNU검색조건
		if(dongCode == "4280000000"){
			
			var cutPnuCode = pnuCode.substring(0, 4);
			params.pnu = cutPnuCode;
			
			$.get(url, params).done(function(response) {
				var data = [];
				var ids = [];
				
				if(response.result.length < 1){
					$.messager.alert(that.TITLE, "조건에 맞는 결과가 없습니다.");
					return false;
				}
				else {
					data.push({
						dataAlias : "토지사용정보대장",
						dataId : "LD_USE_MA",
						ids : ids
					});
					
					for(var j in response.result) {
						ids.push(response.result[j].luiIdn);
					}
					
					resultObj.addRows(data);
				}
			}).fail(function() {
				$.messager.alert(that.TITLE, "공사대장 리스트를 가져오는데 실패하였습니다.");
			});
		}
		// 읍면동이 강릉시가 아닌경우 PNU검색 조건 
		else {
			
			if(dongCode!='' && mntn == "1" && mnnm!='' && slno!=''){
				var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
				params.pnu = pnuCode;
			}
			else if(dongCode!='' && mntn == "2" && mnnm!='' && slno!=''){
				var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
				params.pnu = pnuCode;
			}
			else if(dongCode!='' && mntn == "1" && mnnm!=''){
				var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
				var cutPnuCode = pnuCode.substring(0, 15);
				params.pnu = cutPnuCode;
			}
			else if(dongCode!='' && mntn == "2" && mnnm!=''){
				var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
				var cutPnuCode = pnuCode.substring(0, 15);
				params.pnu = cutPnuCode;
			}
			else if(dongCode!='' && mntn == "1"){
				var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
				var cutPnuCode = pnuCode.substring(0, 11);
				params.pnu = cutPnuCode;
			}
			else if(dongCode!='' && mntn == "2"){
				var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
				var cutPnuCode = pnuCode.substring(0, 11);
				params.pnu = cutPnuCode;
			}
			
			// 본번 미입력 확인
			if(mnnm == '' && slno != ''){
				$.messager.alert(that.TITLE, "본번을 입력하십시오.");
			} else {
				$.get(url, params).done(function(response) {
					var data = [];
					var ids = [];
					
					if(response.result.length < 1){
						$.messager.alert(that.TITLE, "조건에 맞는 결과가 없습니다.");
						return false;
					}
					else {
						data.push({
							dataAlias : "토지사용정보대장",
							dataId : "LD_USE_MA",
							ids : ids
						});
						
						for(var j in response.result) {
							ids.push(response.result[j].luiIdn);
						}
						
						resultObj.addRows(data);
					}
				}).fail(function() {
					$.messager.alert(that.TITLE, "공사대장 리스트를 가져오는데 실패하였습니다.");
				});
			}
		}
	},
	
	open : function(){
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/landUseInfo/searchLandUseInfoRegstrPage.do";
		var windowOptions = {
			width : 660,
			height : 291,
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
	
	close : function(){
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
};

/// 토지사용정보대장 조회
landUseInfoRegstrObj.view = {
		
	TITLE : "토지사용정보대장 조회",
	
	CLASS_NAME : "LandUseInfoRegstrView",
	
	selector : null,

	luiIdn : null,
	
	cntIdn : null,
	
	isCreated : false,
	
	init : function(){
		var that = this;

		$("#a_writtenConsentOfUse").hide();
	
		var luiIdn = $("#luiIdn", that.selector).val();
		var url = CONTEXT_PATH + "/landUseInfo/" + luiIdn + "/selectFile.do";
		
		$.get(url).done(function(result){
			if(result && result.result != ""){

				$("#a_writtenConsentOfUse").show();
//				$("#a_writtenConsentOfUse").attr("href", CONTEXT_PATH + "/landUseInfo/" + result.result.fileNo + "/landFileDownload.do");
				$("#a_writtenConsentOfUse", that.selector).click(function() {
					var pdfWindow = window.open(CONTEXT_PATH + "/landUseInfo/pdf.do?fileNo=" + result.result.fileNo, "_blank");
					pdfWindow.focus();
				});
				
				$(".btn_landUseInfo_writtenConsentOfUse").val(result.result.fileNo);
				
			}
		});
	},
	
	bindEvents : function(){
		var that = this;
		
		// 편집버튼
		$(".btn_modify_landUseInfoRegstrDetail").on("click", function(){
			var cntIdn = $("#cntIdn", that.selector).val();
			var luiIdn = $("#luiIdn", that.selector).val();
			
			landUseInfoRegstrObj.modify.open(luiIdn,cntIdn);
			that.close();
			
			return false;
		});
		
		// 삭제버튼
		$(".btn_delete_landUseInfoRegstrDetail").on("click", function(){
			$.messager.confirm(that.TITLE, "삭제하시겠습니까?", function(isTrue) {
				if(isTrue) {
					that.remove();
				}
			});
			return false;
		});
		
		// 닫기버튼
		$(".btn_close_landUseInfoRegstrDetail").on("click", function(){
			that.close();
		
			return false;
		});
	},
	
	// 토지사용정보대장 삭제 
	remove : function(){
		var that = this;
		
		that.cntIdn = $("#cntIdn", that.selector).val();
		that.luiIdn = $("#luiIdn", that.selector).val();
		
		var url = CONTEXT_PATH + "/landUseInfo/"+ that.cntIdn + "/" + that.luiIdn + "/removeLandUseInfoRegstr.do";
		
		$.post(url).done(function(result) {
			if(result) {
				
				that.close();
				$.messager.alert(that.TITLE, "토지사용정보대장을 삭제하였습니다.");
			}
			else {
				$.messager.alert(that.TITLE, "토지사용정보대장을 삭제하였습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "토지사용정보대장을 삭제하였습니다.");
		});
	},
	
	//토지사용정보대장 상세정보 pnu값으로 주소매칭
	pnuMatch : function(){
		var that = this;
		luiIdn = $("#luiIdn", that.selector).val();
		var url = CONTEXT_PATH + "/landUseInfo/" + luiIdn +"/pnuMatch.do";

		$.get(url).done(function(result) {
			$("#useInfoAddress").text(result.result);
		});
	},

	open : function(fids){
		var that = this;
		
		if(fids.length == 1) {
			
			if(that.selector) {
				that.close();
			}
			
			that.luiIdn = fids;
			
			var url = CONTEXT_PATH + "/landUseInfo/" + that.luiIdn +"/selectOneLandUseInfoRegstrPage.do";
			var windowOptions = {
				width : 570,
				height : 320,
				top : 120,
				left : 550,
				onClose : function() {
					that.close();
				}
			};
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
				if(!that.isCreated) {
					that.pnuMatch();
					that.init();
					that.bindEvents();
					that.isCreated = true;
				}
			});
			
			that.selector = "#" + id;
		} else {
			$.messager.alert(that.TITLE, "하나의 항목만 조회가 가능합니다.");
		}
	},
	
	close : function(){
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.luiIdn = null;
		that.selector = null;
		that.isCreated = false;
	}
};

/// 토지사용정보대장 수정
landUseInfoRegstrObj.modify = {
		
	TITLE : "토지사용정보대장 수정",
	
	CLASS_NAME : "LandUseInfoRegstrModify",
	
	selector : null,
	
	luiIdn : null,
	
	isCreated : false,

	init : function(){
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					landUseInfoRegstrObj.view.open([that.luiIdn]);
					that.close();
					alert("토지사용정보대장이 수정되었습니다.");
				}
				else {
					alert("토지사용정보대장 수정에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function(){
		var that = this;
		
		// easyUi설정
		$("#landUseInfoRegstrModify .easyui-datebox", that.selector).datebox();
		$("#landUseInfoRegstrModify .easyui-textbox", that.selector).textbox();
		$("#landUseInfoRegstrModify .easyui-combobox", that.selector).combobox();
		$("#landUseInfoRegstrModify .easyui-numberbox", that.selector).numberbox();
		
		// tab 초기화
		$(".tab-list-1 li a", that.selector).on("click", function() {
			$(".tab-list-1 li, .tab-panel-1", that.selecor).removeClass("active");
			$(this, that.selector).parent().addClass("active");
			$(".tab-panel-1", that.selector).eq($(this, that.selector).parent().index()).addClass("active");
		});
	},
	
	bindEvents : function(){
		var that = this;
		
		// 본번
		$(".modify_txt_mnnm", that.selector).numberbox({
			required : true	,
			prompt : "필수 입력",
			max : 9999,
			min : 1
		});
		
		// 부번
		$(".modify_txt_slno", that.selector).numberbox({
			max : 9999,
			min : 0
		});
		
		// pnu 매칭
		var pnuResult = $("#pnu", that.selector).val();
		var selDong = pnuResult.substr(0,10);
		var chkMntn = pnuResult.substr(10,1);
		var txtMnnm = pnuResult.substr(11,4);
		var txtSlno = pnuResult.substr(15,4);
		
		//앞에 0제거
		var trimZeroMnnm = txtMnnm.replace(/(^0+)/,"");
		var trimZeroSlno = txtSlno.replace(/(^0+)/,"");
		
		$(".modify_sel_dong").combobox("select",selDong);
		if(chkMntn != null && chkMntn == 2){
			$(".modify_chk_mntn").prop("checked",true);
		} else {
			$(".modify_chk_mntn").prop("checked",false);
		}
		$(".modify_txt_mnnm").textbox('setValue',trimZeroMnnm);
		$(".modify_txt_slno").textbox('setValue',trimZeroSlno);
		
		// 저장버튼
		$(".btn_save_landUseInfoRegstrModify", that.selector).on("click", function() {
			that.modify();
			return false;
		});
		
		// 취소버튼
		$(".btn_cancel_landUseInfoRegstrModify", that.selector).on("click", function() {
			that.close();
		});
	},
	
	open : function(luiIdn, cntIdn){
		var that = this;
		
		if(that.selector){
			that. close();
		}
		
		that.luiIdn = luiIdn;
		that.cntIdn = cntIdn;
		
		var url = CONTEXT_PATH + "/landUseInfo/" + that.luiIdn + "/" + that.cntIdn + "/modifyLandUseInfoRegstrPage.do";
		var windowOptions = {
			width : 680,
			height : 370,
			top : 120,
			left : 550,
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
	
	modify : function(){
		var that = this;
		
		that.luiIdn = $("#luiIdn", that.selector).val();
		that.cntIdn = $("#cntIdn", that.selector).val();
		
		var dongCode = $(".modify_sel_dong", that.selector).combobox("getValue");
		var mntn = $(".modify_chk_mntn", that.selector).is(":checked")?"2":"1";
		var mnnm = $(".modify_txt_mnnm", that.selector).numberbox("getValue");
		var slno = $(".modify_txt_slno", that.selector).numberbox("getValue");
		var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
		
		$("#pnu").val(pnuCode);
		
		var url = CONTEXT_PATH + "/landUseInfo/" + that.luiIdn + "/" + that.cntIdn + "/modifyLandUseInfoRegstr.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	close : function(){
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.luiIdn = null;
		that.selector = null;
		that.isCreated = false;
	}
};

/// 토지사용정보대장 등록
landUseInfoRegstrObj.add = {
		
	TITLE : "토지사용정보대장 등록",
	
	CLASS_NAME : "LandUseInfoRegstrAdd",
	
	selector : null,
	
	isCreated : false,
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					that.close();
					$.messager.alert(that.TITLE, "공사대장이 등록되었습니다.");
				}
				else {
					$.messager.alert(that.TITLE, "공사대장 등록에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function(){
		var that = this;
		
		// easyUi설정
		$("#addLandUseInfoRegstr .easyui-datebox", that.selector).datebox();
		$("#addLandUseInfoRegstr .easyui-textbox", that.selector).textbox();
		$("#addLandUseInfoRegstr .easyui-combobox", that.selector).combobox();
		$("#addLandUseInfoRegstr .easyui-numberbox", that.selector).numberbox();

		// tab 초기화
		$(".tab-list-1 li a", that.selector).on("click", function() {
			$(".tab-list-1 li, .tab-panel-1", that.selecor).removeClass("active");
			$(this, that.selector).parent().addClass("active");
			$(".tab-panel-1", that.selector).eq($(this, that.selector).parent().index()).addClass("active");
		});
		
	},
	
	bindEvents : function() { 
		var that = this;
		
		// 본번
		$(".txt_mnnm", that.selector).numberbox({
			required : true	,
			prompt : "필수 입력",
			max : 9999,
			min : 1
		});
		
		// 부번
		$(".txt_slno", that.selector).numberbox({
			max : 9999,
			min : 0
		});
		
		// 저장버튼
		$(".btn_save_addLandUseInfoRegstr", that.selector).on("click", function() {
			
			that.save();
			return false;
		});
		
		// 취소버튼
		$(".btn_cancel_addLandUseInfoRegstr", that.selector).on("click", function() {
			that.close();
		});
	},

	open : function(luiIdn){
	var that = this;	
		
		if(that.selector) {
			that.close();
		}
		
		that.luiIdn = luiIdn;
		
		
		var url = CONTEXT_PATH + "/landUseInfo/addLandUseInfoRegstrPage.do";
		var windowOptions = {
			width : 680,
			height : 335,
			top : 120,
			left : 550,
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
	
	save : function(){
		var that = this;
		
		var dongCode = $(".sel_dong", that.selector).combobox("getValue");
		var mntn = $(".chk_mntn", that.selector).is(":checked")?"2":"1";
		var mnnm = $(".txt_mnnm", that.selector).numberbox("getValue");
		var slno = $(".txt_slno", that.selector).numberbox("getValue");
		var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
		
		$("#landUseInfoRegstrAddformPnu").val(pnuCode);
		
		var pnuUrl = CONTEXT_PATH + "/landUseInfo/" + pnuCode + "/searchNullPnu.do";
		$.get(pnuUrl).done(function(result) {
			if(result.result == "") {
				alert("올바른 주소를 입력하여 주십시오.");
			}
			else {
				var url = CONTEXT_PATH + "/landUseInfo/addLandUseInfoRegstr.do";
				$("form", that.selector).attr("action", url);
		        $("form", that.selector).submit();
			}
		});
	},
	
	close : function(){
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
};
