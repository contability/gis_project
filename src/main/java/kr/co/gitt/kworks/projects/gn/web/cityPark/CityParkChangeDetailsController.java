package kr.co.gitt.kworks.projects.gn.web.cityPark;

import javax.annotation.Resource;

import kr.co.gitt.kworks.projects.gn.dto.CityParkChangeDetailsSearchDTO;
import kr.co.gitt.kworks.projects.gn.service.cityPark.CityParkService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class CityParkChangeDetailsController
/// kr.co.gitt.kworks.projects.gn.web.cityPark \n
///   ㄴ CityParkChangeDetailsController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 5. 26. 오후 6:26:01 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도시공원 변천내역 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/cityPark/changeDetails/")
@Profile({"gn_dev", "gn_oper", "gds_dev", "gds_oper"})
public class CityParkChangeDetailsController {
	
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
	public String listChangeDetails(CityParkChangeDetailsSearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("rows", cityParkService.listChangeDetails(searchDTO, paginationInfo));
		return "jsonView";
	}

}
