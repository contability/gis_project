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
	function keywordCheck() {
		var keyword = document.getElementById("searchKeyword").value;
		if (keyword=='') {
			$.messager.alert("나의 시스템 목록","검색어를 입력해주세요.");
			return null;
		}
				
		var param = {};
		param.searchKeyword = keyword;
		param.searchCondition = 'sysNm';
		fn_locationPost(BaseUrl + "my/systemList.do", param);
	}
	function keyEnter() {
		if(event.keyCode == 13){
			event.keyCode = 0;
			keywordCheck();
		}
	}
	function systemModify(sysId) {
		var form = document.systemList;
		form.sysId.value = sysId;
		form.action = BaseUrl +  "my/systemModifyForm.do";
		form.method = "post";
		form.submit();
	}
	function systemDelete(sysId) {
		var delYn = confirm("이 시스템을 삭제하시겠습니까?");
		if (delYn) {
			var form = document.systemList;
			form.sysId.value = sysId;
			form.action = BaseUrl + "my/systemDelete.do";
			form.method = "post";
			form.submit();
		}
	}
</script>

	<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>
	<form name="systemList" onsubmit="return false;">
	<input type="hidden" name="sysId" />
	<div class="panel cont">
		<div class="titleArea pa_t_20 m_auto">
			<ul>
				<li class="f_l pa_l_5 pa_b_12"><img src="<c:url value='/images/kworks/system/tit_mysystem.png' />"></img></li>
				<li class="f_r pa_r_10 pa_t_10"><span class="f_s_12 color_6">Home &gt; <strong>나의 시스템 설정</strong></span></li>
			</ul>
		</div>
		<div class="content pa_l_20 pa_r_20 m_auto">
			<div class="searchBox">
				<span class="sLabel">시스템 명</span>
				<input class="w_240" type="text" name="searchKeyword" id="searchKeyword" value="${keyword }" onkeyup="javascript:keyEnter();" />
				<a href="#" onclick="keywordCheck();">검색</a>
			</div>
			
			<div class="pa_t_10 pa_b_10 h_27"><a class="f_r" href="<c:url value='/my/systemWriteForm.do'/>" ><img src="<c:url value='/images/kworks/system/btn_becoming.png' />" /></a></div>
			<div class="noticeTb">
				<table>
					<colgroup>
						<col width="*" />
						<col width="10%" />
						<col width="10%" />
						<col width="10%" />
						<col width="10%" />
					</colgroup>
					<thead>
						<tr>
							<th>시스템명</th>
							<th>작성일자</th>
							<th>공유여부</th>
							<th>수정</th>
							<th>삭제</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="list" items="${list }">
						<tr>
							<td class="left">${list.sysNm }</td>
							<td>${list.registDt }</td>
							<td>X</td>
							<td><a href="#" onclick="systemModify('${list.sysId }');"><img src="<c:url value='/images/kworks/system/btn_modify.png' />" /></a></td>
							<td><a href="#" onclick="systemDelete('${list.sysId }');"><img src="<c:url value='/images/kworks/system/btn_del.png' />" /></a></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</form>