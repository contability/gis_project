package kr.co.gitt.kworks.rest.analysis.topographic;

import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.topographic.TopographicSearchDTO;
import kr.co.gitt.kworks.service.analysis.topographic.TopographicAnalysisService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/topographic/")
public class TopographicSearchController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
    private MessageSource messageSource;
	
	@Resource
	TopographicAnalysisService topographicAnalysisService;	

	
	@RequestMapping(value="search.do", method=RequestMethod.POST)
	public String search(TopographicSearchDTO searchDTO, Model model) throws Exception {
		String serviceUrl = messageSource.getMessage("Map.Url.WPS", null, Locale.getDefault());
		
		model.addAttribute("gridData", topographicAnalysisService.search(serviceUrl, searchDTO));		
		return "jsonView";
	}

}
