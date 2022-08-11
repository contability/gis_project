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
					<th>지급구분</th>
					<td>
						<c:forEach items="${payCdeList}" var="payCdeList">
							<c:if test="${payCdeList.codeId eq result.payCde}">${payCdeList.codeNm}</c:if>
						</c:forEach>
					</td>
					<th>지급일자</th>
					<td>
						<fmt:parseDate value="${result.payYmd}" var="payYmd" pattern="yyyyMMdd" />
						<fmt:formatDate value="${payYmd}" pattern="yyyy-MM-dd" />
					</td>
				</tr>
				<tr>
					<th>지급금액</th>
					<td colspan="3">
						<fmt:formatNumber value="${result.payAmt}" pattern="#,###.##" />원
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
