package kr.co.gitt.kworks.service.menu;

import java.util.List;

import kr.co.gitt.kworks.model.KwsMenu;

/////////////////////////////////////////////
/// @class MenuService
/// kr.co.gitt.kworks.service.menu \n
///   ㄴ MenuService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오후 12:39:10 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 메뉴 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface MenuService {

	/////////////////////////////////////////////
	/// @fn listAllMenu
	/// @brief 함수 간략한 설명 : 메뉴 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsMenu
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsMenu> listAllMenu(KwsMenu kwsMenu);
	
}
