<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui"			 uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt"			 uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		 uri="http://www.springframework.org/tags/form" %>

<div class="window_container">
	<div class="window_article">
		<input type="hidden" id="cntIdn" value="${result.cntIdn}" />
		<table class="table_text">
			<tbody>
					<tr>
						<th>공사번호</th>
						<td colspan="2">${result.cntIdn}</td>
					</tr>
					<tr>
						<th>공사명</th>
						<td colspan="2">${result.cntNam}</td>
					</tr>
			</tbody>
		</table>
		<div class="tabs-1">
			<ul class="tab-list-1">
				<li class="active"><a class="tab-control" href="#list_tab1">일반사항</a></li>
				<li><a class="tab-control" href="#list_tab2">사용증명서</a></li>
			</ul>
		</div>
		<div class="tab-panel-1 active" id="list_tab1" style="height:212px">
			<table class="table_text">			
				<tbody>
					<tr>
						<th>공사부서</th>
						<td colspan="5">
						<c:forEach items="${cntDeptList}" var="cntDeptList">
								<c:if test="${cntDeptList.deptNm eq result.cntDept}">${cntDeptList.deptNm}</c:if>
						</c:forEach>
						</td>
					</tr>
					<tr>
						<th>도급자</th>
						<td colspan="2">
							${result.gcnNam}
						</td>
						<th>대표자 성명</th>
						<td colspan="2">
							${result.pocNam}
						</td>											
					</tr>
					<tr>
						<th>계약금액</th>
						<td colspan="5">
							<fmt:formatNumber value="${result.totAmt}" pattern="#,###.##" />원
						</td>
					</tr>
					<tr>
						<th>계약일</th>
						<td colspan="2">
							<fmt:parseDate value="${result.cttYmd}" var="cttYmd" pattern="yyyyMMdd" />
							<fmt:formatDate value="${cttYmd}" pattern="yyyy-MM-dd" />
						</td>
						<th>착공일</th>
						<td colspan="2">
							<fmt:parseDate value="${result.begYmd}" var="begYmd" pattern="yyyyMMdd" />
							<fmt:formatDate value="${begYmd}" pattern="yyyy-MM-dd" />
						</td>
					</tr>
					<tr>
						<th>준공기한</th>
						<td colspan="2">
							<fmt:parseDate value="${result.fnsYms}" var="fnsYms" pattern="yyyyMMdd" />
							<fmt:formatDate value="${fnsYms}" pattern="yyyy-MM-dd" />
						</td>
						<th>준공일</th>
						<td colspan="2">
							<fmt:parseDate value="${result.rfnYmd}" var="rfnYmd" pattern="yyyyMMdd" />
							<fmt:formatDate value="${rfnYmd}" pattern="yyyy-MM-dd" />
						</td>
					</tr>
					<tr>
						<th>준공검사</th>
						<td colspan="5">
							<fmt:parseDate value="${result.fchYmd}" var="fchYmd" pattern="yyyyMMdd" />
							<fmt:formatDate value="${fchYmd}" pattern="yyyy-MM-dd" />
						</td>
					</tr>
					<tr>
						<th>비고</th>
						<td colspan="5">
							${result.remark}
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="tab-panel-1" id="list_tab2" style="height:212px;">
			<table class="table_text tableSelector">
				<tbody>
					<tr>
						<td>번호</td>
						<td>위치</td>
						<td>전체면적</td>
						<td>편입면적</td>
						<td>소유자</td>
						<td>생년월일</td>
					</tr>
					<c:forEach var="useResultObj" items="${useResult}">
						<tr data-lui-idn="${useResultObj.luiIdn}">
							<td data-lui-idn="${useResultObj.luiIdn}">${useResultObj.luiIdn}</td>
							<td>${useResultObj.pnu}</td>
							<td>${useResultObj.totArea}</td>
							<td>${useResultObj.incArea}</td>
							<td>${useResultObj.ownNam}</td>
							<td>${useResultObj.ownYmd}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="window_footer">
		<div class="button_flat">
			<button class="btn_modify_landCenterCntrwkRegstrDetail button_flat_normal btn_blue">편집</button>
			<button class="btn_delete_landCenterCntrwkRegstrDetail button_flat_normal btn_blue">삭제</button>
			<button class="btn_close_landCenterCntrwkRegstrDetail button_flat_normal btn_blue">닫기</button>
		</div>
	</div>
</div>