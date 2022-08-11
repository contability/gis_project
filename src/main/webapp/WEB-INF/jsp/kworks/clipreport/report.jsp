<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Report</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="<c:url value='/ClipReport4/css/clipreport.css' />">
<script type='text/javascript' src="<c:url value='/ClipReport4/js/clipreport.js' />"></script>
<script type='text/javascript' src="<c:url value='/ClipReport4/js/jquery-1.11.1.js' />"></script>
<script type='text/javascript' src="<c:url value='/ClipReport4/js/UserConfig.js' />"></script>
<script type='text/javascript'>
var clipPath = "<c:url value='/ClipReport4/Clip.jsp' />";
$(document).ready(function() {
	var divPath = "div_target";
    var reportkey = decodeURIComponent("<c:out value='${reportkey}' />");
	var report = createImportJSPReport(clipPath, reportkey, document.getElementById(divPath));
    //실행
    report.setSlidePage(true);
    report.view();
});
</script>
</head>
<body onload="html2xml('targetDiv1')">
<div id='div_target' style='position:absolute;top:50px;left:0px;height:1500px;width:100%;'></div>
</body>
</html>
