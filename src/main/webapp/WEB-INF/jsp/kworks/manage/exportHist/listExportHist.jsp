<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	 uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 	 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/exportHist.js' />"></script>

<div id="div_export_hist_list" class="panel cont">

	<h1>내보내기 이력관리</h1>
	<hr class="hr_title" />
	
	<form:form commandName="exportSearchDTO" name="frm_export_hist_list" method="GET" >
		
		<div class="searchBox">
			<label for="searchStartDt">시작 : </label>
			<form:input id="searchStartDt" class="datepicker" path="searchStartDt" />
			<label for="searchEndDt"> ~끝 : </label>
			<form:input id="searchEndDt" class="datepicker" path="searchEndDt" />
			<a id="a_search_exportHist" href="#" class="a_search">검색</a>
		</div>
		
		<div class="t_a_l f_s_15 c_666 bold">총계 : ${paginationInfo.totalRecordCount}</div>
	
		<table class="tbl_export_hist_list" >
			<colgroup>
				<col width="5%">
				<col width="10%">
				<col width="10%">
				<col width="15%">
				<col width="5%">
				<col width="5%">
				<col width="10%">
				<col width="6%">
				<col width="8%">
				<col width="*">
				<col width="10%">
				<col width="10%">
			</colgroup>
			<thead>
				<tr>
					<th>순번</th>
					<th>출력<br>일시</th>
					<th>자료구분</th>
					<th>출력위치</th>
					<th>출력형태</th>
					<th>건수</th>
					<th>출력자<br>(복제자)</th>
					<th>열람자 소속</th>
					<th>열람자</th>
					<th>열람사유</th>
					<th>예고문</th>
					<th>승인관 결재(부서장)</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows}" varStatus="status">
					<tr>
						<td>
							${paginationInfo.firstRecordIndex + status.index + 1}
						</td>
						<td>${row.outptDt}</td>
						<td>${row.exportDtaSeNm}</td>
						<td>${row.centerLat}<br />${row.centerLon}</td>
						<td>${row.exportTyNm}</td>
						<td>${row.cnt}</td>
						<td>${row.outptUserNm}</td>
						<td>${row.readngDeptNm}</td>
						<td>${row.readngUserNm}</td>
						<td>${row.exportResn}</td>
						<td>${row.prvntcDoc}</td>
						<td>${row.confmerNm}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div id="paginate" align="center">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="exportHistObj.search" />
			<form:hidden path="pageIndex"  />
		</div>			
	</form:form>
	
	
		<div class="div_btn">
		<c:if test="${ prjCode eq 'gs' }">
			<a class="a_clipreport_exportHist" href="#" onclick="exportHistObj.print();"><img src="<c:url value='/images/kworks/map/skin2/btn_down.gif' />" /></a>
		</c:if>
		</div>
</div>
