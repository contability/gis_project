<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="window_container">
	<div class="window_article">
		<table class="table_text">
			<colgroup>
				<col width="20%">
				<col width="11%">
				<col width="11%">
				<col width="*">
				<col width="20%">
				<col width="11%">
				<col width="11%">
				<col width="11%">
			</colgroup>
			<tbody>
				<tr>
					<th>읍면동</th>
					<td><input id="comboBx_emd"></td>
					<th>산</th>
					<td><input id="mntn"></td>
					<th>본번</th>
					<td><input id="mnnm" class="numSpn"></td>
					<th>부번</th>
					<td><input id="slno" class="numSpn"></td>
				</tr>
				<tr>
					<th>재산구분</th>
					<td colspan="3"><input id="comboBx_cmmn"></td>
					<th>조사년도</th>
					<td colspan="3"><input id="comboBx_srchY"></td>
				</tr>
				<tr>
					<th>위임관리관</th>
					<td colspan="3"><input id="text_mndNm" class="textarea_acex"></td>
					<th>재산관리관</th>
					<td colspan="3"><input id="text_manNm" class="textarea_acex"></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button id="btn_reset" class="button_flat_normal btn_blue">초기화</button>
			<button id="btn_search" class="button_flat_normal btn_blue">검색</button>
		</div>
	</div>
</div>