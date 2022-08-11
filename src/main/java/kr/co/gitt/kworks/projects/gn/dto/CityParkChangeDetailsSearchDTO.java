package kr.co.gitt.kworks.projects.gn.dto;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;

/////////////////////////////////////////////
/// @class CityParkChangeDetailsSearchDTO
/// kr.co.gitt.kworks.projects.gn.dto \n
///   ㄴ CityParkChangeDetailsSearchDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 5. 16. 오후 3:22:25 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도시 공원 변천 내역 검색 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class CityParkChangeDetailsSearchDTO extends SearchDTO {

	/// 분류
	private String type;
	
	/// 법정동
	private String bjdCde;
	
	/// 명칭
	private String name;
	
	/// 위치
	private String location;
	
	/// 기간 시작일
	private String dateStart;
	
	/// 기간 종료일
	private String dateEnd;
	
	/// 폐지 여부
	private String abolitionYn;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBjdCde() {
		return bjdCde;
	}

	public void setBjdCde(String bjdCde) {
		this.bjdCde = bjdCde;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getAbolitionYn() {
		return abolitionYn;
	}

	public void setAbolitionYn(String abolitionYn) {
		this.abolitionYn = abolitionYn;
	}
	
}
