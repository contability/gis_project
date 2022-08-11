// 단위도면 관리 객체
windowObj.localPlanObj = {
	
	TITLE : "단위도면 관리",
	
	CLASS_NAME : "LocalPlan",
	
	selector : null,
	
	ftrIdn : null,
	
	initUi : function() {
		var that = this;
		var ftrIdn = that.ftrIdn;
		
		$("#tbl_local_plan_list", that.selector).datagrid({
			height : 200,
			columns : [[
			            {field:"secDis", title:"구간이정", width:190, align:"center"},
			            {field:"lclCde", title:"도면", width:150, align:"center",
			            	formatter:function(value, row, index) {
			            		if(row.lclCde) {
			            			var code = domnCodeObj.getCode("KWS-0418", row.lclCde);
			            			return code.codeNm;
			            		}
			            		else {
			            			return value;
			            		}
			            	}},
			            {field:"frstRgsde", title:"작성일자", width:150, align:"center"},
			          ]],
            fitColumns : true,
            singleSelect : true,
            selectOnCheck : false,
            checkOnSelect : false,
            onSelect : function(index, field) {
            	windowObj.localPlanObj.view.open(field.fileNo, ftrIdn, function() {
            		windowObj.registerObj.localPlan();
            	});
            }
		});
	},
	
	bindEvents : function(ftrIdn) {
		var that = this;
		
		// 등록
		$(".a_add_list_local", that.selector).on("click", function() {
			windowObj.localPlanObj.add.open(that.ftrIdn);
			that.close();
			return false;
		});
		
		// 닫기
		$(".a_close_list_local", that.selector).on("click", function() {
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
		
		var url = CONTEXT_PATH + "/window/localPlan/listLocalPlan/page.do";
		var windowOptions = {
			width : 630,
			height : 370,
			isClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents(ftrIdn);
			that.loadData(ftrIdn);
		});
		that.selector = "#" + id; 
	},
	
	loadData : function(ftrIdn) {
		var that = this;
		
		that.ftrIdn = ftrIdn;
		var url = CONTEXT_PATH + "/localPlan/" + ftrIdn + "/list.do";
		$.get(url).done(function(response) {
			
			var data = response.data;
			
			$("#hid_list_local_plan_ftr_idn", that.selector).val(data.ftrIdn);
			$("#txt_list_local_plan_rot_nam", that.selector).val(data.rotNam);
			$("#txt_list_local_plan_rot_idn", that.selector).val(data.rotIdn);
			$("#txt_list_local_plan_sec_idn", that.selector).val(data.secIdn);
			
			$("#tbl_local_plan_list", that.selector).show();
			$("#tbl_local_plan_list", that.selector).datagrid("loadData", { total : response.rows.length, rows : response.rows });
			
		}).fail(function() {
			$.messager.alert("단위도면 관리", "단위도면 정보를 가져오는데 실패했습니다.");
		});
	},
	
	close : function() {
		var that = this;

		that.ftrIdn = null;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
};

// 단위도면 상세정보 객체
windowObj.localPlanObj.view = {
	
	TITLE : "단위도면 상세정보",
	
	CLASS_NAME : "localPlanDetail",
	
	selector : null,
	
	ftrIdn : null,
	
	fileNo : null,
	
	onRemove : null,
	
	onClose : null,
	
	bindEvents : function() {
		var that = this;
		
		$(".a_modify_view_local", that.selector).on("click", function() {
			var fileNo = $("#hid_view_local_plan_file_no", that.selector).val();
			windowObj.localPlanObj.modify.open(fileNo, that.onRemove);
			that.close();
			return false;
		});
		
		$(".a_remove_view_local", that.selector).on("click", function() {
			that.remove();
			return false;
		});
		
		$(".a_close_view_local_plan", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},	
		
	open : function(fileNo, ftrIdn, onRemove, onClose, options) {
		var that = this;
		
		that.fileNo = fileNo;
		that.ftrIdn = ftrIdn;
		that.onRemove = onRemove;
		that.onClose = onClose;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/localPlan/viewLocalPlan/page.do";
		var windowOptions = {
			width : 630,
			height : 240,
			onClose : function(){
				that.close();
			}
		};
		
		var title = null;
		if(options && options.title) {
			title = options.title;
		}
		else {
			title = that.TITLE;
		}
		
		var id = windowObj.createWindow(that.CLASS_NAME, title, url, null, windowOptions, function() {
			windowObj.localPlanObj.close();	
			that.bindEvents();
			that.loadData(fileNo, ftrIdn);
		});
		that.selector = "#" + id;
	},
	
	loadData : function(fileNo, ftrIdn) {
		var that = this;
		var url = CONTEXT_PATH + "/localPlan/" + fileNo + "/select.do";
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;

				var fileDownloadUrl = CONTEXT_PATH + "/cmmn/file/" + data.fileNo + "/download.do";
				
				$("#hid_view_local_plan_file_no", that.selector).val(data.fileNo);
				$("#spn_view_local_plan_rot_nam", that.selector).text(data.rotNam);
				$("#spn_view_local_plan_rot_idn", that.selector).text(data.rotIdn);
				$("#spn_view_local_plan_sec_idn", that.selector).text(data.secIdn);
				$("#spn_view_local_plan_sec_dis", that.selector).text(data.secDis);
				
				if(data.lclCde) {
					var code = domnCodeObj.getCode("KWS-0418", data.lclCde);
					$("#spn_view_local_plan_lcl_cde", that.selector).text(code.codeNm);
				} else {
					$("#spn_view_local_plan_lcl_cde", that.selector).text(data.lclCde);
				}
				
				$("#spn_view_local_plan_frst_rgsde", that.selector).text(data.frstRgsde);
				$("#spn_view_local_plan_doc_file", that.selector).text(data.docFile);
				
				var docFile = data.docFile;
				if(docFile != "") {
					$(".a_down_view_local", that.selector).show();
					$(".a_down_view_local", that.selector).attr("href", fileDownloadUrl);
				} else {
					$(".a_down_view_local", that.selector).hide();
				}
				
			} else {
				alert("단위도면 정보를 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("단위도면 정보를 불러오는데 실패했습니다.");
		});
	},
	
	remove : function() {
		var that = this;
		
		var fileNo = $("#hid_view_local_plan_file_no", that.selector).val();
		var url = CONTEXT_PATH + "/localPlan/" + fileNo + "/remove.do";
		
		$.get(url).done(function() {
			if(that.onRemove) {
				that.onRemove();
			}
			that.close();
			alert("단위도면 정보가 삭제되었습니다.");
		}).fail(function(result) {
			alert("단위도면 정보 삭제에 실패했습니다.");
		});
	},
	
	close : function() {
		var that = this;
		if(that.onClose) {
			that.onClose();
		}
		$("#hid_view_local_plan_file_no", that.selector).val("");
		that.ftrIdn = null;
		that.fileNo = null;
		that.field = null;
		that.onClose = null;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
};

// 단위도면 파일 수정 객체
windowObj.localPlanObj.modify = {
		
	TITLE : "단위도면 관리",
	
	CLASS_NAME : "LocalPlanModify",
	
	selector : null,
	
	ftrIdn : null,
	
	isCreated : null,
	
	init : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			beforeSubmit : function(arr, form) {
				for(var i=0, len=arr.length; i < len; i++) {
					var obj = arr[i];
					var name = obj.name;
					
					// 필수 입력 체크
					if(name == "secDis") {
						if(!obj.value) {
							$.messager.alert(that.TITLE, "구간이정을 입력하여 주십시오.", null, function() {
								form.find("input[name=" + name + "]").focus();								
							});
							return false;
						}
					}
					else if(name == "lclCde") {
						if(!obj.value) {
							$.messager.alert(that.TITLE, "도면을 입력하여 주십시오.", null, function() {
								form.find("input[name=" + name + "]").focus();	
							});
							return false;
						}
					}
					else if(name == "localPlanFile") {
						if(obj.value) {
							var file = obj.value;
							if(file instanceof String) {
								fileName = file;
							}
							else {
								fileName = file.name;
							}
							var extension = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
							var extensions = ["dwg"];
							if(extensions.indexOf(extension) == -1) {
								$.messager.alert(that.TITLE, "다음 확장자만 등록 가능합니다. (" + extensions.join(", ") + ")");
								return false;	
							}
						} else if(!obj.value) {
							$.messager.alert(that.TITLE, "단위도면 파일을 입력하여 주십시오.", null, function() {
								form.find("input[name=" + name + "]").focus();	
							});
							return false;
						}
					}
				}
			},
			success : function(result) {
				if(result) {
					that.close();
					$.messager.alert(that.TITLE, "단위도면이 수정되었습니다.");
				} else {
					$.messager.alert(that.TITLE, "단위도면이 수정에 실패하였습니다.");
				}
			}
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		$(".a_save_modify_local_plan", that.selector).on("click", function() {
			that.modify();
			return false;
		});
		
		$(".a_close_modify_local_plan", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	open : function(fileNo) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.fileNo = fileNo;
		
		var url = CONTEXT_PATH + "/localPlan/" + that.fileNo + "/modifyLocalPlanPage.do";
		var windowOptions = {
			width : 600,
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
		
		that.fileNo = $("#fileNo", that.selector).val();
		
		var url = CONTEXT_PATH + "/localPlan/" + that.fileNo + "/modifyLocalPlan.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		windowObj.removeWindow(that.selector);
		that.fileNo = null;
		that.selector = null;
		that.isCreated = false;
	}
};

// 단위도면 등록
windowObj.localPlanObj.add = {
		
	TITLE : "단위도면 등록",
	
	CLASS_NAME : "LocalPlanAdd",
	
	ftrIdn : null,
	
	selector : null,
	
	isCreated : false,
	
	initUi : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			beforeSubmit : function(arr, form) {
				for(var i=0, len=arr.length; i < len; i++) {
					var obj = arr[i];
					var name = obj.name;
					if(name == "secDis") {
						if(!obj.value) {
							$.messager.alert(that.TITLE, "구간이정을 입력하여 주십시오.", null, function() {
								form.find("input[name=" + name + "]").focus();								
							});
							return false;
						}
					} else if(name == "lclCde") {
						if(!obj.value) {
							$.messager.alert(that.TITLE, "도면을 입력하여 주십시오.", null, function() {
								form.find("input[name=" + name + "]").focus();	
							});
							return false;
						}
					} else if(name == "localPlanFile") {
						if(obj.value) {
							var file = obj.value;
							if(file instanceof String) {
								fileName = file;
							}
							else {
								fileName = file.name;
							}
							var extension = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
							var extensions = ["dwg"];
							if(extensions.indexOf(extension) == -1) {
								$.messager.alert(that.TITLE, "다음 확장자만 등록 가능합니다. (" + extensions.join(", ") + ")");
								return false;	
							}
						}
					}
				}
			},
			success : function(result) {
				if(result) {
					that.close();
					$.messager.alert(that.TITLE, "단위도면이 등록되었습니다.");
				}
				else {
					$.messager.alert(that.TITLE, "단위도면 등록에 실패하였습니다.");
				}
			}
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 저장버튼
		$(".a_save_add_local_plan", that.selector).on("click", function() {
			that.add();
			return false;
		});
		
		// 닫기버튼
		$(".a_close_add_local_plan", that.selector).on("click", function() {
			that.close();
		});
	},
	
	open : function(ftrIdn){
		var that = this;
		
		if(that.selector) {
			that.close();
		}
			
		that.ftrIdn = ftrIdn;
				
		var url = CONTEXT_PATH + "/localPlan/" + that.ftrIdn + "/addLocalPlanPage.do";
		var windowOptions = {
			width : 680,
			height : 240,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
			that.loadData(ftrIdn);
			
			});
		that.selector = "#" + id;
	},
	
	loadData : function(ftrIdn) {
		var that = this;
		
		that.ftrIdn = ftrIdn;
		var url = CONTEXT_PATH + "/localPlan/" + ftrIdn + "/list.do";
		$.get(url).done(function(response) {
			
			var data = response.data;
			
			$("#txt_add_local_plan_file_no", that.selector).val(data.fileNo);
			$("#txt_add_local_plan_rot_nam", that.selector).val(data.ftrIdn);
			$("#txt_add_local_plan_rot_nam", that.selector).val(data.rotNam);
			$("#txt_add_local_plan_rot_idn", that.selector).val(data.rotIdn);
			$("#txt_add_local_plan_sec_idn", that.selector).val(data.secIdn);
			
		}).fail(function() {
			$.messager.alert("단위도면 관리", "단위도면 정보를 가져오는데 실패했습니다.");
		});
	},
		
	add : function() {
		var that = this;
		
		var url = CONTEXT_PATH + "/localPlan/" + that.ftrIdn + "/addLocalPlan.do";
		$("form", that.selector).attr("action", url);
        $("form", that.selector).submit();
	},
	
	close : function(){
		var that = this;

		that.ftrIdn = null;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
};