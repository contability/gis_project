package kr.co.gitt.kworks.dto.sld;

public class KwsTextSymbolVO extends KwsFontVO {
	private String visible;
	private String title;
	private String name;
	private String label;
	private String anchor;
	private String displacementX;
	private String displacementY;
	private String fill;
	private String fillOpacity;
	private String maxScale;
	private String minScale;
	private KwsVendorVO vector;
	
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getAnchor() {
		return anchor;
	}
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	public String getDisplacementX() {
		return displacementX;
	}
	public void setDisplacementX(String displacementX) {
		this.displacementX = displacementX;
	}
	public String getDisplacementY() {
		return displacementY;
	}
	public void setDisplacementY(String displacementY) {
		this.displacementY = displacementY;
	}
	public String getFill() {
		return fill;
	}
	public void setFill(String fill) {
		this.fill = fill;
	}
	public String getFillOpacity() {
		return fillOpacity;
	}
	public String getMaxScale() {
		return maxScale;
	}
	public void setMaxScale(String maxScale) {
		this.maxScale = maxScale;
	}
	public String getMinScale() {
		return minScale;
	}
	public void setMinScale(String minScale) {
		this.minScale = minScale;
	}
	public void setFillOpacity(String fillOpacity) {
		this.fillOpacity = fillOpacity;
	}
	public KwsVendorVO getVector() {
		return vector;
	}
	public void setVector(KwsVendorVO vector) {
		this.vector = vector;
	}
}
