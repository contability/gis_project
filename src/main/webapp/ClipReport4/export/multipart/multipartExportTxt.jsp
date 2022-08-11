<%@page import="com.clipsoft.clipreport.server.service.export.ExportStatusInfo"%>
<%@page import="com.clipsoft.clipreport.export.option.JPGOption"%>
<%@page import="com.clipsoft.clipreport.export.option.PDFOption"%>
<%@page import="com.clipsoft.clipreport.export.option.TextOption"%>
<%@page import="com.clipsoft.clipreport.oof.OOFFile"%>
<%@page import="com.clipsoft.clipreport.oof.OOFDocument"%>
<%@page import="com.clipsoft.clipreport.oof.connection.*"%>
<%@page import="com.clipsoft.clipreport.server.service.ClipReportExport"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileOutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../Property.jsp"%>
<%
out.clear(); // where out is a JspWriter
out = pageContext.pushBody();

OOFDocument oof = OOFDocument.newOOF();
OOFFile file = oof.addFile("crf.root", "%root%/crf/CLIP.crf");
file.addConnectionData("*", "oracle1");
oof.addOption("saveTxtFile", "true");

//리포트 엔진을 실행시킨다. 리포트 템프 파일을 생성한다.
ExportStatusInfo exportStatus = ClipReportExport.EngingeRun(request, propertyPath, oof);
//exportStatus.getErrorType()
//ErrorType == 0 정상적인 출력
//ErrorType == 1 인스톨 오류
//ErrorType == 2 oof 문서 오류
//ErrorType == 3 리포트 엔진 오류
//ErrorType == 10 해당 서버에 Temp폴더에 해당키의 Txt 파일이 존재하지 않을경우
//서버에 PDF파일로 저장 할 때

File localFileSave = new File("c:\\Temp\\" + exportStatus.getKey() + ".txt");
OutputStream fileStream = new FileOutputStream(localFileSave);

TextOption option = new TextOption();
ClipReportExport.exportForTxt(exportStatus, fileStream, option);
fileStream.close();


//리포트 관련 템프 파일 삭제
ClipReportExport.deleteReportFile(exportStatus);
%>