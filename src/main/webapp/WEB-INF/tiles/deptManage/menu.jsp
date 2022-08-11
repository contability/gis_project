<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>

<script type="text/javascript" src="<c:url value='/js/kworks/manage/leftMenu.js' />"></script>
<div class="nav f_l bd_l_d6 bd_r_d6 bd_b_d6">
	<input type="hidden" id="menu_request_uri" value='${requestScope["javax.servlet.forward.request_uri"]}' />
	<div class="pa_b_10"><img src="<c:url value='/images/kworks/system/left_image.png' />" /></div>
	<ul id="ul_menu_list">
		<li class="pa_b_2"><a href="<c:url value='/deptManage/export/list.do?progrsSttus=CONSENT_WAITING'/>"><span class="leftMenu">내보내기 관리</span></a></li>
		<li class="pa_b_2"><a href="<c:url value='/deptManage/exportHist/list.do'/>"><span class="leftMenu">내보내기 이력조회</span></a></li>
	</ul>
</div>