package kr.co.gitt.kworks.cmmn.aop;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;

import org.aspectj.lang.JoinPoint;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class UserNameAdvice
/// kr.co.gitt.kworks.cmmn.aop \n
///   ㄴ UserNameAdvice.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 21. 오전 8:30:34 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 명 어드바이스 입니다.
///   -# 
/////////////////////////////////////////////
public class UserNameAdvice {

	/////////////////////////////////////////////
	/// @fn after
	/// @brief 함수 간략한 설명 : 사용자 명 추가
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
			if(userDTO != null) {
				model.addAttribute("userName", userDTO.getUserNm());
			}
		}
	}
	
}
