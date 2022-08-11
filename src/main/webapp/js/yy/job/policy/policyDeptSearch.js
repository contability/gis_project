var policyDeptSearchObj = {
		
	CLASS_NAME : "POLICY_DEPT_MAP",
		
	PAGE : "policyDeptMap",
	
	selector : "#div_menu_panel_job",
	
	TITLE : "정책현황",
	
	init : function() {
		var that = this;

		that.initUi();
		that.bindEvents();
		that.open();
	},
	
	
	initUi : function() {
		var that = this;
		var url = CONTEXT_PATH + "/rest/menu/listAll.do";
		var params = { sysId : SYS_ID };
		$.get(url, params).done(function(result) {
			if(result && result.rows) {
				var rows = result.rows;
				var tagStr = '';
				for(var i=0; i < rows.length; i++) {
					var row = rows[i];
					if(row.upperMenuId == -1) {
						var groupMenuId = row.menuNo?row.menuNo:row.menuId;
						tagStr += '<li data-menu-id="' + groupMenuId + '" class="li_menu_group collapse">';
						tagStr += '<h2>' + row.menuNm + '</h2>';
						tagStr += '</li>';
						
						for(var j=0; j < rows.length; j++) {
							if(groupMenuId == rows[j].upperMenuId) {
								var menuId = rows[j].menuId?rows[j].menuId:rows[j].kwsMenu.menuId;
								var fnctId = rows[j].fnctId?rows[j].fnctId:rows[j].kwsMenu.fnctId;
								var url = rows[j].url?rows[j].url:rows[j].kwsMenu.url;
								tagStr += '<li class="li_menu" data-upper-menu-id="' + rows[j].upperMenuId + '" >';
								tagStr += '<a href="' + CONTEXT_PATH + url + '" class="a_menu" data-menu-id="' + menuId + '" data-fnct-id="' + fnctId + '" data-menu-nm="' + rows[j].menuNm + '" >';
								tagStr += '<img src="' + CONTEXT_PATH + '/images/kworks/map/skin2/left_3dep_bl.png" alt="DASH" />';
								tagStr += rows[j].menuNm;
								tagStr += '</a>';
								tagStr += '</li>';
							}
						}
						that.load.initUi();
					}
				}
				$(".ul_menu_job_menu_list", that.selector).html(tagStr);
				
			}
			else {
				$.messager.alert(that.TITLE, "업무 목록을 불러오는 데 실패했습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "업무 목록을 불러오는 데 실패했습니다.");
		});
	},
	
	/**
	 * 열기
	 */
	open : function() {
		var that = this;
		$(that.selector).show();
		// 화면 크기 변환 시 트리 영역 크기 조절 이벤트 등록
		mainObj.addResizeHandler("jopMenu", function() {
			that.resizeWindowHandler();
		});
		that.resizeWindowHandler();
	},
	
	/**
	 * 닫기
	 */
	close : function() {
		var that = this;
		selectObj.clear(that.CLASS_NAME);
		$(that.selector).hide();
		mainObj.removeResizeHandler("jopMenu");
	},
	
	/**
	 * 윈도우 크기 조절 핸들러
	 */
	resizeWindowHandler : function() {
		var that = this;
		var height = $("#div_menu_panel").height();
		var titleHeight = $("> .title", that.selector).height();
		var tagsHeight = $(".tabs-header", that.selector).height();
		var conditionHeight = $(".div_search_condition", that.selector).height();
		var pagenationHeight = $(".div_search_pagination", that.selector).height();
		var offset = 120;
		
		
		var contentHeight = height - titleHeight - tagsHeight - conditionHeight - pagenationHeight - offset;
		if(contentHeight < 200) contentHeight = 200;
		$(".div_menu_content", that.selector).height(contentHeight);
	},
	
	bindEvents : function() {
		var that = this;
		
		// 메뉴 그룹 클릭
		$(that.selector).on("click", ".li_menu_group", function() {
			var element = $(this);
			var menuId = element.attr("data-menu-id");
			if(element.hasClass("collapse")) {
				$(".li_menu[data-upper-menu-id="+menuId+"]").show();
				element.addClass("expand");
				element.removeClass("collapse");
			}
			else {
				$(".li_menu[data-upper-menu-id="+menuId+"]").hide();
				element.addClass("collapse");
				element.removeClass("expand");
			}
		});
		
		// 메뉴 선택
		$(that.selector).on("click", ".a_menu", function() {
			var element = $(this);
			
			$(".li_menu", that.selector).removeClass("on");
			element.parent().addClass("on");
			
			var url = element.attr("href");
			var menuId = element.attr("data-menu-id");
			var menuNm = element.attr("data-menu-nm");
			var fnctId = element.attr("data-fnct-id");
			
			switch(fnctId) {
				case "POLICY_REGISTER_SEARCH" :
					that.policyRegisterSearch(menuId, menuNm, url);
					break;
				case "POLICY_REGISTER_SPATIAL" :
					that.searchPolicyRegisterByBBox(menuId, menuNm, url);
					break;
				case "POLICY_FACILITIES_EDITING" :
					that.modifyPolicy(menuId, menuNm, url);
					break;
				default :
					if (menuObj.jobObj[PROJECT_CODE+"Obj"]) {
						var functionName = camelcaseUtils.underbarToCamelcase(fnctId);
						if (menuObj.jobObj[PROJECT_CODE+"Obj"][functionName]) {
							menuObj.jobObj[PROJECT_CODE+"Obj"][functionName](menuId, menuNm, url);
						}
					}
					break;
			}
			return false;
		});
	},
	
	/**
	 * 점책지도 관리서비스 공간검색
	 * @param menuId - 메뉴 아이디
	 * @param menuNm - 메뉴 명칭
	 * @param url - 서비스 URL
	 */
	policyRegisterSearch : function(menuId, menuNm, url) {
		resultObj.close();
		policyRegstrObj.close();
		
		policyRegstrObj.open();
	},
	
	/**
	 * 정책지도 대장 공간검색
	 * @param menuId - 메뉴 아이디
	 * @param menuNm - 메뉴 명칭
	 * @param url - 서비스 URL
	 */
	searchPolicyRegisterByBBox : function(menuId, menuNm, url) {
		var extent = mapObj.getMap().getExtent();
		var from = mapObj.getMap().getView().getProjection();
		var to = ol.proj.get(MAP.PROJECTION);
		var transformExtent = spatialUtils.transformExtent(extent, from ,to);
		
		var filter = {
			menuId : menuId,
			filterType : "BBOX",
			xmin : transformExtent[0],
			ymin : transformExtent[1],
			xmax : transformExtent[2],
			ymax : transformExtent[3]
		};
		$.post(url, filter).done(function(response) {
			var policyData = response.result;
			if(policyData && policyData.length > 0) {
				policyRegResultObj.createData([policyData]);
			}
			else {
				$.messager.alert(menuNm, "데이터가 없습니다.");
			}
		}).fail(function() {
			$.messager.alert(menuNm, "권한이 없습니다.");
		});
	},	
	
	modifyPolicy : function(menuId, menuNm, url) {
		url += "?menuId=" + menuId;
		$.get(url).done(function(response) {
			if(response) {
				var data = response.data;
				if(response.isEdit) {
					if(response.isAuthor) {
						var layer = response.layer;
						var drawType = null;
						switch(layer.lyrTy) {
						case "POLYGON" :
						case "MULTIPOLYGON" :
							drawType = "Polygon";
							break;
						}
						editPolicyObj.open(data.dataId, data.dataAlias, drawType); 
					}
					else {
						$.messager.alert(menuNm, "권한이 없습니다.");
					}
				}
				else {
					$.messager.alert(menuNm, "편집 제한");
				}
			}
		});
	},
};

/**
 * 공간 편집
 * 
 * @namespace
 */
policyDeptSearchObj.load = {

	/**
	 * 클래스 명
	 * 
	 * @type {String}
	 */
	CLASS_NAME : "PolicyDeptSearch",

	data : null,
	
	selector : "#div_menu_panel_job",

	// 시작시 로딩
	init : function() {
		var that = this;
		var deferred = $.Deferred();
		var url = CONTEXT_PATH + "/policy/listDeptSub.do";
		$.get(url).done(function(result) {
			if (result && result.rows) {
				that.setData(result.rows);
				deferred.resolve(that.getData());
			} else {
				$.messager.alert(that.TITLE, "실과소 목록을 불러오는데 실패하였습니다.");
			}
		}).fail(function() {
			$.messager.alert(that.TITLE, "실과소 목록을 불러오는데 실패하였습니다.");
		});
	},
	
	//데이터 저장
	setData : function(data) {
		var that = this;
		that.data = data;
	},
	
	//데이터 가져오기
	getData : function() {
		var that = this;
		return that.data;
	},
	
	initUi : function(){
		var that = this;
		that.bindEvents();
		that.createTreeData();
	},
	
	bindEvents : function() {
		var that = this;
		$("#div_dept_tree").hide();
		// 메뉴 그룹 클릭
		$(that.selector).on("click", ".li_menu_dept_group", function() {
			var element = $(this);
			if(element.hasClass("collapse")) {
				$("#div_dept_tree").show();
				element.addClass("expand");
				element.removeClass("collapse");
			}
			else {
				$("#div_dept_tree").hide();
				element.addClass("collapse");
				element.removeClass("expand");
			}
		});
		
		$("#div_dept_tree", that.selector).tree();
	},

	//트리구조 생성
	createTreeData : function (){
		var that = this;
		that.menuData();
		var data = that.createDepData();
		
		$("#div_dept_tree", that.selector).tree({
			data : data,
			dnd : true,
			checkbox : true,
			checked : false,
			animate : true,
			onCheck : function(node) {
				if(node.type=="plcy"){
					if(node.checked==true){
						if(node.children==null){
							that.showFeatures([node]);
						}else{
							that.showFeatures(node.children);
						}
					}else{
						that.showFeatures([]);
					}
					
				}else if(node.type=="deptSub"){
					if(node.checked==true){
						that.showFeatures(node.children);
					}else{
						that.showFeatures([]);
					}
				}
			},
			onClick : function(node){
				if(node.type=="plcy"){
					that.showFeatures([node]);
					windowObj.policyRegisterObj.open(node);
				}
			} 
		});
	},
	
	showFeatures : function(data) {
		var that = this;
		
		that.removeFeatures();
		
		var format = new ol.format.WKT();
		var features = [];
		for (var i = 0, len = data.length; i < len; i++) {
			var row = data[i];
			var geom = row[MAP.GEOMETRY_ATTR_NAME];
			if (geom) {
				var feature = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);
				features.push(feature);
			} else {
				if (data.length == 1) {
					$.messager.alert(that.TITLE, '공간정보가 없습니다.');
				}
			}
		}
		if (features && features.length > 0) {
			highlightObj.showRedFeatures(that.CLASS_NAME, features,
					!that.isFixed, {
						projection : ol.proj.get(MAP.PROJECTION)
					});
			}
//		}
	},
	
	/**
	 * 도형 표시 제거
	 */
	removeFeatures : function() {
		var that = this;
		highlightObj.removeFeatures(that.CLASS_NAME);
	},
	
	menuData : function(){
		var that = this;
		var tagMun = '';
		tagMun += '<li data-menu-id="' + '99999' + '" class="li_menu_dept_group collapse">';
		tagMun += '<h2>' + '조직별 조회' + '</h2>';
		tagMun += '</li>';
		
		$(".ul_menu_dept_menu_tree", that.selector).html(tagMun);
		
	},
	
	//lodYmd > dept > deptSub > plcy
	createDepData : function() {
		var that = this;
		
		var treeData = []; //트리 구조를 만들 데이터
		
		var data = that.data; //데이터가져오기
		var lodYmdList = {};
		
		//두번째 트리만드는 for문
		for(var i=0, len=data.length; i < len; i++) {
			
			//두번째 트리 만들기
			if(data) {
				
				var deptList = {}; // 첫번쨰 트리
				
				var deptSubNamed = data[i].kwsDeptSubs; 
				//세번째 트리만들기
				for(var j=0, jLen=deptSubNamed.length; j < jLen; j++) {
					var plcyTree = that.getPlcyNode(deptSubNamed[j].deptSubCode);
					// 네번째 트리 추가
					for(var k=0, kLen=plcyTree.length; k < kLen; k++) {
						var plcyData = plcyTree[k];
						
						//첫번째 트리 추가
						if(!lodYmdList[plcyData.lodYmd.substr(0,4)]){
							lodYmdList[plcyData.lodYmd.substr(0,4)] = {
									id: plcyData.lodYmd,
									text: plcyData.lodYmd.substr(0,4),
									type: 'lodYmd',
									children : []
							};
							
							//두번째트리
							text = '<span class="span_text" title="'+ data[i].deptNm +'">' + data[i].deptNm + '</span>';
							
							deptList = {
									id : data[i].deptCode,
									text : text,
									type : "dept",
									children : []
							};
							
							//세번쨰트리
							text = "<span class='span_text' title='" + deptSubNamed[j].deptSubNm + "'>" + deptSubNamed[j].deptSubNm + "</span>";
							
							var deptSub ={
								id : deptSubNamed[j].deptSubCode,
								text :  text,
								state : "closed",
								type : "deptSub",
								checked : false,
								children : []
							};
							
							deptSub.children.push(that.createPlcyNode(plcyData));
							deptList.children.push(deptSub);
							lodYmdList[plcyData.lodYmd.substr(0,4)].children.push(deptList);
							
						}else{
							var ymdChildren = lodYmdList[plcyData.lodYmd.substr(0,4)].children;
							var ymdChildrenNo = 0;
							var chkChildren = false;
							var chkChildrenChildren = false;
							var ymdChildrenChildrenNo = 0;
							
							for(var l = 0; l < ymdChildren.length; l++){
								if(ymdChildren[l].id == data[i].deptCode){
									ymdChildrenNo = l;
									chkChildren = true;
								}
							}
							
							if(chkChildren){
								var ymdChildrenChildren = ymdChildren[ymdChildrenNo].children;
								
								for(var m = 0; m < ymdChildrenChildren.length; m++){
									if(ymdChildrenChildren[m].id == deptSubNamed[j].deptSubCode){
										chkChildrenChildren = true;
										ymdChildrenChildrenNo = m;
									}
								}
								
								if(chkChildrenChildren){
									ymdChildrenChildren[ymdChildrenChildrenNo].children.push(that.createPlcyNode(plcyData));
								}else{
									//세번쨰트리
									text = "<span class='span_text' title='" + deptSubNamed[j].deptSubNm + "'>" + deptSubNamed[j].deptSubNm + "</span>";
									
									var deptSub ={
											id : deptSubNamed[j].deptSubCode,
											text :  text,
											state : "closed",
											type : "deptSub",
											checked : false,
											children : []
									};
									
									deptSub.children.push(that.createPlcyNode(plcyData));
									ymdChildrenChildren.push(deptSub);
								}
							}else{
								var text = '';
								
								//두번째트리
								text = '<span class="span_text" title="'+ data[i].deptNm +'">' + data[i].deptNm + '</span>';
								
								deptList = {
									id : data[i].deptCode,
									text : text,
									type : "dept",
									children : []
								};
								
								//세번쨰트리
								text = "<span class='span_text' title='" + deptSubNamed[j].deptSubNm + "'>" + deptSubNamed[j].deptSubNm + "</span>";
								
								var deptSub ={
									id : deptSubNamed[j].deptSubCode,
									text :  text,
									state : "closed",
									type : "deptSub",
									checked : false,
									children : []
								};
								
								deptSub.children.push(that.createPlcyNode(plcyData));
								deptList.children.push(deptSub);
								lodYmdList[plcyData.lodYmd.substr(0,4)].children.push(deptList);
							}
						}
					}	
				}
			}	
		}
		
		for(var i in lodYmdList){
			treeData.push(lodYmdList[i]);
		}
		
		return treeData;
	},
	
	//네번쨰 트리 정책지도 트리
	createPlcyNode : function(data){
		var ftrIdn = data.ftrIdn;
		var text = "<canvas class='canvas_legend' data-ftr-idn='"+ftrIdn+"' width='3' height='16' ></canvas>";
		text += "<span class='span_text' title='" + data.plcyTit + "'>" + data.plcyTit + "</span>";
		
		
		var plcyStatTree = {
			id : ftrIdn,
			text : text,
			geom : data.geom,
			checkbox : true,
			checked : false,
			type : "plcy",
			lodYmd : data.lodYmd
		};
		return plcyStatTree;
	},
	
	getPlcyNode : function(deptSbCd) {
		var plcyData =[];
		var url = CONTEXT_PATH + "/policy/" +deptSbCd+ "/deptSubList.do";
		$.ajax({
			url : url,
			data : deptSbCd,
			type : "GET",
			async : false,
			dataType : "json",
			
			success : function(response){
				plcyData = response.rows;
			}
		});
		return plcyData;
	},

};
