package kr.co.gitt.kworks.projects.dh.web.cityPlanRoad;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO;
import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.model.KwsDomnCode;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.projects.dh.dto.CityPlanRoadSearchDTO;
import kr.co.gitt.kworks.projects.dh.model.DhgConsHt;
import kr.co.gitt.kworks.projects.dh.model.DhgRepaHt;
import kr.co.gitt.kworks.projects.dh.model.DhgRoplAs;
import kr.co.gitt.kworks.projects.dh.service.cityPlanRoad.CityPlanRoadSearchService;
import kr.co.gitt.kworks.rest.export.ExportImageDTO;
import kr.co.gitt.kworks.service.data.DataService;
import kr.co.gitt.kworks.service.domnCode.DomnCodeService;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;


@Controller
@RequestMapping("/rest/dhPlanRoad")
@Profile({"dh_dev", "dh_oper"})
public class CityPlanRoadSearchController 
{
	/**
	 * 로거
	 */
	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 프로퍼티
	 */
	@Resource
    private MessageSource messageSource;
	
	/**
	 * 데이터 서비스
	 */
	@Resource
	private DataService dataService;
	
	/**
	 * 코드도메인
	 */
	@Resource
	DomnCodeService domnCodeService;
	
	/**
	 * 검색 서비스
	 */
	@Resource
	private CityPlanRoadSearchService dhPlanRoadSearchService;
	
	
	/**
	 * 검색
	 * 
	 * @param planRoadSearchDTO 검색조건 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listAllSummary.do", method=RequestMethod.POST)
	public String searchSummaries(CityPlanRoadSearchDTO planRoadSearchDTO, Model model) throws Exception {
		
		model.addAttribute("rows", dhPlanRoadSearchService.listAllSummary(planRoadSearchDTO));
		return "jsonView";
	}

	@RequestMapping(value="sortRegister.do", method=RequestMethod.POST)
	public String sortRegister(CityPlanRoadSearchDTO planRoadSearchDTO, Model model)  throws Exception {
	
		model.addAttribute("rows", dhPlanRoadSearchService.sortRegister(planRoadSearchDTO));
		return "jsonView";
	}
	
	@RequestMapping(value="searchRegister.do", method=RequestMethod.POST)
	public String searchRegister(CityPlanRoadSearchDTO planRoadSearchDTO, Model model)  throws Exception {
	
		List<EgovMap> result = dhPlanRoadSearchService.searchRegister(planRoadSearchDTO);
		model.addAttribute("rows", result);
		return "jsonView";
	}	
	
	@RequestMapping(value="{ftrIdn}/viewRegister.do", method=RequestMethod.GET)
	public String pageRegister(@PathVariable("ftrIdn") Long ftrIdn, Model model)  throws Exception {
	
		String spatial = messageSource.getMessage("Extension.CityPlanRoad.Spatial", null, Locale.getDefault());
		KwsData spatialData = dataService.selectOneData(spatial);
		String editAt = spatialData.getEditAt();
		
		model.addAttribute("editAt", editAt);
		model.addAttribute("row", dhPlanRoadSearchService.getRegister(ftrIdn, false));
		return "projects/dh/job/cityPlanRoad/viewRegister";
	}	
	
	@RequestMapping(value="{ftrIdn}/modifyRegister.do", method=RequestMethod.GET)
	public String pageModify(@PathVariable("ftrIdn") Long ftrIdn, Model model)  throws Exception {
	
		// 규모별 구분 코드
		String grdCde = messageSource.getMessage("Extension.CityPlanRoad.Spatial.GrdCde", null, Locale.getDefault());
		KwsDomnCode grdCdeList = new KwsDomnCode();
		grdCdeList.setSearchCondition("2");
		grdCdeList.setDomnId(grdCde);
		
		// 기능별 구분 코드
		String fncCde = messageSource.getMessage("Extension.CityPlanRoad.Spatial.FncCde", null, Locale.getDefault());
		KwsDomnCode fncCdeList = new KwsDomnCode();
		fncCdeList.setSearchCondition("2");
		fncCdeList.setDomnId(fncCde);
		
		model.addAttribute("grdList", domnCodeService.listDomnCode(grdCdeList));
		model.addAttribute("fncList", domnCodeService.listDomnCode(fncCdeList));
		model.addAttribute("row", dhPlanRoadSearchService.getRegister(ftrIdn, true));
		return "projects/dh/job/cityPlanRoad/modifyRegister";
	}
	
	@RequestMapping(value="{ftrIdn}/updateRegister.do", method=RequestMethod.POST)
	public String updateRegister(@PathVariable("ftrIdn") Long ftrIdn, DhgRoplAs data, Model model)  throws Exception {
	
		model.addAttribute("type", "upd");
		model.addAttribute("state", dhPlanRoadSearchService.updateRegister(ftrIdn, data));
		return "jsonView";
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	//
	// 도시계획도로 공사개요
	//
	@RequestMapping(value="{ftrIdn}/listConstruction.do", method=RequestMethod.GET)
	public String getConstruction(@PathVariable("ftrIdn") Long ftrIdn, Model model)  throws Exception {
	
		List<EgovMap> result = dhPlanRoadSearchService.getConstruction(ftrIdn);
		model.addAttribute("rows", result);
		return "jsonView";
	}	

	@RequestMapping(value="{ftrIdn}/{conSeq}/detailConstruction.do", method=RequestMethod.GET)
	public String pageDetailConstruction(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("conSeq") Long conSeq, Model model)  throws Exception {
	
		String attribute = messageSource.getMessage("Extension.CityPlanRoad.Construction", null, Locale.getDefault());
		KwsData attrData = dataService.selectOneData(attribute);
		String editAt = attrData.getEditAt();
		
		model.addAttribute("editAt", editAt);
		if (editAt.equalsIgnoreCase("Y"))
			model.addAttribute("row", dhPlanRoadSearchService.getConstruction(ftrIdn, conSeq, true));
		else
			model.addAttribute("row", dhPlanRoadSearchService.getConstruction(ftrIdn, conSeq, false));
		return "projects/dh/job/cityPlanRoad/detailConstruction";
	}
	
	@RequestMapping(value="{ftrIdn}/addConstruction.do", method=RequestMethod.GET)
	public String pageAddConstruction(@PathVariable("ftrIdn") Long ftrIdn, Model model)  throws Exception {
	
		model.addAttribute("ftrIdn", ftrIdn);
		return "projects/dh/job/cityPlanRoad/addConstruction";
	}
	
	@RequestMapping(value="{ftrIdn}/insertConstruction.do", method=RequestMethod.POST)
	public String insertConstruction(@PathVariable("ftrIdn") Long ftrIdn, DhgConsHt data, Model model)  throws Exception {
	
		model.addAttribute("type", "ins");
		model.addAttribute("state", dhPlanRoadSearchService.insertConstruction(ftrIdn, data));
		return "jsonView";
	}
	
	@RequestMapping(value="{ftrIdn}/{conSeq}/updateConstruction.do", method=RequestMethod.POST)
	public String updateConstruction(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("conSeq") Long conSeq, DhgConsHt data, Model model)  throws Exception {
	
		model.addAttribute("type", "upd");
		model.addAttribute("state", dhPlanRoadSearchService.updateConstruction(ftrIdn, conSeq, data));
		return "jsonView";
	}

	@RequestMapping(value="{ftrIdn}/{conSeq}/deleteConstruction.do", method=RequestMethod.GET)
	public String deleteConstruction(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("conSeq") Long conSeq, Model model)  throws Exception {

		model.addAttribute("type", "del");
		model.addAttribute("state", dhPlanRoadSearchService.deleteConstruction(ftrIdn, conSeq));
		return "jsonView";
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	//
	// 도시계획도로 정비이력
	//
	@RequestMapping(value="{ftrIdn}/listRepair.do", method=RequestMethod.GET)
	public String getRepair(@PathVariable("ftrIdn") Long ftrIdn, Model model)  throws Exception {
	
		List<EgovMap> result = dhPlanRoadSearchService.getRepair(ftrIdn);
		model.addAttribute("rows", result);
		return "jsonView";
	}	

	@RequestMapping(value="{ftrIdn}/{repSeq}/detailRepair.do", method=RequestMethod.GET)
	public String pageDetailRepair(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("repSeq") Long repSeq, Model model)  throws Exception {
	
		String attribute = messageSource.getMessage("Extension.CityPlanRoad.Repair", null, Locale.getDefault());
		KwsData attrData = dataService.selectOneData(attribute);
		String editAt = attrData.getEditAt();
		
		model.addAttribute("editAt", editAt);
		if (editAt.equalsIgnoreCase("Y"))
			model.addAttribute("row", dhPlanRoadSearchService.getRepair(ftrIdn, repSeq, true));
		else
			model.addAttribute("row", dhPlanRoadSearchService.getRepair(ftrIdn, repSeq, false));
		return "projects/dh/job/cityPlanRoad/detailRepair";
	}
	
	@RequestMapping(value="{ftrIdn}/addRepair.do", method=RequestMethod.GET)
	public String pageAddRepair(@PathVariable("ftrIdn") Long ftrIdn, Model model)  throws Exception {
	
		model.addAttribute("ftrIdn", ftrIdn);
		return "projects/dh/job/cityPlanRoad/addRepair";
	}

	@RequestMapping(value="{ftrIdn}/insertRepair.do", method=RequestMethod.POST)
	public String insertRepair(@PathVariable("ftrIdn") Long ftrIdn, DhgRepaHt data, Model model)  throws Exception {
	
		model.addAttribute("type", "ins");
		model.addAttribute("state", dhPlanRoadSearchService.insertRepair(ftrIdn, data));
		return "jsonView";
	}
	
	@RequestMapping(value="{ftrIdn}/{repSeq}/updateRepair.do", method=RequestMethod.POST)
	public String updateRepair(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("repSeq") Long repSeq, DhgRepaHt data, Model model)  throws Exception {
	
		model.addAttribute("type", "upd");
		model.addAttribute("state", dhPlanRoadSearchService.updateRepair(ftrIdn, repSeq, data));
		return "jsonView";
	}

	@RequestMapping(value="{ftrIdn}/{repSeq}/deleteRepair.do", method=RequestMethod.GET)
	public String deleteRepair(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("repSeq") Long repSeq, Model model)  throws Exception {

		model.addAttribute("type", "del");
		model.addAttribute("state", dhPlanRoadSearchService.deleteRepair(ftrIdn, repSeq));
		return "jsonView";
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////
	//
	// 도시계획도로 사진자료
	//
	
	@RequestMapping(value="{ftrIdn}/capture.do", method=RequestMethod.POST)
	public String capture(@PathVariable("ftrIdn") Long ftrIdn, ExportImageDTO exportDTO, Model model) throws IOException, FdlException {
		
		UserDTO user = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		String fileName = user.getUserId() + "_" + System.currentTimeMillis();
		String fullName = fileName + ".jpg";
		
		// 이미지 저장 경로
		String path = messageSource.getMessage("Com.CityPlanRoad.Image.Path", null, Locale.getDefault());
		if (!path.endsWith("\\"))
			path += File.separator;
		String fullPath = path + fullName;
		File directory = new File(path);
		if (!directory.exists())
			directory.mkdirs();
		
		// 이미지 데이터 가져오기
		String data = exportDTO.getData();
		String base64Str = null;
		String[] split = data.split(",");
		if(split.length == 2) {
			base64Str = split[1];
		}
		
		byte[] bytes = Base64.decodeBase64(base64Str);
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
		
		// 이미지 저장
		File outputfile = new File(fullPath);
		ImageIO.write(image, "JPG", outputfile);
		
		// 반환
		model.addAttribute("image", fileName);
		return "jsonView";		
	}
	
	/**
	 * 사진 목록 조회
	 * 
	 * @param ftrCde
	 * @param ftrIdn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="{ftrCde}/{ftrIdn}/listPhoto.do", method=RequestMethod.GET)
	public String listPhoto(@PathVariable("ftrCde") String ftrCde, @PathVariable("ftrIdn") Long ftrIdn, Model model)  throws Exception {
	
		model.addAttribute("rows", dhPlanRoadSearchService.listPhoto(ftrCde, ftrIdn));
		return "jsonView";
	}
	
	/**
	 * 이미지 등록 페이지
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="{ftrCde}/{ftrIdn}/addPhoto.do", method=RequestMethod.GET)
	public String pageAddImage(@PathVariable("ftrCde") String ftrCde, @PathVariable("ftrIdn") Long ftrIdn, Model model) {
		
		model.addAttribute("ftrCde", ftrCde);
		model.addAttribute("ftrIdn", ftrIdn);
		model.addAttribute("imageTy", "CITY_PLAN_ROAD");
		
		return "projects/dh/job/cityPlanRoad/addPhoto";
	}
	
	/**
	 * 이미지 등록
	 * 
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

		if (dhPlanRoadSearchService.checkPhoto(imageDTO.getFtrCde(),imageDTO.getFtrIdn(), imageDTO.getImageSj())) {
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				MultipartFile multipartFile = entry.getValue();
				if(!multipartFile.isEmpty()) {
					model.addAttribute("data", dhPlanRoadSearchService.insertPhoto(imageDTO, multipartFile, 270, 210));
				}
			}
		}
		else {
			model.addAttribute("msg", imageDTO.getImageSj() + " 사진은 신규로 등록할 수 없습니다.");
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
		
		KwsImage kwsImage = dhPlanRoadSearchService.selectPhotoByNo(imageNo);
		
		model.addAttribute("image", kwsImage);
		return "projects/dh/job/cityPlanRoad/modifyPhoto";
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
	public String updatePhoto(ImageDTO imageDTO, Model model, MultipartRequest multipartRequest) throws Exception {

		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		imageDTO.setUpdusrId(userDTO.getUserId());
		
		MultipartFile multipartFile = null;
		if(multipartRequest.getFileMap().size() > 0) {
			for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
				multipartFile = entry.getValue();
			}
		}
		
		model.addAttribute("data", dhPlanRoadSearchService.updatePhoto(imageDTO, multipartFile, 270, 210));
		return "jsonViewTextPlain";
	}
	
}
