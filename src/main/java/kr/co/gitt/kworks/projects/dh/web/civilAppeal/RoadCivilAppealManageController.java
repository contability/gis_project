package kr.co.gitt.kworks.projects.dh.web.civilAppeal;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.projects.dh.model.RdtRserMa;
import kr.co.gitt.kworks.projects.dh.service.civilAppeal.RoadCivilAppealService;
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
/// @class RoadCivilAppealManageController
/// kr.co.gitt.kworks.projects.dh.web.civilAppeal \n
///   ㄴ RoadCivilAppealManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 22. 오후 2:34:18 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 도로시스템 민원 관리 컨트롤러 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/civilAppeal/road/")
@Profile({"dh_dev", "dh_oper"})
public class RoadCivilAppealManageController {
	
	// logger  
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	// 도로시스템 민원서비스
	@Resource
	RoadCivilAppealService roadCivilAppealService;
	
	/////////////////////////////////////////////
	/// @fn searchRoadCivilAppealPage
	/// @brief 함수 간략한 설명 : 도로민원 검색 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtRserMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="searchRoadCivilAppealPage.do", method=RequestMethod.GET)
	public String searchRoadCivilAppealPage(RdtRserMa rdtRserMa, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 도로민원구분
		kwsDomnCode.setDomnId("KWS-1526");
		model.addAttribute("aplCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 처리상태
		kwsDomnCode.setDomnId("KWS-0002");
		model.addAttribute("proCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 민원지법정읍/면/동
		kwsDomnCode.setDomnId("KWS-0562");
		model.addAttribute("aplBjd", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/dh/job/civilAppeal/road/searchRoadCivilAppeal";
	}
	
	/////////////////////////////////////////////
	/// @fn listRoadCivilAppeal
	/// @brief 함수 간략한 설명 : 도로민원 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtRserMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listRoadCivilAppeal.do", method=RequestMethod.GET)
	public String listRoadCivilAppeal(RdtRserMa rdtRserMa, Model model) {
		model.addAttribute("result", roadCivilAppealService.listRoadCivilAppeal(rdtRserMa));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneRoadCivilAppeal
	/// @brief 함수 간략한 설명 : 도로민원 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param rdtRserMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/selectOneRoadCivilAppeal.do", method=RequestMethod.GET)
	public String selectOneRoadCivilAppeal(@PathVariable("ftrIdn") Long ftrIdn, RdtRserMa rdtRserMa, Model model) {
		model.addAttribute("data",roadCivilAppealService.selectOneRoadCivilAppeal(ftrIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyRoadCivilAppeal
	/// @brief 함수 간략한 설명 : 도로민원 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param rdtRserMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/modifyRoadCivilAppeal.do", method=RequestMethod.POST)
	public String modifyRoadCivilAppeal(@PathVariable("ftrIdn") Long ftrIdn, RdtRserMa rdtRserMa, Model model) {
		rdtRserMa.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", roadCivilAppealService.modifyRoadCivilAppeal(rdtRserMa));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addRoadCivilAppeal
	/// @brief 함수 간략한 설명 : 도로민원 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtRserMa
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addRoadCivilAppeal.do", method=RequestMethod.POST)
	public String addRoadCivilAppeal(RdtRserMa rdtRserMa, Model model) throws FdlException {
		model.addAttribute("rowCount", roadCivilAppealService.addRoadCivilAppeal(rdtRserMa));
		return "jsonView";
	}

}
