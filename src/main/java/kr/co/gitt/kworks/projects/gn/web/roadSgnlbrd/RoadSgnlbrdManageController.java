package kr.co.gitt.kworks.projects.gn.web.roadSgnlbrd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/////////////////////////////////////////////
/// @class RoadSgnlbrdManageController
/// kr.co.gitt.kworks.projects.gn.web.roadSgnlbrd \n
///   ㄴ RoadSgnlbrdManageController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sjlee |
///    | Date | 2019. 10. 16. 오후 4:36:00 |
///    | Class Version | v1.0 |
///    | 작업자 | sjlee, Others... |
/// @section 상세설명
/// - 이 클래스는 강릉시 도로표지판 관리 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/roadSgnlbrd/")
@Profile({"gn_dev", "gn_oper"})
public class RoadSgnlbrdManageController {
	
	//logger
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/////////////////////////////////////////////
	/// @fn searchLandCenterCntrwkRegstrPage
	/// @brief 함수 간략한 설명 : 토지중심공사대장 검색 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ldConsMa
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping(value = "searchRoadSgnlbrdPage.do", method = RequestMethod.GET)
	public String searchRoadSgnlbrdPage() {
		return "/projects/gn/job/roadSgnlbrd/listRoadSgnlbrd";
	}
}
