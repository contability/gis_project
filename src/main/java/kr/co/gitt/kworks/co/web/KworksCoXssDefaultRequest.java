package kr.co.gitt.kworks.co.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * KworksCoXssDefaultRequest
 * @author 최인호
 * @since 2015.03.03
 * == 개정이력(Modification Information) ==
 *
 * 수정일                		수정자 	 			수정내용
 * ------------		---------------		---------------------------
 * 2015.03.03                 최인호	           	최초 생성
 *  Copyright (C) by gitt. All right reserved.
 */
public class KworksCoXssDefaultRequest extends HttpServletRequestWrapper {
    
    Logger logger = LoggerFactory.getLogger(getClass());
    public KworksCoXssDefaultRequest(HttpServletRequest request) {
        super(request);
    }

    public String getParameter(String key) {
        String value = null;
        try {
            value = super.getParameter(key);
            if (value != null) {
                //value = xssReplace(key);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return value;
    }
 
    @SuppressWarnings("unused")
	public String[] getParameterValues(String key) {
    
    	String[] values =super.getParameterValues(key);
        
        int count = values.length;   
        try {
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    //values[i] = xssReplace(key);     
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return values;
    }
    
    public String getHeader(String name) {   
        
    	String value = super.getHeader(name);   
        if (value == null){   
            return null;   
        }
        
        return value;
        //return xssReplace(value);
    }  
    
    /*
    public String[] getParameterValues(String parameter) {   
    	 
    	logger.debug("getParameterValues:::::::::::::::::::::::");
    	
        String[] values = super.getParameterValues(parameter);   
        if (values==null)  {   
                    return null;   
            }   
        int count = values.length;   
        logger.debug("count:::::::::::::::::::::::");
        
        String[] encodedValues = new String[count];   
        for (int i = 0; i < count; i++) {   
                   encodedValues[i] = xssReplace(values[i]);   
         }     
        return encodedValues;    
      }   
    */
    
    @SuppressWarnings("unused")
	private String xssReplace(String key){
        String rslt   = (super.getParameter(key) == null) ? "" : super.getParameter(key);
        if(rslt != null){
        	rslt = rslt.trim();
        }
        if(!key.equals("INIpluginData")&&
                !key.equals("_ETEExt_SEED_")&&
                !key.equals("ozrPath")&&
                //!key.equals("forward")&&
                 rslt!=null &&
                !rslt.equals("")
                )
        {

        	//{"&", "--", "'", "\"", "/", "@", "<", ">", "\\", "$", "%", ";", "|", "+", "..", "SCRIPT", "<IFRAME", "<OBJECT", "<EMBEDED", "<FORM", "JAVASCRIPT"}
        	
        	//rslt = rslt.replaceAll("&","&#38;");
            //rslt = rslt.replaceAll("/","&#47;");
            //rslt = rslt.replaceAll("<","&#60;");
            //rslt = rslt.replaceAll(">","&#62;");
            
            rslt = rslt.replaceAll("<","&lt;");
            rslt = rslt.replaceAll(">","&gt;");
            
            rslt = rslt.replaceAll("--","");
            rslt = rslt.replaceAll("#","");

            //rslt = rslt.replaceAll("\\(", "& #40;");
            //rslt = rslt.replaceAll("\\)", "& #41;");
            rslt = rslt.replaceAll("'", "\'");
            rslt = rslt.replaceAll("eval\\((.*)\\)", "");
            rslt = rslt.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
            //rslt = rslt.replaceAll("script", "");
        }    
        return rslt;
    }
}
