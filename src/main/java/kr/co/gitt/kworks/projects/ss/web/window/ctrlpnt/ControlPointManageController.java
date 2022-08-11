package kr.co.gitt.kworks.projects.ss.web.window.ctrlpnt;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsDataAuthor;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.model.RdlPcbsPs;
import kr.co.gitt.kworks.model.RdtPcbsDt;
import kr.co.gitt.kworks.projects.ss.service.ctrlpnt.CtrlpntService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class ControlPointManageController
/// kr.co.gitt.kworks.web.window.ctrlPnt \n
///   ㄴ ControlPointManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2016. 12. 22. 오후 5:09:14 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 도시기준점 컨트롤러 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/window/ctrlPnt/")
@Profile({"ss_dev", "ss_oper"})
public class ControlPointManageController {

	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());

	// 도시기준점 서비스
	@Resource
	CtrlpntService ctrlpntService;
	
	// 도메인 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 도시기준점 리스트 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("listAll.do")
	public String controlPointListAll(Model model) {
		String dataId = "RDL_PCBS_PS";
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthor = false;
		for(KwsAuthorGroup kwsAuthorGroup : userDTO.getKwsAuthorGroups()) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}
		if(!isAuthor) {
			logger.warn("기준점에 대한 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}
		model.addAttribute("result", ctrlpntService.listAll());
		return "window/ctrlPnt/listControlPoint";
	}
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 도시기준점 리스트
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdlPcbsPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("list.do")
	public String controlPointList(RdlPcbsPs rdlPcbsPs, Model model){
		model.addAttribute("result", ctrlpntService.list(rdlPcbsPs));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 도시기준점 단 건 조회 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param serIdn
	/// @param rdlPcbsPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("{ftrIdn}/selectOne.do")
	public String controlPointSelectOne(@PathVariable("ftrIdn") Long ftrIdn, RdlPcbsPs rdlPcbsPs, Model model){
		rdlPcbsPs.setFtrIdn(ftrIdn);
		model.addAttribute("result", ctrlpntService.selectOne(rdlPcbsPs));
		return "window/ctrlPnt/viewControlPoint";
	}
	
	/////////////////////////////////////////////
	/// @fn ControlPointModifyView
	/// @brief 함수 간략한 설명 : 도시기준점 편집 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param rdlPcbsPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("{ftrIdn}/modifyControlPointPage.do")
	public String modifyControlPointPage(@PathVariable("ftrIdn") Long ftrIdn, RdlPcbsPs rdlPcbsPs, Model model){
		rdlPcbsPs.setFtrIdn(ftrIdn);
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setDomnId("KWS-059");
		model.addAttribute("hjdCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		kwsDomnCode.setDomnId("KWS-078");
		model.addAttribute("mngCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		kwsDomnCode.setDomnId("KWS-302");
		
		model.addAttribute("pcbCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		model.addAttribute("result", ctrlpntService.selectOne(rdlPcbsPs));
		return "window/ctrlPnt/modifyControlPoint";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyControlPoint
	/// @brief 함수 간략한 설명 : 도시기준점 수정 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param deleteImageType
	/// @param rdlPcbsPs
	/// @param multipartRequest
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("{ftrIdn}/modifyControlPoint.do")
	public String modifyControlPoint(@PathVariable("ftrIdn") Long ftrIdn, @RequestParam("deleteImageType") List<String> deleteImageType, RdlPcbsPs rdlPcbsPs, MultipartRequest multipartRequest, Model model) throws Exception{
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		Integer cnt = ctrlpntService.modify(rdlPcbsPs, multipartRequest, userDTO.getUserId(), deleteImageType);
		model.addAttribute("cnt", cnt);
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn addInfo
	/// @brief 함수 간략한 설명 : 도시기준점 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("addControlPointPage.do")
	public String addControlPointPage(Model model){
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setDomnId("KWS-059");
		model.addAttribute("hjdCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		kwsDomnCode.setDomnId("KWS-078");
		model.addAttribute("mngCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		kwsDomnCode.setDomnId("KWS-302");
		model.addAttribute("pcbCdeList", domnCodeService.listDomnCode(kwsDomnCode));
		
		RdlPcbsPs rdlPcbsPs = new RdlPcbsPs();
		model.addAttribute("result", rdlPcbsPs);
		return "window/ctrlPnt/addControlPoint";
	}
	
	/////////////////////////////////////////////
	/// @fn add
	/// @brief 함수 간략한 설명 : 도시기준점 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdlPcbsPs
	/// @param multipartRequest
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("addControlPoint.do")
	public String addControlPoint(RdlPcbsPs rdlPcbsPs, MultipartRequest multipartRequest, Model model) throws Exception{
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		Integer cnt = ctrlpntService.add(rdlPcbsPs, multipartRequest, userDTO.getUserId());
		model.addAttribute("cnt", cnt);
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn listControlPointManagePage
	/// @brief 함수 간략한 설명 : 도시기준점 관리이력 리스트 페이지 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param serIdn
	/// @param rdtPcbsDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("{serIdn}/listControlPointHistory.do")
	public String listControlPointHistoryPage(@PathVariable("serIdn") String serIdn, RdtPcbsDt rdtPcbsDt, Model model){
		model.addAttribute("result", ctrlpntService.listRdtPcbsDt(rdtPcbsDt));
		rdtPcbsDt.setSerIdn(serIdn);
		return "window/ctrlPnt/listControlPointHistory";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneControlPointHistoryPage
	/// @brief 함수 간략한 설명 : 도시기준점 관리이력 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param rdtPcbsDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("{ftrIdn}/selectOneControlPointHistory.do")
	public String selectOneControlPointHistoryPage(@PathVariable("ftrIdn") Integer ftrIdn, RdtPcbsDt rdtPcbsDt, Model model){
		
		RdtPcbsDt selectObj = ctrlpntService.selectOneRdtPcbsDt(rdtPcbsDt);
		RdlPcbsPs parentObj = new RdlPcbsPs();
		parentObj.setSerIdn(selectObj.getSerIdn());		
		
		model.addAttribute("data", ctrlpntService.selectOne(parentObj));
		model.addAttribute("result", selectObj);
		return "window/ctrlPnt/viewControlPointHistory";
	}
	
	/////////////////////////////////////////////
	/// @fn addControlPointHistory
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param serIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("{serIdn}/addControlPointHistoryPage.do")
	public String addControlPointHistoryPage(@PathVariable("serIdn") String serIdn, Model model){
		RdtPcbsDt rdtPcbsDt = new RdtPcbsDt();
		RdlPcbsPs rdlPcbsPs = new RdlPcbsPs();
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setDomnId("KWS-207");
		model.addAttribute("examinResultList", domnCodeService.listDomnCode(kwsDomnCode));
		
		rdlPcbsPs.setSerIdn(serIdn);
		model.addAttribute("data", ctrlpntService.selectOne(rdlPcbsPs));
		model.addAttribute("result", rdtPcbsDt);
		return "window/ctrlPnt/addControlPointHistory";
	}
	
	/////////////////////////////////////////////
	/// @fn addControlPointHistory
	/// @brief 함수 간략한 설명 : 도시기준점 관리이력 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtPcbsDt
	/// @param multipartRequest
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("addControlPointHistory.do")
	public String addControlPointHistory(RdtPcbsDt rdtPcbsDt, MultipartRequest multipartRequest, Model model) throws Exception{
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		Integer cnt = ctrlpntService.addRdtPcbsDt(rdtPcbsDt, multipartRequest, userDTO.getUserId());
		model.addAttribute("cnt", cnt);
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyInfoRdtPcbsDt
	/// @brief 함수 간략한 설명 : 도시기준점 관리이력 수정페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param rdtPcbsDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("{ftrIdn}/modifyControlPointHistoryPage.do")
	public String modifyControlPointHistoryPage(@PathVariable("ftrIdn") Integer ftrIdn, RdtPcbsDt rdtPcbsDt, Model model){
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setDomnId("KWS-207");
		model.addAttribute("examinResultList", domnCodeService.listDomnCode(kwsDomnCode));
		
		RdlPcbsPs rdlPcbsPs = new RdlPcbsPs();
		
		rdtPcbsDt.setFtrCde("ZC503");
		RdtPcbsDt selectObj = ctrlpntService.selectOneRdtPcbsDt(rdtPcbsDt);
		
		rdlPcbsPs.setSerIdn(selectObj.getSerIdn());
		
		model.addAttribute("data", ctrlpntService.selectOne(rdlPcbsPs));
		model.addAttribute("result", selectObj);
		return "window/ctrlPnt/modifyControlPointHistory";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyControlPointHistory
	/// @brief 함수 간략한 설명 : 도시기준점 관리이력 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param rdtPcbsDt
	/// @param deleteImageType
	/// @param multipartRequest
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("{ftrIdn}/modifyControlPointHistory.do")
	public String modifyControlPointHistory(@PathVariable("ftrIdn") Long ftrIdn, RdtPcbsDt rdtPcbsDt, @RequestParam("deleteImageType") List<String> deleteImageType, MultipartRequest multipartRequest, Model model) throws Exception{
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		Integer cnt = ctrlpntService.modifyRdtPcbsDt(rdtPcbsDt, multipartRequest, userDTO.getUserId(), deleteImageType);
		model.addAttribute("cnt", cnt);
		return "jsonViewTextPlain";
	}
	
	@RequestMapping("{ftrIdn}/removeRdtPcbsDt.do")
	public String removeControlPointHistory(@PathVariable("ftrIdn") Integer ftrIdn, RdtPcbsDt rdtPcbsDt, Model model) throws Exception{
		
		Integer cnt = ctrlpntService.removeRdtPcbsDt(rdtPcbsDt);
		model.addAttribute("cnt", cnt);
		return "jsonViewTextPlain";
	}
}
