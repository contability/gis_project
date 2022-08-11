<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 해안침식 분석 결과 -->
<div class="div_window_beach_erosion_result">
	<div class="div_result_layout" data-options="fit:true" style="width:100%;height:350px;">
		<div data-options="region:'west',title:'검색 결과 목록'" style="width:300px;">
			<table class="grid_result_layers"></table>
		</div>
		<div class="div_result_center" data-options="region:'center'">
			<div class="div_result_detail">
				<table class="grid_result_detail"></table>
			</div>
		</div>
	</div>
</div>