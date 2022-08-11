<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div id="cmmn_regester">
	<div class="cmmn_reges">
		<div class="div_regestar" data-options="fit_true">
			<table class="table_text">
				<colgroup>
					<col width="20%">
					<col width="30%">
					<col width="20%">
					<col width="30%">
				</colgroup>
				<tbody>
					<td colspan="8" style="border-bottom:1px solid #444444; border-top:0px;border-left:0px;border-right:0px;text-align:left;font-size:15px">▣ 실태조사기본정보</td>
					<tr>
						<th>소재지</th>
						<td colspan="3" id="acex_locPlc"></td>
					</tr>
					<tr>
						<th>조사자</th>
						<td id="acex_srchpNm"></td>
						<th>조사일자</th>
						<td id="acex_srchYmd"></td>
					</tr>
					<tr>
						<th>확인자</th>
						<td id="acex_cfpNm"></td>
						<th>확인일자</th>
						<td id="acex_confmYmd"></td>
					</tr>
					<td colspan="8" style="border-bottom:1px solid #444444; border-top:0px;border-left:0px;border-right:0px;text-align:left;font-size:15px">▣ 이용현황</td>
					<tr>
						<th>소유자명</th>
						<td id="acex_useBdngOwnerNm"></td>
						<th>명칭</th>
						<td id="acex_useMeansNm"></td>
					</tr>
					<tr>
						<th>이용상황</th>
						<td id="acex_useSitu"></td>
						<th>이용상제한</th>
						<td id="acex_useLimit"></td>
					</tr>
					<tr>
						<th>용도</th>
						<td id="acex_useSrv"></td>
						<th>지목</th>
						<td id="acex_useSituJimk"></td>
					</tr>
					<tr>
						<th>총면적(㎡)</th>
						<td id="acex_useTotAr"></td>
						<th>연면적(㎡)</th>
						<td id="acex_useYarea"></td>
					</tr>
					<tr>
						<th>일단의필지수</th>
						<td id="acex_useNearFiljiNum"></td>
						<th>대표필지</th>
						<td id="acex_useRepFilji"></td>
					</tr>
					<tr>
						<th>건물/구축물</th>
						<td id="acex_useBdngStc"></td>
						<th>구조/지붕</th>
						<td id="acex_useStcRof"></td>
					</tr>
					<tr>
						<th>층수</th>
						<td id="acex_useBdngLayer"></td>
						<th>건축년월일</th>
						<td id="acex_useCnstD"></td>
					</tr>
					<tr>
						<th>제3자건축물</th>
						<td id="acex_useThirdpBdng"></td>
						<th>건축물대장등재여부</th>
						<td id="acex_useAbdngRegstYn"></td>
					</tr>
					<tr>
						<th>주사용주체</th>
						<td colspan="3" id="acex_useMuseMbd"></td>
					</tr>
					<td colspan="8" style="border-bottom:1px solid #444444; border-top:0px;border-left:0px;border-right:0px;text-align:left;font-size:15px">▣ 관리형태</td>
					<tr>
						<th>고유번호</th>
						<td id="acex_prpNo"></td>
						<th>회계구분</th>
						<td id="acex_accNm"></td>
					</tr>
					<tr>
						<th>재산구분</th>
						<td id="acex_mesrvNm"></td>
						<th>재산관리관</th>
						<td id="acex_manNm"></td>
					</tr>
					<tr>
						<th>관리구분</th>
						<td id="acex_mgtGbn"></td>
						<th>위임관리관</th>
						<td id="acex_mndNm"></td>
					</tr>
					<tr>
						<th>관리여부</th>
						<td id="acex_mgtYn"></td>
						<th>유휴상태</th>
						<td id="acex_unusedStt"></td>
					</tr>
					<td colspan="8" style="border-bottom:1px solid #444444; border-top:0px;border-left:0px;border-right:0px;text-align:left;font-size:15px">▣ 취득구분</td>
					<tr>
						<th>부서</th>
						<td id="acex_acqDept"></td>
						<th>일자</th>
						<td id="acex_acqDate"></td>
					</tr>
					<tr>
						<th>취득방법</th>
						<td id="acex_acqCde"></td>
						<th>금액(원)</th>
						<td id="acex_acqPc"></td>
					</tr>
					<tr>
						<th>등기여부/등기번호</th>
						<td>
							<span id="acex_rstYn"></span>
							<span id="acex_pstNum"></span>
						</td>
						<th>대부가능여부</th>
						<td id="acex_loanYn"></td>
					</tr>
					<td colspan="8" style="border-bottom:1px solid #444444; border-top:0px;border-left:0px;border-right:0px;text-align:left;font-size:15px">▣ 토지현황</td>
					<tr>
						<th>용도지역</th>
						<td id="acex_srvRegn"></td>
						<th>용도지구</th>
						<td id="acex_srvZone"></td>
					</tr>
					<tr>
						<th>도시계획시설</th>
						<td id="acex_ctPlnFc"></td>
						<th>토지거래허가</th>
						<td id="acex_lntrdPrm"></td>
					</tr>
					<tr>
						<th>주위환경</th>
						<td id="acex_env"></td>
						<th>편의시설</th>
						<td id="acex_cnveFcl"></td>
					</tr>
					<tr>
						<th>대중교통</th>
						<td id="acex_pbLcmtn"></td>
						<th>도로접면</th>
						<td id="acex_rdSrfc"></td>
					</tr>
					<tr>
						<th>간선도로거리</th>
						<td id="acex_mrdDist"></td>
						<th>고저</th>
						<td id="acex_highLw"></td>
					</tr>
					<tr>
						<th>형상</th>
						<td id="acex_shape"></td>
						<th>방위</th>
						<td id="acex_direction"></td>
					</tr>
					<td colspan="8" style="border-bottom:1px solid #444444; border-top:0px;border-left:0px;border-right:0px;text-align:left;font-size:15px">▣ 기타사항</td>
					<tr>
						<th>용도지역</th>
						<td id="acex_etcSrvRegn"></td>
						<th>공유지분율</th>
						<td id="acex_etcShaQuota"></td>
					</tr>
					<tr>
						<th>사권설정여부 건수</th>
						<td id="acex_etcCnt"></td>
						<th>사권설정여부 금액(원)</th>
						<td id="acex_etcAm"></td>
					</tr>
					<tr>
						<th>활용형태</th>
						<td id="acex_etcUseForm"></td>
						<th>활용가능성</th>
						<td id="acex_etcUseCanSrv"></td>
					</tr>
					<tr>
						<th>활용가능면적(㎡)</th>
						<td id="acex_etcUseCanAr"></td>
						<th>위치</th>
						<td id="acex_etcLoc"></td>
					</tr>
					<tr>
						<th>주변현황</th>
						<td id="acex_etcNearSitu"></td>
						<th>특기사항</th>
						<td id="acex_etcItem"></td>
					</tr>
					<tr>
						<th>조사자 종합의견</th>
						<td colspan="3" id="acex_etcRm"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="tab_wrap">
		<div class="cmmn_tabs">
			<div title="대부및사용허가">
				<div style="margin:5px 0;"></div>
				<div class="easyui-layout" style="width:100%; height: 300px;">
					<div data-options="region:'center'">
						<table class="grid_result_loan" style="height: 100%;"></table>
					</div>
				</div>
				<div class="window_footer_white">
					<div class="button_flat">
						<button id="btn_loan_selectOne" class="button_flat_normal btn_blue">대장조회</button>
					</div>
				</div>
			</div>
			<div title="무단점유 사용자">
				<div style="margin:5px 0;"></div>
				<div class="easyui-layout" style="width:100%; height: 300px;">
					<div data-options="region:'center'">
						<table class="grid_result_occp" style="height: 100%;"></table>
					</div>
				</div>
				<div class="window_footer_white">
					<div class="button_flat">
						<button id="btn_occp_selectOne" class="button_flat_normal btn_blue">대장조회</button>
					</div>
				</div>
			</div>
		</div>
	</div>
		<!-- <div class="cmmn_miniMap">
			<div style="position:relative;width:100%;height:100%;">
				<div id="index_map_cmmn"></div>
			</div>
		</div> -->
	</div>
</div>