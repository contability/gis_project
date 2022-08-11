<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<form:form commandName="result" enctype="multipart/form-data">
<div class="over_xh h_500 over_ys">
	<input type="hidden" name="ftrCde" class="hdn_ftrCde" value="${result.ftrCde}" />
	<input type="hidden" name="ftrIdn" class="hdn_ftrIdn" value="${result.ftrIdn}" />
	<input type="hidden" name="serIdn" class="hdn_serIdn" value="${result.serIdn}" />
	<input type="hidden" name="objectid" class="hdn_objectid" value="${result.objectid}" />
	<table class="cmmTable v2 ma_b_10">
		<form:input path="mngCde" type="hidden" cssClass="inputText w_150" />
		<caption>도시기준점 편집 테이블</caption>
		<colgroup>
			<col width="16%">
			<col width="16%">
			<col width="16%">
			<col width="16%">
			<col width="16%">
			<col width="16%">
		</colgroup>
		<tbody>
			<tr>
				<th>점의번호</th>
				<td><c:out value="${result.serIdn}" /></td>
				<th>점의종류</th>
				<td><form:input path="grad" cssClass="inputText w_80" /></td>
				<th>급수</th>
				<td>
					<form:select cssClass="selectBox w_80" path="pcbCde" >
						<form:options items="${pcbCdeList}" itemValue="codeId" itemLabel="codeNm" />
					</form:select>
				</td>
			</tr>
			<tr>
				<th>사업명</th>
				<td colspan="3"><form:input path="bsnsNm" cssClass="inputText w_250" maxlength="50" /></td>
				<th>고시번호</th>
				<td><form:input path="ntfcNo" cssClass="inputText w_80" maxlength="10" /></td>
			</tr>
			<tr>
				<th>도엽번호</th>
				<td><form:input path="shtNum" cssClass="inputText w_80" maxlength="10" /></td>
				<th>도엽명</th>
				<td><form:input path="shtNumNm" cssClass="inputText w_80" maxlength="4" /></td>
				<th>소재지</th>
				<td>
					<form:select cssClass="selectBox w_80" path="hjdCde" >
						<form:options items="${hjdCdeList}" itemValue="codeId" itemLabel="codeNm" />
					</form:select>
				</td>
			</tr>
			<tr>
				<th>계획기관</th>
				<td><form:input path="planCde" cssClass="inputText w_80" maxlength="15" /></td>
				<th>시행기관</th>
				<td><form:input path="wrkOrg" cssClass="inputText w_80" maxlength="15" /></td>
				<th>매설형태</th>
				<td><form:input path="pcbDes" cssClass="inputText w_80" maxlength="5" /></td>
			</tr>
			<tr>
				<th>매설<br>(YYYYMMDD)</th>
				<td colspan="2"><form:input path="insYmd" cssClass="inputText w_150" maxlength="8" /></td>
				<th>매설자</th>
				<td colspan="2"><form:input path="insUser" cssClass="inputText w_150" maxlength="5" /></td>
			</tr>
			<tr>
				<th>관측<br>(YYYYMMDD)</th>
				<td colspan="2"><form:input path="srvYmd" cssClass="inputText w_150" maxlength="8" /></td>
				<th>관측자</th>
				<td colspan="2"><form:input path="srvUser" cssClass="inputText w_150" maxlength="5" /></td>
			</tr>
			<tr>
				<th>성과구분</th>
				<td colspan="2">
					<form:radiobutton path="rsltSe" value="sin" class="rsltSeChk" />
					<span name="chk_view_rdlPcbsPs_rsltSe_sin">신성과</span>
					/
					<form:radiobutton path="rsltSe" value="gu" class="rsltSeChk" />
					<span name="chk_view_rdlPcbsPs_rsltSe_gu">구성과</span>
				</td>
				<th>수준점구분</th>
				<td colspan="2">
					<form:radiobutton path="lvlPntSe" value="direct" class="lvlPntSe" />
					<span name="chk_view_lvlPntSe_direct">직접수준</span>
					/
					<form:radiobutton path="lvlPntSe" value="indrt" class="lvlPntSe" />
					<span name="chk_view_lvlPntSe_indrt">간접수준</span>
				</td>
			</tr>
			<tr>
				<th>경도</th>
				<td colspan="2"><form:input path="la" cssClass="inputText w_150" maxlength="20" /></td>
				<th>위도</th>
				<td colspan="2"><form:input path="lo" cssClass="inputText w_150" maxlength="20" /></td>
			</tr>
			<tr>
				<th rowspan="3">성과</th>
				<th>타원체</th>
				<th>X(m)</th>
				<th>Y(m)</th>
				<th>h(m)</th>
				<th>좌표원점</th>
			</tr>
			<tr>
				<th>BESSEL</th>
				<td class="bd_r_c"><form:input path="bslXco" cssClass="w_80" maxlength="50" /></td>
				<td class="bd_r_c"><form:input path="bslYco" cssClass="w_80" maxlength="50" /></td>
				<td class="bd_r_c" rowspan="2"><form:input path="pyoGoh" cssClass="inputText w_80" maxlength="50" /></td>
				<td class="bd_r_c" rowspan="2"><form:input path="srtPnt" cssClass="inputText w_80" maxlength="50" /></td>
			</tr>			
			<tr>
				<th>GRS 80</th>
				<td class="bd_r_c"><form:input path="grsXco" cssClass="inputText w_80" maxlength="50" /></td>
				<td class="bd_r_c"><form:input path="grsYco" cssClass="inputText w_80" maxlength="50" /></td>
			</tr>
			<tr>
				<th>경로</th>
				<td colspan="5"><form:input path="abrDes" cssClass="inputText w_450" maxlength="100" /></td>
			</tr>
			<!-- 사진 -->
			<tr>
				<th>약도</th>
				<td class="td_rdlPcbsPs_modify_outlineMap" colspan="5">
					<input type="hidden" name="deleteImageType" value="" />
					<c:if test="${result.outlineMapImage eq null}">
						<input type="file" name="outlineMap" />
					</c:if>
					<c:if test="${result.outlineMapImage ne null}">
						<img src="<c:url value='/cmmn/image/${result.outlineMapImage.imageNo}/thumbnail.do'/>" alt="약도 사진" />
						<a class="a_rdlPcbsPs_modify_outlineMap_remove cursor_point"><span class="color_orange">이미지삭제</span><img src="<c:url value='/images/kworks/map/skin2/btn_remove.png' />" alt="이미지삭제"></a>
					</c:if>
				</td>
			</tr>
			<tr>
				<th>관측도</th>
				<td class="td_rdlPcbsPs_modify_observationMap" colspan="5">
					<input type="hidden" name="deleteImageType" value="" />
					<c:if test="${result.observationMapImage eq null}">
						<input type="file" name="observationMap" />
					</c:if>
					<c:if test="${result.observationMapImage ne null}">
						<img src="<c:url value='/cmmn/image/${result.observationMapImage.imageNo}/thumbnail.do'/>" alt="관측도 사진" />
						<a class="a_rdlPcbsPs_modify_observationMap_remove cursor_point"><span class="color_orange">이미지삭제</span><img src="<c:url value='/images/kworks/map/skin2/btn_remove.png' />" alt="이미지삭제"></a>
					</c:if>
				</td>
			</tr>
			<tr>
				<th>근경</th>
				<td class="td_rdlPcbsPs_modify_closeRangeView" colspan="5">
					<input type="hidden" name="deleteImageType" value="" />
					<c:if test="${result.closeRangeViewImage eq null}">
						<input type="file" name="closeRangeView" />
					</c:if>
					<c:if test="${result.closeRangeViewImage ne null}">
						<img src="<c:url value='/cmmn/image/${result.closeRangeViewImage.imageNo}/thumbnail.do'/>" alt="근경 사진" />
						<a class="a_rdlPcbsPs_modify_closeRangeView_remove cursor_point"><span class="color_orange">이미지삭제</span><img src="<c:url value='/images/kworks/map/skin2/btn_remove.png' />" alt="이미지삭제"></a>
					</c:if>
				</td>
			</tr>
			<tr>
				<th>원경</th>
				<td class="td_rdlPcbsPs_modify_distantView" colspan="5">
					<input type="hidden" name="deleteImageType" value="" />
					<c:if test="${result.distantViewImage eq null}">
						<input type="file" name="distantView" />
					</c:if>
					<c:if test="${result.distantViewImage ne null}">
						<img src="<c:url value='/cmmn/image/${result.distantViewImage.imageNo}/thumbnail.do'/>" alt="원경 사진" />
						<a class="a_rdlPcbsPs_modify_distantView_remove cursor_point"><span class="color_orange">이미지삭제</span><img src="<c:url value='/images/kworks/map/skin2/btn_remove.png' />" alt="이미지삭제"></a>
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>
</div>
</form:form>	
<p class="btnCenter">
	<a id="btn_controlPoint_modify_save"><img src="<c:url value='/images/kworks/map/skin2/btn_change.png' />" alt="수정"></a>
	<a id="btn_controlPoint_modify_close"><img src="<c:url value='/images/kworks/map/skin2/btn_close.png' />" alt="닫기"></a>
</p>