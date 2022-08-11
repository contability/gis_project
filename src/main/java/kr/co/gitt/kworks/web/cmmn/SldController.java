package kr.co.gitt.kworks.web.cmmn;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.service.sld.SldCacheService;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/////////////////////////////////////////////
/// @class SldController
/// kr.co.gitt.kworks.web.gis \n
///   ㄴ SldController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 10. 오후 4:29:08 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 SLD 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/cmmn/sld/")
public class SldController {

	/// SLD 캐쉬 서비스
	@Resource
	SldCacheService sldCacheService;
	
	/////////////////////////////////////////////
	/// @fn putSLD
	/// @brief 함수 간략한 설명 : JSon 형태의 데이터를 받아 캐쉬에 저장 후 sldKey를 반환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param request
	/// @param model
	/// @return
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("putSLD.do")
	public String putSLD(HttpServletRequest request, Model model) throws IOException {
		String json = IOUtils.toString(request.getInputStream(), "UTF-8");
		model.addAttribute("sldKey", sldCacheService.putSLD(json));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn getSLD
	/// @brief 함수 간략한 설명 : sldKey 를 받아 캐쉬에서  XML 형태의 스타일 반환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param sldKey
	/// @param response
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("getSLD.do")
	public void getSLD(String sldKey, HttpServletResponse response) throws IOException {
		response.setHeader("content-type", "text/xml; charset=UTF-8;");
		response.getWriter().write(sldCacheService.getSLD(sldKey));
		response.getWriter().flush();
		response.getWriter().close();
	}
	
}
