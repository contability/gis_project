/*
 설문 조사 modal
 */
windowObj.surveyObj = {

	selector : "#div_survey",

	TITLE : "설문 조사",

	CLASS_NAME : "Survey",

	// 문항 수
	questionCount : 11,

	init : function() {
		var that = this;
		
		// 팝업 창 속성
		$(that.selector).dialog({
			title: that.TITLE,
			autoOpen: true,
			height: 800,
			width: 1000,
			closed: false,
			shadow: true,
			resizable: false,
			position: 'center',
			modal: true,
			buttons: {
				"등록": function(){
					that.submit();
				},
				"취소": function(){
					that.close();
				}
			}
		});
		
		
		that.bindEvents();
		//that.radioChk();
		
		$.ajax({
			type: 'get',
			url: CONTEXT_PATH + '/surveySelect.do',
			datatype: 'text',
			success: function(result){
				if(!result.row){
					$(that.selector).dialog("close");
					
				}else{
					that.open();
				}
			},
			error: function(e){
				alert(e);
			}
		});
		
		
	},
	
	open : function(){
		var that = this;
		
		$(that.selector).dialog('option','position',{my:"center",at:"center",of:window});
		$(that.selector).dialog("open");
		
		
		$("button[title='Close'").remove();
	},
	
	/*
	 	//라디오 버튼 1번 체크
	 	radioChk : function(){
		var that = this;
		
		for(var i = 1; i <= that.questionCount; i++){
			$("input[name='qst"+ i +"']").eq(0).attr('checked','checked');
		}
		
		$("button[title='Close'").remove();
	},*/
	
	bindEvents : function(){
		
		// 라디오버튼 관련
		$("input:radio").click(function(){
			
			var chkedAnswer = $(this).val();
			
			if(chkedAnswer.indexOf('etcetera') >= 0){
				var thisName = $(this).attr("name");
				$("#"+thisName+"-concat").attr("disabled",true);
				$("#"+chkedAnswer).attr("disabled",false);
				$("#"+chkedAnswer).focus();
			}else if(chkedAnswer.indexOf("concat") >= 0){
				var thisName = $(this).attr("name");
				$("#"+thisName+"-etcetera").attr("disabled",true);
				$("#"+chkedAnswer).attr("disabled",false);
				$("#"+chkedAnswer).focus();
			}else{
				var thisName = $(this).attr("name");
				$("#"+thisName+"-etcetera").attr("disabled",true);
				$("#"+thisName+"-concat").attr("disabled",true);
			}
		});
		
		// 체크 박스 관련
		$("input:checkbox").click(function(){
			var chkedAnswer = $(this).val();
			var chkedBtn = $(this).is(":checked");
			
			if(chkedAnswer.indexOf("etcetera") >= 0){
				if(chkedBtn){
					$("#"+chkedAnswer).attr("disabled",false);
					$("#"+chkedAnswer).focus();
				}else{
					$("#"+chkedAnswer).attr("disabled",true);
				}
				
			}
		});
			
		
	},
	
	// 답변 체크(모든 답변은 필수)
	requiredAnswer : function(answer){
		var chk = false;
		if(typeof answer == 'string' && answer != '' && answer != '______'){
			chk = true;
		}
		
		return chk;
	},
	
	// 제출
	submit : function(){
		var that = this;
		
		var url = CONTEXT_PATH + '/surveyInsert.do';
		var data = {};
		
		// 문항 수 만큼 반복
		for(var i = 1; i <= that.questionCount; i++){
			var answer = $("input[name='qst"+i+"']");
			var answerType = answer.prop("type");
			var joinStr = [];
			
			if(answer.length == 0){
				// 테이블로 된 문항들일 때
				var tableQstCount = $("input[class='qst"+i+"']").attr("tableQstCount");
				var answerCount = 0;
				
				$("input[class='qst"+i+"']").each(function(index,item){
					if(item.checked){
						joinStr.push(item.value);
						answerCount++;
					}else if(item.type == 'text'){
						joinStr.push(item.value);
					}
				});
				answer = joinStr.join('___');
				if(!that.requiredAnswer(answer) || tableQstCount != answerCount){
					alert(i+"번째 문항을 체크 해주세요.");
					return false;
				}
			}
			else if(answer.length == 1){
				// 1개의 텍스트 문항일 때
				answer = $("input[name='qst"+i+"']").val();
				if(!that.requiredAnswer(answer)){
					alert(i+"번째 문항을 체크 해주세요.");
					return false;
				}
			}else if(answer.length > 1){
				// 라디오 버튼일 때
				if(answerType == 'radio'){
					$("input[name='qst"+i+"']").each(function(index,item){
						if(item.checked){
							var itemChk = item.value;
							if(itemChk.indexOf("etcetera") >= 0){
								answer = $("#"+itemChk).val();
							}else if(itemChk.indexOf("concat") >= 0){
								var prefix = $("#"+itemChk+"-prefix").html();
								var suffix = $("#"+itemChk+"-suffix").html();
								answer = prefix + $("#"+itemChk).val() + suffix;
								
							}
							else{
								answer = itemChk;
							}
						}
					});
					
					if(!that.requiredAnswer(answer)){
						alert(i+"번째 문항을 체크 해주세요.");
						return false;
					}
				
				// 2개 이상의 텍스트 문항일 때
				}else if(answerType == 'text'){
					$("input[name='qst"+i+"']").each(function(index,item){
						joinStr.push(item.value);
					});
					answer = joinStr.join('___');
					
					if(!that.requiredAnswer(answer)){
						alert(i+"번째 문항을 체크 해주세요.");
						return false;
					}
					
				// 체크 박스일 때
				}else if(answerType == 'checkbox'){
					$("input[name='qst"+i+"']").each(function(index,item){
						if(item.checked){
							var itemChk = item.value;
							if(itemChk.indexOf("etcetera") >= 0){
								joinStr.push($("#"+itemChk).val());
							}else{
								joinStr.push(itemChk);
							}
						}
					});
					answer = joinStr.join('___');
					
					if(!that.requiredAnswer(answer)){
						alert(i+"번째 문항을 체크 해주세요.");
						return false;
					}
				}
			}
			
			data['qst'+i] = answer;
			
		}
		
		$.ajax({
			type: 'post',
			url: url,
			data: data,
			datatype: "text",
			success: function(result){
				if(result.rowCount > 0){
					/*$.messager.alert(that.TITLE, "참여 해주셔서 감사합니다.");*/
					$(that.selector).dialog("close");
					alert("참여 해주셔서 감사합니다.");
				}else{
					alert("설문 조사 오류");
				}
			},
			error: function(e){
				alert(e);
				alert("설문 조사 오류");
			}
		});
	},

	close : function(){
		var that = this;
		
		$(that.selector).dialog("close");
		//window.open('about:blank','_self').self.close();
		//location.href = CONTEXT_PATH + "/logout.do";
		//window.open('','_parent').parent.close();
		location.href = CONTEXT_PATH + '/j_spring_security_logout';
	}
};