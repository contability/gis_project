package kr.co.gitt.kworks.projects.ss.web.window.cntrctRegstr;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.CntrctRegstrSearchDTO;
import kr.co.gitt.kworks.projects.ss.service.cntrctRegstr.CntrctRegstrService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/cntrctRegstr/")
@Profile({"ss_dev", "ss_oper"})
public class CntrctRegstrController {
	
	// 계약대장 서비스
	@Resource
	CntrctRegstrService cntrctRegstrService;

	@RequestMapping(value="list.do", method=RequestMethod.GET)
	public String listCntrctRegstr(CntrctRegstrSearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", cntrctRegstrService.list(searchDTO, paginationInfo));
		
		return "jsonView";
	}
	
	@RequestMapping(value="{ctrtAcctBookMngNo}/select.do", method=RequestMethod.GET)
	public String selectOneCntrctRegstr(@PathVariable("ctrtAcctBookMngNo") String ctrtAcctBookMngNo, Model model) {
		model.addAttribute("data", cntrctRegstrService.selectOneCntrctRegstr(ctrtAcctBookMngNo));
		return "jsonView";
	}
	
	@RequestMapping(value="listYears.do", method=RequestMethod.GET)
	public String listYears(Model model) {
		model.addAttribute("rows", cntrctRegstrService.listYears());
		return "jsonView";
	}

	@RequestMapping(value="listCtrtKinds.do", method=RequestMethod.GET)
	public String listCtrtKinds(Model model) {
		model.addAttribute("rows", cntrctRegstrService.listCodes("CM004"));
		return "jsonView";
	}
	
}
