windowObj.editPolicyRegisterObj = {

	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "정책현황 등록",

	/**
	 * 클래스 명
	 * 
	 * @type {String}
	 */
	CLASS_NAME : "policyAddRegister",

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

	// 초기화
	open : function(dataId, fids, feature, onSave, onLoad) {
		var that = this;
		if (that.selector) {
			that.close();
		}
		that.dataId = dataId;
		that.fids = fids;
		that.feature = feature;
		that.ftrIdn = feature.values_.FTR_IDN;
		that.onSave = onSave;
		that.onLoad = onLoad;

		that.data = dataObj.getDataByDataId(that.dataId, true);
		var url;
        if (fids){
			url = CONTEXT_PATH + "/policy/"+that.ftrIdn+"/modifypolicyRegstrPage.do";
		}else{
			url = CONTEXT_PATH + "/policy/addPolicyRegistrPage.do";
        }    
		var windowOptions = {
			width : 750,
			height : 750,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.initUi();
				that.bindEvents();
				if(that.fids){
					that.loadDataView();
				}
				if(!that.fids){
					that.loadCk();
					that.show();
				}
				that.isCreated = true; 
			}
		});
		
		that.selector = "#" + id;
	},
	
	initUi : function() {
		
		var that = this;
		
		// easyUi설정
		$("#addPolicyRegstr .easyui-textbox", that.selector).textbox();
		$("#addPolicyRegstr .easyui-combobox", that.selector).combobox();
		$("#addPolicyRegstr .easyui-numberspinner", that.selector).numberspinner();
		$("#addPolicyRegstr .easyui-switchbutton", that.selector).switchbutton();

		// dongObj 오브젝트의 로딩상태를 확인 후 동 데이터 가져오기
		if (!dongObj.getPromise()) {
			dongObj.init();
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
		$("#checkMauntain", that.selector).switchbutton('uncheck');

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
			valueField: 'deptCode',
			textField: 'deptNm',
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
	
	loadCk : function(){
		var htmlload = "<p></p><p><span style=\"font-family:휴먼명조;\"><span style=\"font-size:20px;\">[ 정책명 ]</span></span></p><p>&nbsp;</p><p><span style=\"font-size:18px;\"><span style=\"font-family:휴먼명조;\">□ 사업목적</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○</span></span></p><p>&nbsp;</p><p><span style=\"font-size:18px;\"><span style=\"font-family:휴먼명조;\">□ 사업개요</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○ 사업지구</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　　-</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○ 사업기간 :</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　　-</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○ 총사업비 :</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○ 총사업량</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　　-</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　　-</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　　-</span></span></p><p>&nbsp;</p><p><span style=\"font-size:18px;\"><span style=\"font-family:휴먼명조;\">□ 추진현황</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○</span></span></p><p>&nbsp;</p><p><span style=\"font-size:18px;\"><span style=\"font-family:휴먼명조;\">□ 향후계획</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○</span></span></p><p><span style=\"font-size:15px;\"><span style=\"font-family:휴먼명조;\">　○</span></span></p>";
		CKEDITOR.instances.txa_repo_add_doc.setData(htmlload,function(){
           CKEDITOR.instances.txa_repo_add_doc.setData(htmlload);
         });
	},
	
	bindEvents : function() {
		var that = this;
		
		
		
		//도형의 위치 찾기
		var title = that.title;
		var className = that.CLASS_NAME;
        that.searchPnuGeom(that.feature, className, title).done(function(result) {
        	// 지번 컨트롤 초기화
        	var pnu = result.pnu;
        	
        	// 법정동
	        var selDong = pnu.substring(0,10);
			$("#selDong", that.selector).combobox("select", selDong);

			// 일반/산 찾기
			var checkMauntain = pnu.substring(10,11);
			if(checkMauntain==1){
				$("#checkMauntain", that.selector).switchbutton('uncheck');
			}else{
				$("#checkMauntain", that.selector).switchbutton('check');
			}
						
			//본번
			var mainNum = pnu.substring(11,15);
			$("#mainNum", that.selector).numberspinner({
			    required : true,
			    value : mainNum,
			    min : 1,
			    max : 9999,
			    increment : 1,
			    width : 120
	    	});
			
			//부번
			var subNum = pnu.substring(15,19);
			$("#subNum", that.selector).numberspinner({
			    required : false,
			    value : subNum,
			    min : 0,
			    max : 9999,
			    increment : 1,
			    width : 120
	    	});
			
		});
        
        // 부서 콤보박스 초기화
//        var plcyDep = $("#hid_dept_name").val();	//main.jsp에서
//        $("#plcyDep", that.selector).combobox("select", plcyDep);
        
        // 작성자 초기화
        var userName = $("#hid_user_name").val();	//main.jsp에서
        
        // 담당자 초기화
        $("#plcyMng", that.selector).textbox("setText", userName);
        
        // LOD_YMD 생성
        var Current = new Date($.now());
        var year = Current.getFullYear();
        var month = Current.getMonth()+1;
        var day = Current.getDate();
        if(month < 10){
            month = "0"+month;
        }
        if(day < 10){
            day = "0"+day;
        }
        var CurrentDate = year + month + day;

        // HJD_CDE
        var hjdCde = that.getDefaultSpatialValue(hDongObj);
               
        //저장시 
		$(".btn_save_policyRegstrAdd", that.selector).click(function() {
			if(that.feature) {
				that.feature.repoDoc = CKEDITOR.instances.txa_repo_add_doc.getData();
				
				var ftrIdn = that.feature.values_.FTR_IDN;
		
				// PNU
				var bjdCde = $("#selDong", that.selector).combobox("getValue");
				var checkMauntain = $("#checkMauntain", that.selector).switchbutton("options").checked?'2':'1';
				var mainNum = $("#mainNum", that.selector).numberspinner("getValue");
				var subNum = $("#subNum", that.selector).numberspinner("getValue");
				var plcyAdr = pnuObj.createPnu(bjdCde, checkMauntain, mainNum, subNum);
				var crtFtrIdn = ftrIdn + '';
				if(crtFtrIdn.length < 5){
					crtFtrIdn = CurrentDate.substr(2,2) + crtFtrIdn;	// FTR_IDN --> TABLE_SN		//추가-> 관리번호 앞에 연도 붙여달라고 함
				}
				
				var props = {
						PLCY_ADR : plcyAdr,
						PLCY_WRT : $("#plcyMng", that.selector).textbox("getValue"), // 작성자
						LOD_YMD : CurrentDate, // 자료등록일자
						BJD_CDE : bjdCde, //법정동
						HJD_CDE : hjdCde, // 행정동
						FTR_IDN : crtFtrIdn,
						FTR_CDE : 'PA000', // PA000 - > kws_domn_code -- 정책지도
						PLCY_TIT : $("#plcyTit", that.selector).textbox("getText"), //정책명 
						PLCY_DEP : $("#plcyDep", that.selector).combobox("getText"), // 담당부서
						PLCY_MNG : $("#plcyMng", that.selector).textbox("getText"), // 담당자
						CTRCT_DT : $("#ctrctDt", that.selector).textbox("getText"), //계약일
						CTT_BEG : $("#cttBeg", that.selector).textbox("getText"), // 착공일
						CTT_FRN : $("#cttFrn", that.selector).textbox("getText"),  // 준공일
						CTT_NAM : $("#cttNam", that.selector).textbox("getText"),  //도급자상호
						CTRCTAMT : $("#ctrctamt", that.selector).textbox("getText"), //금액
						RMK_EXP : $("#rmkExp", that.selector).textbox("getText"),
						PLCY_WRT : userName,	// 작성자
						DEPT_SB_NM : $("#deptSbNm", that.selector).textbox("getText"),
						DEPT_SB_CD : $("#deptSbNm", that.selector).combobox("getValue")
					};
				if(props) {
					that.feature.setProperties(props);
					if(props.PLCY_TIT ==''){
					    $.messager.alert("정책현황", "정책명은 필수 값입니다.");	
					}else if(props.PLCY_DEP ==''){
						$.messager.alert("정책현황", "담당부서는 필수 값입니다.");	
					}else{
						if(that.onSave) {
							that.onSave();
						}
					}
					that.close();
				} else {
					$.messager.alert(that.title, "편집한 내용이 없습니다.");
				}
			}
			return false;
		});
		
		// 취소버튼
		$(".btn_close_policyRegstrAdd", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	loadDataView : function(){
		var that = this;
		var rows = that.feature.values_;
		var ftrIdn = that.ftrIdn;
		var deptSbCd = rows.DEPT_SB_CD;
		//var deptSubNm = rows.DEPT_SUB_NM;
		//var plcyDep = rows.PLCY_DEP;
//		$("#plcyAdr", that.selector).textbox('setValue', rows.PLCY_ADR); //위치
		
		var pnu = rows.PLCY_ADR;
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
		
		//2021.07.30 정신형
		//feature에 deptcode 정보가 없어 역으로 불러온다
		var plcyDeptCode = deptSubObj.forDeptCode(deptSbCd); 
		var data = deptSubObj.getData(plcyDeptCode).slice();
		
		$(".plcyDep", that.selector).combobox("setValue", plcyDeptCode);
		$(".deptSbNm", that.selector).combobox("loadData", data);
		$(".deptSbNm", that.selector).combobox("setValue", deptSbCd);
		
		var url = CONTEXT_PATH + "/policy/" + ftrIdn + "/listRepoCt.do";
		$.get(url).done(function(response){
			var rows = response.rows;
			CKEDITOR.instances.txa_repo_add_doc.setData(rows.repoDoc,function(){
                CKEDITOR.instances.txa_repo_add_doc.setData(rows.repoDoc);
              });
		});
	},
	
	//대장등록시 geom정보로 pnu 계산
	searchPnuGeom : function(feature, className, title) {
		var that = this;
		return that.searchLandFieldGeom(feature,  className, title, 'pnu');
	},
	//대장등록시 geom정보로 pnu 계산 //searchPnuGeom에서 실행
	searchLandFieldGeom : function(feature, className, title, fieldName) {
		var deferred = $.Deferred();

		var buffer = mapObj.getMap().getView().getResolution() * 10;
		if(buffer < 1) {
			buffer = 0;
		}
				
		var geometry = feature.getGeometry();
		var bufferGeometry = spatialUtils.buffer(geometry, buffer);
		var mapProjection = mapObj.getMap().getView().getProjection();
		var sysProjection = ol.proj.get(MAP.PROJECTION);
		var transformGeometry = spatialUtils.transform(bufferGeometry, mapProjection, sysProjection);
		var format = new ol.format.WKT();
		var wkt = format.writeFeature(new ol.Feature(transformGeometry));
		
		// 필터
		var filter = {
			filterType : "SPATIAL",
			spatialType : "INTERSECTS",
			wkt : wkt
		};
		
		// 공간 검색
		spatialSearchUtils.listAll(title, COMMON.LAND, filter, function(data) {

				var result = {};
				result.pnu = data[0][fieldName];
				result.features = data[0][MAP.GEOMETRY_ATTR_NAME];
				
				deferred.resolve(result);
				mainObj.defaultActive();
			
		});
		return deferred.promise();
	},
	
	getDefaultSpatialValue : function(spatialDataobj) {
		var that = this;
		var value = '';
		
		var format = new ol.format.WKT();
		var data = spatialDataobj.getData();
		for(var j=0, jLen=data.length; j < jLen; j++) {
			var features = [format.readFeature(data[j][MAP.GEOMETRY_ATTR_NAME])];
			if(features && features.length > 0) {
				var coord = that.feature.getGeometry().getFirstCoordinate();
				var from = mapObj.getMap().getView().getProjection();
				var to = ol.proj.get(MAP.PROJECTION);
				var transCoord = spatialUtils.transformCooridate(coord, from , to);
				
				var feature = features[0];
				if(feature.getGeometry().containsXY(transCoord[0], transCoord[1])) {
					value = data[j][camelcaseUtils.underbarToCamelcase(spatialDataobj.codeField)];
					break;
				}
			}
		}
		
		return value;
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;

		that.data = null;
		that.dataId = null;
		that.fids = null;
		that.ftrIdn = null;
		that.index = 0;
		that.categories = null;
		that.feature = null;
		that.properties = {};
		that.onSave = null;
		that.onLoad = null;
		that.isCreated = false; 
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
};
