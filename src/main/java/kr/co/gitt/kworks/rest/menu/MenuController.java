package kr.co.gitt.kworks.rest.menu;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsMenu;
import kr.co.gitt.kworks.model.KwsSys;
import kr.co.gitt.kworks.model.KwsSys.SysTy;
import kr.co.gitt.kworks.model.KwsUserMenu;
import kr.co.gitt.kworks.service.menu.MenuService;
import kr.co.gitt.kworks.service.selfSys.SelfSystemService;
import kr.co.gitt.kworks.service.sys.SysService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class MenuController
/// kr.co.gitt.kworks.rest.job \n
///   ㄴ MenuController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 11. 오후 6:05:15 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 메뉴 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/menu/")
public class MenuController {

	/// 메뉴 서비스
	@Resource
	MenuService menuService;
	
	/// 시스템 서비스
	@Resource
	SysService sysService;
	
	/// 나의 시스템 서비스
	@Resource
	SelfSystemService selfSystemService;
	
	/////////////////////////////////////////////
	/// @fn listAllMenu
	/// @brief 함수 간략한 설명 : 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsMenu
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listAll.do", method=RequestMethod.GET)
	public String listAllMenu(Long sysId, Model model) {
		KwsSys kwsSys = sysService.selectOneSys(sysId);
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		if(kwsSys.getSysTy().equals(SysTy.SYSTEM)) {
			KwsMenu kwsMenu = new KwsMenu();
			kwsMenu.setSysId(sysId);
			model.addAttribute("rows", menuService.listAllMenu(kwsMenu));
		}
		else if(kwsSys.getSysTy().equals(SysTy.USER)) {
			KwsUserMenu kwsUserMenu = new KwsUserMenu();
			kwsUserMenu.setSysId(sysId);
			kwsUserMenu.setUserId(userDTO.getUserId());
			model.addAttribute("rows", selfSystemService.listUserMenu(kwsUserMenu));
		}
		else {
			model.addAttribute("rows", null);
		}
		return "jsonView";
	}
}
