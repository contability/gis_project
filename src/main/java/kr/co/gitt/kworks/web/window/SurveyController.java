package kr.co.gitt.kworks.web.window;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsSurvey;
import kr.co.gitt.kworks.service.survey.SurveyService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SurveyController {
	
	@Resource
	SurveyService surveyService;
	
	@RequestMapping(value="/surveySelect.do", method=RequestMethod.GET)
	public String surveySelect(Model model){
		
		model.addAttribute("row", surveyService.select());
		
		return "jsonView";
	}
	
	@RequestMapping(value="/surveyInsert.do", method=RequestMethod.POST)
	public String surveyInsert(KwsSurvey kwsSurvey, Model model){
		
		model.addAttribute("rowCount", surveyService.insert(kwsSurvey));
		
		return "jsonView";
	}
	
}
