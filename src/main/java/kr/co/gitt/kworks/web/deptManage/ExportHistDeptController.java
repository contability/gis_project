package kr.co.gitt.kworks.web.deptManage;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ExportSearchDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.service.export.ExportHistoryService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class ExportHistDeptController
/// kr.co.gitt.kworks.web.manage.dept \n
///   ㄴ ExportHistDeptController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | "윤중근" |
///    | Date | 2016. 9. 8. 오후 2:32:33 |
///    | Class Version | v1.0 |
///    | 작업자 | "윤중근", Others... |
/// @section 상세설명
/// - 이 클래스는 (부서)내보내기 이력관리 컨트롤러 클래스 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/deptManage/exportHist/")
public class ExportHistDeptController {
	
	/// 내보내기 관리 서비스
	@Resource
	ExportHistoryService exportHistoryService;
	
	/////////////////////////////////////////////
	/// @fn listExportPage
	/// @brief 함수 간략한 설명 : 리스트 조회
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
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		if(userDTO.getUserGrad().name() != "ROLE_MNGR"){
			searchDTO.setDeptCode(userDTO.getDeptCode());
		}

		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", exportHistoryService.listExportOutput(searchDTO, paginationInfo));
		return "/deptManage/exportHist/listExportHist";
	}
	
}
