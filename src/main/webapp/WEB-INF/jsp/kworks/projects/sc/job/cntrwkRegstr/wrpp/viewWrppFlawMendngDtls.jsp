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
					<th>하자발생일</th>
					<td>
						<fmt:parseDate value="${result.flaYmd}" var="flaYmd" pattern="yyyyMMdd" />
						<fmt:formatDate value="${flaYmd}" pattern="yyyy-MM-dd" />
					</td>
					<th>하자보수일</th>
					<td>
						<fmt:parseDate value="${result.rprYmd}" var="rprYmd" pattern="yyyyMMdd" />
						<fmt:formatDate value="${rprYmd}" pattern="yyyy-MM-dd" />
					</td>
				</tr>
				<tr class="h_100">
					<th>하자보수<br>내용</th>
					<td colspan="3">${result.rprDes}</td>				
				</tr>
			</tbody>
		</table>
	</div>
</div>
