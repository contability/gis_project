<%@page import="com.clipsoft.clipreport.oof.OOFFile"%>
<%@page import="com.clipsoft.clipreport.oof.OOFDocument"%>
<%@page import="com.clipsoft.clipreport.oof.connection.*"%>
<%@page import="java.io.File"%>
<%@page import="com.clipsoft.clipreport.server.service.ReportUtil"%>
<%@page import="com.clipsoft.org.apache.commons.lang.StringEscapeUtils"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");       //한글이 깨지지 않도록 UTF-8으로 받음. getParameter위에 써야함!!
	
 	String	param0	= request.getParameter("param0");
	String	param1	= request.getParameter("param1");

	/*
	***** crf file 내에서 사용하는 오라클 함수 *****
	*
	* FN_GET_ADACODE	: [IN] RDL_IDN [OUT] ADA_CODE_NM (도로종류명) (ADA_CDE 에 해당하는 CODE_NM)
	* FN_GET_ROADNAME	: [IN] RDL_IDN [OUT] RDN_NAM (도로명)
	* FN_GET_ROUTNAME	: [IN] RDL_IDN [OUT] RUT_NAM (노선명)
	* FN_GET_ROUTNUMBER	: [IN] RDL_IDN [OUT] RUT_IDN (노선번호)
	*/
	OOFDocument oof = OOFDocument.newOOF();
	
	String path = "%root%/crf/STAT/";
	path += param0;
	path += "/";
	path += param1;
	path += ".crf";
	
	System.out.println(path);
	
	OOFFile file = oof.addFile("crf.root", path);
	oof.addConnectionData("*", "dataconnection1");
%>
<%@include file="Property.jsp"%>
<%
	String resultKey =  ReportUtil.createReport(request, oof.toString(), "false", "false", "localhost", propertyPath);
%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Report</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="./css/clipreport.css">
<link rel="stylesheet" type="text/css" href="./css/UserConfig.css">
<link rel="stylesheet" type="text/css" href="./css/font.css">
<script type='text/javascript' src='./js/jquery-1.11.1.js'></script>
<script type='text/javascript' src='./js/clipreport.js'></script>
<script type='text/javascript' src='./js/UserConfig.js'></script>
<script type='text/javascript'>
var clipPath = "<c:url value='/ClipReport4/Clip.jsp' />";
function html2xml(divPath){	
    var reportkey = "<%=resultKey%>";
	var report = createImportJSPReport(clipPath, reportkey, document.getElementById(divPath));
    //실행
    report.setSlidePage(true);
    report.view();
}
</script>
</head>
<body onload="html2xml('targetDiv1')">
<div id='targetDiv1' style='position:absolute;top:50px;left:0px;height:1500px;width:100%;'></div>
</body>
</html>
