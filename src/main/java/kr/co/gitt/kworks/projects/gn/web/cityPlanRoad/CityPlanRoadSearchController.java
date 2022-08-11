package kr.co.gitt.kworks.projects.gn.web.cityPlanRoad;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.cmmn.dto.cityplanroad.PlanRoadSearchDTO;
import kr.co.gitt.kworks.model.KwsData;
import kr.co.gitt.kworks.projects.gn.model.UrbPlanHt;
import kr.co.gitt.kworks.projects.gn.model.UrbPlanMa;
import kr.co.gitt.kworks.projects.gn.service.cityPlanRoad.CityPlanRoadSearchService;
import kr.co.gitt.kworks.rest.export.ExportImageDTO;
import kr.co.gitt.kworks.service.data.DataService;

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

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/rest/cityPlanRoad/")
@Profile({"gn_dev", "gn_oper"})
public class CityPlanRoadSearchController {

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
	DataService dataService;
	
	/**
	 * 검색 서비스
	 */
	@Resource
	CityPlanRoadSearchService cityPlanRoadSearchService;
	

	///////////////////////////////////////////////////////////////////////////////////////////
	// 검색
	
	// 조건 검색
	@RequestMapping(value="list.do", method=RequestMethod.POST)
	public String list(PlanRoadSearchDTO searchDTO, PaginationInfo paginationInfo, Model model) throws Exception {
		
		paginationInfo.setCurrentPageNo(searchDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchDTO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchDTO.getPageSize());
		
		searchDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("rows", cityPlanRoadSearchService.listRegister(searchDTO, paginationInfo));
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "jsonView";
	}
	
	// 단건 검색
	@RequestMapping(value="{uprIdn}/search.do", method=RequestMethod.POST)
	public String search(@PathVariable("uprIdn") String uprIdn, Model model) throws Exception {
		
		model.addAttribute("row", cityPlanRoadSearchService.getRegister(uprIdn));
		return "jsonView";
	}	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	// 도시계획도로
	//
	@RequestMapping(value="{ftrIdn}/viewRegister.do", method=RequestMethod.GET)
	public String pageRegister(@PathVariable("ftrIdn") Long ftrIdn, Model model)  throws Exception {
	
		String attribute = messageSource.getMessage("Extension.CityPlanRoad.Attribute", null, Locale.getDefault());
		KwsData attrData = dataService.selectOneData(attribute);
		String editAt = attrData.getEditAt();
		
		model.addAttribute("editAt", editAt);
		model.addAttribute("row", cityPlanRoadSearchService.getRegister(ftrIdn, false));
		return "window/cityPlanRoad/viewRegister";
	}

	@RequestMapping(value="{uprIdn}/addRegister.do", method=RequestMethod.GET)
	public String pageAdd(@PathVariable("uprIdn") String uprIdn, Model model)  throws Exception {
	
		model.addAttribute("uprIdn", uprIdn);
		return "window/cityPlanRoad/addRegister";
	}
	
	@RequestMapping(value="{ftrIdn}/modifyRegister.do", method=RequestMethod.GET)
	public String pageModify(@PathVariable("ftrIdn") Long ftrIdn, Model model)  throws Exception {
	
		model.addAttribute("row", cityPlanRoadSearchService.getRegister(ftrIdn, true));
		return "window/cityPlanRoad/modifyRegister";
	}

	@RequestMapping(value="insert.do", method=RequestMethod.POST)
	public String insertRegister(UrbPlanMa data, Model model)  throws Exception {
	
		model.addAttribute("type", "ins");
		model.addAttribute("state", cityPlanRoadSearchService.insertRegister(data));
		return "jsonView";
	}

	@RequestMapping(value="{ftrIdn}/update.do", method=RequestMethod.POST)
	public String updateRegister(@PathVariable("ftrIdn") Long ftrIdn, UrbPlanMa data, Model model)  throws Exception {
	
		model.addAttribute("type", "upd");
		model.addAttribute("state", cityPlanRoadSearchService.updateRegister(ftrIdn, data));
		return "jsonView";
	}

	@RequestMapping(value="{ftrIdn}/delete.do", method=RequestMethod.GET)
	public String deleteHistory(@PathVariable("ftrIdn") Long ftrIdn, Model model)  throws Exception {
	
		model.addAttribute("type", "del");
		model.addAttribute("state", cityPlanRoadSearchService.deleteRegister(ftrIdn));
		return "jsonView";
	}

	@RequestMapping(value="{ftrIdn}/capture.do", method=RequestMethod.POST)
	public String capture(@PathVariable("ftrIdn") Long ftrIdn, ExportImageDTO exportDTO, Model model) throws IOException, FdlException {
		
		UserDTO user = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		String fileName = user.getUserId() + "_" + System.currentTimeMillis();
		String fullName = fileName + ".jpg";
		String path = messageSource.getMessage("Com.CityPlanRoad.Image.Path", null, Locale.getDefault());
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
		
//		FileOutputStream fileStream = new FileOutputStream(fullPath);
//		JPEGEncodeParam jpegParams = new JPEGEncodeParam();
//        jpegParams.setQuality(1.0f);
//		ImageEncoder encoder = ImageCodec.createImageEncoder("JPEG", fileStream, jpegParams);
//		encoder.encode(image);
//		fileStream.flush();
//		fileStream.close();
		
		File outputfile = new File(fullPath);
		ImageIO.write(image, "JPG", outputfile);
		
		model.addAttribute("image", fileName);
		return "jsonView";		
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	// 도시계획도로 변경이력
	//
	@RequestMapping(value="{ftrIdn}/listHistory.do", method=RequestMethod.GET)
	public String getHistory(@PathVariable("ftrIdn") Long ftrIdn, Model model)  throws Exception {
	
		List<EgovMap> result = cityPlanRoadSearchService.getHistory(ftrIdn, false);
		model.addAttribute("rows", result);
		
		return "jsonView";
	}

	@RequestMapping(value="{ftrIdn}/addHistory.do", method=RequestMethod.GET)
	public String pageAddHistory(@PathVariable("ftrIdn") Long ftrIdn, Model model)  throws Exception {
	
		model.addAttribute("row", cityPlanRoadSearchService.getRegister(ftrIdn, true));
		return "window/cityPlanRoad/addHistory";
	}

	@RequestMapping(value="{ftrIdn}/{uprSeq}/detailHistory.do", method=RequestMethod.GET)
	public String pageDetail(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("uprSeq") Long uprSeq, Model model)  throws Exception {
	
		String attribute = messageSource.getMessage("Extension.CityPlanRoad.Attribute", null, Locale.getDefault());
		KwsData attrData = dataService.selectOneData(attribute);
		String editAt = attrData.getEditAt();
		
		model.addAttribute("editAt", editAt);
		if (editAt.equalsIgnoreCase("Y"))
			model.addAttribute("row", cityPlanRoadSearchService.getDetail(ftrIdn, uprSeq, true));
		else
			model.addAttribute("row", cityPlanRoadSearchService.getDetail(ftrIdn, uprSeq, false));
		return "window/cityPlanRoad/detailHistory";
	}

	@RequestMapping(value="{ftrIdn}/insert.do", method=RequestMethod.POST)
	public String insertHistory(@PathVariable("ftrIdn") Long ftrIdn, UrbPlanHt data, Model model)  throws Exception {
	
		model.addAttribute("type", "ins");
		model.addAttribute("state", cityPlanRoadSearchService.insertHistory(ftrIdn, data));
		return "jsonView";
	}

	@RequestMapping(value="{ftrIdn}/{uprSeq}/update.do", method=RequestMethod.POST)
	public String updateHistory(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("uprSeq") Long uprSeq, UrbPlanHt data, Model model)  throws Exception {
	
		model.addAttribute("type", "upd");
		model.addAttribute("state", cityPlanRoadSearchService.updateHistory(ftrIdn, uprSeq, data));
		return "jsonView";
	}

	@RequestMapping(value="{ftrIdn}/{uprSeq}/delete.do", method=RequestMethod.GET)
	public String deleteHistory(@PathVariable("ftrIdn") Long ftrIdn, @PathVariable("uprSeq") Long uprSeq, Model model)  throws Exception {

		model.addAttribute("type", "del");
		model.addAttribute("state", cityPlanRoadSearchService.deleteHistory(ftrIdn, uprSeq));
		return "jsonView";
	}

}
