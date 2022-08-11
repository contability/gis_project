package kr.co.gitt.kworks.projects.yy.web.ecologyNatureMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.projects.yy.service.ecologyNatureMap.EcologyNatureMapService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ecologyNatureMap/")
@Profile({"yy_dev", "yy_oper"})
public class EcologyNatureMapController {
	
	/// 메세지 소스
	@Resource(name="messageSource")
    MessageSource messageSource;

	@Resource
	EcologyNatureMapService ecologyNatureMapService;
	
	@RequestMapping(value="search.do", method=RequestMethod.GET)
	public String search(String dataId, String pnu, Model model) throws Exception {
		model.addAttribute("rows", ecologyNatureMapService.search(dataId, pnu));
		return "jsonView";
	}
	
	@RequestMapping(value="searchJibun.do", method=RequestMethod.GET)
	public String searchJibun(String dataId, String pnu, Model model) throws Exception {
		model.addAttribute("data", ecologyNatureMapService.searchJibun(dataId, pnu));
		return "jsonView";
	}
	
	@RequestMapping(value="pdf.do", method=RequestMethod.GET)
	public void downloadPdf(String shtNum, HttpServletResponse response, @RequestHeader(value="User-Agent") String userAgent) throws Exception {
		String path = messageSource.getMessage("Extensions.ecologyNatureMap.pdf.path", null, Locale.getDefault());
		File file = new File(path + shtNum + ".pdf");
		if(file.exists()) {
			InputStream is = new FileInputStream(file.getPath());
			
			String fileName = file.getName();
			String docName = null;
			if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")) {
				docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20")+";";
			}
			else {
				docName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"";
			}
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline; filename="+docName);
			
			OutputStream os = response.getOutputStream();
			IOUtils.copy(is, os);
			is.close();
			os.close();
		}
	}
	
	@RequestMapping(value="excel.do", method=RequestMethod.POST)
	public ModelAndView downloadExcel(String dataId, String pnu, String data) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", URLDecoder.decode(data, "UTF-8"));
		map.put("jibun", ecologyNatureMapService.searchJibun(dataId, pnu));
		map.put("grades", ecologyNatureMapService.search(dataId, pnu));
		return new ModelAndView("ecologyNatureMapView", "data", map);
	}
	
}
