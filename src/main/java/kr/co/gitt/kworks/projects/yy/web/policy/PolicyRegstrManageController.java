package kr.co.gitt.kworks.projects.yy.web.policy;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.cmmn.dto.WfsTransactionDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsDataAuthor;
import kr.co.gitt.kworks.model.KwsDataFtrCde;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.model.KwsMenu;
import kr.co.gitt.kworks.projects.yy.dto.PolicyRegisterSpatialSearchDTO;
import kr.co.gitt.kworks.projects.yy.dto.PolicyRepoRegisterDTO;
import kr.co.gitt.kworks.projects.yy.mappers.PlcyStatAsMapper;
import kr.co.gitt.kworks.projects.yy.model.PlcyEditHi;
import kr.co.gitt.kworks.projects.yy.model.PlcyRefrHi;
import kr.co.gitt.kworks.projects.yy.model.PlcyStatAs;
import kr.co.gitt.kworks.projects.yy.service.policy.PolicyRegstrHistoryService;
import kr.co.gitt.kworks.projects.yy.service.policy.PolicyService;
import kr.co.gitt.kworks.projects.yy.service.policy.PolicyServiceImpl;
import kr.co.gitt.kworks.rest.export.ExportImageDTO;
import kr.co.gitt.kworks.service.cmmn.ImageService;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;
import kr.co.gitt.kworks.service.file.FileService;
import kr.co.gitt.kworks.service.layer.LayerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


@Controller
@RequestMapping("/policy/")
@Profile({"yy_dev", "yy_oper", "gds_dev", "gds_oper"})
public class PolicyRegstrManageController {
	
	// logger
	Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	PolicyService policyService;
	
	/// 데이터 서비스
	@Resource
	DataService dataService;
	
	/// 레이어 서비스
	@Resource
	LayerService layerService;
	
	/// 파일 서비스
	@Resource
	FileService fileService;
	
	@Resource
	PlcyStatAsMapper plcyStatAsMapper;
	
	@Resource
	PolicyRegstrHistoryService policyRegstrHistoryService;
	
	@Resource
	EgovIdGnrService plcyRepoMaIdGnrService;
	
	@Resource
	PolicyServiceImpl policyServiceImpl;
	
	/**
	 * 프로퍼티
	 */
	@Resource
    private MessageSource messageSource;
	
	@Resource
	DomnCodeService domnCodeService;
	
	/**
	 *  이미지 서비스
	 */
	@Resource
	ImageService imageService;
	
	//통합검색 페이지 
	//policyDeptList 부서목록
	@RequestMapping(value="searchPolicyPage.do", method=RequestMethod.GET)
	public String totalSearchPage(Model model) {
		
		model.addAttribute("policyDeptList", policyService.listAllDept());
		return "/projects/yy/job/policy/searchPolicy";
	}
	
	// 통합검색
	@RequestMapping(value="totalListPolicy.do", method=RequestMethod.GET)
	public String totalListPolicy(PlcyStatAs plcyStatAs, Model model) {
		model.addAttribute("result", policyService.listDep(plcyStatAs));
		return "jsonView";
	}
	
	// kws_menu 위치 편집
	@RequestMapping(value="{dataId}/modifyPolicy.do", method=RequestMethod.GET)
	public String modifyPolicy(@PathVariable("dataId") String dataId, KwsMenu kwsMenu, Model model) {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isEdit = false;
		boolean isAuthor = false;
		
		KwsData kwsData = dataService.selectOneData(dataId);
		if(kwsData != null && StringUtils.equals("Y", kwsData.getEditAt())) {
			List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
			for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
				for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
					if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getEditAt())) {
						isAuthor = true;
						break;
					}
				}
			}
			isEdit = true;
		}
		model.addAttribute("isEdit", isEdit);
		model.addAttribute("isAuthor", isAuthor);
		model.addAttribute("data", dataService.selectOneData(dataId));
		model.addAttribute("layer", layerService.selectOneLayerByDataId(dataId));
		return "jsonView";
	}
	
	//KWS_MENU 위치 조회
	@RequestMapping(value="{dataId}/listAllSummary.do", method=RequestMethod.POST)
	public String listAllDataSummary(@PathVariable("dataId") String dataId, PolicyRegisterSpatialSearchDTO policyRegisterSpatialSearchDTO, Model model) {
		
		Double Xmax = policyRegisterSpatialSearchDTO.getXmax();
		Double Ymax = policyRegisterSpatialSearchDTO.getYmax();
		Double Xmin = policyRegisterSpatialSearchDTO.getXmin();
		Double Ymin = policyRegisterSpatialSearchDTO.getYmin();
		
		String geom = "POLYGON ((" + Xmin + ' ' + Ymin + ',' + ' ' + Xmax + ' ' + Ymin + ' ' + ','
				+ ' ' + Xmax + ' ' + Ymax + ',' + ' ' + Xmin + ' ' + Ymax + ',' + ' ' + Xmin + ' ' + Ymin + "))";
		
		model.addAttribute("result", policyService.listSearch(geom));
		
		return "jsonView";
	}
	
	//대장삭제시 대장삭제 이미지삭제 보고서삭제
	@RequestMapping(value="{ftrIdn}/removePlcyStatAs.do", method=RequestMethod.POST)
	public String removePlcyStatAs(@PathVariable("ftrIdn") Long ftrIdn, PlcyEditHi plcyEditHi, Model model) throws Exception{	
		PolicyRepoRegisterDTO policyRepoReter = new PolicyRepoRegisterDTO();
		policyRepoReter.setFtrIdn(ftrIdn);
		model.addAttribute("rowCount", policyService.removeImage(ftrIdn));
		model.addAttribute("rowCount", policyService.deleteRepoMa(policyRepoReter));
		model.addAttribute("rowCount", policyService.removePlcyStatAs(ftrIdn));
		//model.addAttribute("result", policyRegstrHistoryService.insert(plcyEditHi));
		return "jsonView";
	}
	
	/**
	 * 위치편집 버튼 클릭시 페이지 띄우기
	 * @param model
	 * @return
	 */
	@RequestMapping(value="editPolicyPage.do", method=RequestMethod.GET)
	public String editPolicyPage(Model model) {
		model.addAttribute("policyDeptList", policyService.listAllDept());
		return "/projects/yy/job/policy/editPolicy";
	}
	
	//위치편집 -> 속성편집 버튼 ( 정책 신규등록 )
	@RequestMapping("addPolicyRegistrPage.do")
	public String policyaddPage(Model model) {
		model.addAttribute("policyDeptList", policyService.listAllDept());
		return "/projects/yy/job/policy/addPolicyRegistrPage";
	}

	@RequestMapping(value="/insert/transaction.do", method=RequestMethod.POST)
	public void insertTransaction(WfsTransactionDTO wfsTransactionDTO, HttpServletRequest request, HttpServletResponse res, @RequestParam("ftrIdn") Long ftrIdn,@RequestParam("plcyTit") String plcyTit) throws Exception {
		String urlStr = messageSource.getMessage("Map.Url.WFS", null, Locale.getDefault());
		HttpURLConnection huc = null;
		OutputStream ios = null;
		
		InputStream inputDataStream = null;
		InputStream outputDataStream = null;
		
		long startTime = Calendar.getInstance().getTimeInMillis();
		URL url;
		try {
			url = new URL(urlStr+"?");
			URLConnection connection = url.openConnection();
			huc = (HttpURLConnection)connection;
			huc.setRequestMethod("POST");
			huc.setDoOutput(true);
			huc.setDoInput(true);
			huc.setUseCaches(false);
			huc.setDefaultUseCaches(false);
			huc.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
			
			String inputDataString = IOUtils.toString(request.getInputStream());
			inputDataStream = IOUtils.toInputStream(inputDataString, "UTF-8");
			IOUtils.copy(inputDataStream, huc.getOutputStream());

			res.reset();
			res.setContentType(huc.getContentType());
			ios = res.getOutputStream();
			
			String outputDataString = IOUtils.toString(huc.getInputStream());
			outputDataStream = IOUtils.toInputStream(outputDataString, "UTF-8");
			IOUtils.copy(outputDataStream, ios);
//			UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
//			String userId = userDTO.getUserId();
//			policyService.insertPlcy(ftrIdn, userId, plcyTit);
			
			long endTime = Calendar.getInstance().getTimeInMillis();
			long useTime = endTime - startTime;
			
			logger.debug("Input Data String : " + inputDataString);
			logger.debug("Output Data String : " + outputDataString);
			logger.debug("ProxyPost UseTime : " + useTime + "(ms)");
			logger.debug("ProxyPost URL : " + urlStr);
		} catch (IOException e) {
			logger.warn(e.getMessage());
		} finally {
			if(ios != null) {
				ios.close();
			}
			if(huc != null) {
				huc.disconnect();
			}
			if(inputDataStream != null) {
				inputDataStream.close();
			}
			if(outputDataStream != null) {
				outputDataStream.close();
			}
		}
	}
	
	/**
	 * 대장편집 페이지 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="{ftrIdn}/modifypolicyRegstrPage.do", method=RequestMethod.GET)
	public String modifypolicyRegstrPage(@PathVariable("ftrIdn") Long ftrIdn, Model model) {
		
		model.addAttribute("policyDeptList", policyService.listAllDept());
		model.addAttribute("result", policyService.selectOne(ftrIdn));
		
		return "/projects/yy/job/policy/modifypolicyRegstrPage";
	}
	
	@RequestMapping(value="{ftrIdn}/editpolicyRegstrPage.do", method=RequestMethod.GET)
	public String editpolicyRegstrPage(@PathVariable("ftrIdn") Long ftrIdn, Model model) {
		
		model.addAttribute("policyDeptList", policyService.listAllDept());
		model.addAttribute("result", policyService.selectOne(ftrIdn));
		model.addAttribute("rows", policyService.selectOneRepo(ftrIdn));
		return "/projects/yy/job/policy/editpolicyRegstrPage";
	}
	
	 // 대장조회 창
	@RequestMapping(value="{ftrIdn}/viewPolicy.do", method=RequestMethod.GET)
	public String viewPolicy(@PathVariable("ftrIdn") Long ftrIdn, Model model) {
		model.addAttribute("result", policyService.selectOne(ftrIdn));
		model.addAttribute("rows", policyService.selectOneRepo(ftrIdn));
		return "/projects/yy/job/policy/viewPolicyRegstr";
	}
	
	//이미지목록조회
	@RequestMapping(value="{ftrCde}/{ftrIdn}/listPhoto.do", method=RequestMethod.GET)
	public String listImage(@PathVariable("ftrCde") String ftrCde, @PathVariable("ftrIdn") Long ftrIdn, Model model)  throws Exception {
	
		model.addAttribute("rows", policyService.listPhoto(ftrCde, ftrIdn));
		return "jsonView";
	}
	
	//이미지등록
	@RequestMapping(value="{dataId}/{ftrIdn}/addPhoto.do", method=RequestMethod.GET)
	public String pageAddImage(@PathVariable("dataId") String dataId, @PathVariable("ftrIdn") Long ftrIdn, Model model) {
		
		KwsData kwsData = dataService.selectOneData(dataId);

		KwsDataFtrCde kwsDataFtrCde = new KwsDataFtrCde();
		kwsDataFtrCde.setDataId(dataId);
		List<KwsDataFtrCde> listFtrCde = dataService.listAllDataFtrCde(kwsDataFtrCde);
		KwsDataFtrCde ftrCde = listFtrCde.get(0);
		
		model.addAttribute("alias", kwsData.getDataAlias());
		model.addAttribute("ftrCde", ftrCde.getCodeId());
		model.addAttribute("ftrIdn", ftrIdn);
		model.addAttribute("imageTy", "POLICY_PHOTO");
		
		return "/projects/yy/job/policy/addPhoto";
	}

	//이미지등록
	@RequestMapping(value="insertPhoto.do", method=RequestMethod.POST)
	public String addImage(ImageDTO imageDTO, Model model, MultipartRequest multipartRequest) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		imageDTO.setWrterId(userDTO.getUserId());
		imageDTO.setUpdusrId(userDTO.getUserId());
		Long ftrIdn = imageDTO.getFtrIdn();
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			MultipartFile multipartFile = entry.getValue();
			if(!multipartFile.isEmpty()) {
				model.addAttribute("data", imageService.addImage(imageDTO, multipartFile, 270, 210));

				PlcyStatAs plcyStatAs = plcyStatAsMapper.selectPlcyStatAs(ftrIdn);

				String plcyTit = plcyStatAs.getPlcyTit();
				
				String imageSj = imageDTO.getImageSj();
				PlcyRefrHi plcyRefrHi = new PlcyRefrHi(); 
				
				plcyRefrHi.setUserId(userDTO.getUserId());
				plcyRefrHi.setUserId(userDTO.getUserNm());
				plcyRefrHi.setFtrIdn(ftrIdn);
				plcyRefrHi.setEditType("INS");
				plcyRefrHi.setDocCde(imageSj);
				plcyRefrHi.setPlcyTit(plcyTit);
				policyRegstrHistoryService.insertRefrHi(plcyRefrHi);
				
			}
		}
		return "jsonViewTextPlain";
	}
	
	//이미지편집
	@RequestMapping(value="{imageNo}/modifyPhoto.do", method=RequestMethod.GET)
	public String pageModifyPhoto(@PathVariable("imageNo") Long imageNo, Model model) throws Exception {
		
		KwsImage kwsImage = policyService.selectPhotoByNo(imageNo);
		
		KwsDataFtrCde kwsDataFtrCde = new KwsDataFtrCde();
		kwsDataFtrCde.setCodeId(kwsImage.getFtrCde());
		List<KwsDataFtrCde> listFtrCde = dataService.listAllDataFtrCde(kwsDataFtrCde);
		KwsDataFtrCde ftrCde = listFtrCde.get(0);
		
		KwsData kwsData = dataService.selectOneData(ftrCde.getDataId());
		
		model.addAttribute("alias", kwsData.getDataAlias());
		model.addAttribute("image", kwsImage);
		return "/projects/yy/job/policy/modifyPhoto";
	}
	
	//이미지편집
	@RequestMapping(value="updatePhoto.do", method=RequestMethod.POST)
	public String updateImage(ImageDTO imageDTO, Model model, MultipartRequest multipartRequest) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		imageDTO.setUpdusrId(userDTO.getUserId());
		Long ftrIdn = imageDTO.getFtrIdn();
		String imageSj = imageDTO.getImageSj();
		
		PlcyStatAs plcyStatAs = plcyStatAsMapper.selectPlcyStatAs(ftrIdn);
		String plcyTit = plcyStatAs.getPlcyTit();
		
		PlcyRefrHi plcyRefrHi = new PlcyRefrHi(); 
		plcyRefrHi.setUserId(userDTO.getUserId());
		plcyRefrHi.setFtrIdn(ftrIdn);
		plcyRefrHi.setEditType("UPD");
		plcyRefrHi.setDocCde(imageSj);
		plcyRefrHi.setPlcyTit(plcyTit);
		policyRegstrHistoryService.insertRefrHi(plcyRefrHi);
		
		MultipartFile multipartFile = null;
		if(multipartRequest.getFileMap().size() > 0) {
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				multipartFile = entry.getValue();
			}
		}
		
		model.addAttribute("data", policyService.updatePhoto(imageDTO, multipartFile, 270, 210));
		return "jsonViewTextPlain";
	}
	
	//이미지삭제
	@RequestMapping(value="{imageNo}/remove.do", method=RequestMethod.GET)
	public String removePhotoManage(@PathVariable("imageNo") Long imageNo, ImageDTO imageDTO, Model model) throws Exception {
		imageDTO.setImageNo(imageNo);
		KwsImage kwsImage = imageService.selectOneImage(imageNo);
		model.addAttribute("data", imageService.removeImage(imageDTO));
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		Long ftrIdn = kwsImage.getFtrIdn();
		String imageSj = kwsImage.getImageSj();
		
		PlcyStatAs plcyStatAs = plcyStatAsMapper.selectPlcyStatAs(ftrIdn);
		String plcyTit = plcyStatAs.getPlcyTit();
		
		PlcyRefrHi plcyRefrHi = new PlcyRefrHi(); 
		
		plcyRefrHi.setUserId(userDTO.getUserId());
		plcyRefrHi.setFtrIdn(ftrIdn);
		plcyRefrHi.setEditType("DEL");
		plcyRefrHi.setDocCde(imageSj);
		plcyRefrHi.setPlcyTit(plcyTit);
		policyRegstrHistoryService.insertRefrHi(plcyRefrHi);
		return "jsonView";
	}
	
	//속성정보에서 편집
	@RequestMapping(value="{ftrIdn}/modifyPlcyStatAs.do", method=RequestMethod.POST)
	public String modifyPlcyStatAs(@PathVariable("ftrIdn") Long ftrIdn, PlcyStatAs plcyStatAs, @RequestParam("plcyAdr") String plcyAdr, Model model) throws Exception{
		plcyStatAs.setFtrIdn(ftrIdn);
		plcyStatAs.setPlcyAdr(plcyAdr);
		model.addAttribute("rowCount", policyService.update(plcyStatAs));
		return "jsonView";
	}

	//레포트 등록 공간정보
	@RequestMapping(value="{ftrIdn}/insertplcyRepo.do", method=RequestMethod.POST)
	public String insertplcyRepo(@PathVariable("ftrIdn") Long ftrIdn, PolicyRepoRegisterDTO policyRepoRegstr, Model model) throws Exception{
		policyRepoRegstr.setFtrIdn(ftrIdn);
		Long repoIdn = plcyRepoMaIdGnrService.getNextLongId();
		policyRepoRegstr.setRepoIdn(repoIdn);
		model.addAttribute("rowCount", policyService.insertRepoMaRegster(policyRepoRegstr));
		model.addAttribute("rowCount", policyService.insertRepoCtRegster(policyRepoRegstr));
		return "jsonView";
	}
	
	//레포트 수정 공간정보
	@RequestMapping(value="{ftrIdn}/updateplcyRepo.do", method=RequestMethod.POST)
	public String updateplcyRepo(@PathVariable("ftrIdn") Long ftrIdn, PolicyRepoRegisterDTO policyRepoRegstr, Model model) throws Exception{
		policyRepoRegstr.setFtrIdn(ftrIdn);

		model.addAttribute("rowCount", policyService.updateRepoMaReg(policyRepoRegstr));
		return "jsonView";
	}
	
	//보고서 수정 창 // windowObj.policyRepoRegisterObj / loadData
	@RequestMapping(value="{ftrIdn}/listRepoCt.do", method=RequestMethod.GET)
	public String listRepoCt(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception{
		
		model.addAttribute("rows", policyService.selectOneRepo(ftrIdn));
		return "jsonView";
	}
	
	@RequestMapping(value="edit/list.do")
	public String editHistory(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		// 편집 타입
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		kwsDomnCode.setDomnId("KWS-0378");
		model.addAttribute("editType", domnCodeService.listDomnCode(kwsDomnCode));
		
		System.out.println("searchDTO.getPageIndex() : " + searchDTO.getPageIndex());
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		paginationInfo.setTotalRecordCount(policyRegstrHistoryService.editCount());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", policyRegstrHistoryService.editSearch(searchDTO));
		
		return "/manage/policyRegstr/policyEditHistory";
	}
	
	//부속자료이력 
	@RequestMapping(value="editRefrHi/list.do")
	public String editRefrHistory(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		// 편집 타입
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		kwsDomnCode.setDomnId("KWS-0378");
		model.addAttribute("editType", domnCodeService.listDomnCode(kwsDomnCode));
		
		System.out.println("searchDTO.getPageIndex() : " + searchDTO.getPageIndex());
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		paginationInfo.setTotalRecordCount(policyRegstrHistoryService.refrCount());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", policyRegstrHistoryService.refrSearch(searchDTO));
		
		return "/manage/policyRegstr/policyRefrHistory";
	}
	
	
	@RequestMapping(value="download/list.do")
	public String downloadHistory(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");

		// 부속자료 코드
		kwsDomnCode.setDomnId("KWS-0378");
		model.addAttribute("docCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		System.out.println("searchDTO.getPageIndex() : " + searchDTO.getPageIndex());
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		paginationInfo.setTotalRecordCount(policyRegstrHistoryService.downloadCount());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", policyRegstrHistoryService.downloadSearch(searchDTO));
		
		return "/manage/policyRegstr/policyDownHistory";
	}
	
	@RequestMapping(value="repo/list.do")
	public String repoHistory(SearchDTO searchDTO, PaginationInfo paginationInfo, Model model) {
		// 편집 타입
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		kwsDomnCode.setDomnId("KWS-0378");
		model.addAttribute("editType", domnCodeService.listDomnCode(kwsDomnCode));
		
		System.out.println("searchDTO.getPageIndex() : " + searchDTO.getPageIndex());
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		paginationInfo.setTotalRecordCount(policyRegstrHistoryService.repoCount());
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("rows", policyRegstrHistoryService.repoSearch(searchDTO));
		
		return "/manage/policyRegstr/policyRepoHistory";
	}
	
	/////////
	////확인필요 이건 뭐지?
	////////////////
	@RequestMapping(value="{ftrIdn}/plcyAdrSearch.do", method=RequestMethod.GET)
	public String plcyAdrSearch(@PathVariable("ftrIdn") Long ftrIdn, Model model) {
		model.addAttribute("result", policyService.selectPlcyAdr(ftrIdn));
		return "jsonView";
	}

	@RequestMapping(value="{fileNo}/download.do")
	public void download(@PathVariable("fileNo") Long fileNo, HttpServletResponse response, @RequestHeader(value="User-Agent") String userAgent) throws Exception {
		KwsFile kwsFile = fileService.selectOneFile(fileNo);
		InputStream is = new FileInputStream(kwsFile.getFileStreCours() + kwsFile.getStreFileNm());
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
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
		
		KwsImage kwsImage = imageService.selectOneByFileNo(fileNo);
		Long ftrIdn = kwsImage.getFtrIdn();
		String imageSj = kwsImage.getImageSj();
		
		PlcyStatAs plcyStatAs = plcyStatAsMapper.selectPlcyStatAs(ftrIdn);
		//plcyStatAs.setFtrIdn(ftrIdn);
		String plcyTit = plcyStatAs.getPlcyTit();
		
		// 대장 편집이력
		PlcyRefrHi plcyRefrHi = new PlcyRefrHi(); 
		
		plcyRefrHi.setUserId(userDTO.getUserId());
		plcyRefrHi.setFtrIdn(ftrIdn);
		//테이블처리 edit_dt
		plcyRefrHi.setEditType("DWN");
		plcyRefrHi.setFileNo(fileNo);
		plcyRefrHi.setDocCde(imageSj);
		plcyRefrHi.setPlcyTit(plcyTit);
		policyRegstrHistoryService.insertRefrHi(plcyRefrHi);
		
		is.close();
		os.close();
	}
	
	@RequestMapping(value="{ftrIdn}/capture.do", method=RequestMethod.POST)
	public String capture(@PathVariable("ftrIdn") Long ftrIdn, ExportImageDTO exportDTO, Model model) throws IOException, FdlException {
		
		UserDTO user = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		String fileName = user.getUserId() + "_" + System.currentTimeMillis();
		String fullName = fileName + ".jpg";
		String path = messageSource.getMessage("Com.Policy.Image.Path", null, Locale.getDefault());
		if (!path.endsWith("\\"))
			path += File.separator;
		String fullPath = path + fullName;
		File directory = new File(path);
		if (!directory.exists())
			directory.mkdirs();
		
		String data = exportDTO.getData();
		String base64Str = null;
		String[] split = data.split(",");
		if(split.length == 2) {
			base64Str = split[1];
		}
		
		byte[] bytes = Base64.decodeBase64(base64Str);
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
		
		File outputfile = new File(fullPath);
		ImageIO.write(image, "JPG", outputfile);
		
		model.addAttribute("image", fileName);
		return "jsonView";		
	}

	//퀵버튼
	@RequestMapping(value="{plcyAdr}/policyQuick.do", method=RequestMethod.GET)
	public String landUseInfoQuick(@PathVariable("plcyAdr") String plcyAdr, Model model){
		model.addAttribute("result", policyService.policyQuickSearch(plcyAdr));
		
		return "jsonView";
	}
	
	@RequestMapping(value="listDeptSub.do", method=RequestMethod.GET)
	public String listDeptSub(Model model) {
		model.addAttribute("rows", policyService.listAllDeptCategory());
		return "jsonView";
	}
	
	@RequestMapping(value="{deptSubCode}/deptSubList.do", method=RequestMethod.GET)
	public String deptSubList(@PathVariable("deptSubCode") String deptSbCd, Model model) {
		model.addAttribute("rows", policyService.deptSubList(deptSbCd));
		return "jsonView";
	}

	//실과소 가져오기
	@RequestMapping(value="{plcyDep}/deptNmReturn.do", method=RequestMethod.GET)
	public String deptNmReturn(@PathVariable("plcyDep") String plcyDep, Model model) {
		model.addAttribute("rows", policyService.deptNmReturn(plcyDep));
		return "jsonView";
	}
	
	//부서명 가져오기
	@RequestMapping(value="{deptSubCode}/deptSubNmReturn.do", method=RequestMethod.GET)
	public String deptSubNmReturn(@PathVariable("deptSubCode") String deptSbCd, Model model) {
		model.addAttribute("rows", policyService.deptSubNmReturn(deptSbCd));
		return "jsonView";
	}
	
	@RequestMapping(value="{deptSubCode}/forDeptCode.do", method=RequestMethod.GET)
	public String forDeptCode(@PathVariable("deptSubCode") String deptSbCd, Model model){
		model.addAttribute("row", policyService.forDeptCode(deptSbCd));
		return "jsonView";
	}
}