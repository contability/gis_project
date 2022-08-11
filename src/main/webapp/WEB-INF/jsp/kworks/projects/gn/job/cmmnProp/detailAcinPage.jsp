<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div id="cmmn_regester">
	<div class="cmmn_reges">
		<div class="div_regestar" data-options="fit_true" style="display: inline;">
			<table class="table_text">
				<colgroup>
					<col width="20%">
					<col width="30%">
					<col width="20%">
					<col width="30%">
				</colgroup>
				<tbody>
					<td colspan="4" style="border-bottom: 1px solid #444444; border-top: 0px; border-left: 0px; border-right: 0px; text-align: left; font-size: 15px">▣ 영상기반 공유재산 사전조사 분석서</td>
					<tr>
						<th>일련번호</th>
						<td colspan="3" id="acin_prtIdn"></td>
					</tr>
					<tr>
						<th>소재지</th>
						<td colspan="3" class="acin_locPlc"></td>
					</tr>
					<tr>
						<th>공부지목</th>
						<td class="acin_bmlCde"></td>
						<th>현황지목</th>
						<td id="acin_rbmlCde"></td>
					</tr>
					<tr>
						<th>공부면적</th>
						<td id="acin_brsAr"></td>
						<th>대부</th>
						<td id="acin_brsLon"></td>
					</tr>
					<tr>
						<th>신청면적</th>
						<td id="acin_brsArq"></td>
						<th>재산구분</th>
						<td class="acin_meansSe"></td>
					</tr>
					<tr>
						<th>점유면적</th>
						<td id="acin_brsOcpar"></td>
						<th>무단점유</th>
						<td id="acin_brsOcp"></td>
					</tr>
					<tr>
						<th>종합의견</th>
						<td colspan="3" id="acin_totRm" style="height: 100px;"></td>
					</tr>
				</tbody>
			</table>

			<br>
			<table class="table_text">
				<colgroup>
					<col width="20%">
					<col width="30%">
					<col width="20%">
					<col width="30%">
				</colgroup>
				<tbody>
					<td colspan="4" style="border-bottom: 1px solid #444444; border-top: 0px; border-left: 0px; border-right: 0px; text-align: left; font-size: 15px">▣ 국·공유재산 실태조사표</td>
					<tr>
						<th>소재지</th>
						<td colspan="3" class="acin_locPlc"></td>
					</tr>
					<tr>
						<th>지목</th>
						<td class="acin_bmlCde"></td>
						<th>공부면적(㎡)</th>
						<td id="acin_area"></td>
					</tr>
					<tr>
						<th>용도지역</th>
						<td id="acin_spfc"></td>
						<th>소유구분</th>
						<td id="acin_ownCde"></td>
					</tr>
					<tr>
						<th>재산구분</th>
						<td class="acin_meansSe"></td>
						<th>행정재산 용도</th>
						<td id="acin_prsCde"></td>
					</tr>
					<tr>
						<th>공시지가(원)</th>
						<td id="acin_olnlp"></td>
						<th>재산관리관</th>
						<td id="acin_manCde"></td>
					</tr>
					<tr>
						<th>부서</th>
						<td id="acin_acqDept"></td>
						<th>일자</th>
						<td id="acin_acqDate"></td>
					</tr>
					<tr>
						<th>취득방법</th>
						<td id="acin_acqCde"></td>
						<th>등기여부</th>
						<td id="acin_rstYn"></td>
					</tr>
					<tr>
						<th>이용현황 용도</th>
						<td id="acin_useMesrv"></td>
						<th>구조물유무</th>
						<td id="acin_ustrYn"></td>
					</tr>
					<tr>
						<th>기타</th>
						<td id="acin_useEtc"></td>
						<th>건축물대장</th>
						<td id="acin_useBdcmd"></td>
					</tr>
					<tr>
						<th>대부현황 용도</th>
						<td id="acin_lonPrs"></td>
						<th>대부현황 면적(㎡)</th>
						<td id="acin_lonArea"></td>
					</tr>
					<tr>
						<th>대부기간</th>
						<td id="acin_lonDay"></td>
						<th>대부료(원)</th>
						<td id="acin_perAmt"></td>
					</tr>
					<tr>
						<th>무단점유 사용실태</th>
						<td id="acin_ocpAcn"></td>
						<th>무단점유 면적(㎡)</th>
						<td id="acin_ocpArea"></td>
					</tr>
					<tr>
						<th>무단점유자 인적사항</th>
						<td colspan="3" id="acin_ocpHnpt"></td>
					</tr>
					<tr>
						<th>조사자 종합의견</th>
						<td colspan="3" id="acin_ocpRm" style="height: 100px;"></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="window_footer_white">
			<div class="button_flat">
				<button id="btn_bfPdfDownload" class="button_flat_normal btn_blue">사전조사서</button>
				<button id="btn_asPdfDownload" class="button_flat_normal btn_blue">실태조사서</button>
			</div>
		</div>
	</div>

	<div class="div_image_wrap" style="width: 50%; float:right; height: 50%;">
		<table class="table_text" style="width: 100%;">
			<colgroup>
				<col width="50%">
				<col width="50%">
			</colgroup>
			<tbody>
				<td colspan="4" style="border-bottom: 1px solid #444444; border-top: 0px; border-left: 0px; border-right: 0px; text-align: left; font-size: 15px">▣ 영상기반 공유재산 사전조사 분석서 사진</td>
				<tr>
					<th style="text-align: center;">1차변환</th>
					<th style="text-align: center;">2차변환</th>
				</tr>
				<tr>
					<td class="frstCnvs" style="height: 260px;">
						<a class="acinImagePreview" href="#">
							<img class="acinImage" alt="1차변환" src="" style="height: 260px; display: none;">
						</a>
					</td>
					<td class="scndCnvs" style="height: 260px;">
						<a class="acinImagePreview" href="#">
							<img class="acinImage" alt="2차변환" src="" style="height: 260px; display: none;">
						</a>
					</td>
				</tr>
				<tr>
					<th style="text-align: center;">영상판독</th>
					<th></th>
				</tr>
				<tr>
					<td class="itprt" style="height: 260px;">
						<a class="acinImagePreview" href="#">
							<img class="acinImage" alt="영상판독" src="" style="height:260px; display: none;">
						</a>
					</td>
					<td></td>
				</tr>
			</tbody>
		</table>
		<br>
		<table class="table_text">
			<colgroup>
				<col width="50%">
				<col width="50%">
			</colgroup>
			<tbody>
				<td colspan="4" style="border-bottom: 1px solid #444444; border-top: 0px; border-left: 0px; border-right: 0px; text-align: left; font-size: 15px">▣ 국·공유재산 실태조사표 사진</td>
				<tr>
					<th style="text-align: center;">현황사진(근경)</th>
					<th style="text-align: center;">현황사진(원경)</th>
				</tr>
				<tr>
					<td class="clsRngeView" style="height: 260px;">
						<a class="acinImagePreview" href="#">
							<img class="acinImage" alt="현황사진(근경)" src="" style="height: 260px; display: none;">
						</a>
						
					</td>
					<td class="dstnView" style="height: 260px;">
						<a class="acinImagePreview" href="#">
							<img class="acinImage" alt="현황사진(원경)" src="" style="height: 260px; display: none;">
						</a>
					</td>
				</tr>
				<tr>
					<th style="text-align: center;">항공사진</th>
					<th style="text-align: center;">지적도</th>
				</tr>
				<tr>
					<td class="baseMap" style="height: 260px;">
						<a class="acinImagePreview" href="#">
							<img class="acinImage" alt="항공사진" src="" style="height: 260px; display: none;">
						</a>
					</td>
					<td class="landRgstr" style="height: 260px;">
						<a class="acinImagePreview" href="#">
							<img class="acinImage" alt="지적도" src="" style="height: 260px; display: none;">
						</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>