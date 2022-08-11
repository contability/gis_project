package kr.co.gitt.kworks.co;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @Class Name : KworkCoStringUtil.java
 * @Description : KworkCoStringUtil Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2015. 02.26   최초생성
 *
 * @author 지트GDS 최인호
 * @since 2015. 02.26
 * @version 1.0
 * @see
 *
 *  Copyright (C) by 지트GDS All right reserved.
 */
public class KworksCoStringUtil {

	 /**
     * 빈 문자열 <code>""</code>.
     */
    public static final String EMPTY = "";
    
    /**
     * 문자열이 지정한 길이를 초과했을때 지정한길이에다가 해당 문자열을 붙여주는 메서드.
     * @param source 원본 문자열 배열
     * @param output 더할문자열
     * @param slength 지정길이
     * @return 지정길이로 잘라서 더할분자열 합친 문자열
     */
    public static String cutString(String source, String output, int slength) {
        String returnVal = null;
        if (source != null) {
            if (source.length() > slength) {
                returnVal = source.substring(0, slength) + output;
            } else{
                returnVal = source;
            }
        }
        return returnVal;
    }

    /**
     * 문자열이 지정한 길이를 초과했을때 해당 문자열을 삭제하는 메서드
     * @param source 원본 문자열 배열
     * @param slength 지정길이
     * @return 지정길이로 잘라서 더할분자열 합친 문자열
     */
    public static String cutString(String source, int slength) {
        String result = null;
        if (source != null) {
            if (source.length() > slength) {
                result = source.substring(0, slength);
            } else{
                result = source;
            }
        }
        return result;
    }    
    
    /**
     * <p>
     * String이 비었거나("") 혹은 null 인지 검증한다.
     * </p>
     * 
     * <pre>
     *  EfrosStringUtil.isEmpty(null)      = true
     *  EfrosStringUtil.isEmpty("")        = true
     *  EfrosStringUtil.isEmpty(" ")       = false
     *  EfrosStringUtil.isEmpty("bob")     = false
     *  EfrosStringUtil.isEmpty("  bob  ") = false
     * </pre>
     * 
     * @param str - 체크 대상 스트링오브젝트이며 null을 허용함
     * @return <code>true</code> - 입력받은 String 이 빈 문자열 또는 null인 경우 
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * <p>
     * String이 비었거나("") 혹은 null 인지 검증한다.
     * </p>
     * 
     * <pre>
     *  EfrosStringUtil.isNullBlank(null)      = ""
     *  EfrosStringUtil.isNullBlank("")        = ""
     * </pre>
     * 
     * @param str - 체크 대상 스트링오브젝트이며 null을 허용함
     * @return <code>true</code> - 입력받은 String 이 빈 문자열 또는 null인 경우 
     */   
    public static String isNullBlank(String str) {
    	
    	String rstr = "";
    	if(str == null || str.length() == 0 || str.equals("") || str.trim().equals("")){
    		rstr = "";
    	}else{
    		return str;
    	}
        return rstr;
    }
    
    /**
     * 문자 특수문자 제거
     * 
     * @param str		:	파일 이름
     * @return String	:	파일 이름
     * @exception Exception
     * @see
     */  
	 public static String stringReplace(String string){
		 
		 	String str 				= string;
		    String str_imsi   		= "";
		    String[] filter_word = {"","\\.","\\?","\\/","\\~","\\!","\\@","\\#","\\$","\\%","\\^","\\&","\\*","\\(","\\)","\\_","\\+","\\=","\\|","\\\\","\\}","\\]","\\{","\\[","\\\"","\\'","\\:","\\;","\\<","\\,","\\>","\\.","\\?","\\/"};

		    for(int i=0;i<filter_word.length;i++){
		        str_imsi = str.replaceAll(filter_word[i],"");
		        str = str_imsi;
		    }
		    
		    //str = str.replaceAll("[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]", "");    // 특수문자 제거
		    //str = str.replaceAll("\\p{Space}", "");    					  // 공백제거
		    return str;
	}
	
	public static Map<String,String> requestToMap(HttpServletRequest request){
		Logger logger = LoggerFactory.getLogger(KworksCoStringUtil.class);
		Map<String,String> resultMap = new HashMap<String, String>();
		Enumeration params = request.getParameterNames();
		
		while (params.hasMoreElements()){
		    String name = (String)params.nextElement();
		    
		    logger.debug(name + " : " +request.getParameter(name));
		    resultMap.put(name, request.getParameter(name));
		}
		
		return resultMap; 
	}
	
	public static String stringLowerUpperCase(String str){
		String[] strList = str.split("_");
		String resultStr = strList[0].toLowerCase();
		
		for(int i=1; i<strList.length;i++){
			String tmpStr = strList[i].toLowerCase();
			resultStr = resultStr + tmpStr.substring(0, 1).toUpperCase() + tmpStr.substring(1);
		}
		return resultStr;
	}
	
    /**
     * 문자열 비교
     * 
     * @param str
     * @param str2
     * @return String
     * @exception Exception
     * @see
     */    
    public static boolean equals(String str , String str2) {
    	
    	//같으면 true
    	if(str != null && str.equals(str2)){
    		return true;
    	
    	//아니면 false
    	}else{
    		return false;
    	}
    }
    
    /**
     * Xss방지 변환 
     * 
     * @param srcString
     * @return String
     * @exception Exception
     * @see
     */    
    public static String chgXssFilterHtmlStr(String srcString) {
        
    	Logger logger = LoggerFactory.getLogger(KworksCoStringUtil.class);

    	if(srcString == null || srcString.equals("")){
    		return "";
    	}
    			
        String rslt = srcString;
        
        try 
        {
        	rslt = rslt.replaceAll("&amp;"   , "&");
        	rslt = rslt.replaceAll("&lt;"    , "<");
        	rslt = rslt.replaceAll("&gt;"    , ">");
        	rslt = rslt.replaceAll("&nbsp;"  ," ");
        	rslt = rslt.replaceAll("&apos;"  , "\'");
        	rslt = rslt.replaceAll("&quot;"  , "\"");
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
        }
        return  rslt;
    
    }
    
    /**
     * 문자로 된 날짜를 yyyy-mm-dd 포맷의 date로 변환하기
     * @param ymd
     * @return SimpleDateFormat("yyyy-MM-dd")
     * @throws Exception
     */
	 public static Date strToDateFmt(String ymd) throws Exception{
		 SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		 return date.parse(ymd);
	 }
}
