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
					<td colspan="8" style="border-bottom:1px solid #444444; border-top:0px;border-left:0px;border-right:0px;text-align:left;font-size:15px">▣ 재산정보 </td>
					<tr>
						<th>소재지</th>
						<td colspan="3" id="sub_locPlc"></td>
					</tr>
					<tr>
						<th>재산번호</th>
						<td id="sub_prtIdn"></td>
						<th>재산종류</th>
						<td id="sub_pbpKnd"></td>
					</tr>
					<tr>
						<th>재산명</th>
						<td id="sub_prtNam"></td>
						<th>재산소유구분</th>
						<td id="sub_ownCde"></td>
					</tr>
					<tr>
						<th>재산용도</th>
						<td id="sub_prsCde"></td>
						<th>행정재산</th>
						<td id="sub_amnCde"></td>
					</tr>
					<tr>
						<th>회계구분</th>
						<td id="sub_accCde"></td>
						<th>재산관리관</th>
						<td id="sub_manCde"></td>
					</tr>
					<tr>
						<th>담당부서</th>
						<td id="sub_chrNam"></td>
						<th>위임관리관</th>
						<td id="sub_mndCde"></td>
					</tr>
					<tr>
						<th>재산가격(원)</th>
						<td id="sub_ptyPc"></td>
						<th>회계기준가격(원)</th>
						<td id="sub_autAmnt"></td>
					</tr>
					<tr>
						<th>대부가능여부</th>
						<td id="sub_loanYn"></td>
						<th>매각제한유무</th>
						<td id="sub_saleLmt"></td>
					</tr>
				</tbody>
			</table>
			
			<table class="table_text">
				<colgroup>
					<col width="20%">
					<col width="30%">
					<col width="20%">
					<col width="30%">
				</colgroup>
				<tbody>
					<td colspan="8" style="border-bottom:1px solid #444444; border-top:0px;border-left:0px;border-right:0px;text-align:left;font-size:15px">▣ 무단점유</td>
					<tr>
						<th>무단점일련번호</th>
						<td id="occp_ocpIdn"></td>
						<th>재산종류</th>
						<td id="occp_pbpKnd"></td>
					</tr>
					<tr>
						<th>물건지주소</th>
						<td colspan="3" id="occp_ocpAdr"></td>
					</tr>
					<tr>
						<th>점유시작일</th>
						<td id="occp_ocpStr"></td>
						<th>점유종료일</th>
						<td id="occp_ocpEnd"></td>
					</tr>
					<tr>
						<th>무단점유면적(㎡)</th>
						<td id="occp_ocpAra"></td>
						<th>무단점유목적</th>
						<td id="occp_ocpPrs"></td>
					</tr>
					<tr>
						<th>귀속요율-시도분</th>
						<td id="occp_rvrSi"></td>
						<th>귀속요율-시군구</th>
						<td id="occp_rvrGu"></td>
					</tr>
					<td colspan="8" style="border-bottom:1px solid #444444; border-top:0px;border-left:0px;border-right:0px;text-align:left;font-size:15px">▣ 사용자 정보</td>
					<tr>
						<th>성명/명칭</th>
						<td id="occp_empNam"></td>
						<th>연락처</th>
						<td id="occp_empNum"></td>
					</tr>
					<tr>
						<th>도로명주소</th>
						<td id="occp_empRn"></td>
						<th>법정동주소</th>
						<td id="occp_empBjd"></td>
					</tr>
					<tr>
						<th>사용자구분</th>
						<td id="occp_empSe"></td>
						<th>비고</th>
						<td id="occp_rmkExp"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<div class="cmmn_reges_right">
		<div class="easyui-layout" style="width:100%; height: 200px;">
			<div data-options="region:'center'">
				<table class="grid_result_geom" style="height:100%;">
				</table>
			</div>
		</div>
	
		<div class="window_footer">
			<div class="button_flat">
				<button id="btn_geom_insert" class="button_flat_normal btn_blue">등록</button>
				<button id="btn_geom_delete" class="button_flat_normal btn_blue">삭제</button>
			</div>
		</div>
	
		<div class="btn_data window_footer">
			<div class="button_flat">
				<button id="btn_data_drow" class="button_flat_normal btn_blue">그리기</button>
				<button id="btn_data_pointUp" class="button_flat_normal btn_blue">점편집</button>
				<button id="btn_data_insert" class="button_flat_normal btn_blue">저장</button>	
				<button id="btn_data_exit" class="button_flat_normal btn_blue">취소</button>
			</div>
		</div>
		<div class="btn_data_empty window_footer"></div>
		<div id="div_oous_map_" class="cmmn_reges_buttom"></div>
	</div>
</div>