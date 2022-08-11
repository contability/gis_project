package kr.co.gitt.kworks.web.cmmn;

import javax.annotation.Resource;

import kr.co.gitt.kworks.service.cadastralMap.CadastralMapService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 지적원도/드론영상 컨트롤러
 * @author kwangsu.lee
 *
 */
@Controller
@Profile({"yy_dev", "yy_oper", "sc_dev", "sc_oper", "sunchang_dev", "sunchang_oper"})
@RequestMapping("/cmmn/cadastralMap/")
public class CadastralMapController {

	@Resource
	CadastralMapService cadastralMapService;
	
	@RequestMapping("listAll.do")
	public String listAll(Model model) {
		
		model.addAttribute("tiles", cadastralMapService.listAll());
		return "jsonView";
	}
	
}
