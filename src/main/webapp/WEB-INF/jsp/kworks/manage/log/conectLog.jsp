<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/log/opertLog.js' />"></script>

<div id="div_log" class="panel cont">

	<h1>접속 로그</h1>
	<hr class="hr_title" />
	
	<div class="h_30 ma_b_5">
		<label class="f_l ma_t_3">작업 로그 종류&nbsp</label>
		<select class="f_l selectBox">
			<option value="conectLog" selected="true">접속 로그</option>
			<option value="menuLog">메뉴 로그</option>
			<option value="sysConectLog">시스템 접속 로그</option>
		</select>
	</div>
	
	<form:form commandName="searchDTO" method="GET">
		
		<div class="searchBox">
			<label for="searchStartDt">시작 : </label>
			<form:input id="searchStartDt" class="datepicker" path="searchStartDt" />
			<label for="searchEndDt"> ~끝 : </label>
			<form:input id="searchEndDt" class="datepicker" path="searchEndDt" />
			<a id="search" href="#" class="a_search">검색</a>
		</div>
		
		<div class="t_a_l f_s_15 c_666 bold">총계 : ${paginationInfo.totalRecordCount}</div>

		<table>
			<thead>
				<tr>
					<td>번호</td>
					<td>사용자</td>
					<td>접속일자</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${rows}" var="rows" varStatus="status">
					<tr>
						<td>
							${paginationInfo.firstRecordIndex + status.index + 1}
						</td>
						<td>${rows.userNm}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${rows.loginDt}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div id="paginate" align="center">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="opertLogObj.search" />
			<form:hidden path="pageIndex" />
		</div>
		
	</form:form>
	
	<div class="div_btn">
		<a class="a_excelDownload_opertLog" href="#"><img src="<c:url value='/images/kworks/system/download.jpg' />" /></a>
	</div>
	
</div>