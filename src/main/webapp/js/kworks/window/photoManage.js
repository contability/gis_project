// 사진관리 객체
windowObj.photoManageObj = {
		
	TITLE : "사진관리",
	
	CLASS_NAME : "PhotoManage",
	
	selector : null,
	
	ftrCde : null,
	
	ftrIdn : null,
	
	initUi : function() {
		var that = this;
		var ftrCde = that.ftrCde;
		var ftrIdn = that.ftrIdn;
		
		$("#tbl_photo_manage_list", that.selector).datagrid({
			height : 200,
			columns : [[
			            {field:"ftrCde", title:"시설명칭", width:150, align:"center",
			            	formatter:function(value, row, index) {
			            		if(row.ftrCde) {
			            			var code = domnCodeObj.getCode("KWS-000", row.ftrCde);
			            			return code.codeNm;
			            		}
			            		else {
			            			return value;
			            		}
			            	}},
			            {field:"imageSj", title:"제목", width:190, align:"center"},
			            {field:"frstRgsde", title:"등록일자", width:120, align:"center"},
			          ]],
            fitColumns : true,
            singleSelect : true,
            selectOnCheck : false,
            checkOnSelect : false,
            onSelect : function(index, field) {
            	windowObj.photoManageObj.view.open(field.imageNo, ftrCde, ftrIdn, function() {
            		windowObj.registerObj.managePhoto();
            	});
            }
		});
	},
	
	bindEvents : function(ftrCde, ftrIdn) {
		var that = this;
		
		// 등록
		$(".a_add_list_photo", that.selector).on("click", function() {
			windowObj.photoManageObj.add.open(ftrCde, ftrIdn, function() {
				windowObj.registerObj.managePhoto();
			});
			return false;
		});
		
		// 닫기
		$(".a_close_list_photo", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	open : function(ftrCde, ftrIdn) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.ftrCde = ftrCde;
		that.ftrIdn = ftrIdn;
		
		var url = CONTEXT_PATH + "/window/photoManage/listPhotoManage/page.do";
		var windowOptions = {
			width : 490,
			height : 270,
			isClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents(ftrCde, ftrIdn);
			that.loadData(ftrCde, ftrIdn);
		});
		that.selector = "#" + id; 
	},
	
	loadData : function(ftrCde, ftrIdn) {
		var that = this;
		
		that.ftrCde = ftrCde;
		that.ftrIdn = ftrIdn;
		
		var url = CONTEXT_PATH + "/photoManage/" + ftrCde + "/" + ftrIdn + "/list.do";
		
		$.get(url).done(function(response) {
			$("#tbl_photo_manage_list", that.selector).datagrid("loadData", { total : response.rows.length, rows : response.rows });
		}).fail(function() {
			$.messager.alert("사진관리", "사진정보를 가져오는데 실패했습니다.");
		});
		
	},
	
	close : function() {
		var that = this;
		
		that.ftrCde = null;
		that.ftrIdn = null;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
	
};

// 사진 등록 객체
windowObj.photoManageObj.add = {
		
	TITLE : null,
	
	CLASS_NAME : "photoManageAdd",
	
	selector : null,

	ftrCde : null,
	
	ftrIdn : null,
	
	onClose : null,
	
	initUi : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			beforeSubmit : function(arr, form) {
				for(var i=0, len=arr.length; i < len; i++) {
					var obj = arr[i];
					var name = obj.name;
					
					// 필수 입력 체크
					if(name == "imageSj") {
						if(!obj.value) {
							$.messager.alert(that.TITLE, "제목을 입력하여 주십시오.", null, function() {
								form.find("input[name=" + name + "]").focus();								
							});
							return false;
						}
					}
					else if(name == "lcX") {
						if(!obj.value) {
							$.messager.alert(that.TITLE, "위치를 입력하여 주십시오.", null, function() {
								form.find("input[name=" + name + "]").focus();	
							});
							return false;
						}
					}
					else if(name == "lcY") {
						if(!obj.value) {
							$.messager.alert(that.TITLE, "위치를 입력하여 주십시오.", null, function() {
								form.find("input[name=" + name + "]").focus();
							});
							return false;
						}
					}
					else if(name == "orignlFileNm") {
						if(!obj.value) {
							$.messager.alert(that.TITLE, "사진을 등록하여 주십시오.");
							return false;
						}

						var file = obj.value;
						if(file instanceof String) {
							fileName = file;
						}
						else {
							fileName = file.name;
						}
						var extension = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
						var extensions = ["jpg", "jpeg", "bmp", "gif", "png", "mp4"];
						if(extensions.indexOf(extension) == -1) {
							$.messager.alert(that.TITLE, "다음 확장자만 등록 가능합니다. (" + extensions.join(", ") + ")");
							return false;	
						}
					}
				}
			},
			success : function(result) {
				if(that.onClose) {
					that.onClose();
				}
				that.close();
			}
		});
	},
	
	bindEvents : function() {
		var that = this;
		
		// 사진위치 좌표
		// selectObj.once
		$("#btn_add_photo_manage_lcXY", that.selector).on("click", function() {
			that.getLcXY();
			return false;
		});
		
		// 저장
		$(".a_save_add_photo_manage", that.selector).on("click", function() {
			that.add();
			return false;
		});
		
		// 닫기
		$(".a_close_add_photo_manage", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	getLcXY : function() {
		var that = this;
		selectObj.once("photoManageAdd", "Point", "drawend", function(evt) {
			var coords = evt.feature.getGeometry().getCoordinates();
//			var systemCoords = gisObj.toTransformSystem(coords);
			var systemCoords = gisObj.totransformCooridateSystem(coords);
			
			$("#txt_add_photo_manage_lc_x", that.selector).val(systemCoords[0]);
			$("#txt_add_photo_manage_lc_y", that.selector).val(systemCoords[1]);
		}, false);
	},
	
	open : function(ftrCde, ftrIdn, onClose, options) {
		var that = this;
		
		that.ftrCde = ftrCde;
		that.ftrIdn = ftrIdn;
		that.onClose = onClose;
		
		var url = CONTEXT_PATH + "/window/photoManage/addPhotoManage/page.do?nocache=" + Math.random();
		var windowOptions = {
			width : 415,
			height : 300,
			onClose : function(){
				that.close();
			}
		};
		
		if(options && options.title) {
			that.TITLE = options.title;
		}
		else {
			that.TITLE = "사진 등록";
		}
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.bindEvents();
			// promise step4
			that.loadData(ftrCde, ftrIdn).done(function() {
				that.initUi();
			});
		});
		that.selector = "#" + id;
	},
	
	loadData : function(ftrCde, ftrIdn) {
		var that = this;
		
		// promise step1
		var deferred = $.Deferred();
		
		that.ftrCde = ftrCde;
		that.ftrIdn = ftrIdn;
		
		// 관리번호
		$("#txt_add_photo_manage_ftr_idn").val(ftrIdn);
		
		// 지형지물부호
		if(ftrCde) {
			var code = domnCodeObj.getCode("KWS-000", ftrCde);
			$("#txt_add_photo_manage_ftr_cde_nam", that.selector).val(code.codeNm);
			$("#txt_add_photo_manage_ftr_cde", that.selector).val(code.codeId);
		}
		else {
			$("#ftrCde").val(ftrCde);;
		}

		// promise step2
		deferred.resolve();
				
		// promise step3
		return deferred.promise();
	},
	
	add : function() {
		var that = this;
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		that.ftrCde = null;
		that.ftrIdn = null;
		that.onClose = null;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
};

// 사진 상세정보 객체
windowObj.photoManageObj.view = {
		
	TITLE : "사진관리 상세정보",
	
	CLASS_NAME : "photoManageDetail",
	
	selector : null,
	
	ftrCde : null,
	
	ftrIdn : null,
	
	imageNo : null,
	
	onRemove : null,
	
	onClose : null,
	
	bindEvents : function() {
		var that = this;
		
		$(".a_modify_view_photo", that.selector).on("click", function() {
			var imageNo = $("#hid_view_photo_manage_image_no", that.selector).val();
			windowObj.photoManageObj.modify.open(imageNo, that.onRemove);
			that.close();
			return false;
		});
		
		$(".a_remove_view_photo", that.selector).on("click", function() {
			that.remove();
			return false;
		});
		
		$(".a_close_view_photo_manage", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	open : function(imageNo, ftrCde, ftrIdn, onRemove, onClose, options) {
		var that = this;

		that.imageNo = imageNo;
		that.ftrCde = ftrCde;
		that.ftrIdn = ftrIdn;
		that.onRemove = onRemove;
		that.onClose = onClose;
		
		if(that.selector) {
			that.close();
		}
		
		var url = CONTEXT_PATH + "/window/photoManage/viewPhotoManage/page.do";
		var windowOptions = {
			width : 360,
			height : 550,
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
			that.bindEvents();
			that.loadData(imageNo, ftrCde, ftrIdn);
		});
		that.selector = "#" + id;
	},

	loadData : function(imageNo, ftrCde, ftrIdn) {
		var that = this;
		var url = CONTEXT_PATH + "/photoManage/" + imageNo + "/select.do";
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;

				var imgDownloadUrl = CONTEXT_PATH + "/cmmn/file/" + data.imageFileNo + "/download.do";
				if(data.imageFile && data.imageFile.fileExtsn == "mp4") {
					$(".li_image", that.selector).hide();
					$(".li_video", that.selector).show();
					$("#video_view").attr("src", imgDownloadUrl);
				}
				else {
					$(".li_image", that.selector).show();
					$(".li_video", that.selector).hide();
					var thumbnailUrl = CONTEXT_PATH + "/cmmn/image/" + data.imageNo + "/thumbnail.do?nocache=" + Math.random();
					$("#img_view_photo_manage_thumbnail", that.selector).attr("src", thumbnailUrl);
					
					var imageViewUrl = CONTEXT_PATH + "/popup/image/view.do?imageFileNo="+data.imageFileNo;
					$("#img_view_photo_manage_thumbnail", that.selector).parent().attr("href", imageViewUrl);
					//$("#img_view_photo_manage_thumbnail", that.selector).parent().venobox();
				}
				
				$("#hid_view_photo_manage_image_no", that.selector).val(data.imageNo);
				$("#hid_view_photo_manage_image_file_no", that.selector).val(data.imageFileNo);
				$("#hid_view_photo_manage_thumb_file_no", that.selector).val(data.thumbFileNo);
				
				$("#spn_view_photo_manage_frst_rgsde", that.selector).text(data.frstRgsde);
				
				if(data.ftrCde) {
					var code = domnCodeObj.getCode("KWS-000", data.ftrCde);
					$("#spn_view_photo_manage_ftr_cde", that.selector).text(code.codeNm);
				}
				else {
					$("#spn_view_photo_manage_ftr_cde", that.selector).text(data.ftrCde);
				}
				
				$("#spn_view_photo_manage_image_sj", that.selector).text(data.imageSj);

				//var imageCn = data.imageCn.replace(/\n/g,'\n');
				$("#spn_view_photo_manage_image_cn", that.selector).text(data.imageCn);
				
				$("#spn_view_photo_manage_rmkExp", that.selector).text(data.rmkExp);
				
				$(".a_down_view_photo", that.selector).attr("href", imgDownloadUrl);
			}
			else {
				alert("사진정보를 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("사진정보를 불러오는데 실패했습니다.");
		});
	},
	
	remove : function() {
		var that = this;
		
		var imageNo = $("#hid_view_photo_manage_image_no", that.selector).val();
		var url = CONTEXT_PATH + "/photoManage/" + imageNo + "/remove.do";
		
		$.get(url).done(function() {
			if(that.onRemove) {
				that.onRemove();
			}
			that.close();
			alert("사진정보가 삭제되었습니다.");
		}).fail(function(result) {
			alert("사진정보 삭제에 실패했습니다.");
		});
	},
	
	close : function() {
		var that = this;
		if(that.onClose) {
			that.onClose();
		}
		$("#hid_view_photo_manage_image_no", that.selector).val("");
		that.ftrCde = null;
		that.ftrIdn = null;
		that.imageNo = null;
		that.field = null;
		that.onClose = null;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
};

// 사진 수정 객체
windowObj.photoManageObj.modify = {
		
	TITLE : null,
	
	CLASS_NAME : "PhotoManageModify",
	
	selector : null,
	
	imageNo : null,
	
	onClose : null,
	
	initUi : function() {
		var that = this;
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			beforeSubmit : function(arr, form) {
				for(var i=0, len=arr.length; i < len; i++) {
					var obj = arr[i];
					var name = obj.name;
					
					// 필수 입력 체크
					if(name == "imageSj") {
						if(!obj.value) {
							$.messager.alert(that.TITLE, "제목을 입력하여 주십시오.", null, function() {
								form.find("input[name=" + name + "]").focus();								
							});
							return false;
						}
					}
					else if(name == "lcX") {
						if(!obj.value) {
							$.messager.alert(that.TITLE, "위치를 입력하여 주십시오.", null, function() {
								form.find("input[name=" + name + "]").focus();	
							});
							return false;
						}
					}
					else if(name == "lcY") {
						if(!obj.value) {
							$.messager.alert(that.TITLE, "위치를 입력하여 주십시오.", null, function() {
								form.find("input[name=" + name + "]").focus();
							});
							return false;
						}
					}
					else if(name == "orignlFileNm") {
						if(obj.value) {
							var file = obj.value;
							if(file instanceof String) {
								fileName = file;
							}
							else {
								fileName = file.name;
							}
							var extension = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
							var extensions = ["jpg", "jpeg", "bmp", "gif", "png", "mp4"];
							if(extensions.indexOf(extension) == -1) {
								$.messager.alert(that.TITLE, "다음 확장자만 등록 가능합니다. (" + extensions.join(", ") + ")");
								return false;	
							}
						}
					}
				}
			},
			success : function(result) {
				if(that.onClose) {
					that.onClose();
				}
				that.close();
			}
		});
	},

	bindEvents : function() {
		var that = this;
		
		$("#btn_modify_photo_manage_lcXY", that.selector).on("click", function() {
			that.getLcXY();
			return false;
		});
		
		$(".a_save_modify_photo_manage", that.selector).on("click", function() {
			that.modify();
			return false;
		});
		
		$(".a_close_modify_photo_manage", that.selector).on("click", function() {
			that.close();
			return false;
		});
	},
	
	getLcXY : function() {
		var that = this;
		
		selectObj.once("photoManageAdd", "Point", "drawend", function(evt) {
			var coords = evt.feature.getGeometry().getCoordinates();
//			var systemCoords = gisObj.toTransformSystem(coords);
			var systemCoords = gisObj.totransformCooridateSystem(coords);
			$("#txt_modify_photo_manage_lc_x", that.selector).val(systemCoords[0]);
			$("#txt_modify_photo_manage_lc_y", that.selector).val(systemCoords[1]);
		}, false);
	},
	
	open : function(imageNo, onClose, options) {
		var that = this;

		that.imageNo = imageNo;
		that.onClose = onClose;
		
		var url = CONTEXT_PATH + "/window/photoManage/modifyPhotoManage/page.do";
		var windowOptions = {
			width : 415,
			height : 300,
			onClose : function(){
				that.close();
			}
		};
		
		if(options && options.title) {
			that.TITLE = options.title;
		}
		else {
			that.TITLE = "사진/영상 수정";
		}
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi();
			that.bindEvents();
			that.loadData(imageNo);
		});
		that.selector = "#" + id;
		
	},
	
	loadData : function(imageNo) {
		var that = this;
		
		that.imageNo = imageNo;
		
		var url = CONTEXT_PATH + "/photoManage/" + imageNo + "/select.do";
		
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;
				
				$("#txt_modify_photo_manage_image_no", that.selector).val(data.imageNo);
				$("#txt_modify_photo_manage_ftr_idn", that.selector).val(data.ftrIdn);
				
				$("#txt_modify_photo_manage_image_file_no", that.selector).val(data.imageFileNo);
				$("#txt_modify_photo_manage_thumb_file_no", that.selector).val(data.thumbFileNo);
				$("#txt_modify_photo_manage_outpt_file_no", that.selector).val(data.outptFileNo);
				
				if(data.ftrCde) {
					var code = domnCodeObj.getCode("KWS-000", data.ftrCde);
					$("#txt_modify_photo_manage_ftr_cde_nam", that.selector).val(code.codeNm);
					$("#txt_modify_photo_manage_ftr_cde", that.selector).val(code.codeId);
				}
				else {
					$("#txt_modify_photo_manage_ftr_cde_nam", that.selector).val(data.ftrCde);
				}
				
				$("#txt_modify_photo_manage_image_sj", that.selector).val(data.imageSj);
				$("#txt_modify_photo_manage_image_cn", that.selector).val(data.imageCn);
				$("#txt_modify_photo_manage_rmkExp", that.selector).val(data.rmkExp);
				$("#txt_modify_photo_manage_lc_x", that.selector).val(data.lcX);
				$("#txt_modify_photo_manage_lc_y", that.selector).val(data.lcY);
			}
			else {
				alert("사진정보를 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("사진정보를 불러오는데 실패했습니다.");
		});
		
	},
	
	modify : function() {
		var that = this;
		
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		$("#txt_modify_photo_manage_image_no", that.selector).val("");
		$("#txt_modify_photo_manage_ftr_idn", that.selector).val("");
		$("#txt_modify_photo_manage_image_file_no", that.selector).val("");
		$("#txt_modify_photo_manage_thumb_file_no", that.selector).val("");
		$("#txt_modify_photo_manage_outpt_file_no", that.selector).val("");
		that.imageNo = null;
		that.ftrIdn = null;
		that.ftrCde = null;
		that.field = null;
		that.onClose = null;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	}
	
};
