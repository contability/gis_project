<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>

<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>

<!-- 지적원도/드론영상 -->
<div class="div_menu_panel_cadastral">
	<div class="div_search">
		<div>
			<span class="span_title">읍면동</span>
			<span class="span_content"><select class="sel_dong"></select></span>	
		</div>
		<div>
			<span class="span_title">리</span>
			<span class="span_content"><select class="sel_li"></select></span>	
		</div>
		<div>
			<span class="span_title"><label for="div_search_lot_number_mntn">산</label></span>
			<span class="span_content"><input type="checkbox" class="chk_mntn" id="div_search_lot_number_mntn" /></span>
		</div>
		<div>
			<span class="span_title">본번</span>
			<span class="span_content"><input type="text" class="txt_mnnm" /></span>
		</div>
		<div>
			<span class="span_title">부번</span>
			<span class="span_content"><input type="text" class="txt_slno" /></span>
		</div>
		<div class="div_tool">
			<a href="#" class="a_search">검색</a>
		</div>
	</div>
	<div class="div_title">지적원도 목록</div>
	<div class="div_msg" style="margin-top:10px; margin-left:10px; margin-right:10px; font-size:12px; color:#666;">
	</div>
	<div class="div_tree">
		<ul class="ul_cadastral_tree"></ul>
	</div>
</div>