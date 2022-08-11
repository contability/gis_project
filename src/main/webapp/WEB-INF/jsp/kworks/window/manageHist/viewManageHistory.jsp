<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>

<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>

<table class="cmmTable v2 ma_b_10" summary="관리이력 상세정보">
	<caption>관리이력 상세정보</caption>
	<colgroup>
		<col width="30%">
		<col width="*">
	</colgroup>
	<tbody id="tbl_view_manage_hist">
	
	</tbody>
</table>

<p class="div_tool_manageHist">
	<!-- 	강릉, 과천, 동해, 속초 공사대장 조회 가능하도록 수정 -->
	<c:if test="${ prjCode eq 'gn' or prjCode eq 'gc' or prjCode eq 'dh' or prjCode eq 'sc'}">
		<a href="#" class="a_view_cntrct_regstr btn_cons_ma"></a>
	</c:if>
	<a href="#" class="a_edit_view_hist btn_edit"></a>
	<a href="#" class="a_close_view_hist btn_close"></a>
</p>