package kr.co.gitt.kworks.rest.data;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsDataFtrCde;
import kr.co.gitt.kworks.service.data.DataService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/////////////////////////////////////////////
/// @class DataFtrCdeController
/// kr.co.gitt.kworks.rest.data \n
///   ㄴ DataFtrCdeController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 3:11:15 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터 지형지물부호 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/dataFtrCde/")
public class DataFtrCdeController {
	
	/// 데이터 서비스
	@Resource
	DataService dataService;
	
	/////////////////////////////////////////////
	/// @fn listAllDataFtrCde
	/// @brief 함수 간략한 설명 : 데이터 지형지물부호 전체 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsDataFtrCde
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/listAll.do", method=RequestMethod.GET)
	public String listAllDataFtrCde(KwsDataFtrCde kwsDataFtrCde, Model model) {
		model.addAttribute("rows", dataService.listAllDataFtrCde(kwsDataFtrCde));
		return "jsonView";
	}

}
