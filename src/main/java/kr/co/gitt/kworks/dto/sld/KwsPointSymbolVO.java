package kr.co.gitt.kworks.dto.sld;

/**
 * import KWSAnchorPointVO
 */

public class KwsPointSymbolVO {
	private String externalGraphic;
	private String resource;
	private String size;
	private String anchor;
	private String rotation;
	private String opacity;
	private String type;
	private KwsMarkVO mark;

	public String getExternalGraphic() {
		return externalGraphic;
	}
	public void setExternalGraphic(String externalGraphic) {
		this.externalGraphic = externalGraphic;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getAnchor() {
		return anchor;
	}
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	public String getRotation() {
		return rotation;
	}
	public void setRotation(String rotation) {
		this.rotation = rotation;
	}
	public String getOpacity() {
		return opacity;
	}
	public void setOpacity(String opacity) {
		this.opacity = opacity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public KwsMarkVO getMark() {
		return mark;
	}
	public void setMark(KwsMarkVO mark) {
		this.mark = mark;
	}
}
