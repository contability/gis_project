<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>
<input type="hidden" name="area_use_prtIdn"/>
<input type="hidden" name="area_use_pbpKnd"/>
<input type="hidden" name="area_use_dataId"/>
<input type="hidden" name="area_use_objectid"/>
<div id="window_container">
	<div class="div_loading">
		<img src="<c:url value='/images/kworks/common/contact-loading.gif' />" alt="로딩중" title="로딩중" />
	</div>
	<div class="window_article">
		<div id="div_useArea_tabs">
			<div title="필지정보">
				<div id="div_kras_tabs">
					<div title ="토지대장">
						<table class="table_text">
							<colgroup>
								<col width="22%">
								<col width="*">
							</colgroup>
							<tbody id="landInfo">
								<tr>
									<th>소재지</th>
									<td id="landLocPlc"></td>
								</tr>
								<tr>
									<th>지목</th>
									<td id="landJimokNm"></td>
								</tr>
								<tr>
									<th>면적</th>
									<td id="landParea"></td>
								</tr>
								<tr>
									<th>토지이동사유</th>
									<td id="landMovRsnCdNm"></td>
								</tr>
								<tr>
									<th>토지이동일자</th>
									<td id="landMovYmd"></td>
								</tr>
								<tr>
									<th>소유구분</th>
									<td id="landOwnGbnNm"></td>
								</tr>
								<tr>
									<th>소유자명</th>
									<td id="landOwnNm"></td>
								</tr>
								<!-- <tr>
									<th>등록번호</th>
									<td id="land_"></td>
								</tr> -->
								<tr>
									<th>소유자주소</th>
									<td id="landOwnerAddr"></td>
								</tr>
								<tr>
									<th>소유권변동원인</th>
									<td id="landOwnRgtChgRsnCdNm"></td>
								</tr>
								<!-- <tr>
									<th>소유권변동일자</th>
									<td id="land_"></td>
								</tr> -->
								<tr>
									<th>공유인수</th>
									<td id="landShrCnt"></td>
								</tr>
								<tr>
									<th>개별공시지가</th>
									<td id="landPannJiga"></td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- <div title ="공유인연명부">
					</div> -->
					<div title ="용도지역">
						<table class="table_text">
							<colgroup>
								<col width="50%">
								<col width="50%">
								<%-- <col width="*"> --%>
							</colgroup>
							<tbody id="area_use_data">
								<tr>
									<th style="text-align: center;">연속주제</th>
									<th style="text-align: center;">명칭</th>
									<!-- <th style="text-align: center;">라벨</th> -->
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div title="국공유지정보">
				<div class="div_window_select_result_tabs">
					<div title ="재산일반">
						<table class="table_text">
							<colgroup>
								<col width="30%">
								<col width="70%">
							</colgroup>
							<tbody>
								<tr>
									<th>소재지</th>
									<td id="area_use_locPlc"></td>
								</tr>
								<tr>
									<th>재산명</th>
									<td id="area_use_prtNam"></td>
								</tr>
								<tr>
									<th>재산종류</th>
									<td id="area_use_pbpKnd"></td>
								</tr>
								<tr>
									<th>회계구분</th>
									<td id="area_use_accCde"></td>
								</tr>
								<tr>
									<th>재산관리관</th>
									<td id="area_use_manCde"></td>
								</tr>
								<tr>
									<th>사용허가/대부가능</th>
									<td id="area_use_loanYn"></td>
								</tr>
								<tr>
									<th>등기여부</th>
									<td>
										<span id="area_use_rstYn"></span>
										<span id="area_use_pstNum"></span>
									</td>
								</tr>
								<tr>
									<th>재산소유구분</th>
									<td id="area_use_ownCde"></td>
								</tr>
								<tr>
									<th>비고</th>
									<td colspan="3" id="area_use_rmkExp"></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div title ="대부현황">
						<table class="table_text">
							<colgroup>
								<col width="20%">
								<col width="20%">
								<col width="20%">
								<col width="20%">
								<col width="20%">
							</colgroup>
							<tbody id="area_use_loan">
								<tr>
									<th style="text-align: center;">재산종류</th>
									<th style="text-align: center;">계약시작일</th>
									<th style="text-align: center;">계약종료일</th>
									<th style="text-align: center;">대부면적(㎡)</th>
									<th style="text-align: center;">대부목적</th>
								</tr>
							</tbody>
						</table>
					</div>
					<%-- <div title ="실태조사">
						<table class="table_text">
							<colgroup>
								<col width="20%">
								<col width="20%">
								<col width="20%">
								<col width="20%">
								<col width="20%">
							</colgroup>
							<tbody id ="area_use_acex">
								<tr>
									<th style="text-align: center;">소재지</th>
									<th style="text-align: center;">조사자</th>
									<th style="text-align: center;">관리구분</th>
									<th style="text-align: center;">관리여부</th>
									<th style="text-align: center;">유휴상태</th>
								</tr>
							</tbody>
						</table>
					</div> --%>
				</div>
			</div>
		</div>
		<%-- <div class="div_window_select_result_tabs">
			<div title ="재산일반">
				<table class="table_text">
					<colgroup>
						<col width="30%">
						<col width="70%">
					</colgroup>
					<tbody>
						<tr>
							<th>소재지</th>
							<td id="area_use_locPlc"></td>
						</tr>
						<tr>
							<th>재산명</th>
							<td id="area_use_prtNam"></td>
						</tr>
						<tr>
							<th>재산종류</th>
							<td id="area_use_pbpKnd"></td>
						</tr>
						<tr>
							<th>회계구분</th>
							<td id="area_use_accCde"></td>
						</tr>
						<tr>
							<th>재산관리관</th>
							<td id="area_use_manCde"></td>
						</tr>
						<tr>
							<th>사용허가/대부가능</th>
							<td id="area_use_loanYn"></td>
						</tr>
						<tr>
							<th>등기여부</th>
							<td>
								<span id="area_use_rstYn"></span>
								<span id="area_use_pstNum"></span>
							</td>
						</tr>
						<tr>
							<th>재산소유구분</th>
							<td id="area_use_ownCde"></td>
						</tr>
						<tr>
							<th>비고</th>
							<td colspan="3" id="area_use_rmkExp"></td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div title ="대부현황">
				<table class="table_text">
					<colgroup>
						<col width="20%">
						<col width="20%">
						<col width="20%">
						<col width="20%">
						<col width="20%">
					</colgroup>
					<tbody id="area_use_loan">
						<tr>
							<th style="text-align: center;">재산종류</th>
							<th style="text-align: center;">계약시작일</th>
							<th style="text-align: center;">계약종료일</th>
							<th style="text-align: center;">대부면적1(㎡)</th>
							<th style="text-align: center;">대부목적</th>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div title ="실태조사">
				
				<table class="table_text">
					<colgroup>
						<col width="20%">
						<col width="20%">
						<col width="20%">
						<col width="20%">
						<col width="20%">
					</colgroup>
					<tbody id ="area_use_acex">
						<tr>
							<th style="text-align: center;">소재지</th>
							<th style="text-align: center;">조사자</th>
							<th style="text-align: center;">관리구분</th>
							<th style="text-align: center;">관리여부</th>
							<th style="text-align: center;">유휴상태</th>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div title ="용도지역">
				
				<table class="table_text">
					<colgroup>
						<col width="33%">
						<col width="33%">
						<col width="*">
					</colgroup>
					<tbody id="area_use_data">
						<tr>
							<th style="text-align: center;">연속주제</th>
							<th style="text-align: center;">명칭</th>
							<th style="text-align: center;">라벨</th>
						</tr>
					</tbody>
				</table>
			</div>
		</div> --%>
	</div>
</div>
<!-- <div class="window_footer">
	<div class="button_flat">
		<button id="btn_read" class="button_flat_normal btn_blue">상세보기</button>
		<button id="btn_close" class="button_flat_normal btn_blue">닫기</button>
	</div>
</div> -->
	