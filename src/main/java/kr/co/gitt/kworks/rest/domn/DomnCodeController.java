package kr.co.gitt.kworks.rest.domn;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/////////////////////////////////////////////
/// @class DomnCodeController
/// kr.co.gitt.kworks.rest.domn \n
///   ㄴ DomnCodeController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 3:11:35 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도메인 코드 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/domn/")
public class DomnCodeController {

	// 도메인 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	/////////////////////////////////////////////
	/// @fn selectOneData
	/// @brief 함수 간략한 설명 : 도메인 코드 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param domnId
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("{domnId}/listAll.do")
	public String selectOneData(@PathVariable("domnId") String domnId, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setDomnId(domnId);
		model.addAttribute("rows", domnCodeService.listDomnCode(kwsDomnCode));
		return "jsonView";
	}
	
}
