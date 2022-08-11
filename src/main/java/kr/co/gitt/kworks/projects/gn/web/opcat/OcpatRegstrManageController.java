package kr.co.gitt.kworks.projects.gn.web.opcat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsDataAuthor;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.model.KwsOcpatReg;
import kr.co.gitt.kworks.projects.gn.dto.OcpatRegisterSearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.OcpatRegisterSortDTO;
import kr.co.gitt.kworks.projects.gn.dto.OcpatRegisterSpatialSearchDTO;
import kr.co.gitt.kworks.projects.gn.dto.OcpatRegstrDTO;
import kr.co.gitt.kworks.projects.gn.model.OcpatDownHi;
import kr.co.gitt.kworks.projects.gn.model.RdtOccaDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOccnDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcdrDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcdsDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcpatFile;
import kr.co.gitt.kworks.projects.gn.model.RdtOcpeDt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcpeHt;
import kr.co.gitt.kworks.projects.gn.model.RdtOcreDt;
import kr.co.gitt.kworks.projects.gn.service.ocpat.RdtOcpatFileService;
import kr.co.gitt.kworks.projects.gn.service.ocpat.OcpatAtachDtaService;
import kr.co.gitt.kworks.projects.gn.service.ocpat.OcpatRegstrHistoryService;
import kr.co.gitt.kworks.projects.gn.service.ocpat.OcpatRegstrService;
import kr.co.gitt.kworks.service.cmmn.ImageService;
import kr.co.gitt.kworks.service.file.FileService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
@RequestMapping("/ocpat/")
@Profile({"gn_dev", "gn_oper"})
public class OcpatRegstrManageController {
	
	// logger
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 사진관리 서비스
	@Resource
	ImageService imageService;
	
	// 점용허가 부속자료 서비스
	@Resource
	RdtOcpatFileService rdtOcpatFileService;
	
	// 점용허가 서비스
	@Resource
	OcpatRegstrService ocpatRegstrService;
	
	// 변경이력, 부속자료 
	@Resource
	OcpatAtachDtaService ocpatAtachDtaService;
	
	/// 파일 서비스
	@Resource
	FileService fileService;

	/**
	 * 점용대장 이력관리 서비스 
	 */
	@Resource
	OcpatRegstrHistoryService ocpatRegstrHistoryService;
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// 행정읍면동 갱신
	//
	@RequestMapping("reloadUmdNam.do")
	public String reloadUmdNam(Model model) {
		model.addAttribute("result", ocpatRegstrService.reloadUmdNam());
		return "jsonViewTextPlain";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// 검색
	//
	
	/**
	 * 점용대장 목록
	 * @param model
	 * @return
	 */
	@RequestMapping("listAll.do")
	public String listOcpatReg(Model model) {
		model.addAttribute("rows", ocpatRegstrService.listAll());
		return "jsonView";
	}
	
	/**
	 * 통합검색 페이지 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="totalSearchPage.do", method=RequestMethod.GET)
	public String totalSearchPage(Model model) {
		
		return "/projects/gn/job/ocpat/listOcpatRegstr";
	}
	
	/**
	 * 통합검색
	 * @param ocpatRegisterSearchDTO 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="searchRegisterSummaries.do", method=RequestMethod.POST)
	public String searchRoadRegSummaries(OcpatRegisterSearchDTO ocpatRegisterSearchDTO, Model model) throws Exception {
		// 권한체크
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		
		List<KwsOcpatReg> ocpatRegs = ocpatRegstrService.listAll();
		for (int i = ocpatRegs.size()-1; i >= 0; i--) {
			KwsOcpatReg ocpatReg = ocpatRegs.get(i);
			String layerId = ocpatReg.getOcpatLayerId();
			
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
				ocpatRegs.remove(ocpatReg.getOcpatLayerId());
			}
		}
		
		if(ocpatRegs.size() <= 0) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}
		
		model.addAttribute("rows", ocpatRegstrService.searchSummaries(ocpatRegs, ocpatRegisterSearchDTO));
		return "jsonView";
	}

	/**
	 * 공간검색
	 * @param ocpatIdn
	 * @param ocpatRegBBoxSearchDTO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="{ocpatIdn}/searchByBBox.do", method=RequestMethod.POST)
	public String searchByBBox(@PathVariable("ocpatIdn") String ocpatIdn, OcpatRegisterSpatialSearchDTO ocpatRegBBoxSearchDTO, Model model)  throws Exception {
		// 점용대장 정보  
		KwsOcpatReg ocpatReg = ocpatRegstrService.selectOne(ocpatIdn);
		String layerId = ocpatReg.getOcpatLayerId();
		
		// 권한체크
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
		
		model.addAttribute("rows", ocpatRegstrService.searchByBBox(ocpatReg, ocpatRegBBoxSearchDTO));
		return "jsonView";
	}
	
	/**
	 * 검색결과 정렬 
	 * @param ocpatRegisterSortDTO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="sortRegister.do", method=RequestMethod.POST)
	public String sortRegister(OcpatRegisterSortDTO ocpatRegisterSortDTO, Model model)  throws Exception {
		// 점용대장 정보  
		KwsOcpatReg ocpatReg = ocpatRegstrService.selectOne(ocpatRegisterSortDTO.getOcpatIdn());
		String layerId = ocpatReg.getOcpatLayerId();

		// 권한체크
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
		
		model.addAttribute("rows", ocpatRegstrService.sortRegister(ocpatReg, ocpatRegisterSortDTO));
		return "jsonView";
	}
	
	/**
	 * 상세 조회
	 * @param ocpatRegisterSortDTO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="searchRegister.do", method=RequestMethod.POST)
	public String searchRegister(OcpatRegisterSortDTO ocpatRegisterSortDTO, Model model)  throws Exception {
		// 점용대장 정보  
		KwsOcpatReg ocpatReg = ocpatRegstrService.selectOne(ocpatRegisterSortDTO.getOcpatIdn());
		String layerId = ocpatReg.getOcpatLayerId();

		// 권한체크
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
		List<EgovMap> allOcpat=ocpatRegstrService.searchRegister(ocpatReg, ocpatRegisterSortDTO);
		model.addAttribute("rows", allOcpat);
		return "jsonView";
	}

	/**
	 * 빠른 조회
	 * @param ocpatSpatialSearchDTO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="{pnu}/quickSearch.do", method=RequestMethod.POST)
	public String quickSsearch(@PathVariable("pnu") String pnu, OcpatRegisterSpatialSearchDTO ocpatSpatialSearchDTO, Model model)  throws Exception {
		// 권한체크
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		
		List<KwsOcpatReg> ocpatRegs = ocpatRegstrService.listAll();
		for (int i = ocpatRegs.size()-1; i >= 0; i--) {
			KwsOcpatReg ocpatReg = ocpatRegs.get(i);
			String layerId = ocpatReg.getOcpatLayerId();
			
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
				ocpatRegs.remove(ocpatReg.getOcpatLayerId());
			}
		}
		
		if(ocpatRegs.size() <= 0) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}
		
		model.addAttribute("rows", ocpatRegstrService.quickSearch(ocpatRegs, ocpatSpatialSearchDTO, pnu));
		return "jsonView";
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// 점용대장
	//
	
	/**
	 * 대장등록 페이지
	 * @param ocpatIdn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="{ocpatIdn}/addRegstrPage.do", method=RequestMethod.GET)
	public String addRegstrPage(@PathVariable("ocpatIdn") Integer ocpatIdn, Model model)  throws Exception {
		// 점용대장 정보  
		KwsOcpatReg ocpatReg = ocpatRegstrService.selectOne(Integer.toString(ocpatIdn));
		
		model.addAttribute("ocpatIdn", ocpatReg.getOcpatIdn());
		model.addAttribute("ftrCde", ocpatReg.getOcpatPropValue());
        return "/projects/gn/job/ocpat/addOcpatRegstr";
	}

	/**
	 * 대장 등록
	 * @param ocpatRegstr - 대장
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addRegstr.do", method=RequestMethod.POST)
	public String addRegstr(OcpatRegstrDTO ocpatRegstr, Model model) throws Exception {

		model.addAttribute("rowCount", ocpatRegstrService.insertRegster(ocpatRegstr));
		return "jsonViewTextPlain";
	}	

	/**
	 * 점용허가대장 대장조회페이지
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="{ocpatIdn}/viewRdtOcpeDtlist.do", method=RequestMethod.GET)
	public String modifyRdtOcpeDtlist(@PathVariable("ocpatIdn") Integer ocpatIdn, Model model) throws Exception {
        switch(ocpatIdn){
	    	case 100100 : 
	    	case 200100 :
	    	case 300100 :
	    	case 400100 :
	    		return "/projects/gn/job/ocpat/viewRdtOcpeDtRegstr";
	    	case 500100 :
	    		return "/projects/gn/job/ocpat/viewRdtOccnDtRegstr";
	    	case 600100 :
	    		return "/projects/gn/job/ocpat/viewRdtOcreDtRegstr";
	    	case 700100 :
	    		return "/projects/gn/job/ocpat/viewRdtOcdsDtRegstr";
	    	case 800100 :
	    		return "/projects/gn/job/ocpat/viewRdtOccaDtRegstr";
	    	case 900100 :
	    		return "/projects/gn/job/ocpat/viewRdtOcdrDtRegstr";
	    	default :
	    		return "";
        }
	}
	
	/**
	 * 행정읍면동 업데이트
	 * @param layerId
	 * @param ftrCde
	 * @param ftrIdn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="{layerId}/{ftrCde}/{ftrIdn}/updateRegstrUmd.do", method=RequestMethod.GET)
	public String addRegstrPage(@PathVariable("layerId") String layerId, @PathVariable("ftrCde") String ftrCde, @PathVariable("ftrIdn") Long ftrIdn, Model model)  throws Exception {
		
		model.addAttribute("state", ocpatRegstrService.updateRegsterUmd(layerId, ftrCde, ftrIdn));
		return "jsonView";
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// 변경이력
	//
	
	/**
	 * 변경이력 리스트 컨트롤러 
	 * @param - ftrIdn /ftrCde
	 * RdtOcpeHt 메소드
	 */
	@RequestMapping(value="{ftrIdn}/{ftrCde}/rdtOcpeHtlistAll.do", method=RequestMethod.GET)
	public String listRdtOcpeHt(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("ftrCde") String ftrCde, Model model){
		
		RdtOcpeHt rdtOcpeHt = new RdtOcpeHt();
		
		rdtOcpeHt.setFtrIdn(ftrIdn);
		rdtOcpeHt.setFtrCde(ftrCde);
		
		model.addAttribute("rows", ocpatAtachDtaService.listRdtOcpeHt(rdtOcpeHt));
		return "jsonView";
	}
	
	/**
	 * 변경이력 등록 페이지
	 * @param model
	 * @return
	 */
	@RequestMapping(value="{ftrIdn}/{ftrCde}/addRdtOcpeHtPage.do", method=RequestMethod.GET)
	public String addRdtOcpeHt(@PathVariable("ftrIdn") Integer ftrIdn,@PathVariable("ftrCde") String ftrCde, Model model) {
		
		model.addAttribute("ftrIdn", ftrIdn);
		model.addAttribute("ftrCde", ftrCde);
		return "/projects/gn/job/ocpat/addRdtOcpeHt";
	}

	/**
	 * 변경이력 등록
	 * @param model
	 * @return
	 */
	@RequestMapping(value="addRdtOcpeHt.do", method=RequestMethod.POST)
	public String addRdtOcpeHt(RdtOcpeHt rdtOcpeHt, Model model) throws Exception{
		
		model.addAttribute("rowCount", ocpatAtachDtaService.addRdtOcpeHt(rdtOcpeHt));
		return "jsonViewTextPlain";
	}
	
	/**
	 * 변경이력 수정페이지
	 * @param model
	 * @return
	 */
	@RequestMapping(value="{histNo}/modifyRdtOcpeHtPage.do", method=RequestMethod.GET)
	public String modifyRdtOcpeHtPage(@PathVariable("histNo") Long histNo, Model model) throws Exception{
		
		model.addAttribute("result", ocpatAtachDtaService.selectOneRdtOcpeHt(histNo));
		return "/projects/gn/job/ocpat/modifyRdtOcpeHt";
	}
	
	/**
	 * 변경이력 수정
	 * @param model
	 * @return
	 */
	@RequestMapping(value="{histNo}/modifyRdtOcpeHt.do", method=RequestMethod.POST)
	public String modifyRdtOcpeHt(@PathVariable("histNo") Long histNo, RdtOcpeHt rdtOcpeHt, Model model) throws Exception{
		
		rdtOcpeHt.setHistNo(histNo);
		model.addAttribute("rowCount", ocpatAtachDtaService.modifyRdtOcpeHt(rdtOcpeHt));
		return "jsonViewTextPlain";
	}
	
	/**
	 * 변경이력 삭제
	 * @param model
	 * @return
	 */
	@RequestMapping(value="{histNo}/removeRdtOcpeHt.do", method=RequestMethod.POST)
	public String removeRdtOcpeHt(@PathVariable("histNo") Long histNo, Model model) throws Exception{
		
		model.addAttribute("rowCount", ocpatAtachDtaService.removeRdtOcpeHt(histNo));
		return "jsonView";
	}

	
	
	//수정 삭제 부분
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="{ftrIdn}/modifyRdtOccaDtPage.do", method=RequestMethod.GET)
	public String modifyRdtOccaDtPage(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception{
		
		model.addAttribute("result", ocpatAtachDtaService.selectOneRdtOccaDt(ftrIdn));
		return "/projects/gn/job/ocpat/modifyRdtOccaDt";
	}
	
	@RequestMapping(value="{ftrIdn}/modifyRdtOccnDtPage.do", method=RequestMethod.GET)
	public String modifyRdtOccnDtPage(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception{
		
		model.addAttribute("result", ocpatAtachDtaService.selectOneRdtOccnDt(ftrIdn));
		return "/projects/gn/job/ocpat/modifyRdtOccnDt";
	}
	
	@RequestMapping(value="{ftrIdn}/modifyRdtOcdrDtPage.do", method=RequestMethod.GET)
	public String modifyRdtOcdrDtPage(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception{
		
		model.addAttribute("result", ocpatAtachDtaService.selectOneRdtOcdrDt(ftrIdn));
		return "/projects/gn/job/ocpat/modifyRdtOcdrDt";
	}
	
	@RequestMapping(value="{ftrIdn}/modifyRdtOcdsDtPage.do", method=RequestMethod.GET)
	public String modifyRdtOcdsDtPage(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception{
		
		model.addAttribute("result", ocpatAtachDtaService.selectOneRdtOcdsDt(ftrIdn));
		return "/projects/gn/job/ocpat/modifyRdtOcdsDt";
	}
	
	@RequestMapping(value="{ftrIdn}/modifyRdtOcpeDtPage.do", method=RequestMethod.GET)
	public String modifyRdtOcpeDtPage(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception{
		
		model.addAttribute("result", ocpatAtachDtaService.selectOneRdtOcpeDt(ftrIdn));
		return "/projects/gn/job/ocpat/modifyRdtOcpeDt";
	}
	
	@RequestMapping(value="{ftrIdn}/modifyRdtOcreDtPage.do", method=RequestMethod.GET)
	public String modifyRdtOcreDtPage(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception{
		
		model.addAttribute("result", ocpatAtachDtaService.selectOneRdtOcreDt(ftrIdn));
		return "/projects/gn/job/ocpat/modifyRdtOcreDt";
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 변경이력 수정
	 * @param model
	 * @return
	 */
	@RequestMapping(value="{ftrIdn}/modifyRdtOccaDt.do", method=RequestMethod.POST)
	public String modifyRdtOccaDt(@PathVariable("ftrIdn") Long ftrIdn, RdtOccaDt rdtOccaDt, Model model) throws Exception{
		
		rdtOccaDt.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", ocpatAtachDtaService.modifyRdtOccaDt(rdtOccaDt));
		return "jsonViewTextPlain";
	}
	
	@RequestMapping(value="{ftrIdn}/modifyRdtOccnDt.do", method=RequestMethod.POST)
	public String modifyRdtOccnDt(@PathVariable("ftrIdn") Long ftrIdn, RdtOccnDt rdtOccnDt, Model model) throws Exception{
		
		rdtOccnDt.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", ocpatAtachDtaService.modifyRdtOccnDt(rdtOccnDt));
		return "jsonViewTextPlain";
	}
	
	@RequestMapping(value="{ftrIdn}/modifyRdtOcdrDt.do", method=RequestMethod.POST)
	public String modifyRdtOcdrDt(@PathVariable("ftrIdn") Long ftrIdn, RdtOcdrDt rdtOcdrDt, Model model) throws Exception{
		
		rdtOcdrDt.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", ocpatAtachDtaService.modifyRdtOcdrDt(rdtOcdrDt));
		return "jsonViewTextPlain";
	}
	
	@RequestMapping(value="{ftrIdn}/modifyRdtOcdsDt.do", method=RequestMethod.POST)
	public String modifyRdtOcdsDt(@PathVariable("ftrIdn") Long ftrIdn, RdtOcdsDt rdtOcdsDt, Model model) throws Exception{
		
		rdtOcdsDt.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", ocpatAtachDtaService.modifyRdtOcdsDt(rdtOcdsDt));
		return "jsonViewTextPlain";
	}
	
	@RequestMapping(value="{ftrIdn}/modifyRdtOcpeDt.do", method=RequestMethod.POST)
	public String modifyRdtOcpeDt(@PathVariable("ftrIdn") Long ftrIdn, RdtOcpeDt rdtOcpeDt, Model model) throws Exception{
		
//		System.out.println(selDong);
//		System.out.println(mainNum);
//		System.out.println(subNum);
//		System.out.println(checkMauntain);
		
		rdtOcpeDt.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", ocpatAtachDtaService.modifyRdtOcpeDt(rdtOcpeDt));
		return "jsonViewTextPlain";
	}
	
	@RequestMapping(value="{ftrIdn}/modifyRdtOcreDt.do", method=RequestMethod.POST)
	public String modifyRdtOcreDt(@PathVariable("ftrIdn") Long ftrIdn, RdtOcreDt rdtOcreDt, Model model) throws Exception{
		
		rdtOcreDt.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", ocpatAtachDtaService.modifyRdtOcreDt(rdtOcreDt));
		return "jsonViewTextPlain";
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	//부속자료 관리
	
	//리스트
	@RequestMapping(value="{ftrCde}/{ftrIdn}/fileList.do", method=RequestMethod.GET)
	public String listrdtOcpatFile(@PathVariable("ftrCde") String ftrCde, @PathVariable("ftrIdn") Long ftrIdn, RdtOcpatFile rdtOcpatFile, Model model) {
		rdtOcpatFile.setFtrCde(ftrCde);
		rdtOcpatFile.setFtrIdn(ftrIdn);
		model.addAttribute("rows", rdtOcpatFileService.listRdtOcpatFile(rdtOcpatFile));
		
		return "jsonView";
	}
	
	// 부속자료 상세 페이지
	@RequestMapping(value="selectOneFilePage.do", method=RequestMethod.GET)
	public String selectOneOcpatFilePage(){
		return "/projects/gn/job/ocpat/viewPhotoManage";
	}
	
	// 부속자료 상세 정보
	@RequestMapping(value="{ocpatFileNo}/selectOneFile.do", method=RequestMethod.GET)
	public String selectOneOcpatFile(@PathVariable("ocpatFileNo") Long ocpatFileNo, Model model){
		model.addAttribute("data", rdtOcpatFileService.selectOneFile(ocpatFileNo));
		return "jsonView";
	}
	
	// 부속자료 다운로드
	@RequestMapping(value="{fileNo}/download.do")
	public void download(@PathVariable("fileNo") Long fileNo, HttpServletResponse response, @RequestHeader(value="User-Agent") String userAgent) throws Exception {
		KwsFile kwsFile = fileService.selectOneFile(fileNo);
		InputStream is = new FileInputStream(kwsFile.getFileStreCours() + kwsFile.getStreFileNm());
		
		String fileName = kwsFile.getOrignlFileNm();
		String docName = null;
		if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")) {
			docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20")+";";
		}
		else {
			docName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"";
		}
		response.setHeader("Content-Disposition", "attachment; filename="+docName);
		
		OutputStream os = response.getOutputStream();
		IOUtils.copy(is,  os);
		is.close();
		os.close();
		
		
		
		// 부속자료 다운로드 이력등록
		RdtOcpatFile rdtOcpatFile = rdtOcpatFileService.selectOneFilebyFileNo(fileNo);
		String pmtNum = ocpatRegstrService.getRegisterPmtNum(rdtOcpatFile.getFtrCde(), rdtOcpatFile.getFtrIdn());
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		OcpatDownHi downHis = new OcpatDownHi();
		downHis.setUserId(userDTO.getUserId());
		
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		String editDate	= dateFormat.format(ts.getTime());
		downHis.setDownDt(editDate);

		downHis.setFtrCde(rdtOcpatFile.getFtrCde());
		downHis.setFtrIdn(rdtOcpatFile.getFtrIdn());
		downHis.setPmtNum(pmtNum);
		downHis.setDocCde(rdtOcpatFile.getFileSj());
		downHis.setFileNo(fileNo);
		
		ocpatRegstrHistoryService.insert(downHis);
	}
	
	// 부속자료 등록 페이지
	@RequestMapping(value="insertFilePage.do", method=RequestMethod.GET)
	public String insertPage() {
		return "/projects/gn/job/ocpat/addPhotoManage";
	}
	
	// 부속자료 등록
	@RequestMapping(value="insertOcpatFile.do", method=RequestMethod.POST)
	public String insert(RdtOcpatFile rdtOcpatFile, MultipartRequest multipartRequest, Model model) throws FdlException, IOException, ParseException{
		model.addAttribute("data", rdtOcpatFileService.insert(rdtOcpatFile, multipartRequest));
		return "jsonViewTextPlain";
	}
	
	// 부속자료 편집 페이지
	@RequestMapping(value="updateFilePage.do", method=RequestMethod.GET)
	public String KwsImageModifyPage() {
		return "/projects/gn/job/ocpat/modifyPhotoManage";
	}
	
	// 부속자료 편집
	@RequestMapping(value="updateFile.do", method=RequestMethod.POST)
	public String update(RdtOcpatFile rdtOcpatFile, MultipartRequest multipartRequest, Model model){
		model.addAttribute("data", rdtOcpatFileService.update(rdtOcpatFile, multipartRequest));
		return "jsonViewTextPlain";
	}
	
	// 부속자료 삭제
	@RequestMapping(value="{fileNo}/{ocpatFileNo}/deleteFile.do", method=RequestMethod.POST)
	public String delete(@PathVariable("fileNo") Long fileNo,@PathVariable("ocpatFileNo") Long ocpatFileNo, RdtOcpatFile rdtOcpatFile, Model model){
		model.addAttribute("data", rdtOcpatFileService.delete(rdtOcpatFile));
		return "jsonView";
	}
	
	// 부속자료 파일 체크
	@RequestMapping(value="ocpatFileChk.do", method=RequestMethod.GET)
	public String ocpatFileChk(@RequestParam("fileNo") Long fileNo, HttpServletResponse response,Model model){
		
		model.addAttribute("data",fileService.selectOneFile(fileNo));
		
		return "jsonView";
	}
	
	// pdf 파일 미리보기
	@RequestMapping(value = "{fileNo}/ocpatPdfOpen.do", method = RequestMethod.GET)
	public void opendPdf(@PathVariable("fileNo") Long fileNo, HttpServletResponse response,@RequestHeader(value = "User-Agent") String userAgent)throws Exception {
		KwsFile kwsFile = fileService.selectOneFile(fileNo);
		String path = kwsFile.getFileStreCours();
		File file = new File(path + kwsFile.getStreFileNm());
		if (file.exists()) {
			InputStream is = new FileInputStream(file.getPath());

			String fileName = kwsFile.getOrignlFileNm();
			String docName = null;
			if (StringUtils.contains(userAgent, "MSIE")
					|| StringUtils.contains(userAgent, "rv:11.0")
					|| StringUtils.contains(userAgent, "Chrome")) {
				docName = URLEncoder.encode(fileName, "UTF-8").replaceAll(
						"\\+", "%20")
						+ ";";
			} else {
				docName = "\""
						+ new String(fileName.getBytes("UTF-8"), "ISO-8859-1")
						+ "\"";
			}
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline; filename="
					+ docName);

			OutputStream os = response.getOutputStream();
			IOUtils.copy(is, os);
			is.close();
			os.close();
		}
	}
	
	

	// /////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="{ftrIdn}/removeRdtOccaDt.do", method=RequestMethod.POST)
	public String removeRdtOccaDt(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception{
		
		model.addAttribute("rowCount", ocpatAtachDtaService.removeRdtOccaDt(ftrIdn));
		return "jsonView";
	}
	
	@RequestMapping(value="{ftrIdn}/removeRdtOccnDt.do", method=RequestMethod.POST)
	public String removeRdtOccnDt(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception{
		
		model.addAttribute("rowCount", ocpatAtachDtaService.removeRdtOccnDt(ftrIdn));
		return "jsonView";
	}
	
	@RequestMapping(value="{ftrIdn}/removeRdtOcdrDt.do", method=RequestMethod.POST)
	public String removeRdtOcdrDt(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception{
		
		model.addAttribute("rowCount", ocpatAtachDtaService.removeRdtOcdrDt(ftrIdn));
		return "jsonView";
	}
	
	@RequestMapping(value="{ftrIdn}/removeRdtOcdsDt.do", method=RequestMethod.POST)
	public String removeRdtOcdsDt(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception{
		
		model.addAttribute("rowCount", ocpatAtachDtaService.removeRdtOcdsDt(ftrIdn));
		return "jsonView";
	}
	
	@RequestMapping(value="{ftrIdn}/removeRdtOcpeDt.do", method=RequestMethod.POST)
	public String removeRdtOcpeDt(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception{
		
		model.addAttribute("rowCount", ocpatAtachDtaService.removeRdtOcpeDt(ftrIdn));
		return "jsonView";
	}
	
	@RequestMapping(value="{ftrIdn}/removeRdtOcreDt.do", method=RequestMethod.POST)
	public String removeRdtOcreDt(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception{
		
		model.addAttribute("rowCount", ocpatAtachDtaService.removeRdtOcreDt(ftrIdn));
		return "jsonView";
	}

}