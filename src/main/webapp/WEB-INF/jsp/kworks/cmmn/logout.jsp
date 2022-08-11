<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<c:set var="cityNm"><spring:message code='Com.City' /></c:set>

	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=11" />
	<meta http-equiv="X-UA-Compatible" content="IE=10" />
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
	
	<!-- easyui -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/lib/jquery/easyui/themes/icon.css' />" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/lib/jquery/easyui/themes/bootstrap/easyui.css' />" />
	
	<title>${cityNm} 웹표준 공간정보통합관리 서비스</title>
</head>
<body>
	<input type="hidden" class="hid_message_code" value="<c:out value='${code}' />" />

	<script src="<c:url value='/webjars/jquery/2.1.4/jquery.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/lib/jquery/easyui/jquery.easyui.min.js' />" type="text/javascript"></script>
	
	<!-- kworks common -->
	<script src="<c:url value='/js/kworks/cmmn/utils.js' />" type="text/javascript"></script>
	<script type="text/javascript">
		var CONTEXT_PATH = "${pageContext.request.contextPath}";	
		
		$(function() {
			// 시스템 아이디
			var messageCode = $(".hid_message_code").val();
			var messages = {
				MSG001 : {
					title : "로그아웃",
					message : "로그아웃 되었습니다. 시스템을 종료합니다."
				},
				MSG002 : {
					title : "접근거절",
					message : "접근 권한이 없습니다. 포털페이지로 이동합니다."
				},
				MSG003 : {
					title : "세션만료",
					message : "세션이 만료되었습니다. 시스템을 종료합니다."
				}
			};
			
			var title = messages[messageCode].title;
			var message = messages[messageCode].message;
			$.messager.alert(title, message, null, function() {
				if(messageCode == "MSG002") {
					location.href = CONTEXT_PATH + "/portal.do";
				}
				else {
					if(browserUtils.checkIe()) {
						window.close();
					}
					else {
						location.href = "about:blank";
					}
				}
			});
		});
	</script>
</body>