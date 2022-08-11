$(function() {
	restoreObj.init();
});

/**
 * 복원 객체
 * @type {Object}
 */
var restoreObj = {
	
	/**
	 * 선택자
	 */
	selector : "#div_restore_data",
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		that.bindEvents();
	},
	
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 연계 데이터 선택
		$(".tbl_cntc_data_list tr", that.selector).click(function() {
			var element = $(this);
			$(".tbl_cntc_data_list tr", that.selector).removeClass("on");
			$(element).addClass("on");
			
			var dataId = element.attr("data-id");
			that.loadBackupData(dataId);
		});
		
		// 데이터 복원
		$(".tbl_backup_data_list tbody", that.selector).on("click", "a.a_restore_data", function() {
			var element = $(this);
			var backupId = element.attr("data-backup-id");
			that.restore(backupId);
		});
		
	},
	
	/**
	 * 백업 데이터 로딩
	 * @params {String} dataId 데이터 아이디
	 */
	loadBackupData : function(dataId) {
		var that = this;
		
		var url = CONTEXT_PATH + "/manage/restore/listAllBackupData.do";
		var params = { dataId : dataId };
		$.get(url, params).done(function(result) {
			if(result) {
				var tagStr = '';
				for(var i=0, len=result.rows.length; i < len; i++) {
					var row = result.rows[i];
					tagStr += '<tr>';
					tagStr += '<td>'+row.backupDataId+'</td>';
					tagStr += '<td>'+row.backupDataCo+'</td>';
					tagStr += '<td><a class="a_restore_data ui-button ui-widget ui-corner-all" href="#" data-backup-id="'+row.backupId+'" >복원</a></td>';
					tagStr += '</tr>';
				}
				$(".tbl_backup_data_list tbody", that.selector).html(tagStr);
			}
			else {
				alert("백업 데이터 목록을 가져오는 데 실패 했습니다.");
			}
		}).fail(function() {
			alert("백업 데이터 목록을 가져오는 데 실패 했습니다.");
		});
	},
	
	/**
	 * 데이터 복원
	 * @param {Number} backupId 백업 아이디
	 */
	restore : function(backupId) {
		if(confirm("현재 데이터가 모두 삭제되며 백업된 데이터로 복원됩니다. 진행하시겠습니까?")) {
			var url = CONTEXT_PATH + "/manage/restore/restore.do";
			var params = { backupId : backupId };
			$.post(url, params).done(function(result) {
				if(result) {
					alert("데이터가 복원되었습니다.");
					location.reload();
				}
				else {
					alert("데이터를 복원하는 데 실패 했습니다.");
				}
			}).fail(function() {
				alert("데이터를 복원하는 데 실패 했습니다.");
			});
		}
	}

};