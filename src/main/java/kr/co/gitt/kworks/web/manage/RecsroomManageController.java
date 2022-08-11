package kr.co.gitt.kworks.web.manage;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsRecsroom;
import kr.co.gitt.kworks.service.recsroom.RecsroomService;

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
/// @class RecsroomManageController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ RecsroomManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 5. 오후 3:31:03 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 자료실 관리 컨트롤러 입니다. 
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/recsroom/")
public class RecsroomManageController {
	
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
		
		return "/manage/recsroom/listRecsroom";
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
		model.addAttribute("data", recsroomService.selectOneRecsroom(recsroomNo));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addRecsroom
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsRecsroom
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
	public String addRecsroom(KwsRecsroom kwsRecsroom, MultipartRequest multipartRequest, Model model) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsRecsroom.setWrterId(userDTO.getUserId());
		kwsRecsroom.setUpdusrId(userDTO.getUserId());
		
		model.addAttribute("rowCount", recsroomService.addRecsroom(kwsRecsroom, multipartRequest));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyRecsroom
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param recsroomNo
	/// @param deleteFileNos
	/// @param kwsRecsroom
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
	@RequestMapping(value="{recsroomNo}/modify.do", method=RequestMethod.POST)
	public String modifyRecsroom(@PathVariable("recsroomNo") Long recsroomNo, @RequestParam("deleteFileNo") List<Long> deleteFileNos, KwsRecsroom kwsRecsroom, MultipartRequest multipartRequest, Model model) throws FdlException, IllegalStateException, IOException {
		kwsRecsroom.setRecsroomNo(recsroomNo);
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsRecsroom.setUpdusrId(userDTO.getUserId());

		model.addAttribute("rowCount", recsroomService.modifyRecsroom(kwsRecsroom, multipartRequest, deleteFileNos));
		return "jsonViewTextPlain";
	}
	
	/////////////////////////////////////////////
	/// @fn removeRecsroom
	/// @brief 함수 간략한 설명 : 삭제
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
	@RequestMapping(value="{recsroomNo}/remove.do", method=RequestMethod.POST)
	public String removeRecsroom(@PathVariable("recsroomNo") Long recsroomNo, Model model) {
		model.addAttribute("rowCount", recsroomService.removeRecsroom(recsroomNo));
		return "jsonView";
	}
	
}
