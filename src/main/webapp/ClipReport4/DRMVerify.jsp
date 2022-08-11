<%@page import="com.clipsoft.clipreport.server.service.drm.ClipReportDRM"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
//out.clear();
//out=pageContext.pushBody();
//데이터가 파일 업로드로 넘어오는 경우
//ClipReportDRM.verify(request, response);
//데이터가 파라미터 (DRMVerify)명의 base64 String 넘어오는 경우
//ClipReportDRM.verifyBase64(request, response);
//response 사용하지 않고 문자열로 return 받는 경우
String reportSVG = 	ClipReportDRM.verifyBase64String(request);	
%><%=reportSVG%>