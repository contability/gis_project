/**
 * 상수 공사대장 객체
 */
// 상수 공사대장 검색
wrppCntrwkRegstrObj = {
	
	TITLE : "상수공사대장 검색",
	
	CLASS_NAME : "WrppCntrwkRegstrSearch",
	
	selector : null,
	
	isCreated : false,
	
	initUi : function() {
		var that = this;
		
		// easyUi datebox init
		$("#listWrppCntrwkRegstr .easyui-datebox", that.selector).datebox();
	},
	
	bindEvents : function() {
		var that = this;
		
		// 초기화
		$(".a_reset_wrppCntrwkRegstr", that.selector).on("click", function() {
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
		$(".a_search_wrppCntrwkRegstr", that.selector).on("click", function() {
			that.search();
			
			return false;
		});
	},
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wrpp/searchWrppCntrwkRegstrPage.do";
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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wrpp/listWrppCntrwkRegstr.do";
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
					dataId : "WTT_CONS_MA",
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

/// 상수 공사대장 조회
wrppCntrwkRegstrObj.view = {
		
	TITLE : "상수공사대장 상세정보",
	
	CLASS_NAME : "WrppCntrwkRegstrDetail",

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
			var imageNo = element.attr("data-image-no");
			
			if(dataType == "wttFlawDt"){
				// 하자 보수 내역
				wrppCntrwkRegstrObj.wrppFlawMendngDtls.open(ftrIdn, shtIdn);
			}
			else if(dataType == "wttCostDt") {
				// 공사비 지급 내역
				wrppCntrwkRegstrObj.wrppCntrwkCtPymntDtls.open(ftrIdn, shtIdn);
			}
			else if(dataType == "wttChngDt") {
				// 설계 변경 내역
				wrppCntrwkRegstrObj.wrppDsgnChangeDtls.open(ftrIdn, shtIdn);
			}
			else if(dataType == "wttSubcDt") {
				// 하도급 내역
				wrppCntrwkRegstrObj.wrppSubcntrDtls.open(ftrIdn, shtIdn);
			}
			else if(dataType = "wrppCntrwkImage") {
				// 공사사진
				wrppCntrwkRegstrObj.viewPhoto.open(imageNo);
			}
		});
		
		// 편집
		$(".btn_modify_wrppCntrwkRegstrDetail", that.selector).on("click", function() {
			var ftrIdn = $("#ftrIdn", that.selector).val();
			
			wrppCntrwkRegstrObj.modify.open(ftrIdn);
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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wrpp/"+ that.ftrIdn + "/selectOneWrppCntrwkRegstrPage.do";
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

/// 상수 공사대장 수정
wrppCntrwkRegstrObj.modify = {
		
	TITLE : "상수공사대장 수정",
	
	CLASS_NAME : "WrppCntrwkRegstrModify",

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
					wrppCntrwkRegstrObj.view.open(that.ftrIdn);
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
		$("#wrppCntrwkRegstrModify .easyui-datebox", that.selector).datebox();
		
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
			var imageNo = element.attr("data-image-no");
			
			if(dataType == "wttFlawDt"){
				// 하자 보수 내역 수정
				wrppCntrwkRegstrObj.wrppFlawMendngDtls.modify.open(ftrIdn, shtIdn);
			}
			else if(dataType == "wttCostDt") {
				// 공사비 지급 내역 수정
				wrppCntrwkRegstrObj.wrppCntrwkCtPymntDtls.modify.open(ftrIdn, shtIdn);
			}
			else if(dataType == "wttChngDt") {
				// 설계 변경 내역 수정
				wrppCntrwkRegstrObj.wrppDsgnChangeDtls.modify.open(ftrIdn, shtIdn);
			}
			else if(dataType == "wttSubcDt") {
				// 하도급 내역 수정 수정
				wrppCntrwkRegstrObj.wrppSubcntrDtls.modify.open(ftrIdn, shtIdn);
			}
			else if(dataType == "wrppCntrwkImage") {
				// 공사사진 수정
				wrppCntrwkRegstrObj.modifyPhoto.open(imageNo, ftrIdn);
			}
		});
		
		// 하자 보수 내역 등록
		$(".btn_save_wrppFlawMendngDtlsAdd", that.selector).on("click", function() {
			wrppCntrwkRegstrObj.wrppFlawMendngDtls.add.open(that.ftrIdn);
			return false;
		});
		
		// 공사비 지급 내역 등록
		$(".btn_save_wrppCntrwkCtPymntDtlsAdd", that.selector).on("click", function() {
			wrppCntrwkRegstrObj.wrppCntrwkCtPymntDtls.add.open(that.ftrIdn);
			return false;
		});
		
		// 설계 변경 내역 등록
		$(".btn_save_wrppDsgnChangeDtlsAdd", that.selector).on("click", function() {
			wrppCntrwkRegstrObj.wrppDsgnChangeDtls.add.open(that.ftrIdn);
			return false;
		});
		
		// 하도급 내역
		$(".btn_save_wrppSubcntrDtlsAdd", that.selector).on("click", function() {
			wrppCntrwkRegstrObj.wrppSubcntrDtls.add.open(that.ftrIdn);
			return false;
		});
		
		// 공사사진 등록
		$(".btn_photo_wrppCntrwkRegstrAdd", that.selector).on("click", function() {
			wrppCntrwkRegstrObj.addPhoto.open(that.ftrIdn);
			return false;
		});

		// 저장
		$(".btn_save_wrppCntrwkRegstrModify", that.selector).on("click", function() {
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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wrpp/" + that.ftrIdn + "/modifyWrppCntrwkRegstrPage.do";
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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wrpp/" + that.ftrIdn + "/modifyWrppCntrwkRegstr.do";
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

/// 상수 공사대장 등록
wrppCntrwkRegstrObj.add = {
		
	TITLE : "상수공사대장 등록",
	
	CLASS_NAME : "WrppCntrwkRegstrAdd",

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
		$("#wrppCntrwkRegstrAdd .easyui-datebox", that.selector).datebox();
		
		// tab 초기화
		$(".tab-list-1 li a", that.selector).on("click", function() {
			$(".tab-list-1 li, .tab-panel-1", that.selecor).removeClass("active");
			$(this, that.selector).parent().addClass("active");
			$(".tab-panel-1", that.selector).eq($(this, that.selector).parent().index()).addClass("active");
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장
		$(".btn_save_wrppCntrwkRegstrAdd", that.selector).on("click", function() {
			that.save();
			
			return false;
		});
	},
	
	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wrpp/addWrppCntrwkRegstrPage.do";
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
	
	save : function() {
		var that = this;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wrpp/addWrppCntrwkRegstr.do";
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

// 상수 공사대장 사진 등록
wrppCntrwkRegstrObj.addPhoto = {
		
	TITLE : "상수 공사대장 사진 등록",
	
	CLASS_NAME : "WrppCntrwkRegstrPhotoAdd",
	
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
					else if(name == "wrppCntrwkPhoto") {
						if(!obj.value) {
							alert("사진을 등록하여 주십시오.");
							return false;
						}
					}
				}
			},
			success : function(result) {
				if(result) {
					wrppCntrwkRegstrObj.modify.open(that.ftrIdn);
					that.close();
					alert("상수공사대장 공사사진이 등록되었습니다.");
				}
				else {
					alert("상수공사대장 공사사진 등록이 실패하였습니다.");
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
		$("#a_save_wrppCntrwkRegstrPhotoAdd", that.selector).on("click", function() {
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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wrpp/addWrppCntrwkRegstrPhotoPage.do";
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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wrpp/" + that.ftrIdn + "/addWrppCntrwkRegstrPhoto.do";
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

//상수 공사대장 사진 상세조회
wrppCntrwkRegstrObj.viewPhoto = {
		
	TITLE : "상수 공사대장 사진정보",
	
	CLASS_NAME : "WrppCntrwkRegstrPhotoView",
	
	selector : null,
	
	imageNo : null,
	
	isCreated : false,
	
	open : function(imageNo) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.imageNo = imageNo;
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wrpp/" + that.imageNo + "/selectOneWrppCntrwkRegstrPhotoPage.do";
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

//상수 공사대장 사진 수정
wrppCntrwkRegstrObj.modifyPhoto = {
		
	TITLE : "상수 공사대장 사진 수정",
	
	CLASS_NAME : "WrppCntrwkRegstrPhotoModify",
	
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
					wrppCntrwkRegstrObj.modify.open(that.ftrIdn);
					that.close();
					alert("상수공사대장 공사사진이 수정되었습니다.");
				}
				else {
					alert("상수공사대장 공사사진 수정이 실패하였습니다.");
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
		$("#a_save_wrppCntrwkRegstrPhotoModify", that.selector).on("click", function() {
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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wrpp/" + that.imageNo + "/modifyWrppCntrwkRegstrPhotoPage.do";
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
		
		var url = CONTEXT_PATH + "/cntrwkRegstr/wrpp/" + that.ftrIdn + "/" + that.imageNo + "/modifyWrppCntrwkRegstrPhoto.do";
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