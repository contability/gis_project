package kr.co.gitt.kworks.projects.gn.web.cntrwkRegstr;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.model.KwsImage;
/*import kr.co.gitt.kworks.projects.gn.dto.CntrwkRegstrDTO;*/
import kr.co.gitt.kworks.projects.gn.model.LwtDeptMa;
import kr.co.gitt.kworks.projects.gn.service.cntrwkRegstr.LwlCntrwkRegstrService;
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
/// @class SwgeCntrwkRegstrManageController
/// kr.co.gitt.kworks.projects.gn.web.cntrwkRegstr \n
///   ㄴ SwgeCntrwkRegstrManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 9. 오후 5:00:17 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 침출수 공사대장 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/cntrwkRegstr/lwl/")
@Profile({"gn_dev", "gn_oper", "gds_dev", "gds_oper"})
public class LwlCntrwkRegstrManageController {
	
	// logger
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	// 공사대장 서비스
	@Resource
	LwlCntrwkRegstrService lwlCntrwkRegstrService;
	
	// 이미지 서비스
	@Resource
	ImageService imageService;
	
	/////////////////////////////////////////////
	/// @fn pageSwgeCntrwkRegstr
	/// @brief 함수 간략한 설명 : 침출수 공사대장 검색 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtConsMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="searchLwlCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String searchLwlCntrwkRegstrPage(LwtDeptMa lwtDeptMa, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0054");
		model.addAttribute("wrkCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/gn/job/cntrwkRegstr/lwl/listLwlCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn listSwgeCntrwkRegstr
	/// @brief 함수 간략한 설명 : 침출수 공사대장 리스트 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtConsMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listLwlCntrwkRegstr.do", method=RequestMethod.GET)
	public String listLwlCntrwkRegstr(LwtDeptMa lwtDeptMa, Model model) {
		model.addAttribute("result", lwlCntrwkRegstrService.listLwlCntrwkRegstr(lwtDeptMa));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneSwgeCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 침출수 공사대장 단 건 조회
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
	@RequestMapping(value="{ftrIdn}/selectOneLwlCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String selectOneLwlCntrwkRegstrPage(@PathVariable("ftrIdn") Long ftrIdn, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0054");
		model.addAttribute("wrkCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		kwsDomnCode.setDomnId("KWS-0137");
		model.addAttribute("cttCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		kwsDomnCode.setDomnId("KWS-0007");
		model.addAttribute("payCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		LwtDeptMa lwtDeptMa = lwlCntrwkRegstrService.selectOneLwlCntrwkRegstr(ftrIdn);
		
		model.addAttribute("result",lwtDeptMa);
/*		model.addAttribute("swgeFlawMendngDtls", swgeCntrwkRegstrService.listSwgeFlawMendngDtls(swtFlawDt));
		model.addAttribute("swgeCntrwkCtPymntDtls", swgeCntrwkRegstrService.listSwgeCntrwkCtPymntDtls(swtCostDt));
		model.addAttribute("swgeDsgnChangeDtls", swgeCntrwkRegstrService.listSwgeDsgnChangeDtls(swtChngDt));
		model.addAttribute("swgeSubcntrDtls", swgeCntrwkRegstrService.listSwgeSubcntrDtls(swtSubcDt));*/
		
		KwsImage kwsImage = new KwsImage();
		kwsImage.setFtrIdn(ftrIdn);
		kwsImage.setFtrCde(lwtDeptMa.getFtrCde());
		model.addAttribute("resultPhoto", imageService.listAllImage(kwsImage));

		return "/projects/gn/job/cntrwkRegstr/lwl/viewLwlCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn addSwgeCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 침출수 공사대장 수정 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtConsMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/modifyLwlCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String modifyLwlCntrwkRegstrPage(@PathVariable("ftrIdn") Long ftrIdn, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0054");
		model.addAttribute("wrkCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		kwsDomnCode.setDomnId("KWS-0137");
		model.addAttribute("cttCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		kwsDomnCode.setDomnId("KWS-0007");
		model.addAttribute("payCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("result",lwlCntrwkRegstrService.selectOneLwlCntrwkRegstr(ftrIdn));
/*		model.addAttribute("swgeFlawMendngDtls", swgeCntrwkRegstrService.listSwgeFlawMendngDtls(swtFlawDt));
		model.addAttribute("swgeCntrwkCtPymntDtls", swgeCntrwkRegstrService.listSwgeCntrwkCtPymntDtls(swtCostDt));
		model.addAttribute("swgeDsgnChangeDtls", swgeCntrwkRegstrService.listSwgeDsgnChangeDtls(swtChngDt));
		model.addAttribute("swgeSubcntrDtls", swgeCntrwkRegstrService.listSwgeSubcntrDtls(swtSubcDt));*/
		
		KwsImage kwsImage = new KwsImage();
		kwsImage.setFtrIdn(ftrIdn);
		model.addAttribute("resultPhoto", imageService.listAllImage(kwsImage));
		
		return "/projects/gn/job/cntrwkRegstr/lwl/modifyLwlCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn modifySwgeCntrwkRegstr
	/// @brief 함수 간략한 설명 : 침출수 공사대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param swtConsMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/modifyLwlCntrwkRegstr.do", method=RequestMethod.POST)
	public String modifyLwlCntrwkRegstr(@PathVariable("ftrIdn") Long ftrIdn, LwtDeptMa lwtDeptMa, Model model) {
		lwtDeptMa.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", lwlCntrwkRegstrService.modifyLwlCntrwkRegstr(lwtDeptMa));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn addSwgeCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 침출수 공사대장 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtConsMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addLwlCntrwkRegstrPage.do", method=RequestMethod.GET)
	public String addLwlCntrwkRegstrPage(LwtDeptMa lwtDeptMa, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0054");
		model.addAttribute("wrkCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		kwsDomnCode.setDomnId("KWS-0137");
		model.addAttribute("cttCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		kwsDomnCode.setDomnId("KWS-0007");
		model.addAttribute("payCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/gn/job/cntrwkRegstr/lwl/addLwlCntrwkRegstr";
	}
	
	/////////////////////////////////////////////
	/// @fn addSwgeCntrwkRegstr
	/// @brief 함수 간략한 설명 : 침출수 공사대장 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtConsMa
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addLwlCntrwkRegstr.do", method=RequestMethod.POST)
	public String addLwlCntrwkRegstr(LwtDeptMa lwtDeptMa, Model model) throws FdlException {
		model.addAttribute("rowCount", lwlCntrwkRegstrService.addLwlCntrwkRegstr(lwtDeptMa));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn modifySwgeFlawMendngDtlsPage
	/// @brief 함수 간략한 설명 : 침출수 하자 보수 내역 조회 페이지
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
/*	@RequestMapping(value="{ftrIdn}/{shtIdn}/selectOneLwlFlawMendngDtlsPage.do", method=RequestMethod.GET)
	public String selectOneLwlFlawMendngDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		model.addAttribute("result",lwlCntrwkRegstrService.selectOneLwlFlawMendngDtls(cntrwkRegstrDTO));
		return "/projects/gn/job/cntrwkRegstr/lwl/viewSwgeFlawMendngDtls";
	}*/
	
	/////////////////////////////////////////////
	/// @fn modifySwgeFlawMendngDtlsPage
	/// @brief 함수 간략한 설명 : 침출수 하자 보수 내역 수정 페이지
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
/*	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifySwgeFlawMendngDtlsPage.do", method=RequestMethod.GET)
	public String modifySwgeFlawMendngDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		model.addAttribute("result",swgeCntrwkRegstrService.selectOneSwgeFlawMendngDtls(cntrwkRegstrDTO));
		return "/projects/gn/job/cntrwkRegstr/swge/modifySwgeFlawMendngDtls";
	}
	*/
	/////////////////////////////////////////////
	/// @fn modifySwgeFlawMendngDtls
	/// @brief 함수 간략한 설명 : 침출수 하자보수내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @param swtFlawDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
/*	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifySwgeFlawMendngDtls.do", method=RequestMethod.POST)
	public String modifySwgeFlawMendngDtls(SwtFlawDt swtFlawDt, Model model) {
		model.addAttribute("rowCount", swgeCntrwkRegstrService.modifySwgeFlawMendngDtls(swtFlawDt));
		return "jsonViewTextPlain";
	}
	*/
	/////////////////////////////////////////////
	/// @fn addSwgeFlawMendngDtlsPage
	/// @brief 함수 간략한 설명 : 침출수 하자보수내역 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtFlawDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
/*	@RequestMapping(value="addSwgeFlawMendngDtlsPage.do", method=RequestMethod.GET)
	public String addSwgeFlawMendngDtlsPage(SwtFlawDt swtFlawDt, Model model) {
		return "/projects/gn/job/cntrwkRegstr/swge/addSwgeFlawMendngDtls";
	}
	*/
	/////////////////////////////////////////////
	/// @fn addSwgeFlawMendngDtls
	/// @brief 함수 간략한 설명 : 침출수 하자보수내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtFlawDt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
/*	@RequestMapping(value="{ftrIdn}/addSwgeFlawMendngDtls.do", method=RequestMethod.POST)
	public String addSwgeFlawMendngDtls(@PathVariable("ftrIdn") Long ftrIdn, SwtFlawDt swtFlawDt, Model model) throws FdlException {
		swtFlawDt.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", swgeCntrwkRegstrService.addSwgeFlawMendngDtls(swtFlawDt));
		return "jsonViewTextPlain";
	}*/
	
	/////////////////////////////////////////////
	/// @fn removeSwgeFlawMendngDtls
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
/*	@RequestMapping(value="{shtIdn}/removeSwgeFlawMendngDtls.do", method=RequestMethod.POST)
	public String removeSwgeFlawMendngDtls(@PathVariable("shtIdn") Long shtIdn, Model model) {
		model.addAttribute("rowCount", swgeCntrwkRegstrService.removeSwgeFlawMendngDtls(shtIdn));
		return "jsonView";
	}
	*/
	/////////////////////////////////////////////
	/// @fn selectOneSwgeCntrwkCtPymntDtlsPage
	/// @brief 함수 간략한 설명 : 침출수 공사비 지급내역 조회 페이지
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
/*	@RequestMapping(value="{ftrIdn}/{shtIdn}/selectOneSwgeCntrwkCtPymntDtlsPage.do", method=RequestMethod.GET)
	public String selectOneSwgeCntrwkCtPymntDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0007");
		model.addAttribute("payCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("result",swgeCntrwkRegstrService.selectOneSwgeCntrwkCtPymntDtls(cntrwkRegstrDTO));
		return "/projects/gn/job/cntrwkRegstr/swge/viewSwgeCntrwkCtPymntDtls";
	}*/
	
	/////////////////////////////////////////////
	/// @fn modifySwgeCntrwkCtPymntDtlsPage
	/// @brief 함수 간략한 설명 : 침출수 공사비 지급내역 수정 페이지 
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
/*	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifySwgeCntrwkCtPymntDtlsPage.do", method=RequestMethod.GET)
	public String modifySwgeCntrwkCtPymntDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
	
		kwsDomnCode.setDomnId("KWS-0007");
		model.addAttribute("payCdeList", domnCodeService.listDomnCode(kwsDomnCode));
	
		model.addAttribute("result",swgeCntrwkRegstrService.selectOneSwgeCntrwkCtPymntDtls(cntrwkRegstrDTO));
		return "/projects/gn/job/cntrwkRegstr/swge/modifySwgeCntrwkCtPymntDtls";
	}*/
	
	/////////////////////////////////////////////
	/// @fn modifySwgeCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 침출수 공사비 지급내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @param swtCostDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
/*	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifySwgeCntrwkCtPymntDtls.do", method=RequestMethod.POST)
	public String modifySwgeCntrwkCtPymntDtls(SwtCostDt swtCostDt, Model model) {
		model.addAttribute("rowCount", swgeCntrwkRegstrService.modifySwgeCntrwkCtPymntDtls(swtCostDt));
		return "jsonViewTextPlain";
	}*/
	
	/////////////////////////////////////////////
	/// @fn removeSwgeCntrwkCtPymntDtls
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
/*	@RequestMapping(value="{shtIdn}/removeSwgeCntrwkCtPymntDtls.do", method=RequestMethod.POST)
	public String removeSwgeCntrwkCtPymntDtls(@PathVariable("shtIdn") Long shtIdn, Model model) {
		model.addAttribute("rowCount", swgeCntrwkRegstrService.removeSwgeCntrwkCtPymntDtls(shtIdn));
		return "jsonView";
	}*/
	
	/////////////////////////////////////////////
	/// @fn addSwgeCntrwkCtPymntDtlsPage
	/// @brief 함수 간략한 설명 : 침출수 공사비 지급내역 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtCostDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
/*	@RequestMapping(value="addSwgeCntrwkCtPymntDtlsPage.do", method=RequestMethod.GET)
	public String addSwgeCntrwkCtPymntDtlsPage(SwtCostDt swtCostDt, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0007");
		model.addAttribute("payCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/gn/job/cntrwkRegstr/swge/addSwgeCntrwkCtPymntDtls";
	}
	*/
	/////////////////////////////////////////////
	/// @fn addSwgeCntrwkCtPymntDtls
	/// @brief 함수 간략한 설명 : 침출수 공사비 지급내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtCostDt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
/*	@RequestMapping(value="{ftrIdn}/addSwgeCntrwkCtPymntDtls.do", method=RequestMethod.POST)
	public String addSwgeCntrwkCtPymntDtls(@PathVariable("ftrIdn") Long ftrIdn, SwtCostDt swtCostDt, Model model) throws FdlException {
		swtCostDt.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", swgeCntrwkRegstrService.addSwgeCntrwkCtPymntDtls(swtCostDt));
		return "jsonViewTextPlain";
	}*/
	
	/////////////////////////////////////////////
	/// @fn selectOneSwgeDsgnChangeDtlsPage
	/// @brief 함수 간략한 설명 : 침출수 설계 변경 내역 조회 페이지
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
/*	@RequestMapping(value="{ftrIdn}/{shtIdn}/selectOneSwgeDsgnChangeDtlsPage.do", method=RequestMethod.GET)
	public String selectOneSwgeDsgnChangeDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		model.addAttribute("result",swgeCntrwkRegstrService.selectOneSwgeDsgnChangeDtls(cntrwkRegstrDTO));
		return "/projects/gn/job/cntrwkRegstr/swge/viewSwgeDsgnChangeDtls";
	}*/
	
	/////////////////////////////////////////////
	/// @fn modifySwgeDsgnChangeDtlsPage
	/// @brief 함수 간략한 설명 : 침출수 설계 변경 내역 수정 페이지
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
/*	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifySwgeDsgnChangeDtlsPage.do", method=RequestMethod.GET)
	public String modifySwgeDsgnChangeDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		model.addAttribute("result",swgeCntrwkRegstrService.selectOneSwgeDsgnChangeDtls(cntrwkRegstrDTO));
		return "/projects/gn/job/cntrwkRegstr/swge/modifySwgeDsgnChangeDtls";
	}*/
	
	/////////////////////////////////////////////
	/// @fn modifySwgeDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 침출수 설계 변경 내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @param swtChngDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
/*	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifySwgeDsgnChangeDtls.do", method=RequestMethod.POST)
	public String modifySwgeDsgnChangeDtls(SwtChngDt swtChngDt, Model model) {
		model.addAttribute("rowCount", swgeCntrwkRegstrService.modifySwgeDsgnChangeDtls(swtChngDt));
		return "jsonViewTextPlain";
	}*/
	
	/////////////////////////////////////////////
	/// @fn addSwgeDsgnChangeDtlsPage
	/// @brief 함수 간략한 설명 : 침출수 설계 변경 내역 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtChngDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
/*	@RequestMapping(value="addSwgeDsgnChangeDtlsPage.do", method=RequestMethod.GET)
	public String addSwgeDsgnChangeDtlsPage(SwtChngDt swtChngDt, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// domn code 추가 필요
		
		return "/projects/gn/job/cntrwkRegstr/swge/addSwgeDsgnChangeDtls";
	}*/
	
	/////////////////////////////////////////////
	/// @fn addSwgeDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 침출수 설계 변경 내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtChngDt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
/*	@RequestMapping(value="{ftrIdn}/addSwgeDsgnChangeDtls.do", method=RequestMethod.POST)
	public String addSwgeDsgnChangeDtls(@PathVariable("ftrIdn") Long ftrIdn, SwtChngDt swtChngDt, Model model) throws FdlException {
		swtChngDt.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", swgeCntrwkRegstrService.addSwgeDsgnChangeDtls(swtChngDt));
		return "jsonViewTextPlain";
	}*/
	
	/////////////////////////////////////////////
	/// @fn removeSwgeDsgnChangeDtls
	/// @brief 함수 간략한 설명 : 침출수 설계 변경 내역 삭제
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
/*	@RequestMapping(value="{shtIdn}/removeSwgeDsgnChangeDtls.do", method=RequestMethod.POST)
	public String removeSwgeDsgnChangeDtls(@PathVariable("shtIdn") Long shtIdn, Model model) {
		model.addAttribute("rowCount", swgeCntrwkRegstrService.removeSwgeDsgnChangeDtls(shtIdn));
		return "jsonView";
	}*/
	
	/////////////////////////////////////////////
	/// @fn selectOneSwgeSubcntrDtlsPage
	/// @brief 함수 간략한 설명 : 침출수 하도급 내역 조회 페이지
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
/*	@RequestMapping(value="{ftrIdn}/{shtIdn}/selectOneSwgeSubcntrDtlsPage.do", method=RequestMethod.GET)
	public String selectOneSwgeSubcntrDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		model.addAttribute("result",swgeCntrwkRegstrService.selectOneSwgeSubcntrDtls(cntrwkRegstrDTO));
		return "/projects/gn/job/cntrwkRegstr/swge/viewSwgeSubcntrDtls";
	}*/
	
	/////////////////////////////////////////////
	/// @fn modifySwgeSubcntrDtlsPage
	/// @brief 함수 간략한 설명 : 침출수 하도급 내역 수정 페이지
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
/*	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifySwgeSubcntrDtlsPage.do", method=RequestMethod.GET)
	public String modifySwgeSubcntrDtlsPage(CntrwkRegstrDTO cntrwkRegstrDTO, Model model) {
		model.addAttribute("result",swgeCntrwkRegstrService.selectOneSwgeSubcntrDtls(cntrwkRegstrDTO));
		return "/projects/gn/job/cntrwkRegstr/swge/modifySwgeSubcntrDtls";
	}*/
	
	/////////////////////////////////////////////
	/// @fn modifySwgeSubcntrDtls
	/// @brief 함수 간략한 설명 : 침출수 하도급 내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @param swtSubcDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
/*	@RequestMapping(value="{ftrIdn}/{shtIdn}/modifySwgeSubcntrDtls.do", method=RequestMethod.POST)
	public String modifySwgeSubcntrDtls(SwtSubcDt swtSubcDt, Model model) {
		model.addAttribute("rowCount", swgeCntrwkRegstrService.modifySwgeSubcntrDtls(swtSubcDt));
		return "jsonViewTextPlain";
	}*/
	
	/////////////////////////////////////////////
	/// @fn addSwgeSubcntrDtlsPage
	/// @brief 함수 간략한 설명 : 침출수 하도급 내역 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSubcDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
/*	@RequestMapping(value="addSwgeSubcntrDtlsPage.do", method=RequestMethod.GET)
	public String addSwgeSubcntrDtlsPage(SwtSubcDt swtSubcDt, Model model) {
		return "/projects/gn/job/cntrwkRegstr/swge/addSwgeSubcntrDtls";
	}*/
	
	/////////////////////////////////////////////
	/// @fn addSwgeSubcntrDtls
	/// @brief 함수 간략한 설명 : 침출수 하도급 내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtSubcDt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
/*	@RequestMapping(value="{ftrIdn}/addSwgeSubcntrDtls.do", method=RequestMethod.POST)
	public String addSwgeSubcntrDtls(@PathVariable("ftrIdn") Long ftrIdn, SwtSubcDt swtSubcDt, Model model) throws FdlException {
		swtSubcDt.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", swgeCntrwkRegstrService.addSwgeSubcntrDtls(swtSubcDt));
		return "jsonViewTextPlain";
	}*/
	
	/////////////////////////////////////////////
	/// @fn removeSwgeSubcntrDtls
	/// @brief 함수 간략한 설명 : 침출수 하도급 내역 삭제
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
/*	@RequestMapping(value="{shtIdn}/removeSwgeSubcntrDtls.do", method=RequestMethod.POST)
	public String removeSwgeSubcntrDtls(@PathVariable("shtIdn") Long shtIdn, Model model) {
		model.addAttribute("rowCount", swgeCntrwkRegstrService.removeSwgeSubcntrDtls(shtIdn));
		return "jsonView";
	}*/
	
	/////////////////////////////////////////////
	/// @fn addSwgeCntrwkRegstrPhotoPage
	/// @brief 함수 간략한 설명 : 침출수 공사대장 사진 등록 페이지
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
	@RequestMapping(value="addLwlCntrwkRegstrPhotoPage.do", method=RequestMethod.GET)
	public String addLwlCntrwkRegstrPhotoPage(KwsImage kwsImage, Model model) {
		return "/projects/gn/job/cntrwkRegstr/lwl/addLwlCntrwkRegstrPhoto";
	}
	
	/////////////////////////////////////////////
	/// @fn addSwgeCntrwkRegstrPhoto
	/// @brief 함수 간략한 설명 : 침출수 공사대장 사진 등록
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
	@RequestMapping(value="{ftrIdn}/addLwlCntrwkRegstrPhoto.do", method=RequestMethod.POST)
	public String addLwlCntrwkRegstrPhoto(@PathVariable("ftrIdn") Long ftrIdn, KwsImage kwsImage, MultipartRequest multipartRequest, Model model) throws Exception {
		kwsImage.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", lwlCntrwkRegstrService.addLwlCntrwkRegstrPhoto(kwsImage, multipartRequest));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneSwgeCntrwkRegstrPhotoPage
	/// @brief 함수 간략한 설명 : 침출수 공사사진 상세조회
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
	@RequestMapping(value="{imageNo}/selectOneLwlCntrwkRegstrPhotoPage.do", method=RequestMethod.GET)
	public String selectOneLwlCntrwkRegstrPhotoPage(@PathVariable("imageNo") Long imageNo, KwsImage kwsImage, Model model) {
		model.addAttribute("result", imageService.selectOneImage(imageNo));
		return "/projects/gn/job/cntrwkRegstr/lwl/viewLwlCntrwkRegstrPhoto";
	}
	
	/////////////////////////////////////////////
	/// @fn modifySwgeCntrwkRegstrPhotoPage
	/// @brief 함수 간략한 설명 : 침출수 공사대장 사진 수정 페이지
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
	@RequestMapping(value="{imageNo}/modifyLwlCntrwkRegstrPhotoPage.do", method=RequestMethod.GET)
	public String modifyLwlCntrwkRegstrPhotoPage(@PathVariable("imageNo") Long imageNo, KwsImage kwsImage, Model model) {
		model.addAttribute("result", imageService.selectOneImage(imageNo));
		return "/projects/gn/job/cntrwkRegstr/lwl/modifyLwlCntrwkRegstrPhoto";
	}
	
	/////////////////////////////////////////////
	/// @fn modifySwgeCntrwkRegstrPhoto
	/// @brief 함수 간략한 설명 : 침출수 공사대장 사진 수정
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
	@RequestMapping(value="{ftrIdn}/{imageNo}/modifyLwlCntrwkRegstrPhoto.do", method=RequestMethod.POST)
	public String modifyLwlCntrwkRegstrPhoto(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("imageNo") Long imageNo, KwsImage kwsImage, MultipartRequest multipartRequest, Model model) throws Exception {
		kwsImage.setFtrIdn(ftrIdn);
		kwsImage.setImageNo(imageNo);
		model.addAttribute("rowCount", lwlCntrwkRegstrService.modifyLwlCntrwkRegstrPhoto(kwsImage, multipartRequest));
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
		model.addAttribute("wkts", lwlCntrwkRegstrService.searchSpatial(ftrIdn));
		return "jsonView";
	}
}
