package kr.co.gitt.kworks.web.window.section;

import java.io.IOException;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.FileDTO;
import kr.co.gitt.kworks.model.RdtSectMa;
import kr.co.gitt.kworks.service.section.SectionPlanFileService;
import kr.co.gitt.kworks.service.section.SectionPlanService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;

@Controller
@RequestMapping("/sectionPlan/")
@Profile({"gn_dev", "gn_oper"})
public class SectionManageController {
	
	// 구간도면 파일 관리 서비스
	@Resource
	SectionPlanService sectionPlanService;
	
	// 구간도면 파일 서비스
	@Resource
	SectionPlanFileService sectionPlanFileService;
	
	@RequestMapping(value="{ftrIdn}/select.do", method=RequestMethod.GET)
	public String selectOneSectionPlan(@PathVariable("ftrIdn") Long ftrIdn, Model model) {
		
		model.addAttribute("data", sectionPlanService.selectOneFile(ftrIdn));
		
		return "jsonView";
	}
	
	@RequestMapping(value="{ftrIdn}/modifySectionPlanPage.do", method=RequestMethod.GET)
	public String modifySectionPlanPage(@PathVariable("ftrIdn") Long ftrIdn, RdtSectMa rdtSectMa, Model model) {
		
		rdtSectMa.setFtrIdn(ftrIdn);
		model.addAttribute("result", sectionPlanService.selectOneFile(ftrIdn));
		model.addAttribute("file", sectionPlanFileService.listSectionPlanFile(rdtSectMa));
		
		return "/window/sectionPlan/modifySectionPlan";
	}
	
	@RequestMapping(value="{ftrIdn}/modifySectionPlanFile.do", method=RequestMethod.GET)
	public String modifySecionPlanFile(@PathVariable("ftrIdn") Long ftrIdn, Model model) {
		
		RdtSectMa rdtSectMa = new RdtSectMa();
		rdtSectMa.setFtrIdn(ftrIdn);
		model.addAttribute("data", sectionPlanFileService.listSectionPlanFile(rdtSectMa));
				
		return "jsonView";
	}
	
	@RequestMapping(value="{ftrIdn}/modifySectionPlan.do", method=RequestMethod.POST)
	public String modifySectionPlan(@PathVariable("ftrIdn") Long ftrIdn, FileDTO fileDTO, MultipartRequest multipartRequest, Model model) throws FdlException, IllegalStateException, IOException{
		
		model.addAttribute("rowCount", sectionPlanService.modifySectionPlan(fileDTO, multipartRequest));
		
		return "jsonViewTextPlain";
	}
	
}