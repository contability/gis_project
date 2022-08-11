<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/reset.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/common.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/layout_system.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/button.css' />" />

<!-- easy ui -->
<link rel="stylesheet" type="text/css" href="<c:url value='/js/kworks/easyui/themes/bootstrap/easyui.css' />" />
<script type="text/javascript" src="<c:url value='/js/kworks/easyui/jquery.easyui.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/lib/jquery/form/jquery.form.min.js' />"></script>

<script type="text/javascript">
	 function listLoad(){
		var arr;
		"<c:set value='${arr}' var='aa'/>";
		var arr = ${arr};
		$(".fnctList").each(function() {
			value = $(this).val();
			for (var i=0; i<arr.length; i++) {
				if (arr[i] == value) {
					v = $(this).parent("li");
					map.put(value, v);
					v.hide();
				}
			}
		});
	}

	$(document).ready(function(){
		$("#addGroup").hide();
		
		$("#listKSC").on("change",function(){
			var chgVal = $(this).val();
			
			if(chgVal != 'ALL'){
				getListKSFC(Number(chgVal));
				getListKSFAllSetSys(Number(chgVal));
			}else{
				getListKSFCAll();
			}
		});
		
		$("#listKSFC").on("change",function(){
			var selectVal = $(this).val();
			if(selectVal != 'ALL'){
				getListKSF(Number(selectVal));
			}else{
				var tmpSysId = $("#listKSC").val();
				if(tmpSysId != "ALL"){
					getListKSFAllSetSys(Number(tmpSysId));
				}
			}
		});
	});
	
	function saveMenu() {
		var form = document.systemWrite;
		var sysNm = document.getElementById("sysNm").value;
		var sysDc = document.getElementById("sysDc").value;
		var sysId = document.getElementById("sysId").value;
		
		var arr = new Array();
		if (sysNm == "") {
			alert("시스템명을 입력하세요.");
		} else if (($(".myGroup").length == 0) || ($(".fnct").length == 0)) {
			alert("그룹과 메뉴를 추가 후 저장 가능합니다.");
//		} else if ($(".fnct").length > 30) {
//			alert("한번에 30개 이하의 메뉴만 추가 가능합니다.");
		} else {
			$(".myGroup").each(function() { //value
				var fnctCtgryNm = $(this).val();
				$(".fnct").each(function() { //name
					var name = $(this).attr("name");
					console.log("name : " + name );
					if (fnctCtgryNm == name) {
						var fnctId = $(this).val();
						var fnctNm = $(this).parent("li").text();
						fnctNm = fnctNm.replace(/(^\s*)|(\s*$)/gi, "");
						arr.push(fnctCtgryNm+"_"+fnctId+"_"+fnctNm);
					}
				});
			});
			
			/* 
			form.arr.value = arr;
			//form.sysId.value = 12;
			form.action = BaseUrl + "systemModifyWrite.do";
			form.submit();
			 */
			var param={};
			param.arr = arr;
			param.sysNm = sysNm;
			param.sysDc = sysDc;
			param.sysId = sysId;
			
			$.ajaxSettings.traditional = true;
			$.post(BaseUrl + "my/systemModifyWrite.do",param, function(){
				$(location).attr("href", BaseUrl + "my/systemList.do");
			});
		}
	}
	
	function getListKSFCAll() {
		var url = BaseUrl + "my/getListKSFAll.do";
		$("#listKSFC").html("<option>선택</option>");
		$.ajax({
			type : "POST",
			url : url,
			data : "",
			success : function(result) {
				$("#listKSF").html(result);
			}, 
          	error : function(xhr, status, error) {
                alert("ajax error");
            }
		});
	}
	
	function getListKSFC(sysId) {
		var url = BaseUrl + "my/getListKSFC.do";
		var params = "sysId="+sysId;
		$.ajax({
			type : "POST",
			url : url,
			data : params,
			success : function(result) {
				$("#listKSFC").html(result);
			}, 
          	error : function(xhr, status, error) {
                alert("ajax error");
            }
		}); 
	}
	function getListKSF(fnctCtgryId) {
		var url = BaseUrl + "my/getListKSF.do";
		var params = "fnctCtgryId="+fnctCtgryId;
		$.ajax({
			type : "POST",
			url : url,
			data : params,
			success : function(result) {
				$("#listKSF").html(result);
			}, 
          	error : function(xhr, status, error) {
                alert("ajax error");
            }
		});
	}
	
	function getListKSFAllSetSys(sysId) {
		var url = BaseUrl + "my/getListKSFSetSys.do";
		var params = "sysId=" + sysId;
		$.ajax({
			type : "POST",
			url : url,
			data : params,
			success : function(result) {
				$("#listKSF").html(result);
			}, 
          	error : function(xhr, status, error) {
                alert("ajax error");
            }
		});
	}
	function menuAdd() {
		document.getElementById("addGroup").style.position = "absolute";
		document.getElementById("groupName").value = "";
		$("#addGroup").show();
	}
	function menuDel() {
		var chkCnt = $(".myGroup:checked").length;
		if (chkCnt < 1) {
			alert("삭제할 그룹을 체크하세요.");
		} else {
			$(".myGroup:checked").each(function() {
				$(this).parent().remove();
			});
		}
	}
	function add() {
		var name = document.getElementById("groupName").value;
		if (name == "") {
			alert("그룹명을 입력하세요.");
		} else {
			var li = document.createElement("LI");
			var input = document.createElement("INPUT");
			var text = document.createTextNode(name);
			
			li.setAttribute("class", "pa_t_8 pa_l_10 f_s_12 color_6");
			li.setAttribute("id", name);
			input.setAttribute("type", "checkbox");
			input.setAttribute("class", "myGroup ma_r_3");
			//input.setAttribute("id", "myGroup");
			
			input.setAttribute("value", name);
			li.appendChild(input);
			li.appendChild(text);
			document.getElementById("menuList").appendChild(li);
			$("#addGroup").hide();
		}
	}
	function hide() {
		$("#addGroup").hide();
	}
	
	var map = newMap();
	function menuPlus() {
		var chkCntF = $(".fnctList:checked").length;
		var chkCntG = $(".myGroup:checked").length;
		var name;
		var value;
		var k;
		var v;
		if (chkCntF < 1) {
			alert("등록할 메뉴를 선택하세요.");
		} else {
			if (chkCntG < 1) {
				alert("메뉴를 등록할 그룹을 선택하세요.");
			} else {
				$(".fnctList:checked").each(function() {
					name = $(this).parent("li").text();
					value = $(this).val();
					v = $(this).parent("li");
					map.put(value, v);
					//$("input[class=fnctList]:checkbox").prop("checked", false);
					if($(".fnctList:checked").length > 100) {
						$(this).parent("li").remove();
					} else {
						$(this).parent("li").hide();	
					}
					
					$(".myGroup:checked").each(function() {
						var id = $(this).val();
						//var gid = $(this).parent("li").attr("id");
						var li = document.createElement("LI");
						var input = document.createElement("INPUT");
						var text = document.createTextNode(name); //fnctNm
						
						li.setAttribute("class", "pa_t_8 pa_l_20 f_s_12 color_6");
						input.setAttribute("type", "checkbox");
						input.setAttribute("class", "fnct ma_r_3");
						//input.setAttribute("id", "fnct");
						
						input.setAttribute("value", value);  //fnct checkbox.value
						//input.setAttribute("name", gid);  //myGroup < li.id
						input.setAttribute("name", id);  //myGroup < li.id
						li.appendChild(input);
						li.appendChild(text);
						document.getElementById(id).appendChild(li);
					});
				});
			}
		}
	}
	function menuMinus() {
		var chkCntF = $(".fnct:checked").length;
		if (chkCntF < 1) {
			alert("제외할 메뉴를 선택하세요.");
		} else {
			$(".fnct:checked").each(function() {
				var li = $(this).parent("li").text();
				var key = $(this).val();
					if (key == map.getKey(key)) {
						map.get(key).show();
					}
				$(".fnctList").prop("checked", false);
				$(this).parent("li").remove();
			});
		}
	}
	function menuPlusAll() {
		var chkCntG = $(".myGroup:checked").length;
		if (chkCntG < 1) {
			alert("메뉴를 등록할 그룹을 선택하세요.");
		} else {
			$(".fnctList").each(function() {
				var name = $(this).parent("li").text();
				var value = $(this).val();
				v = $(this).parent("li");
				map.put(value, v);
				$(".fnctList").prop("checked", false);
				$(this).parent("li").remove();
				
				$(".myGroup:checked").each(function() {
					var id = $(this).val();
					//var gid = $(this).parent("li").attr("id");
					var li = document.createElement("LI");
					var input = document.createElement("INPUT");
					var text = document.createTextNode(name);
					
					li.setAttribute("class", "pa_t_8 pa_l_20 f_s_12 color_6");
					input.setAttribute("type", "checkbox");
					input.setAttribute("class", "fnct ma_r_3");
					//input.setAttribute("id", "fnct");
					
					input.setAttribute("value", value);
					//input.setAttribute("name", gid);
					input.setAttribute("name", id);
					
					li.appendChild(input);
					li.appendChild(text);
					document.getElementById(id).appendChild(li);
				});
			});
		}
	}
	function menuMinusAll() {
		$(".fnct").each(function() {
			var name = $(this).parent("li").text();
			var value = $(this).val();
			var li = document.createElement("LI");
			var input = document.createElement("INPUT");
			var text = document.createTextNode(name);
			
			li.setAttribute("class", "pa_t_8 pa_l_10 f_s_12 color_6");
			input.setAttribute("type", "checkbox");
			input.setAttribute("class", "fnctList ma_r_3");
			//input.setAttribute("id", "fnctList");
			
			input.setAttribute("value", value);
			li.appendChild(input);
			li.appendChild(text);
			document.getElementById("listKSF").appendChild(li);
			$(this).parent("li").remove();
		});
	}
	function fnctSelectAll() {
		$(".fnctList").prop("checked", true);
	}
	function fnctDeselectAll() {
		$(".fnctList").prop("checked", false);
	}
	function newMap() {
		  var map = {};
		  map.value = {};
		  map.getKey = function(id) {
		    return id;
		  };
		  map.put = function(id, value) {
		    var key = map.getKey(id);
		    map.value[key] = value;
		  };
		  map.contains = function(id) {
		    var key = map.getKey(id);
		    if(map.value[key]) {
		      return true;
		    } else {
		      return false;
		    }
		  };
		  map.get = function(id) {
		    var key = map.getKey(id);
		    if(map.value[key]) {
		      return map.value[key];
		    }
		    return null;
		  };
		  map.remove = function(id) {
		    var key = map.getKey(id);
		    if(map.contains(id)){
		      map.value[key] = undefined;
		    }
		  };
		 
		  return map;
		}
</script>
</head>

	<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>
	<form name="systemWrite">
	<input type="hidden" id="arr" name="arr" />
	<input type="hidden" id="sysId" name="sysId" value="${sysId}" />
	<div class="panel cont">
		<div class="titleArea bd_b_blue3 pa_t_20 m_auto">
			<ul>
				<li class="f_l pa_l_5 pa_b_12"><img src="<c:url value='/images/kworks/system/tit_mysystem.png' />" /></li>
				<li class="f_r pa_r_10 pa_t_10"><span class="f_s_12 color_6">Home > <strong>나의 시스템 설정</strong></span></li>
			</ul>
		</div>
		<div class="content pa_l_20 pa_r_20 m_auto">
			<div class="searchBox v2">
				<p>
				<span class="sLabel">시스템 명</span>
				<input class="w_250" type="text" name="sysNm" id="sysNm" value="${getNm }">
				</p>
				<p>
				<span class="sLabel">설명</span>
				<input class="w_250" type="text" name="sysDc" id="sysDc" value="${getDc }">
				</p>
			</div>
			
			<div class="ma_b_20">
				<div class="f_l pa_l_30 w_350 pa_t_10">
					<div class="bd_gray3 pa_b_5 ma_t_10 w100 h_300">
						<div class="bg_ed h_36 w100">
							<span class="f_l pa_t_10 pa_l_20 f_s_12 color_7 bold">기능목록</span>
							<span class="f_r f_s_12 color_6 pa_r_5 pa_l_5 pa_t_5">
								<select class="pa_l_5 pa_r_5 h_24 w_100" id="listKSFC">
									<option VALUE="ALL" selected>전체</option>
									 
								</select></span>
							<span class="f_r f_s_12 color_6 pa_t_5">
								<select class="pa_l_5 pa_r_5 h_24 w_100" id="listKSC">
									<option value="ALL" selected>전체</option>
									<c:forEach var="listKS" items="${listKS }">
										<option value="${listKS.sysId }" >${listKS.sysNm }</option>
									</c:forEach>
								</select>
							</span>
						</div>
						<div class="listBox over_ys h_270">
							<ul class="pa_b_10 f_l pa_t_10 pa_l_20 t_a_l" id="listKSF">
								<c:forEach var="listKSF" items="${listKSF }">
								<li class="pa_t_8 pa_l_10 f_s_12 color_6">
									<input class="fnctList ma_r_3" type="checkbox" value="${listKSF.fnctId }" >${listKSF.fnctNm }</li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<div class="pa_t_10 pa_b_20 f_l">
						<a href="javascript:fnctSelectAll();"><img src="<c:url value='/images/kworks/system/btn_select.png' />" /></a>
						<a href="javascript:fnctDeselectAll();"><img class="pa_l_5" src="<c:url value='/images/kworks/system/btn_concellation.png' />" /></a>
					</div>
				</div>
				<div class="f_l w_27 pa_l_50 pa_t_120">
					<span><a href="javascript:menuPlus();"><img src="<c:url value='/images/kworks/system/boardLst_n.png' />" /></a></span><br/>
					<span><a href="javascript:menuMinus();"><img src="<c:url value='/images/kworks/system/boardLst_p.png' />" /></a></span><br/>
					<span><a href="javascript:menuPlusAll();"><img src="<c:url value='/images/kworks/system/boardLst_nn.png' />" /></a></span><br/>
					<span><a href="javascript:menuMinusAll();"><img src="<c:url value='/images/kworks/system/boardLst_pp.png' />" /></a></span>
				</div>
				<div class="f_l pa_l_50 w_350 pa_t_10">
					<div class="bd_gray3 pa_b_5 ma_t_10 w100 h_300">
						<div class="bg_ed h_36 w100">
							<span class="f_l pa_t_10 pa_l_20 f_s_12 color_7 bold">메뉴구성</span>
						</div>
						<div class="listBox over_ys h_270">
							<ul class="pa_b_10 f_l pa_t_10 pa_l_10 t_a_l" id="menuList">
								<c:forEach var="getMyList" items="${getMyList }" varStatus="idx">
									<c:choose>
										<c:when test="${idx.index==0 }">
											<ul class="pa_t_8 pa_l_10 f_s_12 color_6" id="${getMyList.fnctCtgryNm}" >
											<input class="myGroup ma_r_3" type="checkbox"  value="${getMyList.fnctCtgryNm }">${getMyList.fnctCtgryNm }
										<li class="pa_t_8 pa_l_20 f_s_12 color_6">
											<input class="fnct ma_r_3" type="checkbox"  name="${getMyList.fnctCtgryNm }" value="${getMyList.fnctId }"> ${getMyList.fnctNm }</li>
										<c:set var="tempId" value="${getMyList.fnctCtgryId }" />
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${getMyList.fnctCtgryId eq tempId }">
													<li class="pa_t_8 pa_l_20 f_s_12 color_6">
													<input class="fnct ma_r_3" type="checkbox"  name="${getMyList.fnctCtgryNm }" value="${getMyList.fnctId }"> ${getMyList.fnctNm }</li>
													<c:if test="${idx.last eq true }">
														</ul>
													</c:if>
												</c:when>
												<c:otherwise>
													</ul>
													<ul class="pa_t_8 pa_l_10 f_s_12 color_6" id="${getMyList.fnctCtgryNm }">
													<input class="myGroup ma_r_3" type="checkbox"  value="${getMyList.fnctCtgryNm }">${getMyList.fnctCtgryNm }
												<li class="pa_t_8 pa_l_20 f_s_12 color_6">
													<input class="fnct ma_r_3" type="checkbox" name="${getMyList.fnctCtgryNm }" value="${getMyList.fnctId }"> ${getMyList.fnctNm }</li>
												<c:set var="tempId" value="${getMyList.fnctCtgryId }" />
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
							</c:forEach>
							</ul>
						</div>
					</div>
					<div class="f_l pa_t_10 pa_b_20 f_l">
					<a href="javascript:menuAdd();"><img src="<c:url value='/images/kworks/system/btn_menuAdd.png' />" /></a><a href="javascript:menuDel();"><img class="pa_l_5" src="<c:url value='/images/kworks/system/btn_menuDel.png' />" /></a>
					</div>
				</div>
			</div>
			<div class="f_l pa_t_10 bd_t_e1_2 w100">
				<div class="f_r"><a class="pa_r_5" href="javascript:saveMenu();"><img src="<c:url value='/images/kworks/system/btn_save.png' />" /></a>
								 <a href="<c:url value='/my/systemList.do'/>" ><img src="<c:url value='/images/kworks/system/btn_cancle.png' />" /></a></div>
			</div>
		</div>
		<!-- map_popup02 -->
		<div class="k_pop_box bd_sky pa_15 w_300 bg_white ma_t_40 ma_l_750" id="addGroup">
			<div>
				<h1 class="f_s_14 bd_b_blue2 color_3 pa_t_3 pa_b_5 t_a_l">메뉴그룹추가
				<a class="x" href="#"><img src="<c:url value='/images/kworks/map/common/popup_close_on.png' />" /></a></h1>
			</div>
			<div class="pa_t_10">
				<span class="f_s_12 color_black pa_l_10 pa_r_5">그룹명</span><input class="w_200 bg_white bd_gray h_18" type="text" id="groupName"></input>
				
			</div>
			
			<div class="w100 pa_t_10">
				<a class="f_r" id="hide" href="javascript:hide();"><img src="<c:url value='/images/kworks/system/btn_cancle2.png' />" /></a>
				<a class="f_r pa_r_5" href="javascript:add();"><img src="<c:url value='/images/kworks/system/btn_add.png' />" /></a>
			</div>
		</div>
		<!-- //map_popup02 -->
	</div>
</form>