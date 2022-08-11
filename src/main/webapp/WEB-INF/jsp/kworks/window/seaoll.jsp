<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<div class="agrld_info_tap">
	<c:if test="${agrld.apvPermRegMgtNo eq null || empty agrld.apvPermRegMgtNo && dfnnd.fomApvNo eq null && empty dfnnd.fomApvNo}">
		<h3>데이터가 존재하지 않습니다.</h3>
	</c:if>
	
	<c:if test="${agrld.apvPermRegMgtNo ne null && !empty agrld.apvPermRegMgtNo}">
	<div title="농지전용신청정보" class="">
		<table class="cmmTable v2 ma_b_15">
			<colgroup>
				<col width="25%" />
				<col width="25%" />
				<col width="25%" />
				<col width="25%" />
			</colgroup>
			<tr>
				<th>관리번호</th>
				<td colspan="3">${agrld.apvPermRegMgtNo}</td>
			</tr>
			<tr>
				<th>허가번호</th>
				<td>${agrld.capCggApvPermNo}</td>
				<th>허가일자</th>
				<td>${agrld.capApvPermYmd}</td>
			</tr>
			<tr>
				<th>전용자명</th>
				<td>${agrld.tgtResiOrgzNm}</td>
				<th>생년월일(법인번호)</th>
				<td>${agrld.sid}</td>
			</tr>
			<tr>
				<th>전용자연락처</th>
				<td>${agrld.tgtTelNo}</td>
				<th>전용목적</th>
				<td>${agrld.apmFlapObjNm}</td>
			</tr>
			<tr>
				<th>소재지</th>
				<td colspan="3">${agrld.fmlAddr}</td>
				
			</tr>
			<tr>
				<th>본번-부번</th>
				<td>${agrld.bonNo}-${agrld.buNo}</td>
				<th>법정동명</th>
				<td>${agrld.regnCodeNm}</td>
			</tr>
			<tr>
				<th>업무구분</th>
				<td>${agrld.bsnClCode}</td>
				<th>업무종료일자</th>
				<td>${agrld.bsnEndYmd}</td>
			</tr>
			<tr>
				<th>농지보전부담금</th>
				<td>${agrld.pymntPreArrPstnBeaAm}</td>
				<th>실제지목</th>
				<td>${agrld.pflFactJimkCode}</td>
			</tr>
			<tr>
				<th>필지면적(㎡)</th>
				<td>${agrld.pflRlndAr}</td>
				<th>전용면적</th>
				<td>${agrld.pflEexclAr}</td>
			</tr>
			<tr>
				<th>농지구분</th>
				<td>${agrld.pflFmlSeCode}</td>
				<th>용도구역</th>
				<td>${agrld.pflSrvDistCode}</td>
			</tr>
			<tr>
				<th>경지정리여부</th>
				<td>${agrld.pflClndArgmtYn}</td>
				<th>용수개발여부</th>
				<td>${agrld.pflWtrDevelopYn}</td>
			</tr>
			<tr>
				<th>주재배작물</th>
				<td colspan="3">${agrld.pflMstCultCrpCode}</td>
				
			</tr>
		
		</table>
	</div>
	</c:if>
	
	<c:if test="${dfnnd.fomApvNo ne null && !empty dfnnd.fomApvNo}" >
	<div title="산지전용관리대장" class="">
		<table class="cmmTable v2 ma_b_15">
			<colgroup>
				<col width="25%" />
				<col width="25%" />
				<col width="25%" />
				<col width="25%" />
			</colgroup>
			<tr>
				<th>인허가번호</th>
				<td>${dfnnd.fomApvNo}</td>
				<th>허가일자</th>
				<td>${dfnnd.permYmd}</td>
			</tr>
			<tr>
				<th>수허가자명</th>
				<td colspan="3">${dfnnd.rgtMbdNm}</td>
				
			</tr>
			<tr>
				<th>수허가자 지번주소</th>
				<td colspan="3">${dfnnd.aplrAddr}</td>
				
			</tr>
			<tr>
				<th>법정동명</th>
				<td>${dfnnd.regnNm}</td>
				<th>지번(본번-부번)</th>
				<td>${dfnnd.boJibn}-${dfnnd.buJibn}</td>
			</tr>
			<tr>
				<th>수허가자 도로명주소</th>
				<td colspan="3">${dfnnd.rdnWhlAddr}</td>
				
			</tr>
			<tr>
				<th>산림소재지</th>
				<td colspan="3">${dfnnd.halfFomAddr}</td>
				
			</tr>
			<tr>
				<th>지적</th>
				<td>${dfnnd.acrg}</td>
				<th>전용면적(㎡)</th>
				<td>${dfnnd.permArea}</td>
			</tr>
			<tr>
				<th>전용목적</th>
				<td colspan="3">${dfnnd.exclPurpose}</td>
				
			</tr>
			<tr>
				<th>기타목적</th>
				<td colspan="3">${dfnnd.etcPurpose}</td>
				
			</tr>
			<tr>
				<th>사업시작일</th>
				<td>${dfnnd.workStdt}</td>
				<th>사업종료일</th>
				<td>${dfnnd.workEnddt}</td>
			</tr>
			<tr>
				<th>대체조림비금액</th>
				<td>${dfnnd.aaexpAmt}</td>
				<th>복구금액</th>
				<td>${dfnnd.recoryAmt}</td>
			</tr>
			<tr>
				<th>복구완료일</th>
				<td>${dfnnd.recoryEnddt}</td>
				<th>복구준공일</th>
				<td>${dfnnd.recoryJgongYmd}</td>
			</tr>
			<tr>
				<th>복구의무면제여부</th>
				<td colspan="3">${dfnnd.recoryDtyExmYn}</td>
				
			</tr>
		
		</table>
	</div>	
	</c:if>
</div>

<div class="div_agrld f_r"  >
	<a class="a_agrld_close" href="#">
		<img src="<c:url value='/images/kworks/main/attr/btn_close.png'/>" />
	</a>
</div>