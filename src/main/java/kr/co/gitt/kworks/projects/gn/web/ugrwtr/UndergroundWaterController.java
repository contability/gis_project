package kr.co.gitt.kworks.projects.gn.web.ugrwtr;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.projects.gn.model.BmlWellCheck;
import kr.co.gitt.kworks.projects.gn.model.BmlWellPs;
import kr.co.gitt.kworks.projects.gn.service.ugrwtr.UndergroundWaterService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class UndergroundWaterController
/// kr.co.gitt.kworks.projects.dh.web.ugrwtr \n
///   ㄴ UndergroundWaterController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 2. 15. 오후 1:59:59 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 강릉시 지하수관정 컨트롤러 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/ugrwtr/")
@Profile({"gn_dev", "gn_oper", "gds_dev", "gds_oper"})
public class UndergroundWaterController {
	
	// logger
	Logger logger = LoggerFactory.getLogger(getClass());
	
	//지하수관정 서비스
	@Resource
	UndergroundWaterService undergroundWaterService;
	
	//도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	/////////////////////////////////////////////
	/// @fn pageUndergroundWater
	/// @brief 함수 간략한 설명 : 지하수관정 검색 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="page.do", method=RequestMethod.GET)
	public String pageUndergroundWater(BmlWellPs bmlWellPs, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 사용구분
		kwsDomnCode.setDomnId("KWS-218");
		model.addAttribute("disCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 시설구분
		kwsDomnCode.setDomnId("KWS-220");
		model.addAttribute("permNtFo", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 법정동
		kwsDomnCode.setDomnId("KWS-0199");
		model.addAttribute("dvopLocC", domnCodeService.listDomnCode(kwsDomnCode));

		// 용도구분
		kwsDomnCode.setDomnId("KWS-0307");
		model.addAttribute("uwaterSrv", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/gn/job/ugrwtr/listUndergroundWater";
	}
	
	/////////////////////////////////////////////
	/// @fn listUndergroundWater
	/// @brief 함수 간략한 설명 : 지하수관정 리스트 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="list.do", method=RequestMethod.GET)
	public String listUndergroundWater(BmlWellPs bmlWellPs, Model model) {
		model.addAttribute("result", undergroundWaterService.list(bmlWellPs));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneUndergroundWaterPage
	/// @brief 함수 간략한 설명 : 지하수관정 상세조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param objectid
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/selectOne.do", method=RequestMethod.GET)
	public String selectOneUndergroundWaterPage(@PathVariable("ftrIdn") Long ftrIdn, BmlWellPs bmlWellPs, Model model){
		
		bmlWellPs.setFtrIdn(ftrIdn);
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 사용구분
		kwsDomnCode.setDomnId("KWS-218");
		model.addAttribute("kwsDisCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 시설구분
		kwsDomnCode.setDomnId("KWS-220");
		model.addAttribute("kwsPermNtFo", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 이용자구분
		kwsDomnCode.setDomnId("KWS-219");
		model.addAttribute("kwsRgtMbdGb", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("result",undergroundWaterService.selectOne(bmlWellPs));
		return "/projects/gn/job/ugrwtr/viewUndergroundWater";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyUndergroundWaterPage
	/// @brief 함수 간략한 설명 : 지하수관정 수정페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param objectid
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/edit.do", method=RequestMethod.GET)
	public String modifyUndergroundWaterPage(@PathVariable("ftrIdn") Long ftrIdn, BmlWellPs bmlWellps, Model model) {

		bmlWellps.setFtrIdn(ftrIdn);
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 사용구분
		kwsDomnCode.setDomnId("KWS-218");
		model.addAttribute("kwsDisCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 시설구분
		kwsDomnCode.setDomnId("KWS-220");
		model.addAttribute("kwsPermNtFo", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 이용자구분
		kwsDomnCode.setDomnId("KWS-219");
		model.addAttribute("kwsRgtMbdGb", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("result",undergroundWaterService.selectOne(bmlWellps));
		return "/projects/gn/job/ugrwtr/modifyUndergroundWater";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyUndergroundWater
	/// @brief 함수 간략한 설명 : 지하수관정 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param objectid
	/// @param bmlWellPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/modify.do", method=RequestMethod.POST)
	public String modifyUndergroundWater(@PathVariable("ftrIdn") Long ftrIdn, BmlWellPs bmlWellPs, MultipartRequest multipartRequest, Model model) throws Exception {
		bmlWellPs.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", undergroundWaterService.modify(bmlWellPs, multipartRequest));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn pageUndergroundWaterChckImprmnHist
	/// @brief 함수 간략한 설명 : 지하수관정 점검정비이력 리스트
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="chckImprmnHist/{permNtNo}/page.do", method=RequestMethod.GET)
	public String pageUndergroundWaterChckImprmnHist(@PathVariable("permNtNo") String permNtNo, BmlWellCheck bmlWellCheck, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 점검정비구분
		kwsDomnCode.setDomnId("KWS-222");
		model.addAttribute("chkCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("result", undergroundWaterService.listChckImprmnHist(bmlWellCheck));
		return "/projects/gn/job/ugrwtr/listUndergroundWaterChckImprmnHist";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneUndergroundWaterChckImprmnHistPage
	/// @brief 함수 간략한 설명 : 지하수관정 점검정비이력 수정창
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chkIdn
	/// @param bmlWellCheck
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="chckImprmnHist/{chkIdn}/selectOne.do", method=RequestMethod.GET)
	public String selectOneUndergroundWaterChckImprmnHistPage(@PathVariable("chkIdn") Long chkIdn, BmlWellCheck bmlWellCheck, Model model){
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 점검정비구분
		kwsDomnCode.setDomnId("KWS-222");
		model.addAttribute("chkCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("result", undergroundWaterService.selectOneChckImprmnHist(chkIdn));
		return "/projects/gn/job/ugrwtr/modifyUndergroundWaterChckImprmnHist";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyUndergroundWaterChckImprmnHist
	/// @brief 함수 간략한 설명 : 지하수관정 점검이력 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chkIdn
	/// @param bmlWellCheck
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="chckImprmnHist/{chkIdn}/modify.do", method=RequestMethod.POST)
	public String modifyUndergroundWaterChckImprmnHist(@PathVariable("chkIdn") Long chkIdn, BmlWellCheck bmlWellCheck, Model model) {
		bmlWellCheck.setChkIdn(chkIdn);
		model.addAttribute("rowCount", undergroundWaterService.modifyChckImprmnHist(bmlWellCheck));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn removeUndergroundWaterChckImprmnHist
	/// @brief 함수 간략한 설명 : 지하수관정 점검이력 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chkIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="chckImprmnHist/{chkIdn}/remove.do", method=RequestMethod.POST)
	public String removeUndergroundWaterChckImprmnHist(@PathVariable("chkIdn") Long chkIdn, Model model) {
		model.addAttribute("rowCount", undergroundWaterService.removeChckImprmnHist(chkIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn pageUndergroundWaterChckImprmnHistAdd
	/// @brief 함수 간략한 설명 : 지하수관정 점검이력 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellCheck
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="chckImprmnHist/addPage.do", method=RequestMethod.GET)
	public String pageUndergroundWaterChckImprmnHistAdd(BmlWellCheck bmlWellCheck, Model model){
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 점검정비구분
		kwsDomnCode.setDomnId("KWS-222");
		model.addAttribute("chkCde", domnCodeService.listDomnCode(kwsDomnCode));
		return "/projects/gn/job/ugrwtr/addUndergroundWaterChckImprmnHist";
	}
	
	/////////////////////////////////////////////
	/// @fn addUndergroundWaterChckImprmnHist
	/// @brief 함수 간략한 설명 : 지하수관정 점검이력 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param permNtNo
	/// @param bmlWellCheck
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="chckImprmnHist/{permNtNo}/add.do", method=RequestMethod.POST)
	public String addUndergroundWaterChckImprmnHist(@PathVariable("permNtNo") String permNtNo, BmlWellCheck bmlWellCheck, Model model) throws FdlException {
		bmlWellCheck.setPermNtNo(permNtNo);
		model.addAttribute("rowCount", undergroundWaterService.addChckImprmnHist(bmlWellCheck));
		return "jsonView";
	}
	
}
