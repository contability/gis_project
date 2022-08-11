package kr.co.gitt.kworks.rest.analysis.topographic;

import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.topographic.TopographicAnalysisDTO;
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
public class TopographicAnalysisController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
    private MessageSource messageSource;
	
	@Resource
	TopographicAnalysisService topographicAnalysisService;	
	
	
	@RequestMapping(value="profile.do", method=RequestMethod.POST)
	public String profile(TopographicAnalysisDTO analysisDTO, Model model) throws Exception {
		String serviceUrl = messageSource.getMessage("Map.Url.WPS", null, Locale.getDefault());
		
		model.addAttribute("profileData", topographicAnalysisService.analysisProfile(serviceUrl, analysisDTO));		
		return "jsonView";
	}

}