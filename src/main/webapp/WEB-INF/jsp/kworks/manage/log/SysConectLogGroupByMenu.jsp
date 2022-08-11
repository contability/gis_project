<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/log/log.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/kworks/manage/log/SysConectLogGroupByMenu.js' />"></script>

<div class="panel cont">

	<h1>메뉴별 접속 통계</h1>
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
				<col width="25%">
				<col width="50%">
				<col width="35%">
			</colgroup>
			<thead>
				<td>시스템명</td>
				<td>메뉴 명</td>
				<td>카운터</td>
			</thead>
			<tbody id="list">
			</tbody>
		</table>
	</div>
</div>