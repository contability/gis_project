package kr.co.gitt.kworks.dto.sld.json;

/////////////////////////////////////////////
/// @class PointSymbolizer
/// kr.co.gitt.kworks.dto.sld.json \n
///   ㄴ PointSymbolizer.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 1. 17. 오전 10:13:31 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 점형 심볼라이저 입니다.
///   -# 
/////////////////////////////////////////////
public class PointSymbolizer {
	
	/// 리소스
	private String resource;
	
	/// 크기
	private Double size;
	
	/// 위치
	private String anchor;
	
	/// 기호 - 하수관거 방향성 표시를 위해 사용함
	private Mark mark;

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}


	public Double getSize() {
		return size;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public String getAnchor() {
		return anchor;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	public Mark getMark() {
		return mark;
	}

	public void setMark(Mark mark) {
		this.mark = mark;
	}
	
}
