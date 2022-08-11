windowObj.ocpatHistObj = {
		
		TITLE : "변경이력 등록",
		
		CLASS_NAME : "RdtOcpeHtCreate",
		
		selector : null,
		
		isCreated : false,
		
		init : function() {
			var that = this;
			
			$("form", that.selector).ajaxForm({
				dataType : "json",
				success : function(result) {
					if(result) {
						that.close();
						$.messager.alert(that.TITLE, "변경이력이 등록되었습니다.");
					}
					else {
						$.messager.alert(that.TITLE, "변경이력 등록에 실패하였습니다.");
					}
				}
			});
		},
		
		initUi : function(){
			var that = this;
			
			// easyUi설정
			$("#addRdtOcpeHtRegstr .easyui-datebox", that.selector).datebox();
			$("#addRdtOcpeHtRegstr .easyui-textbox", that.selector).textbox();
			$("#addRdtOcpeHtRegstr .easyui-combobox", that.selector).combobox();
		},
		
		bindEvents : function() { 
			var that = this;
			
			// 저장버튼
			$(".btn_save_RdtOcpeHtRegstr", that.selector).on("click", function() {
				that.save();
				return false;
			});
			
			// 취소버튼
			$(".btn_close_RdtOcpeHtRegstr", that.selector).on("click", function() {
				that.close();
			});
		},

		open : function(ftrIdn,ftrCde){
			var that = this;
			
			if(that.selector) {
				that.close();
			}
			var url = CONTEXT_PATH + "/ocpat/" + ftrIdn + "/" + ftrCde + "/addRdtOcpeHtPage.do";
			var windowOptions = {
					width : 660,
					top : 120,
					left : 330,
					isClose : function() {
						that.close();
					}
				};
				
				var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
					if(!that.isCreated) {
						that.init();
						that.initUi();
						that.bindEvents();
						that.isCreated = true;
					}
				});
				
				that.selector = "#" + id;
		},
		save : function() {
			var that = this;
			
			var url = CONTEXT_PATH + "/ocpat/addRdtOcpeHt.do";
			$("form", that.selector).attr("action", url);
			$("form", that.selector).submit();
			
		},

		close : function() {
			var that = this;
			
			windowObj.removeWindow(that.selector);
			that.selector = null;
			that.isCreated = false;
		}
};
