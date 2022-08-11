<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>

<div id="div_window_local_plan" class="layerCont">
	<input type="hidden" id="hid_list_local_plan_ftr_idn" value="" />
	<table class="cmmTable v2 ma_b_30" summary="단위도면 정보 관련 테이블">
		<caption>단위도면 정보 테이블</caption>
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
					<span>
						<input type="text" id="txt_list_local_plan_rot_nam" name="rotNam" value="" class="w100" readonly="readonly" />
					</span>
				</td>
			</tr>
			<tr>
				<th>노선번호</th>
				<td>
					<span>
						<input type="text" id="txt_list_local_plan_rot_idn" name="rotIdn" value="" class="w100" readonly="readonly" />
					</span>
				</td>
				<th>구간</th>
				<td>
					<span>
						<input type="text" id="txt_list_local_plan_sec_idn" name="secIdn" value="" class="w100" readonly="readonly" />
					</span>
				</td>
			</tr>
		</tbody>
	</table>

	<div class="div_content_container panel">
		<!-- datagrid 영역 -->
		<table id="tbl_local_plan_list">
		</table>
	</div>
	<!-- 하단 버튼 레이아웃 영역 -->
	<div class="div_tool_local_plan">
		<!-- 편집 저장, 닫기 버튼 -->
		<a class="a_add_list_local btn_register" href="#" ></a>
		<a class="a_close_list_local btn_close" href="#" ></a>
	</div>
</div>
