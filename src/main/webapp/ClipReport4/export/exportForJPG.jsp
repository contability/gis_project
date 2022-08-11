<%@page import="com.clipsoft.clipreport.export.option.JPGOption"%>
<%@page import="com.clipsoft.clipreport.oof.OOFFile"%>
<%@page import="com.clipsoft.clipreport.oof.OOFDocument"%>
<%@page import="com.clipsoft.clipreport.server.service.ClipReportExport"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileOutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../Property.jsp"%>
<%

out.clear(); // where out is a JspWriter
out = pageContext.pushBody();


OOFDocument oof = OOFDocument.newOOF();
OOFFile file = oof.addFile("crf.root", "%root%/crf/CLIP.crf");

//지정한 폴더에 (파일명 + 페이지번호) 저장합니다.
//지정한 폴더에 report1.jpg 로 저장됩니다.
//리포트가 1~3페이지 (여러 페이지)일 경우 report1.jpg, report2.jpg, report3.jpg 저장 됩니다.
String fileName = "c:\\temp1\\test\\report.jpg";

JPGOption jpgOption = new JPGOption();
//저장파일 
jpgOption.setOutputFilename(fileName);
//X DPI 설정
jpgOption.setDpiX(96);
//Y DPI 설정
jpgOption.setDpiY(96);
//품질(1~100)
jpgOption.setQuality(100);
//회전
jpgOption.setRotate90(false);

int statusType = ClipReportExport.createExportForJPG(request, propertyPath, oof, jpgOption);
//statusType == 0 정상적인 출력
//statusType == 1 인스톨 오류
//statusType == 2 oof 문서 오류
//statusType == 3 리포트 엔진 오류
//statusType == 4 img 출력 오류
%>