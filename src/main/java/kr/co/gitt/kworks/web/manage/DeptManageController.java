package kr.co.gitt.kworks.web.manage;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsDept;
import kr.co.gitt.kworks.service.dept.DeptService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
/////////////////////////////////////////////
/// @class DeptManageController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ DeptManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 8. 16. 오후 2:43:09 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 부서 관리 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/dept/")
public class DeptManageController {

	/// 부서 서비스
	@Resource
	DeptService deptService;
	
	/////////////////////////////////////////////
	/// @fn listDeptPage
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
	public String listDeptPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
	
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", deptService.listDept(searchDTO, paginationInfo));
		
		return "/manage/dept/listDept";
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneDept
	/// @brief 함수 간략한 설명 : 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param deptCode
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{deptCode}/select.do", method=RequestMethod.GET)
	public String selectOneDept(@PathVariable("deptCode") String deptCode, Model model) {
		model.addAttribute("data", deptService.selectOneDept(deptCode));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn addDept
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsDept
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="add.do", method=RequestMethod.POST)
	public String addDept(KwsDept kwsDept, Model model) throws Exception {
		if(deptService.selectOneDept(kwsDept.getDeptCode()) != null){
			model.addAttribute("errMsg", "동일한 부서코드값이 존재합니다.");
		}else{
			model.addAttribute("rowCount", deptService.addDept(kwsDept));
		}
		
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn modifyDept
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsDept
	/// @param model
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{deptCode}/modify.do", method=RequestMethod.POST)
	public String modifyDept(@PathVariable("deptCode") String deptCode, KwsDept kwsDept, Model model) throws Exception {
		kwsDept.setDeptCode(deptCode);
		
		model.addAttribute("rowCount", deptService.modifyDept(kwsDept));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn removeDept
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param deptCode
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{deptCode}/remove.do", method=RequestMethod.POST)
	public String removeDept(@PathVariable("deptCode") String deptCode, Model model) {
		model.addAttribute("rowCount", deptService.removeDept(deptCode));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn deptExcelDownload
	/// @brief 함수 간략한 설명 : 엑셀 다운로드
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("excel.do")
	public ModelAndView deptExcelDownload(SearchDTO searchDTO) throws Exception {  
		Map<String, Object> map = new HashMap<String, Object>();  
	    map.put("category", deptService.listDeptExcel(searchDTO));
	    return new ModelAndView("deptExcelDownload", "categoryMap", map);
	}
	
}
