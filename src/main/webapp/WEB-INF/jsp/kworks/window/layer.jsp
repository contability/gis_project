<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 레이어 선택 팝업 -->
<div class="div_window_layer">
	<div class="div_select_category">
		대상 레이어 분류
		<select class="sel_select_category">
		</select>
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
			<a class="a_select_select" href="#"><img src="<c:url value='/images/kworks/map/skin2/btn_select3_off.png' />" alt="선택" title="선택" /></a>
			<a class="a_select_cancel" href="#"><img src="<c:url value='/images/kworks/map/skin2/btn_cancel2.png' />" alt="취소" title="취소" /></a>
		</span>
	</div>
</div>