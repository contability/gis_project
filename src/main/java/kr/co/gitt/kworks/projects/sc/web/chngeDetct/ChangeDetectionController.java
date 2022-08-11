package kr.co.gitt.kworks.projects.sc.web.chngeDetct;

import java.io.IOException;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.ChgAreaDt;
import kr.co.gitt.kworks.model.ChgDtlsDt;
import kr.co.gitt.kworks.service.chngeDetct.ChangeDetectionService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class ChangeDetectionController
/// kr.co.gitt.kworks.projects.dh.web.chngeDetct \n
///   ㄴ ChangeDetectionController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | yun4810 |
///    | Date | 2018. 10. 2. 오후 3:00:07 |
///    | Class Version | v1.0 |
///    | 작업자 | yun4810, Others... |
/// @section 상세설명
/// - 이 클래스는 변화탐지 컨트롤러 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/chngeDetct/")
@Profile({"sc_dev", "sc_oper"})
public class ChangeDetectionController {

	// 변화탐지내역 서비스
	@Resource
	ChangeDetectionService changeDetectionService;
	
	/////////////////////////////////////////////
	/// @fn listChangeDetectionDetailsPage
	/// @brief 함수 간략한 설명 : 변화탐지내역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgDtlsDt
	/// @param paginationInfo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value = "listChangeDetectionDetails.do", method=RequestMethod.GET)
	public String listChangeDetectionDetails(ChgDtlsDt chgDtlsDt, PaginationInfo paginationInfo, Model model){
		paginationInfo.setCurrentPageNo(chgDtlsDt.getPageIndex());
		paginationInfo.setRecordCountPerPage(chgDtlsDt.getRecordCountPerPage());
		paginationInfo.setPageSize(chgDtlsDt.getPageSize());
		
		chgDtlsDt.setFirstIndex(paginationInfo.getFirstRecordIndex());
		chgDtlsDt.setLastIndex(paginationInfo.getLastRecordIndex());
		chgDtlsDt.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", changeDetectionService.listChangeDetectionDetails(chgDtlsDt, paginationInfo));
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addChangeDetectionDetailsPage
	/// @brief 함수 간략한 설명 : 변화탐지내역 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgDtlsDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addChangeDetectionDetailsPage.do", method=RequestMethod.GET)
	public String addChangeDetectionDetailsPage(ChgDtlsDt chgDtlsDt, Model model){
		return "/main/menu/addChangeDetection";
	}
	
	/////////////////////////////////////////////
	/// @fn addChangeDetectionDetailsPage
	/// @brief 함수 간략한 설명 : 변화탐지내역 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgDtlsDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value = "addChangeDetectionDetails.do", method = RequestMethod.POST)
	public String addChangeDetectionDetails(ChgDtlsDt chgDtlsDt, Model model) throws FdlException{
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		chgDtlsDt.setWrterId(userDTO.getUserId());
		chgDtlsDt.setUpdusrId(userDTO.getUserId());
		model.addAttribute("rowCount", changeDetectionService.addChangeDetectionDetails(chgDtlsDt));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneChangeDetectionDetails
	/// @brief 함수 간략한 설명 : 변화탐지내역 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chngeDetctNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value = "{chngeDetctNo}/selectOneChangeDetectionDetails.do", method = RequestMethod.GET)
	public String selectOneChangeDetectionDetails(@PathVariable("chngeDetctNo") Long chngeDetctNo, Model model){
		model.addAttribute("data", changeDetectionService.selectOneChangeDetectionDetails(chngeDetctNo));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyChangeDetectionAreaDetailsPage
	/// @brief 함수 간략한 설명 : 변화 탐지 내역 수정 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chngeDetctNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{chngeDetctNo}/modifyChangeDetectionDetailsPage.do", method=RequestMethod.GET)
	public String modifyChangeDetectionDetailsPage(@PathVariable("chngeDetctNo") Long chngeDetctNo, Model model){
		model.addAttribute("data", changeDetectionService.selectOneChangeDetectionDetails(chngeDetctNo));
		return "/projects/sc/job/changeDetection/modifyChangeDetectionDetails";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyChangeDetectionDetails
	/// @brief 함수 간략한 설명 : 변화 탐지 내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chngeDetctNo
	/// @param chgDtlsDt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value = "{chngeDetctNo}/modifyChangeDetectionDetails.do", method = RequestMethod.POST)
	public String modifyChangeDetectionDetails(@PathVariable("chngeDetctNo") Long chngeDetctNo, ChgDtlsDt chgDtlsDt, Model model) throws FdlException{
		chgDtlsDt.setChngeDetctNo(chngeDetctNo);
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		chgDtlsDt.setUpdusrId(userDTO.getUserId());
		model.addAttribute("rowCount", changeDetectionService.modifyChangeDetectionDetails(chgDtlsDt));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn removeChangeDetectionDetails
	/// @brief 함수 간략한 설명 : 변화 탐지 내역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chngeDetctNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value = "{chngeDetctNo}/removeChangeDetectionDetails.do", method = RequestMethod.POST)
	public String removeChangeDetectionDetails(@PathVariable("chngeDetctNo") Long chngeDetctNo, Model model){
		model.addAttribute("rowCount", changeDetectionService.removeChangeDetectionDetails(chngeDetctNo));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listChangeDetectionArea
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgAreaDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value = "{chngeDetctNo}/listChangeDetectionArea.do", method = RequestMethod.GET)
	public String listChangeDetectionArea(@PathVariable("chngeDetctNo") Long chngeDetctNo, ChgAreaDt chgAreaDt, PaginationInfo paginationInfo, Model model){
		chgAreaDt.setChngeDetctNo(chngeDetctNo);
		
		paginationInfo.setCurrentPageNo(chgAreaDt.getPageIndex());
		paginationInfo.setRecordCountPerPage(chgAreaDt.getRecordCountPerPage());
		paginationInfo.setPageSize(chgAreaDt.getPageSize());
		
		chgAreaDt.setFirstIndex(paginationInfo.getFirstRecordIndex());
		chgAreaDt.setLastIndex(paginationInfo.getLastRecordIndex());
		chgAreaDt.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", changeDetectionService.listChangeDetectionArea(chgAreaDt, paginationInfo));
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneChangeDetectionArea
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 상세조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chngeAreaNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{chngeAreaNo}/selectOneChangeDetectionAreaPage.do", method=RequestMethod.GET)
	public String selectOneChangeDetectionAreaPage(@PathVariable("chngeAreaNo") Long chngeAreaNo, Model model){
		model.addAttribute("data", changeDetectionService.selectOneChangeDetectionArea(chngeAreaNo));
		return "/projects/sc/job/changeDetection/veiwChangeDetectionArea";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneChangeDetectionArea
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chngeAreaNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value = "{chngeAreaNo}/selectOneChangeDetectionArea.do", method = RequestMethod.GET)
	public String selectOneChangeDetectionArea(@PathVariable("chngeAreaNo") Long chngeAreaNo, Model model){
		model.addAttribute("data", changeDetectionService.selectOneChangeDetectionArea(chngeAreaNo));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn removeChangeDetectionArea
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chngeAreaNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value = "{chngeAreaNo}/removeChangeDetectionArea.do", method = RequestMethod.POST)
	public String removeChangeDetectionArea(@PathVariable("chngeAreaNo") Long chngeAreaNo, Model model){
		model.addAttribute("rowCount", changeDetectionService.removeChangeDetectionArea(chngeAreaNo));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyChangeDetectionAreaPage
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 수정 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chngeAreaNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value = "{chngeAreaNo}/modifyChangeDetectionAreaPage.do", method = RequestMethod.GET)
	public String modifyChangeDetectionAreaPage(@PathVariable("chngeAreaNo") Long chngeAreaNo, Model model){
		model.addAttribute("data", changeDetectionService.selectOneChangeDetectionArea(chngeAreaNo));
		return "/projects/sc/job/changeDetection/modifyChangeDetectionArea";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyChangeDetectionArea
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chngeAreaNo
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value = "{chngeAreaNo}/modifyChangeDetectionArea.do", method = RequestMethod.POST)
	public String modifyChangeDetectionArea(@PathVariable("chngeAreaNo") Long chngeAreaNo, ChgAreaDt chgAreaDt, String[] data, Model model) throws FdlException, IOException, Exception{
		chgAreaDt.setChngeAreaNo(chngeAreaNo);
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		chgAreaDt.setUpdusrId(userDTO.getUserId());
		model.addAttribute("rowCount", changeDetectionService.modifyChangeDetectionArea(chgAreaDt, data));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addChangeDetectionAreaPage
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgDtlsDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value = "addChangeDetectionAreaPage.do", method = RequestMethod.GET)
	public String addChangeDetectionAreaPage(ChgDtlsDt chgDtlsDt, Model model){
		return "/projects/sc/job/changeDetection/addChangeDetectionArea";
	}
	
	/////////////////////////////////////////////
	/// @fn addChangeDetectionArea
	/// @brief 함수 간략한 설명 : 변화 탐지 지역 등록 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param chgAreaDt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value = "addChangeDetectionArea.do", method = RequestMethod.POST)
	public String addChangeDetectionArea(ChgAreaDt chgAreaDt, String[] data, Model model) throws FdlException, IOException, Exception{
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		chgAreaDt.setWrterId(userDTO.getUserId());
		chgAreaDt.setUpdusrId(userDTO.getUserId());
		model.addAttribute("rowCount", changeDetectionService.addChangeDetectionArea(chgAreaDt, data));
		return "jsonView";
	}
}
