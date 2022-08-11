<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 차단제수변 팝업 -->
<div class="div_window_water_control_value_result">
	<div class="div_result_layout" data-options="fit:true" style="width:100%;height:350px;">
		<div data-options="region:'west',title:'검색 결과 목록'" style="width:300px;">
			<table class="grid_result_layers"></table>
		</div>
		<div class="div_result_center" data-options="region:'center'">
			<div class="div_result_menu" style="display:block;height:60px;">
				<div class="f_r">
					<a href="#" class="a_search_register">
							<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_list_off.png' />" alt="대장조회" />
					</a>
					<a href="#" class="a_export_excel">
						<img class="img_ov_off" src="<c:url value='/images/kworks/map/common/btn_slide_send1_off.png' />" alt="속성내보내기" />
					</a>
				</div>
			</div>
			<div class="div_result_detail">
				<table class="grid_result_detail"></table>
			</div>
		</div>
	</div>
</div>