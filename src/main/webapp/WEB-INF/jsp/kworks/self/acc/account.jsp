<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/reset.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/common.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/layout_system.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/button.css' />" />
<link rel="stylesheet" type="text/css" href="<c:url value='/js/kworks/easyui/themes/bootstrap/easyui.css' />" />
<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>
<!-- wrap -->
<div class="panel cont">
	<div class="titleBox">
		<div class="titleArea bd_b_blue3 pa_t_20 m_auto">
			<ul>
				<li class="f_l pa_l_5 pa_b_12"><img src="<c:url value='/images/kworks/system/titl_myinfo.png' />"></img></li>
				<li class="f_r pa_r_10 pa_t_10"><span class="f_s_12 color_6">Home &gt; <strong>내정보관리</strong></span></li>
			</ul>
		</div>
		<form:form commandName="userVO" id="editForm" name="editForm" action="/my/insertUser.do" >
			<div class="content m_auto">
				<div class="noticeTbView v2">
					<table>
						<colgroup>
							<col width="15%" />
							<col width="*" />
						</colgroup>
						<tbody>
							<tr>
								<th>ID</th>
								<td class="left" ><form:hidden path="userId" class="w500px" /><c:out value='${userVO.userId}' /></td>
							</tr>
							<tr>
								<th>부서</th>
								<td class="left" >
									<form:select path="deptCode">
										<form:options items="${depts}" itemValue="deptCode" itemLabel="deptNm" />
									</form:select>
								</td>
							</tr>
							<tr>
								<th>이름</th>
								<td class="left" ><form:input path="userNam" class="w500px" /></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="pa_t_10 pa_b_10 h_27">
					<a id="a_save_user" class="f_r" href="#" ><img src="<c:url value='/images/kworks/system/btn_save.png' />" /></a>
				</div>
			</div>
		</form:form>
	</div>
</div>

<script type="text/javascript" src="<c:url value='/js/kworks/easyui/jquery.easyui.min.js' />"></script>
<script type="text/javascript">
	$(function() {
		// 사용자 정보 저장
		$("#a_save_user").click(function() {
			$.messager.confirm("내정보수정", "부서정보가 변경될 경우 업무권한이 초기화 됩니다. 내 정보를 수정하겠습니까?", function(isTrue) {
				if(isTrue) {
					var elemUserId = $("#userId");
					if(elemUserId.val() == null || elemUserId.val() == "") {
						$.messager.alert("내정보수정", "사용자 아이디는 필수 입력입니다.");
						elemUserId.focus();
						return false;
					}
					
					var elemDeptCode = $("#deptCode");
					if(elemDeptCode.val() == null || elemDeptCode.val() == "") {
						$.messager.alert("내정보수정", "부서코드는 필수 입력입니다.");
						elemDeptCode.focus();
						return false;
					}
					
					var elemUserNam = $("#userNam");
					if(elemUserNam.val() == null || elemUserNam.val() == "") {
						$.messager.alert("내정보수정", "사용자명은 필수 입력입니다.");
						elemUserNam.focus();
						return false;
					}
					
					$("#editForm").submit();
				}
			});
			return false;
		});
	});
</script>
