package kr.co.gitt.kworks.projects.gn.web.manage.spatialInfoRegstr;

import javax.annotation.Resource;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.MngGrphinfoManageRegstrDTO;
import kr.co.gitt.kworks.projects.gn.service.spatialInfoRegstr.SpatialInfoRegstrService;

@Controller
@RequestMapping("/manage/spatialInfoRegstr/")
@Profile({"gn_dev", "gn_oper"})
public class spatialInfoRegstrController {
	
	@Resource
	SpatialInfoRegstrService spatialInfoRegstrService;	
	
	@RequestMapping(value = "list.do", method = RequestMethod.GET)
	public String listMngGrphinfoManageRegstrPage(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", spatialInfoRegstrService.listMngGrphinfoManageRegstr(searchDTO, paginationInfo));
		return "/projects/gn/manage/spatialInfoRegstr/listSpatialInfoRegstr";
	}
	
	@RequestMapping(value = "{regstrSn}/select.do", method = RequestMethod.GET)
	public String selectOneMngGrphinfoManageRegstrPage(@PathVariable("regstrSn") Long regstrSn, Model model) {
		model.addAttribute("row", spatialInfoRegstrService.selectOneMngGrphinfoManageRegstr(regstrSn));
		model.addAttribute("uploadRows", spatialInfoRegstrService.selectMngGrphinfoUploadDtls(regstrSn));
		return "jsonView";
	}
	
	@RequestMapping(value="modify.do", method=RequestMethod.POST)
	public String modifyMngGrphinfoManageRegstr(MngGrphinfoManageRegstrDTO mngGrphinfoManageRegstrDTO, Model model){
		String grphinfoUploadDe = mngGrphinfoManageRegstrDTO.getGrphinfoUploadDe().replace(".", "");
		mngGrphinfoManageRegstrDTO.setGrphinfoUploadDe(grphinfoUploadDe);
		
		model.addAttribute("rowCount", spatialInfoRegstrService.modifyMngGrphinfoManageRegstr(mngGrphinfoManageRegstrDTO));
		return "jsonView";
	}
}
