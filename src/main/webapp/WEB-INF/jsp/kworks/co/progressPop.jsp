<%
/**
 * @Class Name   :progressPop.jsp
 * @Description  :로딩 움직이는 gif 이미지 팝업 화면
 * @Modification :개정이력(Modification Information)
 * @
 * @  수정일                   수정자                  수정내용
 * @ ---------    ---------   -------------------------------
 * @ 2015. 05.26  최인호                  최초생성
 *
 * @author 지트GDS 최인호
 * @since 2015. 05.26
 * @version 1.0
 * @see
 *
 *  Copyright (C) by 지트GDS All right reserved.
 */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/kworks/mps/inc/taglib_common.jsp" %>
<%

	//out.println("<script>var proWid = window.window.innerWidth;var proHigh = window.innerHeight; alert('proWid : ' + proWid + ': proHigh : ' + proHigh);</script>");
	
	//String proressPath = (request.isSecure()?"https":"http") + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/images/kworks/common/loading44_ribon.gif";
	
	//out.println("<div id='kowrksComProgressDiv' style='width:100%;height:100%;'>");
	//out.println("	<div style='background-color:white;width:300px;height:230px;margin:0 auto;'>");
	//out.println("		<div><img src='" + proressPath + "'  alt='로딩중' title='로딩중' /></div>");
	//out.println("		<div><p>로딩중입니다.<br/><br/>잠시만 기다려 주세요.</p></div>");
	//out.println("	</div>");
	//out.println("</div>");
	
	//out.flush();
%>
		
<div id="kowrksComProgressDiv" style="background-color: white;width:100%;height:100%;">
	<div><img src='<c:url value='/images/kworks/common/loading44_ribon.gif'/>'  alt="로딩중" title="로딩중" /></div>
	<div><p>로딩중입니다.<br/><br/>잠시만 기다려 주세요.</p></div>
</div>