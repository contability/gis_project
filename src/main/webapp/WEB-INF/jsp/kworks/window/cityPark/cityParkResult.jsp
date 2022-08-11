<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 도시 공원 검색 결과 -->
<div class="div_window_city_park_result">
	<div class="div_window_city_park_result_tabs">
		<div title="일반">
			<div class="div_general" data-options="fit:true" style="width:100%;height:280px;">
				<div data-options="region:'west'" style="width:400px;">
					<table class="grid_general"></table>
				</div>
				<div class="div_result_center" data-options="region:'center'">
					<div class="div_facility" data-options="fit:true" style="width:100%;height:250px;">
						<div data-options="region:'west'" style="width:180px;">
							<table class="grid_facilities"></table>
						</div>
						<div data-options="region:'center'">
							<table class="grid_facility"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div title="토지">
			<div class="div_land" style="width:100%;height:280px;">
				<table class="grid_land"></table>
			</div>
		</div>
		<!-- 
		<div title="민원">
			<div class="div_civil_appeal" style="width:100%;height:280px;">
				<table class="grid_civil_appeal"></table>
			</div>
		</div>
		<div title="변천내역">
			<div class="div_change_details" style="width:100%;height:280px;">
				<table class="grid_change_details"></table>
			</div>
		</div> 
		-->
		<div title="점용">
			<div class="div_occupation" style="width:100%;height:280px;">
				<table class="grid_occupation"></table>
			</div>
		</div>
		<div title="사진">
			<div class="div_photo">
				<div class="div_photo_tools">
					<a href="#" class="a_registration" style="margin:3px 10px" >등록</a>
				</div>
				<ul class="ul_photo" style="width:99%;height:230px;">
				</ul>
			</div>
		</div>
	</div>
	<div class="div_window_city_park_result_tabs_tool" style="padding:3px 5px 0 5px;">
		<a href="#" class="a_register_inquiry">대장조회</a>
		<a href="#" class="a_output_register">대장출력</a>
	</div>
</div>