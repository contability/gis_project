package kr.co.gitt.kworks.projects.gn.web.landCenter;

import javax.annotation.Resource;

import kr.co.gitt.kworks.projects.gn.model.LdConsMa;
import kr.co.gitt.kworks.projects.gn.service.ladCntr.LadCntrwkRegstrService;
import kr.co.gitt.kworks.service.dept.DeptService;
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
/// @class LandCenterCntrwkRegstrManageController
/// kr.co.gitt.kworks.projects.gn.web.landCenter \n
///   ㄴ LandCenterCntrwkRegstrManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2018. 4. 4. 오후 4:36:00 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 토지중심공사대장 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/landCenter/")
@Profile({"gn_dev", "gn_oper"})
public class LandCenterCntrwkRegstrManageController {
	
	// logger
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	/// 부서관리 서비스
	@Resource
	DeptService deptService;
	
	// 토지중심공사대장 서비스
	@Resource
	LadCntrwkRegstrService ladCntrwkRegstrService;
	
	/////////////////////////////////////////////
	/// @fn searchLandCenterCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 토지중심공사대장 검색 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldConsMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="searchLandCenterCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String searchLandCenterCntrwkRegstrPage(LdConsMa ldConsMa, Model model) {
//		KwsDomnCode kwsDomnCode = new KwsDomnCode();
//		kwsDomnCode.setSearchCondition("2");
//		
//		kwsDomnCode.setDomnId("KWS-224");
//		model.addAttribute("cntDept", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("cntDept", deptService.listAllDept());
		
		return "/projects/gn/job/landCenter/listLandCenterCntrwkRegstr";
	}
	
	
	/////////////////////////////////////////////
	/// @fn listLandCenterCntrwkRegstr
	/// @brief 함수 간략한 설명 : 토지중심공사대장 리스트 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldConsMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listLandCenterCntrwkRegstr.do", method=RequestMethod.GET)
	public String listLandCenterCntrwkRegstr(LdConsMa ldConsMa, Model model){

		model.addAttribute("result", ladCntrwkRegstrService.listLandCenterCetrwkRegstr(ldConsMa));
		return "jsonView";
	}
	
	
	/////////////////////////////////////////////
	/// @fn selectOneLandCenterCntrwkRegstr
	/// @brief 함수 간략한 설명 : 토지중심공사대장 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{cntIdn}/selectOneLandCenterCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String selectOneLandCenterCntrwkRegstr(@PathVariable("cntIdn") Long cntIdn, Model model){
//		KwsDomnCode kwsDomnCode = new KwsDomnCode();
//		kwsDomnCode.setSearchCondition("2");
//		
//		kwsDomnCode.setDomnId("KWS-224");
//		model.addAttribute("cntDeptList", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("cntDeptList", deptService.listAllDept());
		model.addAttribute("result",ladCntrwkRegstrService.selectOneLandCenterCetrwkRegstr(cntIdn));
		model.addAttribute("useResult", ladCntrwkRegstrService.listLandUseInfoRegstr(cntIdn));
		
		return "/projects/gn/job/landCenter/viewLandCenterCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn addLandCenterCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 토지중심공사대장 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldConsMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	
	@RequestMapping(value="addLandCenterCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String addLandCenterCntrwkRegstrPage(LdConsMa ldConsMa, Model model){
//		KwsDomnCode kwsDomnCode = new KwsDomnCode();
//		kwsDomnCode.setSearchCondition("2");
//		
//		kwsDomnCode.setDomnId("KWS-224");
//		model.addAttribute("cntDeptList", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("cntDeptList", deptService.listAllDept());
		
		return "/projects/gn/job/landCenter/addLandCenterCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn addLandCenterCntrwkRegstr
	/// @brief 함수 간략한 설명 : 토지중심공사대장 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldConsMa
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addLandCenterCntrwkRegstr.do", method=RequestMethod.POST)
	public String addLandCenterCntrwkRegstr(LdConsMa ldConsMa, Model model) throws FdlException{
		
		model.addAttribute("rowCount", ladCntrwkRegstrService.addLandCenterCetrwkRegstr(ldConsMa));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyLandCenterCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 토지중심공사대장 수정 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{cntIdn}/modifyLandCenterCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String modifyLandCenterCntrwkRegstrPage(@PathVariable("cntIdn") Long cntIdn, Model model){
//		KwsDomnCode kwsDomnCode = new KwsDomnCode();
//		kwsDomnCode.setSearchCondition("2");
//		
//		kwsDomnCode.setDomnId("KWS-224");
//		model.addAttribute("cntDeptList", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("cntDeptList", deptService.listAllDept());
		
		model.addAttribute("result", ladCntrwkRegstrService.selectOneLandCenterCetrwkRegstr(cntIdn));
		
		return "/projects/gn/job/landCenter/modifyLandCenterCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyLandCenterCntrwkRegstr
	/// @brief 함수 간략한 설명 : 토지중심공사대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @param ldConsMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{cntIdn}/modifyLandCenterCntrwkRegstr.do", method=RequestMethod.POST)
	public String modifyLandCenterCntrwkRegstr(@PathVariable("cntIdn") Long cntIdn, LdConsMa ldConsMa, Model model) throws FdlException{
		ldConsMa.setCntIdn(cntIdn);
		model.addAttribute("rowCount", ladCntrwkRegstrService.modifyLandCenterCetrwkRegstr(ldConsMa));
		
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn removeLandCenterCntrwkRegstr
	/// @brief 함수 간략한 설명 : 토지중심공사대장 삭제 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{cntIdn}/removeLandCenterCntrwkRegstr.do", method=RequestMethod.POST)
	public String removeLandCenterCntrwkRegstr(@PathVariable("cntIdn") Long cntIdn, Model model) throws FdlException {
		model.addAttribute("rowCount", ladCntrwkRegstrService.removeLandCenterCetrwkRegstr(cntIdn));
		return "jsonView";
	}

}
