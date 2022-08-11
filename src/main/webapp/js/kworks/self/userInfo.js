$(function() {
	userInfoObj.init();
});

var userInfoObj = {
		
	selector : "#div_userInfo_list",
	
	init : function() {
		var that = this;
		that.bindEvents();
		that.modifyDialog.init(that);
	},
	
	bindEvents : function() {
		var that = this;
		
		// 개인정보 관리 창 열기
		$(".tbl_userInfo_list tbody tr", that.selector).click(function() {
			var element = $(this);
			var userId = element.attr("data-user-id");
			that.openModifyDialog(userId);
		});
	},
	
	search : function(pageIndex) {
		var that = this;
		$("form", that.selector).submit();
	},
	
	openModifyDialog : function(userId) {
		var that = this;
		that.closeDialog();
		that.modifyDialog.open(userId);
	},
	
	closeDialog : function() {
		var that = this;
		that.modifyDialog.close();
	}
};

//개인정보 관리 수정 팝업
userInfoObj.modifyDialog = {
		
		selector : "#div_userInfo_modify",
		
		parent : null,
		
		userId : null,
		
		init : function(parent) {
			var that = this;
			that.parent = parent;
			
			that.initUi();
		},
		
		initUi : function() {
			var that = this;
			
			$(that.selector).dialog({
				autoOpen : false,
				width : 700,
				height : 400,
				buttons : {
					"수정" : function() {
						that.modify();
					},
					"닫기" : function() {
			        	that.close();
			        }
				},
				close : function() {
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
						alert("개인정보 관리 수정에 실패했습니다.");
					}
				}
			});
			
			$(".div_userInfo_modify_password").hide();
		},
		
		open : function(userId) {
			var that = this;
			
			that.userId = userId;
			var url = restUtils.getResUrl() + userId + "/select.do?nocache="+Math.random();
			$.get(url).done(function(result) {
				if(result && result.data) {
					var data = result.data;
					
					if(data.userType == "CRDNS_USER" || data.userType =="SEAOLL_USER") {
						$(".div_userInfo_modify_password").show();
						$(that.selector).dialog("option", "height", 400);
					}
					
					//사용자 아이디
					$(".userId", that.selector).html(data.userId);
					//사용자명
					$(".userNm", that.selector).html(data.userNm);
					//패스워드
					$(".div_userInfo_modify_password").show();
					$(that.selector).dialog("option", "height", 550);
					//패스워드 확인
					$(".div_userInfo_modify_password_check").show();
					$(that.selector).dialog("option", "height", 550);
					//부서
					$("select[name=deptCode]").val(data.deptCode);
					
					$(that.selector).dialog("open");
				} else {
					alert("개인정보 관리를 불러오는데 실패했습니다.");
				}
			}).fail(function(result) {
				alert("개인정보 관리를 불러오는데 실패했습니다.");
			});
		},
		
		//수정
		modify : function() {
			var that = this;
			if($("input[name=password]").val() == $("input[name=password_check]").val()){
				var url = restUtils.getResUrl() + that.userId + "/modify.do";
				
				$("form", that.selector).attr("action", url);
				$("form", that.selector).submit();
				$(that.selector).dialog("close");
				
				alert("개인정보 수정이 완료되었습니다.");
				
			} else {
				alert("패스워드가 일치하지 않습니다.");
			}
		},
		
		//닫기
		close : function() {
			var that = this;
			$("#txt_userInfo_modify_password").val("");
			$(".div_userInfo_modify_password").hide();
			$(that.selector).dialog("close");
		}
};

