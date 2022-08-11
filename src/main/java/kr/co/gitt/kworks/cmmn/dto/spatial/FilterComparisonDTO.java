package kr.co.gitt.kworks.cmmn.dto.spatial;

/////////////////////////////////////////////
/// @class FilterComparisonDTO
/// kr.co.gitt.kworks.cmmn.dto.spatial \n
///   ㄴ FilterComparisonDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 1:54:00 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 비교 필터 입니다.
///   -# 
/////////////////////////////////////////////
public class FilterComparisonDTO {

	/////////////////////////////////////////////
	/// @class ComparisonType
	/// kr.co.gitt.kworks.cmmn.dto.spatial \n
	///   ㄴ FilterComparisonDTO.java
	/// @section 클래스작성정보
	///    |    항  목       |      내  용       |
	///    | :-------------: | -------------   |
	///    | Company | (주)GittGDS |    
	///    | Author | admin |
	///    | Date | 2016. 11. 21. 오후 1:54:10 |
	///    | Class Version | v1.0 |
	///    | 작업자 | admin, Others... |
	/// @section 상세설명
	/// - 이 클래스는 비교 타입입니다.
	///   -# 
	/////////////////////////////////////////////
	public enum ComparisonType {
		EQUAL_TO,
		LIKE
	}
	
	/// 비교 타입
	private ComparisonType comparisonType;
	
	/// 속성명
	private String property;
	
	/// 속성값
	private String value;
	
	public ComparisonType getComparisonType() {
		return comparisonType;
	}

	public void setComparisonType(ComparisonType comparisonType) {
		this.comparisonType = comparisonType;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
