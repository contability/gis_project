<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<div class="over_xh h_500 over_ya">
	<table class="cmmTable v2 ma_b_10">
		<input type="hidden" class="hid_controlView_ftrCde" value="${result.ftrCde}" />
		<input type="hidden" class="hid_controlView_ftrIdn" value="${result.ftrIdn}" />
		<input type="hidden" class="hid_controlView_serIdn" value="${result.serIdn}" />
		<caption>도시기준점 정보 테이블</caption>
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
				<td>${result.serIdn}</td>
				<th>점의종류</th>
				<td>${result.grad}</td>
				<th>급수</th>
				<td>${result.pcbCdeNm}</td>
			</tr>
			<tr>
				<th>사업명</th>
				<td colspan="3">${result.bsnsNm}</td>
				<th>고시번호</th>
				<td>${result.ntfcNo}</td>
			</tr>
			<tr>
				<th>도엽번호</th>
				<td>${result.shtNum}</td>
				<th>도엽명</th>
				<td>${result.shtNumNm}</td>
				<th>소재지</th>
				<td>${result.hjdCdeNm}</td>
			</tr>
			<tr>
				<th>계획기관</th>
				<td>${result.planCde}</td>
				<th>시행기관</th>
				<td>${result.wrkOrg}</td>
				<th>매설형태</th>
				<td>${result.pcbDes}</td>
			</tr>
			<tr>
				<th>매설</th>
				<td colspan="2">${result.insYmdDate}</td>
				<th>매설자</th>
				<td colspan="2">${result.insUser}</td>
			</tr>
			<tr>
				<th>관측</th>
				<td colspan="2">${result.srvYmdDate}</td>
				<th>관측자</th>
				<td colspan="2">${result.srvUser}</td>
			</tr>
			<tr>
				<th>성과구분</th>
				<td colspan="2">
					<input type="checkbox" id="chk_controlPointView_rsltSe_sin" name="rsltSe" <c:if test="${result.rsltSe=='sin'}"> checked="checked" </c:if> onclick="return false;">
					<span name="spn_controlPointView_rsltSe_sin">신성과</span>
					/
					<input type="checkbox" id="chk_controlPointView_rsltSe_gu" name="rsltSe" <c:if test="${result.rsltSe=='gu'}"> checked="checked" </c:if> onclick="return false;">
					<span name="spn_controlPointView_rsltSe_gu">구성과</span>
				</td>
				<th>수준점구분</th>
				<td colspan="2">
					<input type="checkbox" id="chk_controlPointView_lvlPntSe_direct" name="lvlPntSe" <c:if test="${result.lvlPntSe=='direct'}"> checked="checked" </c:if> onclick="return false;">
					<span name="spn_controlPointView_lvlPntSe_direct">직접수준</span>
					/
					<input type="checkbox" id="chk_controlPointView_lvlPntSe_indrt" name="lvlPntSe" <c:if test="${result.lvlPntSe=='indrt'}"> checked="checked" </c:if> onclick="return false;">
					<span name="spn_controlPointView_lvlPntSe_indrt">간접수준</span>
				</td>
			</tr>
			<tr>
				<th>경도</th>
				<td colspan="2">${result.la}</td>
				<th>위도</th>
				<td colspan="2">${result.lo}</td>
			</tr>
			<tr>
				<th rowspan="3">성과</th>
				<th>타원체</th>
				<th>X(m)</th>
				<th>Y(m)</th>
				<th>H(m)</th>
				<th>좌표원점</th>
			</tr>
			<tr>
				<th>BESSEL</th>
				<td class="bd_r_c">${result.bslXco}</td>
				<td class="bd_r_c">${result.bslYco}</td>
				<td class="bd_r_c" rowspan="2">${result.pyoGoh}</td>
				<td class="bd_r_c" rowspan="2">${result.srtPnt}</td>
			</tr>			
			<tr>
				<th>GRS 80</th>
				<td class="bd_r_c">${result.grsXco}</td>
				<td class="bd_r_c">${result.grsYco}</td>
			</tr>
			<tr>
				<th>경로</th>
				<td colspan="5">${result.abrDes}</td>
			</tr>
			<!-- 사진 -->
			<tr>
				<th colspan="3">약도</th>
				<th colspan="3">관측도</th>
			</tr>
			<tr>
				<td colspan="3" class="h_200">
					<c:if test="${result.outlineMapImage ne null}">
						<img src="<c:url value='/cmmn/image/${result.outlineMapImage.imageNo}/thumbnail.do'/>" alt="약도 사진" />
					</c:if>
				</td>
				<td colspan="3" class="h_200">
					<c:if test="${result.observationMapImage ne null}">
						<img src="<c:url value='/cmmn/image/${result.observationMapImage.imageNo}/thumbnail.do'/>" alt="관측도 사진" />
					</c:if>
				</td>
			</tr>
			<tr>
				<th colspan="3">근경</th>
				<th colspan="3">원경</th>
			</tr>
			<tr>
				<td colspan="3" class="h_200">
					<c:if test="${result.closeRangeViewImage ne null}">
						<img src="<c:url value='/cmmn/image/${result.closeRangeViewImage.imageNo}/thumbnail.do'/>" alt="근경 사진" />
					</c:if>
				</td>
				<td colspan="3" class="h_200">
					<c:if test="${result.distantViewImage ne null}">
						<img src="<c:url value='/cmmn/image/${result.distantViewImage.imageNo}/thumbnail.do'/>" alt="원경 사진" />
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<p class="btnCenter ">
	<a href="#" id="btn_controlPointHistory_list" class="f_l ma_l_20"><img src="<c:url value='/images/kworks/map/skin2/btn_history.png' />" alt="관리대장"></a>
	<a href="#" id="btn_controlPoint_print" class="f_l ma_l_10"><img src="<c:url value='/images/kworks/map/skin2/btn_print2.png' />" alt="인쇄"></a>
	<a href="#" id="btn_controlPoint_close" class="f_r ma_r_20"><img src="<c:url value='/images/kworks/map/skin2/btn_close.png' />" alt="닫기"></a>
	<a href="#" id="btn_controlPoint_modify" class="f_r ma_r_10"><img src="<c:url value='/images/kworks/map/skin2/btn_edit2.png' />" alt="편집"></a>
</p>