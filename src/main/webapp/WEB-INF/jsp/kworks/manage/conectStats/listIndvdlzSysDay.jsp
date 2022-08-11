<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/indvdlzSysConectStats.js' />"></script>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/manage/conectStats.css' />" />

<div class="panel cont">
	<h1>개별시스템 접속통계</h1>
	<hr class="hr_title" />
		<label class="f_l">통계종류</label>
		<select class="select_stats f_l selectBox" name="selectStats">
			<option value="today">금일접속현황</option>
			<option value="day" selected="selected">일별접속현황</option>
			<option value="month">월별접속현황</option>
			<option value="week">요일별접속현황</option>
		</select>
	<form>
		<div class="searchBox clear">
			<span class="">통계기간 : </span>
			<input name="searchStartDt" class="datepicker" value="${searchDTO.searchStartDt}">
			<span class="">~</span>
			<input name="searchEndDt" class="datepicker" value="${searchDTO.searchEndDt}">
			<span class="date1month"><img src="<c:url value='/images/kworks/map/skin2/btn_1month_off.png' />" alt="1개월" /></span>
			<span class="date3month"><img src="<c:url value='/images/kworks/map/skin2/btn_3month_off.png' />" alt="3개월" /></span>
			<span class="date6month"><img src="<c:url value='/images/kworks/map/skin2/btn_6month_off.png' />" alt="6개월" /></span>
			<span class="date12month"><img src="<c:url value='/images/kworks/map/skin2/btn_1year_off.png' />" alt="1년" /></span>
			<a id="a_search" href="#" class="a_search">검색</a>
		</div>
	</form>
	<div class="div_conts_list">
		<table class="scroll">
			<thead>
				<tr>
					<th>접속일</th>
					<th>합계</th>
					<c:forEach items="${sysList }" var="sysList">
						<th>${sysList.sysAlias }</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${result}" var="list">
				<tr>
					<td>${list.day}</td>
					<td>${list.totalCount}</td>
					<c:forEach items="${sysList }" var="sysList">
						<c:set var="sysCount" value="sys${sysList.sysId }Count"/>
						<td>${list[sysCount] }</td>
					</c:forEach>
				</tr>
			</c:forEach>
				<tr>
					<th>합계</th>
					<th>${sysTotalCntMap.allTotal }</th>
					<c:forEach items="${sysList }" var="sysList">
						<c:set var="sysTotal" value="sys${sysList.sysId }Total"/>
						<th>${sysTotalCntMap[sysTotal] }</th>
					</c:forEach>
				</tr>	
			</tbody>
		</table>
	</div>
	<div class="div_btn">
		<a class="a_excelDownload_indvdlzSys" href="#"><img src="<c:url value='/images/kworks/system/download.jpg' />" /></a>
	</div>
</div>