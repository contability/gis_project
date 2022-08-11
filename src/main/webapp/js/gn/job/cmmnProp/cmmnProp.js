var cmmnPropObj = {

	TITLE : "공유재산 검색",
	
	CLASS_NAME : "CommonPropertySearch",
	
	selector : null,
	
	isClosed : false,
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var windowOptions = {
			width :	1000,
			height : 218,
			top : 120,
			left : 330,
			onClose : function() {
				that.close();
			}
		};
		
		var url = CONTEXT_PATH + '/cmmnprop/searchMainPage.do';
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.init();
			that.bindEvents();
		});
			
		that.selector = "#" + id;
		
	},
	
	initUi : function(){
		var that = this;
		
		$('#comboBx_emd', that.selector).combobox({
			valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD)
		});
		
		$('#comboBx_cmmn', that.selector).combobox({
			valueField : 'value',
			textField : 'label',
			data:[{
				value : 'BML_PROP_AS,BML_BUID_AS',
				label : ''
			},{
				value:'BML_PROP_AS',
				label:'토지'
			},{
				value:'BML_BUID_AS',
				label:'건물'
			}],
			width : 362
		});
		
		$('#comboBx_accCde', that.selector).combobox({
			valueField : 'accCde',
			textField : 'accNm',
			width : 362
		});
		
		$('#comboBx_bmlCde', that.selector).combobox({
			valueField : 'codeId',
			textField : 'codeNm',
			width : 362
		});
		
		$('#comboBx_manCde', that.selector).combobox({
			width : 362,
			valueField : 'manCde',
			textField : 'manNm'
		});
		
		$('.swtchBtn', that.selector).switchbutton();
		
		$('.numSpn', that.selector).numberspinner({
			min : 0,
			max: 999999
		});
	},
	
	init : function(){
		var that = this;
		
		var dongList = dongObj.getData();
		var emdData = [];
		
		for(var i = 0 ; i < dongList.length; i++){
			emdData.push(dongList[i]);
		}
		
		emdData.unshift({
			bjdCde : '',
			bjdNam : ''
		});
		
		$('#comboBx_emd', that.selector).combobox('loadData', emdData);
		
		var jimokList = domnCodeObj.getData('KWS-0452');
		var jimokData = [];
		
		for(var i = 0; i < jimokList.length; i++){
			jimokData.push(jimokList[i]);
		}
		
		jimokData.unshift({
			codeId : '',
			codeNm : ''
		});
		
		$('#comboBx_bmlCde', that.selector).combobox('loadData', jimokData);
		
		$('#comboBx_cmmn', that.selector).combobox('select','BML_PROP_AS,BML_BUID_AS');
		
		that.initManList();
		that.initAccList();
	},
	
	initManList : function(){
		 var that = this;
	
		var dept = that.getDept();
		
		$.ajax({
			url : CONTEXT_PATH + '/cmmnprop/' + that.dataId + '/selectManGroupList.do',
			type : 'GET',
			dataType : 'json',
			success : function(rs){
				if(rs && rs.rows){
					var manList = rs.rows;
					
					manList.unshift({
						manCde : '',
						manNm : ''
					});
					
					$('#comboBx_manCde', that.selector).combobox('loadData', manList);
					
					for(var i = 0; i < manList.length; i++){
						if(dept.code + '0000' == manList[i].manCde){
							$('#comboBx_manCde', that.selector).combobox('select', manList[i].manCde);
						}
					}
				}
			}, error : function(err){
				console.error(err);
			}
		});
	},

	bindEvents : function() {
		var that = this;
		
		// 초기화
		$("#btn_reset", that.selector).on("click", function() {
			that.clean();
			return false;
		});

		// 검색
		$("#btn_search", that.selector).on("click", function() {
			that.search();
			return false;
		});
	},
	
	initAccList : function () {
		var that = this;
		
		$.ajax({
			url : CONTEXT_PATH + '/cmmnprop/selectAccGroupList.do',
			dataType : 'json',
			type : 'GET',
			success : function(result){
				if(result && result.rows){
					result.rows.unshift({
						accNm : '',
						accCde : ''
					})
					
					$('#comboBx_accCde', that.selector).combobox('loadData', result.rows);
					$('#comboBx_accCde', that.selector).combobox('select', '');
				}
			},error : function(err){
				console.error(err);				
			}
				
			
		});
	},
	
	search : function(){
		var that = this;
		
		var paramData = {};
		
		paramData.emd = $('#comboBx_emd', that.selector).combobox('getValue');
		paramData.mntn = $('#mntn', that.selector).switchbutton('options').checked? '2' : '1' ;
		paramData.mnnm = $('#mnnm', that.selector).numberspinner('getValue');
		paramData.slno = $('#slno', that.selector).numberspinner('getValue');
		paramData.bmlCde = $('#comboBx_bmlCde', that.selector).combobox('getValue');
		paramData.accCde = $('#comboBx_accCde', that.selector).textbox('getText');
		paramData.manCde = $('#comboBx_manCde', that.selector).combobox('getValue');
		
		var dataIds = $('#comboBx_cmmn', that.selector).combobox('getValue');
		
		var filter = {};
		
		resultObj.close();
		
		if(dataIds.indexOf(',') > -1){
			dataIds = dataIds.split(',');
		}
		
		if(Array.isArray(dataIds)){
			if(dataIds.length > 0){
				for(var i = 0; i < dataIds.length; i++){
					if(paramData.bmlCde != '' && paramData.bmlCde != null){
						if(dataIds[i] == 'BML_BUID_AS'){
							continue;
						}else{
							filter = that.createCqlFilter(paramData, dataIds[i]);
							that.searchRes(dataIds[i], filter);
						}
					}else{
						filter = that.createCqlFilter(paramData, dataIds[i]);
						that.searchRes(dataIds[i], filter);
					}
				}
			}
		}else{
			if(paramData.bmlCde != '' && paramData.bmlCde != null){
				if(dataIds == 'BML_BUID_AS'){
					$.messager.alert(that.TITLE, "건물 재산은 지목으로 검색할 수 없습니다.");
					return false;
				}else{
					filter = that.createCqlFilter(paramData, dataIds);
					that.searchRes(dataIds, filter);
				}
			}else{
				filter = that.createCqlFilter(paramData, dataIds);
				that.searchRes(dataIds, filter);
			}
		}
	},
	
	syncAjaxSummaries : function(filter, dataId){
		var rows = null;
		
		var kwsData = dataObj.getDataByDataId(dataId, false);
		
		filter['dataIds'] = dataId;
		
		$.ajax({
			url : CONTEXT_PATH + '/rest/spatial/searchSummaries.do',
			type : 'POST',
			data : filter,
			dataType : 'json',
			async : false,
			success : function(rs){
				if(rs && rs.rows) {
					if(rs.rows.length > 0) {
						rows = rs.rows;
					}else {
						//$.messager.alert(kwsData.dataAlias, "검색 결과가 없습니다.");
						//return false;
					}
				}
				else {
					$.messager.alert(kwsData.dataAlias, "공간 정보를 불러오는데 실패 했습니다.");
				}
			},
			error : function(err){
				console.error(err);
			}
		});
		
		return rows;
	},
	
	syncAjaxListAll : function(filter, dataId){
		var rows = null;
		
		var kwsData = dataObj.getDataByDataId(dataId, false);
		
		$.ajax({
			url : url = CONTEXT_PATH + "/rest/spatial/" + dataId + "/listAll.do",
			type : 'POST',
			data : filter,
			dataType : 'json',
			async : false,
			success : function(rs){
				if(rs && rs.rows) {
					if(rs.rows.length > 0) {
						rows = rs.rows;
					}else {
							//$.messager.alert(kwsData.dataAlias, "검색 결과가 없습니다.");
					}
				}
				else {
					$.messager.alert(kwsData.dataAlias, "정보를 불러오는데 실패 했습니다.");
				}
			},
			error : function(err){
				console.error(err);
			}
		});
		
		return rows;
	},
	
	getDept : function(){
		
		var dept = {};
		
		$.ajax({
			url : CONTEXT_PATH + '/cmmnprop/getDept.do',
			type : 'GET',
			dataType : 'json',
			async : false,
			success : function(rs){
				if(rs.deptCode && rs.deptNm){
					dept.code = rs.deptCode;
					dept.name = rs.deptNm;
				}
			},
			error : function(err){
				console.error(err);
			}
		});
		
		return dept;
	},
	
	searchRes : function(dataId, filter, index){
		var that = this;
		
		var rows = that.syncAjaxSummaries(filter, dataId);
		
		var kwsData = dataObj.getDataByDataId(dataId, false);
		
		if(rows){
			that.close();
			cmmnLoanObj.close();
			cmmnOccpObj.close();
			cmmnAcexObj.close();
			
			$('.a_menu_collapse').trigger('click');
			
			var option = {
					dataId : dataId,
					eventObj : cmmnPropObj.resultObj.bindEvents
			};
			
			resultObj.addRows(rows, option);
			
		}else{
			$.messager.alert(kwsData.dataAlias, '검색 결과가 없습니다.');
		}	
	},
	
	createCqlFilter : function(param, dataId){
		var cql = '';
		
		/*if((param.emd == '' || param.emd == null) && (param.mntn == '1') && (param.mnnm == '' || param.mnnm == null) && (param.slno == '' || param.slno == null) &&  (param.bmlCde == '' || param.bmlCde == null) && (param.accCde == '' || param.accCde == null) && (param.manCde == '' || param.manCde == null)){
			cql = ' 1 = 1 ';
		}else{*/
			if(param.emd != '' && param.emd != null){
				cql += " AND BJD_CDE = '" + param.emd + "' ";
			}
			
			if(param.mntn != '' && param.mntn != null){
				if((param.emd != '' && param.emd != null && param.mnnm != '' && param.mnnm != null) || param.mntn == '2') {
					cql += " AND MONUT = '" + param.mntn + "' ";
				}
			}
			
			if(param.mnnm != '' && param.mnnm != null){
				cql += " AND BUN = '" + param.mnnm + "' ";
			}
			
			if(param.slno != '' && param.slno != null){
				cql += " AND HO = '" + param.slno + "' ";
			}
			
			if(dataId.indexOf('BML_BUID_AS') < 0){
				if(param.bmlCde != '' && param.bmlCde != null){
					cql += " AND BML_CDE LIKE '" + param.bmlCde + "' ";
				}
			}
			
			if(param.accCde != '' && param.accCde != null){
				cql += " AND ACC_NM = '" + param.accCde + "' ";
			}
			
			if(param.manCde != '' && param.manCde != null){
				cql += " AND MAN_CDE = '" + param.manCde + "' ";
			}
			
			cql += " AND GEOM IS NOT NULL "; 
			
			cql = cql.replace(' AND ', '');
		//}
		
		var filter = {
			filterType : 'CQL',
			cql : cql
		};
	
		return filter;
		
	},
	
	resizeCmmnPropResultHandler : function(selector){
		var conWidth = $('#container').width();
		var conHeight = $('#container').height();
		
		var menuWidth = $('#div_menu').width();
		
		var selectorWidth = conWidth - menuWidth;
		
		var style = {};
		
		var panelHeight = $(selector).window('panel').height();
		
		if($('#div_menu_panel').is(':visible')){
			var menuPanelWid = $('#div_menu_panel').width();
			selectorWidth = selectorWidth - menuPanelWid;
			
			style = {
					width : selectorWidth + 1,
					left : menuPanelWid,
					top : conHeight - panelHeight - 9
				};
			
		}else{
			style = {
					width : selectorWidth,
					left : 0,
					top : conHeight - panelHeight - 9
				};
		}
		
		$(selector).window('resize',style);
	},
	
	clean : function() {
		var that = this;
		
		$('#comboBx_emd', that.selector).combobox('select', '');
		$('#comboBx_bmlCde', that.selector).combobox('select','');
		$('#comboBx_accCde', that.selector).combobox('select', '');
		$('#comboBx_cmmn', that.selector).combobox('select','BML_PROP_AS,BML_BUID_AS');
		$('#comboBx_manCde', that.selector).combobox('select','');
		$('.swtchBtn', that.selector).switchbutton('clear');
		$('.numSpn', that.selector).numberspinner('setValue','');
	},
	
	close : function() {
		var that = this;
		
		that.clean();
		windowObj.removeWindow(that.selector);
	}	
	
};

// 공유재산 포털 알림
cmmnPropObj.portalNotification = {
	
	loanImmCount : 0,
	
	mismatchPropCount : 0,
	
	mismatchBuidCount : 0,
	
	init : function(){
		var that = this;
		
		$('#acin_notification_prop').dialog({
			title : '지번불부합 공유재산',
			autoOpen : false,		
			resizable : false,	
			show: {				 
				effect : 'drop',	
				duration: 500		
			},
			hide: {				
				effect: 'drop',		
				duration: 500		
			}
		});
		
		$('#acin_notification_loan').dialog({
			title : '대부기간 만료 임박 알림',
			autoOpen : false,
			resizable : false,
			show: {
				effect : 'drop',
				duration: 500
			},
			hide: {
				effect: 'drop',
				duration: 500
			}
		});
		
		$.ajax({
			url : CONTEXT_PATH + '/cmmnprop/' + COMMON.BUID + '|' + COMMON.PROP + '|' + COMMON.LOAN + '/portalNotification.do',
			type : 'GET',
			dataType : 'json',
			success : function(rs){
				if(rs.loanImmCount > 0|| rs.mismatchBuid > 0 || rs.mismatchProp > 0){
					var loanImmCount = rs.loanImmCount;
					var mismatchBuid = rs.mismatchBuid;
					var mismatchProp = rs.mismatchProp;
					
					that.loanImmCount = rs.loanImmCount;
					that.mismatchBuidCount = rs.mismatchBuid;
					that.mismatchPropCount = rs.mismatchProp;
					
					var dialogWidth = 500;
					var dialogHeight = 180;
					var offSet = 0;
					
					var dialogTop = 0;
					
					if(mismatchBuid > 0 || mismatchProp > 0){
						$('#acin_notification_prop_total').html(mismatchBuid + mismatchProp);
						
						if(mismatchProp > 0){
							$('#acin_notification_prop').append('<div> - 토지재산 : [<span id="acin_notification_prop_count">' + mismatchProp + '</span>] 건</div>');
						}
						
						if(mismatchBuid > 0){
							$('#acin_notification_prop').append('<div> - 건물재산 : [<span id="acin_notification_buid_count">' + mismatchBuid + '</span>] 건</div>');
						}
						
						$('#acin_notification_prop').dialog('option','height', dialogHeight);
						$('#acin_notification_prop').dialog('option','width', dialogWidth);
						
						
						$('#acin_notification_prop').dialog('option','position',{my:'right', at:'right bottom-'+offSet, of:window});
						
						dialogTop = dialogHeight + offSet;
						
						$('#acin_notification_prop').dialog('open');
					}
					
					if(loanImmCount > 0){
						$('#acin_notification_loan_count').html(loanImmCount);
						
						$('#acin_notification_loan').dialog('option','height', dialogHeight);
						$('#acin_notification_loan').dialog('option','width', dialogWidth);
						$('#acin_notification_loan').dialog('option','position',{my:'right', at:'right bottom-' + dialogTop, of:window});	
						
						$('#acin_notification_loan').dialog('open');
					}
					
					
					$('.notification_prop').css('cursor', 'pointer');
				}
			},error : function(err){
				console.error(err);
			}
		});
		
		that.bindEvents();
	},
	
	bindEvents : function(){
		var that = this;
		
		$('#acin_notification_loan').click(function(){
			that.exportExcel('BML_LOAN_AS');
		});
		
		$('#acin_notification_prop').click(function(){
			var dataIds =[];
			var dataStr = '';
			
			if(that.mismatchPropCount > 0){
				dataIds.push('BML_PROP_AS');
			}
			
			if(that.mismatchBuidCount > 0){
				dataIds.push('BML_BUID_AS');
			}
			
			for(var i = 0; i < dataIds.length; i++){
				dataStr += dataIds[i];
				if(i != dataIds.length - 1){
					dataStr += '|';
				}
			}
			
			that.exportExcel(dataStr);
		});
		
		$('.notification_prop').hover(function(){
			$(this).css('background-color', '#76bdda');
			$(this).css('color', '#ffffff');
		}, function(){
			$(this).css('background-color', '#ffffff');
			$(this).css('color', '#333333');
		})
	},
	
	exportExcel : function(dataId){
		var that = this;
		
		location.href = CONTEXT_PATH + '/cmmnprop/' + dataId + '/exportExcel.do';
	}
};

unKnownCmmnPropObj = {
		TITLE : "주소 불일치 재산 검색",
		
		CLASS_NAME : "unKnownCommonPropertySearch",
		
		search : function(){
			var that = this;
			
			var dataIds = ['BML_PROP_AS', 'BML_BUID_AS', 'BML_LOAN_AS', 'BML_OCCP_AS', 'V_BML_ACEX_AS'];
			var filter = {
					filterType : 'CQL'
			};
			
			resultObj.close();
			var dept = cmmnPropObj.getDept();
			var cql = " GEOM IS NULL ";
			if(dept.code && dept.code != '4200245'){
				cql += " AND MAN_CDE = " + dept.code + "0000 ";
			}
			
			filter.cql = cql;
			
			if(dataIds.length > 0){
				for(var i = 0; i < dataIds.length; i++){
					that.searchRes(dataIds[i], filter);
				}
			}
		},
		
		searchRes : function(dataId, filter, index){
			var rows = cmmnPropObj.syncAjaxSummaries(filter, dataId);
			
			var kwsData = dataObj.getDataByDataId(dataId, false);
			
			if(rows){
				cmmnLoanObj.close();
				cmmnOccpObj.close();
				$('.a_menu_collapse').trigger('click');
				
				var option = {
						dataId : dataId,
						eventObj : cmmnPropObj.resultObj.bindEvents
				};
				
				resultObj.addRows(rows, option);
				
			}else{
				$.messager.alert(kwsData.dataAlias, '검색 결과가 없습니다.');
			}	
		}
};

cmmnPropObj.view ={
		
	TITLE : "tabView",
		
	CLASS_NAME : "CommonPropertyTabView",
		
	selector : '',
		
	dataId : '',
		
	data : {},
		
	init: function(prtIdn, dataId, selector){
		var that = this;
			
		that.dataId = dataId;
		that.selector = selector;
			
		that.dataLoading(prtIdn);
		//that.createMap();
			
		that.initUi();
		that.bindEvents();
	},
		
	initUi : function(){
		var that = this;
		
		$('.cmmn_tabs', that.selector).tabs();
	},
	
	bindEvents : function(){
		var that = this;
		
		$('#btn_loan_selectOne', that.selector).click(function(){
			that.setSelector();
			
			var row = $('.grid_result_loan', that.selector).datagrid('getSelected');
			
			if(!row){
				$.messager.alert('대부 및 사용허가', '행을 선택해주세요.');
			}else{
				that.openAtchTab('BML_LOAN_AS');
			}
		});
		
		$('#btn_occp_selectOne', that.selector).click(function(){
			that.setSelector();
			
			var row = $('.grid_result_occp', that.selector).datagrid('getSelected');
			if(!row){
				$.messager.alert('무단점유 사용자', '행을 선택해주세요.');
			}else{
				that.openAtchTab('BML_OCCP_AS');
			}
		});
		
		/*$('#btn_acex_selectOne', that.selector).click(function(){
			that.setSelector();
			
			var row = $('.grid_result_acex', that.selector).datagrid('getSelected');
			if(!row){
				$.messager.alert('실태조사', '행을 선택해주세요.');
			}else{
				that.openAtchTab('V_BML_ACEX_AS');
			}
		});*/
	},
	
	openAtchTab : function(dataId){
		var that = this;
		
		var row = null;
		var kwsData = dataObj.getDataByDataId(dataId, false);
		var alias = kwsData.dataAlias;
		var callback = null;
		var url = CONTEXT_PATH + '/cmmnprop/' + dataId + '/detailPage.do';
		
		if(dataId.indexOf('BML_LOAN_AS') > -1){
			row = $('.grid_result_loan', that.selector).datagrid('getSelected');
			callback = function(row, selector){
				cmmnLoanObj.view.init(row.lonIdn, dataId, selector, row.pbpKnd, row.prtIdn);
			};
		}else if(dataId.indexOf('BML_OCCP_AS') > -1){
			row = $('.grid_result_occp', that.selector).datagrid('getSelected');
			callback = function(row, selector){
				cmmnOccpObj.view.init(row.ocpIdn, dataId, selector, row.pbpKnd, row.prtIdn);
			};
		}/*else if(dataId.indexOf('V_BML_ACEX_AS') > -1){
			row = $('.grid_result_acex', that.selector).datagrid('getSelected');
			callback = function(row, selector){
				cmmnAcexObj.view.init(row.prtIdn, selector, row.pbpKnd, dataId);
			};
		}*/
		
		tabObj.createTab(dataId, alias, row, url, callback);
	},
	
	setSelector : function(){
		var that = this;
		
		that.selector = '#' + tabObj.getId();
	},
	
	dataLoading : function(prtIdn){
		var that = this;
		
		var url = CONTEXT_PATH + '/cmmnprop/' + prtIdn + '/searchPropData.do';
		
		$.ajax({
			type : 'GET',
			dataType : 'json',
			url : url,
			async : false,
			success : function(rs){
				if(rs && rs.row){
					var row = rs.row;
					
					var data = dataObj.getDataByDataId(that.dataId, true);
					var dataField = data.kwsDataFields;
					
					for(var i = 0; i < dataField.length; i++){
						if(dataField[i].indictTy.indexOf('CURRENCY') > -1){
							row[camelcaseUtils.underbarToCamelcase(dataField[i].fieldId)] = numberUtils.formatCurrency(row[camelcaseUtils.underbarToCamelcase(dataField[i].fieldId)]);
						}
					}
					
					$('#prop_prtIdn', that.selector).html(row.prtIdn);
					$('#prop_pbpKnd', that.selector).html('토지');
					$('#prop_prtNam', that.selector).html(row.prtNam);
					$('#prop_ownCde', that.selector).html(row.ownNm);
					$('#prop_prsCde', that.selector).html(row.prsCde);
					$('#prop_amnCde', that.selector).html(row.amnCde);
					$('#prop_accCde', that.selector).html(row.accNm);
					$('#prop_manCde', that.selector).html(row.manNm);
					$('#prop_chrNam', that.selector).html(row.chrNam);
					$('#prop_mndCde', that.selector).html(row.mndNm);
					$('#prop_bjdCde', that.selector).html(row.bjdCde);
					$('#prop_locPlc', that.selector).html(row.locPlc);
					$('#prop_monut', that.selector).html(row.monut);
					$('#prop_bun', that.selector).html(row.bun);
					$('#prop_ho', that.selector).html(row.ho);
					$('#prop_spcAdr', that.selector).html(row.spcAdr);
					$('#prop_spcDong', that.selector).html(row.spcDong);
					$('#prop_spcHo', that.selector).html(row.spcHo);
					$('#prop_rnAddr', that.selector).html(row.rnAddr);
					$('#prop_ptyPc', that.selector).html(row.ptyPc);
					$('#prop_autAmnt', that.selector).html(row.autAmnt);
					$('#prop_acqDept', that.selector).html(row.acqDept);
					$('#prop_acqPc', that.selector).html(row.acqPc);
					$('#prop_acqDate', that.selector).html(row.acqDate);
					$('#prop_acqCde', that.selector).html(row.acqCde);
					$('#prop_acqPrv', that.selector).html(row.acqPrv);
					$('#prop_rstYn', that.selector).html(row.rstYn);
					$('#prop_loanYn', that.selector).html(row.loanYn);
					$('#prop_saleLmt', that.selector).html(row.saleLmt);
					$('#prop_rmkExp', that.selector).html(row.rmkExp);
					$('#prop_bmlCde', that.selector).html(row.jimokNm);
					$('#prop_mokCde', that.selector).html(row.rjimokNm);
					$('#prop_area', that.selector).html(row.area);
					$('#prop_parea', that.selector).html(row.parea);
					$('#prop_olnlp', that.selector).html(row.olnlp);
					$('#prop_acqAra', that.selector).html(row.acqAra);
					$('#prop_cnrQta', that.selector).html(row.cnrQta);
					$('#prop_spfc', that.selector).html(row.spfc);
					$('#prop_ctyPlan', that.selector).html(row.ctyPlan);
					$('#prop_planFty', that.selector).html(row.planFty);
					$('#prop_dwk', that.selector).html(row.dwk);
					$('#prop_planBns', that.selector).html(row.planBns);
					$('#prop_rstYn', that.selector).html(row.rstYn);
					$('#prop_planBns', that.selector).html(row.planBns);
					if(row.pstNum){
						$('#prop_pstNum', that.selector).html('(등기번호 : ' + row.pstNum + ')');
					}
					
					var id = tabObj.getId();
					
					if(!that.data[id]){
						that.data[id] = {
								loan : null,
								occp : null,
								acex : null
						};
					}
					
					that.loanResultLoading(row.prtIdn, row.pbpKnd);
					that.occpResultLoading(row.prtIdn, row.pbpKnd);
					that.acexResultLoading(row.prtIdn, row.pbpKnd);
				}
			}, error : function(err){
				console.error(err);
			}
		});
	},
		
	/*createMap : function() {
		var that = this;
		that.map = new kworks.IndexMap({
			map : mapObj.getMap(),
			target : 'index_map_cmmn',
			layers : [
				new ol.layer.Image({
					id : "kc_index",
		            source: new kworks.source.ImageWMS({
		              url: CONTEXT_PATH + "/proxy/wms.do",
		              params : $.extend(serverObj.getWMSParams(), {
		                  'LAYERS' : serverObj.getBlankLayers(),
		                  'STYLES' : serverObj.getBlankLayers(),
		            	  'FORMAT' : "image/jpeg"
		              }),
		              ratio : 1,
		              serverType: MAP.SERVER_TYPE
		            })
		        })],
				mode : "MAX",
				view : new ol.View({
					projection : new ol.proj.get(MAP.PROJECTION),
					maxResolution : INDEX_MAP.MAX_RESOLUTION,
					center : [INDEX_MAP.CENTER_X, INDEX_MAP.CENTER_Y],
					zoom : 0
				})
		});
		
		var layer = that.map.getLayer("kc_index");
		var layerNames = [caseUtils.getText(INDEX_MAP.LAYER)];
		sldObj.reloadWMSFromSld("IndexMap", "인덱스맵", layer, layerNames);
		
	},*/
	
	loadDetail : function(dataId){
		var that = this;
		var gridSelector = null;
		
		that.setSelector();
		var id = tabObj.getId();
		
		var data = null;
		
		if(dataId.indexOf('BML_LOAN_AS') > -1){
			data = that.data[id]['loan'];
			gridSelector = '.grid_result_loan';
		}else if(dataId.indexOf('BML_OCCP_AS') > -1){
			data = that.data[id]['occp'];
			gridSelector = '.grid_result_occp';
		}/*else if(dataId.indexOf('V_BML_ACEX_AS') > -1){
			data = that.data[id]['acex'];
			gridSelector = '.grid_result_acex';
		}*/
		
		
		var firstIndex = pagingUtils.getFirstIndex(data.pageNumber, data.pageSize);
		var lastIndex = pagingUtils.getLastIndex(data.pageNumber, data.pageSize);
		var ids = data.ids.slice(firstIndex, lastIndex);
		
		var filter = {
			filterType : 'FIDS',
			fids : ids
		};
		
		var rows = cmmnPropObj.syncAjaxListAll(filter, dataId);
		
		var gridData = {
				total : data.ids.length,
				rows : rows
		};
		
		$(gridSelector, that.selector).datagrid('loadData', gridData);
	},
	
	acexResultLoading : function(prtIdn, pbpKnd){
		var that = this;
		
		var dataId = 'V_BML_ACEX_AS';
		
		var kwsData = dataObj.getDataByDataId(dataId, true);
		var kdf = kwsData.kwsDataFields;
		
		var pkCol = '';
		
		var cols = [];
		var frozenCol = [];
		
		var id = tabObj.getId();
		
		for(var i = 0; i < kdf.length; i++){
			if(kdf[i].pkAt.indexOf('Y') > -1){
				pkCol = camelcaseUtils.underbarToCamelcase(kdf[i].fieldId);
				
				frozenCol.push({
					field : pkCol,
					title : '번호',
					width : 100,
					sortable : true
				});
			}else if(kdf[i].indictTy.indexOf('HIDE') < 0 && kdf[i].indictTy.indexOf('WKT') < 0){
				cols.push({
					field : camelcaseUtils.underbarToCamelcase(kdf[i].fieldId),
					title : kdf[i].fieldAlias,
					width : 100,
					sortable : false
				});
			}
		}
		
		/*$('.grid_result_acex', that.selector).datagrid({
			pk : pkCol,
			frozenColumns : [frozenCol],
			columns : [cols],
			singleSelect : true,
			showFooter : false,
			pagination : true
		});*/
		
		var cql = " PRT_IDN = " + prtIdn + " AND PBP_KND = " + pbpKnd;
		
		var filter = {
			filterType : 'CQL',
			cql : cql
		};
		
		var rows = cmmnPropObj.syncAjaxSummaries(filter, dataId);
		
		if(rows && rows.length > 0){
			that.data[id]['acex'] = {
					pageNumber : 1,
					pageSize : 10,
					ids : rows[0].ids
			};
			that.loadDetail(dataId);
		}
		
		/*var pagination = $('.grid_result_acex', that.selector).datagrid('getPager');
		pagination.pagination({
			displayMsg : '{from} - {to} / {total}',
			onSelectPage : function(pageNumber, pageSize){
				that.data[id]['acex'].pageNumber = pageNumber;
				that.data[id]['acex'].pageSize = pageSize;
				that.loadDetail(dataId);
			}
		});*/
	},
	
	loanResultLoading : function(prtIdn, pbpKnd){
		var that = this;
		
		var dataId = 'BML_LOAN_AS';
		
		var kwsData = dataObj.getDataByDataId(dataId, true);
		var kdf = kwsData.kwsDataFields;
		
		var pkCol = '';
		
		var cols = [];
		var frozenCol = [];
		
		var id = tabObj.getId();
		
		for(var i = 0; i < kdf.length; i++){
			if(kdf[i].pkAt.indexOf('Y') > -1){
				
				pkCol = camelcaseUtils.underbarToCamelcase(kdf[i].fieldId);
				
				frozenCol.push({
					field : pkCol,
					title : '번호',
					width : 100,
					sortable : true
				});
			}else if(kdf[i].indictTy.indexOf('HIDE') < 0 && kdf[i].indictTy.indexOf('WKT') < 0){
				cols.push({
					field : camelcaseUtils.underbarToCamelcase(kdf[i].fieldId),						
					title : kdf[i].fieldAlias,
					width : 100,
					sortable : false
				});
			}
		}
		
		$('.grid_result_loan', that.selector).datagrid({
			pk : pkCol,
			frozenColumns : [frozenCol],
			columns : [cols],
			singleSelect : true,
			showFooter : false,
			pagination : true,
		});
		
		var cql = " PRT_IDN = " + prtIdn + " AND PBP_KND = '" + pbpKnd + "' ";
		
		var filter = {
				filterType : 'CQL',
				cql : cql
		};
		
		var rows = cmmnPropObj.syncAjaxSummaries(filter, dataId);
		
		if(rows && rows.length > 0){
			that.data[id]['loan'] = {
				pageNumber : 1,
				pageSize : 10,
				ids : rows[0].ids
			};
			that.loadDetail(dataId);
		}
			
		var pagination = $('.grid_result_loan', that.selector).datagrid('getPager');
		pagination.pagination({
			displayMsg : '{from} - {to} / {total}',
			onSelectPage : function(pageNumber, pageSize){
				that.data[id]['loan'].pageNumber = pageNumber;
				that.data[id]['loan'].pageSize = pageSize;
				that.loadDetail(dataId);
			}
		});
	},
	
	occpResultLoading : function(prtIdn, pbpKnd){
		var that = this;
		
		var dataId = 'BML_OCCP_AS';
		
		var kwsData = dataObj.getDataByDataId(dataId, true);
		var kdf = kwsData.kwsDataFields;
		
		var pkCol = '';
		
		var cols = [];
		var frozenCol = [];
		
		var id = tabObj.getId();
		
		for(var i = 0; i < kdf.length; i++){
			if(kdf[i].pkAt.indexOf('Y') > -1){
				
				pkCol = camelcaseUtils.underbarToCamelcase(kdf[i].fieldId);
				
				frozenCol.push({
					field : pkCol,
					title : '번호',
					width : 100,
					sortable : true
				});
			}else if(kdf[i].indictTy.indexOf('HIDE') < 0 && kdf[i].indictTy.indexOf('WKT') < 0){
				cols.push({
					field : camelcaseUtils.underbarToCamelcase(kdf[i].fieldId),						
					title : kdf[i].fieldAlias,
					width : 100,
					sortable : false
				});
			}
		}
		
		$('.grid_result_occp', that.selector).datagrid({
			pk : pkCol,
			frozenColumns : [frozenCol],
			columns : [cols],
			singleSelect : true,
			showFooter : false,
			pagination : true,
		});
		
		var cql = " PRT_IDN = " + prtIdn + " AND PBP_KND = '" + pbpKnd + "' ";
		
		var filter = {
				filterType : 'CQL',
				cql : cql
		};
		
		var rows = cmmnPropObj.syncAjaxSummaries(filter, dataId);
		
		if(rows && rows.length > 0){
			
			that.data[id]['occp'] = {
				pageNumber : 1,
				pageSize : 10,
				ids : rows[0].ids
			};
			that.loadDetail(dataId);
			
		}
			
		var pagination = $('.grid_result_occp', that.selector).datagrid('getPager');
		pagination.pagination({
			displayMsg : '{from} - {to} / {total}',
			onSelectPage : function(pageNumber, pageSize){
				
				that.data[id]['occp'].pageNumber = pageNumber;
				that.data[id]['occp'].pageSize = pageSize;
				
				that.loadDetail(dataId);
			}
		});
	}
};

cmmnPropObj.resultObj = {
	bindEvents : function(){
		// 항목 설정
		$(".a_setting_item", resultObj.selector).off();
		$(".a_setting_item", resultObj.selector).click(function() {
			resultObj.settingItem();
			return false;
		});

		// 추가 선택
		$(".a_addition_choice", resultObj.selector).off();
		$(".a_addition_choice", resultObj.selector).click(function() {
			resultObj.additionChoice();
			return false;
		});

		// 선택 제거
		$(".a_remove_rows", resultObj.selector).off();
		$(".a_remove_rows", resultObj.selector).click(function() {
			resultObj.removeRows();
			return false;
		});

		// 조건 검색
		$(".a_search_condition", resultObj.selector).off();
		$(".a_search_condition", resultObj.selector).click(function() {
			resultObj.searchCondition();
			return false;
		});

		// 연관 검색
		$(".a_search_relation", resultObj.selector).off();
		$(".a_search_relation", resultObj.selector).click(function() {
			resultObj.searchRelation();
			return false;
		});

		// 목록 위치
		$(".a_move_location", resultObj.selector).off();
		$(".a_move_location", resultObj.selector).click(function() {
			resultObj.moveLocation();
			return false;
		});
		
		// 대장 조회
		$(".a_search_register", resultObj.selector).off();
		$(".a_search_register", resultObj.selector).click(function() {
			cmmnPropObj.resultObj.cmmnPropTabRegister();
			return false;
		});
		
		// 속성 내보내기
		$(".a_export_excel", resultObj.selector).off();
		$(".a_export_excel", resultObj.selector).click(function() {
			resultObj.exportExcel();
			return false;
		});

		// 공간 내보내기
		$(".a_export_shape", resultObj.selector).off();
		$(".a_export_shape", resultObj.selector).click(function() {
			resultObj.exportShape();
			return false;
		});

		// 지도 고정
		$(".a_export_fix", resultObj.selector).off();
		$(".a_export_fix", resultObj.selector).click(function(event) {
			var element = $(this);
			resultObj.toggleFixed(element);
			event.preventDefault();
		});
	},
	
	cmmnPropTabRegister : function(){
		var that = this;
		
		var selects = $('.grid_result_detail', resultObj.selector).datagrid('getSelections');
		
		if(selects.length > 0){
			// 다중 행 선택으로 대장 tab open시 부속 datagrid가 제대로 만들어지지 않는 현상 발생.
			// 하나로 강제하도록 변경.
			if(selects.length > 1){
				$.messager.alert(cmmnPropObj.TITLE, '하나의 행만 선택해주세요.');
				return false;
			}else{
				for(var i = 0; i < selects.length; i++){
					var url = CONTEXT_PATH + '/cmmnprop/' + resultObj.data.dataId + '/detailPage.do';
					
					var dataId = resultObj.data.dataId;
					var callback = null;
					if(dataId.indexOf('BML_PROP_AS') > -1){
						callback = function(row, selector){
							cmmnPropObj.view.init(row.prtIdn, resultObj.data.dataId, selector);
						};
					}else if (dataId.indexOf('BML_BUID_AS') > -1){
						callback = function(row, selector){
							cmmnBuidObj.init(row.prtIdn, resultObj.data.dataId, selector);
						};
					}else if(dataId.indexOf('BML_LOAN_AS') > -1){
						callback = function(row, selector){
							var pbpKnd = row.pbpKnd;
							var prtIdn = row.prtIdn;
							if(pbpKnd=="01"){
								cmmnLoanObj.view.init(row.lonIdn, resultObj.data.dataId, selector, pbpKnd, prtIdn);
							}else if(pbpKnd=="02"){
								cmmnLoanObj.view.init(row.lonIdn, resultObj.data.dataId, selector, pbpKnd, prtIdn);
							}else{
								cmmnLoanObj.view.init(row.lonIdn, resultObj.data.dataId, selector, pbpKnd, prtIdn);
							}
							
						};
					}else if(dataId.indexOf('BML_OCCP_AS') > -1){
						callback = function(row, selector){
							var pbpKnd = row.pbpKnd;
							var prtIdn = row.prtIdn;
							if(pbpKnd=="01"){
								cmmnOccpObj.view.init(row.ocpIdn, resultObj.data.dataId, selector, pbpKnd, prtIdn);
							}else if(pbpKnd=="02"){
								cmmnOccpObj.view.init(row.ocpIdn, resultObj.data.dataId, selector, pbpKnd, prtIdn);
							}else{
								cmmnOccpObj.view.init(row.ocpIdn, resultObj.data.dataId, selector, pbpKnd, prtIdn);
							}
							
						};
					}else if(dataId.indexOf('V_BML_ACEX_AS') > -1){
						callback = function(row, selector){
							cmmnAcexObj.view.init(row.prtIdn, selector, row.pbpKnd, resultObj.data.dataId);
						};
					}else if(dataId.indexOf('BML_ACIN_AS') > -1){
						callback = function(row, selector){
							cmmnAcinObj.view.init(row.prtIdn, resultObj.data.dataId, selector);
						};
					}
					
					tabObj.createTab(resultObj.data.dataId, resultObj.data.dataAlias, selects[i], url, callback);
				}
			}
		}else{
			$.messager.alert(that.TITLE, '선택된 행이 없습니다.');
		}
	}
};

var cmmnBuidObj = {
		dataId : '',
		
		selector : '',
		
		data : {},
		
		init: function(prtIdn, dataId, selector){
			var that = this;
			
			that.dataId = dataId;
			that.selector = selector;
			
			that.dataLoading(prtIdn);
			//that.createMap();
			
			that.initUi();
			that.bindEvents();
		},
		
		initUi : function(){
			var that = this;
			
			$('.cmmn_tabs', that.selector).tabs();
		},
		
		bindEvents : function(){
			var that = this;
			
			$('#btn_loan_selectOne', that.selector).click(function(){
				that.setSelector();
				
				var row = $('.grid_result_loan', that.selector).datagrid('getSelected');
				
				if(!row){
					$.messager.alert('대부 및 사용허가', '행을 선택해주세요.');
				}else{
					that.openAtchTab('BML_LOAN_AS');
				}
			});
			
//			$('#btn_occp_selectOne', that.selector).click(function(){
//				that.setSelector();
//				
//				var row = $('.grid_result_occp', that.selector).datagrid('getSelected');
//				if(!row){
//					$.messager.alert('무단점유 사용자', '행을 선택해주세요.');
//				}else{
//					that.openAtchTab('BML_OCCP_AS');
//				}
//			});
			
			/*$('#btn_acex_selectOne', that.selector).click(function(){
				that.setSelector();
				
				var row = $('.grid_result_acex', that.selector).datagrid('getSelected');
				if(!row){
					$.messager.alert('실태조사', '행을 선택해주세요.');
				}else{
					that.openAtchTab('V_BML_ACEX_AS');
				}
			});*/
		},
		
		openAtchTab : function(dataId){
			var that = this;
			
			var row = null;
			var kwsData = dataObj.getDataByDataId(dataId, false);
			var alias = kwsData.dataAlias;
			var callback = null;
			var url = CONTEXT_PATH + '/cmmnprop/' + dataId + '/detailPage.do';
			
			if(dataId.indexOf('BML_LOAN_AS') > -1){
				row = $('.grid_result_loan', that.selector).datagrid('getSelected');
				callback = function(row, selector){
					cmmnLoanObj.view.init(row.lonIdn, dataId, selector, row.pbpKnd, row.prtIdn);
				};
			}
//			else if(dataId.indexOf('BML_OCCP_AS') > -1){
//				row = $('.grid_result_occp', that.selector).datagrid('getSelected');
//				callback = function(row, selector){
//					cmmnOccpObj.view.init(row.ocpIdn, dataId, selector, row.pbpKnd, row.prtIdn);
//				};
//			}
			/*else if(dataId.indexOf('V_BML_ACEX_AS') > -1){
				row = $('.grid_result_acex', that.selector).datagrid('getSelected');
				callback = function(row, selector){
					cmmnAcexObj.view.init(row.prtIdn, selector, row.pbpKnd, dataId);
				};
			}*/
			
			tabObj.createTab(dataId, alias, row, url, callback);
		},
		
		setSelector : function(){
			var that = this;
			
			that.selector = '#' + tabObj.getId();
		},
		
		dataLoading : function(prtIdn){
			var that = this;
			
			var url = CONTEXT_PATH + '/cmmnprop/' + prtIdn + '/searchBuidData.do';
			
			$.ajax({
				type : 'GET',
				dataType : 'json',
				url : url,
				async : false,
				success : function(rs){
					if(rs && rs.row){
						var row = rs.row;
						
						var data = dataObj.getDataByDataId(that.dataId, true);
						var dataField = data.kwsDataFields;
						
						for(var i = 0; i < dataField.length; i++){
							if(dataField[i].indictTy.indexOf('CURRENCY') > -1){
								row[camelcaseUtils.underbarToCamelcase(dataField[i].fieldId)] = numberUtils.formatCurrency(row[camelcaseUtils.underbarToCamelcase(dataField[i].fieldId)]);
							}
						}
						
						$('#buid_prtIdn', that.selector).html(row.prtIdn);
						$('#buid_pbpKnd', that.selector).html('건물');
						$('#buid_meansSe', that.selector).html(row.meansSe);
						$('#buid_prtNam', that.selector).html(row.prtNam);
						$('#buid_ownCde', that.selector).html(row.ownNm);
						$('#buid_prsCde', that.selector).html(row.prsCde);
						$('#buid_amnCde', that.selector).html(row.amnCde);
						$('#buid_accCde', that.selector).html(row.accNm);
						$('#buid_manCde', that.selector).html(row.manNm);
						$('#buid_chrNam', that.selector).html(row.chrNam);
						$('#buid_mndCde', that.selector).html(row.mndNm);
						$('#buid_pstNum', that.selector).html(row.pstNum);
						$('#buid_bjdCde', that.selector).html(row.bjdCde);
						$('#buid_locPlc', that.selector).html(row.locPlc);
						$('#buid_monut', that.selector).html(row.monut);
						$('#buid_bun', that.selector).html(row.bun);
						$('#buid_ho', that.selector).html(row.ho);
						$('#buid_spcAdr', that.selector).html(row.spcAdr);
						$('#buid_spcDong', that.selector).html(row.spcDong);
						$('#buid_spcHo', that.selector).html(row.spcHo);
						$('#buid_rnAddr', that.selector).html(row.rnAddr);
						$('#buid_ptyPc', that.selector).html(row.ptyPc);
						$('#buid_autAmnt', that.selector).html(row.autAmnt);
						$('#buid_acqDept', that.selector).html(row.acqDept);
						$('#buid_acqPc', that.selector).html(row.acqPc);
						$('#buid_acqDate', that.selector).html(row.acqDate);
						$('#buid_acqCde', that.selector).html(row.acqCde);
						$('#buid_acqPrv', that.selector).html(row.acqPrv);
						$('#buid_rstYn', that.selector).html(row.rstYn);
						$('#buid_loanYn', that.selector).html(row.loanYn);
						$('#buid_saleLmt', that.selector).html(row.saleLmt);
						$('#buid_rmkExp', that.selector).html(row.rmkExp);
						$('#buid_srcCde', that.selector).html(row.srcCde);
						$('#buid_rfCde', that.selector).html(row.rfCde);
						$('#buid_grdCde', that.selector).html(row.grdCde);
						$('#buid_ugrdCde', that.selector).html(row.ugrdCde);
						$('#buid_bldArea', that.selector).html(row.bldArea);
						$('#buid_bldDate', that.selector).html(row.bldDate);
						$('#buid_bldPri', that.selector).html(row.bldPri);
						$('#buid_bldNam', that.selector).html(row.prtNam);
						$('#buid_bldSe', that.selector).html(row.bldSe);
						$('#buid_bldPrp', that.selector).html(row.bldPrp);
						$('#buid_grsArea', that.selector).html(row.grsArea);
						$('#buid_acsAra', that.selector).html(row.acsArea);
						$('#buid_grdCde', that.selector).html(row.grdCde);
						if(row.pstNum){
							$('#buid_pstNum', that.selector).html(row.pstNum);
						}
						
						var ugrCde = row.ugrdCde;
						
						if(!ugrCde){
							ugrCde = '0';
						}
						
						$('#buid_ugrdCde', that.selector).html(ugrCde);
						
						var id = tabObj.getId();
						
						if(!that.data[id]){
							that.data[id] = {
									loan : null,
									occp : null,
									acex : null
							};
						}
						
						that.loanResultLoading(row.prtIdn, row.pbpKnd);
//						that.occpResultLoading(row.prtIdn, row.pbpKnd);
						that.acexResultLoading(row.prtIdn, row.pbpKnd);
					}
				}, error : function(err){
					console.error(err);
				}
			});
		},
		
		/*createMap : function() {
			var that = this;
			that.map = new kworks.IndexMap({
				map : mapObj.getMap(),
				target : 'index_map_cmmn',
				layers : [
					new ol.layer.Image({
						id : "kc_index",
			            source: new kworks.source.ImageWMS({
			              url: CONTEXT_PATH + "/proxy/wms.do",
			              params : $.extend(serverObj.getWMSParams(), {
			                  'LAYERS' : serverObj.getBlankLayers(),
			                  'STYLES' : serverObj.getBlankLayers(),
			            	  'FORMAT' : "image/jpeg"
			              }),
			              ratio : 1,
			              serverType: MAP.SERVER_TYPE
			            })
			        })],
					mode : "MAX",
					view : new ol.View({
						projection : new ol.proj.get(MAP.PROJECTION),
						maxResolution : INDEX_MAP.MAX_RESOLUTION,
						center : [INDEX_MAP.CENTER_X, INDEX_MAP.CENTER_Y],
						zoom : 0
					})
			});
			
			var layer = that.map.getLayer("kc_index");
			var layerNames = [caseUtils.getText(INDEX_MAP.LAYER)];
			sldObj.reloadWMSFromSld("IndexMap", "인덱스맵", layer, layerNames);
			
		},*/
		
		loadDetail : function(dataId){
			var that = this;
			var gridSelector = null;
			
			that.setSelector();
			var id = tabObj.getId();
			
			var data = null;
			
			if(dataId.indexOf('BML_LOAN_AS') > -1){
				data = that.data[id]['loan'];
				gridSelector = '.grid_result_loan';
			}
//			else if(dataId.indexOf('BML_OCCP_AS') > -1){
//				data = that.data[id]['occp'];
//				gridSelector = '.grid_result_occp';
//			}
			/*else if(dataId.indexOf('V_BML_ACEX_AS') > -1){
				data = that.data[id]['acex'];
				gridSelector = '.grid_result_acex';
			}*/
			
			var firstIndex = pagingUtils.getFirstIndex(data.pageNumber, data.pageSize);
			var lastIndex = pagingUtils.getLastIndex(data.pageNumber, data.pageSize);
			var ids = data.ids.slice(firstIndex, lastIndex);
			
			var filter = {
				filterType : 'FIDS',
				fids : ids
			};
			
			var rows = cmmnPropObj.syncAjaxListAll(filter, dataId);
			
			var gridData = {
					total : data.ids.length,
					rows : rows
			};
			
			$(gridSelector, that.selector).datagrid('loadData', gridData);
		},
		
		acexResultLoading : function(prtIdn, pbpKnd){
			var that = this;
			
			var dataId = 'V_BML_ACEX_AS';
			
			var kwsData = dataObj.getDataByDataId(dataId, true);
			var kdf = kwsData.kwsDataFields;
			
			var pkCol = '';
			
			var cols = [];
			var frozenCol = [];
			
			var id = tabObj.getId();
			
			for(var i = 0; i < kdf.length; i++){
				if(kdf[i].pkAt.indexOf('Y') > -1){
					pkCol = camelcaseUtils.underbarToCamelcase(kdf[i].fieldId);
					
					frozenCol.push({
						field : pkCol,
						title : '번호',
						width : 100,
						sortable : true
					});
				}else if(kdf[i].indictTy.indexOf('HIDE') < 0 && kdf[i].indictTy.indexOf('WKT') < 0){
					cols.push({
						field : camelcaseUtils.underbarToCamelcase(kdf[i].fieldId),
						title : kdf[i].fieldAlias,
						width : 100,
						sortable : false
					});
				}
			}
			
			/*$('.grid_result_acex', that.selector).datagrid({
				pk : pkCol,
				frozenColumns : [frozenCol],
				columns : [cols],
				singleSelect : true,
				showFooter : false,
				pagination : true
			});*/
			
			var cql = " PRT_IDN = " + prtIdn + " AND PBP_KND = " + pbpKnd;
			
			var filter = {
				filterType : 'CQL',
				cql : cql
			};
			
			var rows = cmmnPropObj.syncAjaxSummaries(filter, dataId);
			
			if(rows && rows.length > 0){
				that.data[id]['acex'] = {
						pageNumber : 1,
						pageSize : 10,
						ids : rows[0].ids
				};
				that.loadDetail(dataId);
			}
			
			/*var pagination = $('.grid_result_acex', that.selector).datagrid('getPager');
			pagination.pagination({
				displayMsg : '{from} - {to} / {total}',
				onSelectPage : function(pageNumber, pageSize){
					that.data[id]['acex'].pageNumber = pageNumber;
					that.data[id]['acex'].pageSize = pageSize;
					that.loadDetail(dataId);
				}
			});*/
		},
		
		loanResultLoading : function(prtIdn, pbpKnd){
			var that = this;
			
			var dataId = 'BML_LOAN_AS';
			
			var kwsData = dataObj.getDataByDataId(dataId, true);
			var kdf = kwsData.kwsDataFields;
			
			var pkCol = '';
			
			var cols = [];
			var frozenCol = [];
			
			var id = tabObj.getId();
			
			for(var i = 0; i < kdf.length; i++){
				if(kdf[i].pkAt.indexOf('Y') > -1){
					
					pkCol = camelcaseUtils.underbarToCamelcase(kdf[i].fieldId);
					
					frozenCol.push({
						field : pkCol,
						title : '번호',
						width : 100,
						sortable : true
					});
				}else if(kdf[i].indictTy.indexOf('HIDE') < 0 && kdf[i].indictTy.indexOf('WKT') < 0){
					cols.push({
						field : camelcaseUtils.underbarToCamelcase(kdf[i].fieldId),						
						title : kdf[i].fieldAlias,
						width : 100,
						sortable : false
					});
				}
			}
			
			$('.grid_result_loan', that.selector).datagrid({
				pk : pkCol,
				frozenColumns : [frozenCol],
				columns : [cols],
				singleSelect : true,
				showFooter : false,
				pagination : true,
			});
			
			var cql = " PRT_IDN = " + prtIdn + " AND PBP_KND = '" + pbpKnd + "' ";
			
			var filter = {
					filterType : 'CQL',
					cql : cql
			};
			
			var rows = cmmnPropObj.syncAjaxSummaries(filter, dataId);
			
			if(rows && rows.length > 0){
				that.data[id]['loan'] = {
						pageNumber : 1,
						pageSize : 10,
						ids : rows[0].ids
				};
				that.loadDetail(dataId);
			}
			
			var pagination = $('.grid_result_loan', that.selector).datagrid('getPager');
			pagination.pagination({
				displayMsg : '{from} - {to} / {total}',
				onSelectPage : function(pageNumber, pageSize){
					that.data[id]['loan'].pageNumber = pageNumber;
					that.data[id]['loan'].pageSize = pageSize;
					that.loadDetail(dataId);
				}
			});
		},
		
//		occpResultLoading : function(prtIdn, pbpKnd){
//			var that = this;
//			
//			var dataId = 'BML_OCCP_AS';
//			
//			var kwsData = dataObj.getDataByDataId(dataId, true);
//			var kdf = kwsData.kwsDataFields;
//			
//			var pkCol = '';
//			
//			var cols = [];
//			var frozenCol = [];
//			
//			var id = tabObj.getId();
//			
//			for(var i = 0; i < kdf.length; i++){
//				if(kdf[i].pkAt.indexOf('Y') > -1){
//					
//					pkCol = camelcaseUtils.underbarToCamelcase(kdf[i].fieldId);
//					
//					frozenCol.push({
//						field : pkCol,
//						title : '번호',
//						width : 100,
//						sortable : true
//					});
//				}else if(kdf[i].indictTy.indexOf('HIDE') < 0 && kdf[i].indictTy.indexOf('WKT') < 0){
//					cols.push({
//						field : camelcaseUtils.underbarToCamelcase(kdf[i].fieldId),						
//						title : kdf[i].fieldAlias,
//						width : 100,
//						sortable : false
//					});
//				}
//			}
//			
//			$('.grid_result_occp', that.selector).datagrid({
//				pk : pkCol,
//				frozenColumns : [frozenCol],
//				columns : [cols],
//				singleSelect : true,
//				showFooter : false,
//				pagination : true,
//			});
//			
//			var cql = " PRT_IDN = " + prtIdn + " AND PBP_KND = '" + pbpKnd + "' ";
//			
//			var filter = {
//					filterType : 'CQL',
//					cql : cql
//			};
//			
//			var rows = cmmnPropObj.syncAjaxSummaries(filter, dataId);
//			
//			if(rows && rows.length > 0){
//				
//				that.data[id]['occp'] = {
//						pageNumber : 1,
//						pageSize : 10,
//						ids : rows[0].ids
//				};
//				that.loadDetail(dataId);
//			}
//			
//			var pagination = $('.grid_result_occp', that.selector).datagrid('getPager');
//			pagination.pagination({
//				displayMsg : '{from} - {to} / {total}',
//				onSelectPage : function(pageNumber, pageSize){
//					
//					that.data[id]['occp'].pageNumber = pageNumber;
//					that.data[id]['occp'].pageSize = pageSize;
//					
//					that.loadDetail(dataId);
//				}
//			});
//		}
};


// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

var cmmnLoanObj = {

		TITLE : "대부 및 사용허가 검색",
		
		CLASS_NAME : "CommonLoanSearch",
		
		selector : null,
		
		isClosed : false,
		
		dataId : "BML_LOAN_AS",
		
		open : function() {
			var that = this;
			
			if(that.selector) {
				that.close();
			}
			
			var windowOptions = {
				width :	1000,
				height : 251,
				top : 120,
				left : 330,
				onClose : function() {
					that.close();
				}
			};
			
			var url = CONTEXT_PATH + '/cmmnprop/searchLoanPage.do';
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
				that.initUi();
				that.init();
				that.bindEvents();
			});
				
			that.selector = "#" + id;
			
		},
		
		init : function(){
			var that = this;
			
			var dongList = dongObj.getData();
			var emdData = [];
			
			for(var i = 0 ; i < dongList.length; i++){
				emdData.push(dongList[i]);
			}
			
			emdData.unshift({
				bjdCde : '',
				bjdNam : ''
			});
			
			$('#comboBx_emd', that.selector).combobox('loadData', emdData);
			
			that.initDate();
			that.initMesrvNm();
			that.initManList();
			$('#comBx_pbpKnd', that.selector).combobox('select', '01');
		},
		
		
		
		initUi : function(){
			var that = this;
			
			$('#comBx_manNm', that.selector).combobox({
				width : 362,
				valueField : 'manCde',
				textField : 'manNm'
			});
			
			$('#comBx_mesrvNm', that.selector).combobox({
				textField : 'mesrvNm',
				valueField : 'prsCde',
				width : 362
			});
			
			
			$('#dateBx_lonDateStr', that.selector).textbox({
				width : 169
			});
			
			$('#dateBx_lonDateEnd', that.selector).textbox({
				width : 169
			});
			
			$('.swtchBtn', that.selector).switchbutton();
			
			$('.numSpn', that.selector).numberspinner({
				min : 0,
				max: 999999
			});
			
			$('#comboBx_emd', that.selector).combobox({
				valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
				textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD)
			});
			
			$('#comBx_pbpKnd', that.selector).combobox({
				valueField : 'value',
				textField : 'label',
				data:[/*{
					value:'',
					label:''
				},*/{
					value:'01',
					label:'토지'
				},{
					value:'02',
					label:'건물'
				}],
				width : 362
			});
			
			$('#textBx_empNam', that.selector).textbox({
				width : 861
			});
		},
		
		bindEvents : function(){
			var that = this;
			
			// 초기화
			$("#btn_reset", that.selector).on("click", function() {
				that.clean();
				return false;
			});

			// 검색
			$("#btn_search", that.selector).on("click", function() {
				that.search();
				return false;
			});
		},
		
		search : function(){
			var that = this;
			
			var emd = $('#comboBx_emd', that.selector).combobox('getValue');
			var mntn = $('#mntn', that.selector).switchbutton('options').checked?'2':'1';
			var bun = $('#mnnm', that.selector).numberspinner('getValue');
			var ho = $('#slno', that.selector).numberspinner('getValue');
			//var prtIdn = $('#numSpn_prtIdn', that.selector).numberspinner('getValue');
			var pbpKnd = $('#comBx_pbpKnd', that.selector).combobox('getValue');
			var mesrvNm = $('#comBx_mesrvNm', that.selector).textbox('getText');
			var manNm = $('#comBx_manNm', that.selector).combobox('getValue');
			var empNam = $('#textBx_empNam', that.selector).textbox('getValue');
			var crtStr = $('#dateBx_lonDateStr', that.selector).textbox('getValue');
			var crtEnd = $('#dateBx_lonDateEnd', that.selector).textbox('getValue');
			
			var param = { 
					emd : emd,
					mntn : mntn,
					bun : bun,
					ho : ho,
					pbpKnd : pbpKnd,
					mesrvNm : mesrvNm,
					manNm : manNm,
					empNam : empNam,
					crtStr : crtStr,
					crtEnd : crtEnd
				};
			
			var cql = that.createSqlWhereConstruction(param);
			
			var filter = {
					filterType : 'CQL',
					cql : cql
				};
			
			resultObj.close();
			
			cmmnPropObj.searchRes(that.dataId, filter);
			
		},
		
		initManList : function(){
			var that = this;
		
			var dept = cmmnPropObj.getDept();
			
			$.ajax({
				url : CONTEXT_PATH + '/cmmnprop/' + that.dataId + '/selectManGroupList.do',
				type : 'GET',
				dataType : 'json',
				success : function(rs){
					if(rs && rs.rows){
						var manList = rs.rows;
						
						manList.unshift({
							manCde : '',
							manNm : ''
						});
						
						$('#comBx_manNm', that.selector).combobox('loadData', manList);
						
						for(var i = 0; i < manList.length; i++){
							if(dept.code + '0000' == manList[i].manCde){
								$('#comBx_manNm', that.selector).combobox('select', manList[i].manCde);
							}
						}
					}
				}, error : function(err){
					console.error(err);
				}
			});
		},
		
		initMesrvNm : function(){
			var that = this;
			
			$.ajax({
				url : CONTEXT_PATH + '/cmmnprop/selectPrsGroupList.do',
				dataType : 'json',
				type : 'GET',
				success : function(rs){
					if(rs && rs.rows){
						rs.rows.unshift({
							prsCde : '',
							mesrvNm : ''
						});
						$('#comBx_mesrvNm', that.selector).combobox('loadData',rs.rows);
						$('#comBx_mesrvNm', that.selector).combobox('select', '');
						
					}
				},error : function(err){
					console.error(err);
				}
			});
		},
		
		initDate : function(){
			var that = this;
			
			var today = new Date();
			var year = today.getFullYear();
			
			$('#dateBx_lonDateStr', that.selector).textbox('setValue', year + '0101');
			$('#dateBx_lonDateEnd', that.selector).textbox('setValue', year + '1231');
		},
		
		clean : function() {
			var that = this;
			$('#comboBx_emd', that.selector).combobox('select', '');
			$('#mntn', that.selector).switchbutton('clear');
			$('#mnnm', that.selector).numberspinner('setValue','');
			$('#slno', that.selector).numberspinner('setValue','');
			$('#comBx_pbpKnd', that.selector).combobox('select', '01');
			$('#comBx_mesrvNm', that.selector).combobox('select', '');
			$('#comBx_manNm', that.selector).combobox('select','');
			$('#textBx_empNam', that.selector).textbox('clear');
			that.initDate();
		},
		
		close : function() {
			var that = this;
			
			that.clean();
			windowObj.removeWindow(that.selector);
		},
		
		createSqlWhereConstruction : function(param){
			/*if(
					(param.emd == '' || param.emd == null) && 
					(param.mntn == '1') &&
					(param.bun == '' || param.bun == null) && 
					(param.ho == '' || param.ho == null) && 
					(param.pbpKnd == '' || param.pbpKnd == null) &&
					(param.mesrvNm == '' || param.mesrvNm == null) && 
					(param.manNm == '' || param.manNm == null) && 
					(param.empNam == '' || param.empNam == null) && 
					(param.crtStr == '' || param.crtStr == null) &&
					(param.crtEnd == '' || param.crtEnd == null)
			){
				return '1 = 1';
			}else{*/
				var cqlData = "";
				
				if(param.emd != '' && param.emd != null){
					cqlData+=" AND BJD_CDE = '" + param.emd +"'";
				}
				if(param.mntn != '' && param.mntn != null){
					if((param.emd != '' && param.emd != null && param.bun != '' && param.bun != null) || param.mntn == '2') {
						cqlData+=" AND MONUT = '" + param.mntn +"'";
					}
				}
				if(param.bun != '' && param.bun != null){
					cqlData+=" AND BUN = '" + param.bun +"'";
				}
				if(param.ho != '' && param.ho != null){
					cqlData+=" AND HO = '" + param.ho +"'";
				}
				if(param.prtIdn != '' && param.prtIdn != null){
					cqlData+=" AND PRT_IDN = '" + param.prtIdn +"'";
				}
				if(param.pbpKnd != null){
					if(param.pbpKnd != ''){
						cqlData+=" AND PBP_KND = '" + param.pbpKnd +"'";
					}
				}
				if(param.mesrvNm != '' && param.mesrvNm != null){
					cqlData+=" AND MESRV_NM = '" + param.mesrvNm +"'";
				}
				if(param.empNam != '' && param.empNam != null){
					cqlData+=" AND EMP_NAM LIKE '%" + param.empNam +"%'";
				}
				if(param.crtStr != '' && param.crtStr != null){
					cqlData+=" AND LON_START >= '" + param.crtStr +"'";
				}
				if(param.crtEnd != '' && param.crtEnd != null){
					cqlData+=" AND LON_END <= '" + param.crtEnd +"'";
				}
				if(param.manNm != '' && param.manNm != null){
					cqlData += " AND MAN_CDE = '" + param.manNm + "'";
				}
				
				var cql = cqlData.replace(' AND ', '');
				
				return cql;
				
			//}
		}
};

cmmnLoanObj.endImminent = {
	
		TITLE : "대부 종료기한임박 재산 검색",
		
		CLASS_NAME : "CommonLoanEndImminent",
		
		selector : null,
		
		isClosed : false,
		
		dataId : "BML_LOAN_AS",
		
		open : function(){
			var that = this;
			
			if(that.selector){
				that.close();
			}
			
			var winOpt = {
				width : 1000,
				height : 216,
				top : 120,
				left : 330,
				onClose : function(){
					that.close();
				}
			};
			
			var url = CONTEXT_PATH + '/cmmnprop/searchLoanEndImminentPage.do';
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, winOpt, function() {
				that.initUi();
				that.init();
				that.bindEvents();
			});
			
			that.selector = '#' + id;
			
		},
		
		init : function(){
			var that = this;
			
			var that = this;
			
			var dongList = dongObj.getData();
			var emdData = [];
			
			for(var i = 0 ; i < dongList.length; i++){
				emdData.push(dongList[i]);
			}
			
			emdData.unshift({
				bjdCde : '',
				bjdNam : ''
			});
			
			$('#comboBx_emd', that.selector).combobox('loadData', emdData);
			
			that.initManList();
			that.initMesrvNm();
			$('#comBx_pbpKnd', that.selector).combobox('select', '01');
		},
		
		initUi : function(){
			var that = this;
			
			var that = this;
			
			$('.textbx', that.selector).textbox({
				width : 362
			});
			
			$('#comBx_manNm', that.selector).combobox({
				textField : 'manNm',
				valueField : 'manCde',
				width : 362
			});
			
			$('#comBx_mesrvNm', that.selector).combobox({
				textField : 'mesrvNm',
				valueField : 'prsCde',
				width : 362
			});
			
			$('.swtchBtn', that.selector).switchbutton();
			
			$('.numSpn', that.selector).numberspinner({
				min : 0,
				max: 999999
			});
			
			$('#comboBx_emd', that.selector).combobox({
				valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
				textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD)
			});
			
			$('#comBx_pbpKnd', that.selector).combobox({
				valueField : 'value',
				textField : 'label',
				data:[{
					value:'01',
					label:'토지'
				},{
					value:'02',
					label:'건물'
				}],
				width : 362
			});
		},
		
		initManList : function(){
			var that = this;
		
			var dept = cmmnPropObj.getDept();
			
			$.ajax({
				url : CONTEXT_PATH + '/cmmnprop/' + that.dataId + '/selectManGroupList.do',
				type : 'GET',
				dataType : 'json',
				success : function(rs){
					if(rs && rs.rows){
						var manList = rs.rows;
						
						manList.unshift({
							manCde : '',
							manNm : ''
						});
						
						$('#comBx_manNm', that.selector).combobox('loadData', manList);
						
						for(var i = 0; i < manList.length; i++){
							if(dept.code + '0000' == manList[i].manCde){
								$('#comBx_manNm', that.selector).combobox('select', manList[i].manCde);
							}
						}
					}
				}, error : function(err){
					console.error(err);
				}
			});
		},
		
		initMesrvNm : function(){
			var that = this;
			
			$.ajax({
				url : CONTEXT_PATH + '/cmmnprop/selectPrsGroupList.do',
				dataType : 'json',
				type : 'GET',
				success : function(rs){
					if(rs && rs.rows){
						rs.rows.unshift({
							prsCde : '',
							mesrvNm : ''
						});
						
						$('#comBx_mesrvNm', that.selector).combobox('loadData', rs.rows);
					}
				}, error : function(err){
					console.error(err);
				}
			});
		},
		
		bindEvents : function(){
			var that = this;
			
			// 초기화
			$("#btn_reset", that.selector).on("click", function() {
				that.clean();
				return false;
			});

			// 검색
			$("#btn_search", that.selector).on("click", function() {
				that.search();
				return false;
			});
		},
		
		search : function(){
			var that = this;
			
			var emd = $('#comboBx_emd', that.selector).combobox('getValue');
			var mntn = $('#mntn', that.selector).switchbutton('options').checked?'2':'1';
			var bun = $('#mnnm', that.selector).numberspinner('getValue');
			var ho = $('#slno', that.selector).numberspinner('getValue');
			var pbpKnd = $('#comBx_pbpKnd', that.selector).combobox('getValue');
			var mesrvNm = $('#comBx_mesrvNm', that.selector).textbox('getText');
			var manNm = $('#comBx_manNm', that.selector).combobox('getValue');
			var empNam = $('#textBx_empNam', that.selector).textbox('getValue');
			
			var param = { 
				emd : emd,
				mntn : mntn,
				bun : bun,
				ho : ho,
				pbpKnd : pbpKnd,
				mesrvNm : mesrvNm,
				manNm : manNm,
				empNam : empNam
			};
			
			var filter = that.createCqlFilter(param);
			var row = cmmnPropObj.syncAjaxSummaries(filter, that.dataId);
			
			if(row){
				var option = {
						dataId : that.dataId,
						eventObj : cmmnPropObj.resultObj.bindEvents
				};
				
				resultObj.close();
				$('.a_menu_collapse').trigger('click');
				resultObj.addRows(row, option);
				that.close();
			}else{
				$.messager.alert(that.TITLE,'검색 결과가 없습니다.')
			}
		},
		
		createCqlFilter : function(param){
			var dept = cmmnPropObj.getDept();
			
			var cql = "";
			
			/*if(
			(param.emd == '' || param.emd == null) &&
			(param.mntn == '1') &&
			(param.bun == '' || param.bun == null) &&
			(param.ho == '' || param.ho == null) &&
			(param.prtIdn == '' || param.prtIdn == null) &&
			(param.pbpKnd == null || param.pbpKnd == '') &&
			(param.mesrvNm == '' || param.mesrvNm == null) &&
			(param.empNam == '' || param.empNam == null) &&
			(param.manNm == '' || param.manNm == null)
			){
				cql += " 1 = 1 ";
			}else{*/
				if(param.emd != '' && param.emd != null){
					cql += " AND BJD_CDE = '" + param.emd + "'";
				}
				if(param.mntn != '' && param.mntn != null){
					if((param.emd != '' && param.emd != null && param.bun != '' && param.bun != null) || param.mntn == '2') {
						cql+=" AND MONUT = '" + param.mntn +"'";
					}
				}
				if(param.bun != '' && param.bun != null){
					cql+=" AND BUN = '" + param.bun +"'";
				}
				if(param.ho != '' && param.ho != null){
					cql+=" AND HO = '" + param.ho +"'";
				}
				if(param.prtIdn != '' && param.prtIdn != null){
					cql+=" AND PRT_IDN = '" + param.prtIdn +"'";
				}
				if(param.pbpKnd != null && param.pbpKnd != ''){
					cql+=" AND PBP_KND = '" + param.pbpKnd +"'";
				}
				if(param.mesrvNm != '' && param.mesrvNm != null){
					cql+=" AND MESRV_NM = '" + param.mesrvNm +"'";
				}
				if(param.empNam != '' && param.empNam != null){
					cql+=" AND EMP_NAM LIKE '%" + param.empNam +"%'";
				}
				if(param.manNm != '' && param.manNm != null){
					cql += " AND MAN_CDE = '" + param.manNm + "'";
				}
				
				cql += " AND TO_DATE(LON_END) - 60 <= SYSDATE ";
			
				cql = cql.replace(' AND', '');
			//}
			
			var filter = {
					filterType : 'CQL',
					cql : cql
			};
			
			return filter;
		},
		
		clean : function() {
			var that = this;
			
			$('#comboBx_emd', that.selector).combobox('select', '');
			$('#mntn', that.selector).switchbutton('clear');
			$('#mnnm', that.selector).numberspinner('setValue','');
			$('#slno', that.selector).numberspinner('setValue','');
			$('#comBx_pbpKnd', that.selector).combobox('select', '01');
			$('#comBx_mesrvNm', that.selector).combobox('select', '');
			$('#comBx_manNm', that.selector).combobox('select', '');
			$('#textBx_empNam', that.selector).textbox('clear');
		},
		
		close : function(){
			var that = this;
			
			that.clean();
			windowObj.removeWindow(that.selector);
		}
};

cmmnLoanObj.view = {
		
	dataId : null,
	
	selector : null,
	
	CLASS_NAME : "CommonLoanView",
	
	data : {},
	
	maps : null,
	
	prtIdn : null,
	
	map : null,
	
	scale : null,
	
	extent : null,
	
	geomData : null,
	
	flag : null,
	
	manCde : null,
	
	init : function(lonIdn, dataId, selector, param, prtIdn){
		var that = this;
		
		that.dataId = dataId;
		that.selector = selector;
		
		that.dataLoading(lonIdn, dataId, selector, param, prtIdn);
//		$('#div_loan_map_wrap').append()
		that.initUi();
		
		//대부및 사용허가 위치정보 추가입력
		that.geomDataLoding(prtIdn);
		
	},
	
	initUi : function(){
		var that = this;
		
		$('.cmmn_tabs', that.selector).tabs();
	},
	
	setSelector : function(){
		var that = this;
		
		that.selector = '#' + tabObj.getId();
	},
	
	dataLoading : function(lonIdn, dataId, selector, param, prtIdn){
		var that = this;
		var url = "";
		that.prtIdn = prtIdn;
		if (param=="01"){
			url = CONTEXT_PATH + '/cmmnprop/' + lonIdn + '/' + prtIdn + '/searchLoanPropData.do';
		}else if (param=="02"){
			url = CONTEXT_PATH + '/cmmnprop/' + lonIdn + '/' + prtIdn + '/searchLoanBuilData.do';
		}else {
			url = CONTEXT_PATH + '/cmmnprop/' + lonIdn + '/' + prtIdn + '/searchLoanData.do';
		}
		
		$.ajax({
			type : 'GET',
			dataType : 'json',
			url : url,
			async : false,
			success : function(rs){
				var bmlLoanAs = rs.bmlLoanAs;
				
				if(rs.bmlBuidAs){
					var bmlBuidAs = rs.bmlBuidAs;
					
					var data = dataObj.getDataByDataId('BML_BUID_AS', true);
					var dataField = data.kwsDataFields;
					
					for(var i = 0; i < dataField.length; i++){
						if(dataField[i].indictTy.indexOf('CURRENCY') > -1){
							bmlBuidAs[camelcaseUtils.underbarToCamelcase(dataField[i].fieldId)] = numberUtils.formatCurrency(bmlBuidAs[camelcaseUtils.underbarToCamelcase(dataField[i].fieldId)]);
						}
					}
					
					$('#sub_prtIdn', that.selector).html(bmlBuidAs.prtIdn);
					$('#sub_accCde', that.selector).html(bmlBuidAs.accNm);
					$('#sub_acqCde', that.selector).html(bmlBuidAs.acqCde);
					$('#sub_acqDate', that.selector).html(bmlBuidAs.acqDate);
					$('#sub_acqCde', that.selector).html(bmlBuidAs.acqCde);
					$('#sub_acqDept', that.selector).html(bmlBuidAs.acqDept);
					$('#sub_acqCde', that.selector).html(bmlBuidAs.acqCde);
					$('#sub_acqPc', that.selector).html(bmlBuidAs.acqPc);
					$('#sub_acqPrv', that.selector).html(bmlBuidAs.acqPrv);
					$('#sub_acsArea', that.selector).html(bmlBuidAs.acsArea);
					$('#sub_amnCde', that.selector).html(bmlBuidAs.amnCde);
					$('#sub_autAmnt', that.selector).html(bmlBuidAs.autAmnt);
					$('#sub_bjdCde', that.selector).html(bmlBuidAs.bjdCde);
					$('#sub_bldArea', that.selector).html(bmlBuidAs.bldArea);
					$('#sub_bldDate', that.selector).html(bmlBuidAs.bldDate);
					$('#sub_bldNam', that.selector).html(bmlBuidAs.bldNam);
					$('#sub_bldPri', that.selector).html(bmlBuidAs.bldPri);
					$('#sub_bldPrp', that.selector).html(bmlBuidAs.bldPrp);
					$('#sub_bldSe', that.selector).html(bmlBuidAs.bldSe);
					$('#sub_bun', that.selector).html(bmlBuidAs.bun);
					$('#sub_chrNam', that.selector).html(bmlBuidAs.chrNam);
					$('#sub_compMat', that.selector).html(bmlBuidAs.compMat);
					$('#sub_grdCde', that.selector).html(bmlBuidAs.grdCde);
					$('#sub_grsArea', that.selector).html(bmlBuidAs.grsArea);
					$('#sub_hjdCde', that.selector).html(bmlBuidAs.hjdCde);
					$('#sub_ho', that.selector).html(bmlBuidAs.ho);
					$('#sub_loanYn', that.selector).html(bmlBuidAs.loanYn);
					$('#sub_locPlc', that.selector).html(bmlBuidAs.locPlc);
					$('#sub_manCde', that.selector).html(bmlBuidAs.manNm);
					$('#sub_mndCde', that.selector).html(bmlBuidAs.mndNm);
					$('#sub_monut', that.selector).html(bmlBuidAs.monut);
					$('#sub_objectId', that.selector).html(bmlBuidAs.objectId);
					$('#sub_ownCde', that.selector).html(bmlBuidAs.ownCde);
					$('#sub_prsCde', that.selector).html(bmlBuidAs.prsCde);
					$('#sub_prtNam', that.selector).html(bmlBuidAs.prtNam);
					$('#sub_pstNum', that.selector).html(bmlBuidAs.pstNum);
					$('#sub_ptyPc', that.selector).html(bmlBuidAs.ptyPc);
					$('#sub_rfCde', that.selector).html(bmlBuidAs.rfCde);
					$('#sub_rmkExp', that.selector).html(bmlBuidAs.rmkExp);
					$('#sub_rnAddr', that.selector).html(bmlBuidAs.rnAddr);
					$('#sub_rstYn', that.selector).html(bmlBuidAs.rstYn);
					$('#sub_saleLmt', that.selector).html(bmlBuidAs.saleLmt);
					$('#sub_spcAdr', that.selector).html(bmlBuidAs.spcAdr);
					$('#sub_spcDong', that.selector).html(bmlBuidAs.spcDong);
					$('#sub_spcHo', that.selector).html(bmlBuidAs.spcHo);
					$('#sub_srcCde', that.selector).html(bmlBuidAs.srcCde);
					$('#sub_ugrdCde', that.selector).html(bmlBuidAs.ugrdCde);
					$('#sub_slaeLmt', that.selector).html(bmlBuidAs.saleLmt);
				}else if(rs.bmlPropAs){
					var bmlPropAs = rs.bmlPropAs;
					
					
					var data = dataObj.getDataByDataId('BML_PROP_AS', true);
					var dataField = data.kwsDataFields;
					
					for(var i = 0; i < dataField.length; i++){
						if(dataField[i].indictTy.indexOf('CURRENCY') > -1){
							bmlPropAs[camelcaseUtils.underbarToCamelcase(dataField[i].fieldId)] = numberUtils.formatCurrency(bmlPropAs[camelcaseUtils.underbarToCamelcase(dataField[i].fieldId)]);
						}
					}
					
					$('#sub_prtIdn', that.selector).html(bmlPropAs.prtIdn);
					$('#sub_accCde', that.selector).html(bmlPropAs.accNm);
					$('#sub_acqAra', that.selector).html(bmlPropAs.acqAra);
					$('#sub_acqCde', that.selector).html(bmlPropAs.acqCde);
					$('#sub_acqDate', that.selector).html(bmlPropAs.acqDate);
					$('#sub_acqDept', that.selector).html(bmlPropAs.acqDept);
					$('#sub_acqPc', that.selector).html(bmlPropAs.acqPc);
					$('#sub_acqPrv', that.selector).html(bmlPropAs.acqPrv);
					$('#sub_amnCde', that.selector).html(bmlPropAs.amnNm);
					$('#sub_area', that.selector).html(bmlPropAs.area);
					$('#sub_autAmnt', that.selector).html(bmlPropAs.autAmnt);
					$('#sub_bjdCde', that.selector).html(bmlPropAs.bjdCde);
					$('#sub_bmlCde', that.selector).html(bmlPropAs.bmlCde);
					$('#sub_bun', that.selector).html(bmlPropAs.bun);
					$('#sub_chrNam', that.selector).html(bmlPropAs.chrNam);
					$('#sub_chrQta', that.selector).html(bmlPropAs.chrQta);
					$('#sub_ctyPlan', that.selector).html(bmlPropAs.ctyPlan);
					$('#sub_dwk', that.selector).html(bmlPropAs.dwk);
					$('#sub_hjdCde', that.selector).html(bmlPropAs.hjdCde);
					$('#sub_ho', that.selector).html(bmlPropAs.ho);
					$('#sub_loanYn', that.selector).html(bmlPropAs.loanYn);
					$('#sub_locPlc', that.selector).html(bmlPropAs.locPlc);
					$('#sub_manCde', that.selector).html(bmlPropAs.manNm);
					$('#sub_mndCde', that.selector).html(bmlPropAs.mndNm);
					$('#sub_mokCde', that.selector).html(bmlPropAs.mokCde);
					$('#sub_monut', that.selector).html(bmlPropAs.monut);
					$('#sub_objectId', that.selector).html(bmlPropAs.objectId);
					$('#sub_olnlp', that.selector).html(bmlPropAs.olnlp);
					$('#sub_ownCde', that.selector).html(bmlPropAs.ownNm);
					$('#sub_parea', that.selector).html(bmlPropAs.parea);
					$('#sub_planBns', that.selector).html(bmlPropAs.planBns);
					$('#sub_planFty', that.selector).html(bmlPropAs.planFty);
					$('#sub_prsCde', that.selector).html(bmlPropAs.mesrvNm);
					$('#sub_prtNam', that.selector).html(bmlPropAs.prtNam);
					$('#sub_pstNum', that.selector).html(bmlPropAs.pstNum);
					$('#sub_ptyPc', that.selector).html(bmlPropAs.ptyPc);
					$('#sub_rmkExp', that.selector).html(bmlPropAs.rmkExp);
					$('#sub_rnAddr', that.selector).html(bmlPropAs.rnAddr);
					$('#sub_rstYn', that.selector).html(bmlPropAs.rstYn);
					$('#sub_saleLmt', that.selector).html(bmlPropAs.saleLmt);
					$('#sub_spcAdr', that.selector).html(bmlPropAs.spcAdr);
					$('#sub_spcDong', that.selector).html(bmlPropAs.spcDong);
					$('#sub_spcHo', that.selector).html(bmlPropAs.spcHo);
					$('#sub_spfc', that.selector).html(bmlPropAs.spfc);
					$('#sub_slaeLmt', that.selector).html(bmlPropAs.saleLmt);
				}
				
				var data = dataObj.getDataByDataId('BML_LOAN_AS', true);
				var dataField = data.kwsDataFields;
				
				for(var i = 0; i < dataField.length; i++){
					if(dataField[i].indictTy.indexOf('CURRENCY') > -1){
						bmlLoanAs[camelcaseUtils.underbarToCamelcase(dataField[i].fieldId)] = numberUtils.formatCurrency(bmlLoanAs[camelcaseUtils.underbarToCamelcase(dataField[i].fieldId)]);
					}
				}
				
				that.manCde = bmlLoanAs.manCde;
				
				$('#Loan_lonIdn', that.selector).html(bmlLoanAs.lonIdn);
				$('#Loan_cmpSe', that.selector).html(bmlLoanAs.cmpSe);
				$('#Loan_cntCde', that.selector).html(bmlLoanAs.cntCde);
				$('#Loan_crtDate', that.selector).html(bmlLoanAs.crtDate);
				$('#Loan_crtEnd', that.selector).html(bmlLoanAs.crtEnd);
				$('#Loan_crtStr', that.selector).html(bmlLoanAs.crtStr);
				$('#Loan_empBjd', that.selector).html(bmlLoanAs.empBjd);
				$('#Loan_empMal', that.selector).html(bmlLoanAs.empMal);
				$('#Loan_empNam', that.selector).html(bmlLoanAs.empNam);
				$('#Loan_empNum', that.selector).html(bmlLoanAs.empNum);
				$('#Loan_empRn', that.selector).html(bmlLoanAs.empRn);
				$('#Loan_empRnpt', that.selector).html(bmlLoanAs.empRnpt);
				$('#Loan_empSe', that.selector).html(bmlLoanAs.usrSeNm);
				$('#Loan_empSnum', that.selector).html(bmlLoanAs.empSnum);
				$('#Loan_loaUse', that.selector).html(bmlLoanAs.loaUse);
				$('#Loan_lonArea', that.selector).html(bmlLoanAs.lonArea);
				$('#Loan_lonIdn', that.selector).html(bmlLoanAs.lonIdn);
				$('#Loan_lonPup', that.selector).html(bmlLoanAs.lonPup);
				$('#Loan_pbpKnd', that.selector).html(bmlLoanAs.pbpKnd);
				$('#Loan_prtIdn', that.selector).html(bmlLoanAs.prtIdn);
				$('#Loan_retLrg', that.selector).html(bmlLoanAs.retLrg);
				$('#Loan_rvrGu', that.selector).html(bmlLoanAs.rvrGu);
				$('#Loan_rvrSi', that.selector).html(bmlLoanAs.rvrSi);
				$('#Loan_slonArea', that.selector).html(bmlLoanAs.slonArea);
				$('#Loan_thgAdr', that.selector).html(bmlLoanAs.thgAdr);
				$('#Loan_tntDate', that.selector).html(bmlLoanAs.tntDate);
				$('#Loan_tntResn', that.selector).html(bmlLoanAs.tntResn);
				$('#sub_pbpKnd', that.selector).html(bmlLoanAs.pbpKndnm);
				$('#Loan_perAmt', that.selector).html(bmlLoanAs.perAmt);
				$('#Loan_lonStart', that.selector).html(bmlLoanAs.lonStart);
				$('#Loan_lonEnd', that.selector).html(bmlLoanAs.lonEnd);
				$('#Loan_lonDay', that.selector).html(bmlLoanAs.lonDay);
				$('#Loan_blLonAr', that.selector).html(bmlLoanAs.blLonAr);
				$('#Loan_blBdAr', that.selector).html(bmlLoanAs.blBdAr);
				
				that.dongCode = bmlLoanAs.bjdCde;
				that.mntn = bmlLoanAs.monut;
				that.mnnm = bmlLoanAs.bun;
				that.slno = bmlLoanAs.ho;
				
				
				var id = tabObj.getId();
				
				if(!that.data[id]){
					that.data[id] = {
							comp : null
					};
				}
			},error : function(err){
				console.error(err);
			}
		});
	},
	
	geomDataLoding : function(prtIdn){
		var that =  this;
		
		//지도화면UI
		that.backgroundUi();
		
		//버튼 이벤트
		that.geomBindEvent();
		
		//데이터 넣기
		that.geomDataLode(prtIdn);
	},
	
	//그리드 데이터 삽입
	geomDataLode : function(prtIdn){
		var that = this;
			
		var dataId = 'BML_LOUS_AS';
		
		var kwsData = dataObj.getDataByDataId(dataId, true);
		var kdf = kwsData.kwsDataFields;
		
		var pkCol = '';
		
		var cols = [];
		var frozenCol = [];
		
		var id = tabObj.getId();
		
		for(var i = 0; i < kdf.length; i++){
			if(kdf[i].pkAt.indexOf('Y') > -1){
				
				pkCol = camelcaseUtils.underbarToCamelcase(kdf[i].fieldId);
				
				frozenCol.push({
					field : pkCol,
					title : '번호',
					width : 100,
					sortable : true
				});
			}else if(kdf[i].indictTy.indexOf('HIDE') < 0 && kdf[i].indictTy.indexOf('WKT') < 0){
				cols.push({
					field : camelcaseUtils.underbarToCamelcase(kdf[i].fieldId),						
					title : kdf[i].fieldAlias,
					width : 100,
					sortable : false
				});
			}
		}
		
		$('.grid_result_geom', that.selector).datagrid({
			pk : pkCol,
			frozenColumns : [frozenCol],
			columns : [cols],
			singleSelect : true,
			showFooter : false,
			pagination : true,
			onSelect : function(rowIndex, rowData) {
				that.showFeatures();
			},
			onCheckAll : function(rowIndex, rowData) {
				that.showFeatures();
			},
			onUnselect : function(rowIndex, rowData) {
				that.removeFeatures();
			},
			onUncheckAll : function(rowIndex, rowData) {
				that.removeFeatures();
			}
		});
		
		var cql = " PRT_IDN = " + prtIdn;
		
		var filter = {
				filterType : 'CQL',
				cql : cql
		};
		
		var rows = cmmnPropObj.syncAjaxSummaries(filter, dataId);
		
		if(rows && rows.length > 0){
			that.data[id]['lous'] = {
				pageNumber : 1,
				pageSize : 10,
				ids : rows[0].ids
			};
			that.loadDetail(dataId);
		}
			
		var pagination = $('.grid_result_geom', that.selector).datagrid('getPager');
		pagination.pagination({
			displayMsg : '{from} - {to} / {total}',
			onSelectPage : function(pageNumber, pageSize){
				that.data[id]['lous'].pageNumber = pageNumber;
				that.data[id]['lous'].pageSize = pageSize;
				that.loadDetail(dataId);
			}
		});
	},
	
	/**
	 * 도형 표시
	 */
	showFeatures : function() {
		var that = this;
		
		that.removeFeatures();
		
		var data = dataObj.getDataByDataId('BML_LOUS_AS');
		if (data.dataTy == 'LAYER') {
			var format = new ol.format.WKT();
			var features = [];
			var data = $(".grid_result_geom", that.selector).datagrid("getSelections");
			for (var i = 0, len = data.length; i < len; i++) {
				var row = data[i];
				var geom = row[MAP.GEOMETRY_ATTR_NAME];
				if (geom) {
//					var format = new ol.format.GeoJSON();
//					var features = format.readFeatures(opener.mapObj.getVectorFeatures());
					var feature = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);
					features.push(feature);
				} else {
					if (data.length == 1) {
						$.messager.alert(that.TITLE, '공간 정보가 없습니다.');
					}
				}
			}
			if (features && features.length > 0) {
				highlightLoanObj.showRedFeatures(that.CLASS_NAME, features, !that.isFixed, {
							projection : ol.proj.get(MAP.PROJECTION)
						});
			}
		}
	},

	/**
	 * 도형 표시 제거
	 */
	removeFeatures : function() {
		var that = this;
		highlightObj.removeFeatures(that.CLASS_NAME);
	},
	
	loadDetail : function(dataId){
		var that = this;
		
		var id = tabObj.getId();
		var gridSelector = null;
		var data = null;
		
		if(dataId == 'BML_LOUS_AS'){
			data = that.data[id]['lous'];
			gridSelector = '.grid_result_geom';
		}
		
		var firstIndex = pagingUtils.getFirstIndex(data.pageNumber, data.pageSize);
		var lastIndex = pagingUtils.getLastIndex(data.pageNumber, data.pageSize);
		var ids = data.ids.slice(firstIndex, lastIndex);
		
		var filter = {
				filterType : 'FIDS',
				fids : ids
		};
		
		var rows = cmmnPropObj.syncAjaxListAll(filter, dataId);
		var gridData = {
				total : 0,
				rows : null
		};
		
		if(rows && rows.length > 0){
			gridData.total = data.ids.length;
			gridData.rows = rows;
		}else{
			gridData.rows = [];
		}
		
		$(gridSelector, that.selector).datagrid('loadData', gridData);
	},
	
	//지도 화면UI
	backgroundUi : function(){
		var that = this;
		
		//항공영상 사진생성
//		that.createMaps();
		
		//항공영상 배경지도와 맞게 이동
//		that.maps.moveEnd();
		
		//레이어 가져오기 
//		that.reloadWMS();
		
		that.loadMaps();
	},
	
	loadMaps : function(){
		var that = this;
//		debugger;
		
		jqueryUtils.configAjax();
		
		// 좌표계 검색
		var cntmPromise = coordinateObj.load();
		
		// Sld 설정
		sldObj.setDataToString(sldObj.getDataToString());
		var sldPromise = sldObj.putSld();
		
		var backgroundMapPromise = backgroundMapObj.init(false);
		$.when(cntmPromise, sldPromise, backgroundMapPromise).then(function() {
			var openerMap = mapObj.getMap();
			var service = backgroundMapObj.getService();
			backgroundMapObj.setService(service);
			
			var tmsType = backgroundMapObj.getType();
			var tms = null;
			if(tmsType) {
				backgroundMapObj.setType(tmsType);
				tms = backgroundMapObj.getLayer(service, tmsType);
			}

			var origin = mapObj.getTMSOrigin();
			if (origin) {
				tms[0].getSource().getTileGrid().origin_ = origin;		
			}
			
			var view = backgroundMapObj.getView(service, tms);
			
			var center = openerMap.getCenter();
			var scale = openerMap.getScale();
			var extent = openerMap.getExtent();
			
//			themamapObj.basemapObj.data = $.extend(true, [], opener.themamapObj.basemapObj.getData());
			that.viewMaps(view, tms, center, scale, extent);
//			themamapObj.basemapObj.show(highRankMapObj.getMap());
//			
//			var format = new ol.format.GeoJSON();
//			var features = format.readFeatures(opener.mapObj.getVectorFeatures());
//			for(var i=0, len=features.length; i < len; i++) {
//				if(features[i].getProperties().type == "Circle") {
//					features[i] = spatialUtils.convertPointToCircle(features[i]);
//				}
//			}
//			windowObj.drawToolObj.source.addFeatures(features);
			
//			exportOutputObj.init();
		});
	},
	
//	getVectorFeatures : function() {
//		var that = this;
//		var addFeatures = [];
//		var layers = that.map.getLayers();
//		
//		for(var i=0, len=layers.get("length"); i < len; i++) {
//			var layer = layers.item(i);
//			var layerId = layer.get("id");
//			if(layer.getVisible() && layer.getSource && layer.getSource().getFeatures) {
//				var features = $.extend([], layer.getSource().getFeatures());
//				if(features && features.length > 0) {
//					if(layerId && (layerId == "drawToolLayer" || layerId.indexOf("user_") == 0)) {
//						// Lks : 그리기 도구 & 사용자 그래픽
//						for(var j=0, jLen=features.length; j < jLen; j++) {
//							if(features[j].getProperties().type == "Circle") {
//								features[j] = spatialUtils.convertCircleToPoint(features[j]);
//							}
//							addFeatures.push(features[j]);
//						}
//					}
//					else if(layer.getStyle() instanceof Function) {
//						// Lks : 공간확인(Orange)
//						for(var j=0, jLen=features.length; j < jLen; j++) {
//							var feature = features[j];
//							var style = null;
//							feature.setProperties(that.getPropertiesByStyle(feature, style));
//							if(layer.getStyle()(feature) instanceof Array) {
//								style = layer.getStyle()(feature)[0];
//							}
//							else {
//								style = layer.getStyle()(feature);
//							}
//							addFeatures.push(feature);
//						}
//					}
//					else if(layer.getStyle() instanceof ol.style.Style) {
//						// Lks : 검색목록 선택  & 측정 & SHAPE 가져오기
//						var style = layer.getStyle();
//						for(var j=0, jLen=features.length; j < jLen; j++) {
//							var feature = features[j];
//							feature.setProperties(that.getPropertiesByStyle(feature, style));
//							if(feature.getProperties().type == "Circle") {
//								feature = spatialUtils.convertCircleToPoint(feature);
//							}
//							addFeatures.push(feature);
//						}
//					}
//					else {
//						for(var j=0, jLen=features.length; j < jLen; j++) {
//							var feature = features[j];
//							var geometry = feature.getGeometry();
//							var properties = null;
//							if(geometry instanceof ol.geom.Point) {
//								properties = {
//									type : "WellKnown",
//									radius : 5,
//									stroke : "#3399CC",
//									strokeOpacity : 1,
//									strokeWidth: 1.25,
//									strokeDasharray : "solid",
//									fill : "#ffffff",
//									fillOpacity : 0.4
//								};
//							}
//							else if(geometry instanceof ol.geom.LineString || geometry instanceof ol.geom.MultiLineString) {
//								properties = {
//									type : "LineString",
//									stroke : "#3399CC",
//									strokeOpacity : 1,
//									strokeWidth: 1.25,
//									strokeDasharray : "solid"
//								};
//							}
//							else if(geometry instanceof ol.geom.Polygon || geometry instanceof ol.geom.MultiPolygon || geometry instanceof ol.geom.Circle) {
//								properties = {
//									type : "Polygon",
//									stroke : "#3399CC",
//									strokeOpacity : 1,
//									strokeWidth: 1.25,
//									strokeDasharray : "solid",
//									fill : "#ffffff",
//									fillOpacity : 0.4
//								};
//							}
//							else {
//								console.log("정의되지 않은 타입 입니다.");
//							}
//							feature.setProperties(properties);
//							addFeatures.push(feature);
//						}
//					}
//				}
//			}
//		}
//		var format = new ol.format.GeoJSON();
//		return format.writeFeatures(addFeatures);
//	},
	
	viewMaps : function(view, tms, center, scale, extent) {
		var that = this;
		
		that.scale = scale;
		that.extent = extent;
//		that.flag = flag;
		
//		that.initUi();
//		that.bindEvents();
		
		that.initGis(view, tms);
		that.moveMap(center, scale);
	},
	
	initGis : function(view, tms) {
		var that = this;
		
		var layers = [];
		if(tms) {
			layers = layers.concat(tms);
		}
//		layers.push(new ol.layer.Group({
//	    	id : "base_map_group"
//	    }));
		
		var obj = document.getElementById("div_loan_map_");
		obj.setAttribute("id", "div_loan_map_" + that.prtIdn);
		
		layers.push(new ol.layer.Image({
			id : "loan_wms_" + that.prtIdn,
			source : new kworks.source.ImageWMS({
				url : CONTEXT_PATH + "/proxy/wms.do",
				params : $.extend(serverObj.getWMSParams(), sldObj.getParams()),
				ratio : 1,
				serverType : MAP.SERVER_TYPE
			})
		}));
		
		that.map = new kworks.Map({
			target : "div_loan_map_" + that.prtIdn,
			layers : layers,
			view : view
		});
		
//		that.map.on("moveend", function(evt) {
//			that.loadLegend();
//			
//			var scale = null;
//			var extent = null;
//			
//			if(!that.flag){
//				scale = evt.map.getScale();
//				extent = evt.map.getExtent();
//			}else{
//				scale = that.scale;
//				extent = that.extent;
//			}
//			
//			// 20170105 / 윤중근 / 축척제한 1000 해제
//			/*if(scale > 1000) {
//				evt.map.moveToScale(1000);
//			}*/
//			that.loadScale(scale);
//			that.loadExtent(extent);
//		});
		
		that.setMap(that.map);
		
		/// 벡터 레이어 추가
		windowObj.drawToolObj.setMap(that.getMap());
		windowObj.drawToolObj.addLayer();
		
		cmmnPropObj.drawToolObj.init();
		highlightLoanObj.init();
//		cmmnPropObj.drawToolObj.init(that.getMap());
	},
	
	moveMap : function(center, scale) {
		var that = this;
		that.map.moveToCenter(center);
		that.map.moveToScale(scale);
		
	},
	
	getMap : function() {
		var that = this;
		return that.map;
	},
	
	setMap : function(map){
		var that = this;
		that.map = map;
	},
	
//	reloadWMS : function() {
//		var that = this;
//		var mapIndex = 0;
//		var map = that.maps.getMap(mapIndex);
//		var layer = map.getLayer("loan_wms_" + that.prtIdn);
//		var source = layer.getSource();
//		var params =  {LAYERS : "LP_PA_CBND", STYLES : "LP_PA_CBND", FORMAT : "image/png", nocache : Math.random(), SLD_KEY : null };
//		
//		source.updateParams(params);
//	},
	
//	createMaps : function() {
//		var that = this;
//		
//		//항공영상 kworks lib에서 맵생성
//		that.maps = new kworks.Maps();
//		
//		//메인 항공영상 위치정보 set
//		that.maps.setBaseMap(mapObj.getMap());
////		debugger;
////		that.setMaps();
//		
//		var types = backgroundMapObj.data.tms.types;
//		var layers = [];
//		for(var key in types) {
//			layers.push(types[key]);
//		}
//		layers.reverse();
//		
//		var layer = layers[0].layers;
//		
//		var obj = document.getElementById("div_loan_map_");
//		obj.setAttribute("id", "div_loan_map_" + that.prtIdn);
////		alert( obj.getAttribute("id") );
//		
//		//실제 맵생성 하는곳 id값에 지도영역 넣기
//		that.maps.addMap(that.createMap("div_loan_map_" + that.prtIdn, layer));
//		
//		mapObj.getMap().updateSize();
//	},
	
//	createMap : function(target, layer) {
//		var that = this;
//		that.maps.clear();
//		var layers = [];
//		for(var i=0, len=layer.length; i < len; i++) {
//			layers.push(layer[i]);
//		}
//		layers.push(new ol.layer.Image({
//			id : "loan_wms_" + that.prtIdn,
//			source : new kworks.source.ImageWMS({
//				url : CONTEXT_PATH + "/proxy/wms.do",
//				params : $.extend(serverObj.getWMSParams(), {
//					LAYERS : serverObj.getBlankLayers(),
//					STYLES : serverObj.getBlankLayers()
//				}),
//				ratio : 1,
//				serverType : MAP.SERVER_TYPE
//			})
//		}));
//		
//		var view = backgroundMapObj.getView(service, tms);
//		
//		return new kworks.Map({
//			target : target,
//			layers : layers,
//			view : view
//		});
//	},

	
//	setMaps : function(){
//		var that = this;
//	
//		var pnu = pnuObj.createPnu(that.dongCode, that.mntn, that.mnnm, that.slno);
//		
//		var url = CONTEXT_PATH + "/rest/spatial/" + COMMON.LAND + "/listAll.do";
//		var filter = {
//				filterType : "COMPARISON",
//				comparisonType : "EQUAL_TO",
//				property : "PNU",
//				value : pnu
//			};
//		
//		$.ajax({
//			type : 'POST',
//			dataType : 'json',
//			url : url,
//			data : filter,
//			async : false,
//			success : function(response){
//				var rows = response.rows;
//				if(rows && rows.length > 0) {
//					var features = [];
//					var format = new ol.format.WKT();
//					for(var i=0, len=rows.length; i < len; i++) {
//						var data = rows[i];
//						features.push(format.readFeature(data[MAP.GEOMETRY_ATTR_NAME]));
//					}
//					highlightObj.showRedFeatures(that.CLASS_NAME, features, true, {
//						projection : ol.proj.get(MAP.PROJECTION)
//					});
//					debugger;
//				}
//			},error : function(err){
//				console.error(err);
//			}
//		});
//	},
	
	geomBindEvent : function(){
		var that = this;
		
		$('.btn_data', that.selector).hide();
		$('.btn_data_empty', that.selector).show();
		
		//등록 버튼
		$('#btn_geom_insert', that.selector).click(function(){
			var dept = cmmnPropObj.getDept();
		
			if(that.manCde != dept.code + '0000'){
				$.messager.alert('대부물건 등록', '권한이 없습니다.');
			}else{
				that.insertEvent();
			}
		});
		
		//삭제
		$('#btn_geom_delete', that.selector).click(function(){
			var dept = cmmnPropObj.getDept();
		
			if(that.manCde != dept.code + '0000'){
				$.messager.alert('대부물건 등록', '권한이 없습니다.');
			}else{
				$.messager.confirm('대부 물건 삭제', '삭제하시겠습니까?', function(isDel){
					if(isDel){
						that.deleteEvent();
					}
				});
			}
		});
		
		//그리기
		$('#btn_data_drow', that.selector).click(function(){
			cmmnPropObj.drawToolObj.draw();
//			that.clearTools();
		});
		
		//점편집
		$('#btn_data_pointUp', that.selector).click(function(evt){
			cmmnPropObj.drawToolObj.modify(evt);
//			that.pointEdit();
		});
		
		//저장
		$('#btn_data_insert', that.selector).click(function(){
			that.insertData();
//			that.loadDetail('BML_LOUS_AS');
		});
		
		//취소
		$('#btn_data_exit', that.selector).click(function(){
			that.exitEvent();
		});
		
		
	},
	
	deleteEvent : function(){
		var that = this;
		
		var data = $(".grid_result_geom", that.selector).datagrid("getSelections");
		
		if(data){
			var lonIdn = data[0].lonIdn;
			var url = CONTEXT_PATH + '/cmmnprop/' + lonIdn + '/lousDelete.do';
			
			$.ajax({
				type : 'GET',
				dataType : 'json',
				url : url,
				async : false,
				success : function(rs){
					that.loadDetail('BML_LOUS_AS');
					$.messager.alert("대부 물건", "대부 물건 정보가 삭제되었습니다.");
				},error : function(err){
					$.messager.alert("대부 물건", "대부 물건 정보 삭제 중 오류가 발생했습니다. 관리자에게 문의하세요.");
				}
			});
		}else {
			$.messager.alert("대부 물건", "대부 물건 정보를 선택하여 주십시오.");
		}
	},
	
	insertEvent : function() {
		var that = this;
		
		$('.btn_data', that.selector).show();
		$('.btn_data_empty', that.selector).hide();
	},
	
	insertData : function() {
		var that = this;
		var prtIdn = that.prtIdn;
		
		if(prtIdn){
			cmmnPropObj.drawToolObj.open(prtIdn);
		}else{
			$.messager.alert(dataAlias, '검색 결과가 없습니다.');
		}
	},
	
	exitEvent : function (){
		var that = this;
		
		$('.btn_data', that.selector).hide();
		$('.btn_data_empty', that.selector).show();
	},
	
};

// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

var cmmnOccpObj = {

		TITLE : "무단점유사용 검색",
		
		CLASS_NAME : "CommonOccpSearch",
		
		selector : null,
		
		isClosed : false,
		
		dataId : "BML_OCCP_AS",
		
		open : function() {
			var that = this;
			
			if(that.selector) {
				that.close();
			}
			
			var windowOptions = {
				width :	1000,
				height : 216,
				top : 120,
				left : 330,
				onClose : function() {
					that.close();
				}
			};
			
			var url = CONTEXT_PATH + '/cmmnprop/searchOccpPage.do';
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
				that.initUi();
				that.init();
				that.bindEvents();
			});
				
			that.selector = "#" + id;
			
		},
		
		init : function(){
			var that = this;
			
			var dongList = dongObj.getData();
			var emdData = [];
			
			for(var i = 0 ; i < dongList.length; i++){
				emdData.push(dongList[i]);
			}
			
			emdData.unshift({
				bjdCde : '',
				bjdNam : ''
			});
			
			$('#comboBx_emd', that.selector).combobox('loadData', emdData);
			$('#comBx_pbpKnd', that.selector).combobox('select', '');
			
		},
		
		initUi : function(){
			var that = this;
			
			$('.swtchBtn', that.selector).switchbutton();
			
			$('.numSpn', that.selector).numberspinner({
				min : 0,
				max: 999999
			});
			
			$('#comboBx_emd', that.selector).combobox({
				valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
				textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD)
			});
			
			$('.textbx', that.selector).textbox({
				width : 362
			});
			
			$('.datebx', that.selector).datebox({
				width : 169
			});
			
			$('#comBx_pbpKnd', that.selector).combobox({
				valueField : 'value',
				textField : 'label',
				data:[{
					value:'',
					label:''
				},{
					value:'01',
					label:'토지'
				},{
					value:'02',
					label:'건물'
				}],
				width : 362
			});
			
			$('.numSpn_wide', that.selector).numberspinner({
				min : 0,
				max: 999999,
				width : 362
			});
		},
			
		bindEvents : function(){
			var that = this;
			
			// 초기화
			$("#btn_reset", that.selector).on("click", function() {
				that.clean();
				return false;
			});

			// 검색
			$("#btn_search", that.selector).on("click", function() {
				that.search();
				return false;
			});
		},
		
		search : function(){
			var that = this;
			
			var emd = $('#comboBx_emd', that.selector).combobox('getValue');
			var mntn = $('#mntn', that.selector).switchbutton('options').checked?'2':'1';
			var bun = $('#mnnm', that.selector).numberspinner('getValue');
			var ho = $('#slno', that.selector).numberspinner('getValue');
			//var prtIdn = $('#numSpn_prtIdn', that.selector).numberspinner('getValue');
			var pbpKnd = $('#comBx_pbpKnd', that.selector).combobox('getValue');
			var empNam = $('#textBx_empNam', that.selector).textbox('getValue');
			var empNum = $('#textBx_empNum', that.selector).textbox('getValue');
			var ocpStr = $('#dateBx_ocpStr', that.selector).datebox('getValue');
			var ocpEnd = $('#dateBx_ocpEnd', that.selector).datebox('getValue');
			
			var param = { 
					emd : emd,
					mntn : mntn,
					bun : bun,
					ho : ho,
					//prtIdn : prtIdn,
					pbpKnd : pbpKnd,
					empNam : empNam,
					empNum : empNum,
					ocpStr : ocpStr,
					ocpEnd : ocpEnd
				};
			
			var cql = that.createSqlWhereConstruction(param);
			
			var filter = {
					filterType : 'CQL',
					cql : cql
				};
			
			resultObj.close();
			
			cmmnPropObj.searchRes(that.dataId, filter);
		},
		
		
		clean : function() {
			var that = this;
			$('#comboBx_emd', that.selector).combobox('select', '');
			$('#mntn', that.selector).switchbutton('clear');
			$('#mnnm', that.selector).numberspinner('setValue','');
			$('#slno', that.selector).numberspinner('setValue','');
			//$('#numSpn_prtIdn', that.selector).numberspinner('setValue','');
			$('#comBx_pbpKnd', that.selector).combobox('select', '');
			$('.textbx', that.selector).textbox('clear');
			$('#textBx_ocpAdr', that.selector).textbox('clear');
			$('.datebx', that.selector).datebox('clear');
		},
		
		close : function() {
			var that = this;
			
			that.clean();
			windowObj.removeWindow(that.selector);
		},
		
		createSqlWhereConstruction : function(param){
			/*if(
					(param.emd == '' || param.emd == null) && 
					(param.mntn == '1') &&
					(param.bun == '' || param.bun == null) && 
					(param.ho == '' || param.ho == null) && 
					//(param.prtIdn == '' || param.prtIdn == null) && 
					(param.pbpKnd == '' || param.pbpKnd == null) &&
					(param.empNam == '' || param.empNam == null) &&
					(param.empNum == '' || param.empNum == null) && 
					(param.ocpStr == '' || param.ocpStr == null) && 
					(param.ocpEnd == '' || param.ocpEnd == null)
			){
				return '1 = 1';
			}else{*/
				var cqlData = "";
				
				if(param.emd != '' && param.emd != null){
					cqlData+=" AND REGN_CODE = '" + param.emd +"'";
				}
				if(param.mntn != '' && param.mntn != null){
					if((param.emd != '' && param.emd != null && param.bun != '' && param.bun != null) || param.mntn == '2') {
						cqlData+=" AND SAN = '" + param.mntn +"'";
					}
				}
				if(param.bun != '' && param.bun != null){
					cqlData+=" AND BUNJI = '" + param.bun +"'";
				}
				if(param.ho != '' && param.ho != null){
					cqlData+=" AND HO = '" + param.ho +"'";
				}
				/*if(param.prtIdn != '' && param.prtIdn != null){
					cqlData+=" AND PRT_IDN = '" + param.prtIdn +"'";
				}*/
				if(param.pbpKnd != null){
					if(param.pbpKnd != ''){
						cqlData+=" AND PBP_KND = '" + param.pbpKnd +"'";
					}
				}
				if(param.empNam != '' && param.empNam != null){
					cqlData+=" AND EMP_NAM LIKE '%" + param.empNam +"%'";
				}
				if(param.empNum != '' && param.empNum != null){
					cqlData+=" AND EMP_NUM LIKE '%" + param.empNum +"%'";
				}
				if(param.ocpStr != '' && param.ocpStr != null){
					cqlData+=" AND OCP_STR >= '" + param.ocpStr +"'";
				}
				if(param.ocpEnd != '' && param.ocpEnd != null){
					cqlData+=" AND OCP_END <= '" + param.ocpEnd +"'";
				}
				
				var cql = cqlData.replace(' AND ', '');
				
				return cql;
			//}
		}
};

cmmnOccpObj.view = {
		
		dataId : null,
		
		selector : null,
		
		CLASS_NAME : "CommonOousView",
		
		data : {},
		
		manCde : null,
		
		init : function(ocpIdn, dataId, selector, param, prtIdn){
			var that = this;
			
			that.dataId = dataId;
			that.selector = selector;
			
			that.dataLoading(ocpIdn, dataId, selector, param, prtIdn);
			
			that.initUi();
			
			that.geomDataLoding(prtIdn);
		},
		
		getMap : function() {
			var that = this;
			return that.map;
		},
		
		setMap : function(map){
			var that = this;
			that.map = map;
		},
		
		viewMaps : function(view, tms, center, scale, extent) {
			var that = this;
			
			that.scale = scale;
			that.extent = extent;
			
			that.initGis(view, tms);
			that.moveMap(center, scale);
		},
		
		initGis : function(view, tms) {
			var that = this;
			
			var layers = [];
			if(tms) {
				layers = layers.concat(tms);
			}
			
			var obj = document.getElementById("div_oous_map_");
			obj.setAttribute("id", "div_oous_map_" + that.prtIdn);
			
			layers.push(new ol.layer.Image({
				id : "oous_wms_" + that.prtIdn,
				source : new kworks.source.ImageWMS({
					url : CONTEXT_PATH + "/proxy/wms.do",
					params : $.extend(serverObj.getWMSParams(), sldObj.getParams()),
					ratio : 1,
					serverType : MAP.SERVER_TYPE
				})
			}));
			
			that.map = new kworks.Map({
				target : "div_oous_map_" + that.prtIdn,
				layers : layers,
				view : view
			});
			
			that.setMap(that.map);
			
			/// 벡터 레이어 추가
			windowObj.drawToolObj.setMap(that.getMap());
			windowObj.drawToolObj.addLayer();
			
			cmmnPropObj.oousToolObj.init();
			highlightOousObj.init();
		},
		
		showFeatures : function() {
			var that = this;
			
			that.removeFeatures();
			
			var data = dataObj.getDataByDataId('BML_OOUS_AS');
			if (data.dataTy == 'LAYER') {
				var format = new ol.format.WKT();
				var features = [];
				var data = $(".grid_result_geom", that.selector).datagrid("getSelections");
				for (var i = 0, len = data.length; i < len; i++) {
					var row = data[i];
					var geom = row[MAP.GEOMETRY_ATTR_NAME];
					if (geom) {
//						var format = new ol.format.GeoJSON();
//						var features = format.readFeatures(opener.mapObj.getVectorFeatures());
						var feature = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);
						features.push(feature);
					} else {
						if (data.length == 1) {
							$.messager.alert(that.TITLE, '공간 정보가 없습니다.');
						}
					}
				}
				if (features && features.length > 0) {
					highlightOousObj.showRedFeatures(that.CLASS_NAME, features, !that.isFixed, {
								projection : ol.proj.get(MAP.PROJECTION)
							});
				}
			}
		},
		
		moveMap : function(center, scale) {
			var that = this;
			that.map.moveToCenter(center);
			that.map.moveToScale(scale);
			
		},
		
		geomDataLoding : function(prtIdn){
			var that =  this;
			
			that.backgroundUi();
			that.geomBindEvent();
			that.geomDataLode(prtIdn);
		},
		
		backgroundUi : function(){
			var that = this;
			
			that.loadMaps();
		},
		
		geomBindEvent : function(){
			var that = this;
			
			$('.btn_data', that.selector).hide();
			$('.btn_data_empty', that.selector).show();
			
			//등록 버튼
			$('#btn_geom_insert', that.selector).click(function(){
				var dept = cmmnPropObj.getDept();
		
				if(that.manCde != dept.code + '0000'){
					$.messager.alert('대부물건 등록', '권한이 없습니다.');
				}else{
					that.insertEvent();
				}
			});
			
			//삭제
			$('#btn_geom_delete', that.selector).click(function(){
				var dept = cmmnPropObj.getDept();
		
				if(that.manCde != dept.code + '0000'){
					$.messager.alert('대부물건 등록', '권한이 없습니다.');
				}else{
					$.messager.confirm('무단점유 물건 삭제', '삭제하시겠습니까?', function(isDel){
						if(isDel){
							that.deleteEvent();
						}
					});
				}
			});
			
			//그리기
			$('#btn_data_drow', that.selector).click(function(){
				cmmnPropObj.oousToolObj.draw();
			});
			
			//점편집
			$('#btn_data_pointUp', that.selector).click(function(evt){
				cmmnPropObj.oousToolObj.modify(evt);
			});
			
			//저장
			$('#btn_data_insert', that.selector).click(function(){
				that.insertData();
			});
			
			//취소
			$('#btn_data_exit', that.selector).click(function(){
				that.exitEvent();
			});
		},
		removeFeatures : function() {
			var that = this;
			highlightObj.removeFeatures(that.CLASS_NAME);
		},
		
		insertData : function() {
			var that = this;
			var prtIdn = that.prtIdn;
			
			if(prtIdn){
				cmmnPropObj.oousToolObj.open(prtIdn);
			}else{
				$.messager.alert(dataAlias, '검색 결과가 없습니다.');
			}
		},
		
		exitEvent : function (){
			var that = this;
			
			$('.btn_data', that.selector).hide();
			$('.btn_data_empty', that.selector).show();
		},
		
		loadMaps : function(){
			var that = this;
			
			jqueryUtils.configAjax();
			
			// 좌표계 검색
			var cntmPromise = coordinateObj.load();
			
			// Sld 설정
			sldObj.setDataToString(sldObj.getDataToString());
			var sldPromise = sldObj.putSld();
			
			var backgroundMapPromise = backgroundMapObj.init(false);
			$.when(cntmPromise, sldPromise, backgroundMapPromise).then(function() {
				var openerMap = mapObj.getMap();
				var service = backgroundMapObj.getService();
				backgroundMapObj.setService(service);
				
				var tmsType = backgroundMapObj.getType();
				var tms = null;
				if(tmsType) {
					backgroundMapObj.setType(tmsType);
					tms = backgroundMapObj.getLayer(service, tmsType);
				}

				var origin = mapObj.getTMSOrigin();
				if (origin) {
					tms[0].getSource().getTileGrid().origin_ = origin;		
				}
				
				var view = backgroundMapObj.getView(service, tms);
				
				var center = openerMap.getCenter();
				var scale = openerMap.getScale();
				var extent = openerMap.getExtent();
				
				that.viewMaps(view, tms, center, scale, extent);
			});
		},
		
		deleteEvent : function(){
			var that = this;
			var data = $(".grid_result_geom", that.selector).datagrid("getSelections");
			
			if(data){
				var ocpIdn = data[0].ocpIdn;
				var url = CONTEXT_PATH + '/cmmnprop/' + ocpIdn + '/oousDelete.do';
				
				$.ajax({
					type : 'GET',
					dataType : 'json',
					url : url,
					async : false,
					success : function(rs){
						that.loadDetail('BML_OOUS_AS');
						$.messager.alert("무단점유 물건", "무단점유 물건 정보가 삭제되었습니다.");
					},error : function(err){
						$.messager.alert("무단점유 물건", "무단점유 물건 정보 삭제 중 오류가 발생했습니다. 관리자에게 문의하세요.");
					}
				});
			}else {
				$.messager.alert("무단점유 물건", "무단점유 물건 정보를 선택하여 주십시오.");
			}
		},
		
		geomDataLode : function(prtIdn){
			var that = this;
				
			var dataId = 'BML_OOUS_AS';
			
			var kwsData = dataObj.getDataByDataId(dataId, true);
			var kdf = kwsData.kwsDataFields;
			
			var pkCol = '';
			
			var cols = [];
			var frozenCol = [];
			
			var id = tabObj.getId();
			
			for(var i = 0; i < kdf.length; i++){
				if(kdf[i].pkAt.indexOf('Y') > -1){
					
					pkCol = camelcaseUtils.underbarToCamelcase(kdf[i].fieldId);
					
					frozenCol.push({
						field : pkCol,
						title : '번호',
						width : 100,
						sortable : true
					});
				}else if(kdf[i].indictTy.indexOf('HIDE') < 0 && kdf[i].indictTy.indexOf('WKT') < 0){
					cols.push({
						field : camelcaseUtils.underbarToCamelcase(kdf[i].fieldId),						
						title : kdf[i].fieldAlias,
						width : 100,
						sortable : false
					});
				}
			}
			
			$('.grid_result_geom', that.selector).datagrid({
				pk : pkCol,
				frozenColumns : [frozenCol],
				columns : [cols],
				singleSelect : true,
				showFooter : false,
				pagination : true,
				onSelect : function(rowIndex, rowData) {
					that.showFeatures();
				},
				onCheckAll : function(rowIndex, rowData) {
					that.showFeatures();
				},
				onUnselect : function(rowIndex, rowData) {
					that.removeFeatures();
				},
				onUncheckAll : function(rowIndex, rowData) {
					that.removeFeatures();
				}
			});
			
			var cql = " PRT_IDN = " + prtIdn;
			
			var filter = {
					filterType : 'CQL',
					cql : cql
			};
			
			var rows = cmmnPropObj.syncAjaxSummaries(filter, dataId);
			
			if(rows && rows.length > 0){
				that.data[id]['oous'] = {
					pageNumber : 1,
					pageSize : 10,
					ids : rows[0].ids
				};
				that.loadDetail(dataId);
			}
				
			var pagination = $('.grid_result_geom', that.selector).datagrid('getPager');
			pagination.pagination({
				displayMsg : '{from} - {to} / {total}',
				onSelectPage : function(pageNumber, pageSize){
					that.data[id]['oous'].pageNumber = pageNumber;
					that.data[id]['oous'].pageSize = pageSize;
					that.loadDetail(dataId);
				}
			});
		},
		
		initUi : function(){
			var that = this;
			
			$('.cmmn_tabs', that.selector).tabs();
		},
		
		setSelector : function(){
			var that = this;
			
			that.selector = '#' + tabObj.getId();
		},
		
		dataLoading : function(ocpIdn, dataId, selector, param, prtIdn){
			var that = this;
			var url = "";
			that.prtIdn = prtIdn;
			if (param=="01"){
				url = CONTEXT_PATH + '/cmmnprop/' + ocpIdn + '/' + prtIdn + '/searchOccpPropData.do';
			}else if (param=="02"){
				url = CONTEXT_PATH + '/cmmnprop/' + ocpIdn + '/' + prtIdn + '/searchOccpBuidData.do';
			}else {
				url = CONTEXT_PATH + '/cmmnprop/' + ocpIdn + '/' + prtIdn + '/searchOccpData.do';
			}
			
			$.ajax({
				type : 'GET',
				dataType : 'json',
				url : url,
				async : false,
				success : function(rs){
					var bmlOccpDt = rs.bmlOccpDt;
					
					if(rs.bmlBuidAs){
						var bmlBuidAs = rs.bmlBuidAs;
						
						var data = dataObj.getDataByDataId('BML_BUID_AS', true);
						var dataField = data.kwsDataFields;
						
						for(var i = 0; i < dataField.length; i++){
							if(dataField[i].indictTy.indexOf('CURRENCY') > -1){
								bmlBuidAs[camelcaseUtils.underbarToCamelcase(dataField[i].fieldId)] = numberUtils.formatCurrency(bmlBuidAs[camelcaseUtils.underbarToCamelcase(dataField[i].fieldId)]);
							}
						}
						
						
						$('#sub_accCde', that.selector).html(bmlBuidAs.accCde);
						$('#sub_acqCde', that.selector).html(bmlBuidAs.acqCde);
						$('#sub_acqDate', that.selector).html(bmlBuidAs.acqDate);
						$('#sub_acqCde', that.selector).html(bmlBuidAs.acqCde);
						$('#sub_acqDept', that.selector).html(bmlBuidAs.acqDept);
						$('#sub_acqCde', that.selector).html(bmlBuidAs.acqCde);
						$('#sub_acqPc', that.selector).html(bmlBuidAs.acqPc);
						$('#sub_acqPrv', that.selector).html(bmlBuidAs.acqPrv);
						$('#sub_acsArea', that.selector).html(bmlBuidAs.acsArea);
						$('#sub_amnCde', that.selector).html(bmlBuidAs.amnNm);
						$('#sub_autAmnt', that.selector).html(bmlBuidAs.autAmnt);
						$('#sub_bjdCde', that.selector).html(bmlBuidAs.bjdCde);
						$('#sub_bldArea', that.selector).html(bmlBuidAs.bldArea);
						$('#sub_bldDate', that.selector).html(bmlBuidAs.bldDate);
						$('#sub_bldNam', that.selector).html(bmlBuidAs.bldNam);
						$('#sub_bldPri', that.selector).html(bmlBuidAs.bldPri);
						$('#sub_bldPrp', that.selector).html(bmlBuidAs.bldPrp);
						$('#sub_bldSe', that.selector).html(bmlBuidAs.bldSe);
						$('#sub_bun', that.selector).html(bmlBuidAs.bun);
						$('#sub_chrNam', that.selector).html(bmlBuidAs.chrNam);
						$('#sub_compMat', that.selector).html(bmlBuidAs.compMat);
						$('#sub_grdCde', that.selector).html(bmlBuidAs.grdCde);
						$('#sub_grsArea', that.selector).html(bmlBuidAs.grsArea);
						$('#sub_hjdCde', that.selector).html(bmlBuidAs.hjdCde);
						$('#sub_ho', that.selector).html(bmlBuidAs.ho);
						$('#sub_loanYn', that.selector).html(bmlBuidAs.loanYn);
						$('#sub_locPlc', that.selector).html(bmlBuidAs.locPlc);
						$('#sub_manCde', that.selector).html(bmlBuidAs.manNm);
						$('#sub_mndCde', that.selector).html(bmlBuidAs.mndNm);
						$('#sub_monut', that.selector).html(bmlBuidAs.monut);
						$('#sub_objectId', that.selector).html(bmlBuidAs.objectId);
						$('#sub_ownCde', that.selector).html(bmlBuidAs.ownNm);
						$('#sub_prsCde', that.selector).html(bmlBuidAs.mesrvNm);
						$('#sub_prtIdn', that.selector).html(bmlBuidAs.prtIdn);
						$('#sub_prtNam', that.selector).html(bmlBuidAs.prtNam);
						$('#sub_pstNum', that.selector).html(bmlBuidAs.pstNum);
						$('#sub_ptyPc', that.selector).html(bmlBuidAs.ptyPc);
						$('#sub_rfCde', that.selector).html(bmlBuidAs.rfCde);
						$('#sub_rmkExp', that.selector).html(bmlBuidAs.rmkExp);
						$('#sub_rnAddr', that.selector).html(bmlBuidAs.rnAddr);
						$('#sub_rstYn', that.selector).html(bmlBuidAs.rstYn);
						$('#sub_saleLmt', that.selector).html(bmlBuidAs.saleLmt);
						$('#sub_spcAdr', that.selector).html(bmlBuidAs.spcAdr);
						$('#sub_spcDong', that.selector).html(bmlBuidAs.spcDong);
						$('#sub_spcHo', that.selector).html(bmlBuidAs.spcHo);
						$('#sub_srcCde', that.selector).html(bmlBuidAs.srcCde);
						$('#sub_ugrdCde', that.selector).html(bmlBuidAs.ugrdCde);
					}else if(rs.bmlPropAs){
						var bmlPropAs = rs.bmlPropAs;
						
						var data = dataObj.getDataByDataId('BML_PROP_AS', true);
						var dataField = data.kwsDataFields;
						
						for(var i = 0; i < dataField.length; i++){
							if(dataField[i].indictTy.indexOf('CURRENCY') > -1){
								bmlPropAs[camelcaseUtils.underbarToCamelcase(dataField[i].fieldId)] = numberUtils.formatCurrency(bmlPropAs[camelcaseUtils.underbarToCamelcase(dataField[i].fieldId)]);
							}
						}
						
						$('#sub_accCde', that.selector).html(bmlPropAs.accNm);
						$('#sub_acqAra', that.selector).html(bmlPropAs.acqAra);
						$('#sub_acqCde', that.selector).html(bmlPropAs.acqCde);
						$('#sub_acqDate', that.selector).html(bmlPropAs.acqDate);
						$('#sub_acqDept', that.selector).html(bmlPropAs.acqDept);
						$('#sub_acqPc', that.selector).html(bmlPropAs.acqPc);
						$('#sub_acqPrv', that.selector).html(bmlPropAs.acqPrv);
						$('#sub_amnCde', that.selector).html(bmlPropAs.amnNm);
						$('#sub_area', that.selector).html(bmlPropAs.area);
						$('#sub_autAmnt', that.selector).html(bmlPropAs.autAmnt);
						$('#sub_bjdCde', that.selector).html(bmlPropAs.bjdCde);
						$('#sub_bmlCde', that.selector).html(bmlPropAs.bmlCde);
						$('#sub_bun', that.selector).html(bmlPropAs.bun);
						$('#sub_chrNam', that.selector).html(bmlPropAs.chrNam);
						$('#sub_chrQta', that.selector).html(bmlPropAs.cnrQta);
						$('#sub_ctyPlan', that.selector).html(bmlPropAs.ctyPlan);
						$('#sub_dwk', that.selector).html(bmlPropAs.dwk);
						$('#sub_ho', that.selector).html(bmlPropAs.ho);
						$('#sub_loanYn', that.selector).html(bmlPropAs.loanYn);
						$('#sub_locPlc', that.selector).html(bmlPropAs.locPlc);
						$('#sub_manCde', that.selector).html(bmlPropAs.manNm);
						$('#sub_mndCde', that.selector).html(bmlPropAs.mndNm);
						$('#sub_mokCde', that.selector).html(bmlPropAs.mokCde);
						$('#sub_monut', that.selector).html(bmlPropAs.monut);
						$('#sub_objectId', that.selector).html(bmlPropAs.objectId);
						$('#sub_olnlp', that.selector).html(bmlPropAs.olnlp);
						$('#sub_ownCde', that.selector).html(bmlPropAs.ownNm);
						$('#sub_parea', that.selector).html(bmlPropAs.parea);
						$('#sub_planBns', that.selector).html(bmlPropAs.planBns);
						$('#sub_planFty', that.selector).html(bmlPropAs.planFty);
						$('#sub_prsCde', that.selector).html(bmlPropAs.mesrvNm);
						$('#sub_prtIdn', that.selector).html(bmlPropAs.prtIdn);
						$('#sub_prtNam', that.selector).html(bmlPropAs.prtNam);
						$('#sub_pstNum', that.selector).html(bmlPropAs.pstNum);
						$('#sub_ptyPc', that.selector).html(bmlPropAs.ptyPc);
						$('#sub_rmkExp', that.selector).html(bmlPropAs.rmkExp);
						$('#sub_rnAddr', that.selector).html(bmlPropAs.rnAddr);
						$('#sub_rstYn', that.selector).html(bmlPropAs.rstYn);
						$('#sub_saleLmt', that.selector).html(bmlPropAs.saleLmt);
						$('#sub_spcAdr', that.selector).html(bmlPropAs.spcAdr);
						$('#sub_spcDong', that.selector).html(bmlPropAs.spcDong);
						$('#sub_spcHo', that.selector).html(bmlPropAs.spcHo);
						$('#sub_spfc', that.selector).html(bmlPropAs.spfc);
					}
					
					var data = dataObj.getDataByDataId('BML_OCCP_AS', true);
					var dataField = data.kwsDataFields;
					
					for(var i = 0; i < dataField.length; i++){
						if(dataField[i].indictTy.indexOf('CURRENCY') > -1){
							bmlOccpDt[camelcaseUtils.underbarToCamelcase(dataField[i].fieldId)] = numberUtils.formatCurrency(bmlOccpDt[camelcaseUtils.underbarToCamelcase(dataField[i].fieldId)]);
						}
					}
					
					that.manCde = bmlOccpDt.manCde
					
					$('#sub_pbpKnd', that.selector).html(bmlOccpDt.pbpKndnm);
					$('#occp_pbpKnd', that.selector).html(bmlOccpDt.pbpKndnm);
					$('#occp_empBjd', that.selector).html(bmlOccpDt.empBjd);
					$('#occp_empNam', that.selector).html(bmlOccpDt.empNam);
					$('#occp_empNum', that.selector).html(bmlOccpDt.empNum);
					$('#occp_empRn', that.selector).html(bmlOccpDt.empRn);
					$('#occp_empRnpt', that.selector).html(bmlOccpDt.empRnpt);
					$('#occp_empSnum', that.selector).html(bmlOccpDt.empSnum);
					$('#occp_empSe', that.selector).html(bmlOccpDt.usrSeNm);
					$('#occp_ocpAdr', that.selector).html(bmlOccpDt.ocpAdr);
					$('#occp_ocpAra', that.selector).html(bmlOccpDt.ocpAra);
					$('#occp_ocpEnd', that.selector).html(bmlOccpDt.ocpEnd);
					$('#occp_ocpIdn', that.selector).html(bmlOccpDt.ocpIdn);
					$('#occp_ocpPrs', that.selector).html(bmlOccpDt.ocpPrs);
					$('#occp_ocpStr', that.selector).html(bmlOccpDt.ocpStr);
					$('#sub_prtIdn', that.selector).html(bmlOccpDt.prtIdn);
					$('#occp_rmkExp', that.selector).html(bmlOccpDt.rmkExp);
					$('#occp_rvrGu', that.selector).html(bmlOccpDt.rvrGu);
					$('#occp_rvrSi', that.selector).html(bmlOccpDt.rvrSi);
					$('#occp_socpAra', that.selector).html(bmlOccpDt.socpAra);
					
					var id = tabObj.getId();
					
					if(!that.data[id]){
						that.data[id] = {
							rwat : null	
						};
					}
				},error : function(err){
					console.error(err);
				}
			});
		},
		
		loadDetail : function(dataId){
			var that = this;
			
			var id = tabObj.getId();
			var gridSelector = null;
			var data = null;
			
			if(dataId == 'BML_OOUS_AS'){
				data = that.data[id]['oous'];
				gridSelector = '.grid_result_geom';
			}
			
			var firstIndex = pagingUtils.getFirstIndex(data.pageNumber, data.pageSize);
			var lastIndex = pagingUtils.getLastIndex(data.pageNumber, data.pageSize);
			var ids = data.ids.slice(firstIndex, lastIndex);
			
			var filter = {
					filterType : 'FIDS',
					fids : ids
			};
			
			var rows = cmmnPropObj.syncAjaxListAll(filter, dataId);
			var gridData = {
					total : 0,
					rows : null
			};
			
			if(rows && rows.length > 0){
				gridData.total = data.ids.length;
				gridData.rows = rows;
			}else{
				gridData.rows = [];
			}
			
			$(gridSelector, that.selector).datagrid('loadData', gridData);
		},
		
		insertEvent : function() {
			var that = this;
			
			$('.btn_data', that.selector).show();
			$('.btn_data_empty', that.selector).hide();
		},
		
		insertData : function() {
			var that = this;
			var prtIdn = that.prtIdn;
			
			if(prtIdn){
				cmmnPropObj.oousToolObj.open(prtIdn);
			}else{
				$.messager.alert(dataAlias, '검색 결과가 없습니다.');
			}
		},
		
		exitEvent : function (){
			var that = this;
			
			$('.btn_data', that.selector).hide();
			$('.btn_data_empty', that.selector).show();
		}
	};

var cmmnAcexObj = {
		
	TITLE : '실태조사 검색',
	
	dataId : 'V_BML_ACEX_AS',
	
	selector : null,
	
	open : function(){
		var that = this;
		
		if(that.selector){
			that.close();
		}
		
		var winOpt = {
				width : 1000,
				height : 218,
				top : 120,
				left : 330,
				onClose : function(){
					that.close();
				}
		};
		
		var url = CONTEXT_PATH + '/cmmnprop/searchAcexPage.do';
		
		var id = windowObj.createWindow('bmlAcexDtSearch', that.TITLE, url, null, winOpt, function(){
			that.initUi();
			that.init();
			that.bindEvents();
		});
		
		that.selector = '#' + id;
		
	},
	
	init : function(){
		var that = this;
		
		var dongCode = dongObj.getData();
		
		dongCode.unshift({
			bjdNam : '',
			bjdCde : ''
		});
		
		$('#comboBx_emd', that.selector).combobox('loadData', dongCode);
		
		$('#comboBx_cmmn', that.selector).combobox('select','');
		
		var dateList = that.getDateList();
		dateList.unshift({
			text : '',
			value : ''
		});
		
		$('#comboBx_srchY', that.selector).combobox('loadData', dateList);
		
		var dept = cmmnPropObj.getDept();
		if(dept.code && dept.code != '4200245'){
			var deptName = dept.name.split(' ');
			
			$('#text_manNm', that.selector).textbox('setValue', deptName[deptName.length - 1]);
		}
	},
	
	initUi : function(){
		var that = this;
		
		$('#comboBx_emd', that.selector).combobox({
			valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD)
		});
		
		$('#mntn', that.selector).switchbutton();
		
		$('.numSpn', that.selector).numberspinner({
			min : 0,
			max : 999999
		});
		
		$('#comboBx_cmmn', that.selector).combobox({
			width : 362,
			valueField : 'value',
			textField: 'text',
			data : [{
				text : '',
				value : ''
			},{
				text : '토지',
				value : '01'
			},{
				text : '건물',
				value : '02'
			}]
		});
		
		$('#comboBx_srchY', that.selector).combobox({
			width : 362
		});
		
		$('.textarea_acex', that.selector).textbox({
			width : 362
		});
	},
	
	bindEvents : function(){
		var that = this;
		
		$('#btn_reset', that.selector).click(function(){
			that.clean();
		});
		
		$('#btn_search', that.selector).click(function(){
			that.search();
		});
	},
	
	getDateList : function(){
		var today = new Date();
		var todayYear = today.getFullYear();
		
		var dateList = [];
		
		for(var i = todayYear; i >= 1950; i--){
			dateList.push({
				text : i,
				value : i
			});
		}
		
		return dateList;
	},
	
	search : function(){
		var that = this;
		
		var param = {};
		
		param.emd = $('#comboBx_emd', that.selector).combobox('getValue');
		param.mntn = $('#mntn', that.selector).switchbutton('options').checked?'2':'1';
		param.mnnm = $('#mnnm', that.selector).numberspinner('getValue');
		param.slno = $('#slno', that.selector).numberspinner('getValue');
		param.srchY = $('#comboBx_srchY', that.selector).combobox('getValue');
		param.manNm = $('#text_manNm', that.selector).textbox('getValue');
		param.mndNm = $('#text_mndNm', that.selector).textbox('getValue');
		param.cmmn = $('#comboBx_cmmn', that.selector).combobox('getValue');
		
		var cql = that.createSqlWhereConstruction(param);
		
		var filter = {
			filterType : 'CQL',
			cql : cql
		};
		
		that.close();
		resultObj.close();
		cmmnPropObj.close();
		cmmnLoanObj.close();
		cmmnOccpObj.close();
		
		that.searchRes(filter, that.dataId);
	},
	
	searchRes : function(filter, dataId){
		var that = this;
		
		var row = cmmnPropObj.syncAjaxSummaries(filter, dataId);
		
		var kwsData = dataObj.getDataByDataId(that.dataId, false);
		var dataAlias = kwsData.dataAlias;
		
		
		if(row){
			that.close();
			resultObj.close();
			cmmnPropObj.close();
			cmmnLoanObj.close();
			cmmnOccpObj.close();
			$('.a_menu_collapse').trigger('click');
			
			var option = {
				dataId : that.dataId,
				eventObj : cmmnPropObj.resultObj.bindEvents
			};
			
			resultObj.addRows(row, option);
			
		}else{
			$.messager.alert(dataAlias, '검색 결과가 없습니다.');
		}
		
		
	},
	
	createSqlWhereConstruction : function(param){
		var cql  = '';
		
		/*if(
			(param.emd == null || param.emd == '') &&
			(param.mnnm == null || param.mnnm == '') &&
			(param.slno == null || param.slno == '') &&
			(param.mntn == '1') &&
			(param.srchY == null || param.srchY == '') &&
			(param.manNm == null || param.manNm == '') &&
			(param.mndNm == null || param.mndNm == '') &&
			(param.cmmn == null || param.cmmn == '')
		){
			cql = " 1 = 1 ";
		}else{*/
			if(param.emd != null && param.emd != ''){
				cql += " AND BJD_CDE = '" + param.emd + "' ";
			}
			if(param.mntn != null && param.mntn != ''){
				if((param.emd != '' && param.emd != null && param.mnnm != '' && param.mnnm != null) || param.mntn == '2'){
					cql += " AND MONUT = '" + param.mntn + "' ";
				}
			}
			if(param.mnnm != null && param.mnnm != ''){
				cql += " AND BUN = '" + param.mnnm + "' ";
			}
			
			if(param.slno != null && param.slno != ''){
				cql += " AND HO = '" + param.slno + "' ";
			}
			
			if(param.srchY != null && param.srchY != ''){
				cql += " AND SRCH_YMD LIKE '" + param.srchY + "%' ";
			}
			
			if(param.manNm != null && param.manNm != ''){
				cql += " AND MAN_NM LIKE '%" + param.manNm + "%' ";
			}
			
			if(param.mndNm != null && param.mndNm != ''){
				cql += " AND MND_NM LIKE '%" + param.mndNm + "%' ";
			}
			
			if(param.cmmn != null && param.cmmn != ''){
				cql += " AND PBP_KND = '" + param.cmmn + "' ";
			}
			
			cql = cql.replace(' AND', '');
		//}
		
		return cql;
	},
	
	clean : function(){
		var that = this;
		
		$('#comboBx_emd', that.selector).combobox('setValue', '');
		$('#mntn', that.selector).switchbutton('clear');
		$('#mnnm', that.selector).numberspinner('clear');
		$('#slno', that.selector).numberspinner('clear');
		$('#comboBx_cmmn', that.selector).combobox('setValue', '');
		$('#comboBx_srchY', that.selector).combobox('setValue', '');
		$('#text_manNm', that.selector).textbox('clear');
		$('#text_mndNm', that.selector).textbox('clear');
	},
	
	close : function(){
		var that = this;
		
		that.clean();
		windowObj.removeWindow(that.selector);
	}
};

cmmnAcexObj.view = {
	dataId : null,
	
	selector : null,
	
	prtIdn : null,
	
	data : {},
	
	init : function(prtIdn, selector, pbpKnd, dataId){
		var that = this;
		
		that.prtIdn = prtIdn;
		that.selector = selector;
		that.dataId = dataId;
		
		
		$('.cmmn_tabs', that.selector).tabs();
		that.loadData();
		that.bindEvents();
		
	},
	
	bindEvents : function(){
		var that = this;
		
		$('#btn_loan_selectOne', that.selector).click(function(){
			that.setSelector();
			
			var row = $('.grid_result_loan', that.selector).datagrid('getSelected');
			if(!row){
				$.messager.alert('무단점유 사용자', '행을 선택해주세요.');
			}else{
				that.openAtchTab(row, COMMON.LOAN);
			}
		});
		
		$('#btn_occp_selectOne', that.selector).click(function(){
			that.setSelector();
			
			var row = $('.grid_result_occp', that.selector).datagrid('getSelected');
			
			if(!row){
				$.messager.alert('대부 및 사용허가', '행을 선택해주세요.');
			}else{
				that.openAtchTab(row, COMMON.OCCP);
			}
		});
	},
	
	openAtchTab : function(row, dataId){
		var callback = null;
		
		var kwsData = dataObj.getDataByDataId(dataId, false);
		var url = CONTEXT_PATH + '/cmmnprop/' + dataId + '/detailPage.do';
		
		if(dataId == 'BML_LOAN_AS'){
			callback = function(row, selector){
				cmmnLoanObj.view.init(row.lonIdn, dataId, selector, row.pbpKnd, row.prtIdn);
			};
		}else if(dataId == 'BML_OCCP_AS'){
			callback = function(row, selector){
				cmmnOccpObj.view.init(row.ocpIdn, dataId, selector, row.pbpKnd, row.prtIdn);
			};
		}
		
		tabObj.createTab(dataId, kwsData.dataAlias, row, url, callback);
		
	},
	
	loadData : function(){
		var that = this;
		
		var url = CONTEXT_PATH + '/cmmnprop/' + that.prtIdn + '/searchAcexData.do';
		
		$.ajax({
			url : url,
			dataType : 'json',
			type : 'GET',
			success : function(rs){
				if(rs && rs.vBmlAcexAs){
					var vBmlAcexAs = rs.vBmlAcexAs;
					
					var kwsData = dataObj.getDataByDataId(that.dataId, true);
					var kwsDataFields = kwsData.kwsDataFields;
					
					var prtIdn = null;
					var pbpKnd = null;
					
					for(var i = 0; i < kwsDataFields.length; i++){
						if(kwsDataFields[i].indictTy.indexOf('CURRENCY') > -1){
							var camelFieldId = camelcaseUtils.underbarToCamelcase(kwsDataFields[i].fieldId);
							vBmlAcexAs[camelFieldId] = numberUtils.formatCurrency(vBmlAcexAs[camelFieldId]);
						}
					}
					
					for(var i = 0; i < Object.keys(vBmlAcexAs).length; i++){
						var oKey = Object.keys(vBmlAcexAs);
						if(oKey[i] == 'pstNum' && vBmlAcexAs['pstNum']){
							vBmlAcexAs['pstNum'] = ' / ' + vBmlAcexAs['pstNum'];
						}
						$('#acex_' + oKey[i], that.selector).html(vBmlAcexAs[oKey[i]]);
						
						if(oKey[i] == 'prtIdn'){
							prtIdn = vBmlAcexAs['prtIdn'];
						}else if(oKey[i] == 'pbpKnd'){
							pbpKnd = vBmlAcexAs['pbpKnd'];
						}
					}
					
					var id = tabObj.getId();
					that.data[id] = {
							loan : null,
							occp : null
					};
					
					that.loanResultLoading(prtIdn, pbpKnd);
					that.occpResultLoading(prtIdn, pbpKnd);
					
				}
			},error : function(err){
				console.error(err);
			}
		});
	},
	
	occpResultLoading : function(prtIdn, pbpKnd){
		var that = this;
		
		var dataId = COMMON.OCCP;
		var id = tabObj.getId();
		
		var pkCol = null;
		var cols = [];
		var frozenCols = [];
		
		var kwsData = dataObj.getDataByDataId(dataId, true);
		var kdf = kwsData.kwsDataFields;
		
		for(var i = 0; i < kdf.length; i++){
			if(kdf[i].pkAt.indexOf('Y') > -1){
				pkCol = camelcaseUtils.underbarToCamelcase(kdf[i].fieldId);
				
				frozenCols.push({
					field : pkCol,
					title : '번호',
					width : 100,
					sortable : true
				});
			}else if(kdf[i].indictTy != 'HIDE' && kdf[i].indictTy != 'WKT'){
				cols.push({
					field : camelcaseUtils.underbarToCamelcase(kdf[i].fieldId),
					title : kdf[i].fieldAlias,
					width : 100,
					sortable : false
				});
			}
		}
		
		$('.grid_result_occp', that.selector).datagrid({
			pk : pkCol,
			frozenColumns : [frozenCols],
			columns : [cols],
			singleSelect : true,
			showFooter : false,
			pagination : true
		});
		
		var filter = {
			filterType : 'CQL',
			cql : "PRT_IDN = " + prtIdn + " AND PBP_KND = '" + pbpKnd + "' "
		};
		
		var rows = cmmnPropObj.syncAjaxSummaries(filter, dataId);
		
		if(rows && rows.length > 0){
			that.data[id]['occp'] = {
				pageNumber : 1,
				pageSize : 10,
				ids : rows[0].ids
			};
			
			that.loadDetail(dataId);
		}
		
		var pagination = $('.grid_result_occp', that.selector).datagrid('getPager');
		pagination.pagination({
			displayMsg : '{from} - {to} / {total}',
			onSelectPage : function(pageNumber, pageSize){
				that.data[id]['occp'].pageNumber = pageNumber;
				that.data[id]['occp'].pageSize = pageSize;
				
				that.loadDetail(dataId);
			}
		});
	},
	
	loanResultLoading : function(prtIdn, pbpKnd){
		var that = this;
		
		var dataId = COMMON.LOAN;
		
		var kwsData = dataObj.getDataByDataId(dataId, true);
		var kdf = kwsData.kwsDataFields;
		
		var pkCol = null;
		var cols = [];
		var frozenCol = [];
		
		for(var i = 0; i < kdf.length; i++){
			if(kdf[i].pkAt.indexOf('Y') > -1){
				pkCol = camelcaseUtils.underbarToCamelcase(kdf[i].fieldId);
				
				frozenCol.push({
					field : pkCol,
					title : '번호',
					width : 100,
					sortable : true
				});
			}else if(kdf[i].indictTy != 'HIDE' && kdf[i].indictTy != 'WKT'){
				cols.push({
					field : camelcaseUtils.underbarToCamelcase(kdf[i].fieldId),
					title : kdf[i].fieldAlias,
					width : 100,
					sortable : false
				});
			}
		}
		
		$('.grid_result_loan', that.selector).datagrid({
			pk : pkCol,
			frozenColumns : [frozenCol],
			columns : [cols],
			singleSelect : true,
			showFooter : false,
			pagination : true
		});
		
		var filter = {
			filterType : 'CQL',
			cql : "PRT_IDN = " + prtIdn + " AND PBP_KND = '" + pbpKnd + "'"
		};
		
		var rows = cmmnPropObj.syncAjaxSummaries(filter, dataId);
		
		var id = tabObj.getId();
		
		if(rows && rows.length > 0){
			that.data[id]['loan'] = {
					pageNumber : 1,
					pageSize : 10,
					ids : rows[0].ids
			};
			
			that.loadDetail(dataId);
		}
		
		var pagination = $('.grid_result_loan', that.selector).datagrid('getPager');
		pagination.pagination({
			displayMsg : '{from} - {to} / {total}',
			onSelectPage : function(pageNumber, pageSize){
				that.data[id]['loan'].pageNumber = pageNumber;
				that.data[id]['loan'].pageSize = pageSize;
				that.loadDetail(dataId);
			}
		});
		
	},
	
	loadDetail : function(dataId){
		var that = this;
		
		var id = tabObj.getId();
		var gridSelector = null;
		var data = null;
		
		if(dataId == 'BML_LOAN_AS'){
			data = that.data[id]['loan'];
			gridSelector = '.grid_result_loan';
		}else if(dataId == 'BML_OCCP_AS'){
			data = that.data[id]['occp'];
			gridSelector = '.grid_result_occp';
		}else if(dataId == 'BML_LOUS_AS'){
			data = that.data[id]['lous'];
			gridSelector = '.grid_result_geom';
		}
		
		var firstIndex = pagingUtils.getFirstIndex(data.pageNumber, data.pageSize);
		var lastIndex = pagingUtils.getLastIndex(data.pageNumber, data.pageSize);
		var ids = data.ids.slice(firstIndex, lastIndex);
		
		var filter = {
				filterType : 'FIDS',
				fids : ids
		};
		
		var rows = cmmnPropObj.syncAjaxListAll(filter, dataId);
		
		var gridData = {
			total : data.ids.length,
			rows : rows
		};
		
		$(gridSelector, that.selector).datagrid('loadData', gridData);
	}
};

/////////////////////////////////////////////////////////////////////////////////////////////////////////////

var useAreaObj = {

	TITLE : "필지정보",
		
	CLASS_NAME : "UseAreaSearch",
		
	selector : null,
		
	isClosed : false,
	
	dataIds : "BML_BUID_AS,BML_PROP_AS",
	
	dataIds : null,
	
	fids : null,
	
	pnu : null,
	
	retryCount : 0,
	
	open : function(){
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var windowOptions = {
			width :	342,
			height : 426,
			top : 157,
			right : 1,
			draggable : false,
			resizable : false,
			closable : false
		};
		
		var url = CONTEXT_PATH + '/cmmnprop/useAreaPage.do';
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			
			that.useAreaResize();
			that.initUi();
			that.bindEvents();
			that.loadData();
			that.hideLoadingBar();
		});
			
		that.selector = "#" + id;
	},
	
	useAreaResize : function(){
		var that = this;
	
		var selectorHeight = $('#div_map').height() - $('.heder_snb').height() - $('#a_index_open').height() - 42;
		var selectorLeft = $('#div_menu').width() + $('#div_map').width() - $(that.selector).width() - 12;
		
		$(that.selector).window('resize',{
			height : selectorHeight,
			left : selectorLeft
		});
		
		var selectorWidth = $(that.selector).width() + 4;
		
		$('.map_control').css('right',selectorWidth);
		
	},
	
	initUi : function(){
		var that = this;
		
		var selectResultTabsIndex = null;
		
		/*$('#div_useArea_tabs', that.selector).tabs({
			onSelect : function(title, index){
				if(index == 0){
					$('#btn_read', that.selector).hide();
				}else{
					if(selectResultTabsIndex == 0){
						$('#btn_read', that.selector).show();
					}
				}
			}
		});*/
		
		//$('#div_kras_tabs', that.selector).tabs();
		
		$('.div_window_select_result_tabs', that.selector).tabs({
			height : 425,
			onSelect : function(title, index){
		    	if(title.indexOf('재산일반') > -1 && index == 0){
					selectResultTabsIndex = index;
			
		    		//$('#btn_read', that.selector).show();
		    	}/*else{
		    		//$('#btn_read', that.selector).hide();

					var loanTab = $('.div_window_select_result_tabs').tabs('getSelected');
					loanTab.panel('resize').height('325');
					
					var loanTag = $('.btn_search_loan', that.selector);
					if(loanTag.length > 8){
						loanTab.panel('resize').height();
					}
		    	}*/
			}
		});
		
		$('#div_kras_tabs', that.selector).tabs();
		
		$('#div_useArea_tabs', that.selector).tabs({
			onSelect : function(title, index){
				if(index == 0){
					$('#btn_read', that.selector).hide();
				}else{
					if(selectResultTabsIndex == 0){
						$('#btn_read', that.selector).show();
					}
				}
			}
		});
		
		$('#btn_read', that.selector).hide();
		
		mainObj.addResizeHandler(that.CLASS_NAME, function(){
			that.useAreaResize();
		});
	},
	
	showLoadingBar : function() {
		var that = this;
		
		jqueryUtils.setIsLoding(false);
		$(".div_loading", that.selector).show();
		$('.window_article', that.selector).hide();
	},
	
	hideLoadingBar : function() {
		var that = this;
		jqueryUtils.setIsLoding(true);
		$(".div_loading", that.selector).hide();
		$('.window_article', that.selector).show();
	},
	
	searchBass : function() {
		var that = this;
		that.showLoadingBar();
		
		var url = CONTEXT_PATH + "/window/kras/searchBass.do";
		var params = {
			pnu : that.pnu,
			retryCount : that.retryCount
		};
		$.get(url, params).done(function(response) {
			if(response) {
				if(response.landInfo && response.landInfo.header) {
					var header = response.landInfo.header;
					
					if(header.code != "0000") {
						$.messager.alert(that.TITLE, header.message+"(KRAS000002)");
					}else{
						that.loadSearchBass(response.landInfo.body.data);
					}
				}
				else {
					if(that.retryCount == 0) {
						$.messager.confirm(that.TITLE,"연계정보를 불러오는 작업이 지연되고 있습니다.(KRAS000002) 계속 진행하시겠습니까?", function(isTrue) {
							if(isTrue) {
								that.retryCount = 1;
								that.searchBass();
							}else {
								//that.pnu = null;
								that.retryCount = 0;
								that.hideLoadingBar();
							}
						});
					}
					else {
						$.messager.alert(that.TITLE, "서버에 접속량이 많아 통신 상태가 좋지 못합니다.(KRAS000002) 잠시 후 다시 시도해 주십시오.", "error", function() {
							that.retryCount = 0;
							that.hideLoadingBar();
						});
					}
				}
			}
			else {
				$.messager.alert(that.TITLE, "기본 정보를 조회하는데 실패했습니다.(KRAS000002, KRAS000102)");
			}
			
			that.hideLoadingBar();
		}).fail(function() {
			$.messager.alert(that.TITLE, "기본 정보를 조회하는데 실패했습니다.(KRAS000002, KRAS000102)");
		});
		
	},
	
	loadSearchBass : function(data){
		var that = this;
		
		$('#landJimokNm', that.selector).html(data.jimokNm);
		$('#landParea', that.selector).html(numberUtils.formatCurrency(data.parea));
		$('#landMovRsnCdNm', that.selector).html(data.landMovRsnCdNm);
		$('#landMovYmd', that.selector).html(data.landMovYmd);
		$('#landOwnGbnNm', that.selector).html(data.ownGbnNm);
		$('#landOwnNm', that.selector).html(data.ownerNm);
		$('#landOwnerAddr', that.selector).html(data.ownerAddr);
		$('#landOwnRgtChgRsnCdNm', that.selector).html(data.landOwnRgtChgRsnCdNm);
		$('#landShrCnt', that.selector).html(data.shrCnt);
		$('#landPannJiga', that.selector).html(numberUtils.formatCurrency(data.pannJiga) + '원 (' + data.jigaBaseMon + ' 기준)');
	},
	
	loadData : function(pnu, data){
		var that = this;
		
		if(pnu || data){
			that.pnu = pnu;
		
			var dongCode = pnu.substring(0, 10);
			var mntn = pnu.substring(10, 11);
			var mnnm = parseInt(pnu.substring(11, 15));
			var slno = parseInt(pnu.substring(15, 19));
			
			var param = {
					"bjdCde" : dongCode,
					"monut" : mntn,
					"bun" : mnnm
			};
			
			if(slno != '0'){
				param.ho = slno;
			}
			
			$.ajax({
				url : CONTEXT_PATH + '/cmmnprop/useAreaSearch.do',
				type : 'GET',
				data : param,
				dataType : 'json',
				async : false,
				success : function(rs){
					if(rs) {
						that.chkData(rs, function(result){
							//토지
							if(result.bmlPropAsArea && result.bmlPropAsArea.length > 0){
								var bmlPropAsArea = result.bmlPropAsArea[0];
								$('#area_use_locPlc', that.selector).html(bmlPropAsArea.locPlc);
								$('#area_use_prtNam', that.selector).html(bmlPropAsArea.prtNam);
								$('#area_use_pbpKnd', that.selector).html('토지');
								$('#area_use_accCde', that.selector).html(bmlPropAsArea.accNm);
								$('#area_use_manCde', that.selector).html(bmlPropAsArea.manNm);
								$('#area_use_loanYn', that.selector).html(bmlPropAsArea.loanYn);
								$('#area_use_rstYn', that.selector).html(bmlPropAsArea.rstYn);
								$('#area_use_pstNum', that.selector).html(bmlPropAsArea.pstNum);
								$('#area_use_ownCde', that.selector).html(bmlPropAsArea.ownNm);
								$('#area_use_rmkExp', that.selector).html(bmlPropAsArea.rmkExp);
								$('input[name=area_use_prtIdn]').attr('value',bmlPropAsArea.prtIdn);
								$('input[name=area_use_objectid]').attr('value',bmlPropAsArea.objectId);
								$('input[name=area_use_pbpKnd]').attr('value',bmlPropAsArea.pbpKnd);
								$('input[name=area_use_dataId]').attr('value',"BML_PROP_AS");
								
								$('#landLocPlc', that.selector).html(bmlPropAsArea.locPlc);
							}
							
							//건물
							if(result.bmlBuidAsArea && result.bmlBuidAsArea.length > 0){
								var bmlBuidAsArea = result.bmlBuidAsArea[0];
								$('#area_use_locPlc', that.selector).html(bmlBuidAsArea.locPlc);
								$('#area_use_prtNam', that.selector).html(bmlBuidAsArea.prtNam);
								$('#area_use_pbpKnd', that.selector).html('건물');
								$('#area_use_accCde', that.selector).html(bmlBuidAsArea.accNm);
								$('#area_use_manCde', that.selector).html(bmlBuidAsArea.manNm);
								$('#area_use_loanYn', that.selector).html(bmlBuidAsArea.loanYn);
								$('#area_use_rstYn', that.selector).html(bmlBuidAsArea.rstYn);
								$('#area_use_pstNum', that.selector).html(bmlBuidAsArea.pstNum);
								$('#area_use_ownCde', that.selector).html(bmlBuidAsArea.ownNm);
								$('#area_use_rmkExp', that.selector).html(bmlBuidAsArea.rmkExp);
								$('input[name=area_use_prtIdn]').attr('value',bmlBuidAsArea.prtIdn);
								$('input[name=area_use_objectid]').attr('value',bmlBuidAsArea.objectId);
								$('input[name=area_use_pbpKnd]').attr('value',bmlBuidAsArea.pbpKnd);
								$('input[name=area_use_dataId]').attr('value',"BML_BUID_AS");
								
								$('#landLocPlc', that.selector).html(bmlBuidAsArea.locPlc);
							}
							
							//대부현황
							if(result.bmlLoanAsArea){
								var tarLoanStr = '';
								if(result.bmlLoanAsArea.length > 0){
									var bmlLoanAsArea = result.bmlLoanAsArea;
									
									$('.btn_search_loan_empty', that.selector).remove();
									$('.btn_search_loan', that.selector).remove();
									
									for(var i=0; i<bmlLoanAsArea.length; i++){
										tarLoanStr +='<tr class="btn_search_loan" lonIdn="' + bmlLoanAsArea[i].lonIdn +'" prtIdn="' + bmlLoanAsArea[i].prtIdn + '" pbpKnd="' + bmlLoanAsArea[i].pbpKnd + '" objectid="' + bmlLoanAsArea[i].objectid + '" dataId ="BML_LOAN_AS" style="height:20px;">';
										tarLoanStr +='<td style="text-align:center;">' + bmlLoanAsArea[i].pbpKndnm + '</td>';
										tarLoanStr +='<td style="text-align:center;">' + bmlLoanAsArea[i].crtStr + '</td>';
										tarLoanStr +='<td style="text-align:center;">' + bmlLoanAsArea[i].crtEnd + '</td>';
										tarLoanStr +='<td style="text-align:center;">' + bmlLoanAsArea[i].lonArea + '</td>';
										tarLoanStr +='<td style="text-align:center;">' + bmlLoanAsArea[i].lonPup + '</td>';
										tarLoanStr +='</tr>';
										
									}
								}else{
									$('.btn_search_loan_empty', that.selector).remove();
									$('.btn_search_loan', that.selector).remove();
									
									tarLoanStr += '<tr class="btn_search_loan_empty">';
									tarLoanStr += '<td colspan="5" style="text-align:center;">검색 결과가 없습니다.</td>';
									tarLoanStr += '</tr>';
								}
								
								$('#area_use_loan', that.selector).append(tarLoanStr);
							}
							
							/*//실태조사
							if(result.bmlAcexAsArea){
								var tarAcexStr = '';
								if(result.bmlAcexAsArea.length > 0){
									var bmlAcexAsArea = result.bmlAcexAsArea;
									for(var i=0; i<bmlAcexAsArea.length; i++){
										tarAcexStr +='<tr id="btn_search" prtIdn="' + bmlAcexAsArea[i].prtIdn +'" dataId ="V_BML_ACEX_AS">';
										tarAcexStr +='<td style="text-align:center;">' + bmlAcexAsArea[i].locPlc + '</td>';
										tarAcexStr +='<td style="text-align:center;">' + bmlAcexAsArea[i].srchpNm + '</td>';
										tarAcexStr +='<td style="text-align:center;">' + bmlAcexAsArea[i].mgtGbn + '</td>';
										tarAcexStr +='<td style="text-align:center;">' + bmlAcexAsArea[i].mgtYn + '</td>';
										tarAcexStr +='<td style="text-align:center;">' + bmlAcexAsArea[i].unusedStt + '</td>';
										tarAcexStr +='</tr>';
									}
								}else{
									tarAcexStr += '<tr>';
									tarAcexStr += '<td colspan="5" style="text-align:center;">검색 결과가 없습니다.</td>';
									tarAcexStr += '</tr>';
								}
								
								$('#area_use_acex', that.selector).append(tarAcexStr);
							}*/
							
							//용도지구
							if(data){
								var tarDataStr = '';
								if(data.length > 0){
									$('.area_use_search', that.selector).remove();
									$('.area_use_search_empty', that.selector).remove();
									
									for(var i=0; i<data.length; i++){
										var dataAlias = dataObj.getDataByDataId(data[i].dataId, false).dataAlias;
										var useData = data[i].rows;
										for(var l=0; l<useData.length; l++){
											var useAreaData = useData[l];
											
											tarDataStr +='<tr>';
											tarDataStr +='<tr class="area_use_search" geom="' + useAreaData.geom +'">';
											tarDataStr +='<td style="text-align:center;">' + dataAlias + '</td>';
											tarDataStr +='<td style="text-align:center;">' + useAreaData.alias + '</td>';
											//tarDataStr +='<td style="text-align:center;">' + useAreaData.remark + '</td>';
											tarDataStr +='</tr>';
										}
										
									}
								}else{
									$('.area_use_search', that.selector).remove();
									$('.area_use_search_empty', that.selector).remove();
									
									tarDataStr += '<tr>';
									tarDataStr += '<td colspan="3" style="text-align:center;">검색 결과가 없습니다.</td>';
									tarDataStr += '</tr>';
								}
								
								
								$('#area_use_data', that.selector).append(tarDataStr);
							}
						});
						that.searchBass();
					}
					else {
						$.messager.alert(that.TITLE, "공간 정보를 불러오는데 실패 했습니다.");
					}
				},
				error : function(err){
					$.messager.alert(that.TITLE, "공간 정보를 불러오는데 실패 했습니다.");
					console.error(err);
				}
			});
			//용도지구 로딩
		}else{
			var tarLoanStr = '';
			
			tarLoanStr += '<tr class="btn_search_loan_empty">';
			tarLoanStr += '<td colspan="5" style="text-align:center;">검색 결과가 없습니다.</td>';
			tarLoanStr += '</tr>';
			
			$('#area_use_loan', that.selector).append(tarLoanStr);
			
			var tarDataStr = '';
			
			tarDataStr += '<tr class="area_use_search_empty">';
			tarDataStr += '<td colspan="3" style="text-align:center;">검색 결과가 없습니다.</td>';
			tarDataStr += '</tr>';
			
			$('#area_use_data', that.selector).append(tarDataStr);
		}
		
		
	},
	
	chkData : function(rs, callback){
		var prop = rs.bmlPropAsArea;
		var buid = rs.bmlBuidAsArea;
		
		var result = null;
		
		var cnt = 0;
		
		if(buid.length > 0){
			cnt += buid.length;
		}
		
		if(prop.length > 0){
			cnt += prop.length;
		}
		
		if(cnt > 1){
			result = useAreaObj.selectObj.open(rs, callback);
		}else{
			callback(rs);
		}
	},
	
//	createCqlWhereFilter : function(param){
//		
//		var cql = '';
//		
//		if(param.dongCode){
//			cql += "BJD_CDE = '" + param.dongCode + "' ";
//		}
//		
//		if(param.mntn){
//			cql += "AND MONUT = '" + param.mntn + "' ";
//		}
//
//		if(param.mnnm){
//			cql += "AND BUN = '" + param.mnnm + "' ";
//		}
//		
//		if(param.slno != '' && param.slno != null && param.slno != 0){
//			cql += "AND HO = '" + param.slno + "' ";
//		}
//		
//		var filter ={
//				filterType : 'CQL',
//				cql : cql
//			};
//		
//		return filter;
//		
//	},
	
	removeFeatures : function() {
		var that = this;
		highlightObj.removeFeatures(that.CLASS_NAME);
	},
	
	removeLoanFeatures : function(data) {
		highlightObj.removeFeatures(data);
	},

	
	showFeatures : function(geom) {
		var that = this;
		
		that.removeLoanFeatures();

		var format = new ol.format.WKT();
		var features = [];

		if (geom) {
			if (geom instanceof Array) {
				for (var i = 0; i < geom.length; i++ ) {
					var feature = format.readFeature(geom[i]);
					features.push(feature);
				}
			} else {
				var feature = format.readFeature(geom);
				features.push(feature);
			}
		}
		else {
			if (data.length == 1) {
				$.messager.alert(that.TITLE, '공간정보가 없습니다.');
			}
		}
		if (features && features.length > 0) {
			highlightObj.showRedFeatures(that.CLASS_NAME, features, false,{
					projection : ol.proj.get(MAP.PROJECTION)
				}
			);
		}
	},
	
	bindEvents : function(){
		var that = this;
		
		// 상세보기
		/*$("#btn_read", that.selector).on("click", function() {
			var pbpKnd = $('input[name=area_use_pbpKnd]').val();
			var prtIdn = $('input[name=area_use_prtIdn]').val();
			var dataId = $('input[name=area_use_dataId]').val();
			var objectid = $('input[name=area_use_objectid]').val();
			
			var row = {
					"prtIdn" : prtIdn,
					"pbpKnd" : pbpKnd,
					"objectid" : objectid
			};
			
			that.read(row, dataId);
			that.close();
			return false;
		});*/
		
//		$("#btn_search", that.selector).on("click", function() {
//			that.read();
//			return false;
//		});
		
		$(that.selector).on('click', '.area_use_search', function(){
	        var geom = $(this).attr('geom');
//	        highlightObj.removeFeatures('SearchInfoPrpos');
	        that.showFeatures(geom);
	        
		});
		
		$(that.selector).on('click', '.btn_search_loan', function(){
			
			var lonIdn = $(this).attr('lonIdn');
			var prtIdn = $(this).attr('prtIdn');
			var dataId = $(this).attr('dataId');
			var pbpKnd = $(this).attr('pbpKnd');
			var objectid = $(this).attr('objectid');
			
			var row = null;
			var kwsData = dataObj.getDataByDataId(dataId, false);
			var alias = kwsData.dataAlias;
			var callback = null;
			var url = CONTEXT_PATH + '/cmmnprop/' + dataId + '/detailPage.do';

			if (dataId =="BML_LOAN_AS"){
				
				row = {
						"prtIdn" : prtIdn,
						"lonIdn" : lonIdn,
						"pbpKnd" : pbpKnd,
						"objectid" : objectid
				};
				
				callback = function(row, selector){
					var pbpKnd = row.pbpKnd;
					var prtIdn = row.prtIdn;
					if(pbpKnd=="01"){
						cmmnLoanObj.view.init(lonIdn, dataId, selector, pbpKnd, prtIdn);
					}else if(pbpKnd=="02"){
						cmmnLoanObj.view.init(lonIdn, dataId, selector, pbpKnd, prtIdn);
					}else{
						cmmnLoanObj.view.init(lonIdn, dataId, selector, pbpKnd, prtIdn);
					}
					
				};
				
				tabObj.createTab(dataId, alias, row, url, callback);
				
				$('.a_menu_collapse').trigger('click');
				//that.close();
				
			}else if(dataId=="V_BML_ACEX_AS"){
				
				row = {
						"prtIdn" : prtIdn,
						"lonIdn" : lonIdn,
						"pbpKnd" : pbpKnd,
						"objectid" : objectid
				};
				
				callback = function(row, selector){
					cmmnAcexObj.view.init(prtIdn, selector, pbpKnd, dataId);
				};
				
				tabObj.createTab(dataId, alias, row, url, callback);
				
				$('.a_menu_collapse').trigger('click');
				//that.close();
				
			}else{
				$.messager.alert(that.TITLE, "선택이 올바르지 않습니다.");
				return false;
			}
			
//			debugger;
		});
		
		// 닫기
		/*$("#btn_close", that.selector).on("click", function() {
			that.close();
			return false;
		});*/
	},
	
	read : function(row, dataId){
//		var that = this;
		
		$('.a_menu_collapse').trigger('click');
		
		var kwsData = dataObj.getDataByDataId(dataId, false);
		var url = CONTEXT_PATH + '/cmmnprop/' + dataId + '/detailPage.do';
		
		if(dataId.indexOf('BML_PROP_AS') > -1){
			callback = function(row, selector){
				cmmnPropObj.view.init(row.prtIdn, dataId, selector);
			};
		}else if (dataId.indexOf('BML_BUID_AS') > -1){
			callback = function(row, selector){
				cmmnBuidObj.init(row.prtIdn, dataId, selector);
			};
		}
		
		tabObj.createTab(dataId, kwsData.dataAlias, row, url, callback);
		
	},
	
	close : function(){
		var that = this;
		that.removeFeatures();
		highlightObj.removeFeatures('SearchInfoPrpos');
		windowObj.removeWindow(that.selector);
		mainObj.removeResizeHandler(that.CLASS_NAME);
		
		$('.map_control').css('right', '40px');
	}
};

useAreaObj.selectObj = {
	TITLE : '공유재산 선택',
	
	CLASS_NAME : 'PropSelect',
	
	selector : null,
	
	rows : null,
	
	open : function(rs, callback){
		var that = this;
		
		var url = CONTEXT_PATH + '/window/spatialSelect/page.do';
		var winOpts = {
			width : 300,
			height : 173,
			top : 240,
			modal : true,
			onClose : function(){
				that.close();
			}
		};
		
		var result = null;
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, winOpts, function(){
			that.rows = rs;
			
			that.initUi();
			that.bindEvents(callback);
			that.loadData(rs);
		});
		
		that.selector = '#' + id;
		
		return result;
	},
	
	initUi : function(){
		var that = this;
		
		$('.div_south', that.selector).css({
			'height' : '30px',
			'text-align' : 'center',
			'padding' : '3px'
		});
		
		$('.feature_list', that.selector).datagrid({
			columns : [[
			            {field : 'pbpKnd', title : '재산종류', align : 'center', width : 135},
			            {field : 'prtIdn', title : '재산번호', align : 'center', width : 135}
			]],
			singleSelect : true,
			scrollbarsize : 0
		});
	},
	
	loadData : function(rs){
		var that = this;
		
		var prop = rs.bmlPropAsArea;
		var buid = rs.bmlBuidAsArea;
		
		var dataList = [];
		
		if(prop && prop.length > 0){
			for(var i = 0; i < prop.length; i++){
				dataList.push({
					pbpKnd : '토지재산',
					prtIdn : prop[i].prtIdn
				});
			}
		}
		
		if(buid && buid.length > 0){
			for(var i = 0; i < buid.length; i++){
				dataList.push({
					pbpKnd : '건물재산',
					prtIdn : buid[i].prtIdn
				});
			}
		}
		
		$('.feature_list', that.selector).datagrid('loadData', dataList);
	},
	
	bindEvents : function(callback){
		var that = this;
		
		$('.a_completion_choice', that.selector).click(function(){
			that.completionChoice(callback);
		});
	},
	
	completionChoice : function(callback){
		var that = this;
		
		var data = $('.feature_list', that.selector).datagrid('getSelected');
		
		var result = that.rows;
		var rsData = null;
		
		if(data.pbpKnd == '건물재산'){
			rsData = result.bmlBuidAsArea;
			
			for(var i = 0; i < rsData.length; i++){
				if(rsData[i].prtIdn == data.prtIdn){
					that.rows.bmlBuidAsArea = [];
					that.rows.bmlBuidAsArea.push(rsData[i]);
					that.rows.bmlPropAsArea = null;
				}
			}
		}else{
			rsData = result.bmlPropAsArea;
			
			for(var i = 0; i < rsData.length; i++){
				if(rsData[i].prtIdn == data.prtIdn){
					that.rows.bmlPropAsArea = [];
					that.rows.bmlPropAsArea.push(rsData[i]);
					that.rows.bmlBuidAsArea = null;
				}
			}
		}
		
		if(callback){
			callback(that.rows);
		}
		
		that.close();
	},
	
	close : function(){
		var that = this;
		
		windowObj.removeWindow(that.selector);
	}
};

var cmmnAcinObj = {
		
		TITLE : '현장조사 결과 검색',
		
		CLASS_NAME : 'CommonAcinSearch',
		
		selector : null,
		
		isClosed : false,
		
		dataId : 'BML_ACIN_AS',
		
		open : function(){
			var that = this;
			
			var winOpts = {
					width : 700,
					height : 217,
					top : 120,
					left : 330,
					onClose : function(){
						that.close();
					}
			};
			
			var url = CONTEXT_PATH + '/cmmnprop/searchAcinPage.do';
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, winOpts, function(){
				that.initUi();
				that.init();
				that.bindEvents();
			});
			
			that.selector = '#' + id;
		},
		
		init : function(){
			var that = this;
			
			var dongList = dongObj.getData();
			var emdData = [];
			
			for(var i = 0; i < dongList.length; i++){
				emdData.push(dongList[i]);
			}
			
			emdData.unshift({
				bjdCde : '',
				bjdNam : ''
			});
			
			$('#comboBx_emd', that.selector).combobox('loadData', emdData);
		},
		
		initUi : function(){
			var that = this;
			
			$('#mntn', that.selector).switchbutton();
			
			$('.numSpn', that.selector).numberspinner({
				min : 0,
				max : 999999,
				width : 232
			});
			
			$('#comboBx_emd', that.selector).combobox({
				valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
				textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD),
				width : 232
			});
			
			$('#comboBx_meansSe', that.selector).combobox({
				width : 567,
				valueField : 'value',
				textField : 'text',
				data : [{
					value : '',
					text : ''
				},{
					value : '행정재산',
					text : '행정재산'
				},{
					value : '일반재산',
					text : '일반재산'
				}]
			});
		},
		
		bindEvents : function(){
			var that = this;
			
			$('#btn_reset', that.selector).click(function(){
				that.clean();
			});
			
			$('#btn_search', that.selector).click(function(){
				that.search();
			});
		},
		
		search : function(){
			var that = this;
			
			var emd = $('#comboBx_emd', that.selector).combobox('getValue');
			var mntn = $('#mntn', that.selector).switchbutton('options').checked?'2':'1';
			var mnnm = $('#mnnm', that.selector).numberspinner('getValue');
			var slno = $('#slno', that.selector).numberspinner('getValue');
			var meansSe = $('#comboBx_meansSe', that.selector).combobox('getValue');
			
			var pnu = '';
			
			if(emd == '' && mntn == '1' && mnnm == '' && slno == ''){
				pnu = null;
			}else if(mnnm != '' && mnnm != null){
				if(emd == '' || emd == null){
					$.messager.alert(that.TITLE, '본번 입력시 읍면동 입력은 필수입니다.');
					return false;
				}else{
					pnu = pnuObj.createPnuForLike(emd, mntn, mnnm, slno);
				}
			}else if(mnnm == '' || mnnm == null){
				if(slno != ''){
					$.messager.alert(that.TITLE, '부번 입력시 본번 입력은 필수입니다.');
					return false;
				}else{
					if(mntn == '2'){
						pnu = pnuObj.createPnuForLike(emd, mntn, mnnm, slno);
					}else{
						pnu = emd + "%";
					}
				}
			}else{
				pnu = pnuObj.createPnu(emd, mntn, mnnm, slno);
			}
			
			var param = {
				pnu : pnu,
				meansSe : meansSe
			};
			
			var cql = that.createSqlWhere(param);
			
			var filter = {
				filterType : 'CQL',
				cql : cql
			};
			
			resultObj.close();
			
			cmmnPropObj.searchRes(that.dataId, filter);
			
			that.close();
		},
		
		createSqlWhere : function(param){
			var cql = '';
			
			if((param.pnu == '' || param.pnu == null) && (param.meansSe == '' || param.meansSe == null)){
				cql = ' 1 = 1 ';
			}else{
				if(param.pnu != '' && param.pnu != null){
					if(param.pnu.indexOf('%') > -1){
						cql += "AND PNU LIKE '" + param.pnu + "' ";
					}else{
						cql += "AND PNU = '" + param.pnu + "' ";
					}
				}
				if(param.meansSe != '' && param.meansSe != null){
					cql += " AND MEANS_SE LIKE '%" + param.meansSe + "%' ";
				}
				cql = cql.replace('AND', '');
			}
			return cql;
		},
		
		clean : function(){
			var that = this;
			
			$('#comboBx_meansSe', that.selector).combobox('select','');
			$('#comboBx_emd', that.selector).combobox('select','');
			$('.numSpn', that.selector).numberspinner('setValue', '');
			$('#mntn', that.selector).switchbutton('clear');
		},
		
		close : function(){
			var that = this;
			
			that.clean();
			windowObj.removeWindow(that.selector);
		}
};

cmmnAcinObj.view = {
	dataId : null,
	
	selector : null,
	
	prtIdn : null,
	
	ftrCde : null,
	
	init : function(prtIdn, dataId, selector){
		var that = this;
		
		that.dataId = dataId;
		that.selector = selector;
		that.prtIdn = prtIdn;
		
		that.loadData();
		that.bindEvents();
	},
	
	loadData : function(){
		var that = this;
		
		var url = CONTEXT_PATH + '/cmmnprop/' + that.prtIdn + '/searchAcinData.do';
		
		$.ajax({
			url : url,
			dataType : 'json',
			type : 'GET',
			success : function(rs){
				if(rs && rs.bmlAcinAs){
					var bmlAcinAs = rs.bmlAcinAs;
					
					var kwsData = dataObj.getDataByDataId(that.dataId, true);
					var df = kwsData.kwsDataFields;
					
					for(var i = 0; i < df.length; i++){
						if(df[i].indictTy == 'CURRENCY'){
							bmlAcinAs[camelcaseUtils.underbarToCamelcase(df[i].fieldId)] = numberUtils.formatCurrency(bmlAcinAs[camelcaseUtils.underbarToCamelcase(df[i].fieldId)]);
						}
						
						if(df[i].fieldId == 'FTR_CDE'){
							that.ftrCde = df[i].dfltValue;
						}
					}
					
					for(var i = 0; i < Object.keys(bmlAcinAs).length; i++){
						var oKey = Object.keys(bmlAcinAs)[i];
						
						if(oKey == 'locPlc' || oKey == 'bmlCde' || oKey == 'meansSe'){
							$('.acin_' + oKey, that.selector).html(bmlAcinAs[oKey]);
						}else{
							$('#acin_' + oKey, that.selector).html(bmlAcinAs[oKey]);
						}
					}
					
					that.imageLoad();
				}
			},error : function(err){
				console.error(err);
			}
		});
	},
	
	imageLoad : function(){
		var that = this;
		
		var url = CONTEXT_PATH + '/cmmnprop/selectImageList.do';
		var param = {
				prtIdn : that.prtIdn,
				ftrCde : that.ftrCde
		};
		
		$.ajax({
			url : url,
			type : 'GET',
			data : param,
			dataType : 'json',
			success : function(rs){
				if(rs && rs.rows.length > 0){
					
					var tdWid = $('.frstCnvs', that.selector).width();
					$('.acinImage', that.selector).css('width', tdWid);
					
					var acinImages = rs.rows;
					
					for(var i = 0; i < acinImages.length; i++){
						var acinImage = acinImages[i];
						
						switch (acinImage.imageTy) {
						case 'FIRST_CONVERSION':
							$('.frstCnvs', that.selector).attr('image-no', acinImage.imageFileNo);
							$('.frstCnvs img', that.selector).attr('src', CONTEXT_PATH + '/cmmnprop/' + acinImage.imageNo + '/thumbnail.do');
							$('.frstCnvs img', that.selector).css('display', 'inline');
							break;
						case 'SECOND_CONVERSION':
							$('.scndCnvs', that.selector).attr('image-no', acinImage.imageFileNo);
							$('.scndCnvs img', that.selector).attr('src', CONTEXT_PATH + '/cmmnprop/' + acinImage.imageNo + '/thumbnail.do');
							$('.scndCnvs img', that.selector).css('display', 'inline');
							break;
						case 'INTERPRETATION':
							$('.itprt', that.selector).attr('image-no', acinImage.imageFileNo);
							$('.itprt img', that.selector).attr('src', CONTEXT_PATH + '/cmmnprop/' + acinImage.imageNo + '/thumbnail.do');
							$('.itprt img', that.selector).css('display', 'inline');
							break;
						case 'CLOSE_RANGE_VIEW':
							$('.clsRngeView', that.selector).attr('image-no', acinImage.imageFileNo);
							$('.clsRngeView img', that.selector).attr('src', CONTEXT_PATH + '/cmmnprop/' + acinImage.imageNo + '/thumbnail.do');
							$('.clsRngeView img', that.selector).css('display', 'inline');
							break;
						case 'DISTANT_VIEW':
							$('.dstnView', that.selector).attr('image-no', acinImage.imageFileNo);
							$('.dstnView img', that.selector).attr('src', CONTEXT_PATH + '/cmmnprop/' + acinImage.imageNo + '/thumbnail.do');
							$('.dstnView img', that.selector).css('display', 'inline');
							break;
						case 'BASEMAP':
							$('.baseMap', that.selector).attr('image-no', acinImage.imageFileNo);
							$('.baseMap img', that.selector).attr('src', CONTEXT_PATH + '/cmmnprop/' + acinImage.imageNo + '/thumbnail.do');
							$('.baseMap img', that.selector).css('display', 'inline');
							break;
						case 'LAND_REGISTERMAP':
							$('.landRgstr', that.selector).attr('image-no', acinImage.imageFileNo);
							$('.landRgstr img', that.selector).attr('src', CONTEXT_PATH + '/cmmnprop/' + acinImage.imageNo + '/thumbnail.do');
							$('.landRgstr img', that.selector).css('display', 'inline');
							break;
						default:
							break;
						}
					}
				}
			},error : function(err){
				console.error(err);
			}
		});
	},
	
	bindEvents : function(){
		var that = this;
		
		$('#btn_bfPdfDownload', that.selector).click(function(){
			window.open(CONTEXT_PATH + '/cmmnprop/BF/' + that.prtIdn + '/pdfDownload.do');
		});
		
		$('#btn_asPdfDownload', that.selector).click(function(){
			window.open(CONTEXT_PATH + '/cmmnprop/AS/' + that.prtIdn + '/pdfDownload.do');
		});
		
		$('.acinImagePreview', that.selector).click(function(){
			var imageNo = $(this).parent().attr('image-no');
			window.open(CONTEXT_PATH + '/cmmnprop/' + imageNo + '/preview.do');
		});
	}
};