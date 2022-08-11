/**
 * 농지·산지 전용 객체
 * @type {Object}
 */
var seaollObj = {
		
		/**
		 * 제목
		 * @type {String}
		 */
		TITLE : "농지·산지 전용",
		
		/**
		 * 클래스 명
		 * @type {String}
		 */
		CLASS_NAME : "Seaoll",
		
		/**
		 * 선택자
		 * @type {String}
		 */
		selector : null,
		
		/**
		 * 토지정보
		 * @type {String}
		 */
		pnu : null,	
		
		/**
		 * 열기
		 * @param {String} pnu PNU 코드
		 */
		open : function(pnu){
			var that = this;
			
			// 이전 창 닫기
			if(that.selector) {
				that.close();
			}
			
			that.pnu = pnu;
			var url = CONTEXT_PATH + "/seaoll/" + pnu + "/selectOne.do";
			
			
			var windowOptions = {
				width : 600,
				height : 470,
				onClose : function() {
					that.close();
				}
			};
			
			var params = { pnu : pnu };
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, params, windowOptions, function() {
				$(".agrld_info_tap").tabs({
					
				});
				$(".a_agrld_close",that.selector).click(function(){
					that.close();
				});
			});
			that.selector = "#" + id;
		},
		
		close : function(){
			var that = this;
			that.pnu = null;
			windowObj.removeWindow(that.selector);
			that.selector = null;
			return false;
		}
};