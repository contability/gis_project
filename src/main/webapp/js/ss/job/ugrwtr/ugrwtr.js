/**
 * 지하수관정 객체
 * @type {Object}
 */
var ugrwtrObj = {

	TITLE : "지하수관정 검색",
	
	CLASS_NAME : "UndergroundWater",

	selector : null,

	bindEvents : function() {
		var that = this;
		// 검색
		$("div", that.selector).on("click", ".btn_search4", function() {
			that.search();
		});

		// 닫기
		$("div", that.selector).on("click", ".btn_close", function() {
			that.close();
		});
	},

	open : function() {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/ugrwtr/page.do";
		var windowOptions = {
			width : 600,
			height : 230,
			top : 120,
			left : 340,
			isClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, that.params, windowOptions, function() {
			that.bindEvents();
			that.getBjdCde();
		});
		
		that.selector = "#" + id;
	},
	
	// 법정동
	getBjdCde : function() {
		dongObj.getPromise().done(
			function() {
				var data = dongObj.getData();
				$.each(data, function(k, v) {
					$(".search select[name = dvopLocRegnCode]").append('<option value="' + v.bjdCde + '">' + v.bjdNam + '</option>');
				});
			}
		);
	},

	search : function() {
		var that = this;
		
		resultObj.close();
		
		var url = CONTEXT_PATH + "/ugrwtr/list.do";
		var params = {};
		
		// 관리번호
		params.mgNo = $("input[name=mgNo]", that.selector).val();
		// 구분
		params.rgtMbdGbn = $("select[name=rgtMbdGbn]", that.selector).val();
		// 시설구분
		params.permNtFormGbn = $("select[name=permNtFormGbn]", that.selector).val();
		// 세부용동
		params.uwaterDtlSrv = $("select[name=uwaterDtlSrv]", that.selector).val();
		// 상호(성명)
		params.rgtMbdNm = $("input[name=rgtMbdNm]", that.selector).val();
		// 사업자번호(법인)
		params.rgtMbdRegNo = $("input[name=rgtMbdRegNo]", that.selector).val();
		// 법정동 코드
		params.dvopLocRegnCode = $("select[name=dvopLocRegnCode]", that.selector).val();
		// 개발위치_본번
		params.dvopLocBunji = $("input[name=dvopLocBunji]", that.selector).val();
		// 개발위치_부번
		params.dvopLocHo = $("input[name=dvopLocHo]", that.selector).val();
		
		$.get(url, params).done(function(response) {
			var data = [];
			var ids = [];
			
			data.push({
				dataAlias : "지하수관정",
				dataId : "BML_WELL_PS",
				ids : ids
			});
			
			for(var j in response.result) {
				ids.push(response.result[j].objectid);
			}
			
			resultObj.addRows(data);
		}).fail(function() {
			alert("지하수관정 리스트를 가져오는데 실패하였습니다.");
		});
	},

	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
	}
};

ugrwtrObj.view = {
	TITLE : "지하수관정 상세조회",
	
	CLASS_NAME : "UndergroundWaterDetail",

	selector : null,
	
	objectid : null,
	
	open : function(fids) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		objectid = fids;
		
		var url = CONTEXT_PATH + "/ugrwtr/" + objectid + "/selectOne.do";
		var windowOptions = {
			width : 630,
			height : 600,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, objectid, windowOptions, function() {
		});
		
		that.selector = "#" + id;
		
	},
	
	close : function() {
		var that = this;
		
		objectid = null;
		windowObj.removeWindow(that.selector);
	}
};