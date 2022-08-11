<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 종단면도 팝업 -->
<div class="div_window_topographic_section_view">
	<div style="padding:20px 10px;">
		<label for="topoCombo">지형데이터</label>
		<select id="topoCombo" class="topographic w_150"></select>
	</div>
	<div style="padding:5px 20px;">
		<label for="topoInterval">추출간격</label>
		<input id="topoInterval" class="interval_distance w_100" type="text" value="100" />
		<label for="topoInterval">M(미터)</label>
	</div>
	<div style="padding:20px 10px;">
		<span class="f_l">
			<a class="a_select" href="#" >
				<img class="w_30 h_30 tool_toggle_radio" src="<c:url value='/images/kworks/map/skin2/popBtn_paint11_off.png'/>" alt="분석위치선택" title="분석위치선택"  />
			</a>
		</span>
		<span class="f_r">
			<a class="a_analysis" href="#" >
				<img src="<c:url value='/images/kworks/map/common/btn_analysis.png'/>" alt="분석" title="분석"  />
			</a>
	 		<a class="a_close" href="#" >
	 			<img src="<c:url value='/images/kworks/map/common/btn_close.png'/>" alt="닫기" title="닫기"  />
	 		</a>
		</span>
	</div>
</div>