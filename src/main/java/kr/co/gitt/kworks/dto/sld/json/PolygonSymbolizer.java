package kr.co.gitt.kworks.dto.sld.json;

/////////////////////////////////////////////
/// @class PolygonSymbolizer
/// kr.co.gitt.kworks.dto.sld.json \n
///   ㄴ PolygonSymbolizer.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 1. 17. 오전 10:36:14 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 면형 심볼라이저 입니다.
///   -# 
/////////////////////////////////////////////
public class PolygonSymbolizer extends LineSymbolizer {

	/// 면 색상
	private String fill;
	
	/// 면 투명도
	private Double fillOpacity;

	public String getFill() {
		return fill;
	}

	public void setFill(String fill) {
		this.fill = fill;
	}

	public Double getFillOpacity() {
		return fillOpacity;
	}

	public void setFillOpacity(Double fillOpacity) {
		this.fillOpacity = fillOpacity;
	}
	
}
