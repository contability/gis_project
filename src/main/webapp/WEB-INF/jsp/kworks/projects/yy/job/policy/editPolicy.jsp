<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<input type = "hidden" name ="sel_policy_edit_proj" id="sel_policy_edit_proj"/>
<input type = "hidden" name ="ftrCde" id="ftrCde" values="PA000"/>
<div class="div_window_edit">
 	<table summary="공간편집 관련 테이블" class="cmmTable v2 ma_b_10">
		<caption>공간편집 테이블</caption>
		<colgroup>
			<col width="25%">
			<col width="">
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">편집대상</th>
				<td id="td_policy_edit_layer_title"></td>
			</tr>
		</tbody>
	</table>
	
	<p class="over_h pa_r_10 ma_b_10">
		<a href="#" id="a_policy_edit_regist"><img src="<c:url value='/images/kworks/map/skin2/new_register_off.png'/>" alt="신규등록" title="신규등록"  /></a>
		<a href="#" id="a_policy_edit_select"><img class='tool_toggle_radio tootip' src="<c:url value='/images/kworks/map/skin2/btn_select3_off.png'/>" alt="선택" title="선택"  /></a>
	</p>
	
	<div id="div_policy_edit_detail" class="display_n">
	
		<p class="over_h ma_b_10 bd_t_c pa_t_10 ma_t_15">
			<span class="f_l">
				<a href="#" id="a_policy_edit_draw"><img class='tool_toggle_radio tootip' src="<c:url value='/images/kworks/map/skin2/btn_draw_on.png' />" alt="그리기" title="그리기" ></a>
				<a href="#" id="a_policy_edit_modify"><img class='tool_toggle_radio tootip' src="<c:url value='/images/kworks/map/skin2/edit_point_off.png'/>" alt="점 편집" title="점 편집" /></a>
				<a href="#" id="a_policy_edit_move"><img class='tool_toggle_radio tootip' src="<c:url value='/images/kworks/map/skin2/transfer_off.png'/>" alt="이동" title="이동" /></a>
			</span>
			<a href="#" id="a_policy_edit_remove" class="f_r"><img src="<c:url value='/images/kworks/map/skin2/btn_del_off.png'/>" alt="삭제" title="삭제"  /></a>
		</p>
		
		<div class="div_policy_edit_markup div_policy_edit_coords">
			<p id="p_policy_edit_coords_menu" class="btnRight ma_t_10">
				<a href="#" id="a_policy_edit_coords_up"><img src="<c:url value='/images/kworks/map/skin2/btn_edit01_on.png' />" alt="위로"></a>
				<a href="#" id="a_policy_edit_coords_down"><img src="<c:url value='/images/kworks/map/skin2/btn_edit02_on.png' />" alt="아래로"></a>
				<a href="#" id="a_policy_edit_coords_add"><img src="<c:url value='/images/kworks/map/skin2/btn_edit03_on.png' />" alt="추가"></a>
				<a href="#" id="a_policy_edit_coords_remove"><img src="<c:url value='/images/kworks/map/skin2/btn_edit04_on.png' />" alt="삭제"></a>
			</p>
			
			<table class="cmmTable ma_t_5" summary="">
				<caption></caption>
				<colgroup>
					<col width="14%">
					<col width="48%">
					<col width="48%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">번호</th>
						<th scope="col">X(경도)</th>
						<th scope="col">Y(위도)</th>
					</tr>
				</thead>
			</table>
			<div class="scrollBox h_170">
				<table class="cmmTable rowLink bd_n" summary="">
					<caption></caption>
					<colgroup>
						<col width="14%">
						<col width="48%">
						<col width="48%">
					</colgroup>
					<tbody id="tby_policy_edit_coords">
					</tbody>
				</table>
			</div>
			<p class="btnRight bd_t_c pa_t_5">
				<a id="a_policy_edit_coords_apply" href="#"><img src="<c:url value='/images/kworks/map/skin2/btn_apply.png' />" alt="적용"></a>
			</p>
		</div>
		
		<div class="div_policy_edit_markup div_policy_edit_move">
			<ul class="listTable bd_t_c pa_t_10">
				<li>
					<span>X 오프셋</span>
					<input id="txt_policy_edit_offset_x" type="text" class="w_210" value="0" /> m
				</li>
				<li>
					<span>Y 오프셋</span>
					<input id="txt_policy_edit_offset_y" type="text" class="w_210" value="0" /> m
				</li>
			</ul>
			<p class="btnRight bd_t_c pa_t_10">
				<a href="#" id="a_policy_edit_offset_apply"><img src="<c:url value='/images/kworks/map/skin2/btn_apply.png' />" alt="적용"></a>
			</p>
		</div>
	
		<div>
			<p class="over_h ma_b_10 bd_t_c pa_t_10 ma_t_15">
				<a href="#" id="a_policy_attEdit_save" class="f_l"><img src="<c:url value='/images/kworks/map/skin2/edit_feature_off.png'/>" alt="속성편집" title="속성편집"  /></a>
				<span class="f_r">
					<a href="#" id="a_policy_edit_merge_features" class="display_n" ><img src="<c:url value='/images/kworks/map/skin2/btn_save3.png'/>" alt="저장" title="저장"  /></a>
					<a href="#" id="a_policy_edit_split_features" class="display_n" ><img src="<c:url value='/images/kworks/map/skin2/btn_save3.png'/>" alt="저장" title="저장"  /></a>
					<a href="#" id="a_policy_edit_save" ><img src="<c:url value='/images/kworks/map/skin2/btn_save3.png'/>" alt="저장" title="저장"  /></a>
					<a href="#" id="a_policy_edit_cancel" ><img src="<c:url value='/images/kworks/map/skin2/btn_cancel2.png'/>" alt="취소" title="취소"  /></a>
				</span>
			</p>
		</div>
		
	</div>
	
	<p class="btnCenter">
		<a href="#" id="a_policy_edit_close"><img src="<c:url value='/images/kworks/map/skin2/new_btn_close.png' />" alt="닫기"></a>
	</p>
	
</div>