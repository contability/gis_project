<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 레이어 선택 팝업 -->
<div class="div_window_search_facilities">
	<div class="div_select_category">
		<input class="txt_select_layer" />
		<a class="a_select_layer" href="#">
			<img src="<c:url value='/images/kworks/system/topToolSearch.png' />" alt="검색" title="검색" />
		</a>
		<span class="span_select_layer">선택된 레이어</span>
	</div>
	<div class="div_select_container">
		<ul class="ul_select_layers"></ul>
		<ul class="ul_select_selected_layers"></ul>
	</div>
	<div class="ul_select_tool">
		<span class="f_l">
			<a class="a_select_selectall" href="#"><img src="<c:url value='/images/kworks/system/btn_select.png' />" alt="전체선택" title="전체선택" /></a>
			<a class="a_select_unselectall" href="#"><img src="<c:url value='/images/kworks/system/btn_concellation.png' />" alt="전체해제" title="전체해제" /></a>
		</span>
		<span class="f_r" >
			<a class="a_select_search" href="#"><img src="<c:url value='/images/kworks/map/skin2/btn_search2.png' />" alt="검색" title="검색" /></a>
			<a class="a_select_close" href="#"><img src="<c:url value='/images/kworks/map/skin2/btn_cancel2.png' />" alt="닫기" title="닫기" /></a>
		</span>
	</div>
</div>