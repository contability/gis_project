<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/log/log.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/manage/log/SysConectLogGroupByWeekday.js' />"></script>

<div class="panel cont">

	<h1>요일별 접속 통계</h1>
	<hr class="hr_title" />
	
	<form:form commandName="searchDTO" method="GET">
		<div class="searchBox">
			<label for="searchUseYn">전체검색</label>
			<form:checkbox id="searchUseYn" path="searchUseYn" value="Y" />
			<label for="searchStartDt">시작 : </label>
			<form:input id="searchStartDt" class="datepicker" path="searchStartDt" />
			<label for="searchEndDt"> ~끝 : </label>
			<form:input id="searchEndDt" class="datepicker" path="searchEndDt" />
			<a id="search" href="#" class="a_search">검색</a>
		</div>
		
		<div>
			<table>
				<thead>
					<td>시스템</td>
					<td>월</td>
					<td>화</td>
					<td>수</td>
					<td>목</td>
					<td>금</td>
					<td>토</td>
					<td>일</td>
					<td>전체</td>
				</thead>
				<tbody>
					<c:forEach items="${sys}" var="sys">
						<tr>
							<td>${sys.sysAlias}</td>
							<c:set var="mon" value="0"/>
							<c:set var="tue" value="0"/>
							<c:set var="wed" value="0"/>
							<c:set var="thu" value="0"/>
							<c:set var="fri" value="0"/>
							<c:set var="sat" value="0"/>
							<c:set var="sun" value="0"/>
							<c:forEach items="${rows}" var="row">
								<c:choose>
									<c:when test="${row.dayOfWeek eq 'MONDAY' && row.sysId eq sys.sysId}"><c:set var="mon" value="${row.cnt }"/></c:when>
									<c:when test="${row.dayOfWeek eq 'TUESDAY' && row.sysId eq sys.sysId}"><c:set var="tue" value="${row.cnt }"/></c:when>
									<c:when test="${row.dayOfWeek eq 'WEDNESDAY' && row.sysId eq sys.sysId}"><c:set var="wed" value="${row.cnt }"/></c:when>
									<c:when test="${row.dayOfWeek eq 'THURSDAY' && row.sysId eq sys.sysId}"><c:set var="thu" value="${row.cnt }"/></c:when>
									<c:when test="${row.dayOfWeek eq 'FRIDAY' && row.sysId eq sys.sysId}"><c:set var="fri" value="${row.cnt }"/></c:when>
									<c:when test="${row.dayOfWeek eq 'SATURDAY' && row.sysId eq sys.sysId}"><c:set var="sat" value="${row.cnt }"/></c:when>
									<c:when test="${row.dayOfWeek eq 'SUNDAY' && row.sysId eq sys.sysId}"><c:set var="sun" value="${row.cnt }"/></c:when>
								</c:choose>
							</c:forEach>
							<td>${mon }</td>
							<td>${tue }</td>
							<td>${wed }</td>
							<td>${thu }</td>
							<td>${fri }</td>
							<td>${sat }</td>
							<td>${sun }</td>
							
							<c:set var="total" value="0"/>
							<c:forEach items="${cnt}" var="cnt">
								<c:if test="${sys.sysId eq cnt.sysId}">
									<c:set var="total" value="${cnt.cnt }"/>
								</c:if>
							</c:forEach>
							<td>${total }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</form:form>
</div>