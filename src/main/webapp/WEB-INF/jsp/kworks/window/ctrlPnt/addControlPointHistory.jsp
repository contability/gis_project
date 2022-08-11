<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<form:form commandName="result" enctype="multipart/form-data" >
<div class="over_xh h_210 over_ya">
	<form:input path="serIdn" type="hidden" cssClass="w_80" value="${data.serIdn}" />
	<form:input path="ftrIdn" type="hidden" cssClass="w_80" value="${data.ftrIdn}" />
	<table class="tbl_RdtPcbsDt cmmTable v2 ma_b_10 ">
		<colgroup>
			<col width="25%">
			<col width="25%">
			<col width="25%">
			<col width="25%">
		</colgroup>
		<tbody>
			<tr>
				<th>사유</th>
				<td><form:input path="examinResn" type="text" cssClass="w_130" /></td>
				<th>조사결과</th>
				<td>
					<form:select cssClass="selectBox w_130" path="examinResult">
						<form:options items="${examinResultList}" itemValue="codeId" itemLabel="codeNm" />
					</form:select>
				</td>
			</tr>
			<tr>
				<th>처리의견</th>
				<td colspan="3"><form:input path="processOpinion" type="text" cssClass="w_450" />
			</tr>
			<tr>
				<th>근경사진</th>
				<td colspan="3"><input type="file" name="closeRangeView" /></td>
			</tr>
			<tr>
				<th>원경사진</th>
				<td colspan="3"><input type="file" name="distantView" /></td>
			</tr>
			<tr>
				<th>조사년월일</th>
				<td><form:input path="examinDe" type="text" cssClass="w_130" /></td>
				<th>조사자 직급<br>및 성명</th>
				<td><form:input path="exmnrInfo" type="text" cssClass="w_130" /></td>
			</tr>
			<tr>
				<th>비고</th>
				<td colspan="3"><form:input path="remark" type="text" cssClass="w_450" /></td>
			</tr>
		</tbody>
	</table>
</div>
</form:form>
<p class="btnCenter">
	<a href="#" id="btn_controlPointHistory_add"><img src="<c:url value='/images/kworks/map/skin2/btn_add.png' />" alt="추가"></a>
	<a href="#" id="btn_controlPointHistory_close"><img src="<c:url value='/images/kworks/map/skin2/btn_close.png' />" alt="닫기"></a>
</p>