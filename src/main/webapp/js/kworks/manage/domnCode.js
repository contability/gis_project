$(function() {
	domnCodeObj.init();
});

/**
 * 도메인코드 객체
 * @type {Object}
 */
var domnCodeObj = {
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_domn_code_manage",
	
	/**
	 * 도메인 ID
	 * @type {Strng}
	 */
	domnId : null,
	
	/**
	 * url 주소
	 * @type {Strng}
	 */
	contextPath : null,
	
	/**
	 * 초기화 
	 */
	init : function() {
		var that = this;
		
		that.domnId = domnObj.domnId;
		that.addDialog.init(that);
		that.modifyDialog.init(that);
		that.bindEvents();
		
		var urlArr = restUtils.getResUrl().split("manage");
		that.contextPath =  urlArr[0]+ "manage/domnCode/";
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		$(".a_new_domnCode", that.selector).on("click",function(){
			that.openAddDialog();
		});
		
		// 도메인 코드 목록 선택 시, 하이라이트 & 팝업 표시.
		$("#div_domn_code_list .tbl_domn_list tbody",that.selector).on("click","tr",function() {
			var element = $(this);
			that.openModifyDialog(this);
			$(element).addClass("on");
		});
		
		// 도메인 코드 검색 이벤트
		$("#search_domn_code",that.selector).click(function() {
			that.search();
		});
	},
	
	/**
	 * 검색
	 */
	search : function() {
		var that = this;
		var param = {};
		var url = that.contextPath + "list.do";
		
		that.domnId = domnObj.domnId;
		
		param.searchCondition = "2";
		param.domnId = that.domnId;
		param.codeId = "";
		param.searchKeyword = $(".searchBox input[name=searchKeyword]",that.selector).val();
		$.get(url, param).done(function(result){
			if(result) {
				that.domnCodeListUp(result.listDomnCode);
			}
		}).fail(function() {
			alert("도메인코드 검색에 실패했습니다.");
		});
	},
	
	/**
	 * 선택 해제
	 */
	clearSelected : function() {
		var that = this;
		$("#div_domn_code_list .tbl_domn_list tbody tr", that.selector).removeClass("on");
	},
	
	/**
	 * 등록 팝업 열기
	 */
	openAddDialog : function(domnId) {
		var that = this;
		that.closeDialog();
		that.addDialog.open(domnId);
	},
	
	/**
	 * 수정 팝업 열기
	 */
	openModifyDialog : function(event) {
		var that = this;
		that.closeDialog();
		that.modifyDialog.open(event);
	},
	
	/**
	 * 팝업 닫기
	 */
	closeDialog : function() {
		var that = this;
		that.addDialog.close();
		that.modifyDialog.close();
	},
	
	domnCodeListUp : function(data) {
		var that =  this;
		var htmlStr = "";
		$("#div_domn_code_list table tbody",that.selector).html(htmlStr);
		
		for(var i in data){
			htmlStr += "<tr data-domn-id='" + data[i].domnId + "' data-code-id='" + data[i].codeId + "'data-code-nm='" + data[i].codeNm + "' data-use-at='" + data[i].useAt +"'>";
			htmlStr += "<td>" + data[i].codeId + "</td>";
			htmlStr += "<td>" + data[i].codeNm + "</td>";
			htmlStr += "<td>" + data[i].useAt + "</td>";
			htmlStr += "</tr>";
			
		}
		
		$("#div_domn_code_list table tbody",that.selector).html(htmlStr);
	}
};

/**
 * 도메인 코드 등록 팝업
 * @type {Object}
 */
domnCodeObj.addDialog = {
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_domn_code_add",
	
	/**
	 * 부모 객체 - 도메인 객체
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
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 도메인 등록 dialog set
		$(that.selector).dialog({
			autoOpen : false,
			height : 260,
			width : 700,
			buttons : {
				"등록" : function() {
					that.add();
				},
				"닫기" : function() {
					that.close();
				}
			},
			close : function() {
				that.close();
			}
		});
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		$("#txt_domn_code_add_domnId").val("");
		
		$(".content input[name=codeId]",that.selector).val("");
		$("#txt_domn_code_add_code_id",that.selector).val("");
		$("#txt_domn_code_add_code_nm",that.selector).val("");
		
		$("#div_domn_code_list .tbl_domn_list tbody tr",that.parent.selector).removeClass("bg_blue3");
	},
	
	/**
	 * 도메인코드 등록
	 */
	add : function() {
		
		var that = this;
		var url = that.parent.contextPath + "add.do";
		
		var data = $("form", that.selector).serializeArray();
		for(var i=0, len=data.length; i < len; i++) {
			var obj = data[i];
			var name = obj.name;
			
			//필수 입력 체크
			if(name == "codeId") {
				if(!obj.value) {
					alert("코드 ID를 입력하여 주십시오.");
					$("form", that.selector).find("input[name=" + name + "]").focus();
					return false;
				}
			}else if(name == "codeNm"){
				if(!obj.value) {
					alert("코드 명을 입력하여 주십시오.");
					$("form", that.selector).find("input[name=" + name + "]").focus();
					return false;
				}
			}
		}
		
		$.post(url, data).done(function(result){
			if(!result.errMsg) {
				if(result && result.rowCount && result.rowCount == 1) {
					that.parent.search();
					that.close();
				} else {
					alert("도메인코드 추가에 실패했습니다.");
				}
			} else {
				alert(result.errMsg);
			}
		}).fail(function() {
			alert("도메인코드 추가에 실패했습니다.");
		});
	},
	
	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		that.clear();
		//도메인 id 값
		$("#txt_domn_code_add_domnId",that.selector).val(that.parent.domnId);
		
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
 * 도메인 코드 편집 팝업
 * @type {Object}
 */
domnCodeObj.modifyDialog = {
	/**
	 * 선택자
	 * @type {String}
	 */	
	selector : "#div_domn_code_modify",
	
	/**
	 * 부모 객체 - 도메인 객체
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 코드ID
	 * @type {String}
	 */
	codeId  : null,
	
	/**
	 * 코드 명
	 * @type {String}
	 */
	codeNm  : null,
	
	/**
	 * 사용여부
	 * @type {String}
	 */
	useAt   : null,
	
	/**
	 * 초기화
	 * @param {Object} parent 부모 객체
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;
		that.initUi();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 도메인코드 등록 dialog set
		$(that.selector).dialog({
			autoOpen : false,
			height : 260,
			width : 700,
			buttons : {
				"수정" : function() {
					that.modify();
				},
				"삭제" : function() {
					if(confirm("삭제하시겠습니까?")){
						that.remove();
					}
				},
				"닫기" : function() {
					that.close();
				}
			},
			close : function() {
				that.close();
			}
		});
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		
		that.parent.clearSelected();
		$("#txt_domn_code_modify_domnId").val("");
		
		$(".content input[name=codeId]",that.selector).val("");
		$("#txt_domn_code_modify_code_id",that.selector).val("");
		
		that.codeId = null;
		that.codeNm = null;
		that.useAt = null;
		
		$("#div_domn_code_list .tbl_domn_list tbody tr",that.parent.selector).removeClass("bg_blue3");
	},
	
	/**
	 * 수정
	 */
	modify : function() {
		
		var that = this;
		var url = that.parent.contextPath + that.parent.codeId + "/modify.do";
		
		$.post(url, $("form", that.selector).serialize()).done(function(result) {
			if(result && result.rowCount && result.rowCount == 1){
				that.parent.search();
				that.close();
			} else {
				alert("선택한 도메인 수정에 실패했습니다.");
			}
		}).fail(function() {
			alert("선택한 도메인 수정에 실패했습니다.");
		});
	},
	
	/**
	 * 삭제
	 */
	remove : function() {
		var that = this;
		var url = that.parent.contextPath + that.codeId + "/remove.do";
		var param = {};
		
		param.domnId = that.parent.domnId;
		param.codeId = that.codeId;
		
		$.post(url,param).done(function(result) {
			if(result && result.rowCount && result.rowCount == 1) {
				that.parent.search();
				that.close();
			} else {
				alert("선택한 도메인 삭제에 실패했습니다.");
			}
		}).fail(function() {
			alert("선택한 도메인 삭제에 실패했습니다.");
		});
		
		$(that.selector).dialog("open");
	},
	
	/**
	 * 열기
	 */
	open : function(event) {
		var that = this;
		that.clear();
		
		that.codeId = $(event).attr("data-code-id");
		that.codeNm = $(event).attr("data-code-nm");
		that.useAt  = $(event).attr("data-use-at");
		
		//도메인 id 값
		$("#txt_domn_code_modify_domn_id",that.selector).val(that.parent.domnId);
		
		$(".content #txt_domn_code_modify_code_id",that.selector).text(that.codeId);
		$(".content input[name=codeId]",that.selector).val(that.codeId);
		
		$(".content input[name=codeNm]",that.selector).val(that.codeNm);
		$(".content select[name=useAt]",that.selector).val(that.useAt);
		
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