<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/log/log.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/manage/log/SysConectLogGroupByTime.js' />"></script>

<div class="panel cont">

	<h1>시간대별 접속 통계</h1>
	<hr class="hr_title" />
	
	<div class="searchBox">
		<input type="checkbox" name="allSearch" alt="전체 검색"><span>전체검색</span>
		<span> 시작 :</span>
		<input type="text" name="searchStartDt" class="datepicker" />
		<span> ~끝 :</span>
		<input type="text" name="searchEndDt" class="datepicker" />
		<a id="search" href="#" class="a_search">검색</a>
	</div>
	
	<div>
		<table>
			<colgroup>
				<col width="8%">
				<col width="13%">
				<col width="13%">
				<col width="13%">
				<col width="13%">
				<col width="13%">
				<col width="13%">
				<col width="14%">
			</colgroup>
			<thead>
				<td>시간대별</td>
				<td>공간정보</br>활용</td>
				<td>도로시설물</br>관리</td>
				<td>상수시설물</br>관리</td>
				<td>하수시설물</br>관리</td>
				<td>지하시설물</br>관리</td>
				<td>지하수정보</br>관리</td>
				<td>전체</td>
			</thead>
			<tbody id="list">
			</tbody>
		</table>
	</div>
</div>