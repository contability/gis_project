<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	 uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 	 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/js/kworks/self/userInfo.js' />"></script>

<div id="div_userInfo_list" class="panel cont">
	<h1>개인정보 관리</h1>
	<hr class="hr_title" />
	
	<form:form commandName="userSearchDTO" id="frm_userInfo_list" name="frm_userInfo_list" method="get" >
	
		<table class="tbl_userInfo_list" >
			<tbody>
				<c:forEach var="row" items="${rows}">
						<tr data-user-id="${row.userId}" style="cursor:pointer">
							<td style="border-right:1px solid #d8d8d8; border-top:1px solid #d8d8d8"; width="200px"; bgcolor="#E6E6E6">사용자 아이디</td>
							<td style="text-indent:10px; text-align:left; border-top:1px solid #d8d8d8";">${row.userId}</td>
						</tr>
						<tr data-user-id="${row.userId}" style="cursor:pointer">
							<td style="border-right:1px solid #d8d8d8;" bgcolor="#E6E6E6">사용자 명</td>
							<td style="text-indent:10px; text-align:left; border-top:1px solid #d8d8d8";">${row.userNm}</td>
						</tr>
						<tr data-user-id="${row.userId}" style="cursor:pointer">
							<td style="border-right:1px solid #d8d8d8;" bgcolor="#E6E6E6">부서</th>
							<td style="text-indent:10px; text-align:left; border-top:1px solid #d8d8d8";">
								<c:forEach var="dept" items="${depts}">
									<c:if test="${dept.deptCode eq row.deptCode}">
										${dept.deptNm}
									</c:if>
			   					</c:forEach>
			   				</td>
		   				</tr>
		   				<tr data-user-id="${row.userId}" style="cursor:pointer">
							<td style="border-right:1px solid #d8d8d8;" bgcolor="#E6E6E6">사용자 등급</td>
							<td style="text-indent:10px; text-align:left; border-top:1px solid #d8d8d8";">
								<c:forEach var="userGrad" items="${userGrads}">
									<c:if test="${row.userGrad eq userGrad}">
										${userGrad.value}
									</c:if>
								</c:forEach>
							</td>
						</tr>
		   				<tr data-user-id="${row.userId}" style="cursor:pointer">
							<td style="border-right:1px solid #d8d8d8;" bgcolor="#E6E6E6">사용자 종류</td>
							<td style="text-indent:10px; text-align:left; border-top:1px solid #d8d8d8";">
								<c:forEach var="userType" items="${userTypes}">
									<c:if test="${row.userType eq userType}">
										${userType.value}
									</c:if>
								</c:forEach>
							</td>
						</tr>
				</c:forEach>
			</tbody>
		</table>
	</form:form>
</div>

<div id="div_userInfo_modify" class="div_modal" title="개인정보 관리">
	<form method="post">
		<fieldset>
			<div>
				<span class="title">
					<label for="span_userInfo_modify_userId">사용자 아이디</label>
				</span>
				<span class="content">
    				<span class="content userId"></span>
    			</span>
			</div>
			<div>
				<span class="title">
					<label for="span_userInfo_modify_userNm">사용자 명</label>
				</span>
				<span class="content">
    				<span class="content userNm"></span>
    			</span>
			</div>
			<div>
				<span class="title">
					<label for="span_userInfo_modify_password">패스워드</label>
				</span>
				<span class="content">
	   				<input type="password" name="password" id="txt_userInfo_modify_password" class="password" value="" maxlength="250">
	   			</span>
			</div>
				<div>
				<span class="title">
					<label for="span_userInfo_modify_password_check">패스워드 확인</label>
				</span>
				<span class="content">
	   				<input type="password" name="password_check" id="txt_userInfo_modify_password_check" class="password_check" value="" maxlength="250">
	   			</span>
			</div>
			<div>
				<span class="title">
					<label for="span_userInfo_modify_deptCode">부서</label>
				</span>
				<span class="content">
					<select name="deptCode" class="select_user_modify_dept selectBox w_180">
    					<c:forEach var="dept" items="${depts}">
    						<option value="${dept.deptCode}">${dept.deptNm}</option>
						</c:forEach>
   					</select>
    			</span>
			</div>
		</fieldset>
	</form>
</div>