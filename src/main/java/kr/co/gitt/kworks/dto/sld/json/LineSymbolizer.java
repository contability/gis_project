package kr.co.gitt.kworks.dto.sld.json;

/////////////////////////////////////////////
/// @class LineSymbolizer
/// kr.co.gitt.kworks.dto.sld.json \n
///   ㄴ LineSymbolizer.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 1. 17. 오전 10:17:15 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 선형 심볼라이저 입니다.
///   -# 
/////////////////////////////////////////////
public class LineSymbolizer {

	/// 선 색상
	private String stroke;
	
	/// 선 투명도
	private Double strokeOpacity;
	
	/// 선 두께
	private Double strokeWidth;
	
	/// 선 스타일
	private String strokeDasharray;

	public String getStroke() {
		return stroke;
	}

	public void setStroke(String stroke) {
		this.stroke = stroke;
	}

	public Double getStrokeOpacity() {
		return strokeOpacity;
	}

	public void setStrokeOpacity(Double strokeOpacity) {
		this.strokeOpacity = strokeOpacity;
	}

	public Double getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(Double strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	public String getStrokeDasharray() {
		return strokeDasharray;
	}

	public void setStrokeDasharray(String strokeDasharray) {
		this.strokeDasharray = strokeDasharray;
	}
	
}
