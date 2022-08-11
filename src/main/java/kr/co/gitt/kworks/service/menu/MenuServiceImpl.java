package kr.co.gitt.kworks.service.menu;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.mappers.KwsMenuMapper;
import kr.co.gitt.kworks.model.KwsMenu;

import org.springframework.stereotype.Service;

/////////////////////////////////////////////
/// @class MenuServiceImpl
/// kr.co.gitt.kworks.service.menu \n
///   ㄴ MenuServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오후 12:39:25 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 메뉴 서비스 구현 입니다.
///   -# 
/////////////////////////////////////////////
@Service("menuService")
public class MenuServiceImpl implements MenuService {

	/// 메뉴 맵퍼
	@Resource
	KwsMenuMapper kwsMenuMapper;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.menu.MenuService#listAllMenu(kr.co.gitt.kworks.model.KwsMenu)
	/////////////////////////////////////////////
	@Override
	public List<KwsMenu> listAllMenu(KwsMenu kwsMenu) {
		return kwsMenuMapper.listAll(kwsMenu);
	}
	
}
