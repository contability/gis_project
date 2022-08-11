<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div id="listMesrSgnalSttusExaminHist" class="layerCont">
	<table id="etUV_main" class="cmmTable v3 ma_b_30 tableSelector" summary="측량표지현황조사 목록 테이블" style="table-layout: fixed; word-wrap: break-word;">
		<caption>측량표지현황조사 목록</caption>
		<colgroup>
			<col width="*" />
			<col width="40%" />
			<col width="40%" />
		</colgroup>
		<tbody>
			<tr>
				<th>번호</th>
				<th>보고대상년도</th>
				<th>기준점표석상태</th>
			</tr>
			<c:forEach var="result" items="${result}">
				<tr data-ftr-cde="${result.ftrCde}" data-ftr-idn="${result.ftrIdn}" data-ftf-cde="${result.ftfCde}" data-ftf-idn="${result.ftfIdn}">
					<td>${result.ftrIdn}</td>
					<td>${result.rptYmy}</td>
					<td>
						<c:forEach items="${cpsCde}" var="cpsCde">
							<c:if test="${cpsCde.codeId eq result.cpsCde}">${cpsCde.codeNm}</c:if>
						</c:forEach>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
</div>

<p class="btnRight">
	<a id="a_add_listMesrSgnalSttusExaminHist" href="#" class="btnBlue">등록</a>
</p>
