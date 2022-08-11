<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<div class="div_window_symbolizer_setting div_window_label_setting">
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
	<div class="div_content_panel div_content_panel_scale">
		<div>
			<span class="span_title">위치 설정</span>
		</div>
		<div class="div_content div_content_position">
			<ul>
				<li data-anchor="lt" ><img src="images/kworks/map/skin2/layerTxt_po01_off.png" alt="좌측상단" /></li>
				<li data-anchor="ct" ><img src="images/kworks/map/skin2/layerTxt_po02_off.png" alt="중앙상단" /></li>
				<li data-anchor="rt" ><img src="images/kworks/map/skin2/layerTxt_po03_off.png" alt="우측상단" /></li>
				<li data-anchor="lm" ><img src="images/kworks/map/skin2/layerTxt_po04_off.png" alt="좌측중앙" /></li>
				<li data-anchor="cm" ><img src="images/kworks/map/skin2/layerTxt_po05_off.png" alt="중앙중앙" /></li>
				<li data-anchor="rm" ><img src="images/kworks/map/skin2/layerTxt_po06_off.png" alt="우측중앙" /></li>
				<li data-anchor="lb" ><img src="images/kworks/map/skin2/layerTxt_po07_off.png" alt="좌측하단" /></li>
				<li data-anchor="cb" ><img src="images/kworks/map/skin2/layerTxt_po08_off.png" alt="중앙하단" /></li>
				<li data-anchor="rb" ><img src="images/kworks/map/skin2/layerTxt_po09_off.png" alt="우측하단" /></li>
			</ul>
		</div>
		<div class="div_content div_content_width">
			<span class="span_content_title" >오프셋 X</span>
			<div class="div_content_content" ><input type="text" class="txt_offset_x" /></div>
		</div>
		<div class="div_content div_content_width">
			<span class="span_content_title" >오프셋 Y</span>
			<div class="div_content_content" ><input type="text" class="txt_offset_y" /></div>
		</div>
	</div>
	<div class="div_content_panel">
		<div>
			<span class="span_title">글꼴</span>
		</div>
		<div class="div_content">
			<span class="span_content_title" >폰트명</span>
			<div class="div_content_content" ><select class="sel_font_name"></select></div>
		</div>
		<div class="div_content div_content_width">
			<span class="span_content_title" >크기</span>
			<div class="div_content_content" ><input type="text" class="txt_size" /></div>
		</div>
		<div class="div_content div_content_style">
			<span class="span_content_title" >스타일</span>
			<div class="div_content_content" >
				<a href="#" class="a_bold"><img alt="굵게" src="images/kworks/map/skin2/btn_bold_off.png" /></a>
				<a href="#" class="a_italic"><img alt="기울기" src="images/kworks/map/skin2/btn_italic_off.png" /></a>
			</div>
		</div>
		<div class="div_content div_content_opacity">
			<span class="span_content_title" ><span>투명도(%)</span></span>
			<div class="div_content_content"><input type="text" class="txt_font_opacity" /></div>
		</div>
		<div class="div_content div_content_color">
			<span class="span_content_title" >색상</span>
			<div class="div_content_content" >
				<div class="div_font_color colorSelector">
					<div style="background-color:#000000"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="div_content_panel">
		<div>
			<span class="span_title">그림자</span>
		</div>
		<div class="div_content div_content_width">
			<span class="span_content_title" >반경</span>
			<div class="div_content_content" ><input type="text" class="txt_radius" /></div>
		</div>
		<div class="div_content div_content_opacity">
			<span class="span_content_title" ><span>투명도(%)</span></span>
			<div class="div_content_content"><input type="text" class="txt_shadow_opacity" /></div>
		</div>
		<div class="div_content div_content_color">
			<span class="span_content_title" >색상</span>
			<div class="div_content_content" >
				<div class="div_shadow_color colorSelector">
					<div style="background-color:#000000"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="div_tool">
		<a href="#" class="a_setting btn_popSet"></a>
		<a href="#" class="a_close btn_popCancel"></a>
	</div>
</div>