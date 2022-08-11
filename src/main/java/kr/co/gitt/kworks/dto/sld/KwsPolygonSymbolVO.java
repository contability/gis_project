package kr.co.gitt.kworks.dto.sld;

/**
 * import KWSStrokeVO
 * import KWSFillVO
 */
public class KwsPolygonSymbolVO extends KwsFillVO{
	String polygonFillUsed;
	String polygonStrokeUsed;
	
	public String getPolygonFillUsed() {
		return polygonFillUsed;
	}
	public void setPolygonFillUsed(String polygonFillUsed) {
		this.polygonFillUsed = polygonFillUsed;
	}
	public String getPolygonStrokeUsed() {
		return polygonStrokeUsed;
	}
	public void setPolygonStrokeUsed(String polygonStrokeUsed) {
		this.polygonStrokeUsed = polygonStrokeUsed;
	}
}
