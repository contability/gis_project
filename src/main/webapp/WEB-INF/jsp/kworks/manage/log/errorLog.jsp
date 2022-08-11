<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn"			uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/log/errorLog.js' />"></script>

<div id="div_error_list" class="panel cont">

	<h1>에러 로그</h1>
	<hr class="hr_title" />

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
			<colgroup>
				<col width="15%">
				<col width="35%">
				<col width="35%">
				<col width="15%">
			</colgroup>
			<thead>
				<tr>
					<td>에러 번호</td>
					<td>에러 메세지</td>
					<td>스택</td>
					<td>발생 일자</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${rows}" var="rows" varStatus="status">
					<tr error-no="${rows.errorNo}" style="cursor:pointer">
						<td>
							${paginationInfo.firstRecordIndex + status.index + 1}
						</td>
						<td>${fn:substring(rows.errorMssage, 0, 40)}...</td>
						<td>${fn:substring(rows.errorTrace, 0, 40)}...</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${rows.errorDt}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div id="paginate" align="center">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="errorLogObj.search" />
			<form:hidden path="pageIndex" />
		</div>
		
	</form:form>
	
	<div class="div_btn">
		<a class="a_excelDownload_errorLog" href="#"><img src="<c:url value='/images/kworks/system/download.jpg' />" /></a>
	</div>
	
</div>

<!-- 에러 상세보기 팝업 -->
<div id="div_error_inqire" class="div_modal" title="에러 상세보기">
	<form method="post">
		<fieldset>
			<div>
				<span class="title">
					<label>에러 번호</label>
				</span>
				<span class="content">
					<label name="errorNo"></label>
				</span>
			</div>
			<div>
				<span class="title">
					<label>발생 일자</label>
				</span>
				<span class="content">
					<label name="errorDt"></label>
				</span>
			</div>
			<div>
				<span class="title">
					<label>에러 메세지</label>
				</span>
				<span class="content">
					<textarea name="errorMssage"></textarea>
				</span>
			</div>
			<div>
				<span class="title">
					<label>스택</label>
				</span>
				<span class="content">
					<textarea name="errorTrace"></textarea>
				</span>
			</div>
		</fieldset>
	</form>
</div>