package kr.co.gitt.kworks.dto.feature;

import java.util.List;

/////////////////////////////////////////////
/// @class FeatureKmlResultDTO
/// kr.co.gitt.kworks.dto.feature \n
///   ㄴ FeatureKmlResultDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | 이승재 |
///    | Date | 2019. 5. 16. |
///    | Class Version | v1.0 |
///    | 작업자 | 이승재 |
/// @section 상세설명
/// - 이 클래스는 KML Import 결과 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class FeatureKmlResultDTO {

	private String name;
	
	private List<KmlPlacemarkDTO> placemarks;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String Name() {
		return name;
	}
	
	public void setPlacemarks(List<KmlPlacemarkDTO> placemarks) {
		this.placemarks = placemarks;
	}
	
	public List<KmlPlacemarkDTO> getPlacemark() {
		return placemarks;
	}
}
