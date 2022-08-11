package kr.co.gitt.kworks.co;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ServletContextAware;

import egovframework.rte.ptl.mvc.tags.ui.pagination.AbstractPaginationRenderer;
/**
 * ImagePaginationRenderer.java 클래스
 * 
 * @author 서준식
 * @since 2011. 9. 16.
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2011. 9. 16.   서준식       이미지 경로에 ContextPath추가
 * </pre>
 */
public class ImagePaginationRenderer extends AbstractPaginationRenderer implements ServletContextAware{

	private ServletContext servletContext;
	Logger logger = LoggerFactory.getLogger(getClass());
	
	public ImagePaginationRenderer() {
	}
	
	public void initVariables(){
		String strWebDir    = servletContext.getContextPath() + "/images/kworks/common";
		firstPageLabel    = "<a href=\"#\" onclick=\"{0}({1});return false; \"><img src=\"" + strWebDir +  "/btn_prev02.gif\" alt=\"처음\" style=\"width:34px;height:34px\" border=\"0\"/></a>&#160;";
        previousPageLabel = "<a href=\"#\" onclick=\"{0}({1});return false; \"><img src=\"" + strWebDir +  "/btn_prev01.gif\" alt=\"이전\" style=\"width:34px;height:34px\" border=\"0\"/></a>&#160;";
        currentPageLabel  = "<strong>{0}</strong>&#160;";
        otherPageLabel    = "<a href=\"#\" onclick=\"{0}({1});return false; \">{2}</a>&#160;";
        nextPageLabel     = "<a href=\"#\" onclick=\"{0}({1});return false; \"><img src=\"" + strWebDir +  "/btn_next01.gif\" alt=\"다음\"  style=\"width:34px;height:34px\" border=\"0\"/></a>&#160;";
        lastPageLabel     = "<a href=\"#\" onclick=\"{0}({1});return false; \"><img src=\"" + strWebDir +  "/btn_next02.gif\" alt=\"마지막\" style=\"width:34px;height:34px\" border=\"0\"/></a>&#160;";
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		initVariables();
	}

}
