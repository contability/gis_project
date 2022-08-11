<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 		uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"	uri="http://www.springframework.org/tags/form" %>

<div id="searchDrngEqpCnfmPrmisn" class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>인허가일자</th>
					<td>
						<input type="text" class="w_85 easyui-datebox" name="pmtYmdMin" path="pmtYmdMin" />
						<span class="space">~</span>
						<input type="text" class="w_85 easyui-datebox" name="pmtYmdMax" path="pmtYmdMax" />
					</td>
					<th>인허가<br>번호</th>
					<td>
						<input type="text" class="input_text" name="pmtNum" path="pmtNum" />
					</td>
				</tr>
				<tr>
					<th>하수처리구분</th>
					<td class="w_205">
						<form:select class="input_text" name="brcCde" path="brcCde" >
							<form:option value="" label="전체"/>
							<form:options items="${brcCde}" itemValue="codeId" itemLabel="codeNm" />
						</form:select>
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
						<input type="text" class="input_text" name="aplNam" path="aplNam" />
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
			<button class="a_reset_DrngEqpCnfmPrmisnSearch button_flat_normal btn_blue">초기화</button>
			<button class="a_search_DrngEqpCnfmPrmisnSearch button_flat_normal btn_blue">검색</button>
		</div>
	</div>
</div>
