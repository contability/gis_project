package kr.co.gitt.kworks.projects.gn.web.civilAppeal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.projects.gn.dto.DrngEqpCnfmPrmisnAtchmnflDTO;
import kr.co.gitt.kworks.projects.gn.dto.DrngEqpCnfmPrmisnDTO;
import kr.co.gitt.kworks.projects.gn.model.SwtSpmtCnncDt;
import kr.co.gitt.kworks.projects.gn.model.SwtSpmtMa;
import kr.co.gitt.kworks.projects.gn.service.civilAppeal.DrngEqpCnfmPrmisnService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class DrngEqpCnfmPrmisnManageController
/// kr.co.gitt.kworks.projects.gn.web.civilAppeal \n
///   ㄴ DrngEqpCnfmPrmisnManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 23. 오후 5:08:34 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
///    | 수정자 | 이승재, 2019.12.09
/// @section 상세설명
/// - 이 클래스는 배수설비인허가대장 관리 컨트롤러 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/civilAppeal/swge/")
@Profile({"gn_dev", "gn_oper", "gds_dev", "gds_oper"})
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
	/// - 수정자 : 이승재, 2019.11.19
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
		
		// 대표지번 법정읍면동코드
		kwsDomnCode.setDomnId("KWS-0199");
		model.addAttribute("rpstBjdcd", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 민원종류코드
		kwsDomnCode.setDomnId("KWS-0237");
		model.addAttribute("pmsCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/gn/job/civilAppeal/swge/searchDrngEqpCnfmPrmisn";
	}
	
	/////////////////////////////////////////////
	/// @fn searchLocation
	/// @brief 함수 간략한 설명 : 배수설비인허가대장 대표지번 위치 찾기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// - 작성자 : 이승재, 2019.12.09
	/// @param ftrIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/searchLocation.do", method=RequestMethod.GET)
	public String searchLocation(@PathVariable("ftrIdn") Long ftrIdn, Model model) {
		model.addAttribute("result", drngEqpCnfmPrmisnService.searchLocation(ftrIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneDrngEqpCnfmPrmisn
	/// @brief 함수 간략한 설명 : 배수설비인허가대장 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// - 이승재, 2020.07.28
	/// - 수정자 : 이승재, 2019.12.10
	/// @param ftrIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/selectOneDrngEqpCnfmPrmisn.do", method=RequestMethod.GET)
	public String selectOneDrngEqpCnfmPrmisn(@PathVariable("ftrIdn") Long ftrIdn, DrngEqpCnfmPrmisnDTO drngEqpCnfmPrmisnDTO, Model model) {
		model.addAttribute("result", drngEqpCnfmPrmisnService.selectOneDrngEqpCnfmPrmisn(ftrIdn));
		
		model.addAttribute("atchmnflList", drngEqpCnfmPrmisnService.listAtchmnfl(ftrIdn));
		
		// 연결된 물받이, 하수연결관 목록 가져오기
		drngEqpCnfmPrmisnDTO.setFtrIdn(ftrIdn);
		model.addAttribute("drngEqpList", drngEqpCnfmPrmisnService.listDrngEqp(drngEqpCnfmPrmisnDTO));
		
		return "/projects/gn/job/civilAppeal/swge/viewDrngEqpCnfmPrmisn";
	}
	
	/////////////////////////////////////////////
	/// @fn removeDrngEqpCnfmPrmisn
	/// @brief 함수 간략한 설명 : 배수설비인허가대장 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// - 수정자 : 이승재, 2019.12.14
	/// @param ftrCde
	/// @param ftrIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value = "{ftrCde}/{ftrIdn}/removeDrngEqpCnfmPrmisn.do", method = RequestMethod.POST)
	public String removeDrngEqpCnfmPrmisn(@PathVariable("ftrCde") String ftrCde, @PathVariable("ftrIdn") Long ftrIdn, Model model) {
		model.addAttribute("result", drngEqpCnfmPrmisnService.removeDrngEqpCnfmPrmisn(ftrCde, ftrIdn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyDrngEqpCnfmPrmisnPage
	/// @brief 함수 간략한 설명 : 배수설비인허가대장 편집 창 열기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// - 작성자 : 이승재, 2019.12.15
	/// @param ftrIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/modifyDrngEqpCnfmPrmisnPage.do", method=RequestMethod.GET)
	public String modifyDrngEqpCnfmPrmisnPage(@PathVariable("ftrIdn") Long ftrIdn, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 대표지번 법정읍면동코드
		kwsDomnCode.setDomnId("KWS-0199");
		model.addAttribute("selBjdcd", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 민원종류코드
		kwsDomnCode.setDomnId("KWS-0237");
		model.addAttribute("selPms", domnCodeService.listDomnCode(kwsDomnCode));
		
		kwsDomnCode.setDomnId("KWS-0440");
		model.addAttribute("selMop", domnCodeService.listDomnCode(kwsDomnCode));
		
		kwsDomnCode.setDomnId("KWS-0441");
		model.addAttribute("selDia", domnCodeService.listDomnCode(kwsDomnCode));
		
		kwsDomnCode.setDomnId("KWS-0442");
		model.addAttribute("selCnMth", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("result", drngEqpCnfmPrmisnService.selectOneDrngEqpCnfmPrmisn(ftrIdn));
		return "/projects/gn/job/civilAppeal/swge/editDrngEqpCnfmPrmisn";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyDrngEqpCnfmPrmisn
	/// @brief 함수 간략한 설명 : 배수설비인허가대장 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// - 수정자 : 이승재, 2019.12.15
	/// @param ftrIdn
	/// @param swtSpmtMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="modifyDrngEqpCnfmPrmisn.do", method=RequestMethod.POST)
	public String modifyDrngEqpCnfmPrmisn(DrngEqpCnfmPrmisnDTO drngEqpCnfmPrmisnDTO, Model model) {
		if (drngEqpCnfmPrmisnDTO.getPmsCde() == "PMS010") {
			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
			drngEqpCnfmPrmisnDTO.setProNam(userDTO.getUserNm());
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			Calendar calendar = Calendar.getInstance();
			drngEqpCnfmPrmisnDTO.setPmtYmd(simpleDateFormat.format(calendar.getTime()));
		}

		model.addAttribute("rowCount", drngEqpCnfmPrmisnService.modifyDrngEqpCnfmPrmisn(drngEqpCnfmPrmisnDTO));
		return "jsonViewTextPlain";
	}
	
	@RequestMapping(value="addDrngEqpCnfmPrmisnPage.do", method=RequestMethod.GET)
	public String addDrngEqpCnfmPrmisnPage(Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 대표지번 법정읍면동코드
		kwsDomnCode.setDomnId("KWS-0199");
		model.addAttribute("selBjdcd", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 민원종류코드
		kwsDomnCode.setDomnId("KWS-0237");
		model.addAttribute("selPms", domnCodeService.listDomnCode(kwsDomnCode));
		
		kwsDomnCode.setDomnId("KWS-0440");
		model.addAttribute("selMop", domnCodeService.listDomnCode(kwsDomnCode));
		
		kwsDomnCode.setDomnId("KWS-0441");
		model.addAttribute("selDia", domnCodeService.listDomnCode(kwsDomnCode));
		
		kwsDomnCode.setDomnId("KWS-0442");
		model.addAttribute("selCnMth", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/gn/job/civilAppeal/swge/editDrngEqpCnfmPrmisn";
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
	public String addDrngEqpCnfmPrmisn(DrngEqpCnfmPrmisnDTO drngEqpCnfmPrmisnDTO, Model model) throws FdlException {
		if (drngEqpCnfmPrmisnDTO.getPmsCde() == "PMS010") {
			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
			drngEqpCnfmPrmisnDTO.setProNam(userDTO.getUserNm());
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			Calendar calendar = Calendar.getInstance();
			drngEqpCnfmPrmisnDTO.setPmtYmd(simpleDateFormat.format(calendar.getTime()));
		}
		
		model.addAttribute("ftrIdn", drngEqpCnfmPrmisnService.addDrngEqpCnfmPrmisn(drngEqpCnfmPrmisnDTO));
		return "jsonViewTextPlain";
	}
	
	@RequestMapping(value="quickSearch.do", method=RequestMethod.GET)
	public String quickSearch(@RequestParam("dataId") String dataId, @RequestParam("fid") String fid, Model model) {
		model.addAttribute("result", drngEqpCnfmPrmisnService.quickSearch(dataId, fid));
		return "jsonView";
	}
	
	@RequestMapping(value="atchmnfl/addAtchmnflpage.do", method=RequestMethod.GET)
	public String addAtchmnflpage() {
		return "/projects/gn/job/civilAppeal/swge/addDrngEqpCnfmPrmisnAtchmnfl";
	}
	
	@RequestMapping(value="atchmnfl/addAtchmnfl.do", method=RequestMethod.POST)
	public String addAtchmnfl(DrngEqpCnfmPrmisnAtchmnflDTO drngEqpCnfmPrmisnAtchmnflDTO, MultipartRequest multipartRequest, Model model) throws FdlException, IllegalStateException, IOException {
        drngEqpCnfmPrmisnService.addAtchmnfl(drngEqpCnfmPrmisnAtchmnflDTO, multipartRequest);
		
		Long ftrIdn = drngEqpCnfmPrmisnAtchmnflDTO.getFtrIdn();
		model.addAttribute("atchmnflList", drngEqpCnfmPrmisnService.listAtchmnfl(ftrIdn));
		return "jsonView";
	}
	
	@RequestMapping(value="atchmnfl/{ftrIdn}/{shtIdn}/{fileNo}/removeAtchmnfl.do", method=RequestMethod.GET)
	public String removeAtchmnfl(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("shtIdn") Long shtIdn, @PathVariable("fileNo") Long fileNo, Model model) {
        drngEqpCnfmPrmisnService.removeAtchmnfl(shtIdn, fileNo);
		
		model.addAttribute("atchmnflList", drngEqpCnfmPrmisnService.listAtchmnfl(ftrIdn));
		return "jsonView";
	}
	
	@RequestMapping(value="searchDscssCodeList.do")
	public String searchDscssCodeList() {
		return "/projects/gn/job/civilAppeal/swge/searchDscssCodeList";
	}
	
	/////////////////////////////////////////////
	/// @fn drngEqpConnect
	/// @brief 함수 간략한 설명 : 배수설비인허가대장과 신규 추가된 물받이,하수연결관 정보를 SWT_SPMT_CNNC_DT 테이블에 입력
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrCde	
	/// @param ftrIdn
	/// @param ftfCde
	/// @param ftfIdn
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrCde}/{ftrIdn}/{ftfCde}/{ftfIdn}/connectDrngEqp.do", method=RequestMethod.GET)
	public void connectDrngEqp(@PathVariable("ftrCde") String ftrCde, @PathVariable("ftrIdn") long ftrIdn, @PathVariable("ftfCde") String ftfCde, @PathVariable("ftfIdn") long ftfIdn) {
		SwtSpmtCnncDt swtSpmtCnncDt = new SwtSpmtCnncDt();
		swtSpmtCnncDt.setFtrCde(ftrCde);
		swtSpmtCnncDt.setFtrIdn(ftrIdn);
		swtSpmtCnncDt.setFtfCde(ftfCde);
		swtSpmtCnncDt.setFtfIdn(ftfIdn);
		drngEqpCnfmPrmisnService.connectDrngEqpCnfmPrmisnAndDrngEqp(swtSpmtCnncDt);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief 함수 간략한 설명 : 배수설비인허가 대장과 연결된 물받이, 하수연결관 정보 합께 가져오기
	/// @remark
	/// - 이승재, 2020.07.28
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param drngEqpCnfmPrmisnDTO
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/listDrngEqp.do", method=RequestMethod.GET)
	public String listDrngEqp(@PathVariable("ftrIdn") Long ftrIdn, DrngEqpCnfmPrmisnDTO drngEqpCnfmPrmisnDTO, Model model) {
		// 연결된 물받이, 하수연결관 목록 가져오기
		drngEqpCnfmPrmisnDTO.setFtrIdn(ftrIdn);
		model.addAttribute("drngEqpList", drngEqpCnfmPrmisnService.listDrngEqp(drngEqpCnfmPrmisnDTO));
		return "jsonView";
	}
}
