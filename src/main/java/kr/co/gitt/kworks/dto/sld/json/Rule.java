package kr.co.gitt.kworks.dto.sld.json;

/////////////////////////////////////////////
/// @class Rule
/// kr.co.gitt.kworks.dto.sld.json \n
///   ㄴ Rule.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 1. 13. 오후 5:03:50 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 룰 입니다.
///   -# 
/////////////////////////////////////////////
public class Rule {

	/// 이름
	private String name;
	
	/// 명칭
	private String title;
	
	/// 표시 여부
	private Boolean visible;
	
	/// 최대 축척
	private Double maxScale;
	
	/// 최소 축척
	private Double minScale;
	
	/// 필터
	private Filter filter;
	
	/// 점 심볼라이저
	private PointSymbolizer point;
	
	/// 선 심볼라이저
	private LineSymbolizer line;
	
	/// 면 심볼라이저
	private PolygonSymbolizer polygon;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Double getMaxScale() {
		return maxScale;
	}

	public void setMaxScale(Double maxScale) {
		this.maxScale = maxScale;
	}

	public Double getMinScale() {
		return minScale;
	}

	public void setMinScale(Double minScale) {
		this.minScale = minScale;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public PointSymbolizer getPoint() {
		return point;
	}

	public void setPoint(PointSymbolizer point) {
		this.point = point;
	}

	public LineSymbolizer getLine() {
		return line;
	}

	public void setLine(LineSymbolizer line) {
		this.line = line;
	}

	public PolygonSymbolizer getPolygon() {
		return polygon;
	}

	public void setPolygon(PolygonSymbolizer polygon) {
		this.polygon = polygon;
	}
	
}
