<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn"			uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="window_container">
	<div class="window_article">
		<table class="table_text tableSelector">
			<tbody class="tbody_2"> 
				<tr data-lui-idn="${result.luiIdn}">
					<td colspan="1">공사명</td>
					<td colspan="5">${result.cntNam}</td>
				</tr>
				<tr data-lui-idn="${result.luiIdn}">
					<td>공사 계약일</td>
					<td>위치</td>
					<td>소유자</td>
					<td>생년월일</td>
					<td>전체면적</td>
					<td>편입면적</td>
				</tr>
				<tr data-lui-idn="${result.luiIdn}">	
					<td>${result.cttYmd}</td>
					<td>${result.pnu}</td>
					<td>${result.ownNam}</td>
					<td>${result.ownYmd}</td>
					<td>${result.totArea}</td>
					<td>${result.incArea}</td>	
				</tr>
			</tbody>
		</table>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="btn_close_landUseInfoList button_flat_normal btn_blue">닫기</button>
		</div>
	</div>
</div>
