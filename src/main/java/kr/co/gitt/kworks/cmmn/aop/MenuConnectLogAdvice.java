package kr.co.gitt.kworks.cmmn.aop;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsMenu;
import kr.co.gitt.kworks.model.KwsMenuConectLog;
import kr.co.gitt.kworks.service.log.MenuConnectLogService;

import org.aspectj.lang.JoinPoint;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class MenuConnectLogAdvice
/// kr.co.gitt.kworks.cmmn.aop \n
///   ㄴ MenuConnectLogAdvice.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 1:51:03 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 메뉴 로그 어드바이스 입니다.
///   -# 
/////////////////////////////////////////////
public class MenuConnectLogAdvice {
	
	/// 메뉴 접속 로그 서비스
	@Resource
	MenuConnectLogService menuConnectLogService;

	/////////////////////////////////////////////
	/// @fn after
	/// @brief 함수 간략한 설명 : 메뉴 접속 로그 등록
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
		KwsMenu kwsMenu = null;
		for(Object obj : joinPoint.getArgs()) {
			if(obj instanceof KwsMenu) {
				kwsMenu = (KwsMenu) obj;
			}
		}
		if(kwsMenu != null && kwsMenu.getMenuId() != null) {
			KwsMenuConectLog kwsMenuConectLog = new KwsMenuConectLog();
			kwsMenuConectLog.setUserId(userDTO.getUserId());
			kwsMenuConectLog.setUserNm(userDTO.getUserNm());
			kwsMenuConectLog.setDeptNm(userDTO.getDeptNm());
			kwsMenuConectLog.setDeptCode(userDTO.getDeptCode());
			kwsMenuConectLog.setMenuId(kwsMenu.getMenuId());
			kwsMenuConectLog.setMenuNm(kwsMenu.getMenuNm());
			menuConnectLogService.insert(kwsMenuConectLog);
		}
	}
	
}
