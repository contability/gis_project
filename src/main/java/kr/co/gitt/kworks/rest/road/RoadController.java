package kr.co.gitt.kworks.rest.road;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.spatial.FilterBboxDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.FilterRelationDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.model.RdtRnmgDt;
import kr.co.gitt.kworks.service.road.RoadService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/////////////////////////////////////////////
/// @class RoadController
/// kr.co.gitt.kworks.rest.road \n
///   ㄴ RoadController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오전 10:15:43 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도로 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/road/")
public class RoadController {
	
	/// 도로 서비스
	@Resource
	RoadService roadService;
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdnCde
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("{rdnCde}/select.do")
	public String selectOne(@PathVariable("rdnCde") String rdnCde, Model model) {
		model.addAttribute("data", roadService.selectOne(rdnCde));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn searchExtext
	/// @brief 함수 간략한 설명 : 영역 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param filterBboxDTO
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("/searchExtent.do")
	public String searchExtext(FilterBboxDTO filterBboxDTO, Model model) {
		model.addAttribute("rows", roadService.searchExtent(filterBboxDTO));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn searchFacilities
	/// @brief 함수 간략한 설명 : 시설물 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param spatialSearchDTO
	/// @param filterRelationDTO
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("searchFacilities.do")
	public String searchFacilities(SpatialSearchDTO spatialSearchDTO, FilterRelationDTO filterRelationDTO, Model model) {
		model.addAttribute("rows", roadService.searchFacilities(spatialSearchDTO.getDataIds(), filterRelationDTO.getRelationDataId(), filterRelationDTO.getFids()));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn searchRelationRegister
	/// @brief 함수 간략한 설명 : 연관 대장 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param dataIds
	/// @param spatialSearchDTO
	/// @param filterBboxDTO
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{dataId}/searchRegister.do", method=RequestMethod.GET)
	public String searchRelationRegister(@PathVariable("dataId") String dataId, SpatialSearchDTO spatialSearchDTO, FilterBboxDTO filterBboxDTO, Model model) {
		model.addAttribute("data", roadService.searchRelationRegister(dataId, spatialSearchDTO.getDataIds(), filterBboxDTO));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyRoad
	/// @brief 함수 간략한 설명 : 도로명 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdnCde
	/// @param rdtRnmgDt
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{rdnCde}/modify.do", method=RequestMethod.POST)
	public String modifyRoad(@PathVariable("rdnCde") String rdnCde, RdtRnmgDt rdtRnmgDt, Model model) {
		rdtRnmgDt.setRdnCde(rdnCde);
		model.addAttribute("rowCount", roadService.modifyRoad(rdtRnmgDt));
		return "jsonView";
	}
	
}
