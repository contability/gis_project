<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 		uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"	uri="http://www.springframework.org/tags/form" %>

<div id="searchRoadCivilAppeal" class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>도로민원<br>구분</th>
					<td>
						<form:select class="input_text" name="aplCde" path="aplCde" >
							<form:option value="" label="전체"/>
							<form:options items="${aplCde}" itemValue="codeId" itemLabel="codeNm" />
						</form:select>
					</td>
					<th>처리상태</th>
					<td class="w_205">
						<form:select class="input_text" name="proCde" path="proCde" >
							<form:option value="" label="전체"/>
							<form:options items="${proCde}" itemValue="codeId" itemLabel="codeNm" />
						</form:select>
					</td>
				</tr>
				<tr>
					<th>접수일자</th>
					<td>
						<input type="text" class="w_85 easyui-datebox" name="rcvYmdMin" path="rcvYmdMin" />
						<span class="space">~</span>
						<input type="text" class="w_85 easyui-datebox" name="rcvYmdMax" path="rcvYmdMax" />
					</td>
					<th>민원지<br>법정동</th>
					<td>
						<form:select class="input_text" name="aplBjd" path="aplBjd" >
							<form:option value="" label="전체"/>
							<form:options items="${aplBjd}" itemValue="codeId" itemLabel="codeNm" />
						</form:select>
					</td>
				</tr>
				<tr>
					<th>민원인<br>성명</th>
					<td>
						<input type="text" class="input_text" name="apmNam" path="apmNam" />
					</td>									
					<th>처리자<br>성명</th>
					<td>
						<input type="text" class="input_text" name="proNam" path="proNam" />
					</td>									
				</tr>
			</tbody>
		</table>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="a_reset_roadCivilAppealSearch button_flat_normal btn_blue">초기화</button>
			<button class="a_search_roadCivilAppealSearch button_flat_normal btn_blue">검색</button>
		</div>
	</div>
</div>
