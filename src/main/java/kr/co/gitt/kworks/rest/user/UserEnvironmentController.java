package kr.co.gitt.kworks.rest.user;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsUserEnvrn;
import kr.co.gitt.kworks.service.user.UserEnvironmentService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class UserEnvironmentController
/// kr.co.gitt.kworks.rest.user \n
///   ㄴ UserEnvironmentController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 17. 오후 2:52:21 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 환경 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/userEnvrn/")
public class UserEnvironmentController {

	/// 사용자 환경 서비스
	@Resource
	UserEnvironmentService userEnvironmentService;
	
	/////////////////////////////////////////////
	/// @fn selectOneUserEnvironment
	/// @brief 함수 간략한 설명 : 사용자 환경 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="select.do")
	public String selectOneUserEnvironment(Model model) {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("data", userEnvironmentService.selectOneUserEnvironment(userDTO.getUserId()));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn persistUserEnvironment
	/// @brief 함수 간략한 설명 : 사용자 환경 영속화
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUserEnvrn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="persist.do")
	public String persistUserEnvironment(KwsUserEnvrn kwsUserEnvrn, Model model) {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsUserEnvrn.setUserId(userDTO.getUserId());
		KwsUserEnvrn selectUserEnvrn = userEnvironmentService.selectOneUserEnvironment(kwsUserEnvrn.getUserId());
		model.addAttribute("rowCount", userEnvironmentService.persistUserEnvironment(selectUserEnvrn, kwsUserEnvrn));
		return "jsonView";
	}
	
}
