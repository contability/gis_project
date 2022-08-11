<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 파일 -->
<div class="mapTab clearfix">
	<ul>
		<li><a data-for-id="div_menu_panel_file_import" class="mTab" href="#"><img src="<c:url value='/images/kworks/main/menu/file/import_tab_on.png' />" /></a></li>
		<li><a data-for-id="div_menu_panel_file_export" class="mTab" href="#"><img src="<c:url value='/images/kworks/main/menu/file/export_tab_off.png' />" /></a></li>
	</ul>
</div>

<div id="div_menu_panel_file_import" class="tab_content" >
	<div class="div_type">
		<select class="sel_import_type">
			<option value="SHAPE" selected="selected">SHAPE</option>
			<option value="EXCEL">EXCEL</option>
			<option value="GML">GML</option>
			<option value="GEOJSON">GEOJSON</option>
		</select>
	</div>
	<div class="div_content">
		<h4>설정</h4>
		<ul class="ul_option">
			<li>
				<span class="span_title">좌표계</span>
				<select class="sel_coorinates"></select>
			</li>
			<li class="li_import_excel">
				<span class="span_title">시작라인수</span>
				<input class="txt_start_line" type="text" />
			</li>
			<li class="li_import_excel">
				<span class="span_title">X필드</span>
				<input class="txt_x_field_index" type="text" />
			</li>
			<li class="li_import_excel">
				<span class="span_title">Y필드</span>
				<input class="txt_y_field_index" type="text" />
			</li>
		</ul>
		<div class="div_import_file">
			<input id="file_import_file" name="file_import_file" type="file" />
		</div>
		<p class="btnRight v2">
			<a href="#" class="a_execute" ><img src="<c:url value='/images/kworks/map/skin2/btn_go.png' />" /></a>
		</p>
	</div>
</div>
<div id="div_menu_panel_file_export" class="tab_content" >
	<div class="div_type">
		<select class="sel_export_type">
			<option value="IMAGE" selected="selected">이미지</option>
			<option value="DXF">DXF</option>
			<option value="EXCEL">EXCEL</option>
			<option value="SHAPE">SHAPE</option>
		</select>
	</div>
	<div class="div_content">
		<h4>설정</h4>
		<ul class="ul_option">
			<li class="li_export li_export_excel li_export_shape li_export_dxf">
				<span class="span_title">좌표계</span>
				<select class="sel_coorinates"></select>
			</li>
			<li class="li_export li_export_excel li_export_shape li_export_dxf">
				<span class="span_title_large">범위</span>
				<div>
					<input type="radio" id="rad_menu_panel_file_export_scope_now_realm" name="rad_menu_panel_file_export_scope" value="BBOX" checked="checked" />
					<label for="rad_menu_panel_file_export_scope_now_realm">현재 영역</label>
				</div>
				<div>
					<input type="radio" id="rad_menu_panel_file_export_scope_polygon"  name="rad_menu_panel_file_export_scope" value="POLYGON" />
					<label for="rad_menu_panel_file_export_scope_polygon">사용자 지정영역</label>
				</div>
				<div>
					<input type="radio" id="rad_menu_panel_file_export_scope_dong"  name="rad_menu_panel_file_export_scope" value="DONG" />
					<label for="rad_menu_panel_file_export_scope_dong">동 경계</label>
					<select class="sel_dong"></select>
				</div>
				
			</li>
			<li class="li_export li_export_excel li_export_shape li_export_dxf">
				<span class="span_title">대상레이어</span>
				<a class="a_export_layers"><img src="<c:url value='/images/kworks/map/skin2/btn_select3_off.png' />" alt="선택" title="선택" /></a>
				<ul class="ul_export_layers">
				</ul>
			</li>
			<li class="li_export li_export_excel">
				<input class="chk_export_field_at" id="div_menu_panel_file_export_field_at" type="checkbox" />
				<label for="div_menu_panel_file_export_field_at" class="f_s_12 color_7" >필드명 기록</label>
			</li>
		</ul>
		
		<div class="btnRight v2">
			<a href="#" class="a_execute"><img src="<c:url value='/images/kworks/map/skin2/btn_go.png' />" /></a>
		</div>
	</div>
</div>
<!-- //파일 -->