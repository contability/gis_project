package kr.co.gitt.kworks.projects.sc.rest.user;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsUser;
import kr.co.gitt.kworks.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class UserInfoController
/// kr.co.gitt.kworks.projects.sc.rest.user
///   ㄴ UserInfoController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | sjlee |
///    | Date | 2020. 5. 13. 오후 1:40:45 |
///    | Class Version | v1.0 |
///    | 작업자 | sjlee, Others... |
/// @section 상세설명
/// - 이 클래스는 권한그룹명으로 특정기능을 제어하기 위한 클래스 입니다.
/// - 권한 그룹에서 제어되지 않던 부분을 권한 그룹명으로 제어하기 위한 임시적 제안
/// - 현재 속초시의 1개 특정권한그룹에만 적용
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/userInfo/")
@Profile({"sc_dev", "sc_oper"})
public class UserInfoController {
	
	/// 사용자관리 서비스
	@Autowired
	UserService userService;

	/////////////////////////////////////////////
	/// @fn checkLimitedAuthor
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 특정기능의 사용 여부를 반환
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="checkLimitedAuthor.do", method=RequestMethod.GET)
	public String checkLimitedAuthor(Model model) {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		KwsUser kwsUser = userService.selectOneUserInfo(userDTO.getUserId());
		
		String prntngMapAt = checkMapPrintAtByAuthorGroupNm(kwsUser);
		
		model.addAttribute("prntngMapAt", prntngMapAt);
		return "jsonView";
	}

	/////////////////////////////////////////////
	/// @fn checkMapPrintAtByAuthorGroupNm
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 :속초시 "공간정보활용서비스-1"라는 그룹명으로 지도출력 여부를 판단
	/// @param kwsUser
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private String checkMapPrintAtByAuthorGroupNm(KwsUser kwsUser) {
		List<KwsAuthorGroup> kwsAutorGroups = kwsUser.getKwsAuthorGroups();
		for (KwsAuthorGroup kwsAuthorGroup : kwsAutorGroups) {
			String authorGroupNm = kwsAuthorGroup.getAuthorGroupNm();
			if (!authorGroupNm.equals("공간정보활용서비스-1")) {
				return "Y";
			}
		}
		return "N";
	}
}
