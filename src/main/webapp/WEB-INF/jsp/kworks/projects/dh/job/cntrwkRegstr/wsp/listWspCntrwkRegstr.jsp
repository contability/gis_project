<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div id="listWspCntrwkRegstr" class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>착공일</th>
					<td>
						<input type="text" class="w_87 easyui-datebox" name="begYmdMin" path="begYmdMin" />
						<span class="space">~</span>
						<input type="text" class="w_87 easyui-datebox" name="begYmdMax" path="begYmdMax" />
					</td>
					<th>시공자상호</th>
					<td>
						<input type="text" class="input_text" name="oprNam" path="oprNam" />
					</td>
				</tr>
				<tr>
					<th>준공일</th>
					<td>
						<input type="text" class="w_87 easyui-datebox" name="fnsYmdMin" path="fnsYmdMin" />
						<span class="space">~</span>
						<input type="text" class="w_87 easyui-datebox" name="fnsYmdMax" path="fnsYmdMax" />	
					</td>
					<th>공사비총액</th>
					<td>
						<input type="text" class="w_87 input_text_w" name="totAmtMin" path="totAmtMin" />
						<span class="space">~</span>
						<input type="text" class="w_87 input_text_w" name="totAmtMax" path="totAmtMax" />
					</td>	
				</tr>
			</tbody>
		</table>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="a_reset_wspCntrwkRegstr button_flat_normal btn_blue">초기화</button>
			<button class="a_search_wspCntrwkRegstr button_flat_normal btn_blue">검색</button>
		</div>
	</div>
</div>
