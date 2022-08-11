<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn"			uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/log/ocpatRegstrDownloadHistory.js' />"></script>

<div id="div_ocpatRegstrDownloadHistory_list" class="panel cont">
	<h1>점용대장 자료 조회이력</h1>
	<hr class="hr_title" />
		<form:form commandName="searchDTO" method="GET">
			<table>
				<colgroup>
					<col width="5%">
					<col width="15%">
					<col width="10%">
					<col width="*">
					<col width="20%">
					<col width="15%">
				</colgroup>
				<thead>
					<tr>
						<td>순번</td>
						<td>조회일시</td>
						<td>조회대장</td>
						<td>허가번호</td>
						<td>조회자료</td>
						<td>열람자</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${rows}" var="rows" varStatus="status">
						<tr>
							<td>${paginationInfo.firstRecordIndex + status.index + 1}</td>
							<td>${rows.downDt}</td>
							<td>${rows.cdeNam}</td>
							<td>${rows.pmtNum}</td>
							<td>
								<c:forEach var="docCde" items="${docCde}">
									<c:if test="${docCde.codeId eq rows.docCde}">
										${docCde.codeNm}
									</c:if>
		   						</c:forEach>
		   					</td>
							<td>${rows.userNm}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div id="paginate" align="center">
				<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="ocpatRegstrDownloadHistoryObj.search" />
				<form:hidden path="pageIndex" />
			</div>
		</form:form>

</div>