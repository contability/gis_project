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
				value:'BML_PROP_AS,BML_BUID_AS',
				label:'전체'
			},{
				value:'BML_PROP_AS',
				label:'토지'
			},{
				value:'BML_BUID_AS',
				label:'건물'
			}],
			width : 362
		});
		
		$('#comboBx_bmlCde', that.selector).combobox({
			valueField : 'codeId',
			textField : 'codeNm',
			width : 362
		});
		
		$('.txt_temp', that.selector).textbox({
			width : 362
		});
		
		$('.swtchBtn', that.selector).switchbutton();
		
		$('.numSpn', that.selector).numberspinner({
			min : 0,
			max: 999999
		});
		
		var deptCode = that.getDeptCode();
		if(deptCode.indexOf('4210209') < 0){
			$('#comboBx_manCde', that.selector).textbox('disable');
		}
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
			bjdNam : '전체'
		});
		
		$('#comboBx_emd', that.selector).combobox('loadData', emdData);
		
		var jimokList = domnCodeObj.getData('KWS-0009');
		var jimokData = [];
		
		for(var i = 0; i < jimokList.length; i++){
			jimokData.push(jimokList[i]);
		}
		
		jimokData.unshift({
			codeId : '',
			codeNm : '전체'
		});
		
		$('#comboBx_bmlCde', that.selector).combobox('loadData', jimokData);
		
		$('#comboBx_cmmn', that.selector).combobox('select','BML_PROP_AS,BML_BUID_AS');
		
		
	},
	
	getDeptCode : function(){
		var rs = null;
		
		$.ajax({
			url : CONTEXT_PATH + '/cmmnprop/getDeptCode.do',
			dataType : 'json',
			type : 'GET',
			async : false,
			success : function(row){
				if(row && row.deptCode){
					rs = row.deptCode;
				}
			},error : function(err){
				console.error(err);
			}
		});
		
		return rs;
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
	
	
	
	search : function(){
		var that = this;
		
		var paramData = {};
		
		paramData.emd = $('#comboBx_emd', that.selector).combobox('getValue');
		paramData.mntn = $('#mntn', that.selector).switchbutton('options').checked? '2' : '1' ;
		paramData.mnnm = $('#mnnm', that.selector).numberspinner('getValue');
		paramData.slno = $('#slno', that.selector).numberspinner('getValue');
		paramData.bmlCde = $('#comboBx_bmlCde', that.selector).combobox('getValue');
		paramData.accCde = $('#comboBx_accCde', that.selector).textbox('getValue');
		paramData.manCde = $('#comboBx_manCde', that.selector).textbox('getValue');
		
		var deptCode = that.getDeptCode();
		paramData.deptCode = deptCode;
		
		var dataIds = $('#comboBx_cmmn', that.selector).combobox('getValue');
		
		var filter = {};
		
		resultObj.close();
		
		if(dataIds.indexOf(',') > -1){
			dataIds = dataIds.split(',');
		}
		
		if(Array.isArray(dataIds)){
			if(dataIds.length > 0){
				for(var i = 0; i < dataIds.length; i++){
					filter = that.createCqlFilter(paramData, dataIds[i]);
					that.searchRes(dataIds[i], filter);
				}
			}
		}else{
			filter = that.createCqlFilter(paramData, dataIds);
			that.searchRes(dataIds, filter);
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
	
	searchRes : function(dataId, filter, index){
		var that = this;
		
		var rows = that.syncAjaxSummaries(filter, dataId);
		
		var kwsData = dataObj.getDataByDataId(dataId, false);
		
		if(rows){
			that.close();
			cmmnLoanObj.close();
			cmmnOccpObj.close();
			cmmnAcinObj.close();
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
		if((param.emd == '' || param.emd == null) && (param.mntn == '' || param.mntn == null) && (param.mnnm == '' || param.mnnm == null) && (param.slno == '' || param.slno == null) &&  (param.bmlCde == '' || param.bmlCde == null) && (param.accCde == '' || param.accCde == null) && (param.manCde == '' || param.manCde == null) && (param.deptCode.indexOf('4210209') > -1)){
			return '1 = 1';
		}else{
			var cql = '';
			
			if(param.emd != '' && param.emd != null){
				cql += " AND BJD_CDE = '" + param.emd + "' ";
			}
			
			if(param.mntn != '' && param.mntn != null){
				cql += " AND MONUT = '" + param.mntn + "' ";
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
				cql += " AND ACC_NM LIKE '%" + param.accCde + "%' ";
			}
			
			if(param.manCde != '' && param.manCde != null){
				cql += " AND MAN_NM LIKE '%" + param.manCde + "%' ";
			}
			
			if(param.deptCode.indexOf('4210209') < 0){
				cql += " AND MAN_CDE = '" + param.deptCode + "0000' ";
			}
			
			cql += " AND GEOM IS NOT NULL "; 
			
			cql = cql.replace(' AND ', '');
			
			var filter = {
					filterType : 'CQL',
					cql : cql
			};
		
			return filter;
		}
		
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
		$('#comboBx_cmmn', that.selector).combobox('select','BML_PROP_AS,BML_BUID_AS');
		$('.txt_temp', that.selector).textbox('setValue','');
		$('.swtchBtn', that.selector).switchbutton('clear');
		$('.numSpn', that.selector).numberspinner('setValue','');
	},
	
	close : function() {
		var that = this;
		
		that.clean();
		windowObj.removeWindow(that.selector);
	}	
	
};

unKnownCmmnPropObj = {
		TITLE : "주소 불일치 재산 검색",
		
		CLASS_NAME : "unKnownCommonPropertySearch",
		
		search : function(){
			var that = this;
			
			var deptCode = cmmnPropObj.getDeptCode();
			var dataIds = ['BML_PROP_AS', 'BML_BUID_AS', 'BML_LOAN_AS', 'BML_OCCP_AS'];
			var filter = {
					filterType : 'CQL'
			};
			
			resultObj.close();
			
			/*if(dataIds.indexOf(',') > -1){
				dataIds = dataIds.split(',');
			}*/
			
			
			
			var cql = "";
			
			if(deptCode.indexOf('4210209') < 0){
				cql += " MAN_CDE = '" + deptCode + "0000' ";
			}else{
				cql = '1 = 1';
			}
			
			cql += " AND GEOM IS NULL ";
			
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
		}else{
			row = $('.grid_result_occp', that.selector).datagrid('getSelected');
			callback = function(row, selector){
				cmmnOccpObj.view.init(row.ocpIdn, dataId, selector, row.pbpKnd, row.prtIdn);
			};
		}
		
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
					//$('#prop_pstNum', that.selector).html(row.pstNum);
					//$('#prop_hjdCde', that.selector).html(row.hjdCde);
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
					//$('#prop_strDate', that.selector).html(row.strDate);
					//$('#prop_rewYn', that.selector).html(row.rewYn);
					$('#prop_rstYn', that.selector).html(row.rstYn);
					$('#prop_planBns', that.selector).html(row.planBns);
					if(row.pstNum){
						$('#prop_pstNum', that.selector).html('(등기번호 : ' + row.pstNum + ')');
					}
					
					var id = tabObj.getId();
					
					if(!that.data[id]){
						that.data[id] = {
								loan : null,
								occp : null
						};
					}
					
					that.loanResultLoading(row.prtIdn, row.pbpKnd);
					that.occpResultLoading(row.prtIdn, row.pbpKnd);
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
		}else{
			data = that.data[id]['occp'];
			gridSelector = '.grid_result_occp';
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
//			else{
//				row = $('.grid_result_occp', that.selector).datagrid('getSelected');
//				callback = function(row, selector){
//					cmmnOccpObj.view.init(row.ocpIdn, dataId, selector, row.pbpKnd, row.prtIdn);
//				};
//			}
			
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
						//$('#buid_pbpKnd', that.selector).html(row.pbpKndnm);
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
						//$('#buid_hjdCde', that.selector).html(row.hjdCde);
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
						//$('#buid_compMat', that.selector).html(row.compMat);
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
									occp : null
							};
						}
						
						that.loanResultLoading(row.prtIdn, row.pbpKnd);
//						that.occpResultLoading(row.prtIdn, row.pbpKnd);
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
//			else{
//				data = that.data[id]['occp'];
//				gridSelector = '.grid_result_occp';
//			}
			
			
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
//				}else if(kdf[i].indictTy.indexOf('HIDE') < 0){
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
				bjdNam : '전체'
			});
			
			$('#comboBx_emd', that.selector).combobox('loadData', emdData);
			
		},
		
		initUi : function(){
			var that = this;
			
			$('.textbx', that.selector).textbox({
				width : 362
			});
			
			
			$('#dateBx_crtDate', that.selector).datebox({
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
					value:'',
					label:'전체'
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
			
			var deptCode = cmmnPropObj.getDeptCode();
			if(deptCode.indexOf('4210209') < 0){
				$('#textBx_manNm', that.selector).textbox('disable');
			}
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
			var prtIdn = $('#numSpn_prtIdn', that.selector).numberspinner('getValue');
			var pbpKnd = $('#comBx_pbpKnd', that.selector).combobox('getValue');
			var mesrvNm = $('#textBx_mesrvNm', that.selector).textbox('getValue');
			var manNm = $('#textBx_manNm', that.selector).textbox('getValue');
			var empNam = $('#textBx_empNam', that.selector).textbox('getValue');
			var crtDate = $('#dateBx_crtDate', that.selector).datebox('getValue');
			
			var deptCode = cmmnPropObj.getDeptCode();
			
			var param = { 
					emd : emd,
					mntn : mntn,
					bun : bun,
					ho : ho,
					prtIdn : prtIdn,
					pbpKnd : pbpKnd,
					mesrvNm : mesrvNm,
					manNm : manNm,
					empNam : empNam,
					crtDate : crtDate,
					deptCode : deptCode
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
			$('#numSpn_prtIdn', that.selector).numberspinner('setValue','');
			$('#comBx_pbpKnd', that.selector).combobox('select', '');
			$('#textBx_mesrvNm', that.selector).textbox('clear');
			$('#textBx_manNm', that.selector).textbox('clear');
			$('#textBx_empNam', that.selector).textbox('clear');
			$('#dateBx_crtDate', that.selector).datebox('clear');
		},
		
		close : function() {
			var that = this;
			
			that.clean();
			windowObj.removeWindow(that.selector);
		},
		
		createSqlWhereConstruction : function(param){
			if(
					(param.emd == '' || param.emd == null) && 
					(param.mntn == '' || param.mntn == null) &&
					(param.bun == '' || param.bun == null) && 
					(param.ho == '' || param.ho == null) && 
					(param.prtIdn == '' || param.prtIdn == null) && 
					(param.pbpKnd == '' || param.pbpKnd == null) &&
					(param.mesrvNm == '' || param.mesrvNm == null) && 
					(param.manNm == '' || param.manNm == null) && 
					(param.empNam == '' || param.empNam == null) && 
					(param.crtDate == '' || param.crtDate == null) &&
					(param.deptCode.indexOf('4210209') > -1)
						
			){
				return '1 = 1';
			}else{
				var cqlData = "";
				
				if(param.emd != '' && param.emd != null){
					cqlData+=" AND BJD_CDE = '" + param.emd +"'";
				}
				if(param.mntn != '' && param.mntn != null){
					cqlData+=" AND MONUT = '" + param.mntn +"'";
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
					cqlData+=" AND MESRV_NM LIKE '%" + param.mesrvNm +"%'";
				}
				if(param.manNm != '' && param.manNm != null){
					cqlData+=" AND MAN_NM LIKE '%" + param.manNm +"%'";
				}
				if(param.empNam != '' && param.empNam != null){
					cqlData+=" AND EMP_NAM LIKE '%" + param.empNam +"%'";
				}
				if(param.crtDate != '' && param.crtDate != null){
					cqlData+=" AND CRT_DATE = '" + param.crtDate +"'";
				}
				if(param.deptCode.indexOf('4210209') < 0){
					cqlData += " AND MAN_CDE = '" + param.deptCode + "0000'";
				}else{
					if(param.manNm != '' && param.manNm != null){
						cqlData += " AND MAN_NM LIKE '%" + param.manNm + "%'";
					}
				}
				
				var cql = cqlData.replace(' AND ', '');
				
				return cql;
				
			}
		},
};

cmmnLoanObj.view = {
		
	dataId : null,
	
	selector : null,
	
	data : {},
	
	init : function(lonIdn, dataId, selector, param, prtIdn){
		var that = this;
		
		that.dataId = dataId;
		that.selector = selector;
		
		that.dataLoading(lonIdn, dataId, selector, param, prtIdn);
		
		that.initUi();
		
	},
	
	initUi : function(){
		var that = this;
		
		$('.cmmn_tabs', that.selector).tabs();
	},
	
	setSelector : function(){
		var that = this;
		
		that.selector = '#' + tabObj.getId();
	},
	
	/*loadDetail : function(dataId){
		var that = this;
		var id = tabObj.getId();
		
		that.setSelector();
		
		var data = that.data[id]['comp'];
		var gridSelector = '.grid_result_comp';
		
		
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
	},*/
	
	dataLoading : function(lonIdn, dataId, selector, param, prtIdn){
		var that = this;
		var url = "";
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
				
				var id = tabObj.getId();
				
				if(!that.data[id]){
					that.data[id] = {
							comp : null
					};
				}
				
				//that.compResultLoading(bmlLoanAs.prtIdn, bmlLoanAs.lonIdn);
				
			},error : function(err){
				console.error(err);
			}
		});
	}/*,
	
	compResultLoading : function(prtIdn, lonIdn){
		var that = this;
		
		var dataId = 'BML_COMP_DT';
		var id = tabObj.getId();
		
		var gridSelector = '.grid_result_comp';
		
		var kwsData = dataObj.getDataByDataId(dataId, true);
		var kdf = kwsData.kwsDataFields;
		
		var frozenCol = [];
		var cols = [];
		var pkCol = '';
		
		for(var i = 0; i < kdf.length; i++){
			if(kdf[i].pkAt.indexOf('Y') > -1){
				pkCol = camelcaseUtils.underbarToCamelcase(kdf[i].fieldId);
				
				frozenCol.push({
					field : pkCol,
					title : '번호',
					width : 100,
					sortable : true
				});
			}else if(kdf[i].indictTy.indexOf('HIDE') < 0){
				cols.push({
					field : camelcaseUtils.underbarToCamelcase(kdf[i].fieldId),
					title : kdf[i].fieldAlias,
					width : 100,
					sortable : false
				});
			}
		}
		
		$(gridSelector, that.selector).datagrid({
			pk : pkCol,
			frozenColumns : [frozenCol],
			columns : [cols],
			singleSelect : true,
			showFooter : false,
			pagination : true
		});
		
		var cql = ' PRT_IDN =  ' + prtIdn + ' AND LON_IDN = ' + lonIdn;
		
		var filter = {
			filterType : 'CQL',
			cql : cql
		};
		
		var rows = cmmnPropObj.syncAjaxSummaries(filter, dataId);
		
		if(rows && rows.length > 0){
			that.data[id]['comp'] = {
					pageNumber : 1,
					pageSize : 10,
					ids : rows[0].ids
			};
			
			that.loadDetail(dataId);
		}
		
		var pagination = $(gridSelector, that.selector).datagrid('getPager');
		pagination.pagination({
			displayMsg : '{from} - {to} / {total}',
			onSelectPage : function(pageNumber, pageSize){
				that.data[id]['comp'].pageNumber = pageNumber;
				that.data[id]['comp'].pageSize = pageSize;
				that.loadDetail(dataId);
			}
		});
	}*/
	
	/*createMap : function(){
		var that =this;
	}*/
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
				height : 251,
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
				bjdNam : '전체'
			});
			
			$('#comboBx_emd', that.selector).combobox('loadData', emdData);
			
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
				width : 362
			});
			
			$('#comBx_pbpKnd', that.selector).combobox({
				valueField : 'value',
				textField : 'label',
				data:[{
					value:'',
					label:'전체'
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
			
			var deptCode = cmmnPropObj.getDeptCode();
			if(deptCode.indexOf('4210209') < 0){
				$('#textBx_manNm', that.selector).textbox('disable');
			}
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
			var prtIdn = $('#numSpn_prtIdn', that.selector).numberspinner('getValue');
			var pbpKnd = $('#comBx_pbpKnd', that.selector).combobox('getValue');
			var empNam = $('#textBx_empNam', that.selector).textbox('getValue');
			var empNum = $('#textBx_empNum', that.selector).textbox('getValue');
			var ocpStr = $('#dateBx_ocpStr', that.selector).datebox('getValue');
			var ocpEnd = $('#dateBx_ocpEnd', that.selector).datebox('getValue');
			var deptCode = cmmnPropObj.getDeptCode();
			
			var param = { 
					emd : emd,
					mntn : mntn,
					bun : bun,
					ho : ho,
					prtIdn : prtIdn,
					pbpKnd : pbpKnd,
					empNam : empNam,
					empNum : empNum,
					ocpStr : ocpStr,
					ocpEnd : ocpEnd,
					deptCode : deptCode
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
			$('#numSpn_prtIdn', that.selector).numberspinner('setValue','');
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
			if(
					(param.emd == '' || param.emd == null) && 
					(param.mntn == '' || param.mntn == null) &&
					(param.bun == '' || param.bun == null) && 
					(param.ho == '' || param.ho == null) && 
					(param.prtIdn == '' || param.prtIdn == null) && 
					(param.pbpKnd == '' || param.pbpKnd == null) &&
					(param.empNam == '' || param.empNam == null) &&
					(param.empNum == '' || param.empNum == null) && 
					(param.ocpStr == '' || param.ocpStr == null) && 
					(param.ocpEnd == '' || param.ocpEnd == null) &&
					(param.deptCode.indexOf('4210209') > -1)
			){
				return '1 = 1';
			}else{
				var cqlData = "";
				
				if(param.emd != '' && param.emd != null){
					cqlData+=" AND REGN_CODE = '" + param.emd +"'";
				}
				if(param.mntn != '' && param.mntn != null){
					cqlData+=" AND SAN = '" + param.mntn +"'";
				}
				if(param.bun != '' && param.bun != null){
					cqlData+=" AND BUNJI = '" + param.bun +"'";
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
				if(param.empNam != '' && param.empNam != null){
					cqlData+=" AND EMP_NAM LIKE '%" + param.empNam +"%'";
				}
				if(param.empNum != '' && param.empNum != null){
					cqlData+=" AND EMP_NUM LIKE '%" + param.empNum +"%'";
				}
				if(param.ocpStr != '' && param.ocpStr != null){
					cqlData+=" AND OCP_STR = '" + param.ocpStr +"'";
				}
				if(param.ocpEnd != '' && param.ocpEnd != null){
					cqlData+=" AND OCP_END = '" + param.ocpEnd +"'";
				}
				if(param.deptCode.indexOf('4210209') < 0){
					cqlData += " AND MAN_CDE = '" + param.deptCode + "0000' ";
				}
				
				var cql = cqlData.replace(' AND ', '');
				
				return cql;
				
			}
		}/*,
		
		relationShowFeature : function(selector){
			var that = this;
			
			var data = $('.grid_result_detail', selector).datagrid('getSelected');
			var dataId = '';
			var pbpKnd = stringUtils.lpad(data.pbpKnd, 2, '0');
			var cql = " PRT_IDN = " + data.prtIdn + " AND PBP_KND = '" + pbpKnd + "' ";
			var features = [];
			
			if(pbpKnd.indexOf('01') > -1){
				dataId = COMMON.PROP;
			}else if(pbpKnd.indexOf('02') > -1){
				dataId = COMMON.BUID;
			}
			
			var filter = {
					filterType : 'CQL',
					cql : cql
			};
			
			var format = new ol.format.WKT();
			
			var summaryRow = cmmnPropObj.syncAjaxSummaries(filter, dataId);
			
			if(summaryRow){
				var listAllfilter = {
						filterType : 'FID',
						fid : summaryRow[0].ids
				};
				
				spatialSearchUtils.listAll(that.TITLE, dataId, listAllfilter, function(data){
					if(data){
						var row = data[0];
						var geom = row.geom;
						
						if(geom){
							if(geom.indexOf('null') < 0){
								var feature = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);
								features.push(feature);
							}else{
								$.messager.alert(that.TITLE, '공간 정보가 없습니다.');
							}
						}else{
							if(data.length == 1){
								$.messager.alert(that.TITLE, '공간 정보가 없습니다.');
							}
						}
						
						if(features && features.length > 0){
							highlightObj.showRedFeatures(that.CLASS_NAME, features, true, {
								projection : ol.proj.get(MAP.PROJECTION)
							});
						}
					}
				}, null);
			}else{
				$.messager.alert(that.TITLE, '재산 정보가 없습니다.');
			}
		}*/
};

cmmnOccpObj.view = {
		
		dataId : null,
		
		selector : null,
		
		data : {},
		
		init : function(ocpIdn, dataId, selector, param, prtIdn){
			var that = this;
			
			that.dataId = dataId;
			that.selector = selector;
			
			that.dataLoading(ocpIdn, dataId, selector, param, prtIdn);
			
			that.initUi();
			
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
					
					//that.rwatResultLoading(bmlOccpDt.prtIdn, bmlOccpDt.ocpIdn);
					
				},error : function(err){
					console.error(err);
				}
			});
		}/*,
		
		rwatResultLoading : function(prtIdn, ocpIdn){
			var that = this;
			
			var dataId = 'BML_RWAT_DT';
			
			var kwsData = dataObj.getDataByDataId(dataId, true);
			var kdf = kwsData.kwsDataFields;
			
			var fznCol = [];
			var cols = [];
			var pkCol = '';
			
			
			for(var i = 0; i < kdf.length; i++){
				if(kdf[i].pkAt.indexOf('Y') > -1){
					pkCol = camelcaseUtils.underbarToCamelcase(kdf[i].fieldId);
					
					fznCol.push({
						field : pkCol,
						title : '번호',
						width : 100,
						sortable : true
					});
				}else if(kdf[i].indictTy.indexOf('HIDE') < 0){
					cols.push({
						field : camelcaseUtils.underbarToCamelcase(kdf[i].fieldId),
						title : kdf[i].fieldAlias,
						width : 100,
						sortable : true
					});
				}
			}
			
			$('.grid_result_rwat', that.selector).datagrid({
				pk : pkCol,
				frozenColumns : [fznCol],
				columns : [cols],
				singleSelect : true,
				showFooter : false,
				pagination : true
			});
			
			var cql = ' PRT_IDN = ' + prtIdn + ' AND OCP_IDN = ' + ocpIdn;
			
			var filter = {
				filterType : 'CQL',
				cql : cql,
			};
			
			var rows = cmmnPropObj.syncAjaxSummaries(filter, dataId);
			var id = tabObj.getId();
			
			if(rows && rows.length > 0){
				that.data[id]['rwat'] = {
						pageNumber : 1,
						pageSize : 10,
						ids : rows[0].ids
						
				};
				that.loadDetail(dataId);
			}
			
			var pagination = $('.grid_result_rwat', that.selector).datagrid('getPager');
			pagination.pagination({
				displayMsg : '{from} - {to} / {total}',
				onSelectPage : function(pageNumber, pageSize){
					that.data[id]['rwat'].pageNumber = pageNumber;
					that.data[id]['rwat'].pageSize = pageSize;
					that.loadDetail(dataId);
				}
			});
		},
		
		loadDetail : function(dataId){
			var that = this;
			
			that.setSelector();
			var id = tabObj.getId();
			
			var data = that.data[id]['rwat'];
			
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
			
			$('.grid_result_rwat', that.selector).datagrid('loadData', gridData);
		}*/
		
		/*createMap : function(){
			var that =this;
		}*/
	};


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

var cmmnAcinObj = {

	TITLE : "현장조사 결과 검색",

	CLASS_NAME : "CommonAcinSearch",

	selector : null,

	isClosed : false,

	dataId : "BML_ACIN_AS",

	open : function() {
		var that = this;

		if (that.selector) {
			that.close();
		}

		var windowOptions = {
			width : 700,
			height : 217,
			top : 120,
			left : 330,
			onClose : function() {
				that.close();
			}
		};

		var url = CONTEXT_PATH + '/cmmnprop/searchAcinPage.do';

		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null,
				windowOptions, function() {
					that.initUi();
					that.init();
					that.bindEvents();
				});

		that.selector = "#" + id;

	},

	init : function() {
		var that = this;

		 var dongList = dongObj.getData();
		 var emdData = [];
		
		 for (var i = 0; i < dongList.length; i++) {
			 emdData.push(dongList[i]);
		 }
		
		 emdData.unshift({
		 bjdCde : '',
		 bjdNam : '전체'
		 });
		
		 $('#comboBx_emd', that.selector).combobox('loadData', emdData);
		 
		 

	},

	initUi : function() {
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
				text : '전체'
			},{
				value : '행정재산',
				text : '행정재산'
			},{
				value : '일반재산',
				text : '일반재산'
			}]
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
	
	search : function(){
		var that = this;
		
		var emd = $('#comboBx_emd', that.selector).combobox('getValue');
		var mntn = $('#mntn', that.selector).switchbutton('options').checked?'2':'1';
		var mnnm = $('#mnnm', that.selector).numberspinner('getValue');
		var slno = $('#slno', that.selector).numberspinner('getValue');
		var meansSe = $('#comboBx_meansSe', that.selector).combobox('getValue');
		var pnu;
		//전체검색용
		if(emd=="" && mntn == 1 && mnnm=="" && slno == ""){
			pnu = null;
		//본번 미입력 / 부번입력시
		}else if (slno!="" && mnnm == ""){
			$.messager.alert(that.TITLE, "부번 입력시 본번은 필수입니다.");
			return false;
		}
		//본번 부번 미입력
		else if (slno=="" || mnnm == ""){
			pnu = pnuObj.createPnuForLike(emd,mntn,mnnm,slno); 
		}
		//전체입력시
		else{
			pnu = pnuObj.createPnu(emd,mntn,mnnm,slno); 
		}
		
		var param = { 
				pnu : pnu,
				meansSe : meansSe
			};
		
		var cql = that.createSqlWhereConstruction(param);
		
		var filter = {
				filterType : 'CQL',
				cql : cql
			};
		
		resultObj.close();
		
		cmmnPropObj.searchRes(that.dataId, filter);
	},
	
	createSqlWhereConstruction : function(param){
		if((param.pnu == '' || param.pnu == null) && (param.meansSe == '' || param.meansSe == null)){
			return '1 = 1';
		}else{
			var cqlData = "";
			
			if(param.pnu != '' && param.pnu != null){
				if(param.pnu.indexOf('%') > -1){
					cqlData += " PNU LIKE '" + param.pnu + "' ";
				}else{
					cqlData += " PNU = '" + param.pnu +"' ";
				}
				if(param.meansSe != '' && param.meansSe != null){
					cqlData += ' AND ';
					cqlData += " MEANS_SE LIKE '%" + param.meansSe + "%' ";
				}
			}else{
				if(param.meansSe != '' && param.meansSe != null){
					cqlData += " MEANS_SE LIKE '%" + param.meansSe + "%' ";
				}
			}
			
			return cqlData;
				
		}
	},
	
	clean : function() {
		var that = this;
		
		$('#comboBx_meansSe', that.selector).combobox('select', '');
		$('#comboBx_emd', that.selector).combobox('select','');
		$('.numSpn', that.selector).numberspinner('setValue','');
		$('#mntn', that.selector).switchbutton('clear');
	},
	
	close : function() {
		var that = this;
		
		that.clean();
		windowObj.removeWindow(that.selector);
	}
	
};

cmmnAcinObj.view = {
		dataId : null,
		
		selector : null,
		
		prtIdn : null,
		
		init : function(prtIdn, dataId, selector){
			var that = this;
			
			that.prtIdn = prtIdn;
			that.dataId = dataId;
			that.selector = selector;
			
			that.loadData();
			that.bindEvents();
		},
		
		loadData : function(){
			var that = this;
			
			var url = CONTEXT_PATH + '/cmmnprop/' + that.prtIdn + '/searchAcinData.do';
			
			$.ajax({
				url : url,
				dataType : 'json',
				success : function(rs){
					if(rs && rs.bmlAcinAs){
						var bmlAcinAs = rs.bmlAcinAs;
						
						var kwsData = dataObj.getDataByDataId(that.dataId, true);
						var dataFields = kwsData.kwsDataFields;
						
						for(var i = 0; i < dataFields.length; i++){
							if(dataFields[i].indictTy.indexOf('CURRENCY') > -1){
								bmlAcinAs[camelcaseUtils.underbarToCamelcase(dataFields[i].fieldId)] = numberUtils.formatCurrency(bmlAcinAs[camelcaseUtils.underbarToCamelcase(dataFields[i].fieldId)]);
							}
							
							if(dataFields[i].fieldId == 'FTR_CDE'){
								that.ftrCde = dataFields[i].dfltValue;
							}
						}
						
						for(var i = 0; i < Object.keys(bmlAcinAs).length; i++){
							var oKey = Object.keys(bmlAcinAs)[i];
							
							if(oKey.indexOf('locPlc') > -1 || oKey == 'bmlCde' || oKey.indexOf('meansSe') > -1){
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