/**
 * 업무 메뉴 객체
 * @type {Object}
 */
menuObj.jobObj = {
		
	/**
	 * 페이지 명
	 * @type {String}
	 */
	PAGE : "job",
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_menu_panel_job",
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "업무관리",
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.load();
		that.bindEvents();
		that.open();
	},

	/**
	 * 로딩
	 */
	load : function() {
		var that = this;
		var url = CONTEXT_PATH + "/rest/menu/listAll.do";
		var params = { sysId : SYS_ID };
		$.get(url, params).done(function(result) {
			if(result && result.rows) {
				var rows = result.rows;
				var tagStr = '';
				for(var i=0; i < rows.length; i++) {
					var row = rows[i];
					if(row.upperMenuId == -1) {
						var groupMenuId = row.menuNo?row.menuNo:row.menuId;
						tagStr += '<li data-menu-id="' + groupMenuId + '" class="li_menu_group collapse">';
						tagStr += '<h2>' + row.menuNm + '</h2>';
						tagStr += '</li>';
						
						for(var j=0; j < rows.length; j++) {
							if(groupMenuId == rows[j].upperMenuId) {
								var menuId = rows[j].menuId?rows[j].menuId:rows[j].kwsMenu.menuId;
								var fnctId = rows[j].fnctId?rows[j].fnctId:rows[j].kwsMenu.fnctId;
								var url = rows[j].url?rows[j].url:rows[j].kwsMenu.url;
								tagStr += '<li class="li_menu" data-upper-menu-id="' + rows[j].upperMenuId + '" >';
								tagStr += '<a href="' + CONTEXT_PATH + url + '" class="a_menu" data-menu-id="' + menuId + '" data-fnct-id="' + fnctId + '" data-menu-nm="' + rows[j].menuNm + '" >';
								tagStr += '<img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/left_3dep_bl.png" alt="DASH" />';
								tagStr += rows[j].menuNm;
								tagStr += '</a>';
								tagStr += '</li>';
							}
						}
					}
				}
				$(".ul_menu_job_menu_list", that.selector).html(tagStr);
				
			}
			else {
				$.messager.alert(that.TITLE, "업무 목록을 불러오는 데 실패했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "업무 목록을 불러오는 데 실패했습니다.");
		});
	},
	
	resizeWindowHandler : function() {
		var that = this;
		var height = $("#div_menu_panel").height();
		var titleHeight = $("> .title", that.selector).height();
		
		var offset = 20;
		$(".ul_menu_job_menu_list", that.selector).height(height - titleHeight - offset);
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 메뉴 그룹 클릭
		$(that.selector).on("click", ".li_menu_group", function() {
			var element = $(this);
			var menuId = element.attr("data-menu-id");
			if(element.hasClass("collapse")) {
				$(".li_menu[data-upper-menu-id="+menuId+"]").show();
				element.addClass("expand");
				element.removeClass("collapse");
//				if(SYS_ID == "7" && PROJECT_CODE=="yy"){
//					$(".li_dept_group").show();
//					$(".li_dept_sub").hide();
//				}
			}
			else {
				$(".li_menu[data-upper-menu-id="+menuId+"]").hide();
				element.addClass("collapse");
				element.removeClass("expand");
//				if(SYS_ID == "7" && PROJECT_CODE=="yy"){
//					$(".li_dept_group").hide();
//					$(".li_dept_sub").hide();
//				}
			}
		});
		
		//양양군 정책지도
//		$(that.selector).on("click", ".li_dept_group", function() {
//			var element = $(this);
//			var menuId = element.attr("data-upper-dept-id");
//			if(element.hasClass("collapse")) {
//				$(".li_dept_sub[data-dept-id="+menuId+"]").show();
//				element.addClass("expand");
//				element.removeClass("collapse");
//			}
//			else {
//				$(".li_dept_sub[data-dept-id="+menuId+"]").hide();
//				element.addClass("collapse");
//				element.removeClass("expand");
//			}
//		});
		
		// 메뉴 선택
		$(that.selector).on("click", ".a_menu", function() {
			var element = $(this);
			
			$(".li_menu", that.selector).removeClass("on");
			element.parent().addClass("on");
			
			var url = element.attr("href");
			var menuId = element.attr("data-menu-id");
			var menuNm = element.attr("data-menu-nm");
			var fnctId = element.attr("data-fnct-id");
			
			switch(fnctId) {
				case "BEACH_EROSION" :
					that.beachErosion(menuId, menuNm, url);
					break;
				case "CITYPARK_FACILITIES_MANAGE" :
					that.editFacilities(menuId, menuNm, url);
					break;
				case "CIVIL_APPEAL_MANAGE" :
					that.civilAppealManage(menuId, menuNm, url);
					break;
				case "CONSTRUCTION_WORK_MANAGE" :
					that.constructionWorkManage(menuId, menuNm, url);
					break;
				case "CONTRACT_REGISTER_SEARCH" :
					windowObj.cntrctRegstrObj.open();
					break;
				case "CONTROL_POINT_MANAGE" :
					that.controlPointManage(menuId, menuNm, url);
					break;
				case "CROSS_SECTIONAL_ANALYSIS" :
					that.crosssectionalViewAnalysis(menuId, menuNm, url);
					break;
				case "DIRECTION_CONVERSION" :	//하수관거방향전환
					that.directionConversion(menuId, menuNm, url);
					break;
				case "FACILITIES_EDITING" :
					that.editFacilities(menuId, menuNm, url);
					break;
				case "FACILITIES_PRINTING" :
					$.messager.alert(menuNm, "기능 수정 중입니다.");
					break;
				case "FACILITIES_STATISTICS" :
					that.statisticsFacilities(menuId, menuNm, url);
					break;
				case "RIVER_SIDE_POINT_MANAGE" :
					that.riverSidePoint(menuId, menuNm, url);
					break;
				case "ROAD_NM_SEARCH" :
					that.roadNameSearch(menuId, menuNm, url);
					break;
				case "ROAD_REGISTER_SEARCH" :
					that.roadRegisterSearch(menuId, menuNm, url);
					break;
				case "ROAD_REGISTER_SPATIAL" :
					that.searchRoadRegisterByBBox(menuId, menuNm, url);
					break;
				case "ROAD_RELATION_SEARCH" :
					that.roadRelationSearch(menuId, menuNm, url);
					break;
				case "ROAD_REGISTER_VIDEO" :
					that.searchRoadVideoByBBox(menuId, menuNm, url);
					break;	
				case "ROAD_ROUTE_SEARCH" :
					that.roadRouteSearch(menuId, menuNm, url);
					break;
				case "FACILITIES_SEARCH" :
					that.searchFacilities(menuId, menuNm, url);
					break;
				case "LOSS_STTEMNT_MANAGE" :
					that.lossSttemnt(menuId, menuNm, url);
					break;
				case "UNDERGROUND_WATER_MANAGE" :
					that.manageUndergroundWater(menuId, menuNm, url);
					break;
				case "UNITY_SEARCH" :
					that.unitySearch(menuId, menuNm, url);
					break;
				case "WATER_CONTROL_VALUE_ANALYSIS" :
					that.waterControlValueAnalysis(menuId, menuNm, url);
					break;
				case "OCPAT_REGISTER_SEARCH" :
					that.ocpatRegisterSearch(menuId, menuNm, url);
					break;
				case "OCPAT_REGISTER_SPATIAL" :
					that.searchOcpatRegisterByBBox(menuId, menuNm, url);
					break;
					//정책지도
				case "POLICY_REGISTER_SEARCH" :
					that.policyRegisterSearch(menuId, menuNm, url);
					break;
				case "POLICY_REGISTER_SPATIAL" :
					that.searchPolicyRegisterByBBox(menuId, menuNm, url);
					break;
				case "POLICY_FACILITIES_EDITING" :
					that.modifyPolicy(menuId, menuNm, url);
					break;
				case "POLICY_DEPT_SEARCH" :
					that.policyDeptSearch(menuId, menuNm, url);
					break;
				case "CONTROL_POINT_SEARCH" :
					that.controlPointSearch(menuId, menuNm, url);
					break;
				case "CONTROL_POINT_ADD" :
					that.controlPointAdd(menuId, menuNm, url);
					break;
				//고성군 환경공간업무 서비스
				//생태현황도 통합검색
				case "ENVIRONMENT_SEARCH" :
					that.environmentSearch(menuId, menuNm, url);
					break;
				//현지조사표 통합검색
				case "ACTUAL_QUESTIONNAIRE_SEARCH" :
					that.actualQuestionnaireSearch(menuId, menuNm, url);
					break;
				//제거작업표 통합검색
				case "REMOVE_JOBSHEET_SPATIAL" :
					that.removeJobsheetSearch(menuId, menuNm, url);
					break;
				case "ENVIRO_EDIT" :
					that.editEnviroPage(menuId, menuNm, url);
					break;
				default :
					if (menuObj.jobObj[PROJECT_CODE+"Obj"]) {
						var functionName = camelcaseUtils.underbarToCamelcase(fnctId);
						if (menuObj.jobObj[PROJECT_CODE+"Obj"][functionName]) {
							menuObj.jobObj[PROJECT_CODE+"Obj"][functionName](menuId, menuNm, url);
						}
					}
					break;
			}
			return false;
		});
	},
	
	/**
	 * 시설물 검색
	 * @param {String} menuId 메뉴 아이디
	 * @param {String} menuNm 메뉴명
	 * @param {String} url 주소
	 */
	searchFacilities : function(menuId, menuNm, url) {
		
		var extent = mapObj.getMap().getExtent();
		var from = mapObj.getMap().getView().getProjection();
		var to = ol.proj.get(MAP.PROJECTION);
		var transformExtent = spatialUtils.transformExtent(extent, from ,to);
		
		var filter = {
			menuId : menuId,
			menuNm: menuNm,
			filterType : "BBOX",
			xmin : transformExtent[0],
			ymin : transformExtent[1],
			xmax : transformExtent[2],
			ymax : transformExtent[3]
		};
		if (SYS_ID==11 && PROJECT_CODE =="gn"){
			$.post(url, filter).done(function(response) {
				// 데이터가 1개 이상인경우 
				if(response && response.data && response.data.ids && response.data.ids.length > 0) {
					resultObj.open([response.data]);
				}
				// 데이터가 0개인경우
				else {
					var kwsData = dataObj.getDataByDataId(response.dataId, true);
					var dataFields = kwsData.kwsDataFields;
					var nulldata = {
						dataId : response.dataId,
						dataFields : response.dataFields,
						dataAlias : kwsData.dataAlias,
						ids : []
					};
					resultObj.open([nulldata]);
					$.messager.alert(menuNm, "데이터가 없습니다.");
				}
			}).fail(function() {
				$.messager.alert(menuNm, "권한이 없습니다.");
			});
		}else {
			$.post(url, filter).done(function(response) {
				if(response && response.data && response.data.ids && response.data.ids.length > 0) {
					resultObj.open([response.data]);
				}
				else {
					$.messager.alert(menuNm, "데이터가 없습니다.");
				}
			}).fail(function() {
				$.messager.alert(menuNm, "권한이 없습니다.");
			});
		}
	},
	
	
	/**
	 * 통합 검색
	 * @param {String} menuId 메뉴 아이디
	 * @param {String} menuNm 메뉴명
	 * @param {String} url 주소
	 */
	unitySearch : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
		$.get(url).done(function(response) {
			if(response && response.data && response.data.kwsSysDataCtgrys && response.data.kwsSysDataCtgrys.length > 0) {
				var categoryIds = [];
				for(var i=0, len=response.data.kwsSysDataCtgrys.length; i < len; i++) {
					var category = response.data.kwsSysDataCtgrys[i];
					categoryIds.push(category.dataCtgryId);
				}
				windowObj.unityConditionSearchObj.open(categoryIds);
			}
			else {
				$.messager.alert(menuNm, "시스템에 포함된 데이터 카테고리 목록이 없습니다.");
			}
		}).fail(function() {
			$.messager.alert(menuNm, "시스템 - 데이터 카테고리 목록을 가져오는데 실패했습니다.");
		});
	},
	
	/**
	 * 도로노선 
	 * @param {String} menuId 메뉴 아이디
	 * @param {String} menuNm 메뉴명
	 * @param {String} url 주소
	 */
	roadRouteSearch : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
		$.get(url).done(function(response) {
			if(response && response.isAuthor) {
				windowObj.roadRouteObj.open();
			}
			else {
				$.messager.alert(menuNm, "권한이 없습니다.");
			}
		});
	},
	
	/**
	 * 도로명
	 * @param {String} menuId 메뉴 아이디
	 * @param {String} menuNm 메뉴명
	 * @param {String} url 주소
	 */
	roadNameSearch : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
		$.get(url).done(function(response) {
			if(response && response.isAuthor) {
				windowObj.roadNameObj.open();
			}
			else {
				$.messager.alert(menuNm, "권한이 없습니다.");
			}
		});
	},
	
	/**
	 * 도로 연관 검색
	 * @param {String} menuId 메뉴 아이디
	 * @param {String} menuNm 메뉴명
	 * @param {String} url 주소
	 */
	roadRelationSearch : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
		$.get(url).done(function(response) {
			if(response && response.isAuthor && response.dataId) {
				var extent = mapObj.getMap().getExtent();
				var from = mapObj.getMap().getView().getProjection();
				var to = ol.proj.get(MAP.PROJECTION);
				var transformExtent = spatialUtils.transformExtent(extent, from ,to);
				
				var url = CONTEXT_PATH + "/rest/road/" + response.dataId + "/searchRegister.do";
				var params = {
					dataIds : roadObj.getRoadDataIds(),
					xmin : transformExtent[0],
					ymin : transformExtent[1],
					xmax : transformExtent[2],
					ymax : transformExtent[3]
				};
				$.get(url, params).done(function(response) {
					if(response && response.data && response.data.ids && response.data.ids.length > 0) {
						resultObj.open([response.data]);
					}
					else {
						$.messager.alert(menuNm, "데이터가 없습니다.");
					}
				}).fail(function() {
					$.messager.alert(menuNm, "정보를 불러오는데 실패 했습니다.");
				});
			}
			else {
				$.messager.alert(menuNm, "권한이 없습니다.");
			}
		});
	},
	
	// 공사대장관리
	constructionWorkManage : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);

		$.get(url).done(function(response) {
			if(response && response.isAuthor) {
				switch(menuNm) {
				case "도로공사대장 목록" :
					roadCntrwkRegstrObj.open();
					break;
				case "도로공사대장 등록" :
					roadCntrwkRegstrObj.add.open();
					break;
				case "상수공사대장 목록" :
					wrppCntrwkRegstrObj.open();
					break;
				case "상수공사대장 등록" :
					wrppCntrwkRegstrObj.add.open();
					break;
				case "급수공사대장 목록" :
					wspCntrwkRegstrObj.open();
					break;
				case "급수공사대장 등록" :
					wspCntrwkRegstrObj.add.open();
					break;
				case "하수공사대장 목록" :
					swgeCntrwkRegstrObj.open();
					break;
				case "하수공사대장 등록" :
					swgeCntrwkRegstrObj.add.open();
					break;
				case "토지공사대장 목록" :
					landCenterCntrwkRegstrObj.open();
					break;
				case "토지공사대장 등록" :
					landCenterCntrwkRegstrObj.add.open();
					break;
				case "토지공사대장 등록(지도)" :
					landCenterCntrwkRegstrObj.add.getLcXY();
					break;
				case "토지사용정보대장 목록" :
					landUseInfoRegstrObj.open();
					break;
				case "토지사용정보대장 등록" :
					landUseInfoRegstrObj.add.open();
					break;
				case "침출수공사대장" :
					lwlCntrwkRegstrObj.open();
					break;
					
				case "도로점용허가 등록":
					ocpatRegstrObj.add.open('100100');
					break;
				case "건축허가협의 등록":
					ocpatRegstrObj.add.open('200100');
					break;
				case "개발행위협의 등록":
					ocpatRegstrObj.add.open('300100');
					break;
				case "사설안내표지판 등록":
					ocpatRegstrObj.add.open('400100');
					break;
				case "변경허가 등록":
					ocpatRegstrObj.add.open('500100');
					break;
				case "원상회복공사 등록":
					ocpatRegstrObj.add.open('600100');
					break;
				case "불허가 등록":
					ocpatRegstrObj.add.open('700100');
					break;
				case "점용취소 등록":
					ocpatRegstrObj.add.open('800100');
					break;
				case "취하원 등록":
					ocpatRegstrObj.add.open('900100');
					break;
				default :
					break;
				}
			}
			else {
				$.messager.alert(menuNm, "권한이 없습니다.");
			}
		});
	},
	
	civilAppealManage : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
		
		$.get(url).done(function(response) {
			if(response && response.isAuthor) {
				switch(menuNm) {
				case "도로민원 목록" :
					roadCivilAppealObj.open();
					break;
				case "도로민원 등록" :
					roadCivilAppealObj.add.open();
					break;
				case "상수민원 목록" :
					wrppCivilAppealObj.open();
					break;
				case "상수민원 등록" :
					wrppCivilAppealObj.add.open();
					break;
				case "하수민원 목록" :
					swgeCivilAppealObj.open();
					break;
				case "하수민원 등록" :
					swgeCivilAppealObj.add.open();
					break;
				case "배수설비인허가 목록" :
					drngEqpCnfmPrmisnObj.open();
					break;
				case "배수설비인허가 등록" :
					drngEqpCnfmPrmisnObj.add.open();
					break;
				default :
					break;
				}
			}
			else {
				$.messager.alert(menuNm, "권한이 없습니다.");
			}
		});
	},
	
	controlPointManage : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
		$.get(url).done(function(response) {
			if(response && response.isAuthor && response.dataId) {
				var dataId = response.dataId;
				
				if(dataId == "RDL_PCBS_PS") {
					windowObj.controlPointObj.open();
				}
				else if(dataId == "ETL_RGCP_PS") {
					if(menuNm == "도시기준점 목록" ||  menuNm == "기준점목록") {
						ctrlPntObj.open();
					}
					else if(menuNm = "도시기준점 등록" ||  menuNm == "기준점등록") {
						ctrlPntObj.add.open();
					}
					else {
						$.messager.alert(menuNm, "정의되지 않은 데이터 타입 입니다.");
					}
				}
				else {
					$.messager.alert(menuNm, "정의되지 않은 데이터 타입 입니다.");
				}
			}
			else {
				$.messager.alert(menuNm, "권한이 없습니다.");
			}
		});
	},
	
	statisticsFacilities : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
		popupUtils.open(url, menuNm, { width : 1200 , height : 800, left : 300, top:150 });
	},
	
	crosssectionalViewAnalysis : function(menuId, menuNm, url) {
		resultObj.close();
		//roadRegResultObj.close();	// Lks : 도로대장
		
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
		windowObj.crossSectionalSelectObj.active(url);
	},
	
	editFacilities : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId+ "&menuNm=" + menuNm;
		url = encodeURI(url);
		$.get(url).done(function(response) {
		
			if(response) {
			
				var data = response.data;
				if(response.isEdit) {
					if(response.isAuthor) {
						var layer = response.layer;
						var drawType = null;
						switch(layer.lyrTy) {
						case "POINT" : 
						case "MULTIPOINT" :
							drawType = "Point";
							break;
						case "LINESTRING" :
						case "MULTILINESTRING" :
							drawType = "LineString";
							break;
						case "POLYGON" :
						case "MULTIPOLYGON" :
							drawType = "Polygon";
							break;
						}
						editObj.open(data.dataId, data.dataAlias, drawType);
					}
					else {
						$.messager.alert(menuNm, "권한이 없습니다.");
					}
				}
				else {
					$.messager.alert(menuNm, "편집 제한");
				}
			}
		});
	},
	
	// 지하수관정
	manageUndergroundWater : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
		$.get(url).done(function(response) {
			if(response && response.isAuthor) {
				ugrwtrObj.open();
			}
			else {
				$.messager.alert(menuNm, "권한이 없습니다.");
			}
		});
	},
	
	waterControlValueAnalysis : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
		$.get(url).done(function(response) {
			if(response && response.isAuthor && response.dataId) {
				windowObj.waterControlValueSelect.open(response.dataId);
			}
			else {
				$.messager.alert(menuNm, "권한이 없습니다.");
			}
		});
	},
	
	lossSttemnt : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
		$.get(url).done(function(response) {
			if(response && response.isAuthor) {
				switch(menuNm) {
				case "망실신고 목록" :
					lossSttemntObj.open();
					break;
				default :
					break;
				}
			}
			else {
				$.messager.alert(menuNm, "권한이 없습니다.");
			}
		});
	},
	

	directionConversion : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
		$.get(url).done(function(response) {
			if(response && response.isAuthor) {
				var data = response.data;
				windowObj.directionConversionObj.open(data.dataId, data.dataAlias);
			}
			else {
				$.messager.alert(menuNm, "권한이 없습니다.");
			}
		});
	},
	
	// 하천측점
	riverSidePoint : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
		$.get(url).done(function(response) {
			if(response && response.isAuthor) {
				riverSidePointObj.open();
			}
			else {
				$.messager.alert(menuNm, "권한이 없습니다.");
			}
		});
	},
	
	beachErosion : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
		$.get(url).done(function(response) {
			if(response && response.isAuthor) {
				beachErosionObj.open();
			}
			else {
				$.messager.alert(menuNm, "권한이 없습니다.");
			}		
		});
	},
	
	// 도로대장 검색
	roadRegisterSearch :function(menuId, menuNm, url) {
		// Lks : 도로대장 검색
//		url += "?menuId=" + menuId + "&menuNm=" + menuNm;;
//		$.get(url).done(function(response) {
//			if(response && response.isAuthor) {
				resultObj.close();
				roadRegResultObj.close();
				
				roadRegstrObj.open();
//			}
//			else {
//				$.messager.alert(menuNm, "권한이 없습니다.");
//			}		
//		});
	},
	
	searchRoadRegisterByBBox : function(menuId, menuNm, url) {
		var extent = mapObj.getMap().getExtent();
		var from = mapObj.getMap().getView().getProjection();
		var to = ol.proj.get(MAP.PROJECTION);
		var transformExtent = spatialUtils.transformExtent(extent, from ,to);
		
		var param = {
			xmin : transformExtent[0],
			ymin : transformExtent[1],
			xmax : transformExtent[2],
			ymax : transformExtent[3]
		};
		$.post(url, param).done(function(response) {
			if(response.rows.length < 1){
				// var kwsData = dataObj.getDataByDataId(response.regIdn, true);
				
				var regIdn = response.regIdn;
				var dataId = roadRegObj.getLayerId(regIdn);
				var kwsData = dataObj.getDataByDataId(dataId, true);
//				var dataFields = kwsData.kwsDataFields;
//				var layerData = dataObj.getDataByRoadRegsterId(dataId);
//				var dataIds = roadObj.getRoadDataIds;
//				var regAlias = kwsData.dataAlias;
				
				var nullopen = {
					dataId : response.dataId,
					dataIds: response.dataids,
					featureIds: (
						dataAlias= kwsData.regAlias,
						dataId= response.dataId,
						ids = []
					),
					regAlias: kwsData.dataAlias,
					regIdn : response.regIdn
				};
				roadRegResultObj.open([nullopen]);
				$.messager.alert(menuNm, "데이터가 없습니다.");
			}
			else {
				roadRegResultObj.open(response.rows);
			}
		}).fail(function() {
			$.messager.alert(menuNm, "권한이 없습니다.");
		});
	},	

	searchRoadVideoByBBox : function(menuId, menuNm, url) {
		var extent = mapObj.getMap().getExtent();
		var from = mapObj.getMap().getView().getProjection();
		var to = ol.proj.get(MAP.PROJECTION);
		var transformExtent = spatialUtils.transformExtent(extent, from ,to);
		
		var param = {
			xmin : transformExtent[0],
			ymin : transformExtent[1],
			xmax : transformExtent[2],
			ymax : transformExtent[3]
		};
		$.post(url, param).done(function(response) {
			if(response.rows.length < 1){
				var regIdn = response.regIdn;
				var dataId = roadRegObj.getLayerId(regIdn, false);
				var kwsData = dataObj.getDataByDataId(dataId, true);
				
				var nullopen = {
					dataId : response.dataId,
					dataIds: response.dataids,
					featureIds: (
						dataAlias= kwsData.regAlias,
						dataId= response.dataId,
						ids = []
					),
					regAlias: kwsData.dataAlias,
					regIdn : response.regIdn
				};
				roadVideoResultObj.open([nullopen]);
				$.messager.alert(menuNm, "데이터가 없습니다.");
			}
			else {
				roadVideoResultObj.open(response.rows);
			}
		}).fail(function() {
			$.messager.alert(menuNm, "권한이 없습니다.");
		});
	},	
	
	/**
	 * 점용허가 대장 공간검색
	 * @param menuId - 메뉴 아이디
	 * @param menuNm - 메뉴 명칭
	 * @param url - 서비스 URL
	 */
	ocpatRegisterSearch : function(menuId, menuNm, url) {
		resultObj.close();
		ocpatRegResultObj.close();
		
		ocpatRegstrObj.open();
	},
	
	
	/**
	 * 점용허가 대장 공간검색
	 * @param menuId - 메뉴 아이디
	 * @param menuNm - 메뉴 명칭
	 * @param url - 서비스 URL
	 */
	searchOcpatRegisterByBBox : function(menuId, menuNm, url) {
		var extent = mapObj.getMap().getExtent();
		var from = mapObj.getMap().getView().getProjection();
		var to = ol.proj.get(MAP.PROJECTION);
		var transformExtent = spatialUtils.transformExtent(extent, from ,to);
		
		var param = {
			xmin : transformExtent[0],
			ymin : transformExtent[1],
			xmax : transformExtent[2],
			ymax : transformExtent[3],
			land : COMMON.LAND,
			menuId : menuId,
			menuNm : menuNm
		};
		
		$.post(url, param).done(function(response) {
			
			if (response && response.rows && response.rows.length > 0) {
				ocpatRegResultObj.open(response.rows);
			}
			else {
				var ocpatIdn = response.ocpatIdn;
				var regData = ocpatRegObj.getRegData(ocpatIdn);
				
				var nullopen = {
					ocpatIdn : ocpatIdn,
					ocpatAlias : regData.ocpatAlias,
					featureIds : []
				};
				ocpatRegResultObj.open([nullopen]);
				$.messager.alert(menuNm, "현재 지역에는 " + regData.ocpatAlias + "대장 데이터가 없습니다.");
			}
		}).fail(function() {
			$.messager.alert(menuNm, "권한이 없습니다.");
		});
	},	
	
	/**
	 * 점책지도 관리서비스 공간검색
	 * @param menuId - 메뉴 아이디
	 * @param menuNm - 메뉴 명칭
	 * @param url - 서비스 URL
	 */
	policyRegisterSearch : function(menuId, menuNm, url) {
		resultObj.close();
		policyRegstrObj.close();
		
		policyRegstrObj.open();
	},
	

	/**
	 * 정책지도 대장 공간검색
	 * @param menuId - 메뉴 아이디
	 * @param menuNm - 메뉴 명칭
	 * @param url - 서비스 URL
	 */
	searchPolicyRegisterByBBox : function(menuId, menuNm, url) {
		var extent = mapObj.getMap().getExtent();
		var from = mapObj.getMap().getView().getProjection();
		var to = ol.proj.get(MAP.PROJECTION);
		var transformExtent = spatialUtils.transformExtent(extent, from ,to);
		
		var filter = {
			menuId : menuId,
			filterType : "BBOX",
			xmin : transformExtent[0],
			ymin : transformExtent[1],
			xmax : transformExtent[2],
			ymax : transformExtent[3]
		};
		$.post(url, filter).done(function(response) {
			var policyData = response.result;
			if(policyData && policyData.length > 0) {
				policyRegResultObj.createData([policyData]);
			}
			else {
				$.messager.alert(menuNm, "데이터가 없습니다.");
			}
		}).fail(function() {
			$.messager.alert(menuNm, "권한이 없습니다.");
		});
	},	
	
	modifyPolicy : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
		$.get(url).done(function(response) {
			if(response) {
				var data = response.data;
				if(response.isEdit) {
					if(response.isAuthor) {
						var layer = response.layer;
						var drawType = null;
						switch(layer.lyrTy) {
						case "POLYGON" :
						case "MULTIPOLYGON" :
							drawType = "Polygon";
							break;
						}
						editPolicyObj.open(data.dataId, data.dataAlias, drawType); 
					}
					else {
						$.messager.alert(menuNm, "권한이 없습니다.");
					}
				}
				else {
					$.messager.alert(menuNm, "편집 제한");
				}
			}
		});
	},
	
	policyDeptSearch : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
		policyDeptSearchObj.open();
	},
	
	/**
	 * 기준점 통합검색
	 * @param menuId - 메뉴 아이디
	 * @param menuNm - 메뉴 명칭
	 * @param url - 서비스 URL
	 */
	controlPointSearch : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
		$.get(url).done(function(response) {
			if(response && response.data && response.data.kwsSysDataCtgrys && response.data.kwsSysDataCtgrys.length > 0) {
				var categoryIds = [];
				for(var i=0, len=response.data.kwsSysDataCtgrys.length; i < len; i++) {
					var category = response.data.kwsSysDataCtgrys[i];
					categoryIds.push(category.dataCtgryId);
				}
				
				resultObj.close();
				windowObj.surveyBasePointObj.open(categoryIds);
			}
			else {
				$.messager.alert(menuNm, "시스템에 포함된 데이터 카테고리 목록이 없습니다.");
			}
		}).fail(function() {
			$.messager.alert(menuNm, "시스템 - 데이터 카테고리 목록을 가져오는데 실패했습니다.");
		});
	},

	/**
	 * 기준점 등록
	 * @param menuId - 메뉴 아이디
	 * @param menuNm - 메뉴 명칭
	 * @param url - 서비스 URL
	 */
	controlPointAdd : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId + "&menuNm=" + menuNm;
		url = encodeURI(url);
		$.get(url).done(function(response) {
			if(response && response.isAuthor && response.dataId) {
				var dataId = response.dataId;

				resultObj.close();
				windowObj.surveyBasePointObj.add.open(dataId);
			}
			else {
				$.messager.alert(menuNm, "권한이 없습니다.");
			}
		});
	},
	
	//생태현황도 주소검색
	environmentSearch : function(menuId, menuNm, url) {
		nelEcSearchObj.open(url);
	},
	
	//현지조사표 주소검색
	actualQuestionnaireSearch : function(menuId, menuNm, url) {
		enviroObj.open(url);
	},
	
	//제거작업표 주소검색
	removeJobsheetSearch : function(menuId, menuNm, url) {
		reantObj.open(url);
	},
	
	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		$(that.selector).show();
		// 화면 크기 변환 시 트리 영역 크기 조절 이벤트 등록
		mainObj.addResizeHandler("jopMenu", function() {
			that.resizeWindowHandler();
		});
		that.resizeWindowHandler();
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		$(that.selector).hide();
		mainObj.removeResizeHandler("jopMenu");
	}
	
};