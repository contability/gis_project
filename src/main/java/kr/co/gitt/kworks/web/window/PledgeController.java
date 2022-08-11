package kr.co.gitt.kworks.web.window;

import javax.annotation.Resource;

import kr.co.gitt.kworks.model.KwsPledge;
import kr.co.gitt.kworks.service.pledge.PledgeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/pledge/")
public class PledgeController {
	
	@Resource
	private PledgeService pledgeService;
	
	@RequestMapping(value="page.do", method=RequestMethod.GET)
	public String page(Model model){
		
		model.addAttribute("row", pledgeService.page());
		
		return "jsonView";
	}
	
	@RequestMapping(value="chk.do", method=RequestMethod.GET)
	public String pledgeChk(Model model){
		
		model.addAttribute("result", pledgeService.pledgeChk());
		
		return "jsonView";
	}
	
	@RequestMapping(value="insert.do", method=RequestMethod.POST)
	public String pledgeInsert(KwsPledge kwsPledge, Model model){
		
		model.addAttribute("result", pledgeService.pledgeInsert(kwsPledge));
		
		return "jsonView";
	}
	
}
