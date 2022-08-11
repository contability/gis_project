package kr.co.gitt.kworks.web.manage;

import java.util.Arrays;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ExportSearchDTO;
import kr.co.gitt.kworks.model.KwsExport.ExportTy;
import kr.co.gitt.kworks.model.KwsExport.ProgrsSttus;
import kr.co.gitt.kworks.service.export.ExportService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class ExportManageController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ ExportManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | "윤중근" |
///    | Date | 2016. 9. 6. 오후 1:59:48 |
///    | Class Version | v1.0 |
///    | 작업자 | "윤중근", Others... |
/// @section 상세설명
/// - 이 클래스는 관리자 시스템 - 내보내기 관리 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/export/")
public class ExportManageController {
	
	/// 내보내기 관리 서비스
	@Resource
	ExportService exportService;
	
	/////////////////////////////////////////////
	/// @fn listExportPage
	/// @brief 함수 간략한 설명 : 목록 조회
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
	public String listExportPage(ExportSearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", exportService.listExport(searchDTO, paginationInfo));
		model.addAttribute("exportTy", ExportTy.values());
		model.addAttribute("progrsSttus", ProgrsSttus.values());
		model.addAttribute("progrsSttusList", Arrays.asList(ProgrsSttus.values()));
		
		return "/manage/export/listExport";
	}
	
}
