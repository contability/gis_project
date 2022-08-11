// 영상 관리 객체
windowObj.videoManageObj = {
		
	TITLE : "영상 관리",
	
	CLASS_NAME : "videoManage",
	
	selector : null,
	
	ftrIdn : null,
	
	bindEvents : function() {
		var that = this;
		
		$(".a_add_list_video").on("click", function() {
			windowObj.videoManageObj.modify.open(that.ftrIdn);
			that.close();
			return false;
		});
		
		$(".a_close_list_video", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	open : function(ftrIdn) {
		var that = this;
		
		that.ftrIdn = ftrIdn;

		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/videoManage/listVideoManage/page.do";
		var windowOptions = {
			width : 415,
			height : 680,
			onClose : function(){
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.bindEvents();
			that.loadData(ftrIdn);
			
		});
		
		that.selector = "#" + id;
	},
	
	loadData : function(ftrIdn) {
		var that = this;
		
		var url = CONTEXT_PATH + "/videoManage/" + ftrIdn + "/select.do";
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;
				
				var videoUploadUrl = CONTEXT_PATH + "/cmmn/file/" + data.upNo + "/download.do";
				var videoDownloadUrl = CONTEXT_PATH + "/cmmn/file/" + data.downNo + "/download.do";
				
				$(".picSlider", that.selector).show();
				$(".li_video", that.selector).show();
				$("#video_up_view").attr("src", videoUploadUrl);
				$("#video_down_view").attr("src", videoDownloadUrl);
				
				$("#hid_list_video_manage_up_no", that.selector).val(data.upNo);
				$("#hid_list_video_manage_down_no", that.selector).val(data.downNo);
				$("#hid_list_video_manage_ftr_idn", that.selector).val(data.ftrIdn);
				$("#spn_list_video_manage_rot_nam", that.selector).text(data.rotNam);
				$("#spn_list_video_manage_rot_idn", that.selector).text(data.rotIdn);
				$("#spn_list_video_manage_sec_idn", that.selector).text(data.secIdn);
				$("#spn_list_video_manage_up_file", that.selector).text(data.upFile);
				$("#spn_list_video_manage_down_file", that.selector).text(data.downFile);
				
			} else {
				alert("영상 정보를 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("영상 정보를 불러오는데 실패했습니다.");
		});
	},
	
	close : function() {
		var that = this;
		if(that.onClose) {
			that.onClose();
		}
		$("#hid_list_video_manage_ftr_idn", that.selector).val("");
		that.ftrIdn = null;
		that.onClose = null;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
};

// 영상 관리 수정 객체
windowObj.videoManageObj.modify = {
		
	TITLE : "영상관리",
	
	CLASS_NAME : "videoManageModify",
	
	selector : null,
	
	ftrIdn : null,
	
	isCreated : false,
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					windowObj.videoManageObj.open([that.ftrIdn]);
					that.close();
					alert("영상이 수정되었습니다.");
				} else {
					alert("영상 수정에 실패하였습니다.");
				}
			}
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		$(".a_save_modify_video_manage", that.selector).on("click", function() {
			that.modify();
			return false;
		});
		
		$(".a_close_modify_video_manage", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	open : function(ftrIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.ftrIdn = ftrIdn;
		
		var url = CONTEXT_PATH + "/videoManage/" + that.ftrIdn + "/modifyVideoManagePage.do";
		var windowOptions = {
			width : 500,
			height : 240,
			onClose : function(){
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			if(!that.isCreated) {
				that.init();
				that.bindEvents();
				that.isCreated = true;
			}
		});
		that.selector = "#" + id;
	},
		
	modify : function() {
		var that = this;
		
		that.ftrIdn = $("#ftrIdn", that.selector).val();
		
		var url = CONTEXT_PATH + "/videoManage/" + that.ftrIdn +  "/modifyVideoManage.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.ftrIdn = null;
		that.selector = null;
		that.isCreated = false;
	}	
};