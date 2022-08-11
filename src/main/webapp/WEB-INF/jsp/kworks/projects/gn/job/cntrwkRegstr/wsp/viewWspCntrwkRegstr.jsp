<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<div class="window_container">
	<div class="window_article">
		<input type="hidden" id="ftrIdn" value="${result.ftrIdn}" />
		<table class="table_text">
			<tbody>
				<tr>
					<th>공사번호</th>
					<td colspan="3">${result.ftrIdn}</td>
				</tr>
				<tr>
					<th>착공일</th>
					<td>
						<fmt:parseDate value="${result.begYmd}" var="begYmd" pattern="yyyyMMdd" />
						<fmt:formatDate value="${begYmd}" pattern="yyyy-MM-dd" />
					</td>
					<th>준공일</th>
					<td>
						<fmt:parseDate value="${result.fnsYmd}" var="fnsYmd" pattern="yyyyMMdd" />
						<fmt:formatDate value="${fnsYmd}" pattern="yyyy-MM-dd" />
					</td>
				</tr>
				<tr>
					<th>수납일</th>
					<td>
						<fmt:parseDate value="${result.rcpYmd}" var="rcpYmd" pattern="yyyyMMdd" />
						<fmt:formatDate value="${rcpYmd}" pattern="yyyy-MM-dd" />
					</td>
					<th>시공자상호</th>
					<td>
						${result.oprNam}
					</td>
				</tr>
				<tr>
					<th>감독자성명</th>
					<td>
						${result.svsNam}
					</td>
					<th>준공검사자성명</th>
					<td>
						${result.fnsNam}
					</td>
				</tr>
				<tr>
					<th>관급자재비</th>
					<td>
						<fmt:formatNumber value="${result.gvrAmt}" pattern="#,###.##" />원
					</td>
					<th>사급자재비</th>
					<td>
						<fmt:formatNumber value="${result.prvAmt}" pattern="#,###.##" />원
					</td>
				</tr>
				<tr>
					<th>부가가치세</th>
					<td>
						<fmt:formatNumber value="${result.taxAmt}" pattern="#,###.##" />원
					</td>
					<th>도로복구비</th>
					<td>
						<fmt:formatNumber value="${result.rorAmt}" pattern="#,###.##" />원
					</td>
				</tr>
				<tr>
					<th>설계수수료</th>
					<td>
						<fmt:formatNumber value="${result.dfeAmt}" pattern="#,###.##" />원
					</td>
					<th>자재검사<br>수수료</th>
					<td>
						<fmt:formatNumber value="${result.gfeAmt}" pattern="#,###.##" />원
					</td>
				</tr>
				<tr>
					<th>준공검사<br>수수료</th>
					<td>
						<fmt:formatNumber value="${result.ffeAmt}" pattern="#,###.##" />원
					</td>
					<th>시설분담금</th>
					<td>
						<fmt:formatNumber value="${result.divAmt}" pattern="#,###.##" />원
					</td>
				</tr>
				<tr>
					<th>기타금액</th>
					<td>
						<fmt:formatNumber value="${result.etcAmt}" pattern="#,###.##" />원
					</td>
					<th>공사비총액</th>
					<td>
						<fmt:formatNumber value="${result.totAmt}" pattern="#,###.##" />원
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="btn_modify_wspCntrwkRegstrDetail button_flat_normal btn_blue">편집</button>
		</div>
	</div>
</div>
