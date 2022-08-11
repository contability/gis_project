package kr.co.gitt.kworks.rest.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsUserLyr;
import kr.co.gitt.kworks.service.user.UserLayerService;

/////////////////////////////////////////////
/// @class UserLayerController
/// kr.co.gitt.kworks.rest.user \n
///   ㄴ UserLayerController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 10. 18. 오후 1:57:58 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 레이어 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/userLayer/")
public class UserLayerController {
	
	/// 사용자 레이어 서비스
	@Resource
	UserLayerService userLayerService;
	
	/////////////////////////////////////////////
	/// @fn listAllUserLayer
	/// @brief 함수 간략한 설명 : 사용자 레이어 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUserLyr
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listAll.do", method=RequestMethod.GET)
	public String listAllUserLayer(KwsUserLyr kwsUserLyr, Model model) {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsUserLyr.setUserId(userDTO.getUserId());
		model.addAttribute("rows", userLayerService.listAllUserLayer(kwsUserLyr));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addUserLayer
	/// @brief 함수 간략한 설명 : 사용자 레이어 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUserLyr
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="add.do", method=RequestMethod.POST)
	public String addUserLayer(KwsUserLyr kwsUserLyr, Model model) throws FdlException {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsUserLyr.setUserId(userDTO.getUserId());
		model.addAttribute("rowCount", userLayerService.addUserLayer(kwsUserLyr));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn removeUserLayer
	/// @brief 함수 간략한 설명 : 사용자 레이어 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param lyrNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{lyrNo}/remove.do", method=RequestMethod.POST)
	public String removeUserLayer(@PathVariable("lyrNo") Long lyrNo, Model model) {
		model.addAttribute("rowCount", userLayerService.removeUserLayer(lyrNo));
		return "jsonView";
	}
}
