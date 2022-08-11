<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="modifyRoadCntrwkCtPymntDtls" name="modifyRoadCntrwkCtPymntDtls" method="post" enctype="multipart/form-data">
	<div class="window_container">
		<div class="window_article">
			<input type="hidden" id="ftrIdn" value="${result.ftrIdn}" />
			<input type="hidden" id="shtIdn" value="${result.shtIdn}" />
			<table class="table_text">
				<tbody>
					<tr>
						<th>지급구분</th>
						<td>
							<select name="payCde" class="input_text">
									<c:forEach items="${payCdeList}" var="payCdeList" varStatus="status">
										<option value="${payCdeList.codeId}" ${payCdeList.codeId==result.payCde ? 'selected' : ''}>${payCdeList.codeNm}</option>
									</c:forEach>
							</select>
						</td>
						<th>지급일자</th>
						<td>
							<input type="text" name="payYmd" class="input_text_n w_90 easyui-datebox" value="${result.payYmd}" maxlength="8" />
						</td>
					</tr>
					<tr>
						<th>지급금액</th>
						<td colspan="3">
							<input type="text" name="payAmt" class="input_text numOnly" value="${result.payAmt}" maxlength="13" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
			<button class="btn_save_roadCntrwkCtPymntDtlsModify button_flat_normal btn_blue">저장</button>
			<button class="btn_save_roadCntrwkCtPymntDtlsRemove button_flat_normal btn_blue">삭제</button>
		</div>
	</div>
</form>