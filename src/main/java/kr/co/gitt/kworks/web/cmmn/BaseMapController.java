package kr.co.gitt.kworks.web.cmmn;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitt.kworks.dto.baseMap.BaseMapSearchDTO;
import kr.co.gitt.kworks.service.baseMap.BaseMapService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/////////////////////////////////////////////
/// @class BaseMapController
/// kr.co.gitt.kworks.web.cmmn \n
///   ㄴ BaseMapController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 2. 3. 오전 10:13:53 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 기본 지도 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/cmmn/baseMap/")
public class BaseMapController {
	
	/// 기본 지도 서비스
	@Resource
	BaseMapService baseMapService;
	
	/////////////////////////////////////////////
	/// @fn listAllBaseMap
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("listAll.do")
	public String listAllBaseMap(Model model) {
		model.addAttribute("rows", baseMapService.listAllBaseMap(null));
		return "jsonView";
	}
	
	/////////////////////////////////////////////
	/// @fn getBaseMapDescriptor
	/// @brief 함수 간략한 설명 : 기본 지도 기술자 반환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param baseMaps
	/// @param response
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("getBaseMapDescriptor.do")
	public void getBaseMapDescriptor(String baseMaps, HttpServletResponse response) throws IOException {
		BaseMapSearchDTO baseMapSearchDTO = new BaseMapSearchDTO();
		baseMapSearchDTO.setBaseMaps(Arrays.asList(baseMaps.split(",")));
		
		response.setHeader("content-type", "text/xml; charset=UTF-8;");
		response.getWriter().write(baseMapService.getBaseMapDescriptor(baseMapSearchDTO));
		response.getWriter().flush();
		response.getWriter().close();
	}

}
