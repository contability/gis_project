<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/indvdlzSysConectStats.js' />"></script>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/manage/conectStats.css' />" />

<div class="panel cont">
	<h1>개별시스템 접속통계</h1>
	<hr class="hr_title" />
	<form:form>
		<label class="f_l">통계종류</label>
		<select class="select_stats f_l selectBox" name="selectStats">
			<option value="today" selected="selected">금일접속현황</option>
			<option value="day">일별접속현황</option>
			<option value="month">월별접속현황</option>
			<option value="week">요일별접속현황</option>
		</select>
		<div class="searchBox clear">
			<span class="">현재 접속자 수 : </span>
			<span class="">${nowCnt}</span>
			<span class="">명</span>
			<span class="">금일 누적 접속자 수 : </span>
			<span class="span_allTotal"></span>
			<span class="">명</span>
		</div>
		<div class="div_conts_list">
			<table class="scroll">
				<thead>
					<tr>
						<th>접속시간</th>
						<th>합계</th>
						<c:forEach items="${sysList }" var="sysList">
							<th>${sysList.sysAlias }</th>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
						<c:set var="allTotal" value="0" />
				<c:forEach items="${result}" var="list">
					<tr>
						<td>${list.time}</td>
						<td>${list.totalCount}</td>
						<c:forEach items="${sysList }" var="sysList">
							<c:set var="sysId" value="sys${sysList.sysId }Count"/>
							<td>${list[sysId] }</td>
						</c:forEach>
					</tr>
				</c:forEach>
					<tr>
						<th>합계</th>
						<th class="th_allTotal">${sysTotalCnt.sysTotalCnt }</th>
						<c:forEach items="${sysList }" var="sysList">
							<c:set var="sysId" value="sys${sysList.sysId }Total"/>
							<th>${sysTotalCnt[sysId] }</th>
						</c:forEach>
					</tr>	
				</tbody>
			</table>
		</div>
	</form:form>
	
	<div class="div_btn">
		<a class="a_excelDownload_indvdlzSys" href="#"><img src="<c:url value='/images/kworks/system/download.jpg' />" /></a>
	</div>
	
</div>
<script>
$(".span_allTotal").text($(".th_allTotal").text());
</script>