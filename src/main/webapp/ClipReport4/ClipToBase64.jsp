<%@page import="com.clipsoft.org.apache.commons.io.output.ByteArrayOutputStream"%>
<%@page import="com.clipsoft.org.apache.commons.codec.binary.Base64"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.URL"%>
<%@page import="java.net.URLConnection"%>
<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.File"%>
<%
out.clear();
out=pageContext.pushBody();

//크로스 도메인 설정
//response.setHeader("Access-Control-Allow-Origin", "*");

String ReportURL = "http://rexpert.clipsoft.kr/ClipReport/Clip.jsp";


String report_param = request.getParameter("report_param");

/*
***********************************************************************
복호화가 들어가야할 코드 (임시적으로 base64인코딩을 예제로 만들었습니다.)
*/
report_param = new String(Base64.decodeBase64(report_param), "UTF-8");
/*
***********************************************************************
*/

ServletOutputStream outputStream = response.getOutputStream();
OutputStreamWriter wr = null;
InputStream inputStream = null;
try{
	URL url = new URL(ReportURL);
	HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	conn.setDoInput(true);
	conn.setDoOutput(true);
	conn.setUseCaches(false);
	conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	if(null != session.getAttribute("ClipRSend")){
		conn.setRequestProperty("Cookie","JSESSIONID=" + session.getAttribute("ClipRSend"));
	}

	wr = new OutputStreamWriter(conn.getOutputStream());
	wr.write(report_param);
	wr.flush();
	
	boolean isText = -1 != conn.getContentType().indexOf("text/html");
	response.setContentType(conn.getContentType());
	String contentDisposition = conn.getHeaderField("Content-Disposition");
	
	if(null != contentDisposition){
		response.setHeader("Content-Disposition", contentDisposition);
	}
	
	Map httpResponseHeaders = conn.getHeaderFields();
	Iterator ite = httpResponseHeaders.keySet().iterator();
	while(ite.hasNext()) {
		String headerFieldKey = (String) ite.next();
		 if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {
			  List keyList = (List) httpResponseHeaders.get(headerFieldKey);
	          Iterator listIter = keyList.iterator();
	          while (listIter.hasNext()) {
	        	 String listValue = (String) listIter.next();
                 String[] fields = listValue.split(";\\s*");
                 String cookieValue = fields[0];
                 if(-1 != cookieValue.indexOf("JSESSIONID")){
                	 String[] cookieValues = cookieValue.split("=");
	             	if(null == session.getAttribute("ClipRSend")){
	             		session.setAttribute("ClipRSend", cookieValues[1]);
	             	}
                 }
                 else{
                	 String[] cookieValues = cookieValue.split("=");
                	 session.setAttribute("ClipRSend", cookieValues[1]);
                 }
	          }
		 }
	}
	inputStream = conn.getInputStream();
	
	if(isText){
		/*
		***********************************************************************
		리포트서버에서 결과값을 받아오는 inputStream을 이용하여 text/html 인 경우에만 적용합니다.
		암호화가 들어가야할 코드 (임시적으로 base64인코딩을 예제로 만들었습니다.)
		*/
		ByteArrayOutputStream bufferOS = new ByteArrayOutputStream();
		byte[] buffer = new byte[4 * 1024];
	    int bytesRead = -1;
	     
	    while ((bytesRead = inputStream.read(buffer)) != -1) {
	    	bufferOS.write(buffer, 0, bytesRead);
	    }
	    bufferOS.flush();
	    outputStream.write(Base64.encodeBase64(bufferOS.toByteArray()));
	    bufferOS.close();
	    /*
		***********************************************************************
		*/
	}
	else{
		byte[] buffer = new byte[4 * 1024];
	    int bytesRead = -1;
	     
	    while ((bytesRead = inputStream.read(buffer)) != -1) {
	    	outputStream.write(buffer, 0, bytesRead);
	    }
	   
	}
	wr.close();
	inputStream.close();
	outputStream.close();
}catch(Exception e){
	e.printStackTrace();
	wr = null;
	inputStream = null;
	outputStream = null;
}
	// Report
%>