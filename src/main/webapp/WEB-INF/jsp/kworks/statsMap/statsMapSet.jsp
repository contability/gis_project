<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<div class="div_statsMap_set_contents h_50">
	<div class="f_l pa_t_5">
		<label class="f_l pa_t_10 ma_r_10" for="div_statsMap_color_set">색상설정</label>
		<div id="div_statsMap_color_set" class="colorSelector f_l">
			<div></div>
			<input class="txt_color_set" type="hidden" /> 
		</div>
	</div>
	<div class="f_r pa_t_10">
		<label class="ma_r_10" for="select_statsMap_level_set">급간설정</label>
		<select id="select_statsMap_level_set" class="w_70">
			<option value="5">5단계</option>
			<option value="6">6단계</option>
			<option value="7">7단계</option>
		</select> 
	</div>
</div>

<div class="div_statsMap_set_info clear">
	<table class="cmmTable">
		<thead>
			<tr>
				<th>급간</th>
				<th>구간시작</th>
				<th>구간끝</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
<div class="btnRight">
<a class="btn_modify2" href="#"></a>
<a class="btn_popCancel" href="#"></a>
</div>