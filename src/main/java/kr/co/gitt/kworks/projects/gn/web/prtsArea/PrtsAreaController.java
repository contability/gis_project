package kr.co.gitt.kworks.projects.gn.web.prtsArea;

import javax.annotation.Resource;

import kr.co.gitt.kworks.projects.gn.dto.PrtsAreaDTO;
import kr.co.gitt.kworks.projects.gn.model.RdtPtimHt;
import kr.co.gitt.kworks.projects.gn.service.prtsArea.PrtsAreaService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/prtsArea/")
@Profile({"gn_dev","gn_oper"})
public class PrtsAreaController {
	
	@Resource
	private PrtsAreaService prtsAreaService;
	
	// 보호구역 리스트
	@RequestMapping(value="selectList.do", method=RequestMethod.POST)
	public String selectList(PrtsAreaDTO searchDTO, PaginationInfo paginationInfo, Model model){
		
		/*if(searchDTO.getPageIndex() > 1){
			paginationInfo.setCurrentPageNo(pageIndex);
		}{
			paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		}*/
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		
		searchDTO.setRecordCountPerPage(10);
		searchDTO.setPageSize(5);
		
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		
		model.addAttribute("rows", prtsAreaService.selectList(searchDTO, paginationInfo));
		
		return "jsonView";
	}
	
	@RequestMapping(value="selectFacilitiesList.do", method=RequestMethod.GET)
	public String selectFacilitiesList(Model model){
		
		return "jsonView";
	}
	
	
	///////////////////////////////////////////// 보호구역 개선사업이력
	
	@RequestMapping(value="rdtPtimHtSelectList.do", method=RequestMethod.GET)
	public String rdtPtimHtSelectList(@RequestParam("ftfCde") String ftfCde, @RequestParam("ftfIdn") Long ftfIdn, Model model){
		
		model.addAttribute("rows",prtsAreaService.rdtPtimHtSelectList(ftfIdn, ftfCde));
		
		return "jsonView";
	}
	
	@RequestMapping(value="rdtPtimHtSelectOnePage.do", method=RequestMethod.GET)
	public String rdtPtimHtSelectOnePage(){
		return "/projects/gn/job/prtsAreaHt/viewPrtsAreaHt";
	}
	
	@RequestMapping(value="{impIdn}/rdtPtimHtSelectOne.do", method=RequestMethod.GET)
	public String rdtPtimHtSelectOne(@PathVariable("impIdn") Long impIdn, Model model){
		model.addAttribute("row", prtsAreaService.rdtPtimHtSelectOne(impIdn));
		return "jsonView";
	}
	
	@RequestMapping(value="rdtPtimHtInsertPage.do", method=RequestMethod.GET)
	public String rdtPtimHtInsertPage(){
		return "/projects/gn/job/prtsAreaHt/addPrtsAreaHt";
	}
	
	@RequestMapping(value="rdtPtimHtInsert.do", method=RequestMethod.POST)
	public String rdtPtimHtInsert(RdtPtimHt rdtPtimHt, Model model) throws FdlException{
		model.addAttribute("result", prtsAreaService.rdtPtimHtInsert(rdtPtimHt));
		return "jsonView";
	}
	
	@RequestMapping(value="rdtPtimHtUpdatePage.do", method=RequestMethod.GET)
	public String rdtPtimHtUpdatePage(){
		return "/projects/gn/job/prtsAreaHt/editPrtsAreaHt";
	}
	
	@RequestMapping(value="rdtPtimHtUpdate.do", method=RequestMethod.POST)
	public String rdtPtimHtUpdate(RdtPtimHt rdtPtimHt, Model model){
		model.addAttribute("result", prtsAreaService.rdtPtimHtUpdate(rdtPtimHt));
		return "jsonView";
	}
	
	@RequestMapping(value="rdtPtimHtDelete.do", method=RequestMethod.POST)
	public String rdtPtimHtDelete(@RequestParam("impIdn") Long impIdn, Model model){
		model.addAttribute("result",prtsAreaService.rdtPtimHtDelete(impIdn));
		return "jsonView";
	}
	
	////////////////////////////// 부속자료
	@RequestMapping(value="insertAtchFilePage.do", method=RequestMethod.GET)
	public String insertAtchFilePage(){
		return "/projects/gn/job/prtsAreaHt/addAtchFile";
	}
}
