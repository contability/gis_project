package kr.co.gitt.kworks.web.window.local;

import java.io.IOException;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.FileDTO;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.model.RdtLoclMa;
import kr.co.gitt.kworks.projects.gn.model.RdlSectLs;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;
import kr.co.gitt.kworks.service.local.LocalPlanFileService;
import kr.co.gitt.kworks.service.local.LocalPlanService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;

@Controller
@RequestMapping("/localPlan/")
@Profile({"gn_dev", "gn_oper"})
public class LocalManageController {
	
	/// 단위도면 관리 서비스
	@Resource
	LocalPlanService localPlanService;
	
	/// 단위도면 파일 서비스
	@Resource
	LocalPlanFileService localPlanFileService;
	
	// 도메인코드 서비스
	@Resource
	DomnCodeService domnCodeService;
	
	@RequestMapping(value="{ftrIdn}/list.do", method=RequestMethod.GET)
	public String listLocalPlanByFtrIdn(@PathVariable("ftrIdn") Long ftrIdn, RdtLoclMa rdtLoclMa, Model model) {
		
		rdtLoclMa.setFtrIdn(ftrIdn);
		model.addAttribute("data", localPlanService.selectOneByFtrIdn(ftrIdn));
		model.addAttribute("rows", localPlanService.listAllFile(rdtLoclMa));

		return "jsonView";
	}

	@RequestMapping(value="{fileNo}/select.do", method=RequestMethod.GET)
	public String selectOneLocalPlan(@PathVariable("fileNo") Long fileNo, Model model) {
		
		model.addAttribute("data", localPlanService.selectOneFile(fileNo));
		
		return "jsonView";
	}
	
	@RequestMapping(value="{fileNo}/remove.do", method=RequestMethod.GET)
	public String removeLocalPlan(@PathVariable("fileNo") Long fileNo, FileDTO fileDTO, Model model) {
		
		model.addAttribute("data", localPlanService.removeFile(fileDTO));
		
		return "jsonView";
	}
	
	@RequestMapping(value="{fileNo}/modifyLocalPlanPage.do", method=RequestMethod.GET)
	public String modifySectionPlanPage(@PathVariable("fileNo") Long fileNo, RdtLoclMa rdtLoclMa, Model model) {
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		// 도면 종류
		kwsDomnCode.setDomnId("KWS-0418");
		model.addAttribute("lclCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		rdtLoclMa.setFileNo(fileNo);
		
		model.addAttribute("result", localPlanService.selectOneFile(fileNo));
		model.addAttribute("file", localPlanFileService.listLocalPlanFile(rdtLoclMa));
		
		return "/window/localPlan/modifyLocalPlan";
	}
	
	@RequestMapping(value="{fileNo}/modifyLocalPlanFile.do", method=RequestMethod.GET)
	public String modifySecionPlanFile(@PathVariable("fileNo") Long fileNo, Model model) {
		
		RdtLoclMa rdtLoclMa = new RdtLoclMa();
		rdtLoclMa.setFileNo(fileNo);
		model.addAttribute("data", localPlanFileService.listLocalPlanFile(rdtLoclMa));
				
		return "jsonView";
	}
	
	@RequestMapping(value="{fileNo}/modifyLocalPlan.do", method=RequestMethod.POST)
	public String modifySectionPlan(@PathVariable("fileNo") Long fileNo, FileDTO fileDTO, MultipartRequest multipartRequest, Model model) throws FdlException, IllegalStateException, IOException{
		
		fileDTO.getFileNo();
		model.addAttribute("rowCount", localPlanService.modifyLocalPlan(fileDTO, multipartRequest));
		
		return "jsonViewTextPlain";
	}
	
	@RequestMapping(value="{ftrIdn}/addLocalPlanPage.do", method=RequestMethod.GET)
	public String addLocalPlanPage(@PathVariable("ftrIdn") Long ftrIdn, RdlSectLs rdlSectLs, FileDTO fileDTO, Model model) {
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		fileDTO.setFtrIdn(ftrIdn);
		
		// 도면 종류
		kwsDomnCode.setDomnId("KWS-0418");
		model.addAttribute("lclCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/window/localPlan/addLocalPlan";
	}

	@RequestMapping(value="{ftrIdn}/addLocalPlan.do", method=RequestMethod.POST)
	public String addLocalPlan(@PathVariable("ftrIdn") Long ftrIdn, FileDTO fileDTO, MultipartRequest multipartRequest, Model model) throws FdlException, IllegalStateException, IOException{
		
		fileDTO.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", localPlanService.addLocalPlan(fileDTO, multipartRequest));
		
		return "jsonViewTextPlain";
	}
}
