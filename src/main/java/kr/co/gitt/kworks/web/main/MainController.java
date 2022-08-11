package kr.co.gitt.kworks.web.main;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.cmmn.util.MessageUtils;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsDept;
import kr.co.gitt.kworks.model.KwsSys;
import kr.co.gitt.kworks.model.KwsSys.SysTy;
import kr.co.gitt.kworks.model.KwsSysAuthor;
import kr.co.gitt.kworks.model.KwsUserSys;
import kr.co.gitt.kworks.service.authorGroup.AuthorGroupService;
import kr.co.gitt.kworks.service.cmmn.ImageService;
import kr.co.gitt.kworks.service.dept.DeptService;
import kr.co.gitt.kworks.service.selfSys.SelfSystemService;
import kr.co.gitt.kworks.service.sys.SysService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class MainController
/// kr.co.gitt.kworks.web.main \n
///   ㄴ MainController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 9. 오후 6:10:57 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 메인 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
public class MainController {
	
	// 부서 서비스
	@Resource
	DeptService deptService;
	
	/// 시스템 서비스
	@Resource
	SysService sysService;
	
	/// 나의 시스템 서비스
	@Resource
	SelfSystemService selfSystemService;
	
	// 권한 그룹 서비스
	@Resource
	AuthorGroupService authorGroupService;
	
	// 이미지 서비스
	@Resource
	ImageService imageService;

	/////////////////////////////////////////////
	/// @fn mainPage
	/// @brief 함수 간략한 설명 : 메인 화면
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsSys
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/main.do")
	public String mainPage(Long sysId, Model model) {
		KwsSys kwsSys = sysService.selectOneSys(sysId);
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		String prjCode = MessageUtils.getMessage("Globals.Prj");
		boolean isAuthor = false;
		if(kwsSys.getSysTy().equals(SysTy.SYSTEM)) {
			// 권한 있는 시스템만 접근 가능
			//KwsAuthorGroup kwsAuthorGroup = userDTO.getKwsAuthorGroup();
			for(KwsAuthorGroup kwsAuthorGroup : userDTO.getKwsAuthorGroups()) {
				for(KwsSysAuthor kwsSysAuthor : kwsAuthorGroup.getKwsSysAuthors()) {
					if(StringUtils.equals(kwsSysAuthor.getAuthorAt(), "Y") && kwsSysAuthor.getSysId() == sysId) {
						isAuthor = true;
						break;
					}
				}
			}
		}
		else if(kwsSys.getSysTy().equals(SysTy.USER)) {
			KwsUserSys kwsUserSys = new KwsUserSys();
			kwsUserSys.setSysId(sysId);
			kwsUserSys.setUserId(userDTO.getUserId());
			if(selfSystemService.selectOneUserSystem(kwsUserSys) != null) {
				isAuthor = true;
			}
		}
		
		if(isAuthor) {
			
		
			/*if(prjCode.equals("dh") && sysId==6){
				
				String deptName = null;
				String deptCode = userDTO.getDeptCode();
				KwsDept kwsDept = deptService.selectOneDept(deptCode);
				if(kwsDept != null) {
					deptName = kwsDept.getDeptNm();
				}
				
				model.addAttribute("deptName", deptName);
				model.addAttribute("sys", kwsSys);
				
				return "tab/tabMain";
			} else {
				
				String deptName = null;
				String deptCode = userDTO.getDeptCode();
				KwsDept kwsDept = deptService.selectOneDept(deptCode);
				if(kwsDept != null) {
					deptName = kwsDept.getDeptNm();
				}
				
				model.addAttribute("deptName", deptName);
				model.addAttribute("sys", kwsSys);
				
				return "main/main";
			}*/
			
			String deptName = null;
			String deptCode = userDTO.getDeptCode();
			KwsDept kwsDept = deptService.selectOneDept(deptCode);
			if(kwsDept != null) {
				deptName = kwsDept.getDeptNm();
			}
			
			model.addAttribute("deptName", deptName);
			model.addAttribute("sys", kwsSys);
			
			
			/// 탭 main페이지
				/// 동해시 공유재산
				/// 강릉시 공유재산
			if((prjCode.equals("dh") && sysId==6) || (prjCode.equals("gn") && sysId==13)){
				return "tab/tabMain";
			}else{
				return "main/main";
			}
		}
		else {
			return "redirect:/portal.do";
		}
		
	}
	
	/////////////////////////////////////////////
	/// @fn menuPage
	/// @brief 함수 간략한 설명 : 메뉴 화면 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param pageName
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("/main/menu/{pageName}/page.do")
	public String menuPage(@PathVariable("pageName") String pageName) {
		return "main/menu/" + pageName;
	}
	
}
