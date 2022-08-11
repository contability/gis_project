<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<form id="wspCntrwkRegstrAdd" name="wspCntrwkRegstrAdd" method="post" enctype="multipart/form-data">
	<div class="window_container">
		<div class="window_article">
			<table class="table_text">
				<tbody>
					<tr>
						<th>착공일</th>
						<td>
							<input type="text" name="begYmd" class="input_text_n w_90 easyui-datebox" maxlength="8" />
						</td>
						<th>준공일</th>
						<td>
							<input type="text" name="fnsYmd" class="input_text_n w_90 easyui-datebox" maxlength="8" />
						</td>
					</tr>
					<tr>
						<th>수입일</th>
						<td>
							<input type="text" name="rcpYmd" class="input_text_n w_90 easyui-datebox" maxlength="8" />
						</td>
						<th>시공자상호</th>
						<td>
							<input type="text" name="oprNam" class="input_text" maxlength="20" />
						</td>
					</tr>
					<tr>
						<th>감독자성명</th>
						<td>
							<input type="text" name="svsNam" class="input_text" maxlength="20" />
						</td>
						<th>준공검사자성명</th>
						<td>
							<input type="text" name="fnsNam" class="input_text" maxlength="20" />
						</td>
					</tr>
					<tr>
						<th>관급자재비</th>
						<td>
							<input type="text" name="gvrAmt" class="input_text numOnly" />
						</td>
						<th>사급자재비</th>
						<td>
							<input type="text" name="prvAmt" class="input_text numOnly" />
						</td>
					</tr>
					<tr>
						<th>부가가치세</th>
						<td>
							<input type="text" name="taxAmt" class="input_text numOnly" />
						</td>
						<th>도로복구비</th>
						<td>
							<input type="text" name="rorAmt" class="input_text numOnly" />
						</td>
					</tr>
					<tr>
						<th>설계수수료</th>
						<td>
							<input type="text" name="dfeAmt" class="input_text numOnly" />
						</td>
						<th>자재검사<br>수수료</th>
						<td>
							<input type="text" name="gfeAmt" class="input_text numOnly" />
						</td>
					</tr>
					<tr>
						<th>준공검사<br>수수료</th>
						<td>
							<input type="text" name="ffeAmt" class="input_text numOnly" />
						</td>
						<th>시설분담금</th>
						<td>
							<input type="text" name="divAmt" class="input_text numOnly" />
						</td>
					</tr>
					<tr>
						<th>기타금액</th>
						<td>
							<input type="text" name="etcAmt" class="input_text numOnly" />
						</td>
						<th>공사비총액</th>
						<td>
							<input type="text" name="totAmt" class="input_text numOnly" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="window_footer">
			<div class="button_flat">
				<button class="btn_save_wspCntrwkRegstrAdd button_flat_normal btn_blue">등록</button>
			</div>
		</div>
	</div>
</form>
