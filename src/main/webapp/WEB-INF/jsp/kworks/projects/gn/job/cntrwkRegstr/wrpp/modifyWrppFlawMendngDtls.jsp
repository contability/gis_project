<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="modifyWrppFlawMendngDtls" name="modifyWrppFlawMendngDtls" method="post" enctype="multipart/form-data">
	<div class="window_container">
		<div class="window_article">
			<input type="hidden" id="ftrIdn" value="${result.ftrIdn}" />
			<input type="hidden" id="shtIdn" value="${result.shtIdn}" />
			<table class="table_text">
				<tbody>
					<tr>
						<th>하자발생일</th>
						<td>
							<input type="text" name="flaYmd" class="input_text_n w_90 easyui-datebox" value="${result.flaYmd}" maxlength="8" />
						</td>
						<th>하자보수일</th>
						<td>
							<input type="text" name="rprYmd" class="input_text_n w_90 easyui-datebox" value="${result.rprYmd}" maxlength="8" />
						</td>
					</tr>
					<tr class="h_100">
						<th>하자보수<br>내용</th>
						<td colspan="3">
							<textarea class="w_300" name="rprDes" rows="6">${result.rprDes}</textarea>
						</td>				
					</tr>
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button class="btn_save_wrppFlawMendngDtlsModify button_flat_normal btn_blue">저장</button>
				<button class="btn_save_wrppFlawMendngDtlsRemove button_flat_normal btn_blue">삭제</button>
			</div>
		</div>
	</div>
</form>
