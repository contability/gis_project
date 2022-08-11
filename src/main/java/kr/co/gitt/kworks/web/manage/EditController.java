package kr.co.gitt.kworks.web.manage;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.DataCtgrySearchDTO;
import kr.co.gitt.kworks.model.KwsEdit;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.edit.EditService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class EditController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ EditController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdAhn |
///    | Date | 2017. 12. 20. 오후 6:20:30 |
///    | Class Version | v1.0 |
///    | 작업자 | jdAhn, Others... |
/// @section 상세설명
/// - 이 클래스는 편집여부 관리 컨트롤러입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/edit/")
public class EditController {

	/// 편집여부 서비스
	@Resource
	EditService editService;
	
	/// 데이터 서비스
	@Resource
	DataService dataService;
	
	
	/////////////////////////////////////////////
	/// @fn listEditPage
	/// @brief 함수 간략한 설명 : 편집여부 목록 화면
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
	public String listEditPage(DataCtgrySearchDTO dataCtgrySearchDTO, PaginationInfo paginationInfo, Model model){
		paginationInfo.setCurrentPageNo(dataCtgrySearchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(dataCtgrySearchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(dataCtgrySearchDTO.getPageSize());
		
		dataCtgrySearchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		dataCtgrySearchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		dataCtgrySearchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", editService.listEdit(dataCtgrySearchDTO, paginationInfo));
		model.addAttribute("dataCtgrylist", dataService.listAllDataCtgry());
		
		return "/manage/edit/listEdit";
	}
	
	
	/////////////////////////////////////////////
	/// @fn selectOneEdit
	/// @brief 함수 간략한 설명 : 편집여부 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{dataId}/select.do", method=RequestMethod.GET)
	public String selectOneEdit(@PathVariable("dataId") String dataId, Model model){
		model.addAttribute("data",editService.selectOneEdit(dataId));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyEdit
	/// @brief 함수 간략한 설명 : 편집여부 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @param kwsEdit
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{dataId}/modify.do", method=RequestMethod.POST)
	public String modifyEdit(@PathVariable("dataId") String dataId, KwsEdit kwsEdit, Model model) throws Exception{
		model.addAttribute("data", editService.modifyEdit(kwsEdit));
		
		return "jsonView";
	}
}
