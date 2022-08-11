package kr.co.gitt.kworks.projects.yg.web.manage.landCenter;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.projects.yg.service.ladCntr.LandCenterUseDownLogService;
import kr.co.gitt.kworks.service.dept.DeptService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/manage/landCenter/down/")
@Profile({"yg_dev", "yg_oper"})
public class LandCenterUseDownLogController {
	
	@Resource
	LandCenterUseDownLogService landCenterUseDownLogService;
	
	@Resource
	DomnCodeService domnCodeService;
	
	@RequestMapping(value="/list.do")
	public String landCenterUseDownLog(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 증명서 코드
		kwsDomnCode.setDomnId("KWS-0001");
		model.addAttribute("docCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 부서코드
		kwsDomnCode.setDomnId("KWS-224");
		model.addAttribute("deptCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		System.out.println("searchDTO.getPageIndex() : " + searchDTO.getPageIndex());
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		paginationInfo.setTotalRecordCount(landCenterUseDownLogService.listCount(searchDTO));
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", landCenterUseDownLogService.listSearch(searchDTO));
		
		return "/manage/ladCntr/landCenterUseDownHistoryLog";
	}
	
}