<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<form:form commandName="result" class="form_rdtPcbsDt_modify" enctype="multipart/form-data" >
<div class="over_xh h_430 over_ya">
	<input type="hidden" name="ftrCde" class="hdn_ftrCde" value="${result.ftrCde}" />
	<input type="hidden" name="ftrIdn" class="hdn_ftrIdn" value="${result.ftrIdn}" />
	<input type="hidden" name="serIdn" class="hdn_serIdn" value="${result.serIdn}" />
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
				<td class="td_rdtPcbsDt_modify_closeRangeView" colspan="3">
					<input type="hidden" name="deleteImageType" value="" />
					<c:if test="${result.closeRangeViewImage eq null}">
						<input type="file" name="closeRangeView" />
					</c:if>
					
					<c:if test="${result.closeRangeViewImage ne null}">
						<img src="<c:url value='/cmmn/image/${result.closeRangeViewImage.imageNo}/thumbnail.do'/>" alt="근경사진" />
						<a class="a_rdtPcbsDt_modify_closeRangeView_remove cursor_point"><span class="color_orange">이미지삭제</span><img src="<c:url value='/images/kworks/map/skin2/btn_remove.png' />" alt="이미지삭제"></a>
					</c:if>
				</td>
			</tr>
			<tr>
				<th>원경사진</th>
				<td class="td_rdtPcbsDt_modify_distantView" colspan="3">
					<input type="hidden" name="deleteImageType" value="" />
					<c:if test="${result.distantViewImage eq null}">
						<input type="file" name="distantView" />
					</c:if>
					<c:if test="${result.distantViewImage ne null}">
						<img src="<c:url value='/cmmn/image/${result.distantViewImage.imageNo}/thumbnail.do'/>" alt="원경사진" />
						<a class="a_rdtPcbsDt_modify_distantView_remove cursor_point"><span class="color_orange">이미지삭제</span><img src="<c:url value='/images/kworks/map/skin2/btn_remove.png' />" alt="이미지삭제"></a>
					</c:if>
				</td>
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
	<a id="a_rdtPcbsDt_modify_change"><img src="<c:url value='/images/kworks/map/skin2/btn_change.png' />" alt="수정"></a>
	<a id="a_rdtPcbsDt_modify_close"><img src="<c:url value='/images/kworks/map/skin2/btn_close.png' />" alt="닫기"></a>
</p>