package kr.co.gitt.kworks.projects.gn.web.roadRegstr;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsDataAuthor;
import kr.co.gitt.kworks.model.KwsRoadReg;
import kr.co.gitt.kworks.projects.gn.dto.RoadRegisterBBoxSearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.RoadRegisterSearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.RoadRegisterSortDTO;
import kr.co.gitt.kworks.projects.gn.model.RdlSectLs;
import kr.co.gitt.kworks.projects.gn.service.roadRegstr.RoadRegstrService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

@Controller
@RequestMapping("/roadRegstr/")
@Profile({"gn_dev", "gn_oper"})
public class RoadRegstrManageController {
	
	// logger
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 도로대장 서비스
	@Resource
	RoadRegstrService roadRegstrService;
	
	/// 메세지 소스
	@Resource(name="messageSource")
    private MessageSource messageSource;
	
	
	@RequestMapping(value="searchRoadRegstrPage.do", method=RequestMethod.GET)
	public String searchRoadRegstrPage(RdlSectLs rdlSectLs, Model model) {
		
		return "/projects/gn/job/roadRegstr/listRoadRegstr";
	}
	
	@RequestMapping(value="listRoadRegstr.do", method=RequestMethod.GET)
	public String listRoadRegstr(RdlSectLs rdlSectLs, Model model) {

		model.addAttribute("result", roadRegstrService.listRoadRegstr(rdlSectLs));
		
		return "jsonView";
	}
	
	
	/////////////////////////////////////////////
	/// @fn listAll
	/// @brief 함수 간략한 설명 : 도로대장 목록 전체 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("listAll.do")
	public String listRoadReg(Model model) {
		model.addAttribute("rows", roadRegstrService.listAll());
		return "jsonView";
	}	
	
	@RequestMapping("rotNamAll.do")
	public String listRotNameAll(Model model) throws Exception {
		model.addAttribute("rows", roadRegstrService.rotNameAll());
		return "jsonView";
	}
	
	@RequestMapping(value="searchRoadRegisterSummaries.do", method=RequestMethod.POST)
	public String searchRoadRegSummaries(RoadRegisterSearchDTO roadRegisterSearchDTO, Model model) throws Exception {
		
		List<KwsRoadReg> roadRegs = roadRegstrService.listSelected(roadRegisterSearchDTO.getRegIdns(), true);
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		
		for(int i=roadRegs.size()-1; i >= 0; i--) {
			KwsRoadReg roadReg = roadRegs.get(i);
			
			String layerId = roadReg.getRegLayerId();
			boolean isAuthor = false;
			for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
				for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
					if(StringUtils.equals(layerId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
						isAuthor = true;
						break;
					}
				}
			}
			
			if(!isAuthor) {
				roadRegs.remove(roadReg.getRegIdn());
			}
		}
		
		if(roadRegs.size() <= 0) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}
		
		model.addAttribute("rows", roadRegstrService.searchSummaries(roadRegs, roadRegisterSearchDTO));
		return "jsonView";
	}

	@RequestMapping(value="sortRoadRegister.do", method=RequestMethod.POST)
	public String sortRoadRegister(RoadRegisterSortDTO roadRegisterSortDTO, Model model)  throws Exception {
		
		KwsRoadReg roadReg = roadRegstrService.selectOne(roadRegisterSortDTO.getRegIdn());
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		
		String layerId = roadReg.getRegLayerId();
		boolean isAuthor = false;
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(layerId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}
			
		if(!isAuthor) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}
		
		model.addAttribute("rows", roadRegstrService.sortRoadRegister(roadReg, roadRegisterSortDTO));
		return "jsonView";
	}
	
	@RequestMapping(value="searchRoadRegister.do", method=RequestMethod.POST)
	public String searchRoadRegister(RoadRegisterSortDTO roadRegisterSortDTO, Model model)  throws Exception {
	
		KwsRoadReg roadReg = roadRegstrService.selectOne(roadRegisterSortDTO.getRegIdn());
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();

		String layerId = roadReg.getRegLayerId();
		boolean isAuthor = false;
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(layerId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}
			
		if(!isAuthor) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}
		
		model.addAttribute("rows", roadRegstrService.searchRoadRegister(roadReg, roadRegisterSortDTO));
		return "jsonView";
	}

	@RequestMapping(value="{regIdn}/searchBBox.do", method=RequestMethod.POST)
	public String searchRoadRegisterByBBox(@PathVariable("regIdn") String regIdn, RoadRegisterBBoxSearchDTO roadRegisterSortDTO, Model model)  throws Exception {

		KwsRoadReg roadReg = roadRegstrService.selectOne(regIdn);
		String layerId = roadReg.getRegLayerId();
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();

		boolean isAuthor = false;
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(layerId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}
			
		if(!isAuthor) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}
		
		model.addAttribute("rows", roadRegstrService.searchByBBox(roadReg, roadRegisterSortDTO));
		return "jsonView";
	}
	
	@RequestMapping(value="{regIdn}/searchVideoByBBox.do", method=RequestMethod.POST)
	public String searchRoadVideoByBBox(@PathVariable("regIdn") String regIdn, RoadRegisterBBoxSearchDTO roadRegisterSortDTO, Model model)  throws Exception {

		KwsRoadReg roadReg = roadRegstrService.selectOne(regIdn);
		String layerId = roadReg.getRegLayerId();
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		
		boolean isAuthor = false;
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(layerId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}		
		
		if(!isAuthor) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}
		
		model.addAttribute("rows", roadRegstrService.searchVideoByBBox(roadReg, roadRegisterSortDTO));
		return "jsonView";		
	}
	
	@RequestMapping(value="sortRoadVideo.do", method=RequestMethod.POST)
	public String sortRoadVideo(RoadRegisterSortDTO roadRegisterSortDTO, Model model)  throws Exception {
		
		KwsRoadReg roadReg = roadRegstrService.selectOne(roadRegisterSortDTO.getRegIdn());
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		
		String layerId = roadReg.getRegLayerId();
		boolean isAuthor = false;
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(layerId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}
			
		if(!isAuthor) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}
		
		model.addAttribute("rows", roadRegstrService.sortRoadVideo(roadReg, roadRegisterSortDTO));
		return "jsonView";
	}
	
	@RequestMapping(value="searchRoadVideo.do", method=RequestMethod.POST)
	public String searchRoadVideo(RoadRegisterSortDTO roadRegisterSortDTO, Model model)  throws Exception {
	
		KwsRoadReg roadReg = roadRegstrService.selectOne(roadRegisterSortDTO.getRegIdn());
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();

		String layerId = roadReg.getRegLayerId();
		boolean isAuthor = false;
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(layerId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}
			
		if(!isAuthor) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}
		
		model.addAttribute("rows", roadRegstrService.searchRoadVideo(roadReg, roadRegisterSortDTO));
		return "jsonView";
	}

	@RequestMapping(value="{regIdn}/{pathIdn}/roadVideo.do", method=RequestMethod.GET)
	public void downloadRoadVideo(@PathVariable("regIdn") String regIdn, @PathVariable("pathIdn") String pathIdn, HttpServletResponse response, @RequestHeader(value="User-Agent") String userAgent) throws Exception {

		KwsRoadReg roadReg = roadRegstrService.selectOne(regIdn);
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();

		String layerId = roadReg.getRegLayerId();
		boolean isAuthor = false;
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(layerId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}
			
		if(!isAuthor) {
			logger.warn("데이터 권한이 없습니다.");
		}
		
		String filename = roadRegstrService.selectOneVideo(roadReg, pathIdn);
		String parentPath = messageSource.getMessage("Com.RoadRegister.Movie.Path", null, Locale.getDefault());
		String filepath = parentPath + filename; 
		InputStream is = new FileInputStream(filepath);
		
		String docName = null;
		if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")) {
			docName = URLEncoder.encode(filename,"UTF-8").replaceAll("\\+", "%20")+";";
		}
		else {
			docName = "\"" + new String(filename.getBytes("UTF-8"), "ISO-8859-1") + "\"";
		}
		response.setHeader("Content-Disposition", "attachment; filename="+docName);
		
		OutputStream os = response.getOutputStream();
		IOUtils.copy(is,  os);
		is.close();
		os.close();
	}
	
	@RequestMapping(value="{regIdn}/searchRoadAreaByBBox.do", method=RequestMethod.POST)
	public String searchRoadAreaByBBox(@PathVariable("regIdn") String regIdn, RoadRegisterBBoxSearchDTO roadRegisterSortDTO, Model model)  throws Exception {

		KwsRoadReg roadReg = roadRegstrService.selectOne(regIdn);
		String layerId = roadReg.getRegLayerId();
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();

		boolean isAuthor = false;
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(layerId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}
			
		if(!isAuthor) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}
		
		model.addAttribute("rows", roadRegstrService.searchRoadAreaByBBox(roadReg, roadRegisterSortDTO));
		return "jsonView";
	}
	
}