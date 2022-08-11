package kr.co.gitt.kworks.service.cmmn;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.KwsErrorMapper;
import kr.co.gitt.kworks.model.KwsError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/////////////////////////////////////////////
/// @class ErrorServiceImpl
/// kr.co.gitt.kworks.service.cmmn \n
///   ㄴ ErrorServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 9. 오후 4:58:24 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 에러 서비스 구현 입니다.
///   -# 
/////////////////////////////////////////////
@Service("errorService")
public class ErrorServiceImpl implements ErrorService {
	
	/// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 에러 ID 생성 서비스
	@Resource
	EgovIdGnrService kwsErrorIdGnrService;

	/// 에러 매퍼
	@Resource
	KwsErrorMapper kwsErrorMapper;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.cmmn.ErrorService#addError(java.lang.Exception)
	/////////////////////////////////////////////
	@Override
	public Integer addError(Exception exception) {
		KwsError kwsError = new KwsError();
		try {
			kwsError.setErrorNo(kwsErrorIdGnrService.getNextLongId());
			kwsError.setErrorMssage(exception.getMessage());
			StringBuffer errorTrace = new StringBuffer();
			for(StackTraceElement element : exception.getStackTrace()) {
				errorTrace.append(element.toString()).append(System.getProperty("line.separator"));
			}
			kwsError.setErrorTrace(errorTrace.toString());
		} catch (FdlException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
		return kwsErrorMapper.insert(kwsError);
	}

}
