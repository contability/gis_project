package kr.co.gitt.kworks.projects.gs.web.enviro;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsDataAuthor;
import kr.co.gitt.kworks.model.KwsDataFtrCde;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.projects.gs.dto.EcologyAttrDTO;
import kr.co.gitt.kworks.projects.gs.model.ExplantAs;
import kr.co.gitt.kworks.projects.gs.model.ReantAs;
import kr.co.gitt.kworks.projects.gs.service.enviro.EnviroService;
import kr.co.gitt.kworks.service.cmmn.ImageService;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.file.FileService;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

@Controller
@RequestMapping("/enviro/")
@Profile({"gs_dev", "gs_oper"})
public class EnviroManageController {
	
	// logger
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/// 데이터 서비스
	@Resource
	DataService dataService;
	
	/// 파일 서비스
	@Resource
	FileService fileService;
	
	@Resource
	EnviroService enviroService;
	
	/**
	 *  이미지 서비스
	 */
	@Resource
	ImageService imageService;
	
	/**
	 * 프로퍼티
	 */
	@Resource
    private MessageSource messageSource;
	
	///////////////////////////검색 페이지//////////////////////////////
	//주소 검색
	@RequestMapping(value="searchPage.do")
	public String searchPage(Model model) {
		
		return "/projects/gs/job/enviro/searchNelEc";
	}

	//현지조사표 검색
	@RequestMapping(value="EXPLANT_AS/searchPage.do")
	public String explantSearchPage(Model model) {
		return "/projects/gs/job/enviro/searchExplant";
	}
	
	//제거작업 검색
	@RequestMapping(value="REANT_AS/searchPage.do")
	public String reantSearchPage(Model model) {
		return "/projects/gs/job/enviro/searchReant";
	}
	///////////////////////////검색 페이지//////////////////////////////
	
	///////////////////////////검색  시도//////////////////////////////
	//주소검색
	@RequestMapping(value="search.do", method=RequestMethod.POST)
	public String search(EcologyAttrDTO ecologyAttrDTO, Model model) throws Exception {
//		model.addAttribute("rows", enviroService.search(dataId, pnu));
		// 권한체크
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
				
		List<String> dataIds = ecologyAttrDTO.getDataIds();
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
		model.addAttribute("datas", enviroService.search(dataIds, ecologyAttrDTO));
		return "jsonView";
	}
	
	//현지조사표 / 제거작업일지 검색
	@RequestMapping(value="searchExplant.do", method=RequestMethod.POST)
	public String explantSearch(EcologyAttrDTO ecologyAttrDTO, Model model) throws Exception {
		model.addAttribute("result", enviroService.explantSearch(ecologyAttrDTO));
		return "jsonView";
	}
	
	
	//제거작업일지 단건조회
	@RequestMapping(value="{objectId}/viewExplant.do", method=RequestMethod.GET)
	public String viewExpant(@PathVariable("objectId") Long objectId, Model model) throws Exception {
		model.addAttribute("result", enviroService.selectOneEx(objectId));
		return "/projects/gs/job/enviro/viewExplant";
	};
	
	//제거작업일지 단건조회
	@RequestMapping(value="{ftrIdn}/viewReant.do", method=RequestMethod.GET)
	public String viewReant(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception {
		model.addAttribute("result", enviroService.selectOneRe(ftrIdn));
		return "/projects/gs/job/enviro/viewReant";
	};
	
	//현지조사표 편집 창
	@RequestMapping(value="{objectId}/modifyPageExplant.do", method=RequestMethod.GET)
	public String modifyPageExpant(@PathVariable("objectId") Long objectId, Model model) throws Exception {
		model.addAttribute("result", enviroService.selectOneEx(objectId));
		return "/projects/gs/job/enviro/modifyPageExpant";
	};
	
	//제거작업일지 편집 창
	@RequestMapping(value="{objectId}/modifyPageReant.do", method=RequestMethod.GET)
	public String modifyPageReant(@PathVariable("objectId") Long objectId, Model model) throws Exception {
		model.addAttribute("result", enviroService.selectOneRe(objectId));
		return "/projects/gs/job/enviro/modifyPageReant";
	};
	
	//현지조사표 편집
	@RequestMapping(value="{objectId}/modifyExplant.do", method=RequestMethod.POST)
	public String modifyExpant(@PathVariable("objectId") Long objectId, ExplantAs data, Model model) throws Exception {
		
		model.addAttribute("type", "upd");
		model.addAttribute("state", enviroService.updateEx(objectId, data));
		return "jsonView";
	};
	
	//제거작업일지 편집
	@RequestMapping(value="{objectId}/modifyReant.do", method=RequestMethod.POST)
	public String modifyReant(@PathVariable("objectId") Long objectId, ReantAs data, Model model) throws Exception {
		
		model.addAttribute("type", "upd");
		model.addAttribute("state", enviroService.updateRe(objectId, data));
		return "jsonView";
	};
	
	//현지조사표 삭제
	@RequestMapping(value="{objectId}/removeExplant.do", method=RequestMethod.GET)
	public String removeExplant(@PathVariable("objectId") Long objectId, Model model) throws Exception {
		
		model.addAttribute("type", "del");
		model.addAttribute("state", enviroService.removeEx(objectId));
		return "jsonView";
	};
	
	//제거작업일지 삭제
	@RequestMapping(value="{objectId}/removeReant.do", method=RequestMethod.GET)
	public String removeReant(@PathVariable("objectId") Long objectId, Model model) throws Exception {
		
		model.addAttribute("type", "del");
		model.addAttribute("state", enviroService.removeRe(objectId));
		return "jsonView";
	};
	
	///////////////////////////////////////현지조사표 공간 편집 부분/////////////////////////////////////////////
	//현지조사표 공간편집창
	@RequestMapping(value="window/explantEditPage.do")
	public String explantEditPage(Model model) {
		return "/projects/gs/job/enviro/explantEditPage";
	}
	
	//제거작업일지 공간편집창
	@RequestMapping(value="window/reantEditPage.do")
	public String reantEditPage(Model model) {
		return "/projects/gs/job/enviro/reantEditPage";
	}
	
	//현지조사표 등록
	@RequestMapping(value="insertPageExplant.do", method=RequestMethod.GET)
	public String searchAddress(Model model) throws Exception {
		model.addAttribute("result", enviroService.lastFtrIdn());
		return "/projects/gs/job/enviro/insertPageExplant";
	};
	
	//현지조사표 편집
	@RequestMapping(value="{ftrIdn}/updatePageExplant.do", method=RequestMethod.GET)
	public String searchAddress(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception {
		model.addAttribute("result", enviroService.selectEx(ftrIdn));
		return "/projects/gs/job/enviro/updatePageExplant";
	};
	
	//제거작업일지 편집
	@RequestMapping(value="{ftrIdn}/updatePageReant.do", method=RequestMethod.GET)
	public String updatePageReant(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception {
		model.addAttribute("result", enviroService.selectOneRe(ftrIdn));
		return "/projects/gs/job/enviro/updatePageReant";
	};
	
	//제거작업일지 등록
	@RequestMapping(value="insertPageReant.do", method=RequestMethod.GET)
	public String insertPageReant(Model model) throws Exception {
		model.addAttribute("result", enviroService.lastReFtrIdn());
		return "/projects/gs/job/enviro/insertPageReant";
	};
	
	//제거작업일지 등록
	@RequestMapping(value="{expIdn}/searchReantList.do", method=RequestMethod.GET)
	public String searchReantList(@PathVariable("expIdn") Long expIdn, Model model) throws Exception {
		model.addAttribute("result", enviroService.listReantAs(expIdn));
		return "jsonView";
	};
	
	//제거작업일지 등록
	@RequestMapping(value="{ftrIdn}/searchPhotoList.do", method=RequestMethod.GET)
	public String searchPhotoList(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception {
		model.addAttribute("result", enviroService.listPhoto(ftrIdn));
		return "jsonView";
	};
	
	//제거작업일지 등록
	@RequestMapping(value="{ftrIdn}/searchRePhotoList.do", method=RequestMethod.GET)
	public String searchRePhotoList(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception {
		model.addAttribute("result", enviroService.listRePhoto(ftrIdn));
		return "jsonView";
	};
	
	///////////////////////////////////////사진////////////////////////////////////////
	//이미지편집
	@RequestMapping(value="{imageNo}/modifyExplantPhoto.do", method=RequestMethod.GET)
	public String pageModifyPhoto(@PathVariable("imageNo") Long imageNo, Model model) throws Exception {
			
		KwsImage kwsImage = enviroService.selectPhotoByNo(imageNo);
			
		KwsDataFtrCde kwsDataFtrCde = new KwsDataFtrCde();
		kwsDataFtrCde.setCodeId(kwsImage.getFtrCde());
		List<KwsDataFtrCde> listFtrCde = dataService.listAllDataFtrCde(kwsDataFtrCde);
		KwsDataFtrCde ftrCde = listFtrCde.get(0);
			
		KwsData kwsData = dataService.selectOneData(ftrCde.getDataId());
			
		model.addAttribute("alias", kwsData.getDataAlias());
		model.addAttribute("image", kwsImage);
		return "/projects/gs/job/enviro/modifyExplantPhoto";
	};
	
	@RequestMapping(value="{imageNo}/modifyReantPhoto.do", method=RequestMethod.GET)
	public String modifyReantPhoto(@PathVariable("imageNo") Long imageNo, Model model) throws Exception {
			
		KwsImage kwsImage = enviroService.selectPhotoByNo(imageNo);
			
		KwsDataFtrCde kwsDataFtrCde = new KwsDataFtrCde();
		kwsDataFtrCde.setCodeId(kwsImage.getFtrCde());
		List<KwsDataFtrCde> listFtrCde = dataService.listAllDataFtrCde(kwsDataFtrCde);
		KwsDataFtrCde ftrCde = listFtrCde.get(0);
			
		KwsData kwsData = dataService.selectOneData(ftrCde.getDataId());
			
		model.addAttribute("alias", kwsData.getDataAlias());
		model.addAttribute("image", kwsImage);
		return "/projects/gs/job/enviro/modifyReantPhoto";
	};
	
	//이미지편집
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
			
		model.addAttribute("data", enviroService.updatePhoto(imageDTO, multipartFile, 270, 210));
		return "jsonViewTextPlain";
	};
	
	//이미지삭제
	@RequestMapping(value="{imageNo}/photoRemove.do", method=RequestMethod.GET)
	public String removePhotoManage(@PathVariable("imageNo") Long imageNo, ImageDTO imageDTO, Model model) throws Exception {
		imageDTO.setImageNo(imageNo);
//		KwsImage kwsImage = imageService.selectOneImage(imageNo);
		model.addAttribute("data", imageService.removeImage(imageDTO));
		return "jsonView";
	};
	
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
	}
	
	//이미지등록
	@RequestMapping(value="{dataId}/{ftrIdn}/addExplantPhoto.do", method=RequestMethod.GET)
	public String pageAddImage(@PathVariable("dataId") String dataId, @PathVariable("ftrIdn") Long ftrIdn, Model model) {
			
		KwsData kwsData = dataService.selectOneData(dataId);

		KwsDataFtrCde kwsDataFtrCde = new KwsDataFtrCde();
		kwsDataFtrCde.setDataId(dataId);
		List<KwsDataFtrCde> listFtrCde = dataService.listAllDataFtrCde(kwsDataFtrCde);
		KwsDataFtrCde ftrCde = listFtrCde.get(0);
			
		model.addAttribute("alias", kwsData.getDataAlias());
		model.addAttribute("ftrCde", ftrCde.getCodeId());
		model.addAttribute("ftrIdn", ftrIdn);
		model.addAttribute("imageTy", "EXPLANT_PHOTO");

		return "/projects/gs/job/enviro/addExplantPhoto";
	}
	
	//이미지등록
	@RequestMapping(value="{dataId}/{ftrIdn}/addReantPhoto.do", method=RequestMethod.GET)
	public String addReantPhoto(@PathVariable("dataId") String dataId, @PathVariable("ftrIdn") Long ftrIdn, Model model) {
				
		KwsData kwsData = dataService.selectOneData(dataId);

		KwsDataFtrCde kwsDataFtrCde = new KwsDataFtrCde();
		kwsDataFtrCde.setDataId(dataId);
		List<KwsDataFtrCde> listFtrCde = dataService.listAllDataFtrCde(kwsDataFtrCde);
		KwsDataFtrCde ftrCde = listFtrCde.get(0);
			
		model.addAttribute("alias", kwsData.getDataAlias());
		model.addAttribute("ftrCde", ftrCde.getCodeId());
		model.addAttribute("ftrIdn", ftrIdn);
		model.addAttribute("imageTy", "REPLANT_PHOTO");
		return "/projects/gs/job/enviro/addReantPhoto";
	}
	
	//이미지등록
	@RequestMapping(value="insertPhoto.do", method=RequestMethod.POST)
	public String addImage(ImageDTO imageDTO, Model model, MultipartRequest multipartRequest) throws Exception {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		imageDTO.setWrterId(userDTO.getUserId());
		imageDTO.setUpdusrId(userDTO.getUserId());
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			MultipartFile multipartFile = entry.getValue();
			if(!multipartFile.isEmpty()) {
				model.addAttribute("data", imageService.addImage(imageDTO, multipartFile, 270, 210));
			}
		}
		return "jsonViewTextPlain";
	}
	////////////////////////출력
	//현지조사표 출력
	@RequestMapping(value="{ftrIdn}/printExplant.do", method=RequestMethod.GET)
	public String printExplant(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception {
		model.addAttribute("result", enviroService.selectEx(ftrIdn));
		return "/projects/gs/job/enviro/printExplant";
	};
	
	//제거작업일지 출력
	@RequestMapping(value="{ftrIdn}/printReant.do", method=RequestMethod.GET)
	public String printReant(@PathVariable("ftrIdn") Long ftrIdn, Model model) throws Exception {
		model.addAttribute("result", enviroService.printReantAs(ftrIdn));
		return "/projects/gs/job/enviro/printReant";
	};
	
	//제거작업일지 존재여부 판단
	@RequestMapping(value="{expIdn}/{jobType}/reantExpIdnSearch.do", method=RequestMethod.GET)
	public String reantExpIdnSearch(@PathVariable("expIdn") Long expIdn, @PathVariable("jobType") String jobType, Model model) throws Exception {
		model.addAttribute("result", enviroService.reantExpIdnSearch(expIdn, jobType));
		return "jsonView";
	};
	
	//제거작업일지 관리번호 갯
	@RequestMapping(value="{expIdn}/ftrIdnList.do", method=RequestMethod.GET)
	public String ftrIdnList(@PathVariable("expIdn") Long expIdn, Model model) throws Exception {
		model.addAttribute("result", enviroService.listFtrIdnGet(expIdn));
		return "jsonView";
	};
	
}