<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div id="listOcpatRegstr" class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>허가번호</th>
					<td>
						<input type="text" class="w_205 easyui-textbox" name="pmtNum" path="pmtNum"/>
					</td>
					<th>허가일</th>
					<td>
						<input type="text" class="w_90 easyui-datebox" name="pmtYmdMin" path="pmtYmdMin"/>
						<span class="space">~</span>
						<input type="text" class="w_90 easyui-datebox" name="pmtYmdMax" path="pmtYmdMax"/>
					</td>							
				</tr>
				<tr>
					<th>점용위치</th>
					<td colspan="3">
						<input type="text" class="easyui-textbox" name="jygLoc" path="jygLoc" style="width:99%"/>
					</td>
				</tr>
				<tr>
					<th>점용자성명</th>
					<td>
						<input type="text" class="w_205 easyui-textbox" name="prsNam" path="prsNam"/>
					</td>
					<th>점용기간</th>
					<td>
						<select id="jygUlm" class="w_205 easyui-combobox">
							<option value="-1">전체</option>
							<option value="0">미분류</option>
							<option value="1">영구</option>
							<option value="2">일시</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>점용시작일</th>
					<td>
						<input type="text" class="w_90 easyui-datebox" name="jysYmdMin" path="jysYmdMin"/>
						<span class="space">~</span>
						<input type="text" class="w_90 easyui-datebox" name="jysYmdMax" path="jysYmdMax"/>
					</td>
					<th>점용종료일</th>
					<td>
						<input type="text" class="w_90 easyui-datebox" name="jyeYmdMin" path="jyeYmdMin"/>
						<span class="space">~</span>
						<input type="text" class="w_90 easyui-datebox" name="jyeYmdMax" path="jyeYmdMax"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="a_reset_ocpatRegstr button_flat_normal btn_blue">초기화</button>
			<button class="a_search_ocpatRegstr button_flat_normal btn_blue">검색</button>
		</div>
	</div>
</div>
