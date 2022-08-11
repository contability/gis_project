<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<form:form commandName="result" enctype="multipart/form-data" >
<div class="over_xh over_yh">
	<table class="cmmTable v2 ma_b_10 h_430 over_y">
		<caption>도시기준점 입력 테이블</caption>
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
				<td><form:input path="serIdn" cssClass="inputText w_80" maxlength="8" /></td>
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
				<td colspan="2"><form:input path="insYmd" cssClass="inputText w_150" maxlength="8"  /></td>
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
				<td colspan="2"><form:input path="lo" cssClass="inputText w_150" maxlength="20" /></td>
				<th>위도</th>
				<td colspan="2"><form:input path="la" cssClass="inputText w_150" maxlength="20" /></td>
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
				<td class="bd_r_c" rowspan="2"><form:input path="pyoGoh" cssClass="w_80" value="" maxlength="50" /></td>
				<td class="bd_r_c" rowspan="2"><form:input path="srtPnt" cssClass="w_80" value="" maxlength="50" /></td>
			</tr>			
			<tr>
				<th>GRS 80</th>
				<td class="bd_r_c"><form:input path="grsXco" cssClass="inputText w_80" value="" maxlength="50" /></td>
				<td class="bd_r_c"><form:input path="grsYco" cssClass="inputText w_80" value="" maxlength="50" /></td>
			</tr>
			<tr>
				<th>경로</th>
				<td colspan="5"><form:input path="abrDes" cssClass="w_450" maxlength="100" /></td>
			</tr>
			<!-- 사진 -->
			<tr>
				<th>약도</th>
				<td colspan="5"><input type="file" name="outlineMap" /></td>
			</tr>
			<tr>
				<th>관측도</th>
				<td colspan="5"><input type="file" name="observationMap" /></td>
			</tr>
			<tr>
				<th>근경</th>
				<td colspan="5"><input type="file" name="closeRangeView" /></td>
			</tr>
			<tr>
				<th>원경</th>
				<td colspan="5"><input type="file" name="distantView" /></td>
			</tr>
		</tbody>
	</table>
</div>
</form:form>
<p class="btnCenter">
	<a href="#" id="btn_controlPoint_add"><img src="<c:url value='/images/kworks/map/skin2/btn_add.png' />" alt="추가"></a>
	<a href="#" id="btn_controlPoint_close"><img src="<c:url value='/images/kworks/map/skin2/btn_close.png' />" alt="닫기"></a>
</p>