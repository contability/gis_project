package kr.co.gitt.kworks.projects.gn.web.manage.spatialInfoData;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.model.SpatialInfoData;
import kr.co.gitt.kworks.projects.gn.service.spatialInfoData.SpatialInfoDataService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/manage/spatialInfoData/")
@Profile({"gn_dev", "gn_oper"})
public class SpatialInfoDataController {
	
	@Resource
	SpatialInfoDataService spatialInfoDataService;
	
	@RequestMapping(value = "list.do", method = RequestMethod.GET)
	public String listSpatialInfoData(SearchDTO searchDTO, String startDate, String endDate, PaginationInfo paginationInfo, Model model){
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", spatialInfoDataService.listSpatialInfoData(searchDTO, paginationInfo, startDate, endDate));
		
		if(startDate != null && startDate != ""){
			model.addAttribute("startDate", startDate);
		}
		
		if(endDate != null && endDate != ""){
			model.addAttribute("endDate", endDate);
		}
		
		return "/manage/spatialInfoData/listSpatialInfoData";
	}
	
	@RequestMapping(value="{dtaNo}/select.do", method = RequestMethod.GET)
	public String selectOneSpatialInfoData(@PathVariable("dtaNo") Long dtaNo, Model model){
		
		model.addAttribute("row", spatialInfoDataService.selectSpatialInfoData(dtaNo));
		
		return "jsonView";
	}
	
	@RequestMapping(value="add.do", method=RequestMethod.POST)
	public String addSpatialInfoData(SpatialInfoData spatialInfoData, Model model){
		
		try {
			model.addAttribute("rowCount", spatialInfoDataService.addSpatialInfoData(spatialInfoData));
		} catch (FdlException e) {
			e.printStackTrace();
		}
		
		return "jsonView";
	}
	
	@RequestMapping(value="modify.do", method=RequestMethod.POST)
	public String modifySpatialInfoData(SpatialInfoData spatialInfoData, Model model){
		
		model.addAttribute("rowCount", spatialInfoDataService.modifySpatialInfoData(spatialInfoData));
		
		return "jsonView";
	}
	
	@RequestMapping(value="{dtaNo}/remove.do", method=RequestMethod.POST)
	public String removeSpatialInfoData(@PathVariable("dtaNo") Long dtaNo, Model model){
		
		model.addAttribute("rowCount", spatialInfoDataService.removeSpatialInfoData(dtaNo));
		
		return "jsonView";
	}
	
	
}
