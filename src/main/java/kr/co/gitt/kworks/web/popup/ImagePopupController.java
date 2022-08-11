package kr.co.gitt.kworks.web.popup;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/popup/image/")
public class ImagePopupController {

	@RequestMapping("view.do")
	public String imagePage(String imageFileNo, Model model) {
		model.addAttribute("imageFileNo", imageFileNo);
		return "popup/viewImage";
	}
	
}
