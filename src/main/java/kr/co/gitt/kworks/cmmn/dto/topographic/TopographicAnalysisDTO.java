package kr.co.gitt.kworks.cmmn.dto.topographic;

/**
 * 지형단면을 분석하기 위한 모델
 * @author kwangsu.lee
 *
 */
public class TopographicAnalysisDTO {

	/**
	 * 레이어 그룹
	 */
	private String group;

	/**
	 * 표고모델 레이어
	 */
	private String layer;
	
	/**
	 * 시작 좌표(픽셀) X
	 */
	private Integer startX;
	
	/**
	 * 시작 좌표(픽셀) Y
	 */
	private Integer startY;

	/**
	 * 종료 좌표(픽셀) X
	 */
	private Integer endX;
	
	/**
	 * 종료 좌표(픽셀) Y
	 */
	private Integer endY;
	
	/**
	 * 분석 간격
	 */
	private Integer interval;
	
	/**
	 * 좌표계
	 */
	private String srs;
	
	/**
	 * 영역 최소X
	 */
	private Double minX;
	
	/**
	 * 영역 최소Y
	 */
	private Double minY;
	
	/**
	 * 영역 최대X
	 */
	private Double maxX;
	
	/**
	 * 영역 최대Y
	 */
	private Double maxY;
	
	/**
	 * 이미지 폭
	 */
	private Integer width;

	/**
	 * 이미지 높이
	 */
	private Integer height;
	
	
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public Integer getStartX() {
		return startX;
	}

	public void setStartX(Integer startX) {
		this.startX = startX;
	}

	public Integer getStartY() {
		return startY;
	}

	public void setStartY(Integer startY) {
		this.startY = startY;
	}

	public Integer getEndX() {
		return endX;
	}

	public void setEndX(Integer endX) {
		this.endX = endX;
	}

	public Integer getEndY() {
		return endY;
	}

	public void setEndY(Integer endY) {
		this.endY = endY;
	}

	public Integer getInterval() {
		return interval;
	}
	
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	
	public String getSrs() {
		return srs;
	}

	public void setSrs(String srs) {
		this.srs = srs;
	}

	public Double getMinX() {
		return minX;
	}

	public void setMinX(Double minX) {
		this.minX = minX;
	}

	public Double getMinY() {
		return minY;
	}

	public void setMinY(Double minY) {
		this.minY = minY;
	}

	public Double getMaxX() {
		return maxX;
	}

	public void setMaxX(Double maxX) {
		this.maxX = maxX;
	}

	public Double getMaxY() {
		return maxY;
	}

	public void setMaxY(Double maxY) {
		this.maxY = maxY;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
	
}
