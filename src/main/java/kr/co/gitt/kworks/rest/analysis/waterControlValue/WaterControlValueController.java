package kr.co.gitt.kworks.rest.analysis.waterControlValue;

import java.util.Locale;

import javax.annotation.Resource;

import kr.co.gitt.kmaps.tools.shutoffleakingpipe.AnalystShutOffLeaking;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/////////////////////////////////////////////
/// @class WaterControlValueController
/// kr.co.gitt.kworks.rest.analysis.waterControlValue \n
///   ㄴ WaterControlValueController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 30. 오전 11:07:31 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 차단제수변 분석 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/analysis/waterControlValue/")
public class WaterControlValueController {

	/// 메세지 소스
	@Resource
    private MessageSource messageSource;
		
	/////////////////////////////////////////////
	/// @fn analysis
	/// @brief 함수 간략한 설명 : 차단제수변 분석 (AnalystShutOffLeaking - 연구소 제공)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param x
	/// @param y
	/// @param buffer
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value="analysis.do", method=RequestMethod.POST)
	public String analysis(@RequestParam("x") Double x, @RequestParam("y") Double y, @RequestParam("buffer") Double buffer, Model model) {
		String serverType = messageSource.getMessage("Map.ServerType", null, Locale.getDefault());
		boolean paramArc = StringUtils.equalsIgnoreCase(serverType, "arcgis")?true:false;
		String paramGeom = messageSource.getMessage("Map.GeometryName", null, Locale.getDefault());
		String paramSrs = messageSource.getMessage("Map.Projection", null, Locale.getDefault());
		
		AnalystShutOffLeaking analysis = new AnalystShutOffLeaking(paramArc, buffer, paramGeom, paramSrs); 
		
		String url = messageSource.getMessage("Map.Url.WFS", null, Locale.getDefault());
		
		String[] host = url.split("/")[2].split(":");
		String ip = host[0];
		int port = Integer.parseInt(host[1]);
		
		model.addAttribute("data", analysis.execute(x, y, ip, port, url));
		
		return "jsonView";
	}
	
}
