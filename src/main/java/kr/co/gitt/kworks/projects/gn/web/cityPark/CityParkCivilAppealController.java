package kr.co.gitt.kworks.projects.gn.web.cityPark;

import javax.annotation.Resource;

import kr.co.gitt.kworks.projects.gn.dto.CityParkCivilAppealSearchDTO;
import kr.co.gitt.kworks.projects.gn.model.CtyRserMa;
import kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class CityParkCivilAppealController
/// kr.co.gitt.kworks.projects.gn.web.cityPark \n
///   ㄴ CityParkCivilAppealController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 5. 26. 오후 6:27:23 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도시공원 민원 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/cityPark/civilAppeal/")
@Profile({"gn_dev", "gn_oper", "gds_dev", "gds_oper"})
public class CityParkCivilAppealController {

	/// 도시공원 서비스
	@Resource
	CityParkService cityParkService;
	
	/////////////////////////////////////////////
	/// @fn listCivilAppeal
	/// @brief 함수 간략한 설명 : 도시공원 민원 목록 검색
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
	@RequestMapping(value="list.do", method=RequestMethod.GET)
	public String listCivilAppeal(CityParkCivilAppealSearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("rows", cityParkService.listCityParkCivilAppeal(searchDTO, paginationInfo));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addCivilAppeal
	/// @brief 함수 간략한 설명 : 도시공원 민원 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ctyRserMa
	/// @param model
	/// @return
	/// @throws FdlException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="add.do", method=RequestMethod.POST)
	public String addCivilAppeal(CtyRserMa ctyRserMa, Model model) throws FdlException {
		model.addAttribute("rowCount", cityParkService.addCivilAppeal(ctyRserMa));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyCivilAppeal
	/// @brief 함수 간략한 설명 : 도시공원 민원 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param ctyRserMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/modify.do", method=RequestMethod.POST)
	public String modifyCivilAppeal(@PathVariable("ftrIdn") Long ftrIdn, CtyRserMa ctyRserMa, Model model) {
		ctyRserMa.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", cityParkService.modifyCivilAppeal(ctyRserMa));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyCivilAppeal
	/// @brief 함수 간략한 설명 : 도시공원 민원 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{ftrIdn}/remove.do", method=RequestMethod.POST)
	public String modifyCivilAppeal(@PathVariable("ftrIdn") Long ftrIdn, Model model) {
		model.addAttribute("rowCount", cityParkService.removeCivilAppeal(ftrIdn));
		return "jsonView";
	}
	
}
