package kr.co.gitt.kworks.dto.sld.json;

/////////////////////////////////////////////
/// @class Filter
/// kr.co.gitt.kworks.dto.sld.json \n
///   ㄴ Filter.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 1. 17. 오전 10:09:16 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 필터 입니다.
///   -# 
/////////////////////////////////////////////
public class Filter {
	
	/// 타입
	private String type;
	
	/// 속성
	private String property;
	
	/// 값
	private String literal;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getLiteral() {
		return literal;
	}

	public void setLiteral(String literal) {
		this.literal = literal;
	}

}
