package kr.co.gitt.kworks.rest.cntm;

import javax.annotation.Resource;

import kr.co.gitt.kworks.service.cntm.CntmService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/////////////////////////////////////////////
/// @class CntmController
/// kr.co.gitt.kworks.web.gis \n
///   ㄴ CntmController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 11. 오후 6:06:09 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 좌표계 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/cntm/")
public class CntmController {
	
	/// 좌표계 서비스
	@Resource
	CntmService cntmService;
	
	/////////////////////////////////////////////
	/// @fn listAllCntm
	/// @brief 함수 간략한 설명 : 전체 목록 검색
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
	public String listAllCntm(Model model) {
		model.addAttribute("rows", cntmService.listAllCntm());
		return "jsonView";
	}

}
