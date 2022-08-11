package kr.co.gitt.kworks.dto.sld.json;

/////////////////////////////////////////////
/// @class TextSymbolizer
/// kr.co.gitt.kworks.dto.sld.json \n
///   ㄴ TextSymbolizer.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 1. 13. 오후 6:06:50 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 문자형 심볼라이저 입니다.
///   -# 
/////////////////////////////////////////////
public class TextSymbolizer {

	/// 표시 여부
	private Boolean visible;
	
	/// 라벨 컬럼 명
	private String label;
	
	/// 글꼴
	private String fontFamily;
	
	/// 폰트 스타일
	private String fontStyle;
	
	/// 폰트 무게
	private String fontWeight;
	
	/// 폰트 사이즈
	private Double fontSize;
	
	/// 표시 위치
	private String anchor;
	
	/// 변위 X
	private Double displacementX;
	
	/// 변위 Y
	private Double displacementY;
	
	/// Halo 색상
	private String halo;
	
	/// Halo 반경
	private Double haloRadius;
	
	/// Halo 투명도
	private Double haloOpacity;
	
	/// 채우기 색상
	private String fill;
	
	/// 채우기 투명도
	private Double fillOpacity;
	
	/// 최대 축척
	private Double maxScale;
	
	/// 최소 축척
	private Double minScale;
	
	/// 벤더
	private Vendor vendor;

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	public String getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}

	public String getFontWeight() {
		return fontWeight;
	}

	public void setFontWeight(String fontWeight) {
		this.fontWeight = fontWeight;
	}

	public Double getFontSize() {
		return fontSize;
	}

	public void setFontSize(Double fontSize) {
		this.fontSize = fontSize;
	}

	public String getAnchor() {
		return anchor;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	public Double getDisplacementX() {
		return displacementX;
	}

	public void setDisplacementX(Double displacementX) {
		this.displacementX = displacementX;
	}

	public Double getDisplacementY() {
		return displacementY;
	}

	public void setDisplacementY(Double displacementY) {
		this.displacementY = displacementY;
	}

	public String getHalo() {
		return halo;
	}

	public void setHalo(String halo) {
		this.halo = halo;
	}

	public Double getHaloRadius() {
		return haloRadius;
	}

	public void setHaloRadius(Double haloRadius) {
		this.haloRadius = haloRadius;
	}

	public Double getHaloOpacity() {
		return haloOpacity;
	}

	public void setHaloOpacity(Double haloOpacity) {
		this.haloOpacity = haloOpacity;
	}

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

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
}
