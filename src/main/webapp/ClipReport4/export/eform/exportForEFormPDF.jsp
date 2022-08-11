<%@page import="com.clipsoft.clipreport.server.service.ClipEFormExport"%>
<%@page import="com.clipsoft.clipreport.server.service.ExportInfo"%>
<%@page import="com.clipsoft.clipreport.export.option.PDFOption"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileOutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
out.clear(); // where out is a JspWriter
out = pageContext.pushBody();

//getEFormSaveData()함수를 통하여 나온 데이터를 서버에 파라미터로 받아서 export하는 예제입니다.
//기본적으로 데이터가 base64로 인코딩 되어 있습니다.
String eform_data = request.getParameter("eform_data");

if(null != eform_data){
	//서버에 파일로 저장 할 때
	File localFileSave = new File("c:\\temp1\\test\\report.pdf");
	OutputStream fileStream = new FileOutputStream(localFileSave);
	
	//클라이언트로 파일을 내릴 때
	//String fileName = "report.pdf";
	//response.setContentType("application/octet-stream; charset=UTF-8");
	//response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
	//OutputStream fileStream = response.getOutputStream();
	
	//클라이언트 브라우져에서 바로 보는 방법(헤더 변경)
	//response.setContentType("application/pdf");
	//OutputStream fileStream = response.getOutputStream();
	
	PDFOption option = new PDFOption();
	/*
	option = new PDFOption();
	option.setUserPassword("사용자(읽기) 암호");
	option.setOwnerPassword("저자(쓰기) 암호");
	option.setTextToImage(true); // 글자를 이미지로 처리 - unicode 처리시 사용
	option.setNumCopies(1); // 프린팅 매수 미리 설정
	option.setImportOriginImage(true); // 원본 이미지 삽입
	*/
	ExportInfo exportInfo = ClipEFormExport.createExportForPDF(request, eform_data, fileStream, option);
	int errorCode = exportInfo.getErrorCode();
	//errorCode == 0 정상
	//errorCode == 1 세션안에 리포트정보가 없을 때 오류 
	//errorCode == 2 리포트 서버가 설치가 안되어 있을 때 오류 
	//errorCode == 3 결과물(document) 파일을 찾지 못할 때 발생하는 오류
	//errorCode == 4 PDF 파일 생성 오류
	//errorCode == 5 eform_data 데이터가 문제가 있을 경우
}
%>