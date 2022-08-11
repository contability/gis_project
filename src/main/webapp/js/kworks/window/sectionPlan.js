// 구간도면 관리 객체
windowObj.sectionPlanObj = {
		
	TITLE : "구간도면 관리",
	
	CLASS_NAME : "sectionPlan",
	
	selector : null,
	
	ftrIdn : null,

	bindEvents : function() {
		var that = this;
		
		$(".a_add_list_section", that.selector).on("click", function() {
			windowObj.sectionPlanObj.modify.open(that.ftrIdn);
			that.close();
			return false;
		});
		
		$(".a_close_list_section", that.selector).on("click", function() {
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
		
		var url = CONTEXT_PATH + "/window/sectionPlan/listSectionPlan/page.do";
		var windowOptions = {
			width : 600,
			height : 180,
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
		
		var url = CONTEXT_PATH + "/sectionPlan/" + ftrIdn + "/select.do";
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;
				
				var fileDownloadUrl = CONTEXT_PATH + "/cmmn/file/" + data.fileNo + "/download.do";
			
				$("#hid_list_section_plan_file_no", that.selector).val(data.fileNo);
				$("#hid_list_section_plan_ftr_idn", that.selector).val(data.ftrIdn);
				$("#spn_list_section_plan_rot_nam", that.selector).text(data.rotNam);
				$("#spn_list_section_plan_rot_idn", that.selector).text(data.rotIdn);
				$("#spn_list_section_plan_sec_idn", that.selector).text(data.secIdn);
				$("#spn_list_section_plan_doc_file", that.selector).text(data.docFile);
				
				var docFile = data.docFile;
				if(docFile != "") {
					$(".a_down_list_section", that.selector).show();
					$(".a_down_list_section", that.selector).attr("href", fileDownloadUrl);
				} else {
					$(".a_down_list_section", that.selector).hide();
				}
	
			} else {
				alert("구간도면 정보를 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("구간도면 정보를 불러오는데 실패했습니다.");
		});
	},
	
	close : function() {
		var that = this;
		if(that.onClose) {
			that.onClose();
		}
		$("#hid_list_section_plan_ftr_idn", that.selector).val("");
		that.ftrIdn = null;
		that.onClose = null;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
};

// 구간도면 파일 수정 객체
windowObj.sectionPlanObj.modify = {
		
	TITLE : "구간도면 관리",
	
	CLASS_NAME : "SectionPlanModify",
	
	selector : null,
	
	ftrIdn : null,
	
	isCreated : false,
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result) {
					windowObj.sectionPlanObj.open([that.ftrIdn]);
					that.close();
					alert("구간도면이 수정되었습니다.");
				} else {
					alert("구간도면 수정에 실패하였습니다.");
				}
			}
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		$(".a_save_modify_section_plan", that.selector).on("click", function() {
			that.modify();
			return false;
		});
		
		$(".a_close_modify_section_plan", that.selector).on("click", function() {
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
		
		var url = CONTEXT_PATH + "/sectionPlan/" + that.ftrIdn + "/modifySectionPlanPage.do";
		var windowOptions = {
			width : 600,
			height : 180,
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
		
		var url = CONTEXT_PATH + "/sectionPlan/" + that.ftrIdn +  "/modifySectionPlan.do";
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