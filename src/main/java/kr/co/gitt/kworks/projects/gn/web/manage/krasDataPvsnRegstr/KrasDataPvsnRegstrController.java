package kr.co.gitt.kworks.projects.gn.web.manage.krasDataPvsnRegstr;

import javax.annotation.Resource;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.KrasDataPvsnRegstrDTO;
import kr.co.gitt.kworks.projects.gn.service.krasDataPvsnRegstr.KrasDataPvsnRegstrService;

@Controller
@RequestMapping("/manage/krasDataPvsnRegstr/")
@Profile({"gn_dev", "gn_oper"})
public class KrasDataPvsnRegstrController {
	
	@Resource(name = "krasDataPvsnRegstrService")
	KrasDataPvsnRegstrService krasDataPvsnRegstrService;
	
	@RequestMapping(value = "list.do", method = RequestMethod.GET)
	public String listMngKrasDataPvsnRegstrPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", krasDataPvsnRegstrService.listMngKrasDataPvsnRegstr(searchDTO, paginationInfo));
		
		String searchStartDt = searchDTO.getSearchStartDt();
		String searchEndDt = searchDTO.getSearchEndDt();
		
		if(searchStartDt != null && searchStartDt != ""){
			model.addAttribute("searchStartDt", searchStartDt);
		}
		
		if(searchEndDt != null && searchEndDt != ""){
			model.addAttribute("searchEndDt", searchEndDt);
		}
		
		return "/projects/gn/manage/krasDataPvsnRegstr/listKrasDataPvsnRegstr";
	}
	
	@RequestMapping(value = "{dtaNo}/select.do", method = RequestMethod.GET)
	public String selectOneMngKrasDataPvsnRegstr(@PathVariable("dtaNo") Long dtaNo, Model model) {
		model.addAttribute("row", krasDataPvsnRegstrService.selectOnrMngKrasDataPvsnRegstr(dtaNo));
		return "jsonView";
	}
	
	@RequestMapping(value = "add.do", method = RequestMethod.POST)
	public String addMngKrasDataPvsnRegstr(KrasDataPvsnRegstrDTO krasDataPvsnRegstrDTO, Model model) throws FdlException {
		model.addAttribute("rowCount", krasDataPvsnRegstrService.addMngKrasDataPvsnRegstr(krasDataPvsnRegstrDTO));
		return "jsonView";
	}
	
	@RequestMapping(value = "modify.do", method = RequestMethod.POST)
	public String modifyMngKrasDataPvsnRegstr(KrasDataPvsnRegstrDTO krasDataPvsnRegstrDTO, Model model) {
		model.addAttribute("rowCount", krasDataPvsnRegstrService.modifyMngKrasDataPvsnRegstr(krasDataPvsnRegstrDTO));
		return "jsonView";
	}
	
	@RequestMapping(value = "{dtaNo}/remove.do", method = RequestMethod.POST)
	public String removeMngKrasDataPvsnRegstr(@PathVariable("dtaNo") Long dtaNo, Model model) {
		model.addAttribute("rowCount", krasDataPvsnRegstrService.removeMngKrasDataPvsnRegstr(dtaNo));
		return "jsonView";
	}
}
