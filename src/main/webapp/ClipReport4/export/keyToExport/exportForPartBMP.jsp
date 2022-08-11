<%@page import="com.clipsoft.clipreport.export.option.BMPOption"%>
<%@page import="com.clipsoft.clipreport.export.option.JPGOption"%>
<%@page import="com.clipsoft.clipreport.server.service.ExportInfo"%>
<%@page import="com.clipsoft.clipreport.server.service.ClipReportExport"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileOutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
out.clear(); // where out is a JspWriter
out = pageContext.pushBody();

//리포트 키를 받아서 처리 합니다. (report_key 파라미터 이름은 변경하여도 상관 없습니다)
String report_key = request.getParameter("report_key");

if(null != report_key){
	//지정한 폴더에 (파일명 + 페이지번호) 저장합니다.
	//지정한 폴더에 report1.bmp 로 저장됩니다.
	//리포트가 1~3페이지 (여러 페이지)일 경우 report1.bmp, report2.bmp, report3.bmp 저장 됩니다.
	String fileName = "c:\\temp1\\test\\report.bmp";

	BMPOption bmpOption = new BMPOption();
	//저장파일 
	bmpOption.setOutputFilename(fileName);
	//X DPI 설정
	bmpOption.setDpiX(96);
	//Y DPI 설정
	bmpOption.setDpiY(96);
	//회전
	bmpOption.setRotate90(false);
	//흑백설정
	bmpOption.setBlackWhite(true);
	
	
	ExportInfo exportInfo = ClipReportExport.createExportForPartBMP(request, report_key, bmpOption);
	int errorCode = exportInfo.getErrorCode();
	//errorCode == 0 정상
	//errorCode == 1 세션안에 리포트정보가 없을 때 오류 
	//errorCode == 2 리포트 서버가 설치가 안되어 있을 때 오류 
	//errorCode == 3 결과물(document) 파일을 찾지 못할 때 발생하는 오류
	//errorCode == 4 jpg 파일 생성 오류
	
	//리포트 총 페이지 수
	//System.out.println(exportInfo.getPageCount());
}
%>