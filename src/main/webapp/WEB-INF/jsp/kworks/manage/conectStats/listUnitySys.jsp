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
			<span class="">${result.t_23}</span>
			<span class="">명</span>
		</div>
		<div class="div_conts_list">
			<table class="scroll">
				<thead>
					<tr>
						<th>접속시간</th>
						<th>통합시스템 접속자 수</th>
						<th>누적 접속자 수</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>00:00 ~ 01:00</td>
						<td>${result.h_00}</td>
						<td>${result.t_00}</td>
					</tr>
					<tr>
						<td>01:00 ~ 02:00</td>
						<td>${result.h_01}</td>
						<td>${result.t_01}</td>
					</tr>
					<tr>
						<td>02:00 ~ 03:00</td>
						<td>${result.h_02}</td>
						<td>${result.t_02}</td>
					</tr>
					<tr>
						<td>03:00 ~ 04:00</td>
						<td>${result.h_03}</td>
						<td>${result.t_03}</td>
					</tr>
					<tr>
						<td>04:00 ~ 05:00</td>
						<td>${result.h_04}</td>
						<td>${result.t_04}</td>
					</tr>
					<tr>
						<td>05:00 ~ 06:00</td>
						<td>${result.h_05}</td>
						<td>${result.t_05}</td>
					</tr>
					<tr>
						<td>06:00 ~ 07:00</td>
						<td>${result.h_06}</td>
						<td>${result.t_06}</td>
					</tr>
					<tr>
						<td>07:00 ~ 08:00</td>
						<td>${result.h_07}</td>
						<td>${result.t_07}</td>
					</tr>
					<tr>
						<td>08:00 ~ 09:00</td>
						<td>${result.h_08}</td>
						<td>${result.t_08}</td>
					</tr>
					<tr>
						<td>09:00 ~ 10:00</td>
						<td>${result.h_09}</td>
						<td>${result.t_09}</td>
					</tr>
					<tr>
						<td>10:00 ~ 11:00</td>
						<td>${result.h_10}</td>
						<td>${result.t_10}</td>
					</tr>
					<tr>
						<td>11:00 ~ 12:00</td>
						<td>${result.h_11}</td>
						<td>${result.t_11}</td>
					</tr>
					<tr>
						<td>12:00 ~ 13:00</td>
						<td>${result.h_12}</td>
						<td>${result.t_12}</td>
					</tr>
					<tr>
						<td>13:00 ~ 14:00</td>
						<td>${result.h_13}</td>
						<td>${result.t_13}</td>
					</tr>
					<tr>
						<td>14:00 ~ 15:00</td>
						<td>${result.h_14}</td>
						<td>${result.t_14}</td>
					</tr>
					<tr>
						<td>15:00 ~ 16:00</td>
						<td>${result.h_15}</td>
						<td>${result.t_15}</td>
					</tr>
					<tr>
						<td>16:00 ~ 17:00</td>
						<td>${result.h_16}</td>
						<td>${result.t_16}</td>
					</tr>
					<tr>
						<td>17:00 ~ 18:00</td>
						<td>${result.h_17}</td>
						<td>${result.t_17}</td>
					</tr>
					<tr>
						<td>18:00 ~ 19:00</td>
						<td>${result.h_18}</td>
						<td>${result.t_18}</td>
					</tr>
					<tr>
						<td>19:00 ~ 20:00</td>
						<td>${result.h_19}</td>
						<td>${result.t_19}</td>
					</tr>
					<tr>
						<td>20:00 ~ 21:00</td>
						<td>${result.h_20}</td>
						<td>${result.t_20}</td>
					</tr>
					<tr>
						<td>21:00 ~ 22:00</td>
						<td>${result.h_21}</td>
						<td>${result.t_21}</td>
					</tr>
					<tr>
						<td>22:00 ~ 23:00</td>
						<td>${result.h_22}</td>
						<td>${result.t_22}</td>
					</tr>
					<tr>
						<td>23:00 ~ 24:00</td>
						<td>${result.h_23}</td>
						<td>${result.t_23}</td>
					</tr>
					<tr>
						<th class="bold">합계</th>
						<th colspan="2" class="bold"><span>${result.t_23}</span></th>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	
	<div class="div_btn">
		<a class="a_excelDownload_unitySys" href="#"><img src="<c:url value='/images/kworks/system/download.jpg' />" /></a>
	</div>
	
</div>