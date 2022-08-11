package kr.co.gitt.kworks.dto.feature;

import com.kmaps.framework.common.Coordinate;
import com.kmaps.framework.spatialdata.geometry.IGeometry;

/////////////////////////////////////////////
/// @class KmlPlacemarkDTO
/// kr.co.gitt.kworks.dto.feature \n
///   ㄴ KmlPlacemarkDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | 이승재 |
///    | Date | 2019. 5. 16. |
///    | Class Version | v1.0 |
///    | 작업자 | 이승재 |
/// @section 상세설명
/// - 이 클래스는 KML Placemark 파싱결과 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class KmlPlacemarkDTO {

	private String name;
	
	private String description;
	
	private IGeometry geometry;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setGeometry(IGeometry geometry) {
		this.geometry = geometry;
	}
	
	public IGeometry getGeometry() {
		return geometry;
	}
}
