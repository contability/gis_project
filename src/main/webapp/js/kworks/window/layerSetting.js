windowObj.layerSettingObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,

	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "레이어 설정",
	
	/**
	 * 클래스명
	 * @type {String}
	 */
	CLASS_NAME : "LayerSetting",

	
	/**
	 * KWS_DATA 정보
	 * @type {Object}
	 */
	data : null,
	
	/**
	 * 데이터 아이디
	 */
	dataId : null,
	
	/**
	 * KWS_LYR 정보
	 * @type {Object}
	 */
	layer : null,

	/**
	 * 레이어 명
	 * @type {Object}
	 */
	layerName : null,

	/**
	 * 레이어 별칭
	 * @type {Object}
	 */
	layerAlias : null,
	
	/**
	 * 룰 목록 (기본, 도메인, 숫자) - 필드별 설정한 룰 목록 저장
	 * @type {Object}
	 */
	rulesList : {},
	
	/**
	 * 텍스트 룰
	 * @type {Object}
	 */
	textRule : {},
	
	/**
	 * 필드 목록에서 선택한 레코드 정보
	 * @type {Object}
	 */
	record : null,
	
	onSubmit : null,
	
	/**
	 * 레이어 설정 창 열기
	 * @param layerName 레이어 명
	 * @param options 옵션
	 */
	open : function(layerName, dataId, title, options) {
		var that = this;
		var url = CONTEXT_PATH + "/window/layerSetting/page.do";
		var windowOptions = {
			width : 270,
			height : that.getHeight(layerName),
			modal : true,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			// 레이어 설정 초기화
			that.clear();

			that.layerAlias = title;
			that.layerName = layerName;
			that.dataId = dataId;
			if (!dataId || dataId.length <= 0)
				that.dataId = layerName;
			
			that.initUi();
			that.loadData();
			that.loadLayer();
			that.loadTitle();
			that.loadFields();
			that.loadLabelFields();
			that.setCurrentRule();
			
			that.bindEvents();
			
			// 현재 객체에 전달되는 값 적용 (onSubmit)
			if(options) {
				$.extend(that, options);
			}
		});
		that.selector = "#" + id;
	},
	
	/**
	 * 레이어 설정 창 닫기
	 */
	close : function() {
		var that = this;
		that.clear();
		windowObj.removeWindow(that.selector);
		that.selector = null;
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		that.data = null;
		that.layer = null;
		that.record = null;
		that.layerName = null;
		that.rulesList = {};
		that.textRule = null;
		that.onSubmit = null;
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 필드 목록
		$(".div_fields .sel_fields", that.selector).combobox({
			required : true,
			valueField : "fieldId",
			textField : "fieldAlias",
			onSelect : function(record) {
				that.record = record;
				that.loadRules();
			}
		});
		
		// 도메인 분류 설정
		$(".a_setting_domain_cl", that.selector).click(function() {
			var fieldId = that.record.fieldId;
			var domnId = that.record.domnId;
			
			// 현재 표시된 룰명 (도메인코드 아이디) 목록
			var codeIdList = {};
			var rules = that.rulesList[fieldId];
			for(var i=0, len=rules.length; i < len; i++) {
				codeIdList[rules[i].name] = true;
			}
			
			// 도메인 분류 선택 창 열기
			windowObj.domainClObj.open(domnId, codeIdList, {
				onSubmit : function(domnCodes) {
					that.applyDomainRules(domnCodes);
				}
			});
			return false;
		});
		
		// 최소 축척
		$(".div_scale .txt_min_scale", that.selector).numberspinner({
			min : 0,
			increment : 1000
		});
		
		// 최대 축척
		$(".div_scale .txt_max_scale", that.selector).numberspinner({
			min : 1,
			increment : 1000
		});
		
		// 축척 일괄 적용
		$(".div_scale .a_apply_scale", that.selector).linkbutton();
		
		// 레이블 필드 목록
		$(".div_label .sel_label_fields", that.selector).combobox({
			valueField : "fieldId",
			textField : "fieldAlias"
		});
		
		// 채우기 투명도
		$(".txt_fill_opacity", that.selector).slider({
			value : 0,
			showTip:true
		});
		
		
		// 외곽선 투명도
		$(".txt_line_opacity", that.selector).slider({
			value : 0,
			showTip:true
		});
		
		// 채우기 투명도 일괄적용
		$(".a_apply_polygon_opacity", that.selector).linkbutton();
		
		// 외곽선 채우기 일괄적용
		$(".a_apply_line_opacity", that.selector).linkbutton();
		
		
		// 점, 선, 면에 따른 UI 변경
		var layer = layerObj.getLayerByLayerName(that.layerName);
		var lyrTy = layer.lyrTy;
		if(lyrTy == "POINT") {
			$(".div_line_opacity").hide();
			$(".div_polygon_opacity").hide();
		}
		else if(lyrTy == "LINESTRING") {
			$(".div_line_opacity").show();
			$(".div_polygon_opacity").hide();
		}
		else if(lyrTy == "POLYGON") {
			$(".div_line_opacity").show();
			$(".div_polygon_opacity").show();
		}
	},
	
	getHeight : function(layerName) {
		var layer = layerObj.getLayerByLayerName(layerName);
		var height = 530;
		var lyrTy = layer.lyrTy;
		if(lyrTy == "LINESTRING") {
			height = 630;
			
			
		}
		else if(lyrTy == "POLYGON") {
			height = 660;
		}
		return height;
	},
	
	bindEvents : function() {
		var that = this;
		
		// 심볼 설정
		$(that.selector).on("click", ".a_setting_symbol", function() {
			var element = $(this);
			var lyrTy = that.layer.lyrTy;
			var ruleName = element.attr("data-rule-name");
			var rules = that.rulesList[that.record.fieldId];
			for(var i=0, len=rules.length; i < len; i++) {
				if(rules[i].name == ruleName) {
					break;
				}
			}
			
			if(lyrTy == "POINT") {
				windowObj.symbolSettingObj.open(rules[i], {
					onSubmit : function(ruleObj) {
						rules[i] = ruleObj;
					}
				});
			}
			else if(lyrTy == "LINESTRING") {
				windowObj.lineSettingObj.open(rules[i], {
					onSubmit : function(ruleObj) {
						rules[i] = ruleObj;
					}
				});
			}
			else if(lyrTy == "POLYGON") {
				windowObj.polygonSettingObj.open(rules[i], {
					onSubmit : function(ruleObj) {
						rules[i] = ruleObj;
					}
				});
			}
			else {
				$.messager.alert(that.TITLE, "[" + lyrTy + "] 정의 되지 않은 레이어 타입 입니다.");
			}
			return false;
		});
		
		// 축척 일괄 설정
		$(".div_scale .a_apply_scale", that.selector).click(function() {
			$.messager.confirm(that.TITLE, "설정한 유효축척이 모든 서브레이어에 공통 적용됩니다. 적용하시겠습니까?", function(isTrue) {
				if(isTrue) {
					var minScale = $(".div_scale .txt_min_scale", that.selector).numberspinner("getValue");
					var maxScale = $(".div_scale .txt_max_scale", that.selector).numberspinner("getValue");
					
					var rules = that.rulesList[that.record.fieldId];
					for(var i=0, len=rules.length; i < len; i++) {
						var rule = rules[i];
						if(rule.minScale) delete rule.minScale;
						if(rule.maxScale) delete rule.maxScale;
						
						if(minScale) rule.minScale = minScale;
						if(maxScale) rule.maxScale = maxScale;
					}
				}
			});
		});

		// 레이블 설정
		$(".a_setting_text_symbol", that.selector).click(function() {
			windowObj.labelSettingObj.open(that.textRule, {
				onSubmit : function(ruleObj) {
					that.textRule = ruleObj;
				}
			});
			return false;
		});
		
		// 면 투명도 일괄 적용
		$(".a_apply_polygon_opacity", that.selector).click(function() {
			$.messager.confirm(that.TITLE, "설정한 채우기 투명도가 모든 서브레이어에 공통 적용됩니다. 적용하시겠습니까?", function(isTrue) {
				if(isTrue) {
					var value = $(".txt_fill_opacity", that.selector).slider("getValue");
					var opacity = sldObj.convertOpacityToValue(value);
					
					var rules = that.rulesList[that.record.fieldId];
					for(var i=0, len=rules.length; i < len; i++) {
						var rule = rules[i];
						if(rule && rule.polygon) {
							rule.polygon.fillOpacity = opacity;
						}
					}
				}
			});
		});
		
		// 선 투명도 일괄 적용
		$(".a_apply_line_opacity", that.selector).click(function() {
			$.messager.confirm(that.TITLE, "설정한 외곽선 투명도가 모든 서브레이어에 공통 적용됩니다. 적용하시겠습니까?", function(isTrue) {
				if(isTrue) {
					var value = $(".txt_line_opacity", that.selector).slider("getValue");
					var opacity = sldObj.convertOpacityToValue(value);
					
					var rules = that.rulesList[that.record.fieldId];
					for(var i=0, len=rules.length; i < len; i++) {
						var rule = rules[i];
						if(rule && rule.polygon) {
							rule.polygon.strokeOpacity = opacity;
						}
						if(rule && rule.line) {
							rule.line.strokeOpacity = opacity;
						}
					}
				}
			});
		});
		
		
		// 설정
		$(".a_setting", that.selector).click(function() {
			if(that.onSubmit) {
				var textRule = null;
				var labelField = $(".div_label .sel_label_fields", that.selector).combobox("getValue");
				if(labelField != "NONE") {
					textRule = that.textRule;
					textRule.label = labelField;
				}
				that.onSubmit(that.rulesList[that.record.fieldId], textRule);
				that.close();
			}
		});
		
		// 닫기
		$(".a_close", that.selector).click(function() {
			that.close();
		});
	},
	
	/**
	 * 데이터 & 데이터 필드 정보 불러오기
	 */
	loadData : function() {
		var that = this;
//		that.data = dataObj.getDataByDataId(that.layerName, true);
		that.data = dataObj.getDataByDataId(that.dataId, true);
	},
	
	loadLayer : function() {
		var that = this;
		that.layer = layerObj.getLayerByLayerName(that.layerName);
	},
	
	/**
	 * 레이어명 표시
	 */
	loadTitle : function() {
		var that = this;
//		$(".div_title .span_content", that.selector).text(that.data.dataAlias);
		$(".div_title .span_content", that.selector).text(that.layerAlias);
	},
	
	/**
	 * 필드 목록 표시
	 */
	loadFields : function() {
		var that = this;
		
		var fields = [];
		
		// 기본 룰 추가
		fields.push({ fieldId : "DEFAULT", fieldAlias : "기본", type : "default" });
		
		var kwsDataFields = that.data.kwsDataFields;
		for(var i=0, len=kwsDataFields.length; i < len; i++) {
			var kwsDataField = kwsDataFields[i];
			var field = {
				fieldId : kwsDataField.fieldId,
				fieldAlias : kwsDataField.fieldAlias
			};
			if(kwsDataField.domnId) {
				field.type = "domain";
				field.domnId = kwsDataField.domnId;
			}
			if(field.type) {
				fields.push(field);
			}
		}
		$(".div_fields .sel_fields", that.selector).combobox("loadData", fields);
	},
	
	loadLabelFields : function() {
		var that = this;
		var fields = [];
		fields.push({ fieldId : "NONE", fieldAlias : "없음" });
		var kwsDataFields = that.data.kwsDataFields;
		for(var i=0, len=kwsDataFields.length; i < len; i++) {
			var field = kwsDataFields[i];
			if(field.pkAt != "Y" && field.fieldTy != "GEOMETRY") {
				fields.push(field);
			}
		}
		$(".div_label .sel_label_fields", that.selector).combobox("loadData", fields);
	},
	
	/**
	 * 현재 룰 설정
	 */
	setCurrentRule : function() {
		var that = this;
		
		// 현재 정의된 룰 찾기
		var ruleField = "DEFAULT";
		var namedLayer = sldObj.getNamedLayer(that.layerName);
		for(var i in namedLayer.rules) {
			if(namedLayer.rules.length > 0 && namedLayer.rules[i].filter) {
				ruleField = namedLayer.rules[i].filter.property;
			}
		}
		that.rulesList[ruleField] = $.extend(true, [], namedLayer.rules);
		$(".div_fields .sel_fields", that.selector).combobox("setValue", ruleField);
		
		// 현재 정의된 레이블 룰
		if(namedLayer.text) {
			that.textRule = namedLayer.text;
			$(".div_label .sel_label_fields", that.selector).combobox("setValue", namedLayer.text.label);
		}
		else {
			that.textRule = sldObj.createTextRule();
			$(".div_label .sel_label_fields", that.selector).combobox("setValue", "NONE");
		}
	},
	
	loadRules : function() {
		var that = this;
		$(".div_rule", that.selector).hide();
		if(that.record.type == "default") {
			that.loadDefaultRules();
			$(".a_setting_domain_cl", that.selector).hide();
			$(".div_rule_default", that.selector).show();
		}
		else if(that.record.type == "domain") {
			that.loadDomainRules();
			$(".a_setting_domain_cl", that.selector).show();
			$(".div_rule_domain", that.selector).show();
		}
		else {
			$.messager.alert(that.TITLE, "정의되지 않은 룰 타입입니다.");
		}
	},
	
	loadDefaultRules : function() {
		var that = this;
		var fieldId = that.record.fieldId;
		if(!that.rulesList[fieldId]) {
			that.rulesList[fieldId] = [];
			var symbolizer = sldObj.createSymbolizer(that.layer.lyrTy);
			var rule = sldObj.createRule("DEFAULT", "기본", null, symbolizer);
			that.rulesList[fieldId].push(rule);
		}
		
		var tagStr = '';
		var rules = that.rulesList[fieldId];
		for(var i=0, len=rules.length; i < len; i++) {
			var rule = rules[i];
			tagStr += that.createRuleTag(rule);
		}
		$(".div_rule_default ul").html(tagStr);
	},
	
	loadDomainRules : function(fieldId, domnId) {
		var that = this;
		
		var fieldId = that.record.fieldId;
		var domnId = that.record.domnId;
		
		if(!that.rulesList[fieldId]) {
			that.rulesList[fieldId] = [];
			var symbolizer = sldObj.createSymbolizer(that.layer.lyrTy);
			var domnCodes = domnCodeObj.getData(domnId);
			for(var i=0, len=domnCodes.length; i < len; i++) {
				var domnCode = domnCodes[i];
				var filter = that.createDomainFilter(fieldId, domnCode.codeId);
				var rule = sldObj.createRule(domnCode.codeId, domnCode.codeNm, filter, symbolizer);
				that.rulesList[fieldId].push(rule);
			}
		}
		
		var tagStr = '';
		var rules = that.rulesList[fieldId];
		for(var i=0, len=rules.length; i < len; i++) {
			var rule = rules[i];
			tagStr += that.createRuleTag(rule);
		}
		$(".div_rule_domain ul").html(tagStr);
	},
	
	applyDomainRules : function(domnCodes) {
		var that = this;
		
		var fieldId = that.record.fieldId;
		var newRules = [];
		var rules = that.rulesList[fieldId];
		
		// 이전에 설정된 심볼 설정이 있을 경우 그대로 사용하고 없을 경우 디폴트로 생성함.
		var symbolizer = sldObj.createSymbolizer(that.layer.lyrTy);
		for(var i=0, len=domnCodes.length; i < len; i++) {
			var newRule = null;
			var domnCode = domnCodes[i];
			for(var j=0, jLen=rules.length; j < jLen; j++) {
				var rule = rules[j];
				if(rule.name == domnCode.codeId) {
					newRule = rule;
					break;
				}
			}
			if(!newRule) {
				var filter = that.createDomainFilter(fieldId, domnCode.codeId);
				newRule = sldObj.createRule(domnCode.codeId, domnCode.codeNm, filter, symbolizer);
			}
			newRules.push(newRule);
		}
		
		that.rulesList[fieldId] = newRules;
		that.loadDomainRules();
	},
	
	createRuleTag : function(rule) {
		var tagStr = '';
		tagStr += '<li class="clearfix">';
		tagStr += '<span class="span_title">' + rule.title + '</span>';
		tagStr += '<a href="#" class="a_setting_symbol" data-rule-name="' + rule.name + '" >';
		tagStr += '<img src="' + CONTEXT_PATH + '/images/kworks/map/common/layer_icon2_ov.png" alt="필드 설정" title="필드 설정" />';
		tagStr += '</a>';
		tagStr += '</li>';
		return tagStr;
	},
	
	createDomainFilter : function(property, value) {
		var filter = {
			type : "==",
			property : property,
			literal : value
		};
		return filter;
	}
	
};