package kr.co.gitt.kworks.projects.yg.web.manage.landCenter;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.projects.yg.service.ladCntr.LandCenterEditLogService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/manage/landCenter/edit/")
@Profile({"yg_dev", "yg_oper"})
public class LandCenterEditLogController {
	
	@Resource
	LandCenterEditLogService landCenterEditLogService;
	
	@Resource
	DomnCodeService domnCodeService;
	
	@RequestMapping(value="/list.do")
	public String landCenterEditLog(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 대장 코드
		kwsDomnCode.setDomnId("KWS-0002");
		model.addAttribute("ledgCode", domnCodeService.listDomnCode(kwsDomnCode));
		
		// 편집 타입
		kwsDomnCode.setDomnId("KWS-0003");
		model.addAttribute("editType", domnCodeService.listDomnCode(kwsDomnCode));
		
		System.out.println("searchDTO.getPageIndex() : " + searchDTO.getPageIndex());
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		paginationInfo.setTotalRecordCount(landCenterEditLogService.listCount(searchDTO));
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", landCenterEditLogService.listSearch(searchDTO));
		
		return "/manage/ladCntr/landCenterEditLog";
	}
	
}