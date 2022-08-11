package kr.co.gitt.kworks.projects.sc.web.civilAppeal;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.projects.sc.model.SwtSpmtMa;
import kr.co.gitt.kworks.projects.sc.service.civilAppeal.DrngEqpCnfmPrmisnService;
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
/// @class DrngEqpCnfmPrmisnManageController
/// kr.co.gitt.kworks.projects.sc.web.civilAppeal \n
///   ㄴ DrngEqpCnfmPrmisnManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 23. 오후 5:08:34 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 배수설비인허가대장 관리 컨트롤러 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/civilAppeal/swge/")
@Profile({"sc_dev", "sc_oper"})
public class DrngEqpCnfmPrmisnManageController {
	
	// logger  
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	// 배수설비인허가대장 민원서비스
	@Resource
	DrngEqpCnfmPrmisnService drngEqpCnfmPrmisnService;
	

	/////////////////////////////////////////////
	/// @fn searchDrngEqpCnfmPrmisnPage
	/// @brief 함수 간략한 설명 : 배수설비인허가대장 검색 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSpmtMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="searchDrngEqpCnfmPrmisnPage.do", method=RequestMethod.GET)
	public String searchDrngEqpCnfmPrmisnPage(SwtSpmtMa swtSpmtMa, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 하수처리구분
		kwsDomnCode.setDomnId("KWS-0149");
		model.addAttribute("brcCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 민원지행정읍/면/동
		kwsDomnCode.setDomnId("KWS-0114");
		model.addAttribute("aplBjd", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 인허가구분
		kwsDomnCode.setDomnId("KWS-0142");
		model.addAttribute("pmsCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/sc/job/civilAppeal/swge/searchDrngEqpCnfmPrmisn";
	}
	
	/////////////////////////////////////////////
	/// @fn listDrngEqpCnfmPrmisn
	/// @brief 함수 간략한 설명 : 배수설비인허가대장 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSpmtMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listDrngEqpCnfmPrmisn.do", method=RequestMethod.GET)
	public String listDrngEqpCnfmPrmisn(SwtSpmtMa swtSpmtMa, Model model) {
		model.addAttribute("result", drngEqpCnfmPrmisnService.listDrngEqpCnfmPrmisn(swtSpmtMa));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneDrngEqpCnfmPrmisn
	/// @brief 함수 간략한 설명 : 배수설비인허가대장 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param swtSpmtMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/selectOneDrngEqpCnfmPrmisn.do", method=RequestMethod.GET)
	public String selectOneDrngEqpCnfmPrmisn(@PathVariable("ftrIdn") Long ftrIdn, SwtSpmtMa swtSpmtMa, Model model) {
		model.addAttribute("data",drngEqpCnfmPrmisnService.selectOneDrngEqpCnfmPrmisn(ftrIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyDrngEqpCnfmPrmisn
	/// @brief 함수 간략한 설명 : 배수설비인허가대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param swtSpmtMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/modifyDrngEqpCnfmPrmisn.do", method=RequestMethod.POST)
	public String modifyDrngEqpCnfmPrmisn(@PathVariable("ftrIdn") Long ftrIdn, SwtSpmtMa swtSpmtMa, Model model) {
		swtSpmtMa.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", drngEqpCnfmPrmisnService.modifyDrngEqpCnfmPrmisn(swtSpmtMa));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addDrngEqpCnfmPrmisn
	/// @brief 함수 간략한 설명 : 배수설비인허가대장 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSpmtMa
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addDrngEqpCnfmPrmisn.do", method=RequestMethod.POST)
	public String addDrngEqpCnfmPrmisn(SwtSpmtMa swtSpmtMa, Model model) throws FdlException {
		model.addAttribute("rowCount", drngEqpCnfmPrmisnService.addDrngEqpCnfmPrmisn(swtSpmtMa));
		return "jsonView";
	}

}
