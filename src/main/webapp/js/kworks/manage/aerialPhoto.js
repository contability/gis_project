$(function() {
	aerialPhotoObj.init();
});

/**
 * 항공사진 객체
 * @type {Object}
 */
var aerialPhotoObj = {
		
	/**
	 * 선택자
	 */
	selector : "#div_aerialPhoto_list",
	
	/**
	 * 초기화 
	 */
	init : function() {
		var that = this;
		
		that.modifyDialog.init(that);
		that.bindEvents();
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 항목 선택
		$(".tbl_aerialPhoto_list tbody tr", that.selector).click(function() {
			var element = $(this);
			var mapNo = element.attr("data-map-no");
			that.openModifyDialog(mapNo);
			$(element).addClass("on");
		});
	},
	
	/**
	 * 검색
	 */
	search : function() {
		var that = this;
		
		$("form", that.selector).submit();
	},

	/**
	 * 선택 해제
	 */
	clearSelected : function() {
		var that = this;
		$(".tbl_aerialPhoto_list tbody tr", that.selector).removeClass("on");
	},

	/**
	 * 수정 팝업 열기
	 */
	openModifyDialog : function(mapNo) {
		var that = this;
		
		that.closeDialog();
		that.modifyDialog.open(mapNo);
	},
	
	/**
	 * 팝업 창 닫기
	 */
	closeDialog : function() {
		var that = this;
		
		that.modifyDialog.close();
	}
};

/**
 * 배경지도 수정 객체
 * @type {Object}
 */
aerialPhotoObj.modifyDialog = {
	
	/**
	 * 선택자
	 */
	selector : "#div_aerialPhoto_modify",
	
	/**
	 * 부모 객체 - 지도 객체
	 */
	parent : null,
	
	/**
	 * 지도 번호
	 */
	mapNo : null,
	
	/**
	 * 초기화
	 */
	init : function(parent) {
		var that = this;
		
		that.parent = parent;
		that.initUi();
	},

	/**
	 * UI 초기화
	 */
	initUi : function() {
		var that = this;
		
		$(that.selector).dialog({
			autoOpen: false,
			width: 700,
	     	height: 380,
	      	buttons: {
		    	"수정" : function() {
		    		that.modify();
		    	},
		        "닫기" : function() {
		        	that.close();
		        }
			},
	      	close: function() {
	      		that.close();
	      	}
	    });
		
		$("form", that.selector).ajaxForm({
			dataType : "json",
			beforeSubmit : function(arr, form) {
				for(var i=0, len=arr.length; i < len; i++) {
					var obj = arr[i];
					var name = obj.name;
					
					// 필수 입력 체크
					if(name == "description") {
						if(!obj.value) {
							alert("배경지도 설명을 입력하여 주십시오.");
							form.find("input[name=" + name + "]").focus();
							return false;
						}
					}
					else if(name == "requestFormat") {
						if(!obj.value) {
							alert("요청 포멧을 입력하여 주십시오.");
							form.find("input[name=" + name + "]").focus();
							return false;
						}
					}
				}
			},
			success : function(result) {
				if(result) {
					that.parent.search();
				}
				else {
					alert("배경지도 정보 수정에 실패하였습니다.");
				}
			}
		});
	},
	
	/**
	 * 팝업 열기
	 */
	open : function(mapNo) {
		var that = this;
		that.clear();
		
		that.mapNo = mapNo;
		var url = restUtils.getResUrl() + mapNo + "/select.do";
		
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;
				
				$("input[name=title]", that.selector).val(data.title);
				$("input[name=description]", that.selector).val(data.description);
				$("input[name=requestFormat]", that.selector).val(data.requestFormat);
				$("select[name=baseYn]", that.selector).val(data.baseYn);
				$("select[name=baseMapYn]", that.selector).val(data.baseMapYn);
				$("select[name=layerYn]", that.selector).val(data.layerYn);
				$("input[name=photographyYear]", that.selector).val(data.photographyYear);
				$("input[name=photographyInstitution]", that.selector).val(data.photographyInstitution);
				$("input[name=resolution]", that.selector).val(data.resolution);
				
				$(that.selector).dialog("open");
			}
			else {
				alert("배경지도 정보를 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("배경지도 정보를 불러오는데 실패했습니다.");
		});
	},
	
	/**
	 * 배경지도 정보 수정
	 */
	modify : function() {
		var that = this;
		
		var url = restUtils.getResUrl() + that.mapNo + "/modifyBcrnMapInfo.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 정리
	 */
	clear : function() {
		var that = this;
		
		that.parent.clearSelected();
		that.mapNo = null;
		$("input[name=description]", that.selector).val("");
		$("input[name=requestFormat]", that.selector).val("");
	},
	
	/**`
	 * 닫기
	 */
	close : function() {
		var that = this;
		
		that.clear();
		$(that.selector).dialog("close");
	}
};
