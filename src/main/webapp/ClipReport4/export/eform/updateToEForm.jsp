<%@page import="com.clipsoft.clipreport.server.service.eform.ClipEFormUpdate"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileOutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
out.clear(); // where out is a JspWriter
out = pageContext.pushBody();

//getEFormSaveData()함수를 통하여 나온 데이터를 서버에 파라미터로 받아서 서버에 저장된 리포트폼에 반영하는 예제입니다.
//기본적으로 데이터가 base64로 인코딩 되어 있습니다.
String eform_data = request.getParameter("eform_data");

if(null != eform_data){
	int errorCode = ClipEFormUpdate.update(request, eform_data);
	//errorCode == 0 정상
	//errorCode == 1 eform_data Base64 Decode 실패 
	//errorCode == 2 리포트폼를 찾지 못할 때
	//errorCode == 3 서버에 EForm서버가 설치가 안되어 있을 때 오류
	//errorCode == 4 업데이트 실패
}
%>