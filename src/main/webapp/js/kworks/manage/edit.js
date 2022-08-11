$(function() {
	editObj.init();
});

/**
 * 사용자 객체
 * @type {Object}
 */
var editObj = {
		
	/**
	 * 선택자
	 * @type {String}
	 */	
	selector : "#div_edit_manage_list",
	
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
		
		// 검색
		$(".a_search", that.selector).click(function() {
			that.search(1);
			return false;
		});
		
		// 데이터 수정 창 열기
		$(".tbl_user_list tbody tr",that.selector).click(function(){
			var element = $(this);
			var dataId = element.attr("data-user-id");
			that.openModifyDialog(dataId);
			$(element).addClass("on");
		});
		
		// 엔터키 검색설정
		$("#searchKeyword").keydown(function(e) {
			if(e.keyCode == 13){
				that.search(1);
			}
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
		$(".tbl_user_list tbody tr", that.selector).removeClass("on");
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
	 * @param dataId
	 */
	openModifyDialog : function(dataId) {
		var that = this;
		that.modifyDialog.open(dataId);
	}
};

/**
 * 편집여부 수정
 */
editObj.modifyDialog = {
		
		/**
		 * 선택자
		 * @type {String}
		 */
		selector : "#div_edit_modify",
		
		/**
		 * 부모 객체 - 편집여부 객체
		 * 
		 * @type {Object}
		 */
		parent : null,
		
		//dataId : null,
		
		/**
		 * 초기화
		 * 
		 * @param {Object} parent 부모 객체
		 */
		init : function(parent){
			var that = this;
			that.parent = parent;
			
			that.initUi();
			that.bindEvents();
		},
		
		/**
		 * UI 초기화
		 */
		initUi : function(){
			var that = this;
			
			// 편집여부 수정 다이어로그 설정
			$(that.selector).dialog({
				autoOpen: false,
				width : 700,
				height: 300,
				buttons: {
					"수정" : function() {
			    		that.modify();
			    	},
			    	"닫기" : function() {
			        	that.close();
			        }
				},
				close: function(){
					that.close();
				}
			});
		
		},
		
		/**
		 * 이벤트 등록
		 */
		bindEvents : function(){
			var that = this;
			
			$("form", that.selector).submit(function() {
				return false;
			});
		},
			
		
		/**
		 * 정리
		 */
		clear : function() {
			var that = this;
			
			that.parent.clearSelected();
			that.deptCode = null;
			$(".dataId", that.selector).text("");
			$(".dataAlias", that.selector).text("");
			$("select[name=editAt]", that.selector).val("");
		},
		
		/**
		 * 수정
		 */
		modify : function() {
			var that = this;
			var url = restUtils.getResUrl() + that.dataId + "/modify.do";
			$.post(url,$("form", that.selector).serialize()).done(function(result) {
				if(result && result.data && result.data == 1) {
					that.parent.search();
				}
				else {
					alert("선택한 편집관리 수정이 실패했습니다.");
				}
			}).fail(function() {
				alert("선택한 편집관리 수정이 실패했습니다.");
			});
		},
		
		/**
		 * 열기
		 * @param {String} deptCode 부서코드
		 */
		open : function(dataId) {
			var that = this;
			that.clear();
			
			that.dataId = dataId;
			var url = restUtils.getResUrl() + that.dataId + "/select.do";
			
			var param = {};
			$.get(url,param).done(function(result){
				var data = result.data;
				$(".dataId", that.selector).text(data.dataId);
				$(".dataAlias", that.selector).text(data.dataAlias);
				$("select[name=editAt]", that.selector).val(data.editAt);
				
			});
			
			$(that.selector).dialog("open");
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