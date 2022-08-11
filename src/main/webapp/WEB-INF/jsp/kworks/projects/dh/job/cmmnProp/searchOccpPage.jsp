<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

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
					<td><input id="mntn" class="swtchBtn"></td>
					<th>본번</th>
					<td><input id="mnnm" class="numSpn"></td>
					<th>부번</th>
					<td><input id="slno" class="numSpn"></td>
				</tr>
				<tr>
					<th>재산번호</th>
					<td colspan="3"><input id="numSpn_prtIdn" class="numSpn_wide"></td>
					<th>재산종류</th>
					<td colspan="3"><input id="comBx_pbpKnd"></td>
				</tr>
				<tr>
					<th>점유시작일</th>
					<td colspan="3"><input id="dateBx_ocpStr" class="datebx"></td>
					<th>점유종료일</th>
					<td colspan="3"><input id="dateBx_ocpEnd" class="datebx"></td>
				</tr>
				<tr>
					<th>성명/명칭</th>
					<td colspan="3"><input id="textBx_empNam" class="textbx"></td>
					<th>연락처</th>
					<td colspan="3"><input id="textBx_empNum" class="textbx"></td>
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