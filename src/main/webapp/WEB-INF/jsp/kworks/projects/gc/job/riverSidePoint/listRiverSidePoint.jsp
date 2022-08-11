<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div id="SearchRiverSidePoint" class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>하천명</th>
					<td>
						<form:select class="input_text_w w_160" name="rivNam" path="rivNam" >
							<form:option value="" label="전체"/>
							<form:options items="${rivNam}" itemValue="codeId" itemLabel="codeNm" />
						</form:select>
					</td>
					<th>하천번호</th>
					<td>
						<input type="text" class="input_text_w w_160" name="filNam" path="filNam" />
					</td>			
				</tr>
			</tbody>
		</table>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="a_reset_riverSidePointSearch button_flat_normal btn_blue">초기화</button>
			<button class="a_search_riverSidePointSearch button_flat_normal btn_blue">검색</button>
		</div>
	</div>
</div>
