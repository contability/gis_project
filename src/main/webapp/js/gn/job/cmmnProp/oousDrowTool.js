cmmnPropObj.oousToolObj = {
		
		/**
		 * 선택자
		 * @type {String}
		 */
		selector : null,
		
		/**
		 * 제목
		 * @type {String}
		 */
		TITLE : "공간 추가",
		
		/**
		 * 클래스명
		 * @type {String}
		 */
		CLASS_NAME : "ouus_Geom",
		
		/**
		 * 레이어명
		 * @type {String}
		 */
		layerName : null,

		/**
		 * 별칭
		 * @type {String}
		 */
		aliasName : null,
		
		/**
		 * 그리기 종류 (Point, LineString, Polygon)
		 * @type {String} 
		 */
		drawType : "Polygon",
		
		/**
		 * 벡터 소스
		 * @type {ol.source.Vector}
		 */
		source : null,
		
		/**
		 * 등록 또는 선택 중인 도형
		 * @type {ol.Feature}
		 */
		feature : null,
		
		/**
		 * 편집저장 시 실행될 추가적 이벤트 함수
		 */
		onSave : null,
		
		onClose : null,
		
		ocpIdn : null,
		
		prtIdn : null,
		
		init : function(map) {
			var that = this;
			
			that.initGis();
			
		},
		
		initGis : function() {
			var that = this;
			
			// 선택 레이어 생성 및 등록
			that.source = new ol.source.Vector();
			var layer = new ol.layer.Vector({
				id : "editLayer",
				source : that.source
			});
			cmmnOccpObj.view.getMap().addLayer(layer);
			that.source.on("addfeature", that.addFeature, that);
			
			// 점, 선, 면 그리기 인터렉션 생성 및 등록
			var types = ["Polygon"];
			for(var i in types) {
				var options = {};
				options["type"] = types[i];
				options["source"] = that.source;
				var interaction = new ol.interaction.Draw(options);
				interaction.set("id", "editLoan"+types[i]);
				interaction.set("name", "editLoan"+types[i]);
				cmmnOccpObj.view.getMap().addInteraction(interaction);
				interaction.setActive(false);
				interaction.on("drawend", that.drawend, that);
			}
			
			// 도형 편집 인터렉션
			var modify = new kworks.interaction.Modify({
				features : new ol.Collection()
			});
			modify.set("id", "editLoanModify");
			modify.set("name", "editLoanModify");
			cmmnOccpObj.view.getMap().addInteraction(modify);
			modify.setActive(false);
			modify.on("modifyend", that.modifyend, that);
			
			// 도형 이동 인터렉션
//			var translate = new kworks.interaction.Translate({
//				features : new ol.Collection()
//			});
//			translate.set("id", "editTranslate");
//			translate.set("name", "editTranslate");
//			cmmnOccpObj.view.getMap().addInteraction(translate);
//			translate.setActive(false);
		},
		
		/**
		 * 그리기
		 */
		draw : function() {
			var that = this;
//			$(".div_edit_markup", that.selector).hide();
//			$(".div_edit_coords", that.selector).show();
			cmmnOccpObj.view.getMap().activeInteractions("drag", "editLoan"+that.drawType);
			var geometry = that.feature?that.feature.getGeometry():null;
			that.show(that.drawType, geometry);
		},
		
		/**
		 * 도형이 추가되었을 때 이전 도형이 있을 경우 필요한 값들을 복사하고 좌표 목록 표시 
		 * (that.source 에 addfeature 발생 시 실행되는 함수)
		 * @param {Object} evt 이벤트 객체
		 */
		addFeature : function(evt) {
			var that = this;
			if(that.feature && that.feature.getId()) {
				evt.feature.setId(that.feature.getId());
				var properties = $.extend({}, that.feature.getProperties(), evt.feature.getProperties());
				delete properties[MAP.GEOMETRY_ATTR_NAME];
				evt.feature.setProperties(properties);
				evt.feature.fid = that.feature.fid;
				evt.feature.tableName = that.feature.tableName;
				that.feature = evt.feature;
			}
			else {
				that.feature = evt.feature;
			}
			var geometry = that.feature?that.feature.getGeometry():null;
			that.show(that.drawType, geometry);
		},
		
		/**
		 * 점편집 시 좌표 목록 갱신
		 * @param {Object} evt 이벤트 객체
		 */
		modifyend : function(evt) {
			var that = this;
			var features = evt.features;
			if(features.get("length") > 0) {
				var geometry = that.feature?that.feature.getGeometry():null;
//				that.coords.show(that.drawType, geometry);
			}
			else {
				$.messager.alert(that.TITLE, "점편집 중 오류가 발생했습니다. 관리자에게 문의하여 주십시오.");
			}
		},
		
		/**
		 * 그리기 완료 시 이전에 그린 도형들 삭제
		 * @param {Object} evt 이벤트 객체
		 */
		drawend : function(evt) {
			var that = this;
			that.source.clear();
		},
		
		show : function(drawType, geometry) {
			var that = this;
//			that.clear();
			
			if(geometry) {
				var geom = geometry.clone();
				var proj = MAP.PROJECTION;
				var mapProj = cmmnOccpObj.view.getMap().getView().getProjection();
				if(proj != mapProj) {
					geom = geom.transform(mapProj, proj);
				}
				
//				var coords = geom.getCoordinates();
//				var tagStr = '';
//				if(geom instanceof ol.geom.Polygon) {
//					if(coords.length == 1) {
//						var linerRing = coords[0];
//						for(var i=0, len=linerRing.length; i < len; i++) {
//							var coord = linerRing[i];
//							tagStr += that.createCoordTag(coord);
//						}
//					}
//					else {
//						$.messager.alert(that.TITLE, "지원되지 않는 공간 형태입니다.");
//					}
//				}
//				else {
//					$.messager.alert(that.TITLE, "지원되지 않는 공간 타입입니다.");
//				}
//				$("#tby_edit_coords").html(tagStr);
//				that.showSeq();
			}
		},
		
		modify : function(evt) {
			var that = this;
			if(that.feature) {
				var features = new ol.Collection();
				features.push(that.feature);
				var modify = cmmnOccpObj.view.getMap().getInteraction("editLoanModify");
				modify.setFeatures(features);
				cmmnOccpObj.view.getMap().activeInteractions("drag", "editLoanModify");
//				$(".div_edit_markup", that.selector).hide();
//				$(".div_edit_coords", that.selector).show();
				var geometry = that.feature?that.feature.getGeometry():null;
//				that.coords.show(that.drawType, geometry);
			}
			else {
				$.messager.alert(that.TITLE, "선택된 시설물이 없습니다.");
				evt.stopPropagation();
			}
		},
		
		
		clear : function() {
			var that = this;
			that.source.clear();
			that.close();
//			$("#tby_edit_coords").html("");
		},
		
		open : function(prtIdn){
			var that = this;
			
			that.prtIdn = prtIdn;
			
			if(that.feature){
				
				if(that.selector) {
					that.close();
				}
				
				var windowOptions = {
					width :	500,
					height : 500,
					top : 120,
					left : 330,
					onClose : function() {
						that.close();
					}
				};
				
				var url = CONTEXT_PATH + '/cmmnprop/insertOousPage.do';
				
				var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function() {
					that.initUi();
					that.bindEvents();
				});
					
				that.selector = "#" + id;
			} else {
				$.messager.alert(that.TITLE, "공간객체를 먼저 만들어주세요.");
				return false;
			}
		},
		
		initUi : function(){
			var that = this;
			
			$('#oous_oonTit', that.selector).textbox({
				width : 370
			});
			
			$('#oous_rmkExp', that.selector).textbox({
				multiline : true,
				labelPosition : 'top',
				width : 370,
				height : 340,
			});
		},
		
		bindEvents : function(){
			var that = this;
			
			// 저장
			$("#btn_insert", that.selector).on("click", function() {
				that.insert();
				return false;
			});

			// 닫기
			$("#btn_close", that.selector).on("click", function() {
				that.close();
				return false;
			});
		},
		
		getPkId : function(){
			var that = this;
			var url = CONTEXT_PATH + '/cmmnprop/searchOousPk.do';
			
			$.ajax({
				type : 'GET',
				dataType : 'json',
				url : url,
				async : false,
				success : function(rs){
					var result = rs.searchOousPk;
					that.ocpIdn = result;
//					debugger;
//					return result;
				},error : function(err){
					console.error(err);
				}
			});
		},
		
		getToday : function(){
		    var date = new Date();
		    var year = date.getFullYear();
		    var month = ("0" + (1 + date.getMonth())).slice(-2);
		    var day = ("0" + date.getDate()).slice(-2);

		    return year + month + day;
		},
		
		getDataList : function(){
			var that = this;
			
			var lonTit = encodeURI($('#oous_oonTit', that.selector).textbox('getValue'));
			var rmkExp = encodeURI($('#oous_rmkExp', that.selector).textbox('getValue'));
			var prtDat = that.getToday();
			var prtIdn = that.prtIdn;
			
			that.feature.setProperties({PRT_IDN : prtIdn, OON_TIT : lonTit, PRT_DAT : prtDat, RMK_EXP : rmkExp});
			
		},
		
		//that.feature.setProperties
		insert : function(){
			var that = this;
			
			if (that.feature) {
				
				that.getPkId();
				that.getDataList();
				
				var pkId = that.ocpIdn;	
				
				that.feature.setProperties({ OCP_IDN : pkId });
//				console.log(pkId);
//				console.log(dataList);
				
//				debugger;
				
				var insertFeatures = [];
//				var updateFeatures = [];
				
				insertFeatures.push(that.feature);
				
//				if (that.feature.getId()) {
//					updateFeatures.push(that.feature);
//				} else {
//					var properties = that.feature.getProperties();
//					insertFeatures.push(that.feature);
//				}
//				debugger;
				
				cmmnOccpObj.view.getMap().getInteraction("editLoanModify").clearFeatures();

				that.feature.setGeometry(gisObj.toTransformSystem(that.feature.getGeometry()));
				
				var url = CONTEXT_PATH + "/proxy/transaction.do?sysId="+SYS_ID+"&editType=ATTRIBUTE";
				var params = {
					featureType : "BML_OOUS_AS",
					featureNS : MAP.PREFIX,
					featurePrefix : MAP.PREFIX,
					srsName : MAP.PROJECTION,
					geometryName : MAP.GEOMETRY_ATTR_NAME.toUpperCase(),
					serverType : MAP.SERVER_TYPE
				};
				
				kworks.service.WFS.transaction(url, params, insertFeatures, null, null, function(response) {
					if(response.inserted == 1) {
						if (that.onSave) {
							that.onSave(that.feature);
						}
						cmmnOccpObj.view.geomDataLode(that.prtIdn);
						$.messager.alert(that.TITLE, "등록되었습니다.");
//						that.onSave(that.feature);
						that.clear();
						mapObj.reloadWMS();
//						that.close;
					}else {
						$.messager.alert(that.TITLE, "등록에 실패하였습니다.");
					}
				});
				cmmnOccpObj.view.exitEvent();
			}else {
				$.messager.alert(that.TITLE, "공간정보는 필수 입력사항입니다.");
			}
			
		},
		
		close : function() {
			var that = this;
			
//			that.clean();
			windowObj.removeWindow(that.selector);
		},
		
};