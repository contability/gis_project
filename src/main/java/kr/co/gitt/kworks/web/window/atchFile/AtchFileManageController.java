package kr.co.gitt.kworks.web.window.atchFile;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.model.KwsAtch;
import kr.co.gitt.kworks.service.cmmn.AtchFileService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;

//부속 자료 컨트롤러
//2021.08.24 정신형
@Controller
@RequestMapping("/atchManage/")
public class AtchFileManageController {

	@Resource
	AtchFileService atchFileService;
	
	@RequestMapping(value="{ftrIdn}/{ftrCde}/listByFtr.do", method=RequestMethod.GET)
	public String listAtchFileByFtf(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("ftrCde") String ftrCde, Model model){
		KwsAtch kwsAtch = new KwsAtch();
		kwsAtch.setFtfIdn(ftrIdn);
		kwsAtch.setFtfCde(ftrCde);
		
		model.addAttribute("rows", atchFileService.listAtchFileByFtf(kwsAtch));
		
		return "jsonView";
	}
	
	@RequestMapping(value="{ftrIdn}/{ftrCde}/insertAtchFile.do", method=RequestMethod.POST)
	public String insertAtchFile(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("ftrCde") String ftrCde, KwsAtch kwsAtch, MultipartRequest mr, Model model) throws FdlException, IOException{
		kwsAtch.setFtfIdn(ftrIdn);
		kwsAtch.setFtfCde(ftrCde);
		
		model.addAttribute("result", atchFileService.insertAtchFile(kwsAtch, mr));
		
		return "jsonView";
	}
	
	@RequestMapping(value="deleteAtchFiles.do", method=RequestMethod.POST)
	public String deleteAtchFiles(@RequestParam("fileNos") Long[] fileNos, Model model){
		model.addAttribute("result", atchFileService.deleteAtchFiles(fileNos));
		return "jsonView";
	}
	
	@RequestMapping(value="{dataId}/downloadAtchFiles.do", method=RequestMethod.GET)
	public void downloadAtchFiles(@RequestParam("fileNos") Long[] fileNos, @PathVariable("dataId") String dataId, HttpServletResponse response, @RequestHeader(value="User-Agent") String userAgent) throws IOException{
		atchFileService.downloadAtchFiles(fileNos, dataId, response, userAgent);
	}
	
	@RequestMapping(value="{fileNo}/previewAtchFile.do", method=RequestMethod.GET)
	public void previewAtchFile(@PathVariable("fileNo") Long fileNo, @RequestHeader(value="User-Agent") String userAgent, HttpServletResponse response) throws IOException{
		atchFileService.previewAtchFile(fileNo, userAgent, response);
	}
}
