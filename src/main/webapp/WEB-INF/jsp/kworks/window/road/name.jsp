<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<div class="div_window_road">
	<div class="div_condition">
		<span>검색영역</span>
		<span class="span_content">
			<select class="sel_search_extent">
				<option value="ALL">전체</option>
				<option value="EXTENT">현재영역</option>
			</select>
		</span>
		<a href="#" class="a_search">검색</a>
	</div>
	<div class="div_content">
		<div>
			<span class="span_title">도로명</span>
		</div>
		<ul class="ul_content">
		</ul>
		<div class="div_content_tool">
			<a class="a_highlight" href="#"><img src="<c:url value='/images/kworks/map/skin2/popBtn_search01.png' />" alt="위치보기" /></a>
			<a class="a_facilities_search" href="#"><img src="<c:url value='/images/kworks/map/skin2/popBtn_search02.png' />" alt="시설검색" /></a>
			<a class="a_modify" href="#"><img src="<c:url value='/images/kworks/map/skin2/popBtn_search03.png' />" alt="편집" /></a>
		</div>
	</div>
	<div class="div_tool">
		<a class="btn_close" href="#"></a>
	</div>
</div>