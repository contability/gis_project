<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<div class="window_container">
	<div class="window_article">
		<table class="table_text">
			<tbody>
				<tr>
					<th>상호명</th>
					<td>
						${result.subNam}
					</td>
					<th>대표자</th>
					<td>
						${result.psbNam}
					</td>
				</tr>
				<tr>
					<th>주소</th>
					<td colspan="3">
						${result.subAdr}
					</td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td colspan="3">
						${result.subTel}
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
