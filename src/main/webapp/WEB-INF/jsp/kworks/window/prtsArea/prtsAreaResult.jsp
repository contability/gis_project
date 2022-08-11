<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
#prtsArea_dtl{
	text-align: center;
	display : block;
	position: relative;
	padding : 5px;
	left: 280px;
	top : 7px;
	width: 84px;
}

</style>

<!-- 보호구역 검색 결과 -->
<div class="div_window_prtsArea_result">
	<div class="div_window_prtsArea_result_tabs">
		<div title="보호구역">
			<div class="div_prtsArea" data-options="fit:true" style="width:100%; height:280px;">
				<div data-options="region:'west'" style="width: 400px;">
					<table class="grid_prtsArea"></table>
					<div id="prtsArea_dtl">
						<a>상세 조회</a>
					</div>
				</div>
				<div class="div_result_center" data-options = "region:'center'">
					<div class="div_facility" data-options="fit:true" style="width:100%; height:250px;">
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
		<div title="개선사업이력">
			<div class="div_prtsAreaHt" data-options="fit:true" style="width:100%; height:280px;">
				<div data-options="region:'center'" style="width:100%;">
					<table class="grid_prtsAreaHt"></table>
				</div>
			</div>
			<!-- <table class="grid_prtsAreaHt"></table> -->
		</div>
		
		
		
		<div class="div_window_prtsArea_tabs_tool" style="padding:3px 5px 0px 5px;">
			<a href="#" class="a_addPrtsAreaHt">개선사업이력 등록</a>
			<a href="#" class="a_exportToExcel">엑셀 내보내기</a>
		</div>
	</div>
</div>