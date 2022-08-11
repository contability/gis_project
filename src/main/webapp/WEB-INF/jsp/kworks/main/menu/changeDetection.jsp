<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="div_menu_panel_changeDetection" class="tab_content" >
	<div class="div_title">
		<h4>변화지역 탐지</h4>
	</div>
	<div class="div_search_condition">
		<div style="display: none;">
			<span class="span_title">작성년도 : </span>
			<span class="span_content"><input type="text" class="writingYear" /></span> 
		</div>
		<div>
			<span class="span_title">제목 : </span>
			<span class="span_content"><input type="text" class="subject" /></span>
		</div>
		<div class="div_tool">
			<a href="#" class="a_add">추가</a>
			<a href="#" id="a_search_menu_changeDetectionDetails" class="a_search">검색</a>
		</div>
	</div>
	<div class="div_search_content">
		<div class="total_count">총  <font class="font_total_count"></font>건을 검색하였습니다.</div>
		<ul class="ul_search_content">
		</ul>
	</div>
	<div class="div_search_pagination">
	</div>
</div>