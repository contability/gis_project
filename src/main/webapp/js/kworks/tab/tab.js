/**
 * 탭 객체
 * @type {Object}
 * 2021.11.19	정신형
 */
var tabObj = {
	
	CLASS_NAME : 'kworks_tab',
	
	selector : '#map_container',
	
	index : 0,
	
	initMainTab : function(){
		var conHeight = $('#container').height();
		var conWidth = $('#container').width();
		
		var menuWidth = $('#div_menu').width();
		
		var mapConWidth = conWidth - menuWidth;
		
		$('#map_container').tabs({
			width : mapConWidth,
			height : conHeight,
			border : false,
		    onSelect : function(title, index){
		    	if(title.indexOf('배경지도') > -1 && index == 0){
		    		mainObj.resizeWindowHandler();
		    	}
			}
		});
		
		
		mainObj.addResizeHandler('mainTab', function(){
			var conHeight = $('#container').height();
			var conWidth = $('#container').width();
			
			var menuWidth = $('#div_menu').width();
			
			var mapConWidth = conWidth - menuWidth;
			
			var style = {};
			
			mapConWidth = conWidth - menuWidth;
			
			style = {
					width : mapConWidth,
					height : conHeight
				};
			
			$('#map_container').css('left', menuWidth);
			
			$('#map_container').tabs('resize',style);
		});
	},
	
	// 탭 생성
	createTab : function(dataId, alias, row, url, callback, options){
		var that = this;
		
		var pkVal = that.getPkVal(dataId, row);
		var title = '';
		
		if(!alias){
			title = 'none_' + pkVal;
		}else{
			title = alias + '_' + pkVal;
		}
		
		var tabId = dataId + '_' + pkVal;
		
		if($(that.selector).tabs('exists', title)){
			$(that.selector).tabs('select', title);
		}else{
			$(that.selector).tabs('add',{
				title : title,
				closable : true,
				href : url,
				id : tabId,
				onLoad : function(){	
					if(callback){
						var selector = '#' + tabId;
						callback(row, selector);
					}
				}
			});
		}
	},
	
	getPkVal : function(dataId, row){
		var data = dataObj.getDataByDataId(dataId, true);
		var dataField = data.kwsDataFields;
		var pkVal = null;
		
		for(var i = 0; i < dataField.length; i++){
			if(dataField[i].pkAt.indexOf('Y') > -1){
				var pkId = dataField[i].fieldId; 
				pkVal = row[camelcaseUtils.underbarToCamelcase(pkId)];
				break;
			}
		}
		
		return pkVal;
	},
	
	getId : function(){
		var that = this;
		
		return $(that.selector).tabs('getSelected').panel('options').id;
	}
};