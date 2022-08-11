windowObj.policyRegisterObj = {

		/**
		* 제목
		* @type {String}
		*/
		TITLE : "정책현황",

		/**
		 * 클래스 명
		 * 
		 * @type {String}
		 */
		CLASS_NAME : "PolicyRegister",

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
		/**
		 * 상세 정보 데이터 그리드 옵션 목록
		 * 
		 * @type {Object}
		 */
		gridOptions : {},

		
		open : function(info) {
			var that = this;
			
			var data =  info;
			var TITLE = that.TITLE;
			var ftrIdn = data.ftrIdn;
			var ftrCde =  "PA000";
			that.ftrCde = ftrCde;
			that.ftrIdn = ftrIdn;
			
			if(ftrIdn==null){
				ftrIdn = info.id;
				that.ftrIdn = ftrIdn;
			}

			if (that.selector) {
				that.close();
			}
			
			var url = CONTEXT_PATH + "/policy/" + ftrIdn + "/viewPolicy.do";
			var windowOptions = {
				width : 750,
				top : 120,
				height : 730,
				left : 330,
				isClose : function() {
					that.close();
				}
			};
			
			var id = windowObj.createWindow(that.CLASS_NAME, TITLE, url, null, windowOptions, function() {
					that.initUi();
					that.isCreated = true;
					that.bindEvents();
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
		
		bindEvents : function() {
			var that = this;
			var ftrIdn = that.ftrIdn;
//			var ftrCde = that.ftrCde;
			
			//대장출력
			$(".btn_pnt_PlcyStatAsRegstr", that.selector).on("click", function() {
				that.policyPrintOne();
			});
			
			// 닫기버튼
			$(".btn_close_PlcyStatAsRegstr", that.selector).on("click", function() {
				that.close();
			});
			
			// 편집버튼
			$(".btn_modify_PlcyStatAsRegstr", that.selector).on("click", function() {
				that.modify(ftrIdn);
			});
			
			// 삭제버튼
			$(".btn_remove_PlcyStatAsRegstr", that.selector).on("click", function() {
				that.remove(ftrIdn);
				return false;
			});
			
			//이미지등록
			$(".btn_add_KwsImagePolicyRegstr", that.selector).on("click", function() {
				
				that.addPhoto();
				return false;
			});

		},
		
//		loadAdr : function(){
//			var that = this;
//			var url = CONTEXT_PATH + "/policy/" + that.ftrIdn + "/plcyAdrSearch.do";
//			$.get(url).done(function(response){
//				var plcyAdr = response.result;
//				
//				//PNU 10자리 읍면동 / 1 산여부 / 메인지번 4 / 부번4
//				var pnu = plcyAdr;
//				//PNU 동찾기
//				var selDong = pnu.substring(0,10);
//				var data = dongObj.getDongName(selDong);
//				$("#selDong", that.selector).text(data);
//				
//				//PNU 일반/산 찾기
//				var isMount = pnu.substring(10,11);
//				if (isMount==2){
//				$("#isMount", that.selector).text("산");
//				}
//				
//				//PNU 지번
//				var mainNum = pnu.substring(11,15);
//				$("#mainNum", that.selector).text(mainNum);
//				
//				//PNU 부번
//				var subNum = pnu.substring(15,19);
//				$("#subNum", that.selector).text(subNum);
//				
//			});
//		},
		
		addPhoto : function() {
			var that = this;
			
			windowObj.policyRegisterObj.photoAdd.open(that, that.dataId, that.ftrIdn);
		},
		
		modify : function(ftrIdn) {
//			var that = this;
			windowObj.policyRegisterObj.editPlcy.open(ftrIdn);
		},
		
		loadPhoto : function() {
			var that = this;
			
			var url = CONTEXT_PATH + "/policy/" + that.ftrCde + "/" + that.ftrIdn + "/listPhoto.do";
			$.get(url).done(function(response){
				var trTag = '<tr><th width="8%">순번</th><th>제목</th><th>등록일</th></tr>';
				var rows = response.rows;
				
				for(var i=0; i<rows.length;i++){
					var row = rows[i];
					trTag += '<tr data-no="' + row.imageNo + '" class="selectableRow">';
					trTag += '<td>' + (i+1) +'</td>';
					trTag += '<td>' + row.imageCn + '</td>';
					trTag += '<td>' + row.lastUpdde + '</td>';
					trTag += '</tr>';
				}
				$('.tbodyImage', that.selector).html(trTag);
				
				// 클릭 이벤트 설정
				$(".tbodyImage tr", that.selector).click(function() {
					var element = $(this);
					var no = element.attr("data-no");
					
					if(no && no.length > 0) {
						$(".tbodyImage tr", that.selecor).removeClass("tr_select");
						$(this, that.selector).addClass("tr_select");
						
						windowObj.policyRegisterObj.photoModify.open(that, no);
					}
				});
			});
		},

		remove : function(ftrIdn){
			var that = this;
			
			that.ftrIdn = ftrIdn;
			
			url = CONTEXT_PATH + "/policy/"  + that.ftrIdn + "/removePlcyStatAs.do";
					
			$.post(url).done(function(result) {
				if(result) {				
					$.messager.alert("정책지도", "정책 지도 대장을 삭제하였습니다");
					
					// 이승재, 2020.12.08
					// 삭제 후 검색 목록 혀황 창 갱신
					policyRegResultObj.refreshResultLayersGrid();
					//policyRegResultObj.createDetail();
					// 지도갱신
					mapObj.reloadWMS();
					// 하이라이트 삭제
					highlightObj.removeFeatures();
					that.close();
				}
				else {
					$.messager.alert("정책지도", "정책 지도 대장 삭제에 실패했습니다.");
				}
			}).fail(function() {
				$.messager.alert("정책지도", "정책 지도 대장 삭제에 실패했습니다.");
			});
		},
		
		policyPrintOne : function(){
			var that = this;
			
			var map = mapObj.getMap();
			
			var center = map.getCenter();
			var point = new ol.geom.Point(center);
			
			var from = map.getView().getProjection();
			var to = ol.proj.get("EPSG:4326");
			
			var transformPoint = spatialUtils.transform(point, from, to);
			var transformCenter = transformPoint.getCoordinates();

			map.once("postcompose", function(event) {
				if(map.tileQueue_.isEmpty()) {
					var canvas = event.context.canvas;
					var url = CONTEXT_PATH + "/policy/" + that.ftrIdn + "/capture.do";
					var data = canvas.toDataURL("image/jpeg");
					var params = {
						exportDtaSe : "OPEN_LIMITATION",
						exportResn : "정책지도 위치도",
						centerLon : transformCenter[0],
						centerLat : transformCenter[1],
						data : data,
						viewId : backgroundMapObj.getService(),
						tmsType : backgroundMapObj.getType()
					};
					
					$.post(url, params).done(function(result) {
						if(result && result.image) {
							var ftrIdn = result.ftrIdn;
							var fileName = result.image;
							var ftrCde = that.ftrCde;
							
							var url = CONTEXT_PATH + "/clipreport/plcyStatAspnt.do?fileName=" + fileName + "&ftrIdn=" + ftrIdn + "&ftrCde=" + ftrCde;
				
							popupUtils.open(url, "정책현황", { width : 900 , height : 800, left : 300, top:150 });

						}
						else {
							$.messager.alert(that.TITLE, "위치도 생성에 실패하였습니다.");
						}
					}).fail(function() {
						$.messager.alert(that.TITLE, "위치도 생성에 실패하였습니다.");
					});
				}
				else {
					$.messager.alert("대장출력", "지도 로딩 중에는 사용할 수 없습니다.");
				}
			});
			map.renderSync();
			
		},
		
		close : function() {
			var that = this;
			windowObj.removeWindow(that.selector);
			that.selector = null;
			that.isCreated = false;
		}
		
};

windowObj.policyRegisterObj.photoAdd = {

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
			
			dataId = 'PLCY_STAT_AS';
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
				
			var url = CONTEXT_PATH + "/policy/" + dataId + "/" + ftrIdn + "/addPhoto.do";
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
			$("form", that.selector).submit();
		},
		
		/**
		 * 닫기
		 */
		close : function() {
			var that = this;
			
			windowObj.removeWindow(that.selector);
			that.parent.loadPhoto();
			that.dataId = null;
			that.ftrCde = null;
			that.ftrIdn = null;
			that.imageTy = null;
			that.selector = null;
		}	
	};

windowObj.policyRegisterObj.editPlcy = {
		/**
		* 제목
		* @type {String}
		*/
		TITLE : "정책지도 편집",

		/**
		 * 클래스 명
		 * 
		 * @type {String}
		 */
		CLASS_NAME : "PolicyEditReg",

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
		/**
		 * 상세 정보 데이터 그리드 옵션 목록
		 * 
		 * @type {Object}
		 */
		gridOptions : {},

		init : function() {
			var that = this;
			
			$("form", that.selector).ajaxForm({
				dataType : "json",
				success : function(result) {
					if(result) {
						var url = CONTEXT_PATH + "/policy/"+ that.ftrIdn +"/updateplcyRepo.do";
						var params ={
							//plcyTit : that.plcyTit,
							repoDoc : that.repoDoc,
							ftrIdn : that.ftrIdn
						};
						$.post(url,params).done(function(response) {
							//alert("보고서가 등록되었습니다.");
						}).fail(function(result) {
							alert("보고서 등록에 실패했습니다.");
						});
						
						$.messager.alert(that.TITLE, "정책현황이 저장되었습니다.");
						
//						var info = {
//								dataId : 'PLCY_STAT_AS',
//								ftrCde : '정책현황',
//								ftrIdn : that.ftrIdn
//						};						
//						windowObj.policyRegisterObj.open(info);
						that.close();
						// 검색 결과 목록 현황 업데이트
						policyRegResultObj.refreshResultLayersGrid();
					}
					else {
						$.messager.alert(that.TITLE, "정책현황 저장에 실패하였습니다.");
					}
				}
			});
		},
		
		open : function(ftrIdn) {
			var that = this;
			windowObj.policyRegisterObj.close();
			that.ftrIdn = ftrIdn;
			var TITLE = that.TITLE;
			if (that.selector) {
				that.close();
			}
			
			var url = CONTEXT_PATH + "/policy/"+ftrIdn+"/editpolicyRegstrPage.do";
			var windowOptions = {
				width : 750,
				top : 120,
			    height : 750,
				left : 550,
				isClose : function() {
					that.close();
				}
			};
			
			var id = windowObj.createWindow(that.CLASS_NAME, TITLE, url, null, windowOptions, function() {
					that.init();	
					that.initUi();
					that.isCreated = true;
					that.bindEvents();
					that.loadDataView();
			});
			
			that.selector = "#" + id;
		},
		
		initUi : function() {
			
			var that = this;
			
			// easyUi설정
			$("#editPolicyRegstr .easyui-textbox", that.selector).textbox();
			$("#editPolicyRegstr .easyui-combobox", that.selector).combobox();
			$("#editPolicyRegstr .easyui-numberspinner", that.selector).numberspinner();
				
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
				width : 100
			});
				
				// 부번
			$("#subNum", that.selector).numberspinner({
				required : false,
				value : 0,
				min : 0,
				max : 9999,
				increment : 1,
				width : 100
			});
			
			$(".deptSbNm", that.selector).combobox({
				required : false,
				valueField : "deptSubCode",
				textField : "deptSubNm",
				width : 110,
				height : 25
			});
			
			$(".plcyDep", that.selector).combobox({
				required : false,
				width : 110,
				height : 25,
				onChange : function() {
					that.show();
				}
			});
			
			// 편집기 적용
			CKEDITOR.replace('txa_repo_add_doc',
			{
				customConfig: CONTEXT_PATH + '/js/yy/job/policy/ckeditorconfig.js'
			});
			
		},
		
		show : function() {
			var that = this;
			var selectDept = $(".plcyDep", that.selector).combobox("getValue");
			if (selectDept==''){
			} else{
				var data = deptSubObj.getData(selectDept).slice();
			    $(".deptSbNm", that.selector).combobox("loadData", data);
			    $(".deptSbNm", that.selector).combobox("setValue", data[0].deptSubCode);
			}
		},
		
		loadDataView : function(){
			var that = this;
			var ftrIdn = that.ftrIdn;
//			var repoIdn = that.repoIdn;
			var url = CONTEXT_PATH + "/policy/" + ftrIdn + "/plcyAdrSearch.do";
			$.get(url).done(function(response){
				var pnu = response.result.plcyAdr;
				var selDong = pnu.substring(0,10);
				var deptSbCd = response.result.deptSbCd;
				var deptSbNm = response.result.deptSbNm;
				var deptData = {};
				deptData.push = ({'deptSbNm' : deptSbNm, 'deptSbCd' : deptSbCd});

				//PNU 일반/산 찾기
				var isMount = pnu.substring(10,11);
				//PNU 지번
				var mainNum = pnu.substring(11,15);
				//PNU 부번
				var subNum = pnu.substring(15,19);
				
				$("#selDong", that.selector).combobox('setValue', selDong);
				
				//2021.07.30 정신형
				//feature에 deptcode 정보가 없어 역으로 불러온다
				var plcyDeptCode = deptSubObj.forDeptCode(deptSbCd); 
				var data = deptSubObj.getData(plcyDeptCode).slice();
				
				$(".plcyDep", that.selector).combobox("setValue", plcyDeptCode);
				$(".deptSbNm", that.selector).combobox("loadData", data);
				$(".deptSbNm", that.selector).combobox("setValue", deptSbCd);
				/*
				$(".deptSbNm", that.selector).combobox("setValue", deptSbCd);
				$(".deptSbNm", that.selector).combobox("setText", deptSbNm);*/
				
				if (isMount==1){
					$("#checkMauntain", that.selector).switchbutton('uncheck');
				}
				else if(isMount==2){
					$("#checkMauntain", that.selector).switchbutton('check');
				}
				$("#mainNum", that.selector).numberspinner('setValue',mainNum);
				$("#subNum", that.selector).numberspinner('setValue',subNum);
			});
			var url = CONTEXT_PATH + "/policy/" + ftrIdn + "/listRepoCt.do";
			$.get(url).done(function(response){
				var htmlload = response.rows.repoDoc;
				if (htmlload == '') {
					htmlload = "<p></p><p><span style=\"font-family:휴먼명조;\"><span style=\"font-size:20px;\">[ 정책명 ]</span></span></p><p>&nbsp;</p><p><span style=\"font-size:18px;\"><span style=\"font-family:휴먼명조;\">□ 사업목적</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○</span></span></p><p>&nbsp;</p><p><span style=\"font-size:18px;\"><span style=\"font-family:휴먼명조;\">□ 사업개요</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○ 사업지구</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　　-</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○ 사업기간 :</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　　-</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○ 총사업비 :</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○ 총사업량</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　　-</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　　-</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　　-</span></span></p><p>&nbsp;</p><p><span style=\"font-size:18px;\"><span style=\"font-family:휴먼명조;\">□ 추진현황</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○</span></span></p><p>&nbsp;</p><p><span style=\"font-size:18px;\"><span style=\"font-family:휴먼명조;\">□ 향후계획</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○</span></span></p>";
				}
				CKEDITOR.instances.txa_repo_add_doc.setData(htmlload,function(){
	                CKEDITOR.instances.txa_repo_add_doc.setData(htmlload);
	              });
			});
		},
		
		bindEvents : function() {
			var that = this;
			
			//저장버튼
			$(".btn_save_policyRegstrEdit", that.selector).on("click", function() {
				that.save();
				return false;
			});
			
			// 취소버튼
			$(".btn_close_policyRegstrEdit", that.selector).on("click", function() {
//				var info = {
//						dataId : 'PLCY_STAT_AS',
//						ftrCde : '정책현황',
//						ftrIdn : that.ftrIdn
//				};
//				windowObj.policyRegisterObj.open(info);
				that.close();
				return false;
			});
		},
		
		save : function() {
			var that = this;
			var ftrIdn = that.ftrIdn;
			var pnuCode = '';
//			var cutPnuCode = '';
			var dongCode = $("#selDong", that.selector).combobox("getValue");
			var mnnm = $("#mainNum", that.selector).numberspinner("getValue");
			var slno = $("#subNum", that.selector).numberspinner("getValue");
			var mntn = $("#checkMauntain", that.selector).switchbutton("options").checked?'2':'1';
			
			if(dongCode != "4280000000") {
				if(mnnm == '' && slno != ''){
					$.messager.alert(that.TITLE, "본번을 입력하십시오.");
				} else {
					if(dongCode!='' && mntn == "1" && mnnm!='' && slno!=''){
						pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
					}
					else if(dongCode!='' && mntn == "2" && mnnm!='' && slno!=''){
						pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
					}
					else if(dongCode!='' && mntn == "1" && mnnm!=''){
						pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
//						cutPnuCode = pnuCode.substring(0, 15);
					}
					else if(dongCode!='' && mntn == "2" && mnnm!=''){
						pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
//						cutPnuCode = pnuCode.substring(0, 15);
					}
					else if(dongCode!='' && mntn == "1"){
						pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
//						cutPnuCode = pnuCode.substring(0, 11);
					}
					else if(dongCode!='' && mntn == "2"){
						pnuCode = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
//						cutPnuCode = pnuCode.substring(0, 11);
					}
				}
			$("#plcyAdr",that.selector).val(pnuCode);
				
			}
			var repoDoc = CKEDITOR.instances.txa_repo_add_doc.getData();
			that.repoDoc = repoDoc;

			that.plcyAdr = pnuCode;
			var plcyAdr = that.plcyAdr;

			var url = CONTEXT_PATH + "/policy/" + ftrIdn + "/modifyPlcyStatAs.do?&plcyAdr=" + plcyAdr;
			$("form", that.selector).attr("action", url);
			$("form", that.selector).submit();
		},
		
		close : function() {
			var that = this;
			
			that.dataId = null;
			that.fids = null;
			that.data = null;
			that.dataFields = null;
			that.featureInfos = null;
			that.isCreated = false;
			that.index = null;
			that.categories = null;
			that.feature = null;
			that.properties = null;
			that.onSave = null;
			that.onLoad = null;
			that.sort = null;
			that.ordr = null;
			that.gridOptions = null;
			
			windowObj.removeWindow(that.selector);
			that.selector = null;
		}
};
	/**
	* 사진 편집 
	*/
	windowObj.policyRegisterObj.photoModify = {

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
				
			var url = CONTEXT_PATH + "/policy/" + imageNo + "/modifyPhoto.do";
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
			
			$(".easyui-textbox", that.selector).textbox();
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
		
		/**
		 * 사진 로딩
		 */
		loadImage : function() {
			var that = this;

			var fileNo = $("input[name=fileNo]", that.selector).val();
			
			var imgDownloadUrl = CONTEXT_PATH + "/policy/" + fileNo + "/download.do";
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
					var url = CONTEXT_PATH + "/policy/" + that.imageNo + "/remove.do";
					
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
		