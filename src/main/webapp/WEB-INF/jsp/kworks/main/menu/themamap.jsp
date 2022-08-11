<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<div class="mapTab clearfix">
	<ul>
		<li><a data-for-id="div_menu_panel_themamap_user" class="mTab" href="#"><img src="<c:url value='/images/kworks/main/menu/themamap/tab_user_on.png' />"  /></a></li>
		<li><a data-for-id="div_menu_panel_themamap_public" class="mTab" href="#"><img src="<c:url value='/images/kworks/main/menu/themamap/tab_public_off.png' />" /></a></li>
	</ul>
</div>

<div id="div_menu_panel_themamap_user" class="tab_content" >
	<div class="div_tool">
		<a class="a_add_themamap" href="#"><img src="<c:url value='/images/kworks/map/common/map_plus.png' />" alt="주제도 추가" /></a>
	</div> 
	<ul class="ul_themamap">
	</ul>
</div>

<div id="div_menu_panel_themamap_public" class="tab_content display_n" >
	<ul class="ul_themamap">
	</ul>
</div>
