package kr.co.gitt.kworks.cmmn.aop;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsConectLog;
import kr.co.gitt.kworks.service.log.ConnectLogService;

import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class ConnectLogAdvice
/// kr.co.gitt.kworks.cmmn.aop \n
///   ㄴ ConnectLogAdvice.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 1:50:29 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 접속 로그 어드바이스 입니다.
///   -# 
/////////////////////////////////////////////
public class ConnectLogAdvice {

	/// 접속 로그 서비스
	@Resource
	ConnectLogService connectLogService;

	/////////////////////////////////////////////
	/// @fn after
	/// @brief 함수 간략한 설명 : 접속 로그 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param joinPoint
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void after(JoinPoint joinPoint) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		
		KwsConectLog kwsConectLog = new KwsConectLog();
		kwsConectLog.setUserId(userDTO.getUserId());
		kwsConectLog.setUserNm(userDTO.getUserNm());
		kwsConectLog.setDeptCode(userDTO.getDeptCode());
		kwsConectLog.setUserIp(request.getRemoteAddr());
		kwsConectLog.setSessionId(session.getId());
		
		if(connectLogService.checkSession(kwsConectLog.getSessionId()) == 0) {
			connectLogService.login(kwsConectLog);
		}
	}
	
}
