<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div class="div_print_container">
	<div class="div_preview_board">
		<div class="div_preview_portrait">
  			<div id="explant_doc" class="div_a4_portrait">
				<div class="div_explant_blank"></div>
				<div class="div_explant_title">생태계 교란식물 현지조사표</div>
				<div class="div_explant_blank"></div>
				<div id="div_page_seq" class="div_explant_sequence">조사표 번호 : 고성-2019-000</div>
				<div class="div_explant_blank"></div>
				
 				<table class="div_table">
				  <tr>
				    <td class="tg-bn4o">조사대상 종</td>
				    <td id="td_exp_type" class="tg-baqh" colspan="3">${result.expType}</td>
				  </tr>
				  <tr>
				    <td class="tg-bn4o">조사자(성명)</td>
				    <td id="td_inv_name" class="tg-baqh">${result.invNam}</td>
				    <td class="tg-bn4o">조사 일시</td>
				    <td id="td_inv_date" class="tg-baqh">${result.invDate}</td>
				  </tr>
				  <tr>
				    <td class="tg-bn4o">조사장소(주소)</td>
				    <td id="td_inv_address" class="tg-baqh" colspan="3">${result.address}</td>
				  </tr>
				  <tr>
				    <td class="tg-bn4o">분포지 유형<br></td>
				    <td id="td_dis_type" class="tg-baqh" colspan="3">${result.disType}</td>
				  </tr>
				  <tr>
				    <td class="tg-bn4o">분포 면적</td>
				    <td id="td_dis_area" class="tg-lap0">${result.disArea}m <sup>2</sup></td>
				    <td class="tg-bn4o" rowspan="2">분포밀도</td>
				    <td id="td_dis_dens" class="tg-lap0" rowspan="2">${result.disDens} 개체 / m<sup>2</sup></td>
				  </tr>
				  <tr>
				    <td class="tg-bn4o">평균 키</td>
				    <td id="td_exp_size" class="tg-lap0">${result.expSize} cm</td>
				  </tr>
				  <tr>
				    <td class="tg-bn4o">생육 단계</td>
				    <td id="td_exp_step" class="tg-baqh" colspan="3">${result.expStep}</td>
				  </tr>
				  <tr>
				    <td class="tg-bn4o" colspan="4">분포현황도(약도)</td>
				  </tr>
				  <tr>
				    <td id="imageFilNo1" class="td-img" colspan="2"></td>
				    <td id="imageFilNo2" class="td-img" colspan="2"></td>
				  </tr>
				  <tr>
				    <td class="tg-bn4o" colspan="4">분포 현황 사진 자료</td>
				  </tr>
				  <tr>
				    <td id="imageFilNo3" class="td-img" colspan="2"></td>
				    <td id="imageFilNo4" class="td-img" colspan="2"></td>
				  </tr>
				  <tr>
				    <td class="td-title">특이사항</td>
				    <td id="td_inv_note" class="td-note" colspan="3">${result.invNote}</td>
				  </tr>
				</table>				
			</div>
		</div>
	</div>
	<div class="div_preview_toolbar" data-options="region:'center'">
		<a href="#" class="a_print ma_t_5">출력</a>
	</div>		
</div>