package kr.co.gitt.kworks.web.manage;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.dto.baseMap.BaseMapSearchDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsBaseMap.MapType;
import kr.co.gitt.kworks.model.KwsSys;
import kr.co.gitt.kworks.model.KwsSys.SysTy;
import kr.co.gitt.kworks.service.authorGroup.AuthorGroupService;
import kr.co.gitt.kworks.service.baseMap.BaseMapService;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.sys.SysService;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class AuthorGroupManageController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ AuthorGroupManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 23. 오전 11:20:18 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 권한 그룹 관리 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/authorGroup/")
public class AuthorGroupManageController {

	// 권한 그룹 관리 서비스
	@Resource
	AuthorGroupService authorGroupService;
	
	// 시스템  서비스
	@Resource
	SysService sysService;
	
	// 데이터 서비스
	@Resource
	DataService dataService;
	
	// 기본지도 서비스
	@Resource
	BaseMapService baseMapService;
	
	/////////////////////////////////////////////
	/// @fn listAuthorGroupPage
	/// @brief 함수 간략한 설명 : 권한 그룹 목록 화면
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
	public String listAuthorGroupPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		KwsSys kwsSys = new KwsSys();
		kwsSys.setSysTy(SysTy.SYSTEM);
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", authorGroupService.listAuthorGroup(searchDTO, paginationInfo));
		model.addAttribute("sysData", sysService.listAllSys(kwsSys));
		model.addAttribute("dataCtgry", dataService.listAllDataCtgry());
		model.addAttribute("data", dataService.listAllData(null));
		
		BaseMapSearchDTO baseMapSearchDTO = new BaseMapSearchDTO();
		baseMapSearchDTO.setMapType(MapType.FLIGHT);
		model.addAttribute("baseMaps", baseMapService.listAllBaseMap(baseMapSearchDTO));
		
		return "/manage/authorGroup/listAuthorGroup";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneAuthorGroup
	/// @brief 함수 간략한 설명 : 권한 그룹 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param authorGroupNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{authorGroupNo}/select.do", method=RequestMethod.GET)
	public String selectOneAuthorGroup(@PathVariable("authorGroupNo") Long authorGroupNo, Model model) {
		model.addAttribute("data", authorGroupService.selectOneAuthorGroup(authorGroupNo));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addAuthorGroup
	/// @brief 함수 간략한 설명 : 권한 그룹 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param data
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="add.do", method=RequestMethod.POST)
	public String addAuthorGroup(String data, Model model) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		KwsAuthorGroup kwsAuthorGroup = objectMapper.readValue(data, KwsAuthorGroup.class);
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsAuthorGroup.setWrterId(userDTO.getUserId());
		kwsAuthorGroup.setUpdusrId(userDTO.getUserId());
		
		model.addAttribute("rowCount", authorGroupService.addAuthorGroup(kwsAuthorGroup));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyAuthorGroup
	/// @brief 함수 간략한 설명 : 권한 그룹 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param authorGroupNo
	/// @param data
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{authorGroupNo}/modify.do", method=RequestMethod.POST)
	public String modifyAuthorGroup(@PathVariable("authorGroupNo") Long authorGroupNo, String data, Model model) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		KwsAuthorGroup kwsAuthorGroup = objectMapper.readValue(data, KwsAuthorGroup.class);
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		kwsAuthorGroup.setUpdusrId(userDTO.getUserId());
		kwsAuthorGroup.setAuthorGroupNo(authorGroupNo);
		
		model.addAttribute("rowCount", authorGroupService.modifyAuthorGroup(kwsAuthorGroup));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn removeAuthorGroup
	/// @brief 함수 간략한 설명 : 권한 그룹 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param authorGroupNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{authorGroupNo}/remove.do", method=RequestMethod.POST)
	public String removeAuthorGroup(@PathVariable("authorGroupNo") Long authorGroupNo, Model model) {
		model.addAttribute("rowCount", authorGroupService.removeAuthorGroup(authorGroupNo));
		return "jsonView";
	}
	
}
