/**
 * 추가 선택 객체
 * @type {Object}
 */
windowObj.resultConditionSearchObj = {
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "조건 검색",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "ResultConditionSearch",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 데이터
	 * @type {Object}
	 */
	data : null,
	
	/**
	 * 선택완료 시 실행될 함수
	 */
	onSubmit : null,
	
	/**
	 * 열기
	 * @param {Object} data 데이터
	 */
	open : function(data, options) {
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/result/conditionSearch/page.do";
		var windowOptions = {
			width : 530,
			height : 335,
			top : 120,
			modal : true,
			onClose : function() {
				that.close();
			}
		};
		
		that.data = data;
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
			that.loadFields(that.data.dataId);
			// 현재 객체에 전달되는 값 적용 (onSubmit)
			if(options) {
				$.extend(that, options);
			}
		});
		that.selector = "#" + id;
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 도메인 조건 
		$(".sel_domain_condition", that.selector).combobox({
			width : 70,
			editable : false
		});
		$(".sel_domain_condition", that.selector).combobox("setValue", "=");
		
		// 숫자 조건
		$(".txt_number_min").numberspinner({
			width : 90,
			min : 0,
			value : 0
		});
		$(".txt_number_max").numberspinner({
			width : 90,
			min : 0,
			value : 0
		});
		$(".sel_number_condition", that.selector).combobox({
			width : 70,
			editable : false
		});
		$(".sel_number_condition", that.selector).combobox("setValue", "=");
		$(".txt_number_value").numberspinner({
			width : 90,
			min : 0,
			value : 0
		});
		
		// 날짜 조건
		$(".txt_date_start").datebox({
			width : 90
		});
		$(".txt_date_end").datebox({
			width : 90
		});
		$(".sel_date_condition", that.selector).combobox({
			width : 70,
			editable : false
		});
		$(".sel_date_condition", that.selector).combobox("setValue", "=");
		$(".txt_date_value").datebox({
			width : 90
		});
		
		// 문자열 조건
		$(".txt_string_value", that.selector).textbox({
			prompt : "검색어를 입력하세요.",
			width : 150
		});
	},
	
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 조건 대상 항목 선택
		$(".ul_fields", that.selector).on("click", "li", function() {
			var element = $(this);
			
			$(".ul_fields li", that.selector).removeClass("on");
			element.addClass("on");
			
			var fieldId = element.attr("data-field-id");
			that.loadCondition(fieldId);
		});
		
		// 조건 설정 도메인 항목 선택
		$(".ul_domain_codes", that.selector).on("click", "li", function() {
			var element = $(this);
			
			$(".ul_domain_codes li", that.selector).removeClass("on");
			element.addClass("on");
		});
		
		// 저장
		$(".a_save", that.selector).click(function() {
			that.save();
			return false;
		});
		
		// 검색 조건 항목 선택
		$(".ul_items", that.selector).on("click", "li", function() {
			var element = $(this);
			
			$(".ul_items li", that.selector).removeClass("on");
			element.addClass("on");
		});
		
		// 삭제
		$(".a_remove", that.selector).click(function() {
			that.removeItem();
		});
		
		// 검색
		$(".a_search", that.selector).click(function() {
			that.search();
			return false;
		});
		
		// 닫기
		$(".a_close", that.selector).click(function() {
			that.close();
			return false;
		});
	},
	
	/**
	 * 필드 목록 불러오기
	 * @param {String} dataId 데이터 아이디
	 */
	loadFields : function(dataId) {
		var that = this;
		
		var data = dataObj.getDataByDataId(dataId, true);
		var fields = data.kwsDataFields;
		
		var tagStr = '';
		for(var i=0, len=fields.length; i < len; i++) {
			var field = fields[i];
			if(field.indictTy == "HIDE" || field.indictTy == "WKT") {
				continue;
			}
			if(field.domnId || field.fieldTy == "STRING" || field.fieldTy == "NUMBER" || field.fieldTy == "DATE" ||field.fieldTy == "DATE_FROM_STRING" ) {
				tagStr += '<li data-field-id="' + field.fieldId + '">';
				tagStr += field.fieldAlias;
				tagStr += '</li>';
			}
		}
		$(".ul_fields", that.selector).html(tagStr);
	},
	
	/**
	 * 조건 불러오기
	 * @param {String} fieldId 필드 아이디
	 */
	loadCondition : function(fieldId) {
		 var that = this;
		 $(".div_condition_field", that.selector).hide();
		 
		 var field = dataObj.getDataField(that.data.dataId, fieldId);
		 if(field.domnId) {
			 that.loadDomainCodes(field.domnId);
			 $(".div_domain_field", that.selector).show();
		 }
		 else if(field.fieldTy == "NUMBER") {
			 $(".div_number_field", that.selector).show();
		 }
		 else if(field.fieldTy == "DATE" || field.fieldTy == "DATE_FROM_STRING") {
			 $(".div_date_field", that.selector).show();
		 }
		 else if(field.fieldTy == "STRING") {
			 $(".div_string_field", that.selector).show();
		 }
 	},
	
 	/**
 	 * 도메인 코드 불러오기
 	 * @param {String} domnId 도메인 아이디
 	 */
	loadDomainCodes : function(domnId) {
		var that = this;
		var data = [];
		var fieldId = $(".ul_fields li.on", that.selector).attr("data-field-id");
		
		var domnCodes = domnCodeObj.getData(domnId);
		if(fieldId == "FTR_CDE") {
			var ftrCdes = dataFtrCdeObj.getData(that.data.dataId);
			for(var i=0, len=ftrCdes.length; i < len; i++) {
				for(var j=0, jLen=domnCodes.length; j < jLen; j++) {
					if(ftrCdes[i].codeId == domnCodes[j].codeId) {
						data.push(domnCodes[j]);
						break;
					}
				}
			}
		}
		else {
			data = domnCodes;
		}
		
		var tagStr = '';
		for(var i=0, len=data.length; i < len; i++) {
			var domnCode = data[i];
			tagStr += '<li data-code-id="' + domnCode.codeId + '">';
			tagStr += domnCode.codeNm;
			tagStr += '</li>';
		}
		$(".ul_domain_codes", that.selector).html(tagStr);
	},
	
	/**
	 * 저장
	 */
	save : function() {
		var that = this;
		
		// 조건 대상 항목
		if($(".ul_fields li.on", that.selector).length <= 0) {
			$.messager.alert(that.TITLE, "선택된 조건 대상 항목이 없습니다.");
			return;
		}
		
		var fieldId = $(".ul_fields li.on", that.selector).attr("data-field-id");
		var field = dataObj.getDataField(that.data.dataId, fieldId);
		if (field.domnId) {
			if($(".ul_domain_codes li.on", that.selector).length <= 0) {
				$.messager.alert(that.TITLE, "선택된 조건이 없습니다.");
				return;
			}
			var condition = $(".sel_domain_condition").combobox("getValue");
			var codeId = $(".ul_domain_codes li.on", that.selector).attr("data-code-id");
			var codeNm = $(".ul_domain_codes li.on", that.selector).text();
			var text = field.fieldAlias + " " + condition + " " + codeNm;
			that.addItem("domain", field.fieldId, condition, codeId, text);
		} 
		else if (field.fieldTy == "NUMBER") {
			var numberType = $("input[name=chk_unity_search_number]:checked").val();
			if(numberType == "range") {
				var min = parseFloat($(".txt_number_min", that.selector).numberspinner("getValue"));
				var max = parseFloat($(".txt_number_max", that.selector).numberspinner("getValue"));
				if(min || max) {
					if(min) {
						var text = field.fieldAlias + " >= " + min;
						that.addItem("number", field.fieldId, ">=", min, text);
					}
					if(max) {
						var text = field.fieldAlias + " <= " + max;
						that.addItem("number", field.fieldId, "<=", max, text);
					}
				}
				else {
					$.messager.alert(that.TITLE, "최소, 최대값이 없습니다.");
				}
			}
			else if(numberType == "equals") {
				var condition = $(".sel_number_condition").combobox("getValue");
				var value = $(".txt_number_value", that.selector).numberspinner("getValue");
				if(value) {
					var text = field.fieldAlias + " " + condition + " " + value;
					that.addItem("number", field.fieldId, condition, value, text);
				}
				else {
					$.messager.alert(that.TITLE, "특정값을 입력하여 주십시오.");
				}
			}
			else {
				$.messager.alert(that.TITLE, "정의되지 않은 숫자 타입입니다.");
				return;
			}
		} 
		else if (field.fieldTy == "DATE" || field.fieldTy == "DATE_FROM_STRING") {
			var dateType = $("input[name=chk_unity_search_date]:checked").val();
			if(dateType == "range") {
				var start = $(".txt_date_start", that.selector).datebox("getValue");
				var end = $(".txt_date_end", that.selector).datebox("getValue");
				if(start || end) {
					if(start) {
						var text = field.fieldAlias + " >= " + start;
						that.addItem("date", field.fieldId, ">=", start, text);
					}
					if(end) {
						var text = field.fieldAlias + " <= " + end;
						that.addItem("date", field.fieldId, "<=", end, text);
					}
				}
				else {
					$.messager.alert(that.TITLE, "시작일, 종료일이 없습니다.");
				}
			}
			else if(dateType == "equals") {
				var condition = $(".sel_number_condition").combobox("getValue");
				var value = $(".txt_date_value", that.selector).datebox("getValue");
				if(value) {
					var text = field.fieldAlias + " " + condition + " " + value;
					that.addItem("date", field.fieldId, condition, value, text);
				}
				else {
					$.messager.alert(that.TITLE, "특정일을 입력하여 주십시오.");
				}
			}
			else {
				$.messager.alert(that.TITLE, "정의되지 않은 날짜 타입입니다.");
				return;
			}
		}
		else if (field.fieldTy == "STRING") {
			var condition = "LIKE";
			var value = $(".txt_string_value", that.selector).textbox("getValue");
			if(value) {
				var text = field.fieldAlias + " = *" + value + "*";
				value = "%"+value+"%";
				that.addItem("string", field.fieldId, condition, value, text);
			}
			else {
				$.messager.alert(that.TITLE, "검색어를 입력하여 주십시오.");
			}
		}
	},
	
	/**
	 * 항목 추가
	 * @param {String} type 타입 (domain, number, date, string)
	 * @param {String} fieldId 필드 아이디
	 * @param {String} condition 조건
	 * @param {String} value 값
	 * @param {String} text 화면에 표시할 문자열
	 */
	addItem : function(type, fieldId, condition, value, text) {
		var that = this;
		var tagStr = '';
		tagStr += '<li ';
		tagStr += 'data-type="' + type + '" ';
		tagStr += 'data-field="' + fieldId + '" ';
		tagStr += 'data-condition="' + condition + '" ';
		tagStr += 'data-value="' + value + '" ';
		tagStr += '>';
		tagStr += text;
		tagStr += '</li>';
		$(".ul_items", that.selector).append(tagStr);
	},
	
	/**
	 * 항목 삭제
	 */
	removeItem : function() {
		var that = this;
		if($(".ul_items li.on", that.selector).length > 0) {
			$(".ul_items li.on", that.selector).remove();
		}
		else {
			$.messager.alert(that.TITLE, "선택된 검색 조건 항목이 없습니다.");
		}
	},
	
	/**
	 * 검색
	 */
	search : function() {
		var that = this;
		
		var elements = $(".ul_items li", that.selector);
		if(elements.length <= 0) {
			$.messager.alert(that.TITLE, "조건 항목이 없습니다.");
			return;
		}
		
		var cql = "";
		for(var i=0, len=elements.length; i < len; i++) {
			var element = $(elements[i]);
			if(i != 0) {
				cql += " AND ";
			}
			cql += element.attr("data-field") + " ";
			cql += element.attr("data-condition") + " ";
			if(element.attr("data-type") == "number") {
				cql += element.attr("data-value");
			}
			else {
				cql += "'" + element.attr("data-value") + "'";
			}
		}
		
		var filter = {
			filterType : "CQL",
			cql : cql
		};
		spatialSearchUtils.listAllSummary(that.TITLE, that.data.dataId, filter, function(data) {
			if(data) {
				if(that.onSubmit) {
					that.onSubmit(data);
				}
				mapToolObj.deactive();
			}
		});
	}
		
};