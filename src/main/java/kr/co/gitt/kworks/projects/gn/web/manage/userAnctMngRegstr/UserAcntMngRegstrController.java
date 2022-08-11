package kr.co.gitt.kworks.projects.gn.web.manage.userAnctMngRegstr;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsUser.UserGrad;
import kr.co.gitt.kworks.projects.gn.model.MngUserAuthorHist;
import kr.co.gitt.kworks.projects.gn.service.userAnctMngRegstr.UserAcntMngRegstrService;

/////////////////////////////////////////////
/// @class UserAcntMngRegstrController
/// kr.co.gitt.kworks.projects.gn.web.manage.userAnctMngRegstr \n
///   ㄴ UserAcntMngRegstrController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용     |
///    | :-------------: | ------------- |
///    | Company | (주)gitt              |    
///    | Author | 이승재                  |
///    | Date | 2021. 9. 24             |
///    | Class Version | v1.0           |
///    | 작업자 |                         |
/// @section 상세설명
/// - 이 클래스는 사용자 권한부여 이력을 기반으로 한
///   사용자계정관리대장 컨트롤러 클래스 입니다.
/// -
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/userAcntMngRegstr/")
@Profile({"gn_dev", "gn_oper"})
public class UserAcntMngRegstrController {
	
	@Resource
	UserAcntMngRegstrService userAcntMngRegstrService;
	
	/////////////////////////////////////////////
	/// @fn listMngUserAuthorHistPage
	/// @brief 함수 간략한 설명 : 사용자계정관리대장 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @param paginationInfo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="list.do", method=RequestMethod.GET)
	public String listMngUserAuthorHistPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", userAcntMngRegstrService.listMngUserAuthorHist(searchDTO, paginationInfo));
		model.addAttribute("userGrads", UserGrad.values());
		
		return "/projects/gn/manage/userAcntMngRegstr/listUserAuthorHist";
	}
	
	@RequestMapping(value="{sn}/updateMngUserAuthorHistPage.do", method=RequestMethod.GET)
	public String updateMngUserAuthorHistPage(@PathVariable("sn") Long sn, Model model){
		model.addAttribute("row", userAcntMngRegstrService.selectMngUserAuthorHist(sn));
		//return "/projects/gn/manage/userAcntMngRegstr/updateUserAuthorHist";
		return "jsonView";
	}
	
	@RequestMapping(value="updateMngUserAuthorHist.do", method=RequestMethod.POST)
	public String updateMngUserAuthorHist(MngUserAuthorHist mngUserAuthorHist, Model model){
		model.addAttribute("result", userAcntMngRegstrService.updateMngUserAuthorHist(mngUserAuthorHist));
		return "jsonView";
	}
	
	@RequestMapping(value="deleteMngUserAuthorHist.do", method=RequestMethod.POST)
	public String deleteMngUserAuthorHist(Long sn, Model model){
		model.addAttribute("result", userAcntMngRegstrService.deleteMngUserAuthorHist(sn));
		return "jsonView";
	}
}
