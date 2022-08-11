<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<div class="over_xh h_500 over_ya">
	<input type="hidden" class="hid_controlPointHistoryList_serIdn" value="${serIdn}" />
	<table class="tbl_controlPointHistoryList cmmTable v2 ma_b_10">
		<colgroup>
			<col width="17%">
			<col width="33%">
			<col width="33%">
			<col width="17%">
		</colgroup>
		<thead>
			<tr>
				<th><center>조사년월일&nbsp;&nbsp;&nbsp;</center></th>
				<th><center>사유&nbsp;&nbsp;</center></th>
				<th><center>처리의견&nbsp;&nbsp;</center></th>
				<th><center>조사결과&nbsp;&nbsp;</center></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${result}" var="list">
			<tr data-ftr-idn = "${list.ftrIdn}">
				<td><center>${list.examinDe}</center></center></td>
				<td><center>${list.examinResn}</center></td>
				<td><center>${list.processOpinion}</center></td>
				<td><center>${list.examinResultNm}</center></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
<p class="btnCenter">
	<a href="#" id="btn_controlPointHistory_add" class="f_l ma_l_20"><img src="<c:url value='/images/kworks/map/skin2/btn_listPlus.png' />" alt="목록추가"></a>
	<a href="#" id="btn_controlPointHistory_print" class="f_l ma_l_10"><img src="<c:url value='/images/kworks/map/skin2/btn_print2.png' />" alt="인쇄"></a>
	<a href="#" id="btn_controlPointHistory_close" class="f_r ma_r_20"><img src="<c:url value='/images/kworks/map/skin2/btn_close.png' />" alt="닫기"></a>
</p>
