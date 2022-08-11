package kr.co.gitt.kworks.web.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsError;
import kr.co.gitt.kworks.service.log.ErrorLogService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/manage/log/")
public class ErrorLogController {
	
	@Resource
	ErrorLogService errorLogService;
	
	// 전체 리스트 출력
	@RequestMapping(value="/errorLog.do")
	public String errorLogPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		
		System.out.println("searchDTO.getPageIndex() : " + searchDTO.getPageIndex());
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		paginationInfo.setTotalRecordCount(errorLogService.listCount(searchDTO));
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", errorLogService.listSearch(searchDTO));
		
		return "/manage/log/errorLog";
	}
	
	// 에러 단건 조회 & 상세보기
	@RequestMapping(value="/errorLog/{errorNo}/View.do", method=RequestMethod.GET)
	public String errorLogInqire(@PathVariable("errorNo") Integer errorNo, Model model) {
		KwsError result = errorLogService.selectOne(errorNo);
		Date errorDt = result.getErrorDt();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd (E)");
		
		model.addAttribute("result", result);
		model.addAttribute("errorDt", formatter.format(errorDt));
		return "jsonView";
	}
	
	@RequestMapping("excel.do")
	public ModelAndView errorLogExcelDownload(SearchDTO searchDTO) throws Exception {  
		Map<String, Object> map = new HashMap<String, Object>();  
	    map.put("category", errorLogService.listErrorLogExcel(searchDTO));
	    return new ModelAndView("errorLogExcelDownload", "categoryMap", map);
	}
	
}