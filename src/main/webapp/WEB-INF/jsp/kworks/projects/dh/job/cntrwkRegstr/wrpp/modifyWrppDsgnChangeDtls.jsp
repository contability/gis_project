<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="modifyWrppDsgnChangeDtls" name="modifyWrppDsgnChangeDtls" method="post" enctype="multipart/form-data">
	<div class="window_container">
		<div class="window_article">
			<input type="hidden" id="ftrIdn" value="${result.ftrIdn}" />
			<input type="hidden" id="shtIdn" value="${result.shtIdn}" />
			<table class="table_text">
				<tbody>
					<tr>
						<th>변경일자</th>
						<td>
							<input type="text" name="chgYmd" class="input_text_n w_90 easyui-datebox" value="${result.chgYmd}" maxlength="8" />
						</td>
						<th>증감금액</th>
						<td>
							<input type="text" name="incAmt" class="input_text numOnly" value="${result.incAmt}" maxlength="13" />
						</td>
					</tr>
					<tr>
						<th>증감관급<br>금액</th>
						<td>
							<input type="text" name="igvAmt" class="input_text numOnly" value="${result.igvAmt}" maxlength="13" />
						</td>
						<th>변경공사<br>총액</th>
						<td>
							<input type="text" name="chgAmt" class="input_text numOnly" value="${result.chgAmt}" maxlength="13" />
						</td>
					</tr>
					<tr>
						<th>변경사업량</th>
						<td colspan="3">
							<input type="text" name="chgDes" class="input_text" value="${result.chgDes}" maxlength="99" />
						</td>
					</tr>
					<tr>
						<th>변경관급량</th>
						<td colspan="3">
							<input type="text" name="cgvDes" class="input_text" value="${result.cgvDes}" maxlength="99" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
			<button class="btn_save_wrppDsgnChangeDtlsModify button_flat_normal btn_blue">저장</button>
			<button class="btn_save_wrppDsgnChangeDtlsRemove button_flat_normal btn_blue">삭제</button>
		</div>
	</div>
</form>
