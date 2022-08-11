<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<div class="div_window_cntrct_regstr div_window_cntrct_regstr_view">
	<table class="tbl_conditon cmmTable">
		<colgroup>
			<col width="15%">
			<col width="20%">
			<col width="15%">
			<col width="20%">
			<col width="15%">
			<col width="25%">
		</colgroup>
		<tbody>
			<tr>
				<th>회계년도</th>
				<td class="fisYear"></td>
				<th>계약서구분</th>
				<td></td>
				<th>계약번호</th>
				<td class="ctrtAcctBookMngNo"></td>
			</tr>
			<tr>
				<th>계약명</th>
				<td colspan="3" class="ctrtNm"></td>
				<th>계약형태</th>
				<td class="ctrtFgNm"></td>
			</tr>
			<tr>
				<th>관급자재</th>
				<td></td>
				<th>계약종류</th>
				<td class="ctrtKind"></td>
				<th>계약유형</th>
				<td class="ctrtTypeNm"></td>
			</tr>
			<tr>
				<th>계약일자</th>
				<td class="ctrtYmd"></td>
				<th>계약금액</th>
				<td class="summaryCtrtTotAmt"></td>
				<th>착공(예정)일자</th>
				<td class="startWorkYmd"></td>
			</tr>
			<tr>
				<th>절대공기(일)</th>
				<td class="firstContPrd"></td>
				<th>최초계약금액</th>
				<td class="firstSummaryCtrtAmt"></td>
				<th>준공/납품예정일자</th>
				<td class="compltSchdYmd"></td>
			</tr>
			<tr>
				<th>경비구분</th>
				<td class="expFg"></td>
				<th>지체상율(n/1000)</th>
				<td class="delayCompenRate"></td>
				<th>준공/납품일자</th>
				<td class="compltYmd"></td>
			</tr>
			<tr>
				<th>직불여부</th>
				<td></td>
				<th>계약방법</th>
				<td></td>
				<td class="ctrtMethNm"></td>
				<td class="ctrtRsn"></td>
			</tr>
			<tr>
				<th>리스여부</th>
				<td></td>
				<th>공동계약방법</th>
				<td class="collabCtrtFg"></td>
				<th>채무부담행위액</th>
				<td class="liabBundAmt"></td>
			</tr>
			<tr>
				<th rowspan="2">사업개요</th>
				<td colspan="3" rowspan="2" class="bizCont"></td>
				<th>총액계약번호</th>
				<td></td>
			</tr>
			<tr>
				<th>총액계약일자</th>
				<td></td>
			</tr>
			<tr>
				<th>위치(납품장소)</th>
				<td colspan="3" class="locatNm"></td>
				<th>총계약금액</th>
				<td></td>
			</tr>
			<tr>
				<th>계약사유</th>
				<td colspan="3"></td>
				<th>개산계약사유</th>
				<td class="rouestYn"></td>
			</tr>
			<tr>
				<th>조달구분</th>
				<td class="splyFg"></td>
				<th>조달번호</th>
				<td class="splyMngNo"></td>
				<th>조달진행구분</th>
				<td></td>
			</tr>
			<tr>
				<th>계약담당자</th>
				<td></td>
				<th>기지출액</th>
				<td class="beforeSummaryCtrtAmt"></td>
				<th>개발공채</th>
				<td></td>
			</tr>
			<tr>
				<th>원인행위총액</th>
				<td class="causeActAmt"></td>
				<th>물품구입총계</th>
				<td></td>
				<th>수입인지</th>
				<td></td>
			</tr>
			<tr>
				<th>계약금액조정방법</th>
				<td colspan="3"></td>
				<th>종합관리사업명</th>
				<td></td>
			</tr>
		</tbody>
	</table>
	<!-- 하단 버튼 레이아웃 영역 -->
	<div class="div_tools">
		<!-- 편집 저장, 닫기 버튼 -->
		<a class="a_close_view_cntrct_regstr btn_close" href="#" ></a>
	</div>
</div>