<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 연관 검색 팝업 -->
<div class="div_window_result_relation_search">
	<div class=" layerSet">
		<div class="f_l pa_t_10 pa_b_10 bd_b_gray1">
			<div class="f_l">
				<ul>
					<li><span class="h_28 f_s_12 color_black pa_l_5 pa_r_5">대상레이어</span></li>
					<li>
						<a href="#" class="a_spatial_select_target_layers">
							<img class="pa_t_10 pa_l_15 pa_b_3" src="<c:url value='/images/kworks/map/skin2/img_layer.png' />" />
						</a>
					</li>
				</ul>
			</div>
			<div class="f_l listBox3 w_215 bd_gray3 pa_t_5 pa_b_5 h_60 over_a display_in">
				<ul class="ul_target_layers">
				</ul>
			</div>
		</div>
		<div class="f_l w100">
			<ul class="clear pa_t_10">
				<li><span class="setbox f_l f_s_12 color_3 pa_r_18 pa_t_3 pa_l_5">공간필터</span></li>
				<li class="pa_t_3 f_l">
					<input class="ma_r_3" id="rad_result_relation_search_spatial_type_intersects" name="rad_result_relation_search_spatial_type" value="INTERSECTS" type="radio" checked="checked" />
					<span class="f_s_12 color_7"><label for="rad_result_relation_search_spatial_type_intersects">걸침 포함</label></span>
					<input class="ma_r_3" id="rad_result_relation_search_spatial_type_within" name="rad_result_relation_search_spatial_type" value="WITHIN" type="radio" />
					<span class="f_s_12 color_7"><label for="rad_result_relation_search_spatial_type_within">영역내부만</label></span>
				</li>
			</ul>
			<ul class="clear relationSearchBufferDistance pa_t_10">
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