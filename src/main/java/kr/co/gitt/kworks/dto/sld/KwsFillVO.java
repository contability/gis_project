package kr.co.gitt.kworks.dto.sld;

public class KwsFillVO extends KwsStrokeVO {
	private String fill;
	private String fillOpacity;
	
	public String getFill() {
		return fill;
	}
	public void setFill(String fill) {
		this.fill = fill;
	}
	public String getFillOpacity() {
		return fillOpacity;
	}
	public void setFillOpacity(String fillOpacity) {
		this.fillOpacity = fillOpacity;
	}
}
