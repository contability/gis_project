package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsBaseMapLevel
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsBaseMapLevel.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 2. 1. 오전 11:19:51 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 기본 지도 레벨 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsBaseMapLevel {

	/// 레벨 번호
	private Long levelNo;
	
	/// 지도 번호
	private Long mapNo;
	
	/// 해상도
	private Double resolution;
	
	/// 번호
	private Integer num;

	public Long getLevelNo() {
		return levelNo;
	}

	public void setLevelNo(Long levelNo) {
		this.levelNo = levelNo;
	}

	public Long getMapNo() {
		return mapNo;
	}

	public void setMapNo(Long mapNo) {
		this.mapNo = mapNo;
	}

	public Double getResolution() {
		return resolution;
	}

	public void setResolution(Double resolution) {
		this.resolution = resolution;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
}
