<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/log/log.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/manage/log/SysConectLogGroupByDept.js' />"></script>

<div class="panel cont">

	<h1>부서별 접속 통계</h1>
	<hr class="hr_title" />
	
	<form:form commandName="searchDTO" method="GET">
		<div class="searchBox">
			<label for="searchUseYn">전체검색</label>
			<form:checkbox id="searchUseYn" path="searchUseYn" value="Y" />
			<label for="searchStartDt">시작 : </label>
			<form:input id="searchStartDt" class="datepicker" path="searchStartDt" />
			<label for="searchEndDt"> ~끝 : </label>
			<form:input id="searchEndDt" class="datepicker" path="searchEndDt" />
			<label for="searchKeyword"> 부서 :</label>
			<form:input id="searchKeyword" path="searchKeyword" />
			<a id="search" href="#" class="a_search">검색</a>
		</div>
		
		<div>
			<table>
				<thead>
					<tr>
						<td>이름</td>
						<c:forEach items="${sys}" var="sys">
							<td style="font-size:11px;">${sys.sysAlias}</td>
						</c:forEach>
						<td>전체</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${cnt}" var="cnt">
						<tr>
							<td>${cnt.deptNm}</td>
							<c:forEach items="${sys}" var="sys">
								<td>
									<%-- //기본값 설정 --%>
									<c:set var="reCnt" value="0" />
									
									<%-- //userId 와 sysId 가 같은 값을 rows에서 찾아서 기본값에 셋팅한다. --%>
									<c:forEach items="${rows}" var="row">
										<c:if test="${row.deptCode eq cnt.deptCode && sys.sysId eq row.sysId}">
											<c:set var="reCnt" value="${row.cnt}" />
										</c:if>
									</c:forEach>
									
									<%-- //값을 출력한다. --%>
									<c:out value="${reCnt}" />
								</td>
							</c:forEach>
							<td>${cnt.cnt}</td>
						</tr>	
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="paginate" align="center">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="GroupByDeptObj.goPage" />
			<form:hidden path="pageIndex" name="pageIndex" />
		</div>
	</form:form>
</div>