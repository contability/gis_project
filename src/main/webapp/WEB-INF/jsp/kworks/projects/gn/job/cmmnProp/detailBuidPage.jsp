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
						<td colspan="3" id="buid_locPlc"></td>
					</tr>
					<tr>
						<th>소재지(도로명)</th>
						<td colspan="3" id="buid_rnAddr"></td>
					</tr>
					<tr>
						<th>재산종류</th>
						<td id="buid_pbpKnd"></td>
						<th>회계구분</th>
						<td id="buid_accCde"></td>
					</tr>
					<tr>
						<th>재산관리관</th>
						<td id="buid_manCde"></td>
						<th>위임관리관</th>
						<td id="buid_mndCde"></td>
					</tr>
					<tr>
						<th>담당부서</th>
						<td id="buid_chrNam"></td>
						<th>처분(매각)제한 유무</th>
						<td id="buid_saleLmt"></td>
					</tr>
					<tr>
						<th>사용허가/대부가능</th>
						<td id="buid_loanYn"></td>
						<th>등기여부</th>
						<td>
							<span id="buid_rstYn"></span>
							<span id="buid_pstNum"></span>
						</td>
					</tr>
					<tr>
						<th>재산소유구분</th>
						<td id="buid_ownCde"></td>
						<th>재산구분</th>
						<td id="buid_meansSe"></td>
					</tr>
					<!-- <tr>
						<th>사업구분</th>
						<td colspan="3"></td>
					</tr> -->
					<tr>
						<th>비고</th>
						<td colspan="3" class='td_text' id="buid_rmkExp"></td>
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
						<th>건물구분</th>
						<td id='buid_bldSe'></td>
						<th>건물용도</th>
						<td id='buid_bldPrp'></td>
					</tr>
					<tr>
						<th>건물명칭</th>
						<td id='buid_bldNam'></td>
						<th>건축가격(원)</th>
						<td id='buid_bldPri'></td>
					</tr>
					<!-- <tr>
						<th>공유지분</th>
						<td colspan="3"></td>
					</tr> -->
					<tr>
						<th>연면적(㎡)</th>
						<td id='buid_grsArea'></td>
						<th>층수</th>
						<td>
							<span>지상 : </span>
							<span id="buid_grdCde"></span>
							<span>, 지하 : </span>
							<span id="buid_ugrdCde"></span>
						</td>
					</tr>
					<tr>
						<th>주구조</th>
						<td id='buid_srcCde'></td>
						<th>지붕구조</th>
						<td id='buid_rfCde'></td>
					</tr>
					<tr>
						<th>건축면적(㎡)</th>
						<td id='buid_bldArea'></td>
						<th>건축일자</th>
						<td id='buid_bldDate'></td>
					</tr>
					<tr>
						<th>회계기준가액(원)</th>
						<td id='buid_autAmnt'></td>
						<th>재산가격(원)</th>
						<td id='buid_ptyPc'></td>
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
					<td colspan="4" style="border-bottom:1px solid #444444; border-top:0px;border-left:0px;border-right:0px;text-align:left;font-size:15px">▣ 취득정보</td>
					<tr>
						<th>취득방법</th>
						<td id='buid_acqCde'></td>
						<th>취득일</th>
						<td id='buid_acqDate'></td>
					</tr>
					<tr>
						<th>취득사유</th>
						<td id='buid_acqPrv'></td>
						<th>취득부서</th>
						<td id='buid_acqDept'></td>
					</tr>
					<tr>
						<th>취득가액(원)</th>
						<td id='buid_acqPc'></td>
						<th>(최초)취득면적(㎡)</th>
						<td id='buid_acsAra'></td>
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
			<!-- <div title="무단점유 사용자">
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
			</div> -->
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
		<!-- 
		<div class="cmmn_miniMap">
			<div style="position:relative;width:100%;height:10%;">
				<div id="index_map_cmmn"></div>
			</div>
		</div> -->
	</div>
</div>