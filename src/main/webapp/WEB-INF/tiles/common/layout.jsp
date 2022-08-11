<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" 		uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
<meta http-equiv="X-UA-Compatible" content="IE=11" />
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />

<!-- project -->
<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>
<c:set var="envType"><spring:message code='Globals.EnvType' /></c:set>
	
<!-- BaseUrl 관련 include -->
<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/reset.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/common.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/cmmn/common.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/layout_admin.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/button.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/manage/manage.css' />" />
<link type="text/css" rel="stylesheet" href="<c:url value='/webjars/jquery-ui/1.12.0/jquery-ui.min.css' />" />
	
<script type="text/javascript" src="<c:url value='/webjars/jquery/2.1.4/jquery.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/lib/jquery/form/jquery.form.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/webjars/jquery-ui/1.12.0/jquery-ui.min.js' />"></script>
	
<script type="text/javascript" src="<c:url value='/js/kworks/cmmn/utils.js' />"></script>
<script type="text/javascript">
	var CONTEXT_PATH = "${pageContext.request.contextPath}";
</script>

<title><spring:message code='Com.City' /> 웹표준 공간정보통합관리 체계</title>
</head>

<body>

	<!-- 과천시 재난현장 모바일 파일 다운로드 -->
	<div class="div_loading">
		<img src="<c:url value='/images/kworks/common/file-downloading.gif' />" alt="다운로딩중" title="다운로딩중" />
	</div>

	<!-- wrap -->
	<div id="wrap">
		<tiles:insertAttribute name="header" />
		<div class="container_wrap" id="manage_container">
			<div class="w_1200 m_auto">
				<tiles:insertAttribute name="menu" />
				<tiles:insertAttribute name="content" />
			</div>
		</div>
	</div>
	
</body>
</html>