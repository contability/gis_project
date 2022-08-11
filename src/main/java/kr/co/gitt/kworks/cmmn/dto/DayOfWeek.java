package kr.co.gitt.kworks.cmmn.dto;

/////////////////////////////////////////////
/// @class DayOfWeek
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ DayOfWeek.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 1:57:41 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 요일 입니다.
///   -# 
/////////////////////////////////////////////
public enum DayOfWeek {
	MONDAY("월"),
	TUESDAY("화"),
	WEDNESDAY("수"),
	THURSDAY("목"),
	FRIDAY("금"),
	SATURDAY("토"),
	SUNDAY("일");
	
	private String value;
	
	private DayOfWeek(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
