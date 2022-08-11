<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="modifyWrppSubcntrDtls" name="modifyWrppSubcntrDtls" method="post" enctype="multipart/form-data">
	<div class="window_container">
		<div class="window_article">
			<input type="hidden" id="ftrIdn" value="${result.ftrIdn}" />
			<input type="hidden" id="shtIdn" value="${result.shtIdn}" />
			<table class="table_text">
				<tbody>
					<tr>
						<th>상호명</th>
						<td>
							<input type="text" name="subNam" class="input_text" value="${result.subNam}" maxlength="29" />
						</td>
						<th>대표자</th>
						<td>
							<input type="text" name="psbNam" class="input_text" value="${result.psbNam}" maxlength="29" />
						</td>
					</tr>
					<tr>
						<th>주소</th>
						<td colspan="3">
							<input type="text" name="subAdr" class="input_text" value="${result.subAdr}" maxlength="49" />
						</td>
					</tr>
					<tr>
						<th>전화번호</th>
						<td colspan="3">
							<input type="text" name="subTel" class="input_text" value="${result.subTel}" maxlength="49" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
			<button class="btn_save_wrppSubcntrDtlsModify button_flat_normal btn_blue">저장</button>
			<button class="btn_save_wrppSubcntrDtlsRemove button_flat_normal btn_blue">삭제</button>
		</div>
	</div>
</form>