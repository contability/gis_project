/**
 * 토지, 건물 정보 객체
 * @type {Object}
 */
var krasObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "토지·건물 정보",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "Kras",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 토지정보
	 * @type {String}
	 */
	pnu : null,
	
	// 행위제한 내용 불러오기 여부
	restrictYn : "N",
	
	/**
	 * 재시도 횟 수
	 */
	retryCount : 0,
	
	/**
	 * 연계 정보
	 */
	data : {
		landInfo : null,
		bldgInfo : null,
		landMovHistSet : null,
		jigaList : null,
		usePlan : null,
		ucodes : null
	},
	
	/**
	 * 열기
	 * @param {String} pnu PNU 코드
	 */
	open : function(pnu){
		var that = this;
		
		// 이전 창 닫기
		if(that.selector) {
			that.close();
		}
						
		that.pnu = pnu;
		var url = CONTEXT_PATH + "/window/kras/list.do";
		var windowOptions = {
			width : 810,
			height : 600,
			onClose : function() {
				// 조회창 닫을때 하이라이트 삭제를 위하여
				// 2021.08.11, 이승재
				$(".a_close", that.selector).trigger('click');
				//that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			// 토지 소재지 표시
			$(".landLoc", that.selector).text(pnuObj.getAddr(pnu, true));
	
			that.initUi();
			that.bindEvents();
			that.searchBass();
			
			if(CONTACT.EAIS_USE_AT == "true") {
				that.searchBildngPrmisnRegstrList();
			}
			
			// 첫번째 탭 선택
			$(".div_kras_tab_list li a:first", that.selector).trigger("click");
		});
		that.selector = "#" + id;
	},
	
	/**
	 * 닫기
	 */
	close:function(){
		var that = this;
		that.pnu = null;
		that.restrictYn = "N";
		that.retryCount = 0;
		for(var key in that.data) {
			that.data[key] = null;
		}
		that.hideLoadingBar();
		windowObj.removeWindow(that.selector);
		that.selector = null;
		return false;
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 축척 표시
		$(".txt_land_view_scale", that.selector).numberspinner({
			required : true,
			width : 130,
			min : 100,
			increment : 1000
		});
	},
	
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 메인 탭 (기본정보, 토지정보, 개별공시지가, 토지이용계획, 건물정보) 이벤트 등록
		$(".div_kras_tab_list li a", that.selector).click(function(){
			var element = $(this);
			var divId = element.attr("data-div-id");
			
			$(".div_kras_tab_list li", that.selector).removeClass("on");
			element.parent().addClass("on");
			
			$(".tabCont", that.selector).hide();
			$("#"+divId, that.selector).show();
			
			if(divId == "div_kras_land_info") {
				if(!that.data.landMovHistSet){
					that.searchLandMovHist();
				}
			}
			else if(divId == "div_kras_jiga_info") {
				if(!that.data.jigaList) {
					that.searchJiga();
				}
			}
			else if(divId == "div_kras_plan_info") {
				if(!that.data.usePlan) {
					that.searchUsePlan();
				}
			}
		});
		
		//토지·건물 정보 출력
		$(".land_print").click(function(pnu){
			var tab = $(".div_kras_tab_list li.on").text();

		    var path = CONTEXT_PATH;
		    var pHeader="<html><head><link rel='stylesheet' type='text/css' href='"+ path +"/css/kworks/common/common.css'/>" +
						"<link rel='stylesheet' type='text/css' href='"+ path +"/lib/jquery/easyui/themes/bootstrap/easyui.css'/>" +
						"<link rel='stylesheet' type='text/css' href='"+ path +"/css/kworks/common/reset.css'/>" +
						"<title>토지·건물 정보 출력</title></head><body>";
			
			if(tab == '기본정보'){			 
			    var pgetContent=document.getElementById("div_kras_bass_info").innerHTML + "<br></body></html>";
			    pContent=pHeader + pgetContent;
			}
			else if(tab == '토지정보'){
			    var pgetContent=document.getElementById("div_kras_land_info").innerHTML + "<br></body></html>";
			    pContent=pHeader + pgetContent;
			}
			else if(tab == '개별공시지가'){
			    var pgetContent=document.getElementById("div_kras_jiga_info").innerHTML + "<br></body></html>";
			    pContent=pHeader + pgetContent;
			}
			else if(tab == '토지이용계획'){
			    var pgetContent=document.getElementById("div_kras_plan_info").innerHTML + "<br></body></html>";
			    pContent=pHeader + pgetContent;
			}
			else if(tab == '건물정보'){
			    var pgetContent=document.getElementById("div_kras_buld_info").innerHTML + "<br></body></html>";
			    pContent=pHeader + pgetContent;
			}
			
			pWin=window.open("","print","width=810 ,height=600 ,top=200 ,left=400,status=yes,scrollbars=yes");
		     pWin.document.open(); 
		     pWin.document.write(pContent); 
		     pWin.document.close();
		     pWin.print();
		     pWin.close();
		});
		
		// 서브 탭 (변동현황, 층별현황) 이벤트 등록
		$(".bldgTabList li a", that.selector).click(function(){
			var element = $(this);
			var divId = element.attr("data-div-id");
			
			$(".bldgTabList li", that.selector).removeClass("on");
			element.parent().addClass("on");
			
			$(".bldgTabCont", that.selector).hide();
			$("#"+divId, that.selector).show();
		});
		
		/// 축척 적용
		$(".a_apply_land_view_scale").click(function() {
			
			if(!$(".txt_land_view_scale", that.selector).numberspinner("isValid")) {
				$.messager.alert(that.TITLE, "축척 값을 입력하여주십시오.", null, function() {
					$(".txt_land_view_scale", that.selector).numberspinner("textbox").focus();
				});
				return;
			}
			
			$(".div_loading", that.selector).show();
			
			var scale = $(".txt_land_view_scale").numberspinner("getValue");
			$.get(
				CONTEXT_PATH + "/window/kras/landUsePlanCnfInfo.do",
				{
					pnu : that.pnu,
					scale : scale,
					//retryCount : that.retryCount
					retryCount : 30
				},
				function(res) {
					if(res && res.usePlan && res.usePlan.body && res.usePlan.body.data) {
						var data = res.usePlan.body.data;

						if(data.landUsePlanCnfInfoBase && data.landUsePlanCnfInfoBase.img) {
							var src = "data:image/png;base64," + data.landUsePlanCnfInfoBase.img;
							$(".img_land_view_img", that.selector).attr("src", src);
						}
						
						var tagStr = '';
						if(data.legendList) {
							for(var i in data.legendList) {
								var legend = data.legendList[i];

								tagStr += '<li>';
								tagStr += '<img alt="' + legend.text + '" src="data:image/png;base64,' + legend.img + '" /> ';
								tagStr += legend.text;
								tagStr += '</li>';
							}
						}

						$(".ul_land_view_legend", that.selector).html(tagStr);
					}
					else {
						$.messager.alert(that.TITLE, "도면을 불러오는 데 실패했습니다.(KRAS000026)");
					}
					
					$(".div_loading", that.selector).hide();
				}
			);

			return false;
		});
		
		// 행위제한 내용 불러오기
		
		$(".a_load_ucodes", that.selector).linkbutton({
			onClick : function() {
				that.restrictYn = "Y";
				that.searchUsePlan();
			}
		});
		
		/// 동 목록 클릭 시 총괄 or 집합 or 일반 건축물 정보 표시
		$(".tby_kras_dong_info").on("click", "tr", function() {
			var element = $(this);

			$(".tby_kras_dong_info tr.on").removeClass("on");
			element.addClass("on");
			
			that.searchBldg();
		});
		
		/// 건축허가대장 목록 클릭 시 해당 대장정보 표시
		$(".tby_eais_bildngPrmisn_info").on("click", "tr", function() {
			var element = $(this);

			$(".tby_eais_bildngPrmisn_info tr.on").removeClass("on");
			element.addClass("on");
			
			that.searchBildngPrmisnRegstr();
		});
		
		// 닫기
		$(".a_close", that.selector).click(function() {
			//조회창 닫을 시 하이라이트 삭제
			//2021.08.11, 이승재
			highlightObj.removeFeatures(quickObj.CLASS_NAME);
		
			that.close();
		});
	},
	
	/**
	 * 로딩바 표시
	 */
	showLoadingBar : function() {
		var that = this;
		jqueryUtils.setIsLoding(false);
		$(".div_loading", that.selector).show();
	},
	
	/**
	 * 로딩바 숨김
	 */
	hideLoadingBar : function() {
		var that = this;
		jqueryUtils.setIsLoding(true);
		$(".div_loading", that.selector).hide();
	},
	
	/**
	 * 토지/건물 대장 검색
	 */
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
					
					// 연계 요청 성공시에만 정보 표시
					if(header.code == "0000") {
						that.data.landInfo = response.landInfo.body.data;
						that.loadLand();
					}
					else {
						$.messager.alert(that.TITLE, header.message+"(KRAS000002)");
					}
				}
				else {
					if(that.retryCount == 0) {
						$.messager.confirm(that.TITLE,"연계정보를 불러오는 작업이 지연되고 있습니다.(KRAS000002) 계속 진행하시겠습니까?", function(isTrue) {
							if(isTrue) {
								that.retryCount = 1;
								that.searchBass();
							}
							else {
								that.close();
							}
						});
					}
					else {
						$.messager.alert(that.TITLE, "서버에 접속량이 많아 통신 상태가 좋지 못합니다.(KRAS000002) 잠시 후 다시 시도해 주십시오.", "error", function() {
							that.close();
						});
					}
				}
				
				if(response.bldgInfo && response.bldgInfo.header) {
//					var header = response.bldgInfo.header;
					
					if(response.bldgInfo.header.code == "0000"){
						that.data.bldgInfo = response.bldgInfo.body.data;
						that.loadBldg();
					}
					else{
						/*$.messager.alert(that.TITLE, header.message+"(KRAS000102)");*/
						$(".bldg_info", that.selector).hide();
					}
					
				}
				else {
					$(".bldg_info", that.selector).hide();
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
	
	/**
	 * 토지이동연혁 검색
	 */
	searchLandMovHist : function() {
		var that = this;
		that.showLoadingBar();
		
		var url = CONTEXT_PATH + "/window/kras/searchLandMovHist.do";
		var params = {
			pnu : that.pnu,
			retryCount : that.retryCount
		};
		$.get(url, params).done(function(response) {
			if(response && response.rows && response.rows.header) {
				if(response.rows.header.code == "0000") {
					that.data.landMovHistSet = response.rows.body.data;
					that.loadLandMovHist();
				}
				else {
					/*$.messager.alert(that.TITLE, response.rows.header.message+"(KRAS000006)");*/
				}
			}
				else {
					if(that.retryCount == 0) {
						$.messager.confirm(that.TITLE, "연계정보를 불러오는 작업이 지연되고 있습니다.(KRAS000006) 계속 진행하시겠습니까?", function(isTrue) {
							if(isTrue) {
								that.retryCount = 1;
								that.searchLandMovHist();
							}
						});
					}
					else {
						$.messager.alert(that.TITLE, "서버에 접속량이 많아 통신 상태가 좋지 못합니다.(KRAS000006) 잠시 후 다시 시도해 주십시오.", "error");
					}
				}
				that.hideLoadingBar();
			}).fail(function() {
				$.messager.alert(that.TITLE, "공시지가 정보를 조회하는데 실패했습니다.(KRAS000006)");
				that.hideLoadingBar();
			});
			
		},
	
	/**
	 * 공시지가 검색
	 */
	searchJiga : function() {
		var that = this;
		that.showLoadingBar();
		
		var url = CONTEXT_PATH + "/window/kras/searchJiga.do";
		var params = {
			pnu : that.pnu,
			retryCount : that.retryCount
		};
		$.get(url, params).done(function(response) {
			if(response && response.rows && response.rows.header) {
				if(response.rows.header.code == "0000") {
					that.data.jigaList = response.rows.body.data;
					that.loadJiga();
				}
				else {
					$.messager.alert(that.TITLE, response.rows.header.message+"(KRAS000011)");
				}
			}
			else {
				if(that.retryCount == 0) {
					$.messager.confirm(that.TITLE, "연계정보를 불러오는 작업이 지연되고 있습니다.(KRAS000011) 계속 진행하시겠습니까?", function(isTrue) {
						if(isTrue) {
							that.retryCount = 1;
							that.searchJiga();
						}
					});
				}
				else {
					$.messager.alert(that.TITLE, "서버에 접속량이 많아 통신 상태가 좋지 못합니다.(KRAS000011) 잠시 후 다시 시도해 주십시오.", "error");
				}
			}
			that.hideLoadingBar();
		}).fail(function() {
			$.messager.alert(that.TITLE, "공시지가 정보를 조회하는데 실패했습니다.(KRAS000011)");
			that.hideLoadingBar();
		});
		
	},
	
	/**
	 * 토지이용계획 검색
	 */
	searchUsePlan : function() {
		var that = this;
		that.showLoadingBar();
		
		var url = CONTEXT_PATH + "/window/kras/searchUsePlan.do";
		var params = {
			pnu : that.pnu,
			restrictYn : that.restrictYn,
			//retryCount : that.retryCount
			retryCount : 30
		};
		$.get(url, params).done(function(response) {
			if(response && response.usePlan && response.usePlan.header) {
				if(response.usePlan.header.code == "0000") {
					that.data.usePlan = response.usePlan.body.data;
					that.loadUsePlan();

					if(response.ucodes && response.ucodes.length > 0) {
						that.data.ucodes = response.ucodes;
						that.loadUcodes();
					}
				}
				else {
					$.messager.alert(that.TITLE, response.usePlan.header.message+"(KRAS000026)");
				}
			}
			else {
				if(that.retryCount == 0) {
					$.messager.confirm(that.TITLE, "연계정보를 불러오는 작업이 지연되고 있습니다.(KRAS000026) 계속 진행하시겠습니까?", function(isTrue) {
						if(isTrue) {
							that.retryCount = 1;
							that.searchJiga();
						}
					});
				}
				else {
					$.messager.alert(that.TITLE, "서버에 접속량이 많아 통신 상태가 좋지 못합니다.(KRAS000026) 잠시 후 다시 시도해 주십시오.", "error");
				}
			}
			that.hideLoadingBar();
		}).fail(function() {
			that.hideLoadingBar();
			$.messager.alert(that.TITLE, "토지이용계획 정보를 조회하는데 실패했습니다.(KRAS000026)");
		});
	},

	/**
	 * 건물대장 조회
	 */
	searchBldg : function() {
		var that = this;
		that.showLoadingBar();

		// 창 초기화
		$(".tbl_kras_bldg_info td", that.selector).text("");
		$(".tby_kras_bldg_chg_info", that.selector).html("");
		$(".tby_kras_bldg_floor_info", that.selector).html("");
		
		// 대지위치 & 지번 표시
		$(".tbl_kras_bldg_info .landLoc", that.selector).text(pnuObj.getLocNm(that.pnu));
		$(".tbl_kras_bldg_info .jibun", that.selector).text(pnuObj.getJibunNm(that.pnu));
		
		// KRAS 서비스 호출
		var bldgGbnNo = $(".tby_kras_dong_info tr.on").attr("data-gbn-no");
		var bldgKindCd = $(".tby_kras_dong_info tr.on").attr("data-kind-cd");

		if(bldgGbnNo && bldgKindCd) {
			var url = CONTEXT_PATH + "/window/kras/searchBldgHdsInfoJson.do";
			var params = {
				pnu : that.pnu,
				bldgGbnNo : bldgGbnNo,
				bldgKindCd : bldgKindCd,
				retryCount : that.retryCount
			};
			
			$.get(url, params).done(function(result) {
				// Header 정보 읽음
				if(result && result.data && result.data.header) {
					var header = result.data.header;
					
					// 연계 요청 성공시에만 정보 표시
					if(header.code == "0000") {
						var body = result.data.body;
						
						if(body && body.data) {
							/// 총괄
							if(bldgKindCd == "1") {
								var bldgLedgGenHdsInfo = body.data;

								for(var i in bldgLedgGenHdsInfo) {
									if(i == "bldgNm") {
										$(".tbl_kras_bldg_info ." + i, that.selector).text(bldgLedgGenHdsInfo[i] + " [" + bldgLedgGenHdsInfo["bldgGbnNo"] + "]");
									} 
									else if(i == "vioBldgYn") {
										$(".tbl_kras_bldg_info ." + i, that.selector).text(bldgLedgGenHdsInfo[i]=="0"?"N":"Y");
									}
									else {
										$(".tbl_kras_bldg_info ." + i, that.selector).text(bldgLedgGenHdsInfo[i]);
									}
								}
							}
							else {
								/// 집합 or 일반 
								if(body.data.bldgHdsInfo) {
									var bldgHdsInfo = body.data.bldgHdsInfo;

									for(var i in bldgHdsInfo) {
										if(i == "bldgNm") {
											$(".tbl_kras_bldg_info ." + i, that.selector).text(bldgHdsInfo[i] + " [" + bldgHdsInfo["bldgGbnNo"] + "]");
										} 
										else if(i == "vioBldgYn") {
											$(".tbl_kras_bldg_info ." + i, that.selector).text(bldgHdsInfo[i]=="0"?"N":"Y");
										}
										else {
											$(".tbl_kras_bldg_info ." + i, that.selector).text(bldgHdsInfo[i]);
										}
									}
									
									// 변동 현황
									if(bldgHdsInfo.chgDataList) {
										var chgDataList = bldgHdsInfo.chgDataList;
										var tagStr = "";

										for(var i in chgDataList) {
											tagStr += "<tr>";
											tagStr += "<td>" + chgDataList[i].chgRsnNm + "</td>";
											tagStr += "<td>" + chgDataList[i].chgCntn + "</td>";
											tagStr += "<td>" + chgDataList[i].chgYmd + "</td>";
											tagStr += "<td>" + chgDataList[i].adjYmd + "</td>";
											tagStr += "</tr>";
										}

										$(".tby_kras_bldg_chg_info", that.selector).html(tagStr);
									}
									
									// 층별 현황
									if(bldgHdsInfo.floorInfoList) {
										var floorInfoList = bldgHdsInfo.floorInfoList;
										var tagStr = "";

										for(var i in floorInfoList) {
											tagStr += "<tr>";
											tagStr += "<td>" + floorInfoList[i].flr + "</td>";
											tagStr += "<td>" + floorInfoList[i].etcStru + "</td>";
											tagStr += "<td>" + floorInfoList[i].etcUse + "</td>";
											tagStr += "<td>" + floorInfoList[i].btmArea + "</td>";
											tagStr += "</tr>";
										}

										$(".tby_kras_bldg_floor_info", that.selector).html(tagStr);
									}
								}
							}
						}
						
						$(".bldgTabList li a:first").trigger("click");
					}
					else {
						$.messager.alert(that.TITLE, header.message+"(KRAS000017),(KRAS000014),(KRAS000015)");
					}
				}
				else {
					if(that.retryCount == 0) {
						$.messager.confirm(that.TITLE, "연계정보를 불러오는 작업이 지연되고 있습니다.(KRAS000017),(KRAS000014),(KRAS000015) 계속 진행하시겠습니까?", function(isTrue) {
							if(isTrue) {
								that.retryCount = 1;
								that.searchJiga();
							}
						});
					}
					else {
						$.messager.alert(that.TITLE, "서버에 접속량이 많아 통신 상태가 좋지 못합니다.(KRAS000017),(KRAS000014),(KRAS000015) 잠시 후 다시 시도해 주십시오.", "error");
					}
				}
				that.hideLoadingBar();
			}).fail(function() {
				$.messager.alert(that.TITLE, "건축물 정보를 조회하는데 실패했습니다.(KRAS000017),(KRAS000014),(KRAS000015)");
				that.hideLoadingBar();
			});
		}
	},
	
	/**
	 * 토지정보 로딩
	 */
	loadLand : function() {
		var that = this;
		
		var landInfo = that.data.landInfo;
		$(".jimokNm", that.selector).text(landInfo.jimokNm);
		$(".parea", that.selector).text(landInfo.parea);
		$(".pannJiga", that.selector).text(numberUtils.formatCurrency(landInfo.pannJiga));
		$(".grd", that.selector).text(landInfo.grd);
		$(".grdYmd", that.selector).text(landInfo.grdYmd);
		$(".landMovRsnCdNm", that.selector).text(landInfo.landMovRsnCdNm);
		$(".landMovYmd", that.selector).text(landInfo.landMovYmd);
		$(".ownGbnNm", that.selector).text(landInfo.ownGbnNm);
		$(".ownerNm", that.selector).text(landInfo.ownerNm);
		$(".shrCnt", that.selector).text(landInfo.shrCnt);
	},
	
	/**
	 * 건물정보 로딩
	 */
	loadBldg : function() {
		var that = this;
		var tagStr = '';
		
		var bldgDongInfos = that.data.bldgInfo.bldgDongInfos;
		for(var i=0, len=bldgDongInfos.length; i < len; i++) {
			var bldgDongInfo = bldgDongInfos[i];
			tagStr += '<tr data-gbn-no="'+bldgDongInfo.bldgGbnNo+'" data-kind-cd="'+bldgDongInfo.bldgKindCd+'" >';
			tagStr += '<td>' + bldgDongInfo.bldgKindNm + '</td>';
			tagStr += '<td>' + bldgDongInfo.bldgGbnNo + '</td>';
			tagStr += '<td>' + bldgDongInfo.bldgNm + '</td>';
			tagStr += '<td>' + bldgDongInfo.dongNm + '</td>';
			tagStr += '<td>' + bldgDongInfo.garea + '</td>';
			tagStr += '</tr>';
		}
		$(".bldgDongInfos", that.selector).html(tagStr);
		
		$(".tby_kras_dong_info", that.selector).html(tagStr);
		$(".tby_kras_dong_info tr:first").trigger("click");
	},
	
	/**
	 * 토지이동연혁 로딩
	 */
	loadLandMovHist : function() {
		var that = this;
		
		var tagStr = '';
		var landMovHists = that.data.landMovHistSet.landMovHists;
		for(var i=0, len=landMovHists.length; i < len; i++) {
			var landMovHist = landMovHists[i];
			tagStr += '<tr>';
			tagStr += '<td>' + landMovHist.landHistOdrno + '</td>';
			tagStr += '<td>' + landMovHist.dymd + '</td>';
			tagStr += '<td>' + that.getJimokNm(landMovHist.jimok) + '</td>';	//이승재, 2020.06.28, 지목명으로 변환
			tagStr += '<td>' + landMovHist.parea + '</td>';
			tagStr += '<td>' + landMovHist.landMovRsnCdNm + '</td>';
			tagStr += '</tr>';
		}
		$(".tby_land_mov_hist_list", that.selector).append(tagStr);
	},
	
	/// 이승재, 2020.06.28
	/// 지목코드를 지목명으로 변경
	getJimokNm : function(jimok) {
		var jimokNm = '';
		switch(jimok) {
		case '01':
			jimokNm = '전';
			break;
		case '02':
			jimokNm = '답';
			break;
		case '03':
			jimokNm = '과수원';
			break;
		case '04':
			jimokNm = '목장용지';
			break;
		case '05':
			jimokNm = '임야';
			break;
		case '06':
			jimokNm = '광천지';
			break;
		case '07':
			jimokNm = '염전';
			break;
		case '08':
			jimokNm = '대';
			break;
		case '09':
			jimokNm = '공장용지';
			break;
		case '10':
			jimokNm = '학교용지';
			break;
		case '11':
			jimokNm = '주차장';
			break;
		case '12':
			jimokNm = '주유소용지';
			break;
		case '13':
			jimokNm = '창고용지';
			break;
		case '14':
			jimokNm = '도로';
			break;
		case '15':
			jimokNm = '철도용지';
			break;
		case '16':
			jimokNm = '제방';
			break;
		case '17':
			jimokNm = '하천';
			break;
		case '18':
			jimokNm = '구거';
			break;
		case '19':
			jimokNm = '유지';
			break;
		case '20':
			jimokNm = '양어장';
			break;
		case '21':
			jimokNm = '수도용지';
			break;
		case '22':
			jimokNm = '공원';
			break;
		case '23':
			jimokNm = '체육용지';
			break;
		case '24':
			jimokNm = '유원지';
			break;
		case '25':
			jimokNm = '종교용지';
			break;
		case '26':
			jimokNm = '사적지';
			break;
		case '27':
			jimokNm = '묘지';
			break;
		case '28':
			jimokNm = '잡종지';
			break;
		default:
			jimokNm = jimok;
			break;
		}
		return jimokNm;
	},
	
	/**
	 * 공시지가 로딩
	 */
	loadJiga : function() {
		var that = this;
		
		var tagStr = '';
		var jigas = that.data.jigaList.jigas;
		for(var i=0, len=jigas.length; i < len; i++) {
			var jiga = jigas[i];
			tagStr += '<tr>';
			tagStr += '<td>' + jiga.baseYear + '</td>';
			tagStr += '<td>' + jiga.baseMon + '</td>';
			tagStr += '<td>' + numberUtils.formatCurrency(jiga.pannJiga) + '</td>';
			tagStr += '<td>' + jiga.pannYmd + '</td>';
			tagStr += '</tr>';
		}
		$(".tby_jiga_list", that.selector).append(tagStr);
	},
	
	/**
	 * 토지이용계획 로딩
	 */
	loadUsePlan : function() {
		var that = this;
		
		var landUsePlanCnfInfoBase = that.data.usePlan.landUsePlanCnfInfoBase;
		$(".plan_parea", that.selector).text(landUsePlanCnfInfoBase.parea);
		$(".plan_uselawA", that.selector).text(landUsePlanCnfInfoBase.uselawA);
		$(".plan_uselawB", that.selector).text(landUsePlanCnfInfoBase.uselawB);
		$(".plan_uselawC", that.selector).text(landUsePlanCnfInfoBase.uselawC);
		$(".plan_uselawD", that.selector).text(landUsePlanCnfInfoBase.uselawD);
	
		that.loadLegendList();
	},
	
	/**
	 * 도면 & 범례 목록 로딩
	 */
	loadLegendList : function() {
		var that = this;
		var data = that.data.usePlan;

		if(data.landUsePlanCnfInfoBase && data.landUsePlanCnfInfoBase.img) {
			var src = "data:image/png;base64," + data.landUsePlanCnfInfoBase.img;
			$(".img_land_view_img", that.selector).attr("src", src);
		}
		
		var tagStr = '';

		if(data.legendList) {
			for(var i in data.legendList) {
				var legend = data.legendList[i];
				tagStr += '<li>';
				tagStr += '<img alt="' + legend.text + '" src="data:image/png;base64,' + legend.img + '" /> ';
				tagStr += legend.text;
				tagStr += '</li>';
			}
		}

		$(".ul_land_view_legend").html(tagStr);
	},
	
	/**
	 * 행위제한 내용 로딩
	 */
	loadUcodes : function() {
		var that = this;
		
		var ucodes = that.data.ucodes;
		var landUsePlanRestrictList = that.data.usePlan.landUsePlanRestrictList;
		
		var tagStr = '';
		for(var i=0, len=ucodes.length; i < len; i++) {
			var ucode = ucodes[i];
			
			tagStr += '[' + ucode.codeNm + ']<br />';
			for(var j=0, jLen=landUsePlanRestrictList.length; j < jLen; j++) {
				var landUsePlanRestrict = landUsePlanRestrictList[j];
				
				if(landUsePlanRestrict.lawLevel == '0' && landUsePlanRestrict.ucode == ucode.codeId ) {
					tagStr += landUsePlanRestrict.lawContents + '<br />';
				}
			}
			tagStr += '<br />';	
		}
		$(".ucodes", that.selector).html(tagStr);
	},
	
	/**
	 * 건축허가대장 목록 검색
	 */
	searchBildngPrmisnRegstrList : function() {
		var that = this;
		
		var url = CONTEXT_PATH + "/contact/eais/bildngPrmisnRegstr/list.do";
		var params = {
			pnu : that.pnu
		};
		$.get(url, params).done(function(response) {
			if(response && response.rows && response.rows.length > 0) {
				var tagStr = '';
				for(var i=0, len=response.rows.length; i < len; i++) {
					var row = response.rows[i];
					tagStr += '<tr data-pmsrgst-pk="'+ row.pmsrgstPk +'">';
					tagStr += '<td>' + row.pmsrgstPk + '</td>';
					tagStr += '<td>' + row.pmsnoYear + '</td>';
					tagStr += '<td>' + row.pmsnoKikCdNm + '</td>';
					tagStr += '<td>' + row.pmsnoGbCdNm + '</td>';
					tagStr += '</tr>';
				}
				$(".li_bildngPrmisn", that.selector).show();
				$(".tby_eais_bildngPrmisn_info", that.selector).html(tagStr);
				$(".tby_eais_bildngPrmisn_info tr:first", that.selector).trigger("click");
			}
		}).fail(function(result) {
			alert("건축허가대장 정보를 불러오는데 실패했습니다.");
		});
	},
	
	/**
	 * 건축허가대장 단건 검색
	 */
	searchBildngPrmisnRegstr : function() {
		var that = this;
		
		// 창 초기화
		$(".div_eais_bildngPrmisn_info td", that.selector).text("");
		
		// 대지위치 & 지번 표시
		$(".tbl_eais_bildngPrmisn_info .landLoc", that.selector).text(pnuObj.getLocNm(that.pnu));
		$(".tbl_eais_bildngPrmisn_info .jibun", that.selector).text(pnuObj.getJibunNm(that.pnu));
		
		// EAIS 서비스 호출
		var pmsrgstPk = $(".tby_eais_bildngPrmisn_info tr.on").attr("data-pmsrgst-pk");
		
		if(pmsrgstPk) {
			var url = CONTEXT_PATH + "/contact/eais/bildngPrmisnRegstr/" + pmsrgstPk + "/select.do";
			var params = {
					pnu : that.pnu
			};
			$.get(url, params).done(function(result) {
				if(result && result.data) {
					var data = result.data;
					
					for(var key in data) {
						$("." + key, that.selector).text(data[key]);
					}
					
					if(data.entirUseaprYn == "0") {
						$(".entirUseaprYn").text("일부");
					}
					else if(data.entirUseaprYn == "1") {
						$(".entirUseaprYn").text("전체");
					}
				}
				else {
					alert("건축허가대장 정보를 불러오는데 실패했습니다.");
				}
			}).fail(function(result) {
				alert("건축허가대장 정보를 불러오는데 실패했습니다.");
			});
		}
	}
	
};

/**
 * 토지, 건물 정보 조회창 윈도우 팝업 오픈
 * @type {Object}
 */
krasObj.krasPopup = {
		
		/**
		 * 팝업창 
		 * @type {String}
		 */
		popup : null,
		
		open : function(pnu) {
			var that = this;
			
			if (that.popup) {
				that.popup.close();
			}
			
			var url = CONTEXT_PATH + '/window/kras/popupList.do?pnu=' + pnu;
			var options = {
					scrollbars : 'yes',
					left : 100 + 'px',
					top : 20 + 'px',
					width : 810 + 'px',
					height : 600 + 'px'
			};
			that.popup = popupUtils.open(url, 'KrasPopup', options);
		}
};

