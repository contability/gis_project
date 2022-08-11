package kr.co.gitt.kworks.web.manage;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsDomn;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.service.domn.DomnService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/////////////////////////////////////////////
/// @class DomnManageController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ DomnManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오후 12:46:34 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 도메인 관리 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/domn/")
public class DomnManageController {
	
	// 도메인 서비스
	@Resource
	DomnService domnService;
	
	// 도메인 코드 서비스
	@Resource
	DomnCodeService domnCodeService;
		
	/////////////////////////////////////////////
	/// @fn listDomnPage
	/// @brief 함수 간략한 설명 : 목록 화면
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsDomn 
	/// @param kwsDomnCode
	/// @param model
	/// @return
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="list.do", method=RequestMethod.GET)
	public String listDomnPage(SearchDTO searchDTO, Model model) {
		model.addAttribute("listDomn", domnService.listDomn(searchDTO));
		return "/manage/domn/listDomn";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneDomn
	/// @brief 함수 간략한 설명 : 단건 조회
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
	@RequestMapping(value="{domnId}/select.do", method=RequestMethod.GET)
	public String selectOneDomn(@PathVariable("domnId") String domnId, Model model) {
		
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addDomn
	/// @brief 함수 간략한 설명 : 도메인 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsDomn
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="add.do", method=RequestMethod.POST)
	public String addDomn(KwsDomn kwsDomn, Model model) throws Exception {
		if(domnService.selectOneDomn(kwsDomn.getDomnId()) != null){
			model.addAttribute("errMsg", "동일한 도메인ID값이 존재합니다.");
		}else{
			model.addAttribute("rowCount", domnService.addDomn(kwsDomn));
		}
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn updateDomn
	/// @brief 함수 간략한 설명 : 도메인 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param domnId
	/// @param kwsDomn
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{domnId}/modify.do", method=RequestMethod.POST)
	public String updateDomn(@PathVariable("domnId") String domnId, KwsDomn kwsDomn, Model model) throws Exception {
		model.addAttribute("rowCount", domnService.modifyDomn(kwsDomn));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn removeDomn
	/// @brief 함수 간략한 설명 : 도메인 삭제
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
	@RequestMapping(value="{domnId}/remove.do", method=RequestMethod.POST)
	public String removeDomn(@PathVariable("domnId") String domnId, Model model) {
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setDomnId(domnId);
		domnCodeService.removeDomnCode(kwsDomnCode);
		model.addAttribute("rowCount", domnService.removeDomn(domnId));
		return "jsonView";
	}
}
