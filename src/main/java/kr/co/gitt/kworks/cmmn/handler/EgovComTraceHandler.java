package kr.co.gitt.kworks.cmmn.handler;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.rte.fdl.cmmn.trace.handler.TraceHandler;


/**
 * @Class Name : EgovComTraceHandler.java
 * @Description : 공통서비스의 trace 처리 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2011. 09. 30.     JJY
 *
 * @author JJY
 * @since 2011. 9. 30.
 *
 */
public class EgovComTraceHandler implements TraceHandler {

	Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 발생된 메시지를 출력한다.
     */
    public void todo(Class<?> clazz, String message) {
    	logger.debug("[TRACE]CLASS::: "+ clazz.getName());
    	logger.debug("[TRACE]MESSAGE::: "+ message);
    	//이곳에서 후속처리로 필요한 액션을 취할 수 있다.
    }
}
