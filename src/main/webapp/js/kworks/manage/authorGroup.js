$(function() {
	authorGroupObj.init();
});

/**
 * 권한 그룹 관리 객체
 * 
 * @type {Object}
 */
var authorGroupObj = {

	/**
	 * 선택자
	 * 
	 * @type {String}
	 */
	selector : "#div_authorGroup_list",

	/**
	 * 데이터 카테고리 아이디
	 * @type {Object}
	 */
	dataCtgryId : null,
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;

		that.addDialog.init(that);
		that.modifyDialog.init(that);
		that.bindEvents();
	},

	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;

		// 권한 그룹 등록 창 열기
		$(".a_add_authorGroup", that.selector).click(function() {
			that.openAddDialog();
		});

		// 권한 그룹 항목 선택(조회&수정&삭제)
		$(".tbl_author_group_list tbody tr", that.selector).click(function() {
			var element = $(this);
			var authorGroupNo = element.attr("data-authorGroup-no");
			that.openModifyDialog(authorGroupNo);
			$(element).addClass("on");
		});
	},
	
	/**
	 * 검색
	 * @param {Number} pageIndex 페이지 인덱스
	 */
	search : function(pageIndex) {
		var that = this;
		if(pageIndex) {
			$("#pageIndex").val(pageIndex);
		}
		$("form", that.selector).submit();
	},
	
	/**
	 * 선택 해제
	 */
	clearSelected : function() {
		var that = this;
		$(".tbl_author_group_list tbody tr", that.selector).removeClass("on");
	},

	/**
	 * 팝업 창 닫기
	 */
	closeDialog : function() {
		var that = this;
		that.addDialog.close();
		that.modifyDialog.close();
	},
	
	/**
	 * 등록 팝업 열기
	 */
	openAddDialog : function() {
		var that = this;
		that.closeDialog();
		that.addDialog.open();
	},
	
	/**
	 * 수정 팝업 열기
	 * @param authorGroupNo
	 */
	openModifyDialog : function(authorGroupNo) {
		var that = this;
		that.modifyDialog.open(authorGroupNo);
	}
};

/**
 * 권한 그룹 등록
 * 
 * @type {Object}
 */
authorGroupObj.addDialog = {

	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_author_group_add",
		
	/**
	 * 부모 객체 - 권한 그룹 객체
	 * 
	 * @type {Object}
	 */
	parent : null,

	/**
	 * 초기화
	 * 
	 * @param {Object} parent 부모 객체
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;

		that.initUi();
		that.bindEvents();
	},

	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;

		// 권한 그룹 등록 다이어로그 설정
		$(that.selector).dialog({
			autoOpen : false,
			height : 600,
			width : 700,
			buttons : {
				"등록" : function() {
					that.add();
				},
				"닫기" : function() {
					that.close();
				}
			},
			close : function() {
				that.close();
			}
		});
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 시스템(서비스) 체크박스 전체 선택
		$(".a_chk_system", that.selector).click(function() {
			$(".ul_author_group_system li input[type=checkbox]", that.selector).prop("checked", true);
		});
		// 시스템(서비스) 체크박스 전체 해제
		$(".a_unchk_system", that.selector).click(function() {
			$(".ul_author_group_system li input[type=checkbox]", that.selector).prop("checked", false);
		});
		
		// 데이터 카테고리 selectbox 이벤트 (하위 레이어 리스트업)
		$(".select_author_group_data").change(function() {
			var element = $(this);
			var dataCtgryId = element.val();
			
			if(dataCtgryId == "all"){
				$(".tbl_author_group_datas tbody tr", that.selector).show();
			}
			else {
				$(".tbl_author_group_datas tbody tr", that.selector).hide();
				$("." + dataCtgryId ).show();
			}
			
			that.triggerCheck();
		});
		
		// 데이터 접근권한 체크박스 전체선택
		$(".a_chk_data").click(function() {
			$(".tbl_author_group_datas thead tr th input[type=checkbox]", that.selector).prop("checked", true);
			$(".tbl_author_group_datas tbody tr:visible input[type=checkbox]", that.selector).prop("checked", true);
		});
		
		// 데이터 접근권한 체크박스 전체해제
		$(".a_unchk_data").click(function() {
			$(".tbl_author_group_datas thead tr th input[type=checkbox]", that.selector).prop("checked", false);
			$(".tbl_author_group_datas tbody tr:visible input[type=checkbox]", that.selector).prop("checked", false);
		});
		
		// 레이어명 체크박스 체크 시 권한 모두 선택 / 모두 해제 (표시여부, 편집여부, 공간출력여부, 속성출력여부)
		$(".td_layer_name input[type=checkbox]", that.selector).click(function() {
			var element = $(this);
			var checked = element.is(":checked");
			element.parent().parent().find("input[type=checkbox]").prop("checked", checked);
		});
		
		// 체크 시 해당 권한의 데이터 모두 선택 / 모두 해제
		$(".chk_author_all", that.selector).click(function() {
			var element = $(this);
			var type = element.val();
			var checked = element.is(":checked");
			$(".tbl_author_group_datas tbody tr:visible input[name=" + type + "]", that.selector).prop("checked", checked);
		});
		
		// 권한 체크시 행 및 열 제목의 체크박스 동기화
		$(".td_author_item", that.select).on("click", "input[type=checkbox]", function() {
			var element = $(this);
			var trElement = element.parent().parent();
			that.triggerLayerCheck(trElement);
			
			var type = element.attr("name");
			that.triggerTypeCheck(type);
		});
	},
	
	/**
	 * 행 및 열 제목의 체크 박스 동기화
	 */
	triggerCheck : function() {
		var that = this;
		$(".tbl_author_group_datas tbody tr", that.selector).each(function() {
			var element = $(this);
			that.triggerLayerCheck(element);
		});
		
		$(".chk_author_all", that.selector).each(function() {
			var element = $(this);
			var type = element.val();
			that.triggerTypeCheck(type);
		});
	},
	
	/**
	 * 행 체크 박스 동기화
	 * @param trElement
	 */
	triggerLayerCheck : function(trElement) {
		if(trElement.find(".td_author_item input[type=checkbox]").length == trElement.find(".td_author_item input[type=checkbox]:checked").length) {
			trElement.find(".td_layer_name input[type=checkbox]").prop("checked", true);
		}
		else {
			trElement.find(".td_layer_name input[type=checkbox]").prop("checked", false);
		}
	},
	
	/**
	 * 열 체크 박스 동기화
	 * @param type
	 */
	triggerTypeCheck : function(type) {
		var that = this;
		if($(".tbl_author_group_datas tbody tr:visible input[name=" + type + "]", that.selector).length == $(".tbl_author_group_datas tbody tr:visible input[name=" + type + "]:checked", that.selector).length) {
			$(".chk_author_all[value=" + type + "]", that.selector).prop("checked", true);
		}
		else {
			$(".chk_author_all[value=" + type + "]", that.selector).prop("checked", false);
		}
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		
		// 권한 타입 초기화
		$(".chk_author_all", that.selector).prop("checked", false);
		
		// 권한 그룹 명 텍스트박스 초기화
		$("#txt_author_group_add_nm").val("");
		
		// 서비스 접근 권한 체크박스 초기화
		$(".ul_author_group_system li input[type=checkbox]", that.selector).prop("checked", false);
		
		// 데이터 카테고리 셀렉트박스 초기화
		$(".div_author_group_data span select", that.selector).val("all").attr('selected', 'selected');
		
		$(".tbl_author_group_datas tbody tr", that.selector).show();
		
		// 데이터 접근 권한 체크박스 초기화
		$(".tbl_author_group_datas tbody tr input[type=checkbox]", that.selector).prop("checked", false);
	},
	
	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		$(that.selector).dialog("open");
	},

	/**
	 * 등록
	 */
	add : function() {
		var that = this;
		
		// 필수입력값 체크
		if($("#txt_author_group_add_nm").val() == "") {
			alert("권한 그룹 명을 입력하여 주십시오.");
			$("#txt_author_group_add_nm").focus();
			return false;
		}
		
		var data = {
			authorGroupNm : $("#txt_author_group_add_nm").val(),
			kwsSysAuthors : [],
			kwsDataAuthors : [],
			kwsBaseMapAuthors : []
		};

		$(".ul_author_group_system li input[type=checkbox]", that.selector).each(function() {
			var element = $(this);
			var authorAt = element.is(":checked")? "Y" : "N";
			
			var sysObj = {
				sysId : element.val(),
				authorAt : authorAt
			};
			data.kwsSysAuthors.push(sysObj);
		});
		
		
		$(".tbl_author_group_datas tbody tr", that.selector).each(function() {
			var trElement = $(this);
			var dataId = trElement.attr("data-id");
			var category = trElement.attr("data-category");
			var indictAt = $("input[name=indictAt]", trElement).is(":checked") ? "Y" : "N";
			var editAt = $("input[name=editAt]", trElement).is(":checked") ? "Y" : "N";
			var exportAt = $("input[name=exportAt]", trElement).is(":checked") ? "Y" : "N";
			var prntngAt = $("input[name=prntngAt]", trElement).is(":checked") ? "Y" : "N";
			
			var dataObj = {
				indictAt : indictAt,
				editAt : editAt,
				exportAt : exportAt,
				prntngAt : prntngAt
			};
			
			if(category == "DATA") {
				dataObj.dataId = dataId;
				data.kwsDataAuthors.push(dataObj);
			}
			else {
				dataObj.mapNo = dataId;
				data.kwsBaseMapAuthors.push(dataObj);
			}
			
		});
		
		var jsonStr = JSON.stringify(data);
		
		var url = $("form", that.selector).attr("action");
		$.post(url, {
			data : jsonStr
		}).done(function(result) {
			if(result && result.rowCount && result.rowCount == 1) {
				that.parent.search();
			}
			else {
				alert("권한 그룹 등록에 실패했습니다.");
			}
		});
	},

	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		that.clear();
		$(that.selector).dialog("close");
	}

};

/**
 * 권한 그룹 수정
 * 
 * @type {Object}
 */
authorGroupObj.modifyDialog = {
		
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_author_group_modify",
		
	/**
	 * 부모 객체 - 권한 그룹 객체
	 * 
	 * @type {Object}
	 */
	parent : null,

	/**
	 * 초기화
	 * 
	 * @param {Object} parent 부모 객체
	 */
	init : function(parent) {
		var that = this;
		that.parent = parent;

		that.initUi();
		that.bindEvents();
	},

	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;

		// 권한 그룹 등록 다이어로그 설정
		$(that.selector).dialog({
			autoOpen : false,
			height : 600,
			width : 700,
			buttons : {
				"수정" : function() {
		    		that.modify();
		    	},
		    	"삭제" : function() {
		    		if(confirm("삭제하시겠습니까?")) {
		    		that.remove();
		    		}
		    	},
			},
			close : function() {
				that.close();
			}
		});
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 시스템(서비스) 체크박스 전체 선택
		$(".a_chk_system", that.selector).click(function() {
			$(".ul_author_group_system li input[type=checkbox]", that.selector).prop("checked", true);
		});
		// 시스템(서비스) 체크박스 전체 해제
		$(".a_unchk_system", that.selector).click(function() {
			$(".ul_author_group_system li input[type=checkbox]", that.selector).prop("checked", false);
		});
		
		// 데이터 카테고리 selectbox 이벤트 (하위 레이어 리스트업)
		$(".select_author_group_data").change(function() {
			var element = $(this);
			var dataCtgryId = element.val();
			
			if(dataCtgryId == "all"){
				$(".tbl_author_group_datas tbody tr", that.selector).show();
			}
			else {
				$(".tbl_author_group_datas tbody tr", that.selector).hide();
				$("." + dataCtgryId ).show();
			}
			
			that.triggerCheck();
		});
		
		// 데이터 접근권한 체크박스 전체선택
		$(".a_chk_data").click(function() {
			$(".tbl_author_group_datas thead tr th input[type=checkbox]", that.selector).prop("checked", true);
			$(".tbl_author_group_datas tbody tr:visible input[type=checkbox]", that.selector).prop("checked", true);
		});
		
		// 데이터 접근권한 체크박스 전체해제
		$(".a_unchk_data").click(function() {
			$(".tbl_author_group_datas thead tr th input[type=checkbox]", that.selector).prop("checked", false);
			$(".tbl_author_group_datas tbody tr:visible input[type=checkbox]", that.selector).prop("checked", false);
		});
		
		// 레이어명 체크박스 체크 시 권한 모두 선택 / 모두 해제 (표시여부, 편집여부, 공간출력여부, 속성출력여부)
		$(".td_layer_name input[type=checkbox]", that.selector).click(function() {
			var element = $(this);
			var checked = element.is(":checked");
			element.parent().parent().find("input[type=checkbox]").prop("checked", checked);
		});
		
		// 체크 시 해당 권한의 데이터 모두 선택 / 모두 해제
		$(".chk_author_all", that.selector).click(function() {
			var element = $(this);
			var type = element.val();
			var checked = element.is(":checked");
			$(".tbl_author_group_datas tbody tr:visible input[name=" + type + "]", that.selector).prop("checked", checked);
		});
		
		// 권한 체크시 행 및 열 제목의 체크박스 동기화
		$(".td_author_item", that.select).on("click", "input[type=checkbox]", function() {
			var element = $(this);
			var trElement = element.parent().parent();
			that.triggerLayerCheck(trElement);
			
			var type = element.attr("name");
			that.triggerTypeCheck(type);
		});
	},
	
	/**
	 * 행 및 열 제목의 체크 박스 동기화
	 */
	triggerCheck : function() {
		var that = this;
		$(".tbl_author_group_datas tbody tr", that.selector).each(function() {
			var element = $(this);
			that.triggerLayerCheck(element);
		});
		
		$(".chk_author_all", that.selector).each(function() {
			var element = $(this);
			var type = element.val();
			that.triggerTypeCheck(type);
		});
	},
	
	/**
	 * 행 체크 박스 동기화
	 * @param trElement
	 */
	triggerLayerCheck : function(trElement) {
		if(trElement.find(".td_author_item input[type=checkbox]").length == trElement.find(".td_author_item input[type=checkbox]:checked").length) {
			trElement.find(".td_layer_name input[type=checkbox]").prop("checked", true);
		}
		else {
			trElement.find(".td_layer_name input[type=checkbox]").prop("checked", false);
		}
	},
	
	/**
	 * 열 체크 박스 동기화
	 * @param type
	 */
	triggerTypeCheck : function(type) {
		var that = this;
		if($(".tbl_author_group_datas tbody tr:visible input[name=" + type + "]", that.selector).length == $(".tbl_author_group_datas tbody tr:visible input[name=" + type + "]:checked", that.selector).length) {
			$(".chk_author_all[value=" + type + "]", that.selector).prop("checked", true);
		}
		else {
			$(".chk_author_all[value=" + type + "]", that.selector).prop("checked", false);
		}
	},
	
	/**
	 * 열기
	 * @param {Number} authorGroupNo 공지사항 번호
	 */
	open : function(authorGroupNo) {
		var that = this;
		that.clear();
		
		that.authorGroupNo = authorGroupNo;
		
		var url = restUtils.getResUrl() + authorGroupNo + "/select.do";
		
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;

				// 권한 그룹 정보 로딩
				$("input[name=authorGroupNm]", that.selector).val(data.authorGroupNm);
				
				// 서비스 접근권한 체크
				for(var i in data.kwsSysAuthors) {
					if(data.kwsSysAuthors[i].authorAt == "Y") {
						$(".ul_author_group_system li input[value="+data.kwsSysAuthors[i].sysId+"]", that.selector).prop("checked", true);
					}
				}

				// 데이터 접근권한 체크
				// 배열 속성의 key를 비교
				for(var i in data.kwsDataAuthors) {
					var kwsDataAuthor = data.kwsDataAuthors[i];
					var dataId = kwsDataAuthor.dataId;
					for(var name in kwsDataAuthor) {
						if(name != "dataId" || name != "authorGroupNo") {
							var value = kwsDataAuthor[name];
							if(value == "Y") {
								$(".tbl_author_group_datas tbody tr[data-id="+dataId+"]", that.selector).find("input[name="+name+"]").prop("checked", true);
							}
						}
					}
				}
				
				for(var i in data.kwsBaseMapAuthors) {
					var kwsBaseMapAuthor = data.kwsBaseMapAuthors[i];
					var mapNo = kwsBaseMapAuthor.mapNo;
					for(var name in kwsBaseMapAuthor) {
						if(name != "mapNo" || name != "authorGroupNo") {
							var value = kwsBaseMapAuthor[name];
							if(value == "Y") {
								$(".tbl_author_group_datas tbody tr[data-id="+mapNo+"]", that.selector).find("input[name="+name+"]").prop("checked", true);
							}
						}
					}
				}
				
				// 권한 그룹 정보 수정창 열기
				$(that.selector).dialog("open");
				
				that.triggerCheck();
			}
			else {
				alert("권한 그룹 정보를 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("권한 그룹 정보를 불러오는데 실패했습니다.");
		});
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		
		that.parent.clearSelected();
		// 권한 타입 초기화
		$(".chk_author_all", that.selector).prop("checked", false);
		
		// 권한 그룹 명 텍스트박스 초기화
		$("#txt_author_group_modify_nm").val("");
		
		// 서비스 접근 권한 체크박스 초기화
		$(".ul_author_group_system li input[type=checkbox]", that.selector).prop("checked", false);
		
		// 데이터 카테고리 셀렉트박스 초기화
		$(".div_author_group_data span select", that.selector).val("all").attr('selected', 'selected');
		
		$(".tbl_author_group_datas tbody tr", that.selector).show();
		
		// 데이터 접근 권한 체크박스 초기화
		$(".tbl_author_group_datas tbody tr input[type=checkbox]", that.selector).prop("checked", false);
	},
	
	/**
	 * 수정
	 */
	modify : function(authorGroupNo) {
		var that = this;
		
		// 필수입력값 체크
		if($("#txt_author_group_modify_nm").val() == "") {
			alert("권한 그룹 명을 입력하여 주십시오.");
			$("#txt_author_group_modify_nm").focus();
			return false;
		}
		
		var data = {
			authorGroupNm : $("#txt_author_group_modify_nm").val(),
			kwsSysAuthors : [],
			kwsDataAuthors : [],
			kwsBaseMapAuthors : []
		};

		$(".ul_author_group_system li input[type=checkbox]", that.selector).each(function() {
			var element = $(this);
			var authorAt = element.is(":checked")? "Y" : "N";
			
			var sysObj = {
				sysId : element.val(),
				authorAt : authorAt
			};
			data.kwsSysAuthors.push(sysObj);
		});
		
		$(".tbl_author_group_datas tbody tr", that.selector).each(function() {
			var trElement = $(this);
			var dataId = trElement.attr("data-id");
			var category = trElement.attr("data-category");
			var indictAt = $("input[name=indictAt]", trElement).is(":checked") ? "Y" : "N";
			var editAt = $("input[name=editAt]", trElement).is(":checked") ? "Y" : "N";
			var exportAt = $("input[name=exportAt]", trElement).is(":checked") ? "Y" : "N";
			var prntngAt = $("input[name=prntngAt]", trElement).is(":checked") ? "Y" : "N";
			
			var dataObj = {
				indictAt : indictAt,
				editAt : editAt,
				exportAt : exportAt,
				prntngAt : prntngAt
			};
			
			if(category == "DATA") {
				dataObj.dataId = dataId;
				data.kwsDataAuthors.push(dataObj);
			}
			else {
				dataObj.mapNo = dataId;
				data.kwsBaseMapAuthors.push(dataObj);
			}
		});
		
		var jsonStr = JSON.stringify(data);
		
		var url = restUtils.getResUrl() + that.authorGroupNo + "/modify.do";
		$.post(url, {
			data : jsonStr
		}).done(function(result) {
			if(result && result.rowCount && result.rowCount == 1) {
				that.parent.search();
			}
			else {
				alert("권한 그룹 수정에 실패했습니다.");
			}
		});
	},
	
	/**
	 * 삭제
	 */
	remove : function() {
		var that = this;
		var url = restUtils.getResUrl() + that.authorGroupNo + "/remove.do";
		$.post(url).done(function(result) {
			if(result && result.rowCount && result.rowCount == 1) {
				that.parent.search(1);
			}
			else {
				alert("삭제할 권한 그룹을 사용하고 있는 사용자가 있는 경우 삭제할 수 없습니다.");
			}
		}).fail(function() {
			alert("삭제할 권한 그룹을 사용하고 있는 사용자가 있는 경우 삭제할 수 없습니다.");
		});	
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		that.clear();
		$(that.selector).dialog("close");
	}
};
