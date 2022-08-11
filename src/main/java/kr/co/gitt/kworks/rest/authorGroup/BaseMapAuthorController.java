package kr.co.gitt.kworks.rest.authorGroup;

import java.util.ArrayList;
import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.UserDTO;
import kr.co.gitt.kworks.model.KwsAuthorGroup;
import kr.co.gitt.kworks.model.KwsBaseMapAuthor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

@Controller
@RequestMapping("/rest/baseMapAuthor/")
public class BaseMapAuthorController {

	@RequestMapping(value="/listAll.do", method=RequestMethod.GET)
	public String listAllBaseMapAuthor(Model model) {
		UserDTO userDTO = (UserDTO) EgovUserDetailsHelper.getAuthenticatedUser();
		List<Integer> mapNos = new ArrayList<Integer>();
		
		List<KwsBaseMapAuthor> kwsBaseMapAuthors = new ArrayList<KwsBaseMapAuthor>();
		List<KwsAuthorGroup> kwsAuthorGroups = userDTO.getKwsAuthorGroups();
		for(KwsAuthorGroup kwsAuthorGroup : kwsAuthorGroups) {
			for(KwsBaseMapAuthor kwsBaseMapAuthor : kwsAuthorGroup.getKwsBaseMapAuthors()) {
				Integer mapNo = kwsBaseMapAuthor.getMapNo();
				if(mapNos.contains(mapNo)) {
					for(KwsBaseMapAuthor baseMapAuthor : kwsBaseMapAuthors) {
						if(baseMapAuthor.getMapNo().equals(mapNo)) {
							if(kwsBaseMapAuthor.getIndictAt().equals("Y")) {
								baseMapAuthor.setIndictAt("Y");
							}
							if(kwsBaseMapAuthor.getEditAt().equals("Y")) {
								baseMapAuthor.setEditAt("Y");
							}
							if(kwsBaseMapAuthor.getExportAt().equals("Y")) {
								baseMapAuthor.setExportAt("Y");
							}
							if(kwsBaseMapAuthor.getPrntngAt().equals("Y")) {
								baseMapAuthor.setPrntngAt("Y");
							}
						}
					}
				}
				else {
					mapNos.add(mapNo);
					kwsBaseMapAuthors.add(kwsBaseMapAuthor);
				}	
			}
		}
		model.addAttribute("rows", kwsBaseMapAuthors);
		return "jsonView";
	}
	
}
