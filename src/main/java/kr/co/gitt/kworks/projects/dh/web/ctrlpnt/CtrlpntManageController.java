package kr.co.gitt.kworks.projects.dh.web.ctrlpnt;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.projects.dh.model.EtlRgcpPs;
import kr.co.gitt.kworks.projects.dh.service.ctrlpnt.CtrlpntService;
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

/////////////////////////////////////////////
/// @class CtrlpntManageController
/// kr.co.gitt.kworks.projects.dh.web.ctrlpnt \n
///   ㄴ CtrlpntManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 15. 오전 11:34:18 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 기준점 관리 컨트롤러 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/ctrlpnt/")
@Profile({"dh_dev", "dh_oper"})
public class CtrlpntManageController {
	
	// logger
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	// 기준점 서비스
	@Resource
	CtrlpntService ctrlpntService;
	
	// 이미지 서비스
	@Resource
	ImageService imageService;
	
	/////////////////////////////////////////////
	/// @fn searchCtrlpntPage
	/// @brief 함수 간략한 설명 : 기준점 검색 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param etlRgcpPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="searchCtrlpntPage.do", method=RequestMethod.GET)
	public String searchCtrlpntPage(EtlRgcpPs etlRgcpPs, Model model) {
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-203");
		model.addAttribute("grdCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/dh/job/ctrlpnt/listCtrlpnt";
	}
	
	/////////////////////////////////////////////
	/// @fn listCtrlpnt
	/// @brief 함수 간략한 설명 : 기준점 리스트 조회 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param etlRgcpPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listCtrlpnt.do", method=RequestMethod.GET)
	public String listCtrlpnt(EtlRgcpPs etlRgcpPs, Model model) {
		
		model.addAttribute("result", ctrlpntService.listCtrlpnt(etlRgcpPs));
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneCtrlpntPage
	/// @brief 함수 간략한 설명 : 기준점 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param etlRgcpPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/selectOneCtrlpntPage.do", method=RequestMethod.GET)
	public String selectOneCtrlpntPage(@PathVariable("ftrIdn") Long ftrIdn, EtlRgcpPs etlRgcpPs, Model model) {
		
		etlRgcpPs.setFtrIdn(ftrIdn);
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 점의종류
		kwsDomnCode.setDomnId("KWS-202");
		model.addAttribute("cpkCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 기준점등급
		kwsDomnCode.setDomnId("KWS-203");
		model.addAttribute("grdCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 상태
		kwsDomnCode.setDomnId("KWS-207");
		model.addAttribute("cpsCde", domnCodeService.listDomnCode(kwsDomnCode));

		// 수준측량방법
		kwsDomnCode.setDomnId("KWS-204");
		model.addAttribute("lskCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 법정읍면동
		kwsDomnCode.setDomnId("KWS-0562");
		model.addAttribute("bjdCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("result", ctrlpntService.selectOneCtrlpnt(etlRgcpPs));

		return "/projects/dh/job/ctrlpnt/viewCtrlpnt";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyRoadCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 기준점 수정 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param etlRgcpPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/modifyCtrlpntPage.do", method=RequestMethod.GET)
	public String modifyCtrlpntPage(@PathVariable("ftrIdn") Long ftrIdn, EtlRgcpPs etlRgcpPs, Model model) {
		
		etlRgcpPs.setFtrIdn(ftrIdn);
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 점의종류
		kwsDomnCode.setDomnId("KWS-202");
		model.addAttribute("cpkCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 기준점등급
		kwsDomnCode.setDomnId("KWS-203");
		model.addAttribute("grdCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 상태
		kwsDomnCode.setDomnId("KWS-207");
		model.addAttribute("cpsCde", domnCodeService.listDomnCode(kwsDomnCode));

		// 수준측량방법
		kwsDomnCode.setDomnId("KWS-204");
		model.addAttribute("lskCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 법정읍면동
		kwsDomnCode.setDomnId("KWS-0562");
		model.addAttribute("bjdCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("result", ctrlpntService.selectOneCtrlpnt(etlRgcpPs));
		
		return "/projects/dh/job/ctrlpnt/modifyCtrlpnt";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyCtrlpnt
	/// @brief 함수 간략한 설명 : 기준점 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param etlRgcpPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/modifyCtrlpnt.do", method=RequestMethod.POST)
	public String modifyCtrlpnt(@PathVariable("ftrIdn") Long ftrIdn, EtlRgcpPs etlRgcpPs, MultipartRequest multipartRequest, Model model) throws Exception {
	
		etlRgcpPs.setFtrIdn(ftrIdn);
		
		model.addAttribute("rowCount", ctrlpntService.modifyCtrlpnt(etlRgcpPs, multipartRequest));
		
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn addCtrlpntPage
	/// @brief 함수 간략한 설명 : 기준점 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param etlRgcpPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addCtrlpntPage.do", method=RequestMethod.GET)
	public String addCtrlpntPage(EtlRgcpPs etlRgcpPs, Model model) {
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 점의종류
		kwsDomnCode.setDomnId("KWS-202");
		model.addAttribute("cpkCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 기준점등급
		kwsDomnCode.setDomnId("KWS-203");
		model.addAttribute("grdCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 상태
		kwsDomnCode.setDomnId("KWS-207");
		model.addAttribute("cpsCde", domnCodeService.listDomnCode(kwsDomnCode));

		// 수준측량방법
		kwsDomnCode.setDomnId("KWS-204");
		model.addAttribute("lskCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 법정읍면동
		kwsDomnCode.setDomnId("KWS-0562");
		model.addAttribute("bjdCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/dh/job/ctrlpnt/addCtrlpnt";
	}
	
	/////////////////////////////////////////////
	/// @fn addCtrlpnt
	/// @brief 함수 간략한 설명 : 기준점 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param etlRgcpPs
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addCtrlpnt.do", method=RequestMethod.POST)
	public String addCtrlpnt(EtlRgcpPs etlRgcpPs, MultipartRequest multipartRequest, Model model) throws Exception {
		model.addAttribute("rowCount", ctrlpntService.addCtrlpnt(etlRgcpPs, multipartRequest));
		return "jsonViewTextPlain";
	}
	
}
