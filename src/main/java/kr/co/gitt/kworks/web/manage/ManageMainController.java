package kr.co.gitt.kworks.web.manage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/////////////////////////////////////////////
/// @class ManageMainController
/// kr.co.gitt.kworks.web.manage \n
///   ㄴ ManageMainController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 3. 오전 9:55:07 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 관리자 메인 컨트롤러입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/manage/main/")
public class ManageMainController {
	
	/////////////////////////////////////////////
	/// @fn mainPage
	/// @brief 함수 간략한 설명 : 관리자 메인 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param model
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("main.do")
	public String mainPage(Model model) {
		return "/manage/main/main";
	}
	
}
