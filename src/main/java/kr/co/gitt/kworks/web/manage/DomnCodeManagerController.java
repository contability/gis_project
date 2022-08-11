package kr.co.gitt.kworks.web.manage;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/////////////////////////////////////////////
/// @class DomnCodeController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ DomnCodeController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 8. 26. 오후 3:35:58 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/domnCode/")
public class DomnCodeManagerController {
	
	// 도메인 코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	/////////////////////////////////////////////
	/// @fn listDomnCode
	/// @brief 함수 간략한 설명 : 도메인코드 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsDomnCode
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="list.do", method=RequestMethod.GET)
	public String listDomnCode(KwsDomnCode kwsDomnCode, Model model) {
		model.addAttribute("listDomnCode", domnCodeService.listDomnCode(kwsDomnCode));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addDomn
	/// @brief 함수 간략한 설명 : 도메인코드 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsDomnCode
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="add.do", method=RequestMethod.POST)
	public String addDomn(KwsDomnCode kwsDomnCode, Model model) throws Exception {
		if(domnCodeService.selectOneDomnCode(kwsDomnCode.getCodeId()) != null){
			model.addAttribute("errMsg", "동일한 코드ID 값이 존재합니다.");
		}else{
			model.addAttribute("rowCount", domnCodeService.addDomnCode(kwsDomnCode));
		}
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn updateDomn
	/// @brief 함수 간략한 설명 : 도메인코드 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param domnId
	/// @param kwsDomnCode
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{domnId}/modify.do", method=RequestMethod.POST)
	public String updateDomn(@PathVariable("domnId") String domnId, KwsDomnCode kwsDomnCode, Model model) throws Exception {
		model.addAttribute("rowCount", domnCodeService.modifyDomnCode(kwsDomnCode));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn removeDomn
	/// @brief 함수 간략한 설명 : 도메인 코드 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param codeId
	/// @param kwsDomnCode
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{codeId}/remove.do", method=RequestMethod.POST)
	public String removeDomn(@PathVariable("codeId") String codeId, KwsDomnCode kwsDomnCode, Model model) {
		model.addAttribute("rowCount", domnCodeService.removeDomnCode(kwsDomnCode));
		return "jsonView";
	}
}
