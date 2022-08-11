package kr.co.gitt.kworks.web.self;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.dto.UserSearchDTO;
import kr.co.gitt.kworks.model.KwsUser;
import kr.co.gitt.kworks.model.KwsUser.UserGrad;
import kr.co.gitt.kworks.model.KwsUser.UserType;
import kr.co.gitt.kworks.service.authorGroup.AuthorGroupService;
import kr.co.gitt.kworks.service.dept.DeptService;
import kr.co.gitt.kworks.service.user.UserEnvironmentService;
import kr.co.gitt.kworks.service.user.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;


@Controller
@RequestMapping("/self/userInfo/")
public class UserInfoSelfController {
	
	/// 사용자관리 서비스
	@Resource
	UserService userService;
	
	/// 사용자 환경 서비스
	@Resource
	UserEnvironmentService userEnvironmentService;
	
	/// 부서관리 서비스
	@Resource
	DeptService deptService;
	
	/// 권한 그룹 서비스
	@Resource
	AuthorGroupService authorGroupService;
	
	/// 개인정보 관리 목록 화면
	@RequestMapping(value="list.do", method=RequestMethod.GET)
	public String listUserInfoPage(UserSearchDTO searchDTO, Model model) {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		searchDTO.setUserId(userDTO.getUserId());
		searchDTO.setUserGrad(userDTO.getUserGrad());

		model.addAttribute("depts", deptService.listAllDept());
		model.addAttribute("authorGroups", authorGroupService.listAllAuthorGroup());
		model.addAttribute("rows", userService.listUserInfo(searchDTO));
		model.addAttribute("userGrads", UserGrad.values());
		model.addAttribute("userTypes", UserType.values());
		
		return "/self/userInfo/listUserInfo";
		
	}
	
	// 단 건 검색
	@RequestMapping(value="{userId}/select.do", method=RequestMethod.GET)
	public String selectOneUser(@PathVariable("userId") String userId, Model model) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		KwsUser selectUserInfo = userService.selectOneUserInfo(userId);
		if(!StringUtils.equals(selectUserInfo.getUserId(), userDTO.getUserId())) {
			throw new Exception("로그인된 사용자의 개인정보만 조회할 수 있습니다.");
		}

		model.addAttribute("data", selectUserInfo);
		
		return "jsonView";
	}
	
	// 수정
	@RequestMapping(value="{userId}/modify.do", method=RequestMethod.POST)
	public String modifyUser(@PathVariable("userId") String userId, KwsUser kwsUser, Model model) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		KwsUser selectUserInfo = userService.selectOneUserInfo(userId);
		
		if(!StringUtils.equals(selectUserInfo.getUserId(), userDTO.getUserId())) {
			throw new Exception("로그인된 사용자의 개인정보만 조회할 수 있습니다.");
		}
		
		model.addAttribute("rowCount", userService.modifyUserInfo(kwsUser));
		
		return "jsonView";
	}
}
