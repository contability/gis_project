<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/user.js' />"></script>

<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>

<div id="div_user_list" class="panel cont">

	<h1>사용자 관리</h1>
	<hr class="hr_title" />
	
	<form:form commandName="userSearchDTO" id="frm_user_list" name="frm_user_list" method="get" >
		<div class="searchBox">
			<span class="sLabel">검색어</span>
			<form:select path="searchCondition" class="w_110" >
				<form:option value="1">이름</form:option>
				<form:option value="2">아이디</form:option>
				<form:option value="3">부서명</form:option>
				<form:option value="4">권한 그룹</form:option>
			</form:select>
			<form:input name="searchKeyword" path="searchKeyword" class="w_240" value="" />
			<form:select name="authorGroupNo" path="authorGroupNo" class="w_240">
				<form:options items="${authorGroups}" itemValue="authorGroupNo" itemLabel="authorGroupNm" />
			</form:select>
			<a href="#" class="a_search">검색</a>
		</div>
		
		<div class="t_a_l f_s_15 c_666 bold">총계 : ${paginationInfo.totalRecordCount}</div>
	
		<table class="tbl_user_list" >
			<colgroup>
				<col width="5%">
				<col width="16%">
				<col width="16%">
				<col width="16%">
				<col width="16%">
				<col width="*">
				<col width="13%">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>사용자 아이디</th>
					<th>사용자 명</th>
					<th>부서</th>
					<th>사용자 등급</th>
					<th>권한 그룹</th>
					<th>사용자 종류</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows}" varStatus="status">
					<tr data-user-id="${row.userId}" style="cursor:pointer">
						<td>
							${paginationInfo.firstRecordIndex + status.index + 1}
						</td>
						<td>${row.userId}</td>
						<td>${row.userNm}</td>
						<td>
							<c:forEach var="dept" items="${depts}">
								<c:if test="${dept.deptCode eq row.deptCode}">
									${dept.deptNm}
								</c:if>
	   						</c:forEach>
	   					</td>
						<td>
							<c:forEach var="userGrad" items="${userGrads}">
								<c:if test="${row.userGrad eq userGrad}">
									${userGrad.value}
								</c:if>
							</c:forEach>
						</td>
						<td>
							<c:forEach var="authorGroup" items="${row.kwsAuthorGroups}">
								${authorGroup.authorGroupNm} <br />
	   						</c:forEach>
						</td>
						<td>
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
		
		<div id="paginate" align="center">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="userObj.search" />
			<form:hidden path="pageIndex"  />
		</div>			
	</form:form>
	
	<div class="div_btn">
		<a class="a_new_user" href="#"><img src="<c:url value='/images/kworks/system/btn_add.png' />" /></a>
		<a class="a_excelDownload_user" href="#"><img src="<c:url value='/images/kworks/system/download.jpg' />" /></a>
		<c:if test="${prjCode eq 'ss'}">
			<a class="a_sync_saeol" href="#"><img src="<c:url value='/images/kworks/system/btn_seaoll_sync.png' />" /></a>
		</c:if>
	</div>
	
</div>

<div id="div_user_modify" class="div_modal" title="사용자 정보 수정">
	<form method="post" >
    	<fieldset>
    		<div>
    			<span class="title">
    				<label for="span_user_modify_userId">사용자 아이디</label>
    			</span>
    			<span class="content">
    				<span class="content userId"></span>
    			</span>
    		</div>
    		<div>
    			<span class="title">
    				<label for="span_user_modify_userNm">사용자 명</label>
    			</span>
    			<span class="content">
    				<input type="text" name="userNm" id="txt_user_modify_userNm" maxlength="20">
    			</span>
    		</div>
    		<div class="div_user_modify_password">
				<span class="title">
	   				<label for="span_user_modify_password">패스워드</label>
	    		</span>
	    		<span class="content">
	   				<input type="password" name="password" id="txt_user_modify_password" class="password" value="" maxlength="20">
	   			</span>
			</div>
			<div>
				<span class="title">
					<label for="span_user_modify_dept_nm">부서</label>
				</span>
				<span class="content">
					<select name="deptCode" class="select_user_modify_deptCode selectBox w_180">
						<c:forEach items="${depts }" var="dept">
							<c:if test="${dept.useAt eq 'Y' }">
								<option value="${dept.deptCode }">${dept.deptNm}</option>
							</c:if>
						</c:forEach>
					</select>
				</span>
			</div>
			<div>
    			<span class="title">
    				<label for="span_user_modify_userType">사용자 분류</label>
    			</span>
    			<span class="content">
    				<select name="userType" class="select_user_modify_userType selectBox w_180">
    					<c:forEach items="${userTypes }" var="userType">
    						<option value="${userType }">${userType.value }</option>
    					</c:forEach>
    				</select>
    			</span>
    		</div>
    		<div>
    			<span class="title">
    				<label for="span_user_modify_userGrad">사용자 등급</label>
    			</span>
    			<span class="content">
    				<select name="userGrad" class="select_user_modify_userGrad selectBox w_180">
    					<c:forEach var="userGrad" items="${userGrads}">
    						<option value="${userGrad}">${userGrad.value}</option>
						</c:forEach>
   					</select>
    			</span>
    		</div>
    		<div>
    			<span class="title">
    				<label for="sel_user_modify_author_group">권한 그룹</label>
    			</span>
    			<div class="content content_list">
    				<ul>
    					<c:forEach var="authorGroup" items="${authorGroups}">
    						<li>
    							<input name="authorGroup" class="edit_author_group" id="group_edit_${authorGroup.authorGroupNo}" type="checkbox" value="${authorGroup.authorGroupNo}"></input>
    							<label for="group_edit_${authorGroup.authorGroupNo}">${authorGroup.authorGroupNm}</label>
	    					</li>
    					</c:forEach>
    				</ul>
    			</div>
    		</div>
    	</fieldset>
	</form>
</div>

<div id="div_user_add" class="div_modal" title="사용자 정보 등록">
	<form>
		<fieldset>
			<div>
				<span class="title">
	   				<label for="span_user_add_userId">사용자 아이디</label>
	    		</span>
	    		<span class="content">
	   				<input type="text" name="userId" id="txt_user_add_userId" value="" maxlength="20">
	   			</span>
			</div>
			<div>
				<span class="title">
	   				<label for="span_user_add_userNm">사용자 명</label>
	    		</span>
	    		<span class="content">
	   				<input type="text" name="userNm" id="txt_user_add_userNm" value="" maxlength="20">
	   			</span>
			</div>
			<div>
				<span class="title">
	   				<label for="span_user_add_password">패스워드</label>
	    		</span>
	    		<span class="content">
	   				<input type="text" name="password" id="txt_user_add_password" value="" maxlength="20">
	   			</span>
			</div>
			<div>
    			<span class="title">
    				<label for="span_user_add_dept_nm">부서</label>
    			</span>
    			<span class="content">
    				<select name="deptCode" class="select_user_add_deptCode selectBox w_180">
    					<c:forEach var="dept" items="${depts}">
    						<c:if test="${dept.useAt eq 'Y'}">
    							<option value="${dept.deptCode}">${dept.deptNm}</option>
    						</c:if>
						</c:forEach>
   					</select>
    			</span>
    		</div>
    		<div>
    			<span class="title">
    				<label for="span_user_add_userType">사용자 분류</label>
    			</span>
    			<span class="content">
    				<select name="userType" class="select_user_add_userType selectBox w_180">
    					<c:forEach items="${userTypes }" var="userType">
    						<option value="${userType }">${userType.value }</option>
    					</c:forEach>
    				</select>
    			</span>
    		</div>
			<div>
    			<span class="title">
    				<label for="span_user_add_userGrad">사용자 등급</label>
    			</span>
    			<span class="content">
    				<select name="userGrad" class="select_user_add_userGrad selectBox w_180">
    					<c:forEach var="userGrad" items="${userGrads}">
    						<option value="${userGrad}">${userGrad.value}</option>
						</c:forEach>
   					</select>
    			</span>
    		</div>
    		<div>
    			<span class="title">
    				<label for="sel_user_add_author_group">권한 그룹</label>
    			</span>
    			<div class="content content_list">
    				<ul>
    					<c:forEach var="authorGroup" items="${authorGroups}">
    						<li>
    							<input name="authorGroup" id="group_add_${authorGroup.authorGroupNo}" type="checkbox" value="${authorGroup.authorGroupNo}"></input>
    							<label for="group_add_${authorGroup.authorGroupNo}">${authorGroup.authorGroupNm}</label>
	    					</li>
    					</c:forEach>
    				</ul>
    			</div>
    		</div>
		</fieldset>
	</form>
</div>