$(function() {
	domnObj.init();
});

/**
 * 도메인 객체
 * @type {Object}
 */
var domnObj = {
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_domn_manage",
	
	/**
	 * 도메인ID
	 * @type {String}
	 */
	domnId : null,
	
	/**
	 * 도메인 명
	 * @type {String}
	 */
	domnNm : null,
	
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
		var selectAtDomn = false;
		
		// 도메인 목록 선택 시, 하이라이트 & 코드 목록 표시. 
		$("#div_domn_list table tr").click(function() {
			
			if(!selectAtDomn) {
				// 편집, 삭제 버튼 표시.
				$(".a_modify_domn").removeClass("display_n");
				$(".a_delete_domn").removeClass("display_n");
				
				// 도메인 선택 안내 문구 제거
				$(".a_blank").addClass("display_n");
				
				// 도메인 코드 목록 표시
				$("#div_domn_code_manage form").removeClass("display_n");
				
				// 도메인 선택 중
				selectAtDomn = true;
			}
			
			// 기존 선택된 도메인의 코드 display_n
			$("#div_domn_code_list table .display_y").removeClass("display_y").addClass("display_n");
			
			// 기존 하이라이트 지우기
			$("#div_domn_list table tr").removeClass("bg_blue3");
			$("#div_domn_code_list table tr").removeClass("bg_blue3");
			
			// 하이라이트 주기
			$(this).addClass("bg_blue3");
			
			// 선택한 도메인의 코드에 display_y 주기
			$("#div_domn_code_list table [data-domn-id=" + $("#div_domn_list table .bg_blue3").attr("data-domn-id") +"]").removeClass("display_n").addClass("display_y");
			
			that.domnId = $(this).attr("data-domn-id");
			that.domnNm = $(this).attr("data-domn-nm");
			
			$(".searchBox input[name=searchKeyword]","#div_domn_code_manage").val("");
			domnCodeObj.search();
		});
		
		// 도메인 검색 검색 이벤트
		$("#search_domn").click(function() {
			that.search();
		});
		
		
		// 추가버튼 이벤트
		$(".a_new_domn").click(function() {
			that.openAddDialog();
		});
		
		// 편집버튼 이벤트
		$(".a_modify_domn").click(function() {
			that.openModifyDialog();
		});
		
		
		//form 엔터키 submit 방지
		$("input:text").keydown(function(e) {
			if(e.keyCode == 13){
				if(e.target.id == "searchKeyword_domn"){
					that.search();
				}else if(e.target.id == "searchKeyword_domn_code"){
					domnCodeObj.search();
					return false;
				}else{
					return false;
				}
			}
	    });
	},
	
	/**
	 * 도메인 검색
	 */
	search : function() {
		var that = this;
		$("form", that.selector).submit();
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
	 */
	openModifyDialog : function(domnId) {
		var that = this;
		that.closeDialog();
		that.modifyDialog.open(domnId);
	},
	
	/**
	 * 팝업 닫기
	 */
	closeDialog : function() {
		var that = this;
		that.addDialog.close();
		that.modifyDialog.close();
	}
};

/**
 * 도메인 등록 팝업 객체
 * @type {Object}
 */
domnObj.addDialog = {
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_domn_add",
	
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
		
		domnCodeObj.init();
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 도메인 등록 dialog set
		$(that.selector).dialog({
			autoOpen : false,
			height : 230,
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
		$("input[name=domnId]", that.selector).val("");
		$("input[name=domnNm]", that.selector).val("");
	},
	
	add : function() {
		var that = this;
		var url = restUtils.getResUrl() + "add.do";
		
		var data = $("form", that.selector).serializeArray();
		for(var i=0, len=data.length; i < len; i++) {
			var obj = data[i];
			var name = obj.name;
			
			//필수 입력 체크
			if(name == "domnId") {
				if(!obj.value) {
					alert("도메인 ID를 입력하여 주십시오.");
					$("form", that.selector).find("input[name=" + name + "]").focus();
					return false;
				}
			}else if(name == "domnNm"){
				if(!obj.value) {
					alert("도메인 명을 입력하여 주십시오.");
					$("form", that.selector).find("input[name=" + name + "]").focus();
					return false;
				}
			}
		}
		
		$.post(url, data).done(function(result){
			if(!result.errMsg) {
				if(result && result.rowCount && result.rowCount == 1) {
					that.parent.search();
				} else {
					alert("도메인 추가에 실패했습니다.");
				}
			} else {
				alert(result.errMsg);
			}
		}).fail(function() {
			alert("도메인 추가에 실패했습니다.");
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
 * 도메인 편집 팝업 객체
 * @type {Object}
 */
domnObj.modifyDialog = {
	/**
	 * 선택자
	 * @type {String}
	 */	
	selector : "#div_domn_modify",
	
	/**
	 * 부모 객체 - 도메인 객체
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 도메인ID
	 * @type {String}
	 */
	domnId : null,
	
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
		// 도메인명 수정 dialog set
		$(that.selector).dialog({
			autoOpen : false,
			height : 220,
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
		that.domnId = null;
		
		$(".content span",that.selector).text("");
		$(".content input[name=domnId]",that.selector).val("");
		$(".content input[name=domnNm]",that.selector).val("");
	},
	
	/**
	 * 수정
	 */
	modify : function() {
		var that = this;
		var url = restUtils.getResUrl() + that.domnId + "/modify.do";
		
		$.post(url, $("form", that.selector).serialize()).done(function(result) {
			if(result && result.rowCount && result.rowCount == 1){
				that.parent.search();
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
		var url = restUtils.getResUrl() + that.domnId + "/remove.do";
		
		$.post(url).done(function(result) {
			if(result && result.rowCount && result.rowCount == 1) {
				that.parent.search();
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
	open : function() {
		var that = this;
		
		var domnId = $("#div_domn_list .bg_blue3").attr("data-domn-id");
		var domnNm = $("#div_domn_list .bg_blue3").attr("data-domn-nm");
		
		that.domnId = domnId;
		
		//도메인 id 값
		$(".content span",that.selector).text(domnId);
		$(".content input[name=domnId]",that.selector).val(domnId);
		
		//도메인 명
		$(".content input[name=domnNm]",that.selector).val(domnNm);
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