<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>

	.cmmTable.v2 td{
		border-right: 1px solid #d7d7d7;
	}
</style>

<div id="div_rplotreexmn_select" class="layerCont">
	<table id="etUV_main" class="cmmTable v2 ma_b_30" summary="토지대업무 테이블" style="table-layout: fixed; word-wrap: break-word;">
		<caption>토지대업무 테이블</caption>
		<colgroup>
			<col width="22%">
			<col width="28%">
			<col width="20%">
			<col width="30%">
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">사업지구명</th>
				<td id="detail_bsnsAreaNm"></td>
				<th scope="row">사업구분</th>
				<td id="detail_bsnsSe"></td>
			</tr>
			<tr>
				<th scope="row">사업연도</th>
				<td id="detail_bsnsRplotYr"></td>
				<th scope="row">정리일자</th>
				<td id="detail_changeYmd"></td>
			</tr>
			<tr>
				<th scope="row">시행자</th>
				<td id="detail_enforcemen" colspan="3"></td>
			</tr>
			<tr>
				<th scope="row">소유자성명</th>
				<td id="detail_ownerNm"></td>
				<th scope="row">생년월일</th>
				<td id="detail_ownerBrthdy"></td>
			</tr>
			<tr>
				<th scope="row">비고</th>
				<td colspan="3" id="detail_rmkExp"></td>
			</tr>
		</tbody>
	</table>
	<table class="cmmTable v2 ma_b_30" summary="토지대업무 지적 정보 테이블">
		<caption>토지대업무 지적 정보 테이블</caption>
		<colgroup>
			<col width="30%">
			<col width="10%">
			<col width="10%">
			<col width="*">
			<col width="10%">
			<col width="10%">
		</colgroup>
		<tbody>
			<tr>
				<th scope="row" colspan="3" class="center">종전</th>
				<th scope="row" colspan="3" class="center">사업 후</th>
			</tr>
			<tr>
				<th scope="row">지번</th>
				<th scope="row">지목</th>
				<th scope="row">면적</th>
				<th scope="row">지번</th>
				<th scope="row">지목</th>
				<th scope="row">면적</th>
			</tr>
			<tr>
				<td id="detail_bfr_jibun"></td>
				<td id="detail_bfr_rplotLndcgr"></td>
				<td id="detail_bfr_rplotAr"></td>
				<td id="detail_aft_rplotreexmnNm"></td>
				<td id="detail_aft_rplotLndcgr"></td>
				<td id="detail_aft_rplotAr"></td>
			</tr>
		</tbody>
	</table>
</div>
<p class="btnRight v2">
	<button id="a_close_rplotreexmn" class="button_flat_normal btn_blue">닫기</button>
</p>