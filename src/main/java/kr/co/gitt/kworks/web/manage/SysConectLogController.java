package kr.co.gitt.kworks.web.manage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsSys;
import kr.co.gitt.kworks.model.KwsSys.SysTy;
import kr.co.gitt.kworks.service.log.SysConectLogService;
import kr.co.gitt.kworks.service.sys.SysService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class SysConectLogController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ SysConectLogController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | nam |
///    | Date | 2016. 9. 05. 오후 04:27:03 |
///    | Class Version | v1.0 |
///    | 작업자 | nam, Others... |
/// @section 상세설명
/// - 이 클래스는 시스템 접속 통계 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/log/")
public class SysConectLogController {
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 로그 서비스
	@Resource
	SysConectLogService sysConectLogService;
	
	// SYSTEM 서비스
	@Resource
	SysService sysService;
	
	/////////////////////////////////////////////
	/// @fn groupBySysPage
	/// @brief 함수 간략한 설명 : 시스템별 접속 통계 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @param model
	/// @param req
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listGroupBySys.do", method=RequestMethod.GET)
	public String groupBySysPage(SearchDTO searchDTO, Model model, HttpServletRequest req) {
		
		String searchUseYn		= searchDTO.getSearchUseYn() == null ? ""  : searchDTO.getSearchUseYn().toString();
		String searchStartDt	= searchDTO.getSearchStartDt() == null ? ""  : searchDTO.getSearchStartDt().toString();
		String searchEndDt		= searchDTO.getSearchEndDt() == null ? ""  : searchDTO.getSearchEndDt().toString();
		
		if(searchUseYn.equals("Y")){
			searchDTO.setSearchStartDt("");
			searchDTO.setSearchEndDt("");
			model.addAttribute("rows", sysConectLogService.listCountGroupBySys(searchDTO));
		}else if(!searchUseYn.equals("Y") && (!searchStartDt.equals("") || !searchEndDt.equals(""))){
			KwsSys kwsSys = new KwsSys();
			kwsSys.setSysTy(SysTy.SYSTEM);
			model.addAttribute("sys", sysService.listAllSys(kwsSys));
			model.addAttribute("rows", sysConectLogService.listCountGroupBySys(searchDTO));
		}
		
		return "/manage/log/SysConectLogGroupBySys";
	}
	
	/////////////////////////////////////////////
	/// @fn groupByUserPage
	/// @brief 함수 간략한 설명 : 사용자별 접속 통계 페이지
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
	@RequestMapping(value="listGroupByUser.do", method=RequestMethod.GET)
	public String groupByUserPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		searchDTO.setSearchCondition("name");
		
		String searchUseYn		= searchDTO.getSearchUseYn() == null ? ""  : searchDTO.getSearchUseYn().toString();
		String searchStartDt	= searchDTO.getSearchStartDt() == null ? ""  : searchDTO.getSearchStartDt().toString();
		String searchEndDt		= searchDTO.getSearchEndDt() == null ? ""  : searchDTO.getSearchEndDt().toString();
		String searchKeyword	= searchDTO.getSearchKeyword() == null ? ""  : searchDTO.getSearchKeyword().toString();
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		KwsSys kwsSys = new KwsSys();
		kwsSys.setSysTy(SysTy.SYSTEM);
		model.addAttribute("sys", sysService.listAllSys(kwsSys));
		
		if(searchUseYn.equals("Y")){
			searchDTO.setSearchStartDt("");
			searchDTO.setSearchEndDt("");
			searchDTO.setSearchKeyword("");
			
		}else if(!searchUseYn.equals("Y") && (!searchStartDt.equals("") || !searchEndDt.equals("") || !searchKeyword.equals(""))){
			
		}else{
			return "/manage/log/SysConectLogGroupByUser";
		}
		
		paginationInfo.setTotalRecordCount(sysConectLogService.listCountUser(searchDTO));
		model.addAttribute("rows", sysConectLogService.listCountGroupByUser(searchDTO));
		model.addAttribute("cnt", sysConectLogService.listUser(searchDTO));
		
		return "/manage/log/SysConectLogGroupByUser";
	}
	
	/////////////////////////////////////////////
	/// @fn groupByDeptPage
	/// @brief 함수 간략한 설명 : 부서별 접속 통계 페이지
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
	@RequestMapping(value="listGroupByDept.do", method=RequestMethod.GET)
	public String groupByDeptPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		searchDTO.setSearchCondition("dept");
		String searchUseYn		= searchDTO.getSearchUseYn() == null ? ""  : searchDTO.getSearchUseYn().toString();
		String searchStartDt	= searchDTO.getSearchStartDt() == null ? ""  : searchDTO.getSearchStartDt().toString();
		String searchEndDt		= searchDTO.getSearchEndDt() == null ? ""  : searchDTO.getSearchEndDt().toString();
		String searchKeyword	= searchDTO.getSearchKeyword() == null ? ""  : searchDTO.getSearchKeyword().toString();
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		KwsSys kwsSys = new KwsSys();
		kwsSys.setSysTy(SysTy.SYSTEM);
		model.addAttribute("sys", sysService.listAllSys(kwsSys));
		
		if(searchUseYn.equals("Y")){
			searchDTO.setSearchStartDt("");
			searchDTO.setSearchEndDt("");
			searchDTO.setSearchKeyword("");
			
		}else if(!searchUseYn.equals("Y") && (!searchStartDt.equals("") || !searchEndDt.equals("") || !searchKeyword.equals(""))){
			
		}else{
			return "/manage/log/SysConectLogGroupByDept";
		}
		
		paginationInfo.setTotalRecordCount(sysConectLogService.listCountDept(searchDTO));
		model.addAttribute("rows", sysConectLogService.listCountGroupByDept(searchDTO));
		model.addAttribute("cnt", sysConectLogService.listDept(searchDTO));
		
		return "/manage/log/SysConectLogGroupByDept";
	}

	/////////////////////////////////////////////
	/// @fn groupByMonthPage
	/// @brief 함수 간략한 설명 : 월별 접속 통계 페이지
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
	@RequestMapping(value="listGroupByMonth.do", method=RequestMethod.GET)
	public String groupByMonthPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		
		String searchUseYn		= searchDTO.getSearchUseYn() == null ? ""  : searchDTO.getSearchUseYn().toString();
		String searchStartDt	= searchDTO.getSearchStartDt() == null ? ""  : searchDTO.getSearchStartDt().toString();
		String searchEndDt		= searchDTO.getSearchEndDt() == null ? ""  : searchDTO.getSearchEndDt().toString();
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		KwsSys kwsSys = new KwsSys();
		kwsSys.setSysTy(SysTy.SYSTEM);
		model.addAttribute("sys", sysService.listAllSys(kwsSys));
		
		if(searchUseYn.equals("Y")){
			searchDTO.setSearchStartDt("");
			searchDTO.setSearchEndDt("");
		}else if(!searchUseYn.equals("Y") && (!searchStartDt.equals("") || !searchEndDt.equals("")) ){
			
		}else{
			return "/manage/log/SysConectLogGroupByMonth";
		}
		
		paginationInfo.setTotalRecordCount(sysConectLogService.listCountMonth(searchDTO));
		model.addAttribute("rows", sysConectLogService.listCountGroupByMonth(searchDTO));
		model.addAttribute("cnt", sysConectLogService.listMonth(searchDTO));
		
		return "/manage/log/SysConectLogGroupByMonth";
	}

	/////////////////////////////////////////////
	/// @fn groupByDayPage
	/// @brief 함수 간략한 설명 : 일별 접속 통계 페이지
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
	@RequestMapping(value="listGroupByDay.do", method=RequestMethod.GET)
	public String groupByDayPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		String searchUseYn		= searchDTO.getSearchUseYn() == null ? ""  : searchDTO.getSearchUseYn().toString();
		String searchStartDt	= searchDTO.getSearchStartDt() == null ? ""  : searchDTO.getSearchStartDt().toString();
		String searchEndDt		= searchDTO.getSearchEndDt() == null ? ""  : searchDTO.getSearchEndDt().toString();
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		KwsSys kwsSys = new KwsSys();
		kwsSys.setSysTy(SysTy.SYSTEM);
		model.addAttribute("sys", sysService.listAllSys(kwsSys));
		
		if(searchUseYn.equals("Y")){
			searchDTO.setSearchStartDt("");
			searchDTO.setSearchEndDt("");
			searchDTO.setSearchKeyword("");
			
		}else if(!searchUseYn.equals("Y") && (!searchStartDt.equals("") || !searchEndDt.equals("")) ){
			
		}else{
			return "/manage/log/SysConectLogGroupByDay";
		}
		
		paginationInfo.setTotalRecordCount(sysConectLogService.listCountDay(searchDTO));
		model.addAttribute("rows", sysConectLogService.listCountGroupByDay(searchDTO));
		model.addAttribute("cnt", sysConectLogService.listDay(searchDTO));
		
		return "/manage/log/SysConectLogGroupByDay";
	}
	
	/////////////////////////////////////////////
	/// @fn groupByWeekdayPage
	/// @brief 함수 간략한 설명 : 요일별 접속 통계 페이지
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
	@RequestMapping(value="listGroupByWeekday.do", method=RequestMethod.GET)
	public String groupByWeekdayPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		String searchUseYn		= searchDTO.getSearchUseYn() == null ? ""  : searchDTO.getSearchUseYn().toString();
		String searchStartDt	= searchDTO.getSearchStartDt() == null ? ""  : searchDTO.getSearchStartDt().toString();
		String searchEndDt		= searchDTO.getSearchEndDt() == null ? ""  : searchDTO.getSearchEndDt().toString();
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		if(searchUseYn.equals("Y")){
			searchDTO.setSearchStartDt("");
			searchDTO.setSearchEndDt("");
			searchDTO.setSearchKeyword("");
			
		}else if(!searchUseYn.equals("Y") && (!searchStartDt.equals("") || !searchEndDt.equals("")) ){
			
		}else{
			return "/manage/log/SysConectLogGroupByWeekday";
		}
		
		paginationInfo.setTotalRecordCount(sysConectLogService.listCountWeekday(searchDTO));
		
		KwsSys kwsSys = new KwsSys();
		kwsSys.setSysTy(SysTy.SYSTEM);
		model.addAttribute("sys", sysService.listAllSys(kwsSys));
		model.addAttribute("rows", sysConectLogService.listCountGroupByWeekday(searchDTO));
		model.addAttribute("cnt", sysConectLogService.listWeekday(searchDTO));
		
		return "/manage/log/SysConectLogGroupByWeekday";
	}

	/////////////////////////////////////////////
	/// @fn groupByMenuPage
	/// @brief 함수 간략한 설명 : 메뉴별 접속 통계 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="SysConectLogGroupByMenu.do", method=RequestMethod.GET)
	public String groupByMenuPage(Model model) {
		return "/manage/log/SysConectLogGroupByMenu";
	}
		
	/////////////////////////////////////////////
	/// @fn groupByTimePage
	/// @brief 함수 간략한 설명 : 시간대별 접속 통계 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="SysConectLogGroupByTime.do")
	public String groupByTimePage(Model model) {
		return "/manage/log/SysConectLogGroupByTime";
	}
}
