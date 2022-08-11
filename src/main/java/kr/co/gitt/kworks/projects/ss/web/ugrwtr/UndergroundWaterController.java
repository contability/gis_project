package kr.co.gitt.kworks.projects.ss.web.ugrwtr;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.projects.ss.model.BmlWellPs;
import kr.co.gitt.kworks.projects.ss.service.ugrwtr.UndergroundWaterService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/////////////////////////////////////////////
/// @class UndergroundWaterController
/// kr.co.gitt.kworks.web.ugrwtr \n
///   ㄴ UndergroundWaterController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 9. 22. 오후 5:14:56 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 지하수관정 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/ugrwtr/")
@Profile({"ss_dev", "ss_oper"})
public class UndergroundWaterController {
	// 로거
	Logger logger = LoggerFactory.getLogger(getClass());
	
	//지하수관정 서비스
	@Resource
	UndergroundWaterService undergroundWaterService;
	
	//도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	/////////////////////////////////////////////
	/// @fn pageUndergroundWater
	/// @brief 함수 간략한 설명 : 지하수관정 검색 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="page.do", method=RequestMethod.GET)
	public String pageUndergroundWater(BmlWellPs bmlWellPs, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 세부용도
		kwsDomnCode.setDomnId("KWS-223");
		model.addAttribute("listUwaterDtlSrv", domnCodeService.listDomnCode(kwsDomnCode));
				
		// 시설구분
		kwsDomnCode.setDomnId("KWS-224"); 
		model.addAttribute("listPermNtFormGbn", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 구분
		kwsDomnCode.setDomnId("KWS-225");
		model.addAttribute("listRgtMbdGbn", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "projects/ss/job/ugrwtr/listUndergroundWater";
	}
	
	/////////////////////////////////////////////
	/// @fn listUndergroundWater
	/// @brief 함수 간략한 설명 : 지하수관정 리스트 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bmlWellPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="list.do", method=RequestMethod.GET)
	public String listUndergroundWater(BmlWellPs bmlWellPs, Model model) {
		model.addAttribute("result", undergroundWaterService.list(bmlWellPs));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneUndergroundWaterPage
	/// @brief 함수 간략한 설명 : 상세조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param objectid
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{objectid}/selectOne.do", method=RequestMethod.POST)
	public String selectOneUndergroundWaterPage(@PathVariable("objectid") String objectid, Model model){
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		//세부용도
		kwsDomnCode.setDomnId("KWS-223");
		model.addAttribute("listUwaterDtlSrv", domnCodeService.listDomnCode(kwsDomnCode));
				
		//시설구분 도메인
		kwsDomnCode.setDomnId("KWS-224"); 
		model.addAttribute("listPermNtFormGbn", domnCodeService.listDomnCode(kwsDomnCode));
		
		//구분
		kwsDomnCode.setDomnId("KWS-225");
		model.addAttribute("listRgtMbdGbn", domnCodeService.listDomnCode(kwsDomnCode));
		
		//민원취하
		kwsDomnCode.setDomnId("KWS-226");
		model.addAttribute("listPermYn", domnCodeService.listDomnCode(kwsDomnCode));
		
		//허가취소
		kwsDomnCode.setDomnId("KWS-227");
		model.addAttribute("listPermCancelYn", domnCodeService.listDomnCode(kwsDomnCode));
		
		model.addAttribute("result",undergroundWaterService.selectOne(objectid));
		return "projects/ss/job/ugrwtr/viewUndergroundWater";
	}
	
}
