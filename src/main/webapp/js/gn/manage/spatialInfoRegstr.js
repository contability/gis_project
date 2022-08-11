$(document).ready(function(){
	spatialInfoRegstrObj.init();
});

var spatialInfoRegstrObj = {

	selector : "#div_spatialInfoRegstr_list",
	
	exportVariable : {
		searchCondition : null,
		searchKeyword : null,
		searchStartDt : null,
		searchEndDt : null
	},

	init : function() {
		var that = this;
		
		that.initUi();
		//that.addDialog.init(that);
		that.modifyDialog.init(that);
		that.bindEvents();
	},
	
	initUi : function(){
		var that = this;
		
		var selVal = $('.searchCondition').val();
		that.exportVariable.searchCondition = selVal;
		that.exportVariable.searchStartDt = $("input[name='searchStartDt']").val();
		that.exportVariable.searchEndDt = $("input[name='searchEndDt']").val();
		that.exportVariable.searchKeyword = $("input[name='searchKeyword']").val();
		
		$("#searchStartDt").css("vertical-align","5px");
		$("#searchEndDt").css("vertical-align","5px");
		
		if(selVal == 1) {
			$("input[class='w_240']").attr("type","text");
			$("input[class='w_240']").attr("placeholder","");
			$("#dateSearch").hide();
		}else if(selVal == 2){
			$("input[class='w_240']").attr("type","text");
			$("input[class='w_240']").attr("placeholder","YYYY-MM-DD");
			$("#dateSearch").hide();
		}else{
			$("input[class='w_240']").attr("type","hidden");
			$("#dateSearch").show();
		}
	},

	bindEvents : function() {
		var that = this;
		
		$(".tbl_spatialInfoData_list tbody tr", that.selector).click(function(){
			var element = $(this);
			var regstrSn = element.attr("data-no");
			that.openModifyDialog(regstrSn);
			$(element).addClass("on");
		});
								
		$(".a_search").click(function(){
			var selOpt = $(".searchCondition").val();
			
			if(selOpt == 3) {
				var startDate = $("#searchStartDt").val();
				var endDate = $("#searchEndDt").val();
				
				if(startDate == null || startDate == ''){
					alert("기간을 설정해주세요.");
					return false;
				}
				
				if(endDate == null || endDate == ''){
					alert("기간을 설정해주세요.");
					return false;
				}
			}
			
			that.search(1);
			return false;
		});
		
		$(".searchCondition").change(function(){
			var selVal = $(".searchCondition").val();
			if(selVal == 1) {
				$("input[class='w_240']").attr("type","text");
				$("input[class='w_240']").attr("placeholder","");
				$("#dateSearch").hide();
			} else if(selVal == 2){
				$("input[class='w_240']").attr("type","text");
				$("input[class='w_240']").attr("placeholder","YYYY-MM-DD");
				$("#dateSearch").hide();
			} else {
				$("input[class='w_240']").attr("type","hidden");
				$("#dateSearch").show();
			}
		});
	},
	
	dateValidateChk : function(chkDate){
		if(chkDate == ''){
			return true;
		}else{
			var datePattern = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/;
			
			if(!datePattern.test(chkDate)){
				alert("날짜 형식은 yyyy-MM-dd 입니다.");
				return false;
			}else{
				return true;
			}
		}
	},

	clearSelected : function() {
		var that = this;
		$(".tbl_spatialInfoData_list tbody tr", that.selector).removeClass("on");
	},
	
	search : function(pageIndex) {
		var that = this;
		
		var startDate = $("#searchStartDt").val().replace("-", "").replace("-", "");
		var endDate = $("#searchEndDt").val().replace("-", "").replace("-", "");
		
		if(that.dateValidateChk(startDate)&&that.dateValidateChk(endDate)){
			if(pageIndex){
				$("#pageIndex").val(pageIndex);
			}
			$("form", that.selector).submit();
		}
	},
	
	closeDialog : function() {
		var that = this;
		that.clearSelected();
		that.modifyDialog.close();
	},
	
	openModifyDialog : function(regstrSn) {
		var that = this;
		
		that.closeDialog();
		that.modifyDialog.open(regstrSn);
	},
	
	//redirectPath : function(){
	//	location.href=CONTEXT_PATH+'/manage/spatialInfoData/list.do';
	//}
};

spatialInfoRegstrObj.modifyDialog = {
		
		selector : "#div_spatialInfoRegstr_modify",
		
		parent : null,
		
		regstrSn : null,
		
		printFlag : 1,
		
		init : function(parent){
			var that = this;
			
			that.parent = parent;
			that.initUi();
			that.bindEvents();
		},
		
		initUi : function(){
			var that = this;

			$(that.selector).dialog({
				autoOpen: false,
				height: 790,
				width: 900,
				buttons: {
					"출력" : function(){
						that.print();
					},
					"수정" : function(){
						that.modify();
					},
					//"삭제" : function(){
					//	if(confirm("삭제하시겠습니까?")){
					//		that.remove();
					//	}
					//},
					"닫기" : function(){
						that.close();
					}
				},
				close: function(){
					that.close();
				}
			});
			
			$("form", that.selector).ajaxForm({
				dataType : "json",
				/*beforeSubmit : function(arr, form){
					for(var i=0, len=arr.length; i<len; i++){
						var obj = arr[i];
						var name = obj.name;
						
						
					}
				},*/
				success : function(result){
					if(result && result.rowCount && result.rowCount == 1){
						that.close();
						alert("공간정보관리대장이 수정되었습니다.");
						//that.parent.redirectPath();
						that.parent.search();
					}
					else{
						alert("공간정보관리대장 수정에 실패했습니다.");
					}
				}
			});
			
		},
		
		print : function() {
			var that = this;
			
			var regstrSn = that.regstrSn;
			var printFlag = that.printFlag;
			var url;
			if (printFlag == 1) {
				 url = CONTEXT_PATH + "/clipreport/manage/mngGrphinfoManageRegstr.do?regstrSn="+regstrSn;
			} else {
				url = CONTEXT_PATH + "/clipreport/manage/mngGrphinfoUploadDtls.do?regstrSn="+regstrSn;
			}
						
			popupUtils.open(url, "spatialInfoDataReport", { width : 1120 , height : 800, left : 300, top:150 });
		},
		
		bindEvents : function(){
			var that = this;
			
			// tab 이벤트
			$(".tab-list-1 li a", that.selector).on("click", function() {
				$(".tab-list-1 li, .tab-panel-1", that.selecor).removeClass("active");
				$(this, that.selector).parent().addClass("active");
				that.printFlag = $(this, that.selector).parent().attr("data-no");
				$(".tab-panel-1", that.selector).eq($(this, that.selector).parent().index()).addClass("active");
			});
		},
		
		clear : function(){
			var that = this;
			
			$("input[name=manageNo]", that.selector).val("");
			$("input[name=grphinfoMnfctYear]", that.selector).val("");
			$("input[name=bsnsNm]", that.selector).val("");
			$("textarea[name=bsnsSumry]", that.selector).val("");
			$("input[name=manageRspnber]", that.selector).val("");
			$("input[name=grphinfoUploadDe]", that.selector).val("");
			$("input[name=grphinfoNm]", that.selector).val("");
			$("input[name=servcEntrpsNm]", that.selector).val("");
		},
		
		modify : function(){
			var that = this;
			
			var url = restUtils.getResUrl() + "/modify.do";
				$("form", that.selector).attr("action", url);
				$("form", that.selector).submit();
		},
		
		remove : function(){
			var that = this;
			var url = restUtils.getResUrl() + that.dtaNo + "/remove.do";
			$.post(url).done(function(result){
				if(result && result.rowCount && result.rowCount == 1){
					alert("공간정보 자료제공 대장이 삭제되었습니다.");
					//that.parent.redirectPath();
					that.parent.search(1);
				}
				else{
					alert("공간정보 자료제공 대장 삭제에 실패했습니다.");
				}
			}).fail(function(){
				alert("공간정보 자료제공 대장 삭제에 실패했습니다.");
			});
		},
		
		open : function(regstrSn){
			var that = this;
			
			that.clear();
			that.regstrSn = regstrSn;
			var url = restUtils.getResUrl() + regstrSn + "/select.do";
			
			$.get(url).done(function(result){
				if(result && result.row){
					var data = result.row;
					$("input[name=regstrSn]", that.selector).val(data.regstrSn);
					$("input[name=manageNo]", that.selector).val(data.manageNo);
					$("input[name=grphinfoMnfctYear]", that.selector).val(data.grphinfoMnfctYear);
					$("input[name=bsnsNm]", that.selector).val(data.bsnsNm);
					$("textarea[name=bsnsSumry]", that.selector).val(data.bsnsSumry);
					$("input[name=manageRspnber]", that.selector).val(data.manageRspnber);
					$("input[name=grphinfoUploadDe]", that.selector).val(data.grphinfoUploadDe);
					$("input[name=grphinfoNm]", that.selector).val(data.grphinfoNm);
					$("input[name=servcEntrpsNm]", that.selector).val(data.servcEntrpsNm);
					
					if (result.uploadRows) {
						var tagStr;
						for (var i = 0; i < result.uploadRows.length; i++) {
							tagStr += "<tr>";
							tagStr += "<td>" + result.uploadRows[i].dataId + "</td>";
							tagStr += "<td>" + result.uploadRows[i].dataAlias + "</td>";
							tagStr += "<td>" + numberUtils.formatCurrency(result.uploadRows[i].bfeUploadCo) + "</td>";
							tagStr += "<td>" + numberUtils.formatCurrency(result.uploadRows[i].bfeUploadEt) + "</td>";
							tagStr += "<td>" + numberUtils.formatCurrency(result.uploadRows[i].afeUploadCo) + "</td>";
							tagStr += "<td>" + numberUtils.formatCurrency(result.uploadRows[i].afeUploadEt) + "</td>";
							tagStr += "</tr>";
						}
						$(".atcFileViewArea", that.selector).append(tagStr);
					}
					
					$(that.selector).dialog("open");
				}
				else{
					alert("공간정보관리대장을 불러오는데 실패했습니다.");
				}
			}).fail(function() {
				alert("공간정보관리대장을 불러오는데 실패했습니다.");
			});
		},
		
		close : function(){
			var that = this;
			that.clear();
			$(that.selector).dialog("close");
		}
};
