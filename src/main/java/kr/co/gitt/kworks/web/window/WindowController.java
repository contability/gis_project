package kr.co.gitt.kworks.web.window;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/////////////////////////////////////////////
/// @class WindowController
/// kr.co.gitt.kworks.web.window \n
///   ㄴ WindowController.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 21. 오후 9:07:13 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 윈도우 컨트롤러 입니다.
///   -# 
/////////////////////////////////////////////
@Controller
@RequestMapping("/window/")
public class WindowController {

	/////////////////////////////////////////////
	/// @fn windowPage
	/// @brief 함수 간략한 설명 : 윈도우 페이지
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param pageName
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("{pageName}/page.do")
	public String windowPage(@PathVariable("pageName") String pageName) {
		return "window/"+pageName;
	}
	
	/////////////////////////////////////////////
	/// @fn windowSubPage
	/// @brief 함수 간략한 설명 : 윈도우 페이지 (폴더/페이지)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param directoryName
	/// @param pageName
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	@RequestMapping("{directoryName}/{pageName}/page.do")
	public String windowSubPage(@PathVariable("directoryName") String directoryName, @PathVariable("pageName") String pageName) {
		return "window/"+directoryName+"/"+pageName;
	}
	
}
