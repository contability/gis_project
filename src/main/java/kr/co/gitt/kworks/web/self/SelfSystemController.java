package kr.co.gitt.kworks.web.self;

import java.io.IOException;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsMenu;
import kr.co.gitt.kworks.model.KwsSys;
import kr.co.gitt.kworks.model.KwsSys.SysTy;
import kr.co.gitt.kworks.model.KwsUserMenu;
import kr.co.gitt.kworks.model.KwsUserSys;
import kr.co.gitt.kworks.service.menu.MenuService;
import kr.co.gitt.kworks.service.selfSys.SelfSystemService;
import kr.co.gitt.kworks.service.sys.SysService;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

@Controller
@RequestMapping("/self/selfSys/")
public class SelfSystemController {

	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
		
	// 나의 시스템 서비스
	@Resource
	SelfSystemService selfSystemService;
	
	// 시스템 서비스
	@Resource
	SysService sysService;

	// 메뉴 서비스
	@Resource
	MenuService menuService;
	
	@RequestMapping(value="list.do", method=RequestMethod.GET)
	public String listPage(KwsSys kwsSys, Model model){
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		String userId = userDTO.getUserId();
		KwsUserSys kwsUserSys = new KwsUserSys(); 
		KwsMenu kwsMenu = new KwsMenu();
		
		kwsUserSys.setUserId(userId);
		kwsUserSys.setSysTy(SysTy.USER);
		kwsSys.setSysTy(SysTy.SYSTEM);
		//그룹이 아닌 항목만 검색하기 위한 설정
		kwsMenu.setFnctId("NotGroup");
		
		model.addAttribute("result", selfSystemService.list(kwsUserSys));
		model.addAttribute("sysList", sysService.listAllSys(kwsSys));
		model.addAttribute("menuList", menuService.listAllMenu(kwsMenu));
		return "/self/selfSys/selfSystemList";
	}
	
	@RequestMapping(value="allList.do", method=RequestMethod.POST)
	public String allList(Model model){
		KwsMenu kwsMenu = new KwsMenu();
		
		KwsSys kwsSys = new KwsSys();
		kwsSys.setSysTy(SysTy.SYSTEM);
		
		//그룹이 아닌 항목만 검색하기 위한 설정
		kwsMenu.setFnctId("NotGroup");
				
		model.addAttribute("sysList", sysService.listAllSys(kwsSys) );
		model.addAttribute("menuList", menuService.listAllMenu(kwsMenu));
		
		return "jsonView";
	}
	
	@RequestMapping(value="add.do", method=RequestMethod.POST)
	public String add(String content, Model model) throws FdlException, JsonParseException, JsonMappingException, IOException{
		
		ObjectMapper objectMapper = new ObjectMapper();
		KwsUserSys kwsUserSys = objectMapper.readValue(content, KwsUserSys.class);
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		String userId = userDTO.getUserId();
		
		model.addAttribute("result", selfSystemService.add(kwsUserSys, userId));
		return "jsonView";
	}
	
	@RequestMapping(value="{sysId}/selectOne.do",method=RequestMethod.POST)
	public String selectOne(@PathVariable("sysId") Long sysId, Model model){
		KwsUserMenu kwsUserMenu = new KwsUserMenu();
		kwsUserMenu.setSysId(sysId);
		
		model.addAttribute("sys", sysService.selectOneSys(sysId));
		model.addAttribute("menuList", selfSystemService.listUserMenu(kwsUserMenu));
		return "jsonView";
	}
	
	@RequestMapping(value="{sysId}/modify.do", method=RequestMethod.POST)
	public String modify(@PathVariable("sysId") Long sysId, String content, Model model) throws JsonParseException, JsonMappingException, IOException, FdlException{
		
		ObjectMapper objectMapper = new ObjectMapper();
		KwsUserSys kwsUserSys = objectMapper.readValue(content, KwsUserSys.class);
		
		kwsUserSys.setSysId(sysId);
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		String userId = userDTO.getUserId();
		
		model.addAttribute("result", selfSystemService.modify(kwsUserSys, userId));
		return "jsonView";
	}

	@RequestMapping(value="{sysId}/remove.do", method=RequestMethod.POST)
	public String reomve(@PathVariable("sysId") Long sysId, Model model){
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		String userId = userDTO.getUserId();
		
		KwsUserMenu kwsUserMenu = new KwsUserMenu();
		kwsUserMenu.setSysId(sysId);
		
		model.addAttribute("result", selfSystemService.remove(kwsUserMenu, userId));
		return "jsonView";
	}
}
