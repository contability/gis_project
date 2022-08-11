/*
 * 강릉시 도로표지판 관리
 * @type {Object}
 */
var roadSgnlbrdManageObj = {
		
		c : null,
		
		MENU_ID : null,
		
		MENU_NM : null,
		
		init : function(menuId, menuNm, url) {
			var that = this;
			
			that.DATA_ID = that.parseDataId(url);
			that.MENU_ID = menuId;
			that.MENU_NM = menuNm;
		},
		
		parseDataId : function(url) {
			var urlElemens = url.split('/');
			return urlElemens[urlElemens.length - 2];
		},
		
		getMenuId : function() {
			var that = this;
			return that.MENU_ID;
		},
		
		getMenuNm : function() {
			var that = this;
			return that.MENU_NM;
		},
		
		getDataId : function() {
			var that = this;
			return that.DATA_ID;
		}
};

roadSgnlbrdManageObj.search = {
		
		TITLE : '도로표지판 검색',
		
		CLASS_NAME : 'RoadSgnlbrdSearch',
		
		selector : null,
		
		isCreated : null,
		
		initUi : function() {
			var that = this;
			
			// easyUi설정
			$('#listRoadSgnlbrd .easyui-textbox', that.selector).textbox();
		},
		
		open : function() {
			var that = this;
			
			if (that.selector) {
				that.close();
			}
			
			var url = CONTEXT_PATH + '/roadSgnlbrd/searchRoadSgnlbrdPage.do';
			var windowOptions = {
				width : 655,
				height : 183,
				top : 120,
				left : 330,
				isClose : function() {
					that.close();
				}
			};
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
				if (!that.isCreated) {
					that.initUi();
					that.bindEvents();
					that.isCreated = true;
				}
			});
			
			that.selector = '#' + id;
		},
		
		bindEvents : function() {
			var that = this;
			
			//검색방법 변경
			$('input[name=listRoadSgnlbrd_searchKind]', that.selector).click(function() {
				var value = $(this).val();
				if(value == "SGNAL_CN") {
					$('.signalDetails', that.selector).textbox('enable');
				}
				else {
					$('.signalDetails', that.selector).textbox({
						disabled : true
					});
					$('.signalDetails', that.selector).textbox('clear');
				}
			});
			
			//검색
			$(".a_search_roadSgnlbrd", that.selector).on("click", function() {
				var searchKind = $('input[name=listRoadSgnlbrd_searchKind]:checked', that.selector).val();
				if (searchKind == "SGNAL_CN") {
					that.searchSummaries();
				} else {
					that.searchFacilities();
				}
				
				return false;
			});
		},
		
		searchSummaries : function() {
			var that = this;

			var searchConditions = $('.signalDetails', that.selector).textbox('getValue').split(',');
			var cql = that.getSqlWhereConstruction(searchConditions);
			
			var dataId = roadSgnlbrdManageObj.getDataId();
			var filter = {
				filterType : 'CQL',
				cql : cql
			};
			spatialSearchUtils.searchSummaries(that.TITLE, dataId, filter, function(rows) {
				if(rows && rows.length > 0) {
					resultObj.close();
					resultObj.addRows(rows);
					that.close();
				}
			});
		},
		
		getSqlWhereConstruction : function(searchConditions) {
			if (searchConditions.length == 0) {
				return "1 = 1";
			} else {
				var SqlWhereConstruction = "";
				var searchCondition = "";
				var conditionField = "T12_DRC||','||T13_DRC||','||T15_DRC||','||T17_DRC||','||T18_DRC||','||T19_DRC||','||T21_DRC||','||T23_DRC";
				for (var i = 0; i < searchConditions.length; i++) {
					searchCondition = searchConditions[i].replace(/^\s*|\s*$/g, "");
					if (searchCondition != "") {
						SqlWhereConstruction += conditionField + " LIKE ('%" + searchCondition + "%') OR ";  
					}
				}
				SqlWhereConstruction = SqlWhereConstruction.substring(0, SqlWhereConstruction.length - 4);
				return SqlWhereConstruction;
			}
		},
		
		searchFacilities : function() {
			var that = this;
			
			var menuId = roadSgnlbrdManageObj.getMenuId();
			var menuNm = roadSgnlbrdManageObj.getMenuNm();
			var url = CONTEXT_PATH + '/rest/spatial/' + roadSgnlbrdManageObj.getDataId() + '/listAllSummary.do';
			
			menuObj.jobObj.searchFacilities(menuId, menuNm, url);
			
			that.close();
		},
		
		close : function() {
			var that = this;
			
			windowObj.removeWindow(that.selector);
			that.selector = null;
			that.isCreated = null;
		}
};