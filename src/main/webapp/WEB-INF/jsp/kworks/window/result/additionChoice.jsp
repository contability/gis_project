<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 추가 선택 -->
<div class="div_window_result_addition_choice">
	<div class=" layerSet">
		<div class="f_l pa_t_10 w100">
			<ul>
				<li><span class="setbox f_l f_s_12 color_3 pa_r_18 pa_t_3 pa_l_5">선택방법</span></li>
				<li class="pa_t_3 f_l">
					<a href="#" class="a_spatial_point"><img class="tool_toggle_radio" src="<c:url value='/images/kworks/map/skin2/select01_off.png' />" alt="점선택" title="점선택" /></a>
					<a href="#" class="a_spatial_linestring"><img class="tool_toggle_radio" src="<c:url value='/images/kworks/map/skin2/select02_off.png' />" alt="선선택" title="선선택"  /></a>
					<a href="#" class="a_spatial_rect"><img class="tool_toggle_radio" src="<c:url value='/images/kworks/map/skin2/select03_off.png' />" alt="면선택" title="면선택"  /></a>
					<a href="#" class="a_spatial_polygon"><img class="tool_toggle_radio" src="<c:url value='/images/kworks/map/skin2/select04_off.png' />" alt="다각형선택" title="다각형선택"  /></a>
					<a href="#" class="a_spatial_circle"><img class="tool_toggle_radio" src="<c:url value='/images/kworks/map/skin2/select05_off.png' />" alt="원선택" title="원선택"  /></a>
				</li>
			</ul>
			<ul class="clear pa_t_20">
				<li><span class="setbox f_l f_s_12 color_3 pa_r_18 pa_t_3 pa_l_5">공간필터</span></li>
				<li class="pa_t_3 f_l">
					<input class="ma_r_3" id="rad_result_addition_choise_spatial_type_intersects" name="rad_result_addition_choise_spatial_type" value="INTERSECTS" type="radio" checked="checked" />
					<span class="f_s_12 color_7"><label for="rad_result_addition_choise_spatial_type_intersects">걸침 포함</label></span>
					<input class="ma_r_3" id="rad_result_addition_choise_spatial_type_within" name="rad_result_addition_choise_spatial_type" value="WITHIN" type="radio" />
					<span class="f_s_12 color_7"><label for="rad_result_addition_choise_spatial_type_within">영역내부만</label></span>
				</li>
			</ul>
			<ul class="clear pa_t_20">
				<li><span class="setbox f_s_12 color_3 pa_r_18 pa_t_3 pa_l_5 f_l">버퍼거리</span></li>
				<li>
					<span class="f_s_11 color_6 pa_r_5">
						<input class="txt_spatial_buffer_distance" type="text" value="0" /> M(미터)
					</span>
				</li>
			</ul>
		</div>
	</div>
	<div class="ma_t_20 pa_b_10 f_r">
		<a class="a_spatial_search btn_ri pa_r_5 btn_search4" href="#" ></a>
		<a class="a_spatial_close btn_ri btn_close" href="#" ></a>
	</div>
</div>