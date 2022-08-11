$(function() {
	
	/// 아이디 엔터 키 이벤트
	$("#j_username").keypress(function(event){
		if(event.keyCode == 13){
			loginSubmit();
		}
	});
	
	/// 패스워드 엔터 키 이벤트
	$("#j_password").keypress(function(event){
		if(event.keyCode == 13){
			loginSubmit();
		}
	});	
	
	/// 로그인
	$("#a_submit_login").click(function() {
		loginSubmit();
	});
});

// login event
/// 유관기관사용자 및 admin만 로그인 가능
function loginSubmit() {
	var id = $("#j_username").val();
	var pw = $("#j_password").val();
	
	if(!id){
		alert("아이디를 입력하세요.");
		$("#j_username").focus();
		return;
	} else if(!pw){
		alert("비밀번호를 입력하세요.");
		$("#j_password").focus();
		return;
	}
	
	if (id == 'admin') {
		$("#frm_login").submit();
		return false;
	} else {
		var url = CONTEXT_PATH + '/getUserType.do?userId=' + id;
		$.ajax({
			type : "GET",
			dataType : 'json',
			url : url,
			async : false,
			success : function(response) {
				var userType = response.userType;
				if (userType != 'CRDNS_USER') {
					alert('로그인할 수 없는 사용자 입니다.');
					$('#j_username').val('');
					$('#j_password').val('');
					return;
				}
				else {
					$("#frm_login").submit();
					return false;
				}
			},
			fail : function() {
				alert('다시 로그인을 시도해 주십시요.');
				return;
			}
		});
	}
};