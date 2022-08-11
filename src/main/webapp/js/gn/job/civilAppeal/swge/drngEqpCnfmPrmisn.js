/**
 * 배수설비인허가 객체
 */

// 배수설비인허가대장 검색
drngEqpCnfmPrmisnObj = {
	
	TITLE : "배수설비인허가대장 검색",
	
	CLASS_NAME : "DrngEqpCnfmPrmisnSearch",
	
	DATA_ID : 'SWT_SPMT_MA',
	
	selector : null,
	
	isCreated : false,
	
	//수정자 : 이승재, 2019.11.19
	initUi : function() {
		var that = this;
		
		// easyUi init
		$('#searchDrngEqpCnfmPrmisn .easyui-numberbox', that.selector).numberbox();
		$('#searchDrngEqpCnfmPrmisn .easyui-textbox', that.selector).textbox();
		$('#searchDrngEqpCnfmPrmisn .easyui-combobox', that.selector).combobox();
		
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
	
	//수정자 : 이승재, 2019.11.22
	bindEvents : function() {
		var that = this;
		
		//동선택 콤보박스
		$('.sel_dong', that.selector).combobox({
			onChange : function(newValue, oldValue) {
				//강릉시가 선택되면 주소 입력 불가
				if (newValue == '4215000000') {
					$(".chk_mntn", that.selector).prop('checked', false);
					$('.txt_mnnm', that.selector).numberbox('clear');
					$('.txt_slno', that.selector).numberbox('clear');
					
					$(".chk_mntn", that.selector).prop("disabled", true);
					$('.txt_mnnm', that.selector).numberbox({
						disabled : true
						});
					$('.txt_slno', that.selector).numberbox({
						disabled : true
						});
				} else {
					$(".chk_mntn", that.selector).prop("disabled", false);
					$('.txt_mnnm', that.selector).numberbox({
						disabled : false
						});
					$('.txt_slno', that.selector).numberbox({
						disabled : false
						});
				}
			}
		});
		
		// 초기화
		$(".a_reset_DrngEqpCnfmPrmisnSearch", that.selector).on("click", function() {
			$(".chk_mntn", that.selector).prop('checked', false);
			$('#searchDrngEqpCnfmPrmisn .easyui-numberbox', that.selector).numberbox('clear');
			$('#searchDrngEqpCnfmPrmisn .easyui-textbox', that.selector).textbox('clear');
			$('.sel_dong', that.selector).combobox('select', '4215000000');
			$('.pmsCde', that.selector).combobox('clear');

			return false;
		});
		
		// 검색
		$(".a_search_DrngEqpCnfmPrmisnSearch", that.selector).on("click", function() {
			that.search();
			
			return false;
		});
	},
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/civilAppeal/swge/searchDrngEqpCnfmPrmisnPage.do";
		var windowOptions = {
			width : 560,
			height : 179,
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
	
	//수정자 : 이승재, 2019.12.06
	search : function() {
		var that = this;
		
		var cql = that.createCqlFilter();
		
		var filter = {
				filterType : 'CQL',
				cql : cql
		};
		spatialSearchUtils.listAllSummary(that.TITLE, that.DATA_ID, filter, function(data) {
			if(data && data.ids && data.ids.length > 0) {
				var option = {
						dataId : data.dataId,
						menuObj : drngEqpCnfmPrmisnObj.resultObj.modifyMenu,
						eventObj : drngEqpCnfmPrmisnObj.resultObj.bindEvents
				};

				resultObj.open([data], [option]);
				mapToolObj.deactive();
				that.close();
			} else {
				$.messager.alert(that.TITLE, "검색 결과가 없습니다.");
			}
		});
	},
	
	//작성자 : 이승재, 2019.12.06
	createCqlFilter : function() {
		var that = this;
		
		var cql = '1 = 1';
		
		var dongCode = $(".sel_dong", that.selector).combobox("getValue");
		if (dongCode != '4215000000' && dongCode != '') {
			cql += " AND RPST_BJDCD = '" + dongCode + "'";
		} else {
			dongCode = '';
		}
		
		var mnnm = $(".txt_mnnm", that.selector).numberbox("getValue");
		if (mnnm != '') {
			cql += " AND RPST_MNNM = '" + mnnm +"'";
		}
		
		var slno = $(".txt_slno", that.selector).numberbox("getValue");
		if (slno != '') {
			cql += " AND RPST_SLNO = '" + slno +"'";
		}
		
		var mntn = $(".chk_mntn", that.selector).is(":checked")?"2":"1";
		if (dongCode != '' || mnnm != '' || slno != '' || mntn == '2') {
			cql += " AND RPST_MTLNB = '" + mntn +"'";
		}

		var pmsCde = $(".pmsCde", that.selector).combobox("getValue");
		if (pmsCde != '') {
			cql += " AND PMS_CDE = '" + pmsCde +"'";
		}
		
		var budNam = $(".budNam", that.selector).textbox("getValue");
		if (budNam != '') {
			cql += " AND BUD_NAM = '" + budNam +"'";
		}
		
		return cql;
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
};

//검색결과창 컨트롤
//작성자 : 이승재, 2019.12.10
drngEqpCnfmPrmisnObj.resultObj = {
		//작성자 : 이승재, 2019.12.06
		modifyMenu : function(dataId) {
			var that = this;
			
			var data = dataObj.getDataByDataId(dataId);
			var dataAuthor = dataAuthorObj.getDataAuthor(dataId);
			
			/*
			 * 검색결과 목록 가공(추가선택, 선택제거) 버튼 활성화
			 */
			$(".a_addition_choice").show();
			$(".a_remove_rows").show();
			$(".a_search_condition").show();
			$(".a_search_relation").hide();
			
			/*
			 * 목록 위치보기 버튼 활성화
			 */
			$(".a_move_location").show();
			$(".a_export_fix").hide();
			
			/*
			 * 공사위치 버튼 사용 여부
			 */
			$(".a_corporation_location").hide();
			
			/*
			 * 데이터 Expot 버튼 활성화
			 */
			if (data.exportAuthorAt == 'Y' && dataAuthor && dataAuthor.exportAt == 'Y') {
				$(".a_export_excel", that.selector).show();
				$(".a_export_shape", that.selector).hide();
			} else {
				$(".a_export_excel", that.selector).hide();
				$(".a_export_shape", that.selector).hide();
			}
		},
		
		//작성자 : 이승재, 2019.12.09
		bindEvents : function() {
			var that = this;
			
			// 목록 위치
			$(".a_move_location", that.selector).off();
			$(".a_move_location", that.selector).click(function() {
				drngEqpCnfmPrmisnObj.resultObj.moveLocation();
				return false;
			});
			
			// 대장 조회
			$(".a_search_register", that.selector).off();
			$(".a_search_register", that.selector).click(function() {
				drngEqpCnfmPrmisnObj.resultObj.searchRegister();
				return false;
			});
		},
		
		//작성자 : 이승재, 2019.12.09
		moveLocation : function() {
			var that = this;
			
			var selected = $(".grid_result_detail", that.selector).datagrid("getSelected");
			var rpstBjdcd = selected["rpstBjdcd"];
			var rpstMnnm = selected["rpstMnnm"];
			
			if (rpstBjdcd == '' || rpstMnnm == '') {
				$.messager.alert(that.TITLE, "지번이 옳바르지 않습니다.");
				return false;
			}
			
			var ftrIdn = selected["ftrIdn"];
			var url = CONTEXT_PATH + "/civilAppeal/swge/ " + ftrIdn + "/searchLocation.do";
			$.get(url).done(function(response) {
				var features = [];
				
				if (response && response.result && response.result.length > 0) {
					for (var i = 0; i < response.result.length; i++) {
						var geom = response.result[i].geom;
						var format = new ol.format.WKT();
						var feature = format.readFeature(geom);
						features.push(feature);
					}
					highlightObj.showRedFeatures(that.CLASS_NAME, features, true, {
						projection : ol.proj.get(MAP.PROJECTION)
					});
				} else {
					$.messager.alert(that.TITLE, "해당 필지가 존재하지 않습니다.");
				}
			});
	
		},
		
		searchRegister : function() {
			var that = this;
			var selected = $(".grid_result_detail", that.selector).datagrid("getSelected");
			if (selected != null) {
				var ftrIdn = selected["ftrIdn"];
				drngEqpCnfmPrmisnObj.view.open(ftrIdn);
			} else {
				$.messager.alert(that.TITLE, '선택된 배수설비인허가대장이 없습니다.');
			}
		}
},

/// 배수설비인허가대장 조회
/// 이승재, 2020.07.28
/// 작성자 : 이승재, 2019.12.10
drngEqpCnfmPrmisnObj.view = {
		
		TITLE : '배수설비인허가대장 상세정보',
		
		CALSS_NAME : 'drngEqpCnfmPrmisnDetail',
		
		DATA_ID : 'SWT_SPMT_MA',
		
		selector : null,
		
		isCreated : false,
		
		newAtchflList : null,
		
		initUi : function() {
			var that = this;

			var data = dataObj.getDataByDataId(that.DATA_ID);
			var dataAuthor = dataAuthorObj.getDataAuthor(that.DATA_ID);
			if (data.editAt == 'Y' && dataAuthor && dataAuthor.editAt == 'Y') {
				$('.btn_modify_drngEqpCnfmPrmisnDetail', that.selector).show();
				$('.btn_delete_drngEqpCnfmPrmisnDetail', that.selector).show();
			} else {
				$('.btn_modify_drngEqpCnfmPrmisnDetail', that.selector).hide();
				$('.btn_delete_drngEqpCnfmPrmisnDetail', that.selector).hide();
			}
			
			$('.btn_fileDownload_drngEqpCnfmPrmisnDetail', that.selector).hide();
			$('.btn_fileAdd_drngEqpCnfmPrmisnDetail', that.selector).hide();
			$('.btn_fileDelete_drngEqpCnfmPrmisnDetail', that.selector).hide();
			
			$('.btn_spotEdit_drngEqpCnfmPrmisnDetail', that.selector).hide();
			$('.btn_connEdit_drngEqpCnfmPrmisnDetail', that.selector).hide();
		},
		
		
		bindEvents : function() {
			var that = this;
			
			// tab 이벤트
			$(".tab-list-1 li a", that.selector).on("click", function() {
				$(".tab-list-1 li, .tab-panel-1", that.selecor).removeClass("active");
				$(this, that.selector).parent().addClass("active");
				$(".tab-panel-1", that.selector).eq($(this, that.selector).parent().index()).addClass("active");

				var tabIndexNumber = $(this, that.selector).parent().index();
				var data = dataObj.getDataByDataId(that.DATA_ID);
				var dataAuthor = dataAuthorObj.getDataAuthor(that.DATA_ID);
				var pmsCde = document.getElementById('drngEqpCnfmPrmisnDetailPmsCde').value;
				switch (tabIndexNumber) {
					case 2:
						if (data.editAt == 'Y' && dataAuthor && dataAuthor.editAt == 'Y') {
							if (pmsCde == 'PMS012') {
								$('.btn_spotEdit_drngEqpCnfmPrmisnDetail', that.selector).show();
								$('.btn_connEdit_drngEqpCnfmPrmisnDetail', that.selector).show();
							} else {
								$('.btn_spotEdit_drngEqpCnfmPrmisnDetail', that.selector).hide();
								$('.btn_connEdit_drngEqpCnfmPrmisnDetail', that.selector).hide();
							}
							$('.btn_modify_drngEqpCnfmPrmisnDetail', that.selector).show();
							$('.btn_delete_drngEqpCnfmPrmisnDetail', that.selector).show();
						} else {
							$('.btn_modify_drngEqpCnfmPrmisnDetail', that.selector).hide();
							$('.btn_delete_drngEqpCnfmPrmisnDetail', that.selector).hide();
						}
				
						$('.btn_fileDownload_drngEqpCnfmPrmisnDetail', that.selector).hide();
						$('.btn_fileAdd_drngEqpCnfmPrmisnDetail', that.selector).hide();
						$('.btn_fileDelete_drngEqpCnfmPrmisnDetail', that.selector).hide();
						break;
					case 3:
						$('.btn_modify_drngEqpCnfmPrmisnDetail', that.selector).hide();
						$('.btn_delete_drngEqpCnfmPrmisnDetail', that.selector).hide();
						
						$('.btn_fileDownload_drngEqpCnfmPrmisnDetail', that.selector).show();
						$('.btn_fileAdd_drngEqpCnfmPrmisnDetail', that.selector).show();
						$('.btn_fileDelete_drngEqpCnfmPrmisnDetail', that.selector).show();
						
						$('.btn_spotEdit_drngEqpCnfmPrmisnDetail', that.selector).hide();
						$('.btn_connEdit_drngEqpCnfmPrmisnDetail', that.selector).hide();
						break;
					default:
						if (data.editAt == 'Y' && dataAuthor && dataAuthor.editAt == 'Y') {
							$('.btn_modify_drngEqpCnfmPrmisnDetail', that.selector).show();
							$('.btn_delete_drngEqpCnfmPrmisnDetail', that.selector).show();
						} else {
							$('.btn_modify_drngEqpCnfmPrmisnDetail', that.selector).hide();
							$('.btn_delete_drngEqpCnfmPrmisnDetail', that.selector).hide();
						}
				
						$('.btn_fileDownload_drngEqpCnfmPrmisnDetail', that.selector).hide();
						$('.btn_fileAdd_drngEqpCnfmPrmisnDetail', that.selector).hide();
						$('.btn_fileDelete_drngEqpCnfmPrmisnDetail', that.selector).hide();
						
						$('.btn_spotEdit_drngEqpCnfmPrmisnDetail', that.selector).hide();
						$('.btn_connEdit_drngEqpCnfmPrmisnDetail', that.selector).hide();
						break;
				}
			});
			
			//물받이 편집
			$('.btn_spotEdit_drngEqpCnfmPrmisnDetail', that.selector).on('click', function() {
				$(that.selector).window('collapse', true);
				var option = {
						onSave : function(feature) {
							that.addDrngEqpConnect(feature);
						} ,
						onClose : function() {
							$(that.selector).window('expand', true);
						}
				};
				editObj.open('SWL_SPOT_PS', '물받이', 'Point', option);
				return false;
			});
			
			//연결관 편집
			$('.btn_connEdit_drngEqpCnfmPrmisnDetail', that.selector).on('click', function() {
				$(that.selector).window('collapse', true);
				var option = {
						onSave : function(feature) {
							that.addDrngEqpConnect(feature);
						} ,
						onClose : function() {
							$(that.selector).window('expand', true);
						}
				};
				editObj.open('SWL_CONN_LS', '하수연결관', 'LineString', option);
				return false;
			});
			
			//편집
			$('.btn_modify_drngEqpCnfmPrmisnDetail', that.selector).on('click', function() {
				var ftrIdn = document.getElementById('drngEqpCnfmPrmisnDetailFtrIdn').value;
				drngEqpCnfmPrmisnObj.modify.open(ftrIdn);
				that.close();
				return false;
			});
			
			//삭제
			$('.btn_delete_drngEqpCnfmPrmisnDetail', that.selector).on('click', function() {
				$.messager.confirm(that.TITLE, "삭제하시겠습니까?", function(isTrue) {
					if(isTrue) {
						that.remove();
					}
				});
				return false;
			});
			
			//닫기
			$('.btn_close_drngEqpCnfmPrmisnDetail', that.selector).on('click', function() {
				that.close();
				return false;
			});
			
			//배수설비 시설물대장 조회
			$('.tableSelector_drngEqpList tr', that.selector).on('click', function() {
				var node = $(this);
				var dataId = node.attr('data-data-id');
				var objectid =  node.attr('data-objectid');
				windowObj.registerObj.open(dataId, [objectid]);
			});
			
			//첨부파일 목록 체크
			$(".tableSelector input[type=checkbox]", that.selector).on('click', function() {
				if ($(this).is(':checked')) {
					$(".tableSelector input[type=checkbox]", that.selector).prop('checked', false);
					$(this).prop('checked', true);
				}
			});
			
			//첨부파일 내려받기
			$('.btn_fileDownload_drngEqpCnfmPrmisnDetail', that.selector).on('click', function() {
				var node = $(".tableSelector input[type=checkbox]:checked", that.selector).parent().parent();
				var fileNo = node.attr("data-file-no");
				that.downloadAtchfl(fileNo);
				return false;
			});
			
			//첨부파일 등록
			$('.btn_fileAdd_drngEqpCnfmPrmisnDetail', that.selector).on('click', function() {
				var ftrCde = document.getElementById('drngEqpCnfmPrmisnDetailFtrCde').value;
				var ftrIdn = document.getElementById('drngEqpCnfmPrmisnDetailFtrIdn').value;
				drngEqpCnfmPrmisnObj.addAtchmnfl.open(ftrCde, ftrIdn, function() {
					that.updateAtchflList();
				});
				return false;
			});
			
			//첨부파일 삭제
			$('.btn_fileDelete_drngEqpCnfmPrmisnDetail', that.selector).on('click', function() {
				var node = $(".tableSelector input[type=checkbox]:checked", that.selector).parent().parent();
				var shtIdn = node.attr("data-sht-idn");
				var fileNo = node.attr("data-file-no");
				var ftrIdn = document.getElementById('drngEqpCnfmPrmisnDetailFtrIdn').value;
				that.removeAtchfl(ftrIdn, shtIdn, fileNo);
				return false;
			});
		},
		
		/// 배서설비 목록 갱신
		/// 이승재, 2020.07.28
		updateDrngEqpList : function() {
			var ftrIdn = document.getElementById('drngEqpCnfmPrmisnDetailFtrIdn').value;
			var url = CONTEXT_PATH + '/civilAppeal/swge/' + ftrIdn + '/listDrngEqp.do';
			$.get(url).done(function(result) {
				var tagStr = '<tr><td>구분</td><td>시설종류</td><td>관리번호</td></tr>';
				if (result && result.drngEqpList) {
					var list = result.drngEqpList;
					for (var i = 0; i < list.length; i++) {
						tagStr += '<tr data-data-id="' + list[i].dataId + '" data-objectid="' + list[i].objectid + '">';
						tagStr += '<td>' + list[i].dataAlias + '</td>';
						tagStr += '<td>' + list[i].prpos + '</td>';
						tagStr += '<td>' + list[i].ftrIdn + '</td>';
						tagStr += '</tr>';
					}
				}
				$(".tableSelector_drngEqpList", that.selector).empty();
				$(".tableSelector_drngEqpList", that.selector).append(tagStr);
			});
		},
		
		addDrngEqpConnect : function(feature) {		
			var ftrCde = document.getElementById('drngEqpCnfmPrmisnDetailFtrCde').value;
			var ftrIdn = document.getElementById('drngEqpCnfmPrmisnDetailFtrIdn').value;
			
			var properties = feature.getProperties();
			var ftfCde = properties['FTR_CDE'];
			var ftfIdn = properties['FTR_IDN'];
			
			var url = CONTEXT_PATH + '/civilAppeal/swge/' + ftrCde + '/' + ftrIdn + '/' + ftfCde + '/' + ftfIdn + '/connectDrngEqp.do';
			$.get(url).done(function() {
				//배수설비 목록 갱신
				that.updateDrngEqpList();
			});
		},
		
		downloadAtchfl : function(fileNo) {
			var that = this;
			
			var url = CONTEXT_PATH + '/cmmn/file/' + fileNo + '/download.do';
			$("form", that.selector).attr("action", url);
			$("form", that.selector).submit();
		},
		
		updateAtchflList : function() {
			var that = this;
			
			if (that.newAtchflList && that.newAtchflList.atchmnflList) {
				var list = that.newAtchflList.atchmnflList;
				
				var tagStr = '<tr><td></td><td>번호</td><td>제목</td><td>파일명</td></tr>';
				for (var i = 0; i < list.length; i++) {
					tagStr += '<tr data-file-no="' + list[i].fileNo + '" data-sht-idn="' + list[i].shtIdn + '">';
					tagStr += '<td> <input type="checkbox"></td>';
					tagStr += '<td>' + list[i].rownum + '</td>';
					tagStr += '<td>' + list[i].atchflSj + '</td>';
					tagStr += '<td>' + list[i].orignlFileNm + '</td>';
					tagStr += '</tr>';
				}
				$(".tableSelector", that.selector).empty();
				$(".tableSelector", that.selector).append(tagStr);

				that.newAtchflList = null;
			}
		},
		
		removeAtchfl : function(ftrIdn, shtIdn, fileNo) {
			var that = this;
			
			var url = CONTEXT_PATH + "/civilAppeal/swge/atchmnfl/" + ftrIdn + "/" + shtIdn + "/" + fileNo + "/removeAtchmnfl.do";
			$.get(url).done(function(result){
				if(result) {
					that.newAtchflList = result;
					that.updateAtchflList();
					$.messager.alert(that.TITLE, "파일 정보를 삭제하였습니다.");
				} else {
					$.messager.alert(that.TITLE, "파일 정보를 삭제하는데 실패하였습니다.");
				}
			}).fail(function(){
				$.messager.alert(that.TITLE, "파일 정보를 삭제하는데 실패하였습니다.");
			});
		},
		
		open : function(ftrIdn) {
			var that = this;
			
			if (that.selector) {
				that.close();
			}
			
			var url = CONTEXT_PATH + '/civilAppeal/swge/' + ftrIdn + '/selectOneDrngEqpCnfmPrmisn.do';			
			var windowOptions = {
					width : 708,
					height : 432,
					top : 120,
					left : 330,
					onClose : function() {
						that.close();
					}
			};
			var id = windowObj.createWindow(that.CALSS_NAME, that.TITLE, url, null, windowOptions, function() {
				if (!that.isCreated) {
					that.isCreated = true;
					
					that.initUi();
					that.bindEvents();
				}
			});
			that.selector = '#' + id;
		},
		
		close : function() {
			var that = this;
			
			that.isCreated = false;
			windowObj.removeWindow(that.selector);
		},
		
		remove : function() {
			var that = this;
			
			var ftrCde = document.getElementById('drngEqpCnfmPrmisnDetailFtrCde').value;
			var ftrIdn = document.getElementById('drngEqpCnfmPrmisnDetailFtrIdn').value;
			
			var url = CONTEXT_PATH + "/civilAppeal/swge/" + ftrCde + "/" + ftrIdn + "/removeDrngEqpCnfmPrmisn.do";
			$.post(url).done(function(result) {
				if (result && result.result) {
					if ($(resultObj.selector).is(':visible')) {
						resultObj.refreshResultLayersGrid();
					}
					that.close();
					$.messager.alert(that.TITLE, "배수설비인허가대장을 삭제하였습니다.");
				} else {
					$.messager.alert(that.TITLE, "배수설비인허가대장 삭제에 실패했습니다.");
				}
			}).fail(function() {
				$.messager.alert(that.TITLE, "배수설비인허가대장 삭제에 실패했습니다.");
			});
		}
},

//배수설비인허가대장 등록/수정 공통
// 작성자 : 이승재, 2019.12.18
drngEqpCnfmPrmisnObj.edit = {
		
		selector : null,
		
		init : function(seletor) {
			var that = this;
			
			that.selector = seletor;
		},
		
		initUi : function() {
			var that = this;
			
			// easyUi설정
			$("#drngEqpCnfmPrmisnModify .easyui-datebox", that.selector).datebox();
			$("#drngEqpCnfmPrmisnModify .easyui-textbox", that.selector).textbox();
			$("#drngEqpCnfmPrmisnModify .easyui-combobox", that.selector).combobox();
			$("#drngEqpCnfmPrmisnModify .easyui-numberbox", that.selector).numberbox();
			
			//산번지
			$('.modify_chk_mtlnb', that.selecto).prop('checked', false);
			
			//연면적
			$('#bldAra', that.selector).numberbox({
				min : 0,
				precision : 2
				});
			
			//신청면적
			$('#reqstAr', that.selector).numberbox({
				min : 0,
				precision : 2
				});
			
			//오수량
			$('#sdrVol', that.selector).numberbox({
				min : 0,
				precision : 2
				});
			
			//민원종류기타
			$('#pmsEtc', that.selector).textbox({
				disabled : true
				});
			
			//도로명주소
			$('#bildRnadr', that.selector).textbox({
				disabled : true
				});
			
			//관종류기타
			$('#mopEtc', that.selector).textbox({
				disabled : true
				});
			
			//관경기타
			$('#diaEtc', that.selector).numberbox({
				disabled : true,
				});
			
			//연장
			$('#pipLen', that.selector).numberbox({
				min : 0,
				precision : 2
				});
						
			//접합방법 기타
			$('#cnMthEtc', that.selector).textbox({
				disabled : true
				});
		},
		
		bindEvents : function() {
			var that = this;
			
			// tab 이벤트
			$(".tab-list-1 li a", that.selector).on("click", function() {
				$(".tab-list-1 li, .tab-panel-1", that.selecor).removeClass("active");
				$(this, that.selector).parent().addClass("active");
				$(".tab-panel-1", that.selector).eq($(this, that.selector).parent().index()).addClass("active");
			});
			
			//민원종류
			$('.modify_sel_pms', that.selector).combobox({
				onChange : function(newValue, oldValue) {
					if (newValue == 'PMS999') {
						$('#pmsEtc', that.selector).textbox({
							disabled : false
							});
					} else {
						$('#pmsEtc', that.selector).textbox({
							disabled : true
							});
					}
					
					if (newValue == 'PMS010') {
						//도로명주소
						$('#bildRnadr', that.selector).textbox({
							disabled : false
							});
					} else {
						//도로명주소
						$('#bildRnadr', that.selector).textbox({
							disabled : true
							});
					}
				}
			});
					
			//협의내용
			$('.a_code_list', that.selector).on('click', function() {
				var pmsCde = $('.modify_sel_pms', that.selector).combobox('getValue');
				drngEqpCnfmPrmisnObj.dscssList.open(pmsCde, function(dscssContent) {
					document.getElementById('dscssContext').innerText = dscssContent;
				});
			});
			
			//관종류
			$('.modify_sel_mop', that.selector).combobox({
				onChange : function(newValue, oldValue) {
					if (newValue == 'MOP999') {
						$('#mopEtc', that.selector).textbox({
							disabled : false
							});
					} else {
						$('#mopEtc', that.selector).textbox({
							disabled : true
							});
					}
				}
			});
			
			//관경
			$('.modify_sel_dia', that.selector).combobox({
				onChange : function(newValue, oldValue) {
					if (newValue == 'PCB999') {
						$('#diaEtc', that.selector).numberbox({
							disabled : false
							});
					} else {
						$('#diaEtc', that.selector).numberbox({
							disabled : true
							});
					}
				}
			});
			
			//접합방법
			$('.modify_sel_cnMth', that.selector).combobox({
				onChange : function(newValue, oldValue) {
					if (newValue == 'JHM999') {
						$('#cnMthEtc', that.selector).textbox({
							disabled : false
							});
					} else {
						$('#cnMthEtc', that.selector).textbox({
							disabled : true
							});
					}
				}
			});
		}
};

//배수설비인허가대장 등록
//수정자 : 이승재, 2019.12.18
drngEqpCnfmPrmisnObj.add = {
		
		selector : null,
		
		isCreated : null,
		
		CALSS_NAME : 'drngEqpCnfmPrmisnAdd',
		
		TITLE : '배수설비인허가대장 등록',
		
		FTR_CDE : 'SB981',
		
		pnu : null,
		
		init : function() {
			var that = this;
			drngEqpCnfmPrmisnObj.edit.init(that.selector);
			
			$('form', that.selector).ajaxForm({
				dataType : 'json',
				success : function(result) {
					if (result) {
						drngEqpCnfmPrmisnObj.view.open(result.ftrIdn);
						if ($(resultObj.selector).is(':visible')) {
							resultObj.refreshResultLayersGrid();
						}
						that.close();
						alert('배수설비인허가대장이 등록되었습니다.');
					} else {
						alert('배수설비인허가대장 등록에 실패하였습니다.');
					}
				}
			});
		},
		
		initUi : function() {
			var that = this;
			drngEqpCnfmPrmisnObj.edit.initUi();
			
			$("#drngEqpCnfmPrmisnModify .easyui-numberbox", that.selector).numberbox('setValue', 0);
		},
		
		bindEvents : function() {
			var that = this;
			
			drngEqpCnfmPrmisnObj.edit.bindEvents();
			
			//저장
			$('.btn_save_drngEqpCnfmPrmisnModify', that.selector).on('click', function() {
				that.save();
				return false;
			});
			
			//취소
			$('.btn_close_drngEqpCnfmPrmisnModify', that.selector).on('click', function() {
				that.close();
				return false;
			});
		},
		
		bindData : function() {
			var that =this;
			
			//pnu = 법/정읍면동10자리 + 산1자리 + 본번4자리 + 부번4자리 = 총 19자리 
			var pnu = that.pnu;
			var bjdCde = pnu.substr(0,10);
			var sanCde = pnu.substr(10,1);
			var facNum = pnu.substr(11,4).replace(/(^0+)/,"");
			var fadNum = pnu.substr(15,4).replace(/(^0+)/,"");
			
			$(".modify_sel_bjdcd", that.selector).combobox("select", bjdCde);
			
			if(sanCde != null && sanCde == 2){
				$(".modify_chk_mtlnb").prop("checked",true);
			} else {
				$(".modify_chk_mtlnb").prop("checked",false);
			}
			$('input[name=sanCde]').val(sanCde);
			
			//본번
			$(".modify_rpstMnnm").textbox('setValue', facNum);
			
			//부번
			$(".modify_rpstSlno").textbox('setValue', fadNum);
			
		},
		
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/civilAppeal/swge/addDrngEqpCnfmPrmisnPage.do";
		var windowOptions = {
				width : 708,
				height : 432,
				top : 120,
				left : 330,
				onClose : function() {
					that.close();
				}
		};
		var id = windowObj.createWindow(that.CALSS_NAME, that.TITLE, url, null, windowOptions, function() {
			if (!that.isCreated) {
				that.isCreated = true;
				that.init();
				that.initUi();
				that.bindEvents();
				if (that.pnu) {
					that.bindData();
				}
			}
		});
		that.selector = '#' + id;
	},
	
	selCbnd : function() {
		var that = this;
		
		selectObj.once(that.TITLE, "Point", "drawend", function(evt) {
			
			quickObj.searchPnu(evt.feature, that.CLASS_NAME, that.TITLE).done(function(pnu) {
				that.pnu = pnu;
				that.open();
			});
		}, false);
	},
	
	save : function(feature) {
		var that = this;
		
		document.getElementById('drngEqpCnfmPrmisnModifyFtrCde').value = that.FTR_CDE;
		document.getElementById('drngEqpCnfmPrmisnModifyrRstBjdcd').value = $('.modify_sel_bjdcd', that.selector).combobox('getValue');
		document.getElementById('drngEqpCnfmPrmisnModifyRpstMtlnb').value = $(".modify_chk_mtlnb", that.selector).is(":checked")?"2":"1";
		document.getElementById('drngEqpCnfmPrmisnModifyPmsCde').value = $('.modify_sel_pms', that.selector).combobox('getValue');
		document.getElementById('drngEqpCnfmPrmisnModifyMopCde').value = $('.modify_sel_mop', that.selector).combobox('getValue');
		document.getElementById('drngEqpCnfmPrmisnModifyDiaCde').value = $('.modify_sel_dia', that.selector).combobox('getValue');
		document.getElementById('drngEqpCnfmPrmisnModifyCnMthCde').value = $('.modify_sel_cnMth', that.selector).combobox('getValue');
		document.getElementById('drngEqpCnfmPrmisnModifyDscss').value = document.getElementById('dscssContext').innerText;
		
		var url = CONTEXT_PATH + "/civilAppeal/swge/addDrngEqpCnfmPrmisn.do";
		var enctype = "multipart/form-data";
		var method = "post";
		$("form", that.selector).attr("method", method);
		$("form", that.selector).attr("action", url);
		$("form", that.selector).attr("enctype", enctype);
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		that.isCreated = false;
		highlightObj.removeFeatures(that.CLASS_NAME);
		windowObj.removeWindow(that.selector);
	}

};

// 배수설비인허가대장 수정
// 수정자 : 이승재, 2019.12.15
drngEqpCnfmPrmisnObj.modify = {
		
		TITLE : '배수설비인허가대장 편집',
		
		CALSS_NAME : 'drngEqpCnfmPrmisnModify',
		
		isCreated : false,
		
		selector : null,
		
	init : function() {
		var that = this;
		drngEqpCnfmPrmisnObj.edit.init(that.selector);

		var ftrIdn = document.getElementById('drngEqpCnfmPrmisnModifyFtrIdn').value;
		$('form', that.selector).ajaxForm({
			dataType : 'json',
			success : function(result) {
				if (result) {
					drngEqpCnfmPrmisnObj.view.open(ftrIdn);
					if ($(resultObj.selector).is(':visible')) {
						resultObj.refreshResultLayersGrid();
					}
					that.close();
					alert('배수설비인허가대장이 수정되었습니다.');
				} else {
					alert('배수설비인허가대장 수정에 실패하였습니다.');
				}
			}
		});
	},
	
	initUi : function() {
		drngEqpCnfmPrmisnObj.edit.initUi();
	},
	
	bindEvents : function() {
		var that = this;
		drngEqpCnfmPrmisnObj.edit.bindEvents();
		
		//저장
		$('.btn_save_drngEqpCnfmPrmisnModify', that.selector).on('click', function() {
			that.save();
			return false;
		});
		
		//취소
		$('.btn_close_drngEqpCnfmPrmisnModify', that.selector).on('click', function() {
			var ftrIdn = document.getElementById('drngEqpCnfmPrmisnModifyFtrIdn').value;
			drngEqpCnfmPrmisnObj.view.open(ftrIdn);
			that.close();
			return false;
		});
	},
	
	bindData : function() {
		var that = this;
		
		var rpstBjdcd = document.getElementById('drngEqpCnfmPrmisnModifyrRstBjdcd').value;
		$('.modify_sel_bjdcd',  that.selector).combobox('select', rpstBjdcd);
		//산번지
		var rpstMtlnb = document.getElementById('drngEqpCnfmPrmisnModifyRpstMtlnb').value;
		if (rpstMtlnb == '2') {
			$('.modify_chk_mtlnb', that.selecto).prop('checked', true);
		} else {
			$('.modify_chk_mtlnb', that.selecto).prop('checked', false);
		}
		//민원종류
		var pmsCde = document.getElementById('drngEqpCnfmPrmisnModifyPmsCde').value;
		$('.modify_sel_pms',  that.selector).combobox('select', pmsCde);
		//관종류
		var mopCde = document.getElementById('drngEqpCnfmPrmisnModifyMopCde').value;
		$('.modify_sel_mop',  that.selector).combobox('select', mopCde);
		//관경
		var diaCde = document.getElementById('drngEqpCnfmPrmisnModifyDiaCde').value;
		$('.modify_sel_dia',  that.selector).combobox('select', diaCde);
		//접합방법
		var cnMthCde = document.getElementById('drngEqpCnfmPrmisnModifyCnMthCde').value;
		$('.modify_sel_cnMth',  that.selector).combobox('select', cnMthCde);
	},
	
	open : function(ftrIdn) {
		var that = this;
		
		if (that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + '/civilAppeal/swge/' + ftrIdn + '/modifyDrngEqpCnfmPrmisnPage.do';			
		var windowOptions = {
				width : 708,
				height : 432,
				top : 120,
				left : 330,
				onClose : function() {
					that.close();
				}
		};
		var id = windowObj.createWindow(that.CALSS_NAME, that.TITLE, url, null, windowOptions, function() {
			if (!that.isCreated) {
				that.isCreated = true;
				that.init();
				that.initUi();
				that.bindEvents();
			    that.bindData();
			}
		});
		that.selector = '#' + id;
	},
	
	//수정자 : 이승재, 2019.12.15
	save : function() {
		var that = this;
		
		document.getElementById('drngEqpCnfmPrmisnModifyrRstBjdcd').value = $('.modify_sel_bjdcd', that.selector).combobox('getValue');
		document.getElementById('drngEqpCnfmPrmisnModifyRpstMtlnb').value = $(".modify_chk_mtlnb", that.selector).is(":checked")?"2":"1";
		document.getElementById('drngEqpCnfmPrmisnModifyPmsCde').value = $('.modify_sel_pms', that.selector).combobox('getValue');
		document.getElementById('drngEqpCnfmPrmisnModifyMopCde').value = $('.modify_sel_mop', that.selector).combobox('getValue');
		document.getElementById('drngEqpCnfmPrmisnModifyDiaCde').value = $('.modify_sel_dia', that.selector).combobox('getValue');
		document.getElementById('drngEqpCnfmPrmisnModifyCnMthCde').value = $('.modify_sel_cnMth', that.selector).combobox('getValue');
		document.getElementById('drngEqpCnfmPrmisnModifyDscss').value = document.getElementById('dscssContext').innerText;
		
		var url = CONTEXT_PATH + "/civilAppeal/swge/modifyDrngEqpCnfmPrmisn.do";
		var enctype = "multipart/form-data";
		var method = "post";
		$("form", that.selector).attr("method", method);
		$("form", that.selector).attr("action", url);
		$("form", that.selector).attr("enctype", enctype);
		$("form", that.selector).submit();
		
	},
	
	close : function() {
		var that = this;
		
		that.isCreated = false;
		windowObj.removeWindow(that.selector);
	}
	
};

drngEqpCnfmPrmisnObj.addAtchmnfl = {
		
		isCreated : false,
		
		selector : null,
		
		TITLE : '배수설비인허가대장 첨부파일 등록',
		
		CLASS_NAME : 'drngEqpCnfmPrmisnAtchmnflAdd',
		
		onClose : null,
		
		init : function(ftrCde, ftrIdn) {
			
			var that = this;
			
			$("form", that.selector).ajaxForm({
				dataType : "json",
				beforeSubmit : function(arr, form) {
					for(var i=0, len=arr.length; i < len; i++) {
						var obj = arr[i];
						var name = obj.name;
						
						// 필수 입력 체크
						if(name == "atchmnSj") {
							if(!obj.value) {
								$.messager.alert(that.TITLE, "제목을 입력하여 주십시오.", null, function() {
									form.find("input[name=" + name + "]").focus();								
								});
								return false;
							}
						}
						else if(name == "file") {
							if(!obj.value) {
								$.messager.alert(that.TITLE, "파일을 등록하여 주십시오.");
								return false;
							}

							var file = obj.value;
							if(file instanceof String) {
								fileName = file;
							}
							else {
								fileName = file.name;
							}
						}
					}
				},
				success : function(result) {
					if (result) {
						drngEqpCnfmPrmisnObj.view.newAtchflList = result;
						
						if(that.onClose) {
							that.onClose();
						}
						that.close();
					} else {
						$.messager.alert(that.TITLE, "파일을 등록에 실패하였습니다.");
						that.close();
					}
				}
			});
				
			$("#add_drngEqpCnfmPrmisnAtchmnfl_ftr_cde").val(ftrCde);
			$("#add_drngEqpCnfmPrmisnAtchmnfl_ftr_idn").val(ftrIdn);
		},
		
		initUi : function () {
			var that = this; 
			
			// easyUi설정
			$("#add_drngEqpCnfmPrmisnAtchmnfl .easyui-textbox", that.selector).textbox();
		},
		
		bindEvents : function() {
			var that = this;
			
			// 저장
			$(".a_save_add_drngEqpCnfmPrmisnAtchmnfl", that.selector).on("click", function() {
				that.add();
				return false;
			});
			
			// 닫기
			$(".a_close_add_drngEqpCnfmPrmisnAtchmnfl", that.selector).on("click", function() {
				that.close();
				return false;
			});
		},
		
		open : function(ftrCde, ftrIdn, onClose) {
			var that = this;
			
			that.onClose = onClose;
			
			var url = CONTEXT_PATH + "/civilAppeal/swge/atchmnfl/addAtchmnflpage.do";
			var windowOptions = {
					width : 340,
					height : 155,
					top : 240,
					left : 650,
					onClose : function() {
						that.close();
					}
			};
			var id = windowObj.createWindow(that.CALSS_NAME, that.TITLE, url, null, windowOptions, function() {
				if (!that.isCreated) {
					that.isCreated = true;
					
					that.init(ftrCde, ftrIdn);
					that.initUi();
					that.bindEvents();
				}
			});
			that.selector = '#' + id;
		},
		
		add : function() {
			var that = this;
		
			var url = CONTEXT_PATH + "/civilAppeal/swge/atchmnfl/addAtchmnfl.do";
			var enctype = "multipart/form-data";
			var method = "post";
			
			$("form", that.selector).attr("method", method);
			$("form", that.selector).attr("action", url);
			$("form", that.selector).attr("enctype", enctype);
			$("form", that.selector).submit();
		},
		
		close : function() {
			var that = this;
			
			that.onClose = null;
			that.isCreated = null;
			windowObj.removeWindow(that.selector);
			that.selector = null;
		}
};

drngEqpCnfmPrmisnObj.quickSearch = {
		
		TITLE : "배수설비 검색",
		
		CLASS_NAME : 'DrngEqpSearch',
		
		init : function() {
			var that = this;
			
			that.initUi();
			that.bindEvents();
		},
		
		initUi :  function() {
			var tagStr = '<a id="a_quick_menu_spmt" href="#"><img class="tool_toggle_radio img_ov_off tootip" src="'+ CONTEXT_PATH +'/images/kworks/map/tool/spmt_off.png" alt="배수설비" title="배수설비" /></a>';
			$("#div_quick_menu_container").append(tagStr);
		},
		
		bindEvents : function() {
			var that = this;
			
			$("#a_quick_menu_spmt").click(function(event) {
				selectObj.active(that.CLASS_NAME, "Point", "drawend", function(evt) {
					that.searchDrngEqp(evt.feature).done(function(rows) {
						var dataId = '';
						var fid = 0;
						for (var i = 0; i < rows.length; i++) {
							if (rows[i].dataId == 'SWL_SPOT_PS') {
								dataId = rows[i].dataId;
								fid = rows[i].ids[0];
								break;
							} else if (rows[i].dataId == 'SWL_CONN_LS') {
								dataId = rows[i].dataId;
								fid = rows[i].ids[0];
							} else if (dataId == '' || fid == 0) {
								dataId = rows[i].dataId;
								fid = rows[i].ids[0];
							}
						}
						
						if (dataId != '' && fid != 0) {
							that.viewDrngEqp(dataId, fid);
						} else {
							$.messager.alert(that.TITLE, '배수설비가 존재하지 않습니다.');
						}
						
						highlightObj.removeFeatures(that.CLASS_NAME);
//						mainObj.defaultActive();
					});
				});
				event.preventDefault();
			});
		},
		
		searchDrngEqp : function(feature) {
			var that = this;

			var deferred = $.Deferred();
			
			// 축척 제한
			var scale = mapObj.getMap().getScale();
			if(scale > 2500) {
				$.messager.confirm(that.TITLE, "2500 이하의 축척에서만 사용 가능합니다. 축척을 확대 하시겠습니까?", function(isTrue) {
					if(isTrue) {
						var cooridates = feature.getGeometry().getCoordinates();
						mapObj.getMap().moveToCenter(cooridates);
						mapObj.getMap().moveToScale(2500);
					}
				});
				return deferred.promise();
			}
			
			// 버퍼 적용
			var buffer = mapObj.getMap().getView().getResolution() * 10;
			if(buffer < 1) {
				buffer = 1;
			}
			var geometry = feature.getGeometry();
			var bufferGeometry = spatialUtils.buffer(geometry, buffer);
			highlightObj.showYellowFeatures(that.CLASS_NAME, [new ol.Feature(bufferGeometry)]);
			
			var mapProjection = mapObj.getMap().getView().getProjection();
			var sysProjection = ol.proj.get(MAP.PROJECTION);
			var transformGeometry = spatialUtils.transform(bufferGeometry, mapProjection, sysProjection);
			var format = new ol.format.WKT();
			var wkt = format.writeFeature(new ol.Feature(transformGeometry));

			var dataIds = ['SWL_SPOT_PS', 'SWL_CONN_LS', COMMON.LAND];
			// 필터
			var filter = {
				filterType : "SPATIAL",
				spatialType : "INTERSECTS",
				wkt : wkt
			};
			spatialSearchUtils.searchSummaries(that.TITLE, dataIds, filter, function(rows) {
				if (rows && rows.length && rows.length > 0) {
					deferred.resolve(rows);
				}
			}, {isNoMessage : true});
			return deferred.promise();
		},
		
		viewDrngEqp : function(dataId, fid) {
			var that = this;

			var params = {
					dataId : dataId,
					fid : fid	
				};
			var url = CONTEXT_PATH + '/civilAppeal/swge/quickSearch.do';
			$.get(url, params).done(function(reponse) {
				if (reponse && reponse.result && reponse.result.ids.length > 0) {
					if (reponse.result.dataId == 'SWT_SPMT_MA') {
						that.openDrngEqpCnfmPrmisn(reponse.result);
					} else {
						that.openDrngEqp(reponse.result);
					}
				} else {
					$.messager.alert(that.TITLE, "검색 결과가 없습니다.");
				}
			});
		},
		
		openDrngEqp : function(data) {
			windowObj.registerObj.open(data.dataId, data.ids);
		},
		
		openDrngEqpCnfmPrmisn : function(data) {
			if (data.ids.length > 1) {
				var rows = [];
				rows.push(data);
				
				var option = {
						dataId : data.dataId,
						menuObj : drngEqpCnfmPrmisnObj.resultObj.modifyMenu,
						eventObj : drngEqpCnfmPrmisnObj.resultObj.bindEvents
				};
				resultObj.addRows(rows, option);
			} else {
				drngEqpCnfmPrmisnObj.view.open(data.ids[0]);
			}
		}
};

drngEqpCnfmPrmisnObj.dscssList = {
		
		TITLE : '협의내용',
		
		CLASS_NAME : 'DscssCodeList',
		
		onSelect : null,
		
		isCreated : false,
		
		selector : null,
		
		open : function(pmsCde, onSelect) {
			var that = this;
			
			that.onSelect = onSelect;
			
			var url = CONTEXT_PATH + "/civilAppeal/swge/searchDscssCodeList.do";
			var windowOptions = {
					width : 800,
					height : 500,
					top : 180,
					left : 450,
					modal : true,
					onClose : function() {
						that.close();
					}
			};
			var id = windowObj.createWindow(that.CALSS_NAME, that.TITLE, url, null, windowOptions, function() {
				if (!that.isCreated) {
					that.isCreated = true;
					
					that.bindData(pmsCde);
					that.resizeWindowHandler();
					that.bindEvents();
				}
			});
			that.selector = '#' + id;
		},
		
		bindEvents : function() {
			var that = this;
			
			//목록 체크
			$(".tableSelector input[type=checkbox]", that.selector).on('click', function() {
				if ($(this).is(':checked')) {
					$(".tableSelector input[type=checkbox]", that.selector).prop('checked', false);
					$(this).prop('checked', true);
				}
				return true;
			});
			
			//협의사항 선택
			$('.a_select_dscssCodeList', that.selector).on('click', function() {
				var dscssContent = $(".tableSelector input[type=checkbox]:checked", that.selector).parent().next().text();
				that.onSelect(dscssContent);
				that.close();
				return false;
			});
			
			// 닫기
			$(".a_close_dscssCodeList", that.selector).on("click", function() {
				that.close();
				return false;
			});
		},
		
		bindData : function(pmsCde) {
			var tagStr = '<tr><td></td><td>협의내용</td></tr>';
			tagStr += '<tr>';
			tagStr += '<td><input type="checkbox"></td>';
			tagStr += '<td>하수도법 제15조, 하수처리구역 외 지역임</td>';
			tagStr += '</tr>';
			switch (pmsCde) {
				case 'PMS002':	//가설건축물-허가
				case 'PMS003':	//가설건축물-신고
				case 'PMS004':	//추인허가-신축
				case 'PMS006':	//추인신고-신축
				case 'PMS008':	//신축-허가
				case 'PMS009':	//신축-신고
					tagStr += '<tr>';
					tagStr += '<td><input type="checkbox"></td>';
					tagStr += '<td>하수도법 제27조, 하수처리구역 내(합류) 지역으로서, 배수설비 설치 대상입니다. 배수설비 공사는 강릉시 배수설비 공사 시공 대행업자로 등록된 업체만 시공할 수 있으며(강릉시에 등록된 배수설비 시공대행업자 현황문의, 640-5979), 상수도과와 사전 협의하여 중복 공사가 되지 않도록 유의하여 주시기 바랍니다. ※ 강릉시 미등록 업체가 배수설비 공사 시 건축물 준공허가가 안 될 수 있음을 알려드립니다. 배수설비를 시공한 후 건축물의 사용승인 전에 준공검사를 득하시기 바라며, 대지 경계 내에 1개소 이상의 맨홀이나 막힘 예방시설을 반드시 설치하여 배수관로의 막힘사고 등에 대비하시기 바라며, 하수도사용조례 시행규칙 제3조 별표1의 배수설비 설치 기준을 준수하여야 함.</td>';
					tagStr += '</tr>';
					
					tagStr += '<tr>';
					tagStr += '<td><input type="checkbox"></td>';
					tagStr += '<td>하수도법 제27조, 하수처리구역 내(분류-BTL) 지역으로서, 배수설비설치 대상입니다. 배수설비 공사는 강릉시 배수설비 공사 시공대행업자로 등록된 업체만 시공할 수 있으며(강릉시에 등록된 배수설비 시공대행업자 현황문의, 640-5979), 상수도과와 사전 협의하여 중복 공사가 되지 않도록 유의하여 주시기 바랍니다. ※ 현재 지역 하수관로는 강릉아랫물길㈜에서 관리하고 있으며, 배수설비공사 시 아랫물길에 필히 연락하시어 사전에 연결지점 등을 협의하시고 준공 후 아랫물길로 부터 비오접확인서를 발부 받아 준공검사 신청시 제출하시기 바랍니다.(아랫물길 연락처 : 070-7525-4271) 배수설비를 시공한 후 건물의 사용승인 전에 배수설비 준공검사를 받아야 하며,  대지 경계 내에 1개소 이상의 맨홀이나 막힘 예방시설을 반드시 설치하여 배수관로의 막힘사고 등에 대비하시기 바라며, 하수도사용조례 시행규칙 제3조 별표1의 배수설비 설치 기준을 준수하여야 함.</td>';
					tagStr += '</tr>';
					
					tagStr += '<tr>';
					tagStr += '<td><input type="checkbox"></td>';
					tagStr += '<td>하수도법 제27조, 하수처리구역 내(분류) 지역으로서, 배수설비 설치 대상입니다. 배수설비 공사는 강릉시 배수설비 공사 시공 대행업자로 등록된 업체만 시공할 수 있으며(시공대행업자 현황문의, 640-5979), 상수도과와 사전 협의하여 중복 공사가 되지 않도록 유의하여 주시기 바랍니다. ※ 강릉시 미등록 업체가 배수설비 공사 시 건축물 준공허가가 안 될 수 있음을 알려드립니다. 배수설비를 시공한 후 건축물의 사용승인 전에 배수설비 준공검사를 득하시기 바라며, 대지 경계 내에 1개소 이상의 맨홀이나 막힘 예방시설을 반드시 설치하여 배수관로의 막힘사고 등에 대비하시기 바라며, 하수도사용조례 시행규칙 제3조 별표1의 배수설비 설치 기준을 준수하여야 함.</td>';
					tagStr += '</tr>';
					break;
				case 'PMS001':	//용도변경
				case 'PMS005':	//추인허가-증축
				case 'PMS007':	//추인신고-증축
				case 'PMS010':	//증축-허가
				case 'PMS011':	//증축-신고
				case 'PMS013':	//표지변경
					tagStr += '<tr>';
					tagStr += '<td><input type="checkbox"></td>';
					tagStr += '<td>하수도법 제61조, 하수도원인자부담금 해당없음(합류)</td>';
					tagStr += '</tr>';
					
					tagStr += '<tr>';
					tagStr += '<td><input type="checkbox"></td>';
					tagStr += '<td>하수도법 제61조, 하수도원인자부담금 해당없음(분류)</td>';
					tagStr += '</tr>';
					
					tagStr += '<tr>';
					tagStr += '<td><input type="checkbox"></td>';
					tagStr += '<td>하수도법 제61조, 하수도원인자부담금 해당없음(분류-BTL)</td>';
					tagStr += '</tr>';
					break;
				default:	//사용승인, 미분류, 기타
					tagStr += '<tr>';
					tagStr += '<td><input type="checkbox"></td>';
					tagStr += '<td>하수도법 제27조, 배수설비 적정 시공됨, 하수도법 제61조, 하수도원인자부담금 해당없음(합류)</td>';
					tagStr += '</tr>';
					
					tagStr += '<tr>';
					tagStr += '<td><input type="checkbox"></td>';
					tagStr += '<td>하수도법 제27조, 배수설비 적정 시공됨, 하수도법 제61조, 하수도원인자부담금 해당없음(분류)</td>';
					tagStr += '</tr>';
					
					tagStr += '<tr>';
					tagStr += '<td><input type="checkbox"></td>';
					tagStr += '<td>하수도법 제27조, 배수설비 적정 시공됨, 하수도법 제61조, 하수도원인자부담금 해당없음(BTL)</td>';
					tagStr += '</tr>';
			}
			tagStr += '<tr>';
			tagStr += '<td><input type="checkbox"></td>';
			tagStr += '<td>기타</td>';
			tagStr += '</tr>';
			
			$(".tableSelector").empty();
			$(".tableSelector").append(tagStr);
		},
		
		resizeWindowHandler : function() {
			var that = this;
			
			var contentHeigth = $('.tableSelector', that.selector).height();
			$(that.selector).window('resize', {
				height : contentHeigth + 70
			});
		},
		
		close : function() {
			var that = this;
			
			that.onSelect = null;
			that.isCreated = false;
			windowObj.removeWindow(that.selector);
			that.selector = null;
		}
};