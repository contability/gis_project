package kr.co.gitt.kworks.web.manage;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.service.log.ConnectLogService;
import kr.co.gitt.kworks.service.log.MenuConnectLogService;
import kr.co.gitt.kworks.service.log.SysConectLogService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class OpertLogController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ OpertLogController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | nam |
///    | Date | 2016. 10. 26. 오전 11:39:03 |
///    | Class Version | v1.0 |
///    | 작업자 | nam, Others... |
/// @section 상세설명
/// - 이 클래스는 작업 로그 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/opertLog/")
public class OpertLogController {
	
	/// 접속 로그 서비스
	@Resource
	ConnectLogService connectLogService;
	
	/// 메뉴 로그 서비스
	@Resource
	MenuConnectLogService menuConnectLogService;
	
	/// 시스템 접속 로그 서비스
	@Resource
	SysConectLogService sysConectLogService;
	
	/////////////////////////////////////////////
	/// @fn listConectLogPage
	/// @brief 함수 간략한 설명 : 접속 로그 목록 화면
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @param paginationInfo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/conectLog.do", method=RequestMethod.GET)
	public String listConectLogPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		paginationInfo.setTotalRecordCount(connectLogService.listCount(searchDTO));
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", connectLogService.listSearch(searchDTO));
		
		return "/manage/log/conectLog";
	}
	
	/////////////////////////////////////////////
	/// @fn editHistoryExcelDownload
	/// @brief 함수 간략한 설명 : 접속로그 엑셀 다운로드
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("conectLogExcel.do")
	public ModelAndView conectLogExcelDownload(SearchDTO searchDTO) throws Exception {  
		Map<String, Object> map = new HashMap<String, Object>();  
	    map.put("category", connectLogService.listConectLogExcel(searchDTO));
	    return new ModelAndView("conectLogExcelDownload", "categoryMap", map);
	}
	
	/////////////////////////////////////////////
	/// @fn listMenuLogPage
	/// @brief 함수 간략한 설명 : 메뉴 로그 목록 화면
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @param paginationInfo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/menuLog.do", method=RequestMethod.GET)
	public String listMenuLogPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		paginationInfo.setTotalRecordCount(menuConnectLogService.listCount(searchDTO));
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", menuConnectLogService.listSearch(searchDTO));
		
		return "/manage/log/menuLog";
	}
	
	/////////////////////////////////////////////
	/// @fn menuLogExcelDownload
	/// @brief 함수 간략한 설명 : 엑셀 다운로드 메뉴 로그 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("menuLogExcel.do")
	public ModelAndView menuLogExcelDownload(SearchDTO searchDTO) throws Exception {  
		Map<String, Object> map = new HashMap<String, Object>();  
	    map.put("category", menuConnectLogService.listMenuLogExcel(searchDTO));
	    return new ModelAndView("menuLogExcelDownload", "categoryMap", map);
	}
	
	/////////////////////////////////////////////
	/// @fn listSysConectLogPage
	/// @brief 함수 간략한 설명 : 시스템 접속 로그 목록 화면
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @param paginationInfo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/sysConectLog.do", method=RequestMethod.GET)
	public String listSysConectLogPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
	
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		paginationInfo.setTotalRecordCount(sysConectLogService.listCount(searchDTO));
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", sysConectLogService.listSearch(searchDTO));
		
		return "/manage/log/sysConectLog";
	}
	
	/////////////////////////////////////////////
	/// @fn sysConectLogExcelDownload
	/// @brief 함수 간략한 설명 : 시스템접속로그 엑셀 다운로드 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("sysConectLogExcel.do")
	public ModelAndView sysConectLogExcelDownload(SearchDTO searchDTO) throws Exception {  
		Map<String, Object> map = new HashMap<String, Object>();  
	    map.put("category", sysConectLogService.listSysConectLogExcel(searchDTO));
	    return new ModelAndView("sysConectLogExcelDownload", "categoryMap", map);
	}
	
}
