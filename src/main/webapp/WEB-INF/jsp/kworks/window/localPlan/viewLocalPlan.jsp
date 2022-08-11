<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	 uri="http://java.sun.com/jsp/jstl/core" %>

<div class="layerCont">
	<input type="hidden" id="hid_view_local_plan_file_no" value="">
	<input type="hidden" id="hid_view_local_plan_ftr_idn" value="">
	<table class="cmmTable v2 ma_b_30" summary="단위도면 정보 관련 테이블">
		<caption>단위도면 정보 테이블</caption>
		<colgroup>
			<col width="22%" />
			<col width="28%" />
			<col width="25%" />
			<col width="25%" />
		</colgroup>
		<tbody id="tbl_local_plan_view">
			<tr>
				<th scope="row">노선명</th>
				<td colspan="3">
					<span id="spn_view_local_plan_rot_nam"></span>
				</td>
			</tr>
			<tr>
				<th>노선번호</th>
				<td>
					<span id="spn_view_local_plan_rot_idn"></span>
				</td>
				<th>구간</th>
				<td>
					<span id="spn_view_local_plan_sec_idn"></span>
				</td>
			</tr>
			<tr>
				<th>구간이정</th>
				<td>
					<span id="spn_view_local_plan_sec_dis"></span>
				</td>
				<th>도면</th>
				<td>
					<span id="spn_view_local_plan_lcl_cde"></span>
				</td>
			</tr>
			<tr>
				<th scope="row">작성일자</th>
				<td colspan="3">
					<span id="spn_view_local_plan_frst_rgsde"></span>
				</td>
			</tr>
			<tr>
				<th scope="row">단위도면 파일명</th>
				<td colspan="3">
					<span id="spn_view_local_plan_doc_file"></span>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="div_tool_local_plan">
		<a class="a_down_view_local btn_down" href="#" ></a>
		<a class="a_modify_view_local btn_edit" href="#" ></a>
		<a class="a_remove_view_local btn_del" href="#" ></a>
		<a class="a_close_view_local_plan btn_close" href="#" ></a>
	</div>
</div>
