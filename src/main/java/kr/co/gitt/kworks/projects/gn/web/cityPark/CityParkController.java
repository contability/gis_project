package kr.co.gitt.kworks.projects.gn.web.cityPark;

import javax.annotation.Resource;

import kr.co.gitt.kworks.projects.gn.dto.CityParkSearchDTO;
import kr.co.gitt.kworks.projects.gn.model.CtyPksdAs;
import kr.co.gitt.kworks.projects.gn.model.CtyRegisterInquiry;
import kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class CityParkController
/// kr.co.gitt.kworks.projects.gn.web.cityPark \n
///   ㄴ CityParkController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 5. 26. 오후 6:28:14 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도시공원 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/cityPark/")
@Profile({"gn_dev", "gn_oper", "gds_dev", "gds_oper"})
public class CityParkController {
	
	/// 도시공원 서비스
	@Resource
	CityParkService cityParkService;
	
	/////////////////////////////////////////////
	/// @fn listAllCityPark
	/// @brief 함수 간략한 설명 : 도시공원 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listAll.do", method=RequestMethod.GET)
	public String listAllCityPark(Model model) {
		model.addAttribute("rows", cityParkService.listCityAllPark());
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 도시공원 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param cityParkSearchDTO
	/// @param paginationInfo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="list.do", method=RequestMethod.GET)
	public String listCityPark(CityParkSearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("rows", cityParkService.listCityPark(searchDTO, paginationInfo));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listCityParkZone
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyPksdAs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/zone/scap/list.do", method=RequestMethod.GET)
	public String listCityParkZoneScap(CtyPksdAs ctyPksdAs, Model model) {
		model.addAttribute("rows", cityParkService.listCityParkZoneScap(ctyPksdAs));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listCityParkZoneAthl
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyPksdAs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/zone/athl/list.do", method=RequestMethod.GET)
	public String listCityParkZoneAthl(CtyPksdAs ctyPksdAs, Model model) {
		model.addAttribute("rows", cityParkService.listCityParkZoneAthl(ctyPksdAs));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listCityParkZonePlay
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyPksdAs
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/zone/play/list.do", method=RequestMethod.GET)
	public String listCityParkZonePlay(CtyPksdAs ctyPksdAs, Model model) {
		model.addAttribute("rows", cityParkService.listCityParkZonePlay(ctyPksdAs));
		return "jsonView";
	}
	
	
	/////////////////////////////////////////////
	/// @fn listAthlPs
	/// @brief 함수 간략한 설명 : 운동시설 대장 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyRegisterInquiry
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/getPk/ctyAthlPs/list.do", method=RequestMethod.GET)
	public String listAthlPs(CtyRegisterInquiry ctyRegisterInquiry, Model model){
		model.addAttribute("rows", cityParkService.listCityParkAthl(ctyRegisterInquiry));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listPlayPs
	/// @brief 함수 간략한 설명 : 유희시설 대장 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyRegisterInquiry
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/getPk/ctyPlayPs/list.do", method=RequestMethod.GET)
	public String listPlayPs(CtyRegisterInquiry ctyRegisterInquiry, Model model){
		model.addAttribute("rows", cityParkService.listCityParkPlay(ctyRegisterInquiry));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listRestPs
	/// @brief 함수 간략한 설명 : 휴게시설(점) 대장 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyRegisterInquiry
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/getPk/ctyRestPs/list.do", method=RequestMethod.GET)
	public String listRestPs(CtyRegisterInquiry ctyRegisterInquiry, Model model){
		model.addAttribute("rows", cityParkService.listCityParkRestPs(ctyRegisterInquiry));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listRestLs
	/// @brief 함수 간략한 설명 : 휴게시설(선) 대장 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyRegisterInquiry
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/getPk/ctyRestLs/list.do", method=RequestMethod.GET)
	public String listRestLs(CtyRegisterInquiry ctyRegisterInquiry, Model model){
		model.addAttribute("rows", cityParkService.listCityParkRestLs(ctyRegisterInquiry));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listRestAs
	/// @brief 함수 간략한 설명 : 휴게시설(면) 대장 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyRegisterInquiry
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/getPk/ctyRestAs/list.do", method=RequestMethod.GET)
	public String listRestAs(CtyRegisterInquiry ctyRegisterInquiry, Model model){
		model.addAttribute("rows", cityParkService.listCityParkRestAs(ctyRegisterInquiry));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listPksdAs
	/// @brief 함수 간략한 설명 : 공원구역 목록 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyRegisterInquiry
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/ctyPksdAs/list.do", method=RequestMethod.GET)
	public String listPksdAs(CtyRegisterInquiry ctyRegisterInquiry, Model model){
		model.addAttribute("rows", cityParkService.listCityParkPksdAs(ctyRegisterInquiry));
		return "jsonView";
	}
	
}
