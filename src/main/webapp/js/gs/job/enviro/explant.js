/**
 * 현지조사표 검색 객체
 * @type {Object}
 */
enviroObj = {

	TITLE : "현지조사표 검색",

	CLASS_NAME : "ExplantAsSearch",

	selector : null,

	isCreated : false,
	
	initUi : function() {
		var that = this;
		
		// easyUi설정
		$("#searchExplant .easyui-datebox", that.selector).datebox();
		$("#searchExplant .easyui-textbox", that.selector).textbox();
		$("#searchExplant .easyui-combobox", that.selector).combobox();
		$("#searchExplant .easyui-numberspinner", that.selector).numberspinner();
		
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

		selDong = $('#selDong', that.selector).combobox('getValue');;
		checkMauntain = $("#checkMauntain", that.selector).switchbutton("options").checked?'2':'1';;
		mainNum = $('#mainNum', that.selector).numberspinner('getValue');
		subNum = $('#subNum', that.selector).numberspinner('getValue');
		
		var pnu = pnuObj.createPnu(selDong,checkMauntain,mainNum,subNum);
		
		var params = {};
		params.pnu = pnu;
		params.dataIds = ["EXPLANT_AS"];
		var url = CONTEXT_PATH + "/enviro/searchExplant.do";
		$.post(url, params).done(function(response) {
            if(response && response.result){
            	var rows = response.result;
			    if(rows.length > 0){
			    	resultObj.open(response.result);
			    }else{
			    	alert("현지조사표 검색결과가 없습니다.");
					return false;
			    }
            }

		}).fail(function() {
			alert("현지조사표 검색에 실패하였습니다");
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
	}

};

enviroObj.registerObj={
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "현지조사표 조회",
	
	/**
	 * 클래스 명
	 * 
	 * @type {String}
	 */
	CLASS_NAME : "EnviroRegister",
	
	/**
	 * 선택자
	 * 
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * KWS_DATA 정보
	 * 
	 * @type {Array.<Object>}
	 */
	data : null,
	
	/**
	 * 데이터 아이디
	 * 
	 * @type {String{
	 */
	dataId : null,
	
	/**
	 * 객체 아이디 목록
	 * 
	 * @type {Array.<Number>}
	 */
	fids : [],
	
	ftrIdn : null,
	
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
	 * 제목
	 * @type {String}
	 */
	title : null,

	/**
	 * 클래스 명
	 * 
	 * @type {String}
	 */
	CLASS_NAME : "Register",

	/**
	 * 선택자
	 * 
	 * @type {String}
	 */
	selector : null,

	/**
	 * KWS_DATA 정보
	 * 
	 * @type {Array.<Object>}
	 */
	data : null,

	/**
	 * 데이터 아이디
	 * 
	 * @type {String{
	 */
	dataId : null,

	/**
	 * 객체 아이디 목록
	 * 
	 * @type {Array.<Number>}
	 */
	fids : [],
	
	ftrIdn : null,

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
	
	open : function(fids,ftrIdn){
		
		var that = this;
		
		if (that.selector) {
			that.close();
		}
		that.dataId = "EXPLANT_AS";
		that.objectId= fids;
		that.ftrIdn = ftrIdn;
		
		var url = CONTEXT_PATH + "/enviro/" + fids + "/viewExplant.do";
		var windowOptions = {
			width : 650,
			top : 120,
			height : 580,
			left : 330,
			isClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
				that.initUi();
				that.isCreated = true;
				that.bindEvents();
				that.loadReant();
				that.loadPhoto();
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
	
	loadReant : function(){
		var that = this;
		var url = CONTEXT_PATH + "/enviro/" + that.ftrIdn + "/searchReantList.do";
		$.get(url).done(function(response){
			var trTag ='<tr><th width="8%">순번</th><th width="62%">작업내용</th><th width="30%">작업날짜</th></tr>';
			
			for(var i=0; i<response.result.length; i++){
				var rows = response.result[i];
				trTag += '<tr data-idn="' + rows.ftrIdn + '" class="selectableRow">';
				trTag += '<td>' + (i+1) +'</td>';
				trTag += '<td>' + rows.jobNote +'</td>';
				trTag += '<td>' + rows.jobDate +'</td>';
				trTag += '</tr>';
			}
			
			$('.reantList', that.selector).html(trTag);
			
			// 클릭 이벤트 설정
			$(".reantList tr", that.selector).click(function() {
				var element = $(this);
				var idn = element.attr("data-idn");
				
				if(idn && idn.length > 0) {
					$(".tbodyConstruction tr", that.selecor).removeClass("tr_select");
					$(this, that.selector).addClass("tr_select");
					
					reantObj.registerObj.open(idn);
				}
			});
		});
	},
	
	loadPhoto : function(){
		var that = this;
		
		var url = CONTEXT_PATH + "/enviro/" + that.ftrIdn + "/searchPhotoList.do";
		$.get(url).done(function(response){
			var trTag ='<tr><th width="8%">순번</th><th width="62%">사진제목</th><th width="30%">등록날짜</th></tr>';
			
			for(var i=0; i<response.result.length; i++){
				var rows = response.result[i];
				trTag += '<tr data-idn="' + rows.imageNo + '" class="selectableRow">';
				trTag += '<td>' + (i+1) +'</td>';
				trTag += '<td>' + rows.imageCn +'</td>';
				trTag += '<td>' + rows.lastUpdde +'</td>';
				trTag += '</tr>';
			}
			
			$('.photoList', that.selector).html(trTag);
			
			// 클릭 이벤트 설정
			$(".photoList tr", that.selector).click(function() {
				var element = $(this);
				var idn = element.attr("data-idn");
				
				if(idn && idn.length > 0) {
					$(".tbodyConstruction tr", that.selecor).removeClass("tr_select");
					$(this, that.selector).addClass("tr_select");
					
					enviroObj.photoObj.open(that, idn);
				}
			});
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		//대장출력
		$(".btn_prt", that.selector).on("click", function() {
			that.print(that.ftrIdn);
			return false;
		});
		
		// 닫기버튼
		$(".btn_cls", that.selector).on("click", function() {
			that.close();
			return false;
		});
		
		// 편집버튼
		$(".btn_edi", that.selector).on("click", function() {
			that.modify();
			return false;
		});
		
		// 삭제버튼
		$(".btn_rem", that.selector).on("click", function() {
			that.remove();
			return false;
		});
		
		//이미지등록
		$(".btn_add_photo", that.selector).on("click", function() {
			that.addPhoto();
			return false;
		});

	},
	
	addPhoto : function() {
		var that = this;
		
		enviroObj.photoAddObj.open(that, that.dataId, that.ftrIdn);
	},
	
	print : function(ids){
		
		enviroObj.printObj.open(ids);
	},
	
	modify : function(){
		var that = this;
		var pnu = $('#pnu').val();
		enviroObj.modifyObj.open(that.dataId, that.objectId,pnu);
		that.close();
	},
	
	remove : function(){
		var that = this;
		
		$.messager.confirm(that.TITLE, "현지조사표 정보를 삭제하시겠습니까?", function(isTrue) {
			if(isTrue) {
				var url = CONTEXT_PATH + "/enviro/" + that.objectId + "/removeExplant.do";
				
				$.get(url).done(function(result) {
					if(result.type == 'del' && result.state >= 1) {
						$.messager.alert({
							title : that.TITLE,
							msg : "현지조사표 정보를 삭제하였습니다.",
							fn : function() {
								that.close();
							}
						});
					}
					else {
						$.messager.alert(that.TITLE, "현지조사표 삭제에 실패했습니다.");
					}
				}).fail(function() {
					$.messager.alert(that.TITLE, "현지조사표 삭제에 실패했습니다.");
				});
			}
		});
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.dataId = null;
		that.objectId = null;
		that.selector = null;
		that.isCreated = false;
	},
};

enviroObj.photoObj={
		
	TITLE : "사진 편집",
		
	CLASS_NAME : "ModifyPhoto",
		
	selector : null,

	/**
	 * 부모 창
	 */
	parent : null,
		
	/**
	* 사진 고유번호
	*/
	imageNo : null,
		
		
	open : function(parent, imageNo) {
		var that = this;
			
		if(that.selector) {
			that.close();
		}
			
		that.parent = parent;
		that.imageNo = imageNo;
			
		var windowOptions = {
			width : 425,
			top : 120,
			isClose : function() {
				that.close();
			}
		};
				
		var url = CONTEXT_PATH + "/enviro/" + imageNo + "/modifyExplantPhoto.do";
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.init();
			that.initUi();
			that.bindEvents();
			that.loadImage();
		});
		
		that.selector = "#" + id;
	},
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			beforeSubmit : function(arr, form) {
				for(var i=0, len=arr.length; i < len; i++) {
					var obj = arr[i];
					var name = obj.name;
					
					// 필수 입력 체크
					if(name == "imageCn") {
						if(!obj.value) {
							$.messager.alert(that.TITLE, "제목을 입력하여 주십시오.", null, function() {
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
							var extensions = ["jpg", "jpeg", "bmp", "gif", "png"];
							if(extensions.indexOf(extension) == -1) {
								$.messager.alert(that.TITLE, "다음 확장자만 등록 가능합니다. (" + extensions.join(", ") + ")");
								return false;	
							}
						}
					}
				}
			},
			success : function(result) {
				if(result.data) {
					$.messager.alert(that.TITLE, "사진 등록에 성공하였습니다.");
					that.close();
				}
				else {
					$.messager.alert(that.TITLE, "사진 등록에 실패하였습니다.");
				}
			}
		});
	},
	
	bindEvents : function() {
		var that = this;

		// 저장버튼
		$("#btn_update", that.selector).on("click", function() {
			that.modify();
			return false;
		});

		// 삭제버튼
		$("#btn_delete", that.selector).on("click", function() {
			that.remove();
			return false;
		});
		
		// 닫기버튼
		$("#btn_close", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	initUi : function(){
		var that = this;
		
		$(".easyui-textbox", that.selector).textbox();
	},	
	
	/**
	 * 사진 로딩
	 */
	loadImage : function() {
		var that = this;

		var fileNo = $("input[name=fileNo]", that.selector).val();
		
		var imgDownloadUrl = CONTEXT_PATH + "/enviro/" + fileNo + "/download.do";
		var thumbnailUrl = CONTEXT_PATH + "/cmmn/image/" + that.imageNo + "/thumbnail.do?nocache=" + Math.random();
		$("#img_view_photo_manage_thumbnail", that.selector).attr("src", thumbnailUrl);
		
		var imageViewUrl = CONTEXT_PATH + "/popup/image/view.do?imageFileNo=" + fileNo;
		$("#img_view_photo_manage_thumbnail", that.selector).parent().attr("href", imageViewUrl);

		$(".a_down_view_photo", that.selector).attr("href", imgDownloadUrl);
	},
	
	/**
	 * 저장
	 */
	modify : function() {
		var that = this;
		$("form", that.selector).submit();
	},

	/**
	 * 삭제
	 */
	remove : function() {
		var that = this;
		
		$.messager.confirm(that.TITLE, "사진을 삭제하시겠습니까?", function(isTrue) {
			if(isTrue) {
				var url = CONTEXT_PATH + "/enviro/" + that.imageNo + "/photoRemove.do";
				
				$.get(url).done(function() {
					$.messager.alert({
						title : that.TITLE,
						msg : "사진을 삭제하였습니다.",
						fn : function() {
							that.close();
						}
					});
				}).fail(function() {
					$.messager.alert(that.TITLE, "사진 삭제에 실패했습니다.");
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
		that.parent.loadPhoto();
		that.imageNo = null;
		that.selector = null;
	}	
};

enviroObj.printObj={

	selector : null,
		
	TITLE : "현지조사표 출력",
		
	CLASS_NAME : "ExplantDocument",
			
	idn : null,
		
	properties : null,
	
	open : function(fids){
		var that = this;
		
		if (that.selector) {
			that.close();
		}
		that.ftrIdn = fids;
		var url = CONTEXT_PATH + "/enviro/" + fids + "/printExplant.do";
		var windowOptions = {
			left : 650,
			top : 0,
			width : 500,
			height : "80%",
			inline : true,
			draggable : true,
			isClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
				that.initUi();
				that.bindEvents();
				that.loadImage();
		});
		
		that.selector = "#" + id;
		
	},
	
	bindEvents : function() {
		var that = this;

		$(".a_print", that.selector).linkbutton("enable");
		
	},
	
	initUi : function() {
		var that = this;
		
		// 대장출력
		$(".a_print", that.selector).linkbutton({
			iconCls : "icon-print",
			disabled : true,
			onClick : function() {
				that.print();
			}
		});
	},
	
	loadImage : function(){
		var that = this;
		
		var ftrCde = "EX001";
		var ftrIdn = that.ftrIdn;
		
		url = CONTEXT_PATH + "/photoManage/" + ftrCde + "/" + ftrIdn + "/list.do";
		$.get(url).done(function(response) {
			var rows = response.rows;
			if (rows.length != 0) {
				var size = rows.length;
				var idx = size - 6;
				if (idx < 0)
					idx = 0;
				
				var imgDownloadUrl = CONTEXT_PATH + "/cmmn/file/" + rows[idx].imageFileNo + "/download.do";
				$("#imageSj1", that.selector).text(rows[idx].imageSj);
				$("#imageFilNo1").html("<div class='div_img' ><img class='img_style' src='" + imgDownloadUrl + "' /></div>");
				idx++;

				if (idx < size) {
					$("#imageSj2", that.selector).text(rows[idx].imageSj);
					var imgDownloadUrl = CONTEXT_PATH + "/cmmn/file/" + rows[idx].imageFileNo + "/download.do";
					$("#imageFilNo2").html("<div class='div_img' ><img class='img_style' src='"	+ imgDownloadUrl + "' /></div>");
					idx++;
				}

				if (idx < size) {
					$("#imageSj3", that.selector).text(rows[idx].imageSj);
					var imgDownloadUrl = CONTEXT_PATH + "/cmmn/file/" + rows[idx].imageFileNo + "/download.do";
					$("#imageFilNo3").html("<div class='div_img' ><img class='img_style' src='" + imgDownloadUrl + "' /></div>");
					idx++;
				}

				if (idx < size) {
					$("#imageSj4", that.selector).text(rows[idx].imageSj);
					var imgDownloadUrl = CONTEXT_PATH + "/cmmn/file/" + rows[idx].imageFileNo + "/download.do";
					$("#imageFilNo4").html("<div class='div_img' ><img class='img_style' src='" + imgDownloadUrl + "' /></div>");
					idx++;
				}
			}
		});
	},
	
	print : function() {
		
		jqueryUtils.setIsLoding(false);
		$(".div_loading").show();
		
		jQuery.get('../css/gs/common/printcontainer.css', function (printcss) {
			var printContainer = printcss;

			jQuery.get('../css/gs/common/explantprint.css', function (explantcss) {
				var explantPrint = explantcss;
				
				var win = window.open("","_blank","");
				win.document.open();

				win.document.write('<html><head><meta charset="UTF-8"/>');
				win.document.write('<title></title><script type="text/javascript">');
				win.document.write('$(function() {');
				win.document.write('location.reload(true);');
				win.document.write('});');
				win.document.write('</script><style>');
				win.document.write(printContainer); 
				win.document.write(explantPrint);
				win.document.write('</style></head><body onload="step1()" style="width:95%; height:95%; page-break-before:always; ">');
				
				var divContent = document.getElementById("explant_doc").innerHTML;
				
				win.document.write(divContent);
		 		win.document.write('</body></html>');
				win.document.close();
				
				setTimeout(function() {
					win.print(); 
					win.close();}, 1000);
			});		
		});
		
		jqueryUtils.setIsLoding(true);
		$(".div_loading").hide();
	},
	
	close : function() {
		var that = this;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	},
};

enviroObj.modifyObj={
		
	TITLE : "현지조사표 편집",
	
	CLASS_NAME : "EnviroModify",
	
	selector : null,

	/**
	 * 데이터아이디
	 */
	
	dataId : null,
	
	/**
	 * 관리번호
	 */
	ftrIdn : null,
	
	open : function(dataId,objectId,pnu){
		
		var that = this;
			that.objectId = objectId;
			that.pnu = pnu;
			// 이전 창 닫기
			if(that.selector) {
				that.close();
			}
			
			var windowOptions = {
				width : 680,
				height : 325,
				top : 120,
				left : 550,
				onClose : function() {
					that.close();
				}
			};
				
			var url = CONTEXT_PATH + "/enviro/" + objectId + "/modifyPageExplant.do";
		
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
				that.init();
				that.initUi();
				that.bindEvents();
			});
			
			that.selector = "#" + id;
	},
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result.state >= 1) {
					that.close();
					$.messager.alert(that.TITLE, "현지조사표가 변경되었습니다.");
				}
				else {
					$.messager.alert(that.TITLE, "현지조사표 편집에 실패하였습니다.");
				}
			}
		});
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
	
	initUi : function(){
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
		
		var pnu = that.pnu;	// 기점
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
	
	modify : function() {
		var that = this;
		
		var selDong = $("#selDong", that.selector).combobox("getValue");
		var checkMauntain = $('input[name=checkMauntain]').val();
		var mainNum = $("#mainNum", that.selector).textbox("getValue");
		var subNum = $("#subNum", that.selector).textbox("getValue");
		var pnu = pnuObj.createPnu(selDong, checkMauntain, mainNum, subNum);
		if (selDong && pnu.length == 19){
			$("input[name=pnu]", that.selector).val(pnu);
		}
		else{
			$("input[name=pnu]", that.selector).val("");
		}
		var url = CONTEXT_PATH + "/enviro/" + that.objectId + "/modifyExplant.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		enviroObj.registerObj.open(that.objectId);
		that.dataId = null;
		that.objectId = null;
		that.selector = null;
	}
	
};

enviroObj.photoAddObj = {

	TITLE : "사진 등록",
	
	CLASS_NAME : "AddPhoto",
	
	selector : null,
	
	/**
	 * 부모 창
	 */
	parent : null,
	
	/**
	 * 데이터아이디
	 */
	dataId : null,
	
	/**
	 * 지형지물부호
	 */
	ftrCde : null,
	
	/**
	 * 관리번호
	 */
	ftrIdn : null,
	
	/**
	 * 이미지 타입
	 */
	imageTy : null,
	
		
	open : function(parent, dataId, ftrIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		dataId = 'EXPLANT_AS';
		that.parent = parent;
		that.dataId = dataId;
		that.ftrIdn = ftrIdn;
		
		var windowOptions = {
			width : 415,
			top : 300,
			onClose : function() {
				that.close();
			}
		};
			
		var url = CONTEXT_PATH + "/enviro/" + dataId + "/" + ftrIdn + "/addExplantPhoto.do";
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.init();
			that.initUi();
			that.bindEvents();
		});
		
		that.selector = "#" + id;
	},
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			beforeSubmit : function(arr, form) {
				for(var i=0, len=arr.length; i < len; i++) {
					var obj = arr[i];
					var name = obj.name;
					
					// 필수 입력 체크
					if(name == "imageCn") {
						if(!obj.value) {
							$.messager.alert(that.TITLE, "제목을 입력하여 주십시오.", null, function() {
								form.find("input[name=" + name + "]").focus();								
							});
							return false;
						}
					}
					else if(name == "orignlFileNm") {
						if(!obj.value) {
							$.messager.alert(that.TITLE, "사진을 등록하여 주십시오.");
							return false;
						}
						var file = obj.value;
						if(file instanceof String) {
							fileName = file;
						}
						else {
							fileName = file.name;
						}
						var extension = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
						var extensions = ["jpg", "jpeg", "bmp", "gif", "png"];
						if(extensions.indexOf(extension) == -1) {
							$.messager.alert(that.TITLE, "다음 확장자만 등록 가능합니다. (" + extensions.join(", ") + ")");
							return false;	
						}
					}
				}
			},
			success : function(result) {
				if(result.data) {
					$.messager.alert(that.TITLE, "사진 등록에 성공하였습니다.");
					that.close();
				}
				else {
					$.messager.alert(that.TITLE, "사진 등록에 실패하였습니다.");
				}
			}
		});
	},
	initUi : function(){
		var that = this;
		
		that.ftrCde = $("input[name=ftrCde]", that.selector).val();
		that.imageTy = $("input[name=imageTy]", that.selector).val();
		$("#addPhoto .easyui-combobox", that.selector).combobox();
		$(".easyui-textbox", that.selector).textbox();
	},	
	
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
	
	/**
	 * 등록
	 */
	insert : function() {
		var that = this;
//		$("form", that.selector).submit();
		$.ajax({
			url : CONTEXT_PATH + "/enviro/" + that.ftrIdn + "/searchPhotoList.do",
			async : false,
			type : "GET",
			success : function(data) {
				var rows = null;
				var image = $('#imageSj', that.selector).combobox('getValue');
				//0개면 그냥저장
				if (data.result.length<4){
					if(image=="EX000"){
						for(var i=0; i<data.result.length; i++){
							rows = data.result[i];
							if(rows.imageSj==image){
								$.messager.alert(that.TITLE, "약도 사진은 한장만 등록이 가능합니다.");
								return false;
							}else{
								$("form", that.selector).submit();
								return false;
							}
						}
					}
					$("form", that.selector).submit();
					return false;
				}else{
					$.messager.alert(that.TITLE, "최대 4장까지 등록이 가능합니다.");
					return false;
//					$("form", that.selector).submit();
				}
			},
			error : function(){
			}
		});
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.parent.loadPhoto(that.ftrIdn);
		that.dataId = null;
		that.ftrCde = null;
		that.ftrIdn = null;
		that.imageTy = null;
		that.selector = null;
	}	
};