package kr.co.gitt.kworks.rest.input;

import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.dto.feature.FeatureResultDTO;
import kr.co.gitt.kworks.dto.input.InputExcelDTO;
import kr.co.gitt.kworks.service.feature.FeatureService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

@Controller
@RequestMapping("/input/")
public class InputController {

	@Resource
	FeatureService featureService;

	@RequestMapping(value="excel.do", method=RequestMethod.POST)
	public String inputExcel(MultipartRequest multipartRequest, InputExcelDTO inputExcelDTO, Model model) {
		boolean isSuccess = true;
		FeatureResultDTO result = null;
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			MultipartFile multipartFile = entry.getValue();
			try {
				result = featureService.getFeaturesFromExcelFile(multipartFile, inputExcelDTO.getStartLine(), inputExcelDTO.getxIndex(), inputExcelDTO.getyIndex());
			} catch (Exception e) {
				isSuccess = false;
			}
		}
		model.addAttribute("isSuccess", isSuccess);
		model.addAttribute("result", result);
		return "jsonView";
	}
	
	
}
