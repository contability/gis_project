<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div class="div_print_container">
	<div class="div_preview_board">
		<div class="div_preview_portrait">
  			<div id="replant_doc1" class="div_a4_portrait">
				<div class="div_relant_blank"></div>
				<div class="div_replant_title">생태계 교란식물 제거작업 일지</div>
				<div class="div_replant_blank"></div>
				<table class="approval_table">
					<tr>
					    <th class="th_approval" width="5%" rowspan="3">결<br><br>재</th>
					    <th class="th_approval" width="15%">담당자</th>
						<th class="th_approval" width="15%">담 당</th>
					</tr>
					<tr>
					  	<td class="td_approval" rowspan="2"></td>
						<td class="td_approval" rowspan="2"></td>
					</tr>
				</table>
				<div class="div_replant_blank"></div>
				<table class="job_table">
					<tr>
						<th class="th_job" width="15%">근무일</th>
					    <th class="th_job" width="30%">근무지역</th>
					    <th class="th_job" width="40%">작업내용</th>
					    <th class="th_job" width="12%">비 고</th>
					</tr>
					<tr>
					    <th id="th_job_date" class="th_job" width="15%" rowspan="4">${result.jobDateTo}</th>
					    <td id="td_rem_job" class="td_job" width="30%">제거작업 <br> ${result.jobAddrA}</td>
					    <td id="td_rem_note" class="td_job" width="40%"> -근로 인원 : ${result.jobPerA} 명  <br> -작업내용 : <br> ${result.jobNoteA}</td>
					    <td class="td_job" width="12%"></td>
					</tr>
					<tr>
					    <td id="td_mow_job" class="td_job" width="30%">제거작업(예초기) <br> ${result.jobAddrB}</td>
					    <td id="td_mow_note" class="td_job" width="40%">-근로 인원 : ${result.jobPerB} 명  <br> -작업내용 : <br> ${result.jobNoteB}</td></td>
					    <td class="td_job" width="12%"></td>
					</tr>
					<tr>
					    <td id="td_pla_job" class="td_job" width="30%">식재작업<br> ${result.jobAddrC}</td>
					    <td id="td_pla_note" class="td_job" width="40%">-근로 인원 : ${result.jobPerC} 명  <br> -작업내용 : <br> ${result.jobNoteC}</td></td>
					    <td class="td_job" width="12%"></td>
					</tr>
					<tr>
					    <td id="td_hab_job" class="td_job" width="30%">서식지조사<br> ${result.jobAddrD}</td>
					    <td id="td_hab_note" class="td_job" width="40%">-근로 인원 : ${result.jobPerC} 명  <br> -작업내용 : <br> ${result.jobNoteC}</td></td>
					    <td class="td_job" width="12%"></td>
					</tr>
				</table>
				<div class="div_replant_blank"></div>
				<div>
					<p>
						<span class="div_sub_title">&#10065; 금일추진실적</span>
						<span class="div_unit">(단위 : m<sup>2</sup>)</span>
					</p>				
				</div>
				<table class="performance_table">
					<tr>
					    <th class="th_area" width="10%" rowspan="2">구분</th>
					    <th class="th_area" width="7%" colspan="6">누계</th>
					    <th class="th_area" width="7%" colspan="6">금일</th>
					</tr>
					<tr>
					    <td id="" class="td_area" width="7%">간성</td>
					    <td class="td_area" width="7%">거진</td>
					    <td class="td_area" width="7%">현내</td>
					    <td class="td_area" width="7%">죽왕</td>
					    <td class="td_area" width="7%">토성</td>
					    <td class="td_area" width="9%">계</td>
					    <td class="td_area" width="7%">간성</td>
					    <td class="td_area" width="7%">거진</td>
					    <td class="td_area" width="7%">현내</td>
					    <td class="td_area" width="7%">죽왕</td>
					    <td class="td_area" width="7%">토성</td>
					    <td class="td_area" width="9%">계</td>
					</tr>
					<tr>
					    <td class="td_area">제거<br>작업</td>
					    <td id="td_tot_rem_gs" class="td_value">${result.areaGsA}</td>
					    <td id="td_tot_rem_gj" class="td_value">${result.areaGjA}</td>
					    <td id="td_tot_rem_hn" class="td_value">${result.areaHnA}</td>
					    <td id="td_tot_rem_jw" class="td_value">${result.areaJwA}</td>
					    <td id="td_tot_rem_ts" class="td_value">${result.areaTsA}</td>
					    <td id="td_tot_rem_sum" class="td_value">${result.totalTodayA}</td>
					    <td id="td_cur_rem_gs" class="td_value">${result.totalGsA}</td>
					    <td id="td_cur_rem_gj" class="td_value">${result.totalGjA}</td>
					    <td id="td_cur_rem_hn" class="td_value">${result.totalHnA}</td>
					    <td id="td_cur_rem_jw" class="td_value">${result.totalJwA}</td>
					    <td id="td_cur_rem_ts" class="td_value">${result.totalTsA}</td>
					    <td id="td_cur_rem_sum" class="td_value">${result.totalAllA}</td>
					</tr>
					<tr>
					    <td class="td_area">식재<br>작업</td>
					    <td id="td_tot_pla_gs" class="td_value">${result.areaGsC}</td>
					    <td id="td_tot_pla_gj" class="td_value">${result.areaGsC}</td>
					    <td id="td_tot_pla_hn" class="td_value">${result.areaGsC}</td>
					    <td id="td_tot_pla_jw" class="td_value">${result.areaGsC}</td>
					    <td id="td_tot_pla_ts" class="td_value">${result.areaGsC}</td>
					    <td id="td_tot_pla_sum" class="td_value">${result.totalTodayC}</td>
					    <td id="td_cur_pla_gs" class="td_value">${result.totalGsC}</td>
					    <td id="td_cur_pla_gj" class="td_value">${result.totalGjC}</td>
					    <td id="td_cur_pla_hn" class="td_value">${result.totalHnC}</td>
					    <td id="td_cur_pla_jw" class="td_value">${result.totalJwC}</td>
					    <td id="td_cur_pla_ts" class="td_value">${result.totalTsC}</td>
					    <td id="td_cur_pla_sum" class="td_value">${result.totalAllC}</td>
					</tr>
					<tr>
					    <td class="td_area">서식지<br>조사</td>
					    <td id="td_tot_hab_gs" class="td_value">${result.areaGsD}</td>
					    <td id="td_tot_hab_gj" class="td_value">${result.areaGsD}</td>
					    <td id="td_tot_hab_hn" class="td_value">${result.areaGsD}</td>
					    <td id="td_tot_hab_jw" class="td_value">${result.areaGsD}</td>
					    <td id="td_tot_hab_ts" class="td_value">${result.areaGsD}</td>
					    <td id="td_tot_hab_sum" class="td_value">${result.totalTodayD}</td>
					    <td id="td_cur_hab_gs" class="td_value">${result.totalGsD}</td>
					    <td id="td_cur_hab_gj" class="td_value">${result.totalGjD}</td>
					    <td id="td_cur_hab_hn" class="td_value">${result.totalHnD}</td>
					    <td id="td_cur_hab_jw" class="td_value">${result.totalJwD}</td>
					    <td id="td_cur_hab_ts" class="td_value">${result.totalTsD}</td>
					    <td id="td_cur_hab_sum" class="td_value">${result.totalAllD}</td>
					</tr>					
				</table>
				<div class="div_replant_blank"></div>
				<div class="div_sub_title">&#10065; 특이사항</div>
				<div style="clear:both">
					<div class="remark_title">&#9678; 향후일정</div>
					<div id="rem_note" class="remark_note">${result.etcNoteA}</div>
					<div id="mow_note" class="remark_note">${result.etcNoteB}</div>
					<div id="pla_note" class="remark_note">${result.etcNoteC}</div>
					<div id="hab_note" class="remark_note">${result.etcNoteD}</div>
				</div>
			</div>
			
  			<div id="replant_doc2" class="div_a4_portrait">
				<div class="div_replant_blank"></div>
				<div class="div_replant_title">구간별 제거작업 사진대장[전·후]</div>
				<div class="div_replant_blank"></div>
				<table class="photo_table">
					<tr>
				    	<th class="th_photo_title" width="16%" height=10px >작업일자<br>(근무인원)</th>
				    	<td id="td_rem_date" class="td_photo_note">${result.jobPerA}</td>
				    	<th class="th_photo_title" colspan="3" width="16%">작업장소</th>
				    	<td id="td_rem_address" class="td_photo_note">${result.jobAddrA}</td>
				  	</tr>
  				    <tr>
					    <th class="th_photo_title">제 거<br>작업 전</th>
					    <td id="td_rem_bef" class="td_photo"></td>
					    <th class="th_photo_title" colspan="3">제거<br>작업 후</th>
					    <td id="td_rem_aft"class="td_photo"></td>
					</tr>
					<tr>
				    	<th class="th_photo_title" width="16%" height=10px >작업일자<br>(근무인원)</th>
				    	<td id="td_mow_date" class="td_photo_note" >${result.jobPerB}</td>
				    	<th class="th_photo_title" colspan="3" width="16%">작업장소</th>
				    	<td id="td_mow_address" class="td_photo_note">${result.jobAddrB}</td>
				  	</tr>
  				    <tr>
					    <th class="th_photo_title">제거작업 전<br>(예초기)</th>
					    <td id="td_mow_bef" class="td_photo"></td>
					    <th class="th_photo_title" colspan="3">제거작업 후<br>(예초기)</th>
					    <td id="td_mow_aft" class="td_photo"></td>
					</tr>				  	
					<tr>
				    	<th class="th_photo_title" width="16%" height=10px >작업일자<br>(근무인원)</th>
				    	<td id="td_pla_date" class="td_photo_note" >${result.jobPerC}</td>
				    	<th class="th_photo_title" colspan="3" width="16%">작업장소</th>
				    	<td id="td_pla_address" class="td_photo_note">${result.jobAddrC}</td>
				  	</tr>
  				    <tr>
					    <th class="th_photo_title">식재<br>작업전</th>
					    <td id="td_pla_bef" class="td_photo"></td>
					    <th class="th_photo_title" colspan="3">식재<br>작업후</th>
					    <td id="td_pla_aft" class="td_photo"></td>
					</tr>
					<tr>
				    	<th class="th_photo_title" width="16%" height=10px >작업일자<br>(근무인원)</th>
				    	<td id="td_hab_date" class="td_photo_note" >${result.jobPerD}</td>
				    	<th class="th_photo_title" colspan="3" width="16%">작업장소</th>
				    	<td id="td_hab_address" class="td_photo_note">${result.jobAddrD}</td>
				  	</tr>
  				    <tr>
					    <th class="th_photo_title">서식지조사<br>작업</th>
					    <td id="td_hab_bef" class="td_photo"></td>
					    <th class="th_photo_title" colspan="3">서식지조사<br>작업</th>
					    <td id="td_hab_aft" class="td_photo"></td>
					</tr>				  	
				</table>				
			</div>
			
		</div>
	</div>
	<div class="div_preview_toolbar" data-options="region:'center'">
		<a href="#" class="a_prev ma_t_5">작업일지</a>
		<a href="#" class="a_print ma_t_5">출력</a>
		<a href="#" class="a_next ma_t_5">사진대장</a>
	</div>		
</div>		