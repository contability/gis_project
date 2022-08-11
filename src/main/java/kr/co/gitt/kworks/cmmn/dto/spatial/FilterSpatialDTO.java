package kr.co.gitt.kworks.cmmn.dto.spatial;

import kr.co.gitt.kworks.cmmn.dto.SpatialType;

/////////////////////////////////////////////
/// @class FilterSpatialDTO
/// kr.co.gitt.kworks.cmmn.dto.spatial \n
///   ㄴ FilterSpatialDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 1:56:04 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 공간 필터 입니다.
///   -# 
/////////////////////////////////////////////
public class FilterSpatialDTO {
	
	/// 공간 검색 타입
	private SpatialType spatialType;
	
	/// WKT (공간 정보 텍스트)
	private String wkt;

	public SpatialType getSpatialType() {
		return spatialType;
	}

	public void setSpatialType(SpatialType spatialType) {
		this.spatialType = spatialType;
	}

	public String getWkt() {
		return wkt;
	}

	public void setWkt(String wkt) {
		this.wkt = wkt;
	}
	
}
