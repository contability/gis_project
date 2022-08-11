<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/log/log.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/manage/log/SysConectLogGroupBySys.js' />"></script>

<div class="panel cont">

	<h1>시스템 접속 통계</h1>
	<hr class="hr_title" />
	<form:form commandName="searchDTO" method="GET">
		<div class="searchBox">
			<label for="searchCondition">전체검색</label>
			<form:checkbox id="searchCondition" path="searchCondition" value="Y" />
			<label for="searchStartDt">시작 : </label>
			<form:input id="searchStartDt" class="datepicker" path="searchStartDt" label="시작 : " />
			<label for="searchEndDt"> ~끝 : </label>
			<form:input id="searchEndDt" class="datepicker" path="searchEndDt" label=" ~끝 : " />
			<a id="search" href="#" class="a_search">검색</a>
		</div>
		
		<div>
			<table>
				<colgroup>
					<col width="50%">
					<col width="50%">
				</colgroup>
				<thead>
					<td>시스템명</td>
					<td>카운터</td>
				</thead>
				<tbody>
					<c:forEach items="${sys}" var="sys">
						<tr>
							<td>${sys.sysAlias }</td>
							<c:set var="cnt" value="0" />
							<c:forEach items="${rows}" var="row" >
								<c:if test="${row.sysId eq sys.sysId }">
									<c:set var="cnt" value="${row.cnt }" />
								</c:if>
							</c:forEach>
							<td>${cnt }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</form:form>	
</div>