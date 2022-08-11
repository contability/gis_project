package kr.co.gitt.kworks.projects.sc.web.cntrwkRegstr;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.projects.sc.dto.CntrwkRegstrDTO;
import kr.co.gitt.kworks.projects.sc.model.WttChngDt;
import kr.co.gitt.kworks.projects.sc.model.WttConsMa;
import kr.co.gitt.kworks.projects.sc.model.WttCostDt;
import kr.co.gitt.kworks.projects.sc.model.WttFlawDt;
import kr.co.gitt.kworks.projects.sc.model.WttSubcDt;
import kr.co.gitt.kworks.projects.sc.service.cntrwkRegstr.WrppCntrwkRegstrService;
import kr.co.gitt.kworks.service.cmmn.ImageService;
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
/// @class WrppCntrwkRegstrManageController
/// kr.co.gitt.kworks.projects.sc.web.cntrwkRegstr \n
///   ㄴ WrppCntrwkRegstrManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 10. 오후 5:15:21 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 상수 공사대장 컨트롤러 입니다. 
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/cntrwkRegstr/wrpp/")
@Profile({"sc_dev", "sc_oper"})
public class WrppCntrwkRegstrManageController {
	
	// logger
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	// 상수 공사대장 서비스
	@Resource
	WrppCntrwkRegstrService wrppCntrwkRegstrService;
	
	// 이미지 서비스
	@Resource
	ImageService imageService;
	
	/////////////////////////////////////////////
	/// @fn pageWrppCntrwkRegstr
	/// @brief 함수 간략한 설명 : 상수 공사대장 검색 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttConsMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="searchWrppCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String searchWrppCntrwkRegstrPage(WttConsMa wttConsMa, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0046");
		model.addAttribute("wrkCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/sc/job/cntrwkRegstr/wrpp/listWrppCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn listWrppCntrwkRegstr
	/// @brief 함수 간략한 설명 : 상수 공사대장 리스트 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttConsMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listWrppCntrwkRegstr.do", method=RequestMethod.GET)
	public String listWrppCntrwkRegstr(WttConsMa wttConsMa, Model model) {
		model.addAttribute("result", wrppCntrwkRegstrService.listWrppCntrwkRegstr(wttConsMa));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneWrppCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 상수 공사대장 단 건 조회
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
	@RequestMapping(value="{ftrIdn}/selectOneWrppCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String selectOneWrppCntrwkRegstrPage(@PathVariable("ftrIdn") Long ftrIdn, WttFlawDt wttFlawDt, WttCostDt wttCostDt, WttChngDt wttChngDt, WttSubcDt wttSubcDt, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0046");
		model.addAttribute("wrkCdeList", domnCodeService.listDomnCode(kwsDomnCode));

		kwsDomnCode.setDomnId("KWS-0045");
		model.addAttribute("cttCdeList", domnCodeService.listDomnCode(kwsDomnCode));

		kwsDomnCode.setDomnId("KWS-0106");
		model.addAttribute("payCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		WttConsMa wttConsMa = wrppCntrwkRegstrService.selectOneWrppCntrwkRegstr(ftrIdn);
		
		model.addAttribute("result", wttConsMa);
		model.addAttribute("wrppFlawMendngDtls", wrppCntrwkRegstrService.listWrppFlawMendngDtls(wttFlawDt));
		model.addAttribute("wrppCntrwkCtPymntDtls", wrppCntrwkRegstrService.listWrppCntrwkCtPymntDtls(wttCostDt));
		model.addAttribute("wrppDsgnChangeDtls", wrppCntrwkRegstrService.listWrppDsgnChangeDtls(wttChngDt));
		model.addAttribute("wrppSubcntrDtls", wrppCntrwkRegstrService.listWrppSubcntrDtls(wttSubcDt));
		
		KwsImage kwsImage = new KwsImage();
		kwsImage.setFtrIdn(ftrIdn);
		kwsImage.setFtrCde(wttConsMa.getFtrCde());
		model.addAttribute("resultPhoto", imageService.listAllImage(kwsImage));

		return "/projects/sc/job/cntrwkRegstr/wrpp/viewWrppCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn addWrppCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 상수 공사대장 수정 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttConsMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/modifyWrppCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String modifyWrppCntrwkRegstrPage(@PathVariable("ftrIdn") Long ftrIdn, WttFlawDt wttFlawDt, WttCostDt wttCostDt, WttChngDt wttChngDt, WttSubcDt wttSubcDt, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0046");
		model.addAttribute("wrkCdeList", domnCodeService.listDomnCode(kwsDomnCode));

		kwsDomnCode.setDomnId("KWS-0045");
		model.addAttribute("cttCdeList", domnCodeService.listDomnCode(kwsDomnCode));

		kwsDomnCode.setDomnId("KWS-0106");
		model.addAttribute("payCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("result", wrppCntrwkRegstrService.selectOneWrppCntrwkRegstr(ftrIdn));
		model.addAttribute("wrppFlawMendngDtls", wrppCntrwkRegstrService.listWrppFlawMendngDtls(wttFlawDt));
		model.addAttribute("wrppCntrwkCtPymntDtls", wrppCntrwkRegstrService.listWrppCntrwkCtPymntDtls(wttCostDt));
		model.addAttribute("wrppDsgnChangeDtls", wrppCntrwkRegstrService.listWrppDsgnChangeDtls(wttChngDt));
		model.addAttribute("wrppSubcntrDtls", wrppCntrwkRegstrService.listWrppSubcntrDtls(wttSubcDt));
		
		KwsImage kwsImage = new KwsImage();
		kwsImage.setFtrIdn(ftrIdn);
		model.addAttribute("resultPhoto", imageService.listAllImage(kwsImage));
		
		return "/projects/sc/job/cntrwkRegstr/wrpp/modifyWrppCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyWrppCntrwkRegstr
	/// @brief 함수 간략한 설명 : 상수 공사대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param wttConsMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/modifyWrppCntrwkRegstr.do", method=RequestMethod.POST)
	public String modifyWrppCntrwkRegstr(@PathVariable("ftrIdn") Long ftrIdn, WttConsMa wttConsMa, Model model) {
		wttConsMa.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", wrppCntrwkRegstrService.modifyWrppCntrwkRegstr(wttConsMa));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn addWrppCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 상수 공사대장 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttConsMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addWrppCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String addWrppCntrwkRegstrPage(WttConsMa wttConsMa, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0046");
		model.addAttribute("wrkCdeList", domnCodeService.listDomnCode(kwsDomnCode));

		kwsDomnCode.setDomnId("KWS-0045");
		model.addAttribute("cttCdeList", domnCodeService.listDomnCode(kwsDomnCode));

		kwsDomnCode.setDomnId("KWS-0106");
		model.addAttribute("payCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/sc/job/cntrwkRegstr/wrpp/addWrppCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn addWrppCntrwkRegstr
	/// @brief 함수 간략한 설명 : 상수 공사대장 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttConsMa
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addWrppCntrwkRegstr.do", method=RequestMethod.POST)
	public String addWrppCntrwkRegstr(WttConsMa wttConsMa, Model model) throws FdlException {
		model.addAttribute("rowCount", wrppCntrwkRegstrService.addWrppCntrwkRegstr(wttConsMa));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyWrppFlawMendngDtlsPage
	/// @brief 함수 간략한 설명 : 상수 하자 보수 내역 조회 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{shtIdn}/selectOneWrppFlawMendngDtlsPage.do", method=RequestMethod.GET)
	public String selectOneWrppFlawMendngDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		model.addAttribute("result", wrppCntrwkRegstrService.selectOneWrppFlawMendngDtls(cntrwkRegstrDTO));
		return "/projects/sc/job/cntrwkRegstr/wrpp/viewWrppFlawMendngDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyWrppFlawMendngDtlsPage
	/// @brief 함수 간략한 설명 : 상수 하자 보수 내역 수정 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifyWrppFlawMendngDtlsPage.do", method=RequestMethod.GET)
	public String modifyWrppFlawMendngDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		model.addAttribute("result", wrppCntrwkRegstrService.selectOneWrppFlawMendngDtls(cntrwkRegstrDTO));
		return "/projects/sc/job/cntrwkRegstr/wrpp/modifyWrppFlawMendngDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyWrppFlawMendngDtls
	/// @brief 함수 간략한 설명 : 상수 하자보수내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @param wttFlawDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifyWrppFlawMendngDtls.do", method=RequestMethod.POST)
	public String modifyWrppFlawMendngDtls(WttFlawDt wttFlawDt, Model model) {
		model.addAttribute("rowCount", wrppCntrwkRegstrService.modifyWrppFlawMendngDtls(wttFlawDt));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn addWrppFlawMendngDtlsPage
	/// @brief 함수 간략한 설명 : 상수 하자보수내역 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttFlawDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addWrppFlawMendngDtlsPage.do", method=RequestMethod.GET)
	public String addWrppFlawMendngDtlsPage(WttFlawDt wttFlawDt, Model model) {
		return "/projects/sc/job/cntrwkRegstr/wrpp/addWrppFlawMendngDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn addWrppFlawMendngDtls
	/// @brief 함수 간략한 설명 : 상수 하자보수내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttFlawDt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/addWrppFlawMendngDtls.do", method=RequestMethod.POST)
	public String addWrppFlawMendngDtls(@PathVariable("ftrIdn") Long ftrIdn, WttFlawDt wttFlawDt, Model model) throws FdlException {
		wttFlawDt.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", wrppCntrwkRegstrService.addWrppFlawMendngDtls(wttFlawDt));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn removeWrppFlawMendngDtls
	/// @brief 함수 간략한 설명 : 하자 보수 내역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{shtIdn}/removeWrppFlawMendngDtls.do", method=RequestMethod.POST)
	public String removeWrppFlawMendngDtls(@PathVariable("shtIdn") Long shtIdn, Model model) {
		model.addAttribute("rowCount", wrppCntrwkRegstrService.removeWrppFlawMendngDtls(shtIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneWrppCntrwkCtPymntDtlsPage
	/// @brief 함수 간략한 설명 : 상수 공사비 지급내역 조회 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntrwkRegstrDTO
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{shtIdn}/selectOneWrppCntrwkCtPymntDtlsPage.do", method=RequestMethod.GET)
	public String selectOneWrppCntrwkCtPymntDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0106");
		model.addAttribute("payCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("result", wrppCntrwkRegstrService.selectOneWrppCntrwkCtPymntDtls(cntrwkRegstrDTO));
		return "/projects/sc/job/cntrwkRegstr/wrpp/viewWrppCntrwkCtPymntDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyWrppCntrwkCtPymntDtlsPage
	/// @brief 함수 간략한 설명 : 상수 공사비 지급내역 수정 페이지 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifyWrppCntrwkCtPymntDtlsPage.do", method=RequestMethod.GET)
	public String modifyWrppCntrwkCtPymntDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
	
		kwsDomnCode.setDomnId("KWS-0106");
		model.addAttribute("payCdeList", domnCodeService.listDomnCode(kwsDomnCode));
	
		model.addAttribute("result", wrppCntrwkRegstrService.selectOneWrppCntrwkCtPymntDtls(cntrwkRegstrDTO));
		return "/projects/sc/job/cntrwkRegstr/wrpp/modifyWrppCntrwkCtPymntDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyWrppCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 상수 공사비 지급내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @param wttCostDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifyWrppCntrwkCtPymntDtls.do", method=RequestMethod.POST)
	public String modifyWrppCntrwkCtPymntDtls(WttCostDt wttCostDt, Model model) {
		model.addAttribute("rowCount", wrppCntrwkRegstrService.modifyWrppCntrwkCtPymntDtls(wttCostDt));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn removeWrppCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 공사비 지급내역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{shtIdn}/removeWrppCntrwkCtPymntDtls.do", method=RequestMethod.POST)
	public String removeWrppCntrwkCtPymntDtls(@PathVariable("shtIdn") Long shtIdn, Model model) {
		model.addAttribute("rowCount", wrppCntrwkRegstrService.removeWrppCntrwkCtPymntDtls(shtIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addWrppCntrwkCtPymntDtlsPage
	/// @brief 함수 간략한 설명 : 상수 공사비 지급내역 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttCostDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addWrppCntrwkCtPymntDtlsPage.do", method=RequestMethod.GET)
	public String addWrppCntrwkCtPymntDtlsPage(WttCostDt wttCostDt, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0106");
		model.addAttribute("payCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/sc/job/cntrwkRegstr/wrpp/addWrppCntrwkCtPymntDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn addWrppCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 상수 공사비 지급내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttCostDt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/addWrppCntrwkCtPymntDtls.do", method=RequestMethod.POST)
	public String addWrppCntrwkCtPymntDtls(@PathVariable("ftrIdn") Long ftrIdn, WttCostDt wttCostDt, Model model) throws FdlException {
		wttCostDt.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", wrppCntrwkRegstrService.addWrppCntrwkCtPymntDtls(wttCostDt));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneWrppDsgnChangeDtlsPage
	/// @brief 함수 간략한 설명 : 상수 설계 변경 내역 조회 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntrwkRegstrDTO
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{shtIdn}/selectOneWrppDsgnChangeDtlsPage.do", method=RequestMethod.GET)
	public String selectOneWrppDsgnChangeDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		model.addAttribute("result", wrppCntrwkRegstrService.selectOneWrppDsgnChangeDtls(cntrwkRegstrDTO));
		return "/projects/sc/job/cntrwkRegstr/wrpp/viewWrppDsgnChangeDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyWrppDsgnChangeDtlsPage
	/// @brief 함수 간략한 설명 : 상수 설계 변경 내역 수정 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifyWrppDsgnChangeDtlsPage.do", method=RequestMethod.GET)
	public String modifyWrppDsgnChangeDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		model.addAttribute("result", wrppCntrwkRegstrService.selectOneWrppDsgnChangeDtls(cntrwkRegstrDTO));
		return "/projects/sc/job/cntrwkRegstr/wrpp/modifyWrppDsgnChangeDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyWrppDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 상수 설계 변경 내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @param wttChngDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifyWrppDsgnChangeDtls.do", method=RequestMethod.POST)
	public String modifyWrppDsgnChangeDtls(WttChngDt wttChngDt, Model model) {
		model.addAttribute("rowCount", wrppCntrwkRegstrService.modifyWrppDsgnChangeDtls(wttChngDt));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn addWrppDsgnChangeDtlsPage
	/// @brief 함수 간략한 설명 : 상수 설계 변경 내역 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttChngDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addWrppDsgnChangeDtlsPage.do", method=RequestMethod.GET)
	public String addWrppDsgnChangeDtlsPage(WttChngDt wttChngDt, Model model) {
		return "/projects/sc/job/cntrwkRegstr/wrpp/addWrppDsgnChangeDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn addWrppDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 상수 설계 변경 내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttChngDt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/addWrppDsgnChangeDtls.do", method=RequestMethod.POST)
	public String addWrppDsgnChangeDtls(@PathVariable("ftrIdn") Long ftrIdn, WttChngDt wttChngDt, Model model) throws FdlException {
		wttChngDt.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", wrppCntrwkRegstrService.addWrppDsgnChangeDtls(wttChngDt));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn removeWrppDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 상수 설계 변경 내역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{shtIdn}/removeWrppDsgnChangeDtls.do", method=RequestMethod.POST)
	public String removeWrppDsgnChangeDtls(@PathVariable("shtIdn") Long shtIdn, Model model) {
		model.addAttribute("rowCount", wrppCntrwkRegstrService.removeWrppDsgnChangeDtls(shtIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneWrppSubcntrDtlsPage
	/// @brief 함수 간략한 설명 : 상수 하도급 내역 조회 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cntrwkRegstrDTO
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{shtIdn}/selectOneWrppSubcntrDtlsPage.do", method=RequestMethod.GET)
	public String selectOneWrppSubcntrDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		model.addAttribute("result", wrppCntrwkRegstrService.selectOneWrppSubcntrDtls(cntrwkRegstrDTO));
		return "/projects/sc/job/cntrwkRegstr/wrpp/viewWrppSubcntrDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyWrppSubcntrDtlsPage
	/// @brief 함수 간략한 설명 : 상수 하도급 내역 수정 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifyWrppSubcntrDtlsPage.do", method=RequestMethod.GET)
	public String modifyWrppSubcntrDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		model.addAttribute("result", wrppCntrwkRegstrService.selectOneWrppSubcntrDtls(cntrwkRegstrDTO));
		return "/projects/sc/job/cntrwkRegstr/wrpp/modifyWrppSubcntrDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyWrppSubcntrDtls
	/// @brief 함수 간략한 설명 : 상수 하도급 내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @param wttSubcDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifyWrppSubcntrDtls.do", method=RequestMethod.POST)
	public String modifyWrppSubcntrDtls(WttSubcDt wttSubcDt, Model model) {
		model.addAttribute("rowCount", wrppCntrwkRegstrService.modifyWrppSubcntrDtls(wttSubcDt));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn addWrppSubcntrDtlsPage
	/// @brief 함수 간략한 설명 : 상수 하도급 내역 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttSubcDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addWrppSubcntrDtlsPage.do", method=RequestMethod.GET)
	public String addWrppSubcntrDtlsPage(WttSubcDt wttSubcDt, Model model) {
		return "/projects/sc/job/cntrwkRegstr/wrpp/addWrppSubcntrDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn addWrppSubcntrDtls
	/// @brief 함수 간략한 설명 : 상수 하도급 내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttSubcDt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/addWrppSubcntrDtls.do", method=RequestMethod.POST)
	public String addWrppSubcntrDtls(@PathVariable("ftrIdn") Long ftrIdn, WttSubcDt wttSubcDt, Model model) throws FdlException {
		wttSubcDt.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", wrppCntrwkRegstrService.addWrppSubcntrDtls(wttSubcDt));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn removeWrppSubcntrDtls
	/// @brief 함수 간략한 설명 : 상수 하도급 내역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{shtIdn}/removeWrppSubcntrDtls.do", method=RequestMethod.POST)
	public String removeWrppSubcntrDtls(@PathVariable("shtIdn") Long shtIdn, Model model) {
		model.addAttribute("rowCount", wrppCntrwkRegstrService.removeWrppSubcntrDtls(shtIdn));
		return "jsonView";
	}
		
	/////////////////////////////////////////////
	/// @fn addWrppCntrwkRegstrPhotoPage
	/// @brief 함수 간략한 설명 : 상수 공사대장 사진 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtConsMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addWrppCntrwkRegstrPhotoPage.do", method=RequestMethod.GET)
	public String addWrppCntrwkRegstrPhotoPage(KwsImage kwsImage, Model model) {
		return "/projects/sc/job/cntrwkRegstr/wrpp/addWrppCntrwkRegstrPhoto";
	}
	
	/////////////////////////////////////////////
	/// @fn addWrppCntrwkRegstrPhoto
	/// @brief 함수 간략한 설명 : 상수 공사대장 사진 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsImage
	/// @param multipartRequest
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/addWrppCntrwkRegstrPhoto.do", method=RequestMethod.POST)
	public String addWrppCntrwkRegstrPhoto(@PathVariable("ftrIdn") Long ftrIdn, KwsImage kwsImage, MultipartRequest multipartRequest, Model model) throws Exception {
		kwsImage.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", wrppCntrwkRegstrService.addWrppCntrwkRegstrPhoto(kwsImage, multipartRequest));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneWrppCntrwkRegstrPhotoPage
	/// @brief 함수 간략한 설명 : 상수 공사사진 상세조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param imageNo
	/// @param kwsImage
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{imageNo}/selectOneWrppCntrwkRegstrPhotoPage.do", method=RequestMethod.GET)
	public String selectOneWrppCntrwkRegstrPhotoPage(@PathVariable("imageNo") Long imageNo, KwsImage kwsImage, Model model) {
		model.addAttribute("result", imageService.selectOneImage(imageNo));
		return "/projects/sc/job/cntrwkRegstr/wrpp/viewWrppCntrwkRegstrPhoto";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyWrppCntrwkRegstrPhotoPage
	/// @brief 함수 간략한 설명 : 상수 공사대장 사진 수정 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param imageNo
	/// @param kwsImage
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{imageNo}/modifyWrppCntrwkRegstrPhotoPage.do", method=RequestMethod.GET)
	public String modifyWrppCntrwkRegstrPhotoPage(@PathVariable("imageNo") Long imageNo, KwsImage kwsImage, Model model) {
		model.addAttribute("result", imageService.selectOneImage(imageNo));
		return "/projects/sc/job/cntrwkRegstr/wrpp/modifyWrppCntrwkRegstrPhoto";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyWrppCntrwkRegstrPhoto
	/// @brief 함수 간략한 설명 : 상수 공사대장 사진 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param imageNo
	/// @param kwsImage
	/// @param multipartRequest
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{imageNo}/modifyWrppCntrwkRegstrPhoto.do", method=RequestMethod.POST)
	public String modifyWrppCntrwkRegstrPhoto(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("imageNo") Long imageNo, KwsImage kwsImage, MultipartRequest multipartRequest, Model model) throws Exception {
		kwsImage.setFtrIdn(ftrIdn);
		kwsImage.setImageNo(imageNo);
		model.addAttribute("rowCount", wrppCntrwkRegstrService.modifyWrppCntrwkRegstrPhoto(kwsImage, multipartRequest));
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
		model.addAttribute("wkts", wrppCntrwkRegstrService.searchSpatial(ftrIdn));
		return "jsonView";
	}
}
