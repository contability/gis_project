<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form id="addPassPointform" name="addPassPointform" method="post" enctype="multipart/form-data">
	<div id="addPassPoint" class="window_container">
		<div class="window_article">
			<input type="hidden" name="alias" value="${alias}" />
			<input type="hidden" name="ftrCde" value="${ftrCde}" />
			<input type="hidden" name="ftrIdn" value="${ftrIdn}" />
			<table class="table_text">
				<tbody>
					<tr>
						<th>기준점 종류</th>
						<td>${alias}</td>
						<th>관리번호</th>
						<td>${ftrIdn}</td>
					</tr>
					<tr>
						<th>시준점</th>
						<td colspan="5">
							<input type="text" class="easyui-textbox" name="ecpNam" style="width:99%"/>						
						</td>
					</tr>
					<tr>
						<th>방위각</th>
						<td>
							<input type="text" class="w_205 easyui-textbox" name="ecpDeg"/>						
						</td>
						<th>거리</th>
						<td>
							<input type="text" class="w_205 easyui-numberbox" name="ecpLen"/>						
						</td>
					</tr>
					<tr>
						<th>비고</th>
						<td colspan="3">
							<input type="text" class="easyui-textbox" name="rmkDes" style="width:99%"/>						
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
			