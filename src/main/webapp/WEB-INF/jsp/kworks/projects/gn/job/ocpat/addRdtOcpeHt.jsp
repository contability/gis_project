<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form id="addRdtOcpeHtAddform" name="addRdtOcpeHtAddform" method="post" enctype="multipart/form-data">
	<div id="addRdtOcpeHtRegstr" class="window_container">
		<div class="window_article">
			<input type="hidden" name="ftrCde" path="ftrCde" value="${ftrCde}" />
			<input type="hidden" name="ftrIdn" path="ftrIdn" value="${ftrIdn}" />
			
			<table class="table_text">
				<tbody>
					<tr>
						<th>관리번호</th>
						<td colspan="5">${ftrIdn}</td>
					</tr>
					<tr>
						<th>변동허가번호</th>
						<td colspan="5">
							<input type="text" class="easyui-textbox" name="histNum" path="histNum" style="width:99%"/>
						</td>
					</tr>
					<tr>
						<th>변동사항</th>
						<td colspan="5">
							<input type="text" class="easyui-textbox" name="histDoc" path="histDoc" style="width:99%"/>
						</td>
					</tr>
					<tr>
						<th>비고</th>
						<td colspan="5">
							<input type="text" class="easyui-textbox" name="rmkExp" path="rmkExp" style="width:99%"/>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
			<div class="window_footer"> <!-- 하단 -->
				<div class="button_flat">
					<button class="btn_save_RdtOcpeHtRegstr button_flat_normal btn_blue">저장</button>
					<button class="btn_close_RdtOcpeHtRegstr button_flat_normal btn_blue">닫기</button>
				</div>
			</div> <!-- 하단 -->
	</div>
</form>	
