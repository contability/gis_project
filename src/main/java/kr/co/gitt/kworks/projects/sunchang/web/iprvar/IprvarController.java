package kr.co.gitt.kworks.projects.sunchang.web.iprvar;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.sunchang.model.KwsIprvarDt;
import kr.co.gitt.kworks.projects.sunchang.model.KwsIprvarDtFile;
import kr.co.gitt.kworks.projects.sunchang.service.iprvar.IprvarService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/iprvar/")
@Profile({"sunchang_dev","sunchang_oper"})
public class IprvarController {
	
	@Resource
	private IprvarService iprvarSerivce;
	
	//정비보류지역 관리조서 리스트
	@RequestMapping(value="selectList.do", method=RequestMethod.GET)
	public String selectList(@RequestParam("pageIndex") int pageIndex, @RequestParam("pnu") String pnu, SearchDTO searchDTO, PaginationInfo paginationInfo, Model model){
		
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
		model.addAttribute("rows", iprvarSerivce.selectList(pnu, searchDTO, paginationInfo));
		
		return "jsonView";
	}
	
	//정비보류지역 관리조서 단건 조회 페이지
	@RequestMapping(value="selectOnePage.do", method=RequestMethod.GET)
	public String selectOnePage(){
		return "/projects/sunchang/job/iprvar/detailIprvar";
	}
	
	//정비보류지역 관리조서 단건 조회
	@RequestMapping(value="{iprvarNo}/selectOne.do", method=RequestMethod.GET)
	public String selectOne(@PathVariable("iprvarNo") Long iprvarNo, Model model){
		model.addAttribute("row",iprvarSerivce.selectOne(iprvarNo));
		model.addAttribute("iprvarFiles", iprvarSerivce.fileSelectList(iprvarNo));
		return "jsonView";
	}
	
	//정비보류지역 관리조서 등록 페이지
	@RequestMapping(value="insertPage.do", method=RequestMethod.GET)
	public String insertPage(KwsIprvarDt kwsIprvarDt, Model model){
		return "/projects/sunchang/job/iprvar/addIprvar";
	}
	
	//정비보류지역 관리조서 등록
	@RequestMapping(value="insert.do", method=RequestMethod.POST)
	public String insert(KwsIprvarDt kwsIprvarDt, Model model){
		try {
			model.addAttribute("result", iprvarSerivce.insert(kwsIprvarDt));
		} catch (FdlException e) {
			e.printStackTrace();
		}
		return "jsonView";
	}
	
	//정비보류지역 관리조서 수정 페이지
	@RequestMapping(value="{iprvarNo}/updatePage.do", method=RequestMethod.GET)
	public String updatePage(@PathVariable("iprvarNo") Long iprvarNo, Model model){
		model.addAttribute("row",iprvarSerivce.selectOne(iprvarNo));
		return "/projects/sunchang/job/iprvar/updateIprvar";
	}
	
	//정비보류지역 관리조서 수정
	@RequestMapping(value="update.do", method=RequestMethod.POST)
	public String update(KwsIprvarDt kwsIprvarDt, Model model){
		model.addAttribute("result", iprvarSerivce.update(kwsIprvarDt));
		return "jsonView";
	}
	
	//정비보류지역 관리조서 삭제
	@RequestMapping(value="delete.do", method=RequestMethod.POST)
	public String delete(@RequestParam("iprvarNo") Long iprvarNo, Model model){
		model.addAttribute("result", iprvarSerivce.delete(iprvarNo));
		return "jsonView";
	}
	
	//현재 유효한 pnu 값인지 검색
	@RequestMapping(value="chkPnu.do", method=RequestMethod.GET)
	public String chkPnu(@RequestParam("pnu") String pnu, Model model){
		model.addAttribute("chkPnu",iprvarSerivce.chkPnu(pnu));
		return "jsonView";
	}
	
	
	/////////////////////////////////////////// 부속자료
	
	//정비보류지역 관리조서 부속자료 리스트
	@RequestMapping(value="{iprvarNo}/fileSelectList.do", method=RequestMethod.GET)
	public String fileSelectList(@PathVariable("iprvarNo") Long iprvarNo, Model model){
		model.addAttribute("rows", iprvarSerivce.fileSelectList(iprvarNo));
		return "jsonView";
	}
	
	//정비보류지역 관리조서 부속자료 등록 페이지
	@RequestMapping(value="{iprvarNo}/fileInsertPage.do", method=RequestMethod.GET)
	public String fileInsertPage(@PathVariable("iprvarNo") Long iprvarNo, Model model){
		model.addAttribute("iprvarNo", iprvarNo);
		return "/projects/sunchang/job/iprvar/addIprvarFile";
	}
	
	//정비보류지역 관리조서 부속자료 등록
	@RequestMapping(value="fileInsert.do", method=RequestMethod.POST)
	public String fileInsert(KwsIprvarDtFile kwsIprvarDtFile, MultipartRequest mr, Model model){
		try {
			model.addAttribute("result", iprvarSerivce.fileInsert(kwsIprvarDtFile, mr));
		} catch (FdlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "jsonView";
	}
	
	//정비보류지역 관리조서 부속자료 삭제
	@RequestMapping(value="fileDelete.do", method=RequestMethod.POST)
	public String fileDelete(@RequestParam("iprvarFileNos") String[] iprvarFileNos, @RequestParam("fileNos") String[] fileNos, Model model){
		model.addAttribute("result", iprvarSerivce.fileDelete(iprvarFileNos, fileNos));
		return "jsonView";
	}
	
	@RequestMapping(value="{fileNo}/iprvarFileChk.do")
	public String iprvarFileChk(@PathVariable("fileNo") Long fileNo, Model model){
		model.addAttribute("row",iprvarSerivce.iprvarFileChk(fileNo));
		return "jsonView";
	}
	
	@RequestMapping(value="{fileNo}/iprvarImgPreview.do", method=RequestMethod.GET)
	public void iprvarImgPreview(@PathVariable("fileNo") Long fileNo, HttpServletResponse response, @RequestHeader(value="User-Agent") String userAgent){
		
	}
	
	@RequestMapping(value="{fileNo}/iprvarPdfPreview.do", method=RequestMethod.GET)
	public void iprvarPdfPreview(@PathVariable("fileNo") Long fileNo, HttpServletResponse response, @RequestHeader(value="User-Agent") String userAgent){
		try {
			iprvarSerivce.iprvarPdfPreview(fileNo, response, userAgent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="iprvarFileDownload.do", method=RequestMethod.GET)
	public void iprvarFileDownload(@RequestParam("chkArr") String chkArr, HttpServletResponse response, @RequestHeader(value="User-Agent") String userAgent){
		try {
			iprvarSerivce.iprvarFileDownload(chkArr, response, userAgent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
