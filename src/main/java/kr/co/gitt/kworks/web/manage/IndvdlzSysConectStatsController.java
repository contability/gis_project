package kr.co.gitt.kworks.web.manage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsSys;
import kr.co.gitt.kworks.model.KwsSys.SysTy;
import kr.co.gitt.kworks.service.conectStats.IndvdlzSysService;
import kr.co.gitt.kworks.service.conectStats.UnitySysService;
import kr.co.gitt.kworks.service.sys.SysService;

/////////////////////////////////////////////
/// @class IndvdlzSysConectStatsController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ IndvdlzSysConectStatsController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 12. 28. 오후 5:20:16 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 개별시스템 접속통계 컨트롤러 입니다
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/conectStats/indvdlzSys/")
public class IndvdlzSysConectStatsController {
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	//개별시스템 접속통계 서비스
	@Resource
	IndvdlzSysService indvdlzSysService;
	
	//통합시스템 접속통계 서비스
	@Resource
	UnitySysService unitySysService;
	
	@Resource
	SysService sysService;
	
	/////////////////////////////////////////////
	/// @fn listPage
	/// @brief 함수 간략한 설명 : 금일 접속현황
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="list.do")
	public String listPage(SearchDTO searchDTO, Model model){
		
		Map<String, Object> list = indvdlzSysService.listTodayGroupByHourCount();
		
		model.addAttribute("nowCnt", unitySysService.nowConectCount());
		model.addAttribute("result", list.get("mapList"));
		model.addAttribute("sysTotalCnt", list.get("sysTotalCnt"));
		model.addAttribute("sysList", list.get("sysList"));
		return "/manage/conectStats/listIndvdlzSys";
	}
	
	/////////////////////////////////////////////
	/// @fn todayIndvdlzSysConectStatsExcel
	/// @brief 함수 간략한 설명 : 금일 접속현황 엑셀 다운로드
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
	@RequestMapping("todayIndvdlzSysConectStatsExcel.do")
	public ModelAndView todayIndvdlzSysConectStatsExcel(SearchDTO searchDTO) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("category", indvdlzSysService.listTodayGroupByHourCount());
	    return new ModelAndView("todayIndvdlzSysConectStatsExcelDownload", "categoryMap", map);
	}
	
	/////////////////////////////////////////////
	/// @fn listDayPage
	/// @brief 함수 간략한 설명 : 일별 접속 현황
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @param model
	/// @return
	/// @throws ParseException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listDay.do")
	public String listDayPage(SearchDTO searchDTO, Model model) throws ParseException{
		
		String startDtStr = searchDTO.getSearchStartDt() == null ? "" : searchDTO.getSearchStartDt();
		String endDtStr = searchDTO.getSearchEndDt() == null ? "" : searchDTO.getSearchEndDt();
		
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
		Date currentTime = new Date ( );
		
		if(startDtStr.equals("")){
			startDtStr = formatter.format ( currentTime );
			searchDTO.setSearchStartDt(startDtStr);
		}
		
		if(endDtStr.equals("")){
			endDtStr = formatter.format ( currentTime );
			searchDTO.setSearchEndDt(startDtStr);
		}
		
		Map<String, Object> resultMap = indvdlzSysService.listGroupByDayCount(searchDTO);
		
		model.addAttribute("nowCnt", unitySysService.nowConectCount());
		model.addAttribute("result",resultMap.get("mapList"));
		model.addAttribute("sysList", resultMap.get("sysList"));
		model.addAttribute("sysTotalCntMap", resultMap.get("sysTotalCntMap"));
		return "/manage/conectStats/listIndvdlzSysDay";
	}
	
	/////////////////////////////////////////////
	/// @fn dayIndvdlzSysConectStatsExcel
	/// @brief 함수 간략한 설명 : 일별접속현황 엑셀다운로드 
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
	@RequestMapping("dayIndvdlzSysConectStatsExcel.do")
	public ModelAndView dayIndvdlzSysConectStatsExcel(SearchDTO searchDTO) throws Exception {  
		Map<String, Object> map = new HashMap<String, Object>();
		
	    map.put("category", indvdlzSysService.listGroupByDayCount(searchDTO));
	    return new ModelAndView("dayIndvdlzSysConectStatsExcelDownload", "categoryMap", map);
	}
	
	/////////////////////////////////////////////
	/// @fn listMonthPage
	/// @brief 함수 간략한 설명 : 월별 접속현황
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @param model
	/// @return
	/// @throws ParseException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listMonth.do")
	public String listMonthPage(SearchDTO searchDTO, Model model) throws ParseException{
		String startDtStr = searchDTO.getSearchStartDt() == null ? "" : searchDTO.getSearchStartDt();
		String endDtStr = searchDTO.getSearchEndDt() == null ? "" : searchDTO.getSearchEndDt();
		
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM", Locale.KOREA );
		Date currentTime = new Date ( );
		
		if(startDtStr.equals("")){
			startDtStr = formatter.format ( currentTime );
			searchDTO.setSearchStartDt(startDtStr + "-01");
		}else{
			String[] startDtStrArr = startDtStr.split("-");
			if(!(startDtStrArr.length > 2)){
				startDtStr = startDtStrArr[0] + "-" + startDtStrArr[1] + "-1";
			}else{
				startDtStr = startDtStrArr[0] + "-" + startDtStrArr[1] + "-" + startDtStrArr[2];
			}
			
			searchDTO.setSearchStartDt(startDtStr);
		}
		
		if(endDtStr.equals("")){
			endDtStr = formatter.format ( currentTime );
			searchDTO.setSearchEndDt(endDtStr + "-01");
		}else{
			String[] endDtStrArr = endDtStr.split("-");
			if(!(endDtStrArr.length > 2)){
				endDtStr = endDtStrArr[0] + "-" + endDtStrArr[1] + "-1";
			}else{
				endDtStr = endDtStrArr[0] + "-" + endDtStrArr[1] + "-" + endDtStrArr[2];
			}
			
			searchDTO.setSearchEndDt(endDtStr);
		}
		
		Map<String, Object> resultMap = indvdlzSysService.listGroupByMonthCount(searchDTO);
		
		model.addAttribute("nowCnt", unitySysService.nowConectCount());
		model.addAttribute("result", resultMap.get("mapList"));
		model.addAttribute("sysList", resultMap.get("sysList"));
		model.addAttribute("sysTotalCntMap", resultMap.get("sysTotalCntMap"));
		
		return "/manage/conectStats/listIndvdlzSysMonth";
	}
	
	/////////////////////////////////////////////
	/// @fn monthIndvdlzSysConectStatsExcel
	/// @brief 함수 간략한 설명 : 월별 접속현황 엑셀 다운로드
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
	@RequestMapping("monthIndvdlzSysConectStatsExcel.do")
	public ModelAndView monthIndvdlzSysConectStatsExcel(SearchDTO searchDTO) throws Exception {  
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("category", indvdlzSysService.listGroupByMonthCount(searchDTO));
	    return new ModelAndView("monthIndvdlzSysConectStatsExcelDownload", "categoryMap", map);
	}
	
	
	/////////////////////////////////////////////
	/// @fn listWeekPage
	/// @brief 함수 간략한 설명 : 요일별 접속현황
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @param model
	/// @return
	/// @throws ParseException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listWeek.do")
	public String listWeekPage(SearchDTO searchDTO, Model model) throws ParseException{
		String startDtStr = searchDTO.getSearchStartDt() == null ? "" : searchDTO.getSearchStartDt();
		String endDtStr = searchDTO.getSearchEndDt() == null ? "" : searchDTO.getSearchEndDt();
		
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
		Date currentTime = new Date ( );
		
		if(startDtStr.equals("")){
			startDtStr = formatter.format ( currentTime );
			searchDTO.setSearchStartDt(startDtStr);
		}
		
		if(endDtStr.equals("")){
			endDtStr = formatter.format ( currentTime );
			searchDTO.setSearchEndDt(startDtStr);
		}
		
		Map<String, Object> list = indvdlzSysService.listGroupByWeekCount(searchDTO);
		model.addAttribute("nowCnt", unitySysService.nowConectCount());
		model.addAttribute("result",list.get("mapList"));
		model.addAttribute("sysList", list.get("sysList"));
		model.addAttribute("sysTotalCntMap", list.get("sysTotalCntMap"));
		return "/manage/conectStats/listIndvdlzSysWeek";
	}
	
	@RequestMapping("weekIndvdlzSysConectStatsExcel.do")
	public ModelAndView weekIndvdlzSysConectStatsExcel(SearchDTO searchDTO) throws Exception {  
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("category", indvdlzSysService.listGroupByWeekCount(searchDTO));
	    return new ModelAndView("weekIndvdlzSysConectStatsExcelDownload", "categoryMap", map);
	}
}