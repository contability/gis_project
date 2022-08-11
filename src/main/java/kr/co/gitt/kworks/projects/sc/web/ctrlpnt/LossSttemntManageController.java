package kr.co.gitt.kworks.projects.sc.web.ctrlpnt;

import javax.annotation.Resource;

import kr.co.gitt.kworks.projects.sc.dto.LossSttemntDTO;
import kr.co.gitt.kworks.projects.sc.model.EttLscpDt;
import kr.co.gitt.kworks.projects.sc.service.lossSttemnt.LossSttemntService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class LossSttemntManageController
/// kr.co.gitt.kworks.projects.sc.web.ctrlpnt \n
///   ㄴ LossSttemntManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 16. 오전 11:29:56 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 망실 신고 관리 컨트롤러 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/lossSttemnt/")
@Profile({"sc_dev", "sc_oper"})
public class LossSttemntManageController {
	
	// logger
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	// 기준점 서비스
	@Resource
	LossSttemntService lossSttemntService;
	
	/////////////////////////////////////////////
	/// @fn searchLossSttemntPage
	/// @brief 함수 간략한 설명 : 망실신고 검색 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ettLscpDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="searchLossSttemntPage.do", method=RequestMethod.GET)
	public String searchLossSttemntPage(EttLscpDt ettLscpDt, Model model) {
		return "/projects/sc/job/lossSttemnt/listLossSttemnt";
	}
	
	/////////////////////////////////////////////
	/// @fn listLossSttemnt
	/// @brief 함수 간략한 설명 : 망실 신고 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ettLscpDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listLossSttemnt.do", method=RequestMethod.GET)
	public String listLossSttemnt(EttLscpDt ettLscpDt, Model model) {
		
		model.addAttribute("result", lossSttemntService.listLossSttemnt(ettLscpDt));
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneLossSttemntPage
	/// @brief 함수 간략한 설명 : 망실 신고 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param ettLscpDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{shtIdn}/selectOneLossSttemntPage.do", method=RequestMethod.GET)
	public String selectOneLossSttemntPage(LossSttemntDTO lossSttemntDTO, Model model) {
		
		model.addAttribute("result", lossSttemntService.selectOneLossSttemnt(lossSttemntDTO));

		return "/projects/sc/job/lossSttemnt/viewLossSttemnt";
	}
	
	/////////////////////////////////////////////
	/// @fn addLossSttemntPage
	/// @brief 함수 간략한 설명 : 망실신고 등록 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ettLscpDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="addLossSttemntPage.do", method=RequestMethod.GET)
	public String addLossSttemntPage(EttLscpDt ettLscpDt, Model model) {
		
		return "/projects/sc/job/lossSttemnt/addLossSttemnt";
	}
	
	/////////////////////////////////////////////
	/// @fn addLossSttemnt
	/// @brief 함수 간략한 설명 : 망실신고 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ettLscpDt
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/{bseNam}/addLossSttemnt.do", method=RequestMethod.POST)
	public String addLossSttemnt(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("bseNam") String bseNam, EttLscpDt ettLscpDt, Model model) throws FdlException {
		ettLscpDt.setFtrIdn(ftrIdn);
		ettLscpDt.setBseNam(bseNam);
		model.addAttribute("rowCount", lossSttemntService.addLossSttemnt(ettLscpDt));
		return "jsonViewTextPlain";
	}
	
}
