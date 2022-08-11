package kr.co.gitt.kworks.co.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**  
 * @Class Name  : KworksCoXssFilter.java
 * @Description : XSS 방지를 위한 Filter
 * @Modification Information  
 * @
 * @ 수정일      수정자      수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2015.03.03  gitt     
 * 
 * @author gitt
 * @since 2015.03.03
 * @version 1.0
 * 
 *  Copyright (C) 2015 by gitt All right reserved.
 */
public class KworksCoXssFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(getClass());
    
    public void destroy() {
        logger.debug("destroy");
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest  request  = (HttpServletRequest)  req;
        HttpServletResponse response = (HttpServletResponse) res;
        request.setCharacterEncoding("utf-8");
        
        String requestURI  = request.getRequestURI();
        	   requestURI  = requestURI.toLowerCase();
        
		/*
		logger.info("requestURI:::::::::::::"+requestURI);
		chain.doFilter(new CmsCoXssChnageRequest(request), response);
		*/
        
        //저장 , 등록 페이지만 체크
        if(requestURI.startsWith("/mm/"))
        {
        	chain.doFilter(request, response);
        }
        else if(   requestURI.contains("insert")
           || requestURI.contains("update")
           || requestURI.contains("modify")
           || requestURI.contains("deleteRequest"))
        {
        	chain.doFilter(new KworksCoXssChnageRequest(request), response);
        }
        else
        {
        	chain.doFilter(request, response);
        }
    }
        
    public void init(FilterConfig arg0) throws ServletException {
        logger.debug("init");
    }

}