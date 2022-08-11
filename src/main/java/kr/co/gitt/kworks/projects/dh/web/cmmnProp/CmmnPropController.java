package kr.co.gitt.kworks.projects.dh.web.cmmnProp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.cmmn.dto.ImageDTO.ImageType;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsDataAuthor;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.model.KwsMenu;
import kr.co.gitt.kworks.projects.dh.service.cmmnProp.CmmnPropService;
import kr.co.gitt.kworks.service.cmmn.ImageService;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;
import kr.co.gitt.kworks.service.file.FileService;

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
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;


@Controller
@RequestMapping("/cmmnprop/")
@Profile({"dh_dev", "dh_oper"})
public class CmmnPropController {
	
	//로거
	Logger logger = LoggerFactory.getLogger(getClass());

	//프로퍼티
	@Resource
    private MessageSource messageSource;
	
	//데이터서비스
	@Resource
	private DataService dataService;
	
	//코드도메인
	@Resource
	DomnCodeService domnCodeService;
	
	//공유재산서비스
	@Resource
	CmmnPropService cmmnPropService;
	
	@Resource
	ImageService imageService;
	
	@Resource
	FileService fileService;
	
	// 다중 권한 체크
	@RequestMapping(value="{dataId}/cmmnPropIndict.do", method=RequestMethod.GET)
	public String confirm(@PathVariable("dataId") String dataIdList, KwsMenu kwsMenu, Model model){
		
		String[] dataIds = dataIdList.split("\\|");
		
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		boolean isAuthor = false;
		int isAuthorCnt = 0;
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups){
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()){
				/*if(kwsAuthorGroup.getAuthorGroupNo() == 1){
					if(kwsDataAuthor.getDataId().equals("BML_PROP_AS") || kwsDataAuthor.getDataId().equals("BML_BUID_AS")){
						System.out.println(kwsDataAuthor.getDataId() + " indictAt : " + kwsDataAuthor.getIndictAt());
					}
				}*/
				for(int i = 0; i < dataIds.length; i ++){
					
					if(StringUtils.equals(dataIds[i], kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())){
						isAuthorCnt ++;
					}
				}
			}
		}
		
		///////////////////////////////////////////////////////
		/// 이승재, 2021.12.29
		/// 권한체크 시 문제
		/// 예를들어 공간정보활용과 공유재산 권한을 모두 가지고 있는 경우
		/// 공간정보활용 권한 그룹에서도 공유재산 데이터가 권한이 있는 것으로 나타나
		/// isAuthorCnt가 dataIds.length보다 커지는 현상 발생
		/// 그런데 공간정보활용 권한 그룹에 공유재산 데이터가 권한 있는것으로 나타나는지는 모르겠음
		/*
		if(isAuthorCnt == dataIds.length){
			isAuthor = true;
		}
		*/
		if(isAuthorCnt >= dataIds.length){
			isAuthor = true;
		}
		///////////////////////////////////////////////////////
		
		model.addAttribute("dataIds", dataIds);
		model.addAttribute("isAuthor", isAuthor);
		
		return "jsonView";
	}
	
	//재산조회 검색창
	@RequestMapping(value="searchMainPage.do", method=RequestMethod.GET)
	public String searchMainPage(Model model) throws Exception {
		return "/projects/dh/job/cmmnProp/searchMainPage";
	}
	
	//대부 및 사용허가 조회 검색창
	@RequestMapping(value="searchLoanPage.do", method=RequestMethod.GET)
	public String searchLoanPage(Model model) throws Exception {
		return "/projects/dh/job/cmmnProp/searchLoanPage";
	}
	
	//무단사용 조회 검색창
	@RequestMapping(value="searchOccpPage.do", method=RequestMethod.GET)
	public String searchOccpPage(Model model) throws Exception {
		return "/projects/dh/job/cmmnProp/searchOccpPage";
	}
	
	//실태조사 결과조회
	@RequestMapping(value="searchAcinPage.do", method=RequestMethod.GET)
	public String searchAcinPage(Model model){
		return "/projects/dh/job/cmmnProp/searchAcinPage";
	}
	
	@RequestMapping(value="{dataId}/detailPage.do", method=RequestMethod.GET)
	public String detailPage(@PathVariable("dataId") String dataId){
		if(StringUtils.equals(dataId, "BML_PROP_AS")){
			return "/projects/dh/job/cmmnProp/detailPropPage";
		}else if(StringUtils.equals(dataId, "BML_BUID_AS")){
			return "/projects/dh/job/cmmnProp/detailBuidPage";
		}else if(StringUtils.equals(dataId, "BML_LOAN_AS")){
			return "/projects/dh/job/cmmnProp/detailLoanPage";
		}else if(StringUtils.equals(dataId, "BML_OCCP_AS")){
			return "/projects/dh/job/cmmnProp/detailOccpPage";
		}else if(StringUtils.equals(dataId, "BML_ACIN_AS")){
			return "/projects/dh/job/cmmnProp/detailAcinPage";
		}else{
			return "redirect:/portal.do";
		}
	}
	
	//토지조회 단건조회 데이터
	@RequestMapping(value="{prtIdn}/searchPropData.do", method=RequestMethod.GET)
	public String searchPropData(@PathVariable("prtIdn") Long prtIdn, Model model) throws Exception {
		model.addAttribute("row", cmmnPropService.selectProp(prtIdn));
		return "jsonView";
	}
	
	//건물조회 단건조회 데이터
	@RequestMapping(value="{prtIdn}/searchBuidData.do", method=RequestMethod.GET)
	public String searchBuidData(@PathVariable("prtIdn") Long prtIdn, Model model) throws Exception {
		model.addAttribute("row", cmmnPropService.selectBuid(prtIdn));
		return "jsonView";
	}
	
	//대부내역 단건조회 데이터
	@RequestMapping(value="{lonIdn}/{prtIdn}/searchLoanData.do", method=RequestMethod.GET)
	public String searchLoanData(@PathVariable("lonIdn") Long lonIdn, @PathVariable("prtIdn") Long prtIdn, Model model) throws Exception {
		model.addAttribute("bmlLoanAs", cmmnPropService.selectLoan(lonIdn, prtIdn));
		return "jsonView";
	}
	
	//대부내역+토지 단건조회 데이터
	@RequestMapping(value="{lonIdn}/{prtIdn}/searchLoanPropData.do", method=RequestMethod.GET)
	public String searchLoanPropData(@PathVariable("lonIdn") Long lonIdn, @PathVariable("prtIdn") Long prtIdn, Model model) throws Exception {
		model.addAttribute("bmlLoanAs", cmmnPropService.selectLoan(lonIdn, prtIdn));
		model.addAttribute("bmlPropAs", cmmnPropService.selectProp(prtIdn));
		return "jsonView";
	}
	
	//대부내역+건물 단건조회 데이터
	@RequestMapping(value="{lonIdn}/{prtIdn}/searchLoanBuilData.do", method=RequestMethod.GET)
	public String searchLoanBuilData(@PathVariable("lonIdn") Long lonIdn, @PathVariable("prtIdn") Long prtIdn, Model model) throws Exception {
		model.addAttribute("bmlLoanAs", cmmnPropService.selectLoan(lonIdn, prtIdn));
		model.addAttribute("bmlBuidAs", cmmnPropService.selectBuid(prtIdn));
		return "jsonView";
	}
	
	//무단점유 단건조회 데이터
	@RequestMapping(value="{ocpIdn}/{prtIdn}/searchOccpData.do", method=RequestMethod.GET)
	public String searchOccpData(@PathVariable("ocpIdn") Long ocpIdn, @PathVariable("prtIdn") Long prtIdn, Model model) throws Exception {
		model.addAttribute("bmlOccpDt", cmmnPropService.selectOccp(ocpIdn, prtIdn));
		return "jsonView";
	}
	
	//무단점유 +토지 단건조회 데이터
	@RequestMapping(value="{ocpIdn}/{prtIdn}/searchOccpPropData.do", method=RequestMethod.GET)
	public String searchOccpPropData(@PathVariable("ocpIdn") Long ocpIdn, @PathVariable("prtIdn") Long prtIdn, Model model) throws Exception {
		model.addAttribute("bmlOccpDt", cmmnPropService.selectOccp(ocpIdn, prtIdn));
		model.addAttribute("bmlPropAs", cmmnPropService.selectProp(prtIdn));
		return "jsonView";
	}
	
	//무단점유 +건물 단건조회 데이터
	@RequestMapping(value="{ocpIdn}/{prtIdn}/searchOccpBuidData.do", method=RequestMethod.GET)
	public String searchOccpBuidData(@PathVariable("ocpIdn") Long ocpIdn, @PathVariable("prtIdn") Long prtIdn, Model model) throws Exception {
		model.addAttribute("bmlOccpDt", cmmnPropService.selectOccp(ocpIdn, prtIdn));
		model.addAttribute("bmlBuidAs", cmmnPropService.selectBuid(prtIdn));
		return "jsonView";
	}
	
	// 실태조사 단건 조회
	@RequestMapping(value="{prtIdn}/searchAcinData.do", method=RequestMethod.GET)
	public String searchAcinData(@PathVariable("prtIdn") Long prtIdn, Model model){
		model.addAttribute("bmlAcinAs", cmmnPropService.selectAcin(prtIdn));
		return "jsonView";
	}

	@RequestMapping(value="{fileKnd}/{prtIdn}/pdfDownload.do", method=RequestMethod.GET)
	public void pdfDownload(@PathVariable("fileKnd") String fileKnd, @PathVariable("prtIdn") String prtIdn, @RequestHeader(value="User-Agent") String agent, HttpServletResponse rs){
		try {
			cmmnPropService.pdfDownload(fileKnd, prtIdn, agent, rs);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 사용자 부서코드
	@RequestMapping(value="getDeptCode.do", method=RequestMethod.GET)
	public String getDeptCode(Model model){
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("deptCode" ,userDTO.getDeptCode());
		return "jsonView";
	}
	
	// 이미지 목록 조회
	@RequestMapping(value="selectImageList.do", method=RequestMethod.GET)
	public String selectImageList(@RequestParam("ftrCde") String ftrCde, @RequestParam("prtIdn") Long prtIdn, Model model){
		
		KwsImage kwsImage = new KwsImage();
		kwsImage.setFtrCde(ftrCde);
		kwsImage.setFtrIdn(prtIdn);
		
		model.addAttribute("rows", imageService.listAllImage(kwsImage));
		
		return "jsonView";
	}
	
	@RequestMapping(value="{imageNo}/thumbnail.do", method=RequestMethod.GET)
	public void thumbnailImage(@PathVariable("imageNo") Long imageNo, HttpServletRequest rq, HttpServletResponse rs, @RequestHeader(value="User-Agent") String userAgent){
		KwsImage kwsImage = imageService.selectOneImage(imageNo);
		File file = imageService.getImageFile(kwsImage, ImageType.IMAGE);
		
		try {
			cmmnPropService.thumbnailImage(file, rq, rs, userAgent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="{fileNo}/preview.do", method=RequestMethod.GET)
	public void previewImage(@PathVariable("fileNo") Long fileNo, HttpServletRequest rq, HttpServletResponse rs, @RequestHeader(value="User-Agent") String userAgent){
		KwsFile kwsFile = fileService.selectOneFile(fileNo);
		
		try {
			cmmnPropService.previewFile(kwsFile, userAgent, rs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
