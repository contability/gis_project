package kr.co.gitt.kworks.web.cmmn;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsMenu;
import kr.co.gitt.kworks.model.KwsSys;
import kr.co.gitt.kworks.model.KwsSys.SysTy;
import kr.co.gitt.kworks.service.sys.SysService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/////////////////////////////////////////////
/// @class SysController
/// kr.co.gitt.kworks.web.cmmn \n
///   ㄴ SysController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 21. 오후 9:05:26 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 시스템 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/cmmn/sys/")
public class SysController {

	// 시스템 서비스
	@Resource
	SysService sysService;
	
	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 시스템 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/listAll.do", method=RequestMethod.GET)
	public String listAll(Model model) {
		KwsSys kwsSys = new KwsSys();
		kwsSys.setSysTy(SysTy.SYSTEM);
		model.addAttribute("data", sysService.listAllSys(kwsSys));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneSystem
	/// @brief 함수 간략한 설명 : 시스템 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param sysId
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{sysId}/select.do", method=RequestMethod.GET)
	public String selectOneSystem(@PathVariable("sysId") Long sysId, KwsMenu kwsMenu, Model model) {
		model.addAttribute("data", sysService.selectOneSys(sysId));
		return "jsonView";
	}
	
}
