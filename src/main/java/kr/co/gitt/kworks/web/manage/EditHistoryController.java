package kr.co.gitt.kworks.web.manage;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsEditHist.EditType;
import kr.co.gitt.kworks.service.edit.EditHistoryService;
import kr.co.gitt.kworks.service.sys.SysService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class EditHistoryController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ EditHistoryController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 23. 오전 10:00:16 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 편집 이력 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/editHist/")
public class EditHistoryController {

	/// 편집 이력 서비스
	@Resource
	EditHistoryService editHistoryService;
	
	/// 시스템 서비스
	@Resource
	SysService sysService;
	
	/////////////////////////////////////////////
	/// @fn listEditHistoryPage
	/// @brief 함수 간략한 설명 : 목록 검색
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
	public String listEditHistoryPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", editHistoryService.listEditHistory(searchDTO, paginationInfo));
		model.addAttribute("editTypes", EditType.values());
		
		model.addAttribute("sysList", sysService.listSys());
		
		return "/manage/editHist/listEditHistory";
	}
	
	/////////////////////////////////////////////
	/// @fn editHistoryExcelDownload
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
	public ModelAndView editHistoryExcelDownload(SearchDTO searchDTO) throws Exception {  
		Map<String, Object> map = new HashMap<String, Object>();  
	    map.put("category", editHistoryService.listEditHistoryExcel(searchDTO));
	    return new ModelAndView("editHistoryExcelDownload", "categoryMap", map);
	}
	
}
