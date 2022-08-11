<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	var CONTEXT_PATH = "${pageContext.request.contextPath}";
</script>

<link type="text/css" rel="stylesheet"
	href="<c:url value='/css/kworks/main/main.css'/>" />

<!-- 상단 탭  -->
<div class="div_menu_panel_prtsArea_tab" class="mapTab clearfix">
	<div class="prtsAreaTab" title="<span class='span_tab_title'>보호<br>구역</span>">
		<div id="div_menu_panel_search_prtsArea" class="tab_content">
			<div class="div_search_condition">
				<div>
					<span class="span_title">분류</span> 
					<span class="span_content"><select class="prtsArea_ftrCde"></select></span>
				</div>
				<div>
					<span class="span_title">법정동</span> 
					<span class="span_content"><select class="prtsArea_sel_dong"></select></span>
				</div>
				<!-- <div>
					<span class="span_title">산</span> 
					<span class="span_content"><input class="prtsArea_mntn"></span>
				</div> -->
				<div>
					<span class="span_title">본번</span> 
					<span class="span_content"><input class="prtsArea_facNum"></span>
				</div>
				<div>
					<span class="span_title">부번</span>
					<span class="span_content"><input class="prtsArea_fadNum"></span>
				</div>
				<div>
					<span class="span_title">관리번호</span>
					<span class="span_content"><input class="prtsArea_ftrIdn"></span>
				</div>
				<div>
					<span class="span_title">도로명</span> 
					<span class="span_content"><input class="prtsArea_roadNm"></span>
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
			<div class="div_search_pagination"></div>
		</div>
	</div>
</div>
