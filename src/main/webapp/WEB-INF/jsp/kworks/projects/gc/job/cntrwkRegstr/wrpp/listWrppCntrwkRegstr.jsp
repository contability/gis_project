<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div id="listWrppCntrwkRegstr" class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>공사명</th>
					<td colspan="3">
						<input type="text" class="input_text" name="cntNam" path="cntNam" />
					</td>
				</tr>
				<tr>
					<th>공사번호</th>
					<td>
						<input type="text" class="input_text" name="ftrIdn" path="ftrIdn" />
					</td>			
					<th>공사구분</th>
					<td>
						<form:select class="input_text" name="wrkCde" path="wrkCde" >
							<form:option value="" label="전체"/>
							<form:options items="${wrkCde}" itemValue="codeId" itemLabel="codeNm" />
						</form:select>
					</td>
				</tr>
				<tr>
					<th>계약일</th>
					<td>
						<input type="text" class="w_87 easyui-datebox" name="cttYmdMin" path="cttYmdMin" />
						<span class="space">~</span>
						<input type="text" class="w_87 easyui-datebox" name="cttYmdMax" path="cttYmdMax" />
					</td>
					<th>착공일</th>
					<td>
						<input type="text" class="w_87 easyui-datebox" name="begYmdMin" path="begYmdMin" />
						<span class="space">~</span>
						<input type="text" class="w_87 easyui-datebox" name="begYmdMax" path="begYmdMax" />
					</td>
				</tr>
				<tr>
					<th>준공일</th>
					<td>
						<input type="text" class="w_87 easyui-datebox" name="rfnYmdMin" path="rfnYmdMin" />
						<span class="space">~</span>
						<input type="text" class="w_87 easyui-datebox" name="rfnYmdMax" path="rfnYmdMax" />	
					</td>
					<th>계약금액(원)</th>
					<td>
						<input type="text" class="w_87 input_text_w" name="tctAmtMin" path="tctAmtMin" />
						<span class="space">~</span>
						<input type="text" class="w_87 input_text_w" name="tctAmtMax" path="tctAmtMax" />
					</td>											
				</tr>
			</tbody>
		</table>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="a_reset_wrppCntrwkRegstr button_flat_normal btn_blue">초기화</button>
			<button class="a_search_wrppCntrwkRegstr button_flat_normal btn_blue">검색</button>
		</div>
	</div>
</div>
