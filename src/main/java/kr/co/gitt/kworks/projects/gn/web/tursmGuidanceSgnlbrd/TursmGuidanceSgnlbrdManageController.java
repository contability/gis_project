package kr.co.gitt.kworks.projects.gn.web.tursmGuidanceSgnlbrd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/////////////////////////////////////////////
/// @class TursmGuidanceSgnlbrdManageController
/// kr.co.gitt.kworks.projects.gn.web.tursmGuidanceSgnlbrd \n
///   ㄴ TursmGuidanceSgnlbrdManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)Gitt |    
///    | Author | shJung |
///    | Date | 2020. 11. 02. 오후 17:56:00 |
///    | Class Version | v1.0 |
///    | 작업자 | shJung, Others... |
/// @section 상세설명
/// - 이 클래스는 강릉시 관광안내표지판 관리 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/tursmGuidanceSgnlbrd/")
@Profile({"gn_dev","gn_oper"})
public class TursmGuidanceSgnlbrdManageController {
	
	//logger
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	// 관광안내표지판 검색 페이지
	@RequestMapping(value = "searchTursmGuidanceSgnlbrdPage.do", method = RequestMethod.GET)
	public String searchTursmGuidanceSgnlbrdPage(){
		return "/projects/gn/job/tursmGuidanceSgnlbrd/listTursmGuidanceSgnlbrd";
	}

}
