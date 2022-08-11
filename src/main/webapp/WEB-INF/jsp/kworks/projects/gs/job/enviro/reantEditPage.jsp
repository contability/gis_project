<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div class="div_window_replantedit">

 	<table summary="공간편집 관련 테이블" class="cmmTable v2 ma_b_10">
		<colgroup>
			<col width="25%">
			<col width="">
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">편집대상</th>
				<td id="td_edit_replant_title"></td>
			</tr>
		</tbody>
	</table>
	
	<p class="over_h pa_r_10 ma_b_10">
		<a href="#" id="a_replant_regist"><img class='edit_toggle_regist' src="<c:url value='/images/kworks/map/skin2/new_register_off.png'/>" alt="신규등록" title="신규등록"  /></a>
		<a href="#" id="a_replant_select"><img class='edit_toggle_select' src="<c:url value='/images/kworks/map/skin2/btn_select3_off.png'/>" alt="선택" title="선택"  /></a>
	</p>
	
	<div id="div_edit_detail" class="display_n">
	
		<p class="over_h ma_b_10 bd_t_c pa_t_10 ma_t_15">
			<span class="f_l">
				<a href="#" id="a_replant_draw"><img class='edit_toggle_draw' src="<c:url value='/images/kworks/map/skin2/btn_draw_off.png'/>" alt="그리기" title="그리기" ></a>
				<a href="#" id="a_replant_move"><img class='edit_toggle_move' src="<c:url value='/images/kworks/map/skin2/transfer_off.png'/>" alt="이동" title="이동" /></a>
			</span>
			<span class="f_r">
				<a href="#" id="a_edit_remove"><img src="<c:url value='/images/kworks/map/skin2/btn_del_off.png'/>" alt="삭제" title="삭제"  /></a>
			</span>
		</p>
	
		<div class="div_edit_markup div_edit_coords">
			<table class="cmmTable ma_t_5" summary="">
				<colgroup>
					<col width="100%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">작업지역(주소)</th>
					</tr>
				</thead>
				<tbody id="tby_edit_address">
					<tr>
						<td id="td_edit_address" class="h_30"></td>
					</tr>
				</tbody>
			</table>
			<table class="cmmTable ma_t_5" summary="">
				<colgroup>
					<col width="100%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">작업면적</th>
					</tr>
				</thead>
				<tbody id="tby_edit_area">
					<tr>
						<td id="td_edit_area" class="h_30">0.0 m<sup>2</sup></td>
					</tr>
				</tbody>
			</table>
			<table class="cmmTable ma_t_5" summary="">
				<colgroup>
					<col width="50%">
					<col width="50%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">중심점 X</th>
						<th scope="col">중심점 Y</th>
					</tr>
				</thead>
				<tbody id="tby_edit_coords">
					<tr>
						<td id="txt_edit_coords_coord_x" class="w_120 h_30"></td>
						<td id="txt_edit_coords_coord_y" class="w_120 h_30"></td>
					</tr>
				</tbody>
			</table>
		</div>
	
		<div>
			<p class="over_h ma_b_10 bd_t_c pa_t_10 ma_t_15">
				<a href="#" id="a_property_save" class="f_l"><img src="<c:url value='/images/kworks/map/skin2/edit_feature_off.png'/>" alt="속성편집" title="속성편집"  /></a>
				<span class="f_r">
					<a href="#" id="a_replant_save" ><img src="<c:url value='/images/kworks/map/skin2/btn_save3.png'/>" alt="저장" title="저장"  /></a>
				</span>
			</p>
		</div>
	
	</div>
	
	<p class="btnCenter">
		<a href="#" id="a_replant_close"><img src="<c:url value='/images/kworks/map/skin2/new_btn_close.png'/>" alt="닫기"></a>
	</p>
	
</div>