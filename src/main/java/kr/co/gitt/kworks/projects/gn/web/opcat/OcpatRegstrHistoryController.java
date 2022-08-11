package kr.co.gitt.kworks.projects.gn.web.opcat;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.projects.gn.service.ocpat.OcpatRegstrHistoryService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/manage/ocpatRegstr/")
@Profile({"gn_dev", "gn_oper"})
public class OcpatRegstrHistoryController {

	@Resource
	OcpatRegstrHistoryService opcatRegstrHistoryService;
	
	@Resource
	DomnCodeService domnCodeService;

	
	@RequestMapping(value="edit/list.do")
	public String editHistory(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		
		// 편집 타입
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		kwsDomnCode.setDomnId("KWS-0378");
		model.addAttribute("editType", domnCodeService.listDomnCode(kwsDomnCode));
		
		System.out.println("searchDTO.getPageIndex() : " + searchDTO.getPageIndex());
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		paginationInfo.setTotalRecordCount(opcatRegstrHistoryService.editCount());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", opcatRegstrHistoryService.editSearch(searchDTO));
		
		return "/manage/ocpatRegstr/ocpatEditHistory";
	}
	
	@RequestMapping(value="download/list.do")
	public String downloadHistory(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");

		// 부속자료 코드
		kwsDomnCode.setDomnId("KWS-0443");
		model.addAttribute("docCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		System.out.println("searchDTO.getPageIndex() : " + searchDTO.getPageIndex());
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		paginationInfo.setTotalRecordCount(opcatRegstrHistoryService.editCount());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", opcatRegstrHistoryService.downloadSearch(searchDTO));
		
		return "/manage/ocpatRegstr/ocpatDownloadHistory";
	}
	
}
