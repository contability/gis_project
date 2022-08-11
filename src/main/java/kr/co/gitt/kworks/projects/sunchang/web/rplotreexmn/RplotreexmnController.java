package kr.co.gitt.kworks.projects.sunchang.web.rplotreexmn;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.sunchang.dto.kwsRplotreexmnLgstrDTO;
import kr.co.gitt.kworks.projects.sunchang.service.rplotreexmn.RplotreexmnService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/rplotreexmn/")
@Profile({"sunchang_dev","sunchang_oper"})
public class RplotreexmnController {
	
	@Resource
	private RplotreexmnService rplotreexmnService;
	
	//리스트 검색
	@RequestMapping(value="selectList.do", method=RequestMethod.GET)
	public String selectList(@RequestParam("pageIndex") int pageIndex, @RequestParam("pnu") String pnu, @RequestParam("bsnsAreaNm") String bsnsAreaNm, @RequestParam("bsnsSe") String bsnsSe, kwsRplotreexmnLgstrDTO searchDTO, PaginationInfo paginationInfo, Model model){
		if(searchDTO.getPageIndex() > 1){
			paginationInfo.setCurrentPageNo(pageIndex);
		}else{
			paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		}
		
		searchDTO.setRecordCountPerPage(10);
		searchDTO.setPageSize(5);
		
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", rplotreexmnService.selectList(pnu, bsnsAreaNm, bsnsSe, searchDTO, paginationInfo));
		
		return "jsonView";
	}
	
	//지적확정 상세정보 페이지
	@RequestMapping(value="selectOnePage.do", method=RequestMethod.GET)
	public String selectOnePage(){
		return "/projects/sunchang/job/rplotreexmn/detailRplotreexmn";
	}
	
	//상세정보 조회
	@RequestMapping(value="{rplotreexmnNo}/selectOne.do", method=RequestMethod.GET)
	public String selectOne(@PathVariable("rplotreexmnNo") Long rplotreexmnNo, Model model){
		model.addAttribute("rplotreexmn",rplotreexmnService.selectOne(rplotreexmnNo));
		model.addAttribute("rplotreexmnLgstr",rplotreexmnService.selectOneLgstr(rplotreexmnNo));
		return "jsonView";
	}
	
	//재조사 상세정보 페이지
	@RequestMapping(value="reexmnSelectOnePage.do", method=RequestMethod.GET)
	public String reexmnSelectOnePage(){
		return "/projects/sunchang/job/reexmn/detailReexmn";
	}
	
}
