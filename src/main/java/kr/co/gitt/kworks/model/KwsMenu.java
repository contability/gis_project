package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsMenu
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsMenu.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오전 11:47:40 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 메뉴 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsMenu {
	
	// 메뉴 아이디
	private Long menuId;
	
	// 메뉴 명
	private String menuNm;
	
	// 상위 메뉴 아이디
	private Long upperMenuId;
	
	// 메뉴 순서
	private Long menuOrdr;
	
	// 사용 여부
	private String useAt;
	
	// URL
	private String url;
	
	// 시스템 아이디
	private Long sysId;
	
	// 기능 아이디
	private String fnctId;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
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

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public String getFnctId() {
		return fnctId;
	}

	public void setFnctId(String fnctId) {
		this.fnctId = fnctId;
	}


}
