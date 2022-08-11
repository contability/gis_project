<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>

<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>

<div class="div_window_road_register">
	<!-- 좌측 카테고리 탭-->
	<div class="div_category">
		<ul class='ul_category'>
		</ul>
	</div>
	<!-- 우측 내용 -->
	<div class="div_content_container">
	</div>
	
	<!-- 하단 버튼 레이아웃 시작 -->
	<div class="div_tool">
		<!-- 인쇄, 편집, 닫기 버튼 -->
		<div class="div_tool_view">
			<a class="a_mesrSgnalSttusExaminHist" href="#"><img src="<c:url value='/images/kworks/map/skin2/btn_mesrSgnal.png' />" alt="측량표지현황조사이력" /></a>
			<!-- 강릉시 도로대장관리서비스 사용 -->
			<c:if test="${ prjCode eq 'gn' }">
				<a class="a_video_manage" href="#"><img src="<c:url value='/images/kworks/map/skin2/btn_video.png' />" alt="영상관리" /></a>
				<a class="a_section_plan" href="#"><img src="<c:url value='/images/kworks/map/skin2/btn_section.png' />" alt="구간도면" /></a>
				<a class="a_local_plan" href="#"><img src="<c:url value='/images/kworks/map/skin2/btn_local.png' />" alt="단위도면" /></a>
			</c:if>
			<a class="a_manage_history" href="#"><img src="<c:url value='/images/kworks/map/skin2/btn_history.png' />" alt="관리이력" /></a>
			<a class="a_photo_manage" href="#"><img src="<c:url value='/images/kworks/map/skin2/btn_imgView.png' />" alt="사진관리" /></a>
			<a class="a_view_print btn_print2" href="#"></a>
			<!-- 강릉시 도로대장관리서비스 사용 -->
			<c:if test="${ prjCode eq 'gn' }">
				<a class="a_view_road_regstr" href="#"><img src="<c:url value='/images/kworks/map/skin2/btn_road_regstr.png' />" alt="대장인쇄" /></a>
				<a class="a_united_road_regstr" href="#"><img src="<c:url value='/images/kworks/map/skin2/btn_united_regstr.png' />" alt="통합출력" /></a>
			</c:if>
			<a class="a_view_edit btn_edit" href="#"></a>
			<a class="a_view_close btn_close" href="#"></a>
		</div>
		
		<!-- 편집 저장, 닫기 버튼 -->
		<div class="div_tool_edit" >
			<a class="a_edit_update btn_save2" href="#" ></a>
			<a class="a_edit_cancel btn_close" href="#" ></a>
		</div>
	</div>
</div>

<div id="div_window_road_register_tools">
	<a href="#" id="div_window_road_register_tools_prev" class="icon-back"></a>
	<a href="#" id="div_window_road_register_tools_next" class="icon-next"></a>
</div>