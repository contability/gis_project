package kr.co.gitt.kworks.dto.sld;

public class KwsRulesVO {
	private String visible;
	private String title;
	private String name;
	private String maxScale;
	private String minScale;
	private KwsFilterVO filter;
	private KwsPointSymbolVO point;
	private KwsLineSymbolVO line;
	private KwsPolygonSymbolVO polygon;
	
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
	public KwsFilterVO getFilter() {
		return filter;
	}
	public void setFilter(KwsFilterVO filter) {
		this.filter = filter;
	}
	public KwsPointSymbolVO getPoint() {
		return point;
	}
	public void setPoint(KwsPointSymbolVO point) {
		this.point = point;
	}
	public KwsLineSymbolVO getLine() {
		return line;
	}
	public void setLine(KwsLineSymbolVO line) {
		this.line = line;
	}
	public KwsPolygonSymbolVO getPolygon() {
		return polygon;
	}
	public void setPolygon(KwsPolygonSymbolVO polygon) {
		this.polygon = polygon;
	}
}
