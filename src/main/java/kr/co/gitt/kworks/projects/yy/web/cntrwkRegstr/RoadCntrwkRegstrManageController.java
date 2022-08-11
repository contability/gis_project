package kr.co.gitt.kworks.projects.yy.web.cntrwkRegstr;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.projects.yy.dto.CntrwkRegstrDTO;
import kr.co.gitt.kworks.projects.yy.model.RdtChngDt;
import kr.co.gitt.kworks.projects.yy.model.RdtConsMa;
import kr.co.gitt.kworks.projects.yy.model.RdtCostDt;
import kr.co.gitt.kworks.projects.yy.model.RdtFlawDt;
import kr.co.gitt.kworks.projects.yy.model.RdtSubcDt;
import kr.co.gitt.kworks.projects.yy.service.cntrwkRegstr.RoadCntrwkRegstrService;
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
/// @class RoadCntrwkRegstrManageController
/// kr.co.gitt.kworks.projects.yy.web.cntrwkRegstr \n
///   ㄴ RoadCntrwkRegstrManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 17. 오후 3:57:12 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 도로공사대장 컨트롤러 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/cntrwkRegstr/road/")
@Profile({"yy_dev", "yy_oper"})
public class RoadCntrwkRegstrManageController {
	
	// logger
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	// 공사대장 서비스
	@Resource
	RoadCntrwkRegstrService roadCntrwkRegstrService;
	
	// 이미지 서비스
	@Resource
	ImageService imageService;
	
	/////////////////////////////////////////////
	/// @fn pageRoadCntrwkRegstr
	/// @brief 함수 간략한 설명 : 도로 공사대장 검색 페이지
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
	@RequestMapping(value="searchRoadCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String searchRoadCntrwkRegstrPage(RdtConsMa rdtConsMa, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0054");
		model.addAttribute("wrkCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/yy/job/cntrwkRegstr/road/listRoadCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn listRoadCntrwkRegstr
	/// @brief 함수 간략한 설명 : 도로 공사대장 리스트 검색
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
	@RequestMapping(value="listRoadCntrwkRegstr.do", method=RequestMethod.GET)
	public String listRoadCntrwkRegstr(RdtConsMa rdtConsMa, Model model) {
		model.addAttribute("result", roadCntrwkRegstrService.listRoadCntrwkRegstr(rdtConsMa));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneRoadCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 도로 공사대장 단 건 조회
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
	@RequestMapping(value="{ftrIdn}/selectOneRoadCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String selectOneRoadCntrwkRegstrPage(@PathVariable("ftrIdn") Long ftrIdn, RdtFlawDt rdtFlawDt, RdtCostDt rdtCostDt, RdtChngDt rdtChngDt, RdtSubcDt rdtSubcDt, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0054");
		model.addAttribute("wrkCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		kwsDomnCode.setDomnId("KWS-0137");
		model.addAttribute("cttCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		kwsDomnCode.setDomnId("KWS-0007");
		model.addAttribute("payCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		RdtConsMa rdtConsMa = roadCntrwkRegstrService.selectOneRoadCntrwkRegstr(ftrIdn);
		
		model.addAttribute("result", rdtConsMa);
		model.addAttribute("roadFlawMendngDtls", roadCntrwkRegstrService.listRoadFlawMendngDtls(rdtFlawDt));
		model.addAttribute("roadCntrwkCtPymntDtls", roadCntrwkRegstrService.listRoadCntrwkCtPymntDtls(rdtCostDt));
		model.addAttribute("roadDsgnChangeDtls", roadCntrwkRegstrService.listRoadDsgnChangeDtls(rdtChngDt));
		model.addAttribute("roadSubcntrDtls", roadCntrwkRegstrService.listRoadSubcntrDtls(rdtSubcDt));

		KwsImage kwsImage = new KwsImage();
		kwsImage.setFtrIdn(ftrIdn);
		kwsImage.setFtrCde(rdtConsMa.getFtrCde());
		model.addAttribute("resultPhoto", imageService.listAllImage(kwsImage));
		
		return "/projects/yy/job/cntrwkRegstr/road/viewRoadCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn addRoadCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 도로 공사대장 수정 페이지
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
	@RequestMapping(value="{ftrIdn}/modifyRoadCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String modifyRoadCntrwkRegstrPage(@PathVariable("ftrIdn") Long ftrIdn, RdtFlawDt rdtFlawDt, RdtCostDt rdtCostDt, RdtChngDt rdtChngDt, RdtSubcDt rdtSubcDt, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0054");
		model.addAttribute("wrkCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		kwsDomnCode.setDomnId("KWS-0137");
		model.addAttribute("cttCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		kwsDomnCode.setDomnId("KWS-0007");
		model.addAttribute("payCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("result",roadCntrwkRegstrService.selectOneRoadCntrwkRegstr(ftrIdn));
		model.addAttribute("roadFlawMendngDtls", roadCntrwkRegstrService.listRoadFlawMendngDtls(rdtFlawDt));
		model.addAttribute("roadCntrwkCtPymntDtls", roadCntrwkRegstrService.listRoadCntrwkCtPymntDtls(rdtCostDt));
		model.addAttribute("roadDsgnChangeDtls", roadCntrwkRegstrService.listRoadDsgnChangeDtls(rdtChngDt));
		model.addAttribute("roadSubcntrDtls", roadCntrwkRegstrService.listRoadSubcntrDtls(rdtSubcDt));
		
		KwsImage kwsImage = new KwsImage();
		kwsImage.setFtrIdn(ftrIdn);
		model.addAttribute("resultPhoto", imageService.listAllImage(kwsImage));
		
		return "/projects/yy/job/cntrwkRegstr/road/modifyRoadCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyRoadCntrwkRegstr
	/// @brief 함수 간략한 설명 : 도로 공사대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param rdtConsMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/modifyRoadCntrwkRegstr.do", method=RequestMethod.POST)
	public String modifyRoadCntrwkRegstr(@PathVariable("ftrIdn") Long ftrIdn, RdtConsMa rdtConsMa, Model model) {
		rdtConsMa.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", roadCntrwkRegstrService.modifyRoadCntrwkRegstr(rdtConsMa));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn addRoadCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 도로 공사대장 등록 페이지
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
	@RequestMapping(value="addRoadCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String addRoadCntrwkRegstrPage(RdtConsMa rdtConsMa, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0054");
		model.addAttribute("wrkCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		kwsDomnCode.setDomnId("KWS-0137");
		model.addAttribute("cttCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		kwsDomnCode.setDomnId("KWS-0007");
		model.addAttribute("payCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/yy/job/cntrwkRegstr/road/addRoadCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn addRoadCntrwkRegstr
	/// @brief 함수 간략한 설명 : 도로 공사대장 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtConsMa
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addRoadCntrwkRegstr.do", method=RequestMethod.POST)
	public String addRoadCntrwkRegstr(RdtConsMa rdtConsMa, Model model) throws FdlException {
		model.addAttribute("rowCount", roadCntrwkRegstrService.addRoadCntrwkRegstr(rdtConsMa));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyRoadFlawMendngDtlsPage
	/// @brief 함수 간략한 설명 : 도로 하자 보수 내역 조회 페이지
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
	@RequestMapping(value="{ftrIdn}/{shtIdn}/selectOneRoadFlawMendngDtlsPage.do", method=RequestMethod.GET)
	public String selectOneRoadFlawMendngDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		model.addAttribute("result",roadCntrwkRegstrService.selectOneRoadFlawMendngDtls(cntrwkRegstrDTO));
		return "/projects/yy/job/cntrwkRegstr/road/viewRoadFlawMendngDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyRoadFlawMendngDtlsPage
	/// @brief 함수 간략한 설명 : 도로 하자 보수 내역 수정 페이지
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
	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifyRoadFlawMendngDtlsPage.do", method=RequestMethod.GET)
	public String modifyRoadFlawMendngDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		model.addAttribute("result",roadCntrwkRegstrService.selectOneRoadFlawMendngDtls(cntrwkRegstrDTO));
		return "/projects/yy/job/cntrwkRegstr/road/modifyRoadFlawMendngDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyRoadFlawMendngDtls
	/// @brief 함수 간략한 설명 : 도로 하자보수내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @param rdtFlawDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifyRoadFlawMendngDtls.do", method=RequestMethod.POST)
	public String modifyRoadFlawMendngDtls(RdtFlawDt rdtFlawDt, Model model) {
		model.addAttribute("rowCount", roadCntrwkRegstrService.modifyRoadFlawMendngDtls(rdtFlawDt));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn addRoadFlawMendngDtlsPage
	/// @brief 함수 간략한 설명 : 도로 하자보수내역 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtFlawDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addRoadFlawMendngDtlsPage.do", method=RequestMethod.GET)
	public String addRoadFlawMendngDtlsPage(RdtFlawDt rdtFlawDt, Model model) {
		return "/projects/yy/job/cntrwkRegstr/road/addRoadFlawMendngDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn addRoadFlawMendngDtls
	/// @brief 함수 간략한 설명 : 도로 하자보수내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtFlawDt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/addRoadFlawMendngDtls.do", method=RequestMethod.POST)
	public String addRoadFlawMendngDtls(@PathVariable("ftrIdn") Long ftrIdn, RdtFlawDt rdtFlawDt, Model model) throws FdlException {
		rdtFlawDt.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", roadCntrwkRegstrService.addRoadFlawMendngDtls(rdtFlawDt));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn removeRoadFlawMendngDtls
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
	@RequestMapping(value="{shtIdn}/removeRoadFlawMendngDtls.do", method=RequestMethod.POST)
	public String removeRoadFlawMendngDtls(@PathVariable("shtIdn") Long shtIdn, Model model) {
		model.addAttribute("rowCount", roadCntrwkRegstrService.removeRoadFlawMendngDtls(shtIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneRoadCntrwkCtPymntDtlsPage
	/// @brief 함수 간략한 설명 : 도로 공사비 지급내역 조회 페이지
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
	@RequestMapping(value="{ftrIdn}/{shtIdn}/selectOneRoadCntrwkCtPymntDtlsPage.do", method=RequestMethod.GET)
	public String selectOneRoadCntrwkCtPymntDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0007");
		model.addAttribute("payCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("result",roadCntrwkRegstrService.selectOneRoadCntrwkCtPymntDtls(cntrwkRegstrDTO));
		return "/projects/yy/job/cntrwkRegstr/road/viewRoadCntrwkCtPymntDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyRoadCntrwkCtPymntDtlsPage
	/// @brief 함수 간략한 설명 : 도로 공사비 지급내역 수정 페이지 
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
	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifyRoadCntrwkCtPymntDtlsPage.do", method=RequestMethod.GET)
	public String modifyRoadCntrwkCtPymntDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
	
		kwsDomnCode.setDomnId("KWS-0007");
		model.addAttribute("payCdeList", domnCodeService.listDomnCode(kwsDomnCode));
	
		model.addAttribute("result",roadCntrwkRegstrService.selectOneRoadCntrwkCtPymntDtls(cntrwkRegstrDTO));
		return "/projects/yy/job/cntrwkRegstr/road/modifyRoadCntrwkCtPymntDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyRoadCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 도로 공사비 지급내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @param rdtCostDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifyRoadCntrwkCtPymntDtls.do", method=RequestMethod.POST)
	public String modifyRoadCntrwkCtPymntDtls(RdtCostDt rdtCostDt, Model model) {
		model.addAttribute("rowCount", roadCntrwkRegstrService.modifyRoadCntrwkCtPymntDtls(rdtCostDt));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn removeRoadCntrwkCtPymntDtls
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
	@RequestMapping(value="{shtIdn}/removeRoadCntrwkCtPymntDtls.do", method=RequestMethod.POST)
	public String removeRoadCntrwkCtPymntDtls(@PathVariable("shtIdn") Long shtIdn, Model model) {
		model.addAttribute("rowCount", roadCntrwkRegstrService.removeRoadCntrwkCtPymntDtls(shtIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addRoadCntrwkCtPymntDtlsPage
	/// @brief 함수 간략한 설명 : 도로 공사비 지급내역 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtCostDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addRoadCntrwkCtPymntDtlsPage.do", method=RequestMethod.GET)
	public String addRoadCntrwkCtPymntDtlsPage(RdtCostDt rdtCostDt, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0007");
		model.addAttribute("payCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/yy/job/cntrwkRegstr/road/addRoadCntrwkCtPymntDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn addRoadCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 도로 공사비 지급내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtCostDt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/addRoadCntrwkCtPymntDtls.do", method=RequestMethod.POST)
	public String addRoadCntrwkCtPymntDtls(@PathVariable("ftrIdn") Long ftrIdn, RdtCostDt rdtCostDt, Model model) throws FdlException {
		rdtCostDt.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", roadCntrwkRegstrService.addRoadCntrwkCtPymntDtls(rdtCostDt));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneRoadDsgnChangeDtlsPage
	/// @brief 함수 간략한 설명 : 도로 설계 변경 내역 조회 페이지
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
	@RequestMapping(value="{ftrIdn}/{shtIdn}/selectOneRoadDsgnChangeDtlsPage.do", method=RequestMethod.GET)
	public String selectOneRoadDsgnChangeDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		model.addAttribute("result",roadCntrwkRegstrService.selectOneRoadDsgnChangeDtls(cntrwkRegstrDTO));
		return "/projects/yy/job/cntrwkRegstr/road/viewRoadDsgnChangeDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyRoadDsgnChangeDtlsPage
	/// @brief 함수 간략한 설명 : 도로 설계 변경 내역 수정 페이지
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
	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifyRoadDsgnChangeDtlsPage.do", method=RequestMethod.GET)
	public String modifyRoadDsgnChangeDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		model.addAttribute("result",roadCntrwkRegstrService.selectOneRoadDsgnChangeDtls(cntrwkRegstrDTO));
		return "/projects/yy/job/cntrwkRegstr/road/modifyRoadDsgnChangeDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyRoadDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 도로 설계 변경 내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @param rdtChngDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifyRoadDsgnChangeDtls.do", method=RequestMethod.POST)
	public String modifyRoadDsgnChangeDtls(RdtChngDt rdtChngDt, Model model) {
		model.addAttribute("rowCount", roadCntrwkRegstrService.modifyRoadDsgnChangeDtls(rdtChngDt));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn addRoadDsgnChangeDtlsPage
	/// @brief 함수 간략한 설명 : 도로 설계 변경 내역 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtChngDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addRoadDsgnChangeDtlsPage.do", method=RequestMethod.GET)
	public String addRoadDsgnChangeDtlsPage(RdtChngDt rdtChngDt, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// domn code 추가 필요
		
		return "/projects/yy/job/cntrwkRegstr/road/addRoadDsgnChangeDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn addRoadDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 도로 설계 변경 내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtChngDt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/addRoadDsgnChangeDtls.do", method=RequestMethod.POST)
	public String addRoadDsgnChangeDtls(@PathVariable("ftrIdn") Long ftrIdn, RdtChngDt rdtChngDt, Model model) throws FdlException {
		rdtChngDt.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", roadCntrwkRegstrService.addRoadDsgnChangeDtls(rdtChngDt));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn removeRoadDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 도로 설계 변경 내역 삭제
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
	@RequestMapping(value="{shtIdn}/removeRoadDsgnChangeDtls.do", method=RequestMethod.POST)
	public String removeRoadDsgnChangeDtls(@PathVariable("shtIdn") Long shtIdn, Model model) {
		model.addAttribute("rowCount", roadCntrwkRegstrService.removeRoadDsgnChangeDtls(shtIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneRoadSubcntrDtlsPage
	/// @brief 함수 간략한 설명 : 도로 하도급 내역 조회 페이지
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
	@RequestMapping(value="{ftrIdn}/{shtIdn}/selectOneRoadSubcntrDtlsPage.do", method=RequestMethod.GET)
	public String selectOneRoadSubcntrDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		model.addAttribute("result",roadCntrwkRegstrService.selectOneRoadSubcntrDtls(cntrwkRegstrDTO));
		return "/projects/yy/job/cntrwkRegstr/road/viewRoadSubcntrDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyRoadSubcntrDtlsPage
	/// @brief 함수 간략한 설명 : 도로 하도급 내역 수정 페이지
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
	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifyRoadSubcntrDtlsPage.do", method=RequestMethod.GET)
	public String modifyRoadSubcntrDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		model.addAttribute("result",roadCntrwkRegstrService.selectOneRoadSubcntrDtls(cntrwkRegstrDTO));
		return "/projects/yy/job/cntrwkRegstr/road/modifyRoadSubcntrDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyRoadSubcntrDtls
	/// @brief 함수 간략한 설명 : 도로 하도급 내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @param rdtSubcDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifyRoadSubcntrDtls.do", method=RequestMethod.POST)
	public String modifyRoadSubcntrDtls(RdtSubcDt rdtSubcDt, Model model) {
		model.addAttribute("rowCount", roadCntrwkRegstrService.modifyRoadSubcntrDtls(rdtSubcDt));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn addRoadSubcntrDtlsPage
	/// @brief 함수 간략한 설명 : 도로 하도급 내역 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtSubcDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addRoadSubcntrDtlsPage.do", method=RequestMethod.GET)
	public String addRoadSubcntrDtlsPage(RdtSubcDt rdtSubcDt, Model model) {
		return "/projects/yy/job/cntrwkRegstr/road/addRoadSubcntrDtls";
	}
	
	/////////////////////////////////////////////
	/// @fn addRoadSubcntrDtls
	/// @brief 함수 간략한 설명 : 도로 하도급 내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtSubcDt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/addRoadSubcntrDtls.do", method=RequestMethod.POST)
	public String addRoadSubcntrDtls(@PathVariable("ftrIdn") Long ftrIdn, RdtSubcDt rdtSubcDt, Model model) throws FdlException {
		rdtSubcDt.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", roadCntrwkRegstrService.addRoadSubcntrDtls(rdtSubcDt));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn removeRoadSubcntrDtls
	/// @brief 함수 간략한 설명 : 도로 하도급 내역 삭제
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
	@RequestMapping(value="{shtIdn}/removeRoadSubcntrDtls.do", method=RequestMethod.POST)
	public String removeRoadSubcntrDtls(@PathVariable("shtIdn") Long shtIdn, Model model) {
		model.addAttribute("rowCount", roadCntrwkRegstrService.removeRoadSubcntrDtls(shtIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addRoadCntrwkRegstrPhotoPage
	/// @brief 함수 간략한 설명 : 도로 공사대장 사진 등록 페이지
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
	@RequestMapping(value="addRoadCntrwkRegstrPhotoPage.do", method=RequestMethod.GET)
	public String addRoadCntrwkRegstrPhotoPage(KwsImage kwsImage, Model model) {
		return "/projects/yy/job/cntrwkRegstr/road/addRoadCntrwkRegstrPhoto";
	}
	
	/////////////////////////////////////////////
	/// @fn addRoadCntrwkRegstrPhoto
	/// @brief 함수 간략한 설명 : 도로 공사대장 사진 등록
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
	@RequestMapping(value="{ftrIdn}/addRoadCntrwkRegstrPhoto.do", method=RequestMethod.POST)
	public String addRoadCntrwkRegstrPhoto(@PathVariable("ftrIdn") Long ftrIdn, KwsImage kwsImage, MultipartRequest multipartRequest, Model model) throws Exception {
		kwsImage.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", roadCntrwkRegstrService.addRoadCntrwkRegstrPhoto(kwsImage, multipartRequest));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneRoadCntrwkRegstrPhotoPage
	/// @brief 함수 간략한 설명 : 도로 공사사진 상세조회
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
	@RequestMapping(value="{imageNo}/selectOneRoadCntrwkRegstrPhotoPage.do", method=RequestMethod.GET)
	public String selectOneRoadCntrwkRegstrPhotoPage(@PathVariable("imageNo") Long imageNo, KwsImage kwsImage, Model model) {
		model.addAttribute("result", imageService.selectOneImage(imageNo));
		return "/projects/yy/job/cntrwkRegstr/road/viewRoadCntrwkRegstrPhoto";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyRoadCntrwkRegstrPhotoPage
	/// @brief 함수 간략한 설명 : 도로 공사대장 사진 수정 페이지
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
	@RequestMapping(value="{imageNo}/modifyRoadCntrwkRegstrPhotoPage.do", method=RequestMethod.GET)
	public String modifyRoadCntrwkRegstrPhotoPage(@PathVariable("imageNo") Long imageNo, KwsImage kwsImage, Model model) {
		model.addAttribute("result", imageService.selectOneImage(imageNo));
		return "/projects/yy/job/cntrwkRegstr/road/modifyRoadCntrwkRegstrPhoto";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyRoadCntrwkRegstrPhoto
	/// @brief 함수 간략한 설명 : 도로 공사대장 사진 수정
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
	@RequestMapping(value="{ftrIdn}/{imageNo}/modifyRoadCntrwkRegstrPhoto.do", method=RequestMethod.POST)
	public String modifyRoadCntrwkRegstrPhoto(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("imageNo") Long imageNo, KwsImage kwsImage, MultipartRequest multipartRequest, Model model) throws Exception {
		kwsImage.setFtrIdn(ftrIdn);
		kwsImage.setImageNo(imageNo);
		model.addAttribute("rowCount", roadCntrwkRegstrService.modifyRoadCntrwkRegstrPhoto(kwsImage, multipartRequest));
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
	model.addAttribute("wkts", roadCntrwkRegstrService.searchSpatial(ftrIdn));
	return "jsonView";
}
}
