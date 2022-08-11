package kr.co.gitt.kworks.web.cmmn;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/////////////////////////////////////////////
/// @class DownloadController
/// kr.co.gitt.kworks.web.cmmn \n
///   ㄴ DownloadController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 30. 오후 6:16:51 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 다운로드 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/download/")
public class DownloadController {
	
	/////////////////////////////////////////////
	/// @fn downloadImage
	/// @brief 함수 간략한 설명 : Base64 로 된 이미지를 다운로드한다.
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param request
	/// @param response
	/// @param userAgent
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("base64Image.do")
	public void downloadImage(HttpServletRequest request, HttpServletResponse response, @RequestHeader(value="User-Agent") String userAgent) throws Exception {
		String data = request.getParameter("data");
		String base64Str = null;
		if(StringUtils.isBlank(data)) {
			data = IOUtils.toString(request.getInputStream());
			String[] split = data.split("%2C");
			base64Str = split[1];
		}
		else {
			String[] split = data.split(",");
			if(split.length == 2) {
				base64Str = split[1];
			}
		}
		
		if(StringUtils.isNotBlank(data)) {
			byte[] bytes = Base64.decodeBase64(base64Str);
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
			
			
			BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = bi.createGraphics();
			graphics.setColor(Color.white);
			graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());
			graphics.drawImage(image, 0, 0, null);
			
			String fileName = "export.png";
			String docName = null;
			if(StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:11.0") || StringUtils.contains(userAgent, "Chrome")) {
				docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20")+";";
			}
			else {
				docName = "\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"";
			}
			response.setHeader("Content-Disposition", "attachment; filename="+docName);
			
			OutputStream os = response.getOutputStream();
			ImageIO.write(bi, "png", os);
			os.close();
		}
	}

}
