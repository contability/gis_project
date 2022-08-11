package kr.co.gitt.kworks.co.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


/**  
 * @Class Name  : KworksCoCorsFilter.java
 * @Description : KworksCoCorsFilter
 * @Modification Information  
 * @
 * @ 수정일      수정자      수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2015.03.03  gitt     
 * 
 * @author gitt
 * @since 2015.03.03
 * @version 1.0
 */
public class KworksCoCorsFilter implements Filter {

    public void init(FilterConfig arg0) throws ServletException {}
    
    public void destroy() {}

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "http://" + req.getServerName());
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		chain.doFilter(req, res);
    }

}