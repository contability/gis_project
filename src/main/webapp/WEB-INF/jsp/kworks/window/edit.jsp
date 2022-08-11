<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<c:set var="isEditUpwid"><spring:message code='Extensions.EditUpwid.UseAt' /></c:set>	<!-- Lks : 상월/하월 편집 -->
<c:choose>
	<c:when test="${isEditUpwid eq true}">
		<div class="div_window_edit_500">
	</c:when>
	<c:otherwise>
		<div class="div_window_edit">
	</c:otherwise>
</c:choose>
 	<table summary="공간편집 관련 테이블" class="cmmTable v2 ma_b_10">
		<caption>공간편집 테이블</caption>
		<colgroup>
			<col width="25%">
			<col width="">
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">편집대상</th>
				<td id="td_edit_layer_title"></td>
			</tr>
		</tbody>
	</table>
	
	<p class="over_h pa_r_10 ma_b_10">
		<a href="#" id="a_edit_regist"><img src="<c:url value='/images/kworks/map/skin2/new_register_off.png'/>" alt="신규등록" title="신규등록"  /></a>
		<a href="#" id="a_edit_select"><img class='tool_toggle_radio tootip' src="<c:url value='/images/kworks/map/skin2/btn_select3_off.png'/>" alt="선택" title="선택"  /></a>
	</p>
	
	<div id="div_edit_detail" class="display_n">
	
		<p class="over_h ma_b_10 bd_t_c pa_t_10 ma_t_15">
			<span class="f_l">
				<a href="#" id="a_edit_draw"><img class='tool_toggle_radio tootip' src="<c:url value='/images/kworks/map/skin2/btn_draw_on.png' />" alt="그리기" title="그리기" ></a>
				<a href="#" id="a_edit_modify"><img class='tool_toggle_radio tootip' src="<c:url value='/images/kworks/map/skin2/edit_point_off.png'/>" alt="점 편집" title="점 편집" /></a>
				<a href="#" id="a_edit_move"><img class='tool_toggle_radio tootip' src="<c:url value='/images/kworks/map/skin2/transfer_off.png'/>" alt="이동" title="이동" /></a>
				<a href="#" id="a_edit_clip"><img class='tool_toggle_radio tootip' src="<c:url value='/images/kworks/map/skin2/clip_off.png'/>" alt="자르기" title="자르기" /></a>
				<a href="#" id="a_edit_merge"><img class='tool_toggle_radio tootip' src="<c:url value='/images/kworks/map/skin2/merge_off.png'/>" alt="병합" title="병합" /></a>
				<a href="#" id="a_edit_split"><img class='tool_toggle_radio tootip' src="<c:url value='/images/kworks/map/skin2/split_off.png'/>" alt="분할" title="분할" /></a>
				<c:if test="${isEditUpwid eq true}" >
					<a href="#" id="a_edit_upwid"><img class='tool_toggle_radio tootip' src="<c:url value='/images/kworks/map/skin2/upwid_off.png'/>" alt="상월" title="상월" /></a>
					<a href="#" id="a_edit_downwid"><img class='tool_toggle_radio tootip' src="<c:url value='/images/kworks/map/skin2/downwid_off.png'/>" alt="하월" title="하월" /></a>
				</c:if>
			</span>
			<a href="#" id="a_edit_remove" class="f_r"><img src="<c:url value='/images/kworks/map/skin2/btn_del_off.png'/>" alt="삭제" title="삭제"  /></a>
		</p>
		
		<div class="div_edit_markup div_edit_coords">
		
			<ul class="listTable">
				<li>
					<span><input type="checkbox" id="chk_edit_snap" /><label for="chk_edit_snap">스냅대상</label></span>
					<select id="sel_edit_snap" >
					</select>
					<a href="#"><img id="img_edit_snap_view" src="<c:url value='/images/kworks/map/skin2/visibility_off.png' />" alt=""></a>
				</li>
			</ul>
			
			<ul class="listTable">
				<li>
					<span><label for="sel_edit_proj">좌표계</label></span>
					<select id="sel_edit_proj">
					</select>
				</li>
			</ul>
			
			<p id="p_edit_coords_menu" class="btnRight ma_t_10">
				<a href="#" id="a_edit_coords_reverse" class="f_l" ><img src="<c:url value='/images/kworks/map/skin2/btn_edit_revert_on.png' />" alt="방향전환"></a>
				<a href="#" id="a_edit_coords_up"><img src="<c:url value='/images/kworks/map/skin2/btn_edit01_on.png' />" alt="위로"></a>
				<a href="#" id="a_edit_coords_down"><img src="<c:url value='/images/kworks/map/skin2/btn_edit02_on.png' />" alt="아래로"></a>
				<a href="#" id="a_edit_coords_add"><img src="<c:url value='/images/kworks/map/skin2/btn_edit03_on.png' />" alt="추가"></a>
				<a href="#" id="a_edit_coords_remove"><img src="<c:url value='/images/kworks/map/skin2/btn_edit04_on.png' />" alt="삭제"></a>
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
					<tbody id="tby_edit_coords">
					</tbody>
				</table>
			</div>
			<p class="btnRight bd_t_c pa_t_5">
				<a id="a_edit_coords_apply" href="#"><img src="<c:url value='/images/kworks/map/skin2/btn_apply.png' />" alt="적용"></a>
			</p>
		</div>
		
		<div class="div_edit_markup div_edit_move">
			<ul class="listTable bd_t_c pa_t_10">
				<li>
					<span>X 오프셋</span>
					<input id="txt_edit_offset_x" type="text" class="w_210" value="0" /> m
				</li>
				<li>
					<span>Y 오프셋</span>
					<input id="txt_edit_offset_y" type="text" class="w_210" value="0" /> m
				</li>
			</ul>
			<p class="btnRight bd_t_c pa_t_10">
				<a href="#" id="a_edit_offset_apply"><img src="<c:url value='/images/kworks/map/skin2/btn_apply.png' />" alt="적용"></a>
			</p>
		</div>
		
		<div class="div_edit_markup div_edit_clip">
			<table class="cmmTable ma_t_5" summary="">
				<caption></caption>
				<colgroup>
					<col width="50%">
					<col width="50%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">번호</th>
						<th scope="col">지우기</th>
					</tr>
				</thead>
			</table>
			<div class="scrollBox h_100">
				<table class="cmmTable rowLink bd_n" summary="">
					<caption></caption>
					<colgroup>
						<col width="50%">
						<col width="50%">
					</colgroup>
					<tbody id="tby_edit_clip_choice">
					</tbody>
				</table>
			</div>
		</div>
		
		<div class="div_edit_markup div_edit_merge">
			<table class="cmmTable ma_t_5" summary="">
				<caption></caption>
				<colgroup>
					<col width="50%">
					<col width="50%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">번호</th>
						<th scope="col">관리번호</th>
					</tr>
				</thead>
			</table>
			<div class="scrollBox h_100">
				<table class="cmmTable rowLink bd_n" summary="">
					<caption></caption>
					<colgroup>
						<col width="50%">
						<col width="50%">
					</colgroup>
					<tbody id="tby_edit_merge_choice">
					</tbody>
				</table>
			</div>
		</div>
		
		<div class="div_edit_markup div_edit_split">
			<div id="div_split_coords_container" class="display_n">
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
						<tbody id="tby_edit_split_coords">
						</tbody>
					</table>
				</div>
				<p class="btnRight bd_t_c pa_t_5">
					<a id="a_edit_split_apply" href="#"><img src="<c:url value='/images/kworks/map/skin2/btn_apply.png' />" alt="적용"></a>
				</p>
			</div>
			<div id="div_split_feature_container" class="display_n">
				<table class="cmmTable ma_t_5" summary="">
					<caption></caption>
					<colgroup>
						<col width="50%">
						<col width="50%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">속성 편집</th>
						</tr>
					</thead>
				</table>
				<div class="scrollBox h_100">
					<table class="cmmTable rowLink bd_n" summary="">
						<caption></caption>
						<colgroup>
							<col width="50%">
							<col width="50%">
						</colgroup>
						<tbody id="tby_edit_split_choice">
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
		<div>
			<p class="over_h ma_b_10 bd_t_c pa_t_10 ma_t_15">
				<a href="#" id="a_attEdit_save" class="f_l"><img src="<c:url value='/images/kworks/map/skin2/edit_feature_off.png'/>" alt="속성편집" title="속성편집"  /></a>
				<span class="f_r">
					<a href="#" id="a_edit_merge_features" class="display_n" ><img src="<c:url value='/images/kworks/map/skin2/btn_save3.png'/>" alt="저장" title="저장"  /></a>
					<a href="#" id="a_edit_split_features" class="display_n" ><img src="<c:url value='/images/kworks/map/skin2/btn_save3.png'/>" alt="저장" title="저장"  /></a>
					<a href="#" id="a_edit_save" ><img src="<c:url value='/images/kworks/map/skin2/btn_save3.png'/>" alt="저장" title="저장"  /></a>
					<a href="#" id="a_edit_cancel" ><img src="<c:url value='/images/kworks/map/skin2/btn_cancel2.png'/>" alt="취소" title="취소"  /></a>
				</span>
			</p>
		</div>
		
	</div>
	
	<p class="btnCenter">
		<a href="#" id="a_edit_close"><img src="<c:url value='/images/kworks/map/skin2/new_btn_close.png' />" alt="닫기"></a>
	</p>
	
</div>