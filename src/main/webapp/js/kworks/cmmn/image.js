/**
 * 사진 객체
 * 20170404 : imageObj는 차후 필요 시 개발 예정 - 윤중근
 * 
 */
var imageObj = {
		
	TITLE : "사진 검색",
		
	CLASS_NAME : "imageSearch",
	
	selector : null,
	
	isCreated : false,
	
	initUi : function() {
		var that = this;
		
	},
	
	bindEvents : function() {
		var that = this;
		
	},
	
	open : function() {
		var that = this;
		
	},
	
	search : function() {
		var that = this;
		
	},
	
	close : function() {
		var that = this;
		
		that.selector = null;
		that.isCreated = false;
	}
};

/**
 * 사진조회 객체
 */
imageObj.view = {
	
	TITLE : null,
	
	CLASS_NAME : "imageView",
	
	selector : null,
	
	imageNo : null,
	
	isCreated : false,
	
	bindEvents : function() {
		var that = this;

		// 사진 내려받기
		$(".btn_down_image_view", that.selector).on("click", function() {
			that.download();
			return false;
		});
		
	},
	
	open : function(TITLE, imageNo) {
		var that = this;
		
		if(that.selector) {
			that.close();
		}
		
		that.TITLE = TITLE;
		that.imageNo = imageNo;
		
		var url = CONTEXT_PATH + "/cmmn/image/" + that.imageNo + "/select.do";
		var windowOptions = {
			width : 445,
			height : 571,
			top : 120,
			left : 330,
			isClose : function() {
				that.close();
			}
		};
		
		var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
			that.bindEvents();
			if(!that.isCreated) {
				that.isCreated = true;
			}
		});
		
		that.selector = "#" + id;
	},
	
	download : function() {
		var that = this;

		var url = CONTEXT_PATH + "/cmmn/image/" + that.imageNo + "/download.do";
		
		$("form", that.selector).attr("action", url);
		$("form", that.selector).submit();
	},
	
	close : function() {
		var that = this;
		
		that.TITLE = null;
		that.selector = null;
		that.isCreated = false;
	}
};