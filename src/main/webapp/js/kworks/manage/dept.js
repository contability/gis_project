$(function() {
	deptObj.init();
});

/**
 * 부서 객체
 * @type {Object}
 */
var deptObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_dept_list",
	
	/**
	 * 초기화 
	 */
	init : function() {
		var that = this;
		that.addDialog.init(that);
		that.modifyDialog.init(that);
		that.bindEvents();
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 부서 항목 선택
		$(".tbl_dept_list tbody tr", that.selector).click(function() {
			var element = $(this);
			var deptCode = element.attr("data-dept-code");
			that.openModifyDialog(deptCode);
			(element).addClass("on");
		});

		// 부서 등록 창 열기
		$(".a_new_dept", that.selector).click(function() {
			that.openAddDialog();
	    });
		
		// excel download
		$(".a_excelDownload_dept", that.selector).click(function() {
			that.excelDownload(1);
		});
	},
	
	/**
	 * 검색
	 * @param {Number} pageIndex 페이지 번호
	 */
	search : function(pageIndex) {
		var that = this;
		if(pageIndex) {
			$("#pageIndex").val(pageIndex);
		}
		$("form", that.selector).submit();
	},
	
	/**
	 * 엑셀다운로드
	 * @param {Number} pageIndex 페이지 인덱스
	 */
	excelDownload : function(pageIndex) {
		var that = this;
		
		$("form", that.selector).attr("action", restUtils.getResUrl() + "excel.do");
		$("form", that.selector).submit();

		// 기존 검색 url로 변경
		$("form", that.selector).attr("action", restUtils.getResUrl() + "list.do");
	},
	
	/**
	 * 선택 해제
	 */
	clearSelected : function() {
		var that = this;
		$(".tbl_dept_list tbody tr", that.selector).removeClass("on");
	},
	
	/**
	 * 팝업 창 닫기
	 */
	closeDialog : function() {
		var that = this;
		that.modifyDialog.close();
		that.addDialog.close();
	},
	
	/**
	 * 등록 팝업 열기
	 */
	openAddDialog : function() {
		var that = this;
		that.closeDialog();
		that.addDialog.open();
	},
	
	/**
	 * 수정 팝업 열기
	 * @param deptCode
	 */
	openModifyDialog : function(deptCode) {
		var that = this;
		that.closeDialog();
		that.modifyDialog.open(deptCode);
	}
	
};

/**
 * 부서 등록 팝업
 * @type {Object}
 */
deptObj.addDialog = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_dept_add",
	
	/**
	 * 부모 객체 - 부서 객체
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 초기화
	 * @param {Object} parent 부모 객체
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
		
		that.initUi();
		that.bindEvents();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 부서 등록 dialog set
		$(that.selector).dialog({
			autoOpen: false,
	      	width: 700,
	      	height: 225,
	      	buttons: {
		    	"등록" : function() {
		    		that.add();
		    	},
		        "닫기" : function() {
		        	that.close();
		        }
			},
	      	close: function() {
	      		that.close();
	      	}
	    });
		
	},
	
	bindEvents : function() {
		var that = this;
		// 엔터 시 등록
		$("input[name=deptNm]", that.selector).keyup(function(event) {
			var keyCode = event.keyCode ? event.keyCode : event.which;
			if(keyCode == 13) {
				that.add();
			}
		});
		
		// input 이 1개 일 경우 enter 키로 submit 되는 것을 막음
		$("form", that.selector).submit(function() {
			return false;
		});
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		$("input[name=deptCode]", that.selector).val("");
		$("input[name=deptNm]", that.selector).val("");
	},
	
	/**
	 * 등록
	 */
	add : function() {
		var that = this;
		var url = restUtils.getResUrl() + "add.do";
		
		var data = $("form", that.selector).serializeArray();
		for(var i=0, len=data.length; i < len; i++) {
			var obj = data[i];
			var name = obj.name;
			
			// 필수 입력 체크
			if(name == "deptCode") {
				if(!obj.value) {
					alert("부서코드를 입력하여 주십시오.");
					$("form", that.selector).find("input[name=" + name + "]").focus();
					return false;
				}
			}
		}
		
		$.post(url, data).done(function(result){
			if(!result.errMsg){
				if(result && result.rowCount && result.rowCount == 1) {
					that.parent.search();
				}
				else {
					alert("부서 추가에 실패했습니다.");
				}
			}else{
				alert(result.errMsg);
			}
		}).fail(function() {
			alert("부서 추가에 실패했습니다.");
		});
		
	},
	
	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		$(that.selector).dialog("open");
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		that.clear();
		$(that.selector).dialog("close");
	}
		
};

/**
 * 부서 수정 팝업
 * @type {Object}
 */
deptObj.modifyDialog = {
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_dept_modify",
		
	/**
	 * 부모 객체 - 부서 객체
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 부서코드
	 * @type {Object}
	 */
	deptCode : null,
		
	/**
	 * 초기화
	 * @param {Object} parent  부모 객체
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
		
		that.initUi();
		that.bindEvents();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 부서코드 수정 dialog set
		$(that.selector).dialog({
			autoOpen: false,
			width: 700,
	     	height: 225,
	      	buttons: {
		    	"수정" : function() {
		    		that.modify();
		    	},
		    	"삭제" : function() {
		    		if(confirm("삭제하시겠습니까?")) {
		    			that.remove();
		    		}
		    	},
		        "닫기" : function() {
		        	that.close();
		        }
			},
	      	close: function() {
	      		that.close();
	      	}
	    });
		
		
	},
	
	bindEvents : function() {
		var that = this;
		// 엔터 시 수정
		$("input[name=deptNm]", that.selector).keyup(function(event) {
			var keyCode = event.keyCode ? event.keyCode : event.which;
			if(keyCode == 13) {
				that.modify();
			}
		});
		
		// input 이 1개 일 경우 enter 키로 submit 되는 것을 막음
		$("form", that.selector).submit(function() {
			return false;
		});
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;

		that.parent.clearSelected();
		that.deptCode = null;
		
		$(".deptCode", that.selector).text("");
		$("input[name=deptNm]", that.selector).val("");
	},
	
	/**
	 * 수정
	 */
	modify : function() {
		var that = this;
		var url = restUtils.getResUrl() + that.deptCode + "/modify.do";
		$.post(url,$("form", that.selector).serialize()).done(function(result) {
			if(result && result.rowCount && result.rowCount == 1) {
				that.parent.search();
			}
			else {
				alert("선택한 부서 수정에 실패했습니다.");
			}
		}).fail(function() {
			alert("선택한 부서 수정에 실패했습니다.");
		});
	},
	
	/**
	 * 삭제
	 */
	remove : function() {
		var that = this;
		var url = restUtils.getResUrl() + that.deptCode + "/remove.do";
		$.post(url).done(function(result) {
			if(result && result.rowCount && result.rowCount == 1) {
				that.parent.search(1);
			}
			else {
				alert("선택한 부서 삭제에 실패했습니다.");
			}
		}).fail(function() {
			alert("선택한 부서 삭제에 실패했습니다.");
		});
	},
	
	/**
	 * 열기
	 * @param {String} deptCode 부서코드
	 */
	open : function(deptCode) {
		var that = this;
		that.deptCode = deptCode;
		
		var url = restUtils.getResUrl() + that.deptCode + "/select.do";
		var param = {};
		$.get(url,param).done(function(result){
			var data = result.data;
			$(".deptCode", that.selector).text(data.deptCode);
			$("input[name=deptNm]", that.selector).val(data.deptNm);
		});
		
		$(that.selector).dialog("open");
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		that.clear();
		$(that.selector).dialog("close");
	}
		
};