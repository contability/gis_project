$(function(){
	userAcntMngRegstrObj.init();
});

var userAcntMngRegstrObj = {
		
		selector : '#userAcntMngRegstr_list',
		
		exportVariable : {
			searchCondition : null,
			searchkeyword : null,
			searchStartDt : null,
			searchEndDt : null
		},
		
		init : function(){
			var that = this;
			
			that.bindEvents();
			userAcntMngRegstrObj.update.init();
		},
		
		bindEvents : function(){
			var that = this;
			
			$('.a_search', that.selector).click(function(){
				that.search(1);
				return false;
			});
			
			$('.datepicker', that.selector).datepicker({
				dateFormat:'yy-mm-dd'
			});
			
			$('.date1month', that.selector).click(function(){
				$('input[name=searchStartDt]', that.selector).val(that.getMonth(-1));
				$('input[name=searchEndDt]', that.selector).val(that.getToday());
			});
			
			$('.date3month', that.selector).click(function(){
				$('input[name=searchStartDt]', that.selector).val(that.getMonth(-3));
				$('input[name=searchEndDt]', that.selector).val(that.getToday());
			});
			
			$('.date6month', that.selector).click(function(){
				$('input[name=searchStartDt]', that.selector).val(that.getMonth(-6));
				$('input[name=searchEndDt]', that.selector).val(that.getToday());
			});
			
			$('.a_excelDownload_user_author_hist', that.selector).click(function(){
				that.print();
			});
			
			$('.tbl_user_author_hist_list tbody tr', that.selector).click(function(){
				var sn = $(this).attr('sn');
				userAcntMngRegstrObj.update.open(sn);
			});
		},
		
		getToday : function(){
			var today = new Date();
			var year = today.getFullYear();
			var month = today.getMonth() + 1;
			var day = today.getDate();
			var todayStr = year + '-' + month + '-' + day;
			
			return todayStr;
		},
		
		getMonth : function(month){
			var today = new Date();
			
			today.setMonth(today.getMonth() + month);
			today.setDate(today.getDate() +1);
			
			var year = today.getFullYear();
			var month = today.getMonth() + 1;
			var day = today.getDate();
			var todayStr = year + '-' + month + '-' + day;
			
			return todayStr;
		},
		
		search : function(pageIndex){
			var that = this;
			
			if(pageIndex){
				$('#pageIndex').val(pageIndex);
			}
			
			$('form', that.selector).submit();
		},
		
		excelDownload : function(pageIndex){
			var that = this;
			
			$('form', that.selector).attr('action', restUtils.getResUrl() + 'excel.do');
			$('form', that.selector).submit();
			
			$('form', that.selector).attr('action', restUtils.getResUrl() + 'list.do');
		},
		
		print : function(){
			var that = this;
			
			var searchCondition = $('#searchCondition', that.selector).val();
			var searchKeyword = $('#searchKeyword', that.selector).val();
			var searchStartDt = $('input[name=searchStartDt]', that.selector).val();
			var searchEndDt = $('input[name=searchEndDt]', that.selector).val();
			
			
			var url = CONTEXT_PATH + '/clipreport/manage/mngUserAuthorHist.do';
			url += '?';
			url += 'searchCondition=' + searchCondition + '&';
			url += 'searchKeyword=' + searchKeyword + '&';
			url += 'searchStartDt=' + searchStartDt + '&';
			url += 'searchEndDt=' + searchEndDt;
			
			url = encodeURI(url);
			
			popupUtils.open(url, "mngUserAuthorHistReport", {width : 1120, height : 800, left : 300, top : 150});
		},
		
		clear : function(){
			$('input[name=searchKeyword]').val('');
		}
};


userAcntMngRegstrObj.update = {
	
		selector : '#userAcntMngRegstr_modify',
		
		sn : null,
		
		isClosed : true,
		
		open : function(sn){
			var that = this;
			
			if(!that.isClosed){
				that.close();
			}
			
			$('input[name=sn]', that.selector).val(sn);
			
			var url = CONTEXT_PATH + '/manage/userAcntMngRegstr/' + sn + '/updateMngUserAuthorHistPage.do';
			
			$.ajax({
				url : url,
				type : 'GET',
				dataType : 'json',
				success : function(rs){
					if(rs && rs.row){
						var row = rs.row;
						
						$('#txt_userAcntMngRegstr_modify_deptNm', that.selector).html(row.deptNm);
						$('#txt_userAcntMngRegstr_modify_userNm', that.selector).html(row.userNm);
						$('#txt_userAcntMngRegstr_modify_gradAlwncDt', that.selector).html(row.gradAlwncDt);
						$('#txt_userAcntMngRegstr_modify_gradUserNm', that.selector).html(row.gradUserNm);
						
						$('#txt_userAcntMngRegstr_modify_authorGroupNm', that.selector).val(row.authorGroupNm);
						$('#txt_userAcntMngRegstr_modify_prcrCn', that.selector).val(row.prcrCn);
						$('#txt_userAcntMngRegstr_modify_jobNm', that.selector).val(row.jobNm);
					}else{
						alert('사용자 권한부여 이력을 불러오는데 실패했습니다.');
					}
				},
				error : function(err){
					alert('사용자 권한부여 이력을 불러오는데 실패했습니다.');
				}
			});
			
			$(that.selector).dialog('open');
			
		},
		
		init : function(){
			var that = this;
			
			$(that.selector).dialog({
				autoOpen: false,
				height: 270,
				width: 900,
				buttons: {
					'수정' : function(){
						that.update();
					},
					'삭제' : function(){
						if(confirm('정말 삭제하시겠습니까?')){
							that.remove();
						}
					},
					'닫기' : function(){
						that.close();
					}
				},
				close : function(){
					that.close();
				}
			});
			
			$('form', that.selector).ajaxForm({
				dataType : 'json',
				success : function(rs){
					if(rs && rs.result > 0){
						alert('사용자 권한부여 이력이 수정 되었습니다.');
						userAcntMngRegstrObj.search();
						that.close();
					}else{
						alert('사용자 권한부여 이력 수정에 실패했습니다.');
					}
				}
			});
		},
		
		update : function(){
			var that = this;
			
			var url = CONTEXT_PATH + '/manage/userAcntMngRegstr/updateMngUserAuthorHist.do';
			
			$('form', that.selector).attr('action', url);
			$('form', that.selector).submit();
		},
		
		remove : function(){
			var that = this;
			
			var url = CONTEXT_PATH + '/manage/userAcntMngRegstr/deleteMngUserAuthorHist.do';
			var sn = $('input[name=sn]', that.selector).val();
			
			$.ajax({
				type : 'POST',
				url : url,
				data : {
					sn : sn
				},
				dataType : 'json',
				success : function(rs){
					if(rs && rs.result > 0){
						alert('사용자 권한부여 이력이 삭제 되었습니다.');
						userAcntMngRegstrObj.search();
						that.close();
					}
				},
				error : function(err){
					alert('사용자 권한부여 이력 삭제에 실패했습니다.');
				}
				
			});
			
		},
		
		close : function(){
			var that = this;
			
			$('#txt_userAcntMngRegstr_modify_deptNm', that.selector).html('');
			$('#txt_userAcntMngRegstr_modify_userNm', that.selector).html('');
			$('#txt_userAcntMngRegstr_modify_gradAlwncDt', that.selector).html('');
			$('#txt_userAcntMngRegstr_modify_gradUserNm', that.selector).html('');
			
			$('#txt_userAcntMngRegstr_modify_authorGroupNm', that.selector).val('');
			$('#txt_userAcntMngRegstr_modify_prcrCn', that.selector).val('');
			$('#txt_userAcntMngRegstr_modify_jobNm', that.selector).val('');
			
			that.isClosed = true;
			$(that.selector).dialog('close');
		}
};











