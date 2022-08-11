<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/editHistory.js' />"></script>

<div id="div_edit_history_list" class="panel cont">

	<h1>편집 이력 조회</h1>
	<hr class="hr_title" />
	
	<form:form commandName="searchDTO" method="get" >
	
		<div class="searchBox">
			<span class="sLabel">검색어</span>
			<form:select path="searchCondition" class="w_80" >
				<form:option value="1">서비스</form:option>
				<form:option value="2">편집종류</form:option>
				<form:option value="3">레이어</form:option>
				<form:option value="4">편집자</form:option>
			</form:select>
			<form:input name="searchKeyword" path="searchKeyword" class="w_200" value="" />
			<form:select name="sysId" path="sysId" class="w_200">
				<form:option value="">전체</form:option>
				<form:options items="${sysList}" itemValue="sysId" itemLabel="sysAlias" />
			</form:select>
			
			<form:select name="editType" path="editType" class="w_240">
				<form:option value="">전체</form:option>
				<form:option value="SPATIAL">공간</form:option>
				<form:option value="ATTRIBUTE">속성</form:option>
			</form:select>
			&nbsp;
			&nbsp;
			<span class="sLabel">편집기간</span>
			<input name="searchStartDt" class="datepicker w_80" value="${searchDTO.searchStartDt}" />
			<span>~</span>
			<input name="searchEndDt" class="datepicker w_80" value="${searchDTO.searchEndDt}" />
			<span class="date1month"><img src="<c:url value='/images/kworks/map/skin2/btn_1month_off.png' />" alt="1개월" /></span>
			<span class="date3month"><img src="<c:url value='/images/kworks/map/skin2/btn_3month_off.png' />" alt="3개월" /></span>
			<span class="date6month"><img src="<c:url value='/images/kworks/map/skin2/btn_6month_off.png' />" alt="6개월" /></span>
			<a href="#" class="a_search">검색</a>
		</div>
		
		<table class="tbl_edit_history_list" >
			<colgroup>
				<col width="10%">
				<col width="15%">
				<col width="15%">
				<col width="15%">
				<col width="15">
				<col width="15%">
				<col width="15%">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>서비스</th>
					<th>레이어</th>
					<th>편집종류</th>
					<th>편집자</th>
					<th>편집일시</th>
					<th>위치</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${rows}" varStatus="status">
					<tr>
						<td>${paginationInfo.firstRecordIndex + status.index + 1}</td>
						<td>${row.sysAlias}</td>
						<td>${row.dataAlias}</td>
						<td>
							<c:forEach var="editType" items="${editTypes}">
								<c:if test="${row.editType eq editType}">
									${editType.value}
								</c:if>
							</c:forEach>
						</td>
						<td>${row.editorNm}</td>
						<td><fmt:formatDate value="${row.editDt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${row.bjdNam}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div id="paginate" align="center">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="restUtils.search" />
			<form:hidden path="pageIndex"  />
		</div>			
	</form:form>
	
	<div class="div_btn">
		<a class="a_excelDownload_editHistory" href="#"><img src="<c:url value='/images/kworks/system/download.jpg' />" /></a>
	</div>

</div>
