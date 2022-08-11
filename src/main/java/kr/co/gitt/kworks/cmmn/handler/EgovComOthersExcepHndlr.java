package kr.co.gitt.kworks.cmmn.handler;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.rte.fdl.cmmn.exception.handler.ExceptionHandler;

public class EgovComOthersExcepHndlr implements ExceptionHandler {

	Logger logger = LoggerFactory.getLogger(getClass());
    public void occur(Exception exception, String packageName) {
    	logger.error(packageName, exception);
    }
}
