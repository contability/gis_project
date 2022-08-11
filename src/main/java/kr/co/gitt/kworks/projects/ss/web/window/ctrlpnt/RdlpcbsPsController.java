package kr.co.gitt.kworks.projects.ss.web.window.ctrlpnt;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ctrlpnt/")
@Profile({"ss_dev", "ss_oper"})
public class RdlpcbsPsController {

	@RequestMapping(value="list.do")
	public String list(Model model){
		
		return "ctrlpnt/listRdlPcbsPs";
	}
	
	@RequestMapping(value="{objectid}/selectOne.do")
	public String selectOne(@PathVariable("ftrCde") String ftrCde, @PathVariable("ftrIdn") Integer ftrIdn, Model model){
		
		return "ctrlpnt/rdlPcbsPs";
	}
	
//	@RequestMapping(value="{objectid}/modify.do")
//	public Integer modify(RdlPcbsPs rdlPcbsPs, Model model){
//		
//	}
//	
//	@RequestMapping(value="add.do")
//	public Integer add(RdlPcbsPs rdlPcbsPs, Model model){
//		
//	}
	
}
