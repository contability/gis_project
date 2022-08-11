<%
/**
 * @Class Name   :index.jsp
 * @Description  :index페이지
 * @Modification :개정이력(Modification Information)
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2015. 07.10           최초생성
 *
 * @author 지트GDS 최인호
 * @since 2015. 07.10
 * @version 1.0
 * @see
 *
 *  Copyright (C) by 지트GDS All right reserved.
 */
%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%  
String contextPath = request.getContextPath();

response.sendRedirect(contextPath + "/portal.do");
%>
