package kr.co.gitt.kworks.web.self;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsQestn;
import kr.co.gitt.kworks.model.KwsQestn.QestnProgrsSttus;
import kr.co.gitt.kworks.service.qna.QnaService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class QestnQnaController
/// kr.co.gitt.kworks.web.self \n
///   ㄴ QestnQnaController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 10. 오전 11:44:40 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 묻고답하기-질문 관리 컨트롤러 입니다. 
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/self/qna/")
public class QestnQnaController {
	
	/// 묻고답하기 서비스
	@Resource
	QnaService qnaService;
	
	/////////////////////////////////////////////
	/// @fn listQestnPage
	/// @brief 함수 간략한 설명 : 목록화면
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
	public String listQestnPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) throws Exception {
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		/// 등록한 사용자만 조회 가능하도록 수정
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		searchDTO.setUserGrad(userDTO.getUserGrad());
		searchDTO.setUserId(userDTO.getUserId());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", qnaService.listQestn(searchDTO, paginationInfo));
		model.addAttribute("progrsSttus", QestnProgrsSttus.values());
		
		return "/self/qna/listQestn";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneQestn
	/// @brief 함수 간략한 설명 : 단 건 조회 화면
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param qestnNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{qestnNo}/select.do", method=RequestMethod.GET)
	public String selectOneQestn(@PathVariable("qestnNo") Long qestnNo, Model model) throws Exception {
		/// 등록한 사용자만 조회 가능하도록 수정
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		KwsQestn selectQestn = qnaService.selectOneQna(qestnNo);
		String Weter = selectQestn.getWrterId();
		int idx = Weter.indexOf(" "); 
		String WeterId = Weter.substring(idx+1);
		if(!StringUtils.equals(WeterId, userDTO.getUserId())) {
			throw new Exception("등록한 사용자만 조회할 수 있습니다.");
		}
		
		
		model.addAttribute("data", selectQestn);
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addQetstn
	/// @brief 함수 간략한 설명 :등록 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsQestn
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
	public String addQetstn(KwsQestn kwsQestn, MultipartRequest multipartRequest, Model model) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsQestn.setWrterId(userDTO.getUserId());
		
		model.addAttribute("rowCount", qnaService.addQestn(kwsQestn, multipartRequest));
		
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyQestn
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param qestnNo
	/// @param deleteFileNos
	/// @param kwsQestn
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
	@RequestMapping(value="{qestnNo}/modify.do", method=RequestMethod.POST)
	public String modifyQestn(@PathVariable("qestnNo") Long qestnNo, @RequestParam("deleteFileNo") List<Long> deleteFileNos, KwsQestn kwsQestn, MultipartRequest multipartRequest, Model model) throws Exception {
		// 등록한 사용자이면서 답변 중인 경우에만 수정 가능
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		KwsQestn selectQestn = qnaService.selectOneQna(qestnNo);
		String Weter = selectQestn.getWrterId();
		int idx = Weter.indexOf(" "); 
		String WeterId = Weter.substring(idx+1);
		
		if(!StringUtils.equals(WeterId, userDTO.getUserId())) {
			throw new Exception("작성한 사용자만 수정할 수 있습니다.");
		}
		if(selectQestn.getProgrsSttus() != QestnProgrsSttus.ANSWER_WAITING) {
			throw new Exception("답변 중인 게시물만 수정할 수 있습니다.");
		}
		
		model.addAttribute("rowCount", qnaService.modifyQestn(kwsQestn, multipartRequest, deleteFileNos));
		
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn removeQestn
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param qestnNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{qestnNo}/remove.do", method=RequestMethod.POST)
	public String removeQestn(@PathVariable("qestnNo") Long qestnNo, Model model) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		KwsQestn selectQestn = qnaService.selectOneQna(qestnNo);
		String Weter = selectQestn.getWrterId();
		int idx = Weter.indexOf(" "); 
		String WeterId = Weter.substring(idx+1);
		
		if(!StringUtils.equals(WeterId, userDTO.getUserId())) {
			throw new Exception("작성한 사용자만 삭제할 수 있습니다.");
		}
		if(selectQestn.getProgrsSttus() != QestnProgrsSttus.ANSWER_WAITING) {
			throw new Exception("답변 중인 게시물만 삭제할 수 있습니다.");
		}
		
		model.addAttribute("rowCount", qnaService.removeQestn(qestnNo));
		
		return "jsonView";
	}
	
}
