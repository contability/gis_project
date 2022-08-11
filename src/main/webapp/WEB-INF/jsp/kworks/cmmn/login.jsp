<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=11" />
	<meta http-equiv="X-UA-Compatible" content="IE=10" />
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">

	<!-- project -->
	<c:set var="prjCode"><spring:message code='Globals.Prj' /></c:set>
	<c:set var="envType"><spring:message code='Globals.EnvType' /></c:set>
	
	<!-- import css -->
	<link type="text/css" rel="stylesheet" href="<c:url value='/webjars/jquery-ui/1.12.0/jquery-ui.min.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/reset.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/common.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/layout_main.css' />" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/kworks/common/button.css' />" />

	<!-- import javascript -->
	<!-- jquery -->
	<script type="text/javascript" src="<c:url value='/webjars/jquery/2.1.4/jquery.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/js/kworks/cmmn/login.js' />" ></script>
	
	<!-- import javascript -->
	<script type="text/javascript">
		// 컨텍스트 패스
		var CONTEXT_PATH = "${pageContext.request.contextPath}";
	</script>
	
	<title><spring:message code='Com.City' /> 웹표준 공간정보통합관리 체계</title>
</head>
	<body>
		<input type="hidden" class="hid_message_code" value="" />
		<div style="background-color:#ffffff;width:100%;padding-top:140px;">
			<p class="t_a_c">
			<img style="padding:0px;margin:0px;" src="<c:url value='/images/${prjCode}/login/loginMainImg.png' />" />
			</p>
		</div>
		<div style="background-image:url(<c:url value='/images/kworks/login/loginLine.png' />);background-repeat:repeat-x;width:100%;height:4px;padding:0px;margin:0px;"></div>
		<div style="width:100%;margin-top:100px;">
			<div style="width:340px;margin:0 auto; padding:0 auto;">
			<div style="float:left;background-image:url(<c:url value='/images/kworks/login/loginFormLeft.png' />);width:15px;height:117px;padding:0px;margin:0px;"></div>
			<div style="float:left;background-image:url(<c:url value='/images/kworks/login/loginFormMiddle.png' />);background-repeat:repeat-x;width:310px;height:117px;padding:0px;margin:0px;">
				<form id="frm_login" action="<c:url value='/j_spring_security_check'/>" method="POST" >
					<div style="float:left;margin-left:15px;margin-top:23px;">
						<ul>
							<li><span><img src="<c:url value='/images/kworks/login/loginUserNm.png' />" /></span><input type="text" style="height:20px;vertical-align:top;margin-left:10px;margin-top:3px;ime-mode:inactive;" id="j_username" name="j_username" value="" /></li>
							<li><span><img src="<c:url value='/images/kworks/login/loginPw.png' />" /></span><input type="password" style="height:20px;vertical-align:top;margin-left:10px;margin-top:3px;" id="j_password" name="j_password" value="" /></li>
						</ul>
					</div>
					<div style="float:left;margin-left:10px;margin-top:23px;">
						<ul>
							<li><a href="#" id="a_submit_login" ><img src="<c:url value='/images/kworks/login/loginBtn.png' />"></a></li>
						</ul>
					</div>
				</form>
			</div>
			<div style="float:left;background-image:url(<c:url value='/images/kworks/login/loginFormRight.png' />);width:15px;height:117px;padding:0px;margin:0px;"></div>
			</div>
		</div>
	</body>
</html>
