package kr.co.gitt.kworks.web.cmmn;

import javax.annotation.Resource;

import kr.co.gitt.kworks.service.topographyMap.TopographicMapService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 지형지도 컨트롤러
 * @author kwangsu.lee
 *
 */
@Controller
@RequestMapping("/cmmn/topographicMap/")
public class TopographicMapController {

	@Resource
	TopographicMapService topographicMapService;
	
	
	@RequestMapping("listAll.do")
	public String listAllTopographyMap(Model model) {
		
		model.addAttribute("groups", topographicMapService.listGroup());
		model.addAttribute("dataTypes", topographicMapService.listAll());
		return "jsonView";
	}
	
	@RequestMapping(value="metaPage.do", method=RequestMethod.GET)
	public String metaPage(Model model) {
		return "/window/topographic/metaInfo";	
	}
	
}