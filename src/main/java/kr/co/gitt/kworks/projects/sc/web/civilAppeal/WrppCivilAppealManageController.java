package kr.co.gitt.kworks.projects.sc.web.civilAppeal;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.projects.sc.model.WttWserMa;
import kr.co.gitt.kworks.projects.sc.service.civilAppeal.WrppCivilAppealService;
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
/// @class WrppCivilAppealManageController
/// kr.co.gitt.kworks.projects.sc.web.civilAppeal \n
///   ㄴ WrppCivilAppealManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 23. 오후 4:01:45 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 상수시스템 민원관리 컨트롤러 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/civilAppeal/wrpp/")
@Profile({"sc_dev", "sc_oper"})
public class WrppCivilAppealManageController {
	
	// logger  
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	// 상수시스템 민원서비스
	@Resource
	WrppCivilAppealService wrppCivilAppealService;
	

	/////////////////////////////////////////////
	/// @fn searchWrppCivilAppealPage
	/// @brief 함수 간략한 설명 : 상수민원 검색 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttWserMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="searchWrppCivilAppealPage.do", method=RequestMethod.GET)
	public String searchWrppCivilAppealPage(WttWserMa wttWserMa, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 상수민원구분
		kwsDomnCode.setDomnId("KWS-0134");
		model.addAttribute("aplCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 처리상태
		kwsDomnCode.setDomnId("KWS-0070");
		model.addAttribute("proCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 민원지행정읍/면/동
		kwsDomnCode.setDomnId("KWS-0114");
		model.addAttribute("aplHjd", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/sc/job/civilAppeal/wrpp/searchWrppCivilAppeal";
	}
	
	/////////////////////////////////////////////
	/// @fn listWrppCivilAppeal
	/// @brief 함수 간략한 설명 : 상수민원 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttWserMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listWrppCivilAppeal.do", method=RequestMethod.GET)
	public String listWrppCivilAppeal(WttWserMa wttWserMa, Model model) {
		model.addAttribute("result", wrppCivilAppealService.listWrppCivilAppeal(wttWserMa));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneWrppCivilAppeal
	/// @brief 함수 간략한 설명 : 상수민원 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param wttWserMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/selectOneWrppCivilAppeal.do", method=RequestMethod.GET)
	public String selectOneWrppCivilAppeal(@PathVariable("ftrIdn") Long ftrIdn, WttWserMa wttWserMa, Model model) {
		model.addAttribute("data",wrppCivilAppealService.selectOneWrppCivilAppeal(ftrIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyWrppCivilAppeal
	/// @brief 함수 간략한 설명 : 상수민원 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param wttWserMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/modifyWrppCivilAppeal.do", method=RequestMethod.POST)
	public String modifyWrppCivilAppeal(@PathVariable("ftrIdn") Long ftrIdn, WttWserMa wttWserMa, Model model) {
		wttWserMa.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", wrppCivilAppealService.modifyWrppCivilAppeal(wttWserMa));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addWrppCivilAppeal
	/// @brief 함수 간략한 설명 : 상수민원 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttWserMa
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addWrppCivilAppeal.do", method=RequestMethod.POST)
	public String addWrppCivilAppeal(WttWserMa wttWserMa, Model model) throws FdlException {
		model.addAttribute("rowCount", wrppCivilAppealService.addWrppCivilAppeal(wttWserMa));
		return "jsonView";
	}

}
