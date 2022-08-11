package kr.co.gitt.kworks.model;

import java.util.List;


/////////////////////////////////////////////
/// @class KwsUserSys
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsUserSys.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 3:00:05 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 시스템 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsUserSys extends KwsSys {
	
	/// 사용자 아이디
	private String userId;
	
	// 시스템 아이디
	private Long sysId;
	
	// 사용자 메뉴 클래스
	private List<KwsUserMenu> kwsUserMenu;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public List<KwsUserMenu> getKwsUserMenu() {
		return kwsUserMenu;
	}

	public void setKwsUserMenu(List<KwsUserMenu> kwsUserMenu) {
		this.kwsUserMenu = kwsUserMenu;
	}
}
