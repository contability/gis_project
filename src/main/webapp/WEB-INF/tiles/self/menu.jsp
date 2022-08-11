<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<c:set var="isMySystem"><spring:message code='Extensions.MySystem.UseAt' /></c:set>
<c:set var="isMyInfoManager"><spring:message code='Extensions.MyInfoManager.UseAt' /></c:set>
	
<script type="text/javascript" src="<c:url value='/js/kworks/manage/leftMenu.js' />"></script>
<div class="nav f_l bd_l_d6 bd_r_d6 bd_b_d6">
	<input type="hidden" id="menu_request_uri" value='${requestScope["javax.servlet.forward.request_uri"]}' />
	<div class="pa_b_10"><img src="<c:url value='/images/kworks/system/left_image.png' />" /></div>
	<ul id="ul_menu_list">
		<li class="pa_b_2"><a href="<c:url value='/self/export/list.do'/>"><span class="leftMenu">내보내기 관리</span></a></li>
		<c:if test="${isMySystem eq true}" >
			<li class="pa_b_2"><a href="<c:url value='/self/selfSys/list.do'/>"><span class="leftMenu">나의 시스템 관리</span></a></li>
		</c:if>
		<li class="pa_b_2"><a href="<c:url value='/self/qna/list.do'/>"><span class="leftMenu">묻고답하기</span></a></li>
		<c:if test="${isMyInfoManager eq true}" >
		<li class="pa_b_2"><a href="<c:url value='/self/userInfo/list.do'/>"><span class="leftMenu">개인정보 관리</span></a></li>
		</c:if>
	</ul>
</div>