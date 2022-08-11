<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 통합 검색 팝업 -->
<div class="div_window_result_condition_search">

	<div class="ma_b_20 f_l">
		<div class="f_l w_150 pa_t_10 pa_r_5 ma_b_10 ma_b_5">
			<div class="bg_gray1 t_a_c">
				<span class="h_28 f_s_12 color_black bold pa_l_5">조건대상 항목</span>
			</div>
			<div class="pop_2dep bd_gray3 pa_r_5 ma_t_10 over_ys h_180">
				<ul class="ul_fields ul_content"></ul>
			</div>
		</div>
		<div class="f_l w_180 pa_t_10 pa_r_5">
			<div class="bg_gray1 t_a_c">
				<span class="h_28 f_s_12 color_black bold pa_l_5">조건 설정</span>
			</div>
	
			<!-- 도메인 필드 -->
			<div class="div_condition_field div_domain_field">
				<div class="f_l layerSet pa_t_10 pa_b_5">
					<ul>
						<li class="f_l"><span class="setbox f_l f_s_12 color_3 pa_t_3 pa_l_5">비교연산</span></li>
						<li class="f_l">
							<span class="f_s_11 color_6 pa_l_5">
								<select class="sel_domain_condition">
										<option value="=">일치</option>
										<option value="!=">불일치</option>
								</select>
							</span>
						</li>
					</ul>
				</div>
				<div class="pop_2dep bd_gray3 pa_r_5 pa_b_5 ma_t_10 over_ys h_120 clear">
					<ul class="ul_domain_codes ul_content"></ul>
				</div>
			</div>
			<!-- //도메인 필드 -->
	
			<!-- 숫자 필드 -->
			<div class="div_condition_field div_number_field f_l layerSet pa_t_10">
				<ul>
					<li class="h_28 pa_l_10">
						<input id="chk_result_condition_search_number_range" name="chk_unity_search_number" value="range" class="ma_r_5" type="radio" checked="checked" />
						<span class="f_s_12 color_7"><label for="chk_result_condition_search_number_range">범위</label></span>
					</li>
					<li class="h_24 pa_l_10">
						<span class="f_s_12 color_7 pa_r_5">최소</span>
						<input type="text" class="txt_number_min pa_l_5 pa_r_5 w_80" />
					</li>
					<li class="h_24 pa_l_10">
						<span class="f_s_12 color_7 pa_r_5">최대</span>
						<input type="text" class="txt_number_max pa_l_5 pa_r_5 w_80" />
					</li>
					<li class="h_28 pa_l_10 pa_t_10">
						<input id="chk_result_condition_search_number_equals" name="chk_unity_search_number" value="equals" class="ma_r_5" type="radio" />
						<span class="f_s_12 color_7"><label for="chk_result_condition_search_number_equals">특정값</label></span>
					</li>
					<li class="h_20 pa_l_10">
						<span class="f_s_12 color_6">
							<select class="sel_number_condition">
								<option value="=">일치</option>
								<option value="!=">불일치</option>
							</select>
						</span>
						<input type="text" class="txt_number_value w_70 h_16 " maxlength="7" />
					</li>
				</ul>
			</div>
			<!-- //숫자 필드 -->
	
			<!-- 날짜 필드 -->
			<div class="div_condition_field div_date_field f_l layerSet pa_t_10">
				<ul>
					<li class="h_28 pa_l_10">
						<input id="chk_result_condition_search_date_range" name="chk_unity_search_date" value="range" class="ma_r_5" type="radio" checked="checked" />
						<span class="f_s_12 color_7"><label for="chk_result_condition_search_date_range">범위</label></span>
					</li>
					<li class="h_24 pa_l_10">
						<span class="f_s_12 color_7 pa_r_5">시작일</span>
						<input type="text" class="txt_date_start pa_l_5 pa_r_5 w_80" value="" maxlength="10" />
					</li>
					<li class="h_24 pa_l_10">
						<span class="f_s_12 color_7 pa_r_5">종료일</span>
						<input type="text" class="txt_date_end pa_l_5 pa_r_5 w_80" value="" maxlength="10" />
					</li>
					<li class="h_28 pa_l_10 pa_t_10">
						<input id="chk_result_condition_search_date_equals" name="chk_unity_search_date" value="equals" class="ma_r_5" type="radio" />
						<span class="f_s_12 color_7"><label for="chk_result_condition_search_date_equals">특정일</label></span>
					</li>
					<li class="h_20 pa_l_10">
						<span class="f_s_12 color_6">
							<select class="sel_date_condition">
								<option value="=">일치</option>
								<option value="!=">불일치</option>
							</select>
						</span>
						<input type="text" class="txt_date_value" />
					</li>
				</ul>
			</div>
			<!-- // 날짜 필드 -->
	
			<!-- 문자열 필드 -->
			<div class="div_condition_field div_string_field f_l layerSet pa_t_10">
				<ul>
					<li class="h_28 pa_l_10">
						<span class="f_s_12 color_7"><label for="chk_unity_search_number_range">검색어</label></span>
					</li>
					<li class="h_20 pa_l_10">
						<input type="text" class="txt_string_value h_16 " maxlength="7" />
					</li>
				</ul>
			</div>
	
			<!-- //문자열 필드 -->
	
			<!-- 저장 -->
			<div class="pa_t_10 f_r">
				<a href="#" class="a_save">
					<img src="<c:url value='/images/kworks/map/skin2/btn_save3.png' />" />
				</a>
			</div>
		</div>
		<div class="f_l w_150 pa_t_10">
			<div class="bg_gray1 t_a_c">
				<span class="h_28 f_s_12 color_black bold pa_l_5">검색 조건 항목</span>
			</div>
			<div class="listBox bd_gray3 ma_t_10 over_ys h_150">
				<ul>
					<li class="pa_t_8 pa_l_10 f_s_12 color_6 bold bg_gray1 pa_0 t_a_c">조건</li>
				</ul>
				<ul class="ul_items ul_content">
				</ul>
			</div>
			<div class="pa_t_10 f_r">
				<a href="#" class="a_remove">
					<img src="<c:url value='/images/kworks/map/skin2/btn_del2.png' />" />
				</a>
			</div>
		</div>
	</div>
	<div class="f_r ma_t_20">
		<a class="a_search pa_r_5" href="#">
			<img src="<c:url value='/images/kworks/map/skin2/btn_search2.png' />" />
		</a>
		<a class="a_close btn_popCancel" href="#"></a>
	</div>

</div>