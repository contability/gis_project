package kr.co.gitt.kworks.web.manage;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsNotice;
import kr.co.gitt.kworks.service.notice.NoticeService;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class NoticeManageController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ NoticeManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 3. 오전 9:41:18 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 공지사항 관리 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/notice/")
public class NoticeManageController {
	
	/// 공지사항 서비스
	@Resource
	NoticeService noticeService;
	
	@Resource
	private MessageSource messageSource;
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
		
		return "/manage/notice/listNotice";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneNotice
	/// @brief 함수 간략한 설명 : 단 건 조회
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
		model.addAttribute("data", noticeService.selectOneNotice(noticeNo));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addNotice
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsNotice
	/// @param multipartRequest
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="add.do", method=RequestMethod.POST)
	public String addNotice(KwsNotice kwsNotice, MultipartRequest multipartRequest, Model model) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsNotice.setWrterId(userDTO.getUserId());
		kwsNotice.setUpdusrId(userDTO.getUserId());
		
		model.addAttribute("rowCount", noticeService.addNotice(kwsNotice, multipartRequest));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyNotice
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param noticeNo
	/// @param deleteFileNos
	/// @param kwsNotice
	/// @param multipartRequest
	/// @param model
	/// @return
	/// @throws FdlException
	/// @throws IllegalStateException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{noticeNo}/modify.do", method=RequestMethod.POST)
	//public String modifyNotice(@PathVariable("noticeNo") Long noticeNo, @RequestParam("deleteFileNo") List<Long> deleteFileNos, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, KwsNotice kwsNotice, MultipartRequest multipartRequest, Model model) throws FdlException, IllegalStateException, IOException {
	public String modifyNotice(@PathVariable("noticeNo") Long noticeNo, @RequestParam("deleteFileNo") List<Long> deleteFileNos, KwsNotice kwsNotice, MultipartRequest multipartRequest, Model model) throws FdlException, IllegalStateException, IOException {
		
		kwsNotice.setNoticeNo(noticeNo);
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsNotice.setUpdusrId(userDTO.getUserId());
		
		model.addAttribute("rowCount", noticeService.modifyNotice(kwsNotice, multipartRequest, deleteFileNos));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn removeNotice
	/// @brief 함수 간략한 설명 : 삭제
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
	@RequestMapping(value="{noticeNo}/remove.do", method=RequestMethod.POST)
	public String removeNotice(@PathVariable("noticeNo") Long noticeNo, Model model) {
		model.addAttribute("rowCount", noticeService.removeNotice(noticeNo));
		return "jsonView";
	}
	
	@RequestMapping(value="noticePopup.do", method=RequestMethod.GET)
	public String selectPopup(Model model){
		model.addAttribute("result", noticeService.selectPopup());
		return "jsonView";
	}
	
}
