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
					<td colspan="8" style="border-bottom:1px solid #444444; border-top:0px;border-left:0px;border-right:0px;text-align:left;font-size:15px">▣ 대부내역</td>
					<tr>
						<th>계약시작일</th>
						<td id="Loan_crtStr"></td>
						<th>계약종료일</th>
						<td id="Loan_crtEnd"></td>
					</tr>
					<tr>
						<th>계약일</th>
						<td id="Loan_crtDate"></td>
						<th>해지일</th>
						<td id="Loan_tntDate"></td>
					</tr>
					<tr>
						<th>해지사유</th>
						<td colspan="3" id="Loan_tntResn"></td>
					</tr>
					<tr>
						<th>대부허가요금</th>
						<td id="Loan_perAmt"></td>
						<th>대부허가일수</th>
						<td id="Loan_lonDay"></td>
					</tr>
					<!-- <tr>
						<th>무상시관련법규</th>
						<td colspan="3" id="Loan_retLrg"></td>
					</tr> -->
					<tr>
						<th>대부허가기간 시작일자</th>
						<td id="Loan_lonStart"></td>
						<th>대부허가기간 종료일자</th>
						<td id="Loan_lonEnd"></td>
					</tr>
					<tr>
						<th>대부면적1(㎡)</th>
						<td id="Loan_lonArea"></td>
						<th>대부면적2(㎡)</th>
						<td id="Loan_slonArea"></td>
					</tr>
					<tr>
						<th>건물대부면적</th>
						<td id="Loan_blLonAr"></td>
						<th>건물바닥면적</th>
						<td id="Loan_blBdAr"></td>
					</tr>
					<tr>
						<th>대부목적</th>
						<td colspan="3" id="Loan_lonPup"></td>
					</tr>
					<td colspan="8" style="border-bottom:1px solid #444444; border-top:0px;border-left:0px;border-right:0px;text-align:left;font-size:15px">▣ 사용자 정보</td>
					<tr>
						<th>성명/명칭</th>
						<td id="Loan_empNam"></td>
						<th>사용자구분</th>
						<td id="Loan_empSe"></td>
					</tr>
					<tr>
						<th>법정동주소</th>
						<td colspan="3" id="Loan_empBjd"></td>
					</tr>
					<tr>
						<th>도로명주소</th>
						<td colspan="3" id="Loan_empRn"></td>
					</tr>
					<tr>
						<th>연락처1</th>
						<td colspan="3" id="Loan_empNum"></td>
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
		
		<!-- <div class="window_footer_white">
			<div class="button_flat">
				<button id="btn_geom_insert" class="button_flat_normal btn_blue">등록</button>
				<button id="btn_geom_delete" class="button_flat_normal btn_blue">삭제</button>
				
				<br>
				<div class="button_drow_event">
					<button id="btn_data_drow" class="button_flat_normal btn_blue">그리기</button>
					<button id="btn_data_pointUp" class="button_flat_normal btn_blue">점편집</button>
					<button id="btn_data_insert" class="button_flat_normal btn_blue">저장</button>	
					<button id="btn_data_exit" class="button_flat_normal btn_blue">취소</button>
				</div>
				
			</div>
		</div> -->
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
		<div id="div_loan_map_" class="cmmn_reges_buttom"></div>
	</div>
	
	<!-- 지도영역 -->
	<!-- <div id="div_loan_map_" class="cmmn_reges_buttom"></div> -->
	<!-- <div class="tab_wrap">
		<div class="cmmn_tabs">
			<div title="산출내역">
				<div style="margin:5px 0;"></div>
	    		<div class="easyui-layout" style="width:100%; height: 300px;">
	    			<div data-options="region:'center'">
			            <table class="grid_result_comp" style="height:100%;">
			            </table>
	    			</div>
				</div>
				<div class="window_footer">
					<div class="button_flat">
						<button id="btn__selectOne" class="button_flat_normal btn_blue">대장조회</button>
					</div>
			    </div>
			</div>
		</div>
		<div class="cmmn_miniMap">
			<div style="position:relative;width:100%;height:100%;">
				<div id="index_map_cmmn"></div>
			</div>
		</div>
	</div> -->
</div>