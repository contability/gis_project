<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<div class="div_window_draw_tool">
	<table summary="그리기 도구 레이어명" class="cmmTable v2 ma_b_10">
		<caption>그리기 도구 레이어명</caption>
		<colgroup>
			<col width="25%">
			<col width="">
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">레이어명</th>
				<td class="td_draw_layer_name">
					<input type="text" class="txt_layer_name" />
				</td>
			</tr>
		</tbody>
	</table>
	
	<div class="div_draw_tool clearfix">
		<span class="span_title setbox f_l f_s_12 color_3 pa_r_18 pa_t_3 pa_l_5">신규 <br />그리기</span>
		<ul>
			<li class="pa_t_3 f_l">
				<a href="#" class="a_draw_tool_point"><img class="tool_toggle_radio" src="<c:url value='/images/kworks/main/draw/btn_symbol_off.png' />" alt="심볼 그리기" title="심볼 그리기" /></a>
				<a href="#" class="a_draw_tool_linestring"><img class="tool_toggle_radio" src="<c:url value='/images/kworks/main/draw/btn_line_off.png' />" alt="선 그리기" title="선 그리기"  /></a>
				<a href="#" class="a_draw_tool_rect"><img class="tool_toggle_radio" src="<c:url value='/images/kworks/main/draw/btn_rect_off.png' />" alt="면 그리기" title="면 그리기"  /></a>
				<a href="#" class="a_draw_tool_polygon"><img class="tool_toggle_radio" src="<c:url value='/images/kworks/main/draw/btn_polygon_off.png' />" alt="다각형 그리기" title="다각형 그리기"  /></a>
				<a href="#" class="a_draw_tool_circle"><img class="tool_toggle_radio" src="<c:url value='/images/kworks/main/draw/btn_circle_off.png' />" alt="원 그리기" title="원 그리기"  /></a>
				<a href="#" class="a_draw_tool_text"><img class="tool_toggle_radio" src="<c:url value='/images/kworks/main/draw/btn_text_off.png' />" alt="텍스트 그리기" title="텍스트 그리기"  /></a>
			</li>
		</ul>
	</div>
	<div class="div_select_tool clearfix">
		<span class="span_title setbox f_l f_s_12 color_3 pa_r_18 pa_t_3 pa_l_5">선택</span>
		<ul>
			<li class="pa_t_3 f_l">
				<a href="#" class="a_draw_tool_select"><img class="tool_toggle_radio" src="<c:url value='/images/kworks/main/draw/btn_select_off.png' />" alt="선택" title="선택" /></a>
				<a href="#" class="a_draw_tool_move"><img class="tool_toggle_radio" src="<c:url value='/images/kworks/main/draw/btn_move_off.png' />" alt="이동" title="이동" /></a>
				<a href="#" class="a_draw_tool_convert"><img src="<c:url value='/images/kworks/main/draw/btn_convert_off.png' />" alt="객체 전환" title="객체 전환"  /></a>
			</li>
		</ul>
	</div>
	
	<div class="div_draw_list">
		<table class="cmmTable ma_t_5" summary="">
			<caption></caption>
			<colgroup>
				<col width="25%">
				<col width="25%">
				<col width="25%">
				<col width="25%">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">순번</th>
					<th scope="col">타입</th>
					<th scope="col">스타일</th>
					<th scope="col">지우기</th>
				</tr>
			</thead>
		</table>
		<div class="scrollBox h_170">
			<table class="cmmTable rowLink bd_n" summary="">
				<caption></caption>
				<colgroup>
					<col width="25%">
					<col width="25%">
					<col width="25%">
					<col width="25%">
				</colgroup>
				<tbody class="tby_draw_list">
				</tbody>
			</table>
		</div>
	</div>
	<div class="div_draw_tool_tools">
		<a href="#" class="a_save" ><img src="<c:url value='/images/kworks/map/skin2/btn_save3.png'/>" alt="저장" title="저장"  /></a>
		<a href="#" class="a_close" ><img src="<c:url value='/images/kworks/main/draw/btn_close.png'/>" alt="닫기" title="닫기"  /></a>
	</div>
</div>