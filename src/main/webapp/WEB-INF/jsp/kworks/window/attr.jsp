<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" 			uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="validator" 	uri="http://www.springmodules.org/tags/commons-validator" %>

<!-- 속성 조회 팝업 -->
<div class="div_window_attr">
	<div class="div_attr_west" data-options="region:'west'" style="width:150px;">
		<div class="div_attr_datas">
   		</div>
	</div>
	<div data-options="region:'center'">
		<table class="tbl_attr_detail"></table>
	</div>
    <div class="div_attr_south" data-options="region:'south'" style="padding-top:5px;">
		<a class="a_attr_seaoll btnBlue" href="#" >농지·산지 전용</a>
        <a class="a_attr_kras btnBlue" href="#" >토지·건물 정보</a>
        <a class="a_attr_add_list" href="#">
        	<img src="<c:url value='/images/kworks/main/attr/btn_add.png'/>" />
        </a>
        <a class="a_attr_close" href="#">
        	<img src="<c:url value='/images/kworks/main/attr/btn_close.png'/>" />
        </a>
	</div>
</div>
