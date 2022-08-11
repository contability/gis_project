package kr.co.gitt.kworks.rest.bookMark;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsUserBkmk;
import kr.co.gitt.kworks.service.bookMark.BookMarkService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/////////////////////////////////////////////
/// @class BookMarkController
/// kr.co.gitt.kworks.rest.bookMark \n
///   ㄴ BookMarkController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 3:07:19 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 북마크 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/rest/bookMark/")
public class BookMarkController {

	/// 북마크 서비스
	@Resource
	BookMarkService bookMarkService;
	
	/////////////////////////////////////////////
	/// @fn listAllBookMark
	/// @brief 함수 간략한 설명 : 북마크 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listAll.do", method=RequestMethod.GET)
	public String listAllBookMark(Model model) {
		KwsUserBkmk kwsUserBkmk = new KwsUserBkmk();
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsUserBkmk.setUserId(userDTO.getUserId());
		
		model.addAttribute("rows", bookMarkService.listAllBookMark(kwsUserBkmk));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addBookMark
	/// @brief 함수 간략한 설명 : 북마크 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsUserBkmk
	/// @param jsonStr
	/// @param request
	/// @param model
	/// @return
	/// @throws FdlException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="add.do", method=RequestMethod.POST)
	public String addBookMark(KwsUserBkmk kwsUserBkmk, String jsonStr, HttpServletRequest request, Model model) throws FdlException, IOException {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsUserBkmk.setUserId(userDTO.getUserId());
		
		if(bookMarkService.selectOneUserBkmk(kwsUserBkmk) != null) {
			model.addAttribute("errMsg", "동일한 북마크 명칭이 존재합니다.");
		} else {
			model.addAttribute("rowCount", bookMarkService.addBookMark(kwsUserBkmk));
		}
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn removeBookMark
	/// @brief 함수 간략한 설명 : 북마크 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bkmkNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{bkmkNo}/remove.do", method=RequestMethod.POST)
	public String removeBookMark(@PathVariable("bkmkNo") Long bkmkNo, Model model) {
		model.addAttribute("rowCount", bookMarkService.removeBookMark(bkmkNo));
		return "jsonView";
	}
	
}
