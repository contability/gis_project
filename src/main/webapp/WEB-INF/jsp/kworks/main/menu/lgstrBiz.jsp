<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href='<c:url value="/css/kworks/main/main.css"/>'/>

<!-- tab -->
<div id="div_menu_panel_lgstrBiz_tab" class="mapTab clearfix"></div>

<!-- 환지 검색  -->
<div id="div_menu_panel_search_rplotreexmn" class="tab_content">
	<div class="div_search_condition">
		<div>
			<span class="span_title">읍면동</span>
			<span class="span_content"><select class="sel_dong"></select></span>
		</div>
		<div>
			<span class="span_title">리</span>
			<span class="span_content"><select class="sel_li"></select></span>
		</div>
		<div>
			<span class="span_title"><label for="div_menu_panel_rplotreexmn_search_lot_number_mntn">산</label></span>
			<span class="span_content"><input type="checkbox" class="chk_mntn" id="div_menu_panel_rplotreexmn_search_lot_number_mntn"></span>
		</div>
		<div>
			<span class="span_title">본번</span>
			<span class="span_content"><input type="text" class="txt_mnnm"></span>
		</div>
		<div>
			<span class="span_title">부번</span>
			<span class="span_content"><input type="text" class="txt_slno"></span>
		</div>
		<div>
			<span class="span_title">사업지구명</span>
			<span class="span_content"><input id="bsnsAreaNm"></span>
		</div>
		<div class="div_tool">
			<a href="#" class="a_reset">초기화</a>
			<a href="#" class="a_search">검색</a>
		</div>
	</div>
	<div class="div_search_content">
		<div class="total_count">총 <font class="font_total_count"></font>건을 검색하였습니다.</div>
		<ul class="ul_search_content"></ul>
	</div>
	<div class="div_search_pagination">
	</div>
</div>

<!-- 재조사 검색  -->
<div id="div_menu_panel_search_reexmn" class="tab_content">
	<div class="div_search_condition">
		<div>
			<span class="span_title">읍면동</span>
			<span class="span_content"><select class="sel_dong"></select></span>
		</div>
		<div>
			<span class="span_title">리</span>
			<span class="span_content"><select class="sel_li"></select></span>
		</div>
		<div>
			<span class="span_title"><label for="div_menu_panel_rplotreexmn_search_lot_number_mntn">산</label></span>
			<span class="span_content"><input type="checkbox" class="chk_mntn" id="div_menu_panel_reexmn_search_lot_number_mntn"></span>
		</div>
		<div>
			<span class="span_title">본번</span>
			<span class="span_content"><input type="text" class="txt_mnnm"></span>
		</div>
		<div>
			<span class="span_title">부번</span>
			<span class="span_content"><input type="text" class="txt_slno"></span>
		</div>
		<div>
			<span class="span_title">사업지구명</span>
			<span class="span_content"><input id="bsnsAreaNm"></span>
		</div>
		<div class="div_tool">
			<a href="#" class="a_reset">초기화</a>
			<a href="#" class="a_search">검색</a>
		</div>
	</div>
	<div class="div_search_content">
		<div class="total_count">총 <font class="font_total_count"></font>건을 검색하였습니다.</div>
		<ul class="ul_search_content"></ul>
	</div>
	<div class="div_search_pagination">
	</div>
</div>


<!--  정비보류지역 관리조서 검색  -->
<div id="div_menu_panel_search_iprvar" class="tab_content">
	<div class="div_search_condition">
		<div>
			<span class="span_title">읍면동</span>
			<span class="span_content"><select class="sel_dong"></select></span>
		</div>
		<div>
			<span class="span_title">리</span>
			<span class="span_content"><select class="sel_li"></select></span>
		</div>
		<div>
			<span class="span_title"><label for="div_menu_panel_iprvar_search_lot_number_mntn">산</label></span>
			<span class="span_content"><input type="checkbox" class="chk_mntn" id="div_menu_panel_iprvar_search_lot_number_mntn"></span>
		</div>
		<div>
			<span class="span_title">본번</span>
			<span class="span_content"><input type="text" class="txt_mnnm"></span>
		</div>
		<div>
			<span class="span_title">부번</span>
			<span class="span_content"><input type="text" class="txt_slno"></span>
		</div>
		<div class="div_tool">
			<a href="#" class="a_register">등록</a>
			<a href="#" class="a_reset">초기화</a>
			<a href="#" class="a_search">검색</a>
		</div>
	</div>
	<div class="div_search_content">
		<div class="total_count">총 <font class="font_total_count"></font>건을 검색하였습니다.</div>
		<ul class="ul_search_content"></ul>
	</div>
	<div class="div_search_pagination">
	</div>
</div>