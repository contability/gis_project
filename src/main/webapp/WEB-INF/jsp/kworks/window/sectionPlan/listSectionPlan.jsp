<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<div id="div_window_section_plan" class="layerCont">
	<input type="hidden" id="hid_list_section_plan_file_no" value="" />
	<input type="hidden" id="hid_list_section_plan_ftr_idn" value="" />
	<table class="cmmTable v2 ma_b_30" summary="구간도면 정보 관련 테이블">
		<caption>구간도면 정보 테이블</caption>
		<colgroup>
			<col width="22%" />
			<col width="28%" />
			<col width="25%" />
			<col width="25%" />
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">노선명</th>
				<td colspan="3">
					<span id="spn_list_section_plan_rot_nam"></span>
				</td>
			</tr>
			<tr>
				<th>노선번호</th>
				<td>
					<span id="spn_list_section_plan_rot_idn"></span>
				</td>
				<th>구간</th>
				<td>
					<span id="spn_list_section_plan_sec_idn"></span>
				</td>
			</tr>
			<tr>
				<th scope="row">구간도면 파일명</th>
				<td colspan="3">
					<span id="spn_list_section_plan_doc_file"></span>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="div_tool_section_plan">
		<!-- 내려받기, 편집, 닫기 버튼 -->
		<a class="a_down_list_section btn_down" href="#" ></a>
		<a class="a_add_list_section btn_register" href="#" ></a>
		<a class="a_close_list_section btn_close" href="#" ></a>
	</div>
</div>
