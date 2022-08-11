package kr.co.gitt.kworks.projects.gn.dto;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;

/////////////////////////////////////////////
/// @class CityParkSearchDTO
/// kr.co.gitt.kworks.projects.gn.dto \n
///   ㄴ CityParkSearchDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 4. 21. 오후 5:17:45 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도시 공원 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class CityParkSearchDTO extends SearchDTO {

	/// 공원 종류
	private String prkCde;
	
	/// 법정동 코드
	private String bjdCde;
	
	/// 명칭
	private String prkNam;
	
	/// 위치
	private String prkLoc;
	
	/// 영역 WKT
	private String wkt;

	public String getPrkCde() {
		return prkCde;
	}

	public void setPrkCde(String prkCde) {
		this.prkCde = prkCde;
	}

	public String getBjdCde() {
		return bjdCde;
	}

	public void setBjdCde(String bjdCde) {
		this.bjdCde = bjdCde;
	}

	public String getPrkNam() {
		return prkNam;
	}

	public void setPrkNam(String prkNam) {
		this.prkNam = prkNam;
	}

	public String getPrkLoc() {
		return prkLoc;
	}

	public void setPrkLoc(String prkLoc) {
		this.prkLoc = prkLoc;
	}

	public String getWkt() {
		return wkt;
	}

	public void setWkt(String wkt) {
		this.wkt = wkt;
	}
	
}
