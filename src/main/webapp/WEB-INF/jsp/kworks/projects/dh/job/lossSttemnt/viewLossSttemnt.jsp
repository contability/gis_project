<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" 			uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form" %>

<div class="layerCont">
	<table class="cmmTable v2" summary="기준점망실신고 세부내용 관련 테이블">
		<caption>기준점망실신고 조회 테이블</caption>
		<colgroup>
			<col width="20%" />
			<col width="*" />
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">신고제목</th>
				<td>${result.lptDes}</td>
			</tr>
			<tr>
				<th scope="row">신고내용</th>
				<td>${result.lpcExp}</td>
			</tr>
			<tr>
				<th scope="row">비고<br>(불가사유)</th>
				<td>${result.lpaExp}</td>
			</tr>
		</tbody>
	</table>
</div>
