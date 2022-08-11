package kr.co.gitt.kworks.rest.road;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.spatial.FilterBboxDTO;
import kr.co.gitt.kworks.service.road.RouteService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/////////////////////////////////////////////
/// @class RouteController
/// kr.co.gitt.kworks.rest.road \n
///   ㄴ RouteController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 3:13:52 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도로 구간 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/route/")
public class RouteController {

	/// 도로 구간 서비스
	@Resource
	RouteService routeService;
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 단 건 검색
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
	@RequestMapping("{ftrIdn}/select.do")
	public String selectOne(@PathVariable("ftrIdn") Long ftrIdn, Model model) {
		model.addAttribute("data", routeService.selectOne(ftrIdn));
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
		model.addAttribute("rows", routeService.searchExtent(filterBboxDTO));
		return "jsonView";
	}
	
}
