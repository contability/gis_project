package kr.co.gitt.kworks.web.statsMap;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsStatsCl;
import kr.co.gitt.kworks.model.KwsStatsMastr;
import kr.co.gitt.kworks.model.KwsStatsValu;
import kr.co.gitt.kworks.service.statsMap.StatsMapService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/////////////////////////////////////////////
/// @class StatsMapController
/// kr.co.gitt.kworks.web.statsMap \n
///   ㄴ StatsMapController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 12. 28. 오후 4:15:10 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명 통계지도 클래스 입니다.
/// - 이 클래스는 
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/statsMap/")
public class StatsMapController {

	@Resource 
	StatsMapService statsMapService;
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 통계지도 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="list.do", method=RequestMethod.POST)
	public String list(Model model){
		KwsStatsCl kwsStatsCl = new KwsStatsCl();
		KwsStatsMastr kwsStatsMastr = new KwsStatsMastr();
		
		List<KwsStatsMastr> list = statsMapService.groupByIemYear();
		kwsStatsMastr.setIemYear(list.get(list.size() -1).getIemYear());
		
		model.addAttribute("year", list.get(list.size() -1).getIemYear());
		model.addAttribute("groupByIemYear", list);
		model.addAttribute("kwsStatsCl", statsMapService.listKwsStatsCl(kwsStatsCl));
		model.addAttribute("kwsStatsMastr", statsMapService.listKwsStatsMastr(kwsStatsMastr));
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listKwsStatsCl
	/// @brief 함수 간략한 설명 : 통계지도 항목 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsStatsCl
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listKwsStatsCl.do", method=RequestMethod.POST)
	public String listKwsStatsCl(KwsStatsCl kwsStatsCl, Model model){

		model.addAttribute("result", statsMapService.listKwsStatsCl(kwsStatsCl));
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listKwsStatsMastr
	/// @brief 함수 간략한 설명 : 통계지도 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsStatsMastr
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listKwsStatsMastr.do", method=RequestMethod.POST)
	public String listKwsStatsMastr(KwsStatsMastr kwsStatsMastr,Model model){
		
		model.addAttribute("result", statsMapService.listKwsStatsMastr(kwsStatsMastr));
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn statsMapInfo
	/// @brief 함수 간략한 설명 : 통계지도 팝업 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="statsMapInfo.do", method=RequestMethod.GET)
	public String statsMapInfo(Model model){
		
		return "/statsMap/statsMapInfo";
	}
	
	/////////////////////////////////////////////
	/// @fn statsMapSet
	/// @brief 함수 간략한 설명 : 통계지도 설정 팝업
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="statsMapSet.do", method=RequestMethod.GET)
	public String statsMapSet(Model model){
		
		return "/statsMap/statsMapSet";
	}
	
	/////////////////////////////////////////////
	/// @fn minMaxKwsStatsValu
	/// @brief 함수 간략한 설명 : 통계지도 최대 최소값을 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsStatsValu
	/// @param iemUnit
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="minMaxValu.do", method=RequestMethod.POST)
	public String minMaxKwsStatsValu(KwsStatsValu kwsStatsValu,String iemUnit, Model model){
		model.addAttribute("iemUnit", iemUnit);
		model.addAttribute("minVal", statsMapService.minValuKwsStatsValu(kwsStatsValu));
		model.addAttribute("maxVal", statsMapService.maxValuKwsStatsValu(kwsStatsValu));
		model.addAttribute("list", statsMapService.listKwsStatsValu(kwsStatsValu));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listKwsStatsValu
	/// @brief 함수 간략한 설명 : 통계지도 값을 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsStatsValu
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listValu.do", method=RequestMethod.POST)
	public String listKwsStatsValu(KwsStatsValu kwsStatsValu, Model model){
		model.addAttribute("kwsStatsValu", statsMapService.listKwsStatsValu(kwsStatsValu));
		return "jsonView";
	}
}
