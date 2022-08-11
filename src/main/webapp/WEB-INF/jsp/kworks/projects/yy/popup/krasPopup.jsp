<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<meta http-equiv="X-UA-Compatible" content="IE=11" />
	<meta http-equiv="X-UA-Compatible" content="IE=10" />
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	
	<c:set var="prjCode"><spring:message code='Globals.Prj'/></c:set>
	
	<!-- import css -->
	<!-- kworks common -->
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/reset.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/common.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/layout_map.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/button.css' />" />
	
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/cmmn/common.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/main/main.css' />" />	
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/map/stateMap/stateMap.css' />" />
	
	<!-- easyui -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/lib/jquery/easyui/themes/icon.css' />" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/lib/jquery/easyui/themes/bootstrap/easyui.css' />" />
			
	<!-- kworks -->
	<link rel="stylesheet" href="<c:url value='/lib/kworks/css/kworks.css' />" type="text/css">
	
	<!-- window -->
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/window/window.css' />" />
	
	<!-- import javascript -->
	<script type="text/javascript">
		// 컨텍스트 패스
		var CONTEXT_PATH = opener.CONTEXT_PATH;
		var CONTACT = opener.CONTACT;
		
		var PNU = '${pnu}';
		var pnuObj = opener.pnuObj;
	</script>
	
	<!-- jquery -->
	<script src="<c:url value='/webjars/jquery/2.1.4/jquery.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/lib/jquery/cookie/jquery.cookie.js' />" type="text/javascript"></script>
	<script src="<c:url value='/lib/jquery/form/jquery.form.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/lib/jquery/easyui/jquery.easyui.min.js' />" type="text/javascript"></script>
	
	<!-- kworks -->
	<script src="<c:url value='/js/kworks/cmmn/utils.js' />" type="text/javascript"></script>
	
	<!-- business -->
	<script src="<c:url value='/js/${prjCode}/window/kras.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/${prjCode}/popup/krasPopup.js' />" type="text/javascript"></script>
	
	<title>토지/건물 정보 조회</title>
</head>
<body>
	<div class="panel panel-body">
		<jsp:include page="/WEB-INF/jsp/kworks/projects/${prjCode}/window/kras.jsp" flush="true" />
	</div>
</body>
</html>