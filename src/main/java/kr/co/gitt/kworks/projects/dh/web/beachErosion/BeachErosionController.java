package kr.co.gitt.kworks.projects.dh.web.beachErosion;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.projects.dh.dto.BeachErosionRequestDTO;
import kr.co.gitt.kworks.projects.dh.service.beachErosion.BeachErosionService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class BeachErosionController
/// kr.co.gitt.kworks.projects.dh.web.beachErosion \n
///   ㄴ BeachErosionController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 8. 22. 오후 4:28:38 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 해안 침식 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/beachErosion/")
@Profile({"dh_dev", "dh_oper"})
public class BeachErosionController {

	/// 해안 침식 서비스
	@Resource
	BeachErosionService beachErosService;
	
	/////////////////////////////////////////////
	/// @fn getComboboxData
	/// @brief 함수 간략한 설명 : 검색 콤보 박스 정보 반환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param fieldName
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{fieldName}/combobox.do")
	public String getComboboxData(@PathVariable("fieldName") String fieldName, Model model) {
		List<String> data = null;
		if(StringUtils.equals("mesrYy", fieldName)) {
			data = beachErosService.searchMserYy();
		}
		else if(StringUtils.equals("bganMt", fieldName)) {
			data = beachErosService.searchBganMt();
		}
		else if(StringUtils.equals("edanMt", fieldName)) {
			data = beachErosService.searchEdanMt();
		}
		else if(StringUtils.equals("zoneNm", fieldName)) {
			data = beachErosService.searchZoneNm();
		}
		model.addAttribute("data", data);
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn listCount
	/// @brief 함수 간략한 설명 : 목록 수 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param beachErosionRequestDTO
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="listCount.do", method=RequestMethod.GET)
	public String listCount(BeachErosionRequestDTO beachErosionRequestDTO, Model model) {
		model.addAttribute("rowCount", beachErosService.listCount(beachErosionRequestDTO));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param beachErosionRequestDTO
	/// @param paginationInfo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="list.do", method=RequestMethod.GET)
	public String list(BeachErosionRequestDTO beachErosionRequestDTO, PaginationInfo paginationInfo, Model model) {
		paginationInfo.setCurrentPageNo(beachErosionRequestDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(beachErosionRequestDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(beachErosionRequestDTO.getPageSize());
		
		beachErosionRequestDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		beachErosionRequestDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		beachErosionRequestDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("rows", beachErosService.list(beachErosionRequestDTO));
		return "jsonView";
	}
	
	
}
