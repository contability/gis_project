package kr.co.gitt.kworks.dto.sld.json;

/////////////////////////////////////////////
/// @class Mark
/// kr.co.gitt.kworks.dto.sld.json \n
///   ㄴ Mark.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 1. 17. 오전 10:56:51 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 기호 입니다.
///   -# 
/////////////////////////////////////////////
public class Mark extends PolygonSymbolizer {

	/// 잘 알려진 명칭 
	private String wellKnownName;
	
	public String getWellKnownName() {
		return wellKnownName;
	}

	public void setWellKnownName(String wellKnownName) {
		this.wellKnownName = wellKnownName;
	}
	
}
