<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/unitySysConectStats.js' />"></script>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/manage/conectStats.css' />" />

<div class="panel cont">

	<h1>통합시스템 접속통계</h1>
	<hr class="hr_title" />
	<form>
		<label class="f_l">통계종류</label>
		<select class="select_stats f_l selectBox" name="selectStats">
			<option value="today">금일접속현황</option>
			<option value="day">일별접속현황</option>
			<option value="month">월별접속현황</option>
			<option value="week" selected="selected">요일별접속현황</option>
		</select>
		<div class="searchBox clear">
			<span class="">통계기간 : </span>
			<input name="searchStartDt" class="datepicker" value="${searchDTO.searchStartDt}" />
			<span class="">~</span>
			<input name="searchEndDt" class="datepicker" value="${searchDTO.searchEndDt}" />
			<span class="date1month"><img src="<c:url value='/images/kworks/map/skin2/btn_1month_off.png' />" alt="1개월" /></span>
			<span class="date3month"><img src="<c:url value='/images/kworks/map/skin2/btn_3month_off.png' />" alt="3개월" /></span>
			<span class="date6month"><img src="<c:url value='/images/kworks/map/skin2/btn_6month_off.png' />" alt="6개월" /></span>
			<span class="date12month"><img src="<c:url value='/images/kworks/map/skin2/btn_1year_off.png' />" alt="1년" /></span>
			<a id="search" href="#" class="a_search">검색</a>
		</div>
		<div class="div_conts_list">
			<table class="scroll">
				<thead>
					<tr>
						<th>접속요일</th>
						<th>요일별 접속자 수</th>
						<th>요일별 누적 접속자 수</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${result.get(1).week}</td>
						<td>${result.get(1).count}</td>
						<td>${result.get(1).totalCount}</td>
					</tr>
					<tr>
						<td>${result.get(2).week}</td>
						<td>${result.get(2).count}</td>
						<td>${result.get(2).totalCount}</td>
					</tr>
					<tr>
						<td>${result.get(3).week}</td>
						<td>${result.get(3).count}</td>
						<td>${result.get(3).totalCount}</td>
					</tr>
					<tr>
						<td>${result.get(4).week}</td>
						<td>${result.get(4).count}</td>
						<td>${result.get(4).totalCount}</td>
					</tr>
					<tr>
						<td>${result.get(5).week}</td>
						<td>${result.get(5).count}</td>
						<td>${result.get(5).totalCount}</td>
					</tr>
					<tr>
						<td>${result.get(6).week}</td>
						<td>${result.get(6).count}</td>
						<td>${result.get(6).totalCount}</td>
					</tr>
					<tr>
						<td>${result.get(0).week}</td>
						<td>${result.get(0).count}</td>
						<td>${result.get(0).totalCount}</td>
					</tr>
					<tr>
						<th>합계</th>
						<th colspan="2">${result.get(0).totalCount + result.get(1).totalCount + result.get(2).totalCount + result.get(3).totalCount + result.get(4).totalCount + result.get(5).totalCount + result.get(6).totalCount}</th>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	
	<div class="div_btn">
		<a class="a_excelDownload_unitySys" href="#"><img src="<c:url value='/images/kworks/system/download.jpg' />" /></a>
	</div>
	
</div>