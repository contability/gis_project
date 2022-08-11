menuObj.lgstrStatsObj = {
	/**
	 * 페이지 명
	 * 
	 * @type {String}
	 */
	PAGE : "lgstrStats",

	CLASS_NAME : "LgstrStatsMenu",

	/**
	 * 선택자
	 * 
	 * @type {String}
	 */
	selector : "#div_menu_panel_lgstrStats",

	/**
	 * 제목
	 * 
	 * @type {String}
	 */
	TITLE : "지적통계",

	pageIndex : 1,

	/**
	 * 초기화
	 */
	init : function() {
		var that = this;

		that.bindEvents();
		that.open();
	},

	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;

		$("#a_new_lgstrStats", that.selector).click(function() {
			that.add.open();
			return false;
		});

		$(that.selector).on("click", ".a_content", function() {
			var element = $(this);
			var id = element.attr("data-id");
			that.selectOne.open(id);
			return false;
		});

		// 페이징 이동
		$(that.selector).on("click", ".div_lgstrStats_pagination a",
				function() {
					var element = $(this);
					var pageIndex = element.attr("data-page-index");
					that.pageIndex = pageIndex;
					that.list();
				});
	},

	list : function() {
		var that = this;
		
		$.ajax({
			type : 'GET',
			url : CONTEXT_PATH + '/lgstrStats/listLgstrStats.do',
			data : {
				pageIndex : that.pageIndex
			},
			dataType : 'json',
			success : function(result) {
				that.createContent(result.rows);
				that.createPagination(result.paginationInfo);
			},
			error : function() {

			}
		});
	},

	createContent : function(data) {

		var tagStr = '';

		for (var i = 0, len = data.length; i < len; i++) {
			var row = data[i];
			tagStr += '<li>';
			tagStr += '<a href="#" class="a_content" data-id="'
					+ row.lgstrStatsNo + '" >' + row.lgstrStatsSj + '</a>';
			tagStr += '</li>';
		}

		$(".ul_lgstrStats_content").html(tagStr);
	},

	createPagination : function(pagination) {
		var tagStr = '';

		if (pagination.currentPageNo > pagination.firstPageNo) {
			tagStr += '<a href="#" class="a_img" data-page-index="'
					+ pagination.firstPageNo
					+ '"><img src="images/kworks/map/common/boardLst_pp.png" /></a>';
			tagStr += '<a href="#" class="a_img" data-page-index="'
					+ (pagination.currentPageNo - 1)
					+ '"><img src="images/kworks/map/common/boardLst_p.png" /></a>';
		}

		for (var i = pagination.firstPageNoOnPageList; i <= pagination.lastPageNoOnPageList; i++) {
			tagStr += '<a href="#" class="a_text';
			tagStr += i == pagination.currentPageNo ? " a_select " : "";
			tagStr += '" data-page-index="' + i + '">';
			tagStr += i;
			tagStr += '</a>';
		}

		if (pagination.currentPageNo < pagination.lastPageNo) {
			tagStr += '<a href="#" class="a_img" data-page-index="'
					+ (pagination.currentPageNo + 1)
					+ '"><img src="images/kworks/map/common/boardLst_n.png" /></a>';
			tagStr += '<a href="#" class="a_img" data-page-index="'
					+ pagination.lastPageNo
					+ '"><img src="images/kworks/map/common/boardLst_nn.png" /></a>';
		}

		$(".div_lgstrStats_pagination").html(tagStr);
	},

	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		that.list();
		$(that.selector).show();
	},

	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		$(that.selector).hide();
	}

};

// 지적통계 단건 조회
menuObj.lgstrStatsObj.selectOne = {

	TITLE : "지적통계 조회",

	CLASS_NAME : "lgstrStatsSelect",

	selector : "#div_lgstrStats_select",

	ognlSelector : null,

	isCreated : false,

	dataId : 0,

	loadData : function(lgstrStatsNo) {
		var that = this;

		var url = CONTEXT_PATH + "/lgstrStats/" + lgstrStatsNo + "/select.do";

		$.get(url).done(
				function(result) {
					if (result && result.data) {
						var data = result.data;

						$(".lgstrStatsSj", that.selector).text(
								data.lgstrStatsSj);
						$(".lgstrStatsCn", that.selector).html(
								data.lgstrStatsCn);

						if (data.lgstrStatsFile) {
							var tagStr = '';

							var file = data.lgstrStatsFile.kwsFile;
							tagStr += '<li>';
							tagStr += '<a href="' + CONTEXT_PATH
									+ '/cmmn/file/' + file.fileNo
									+ '/download.do">';
							tagStr += '<span>' + file.orignlFileNm + '</span>';
							tagStr += '</a>';
							tagStr += '</li>';

							$(".fileList", that.selector).html(tagStr);
							$(".div_file_list", that.selector).show();
							$(".lgstrStatsSelect").css("height","100%");
						}
					} else {
						$.messager.alert('지적 통계',"지적통계 정보를 불러오는데 실패했습니다.");
					}
				}).fail(function(result) {
			$.messager.alert('지적 통계',"지적통계 정보를 불러오는데 실패했습니다.");
		});
		
		
	},

	bindEvents : function() {
		var that = this;

		$("#btn_modify_lgstrStats").click(function() {
			menuObj.lgstrStatsObj.modify.open(that.dataId);
			that.close();
			return false;
		});

		$("#btn_delete_lgstrStats").click(function() {
			$.messager.confirm('지적 통계','정말 삭제 하시겠습니까?',function(result){
				if(result){
					that.remove(that.dataId);
					that.close();
					return false;
				}
			});
		});

		$("#btn_close_lgstrStats").click(function() {
			that.close();
			return false;
		});
	},

	remove : function(lgstrStatsNo) {

		$.ajax({
			type : 'POST',
			url : CONTEXT_PATH + '/lgstrStats/' + lgstrStatsNo + '/remove.do',
			dataType : 'json',
			success : function(result) {
				$.messager.alert('지적 통계',"지적 통계가 삭제되었습니다.");
				menuObj.lgstrStatsObj.init();
			},
			error : function() {
				$.messager.alert('지적 통계',"지적 통계 삭제에 실패하였습니다.");
			}
		});
	},

	open : function(lgstrStatsNo) {
		var that = this;

		that.dataId = lgstrStatsNo;

		if (that.selector) {
			that.close();
		}

		var url = CONTEXT_PATH + "/lgstrStats/" + lgstrStatsNo
				+ "/selectPage.do";
		var windowOptions = {
			width : 700,
			height : 200,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};

		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null,
				windowOptions, function() {
					if (!that.isCreated) {
						that.loadData(lgstrStatsNo);
						that.bindEvents();
						that.isCreated = true;
					}
				});
		
		
		that.ognlSelector = "#" + id;
	},

	close : function() {
		var that = this;
		windowObj.removeWindow(that.ognlSelector);
		that.ognlSelector = null;
		that.isCreated = false;
	}

};

menuObj.lgstrStatsObj.modify = {

	TITLE : "지적통계 편집",

	CLASS_NAME : "lgstrStatsModify",

	selector : "#div_lgstrStats_modify",

	ognlSelector : null,

	isCreated : false,

	dataId : null,
	
	lgstrStatsCn : null,

	init : function() {
		var that = this;

		$("form", that.selector)
				.ajaxForm(
						{
							dataType : "json",
							beforeSubmit : function(arr, form) {
								for (var i = 0, len = arr.length; i < len; i++) {
									var obj = arr[i];
									var name = obj.name;

									if (name == "lgstrStatsSj") {
										if (!obj.value) {
											$.messager.alert('지적 통계',"제목을 입력하여 주십시오.");
											form.find(
													"input[name=" + name + "]")
													.focus();
											return false;
										}
									} /*else if (name == "lgstrStatsCn") {
										// textarea 의 내용이 아닌 ckeditor 의 내용 입력
										obj.value = CKEDITOR.instances.txa_lgstrStats_modify_cn
												.getData();
									}*/

								}
							},
							success : function(result) {
								if (result) {
									$.messager.alert('지적 통계',"지적 통계가 편집되었습니다.");
									menuObj.lgstrStatsObj.init();
									that.close();
								} else {
									$.messager.alert('지적 통계',"지적 통계 편집에 실패하였습니다.");
								}
							},
							error : function() {
								$.messager.alert('지적 통계',"error");
							}
						});

		// 편집기 적용
		/*CKEDITOR.replace('txa_lgstrStats_modify_cn', {
			customConfig : CONTEXT_PATH + '/js/yy/job/lgstrStats/ckeditorconfig.js'
		});*/
	},

	loadData : function(lgstrStatsNo) {
		var that = this;

		var url = CONTEXT_PATH + "/lgstrStats/" + lgstrStatsNo + "/select.do";

		$.get(url).done(
				function(result) {
					if (result && result.data) {
						var data = result.data;

						$("input[name=lgstrStatsSj]", that.selector).val(
								data.lgstrStatsSj);
						
						/*CKEDITOR.instances.txa_lgstrStats_modify_cn.setData(data.lgstrStatsCn,function(){
							CKEDITOR.instances.txa_lgstrStats_modify_cn.setData(data.lgstrStatsCn);
						});*/
						$("textarea[name=lgstrStatsCn]", that.selector).val(data.lgstrStatsCn);

						if (data.lgstrStatsFile) {
							var file = data.lgstrStatsFile.kwsFile;
							$(".fileList input[name=fileNo]", that.selector)
									.val(file.fileNo);
							$(".fileList .orignlFileNm", that.selector).text(
									
									file.orignlFileNm);
							$(".fileList", that.selector).show();
						}
					} else {
						$.messager.alert('지적 통계',"지적통계 정보를 불러오는데 실패했습니다.");
					}
				}).fail(function(result) {
			$.messager.alert('지적 통계',"지적통계 정보를 불러오는데 실패했습니다.");
		});
	},

	open : function(lgstrStatsNo) {
		var that = this;
		
		if (that.selector) {
			that.close();
		}
		
		that.dataId = lgstrStatsNo;

		var url = CONTEXT_PATH + "/lgstrStats/" + lgstrStatsNo + "/modifyPage.do";
		var windowOptions = {
			width : 700,
			height : 500,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};

		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null,
				windowOptions, function() {
					if (!that.isCreated) {
						that.init();
						that.loadData(lgstrStatsNo);
						that.bindEvents();
						that.isCreated = true;
					}
				});

		that.ognlSelector = "#" + id;
	},

	modify : function() {
		var that = this;
		
		var url = CONTEXT_PATH + "/lgstrStats/" + that.dataId + "/modify.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},

	bindEvents : function() {
		var that = this;

		$(".fileList li a.a_remove_file", that.selector).click(
				function() {
					var fileNo = $(".fileList input[name=fileNo]",
							that.selector).val();
					if (fileNo != "") {
						$(".fileList input[name=deleteFileNo]", that.selector)
								.val(fileNo);
						$(".fileList", that.selector).hide();
					}
					return false;
				});

		// 지적통계 파일 추가 시 기존 파일 삭제
		$(".span_file", that.selector).on("change","input[name=lgstrStatsFile]",function() {
					$(".fileList li a.a_remove_file", that.selector).trigger(
							"click");
				});

		$(".btn_modify_lgstrStats").click(function() {
			that.modify();
			return false;
		});

		$(".btn_close_lgstrStats").click(function() {
			that.close();
			return false;
		});
	},

	clear : function() {
		var that = this;
		

		that.dataId = 0;
		$("input[name=lgstrStatsSj]", that.selector).val("");
		$(".fileList input[name=fileNo]", that.selector).val("");
		$(".fileList input[name=deleteFileNo]", that.selector).val("");
		$(".fileList .orignlFileNm", that.selector).text("");
		$(".span_file", that.selector).html(
				'<input type="file" name="lgstrStatsFile" value="" />');
		$(".fileList", that.selector).hide();
		
		/*CKEDITOR.instances.txa_lgstrStats_modify_cn.setData("",function(){
            CKEDITOR.instances.txa_lgstrStats_modify_cn.setData("");
        });*/
		$("textarea[name=lgstrStatsCn]", that.selector).text("");
	},

	close : function() {
		var that = this;
		that.clear();
		windowObj.removeWindow(that.ognlSelector);
		that.ognlSelector = null;
		that.isCreated = false;
	}

};

// 지적통계 등록
menuObj.lgstrStatsObj.add = {

	TITLE : "지적통계 등록",

	CLASS_NAME : "lgstrStatsAdd",

	selector : "#div_lgstrStats_add",

	ognlSelector : null,

	isCreated : false,

	init : function() {
		var that = this;

		// $("#addLgstrStatsForm").ajaxForm({
		$("form", that.selector)
				.ajaxForm(
						{
							dataType : "json",
							beforeSubmit : function(arr, form) {
								for (var i = 0, len = arr.length; i < len; i++) {
									var obj = arr[i];
									var name = obj.name;

									if (name == "lgstrStatsSj") {
										if (!obj.value) {
											$.messager.alert('지적 통계',"제목을 입력하여 주십시오.");
											form.find(
													"input[name=" + name + "]")
													.focus();
											return false;
										}
									} /*else if (name == "lgstrStatsCn") {
										// textarea 의 내용이 아닌 ckeditor 의 내용 입력
										obj.value = CKEDITOR.instances.txa_lgstrStats_add_cn
												.getData();
									}*/
									else if (name == "lgstrStatsFile"){
										if(!obj.value){
											$.messager.alert('지적 통계',"파일을 등록해 주십시오.");
											return false;
										}
									}

								}
							},
							success : function(result) {
								if (result) {
									$.messager.alert('지적 통계',"지적 통계가 등록되었습니다.");
									menuObj.lgstrStatsObj.init();
									that.close();
								} else {
									$.messager.alert('지적 통계',"지적 통계 등록에 실패하였습니다.");
								}
							},
							error : function() {
								$.messager.alert('지적 통계',"error");
							}
						});

		// 편집기 적용
		/*CKEDITOR.replace('txa_lgstrStats_add_cn', {
			customConfig : CONTEXT_PATH + '/js/yy/job/lgstrStats/ckeditorconfig.js'
		});*/
	},

	bindEvents : function() {
		var that = this;

		// 저장
		$(".btn_save_addChangeDetection", that.selector).on("click",
				function() {
					that.add();
					return false;
				});

		// 닫기
		$(".btn_close_addChangeDetection", that.selector).on("click",
				function() {
					that.close();
					return false;
				});
	},

	open : function() {
		var that = this;

		if (that.selector) {
			that.close();
		}

		var url = CONTEXT_PATH + "/lgstrStats/addLgstrStatsPage.do";
		var windowOptions = {
			width : 700,
			height : 430,
			top : 120,
			left : 550,
			onClose : function() {
				that.close();
			}
		};

		that.selector = "#" + "div_lgstrStats_add";

		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null,
				windowOptions, function() {
					if (!that.isCreated) {
						that.init();
						that.bindEvents();
						that.isCreated = true;
					}
				});

		that.ognlSelector = "#" + id;
	},

	add : function() {
		var that = this;
		var url = CONTEXT_PATH + "/lgstrStats/addLgstrStatsRes.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},

	close : function() {
		var that = this;
		windowObj.removeWindow(that.ognlSelector);
		that.ognlSelector = null;
		that.isCreated = false;
	}

};
