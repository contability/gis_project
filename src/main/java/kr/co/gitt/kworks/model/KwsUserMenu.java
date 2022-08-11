package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsUserMenu
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsUserMenu.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 28. 오후 12:15:05 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 
///   -# 
/////////////////////////////////////////////
public class KwsUserMenu {
	
	// 메뉴 번호
	private Long menuNo;
	
	// 메뉴 명
	private String menuNm;
	
	// 상위 메뉴 아이디
	private Long upperMenuId;
	
	// 메뉴 순서
	private Long menuOrdr;
	
	// 원본 메뉴 아이디
	private Long orginlMenuId;
	
	// 시스템 아이디
	private Long sysId;

	// 사용자 아이디
	private String userId;
	
	// 메뉴
	private KwsMenu kwsMenu;

	public Long getMenuNo() {
		return menuNo;
	}

	public void setMenuNo(Long menuNo) {
		this.menuNo = menuNo;
	}

	public String getMenuNm() {
		return menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public Long getUpperMenuId() {
		return upperMenuId;
	}

	public void setUpperMenuId(Long upperMenuId) {
		this.upperMenuId = upperMenuId;
	}

	public Long getMenuOrdr() {
		return menuOrdr;
	}

	public void setMenuOrdr(Long menuOrdr) {
		this.menuOrdr = menuOrdr;
	}

	public Long getOrginlMenuId() {
		return orginlMenuId;
	}

	public void setOrginlMenuId(Long orginlMenuId) {
		this.orginlMenuId = orginlMenuId;
	}

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public KwsMenu getKwsMenu() {
		return kwsMenu;
	}

	public void setKwsMenu(KwsMenu kwsMenu) {
		this.kwsMenu = kwsMenu;
	}
}
