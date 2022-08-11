package kr.co.gitt.kworks.web.manage;

import java.io.IOException;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsAnswer;
import kr.co.gitt.kworks.model.KwsQestn.QestnProgrsSttus;
import kr.co.gitt.kworks.service.qna.QnaService;
import kr.co.gitt.kworks.service.user.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


/////////////////////////////////////////////
/// @class AnswerQnaController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ AnswerQnaController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 11. 오후 4:55:47 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 묻고답하기-답변 관리 컨트롤러 입니다.  
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/qna/")
public class AnswerQnaController {
	
	/// 묻고답하기 서비스
	@Resource
	QnaService qnaService;
	
	/// 묻고답하기 서비스
	@Resource
	UserService userService;
	
	/////////////////////////////////////////////
	/// @fn listAnswerPage
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 목록화면
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
	public String listAnswerPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) throws Exception {
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", qnaService.listQestn(searchDTO, paginationInfo));
//		model.addAttribute("dept", userService.selectOneUserInfo(searchDTO.getUserId()));
		model.addAttribute("progrsSttus", QestnProgrsSttus.values());
		
		return "/manage/qna/listAnswer";
	}
		
	/////////////////////////////////////////////
	/// @fn selectOneAnswer
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 단 건 조회 화면
	/// @param answerNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{qestnNo}/select.do", method=RequestMethod.GET)
	public String selectOneAnswer(@PathVariable("qestnNo") Long qestnNo, Model model) {
		model.addAttribute("data", qnaService.selectOneQna(qestnNo));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addAnswer
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsAnswer
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
	public String addAnswer(KwsAnswer kwsAnswer, Model model) throws Exception {
		// 작성자, 수정자
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsAnswer.setWrterId(userDTO.getUserId());
		kwsAnswer.setUpdUsrid(userDTO.getUserId());
		
		// 답변 등록
		model.addAttribute("rowCount", qnaService.addAnswer(kwsAnswer));
		
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyAnswer
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param answerNo
	/// @param deleteFileNos
	/// @param kwsAnswer
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
	@RequestMapping(value="{answerNo}/modify.do", method=RequestMethod.POST)
	public String modifyAnswer(@PathVariable("answerNo") Long answerNo, KwsAnswer kwsAnswer, Model model) throws FdlException, IllegalStateException, IOException {
		// 답변번호
		kwsAnswer.setAnswerNo(answerNo);

		// 수정자
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsAnswer.setUpdUsrid(userDTO.getUserId());

		// 답변수정
		model.addAttribute("rowCount", qnaService.modifyAnswer(kwsAnswer));
		
		return "jsonViewTextPlain";
	}
	
}
