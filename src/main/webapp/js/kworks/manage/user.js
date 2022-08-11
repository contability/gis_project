$(function() {
	userObj.init();
});

/**
 * 사용자 객체
 * @type {Object}
 */
var userObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_user_list",
	
	/**
	 * 초기화 
	 */
	init : function() {
		var that = this;
		that.initUi();
		that.bindEvents();
		that.addDialog.init(that);
		that.modifyDialog.init(that);
	},	
	
	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		// 검색 키워드 표시 변경
		that.changeSearchCondition();
		
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		// 검색
		$(".a_search", that.selector).click(function() {
			that.search(1);
			return false;
		});
		
		// excel download
		$(".a_excelDownload_user", that.selector).click(function() {
			that.excelDownload(1);
		});
		
		// 새올 부서/사용자 정보 연계
		$(".a_sync_saeol", that.selector).click(function() {
			that.syncSaeol();
		});
		
		// 사용자 등록 창 열기
		$(".a_new_user", that.selector).click(function() {
			that.openAddDialog();
		});
		
		// 사용자 수정 창 열기
		$(".tbl_user_list tbody tr", that.selector).click(function() {
			var element = $(this);
			var userId = element.attr("data-user-id");
			that.openModifyDialog(userId);
			$(element).addClass("on");
		});
		
		// 검색 조건 변경
		$("#searchCondition", that.selector).change(function() {
			that.changeSearchCondition();
		});
		
	},
	
	/**
	 * 검색 조건 변경 시 이벤트
	 */
	changeSearchCondition :function() {
		var that = this;
		var searchCondition = $("#searchCondition", that.selector).val();
		if(searchCondition == 4) {
			$("#searchKeyword", that.selector).hide();
			$("#authorGroupNo", that.selector).show();
		}
		else {
			$("#searchKeyword", that.selector).show();
			$("#authorGroupNo", that.selector).hide();
		}
	},
	
	/**
	 * 검색
	 * @param {Number} pageIndex 페이지 인덱스
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
	
	syncSaeol : function() {
		var that = this;
		if(confirm("새올을 연계하여 부서 및 사용자 정보를 갱신합니다. 진행하시겠습니까?")) {
			var url = CONTEXT_PATH + "/manage/user/syncSaeol.do";
			$.post(url).done(function(response) {
				if(response == "CONTACT_SUCCESS") {
					alert("부서 및 사용자 정보가 갱신 되었습니다.");
					that.search(1);
				}
				else {
					alert("부서 및 사용자 정보 연계에 실패했습니다.");
				}
			}).fail(function() {
				alert("부서 및 사용자 정보 연계에 실패했습니다.");
			});
		}
	},
	
	/**
	 * 선택 해제
	 */
	clearSelected : function() {
		var that = this;
		$(".tbl_user_list tbody tr", that.selector).removeClass("on");
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		$("input[name=searchKeyword]").val("");
	},
	
	/**
	 * 등록 팝업 열기
	 * @param 
	 */
	openAddDialog : function() {
		var that = this;
		that.closeDialog();
		that.addDialog.open();
	},
	
	/**
	 * 수정 팝업 열기
	 * @param userId
	 */
	openModifyDialog : function(userId) {
		var that = this;
		that.closeDialog();
		that.modifyDialog.open(userId);
	},
	
	/**
	 * 팝업 창 닫기
	 */
	closeDialog : function() {
		var that = this;
		that.addDialog.close();
		that.modifyDialog.close();
	}
	
};

/**
 * 사용자 관리 수정 팝업
 * @type {Object}
 */
userObj.modifyDialog = {
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_user_modify",
		
	/**
	 * 부모 객체 - 사용자 관리 객체
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 사용자 아이디
	 * @type {Object}
	 */
	userId : null,
		
	/**
	 * 초기화
	 * @param {Object} parent  부모 객체
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
		
		// 사용자 관리 수정 다이어로그 설정
		$(that.selector).dialog({
			autoOpen: false,
			width: 700,
	     	height: 510,
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
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result && result.rowCount && result.rowCount == 1) {
					that.parent.search();
				}
				else {
					alert("사용자 정보 수정에 실패했습니다.");
				}
			}
		});
		
		$(".div_user_modify_password").hide();
	},
	
	/**
	 * 열기
	 * @param {Number} userId 사용자 아이디
	 */
	open : function(userId) {
		var that = this;
		that.parent.clearSelected();
		
		that.userId = userId;
		var url = restUtils.getResUrl() + userId + "/select.do?nocache="+Math.random();
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;

				// 유관기관 사용자일 경우
				if(data.userType == "CRDNS_USER") {
					$(".div_user_modify_password").show();
					$(that.selector).dialog("option", "height", 550);
				}
				
				// 사용자 아이디
				$(".userId", that.selector).html(data.userId);
				// 사용자 명
				$("#txt_user_modify_userNm", that.selector).val(data.userNm);
				// 패스워드
				$(".password", that.selector).val(data.password);
				// 사용자 등급
				$("select[name=userGrad]", that.selector).val(data.userGrad);
				// 사용자 부서
				var deptCodeOpts = $('.select_user_modify_deptCode option', that.selector);
				for(var i = 0; i < deptCodeOpts.length; i++){
					if(data.deptCode == deptCodeOpts[i].value){
						deptCodeOpts[i].setAttribute('selected', 'selected');
					}
				}
				// 사용자 분류
				var usrTypeOpts = $('.select_user_modify_userType option', that.selector);
				for(var i = 0; i < usrTypeOpts.length; i++){
					if(data.userType == usrTypeOpts[i].value){
						usrTypeOpts[i].setAttribute('selected', 'selected');
					}
				}
				// 권한 그룹
				$(".edit_author_group", that.selector).prop("checked", false);
				for(var i=0, len=data.kwsAuthorGroups.length; i < len; i++) {
					var authorGroup = data.kwsAuthorGroups[i];
					$("#group_edit_" + authorGroup.authorGroupNo, that.selector).prop("checked", true);
				}
				$(that.selector).dialog("open");
			}
			else {
				alert("사용자 정보를 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("사용자 정보를 불러오는데 실패했습니다.");
		});
		
	},
	
	/**
	 * 수정
	 */
	modify : function() {
		var that = this;
		if($("input[name=authorGroup]:checked", that.selector).length > 0) {
			var url = restUtils.getResUrl() + that.userId + "/modify.do";
			$("form", that.selector).attr("action", url);
			$("form", that.selector).submit();
		}
		else {
			alert("권한 그룹을 1개 이상 선택하여 주십시오.");
		}
	},
	
	/**
	 * 삭제
	 */
	remove : function() {
		var that = this;
		var url = restUtils.getResUrl() + that.userId + "/remove.do";
		
		$.post(url).done(function(result) {
			if(result && result.rowCount && result.rowCount == 1) {
				that.parent.search(1);
			}
			else {
				alert("사용자 삭제에 실패했습니다.");
			}
		}).fail(function() {
			alert("사용자 삭제에 실패했습니다.");
		});
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		that.parent.clearSelected();
		$(that.selector).dialog( "option", "height", 510 );
		$("#txt_user_modify_password").val("");
		$(".div_user_modify_password").hide();
		$(that.selector).dialog("close");
	}
};

/**
 * 사용자 관리 등록 팝업
 * @type {Object}
 */
userObj.addDialog = {
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_user_add",
		
	/**
	 * 부모 객체 - 사용자 관리 객체
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 초기화
	 * @param {Object} parent  부모 객체
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
		
		// 사용자 관리 수정 다이어로그 설정
		$(that.selector).dialog({
			autoOpen: false,
			width: 700,
	     	height: 600,
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
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result && result.rowCount && result.rowCount == 1) {
					that.parent.search();
				}
				else {
					alert("사용자 등록에 실패했습니다.");
				}
			}
		});
	},
	
	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		$(that.selector).dialog("open");
		$("#group_add_1", that.selector).prop("checked", true);
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
			if(name == "userId") {
				if(!obj.value) {
					alert("사용자 아이디를 입력하여 주십시오.");
					$("form", that.selector).find("input[name=" + name + "]").focus();
					return false;
				}
			}
			else if(name == "userNm") {
				if(!obj.value) {
					alert("사용자 명을 입력하여 주십시오.");
					$("form", that.selector).find("input[name=" + name + "]").focus();
					return false;
				}
			}
			else if(name == "password") {
				if(!obj.value) {
					alert("패스워드를 입력하여 주십시오.");
					$("form", that.selector).find("input[name=" + name + "]").focus();
					return false;
				}
			}
		}
		
		// 사용자 권한 체크
		if($("input[name=authorGroup]:checked", that.selector).length == 0) {
			alert("권한 그룹을 1개 이상 선택하여 주십시오.");
			return false;
		}
		
		$.post(url, data).done(function(result){
			if(!result.errMsg){
				if(result && result.rowCount && result.rowCount == 1) {
					that.parent.search();
				}
				else {
					alert("사용자 등록에 실패했습니다.");
				}
			}else{
				alert(result.errMsg);
			}
		}).fail(function() {
			alert("사용자 등록에 실패했습니다.");
		});
		
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		$("#txt_user_add_userId").val("");
		$("#txt_user_add_userNm").val("");
		$("#txt_user_add_password").val("");
		$(that.selector).dialog("close");
	}
};
