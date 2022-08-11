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
					<td colspan="4" style="border-bottom:1px solid #444444; border-top:0px;border-left:0px;border-right:0px;text-align:left;font-size:15px">▣ 관리항목 </td>
					<tr>
						<th>소재지(지번)</th>
						<td colspan="3" id="prop_locPlc"></td>
					</tr>
					<tr>
						<th>소재지(도로명)</th>
						<td colspan="3" id="prop_rnAddr"></td>
					</tr>
					<tr>
						<th>재산종류</th>
						<td id="prop_pbpKnd"></td>
						<th>회계구분</th>
						<td id="prop_accCde"></td>
					</tr>
					<tr>
						<th>재산관리관</th>
						<td id="prop_manCde"></td>
						<th>위임관리관</th>
						<td id="prop_mndCde"></td>
					</tr>
					<tr>
						<th>담당부서</th>
						<td id="prop_chrNam"></td>
						<th>처분(매각)제한 유무</th>
						<td id="prop_saleLmt"></td>
					</tr>
					<tr>
						<th>사용허가/대부가능</th>
						<td id="prop_loanYn"></td>
						<th>등기여부</th>
						<td>
							<span id="prop_rstYn"></span>
							<span id="prop_pstNum"></span>
						</td>
					</tr>
					<tr>
						<th>재산소유구분</th>
						<td colspan="3" id="prop_ownCde"></td>
					</tr>
					<!-- <tr>
						<th>사업구분</th>
						<td colspan="3"></td>
					</tr> -->
					<tr>
						<th>비고</th>
						<td colspan="3" class='td_text' id="prop_rmkExp"></td>
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
					<td colspan="6" style="border-bottom:1px solid #444444; border-top:0px;border-left:0px;border-right:0px;text-align:left;font-size:15px">▣ 재산항목 </td>
					<tr>
						<!-- <th>토지명칭</th>
						<td></td> -->
						<th>공유지분</th>
						<td id='prop_cnrQta'></td>
						<th>공시지가(원)</th>
						<td id='prop_olnlp'></td>
					</tr>
					<tr>
						<th>지목(공부)</th>
						<td id='prop_bmlCde'></td>
						<th>지목(현황)</th>
						<td id='prop_mokCde'></td>
					</tr>
					<!-- <tr>
						<th>공유지분</th>
						<td colspan="3"></td>
					</tr> -->
					<tr>
						<th>면적(공부)(㎡)</th>
						<td id='prop_area'></td>
						<th>실면적(㎡)</th>
						<td id='prop_parea'></td>
					</tr>
					<tr>
						<th>재산가격(원)</th>
						<td id='prop_ptyPc'></td>
						<th>회계기준가액(원)</th>
						<td id='prop_autAmnt'></td>
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
					<td colspan="4" style="border-bottom:1px solid #444444; border-top:0px;border-left:0px;border-right:0px;text-align:left;font-size:15px">▣ 취득정보 및 도시계획정보</td>
					<tr>
						<th>취득방법</th>
						<td id='prop_acqCde'></td>
						<th>취득일</th>
						<td id='prop_acqDate'></td>
					</tr>
					<tr>
						<th>취득사유</th>
						<td id='prop_acqPrv'></td>
						<th>취득부서</th>
						<td id='prop_acqDept'></td>
					</tr>
					<tr>
						<th>취득가액(원)</th>
						<td id='prop_acqPc'></td>
						<th>(최초)취득면적(㎡)</th>
						<td id='prop_acqAra'></td>
					</tr>
					<tr>
						<th>용도지역</th>
						<td id='prop_spfc'></td>
						<th>도시계획지구</th>
						<td id='prop_ctyPlan'></td>
					</tr>
					<tr>
						<th>계획시설</th>
						<td id='prop_planFty'></td>
						<th>개발사업</th>
						<td id='prop_dwk'></td>
					</tr>
					<tr>
						<th>계획사업</th>
						<td colspan="3" id='prop_planBns'></td>
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
			            <table class="grid_result_loan" style="height:100%;">
			            </table>
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
	    		<div class="easyui-layout" style="width:100%;height:300px;">
	    			<div data-options="region:'center'">
			            <table class="grid_result_occp" style="height: 100%;">
			            </table>
	    			</div>
				</div>
				<div class="window_footer_white">
					<div class="button_flat">
						<button id="btn_occp_selectOne" class="button_flat_normal btn_blue">대장조회</button>
					</div>
			    </div>
			</div>
			<!-- <div title="실태조사">
				<div style="margin:5px 0;"></div>
				<div class="easyui-layout" style="width:100%;height:300px;">
					<div data-options="region:'center'">
						<table class="grid_result_acex" style="height: 100%;">
						</table>
					</div>
				</div>
				<div class="window_footer_white">
					<div class="button_flat">
						<button id="btn_acex_selectOne" class="button_flat_normal btn_blue">대장조회</button>
					</div>
				</div>
			</div> -->
		</div>
		<!-- <div class="cmmn_miniMap">
			<div style="position:relative;width:100%;height:100%;">
				<div id="index_map_cmmn"></div>
			</div>
		</div> -->
	</div>
</div>