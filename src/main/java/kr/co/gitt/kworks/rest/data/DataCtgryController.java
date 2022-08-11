package kr.co.gitt.kworks.rest.data;

import javax.annotation.Resource;

import kr.co.gitt.kworks.service.data.DataService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/////////////////////////////////////////////
/// @class DataCtgryController
/// kr.co.gitt.kworks.web.gis \n
///   ㄴ DataCtgryController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 26. 오후 2:41:26 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터 카테고리 컨트롤러 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/dataCtgry/")
public class DataCtgryController {
	
	/// 데이터 서비스
	@Resource
	DataService dataService;
	
	/////////////////////////////////////////////
	/// @fn listAllDataCtgry
	/// @brief 함수 간략한 설명 : 데이터 목록 전체 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="/listAll.do", method=RequestMethod.GET)
	public String listAllDataCtgry(Model model) {
		model.addAttribute("rows", dataService.listAllDataCtgry());
		return "jsonView";
	}
	
}
