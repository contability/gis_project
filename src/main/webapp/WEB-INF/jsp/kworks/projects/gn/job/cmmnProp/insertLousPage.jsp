<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div class="window_container">
	<div class="window_article">
		<table class="table_text">
			<colgroup>
				<col width="20%">
				<col width="80%">
			</colgroup>
			<tbody>
				<tr>
					<th>제목</th>
					<td><input id="lous_lonTit"></td>
					
				</tr>
				<tr>
					<th>내용</th>
					<td><input id="lous_rmkExp"></td>
				</tr>
				
			</tbody>
		</table>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button id="btn_insert" class="button_flat_normal btn_blue">저장</button>
			<button id="btn_close" class="button_flat_normal btn_blue">닫기</button>
		</div>
	</div>
</div>