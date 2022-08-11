/**
 * 항목 선택
 */
windowObj.resultItemSetupObj = {
		
	/**
	 * 제목
	 * @type {String}
	 */
	TITLE : "항목 설정",

	/**
	 * 클래스 명
	 * @type {String}
	 */
	CLASS_NAME : "ResultItemSetup",
	
	/**
	 * 선택자
	 * @type {String}
	 */
	selector : null,
	
	/**
	 * 선택완료 시 실행될 함수
	 */
	onSubmit : null,
	
	/**
	 * 열기
	 * @param {Array.Array<Object>} columns 컬럼 목록
	 * @param {Object} options 옵션
	 */
	open : function(columns, options) {
		var that = this;
		
		var url = CONTEXT_PATH + "/window/result/itemSetup/page.do";
		var windowOptions = {
			width : 270,
			height : 400,
			top : 120,
			modal : true,
			onClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.initUi(that.createTreeData(columns));
			that.bindEvents();
			// 현재 객체에 전달되는 값 적용 (onSubmit)
			if(options) {
				$.extend(that, options);
			}
		});
		that.selector = "#" + id;
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		windowObj.removeWindow(that.selector);
		that.selector = null;
	},
	
	/**
	 * UI 초기화
	 */
	initUi : function(data) {
		var that = this;
		
		// 레이아웃 설정
		$(".div_window_result_item_setup", that.selector).layout({
			fit : true
		});
		
		// 항목 트리
		$(".ul_item_tree", that.selector).tree({
			data : data,
			dnd : true,
			animate : true,
			checkbox : true,
			onDragOver : function(target, source, point) {
				if(point == "append" || !$(".ul_item_tree", that.selector).tree("isLeaf", target)) {
					return false;
				}
			},
			onBeforeDrop : function(target, source, point) {
				if(point == "append" || !$(".ul_item_tree", that.selector).tree("isLeaf", target)) {
					return false;
				}
			},
			onDrop : function(target, source, point) {
				if(point == "append" || !$(".ul_item_tree", that.selector).tree("isLeaf", target)) {
					return false;
				}
			}
		});
		
	},
	
	/**
	 * 트리 데이터 생성
	 * @param {Array.Array<Object>} columns 컬럼 목록
	 * @returns {Array<Object>}
	 */
	createTreeData : function(columns) {
		var children = [];
		if(columns && columns.length > 0) {
			var cols = columns[0];
			for(var i=0, len=cols.length; i < len; i++) {
				var column = cols[i];
				var checked = column.hidden?false:true;
				var node = {
					id : column.field,
					text : column.title,
					checked : checked
				};
				children.push(node);
			}
		}
		
		var gridData = [{
			id : 0,
			text : "항목",
			children : children
		}];
		return gridData;
	},
	
	/**
	 * 이벤트 연결
	 */
	bindEvents : function() {
		var that = this;
		
		// 선택 완료
		$(".a_completion_choice", that.selector).click(function() {
			that.completionChoice();
			return false;
		});
		
		// 닫기
		$(".a_close", that.selector).click(function() {
			that.close();
			return false;
		});
		
	},
	
	/**
	 * 선택 완료
	 */
	completionChoice : function() {
		var that = this;
		if(that.onSubmit) {
			var data = $(".ul_item_tree", that.selector).tree("getRoot").children;
			that.onSubmit(data);
		}
		that.close();
	}
		
};