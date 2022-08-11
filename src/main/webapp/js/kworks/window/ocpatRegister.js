windowObj.ocpatRegisterObj = {

	/**
	* 제목
	* @type {String}
	*/
	TITLE : null,

	/**
	 * 클래스 명
	 * 
	 * @type {String}
	 */
	CLASS_NAME : "OcpatRegister",

	/**
	 * 선택자
	 * 
	 * @type {String}
	 */
	selector : null,

	dataId : null,

	/**
	 * 객체 아이디 목록
	 * 
	 * @type {Array.<Number>}
	 */
	fids : [],
	
	/**
	 * KWS_DATA 정보
	 * 
	 * @type {Array.<Object>}
	 */
	data : null,
	
	dataFields : null,

	/**
	 * 객체 아이디 목록
	 * 
	 * @type {Array.<Number>}
	 */
	featureInfos : [],

	/**
	 * 인덱스 (현재 객체 아이디)
	 * 
	 * @type {Number}
	 */
	index : 0,

	/**
	 * KWS_DATA_FILED_CTGRY 정보
	 * 
	 * @type {Array.<Object>}
	 */
	categories : null,

	/**
	 * 호출자 (result - 대장조회, edit - 속성편집)
	 * 
	 * @type {String}
	 */
	feature : null,
	
	/**
	 * 프로퍼티 목록
	 */
	properties : {},
	
	/**
	 * 저장 시 호출할 콜백 함수
	 */
	onSave : null,
	
	/**
	 * 데이터 로드 후 호출할 콜백 함수  
	 */
	onLoad : null,
	/**
	 * 정렬 항목
	 * 
	 * @type {String}
	 */
	sort : null,

	/**
	 * 정렬 순서
	 * 
	 * @type {String}
	 */
	ordr : null,
	
	//부속자료 선택된 그리드
	selectTr : null,
	
	ocpatIdn : null,
	
	ftrCde: null,
	/**
	 * 상세 정보 데이터 그리드 옵션 목록
	 * 
	 * @type {Object}
	 */
	gridOptions : {},

	// 초기화
	// 초기화
	open : function(ftrIdn,layerId,featureIds,data,ftrCde,pnu) {
		var that = this;
		var TITLE = data.ocpatAlias;
		
		if (that.selector) {
			that.close();
		}

		ocpatIdn = data.ocpatIdn;
		that.pnu = pnu;
		
		var url = CONTEXT_PATH + "/ocpat/"+ ocpatIdn + "/viewRdtOcpeDtlist.do";
		var windowOptions = {
			width : 660,
			top : 120,
			hight : 750,
			left : 330,
			isClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, TITLE, url, null, windowOptions, function() {
				that.initUi();
				that.isCreated = true;
				that.loadData(ftrIdn,layerId,featureIds,data);
				that.chageList(ftrIdn,ocpatIdn);
				that.fileList(ftrIdn,ocpatIdn);
				that.bindEvents(ftrIdn,ocpatIdn,layerId,featureIds,data);
		});
		
		that.selector = "#" + id;
	},
	
	loadData : function(ftrIdn,layerId,featureIds,data){
		var that = this;
		
		var params = {};
		params.ocpatIdn = ocpatIdn;
		params.layerId = layerId;
		params.featureIds = ftrIdn;
		params.idnField = camelcaseUtils.camelcaseToUnderbar(ocpatRegResultObj.getDataPkColumn());
		params.sortField = camelcaseUtils.camelcaseToUnderbar(ocpatRegResultObj.sort);
		params.sortDirection = ocpatRegResultObj.ordr;
		
		var url = CONTEXT_PATH + "/ocpat/searchRegister.do";
		$.post(url,params).done(function(response){
			var rows = response.rows[0];
			$("#ftrIdn", that.selector).text(rows.ftrIdn);
			$("#pmtNum", that.selector).text(rows.pmtNum);
			$("#pmtYmd", that.selector).text(rows.pmtYmd);
			$("#rodTyp", that.selector).text(rows.rodTyp);
			$("#rotNam", that.selector).text(rows.rotNam);
			$("#regNum", that.selector).text(rows.regNum);
			$("#pmtNum", that.selector).text(rows.pmtNum);
			$("#prsNam", that.selector).text(rows.prsNam);
			$("#regNum", that.selector).text(rows.regNum);
			$("#prsAdr", that.selector).text(rows.prsAdr);
			$("#jygLoc", that.selector).text(rows.jygLoc);
			$("#jygPur", that.selector).text(rows.jygPur);
			$("#jygAra", that.selector).text(rows.jygAra);
//			$("#jysYmd", that.selector).text(rows.jysYmd);
//			$("#jyeYmd", that.selector).text(rows.jyeYmd);
//			$("#jygUlm", that.selector).text(rows.jygUlm);
			$("#jygCmt", that.selector).text(rows.jygCmt);
			$("#jygPay", that.selector).text(rows.jygPay);
			$("#jygTel", that.selector).text(rows.jygTel);
			$("#rmkExp", that.selector).text(rows.rmkExp);
			$("#umdNam", that.selector).text(rows.umdNam);
			$("#oldCde", that.selector).text(rows.oldCde);
			$("#oldIdn", that.selector).text(rows.oldIdn);
			$("#chnCmt", that.selector).text(rows.chnCmt);
			$("#chnYmd", that.selector).text(rows.chnYmd);
			$("#endYmd", that.selector).text(rows.endYmd);

			var st = rows.jysYmd;
			var ed = rows.jyeYmd;
			if (!st && !ed) {
				var jygYmd = "<span class=\"space\" id = \"jygUlm\">" + rows.jygUlm + "</span>:";
				$("#jygYmd", that.selector).html(jygYmd);
			}
			else {
				$("#jygUlm", that.selector).text(rows.jygUlm);
				$("#jysYmd", that.selector).text(st);
				$("#jyeYmd", that.selector).text(ed);
			}
			//PNU 10자리 읍면동 / 1 산여부 / 메인지번 4 / 부번4
			var pnu = rows.pnu;
			//PNU 동찾기
			var selDong = pnu.substring(0,10);
			var data = dongObj.getDongName(selDong);
			$("#selDong", that.selector).text(data);
			
			//PNU 일반/산 찾기
			var isMount = pnu.substring(10,11);
			if (isMount==2){
			$("#isMount", that.selector).text("산");
			}
			
			//PNU 지번
			var mainNum = pnu.substring(11,15);
			$("#mainNum", that.selector).text(mainNum);
			
			//PNU 부번
			var subNum = pnu.substring(15,19);
			$("#subNum", that.selector).text(subNum);
			
		});
	},
	
	chageList : function(ftrIdn,ocpatIdn) {
		var that = this;
		
        switch(ocpatIdn){
    	case '100100' : 
    	  ftrCde = 'OC001'
    	 break;
    	case '200100' :
    	 ftrCde = 'OC002'
    	 break;
    	case '300100' :
    	 ftrCde = 'OC003'
    	 break;
    	case '400100' :
    	 ftrCde = 'OC004'
    	 break;
    	case '500100' :
    	 ftrCde = 'OC005'
    	 break;
    	case '600100' :
    	 ftrCde = 'OC006'
    	 break;
    	case '700100' :
       	 ftrCde = 'OC007'
       	 break;
    	case '800100' :
       	 ftrCde = 'OC008'
       	 break;
    	case '900100' :
       	 ftrCde = 'OC009'
       	 break;
    	default :
    	 ftrCde = 'Null';
    }
		var url = CONTEXT_PATH + "/ocpat/" + ftrIdn+ "/" + ftrCde + "/rdtOcpeHtlistAll.do";
		$.get(url).done(function(response) {
			var trTag = '';
			
			for(var i=0; i<response.rows.length;i++){
				var rows = response.rows[i];
				trTag += '<tr onclick="javascript:windowObj.HtRegisterObj.open(' + rows.histNo + ');">' + '<td>' + rows.histNo +'</td> <td colspan=2>' + rows.histDoc +'</td> <td colspan=3>' + rows.histNum +'</td></tr>';
				//trTag += "<tr> <td>" + rows.histNo +"</td> <td colspan=2>" + rows.histDoc +"</td> <td colspan=3>" + rows.histNum +"</td></tr>";	
			}
			$('#ocpatTable_Change', that.selector).append(trTag);
		});
	},
	
	fileList : function(ftrIdn,ocpatIdn){
		var that = this;
		
        switch(ocpatIdn){
    	case '100100' : 
    	  ftrCde = 'OC001';
    	 break;
    	case '200100' :
    	 ftrCde = 'OC002';
    	 break;
    	case '300100' :
    	 ftrCde = 'OC003';
    	 break;
    	case '400100' :
    	 ftrCde = 'OC004';
    	 break;
    	case '500100' :
    	 ftrCde = 'OC005';
    	 break;
    	case '600100' :
    	 ftrCde = 'OC006';
    	 break;
    	case '700100' :
       	 ftrCde = 'OC007';
       	 break;
    	case '800100' :
       	 ftrCde = 'OC008';
       	 break;
    	case '900100' :
       	 ftrCde = 'OC009';
       	 break;
    	default :
    	 ftrCde = 'Null';
    }
        var url = CONTEXT_PATH + "/ocpat/" + ftrCde + "/" + ftrIdn + "/fileList.do";
		
		$.get(url).done(function(response) {
			var trTag = '';
			for(var i=0; i<response.rows.length;i++){
				var rows = response.rows[i];
			var fileSj = rows.fileSj;
			var fileTitle = domnCodeObj.getCode("KWS-0443", fileSj);
			trTag += '<tr ocpatFileNo='+ rows.ocpatFileNo +' fileNo='+ rows.fileNo +'>' + 
			'<td>' + rows.ocpatFileNo +'</td><td>' +
				fileTitle.codeNm +'</td> <td colspan="5">' +
				rows.fileCn+'</td><td>'
				+ rows.lastUpdde +'</td></tr>';
			}
			$('#ocpatTable_file', that.selector).append(trTag);
			$(".table_text > .tbody_2 > tr > td").css("background-color","transparent");
			that.ocpatIdn = ocpatIdn;
			that.ftrCde = ftrCde;
			
            $("#ocpatTable_file tr").on("click", function(event){
            	var selectTag = $(this);
            	
            	var trTag = $("#ocpatTable_file tr");
        		for(var i = 0; i < trTag.length; i++){
        			trTag.eq(i).removeClass();
        		}
        		
        		selectTag.addClass("datagrid-row-selected");
        		that.selectTr = selectTag;
            });
            
		}).fail(function() {
			$.messager.alert("부속자료관리", "부속자료를 가져오는데 실패했습니다.");
		});
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
	
	// 부속자료 삭제
	fileRemove : function(ftrIdn, ocpatFileNo, fileNo, ocpatIdn) {
		var that = this;
		
		var url = CONTEXT_PATH + "/ocpat/" + fileNo + "/" + ocpatFileNo + "/deleteFile.do";
		
		$.post(url).done(function(result) {
			if(that.onRemove) {
				that.onRemove();
			}
			alert("부속자료가 삭제되었습니다.");
			
			$('#ocpatTable_file tr', that.selector).remove();
			that.fileList(ftrIdn, ocpatIdn);
			
		}).fail(function(result) {
			alert("부속자료 삭제에 실패했습니다.");
		});
	},
	
	bindEvents : function(ftrIdn) {
		var that = this;
		
		var pnu = that.pnu;
		
		var ocpatIdn = that.ocpatIdn;
		
		// 부속자료 미리보기
		$(".a_preview_view_file", that.selector).on("click",function(){
			if(that.selectTr){
				var fileNo = that.selectTr.attr("fileNo");
				var url = CONTEXT_PATH+"/ocpat/ocpatFileChk.do?fileNo="+fileNo;
				var imgArr = ["jpg", "jpeg", "bmp", "gif", "png"];
				var boolImg = false;
				
				$.get(url).done(function(response){
					if(response){
						var fileExtsn = response.data.fileExtsn.toLowerCase();
						
						imgArr.forEach(function(e){
							if(e == fileExtsn){
								boolImg = true;
							}
						});
						
						if(fileExtsn == "pdf"){
							window.open(CONTEXT_PATH+"/ocpat/"+fileNo+"/ocpatPdfOpen.do");
						}else if(boolImg){
							window.open(CONTEXT_PATH+"/popup/image/view.do?imageFileNo="+fileNo);
						}
					}else{
						$.messager.alert("부속자료 미리보기", "부속자료 미리보기에 실패하였습니다.");
					}
				});
			}else{
				$.messager.alert("부속자료 미리보기", "부속자료를 선택해주세요.");
			}
		});
		
		// 부속자료 등록
		$(".a_add_list_file", that.selector).on("click", function() {
			windowObj.ocpatFileRegisterObj.add.open(ftrIdn, that.ftrCde, that.ocpatIdn, function(){});
			return false;
		});
		
		// 부속자료 편집
		$(".a_modify_view_file", that.selector).on("click", function() {
			if(that.selectTr){
				var ocpatFileNo = that.selectTr.attr("ocpatFileNo");
				windowObj.ocpatFileRegisterObj.modify.open(ftrIdn, ocpatFileNo, that.ocpatIdn, that.onRemove);
				return false;
			}else{
				$.messager.alert("부속자료 미리보기", "부속자료를 선택해주세요.");
			}
		});
		

		// 부속자료 삭제
		$(".a_remove_view_file", that.selector).on("click", function() {
			if (that.selectTr) {
				$.messager.confirm("부속자료 삭제", "삭제하시겠습니까?", function(isTrue) {
					if (isTrue) {
						var ocpatFileNo = that.selectTr.attr("ocpatFileNo");
						var fileNo = that.selectTr.attr("fileNo");
						that.fileRemove(ftrIdn, ocpatFileNo, fileNo, that.ocpatIdn);
						that.selectTr = null;
						return false;
					}
				});
				return false;
			} else {
				$.messager.alert("부속자료 미리보기", "부속자료를 선택해주세요.");
			}
		});
		
		// 부속자료 다운로드
		$(".a_down_view_file", that.selector).on("click", function(){
			if(that.selectTr){
				var fileNo = that.selectTr.attr("fileNo");
				var imgDownloadUrl = CONTEXT_PATH + "/ocpat/" + fileNo + "/download.do";
				location.href=imgDownloadUrl;
			}else{
				$.messager.alert("부속자료 미리보기", "부속자료를 선택해주세요.");
			}
		});
		
		// 변경이력 등록버튼
		$(".btn_add_ocpatChange", that.selector).on("click", function() {
			windowObj.ocpatHistObj.open(ftrIdn,that.ftrCde);
		});
		
		// 대장출력  1대N
		$(".btn_modify_ocpatPrint", that.selector).on("click", function() {
			that.ocpatPrintOne(ftrIdn,ocpatIdn,layerId);
		});
		
		// 취소버튼
		$(".btn_close_ocpatRegstrAdd", that.selector).on("click", function() {
			that.close();
		});
		
		// 편집버튼
		$(".btn_update_ocpatRegstr", that.selector).on("click", function() {
			windowObj.LookDtObj.open(ftrIdn,pnu);
		});
		
		// 삭제버튼
		$(".btn_remove_RdtOcpatDtRegstr", that.selector).on("click", function() {
				windowObj.LookDtObj.remove(ftrIdn,ocpatIdn);
			return false;
		});
	},
	
	ocpatPrintOne : function(ftrIdn,ocpatIdn,layerId){
		var that = this;
		
        switch(ocpatIdn){
    	case '100100' : 
    	  ftrCde = 'OC001'
    	 break;
    	case '200100' :
    	 ftrCde = 'OC002'
    	 break;
    	case '300100' :
    	 ftrCde = 'OC003'
    	 break;
    	case '400100' :
    	 ftrCde = 'OC004'
    	 break;
    	case '500100' :
    	 ftrCde = 'OC005'
    	 break;
    	case '600100' :
    	 ftrCde = 'OC006'
    	 break;
    	case '700100' :
       	 ftrCde = 'OC007'
       	 break;
    	case '800100' :
       	 ftrCde = 'OC008'
       	 break;
    	case '900100' :
       	 ftrCde = 'OC009'
       	 break;
    	default :
    	 ftrCde = 'Null';
    }
		
		var url = CONTEXT_PATH + "/clipreport/";
		
		url += ftrCde+"/ocpatPrint.do?ftrCde=" + ftrCde + "&ftrIdn=" + ftrIdn;
		
	    popupUtils.open(url, "점용허가", { width : 900 , height : 800, left : 300, top:150 });
	},
	
	getFtrCde : function() {
		var that = this;
		
		var ftrCdeNm = null;
		var categories = that.categories;
		// 데이터의 카테고리
		var checked = false;
		for ( var id in categories) {
			var data = $("."+id, that.selector).propertygrid("getData");
			for(var i=0, len=data.rows.length; i < len; i++) {
				var row = data.rows[i];
				if(row.fieldId == "FTR_CDE") {
					checked = true;
					ftrCdeNm = row.value;
					break;
				}
			}
			if(checked) {
				break;
			}
		}
		
		var domnId = null;
		var dataInfo = dataObj.getDataByDataId(that.dataId, true);
		var fields = dataInfo.kwsDataFields;
		for(var i=0, len=fields.length; i < len; i++) {
			if(fields[i].fieldId == "FTR_CDE") {
				domnId = fields[i].domnId;
				break;
			}
		}
		
		if(domnId) {
			var codes = domnCodeObj.getData(domnId);
			for(var i=0, len=codes.length; i < len; i++) {
				if(codes[i].codeNm == ftrCdeNm) {
					return codes[i].codeId;					
				}
			}
		}
		
		return null;
	},

	// 데이터 필드 카데고리 로드
	loadDataFieldCategory : function() {
		var url = CONTEXT_PATH + "/rest/dataFieldCtgry/listAll.do";
		var promise = $.get(url);
		return promise;
	},

	/**
	 * 목록 추가
	 * 
	 * @param data
	 */
	addRows : function(data) {
		var that = this;
		
		// 기존목록 있을시 닫고 실행
		that.close();

		// 중복 검사
		var isExist = false;
		for (var i = 0, len = data.length; i < len; i++) {
			for (var j = 0, jLen = rows.length; j < jLen; j++) {
				if (data[i].ocpatIdn == rows[j].ocpatIdn) {
					isExist = true;
					break;
				}
			}
		}

		// 중복된 데이터(KWS_OCPAT_REG)가 있는 경우 선택창 표시
		if (isExist) {
			windowObj.duplicationConfirmObj.open({
				onSubmit : function(mode) {
					if (mode == "new") {
						that.open(data);
					} else if (mode == "append") {
						that.appendRows(data);
					}
				}
			});
		} else {
			that.appendRows(data);
		}
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;

		that.ocpatIdn = null;
		that.data = null;
		that.dataFields = null;
		that.featureInfos = [];
		that.index = 0;
		that.categories = null;
		that.feature = null;
		that.properties = {};
		that.onSave = null;
		that.onLoad = null;
		that.fids = null;
		that.selectTr = null;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
};

windowObj.HtRegisterObj ={
		
		TITLE : "변경이력 상세조회",
		
		CLASS_NAME : "modifyRdtOcpeHt",
		
		selector : null,
		
		isCreated : false,
		
		init : function() {
			var that = this;
			
			$("form", that.selector).ajaxForm({
				dataType : "json",
				success : function(result) {
					if(result) {
						that.close();
						$.messager.alert(that.TITLE, "변경이력이 저장되었습니다.");
					}
					else {
						$.messager.alert(that.TITLE, "변경이력 저장에 실패하였습니다.");
					}
				}
			});
		},
		
		initUi : function(){
			var that = this;
			
			// easyUi설정
			$("#modifyRdtOcpeHtRegstr .easyui-datebox", that.selector).datebox();
			$("#modifyRdtOcpeHtRegstr .easyui-textbox", that.selector).textbox();
			$("#modifyRdtOcpeHtRegstr .easyui-combobox", that.selector).combobox();
		},
		
		bindEvents : function() { 
			var that = this;
			
			//관리이력 이벤트바인딩
			// 저장버튼
			$(".btn_save_RdtOcpeHtRegstr", that.selector).on("click", function() {
				that.save();
				return false;
			});
			// 저장버튼--수정부분
			$(".btn_update_RdtOcpeHtRegstr", that.selector).on("click", function() {
				that.modify();
				return false;
			});
			
			// 취소버튼
			$(".btn_close_RdtOcpeHtRegstr", that.selector).on("click", function() {
				that.close();
			});
			
			// 삭제버튼
			$(".btn_remove_RdtOcpeHtRegstr").on("click", function(){
				$.messager.confirm(that.TITLE, "삭제하시겠습니까?", function(isTrue) {
					if(isTrue) {
						
						that.remove();
					}
				});
				return false;
			});
		},
		
		modify : function(){
			var that = this;
			
			var url = CONTEXT_PATH + "/ocpat/" + that.histNo + "/modifyRdtOcpeHt.do";
			$("form", that.selector).attr("action", url);
			$("form", that.selector).submit();
		},
		
		remove : function(){
			var that = this;
			
			var url = CONTEXT_PATH + "/ocpat/" + that.histNo + "/removeRdtOcpeHt.do";
			
			$.post(url).done(function(result) {
				if(result) {
					
					that.close();
					$.messager.alert(that.TITLE, "변경이력을 삭제하였습니다.");
				}
				else {
					$.messager.alert(that.TITLE, "변경이력 삭제에 실패했습니다.");
				}
			}).fail(function() {
				$.messager.alert(that.TITLE, "변경이력 삭제에 실패했습니다.");
			});
		},

		open : function(histNo){
			var that = this;
			
			that.histNo=histNo;
			
			if(that.selector) {
				that.close();
			}
			var url = CONTEXT_PATH + "/ocpat/" + histNo + "/modifyRdtOcpeHtPage.do";
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
		save : function() {
			var that = this;
			
			var url = CONTEXT_PATH + "/ocpat/addRdtOcpeHt.do";
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

///////////////////////////////////////////이미지다//////////////////////////////////////////

windowObj.ocpatFileRegisterObj ={
		
		/*TITLE : "부속자료 상세정보",
		
		CLASS_NAME : "ocpatFileManageDetail",
		
		selector : null,
		
		ftrCde : null,
		
		ftrIdn : null,
		
		ocpatFileNo : null,
		
		onRemove : null,
		
		onClose : null,
		
		open : function(ocpatFileNo, options) {
			var that = this;

			that.ocpatFileNo = ocpatFileNo;
			
			if(that.selector) {
				that.close();
			}
			
			var url = CONTEXT_PATH + "/ocpat/selectOneFilePage.do";
			var windowOptions = {
				width : 360,
				height : 550,
				onClose : function(){
					that.close();
				}
			};
			
			var title = null;
			if(options && options.title) {
				title = options.title;
			}
			else {
				title = that.TITLE;
			}
			
			var id = windowObj.createWindow(that.CLASS_NAME, title, url, null, windowOptions, function() {
				that.loadData(ocpatFileNo);
			});
			that.selector = "#" + id;
		},
		
		remove : function(ocpatFileNo, fileNo) {
			var that = this;
			
			var url = CONTEXT_PATH + "/ocpat/" + fileNo + "/" + ocpatFileNo + "/deleteFile.do";
			
			$.post(url).done(function(result) {
				if(that.onRemove) {
					that.onRemove();
				}
				that.close();
				alert("부속자료가 삭제되었습니다.");
			}).fail(function(result) {
				alert("부속자료 삭제에 실패했습니다.");
			});
		},
		
		close : function() {
			var that = this;
			if(that.onClose) {
				that.onClose();
			}
			$("#hid_view_photo_manage_image_no", that.selector).val("");
			that.ftrCde = null;
			that.ftrIdn = null;
			that.ocpatFileNo = null;
			that.field = null;
			that.onClose = null;
			windowObj.removeWindow(that.selector);
			that.selector = null;
		}*/
};
windowObj.ocpatFileRegisterObj.modify = {
		
		TITLE : null,
		
		CLASS_NAME : "ocpatFileManageModify",
		
		selector : null,
		
		ocpatFileNo : null,
		
		onClose : null,
		
		initUi : function(ftrIdn,ocpatIdn) {
			var that = this;
			
			$("form", that.selector).ajaxForm({
				dataType : "json",
				beforeSubmit : function(arr, form) {
					for(var i=0, len=arr.length; i < len; i++) {
						var obj = arr[i];
						var name = obj.name;
						
						// 필수 입력 체크
						if(name == "fileCn") {
							if(!obj.value) {
								$.messager.alert(that.TITLE, "제목을 입력하여 주십시오.", null, function() {
									form.find("input[name=" + name + "]").focus();								
								});
								return false;
							}
						}
						else if(name == "lcX") {
							if(!obj.value) {
								$.messager.alert(that.TITLE, "위치를 입력하여 주십시오.", null, function() {
									form.find("input[name=" + name + "]").focus();	
								});
								return false;
							}
						}
						else if(name == "lcY") {
							if(!obj.value) {
								$.messager.alert(that.TITLE, "위치를 입력하여 주십시오.", null, function() {
									form.find("input[name=" + name + "]").focus();
								});
								return false;
							}
						}
						else if(name == "orignlFileNm") {
							if(obj.value) {
								var file = obj.value;
								if(file instanceof String) {
									fileName = file;
								}
								else {
									fileName = file.name;
								}
								var extension = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
								var extensions = ["jpg", "jpeg", "bmp", "gif", "png", "pdf"];
								if(extensions.indexOf(extension) == -1) {
									$.messager.alert(that.TITLE, "다음 확장자만 등록 가능합니다. (" + extensions.join(", ") + ")");
									return false;	
								}
							}
						}
					}
				},
				success : function(result) {
					alert("부속자료가 편집 되었습니다.");
					
					if(that.onClose) {
						that.onClose();
					}
					that.close();
					
					$('#ocpatTable_file tr', that.selector).remove();
					windowObj.ocpatRegisterObj.fileList(ftrIdn, ocpatIdn);
					windowObj.ocpatRegisterObj.selectTr = null;
				}
			});
		},

		bindEvents : function() {
			var that = this;
			
			$("#btn_modify_photo_manage_lcXY", that.selector).on("click", function() {
				that.getLcXY();
				return false;
			});
			
			$(".a_save_modify_file_manage", that.selector).on("click", function() {
				that.modify();
				return false;
			});
			
			$(".a_close_modify_file_manage", that.selector).on("click", function() {
				that.close();
				return false;
			});
		},
		
		getLcXY : function() {
			var that = this;
			
			selectObj.once("photoManageAdd", "Point", "drawend", function(evt) {
				var coords = evt.feature.getGeometry().getCoordinates();
//				var systemCoords = gisObj.toTransformSystem(coords);
				var systemCoords = gisObj.totransformCooridateSystem(coords);
				$("#txt_modify_photo_manage_lc_x", that.selector).val(systemCoords[0]);
				$("#txt_modify_photo_manage_lc_y", that.selector).val(systemCoords[1]);
			}, false);
		},
		
		open : function(ftrIdn, ocpatFileNo, ocpatIdn, onClose, options) {
			var that = this;

			that.ocpatFileNo = ocpatFileNo;
			that.onClose = onClose;
			
			var url = CONTEXT_PATH + "/ocpat/updateFilePage.do";
			var windowOptions = {
				width : 415,
				height : 300,
				onClose : function(){
					that.close();
				}
			};
			
			if(options && options.title) {
				that.TITLE = options.title;
			}
			else {
				that.TITLE = "부속자료 편집";
			}
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
				that.initUi(ftrIdn, ocpatIdn);
				that.bindEvents();
				that.loadData(ocpatFileNo);
			});
			that.selector = "#" + id;
			
		},
		
		loadData : function(ocpatFileNo) {
			var that = this;
			
			that.ocpatFileNo = ocpatFileNo;
			
			var url = CONTEXT_PATH + "/ocpat/" + ocpatFileNo + "/selectOneFile.do";
			
			$.get(url).done(function(result) {
				if(result && result.data) {
					var data = result.data;
					
					$("#txt_modify_file_manage_no", that.selector).val(data.ocpatFileNo);
					$("#txt_modify_file_manage_ftr_idn", that.selector).val(data.ftrIdn);
					
					$("#txt_modify_file_manage_file_no", that.selector).val(data.fileNo);
					
					if(data.ftrCde) {
						var code = domnCodeObj.getCode("KWS-0447", data.ftrCde);
						$("#txt_modify_file_manage_ftr_cde_nam", that.selector).val(code.codeNm);
						$("#txt_modify_file_manage_ftr_cde", that.selector).val(code.codeId);
					}
					else {
						$("#txt_modify_file_manage_ftr_cde_nam", that.selector).val(data.ftrCde);
					}
					
					$("#txt_modify_file_manage_sj", that.selector).val(data.fileSj);
					$("#txt_modify_file_manage_cn", that.selector).val(data.fileCn);
					/*$("#txt_modify_photo_manage_lc_x", that.selector).val(data.lcX);
					$("#txt_modify_photo_manage_lc_y", that.selector).val(data.lcY);*/
				}
				else {
					alert("부속자료를 불러오는데 실패했습니다.");
				}
			}).fail(function(result) {
				alert("부속자료를 불러오는데 실패했습니다.");
			});
			
		},
		
		modify : function() {
			var that = this;
			
			$("form", that.selector).submit();
		},
		
		close : function() {
			var that = this;
			
			$("#txt_modify_file_manage_no", that.selector).val("");
			$("#txt_modify_file_manage_ftr_idn", that.selector).val("");
			$("#txt_modify_file_manage_file_no", that.selector).val("");
			that.fileNo = null;
			that.ftrIdn = null;
			that.ftrCde = null;
			that.field = null;
			that.onClose = null;
			windowObj.removeWindow(that.selector);
			that.selector = null;
		}
};

windowObj.ocpatFileRegisterObj.add = {
		
		TITLE : null,
		
		CLASS_NAME : "ocpatFileManageAdd",
		
		selector : null,

		ftrCde : null,
		
		ftrIdn : null,
		
		onClose : null,
		
		initUi : function(ftrIdn,ocpatIdn) {
			var that = this;
			
			$("form", that.selector).ajaxForm({
				dataType : "json",
				beforeSubmit : function(arr, form) {
					for(var i=0, len=arr.length; i < len; i++) {
						var obj = arr[i];
						var name = obj.name;
						
						// 필수 입력 체크
						if(name == "fileSj") {
							if(!obj.value) {
								$.messager.alert(that.TITLE, "제목을 입력하여 주십시오.", null, function() {
									form.find("input[name=" + name + "]").focus();								
								});
								return false;
							}
						}
						else if(name == "lcX") {
							if(!obj.value) {
								$.messager.alert(that.TITLE, "위치를 입력하여 주십시오.", null, function() {
									form.find("input[name=" + name + "]").focus();	
								});
								return false;
							}
						}
						else if(name == "lcY") {
							if(!obj.value) {
								$.messager.alert(that.TITLE, "위치를 입력하여 주십시오.", null, function() {
									form.find("input[name=" + name + "]").focus();
								});
								return false;
							}
						}
						else if(name == "orignlFileNm") {
							if(obj.value) {
								var file = obj.value;
								if(file instanceof String) {
									fileName = file;
								}
								else {
									fileName = file.name;
								}
								var extension = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
								var extensions = ["jpg", "jpeg", "bmp", "gif", "png", "pdf"];
								if(extensions.indexOf(extension) == -1) {
									$.messager.alert(that.TITLE, "다음 확장자만 등록 가능합니다. (" + extensions.join(", ") + ")");
									return false;	
								}
							}else if(!obj.value) {
								$.messager.alert(that.TITLE, "파일을 등록하여 주십시오.");
								return false;
							}
						}
					}
				},
				success : function(result) {
					
					alert("부속자료가 등록 되었습니다.");
					
					if(that.onClose) {
						that.onClose();
					}
					that.close();
					
					$('#ocpatTable_file tr', that.selector).remove();
					windowObj.ocpatRegisterObj.fileList(ftrIdn, ocpatIdn);
					windowObj.ocpatRegisterObj.selectTr = null;
				}
			});
		},
		
		bindEvents : function() {
			var that = this;
			
			// 사진위치 좌표
			// selectObj.once
			$("#btn_add_photo_manage_lcXY", that.selector).on("click", function() {
				that.getLcXY();
				return false;
			});
			
			// 저장
			$(".a_save_add_file_manage", that.selector).on("click", function() {
				that.add();
				return false;
			});
			
			// 닫기
			$(".a_close_add_file_manage", that.selector).on("click", function() {
				that.close();
				return false;
			});
		},
		
		getLcXY : function() {
			var that = this;
			selectObj.once("photoManageAdd", "Point", "drawend", function(evt) {
				var coords = evt.feature.getGeometry().getCoordinates();
//				var systemCoords = gisObj.toTransformSystem(coords);
				var systemCoords = gisObj.totransformCooridateSystem(coords);
				
				$("#txt_add_photo_manage_lc_x", that.selector).val(systemCoords[0]);
				$("#txt_add_photo_manage_lc_y", that.selector).val(systemCoords[1]);
			}, false);
		},
		
		open : function(ftrIdn, ftrCde, ocpatIdn, onClose, options) {
			var that = this;
			
			that.ftrCde = ftrCde;
			that.ftrIdn = ftrIdn;
			that.onClose = onClose;
			
			var url = CONTEXT_PATH + "/ocpat/insertFilePage.do?nocache=" + Math.random();
			var windowOptions = {
				width : 415,
				height : 300,
				onClose : function(){
					that.close();
				}
			};
			
			if(options && options.title) {
				that.TITLE = options.title;
			}
			else {
				that.TITLE = "부속자료 등록";
			}
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
				that.bindEvents();
				// promise step4
				that.loadData(ftrCde, ftrIdn).done(function() {
					that.initUi(ftrIdn, ocpatIdn);
				});
			});
			that.selector = "#" + id;
		},
		
		loadData : function(ftrCde, ftrIdn) {
			var that = this;
			
			// promise step1
			var deferred = $.Deferred();
			
			that.ftrCde = ftrCde;
			that.ftrIdn = ftrIdn;
			
			// 관리번호
			$("#txt_add_file_manage_ftr_idn").val(ftrIdn);
			
			// 지형지물부호
			if(ftrCde) {
				var code = domnCodeObj.getCode("KWS-0447", ftrCde);
				$("#txt_add_file_manage_ftr_cde_nam", that.selector).val(code.codeNm);
				$("#txt_add_file_manage_ftr_cde", that.selector).val(code.codeId);
			}
			else {
				$("#ftrCde").val(ftrCde);;
			}

			// promise step2
			deferred.resolve();
					
			// promise step3
			return deferred.promise();
		},
		
		add : function() {
			var that = this;
			$("form", that.selector).submit();
		},
		
		close : function() {
			var that = this;
			
			that.ftrCde = null;
			that.ftrIdn = null;
			that.onClose = null;
			windowObj.removeWindow(that.selector);
			that.selector = null;
		}
	};


windowObj.LookDtObj ={
		
		TITLE : "점용대장 편집",
		
		CLASS_NAME : null,
		
		selector : null,
		
		isCreated : false,
		
		init : function() {
			var that = this;
			
			$("form", that.selector).ajaxForm({
				dataType : "json",
				success : function(result) {
					if(result) {
						that.close();
						$.messager.alert(that.TITLE, "점용대장이 저장되었습니다.");
					}
					else {
						$.messager.alert(that.TITLE, "점용대장 저장에 실패하였습니다.");
					}
				}
			});
		},
		
		initUi : function(){
			var that = this;
			
			// easyUi설정
			// easyUi설정
			switch(ocpatIdn) {
			case "100100":
			case "200100":
			case "300100":
			case "400100":
				$("#modifyOcpeDtRegstr .easyui-numberspinner", that.selector).numberspinner();
			    $("#modifyOcpeDtRegstr .easyui-datebox", that.selector).datebox();
			    $("#modifyOcpeDtRegstr .easyui-textbox", that.selector).textbox();
			    $("#modifyOcpeDtRegstr .easyui-combobox", that.selector).combobox();
			break;
			case "500100":
				$("#modifyRdtOccnDtRegstr .easyui-numberspinner", that.selector).numberspinner();
				$("#modifyRdtOccnDtRegstr .easyui-numberbox", that.selector).numberbox();
			    $("#modifyRdtOccnDtRegstr .easyui-datebox", that.selector).datebox();
		    	$("#modifyRdtOccnDtRegstr .easyui-textbox", that.selector).textbox();
			    $("#modifyRdtOccnDtRegstr .easyui-combobox", that.selector).combobox();
			break;
			case "600100":
				$("#modifyRdtOcdsDtRegstr .easyui-numberspinner", that.selector).numberspinner();
		    	$("#modifyRdtOcdsDtRegstr .easyui-datebox", that.selector).datebox();
		    	$("#modifyRdtOcdsDtRegstr .easyui-textbox", that.selector).textbox();
		    	$("#modifyRdtOcdsDtRegstr .easyui-combobox", that.selector).combobox();
				break;
			case "700100":
				$("#modifyRdtOcdsDtRegstr .easyui-numberspinner", that.selector).numberspinner();
			    $("#modifyRdtOcdsDtRegstr .easyui-datebox", that.selector).datebox();
		    	$("#modifyRdtOcdsDtRegstr .easyui-textbox", that.selector).textbox();
		    	$("#modifyRdtOcdsDtRegstr .easyui-combobox", that.selector).combobox();
				break;
			case "800100":
				$("#modifyRdtOccaDtRegstr .easyui-numberspinner", that.selector).numberspinner();
			    $("#modifyRdtOccaDtRegstr .easyui-datebox", that.selector).datebox();
			    $("#modifyRdtOccaDtRegstr .easyui-textbox", that.selector).textbox();
			    $("#modifyRdtOccaDtRegstr .easyui-combobox", that.selector).combobox();
			
				break;
			case "900100":
				$("#modifyRdtOcdrDtRegstr .easyui-numberspinner", that.selector).numberspinner();
			    $("#modifyRdtOcdrDtRegstr .easyui-datebox", that.selector).datebox();
			    $("#modifyRdtOcdrDtRegstr .easyui-textbox", that.selector).textbox();
			    $("#modifyRdtOcdrDtRegstr .easyui-combobox", that.selector).combobox();
			
				break;
			}
			
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
				required : true,
				value : 1,
				min : 1,
				max : 9999,
				increment : 1,
				width : 98
			});
			
			// 부번
			$("#subNum", that.selector).numberspinner({
				required : true,
				value : 0,
				min : 0,
				max : 9999,
				increment : 1,
				width : 98
			});
		},
		
		bindEvents : function() { 
			var that = this;
			
			//편집버튼
			$(".btn_save_RdtOcpatDtRegstr", that.selector).on("click", function() {
				that.modify();
				return false;
			});
			
			// 취소버튼
			$(".btn_close_RdtOcpatDtRegstr", that.selector).on("click", function() {
				that.close();
			});
			
		},
		
		modify : function(){
			var that = this;
			
			var url = null;
			//var selDong = getSelDong();
			//검증완료
//			var selDong = $("#selDong", that.selector).text("getValue");
//			var mainNum = $("#mainNum", that.selector).text("getValue");
//			var subNum = $("#subNum", that.selector).text("getValue");
//			var isMount = $("#checkMauntain", that.selector).is(":checked")?"2":"1";
			var dongCode = $("input[name=selDong]", that.selector).val();
			var mnnm = $("input[name=mainNum]", that.selector).val();
			var slno = $("input[name=subNum]", that.selector).val();
			var mntn = $("input[name=checkMauntain]", that.selector).val();
			
			if(dongCode != "4280000000") {
				if(mnnm == '' && slno != ''){
					$.messager.alert(that.TITLE, "본번을 입력하십시오.");
				} else {
					if(dongCode!='' && mntn == "1" && mnnm!='' && slno!=''){
						var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
					}
					else if(dongCode!='' && mntn == "2" && mnnm!='' && slno!=''){
						var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
					}
					else if(dongCode!='' && mntn == "1" && mnnm!=''){
						var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
						var cutPnuCode = pnuCode.substring(0, 15);
					}
					else if(dongCode!='' && mntn == "2" && mnnm!=''){
						var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
						var cutPnuCode = pnuCode.substring(0, 15);
					}
					else if(dongCode!='' && mntn == "1"){
						var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
						var cutPnuCode = pnuCode.substring(0, 11);
					}
					else if(dongCode!='' && mntn == "2"){
						var pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
						var cutPnuCode = pnuCode.substring(0, 11);
					}
				}
			$("#pnu",that.selector).val(pnuCode);
				
			}
			
			switch(ocpatIdn) {
			case "100100":
			case "200100":
			case "300100":
			case "400100":
				url = CONTEXT_PATH + "/ocpat/"  + that.ftrIdn + "/modifyRdtOcpeDt.do";
				break;
			case "500100":
				url = CONTEXT_PATH + "/ocpat/"  + that.ftrIdn + "/modifyRdtOccnDt.do";
				break;
			case "600100":
				url = CONTEXT_PATH + "/ocpat/"  + that.ftrIdn + "/modifyRdtOcreDt.do";
				break;
			case "700100":
				url = CONTEXT_PATH + "/ocpat/"  + that.ftrIdn + "/modifyRdtOcdsDt.do";
				break;
			case "800100":
				url = CONTEXT_PATH + "/ocpat/"  + that.ftrIdn + "/modifyRdtOccaDt.do";
				break;
			case "900100":
				url = CONTEXT_PATH + "/ocpat/"  + that.ftrIdn + "/modifyRdtOcdrDt.do";
				break;
			}
			$("form", that.selector).attr("action", url, pnu);
			$("form", that.selector).submit();
		},
		
		remove : function(ftrIdn,ocpatIdn){
			var that = this;
			
			that.ftrIdn = ftrIdn;
			
			var url = null;
			$.messager.confirm(that.TITLE, "삭제하시겠습니까?", function(isTrue) {
				if(isTrue) {
					
					switch(ocpatIdn) {
					case "100100":
					case "200100":
					case "300100":
					case "400100":
						url = CONTEXT_PATH + "/ocpat/"  + that.ftrIdn + "/removeRdtOcpeDt.do";
						break;
					case "500100":
						url = CONTEXT_PATH + "/ocpat/"  + that.ftrIdn + "/removeRdtOccnDt.do";
						break;
					case "600100":
						url = CONTEXT_PATH + "/ocpat/"  + that.ftrIdn + "/removeRdtOcreDt.do";
						break;
					case "700100":
						url = CONTEXT_PATH + "/ocpat/"  + that.ftrIdn + "/removeRdtOcdsDt.do";
						break;
					case "800100":
						url = CONTEXT_PATH + "/ocpat/"  + that.ftrIdn + "/removeRdtOccaDt.do";
						break;
					case "900100":
						url = CONTEXT_PATH + "/ocpat/"  + that.ftrIdn + "/removeRdtOcdrDt.do";
						break;
					}
					
					$.post(url).done(function(result) {
						if(result) {
							
							that.close();
							$.messager.alert(that.TITLE, "점용대장을 삭제하였습니다.");
						}
						else {
							$.messager.alert(that.TITLE, "점용대장 삭제에 실패했습니다.");
						}
					}).fail(function() {
						$.messager.alert(that.TITLE, "점용대장 삭제에 실패했습니다.");
					});
				};
			});
		},

		open : function(ftrIdn,pnu){
			var that = this;
			
			that.ftrIdn=ftrIdn;
			that.ocpatIdn=ocpatIdn;
			that.pnu=pnu;
			
			if(that.selector) {
				that.close();
			}
			
			var url = null;
			var windowOptions = null;
			
			switch(ocpatIdn) {
			case "100100":
			case "200100":
			case "300100":
			case "400100":
				CLASS_NAME = "modifyOcpeDtRegstr";
				url = CONTEXT_PATH + "/ocpat/"  + ftrIdn + "/modifyRdtOcpeDtPage.do";
				windowOptions = {
						width : 680,
						height : 531,
						top : 120,
						left : 550,
						onClose : function() {
							that.close();
						}
					};
				break;
			case "500100":
				CLASS_NAME = "modifyRdtOccnDtRegstr";
				url = CONTEXT_PATH + "/ocpat/"  + ftrIdn + "/modifyRdtOccnDtPage.do";
				windowOptions = {
						width : 680,
						height : 566,
						top : 120,
						left : 550,
						onClose : function() {
							that.close();
						}
					};
				break;
			case "600100":
				CLASS_NAME = "modifyRdtOcreDtRegstr";
				url = CONTEXT_PATH + "/ocpat/"  + ftrIdn + "/modifyRdtOcreDtPage.do";
				windowOptions = {
						width : 680,
						height : 531,
						top : 120,
						left : 550,
						onClose : function() {
							that.close();
						}
					};
				break;
			case "700100":
				CLASS_NAME = "modifyRdtOcdsDtRegstr";
				url = CONTEXT_PATH + "/ocpat/"  + ftrIdn + "/modifyRdtOcdsDtPage.do";
				windowOptions = {
						width : 680,
						height : 531,
						top : 120,
						left : 550,
						onClose : function() {
							that.close();
						}
					};
				break;
			case "800100":
				CLASS_NAME = "modifyRdtOccaDtRegstr";
				url = CONTEXT_PATH + "/ocpat/"  + ftrIdn + "/modifyRdtOccaDtPage.do";
				windowOptions = {
						width : 680,
						height : 531,
						top : 120,
						left : 550,
						onClose : function() {
							that.close();
						}
					};
				break;
			case "900100":
				CLASS_NAME = "modifyRdtOcdrDtRegstr";
				url = CONTEXT_PATH + "/ocpat/"  + ftrIdn + "/modifyRdtOcdrDtPage.do";
				windowOptions = {
						width : 680,
						height : 531,
						top : 120,
						left : 550,
						onClose : function() {
							that.close();
						}
					};
				break;
			}
			
				
				var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
					if(!that.isCreated) {
						that.init();
						that.initUi();
						that.bindEvents();
						that.loadPnuData(pnu);
						that.isCreated = true;
					}
				});
				
				that.selector = "#" + id;
		},
		
		loadPnuData : function(pnu){
			var that= this;
			
			var selDong = pnu.substring(0,10);
			//var data = dongObj.getDongName(selDong);
			//PNU 일반/산 찾기
			var isMount = pnu.substring(10,11);
			//PNU 지번
			var mainNum = pnu.substring(11,15);
			//PNU 부번
			var subNum = pnu.substring(15,19);
			
			$("#selDong", that.selector).combobox('setValue', selDong);
			if (isMount==1){
				$("#checkMauntain", that.selector).switchbutton('uncheck');
			}
			else if(isMount==2){
				$("#checkMauntain", that.selector).switchbutton('check');
			}
			$("#mainNum", that.selector).numberspinner('setValue',mainNum);
			$("#subNum", that.selector).numberspinner('setValue',subNum);
			
		},

		close : function() {
			var that = this;
			
			windowObj.removeWindow(that.selector);
			that.selector = null;
			that.isCreated = false;
		}
};