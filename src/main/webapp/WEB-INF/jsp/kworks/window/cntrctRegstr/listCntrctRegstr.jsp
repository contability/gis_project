<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<div class="div_window_cntrct_regstr div_window_cntrct_regstr_list">
	<table class="tbl_conditon cmmTable">
		<colgroup>
			<col width="13%">
			<col width=*>
			<col width="13%">
			<col width=*>
		</colgroup>
		<tbody>
			<tr>
				<th>회계년도</th>
				<td>
					<select class="fisYear">
					</select>
				</td>
				<th>계약종류</th>
				<td>
					<select class="ctrtKind">
					</select>
				</td>
			</tr>
			<tr>
				<th>계약번호</th>
				<td><input class="ctrtAcctBookMngNo" type="text" value="" /></td>
				<th>계약명</th>
				<td><input class="ctrtNm" type="text" value="" /></td>
			</tr>
			<tr>
				<th>계약일자</th>
				<td>
					<input class="ctrtYmdStart" type="text" value="" />
					~
					<input class="ctrtYmdEnd" type="text" value="" />
				</td>
				<th>준공예정일자</th>
				<td>
					<input class="compltSchdYmdStart" type="text" value="" />
					~
					<input class="compltSchdYmdEnd" type="text" value="" />
				</td>
			</tr>
		</tbody>
	</table>
	<div class="div_condition_tools">
		<a class="a_search btn_search4" href="#"></a>
	</div>
	<div class="div_content_container panel">
		<!-- datagrid 영역 -->
		<table id="tbl_cntrct_regstr_list">
		</table>
	</div>
	<!-- 하단 버튼 레이아웃 영역 -->
	<div class="div_tools">
		<!-- 편집 저장, 닫기 버튼 -->
		<a class="a_close btn_close" href="#" ></a>
	</div>
</div>