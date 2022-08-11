$(function() {
	bassThemamapObj.init();
});

/**
 * 시스템 기본 주제도 객체
 * @type {Object}
 */
var bassThemamapObj = {
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_bass_themamap_list",
	
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
		
		// 시스템 기본 주제도 항목 선택
		$(".tbl_bass_themamap_list tbody tr", that.selector).click(function() {
			var element = $(this);
			var sysId = element.attr("data-sys-id");
			that.openModifyDialog(sysId);
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
		$(".tbl_bass_themamap_list tbody tr", that.selector).removeClass("on");
	},
	
	/**
	 * 팝업 창 닫기
	 */
	closeDialog : function() {
		var that = this;
		that.modifyDialog.close();
	},
	
	/**
	 * 수정 팝업 열기
	 * @param themamapId
	 */
	openModifyDialog : function(themamapId) {
		var that = this;
		that.closeDialog();
		that.modifyDialog.open(themamapId);
	}
};

/**
 * 시스템 기본 주제도 수정 팝업
 * @type {Object}
 */
bassThemamapObj.modifyDialog = {
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_bass_themamap_modify",
		
	/**
	 * 부모 객체 - 시스템 기본 주제도 객체
	 * @type {Object}
	 */
	parent : null,
	
	/**
	 * 시스템 기본 주제도 번호
	 * @type {Object}
	 */
	sysId : null,
		
	/**
	 * 초기화
	 * @param {Object} parent  부모 객체
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
		
		// 시스템 기본 주제도 수정 다이어로그 설정
		$(that.selector).dialog({
			autoOpen: false,
			width: 700,
	     	height: 230,
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
		
		// Ajax 파일 업로드를 위해 ajaxForm 으로 등록
		$("form", that.selector).ajaxForm({
			dataType : "json",
			success : function(result) {
				if(result && result.rowCount && result.rowCount == 1) {
					that.parent.search();
				}
				else {
					alert("시스템 기본 주제도 설정에 실패했습니다.");
				}
			}
		});
	},
	
	/**
	 * 수정
	 */
	modify : function() {
		var that = this;
		var url = restUtils.getResUrl() + that.sysId + "/modify.do";
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	/**
	 * 열기
	 * @param {Number} sysId 주제도 번호
	 */
	open : function(sysId) {
		var that = this;
		that.parent.clearSelected();
		
		that.sysId = sysId;
		var url = restUtils.getResUrl() + sysId + "/select.do";
		
		$.get(url).done(function(result) {
			if(result && result.data) {
				var data = result.data;
				
				// 시스템명
				$(".themamapId", that.selector).html(data.sysAlias);
				// 기본주제도
				$("select[name=themamapId]").val(data.themamapId);
				
				$(that.selector).dialog("open");
			}
			else {
				alert("시스템 기본 주제도 정보를 불러오는데 실패했습니다.");
			}
		}).fail(function(result) {
			alert("시스템 기본 주제도 정보를 불러오는데 실패했습니다.");
		});
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		that.parent.clearSelected();
		$(that.selector).dialog("close");
	}
		
};

