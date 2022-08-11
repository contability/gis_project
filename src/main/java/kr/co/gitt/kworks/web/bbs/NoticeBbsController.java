package kr.co.gitt.kworks.web.bbs;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.service.notice.NoticeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class NoticeBbsController
/// kr.co.gitt.kworks.web.bbs \n
///   ㄴ NoticeBbsController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 11. 오전 11:18:49 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 공지사항 View 컨트롤러 입니다.
///   -# 일반 사용자용
/////////////////////////////////////////////
@Controller
@RequestMapping("/bbs/notice/")
public class NoticeBbsController {

	/// 공지사항 서비스
	@Resource
	NoticeService noticeService;

	/////////////////////////////////////////////
	/// @fn listNoticePage
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
	public String listNoticePage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", noticeService.listNotice(searchDTO, paginationInfo));
		
		return "/bbs/notice/listNotice";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneNotice
	/// @brief 함수 간략한 설명 : 단 건 조회 화면
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param noticeNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{noticeNo}/select.do", method=RequestMethod.GET)
	public String selectOneNotice(@PathVariable("noticeNo") Long noticeNo, Model model) {
		noticeService.updateRdcnt(noticeNo);
		model.addAttribute("data", noticeService.selectOneNotice(noticeNo));
		return "jsonView";
	}
	
	@RequestMapping(value="noticePopupPage.do", method=RequestMethod.GET)
	public String selectPopupPage(){
		return "/popup/noticePopup";
	}
	
	@RequestMapping(value="noticePopup.do", method=RequestMethod.GET)
	public String selectPopup(Model model){
		model.addAttribute("result", noticeService.selectPopup());
		return "jsonView";
	}
	
}
