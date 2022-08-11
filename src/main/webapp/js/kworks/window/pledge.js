
//서약서
windowObj.pledgeObj = {
	selector : '#div_pledge',
	
	TITLE : '보안서약서',
	
	CLASS_NAME : 'PLEDGE',
	
	init : function(){
		var that = this;
		
		$(that.selector).dialog({
			autoOpen: false,
			height: 720,
			width: 1300,
			closed: false,
			shadow: true,
			resizable: false,
			position: 'center',
			modal: true,
			buttons:{
				"동의":function(){
					that.submit();
				},
				"취소":function(){
					that.close();
				}
			}
		});
		
		that.chk();
		that.bindEvents();
	},
	
	chk : function(){
		var that = this;
		
		$.ajax({
			type: 'GET',
			url: CONTEXT_PATH + '/pledge/chk.do',
			datatype: 'json',
			success : function(rs){
				if(!rs.result){
					$(that.selector).dialog('open');
				}
			},
			error : function(e){
				
			}
		});
	},
	
	open : function(){
		var that = this;
		
		$.ajax({
			type: 'GET',
			url : CONTEXT_PATH + '/pledge/page.do',
			datatype : 'json',
			success : function(rs){
				var row = rs.row;
					
				$("#pledge_dept").html(row.deptNm);
				$("#pledge_nm").html(row.userNm);
			},
			error : function(e){
				console.log(e);
			}
			
		});
		
		$(that.selector).dialog('option','position',{my:"center", at:"center", of:window});
		$(that.selector).dialog('open');
		
		$('button[title="Close"]').remove();
	},
	
	bindEvents : function(){
		$(".chkContents").change(function(){
			if($(this).is(":checked")){
				$(this).parent().attr('class','on_green');
			}else{
				$(this).parent().attr('class','off_green');
			}
		});
	},
	
	close : function(){
		var that = this;
		
		$(that.selector).dialog('close');
		location.href = CONTEXT_PATH + '/j_spring_security_logout';
	},
	
	submit : function(){
		var that = this;
		
		if(that.chkData()){
			var url = CONTEXT_PATH + '/pledge/insert.do';
			
			var data = {
				deptNm : $("#pledge_dept").html(),
				userNm : $("#pledge_nm").html(),
				pldClf : $("input[name='pledge_clf']").val(),
				pldOfp : $("input[name='pledge_ofp']").val()
			};
			
			$.ajax({
				type : 'POST',
				data : data,
				datatype : 'json',
				url : url,
				success : function(rs){
					alert("서비스 이용이 가능합니다");
					$(that.selector).dialog('close');
				},
				error : function(e){
					console.log(e);
				}
			});
		}
	},
	
	chkData : function(){
		var isChk = true;
		
		var pldDate = $('.pledge_date');
		var chkContents = $('.chkContents');
		var pldClf = $('input[name="pledge_clf"]').val();
		var pldOfp = $('input[name="pledge_ofp"]').val();
		
		for(var i = 0; i < pldDate.length; i++){
			if(pldDate[i].value == '' || pldDate[i].value == null){
				alert('날짜를 적어주세요');
				isChk = false;
				break;
			}
		}
		
		for(var i = 0; i < chkContents.length; i++){
			if(!chkContents[i].checked){
				alert('내용을 체크해주세요');
				isChk = false;
				break;
			}
		}
		
		if(pldClf == '' || pldClf == null){
			alert('직급을 적어주세요');
			$('input[name="pledge_clf"]').focus();
			isChk = false;
		}else if(pldOfp == '' || pldOfp == null){
			alert('직위를 적어주세요');
			$('input[name="pledge_ofp"]').focus();
			isChk = false;
		}
		
		return isChk;
	}
};