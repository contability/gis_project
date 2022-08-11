package kr.co.gitt.kworks.projects.gn.dto;

/////////////////////////////////////////////
/// @class CityParkFacilityResultDTO
/// kr.co.gitt.kworks.projects.gn.dto \n
///   ㄴ CityParkFacilityResultDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 4. 28. 오전 11:23:48 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도시공원 시설 결과 입니다.
///   -# 
/////////////////////////////////////////////
public class CityParkFacilityResultDTO {

	/// 종류
	private String kind;
	
	/// 일자
	private String dt;
	
	/// 수량
	private Long quantity;

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
}
