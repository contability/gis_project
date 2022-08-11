<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form id="addRepairform" name="addRepairform" method="post" enctype="multipart/form-data">
	<div id="addCityPlanRoadRepair" class="window_container">
		<div class="window_article">
			<input type="hidden" name="ftrIdn" value="${ftrIdn}" />
			<table class="table_text">
				<tbody>
					<tr>
						<th>관리번호</th>
						<td colspan="5" id="ftrIdn">${ftrIdn}</td>
					</tr>
					<tr>
						<th>시작일</th>
						<td>
							<input type="text" class="w_205 easyui-datebox"	name="repStr" />						
						</td>
						<th>종료일</th>
						<td>
							<input type="text" class="w_205 easyui-datebox"	name="repEnd" />						
						</td>
					</tr>
					<tr>
						<th>정비내용</th>
						<td colspan="5">
							<input type="text" class="easyui-textbox" name="repDoc" style="width:99%" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button id="btn_insert" class="button_flat_normal btn_blue">등록</button>
				<button id="btn_close" class="button_flat_normal btn_black">닫기</button>
			</div>
		</div>
	</div>
</form>