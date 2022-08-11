package kr.co.gitt.kworks.dto.sld.json;

/////////////////////////////////////////////
/// @class Vendor
/// kr.co.gitt.kworks.dto.sld.json \n
///   ㄴ Vendor.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 1. 17. 오전 10:48:03 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 벤더 (업체마다 추가되거나 달라지는 속성) 입니다.
///   -# 
/////////////////////////////////////////////
public class Vendor {

	/// 선 따라 표시
	private String followLine;

	public String getFollowLine() {
		return followLine;
	}

	public void setFollowLine(String followLine) {
		this.followLine = followLine;
	}
	
}
