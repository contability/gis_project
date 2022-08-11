package kr.co.gitt.kworks.rest.cmmn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.cmmn.dto.ImageDTO.ImageType;
import kr.co.gitt.kworks.model.KwsImage;
import kr.co.gitt.kworks.service.cmmn.ImageService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/////////////////////////////////////////////
/// @class ImageController
/// kr.co.gitt.kworks.rest.cmmn \n
///   ㄴ ImageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 8. 오후 2:56:00 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 이미지 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/cmmn/image/")
public class ImageController {

	/// 이미지 서비스
	@Resource
	ImageService imageService;
	
	/////////////////////////////////////////////
	/// @fn selectOneImagePage
	/// @brief 함수 간략한 설명 : 이미지 조회 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param imageNo
	/// @param kwsImage
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{imageNo}/select.do", method=RequestMethod.GET)
	public String selectOneImagePage(@PathVariable("imageNo") Long imageNo, KwsImage kwsImage, Model model) {
		model.addAttribute("result", imageService.selectOneImage(imageNo));
		return "/image/viewImage";
	}
	
	/////////////////////////////////////////////
	/// @fn downloadImage
	/// @brief 함수 간략한 설명 : 이미지 다운로드
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param imageNo
	/// @param request
	/// @param response
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{imageNo}/download.do", method=RequestMethod.GET)
	public void downloadImage(@PathVariable("imageNo") Long imageNo, HttpServletRequest request, HttpServletResponse response, @RequestHeader(value="User-Agent") String userAgent) throws IOException {
		download(imageNo, ImageType.IMAGE, response, userAgent);
	}
	
	/////////////////////////////////////////////
	/// @fn thumbnailImage
	/// @brief 함수 간략한 설명 : 썸네일 다운로드
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param imageNo
	/// @param request
	/// @param response
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{imageNo}/thumbnail.do", method=RequestMethod.GET)
	public void thumbnailImage(@PathVariable("imageNo") Long imageNo, HttpServletRequest request, HttpServletResponse response, @RequestHeader(value="User-Agent") String userAgent) throws IOException {
		download(imageNo, ImageType.THUMBNAIL, response, userAgent);
	}
	
	/////////////////////////////////////////////
	/// @fn outputImage
	/// @brief 함수 간략한 설명 : 출력용 이미지 다운로드
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param imageNo
	/// @param request
	/// @param response
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="{imageNo}/output.do", method=RequestMethod.GET)
	public void outputImage(@PathVariable("imageNo") Long imageNo, HttpServletRequest request, HttpServletResponse response, @RequestHeader(value="User-Agent") String userAgent) throws IOException {
		download(imageNo, ImageType.OUTPUT, response, userAgent);
	}
	
	/////////////////////////////////////////////
	/// @fn download
	/// @brief 함수 간략한 설명 : 다운로드 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param imageNo
	/// @param imageType
	/// @param response
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void download(Long imageNo, ImageType imageType, HttpServletResponse response, String userAgent) throws IOException {
		KwsImage kwsImage = imageService.selectOneImage(imageNo);
		File file = imageService.getImageFile(kwsImage, imageType);
		InputStream input = new FileInputStream(file);
		
		response.reset();
		
		String fileName = null;
		if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")) {
			fileName = URLEncoder.encode(file.getName(),"UTF-8").replaceAll("\\+", "%20")+";";
		}
		else {
			fileName = "\"" + new String(file.getName().getBytes("UTF-8"), "ISO-8859-1") + "\"";
		}
		response.setHeader("Content-Disposition", "attachment; filename="+fileName);
		
		OutputStream output = response.getOutputStream();
		IOUtils.copy(input, output);
		output.close();
	}
	
	
	
}
