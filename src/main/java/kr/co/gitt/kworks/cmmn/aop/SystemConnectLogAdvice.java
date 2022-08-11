package kr.co.gitt.kworks.cmmn.aop;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsSys;
import kr.co.gitt.kworks.model.KwsSysConectLog;
import kr.co.gitt.kworks.service.log.SysConectLogService;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class SystemConnectLogAdvice
/// kr.co.gitt.kworks.cmmn.aop \n
///   ㄴ SystemConnectLogAdvice.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 1:51:56 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 시습템 접속 로그 어드바이스 입니다.
///   -# 
/////////////////////////////////////////////
public class SystemConnectLogAdvice {
	
	/// 시스템 접속 로그 서비스
	@Resource
	SysConectLogService sysConectLogService;
	
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());

	/////////////////////////////////////////////
	/// @fn after
	/// @brief 함수 간략한 설명 : 시스템 접속 로그 등록
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
		Model model = null;
		for(Object obj : joinPoint.getArgs()) {
			if(obj instanceof ModelMap) {
				model = (Model) obj;
			}
		}
		if(model != null) {
			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
			KwsSys kwsSys = (KwsSys) model.asMap().get("sys");
			if(kwsSys == null) {
				logger.warn("KWS_SYS IS NULL");
			}
			else {
				Long sysId = kwsSys.getSysId();
				KwsSysConectLog kwsSysConectLog = new KwsSysConectLog();
				kwsSysConectLog.setUserId(userDTO.getUserId());
				kwsSysConectLog.setUserNm(userDTO.getUserNm());
				kwsSysConectLog.setDeptCode(userDTO.getDeptCode());
				kwsSysConectLog.setDeptNm(userDTO.getDeptNm());
				kwsSysConectLog.setSysId(sysId);
				sysConectLogService.insertSystemConnectLog(kwsSysConectLog);
			}
		}
	}
	
}
