package kr.co.gitt.kworks.rest.data;

import javax.annotation.Resource;

import kr.co.gitt.kworks.service.data.DataService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/////////////////////////////////////////////
/// @class DataController
/// kr.co.gitt.kworks.web.gis \n
///   ㄴ DataController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 26. 오후 2:40:09 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터(layer)컨트롤러 입니다. 
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/data/")
public class DataController {

	/// 데이터 서비스
	@Resource
	DataService dataService;
	
	/////////////////////////////////////////////
	/// @fn selectOneData
	/// @brief 함수 간략한 설명 : 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("{dataId}/select.do")
	public String selectOneData(@PathVariable("dataId") String dataId, Model model) {
		model.addAttribute("data", dataService.selectOneData(dataId));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listAllData
	/// @brief 함수 간략한 설명 : 리스트 전체 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("/listAll.do")
	public String listAllData(Model model) {
		model.addAttribute("rows", dataService.listAllData(null));
		return "jsonView";
	}
}
