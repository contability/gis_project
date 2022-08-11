<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>


<script type="text/javascript" src="<c:url value='/js/kworks/manage/mobile.js' />"></script>

<div id ="div_mobile_export_list" class="panel cont">

	<h1>재난현장 모바일 데이터 관리</h1>
	<hr class="hr_title"/>
	
	<form:form commandName="exportSearchDTO" name="frm_mobile_export_list" method="GET" >

		<div class="searchBox">
			<label for="searchStartDt">시작 : </label>
			<form:input id="searchStartDt" class="datepicker" path="searchStartDt" />
			<label for="searchEndDt"> ~끝 : </label>
			<form:input id="searchEndDt" class="datepicker" path="searchEndDt" />
			<a id="a_search_mobile_export" href="#" class="a_search">검색</a>
		</div>
	
		<div class="t_a_l f_s_15 c_666 bold">총계 : ${paginationInfo.totalRecordCount}</div>
		
		<table class="tbl_mobile_export_list" >
			<colgroup>
				<col width="20%">
				<col width="*">
				<col width="30%">
				
			</colgroup>
			<thead>
				<tr>
					<th>순번</th>
					<th>다운로드 일시</th>
					<th>사용자</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows}" varStatus="status">
					<tr ext-no="${row.extNo}" style="cursor:pointer">
						<td>
							${status.index + 1}
						</td>
						<td><fmt:formatDate value="${row.extDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${row.extName}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
			
		<div id="paginate" align="center">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="mobileExportObj.search" />
			<form:hidden path="pageIndex"  />
		</div>			
		
	</form:form>
	
	<div class="div_btn">
		<a class="a_download" href="#"><img src="<c:url value='/images/kworks/system/download.jpg' />" /></a>
	</div>
	
</div>