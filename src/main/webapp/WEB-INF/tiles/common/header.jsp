<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp"%>
<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>
<div class="header_wrap" id="header">
	<div class="w_1200 m_auto">
		<div class="logo pa_t_9 pa_l_10">
			<a href="<c:url value='/'/>"><img alt="<spring:message code='Com.City' /> 웹표준 공간정보통합관리 체계" src="<c:url value='/images/${prjCode}/common/logo.png' />" /></a>
		</div>
		<div class="gnb ma_t_18">
			<div class="gnb_text color_3 ma_t_6 f_s_14"><span><strong><c:out value='${userName}' /></strong> 님 환영합니다.</span></div>
			<div class="gnb_line color_3 ma_t_4 ma_l_6 ma_r_6 f_s_18"><span><strong>|</strong></span></div>
			<div class="gnb_text color_3"><a href="<c:url value='/j_spring_security_logout' />" class="btn_logout"></a></div>
		</div>
	</div>
</div>