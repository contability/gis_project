
// 지적 업무
// 2021.05.25 정신형

menuObj.lgstrBizObj = {
	/**
	 * 페이지 명
	 * 
	 * @type {String}
	 */
	PAGE : "lgstrBiz",

	CLASS_NAME : "LgstrBizMenu",

	/**
	 * 선택자
	 * 
	 * @type {String}
	 */
	selector : "#div_menu_panel_lgstrBiz",

	/**
	 * 제목
	 * 
	 * @type {String}
	 */
	/*TITLE : "지적업무",*/
	TITLE : "변환지적",

	pageIndex : 1,

	isMenuCreated : false,
	
	lndcgrList : null,
	
	isAuthor : null,

	/**
	 * 초기화
	 */
	init : function() {
		var that = this;
		
		var isAuthor = dataAuthorObj.getDataAuthor('KWS_LGSTR_BSNS');
		that.isAuthor = isAuthor.indictAt;
		
		that.createMenu(PROJECT_CODE);
		that.rplotreexmnObj.init(that);
		that.reexmnObj.init(that);
		if(that.isAuthor.indexOf('Y') > -1){
			that.iprvarObj.init(that);
		}

		that.bindEvents();
		that.open();
		
		that.lndcgrListData();
		
		$(".mapTab .mTab:first", that.selector).trigger("click");
		$(".div_search_content").hide();
	},

	createMenu : function(projectCode) {

		var that = this;

		// 메뉴 기 생성여부 확인
		if (!that.isMenuCreated) {
			var tagStr = '<ul>';
			
			var rplotreexmnImgNo = '';
			var reexmnImgNo = '';
			var iprvarImgTag = '';
			
			if(that.isAuthor.indexOf('Y') > -1){
				rplotreexmnImgNo = 'tab8';
				reexmnImgNo = 'tab10';
				iprvarImgTag = '<li><a data-for-id="div_menu_panel_search_iprvar" class="mTab" href="#"><img src="'
					+ CONTEXT_PATH
					+ '/images/kworks/map/skin2/search_tab9_off.png" alt="정비보류지역 관리조서"/></a></li>';
			}else{
				rplotreexmnImgNo = 'tab11';
				reexmnImgNo = 'tab12';
			}
			
			tagStr += '<li><a data-for-id="div_menu_panel_search_rplotreexmn" class="mTab" href="#"><img src="'
					+ CONTEXT_PATH
					+ '/images/kworks/map/skin2/search_' + rplotreexmnImgNo + '_on.png" alt="지적확정"/></a></li>';
			
			tagStr += '<li><a data-for-id="div_menu_panel_search_reexmn" class="mTab" href="#"><img src="'
				+ CONTEXT_PATH
				+ '/images/kworks/map/skin2/search_' + reexmnImgNo + '_off.png" alt="재조사"/></a></li>';
			
			tagStr += iprvarImgTag;
			tagStr += '</ul>';

			$(".mapTab", that.selector).append(tagStr);
			that.isMenuCreated = true;
		}
	},

	bindEvents : function() {
		var that = this;
		
		// 탭 변경
		$(".mapTab .mTab", that.selector).click(function() {
			var element = $(this);
			var contentId = element.attr("data-for-id");

			$(".mapTab .mTab", that.selector).find("img").each(function() {
				var imgElement = $(this);
				imgElement.attr("src", imgElement.attr("src").replace("_on", "_off"));
			});

			element.find("img").each(function() {
				var imgElement = $(this);
				imgElement.attr("src", imgElement.attr("src").replace("_off", "_on"));
			});

			$(".tab_content", that.selector).hide();
			$("#" + contentId).show();
		});
	},
	
	createResult : function(selector, data, pagination){
		var that = this;
		$(".font_total_count", selector).text(pagination.totalRecordCount);
		that.createSearchContent(selector, data);
		that.createSearchPagination(selector, pagination);
		
		$(".div_search_content", selector).show();
		
		if(data.length > 0){
			$(".div_search_pagination", selector).show();
		}else{
			$(".div_search_pagination", selector).hide();
		}
	},
	
	chkPnu : function(pnu){
		var chkPnu = 0;
		$.ajax({
			type: 'GET',
			url : CONTEXT_PATH +"/iprvar/chkPnu.do",
			async : false,
			data : {
				pnu : pnu
			},
			dataType : 'json',
			success:function(result){
				chkPnu = result.chkPnu;
			}
		});
		
		return chkPnu;
	},
	
	createSearchContent : function(selector, data){
		var that = this;
		
		var tagStr = '';
		for(var i=0, len=data.length; i<len; i++){
			var row = data[i];
			
			var chkPnu = that.chkPnu(row.pnu);
			
			var content = '';
			
			if(row.content.substr(row.content.length-2,2) == '-0'){
            	content = row.content.replace('-0','');
            }else{
            	content = row.content;
            }
			
			tagStr += '<li>';
			if(chkPnu > 0){
				tagStr += '<a href="#" class="a_content" style="width:172px;">' + content + '</a>';
				tagStr +='<a href="#" class="a_move" data-pnu="' + row.pnu + '">';
				tagStr +='<img alt="위치이동" src="'+ CONTEXT_PATH +'/images/kworks/map/common/boomark_icon3_off.png" title="위치이동">';
				tagStr +='</a>';
			}else{
				tagStr += '<a href="#" class="a_content" style="width:195px;">' + content + '</a>';
			}
			tagStr +='<a href="#" class="a_detail" data-id="'+ row.id +'">';
			tagStr +='<img alt="상세정보" src="'+ CONTEXT_PATH +'/images/kworks/map/common/search_icon1_ov.png" title="상세정보">';
			tagStr +='</a>';
			tagStr +='</li>';
		}
		
		$(".ul_search_content", selector).html(tagStr);
	},
	
	createSearchPagination : function(selector, pagination) {
		var tagStr = '';
		
		if(pagination.currentPageNo > pagination.firstPageNo) {
			tagStr += '<a href="#" class="a_img" data-page-index="' + pagination.firstPageNo + '"><img src="images/kworks/map/common/boardLst_pp.png" /></a>';
			tagStr += '<a href="#" class="a_img" data-page-index="' + (pagination.currentPageNo-1) + '"><img src="images/kworks/map/common/boardLst_p.png" /></a>';
		}
		
		for(var i = pagination.firstPageNoOnPageList; i <= pagination.lastPageNoOnPageList; i++) {
			tagStr += '<a href="#" class="a_text';
			tagStr += i==pagination.currentPageNo?" a_select ":"";
			tagStr += '" data-page-index="' + i + '">';
			tagStr += i;
			tagStr += '</a>';
		}
		
		if(pagination.currentPageNo < pagination.lastPageNo) {
			tagStr += '<a href="#" class="a_img" data-page-index="' + (pagination.currentPageNo+1) + '"><img src="images/kworks/map/common/boardLst_n.png" /></a>';
			tagStr += '<a href="#" class="a_img" data-page-index="' + pagination.lastPageNo + '"><img src="images/kworks/map/common/boardLst_nn.png" /></a>';
		}
		
		$(".div_search_pagination", selector).html(tagStr);
	},
	
	highlight : function(pnu, data){
		var that = this;
		
		var format = new ol.format.WKT();
		for(var i = 0, len=data.length; i<len; i++){
			var row = data[i];
			if(row['pnu'] == pnu){
				var feature = format.readFeature(row[MAP.GEOMETRY_ATTR_NAME]);
				highlightObj.showRedFeatures(that.CLASS_NAME, [feature], true, {
					projection : ol.proj.get(MAP.PROJECTION)
				});
			}
		}
	},

	open : function() {
		var that = this;
		$(that.selector).show();
	},

	close : function() {
		var that = this;
		$(that.selector).hide();
	},
	
	jibunByPnu : function(pnu){
		dongLiObj.init();
		var emdList = dongLiObj.getData();
		var emdNm = '';
		for(var i = 0; i < emdList.length; i++){
			if(emdList[i].emdCd == pnu.substring(0,8)){
				emdNm = emdList[i].emdNm;
			}
		}
		var dongLocNm = pnuObj.getAddr(pnu, false);
		
		return emdNm + " " + dongLocNm;
	},
	
	lndcgrListData : function(){
		var that = this;
		
		that.lndcgrList = [{'codeId' : '01', 'codeNm' : '전'},
		                   {'codeId' : '02', 'codeNm' : '답'},
		                   {'codeId' : '03', 'codeNm' : '과수원'},
		                   {'codeId' : '04', 'codeNm' : '목장용지'},
		                   {'codeId' : '05', 'codeNm' : '임야'},
		                   {'codeId' : '06', 'codeNm' : '광천지'},
		                   {'codeId' : '07', 'codeNm' : '염전'},
		                   {'codeId' : '08', 'codeNm' : '대'},
		                   {'codeId' : '09', 'codeNm' : '공장용지'},
		                   {'codeId' : '10', 'codeNm' : '학교용지'},
		                   {'codeId' : '11', 'codeNm' : '주차장'},
		                   {'codeId' : '12', 'codeNm' : '주유소용지'},
		                   {'codeId' : '13', 'codeNm' : '창고용지'},
		                   {'codeId' : '14', 'codeNm' : '도로'},
		                   {'codeId' : '15', 'codeNm' : '철도용지'},
		                   {'codeId' : '16', 'codeNm' : '제방'},
		                   {'codeId' : '17', 'codeNm' : '하천'},
		                   {'codeId' : '18', 'codeNm' : '구거'},
		                   {'codeId' : '19', 'codeNm' : '유지'},
		                   {'codeId' : '20', 'codeNm' : '양어장'},
		                   {'codeId' : '21', 'codeNm' : '수도용지'},
		                   {'codeId' : '22', 'codeNm' : '공원'},
		                   {'codeId' : '23', 'codeNm' : '체육용지'},
		                   {'codeId' : '24', 'codeNm' : '유원지'},
		                   {'codeId' : '25', 'codeNm' : '종교용지'},
		                   {'codeId' : '26', 'codeNm' : '사적지'},
		                   {'codeId' : '27', 'codeNm' : '묘지'},
		                   {'codeId' : '28', 'codeNm' : '잡종지'}];
	},
	
	getLndcgrCodeNm : function(lndcgrCd){
		var that = this;
		
		var lndcgrList = that.lndcgrList;
		
		for(var i = 0; i < lndcgrList.length; i++){
			if(lndcgrCd == lndcgrList[i].codeId){
				return lndcgrList[i].codeNm;
			}
		}
	}
};

menuObj.lgstrBizObj.rplotreexmnObj = {
	selector : "#div_menu_panel_search_rplotreexmn",
	
	CLASS_NAME : "rplotreexmn",

	parent : null,

	TITLE : "지적 확정",

	data : null,
	
	moveData : null,

	init : function(parent) {
		var that = this;
		that.parent = parent;
		
		that.initUi();
		that.load();
		that.bindEvents();
	},
	
	initUi : function(){
		var that = this;
		
		$('#bsnsAreaNm', that.selector).textbox({
			required : false,
			width : 155
		});
		
		$(".sel_dong", that.selector).combobox({
			//required : true,
			required : false,
			valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONGLI_CODE_FIELD),
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONGLI_NAME_FIELD),
			width : 155,
			onSelect : function(record){
				that.searchLi(record.emdCd);
			}
		});
		
		$(".sel_li", that.selector).combobox({
			//required : true,
			required : false,
			valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD),
			width : 155
		});
		
		$(".txt_mnnm", that.selector).numberspinner({
			min : 1,
			max : 9999,
			increment : 1,
			width : 155
		});
		
		$(".txt_slno", that.selector).numberspinner({
			min : 0,
			max : 9999,
			increment : 1,
			width : 155
		});
		
		$(".a_reset", that.selector).linkbutton({
			iconCls : "icon-reload"
		});
		
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
	},
	
	searchLi : function(emdCode){
		var that = this;
		
		var filter = {
				filterType : "COMPARISON",
				comparisonType : "LIKE",
				property : "BJD_CDE",
				value : emdCode+"%",
				sortOrdr : "BJD_NAM",
				isOriginColumnValue : true
		};
		
		spatialSearchUtils.listAll(that.TITLE, liObj.getDataId(), filter, function(data) {
			if(data) {
				$(".sel_li", that.selector).combobox("setValue", "");
				$(".sel_li", that.selector).combobox("loadData", data);
				//$(".sel_li", that.selector).combobox("setValue", data[0].bjdCde);
			}
		});
	},
	
	move : function(pnu){
		var that = this;
		
		that.lpPaCbndList(pnu, 1, 50, function(tagData, data, pagination) {
			that.parent.highlight(pnu, data);
		});
	},
	search : function(pageIndex){
		var that = this;
		
		var bsnsAreaNm = $('#bsnsAreaNm', that.selector).textbox('getValue');
		var pnu = '';
		var dongCode = $(".sel_dong", that.selector).combobox("getValue");
		var liCode = $(".sel_li", that.selector).combobox("getValue");
		var mntn = $(".chk_mntn", that.selector).is(":checked")?"2":"1";
		var mnnm = $(".txt_mnnm", that.selector).numberspinner("getValue");
		var slno = $(".txt_slno", that.selector).numberspinner("getValue");
		
		var isValidMnnm = false;
		if(!mnnm == '' || !mnnm == null){
			isValidMnnm = true;
			mnnm = stringUtils.lpad(mnnm, 4);
		}
		
		var isValidSlno = false;
		if(!slno == '' || !slno == null){
			isValidSlno = true;
			slno = stringUtils.lpad(slno, 4);
		}
		
		if(!dongObj.checkDongCode(liCode)){
			if(!dongLiObj.checkDongLiCode(dongCode)){
				pnu = '__________' + mntn;
				
				if(isValidMnnm && isValidSlno){
					pnu += mnnm + slno;
				}else if(isValidMnnm && !isValidSlno){
					pnu += mnnm + '%';
				}else if(!isValidMnnm && isValidSlno){
					pnu += '____' + slno;
				}else if(!isValidMnnm && !isValidSlno){
					if(mntn == '1'){
						pnu = '%%';
					}else{
						pnu += '%';
					}
				}
				
			}else{
				pnu = dongCode + '__' + mntn;
				
				if(isValidMnnm && isValidSlno){
					pnu += mnnm + slno;
				}else if(isValidMnnm && !isValidSlno){
					pnu += mnnm + '%';
				}else if(!isValidMnnm && isValidSlno){
					pnu += '____' + slno;
				}else if(!isValidMnnm && !isValidSlno){
					pnu += '%';
				}
			}
		}else{
			pnu = pnuObj.createPnuForLike(liCode, mntn, mnnm, slno);
		}
		
		
		
		that.rplotreexmnList(pnu, bsnsAreaNm, pageIndex, 10, function(tagData, data, pagination){
			that.parent.createResult(that.selector, tagData, pagination);
		});
	},
	
	validator : function(){
		var that = this;
		
		if(!$(".sel_dong", that.selector).combobox("isValid")){
			$.messager.alert(that.TITLE, "법정동을 선택하여 주십시오.");
			return false;
		}
		return true;
	},
	
	rplotreexmnList : function(pnu, bsnsAreaNm, pageIndex, recordCountPerPage, callback, options){
		var that=this;
		
		$.ajax({
			type:'GET',
			url : CONTEXT_PATH + '/rplotreexmn/selectList.do',
			data : {
				pnu : pnu,
				bsnsAreaNm : bsnsAreaNm,
				pageIndex : pageIndex,
				bsnsSe : '1'	// 지적확정
			},
			dataType : 'json',
			success : function(result){
				var rows = result.rows;
				var data = [];
				for(var i = 0; i < rows.length; i++){
					data.push({
						id: rows[i].rplotreexmnNo,
						lpPaCbndId : null,
						pnu: rows[i].pnu,
						content : rows[i].rplotreexmnNm
					});
				}
				that.parent.createResult(that.selector, data, result.paginationInfo);
			}
		});
	},
	
	lpPaCbndList : function(pnu, pageIndex, recordCountPerPage, callback, options){
		var that = this;
		var filter = {
			filterType : "COMPARISON",
			comparisonType : "LIKE",
			property : "PNU",
			value : pnu,
			pageIndex : pageIndex,
			recordCountPerPage : recordCountPerPage,
			pageSize : 5,
			sortOrdr : "PNU"
		};
		spatialSearchUtils.list(that.TITLE, COMMON.LAND, filter, function(rows, pagination) {
			var data = [];
			for(var i=0, len=rows.length; i < len; i++) {
				var row = rows[i];
				data.push({
					id : row[COMMON.PK],
					content : pnuObj.getLocNm(row.pnu) + " " + row.jibun,
					pnu : row.pnu
				});
			}
			if(callback) {
				callback(data, rows, pagination);
			}
		}, options);
	},
	
	load : function(){
		var that = this;
		
		dongLiObj.getPromise().done(function(){
			var data = dongLiObj.getData();
			$(".sel_dong", that.selector).combobox("loadData", data);
		});
		
		/*dongObj.getPromise().done(function(){
			var data = dongObj.getData();
			$(".sel_li", that.selector).combobox("loadData", data);
		});*/
	},
	
	bindEvents : function(){
		var that = this;
		
		//읍면동에서 엔터 시 본번으로 이동
		$(".sel_dong", that.selector).combobox("textbox").keyup(function(e){
			var keyCode = e.keyCode?e.keyCode:e.which;
			if(keyCode == 13){
				$(".txt_mnnm", that.selector).numberspinner("textbox").focus();
			}
		});
		
		//본번에서 엔터 시 부번으로 이동
		$(".txt_mnnm", that.selector).numberspinner("textbox").keyup(function(e){
			var keyCode = e.keyCode?e.keyCode:e.which;
			if(keyCode == 13){
				$(".txt_slno", that.selector).numberspinner("textbox").focus();
			}
		});
		
		// 부번에서 엔터 시 검색
		$(".txt_slno", that.selector).numberspinner("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".a_search", that.selector).trigger("click");
			}
		});
		
		//검색
		$(".a_search", that.selector).click(function(){
			//if(that.validator()){
				that.search(1);
				//quickObj.triggerOnLand();
			//}
			return false;
		});
		
		//초기화
		$(".a_reset", that.selector).click(function(){
			that.reset();
		});
		
		//위치 이동
		$(that.selector).on("click", ".a_move", function(){
			var element = $(this);
			var id = element.attr("data-pnu");
			that.move(id);
			return false;
		});
		
		//페이징 이동
		$(that.selector).on("click",".div_search_pagination a", function(){
			var element = $(this);
			var pageIndex = element.attr("data-page-index");
			that.search(pageIndex);
		});
		
		//상세 정보
		$(that.selector).on("click",".a_detail", function(){
			var element = $(this);
			var rplotreexmnNo = element.attr("data-id");
			that.selectOne.open(rplotreexmnNo);
		});
	},
	
	reset : function(){
		var that = this;
		
		$(".sel_dong", that.selector).combobox("setValue","");
		$(".sel_li", that.selector).combobox("setValue","");
		$(".chk_mntn", that.selector).prop("checked",false);
		$(".txt_mnnm", that.selector).numberspinner("setValue","");
		$(".txt_slno", that.selector).numberspinner("setValue","");
		$('#bsnsAreaNm', that.selector).textbox('clear');
	}
};

menuObj.lgstrBizObj.rplotreexmnObj.selectOne = {
		selector : "#div_rplotreexmn_select",
		
		createSelector : null,
		
		TITLE : "지적확정 상세정보",
		
		CLASS_NAME : "rplotreexmnSelect",
		
		isCreated : false,
		
		rplotreexmnNo : 0,
		
		open : function(rplotreexmnNo){
			var that = this;
			
			if(that.selector){
				that.close();
			}
			
			var windowOptions = {
					width:700,
					height:390,
					top:150,
					left:550,
					onClose:function(){
						that.close();
					}
			};
			
			var url = CONTEXT_PATH + '/rplotreexmn/selectOnePage.do';
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function(){
				if(!that.isCreated){
					that.rplotreexmnNo = rplotreexmnNo;
					that.load();
					that.bindEvents();
					that.isCreated = true;
				}
			});
			
			that.createSelector = '#'+id;
			
			
		},
		
		load : function(){
			var that = this;
			
			var url = CONTEXT_PATH + '/rplotreexmn/'+ that.rplotreexmnNo +'/selectOne.do';
			
			$.ajax({
				type:'GET',
				dataType : 'json',
				url:url,
				success:function(rs){
					var rpx = rs.rplotreexmn;
					var rpxLgstr = rs.rplotreexmnLgstr;
					
					for(var i = 0; i < Object.keys(rpx).length; i++){
						var key = Object.keys(rpx)[i];
						var tag = $("#detail_"+key);
						
						 if(key == 'bsnsSe'){
	                        	if(rpx[key] == '1'){
	                        		tag.html('환지정리');
	                        	}else{
	                        		tag.html('지적재조사');
	                        	}
	                        }else{
	                        	tag.html(rpx[key]);
	                        }
					}
					
					for(var i = 0; i < rpxLgstr.length; i++){
						
						if(rpxLgstr[i].brftrSe == '1'){
							for(var j = 0; j < Object.keys(rpxLgstr[i]).length; j++){
								var key = Object.keys(rpxLgstr[i])[j];
								
								if(key == 'pnu'){
									if(rpxLgstr[i][key] != '' && rpxLgstr[i][key] != null){
										 var pnu = menuObj.lgstrBizObj.jibunByPnu(rpxLgstr[i][key]);

										    if(pnu.substr(pnu.length-2,2) == '-0'){
		            	                        pnu = pnu.replace('-0','');
		                                    }
											
										$("#detail_bfr_jibun").html(pnu);
									}
								}else if(key == 'rplotLndcgr'){
									if(rpxLgstr[i][key] != '' && rpxLgstr[i][key] != null){
										$("#detail_bfr_"+key).html(menuObj.lgstrBizObj.getLndcgrCodeNm(rpxLgstr[i][key]));
									}
								}else{
									$("#detail_bfr_"+key).html(rpxLgstr[i][key]);
								}
								
							}
						}else{
							for(var j = 0; j < Object.keys(rpxLgstr[i]).length; j++){
								var key = Object.keys(rpxLgstr[i])[j];
								
								if(key == 'rplotLndcgr'){
									if(rpxLgstr[i][key] != '' && rpxLgstr[i][key] != null){
										$("#detail_aft_"+key).html(menuObj.lgstrBizObj.getLndcgrCodeNm(rpxLgstr[i][key]));
									}
								}else{
									$("#detail_aft_"+key).html(rpxLgstr[i][key]);
								}
							}
						}
					}
				}
			});
		},
		
		bindEvents : function(){
			var that = this;
			
			$("#a_close_rplotreexmn").click(function(){
				that.close();
			});
		},
		
		close : function(){
			var that = this;
			
			windowObj.removeWindow(that.createSelector);
			that.createSelector = null;
			that.isCreated = false;
		}
};

menuObj.lgstrBizObj.reexmnObj = {
		selector : "#div_menu_panel_search_reexmn",
		
		CLASS_NAME : "reexmn",

		parent : null,

		TITLE : "재조사",

		data : null,
		
		moveData : null,

		init : function(parent) {
			var that = this;
			that.parent = parent;
			
			that.initUi();
			that.load();
			that.bindEvents();
		},
		
		initUi : function(){
			var that = this;
			
			$('#bsnsAreaNm', that.selector).textbox({
				required : false,
				width : 155
			});
			
			$(".sel_dong", that.selector).combobox({
				required : false,
				valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONGLI_CODE_FIELD),
				textField : camelcaseUtils.underbarToCamelcase(COMMON.DONGLI_NAME_FIELD),
				width : 155,
				onSelect : function(record){
					that.searchLi(record.emdCd);
				}
			});
			
			$(".sel_li", that.selector).combobox({
				required : false,
				valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
				textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD),
				width : 155
			});
			
			$(".txt_mnnm", that.selector).numberspinner({
				min : 1,
				max : 9999,
				increment : 1,
				width : 155
			});
			
			$(".txt_slno", that.selector).numberspinner({
				min : 0,
				max : 9999,
				increment : 1,
				width : 155
			});
			
			$(".a_reset", that.selector).linkbutton({
				iconCls : "icon-reload"
			});
			
			$(".a_search", that.selector).linkbutton({
				iconCls : "icon-search"
			});
		},
		
		searchLi : function(emdCode){
			var that = this;
			
			var filter = {
					filterType : "COMPARISON",
					comparisonType : "LIKE",
					property : "BJD_CDE",
					value : emdCode+"%",
					sortOrdr : "BJD_NAM",
					isOriginColumnValue : true
			};
			
			spatialSearchUtils.listAll(that.TITLE, liObj.getDataId(), filter, function(data) {
				if(data) {
					$(".sel_li", that.selector).combobox("setValue", "");
					$(".sel_li", that.selector).combobox("loadData", data);
					//$(".sel_li", that.selector).combobox("setValue", data[0].bjdCde);
				}
			});
		},
		
		move : function(pnu){
			var that = this;
			
			that.lpPaCbndList(pnu, 1, 50, function(tagData, data, pagination) {
				that.parent.highlight(pnu, data);
			});
		},
		search : function(pageIndex){
			var that = this;
			
			var bsnsAreaNm = $('#bsnsAreaNm', that.selector).textbox('getValue');
			var pnu = '';
			var dongCode = $(".sel_dong", that.selector).combobox("getValue");
			var liCode = $(".sel_li", that.selector).combobox("getValue");
			var mntn = $(".chk_mntn", that.selector).is(":checked")?"2":"1";
			var mnnm = $(".txt_mnnm", that.selector).numberspinner("getValue");
			var slno = $(".txt_slno", that.selector).numberspinner("getValue");
			
			var isValidMnnm = false;
			if(!mnnm == '' || !mnnm == null){
				isValidMnnm = true;
				mnnm = stringUtils.lpad(mnnm, 4);
			}
			
			var isValidSlno = false;
			if(!slno == '' || !slno == null){
				isValidSlno = true;
				slno = stringUtils.lpad(slno, 4);
			}
			
			if(!dongObj.checkDongCode(liCode)){
				if(!dongLiObj.checkDongLiCode(dongCode)){
					pnu = '__________' + mntn;
					
					if(isValidMnnm && isValidSlno){
						pnu += mnnm + slno;
					}
					else if(isValidMnnm && !isValidSlno){
						pnu += mnnm + '%';
					}
					else if(!isValidMnnm && isValidSlno){
						pnu += '____' + slno;
					}
					else if(!isValidMnnm && !isValidSlno){
						if(mntn == '1'){
							pnu = '%%';
						}else{
							pnu += '%';
						}
					}
				}else{
					pnu = dongCode + '__' + mntn;
					
					if(isValidMnnm && isValidSlno){
						pnu += mnnm + slno;
					}
					else if(isValidMnnm && !isValidSlno){
						pnu += mnnm + '%';
					}
					else if(!isValidMnnm && isValidSlno){
						pnu += '____' + slno;
					}else if(!isValidMnnm && !isValidSlno){
						pnu += '%';
					}
				}
			}else{
				pnu = pnuObj.createPnuForLike(liCode, mntn, mnnm, slno);
			}
			
			//var pnu = pnuObj.createPnuForLike(code, mntn, mnnm, slno);
			that.reexmnList(pnu, bsnsAreaNm, pageIndex, 10, function(tagData, data, pagination){
				that.parent.createResult(that.selector, tagData, pagination);
			});
		},
		
		validator : function(){
			var that = this;
			
			if(!$(".sel_dong", that.selector).combobox("isValid")){
				$.messager.alert(that.TITLE, "법정동을 선택하여 주십시오.");
				return false;
			}
			return true;
		},
		
		reexmnList : function(pnu, bsnsAreaNm, pageIndex, recordCountPerPage, callback, options){
			var that=this;
			
			$.ajax({
				type:'GET',
				url : CONTEXT_PATH + '/rplotreexmn/selectList.do',
				data : {
					pnu : pnu,
					bsnsAreaNm : bsnsAreaNm,
					pageIndex : pageIndex,
					bsnsSe : '2'	// 재조사
				},
				dataType : 'json',
				success : function(result){
					var rows = result.rows;
					var data = [];
					for(var i = 0; i < rows.length; i++){
						data.push({
							id: rows[i].rplotreexmnNo,
							lpPaCbndId : null,
							pnu: rows[i].pnu,
							content : rows[i].rplotreexmnNm
						});
					}
					that.parent.createResult(that.selector, data, result.paginationInfo);
				}
			});
		},
		
		lpPaCbndList : function(pnu, pageIndex, recordCountPerPage, callback, options){
			var that = this;
			var filter = {
				filterType : "COMPARISON",
				comparisonType : "LIKE",
				property : "PNU",
				value : pnu,
				pageIndex : pageIndex,
				recordCountPerPage : recordCountPerPage,
				pageSize : 5,
				sortOrdr : "PNU"
			};
			spatialSearchUtils.list(that.TITLE, COMMON.LAND, filter, function(rows, pagination) {
				var data = [];
				for(var i=0, len=rows.length; i < len; i++) {
					var row = rows[i];
					data.push({
						id : row[COMMON.PK],
						content : pnuObj.getLocNm(row.pnu) + " " + row.jibun,
						pnu : row.pnu
					});
				}
				if(callback) {
					callback(data, rows, pagination);
				}
			}, options);
		},
		
		load : function(){
			var that = this;
			
			dongLiObj.getPromise().done(function(){
				var data = dongLiObj.getData();
				$(".sel_dong", that.selector).combobox("loadData", data);
			});
			
			/*dongObj.getPromise().done(function(){
				var data = dongObj.getData();
				$(".sel_li", that.selector).combobox("loadData", data);
			});*/
		},
		
		bindEvents : function(){
			var that = this;
			
			//읍면동에서 엔터 시 본번으로 이동
			$(".sel_dong", that.selector).combobox("textbox").keyup(function(e){
				var keyCode = e.keyCode?e.keyCode:e.which;
				if(keyCode == 13){
					$(".txt_mnnm", that.selector).numberspinner("textbox").focus();
				}
			});
			
			//본번에서 엔터 시 부번으로 이동
			$(".txt_mnnm", that.selector).numberspinner("textbox").keyup(function(e){
				var keyCode = e.keyCode?e.keyCode:e.which;
				if(keyCode == 13){
					$(".txt_slno", that.selector).numberspinner("textbox").focus();
				}
			});
			
			// 부번에서 엔터 시 검색
			$(".txt_slno", that.selector).numberspinner("textbox").keyup(function(e) {
				var keyCode = e.keyCode ? e.keyCode : e.which;
				if (keyCode == 13) {
					$(".a_search", that.selector).trigger("click");
				}
			});
			
			//검색
			$(".a_search", that.selector).click(function(){
				//if(that.validator()){
					that.search(1);
				//}
				return false;
			});
			
			//위치 이동
			$(that.selector).on("click", ".a_move", function(){
				var element = $(this);
				var id = element.attr("data-pnu");
				that.move(id);
				return false;
			});
			
			//페이징 이동
			$(that.selector).on("click",".div_search_pagination a", function(){
				var element = $(this);
				var pageIndex = element.attr("data-page-index");
				that.search(pageIndex);
			});
			
			//상세 정보
			$(that.selector).on("click",".a_detail", function(){
				var element = $(this);
				var reexmnNo = element.attr("data-id");
				that.selectOne.open(reexmnNo);
			});
			
			//초기화
			$(".a_reset", that.selector).click(function(){
				that.reset();
			});
		},
		
		reset : function(){
			var that = this;
			
			$(".sel_dong", that.selector).combobox("setValue","");
			$(".sel_li", that.selector).combobox("setValue","");
			$(".chk_mntn", that.selector).prop("checked",false);
			$(".txt_mnnm", that.selector).numberspinner("setValue","");
			$(".txt_slno", that.selector).numberspinner("setValue","");
			$('#bsnsAreaNm', that.selector).textbox('clear');
		}
};

menuObj.lgstrBizObj.reexmnObj.selectOne = {
			selector : "#div_reexmn_select",
			
			createSelector : null,
			
			TITLE : "재조사 상세정보",
			
			CLASS_NAME : "reexmnSelect",
			
			isCreated : false,
			
			reexmnNo : 0,
			
			open : function(reexmnNo){
				var that = this;
				
				if(that.selector){
					that.close();
				}
				
				var windowOptions = {
						width:700,
						height:390,
						top:150,
						left:550,
						onClose:function(){
							that.close();
						}
				};
				
				var url = CONTEXT_PATH + '/rplotreexmn/reexmnSelectOnePage.do';
				
				var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function(){
					if(!that.isCreated){
						that.reexmnNo = reexmnNo;
						that.load();
						that.bindEvents();
						that.isCreated = true;
					}
				});
				
				that.createSelector = '#'+id;
				
				
			},
			
			load : function(){
				var that = this;
				
				var url = CONTEXT_PATH + '/rplotreexmn/'+ that.reexmnNo +'/selectOne.do';
				
				$.ajax({
					type:'GET',
					dataType : 'json',
					url:url,
					success:function(rs){
						var rpx = rs.rplotreexmn;
						var rpxLgstr = rs.rplotreexmnLgstr;
						
						for(var i = 0; i < Object.keys(rpx).length; i++){
							var key = Object.keys(rpx)[i];
							var tag = $("#detail_"+key);
							
							 if(key == 'bsnsSe'){
		                        	if(rpx[key] == '1'){
		                        		tag.html('환지정리');
		                        	}else{
		                        		tag.html('지적재조사');
		                        	}
		                        }else{
		                        	tag.html(rpx[key]);
		                        }
						}
						
						for(var i = 0; i < rpxLgstr.length; i++){
							
							if(rpxLgstr[i].brftrSe == '1'){
								for(var j = 0; j < Object.keys(rpxLgstr[i]).length; j++){
									var key = Object.keys(rpxLgstr[i])[j];
									
									if(key == 'pnu'){
										if(rpxLgstr[i][key] != '' && rpxLgstr[i][key] != null){
											 var pnu = menuObj.lgstrBizObj.jibunByPnu(rpxLgstr[i][key]);

											    if(pnu.substr(pnu.length-2,2) == '-0'){
			            	                        pnu = pnu.replace('-0','');
			                                    }
												
											$("#detail_bfr_jibun").html(pnu);
										}
									}else if(key == 'rplotLndcgr'){
										if(rpxLgstr[i][key] != '' && rpxLgstr[i][key] != null){
											$("#detail_bfr_"+key).html(menuObj.lgstrBizObj.getLndcgrCodeNm(rpxLgstr[i][key]));
										}
									}else{
										$("#detail_bfr_"+key).html(rpxLgstr[i][key]);
									}
									
								}
							}else{
								for(var j = 0; j < Object.keys(rpxLgstr[i]).length; j++){
									var key = Object.keys(rpxLgstr[i])[j];
									
									if(key == 'rplotLndcgr'){
										if(rpxLgstr[i][key] != '' && rpxLgstr[i][key] != null){
											$("#detail_aft_"+key).html(menuObj.lgstrBizObj.getLndcgrCodeNm(rpxLgstr[i][key]));
										}
									}else{
										$("#detail_aft_"+key).html(rpxLgstr[i][key]);
									}
								}
							}
						}
					}
				});
			},
			
			bindEvents : function(){
				var that = this;
				
				$("#a_close_reexmn").click(function(){
					that.close();
				});
			},
			
			close : function(){
				var that = this;
				
				windowObj.removeWindow(that.createSelector);
				that.createSelector = null;
				that.isCreated = false;
			}
	};
	
menuObj.lgstrBizObj.iprvarObj = {
	selector : "#div_menu_panel_search_iprvar",

	parent : null,

	TITLE : "정비보류지역 관리조서",

	data : null,

	init : function(parent) {
		var that = this;
		
		that.parent = parent;
		
		that.initUi();
		that.bindEvents();
		that.load();
	},
	
	initUi : function(){
		var that = this;
		
		$(".sel_dong", that.selector).combobox({
			required : false,
			valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONGLI_CODE_FIELD),
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONGLI_NAME_FIELD),
			width : 155,
			onSelect : function(record){
				that.searchLi(record.emdCd);
			}
		});
		
		$(".sel_li", that.selector).combobox({
			required : false,
			valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
			textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD),
			width : 155
		});
		
		$(".txt_mnnm", that.selector).numberspinner({
			min : 1,
			max : 9999,
			increment : 1,
			width : 155
		});
		
		$(".txt_slno", that.selector).numberspinner({
			min : 0,
			max : 9999,
			increment : 1,
			width : 155
		});
		
		$(".a_reset", that.selector).linkbutton({
			iconCls : "icon-reload"
		});
		
		$(".a_search", that.selector).linkbutton({
			iconCls : "icon-search"
		});
		
		$(".a_register", that.selector).linkbutton({
			iconCls : "icon-add"
		});
	},
	
	bindEvents : function(){
		var that = this;
		
		//읍면동에서 엔터 시 본번으로 이동
		$(".sel_dong", that.selector).combobox("textbox").keyup(function(e){
			var keyCode = e.keyCode?e.keyCode:e.which;
			if(keyCode == 13){
				$(".txt_mnnm", that.selector).numberspinner("textbox").focus();
			}
		});
		
		//본번에서 엔터 시 부번으로 이동
		$(".txt_mnnm", that.selector).numberspinner("textbox").keyup(function(e){
			var keyCode = e.keyCode?e.keyCode:e.which;
			if(keyCode == 13){
				$(".txt_slno", that.selector).numberspinner("textbox").focus();
			}
		});
		
		// 부번에서 엔터 시 검색
		$(".txt_slno", that.selector).numberspinner("textbox").keyup(function(e) {
			var keyCode = e.keyCode ? e.keyCode : e.which;
			if (keyCode == 13) {
				$(".a_search", that.selector).trigger("click");
			}
		});
		
		//검색
		$(".a_search", that.selector).click(function(){
			//if(that.validator()){
				that.search(1);
				//quickObj.triggerOnLand();
			//}
			return false;
		});
		
		//위치 이동
		$(that.selector).on("click", ".a_move", function(){
			var element = $(this);
			var pnu = element.attr("data-pnu");
			that.move(pnu);
			
			return false;
		});
		
		//페이징 이동
		$(that.selector).on("click",".div_search_pagination a", function(){
			var element = $(this);
			var pageIndex = element.attr("data-page-index");
			that.search(pageIndex);
		});
		
		//상세정보
		$(that.selector).on("click",".a_detail",function(){
			var element = $(this);
			var id = element.attr("data-id");
			that.selectOne.open(id);
		});
		
		//등록
		$(that.selector).on("click",".a_register",function(){
			that.insert.open();
		});
		
		//초기화
		$(".a_reset", that.selector).click(function(){
			that.reset();
		});
	},
	
	reset : function(){
		var that = this;
		
		$(".sel_dong", that.selector).combobox("setValue","");
		$(".sel_li", that.selector).combobox("setValue","");
		$(".chk_mntn", that.selector).prop("checked",false);
		$(".txt_mnnm", that.selector).numberspinner("setValue","");
		$(".txt_slno", that.selector).numberspinner("setValue","");
	},
	
	move : function(pnu){
		var that = this;
		
		that.lpPaCbndList(pnu, 1, 50, function(tagData, data, pagination) {
			that.parent.highlight(pnu, data);
		});
	},
	
	load : function(){
		var that = this;
		dongLiObj.getPromise().done(function(){
			var data = dongLiObj.getData();
			$(".sel_dong", that.selector).combobox("loadData", data);
		});
		
		/*dongObj.getPromise().done(function(){
			var data = dongObj.getData();
			$(".sel_li", that.selector).combobox("loadData", data);
		});*/
	},
	
	validator : function(){
		var that = this;
		
		if(!$(".sel_dong", that.selector).combobox("isValid")){
			$.messager.alert(that.TITLE, "법정동을 선택하여 주십시오.");
			return false;
		}
		
		return true;
	},
	
	searchLi : function(emdCode){
		var that = this;
			var filter = {
					filterType : "COMPARISON",
					comparisonType : "LIKE",
					property : "BJD_CDE",
					value : emdCode+"%",
					sortOrdr : "BJD_NAM",
					isOriginColumnValue : true
			};
			
			spatialSearchUtils.listAll(that.TITLE, liObj.getDataId(), filter, function(data) {
				if(data) {
					$(".sel_li", that.selector).combobox("setValue", "");
					$(".sel_li", that.selector).combobox("loadData", data);
					//$(".sel_li", that.selector).combobox("setValue", data[0].bjdCde);
				}
			});
	},
	
	search : function(pageIndex){
		var that = this;
		
		var pnu = '';
		var dongCode = $(".sel_dong", that.selector).combobox("getValue");
		var liCode = $(".sel_li", that.selector).combobox("getValue");
		var mntn = $(".chk_mntn", that.selector).is(":checked")?"2":"1";
		var mnnm = $(".txt_mnnm", that.selector).numberspinner("getValue");
		var slno = $(".txt_slno", that.selector).numberspinner("getValue");
		
		var isValidMnnm = false;
		if(!mnnm == '' || !mnnm == null){
			isValidMnnm = true;
			mnnm = stringUtils.lpad(mnnm, 4);
		}
		
		var isValidSlno = false;
		if(!slno == '' || !slno == null){
			isValidSlno = true;
			slno = stringUtils.lpad(slno, 4);
		}
		
		if(!dongObj.checkDongCode(liCode)){
			if(!dongLiObj.checkDongLiCode(dongCode)){
				pnu = '__________' + mntn;
				
				if(isValidMnnm && isValidSlno){
					pnu += mnnm + slno;
				}else if(isValidMnnm && !isValidSlno){
					pnu += mnnm + '%';
				}else if(!isValidMnnm && isValidSlno){
					pnu += '____' + slno;
				}else if(!isValidMnnm && !isValidSlno){
					if(mntn == '1'){
						pnu = '%%';
					}else{
						pnu += '%';
					}
				}
			}else{
				pnu = dongCode + '__' + mntn;
				
				if(isValidMnnm && isValidSlno){
					pnu += mnnm + slno;
				}else if(isValidMnnm && !isValidSlno){
					pnu += mnnm + '%';
				}else if(!isValidMnnm && isValidSlno){
					pnu += '____' + slno;
				}else if(!isValidMnnm && !isValidSlno){
					pnu += '%';
				}
			}
		}else{
			pnu = pnuObj.createPnuForLike(liCode, mntn, mnnm, slno);
		}
		
		that.iprvarList(pnu, pageIndex, 10, function(tagData, data, pagination){
			that.parent.createResult(that.selector, tagData, pagination);
		});
	},
	
	lpPaCbndList : function(pnu, pageIndex, recordCountPerPage, callback, options) {
		var that = this;
		var filter = {
			filterType : "COMPARISON",
			comparisonType : "LIKE",
			property : "PNU",
			value : pnu,
			pageIndex : pageIndex,
			recordCountPerPage : recordCountPerPage,
			pageSize : 5,
			sortOrdr : "PNU"
		};
		spatialSearchUtils.list(that.TITLE, COMMON.LAND, filter, function(rows, pagination) {
			var data = [];
			for(var i=0, len=rows.length; i < len; i++) {
				var row = rows[i];
				data.push({
					id : row[COMMON.PK],
					content : pnuObj.getLocNm(row.pnu) + " " + row.jibun,
					pnu : row.pnu
				});
			}
			if(callback) {
				callback(data, rows, pagination);
			}
		}, options);
	},
	
	
	
	iprvarList : function(pnu, pageIndex, recordCountPerPage, callback, options){
		var that=this;
		
		$.ajax({
			type:'GET',
			url : CONTEXT_PATH + '/iprvar/selectList.do',
			data : {
				pnu : pnu,
				pageIndex : pageIndex
			},
			dataType : 'json',
			success : function(result){
				var rows = result.rows;
				var data = [];
				for(var i = 0; i < rows.length; i++){
					var lndcgrCd = rows[i].rplotLndcgr;
					var lndcgrNm = '';
					if(lndcgrCd != 'undefined' && lndcgrCd != null && lndcgrCd != ''){
						lndcgrNm = that.parent.getLndcgrCodeNm(rows[i].iprvarLndcgr);
					}
					
					data.push({
						id: rows[i].iprvarNo,
						lpPaCbndId : null,
						pnu: rows[i].pnu,
						content : that.parent.jibunByPnu(rows[i].pnu) + lndcgrNm
					});
				}
				that.parent.createResult(that.selector, data, result.paginationInfo);
			}
		});
	}
};

menuObj.lgstrBizObj.iprvarObj.selectOne = {
	
		TITLE : "정비보류지역 관리조서 상세정보",
		
		CLASS_NAME : "iprvarSelect",
		
		selector : "#div_iprvar_select",
		
		createSelector : null,
		
		iprvarNo : 0,
		
		isCreated : false,
		
		height : 432,
		
		open : function(iprvarNo){
			var that = this;
			
			if(that.selector){
				that.close();
			}
			
			var url = CONTEXT_PATH + "/iprvar/selectOnePage.do";
			
			var windowOptions = {
				width : 700,
				height : that.height,
				top : 150,
				left : 550,
				onClose : function(){
					that.close();
				}
			};
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function(){
				if(!that.isCreated){
					that.iprvarNo = iprvarNo;
					that.load(iprvarNo);
					that.bindEvents();
					that.isCreated = true;
				}
			});
			
			that.createSelector = "#"+id;
			
		},
		
		load : function(iprvarNo){
			var that = this;
			
			var url = CONTEXT_PATH + "/iprvar/" + iprvarNo + "/selectOne.do";
			
			$.ajax({
				type:'GET',
				dataType:'json',
				url:url,
				success:function(result){
					var row = result.row;
					var iprvarFiles = result.iprvarFiles;
					
					var pnu = menuObj.lgstrBizObj.jibunByPnu(row.pnu);
					
					if(pnu.substr(pnu.length-2,2) == '-0'){
		            	pnu = pnu.replace('-0','');
		            }
					
					var pnuSpl = pnu.split(' ');
					var jibun = '';
					
					if(pnuSpl[2] != '산'){
							jibun = pnuSpl[2];
					}else{
						jibun = pnuSpl[2] + ' ' + pnuSpl[3];
					}
					
					var lndcgrNm = menuObj.lgstrBizObj.getLndcgrCodeNm(row.iprvarLndcgr);
					
					$("#iprvarNo").val(row.iprvarNo);
					$("#detail_emd").html(pnuSpl[0]);
					$("#detail_li").html(pnuSpl[1]);
					$("#detail_jibun").html(jibun);
					$("#detail_lndcgr").html(lndcgrNm);
					$("#detail_area").html(row.iprvarAr);
					$("#detail_scale").html(row.iprvarSc);
					$("#detail_iem").html(row.updtIem);
					$("#detail_doho").html(row.iprvarDoho);
					$("#detail_why").html(row.iprvarWhy);
					$("#detail_rmkExp").html(row.rmkExp);
					
					that.fileList(iprvarFiles);
					
				}
			});
		},
		
		fileList : function(iprvarFiles){
			var that = this;
			
			if(iprvarFiles){
				
				var tagStr = '';
				
				var styleStr = '';
				
				if(iprvarFiles.length > 4){
					styleStr = 'width:571px; word-break:break-all; text-align:center; padding-left:27px;';
				}else{
					styleStr = 'width:571px; word-break:break-all; text-align:center; padding:0px 27px 0px 30px;';
				}
				
				for(var i = 0; i < iprvarFiles.length; i++){
					tagStr += '<tr iprvarFileNo = "'+ iprvarFiles[i].iprvarFileNo +'" fileNo = "'+ iprvarFiles[i].fileNo +'" style="height:36px;">';
					tagStr += '<td style="width:72px; text-align:center; padding:7px 7px 7px 9px; border-right:1px solid #d7d7d7">';
					tagStr += '<input type="checkbox" class="iprvarFile_chkBox">';
					tagStr += '</td>';
					tagStr += '<td style="'+ styleStr +'">';
					tagStr += '<a href="#" class="iprvarFileSj">';
					tagStr += iprvarFiles[i].iprvarFileSj;
					tagStr += '</a>';
					tagStr += '</td>';
					tagStr += '</tr>';
				}
				
				if(iprvarFiles.length > 4){
					$(that.createSelector).css("height", that.height + (4 * 38) + "px");
				}else{
					$(that.createSelector).css("height", that.height + (iprvarFiles.length * 40) + "px");
				}
				
				$("#fileTBody", that.selector).html(tagStr);
			}
		},
		
		bindEvents : function(){
			var that = this;
			
			//정비보류지역 관리조서 삭제
			$("#a_remove_iprvar").click(function(){
				$.messager.confirm("정비보류지역 관리조서 삭제","삭제하시겠습니까?",function(rs){
					if(rs){
						that.remove();
					}
				});
			});
			
			//정비보류지역 관리조서 편집
			$("#a_update_iprvar").click(function(){
				that.close();
				menuObj.lgstrBizObj.iprvarObj.update.open(that.iprvarNo);
			});
			
			//정비보류지역 관리조서 등록
			$(that.selector).on("click",".a_register",function(){
				that.insert.open();
			});
			
			//정비보류지역 관리조서 닫기
			$("#a_close_iprvar").click(function(){
				that.close();
			});
			
			//정비보류지역 관리조서 부속자료 등록
			$("#a_add_iprvar_file", that.selector).click(function(){
				menuObj.lgstrBizObj.iprvarObj.insertFile.open(that.iprvarNo);
			});
			
			//정비보류지역 관리조서 부속자료 전체 체크/전체 체크 해제
			$("#allChk").change(function(){
				var chkVal = $("#allChk").is(":checked");
				
				$(".iprvarFile_chkBox").prop("checked", chkVal);
			});
			
			$(that.selector).on("change",".iprvarFile_chkBox", function(){
				var chkBoxes = $(".iprvarFile_chkBox");
                var chkedLength = 0;

                for(var i = 0; i < chkBoxes.length; i++){
                	if(chkBoxes[i].checked){
                		chkedLength ++ ;
                	}
                }

                if(chkBoxes.length == chkedLength){
                	$("#allChk").prop("checked", true);
                }else{
                	$("#allChk").prop("checked", false);
                }
			});
			
			//정비보류지역 관리조서 부속자료 삭제
			$("#a_remove_iprvar_file").click(function(){
				var chkedBoxes = $("input:checkbox[class=iprvarFile_chkBox]:checked");
				
				$.messager.confirm('정비보류지역 관리조서 부속자료 삭제', '부속자료 '+ chkedBoxes.length +'개를 삭제하시겠습니까?', function(r){
					if(r){
						that.fileRemove(chkedBoxes);
					}
				});
			});
			
			//정비보류지역 관리조서 부속자료 내려받기
			$("#a_down_iprvar_file").click(function(){
				var chkedBoxes = $("input:checkbox[class=iprvarFile_chkBox]:checked");
				that.fileDownload(chkedBoxes);
			});
			
			//정비보류지역 관리조서 부속자료 미리보기
			$(that.selector).on("click",".iprvarFileSj",function(){
				that.preview($(this));
			});
		},
		
		fileDownload : function(chkedBoxes){
			var chkArr = [];
			
			if(chkedBoxes){
				for(var i = 0; i < chkedBoxes.length; i++){
					var fileNo = $("input:checkbox[class=iprvarFile_chkBox]:checked").eq(i).parent().parent().attr("fileNo");
					chkArr.push(fileNo);
				}
				
				var url = CONTEXT_PATH + '/iprvar/iprvarFileDownload.do?chkArr=' + chkArr;
				location.href = url;
				
			}else{
				$.message.alert('정비보류지역 관리조서 부속자료 내려받기','부속자료를 하나 이상 선택 해주세요.');
			}
		},
		
		preview : function(aTag){
			var fileNo = aTag.parent().parent().attr("fileNo");
			var url = CONTEXT_PATH + '/iprvar/' + fileNo + '/iprvarFileChk.do';
			
			$.get(url).done(function(response){
				if(response){
					var fileExtsn = response.row.fileExtsn.toLowerCase();
					
					if(fileExtsn == "pdf"){
						window.open(CONTEXT_PATH+'/iprvar/'+ fileNo +'/iprvarPdfPreview.do');
					}else{
						window.open(CONTEXT_PATH+'/popup/image/view.do?imageFileNo='+fileNo);
					}
				}else{
					$.messager.alert("정비보류지역 관리조서 부속자료 미리보기","미리보기에 실패하였습니다.");
				}
			});
		},
		
		remove : function(){
			var that = this;
			
			url = CONTEXT_PATH + "/iprvar/delete.do";
			
			$.ajax({
				type:'POST',
				data : {
					iprvarNo : that.iprvarNo
				},
				url : url,
				dataType : 'json',
				success : function(rs){
					if(rs.result > 0){
						$.messager.alert("정비보류지역 관리조서 삭제","삭제 되었습니다.");
						that.close();
						$(".a_search", "#div_menu_panel_search_iprvar").trigger("click");
					}
				}
			});
			
		},
		
		fileRemove : function(chkedBoxes){
			var that = this;
			
			var fileNos = [];
			var iprvarFileNos = [];
			
			for(var i = 0; i < chkedBoxes.length; i++){
				
				var chkBox = $("input:checkbox[class=iprvarFile_chkBox]:checked").eq(i).parent().parent();
				
				var iprvarFileNo = chkBox.attr("iprvarFileNo");
				iprvarFileNos.push(iprvarFileNo);
				
				var fileNo = chkBox.attr("fileNo");
				fileNos.push(fileNo);
			}
			
			var url = CONTEXT_PATH + '/iprvar/fileDelete.do';
			
			$.ajax({
				type:'POST',
				data:{
					fileNos : fileNos,
					iprvarFileNos : iprvarFileNos
				},
				dataType:'json',
				url:url,
				success:function(rs){
					$.messager.alert('정비보류지역 관리조서 부속자료 삭제','부속자료 '+ rs.result +'개가 삭제되었습니다.', null, function(){
						that.open(that.iprvarNo);
					});
				}
			});
			
			
		},
		
		close : function(){
			var that = this;
			
			windowObj.removeWindow(that.createSelector);
			that.createSelector = null;
			that.isCreated = false;
		}
		
};

menuObj.lgstrBizObj.iprvarObj.update = {
		TITLE : "정비보류지역 관리조서 수정",
		
		CLASS_NAME : "iprvarUpdate",
		
		isCreated : false,
		
		iprvarNo : 0,
		
		selector : "#div_iprvar_update",
		
		createSelector : null,
		
		open : function(iprvarNo){
			var that = this;
			
			var url = CONTEXT_PATH + "/iprvar/" + iprvarNo + "/updatePage.do";
			
			var windowOptions = {
					width : 700,
					height : 410,
					top : 150,
					left : 550,
					onClose : function(){
						that.close();
					}
				};
				
				var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function(){
					if(!that.isCreated){
						that.iprvarNo = iprvarNo;
						that.initUi();
						that.load(iprvarNo);
						that.init();
						that.bindEvents();
						that.isCreated = true;
					}
				});
				
				that.createSelector = "#" + id;
		},
		
		init : function(){
			var that = this;
			
			$("form", that.selector).ajaxForm({
				dataType : 'json',
				beforeSubmit : function(arr, form){
					var selDong = $(".sel_dong", that.selector).combobox("getValue");
					var dongCode = $(".sel_li", that.selector).combobox("getValue");
					var mnnm = $(".txt_mnnm", that.selector).numberspinner("getValue");
					var iprvarAr = $("input[name=iprvarAr]", that.selector).val();
					var iprvarSc = $("input[name=iprvarSc]", that.selector).val();
					
					if(selDong == ''){
						$.messager.alert(that.TITLE, "읍면동을 선택해주세요.");
						return false;
					}else if(dongCode == ''){
						$.messager.alert(that.TITLE, "리를 선택해주세요.");
						return false;
					}else if(mnnm == ''){
						$.messager.alert(that.TITLE, "본번을 입력해주세요.");
						return false;
					}else if(iprvarAr != '' && isNaN(iprvarAr)){
						$.messager.alert(that.TITLE, "면적을 정확히 입력해주세요.");
						return false;
					}else if(iprvarSc != '' && isNaN(iprvarSc)){
						$.messager.alert(that.TITLE, "축척을 정확히 입력해주세요.");
						return false;
					}
				},
				success : function(result){
					$.messager.alert({
						title:that.TITLE,
						msg:"정비보류지역 관리조서가 편집되었습니다.",
						fn:function(){
							var iprvarNo = that.iprvarNo;
							that.close();
							$(".a_search", "#div_menu_panel_search_iprvar").trigger("click");
							menuObj.lgstrBizObj.iprvarObj.selectOne.open(iprvarNo);
						}
					});
					
				}
			});
		},
		
		update : function(){
			var that = this;
			
			var dongCode = $(".sel_li", that.selector).combobox("getValue");
			var mntn = $("input[id=chk_mntn]").is(":checked")?"2":"1";
			var mnnm = $(".txt_mnnm", that.selector).numberspinner("getValue");
			var slno = $(".txt_slno", that.selector).numberspinner("getValue");
			
			var pnu = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
			$("input[name=pnu]").val(pnu);
			
			var url = CONTEXT_PATH + '/iprvar/update.do';
			$("form", that.selector).attr("action",url);
			$("form", that.selector).submit();
		},
		
		initUi : function(){
			var that = this;
			
			// 법정동 목록
			$(".sel_dong", that.selector).combobox({
				required : true,
				valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONGLI_CODE_FIELD),
				textField : camelcaseUtils.underbarToCamelcase(COMMON.DONGLI_NAME_FIELD),
				width : 155,
				onSelect : function(record){
					that.searchLi(record.emdCd);
				}
			});
			
			// 리 목록
			$(".sel_li", that.selector).combobox({
				required : true,
				valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
				textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD),
				width : 155
			});
			
			
			// 지목 목록
			$(".sel_lndcgr", that.selector).combobox({
				required : true,
				width : 155,
				valueField : 'codeId',
				textField : 'codeNm'
			});
			
			// 본번
			$(".txt_mnnm", that.selector).numberspinner({
				min : 1,
				max : 9999,
				increment : 1,
				width : 155
			});
			
			// 부번
			$(".txt_slno", that.selector).numberspinner({
				min : 0,
				max : 9999,
				increment : 1,
				width : 155
			});
			
			$("input:text[class=inputTxt]", that.selector).textbox({
				width:160
			});
			
			$("input:text[class=inputLongTxt]", that.selector).textbox({
				width:488
			});
			
			$("textarea[class=txtAr]", that.selector).textbox({
				width:488,
				height:80
			});
		},
		
		load : function(iprvarNo){
			var that = this;
			
			//읍면동
			dongLiObj.getPromise().done(function(){
				var data = dongLiObj.getData();
				$(".sel_dong", that.selector).combobox("loadData", data);
			});
			
			//리
			dongObj.getPromise().done(function(){
				var data = dongObj.getData();
				$(".sel_li", that.selector).combobox("loadData", data);
			});
			
			//지목
			$(".sel_lndcgr", that.selector).combobox("loadData", menuObj.lgstrBizObj.lndcgrList);
			
			var url = CONTEXT_PATH + "/iprvar/" + iprvarNo + "/selectOne.do";
			
			$.ajax({
				type : 'GET',
				dataType : 'json',
				url : url,
				success : function(rs){
					var row = rs.row;
					
					var pnu = row.pnu;
					
					$(".sel_dong", that.selector).combobox("setValue", pnu.substring(0,8));
					$(".sel_li", that.selector).combobox("setValue", pnu.substring(0,10));
					$(".sel_lndcgr", that.selector).combobox("setValue", row.iprvarLndcgr);
					
					if(pnu.substring(10,11) == "2"){
						$("#chk_mntn", that.selector).attr("checked",true);
					}
					
					$(".txt_mnnm", that.selector).numberspinner("setValue", parseInt(pnu.substring(11,15)));
					$(".txt_slno", that.selector).numberspinner("setValue", parseInt(pnu.substring(15,19)));
					
					$("input[name=iprvarNo]", that.selector).val(row.iprvarNo);
					
					
					$("#iprvarAr", that.selector).textbox("setValue",row.iprvarAr);
					$("#iprvarSc", that.selector).textbox("setValue",row.iprvarSc);
					$("#updtIem", that.selector).textbox("setValue",row.updtIem);
					$("#iprvarDoho", that.selector).textbox("setValue",row.iprvarDoho);
					$("#iprvarWhy", that.selector).textbox("setValue",row.iprvarWhy);
					$("#rmkExp", that.selector).textbox("setValue",row.rmkExp);
				}
			});
		},
		
		searchLi : function(emdCode){
			var that = this;
			var chkVal = false;
			
				var filter = {
						filterType : "COMPARISON",
						comparisonType : "LIKE",
						property : "BJD_CDE",
						value : emdCode+"%",
						sortOrdr : "BJD_NAM",
						isOriginColumnValue : true
				};
				
				spatialSearchUtils.listAll(that.TITLE, liObj.getDataId(), filter, function(data) {
					if(data) {
						$(".sel_li", that.selector).combobox("loadData", data);
						
						var selLi = $(".sel_li", that.selector).combobox("getValue");
						
						for(var i = 0; i < data.length; i++){
							if(data[i].bjdCde == selLi){
								chkVal = true;
							}
						}
						
						if(!chkVal){
							$(".sel_li", that.selector).combobox("setValue", data[0].bjdCde);
						}else{
							$(".sel_li", that.selector).combobox("setValue", selLi);
						}
						
						
					}
				});
		},
		
		bindEvents : function(){
			var that = this;
			
			$("#a_close_iprvar").click(function(){
				that.close();
			});
			
			$("#a_update_iprvar").click(function(){
				that.update();
			});
			
		},
		
		close : function(){
			var that = this;
			
			windowObj.removeWindow(that.createSelector);
			that.createSelector = null;
			that.iprvarNo = 0;
			that.lndcgrList = null;
			that.isCreated = false;
		}
};

menuObj.lgstrBizObj.iprvarObj.insert = {
		
		TITLE : "정비보류지역 관리조서 등록",
		
		CLASS_NAME : "iprvarInsert",
			
		isCreated : false,
			
		selector : "#div_iprvar_insert",
			
		createSelector : null,
			
		open : function(){
			var that = this;
			
			var url = CONTEXT_PATH + "/iprvar/insertPage.do";
			
			var windowOptions = {
					width : 700,
					height : 410,
					top : 150,
					left : 550,
					onClose : function(){
						that.close();
					}
			};
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function(){
				if(!that.isCreated){
					that.initUi();
					that.load();
					that.init();
					that.bindEvents();
					that.isCreated = true;
				}
			});
			
			that.createSelector = "#"+id;
		},
		
		init : function(){
			var that= this;
			
			$("form", that.selector).ajaxForm({
				dataType : 'json',
				beforeSubmit : function(arr, form){
					var selDong = $(".sel_dong", that.selector).combobox("getValue");
					var dongCode = $(".sel_li", that.selector).combobox("getValue");
					var mnnm = $(".txt_mnnm", that.selector).numberspinner("getValue");
					var iprvarAr = $("input[name=iprvarAr]", that.selector).val();
					var iprvarSc = $("input[name=iprvarSc]", that.selector).val();
					
					if(selDong == ''){
						$.messager.alert(that.TITLE, "읍면동을 선택해주세요.");
						return false;
					}else if(dongCode == ''){
						$.messager.alert(that.TITLE, "리를 선택해주세요.");
						return false;
					}else if(mnnm == ''){
						$.messager.alert(that.TITLE, "본번을 입력해주세요.");
						return false;
					}else if(iprvarAr != '' && isNaN(iprvarAr)){
						$.messager.alert(that.TITLE, "면적을 정확히 입력해주세요.");
						return false;
					}else if(iprvarSc != '' && isNaN(iprvarSc)){
						$.messager.alert(that.TITLE, "축척을 정확히 입력해주세요.");
						return false;
					}
				},
				success : function(result){
					$.messager.alert(that.TITLE, "정비보류지역 관리조서가 등록되었습니다.");
					that.close();
				}
			});
		},
		
		initUi : function(){
			var that = this;
			
			// 법정동 목록
			$(".sel_dong", that.selector).combobox({
				required : true,
				valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONGLI_CODE_FIELD),
				textField : camelcaseUtils.underbarToCamelcase(COMMON.DONGLI_NAME_FIELD),
				width : 155,
				onSelect : function(record){
					that.searchLi(record.emdCd);
				}
			});
			
			// 리 목록
			$(".sel_li", that.selector).combobox({
				required : true,
				valueField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_CODE_FIELD),
				textField : camelcaseUtils.underbarToCamelcase(COMMON.DONG_NAME_FIELD),
				width : 155
			});
			
			
			// 지목 목록
			$(".sel_lndcgr", that.selector).combobox({
				required : true,
				width : 155,
				valueField : 'codeId',
				textField : 'codeNm'
			});
			
			// 본번
			$(".txt_mnnm", that.selector).numberspinner({
				min : 1,
				max : 9999,
				increment : 1,
				width : 155
			});
			
			// 부번
			$(".txt_slno", that.selector).numberspinner({
				min : 0,
				max : 9999,
				increment : 1,
				width : 155
			});
			
			$("input:text[class=inputTxt]", that.selector).textbox({
				width:160
			});
			
			$("input:text[class=inputLongTxt]", that.selector).textbox({
				width:488
			});
			
			$("textarea[class=txtAr]", that.selector).textbox({
				width:488,
				height:80
			});
			
		},
		load : function(){
			var that = this;
			
			//읍면동
			dongLiObj.getPromise().done(function(){
				var data = dongLiObj.getData();
				$(".sel_dong", that.selector).combobox("loadData", data);
			});
			
			//리
			dongObj.getPromise().done(function(){
				var data = dongObj.getData();
				$(".sel_li", that.selector).combobox("loadData", data);
			});
			
			//지목
			$(".sel_lndcgr", that.selector).combobox("loadData", menuObj.lgstrBizObj.lndcgrList);
		},
		searchLi : function(emdCode){
			var that = this;
				var filter = {
						filterType : "COMPARISON",
						comparisonType : "LIKE",
						property : "BJD_CDE",
						value : emdCode+"%",
						sortOrdr : "BJD_NAM",
						isOriginColumnValue : true
				};
				
				spatialSearchUtils.listAll(that.TITLE, liObj.getDataId(), filter, function(data) {
					if(data) {
						$(".sel_li", that.selector).combobox("loadData", data);
						$(".sel_li", that.selector).combobox("setValue", data[0].bjdCde);
					}
				});
		},
		
		bindEvents : function(){
			var that = this;
			
			$("#a_insert_iprvar").click(function(){
				that.insert();
			});
			
			$("#a_close_iprvar").click(function(){
				that.close();
			});
		},
		
		insert : function(){
			var that = this;
			
			var dongCode = $(".sel_li", that.selector).combobox("getValue");
			var mntn = $("input[id=chk_mntn]").is(":checked")?"2":"1";
			var mnnm = $(".txt_mnnm", that.selector).numberspinner("getValue");
			var slno = $(".txt_slno", that.selector).numberspinner("getValue");
			
			var pnu = pnuObj.createPnu(dongCode, mntn, mnnm, slno);
			$("input[name=pnu]").val(pnu);
			
			var url = CONTEXT_PATH + '/iprvar/insert.do';
			$("form", that.selector).attr("action",url);
			$("form", that.selector).submit();
		},
		
		close : function(){
			var that = this;
			
			windowObj.removeWindow(that.createSelector);
			that.createSelector = null;
			that.lndcgrList = null;
			that.isCreated = false;
		}
};

menuObj.lgstrBizObj.iprvarObj.insertFile = {
		
		TITLE : '정비보류지역 관리조서 부속자료 등록',
		
		CLASS_NAME : 'iprvarFileAdd',
		
		selector : '#div_iprvar_file_insert',
		
		createSelector : null,
		
		isCreated : false,
		
		iprvarNo : 0,
		
		open : function(iprvarNo){
			var that = this;
			
			var url = CONTEXT_PATH + "/iprvar/" +iprvarNo + "/fileInsertPage.do";
			var windowOptions = {
				width : 415,
				height : 163,
				onClose : function(){
					that.close();
				}
			};
			
			if(that.isCreated){
				that.close();
			}
			
			var id = windowObj.createWindow(that.CLASS_NAME, that.TITLE, url, null, windowOptions, function(){
				if(!that.isCreated){
					that.init();
					that.initUi();
					that.bindEvents();
					that.isCreated = true;
				}
			});
			
			that.createSelector = "#"+id;
		},
		
		init : function(){
			var that = this;
			
			$("form", that.selector).ajaxForm({
				dataType : "json",
				beforeSubmit : function(arr, form){
					for(var i = 0; i < arr.length; i++){
						var obj = arr[i];
						var name = obj.name;
						
						if(name == 'iprvarFileSj'){
							if(!obj.value){
								$.messager.alert(that.TITLE, '제목을 입력하여 주십시오.', null, function(){
									form.find("input[name=" + name + "]").focus();
								});
								return false;
							}
						}else if(name == 'fileNm'){
							if(obj.value){
								var fileNm = obj.value.name;
								
								var extension = fileNm.substring(fileNm.lastIndexOf(".")+1).toLowerCase();
								var extensions = ['jpg', 'jpeg', 'bmp', 'gif', 'png', 'pdf'];
								if(extensions.indexOf(extension) == -1){
									$.messager.alert(that.TITLE, '다음 확장자만 등록 가능합니다. (' + extensions.join(', ') +')');
									return false;
								}
							}else if(!obj.value){
								$.messager.alert(that.TITLE, '파일을 등록하여 주십시오.');
								return false;
							}
						}
					}
				},
				success : function(result){
					if(result){
						$.messager.alert(that.TITLE, '부속자료가 등록 되었습니다.', null, function(){
							var iprvarNo = $("input[name=iprvarNo]").val();
							menuObj.lgstrBizObj.iprvarObj.selectOne.open(iprvarNo);
							that.close();
						});
					}
				}
			});
		},
		
		initUi : function(){
			var that = this;
			
			$("input:text[name=iprvarFileSj]", that.selector).textbox({
				width: 300
			});
		},
		
		bindEvents : function(){
			var that = this;
			
			$("#a_add_iprvar_file", that.selector).click(function(){
				that.insert();
			});
			
			$("#a_close_iprvar_file", that.selector).click(function(){
				that.close();
			});
		},
		
		insert : function(){
			var that = this;
			$("form", that.selector).attr('action', CONTEXT_PATH + '/iprvar/fileInsert.do');
			$("form", that.selector).attr('method','post');
			$("form", that.selector).submit();
		},
		
		close : function(){
			var that = this;
			
			windowObj.removeWindow(that.createSelector);
			that.createSelector = null;
			that.isCreated = false;
			that.iprvarNo = 0;
		}
};
