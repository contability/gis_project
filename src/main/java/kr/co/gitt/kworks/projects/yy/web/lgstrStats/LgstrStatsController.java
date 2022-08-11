package kr.co.gitt.kworks.projects.yy.web.lgstrStats;


import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.projects.yy.model.LgstrStats;
import kr.co.gitt.kworks.projects.yy.service.lgstrStats.LgstrStatsService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/lgstrStats/")
@Profile({"yy_dev", "yy_oper"})
public class LgstrStatsController {
	
	@Resource
	LgstrStatsService lgstrStatsService;
	
	@RequestMapping(value="listLgstrStats.do", method=RequestMethod.GET)
	public String listLgstrStats(@RequestParam("pageIndex") int pageIndex, SearchDTO searchDTO, PaginationInfo paginationInfo, Model model){
		if(pageIndex > 1){
			paginationInfo.setCurrentPageNo(pageIndex);
		}else{
			paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		}
		
		searchDTO.setRecordCountPerPage(25);
		searchDTO.setPageSize(5);
		
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());

		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", lgstrStatsService.listLgstrStats(searchDTO, paginationInfo));
		
		return "jsonView";
	}
	
	@RequestMapping(value="{lgstrStatsNo}/selectPage.do", method=RequestMethod.GET)
	public String selectOnePageLgstrStats(@PathVariable("lgstrStatsNo") Long lgstrStatsNo, Model model){
		return "/projects/yy/job/lgstrStats/detailLgstrStats";
	}
	
	@RequestMapping(value="{lgstrStatsNo}/select.do", method=RequestMethod.GET)
	public String selectOneLgstrStats(@PathVariable("lgstrStatsNo") Long lgstrStatsNo, Model model){
		model.addAttribute("data", lgstrStatsService.selectOneLgstrStats(lgstrStatsNo));
		return "jsonView";
	}
	
	@RequestMapping(value="addLgstrStatsPage.do", method=RequestMethod.GET)
	public String addLgstrStatsPage(LgstrStats lgstrStats, Model model){
		return "/projects/yy/job/lgstrStats/addLgstrStats";
	}
	
	@RequestMapping(value="addLgstrStatsRes.do", method=RequestMethod.POST)
	public String addLgstrStatsRes(String lgstrStatsSj, String lgstrStatsCn, MultipartRequest multipartRequest, Model model) throws Exception{
		UserDTO userDTO = (UserDTO)EgovUserDetailsHelper.getAuthenticatedUser();
		LgstrStats lgstrStats = new LgstrStats();
		lgstrStats.setLgstrStatsSj(lgstrStatsSj);
		lgstrStats.setLgstrStatsCn(lgstrStatsCn);
		lgstrStats.setWrterId(userDTO.getUserId());
		
		model.addAttribute("rowCount", lgstrStatsService.addLgstrStats(lgstrStats, multipartRequest));
		return "jsonViewTextPlain";
	}
	
	@RequestMapping(value="{lgstrStatsNo}/modifyPage.do", method=RequestMethod.GET)
	public String modifyLgstrStatsPage(@PathVariable("lgstrStatsNo") Long lgstrStatsNo, Model model){
		model.addAttribute("data", lgstrStatsService.selectOneLgstrStats(lgstrStatsNo));
		return "/projects/yy/job/lgstrStats/modifyLgstrStats";
	}
	
	@RequestMapping(value="{lgstrStatsNo}/modify.do", method=RequestMethod.POST)
	public String modifyLgstrStatsRes(@PathVariable("lgstrStatsNo") Long lgstrStatsNo, @RequestParam("deleteFileNo") Long deleteFileNo, String lgstrStatsSj, String lgstrStatsCn, MultipartRequest multipartRequest, Model model) throws FdlException, IllegalStateException, IOException{
		UserDTO userDTO = (UserDTO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		LgstrStats lgstrStats = new LgstrStats();
		lgstrStats.setLgstrStatsNo(lgstrStatsNo);
		lgstrStats.setLgstrStatsSj(lgstrStatsSj);
		lgstrStats.setLgstrStatsCn(lgstrStatsCn);
		lgstrStats.setWrterId(userDTO.getUserId());
		
		model.addAttribute("rowCount", lgstrStatsService.modifyLgstrStats(lgstrStats, multipartRequest, deleteFileNo));
		
		return "jsonViewTextPlain";
	}
	
	@RequestMapping(value="{lgstrStatsNo}/remove.do", method=RequestMethod.POST)
	public String removeLgstrStats(@PathVariable("lgstrStatsNo") Long lgstrStatsNo, Model model){
		model.addAttribute("rowCount", lgstrStatsService.removeLgstrStats(lgstrStatsNo));
		return "jsonView";
	}
}
