<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<div class="div_window_layer_setting">
	<div class="div_title">
		<span class="span_title">레이어명</span>
		<span class="span_content"></span>
	</div>
	<div class="div_fields">
		<select class="sel_fields"></select>
		<a href="#" class="a_setting_domain_cl">
			<img src="images/kworks/map/common/layer_icon2_ov.png" alt="도메인 분류 설정" title="도메인 분류 설정" />
		</a>
	</div>
	<div class="div_rules">
		<div class="div_rule div_rule_default"><ul></ul></div>
		<div class="div_rule div_rule_domain"><ul></ul></div>
	</div>
	<div class="div_scale">
		<div>
			<span class="span_title">유효축척</span>
		</div>
		<div class="div_content">
			<input type="text" class="txt_min_scale" /> ~ <input type="text" class="txt_max_scale" />
			<a href="#" class="a_apply_scale" >일괄적용</a>
		</div>
	</div>
	<div class="div_label">
		<div>
			<span class="span_title">레이블 설정</span>
		</div>
		<div>
			<select class="sel_label_fields"></select>
			<a href="#" class="a_setting_text_symbol">
				<img src="images/kworks/map/skin2/layer_icon_text.png" alt="텍스트 심볼 설정" />
			</a>
		</div>
	</div>
	<div class="div_opacity div_polygon_opacity">
		<div>
			<span class="span_title">채우기 투명도(%)</span>
		</div>
		<div class="div_content">
			<div class="div_slider"><input type="text" class="txt_fill_opacity" /></div>
			<span class="span_apply"><a href="#" class="a_apply_polygon_opacity" >일괄적용</a></span>
		</div>
		
	</div>
	<div class="div_opacity div_line_opacity">
		<div>
			<span class="span_title">외곽선 투명도(%)</span>
		</div>
		<div class="div_content">
			<div class="div_slider"><input type="text" class="txt_line_opacity" /></div>
			<span class="span_apply"><a href="#" class="a_apply_line_opacity" >일괄적용</a></span>
		</div>
	</div>
	<div class="div_tool">
		<a href="#" class="a_setting btn_popSet"></a>
		<a href="#" class="a_close btn_popCancel"></a>
	</div>
</div>