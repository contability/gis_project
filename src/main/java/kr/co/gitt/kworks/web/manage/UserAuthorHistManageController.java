package kr.co.gitt.kworks.web.manage;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsUser.UserGrad;
import kr.co.gitt.kworks.service.user.UserAuthorHistService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class UserAuthorHistManageController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ UserAuthorHistManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 2. 10. 오전 11:37:12 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 권한부여 이력관리 컨트롤러 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/userAuthorHist/")
public class UserAuthorHistManageController {

	/// 사용자 권한 이력 서비스
	@Resource
	UserAuthorHistService userAuthorHistService;
	
	/////////////////////////////////////////////
	/// @fn listUserAuthorHistPage
	/// @brief 함수 간략한 설명 : 사용자 권한부여 이력 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @param paginationInfo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="list.do", method=RequestMethod.GET)
	public String listUserAuthorHistPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", userAuthorHistService.listUserAuthorHist(searchDTO, paginationInfo));
		model.addAttribute("userGrads", UserGrad.values());
		
		return "/manage/authorHist/listUserAuthorHist";
	}
	
	/////////////////////////////////////////////
	/// @fn userAuthorHistoryExcelDownload
	/// @brief 함수 간략한 설명 : 엑셀 다운로드
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("excel.do")
	public ModelAndView userAuthorHistoryExcelDownload(SearchDTO searchDTO) throws Exception {  
		Map<String, Object> map = new HashMap<String, Object>();  
	    map.put("category", userAuthorHistService.listUserAuthorHistExcel(searchDTO));
	    return new ModelAndView("userAuthorHistoryExcelDownload", "categoryMap", map);
	}
	
}
