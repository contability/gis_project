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
					<th>변경일자</th>
					<td>
						<%-- <fmt:parseDate value="${result.chgYmd}" var="chgYmd" pattern="yyyyMMdd" />
						<fmt:formatDate value="${chgYmd}" pattern="yyyy-MM-dd" /> --%>
						${result.chgYmd}
					</td>
					<th>증감금액</th>
					<td>
						<fmt:formatNumber value="${result.incAmt}" pattern="#,###.##" />원
					</td>
				</tr>
				<tr>
					<th>증감관급<br>금액</th>
					<td>
						<fmt:formatNumber value="${result.igvAmt}" pattern="#,###.##" />원
					</td>
					<th>변경공사<br>총액</th>
					<td>
						<fmt:formatNumber value="${result.chgAmt}" pattern="#,###.##" />원
					</td>
				</tr>
				<tr>
					<th>변경사업량</th>
					<td colspan="3">
						${result.chgDes}
					</td>
				</tr>
				<tr>
					<th>변경관급량</th>
					<td colspan="3">
						${result.cgvDes}
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>