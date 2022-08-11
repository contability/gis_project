<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn"			uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/log/ocpatRegstrEditHistory.js' />"></script>

<div id="div_ocpatRegstrEditHistory_list" class="panel cont">
	<h1>점용대장 편집이력</h1>
	<hr class="hr_title" />
	<form:form commandName="searchDTO" method="GET">
		<table>
			<colgroup>
				<col width="5%">
				<col width="15%">
				<col width="13%">
				<col width="10%">
				<col width="13%">
				<col width="*">
				<col width="10%">
			</colgroup>
			<thead>
				<tr>
					<td>순번</td>
					<td>편집자</td>
					<td>편집일시</td>
					<td>관리번호</td>
					<td>편집대장</td>
					<td>허가번호</td>
					<td>편집내용</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${rows}" var="rows" varStatus="status">
					<tr>
						<td>${paginationInfo.firstRecordIndex + status.index + 1}</td>
						<td>${rows.userNm}</td>
						<td>${rows.editDt}</td>
						<td>${rows.ftrIdn}</td>
						<td>${rows.cdeNam}</td>
						<td>${rows.pmtNum}</td>
						<td>
							<c:forEach var="editType" items="${editType}">
								<c:if test="${editType.codeId eq rows.editType}">
									${editType.codeNm}
								</c:if>
	   						</c:forEach>
	   					</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div id="paginate" align="center">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="ocpatRegstrEditHistoryObj.search" />
			<form:hidden path="pageIndex" />
		</div>
	</form:form>
</div>