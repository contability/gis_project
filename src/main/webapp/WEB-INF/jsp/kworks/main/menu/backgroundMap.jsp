<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>

<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>

<div class="mapTab clearfix">
	<ul>
		<li><a data-for-id="div_menu_panel_background_map_flight" class="mTab" href="#"><img src="<c:url value='/images/${prjCode}/main/menu/backgroundMap/tab_flight_on.png' />"  /></a></li>
		<li><a data-for-id="div_menu_panel_background_map_external" class="mTab" href="#"><img src="<c:url value='/images/kworks/main/menu/backgroundMap/tab_external_off.png' />" /></a></li>
	</ul>
</div>

<div id="div_menu_panel_background_map_flight" class="tab_content" >
</div>

<div id="div_menu_panel_background_map_external" class="tab_content display_n" >
	<div>
		<h4><img src="<c:url value='/images/kworks/map/skin2/map_tit1.png' />" alt="네이버" /></h4>
		<div data-service="naver" data-type="base" class="div_background_map_item">
			<img src="<c:url value='/images/kworks/main/menu/backgroundMap/naver_base.png' />" />
			<span>일반지도</span>
		</div>
		<div data-service="naver" data-type="satellite" class="div_background_map_item">
			<img src="<c:url value='/images/kworks/main/menu/backgroundMap/naver_satellite.png' />" />
			<span>항공영상</span>
		</div>
		<div data-service="naver" data-type="hybrid" class="div_background_map_item">
			<img src="<c:url value='/images/kworks/main/menu/backgroundMap/naver_hybrid.png' />" />
			<span>하이브리드</span>			
		</div>
	</div>
	<div>
		<h4><img src="<c:url value='/images/kworks/map/skin2/map_tit2.png' />" alt="카카오" /></h4>
		<div data-service="daum" data-type="base" class="div_background_map_item">
			<img src="<c:url value='/images/kworks/main/menu/backgroundMap/kakao_base.png' />" />
			<span>일반지도</span>
		</div>
		<div data-service="daum" data-type="satellite" class="div_background_map_item">
			<img src="<c:url value='/images/kworks/main/menu/backgroundMap/kakao_satellite.png' />" />
			<span>항공영상</span>
		</div>
		<div data-service="daum" data-type="hybrid" class="div_background_map_item">
			<img src="<c:url value='/images/kworks/main/menu/backgroundMap/kakao_hybrid.png' />" />
			<span>하이브리드</span>			
		</div>
	</div>
	<div>
		<h4><img src="<c:url value='/images/kworks/map/skin2/map_tit3.png' />" alt="브이월드" /></h4>
		<div data-service="vworld" data-type="base" class="div_background_map_item">
			<img src="<c:url value='/images/kworks/main/menu/backgroundMap/vworld_base.png' />" />
			<span>일반지도</span>
		</div>
		<div data-service="vworld" data-type="satellite" class="div_background_map_item">
			<img src="<c:url value='/images/kworks/main/menu/backgroundMap/vworld_satellite.png' />" />
			<span>항공영상</span>
		</div>
		<div data-service="vworld" data-type="hybrid" class="div_background_map_item">
			<img src="<c:url value='/images/kworks/main/menu/backgroundMap/vworld_hybrid.png' />" />
			<span>하이브리드</span>			
		</div>
	</div>
</div>