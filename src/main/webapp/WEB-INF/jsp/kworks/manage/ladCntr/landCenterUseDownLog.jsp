<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn"			uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/log/landCenterUseDownLog.js' />"></script>

<div id="div_landCenterUseDownLog_list" class="panel cont">
	<h1>토지사용증명서 이력관리</h1>
	<hr class="hr_title" />
		<form:form commandName="searchDTO" method="GET">
			<table>
				<colgroup>
					<col width="5%">
					<col width="15%">
					<col width="*">
					<c:if test="${ prjCode eq 'gn' }">
					<col width="20%">
					</c:if>
					<col width="10%">
					<col width="10%">
					<c:if test="${ prjCode eq 'gn' }">
					<col width="10%">
					</c:if>
				</colgroup>
				<thead>
					<tr>
						<td>순번</td>
						<td>다운로드 일시</td>
						<td>공사명</td>
						<c:if test="${ prjCode eq 'gn' }">
						<td>위치</td>
						</c:if>
						<td>증명서 구분</td>
						<td>열람자 소속</td>
						<c:if test="${ prjCode eq 'gn' }">
						<td>열람자</td>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${rows}" var="rows" varStatus="status">
						<tr>
							<td>${paginationInfo.firstRecordIndex + status.index + 1}</td>
							<td>${rows.dwYmd}</td>
							<td>${rows.cntNam}</td>
							<c:if test="${ prjCode eq 'gn' }">
							<td>${rows.cntLoc}</td>
							</c:if>
							<td>
								<c:forEach var="docCde" items="${docCde}">
									<c:if test="${docCde.codeId eq rows.docCde}">
										${docCde.codeNm}
									</c:if>
		   						</c:forEach>
		   					</td>
							<td>
								<c:forEach var="deptCde" items="${deptCde}">
									<c:if test="${deptCde.codeId eq rows.deptCde}">
										${deptCde.codeNm}
									</c:if>
		   						</c:forEach>
		   					</td>
		   					<c:if test="${ prjCode eq 'gn' }">
							<td>${rows.userNm}</td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div id="paginate" align="center">
				<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="landCenterUseDownLogObj.search" />
				<form:hidden path="pageIndex" />
			</div>
		</form:form>

</div>