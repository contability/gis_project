<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>

<div class="div_window_draw_tool">
	<input type="hidden" class="chngeDetctNo">
	<input type="hidden" class="partitnCd">
	<table summary="그리기 도구 레이어명" class="cmmTable v2 ma_b_10">
		<caption>그리기 도구 레이어명</caption>
		<colgroup>
			<col width="25%">
			<col width="">
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">위치</th>
				<td class="td_change_detection_area_location">
					<input type="text" class="txt_chnge_area_lc" />
				</td>
			</tr>
			<tr>
				<th scope="row">설명</th>
				<td class="td_change_detection_area_description">
					<input type="text" class="txt_chnge_area_dc" />
				</td>
			</tr>
			<tr>
				<th scope="row">특이사항</th>
				<td class="td_change_detection_area_remark">
					<textarea class="txa_chnge_area_rm" name="chngeAreaRm" rows="4" cols="43"></textarea>
				</td>
			</tr>
		</tbody>
	</table>
	
	<div class="div_multi_tool clearfix">
		<span class="span_title setbox f_l f_s_12 color_3 pa_r_18 pa_t_3 pa_l_5">화면분할 <br/>선택</span>
		<ul>
			<li class="pa_t_3 f_l">
				<a href="#" class="a_draw_tool_rtn01 a_changeDetectionArea_multi_map" data-mode="0">
					<img class="tool_toggle_radio img_changeDetectionArea_tool_RTN00" src="<c:url value='/images/kworks/map/skin2/square01_off.png' />" alt="1면" title="1면"/>
				</a>
				<a href="#" class="a_draw_tool_rtn02 a_changeDetectionArea_multi_map" data-mode="1">
					<img class="tool_toggle_radio img_changeDetectionArea_tool_RTN01" src="<c:url value='/images/kworks/map/skin2/square02_off.png' />" alt="2면(좌우)" title="2면(좌우)"/>
				</a>
				<a href="#" class="a_draw_tool_rtn03 a_changeDetectionArea_multi_map" data-mode="2">
					<img class="tool_toggle_radio img_changeDetectionArea_tool_RTN02" src="<c:url value='/images/kworks/map/skin2/square03_off.png' />" alt="2면(상하)" title="2면(상하)"/>
				</a>
				<a href="#" class="a_draw_tool_rtn04 a_changeDetectionArea_multi_map" data-mode="3">
					<img class="tool_toggle_radio img_changeDetectionArea_tool_RTN03" src="<c:url value='/images/kworks/map/skin2/square04_off.png' />" alt="3면(좌)" title="3면(좌)"/>
				</a>
				<a href="#" class="a_draw_tool_rtn05 a_changeDetectionArea_multi_map" data-mode="4">
					<img class="tool_toggle_radio img_changeDetectionArea_tool_RTN04" src="<c:url value='/images/kworks/map/skin2/square05_off.png' />" alt="3면(우)" title="3면(우)"/>
				</a>
				<a href="#" class="a_draw_tool_rtn06 a_changeDetectionArea_multi_map" data-mode="5">
					<img class="tool_toggle_radio img_changeDetectionArea_tool_RTN05" src="<c:url value='/images/kworks/map/skin2/square06_off.png' />" alt="3면(상)" title="3면(상)"/>
				</a>
				<a href="#" class="a_draw_tool_rtn07 a_changeDetectionArea_multi_map" data-mode="6">
					<img class="tool_toggle_radio img_changeDetectionArea_tool_RTN06" src="<c:url value='/images/kworks/map/skin2/square07_off.png' />" alt="3면(하)" title="3면(하)"/>
				</a>
				<a href="#" class="a_draw_tool_rtn08 a_changeDetectionArea_multi_map" data-mode="7">
					<img class="tool_toggle_radio img_changeDetectionArea_tool_RTN07" src="<c:url value='/images/kworks/map/skin2/square08_off.png' />" alt="4면" title="4면"/>
				</a>
			</li>
		</ul>
	</div>
	
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
				<a href="#" class="a_draw_tool_edit"><img class="tool_toggle_radio" src="<c:url value='/images/kworks/main/draw/btn_edit_off.png' />" alt="객체편집" title="객체편집" /></a>
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
		<a href="#" class="a_save_addChangeDetectionArea" ><img src="<c:url value='/images/kworks/map/skin2/btn_save3.png'/>" alt="저장" title="저장"  /></a>
		<a href="#" class="a_close_addChangeDetectionArea" ><img src="<c:url value='/images/kworks/main/draw/btn_close.png'/>" alt="닫기" title="닫기"  /></a>
	</div>
</div>