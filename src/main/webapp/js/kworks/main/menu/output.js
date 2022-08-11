/**
 * 출력 메뉴 객체
 * @type {Object}
 */
menuObj.outputObj = {
		
	/**
	 * 페이지 명
	 * @type {String}
	 */
	PAGE : "output",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : "#div_menu_panel_output",
	
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "출력",
	
	/**
	 * 팝업
	 * @type {Popup}
	 */
	popup : null,
	
	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		
		that.bindEvents();
		
		// 고급 출력 객체 초기화
		hghnkOutputObj.init();
		hghnkOutputObj.getPromise().done(function() {
			var data = hghnkOutputObj.getData();
			that.load(data);
		});
	},
	
	/**
	 * 이벤트 등록
	 */
	bindEvents : function() {
		var that = this;
		
		// 고급 출력 서비스 이용
		$(".a_connect_output", that.selector).click(function() {
			hghnkOutputObj.getCapabilities(true);
			hghnkOutputObj.getPromise().done(function() {
				var data = hghnkOutputObj.getData();
				that.load(data);
			});
			return false;
		});
	},
	
	/**
	 * 로딩
	 * @param data 데이터
	 */
	load : function(data) {
		var that = this;
		if(data && data.layouts) {
			var tagStr = '';
			for(var layoutId in data.layouts) {
				tagStr += '<li class="ma_l_15">';
				tagStr += '<a href="#" data-layout-id="';
				tagStr += layoutId;
				tagStr += '">';
				tagStr += '<img class="layout_img" src="';
				tagStr += hghnkOutputObj.getLayoutImgSrc(layoutId);
				tagStr += '" alt="';
				tagStr += data.layouts[layoutId].name;
				tagStr += '" />';
				tagStr += '</a>';
				tagStr += '<span>';
				tagStr += data.layouts[layoutId].name;
				tagStr += '<br /><br />용지방향 : ';
				tagStr += data.layouts[layoutId].type == "Landscape"?"가로":"세로";
				tagStr += '</span>';
				tagStr += '</li>';
			}
			
			$(".print_ul_forms", that.selector).html(tagStr);
			$(".print_ul_forms li a", that.selector).click(function() {
				var node = $(this);
				that.openHghnkOutput(node.attr("data-layout-id"));
				return false;
			});
			
			var menuPanelHeight = $("#div_menu_panel").height();
			var titleHeight = $(".title", that.selector).height();
			var divTitle = $(".div_title", that.selector).height();
			
			$(".div_content", that.selector).height(menuPanelHeight - titleHeight - divTitle - 20);
		}
	},
	
	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		$(that.selector).show();
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		$(that.selector).hide();
	},
	
	/**
	 * 고급 출력 열기
	 * @param {String} layoutId 레이아웃 아이디
	 */
	openHghnkOutput : function(layoutId) {
		var that = this;
		
		if(that.popup) {
			that.popup.close();
		}
		
		var layOut = hghnkOutputObj.getLayout(layoutId);
		
		var width, height = 0;
		if(layOut.type == "Landscape") {
			/*width = 1120;*/
			width = 1125;
			height = 790;
		}
		else {
			width = 750;
			height = 850;
		}
		
		var url = CONTEXT_PATH + "/popup/output/exportOutput.do?layoutId="+layoutId;
		var options = {
			scrollbars : "yes",
			left : 100+"px",
			top : 20+"px",
			width : width+"px",
			height : height+"px"
		};
		
		that.popup = popupUtils.open(url, "exportOutput", options);
	},
	
	confirmRedirect : function() {
		var that = this;
		if(PROJECT_CODE == 'sc' || PROJECT_CODE == 'yy') {
			$.messager.confirm(that.TITLE, "지금 바로 출력하시겠습니까?", function(isTrue) {
				if(isTrue) {
					var url = CONTEXT_PATH + "/popup/output/selectOneHighRankOutput.do?";
					$.get(url).done(function(result){
						if(result && result.exportNo){
							var exportNo = result.exportNo;
							var url = CONTEXT_PATH + "/popup/output/highRankOutput.do?exportNo="+exportNo;
							var options = {
								scrollbars : "no",
								left : 100+"px",
								top : 20+"px",
								width : 750+"px",
								height : 850+"px"
							};
							that.popup = popupUtils.open(url, "exportOutput", options);
						}});
				}else {
					window.open(CONTEXT_PATH + "/self/export/list.do", "_blank");
				}
			});
		}else {
			$.messager.confirm(that.TITLE, "고급출력은 '메인 > 나의 시스템 > 데이터요청 관리' 에서 출력 할 수 있습니다. 지금 확인하시겠습니까?", function(isTrue) {
				if(isTrue) {
					window.open(CONTEXT_PATH + "/self/export/list.do", "_blank");
				}
			});
		}
	}
};