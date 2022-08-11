<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	 uri="http://java.sun.com/jsp/jstl/core" %>

<div class="layerCont">
	<form method="post" id="addLocalPlanAddform" name="addLocalPlanAddform" enctype="multipart/form-data" >
		<input type="hidden" id="txt_add_local_plan_ftr_idn" name="ftrIdn" value ="" />
		<input type="hidden" id="txt_add_local_plan_file_no" name="fileNo" value ="" />
			<table class="cmmTable v2" summary="단위도면 등록 테이블">
				<caption>단위도면 등록 테이블</caption>
				<colgroup>
					<col width="22%" />
					<col width="28%" />
					<col width="25%" />
					<col width="25%" />
				</colgroup>
				<tbody id="tbl_local_plan_add">
					<tr>
						<th scope="row">노선명</th>
						<td colspan="3">
							<input type="text" id="txt_add_local_plan_rot_nam" name="rotNam" class="w100" value="" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<th>노선번호</th>
						<td>
							<input type="text" id="txt_add_local_plan_rot_idn" name="rotIdn" class="w100" value="" readonly="readonly" />
						</td>
						<th>구간</th>
						<td>
							<input type="text" id="txt_add_local_plan_sec_idn" name="secIdn" class="w100" value="" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<th>구간이정</th>
						<td>
							<input type="text" id="txt_add_local_plan_sec_dis" name="secDis" class="w100" value="" />
						</td>
						<th>도면</th>
						<td>
							<select name="lclCde" class="selectBox w100">
								<c:forEach items="${lclCde}" var="lclCde" varStatus="status">
									<option value="${lclCde.codeId}">${lclCde.codeNm}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
					<tr>
						<th scope="row">파일</th>
						<td colspan="3">
							<p class="ma_b_10">
								<input type="file" name="localPlanFile" value="" accept="video/mp4|image/*" />
							</p>
						</td>
					</tr>
				</tbody>
			</table>
		<div class="div_tool_local_plan">
			<a class="a_save_add_local_plan btn_register" href="#" ></a>
			<a class="a_close_add_local_plan btn_close" href="#" ></a>
		</div>
	</form>
</div>