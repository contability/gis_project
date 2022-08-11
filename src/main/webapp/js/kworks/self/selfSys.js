$(document).ready(function(){
	selfSysObj.init();
});
/**
 * 
 */
var selfSysObj = {
		
		selector : "#div_selfSys_list",
		
		init : function(){
			var that = this;
			selfSysObj.viewDialog.init();
			selfSysObj.addDialog.init();
			selfSysObj.menuGroupAddDialog.init();
			that.bindEvents();
		},
		
		/**
		 * UI 초기화
		 */
		initUi : function() {
			
		},
		
		/**
		 * 이벤트 등록
		 */
		bindEvents : function() {
			var that = this;
			
			//상세보기 이벤트
			$("tbody",that.selector).on("click","tr",function(){
				selfSysObj.viewDialog.open($(this).attr("data-id"));
				return false;
			});
			
			//등록하기 버튼 이벤트
			$(".a_new_selfSys",that.selector).on("click",function(){
				selfSysObj.addDialog.open("ADD");
				return false;
			});
		}
};

//나의시스템 상세보기 팝업 객체
selfSysObj.viewDialog = {
		
		selector : "#div_selfSys_view",	
		
		SYS_ID : null,
		
		init : function(){
			var that = this;
			
			that.clear();
			that.initUi();
			that.bindEvents();
		},
		
		/**
		 * UI 초기화
		 */
		initUi : function(){
			var that = this;
			
			$(that.selector).dialog({
				autoOpen: false,
		     	height: 600,
		      	width: 450,
		      	buttons: {
			    	"편집" : function() {
			    		that.edit();
			    	},
			    	
			    	"삭제" : function(){
			    		that.remove();
			    	},
			    	
			        "닫기" : function() {
			        	that.close();
			        }
				},
		      	close: function() {
		      		that.close();
		      	}
		    });
		},
		
		/**
		 * 이벤트 등록
		 */
		bindEvents : function(){
			
		},
		
		/**
		 * 열기
		 */
		open : function(sysId){
			var that = this;
			that.SYS_ID = sysId;
			$(that.selector).dialog("open");
			that.selfMenuList();
		},
		
		/**
		 * 편집
		 */
		edit : function(){
			var that = this;
			selfSysObj.addDialog.open("EDIT",that.SYS_ID);
			that.close();
		},
		
		/**
		 * 삭제
		 */
		remove : function(){
			var that = this;
			var url = restUtils.getResUrl() + that.SYS_ID + "/remove.do";
			
			$.post(url).done(function(result){
				alert("사용자 시스템을 삭제하였습니다.");
				$(location).attr('href', restUtils.getResUrl() + "list.do");
			}).fail(function(){
				alert("사용자 시스템 삭제에 실패하였습니다.");
			});
		},
		
		selfMenuList : function(){
			var that = this;
			var url = restUtils.getResUrl() + that.SYS_ID + "/selectOne.do";
			var menuSelector = that.selector + " .div_selfSys_list .listBox ul";
			$.post(url).done(function(result){
				var htmlStr = "";
				var sysNm = result.sys.sysAlias;
				var sysDc = result.sys.sysDc;
				
				$("#selfSys_view_sysNm",that.selector).val(sysNm);
				$("#selfSys_view_sysDc",that.selector).val(sysDc);
				
				$.each(result.menuList,function(key,val){
					var menuId = val.menuNo;
					var menuNm = val.menuNm;
					var upperMenuId = val.upperMenuId;
					if(val.upperMenuId == -1){
						htmlStr += that.makeMenuHtml(that.SYS_ID, menuId, menuNm, upperMenuId,true);
					}
				});
				
				$(menuSelector).append(htmlStr);
				
				$.each(result.menuList,function(key,val){
					var menuId = val.menuNo;
					var menuNm = val.menuNm;
					var upperMenuId = val.upperMenuId;
					if(val.upperMenuId != -1){
						var subMenuHtmlStr = that.makeMenuHtml(that.SYS_ID, menuId, menuNm, upperMenuId,false);
						$("[data-menuId^='" + upperMenuId + "'] ul", menuSelector).append(subMenuHtmlStr);
					}
				});
				
			}).fail(function(){
				alert("사용자 메뉴 로드에 실패하였습니다.");
			});
		},
		
		makeMenuHtml : function(sysId, menuId, menuNm, upperMenuId, group){
			var htmlStr = "";
			htmlStr += "<li class='li_menu'";
			htmlStr += " data-sysId='" + sysId + "'";
			htmlStr += " data-menuId='" + menuId + "'";
			htmlStr += " data-menuNm='" + menuNm + "'";
			htmlStr += " data-upperMenuId='" + upperMenuId + "'";
			htmlStr += ">";
			
			htmlStr += "<span>";
			htmlStr += menuNm;
			htmlStr += "</span>";
			//그룹이 true일 경우만 
			if(group){
				htmlStr += "<ul class='pa_l_10'></ul>";
			}
			
			htmlStr += "</li>";
			return htmlStr;
		},
		
		/**
		 * 닫기
		 */
		close : function(){
			var that = this;
			that.clear();
			$(that.selector).dialog("close");
		},
		
		clear : function(){
			var that = this;
			$(".div_selfSys_list .listBox > ul",that.selector).empty();
			$("#selfSys_view_sysNm",that.selector).val("");
			$("#selfSys_view_sysDc",that.selector).val("");
			that.SYS_ID = null;
		}
};

//나의시스템 등록 팝업 객체
selfSysObj.addDialog = {
		
		selector : "#div_selfSys_add",
		
		menuList : null,
		
		SYS_ID : null,
		
		TYPE : null,
		
		init : function(type,sysId){
			var that = this;
			that.clear();
			that.TYPE = type;
			that.SYS_ID = sysId;
			that.initUi();
			if(type == "EDIT"){
				var sysAlias = $("#selfSys_view_sysNm","#div_selfSys_view").val();
				var sysDc = $("#selfSys_view_sysDc","#div_selfSys_view").val();
				$("#selfSys_sysNm", that.selector).val(sysAlias);
				$("#selfSys_sysDc", that.selector).val(sysDc);
			}
		},
		
		/**
		 * UI 초기화
		 */
		initUi : function() {
			var that = this;
			
			$(that.selector).dialog({
				autoOpen: false,
		     	height: 600,
		      	width: 700,
		      	buttons: that.initButtons(),
		      	close: function() {
		      		that.close();
		      	}
		    });
		},
		
		initButtons : function(){
			var that = this;
			var buttons = null;
			if(that.TYPE == "EDIT"){
				buttons = {
					"편집" : function() {
			    		that.edit();
			    	},
			        "닫기" : function() {
			        	that.close();
			        }	
				};
			}else{
				buttons = {
					"등록" : function() {
			    		that.add();
			    	},
			        "닫기" : function() {
			        	that.close();
			        }	
				};
			}
			
			return buttons;
		},
		
		/**
		 * 이벤트 등록
		 */
		bindEvents : function() {
			var that = this;
			$(".div_basic_list",that.selector).on("change",".select_basic_sys",function(){
				that.changeSys($(this).val());
			});
			
			$(".div_basic_list",that.selector).on("change",".select_category",function(){
				that.changeCategory($(".select_basic_sys",that.selector).val(), $(this).val());
			});
			
			$(".allChecked",that.selector).on("click",function(){
				that.chkBoxAllChecked();
			});
			
			$(".allRemove",that.selector).on("click",function(){
				that.chkBoxAllRemoved();
			});
			
			$(that.selector).on("click",".menuAdd",function(evt){
				if(that.sysMenuCheck() < 1 ){
					alert("메뉴가 한개이상 선택이 되어야 합니다.");
				}else if(that.selfMenuGroupCheck() < 1){
					console.log("that.selfMenuGroupCheck() : " + that.selfMenuGroupCheck());
					alert("메뉴그룹이 한개가 선택이 되어야 합니다.");
				}else if(that.selfMenuGroupCheck() > 1){
					console.log("that.selfMenuGroupCheck() : " + that.selfMenuGroupCheck());
					alert("메뉴그룹이 한개만 선택이 되어야 합니다.");
				}else{
					that.menuAdd();
				}
			});
			
			$(".menuAllAdd",that.selector).on("click",function(evt){
				if(that.selfMenuGroupCheck() < 1){
					alert("메뉴그룹이 한개가 선택이 되어야 합니다.");
				}else if(that.selfMenuGroupCheck() > 1){
					alert("메뉴그룹이 한개만 선택이 되어야 합니다.");
				}else{
					that.menuAllAdd();
				}
			});
			
			$(".menuRemove",that.selector).on("click",function(evt){
				if(that.selfMenuCheck() > 0 || that.selfMenuGroupCheck() > 0){
					that.menuRemove();
				}else{
					alert("나의 메뉴가 한개이상 선택이 되거나 나의 메뉴그룹이 한개이상 선택이 되어야 합니다.");
				}
			});
			
			$(".menuAllRemove",that.selector).on("click",function(evt){
				that.menuAllRemove();
			});
			
			$(".menuGroupAdd",that.selector).on("click",function(){
				selfSysObj.menuGroupAddDialog.open();
			});
			
			$(".menuGroupRemove",that.selector).on("click",function(){
				selfSysObj.menuGroupRemove.remove();
			});
		},
		
		/**
		 * 열기
		 */
		open : function(type,sysId) {
			var that = this;
			that.init(type,sysId);
			that.allList();
			$(that.selector).dialog("open");
			that.bindEvents();
		},
		
		/**
		 * 추가하기
		 */
		add : function() {
			var that = this;
			var url = restUtils.getResUrl() + "add.do";
			that.save(url);
		},

		/**
		 * 편집 저장하기
		 */
		edit : function(){
			var that = this;
			var url = restUtils.getResUrl() + that.SYS_ID + "/modify.do";
			that.save(url);
		},
		
		/**
		 * 나의시스템 저장
		 */
		save : function(url){
			var that = this;
			var param = {};
			
			param.sysAlias = $("#selfSys_sysNm", that.selector).val();
			param.sysDc = $("#selfSys_sysDc", that.selector).val();
			param.kwsUserMenu = that.addUserMenuList();

			if(param.sysAlias == "" || param.sysAlias == null){
				alert("시스템 명은 반드시 필요합니다.");
				$("#selfSys_sysNm",that.selector).focus();
				return false;
			}else if(param.sysDc == "" || param.sysDc == null){
				alert("시스템 설명이 반드시 필요합니다.");
				$("#selfSys_sysDc",that.selector).focus();
				return false;
			}else if(param.kwsUserMenu.length < 2){
				//기본적으로 메뉴 그룹이랑 메뉴가 있어야 하므로 최소 2개가 있어야 함.
				alert("등록할 메뉴가 있어야 합니다.");
				return false;
			}
			
			var data = JSON.stringify(param);
			var paramObj = {};
			paramObj.content = data;
			
			$.post(url,paramObj).done(function(result){
				alert("저장 되었습니다.");
				$(location).attr('href', restUtils.getResUrl() + "list.do");
			}).fail(function(){
				alert("사용자 시스템 저장에 실패했습니다.");
			});
		},
		
		addUserMenuList : function(){
			var that = this;
			var menuList = new Array();
			
			var menuGroupList = $(".div_selfSys_list .listBox .menuGroup" , that.selector );
			
			// menuGroup 추가
			$.each(menuGroupList,function(key,menuObj){
				var param = new Object();
				param.menuNm = $(this).attr("data-menuNm");
				param.upperMenuId = -1;
				param.menuOrdr = key + 1;
				param.orginlMenuId = -1;
				menuList.push(param);
				
				$.each($(".li_menu",this),function(k,obj){
					var menuParam = {};
					menuParam.menuNm = $(this).attr("data-menuNm");
					menuParam.upperMenuId = 0;
					menuParam.menuOrdr = k + 1;
					menuParam.orginlMenuId = Number($(this).attr("data-menuId"));
					menuList.push(menuParam);
				});
			});
			
			return menuList;
		},
		
		selfMenuList : function(){
			var that = this;
			var url = restUtils.getResUrl() + that.SYS_ID + "/selectOne.do";
			var menuSelector = that.selector + " .div_selfSys_list .listBox > ul";
			$.post(url).done(function(result){
				var htmlStr = "";
				var sysNm = result.sys.sysAlias;
				var sysDc = result.sys.sysDc;
				
				$("#selfSys_view_sysNm",that.selector).val(sysNm);
				$("#selfSys_view_sysDc",that.selector).val(sysDc);
				
				$.each(result.menuList,function(key,val){
					var menuId = val.menuNo;
					var menuNm = val.menuNm;
//					var orginlMenuId = val.orginlMenuId;
					var upperMenuId = val.upperMenuId;
					if(val.upperMenuId == -1){
						htmlStr += that.makeEditGroupHtml(that.SYS_ID, menuId, menuNm, upperMenuId);
					}
				});
				
				$(menuSelector).append(htmlStr);
				
				$.each(result.menuList,function(key,val){
					var menuNm = val.menuNm;
					var orginlMenuId = val.orginlMenuId + "";
					var upperMenuId = val.upperMenuId;
					if(val.upperMenuId != -1){
						var menuSysId = orginlMenuId.substring(0,orginlMenuId.length -4);
						var subMenuHtmlStr = that.makeEditMenuHtml(menuSysId, orginlMenuId, menuNm, upperMenuId);
						$("[data-menuId^='" + upperMenuId + "'] ul", menuSelector).append(subMenuHtmlStr);
						that.editMenuList(menuSysId,orginlMenuId,"USER");
					}
				});
				
				var changeSysId = $(".select_basic_sys",that.selector).val();
				var changeMenuId = $(".select_category",that.selector).val();
				if($(".select_basic_sys",that.selector).val() == "all"){
					that.changeSys(changeSysId);
				}else{
					that.changeCategory(changeSysId,changeMenuId);
				}
				
			}).fail(function(){
				alert("사용자 메뉴 로드에 실패하였습니다.");
			});
		},
		
		remove : function(){
			var url = restUtils.getResUrl() + "add.do";
			var param = {};
			
			param.sysId = "";
			$.post(url,param).done(function(result){
				alert("삭제 되었습니다. : " + result.result);
				$(location).attr('href', restUtils.getResUrl() + "list.do");
			}).fail(function(){
				alert("사용자 시스템 저장에 실패했습니다.");
			});
		},
		
		clear : function(){
			var that = this;
			that.TYPE = null;
			that.menuList = null;
			
			$("#selfSys_sysNm",that.selector).val("");
			$("#selfSys_sysDc",that.selector).val("");
			
			$(".div_basic_menu ul",that.selector).empty();
			$(".listBox ul",that.selector).empty();
			
			var sysVal = $(".select_basic_sys option:eq(0)",that.selector).val();
			
			$(".select_basic_sys",that.selector).val(sysVal);
			that.changeSys(sysVal);
		},
		
		makeMenuList : function(data){
			var that = this;
			that.menuList = new Object();
			
			if(data.sysList.length > 0){
				$.each(data.sysList,function(k,v){
					var menuSys = "sys" + v.sysId;
					that.menuList[menuSys] = new Array();
				});
			}
			
			if(data.menuList.length > 0){
				$.each(data.menuList,function(k,v){
					var menuSys = "sys" + v.sysId;
					var tmpObj = {
							sysId : v.sysId,
							upperMenuId : v.upperMenuId,
							menuId : v.menuId,
							menuNm : v.menuNm,
							fnctId : v.fnctId,
							url : v.url,
							useAt : v.useAt,
							menuOrdr : v.menuOrdr
					};
					
					that.menuList[menuSys].push(tmpObj);
				});
			}
		},
		
		allList : function(){
			var that = this;
			var url = restUtils.getResUrl() + "allList.do";
			
			$.post(url).done(function(result){
				that.menuList = new Object();
				if(result.sysList.length > 0){
					$.each(result.sysList,function(k,v){
						if(v.sysTy == "SYSTEM"){
							var menuSys = "sys" + v.sysId;
							that.menuList[menuSys] = new Array();
						}
					});
				}
				
				if(result.menuList.length > 0){
					$.each(result.menuList,function(k,v){
						var menuSys = "sys" + v.sysId;
						//menuAttr : 메뉴속성 - 화면에서 어디에 표시되어있는지를 나타냄 ( SYSTEM, USER ) 시스템 메뉴목록 , 사용자 메뉴목록 
						var tmpObj = {
								sysId : v.sysId,
								upperMenuId : v.upperMenuId,
								menuId : v.menuId,
								menuNm : v.menuNm,
								fnctId : v.fnctId,
								url : v.url,
								useAt : v.useAt,
								menuOrdr : v.menuOrdr,
								menuAttr : "SYSTEM"
						};
						
						that.menuList[menuSys].push(tmpObj);
					});
				}
				
				that.appendMenuList($(".select_basic_sys",that.selector).val());
				
				if(that.TYPE == "EDIT"){
					that.selfMenuList();
				}
			}).fail(function() {
				alert("데이터 로딩에 실패했습니다.");
			});
		},
		
		chkBoxAllChecked : function(){
			var that = this;
			$(".div_basic_menu ul li input:checkbox",that.selector).prop("checked",true);
		},
		
		chkBoxAllRemoved : function(){
			var that = this;
			$(".div_basic_menu ul li input:checkbox",that.selector).prop("checked",false);
		},
		
		changeSys : function(sysId){
			var that = this;
			
			//전체 해제 및 display none
			$(".select_category option",that.selector).css("display","none");
			$(".select_category option",that.selector).attr("selected",false);
			
			//전체 선태 및 display inline-block
			$(".select_category option[value='all']",that.selector).attr("selected",true);
			$(".select_category option[value='all']",that.selector).css("display","inline-block");
			
			// 선택된 sysId 값으로 display inline-block
			$(".select_category option[data-sysId^='" + sysId + "']",that.selector).css("display","inline-block");

			that.appendMenuList(sysId);
			return false;
		},
		
		//메뉴 카테고리가 변경시 이벤트 처리
		changeCategory : function(sysId,menuId){
			var that = this;
			that.appendMenu(sysId,menuId);
		},
		
		//시스템 메뉴목록에  menuId로 메뉴추가 추가
		appendMenu : function(sysId,menuId){
			var that = this;
			that.removeList();
			if(that.menuList == null){
				return false;
			}
			
			var data = that.menuList["sys"+ sysId];
			var htmlStr = "";
			$.each(data,function(k,v){
				if((v.upperMenuId == menuId || menuId == "all") && v.menuAttr == "SYSTEM"){
					 htmlStr += that.makeMenuHtml(v.sysId,v.menuId,v.menuNm);
				}
			});
			
			$(".div_basic_menu ul",that.selector).html(htmlStr);
			return false;
		},
		
		//시스템 메뉴목록에  메뉴 전체 추가
		appendMenuList : function(sysId){
			var that = this;
			that.removeList();
			if(that.menuList == null){
				return false;
			}
			
			var data = that.menuList["sys"+ sysId];
			var htmlStr = "";
			
			$.each(data,function(k,v){
				if(v.menuAttr == "SYSTEM"){
					htmlStr += that.makeMenuHtml(v.sysId,v.menuId,v.menuNm);
				}
			});
			
			$(".div_basic_menu ul",that.selector).html(htmlStr);
		},
		
		removeList : function(){
			var that = this;
			$(".div_basic_menu ul",that.selector).empty();
		},
		
		menuListAllSystem : function(){
			var that = this;
			$.each(that.menuList,function(k,v){
				$.each(v,function(key,val){
					if(val.menuAttr != undefined){
						val.menuAttr = "SYSTEM";
					}
				});
			});
		},
		
		editMenuList : function(sysId,menuId,attr){
			var that = this;
			var data = that.menuList["sys"+ sysId];
			
			$.grep(data,function(obj){ 
				if(obj.menuId == menuId){
					obj.menuAttr = attr;
				}
			});
		},
		
		makeMenuHtml : function(sysId, menuId, menuNm){
			var htmlStr = "";
			htmlStr += "<li class='li_menu'";
			htmlStr += " data-sysId='" + sysId + "'";
			htmlStr += " data-menuId='" + menuId + "'";
			htmlStr += " data-menuNm='" + menuNm + "'";
			htmlStr += ">";
			
			htmlStr += "<input type='checkbox'";
			htmlStr += " data-sysId='" + sysId + "'";
			htmlStr += " data-menuId='" + menuId + "'";
			htmlStr += " data-menuNm='" + menuNm + "'";
			htmlStr += ">";
			
			htmlStr += "<span>";
			htmlStr += menuNm;
			htmlStr += "</span>";
			htmlStr += "</li>";
			return htmlStr;
		},
		
		makeEditMenuHtml : function(sysId, orginlMenuId, menuNm){
			var htmlStr = "";
			htmlStr += "<li class='li_menu'";
			htmlStr += " data-sysId='" + sysId + "'";
			htmlStr += " data-menuId='" + orginlMenuId + "'";
			htmlStr += " data-menuNm='" + menuNm + "'";
			htmlStr += ">";
			
			htmlStr += "<input type='checkbox'";
			htmlStr += " data-sysId='" + sysId + "'";
			htmlStr += " data-menuId='" + orginlMenuId + "'";
			htmlStr += " data-menuNm='" + menuNm + "'";
			htmlStr += ">";
			
			htmlStr += "<span>";
			htmlStr += menuNm;
			htmlStr += "</span>";
			htmlStr += "</li>";
			return htmlStr;
		},
		
		makeEditGroupHtml : function(sysId, menuId, menuNm){
			var htmlStr = "";
			htmlStr += "<li";
			htmlStr += " id='menuGroup_" + menuNm + "'";
			htmlStr += " class='menuGroup'";
			htmlStr += " data-menuId='" + menuId + "'";
			htmlStr += " data-menuNm='" + menuNm + "'";
			htmlStr += ">";
			
			htmlStr += "<input type='checkbox'";
			htmlStr += " data-menuNm='" + menuNm + "'";
			htmlStr += ">";
			
			htmlStr += "<span>";
			htmlStr += menuNm;
			htmlStr += "</span>";
			htmlStr += "<ul class='pa_l_10'></ul>";
			htmlStr += "</li>";
			return htmlStr;
		},
		
		menuAdd : function(){
			var that = this;
			var htmlStr = "";
			var menuGroupSelector = that.menuGroupSelector();
			var sysId = $(".select_basic_sys",that.selector).val();
			
			$(".div_basic_menu ul input:checked",that.selector).each(function(){
				var menuId = $(this).attr("data-menuId");
				var menuNm = $(this).attr("data-menuNm");
				htmlStr += that.makeMenuHtml(sysId,menuId,menuNm);
				$(this).parent().remove();
				that.editMenuList(sysId,menuId,"USER");
			});
			
			var ulCnt = $("ul","#" + menuGroupSelector,that.selector).length;
			if(ulCnt == 0){
				$("#" + menuGroupSelector,that.selector).append("<ul class='pa_l_10'></ul>");
			}
			
			$("#" + menuGroupSelector + " ul",that.selector).append(htmlStr);
		},
		
		menuGroupSelector : function(){
			return $(".div_selfSys_list input:checked").parent().attr("id");
		},
		
		menuAllAdd : function(){
			var that = this;
			var htmlStr = "";
			var menuGroupSelector = that.menuGroupSelector();
			var sysId = $(".select_basic_sys",that.selector).val();
			$(".div_basic_menu input",that.selector).each(function(){
				var menuId = $(this).attr("data-menuId");
				var menuNm = $(this).attr("data-menuNm");
				
				htmlStr += that.makeMenuHtml(sysId,menuId,menuNm);
				$(this).parent().remove();
				that.editMenuList(sysId,menuId,"USER");
			});
			
			var ulCnt = $("ul","#" + menuGroupSelector,that.selector).length;
			if(ulCnt == 0){
				$("#" + menuGroupSelector,that.selector).append("<ul class='pa_l_10'></ul>");
			}
			
			$("#" + menuGroupSelector + " ul",that.selector).append(htmlStr);
		},
		
		menuRemove : function(){
			var that = this;
			$(".div_selfSys_list .listBox .menuGroup li > input:checked",that.selector).each(function(){
				var menuId = $(this).attr("data-menuId");
				var sysId = $(this).attr("data-sysId");
				
				$(this).parent().remove();
				that.editMenuList(sysId,menuId,"SYSTEM");
				
				var changeSysId = $(".select_basic_sys",that.selector).val();
				var changeMenuId = $(".select_category",that.selector).val();
				if($(".select_basic_sys",that.selector).val() == "all"){
					that.changeSys(changeSysId);
				}else{
					that.changeCategory(changeSysId,changeMenuId);
				}
				
			});
		},
		
		menuAllRemove : function(){
			var that = this;
			
			var changeSysId = $(".select_basic_sys",that.selector).val();
			var changeMenuId = $(".select_category",that.selector).val();
			
			that.menuListAllSystem();
			$(".div_selfSys_list .listBox ul",that.selector).empty();
			if($(".select_basic_sys",that.selector).val() == "all"){
				that.changeSys(changeSysId);
			}else{
				that.changeCategory(changeSysId,changeMenuId);
			}
		},
		
		/**
		 * 닫기
		 */
		close : function() {
			var that = this;
			that.clear();
			
			//메뉴그룹추가 팝업 닫기
			selfSysObj.menuGroupAddDialog.close();
			$(that.selector).dialog("close");
		},
		
		//sysMenu 리스트에서 체크된 갯수
		sysMenuCheck : function(){
			var that = this;
			var chkCnt = $(".div_basic_menu li > input:checkbox:checked",that.selector).length;
			return chkCnt;
		},
		
		//selfMenu 리스트에서 체크된 갯수
		selfMenuCheck : function(){
			var chkCnt = $(".div_selfSys_list .listBox .menuGroup li > input:checkbox:checked").length;
			return chkCnt;
		},
		
		selfMenuGroupCheck : function(){
			var that = this;
			var chkCnt = $(".div_selfSys_list .listBox > ul > li > input:checkbox:checked",that.selector).length;
			return chkCnt;
		}
		
};

selfSysObj.menuGroupAddDialog = {
		
		selector : "#div_menuGroup_add",
		
		init : function(){
			var that = this;
			
			that.clear();
			that.initUi();
		},
		
		/**
		 * UI 초기화
		 */
		initUi : function() {
			var that = this;
			
			$(that.selector).dialog({
				autoOpen: false,
		     	height: 170,
		      	width: 400,
		      	buttons: {
			    	"메뉴그룹추가" : function() {
			    		that.add();
			    	},
			        "닫기" : function() {
			        	that.close();
			        }
				},
		      	close: function() {
		      		that.close();
		      	}
		    });
		},
		
		add : function(){
			var that = this;
			var menuGroupNm = $(".txt_menuGroupNm",that.selector ).val();
			var htmlStr = "";
			
			$(".div_selfSys_list .listBox ul input:checked", selfSysObj.addDialog.selector).prop("checked",false);
			
			htmlStr += "<li";
			htmlStr += " id='menuGroup_" + menuGroupNm + "' class='menuGroup'";
			htmlStr += " data-menuNm='" + menuGroupNm + "'";
			htmlStr += ">";
			
			htmlStr += "<input type='checkbox' checked='checked'";
			htmlStr += " data-menuNm='" + menuGroupNm + "'";
			htmlStr += ">";
			
			htmlStr += "<span>";
			htmlStr += menuGroupNm;
			htmlStr += "</span>";
			htmlStr += "</li>";
			
			$(".div_selfSys_list .listBox > ul", selfSysObj.addDialog.selector).append(htmlStr);
			that.close();
		},
		
		clear : function(){
			var that = this;
			$(".txt_menuGroupNm", that.selector).val("");
		},
		
		open : function(){
			var that = this;
			$(that.selector).dialog("open");
		},
		
		/**
		 * 닫기
		 */
		close : function(){
			var that = this;
			that.clear();
			$(that.selector).dialog("close");
		}
};

selfSysObj.menuGroupRemove = {
	remove : function(){
		var menuGroupChecked = $(".menuGroup input:checked", selfSysObj.addDialog.selector);
		$.each(menuGroupChecked,function(obj){
			var tmpId = "#" + $(this).parent().attr("id");
			var menuGroupObj = $("li",tmpId);
			$.each(menuGroupObj,function(menuObj){
				var menuId = $(this).attr("data-menuId");
				var sysId = $(this).attr("data-sysId");
				selfSysObj.addDialog.editMenuList(sysId,menuId,"SYSTEM");
			});
			
			$(tmpId).remove();
		});
		
		var changeSysId = $(".select_basic_sys",selfSysObj.addDialog.selector).val();
		var changeMenuId = $(".select_category",selfSysObj.addDialog.selector).val();
		if($(".select_basic_sys",selfSysObj.addDialog.selector).val() == "all"){
			selfSysObj.addDialog.changeSys(changeSysId);
		}else{
			selfSysObj.addDialog.changeCategory(changeSysId,changeMenuId);
		}
	}	
};