package kr.co.gitt.kworks.rest.data;

import javax.annotation.Resource;

import kr.co.gitt.kworks.service.data.DataService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/////////////////////////////////////////////
/// @class DataFieldCtgryController
/// kr.co.gitt.kworks.rest.data \n
///   ㄴ DataFieldCtgryController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오전 11:57:02 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터 카테고리 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/dataFieldCtgry/")
public class DataFieldCtgryController {
	
	/// 데이터 서비스
	@Resource
	DataService dataService;

	/////////////////////////////////////////////
	/// @fn listAllDataFieldCtgry
	/// @brief 함수 간략한 설명 : 데이터 카테고리 전체 목록 검색
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
	public String listAllDataFieldCtgry(Model model) {
		model.addAttribute("rows", dataService.listAllDataFieldCtgry());
		return "jsonView";
	}
	
}
