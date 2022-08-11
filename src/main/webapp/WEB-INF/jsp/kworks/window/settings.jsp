<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<div class="div_settings">
	<div class="div_menu_container">
		<ul>
			<li class="on">
				<img src="<c:url value='/images/kworks/map/skin2/2depth_bullet2.png' />" />
				<a data-content-id="div_settings_map_tool" href="#">지도조작버튼</a>
			</li>
			<li>
				<img src="<c:url value='/images/kworks/map/skin2/2depth_bullet2.png' />" />
				<a data-content-id="div_settings_index" href="#">인덱스창</a>
			</li>
			<li>
				<img src="<c:url value='/images/kworks/map/skin2/2depth_bullet2.png' />" />
				<a data-content-id="div_settings_scale" href="#">지도축척바</a>
			</li>
			<li>
				<img src="<c:url value='/images/kworks/map/skin2/2depth_bullet2.png' />" />
				<a data-content-id="div_settings_center" href="#">지도중심좌표</a>
			</li>
			<li>
				<img src="<c:url value='/images/kworks/map/skin2/2depth_bullet2.png' />" />
				<a data-content-id="div_settings_zoom" href="#">Zoom렌즈</a>
			</li>
			<li>
				<img src="<c:url value='/images/kworks/map/skin2/2depth_bullet2.png' />" />
				<a data-content-id="div_settings_xray" href="#">X-ray렌즈</a>
			</li>
		</ul>
	</div>
	
	<div class="div_content_container">
	
		<!-- 지도 조작 -->
		<div id="div_settings_map_tool">
			<div class="div_title clearfix">
				<div class="div_title_img"><img src="<c:url value='/images/kworks/map/skin2/set_image1.png' />" alt="지도조작 이미지" /></div>
				<div class="div_title_title">지도조작버튼</div>
			</div>
			<div class="div_content clearfix">
				<ul class="ul_content_right">
					<li class="li_content_title">위치</li>
					<li class="li_content_content">
						<input id="rad_settings_maptool_location_lt" name="rad_settings_maptool_location" type="radio" value="LT" checked="checked" />
						<label for="rad_settings_maptool_location_lt">좌상단</label>
					</li>
					<li class="li_content_content">
						<input id="rad_settings_maptool_location_rt" name="rad_settings_maptool_location" type="radio" value="RT" />
						<label for="rad_settings_maptool_location_rt">우상단</label>
					</li>
				</ul>
			</div>
		</div>
		
		<!-- 인덱스 맵 -->
		<div id="div_settings_index" class="display_n">
			<div class="div_title clearfix">
				<div class="div_title_img"><img src="<c:url value='/images/kworks/map/skin2/set_image2.png' />" alt="인덱스창 이미지" /></div>
				<div class="div_title_title">인덱스창</div>
			</div>
			<div class="div_content clearfix">
				<ul class="ul_content_left">
					<li class="li_content_title">크기</li>
					<li class="li_content_content">
						<input id="rad_settings_index_size_300" name="rad_settings_index_size" type="radio" value="300" checked="checked" />
						<label for="rad_settings_index_size_300">300×300</label>
					</li>
					<li class="li_content_content">
						<input id="rad_settings_index_size_500" name="rad_settings_index_size" type="radio" value="500" />
						<label for="rad_settings_index_size_500">500×500</label>
					</li>
				</ul>
				<ul class="ul_content_right">
					<li class="li_content_title">위치</li>
					<li class="li_content_content">
						<input id="rad_settings_index_location_lb" name="rad_settings_index_location" type="radio" value="LB" checked="checked">
						<label for="rad_settings_index_location_lb">좌하단</label>
					</li>
					<li class="li_content_content">
						<input id="rad_settings_index_location_rb" name="rad_settings_index_location" type="radio" value="RB">
						<label for="rad_settings_index_location_rb">우하단</label>
					</li>
				</ul>
			</div>
		</div>
		
		<!-- 지도 축척 바 -->
		<div id="div_settings_scale" class="display_n">
			<div class="div_title clearfix">
				<div class="div_title_img"><img src="<c:url value='/images/kworks/map/skin2/set_image3.png' />" alt="지도축척바 이미지" /></div>
				<div class="div_title_title">지도축척바</div>
			</div>
			<div class="div_content clearfix">
				<ul class="ul_content_right">
					<li class="li_content_title">위치</li>
					<li class="li_content_content">
						<input id="rad_settings_scale_location_lb" name="rad_settings_scale_location" type="radio" value="LB" checked="checked">
						<label for="rad_settings_scale_location_lb">좌하단</label>
					</li>
					<li class="li_content_content">
						<input id="rad_settings_scale_location_rb" name="rad_settings_scale_location" type="radio" value="RB">
						<label for="rad_settings_scale_location_rb">우하단</label>
					</li>
					<li class="li_content_content">
						<input id="rad_settings_scale_location_none" name="rad_settings_scale_location" type="radio" value="NONE">
						<label for="rad_settings_scale_location_none">표시안함</label>
					</li>
				</ul>
			</div>
		</div>
		
		<!-- 지도 중심 좌표 -->
		<div id="div_settings_center" class="display_n">
			<div class="div_title clearfix">
				<div class="div_title_img"><img src="<c:url value='/images/kworks/map/skin2/set_image4.png' />" alt="지도중심좌표 이미지" /></div>
				<div class="div_title_title">지도중심좌표</div>
			</div>
			<div class="div_content clearfix">
				<ul class="ul_content_left">
					<li class="li_content_title">좌표 타입</li>
					<li class="li_content_content">
						<input id="rad_settings_center_mode_tm" name="rad_settings_center_mode" type="radio" value="TM" checked="checked" />
						<label for="rad_settings_center_mode_tm">TM</label>
					</li>
					<li class="li_content_content">
						<input id="rad_settings_center_mode_lonlat" name="rad_settings_center_mode" type="radio" value="LONLAT" />
						<label for="rad_settings_center_mode_lonlat">도</label>
					</li>
					<li class="li_content_content">
						<input id="rad_settings_center_mode_dms" name="rad_settings_center_mode" type="radio" value="DMS" />
						<label for="rad_settings_center_mode_dms">DMS</label>
					</li>
				</ul>
				<ul class="ul_content_right">
					<li class="li_content_title">위치</li>
					<li class="li_content_content">
						<input id="rad_settings_center_location_lb" name="rad_settings_center_location" type="radio" value="LB" checked="checked">
						<label for="rad_settings_center_location_lb">좌하단</label>
					</li>
					<li class="li_content_content">
						<input id="rad_settings_center_location_rb" name="rad_settings_center_location" type="radio" value="RB">
						<label for="rad_settings_center_location_rb">우하단</label>
					</li>
					<li class="li_content_content">
						<input id="rad_settings_center_location_none" name="rad_settings_center_location" type="radio" value="NONE">
						<label for="rad_settings_center_location_none">표시안함</label>
					</li>
				</ul>
			</div>
		</div>
		
		<!-- Zoom 렌즈 -->
		<div id="div_settings_zoom" class="display_n">
			<div class="div_title clearfix">
				<div class="div_title_img"><img src="<c:url value='/images/kworks/map/skin2/set_image5.png' />" alt="Zoom렌즈 이미지" /></div>
				<div class="div_title_title">Zoom 렌즈</div>
			</div>
			<div class="div_content clearfix">
				<ul class="ul_content_right">
					<li class="li_content_title">크기</li>
					<li class="li_content_content">
						<input id="rad_settings_zoom_size_300" name="rad_settings_zoom_size" type="radio" value="300" checked="checked" />
						<label for="rad_settings_zoom_size_300">300×300</label>
					</li>
					<li class="li_content_content">
						<input id="rad_settings_zoom_size_400" name="rad_settings_zoom_size" type="radio" value="400" />
						<label for="rad_settings_zoom_size_400">400×400</label>
					</li>
					<li class="li_content_content">
						<input id="rad_settings_zoom_size_500" name="rad_settings_zoom_size" type="radio" value="500" />
						<label for="rad_settings_zoom_size_500">500×500</label>
					</li>
				</ul>
			</div>
		</div>
		
		<!-- XRay 렌즈 -->
		<div id="div_settings_xray" class="display_n">
			<div class="div_title clearfix">
				<div class="div_title_img"><img src="<c:url value='/images/kworks/map/skin2/set_image5.png' />" alt="X-ray렌즈 이미지" /></div>
				<div class="div_title_title">X-ray 렌즈</div>
			</div>
			<div class="div_content clearfix">
				<ul class="ul_content_right">
					<li class="li_content_title">크기</li>
					<li class="li_content_content">
						<input id="rad_settings_xray_size_300" name="rad_settings_xray_size" type="radio" value="300" checked="checked" />
						<label for="rad_settings_xray_size_300">300×300</label>
					</li>
					<li class="li_content_content">
						<input id="rad_settings_xray_size_400" name="rad_settings_xray_size" type="radio" value="400" />
						<label for="rad_settings_xray_size_400">400×400</label>
					</li>
					<li class="li_content_content">
						<input id="rad_settings_xray_size_500" name="rad_settings_xray_size" type="radio" value="500" />
						<label for="rad_settings_xray_size_500">500×500</label>
					</li>
				</ul>
			</div>
		</div>
		
	</div>
	
	<div class="div_tool_container">
		<a class="a_save btn_save2" href="#" ></a>
		<a class="a_close btn_close" href="#" ></a>
	</div>
	
</div>