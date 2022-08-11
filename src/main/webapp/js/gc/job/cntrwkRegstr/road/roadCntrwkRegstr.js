/**
 * 도로 공사대장 객체
 */
// 도로 공사대장 검색
roadCntrwkRegstrObj = {
	
	TITLE : "도로공사대장 검색",
	
	CLASS_NAME : "RoadCntrwkRegstrSearch",
	
	selector : null,
	
	isCreated : false,
	
	initUi : function() {
		var that = this;
		
		// easyUi datebox init
		$("#listRoadCntrwkRegstr .easyui-datebox", that.selector).datebox();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 초기화
		$(".a_reset_roadCntrwkRegstr", that.selector).on("click", function() {
			$("input[name=ftrIdn]", that.selector).val("");
			$("input[name=cntNam]", that.selector).val("");
			$("select[name=wrkCde]", that.selector).val("");
			$("input[name=cttYmdMin]", that.selector).val("");
			$("input[name=cttYmdMax]", that.selector).val("");
			$("input[name=begYmdMin]", that.selector).val("");
			$("input[name=begYmdMax]", that.selector).val("");
			$("input[name=rfnYmdMin]", that.selector).val("");
			$("input[name=rfnYmdMax]", that.selector).val("");
			$("input[name=tctAmtMin]", that.selector).val("");
			$("input[name=tctAmtMax]", that.selector).val("");

			return false;
		});
		
		// 검색
		$(".a_search_roadCntrwkRegstr", that.selector).on("click", function() {
			that.search();
			
			return false;
		});
	},
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/searchRoadCntrwkRegstrPage.do";
		var windowOptions = {
			width : 655,
			height : 265,
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
	
	search : function() {
		var that = this;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/listRoadCntrwkRegstr.do";
		var params = {};
		
		// 공사번호
		params.ftrIdn = $("input[name=ftrIdn]", that.selector).val();
		// 공사명
		params.cntNam = $("input[name=cntNam]", that.selector).val();
		// 공사구분
		params.wrkCde = $("select[name=wrkCde]", that.selector).val();
		// 계약일
		params.cttYmdMin = $("input[name=cttYmdMin]", that.selector).val();
		params.cttYmdMax = $("input[name=cttYmdMax]", that.selector).val();
		// 착공일
		params.begYmdMin = $("input[name=begYmdMin]", that.selector).val();
		params.begYmdMax = $("input[name=begYmdMax]", that.selector).val();
		// 준공일
		params.rfnYmdMin = $("input[name=rfnYmdMin]", that.selector).val();
		params.rfnYmdMax = $("input[name=rfnYmdMax]", that.selector).val();
		// 계약금액
		params.tctAmtMin = $("input[name=tctAmtMin]", that.selector).val();
		params.tctAmtMax = $("input[name=tctAmtMax]", that.selector).val();
		
		$.get(url, params).done(function(response) {
			var data = [];
			var ids = [];
			
			if(response.result.length < 1){
				alert("조건에 맞는 결과가 없습니다.");
				return false;
			}
			else {
				data.push({
					dataAlias : "공사대장",
					dataId : "RDT_CONS_MA",
					ids : ids
				});
				
				for(var j in response.result) {
					ids.push(response.result[j].ftrIdn);
				}
				
				resultObj.addRows(data);
			}
		}).fail(function() {
			alert("공사대장 리스트를 가져오는데 실패하였습니다.");
		});
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
};

/// 도로 공사대장 조회
roadCntrwkRegstrObj.view = {
		
	TITLE : "도로공사대장 상세정보",
	
	CLASS_NAME : "RoadCntrwkRegstrDetail",

	selector : null,
	
	ftrIdn : null,
	
	isCreated : false,
	
	initUi : function() {
		var that = this;
		
		// number olny
		$(".numOnly").keyup(function() {
			$(this).val($(this).val().replace(/[^0-9]/g, ""));
		});
		
		// tab 초기화
		$(".tab-list-1 li a", that.selector).on("click", function() {
			$(".tab-list-1 li, .tab-panel-1", that.selecor).removeClass("active");
			$(this, that.selector).parent().addClass("active");
			$(".tab-panel-1", that.selector).eq($(this, that.selector).parent().index()).addClass("active");
		});
	},
	
	bindEvents : function() {
		var that = this;

		// 테이블 클릭 이벤트 (하자보수내역, 공사비지급내역, 설계변경내역, 하도급내역)
		$(".tableSelector tbody tr", that.selector).click(function() {
			var element = $(this);
			var dataType = element.attr("data-type");
			var ftrIdn = element.attr("data-ftr-idn");
			var shtIdn = element.attr("data-sht-idn");
			var imageNo = element.attr("data-image-no");		//qwe123
			
			if(dataType == "rdtFlawDt"){
				// 하자 보수 내역
				roadCntrwkRegstrObj.roadFlawMendngDtls.open(ftrIdn, shtIdn);
			}
			else if(dataType == "rdtCostDt") {
				// 공사비 지급 내역
				roadCntrwkRegstrObj.roadCntrwkCtPymntDtls.open(ftrIdn, shtIdn);
			}
			else if(dataType == "rdtChngDt") {
				// 설계 변경 내역
				roadCntrwkRegstrObj.roadDsgnChangeDtls.open(ftrIdn, shtIdn);
			}
			else if(dataType == "rdtSubcDt") {
				// 하도급 내역
				roadCntrwkRegstrObj.roadSubcntrDtls.open(ftrIdn, shtIdn);
			}
			else if(dataType = "roadCntrwkImage") {
				// 공사사진
				roadCntrwkRegstrObj.viewPhoto.open(imageNo);
			}
		});
		
		// 편집
		$(".btn_modify_roadCntrwkRegstrDetail", that.selector).on("click", function() {
			var ftrIdn = $("#ftrIdn", that.selector).val();
			
			roadCntrwkRegstrObj.modify.open(ftrIdn);
			that.close();
			
			return false;
		});
	},
	
	open : function(fids) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.ftrIdn = fids;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/" + that.ftrIdn + "/selectOneRoadCntrwkRegstrPage.do";
		var windowOptions = {
			width : 680,
			height : 579,
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
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.ftrIdn = null;
		that.selector = null;
		that.isCreated = false;
	}
};

/// 도로 공사대장 수정
roadCntrwkRegstrObj.modify = {
		
	TITLE : "도로공사대장 수정",
	
	CLASS_NAME : "RoadCntrwkRegstrModify",

	selector : null,
	
	ftrIdn : null,
	
	isCreated : false,
	
	init : function() {
		var that = this;
		
		// number olny
		$(".numOnly").keyup(function() {
			$(this).val($(this).val().replace(/[^0-9]/g, ""));
		});
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					roadCntrwkRegstrObj.view.open(that.ftrIdn);
					that.close();
					alert("공사대장이 수정되었습니다.");
				}
				else {
					alert("공사대장 수정에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function() {
		var that = this;
		
		// easyUi datebox init
		$("#roadCntrwkRegstrModify .easyui-datebox", that.selector).datebox();
		
		// tab 초기화
		$(".tab-list-1 li a", that.selector).on("click", function() {
			$(".tab-list-1 li, .tab-panel-1", that.selecor).removeClass("active");
			$(this, that.selector).parent().addClass("active");
			$(".tab-panel-1", that.selector).eq($(this, that.selector).parent().index()).addClass("active");
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		that.ftrIdn = $("#ftrIdn", that.selector).val();
		
		// 테이블 클릭 이벤트 (하자보수내역, 공사비지급내역, 설계변경내역, 하도급내역)
		$(".tableSelector tbody tr", that.selector).click(function() {
			var element = $(this);
			var dataType = element.attr("data-type");
			var ftrIdn = element.attr("data-ftr-idn");
			var shtIdn = element.attr("data-sht-idn");
			var imageNo = element.attr("data-image-no");	//qwe123
			
			if(dataType == "rdtFlawDt"){
				// 하자 보수 내역 수정
				roadCntrwkRegstrObj.roadFlawMendngDtls.modify.open(ftrIdn, shtIdn);
			}
			else if(dataType == "rdtCostDt") {
				// 공사비 지급 내역 수정
				roadCntrwkRegstrObj.roadCntrwkCtPymntDtls.modify.open(ftrIdn, shtIdn);
			}
			else if(dataType == "rdtChngDt") {
				// 설계 변경 내역 수정
				roadCntrwkRegstrObj.roadDsgnChangeDtls.modify.open(ftrIdn, shtIdn);
			}
			else if(dataType == "rdtSubcDt") {
				// 하도급 내역 수정 수정
				roadCntrwkRegstrObj.roadSubcntrDtls.modify.open(ftrIdn, shtIdn);
			}
			else if(dataType == "roadCntrwkImage") {
				// 공사사진 수정
				roadCntrwkRegstrObj.modifyPhoto.open(imageNo, ftrIdn);
			}
		});
		
		// 하자 보수 내역 등록
		$(".btn_save_roadFlawMendngDtlsAdd", that.selector).on("click", function() {
			roadCntrwkRegstrObj.roadFlawMendngDtls.add.open(that.ftrIdn);
			return false;
		});
		
		// 공사비 지급 내역 등록
		$(".btn_save_roadCntrwkCtPymntDtlsAdd", that.selector).on("click", function() {
			roadCntrwkRegstrObj.roadCntrwkCtPymntDtls.add.open(that.ftrIdn);
			return false;
		});
		
		// 설계 변경 내역 등록
		$(".btn_save_roadDsgnChangeDtlsAdd", that.selector).on("click", function() {
			roadCntrwkRegstrObj.roadDsgnChangeDtls.add.open(that.ftrIdn);
			return false;
		});
		
		// 하도급 내역
		$(".btn_save_roadSubcntrDtlsAdd", that.selector).on("click", function() {
			roadCntrwkRegstrObj.roadSubcntrDtls.add.open(that.ftrIdn);
			return false;
		});
		
		// 공사사진 등록
		$(".btn_photo_roadCntrwkRegstrAdd", that.selector).on("click", function() {
			roadCntrwkRegstrObj.addPhoto.open(that.ftrIdn);
			return false;
		});

		// 저장
		$(".btn_save_roadCntrwkRegstrModify", that.selector).on("click", function() {
			that.modify();
			return false;
		});
	},
	
	open : function(ftrIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.ftrIdn = ftrIdn;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/" + that.ftrIdn + "/modifyRoadCntrwkRegstrPage.do";
		var windowOptions = {
			width : 680,
			height : 579,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.init();
				that.initUi();
				that.bindEvents();
				
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	modify : function() {
		var that = this;
		
		that.ftrIdn = $("#ftrIdn", that.selector).val();
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/" + that.ftrIdn + "/modifyRoadCntrwkRegstr.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.ftrIdn = null;
		that.selector = null;
		that.isCreated = false;
	}
};

/// 도로 공사대장 등록
roadCntrwkRegstrObj.add = {
		
	TITLE : "도로공사대장 등록",
	
	CLASS_NAME : "RoadCntrwkRegstrAdd",

	selector : null,
	
	isCreated : false,
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					that.close();
					alert("공사대장이 등록되었습니다.");
				}
				else {
					alert("공사대장 등록에 실패하였습니다.");
				}
			}
		});
	},
	
	initUi : function() {
		var that = this;
		
		// easyUi datebox init
		$("#roadCntrwkRegstrAdd .easyui-datebox", that.selector).datebox();
		
		// tab 초기화
		$(".tab-list-1 li a", that.selector).on("click", function() {
			$(".tab-list-1 li, .tab-panel-1", that.selecor).removeClass("active");
			$(this, that.selector).parent().addClass("active");
			$(".tab-panel-1", that.selector).eq($(this, that.selector).parent().index()).addClass("active");
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// ehojo 조회
		$(".btn_eHojoSearch_roadCntrwkRegstrAdd", that.selector).on("click", function() {
			that.ehojo();
			return false;
		});

		// 저장
		$(".btn_save_roadCntrwkRegstrAdd", that.selector).on("click", function() {
			that.save();
			return false;
		});
	},
	
	open : function(ftrIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.ftrIdn = ftrIdn;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/addRoadCntrwkRegstrPage.do";
		var windowOptions = {
			width : 680,
			height : 585,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.init();
				that.initUi();
				that.bindEvents();
				
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	ehojo : function() {
		var that = this;
		var cttNum = $("input[name=cttNum]", that.selector).val();
		
		if(cttNum != ""){
			var url = CONTEXT_PATH + "/cntrwkRegstr/road/" + cttNum + "/ehojoInfo.do";
			
			$.get(url).done(function(response) {
				if (response) {
					var data = response.result;
					
					$("input[name=cttNum]", that.selector).val(data.cttNum);
					$("input[name=cntNam]", that.selector).val(data.cntNam);
					$("input[name=cntLoc]", that.selector).val(data.cntLoc);
					$("input[name=svsNam]", that.selector).val(data.svsNam);
					$(".begYmd", that.selector).datebox("setValue", data.begYmd);
					$("input[name=fchNam]", that.selector).val(data.fchNam);
					$(".fnsYmd", that.selector).datebox("setValue",data.fnsYmd);
					$(".fchYmd", that.selector).datebox("setValue",data.fchYmd);
					$(".rfnYmd", that.selector).datebox("setValue",data.rfnYmd);
					$(".bidYmd", that.selector).datebox("setValue",data.bidYmd);
					$(".cttYmd", that.selector).datebox("setValue",data.cttYmd);
					$("input[name=cttCde]", that.selector).val(data.cttCde);
					$("input[name=estAmt]", that.selector).val(data.estAmt);
					$("input[name=tctAmt]", that.selector).val(data.tctAmt);
					$("input[name=gcnNam]", that.selector).val(data.gcnNam);
					$("input[name=pocNam]", that.selector).val(data.pocNam);
					$("input[name=gcnAdr]", that.selector).val(data.gcnAdr);
					$("input[name=gcnTel]", that.selector).val(data.gcnTel);
				} else {
					$.messager.alert(that.TITLE, "e호조 정보를 불러오는 데 실패했습니다.");
				}
			});
		}
		else {
			$.messager.alert(that.TITLE, "e호조번호를 입력하여 주세요.");
		}
	},
	
	save : function() {
		var that = this;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/addRoadCntrwkRegstr.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.isCreated = false;
	}
};

// 도로 공사대장 사진 등록
roadCntrwkRegstrObj.addPhoto = {
		
	TITLE : "도로 공사대장 사진 등록",
	
	CLASS_NAME : "RoadCntrwkRegstrPhotoAdd",
	
	selector : null,
	
	ftrIdn : null,
	
	isCreated : false,
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			beforeSubmit : function(arr, form) {
				for(var i=0, len=arr.length; i < len; i++) {
					var obj = arr[i];
					var name = obj.name;
					
					// 필수 입력 체크
					if(name == "imageSj") {
						if(!obj.value) {
							alert("제목을 입력하여 주십시오.");
							form.find("input[name=" + name + "]").focus();
							return false;
						}
					}
					else if(name == "roadCntrwkPhoto") {
						if(!obj.value) {
							alert("사진을 등록하여 주십시오.");
							return false;
						}
					}
				}
			},
			success : function(result) {
				if(result) {
					roadCntrwkRegstrObj.modify.open(that.ftrIdn);
					that.close();
					alert("도로공사대장 공사사진이 등록되었습니다.");
				}
				else {
					alert("도로공사대장 공사사진 등록이 실패하였습니다.");
				}
			}
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 사진위치
		$("#getLcXY", that.selector).on("click", function() {
			that.getLcXY();
			
			return false;
		});
		
		// 등록
		$("#a_save_roadCntrwkRegstrPhotoAdd", that.selector).on("click", function() {
			that.save();
			
			return false;
		});
	},
	
	open : function(ftrIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.ftrIdn = ftrIdn;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/addRoadCntrwkRegstrPhotoPage.do";
		var windowOptions = {
			width : 430,
			height : 279,
			top : 120,
			left : 330,
			isClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.init();
				that.bindEvents();
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	getLcXY : function() {
		var that = this;
		
		selectObj.once(that.CLASS_NAME, "Point", "drawend", function(evt) {
			var coords = evt.feature.getGeometry().getCoordinates();
			$("input[name=lcX]").val(coords[0]);
			$("input[name=lcY]").val(coords[1]);
		}, false);
	},
	
	save : function() {
		var that = this;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/" + that.ftrIdn + "/addRoadCntrwkRegstrPhoto.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.ftrIdn = null;
		that.isCreated = false;
	}
		
};

//도로 공사대장 사진 상세조회
roadCntrwkRegstrObj.viewPhoto = {
		
	TITLE : "도로 공사대장 사진정보",
	
	CLASS_NAME : "RoadCntrwkRegstrPhotoView",
	
	selector : null,
	
	imageNo : null,
	
	isCreated : false,
	
	open : function(imageNo) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.imageNo = imageNo;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/" + that.imageNo + "/selectOneRoadCntrwkRegstrPhotoPage.do";
		var windowOptions = {
			width : 445,
			height : 529,
			top : 120,
			left : 330,
			isClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.imageNo = null;
		that.isCreated = false;
	}
};

//도로 공사대장 사진 수정
roadCntrwkRegstrObj.modifyPhoto = {
		
	TITLE : "도로 공사대장 사진 수정",
	
	CLASS_NAME : "RoadCntrwkRegstrPhotoModify",
	
	selector : null,
	
	imageNo : null,
	
	ftrIdn : null,
	
	isCreated : false,
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			beforeSubmit : function(arr, form) {
				for(var i=0, len=arr.length; i < len; i++) {
					var obj = arr[i];
					var name = obj.name;
					
					// 필수 입력 체크
					if(name == "imageSj") {
						if(!obj.value) {
							alert("제목을 입력하여 주십시오.");
							form.find("input[name=" + name + "]").focus();
							return false;
						}
					}
				}
			},
			success : function(result) {
				if(result) {
					roadCntrwkRegstrObj.modify.open(that.ftrIdn);
					that.close();
					alert("도로공사대장 공사사진이 수정되었습니다.");
				}
				else {
					alert("도로공사대장 공사사진 수정이 실패하였습니다.");
				}
			}
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 사진위치
		$("#getLcXY", that.selector).on("click", function() {
			that.getLcXY();
			
			return false;
		});
		
		// 수정
		$("#a_save_roadCntrwkRegstrPhotoModify", that.selector).on("click", function() {
			that.save();
			
			return false;
		});
	},
	
	open : function(imageNo, ftrIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.imageNo = imageNo;
		that.ftrIdn = ftrIdn;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/" + that.imageNo + "/modifyRoadCntrwkRegstrPhotoPage.do";
		var windowOptions = {
			width : 430,
			height : 279,
			top : 120,
			left : 330,
			isClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.init();
				that.bindEvents();
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	getLcXY : function() {
		var that = this;
		
		selectObj.once(that.CLASS_NAME, "Point", "drawend", function(evt) {
			var coords = evt.feature.getGeometry().getCoordinates();
			$("input[name=lcX]").val(coords[0]);
			$("input[name=lcY]").val(coords[1]);
		}, false);
	},
	
	save : function() {
		var that = this;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/road/" + that.ftrIdn + "/" + that.imageNo + "/modifyRoadCntrwkRegstrPhoto.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.selector = null;
		that.imageNo = null;
		that.ftrIdn = null;
		that.isCreated = false;
	}
		
};