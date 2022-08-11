var prtsAreaObj = {
	selector : '#div_menu_panel_search_prtsArea',
	
	PAGE : "prtsArea",
	
	CLASS_NAME : "prtsAreaMenu",
	
	TITLE : "보호구역",
	
	dataId : 'RDL_PRTS_AS',
		
	pageIndex : 1,
	
	isMenuCreated : false,
	
	dongList : [],
	
	transformJobMenu : function(){
		var that = this;
		//dataAuthorObj.load();
		var isAuthor = dataAuthorObj.getDataAuthor(that.dataId);
		if(typeof(isAuthor) != 'undefined' && isAuthor != null && isAuthor != ''){
			if(isAuthor.indictAt == 'Y'){
				menuObj.selectMenu(that.PAGE);
			}
		}else{
			$.messager.alert('메뉴 선택', '권한이 없습니다.');
			return false;
		}
	},
	
	init : function(){
		var that = this;
		
		that.initUi();
		that.load();
		that.bindEvents();
		that.open();
		
	},
	
	initUi : function(){
		var that = this;
		
		$(".div_menu_panel_prtsArea_tab").tabs({
			tabWidth : 50,
			tabHeight : 45
		});
		
		$(".prtsArea_ftrCde", that.selector).combobox({
			required: false,
			valueField : 'codeId',
			textField : 'codeNm'
		});
		
		$(".prtsArea_sel_dong", that.selector).combobox({
			required: false,
			valueField: camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD)
		});
		
		$(".prtsArea_roadNm", that.selector).textbox({
			prompt : '명칭을 입력하십시오.'
		});
		
		$(".prtsArea_facNum", that.selector).numberspinner({
			min : 1,
			max : 9999,
			increment : 1
		});
		
		$(".prtsArea_fadNum", that.selector).numberspinner({
			min : 1,
			max : 9999,
			increment : 1
		});
		
		$(".prtsArea_ftrIdn", that.selector).numberspinner({
			min : 1,
			max : 99999999,
			increment : 1
		});
		
		/*$(".prtsArea_mntn", that.selector).checkbox({
			label : "ok",
			value: "Y",
			checked: false
		});
		
		$(".prtsArea_mntn", that.selector).switchbutton({
			checked: false,
			onText : 'Y',
			offText : 'N'
		});*/
		
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
		
		$(".a_reset", that.selector).linkbutton({
			iconCls : "icon-reload"
		});
	},
	
	load : function(){
		var that = this;
		
		var sel_dongData = dongObj.getData();
		for(var i = 0; i < sel_dongData.length; i++){
			that.dongList[i] = sel_dongData[i];
		}
		var domnCodeList = domnCodeObj.getData('KWS-000');
		var ftrCdeData = dataFtrCdeObj.getData(that.dataId);
		
		for(var i = 0; i < ftrCdeData.length; i++){
			for(var j = 0; j < domnCodeList.length; j++){
				if(ftrCdeData[i].codeId == domnCodeList[j].codeId){
					ftrCdeData[i]['codeNm'] = domnCodeList[j].codeNm;
				}
			}
		}
		
		ftrCdeData.unshift({
			codeId : '',
			codeNm : '전체'
		});
		
		that.dongList.unshift({
			bjdCde : '',
			bjdNam : '전체'
		});
		
		$(".prtsArea_ftrCde", that.selector).combobox("loadData", ftrCdeData);
		//$(".prtsArea_ftrCde", that.selector).combobox("setValue", ftrCdeData[1].codeId);
		
		$(".prtsArea_sel_dong", that.selector).combobox("loadData", that.dongList);
		//$(".prtsArea_sel_dong", that.selector).combobox("setValue", sel_dongData[0].bjdCde);
	},
	
	bindEvents : function(){
		var that = this;
		
		$(".prtsArea_ftrCde", that.selector).combobox("textbox").keyup(function(e){
			var keyCode = e.keyCode?e.keyCode:e.which;
			if(keyCode == 13){
				$(".prtsArea_sel_dong", that.selector).combobox("textbox").focus();
			}
		});
		
		$(".prtsArea_facNum", that.selector).numberspinner("textbox").keyup(function(e){
			var keyCode = e.keyCode?e.keyCode:e.which;
			if(keyCode == 13){
				$(".prtsArea_fadNum", that.selector).numberspinner("textbox").focus();
			}
		});
		
		$(".prtsArea_roadNm", that.selector).textbox("textbox").keyup(function(e){
			var keyCode = e.keyCode?e.keyCode:e.which;
			if(keyCode == 13){
				$(".a_search", that.selector).focus();
			}
		});
		
		$(".a_reset", that.selector).click(function(){
			that.reset();
		});
		
		$(".a_search", that.selector).click(function(){
			that.search(1);
		});
		
		$(that.selector).on("click",".a_content", function(){
			var dataNo = $(this).attr("dataNo");
			prtsAreaObj.prtsAreaResultObj.open(that.dataId ,dataNo);
		});
		
		$(that.selector).on("click", ".div_search_pagination a", function(){
			var pageIndex = $(this).attr("data-page-index");
			that.search(pageIndex);
		});
	},
	
	search : function(pageIndex){
		var that = this;
		
		var data = {};
		
		data['pageIndex'] = pageIndex;
		
		var ftrCde = $(".prtsArea_ftrCde", that.selector).combobox("getValue");
		var bjdCde = $(".prtsArea_sel_dong", that.selector).combobox("getValue");
		//var mntnVal = $(".prtsArea_mntn", that.selector).switchbutton("options").value.indexOf('on') > -1 ? '2':'1';
		var facNum = $(".prtsArea_facNum", that.selector).numberspinner("getValue");
		var fadNum = $(".prtsArea_fadNum", that.selector).numberspinner("getValue");
		var ftrIdn = $(".prtsArea_ftrIdn", that.selector).numberspinner("getValue");
		var rodNam = $(".prtsArea_roadNm", that.selector).textbox("getValue");
		
		if(ftrCde != null && ftrCde != ''){
			data['ftrCde'] = ftrCde;
		}

		if(bjdCde != null && bjdCde != ''){
			data['bjdCde'] = bjdCde;
		}

		if(facNum != null && facNum != ''){
			facNum = stringUtils.lpad(facNum, 4);
			data['facNum'] = facNum;
		}

		if(fadNum != null && fadNum != ''){
			fadNum = stringUtils.lpad(fadNum, 4);
			data['fadNum'] = fadNum;
		}

		if(ftrIdn != null && ftrIdn != ''){
			data['ftrIdn'] = ftrIdn;
		}

		if(rodNam != null && rodNam != ''){
			data['rodNam'] = '%' + rodNam + '%';
		}
		
		var url = CONTEXT_PATH + '/prtsArea/selectList.do';
		
		$.ajax({
			type: 'POST',
			dataType : 'json',
			url: url,
			data: data,
			success:function(rs){
				that.createListContent(rs.rows);
				that.createPaging(rs.paginationInfo);
			},
			error:function(err){
				console.log(err);
			}
		});
	},
	
	createPaging : function(paginationInfo){
		var that = this;
		
		var tagStr = '';
		
		$(".font_total_count", that.selector).html(paginationInfo.totalRecordCount);
		
		if(paginationInfo.currentPageNo > paginationInfo.firstPageNo){
			tagStr += '<a href="#" class = "a_img" data-page-index="' + paginationInfo.firstPageNo + '"><img src="images/kworks/map/common/boardLst_pp.png"/></a>';
			tagStr += '<a href="#" class = "a_img" data-page-index="' + (paginationInfo.currentPageNo-1) + '"><img src="images/kworks/map/common/boardLst_p.png" /></a>';
		}
		
		for(var i = paginationInfo.firstPageNoOnPageList; i <= paginationInfo.lastPageNoOnPageList; i++){
			tagStr += '<a href="#" class="a_text';
			tagStr += i==paginationInfo.currentPageNo?" a_select ":"";
			tagStr += '" data-page-index="' + i + '">';
			tagStr += i;
			tagStr += '</a>';
		}
		
		if(paginationInfo.currentPageNo < paginationInfo.lastPageNo){
			tagStr += '<a href="#" class="a_img" data-page-index="' + (paginationInfo.currentPageNo+1) + '"><img src="images/kworks/map/common/boardLst_n.png" /></a>';
			tagStr += '<a href="#" class="a_img" data-page-index="' + paginationInfo.lastPageNo + '"><img src="images/kworks/map/common/boardLst_nn.png" /></a>';
				
		}
		
		$(".div_search_pagination", that.selector).html(tagStr);
		$(".div_search_pagination", that.selector).css("display","block");
	},
	
	createListContent : function(prtsAreaList){
		var that = this;
		
		var tagStr = '';
		
		for(var i = 0; i < prtsAreaList.length; i++){
			//var dongNm = dongObj.getDongName(prtsAreaList[i].bjdCde);
			var dongNm = '';
			for(var j = 0; j < that.dongList.length; j++){
				if(that.dongList[j].bjdCde == prtsAreaList[i].bjdCde){
					dongNm = that.dongList[j].bjdNam;
				} 
			}
			var facNum = prtsAreaList[i].facNum;
			var fadNum = prtsAreaList[i].fadNum;
			var schNam = prtsAreaList[i].schNam;
			
			var a_content = dongNm;
			
			if(facNum != null && facNum != ''){
				a_content += ' ' + facNum;
				
				if(fadNum != null && fadNum != ''){
					fadNum = stringUtils.lpad(fadNum, 4);
					if(fadNum != '0000'){
						a_content += '-' + fadNum + ' ';
					}
				}
			}
			
			if(schNam != null && schNam != ''){
				a_content += '(' + schNam + ')';
			}
			
			tagStr += '<li>';
			
			if(a_content.length > 21){
				tagStr += '<a href="#" class="a_content" dataNo = "'+ prtsAreaList[i].objectId +'" title="' + a_content + '">';
				
				tagStr += a_content.replace(a_content.substring(21, a_content.length),'...');
				
			}else{
				tagStr += '<a href="#" class="a_content" dataNo = "'+ prtsAreaList[i].objectId +'">';
				tagStr += a_content;
			}
			
			tagStr += '</a></li>';
		}
		
		$(".ul_search_content", that.selector).html(tagStr);
		$(".div_search_content", that.selector).css("display","block");
	},
	
	reset : function(){
		var that =this;
		
		$(".prtsArea_ftrCde", that.selector).combobox("setValue","");
		$(".prtsArea_sel_dong", that.selector).combobox("setValue","");
		//$(".prtsArea_mntn", that.selector).switchbutton("reset");
		$(".prtsArea_facNum", that.selector).numberspinner("setValue","");
		$(".prtsArea_fadNum", that.selector).numberspinner("setValue","");
		$(".prtsArea_ftrIdn", that.selector).numberspinner("setValue","");
		$(".prtsArea_roadNm", that.selector).textbox("setValue","");
	},
	
	open : function(){
		var that = this;
		$(that.selector).show();
	},
	
	close : function() {
		var that = this;
		$(that.selector).hide();
	}
};

///////////////////////////////////////////////////////////////////////////////
prtsAreaObj.prtsAreaResultObj = {
		TITLE : '보호구역 상세조회',
		
		CLASS_NAME : 'PrtsAreaResult',
		
		selector : null,
		
		dataId : null,
		
		dataIds : [],
		
		dataNo : null,
		
		ftrCde : null,
		
		ftrIdn : null,
		
		selectFacility : null,
		
		selectFacilityPk : null,
		
		data : null,
		
		//도로 시설물중 제외되는 데이터들
		toBeRemoved : [
			"RDL_SECT_LS",	"SECT_PATH_LS",	"SECT_PATH_TM",
			"OCP_PERM_PS",	"OCP_PERM_LS",	"OCP_PERM_AS",
			"RDL_STLT_DT",	"RDL_TREE_DT",	"RDL_PEGB_PS",
			"A_BASE",	"RDL_BRDG_AS",	"RDL_CROS_PS",
			"RDL_CTLR_LS",	"RDL_EVRD_AS",	"RDL_RBLN_LS",
			"RDL_RSMN_AS",	"RDL_BYCR_LS",	"RDL_IPCR_LS",
			"RDL_CNSL_LS",	"RDL_SPBY_PS",	"RDL_INTE_AS",
			"RDL_CLBM_AS",	"RDST_AREA_AS",	"RDL_CMDT_AS",
			"RDL_EXCV_AS",	"RDL_JEOB_AS",	"RDL_NRDS_PS",
			"RDL_PAVE_AS",	"RDL_RDAR_AS",	"RDL_SQAR_AS",
			"RDL_SWAR_AS",	"RDL_REAL_LS",	"RDL_ORCP_PS",
			"RDL_RMSW_PS",	"RDL_SHEL_AS",	"RDL_EGBK_LS",
			"RDL_RDST_AS",	"RDL_PYRD_AS",	"RDL_RNDW_AS",
			"RDL_OCPY_AS"
			],
		
		open : function(dataId, dataNo, tabIndex){
			var that = this;
			
			if(that.selector){
				that.close();
			}
			
			that.dataId = dataId;
			that.dataNo = dataNo;
			
			var url = CONTEXT_PATH + '/window/prtsArea/prtsAreaResult/page.do?nocache='+Math.random();
			var options = {
				width : 600,
				height : 350,
				top : 100,
				collapsible : false,
				draggable : false,
				containerSelector : '#map_container',
				inline : true,
				onClose : function(){
					that.close();
				}
			};
			
			if(!tabIndex){
				tabIndex = 0;
			}
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, options, function(){
				that.initUi();
				that.loadDataIds();
				that.load(tabIndex);
			});
			
			that.selector = "#" + id;
		},
		
		load : function(tabIndex){
			var that = this;
			
			// 보호구역 상세조회
			spatialSearchUtils.selectOne(that.TITLE, that.dataId, that.dataNo, null, function(data){
				var gridData = [];
				var dataInfo = dataObj.getDataByDataId(that.dataId, true);
				var dataFields = dataInfo.kwsDataFields;
				for(var i = 0; i < dataFields.length; i++){
					var dataField = dataFields[i];
					var indictTy = dataField.indictTy;
					if(indictTy != "HIDE" && indictTy != "WKT"){
						var fieldId = dataField.fieldId;
						if(fieldId == "FTR_CDE" || fieldId == "FTR_IDN" || fieldId == "BJD_CDE" || fieldId == "FAC_NUM" || fieldId == "FAD_NUM" || fieldId == "ROD_NAM" || fieldId == "SCH_NAM"){
							var field = camelcaseUtils.underbarToCamelcase(fieldId);
							if(field.indexOf("ftrCde") > -1){
								that.ftrCde = data[field];
							}else if(field.indexOf("ftrIdn") > -1){
								that.ftrIdn = data[field];
							}
							var value = data[field]; 
							gridData.push({
								section : dataField.fieldAlias,
								attribute : value
							});
						}
					}
				}
				
				that.data = data;
				
				$(".grid_prtsArea", that.selector).datagrid("loadData", gridData);
				
				that.highlight(data);
				
				prtsAreaObj.ptimHtObj.init(that, data);
				
				that.loadFacilities(that.dataIds, tabIndex);
			});
			//that.loadFacilities(that.dataIds, tabIndex);
		},
		
		highlight : function(data){
			var that = this;
			
			var format = new ol.format.WKT();
			var feature = format.readFeature(data[MAP.GEOMETRY_ATTR_NAME]);
			highlightObj.showRedFeatures(that.CLASS_NAME, [feature], true, {
				projection : ol.proj.get(MAP.PROJECTION)
			});
		},
		
		loadDataIds : function(){
			var that = this;
			
			var dataList = dataObj.data;
			var dataIds = [];
			for(var i in dataList){
				var dataCtgryId = dataList[i].dataCtgryId;
				if(dataCtgryId.indexOf("ROAD_ATTACHMENT") > -1 || dataCtgryId.indexOf("ROAD") > -1 || dataCtgryId.indexOf("TRANSPORTATION") > -1 ){
					if(dataList[i].useAt.indexOf("Y") > -1 && dataList[i].dataTy.indexOf("LAYER") > -1){
						dataIds.push(dataList[i].dataId);
					}
				}
			}
			for(var i =0; i < dataIds.length; i++){
				for(var j = 0; j < that.toBeRemoved.length; j++){
					if(dataIds[i] == that.toBeRemoved[j]){
						dataIds.splice(i, 1);
					}
				}
			}
			
			that.dataIds = dataIds;
		},
		
		loadFacility : function(selectGrid){
			var that = this;
			
			var filter = {
					filterType : "RELATION",
					spatialType : "INTERSECTS",
					relationDataId : that.dataId,
					fids : that.dataNo
			};
			
			spatialSearchUtils.searchSummaries(that.TITLE, that.dataIds, filter, function(rows){
				for(var i = 0; i < rows.length; i++){
					if(rows[i].dataAlias == selectGrid.facility){
						
						that.selectFacility = rows[i];
						
						var kwsData = dataObj.getDataByDataId(rows[i].dataId, true);
						var kwsDataField = kwsData.kwsDataFields;
						
						for(var j = 0; j < kwsDataField.length; j++){
							if(kwsDataField[j].pkAt.indexOf("Y") > -1){
								that.selectFacilityPk = camelcaseUtils.underbarToCamelcase(kwsDataField[j].fieldId);
							}
						}
						
						
						var frozenColumns =[{
							field : that.selectFacilityPk,
							title : "번호",
							width : 100,
							sortable : true
						}];
						
						
						var columns = [];
						
						var kwsDataFieldLen = kwsDataField.length;
						kwsDataFieldLen = kwsDataFieldLen > 10 ? 10 : kwsDataFieldLen; 
						
						for(var j = 0; j < kwsDataFieldLen; j++){
							if(kwsDataField[j].indictTy != "WKT" && kwsDataField[j].indictTy != "HIDE"  && kwsDataField[j].fieldId != "BJD_CDE"){
								columns.push({
									field : camelcaseUtils.underbarToCamelcase(kwsDataField[j].fieldId),
									title : kwsDataField[j].fieldAlias,
									width : 100,
									sortable : false
								});
							}
						}
						
						$(".grid_facility", that.selector + " .div_prtsArea").datagrid({
							pk : that.selectFacilityPk,
							frozenColumns : [frozenColumns],
							columns : [columns],
							singleSelect : true,
							fitColumns : true,
							showFooter : false,
							onSelect : function(index, row){
								that.highlight(row);
								that.registerOpenToFacility(that.selectFacility.dataId, row);
							}
						});
						
						var listAllFilter = {
								filterType: "FIDS",
								fids : rows[i].ids,
								sortOrdr: "OBJECTID",
								sortDirection: "desc"
						};
						
						var gridData = [];
						
						spatialSearchUtils.listAll(that.TITLE, rows[i].dataId, listAllFilter, function(facilityList){
							for(var j = 0; j < facilityList.length; j++){
								var oKey = Object.keys(facilityList[j]);
								var gridDataValue = {};
								for(var k = 0; k < oKey.length; k++){
									gridDataValue[oKey[k]] = facilityList[j][oKey[k]];
								}
								gridData.push(gridDataValue);
							}
							$(".grid_facility", that.selector + " .div_prtsArea").datagrid("loadData",gridData);
						});
					}
				}
			});
		},
		
		loadFacilities : function(dataIds, tabIndex){
			var that = this;
			
			/*var filter = {
					filterType : "RELATION",
					spatialType : "INTERSECTS",
					relationDataId : that.dataId,
					fids : that.dataNo
			};
			
			var gridData = [];
			
			spatialSearchUtils.searchSummaries(that.TITLE, dataIds, filter, function(rows){
				for(var i = 0; i < rows.length; i++){
					var field = rows[i].dataAlias;
					var quantity = rows[i].ids.length;
					quantity = numberUtils.formatCurrency(quantity);
					
					gridData.push({
						facility : field,
						quantity : quantity
					});
				}
				
				$(".grid_facilities", that.selector + " .div_prtsArea").datagrid("loadData", gridData);
				
				$(".div_window_prtsArea_result_tabs", that.selector).tabs('select', tabIndex);
				
				if(tabIndex == 1){
					$(".a_addPrtsAreaHt", that.selector).show();
				}
			});*/
			
			var filter = {
						filterType : "RELATION",
						spatialType : "INTERSECTS",
						relationDataId : that.dataId,
						fids : that.dataNo
			};
			
			if(dataIds && dataIds.length > 0){
				filter.dataIds = dataIds;
			};
				
				var url = CONTEXT_PATH + "/rest/spatial/searchSummaries.do";
				
				var gridData = [];
				
				$.ajax({
					type : 'POST',
					dataType : 'json',
					data : filter,
					url : url,
					async : false,
					success : function(rs){
						if(rs && rs.rows){
							var rows = rs.rows;
							for(var i = 0; i < rows.length; i++){
								var field = rows[i].dataAlias;
								var quantity = rows[i].ids.length;
								quantity = numberUtils.formatCurrency(quantity);
								
								gridData.push({
									facility : field,
									quantity : quantity
								});
							}
							
							$(".grid_facilities", that.selector + " .div_prtsArea").datagrid("loadData", gridData);
							
							$(".div_window_prtsArea_result_tabs", that.selector).tabs('select', tabIndex);
							
							if(tabIndex == 1){
								$(".a_addPrtsAreaHt", that.selector).show();
							}
						}
					},
					error : function(err){
						console.log(err);
					},
					beforeSend : function(){
						$("#div_loading").show();
					},
					complete : function(){
						$("#div_loading").hide();
					}
					
				});
		},
		
		exportData : function(exportTy, opt_params){
			var that = this;
			
			var url = CONTEXT_PATH + "/rest/export/addByData.do";
			var params = $.extend({
				exportTy : exportTy,
				exportFilterTy : "FIDS",
				exportCntmId : MAP.PROJECTION,
				dataIds : [that.selectFacility.dataId],
				ids : that.selectFacility.ids
			}, opt_params);
			
			if(exportTy.indexOf("EXCEL") > -1){
				params["fieldNmIndictAt"] = true;
			}
			
			$.post(url, params).done(function(result){
				if(result && result.rowCount){
					$.messager.confirm(that.TITLE, "내보내기 데이터는 '메인 > 나의 시스템 > 데이터요청 관리'에서 다운로드 할 수 있습니다. 지금 확인하시겠습니까?", function(isTrue){
						if(isTrue){
							window.open(CONTEXT_PATH + '/self/export/list.do',"_blank");
						}
					});
				}else{
					$.messager.alert(that.TITLE, "내보내기 요청이 실패하였습니다.");
				}
			}).fail(function(){
				$.messager.alert(that.TITLE, "내보내기 요청이 실패하였습니다.");
			});
		},
		
		initUi : function(){
			var that = this;
			
			$(that.selector).window({
				top : $(window).height() - 398
			});
			
			$(".a_addPrtsAreaHt", that.selector).hide();
			
			$(".div_window_prtsArea_result_tabs", that.selector).tabs({
				tools : that.selector + ' .div_window_prtsArea_tabs_tool',
				onSelect : function(title, index){
					if(index == '1' && title.indexOf("개선사업이력") > -1){
						$(".a_addPrtsAreaHt", that.selector).show();
						$(".a_addPrtsAreaHt", that.selector).css('display','');
						$(".a_addPrtsAreaHt", that.selector).css('position','');
						$(".a_addPrtsAreaHt", that.selector).css('left','');
					}
				},
				onUnselect : function(title, index){
					if(index == '1' && title.indexOf("개선사업이력") > -1){
						$(".a_addPrtsAreaHt", that.selector).hide();
					}
				}
			});
			
			var exportType = 'EXCEL';
			
			$(".a_exportToExcel", that.selector).linkbutton({
				width : 110,
				onClick : function(){
					windowObj.exportObj.open({
						userName : $("#hid_user_name").val(),
						deptName : $("#hid_dept_name").val(),
						exportType : exportType,
						onSubmit : function(params){
							that.exportData(exportType, params);
						}
					});
				}
			});
			
			$(".a_edit_update").click(function(){
				that.open(that.dataId, that.dataNo);
			});
			
			$(".a_addPrtsAreaHt", that.selector).linkbutton({
				width : 110,
				onClick : function(){
					prtsAreaObj.ptimHtObj.insertObj.open(that.ftrCde, that.ftrIdn, that.dataId, that.dataNo);
				}
			});
			
			$(".a_addPrtsAreaHt", that.selector).hide();
			
			mainObj.addResizeHandler(that.CLASS_NAME, function(width, height){
				that.resizeWindowHandler(width,height);
			});
			
			mainObj.resizeWindowHandler();
			
			$(that.selector + " .div_prtsArea").layout();
			
			$(that.selector + " .div_prtsArea").layout("resize",{
				width : "100%"
			});
			
			$(".div_facility", that.selector + " .div_prtsArea").layout();
			$(".div_facility", that.selector + " .div_prtsArea").layout("panel", "center").panel({
				onResize : function(width, height){
					that.resizeDatagrid(width, height);
				}
			});
			
			$(".grid_prtsArea", that.selector + " .div_prtsArea").datagrid({
				columns : [[{
					field : "section",
					title : "구분",
					width : 140
				},{
					field : "attribute",
					title : "속성",
					width : 260
				}]],
				height : 202,
				fitColumns : true,
				singleSelect : true
			});
			
			$("#prtsArea_dtl", that.selector + " .div_prtsArea").linkbutton({
				onClick : function(){
					that.highlight(that.data);
					that.registerOpenToPrtsArea();
				}
			});
			
			$(".grid_facilities", that.selector + " .div_prtsArea").datagrid({
				scrollbarSize : 0,
				columns : [[{
					field : "facility",
					title : "시설 종류",
					width : 120
				},{
					field : "quantity",
					title : "수량",
					width : 60
				}]],
				fitColumns : true,
				singleSelect : true,
				onSelect : function(index, row){
					that.loadFacility(row);
				}
			});
		},
		
		registerOpenToPrtsArea : function(){
			var that = this;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			var dataNo = [that.dataNo];
			
			windowObj.registerObj.open(prtsAreaObj.dataId, dataNo, null, function(){
				prtsAreaObj.prtsAreaResultObj.open(that.dataId, that.dataNo);
			}, null);
			
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		},
		
		resizeDatagrid : function(width, height){
			var that = this;
			
			var selectFacilities =$(".grid_facilities", that.selector + " .div_prtsArea").datagrid('getSelected');
			
			if(selectFacilities){
				$(".grid_facility", that.selector).datagrid("resize", {
					width : "100%"
				});
			}
		},
		
		resizeWindowHandler : function(windowWidth, windowHeight){
			var that = this;
			
			var navWidth = $("#div_menu").width();
			var width = windowWidth - navWidth;
			
			var left = 0;
			if($("#div_menu_panel").is(":visible")){
				left = $("#div_menu_panel").width();
				width = width - left;
			}
				
			
			$(that.selector).window("resize",{
				width : width,
				left : left,
				top : windowHeight - $(that.selector).window("panel").height() - 120
			});
			
			$(".div_window_prtsArea_result_tabs", that.selector).tabs("resize");
		},
		
		close : function(){
			var that = this;
			
			mainObj.removeResizeHandler(that.CLASS_NAME);
			mainObj.removeResizeHandler(prtsAreaObj.ptimHtObj.CLASS_NAME);
			windowObj.removeWindow(that.selector);
			that.selector = null;
			that.dataNo = null;
			
			return false;
		},
		
		registerOpenToFacility : function(dataId, row){
			var that = this;
			
			var fids = [];
			
			fids.push(row[that.selectFacilityPk]);
			
			windowObj.registerObj.open(dataId, fids);
		}
};

/////////////////////////////////////////////////////////////////////////
prtsAreaObj.ptimHtObj = {
		
		TITLE : '보호구역 개선사업이력',
		
		CLASS_NAME : 'prtsAreaHt',
		
		selector : null,
		
		parent : null,
		
		dataId : 'RDT_PTIM_HT',
		
		dataNo : null,
		
		init : function(parent, parentData){
			var that = this;
			
			that.parent = parent;
			
			var dataInfo = dataObj.getDataByDataId(that.dataId, true);
			var dataFields = dataInfo.kwsDataFields;
			
			var pk = null;
			var frozenColumns = [];
			
			var columns = [];
			
			for(var i = 0; i < dataFields.length; i++){
				if(dataFields[i].fieldTy != 'WKT' && dataFields[i].indictTy != 'HIDE' && dataFields[i].fieldId != ('IMP_IDN')){
					
					var camelFieldId = camelcaseUtils.underbarToCamelcase(dataFields[i].fieldId);
					
					var width = 159;
					
					if(dataFields[i].fieldId.indexOf("CNT_DES") > -1){
						width = 370;
					}
					
					columns.push({
						field: camelFieldId,
						title: dataFields[i].fieldAlias,
						width: width,
						sortable: false
					});
				}
				
				if(dataFields[i].pkAt.indexOf('Y') > -1){
					pk = camelcaseUtils.underbarToCamelcase(dataFields[i].fieldId);
					
					frozenColumns.push({
						field: pk,
						title: dataFields[i].fieldAlias,
						width: 100,
						sortable: true
					});
				}
			}
			
			$(".grid_prtsAreaHt", parent.selector + " .div_prtsAreaHt").datagrid({
				pk : pk,
				frozenColumns : [ frozenColumns ],
				columns : [columns],
				singleSelect : true,
				showFooter : false,
				onSelect : function(index, row){
					prtsAreaObj.ptimHtObj.selectOneObj.open(row.impIdn, that.parent.dataNo);
				}
			});
			
			that.loadPrtsAreaHtList(parentData.ftrCde, parentData.ftrIdn, dataFields);
			that.initUi();
			
		},

		loadPrtsAreaHtList : function(ftrCde, ftfIdn, dataFields){
			var that = this;
			
			var codeValue = domnCodeObj.getCodeByValue('KWS-000', ftrCde);
			var dataParam = {
					ftfCde : codeValue.codeId,
					ftfIdn : ftfIdn
			};
			
			var url = CONTEXT_PATH + "/prtsArea/rdtPtimHtSelectList.do";
			
			$.ajax({
				url : url,
				dataType : 'json',
				type: 'GET',
				data: dataParam,
				success:function(rs){
					if(rs || rs.rows.length > 0){
						var gridDatas = [];
						for(var i = 0; i < rs.rows.length; i++){
							var row = rs.rows[i];
							var gridData = {};
							for(var j = 0; j < Object.keys(row).length; j++){
								var oKey = Object.keys(row)[j];
								var value = row[oKey];
								for(var k = 0; k < dataFields.length; k++){
									if(camelcaseUtils.underbarToCamelcase(dataFields[k].fieldId).indexOf(oKey) > -1 && dataFields[k].indictTy.indexOf('DOMAIN') > -1){
										var codeData = domnCodeObj.getCode(dataFields[k].domnId, row[oKey]);
										value = codeData.codeNm;
									}
								}
								gridData[oKey] = value;
							}
							gridDatas.push(gridData);
						}
						$(".grid_prtsAreaHt", that.parent.selector + " .div_prtsAreaHt").datagrid("loadData",gridDatas);
						
						
						
					}
				},
				error: function(err){
					console.log(err);
				}
			});
		},
		
		initUi : function(){
			var that = this;
			
			mainObj.addResizeHandler(that.CLASS_NAME, function(width, height){
				that.resizeGridHandler(width,height);
			});
			
			mainObj.resizeWindowHandler(); 
			
			that.resizeGridHandler($(window).width, $(window).height);
		},
		
		resizeDatagrid : function(){
			$.extend($.fn.datagrid.methods,{resizeColumn:function(jq,param){return jq.each(function(){var dg = $(this);var col = dg.datagrid('getColumnOption', param.field); col.boxWidth = param.width + (col.boxWidth-col.width); col.width = param.width; dg.datagrid('fixColumnSize', param.field);});}});
		},
		
		resizeGridHandler : function(width, height){
			var that = this;
			
			that.resizeDatagrid();
			
			$(".grid_prtsAreaHt", that.parent.selector + " .div_prtsAreaHt").datagrid("resize",{
				width : "100%"
			});
			
			if($("#div_menu_panel").is(":visible")){
				$(".grid_prtsAreaHt", that.parent.selector + " .div_prtsAreaHt").datagrid("resizeColumn",{
					field : 'cntDes',
					width : 240
				});
				
				$(".grid_prtsAreaHt", that.parent.selector + " .div_prtsAreaHt").datagrid("resizeColumn",{
					field : 'prjNam',
					width : 287
				});
			}else{
				$(".grid_prtsAreaHt", that.parent.selector + " .div_prtsAreaHt").datagrid("resizeColumn",{
					field : 'cntDes',
					width : 480
				});
				
				$(".grid_prtsAreaHt", that.parent.selector + " .div_prtsAreaHt").datagrid("resizeColumn",{
					field : 'prjNam',
					width : 296
				});
			}
		}
};

prtsAreaObj.ptimHtObj.selectOneObj = {
		TITLE : '보호구역 개선사업이력 조회',
		
		CLASS_NAME : 'prtsAreaHtSelectOne',
		
		selector : null,
		
		isCreated : false,
		
		impIdn : null,
		
		parentDataNo : null,
		
		ftrCde : null,
		
		open : function(impIdn, parentDataNo, tabIndex){
			var that = this;
			
			if(that.selector){
				that.close();
			}
			
			that.impIdn = impIdn;
			that.parentDataNo = parentDataNo;
			
			var url = CONTEXT_PATH + '/prtsArea/rdtPtimHtSelectOnePage.do';
			var windowOptions = {
				width : 665,
				height : 309,
				top : 120,
				left : 330,
				isClose : function(){
					that.close();
				}
			};
			
			if(!tabIndex){
				tabIndex = 0;
			}
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function(){
					that.initUi(tabIndex);
					that.load();
					that.bindEvents();
			});
			
			that.selector = '#' + id;
		},
		
		bindEvents : function(){
			var that = this;
			
			$(".btn_modifyPrtsAreaHt", that.selector).click(function(){
				prtsAreaObj.ptimHtObj.updateObj.open(that.parentDataNo, that.impIdn);
				that.close();
			});
			
			$(".btn_removePrtsAreaHt", that.selector).click(function(){
				$.messager.confirm(that.TITLE, '보호구역 개선사업이력을 삭제하시겠습니까?',function(res){
					if(res){
						that.remove();
					}
				});
			});
			
			$(".btn_addKwsAtch", that.selector).click(function(){
				prtsAreaObj.ptimHtObj.addAtchFile.open(that.impIdn, that.ftrCde, that.parentDataNo);
			});
			
			$(".btn_removeKwsAtch", that.selector).click(function(){
				$.messager.confirm(that.TITLE, '부속자료를 삭제하시겠습니까?',function(res){
					if(res){
						that.removeAtchFile();
					}
				});
			});
			
			$(".btn_closePrtsAreaHt", that.selector).click(function(){
				that.close();
			});
			
			$("#chkAll", that.selector).change(function(){
				if($("#chkAll", that.selector).is(":checked")){
					$(".chkBox_kwsAtch").prop("checked", true);
				}else{
					$(".chkBox_kwsAtch").prop("checked", false);
				}
			});
			
			$(that.selector).on("change", ".chkBox_kwsAtch", function(){
				var isAllChked = true;
				var chkBoxes = $(".chkBox_kwsAtch", that.selector);
				for(var i = 0; i < chkBoxes.length; i++){
					if(!chkBoxes[i].checked){
						isAllChked = false;
					}
				}
				
				if(isAllChked){
					$("#chkAll", that.selector).prop("checked", true);
				}else{
					$("#chkAll", that.selector).prop("checked", false);
				}
			});
			
			$(".btn_downloadKwsAtch", that.selector).click(function(){
				that.fileDownload();
			});
			
			$(that.selector).on("click", ".fileRow td:not(:first-child)", function(){
				var fileRow = $(this);
				var fileNo = fileRow.parent().children("td").eq(0).children(".chkBox_kwsAtch").val();
				
				that.previewFile(fileNo);
			});
			
			$(that.selector).on("mouseenter",".fileRow", function(){
				var fileRow = $(this);
				fileRow.children().css('background-color','#E6E6E6');
			});
			
			$(that.selector).on("mouseleave",".fileRow", function(){
				var fileRow = $(this);
				fileRow.children().css('background-color','#FFFFFF');
			});
		},
		
		previewFile : function(fileNo){
			var fileChkUrl = CONTEXT_PATH + '/cmmn/file/' + fileNo + '/select.do';
			var url = CONTEXT_PATH + '/atchManage/' + fileNo + '/previewAtchFile.do';
			
			$.get(fileChkUrl).done(function(response){
				if(response){
					var fileExtsn = response.data.fileExtsn.toLowerCase();			
					var extensions = ['jpg', 'jpeg', 'bmp', 'gif', 'png', 'pdf'];	
					
					if(extensions.indexOf(fileExtsn) == -1){			
						$.messager.alert('부속자료 미리보기', '다음 확장자만 미리보기가 가능합니다. (' + extensions.join(', ') + ')');
						return false;
					}else if(extensions.indexOf(fileExtsn) > -1){	
						window.open(url);
					}
				}
			});
		},
		
		fileDownload : function(){
			var that = this;
			
			var chkedBoxes =$("input:checkbox[class=chkBox_kwsAtch]:checked"); 
			
			if(chkedBoxes.length == 0){
				$.messager.alert(that.TITLE, '부속자료를 하나 이상 선택해주세요.');
				return false;
			}
			$("#chkedForm", that.selector).attr('action', CONTEXT_PATH + '/atchManage/' + prtsAreaObj.ptimHtObj.dataId + '/downloadAtchFiles.do');
			$("#chkedForm", that.selector).attr('method', 'GET');
			
			$("#chkedForm", that.selector).submit();
		},
		
		removeAtchFile : function(){
			var that = this;
			
			var chkBoxes = $(".chkBox_kwsAtch", that.selector);
			var fileNos = [];
			
			for(var i = 0; i < chkBoxes.length; i++){
				if(chkBoxes[i].checked){
					fileNos.push(chkBoxes[i].value);
				}
			}
			
			if(fileNos.length == 0){
				$.messager.alert('부속자료 삭제', '부속자료를 하나 이상 선택해주세요.');
				return false;
			}
			
			var url = CONTEXT_PATH + '/atchManage/deleteAtchFiles.do';
			
			$.ajax({
				type : 'POST',
				data : {
					fileNos : fileNos
				},
				dataType : 'json',
				url : url,
				success : function(result){
					if(result){
						$.messager.alert(that.TITLE, '부속자료가 삭제되었습니다.', null, function(){
							that.open(that.impIdn, that.parentDataNo, 1);
						});
					}else{
						$.messager.alert(that.TITLE, '부속자료 삭제에 실패하였습니다.');
					}
				},
				error : function(err){
					console.log(err);
				}
			});
			
			
		},
		
		remove : function(){
			var that = this;
			
			var url = CONTEXT_PATH + '/prtsArea/rdtPtimHtDelete.do';
			$.ajax({
				url : url,
				type : 'POST',
				dataType : 'json',
				data : {
					impIdn : that.impIdn
				},
				//async : true,
				success : function(rs){
					var parentDataNo = that.parentDataNo;
					var prtsAreaDataId = prtsAreaObj.prtsAreaResultObj.dataId;
					$.messager.alert(that.TITLE, '보호구역 개선사업이력이 삭제되었습니다.', null, function(){
						prtsAreaObj.prtsAreaResultObj.open(prtsAreaDataId, parentDataNo, 1);
						that.close();
					});
					
				},error : function(err){
					console.log(err);
				}
				
			});
		},
		
		initUi : function(tabIndex){
			var that = this;
			
			$('.btn_addKwsAtch', that.selector).hide();
			$('.btn_removeKwsAtch', that.selector).hide();
			$('.btn_downloadKwsAtch', that.selector).hide();
			
			$(".div_window_prtsAreaHt_result_tabs", that.selector).tabs({
				onSelect : function(title, index){
					if(title.indexOf('부속자료') > -1 && index == '1'){
						$('.btn_modifyPrtsAreaHt', that.selector).hide();
						$('.btn_removePrtsAreaHt', that.selector).hide();
						
						$('.btn_addKwsAtch', that.selector).show();
						$('.btn_removeKwsAtch', that.selector).show();
						$('.btn_downloadKwsAtch', that.selector).show();
					}
				},
				onUnselect : function(title, index){
					if(title.indexOf('부속자료') > -1 && index == '1'){
						$('.btn_modifyPrtsAreaHt', that.selector).show();
						$('.btn_removePrtsAreaHt', that.selector).show();
						
						$('.btn_addKwsAtch', that.selector).hide();
						$('.btn_removeKwsAtch', that.selector).hide();
						$('.btn_downloadKwsAtch', that.selector).hide();
					}
				}
			});
			
			$(".div_window_prtsAreaHt_result_tabs", that.selector).tabs('select', tabIndex);
			
			$('.table_text > tbody > tr > th', that.selector).css('text-align','center');
		},
		
		load : function(){
			var that = this;
			
			$.ajax({
				url : CONTEXT_PATH + '/prtsArea/' + that.impIdn + '/rdtPtimHtSelectOne.do',
				dataType : 'json',
				type : 'GET',
				success : function(rs){
					if(rs || rs.row.length > 0){
						var row = rs.row[0];
						var oKeys = Object.keys(row);
						
						var dataInfo = dataObj.getDataByDataId(prtsAreaObj.ptimHtObj.dataId, true);
						var dataFields = dataInfo.kwsDataFields;
						
						if(row['ftrCde'] != null || row['ftrCde'] != ''){
							that.ftrCde = row['ftrCde'];
						}
						
						for(var i = 0 ; i < oKeys.length; i++){
							for(var j = 0; j < dataFields.length; j++){
								
								$('.select_prtsAreaHt_' + oKeys[i], that.selector).html(row[oKeys[i]]);
								
								if(camelcaseUtils.underbarToCamelcase(dataFields[j].fieldId).indexOf(oKeys[i]) > -1 && dataFields[j].indictTy.indexOf('DOMAIN') > -1){
									var codeData = domnCodeObj.getCode(dataFields[j].domnId, row[oKeys[i]]);
									var value = codeData.codeNm;
									
									$('.select_prtsAreaHt_' + oKeys[i], that.selector).html(value);
									break;
								}
							}
						}
						that.loadFileList();
					}
				},
				error : function(err){
					console.log(err);
				}
			});
		},
		
		loadFileList : function(){
			var that = this;
			
			var url = CONTEXT_PATH + '/atchManage/' + that.impIdn + '/' + that.ftrCde + '/listByFtr.do';
			
			var tagStr = '';
			
			$.ajax({
				url : url,
				dataType : 'json',
				type : 'GET',
				success : function(rs){
					if(rs && rs.rows.length > 0){
						for(var i = 0; i < rs.rows.length; i++){
							var row = rs.rows[i];
							
							tagStr += '<tr class="fileRow">';
							tagStr += '<td>';
							tagStr += '<input type="checkbox" name="fileNos" class="chkBox_kwsAtch" value="' + row.atchFileNo +'">';
							tagStr += '</td>';
							tagStr += '<td>' + row.atchNo;
							tagStr += '</td>';
							tagStr += '<td>' + row.atchSj;
							tagStr += '</td>';
							tagStr += '<td>' + row.atchCn;
							tagStr += '</td>';
							/*tagStr += '<td>' + row.rmkExp;
							tagStr += '</td>';*/
						}
						
						$('.div_kwsAtch tbody', that.selector).append(tagStr);
						
						$('.div_kwsAtch .table_text > tbody > tr > td', that.selector).css('text-align','center');
						$('.div_kwsAtch .table_text > tbody > tr > td', that.selector).css('min-width','10px');
					}
				},
				error : function(err){
					console.log(err);
				}
			});
		},
		
		close : function(){
			var that = this;
			
			windowObj.removeWindow(that.selector);
			//that.selector = null;
			/*that.impIdn = null;
			isCreated = false;
			that.parentDataNo = null;*/
		}
};

prtsAreaObj.ptimHtObj.insertObj = {
		
		TITLE : '보호구역 개선사업이력 등록',
		
		CLASS_NAME : 'PrtsAreaHtInsert',
		
		parentData : {},
		
		selector : null,
		
		open : function(ftrCde, ftrIdn, parentDataId, parentDataNo){
			var that = this;
			
			that.parentData['ftrCde'] = ftrCde;
			that.parentData['ftrIdn'] = ftrIdn;
			that.parentData['dataId'] = parentDataId;
			that.parentData['dataNo'] = parentDataNo;
			
			if(that.selector){
				that.close();
			}
			
			var windowOptions = {
					width : 665,
					height : 245,
					top : 120,
					left : 330,
					isClose : function(){
						that.close();
					}
				};
			
			var url = CONTEXT_PATH + '/prtsArea/rdtPtimHtInsertPage.do';
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function(){
				that.initUi();
				that.init();
				that.bindEvents();
			});
			
			that.selector = '#' + id;
		},
		
		initUi : function(){
			var that = this;
			
			$(".div_window_prtsAreaHt_register_tabs", that.selector).tabs();
			
			$(".register_prtsAreaHt_prjNam", that.selector).textbox({width:220});
			$(".register_prtsAreaHt_cttYmd", that.selector).datebox({width:202});
			$(".register_prtsAreaHt_begYmd", that.selector).datebox({width:220});
			$(".register_prtsAreaHt_rfnYmd", that.selector).datebox({width:202});
			$(".register_prtsAreaHt_cntDes", that.selector).textbox({width:527});
			
			$(".register_prtsAreaHt_atchSj", that.selector).textbox({width:527});
			$(".register_prtsAreaHt_atchCn", that.selector).textbox({width:527});
			
		},
		
		init : function(){
			var that = this;
			
			$("#form_prtsAreaHt", that.selector).ajaxForm({
				dataType : 'json',
				beforeSubmit : function(arr, form){
					var prjNam = $(".register_prtsAreaHt_prjNam", that.selector).textbox('getValue');
					
					if(prjNam == '' && prjNam == null){
						$.messager.alert(that.TITLE, '사업명을 입력해주세요.');
						return false;
					}
				},
				success : function(result){
					that.close();
					$.messager.alert(that.TITLE, '보호구역 개선사업이력이 등록되었습니다.', null, function(){
						prtsAreaObj.prtsAreaResultObj.open(that.parentData.dataId, that.parentData.dataNo, 1);
					});
				}
			});
		},
		
		bindEvents : function(){
			var that = this;
			
			$(".btn_savePrtsAreaHt", that.selector).click(function(){
				that.insert();
			});
			
			$(".btn_closePrtsAreaHt", that.selector).click(function(){
				that.close();
			});
		},
		
		insert : function(){
			var that = this;
			
			var codeData = domnCodeObj.getCodeByValue('KWS-000', that.parentData.ftrCde);
			
			var ftrCodeData = dataFtrCdeObj.getData(prtsAreaObj.ptimHtObj.dataId);
			var ftrCde = '';
			
			if(ftrCodeData.length == 1){
				ftrCde = ftrCodeData[0].codeId;
			}else{
				ftrCde = [];
				
				for(var i = 0; i < ftrCodeData.length; i++){
					ftrCde.push(ftrCodeData[i].codeId);
				}
			}
			
			$(".register_prtsAreaHt_ftrCde", that.selector).val(ftrCde);
			$(".register_prtsAreaHt_ftfCde", that.selector).val(codeData.codeId);
			$(".register_prtsAreaHt_ftfIdn", that.selector).val(that.parentData.ftrIdn);
			
			$("#form_prtsAreaHt", that.selector).attr('action', CONTEXT_PATH + '/prtsArea/rdtPtimHtInsert.do');
			$("#form_prtsAreaHt", that.selector).attr('method','post');
			
			$("#form_prtsAreaHt", that.selector).submit();
		},
		
		close : function(){
			var that = this;
			
			windowObj.removeWindow(that.selector);
			
			that.ftfCde = null;
			that.ftfIdn = null;
			that.parentDataId = null;
			that.selector = null;
		}
		
};

prtsAreaObj.ptimHtObj.updateObj = {
		TITLE : '보호구역 개선사업이력 편집',
		
		CLASS_NAME : 'prtsAreaHtModify',
		
		selector : null,
		
		parentDataNo : null,
		
		impIdn : null,
		
		orgPrtsAreaHtData : null,
		
		open : function(parentDataNo, impIdn){
			var that = this;
			
			if(that.selector){
				that.close();
			}
			
			that.parentDataNo = parentDataNo;
			that.impIdn = impIdn;
			
			var windowOptions = {
					width : 665,
					height : 245,
					top : 120,
					left : 330,
					isClose : function(){
						that.close();
					}
				};
			
			var url = CONTEXT_PATH + '/prtsArea/rdtPtimHtUpdatePage.do';
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function(){
				that.initUi();
				that.init();
				that.bindEvents();
			});
			
			that.selector = '#' + id;
		},
		
		initUi : function(){
			var that = this;
			
			$('.div_window_prtsAreaHt_modify_tabs', that.selector).tabs();
			
			$(".modify_prtsAreaHt_prjNam", that.selector).textbox({width:220});
			$(".modify_prtsAreaHt_cttYmd", that.selector).datebox({width:202});
			$(".modify_prtsAreaHt_begYmd", that.selector).datebox({width:220});
			$(".modify_prtsAreaHt_rfnYmd", that.selector).datebox({width:202});
			$(".modify_prtsAreaHt_cntDes", that.selector).textbox({width:527});
		},
		
		init : function(){
			var that = this;
			
			$.ajax({
				url : CONTEXT_PATH + '/prtsArea/' + that.impIdn + '/rdtPtimHtSelectOne.do',
				dataType : 'json',
				data : {
					impIdn : that.impIdn
				},
				type : 'GET',
				async : true,
				success : function(rs){
					if(rs || rs.row.length > 0){
						
						that.orgPrtsAreaHtData = rs.row[0];
						var row = rs.row[0];
						
						$(".modify_prtsAreaHt_prjNam", that.selector).textbox('setValue', row['prjNam']);
						$(".modify_prtsAreaHt_cttYmd", that.selector).datebox('setValue', row['cttYmd']);
						$(".modify_prtsAreaHt_begYmd", that.selector).datebox('setValue', row['begYmd']);
						$(".modify_prtsAreaHt_rfnYmd", that.selector).datebox('setValue', row['rfnYmd']);
						$(".modify_prtsAreaHt_cntDes", that.selector).textbox('setValue', row['cntDes']);
					}
				},
				error : function(err){
					console.log(err);
				}
			});
		},
		
		bindEvents : function(){
			var that = this;
			
			$('.btn_savePrtsAreaHt', that.selector).click(function(){
				that.update();
			});
			
			$('.btn_closePrtsAreaHt', that.selector).click(function(){
				that.close();
			});
		},
		
		update : function(){
			var that = this;
			
			var data = {};
			
			data['impIdn'] = that.orgPrtsAreaHtData.impIdn;
			data['ftrCde'] = that.orgPrtsAreaHtData.ftrCde;
			data['ftfCde'] = that.orgPrtsAreaHtData.ftfCde;
			data['ftfIdn'] = that.orgPrtsAreaHtData.ftfIdn;
			data['prjNam'] = $(".modify_prtsAreaHt_prjNam", that.selector).textbox('getValue');
			data['cttYmd'] = $(".modify_prtsAreaHt_cttYmd", that.selector).datebox('getValue');
			data['begYmd'] = $(".modify_prtsAreaHt_begYmd", that.selector).datebox('getValue');
			data['rfnYmd'] = $(".modify_prtsAreaHt_rfnYmd", that.selector).datebox('getValue');
			data['cntDes'] = $(".modify_prtsAreaHt_cntDes", that.selector).textbox('getValue');
			
			$.ajax({
				url : CONTEXT_PATH + '/prtsArea/rdtPtimHtUpdate.do',
				dataType : 'json',
				type : 'POST',
				data : data,
				success : function(rs){
					$.messager.alert(that.TITLE, '보호구역 개선사업이력이 편집되었습니다.', null, function(){
						prtsAreaObj.prtsAreaResultObj.open(prtsAreaObj.dataId, that.parentDataNo, 1);
						prtsAreaObj.ptimHtObj.selectOneObj.open(that.impIdn, prtsAreaObj.ptimHtObj.selectOneObj.parentDataNo);
						that.close();
					});
				},
				error : function(err){
					console.log(err);
				}
			});
			
		},
		
		close : function(){
			var that = this;
			
			windowObj.removeWindow(that.selector);
			that.impIdn = null;
			that.parentDataNo = null;
			that.selector = null;
		}
};

prtsAreaObj.ptimHtObj.addAtchFile = {
	
		TITLE : '부속자료 등록',
		
		CLASS_NAME : 'ptimHtAddAtchFile',
		
		selector : null,
		
		ptimImpIdn : null,
		
		ptimFtrCde : null,
		
		parentDataNo : null,
		
		open : function(ptimImpIdn, ptimFtrCde, parentDataNo){
			var that = this;
			
			if(that.selector){
				that.close();
			}
			
			that.ptimImpIdn = ptimImpIdn;
			that.ptimFtrCde = ptimFtrCde;
			that.parentDataNo = parentDataNo;
			
			var windowOptions = {
					width : 665,
					height : 245,
					top : 120,
					left : 330,
					isClose : function(){
						that.close();
					}
				};
			
			var url = CONTEXT_PATH + '/prtsArea/insertAtchFilePage.do';
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function(){
				that.initUi();
				that.init();
				that.bindEvents();
			});
			
			that.selector  = '#' + id;
		},
		
		initUi : function(){
			var that = this;
			
			$(".registert_atchSj", that.selector).textbox({width:527});
			$(".register_atchCn", that.selector).textbox({width:527});
			$(".register_atchRmkExp", that.selector).textbox({width:527});
		},
		
		init : function(){
			var that = this;
			
			$("form", that.selector).ajaxForm({
				dataType : 'json',
				beforeSubmit : function(arr, form){
					for(var i = 0; i < arr.length; i++){
						var obj = arr[i];
						var name = obj.name;
						
						if(name == 'atchSj'){
							if(!obj.value){
								$.messager.alert(that.TITLE, '제목을 입력하여 주십시오.', function(){
									form.find('input[name='+ name +']').focus();
								});
								return false;
							}
						}else if(name == 'fileNm'){
							if(!obj.value){
								$.messager.alert(that.TITLE, '파일을 등록하여 주십시오.');
								return false;
							}
						}
					}
				},
				success : function(rs){
					var ptimImpIdn = that.ptimImpIdn;
					
					$.messager.alert(that.TITLE, '부속자료가 등록되었습니다.', null, function(){
						prtsAreaObj.ptimHtObj.selectOneObj.open(ptimImpIdn, prtsAreaObj.ptimHtObj.selectOneObj.parentDataNo, 1);
					});
					
					that.close();
				}
			});
		},
		
		bindEvents : function(){
			var that = this;
			
			$('.btn_savePrtsAreaHt_atch', that.selector).click(function(){
				that.insert();
			});
			
			$('.btn_closePrtsAreaHt_atch', that.selector).click(function(){
				that.close();
			});
		},
		
		insert : function(){
			var that = this;
			
			var url = CONTEXT_PATH + '/atchManage/'+ that.ptimImpIdn +'/' + that.ptimFtrCde + '/insertAtchFile.do';
			
			$("form", that.selector).attr('method', 'post');
			$("form", that.selector).attr('action', url);
			$("form", that.selector).attr('enctype','multipart/form-data');
			
			$("form", that.selector).submit();
		},
		
		close : function(){
			var that = this;
			
			windowObj.removeWindow(that.selector);
			that.ptimImpIdn = null;
			that.ptimFtrCde = null;
			that.selector = null;
		}
		
};