package kr.co.gitt.kworks.web.bbs;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.service.recsroom.RecsroomService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class RecsroomBbsController
/// kr.co.gitt.kworks.web.bbs \n
///   ㄴ RecsroomBbsController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 11. 오전 11:20:12 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 자료실 View 컨트롤러 입니다.
///   -# 일반 사용자용 
/////////////////////////////////////////////
@Controller
@RequestMapping("/bbs/recsroom/")
public class RecsroomBbsController {
	
	/// 자료실 서비스
	@Resource
	RecsroomService recsroomService;
		
	/////////////////////////////////////////////
	/// @fn listRecsroomPage
	/// @brief 함수 간략한 설명 : 목록 화면
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
	public String listRecsroomPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", recsroomService.listRecsroom(searchDTO, paginationInfo));
		
		return "/bbs/recsroom/listRecsroom";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneRecsroom
	/// @brief 함수 간략한 설명 : 단 건 조회 화면
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param recsroomNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{recsroomNo}/select.do", method=RequestMethod.GET)
	public String selectOneRecsroom(@PathVariable("recsroomNo") Long recsroomNo, Model model) {
		recsroomService.updateRdcnt(recsroomNo);
		model.addAttribute("data", recsroomService.selectOneRecsroom(recsroomNo));
		return "jsonView";
	}
	
}
