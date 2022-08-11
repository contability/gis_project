package kr.co.gitt.kworks.web.window.video;

import java.io.IOException;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.FileDTO;
import kr.co.gitt.kworks.model.RdtVideoMa;
import kr.co.gitt.kworks.service.video.VideoManageFileService;
import kr.co.gitt.kworks.service.video.VideoManageService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;

@Controller
@RequestMapping("/videoManage/")
@Profile({"gn_dev", "gn_oper"})
public class VideoManageController {
	
	// 영상관리 파일 관리 서비스
	@Resource
	VideoManageService videoManageService;
	
	// 영상관리 파일 서비스
	@Resource
	VideoManageFileService videoManageFileService;
	
	@RequestMapping(value="{ftrIdn}/select.do", method=RequestMethod.GET)
	public String selectOneVideoManage(@PathVariable("ftrIdn") Long ftrIdn, Model model) {
		
		model.addAttribute("data", videoManageService.selectOneFile(ftrIdn));
				
		return "jsonView";
	}
	
	@RequestMapping(value="{ftrIdn}/modifyVideoManagePage.do", method=RequestMethod.GET)
	public String modifyVideoManagePage(@PathVariable("ftrIdn") Long ftrIdn, RdtVideoMa rdtVideoMa, Model model) {
		
		rdtVideoMa.setFtrIdn(ftrIdn);
		model.addAttribute("result", videoManageService.selectOneFile(ftrIdn));
		model.addAttribute("file", videoManageFileService.listVideoManageFile(rdtVideoMa));
		
		return "/window/videoManage/modifyVideoManage";
	}
	
	@RequestMapping(value="{ftrIdn}/modifyVideoManageFile.do", method=RequestMethod.GET)
	public String modifyVideoManageFile(@PathVariable("ftrIdn") Long ftrIdn, Model model) {
		
		RdtVideoMa rdtVideoMa = new RdtVideoMa();
		rdtVideoMa.setFtrIdn(ftrIdn);
		model.addAttribute("data", videoManageFileService.listVideoManageFile(rdtVideoMa));
		
		return "jsonView";
	}
	
	@RequestMapping(value="{ftrIdn}/modifyVideoManage.do", method=RequestMethod.POST)
	public String modifyVideoManage(@PathVariable("ftrIdn") Long ftrIdn, FileDTO fileDTO, MultipartRequest multipartRequest, Model model) throws FdlException, IllegalStateException, IOException{
		
		model.addAttribute("rowCount", videoManageService.modifyVideoManage(fileDTO, multipartRequest));
		
		return "jsonViewTextPlain";
	}
}