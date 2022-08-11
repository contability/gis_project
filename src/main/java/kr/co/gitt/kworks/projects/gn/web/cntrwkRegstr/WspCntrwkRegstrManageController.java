package kr.co.gitt.kworks.projects.gn.web.cntrwkRegstr;

import javax.annotation.Resource;

import kr.co.gitt.kworks.projects.gn.model.WttSplyMa;
import kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.WspCntrwkRegstrService;

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
/// @class WspCntrwkRegstrManageController
/// kr.co.gitt.kworks.projects.gn.web.cntrwkRegstr \n
///   ㄴ WspCntrwkRegstrManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 14. 오전 9:52:58 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 급수 공사대장 컨트롤러 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/cntrwkRegstr/wsp/")
@Profile({"gn_dev", "gn_oper", "gds_dev", "gds_oper"})
public class WspCntrwkRegstrManageController {
	
	// logger
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 급수 공사대장 서비스
	@Resource
	WspCntrwkRegstrService wspCntrwkRegstrService;
	
	/////////////////////////////////////////////
	/// @fn searchWspCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 급수 공사대장 검색 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttSplyMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="searchWspCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String searchWspCntrwkRegstrPage(WttSplyMa wttSplyMa, Model model) {
		return "/projects/gn/job/cntrwkRegstr/wsp/listWspCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn listRoadCntrwkRegstr
	/// @brief 함수 간략한 설명 : 급수 공사대장 리스트 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttSplyMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listWspCntrwkRegstr.do", method=RequestMethod.GET)
	public String listWspCntrwkRegstr(WttSplyMa wttSplyMa, Model model) {
		model.addAttribute("result", wspCntrwkRegstrService.listWspCntrwkRegstr(wttSplyMa));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneWspCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 급수 공사대장 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/selectOneWspCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String selectOneWspCntrwkRegstrPage(@PathVariable("ftrIdn") Long ftrIdn, WttSplyMa wttSplyMa, Model model) {
		model.addAttribute("result", wspCntrwkRegstrService.selectOneWspCntrwkRegstr(ftrIdn));
		return "/projects/gn/job/cntrwkRegstr/wsp/viewWspCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn addWspCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 급수 공사대장 수정 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttSplyMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/modifyWspCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String modifyWspCntrwkRegstrPage(@PathVariable("ftrIdn") Long ftrIdn, WttSplyMa wttSplyMa, Model model) {
		model.addAttribute("result",wspCntrwkRegstrService.selectOneWspCntrwkRegstr(ftrIdn));
		return "/projects/gn/job/cntrwkRegstr/wsp/modifyWspCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyWspCntrwkRegstr
	/// @brief 함수 간략한 설명 : 급수 공사대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param wttSplyMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/modifyWspCntrwkRegstr.do", method=RequestMethod.POST)
	public String modifyWspCntrwkRegstr(@PathVariable("ftrIdn") Long ftrIdn, WttSplyMa wttSplyMa, Model model) {
		wttSplyMa.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", wspCntrwkRegstrService.modifyWspCntrwkRegstr(wttSplyMa));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn addWspCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 급수 공사대장 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttSplyMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addWspCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String addWspCntrwkRegstrPage(WttSplyMa wttSplyMa, Model model) {
		return "/projects/gn/job/cntrwkRegstr/wsp/addWspCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn addWspCntrwkRegstr
	/// @brief 함수 간략한 설명 : 급수 공사대장 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttSplyMa
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addWspCntrwkRegstr.do", method=RequestMethod.POST)
	public String addWspCntrwkRegstr(WttSplyMa wttSplyMa, Model model) throws FdlException {
		model.addAttribute("rowCount", wspCntrwkRegstrService.addWspCntrwkRegstr(wttSplyMa));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn searchSpatial
	/// @brief 함수 간략한 설명 : 공사 위치
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/searchSpatial.do", method=RequestMethod.GET)
	public String searchSpatial(@PathVariable("ftrIdn") Long ftrIdn, Model model) {
		model.addAttribute("wkts", wspCntrwkRegstrService.searchSpatial(ftrIdn));
		return "jsonView";
	}
	
}
