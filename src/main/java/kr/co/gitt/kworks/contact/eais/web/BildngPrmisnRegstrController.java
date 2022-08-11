package kr.co.gitt.kworks.contact.eais.web;

import javax.annotation.Resource;

import kr.co.gitt.kworks.contact.eais.model.BildngPrmisnRegstr;
import kr.co.gitt.kworks.contact.eais.model.DjrBldRgst;
import kr.co.gitt.kworks.contact.eais.service.BildngPrmisnRegstrService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/////////////////////////////////////////////
/// @class BildngPrmisnRegstrController
/// kr.co.gitt.kworks.contact.eais.web \n
///   ㄴ BildngPrmisnRegstrController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 2. 6. 오후 3:15:09 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 건축물대장, 건축허가대장 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/contact/eais/bildngPrmisnRegstr/")
@Profile({"ss_dev", "ss_oper", "sunchang_dev", "sunchang_oper", "is_dev", "is_oper", "gs_dev", "gs_oper", "mj_oper"})
public class BildngPrmisnRegstrController {
	
	/// 건축물허가대장 서비스
	@Resource(name="bildngPrmisnRegstrService")
	BildngPrmisnRegstrService bildngPrmisnRegstrService;
	
	/////////////////////////////////////////////
	/// @fn listBildngPrmisnRegstr
	/// @brief 함수 간략한 설명 : 건축허가대장 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bildngPrmisnRegstr
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="list.do", method=RequestMethod.GET)
	public String listBildngPrmisnRegstr(BildngPrmisnRegstr bildngPrmisnRegstr, Model model) {
		model.addAttribute("rows", bildngPrmisnRegstrService.listBildngPrmisnRegstr(bildngPrmisnRegstr));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn searchBildngPrmisnRegstrInfoJson
	/// @brief 함수 간략한 설명 : 건축허가대장 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param pmsrgstPk
	/// @param krasRequestVO
	/// @param bildngPrmisnRegstr
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{pmsrgstPk}/select.do", method=RequestMethod.GET)
	public String searchBildngPrmisnRegstrInfoJson(@PathVariable("pmsrgstPk") String pmsrgstPk, BildngPrmisnRegstr bildngPrmisnRegstr, Model model) {
		model.addAttribute("data", bildngPrmisnRegstrService.selectOneBildngPrmisnRegstr(bildngPrmisnRegstr));
		return "jsonView";
	}
	
	@RequestMapping(value="DjrBldRgstList.do", method=RequestMethod.GET)
	public String listDjrBldRgst(DjrBldRgst djrBldRgst, Model model){
		model.addAttribute("data", bildngPrmisnRegstrService.listDjrBldRgst(djrBldRgst));
		return "jsonView";
	}
	
	
	@RequestMapping(value="{bldrgstPk}/{regstrKindCd}/selectDjrTitle.do", method=RequestMethod.GET)
	public String selectDjrTitle(@PathVariable("bldrgstPk") int bldrgstPk, @PathVariable("regstrKindCd") String regstrGbCd, DjrBldRgst djrBldRgst, Model model){
		model.addAttribute("data",bildngPrmisnRegstrService.selecOnetDjrTitle(djrBldRgst));
		return "jsonView";
	}
	
	
}
