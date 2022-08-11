
$(document).ready(function(){
	spatialInfoDataObj.init();
});

var spatialInfoDataObj = {

	selector : "#div_spatialInfoData_list",
	
	exportVariable : {
		searchCondition : null,
		searchKeyword : null,
		startDate : null,
		endDate : null
	},

	init : function() {
		var that = this;
		
		that.initUi();
		that.addDialog.init(that);
		that.modifyDialog.init(that);
		that.bindEvents();
	},
	
	initUi : function(){
		var that = this;
		
		var selVal = $(".w_120").val();
		
		that.exportVariable.searchCondition = selVal;
		that.exportVariable.startDate = $("input[name='startDate']").val();
		that.exportVariable.endDate = $("input[name='endDate']").val();
		that.exportVariable.searchKeyword = $("input[name='searchKeyword']").val();
		
		$("#startDate").css("vertical-align","5px");
		$("#endDate").css("vertical-align","5px");
		
		if(selVal > 3){
			$("input[class='w_240']").attr("type","hidden");
			$("#dateSearch").show();
		}else if(selVal == 2){
			$("input[class='w_240']").attr("type","text");
			$("input[class='w_240']").attr("placeholder","YYYY-MM-DD");
			$("#dateSearch").hide();
		}else{
			$("input[class='w_240']").attr("type","text");
			$("input[class='w_240']").attr("placeholder","");
			$("#dateSearch").hide();
		}
	},

	bindEvents : function() {
		var that = this;
		
		$(".tbl_spatialInfoData_list tbody tr", that.selector).click(function(){
			var element = $(this);
			var dtaNo = element.attr("data-no");
			that.openModifyDialog(dtaNo);
			$(element).addClass("on");
		});
		
		$(".a_new_spatialInfoData", that.selector).click(function(){
			that.openAddDialog();
		});
		
		$(".a_clipreport_export", that.selector).click(function(){
			
			var searchCondition = that.exportVariable.searchCondition;
			var startDate = that.exportVariable.startDate;
			var endDate = that.exportVariable.endDate;
			var searchKeyword = that.exportVariable.searchKeyword;
			
			var url = CONTEXT_PATH + "/clipreport/manage/spatialInfoData.do?searchCondition="+searchCondition;
			
			if(startDate && endDate){
				url += "&startDate="+startDate+"&endDate="+endDate;
			}else if(searchKeyword){
				url += "&searchKeyword="+searchKeyword;
			}
			
			popupUtils.open(url, "spatialInfoDataReport", { width : 1120 , height : 800, left : 300, top:150 });
		});
		
		$(".a_search").click(function(){
			var selOpt = $(".w_120").val();
			
			if(selOpt > 3){
				var startDate = $("#startDate").val();
				var endDate = $("#endDate").val();
				
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
		
		$(".w_120").change(function(){
			var selVal = $(".w_120").val();
			that.exportVariable.searchCondition = selVal;
			
			if(selVal > 3){
				$("input[class='w_240']").attr("type","hidden");
				$("input[class='w_240']").val("");
				$("#dateSearch").show();
			}else if(selVal == 2){
				$("input[class='w_240']").attr("type","text");
				$("input[class='w_240']").attr("placeholder","YYYY-MM-DD");
				$("#dateSearch").hide();
			}else{
				$("input[class='w_240']").attr("type","text");
				$("input[class='w_240']").attr("placeholder","");
				$("#startDate").val("");
				$("#endDate").val("");
				$("#dateSearch").hide();
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
		
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		
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
		that.addDialog.close();
	},
	
	openAddDialog : function() {
		var that = this;
		that.closeDialog();
		that.addDialog.open();
	},
	
	openModifyDialog : function(dtaNo) {
		var that = this;
		that.closeDialog();
		that.modifyDialog.open(dtaNo);
	}
};
spatialInfoDataObj.addDialog = {
		selector : "#div_spatialInfoData_add",
		
		parent : null,
		
		init : function(parent){
			var that = this;
			
			that.parent = parent;
			that.initUi();
		},
		
		initUi : function(){
			var that = this;
			
			$(that.selector).dialog({
				autoOpen: false,
				height: 790, 
				width: 900,
				buttons: {
					"등록" : function(){
						that.add();
					},
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
				success : function(result){
					if(result && result.rowCount && result.rowCount == 1){
						that.close();
						alert("공간정보 자료제공 대장이 등록되었습니다.");
						that.parent.search();
						
					}
					else{
						alert("공간정보 자료제공 대장 등록에 실패했습니다.");
					}
				}
			});
		},
		
		clear : function(){
			var that = this;
			$("input[name=rptDt]", that.selector).val("");
			$("input[name=provdDt]", that.selector).val("");
			$("input[name=infoGrad]", that.selector).val("");
			$("input[name=dtlsDtaKnd]", that.selector).val("");
			$("input[name=dtlsInfoSize]", that.selector).val("");
			$("input[name=dtlsUsePurps]", that.selector).val("");
			$("input[name=dtlsProvdType]", that.selector).val("");
			$("input[name=dtlsFee]", that.selector).val("");
			$("input[name=recptrPsitn]", that.selector).val("");
			$("input[name=recptrBrthdy]", that.selector).val("");
			$("input[name=recptrNm]", that.selector).val("");
			$("input[name=recptMeth]", that.selector).val("");
			$("input[name=ipttInfo]", that.selector).val("");
			
		},
		
		add : function(){
			var that = this;
			
			var rptDt = $("#txt_spatialInfoData_add_rptDt").val();
			var provdDt = $("#txt_spatialInfoData_add_provdDt").val();
			var recptrBrthdy = $("#txt_spatialInfoData_add_recptrBrthdy").val();
			
			if(that.parent.dateValidateChk(rptDt)&&that.parent.dateValidateChk(provdDt)&&that.parent.dateValidateChk(recptrBrthdy)){
				$("form", that.selector).submit();
			}
		},
		
		open : function(){
			var that = this;
			$(that.selector).dialog("open");
		},
		
		close : function(){
			var that = this;
			that.clear();
			$(that.selector).dialog("close");
		},
		
		dateValidateChk : function(chkDate){
			var datePattern = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/;
			
			if(!datePattern.test(chkDate)){
				alert("날짜 형식은 yyyy-MM-dd 입니다.");
				return false;
			}else{
				return true;
			}
		}
};
spatialInfoDataObj.modifyDialog = {
		
		selector : "#div_spatialInfoData_modify",
		
		parent : null,
		
		dtaNo : null,
		
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
					"수정" : function(){
						that.modify();
					},
					"삭제" : function(){
						if(confirm("삭제하시겠습니까?")){
							that.remove();
						}
					},
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
				success : function(result){
					if(result && result.rowCount && result.rowCount == 1){
						that.close();
						alert("공간정보 자료제공 대장이 수정되었습니다.");
						that.parent.search();
					}
					else{
						alert("공간정보 자료제공 대장 수정에 실패했습니다.");
					}
				}
			});
			
		},
		
		bindEvents : function(){
			
		},
		
		clear : function(){
			var that = this;
			$("input[name=rptDt]", that.selector).val("");
			$("input[name=provdDt]", that.selector).val("");
			$("input[name=infoGrad]", that.selector).val("");
			$("input[name=dtlsDtaKnd]", that.selector).val("");
			$("input[name=dtlsInfoSize]", that.selector).val("");
			$("input[name=dtlsUsePurps]", that.selector).val("");
			$("input[name=dtlsProvdType]", that.selector).val("");
			$("input[name=dtlsFee]", that.selector).val("");
			$("input[name=recptrPsitn]", that.selector).val("");
			$("input[name=recptrBrthdy]", that.selector).val("");
			$("input[name=recptrNm]", that.selector).val("");
			$("input[name=recptMeth]", that.selector).val("");
			$("input[name=ipttInfo]", that.selector).val("");
		},
		
		modify : function(){
			var that = this;
			
			var rptDt = $("#txt_spatialInfoData_modify_rptDt").val();
			var provdDt = $("#txt_spatialInfoData_modify_provdDt").val();
			var recptrBrthdy = $("#txt_spatialInfoData_modify_recptrBrthdy").val();
			
			if(that.parent.dateValidateChk(rptDt)&&that.parent.dateValidateChk(provdDt)&&that.parent.dateValidateChk(recptrBrthdy)){
				var url = restUtils.getResUrl() + "/modify.do";
				$("form", that.selector).attr("action", url);
				$("form", that.selector).submit();
			}
		},
		
		remove : function(){
			var that = this;
			var url = restUtils.getResUrl() + that.dtaNo + "/remove.do";
			$.post(url).done(function(result){
				if(result && result.rowCount && result.rowCount == 1){
					alert("공간정보 자료제공 대장이 삭제되었습니다.");
					that.parent.search(1);
				}
				else{
					alert("공간정보 자료제공 대장 삭제에 실패했습니다.");
				}
			}).fail(function(){
				alert("공간정보 자료제공 대장 삭제에 실패했습니다.");
			});
		},
		
		open : function(dtaNo){
			var that = this;
			that.clear();
			
			that.dtaNo = dtaNo;
			var url = restUtils.getResUrl() + dtaNo + "/select.do";
			
			$.get(url).done(function(result){
				if(result && result.row){
					var data = result.row;
					$("input[name=dtaNo]", that.selector).val(data.dtaNo);
					$("#txt_spatialInfoData_modify_manageNo", that.selector).html(data.manageNo);
					$("input[name=rptDt]", that.selector).val(data.rptDt);
					$("input[name=provdDt]", that.selector).val(data.provdDt);
					$("input[name=infoGrad]", that.selector).val(data.infoGrad);
					$("input[name=dtlsDtaKnd]", that.selector).val(data.dtlsDtaKnd);
					$("input[name=dtlsInfoSize]", that.selector).val(data.dtlsInfoSize);
					$("input[name=dtlsUsePurps]", that.selector).val(data.dtlsUsePurps);
					$("input[name=dtlsProvdType]", that.selector).val(data.dtlsProvdType);
					$("input[name=dtlsFee]", that.selector).val(data.dtlsFee);
					$("input[name=recptrPsitn]", that.selector).val(data.recptrPsitn);
					$("input[name=recptrBrthdy]", that.selector).val(data.recptrBrthdy);
					$("input[name=recptrNm]", that.selector).val(data.recptrNm);
					$("input[name=recptMeth]", that.selector).val(data.recptMeth);
					$("input[name=ipttInfo]", that.selector).val(data.ipttInfo);
					
					$(that.selector).dialog("open");
				}
				else{
					alert("공간정보 자료제공 대장을 불러오는데 실패했습니다.");
				}
			}).fail(function(result){
				alert("공간정보 자료제공 대장을 불러오는데 실패했습니다.");
			});
		},
		
		close : function(){
			var that = this;
			that.clear();
			$(that.selector).dialog("close");
		}
};
