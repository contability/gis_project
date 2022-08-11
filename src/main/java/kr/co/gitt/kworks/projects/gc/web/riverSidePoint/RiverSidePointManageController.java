package kr.co.gitt.kworks.projects.gc.web.riverSidePoint;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.projects.gc.model.RivrStatPs;
import kr.co.gitt.kworks.projects.gc.service.riverSidePoint.RiverSidePointService;
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
/// @class RiverSidePointManageController
/// kr.co.gitt.kworks.projects.gc.web.riverSidePoint \n
///   ㄴ RiverSidePointManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 5. 24. 오전 11:20:41 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 하천 측점 관리 컨트롤러 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/riverSidePoint/")
@Profile({"gc_dev", "gc_oper"})
public class RiverSidePointManageController {
	
	// logger
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	// 하천측점 서비스
	@Resource
	RiverSidePointService riverSidePointService;
	
	// 이미지 서비스
	@Resource
	ImageService imageService;
	
	/////////////////////////////////////////////
	/// @fn searchRiverSidePointPage
	/// @brief 함수 간략한 설명 : 하천측점 검색 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rivrStatPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="searchRiverSidePointPage.do", method=RequestMethod.GET)
	public String searchRiverSidePointPage(RivrStatPs rivrStatPs, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-235");
		model.addAttribute("rivNam", domnCodeService.listDomnCode(kwsDomnCode));
		return "/projects/gc/job/riverSidePoint/listRiverSidePoint";
	}
	
	/////////////////////////////////////////////
	/// @fn listRiverSidePoint
	/// @brief 함수 간략한 설명 : 하천측점 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rivrStatPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listRiverSidePoint.do", method=RequestMethod.GET)
	public String listRiverSidePoint(RivrStatPs rivrStatPs, Model model) {
		model.addAttribute("result", riverSidePointService.listRiverSidePoint(rivrStatPs));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneRiverSidePointPage
	/// @brief 함수 간략한 설명 : 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param staIdn
	/// @param rivrStatPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{staIdn}/selectOneRiverSidePointPage.do", method=RequestMethod.GET)
	public String selectOneRiverSidePointPage(@PathVariable("staIdn") Long staIdn, RivrStatPs rivrStatPs, Model model) {
		rivrStatPs.setStaIdn(staIdn);
		model.addAttribute("result", riverSidePointService.selectOneRiverSidePoint(rivrStatPs));
		return "/projects/gc/job/riverSidePoint/viewRiverSidePoint";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyRiverSidePointPage
	/// @brief 함수 간략한 설명 : 하첨측점 수정 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param staIdn
	/// @param rivrStatPs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{staIdn}/modifyRiverSidePointPage.do", method=RequestMethod.GET)
	public String modifyRiverSidePointPage(@PathVariable("staIdn") Long staIdn, RivrStatPs rivrStatPs, Model model) {
		rivrStatPs.setStaIdn(staIdn);
		model.addAttribute("result", riverSidePointService.selectOneRiverSidePoint(rivrStatPs));
		return "/projects/gc/job/riverSidePoint/modifyRiverSidePoint";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyRiverSidePoint
	/// @brief 함수 간략한 설명 : 하천측점 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param staIdn
	/// @param rivrStatPs
	/// @param multipartRequest
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{staIdn}/modifyRiverSidePoint.do", method=RequestMethod.POST)
	public String modifyRiverSidePoint(@PathVariable("staIdn") Long staIdn, RivrStatPs rivrStatPs, MultipartRequest multipartRequest, Model model) throws Exception {
		rivrStatPs.setStaIdn(staIdn);
		model.addAttribute("rowCount", riverSidePointService.modifyRiverSidePoint(rivrStatPs, multipartRequest));
		return "jsonViewTextPlain";
	}
	
}
