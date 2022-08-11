/**
 * 공간 클릭 검색 결과 선택
 */
windowObj.spatialSelectObj = {
		/**
		 * 제목
		 * @type {String}
		 */
		TITLE : "선택",
		
		/**
		 * 클래스 명
		 * @type {String}
		 */
		CLASS_NAME : "SpatialSelect",
		
		/**
		 * 선택완료 시 실행될 함수
		 */
		onSubmit : null,
		
		/**
		 * 선택자
		 * @type {String}
		 */
		selector : null,
		
		/**
		 * 검색 결과
		 * @type {Array.Array<Object>}
		 */
		rows : null,
		
		/**
		 * 열기
		 * @param {String} layerAliasName 검색목록
		 * @param {Array.Array<Object>} rows 검색목록
		 * @param {Object} options 옵션
		 */
		open : function(title, rows, options) {
			var that = this;
			
			that.TITLE = title + ' 선택';
			that.rows = rows;
			
			var url = CONTEXT_PATH + '/window/spatialSelect/page.do';
			var windowOptions = {
					width : 150,
					heigth : 400,
					top : 120,
					modal : true,
					onClose : function() {
						that.close();
					}
			};
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
				that.initUi();
				that.loadData();
				that.bindEvents();
				//현재 객체에 전달되는 갑 적용(onSubmit)
				if (options) {
					$.extend(that, options);
				}
			});
			that.selector = '#' + id;
		},
		
		initUi : function(rows) {
			var that = this;
			
			$('.div_south', that.selector).css({
				'height' : '30px',
				'text-align' : 'center',
				'padding' : '3px'
			});
			
			$('.feature_list', that.selector).datagrid({
				columns : [[
				    {field: MAP.GEOMETRY_ATTR_NAME, hidden : true},
					{field:'ftrIdn', title:'관리번호', align : 'center', width:135}
				 ]],
				 singleSelect : true,
				 scrollbarsize : 0,
				 onSelect : function(rowIndex, rowData) {
						that.showFeature();
					}
			});
			
			
		},
		
		loadData : function() {
			var that = this;
			
			$('.feature_list', that.selector).datagrid('loadData', that.rows);
		},
		
		bindEvents : function() {
			var that = this;
			
			// 선택 완료
			$(".a_completion_choice", that.selector).click(function() {
				that.completionChoice();
				return false;
			});
		},
		
		/**
		 * 선택 완료
		 */
		completionChoice : function() {
			var that = this;
			
			that.removeFeatures();
			
			if(that.onSubmit) {
				var data = $(".feature_list", that.selector).datagrid(
				"getSelections");
				var row = windowObj.spatialSelectObj.getSelectedRows(data[0]['ftrIdn']);
				that.onSubmit(row);
			}
			that.close();
		},
		
		getSelectedRows : function(ftrIdn) {
			var that = this;
			
			var selectedRow = null;
			for (var i = 0; i < that.rows.length; i++) {
				if (that.rows[i]['ftrIdn'] == ftrIdn) {
					selectedRow = that.rows[i];
					break;
				}
			}
			
			return selectedRow;
		},
		
		close : function() {
			var that = this;
			
			that.removeFeatures();
			windowObj.removeWindow(that.selector);
			that.selector = null;
			that.rows = null;
			that.onSubmit = null;
		},
		
		/**
		 * 도형 표시
		 */
		showFeature : function() {
			var that = this;
			that.removeFeatures();
			
			var format = new ol.format.WKT();
			var data = $(".feature_list", that.selector).datagrid(
					"getSelections");
			var geom = data[0][MAP.GEOMETRY_ATTR_NAME];
			var feature = null;
			if (geom) {
				feature = format.readFeature(geom);
			}
			if (feature) {
				highlightObj.showRedFeatures(that.CLASS_NAME, [feature],
						false, {
							projection : ol.proj.get(MAP.PROJECTION)
						});
			}
		},
		
		/**
		 * 도형 표시 제거
		 */
		removeFeatures : function() {
			var that = this;
			
			highlightObj.removeFeatures(that.CLASS_NAME);
		}
};