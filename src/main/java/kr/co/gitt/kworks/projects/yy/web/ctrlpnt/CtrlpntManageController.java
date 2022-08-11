package kr.co.gitt.kworks.projects.yy.web.ctrlpnt;

import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.cmmn.dto.spatial.SpatialSearchDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsDataAuthor;
import kr.co.gitt.kworks.model.KwsDataFtrCde;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.projects.yy.dto.ControlPointDTO;
import kr.co.gitt.kworks.projects.yy.dto.ControlPointSearchDTO;
import kr.co.gitt.kworks.projects.yy.model.EtlCopoDt;
import kr.co.gitt.kworks.projects.yy.service.ctrlpnt.CtrlpntService;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

/**
 * 측량기준점 컨트롤러 
 * @author kwangsu.lee
 *
 */
@Controller
@RequestMapping("/ctrlpnt/")
@Profile({"yy_dev", "yy_oper", "gds_dev", "gds_oper"})
public class CtrlpntManageController {
	
	/**
	 * 로거
	 */
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 데이터 서비스
	 */
	@Resource
	DataService dataService;
	
	/**
	 * 코드도메인
	 */
	@Resource
	DomnCodeService domnCodeService;
	
	/**
	 *  기준점 서비스
	 */
	@Resource
	CtrlpntService ctrlpntService;
	
//	/**
//	 * 이미지 서비스
//	 */
//	@Resource
//	ImageService imageService;

	
	/**
	 * 통합검색 페이지
	 * @param model
	 * @return
	 */
	@RequestMapping(value="totalSearchPage.do", method=RequestMethod.GET)
	public String searchPage(Model model) {
		
		return "/projects/yy/job/ctrlpnt/totalSearch";
	}
	
	/**
	 * 통합검색
	 * @param searchDTO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="searchSummaries.do", method=RequestMethod.POST)
	public String searchSummaries(ControlPointSearchDTO searchDTO, Model model) throws Exception {
		// 권한체크
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		
		List<String> dataIds = searchDTO.getDataIds();
		for (int i = dataIds.size()-1; i >= 0; i--) {
			String dataId = dataIds.get(1);
			
			boolean isAuthor = false;
			for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
				for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
					if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
						isAuthor = true;
						break;
					}
				}
			}
			
			if(!isAuthor) {
				dataIds.remove(dataId);
			}
		}
	
		if(dataIds.size() <= 0) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}
		
		model.addAttribute("datas", ctrlpntService.searchSummaries(dataIds, searchDTO));
		return "jsonView";
	}
	
	/**
	 * 기준점 등록 페이지
	 * @param model
	 * @return
	 */
	@RequestMapping(value="{dataId}/addRegiser.do", method=RequestMethod.GET)
	public String pageAdd(@PathVariable("dataId") String dataId, Model model) {
		
		// 권한체크
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		
		boolean isAuthor = false;
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}
		
		if(!isAuthor) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}

		KwsData kwsData = dataService.selectOneData(dataId);

		KwsDataFtrCde kwsDataFtrCde = new KwsDataFtrCde();
		kwsDataFtrCde.setDataId(dataId);
		List<KwsDataFtrCde> listFtrCde = dataService.listAllDataFtrCde(kwsDataFtrCde);
		KwsDataFtrCde ftrCde = listFtrCde.get(0);
		
		model.addAttribute("alias", kwsData.getDataAlias());
		model.addAttribute("ftrCde", ftrCde.getCodeId());
		
		// 설치구분 코드
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		
		kwsDomnCode.setDomnId("KWS-0308");
		model.addAttribute("setCde", domnCodeService.listDomnCode(kwsDomnCode));
		
		return "/projects/yy/job/ctrlpnt/addRegister";
	}
	
	/**
	 * 기준점 등록
	 * @param registerDTO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="add.do", method=RequestMethod.POST)
	public String insertRegister(ControlPointDTO registerDTO, Model model) throws Exception {

		model.addAttribute("type", "ins");
		model.addAttribute("state", ctrlpntService.insertRegister(registerDTO));
		return "jsonView";	
	}
	
	/**
	 * 기준점 조회 페이지
	 * @param model
	 * @return
	 */
	@RequestMapping(value="{dataId}/{ftrIdn}/viewRegiser.do", method=RequestMethod.GET)
	public String pageView(@PathVariable("dataId") String dataId, @PathVariable("ftrIdn") Long ftrIdn, SpatialSearchDTO spatialSearchDTO, Model model) throws Exception {
		
		// 권한체크
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		
		boolean isAuthor = false;
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}
		
		if(!isAuthor) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}

		KwsDataFtrCde kwsDataFtrCde = new KwsDataFtrCde();
		kwsDataFtrCde.setDataId(dataId);
		List<KwsDataFtrCde> listFtrCde = dataService.listAllDataFtrCde(kwsDataFtrCde);
		KwsDataFtrCde ftrCde = listFtrCde.get(0);
		
		model.addAttribute("dataCde", ftrCde.getCodeId());		
		model.addAttribute("row", ctrlpntService.getRegister(dataId, ftrIdn, false));		
		return "/projects/yy/job/ctrlpnt/viewRegister";
	}

	/**
	 * 기준점 편집 페이지
	 * @param model
	 * @return
	 */
	@RequestMapping(value="{dataId}/{ftrIdn}/modifyRegiser.do", method=RequestMethod.GET)
	public String pageModify(@PathVariable("dataId") String dataId, @PathVariable("ftrIdn") Long ftrIdn, SpatialSearchDTO spatialSearchDTO, Model model) throws Exception {
		
		// 권한체크
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		
		boolean isAuthor = false;
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			for(KwsDataAuthor kwsDataAuthor : kwsAuthorGroup.getKwsDataAuthors()) {
				if(StringUtils.equals(dataId, kwsDataAuthor.getDataId()) && StringUtils.equals("Y", kwsDataAuthor.getIndictAt())) {
					isAuthor = true;
					break;
				}
			}
		}
		
		if(!isAuthor) {
			logger.warn("데이터 권한이 없습니다.");
			return "redirect:/accessDenied.do";
		}

		KwsData kwsData = dataService.selectOneData(dataId);
		
		// 설치구분 코드
		KwsDomnCode kwsDomnCode = new KwsDomnCode();
		kwsDomnCode.setSearchCondition("2");
		kwsDomnCode.setDomnId("KWS-0308");
		
		model.addAttribute("alias", kwsData.getDataAlias());
		model.addAttribute("codeList", domnCodeService.listDomnCode(kwsDomnCode));
		model.addAttribute("row", ctrlpntService.getRegister(dataId, ftrIdn, true));		
		return "/projects/yy/job/ctrlpnt/modifyRegister";
	}
	
	/**
	 * 기준점 변경
	 * @param registerDTO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="{ftrIdn}/update.do", method=RequestMethod.POST)
	public String updateRegister(@PathVariable("ftrIdn") Long ftrIdn, ControlPointDTO registerDTO, Model model) throws Exception {
		
		model.addAttribute("type", "upd");
		model.addAttribute("state", ctrlpntService.updateRegister(ftrIdn, registerDTO));
		return "jsonView";	
	}
	
	/**
	 * 기준점 삭제
	 * @param registerDTO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="{ftrCde}/{ftrIdn}/delete.do", method=RequestMethod.GET)
	public String deleteRegister(@PathVariable("ftrCde") String ftrCde, @PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception {
		
		model.addAttribute("type", "del");
		model.addAttribute("state", ctrlpntService.deleteRegister(ftrCde, ftrIdn));
		return "jsonView";	
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	// 시준점
	//
	
	/**
	 * 시준점 목록 조회
	 * @param ftrCde
	 * @param ftrIdn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="{ftrCde}/{ftrIdn}/listPassPoint.do", method=RequestMethod.GET)
	public String listPassPoint(@PathVariable("ftrCde") String ftrCde, @PathVariable("ftrIdn") Long ftrIdn, Model model)  throws Exception {
	
		model.addAttribute("rows", ctrlpntService.listPassPoint(ftrCde, ftrIdn));
		return "jsonView";
	}
	
	/**
	 * 시준점 등록 페이지
	 * @param model
	 * @return
	 */
	@RequestMapping(value="{dataId}/{ftrIdn}/addPassPoint.do", method=RequestMethod.GET)
	public String pageAddPassPoint(@PathVariable("dataId") String dataId, @PathVariable("ftrIdn") Long ftrIdn, Model model) {
		
		KwsData kwsData = dataService.selectOneData(dataId);

		KwsDataFtrCde kwsDataFtrCde = new KwsDataFtrCde();
		kwsDataFtrCde.setDataId(dataId);
		List<KwsDataFtrCde> listFtrCde = dataService.listAllDataFtrCde(kwsDataFtrCde);
		KwsDataFtrCde ftrCde = listFtrCde.get(0);
		
		model.addAttribute("alias", kwsData.getDataAlias());
		model.addAttribute("ftrCde", ftrCde.getCodeId());
		model.addAttribute("ftrIdn", ftrIdn);
		
		return "/projects/yy/job/ctrlpnt/addPassPoint";
	}
	
	/**
	 * 시준점 등록
	 * @param registerDTO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="{ftrCde}/{ftrIdn}/insertPassPoint.do", method=RequestMethod.POST)
	public String addPassPoint(@PathVariable("ftrCde") String ftrCde, @PathVariable("ftrIdn") Long ftrIdn, EtlCopoDt passPoint, Model model) throws Exception {
		
		passPoint.setFtrCde(ftrCde);
		passPoint.setFtrIdn(ftrIdn);
		
		model.addAttribute("type", "ins");
		model.addAttribute("state", ctrlpntService.insertPassPoint(passPoint));
		return "jsonView";	
	}
	
	/**
	 * 시준점 편집 페이지
	 * @param model
	 * @return
	 */
	@RequestMapping(value="{dataId}/{ecpNo}/modifyPassPoint.do", method=RequestMethod.GET)
	public String pageModifyPassPoint(@PathVariable("dataId") String dataId, @PathVariable("ecpNo") Long ecpNo, Model model) throws Exception {
		
		KwsData kwsData = dataService.selectOneData(dataId);
		model.addAttribute("alias", kwsData.getDataAlias());
		model.addAttribute("row", ctrlpntService.selectassPointByNo(ecpNo));
		
		return "/projects/yy/job/ctrlpnt/modifyPassPoint";
	}
	
	/**
	 * 시준점 변경
	 * @param ecpNo
	 * @param data
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="{ecpNo}/updatePassPoint.do", method=RequestMethod.POST)
	public String updatePassPoint(@PathVariable("ecpNo") Long ecpNo, EtlCopoDt data, Model model)  throws Exception {
	
		model.addAttribute("type", "upd");
		model.addAttribute("state", ctrlpntService.updatePassPoint(ecpNo, data));
		return "jsonView";
	}
	
	/**
	 * 시준점 삭제
	 * @param ecpNo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="{ecpNo}/deletePassPoint.do", method=RequestMethod.GET)
	public String deletePassPoint(@PathVariable("ecpNo") Long ecpNo, Model model)  throws Exception {

		model.addAttribute("type", "del");
		model.addAttribute("state", ctrlpntService.deletePassPoint(ecpNo));
		return "jsonView";
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	// 이미지
	//
	
	/**
	 * 이미지 목록 조회
	 * @param ftrCde
	 * @param ftrIdn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="{ftrCde}/{ftrIdn}/listPhoto.do", method=RequestMethod.GET)
	public String listImage(@PathVariable("ftrCde") String ftrCde, @PathVariable("ftrIdn") Long ftrIdn, Model model)  throws Exception {
	
		model.addAttribute("rows", ctrlpntService.listPhoto(ftrCde, ftrIdn));
		return "jsonView";
	}
	
	/**
	 * 이미지 등록 페이지
	 * @param model
	 * @return
	 */
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
		model.addAttribute("imageTy", "SURVEY_BASE_POINT");
		
		return "/projects/yy/job/ctrlpnt/addPhoto";
	}

	/**
	 * 이미지 등록
	 * @param imageDTO
	 * @param model
	 * @param multipartRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="insertPhoto.do", method=RequestMethod.POST)
	public String addImage(ImageDTO imageDTO, Model model, MultipartRequest multipartRequest) throws Exception {

		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		imageDTO.setWrterId(userDTO.getUserId());
		imageDTO.setUpdusrId(userDTO.getUserId());
		
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			MultipartFile multipartFile = entry.getValue();
			if(!multipartFile.isEmpty()) {
				model.addAttribute("data", ctrlpntService.insertPhoto(imageDTO, multipartFile, 270, 210));
			}
		}
		return "jsonViewTextPlain";
	}
	
	/**
	 * 사진 편집 페이지
	 * @param model
	 * @return
	 */
	@RequestMapping(value="{imageNo}/modifyPhoto.do", method=RequestMethod.GET)
	public String pageModifyPhoto(@PathVariable("imageNo") Long imageNo, Model model) throws Exception {
		
		KwsImage kwsImage = ctrlpntService.selectPhotoByNo(imageNo);
		
		KwsDataFtrCde kwsDataFtrCde = new KwsDataFtrCde();
		kwsDataFtrCde.setCodeId(kwsImage.getFtrCde());
		List<KwsDataFtrCde> listFtrCde = dataService.listAllDataFtrCde(kwsDataFtrCde);
		KwsDataFtrCde ftrCde = listFtrCde.get(0);
		
		KwsData kwsData = dataService.selectOneData(ftrCde.getDataId());
		
		model.addAttribute("alias", kwsData.getDataAlias());
		model.addAttribute("image", kwsImage);
		return "/projects/yy/job/ctrlpnt/modifyPhoto";
	}
	
	/**
	 * 이미지 편집
	 * @param imageDTO
	 * @param model
	 * @param multipartRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="updatePhoto.do", method=RequestMethod.POST)
	public String updateImage(ImageDTO imageDTO, Model model, MultipartRequest multipartRequest) throws Exception {

		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		imageDTO.setUpdusrId(userDTO.getUserId());
		
		MultipartFile multipartFile = null;
		if(multipartRequest.getFileMap().size() > 0) {
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				multipartFile = entry.getValue();
			}
		}
		
		model.addAttribute("data", ctrlpntService.updatePhoto(imageDTO, multipartFile, 270, 210));
		return "jsonViewTextPlain";
	}
}
