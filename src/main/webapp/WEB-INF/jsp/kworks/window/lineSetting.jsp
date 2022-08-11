<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<div class="div_window_symbolizer_setting div_window_line_setting">
	<div class="div_content_panel div_content_panel_preview">
		<span class="span_preview">
			<canvas width="90px" height="40px" ></canvas>
		</span>
	</div>
	<div class="div_content_panel div_content_panel_scale">
		<div>
			<span class="span_title">유효축척 설정</span>
		</div>
		<div class="div_content">
			<input type="text" class="txt_min_scale" /> ~ <input type="text" class="txt_max_scale" />
			<a href="#" class="a_init_scale" >초기화</a>
		</div>
	</div>
	<div class="div_content_panel div_content_panel_stroke">
		<div>
			<span class="span_title">외곽선 설정</span>
		</div>
		<div class="div_content div_content_opacity">
			<span class="span_content_title" ><span>투명도(%)</span></span>
			<div class="div_content_content"><input type="text" class="txt_line_opacity" /></div>
		</div>
		<div class="div_content div_content_color">
			<span class="span_content_title" >색상</span>
			<div class="div_content_content" >
				<div class="div_line_color colorSelector">
					<div style="background-color:#000000"></div>
				</div>
			</div>
		</div>
		<div class="div_content div_content_width">
			<span class="span_content_title" >두께</span>
			<div class="div_content_content" ><input type="text" class="txt_width" /></div>
		</div>
		<div class="div_content">
			<span class="span_content_title" >패턴</span>
			<div class="div_content_content" ><select class="sel_pattern"></select></div>
		</div>
	</div>
	<div class="div_content_panel div_content_panel_direction">
		<span class="span_title">
			<input type="checkbox" id="chk_window_line_setting_direction" class="chk_line_direction" />
			<label for="chk_window_line_setting_direction">방향 표시</label>
		</span>
	</div>
	<div class="div_tool">
		<a href="#" class="a_setting btn_popSet"></a>
		<a href="#" class="a_close btn_popCancel"></a>
	</div>
</div>