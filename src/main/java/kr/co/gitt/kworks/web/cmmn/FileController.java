package kr.co.gitt.kworks.web.cmmn;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.service.file.FileService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/////////////////////////////////////////////
/// @class FileController
/// kr.co.gitt.kworks.web.cmmn \n
///   ㄴ FileController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 20. 오후 2:24:06 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 파일 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/cmmn/file/")
public class FileController {

	/// 파일 서비스
	@Resource
	FileService fileService;
	
	/////////////////////////////////////////////
	/// @fn download
	/// @brief 함수 간략한 설명 : 파일 다운로드
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param fileNo
	/// @param response
	/// @param userAgent
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{fileNo}/download.do")
	public void download(@PathVariable("fileNo") Long fileNo, HttpServletResponse response, @RequestHeader(value="User-Agent") String userAgent) throws Exception {
		KwsFile kwsFile = fileService.selectOneFile(fileNo);
		InputStream is = new FileInputStream(kwsFile.getFileStreCours() + kwsFile.getStreFileNm());
		
		String fileName = kwsFile.getOrignlFileNm();
		String docName = null;
		if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")) {
			docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20")+";";
		}
		else {
			docName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"";
		}
		response.setHeader("Content-Disposition", "attachment; filename="+docName);
		
		OutputStream os = response.getOutputStream();
		IOUtils.copy(is,  os);
		is.close();
		os.close();
	}
	
	/////////////////////////////////////////////
	/// @fn selectOneFile
	/// @brief 함수 간략한 설명 : 파일 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param fileNo
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{fileNo}/select.do", method=RequestMethod.GET)
	public String selectOneFile(@PathVariable("fileNo") Long fileNo, Model model) {
		model.addAttribute("data", fileService.selectOneFile(fileNo));
		return "jsonView";
	}
	
}
