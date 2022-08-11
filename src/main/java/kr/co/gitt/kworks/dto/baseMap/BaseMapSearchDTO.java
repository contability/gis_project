package kr.co.gitt.kworks.dto.baseMap;

import java.util.List;

import kr.co.gitt.kworks.model.KwsBaseMap.MapType;

/////////////////////////////////////////////
/// @class BaseMapSearchDTO
/// kr.co.gitt.kworks.dto.baseMap \n
///   ㄴ BaseMapSearchDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 2. 3. 오전 10:05:43 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 기본 지도 검색 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class BaseMapSearchDTO {

	/// 기본 지도 목록
	private List<String> baseMaps;
	
	/// 지도 타입
	private MapType mapType; 

	public List<String> getBaseMaps() {
		return baseMaps;
	}

	public void setBaseMaps(List<String> baseMaps) {
		this.baseMaps = baseMaps;
	}

	public MapType getMapType() {
		return mapType;
	}

	public void setMapType(MapType mapType) {
		this.mapType = mapType;
	}
	
}
