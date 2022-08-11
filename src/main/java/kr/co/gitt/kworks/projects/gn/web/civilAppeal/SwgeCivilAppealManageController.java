package kr.co.gitt.kworks.projects.gn.web.civilAppeal;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.projects.gn.model.SwtSserMa;
import kr.co.gitt.kworks.projects.gn.service.civilAppeal.SwgeCivilAppealService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class SwgeCivilAppealManageController
/// kr.co.gitt.kworks.projects.gn.web.civilAppeal \n
///   ㄴ SwgeCivilAppealManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 23. 오후 4:01:20 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 하수시스템 민원관리 컨트롤러 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/civilAppeal/swge/")
@Profile({"gn_dev", "gn_oper", "gds_dev", "gds_oper"})
public class SwgeCivilAppealManageController {
	
	// logger  
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	// 하수시스템 민원서비스
	@Resource
	SwgeCivilAppealService swgeCivilAppealService;
	

	/////////////////////////////////////////////
	/// @fn searchSwgeCivilAppealPage
	/// @brief 함수 간략한 설명 : 하수민원 검색 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSserMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="searchSwgeCivilAppealPage.do", method=RequestMethod.GET)
	public String searchSwgeCivilAppealPage(SwtSserMa swtSserMa, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 하수민원구분
		kwsDomnCode.setDomnId("KWS-0183");
		model.addAttribute("aplCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 처리상태
		kwsDomnCode.setDomnId("KWS-0184");
		model.addAttribute("proCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 민원지행정읍/면/동
		kwsDomnCode.setDomnId("KWS-0199");
		model.addAttribute("aplBjd", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/gn/job/civilAppeal/swge/searchSwgeCivilAppeal";
	}
	
	/////////////////////////////////////////////
	/// @fn listSwgeCivilAppeal
	/// @brief 함수 간략한 설명 : 하수민원 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSserMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listSwgeCivilAppeal.do", method=RequestMethod.GET)
	public String listSwgeCivilAppeal(SwtSserMa swtSserMa, Model model) {
		model.addAttribute("result", swgeCivilAppealService.listSwgeCivilAppeal(swtSserMa));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneSwgeCivilAppeal
	/// @brief 함수 간략한 설명 : 하수민원 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param swtSserMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/selectOneSwgeCivilAppeal.do", method=RequestMethod.GET)
	public String selectOneSwgeCivilAppeal(@PathVariable("ftrIdn") Long ftrIdn, SwtSserMa swtSserMa, Model model) {
		model.addAttribute("data",swgeCivilAppealService.selectOneSwgeCivilAppeal(ftrIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifySwgeCivilAppeal
	/// @brief 함수 간략한 설명 : 하수민원 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param swtSserMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/modifySwgeCivilAppeal.do", method=RequestMethod.POST)
	public String modifySwgeCivilAppeal(@PathVariable("ftrIdn") Long ftrIdn, SwtSserMa swtSserMa, Model model) {
		swtSserMa.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", swgeCivilAppealService.modifySwgeCivilAppeal(swtSserMa));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addSwgeCivilAppeal
	/// @brief 함수 간략한 설명 : 하수민원 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSserMa
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addSwgeCivilAppeal.do", method=RequestMethod.POST)
	public String addSwgeCivilAppeal(SwtSserMa swtSserMa, Model model) throws FdlException {
		model.addAttribute("rowCount", swgeCivilAppealService.addSwgeCivilAppeal(swtSserMa));
		return "jsonView";
	}

}
