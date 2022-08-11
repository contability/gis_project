<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<div class="over_xh h_390 over_ya">
	<input type="hidden" class="hid_controlPointHistoryView_serIdn" value="${result.serIdn}" />
	<input type="hidden" class="hid_controlPointHistoryView_ftrCde" value="${result.ftrCde}" />
	<input type="hidden" class="hid_controlPointHistoryView_ftrIdn" value="${result.ftrIdn}" />
	<table class="tbl_RdtPcbsDt cmmTable v2 ma_b_10 ">
		<colgroup>
			<col width="25%">
			<col width="25%">
			<col width="25%">
			<col width="25%">
		</colgroup>
		<tbody>
			<tr>
				<th><center>사유&nbsp;&nbsp;</center></th>
				<td colspan="1"><center>${result.examinResn}</center></td>
				<th><center>조사결과&nbsp;&nbsp;</center></th>
				<td colspan="1"><center>${result.examinResultNm}</center></td>
			</tr>
			<tr>
				<th><center>처리의견&nbsp;&nbsp;</center></th>
				<td colspan="3">${result.processOpinion}</td>
			</tr>
			<tr>
				<th colspan="2"><center>근경사진&nbsp;</center></th>
				<th colspan="2"><center>원경사진&nbsp;</center></th>
			</tr>
			<tr>
				<td colspan="2" class="h_200">
					<c:if test="${result.closeRangeViewImage ne null}">
						<img src="<c:url value='/cmmn/image/${result.closeRangeViewImage.imageNo}/thumbnail.do'/>" alt="근경 사진" />
					</c:if>
				</td>
				<td colspan="2" class="h_200">
					<c:if test="${result.distantViewImage ne null}">
						<img src="<c:url value='/cmmn/image/${result.distantViewImage.imageNo}/thumbnail.do'/>" alt="원경 사진" />
					</c:if>
				</td>
			</tr>
			<tr>
				<th><center>조사년월일&nbsp;&nbsp;</center></th>
				<td><center>${result.examinDe}</center></td>
				<th><center>조사자 직급/성명&nbsp;</center></th>
				<td><center>${result.exmnrInfo}</center></td>
			</tr>
			<tr>
				<th><center>비고&nbsp;&nbsp;</center></th>
				<td colspan="3">${result.remark}</td>
			</tr>
		</tbody>
	</table>
</div>
<p class="btnCenter">
	<a href="#" id="btn_controlPointHistoryView_print" class="f_l ma_l_20"><img src="<c:url value='/images/kworks/map/skin2/btn_print2.png' />" alt="인쇄"></a>
	<a href="#" id="btn_controlPointHistoryView_close" class="f_r ma_r_20"><img src="<c:url value='/images/kworks/map/skin2/btn_close.png' />" alt="닫기"></a>
	<a href="#" id="btn_controlPointHistoryView_modify" class="f_r ma_r_10"><img src="<c:url value='/images/kworks/map/skin2/btn_edit2.png' />" alt="편집"></a>
</p>