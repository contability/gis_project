<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 레이어 순서조정 팝업 -->
<div class="div_layer_order_set over_h">
	<div class="f_r display_inline">
		<a class="a_layer_order_first" href="#"><img src="<c:url value='/images/kworks/map/skin2/layer2depBtn_02off.png' />" alt="맨 위로 이동" title="맨 위로 이동" /></a>
		<a class="a_layer_order_up" href="#"><img src="<c:url value='/images/kworks/map/skin2/layer2depBtn_04off.png' />" alt="위로 이동" title="위로 이동" /></a>
		<a class="a_layer_order_down" href="#"><img src="<c:url value='/images/kworks/map/skin2/layer2depBtn_03off.png' />" alt="아래로 이동" title="아래로 이동" /></a>
		<a class="a_layer_order_last" href="#"><img src="<c:url value='/images/kworks/map/skin2/layer2depBtn_01off.png' />" alt="맨 아래로 이동" title="맨 아래로 이동" /></a>
	</div>
	<ul class="ul_layer_ordr_list w_285 h_300 bd_gray over_ys">
	</ul>
	<ul class="ul_base_map_ordr_list w_285 h_100 bd_gray over_ys">
	</ul>
	<div class=" f_r ma_t_10">
		<a class="a_layer_order_set_save ma_l_5" href="#"><img src="<c:url value='/images/kworks/map/skin2/btn_change.png' />" alt="수정" title="수정" /></a>
		<a class="a_layer_order_set_cancel ma_l_5" href="#"><img src="<c:url value='/images/kworks/map/skin2/btn_cancel.png' />" alt="취소" title="취소" /></a>
	</div>
</div>