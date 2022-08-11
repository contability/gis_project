package kr.co.gitt.kworks.web.manage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import kr.co.gitt.kworks.service.conectStats.UnitySysService;

/////////////////////////////////////////////
/// @class UnitySysConectStatsController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ UnitySysConectStatsController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 12. 28. 오후 5:18:03 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 통합시스템 접속통계 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/conectStats/unitySys/")
public class UnitySysConectStatsController {
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	//통합시스템 접속통계 서비스
	@Resource
	UnitySysService unitySysService;
	
	/////////////////////////////////////////////
	/// @fn listTodayPage
	/// @brief 함수 간략한 설명 : 금일접속현황
	/// @remark
	/// - 함수의 상세 설명 :
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="list.do")
	public String listTodayPage(Model model){
		
		model.addAttribute("nowCnt", unitySysService.nowConectCount());
		model.addAttribute("result", unitySysService.listTodayGroupByHourCount());
		
		return "/manage/conectStats/listUnitySys";
	}
	
	/////////////////////////////////////////////
	/// @fn todayConectStatsExcelDownload
	/// @brief 함수 간략한 설명 : 금일접속통계 엑셀 다운로드
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
	@RequestMapping("todayConectStatsExcel.do")
	public ModelAndView todayConectStatsExcelDownload(SearchDTO searchDTO) throws Exception {  
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("category", unitySysService.listTodayGroupByHourCount());
	    return new ModelAndView("todayConectStatsExcelDownload", "categoryMap", map);
	}
	
	/////////////////////////////////////////////
	/// @fn listDayPage
	/// @brief 함수 간략한 설명 : 일별 접속현황
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
		
		ArrayList<HashMap<String,String>> list = unitySysService.listGroupByDayCount(searchDTO);
		
		model.addAttribute("result",list);
		return "/manage/conectStats/listUnitySysDay";
	}
	
	/////////////////////////////////////////////
	/// @fn dayConectStatsExcelDownload
	/// @brief 함수 간략한 설명 : 일별 접속현황 엑셀 다운로드 
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
	@RequestMapping("dayConectStatsExcel.do")
	public ModelAndView dayConectStatsExcelDownload(SearchDTO searchDTO) throws Exception {  
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("category", unitySysService.listGroupByDayCount(searchDTO));
	    return new ModelAndView("dayConectStatsExcelDownload", "categoryMap", map);
	}
	
	/////////////////////////////////////////////
	/// @fn listMonthPage
	/// @brief 함수 간략한 설명 : 월별 접속 현황
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
		
		ArrayList<HashMap<String,String>> list = unitySysService.listGroupByMonthCount(searchDTO);
		
		model.addAttribute("result",list);
		return "/manage/conectStats/listUnitySysMonth";
	}
	
	/////////////////////////////////////////////
	/// @fn monthConectStatsExcelDownload
	/// @brief 함수 간략한 설명 : 월별접속현황 엑셀 다운로드
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
	@RequestMapping("monthConectStatsExcel.do")
	public ModelAndView monthConectStatsExcelDownload(SearchDTO searchDTO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("category", unitySysService.listGroupByMonthCount(searchDTO));
	    return new ModelAndView("monthConectStatsExcelDownload", "categoryMap", map);
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
		
		if(startDtStr.equals("")) {
			startDtStr = formatter.format(currentTime);
			searchDTO.setSearchStartDt(startDtStr);
		}
		
		if(endDtStr.equals("")) {
			endDtStr = formatter.format(currentTime);
			searchDTO.setSearchEndDt(startDtStr);
		}
		
		ArrayList<HashMap<String,String>> list = unitySysService.listGroupByWeekCount(searchDTO);
		
		model.addAttribute("result",list);
		return "/manage/conectStats/listUnitySysWeek";
	}
	
	/////////////////////////////////////////////
	/// @fn weekConectStatsExcelDownload
	/// @brief 함수 간략한 설명 : 요일별 접속현황 엑셀 다운로드
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
	@RequestMapping("weekConectStatsExcel.do")
	public ModelAndView weekConectStatsExcelDownload(SearchDTO searchDTO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("category", unitySysService.listGroupByWeekCount(searchDTO));
	    return new ModelAndView("weekConectStatsExcelDownload", "categoryMap", map);
	}
}
